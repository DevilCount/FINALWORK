# Agent-03 标本服务Agent - 开发任务书

## 文档信息

| 项目名称 | 基于微服务架构的实验室管理系统（LIS） |
|---------|--------------------------------------|
| 文档版本 | V1.0.0 |
| 编写日期 | 2026年4月 |
| 任务书编号 | LIS-AGENT-03 |
| Agent编号 | Agent-03 |
| Agent角色 | 标本服务Agent |
| 负责模块 | 标本管理微服务（lis-specimen，端口8083） |
| 文档状态 | 正式发布 |

---

## 一、Agent基本信息

### 1.1 Agent概述

Agent-03（标本服务Agent）是LIS系统开发团队中负责标本全生命周期管理的核心角色。作为系统的"物流调度中心"，标本服务Agent承担着从标本登记、签收、入库到检验完成的全流程管理工作。标本是检验业务的物理载体，标本管理的规范性和效率直接影响到检验结果的准确性和报告的及时性。标本服务Agent需要设计完善的状态机模型，确保标本在每个流转环节都有明确的状态记录和可追溯的操作日志，同时通过条码技术实现标本的快速识别和精准追踪。

### 1.2 Agent职责范围

标本服务Agent的职责覆盖以下核心领域：

1. **标本登记**：实现标本信息的登记功能，包括生成唯一编号、条码生成、患者信息关联等。
2. **标本签收**：实现标本的签收确认功能，支持单个签收和批量签收，以及不合格标本的处理。
3. **标本入库**：实现标本的入库排号和管理功能，包括排号算法、入库操作和撤销入库。
4. **标本状态管理**：实现标本的状态机管理，定义清晰的状态流转规则。
5. **标本全流程追溯**：实现标本全生命周期的追溯查询，记录每个时间节点的操作信息。
6. **条码管理**：实现标本条码的生成、打印和补打功能。
7. **标本统计**：实现标本相关数据的统计分析功能。

### 1.3 协作关系

| 协作对象 | 协作内容 |
|---------|---------|
| Agent-00（架构师） | 依赖lis-common公共模块、Feign客户端接口、数据库初始化脚本 |
| Agent-01（前端工程师） | 提供标本登记、签收、入库、追溯、条码管理相关REST API接口 |
| Agent-02（用户服务） | 通过Feign调用获取用户信息、校验操作权限、获取部门信息 |
| Agent-04（检验服务） | 提供标本状态查询接口、标本信息查询接口（Feign调用）；接收检验完成通知更新标本状态 |
| Agent-05（设备服务） | 通过Feign调用获取设备信息，关联标本与检验设备 |
| Agent-07（统计服务） | 提供标本统计数据查询接口（Feign调用） |

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
| MySQL | 8.0 | 关系型数据库（lis_specimen库） |
| Redis | 7.0 | 缓存与分布式ID生成（原子自增编号） |
| MapStruct | 1.5.5.Final | 对象映射工具，DTO与Entity转换 |
| Hutool | 5.8.25 | Java工具类库 |
| Lombok | 1.18.30 | 代码简化工具 |
| ZXing | 3.5.2 | 条码生成库（Code128格式） |
| Spring Boot Redis | 2.7.x | Redis集成，用于原子自增编号 |

### 2.2 数据库信息

| 数据库名称 | 字符集 | 排序规则 | 说明 |
|-----------|--------|---------|------|
| lis_specimen | utf8mb4 | utf8mb4_general_ci | 标本管理数据库 |

**核心数据表**：

| 表名 | 说明 | 关键字段 |
|------|------|---------|
| specimen | 标本主表 | id, specimen_no, barcode, patient_id, patient_name, specimen_type, status, receive_time, warehouse_time, complete_time |
| specimen_item | 标本检验项目表 | id, specimen_id, item_code, item_name, item_category, result, unit, ref_range, status |
| specimen_trace | 标本追溯记录表 | id, specimen_id, operation, operator_id, operator_name, operation_time, remark |

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

### 任务一：标本登记

#### 1.1 标本信息登记

**目标**：实现标本信息的登记功能，生成唯一编号和条码，关联患者信息。

**接口定义**：

```
POST /api/specimen/register
Authorization: Bearer {accessToken}
Content-Type: application/json
```

**请求参数**：

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| patientId | Long | 否 | 患者ID（已有患者时传入） |
| patientName | String | 是 | 患者姓名 |
| gender | String | 是 | 性别（male/female） |
| age | String | 是 | 年龄（如"35岁"、"3月"） |
| idCard | String | 否 | 身份证号 |
| phone | String | 否 | 联系电话 |
| visitNo | String | 否 | 就诊卡号/门诊号 |
| department | String | 否 | 送检科室 |
| doctorName | String | 否 | 送检医生 |
| clinicalDiagnosis | String | 否 | 临床诊断 |
| specimenType | String | 是 | 标本类型（blood/urine/stool/body_fluid/other） |
| containerType | String | 是 | 容器类型（根据标本类型匹配） |
| collectSite | String | 否 | 采集部位 |
| collectTime | String | 是 | 采集时间（yyyy-MM-dd HH:mm:ss） |
| collector | String | 是 | 采集人 |
| items | List\<SpecimenItemDTO\> | 是 | 检验项目列表 |
| remark | String | 否 | 备注 |

**SpecimenItemDTO字段**：

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| itemCode | String | 是 | 项目编码 |
| itemName | String | 是 | 项目名称 |
| itemCategory | String | 是 | 项目分类（biochemistry/immunity/hematology/urine/microbiology） |

**响应参数**：

| 字段 | 类型 | 说明 |
|------|------|------|
| specimenId | Long | 标本ID |
| specimenNo | String | 标本编号（如SP20260406001） |
| barcode | String | 条码内容（Code128编码） |
| barcodeImageUrl | String | 条码图片Base64编码（用于前端展示） |
| status | String | 标本状态（registered） |

**业务逻辑**：

1. **参数校验**：患者基本信息、标本类型、采集时间、检验项目列表为必填项。
2. **生成唯一标本编号**：
   - 编号规则：`SP` + `yyyyMMdd` + `4位流水号`（如SP20260406001）。
   - 使用Redis原子自增生成流水号，Key格式为 `lis:specimen:no:{yyyyMMdd}`，每日自动重置。
   - Redis命令：`INCR lis:specimen:no:20260406`，返回值即为当日流水号。
   - 流水号格式化为4位，不足前补零。
3. **生成条码**：
   - 条码内容即为标本编号（specimenNo）。
   - 使用ZXing库生成Code128格式条码。
   - 条码图片转为Base64字符串返回给前端。
4. **患者信息处理**：
   - 如提供了patientId，校验患者是否存在（通过Feign调用检验服务）。
   - 如未提供patientId，仅记录患者基本信息，后续由检验服务完善患者档案。
5. **插入标本主表**（specimen表）：
   - 设置状态为"已登记"（registered）。
   - 记录登记人和登记时间。
6. **批量插入检验项目**（specimen_item表）：
   - 每个检验项目关联标本ID。
   - 项目状态初始化为"待检验"（pending）。
7. **记录追溯日志**（specimen_trace表）：
   - 操作类型：REGISTER（登记）。
   - 记录操作人、操作时间、操作内容。
8. **返回登记结果**：包含标本编号、条码内容和条码图片。

**技术要点**：

- Redis原子自增编号确保并发安全，使用 `INCR` 命令的原子性保证编号唯一。
- 每日编号Key需设置过期时间（48小时），避免Redis中积累过多无用Key。
- 条码生成使用ZXing库的 `Code128Writer`，条码宽度建议300px，高度建议100px。
- 标本登记与检验项目插入需在同一事务中完成。
- 追溯日志记录需异步执行，避免影响主业务流程性能。

#### 1.2 标本登记暂存

**接口定义**：

```
POST /api/specimen/register/draft
```

**功能说明**：将标本登记信息暂存为草稿，不生成标本编号和条码，不记录追溯日志。草稿数据存储在Redis中，Key格式为 `lis:specimen:draft:{userId}`，过期时间24小时。

**草稿提交接口**：

```
POST /api/specimen/register/submit-draft
```

**功能说明**：从Redis中读取草稿数据，执行正式登记流程（生成编号、条码、插入数据库）。

**验收标准**：

- 标本登记功能正常，唯一编号生成正确，无重复编号。
- 条码生成正确，Code128格式可被标准条码扫描枪识别。
- 检验项目批量插入正确，与标本关联关系无误。
- 追溯日志记录完整。
- 草稿暂存和提交功能正常。

---

### 任务二：标本签收

#### 2.1 单个标本签收

**目标**：实现单个标本的扫码签收功能。

**接口定义**：

```
POST /api/specimen/receive
Authorization: Bearer {accessToken}
Content-Type: application/json
```

**请求参数**：

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| barcode | String | 是 | 标本条码（扫码枪输入） |
| receiveStatus | String | 是 | 签收状态（normal-正常，abnormal-异常） |
| abnormalReason | String | 条件必填 | 异常原因（签收状态为abnormal时必填） |
| remark | String | 否 | 备注 |

**异常原因枚举**：

| 值 | 说明 |
|------|------|
| hemolysis | 溶血 |
| lipemia | 脂血 |
| insufficient | 量不足 |
| wrong_container | 容器错误 |
| no_label | 无标签 |
| expired | 标本过期 |
| other | 其他 |

**业务逻辑**：

1. 根据条码查询标本信息（specimen表）。
2. 校验标本状态：只有"已登记"（registered）状态的标本可以签收。
3. 如签收状态为"正常"：
   - 更新标本状态为"已签收"（received）。
   - 记录签收人和签收时间。
4. 如签收状态为"异常"：
   - 更新标本状态为"异常"（abnormal）。
   - 记录异常原因。
   - 异常标本不进入后续流程，等待退回或重新采集。
5. 记录追溯日志（specimen_trace表）：
   - 操作类型：RECEIVE（签收）或 REJECT（拒收）。
   - 记录签收状态、异常原因、操作人、操作时间。

#### 2.2 批量标本签收

**接口定义**：

```
POST /api/specimen/receive/batch
Authorization: Bearer {accessToken}
Content-Type: application/json
```

**请求参数**：

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| barcodes | List\<String\> | 是 | 条码列表（最多100个） |
| receiveStatus | String | 是 | 签收状态（normal/abnormal） |
| abnormalReason | String | 条件必填 | 异常原因 |
| remark | String | 否 | 备注 |

**业务逻辑**：

1. 批量查询条码对应的标本信息。
2. 过滤掉非"已登记"状态的标本，记录跳过原因。
3. 批量更新标本状态。
4. 批量插入追溯日志。
5. 返回签收结果汇总：成功数量、跳过数量、失败数量及详情。

**技术要点**：

- 批量操作使用MyBatis-Plus的 `updateBatchById` 和 `saveBatch` 方法，提升性能。
- 批量操作需控制单次最大数量（建议不超过100条），防止长事务。
- 批量操作失败时支持部分成功，返回每个标本的处理结果。
- 追溯日志批量插入可使用 `INSERT INTO ... VALUES (...), (...), ...` 语法优化。

#### 2.3 不合格标本处理

**接口定义**：

```
PUT /api/specimen/{specimenId}/handle-abnormal
Authorization: Bearer {accessToken}
Content-Type: application/json
```

**请求参数**：

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| handleType | String | 是 | 处理方式（recollect-重新采集，return-退回，discard-废弃） |
| handleRemark | String | 否 | 处理说明 |

**业务逻辑**：

1. 校验标本状态为"异常"（abnormal）。
2. 根据处理方式更新标本状态：
   - 重新采集（recollect）：状态改为"待重新采集"（pending_recollect）。
   - 退回（return）：状态改为"已退回"（returned）。
   - 废弃（discard）：状态改为"已废弃"（discarded）。
3. 记录追溯日志。

**验收标准**：

- 单个签收功能正常，条码扫描后正确更新标本状态。
- 批量签收功能正常，支持最多100个标本同时签收。
- 异常标本处理流程完整，处理方式记录正确。
- 签收后追溯日志记录完整。
- 非法状态转换被正确拦截并返回错误提示。

---

### 任务三：标本入库

#### 3.1 标本排号算法

**目标**：实现标本入库的自动排号功能，确保标本按合理的顺序进入检验流程。

**排号规则**：

1. **优先级排序**：
   - 急诊标本优先（标记为urgent的标本排号权重最高）。
   - 按签收时间升序排列（先签收先入库）。
   - 同一患者的多个标本尽量相邻排列。
2. **排号算法**：
   - 使用Redis原子自增生成入库序号，Key格式为 `lis:specimen:warehouse:no:{yyyyMMdd}`。
   - 序号每日自动重置。
   - 急诊标本使用独立的序号段（如9001起），普通标本从0001开始。
3. **工作站分配**：
   - 根据标本类型和检验项目自动分配检验工作站。
   - 分配规则可配置（通过系统参数或字典数据配置）。

**排号接口**：

```
POST /api/specimen/warehouse/arrange
Authorization: Bearer {accessToken}
Content-Type: application/json
```

**请求参数**：

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| specimenIds | List\<Long\> | 是 | 待排号标本ID列表 |
| workstationId | Long | 否 | 指定工作站（不指定则自动分配） |

**响应参数**：

| 字段 | 类型 | 说明 |
|------|------|------|
| results | List\<ArrangeResult\> | 排号结果列表 |

**ArrangeResult字段**：

| 字段 | 类型 | 说明 |
|------|------|------|
| specimenId | Long | 标本ID |
| specimenNo | String | 标本编号 |
| sequenceNo | String | 入库序号 |
| workstationId | Long | 工作站ID |
| workstationName | String | 工作站名称 |
| priority | String | 优先级（normal/urgent） |

#### 3.2 标本入库

**接口定义**：

```
POST /api/specimen/warehouse
Authorization: Bearer {accessToken}
Content-Type: application/json
```

**请求参数**：

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| specimenIds | List\<Long\> | 是 | 待入库标本ID列表 |
| workstationId | Long | 否 | 指定工作站ID |

**业务逻辑**：

1. 校验所有标本状态为"已签收"（received）。
2. 执行排号算法，生成入库序号和工作站分配。
3. 批量更新标本状态为"已入库"（warehoused）。
4. 记录入库时间、入库序号、分配工作站。
5. 批量记录追溯日志（操作类型：WAREHOUSE）。
6. 返回入库结果汇总。

#### 3.3 撤销入库

**接口定义**：

```
PUT /api/specimen/warehouse/cancel/{specimenId}
Authorization: Bearer {accessToken}
```

**业务逻辑**：

1. 校验标本状态为"已入库"（warehoused），且未被检验服务领取（无检验中记录）。
2. 更新标本状态回退为"已签收"（received）。
3. 清除入库序号和工作站分配信息。
4. 记录追溯日志（操作类型：CANCEL_WAREHOUSE）。

**技术要点**：

- 入库排号使用Redis原子自增，确保并发场景下序号唯一且连续。
- 撤销入库需检查标本是否已被下游服务（检验服务）处理，避免数据不一致。
- 入库操作涉及批量更新，需使用事务保障数据一致性。
- 排号算法应支持配置化，便于根据实际业务需求调整优先级规则。

**验收标准**：

- 排号算法正确执行，急诊标本优先排号。
- 标本入库功能正常，状态更新正确。
- 工作站自动分配逻辑正确。
- 撤销入库功能正常，状态回退正确。
- 已被检验的标本不允许撤销入库。
- 追溯日志记录完整。

---

### 任务四：标本状态管理

#### 4.1 标本状态机设计

**目标**：定义标本的完整状态流转规则，确保状态转换的合法性和可追溯性。

**状态定义**：

| 状态编码 | 状态名称 | 说明 |
|---------|---------|------|
| registered | 已登记 | 标本信息已录入系统 |
| received | 已签收 | 标本已通过签收确认 |
| warehoused | 已入库 | 标本已排号入库 |
| testing | 检验中 | 标本正在检验（由检验服务更新） |
| completed | 已完成 | 检验完成，报告已发布（由检验服务更新） |
| abnormal | 异常 | 标本不合格 |
| pending_recollect | 待重新采集 | 异常标本需重新采集 |
| returned | 已退回 | 异常标本已退回 |
| discarded | 已废弃 | 标本已废弃处理 |

**状态流转规则**：

```
registered  --> received        (签收)
registered  --> abnormal        (签收异常)
received    --> warehoused      (入库)
warehoused  --> testing         (开始检验，由检验服务触发)
warehoused  --> received        (撤销入库)
testing     --> completed       (检验完成，由检验服务触发)
testing     --> warehoused      (检验退回，由检验服务触发)
abnormal    --> pending_recollect (重新采集)
abnormal    --> returned        (退回)
abnormal    --> discarded       (废弃)
pending_recollect --> registered (重新登记)
```

**状态机实现**：

```java
// 位置：com.lis.specimen.enums.SpecimenStatusEnum
// 使用枚举定义所有状态
// 每个状态定义其允许的下一状态列表

// 位置：com.lis.specimen.service.SpecimenStatusService
// 核心方法：
//   - canTransition(currentStatus, targetStatus) -> boolean
//   - transition(specimenId, targetStatus, operatorId, remark) -> void
```

**状态转换校验接口**：

```
PUT /api/specimen/{specimenId}/status
Authorization: Bearer {accessToken}
Content-Type: application/json
```

**请求参数**：

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| targetStatus | String | 是 | 目标状态 |
| remark | String | 否 | 状态变更备注 |

**业务逻辑**：

1. 查询标本当前状态。
2. 调用状态机校验当前状态是否允许转换到目标状态。
3. 如不允许，抛出 `BusinessException`，提示"标本状态不允许从{当前状态}变更为{目标状态}"。
4. 如允许，更新标本状态，记录状态变更时间和操作人。
5. 记录追溯日志。

**技术要点**：

- 状态机使用枚举+Map结构实现，每个状态枚举值定义其允许的下一状态集合。
- 状态转换方法使用 `@Transactional` 注解确保原子性。
- 状态转换失败时抛出明确的业务异常，包含当前状态和目标状态信息。
- 状态变更需记录审计日志，便于问题追溯。

**验收标准**：

- 状态机定义完整，覆盖所有合法状态转换路径。
- 非法状态转换被正确拦截。
- 状态变更后追溯日志记录完整。
- 状态机逻辑清晰，易于维护和扩展。

---

### 任务五：标本全流程追溯

#### 5.1 追溯记录写入

**目标**：在标本的每个流转环节自动记录追溯日志，形成完整的操作时间线。

**追溯日志表结构**（specimen_trace）：

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 主键（雪花算法） |
| specimen_id | BIGINT | 标本ID |
| specimen_no | VARCHAR(32) | 标本编号（冗余，便于查询） |
| operation | VARCHAR(32) | 操作类型（枚举值） |
| operator_id | BIGINT | 操作人ID |
| operator_name | VARCHAR(64) | 操作人姓名（冗余） |
| operation_time | DATETIME | 操作时间 |
| content | VARCHAR(500) | 操作内容描述 |
| remark | VARCHAR(500) | 备注 |
| create_time | DATETIME | 创建时间 |

**操作类型枚举**：

| 操作编码 | 操作名称 | 记录时机 |
|---------|---------|---------|
| REGISTER | 标本登记 | 标本登记完成时 |
| RECEIVE | 标本签收 | 标本签收确认时 |
| REJECT | 标本拒收 | 标本签收异常时 |
| WAREHOUSE | 标本入库 | 标本入库完成时 |
| CANCEL_WAREHOUSE | 撤销入库 | 撤销入库操作时 |
| START_TEST | 开始检验 | 检验服务通知开始检验时 |
| COMPLETE_TEST | 检验完成 | 检验服务通知检验完成时 |
| REPORT_PUBLISHED | 报告发布 | 检验服务通知报告发布时 |
| HANDLE_ABNORMAL | 异常处理 | 不合格标本处理时 |
| RECOLLECT | 重新采集 | 重新登记时 |

**追溯日志写入规范**：

- 所有追溯日志在Service层写入，不暴露为独立的API接口。
- 日志写入使用异步方式（`@Async`），避免影响主业务流程性能。
- 操作人信息从请求头上下文中获取。
- 操作内容自动生成标准描述，如"标本SP20260406001由张三签收，签收结果：正常"。

#### 5.2 追溯查询接口

**接口定义**：

```
GET /api/specimen/{specimenId}/trace
Authorization: Bearer {accessToken}
```

**响应参数**：

| 字段 | 类型 | 说明 |
|------|------|------|
| specimenInfo | SpecimenVO | 标本基本信息 |
| traceList | List\<TraceVO\> | 追溯记录列表（按时间升序） |
| currentNode | String | 当前状态节点名称 |

**TraceVO字段**：

| 字段 | 类型 | 说明 |
|------|------|------|
| operation | String | 操作类型 |
| operationName | String | 操作名称 |
| operatorName | String | 操作人姓名 |
| operationTime | String | 操作时间 |
| content | String | 操作内容描述 |
| remark | String | 备注 |

**按条件追溯查询接口**：

```
GET /api/specimen/trace/search
Authorization: Bearer {accessToken}
```

**请求参数**：

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| specimenNo | String | 否 | 标本编号 |
| patientName | String | 否 | 患者姓名 |
| operation | String | 否 | 操作类型 |
| beginTime | String | 否 | 操作时间起始 |
| endTime | String | 否 | 操作时间结束 |
| pageNum | Integer | 否 | 页码 |
| pageSize | Integer | 否 | 每页条数 |

**技术要点**：

- 追溯查询需关联specimen表和specimen_trace表，使用LEFT JOIN确保标本信息完整。
- 追溯记录按操作时间升序排列，形成完整的时间线。
- 查询性能优化：在specimen_trace表的specimen_id和operation_time字段上建立联合索引。
- 大数据量追溯查询需支持分页，避免一次性返回过多数据。

**验收标准**：

- 标本每个流转环节均正确记录追溯日志。
- 追溯日志内容完整，包含操作类型、操作人、操作时间、操作内容。
- 追溯查询接口返回正确的时间线数据。
- 异步日志写入不影响主业务流程性能。
- 追溯查询支持多条件组合搜索和分页。

---

### 任务六：条码管理

#### 6.1 条码生成

**目标**：实现标本条码的生成功能，支持Code128格式。

**条码生成接口**：

```
POST /api/specimen/barcode/generate
Authorization: Bearer {accessToken}
Content-Type: application/json
```

**请求参数**：

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| specimenNo | String | 是 | 标本编号 |
| width | Integer | 否 | 条码宽度（默认300px） |
| height | Integer | 否 | 条码高度（默认100px） |

**响应参数**：

| 字段 | 类型 | 说明 |
|------|------|------|
| barcode | String | 条码内容（即标本编号） |
| base64Image | String | 条码图片Base64编码 |
| format | String | 条码格式（Code128） |

**技术要点**：

- 使用ZXing库的 `Code128Writer` 生成条码。
- 条码图片输出为PNG格式，转为Base64字符串。
- 条码内容即为标本编号，确保条码与标本一一对应。
- 条码生成参数（宽度、高度）支持自定义，默认值需满足标准条码扫描枪的识别要求。
- 条码图片需包含适当的留白区域（quiet zone），确保扫描识别率。

#### 6.2 条码打印

**接口定义**：

```
GET /api/specimen/barcode/print/{specimenId}
Authorization: Bearer {accessToken}
```

**响应**：返回条码图片文件（PNG格式），Content-Type为 `image/png`。

**功能说明**：

- 根据标本ID查询标本信息，生成条码图片。
- 条码图片包含：条码图形、标本编号文本、患者姓名、标本类型。
- 支持批量打印：一次生成多个标本的条码图片，合并为一张打印页面。

**批量打印接口**：

```
POST /api/specimen/barcode/print/batch
Authorization: Bearer {accessToken}
Content-Type: application/json
```

**请求参数**：

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| specimenIds | List\<Long\> | 是 | 标本ID列表（最多50个） |

**响应**：返回合并后的条码打印页面图片（PNG格式）。

#### 6.3 条码补打

**接口定义**：

```
POST /api/specimen/barcode/reprint/{specimenId}
Authorization: Bearer {accessToken}
```

**功能说明**：

- 补打已生成条码的标本。
- 记录补打日志（操作人、补打时间、补打原因）。
- 补打次数限制：单个标本每日最多补打3次，超过限制需管理员审批。

**技术要点**：

- 条码图片生成使用Java 2D Graphics API绘制，包含条码图形和文本信息。
- 批量打印使用 `BufferedImage` 拼接多张条码图片。
- 打印日志记录在specimen_trace表中，操作类型为REPRINT。
- 补打次数使用Redis计数，Key格式为 `lis:specimen:reprint:{specimenId}:{yyyyMMdd}`，过期时间24小时。

**验收标准**：

- 条码生成正确，Code128格式可被标准条码扫描枪识别。
- 条码打印功能正常，图片清晰。
- 批量打印功能正常，多个条码正确排列。
- 条码补打功能正常，补打次数限制有效。
- 补打日志记录完整。

---

### 任务七：标本统计

#### 7.1 标本统计概览

**接口定义**：

```
GET /api/specimen/statistics/overview
Authorization: Bearer {accessToken}
```

**请求参数**：

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| date | String | 否 | 统计日期（默认今天，格式yyyy-MM-dd） |
| department | String | 否 | 科室筛选 |

**响应参数**：

| 字段 | 类型 | 说明 |
|------|------|------|
| totalRegister | Long | 登记总数 |
| totalReceive | Long | 签收总数 |
| totalWarehouse | Long | 入库总数 |
| totalComplete | Long | 完成总数 |
| totalAbnormal | Long | 异常数 |
| receiveRate | String | 签收率（百分比） |
| completeRate | String | 完成率（百分比） |
| abnormalRate | String | 异常率（百分比） |

#### 7.2 标本类型统计

**接口定义**：

```
GET /api/specimen/statistics/by-type
Authorization: Bearer {accessToken}
```

**请求参数**：

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| beginDate | String | 否 | 起始日期 |
| endDate | String | 否 | 结束日期 |

**响应参数**：

返回按标本类型分组的统计数据列表：

| 字段 | 类型 | 说明 |
|------|------|------|
| specimenType | String | 标本类型 |
| specimenTypeName | String | 标本类型名称 |
| count | Long | 数量 |
| percentage | String | 占比（百分比） |

#### 7.3 标本趋势统计

**接口定义**：

```
GET /api/specimen/statistics/trend
Authorization: Bearer {accessToken}
```

**请求参数**：

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| beginDate | String | 是 | 起始日期 |
| endDate | String | 是 | 结束日期 |
| granularity | String | 否 | 统计粒度（day/week/month，默认day） |

**响应参数**：

返回按时间维度排列的趋势数据列表：

| 字段 | 类型 | 说明 |
|------|------|------|
| date | String | 日期 |
| registerCount | Long | 登记数量 |
| receiveCount | Long | 签收数量 |
| completeCount | Long | 完成数量 |
| abnormalCount | Long | 异常数量 |

**技术要点**：

- 统计查询使用MySQL的聚合函数（COUNT、SUM）和GROUP BY语句。
- 日期范围查询需在specimen表的create_time字段上建立索引。
- 统计数据可缓存到Redis中，设置5分钟过期时间，减少数据库查询压力。
- 趋势统计的周粒度和月粒度使用MySQL的日期函数（WEEK、MONTH）进行分组。

**验收标准**：

- 统计概览数据准确，各指标计算正确。
- 标本类型统计分组正确，占比计算准确。
- 趋势统计数据正确，支持日/周/月三种粒度。
- 统计接口响应时间不超过500ms。
- 缓存机制有效，重复查询命中缓存。

---

## 四、输出路径与交付物

### 4.1 源代码输出

| 输出内容 | 路径 |
|---------|------|
| 标本服务源代码 | `/workspace/lis-project/src/backend/lis-specimen/` |
| Controller层 | `/workspace/lis-project/src/backend/lis-specimen/src/main/java/com/lis/specimen/controller/` |
| Service层 | `/workspace/lis-project/src/backend/lis-specimen/src/main/java/com/lis/specimen/service/` |
| Mapper层 | `/workspace/lis-project/src/backend/lis-specimen/src/main/java/com/lis/specimen/mapper/` |
| Entity层 | `/workspace/lis-project/src/backend/lis-specimen/src/main/java/com/lis/specimen/entity/` |
| DTO/VO层 | `/workspace/lis-project/src/backend/lis-specimen/src/main/java/com/lis/specimen/dto/` |
| 枚举类 | `/workspace/lis-project/src/backend/lis-specimen/src/main/java/com/lis/specimen/enums/` |
| Mapper XML | `/workspace/lis-project/src/backend/lis-specimen/src/main/resources/mapper/` |

### 4.2 项目结构要求

```
lis-specimen/
├── pom.xml
└── src/main/java/com/lis/specimen/
    ├── LisSpecimenApplication.java       # 启动类
    ├── controller/
    │   ├── SpecimenRegisterController.java   # 标本登记接口
    │   ├── SpecimenReceiveController.java    # 标本签收接口
    │   ├── SpecimenWarehouseController.java  # 标本入库接口
    │   ├── SpecimenTraceController.java      # 标本追溯接口
    │   ├── SpecimenBarcodeController.java    # 条码管理接口
    │   └── SpecimenStatisticsController.java # 标本统计接口
    ├── service/
    │   ├── SpecimenService.java              # 标本服务接口
    │   ├── SpecimenStatusService.java        # 标本状态机服务
    │   ├── SpecimenTraceService.java         # 标本追溯服务
    │   ├── BarcodeService.java               # 条码生成服务
    │   ├── SpecimenStatisticsService.java    # 标本统计服务
    │   └── impl/
    │       ├── SpecimenServiceImpl.java
    │       ├── SpecimenStatusServiceImpl.java
    │       ├── SpecimenTraceServiceImpl.java
    │       ├── BarcodeServiceImpl.java
    │       └── SpecimenStatisticsServiceImpl.java
    ├── mapper/
    │   ├── SpecimenMapper.java
    │   ├── SpecimenItemMapper.java
    │   └── SpecimenTraceMapper.java
    ├── entity/
    │   ├── Specimen.java
    │   ├── SpecimenItem.java
    │   └── SpecimenTrace.java
    ├── dto/
    │   ├── SpecimenRegisterDTO.java
    │   ├── SpecimenReceiveDTO.java
    │   ├── SpecimenItemDTO.java
    │   └── SpecimenQueryDTO.java
    ├── vo/
    │   ├── SpecimenVO.java
    │   ├── SpecimenTraceVO.java
    │   ├── SpecimenStatisticsVO.java
    │   └── ArrangeResultVO.java
    ├── enums/
    │   ├── SpecimenStatusEnum.java           # 标本状态枚举
    │   ├── SpecimenTypeEnum.java             # 标本类型枚举
    │   ├── AbnormalReasonEnum.java           # 异常原因枚举
    │   └── TraceOperationEnum.java           # 追溯操作类型枚举
    ├── config/
    │   └── MyBatisPlusConfig.java            # MyBatis-Plus配置
    ├── converter/
    │   ├── SpecimenConverter.java            # MapStruct转换器
    │   └── TraceConverter.java
    └── util/
        └── BarcodeUtils.java                 # 条码生成工具类
```

### 4.3 开发报告

| 输出内容 | 路径 |
|---------|------|
| 标本服务开发报告 | `/workspace/lis-project/reports/agent-reports/Agent-03-标本服务-开发报告.md` |

---

## 五、技术约束与规范要求

### 5.1 编码规范

- 所有Java代码必须符合 `/workspace/lis-project/docs/05-开发规范/编码规范.md` 中的后端编码规范。
- 包命名使用 `com.lis.specimen` 格式。
- 类命名使用大驼峰（UpperCamelCase），方法命名使用小驼峰（lowerCamelCase）。
- 所有公共方法必须提供完整的Javadoc注释。
- 代码中禁止出现魔法值，所有常量必须定义在枚举类或常量类中。
- 使用Lombok简化实体类代码。

### 5.2 接口规范

- 所有API接口必须符合 `/workspace/lis-project/docs/02-设计文档/API接口设计规范.md`。
- 接口URL遵循RESTful风格，统一使用 `/api/specimen/` 前缀。
- 统一使用 `Result<T>` 封装响应结果。
- 统一异常处理，返回标准错误格式。
- 接口需添加 `@RequirePermission` 注解进行权限控制。

### 5.3 数据库规范

- 所有数据库设计必须符合 `/workspace/lis-project/docs/02-设计文档/数据库设计说明书.md`。
- 使用MyBatis-Plus作为ORM框架，简单CRUD使用内置方法，复杂查询使用自定义XML。
- 禁止使用物理删除，统一使用逻辑删除（`@TableLogic`）。
- 主键使用雪花算法（`@TableId(type = IdType.ASSIGN_ID)`）。
- 所有表必须包含公共审计字段。

### 5.4 Redis使用规范

- 标本编号生成使用Redis的 `INCR` 命令，确保原子性和唯一性。
- 每日编号Key需设置过期时间（48小时），避免Key堆积。
- 补打次数计数使用Redis的 `INCR` 命令，设置24小时过期。
- 统计数据缓存使用Redis的 `SET EX` 命令，设置合理的过期时间。
- Redis操作需考虑异常情况，Redis不可用时编号生成应降级为数据库序列方案。

### 5.5 性能要求

- 单个标本登记接口响应时间不超过200ms。
- 批量签收接口（100个标本）响应时间不超过2秒。
- 追溯查询接口响应时间不超过500ms。
- 统计查询接口响应时间不超过500ms。
- 条码生成接口响应时间不超过300ms。

---

## 六、验收标准

### 6.1 功能验收

- [ ] 标本登记功能完整：编号生成、条码生成、患者关联、检验项目关联均正常工作。
- [ ] 标本签收功能完整：单个签收、批量签收、异常标本处理均正常工作。
- [ ] 标本入库功能完整：排号算法正确、入库操作正常、撤销入库正常。
- [ ] 标本状态机完整：所有状态转换规则正确，非法转换被拦截。
- [ ] 标本追溯功能完整：每个流转环节记录追溯日志，追溯查询结果正确。
- [ ] 条码管理功能完整：条码生成、打印、补打均正常工作。
- [ ] 标本统计功能完整：概览统计、类型统计、趋势统计均正确。
- [ ] 所有接口返回格式符合API接口设计规范。

### 6.2 质量验收

- [ ] 代码符合编码规范，无明显的代码坏味道。
- [ ] 所有公共方法提供完整的Javadoc注释。
- [ ] 单元测试覆盖率不低于70%，核心业务逻辑（编号生成、状态机、条码生成）覆盖率不低于90%。
- [ ] Redis原子自增编号在并发场景下无重复。
- [ ] 状态机逻辑严谨，无遗漏的非法状态转换路径。
- [ ] 批量操作性能满足要求。

### 6.3 安全验收

- [ ] 接口权限校验有效，未授权用户无法操作标本。
- [ ] 追溯日志不可篡改，记录完整。
- [ ] SQL注入防护有效。
- [ ] 补打次数限制有效。

### 6.4 文档验收

- [ ] 标本服务开发报告编写完成，内容详实。
- [ ] 状态机流转图包含在开发报告中。
- [ ] 接口文档完整，覆盖所有API端点。

---

## 七、开发进度安排

| 阶段 | 任务内容 | 预计工时 |
|------|---------|---------|
| 第一阶段 | 项目搭建（模块初始化、依赖配置、数据库连接、MyBatis-Plus配置） | 2小时 |
| 第二阶段 | 标本登记开发（编号生成、条码生成、登记接口、草稿暂存） | 4小时 |
| 第三阶段 | 标本签收开发（单个签收、批量签收、异常标本处理） | 3小时 |
| 第四阶段 | 标本入库开发（排号算法、入库操作、撤销入库） | 3小时 |
| 第五阶段 | 标本状态管理开发（状态机设计、状态转换校验） | 2小时 |
| 第六阶段 | 标本追溯开发（追溯日志写入、追溯查询接口） | 2小时 |
| 第七阶段 | 条码管理开发（条码生成、打印、补打） | 2小时 |
| 第八阶段 | 标本统计开发（概览统计、类型统计、趋势统计） | 2小时 |
| 第九阶段 | Feign接口开发（为其他微服务提供的接口） | 1小时 |
| 第十阶段 | 测试与文档（单元测试、集成测试、开发报告编写） | 3小时 |
| **合计** | | **24小时** |

---

## 八、风险与注意事项

### 8.1 技术风险

| 风险项 | 风险等级 | 应对措施 |
|--------|---------|---------|
| Redis不可用导致编号生成失败 | 中 | 设计降级方案：Redis不可用时使用数据库序列（如MySQL的AUTO_INCREMENT或SEQUENCE）生成编号 |
| 并发场景下标本编号重复 | 低 | 使用Redis的INCR原子命令，确保编号唯一；数据库层面添加唯一索引作为兜底 |
| 条码生成图片质量不佳导致扫描失败 | 低 | 使用标准ZXing库，设置合理的条码尺寸和分辨率，测试多种扫描枪兼容性 |
| 批量操作性能问题 | 中 | 控制单次批量最大数量，使用批量SQL优化，分批次处理大数据量 |
| 状态机设计遗漏导致非法状态转换 | 中 | 使用枚举+Map结构严格定义状态转换规则，编写完整的单元测试覆盖所有转换路径 |

### 8.2 注意事项

1. 标本编号是标本的唯一标识，一旦生成不可修改，编号生成逻辑需保证绝对可靠。
2. 状态机是标本管理的核心，任何状态变更都必须经过状态机校验，禁止直接修改数据库状态字段。
3. 追溯日志是医疗质量管理的重要依据，必须确保每条日志的准确性和完整性，不可遗漏。
4. 条码是标本在物理世界中的标识，条码生成质量直接影响标本识别效率，需充分测试。
5. 标本服务与检验服务存在紧密的上下游关系，状态变更需通过Feign调用或消息队列通知。
6. 批量操作需考虑事务边界，避免长事务导致数据库连接池耗尽。
7. 统计查询涉及大量数据聚合，需注意SQL性能优化，合理使用索引。
8. 异常标本的处理流程需符合医疗行业规范，异常原因分类需完整覆盖常见情况。

---

*本文档由项目架构组编写，作为Agent-03（标本服务Agent）的开发任务书，指导其完成LIS系统标本管理微服务的开发工作。*

---

## 【Git工作流规范】

> 本章节为V3.0新增，所有规则具有强制约束力。详细规范参见 `/workspace/lis-project/docs/05-开发规范/Git版本控制规范.md`。

### 分支管理

- **工作分支**：`feature/agent-03-specimen-service`
- **修复分支**：`bugfix/agent-03-{description}`
- **禁止**直接在`main`或`develop`分支上提交任何代码
- 分支由项目经理（PM）创建，你只能在分配的分支上工作

### 提交规范

- **格式**：`[Agent-03] {类型}({范围}): {简述}`
- **类型**：feat（新功能）、fix（修复）、refactor（重构）、docs（文档）、test（测试）、chore（构建/配置）
- **示例**：`[Agent-03] feat(标本服务): 新增XXX功能`
- 每个功能点至少一次提交，禁止积压大量变更后一次性提交
- 提交前必须确保代码可编译、功能可运行

### 过程文档要求

每个功能点完成后，必须同步提交过程文档：

| 文档类型 | 存放路径 | 提交时机 |
|---------|---------|---------|
| 功能开发记录 | `/workspace/lis-project/docs/process/Agent-03/{功能名称}-开发记录.md` | 每个功能点完成时 |
| 变更日志 | `/workspace/lis-project/docs/process/Agent-03/CHANGELOG.md` | 每次提交时更新 |

过程文档模板参见：`/workspace/lis-project/docs/process/开发记录模板.md`

### 阶段交付要求

完成本阶段全部任务后，必须提交以下交付物（缺一不可）：

1. ✅ 阶段开发报告（按D09《开发报告模板》格式）
2. ✅ CHANGELOG变更日志
3. ✅ 所有功能点的开发记录（`docs/process/Agent-03/`目录下）
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
