package com.lis.ai.service.impl;

import com.lis.ai.config.DiagnosisConfig;
import com.lis.ai.dto.DiagnosisRequestDTO;
import com.lis.ai.entity.AiDiagnosisRuleDO;
import com.lis.ai.enums.AbnormalDirectionEnum;
import com.lis.ai.enums.DiagnosisTypeEnum;
import com.lis.ai.enums.RiskLevelEnum;
import com.lis.ai.service.DiagnosisRuleEngine;
import com.lis.ai.service.UrineRoutineAnalysisService;
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
public class UrineRoutineAnalysisServiceImpl implements UrineRoutineAnalysisService {

    private final DiagnosisRuleEngine ruleEngine;
    private final DiagnosisConfig diagnosisConfig;

    private static final Map<String, UrineRoutineItemConfig> ITEM_CONFIGS = new HashMap<>();

    static {
        ITEM_CONFIGS.put("COLOR", new UrineRoutineItemConfig("COLOR", "尿色", "淡黄色", null, null));
        ITEM_CONFIGS.put("CLARITY", new UrineRoutineItemConfig("CLARITY", "透明度", "清晰透明", null, null));
        ITEM_CONFIGS.put("SG", new UrineRoutineItemConfig("SG", "尿比重", 1.003, 1.030, null, null));
        ITEM_CONFIGS.put("PH", new UrineRoutineItemConfig("PH", "酸碱度", 4.6, 8.0, 4.0, 9.0));
        ITEM_CONFIGS.put("LEU", new UrineRoutineItemConfig("LEU", "白细胞", 0.0, 0.0, null, null));
        ITEM_CONFIGS.put("NIT", new UrineRoutineItemConfig("NIT", "亚硝酸盐", 0.0, 0.0, null, null));
        ITEM_CONFIGS.put("PRO", new UrineRoutineItemConfig("PRO", "尿蛋白", 0.0, 0.0, null, null));
        ITEM_CONFIGS.put("GLU", new UrineRoutineItemConfig("GLU", "尿糖", 0.0, 0.0, null, null));
        ITEM_CONFIGS.put("KET", new UrineRoutineItemConfig("KET", "酮体", 0.0, 0.0, null, null));
        ITEM_CONFIGS.put("UBG", new UrineRoutineItemConfig("UBG", "尿胆原", 0.0, 0.0, null, null));
        ITEM_CONFIGS.put("BIL", new UrineRoutineItemConfig("BIL", "胆红素", 0.0, 0.0, null, null));
        ITEM_CONFIGS.put("ERY", new UrineRoutineItemConfig("ERY", "红细胞", 0.0, 0.0, null, null));
        ITEM_CONFIGS.put("WBC#", new UrineRoutineItemConfig("WBC#", "白细胞计数", 0.0, 5.0, null, 50.0));
        ITEM_CONFIGS.put("RBC#", new UrineRoutineItemConfig("RBC#", "红细胞计数", 0.0, 3.0, null, 30.0));
        ITEM_CONFIGS.put("EC", new UrineRoutineItemConfig("EC", "上皮细胞", 0.0, 6.0, null, 50.0));
        ITEM_CONFIGS.put("CAST", new UrineRoutineItemConfig("CAST", "管型", 0.0, 0.0, null, 5.0));
        ITEM_CONFIGS.put("BACT", new UrineRoutineItemConfig("BACT", "细菌", 0.0, 100.0, null, 1000.0));
        ITEM_CONFIGS.put("CRYSTAL", new UrineRoutineItemConfig("CRYSTAL", "结晶", 0.0, 0.0, null, null));
        ITEM_CONFIGS.put("VC", new UrineRoutineItemConfig("VC", "维生素C", 0.0, 0.0, null, null));
    }

    private static final Set<String> QUALITATIVE_ITEMS = Set.of("LEU", "NIT", "PRO", "GLU", "KET", "UBG", "BIL", "ERY");

    @Override
    public DiagnosisResultVO analyze(DiagnosisRequestDTO request) {
        long startTime = System.currentTimeMillis();
        DiagnosisResultVO result = new DiagnosisResultVO();
        result.setDiagnosisNo(DiagnosisNoGenerator.generate("UR"));
        result.setDiagnosisType(DiagnosisTypeEnum.URINE_ROUTINE.getCode());
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
                DiagnosisTypeEnum.URINE_ROUTINE.getCode(), itemCodes);

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
            UrineRoutineItemConfig config = ITEM_CONFIGS.get(itemCode);

            TestItemAnalysisVO analysis = new TestItemAnalysisVO();
            analysis.setItemId(data.getItemId());
            analysis.setItemCode(itemCode);
            analysis.setItemName(data.getItemName() != null ? data.getItemName() :
                    (config != null ? config.getName() : itemCode));
            analysis.setResultValue(data.getResultValue());
            analysis.setUnit(data.getUnit());

            if (config != null && config.getRefLow() != null && config.getRefHigh() != null) {
                analysis.setReferenceLow(BigDecimal.valueOf(config.getRefLow()));
                analysis.setReferenceHigh(BigDecimal.valueOf(config.getRefHigh()));
            } else if (data.getReferenceLow() != null && data.getReferenceHigh() != null) {
                analysis.setReferenceLow(BigDecimal.valueOf(data.getReferenceLow()));
                analysis.setReferenceHigh(BigDecimal.valueOf(data.getReferenceHigh()));
            }

            if (QUALITATIVE_ITEMS.contains(itemCode)) {
                analyzeQualitativeItem(analysis, data, config);
            } else {
                analyzeQuantitativeItem(analysis, data, config);
            }

            analysis.setClinicalSignificance(getClinicalSignificance(itemCode, analysis.getAbnormalDirection()));
            results.add(analysis);
        }

        return results;
    }

    private void analyzeQualitativeItem(TestItemAnalysisVO analysis, DiagnosisRequestDTO.TestItemData data,
                                         UrineRoutineItemConfig config) {
        String resultValue = data.getResultValue();
        boolean isAbnormal = false;

        if (resultValue != null) {
            String upperValue = resultValue.toUpperCase();
            isAbnormal = switch (upperValue) {
                case "POS", "+", "++", "+++", "++++", "1+", "2+", "3+", "4+", "TRACE" -> true;
                case "NEG", "-", "NEGATIVE", "NORMAL", "NORM" -> false;
                default -> {
                    try {
                        double numValue = Double.parseDouble(resultValue);
                        yield numValue > 0;
                    } catch (NumberFormatException e) {
                        yield false;
                    }
                }
            };
        }

        analysis.setIsAbnormal(isAbnormal ? 1 : 0);
        if (isAbnormal) {
            analysis.setAbnormalDirection("high");
            analysis.setAbnormalDegree(BigDecimal.ONE);
            analysis.setFeatureImportance(calculateQualitativeImportance(data.getItemCode()));
        } else {
            analysis.setAbnormalDirection("normal");
        }
    }

    private void analyzeQuantitativeItem(TestItemAnalysisVO analysis, DiagnosisRequestDTO.TestItemData data,
                                          UrineRoutineItemConfig config) {
        if (data.getResultValue() == null) {
            analysis.setIsAbnormal(0);
            analysis.setAbnormalDirection("normal");
            return;
        }

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
                    analysis.setFeatureImportance(calculateFeatureImportance(data.getItemCode(), deviation));
                }
            } else {
                analysis.setIsAbnormal(data.getIsAbnormal() != null ? data.getIsAbnormal() : 0);
                analysis.setAbnormalDirection(analysis.getIsAbnormal() == 1 ? "high" : "normal");
            }
        } catch (NumberFormatException e) {
            log.warn("无法解析结果值: itemCode={}, value={}", data.getItemCode(), data.getResultValue());
            analysis.setIsAbnormal(0);
            analysis.setAbnormalDirection("normal");
        }
    }

    @Override
    public String determineRiskLevel(List<TestItemAnalysisVO> analysisResults) {
        boolean hasProtein = false;
        boolean hasBlood = false;
        boolean hasLeukocyte = false;
        boolean hasGlucose = false;
        boolean hasKetone = false;
        int abnormalCount = 0;

        for (TestItemAnalysisVO analysis : analysisResults) {
            if (analysis.getIsAbnormal() == 1) {
                abnormalCount++;
                String itemCode = analysis.getItemCode();
                if ("PRO".equals(itemCode)) hasProtein = true;
                if ("ERY".equals(itemCode) || "RBC#".equals(itemCode)) hasBlood = true;
                if ("LEU".equals(itemCode) || "WBC#".equals(itemCode)) hasLeukocyte = true;
                if ("GLU".equals(itemCode)) hasGlucose = true;
                if ("KET".equals(itemCode)) hasKetone = true;
            }
        }

        if (hasGlucose && hasKetone) {
            return RiskLevelEnum.CRITICAL.getCode();
        }
        if (hasProtein && hasBlood) {
            return RiskLevelEnum.HIGH.getCode();
        }
        if (abnormalCount >= 3) {
            return RiskLevelEnum.HIGH.getCode();
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
            UrineRoutineItemConfig config = ITEM_CONFIGS.get(itemCode);

            if (config != null && config.getPanicHigh() != null && data.getResultValue() != null) {
                try {
                    double value = Double.parseDouble(data.getResultValue());
                    if (value > config.getPanicHigh()) {
                        return true;
                    }
                } catch (NumberFormatException e) {
                    String upperValue = data.getResultValue().toUpperCase();
                    if ("+++".equals(upperValue) || "++++".equals(upperValue) || "3+".equals(upperValue) || "4+".equals(upperValue)) {
                        return true;
                    }
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
            case "PRO", "ERY", "GLU" -> 0.9;
            case "LEU", "WBC#", "RBC#" -> 0.85;
            case "KET", "NIT", "BIL" -> 0.8;
            case "SG", "PH" -> 0.6;
            default -> 0.5;
        };

        double importance = baseImportance * (1 + deviation.doubleValue() * 0.2);
        return BigDecimal.valueOf(Math.min(importance, 1.0)).setScale(4, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateQualitativeImportance(String itemCode) {
        double importance = switch (itemCode) {
            case "PRO", "ERY", "GLU" -> 0.9;
            case "LEU", "KET", "NIT" -> 0.85;
            case "BIL", "UBG" -> 0.75;
            default -> 0.5;
        };
        return BigDecimal.valueOf(importance).setScale(4, RoundingMode.HALF_UP);
    }

    private String getClinicalSignificance(String itemCode, String abnormalDirection) {
        if (abnormalDirection == null || "normal".equals(abnormalDirection)) {
            return "正常范围";
        }

        return switch (itemCode) {
            case "PRO" -> "蛋白尿可能，提示肾脏损伤或泌尿系统疾病";
            case "GLU" -> "糖尿可能，提示血糖控制不佳或肾糖阈降低";
            case "ERY", "RBC#" -> "血尿可能，提示泌尿系统出血或肾小球疾病";
            case "LEU", "WBC#" -> "白细胞尿可能，提示泌尿系统感染";
            case "NIT" -> "亚硝酸盐阳性，提示细菌感染可能";
            case "KET" -> "酮体阳性，提示代谢异常或糖尿病酮症";
            case "BIL" -> "胆红素阳性，提示肝胆系统疾病";
            case "UBG" -> "尿胆原异常，提示肝胆系统或溶血性疾病";
            case "SG" -> "high".equals(abnormalDirection) ? "尿液浓缩，提示脱水" : "尿液稀释，提示肾功能异常或饮水过多";
            case "PH" -> "high".equals(abnormalDirection) ? "碱性尿，提示感染或代谢性碱中毒" : "酸性尿，提示代谢性酸中毒或饮食因素";
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
            return "尿常规各项指标均在正常范围内";
        }

        StringBuilder sb = new StringBuilder("尿常规异常：");
        for (TestItemAnalysisVO item : abnormalItems) {
            sb.append(item.getItemName()).append("异常；");
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
        sb.append("2. 必要时复查尿常规；");
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

    private static class UrineRoutineItemConfig {
        private final String code;
        private final String name;
        private final Double refLow;
        private final Double refHigh;
        private final Double panicLow;
        private final Double panicHigh;
        private final String normalValue;

        public UrineRoutineItemConfig(String code, String name, Double refLow, Double refHigh,
                                       Double panicLow, Double panicHigh) {
            this.code = code;
            this.name = name;
            this.refLow = refLow;
            this.refHigh = refHigh;
            this.panicLow = panicLow;
            this.panicHigh = panicHigh;
            this.normalValue = null;
        }

        public UrineRoutineItemConfig(String code, String name, String normalValue, Double panicLow, Double panicHigh) {
            this.code = code;
            this.name = name;
            this.refLow = null;
            this.refHigh = null;
            this.panicLow = panicLow;
            this.panicHigh = panicHigh;
            this.normalValue = normalValue;
        }

        public String getCode() { return code; }
        public String getName() { return name; }
        public Double getRefLow() { return refLow; }
        public Double getRefHigh() { return refHigh; }
        public Double getPanicLow() { return panicLow; }
        public Double getPanicHigh() { return panicHigh; }
        public String getNormalValue() { return normalValue; }
    }
}
