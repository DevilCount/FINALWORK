package com.lis.report.service;

import com.lis.common.result.PageResult;
import com.lis.report.dto.ReportApplyDTO;
import com.lis.report.dto.ReportQueryDTO;
import com.lis.report.vo.ReportDetailVO;
import com.lis.report.vo.ReportVO;

public interface ReportApplyService {

    Long createReportApply(ReportApplyDTO dto);

    void updateReportApply(Long reportId, ReportApplyDTO dto);

    void deleteReportApply(Long reportId);

    void cancelReport(Long reportId, String reason);

    ReportVO getReportById(Long reportId);

    ReportDetailVO getReportDetailById(Long reportId);

    PageResult<ReportVO> queryReports(ReportQueryDTO dto);
}
