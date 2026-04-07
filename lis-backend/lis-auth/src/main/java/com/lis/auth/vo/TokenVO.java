package com.lis.auth.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "Token刷新响应信息")
public class TokenVO implements Serializable {

    @ApiModelProperty(value = "访问令牌")
    private String accessToken;

    @ApiModelProperty(value = "刷新令牌")
    private String refreshToken;

    @ApiModelProperty(value = "令牌类型")
    private String tokenType = "Bearer";

    @ApiModelProperty(value = "访问令牌过期时间(毫秒)")
    private Long expiresIn;
}
