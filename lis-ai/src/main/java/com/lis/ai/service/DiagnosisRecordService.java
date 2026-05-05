package com.lis.ai.service;

import com.lis.common.result.PageResult;
import com.lis.ai.dto.DiagnosisRequestDTO;
import com.lis.ai.dto.DiagnosisRecordQueryDTO;
import com.lis.ai.dto.DiagnosisReviewDTO;
import com.lis.ai.vo.DiagnosisResultVO;
import com.lis.ai.vo.DiagnosisRecordVO;

public interface DiagnosisRecordService {

    DiagnosisResultVO performDiagnosis(DiagnosisRequestDTO request);

    DiagnosisRecordVO getDiagnosisRecord(Long id);

    DiagnosisRecordVO getDiagnosisRecordByNo(String diagnosisNo);

    PageResult<DiagnosisRecordVO> queryDiagnosisRecords(DiagnosisRecordQueryDTO queryDTO);

    void reviewDiagnosis(DiagnosisReviewDTO reviewDTO, Long userId, String userName);

    void deleteDiagnosisRecord(Long id);
}
