package com.lis.report.controller;

import com.lis.common.result.PageResult;
import com.lis.common.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/critical-value")
@RequiredArgsConstructor
public class CriticalValueController {

    @GetMapping("/list")
    public Result<PageResult<Map<String, Object>>> getCriticalValueList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        return Result.success(new PageResult<>());
    }

    @GetMapping("/detail/{id}")
    public Result<Map<String, Object>> getCriticalValueDetail(@PathVariable String id) {
        return Result.success(Map.of());
    }

    @PostMapping("/handle")
    public Result<Map<String, Object>> handleCriticalValue(@RequestBody Map<String, Object> data) {
        return Result.success(Map.of());
    }

    @PostMapping("/notify/{id}")
    public Result<Map<String, Object>> notifyCriticalValue(@PathVariable String id, @RequestBody Map<String, String> request) {
        return Result.success(Map.of());
    }

    @GetMapping("/records/{criticalValueId}")
    public Result<List<Map<String, Object>>> getCriticalValueRecords(@PathVariable String criticalValueId) {
        return Result.success(List.of());
    }
}
