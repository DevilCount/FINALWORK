package com.lis.user.feign;

import com.lis.common.result.Result;
import com.lis.user.entity.AuditLogDO;
import com.lis.user.entity.ErrorLogDO;
import com.lis.user.entity.LoginLogDO;
import com.lis.user.entity.OperLogDO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "lis-user", contextId = "logFeignClient", path = "/user")
public interface LogFeignClient {

    @PostMapping("/oper-log/save")
    Result<Void> saveOperLog(@RequestBody OperLogDO operLogDO);

    @PostMapping("/login-log/save")
    Result<Void> saveLoginLog(@RequestBody LoginLogDO loginLogDO);

    @PostMapping("/error-log/save")
    Result<Void> saveErrorLog(@RequestBody ErrorLogDO errorLogDO);

    @PostMapping("/audit-log/save")
    Result<Void> saveAuditLog(@RequestBody AuditLogDO auditLogDO);
}
