package com.lis.report.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "报告明细响应")
public class ReportItemVO implements Serializable {

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "报告ID")
    private Long reportId;

    @ApiModelProperty(value = "报告编号")
    private String reportNo;

    @ApiModelProperty(value = "标本检验项目ID")
    private Long specimenTestItemId;

    @ApiModelProperty(value = "项目编码")
    private String itemCode;

    @ApiModelProperty(value = "项目名称")
    private String itemName;

    @ApiModelProperty(value = "项目英文名")
    private String itemNameEn;

    @ApiModelProperty(value = "结果值")
    private String resultValue;

    @ApiModelProperty(value = "结果文本")
    private String resultText;

    @ApiModelProperty(value = "结果标志（N正常 H偏高 L偏低 HH极高 LL极低）")
    private String resultFlag;

    @ApiModelProperty(value = "单位")
    private String unit;

    @ApiModelProperty(value = "参考值下限")
    private BigDecimal referenceLow;

    @ApiModelProperty(value = "参考值上限")
    private BigDecimal referenceHigh;

    @ApiModelProperty(value = "参考值文本")
    private String referenceText;

    @ApiModelProperty(value = "危急值下限")
    private BigDecimal panicLow;

    @ApiModelProperty(value = "危急值上限")
    private BigDecimal panicHigh;

    @ApiModelProperty(value = "是否危急值")
    private Integer isPanic;

    @ApiModelProperty(value = "是否异常")
    private Integer isAbnormal;

    @ApiModelProperty(value = "检验方法")
    private String method;

    @ApiModelProperty(value = "检验设备")
    private String equipmentName;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "备注")
    private String remark;
}
