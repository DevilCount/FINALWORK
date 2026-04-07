package com.lis.statistics.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@ApiModel(description = "报告统计VO")
public class ReportStatVO implements Serializable {

    @ApiModelProperty(value = "统计日期")
    private LocalDate statDate;

    @ApiModelProperty(value = "科室ID")
    private Long deptId;

    @ApiModelProperty(value = "科室名称")
    private String deptName;

    @ApiModelProperty(value = "检验项目ID")
    private Long testItemId;

    @ApiModelProperty(value = "项目编码")
    private String itemCode;

    @ApiModelProperty(value = "项目名称")
    private String itemName;

    @ApiModelProperty(value = "检验总数")
    private Integer totalCount;

    @ApiModelProperty(value = "正常数")
    private Integer normalCount;

    @ApiModelProperty(value = "异常数")
    private Integer abnormalCount;

    @ApiModelProperty(value = "危急值数")
    private Integer panicCount;

    @ApiModelProperty(value = "阳性数")
    private Integer positiveCount;

    @ApiModelProperty(value = "阴性数")
    private Integer negativeCount;

    @ApiModelProperty(value = "异常率(%)")
    private BigDecimal abnormalRate;

    @ApiModelProperty(value = "阳性率(%)")
    private BigDecimal positiveRate;
}
