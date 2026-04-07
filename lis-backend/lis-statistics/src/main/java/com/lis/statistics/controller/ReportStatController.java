package com.lis.statistics.controller;

import com.lis.common.result.PageResult;
import com.lis.common.result.Result;
import com.lis.statistics.dto.ReportQueryDTO;
import com.lis.statistics.service.ReportStatService;
import com.lis.statistics.vo.EChartsVO;
import com.lis.statistics.vo.ReportStatVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/statistics/report")
@RequiredArgsConstructor
@Api(tags = "报告统计")
public class ReportStatController {

    private final ReportStatService reportStatService;

    @GetMapping("/daily")
    @ApiOperation("获取每日报告统计")
    public Result<List<ReportStatVO>> getDailyStat(ReportQueryDTO queryDTO) {
        return Result.success(reportStatService.getDailyStat(queryDTO));
    }

    @GetMapping("/item/page")
    @ApiOperation("分页获取检验项目统计")
    public Result<PageResult<ReportStatVO>> getItemStatPage(ReportQueryDTO queryDTO) {
        return Result.success(reportStatService.getItemStatPage(queryDTO));
    }

    @GetMapping("/dept")
    @ApiOperation("获取科室报告统计")
    public Result<List<ReportStatVO>> getDeptStat(ReportQueryDTO queryDTO) {
        return Result.success(reportStatService.getDeptStat(queryDTO));
    }

    @GetMapping("/chart/trend")
    @ApiOperation("获取报告趋势图表")
    public Result<EChartsVO> getReportTrendChart(ReportQueryDTO queryDTO) {
        return Result.success(reportStatService.getReportTrendChart(queryDTO));
    }

    @GetMapping("/chart/abnormal")
    @ApiOperation("获取异常率图表")
    public Result<EChartsVO> getAbnormalRateChart(ReportQueryDTO queryDTO) {
        return Result.success(reportStatService.getAbnormalRateChart(queryDTO));
    }

    @GetMapping("/chart/panic")
    @ApiOperation("获取危急值分布图表")
    public Result<EChartsVO> getPanicDistributionChart(ReportQueryDTO queryDTO) {
        return Result.success(reportStatService.getPanicDistributionChart(queryDTO));
    }
}
