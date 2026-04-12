package com.lis.auth.feign;

import com.lis.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class LogFeignClientFallbackFactory implements FallbackFactory<LogFeignClient> {

    @Override
    public LogFeignClient create(Throwable cause) {
        return new LogFeignClient() {
            @Override
            public Result<Void> saveLoginLog(Map<String, Object> loginLog) {
                log.warn("LogFeignClient.saveLoginLog调用失败: error={}", cause.getMessage());
                return Result.success(null);
            }
        };
    }
}
