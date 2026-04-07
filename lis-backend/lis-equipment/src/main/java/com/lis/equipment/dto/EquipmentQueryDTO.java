package com.lis.equipment.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "设备查询DTO")
public class EquipmentQueryDTO implements Serializable {

    @ApiModelProperty(value = "设备编码")
    private String equipmentCode;

    @ApiModelProperty(value = "设备名称")
    private String equipmentName;

    @ApiModelProperty(value = "设备类型ID")
    private Long equipmentTypeId;

    @ApiModelProperty(value = "品牌")
    private String brand;

    @ApiModelProperty(value = "型号")
    private String model;

    @ApiModelProperty(value = "设备状态")
    private String status;

    @ApiModelProperty(value = "是否在线")
    private Integer isOnline;

    @ApiModelProperty(value = "所属实验室ID")
    private Long labId;

    @ApiModelProperty(value = "负责人ID")
    private Long responsibleUserId;

    @ApiModelProperty(value = "页码", example = "1")
    private Integer pageNum = 1;

    @ApiModelProperty(value = "每页大小", example = "10")
    private Integer pageSize = 10;
}
