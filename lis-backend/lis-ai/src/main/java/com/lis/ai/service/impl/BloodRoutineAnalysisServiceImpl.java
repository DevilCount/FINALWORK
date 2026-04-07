package com.lis.ai.service.impl;

import com.alibaba.fastjson2.JSON;
import com.lis.ai.config.DiagnosisConfig;
import com.lis.ai.dto.DiagnosisRequestDTO;
import com.lis.ai.entity.AiDiagnosisRuleDO;
import com.lis.ai.enums.AbnormalDirectionEnum;
import com.lis.ai.enums.DiagnosisTypeEnum;
import com.lis.ai.enums.RiskLevelEnum;
import com.lis.ai.service.BloodRoutineAnalysisService;
import com.lis.ai.service.DiagnosisRuleEngine;
import com.lis.ai.util.DiagnosisNoGenerator;
import com.lis.ai.vo.DiagnosisResultVO;
import com.lis.ai.vo.TestItemAnalysisVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BloodRoutineAnalysisServiceImpl implements BloodRoutineAnalysisService {

    private final DiagnosisRuleEngine ruleEngine;
    private final DiagnosisConfig diagnosisConfig;

    private static final Map<String, BloodRoutineItemConfig> ITEM_CONFIGS = new HashMap<>();

    static {
        ITEM_CONFIGS.put("WBC", new BloodRoutineItemConfig("WBC", "白细胞计数", 4.0, 10.0, "10^9/L", 2.0, 20.0));
        ITEM_CONFIGS.put("RBC", new BloodRoutineItemConfig("RBC", "红细胞计数", 3.5, 5.5, "10^12/L", 2.0, 7.0));
        ITEM_CONFIGS.put("HGB", new BloodRoutineItemConfig("HGB", "血红蛋白", 110.0, 160.0, "g/L", 60.0, 200.0));
        ITEM_CONFIGS.put("HCT", new BloodRoutineItemConfig("HCT", "红细胞压积", 35.0, 50.0, "%", 20.0, 60.0));
        ITEM_CONFIGS.put("MCV", new BloodRoutineItemConfig("MCV", "平均红细胞体积", 80.0, 100.0, "fL", 60.0, 120.0));
        ITEM_CONFIGS.put("MCH", new BloodRoutineItemConfig("MCH", "平均红细胞血红蛋白量", 27.0, 34.0, "pg", 20.0, 45.0));
        ITEM_CONFIGS.put("MCHC", new BloodRoutineItemConfig("MCHC", "平均红细胞血红蛋白浓度", 320.0, 360.0, "g/L", 250.0, 400.0));
        ITEM_CONFIGS.put("PLT", new BloodRoutineItemConfig("PLT", "血小板计数", 100.0, 300.0, "10^9/L", 20.0, 500.0));
        ITEM_CONFIGS.put("NEUT%", new BloodRoutineItemConfig("NEUT%", "中性粒细胞百分比", 50.0, 70.0, "%", 20.0, 90.0));
        ITEM_CONFIGS.put("LYMPH%", new BloodRoutineItemConfig("LYMPH%", "淋巴细胞百分比", 20.0, 40.0, "%", 5.0, 60.0));
        ITEM_CONFIGS.put("MONO%", new BloodRoutineItemConfig("MONO%", "单核细胞百分比", 3.0, 8.0, "%", 0.0, 15.0));
        ITEM_CONFIGS.put("EO%", new BloodRoutineItemConfig("EO%", "嗜酸性粒细胞百分比", 0.5, 5.0, "%", 0.0, 15.0));
        ITEM_CONFIGS.put("BASO%", new BloodRoutineItemConfig("BASO%", "嗜碱性粒细胞百分比", 0.0, 1.0, "%", 0.0, 5.0));
        ITEM_CONFIGS.put("NEUT#", new BloodRoutineItemConfig("NEUT#", "中性粒细胞计数", 2.0, 7.0, "10^9/L", 0.5, 15.0));
        ITEM_CONFIGS.put("LYMPH#", new BloodRoutineItemConfig("LYMPH#", "淋巴细胞计数", 0.8, 4.0, "10^9/L", 0.1, 10.0));
        ITEM_CONFIGS.put("RDW-CV", new BloodRoutineItemConfig("RDW-CV", "红细胞分布宽度CV", 11.5, 14.5, "%", 10.0, 20.0));
        ITEM_CONFIGS.put("PDW", new BloodRoutineItemConfig("PDW", "血小板分布宽度", 9.0, 17.0, "fL", 5.0, 25.0));
        ITEM_CONFIGS.put("MPV", new BloodRoutineItemConfig("MPV", "平均血小板体积", 7.0, 11.0, "fL", 5.0, 15.0));
    }

    @Override
    public DiagnosisResultVO analyze(DiagnosisRequestDTO request) {
        long startTime = System.currentTimeMillis();
        DiagnosisResultVO result = new DiagnosisResultVO();
        result.setDiagnosisNo(DiagnosisNoGenerator.generate("BR"));
        result.setDiagnosisType(DiagnosisTypeEnum.BLOOD_ROUTINE.getCode());
        result.setDiagnosisTime(LocalDateTime.now());

        List<TestItemAnalysisVO> analysisResults = analyzeTestItems(request.getTestData());
        result.setAbnormalItems(buildAbnormalItems(analysisResults));

        boolean hasPanic = checkPanicValues(request.getTestData());
        result.setIsPanic(hasPanic ? 1 : 0);

        String riskLevel = determineRiskLevel(analysisResults);
        result.setRiskLevel(riskLevel);
        result.setIsAbnormal(hasAbnormal(analysisResults) ? 1 : 0);

        Map<String, DiagnosisRequestDTO.TestItemData> testDataMap = request.getTestData().stream()
                .collect(Collectors.toMap(
                        d -> d.getItemCode() != null ? d.getItemCode() : d.getItemName(),
                        d -> d,
                        (a, b) -> a
                ));

        List<String> itemCodes = request.getTestData().stream()
                .map(d -> d.getItemCode() != null ? d.getItemCode() : d.getItemName())
                .collect(Collectors.toList());

        List<AiDiagnosisRuleDO> matchingRules = ruleEngine.findMatchingRules(
                DiagnosisTypeEnum.BLOOD_ROUTINE.getCode(), itemCodes);

        List<DiagnosisResultVO.MatchedRuleVO> matchedRules = new ArrayList<>();
        String primaryDiagnosis = "";
        String primarySuggestion = "";
        BigDecimal maxConfidence = BigDecimal.ZERO;

        for (AiDiagnosisRuleDO rule : matchingRules) {
            if (ruleEngine.evaluateRule(rule, testDataMap)) {
                double confidence = ruleEngine.calculateConfidence(rule, testDataMap);
                if (confidence >= diagnosisConfig.getConfidenceThreshold()) {
                    matchedRules.add(ruleEngine.buildMatchedRule(rule, confidence));

                    if (confidence > maxConfidence.doubleValue()) {
                        maxConfidence = BigDecimal.valueOf(confidence).setScale(4, RoundingMode.HALF_UP);
                        primaryDiagnosis = rule.getDiagnosisTemplate() != null ? rule.getDiagnosisTemplate() : rule.getRuleName();
                        primarySuggestion = rule.getSuggestionTemplate() != null ? rule.getSuggestionTemplate() : "";
                    }
                }
            }
        }

        result.setMatchedRules(matchedRules);
        result.setConfidence(maxConfidence);
        result.setProbability(maxConfidence);

        if (primaryDiagnosis.isEmpty()) {
            primaryDiagnosis = generateDefaultDiagnosis(analysisResults);
            primarySuggestion = generateDefaultSuggestion(analysisResults);
        }

        result.setDiagnosisResult(primaryDiagnosis);
        result.setDiagnosisName(extractDiagnosisName(primaryDiagnosis));
        result.setSuggestion(primarySuggestion);

        long endTime = System.currentTimeMillis();
        result.setDiagnosisDuration(endTime - startTime);

        return result;
    }

    @Override
    public List<TestItemAnalysisVO> analyzeTestItems(List<DiagnosisRequestDTO.TestItemData> testData) {
        List<TestItemAnalysisVO> results = new ArrayList<>();

        for (DiagnosisRequestDTO.TestItemData data : testData) {
            String itemCode = data.getItemCode() != null ? data.getItemCode() : data.getItemName();
            BloodRoutineItemConfig config = ITEM_CONFIGS.get(itemCode);

            TestItemAnalysisVO analysis = new TestItemAnalysisVO();
            analysis.setItemId(data.getItemId());
            analysis.setItemCode(itemCode);
            analysis.setItemName(data.getItemName() != null ? data.getItemName() :
                    (config != null ? config.getName() : itemCode));
            analysis.setResultValue(data.getResultValue());
            analysis.setUnit(data.getUnit() != null ? data.getUnit() :
                    (config != null ? config.getUnit() : ""));

            if (config != null) {
                analysis.setReferenceLow(BigDecimal.valueOf(config.getRefLow()));
                analysis.setReferenceHigh(BigDecimal.valueOf(config.getRefHigh()));
            } else if (data.getReferenceLow() != null && data.getReferenceHigh() != null) {
                analysis.setReferenceLow(BigDecimal.valueOf(data.getReferenceLow()));
                analysis.setReferenceHigh(BigDecimal.valueOf(data.getReferenceHigh()));
            }

            analysis.setIsAbnormal(data.getIsAbnormal() != null ? data.getIsAbnormal() : 0);

            try {
                BigDecimal value = new BigDecimal(data.getResultValue());
                BigDecimal refLow = analysis.getReferenceLow();
                BigDecimal refHigh = analysis.getReferenceHigh();

                if (refLow != null && refHigh != null) {
                    AbnormalDirectionEnum direction = AbnormalDirectionEnum.calculate(
                            value.doubleValue(), refLow.doubleValue(), refHigh.doubleValue());
                    analysis.setAbnormalDirection(direction.getCode());
                    analysis.setIsAbnormal(direction == AbnormalDirectionEnum.NORMAL ? 0 : 1);

                    if (analysis.getIsAbnormal() == 1) {
                        BigDecimal deviation = calculateDeviation(value, refLow, refHigh);
                        analysis.setAbnormalDegree(deviation);
                        analysis.setFeatureImportance(calculateFeatureImportance(itemCode, deviation));
                    }
                }
            } catch (NumberFormatException e) {
                log.warn("无法解析结果值: itemCode={}, value={}", itemCode, data.getResultValue());
            }

            analysis.setClinicalSignificance(getClinicalSignificance(itemCode, analysis.getAbnormalDirection()));
            results.add(analysis);
        }

        return results;
    }

    @Override
    public String determineRiskLevel(List<TestItemAnalysisVO> analysisResults) {
        int highCount = 0;
        int criticalCount = 0;

        for (TestItemAnalysisVO analysis : analysisResults) {
            if (analysis.getIsAbnormal() == 1 && analysis.getAbnormalDegree() != null) {
                double degree = analysis.getAbnormalDegree().doubleValue();
                if (degree > 2.0) {
                    criticalCount++;
                } else if (degree > 1.0) {
                    highCount++;
                }
            }
        }

        if (criticalCount > 0) {
            return RiskLevelEnum.CRITICAL.getCode();
        } else if (highCount > 1) {
            return RiskLevelEnum.HIGH.getCode();
        } else if (highCount > 0) {
            return RiskLevelEnum.MEDIUM.getCode();
        }
        return RiskLevelEnum.LOW.getCode();
    }

    @Override
    public boolean checkPanicValues(List<DiagnosisRequestDTO.TestItemData> testData) {
        for (DiagnosisRequestDTO.TestItemData data : testData) {
            String itemCode = data.getItemCode() != null ? data.getItemCode() : data.getItemName();
            BloodRoutineItemConfig config = ITEM_CONFIGS.get(itemCode);

            if (config != null && data.getResultValue() != null) {
                try {
                    double value = Double.parseDouble(data.getResultValue());
                    if (value < config.getPanicLow() || value > config.getPanicHigh()) {
                        return true;
                    }
                } catch (NumberFormatException e) {
                    log.warn("无法解析结果值: itemCode={}, value={}", itemCode, data.getResultValue());
                }
            }
        }
        return false;
    }

    private BigDecimal calculateDeviation(BigDecimal value, BigDecimal refLow, BigDecimal refHigh) {
        BigDecimal refRange = refHigh.subtract(refLow);
        if (refRange.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }

        if (value.compareTo(refHigh) > 0) {
            return value.subtract(refHigh).divide(refRange, 4, RoundingMode.HALF_UP);
        } else if (value.compareTo(refLow) < 0) {
            return refLow.subtract(value).divide(refRange, 4, RoundingMode.HALF_UP);
        }
        return BigDecimal.ZERO;
    }

    private BigDecimal calculateFeatureImportance(String itemCode, BigDecimal deviation) {
        double baseImportance = switch (itemCode) {
            case "WBC", "HGB", "PLT" -> 0.9;
            case "RBC", "NEUT%", "LYMPH%" -> 0.8;
            case "HCT", "MCV", "MCH" -> 0.7;
            default -> 0.5;
        };

        double importance = baseImportance * (1 + deviation.doubleValue() * 0.2);
        return BigDecimal.valueOf(Math.min(importance, 1.0)).setScale(4, RoundingMode.HALF_UP);
    }

    private String getClinicalSignificance(String itemCode, String abnormalDirection) {
        if (abnormalDirection == null || "normal".equals(abnormalDirection)) {
            return "正常范围";
        }

        return switch (itemCode) {
            case "WBC" -> "high".equals(abnormalDirection) ? "可能提示感染、炎症或白血病" : "可能提示免疫抑制或骨髓抑制";
            case "RBC", "HGB" -> "low".equals(abnormalDirection) ? "提示贫血" : "可能提示红细胞增多症";
            case "PLT" -> "low".equals(abnormalDirection) ? "出血风险增加" : "可能提示血栓风险";
            case "NEUT%" -> "high".equals(abnormalDirection) ? "可能提示细菌感染" : "可能提示病毒感染";
            case "LYMPH%" -> "high".equals(abnormalDirection) ? "可能提示病毒感染" : "可能提示免疫缺陷";
            case "MCV" -> "high".equals(abnormalDirection) ? "大细胞性贫血可能" : "小细胞性贫血可能";
            default -> "需要结合临床综合判断";
        };
    }

    private List<DiagnosisResultVO.AbnormalItemVO> buildAbnormalItems(List<TestItemAnalysisVO> analysisResults) {
        return analysisResults.stream()
                .filter(a -> a.getIsAbnormal() == 1)
                .map(a -> {
                    DiagnosisResultVO.AbnormalItemVO item = new DiagnosisResultVO.AbnormalItemVO();
                    item.setItemCode(a.getItemCode());
                    item.setItemName(a.getItemName());
                    item.setResultValue(a.getResultValue());
                    item.setUnit(a.getUnit());
                    item.setReferenceLow(a.getReferenceLow());
                    item.setReferenceHigh(a.getReferenceHigh());
                    item.setAbnormalDegree(a.getAbnormalDegree());
                    item.setAbnormalDirection(a.getAbnormalDirection());
                    item.setFeatureImportance(a.getFeatureImportance());
                    return item;
                })
                .collect(Collectors.toList());
    }

    private boolean hasAbnormal(List<TestItemAnalysisVO> analysisResults) {
        return analysisResults.stream().anyMatch(a -> a.getIsAbnormal() == 1);
    }

    private String generateDefaultDiagnosis(List<TestItemAnalysisVO> analysisResults) {
        List<TestItemAnalysisVO> abnormalItems = analysisResults.stream()
                .filter(a -> a.getIsAbnormal() == 1)
                .collect(Collectors.toList());

        if (abnormalItems.isEmpty()) {
            return "血常规各项指标均在正常范围内";
        }

        StringBuilder sb = new StringBuilder("血常规异常：");
        for (TestItemAnalysisVO item : abnormalItems) {
            sb.append(item.getItemName())
                    .append(" ")
                    .append("high".equals(item.getAbnormalDirection()) ? "偏高" : "偏低")
                    .append("；");
        }
        return sb.substring(0, sb.length() - 1);
    }

    private String generateDefaultSuggestion(List<TestItemAnalysisVO> analysisResults) {
        List<TestItemAnalysisVO> abnormalItems = analysisResults.stream()
                .filter(a -> a.getIsAbnormal() == 1)
                .collect(Collectors.toList());

        if (abnormalItems.isEmpty()) {
            return "建议定期复查";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("建议：");
        sb.append("1. 结合临床症状综合判断；");
        sb.append("2. 必要时复查血常规；");
        sb.append("3. 如有不适请及时就医。");

        return sb.toString();
    }

    private String extractDiagnosisName(String diagnosis) {
        if (diagnosis == null || diagnosis.isEmpty()) {
            return "";
        }
        int colonIndex = diagnosis.indexOf("：");
        if (colonIndex > 0) {
            return diagnosis.substring(0, colonIndex);
        }
        return diagnosis.length() > 50 ? diagnosis.substring(0, 50) : diagnosis;
    }

    private static class BloodRoutineItemConfig {
        private final String code;
        private final String name;
        private final double refLow;
        private final double refHigh;
        private final String unit;
        private final double panicLow;
        private final double panicHigh;

        public BloodRoutineItemConfig(String code, String name, double refLow, double refHigh, String unit,
                                       double panicLow, double panicHigh) {
            this.code = code;
            this.name = name;
            this.refLow = refLow;
            this.refHigh = refHigh;
            this.unit = unit;
            this.panicLow = panicLow;
            this.panicHigh = panicHigh;
        }

        public String getCode() { return code; }
        public String getName() { return name; }
        public double getRefLow() { return refLow; }
        public double getRefHigh() { return refHigh; }
        public String getUnit() { return unit; }
        public double getPanicLow() { return panicLow; }
        public double getPanicHigh() { return panicHigh; }
    }
}
