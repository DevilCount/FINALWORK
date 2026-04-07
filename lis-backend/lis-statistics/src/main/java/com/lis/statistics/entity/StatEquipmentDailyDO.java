package com.lis.statistics.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("stat_equipment_daily")
public class StatEquipmentDailyDO implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private LocalDate statDate;

    private Long equipmentId;

    private String equipmentCode;

    private String equipmentName;

    private Integer testCount;

    private Integer testItemCount;

    private Integer qcCount;

    private Integer qcPassCount;

    private Integer qcFailCount;

    private BigDecimal qcPassRate;

    private Integer faultCount;

    private Integer faultDuration;

    private BigDecimal uptimeRate;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
