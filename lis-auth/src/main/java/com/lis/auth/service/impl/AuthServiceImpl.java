package com.lis.auth.service.impl;

import com.lis.auth.dto.LoginDTO;
import com.lis.auth.dto.RefreshTokenDTO;
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

    private static final String REFRESH_TOKEN_PREFIX = RedisConstants.LOGIN_TOKEN_PREFIX + "refresh:";
    private static final String ACCESS_TOKEN_BLACKLIST_PREFIX = RedisConstants.BLACKLIST_PREFIX + "token:";

    @Override
    public LoginVO login(LoginDTO loginDTO) {
        Result<Map<String, Object>> userResult = userFeignClient.getUserByUsername(loginDTO.getUsername());
        if (userResult == null || !userResult.isSuccess() || userResult.getData() == null) {
            throw new AuthException(ResultCode.LOGIN_FAILED, "用户名或密码错误");
        }

        Map<String, Object> userMap = userResult.getData();
        Long userId = Long.parseLong(userMap.get("id").toString());
        String username = (String) userMap.get("username");
        String password = (String) userMap.get("password");
        String realName = (String) userMap.get("realName");
        Integer status = (Integer) userMap.get("status");

        if (status != null && status == 0) {
            throw new AuthException(ResultCode.ACCOUNT_DISABLED);
        }

        if (!passwordEncoder.matches(loginDTO.getPassword(), password)) {
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

            String username = jwtTokenProvider.getUsername(refreshToken);
            Result<Map<String, Object>> userResult = userFeignClient.getUserByUsername(username);
            if (userResult == null || !userResult.isSuccess() || userResult.getData() == null) {
                throw new AuthException(ResultCode.TOKEN_INVALID);
            }

            Map<String, Object> userMap = userResult.getData();
            Integer status = (Integer) userMap.get("status");

            if (status != null && status == 0) {
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
}
