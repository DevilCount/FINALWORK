# LIS 实验室管理系统 - 项目信息说明文档

## Overview
- **Summary**: 这是一个基于微服务架构的实验室信息管理系统（Laboratory Information System，简称LIS），为医疗机构提供高效、安全、智能的实验室信息化解决方案。系统覆盖实验室标本管理、检验管理、设备管理、HL7接口对接、数据统计和AI辅助诊断等核心业务场景。
- **Purpose**: 实现医院实验室全流程信息化管理，提升检验效率，降低人为误差，支持数据统计分析和AI辅助诊断。
- **Target Users**: 医院实验室工作人员、检验医生、系统管理员、医疗机构管理人员。

## Goals
- 完整展示项目技术架构和功能模块
- 清晰说明各微服务的职责和端口分配
- 提供快速开始指南帮助开发者快速搭建环境
- 展示项目的目录结构和关键技术栈

## Non-Goals (Out of Scope)
- 不进行新功能开发
- 不修复现有bug
- 不进行性能优化
- 不修改代码实现

## Background & Context
- 项目采用Spring Cloud Alibaba微服务技术栈，前后端分离架构
- 后端使用Java 17 + Spring Boot 2.7.18
- 前端使用Vue.js 3 + TypeScript + Element Plus
- 支持Docker容器化部署
- 参考卫宁健康LIS 6.0产品设计理念

## Functional Requirements
- **FR-1**: 展示项目的完整技术架构
- **FR-2**: 列出所有微服务及其职责
- **FR-3**: 说明项目的目录结构
- **FR-4**: 提供技术栈清单
- **FR-5**: 展示快速开始和部署指南

## Non-Functional Requirements
- **NFR-1**: 文档清晰易读
- **NFR-2**: 信息准确完整
- **NFR-3**: 结构条理清晰

## Constraints
- **Technical**: 基于现有代码库，只读不写
- **Business**: 项目信息展示，非功能开发
- **Dependencies**: 无外部依赖

## Assumptions
- git仓库已正确初始化
- 主分支代码是最新版本
- README.md文件包含完整项目信息

## Acceptance Criteria

### AC-1: 项目基本信息完整
- **Given**: 已拉取主分支代码
- **When**: 查看项目基本信息
- **Then**: 项目名称、开发者、版本等信息完整展示
- **Verification**: `programmatic`

### AC-2: 技术架构清晰
- **Given**: 已了解项目结构
- **When**: 查看技术架构
- **Then**: 微服务架构图、各服务端口、技术栈清单完整展示
- **Verification**: `programmatic`

### AC-3: 功能模块明确
- **Given**: 已分析项目代码
- **When**: 查看功能模块
- **Then**: 各微服务的功能职责清晰说明
- **Verification**: `programmatic`

### AC-4: 目录结构完整
- **Given**: 已查看项目文件
- **When**: 查看目录结构
- **Then**: 后端、前端、文档等目录结构完整展示
- **Verification**: `programmatic`

## Open Questions
- 无
