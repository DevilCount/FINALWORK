package com.lis.hl7.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "HIS检验申请项目")
public class HisLabOrderItemDTO implements Serializable {

    @ApiModelProperty(value = "医嘱控制码(NW新医嘱)")
    private String orderControl;

    @ApiModelProperty(value = "医嘱号")
    private String placerOrderNo;

    @ApiModelProperty(value = "检验项目编码")
    private String orderItemCode;

    @ApiModelProperty(value = "检验项目名称")
    private String orderItemName;

    @ApiModelProperty(value = "标本类型")
    private String specimenType;

    @ApiModelProperty(value = "优先级")
    private String priority;

    @ApiModelProperty(value = "采集时间")
    private String collectionTime;

    @ApiModelProperty(value = "临床信息")
    private String clinicalInfo;

    @ApiModelProperty(value = "开单医生")
    private String orderingDoctor;
}
