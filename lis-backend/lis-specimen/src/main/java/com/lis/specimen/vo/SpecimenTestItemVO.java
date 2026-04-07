package com.lis.specimen.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "标本检验项目响应")
public class SpecimenTestItemVO implements Serializable {

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "检验项目ID")
    private Long testItemId;

    @ApiModelProperty(value = "项目编码")
    private String itemCode;

    @ApiModelProperty(value = "项目名称")
    private String itemName;

    @ApiModelProperty(value = "结果值")
    private String resultValue;

    @ApiModelProperty(value = "结果文本")
    private String resultText;

    @ApiModelProperty(value = "结果标志")
    private String resultFlag;

    @ApiModelProperty(value = "单位")
    private String unit;

    @ApiModelProperty(value = "参考值下限")
    private BigDecimal referenceLow;

    @ApiModelProperty(value = "参考值上限")
    private BigDecimal referenceHigh;

    @ApiModelProperty(value = "参考值文本")
    private String referenceText;

    @ApiModelProperty(value = "是否危急值")
    private Integer isPanic;

    @ApiModelProperty(value = "是否异常")
    private Integer isAbnormal;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "状态名称")
    private String statusName;

    @ApiModelProperty(value = "检验设备名称")
    private String equipmentName;

    @ApiModelProperty(value = "检验时间")
    private LocalDateTime testTime;

    @ApiModelProperty(value = "检验人姓名")
    private String testUserName;

    @ApiModelProperty(value = "审核时间")
    private LocalDateTime auditTime;

    @ApiModelProperty(value = "审核人姓名")
    private String auditUserName;
}
