package com.lis.specimen.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("specimen_trace")
public class SpecimenTraceDO implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long specimenId;

    private String specimenNo;

    private String action;

    private String actionName;

    private String fromStatus;

    private String toStatus;

    private String fromLocation;

    private String toLocation;

    private Long operatorId;

    private String operatorName;

    private LocalDateTime operateTime;

    private String remark;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
