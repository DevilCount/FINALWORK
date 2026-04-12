package com.lis.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("sys_audit_log")
public class AuditLogDO implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private LocalDateTime auditTime;

    private String auditType;

    private String targetType;

    private Long targetId;

    private String targetName;

    private String action;

    private String oldValue;

    private String newValue;

    private String diffSummary;

    private Long operatorId;

    private String operatorName;

    private String operatorIp;

    private String result;

    private String remark;

    private Integer delFlag;

    private LocalDateTime createTime;
}
