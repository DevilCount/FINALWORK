package com.lis.common.constants;

public final class SecurityConstants {

    private SecurityConstants() {
    }

    public static final String JWT_HEADER = "Authorization";
    public static final String JWT_PREFIX = "Bearer ";

    public static final String ROLE_PREFIX = "ROLE_";

    public static final String PERMISSION_ALL = "*:*:*";

    public static final String ANONYMOUS_URL = "/anonymous/**";
    public static final String LOGIN_URL = "/auth/login";
    public static final String LOGOUT_URL = "/auth/logout";
    public static final String REGISTER_URL = "/auth/register";
    public static final String CAPTCHA_URL = "/auth/captcha";
    public static final String REFRESH_TOKEN_URL = "/auth/refresh";

    public static final String[] WHITE_LIST = {
            "/actuator/**",
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/v2/api-docs",
            "/v3/api-docs/**",
            "/webjars/**",
            "/doc.html",
            "/favicon.ico",
            "/error"
    };

    public static final String CURRENT_USER_KEY = "currentUser";
    public static final String USER_ID_KEY = "userId";
    public static final String USERNAME_KEY = "username";
    public static final String ROLES_KEY = "roles";
    public static final String PERMISSIONS_KEY = "permissions";
}
