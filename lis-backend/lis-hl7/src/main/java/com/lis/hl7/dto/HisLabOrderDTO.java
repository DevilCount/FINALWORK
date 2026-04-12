package com.lis.hl7.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "HIS检验申请请求参数")
public class HisLabOrderDTO implements Serializable {

    @ApiModelProperty(value = "患者ID")
    private String patientId;

    @ApiModelProperty(value = "患者姓名")
    private String patientName;

    @ApiModelProperty(value = "患者性别")
    private String gender;

    @ApiModelProperty(value = "患者出生日期")
    private String birthDate;

    @ApiModelProperty(value = "身份证号")
    private String idCardNo;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "就诊类型")
    private String visitClass;

    @ApiModelProperty(value = "科室")
    private String department;

    @ApiModelProperty(value = "病区")
    private String ward;

    @ApiModelProperty(value = "床号")
    private String bedNo;

    @ApiModelProperty(value = "就诊号")
    private String visitNo;

    @ApiModelProperty(value = "主治医生")
    private String attendingDoctor;

    @ApiModelProperty(value = "临床诊断")
    private String clinicalInfo;

    @ApiModelProperty(value = "接口编码")
    private String interfaceCode;

    @ApiModelProperty(value = "检验项目列表")
    private List<HisLabOrderItemDTO> orderItems;
}
