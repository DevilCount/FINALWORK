package com.lis.specimen.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("specimen_type")
public class SpecimenTypeDO implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String typeCode;

    private String typeName;

    private String typeDesc;

    private String colorCode;

    private String containerType;

    private String storageCondition;

    private Integer validityPeriod;

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
