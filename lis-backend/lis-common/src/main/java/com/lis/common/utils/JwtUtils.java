package com.lis.common.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class JwtUtils {

    @Value("${jwt.secret:lis-backend-jwt-secret-key-for-hs512-algorithm-must-be-at-least-512-bits-long-aka-64-bytes}")
    private String secret;

    @Value("${jwt.expiration:86400000}")
    private Long expiration;

    @Value("${jwt.header:Authorization}")
    private String header;

    @Value("${jwt.prefix:Bearer }")
    private String prefix;

    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(Long userId, String username) {
        return generateToken(userId, username, new HashMap<>());
    }

    public String generateToken(Long userId, String username, Map<String, Object> claims) {
        Map<String, Object> allClaims = new HashMap<>(claims);
        allClaims.put("userId", userId);
        allClaims.put("username", username);

        return Jwts.builder()
                .setClaims(allClaims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public String generateToken(Long userId, String username, Long expirationMillis) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("username", username);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(secretKey, SignatureAlgorithm.HS512)
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
        } catch (ExpiredJwtException e) {
            log.warn("Token已过期: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.warn("不支持的Token: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.warn("Token格式错误: {}", e.getMessage());
        } catch (SignatureException e) {
            log.warn("Token签名无效: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.warn("Token为空: {}", e.getMessage());
        }
        return false;
    }

    public boolean isTokenExpired(String token) {
        try {
            Claims claims = parseToken(token);
            if (claims == null) {
                return true;
            }
            return claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    public Long getUserId(String token) {
        Claims claims = parseToken(token);
        if (claims == null) {
            return null;
        }
        Object userId = claims.get("userId");
        if (userId instanceof Integer) {
            return ((Integer) userId).longValue();
        }
        return (Long) userId;
    }

    public String getUsername(String token) {
        Claims claims = parseToken(token);
        return claims != null ? claims.getSubject() : null;
    }

    public Date getExpiration(String token) {
        Claims claims = parseToken(token);
        return claims != null ? claims.getExpiration() : null;
    }

    public Date getIssuedAt(String token) {
        Claims claims = parseToken(token);
        return claims != null ? claims.getIssuedAt() : null;
    }

    public String getHeader() {
        return header;
    }

    public String getPrefix() {
        return prefix;
    }

    public Long getExpiration() {
        return expiration;
    }

    public String extractToken(String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith(prefix)) {
            return bearerToken.substring(prefix.length());
        }
        return null;
    }
}
