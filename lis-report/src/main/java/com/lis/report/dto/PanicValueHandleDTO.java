package com.lis.report.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel(description = "危急值处理请求参数")
public class PanicValueHandleDTO implements Serializable {

    @NotNull(message = "危急值ID不能为空")
    @ApiModelProperty(value = "危急值ID", required = true)
    private Long panicValueId;

    @ApiModelProperty(value = "通知方式")
    private String notifyMethod;

    @ApiModelProperty(value = "接收人姓名")
    private String receiveUserName;

    @ApiModelProperty(value = "处理结果")
    private String handleResult;

    @ApiModelProperty(value = "备注")
    private String remark;
}
