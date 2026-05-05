package com.lis.hl7.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ValidationRuleDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String ruleCode;

    private String ruleName;

    private String ruleType;

    private String messageType;

    private String triggerEvent;

    private String segmentName;

    private String fieldPath;

    private String validationType;

    private String validationExpr;

    private String errorCode;

    private String errorMsg;

    private Integer isEnabled;

    private Integer sort;

    private String remark;
}
