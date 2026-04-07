package com.lis.ai.service.impl;

import com.lis.ai.config.DiagnosisConfig;
import com.lis.ai.dto.DiagnosisRequestDTO;
import com.lis.ai.entity.AiDiagnosisRuleDO;
import com.lis.ai.enums.AbnormalDirectionEnum;
import com.lis.ai.enums.DiagnosisTypeEnum;
import com.lis.ai.enums.RiskLevelEnum;
import com.lis.ai.service.DiagnosisRuleEngine;
import com.lis.ai.service.LiverFunctionAnalysisService;
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
public class LiverFunctionAnalysisServiceImpl implements LiverFunctionAnalysisService {

    private final DiagnosisRuleEngine ruleEngine;
    private final DiagnosisConfig diagnosisConfig;

    private static final Map<String, LiverFunctionItemConfig> ITEM_CONFIGS = new HashMap<>();

    static {
        ITEM_CONFIGS.put("ALT", new LiverFunctionItemConfig("ALT", "丙氨酸氨基转移酶", 0.0, 40.0, "U/L", 0.0, 1000.0));
        ITEM_CONFIGS.put("AST", new LiverFunctionItemConfig("AST", "天门冬氨酸氨基转移酶", 0.0, 40.0, "U/L", 0.0, 1000.0));
        ITEM_CONFIGS.put("ALP", new LiverFunctionItemConfig("ALP", "碱性磷酸酶", 45.0, 125.0, "U/L", 20.0, 500.0));
        ITEM_CONFIGS.put("GGT", new LiverFunctionItemConfig("GGT", "γ-谷氨酰转肽酶", 0.0, 50.0, "U/L", 0.0, 500.0));
        ITEM_CONFIGS.put("TBIL", new LiverFunctionItemConfig("TBIL", "总胆红素", 3.4, 17.1, "μmol/L", 0.0, 300.0));
        ITEM_CONFIGS.put("DBIL", new LiverFunctionItemConfig("DBIL", "直接胆红素", 0.0, 6.8, "μmol/L", 0.0, 200.0));
        ITEM_CONFIGS.put("IBIL", new LiverFunctionItemConfig("IBIL", "间接胆红素", 3.4, 10.3, "μmol/L", 0.0, 200.0));
        ITEM_CONFIGS.put("TP", new LiverFunctionItemConfig("TP", "总蛋白", 65.0, 85.0, "g/L", 40.0, 100.0));
        ITEM_CONFIGS.put("ALB", new LiverFunctionItemConfig("ALB", "白蛋白", 40.0, 55.0, "g/L", 20.0, 70.0));
        ITEM_CONFIGS.put("GLB", new LiverFunctionItemConfig("GLB", "球蛋白", 20.0, 40.0, "g/L", 10.0, 60.0));
        ITEM_CONFIGS.put("A/G", new LiverFunctionItemConfig("A/G", "白球比", 1.2, 2.4, "", 0.5, 3.5));
        ITEM_CONFIGS.put("TBA", new LiverFunctionItemConfig("TBA", "总胆汁酸", 0.0, 10.0, "μmol/L", 0.0, 100.0));
        ITEM_CONFIGS.put("CHE", new LiverFunctionItemConfig("CHE", "胆碱酯酶", 5000.0, 12000.0, "U/L", 2000.0, 20000.0));
        ITEM_CONFIGS.put("ADA", new LiverFunctionItemConfig("ADA", "腺苷脱氨酶", 0.0, 25.0, "U/L", 0.0, 100.0));
        ITEM_CONFIGS.put("AFU", new LiverFunctionItemConfig("AFU", "α-L-岩藻糖苷酶", 0.0, 40.0, "U/L", 0.0, 100.0));
        ITEM_CONFIGS.put("PA", new LiverFunctionItemConfig("PA", "前白蛋白", 200.0, 400.0, "mg/L", 50.0, 500.0));
    }

    @Override
    public DiagnosisResultVO analyze(DiagnosisRequestDTO request) {
        long startTime = System.currentTimeMillis();
        DiagnosisResultVO result = new DiagnosisResultVO();
        result.setDiagnosisNo(DiagnosisNoGenerator.generate("LF"));
        result.setDiagnosisType(DiagnosisTypeEnum.LIVER_FUNCTION.getCode());
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
                DiagnosisTypeEnum.LIVER_FUNCTION.getCode(), itemCodes);

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
            primaryDiagnosis = generateDefaultDiagnosis(analysisResults, testDataMap);
            primarySuggestion = generateDefaultSuggestion(analysisResults, testDataMap);
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
            LiverFunctionItemConfig config = ITEM_CONFIGS.get(itemCode);

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
        boolean hasCriticalEnzyme = false;
        boolean hasCriticalBilirubin = false;
        boolean hasLowAlbumin = false;
        int abnormalCount = 0;
        double maxDeviation = 0;

        for (TestItemAnalysisVO analysis : analysisResults) {
            if (analysis.getIsAbnormal() == 1) {
                abnormalCount++;
                String itemCode = analysis.getItemCode();

                if (analysis.getAbnormalDegree() != null) {
                    maxDeviation = Math.max(maxDeviation, analysis.getAbnormalDegree().doubleValue());
                }

                if (("ALT".equals(itemCode) || "AST".equals(itemCode)) && analysis.getAbnormalDegree() != null) {
                    if (analysis.getAbnormalDegree().doubleValue() > 5.0) {
                        hasCriticalEnzyme = true;
                    }
                }

                if ("TBIL".equals(itemCode) && analysis.getAbnormalDegree() != null) {
                    if (analysis.getAbnormalDegree().doubleValue() > 3.0) {
                        hasCriticalBilirubin = true;
                    }
                }

                if ("ALB".equals(itemCode) && "low".equals(analysis.getAbnormalDirection())) {
                    hasLowAlbumin = true;
                }
            }
        }

        if (hasCriticalEnzyme && hasCriticalBilirubin) {
            return RiskLevelEnum.CRITICAL.getCode();
        }
        if (hasCriticalEnzyme || hasCriticalBilirubin || (hasLowAlbumin && abnormalCount >= 3)) {
            return RiskLevelEnum.HIGH.getCode();
        }
        if (abnormalCount >= 2 || maxDeviation > 2.0) {
            return RiskLevelEnum.MEDIUM.getCode();
        }
        if (abnormalCount >= 1) {
            return RiskLevelEnum.MEDIUM.getCode();
        }
        return RiskLevelEnum.LOW.getCode();
    }

    @Override
    public boolean checkPanicValues(List<DiagnosisRequestDTO.TestItemData> testData) {
        for (DiagnosisRequestDTO.TestItemData data : testData) {
            String itemCode = data.getItemCode() != null ? data.getItemCode() : data.getItemName();
            LiverFunctionItemConfig config = ITEM_CONFIGS.get(itemCode);

            if (config != null && data.getResultValue() != null) {
                try {
                    double value = Double.parseDouble(data.getResultValue());
                    if (value < config.getPanicLow() || value > config.getPanicHigh()) {
                        if ("ALT".equals(itemCode) || "AST".equals(itemCode) || "TBIL".equals(itemCode)) {
                            return true;
                        }
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
            case "ALT", "AST" -> 0.95;
            case "TBIL", "DBIL" -> 0.9;
            case "ALB", "GGT" -> 0.85;
            case "ALP", "TBA" -> 0.8;
            case "TP", "GLB", "A/G" -> 0.7;
            default -> 0.5;
        };

        double importance = baseImportance * (1 + deviation.doubleValue() * 0.15);
        return BigDecimal.valueOf(Math.min(importance, 1.0)).setScale(4, RoundingMode.HALF_UP);
    }

    private String getClinicalSignificance(String itemCode, String abnormalDirection) {
        if (abnormalDirection == null || "normal".equals(abnormalDirection)) {
            return "正常范围";
        }

        return switch (itemCode) {
            case "ALT" -> "high".equals(abnormalDirection) ? "肝细胞损伤指标升高，提示肝炎、脂肪肝等" : "罕见，需排除检测误差";
            case "AST" -> "high".equals(abnormalDirection) ? "肝细胞损伤或心肌损伤可能" : "罕见，需排除检测误差";
            case "ALP" -> "high".equals(abnormalDirection) ? "胆道梗阻或骨代谢异常可能" : "少见，需排除营养不良";
            case "GGT" -> "high".equals(abnormalDirection) ? "胆道疾病或酒精性肝损伤可能" : "临床意义不大";
            case "TBIL" -> "high".equals(abnormalDirection) ? "黄疸可能，需结合直接/间接胆红素判断" : "少见，需排除贫血";
            case "DBIL" -> "high".equals(abnormalDirection) ? "梗阻性黄疸或肝细胞性黄疸可能" : "正常";
            case "IBIL" -> "high".equals(abnormalDirection) ? "溶血性黄疸或肝细胞性黄疸可能" : "正常";
            case "ALB" -> "low".equals(abnormalDirection) ? "肝功能减退或营养不良可能" : "脱水可能";
            case "GLB" -> "high".equals(abnormalDirection) ? "慢性炎症或免疫性疾病可能" : "免疫功能低下可能";
            case "A/G" -> "low".equals(abnormalDirection) ? "肝功能减退或肾病综合征可能" : "少见";
            case "TBA" -> "high".equals(abnormalDirection) ? "肝细胞损伤或胆汁淤积可能" : "正常";
            case "CHE" -> "low".equals(abnormalDirection) ? "肝功能严重减退可能" : "临床意义不大";
            case "PA" -> "low".equals(abnormalDirection) ? "营养不良或肝功能减退" : "临床意义不大";
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

    private String generateDefaultDiagnosis(List<TestItemAnalysisVO> analysisResults,
                                             Map<String, DiagnosisRequestDTO.TestItemData> testDataMap) {
        List<TestItemAnalysisVO> abnormalItems = analysisResults.stream()
                .filter(a -> a.getIsAbnormal() == 1)
                .collect(Collectors.toList());

        if (abnormalItems.isEmpty()) {
            return "肝功能各项指标均在正常范围内";
        }

        boolean altHigh = false, astHigh = false, tbilHigh = false, ggtHigh = false, alpHigh = false;
        boolean albLow = false;

        for (TestItemAnalysisVO item : abnormalItems) {
            String code = item.getItemCode();
            if ("ALT".equals(code) && "high".equals(item.getAbnormalDirection())) altHigh = true;
            if ("AST".equals(code) && "high".equals(item.getAbnormalDirection())) astHigh = true;
            if ("TBIL".equals(code) && "high".equals(item.getAbnormalDirection())) tbilHigh = true;
            if ("GGT".equals(code) && "high".equals(item.getAbnormalDirection())) ggtHigh = true;
            if ("ALP".equals(code) && "high".equals(item.getAbnormalDirection())) alpHigh = true;
            if ("ALB".equals(code) && "low".equals(item.getAbnormalDirection())) albLow = true;
        }

        StringBuilder sb = new StringBuilder();

        if (altHigh && astHigh) {
            sb.append("转氨酶升高，提示肝细胞损伤；");
        } else if (altHigh) {
            sb.append("ALT升高，提示肝细胞损伤；");
        } else if (astHigh) {
            sb.append("AST升高，提示肝细胞或心肌损伤；");
        }

        if (tbilHigh) {
            sb.append("胆红素升高，提示黄疸可能；");
        }

        if (ggtHigh && alpHigh) {
            sb.append("胆道梗阻可能；");
        } else if (ggtHigh) {
            sb.append("GGT升高；");
        }

        if (albLow) {
            sb.append("白蛋白降低；");
        }

        if (sb.length() == 0) {
            sb.append("肝功能异常：");
            for (TestItemAnalysisVO item : abnormalItems) {
                sb.append(item.getItemName())
                        .append(" ")
                        .append("high".equals(item.getAbnormalDirection()) ? "偏高" : "偏低")
                        .append("；");
            }
        }

        return sb.substring(0, sb.length() - 1);
    }

    private String generateDefaultSuggestion(List<TestItemAnalysisVO> analysisResults,
                                              Map<String, DiagnosisRequestDTO.TestItemData> testDataMap) {
        List<TestItemAnalysisVO> abnormalItems = analysisResults.stream()
                .filter(a -> a.getIsAbnormal() == 1)
                .collect(Collectors.toList());

        if (abnormalItems.isEmpty()) {
            return "建议定期复查肝功能";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("建议：");
        sb.append("1. 结合临床症状和病史综合判断；");
        sb.append("2. 必要时完善肝炎病毒标志物、腹部超声等检查；");
        sb.append("3. 避免饮酒和使用肝毒性药物；");
        sb.append("4. 如有不适请及时就医。");

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

    private static class LiverFunctionItemConfig {
        private final String code;
        private final String name;
        private final double refLow;
        private final double refHigh;
        private final String unit;
        private final double panicLow;
        private final double panicHigh;

        public LiverFunctionItemConfig(String code, String name, double refLow, double refHigh,
                                        String unit, double panicLow, double panicHigh) {
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
