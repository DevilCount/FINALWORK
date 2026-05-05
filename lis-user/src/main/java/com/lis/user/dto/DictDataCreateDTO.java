package com.lis.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@ApiModel(description = "字典数据创建参数")
public class DictDataCreateDTO implements Serializable {

    @ApiModelProperty(value = "字典排序")
    private Integer dictSort;

    @NotBlank(message = "字典标签不能为空")
    @Size(max = 100, message = "字典标签长度不能超过100个字符")
    @ApiModelProperty(value = "字典标签", required = true)
    private String dictLabel;

    @NotBlank(message = "字典键值不能为空")
    @Size(max = 100, message = "字典键值长度不能超过100个字符")
    @ApiModelProperty(value = "字典键值", required = true)
    private String dictValue;

    @NotBlank(message = "字典类型不能为空")
    @Size(max = 100, message = "字典类型长度不能超过100个字符")
    @ApiModelProperty(value = "字典类型", required = true)
    private String dictType;

    @Size(max = 100, message = "样式属性长度不能超过100个字符")
    @ApiModelProperty(value = "样式属性")
    private String cssClass;

    @Size(max = 100, message = "表格回显样式长度不能超过100个字符")
    @ApiModelProperty(value = "表格回显样式")
    private String listClass;

    @ApiModelProperty(value = "是否默认")
    private Integer isDefault;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remark;
}
