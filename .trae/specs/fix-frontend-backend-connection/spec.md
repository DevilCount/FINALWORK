# 修复前后端连通性并取消登录验证码 - 规范

## Why

前端开发服务器已启动但后端未运行，导致所有 API 请求返回 ECONNREFUSED。同时用户要求取消登录页面的验证码功能，简化登录流程。

## What Changes

### 取消登录验证码
- 移除登录页面的验证码表单项、验证规则和获取验证码逻辑
- 登录请求不再发送 `code` 和 `uuid` 字段（后端已支持：当这两个字段为 null 时跳过验证码校验）

### 修复前后端连通性
- 验证 Vite 代理配置与 Gateway 路由匹配
- 验证请求路径链路：前端 baseURL → Vite proxy → Gateway → 微服务
- 确保登录流程端到端可用

## Impact
- 受影响的代码：前端登录页面、前端 API 层
- 受影响的功能：用户登录

## ADDED Requirements

### Requirement: 登录页面无验证码

系统 SHALL 提供无验证码的登录页面，用户只需输入用户名和密码即可登录。

#### Scenario: 无验证码登录成功
- **WHEN** 用户输入正确的用户名和密码并点击登录
- **THEN** 系统直接验证用户名密码，不要求验证码，登录成功后跳转到首页

#### Scenario: 无验证码登录失败
- **WHEN** 用户输入错误的用户名或密码
- **THEN** 系统提示"用户名或密码错误"，不显示验证码相关内容

### Requirement: 前后端 API 请求连通

系统 SHALL 确保前端发出的 API 请求能正确到达后端微服务并返回响应。

#### Scenario: 登录请求连通
- **WHEN** 前端发送 POST /api/auth/login 请求
- **THEN** 请求经过 Vite 代理转发到 Gateway (localhost:8080)，Gateway 路由到 Auth 服务，返回登录结果

## MODIFIED Requirements

无

## REMOVED Requirements

### Requirement: 登录验证码
**Reason**: 用户要求取消验证码，简化登录流程
**Migration**: 后端已支持无验证码登录（code/uuid 为 null 时跳过校验），无需修改后端
