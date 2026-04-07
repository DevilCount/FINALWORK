package com.lis.ai.service;

import com.lis.common.result.PageResult;
import com.lis.ai.dto.DiagnosisRuleDTO;
import com.lis.ai.vo.DiagnosisRuleVO;

import java.util.List;

public interface DiagnosisRuleService {

    Long createRule(DiagnosisRuleDTO dto);

    void updateRule(DiagnosisRuleDTO dto);

    void deleteRule(Long id);

    DiagnosisRuleVO getRule(Long id);

    DiagnosisRuleVO getRuleByCode(String ruleCode);

    List<DiagnosisRuleVO> listRulesByCategory(String category);

    List<DiagnosisRuleVO> listEnabledRules();

    PageResult<DiagnosisRuleVO> queryRules(String ruleName, String category, Integer isEnabled, Integer pageNum, Integer pageSize);

    void enableRule(Long id);

    void disableRule(Long id);
}
