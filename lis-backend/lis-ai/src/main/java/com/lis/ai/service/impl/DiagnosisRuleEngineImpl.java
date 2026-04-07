package com.lis.ai.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.lis.ai.dto.DiagnosisRequestDTO;
import com.lis.ai.entity.AiDiagnosisRuleDO;
import com.lis.ai.entity.AiDiagnosisRuleItemDO;
import com.lis.ai.enums.DiagnosisTypeEnum;
import com.lis.ai.enums.RuleCategoryEnum;
import com.lis.ai.mapper.AiDiagnosisRuleItemMapper;
import com.lis.ai.mapper.AiDiagnosisRuleMapper;
import com.lis.ai.service.DiagnosisRuleEngine;
import com.lis.ai.vo.DiagnosisResultVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class DiagnosisRuleEngineImpl implements DiagnosisRuleEngine {

    private final AiDiagnosisRuleMapper ruleMapper;
    private final AiDiagnosisRuleItemMapper ruleItemMapper;

    @Override
    public List<AiDiagnosisRuleDO> findMatchingRules(String diagnosisType, List<String> itemCodes) {
        DiagnosisTypeEnum typeEnum = DiagnosisTypeEnum.getByCode(diagnosisType);
        if (typeEnum == null) {
            return Collections.emptyList();
        }

        String category = mapDiagnosisTypeToCategory(diagnosisType);

        List<AiDiagnosisRuleDO> rules = ruleMapper.selectList(
                new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<AiDiagnosisRuleDO>()
                        .eq(AiDiagnosisRuleDO::getIsEnabled, 1)
                        .eq(AiDiagnosisRuleDO::getStatus, 0)
                        .eq(category != null, AiDiagnosisRuleDO::getCategory, category)
                        .orderByDesc(AiDiagnosisRuleDO::getPriority)
        );

        return rules.stream()
                .filter(rule -> hasMatchingItems(rule, itemCodes))
                .collect(Collectors.toList());
    }

    private String mapDiagnosisTypeToCategory(String diagnosisType) {
        return switch (diagnosisType) {
            case "blood_routine" -> null;
            case "urine_routine" -> null;
            case "liver_function" -> "liver";
            case "kidney_function" -> "kidney";
            case "blood_lipid" -> "cardiovascular";
            case "blood_sugar" -> "metabolism";
            case "thyroid" -> "endocrine";
            case "tumor_marker" -> "tumor";
            default -> null;
        };
    }

    private boolean hasMatchingItems(AiDiagnosisRuleDO rule, List<String> itemCodes) {
        if (rule.getTestItemIds() == null || rule.getTestItemIds().isEmpty()) {
            return true;
        }
        List<String> ruleItemIds = Arrays.asList(rule.getTestItemIds().split(","));
        return itemCodes.stream().anyMatch(ruleItemIds::contains);
    }

    @Override
    public boolean evaluateRule(AiDiagnosisRuleDO rule, Map<String, DiagnosisRequestDTO.TestItemData> testDataMap) {
        if (rule.getRuleCondition() == null || rule.getRuleCondition().isEmpty()) {
            return evaluateRuleItems(rule, testDataMap);
        }

        try {
            JSONObject condition = JSON.parseObject(rule.getRuleCondition());
            String logic = condition.getString("logic");
            JSONArray conditions = condition.getJSONArray("conditions");

            if (conditions == null || conditions.isEmpty()) {
                return evaluateRuleItems(rule, testDataMap);
            }

            List<Boolean> results = new ArrayList<>();
            for (int i = 0; i < conditions.size(); i++) {
                JSONObject cond = conditions.getJSONObject(i);
                boolean result = evaluateSingleCondition(cond, testDataMap);
                results.add(result);
            }

            if ("AND".equalsIgnoreCase(logic)) {
                return results.stream().allMatch(r -> r);
            } else if ("OR".equalsIgnoreCase(logic)) {
                return results.stream().anyMatch(r -> r);
            }
            return !results.isEmpty() && results.get(0);
        } catch (Exception e) {
            log.error("规则条件解析失败: ruleCode={}, error={}", rule.getRuleCode(), e.getMessage());
            return evaluateRuleItems(rule, testDataMap);
        }
    }

    private boolean evaluateRuleItems(AiDiagnosisRuleDO rule, Map<String, DiagnosisRequestDTO.TestItemData> testDataMap) {
        List<AiDiagnosisRuleItemDO> ruleItems = ruleItemMapper.selectList(
                new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<AiDiagnosisRuleItemDO>()
                        .eq(AiDiagnosisRuleItemDO::getRuleId, rule.getId())
                        .orderByAsc(AiDiagnosisRuleItemDO::getSort)
        );

        if (ruleItems.isEmpty()) {
            return false;
        }

        int matchCount = 0;
        for (AiDiagnosisRuleItemDO item : ruleItems) {
            DiagnosisRequestDTO.TestItemData data = testDataMap.get(item.getItemCode());
            if (data != null && evaluateItemCondition(item, data)) {
                matchCount++;
            }
        }

        return matchCount > 0;
    }

    private boolean evaluateSingleCondition(JSONObject condition, Map<String, DiagnosisRequestDTO.TestItemData> testDataMap) {
        String itemCode = condition.getString("itemCode");
        String operator = condition.getString("operator");
        BigDecimal value = condition.getBigDecimal("value");

        DiagnosisRequestDTO.TestItemData data = testDataMap.get(itemCode);
        if (data == null || data.getResultValue() == null) {
            return false;
        }

        try {
            BigDecimal resultValue = new BigDecimal(data.getResultValue());
            return compareValues(resultValue, operator, value);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean evaluateItemCondition(AiDiagnosisRuleItemDO item, DiagnosisRequestDTO.TestItemData data) {
        if (data.getResultValue() == null) {
            return false;
        }

        try {
            BigDecimal resultValue = new BigDecimal(data.getResultValue());
            String conditionType = item.getConditionType();
            String conditionExpr = item.getConditionExpr();

            if ("range".equals(conditionType)) {
                return evaluateRangeCondition(conditionExpr, resultValue);
            } else if ("compare".equals(conditionType)) {
                return evaluateCompareCondition(conditionExpr, resultValue);
            } else if ("formula".equals(conditionType)) {
                return evaluateFormulaCondition(conditionExpr, resultValue, data);
            }

            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean evaluateRangeCondition(String conditionExpr, BigDecimal value) {
        if (conditionExpr == null || conditionExpr.isEmpty()) {
            return false;
        }
        try {
            JSONObject range = JSON.parseObject(conditionExpr);
            BigDecimal low = range.getBigDecimal("low");
            BigDecimal high = range.getBigDecimal("high");

            boolean aboveLow = low == null || value.compareTo(low) >= 0;
            boolean belowHigh = high == null || value.compareTo(high) <= 0;

            return aboveLow && belowHigh;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean evaluateCompareCondition(String conditionExpr, BigDecimal value) {
        if (conditionExpr == null || conditionExpr.isEmpty()) {
            return false;
        }
        try {
            JSONObject compare = JSON.parseObject(conditionExpr);
            String operator = compare.getString("operator");
            BigDecimal targetValue = compare.getBigDecimal("value");

            return compareValues(value, operator, targetValue);
        } catch (Exception e) {
            return false;
        }
    }

    private boolean evaluateFormulaCondition(String conditionExpr, BigDecimal value, DiagnosisRequestDTO.TestItemData data) {
        if (conditionExpr == null || conditionExpr.isEmpty()) {
            return false;
        }
        try {
            JSONObject formula = JSON.parseObject(conditionExpr);
            String operator = formula.getString("operator");
            BigDecimal threshold = formula.getBigDecimal("threshold");

            if (data.getReferenceLow() != null && data.getReferenceHigh() != null) {
                BigDecimal refLow = new BigDecimal(data.getReferenceLow().toString());
                BigDecimal refHigh = new BigDecimal(data.getReferenceHigh().toString());
                BigDecimal deviation = calculateDeviation(value, refLow, refHigh);

                return compareValues(deviation, operator, threshold);
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    private BigDecimal calculateDeviation(BigDecimal value, BigDecimal refLow, BigDecimal refHigh) {
        BigDecimal refMid = refLow.add(refHigh).divide(new BigDecimal("2"), 4, RoundingMode.HALF_UP);
        BigDecimal refRange = refHigh.subtract(refLow);

        if (value.compareTo(refHigh) > 0) {
            return value.subtract(refHigh).divide(refRange, 4, RoundingMode.HALF_UP);
        } else if (value.compareTo(refLow) < 0) {
            return refLow.subtract(value).divide(refRange, 4, RoundingMode.HALF_UP);
        }
        return BigDecimal.ZERO;
    }

    private boolean compareValues(BigDecimal value, String operator, BigDecimal target) {
        if (operator == null || target == null) {
            return false;
        }

        int comparison = value.compareTo(target);

        return switch (operator.toLowerCase()) {
            case ">", "gt" -> comparison > 0;
            case ">=", "gte" -> comparison >= 0;
            case "<", "lt" -> comparison < 0;
            case "<=", "lte" -> comparison <= 0;
            case "==", "eq" -> comparison == 0;
            case "!=", "neq" -> comparison != 0;
            default -> false;
        };
    }

    @Override
    public DiagnosisResultVO.MatchedRuleVO buildMatchedRule(AiDiagnosisRuleDO rule, double confidence) {
        DiagnosisResultVO.MatchedRuleVO matchedRule = new DiagnosisResultVO.MatchedRuleVO();
        matchedRule.setRuleId(rule.getId());
        matchedRule.setRuleCode(rule.getRuleCode());
        matchedRule.setRuleName(rule.getRuleName());
        matchedRule.setCategory(rule.getCategory());
        matchedRule.setMatchConfidence(BigDecimal.valueOf(confidence).setScale(4, RoundingMode.HALF_UP));
        return matchedRule;
    }

    @Override
    public double calculateConfidence(AiDiagnosisRuleDO rule, Map<String, DiagnosisRequestDTO.TestItemData> testDataMap) {
        List<AiDiagnosisRuleItemDO> ruleItems = ruleItemMapper.selectList(
                new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<AiDiagnosisRuleItemDO>()
                        .eq(AiDiagnosisRuleItemDO::getRuleId, rule.getId())
        );

        if (ruleItems.isEmpty()) {
            return 0.5;
        }

        double totalWeight = 0;
        double matchedWeight = 0;

        for (AiDiagnosisRuleItemDO item : ruleItems) {
            BigDecimal weight = item.getWeight() != null ? item.getWeight() : BigDecimal.ONE;
            totalWeight += weight.doubleValue();

            DiagnosisRequestDTO.TestItemData data = testDataMap.get(item.getItemCode());
            if (data != null && evaluateItemCondition(item, data)) {
                matchedWeight += weight.doubleValue();
            }
        }

        if (totalWeight == 0) {
            return 0.5;
        }

        return matchedWeight / totalWeight;
    }

    @Override
    public String evaluateExpression(String expression, Map<String, Object> variables) {
        if (expression == null || expression.isEmpty()) {
            return "";
        }

        String result = expression;
        for (Map.Entry<String, Object> entry : variables.entrySet()) {
            String placeholder = "${" + entry.getKey() + "}";
            if (result.contains(placeholder)) {
                result = result.replace(placeholder, String.valueOf(entry.getValue()));
            }
        }
        return result;
    }
}
