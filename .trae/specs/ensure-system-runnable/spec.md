# 确保前后端系统可正常运行 - 修复规范

## Why

LIS系统前后端存在大量集成问题，包括致命的数据库配置错误、API路径不匹配、类型定义不一致等，导致系统无法正常启动和运行。需要系统性地修复所有阻碍前后端正常运行的问题。

## What Changes

### 后端致命问题修复
- **BREAKING**: 修复 BaseEntity 的 `id` 字段上错误的 `@TableLogic` 注解，否则所有基于ID的查询和删除操作完全失效
- **BREAKING**: 修复 lis-auth 引用不存在的 `lis_db` 数据库，导致 Auth 服务启动失败
- 修复 lis-hl7 缺少 `context-path: /hl7` 配置，网关路由无法到达
- 修复 AI 服务 Feign 客户端路径与实际控制器路径不匹配

### 后端严重问题修复
- 修复密码双重加密 Bug（Auth 加密后传给 User 服务又加密一次）
- 统一 JWT 签名算法（Auth 用 HS512，Common 用 HS256，不一致）
- 修复 AI 服务逻辑删除字段名 `deleteFlag` 与数据库列名 `del_flag` 不匹配
- 修复 Auth 服务 `refreshToken` 方法中 `status` 类型转换不安全
- 修复 hl7 服务 MySQL 默认密码与其他服务不一致
- 修复 `getUserInfoByUsername` 缺少 `deptName` 字段

### 前端严重问题修复
- 修复 `PageResult` 类型定义 `list` 与后端返回 `records` 不匹配
- 修复用户/角色管理 `el-switch` 状态类型不匹配（字符串 vs 数字）
- 添加缺失的路由定义（标本详情、设备详情/编辑、个人中心、修改密码等）
- 修复 AI 分析视图 `createAIAnalysis` 参数类型错误（传 number 而非对象）
- 修复 AI 分析结果数据结构不匹配（`result` 是 string 不是对象）
- 修复 `getReportsForAnalysis` 与 `getDiagnosisRecords` 同端点不同返回类型
- 修复 `getAIRuleCategories` 与 `getEnabledAIRules` 同端点不同返回类型

### 前端高危问题修复
- 修复工作量统计视图属性名不匹配（`totalSamples` vs `totalSpecimens` 等）
- 修复报告统计视图属性名不匹配（`totalCount` vs `total` 等）
- 修复设备详情视图参数类型不匹配（string vs number）
- 修复设备详情 `getEquipmentStatus` 返回值处理错误（当数组取但返回对象）
- 修复标本登记 `getDepartments` 返回数据字段不匹配（`name` vs `deptName`）
- 修复标本登记 `getTestItemCategories` 返回类型不匹配（期望对象数组但返回 string[]）
- 修复 `printReport` 返回类型声明为 void 但被当作 Blob 使用
- 修复菜单管理视图使用 `row.name` 但类型是 `menuName`
- 修复 TagsView 中硬编码的 dashboard 路径与实际路由不匹配

## Impact
- 受影响的代码：后端所有微服务、前端所有 API 文件和视图文件
- 受影响的功能：用户认证、标本管理、检验管理、设备管理、统计报表、AI辅助诊断

## ADDED Requirements

### Requirement: 后端服务可正常启动

系统 SHALL 确保所有后端微服务（Gateway、Auth、User、Specimen、Report、Equipment、HL7、Statistics、AI）可以正常启动，无启动失败或连接错误。

#### Scenario: Auth 服务正常启动
- **WHEN** 启动 lis-auth 服务
- **THEN** 服务在8081端口正常启动，不因数据库连接失败而崩溃

#### Scenario: HL7 服务正常启动并可通过网关访问
- **WHEN** 通过网关请求 `/hl7/**` 路径
- **THEN** 请求正确路由到 hl7 服务，返回正常响应

#### Scenario: 所有基于ID的数据库操作正常
- **WHEN** 执行 selectById、deleteById 等操作
- **THEN** MyBatis-Plus 正确生成 SQL，不因 @TableLogic 误用导致查询条件错误

### Requirement: 前后端数据交互正确

系统 SHALL 确保前端发送的请求路径、参数类型、HTTP方法与后端控制器完全匹配，后端返回的数据结构与前端类型定义一致。

#### Scenario: 分页查询数据正确显示
- **WHEN** 前端执行分页查询（用户列表、角色列表等）
- **THEN** 后端返回的 `records` 字段正确映射到前端 `list` 字段，列表数据正确显示

#### Scenario: AI 诊断功能正常
- **WHEN** 用户提交 AI 分析请求
- **THEN** 前端发送正确的 DiagnosisRequest 对象，AI 服务通过 Feign 正确调用标本和报告服务

#### Scenario: 统计图表正确渲染
- **WHEN** 用户访问统计页面
- **THEN** 前端属性名与后端返回数据字段名匹配，ECharts 图表正确渲染

### Requirement: 前端路由完整可用

系统 SHALL 确保所有视图中引用的路由路径都有对应的路由定义，不会跳转到404页面。

#### Scenario: 标本详情页可访问
- **WHEN** 用户点击标本列表中的查看详情
- **THEN** 正确跳转到标本详情页面，不出现404

#### Scenario: 设备详情页可访问
- **WHEN** 用户点击设备列表中的查看详情
- **THEN** 正确跳转到设备详情页面，不出现404

### Requirement: 密码修改功能正常

系统 SHALL 确保修改密码时不会出现双重加密问题。

#### Scenario: 用户修改密码后可正常登录
- **WHEN** 用户修改密码后使用新密码登录
- **THEN** 登录成功，密码只经过一次BCrypt加密

## MODIFIED Requirements

### Requirement: 用户状态管理

用户/角色的 `status` 字段 SHALL 统一使用数字类型（0=正常, 1=锁定, 2=禁用），前端 `el-switch` 组件使用数字类型的 active-value 和 inactive-value。

## REMOVED Requirements

无移除需求
