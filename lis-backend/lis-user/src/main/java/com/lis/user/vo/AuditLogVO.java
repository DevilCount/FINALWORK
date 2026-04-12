package com.lis.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "审计日志响应")
public class AuditLogVO implements Serializable {

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "审计时间")
    private LocalDateTime auditTime;

    @ApiModelProperty(value = "审计类型")
    private String auditType;

    @ApiModelProperty(value = "目标类型")
    private String targetType;

    @ApiModelProperty(value = "目标ID")
    private Long targetId;

    @ApiModelProperty(value = "目标名称")
    private String targetName;

    @ApiModelProperty(value = "操作动作")
    private String action;

    @ApiModelProperty(value = "变更前值")
    private String oldValue;

    @ApiModelProperty(value = "变更后值")
    private String newValue;

    @ApiModelProperty(value = "变更摘要")
    private String diffSummary;

    @ApiModelProperty(value = "操作人ID")
    private Long operatorId;

    @ApiModelProperty(value = "操作人姓名")
    private String operatorName;

    @ApiModelProperty(value = "操作人IP")
    private String operatorIp;

    @ApiModelProperty(value = "操作结果")
    private String result;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
}
