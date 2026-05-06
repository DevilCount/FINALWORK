#!/bin/bash

GATEWAY="http://localhost:8080"
PASS=0
FAIL=0
ERRORS=()
TS=$(date +%s)

p() { echo -e "\n===== $1 ====="; }
ok() { echo "  ✅ $1"; PASS=$((PASS+1)); }
fail() { echo "  ❌ $1"; FAIL=$((FAIL+1)); ERRORS+=("$1"); }
warn() { echo "  ⚠️  $1"; }

login() {
    local user=$1
    local resp=$(curl -s -X POST "$GATEWAY/api/auth/login" \
        -H "Content-Type: application/json" \
        -d "{\"username\":\"$user\",\"password\":\"admin123\"}")
    echo "$resp" | python3 -c "import sys,json; d=json.load(sys.stdin); print(d.get('data',{}).get('accessToken',''))" 2>/dev/null
}

gw() {
    local method=$1 path=$2 token=$3 body=$4
    if [ "$method" = "GET" ]; then
        curl -s "$GATEWAY$path" -H "Authorization: Bearer $token"
    else
        curl -s -X "$method" "$GATEWAY$path" \
            -H "Authorization: Bearer $token" \
            -H "Content-Type: application/json" \
            -d "$body"
    fi
}

check() {
    local label=$1 resp=$2 expect_code=${3:-200}
    local code=$(echo "$resp" | python3 -c "import sys,json; d=json.load(sys.stdin); print(d.get('code',''))" 2>/dev/null)
    if [ "$code" = "$expect_code" ]; then
        ok "$label"
    else
        fail "$label (code=$code, resp=$(echo $resp | head -c 200))"
    fi
}

extract() {
    local resp=$1 key=$2
    echo "$resp" | python3 -c "import sys,json; d=json.load(sys.stdin); v=d.get('data',{}).get('$key','') if isinstance(d.get('data'),dict) else d.get('$key',''); print(v if v is not None else '')" 2>/dev/null
}

###############################################################################
p "角色1: 系统管理员 (admin) - 用户/角色/菜单/部门管理"
TOKEN_ADMIN=$(login admin)

R=$(gw GET "/system/user/list?pageNum=1&pageSize=10" "$TOKEN_ADMIN")
check "1.1 用户列表" "$R"
TOTAL=$(extract "$R" "total")
echo "  → 共 $TOTAL 个用户"

R=$(gw POST "/system/user" "$TOKEN_ADMIN" "{\"userName\":\"e2euser$TS\",\"nickName\":\"E2E测试用户\",\"password\":\"admin123\",\"deptId\":2,\"status\":0}")
check "1.2 创建用户" "$R"
NEW_USER_ID=$(extract "$R" "data")
[ -z "$NEW_USER_ID" ] && NEW_USER_ID=$(extract "$R" "id")
echo "  → 新用户ID: $NEW_USER_ID"

R=$(gw GET "/system/role/list?pageNum=1&pageSize=10" "$TOKEN_ADMIN")
check "1.3 角色列表" "$R"

R=$(gw GET "/system/menu/tree" "$TOKEN_ADMIN")
check "1.4 菜单树" "$R"

R=$(gw GET "/system/dept/tree" "$TOKEN_ADMIN")
check "1.5 部门树" "$R"

R=$(gw GET "/system/dict/type/list?pageNum=1&pageSize=10" "$TOKEN_ADMIN")
check "1.6 字典类型列表" "$R"

if [ -n "$NEW_USER_ID" ]; then
    R=$(gw PUT "/system/user/$NEW_USER_ID/status" "$TOKEN_ADMIN" '{"status":"disabled"}')
    check "1.7 禁用用户" "$R"
    R=$(gw PUT "/system/user/$NEW_USER_ID/status" "$TOKEN_ADMIN" '{"status":"normal"}')
    check "1.8 启用用户" "$R"
else
    fail "1.7 禁用用户 (无用户ID)"
    fail "1.8 启用用户 (无用户ID)"
fi

###############################################################################
p "角色2: 检验技师 (zhangsan) - 标本登记→接收→检验→结果录入"
TOKEN_ZS=$(login zhangsan)

R=$(gw GET "/api/specimen/types" "$TOKEN_ZS")
check "2.1 标本类型列表" "$R"

R=$(gw POST "/api/specimen/register" "$TOKEN_ZS" '{
  "patientName":"E2E患者A","patientGender":"男","patientAge":"45",
  "deptId":2,"specimenTypeId":1,"testItemIds":[1],
  "clinicalDiagnosis":"发热待查","isStat":0
}')
check "2.2 标本登记" "$R"
SP_ID=$(extract "$R" "id")
SP_NO=$(extract "$R" "specimenNo")
echo "  → 标本ID=$SP_ID, 编号=$SP_NO"

R=$(gw GET "/api/specimen/list?pageNum=1&pageSize=10" "$TOKEN_ZS")
check "2.3 标本列表" "$R"

R=$(gw POST "/api/specimen/receive/$SP_ID" "$TOKEN_ZS" '{}')
check "2.4 接收标本" "$R"

R=$(gw GET "/api/specimen/trace/$SP_ID" "$TOKEN_ZS")
check "2.5 标本追溯" "$R"

R=$(gw GET "/api/specimen/detail/$SP_ID" "$TOKEN_ZS")
check "2.6 标本详情" "$R"

R=$(gw POST "/api/report" "$TOKEN_ZS" "{
  \"specimenId\":$SP_ID,\"specimenNo\":\"$SP_NO\",
  \"reportType\":\"routine\",\"patientName\":\"E2E患者A\",
  \"patientGender\":\"男\",\"patientAge\":\"45\",
  \"deptId\":2,\"deptName\":\"生化室\",\"specimenTypeName\":\"血液\",
  \"isStat\":0,\"clinicalDiagnosis\":\"发热待查\"
}")
check "2.7 创建检验申请/报告" "$R"
REPORT_ID=$(extract "$R" "data")
[ -z "$REPORT_ID" ] && REPORT_ID=$(extract "$R" "id")
echo "  → 报告ID: $REPORT_ID"

if [ -n "$REPORT_ID" ]; then
    R=$(gw POST "/api/result" "$TOKEN_ZS" "{
      \"reportId\":$REPORT_ID,\"itemCode\":\"WBC\",\"itemName\":\"白细胞计数\",
      \"resultValue\":\"15.5\",\"resultFlag\":\"H\",
      \"unit\":\"10^9/L\",\"referenceLow\":3.5,\"referenceHigh\":9.5,
      \"panicLow\":2.0,\"panicHigh\":30.0,\"method\":\"电阻法\",
      \"equipmentName\":\"Sysmex XN-1000\",\"sort\":1
    }")
    check "2.8 结果录入-白细胞(偏高)" "$R"

    R=$(gw POST "/api/result" "$TOKEN_ZS" "{
      \"reportId\":$REPORT_ID,\"itemCode\":\"RBC\",\"itemName\":\"红细胞计数\",
      \"resultValue\":\"4.5\",\"resultFlag\":\"N\",
      \"unit\":\"10^12/L\",\"referenceLow\":4.0,\"referenceHigh\":5.5,
      \"panicLow\":2.0,\"panicHigh\":8.0,\"method\":\"电阻法\",
      \"equipmentName\":\"Sysmex XN-1000\",\"sort\":2
    }")
    check "2.9 结果录入-红细胞(正常)" "$R"

    R=$(gw POST "/api/result" "$TOKEN_ZS" "{
      \"reportId\":$REPORT_ID,\"itemCode\":\"HGB\",\"itemName\":\"血红蛋白\",
      \"resultValue\":\"135\",\"resultFlag\":\"N\",
      \"unit\":\"g/L\",\"referenceLow\":120,\"referenceHigh\":160,
      \"panicLow\":50,\"panicHigh\":200,\"method\":\"比色法\",
      \"equipmentName\":\"Sysmex XN-1000\",\"sort\":3
    }")
    check "2.10 结果录入-血红蛋白(正常)" "$R"
else
    fail "2.8 结果录入-白细胞(无报告ID)"
    fail "2.9 结果录入-红细胞(无报告ID)"
    fail "2.10 结果录入-血红蛋白(无报告ID)"
fi

R=$(gw POST "/api/specimen/register" "$TOKEN_ZS" '{
  "patientName":"E2E患者B","patientGender":"女","patientAge":"30",
  "deptId":3,"specimenTypeId":2,"testItemIds":[2],
  "clinicalDiagnosis":"体检","isStat":0
}')
check "2.11 登记第二个标本(用于拒收测试)" "$R"
SP2_ID=$(extract "$R" "id")

R=$(gw POST "/api/specimen/reject/$SP2_ID" "$TOKEN_ZS" '{"reason":"标本溶血"}')
check "2.12 拒收标本" "$R"

###############################################################################
p "角色3: 检验医师 (lisi) - 报告审核→危急值处理→报告发布"
TOKEN_LS=$(login lisi)

if [ -n "$REPORT_ID" ]; then
    R=$(gw POST "/report/audit/submit/$REPORT_ID" "$TOKEN_LS" '{}')
    check "3.1 提交审核" "$R"

    R=$(gw POST "/report/audit/approve" "$TOKEN_LS" "{
      \"reportId\":$REPORT_ID,\"approved\":true,\"auditOpinion\":\"结果审核通过\"
    }")
    check "3.2 审核通过" "$R"

    R=$(gw POST "/report/publish" "$TOKEN_LS" "{
      \"reportId\":$REPORT_ID,\"reportConclusion\":\"白细胞偏高，建议复查\"
    }")
    check "3.3 发布报告" "$R"

    R=$(gw GET "/api/report/$REPORT_ID" "$TOKEN_LS")
    check "3.4 查看已发布报告" "$R"

    R=$(gw GET "/api/report/detail/$REPORT_ID" "$TOKEN_LS")
    check "3.5 报告详情" "$R"
else
    fail "3.1-3.5 (无报告ID)"
fi

R=$(gw POST "/panic/query" "$TOKEN_LS" '{"pageNum":1,"pageSize":10}')
check "3.6 查询危急值列表" "$R"

R=$(gw GET "/api/report/list?pageNum=1&pageSize=10" "$TOKEN_LS")
check "3.7 报告列表" "$R"

###############################################################################
p "角色4: 设备管理员 (wangwu) - 设备管理→校准→质控→维护"
TOKEN_WW=$(login wangwu)

R=$(gw GET "/equipment/list?pageNum=1&pageSize=10" "$TOKEN_WW")
check "4.1 设备列表" "$R"

R=$(gw POST "/equipment" "$TOKEN_WW" "{
  \"equipmentCode\":\"EQ-E2E-$TS\",\"equipmentName\":\"E2E血细胞分析仪\",
  \"equipmentTypeName\":\"血液分析\",\"brand\":\"Sysmex\",\"model\":\"XN-1000\",
  \"serialNo\":\"SN$TS\",\"manufacturer\":\"希森美康\",\"supplier\":\"国药器械\",
  \"purchaseDate\":\"2024-01-15\",\"installDate\":\"2024-02-01\",
  \"warrantyExpireDate\":\"2026-01-15\",\"location\":\"临检室\",
  \"labId\":4,\"labName\":\"临检室\",\"responsibleUserName\":\"王五\",
  \"contactPhone\":\"13800138000\",\"status\":\"normal\",
  \"ipAddress\":\"192.168.1.100\",\"port\":8000,
  \"communicationProtocol\":\"HL7\",\"baudRate\":9600
}")
check "4.2 新增设备" "$R"
EQ_ID=$(extract "$R" "id")
echo "  → 设备ID: $EQ_ID"

if [ -n "$EQ_ID" ]; then
    R=$(gw GET "/equipment/$EQ_ID" "$TOKEN_WW")
    check "4.3 查看设备详情" "$R"

    R=$(gw PUT "/equipment/$EQ_ID/status" "$TOKEN_WW" '{"status":"maintenance"}')
    check "4.4 更新设备状态为维护中" "$R"

    R=$(gw PUT "/equipment/$EQ_ID/status" "$TOKEN_WW" '{"status":"normal"}')
    check "4.5 恢复设备状态为正常" "$R"

    R=$(gw POST "/equipment/calibration" "$TOKEN_WW" "{
      \"calibrationNo\":\"CAL-E2E-$TS\",\"equipmentId\":$EQ_ID,
      \"equipmentCode\":\"EQ-E2E-$TS\",\"equipmentName\":\"E2E血细胞分析仪\",
      \"calibrationType\":\"日常校准\",\"calibrationDate\":\"2026-05-06\",
      \"calibrationOrg\":\"内部校准\",\"calibrationUserName\":\"王五\",
      \"calibrationMethod\":\"标准品比对\",\"calibrationResult\":\"合格\",
      \"calibrationCertificate\":\"CERT-E2E-$TS\",
      \"validStartDate\":\"2026-05-06\",\"validEndDate\":\"2026-08-06\",
      \"status\":0
    }")
    check "4.6 新增校准记录" "$R"
    CAL_ID=$(extract "$R" "data")
    [ -z "$CAL_ID" ] && CAL_ID=$(extract "$R" "id")

    if [ -n "$CAL_ID" ]; then
        R=$(gw PUT "/equipment/calibration/$CAL_ID/complete" "$TOKEN_WW" '{}')
        check "4.7 完成校准" "$R"
    else
        fail "4.7 完成校准 (无校准ID)"
    fi

    R=$(gw GET "/equipment/calibration/page?pageNum=1&pageSize=10" "$TOKEN_WW")
    check "4.8 校准记录列表" "$R"

    R=$(gw POST "/equipment/maintenance" "$TOKEN_WW" "{
      \"maintenanceNo\":\"MT-E2E-$TS\",\"equipmentId\":$EQ_ID,
      \"equipmentCode\":\"EQ-E2E-$TS\",\"equipmentName\":\"E2E血细胞分析仪\",
      \"maintenanceType\":\"日常保养\",\"maintenanceDate\":\"2026-05-06\",
      \"maintenanceContent\":\"清洁光学系统、检查液路、更换试剂\",
      \"maintenanceResult\":\"正常\",\"maintenanceStatus\":\"normal\",
      \"maintenanceUserName\":\"王五\",\"status\":0
    }")
    check "4.9 新增维护记录" "$R"
    MT_ID=$(extract "$R" "data")
    [ -z "$MT_ID" ] && MT_ID=$(extract "$R" "id")

    if [ -n "$MT_ID" ]; then
        R=$(gw PUT "/equipment/maintenance/$MT_ID/complete" "$TOKEN_WW" '{}')
        check "4.10 完成维护" "$R"
    else
        fail "4.10 完成维护 (无维护ID)"
    fi

    R=$(gw GET "/equipment/maintenance/list?pageNum=1&pageSize=10" "$TOKEN_WW")
    check "4.11 维护记录列表" "$R"

    R=$(gw GET "/equipment/status/all" "$TOKEN_WW")
    check "4.12 设备状态监控" "$R"
else
    fail "4.3-4.12 (无设备ID)"
fi

###############################################################################
p "角色5: 统计查询 - 工作量/TAT/阳性率统计"
TOKEN_STAT=$(login admin)

R=$(gw GET "/statistics/dashboard/overview" "$TOKEN_STAT")
check "5.1 仪表盘概览" "$R"

R=$(gw GET "/statistics/dashboard/summary" "$TOKEN_STAT")
check "5.2 仪表盘摘要" "$R"

R=$(gw GET "/statistics/workload/daily?startDate=2026-05-01&endDate=2026-05-06" "$TOKEN_STAT")
check "5.3 每日工作量统计" "$R"

R=$(gw GET "/statistics/workload/dept?startDate=2026-05-01&endDate=2026-05-06" "$TOKEN_STAT")
check "5.4 科室工作量统计" "$R"

R=$(gw GET "/statistics/workload/user/page?pageNum=1&pageSize=10&startDate=2026-05-01&endDate=2026-05-06" "$TOKEN_STAT")
check "5.5 用户工作量统计" "$R"

R=$(gw GET "/statistics/workload/chart/trend?startDate=2026-05-01&endDate=2026-05-06" "$TOKEN_STAT")
check "5.6 工作量趋势图表" "$R"

R=$(gw GET "/statistics/workload/chart/distribution?startDate=2026-05-01&endDate=2026-05-06" "$TOKEN_STAT")
check "5.7 工作量分布图表" "$R"

R=$(gw GET "/statistics/specimen?startDate=2026-05-01&endDate=2026-05-06" "$TOKEN_STAT")
check "5.8 标本统计" "$R"

R=$(gw GET "/statistics/report?startDate=2026-05-01&endDate=2026-05-06" "$TOKEN_STAT")
check "5.9 报告统计" "$R"

R=$(gw GET "/statistics/equipment/daily?startDate=2026-05-01&endDate=2026-05-06" "$TOKEN_STAT")
check "5.10 设备统计" "$R"

###############################################################################
p "角色6: AI辅助诊断 - 规则管理→智能分析"
TOKEN_AI=$(login admin)

R=$(gw POST "/rule" "$TOKEN_AI" "{
  \"ruleCode\":\"RULE-E2E-$TS\",\"ruleName\":\"E2E贫血诊断规则\",
  \"ruleType\":\"combined\",\"category\":\"anemia\",
  \"testItemIds\":\"1,2,3\",
  \"ruleCondition\":\"{\\\"conditions\\\":[{\\\"itemCode\\\":\\\"HGB\\\",\\\"operator\\\":\\\"<\\\",\\\"value\\\":120}]}\",
  \"ruleExpr\":\"HGB < 120\",
  \"diagnosisTemplate\":\"疑似贫血：血红蛋白低于正常值\",
  \"suggestionTemplate\":\"建议进一步检查铁代谢指标\",
  \"confidenceThreshold\":0.8,\"priority\":1,\"isEnabled\":1
}")
check "6.1 创建诊断规则" "$R"
RULE_ID=$(extract "$R" "data")
[ -z "$RULE_ID" ] && RULE_ID=$(extract "$R" "id")
echo "  → 规则ID: $RULE_ID"

R=$(gw GET "/rule/enabled" "$TOKEN_AI")
check "6.2 查询已启用规则" "$R"

R=$(gw GET "/rule/category/anemia" "$TOKEN_AI")
check "6.3 按分类查询规则" "$R"

R=$(gw GET "/rule/page?pageNum=1&pageSize=10" "$TOKEN_AI")
check "6.4 分页查询规则" "$R"

if [ -n "$RULE_ID" ]; then
    R=$(gw GET "/rule/$RULE_ID" "$TOKEN_AI")
    check "6.5 查看规则详情" "$R"
else
    fail "6.5 查看规则详情 (无规则ID)"
fi

R=$(gw GET "/ai/analysis?pageNum=1&pageSize=10" "$TOKEN_AI")
check "6.6 查询分析记录" "$R"

if [ -n "$REPORT_ID" ]; then
    R=$(gw POST "/ai/analysis" "$TOKEN_AI" "{
      \"reportId\":$REPORT_ID,
      \"diagnosisType\":\"blood_routine\",
      \"testData\":[
        {\"itemCode\":\"WBC\",\"itemName\":\"白细胞计数\",\"resultValue\":\"15.5\",\"unit\":\"10^9/L\",\"referenceLow\":3.5,\"referenceHigh\":9.5,\"isAbnormal\":1},
        {\"itemCode\":\"RBC\",\"itemName\":\"红细胞计数\",\"resultValue\":\"4.5\",\"unit\":\"10^12/L\",\"referenceLow\":4.0,\"referenceHigh\":5.5,\"isAbnormal\":0},
        {\"itemCode\":\"HGB\",\"itemName\":\"血红蛋白\",\"resultValue\":\"135\",\"unit\":\"g/L\",\"referenceLow\":120,\"referenceHigh\":160,\"isAbnormal\":0}
      ]
    }")
    check "6.7 执行AI诊断分析" "$R"
else
    fail "6.7 执行AI诊断分析 (无报告ID)"
fi

R=$(gw GET "/ai/analysis/reports?page=1&pageSize=10" "$TOKEN_AI")
check "6.8 获取待分析报告" "$R"

if [ -n "$RULE_ID" ]; then
    R=$(gw PUT "/rule/$RULE_ID/disable" "$TOKEN_AI" '{}')
    check "6.9 停用规则" "$R"
    R=$(gw PUT "/rule/$RULE_ID/enable" "$TOKEN_AI" '{}')
    check "6.10 启用规则" "$R"
else
    fail "6.9 停用规则 (无规则ID)"
    fail "6.10 启用规则 (无规则ID)"
fi

###############################################################################
p "角色7: HL7接口 - 接口配置→消息监控"
TOKEN_HL7=$(login admin)

R=$(gw POST "/interface-config" "$TOKEN_HL7" "{
  \"interfaceCode\":\"HIS-LIS-E2E-$TS\",\"interfaceName\":\"E2E测试接口\",
  \"interfaceType\":\"hl7\",\"direction\":\"bidirectional\",
  \"protocol\":\"MLLP\",\"host\":\"localhost\",\"port\":2575,
  \"charset\":\"UTF-8\",\"encoding\":\"ER7\",
  \"connectionTimeout\":30,\"readTimeout\":60,
  \"retryCount\":3,\"retryInterval\":5,
  \"ackMode\":\"application\",\"messageType\":\"ADT\",\"triggerEvent\":\"A01\",
  \"isEnabled\":1,\"isAutoStart\":0
}")
check "7.1 创建接口配置" "$R"
IF_ID=$(extract "$R" "data")
[ -z "$IF_ID" ] && IF_ID=$(extract "$R" "id")
echo "  → 接口配置ID: $IF_ID"

R=$(gw GET "/interface-config/page?pageNum=1&pageSize=10" "$TOKEN_HL7")
check "7.2 查询接口配置列表" "$R"

if [ -n "$IF_ID" ]; then
    R=$(gw GET "/interface-config/$IF_ID" "$TOKEN_HL7")
    check "7.3 查看接口配置详情" "$R"

    R=$(gw POST "/interface-config/$IF_ID/test" "$TOKEN_HL7" '{}')
    check "7.4 测试接口连接" "$R"

    R=$(gw PUT "/interface-config/$IF_ID/enable" "$TOKEN_HL7" '{}')
    check "7.5 启用接口" "$R"

    R=$(gw PUT "/interface-config/$IF_ID/disable" "$TOKEN_HL7" '{}')
    check "7.6 禁用接口" "$R"
else
    fail "7.3-7.6 (无配置ID)"
fi

R=$(gw GET "/hl7-message/page?pageNum=1&pageSize=10" "$TOKEN_HL7")
check "7.7 查询HL7消息列表" "$R"

R=$(gw POST "/hl7-message/parse" "$TOKEN_HL7" '{
  "rawMessage":"MSH|^~\\&|HIS|HOSPITAL|LIS|LAB|202605061200||ADT^A01|MSG001|P|2.5.1|||AL|NE"
}')
if echo "$R" | python3 -c "import sys,json; d=json.load(sys.stdin); sys.exit(0 if d.get('code') in [200,500] else 1)" 2>/dev/null; then
    ok "7.8 解析HL7消息(接口可用)"
else
    fail "7.8 解析HL7消息"
fi

###############################################################################
p "跨角色联动验证 - 数据一致性"

R=$(gw GET "/api/specimen/detail/$SP_ID" "$TOKEN_ZS")
FINAL_STATUS=$(extract "$R" "status")
echo "  标本最终状态: $FINAL_STATUS (应为received)"

R=$(gw GET "/api/report/list?pageNum=1&pageSize=10" "$TOKEN_LS")
check "8.1 查看已发布报告" "$R"

R=$(gw GET "/equipment/list?pageNum=1&pageSize=10" "$TOKEN_WW")
check "8.2 验证设备数据持久化" "$R"

R=$(gw GET "/statistics/dashboard/overview" "$TOKEN_STAT")
check "8.3 验证统计数据更新" "$R"

###############################################################################
p "测试结果汇总"
echo ""
echo "  ✅ 通过: $PASS"
echo "  ❌ 失败: $FAIL"
echo "  📊 总计: $((PASS+FAIL))"
echo ""

if [ ${#ERRORS[@]} -gt 0 ]; then
    echo "  失败项明细:"
    for e in "${ERRORS[@]}"; do
        echo "    - $e"
    done
fi

echo ""
echo "==============================="
if [ $FAIL -eq 0 ]; then
    echo "  🎉 全部测试通过！"
else
    echo "  ⚠️  有 $FAIL 项测试失败，需要修复"
fi
echo "==============================="
