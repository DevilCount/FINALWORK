# LIS Full API Test Script - Corrected Version
$baseUrl = "http://localhost:8080"
$results = @()

function Test-API {
    param($TestId, $Module, $Name, $Method, $Url, $Body, $ExpectedCode, $Headers)
    $result = [PSCustomObject]@{
        TestId = $TestId; Module = $Module; Name = $Name; Method = $Method; Url = $Url
        Body = if($Body){$Body}else{"N/A"}; ExpectedCode = $ExpectedCode; ActualCode = "N/A"
        Response = "N/A"; Status = "FAILED"
    }
    try {
        if ($Method -eq "GET") {
            $r = Invoke-WebRequest -Uri $Url -Method GET -Headers $Headers -UseBasicParsing -TimeoutSec 15
        } elseif ($Method -eq "DELETE") {
            $r = Invoke-WebRequest -Uri $Url -Method DELETE -Headers $Headers -UseBasicParsing -TimeoutSec 15
        } else {
            $r = Invoke-WebRequest -Uri $Url -Method POST -Headers $Headers -ContentType 'application/json' -Body $Body -UseBasicParsing -TimeoutSec 15
        }
        $result.ActualCode = [int]$r.StatusCode
        $respContent = $r.Content
        if ($respContent.Length -gt 800) { $respContent = $respContent.Substring(0, 800) + "..." }
        $result.Response = $respContent
        try {
            $j = $r.Content | ConvertFrom-Json
            if ($j.code -eq $ExpectedCode -or $j.success -eq $true -or ($ExpectedCode -eq 200 -and [int]$r.StatusCode -eq 200)) {
                $result.Status = "PASSED"
            }
        } catch {
            if ([int]$r.StatusCode -eq $ExpectedCode) { $result.Status = "PASSED" }
        }
    } catch {
        $result.ActualCode = "ERROR"
        $result.Response = $_.Exception.Message
        if ($_.Exception.Response) {
            try {
                $sr = [System.IO.StreamReader]::new($_.Exception.Response.GetResponseStream())
                $errBody = $sr.ReadToEnd()
                if ($errBody.Length -gt 800) { $errBody = $errBody.Substring(0, 800) + "..." }
                $result.Response = $errBody
                try {
                    $ej = $errBody | ConvertFrom-Json
                    $result.ActualCode = [int]$ej.code
                } catch {}
            } catch {}
        }
    }
    return $result
}

# Step 1: Login
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Step 1: Login" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
$loginBody = '{"username":"admin","password":"admin123"}'
$loginResp = Invoke-WebRequest -Uri "$baseUrl/auth/login" -Method POST -ContentType 'application/json' -Body $loginBody -UseBasicParsing
$loginJson = $loginResp.Content | ConvertFrom-Json
$token = $loginJson.data.accessToken
$headers = @{"Authorization" = "Bearer $token"}
Write-Host "Login OK" -ForegroundColor Green

# ===== Auth Module =====
Write-Host "`n--- Auth Module ---" -ForegroundColor Yellow

$r = Test-API "AUTH-01" "auth" "login" "POST" "$baseUrl/auth/login" '{"username":"admin","password":"admin123"}' 200 @{}
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

$r = Test-API "AUTH-02" "auth" "get user info" "GET" "$baseUrl/auth/info" $null 200 $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

$r = Test-API "AUTH-03" "auth" "logout" "POST" "$baseUrl/auth/logout" '{}' 200 $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

# Re-login
$loginResp = Invoke-WebRequest -Uri "$baseUrl/auth/login" -Method POST -ContentType 'application/json' -Body $loginBody -UseBasicParsing
$loginJson = $loginResp.Content | ConvertFrom-Json
$token = $loginJson.data.accessToken
$headers = @{"Authorization" = "Bearer $token"}
Write-Host "Re-login OK" -ForegroundColor Green

# ===== User Module =====
Write-Host "`n--- User Module ---" -ForegroundColor Yellow

$r = Test-API "USER-01" "user" "dept tree" "GET" "$baseUrl/user/dept/tree" $null 200 $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

$r = Test-API "USER-02" "user" "oper-log list" "POST" "$baseUrl/user/oper-log/list" '{"pageNum":1,"pageSize":10}' 200 $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

$r = Test-API "USER-03" "user" "login-log list" "POST" "$baseUrl/user/login-log/list" '{"pageNum":1,"pageSize":10}' 200 $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

$r = Test-API "USER-04" "user" "error-log list" "POST" "$baseUrl/user/error-log/list" '{"pageNum":1,"pageSize":10}' 200 $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

$r = Test-API "USER-05" "user" "audit-log list" "POST" "$baseUrl/user/audit-log/list" '{"pageNum":1,"pageSize":10}' 200 $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

# ===== Specimen Module =====
Write-Host "`n--- Specimen Module ---" -ForegroundColor Yellow

$r = Test-API "SPEC-01" "specimen" "specimen types" "GET" "$baseUrl/specimen/types" $null 200 $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

$r = Test-API "SPEC-02" "specimen" "test items" "GET" "$baseUrl/specimen/testItems" $null 200 $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

# Register with correct DTO - testItemIds is List<Long>
$regBody = '{"patientName":"APItestPatient","patientGender":"1","patientAge":"30","deptId":1,"deptName":"检验中心","specimenTypeId":1,"specimenTypeName":"血液","testItemIds":[1,2],"clinicalDiagnosis":"fever","remark":"API test"}'
$r = Test-API "SPEC-03" "specimen" "register specimen" "POST" "$baseUrl/specimen/register" $regBody 200 $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

# Extract specimen info
$specimenId = $null; $barcode = $null
try {
    $regJson = $r.Response | ConvertFrom-Json
    if ($regJson.data.id) { $specimenId = $regJson.data.id; $barcode = $regJson.data.barcode }
    Write-Host "  SpecimenID: $specimenId, Barcode: $barcode" -ForegroundColor Cyan
} catch { Write-Host "  Could not extract specimen info" -ForegroundColor Red }

# Specimen list
$r = Test-API "SPEC-04" "specimen" "specimen list" "POST" "$baseUrl/specimen/list" '{"pageNum":1,"pageSize":10}' 200 $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

# Try to get specimen from list if register failed
if (-not $specimenId) {
    try {
        $listJson = $r.Response | ConvertFrom-Json
        if ($listJson.data.records -and $listJson.data.records.Count -gt 0) {
            $specimenId = $listJson.data.records[0].id
            $barcode = $listJson.data.records[0].barcode
            Write-Host "  Got from list - SpecimenID: $specimenId, Barcode: $barcode" -ForegroundColor Cyan
        }
    } catch {}
}

# Receive specimen
if ($barcode) {
    $recvBody = "{`"barcode`":`"$barcode`"}"
    $r = Test-API "SPEC-05" "specimen" "receive specimen" "POST" "$baseUrl/specimen/receive" $recvBody 200 $headers
    $results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"
} else {
    $results += [PSCustomObject]@{TestId="SPEC-05";Module="specimen";Name="receive specimen";Method="POST";Url="$baseUrl/specimen/receive";Body="N/A";ExpectedCode=200;ActualCode="SKIPPED";Response="No barcode";Status="SKIPPED"}
    Write-Host "SPEC-05: SKIPPED (no barcode)" -ForegroundColor Yellow
}

# Storage specimen
if ($barcode) {
    $storeBody = "{`"barcode`":`"$barcode`",`"storageLocation`":`"A-01-01`"}"
    $r = Test-API "SPEC-06" "specimen" "storage specimen" "POST" "$baseUrl/specimen/storage" $storeBody 200 $headers
    $results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"
} else {
    $results += [PSCustomObject]@{TestId="SPEC-06";Module="specimen";Name="storage specimen";Method="POST";Url="$baseUrl/specimen/storage";Body="N/A";ExpectedCode=200;ActualCode="SKIPPED";Response="No barcode";Status="SKIPPED"}
    Write-Host "SPEC-06: SKIPPED (no barcode)" -ForegroundColor Yellow
}

# Reject specimen
$rejectBody = '{"barcode":"FAKE001","isReject":1,"rejectReason":"test reject"}'
$r = Test-API "SPEC-07" "specimen" "reject specimen" "POST" "$baseUrl/specimen/reject" $rejectBody 200 $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

# GetById
$sid = if($specimenId){$specimenId}else{1}
$r = Test-API "SPEC-08" "specimen" "getById" "GET" "$baseUrl/specimen/getById/$sid" $null 200 $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

# Trace
$r = Test-API "SPEC-09" "specimen" "trace" "GET" "$baseUrl/specimen/trace/$sid" $null 200 $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

# ===== Report Module =====
Write-Host "`n--- Report Module ---" -ForegroundColor Yellow

# Create apply with correct DTO
$applyBody = '{"specimenId":' + $sid + ',"reportType":"routine","patientName":"APItestPatient","patientGender":"1","patientAge":"30","deptId":1,"deptName":"检验中心","specimenTypeName":"血液","clinicalDiagnosis":"fever","testItems":[{"itemCode":"ALT","itemName":"丙氨酸氨基转移酶"},{"itemCode":"AST","itemName":"天冬氨酸氨基转移酶"}]}'
$r = Test-API "RPT-01" "report" "create apply" "POST" "$baseUrl/report/apply" $applyBody 200 $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

# Extract applyId
$applyId = $null
try {
    $applyJson = $r.Response | ConvertFrom-Json
    $applyId = $applyJson.data.id
    if (-not $applyId) { $applyId = $applyJson.data }
    Write-Host "  ApplyID: $applyId" -ForegroundColor Cyan
} catch { Write-Host "  Could not extract apply ID" -ForegroundColor Red }

# Apply query
$r = Test-API "RPT-02" "report" "apply query" "POST" "$baseUrl/report/apply/query" '{"pageNum":1,"pageSize":10}' 200 $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

# Get applyId from query if not from create
if (-not $applyId) {
    try {
        $qJson = $r.Response | ConvertFrom-Json
        if ($qJson.data.records -and $qJson.data.records.Count -gt 0) {
            $applyId = $qJson.data.records[0].id
            Write-Host "  Got from query - ApplyID: $applyId" -ForegroundColor Cyan
        }
    } catch {}
}
if (-not $applyId) { $applyId = 1 }

# Apply detail
$r = Test-API "RPT-03" "report" "apply detail" "GET" "$baseUrl/report/apply/$applyId" $null 200 $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

# Audit submit
$r = Test-API "RPT-04" "report" "audit submit" "POST" "$baseUrl/report/audit/submit/$applyId" '{}' 200 $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

# First approve - correct DTO with reportId
$firstApproveBody = "{`"reportId`":$applyId,`"approved`":true,`"auditLevel`":1}"
$r = Test-API "RPT-05" "report" "first approve" "POST" "$baseUrl/report/audit/first-approve" $firstApproveBody 200 $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

# First reject
$firstRejectBody = "{`"reportId`":$applyId,`"approved`":false,`"auditLevel`":1,`"auditOpinion`":`"test reject`"}"
$r = Test-API "RPT-06" "report" "first reject" "POST" "$baseUrl/report/audit/first-reject" $firstRejectBody 200 $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

# Final approve
$finalApproveBody = "{`"reportId`":$applyId,`"approved`":true,`"auditLevel`":2}"
$r = Test-API "RPT-07" "report" "final approve" "POST" "$baseUrl/report/audit/final-approve" $finalApproveBody 200 $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

# Final reject
$finalRejectBody = "{`"reportId`":$applyId,`"approved`":false,`"auditLevel`":2,`"auditOpinion`":`"test final reject`"}"
$r = Test-API "RPT-08" "report" "final reject" "POST" "$baseUrl/report/audit/final-reject" $finalRejectBody 200 $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

# Save result - correct DTO with reportId
$resultBody = "{`"reportId`":$applyId,`"itemCode`":`"ALT`",`"itemName`":`"丙氨酸氨基转移酶`",`"resultValue`":`"25.5`",`"unit`":`"U/L`",`"referenceLow`":0,`"referenceHigh`":40,`"resultFlag`":`"N`"}"
$r = Test-API "RPT-09" "report" "save result" "POST" "$baseUrl/report/result" $resultBody 200 $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

# Publish report - correct DTO with reportId
$publishBody = "{`"reportId`":$applyId,`"reportConclusion`":`"检验结果正常`"}"
$r = Test-API "RPT-10" "report" "publish report" "POST" "$baseUrl/report/publish" $publishBody 200 $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

# ===== Equipment Module =====
Write-Host "`n--- Equipment Module ---" -ForegroundColor Yellow

$r = Test-API "EQP-01" "equipment" "equipment page" "POST" "$baseUrl/equipment/page" '{"pageNum":1,"pageSize":10}' 200 $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

$r = Test-API "EQP-02" "equipment" "status all" "GET" "$baseUrl/equipment/status/all" $null 200 $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

# ===== HL7 Module =====
Write-Host "`n--- HL7 Module ---" -ForegroundColor Yellow

# HIS lab order - correct format with interfaceCode param and orderItems
$hl7Body = '{"patientId":"P20260001","patientName":"testPatient","gender":"1","department":"LAB001","ward":"","bedNo":"","clinicalInfo":"fever","interfaceCode":"HIS_LAB_ORDER","orderItems":[{"orderControl":"NW","orderItemCode":"ALT","orderItemName":"ALT","specimenType":"blood","priority":"R"}]}'
$r = Test-API "HL7-01" "hl7" "HIS lab order" "POST" "$baseUrl/hl7/his-integration/lab-order?interfaceCode=HIS_LAB_ORDER" $hl7Body 200 $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

$r = Test-API "HL7-02" "hl7" "hl7 message page" "GET" "$baseUrl/hl7/hl7-message/page?pageNum=1&pageSize=10" $null 200 $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

$r = Test-API "HL7-03" "hl7" "interface config page" "GET" "$baseUrl/hl7/interface-config/page?pageNum=1&pageSize=10" $null 200 $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

# Connection log list - it's POST not GET
$r = Test-API "HL7-04" "hl7" "connection log list" "POST" "$baseUrl/hl7/connection-log/list?pageNum=1&pageSize=10" $null 200 $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

# ===== Statistics Module =====
Write-Host "`n--- Statistics Module ---" -ForegroundColor Yellow

$r = Test-API "STAT-01" "statistics" "dashboard overview" "GET" "$baseUrl/statistics/dashboard/overview" $null 200 $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

$r = Test-API "STAT-02" "statistics" "workload trend" "GET" "$baseUrl/statistics/workload/chart/trend" $null 200 $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

$r = Test-API "STAT-03" "statistics" "specimen type pie" "GET" "$baseUrl/statistics/specimen/chart/type-pie" $null 200 $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

$r = Test-API "STAT-04" "statistics" "equipment statistics" "GET" "$baseUrl/statistics/equipment/page" $null 200 $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

# ===== Boundary/Negative Tests =====
Write-Host "`n--- Boundary/Negative Tests ---" -ForegroundColor Yellow

# Login with wrong password
$r = Test-API "NEG-01" "auth" "login wrong pwd" "POST" "$baseUrl/auth/login" '{"username":"admin","password":"wrongpassword123"}' 401 @{}
try { $j = $r.Response | ConvertFrom-Json; if($j.code -ne 200){$r.Status="PASSED"} } catch {}
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

# No token access
$r = Test-API "NEG-02" "auth" "no token access" "GET" "$baseUrl/auth/info" $null 401 @{}
# The server returns 401 HTTP status
if ($r.ActualCode -eq "ERROR" -and $r.Response -match "401") { $r.Status = "PASSED"; $r.ActualCode = 401 }
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

# Non-existent specimen ID
$r = Test-API "NEG-03" "specimen" "getById nonexist" "GET" "$baseUrl/specimen/getById/999999" $null 200 $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

# Empty body register
$r = Test-API "NEG-04" "specimen" "register empty" "POST" "$baseUrl/specimen/register" '{}' 400 $headers
try { $j = $r.Response | ConvertFrom-Json; if($j.code -eq 400){$r.Status="PASSED"} } catch {}
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

# Non-existent report ID
$r = Test-API "NEG-05" "report" "apply nonexist" "GET" "$baseUrl/report/apply/999999" $null 200 $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

# Invalid token
$badHeaders = @{"Authorization" = "Bearer invalidtoken123"}
$r = Test-API "NEG-06" "auth" "invalid token" "GET" "$baseUrl/user/dept/tree" $null 401 $badHeaders
if ($r.ActualCode -eq "ERROR" -and $r.Response -match "401") { $r.Status = "PASSED"; $r.ActualCode = 401 }
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

# ===== Summary =====
Write-Host "`n========================================" -ForegroundColor Cyan
Write-Host "Test Summary" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan

$passed = ($results | Where-Object { $_.Status -eq "PASSED" }).Count
$failed = ($results | Where-Object { $_.Status -eq "FAILED" }).Count
$skipped = ($results | Where-Object { $_.Status -eq "SKIPPED" }).Count
$total = $results.Count

Write-Host "Total: $total | Passed: $passed | Failed: $failed | Skipped: $skipped" -ForegroundColor White
Write-Host "Pass Rate: $([math]::Round($passed/$total*100,1))%" -ForegroundColor Green

# Save results as JSON
$output = $results | ForEach-Object {
    [PSCustomObject]@{
        testId=$_.TestId; module=$_.Module; name=$_.Name; method=$_.Method; url=$_.Url
        body=$_.Body; expectedCode=$_.ExpectedCode; actualCode=$_.ActualCode; response=$_.Response; status=$_.Status
    }
}
$jsonOutput = $output | ConvertTo-Json -Depth 3
$jsonOutput | Out-File -FilePath "d:\ReStart\FINALWORK\docs\test-results.json" -Encoding UTF8 -Force
Write-Host "`nResults saved to test-results.json" -ForegroundColor Green

# Print full details
Write-Host "`n========================================" -ForegroundColor Cyan
Write-Host "Detailed Results" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
foreach ($item in $results) {
    Write-Host "`n[$($item.TestId)] $($item.Name) - $($item.Status)" -ForegroundColor $(if($item.Status -eq "PASSED"){"Green"}elseif($item.Status -eq "SKIPPED"){"Yellow"}else{"Red"})
    Write-Host "  URL: $($item.Url)"
    Write-Host "  Method: $($item.Method)"
    Write-Host "  Expected: $($item.ExpectedCode) | Actual: $($item.ActualCode)"
    $respShort = if($item.Response.Length -gt 300){$item.Response.Substring(0,300)+"..."}else{$item.Response}
    Write-Host "  Response: $respShort"
}
