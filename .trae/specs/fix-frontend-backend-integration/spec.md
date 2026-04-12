# LIS 前后端集成与路由修复 Spec

## Why
前端直接访问路由显示 404，多个 API 路径与后端不匹配，导航功能异常，导致系统无法正常使用。需要全面修复前后端集成问题，确保所有页面可访问、所有 API 正确调用、导航流程顺畅。

## What Changes
- 修复 Vite 开发服务器代理配置，确保 SPA 路由回退正确
- 修复 ResultEntryController 路径冗余（`/report/report/result` → `/report/result`）
- 修复前端 API 调用路径与后端 Controller 不匹配的问题
- 修复前端路由守卫和动态路由加载逻辑
- 修复导航组件中"返回首页"按钮功能
- 统一分页参数命名（pageNo vs pageNum）
- 添加缺失的 API 接口和页面组件

## Impact
- Affected specs: 前端路由系统、API 调用层、导航组件
- Affected code:
  - `lis-web/vite.config.ts` - 代理配置
  - `lis-web/src/router/index.ts` - 路由守卫
  - `lis-web/src/router/routes.ts` - 路由定义
  - `lis-web/src/stores/modules/permission.ts` - 动态路由
  - `lis-web/src/stores/modules/user.ts` - 用户状态
  - `lis-web/src/api/*.ts` - 所有 API 文件
  - `lis-web/src/components/Layout/index.vue` - 布局导航
  - `lis-backend/lis-report/src/main/java/com/lis/report/controller/ResultEntryController.java` - 路径修复

## ADDED Requirements

### Requirement: SPA 路由回退
系统 SHALL 在 Vite 开发服务器和生产环境中正确处理 SPA 客户端路由，直接访问任何前端路由路径时 SHALL 返回 index.html 而非 404。

#### Scenario: 直接访问深层路由
- **WHEN** 用户直接在浏览器输入 `http://localhost:3000/specimen/register`
- **THEN** 页面 SHALL 正确加载并显示标本登记页面，而非 404 错误

#### Scenario: 刷新页面
- **WHEN** 用户在任意页面按 F5 刷新
- **THEN** 页面 SHALL 正确重新加载当前路由，而非显示 404

### Requirement: API 路径一致性
前端所有 API 调用路径 SHALL 与后端 Controller 实际路径完全匹配。

#### Scenario: 结果录入 API
- **WHEN** 前端调用 `POST /report/result` 录入检验结果
- **THEN** 请求 SHALL 正确到达后端 ResultEntryController，返回 200 而非 404

#### Scenario: 标本详情 API
- **WHEN** 前端调用 `GET /specimen/getById/{id}` 获取标本详情
- **THEN** 请求 SHALL 正确到达后端 SpecimenController，返回标本数据

### Requirement: 导航功能正常
系统 SHALL 提供完整可用的导航功能，包括首页按钮、菜单导航、浏览器前进后退。

#### Scenario: 返回首页
- **WHEN** 用户点击导航栏中的"首页"或 Logo
- **THEN** 系统 SHALL 导航到 `/dashboard/index` 页面

#### Scenario: 浏览器后退
- **WHEN** 用户点击浏览器后退按钮
- **THEN** 系统 SHALL 正确返回上一个访问的页面

### Requirement: 统一分页参数
前端所有分页查询 SHALL 使用统一的参数命名，与后端 DTO 字段名一致。

#### Scenario: 分页查询
- **WHEN** 前端发送分页查询请求
- **THEN** 请求体 SHALL 包含 `pageNo` 和 `pageSize` 字段（与后端一致）

## MODIFIED Requirements

### Requirement: ResultEntryController 路径
ResultEntryController 的 `@RequestMapping` 从 `/report/result` 修改为 `/result`，因为 lis-report 的 context-path 已经是 `/report`，避免路径重复为 `/report/report/result`。

### Requirement: 前端路由加载
路由守卫 SHALL 在获取用户信息失败时提供友好的错误提示，而非直接跳转到登录页。动态路由 SHALL 在用户首次登录后正确加载所有菜单路由。

## REMOVED Requirements
无
