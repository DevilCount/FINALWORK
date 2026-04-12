package com.lis.hl7.feign;

import com.lis.common.exception.BusinessException;
import com.lis.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class ReportFeignClientFallbackFactory implements FallbackFactory<ReportFeignClient> {

    @Override
    public ReportFeignClient create(Throwable cause) {
        return new ReportFeignClient() {
            @Override
            public Result<Long> createReportApply(Map<String, Object> dto) {
                log.error("ReportFeignClient.createReportApply调用失败: error={}", cause.getMessage());
                throw new BusinessException("报告服务不可用，请稍后重试");
            }
        };
    }
}
