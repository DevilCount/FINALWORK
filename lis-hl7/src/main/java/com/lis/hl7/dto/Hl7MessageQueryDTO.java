package com.lis.hl7.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class Hl7MessageQueryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String interfaceCode;

    private String direction;

    private String messageType;

    private String triggerEvent;

    private String messageControlId;

    private String patientId;

    private String patientName;

    private String visitNo;

    private String specimenNo;

    private String processStatus;

    private String ackStatus;

    private String startTime;

    private String endTime;

    private Integer pageNum = 1;

    private Integer pageSize = 10;
}
