package com.lis.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "操作日志查询参数")
public class OperLogQueryDTO implements Serializable {

    @ApiModelProperty(value = "页码")
    private Integer pageNum = 1;

    @ApiModelProperty(value = "每页大小")
    private Integer pageSize = 10;

    @ApiModelProperty(value = "操作标题")
    private String title;

    @ApiModelProperty(value = "业务类型")
    private Integer businessType;

    @ApiModelProperty(value = "操作人")
    private String operName;

    @ApiModelProperty(value = "开始时间")
    private LocalDateTime beginTime;

    @ApiModelProperty(value = "结束时间")
    private LocalDateTime endTime;
}
