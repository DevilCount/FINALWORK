package com.lis.specimen.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "标本列表响应")
public class SpecimenListVO implements Serializable {

    @ApiModelProperty(value = "标本ID")
    private Long id;

    @ApiModelProperty(value = "标本编号")
    private String specimenNo;

    @ApiModelProperty(value = "条码号")
    private String barcode;

    @ApiModelProperty(value = "患者姓名")
    private String patientName;

    @ApiModelProperty(value = "患者性别")
    private String patientGender;

    @ApiModelProperty(value = "患者年龄")
    private String patientAge;

    @ApiModelProperty(value = "送检科室名称")
    private String deptName;

    @ApiModelProperty(value = "病区名称")
    private String wardName;

    @ApiModelProperty(value = "床号")
    private String bedNo;

    @ApiModelProperty(value = "标本类型名称")
    private String specimenTypeName;

    @ApiModelProperty(value = "采集时间")
    private LocalDateTime collectTime;

    @ApiModelProperty(value = "采集人姓名")
    private String collectUserName;

    @ApiModelProperty(value = "接收时间")
    private LocalDateTime receiveTime;

    @ApiModelProperty(value = "接收人姓名")
    private String receiveUserName;

    @ApiModelProperty(value = "标本状态")
    private String status;

    @ApiModelProperty(value = "标本状态名称")
    private String statusName;

    @ApiModelProperty(value = "是否急诊")
    private Integer isStat;

    @ApiModelProperty(value = "检验项目数量")
    private Integer testItemCount;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
}
