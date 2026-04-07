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
@TableName("stat_specimen_daily")
public class StatSpecimenDailyDO implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private LocalDate statDate;

    private Long deptId;

    private String deptName;

    private Long specimenTypeId;

    private String specimenTypeName;

    private Integer totalCount;

    private Integer receivedCount;

    private Integer completedCount;

    private Integer rejectedCount;

    private Integer statCount;

    private BigDecimal tatAvg;

    private BigDecimal tatWithinRate;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
