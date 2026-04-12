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
@TableName("stat_test_item_daily")
public class StatTestItemDailyDO implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private LocalDate statDate;

    private Long deptId;

    private String deptName;

    private Long testItemId;

    private String itemCode;

    private String itemName;

    private Integer totalCount;

    private Integer normalCount;

    private Integer abnormalCount;

    private Integer panicCount;

    private Integer positiveCount;

    private Integer negativeCount;

    private BigDecimal abnormalRate;

    private BigDecimal positiveRate;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    @TableField("del_flag")
    @TableLogic
    private Integer delFlag;
}
