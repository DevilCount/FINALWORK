package com.lis.specimen.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("specimen")
public class SpecimenDO implements Serializable {

    @TableId(type = IdType.AUTO)
    @TableLogic
    private Long id;

    private String specimenNo;

    private String barcode;

    private Long patientId;

    private String patientName;

    private String patientGender;

    private String patientAge;

    private String patientIdNo;

    private String patientPhone;

    private Long deptId;

    private String deptName;

    private Long wardId;

    private String wardName;

    private String bedNo;

    private Long doctorId;

    private String doctorName;

    private Long specimenTypeId;

    private String specimenTypeName;

    private LocalDateTime collectTime;

    private Long collectUserId;

    private String collectUserName;

    private LocalDateTime receiveTime;

    private Long receiveUserId;

    private String receiveUserName;

    private String status;

    private String rejectReason;

    private Integer isStat;

    private Integer isPrint;

    private String clinicalDiagnosis;

    private String remark;

    private String createBy;

    private LocalDateTime createTime;

    private String updateBy;

    private LocalDateTime updateTime;

    private Integer delFlag;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
