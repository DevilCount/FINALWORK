package com.lis.hl7.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Hl7MessageVO implements Serializable {

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

    private LocalDateTime messageTime;

    private String patientId;

    private String patientName;

    private String visitNo;

    private String specimenNo;

    private String rawMessage;

    private String parsedMessage;

    private String transformedMessage;

    private String ackMessage;

    private String ackStatus;

    private String ackCode;

    private String ackMessageText;

    private String processStatus;

    private LocalDateTime processTime;

    private Long processDuration;

    private String errorCode;

    private String errorMsg;

    private Integer retryCount;

    private String createBy;

    private LocalDateTime createTime;

    private String updateBy;

    private LocalDateTime updateTime;

    private String remark;
}
