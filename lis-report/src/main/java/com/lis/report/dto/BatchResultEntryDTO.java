package com.lis.report.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "批量结果录入请求参数")
public class BatchResultEntryDTO implements Serializable {

    @NotNull(message = "报告ID不能为空")
    @ApiModelProperty(value = "报告ID", required = true)
    private Long reportId;

    @NotNull(message = "结果列表不能为空")
    @ApiModelProperty(value = "结果列表", required = true)
    private List<ResultEntryDTO> results;
}
