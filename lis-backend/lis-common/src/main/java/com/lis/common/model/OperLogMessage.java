package com.lis.common.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class OperLogMessage implements Serializable {

    private String title;

    private Integer businessType;

    private String method;

    private String requestMethod;

    private String operName;

    private String operUrl;

    private String operIp;

    private String operParam;

    private String operResult;

    private Integer costTime;

    private String deptName;

    private String operLocation;

    private LocalDateTime operTime;
}
