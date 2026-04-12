package com.lis.auth.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "验证码响应")
public class CaptchaVO implements Serializable {

    @ApiModelProperty(value = "验证码唯一标识")
    private String uuid;

    @ApiModelProperty(value = "验证码图片(Base64)")
    private String captchaImage;

    public CaptchaVO() {}

    public CaptchaVO(String uuid, String captchaImage) {
        this.uuid = uuid;
        this.captchaImage = captchaImage;
    }
}
