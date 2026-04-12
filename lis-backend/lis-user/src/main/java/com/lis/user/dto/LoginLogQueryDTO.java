package com.lis.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "登录日志查询参数")
public class LoginLogQueryDTO implements Serializable {

    @ApiModelProperty(value = "页码")
    private Integer pageNum = 1;

    @ApiModelProperty(value = "每页大小")
    private Integer pageSize = 10;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "登录状态")
    private Integer status;

    @ApiModelProperty(value = "开始时间")
    private LocalDateTime beginTime;

    @ApiModelProperty(value = "结束时间")
    private LocalDateTime endTime;
}
