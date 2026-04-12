﻿# LIS System Startup Script (PowerShell)
# One-click start all services

param(
    [switch]$SkipBuild
)

$ErrorActionPreference = "Stop"

# Color output functions
function Write-Step($msg) { Write-Host "[STEP] $msg" -ForegroundColor Cyan }
function Write-Success($msg) { Write-Host "[OK] $msg" -ForegroundColor Green }
function Write-Error($msg) { Write-Host "[ERROR] $msg" -ForegroundColor Red }
function Write-Info($msg) { Write-Host "[INFO] $msg" -ForegroundColor Yellow }

# Service port configuration
$ports = @(8080, 8081, 8082, 8083, 8084, 8085, 8086, 8087, 8088, 8089, 8090)

Write-Host ""
Write-Host "========================================" -ForegroundColor Blue
Write-Host "    LIS Lab System - Startup Script" -ForegroundColor Blue
Write-Host "========================================" -ForegroundColor Blue
Write-Host ""

# ========================================
# Step 1: Stop existing services
# ========================================
Write-Step "Stopping existing LIS services..."

# Stop Java processes
Get-Process | Where-Object { $_.ProcessName -eq "java" } | ForEach-Object {
    try {
        Stop-Process -Id $_.Id -Force -ErrorAction SilentlyContinue
        Write-Success "Stopped Java process PID: $($_.Id)"
    } catch {}
}

# Stop Node processes (frontend)
Get-Process | Where-Object { $_.ProcessName -eq "node" } | ForEach-Object {
    try {
        Stop-Process -Id $_.Id -Force -ErrorAction SilentlyContinue
        Write-Success "Stopped Node process PID: $($_.Id)"
    } catch {}
}

# Wait for ports to release
Write-Info "Waiting for ports to release (5 seconds)..."
Start-Sleep -Seconds 5

# ========================================
# Step 2: Check infrastructure
# ========================================
Write-Step "Checking MySQL..."
$oldEAP = $ErrorActionPreference
$ErrorActionPreference = 'SilentlyContinue'
$mysqlCheck = mysql -u root -p1234 -e "SELECT 1" 2>&1
$ErrorActionPreference = $oldEAP
if ($mysqlCheck -match "1") {
    Write-Success "MySQL is ready"
} else {
    Write-Error "MySQL is not running or cannot connect"
    exit 1
}

Write-Step "Checking Redis..."
$oldEAP = $ErrorActionPreference
$ErrorActionPreference = 'SilentlyContinue'
$redisCheck = redis-cli ping 2>&1
$ErrorActionPreference = $oldEAP
if ($redisCheck -match "PONG") {
    Write-Success "Redis is ready"
} else {
    Write-Step "Starting Redis..."
    Start-Process -FilePath "redis-server" -WindowStyle Hidden
    Start-Sleep -Seconds 3
    $ErrorActionPreference = 'SilentlyContinue'
    $redisCheck2 = redis-cli ping 2>&1
    $ErrorActionPreference = $oldEAP
    if ($redisCheck2 -match "PONG") {
        Write-Success "Redis started successfully"
    } else {
        Write-Error "Redis failed to start"
        exit 1
    }
}

Write-Step "Checking Nacos..."
Start-Sleep -Seconds 2
$NACOS_PID = Get-NetTCPConnection -LocalPort 8848 -ErrorAction SilentlyContinue | Select-Object -ExpandProperty OwningProcess
if ($NACOS_PID) {
    Write-Success "Nacos is running (PID: $NACOS_PID)"
} else {
    Write-Step "Starting Nacos..."
    $nacosPath = "D:\ReStart\nacos\bin"
    if (Test-Path "$nacosPath\startup.cmd") {
        Start-Process -FilePath "cmd" -ArgumentList "/c cd /d `"$nacosPath`" && start /B startup.cmd -m standalone" -WindowStyle Hidden
        Write-Info "Waiting for Nacos to start (25 seconds)..."
        Start-Sleep -Seconds 25
        $NACOS_PID = Get-NetTCPConnection -LocalPort 8848 -ErrorAction SilentlyContinue | Select-Object -ExpandProperty OwningProcess
        if ($NACOS_PID) {
            Write-Success "Nacos started successfully (PID: $NACOS_PID)"
        } else {
            Write-Error "Nacos failed to start - check logs at D:\ReStart\nacos\logs"
            exit 1
        }
    } else {
        Write-Error "Nacos not found at $nacosPath"
        exit 1
    }
}

# ========================================
# Step 3: Compile backend
# ========================================
if (-not $SkipBuild) {
    Write-Step "Compiling backend code..."
    Set-Location lis-backend
    try {
        & mvn clean package -DskipTests -q
        if ($LASTEXITCODE -ne 0) { throw "Maven build failed" }
        Write-Success "Backend compilation completed"
    } catch {
        Write-Error "Build failed: $_"
        Set-Location ..
        exit 1
    }
    Set-Location ..
} else {
    Write-Info "Skipping build (using -SkipBuild parameter)"
}

# ========================================
# Step 4: Start backend microservices
# ========================================
Write-Step "Starting backend microservices..."

$services = @(
    @{Name="Gateway"; Port=8080; Jar="lis-gateway"},
    @{Name="Auth"; Port=8081; Jar="lis-auth"},
    @{Name="User"; Port=8082; Jar="lis-user"},
    @{Name="Specimen"; Port=8084; Jar="lis-specimen"},
    @{Name="Report"; Port=8085; Jar="lis-report"},
    @{Name="Equipment"; Port=8086; Jar="lis-equipment"},
    @{Name="Statistics"; Port=8087; Jar="lis-statistics"},
    @{Name="AI"; Port=8088; Jar="lis-ai"},
    @{Name="HL7"; Port=8089; Jar="lis-hl7"}
)

foreach ($svc in $services) {
    $jarPath = "lis-backend/$($svc.Jar)/target/$($svc.Jar)-1.0.0.jar"
    if (Test-Path $jarPath) {
        Start-Process -FilePath "java" -ArgumentList "-jar", $jarPath -WindowStyle Hidden
        Write-Success "$($svc.Name) service started"
    } else {
        Write-Error "$($svc.Name) JAR file not found: $jarPath"
    }
}

# Wait for services to initialize
Write-Info "Waiting for service initialization (15 seconds)..."
Start-Sleep -Seconds 15

# ========================================
# Step 5: Start frontend
# ========================================
Write-Step "Starting frontend dev server..."
Set-Location lis-web
Start-Process -FilePath "npm" -ArgumentList "run", "dev" -WindowStyle Hidden
Set-Location ..
Write-Success "Frontend service started"

# Wait for frontend to start
Start-Sleep -Seconds 5

# ========================================
# Step 6: Status check
# ========================================
Write-Host ""
Write-Host "========================================" -ForegroundColor Blue
Write-Host "         Service Status Check" -ForegroundColor Blue
Write-Host "========================================" -ForegroundColor Blue
Write-Host ""

$portStatus = @()
foreach ($p in $ports) {
    $conn = Get-NetTCPConnection -LocalPort $p -ErrorAction SilentlyContinue
    if ($conn) {
        $portStatus += "Port $p : Running"
    } else {
        $portStatus += "Port $p : Not Running"
    }
}

$portStatus | ForEach-Object {
    if ($_ -match "Running") {
        Write-Host $_ -ForegroundColor Green
    } else {
        Write-Host $_ -ForegroundColor Red
    }
}

Write-Host ""
Write-Host "========================================" -ForegroundColor Green
Write-Host "    All services started successfully!" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Green
Write-Host ""
Write-Host "Access URL: http://localhost:3000" -ForegroundColor Cyan
Write-Host "Login: admin / admin123" -ForegroundColor Cyan
Write-Host ""

pause
