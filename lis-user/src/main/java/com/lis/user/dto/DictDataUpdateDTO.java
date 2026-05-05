package com.lis.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@ApiModel(description = "字典数据更新参数")
public class DictDataUpdateDTO implements Serializable {

    @NotNull(message = "字典数据ID不能为空")
    @ApiModelProperty(value = "字典数据ID", required = true)
    private Long id;

    @ApiModelProperty(value = "字典排序")
    private Integer dictSort;

    @Size(max = 100, message = "字典标签长度不能超过100个字符")
    @ApiModelProperty(value = "字典标签")
    private String dictLabel;

    @Size(max = 100, message = "字典键值长度不能超过100个字符")
    @ApiModelProperty(value = "字典键值")
    private String dictValue;

    @Size(max = 100, message = "字典类型长度不能超过100个字符")
    @ApiModelProperty(value = "字典类型")
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
