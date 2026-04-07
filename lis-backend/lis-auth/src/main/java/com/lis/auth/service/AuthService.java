package com.lis.auth.service;

import com.lis.auth.dto.LoginDTO;
import com.lis.auth.dto.RefreshTokenDTO;
import com.lis.auth.vo.LoginVO;
import com.lis.auth.vo.TokenVO;

public interface AuthService {

    LoginVO login(LoginDTO loginDTO);

    TokenVO refreshToken(RefreshTokenDTO refreshTokenDTO);

    void logout(String token);

    void logoutAll(Long userId);
}
