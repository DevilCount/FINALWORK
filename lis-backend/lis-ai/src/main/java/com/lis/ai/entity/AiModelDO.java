package com.lis.ai.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("ai_model")
public class AiModelDO implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String modelCode;

    private String modelName;

    private String modelType;

    private String modelVersion;

    private String modelDesc;

    private String algorithmType;

    private String modelPath;

    private String modelParams;

    private String featureConfig;

    private String trainConfig;

    private BigDecimal accuracy;

    private BigDecimal precisionRate;

    private BigDecimal recallRate;

    private BigDecimal f1Score;

    private BigDecimal aucScore;

    private LocalDateTime trainTime;

    private Integer trainSamples;

    private Integer isDefault;

    private Integer isEnabled;

    private String status;

    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    private String remark;

    @TableField("del_flag")
    @TableLogic
    private Integer delFlag;
}
