package com.lis.specimen.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("specimen_type")
public class SpecimenTypeDO implements Serializable {

    @TableId(type = IdType.AUTO)
    @TableLogic
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

    private String updateBy;

    private String remark;

    private String createTime;

    private String updateTime;

    private Integer delFlag;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
