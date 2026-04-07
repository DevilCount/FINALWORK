package com.lis.report.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("report_modify_log")
public class ReportModifyLogDO implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long reportId;

    private String reportNo;

    private String modifyType;

    private String modifyField;

    private String oldValue;

    private String newValue;

    private String modifyReason;

    private Long modifyUserId;

    private String modifyUserName;

    private LocalDateTime modifyTime;

    private Integer auditStatus;

    private Long auditUserId;

    private String auditUserName;

    private LocalDateTime auditTime;

    private String auditRemark;

    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
