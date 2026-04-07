package com.lis.hl7.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Hl7PatientVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String patientId;

    private String patientName;

    private String idCardNo;

    private String gender;

    private String birthDate;

    private String phone;

    private String address;

    private String ethnicGroup;

    private String maritalStatus;

    private String bloodType;
}
