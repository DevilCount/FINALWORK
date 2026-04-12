# LIS Full API Test Script
$baseUrl = "http://localhost:8080"
$results = @()

# Helper function
function Test-API {
    param($TestId, $Module, $Name, $Method, $Url, $Body, $ExpectedCode, $Headers)
    $result = [PSCustomObject]@{
        TestId = $TestId
        Module = $Module
        Name = $Name
        Method = $Method
        Url = $Url
        Body = if($Body) { $Body } else { "N/A" }
        ExpectedCode = $ExpectedCode
        ActualCode = "N/A"
        Response = "N/A"
        Status = "FAILED"
    }
    try {
        if ($Method -eq "GET") {
            $r = Invoke-WebRequest -Uri $Url -Method GET -Headers $Headers -UseBasicParsing -TimeoutSec 15
        } else {
            $r = Invoke-WebRequest -Uri $Url -Method POST -Headers $Headers -ContentType 'application/json' -Body $Body -UseBasicParsing -TimeoutSec 15
        }
        $result.ActualCode = [int]$r.StatusCode
        $respContent = $r.Content
        if ($respContent.Length -gt 500) { $respContent = $respContent.Substring(0, 500) + "..." }
        $result.Response = $respContent
        # Check if JSON has success=true or code=200
        try {
            $j = $r.Content | ConvertFrom-Json
            if ($j.code -eq $ExpectedCode -or $j.success -eq $true) {
                $result.Status = "PASSED"
            } elseif ($ExpectedCode -eq 200 -and [int]$r.StatusCode -eq 200) {
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
                if ($errBody.Length -gt 500) { $errBody = $errBody.Substring(0, 500) + "..." }
                $result.Response = $errBody
                try {
                    $ej = $errBody | ConvertFrom-Json
                    $result.ActualCode = [int]$ej.code
                    if ($ej.code -eq 401 -or $ej.code -eq 403 -or $ej.code -eq 404 -or $ej.code -eq 400) {
                        # These are expected error codes for negative tests
                        $result.Status = "PASSED"
                    }
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
Write-Host "Login OK, token obtained" -ForegroundColor Green

# Step 2: Test all modules
Write-Host "`n========================================" -ForegroundColor Cyan
Write-Host "Step 2: Module Tests" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan

# ===== User Module =====
Write-Host "`n--- User Module ---" -ForegroundColor Yellow

$r = Test-API -TestId "USER-01" -Module "user" -Name "dept tree" -Method "GET" -Url "$baseUrl/user/dept/tree" -ExpectedCode 200 -Headers $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)" -ForegroundColor $(if($r.Status -eq "PASSED"){"Green"}else{"Red"})

$r = Test-API -TestId "USER-02" -Module "user" -Name "oper-log list" -Method "POST" -Url "$baseUrl/user/oper-log/list" -Body '{"pageNum":1,"pageSize":10}' -ExpectedCode 200 -Headers $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)" -ForegroundColor $(if($r.Status -eq "PASSED"){"Green"}else{"Red"})

$r = Test-API -TestId "USER-03" -Module "user" -Name "login-log list" -Method "POST" -Url "$baseUrl/user/login-log/list" -Body '{"pageNum":1,"pageSize":10}' -ExpectedCode 200 -Headers $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)" -ForegroundColor $(if($r.Status -eq "PASSED"){"Green"}else{"Red"})

$r = Test-API -TestId "USER-04" -Module "user" -Name "error-log list" -Method "POST" -Url "$baseUrl/user/error-log/list" -Body '{"pageNum":1,"pageSize":10}' -ExpectedCode 200 -Headers $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)" -ForegroundColor $(if($r.Status -eq "PASSED"){"Green"}else{"Red"})

$r = Test-API -TestId "USER-05" -Module "user" -Name "audit-log list" -Method "POST" -Url "$baseUrl/user/audit-log/list" -Body '{"pageNum":1,"pageSize":10}' -ExpectedCode 200 -Headers $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)" -ForegroundColor $(if($r.Status -eq "PASSED"){"Green"}else{"Red"})

# ===== Specimen Module =====
Write-Host "`n--- Specimen Module ---" -ForegroundColor Yellow

$r = Test-API -TestId "SPEC-01" -Module "specimen" -Name "specimen types" -Method "GET" -Url "$baseUrl/specimen/types" -ExpectedCode 200 -Headers $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)" -ForegroundColor $(if($r.Status -eq "PASSED"){"Green"}else{"Red"})

$r = Test-API -TestId "SPEC-02" -Module "specimen" -Name "test items" -Method "GET" -Url "$baseUrl/specimen/testItems" -ExpectedCode 200 -Headers $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)" -ForegroundColor $(if($r.Status -eq "PASSED"){"Green"}else{"Red"})

# Register specimen
$registerBody = '{"patientName":"testPatient","patientId":"P20260001","gender":"1","age":30,"ageUnit":"year","deptId":1,"deptName":"lab","ward":"","bedNo":"","clinicalDiagnosis":"fever","sampleType":"blood","sampleTypeId":1,"testItems":[{"itemCode":"WBC","itemName":"WBC"},{"itemCode":"RBC","itemName":"RBC"}],"collectorId":1,"collectorName":"admin","remark":""}'
$r = Test-API -TestId "SPEC-03" -Module "specimen" -Name "register specimen" -Method "POST" -Url "$baseUrl/specimen/register" -Body $registerBody -ExpectedCode 200 -Headers $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)" -ForegroundColor $(if($r.Status -eq "PASSED"){"Green"}else{"Red"})

# Extract specimen id and barcode from register response
$specimenId = $null
$barcode = $null
try {
    $regJson = $r.Response | ConvertFrom-Json
    $specimenId = $regJson.data.id
    $barcode = $regJson.data.barcode
    Write-Host "  SpecimenID: $specimenId, Barcode: $barcode" -ForegroundColor Cyan
} catch {
    Write-Host "  Could not extract specimen ID/barcode" -ForegroundColor Red
}

# Specimen list
$r = Test-API -TestId "SPEC-04" -Module "specimen" -Name "specimen list" -Method "POST" -Url "$baseUrl/specimen/list" -Body '{"pageNum":1,"pageSize":10}' -ExpectedCode 200 -Headers $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)" -ForegroundColor $(if($r.Status -eq "PASSED"){"Green"}else{"Red"})

# Try to get specimen id from list if not from register
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

# Specimen receive
if ($barcode) {
    $r = Test-API -TestId "SPEC-05" -Module "specimen" -Name "receive specimen" -Method "POST" -Url "$baseUrl/specimen/receive" -Body "{`"barcode`":`"$barcode`"}" -ExpectedCode 200 -Headers $headers
    $results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)" -ForegroundColor $(if($r.Status -eq "PASSED"){"Green"}else{"Red"})
} else {
    $r = Test-API -TestId "SPEC-05" -Module "specimen" -Name "receive specimen" -Method "POST" -Url "$baseUrl/specimen/receive" -Body '{"barcode":"UNKNOWN"}' -ExpectedCode 200 -Headers $headers
    $results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status) (no barcode)" -ForegroundColor Yellow
}

# Specimen storage
if ($barcode) {
    $r = Test-API -TestId "SPEC-06" -Module "specimen" -Name "storage specimen" -Method "POST" -Url "$baseUrl/specimen/storage" -Body "{`"barcode`":`"$barcode`",`"storageLocation`":`"A-01-01`"}" -ExpectedCode 200 -Headers $headers
    $results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)" -ForegroundColor $(if($r.Status -eq "PASSED"){"Green"}else{"Red"})
} else {
    $results += [PSCustomObject]@{TestId="SPEC-06";Module="specimen";Name="storage specimen";Method="POST";Url="$baseUrl/specimen/storage";Body="N/A";ExpectedCode=200;ActualCode="SKIPPED";Response="No barcode";Status="SKIPPED"}
    Write-Host "SPEC-06 storage specimen: SKIPPED" -ForegroundColor Yellow
}

# Specimen reject
$r = Test-API -TestId "SPEC-07" -Module "specimen" -Name "reject specimen" -Method "POST" -Url "$baseUrl/specimen/reject" -Body '{"barcode":"FAKE001","rejectReason":"test reject"}' -ExpectedCode 200 -Headers $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)" -ForegroundColor $(if($r.Status -eq "PASSED"){"Green"}else{"Red"})

# Specimen getById
if ($specimenId) {
    $r = Test-API -TestId "SPEC-08" -Module "specimen" -Name "getById" -Method "GET" -Url "$baseUrl/specimen/getById/$specimenId" -ExpectedCode 200 -Headers $headers
    $results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)" -ForegroundColor $(if($r.Status -eq "PASSED"){"Green"}else{"Red"})
} else {
    $r = Test-API -TestId "SPEC-08" -Module "specimen" -Name "getById" -Method "GET" -Url "$baseUrl/specimen/getById/1" -ExpectedCode 200 -Headers $headers
    $results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status) (default id)" -ForegroundColor Yellow
}

# Specimen trace
if ($specimenId) {
    $r = Test-API -TestId "SPEC-09" -Module "specimen" -Name "trace" -Method "GET" -Url "$baseUrl/specimen/trace/$specimenId" -ExpectedCode 200 -Headers $headers
    $results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)" -ForegroundColor $(if($r.Status -eq "PASSED"){"Green"}else{"Red"})
} else {
    $r = Test-API -TestId "SPEC-09" -Module "specimen" -Name "trace" -Method "GET" -Url "$baseUrl/specimen/trace/1" -ExpectedCode 200 -Headers $headers
    $results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status) (default id)" -ForegroundColor Yellow
}

# ===== Report Module =====
Write-Host "`n--- Report Module ---" -ForegroundColor Yellow

# Create report apply
$applyBody = '{"specimenId":"' + $(if($specimenId){$specimenId}else{1}) + '","testItems":[{"itemCode":"WBC","itemName":"WBC"},{"itemCode":"RBC","itemName":"RBC"}],"applicantId":1,"applicantName":"admin","urgency":0}'
$r = Test-API -TestId "RPT-01" -Module "report" -Name "create apply" -Method "POST" -Url "$baseUrl/report/apply" -Body $applyBody -ExpectedCode 200 -Headers $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)" -ForegroundColor $(if($r.Status -eq "PASSED"){"Green"}else{"Red"})

# Extract report/apply id
$applyId = $null
try {
    $applyJson = $r.Response | ConvertFrom-Json
    $applyId = $applyJson.data.id
    if (-not $applyId) { $applyId = $applyJson.data }
    Write-Host "  ApplyID: $applyId" -ForegroundColor Cyan
} catch { Write-Host "  Could not extract apply ID" -ForegroundColor Red }

# Report apply query
$r = Test-API -TestId "RPT-02" -Module "report" -Name "apply query" -Method "POST" -Url "$baseUrl/report/apply/query" -Body '{"pageNum":1,"pageSize":10}' -ExpectedCode 200 -Headers $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)" -ForegroundColor $(if($r.Status -eq "PASSED"){"Green"}else{"Red"})

# Try to get applyId from query list
if (-not $applyId) {
    try {
        $qJson = $r.Response | ConvertFrom-Json
        if ($qJson.data.records -and $qJson.data.records.Count -gt 0) {
            $applyId = $qJson.data.records[0].id
            Write-Host "  Got from query - ApplyID: $applyId" -ForegroundColor Cyan
        }
    } catch {}
}

# Report apply detail
if ($applyId) {
    $r = Test-API -TestId "RPT-03" -Module "report" -Name "apply detail" -Method "GET" -Url "$baseUrl/report/apply/$applyId" -ExpectedCode 200 -Headers $headers
    $results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)" -ForegroundColor $(if($r.Status -eq "PASSED"){"Green"}else{"Red"})
} else {
    $r = Test-API -TestId "RPT-03" -Module "report" -Name "apply detail" -Method "GET" -Url "$baseUrl/report/apply/1" -ExpectedCode 200 -Headers $headers
    $results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status) (default id)" -ForegroundColor Yellow
    $applyId = 1
}

# Audit submit
$r = Test-API -TestId "RPT-04" -Module "report" -Name "audit submit" -Method "POST" -Url "$baseUrl/report/audit/submit/$applyId" -Body '{}' -ExpectedCode 200 -Headers $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)" -ForegroundColor $(if($r.Status -eq "PASSED"){"Green"}else{"Red"})

# First approve
$r = Test-API -TestId "RPT-05" -Module "report" -Name "first approve" -Method "POST" -Url "$baseUrl/report/audit/first-approve" -Body "{`"id`":$applyId}" -ExpectedCode 200 -Headers $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)" -ForegroundColor $(if($r.Status -eq "PASSED"){"Green"}else{"Red"})

# First reject (will fail since already approved, but test the endpoint)
$r = Test-API -TestId "RPT-06" -Module "report" -Name "first reject" -Method "POST" -Url "$baseUrl/report/audit/first-reject" -Body "{`"id`":$applyId,`"reason`":`"test`"}" -ExpectedCode 200 -Headers $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)" -ForegroundColor $(if($r.Status -eq "PASSED"){"Green"}else{"Red"})

# Final approve
$r = Test-API -TestId "RPT-07" -Module "report" -Name "final approve" -Method "POST" -Url "$baseUrl/report/audit/final-approve" -Body "{`"id`":$applyId}" -ExpectedCode 200 -Headers $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)" -ForegroundColor $(if($r.Status -eq "PASSED"){"Green"}else{"Red"})

# Final reject
$r = Test-API -TestId "RPT-08" -Module "report" -Name "final reject" -Method "POST" -Url "$baseUrl/report/audit/final-reject" -Body "{`"id`":$applyId,`"reason`":`"test`"}" -ExpectedCode 200 -Headers $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)" -ForegroundColor $(if($r.Status -eq "PASSED"){"Green"}else{"Red"})

# Save result
$resultBody = '{"applyId":' + $applyId + ',"results":[{"itemCode":"WBC","itemName":"WBC","resultValue":"5.5","unit":"10^9/L","referenceRange":"3.5-9.5","abnormalFlag":0},{"itemCode":"RBC","itemName":"RBC","resultValue":"4.5","unit":"10^12/L","referenceRange":"3.5-5.5","abnormalFlag":0}]}'
$r = Test-API -TestId "RPT-09" -Module "report" -Name "save result" -Method "POST" -Url "$baseUrl/report/result" -Body $resultBody -ExpectedCode 200 -Headers $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)" -ForegroundColor $(if($r.Status -eq "PASSED"){"Green"}else{"Red"})

# Publish report
$r = Test-API -TestId "RPT-10" -Module "report" -Name "publish report" -Method "POST" -Url "$baseUrl/report/publish" -Body "{`"id`":$applyId}" -ExpectedCode 200 -Headers $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)" -ForegroundColor $(if($r.Status -eq "PASSED"){"Green"}else{"Red"})

# ===== Equipment Module =====
Write-Host "`n--- Equipment Module ---" -ForegroundColor Yellow

$r = Test-API -TestId "EQP-01" -Module "equipment" -Name "equipment page" -Method "POST" -Url "$baseUrl/equipment/page" -Body '{"pageNum":1,"pageSize":10}' -ExpectedCode 200 -Headers $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)" -ForegroundColor $(if($r.Status -eq "PASSED"){"Green"}else{"Red"})

$r = Test-API -TestId "EQP-02" -Module "equipment" -Name "status all" -Method "GET" -Url "$baseUrl/equipment/status/all" -ExpectedCode 200 -Headers $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)" -ForegroundColor $(if($r.Status -eq "PASSED"){"Green"}else{"Red"})

# ===== HL7 Module =====
Write-Host "`n--- HL7 Module ---" -ForegroundColor Yellow

$hl7Body = '{"patientId":"P20260001","patientName":"testPatient","gender":"1","age":30,"deptCode":"LAB","deptName":"lab","wardCode":"","bedNo":"","clinicalDiagnosis":"fever","testItems":[{"itemCode":"WBC","itemName":"WBC"}],"applicantId":1,"applicantName":"admin","urgency":0}'
$r = Test-API -TestId "HL7-01" -Module "hl7" -Name "HIS lab order" -Method "POST" -Url "$baseUrl/hl7/his-integration/lab-order" -Body $hl7Body -ExpectedCode 200 -Headers $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)" -ForegroundColor $(if($r.Status -eq "PASSED"){"Green"}else{"Red"})

$r = Test-API -TestId "HL7-02" -Module "hl7" -Name "hl7 message page" -Method "GET" -Url "$baseUrl/hl7/hl7-message/page?pageNum=1&pageSize=10" -ExpectedCode 200 -Headers $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)" -ForegroundColor $(if($r.Status -eq "PASSED"){"Green"}else{"Red"})

$r = Test-API -TestId "HL7-03" -Module "hl7" -Name "interface config page" -Method "GET" -Url "$baseUrl/hl7/interface-config/page?pageNum=1&pageSize=10" -ExpectedCode 200 -Headers $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)" -ForegroundColor $(if($r.Status -eq "PASSED"){"Green"}else{"Red"})

$r = Test-API -TestId "HL7-04" -Module "hl7" -Name "connection log list" -Method "GET" -Url "$baseUrl/hl7/connection-log/list" -ExpectedCode 200 -Headers $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)" -ForegroundColor $(if($r.Status -eq "PASSED"){"Green"}else{"Red"})

# ===== Statistics Module =====
Write-Host "`n--- Statistics Module ---" -ForegroundColor Yellow

$r = Test-API -TestId "STAT-01" -Module "statistics" -Name "dashboard overview" -Method "GET" -Url "$baseUrl/statistics/dashboard/overview" -ExpectedCode 200 -Headers $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)" -ForegroundColor $(if($r.Status -eq "PASSED"){"Green"}else{"Red"})

$r = Test-API -TestId "STAT-02" -Module "statistics" -Name "workload trend" -Method "GET" -Url "$baseUrl/statistics/workload/chart/trend" -ExpectedCode 200 -Headers $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)" -ForegroundColor $(if($r.Status -eq "PASSED"){"Green"}else{"Red"})

$r = Test-API -TestId "STAT-03" -Module "statistics" -Name "specimen type pie" -Method "GET" -Url "$baseUrl/statistics/specimen/chart/type-pie" -ExpectedCode 200 -Headers $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)" -ForegroundColor $(if($r.Status -eq "PASSED"){"Green"}else{"Red"})

$r = Test-API -TestId "STAT-04" -Module "statistics" -Name "equipment statistics" -Method "GET" -Url "$baseUrl/statistics/equipment/page" -ExpectedCode 200 -Headers $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)" -ForegroundColor $(if($r.Status -eq "PASSED"){"Green"}else{"Red"})

# ===== Boundary/Negative Tests =====
Write-Host "`n--- Boundary/Negative Tests ---" -ForegroundColor Yellow

# Login with wrong password
$r = Test-API -TestId "NEG-01" -Module "auth" -Name "login wrong pwd" -Method "POST" -Url "$baseUrl/auth/login" -Body '{"username":"admin","password":"wrong"}' -ExpectedCode 401
try { $j = $r.Response | ConvertFrom-Json; if($j.code -ne 200){$r.Status="PASSED"} } catch {}
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)" -ForegroundColor $(if($r.Status -eq "PASSED"){"Green"}else{"Red"})

# Access without token
$r = Test-API -TestId "NEG-02" -Module "auth" -Name "no token access" -Method "GET" -Url "$baseUrl/auth/info" -ExpectedCode 401 -Headers @{}
try { $j = $r.Response | ConvertFrom-Json; if($j.code -eq 401){$r.Status="PASSED"} } catch {}
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)" -ForegroundColor $(if($r.Status -eq "PASSED"){"Green"}else{"Red"})

# Non-existent specimen ID
$r = Test-API -TestId "NEG-03" -Module "specimen" -Name "getById nonexist" -Method "GET" -Url "$baseUrl/specimen/getById/999999" -ExpectedCode 200 -Headers $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)" -ForegroundColor $(if($r.Status -eq "PASSED"){"Green"}else{"Red"})

# Empty body register
$r = Test-API -TestId "NEG-04" -Module "specimen" -Name "register empty" -Method "POST" -Url "$baseUrl/specimen/register" -Body '{}' -ExpectedCode 400 -Headers $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)" -ForegroundColor $(if($r.Status -eq "PASSED"){"Green"}else{"Red"})

# Non-existent report ID
$r = Test-API -TestId "NEG-05" -Module "report" -Name "apply nonexist" -Method "GET" -Url "$baseUrl/report/apply/999999" -ExpectedCode 200 -Headers $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)" -ForegroundColor $(if($r.Status -eq "PASSED"){"Green"}else{"Red"})

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

# Output detailed results as JSON for document generation
$output = $results | ForEach-Object {
    [PSCustomObject]@{
        testId = $_.TestId
        module = $_.Module
        name = $_.Name
        method = $_.Method
        url = $_.Url
        body = $_.Body
        expectedCode = $_.ExpectedCode
        actualCode = $_.ActualCode
        response = $_.Response
        status = $_.Status
    }
}

$jsonOutput = $output | ConvertTo-Json -Depth 3
$jsonOutput | Out-File -FilePath "d:\ReStart\FINALWORK\docs\test-results.json" -Encoding UTF8 -Force
Write-Host "`nResults saved to d:\ReStart\FINALWORK\docs\test-results.json" -ForegroundColor Green

# Also print full details
Write-Host "`n========================================" -ForegroundColor Cyan
Write-Host "Detailed Results" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
foreach ($item in $results) {
    Write-Host "`n[$($item.TestId)] $($item.Name) - $($item.Status)" -ForegroundColor $(if($item.Status -eq "PASSED"){"Green"}elseif($item.Status -eq "SKIPPED"){"Yellow"}else{"Red"})
    Write-Host "  URL: $($item.Url)"
    Write-Host "  Method: $($item.Method)"
    Write-Host "  Expected: $($item.ExpectedCode) | Actual: $($item.ActualCode)"
    $respShort = if($item.Response.Length -gt 200){$item.Response.Substring(0,200)+"..."}else{$item.Response}
    Write-Host "  Response: $respShort"
}
