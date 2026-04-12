package com.lis.auth.feign;

import com.lis.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "lis-user", contextId = "logFeignClient", path = "/user", fallbackFactory = LogFeignClientFallbackFactory.class)
public interface LogFeignClient {

    @PostMapping("/login-log/save")
    Result<Void> saveLoginLog(@RequestBody Map<String, Object> loginLog);
}
