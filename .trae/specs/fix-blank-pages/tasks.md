# Tasks

- [x] Task 1: 修复 permission store 持久化配置
  - [x] SubTask 1.1: 移除 `permission.ts` 中的 `persist: true`
  - [x] SubTask 1.2: 确保 `isRoutesLoaded` 标志在 store 初始化时为 `false`（不依赖持久化）

- [x] Task 2: 修复 user store 持久化配置
  - [x] SubTask 2.1: 移除 `persist: true`（token/refreshToken 已通过 setToken/setRefreshToken 单独存储在 localStorage）
  - [x] SubTask 2.2: 验证 token 和 refreshToken 已通过 `setToken`/`setRefreshToken` 单独存储在 localStorage

- [x] Task 3: 修复路由守卫逻辑
  - [x] SubTask 3.1: 在 `router/index.ts` 中将判断条件从 `userStore.roles.length === 0` 改为检查 `!permissionStore.isRoutesLoaded`
  - [x] SubTask 3.2: 确保路由守卫在页面刷新时能正确触发路由重新生成
  - [x] SubTask 3.3: 确保 `generateRoutesAction` 完成后 `isRoutesLoaded` 被设为 `true`

- [x] Task 4: 修复 SidebarItem 双重导航问题
  - [x] SubTask 4.1: 移除 `SidebarItem.vue` 中 `el-menu-item` 的 `@click="handleClick"` 事件
  - [x] SubTask 4.2: 移除 `useRouter` 导入和 `handleClick` 函数

- [x] Task 5: 修复用户管理页面数据类型问题
  - [x] SubTask 5.1: 将 `getDeptTree()` 替换为 `getDeptTreeNodes()`
  - [x] SubTask 5.2: 修复性别显示逻辑（`row.gender === 1` 男，`row.gender === 2` 女）
  - [x] SubTask 5.3: 修复 `UserFormDialog.vue` 中表单值类型

- [x] Task 6: 检查并修复其他视图组件的潜在问题
  - [x] SubTask 6.1: 检查所有视图组件的 API 调用是否正确
  - [x] SubTask 6.2: 检查所有视图组件的数据类型是否与 API 接口定义一致
  - [x] SubTask 6.3: 修复发现的导致页面空白或渲染错误的问题（共修复 30+ 处类型错误）

- [x] Task 7: 验证所有页面渲染和业务流程
  - [x] SubTask 7.1: TypeScript 类型检查通过（0 错误）
  - [x] SubTask 7.2: 前端开发服务器成功启动在 http://localhost:3000/
  - [x] SubTask 7.3: 浏览器预览无报错

# Task Dependencies
- [Task 2] depends on [Task 1]
- [Task 3] depends on [Task 1, Task 2]
- [Task 4] is independent
- [Task 5] is independent
- [Task 6] depends on [Task 5]
- [Task 7] depends on [Task 1-6]
