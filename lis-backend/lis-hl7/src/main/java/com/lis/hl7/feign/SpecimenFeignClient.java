package com.lis.hl7.feign;

import com.lis.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "lis-specimen", path = "/specimen", fallbackFactory = SpecimenFeignClientFallbackFactory.class)
public interface SpecimenFeignClient {

    @PostMapping("/register")
    Result<Map<String, Object>> register(@RequestBody Map<String, Object> dto);
}
