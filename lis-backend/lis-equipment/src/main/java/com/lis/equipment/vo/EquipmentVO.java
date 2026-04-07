package com.lis.equipment.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "设备信息VO")
public class EquipmentVO implements Serializable {

    @ApiModelProperty(value = "设备ID")
    private Long id;

    @ApiModelProperty(value = "设备编码")
    private String equipmentCode;

    @ApiModelProperty(value = "设备名称")
    private String equipmentName;

    @ApiModelProperty(value = "设备英文名")
    private String equipmentNameEn;

    @ApiModelProperty(value = "设备类型ID")
    private Long equipmentTypeId;

    @ApiModelProperty(value = "设备类型名称")
    private String equipmentTypeName;

    @ApiModelProperty(value = "品牌")
    private String brand;

    @ApiModelProperty(value = "型号")
    private String model;

    @ApiModelProperty(value = "序列号")
    private String serialNo;

    @ApiModelProperty(value = "生产厂家")
    private String manufacturer;

    @ApiModelProperty(value = "供应商")
    private String supplier;

    @ApiModelProperty(value = "购置日期")
    private LocalDate purchaseDate;

    @ApiModelProperty(value = "安装日期")
    private LocalDate installDate;

    @ApiModelProperty(value = "保修到期日期")
    private LocalDate warrantyExpireDate;

    @ApiModelProperty(value = "使用年限（年）")
    private Integer useLife;

    @ApiModelProperty(value = "原值")
    private BigDecimal originalValue;

    @ApiModelProperty(value = "净值")
    private BigDecimal netValue;

    @ApiModelProperty(value = "存放位置")
    private String location;

    @ApiModelProperty(value = "所属实验室ID")
    private Long labId;

    @ApiModelProperty(value = "所属实验室名称")
    private String labName;

    @ApiModelProperty(value = "负责人ID")
    private Long responsibleUserId;

    @ApiModelProperty(value = "负责人姓名")
    private String responsibleUserName;

    @ApiModelProperty(value = "联系电话")
    private String contactPhone;

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

    @ApiModelProperty(value = "通信协议")
    private String communicationProtocol;

    @ApiModelProperty(value = "波特率")
    private Integer baudRate;

    @ApiModelProperty(value = "最后通信时间")
    private LocalDateTime lastCommTime;

    @ApiModelProperty(value = "最后校准日期")
    private LocalDate lastCalibrationDate;

    @ApiModelProperty(value = "下次校准日期")
    private LocalDate nextCalibrationDate;

    @ApiModelProperty(value = "最后维护日期")
    private LocalDate lastMaintenanceDate;

    @ApiModelProperty(value = "下次维护日期")
    private LocalDate nextMaintenanceDate;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "备注")
    private String remark;
}
