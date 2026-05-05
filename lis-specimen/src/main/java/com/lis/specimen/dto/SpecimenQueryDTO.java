package com.lis.specimen.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "标本查询请求参数")
public class SpecimenQueryDTO implements Serializable {

    @ApiModelProperty(value = "标本编号")
    private String specimenNo;

    @ApiModelProperty(value = "标本条码")
    private String barcode;

    @ApiModelProperty(value = "患者姓名")
    private String patientName;

    @ApiModelProperty(value = "患者身份证号")
    private String patientIdNo;

    @ApiModelProperty(value = "送检科室ID")
    private Long deptId;

    @ApiModelProperty(value = "病区ID")
    private Long wardId;

    @ApiModelProperty(value = "标本类型ID")
    private Long specimenTypeId;

    @ApiModelProperty(value = "标本状态")
    private String status;

    @ApiModelProperty(value = "是否急诊")
    private Integer isStat;

    @ApiModelProperty(value = "采集开始时间")
    private LocalDateTime collectTimeStart;

    @ApiModelProperty(value = "采集结束时间")
    private LocalDateTime collectTimeEnd;

    @ApiModelProperty(value = "接收开始时间")
    private LocalDateTime receiveTimeStart;

    @ApiModelProperty(value = "接收结束时间")
    private LocalDateTime receiveTimeEnd;

    @ApiModelProperty(value = "页码", example = "1")
    private Integer pageNum = 1;

    @ApiModelProperty(value = "每页大小", example = "10")
    private Integer pageSize = 10;
}
