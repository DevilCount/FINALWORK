$Global:TestResults = [System.Collections.ArrayList]::new()
$base = "http://localhost:8080"

function Add-Result([string]$Test, [string]$Expected, [string]$Actual, [string]$Status) {
    $Global:TestResults.Add([PSCustomObject]@{Test=$Test; Expected=$Expected; Actual=$Actual; Status=$Status}) | Out-Null
    $c = if ($Status -eq "PASS") { "Green" } else { "Red" }
    Write-Host "  [$Status] $Test => $Actual" -ForegroundColor $c
}

function Test-API([string]$Name, [string]$Method, [string]$Url, [string]$Body = "", [string]$ExpHttp = "200", [string]$ExpBiz = "") {
    Write-Host "`n--- $Name ---" -ForegroundColor Yellow
    Write-Host "  $Method $Url"
    try {
        if ($Method -eq "GET") { $resp = Invoke-WebRequest -Uri $Url -Method GET -Headers $headers -UseBasicParsing -TimeoutSec 10 }
        else { $resp = Invoke-WebRequest -Uri $Url -Method POST -Headers $headers -Body $Body -UseBasicParsing -TimeoutSec 10 }
        $st = $resp.StatusCode.ToString(); $ct = $resp.Content
        $bc = ""; $bm = ""
        try { $j = $ct | ConvertFrom-Json; if ($j.code) { $bc = $j.code.ToString() }; if ($j.message) { $bm = $j.message } } catch {}
        $act = "HTTP$st code=$bc"
        if ($bm) { $act += " msg=$bm" }
        Write-Host "  => $act" -ForegroundColor Gray
        $p = ($ExpHttp -eq $st); if ($ExpBiz -and $bc -ne $ExpBiz) { $p = $false }
        Add-Result $Name "HTTP$ExpHttp code=$ExpBiz" $act $(if ($p) { "PASS" } else { "FAIL" })
        return $ct
    } catch {
        $em = $_.Exception.Message
        $eb = ""
        if ($_.Exception.Response) { try { $sr = [System.IO.StreamReader]::new($_.Exception.Response.GetResponseStream()); $eb = $sr.ReadToEnd() } catch {} }
        $st = if ($em -match '\((\d+)\)') { $Matches[1] } else { "ERR" }
        $bc = ""; $bm = ""
        if ($eb) { try { $j = $eb | ConvertFrom-Json; if ($j.code) { $bc = $j.code.ToString() }; if ($j.message) { $bm = $j.message } } catch {} }
        $act = "HTTP$st"
        if ($bc) { $act += " code=$bc" }; if ($bm) { $act += " msg=$bm" }
        Write-Host "  => $act" -ForegroundColor DarkRed
        $p = ($ExpHttp -eq $st); if ($ExpBiz -and $bc -ne $ExpBiz) { $p = $false }
        Add-Result $Name "HTTP$ExpHttp code=$ExpBiz" $act $(if ($p) { "PASS" } else { "FAIL" })
        return $eb
    }
}

# STEP 1: LOGIN
Write-Host "`n========== STEP 1: LOGIN ==========" -ForegroundColor Cyan
try {
    $lr = Invoke-WebRequest -Uri "$base/auth/login" -Method POST -ContentType 'application/json' -Body '{"username":"admin","password":"admin123"}' -UseBasicParsing
    $ld = $lr.Content | ConvertFrom-Json; $token = $ld.data.accessToken
    Add-Result "1.1 POST /auth/login" "HTTP200 code=200" "HTTP200 code=200(token OK)" "PASS"
} catch { Add-Result "1.1 POST /auth/login" "HTTP200" "FAILED" "FAIL"; exit 1 }
$headers = @{ "Authorization" = "Bearer $token"; "Content-Type" = "application/json" }

# STEP 2: REPORT DUAL-LEVEL AUDIT
Write-Host "`n========== STEP 2: REPORT DUAL-LEVEL AUDIT ==========" -ForegroundColor Cyan
$rl = Test-API "2.1 POST /report/apply/query" "POST" "$base/report/apply/query" '{"pageNo":1,"pageSize":50}' "200" "200"
$drid = $null
if ($rl) { try { $ro = $rl | ConvertFrom-Json; if ($ro.data.records) { $dr = $ro.data.records | Where-Object { $_.status -eq "draft" } | Select-Object -First 1; if ($dr) { $drid = $dr.id; Write-Host "  Draft report: $drid" -ForegroundColor Cyan } } } catch {} }
if (-not $drid) { Write-Host "  Creating draft report..." -ForegroundColor Yellow; $cr = Test-API "2.1a POST /report/apply" "POST" "$base/report/apply" '{"patientName":"audit-test","specimenId":1,"testItems":[{"testItemId":1,"result":"normal"}]}'; if ($cr) { try { $co = $cr | ConvertFrom-Json; if ($co.data) { $drid = $co.data; Write-Host "  Created: $drid" -ForegroundColor Cyan } } catch {} } }
if (-not $drid) { $drid = 1; Write-Host "  Using default ID=1" -ForegroundColor DarkYellow }

Test-API "2.2 POST /report/audit/submit/{id}" "POST" "$base/report/audit/submit/$drid" '{}'
Test-API "2.3 POST /report/audit/first-approve" "POST" "$base/report/audit/first-approve" "{`"reportId`":$drid,`"auditComment`":`"first-ok`"}"
Test-API "2.4 POST /report/audit/first-reject" "POST" "$base/report/audit/first-reject" '{"reportId":99999,"auditComment":"reject"}'
Test-API "2.5 POST /report/audit/final-approve" "POST" "$base/report/audit/final-approve" "{`"reportId`":$drid,`"auditComment`":`"final-ok`"}"
Test-API "2.6 POST /report/audit/final-reject" "POST" "$base/report/audit/final-reject" '{"reportId":99999,"auditComment":"final-reject"}'

# STEP 3: SPECIMEN STORAGE STATE MACHINE
Write-Host "`n========== STEP 3: SPECIMEN STORAGE STATE MACHINE ==========" -ForegroundColor Cyan
$sl = Test-API "3.1 POST /specimen/list" "POST" "$base/specimen/list" '{"pageNo":1,"pageSize":50}' "200" "200"
$rid = $null; $pid = $null; $tid = $null; $sid = $null
if ($sl) { try { $so = $sl | ConvertFrom-Json; if ($so.data.records) {
    $rr = $so.data.records | Where-Object { $_.status -eq "received" } | Select-Object -First 1; if ($rr) { $rid = $rr.id; Write-Host "  Received: $rid" -ForegroundColor Cyan }
    $pp = $so.data.records | Where-Object { $_.status -eq "pending" } | Select-Object -First 1; if ($pp) { $pid = $pp.id; Write-Host "  Pending: $pid" -ForegroundColor Cyan }
    $tt = $so.data.records | Where-Object { $_.status -eq "testing" } | Select-Object -First 1; if ($tt) { $tid = $tt.id; Write-Host "  Testing: $tid" -ForegroundColor Cyan }
    $ss = $so.data.records | Where-Object { $_.status -eq "stored" } | Select-Object -First 1; if ($ss) { $sid = $ss.id; Write-Host "  Stored: $sid" -ForegroundColor Cyan }
} } catch {} }

if ($rid) { Test-API "3.2 POST /specimen/storage (received->stored)" "POST" "$base/specimen/storage" "{`"specimenId`":$rid,`"storageLocation`":`"A-01-001`"}" }
else { Test-API "3.2 POST /specimen/storage (ID=1)" "POST" "$base/specimen/storage" '{"specimenId":1,"storageLocation":"A-01-001"}' }
if ($pid) { Test-API "3.3 POST /specimen/storage (pending->stored)" "POST" "$base/specimen/storage" "{`"specimenId`":$pid,`"storageLocation`":`"A-01-002`"}" }
else { Test-API "3.3 POST /specimen/storage (99999)" "POST" "$base/specimen/storage" '{"specimenId":99999,"storageLocation":"A-01-002"}' }
if ($tid) { Test-API "3.4 POST /specimen/storage (testing->stored)" "POST" "$base/specimen/storage" "{`"specimenId`":$tid,`"storageLocation`":`"A-01-003`"}" }
if ($sid) { Test-API "3.5 POST /specimen/storage (stored->stored)" "POST" "$base/specimen/storage" "{`"specimenId`":$sid,`"storageLocation`":`"A-01-004`"}" }

# STEP 4: GATEWAY TOKEN BLACKLIST
Write-Host "`n========== STEP 4: GATEWAY TOKEN BLACKLIST ==========" -ForegroundColor Cyan
$bt = $null; try { $br = Invoke-WebRequest -Uri "$base/auth/login" -Method POST -ContentType 'application/json' -Body '{"username":"admin","password":"admin123"}' -UseBasicParsing; $bt = ($br.Content | ConvertFrom-Json).data.accessToken } catch {}
if (-not $bt) { $bt = $token }
$bh = @{ "Authorization" = "Bearer $bt"; "Content-Type" = "application/json" }
try { Invoke-WebRequest -Uri "$base/auth/info" -Method GET -Headers $bh -UseBasicParsing | Out-Null; Add-Result "4.1 Token valid before logout" "HTTP200" "HTTP200" "PASS" } catch { Add-Result "4.1 Token valid before logout" "HTTP200" "FAILED" "FAIL" }
try { Invoke-WebRequest -Uri "$base/auth/logout" -Method POST -Headers $bh -Body '{}' -UseBasicParsing | Out-Null; Add-Result "4.2 POST /auth/logout" "HTTP200" "HTTP200" "PASS" } catch { Add-Result "4.2 POST /auth/logout" "HTTP200" "FAILED" "FAIL" }
Start-Sleep -Seconds 2
try { Invoke-WebRequest -Uri "$base/auth/info" -Method GET -Headers $bh -UseBasicParsing | Out-Null; Add-Result "4.3 Old token after logout" "HTTP401" "HTTP200 still valid" "FAIL" } catch { if ($_.Exception.Message -match '401') { Add-Result "4.3 Old token after logout" "HTTP401" "HTTP401 blacklisted" "PASS" } else { Add-Result "4.3 Old token after logout" "HTTP401" "$($_.Exception.Message)" "FAIL" } }
$rc = redis-cli EXISTS "blacklist:token:$bt" 2>&1; Add-Result "4.4 Redis blacklist key" "1" "$rc" $(if ($rc -match '1') { "PASS" } else { "FAIL" })

# STEP 5: LOG API TESTS
Write-Host "`n========== STEP 5: LOG API TESTS ==========" -ForegroundColor Cyan
$lb = '{"pageNo":1,"pageSize":5}'
Test-API "5.1 POST /user/oper-log/list" "POST" "$base/user/oper-log/list" $lb "200" "200"
Test-API "5.2 POST /user/login-log/list" "POST" "$base/user/login-log/list" $lb
Test-API "5.3 POST /user/error-log/list" "POST" "$base/user/error-log/list" $lb "200" "200"
Test-API "5.4 POST /user/audit-log/list" "POST" "$base/user/audit-log/list" $lb "200" "200"
Test-API "5.5 POST /hl7/connection-log/list" "POST" "$base/hl7/connection-log/list" $lb "200" "200"

# STEP 6: SQL INJECTION FIX VERIFICATION
Write-Host "`n========== STEP 6: SQL INJECTION FIX VERIFICATION ==========" -ForegroundColor Cyan
Test-API "6.1 POST /specimen/register (normal)" "POST" "$base/specimen/register" '{"patientName":"test1","patientGender":"1","patientAge":"35","patientIdNo":"110101199001011234","patientPhone":"13800138000","deptId":1,"deptName":"NK","specimenTypeId":1,"testItemIds":[1,2],"collectorId":1}' "200" "200"
Test-API "6.2 POST /specimen/register (SQL inj IDs)" "POST" "$base/specimen/register" '{"patientName":"test2","patientGender":"1","patientAge":"35","patientIdNo":"110101199001011235","patientPhone":"13800138001","deptId":1,"deptName":"NK","specimenTypeId":1,"testItemIds":["1;DROP TABLE specimen_test_item;--"]}'
Test-API "6.3 POST /specimen/register (SQL inj string)" "POST" "$base/specimen/register" '{"patientName":"test3;DROP TABLE specimen;--","patientGender":"1","patientAge":"35","patientIdNo":"110101199001011236","patientPhone":"13800138002","deptId":1,"deptName":"NK","specimenTypeId":1,"testItemIds":[1]}'
Test-API "6.4 POST /specimen/list (verify tables)" "POST" "$base/specimen/list" '{"pageNo":1,"pageSize":1}' "200" "200"

# STEP 7: HIS LAB ORDER TEST
Write-Host "`n========== STEP 7: HIS LAB ORDER TEST ==========" -ForegroundColor Cyan
Test-API "7.1 POST /hl7/his-integration/lab-order" "POST" "$base/hl7/his-integration/lab-order?interfaceCode=HIS_LIS" '{"patientName":"HIS-Test","patientIdNo":"110101199001011237","patientPhone":"13800138003","visitNo":"V20260412001","orderItems":[{"testItemCode":"CBC","testItemName":"BloodRoutine"}],"orderingDept":"IM","orderingDoctor":"DrZ"}'

# STEP 8: BASIC REGRESSION
Write-Host "`n========== STEP 8: BASIC REGRESSION ==========" -ForegroundColor Cyan
Test-API "8.1 GET /auth/info" "GET" "$base/auth/info" "" "200" "200"
Test-API "8.2 POST /specimen/list" "POST" "$base/specimen/list" '{"pageNo":1,"pageSize":5}' "200" "200"
Test-API "8.3 POST /report/apply/query" "POST" "$base/report/apply/query" '{"pageNo":1,"pageSize":5}' "200" "200"
Test-API "8.4 GET /equipment/page" "GET" "$base/equipment/page?page=1&size=5" "" "200" "200"
Test-API "8.5 GET /statistics/dashboard/overview" "GET" "$base/statistics/dashboard/overview" "" "200" "200"
Test-API "8.6 GET /specimen/types" "GET" "$base/specimen/types" "" "200" "200"
Test-API "8.7 GET /specimen/testItems" "GET" "$base/specimen/testItems" "" "200" "200"

# FINAL REPORT
Write-Host ""
Write-Host ("=" * 90) -ForegroundColor White
Write-Host "              LIS SYSTEM API TEST REPORT" -ForegroundColor Cyan
Write-Host ("=" * 90) -ForegroundColor White
$pc = ($Global:TestResults | Where-Object { $_.Status -eq "PASS" }).Count
$fc = ($Global:TestResults | Where-Object { $_.Status -eq "FAIL" }).Count
$tc = $Global:TestResults.Count
$pr = if ($tc -gt 0) { [math]::Round($pc/$tc*100,1) } else { 0 }
Write-Host "Total: $tc | PASS: $pc | FAIL: $fc | Rate: $pr%" -ForegroundColor $(if ($pr -ge 80) { "Green" } else { "Red" })
Write-Host ("=" * 90) -ForegroundColor White
Write-Host ""
foreach ($r in $Global:TestResults) {
    $c = if ($r.Status -eq "PASS") { "Green" } else { "Red" }
    $t = $r.Test.PadRight(55)
    $e = $r.Expected.PadRight(25)
    $a = $r.Actual.PadRight(50)
    Write-Host "  $t $e $a [$($r.Status)]" -ForegroundColor $c
}
Write-Host ""
Write-Host "========== FAILURES ==========" -ForegroundColor Red
$fi = $Global:TestResults | Where-Object { $_.Status -eq "FAIL" }
if ($fi) { foreach ($f in $fi) { Write-Host "  $($f.Test): Expected=$($f.Expected) Actual=$($f.Actual)" -ForegroundColor Red } }
else { Write-Host "  All passed!" -ForegroundColor Green }
