@echo off
chcp 65001 >nul
title LIS System Stopper
echo ============================================
echo    LIS 实验室管理系统 - 停止所有服务
echo ============================================
echo.

echo [步骤1] 停止所有 LIS 相关 Java 进程...
echo    正在查找并停止 LIS 服务...

:: 使用 tasklist 查找所有 java.exe 进程
for /f "tokens=5" %%a in ('tasklist /FI "IMAGENAME eq java.exe" /NH 2^>nul') do (
    :: 尝试获取窗口标题来识别 LIS 服务
    wmic process where "name='java.exe'" get processid,commandline 2>nul | findstr /i "lis-" >nul
    if !errorlevel! equ 0 (
        echo    停止进程 PID: %%a
        taskkill /F /PID %%a >nul 2>&1
    )
)

:: 尝试按窗口标题停止
taskkill /F /FI "WINDOWTITLE eq LIS-*" >nul 2>&1
taskkill /F /FI "WINDOWTITLE eq Nacos*" >nul 2>&1
taskkill /F /FI "WINDOWTITLE eq Spring*" >nul 2>&1

echo [步骤2] 停止 Redis...
redis-cli shutdown >nul 2>&1

echo [步骤3] 等待端口释放...
timeout /t 3 /nobreak >nul

echo.
echo [检查] 端口状态:
echo    Gateway 8080: 
netstat -an | findstr ":8080 " | findstr "LISTENING" >nul && echo [占用] || echo [空闲]
echo    Auth 8081: 
netstat -an | findstr ":8081 " | findstr "LISTENING" >nul && echo [占用] || echo [空闲]
echo    User 8082: 
netstat -an | findstr ":8082 " | findstr "LISTENING" >nul && echo [占用] || echo [空闲]
echo    Specimen 8084: 
netstat -an | findstr ":8084 " | findstr "LISTENING" >nul && echo [占用] || echo [空闲]
echo    Nacos 8848: 
netstat -an | findstr ":8848 " | findstr "LISTENING" >nul && echo [占用] || echo [空闲]

echo.
echo ============================================
echo    所有服务已停止！
echo ============================================
pause