﻿# LIS System Stop Script (PowerShell)
# One-click stop all services

$ErrorActionPreference = "SilentlyContinue"

function Write-Step($msg) { Write-Host "[STEP] $msg" -ForegroundColor Cyan }
function Write-Success($msg) { Write-Host "[OK] $msg" -ForegroundColor Green }
function Write-Info($msg) { Write-Host "[INFO] $msg" -ForegroundColor Yellow }

Write-Host ""
Write-Host "========================================" -ForegroundColor Blue
Write-Host "    LIS Lab System - Stop Script" -ForegroundColor Blue
Write-Host "========================================" -ForegroundColor Blue
Write-Host ""

# Stop Java services
Write-Step "Stopping Java microservices..."
$javaProcesses = Get-Process | Where-Object { $_.ProcessName -eq "java" }
$count = 0
foreach ($proc in $javaProcesses) {
    Stop-Process -Id $proc.Id -Force
    $count++
}
Write-Success "Stopped $count Java processes"

# Stop Node processes
Write-Step "Stopping Node frontend service..."
$nodeProcesses = Get-Process | Where-Object { $_.ProcessName -eq "node" }
$count = 0
foreach ($proc in $nodeProcesses) {
    Stop-Process -Id $proc.Id -Force
    $count++
}
Write-Success "Stopped $count Node processes"

# Stop Nacos (optional)
Write-Step "Stopping Nacos..."
$nacosPid = Get-NetTCPConnection -LocalPort 8848 -ErrorAction SilentlyContinue | Select-Object -ExpandProperty OwningProcess
if ($nacosPid) {
    Stop-Process -Id $nacosPid -Force
    Write-Success "Nacos stopped"
} else {
    Write-Info "Nacos not running"
}

# Stop Redis (optional)
Write-Step "Stopping Redis..."
& redis-cli shutdown 2>$null
Write-Success "Redis stopped"

Write-Host ""
Write-Host "========================================" -ForegroundColor Green
Write-Host "    All services stopped!" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Green
Write-Host ""

pause
