package com.lis.report.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("report")
public class ReportDO implements Serializable {

    @TableId(type = IdType.AUTO)
    @TableLogic
    private Long id;

    private String reportNo;

    private String reportType;

    private Long specimenId;

    private String specimenNo;

    private Long patientId;

    private String patientName;

    private String patientGender;

    private String patientAge;

    private String patientIdNo;

    private Long deptId;

    private String deptName;

    private String wardName;

    private String bedNo;

    private String doctorName;

    private String specimenTypeName;

    private LocalDateTime collectTime;

    private LocalDateTime receiveTime;

    private LocalDateTime testTime;

    private LocalDateTime auditTime;

    private LocalDateTime reportTime;

    private Long testUserId;

    private String testUserName;

    private Long auditUserId;

    private String auditUserName;

    private Long reportUserId;

    private String reportUserName;

    private String status;

    private Integer isStat;

    private Integer isPanic;

    private Integer isAbnormal;

    private Integer printCount;

    private LocalDateTime lastPrintTime;

    private String clinicalDiagnosis;

    private String reportConclusion;

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
