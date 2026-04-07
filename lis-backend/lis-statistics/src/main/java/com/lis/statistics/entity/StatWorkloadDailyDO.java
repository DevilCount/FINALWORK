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
@TableName("stat_workload_daily")
public class StatWorkloadDailyDO implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private LocalDate statDate;

    private Long userId;

    private String userName;

    private Long deptId;

    private String deptName;

    private Integer specimenReceiveCount;

    private Integer testCount;

    private Integer auditCount;

    private Integer reportCount;

    private BigDecimal workHours;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
