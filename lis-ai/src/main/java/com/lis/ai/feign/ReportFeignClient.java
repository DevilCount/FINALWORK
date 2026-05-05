package com.lis.ai.feign;

import com.lis.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "lis-report", path = "/report")
public interface ReportFeignClient {

    @GetMapping("/{id}")
    Result<Map<String, Object>> getReportById(@PathVariable("id") Long id);

    @GetMapping("/no/{reportNo}")
    Result<Map<String, Object>> getReportByNo(@PathVariable("reportNo") String reportNo);
}
