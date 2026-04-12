package com.lis.report.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(description = "检验申请项目信息")
public class ReportApplyItemDTO implements Serializable {

    @ApiModelProperty(value = "标本检验项目ID")
    private Long specimenTestItemId;

    @ApiModelProperty(value = "项目编码", required = true)
    private String itemCode;

    @ApiModelProperty(value = "项目名称", required = true)
    private String itemName;

    @ApiModelProperty(value = "项目英文名称")
    private String itemNameEn;

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

    @ApiModelProperty(value = "检测方法")
    private String method;

    @ApiModelProperty(value = "排序号")
    private Integer sort;
}
