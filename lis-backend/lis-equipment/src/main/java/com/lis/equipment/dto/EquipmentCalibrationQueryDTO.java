package com.lis.equipment.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "设备校准查询DTO")
public class EquipmentCalibrationQueryDTO implements Serializable {

    @ApiModelProperty(value = "校准编号")
    private String calibrationNo;

    @ApiModelProperty(value = "设备ID")
    private Long equipmentId;

    @ApiModelProperty(value = "设备编码")
    private String equipmentCode;

    @ApiModelProperty(value = "设备名称")
    private String equipmentName;

    @ApiModelProperty(value = "校准类型")
    private String calibrationType;

    @ApiModelProperty(value = "校准结果")
    private String calibrationResult;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "页码", example = "1")
    private Integer pageNum = 1;

    @ApiModelProperty(value = "每页大小", example = "10")
    private Integer pageSize = 10;
}
