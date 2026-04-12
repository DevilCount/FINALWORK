package com.lis.report.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("patient_visit")
public class PatientVisitDO implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String visitNo;

    private Long patientId;

    private String patientName;

    private String visitType;

    private Long deptId;

    private String deptName;

    private Long wardId;

    private String wardName;

    private String bedNo;

    private Long doctorId;

    private String doctorName;

    private String diagnosis;

    private LocalDateTime visitTime;

    private LocalDateTime dischargeTime;

    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    private String remark;

    @TableField("del_flag")
    @TableLogic
    private Integer delFlag;
}
