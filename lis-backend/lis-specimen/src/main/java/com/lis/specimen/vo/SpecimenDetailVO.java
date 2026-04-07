package com.lis.specimen.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@ApiModel(description = "标本详情响应")
public class SpecimenDetailVO implements Serializable {

    @ApiModelProperty(value = "标本ID")
    private Long id;

    @ApiModelProperty(value = "标本编号")
    private String specimenNo;

    @ApiModelProperty(value = "条码号")
    private String barcode;

    @ApiModelProperty(value = "患者ID")
    private Long patientId;

    @ApiModelProperty(value = "患者姓名")
    private String patientName;

    @ApiModelProperty(value = "患者性别")
    private String patientGender;

    @ApiModelProperty(value = "患者年龄")
    private String patientAge;

    @ApiModelProperty(value = "患者身份证号")
    private String patientIdNo;

    @ApiModelProperty(value = "患者电话")
    private String patientPhone;

    @ApiModelProperty(value = "送检科室ID")
    private Long deptId;

    @ApiModelProperty(value = "送检科室名称")
    private String deptName;

    @ApiModelProperty(value = "病区ID")
    private Long wardId;

    @ApiModelProperty(value = "病区名称")
    private String wardName;

    @ApiModelProperty(value = "床号")
    private String bedNo;

    @ApiModelProperty(value = "送检医生ID")
    private Long doctorId;

    @ApiModelProperty(value = "送检医生姓名")
    private String doctorName;

    @ApiModelProperty(value = "标本类型ID")
    private Long specimenTypeId;

    @ApiModelProperty(value = "标本类型名称")
    private String specimenTypeName;

    @ApiModelProperty(value = "采集时间")
    private LocalDateTime collectTime;

    @ApiModelProperty(value = "采集人ID")
    private Long collectUserId;

    @ApiModelProperty(value = "采集人姓名")
    private String collectUserName;

    @ApiModelProperty(value = "接收时间")
    private LocalDateTime receiveTime;

    @ApiModelProperty(value = "接收人ID")
    private Long receiveUserId;

    @ApiModelProperty(value = "接收人姓名")
    private String receiveUserName;

    @ApiModelProperty(value = "标本状态")
    private String status;

    @ApiModelProperty(value = "标本状态名称")
    private String statusName;

    @ApiModelProperty(value = "拒收原因")
    private String rejectReason;

    @ApiModelProperty(value = "是否急诊")
    private Integer isStat;

    @ApiModelProperty(value = "是否打印条码")
    private Integer isPrint;

    @ApiModelProperty(value = "临床诊断")
    private String clinicalDiagnosis;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "检验项目列表")
    private List<SpecimenTestItemVO> testItems;

    @ApiModelProperty(value = "流转记录列表")
    private List<SpecimenTraceVO> traces;
}
