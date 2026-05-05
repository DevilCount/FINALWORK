package com.lis.hl7.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class MessageRouteDTO implements Serializable {

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

    private String remark;
}
