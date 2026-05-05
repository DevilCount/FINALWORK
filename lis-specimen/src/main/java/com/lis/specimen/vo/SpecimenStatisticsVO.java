package com.lis.specimen.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "标本统计响应")
public class SpecimenStatisticsVO implements Serializable {

    @ApiModelProperty(value = "标本总数")
    private Long totalCount;

    @ApiModelProperty(value = "待接收数量")
    private Long pendingCount;

    @ApiModelProperty(value = "已接收数量")
    private Long receivedCount;

    @ApiModelProperty(value = "检验中数量")
    private Long testingCount;

    @ApiModelProperty(value = "已完成数量")
    private Long completedCount;

    @ApiModelProperty(value = "已拒收数量")
    private Long rejectedCount;

    @ApiModelProperty(value = "急诊标本数量")
    private Long statCount;

    @ApiModelProperty(value = "危急值数量")
    private Long panicCount;

    @ApiModelProperty(value = "异常结果数量")
    private Long abnormalCount;

    @ApiModelProperty(value = "当日采集数量")
    private Long todayCollectCount;

    @ApiModelProperty(value = "当日接收数量")
    private Long todayReceiveCount;

    @ApiModelProperty(value = "当日完成数量")
    private Long todayCompleteCount;
}
