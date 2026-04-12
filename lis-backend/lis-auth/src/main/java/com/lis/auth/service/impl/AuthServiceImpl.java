package com.lis.auth.service.impl;

import com.lis.auth.dto.LoginDTO;
import com.lis.auth.dto.RefreshTokenDTO;
import com.lis.auth.feign.LogFeignClient;
import com.lis.auth.feign.UserFeignClient;
import com.lis.auth.service.AuthService;
import com.lis.auth.util.JwtTokenProvider;
import com.lis.auth.vo.LoginVO;
import com.lis.auth.vo.TokenVO;
import com.lis.common.constants.RedisConstants;
import com.lis.common.exception.AuthException;
import com.lis.common.result.Result;
import com.lis.common.result.ResultCode;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserFeignClient userFeignClient;
    private final JwtTokenProvider jwtTokenProvider;
    private final StringRedisTemplate stringRedisTemplate;
    private final PasswordEncoder passwordEncoder;
    private final LogFeignClient logFeignClient;

    private static final String REFRESH_TOKEN_PREFIX = RedisConstants.LOGIN_TOKEN_PREFIX + "refresh:";
    private static final String ACCESS_TOKEN_BLACKLIST_PREFIX = RedisConstants.BLACKLIST_PREFIX + "token:";
    private static final String CAPTCHA_PREFIX = "captcha:";
    private static final String LOGIN_FAIL_PREFIX = "login_fail:";
    private static final int MAX_LOGIN_FAIL_COUNT = 5;
    private static final long LOGIN_LOCK_MINUTES = 15;

    @Override
    public LoginVO login(LoginDTO loginDTO) {
        if (loginDTO.getUuid() != null && loginDTO.getCode() != null) {
            String captchaKey = CAPTCHA_PREFIX + loginDTO.getUuid();
            String captchaValue = stringRedisTemplate.opsForValue().get(captchaKey);
            stringRedisTemplate.delete(captchaKey);
            if (captchaValue == null || !captchaValue.equalsIgnoreCase(loginDTO.getCode())) {
                throw new AuthException(ResultCode.LOGIN_FAILED, "验证码错误");
            }
        }

        log.info("登录请求: username={}", loginDTO.getUsername());

        String failKey = LOGIN_FAIL_PREFIX + loginDTO.getUsername();
        String failCountStr = stringRedisTemplate.opsForValue().get(failKey);
        if (failCountStr != null && Integer.parseInt(failCountStr) >= MAX_LOGIN_FAIL_COUNT) {
            throw new AuthException(ResultCode.ACCOUNT_LOCKED, "账号已锁定，请15分钟后重试");
        }

        Result<Map<String, Object>> userResult = userFeignClient.getUserByUsername(loginDTO.getUsername());
        log.debug("Feign调用结果: success={}", userResult != null && userResult.isSuccess());
        if (userResult == null || !userResult.isSuccess() || userResult.getData() == null) {
            throw new AuthException(ResultCode.LOGIN_FAILED, "用户名或密码错误");
        }

        Map<String, Object> userMap = userResult.getData();
        Object idObj = userMap.get("id");
        Object usernameObj = userMap.get("username");
        Object passwordObj = userMap.get("password");
        Object statusObj = userMap.get("status");

        Long userId = Long.parseLong(idObj.toString());
        String username = usernameObj != null ? usernameObj.toString() : null;
        String password = passwordObj != null ? passwordObj.toString() : null;
        String realName = userMap.get("realName") != null ? userMap.get("realName").toString() : null;
        Integer status = statusObj != null ? Integer.parseInt(statusObj.toString()) : null;

        if (status != null && status != 0) {
            throw new AuthException(ResultCode.ACCOUNT_DISABLED);
        }

        if (!passwordEncoder.matches(loginDTO.getPassword(), password)) {
            Long failCount = stringRedisTemplate.opsForValue().increment(failKey);
            if (failCount != null && failCount == 1) {
                stringRedisTemplate.expire(failKey, LOGIN_LOCK_MINUTES, TimeUnit.MINUTES);
            }
            saveLoginLog(loginDTO.getUsername(), null, 1, "用户名或密码错误");
            throw new AuthException(ResultCode.LOGIN_FAILED, "用户名或密码错误");
        }

        Result<List<String>> rolesResult = userFeignClient.getRolesByUserId(userId);
        List<String> roles = (rolesResult != null && rolesResult.isSuccess())
                ? rolesResult.getData() : List.of();

        Result<List<String>> permissionsResult = userFeignClient.getPermissionsByUserId(userId);
        List<String> permissions = (permissionsResult != null && permissionsResult.isSuccess())
                ? permissionsResult.getData() : List.of();

        String accessToken = jwtTokenProvider.generateAccessToken(userId, username, roles, permissions);
        String refreshToken = jwtTokenProvider.generateRefreshToken(userId);

        stringRedisTemplate.delete(failKey);

        stringRedisTemplate.opsForValue().set(
                REFRESH_TOKEN_PREFIX + userId,
                refreshToken,
                jwtTokenProvider.getRefreshTokenExpiration(),
                TimeUnit.MILLISECONDS
        );

        LoginVO loginVO = new LoginVO();
        loginVO.setAccessToken(accessToken);
        loginVO.setRefreshToken(refreshToken);
        loginVO.setExpiresIn(jwtTokenProvider.getAccessTokenExpiration());
        loginVO.setUserId(userId);
        loginVO.setUsername(username);
        loginVO.setRealName(realName);
        loginVO.setRoles(roles);
        loginVO.setPermissions(permissions);

        log.info("用户登录成功: userId={}, username={}", userId, username);
        saveLoginLog(username, userId, 0, "登录成功");
        return loginVO;
    }

    @Override
    public TokenVO refreshToken(RefreshTokenDTO refreshTokenDTO) {
        String refreshToken = refreshTokenDTO.getRefreshToken();

        try {
            String tokenType = jwtTokenProvider.getTokenType(refreshToken);
            if (!"refresh".equals(tokenType)) {
                throw new AuthException(ResultCode.TOKEN_INVALID, "无效的刷新令牌");
            }

            Long userId = jwtTokenProvider.getUserId(refreshToken);
            if (userId == null) {
                throw new AuthException(ResultCode.TOKEN_INVALID);
            }

            String storedToken = stringRedisTemplate.opsForValue().get(REFRESH_TOKEN_PREFIX + userId);
            if (storedToken == null || !storedToken.equals(refreshToken)) {
                throw new AuthException(ResultCode.TOKEN_EXPIRED, "刷新令牌已失效");
            }

            Result<Map<String, Object>> userResult = userFeignClient.getUserById(userId);
            if (userResult == null || !userResult.isSuccess() || userResult.getData() == null) {
                throw new AuthException(ResultCode.TOKEN_INVALID);
            }

            Map<String, Object> userMap = userResult.getData();
            String username = userMap.get("username") != null ? userMap.get("username").toString() : null;
            Integer status = userMap.get("status") != null ? Integer.parseInt(userMap.get("status").toString()) : null;

            if (status != null && status != 0) {
                throw new AuthException(ResultCode.ACCOUNT_DISABLED);
            }

            Result<List<String>> rolesResult = userFeignClient.getRolesByUserId(userId);
            List<String> roles = (rolesResult != null && rolesResult.isSuccess())
                    ? rolesResult.getData() : List.of();

            Result<List<String>> permissionsResult = userFeignClient.getPermissionsByUserId(userId);
            List<String> permissions = (permissionsResult != null && permissionsResult.isSuccess())
                    ? permissionsResult.getData() : List.of();

            String newAccessToken = jwtTokenProvider.generateAccessToken(userId, username, roles, permissions);
            String newRefreshToken = jwtTokenProvider.generateRefreshToken(userId);

            stringRedisTemplate.opsForValue().set(
                    REFRESH_TOKEN_PREFIX + userId,
                    newRefreshToken,
                    jwtTokenProvider.getRefreshTokenExpiration(),
                    TimeUnit.MILLISECONDS
            );

            TokenVO tokenVO = new TokenVO();
            tokenVO.setAccessToken(newAccessToken);
            tokenVO.setRefreshToken(newRefreshToken);
            tokenVO.setExpiresIn(jwtTokenProvider.getAccessTokenExpiration());

            log.info("Token刷新成功: userId={}", userId);
            return tokenVO;
        } catch (JwtException e) {
            throw new AuthException(ResultCode.TOKEN_EXPIRED);
        }
    }

    @Override
    public void logout(String token) {
        try {
            Long userId = jwtTokenProvider.getUserId(token);
            if (userId != null) {
                stringRedisTemplate.delete(REFRESH_TOKEN_PREFIX + userId);
            }

            long expiration = jwtTokenProvider.getAccessTokenExpiration();
            stringRedisTemplate.opsForValue().set(
                    ACCESS_TOKEN_BLACKLIST_PREFIX + token,
                    "1",
                    expiration,
                    TimeUnit.MILLISECONDS
            );

            log.info("用户登出成功: userId={}", userId);
        } catch (Exception e) {
            log.warn("登出处理异常: {}", e.getMessage());
        }
    }

    @Override
    public void logoutAll(Long userId) {
        stringRedisTemplate.delete(REFRESH_TOKEN_PREFIX + userId);
        log.info("用户全部Token失效: userId={}", userId);
    }

    private void saveLoginLog(String username, Long userId, int status, String msg) {
        try {
            Map<String, Object> loginLog = new HashMap<>();
            loginLog.put("userName", username);
            loginLog.put("status", status);
            loginLog.put("msg", msg);
            loginLog.put("loginTime", LocalDateTime.now().toString());
            if (userId != null) {
                loginLog.put("userId", userId);
            }
            logFeignClient.saveLoginLog(loginLog);
        } catch (Exception e) {
            log.warn("保存登录日志失败: {}", e.getMessage());
        }
    }
}
