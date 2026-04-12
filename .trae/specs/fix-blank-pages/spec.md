# 修复页面空白及业务流程验证 Spec

## Why
登录后点击除首页外的其他页面显示空白内容，无法正常使用系统功能。根本原因是 Pinia 持久化配置导致路由组件引用丢失，以及路由守卫逻辑在页面刷新时无法正确重新生成动态路由。

## What Changes
- **修复 permission store 的 persist 配置**：移除 `persist: true`，因为路由中的 `component` 函数无法被 JSON 序列化，持久化后组件引用变为 null
- **修复 user store 的 persist 配置**：排除 `roles` 和 `permissions` 字段的持久化，确保页面刷新时路由守卫能正确触发路由重新生成
- **修复路由守卫逻辑**：将判断条件从 `userStore.roles.length === 0` 改为检查路由是否已加载到 Vue Router 实例中
- **修复 SidebarItem 双重导航问题**：移除 `el-menu` 的 `router` 属性或移除 `handleClick` 中的手动导航，避免双重导航
- **修复视图组件数据类型不匹配问题**：用户管理页面的部门树、性别字段、表单值类型等
- **验证所有页面渲染和业务流程**

## Impact
- Affected specs: fix-frontend-backend-connection, ensure-system-runnable
- Affected code:
  - `lis-web/src/stores/modules/permission.ts` - 移除 persist，修复路由生成逻辑
  - `lis-web/src/stores/modules/user.ts` - 修改 persist 配置
  - `lis-web/src/router/index.ts` - 修复路由守卫条件
  - `lis-web/src/components/Layout/SidebarItem.vue` - 修复双重导航
  - `lis-web/src/views/system/user/index.vue` - 修复部门树、性别、表单值类型
  - `lis-web/src/views/system/user/components/UserFormDialog.vue` - 修复表单值类型

## ADDED Requirements

### Requirement: 动态路由持久化安全
系统 SHALL NOT 持久化包含不可序列化属性（如函数、组件引用）的路由对象到 localStorage。

#### Scenario: 页面刷新后路由正常加载
- **WHEN** 用户刷新页面
- **THEN** 路由守卫检测到动态路由未加载，重新获取用户信息并生成动态路由
- **AND** 所有页面可正常访问，不显示空白

### Requirement: 路由守卫正确判断路由加载状态
系统 SHALL 通过检查 Vue Router 实例中是否已添加动态路由来判断是否需要重新生成路由，而非依赖 Pinia store 中的 roles 字段。

#### Scenario: 角色已持久化但路由未加载
- **WHEN** 页面刷新后 user store 的 roles 从 localStorage 恢复
- **AND** Vue Router 的动态路由未恢复（内存中丢失）
- **THEN** 路由守卫仍能正确检测到路由未加载并重新生成

### Requirement: 侧边栏导航无双重触发
系统 SHALL 确保侧边栏菜单点击导航时不会触发两次路由跳转。

#### Scenario: 点击菜单项导航
- **WHEN** 用户点击侧边栏菜单项
- **THEN** 仅触发一次路由导航，不会出现双重导航

### Requirement: 视图组件数据类型一致
系统 SHALL 确保视图组件中的数据类型与 API 接口定义和后端返回的数据类型一致。

#### Scenario: 用户管理页面部门树正确显示
- **WHEN** 用户打开用户管理页面
- **THEN** 部门树节点标签正确显示部门名称

#### Scenario: 用户性别正确显示
- **WHEN** 用户列表中存在不同性别的用户
- **THEN** 性别列正确显示"男"/"女"而非"未知"

## MODIFIED Requirements

### Requirement: Pinia Store 持久化配置
permission store 不再使用 `persist: true`，改为不持久化（路由每次都重新生成）。
user store 的 `persist` 配置改为仅持久化 `token` 和 `refreshToken`（它们已通过 `setToken`/`setRefreshToken` 单独存储在 localStorage，实际上可以完全移除 persist），排除 `roles`、`permissions`、`userInfo` 的持久化。

## REMOVED Requirements
无
