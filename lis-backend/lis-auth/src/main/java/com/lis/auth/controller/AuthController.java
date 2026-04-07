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

@Api(tags = "认证管理")
@RestController
@RequestMapping("/auth")
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
}
