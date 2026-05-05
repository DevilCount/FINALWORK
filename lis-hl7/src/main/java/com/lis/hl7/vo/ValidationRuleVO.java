package com.lis.hl7.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ValidationRuleVO implements Serializable {

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

    private String createBy;

    private LocalDateTime createTime;

    private String updateBy;

    private LocalDateTime updateTime;

    private String remark;
}
