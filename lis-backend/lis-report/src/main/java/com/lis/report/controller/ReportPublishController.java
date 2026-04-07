package com.lis.report.controller;

import com.lis.common.result.Result;
import com.lis.report.dto.ReportPrintDTO;
import com.lis.report.dto.ReportPublishDTO;
import com.lis.report.service.ReportPublishService;
import com.lis.report.vo.ReportPrintLogVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "报告发布管理")
@RestController
@RequestMapping("/report/publish")
@RequiredArgsConstructor
public class ReportPublishController {

    private final ReportPublishService reportPublishService;

    @ApiOperation("发布报告")
    @PostMapping
    public Result<Void> publishReport(@Validated @RequestBody ReportPublishDTO dto) {
        reportPublishService.publishReport(dto);
        return Result.success("发布成功", null);
    }

    @ApiOperation("打印报告")
    @PostMapping("/print")
    public Result<Void> printReport(@Validated @RequestBody ReportPrintDTO dto) {
        reportPublishService.printReport(dto);
        return Result.success("打印成功", null);
    }

    @ApiOperation("获取打印记录")
    @GetMapping("/printLogs/{reportId}")
    public Result<List<ReportPrintLogVO>> getPrintLogs(@PathVariable Long reportId) {
        List<ReportPrintLogVO> logs = reportPublishService.getPrintLogs(reportId);
        return Result.success(logs);
    }
}
