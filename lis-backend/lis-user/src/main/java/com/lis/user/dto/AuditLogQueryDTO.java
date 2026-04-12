package com.lis.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "审计日志查询参数")
public class AuditLogQueryDTO implements Serializable {

    @ApiModelProperty(value = "页码")
    private Integer pageNum = 1;

    @ApiModelProperty(value = "每页大小")
    private Integer pageSize = 10;

    @ApiModelProperty(value = "审计类型")
    private String auditType;

    @ApiModelProperty(value = "操作")
    private String action;

    @ApiModelProperty(value = "目标类型")
    private String targetType;

    @ApiModelProperty(value = "审核人")
    private String auditUserName;

    @ApiModelProperty(value = "开始时间")
    private LocalDateTime beginTime;

    @ApiModelProperty(value = "结束时间")
    private LocalDateTime endTime;
}
