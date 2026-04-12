package com.lis.auth.controller;

import com.lis.auth.dto.LoginDTO;
import com.lis.auth.dto.PasswordUpdateDTO;
import com.lis.auth.dto.RefreshTokenDTO;
import com.lis.auth.feign.UserFeignClient;
import com.lis.auth.service.AuthService;
import com.lis.auth.vo.*;
import com.lis.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Api(tags = "认证管理")
@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserFeignClient userFeignClient;
    private final StringRedisTemplate stringRedisTemplate;
    private final PasswordEncoder passwordEncoder;

    private static final String CAPTCHA_PREFIX = "captcha:";
    private static final int CAPTCHA_WIDTH = 120;
    private static final int CAPTCHA_HEIGHT = 40;

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public Result<LoginVO> login(@Validated @RequestBody LoginDTO loginDTO) {
        LoginVO loginVO = authService.login(loginDTO);
        return Result.success("登录成功", loginVO);
    }

    @ApiOperation("刷新Token")
    @PostMapping("/refresh")
    public Result<TokenVO> refreshToken(@Validated @RequestBody RefreshTokenDTO refreshTokenDTO) {
        TokenVO tokenVO = authService.refreshToken(refreshTokenDTO);
        return Result.success("Token刷新成功", tokenVO);
    }

    @ApiOperation("用户登出")
    @PostMapping("/logout")
    public Result<Void> logout(@RequestHeader(value = "Authorization", required = false) String authorization) {
        if (authorization != null && authorization.startsWith("Bearer ")) {
            String token = authorization.substring(7);
            authService.logout(token);
        }
        return Result.success("登出成功", null);
    }

    @ApiOperation("获取验证码")
    @GetMapping("/captcha")
    public Result<CaptchaVO> getCaptcha() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        Random random = new Random();
        int a = random.nextInt(50) + 1;
        int b = random.nextInt(50) + 1;
        String captchaText = String.valueOf(a + b);
        String captchaQuestion = a + " + " + b + " = ?";

        String base64Image;
        try {
            System.setProperty("java.awt.headless", "true");
            BufferedImage image = new BufferedImage(CAPTCHA_WIDTH, CAPTCHA_HEIGHT, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, CAPTCHA_WIDTH, CAPTCHA_HEIGHT);
            g.setColor(new Color(random.nextInt(100), random.nextInt(100), random.nextInt(100)));
            g.setFont(new Font("Arial", Font.BOLD, 24));
            g.drawString(captchaQuestion, 10, 28);
            for (int i = 0; i < 6; i++) {
                g.setColor(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
                int x1 = random.nextInt(CAPTCHA_WIDTH);
                int y1 = random.nextInt(CAPTCHA_HEIGHT);
                int x2 = random.nextInt(CAPTCHA_WIDTH);
                int y2 = random.nextInt(CAPTCHA_HEIGHT);
                g.drawLine(x1, y1, x2, y2);
            }
            g.dispose();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            base64Image = Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (Exception e) {
            base64Image = Base64.getEncoder().encodeToString(captchaQuestion.getBytes());
        }

        stringRedisTemplate.opsForValue().set(CAPTCHA_PREFIX + uuid, captchaText, 5, TimeUnit.MINUTES);

        return Result.success(new CaptchaVO(uuid, "data:image/png;base64," + base64Image));
    }

    @ApiOperation("获取当前用户信息")
    @GetMapping("/user-info")
    public Result<CurrentUserInfoVO> getUserInfo(
            @RequestHeader(value = "X-User-Id", required = false) String userIdStr,
            @RequestHeader(value = "X-User-Name", required = false) String username) {
        return doGetUserInfo(userIdStr, username);
    }

    @ApiOperation("获取当前用户信息(别名)")
    @GetMapping("/info")
    public Result<CurrentUserInfoVO> getInfo(
            @RequestHeader(value = "X-User-Id", required = false) String userIdStr,
            @RequestHeader(value = "X-User-Name", required = false) String username) {
        return doGetUserInfo(userIdStr, username);
    }

    private Result<CurrentUserInfoVO> doGetUserInfo(String userIdStr, String username) {
        if (userIdStr == null || userIdStr.isEmpty() || "null".equals(userIdStr)) {
            return Result.fail(401, "未登录");
        }

        Long userId;
        try {
            userId = Long.parseLong(userIdStr);
        } catch (NumberFormatException e) {
            return Result.fail(401, "用户ID格式错误");
        }

        Result<Map<String, Object>> userResult = userFeignClient.getUserByUsername(username);
        if (userResult == null || !userResult.isSuccess() || userResult.getData() == null) {
            return Result.fail("用户信息获取失败");
        }

        Map<String, Object> userMap = userResult.getData();

        UserInfoVO userInfoVO = new UserInfoVO();
        if (userMap.get("id") != null) {
            userInfoVO.setId(Long.parseLong(userMap.get("id").toString()));
        }
        userInfoVO.setUsername(userMap.get("username") != null ? userMap.get("username").toString() : null);
        userInfoVO.setRealName(userMap.get("realName") != null ? userMap.get("realName").toString() : null);
        userInfoVO.setPhone(userMap.get("phone") != null ? userMap.get("phone").toString() : null);
        userInfoVO.setEmail(userMap.get("email") != null ? userMap.get("email").toString() : null);
        if (userMap.get("deptId") != null) {
            try {
                userInfoVO.setDeptId(Long.parseLong(userMap.get("deptId").toString()));
            } catch (NumberFormatException ignored) {
            }
        }
        userInfoVO.setDeptName(userMap.get("deptName") != null ? userMap.get("deptName").toString() : null);
        if (userMap.get("status") != null) {
            try {
                userInfoVO.setStatus(Integer.parseInt(userMap.get("status").toString()));
            } catch (NumberFormatException ignored) {
            }
        }
        userInfoVO.setAvatar(userMap.get("avatar") != null ? userMap.get("avatar").toString() : null);

        Result<List<String>> rolesResult = userFeignClient.getRolesByUserId(userId);
        List<String> roles = (rolesResult != null && rolesResult.isSuccess())
                ? rolesResult.getData() : List.of();

        Result<List<String>> permissionsResult = userFeignClient.getPermissionsByUserId(userId);
        List<String> permissions = (permissionsResult != null && permissionsResult.isSuccess())
                ? permissionsResult.getData() : List.of();

        CurrentUserInfoVO result = new CurrentUserInfoVO();
        result.setUser(userInfoVO);
        result.setRoles(roles);
        result.setPermissions(permissions);

        return Result.success(result);
    }

    @ApiOperation("修改密码")
    @PostMapping("/password")
    public Result<Void> updatePassword(
            @Validated @RequestBody PasswordUpdateDTO passwordDTO,
            @RequestHeader(value = "X-User-Id", required = false) String userIdStr,
            @RequestHeader(value = "X-User-Name", required = false) String username) {
        if (userIdStr == null || userIdStr.isEmpty() || "null".equals(userIdStr)) {
            return Result.fail(401, "未登录");
        }

        Long userId;
        try {
            userId = Long.parseLong(userIdStr);
        } catch (NumberFormatException e) {
            return Result.fail(401, "用户ID格式错误");
        }

        Result<Map<String, Object>> userResult = userFeignClient.getUserByUsername(username);
        if (userResult == null || !userResult.isSuccess() || userResult.getData() == null) {
            return Result.fail("用户信息获取失败");
        }

        Map<String, Object> userMap = userResult.getData();
        String currentPassword = (String) userMap.get("password");

        if (!passwordEncoder.matches(passwordDTO.getOldPassword(), currentPassword)) {
            return Result.fail("旧密码错误");
        }

        String encodedPassword = passwordEncoder.encode(passwordDTO.getNewPassword());
        userFeignClient.updatePassword(userId, encodedPassword);

        return Result.success("密码修改成功", null);
    }
}
