package com.lis.hl7.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Hl7VisitVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String visitNo;

    private String visitType;

    private String visitClass;

    private String department;

    private String ward;

    private String bedNo;

    private String attendingDoctor;

    private String admittingDoctor;

    private String admissionTime;

    private String dischargeTime;

    private String diagnosis;

    private String diagnosisCode;
}
