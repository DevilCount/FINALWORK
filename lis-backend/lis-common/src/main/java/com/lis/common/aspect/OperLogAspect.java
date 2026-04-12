package com.lis.common.aspect;

import com.alibaba.fastjson2.JSON;
import com.lis.common.annotation.OperLog;
import com.lis.common.model.OperLogMessage;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@Aspect
@Component
public class OperLogAspect {

    @Around("@annotation(operLog)")
    public Object around(ProceedingJoinPoint point, OperLog operLog) throws Throwable {
        long startTime = System.currentTimeMillis();
        OperLogMessage logMessage = new OperLogMessage();

        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                logMessage.setOperUrl(request.getRequestURI());
                logMessage.setRequestMethod(request.getMethod());
                logMessage.setOperIp(request.getRemoteAddr());

                String userId = request.getHeader("X-User-Id");
                String userName = request.getHeader("X-User-Name");
                if (userName != null) {
                    logMessage.setOperName(userName);
                }
            }
        } catch (Exception e) {
            log.warn("获取请求信息失败", e);
        }

        MethodSignature signature = (MethodSignature) point.getSignature();
        logMessage.setTitle(operLog.title());
        logMessage.setBusinessType(operLog.businessType());
        logMessage.setMethod(signature.getDeclaringTypeName() + "." + signature.getName());

        try {
            Object[] args = point.getArgs();
            if (args != null && args.length > 0) {
                try {
                    logMessage.setOperParam(JSON.toJSONString(args));
                } catch (Exception e) {
                    logMessage.setOperParam("参数序列化失败");
                }
            }

            Object result = point.proceed();

            try {
                if (result != null) {
                    logMessage.setOperResult(JSON.toJSONString(result));
                }
            } catch (Exception e) {
                logMessage.setOperResult("结果序列化失败");
            }

            return result;
        } finally {
            logMessage.setCostTime((int) (System.currentTimeMillis() - startTime));
            logMessage.setOperTime(LocalDateTime.now());
            log.info("操作日志: {}", JSON.toJSONString(logMessage));
        }
    }
}
