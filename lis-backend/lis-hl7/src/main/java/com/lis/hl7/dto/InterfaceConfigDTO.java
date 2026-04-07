package com.lis.hl7.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class InterfaceConfigDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotBlank(message = "接口编码不能为空")
    private String interfaceCode;

    @NotBlank(message = "接口名称不能为空")
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

    private String remark;
}
