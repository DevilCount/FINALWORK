package com.lis.auth.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class JwtTokenProvider {

    @Value("${jwt.secret:lis-backend-jwt-secret-key-for-hs512-algorithm-must-be-at-least-512-bits-long-aka-64-bytes}")
    private String secret;

    @Value("${jwt.access-token-expiration:7200000}")
    private Long accessTokenExpiration;

    @Value("${jwt.refresh-token-expiration:604800000}")
    private Long refreshTokenExpiration;

    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(Long userId, String username, List<String> roles, List<String> permissions) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userId.toString());
        claims.put("username", username);
        claims.put("roles", roles);
        claims.put("permissions", permissions);
        claims.put("type", "access");
        return createToken(claims, accessTokenExpiration);
    }

    public String generateRefreshToken(Long userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userId.toString());
        claims.put("type", "refresh");
        return createToken(claims, refreshTokenExpiration);
    }

    private String createToken(Map<String, Object> claims, Long expiration) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(secretKey, io.jsonwebtoken.SignatureAlgorithm.HS512)
                .compact();
    }

    public Claims parseToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error("解析Token失败: {}", e.getMessage());
            return null;
        }
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            log.warn("Token验证失败: {}", e.getMessage());
            return false;
        }
    }

    public Long getUserId(String token) {
        Claims claims = parseToken(token);
        if (claims == null) {
            return null;
        }
        String sub = claims.get("sub", String.class);
        return sub != null ? Long.parseLong(sub) : null;
    }

    public String getUsername(String token) {
        Claims claims = parseToken(token);
        return claims != null ? claims.get("username", String.class) : null;
    }

    public String getTokenType(String token) {
        Claims claims = parseToken(token);
        return claims != null ? claims.get("type", String.class) : null;
    }

    @SuppressWarnings("unchecked")
    public List<String> getRoles(String token) {
        Claims claims = parseToken(token);
        return claims != null ? claims.get("roles", List.class) : null;
    }

    @SuppressWarnings("unchecked")
    public List<String> getPermissions(String token) {
        Claims claims = parseToken(token);
        return claims != null ? claims.get("permissions", List.class) : null;
    }

    public Long getAccessTokenExpiration() {
        return accessTokenExpiration;
    }

    public Long getRefreshTokenExpiration() {
        return refreshTokenExpiration;
    }
}
