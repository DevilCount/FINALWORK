package com.lis.statistics.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@ApiModel(description = "标本统计VO")
public class SpecimenStatVO implements Serializable {

    @ApiModelProperty(value = "统计日期")
    private LocalDate statDate;

    @ApiModelProperty(value = "科室ID")
    private Long deptId;

    @ApiModelProperty(value = "科室名称")
    private String deptName;

    @ApiModelProperty(value = "标本类型ID")
    private Long specimenTypeId;

    @ApiModelProperty(value = "标本类型名称")
    private String specimenTypeName;

    @ApiModelProperty(value = "标本总数")
    private Integer totalCount;

    @ApiModelProperty(value = "已接收数")
    private Integer receivedCount;

    @ApiModelProperty(value = "已完成数")
    private Integer completedCount;

    @ApiModelProperty(value = "拒收数")
    private Integer rejectedCount;

    @ApiModelProperty(value = "急诊数")
    private Integer statCount;

    @ApiModelProperty(value = "平均周转时间（分钟）")
    private BigDecimal tatAvg;

    @ApiModelProperty(value = "周转时间达标率(%)")
    private BigDecimal tatWithinRate;

    @ApiModelProperty(value = "完成率(%)")
    private BigDecimal completionRate;
}
