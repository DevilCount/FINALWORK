package com.lis.hl7.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class InterfaceConfigQueryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String interfaceCode;

    private String interfaceName;

    private String interfaceType;

    private String direction;

    private String protocol;

    private String status;

    private Integer isEnabled;

    private Integer pageNum = 1;

    private Integer pageSize = 10;
}
