package com.lis.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "登录日志响应")
public class LoginLogVO implements Serializable {

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "IP")
    private String ip;

    @ApiModelProperty(value = "登录地点")
    private String loginLocation;

    @ApiModelProperty(value = "浏览器")
    private String browser;

    @ApiModelProperty(value = "操作系统")
    private String os;

    @ApiModelProperty(value = "登录状态")
    private Integer status;

    @ApiModelProperty(value = "提示消息")
    private String msg;

    @ApiModelProperty(value = "登录时间")
    private LocalDateTime loginTime;
}
