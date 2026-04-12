package com.lis.gateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {

    private SecretKey getSigningKey(String secret) {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims parseToken(String token, String secret) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey(secret))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            log.error("JWT token已过期: {}", e.getMessage());
            throw e;
        } catch (SignatureException e) {
            log.error("JWT签名验证失败: {}", e.getMessage());
            throw e;
        } catch (MalformedJwtException e) {
            log.error("JWT token格式错误: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("JWT解析异常: {}", e.getMessage());
            throw e;
        }
    }

    public boolean validateToken(String token, String secret) {
        try {
            Claims claims = parseToken(token, secret);
            return !isTokenExpired(claims);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }

    public String getUsername(Claims claims) {
        Object username = claims.get("username");
        if (username != null) {
            return username.toString();
        }
        return claims.getSubject();
    }

    public Long getUserId(Claims claims) {
        Object userId = claims.get("userId");
        if (userId != null) {
            if (userId instanceof Integer) {
                return ((Integer) userId).longValue();
            }
            if (userId instanceof Long) {
                return (Long) userId;
            }
            if (userId instanceof String) {
                return Long.parseLong((String) userId);
            }
        }
        String sub = claims.getSubject();
        if (sub != null) {
            return Long.parseLong(sub);
        }
        return null;
    }
}
