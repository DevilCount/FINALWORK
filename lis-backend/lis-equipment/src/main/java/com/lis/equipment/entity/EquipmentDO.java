package com.lis.equipment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("equipment")
public class EquipmentDO implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String equipmentCode;

    private String equipmentName;

    private String equipmentNameEn;

    private Long equipmentTypeId;

    private String equipmentTypeName;

    private String brand;

    private String model;

    private String serialNo;

    private String manufacturer;

    private String supplier;

    private LocalDate purchaseDate;

    private LocalDate installDate;

    private LocalDate warrantyExpireDate;

    private Integer useLife;

    private BigDecimal originalValue;

    private BigDecimal netValue;

    private String location;

    private Long labId;

    private String labName;

    private Long responsibleUserId;

    private String responsibleUserName;

    private String contactPhone;

    private String status;

    private Integer isOnline;

    private String ipAddress;

    private Integer port;

    private String communicationProtocol;

    private Integer baudRate;

    private LocalDateTime lastCommTime;

    private LocalDate lastCalibrationDate;

    private LocalDate nextCalibrationDate;

    private LocalDate lastMaintenanceDate;

    private LocalDate nextMaintenanceDate;

    private String createBy;

    private LocalDateTime createTime;

    private String updateBy;

    private LocalDateTime updateTime;

    private String remark;
}
