package com.lis.gateway.filter;

import com.alibaba.fastjson2.JSON;
import com.lis.gateway.config.GatewayProperties;
import com.lis.gateway.config.JwtProperties;
import com.lis.gateway.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

    private final JwtProperties jwtProperties;
    private final GatewayProperties gatewayProperties;
    private final JwtUtil jwtUtil;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    public JwtAuthenticationFilter(JwtProperties jwtProperties, GatewayProperties gatewayProperties, JwtUtil jwtUtil) {
        this.jwtProperties = jwtProperties;
        this.gatewayProperties = gatewayProperties;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        if (isWhitePath(path)) {
            return chain.filter(exchange);
        }

        String token = getToken(request);
        if (!StringUtils.hasText(token)) {
            return unauthorized(exchange, "未提供认证令牌");
        }

        try {
            Claims claims = jwtUtil.parseToken(token, jwtProperties.getSecret());
            
            if (jwtUtil.isTokenExpired(claims)) {
                return unauthorized(exchange, "令牌已过期");
            }

            ServerHttpRequest mutatedRequest = request.mutate()
                    .header("X-User-Id", String.valueOf(jwtUtil.getUserId(claims)))
                    .header("X-User-Name", jwtUtil.getUsername(claims))
                    .build();

            return chain.filter(exchange.mutate().request(mutatedRequest).build());

        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            return unauthorized(exchange, "令牌已过期");
        } catch (io.jsonwebtoken.SignatureException e) {
            return unauthorized(exchange, "令牌签名无效");
        } catch (Exception e) {
            log.error("JWT验证异常: {}", e.getMessage(), e);
            return unauthorized(exchange, "令牌验证失败");
        }
    }

    private boolean isWhitePath(String path) {
        List<String> whiteList = gatewayProperties.getWhiteList();
        if (whiteList == null || whiteList.isEmpty()) {
            return false;
        }
        return whiteList.stream().anyMatch(pattern -> pathMatcher.match(pattern, path));
    }

    private String getToken(ServerHttpRequest request) {
        String header = request.getHeaders().getFirst(jwtProperties.getHeader());
        if (StringUtils.hasText(header) && header.startsWith(jwtProperties.getPrefix())) {
            return header.substring(jwtProperties.getPrefix().length());
        }
        String param = request.getQueryParams().getFirst("token");
        if (StringUtils.hasText(param)) {
            return param;
        }
        return null;
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange, String message) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> result = new HashMap<>();
        result.put("code", HttpStatus.UNAUTHORIZED.value());
        result.put("message", message);
        result.put("data", null);

        byte[] bytes = JSON.toJSONString(result).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bytes);
        
        return response.writeWith(Mono.just(buffer));
    }

    @Override
    public int getOrder() {
        return -100;
    }
}
