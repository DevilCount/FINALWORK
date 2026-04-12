package com.lis.report.service;

import com.lis.report.dto.ReportAuditDTO;

public interface ReportAuditService {

    void submitForAudit(Long reportId);

    void auditReport(ReportAuditDTO dto);

    void rejectReport(ReportAuditDTO dto);

    void firstAudit(ReportAuditDTO dto);

    void firstAuditReject(ReportAuditDTO dto);

    void finalAudit(ReportAuditDTO dto);

    void finalAuditReject(ReportAuditDTO dto);
}
