@echo off
chcp 65001 >nul
title LIS System Launcher
echo ============================================
echo    LIS 实验室管理系统 - 一键启动脚本
echo ============================================
echo.

set NACOS_HOME=D:\ReStart\nacos
set PROJECT_HOME=D:\ReStart\FINALWORK\lis-backend

echo [步骤1] 停止现有服务...
call "%~dp0stop-all.cmd"

echo.
echo [步骤2] 检查 MySQL...
mysql --version >nul 2>&1
if %errorlevel% neq 0 (
    echo [错误] MySQL 未找到，请确认已加入环境变量
    pause
    exit /b 1
)
echo [OK] MySQL 已就绪

echo.
echo [步骤3] 检查 Redis...
redis-cli ping >nul 2>&1
if %errorlevel% neq 0 (
    echo [启动] 正在启动 Redis...
    start "Redis" redis-server
    timeout /t 3 /nobreak >nul
    redis-cli ping >nul 2>&1
    if %errorlevel% neq 0 (
        echo [错误] Redis 启动失败
        pause
        exit /b 1
    )
)
echo [OK] Redis 已就绪

echo.
echo [步骤4] 检查 Nacos...
netstat -an | findstr "8848" >nul
if %errorlevel% neq 0 (
    echo [启动] 正在启动 Nacos...
    start "Nacos Server" cmd /c "cd /d %NACOS_HOME%\bin && startup.cmd -m standalone"
    echo [等待] Nacos 启动中...
    timeout /t 15 /nobreak >nul
    echo [OK] Nacos 已启动 (端口 8848)
) else (
    echo [OK] Nacos 已在运行
)

echo.
echo [步骤5] 编译后端项目...
cd /d %PROJECT_HOME%
call mvn compile -DskipTests -q
if %errorlevel% neq 0 (
    echo [错误] Maven 编译失败
    pause
    exit /b 1
)
echo [OK] 编译成功

echo.
echo [步骤6] 启动微服务...
echo [启动] Gateway (8080) - 等待5秒...
start "LIS-Gateway" cmd /c "cd /d %PROJECT_HOME%\lis-gateway && mvn spring-boot:run -Dspring-boot.run.jvmArguments=-Dfile.encoding=UTF-8"

timeout /t 5 /nobreak >nul

echo [启动] Auth (8081) - 等待5秒...
start "LIS-Auth" cmd /c "cd /d %PROJECT_HOME%\lis-auth && mvn spring-boot:run -Dspring-boot.run.jvmArguments=-Dfile.encoding=UTF-8"

timeout /t 5 /nobreak >nul

echo [启动] User (8082) - 等待8秒...
start "LIS-User" cmd /c "cd /d %PROJECT_HOME%\lis-user && mvn spring-boot:run -Dspring-boot.run.jvmArguments=-Dfile.encoding=UTF-8"

timeout /t 8 /nobreak >nul

echo [启动] Report (8083)...
start "LIS-Report" cmd /c "cd /d %PROJECT_HOME%\lis-report && mvn spring-boot:run -Dspring-boot.run.jvmArguments=-Dfile.encoding=UTF-8"

timeout /t 3 /nobreak >nul

echo [启动] Specimen (8084)...
start "LIS-Specimen" cmd /c "cd /d %PROJECT_HOME%\lis-specimen && mvn spring-boot:run -Dspring-boot.run.jvmArguments=-Dfile.encoding=UTF-8"

timeout /t 3 /nobreak >nul

echo [启动] Equipment (8085)...
start "LIS-Equipment" cmd /c "cd /d %PROJECT_HOME%\lis-equipment && mvn spring-boot:run -Dspring-boot.run.jvmArguments=-Dfile.encoding=UTF-8"

timeout /t 3 /nobreak >nul

echo [启动] HL7 (8086)...
start "LIS-HL7" cmd /c "cd /d %PROJECT_HOME%\lis-hl7 && mvn spring-boot:run -Dspring-boot.run.jvmArguments=-Dfile.encoding=UTF-8"

timeout /t 3 /nobreak >nul

echo [启动] Statistics (8088)...
start "LIS-Statistics" cmd /c "cd /d %PROJECT_HOME%\lis-statistics && mvn spring-boot:run -Dspring-boot.run.jvmArguments=-Dfile.encoding=UTF-8"

timeout /t 3 /nobreak >nul

echo [启动] AI (8090)...
start "LIS-AI" cmd /c "cd /d %PROJECT_HOME%\lis-ai && mvn spring-boot:run -Dspring-boot.run.jvmArguments=-Dfile.encoding=UTF-8"

echo.
echo ============================================
echo    所有服务已启动！
echo ============================================
echo.
echo    Nacos:       http://localhost:8848/nacos
echo    Gateway:     http://localhost:8080
echo    Auth:        http://localhost:8081/auth
echo    User:        http://localhost:8082/user
echo    Specimen:    http://localhost:8084/specimen
echo    Report:      http://localhost:8083/report
echo    Equipment:   http://localhost:8085/equipment
echo    HL7:         http://localhost:8086/hl7
echo    Statistics:  http://localhost:8088/statistics
echo    AI:          http://localhost:8090/ai
echo.
echo    前端:        http://localhost:3000
echo    登录账号:    admin / admin123
echo.
echo    等待约45秒后所有服务启动完毕即可访问
echo ============================================
pause
