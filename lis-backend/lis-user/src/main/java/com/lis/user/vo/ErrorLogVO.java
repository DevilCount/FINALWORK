package com.lis.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "错误日志响应")
public class ErrorLogVO implements Serializable {

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "错误时间")
    private LocalDateTime errorTime;

    @ApiModelProperty(value = "错误级别")
    private String errorLevel;

    @ApiModelProperty(value = "错误类名")
    private String errorClass;

    @ApiModelProperty(value = "错误方法名")
    private String errorMethod;

    @ApiModelProperty(value = "错误行号")
    private Integer errorLine;

    @ApiModelProperty(value = "错误消息")
    private String errorMessage;

    @ApiModelProperty(value = "错误堆栈")
    private String errorStack;

    @ApiModelProperty(value = "请求URL")
    private String requestUrl;

    @ApiModelProperty(value = "请求方式")
    private String requestMethod;

    @ApiModelProperty(value = "请求参数")
    private String requestParams;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "用户IP")
    private String userIp;

    @ApiModelProperty(value = "服务节点")
    private String serverNode;

    @ApiModelProperty(value = "线程名")
    private String threadName;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
}
