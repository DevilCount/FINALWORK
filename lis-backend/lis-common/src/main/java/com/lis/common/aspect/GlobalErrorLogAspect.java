package com.lis.common.aspect;

import com.alibaba.fastjson2.JSON;
import com.lis.common.model.ErrorLogMessage;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;

@Slf4j
@Aspect
@Component
public class GlobalErrorLogAspect {

    @AfterThrowing(pointcut = "execution(* com.lis..controller..*.*(..))", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Exception ex) {
        ErrorLogMessage errorLog = new ErrorLogMessage();

        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                errorLog.setRequestUrl(request.getRequestURI());
                errorLog.setRequestMethod(request.getMethod());
                errorLog.setIp(request.getRemoteAddr());

                String userId = request.getHeader("X-User-Id");
                String userName = request.getHeader("X-User-Name");
                if (userId != null) {
                    errorLog.setUserId(Long.parseLong(userId));
                }
                if (userName != null) {
                    errorLog.setUserName(userName);
                }
            }
        } catch (Exception e) {
            log.warn("获取请求信息失败", e);
        }

        errorLog.setClassName(joinPoint.getTarget().getClass().getName());
        errorLog.setMethodName(joinPoint.getSignature().getName());
        errorLog.setErrorMsg(ex.getMessage());

        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        errorLog.setErrorStack(sw.toString());
        errorLog.setErrorTime(LocalDateTime.now());

        log.error("全局错误日志: {}", JSON.toJSONString(errorLog), ex);
    }
}
