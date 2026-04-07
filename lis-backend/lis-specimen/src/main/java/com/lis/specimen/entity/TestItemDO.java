package com.lis.specimen.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("test_item")
public class TestItemDO implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String itemCode;

    private String itemName;

    private String itemNameEn;

    private String itemShort;

    private Long categoryId;

    private Long specimenTypeId;

    private String method;

    private String unit;

    private BigDecimal referenceLow;

    private BigDecimal referenceHigh;

    private String referenceText;

    private BigDecimal panicLow;

    private BigDecimal panicHigh;

    private BigDecimal price;

    private Integer tat;

    private Integer isPrint;

    private Integer isStat;

    private Integer status;

    private Integer sort;

    private String createBy;

    private LocalDateTime createTime;

    private String updateBy;

    private LocalDateTime updateTime;

    private String remark;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
