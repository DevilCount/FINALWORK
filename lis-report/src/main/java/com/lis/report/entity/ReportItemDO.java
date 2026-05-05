package com.lis.report.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("report_item")
public class ReportItemDO implements Serializable {

    @TableId(type = IdType.AUTO)
    @TableLogic
    private Long id;

    private Long reportId;

    private String reportNo;

    private Long specimenTestItemId;

    private String itemCode;

    private String itemName;

    private String itemNameEn;

    private String resultValue;

    private String resultText;

    private String resultFlag;

    private String unit;

    private BigDecimal referenceLow;

    private BigDecimal referenceHigh;

    private String referenceText;

    private BigDecimal panicLow;

    private BigDecimal panicHigh;

    private Integer isPanic;

    private Integer isAbnormal;

    private String method;

    private String equipmentName;

    private Integer sort;

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
