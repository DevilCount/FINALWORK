# LIS 实验室管理系统 - 项目信息展示任务清单

## [x] Task 1: 拉取主分支 git 内容
- **Priority**: P0
- **Depends On**: None
- **Description**: 
  - 检查 git 仓库状态
  - 确认当前在 main 分支
  - 验证代码是最新版本
- **Acceptance Criteria Addressed**: [AC-1]
- **Test Requirements**:
  - `programmatic` TR-1.1: 执行 git status 确认在 main 分支
  - `programmatic` TR-1.2: 确认工作区干净且与 origin/main 同步
- **Notes**: 已完成

## [x] Task 2: 分析项目基本信息
- **Priority**: P0
- **Depends On**: Task 1
- **Description**: 
  - 读取 README.md 了解项目概述
  - 确认项目名称、开发者、版本等基本信息
  - 整理项目主要功能特点
- **Acceptance Criteria Addressed**: [AC-1]
- **Test Requirements**:
  - `programmatic` TR-2.1: 读取 README.md 文件
  - `programmatic` TR-2.2: 提取项目基本信息
- **Notes**: 已完成

## [x] Task 3: 分析技术架构
- **Priority**: P0
- **Depends On**: Task 1
- **Description**: 
  - 查看后端 pom.xml 确认技术栈
  - 查看前端 package.json 确认前端技术栈
  - 整理微服务架构和端口分配
- **Acceptance Criteria Addressed**: [AC-2]
- **Test Requirements**:
  - `programmatic` TR-3.1: 读取 lis-backend/pom.xml
  - `programmatic` TR-3.2: 读取 lis-web/package.json
  - `programmatic` TR-3.3: 列出所有微服务模块
- **Notes**: 已完成

## [x] Task 4: 分析功能模块
- **Priority**: P1
- **Depends On**: Task 2
- **Description**: 
  - 分析每个微服务的职责
  - 整理前端页面路由
  - 确认数据库脚本存在
- **Acceptance Criteria Addressed**: [AC-3]
- **Test Requirements**:
  - `programmatic` TR-4.1: 检查 lis-backend 下所有微服务目录
  - `programmatic` TR-4.2: 检查 lis-web/src/views 目录
  - `programmatic` TR-4.3: 确认 sql 目录下 8 个脚本文件存在
- **Notes**: 已完成

## [x] Task 5: 整理目录结构
- **Priority**: P1
- **Depends On**: Task 1
- **Description**: 
  - 查看项目根目录结构
  - 确认 docs、docker 等关键目录存在
  - 整理完整的项目目录树
- **Acceptance Criteria Addressed**: [AC-4]
- **Test Requirements**:
  - `programmatic` TR-5.1: 列出项目根目录所有文件和文件夹
  - `programmatic` TR-5.2: 确认 docs、docker 目录存在
- **Notes**: 已完成
