package com.lis.statistics.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@ApiModel(description = "统计查询基础DTO")
public class StatQueryDTO implements Serializable {

    @ApiModelProperty(value = "开始日期", required = true)
    private LocalDate startDate;

    @ApiModelProperty(value = "结束日期", required = true)
    private LocalDate endDate;

    @ApiModelProperty(value = "科室ID")
    private Long deptId;

    @ApiModelProperty(value = "统计类型：day-按日, week-按周, month-按月, year-按年")
    private String statType;

    @ApiModelProperty(value = "页码")
    private Integer pageNum = 1;

    @ApiModelProperty(value = "每页数量")
    private Integer pageSize = 10;
}
