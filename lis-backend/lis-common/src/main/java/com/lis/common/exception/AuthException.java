package com.lis.common.exception;

import com.lis.common.enums.ResultCode;

@Getter
public class AuthException extends RuntimeException {

    private Integer code;

    public AuthException(String message) {
        super(message);
        this.code = 401;
    }

    public AuthException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public AuthException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
    }
}
