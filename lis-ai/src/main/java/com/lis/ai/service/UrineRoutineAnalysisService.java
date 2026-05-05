package com.lis.ai.service;

import com.lis.ai.dto.DiagnosisRequestDTO;
import com.lis.ai.vo.DiagnosisResultVO;
import com.lis.ai.vo.TestItemAnalysisVO;

import java.util.List;

public interface UrineRoutineAnalysisService {

    DiagnosisResultVO analyze(DiagnosisRequestDTO request);

    List<TestItemAnalysisVO> analyzeTestItems(List<DiagnosisRequestDTO.TestItemData> testData);

    String determineRiskLevel(List<TestItemAnalysisVO> analysisResults);

    boolean checkPanicValues(List<DiagnosisRequestDTO.TestItemData> testData);
}
