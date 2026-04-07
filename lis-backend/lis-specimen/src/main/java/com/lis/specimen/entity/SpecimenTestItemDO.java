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
@TableName("specimen_test_item")
public class SpecimenTestItemDO implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long specimenId;

    private String specimenNo;

    private Long testItemId;

    private String itemCode;

    private String itemName;

    private String resultValue;

    private String resultText;

    private String resultFlag;

    private String unit;

    private BigDecimal referenceLow;

    private BigDecimal referenceHigh;

    private String referenceText;

    private Integer isPanic;

    private Integer isAbnormal;

    private String status;

    private Long equipmentId;

    private String equipmentName;

    private LocalDateTime testTime;

    private Long testUserId;

    private String testUserName;

    private LocalDateTime auditTime;

    private Long auditUserId;

    private String auditUserName;

    private String remark;

    private String createBy;

    private LocalDateTime createTime;

    private String updateBy;

    private LocalDateTime updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
