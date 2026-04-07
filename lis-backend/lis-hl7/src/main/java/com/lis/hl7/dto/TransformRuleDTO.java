package com.lis.hl7.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class TransformRuleDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String ruleCode;

    private String ruleName;

    private String ruleType;

    private String sourceType;

    private String targetType;

    private String sourcePath;

    private String targetPath;

    private String transformExpr;

    private String defaultValue;

    private String valueMapping;

    private Integer isRequired;

    private Integer status;

    private Integer sort;

    private String remark;
}
