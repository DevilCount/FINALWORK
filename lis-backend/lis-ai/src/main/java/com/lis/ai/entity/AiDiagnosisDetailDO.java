package com.lis.ai.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("ai_diagnosis_detail")
public class AiDiagnosisDetailDO implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long diagnosisId;

    private Long testItemId;

    private String itemCode;

    private String itemName;

    private String resultValue;

    private String unit;

    private BigDecimal referenceLow;

    private BigDecimal referenceHigh;

    private Integer isAbnormal;

    private BigDecimal abnormalDegree;

    private BigDecimal featureImportance;

    private BigDecimal contributionScore;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField("del_flag")
    @TableLogic
    private Integer delFlag;
}
