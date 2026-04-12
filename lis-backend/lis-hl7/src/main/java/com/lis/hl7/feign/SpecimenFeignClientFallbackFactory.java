package com.lis.hl7.feign;

import com.lis.common.exception.BusinessException;
import com.lis.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class SpecimenFeignClientFallbackFactory implements FallbackFactory<SpecimenFeignClient> {

    @Override
    public SpecimenFeignClient create(Throwable cause) {
        return new SpecimenFeignClient() {
            @Override
            public Result<Map<String, Object>> register(Map<String, Object> dto) {
                log.error("SpecimenFeignClient.register调用失败: error={}", cause.getMessage());
                throw new BusinessException("标本服务不可用，请稍后重试");
            }
        };
    }
}
