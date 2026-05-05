package com.lis.hl7.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class TransformRuleVO implements Serializable {

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

    private String createBy;

    private LocalDateTime createTime;

    private String updateBy;

    private LocalDateTime updateTime;

    private String remark;
}
