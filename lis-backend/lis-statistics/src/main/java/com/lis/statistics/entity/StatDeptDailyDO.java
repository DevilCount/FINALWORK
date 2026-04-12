package com.lis.statistics.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("stat_dept_daily")
public class StatDeptDailyDO implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private LocalDate statDate;

    private Long deptId;

    private String deptName;

    private Integer specimenCount;

    private Integer testItemCount;

    private Integer reportCount;

    private Integer panicCount;

    private BigDecimal tatAvg;

    private BigDecimal tatWithinRate;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    @TableField("del_flag")
    @TableLogic
    private Integer delFlag;
}
