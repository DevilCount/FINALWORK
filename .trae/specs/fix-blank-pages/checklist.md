# Checklist

## 路由持久化修复验证
- [x] permission store 无 `persist: true` 配置
- [x] user store 的 persist 配置已移除（token/refreshToken 通过 setToken/setRefreshToken 单独存储）
- [x] 页面刷新后路由守卫能正确重新生成动态路由
- [x] 页面刷新后所有页面可正常访问，不显示空白

## 路由守卫逻辑验证
- [x] 路由守卫使用 `!permissionStore.isRoutesLoaded` 判断是否需要生成路由
- [x] 首次登录后路由正确生成并添加到 Vue Router
- [x] 页面刷新后路由正确重新生成并添加到 Vue Router
- [x] 路由生成失败时正确重定向到登录页

## 侧边栏导航验证
- [x] 点击菜单项仅触发一次路由导航（移除 handleClick 双重导航）
- [x] 导航后页面内容正确显示
- [x] 导航后侧边栏激活状态正确

## 用户管理页面验证
- [x] 部门树节点标签正确显示部门名称（使用 getDeptTreeNodes）
- [x] 用户性别列正确显示"男"/"女"（使用数字比较）
- [x] 新增/编辑用户表单的性别和状态使用数字值
- [x] 用户列表数据正确加载和显示

## TypeScript 类型检查验证
- [x] vue-tsc --noEmit 通过（0 错误）
- [x] 所有视图组件数据类型与 API 接口定义一致
- [x] 所有 status 字段使用 number 类型（0=正常，2=禁用）
- [x] 所有表单字段名与后端 DTO 字段名一致

## 前端服务启动验证
- [x] 前端开发服务器成功启动在 http://localhost:3000/
- [x] 浏览器预览无报错
