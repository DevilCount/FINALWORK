package com.lis.common.constants;

public final class RedisConstants {

    private RedisConstants() {
    }

    public static final String LOGIN_TOKEN_PREFIX = "login:token:";
    public static final String LOGIN_USER_PREFIX = "login:user:";
    public static final String CAPTCHA_PREFIX = "captcha:";
    public static final String RATE_LIMIT_PREFIX = "rate:limit:";
    public static final String BLACKLIST_PREFIX = "blacklist:";
    public static final String PERMISSION_PREFIX = "permission:";
    public static final String ROLE_PREFIX = "role:";
    public static final String MENU_PREFIX = "menu:";
    public static final String DICT_PREFIX = "dict:";
    public static final String CONFIG_PREFIX = "config:";
    public static final String LOCK_PREFIX = "lock:";

    public static final Long DEFAULT_EXPIRE_TIME = 7200L;
    public static final Long CAPTCHA_EXPIRE_TIME = 300L;
    public static final Long TOKEN_EXPIRE_TIME = 86400L;
    public static final Long CACHE_EXPIRE_TIME = 3600L;

    public static final Long LOCK_WAIT_TIME = 3L;
    public static final Long LOCK_LEASE_TIME = 30L;
}
