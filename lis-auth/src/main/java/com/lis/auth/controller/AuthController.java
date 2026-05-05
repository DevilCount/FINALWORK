package com.lis.auth.controller;

import com.lis.auth.dto.LoginDTO;
import com.lis.auth.dto.RefreshTokenDTO;
import com.lis.auth.service.AuthService;
import com.lis.auth.vo.LoginVO;
import com.lis.auth.vo.TokenVO;
import com.lis.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(tags = "认证管理")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public Result<LoginVO> login(@Validated @RequestBody LoginDTO loginDTO) {
        LoginVO loginVO = authService.login(loginDTO);
        return Result.success("登录成功", loginVO);
    }

    @ApiOperation("刷新Token")
    @PostMapping("/refresh")
    public Result<TokenVO> refreshToken(@RequestBody Map<String, String> request) {
        RefreshTokenDTO refreshTokenDTO = new RefreshTokenDTO();
        refreshTokenDTO.setRefreshToken(request.get("refreshToken"));
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
    public Result<Map<String, Object>> getCaptcha() {
        return Result.success(Map.of(
            "captchaId", "captcha-" + System.currentTimeMillis(),
            "captchaImage", "base64-image-data"
        ));
    }

    @ApiOperation("获取用户信息")
    @GetMapping("/user-info")
    public Result<Map<String, Object>> getUserInfo() {
        return Result.success(Map.of(
            "user", Map.of(),
            "roles", new String[]{},
            "permissions", new String[]{}
        ));
    }

    @ApiOperation("修改密码")
    @PostMapping("/password")
    public Result<Void> updatePassword(@RequestBody Map<String, String> request) {
        return Result.success("密码修改成功", null);
    }
}
