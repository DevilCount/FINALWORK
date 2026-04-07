package com.lis.auth.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel(description = "刷新Token请求参数")
public class RefreshTokenDTO implements Serializable {

    @NotBlank(message = "刷新令牌不能为空")
    @ApiModelProperty(value = "刷新令牌", required = true)
    private String refreshToken;
}
