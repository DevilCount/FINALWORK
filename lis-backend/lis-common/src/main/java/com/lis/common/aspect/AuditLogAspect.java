package com.lis.common.aspect;

import com.alibaba.fastjson2.JSON;
import com.lis.common.annotation.AuditLog;
import com.lis.common.model.AuditLogMessage;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@Aspect
@Component
public class AuditLogAspect {

    @Around("@annotation(auditLog)")
    public Object around(ProceedingJoinPoint point, AuditLog auditLog) throws Throwable {
        AuditLogMessage logMessage = new AuditLogMessage();

        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                String userId = request.getHeader("X-User-Id");
                String userName = request.getHeader("X-User-Name");
                if (userId != null) {
                    logMessage.setAuditUserId(Long.parseLong(userId));
                }
                if (userName != null) {
                    logMessage.setAuditUserName(userName);
                }
            }
        } catch (Exception e) {
            log.warn("获取请求信息失败", e);
        }

        logMessage.setAuditType(auditLog.auditType());
        logMessage.setAction(auditLog.action());
        logMessage.setTargetType(auditLog.targetType());
        logMessage.setAuditTime(LocalDateTime.now());

        Object result = point.proceed();

        log.info("审计日志: {}", JSON.toJSONString(logMessage));
        return result;
    }
}
