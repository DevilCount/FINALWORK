package com.lis.specimen.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "标本统计查询请求参数")
public class SpecimenStatisticsDTO implements Serializable {

    @ApiModelProperty(value = "统计开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "统计结束时间")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "送检科室ID")
    private Long deptId;

    @ApiModelProperty(value = "标本类型ID")
    private Long specimenTypeId;

    @ApiModelProperty(value = "统计类型（day按天 week按周 month按月）")
    private String statisticsType;
}
