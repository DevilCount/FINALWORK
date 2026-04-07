package com.lis.equipment.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "设备状态监控VO")
public class EquipmentStatusVO implements Serializable {

    @ApiModelProperty(value = "设备ID")
    private Long id;

    @ApiModelProperty(value = "设备编码")
    private String equipmentCode;

    @ApiModelProperty(value = "设备名称")
    private String equipmentName;

    @ApiModelProperty(value = "设备状态")
    private String status;

    @ApiModelProperty(value = "设备状态描述")
    private String statusDesc;

    @ApiModelProperty(value = "是否在线")
    private Integer isOnline;

    @ApiModelProperty(value = "IP地址")
    private String ipAddress;

    @ApiModelProperty(value = "端口号")
    private Integer port;

    @ApiModelProperty(value = "最后通信时间")
    private String lastCommTime;

    @ApiModelProperty(value = "通信状态")
    private String commStatus;

    @ApiModelProperty(value = "通信状态描述")
    private String commStatusDesc;
}
