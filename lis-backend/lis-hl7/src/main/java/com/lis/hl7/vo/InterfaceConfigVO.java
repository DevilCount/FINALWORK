package com.lis.hl7.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class InterfaceConfigVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String interfaceCode;

    private String interfaceName;

    private String interfaceType;

    private String direction;

    private String protocol;

    private String host;

    private Integer port;

    private String charset;

    private String encoding;

    private Integer connectionTimeout;

    private Integer readTimeout;

    private Integer retryCount;

    private Integer retryInterval;

    private String ackMode;

    private String messageType;

    private String triggerEvent;

    private String processingRuleId;

    private String transformRuleId;

    private String validationRuleId;

    private Integer isEnabled;

    private Integer isAutoStart;

    private String status;

    private LocalDateTime lastStartTime;

    private LocalDateTime lastStopTime;

    private Long totalReceived;

    private Long totalSent;

    private Long totalError;

    private LocalDateTime lastErrorTime;

    private String lastErrorMsg;

    private String createBy;

    private LocalDateTime createTime;

    private String updateBy;

    private LocalDateTime updateTime;

    private String remark;
}
