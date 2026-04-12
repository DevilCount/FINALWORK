# Tasks

- [x] Task 1: 修复 ResultEntryController 路径冗余
  - [x] 将 `@RequestMapping("/report/result")` 改为 `@RequestMapping("/result")`
  - [x] 重新构建 lis-report 模块
  - [x] 验证 API 路径 `/report/result` 可通过网关正常访问

- [x] Task 2: 修复 Vite 开发服务器代理和 SPA 路由回退
  - [x] 检查 vite.config.ts 中的 proxy 配置，确保 `/api` 前缀正确代理到后端网关
  - [x] 确认 Vite 开发服务器默认支持 SPA historyApiFallback（Vite 内置支持）
  - [x] 验证直接访问深层路由不再返回 404

- [x] Task 3: 修复前端路由守卫和动态路由加载
  - [x] 修复 router/index.ts 中路由守卫逻辑，确保动态路由正确加载
  - [x] 修复 permission store 中 getAsyncRoutesAction 返回的路由结构
  - [x] 确保用户登录后动态路由被正确添加到 router 实例
  - [x] 修复 CatchAll 路由与动态路由的优先级冲突

- [x] Task 4: 修复导航组件
  - [x] 修复 Layout 组件中"返回首页"按钮，确保点击后导航到 `/dashboard/index`
  - [x] 修复侧边栏菜单导航，确保点击菜单项正确切换路由
  - [x] 修复面包屑导航显示

- [x] Task 5: 修复前端 API 路径与后端不匹配
  - [x] 修复 `api/report.ts` 中 `saveResultEntry` 路径
  - [x] 修复 `api/specimen.ts` 中 `getSpecimenByBarcodePath` 路径
  - [x] 修复 `api/equipment.ts` 中所有 API 路径与后端 EquipmentController 匹配
  - [x] 修复 `api/statistics.ts` 中导出接口的 responseType 处理
  - [x] 修复 `api/ai.ts` 中分页参数映射

- [x] Task 6: 统一分页参数命名
  - [x] 检查所有前端 API 文件中的分页参数，统一使用 `pageNum` 和 `pageSize`
  - [x] 确保与后端 DTO 字段名一致

- [x] Task 7: 重新构建并重启所有服务
  - [x] 停止所有 Java 进程
  - [x] 重新构建后端项目 `mvn clean package -DskipTests`
  - [x] 重启 Nacos、Redis
  - [x] 启动所有 9 个后端服务
  - [x] 验证 9/9 服务正常运行

- [x] Task 8: 端到端测试验证
  - [x] 测试登录流程
  - [x] 测试直接访问深层路由（如 /specimen/register）
  - [x] 测试刷新页面不丢失路由
  - [x] 测试导航菜单点击跳转
  - [x] 测试"返回首页"按钮
  - [x] 测试浏览器前进后退
  - [x] 测试全流程 API（15/15 通过）

# Task Dependencies
- [Task 5] depends on [Task 1] (ResultEntryController 路径修复后才能验证前端 API)
- [Task 7] depends on [Task 1] (需要重新构建后端)
- [Task 8] depends on [Task 2, Task 3, Task 4, Task 5, Task 6, Task 7]
- [Task 2, Task 3, Task 4] 可并行执行
- [Task 5, Task 6] 可并行执行
