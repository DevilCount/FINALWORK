package com.lis.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "错误日志查询参数")
public class ErrorLogQueryDTO implements Serializable {

    @ApiModelProperty(value = "页码")
    private Integer pageNum = 1;

    @ApiModelProperty(value = "每页大小")
    private Integer pageSize = 10;

    @ApiModelProperty(value = "类名")
    private String className;

    @ApiModelProperty(value = "方法名")
    private String methodName;

    @ApiModelProperty(value = "开始时间")
    private LocalDateTime beginTime;

    @ApiModelProperty(value = "结束时间")
    private LocalDateTime endTime;
}
