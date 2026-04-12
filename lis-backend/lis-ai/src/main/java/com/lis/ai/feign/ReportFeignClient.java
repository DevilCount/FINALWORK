package com.lis.ai.feign;

import com.lis.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "lis-report", path = "/report")
public interface ReportFeignClient {

    @GetMapping("/apply/{reportId}")
    Result<Map<String, Object>> getReportById(@PathVariable("reportId") Long id);
}
