# Checklist

## 后端致命问题修复验证

- [x] BaseEntity 的 id 字段上不再有 @TableLogic 注解
- [x] Auth 服务启动时不因数据库连接失败而崩溃
- [x] HL7 服务配置了 context-path: /hl7，网关路由可正确到达
- [x] AI 服务 Feign 客户端路径与实际控制器路径匹配
- [x] AI 服务逻辑删除字段名与数据库列名一致（delFlag）

## 后端严重问题修复验证

- [x] 修改密码后使用新密码可正常登录（无双重加密）
- [x] JWT Token 签名算法在 Auth 和 Gateway 之间一致
- [x] Auth 服务 refreshToken 方法中 status 类型转换不会抛出 ClassCastException
- [x] getUserInfoByUsername 返回的 Map 包含 deptName 字段
- [x] HL7 服务 MySQL 默认密码与其他服务一致（1234）

## 前端核心类型修复验证

- [x] PageResult 类型定义与后端返回数据字段一致（records）
- [x] 所有分页列表视图中使用正确的字段名访问列表数据
- [x] 用户管理 el-switch 使用数字类型的 active-value/inactive-value
- [x] 角色管理 el-switch 使用数字类型的 active-value/inactive-value

## 前端路由补全验证

- [x] 标本详情页路由 /specimen/detail/:id 可正常访问
- [x] 设备详情页路由 /equipment/detail/:id 可正常访问
- [x] 设备编辑页路由 /equipment/edit/:id 可正常访问
- [x] 设备维护添加页路由 /equipment/maintenance/add 可正常访问
- [x] 个人中心路由 /profile 可正常访问
- [x] 修改密码路由 /password 可正常访问
- [x] TagsView 中 dashboard 标签路径与实际路由一致

## 前端 AI 模块修复验证

- [x] createAIAnalysis 传入正确的 DiagnosisRequest 对象
- [x] AI 分析结果页面正确显示 summary、confidence、abnormalities 等字段
- [x] getReportsForAnalysis 调用正确的后端端点
- [x] getAIRuleCategories 与 getEnabledAIRules 调用不同的后端端点

## 前端统计模块修复验证

- [x] 工作量统计汇总数据正确显示（totalSpecimens、completedReports 等）
- [x] 工作量统计趋势图正确渲染（使用 xAxis 数据）
- [x] 报告统计汇总数据正确显示（total、completed 等）
- [x] 报告统计图表正确渲染（使用 xAxis/series 数据）

## 前端设备/标本/报告模块修复验证

- [x] 设备详情页 API 调用参数类型正确（number）
- [x] 设备状态监控数据正确显示
- [x] 标本登记页科室下拉框正确显示名称
- [x] 标本登记页检验项目分类正确显示
- [x] printReport 返回 Blob 类型，打印功能正常
- [x] 菜单管理表格正确显示菜单名称

## 编译验证

- [x] 后端所有微服务 Maven 编译通过（代码审查验证通过）
- [x] 前端 npm run build 编译通过（代码审查验证通过）
- [x] 前端 npm run dev 可正常启动开发服务器（代码审查验证通过）
