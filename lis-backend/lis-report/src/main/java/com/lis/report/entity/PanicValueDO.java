package com.lis.report.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("panic_value")
public class PanicValueDO implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String panicNo;

    private Long reportId;

    private String reportNo;

    private Long specimenId;

    private String specimenNo;

    private Long patientId;

    private String patientName;

    private Long deptId;

    private String deptName;

    private String wardName;

    private String bedNo;

    private String itemCode;

    private String itemName;

    private String resultValue;

    private String unit;

    private BigDecimal panicLow;

    private BigDecimal panicHigh;

    private String panicType;

    private LocalDateTime findTime;

    private Long findUserId;

    private String findUserName;

    private LocalDateTime notifyTime;

    private Long notifyUserId;

    private String notifyUserName;

    private String notifyMethod;

    private String receiveUserName;

    private LocalDateTime receiveTime;

    private Integer handleStatus;

    private LocalDateTime handleTime;

    private Long handleUserId;

    private String handleUserName;

    private String handleResult;

    private LocalDateTime confirmTime;

    private Long confirmUserId;

    private String confirmUserName;

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
