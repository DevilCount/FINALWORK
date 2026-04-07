package com.lis.ai.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(description = "检验项目分析结果")
public class TestItemAnalysisVO implements Serializable {

    @ApiModelProperty(value = "检验项目ID")
    private Long itemId;

    @ApiModelProperty(value = "项目编码")
    private String itemCode;

    @ApiModelProperty(value = "项目名称")
    private String itemName;

    @ApiModelProperty(value = "结果值")
    private String resultValue;

    @ApiModelProperty(value = "单位")
    private String unit;

    @ApiModelProperty(value = "参考值下限")
    private BigDecimal referenceLow;

    @ApiModelProperty(value = "参考值上限")
    private BigDecimal referenceHigh;

    @ApiModelProperty(value = "是否异常：0-正常 1-异常")
    private Integer isAbnormal;

    @ApiModelProperty(value = "异常程度")
    private BigDecimal abnormalDegree;

    @ApiModelProperty(value = "异常方向：high-偏高 low-偏低 normal-正常")
    private String abnormalDirection;

    @ApiModelProperty(value = "特征重要性")
    private BigDecimal featureImportance;

    @ApiModelProperty(value = "贡献分数")
    private BigDecimal contributionScore;

    @ApiModelProperty(value = "临床意义")
    private String clinicalSignificance;
}
