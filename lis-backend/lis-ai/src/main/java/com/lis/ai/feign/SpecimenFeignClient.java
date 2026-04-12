package com.lis.ai.feign;

import com.lis.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "lis-specimen", path = "/specimen")
public interface SpecimenFeignClient {

    @GetMapping("/getById/{id}")
    Result<Map<String, Object>> getSpecimenById(@PathVariable("id") Long id);

    @GetMapping("/getBySpecimenNo")
    Result<Map<String, Object>> getSpecimenByNo(@RequestParam("specimenNo") String specimenNo);
}
