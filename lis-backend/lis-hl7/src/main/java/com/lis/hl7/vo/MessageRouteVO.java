package com.lis.hl7.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class MessageRouteVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String routeCode;

    private String routeName;

    private Long sourceInterfaceId;

    private Long targetInterfaceId;

    private String messageType;

    private String triggerEvent;

    private String conditionExpr;

    private Long transformRuleId;

    private Integer isEnabled;

    private Integer priority;

    private String createBy;

    private LocalDateTime createTime;

    private String updateBy;

    private LocalDateTime updateTime;

    private String remark;
}
