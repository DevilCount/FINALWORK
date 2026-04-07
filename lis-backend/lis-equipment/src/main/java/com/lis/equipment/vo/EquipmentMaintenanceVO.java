package com.lis.equipment.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "设备维护VO")
public class EquipmentMaintenanceVO implements Serializable {

    @ApiModelProperty(value = "维护ID")
    private Long id;

    @ApiModelProperty(value = "维护编号")
    private String maintenanceNo;

    @ApiModelProperty(value = "计划ID")
    private Long planId;

    @ApiModelProperty(value = "计划编号")
    private String planNo;

    @ApiModelProperty(value = "设备ID")
    private Long equipmentId;

    @ApiModelProperty(value = "设备编码")
    private String equipmentCode;

    @ApiModelProperty(value = "设备名称")
    private String equipmentName;

    @ApiModelProperty(value = "维护类型")
    private String maintenanceType;

    @ApiModelProperty(value = "维护类型描述")
    private String maintenanceTypeDesc;

    @ApiModelProperty(value = "维护日期")
    private LocalDate maintenanceDate;

    @ApiModelProperty(value = "开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "结束时间")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "维护内容")
    private String maintenanceContent;

    @ApiModelProperty(value = "维护结果")
    private String maintenanceResult;

    @ApiModelProperty(value = "维护后状态")
    private String maintenanceStatus;

    @ApiModelProperty(value = "维护人ID")
    private Long maintenanceUserId;

    @ApiModelProperty(value = "维护人姓名")
    private String maintenanceUserName;

    @ApiModelProperty(value = "维护费用")
    private BigDecimal cost;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "状态描述")
    private String statusDesc;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "备注")
    private String remark;
}
