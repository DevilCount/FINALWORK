package com.lis.report.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@ApiModel(description = "检验申请创建请求参数")
public class ReportApplyDTO implements Serializable {

    @ApiModelProperty(value = "标本ID")
    private Long specimenId;

    @ApiModelProperty(value = "标本编号")
    private String specimenNo;

    @ApiModelProperty(value = "报告类型（routine常规 stat急诊 review复查）")
    private String reportType;

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

    @ApiModelProperty(value = "科室ID")
    private Long deptId;

    @ApiModelProperty(value = "科室名称")
    private String deptName;

    @ApiModelProperty(value = "病区名称")
    private String wardName;

    @ApiModelProperty(value = "床号")
    private String bedNo;

    @ApiModelProperty(value = "送检医生")
    private String doctorName;

    @ApiModelProperty(value = "标本类型名称")
    private String specimenTypeName;

    @ApiModelProperty(value = "采集时间")
    private LocalDateTime collectTime;

    @ApiModelProperty(value = "是否急诊")
    private Integer isStat;

    @ApiModelProperty(value = "临床诊断")
    private String clinicalDiagnosis;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "检验项目列表")
    private List<ReportApplyItemDTO> testItems;
}
