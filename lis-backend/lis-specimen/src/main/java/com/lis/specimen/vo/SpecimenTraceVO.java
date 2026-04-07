package com.lis.specimen.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "标本流转记录响应")
public class SpecimenTraceVO implements Serializable {

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "标本ID")
    private Long specimenId;

    @ApiModelProperty(value = "标本编号")
    private String specimenNo;

    @ApiModelProperty(value = "操作动作")
    private String action;

    @ApiModelProperty(value = "操作名称")
    private String actionName;

    @ApiModelProperty(value = "原状态")
    private String fromStatus;

    @ApiModelProperty(value = "原状态名称")
    private String fromStatusName;

    @ApiModelProperty(value = "目标状态")
    private String toStatus;

    @ApiModelProperty(value = "目标状态名称")
    private String toStatusName;

    @ApiModelProperty(value = "原位置")
    private String fromLocation;

    @ApiModelProperty(value = "目标位置")
    private String toLocation;

    @ApiModelProperty(value = "操作人ID")
    private Long operatorId;

    @ApiModelProperty(value = "操作人姓名")
    private String operatorName;

    @ApiModelProperty(value = "操作时间")
    private LocalDateTime operateTime;

    @ApiModelProperty(value = "备注")
    private String remark;
}
