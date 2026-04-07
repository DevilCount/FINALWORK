package com.lis.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@ApiModel(description = "字典类型更新参数")
public class DictTypeUpdateDTO implements Serializable {

    @NotNull(message = "字典ID不能为空")
    @ApiModelProperty(value = "字典ID", required = true)
    private Long id;

    @Size(max = 100, message = "字典名称长度不能超过100个字符")
    @ApiModelProperty(value = "字典名称")
    private String dictName;

    @Size(max = 100, message = "字典类型长度不能超过100个字符")
    @ApiModelProperty(value = "字典类型")
    private String dictType;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remark;
}
