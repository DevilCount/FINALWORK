package com.lis.hl7.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Hl7ParsedMessageVO implements Serializable {

    private static final long serialVersionUID = 1L;

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

    private Hl7PatientVO patient;

    private Hl7VisitVO visit;

    private java.util.List<Hl7OrderVO> orders;

    private java.util.List<Hl7ObservationVO> observations;
}
