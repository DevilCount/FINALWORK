# Agent-04 检验服务Agent - 开发任务书

## 文档信息

| 项目名称 | 基于微服务架构的实验室管理系统（LIS） |
|---------|--------------------------------------|
| 文档版本 | V1.0.0 |
| 编写日期 | 2026年4月 |
| 任务书编号 | LIS-AGENT-04 |
| Agent编号 | Agent-04 |
| Agent角色 | 检验服务Agent |
| 负责模块 | 检验管理微服务（lis-report，端口8084） |
| 文档状态 | 正式发布 |

---

## 一、Agent基本信息

### 1.1 Agent概述

Agent-04（检验服务Agent）是LIS系统开发团队中负责检验业务核心流程的关键角色。作为系统的"检验业务引擎"，检验服务Agent承担着患者管理、检验申请、结果录入、报告审核、报告发布、报告查询与打印以及危急值管理等全链条检验业务功能。检验服务Agent的工作质量直接关系到检验报告的准确性、审核流程的规范性以及危急值处理的及时性，是整个LIS系统中业务逻辑最为复杂、数据安全性要求最高的微服务模块。检验服务Agent需要与标本服务Agent紧密协作，确保检验结果与标本的准确关联，同时通过完善的报告状态机和危急值管理机制，保障医疗安全。

### 1.2 Agent职责范围

检验服务Agent的职责覆盖以下核心领域：

1. **患者管理**：实现患者信息的CRUD操作和查询功能，为检验业务提供患者数据基础。
2. **检验申请管理**：实现检验申请单的创建、查询和取消功能。
3. **检验结果录入**：实现检验结果的手工录入、批量录入和仪器数据导入功能。
4. **报告审核**：实现检验报告的初审、终审和审核驳回功能。
5. **报告发布与回收**：实现检验报告的发布、回收和重新发布功能。
6. **报告查询**：实现检验报告的多条件检索和详情查看功能。
7. **报告打印**：实现检验报告的单个打印和批量打印功能。
8. **危急值管理**：实现危急值的自动判断、报警通知、处理记录和临床通知功能。

### 1.3 协作关系

| 协作对象 | 协作内容 |
|---------|---------|
| Agent-00（架构师） | 依赖lis-common公共模块、Feign客户端接口、数据库初始化脚本 |
| Agent-01（前端工程师） | 提供患者管理、检验申请、结果录入、报告审核、报告查询、危急值管理相关REST API接口 |
| Agent-02（用户服务） | 通过Feign调用获取用户信息、校验审核权限、获取医生信息 |
| Agent-03（标本服务） | 通过Feign调用查询标本信息、更新标本状态（检验中、已完成）；获取标本追溯数据 |
| Agent-05（设备服务） | 通过Feign调用获取仪器数据、查询设备状态 |
| Agent-06（HL7服务） | 检验报告通过HL7接口发送给HIS系统 |
| Agent-07（统计服务） | 提供检验统计数据查询接口（Feign调用） |

---

## 二、技术栈与版本约束

### 2.1 核心技术栈

| 技术组件 | 版本要求 | 用途说明 |
|---------|---------|---------|
| JDK | 17 | Java运行环境，采用LTS长期支持版本 |
| Maven | 3.8+ | 项目构建与依赖管理工具 |
| Spring Boot | 2.7.18 | 微服务基础框架 |
| Spring Cloud Alibaba | 2022.0.0.0 | 微服务治理框架 |
| Nacos | 2.2.3 | 服务注册与配置中心 |
| MyBatis-Plus | 3.5.5 | ORM框架，简化数据库操作 |
| MySQL | 8.0 | 关系型数据库（lis_report库） |
| Redis | 7.0 | 缓存报告数据、危急值通知队列 |
| MapStruct | 1.5.5.Final | 对象映射工具，DTO与Entity转换 |
| Hutool | 5.8.25 | Java工具类库 |
| Lombok | 1.18.30 | 代码简化工具 |
| FreeMarker | 2.3.32 | 报告模板引擎，生成检验报告 |
| Apache POI | 5.2.5 | Excel文件读写（仪器数据导入） |
| EasyExcel | 3.3.3 | Excel文件简化读写（批量结果导入） |

### 2.2 数据库信息

| 数据库名称 | 字符集 | 排序规则 | 说明 |
|-----------|--------|---------|------|
| lis_report | utf8mb4 | utf8mb4_general_ci | 检验报告管理数据库 |

**核心数据表**：

| 表名 | 说明 | 关键字段 |
|------|------|---------|
| report | 检验报告主表 | id, report_no, specimen_id, patient_id, report_status, audit_status, first_auditor, final_auditor, publish_time |
| report_item | 报告检验项目表 | id, report_id, item_code, item_name, result, unit, ref_range, abnormal_flag, critical_flag |
| patient | 患者信息表 | id, patient_name, gender, age, id_card, phone, visit_no, department, bed_no |
| critical_value | 危急值记录表 | id, report_id, report_item_id, patient_id, item_name, result_value, critical_range, discover_time, notify_status, handle_status, handler, handle_time |

### 2.3 参考文档

| 文档名称 | 路径 |
|---------|------|
| API接口设计规范 | /workspace/lis-project/docs/02-设计文档/API接口设计规范.md |
| 数据库设计说明书 | /workspace/lis-project/docs/02-设计文档/数据库设计说明书.md |
| 编码规范 | /workspace/lis-project/docs/05-开发规范/编码规范.md |
| 概要设计说明书 | /workspace/lis-project/docs/02-设计文档/概要设计说明书.md |
| 详细设计说明书 | /workspace/lis-project/docs/02-设计文档/详细设计说明书.md |

---

## 三、任务清单

### 任务一：患者管理

#### 1.1 患者信息CRUD

**目标**：实现患者信息的增删改查功能，为检验业务提供患者数据基础。

**患者列表查询接口**：

```
GET /api/report/patients
Authorization: Bearer {accessToken}
```

**请求参数**：

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| patientName | String | 否 | 患者姓名（模糊搜索） |
| idCard | String | 否 | 身份证号（精确匹配） |
| phone | String | 否 | 联系电话（模糊搜索） |
| visitNo | String | 否 | 就诊卡号（精确匹配） |
| department | String | 否 | 科室（精确匹配） |
| beginTime | String | 否 | 创建时间起始 |
| endTime | String | 否 | 创建时间结束 |
| pageNum | Integer | 否 | 页码 |
| pageSize | Integer | 否 | 每页条数 |

**响应参数**：

| 字段 | 类型 | 说明 |
|------|------|------|
| total | Long | 总记录数 |
| rows | List\<PatientVO\> | 患者列表 |

**PatientVO字段**：

| 字段 | 类型 | 说明 |
|------|------|------|
| id | Long | 患者ID |
| patientName | String | 患者姓名 |
| gender | String | 性别 |
| age | String | 年龄 |
| idCard | String | 身份证号 |
| phone | String | 联系电话 |
| visitNo | String | 就诊卡号 |
| department | String | 科室 |
| bedNo | String | 床号 |
| doctorName | String | 主治医生 |
| clinicalDiagnosis | String | 临床诊断 |
| createTime | String | 创建时间 |

**患者新增接口**：

```
POST /api/report/patients
Authorization: Bearer {accessToken}
Content-Type: application/json
```

**请求参数**：

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| patientName | String | 是 | 患者姓名 |
| gender | String | 是 | 性别（male/female） |
| age | String | 是 | 年龄 |
| idCard | String | 否 | 身份证号（唯一） |
| phone | String | 否 | 联系电话 |
| visitNo | String | 否 | 就诊卡号（唯一） |
| department | String | 否 | 科室 |
| bedNo | String | 否 | 床号 |
| doctorName | String | 否 | 主治医生 |
| clinicalDiagnosis | String | 否 | 临床诊断 |
| allergyHistory | String | 否 | 过敏史 |

**业务逻辑**：

1. 参数校验：患者姓名、性别、年龄为必填项。
2. 身份证号唯一性校验（如提供）。
3. 就诊卡号唯一性校验（如提供）。
4. 身份证号格式校验（18位或15位）。
5. 插入 `patient` 表。
6. 如身份证号已存在，返回已有患者信息（患者去重）。

**患者修改接口**：

```
PUT /api/report/patients/{patientId}
Authorization: Bearer {accessToken}
```

**患者查询接口（根据就诊卡号或身份证号）**：

```
GET /api/report/patients/search
Authorization: Bearer {accessToken}
```

**请求参数**：

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| keyword | String | 是 | 搜索关键词（就诊卡号或身份证号） |

**功能说明**：根据关键词自动判断是就诊卡号还是身份证号，进行精确查询，快速定位患者。

**技术要点**：

- 患者信息是检验业务的基础数据，需确保数据准确性和唯一性。
- 患者去重逻辑：同一身份证号视为同一患者，避免重复创建。
- 患者信息修改需记录变更日志，便于追溯。
- 敏感信息（身份证号、手机号）在列表查询时需脱敏处理（如：110***********1234）。

**验收标准**：

- 患者CRUD功能正常，参数校验完整。
- 身份证号和就诊卡号唯一性校验有效。
- 患者去重逻辑正确。
- 敏感信息脱敏处理正确。
- 患者快速查询功能正常。

---

### 任务二：检验申请管理

#### 2.1 申请单创建

**接口定义**：

```
POST /api/report/apply
Authorization: Bearer {accessToken}
Content-Type: application/json
```

**请求参数**：

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| patientId | Long | 是 | 患者ID |
| specimenType | String | 是 | 标本类型 |
| department | String | 否 | 申请科室 |
| doctorName | String | 否 | 申请医生 |
| clinicalDiagnosis | String | 否 | 临床诊断 |
| urgency | String | 否 | 紧急程度（normal/urgent/stat） |
| items | List\<ApplyItemDTO\> | 是 | 检验项目列表 |
| remark | String | 否 | 备注 |

**ApplyItemDTO字段**：

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| itemCode | String | 是 | 项目编码 |
| itemName | String | 是 | 项目名称 |
| itemCategory | String | 是 | 项目分类 |
| price | BigDecimal | 否 | 项目单价 |

**响应参数**：

| 字段 | 类型 | 说明 |
|------|------|------|
| applyId | Long | 申请ID |
| reportNo | String | 报告编号（如RPT20260406001） |
| status | String | 申请状态（pending） |

**业务逻辑**：

1. 参数校验：患者ID、标本类型、检验项目列表为必填项。
2. 患者存在性校验：查询patient表中是否存在指定患者。
3. 生成唯一报告编号：
   - 编号规则：`RPT` + `yyyyMMdd` + `4位流水号`。
   - 使用Redis原子自增生成流水号，Key格式为 `lis:report:no:{yyyyMMdd}`。
4. 创建检验报告记录（report表）：
   - 状态设置为"待检验"（pending）。
   - 审核状态设置为"未审核"（not_audited）。
5. 批量创建报告检验项目（report_item表）：
   - 每个项目关联报告ID。
   - 项目状态初始化为"待录入"（pending）。
6. 通知标本服务：通过Feign调用标本服务，创建标本登记记录（或将申请与已有标本关联）。
7. 返回申请结果。

#### 2.2 申请单查询

**接口定义**：

```
GET /api/report/apply
Authorization: Bearer {accessToken}
```

**请求参数**：

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| reportNo | String | 否 | 报告编号 |
| patientName | String | 否 | 患者姓名 |
| status | String | 否 | 申请状态 |
| urgency | String | 否 | 紧急程度 |
| beginTime | String | 否 | 申请时间起始 |
| endTime | String | 否 | 申请时间结束 |
| pageNum | Integer | 否 | 页码 |
| pageSize | Integer | 否 | 每页条数 |

#### 2.3 申请单取消

**接口定义**：

```
PUT /api/report/apply/{applyId}/cancel
Authorization: Bearer {accessToken}
Content-Type: application/json
```

**请求参数**：

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| cancelReason | String | 是 | 取消原因 |

**业务逻辑**：

1. 校验申请状态为"待检验"（pending），已进入检验流程的申请不可取消。
2. 更新申请状态为"已取消"（cancelled）。
3. 通知标本服务：如已关联标本，更新标本状态。
4. 记录取消日志。

**技术要点**：

- 报告编号生成使用Redis原子自增，确保唯一性。
- 申请单取消需检查下游依赖（标本状态），避免数据不一致。
- 紧急程度（stat-紧急）的申请需在系统中特殊标记，优先处理。

**验收标准**：

- 申请单创建功能正常，报告编号生成唯一。
- 申请单查询功能正常，多条件组合搜索结果准确。
- 申请单取消功能正常，状态校验有效。
- 与标本服务的关联正确。

---

### 任务三：检验结果录入

#### 3.1 手工录入检验结果

**目标**：实现检验结果的逐项手工录入功能。

**接口定义**：

```
PUT /api/report/{reportId}/result
Authorization: Bearer {accessToken}
Content-Type: application/json
```

**请求参数**：

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| items | List\<ResultItemDTO\> | 是 | 检验结果列表 |

**ResultItemDTO字段**：

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| itemId | Long | 是 | 报告检验项目ID |
| result | String | 是 | 结果值 |
| unit | String | 否 | 单位（如不传则使用项目默认单位） |
| refRange | String | 否 | 参考范围（如不传则使用项目默认参考范围） |

**业务逻辑**：

1. 校验报告状态为"待检验"或"检验中"。
2. 校验所有itemId属于该报告。
3. 逐项处理检验结果：
   - 保存结果值到 `report_item` 表的 `result` 字段。
   - **参考值比对**：将结果值与参考范围进行比对，自动标记异常标志。
     - 参考范围格式：`低值-高值`（如 `3.5-5.5`）或 `<高值`（如 `<10`）或 `>低值`（如 `>0.5`）。
     - 比对规则：
       - 数值型结果：与参考范围的高低值进行比较。
       - 结果 < 低值：标记为偏低（abnormal_flag = 'L'）。
       - 结果 > 高值：标记为偏高（abnormal_flag = 'H'）。
       - 结果在范围内：标记为正常（abnormal_flag = 'N'）。
     - 非数值型结果（如阴性/阳性）：不自动比对，由人工判断。
   - **危急值判断**：将结果值与危急值范围进行比对，自动标记危急标志。
     - 危急值范围存储在检验项目配置中（可从字典数据或项目配置表获取）。
     - 如结果值落入危急值范围，设置 `critical_flag = 1`。
     - 触发危急值处理流程（详见任务八）。
4. 更新报告状态为"检验中"（testing）。
5. 通知标本服务：通过Feign调用更新标本状态为"检验中"（testing）。
6. 记录操作日志。

**参考值比对算法详细设计**：

```java
// 位置：com.lis.report.service.impl.ResultCompareServiceImpl
// 核心方法：compareResult(result, refRange) -> AbnormalFlag

// 参考范围解析规则：
// 1. "3.5-5.5" -> 低值=3.5, 高值=5.5
// 2. "<10" -> 高值=10（无低值限制）
// 3. ">0.5" -> 低值=0.5（无高值限制）
// 4. "阴性" -> 非数值型，跳过自动比对

// 比对逻辑：
// - 解析参考范围字符串，提取低值和高值
// - 将结果值转为BigDecimal进行比较
// - 返回异常标志：H（偏高）、L（偏低）、N（正常）、-（无法判断）
```

#### 3.2 批量录入检验结果

**接口定义**：

```
PUT /api/report/result/batch
Authorization: Bearer {accessToken}
Content-Type: application/json
```

**请求参数**：

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| results | List\<BatchResultDTO\> | 是 | 批量结果列表 |

**BatchResultDTO字段**：

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| reportId | Long | 是 | 报告ID |
| itemId | Long | 是 | 项目ID |
| result | String | 是 | 结果值 |

**业务逻辑**：

1. 批量校验报告状态和项目归属。
2. 批量更新结果值。
3. 批量执行参考值比对和危急值判断。
4. 批量更新报告状态。
5. 返回处理结果汇总：成功数量、失败数量及失败详情。

#### 3.3 仪器数据导入

**接口定义**：

```
POST /api/report/result/import
Authorization: Bearer {accessToken}
Content-Type: multipart/form-data
```

**请求参数**：

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| file | MultipartFile | 是 | 数据文件（支持.xlsx、.xls、.csv格式） |
| equipmentId | Long | 否 | 仪器ID（用于关联数据来源） |
| autoSubmit | Boolean | 否 | 是否自动提交审核（默认false） |

**业务逻辑**：

1. 文件格式校验：支持xlsx、xls、csv格式，文件大小不超过10MB。
2. 文件解析：使用EasyExcel解析Excel文件，CSV文件使用自定义解析器。
3. 数据映射：将文件中的列映射到系统字段。
   - 标本编号/条码 -> 关联报告ID。
   - 项目编码 -> 关联报告项目ID。
   - 结果值 -> 填入result字段。
4. 数据校验：
   - 标本编号是否存在。
   - 项目编码是否匹配。
   - 结果值格式是否合法。
5. 数据导入：批量更新report_item表的结果值。
6. 执行参考值比对和危急值判断。
7. 生成导入结果报告：成功条数、失败条数、失败原因明细。
8. 如autoSubmit为true，自动将导入成功的报告提交审核。

**仪器数据格式要求**：

| 列序号 | 列名 | 说明 | 示例 |
|--------|------|------|------|
| A | 标本编号 | 标本条码 | SP20260406001 |
| B | 项目编码 | 检验项目编码 | GLU |
| C | 结果值 | 检验结果 | 5.6 |
| D | 单位 | 结果单位（可选） | mmol/L |
| E | 异常标志 | 仪器标记的异常（可选） | H |

**技术要点**：

- EasyExcel使用SAX模式解析大文件，避免内存溢出。
- 导入数据量较大时使用分批处理（每批100条），避免长事务。
- 仪器数据导入为异步操作，前端通过轮询或WebSocket获取导入进度。
- 数据导入失败时支持部分成功，返回详细的错误信息供用户修正。

**验收标准**：

- 手工录入功能正常，结果值保存正确。
- 参考值比对算法正确，异常标志标记准确。
- 危急值自动判断正确，触发危急值流程。
- 批量录入功能正常，性能满足要求。
- 仪器数据导入功能正常，支持Excel和CSV格式。
- 导入结果报告详细，错误信息明确。

---

### 任务四：报告审核

#### 4.1 报告状态机设计

**目标**：定义检验报告的完整审核状态流转规则。

**报告状态定义**：

| 状态编码 | 状态名称 | 说明 |
|---------|---------|------|
| pending | 待检验 | 报告已创建，等待结果录入 |
| testing | 检验中 | 正在录入检验结果 |
| pending_audit | 待审核 | 结果录入完成，等待审核 |
| first_audited | 初审通过 | 初审通过，等待终审 |
| rejected | 审核驳回 | 审核不通过，退回修改 |
| final_audited | 终审通过 | 终审通过，等待发布 |
| published | 已发布 | 报告已发布 |
| recalled | 已回收 | 报告已回收 |

**审核状态流转规则**：

```
pending       --> testing          (开始录入结果)
testing       --> pending_audit    (提交审核)
pending_audit --> first_audited    (初审通过)
pending_audit --> rejected         (审核驳回)
first_audited --> final_audited    (终审通过)
first_audited --> rejected         (审核驳回)
rejected      --> testing          (退回后重新修改)
final_audited --> published        (发布报告)
published     --> recalled         (回收报告)
recalled      --> pending_audit    (重新提交审核)
recalled      --> published        (重新发布)
```

#### 4.2 初审

**接口定义**：

```
PUT /api/report/{reportId}/first-audit
Authorization: Bearer {accessToken}
Content-Type: application/json
```

**请求参数**：

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| auditResult | String | 是 | 审核结果（pass-通过，reject-驳回） |
| auditOpinion | String | 条件必填 | 审核意见（驳回时必填） |

**业务逻辑**：

1. 校验报告状态为"待审核"（pending_audit）。
2. 校验当前用户具有初审权限。
3. 校验当前用户不是该报告的结果录入人（审核人与录入人不能为同一人）。
4. 审核通过：
   - 更新报告审核状态为"初审通过"（first_audited）。
   - 记录初审人、初审时间。
   - 如系统配置为单级审核（无终审），直接跳转到"终审通过"状态。
5. 审核驳回：
   - 更新报告审核状态为"审核驳回"（rejected）。
   - 记录审核意见。
   - 通知结果录入人员（通过系统消息或WebSocket）。
6. 记录审核日志。

#### 4.3 终审

**接口定义**：

```
PUT /api/report/{reportId}/final-audit
Authorization: Bearer {accessToken}
Content-Type: application/json
```

**请求参数**：

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| auditResult | String | 是 | 审核结果（pass-通过，reject-驳回） |
| auditOpinion | String | 条件必填 | 审核意见（驳回时必填） |

**业务逻辑**：

1. 校验报告状态为"初审通过"（first_audited）。
2. 校验当前用户具有终审权限。
3. 校验当前用户不是该报告的初审人（初审人与终审人不能为同一人）。
4. 审核通过：
   - 更新报告审核状态为"终审通过"（final_audited）。
   - 记录终审人、终审时间。
5. 审核驳回：
   - 更新报告审核状态为"审核驳回"（rejected）。
   - 记录审核意见。
   - 通知结果录入人员。

#### 4.4 批量审核

**接口定义**：

```
PUT /api/report/audit/batch
Authorization: Bearer {accessToken}
Content-Type: application/json
```

**请求参数**：

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| reportIds | List\<Long\> | 是 | 报告ID列表（最多50个） |
| auditType | String | 是 | 审核类型（first/final） |
| auditResult | String | 是 | 审核结果（pass/reject） |
| auditOpinion | String | 否 | 审核意见 |

**业务逻辑**：

1. 批量校验报告状态和审核权限。
2. 过滤不符合条件的报告（状态不对、审核人与录入人为同一人等）。
3. 批量更新审核状态。
4. 返回审核结果汇总：成功数量、跳过数量及跳过原因。

**技术要点**：

- 审核人与录入人/初审人不能为同一人的校验是医疗行业的重要合规要求。
- 审核操作需记录完整的审计日志，包括审核人、审核时间、审核结果、审核意见。
- 批量审核仅允许批量通过，不允许批量驳回（驳回需要填写具体意见）。
- 审核权限通过 `@RequirePermission` 注解控制，区分初审权限和终审权限。

**验收标准**：

- 报告状态机定义完整，所有状态转换路径正确。
- 初审功能正常，权限校验有效。
- 终审功能正常，权限校验有效。
- 审核驳回功能正常，驳回意见记录完整。
- 审核人与录入人/初审人隔离校验有效。
- 批量审核功能正常。
- 审核日志记录完整。

---

### 任务五：报告发布与回收

#### 5.1 报告发布

**接口定义**：

```
PUT /api/report/{reportId}/publish
Authorization: Bearer {accessToken}
```

**业务逻辑**：

1. 校验报告审核状态为"终审通过"（final_audited）。
2. 校验当前用户具有发布权限。
3. 更新报告状态为"已发布"（published）。
4. 记录发布时间。
5. 通知标本服务：通过Feign调用更新标本状态为"已完成"（completed）。
6. 通过HL7接口（Feign调用HL7服务）将报告发送给HIS系统。
7. 如患者存在危急值且未处理，发布时再次提醒。
8. 记录发布日志。

#### 5.2 报告回收

**接口定义**：

```
PUT /api/report/{reportId}/recall
Authorization: Bearer {accessToken}
Content-Type: application/json
```

**请求参数**：

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| recallReason | String | 是 | 回收原因 |

**业务逻辑**：

1. 校验报告状态为"已发布"（published）。
2. 校验当前用户具有回收权限（回收为高危操作，需管理员或科室主任权限）。
3. 校验报告发布时间未超过24小时（超过24小时的报告不可回收，需走特殊审批流程）。
4. 更新报告状态为"已回收"（recalled）。
5. 记录回收原因、回收人、回收时间。
6. 通知标本服务：更新标本状态。
7. 通知HIS系统：通过HL7接口发送报告回收通知。

#### 5.3 重新发布

**接口定义**：

```
PUT /api/report/{reportId}/republish
Authorization: Bearer {accessToken}
```

**业务逻辑**：

1. 校验报告状态为"已回收"（recalled）。
2. 校验报告已修改完毕（所有驳回项已修正）。
3. 重新走审核流程（状态变为"待审核"），或直接发布（如系统配置允许）。
4. 记录操作日志。

**技术要点**：

- 报告发布是检验流程的最终环节，需确保所有检验项目均有结果值。
- 报告回收是高危操作，需严格的权限控制和时间限制。
- 发布和回收操作需通过消息队列异步通知下游系统（标本服务、HL7服务），避免阻塞主流程。

**验收标准**：

- 报告发布功能正常，状态更新正确。
- 报告回收功能正常，权限校验和时间限制有效。
- 重新发布功能正常。
- 下游系统通知正确（标本服务、HL7服务）。
- 操作日志记录完整。

---

### 任务六：报告查询

#### 6.1 多条件检索

**接口定义**：

```
GET /api/report/query
Authorization: Bearer {accessToken}
```

**请求参数**：

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| reportNo | String | 否 | 报告编号（精确匹配） |
| patientName | String | 否 | 患者姓名（模糊搜索） |
| patientId | Long | 否 | 患者ID |
| specimenNo | String | 否 | 标本编号 |
| specimenType | String | 否 | 标本类型 |
| itemName | String | 否 | 检验项目名称（模糊搜索） |
| reportStatus | String | 否 | 报告状态 |
| auditStatus | String | 否 | 审核状态 |
| urgency | String | 否 | 紧急程度 |
| beginTime | String | 否 | 创建时间起始 |
| endTime | String | 否 | 创建时间结束 |
| pageNum | Integer | 否 | 页码 |
| pageSize | Integer | 否 | 每页条数 |

**响应参数**：

| 字段 | 类型 | 说明 |
|------|------|------|
| total | Long | 总记录数 |
| rows | List\<ReportListVO\> | 报告列表 |

**ReportListVO字段**：

| 字段 | 类型 | 说明 |
|------|------|------|
| reportId | Long | 报告ID |
| reportNo | String | 报告编号 |
| patientName | String | 患者姓名 |
| gender | String | 性别 |
| age | String | 年龄 |
| specimenType | String | 标本类型 |
| itemCount | Integer | 检验项目数 |
| abnormalCount | Integer | 异常项目数 |
| reportStatus | String | 报告状态 |
| auditStatus | String | 审核状态 |
| urgency | String | 紧急程度 |
| firstAuditor | String | 初审人 |
| finalAuditor | String | 终审人 |
| publishTime | String | 发布时间 |
| createTime | String | 创建时间 |

#### 6.2 报告详情查看

**接口定义**：

```
GET /api/report/{reportId}/detail
Authorization: Bearer {accessToken}
```

**响应参数**：

| 字段 | 类型 | 说明 |
|------|------|------|
| reportInfo | ReportDetailVO | 报告基本信息 |
| patientInfo | PatientVO | 患者信息 |
| specimenInfo | SpecimenVO | 标本信息 |
| items | List\<ReportItemDetailVO\> | 检验项目结果列表 |
| auditHistory | List\<AuditRecordVO\> | 审核历史记录 |

**ReportItemDetailVO字段**：

| 字段 | 类型 | 说明 |
|------|------|------|
| itemName | String | 项目名称 |
| itemCode | String | 项目编码 |
| result | String | 结果值 |
| unit | String | 单位 |
| refRange | String | 参考范围 |
| abnormalFlag | String | 异常标志（H-偏高，L-偏低，N-正常） |
| criticalFlag | Integer | 危急值标志（0-正常，1-危急） |

**技术要点**：

- 报告查询需关联report、report_item、patient多张表，使用LEFT JOIN确保数据完整。
- 多条件组合查询需动态拼接SQL WHERE条件，使用MyBatis的 `<if>` 标签。
- 查询性能优化：在report_no、patient_id、report_status、create_time等常用查询字段上建立索引。
- 报告详情数据可缓存到Redis中，Key格式为 `lis:report:detail:{reportId}`，过期时间30分钟。

**验收标准**：

- 多条件检索功能正常，组合搜索结果准确。
- 报告详情数据完整，包含患者信息、标本信息、检验结果、审核历史。
- 分页功能正常。
- 查询性能满足要求（响应时间不超过500ms）。

---

### 任务七：报告打印

#### 7.1 单个报告打印

**接口定义**：

```
GET /api/report/{reportId}/print
Authorization: Bearer {accessToken}
```

**响应**：返回报告PDF文件，Content-Type为 `application/pdf`。

**功能说明**：

1. 使用FreeMarker模板引擎生成检验报告HTML。
2. 将HTML转换为PDF文件。
3. 返回PDF文件流。

**FreeMarker报告模板设计**：

```
模板路径：/workspace/lis-project/src/backend/lis-report/src/main/resources/templates/report/
模板文件：
  - report-standard.ftl    # 标准检验报告模板
  - report-microbe.ftl     # 微生物检验报告模板
  - report-blood.ftl       # 血型检验报告模板
```

**标准检验报告模板内容结构**：

```
+--------------------------------------------------+
|              XXX医院检验报告                        |
|                                                    |
| 患者姓名：张三    性别：男    年龄：35岁            |
| 就诊卡号：P001    科室：内科    床号：301           |
| 标本类型：血液    标本编号：SP20260406001          |
| 临床诊断：高血压                                  |
|                                                    |
| 检验项目        结果        单位      参考范围  标志 |
|--------------------------------------------------|
| 葡萄糖(GLU)     5.6       mmol/L    3.9-6.1       |
| 总胆固醇(TC)    6.8       mmol/L    3.1-5.7   H   |
| 甘油三酯(TG)    1.5       mmol/L    0.6-1.7       |
| ...                                               |
|                                                    |
| 检验者：李四          审核者：王五                  |
| 报告日期：2026-04-06                              |
| 备注：本报告仅对本次标本负责                        |
+--------------------------------------------------+
```

**模板数据模型**：

```java
// 位置：com.lis.report.dto.ReportPrintDTO
// 字段：
//   - hospitalName：医院名称（从系统配置获取）
//   - reportNo：报告编号
//   - patientInfo：患者信息（姓名、性别、年龄、就诊卡号、科室、床号）
//   - specimenInfo：标本信息（类型、编号、采集时间）
//   - clinicalDiagnosis：临床诊断
//   - items：检验项目列表（名称、结果、单位、参考范围、异常标志）
//   - examiner：检验者姓名
//   - auditor：审核者姓名
//   - reportDate：报告日期
//   - remark：备注
```

#### 7.2 批量报告打印

**接口定义**：

```
POST /api/report/print/batch
Authorization: Bearer {accessToken}
Content-Type: application/json
```

**请求参数**：

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| reportIds | List\<Long\> | 是 | 报告ID列表（最多20个） |

**响应**：返回合并后的PDF文件（每个报告一页）。

**业务逻辑**：

1. 批量查询报告详情数据。
2. 逐个生成报告PDF页面。
3. 使用PDF合并工具将多个PDF页面合并为一个文件。
4. 返回合并后的PDF文件流。

**技术要点**：

- HTML转PDF使用Flying Saucer（xhtmlrenderer）或iText库。
- PDF合并使用iText的 `PdfMerger` 工具。
- 报告模板支持自定义：医院名称、Logo、页眉页脚等从系统配置中读取。
- 异常结果（abnormalFlag为H或L）在报告中以红色字体显示。
- 打印操作需记录日志（操作人、打印时间、报告编号）。

**验收标准**：

- 单个报告打印功能正常，PDF格式正确。
- 报告内容完整，排版规范，异常结果红色标记。
- 批量打印功能正常，多个报告正确合并。
- 报告模板可配置，支持不同报告类型。
- 打印日志记录完整。

---

### 任务八：危急值管理

#### 8.1 危急值自动判断

**目标**：在检验结果录入时自动判断是否为危急值，并触发危急值处理流程。

**危急值判断规则**：

危急值范围存储在检验项目配置中，每个检验项目可配置独立的危急值范围。配置格式如下：

| 项目编码 | 项目名称 | 危急值低限 | 危急值高限 | 单位 |
|---------|---------|-----------|-----------|------|
| GLU | 葡萄糖 | 2.2 | 22.2 | mmol/L |
| K | 钾离子 | 2.8 | 6.2 | mmol/L |
| Na | 钠离子 | 120 | 160 | mmol/L |
| WBC | 白细胞 | 2.0 | 30.0 | 10^9/L |
| HGB | 血红蛋白 | 50 | 200 | g/L |
| PLT | 血小板 | 30 | 1000 | 10^9/L |

**危急值判断算法**：

```java
// 位置：com.lis.report.service.impl.CriticalValueServiceImpl
// 核心方法：checkCriticalValue(itemId, result) -> CriticalCheckResult

// 判断逻辑：
// 1. 根据项目编码查询危急值配置（从缓存或数据库）
// 2. 如项目未配置危急值范围，跳过判断
// 3. 将结果值转为BigDecimal
// 4. 与危急值低限比较：result < criticalLow -> 触发低危急值
// 5. 与危急值高限比较：result > criticalHigh -> 触发高危急值
// 6. 返回判断结果：是否危急值、危急值类型（高/低）、危急值范围

// 触发条件：
// - result < criticalLow（仅当criticalLow不为null时判断）
// - result > criticalHigh（仅当criticalHigh不为null时判断）
```

**危急值触发流程**：

1. 结果录入时自动执行危急值判断。
2. 如判断为危急值：
   - 在 `critical_value` 表中创建危急值记录。
   - 设置 `notify_status = 'pending'`（待通知）。
   - 设置 `handle_status = 'pending'`（待处理）。
   - 在Redis中发布危急值通知消息（使用Redis Pub/Sub或List）。
   - 前端通过WebSocket实时接收危急值通知，弹出告警弹窗。

#### 8.2 危急值报警通知

**接口定义**：

```
POST /api/report/critical/{criticalId}/notify
Authorization: Bearer {accessToken}
Content-Type: application/json
```

**请求参数**：

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| notifyType | String | 是 | 通知方式（phone-电话，system-系统消息，both-电话+系统消息） |
| notifiedPerson | String | 是 | 被通知人（临床医生姓名） |
| notifiedPhone | String | 条件必填 | 被通知人电话（通知方式为phone或both时必填） |
| notifyContent | String | 否 | 通知内容（默认自动生成） |

**自动生成的通知内容模板**：

```
【危急值通知】
患者：{patientName}（{department}/{bedNo}）
项目：{itemName}
结果：{resultValue} {unit}
危急值范围：{criticalRange}
发现时间：{discoverTime}
请及时处理！
```

**业务逻辑**：

1. 校验危急值记录存在且通知状态为"待通知"。
2. 记录通知信息：通知方式、被通知人、通知时间。
3. 更新通知状态为"已通知"（notified）。
4. 如通知方式包含电话，记录电话拨打时间和结果。
5. 发送系统消息通知（通过WebSocket推送到临床医生工作站）。

#### 8.3 危急值处理记录

**接口定义**：

```
POST /api/report/critical/{criticalId}/handle
Authorization: Bearer {accessToken}
Content-Type: application/json
```

**请求参数**：

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| handleMeasure | String | 是 | 处理措施 |
| handler | String | 是 | 处理人（临床医生） |
| handleResult | String | 是 | 处理结果 |

**业务逻辑**：

1. 校验危急值记录存在且通知状态为"已通知"。
2. 记录处理信息：处理措施、处理人、处理时间、处理结果。
3. 更新处理状态为"已处理"（handled）。
4. 记录处理日志。

#### 8.4 危急值查询与统计

**危急值列表查询接口**：

```
GET /api/report/critical
Authorization: Bearer {accessToken}
```

**请求参数**：

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| patientName | String | 否 | 患者姓名 |
| itemName | String | 否 | 检验项目名称 |
| notifyStatus | String | 否 | 通知状态（pending/notified） |
| handleStatus | String | 否 | 处理状态（pending/handled） |
| beginTime | String | 否 | 发现时间起始 |
| endTime | String | 否 | 发现时间结束 |
| pageNum | Integer | 否 | 页码 |
| pageSize | Integer | 否 | 每页条数 |

**危急值统计接口**：

```
GET /api/report/critical/statistics
Authorization: Bearer {accessToken}
```

**请求参数**：

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| beginDate | String | 是 | 起始日期 |
| endDate | String | 是 | 结束日期 |

**响应参数**：

| 字段 | 类型 | 说明 |
|------|------|------|
| totalCritical | Long | 危急值总数 |
| pendingNotify | Long | 待通知数量 |
| pendingHandle | Long | 待处理数量 |
| handled | Long | 已处理数量 |
| avgHandleMinutes | Long | 平均处理时间（分钟） |
| byItem | List\<ItemStat\> | 按检验项目分组统计 |
| byDepartment | List\<DeptStat\> | 按科室分组统计 |

**技术要点**：

- 危急值判断需在结果录入时同步执行，确保不遗漏。
- 危急值通知需实时推送，使用WebSocket实现前端实时告警。
- 危急值记录不可删除，仅可更新通知状态和处理状态。
- 危急值处理有时效要求（建议30分钟内通知，60分钟内处理），超时需升级告警。
- 危急值统计数据可缓存到Redis中，设置5分钟过期时间。

**验收标准**：

- 危急值自动判断正确，覆盖所有配置了危急值范围的检验项目。
- 危急值通知功能正常，支持电话和系统消息两种通知方式。
- 危急值处理记录完整，处理流程闭环。
- 危急值列表查询和统计功能正常。
- WebSocket实时告警推送正常。
- 危急值处理时效监控有效。

---

## 四、输出路径与交付物

### 4.1 源代码输出

| 输出内容 | 路径 |
|---------|------|
| 检验服务源代码 | `/workspace/lis-project/src/backend/lis-report/` |
| Controller层 | `/workspace/lis-project/src/backend/lis-report/src/main/java/com/lis/report/controller/` |
| Service层 | `/workspace/lis-project/src/backend/lis-report/src/main/java/com/lis/report/service/` |
| Mapper层 | `/workspace/lis-project/src/backend/lis-report/src/main/java/com/lis/report/mapper/` |
| Entity层 | `/workspace/lis-project/src/backend/lis-report/src/main/java/com/lis/report/entity/` |
| DTO/VO层 | `/workspace/lis-project/src/backend/lis-report/src/main/java/com/lis/report/dto/` |
| 枚举类 | `/workspace/lis-project/src/backend/lis-report/src/main/java/com/lis/report/enums/` |
| Mapper XML | `/workspace/lis-project/src/backend/lis-report/src/main/resources/mapper/` |
| 报告模板 | `/workspace/lis-project/src/backend/lis-report/src/main/resources/templates/report/` |

### 4.2 项目结构要求

```
lis-report/
├── pom.xml
└── src/main/java/com/lis/report/
    ├── LisReportApplication.java         # 启动类
    ├── controller/
    │   ├── PatientController.java            # 患者管理接口
    │   ├── ApplyController.java              # 检验申请接口
    │   ├── ResultController.java             # 结果录入接口
    │   ├── AuditController.java              # 报告审核接口
    │   ├── PublishController.java            # 报告发布接口
    │   ├── QueryController.java              # 报告查询接口
    │   ├── PrintController.java              # 报告打印接口
    │   └── CriticalValueController.java      # 危急值管理接口
    ├── service/
    │   ├── PatientService.java               # 患者服务接口
    │   ├── ApplyService.java                 # 检验申请服务接口
    │   ├── ResultService.java                # 结果录入服务接口
    │   ├── ResultCompareService.java         # 参考值比对服务接口
    │   ├── AuditService.java                 # 报告审核服务接口
    │   ├── PublishService.java               # 报告发布服务接口
    │   ├── QueryService.java                 # 报告查询服务接口
    │   ├── PrintService.java                 # 报告打印服务接口
    │   ├── CriticalValueService.java         # 危急值管理服务接口
    │   └── impl/
    │       ├── PatientServiceImpl.java
    │       ├── ApplyServiceImpl.java
    │       ├── ResultServiceImpl.java
    │       ├── ResultCompareServiceImpl.java
    │       ├── AuditServiceImpl.java
    │       ├── PublishServiceImpl.java
    │       ├── QueryServiceImpl.java
    │       ├── PrintServiceImpl.java
    │       └── CriticalValueServiceImpl.java
    ├── mapper/
    │   ├── PatientMapper.java
    │   ├── ReportMapper.java
    │   ├── ReportItemMapper.java
    │   └── CriticalValueMapper.java
    ├── entity/
    │   ├── Patient.java
    │   ├── Report.java
    │   ├── ReportItem.java
    │   └── CriticalValue.java
    ├── dto/
    │   ├── PatientDTO.java
    │   ├── ApplyDTO.java
    │   ├── ResultItemDTO.java
    │   ├── BatchResultDTO.java
    │   ├── AuditDTO.java
    │   ├── ReportPrintDTO.java
    │   ├── CriticalNotifyDTO.java
    │   └── CriticalHandleDTO.java
    ├── vo/
    │   ├── PatientVO.java
    │   ├── ReportListVO.java
    │   ├── ReportDetailVO.java
    │   ├── ReportItemDetailVO.java
    │   ├── AuditRecordVO.java
    │   ├── CriticalValueVO.java
    │   └── CriticalStatisticsVO.java
    ├── enums/
    │   ├── ReportStatusEnum.java             # 报告状态枚举
    │   ├── AuditStatusEnum.java              # 审核状态枚举
    │   ├── AbnormalFlagEnum.java             # 异常标志枚举
    │   ├── UrgencyEnum.java                  # 紧急程度枚举
    │   └── CriticalNotifyStatusEnum.java     # 危急值通知状态枚举
    ├── config/
    │   ├── MyBatisPlusConfig.java            # MyBatis-Plus配置
    │   └── FreeMarkerConfig.java             # FreeMarker配置
    ├── converter/
    │   ├── PatientConverter.java             # MapStruct转换器
    │   ├── ReportConverter.java
    │   └── CriticalValueConverter.java
    └── util/
        ├── ResultCompareUtils.java           # 参考值比对工具类
        └── PdfUtils.java                     # PDF生成工具类
```

### 4.3 报告模板文件

| 文件名 | 说明 |
|--------|------|
| report-standard.ftl | 标准检验报告模板（血液、生化、免疫等） |
| report-microbe.ftl | 微生物检验报告模板 |
| report-blood.ftl | 血型检验报告模板 |
| report-urine.ftl | 尿液检验报告模板 |

### 4.4 开发报告

| 输出内容 | 路径 |
|---------|------|
| 检验服务开发报告 | `/workspace/lis-project/reports/agent-reports/Agent-04-检验服务-开发报告.md` |

---

## 五、技术约束与规范要求

### 5.1 编码规范

- 所有Java代码必须符合 `/workspace/lis-project/docs/05-开发规范/编码规范.md` 中的后端编码规范。
- 包命名使用 `com.lis.report` 格式。
- 类命名使用大驼峰（UpperCamelCase），方法命名使用小驼峰（lowerCamelCase）。
- 所有公共方法必须提供完整的Javadoc注释。
- 代码中禁止出现魔法值，所有常量必须定义在枚举类或常量类中。
- 使用Lombok简化实体类代码。

### 5.2 接口规范

- 所有API接口必须符合 `/workspace/lis-project/docs/02-设计文档/API接口设计规范.md`。
- 接口URL遵循RESTful风格，统一使用 `/api/report/` 前缀。
- 统一使用 `Result<T>` 封装响应结果。
- 统一异常处理，返回标准错误格式。
- 接口需添加 `@RequirePermission` 注解进行权限控制。

### 5.3 数据库规范

- 所有数据库设计必须符合 `/workspace/lis-project/docs/02-设计文档/数据库设计说明书.md`。
- 使用MyBatis-Plus作为ORM框架，简单CRUD使用内置方法，复杂查询使用自定义XML。
- 禁止使用物理删除，统一使用逻辑删除（`@TableLogic`）。
- 主键使用雪花算法（`@TableId(type = IdType.ASSIGN_ID)`）。
- 所有表必须包含公共审计字段。
- 报告数据为医疗核心数据，需确保数据完整性和一致性。

### 5.4 业务规范

- 审核人与录入人不能为同一人，初审人与终审人不能为同一人。
- 报告一旦发布不可修改，如需修改必须先回收。
- 报告回收有时间限制（24小时内），超时需特殊审批。
- 危急值处理有时效要求，需监控处理时效。
- 所有检验结果和审核记录不可物理删除，仅支持逻辑删除。
- 报告编号一旦生成不可修改。

### 5.5 性能要求

- 患者查询接口响应时间不超过300ms。
- 结果录入接口响应时间不超过500ms。
- 报告审核接口响应时间不超过300ms。
- 报告查询接口响应时间不超过500ms。
- 报告打印（单个）响应时间不超过3秒。
- 批量打印（20个报告）响应时间不超过15秒。
- 危急值判断在结果录入时同步执行，判断耗时不超过50ms。

---

## 六、验收标准

### 6.1 功能验收

- [ ] 患者管理功能完整：CRUD操作正常，快速查询正常，敏感信息脱敏正确。
- [ ] 检验申请管理功能完整：申请单创建、查询、取消均正常工作。
- [ ] 检验结果录入功能完整：手工录入、批量录入、仪器数据导入均正常工作。
- [ ] 参考值比对算法正确，异常标志标记准确。
- [ ] 危急值自动判断正确，覆盖所有配置项目。
- [ ] 报告审核功能完整：初审、终审、驳回、批量审核均正常工作。
- [ ] 审核人与录入人/初审人隔离校验有效。
- [ ] 报告发布与回收功能完整：发布、回收、重新发布均正常工作。
- [ ] 报告查询功能完整：多条件检索、详情查看均正常工作。
- [ ] 报告打印功能完整：单个打印、批量打印均正常工作，PDF格式正确。
- [ ] 危急值管理功能完整：自动判断、报警通知、处理记录、查询统计均正常工作。
- [ ] 所有接口返回格式符合API接口设计规范。

### 6.2 质量验收

- [ ] 代码符合编码规范，无明显的代码坏味道。
- [ ] 所有公共方法提供完整的Javadoc注释。
- [ ] 单元测试覆盖率不低于70%，核心业务逻辑（参考值比对、危急值判断、报告状态机）覆盖率不低于90%。
- [ ] 报告状态机逻辑严谨，无遗漏的非法状态转换路径。
- [ ] 仪器数据导入支持大文件（10MB以上）不内存溢出。
- [ ] 报告PDF打印格式规范，内容完整。

### 6.3 安全验收

- [ ] 接口权限校验有效，未授权用户无法操作报告。
- [ ] 审核权限隔离有效，审核人与录入人不能为同一人。
- [ ] 报告回收权限控制严格，普通用户无法回收报告。
- [ ] 危急值记录不可删除，确保医疗安全审计。
- [ ] SQL注入防护有效。
- [ ] 患者敏感信息脱敏处理正确。

### 6.4 文档验收

- [ ] 检验服务开发报告编写完成，内容详实。
- [ ] 报告状态机流转图包含在开发报告中。
- [ ] 危急值处理流程图包含在开发报告中。
- [ ] 接口文档完整，覆盖所有API端点。
- [ ] 报告模板使用说明包含在开发报告中。

---

## 七、开发进度安排

| 阶段 | 任务内容 | 预计工时 |
|------|---------|---------|
| 第一阶段 | 项目搭建（模块初始化、依赖配置、数据库连接、MyBatis-Plus配置、FreeMarker配置） | 2小时 |
| 第二阶段 | 患者管理开发（患者CRUD、快速查询、敏感信息脱敏） | 2小时 |
| 第三阶段 | 检验申请管理开发（申请单创建、查询、取消） | 2小时 |
| 第四阶段 | 检验结果录入开发（手工录入、批量录入、参考值比对算法） | 4小时 |
| 第五阶段 | 仪器数据导入开发（Excel/CSV解析、数据映射、批量导入） | 3小时 |
| 第六阶段 | 报告审核开发（状态机设计、初审、终审、驳回、批量审核） | 4小时 |
| 第七阶段 | 报告发布与回收开发（发布、回收、重新发布、下游通知） | 2小时 |
| 第八阶段 | 报告查询开发（多条件检索、详情查看） | 2小时 |
| 第九阶段 | 报告打印开发（FreeMarker模板、PDF生成、单个/批量打印） | 3小时 |
| 第十阶段 | 危急值管理开发（自动判断、报警通知、处理记录、统计查询） | 4小时 |
| 第十一阶段 | Feign接口开发（为其他微服务提供的接口） | 1小时 |
| 第十二阶段 | 测试与文档（单元测试、集成测试、开发报告编写） | 3小时 |
| **合计** | | **32小时** |

---

## 八、风险与注意事项

### 8.1 技术风险

| 风险项 | 风险等级 | 应对措施 |
|--------|---------|---------|
| 参考值比对算法复杂度较高 | 中 | 充分设计比对规则，覆盖各种参考范围格式，编写全面的单元测试 |
| 仪器数据格式多样化 | 高 | 支持多种文件格式（xlsx/xls/csv），提供灵活的列映射配置，编写数据校验和容错机制 |
| 报告状态机设计复杂 | 中 | 使用枚举+Map结构严格定义状态转换规则，编写完整的单元测试覆盖所有路径 |
| 危急值处理时效性要求 | 高 | 使用WebSocket实时推送告警，设置超时升级机制，定时任务扫描超时未处理的危急值 |
| PDF报告生成性能 | 中 | 使用缓存优化模板渲染，预编译FreeMarker模板，异步生成PDF |
| 大文件导入内存溢出 | 中 | 使用EasyExcel的SAX模式解析，分批处理数据，限制单次导入文件大小 |

### 8.2 注意事项

1. 检验服务是LIS系统的核心业务模块，业务逻辑复杂度最高，需充分理解检验业务流程后再进行开发。
2. 报告审核流程需符合医疗行业规范，审核人与录入人的隔离是重要的合规要求。
3. 危急值管理是医疗安全的核心环节，危急值的判断、通知和处理必须及时、准确、可追溯。
4. 参考值比对算法需覆盖各种参考范围格式，建议收集实际业务中的参考范围格式进行测试。
5. 仪器数据导入需考虑不同品牌、型号仪器的数据格式差异，提供灵活的映射配置。
6. 报告模板需符合医院检验报告的规范要求，建议与医院检验科确认报告格式。
7. 报告一旦发布即为正式医疗文书，不可随意修改或删除，回收操作需严格控制。
8. 与标本服务的状态同步至关重要，检验服务更新状态时必须确保标本服务同步更新，避免数据不一致。
9. FreeMarker模板中的变量需做空值处理，避免模板渲染时抛出异常。
10. 批量操作（批量审核、批量打印、批量导入）需控制单次最大数量，避免长事务和内存溢出。

---

*本文档由项目架构组编写，作为Agent-04（检验服务Agent）的开发任务书，指导其完成LIS系统检验管理微服务的开发工作。*

---

## 【Git工作流规范】

> 本章节为V3.0新增，所有规则具有强制约束力。详细规范参见 `/workspace/lis-project/docs/05-开发规范/Git版本控制规范.md`。

### 分支管理

- **工作分支**：`feature/agent-04-report-service`
- **修复分支**：`bugfix/agent-04-{description}`
- **禁止**直接在`main`或`develop`分支上提交任何代码
- 分支由项目经理（PM）创建，你只能在分配的分支上工作

### 提交规范

- **格式**：`[Agent-04] {类型}({范围}): {简述}`
- **类型**：feat（新功能）、fix（修复）、refactor（重构）、docs（文档）、test（测试）、chore（构建/配置）
- **示例**：`[Agent-04] feat(检验服务): 新增XXX功能`
- 每个功能点至少一次提交，禁止积压大量变更后一次性提交
- 提交前必须确保代码可编译、功能可运行

### 过程文档要求

每个功能点完成后，必须同步提交过程文档：

| 文档类型 | 存放路径 | 提交时机 |
|---------|---------|---------|
| 功能开发记录 | `/workspace/lis-project/docs/process/Agent-04/{功能名称}-开发记录.md` | 每个功能点完成时 |
| 变更日志 | `/workspace/lis-project/docs/process/Agent-04/CHANGELOG.md` | 每次提交时更新 |

过程文档模板参见：`/workspace/lis-project/docs/process/开发记录模板.md`

### 阶段交付要求

完成本阶段全部任务后，必须提交以下交付物（缺一不可）：

1. ✅ 阶段开发报告（按D09《开发报告模板》格式）
2. ✅ CHANGELOG变更日志
3. ✅ 所有功能点的开发记录（`docs/process/Agent-04/`目录下）
4. ✅ 数据库变更脚本（如有数据库结构变更，含回滚脚本）
5. ✅ 通知项目经理进行合并审查

### PM汇报机制

- **每完成一个功能点**：Git commit + 过程文档 + 通知PM（附commit hash）
- **遇到阻塞问题**：立即通知PM，不得自行跳过或绕过
- **每日结束前**：提交当日进度总结到CHANGELOG
- **合并请求**：全部完成后，等待PM审查代码和文档，经PM审批后方可合并到develop

### 违规后果

| 违规行为 | 后果 |
|---------|------|
| 直接在main/develop上提交 | PM执行git reset回退 + 警告 |
| 提交信息不规范 | PM驳回提交，要求修改 |
| 过程文档缺失 | PM驳回合并请求 |
| 未经审批自行合并 | PM回退合并 + 暂停Agent |
| 虚假代码/文档 | PM回退分支 + 按红线禁令R1处理 |
