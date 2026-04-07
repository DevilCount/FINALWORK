package com.lis.ai.service;

import com.lis.ai.dto.DiagnosisRequestDTO;
import com.lis.ai.entity.AiDiagnosisRuleDO;
import com.lis.ai.vo.DiagnosisResultVO;

import java.util.List;
import java.util.Map;

public interface DiagnosisRuleEngine {

    List<AiDiagnosisRuleDO> findMatchingRules(String diagnosisType, List<String> itemCodes);

    boolean evaluateRule(AiDiagnosisRuleDO rule, Map<String, DiagnosisRequestDTO.TestItemData> testDataMap);

    DiagnosisResultVO.MatchedRuleVO buildMatchedRule(AiDiagnosisRuleDO rule, double confidence);

    double calculateConfidence(AiDiagnosisRuleDO rule, Map<String, DiagnosisRequestDTO.TestItemData> testDataMap);

    String evaluateExpression(String expression, Map<String, Object> variables);
}
