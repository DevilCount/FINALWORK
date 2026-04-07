package com.lis.ai.feign;

import com.lis.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "lis-specimen", path = "/specimen")
public interface SpecimenFeignClient {

    @GetMapping("/{id}")
    Result<Map<String, Object>> getSpecimenById(@PathVariable("id") Long id);

    @GetMapping("/no/{specimenNo}")
    Result<Map<String, Object>> getSpecimenByNo(@PathVariable("specimenNo") String specimenNo);
}
