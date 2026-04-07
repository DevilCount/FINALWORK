package com.lis.ai.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "诊断请求参数")
public class DiagnosisRequestDTO implements Serializable {

    @ApiModelProperty(value = "报告ID")
    private Long reportId;

    @ApiModelProperty(value = "报告编号")
    private String reportNo;

    @ApiModelProperty(value = "标本ID")
    private Long specimenId;

    @ApiModelProperty(value = "标本编号")
    private String specimenNo;

    @ApiModelProperty(value = "患者ID")
    private Long patientId;

    @ApiModelProperty(value = "患者姓名")
    private String patientName;

    @ApiModelProperty(value = "患者性别")
    private String patientGender;

    @ApiModelProperty(value = "患者年龄")
    private String patientAge;

    @NotNull(message = "诊断类型不能为空")
    @ApiModelProperty(value = "诊断类型：blood_routine-血常规 urine_routine-尿常规 liver_function-肝功能", required = true)
    private String diagnosisType;

    @NotEmpty(message = "检验项目数据不能为空")
    @ApiModelProperty(value = "检验项目数据列表", required = true)
    private List<TestItemData> testData;

    @ApiModelProperty(value = "是否保存诊断记录")
    private Boolean saveRecord = true;

    @Data
    @ApiModel(description = "检验项目数据")
    public static class TestItemData implements Serializable {

        @ApiModelProperty(value = "检验项目ID")
        private Long itemId;

        @ApiModelProperty(value = "项目编码")
        private String itemCode;

        @ApiModelProperty(value = "项目名称")
        private String itemName;

        @NotNull(message = "结果值不能为空")
        @ApiModelProperty(value = "结果值", required = true)
        private String resultValue;

        @ApiModelProperty(value = "单位")
        private String unit;

        @ApiModelProperty(value = "参考值下限")
        private Double referenceLow;

        @ApiModelProperty(value = "参考值上限")
        private Double referenceHigh;

        @ApiModelProperty(value = "是否异常：0-正常 1-异常")
        private Integer isAbnormal;
    }
}
