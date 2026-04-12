package com.lis.common.handler;

import com.alibaba.fastjson2.JSON;
import com.lis.common.model.ErrorLogMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;

@Slf4j
@Component
public class ErrorLogHandler {

    public void handleError(Exception ex, String className, String methodName,
                            String requestUrl, String requestMethod, String requestParam,
                            Long userId, String userName, String ip) {
        ErrorLogMessage errorLog = new ErrorLogMessage();
        errorLog.setClassName(className);
        errorLog.setMethodName(methodName);
        errorLog.setRequestUrl(requestUrl);
        errorLog.setRequestMethod(requestMethod);
        errorLog.setRequestParam(requestParam);
        errorLog.setUserId(userId);
        errorLog.setUserName(userName);
        errorLog.setIp(ip);
        errorLog.setErrorMsg(ex.getMessage());

        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        errorLog.setErrorStack(sw.toString());
        errorLog.setErrorTime(LocalDateTime.now());

        log.error("错误日志处理器: {}", JSON.toJSONString(errorLog), ex);
    }
}
