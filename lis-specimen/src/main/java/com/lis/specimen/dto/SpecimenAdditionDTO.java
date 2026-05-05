package com.lis.specimen.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "标本附加检验项目请求参数")
public class SpecimenAdditionDTO implements Serializable {

    @NotNull(message = "标本ID不能为空")
    @ApiModelProperty(value = "标本ID", required = true)
    private Long specimenId;

    @NotBlank(message = "标本编号不能为空")
    @ApiModelProperty(value = "标本编号", required = true)
    private String specimenNo;

    @NotNull(message = "检验项目不能为空")
    @ApiModelProperty(value = "附加检验项目ID列表", required = true)
    private List<Long> testItemIds;

    @ApiModelProperty(value = "附加原因")
    private String addReason;

    @ApiModelProperty(value = "附加人ID")
    private Long addUserId;

    @ApiModelProperty(value = "附加人姓名")
    private String addUserName;

    @ApiModelProperty(value = "备注")
    private String remark;
}
