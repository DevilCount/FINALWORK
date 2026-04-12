# LIS Full API Test Script - V3
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
Write-Host "========================================"
Write-Host "Step 1: Login"
Write-Host "========================================"
$loginBody = '{"username":"admin","password":"admin123"}'
$loginResp = Invoke-WebRequest -Uri "$baseUrl/auth/login" -Method POST -ContentType 'application/json' -Body $loginBody -UseBasicParsing
$loginJson = $loginResp.Content | ConvertFrom-Json
$token = $loginJson.data.accessToken
$headers = @{"Authorization" = "Bearer $token"}
Write-Host "Login OK"

# ===== Auth Module =====
Write-Host "`n--- Auth Module ---"

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
Write-Host "Re-login OK"

# ===== User Module =====
Write-Host "`n--- User Module ---"

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
Write-Host "`n--- Specimen Module ---"

$r = Test-API "SPEC-01" "specimen" "specimen types" "GET" "$baseUrl/specimen/types" $null 200 $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

$r = Test-API "SPEC-02" "specimen" "test items" "GET" "$baseUrl/specimen/testItems" $null 200 $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

# Register with correct DTO
$regBody = '{"patientName":"APItestPatient","patientGender":"1","patientAge":"30","deptId":1,"deptName":"LabCenter","specimenTypeId":1,"specimenTypeName":"Blood","testItemIds":[1,2],"clinicalDiagnosis":"fever","remark":"API test"}'
$r = Test-API "SPEC-03" "specimen" "register specimen" "POST" "$baseUrl/specimen/register" $regBody 200 $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

$specimenId = $null; $barcode = $null
try {
    $regJson = $r.Response | ConvertFrom-Json
    if ($regJson.data.id) { $specimenId = $regJson.data.id; $barcode = $regJson.data.barcode }
    Write-Host "  SpecimenID: $specimenId, Barcode: $barcode"
} catch { Write-Host "  Could not extract specimen info" }

# Specimen list
$r = Test-API "SPEC-04" "specimen" "specimen list" "POST" "$baseUrl/specimen/list" '{"pageNum":1,"pageSize":10}' 200 $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

if (-not $specimenId) {
    try {
        $listJson = $r.Response | ConvertFrom-Json
        if ($listJson.data.records -and $listJson.data.records.Count -gt 0) {
            $specimenId = $listJson.data.records[0].id
            $barcode = $listJson.data.records[0].barcode
            Write-Host "  Got from list - SpecimenID: $specimenId, Barcode: $barcode"
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
    Write-Host "SPEC-05: SKIPPED"
}

# Storage specimen
if ($barcode) {
    $storeBody = "{`"barcode`":`"$barcode`",`"storageLocation`":`"A-01-01`"}"
    $r = Test-API "SPEC-06" "specimen" "storage specimen" "POST" "$baseUrl/specimen/storage" $storeBody 200 $headers
    $results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"
} else {
    $results += [PSCustomObject]@{TestId="SPEC-06";Module="specimen";Name="storage specimen";Method="POST";Url="$baseUrl/specimen/storage";Body="N/A";ExpectedCode=200;ActualCode="SKIPPED";Response="No barcode";Status="SKIPPED"}
    Write-Host "SPEC-06: SKIPPED"
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
Write-Host "`n--- Report Module ---"

$applyBody = '{"specimenId":' + $sid + ',"reportType":"routine","patientName":"APItestPatient","patientGender":"1","patientAge":"30","deptId":1,"deptName":"LabCenter","specimenTypeName":"Blood","clinicalDiagnosis":"fever","testItems":[{"itemCode":"ALT","itemName":"ALT"},{"itemCode":"AST","itemName":"AST"}]}'
$r = Test-API "RPT-01" "report" "create apply" "POST" "$baseUrl/report/apply" $applyBody 200 $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

$applyId = $null
try {
    $applyJson = $r.Response | ConvertFrom-Json
    $applyId = $applyJson.data.id
    if (-not $applyId) { $applyId = $applyJson.data }
    Write-Host "  ApplyID: $applyId"
} catch { Write-Host "  Could not extract apply ID" }

# Apply query
$r = Test-API "RPT-02" "report" "apply query" "POST" "$baseUrl/report/apply/query" '{"pageNum":1,"pageSize":10}' 200 $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

if (-not $applyId) {
    try {
        $qJson = $r.Response | ConvertFrom-Json
        if ($qJson.data.records -and $qJson.data.records.Count -gt 0) {
            $applyId = $qJson.data.records[0].id
            Write-Host "  Got from query - ApplyID: $applyId"
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

# First approve
$faBody = "{`"reportId`":$applyId,`"approved`":true,`"auditLevel`":1}"
$r = Test-API "RPT-05" "report" "first approve" "POST" "$baseUrl/report/audit/first-approve" $faBody 200 $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

# First reject
$frBody = "{`"reportId`":$applyId,`"approved`":false,`"auditLevel`":1,`"auditOpinion`":`"test`"}"
$r = Test-API "RPT-06" "report" "first reject" "POST" "$baseUrl/report/audit/first-reject" $frBody 200 $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

# Final approve
$fapBody = "{`"reportId`":$applyId,`"approved`":true,`"auditLevel`":2}"
$r = Test-API "RPT-07" "report" "final approve" "POST" "$baseUrl/report/audit/final-approve" $fapBody 200 $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

# Final reject
$frejBody = "{`"reportId`":$applyId,`"approved`":false,`"auditLevel`":2,`"auditOpinion`":`"test`"}"
$r = Test-API "RPT-08" "report" "final reject" "POST" "$baseUrl/report/audit/final-reject" $frejBody 200 $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

# Save result
$resBody = "{`"reportId`":$applyId,`"itemCode`":`"ALT`",`"itemName`":`"ALT`",`"resultValue`":`"25.5`",`"unit`":`"U/L`",`"referenceLow`":0,`"referenceHigh`":40,`"resultFlag`":`"N`"}"
$r = Test-API "RPT-09" "report" "save result" "POST" "$baseUrl/report/result" $resBody 200 $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

# Publish report
$pubBody = "{`"reportId`":$applyId,`"reportConclusion`":`"Normal`"}"
$r = Test-API "RPT-10" "report" "publish report" "POST" "$baseUrl/report/publish" $pubBody 200 $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

# ===== Equipment Module =====
Write-Host "`n--- Equipment Module ---"

$r = Test-API "EQP-01" "equipment" "equipment page" "POST" "$baseUrl/equipment/page" '{"pageNum":1,"pageSize":10}' 200 $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

$r = Test-API "EQP-02" "equipment" "status all" "GET" "$baseUrl/equipment/status/all" $null 200 $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

# ===== HL7 Module =====
Write-Host "`n--- HL7 Module ---"

$hl7Body = '{"patientId":"P20260001","patientName":"testPatient","gender":"1","department":"LAB001","ward":"","bedNo":"","clinicalInfo":"fever","interfaceCode":"HIS_LAB_ORDER","orderItems":[{"orderControl":"NW","orderItemCode":"ALT","orderItemName":"ALT","specimenType":"blood","priority":"R"}]}'
$r = Test-API "HL7-01" "hl7" "HIS lab order" "POST" "$baseUrl/hl7/his-integration/lab-order?interfaceCode=HIS_LAB_ORDER" $hl7Body 200 $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

$hl7MsgUrl = $baseUrl + "/hl7/hl7-message/page?pageNum=1" + [char]38 + "pageSize=10"
$r = Test-API "HL7-02" "hl7" "hl7 message page" "GET" $hl7MsgUrl $null 200 $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

$hl7CfgUrl = $baseUrl + "/hl7/interface-config/page?pageNum=1" + [char]38 + "pageSize=10"
$r = Test-API "HL7-03" "hl7" "interface config page" "GET" $hl7CfgUrl $null 200 $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

$connLogUrl = $baseUrl + "/hl7/connection-log/list?pageNum=1" + [char]38 + "pageSize=10"
$r = Test-API "HL7-04" "hl7" "connection log list" "POST" $connLogUrl $null 200 $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

# ===== Statistics Module =====
Write-Host "`n--- Statistics Module ---"

$r = Test-API "STAT-01" "statistics" "dashboard overview" "GET" "$baseUrl/statistics/dashboard/overview" $null 200 $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

$r = Test-API "STAT-02" "statistics" "workload trend" "GET" "$baseUrl/statistics/workload/chart/trend" $null 200 $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

$r = Test-API "STAT-03" "statistics" "specimen type pie" "GET" "$baseUrl/statistics/specimen/chart/type-pie" $null 200 $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

$r = Test-API "STAT-04" "statistics" "equipment statistics" "GET" "$baseUrl/statistics/equipment/page" $null 200 $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

# ===== Boundary/Negative Tests =====
Write-Host "`n--- Boundary/Negative Tests ---"

$r = Test-API "NEG-01" "auth" "login wrong pwd" "POST" "$baseUrl/auth/login" '{"username":"admin","password":"wrongpassword123"}' 401 @{}
try { $j = $r.Response | ConvertFrom-Json; if($j.code -ne 200){$r.Status="PASSED"} } catch {}
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

$r = Test-API "NEG-02" "auth" "no token access" "GET" "$baseUrl/auth/info" $null 401 @{}
if ($r.ActualCode -eq "ERROR" -and $r.Response -match "401") { $r.Status = "PASSED"; $r.ActualCode = 401 }
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

$r = Test-API "NEG-03" "specimen" "getById nonexist" "GET" "$baseUrl/specimen/getById/999999" $null 200 $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

$r = Test-API "NEG-04" "specimen" "register empty" "POST" "$baseUrl/specimen/register" '{}' 400 $headers
try { $j = $r.Response | ConvertFrom-Json; if($j.code -eq 400){$r.Status="PASSED"} } catch {}
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

$r = Test-API "NEG-05" "report" "apply nonexist" "GET" "$baseUrl/report/apply/999999" $null 200 $headers
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

$badHeaders = @{"Authorization" = "Bearer invalidtoken123"}
$r = Test-API "NEG-06" "auth" "invalid token" "GET" "$baseUrl/user/dept/tree" $null 401 $badHeaders
if ($r.ActualCode -eq "ERROR" -and $r.Response -match "401") { $r.Status = "PASSED"; $r.ActualCode = 401 }
$results += $r; Write-Host "$($r.TestId) $($r.Name): $($r.Status)"

# ===== Summary =====
Write-Host "`n========================================"
Write-Host "Test Summary"
Write-Host "========================================"

$passed = ($results | Where-Object { $_.Status -eq "PASSED" }).Count
$failed = ($results | Where-Object { $_.Status -eq "FAILED" }).Count
$skipped = ($results | Where-Object { $_.Status -eq "SKIPPED" }).Count
$total = $results.Count

Write-Host "Total: $total | Passed: $passed | Failed: $failed | Skipped: $skipped"
Write-Host "Pass Rate: $([math]::Round($passed/$total*100,1))%"

# Save results as JSON
$output = $results | ForEach-Object {
    [PSCustomObject]@{
        testId=$_.TestId; module=$_.Module; name=$_.Name; method=$_.Method; url=$_.Url
        body=$_.Body; expectedCode=$_.ExpectedCode; actualCode=$_.ActualCode; response=$_.Response; status=$_.Status
    }
}
$jsonOutput = $output | ConvertTo-Json -Depth 3
$jsonOutput | Out-File -FilePath "d:\ReStart\FINALWORK\docs\test-results.json" -Encoding UTF8 -Force
Write-Host "`nResults saved to test-results.json"

# Print full details
Write-Host "`n========================================"
Write-Host "Detailed Results"
Write-Host "========================================"
foreach ($item in $results) {
    $color = if($item.Status -eq "PASSED"){"Green"}elseif($item.Status -eq "SKIPPED"){"Yellow"}else{"Red"}
    Write-Host "`n[$($item.TestId)] $($item.Name) - $($item.Status)" -ForegroundColor $color
    Write-Host "  URL: $($item.Url)"
    Write-Host "  Method: $($item.Method)"
    Write-Host "  Expected: $($item.ExpectedCode) | Actual: $($item.ActualCode)"
    $rShort = if($item.Response.Length -gt 300){$item.Response.Substring(0,300)+"..."}else{$item.Response}
    Write-Host "  Response: $rShort"
}
