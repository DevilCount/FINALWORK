package com.lis.specimen.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(description = "检验项目响应")
public class TestItemVO implements Serializable {

    @ApiModelProperty(value = "检验项目ID")
    private Long id;

    @ApiModelProperty(value = "项目编码")
    private String itemCode;

    @ApiModelProperty(value = "项目名称")
    private String itemName;

    @ApiModelProperty(value = "项目英文名")
    private String itemNameEn;

    @ApiModelProperty(value = "项目简称")
    private String itemShort;

    @ApiModelProperty(value = "项目分类ID")
    private Long categoryId;

    @ApiModelProperty(value = "默认标本类型ID")
    private Long specimenTypeId;

    @ApiModelProperty(value = "检验方法")
    private String method;

    @ApiModelProperty(value = "计量单位")
    private String unit;

    @ApiModelProperty(value = "参考值下限")
    private BigDecimal referenceLow;

    @ApiModelProperty(value = "参考值上限")
    private BigDecimal referenceHigh;

    @ApiModelProperty(value = "参考值文本")
    private String referenceText;

    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    @ApiModelProperty(value = "周转时间（分钟）")
    private Integer tat;

    @ApiModelProperty(value = "是否急诊")
    private Integer isStat;

    @ApiModelProperty(value = "状态")
    private Integer status;
}
