package com.lis.statistics.controller;

import com.lis.common.result.PageResult;
import com.lis.common.result.Result;
import com.lis.statistics.dto.SpecimenQueryDTO;
import com.lis.statistics.service.SpecimenStatService;
import com.lis.statistics.vo.EChartsVO;
import com.lis.statistics.vo.SpecimenStatVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/specimen")
@RequiredArgsConstructor
@Api(tags = "标本统计")
public class SpecimenStatController {

    private final SpecimenStatService specimenStatService;

    @GetMapping("/daily")
    @ApiOperation("获取每日标本统计")
    public Result<List<SpecimenStatVO>> getDailyStat(SpecimenQueryDTO queryDTO) {
        return Result.success(specimenStatService.getDailyStat(queryDTO));
    }

    @GetMapping("/dept/page")
    @ApiOperation("分页获取科室标本统计")
    public Result<PageResult<SpecimenStatVO>> getDeptStatPage(SpecimenQueryDTO queryDTO) {
        return Result.success(specimenStatService.getDeptStatPage(queryDTO));
    }

    @GetMapping("/type")
    @ApiOperation("获取标本类型统计")
    public Result<List<SpecimenStatVO>> getTypeStat(SpecimenQueryDTO queryDTO) {
        return Result.success(specimenStatService.getTypeStat(queryDTO));
    }

    @GetMapping("/chart/trend")
    @ApiOperation("获取标本趋势图表")
    public Result<EChartsVO> getSpecimenTrendChart(SpecimenQueryDTO queryDTO) {
        return Result.success(specimenStatService.getSpecimenTrendChart(queryDTO));
    }

    @GetMapping("/chart/distribution")
    @ApiOperation("获取科室标本分布图表")
    public Result<EChartsVO> getSpecimenDistributionChart(SpecimenQueryDTO queryDTO) {
        return Result.success(specimenStatService.getSpecimenDistributionChart(queryDTO));
    }

    @GetMapping("/chart/type-pie")
    @ApiOperation("获取标本类型饼图")
    public Result<EChartsVO> getSpecimenTypePieChart(SpecimenQueryDTO queryDTO) {
        return Result.success(specimenStatService.getSpecimenTypePieChart(queryDTO));
    }
}
