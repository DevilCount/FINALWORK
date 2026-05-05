package com.lis.report.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(description = "结果录入请求参数")
public class ResultEntryDTO implements Serializable {

    @NotNull(message = "报告ID不能为空")
    @ApiModelProperty(value = "报告ID", required = true)
    private Long reportId;

    @ApiModelProperty(value = "报告明细ID")
    private Long reportItemId;

    @ApiModelProperty(value = "标本检验项目ID")
    private Long specimenTestItemId;

    @NotNull(message = "项目编码不能为空")
    @ApiModelProperty(value = "项目编码", required = true)
    private String itemCode;

    @NotNull(message = "项目名称不能为空")
    @ApiModelProperty(value = "项目名称", required = true)
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

    @ApiModelProperty(value = "检验方法")
    private String method;

    @ApiModelProperty(value = "检验设备")
    private String equipmentName;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "备注")
    private String remark;
}
