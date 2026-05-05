package com.lis.specimen.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@ApiModel(description = "标本登记请求参数")
public class SpecimenRegisterDTO implements Serializable {

    @ApiModelProperty(value = "患者ID")
    private Long patientId;

    @NotBlank(message = "患者姓名不能为空")
    @ApiModelProperty(value = "患者姓名", required = true)
    private String patientName;

    @ApiModelProperty(value = "患者性别")
    private String patientGender;

    @ApiModelProperty(value = "患者年龄")
    private String patientAge;

    @ApiModelProperty(value = "患者身份证号")
    private String patientIdNo;

    @ApiModelProperty(value = "患者电话")
    private String patientPhone;

    @NotNull(message = "送检科室ID不能为空")
    @ApiModelProperty(value = "送检科室ID", required = true)
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

    @NotNull(message = "标本类型ID不能为空")
    @ApiModelProperty(value = "标本类型ID", required = true)
    private Long specimenTypeId;

    @ApiModelProperty(value = "标本类型名称")
    private String specimenTypeName;

    @ApiModelProperty(value = "采集时间")
    private LocalDateTime collectTime;

    @ApiModelProperty(value = "采集人ID")
    private Long collectUserId;

    @ApiModelProperty(value = "采集人姓名")
    private String collectUserName;

    @ApiModelProperty(value = "是否急诊（0否 1是）")
    private Integer isStat;

    @ApiModelProperty(value = "临床诊断")
    private String clinicalDiagnosis;

    @ApiModelProperty(value = "备注")
    private String remark;

    @NotNull(message = "检验项目不能为空")
    @ApiModelProperty(value = "检验项目ID列表", required = true)
    private List<Long> testItemIds;
}
