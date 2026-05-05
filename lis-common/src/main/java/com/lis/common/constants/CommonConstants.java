package com.lis.common.constants;

public final class CommonConstants {

    private CommonConstants() {
    }

    public static final String UTF8 = "UTF-8";

    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String TIME_FORMAT = "HH:mm:ss";
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String TIMESTAMP_FORMAT = "yyyyMMddHHmmssSSS";

    public static final Integer STATUS_ENABLE = 1;
    public static final Integer STATUS_DISABLE = 0;

    public static final Integer DELETE_FLAG_NORMAL = 0;
    public static final Integer DELETE_FLAG_DELETED = 1;

    public static final Long DEFAULT_PAGE_SIZE = 10L;
    public static final Long MAX_PAGE_SIZE = 100L;

    public static final String DEFAULT_PASSWORD = "123456";

    public static final String SUPER_ADMIN_ROLE = "SUPER_ADMIN";
    public static final String ADMIN_ROLE = "ADMIN";
    public static final String USER_ROLE = "USER";

    public static final String ANONYMOUS_USER = "anonymousUser";

    public static final String LOGIN_TOKEN_KEY = "login:token:";
    public static final String LOGIN_USER_KEY = "login:user:";
    public static final String CAPTCHA_KEY = "captcha:";
    public static final String RATE_LIMIT_KEY = "rate:limit:";

    public static final String SUCCESS_MSG = "操作成功";
    public static final String FAIL_MSG = "操作失败";
}
