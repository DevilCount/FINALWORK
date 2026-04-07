package com.lis.specimen.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel(description = "标本入库请求参数")
public class SpecimenStorageDTO implements Serializable {

    @NotBlank(message = "标本条码不能为空")
    @ApiModelProperty(value = "标本条码", required = true)
    private String barcode;

    @NotBlank(message = "存储位置不能为空")
    @ApiModelProperty(value = "存储位置", required = true)
    private String storageLocation;

    @ApiModelProperty(value = "操作人ID")
    private Long operatorId;

    @ApiModelProperty(value = "操作人姓名")
    private String operatorName;

    @ApiModelProperty(value = "备注")
    private String remark;
}
