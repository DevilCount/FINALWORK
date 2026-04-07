package com.lis.specimen.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "标本签收请求参数")
public class SpecimenReceiveDTO implements Serializable {

    @NotBlank(message = "标本条码不能为空")
    @ApiModelProperty(value = "标本条码", required = true)
    private String barcode;

    @ApiModelProperty(value = "接收人ID")
    private Long receiveUserId;

    @ApiModelProperty(value = "接收人姓名")
    private String receiveUserName;

    @ApiModelProperty(value = "是否拒收（0否 1是）")
    private Integer isReject;

    @ApiModelProperty(value = "拒收原因")
    private String rejectReason;

    @ApiModelProperty(value = "拒收类型")
    private String rejectType;

    @ApiModelProperty(value = "备注")
    private String remark;
}
