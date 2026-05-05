package com.lis.hl7.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Hl7OrderVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String orderNo;

    private String orderControl;

    private String orderStatus;

    private String placerOrderNo;

    private String fillerOrderNo;

    private String orderCategory;

    private String orderItemCode;

    private String orderItemName;

    private String specimenType;

    private String specimenCode;

    private String specimenNo;

    private String collectionTime;

    private String receivingTime;

    private String priority;

    private String orderingDoctor;

    private String orderingDept;

    private String clinicalInfo;

    private String dangerCode;

    private String relevantClinicalInfo;
}
