package com.lis.hl7.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ProcessingRuleVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String ruleCode;

    private String ruleName;

    private Long interfaceId;

    private String messageType;

    private String triggerEvent;

    private String conditionExpr;

    private String actionType;

    private String actionConfig;

    private Integer priority;

    private Integer isEnabled;

    private String createBy;

    private LocalDateTime createTime;

    private String updateBy;

    private LocalDateTime updateTime;

    private String remark;
}
