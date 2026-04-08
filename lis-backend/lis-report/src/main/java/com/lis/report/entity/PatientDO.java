package com.lis.report.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("patient")
public class PatientDO implements Serializable {

    @TableId(type = IdType.AUTO)
    @TableLogic
    private Long id;

    private String patientNo;

    private String patientName;

    private String gender;

    private LocalDate birthDate;

    private String age;

    private String idType;

    private String idNo;

    private String phone;

    private String email;

    private String address;

    private String nation;

    private String marriage;

    private String occupation;

    private String workUnit;

    private String contactName;

    private String contactPhone;

    private String contactRelation;

    private String bloodType;

    private String allergyHistory;

    private String medicalHistory;

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
}
