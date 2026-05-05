package com.lis.hl7.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Hl7ObservationVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String setId;

    private String observationId;

    private String observationName;

    private String observationValue;

    private String units;

    private String referenceRange;

    private String abnormalFlag;

    private String probability;

    private String natureOfAbnormalTest;

    private String observationResultStatus;

    private String dateTimeOfObservation;

    private String producerId;

    private String responsibleObserver;

    private String observationMethod;
}
