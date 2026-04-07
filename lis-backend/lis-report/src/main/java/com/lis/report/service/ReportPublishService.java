package com.lis.report.service;

import com.lis.report.dto.ReportPrintDTO;
import com.lis.report.dto.ReportPublishDTO;
import com.lis.report.vo.ReportPrintLogVO;

import java.util.List;

public interface ReportPublishService {

    void publishReport(ReportPublishDTO dto);

    void printReport(ReportPrintDTO dto);

    List<ReportPrintLogVO> getPrintLogs(Long reportId);
}
