package com.lis.report.controller;

import com.lis.common.result.Result;
import com.lis.report.dto.ReportAuditDTO;
import com.lis.report.service.ReportAuditService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "报告审核管理")
@RestController
@RequestMapping("/report/audit")
@RequiredArgsConstructor
public class ReportAuditController {

    private final ReportAuditService reportAuditService;

    @ApiOperation("提交审核")
    @PostMapping("/submit/{reportId}")
    public Result<Void> submitForAudit(@PathVariable Long reportId) {
        reportAuditService.submitForAudit(reportId);
        return Result.success("提交成功", null);
    }

    @ApiOperation("审核通过")
    @PostMapping("/approve")
    public Result<Void> auditReport(@Validated @RequestBody ReportAuditDTO dto) {
        reportAuditService.auditReport(dto);
        return Result.success("审核通过", null);
    }

    @ApiOperation("审核驳回")
    @PostMapping("/reject")
    public Result<Void> rejectReport(@Validated @RequestBody ReportAuditDTO dto) {
        reportAuditService.rejectReport(dto);
        return Result.success("已驳回", null);
    }
}
