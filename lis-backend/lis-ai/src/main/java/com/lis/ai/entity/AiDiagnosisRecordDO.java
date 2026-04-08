package com.lis.ai.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("ai_diagnosis_record")
public class AiDiagnosisRecordDO implements Serializable {

    @TableId(type = IdType.AUTO)
    @TableLogic
    private Long id;

    private String diagnosisNo;

    private Long reportId;

    private String reportNo;

    private Long specimenId;

    private String specimenNo;

    private Long patientId;

    private String patientName;

    private String patientGender;

    private String patientAge;

    private Long modelId;

    private String modelName;

    private Long ruleId;

    private String ruleName;

    private String inputData;

    private String diagnosisResult;

    private String diagnosisCode;

    private String diagnosisName;

    private BigDecimal confidence;

    private BigDecimal probability;

    private String suggestion;

    private String riskLevel;

    private Integer isAbnormal;

    private Integer isPanic;

    private LocalDateTime diagnosisTime;

    private Long diagnosisDuration;

    private Integer reviewStatus;

    private Long reviewUserId;

    private String reviewUserName;

    private LocalDateTime reviewTime;

    private String reviewRemark;

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
