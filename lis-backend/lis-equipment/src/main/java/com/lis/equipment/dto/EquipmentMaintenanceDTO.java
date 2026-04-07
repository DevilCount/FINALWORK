package com.lis.equipment.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "设备维护DTO")
public class EquipmentMaintenanceDTO implements Serializable {

    @ApiModelProperty(value = "维护ID")
    private Long id;

    @NotBlank(message = "维护编号不能为空")
    @ApiModelProperty(value = "维护编号", required = true)
    private String maintenanceNo;

    @ApiModelProperty(value = "计划ID")
    private Long planId;

    @ApiModelProperty(value = "计划编号")
    private String planNo;

    @NotNull(message = "设备ID不能为空")
    @ApiModelProperty(value = "设备ID", required = true)
    private Long equipmentId;

    @ApiModelProperty(value = "设备编码")
    private String equipmentCode;

    @ApiModelProperty(value = "设备名称")
    private String equipmentName;

    @ApiModelProperty(value = "维护类型")
    private String maintenanceType;

    @NotNull(message = "维护日期不能为空")
    @ApiModelProperty(value = "维护日期", required = true)
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

    @ApiModelProperty(value = "备注")
    private String remark;
}
