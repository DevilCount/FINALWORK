package com.lis.report.service;

import com.lis.report.dto.ReportAuditDTO;

public interface ReportAuditService {

    void submitForAudit(Long reportId);

    void auditReport(ReportAuditDTO dto);

    void rejectReport(ReportAuditDTO dto);
}
