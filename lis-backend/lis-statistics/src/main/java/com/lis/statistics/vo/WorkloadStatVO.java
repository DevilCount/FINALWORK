package com.lis.statistics.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@ApiModel(description = "工作量统计VO")
public class WorkloadStatVO implements Serializable {

    @ApiModelProperty(value = "统计日期")
    private LocalDate statDate;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "用户姓名")
    private String userName;

    @ApiModelProperty(value = "科室ID")
    private Long deptId;

    @ApiModelProperty(value = "科室名称")
    private String deptName;

    @ApiModelProperty(value = "标本接收数")
    private Integer specimenReceiveCount;

    @ApiModelProperty(value = "检验数")
    private Integer testCount;

    @ApiModelProperty(value = "审核数")
    private Integer auditCount;

    @ApiModelProperty(value = "报告数")
    private Integer reportCount;

    @ApiModelProperty(value = "工作时长（小时）")
    private BigDecimal workHours;

    @ApiModelProperty(value = "总工作量")
    private Integer totalWorkload;
}
