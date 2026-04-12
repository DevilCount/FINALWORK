package com.lis.common.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ErrorLogMessage implements Serializable {

    private String errorCode;

    private String errorMsg;

    private String errorStack;

    private String className;

    private String methodName;

    private String requestUrl;

    private String requestMethod;

    private String requestParam;

    private Long userId;

    private String userName;

    private String ip;

    private LocalDateTime errorTime;
}
