package com.lis.ai.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@ApiModel(description = "诊断规则响应")
public class DiagnosisRuleVO implements Serializable {

    @ApiModelProperty(value = "规则ID")
    private Long id;

    @ApiModelProperty(value = "规则编码")
    private String ruleCode;

    @ApiModelProperty(value = "规则名称")
    private String ruleName;

    @ApiModelProperty(value = "规则类型：single-单项 combined-组合 panel-组合")
    private String ruleType;

    @ApiModelProperty(value = "规则分类")
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
    private BigDecimal confidenceThreshold;

    @ApiModelProperty(value = "优先级")
    private Integer priority;

    @ApiModelProperty(value = "是否启用：0-否 1-是")
    private Integer isEnabled;

    @ApiModelProperty(value = "状态：0-正常 1-停用")
    private Integer status;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "规则明细列表")
    private List<RuleItemVO> items;

    @Data
    @ApiModel(description = "规则明细")
    public static class RuleItemVO implements Serializable {

        @ApiModelProperty(value = "明细ID")
        private Long id;

        @ApiModelProperty(value = "检验项目ID")
        private Long testItemId;

        @ApiModelProperty(value = "项目编码")
        private String itemCode;

        @ApiModelProperty(value = "项目名称")
        private String itemName;

        @ApiModelProperty(value = "条件类型：range-范围 compare-比较 formula-公式")
        private String conditionType;

        @ApiModelProperty(value = "条件表达式")
        private String conditionExpr;

        @ApiModelProperty(value = "权重")
        private BigDecimal weight;

        @ApiModelProperty(value = "排序")
        private Integer sort;
    }
}
