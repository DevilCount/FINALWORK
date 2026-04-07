package com.lis.statistics.controller;

import com.lis.common.result.PageResult;
import com.lis.common.result.Result;
import com.lis.statistics.dto.WorkloadQueryDTO;
import com.lis.statistics.service.WorkloadStatService;
import com.lis.statistics.vo.EChartsVO;
import com.lis.statistics.vo.WorkloadStatVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/statistics/workload")
@RequiredArgsConstructor
@Api(tags = "工作量统计")
public class WorkloadStatController {

    private final WorkloadStatService workloadStatService;

    @GetMapping("/daily")
    @ApiOperation("获取每日工作量统计")
    public Result<List<WorkloadStatVO>> getDailyWorkload(WorkloadQueryDTO queryDTO) {
        return Result.success(workloadStatService.getDailyWorkload(queryDTO));
    }

    @GetMapping("/user/page")
    @ApiOperation("分页获取用户工作量统计")
    public Result<PageResult<WorkloadStatVO>> getUserWorkloadPage(WorkloadQueryDTO queryDTO) {
        return Result.success(workloadStatService.getUserWorkloadPage(queryDTO));
    }

    @GetMapping("/dept")
    @ApiOperation("获取科室工作量统计")
    public Result<List<WorkloadStatVO>> getDeptWorkload(WorkloadQueryDTO queryDTO) {
        return Result.success(workloadStatService.getDeptWorkload(queryDTO));
    }

    @GetMapping("/chart/trend")
    @ApiOperation("获取工作量趋势图表")
    public Result<EChartsVO> getWorkloadTrendChart(WorkloadQueryDTO queryDTO) {
        return Result.success(workloadStatService.getWorkloadTrendChart(queryDTO));
    }

    @GetMapping("/chart/distribution")
    @ApiOperation("获取工作量分布图表")
    public Result<EChartsVO> getWorkloadDistributionChart(WorkloadQueryDTO queryDTO) {
        return Result.success(workloadStatService.getWorkloadDistributionChart(queryDTO));
    }
}
