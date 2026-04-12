# Tasks

## Phase 1: 后端致命问题修复

- [x] Task 1: 修复 BaseEntity 的 @TableLogic 注解错误
  - [x] SubTask 1.1: 删除 lis-user BaseEntity.java 中 id 字段上的 @TableLogic 注解
  - [x] SubTask 1.2: 检查其他微服务的 BaseEntity 是否有同样问题，一并修复
  - [x] SubTask 1.3: 确认 UserDO 和 DeptDO 的 delFlag 字段上 @TableLogic 注解保留

- [x] Task 2: 修复 Auth 服务数据库配置
  - [x] SubTask 2.1: 在 Auth 启动类上添加 exclude = {DataSourceAutoConfiguration.class, DruidDataSourceAutoConfigure.class}，或移除不需要的 datasource/mybatis/druid 依赖
  - [x] SubTask 2.2: 清理 Auth 的 application.yaml 和 bootstrap.yaml 中的 datasource 配置

- [x] Task 3: 修复 HL7 服务缺少 context-path
  - [x] SubTask 3.1: 在 lis-hl7 的 application.yaml 中添加 server.servlet.context-path: /hl7
  - [x] SubTask 3.2: 修复 hl7 服务 MySQL 默认密码为 1234（与其它服务一致）
  - [x] SubTask 3.3: 添加 spring.mvc.pathmatch.matching-strategy: ant_path_matcher 配置

- [x] Task 4: 修复 AI 服务 Feign 客户端路径
  - [x] SubTask 4.1: 修复 ReportFeignClient 路径：/{id} → /apply/{reportId}，删除不存在的 /no/{reportNo}
  - [x] SubTask 4.2: 修复 SpecimenFeignClient 路径：/{id} → /getById/{id}，/no/{specimenNo} → /getBySpecimenNo?specimenNo=xxx
  - [x] SubTask 4.3: 修复 AI 服务逻辑删除字段名 deleteFlag → delFlag

## Phase 2: 后端严重问题修复

- [x] Task 5: 修复密码双重加密 Bug
  - [x] SubTask 5.1: 修改 AuthController 的 changePassword 方法，传递明文密码而非已加密密码
  - [x] SubTask 5.2: 确认 UserServiceImpl.resetPassword 方法负责加密

- [x] Task 6: 统一 JWT 签名算法
  - [x] SubTask 6.1: 确认 Gateway 使用 Auth 的 JwtTokenProvider 生成的 Token 格式进行验证
  - [x] SubTask 6.2: 确保 Common 模块的 JwtUtils 不被用于验证 Auth 生成的 Token，或统一算法为 HS512

- [x] Task 7: 修复 Auth 服务 refreshToken 方法中 status 类型转换
  - [x] SubTask 7.1: 将 (Integer) userMap.get("status") 改为 Integer.parseInt(statusObj.toString()) 方式

- [x] Task 8: 修复 getUserInfoByUsername 缺少 deptName
  - [x] SubTask 8.1: 在 UserServiceImpl.getUserInfoByUsername 中查询部门名称并添加到返回 Map

## Phase 3: 前端核心类型修复

- [x] Task 9: 修复 PageResult 类型定义
  - [x] SubTask 9.1: 将 api.d.ts 中 PageResult 的 list 字段改为 records，或添加 records 别名
  - [x] SubTask 9.2: 更新所有视图中使用 result.list 的地方改为 result.records
  - [x] SubTask 9.3: 修复 user.ts 和 role.ts 中 mapUserListFromBackend/mapRoleListFromBackend 的字段映射

- [x] Task 10: 修复用户/角色状态类型不匹配
  - [x] SubTask 10.1: 修改 user/index.vue 中 el-switch 的 active-value/inactive-value 为数字 0/2
  - [x] SubTask 10.2: 修改 role/index.vue 中 el-switch 的 active-value/inactive-value 为数字 0/2
  - [x] SubTask 10.3: 确认 updateUserStatus/updateRoleStatus 接收数字类型参数

## Phase 4: 前端路由补全

- [x] Task 11: 添加缺失的路由定义
  - [x] SubTask 11.1: 在 permission.ts 动态路由中添加 /specimen/detail/:id 路由
  - [x] SubTask 11.2: 添加 /equipment/detail/:id 和 /equipment/edit/:id 路由
  - [x] SubTask 11.3: 添加 /equipment/maintenance/add 路由
  - [x] SubTask 11.4: 添加 /profile 和 /password 路由（个人中心、修改密码）
  - [x] SubTask 11.5: 修复 TagsView 中硬编码的 /dashboard 路径为 /dashboard/index

## Phase 5: 前端 AI 模块修复

- [x] Task 12: 修复 AI 分析视图问题
  - [x] SubTask 12.1: 修复 createAIAnalysis 调用，传入 { reportId: selectedReport.value.id } 对象
  - [x] SubTask 12.2: 修复 AI 分析结果数据结构访问，使用 analysisResult.summary 而非 analysisResult.result?.summary
  - [x] SubTask 12.3: 修复 getReportsForAnalysis 端点，改为调用报告服务的接口而非 /ai/diagnosis/records
  - [x] SubTask 12.4: 修复 getAIRuleCategories 与 getEnabledAIRules 同端点问题，修正其中一个的端点路径

## Phase 6: 前端统计模块修复

- [x] Task 13: 修复工作量统计视图
  - [x] SubTask 13.1: 修复属性名：totalSamples → totalSpecimens, completedSamples → completedReports, pendingSamples → pendingReports
  - [x] SubTask 13.2: 修复图表属性名：categories → xAxis
  - [x] SubTask 13.3: 添加缺失的 avgTurnaroundTime 字段到 WorkloadStatistics 类型

- [x] Task 14: 修复报告统计视图
  - [x] SubTask 14.1: 修复属性名：totalCount → total, normalCount → completed
  - [x] SubTask 14.2: 修复图表数据访问，使用 xAxis/series 而非 pieData/trendData/barData

## Phase 7: 前端设备/标本/报告模块修复

- [x] Task 15: 修复设备详情视图
  - [x] SubTask 15.1: 将 equipmentId 从 string 转为 number 传给 API 函数
  - [x] SubTask 15.2: 修复 getEquipmentStatus 返回值处理，直接赋值而非当数组取 [0]
  - [x] SubTask 15.3: 修复 maintenanceFormData 中 equipmentId 类型为 number

- [x] Task 16: 修复标本登记视图
  - [x] SubTask 16.1: 修复 getDepartments 返回数据字段映射，使用 deptName 而非 name
  - [x] SubTask 16.2: 修复 getTestItemCategories 返回类型处理，适配 string[] 返回值

- [x] Task 17: 修复报告模块
  - [x] SubTask 17.1: 修复 printReport 返回类型为 Promise<Blob>
  - [x] SubTask 17.2: 修复 getCriticalValueRecords 与 getCriticalValueDetail 同端点问题

- [x] Task 18: 修复菜单管理视图
  - [x] SubTask 18.1: 修复表格列 prop="name" → prop="menuName"
  - [x] SubTask 18.2: 修复删除确认对话框中 row.name → row.menuName

## Phase 8: 验证与启动测试

- [x] Task 19: 后端编译验证
  - [x] SubTask 19.1: 执行 Maven 编译确保所有后端模块无编译错误
  - [x] SubTask 19.2: 检查各服务配置文件一致性

- [x] Task 20: 前端编译验证
  - [x] SubTask 20.1: 执行 npm run build 确保前端无 TypeScript 编译错误
  - [x] SubTask 20.2: 执行 npm run dev 启动前端开发服务器

# Task Dependencies

- [Task 2] depends on [Task 1] (先修复 BaseEntity 再处理 Auth 数据库问题)
- [Task 5] depends on [Task 2] (Auth 服务修复后再修密码逻辑)
- [Task 9] is independent (前端类型修复可并行)
- [Task 11] is independent (路由补全可并行)
- [Task 12-18] can be parallelized (各模块修复互不依赖)
- [Task 19] depends on [Task 1-8] (后端全部修复后再编译验证)
- [Task 20] depends on [Task 9-18] (前端全部修复后再编译验证)
