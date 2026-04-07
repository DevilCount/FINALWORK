package com.lis.equipment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("equipment_calibration")
public class EquipmentCalibrationDO implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String calibrationNo;

    private Long equipmentId;

    private String equipmentCode;

    private String equipmentName;

    private String calibrationType;

    private LocalDate calibrationDate;

    private String calibrationOrg;

    private Long calibrationUserId;

    private String calibrationUserName;

    private String calibrationMethod;

    private String calibrationResult;

    private String calibrationCertificate;

    private LocalDate validStartDate;

    private LocalDate validEndDate;

    private String calibrationReport;

    private BigDecimal calibrationCost;

    private Integer status;

    private String createBy;

    private LocalDateTime createTime;

    private String updateBy;

    private LocalDateTime updateTime;

    private String remark;
}
