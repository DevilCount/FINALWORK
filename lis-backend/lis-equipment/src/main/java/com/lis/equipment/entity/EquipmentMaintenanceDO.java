package com.lis.equipment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("equipment_maintenance")
public class EquipmentMaintenanceDO implements Serializable {

    @TableId(type = IdType.AUTO)
    @TableLogic
    private Long id;

    private String maintenanceNo;

    private Long planId;

    private String planNo;

    private Long equipmentId;

    private String equipmentCode;

    private String equipmentName;

    private String maintenanceType;

    private LocalDate maintenanceDate;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String maintenanceContent;

    private String maintenanceResult;

    private String maintenanceStatus;

    private Long maintenanceUserId;

    private String maintenanceUserName;

    private BigDecimal cost;

    private Integer status;

    private String createBy;

    private LocalDateTime createTime;

    private String updateBy;

    private LocalDateTime updateTime;

    private String remark;
}
