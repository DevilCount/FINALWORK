package com.lis.report.controller;

import com.lis.common.result.PageResult;
import com.lis.common.result.Result;
import com.lis.report.dto.ReportApplyDTO;
import com.lis.report.dto.ReportQueryDTO;
import com.lis.report.service.ReportApplyService;
import com.lis.report.vo.ReportDetailVO;
import com.lis.report.vo.ReportVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags = "检验申请管理")
@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class ReportApplyController {

    private final ReportApplyService reportApplyService;

    @ApiOperation("创建检验申请")
    @PostMapping
    public Result<Long> createReportApply(@Validated @RequestBody ReportApplyDTO dto) {
        Long reportId = reportApplyService.createReportApply(dto);
        return Result.success("创建成功", reportId);
    }

    @ApiOperation("取消报告")
    @PostMapping("/{reportId}/cancel")
    public Result<Void> cancelReport(@PathVariable Long reportId, @RequestParam(required = false) String reason) {
        reportApplyService.cancelReport(reportId, reason);
        return Result.success("取消成功", null);
    }

    @ApiOperation("根据ID获取报告")
    @GetMapping("/{reportId}")
    public Result<ReportVO> getReportById(@PathVariable Long reportId) {
        ReportVO report = reportApplyService.getReportById(reportId);
        return Result.success(report);
    }

    @ApiOperation("获取报告详情（含明细）")
    @GetMapping("/detail/{id}")
    public Result<ReportDetailVO> getReportDetailById(@PathVariable Long id) {
        ReportDetailVO report = reportApplyService.getReportDetailById(id);
        return Result.success(report);
    }

    @ApiOperation("分页查询报告")
    @GetMapping("/list")
    public Result<PageResult<ReportVO>> queryReports(ReportQueryDTO dto) {
        PageResult<ReportVO> result = reportApplyService.queryReports(dto);
        return Result.success(result);
    }

    @ApiOperation("根据标本ID获取报告")
    @GetMapping("/specimen/{specimenId}")
    public Result<ReportVO> getReportBySpecimenId(@PathVariable String specimenId) {
        return Result.success(null);
    }

    @ApiOperation("获取报告结果")
    @GetMapping("/results/{reportId}")
    public Result<List<Map<String, Object>>> getReportResults(@PathVariable Long reportId) {
        return Result.success(List.of());
    }

    @ApiOperation("提交报告")
    @PostMapping("/submit/{reportId}")
    public Result<ReportVO> submitReport(@PathVariable Long reportId) {
        return Result.success(null);
    }

    @ApiOperation("审核报告")
    @PostMapping("/audit")
    public Result<ReportVO> auditReport(@RequestBody Map<String, Object> request) {
        return Result.success(null);
    }

    @ApiOperation("批量审核报告")
    @PostMapping("/batch-audit")
    public Result<Map<String, Integer>> batchAuditReports(@RequestBody Map<String, Object> request) {
        return Result.success(Map.of("success", 0, "failed", 0));
    }

    @ApiOperation("打印报告")
    @GetMapping("/print/{reportId}")
    public Result<byte[]> printReport(@PathVariable Long reportId) {
        return Result.success(new byte[0]);
    }

    @ApiOperation("预览报告")
    @GetMapping("/preview/{reportId}")
    public Result<String> getReportPreview(@PathVariable Long reportId) {
        return Result.success("");
    }

    @ApiOperation("撤回报告")
    @PostMapping("/retract/{reportId}")
    public Result<ReportVO> retractReport(@PathVariable Long reportId, @RequestBody Map<String, String> request) {
        return Result.success(null);
    }

    @ApiOperation("导出报告列表")
    @GetMapping("/export")
    public Result<byte[]> exportReportList(ReportQueryDTO dto) {
        return Result.success(new byte[0]);
    }

    @ApiOperation("报告统计")
    @GetMapping("/statistics")
    public Result<Map<String, Object>> getReportStatistics(@RequestParam String startTime, @RequestParam String endTime,
                                                           @RequestParam(required = false) String departmentId) {
        return Result.success(Map.of(
            "totalCount", 0,
            "auditedCount", 0,
            "pendingCount", 0,
            "abnormalCount", 0
        ));
    }
}
