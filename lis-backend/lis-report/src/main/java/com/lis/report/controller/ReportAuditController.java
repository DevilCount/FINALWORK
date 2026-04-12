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
@RequestMapping("/audit")
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

    @ApiOperation("初审通过")
    @PostMapping("/first-approve")
    public Result<Void> firstAudit(@Validated @RequestBody ReportAuditDTO dto,
                                   @RequestHeader(value = "X-User-Id", required = false) String userId,
                                   @RequestHeader(value = "X-User-Name", required = false) String userName) {
        if (userId != null) {
            dto.setAuditUserId(Long.parseLong(userId));
        }
        if (userName != null) {
            dto.setAuditUserName(userName);
        }
        reportAuditService.firstAudit(dto);
        return Result.success("初审通过", null);
    }

    @ApiOperation("初审驳回")
    @PostMapping("/first-reject")
    public Result<Void> firstAuditReject(@Validated @RequestBody ReportAuditDTO dto,
                                         @RequestHeader(value = "X-User-Id", required = false) String userId,
                                         @RequestHeader(value = "X-User-Name", required = false) String userName) {
        if (userId != null) {
            dto.setAuditUserId(Long.parseLong(userId));
        }
        if (userName != null) {
            dto.setAuditUserName(userName);
        }
        reportAuditService.firstAuditReject(dto);
        return Result.success("初审驳回", null);
    }

    @ApiOperation("终审通过")
    @PostMapping("/final-approve")
    public Result<Void> finalAudit(@Validated @RequestBody ReportAuditDTO dto,
                                   @RequestHeader(value = "X-User-Id", required = false) String userId,
                                   @RequestHeader(value = "X-User-Name", required = false) String userName) {
        if (userId != null) {
            dto.setAuditUserId(Long.parseLong(userId));
        }
        if (userName != null) {
            dto.setAuditUserName(userName);
        }
        reportAuditService.finalAudit(dto);
        return Result.success("终审通过", null);
    }

    @ApiOperation("终审驳回")
    @PostMapping("/final-reject")
    public Result<Void> finalAuditReject(@Validated @RequestBody ReportAuditDTO dto,
                                         @RequestHeader(value = "X-User-Id", required = false) String userId,
                                         @RequestHeader(value = "X-User-Name", required = false) String userName) {
        if (userId != null) {
            dto.setAuditUserId(Long.parseLong(userId));
        }
        if (userName != null) {
            dto.setAuditUserName(userName);
        }
        reportAuditService.finalAuditReject(dto);
        return Result.success("终审驳回", null);
    }
}
