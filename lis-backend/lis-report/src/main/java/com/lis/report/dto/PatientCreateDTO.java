package com.lis.report.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@ApiModel(description = "患者创建请求参数")
public class PatientCreateDTO implements Serializable {

    @NotBlank(message = "患者姓名不能为空")
    @ApiModelProperty(value = "患者姓名", required = true)
    private String patientName;

    @ApiModelProperty(value = "性别（0男 1女 2未知）")
    private String gender;

    @ApiModelProperty(value = "出生日期")
    private LocalDate birthDate;

    @ApiModelProperty(value = "年龄")
    private String age;

    @ApiModelProperty(value = "证件类型（01身份证 02护照 03军官证 04其他）")
    private String idType;

    @ApiModelProperty(value = "证件号码")
    private String idNo;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    @ApiModelProperty(value = "电子邮箱")
    private String email;

    @ApiModelProperty(value = "家庭住址")
    private String address;

    @ApiModelProperty(value = "民族")
    private String nation;

    @ApiModelProperty(value = "婚姻状况")
    private String marriage;

    @ApiModelProperty(value = "职业")
    private String occupation;

    @ApiModelProperty(value = "工作单位")
    private String workUnit;

    @ApiModelProperty(value = "联系人姓名")
    private String contactName;

    @ApiModelProperty(value = "联系人电话")
    private String contactPhone;

    @ApiModelProperty(value = "联系人关系")
    private String contactRelation;

    @ApiModelProperty(value = "血型")
    private String bloodType;

    @ApiModelProperty(value = "过敏史")
    private String allergyHistory;

    @ApiModelProperty(value = "既往病史")
    private String medicalHistory;

    @ApiModelProperty(value = "备注")
    private String remark;
}
