package com.lis.statistics.controller;

import com.lis.common.result.Result;
import com.lis.statistics.dto.WorkloadQueryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/statistics/specimen")
@RequiredArgsConstructor
public class SpecimenStatController {

    @GetMapping
    @SuppressWarnings("unchecked")
    public Result<List<Map<String, Object>>> getSpecimenStatistics(WorkloadQueryDTO queryDTO) {
        return Result.success(List.of());
    }

    @GetMapping("/chart")
    public Result<Map<String, Object>> getSpecimenChart(WorkloadQueryDTO queryDTO) {
        return Result.success(Map.of(
            "pieData", List.of(),
            "trendData", Map.of("categories", List.of(), "series", List.of())
        ));
    }

    @GetMapping("/export")
    public Result<byte[]> exportSpecimenReport(WorkloadQueryDTO queryDTO) {
        return Result.success(new byte[0]);
    }
}
