package com.lis.report.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("report_print_log")
public class ReportPrintLogDO implements Serializable {

    @TableId(type = IdType.AUTO)
    @TableLogic
    private Long id;

    private Long reportId;

    private String reportNo;

    private Long printUserId;

    private String printUserName;

    private LocalDateTime printTime;

    private Integer printCopies;

    private String printerName;

    private String printIp;

    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
