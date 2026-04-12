package com.lis.hl7.feign;

import com.lis.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "lis-report", path = "", fallbackFactory = ReportFeignClientFallbackFactory.class)
public interface ReportFeignClient {

    @PostMapping("/apply")
    Result<Long> createReportApply(@RequestBody Map<String, Object> dto);
}
