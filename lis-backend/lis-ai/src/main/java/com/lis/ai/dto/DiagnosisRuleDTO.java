package com.lis.ai.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@ApiModel(description = "诊断规则请求参数")
public class DiagnosisRuleDTO implements Serializable {

    @ApiModelProperty(value = "规则ID（更新时必填）")
    private Long id;

    @NotBlank(message = "规则编码不能为空")
    @ApiModelProperty(value = "规则编码", required = true)
    private String ruleCode;

    @NotBlank(message = "规则名称不能为空")
    @ApiModelProperty(value = "规则名称", required = true)
    private String ruleName;

    @ApiModelProperty(value = "规则类型：single-单项 combined-组合 panel-组合")
    private String ruleType = "single";

    @NotBlank(message = "规则分类不能为空")
    @ApiModelProperty(value = "规则分类：anemia-贫血 infection-感染 liver-肝脏 kidney-肾脏等", required = true)
    private String category;

    @ApiModelProperty(value = "关联模型ID")
    private Long modelId;

    @ApiModelProperty(value = "关联检验项目ID（多个逗号分隔）")
    private String testItemIds;

    @ApiModelProperty(value = "规则条件（JSON格式）")
    private String ruleCondition;

    @ApiModelProperty(value = "规则表达式")
    private String ruleExpr;

    @ApiModelProperty(value = "诊断模板")
    private String diagnosisTemplate;

    @ApiModelProperty(value = "建议模板")
    private String suggestionTemplate;

    @ApiModelProperty(value = "置信度阈值")
    private BigDecimal confidenceThreshold = new BigDecimal("0.8");

    @ApiModelProperty(value = "优先级")
    private Integer priority = 0;

    @ApiModelProperty(value = "是否启用：0-否 1-是")
    private Integer isEnabled = 1;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "规则明细列表")
    private List<RuleItemDTO> items;

    @Data
    @ApiModel(description = "规则明细")
    public static class RuleItemDTO implements Serializable {

        @ApiModelProperty(value = "检验项目ID")
        private Long testItemId;

        @ApiModelProperty(value = "项目编码")
        private String itemCode;

        @ApiModelProperty(value = "项目名称")
        private String itemName;

        @ApiModelProperty(value = "条件类型：range-范围 compare-比较 formula-公式")
        private String conditionType = "range";

        @ApiModelProperty(value = "条件表达式")
        private String conditionExpr;

        @ApiModelProperty(value = "权重")
        private BigDecimal weight = BigDecimal.ONE;

        @ApiModelProperty(value = "排序")
        private Integer sort = 0;
    }
}
