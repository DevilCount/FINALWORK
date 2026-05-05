package com.lis.report.service;

import com.lis.report.dto.BatchResultEntryDTO;
import com.lis.report.dto.ResultEntryDTO;
import com.lis.report.vo.ReportItemVO;

public interface ResultEntryService {

    Long entryResult(ResultEntryDTO dto);

    void batchEntryResult(BatchResultEntryDTO dto);

    void deleteResult(Long reportItemId);

    ReportItemVO getResultItemById(Long reportItemId);
}
