# LIS 项目迭代计划 - 任务清单

## [ ] 版本1：前端问题修复与优化

### [ ] Task 1.1: 修复前端目录冗余问题
- **Priority**: P0
- **Depends On**: None
- **Description**: 
  - 删除未使用的 store/ 目录
  - 确保 stores/ 目录的 Pinia 配置完整
- **Acceptance Criteria Addressed**: [AC-1]
- **Test Requirements**:
  - `programmatic` TR-1.1.1: store/ 目录已删除
  - `programmatic` TR-1.1.2: stores/ 目录配置完整

### [ ] Task 1.2: 完善前端API导出
- **Priority**: P0
- **Depends On**: None
- **Description**: 
  - 更新 api/index.ts 文件，导出所有 API 模块
  - 确保所有前端组件能正确导入 API
- **Acceptance Criteria Addressed**: [AC-1]
- **Test Requirements**:
  - `programmatic` TR-1.2.1: api/index.ts 导出所有模块
  - `programmatic` TR-1.2.2: API 导入无错误

### [ ] Task 1.3: 完善路由配置
- **Priority**: P1
- **Depends On**: None
- **Description**: 
  - 完善 asyncRoutes 配置
  - 确保路由守卫正常工作
- **Acceptance Criteria Addressed**: [AC-1]
- **Test Requirements**:
  - `programmatic` TR-1.3.1: asyncRoutes 配置完整
  - `programmatic` TR-1.3.2: 路由守卫工作正常

### [ ] Task 1.4: 前端代码质量优化
- **Priority**: P1
- **Depends On**: Task 1.1, Task 1.2, Task 1.3
- **Description**: 
  - 检查并优化前端代码质量
  - 确保 TypeScript 类型定义完整
  - 优化组件结构和代码风格
- **Acceptance Criteria Addressed**: [AC-1]
- **Test Requirements**:
  - `programmatic` TR-1.4.1: TypeScript 类型检查通过
  - `programmatic` TR-1.4.2: 代码风格符合规范

### [ ] Task 1.5: 前端构建测试
- **Priority**: P0
- **Depends On**: Task 1.4
- **Description**: 
  - 执行 npm install
  - 执行 npm run build
  - 验证构建成功
- **Acceptance Criteria Addressed**: [AC-1]
- **Test Requirements**:
  - `programmatic` TR-1.5.1: npm install 成功
  - `programmatic` TR-1.5.2: npm run build 成功

## [ ] 版本2：后端代码质量优化

### [ ] Task 2.1: 后端代码质量检查
- **Priority**: P0
- **Depends On**: None
- **Description**: 
  - 检查所有10个后端微服务的代码质量
  - 确保代码符合编码规范
  - 检查包结构和命名规范
- **Acceptance Criteria Addressed**: [AC-2]
- **Test Requirements**:
  - `programmatic` TR-2.1.1: 代码符合编码规范
  - `programmatic` TR-2.1.2: 包结构清晰合理

### [ ] Task 2.2: 后端编译测试
- **Priority**: P0
- **Depends On**: Task 2.1
- **Description**: 
  - 执行 mvn clean install -DskipTests
  - 验证所有微服务编译成功
- **Acceptance Criteria Addressed**: [AC-2]
- **Test Requirements**:
  - `programmatic` TR-2.2.1: Maven 编译成功
  - `programmatic` TR-2.2.2: 无编译错误

### [ ] Task 2.3: 配置文件检查
- **Priority**: P1
- **Depends On**: Task 2.2
- **Description**: 
  - 检查所有微服务的配置文件
  - 确保配置项正确完整
  - 检查环境配置的一致性
- **Acceptance Criteria Addressed**: [AC-2]
- **Test Requirements**:
  - `programmatic` TR-2.3.1: 配置文件完整
  - `programmatic` TR-2.3.2: 配置项正确

## [ ] 版本3：功能完整性验证

### [ ] Task 3.1: 核心功能验证
- **Priority**: P0
- **Depends On**: Task 1.5, Task 2.3
- **Description**: 
  - 验证用户管理、标本管理、检验管理等核心功能
  - 检查API接口的完整性
  - 验证前端页面的功能
- **Acceptance Criteria Addressed**: [AC-3]
- **Test Requirements**:
  - `programmatic` TR-3.1.1: 核心功能完整
  - `programmatic` TR-3.1.2: API接口可访问

### [ ] Task 3.2: 基本集成测试
- **Priority**: P0
- **Depends On**: Task 3.1
- **Description**: 
  - 执行基本的集成测试
  - 验证前后端对接
  - 检查核心业务流程
- **Acceptance Criteria Addressed**: [AC-3]
- **Test Requirements**:
  - `programmatic` TR-3.2.1: 前后端对接正常
  - `programmatic` TR-3.2.2: 核心业务流程可执行

### [ ] Task 3.3: 生成迭代报告
- **Priority**: P1
- **Depends On**: Task 3.2
- **Description**: 
  - 生成3个版本的迭代报告
  - 记录修复的问题和优化的内容
  - 总结迭代成果
- **Acceptance Criteria Addressed**: [AC-3]
- **Test Requirements**:
  - `human-judgement` TR-3.3.1: 迭代报告完整
  - `human-judgement` TR-3.3.2: 迭代记录详实
