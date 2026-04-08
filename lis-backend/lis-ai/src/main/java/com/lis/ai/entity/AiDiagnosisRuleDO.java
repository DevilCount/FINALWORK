package com.lis.ai.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("ai_diagnosis_rule")
public class AiDiagnosisRuleDO implements Serializable {

    @TableId(type = IdType.AUTO)
    @TableLogic
    private Long id;

    private String ruleCode;

    private String ruleName;

    private String ruleType;

    private String category;

    private Long modelId;

    private String testItemIds;

    private String ruleCondition;

    private String ruleExpr;

    private String diagnosisTemplate;

    private String suggestionTemplate;

    private BigDecimal confidenceThreshold;

    private Integer priority;

    private Integer isEnabled;

    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    private String remark;
}
