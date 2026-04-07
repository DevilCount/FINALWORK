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

@Api(tags = "检验申请管理")
@RestController
@RequestMapping("/report/apply")
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
    @GetMapping("/{reportId}/detail")
    public Result<ReportDetailVO> getReportDetailById(@PathVariable Long reportId) {
        ReportDetailVO report = reportApplyService.getReportDetailById(reportId);
        return Result.success(report);
    }

    @ApiOperation("分页查询报告")
    @PostMapping("/query")
    public Result<PageResult<ReportVO>> queryReports(@RequestBody ReportQueryDTO dto) {
        PageResult<ReportVO> result = reportApplyService.queryReports(dto);
        return Result.success(result);
    }
}
