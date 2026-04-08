package com.lis.hl7.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("processing_rule")
public class ProcessingRuleDO implements Serializable {

    private Integer delFlag;

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String ruleCode;

    private String ruleName;

    private Long interfaceId;

    private String messageType;

    private String triggerEvent;

    private String conditionExpr;

    private String actionType;

    private String actionConfig;

    private Integer priority;

    private Integer isEnabled;

    private String createBy;

    private LocalDateTime createTime;

    private String updateBy;

    private LocalDateTime updateTime;

    private String remark;
}
