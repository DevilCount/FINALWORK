package com.lis.hl7.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProcessingRuleDTO implements Serializable {

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

    private String remark;
}
