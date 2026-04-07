package com.lis.hl7.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class Hl7MessageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String messageId;

    private Long interfaceId;

    private String interfaceCode;

    private String direction;

    private String messageType;

    private String triggerEvent;

    private String messageControlId;

    private String processingId;

    private String versionId;

    private String sendingApp;

    private String sendingFacility;

    private String receivingApp;

    private String receivingFacility;

    private String messageTime;

    private String patientId;

    private String patientName;

    private String visitNo;

    private String specimenNo;

    @NotBlank(message = "原始消息不能为空")
    private String rawMessage;

    private String parsedMessage;

    private String transformedMessage;

    private String ackMessage;

    private String ackStatus;

    private String ackCode;

    private String ackMessageText;

    private String processStatus;

    private String remark;
}
