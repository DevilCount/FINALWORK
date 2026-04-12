package com.lis.common.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class AuditLogMessage implements Serializable {

    private String auditType;

    private String action;

    private String targetType;

    private Long targetId;

    private String targetName;

    private Long auditUserId;

    private String auditUserName;

    private String auditOpinion;

    private LocalDateTime auditTime;

    private String detail;
}
