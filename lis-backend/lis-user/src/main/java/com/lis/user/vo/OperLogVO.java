package com.lis.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "操作日志响应")
public class OperLogVO implements Serializable {

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "操作标题")
    private String title;

    @ApiModelProperty(value = "业务类型")
    private Integer businessType;

    @ApiModelProperty(value = "方法")
    private String method;

    @ApiModelProperty(value = "请求方式")
    private String requestMethod;

    @ApiModelProperty(value = "操作人")
    private String operName;

    @ApiModelProperty(value = "操作URL")
    private String operUrl;

    @ApiModelProperty(value = "操作IP")
    private String operIp;

    @ApiModelProperty(value = "请求参数")
    private String operParam;

    @ApiModelProperty(value = "返回结果")
    private String operResult;

    @ApiModelProperty(value = "耗时(ms)")
    private Integer costTime;

    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @ApiModelProperty(value = "操作地点")
    private String operLocation;

    @ApiModelProperty(value = "操作时间")
    private LocalDateTime operTime;
}
