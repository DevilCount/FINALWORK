# Tasks

- [x] Task 1: 移除登录页面验证码功能
  - [x] SubTask 1.1: 移除登录页面模板中的验证码表单项（el-form-item prop="captcha" 及其内容）
  - [x] SubTask 1.2: 移除 loginRules 中的 captcha 验证规则
  - [x] SubTask 1.3: 移除 captchaImage ref 和 refreshCaptcha 函数
  - [x] SubTask 1.4: 移除 onMounted 中的 refreshCaptcha() 调用
  - [x] SubTask 1.5: 移除 handleLogin 中 catch 块里的 refreshCaptcha() 和 loginForm.captcha = ''
  - [x] SubTask 1.6: 移除 loginForm 中的 captcha 和 uuid 字段
  - [x] SubTask 1.7: 移除 import 中的 getCaptcha
  - [x] SubTask 1.8: 移除登录页面样式中的 .captcha-row 相关样式
  - [x] SubTask 1.9: 修改 loginAction 调用，不传 code 和 uuid 字段

- [x] Task 2: 验证前端请求路径链路
  - [x] SubTask 2.1: 确认 request.ts 的 baseURL 为 '/api'
  - [x] SubTask 2.2: 确认 vite.config.ts 代理配置：/api → http://localhost:8080，rewrite 去掉 /api 前缀
  - [x] SubTask 2.3: 确认 Gateway 路由配置：/auth/** → lis-auth 服务
  - [x] SubTask 2.4: 确认 Auth 服务 context-path 为 /auth
  - [x] SubTask 2.5: 确认前端 login API 路径为 /auth/login

- [x] Task 3: 验证登录数据流完整性
  - [x] SubTask 3.1: 确认前端 LoginResult 类型与后端 LoginVO 字段匹配
  - [x] SubTask 3.2: 确认 user store 的 loginAction 正确处理登录响应
  - [x] SubTask 3.3: 确认路由守卫在登录后正确获取用户信息和动态路由
  - [x] SubTask 3.4: 确认 getUserInfo API 路径与后端 /auth/user-info 端点匹配

- [ ] Task 4: 启动后端服务并测试
  - [ ] SubTask 4.1: 启动 Nacos、MySQL、Redis 基础设施
  - [ ] SubTask 4.2: 启动 Gateway 网关服务（端口 8080）
  - [ ] SubTask 4.3: 启动 Auth 认证服务（端口 8081）
  - [ ] SubTask 4.4: 启动 User 用户服务（端口 8082）
  - [ ] SubTask 4.5: 测试登录功能端到端可用

# Task Dependencies

- [Task 2] is independent (可并行)
- [Task 3] is independent (可并行)
- [Task 1] is independent (可并行)
- [Task 4] depends on [Task 1, Task 2, Task 3] (修复完成后再启动测试)
