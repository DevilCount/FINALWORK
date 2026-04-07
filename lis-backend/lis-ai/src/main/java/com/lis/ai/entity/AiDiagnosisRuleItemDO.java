package com.lis.ai.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("ai_diagnosis_rule_item")
public class AiDiagnosisRuleItemDO implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long ruleId;

    private Long testItemId;

    private String itemCode;

    private String itemName;

    private String conditionType;

    private String conditionExpr;

    private BigDecimal weight;

    private Integer sort;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
