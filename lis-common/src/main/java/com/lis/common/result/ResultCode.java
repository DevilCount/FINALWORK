package com.lis.common.result;

import lombok.Getter;

@Getter
public enum ResultCode {

    SUCCESS(200, "操作成功"),
    CREATED(201, "创建成功"),
    ACCEPTED(202, "请求已接受"),
    NO_CONTENT(204, "无内容"),

    BAD_REQUEST(400, "请求参数错误"),
    UNAUTHORIZED(401, "未授权"),
    FORBIDDEN(403, "禁止访问"),
    NOT_FOUND(404, "资源不存在"),
    METHOD_NOT_ALLOWED(405, "方法不允许"),
    CONFLICT(409, "资源冲突"),
    UNPROCESSABLE_ENTITY(422, "无法处理的实体"),
    TOO_MANY_REQUESTS(429, "请求过于频繁"),

    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
    SERVICE_UNAVAILABLE(503, "服务不可用"),
    GATEWAY_TIMEOUT(504, "网关超时"),

    BUSINESS_ERROR(1001, "业务异常"),
    VALIDATION_ERROR(1002, "参数校验失败"),
    DATA_NOT_FOUND(1003, "数据不存在"),
    DATA_ALREADY_EXISTS(1004, "数据已存在"),
    DATA_INTEGRITY_VIOLATION(1005, "数据完整性冲突"),
    OPERATION_NOT_ALLOWED(1006, "操作不允许"),

    TOKEN_INVALID(2001, "Token无效"),
    TOKEN_EXPIRED(2002, "Token已过期"),
    TOKEN_MISSING(2003, "Token缺失"),
    LOGIN_FAILED(2004, "登录失败"),
    ACCOUNT_DISABLED(2005, "账号已被禁用"),
    ACCOUNT_LOCKED(2006, "账号已被锁定"),
    PASSWORD_ERROR(2007, "密码错误"),
    PERMISSION_DENIED(2008, "权限不足"),

    REDIS_ERROR(3001, "Redis操作异常"),
    DATABASE_ERROR(3002, "数据库操作异常"),
    FILE_UPLOAD_ERROR(3003, "文件上传失败"),
    FILE_DOWNLOAD_ERROR(3004, "文件下载失败"),
    NETWORK_ERROR(3005, "网络异常");

    private final Integer code;
    private final String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
