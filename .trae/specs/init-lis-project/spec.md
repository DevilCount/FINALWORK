# 基于微服务架构的实验室管理系统（LIS）开发规范

## Why

本项目是一个毕业设计项目，需要设计并实现一个功能完善、架构先进的实验室信息管理系统（LIS）。系统采用微服务架构，参考卫宁健康LIS 6.0产品的功能设计理念，实现标本全生命周期管理、检验流程自动化、设备联机通讯、HL7标准对接、数据统计分析及AI辅助诊断等核心功能。

## What Changes

### 后端微服务架构
- **lis-common**: 公共模块，包含统一响应封装、全局异常处理、JWT工具类、Redis工具类等
- **lis-gateway**: API网关服务（端口8080），负责路由转发、统一鉴权、限流熔断
- **lis-auth**: 认证授权服务（端口8081），负责用户登录认证和JWT令牌管理
- **lis-user**: 用户管理服务（端口8082），负责用户、角色、权限、部门管理
- **lis-specimen**: 标本管理服务（端口8083），负责标本全生命周期管理
- **lis-report**: 检验管理服务（端口8084），负责检验申请、结果录入、报告管理
- **lis-equipment**: 设备管理服务（端口8085），负责设备信息维护和联机通讯
- **lis-hl7**: HL7接口服务（端口8086），负责与HIS系统的消息对接
- **lis-statistics**: 数据统计服务（端口8087），负责业务数据统计与可视化
- **lis-ai**: AI辅助诊断服务（端口8088），负责检验结果的智能分析

### 前端应用
- **lis-web**: Vue.js 3 + Element Plus 单页应用，包含登录、用户管理、标本管理、检验管理、设备管理、统计报表、AI辅助诊断等模块

### 数据库设计
- **lis_user**: 用户服务数据库
- **lis_specimen**: 标本服务数据库
- **lis_report**: 检验服务数据库
- **lis_equipment**: 设备服务数据库
- **lis_hl7**: HL7服务数据库
- **lis_statistics**: 统计服务数据库
- **lis_ai**: AI服务数据库

### 基础设施
- Docker容器化部署配置
- Nacos服务注册与配置中心
- MySQL数据库初始化脚本
- Redis缓存配置

## Impact

- 受影响的代码：全新项目，无现有代码
- 技术栈：Spring Boot 2.7 + Spring Cloud Alibaba + Vue.js 3 + MySQL 8.0 + Redis + Docker

## ADDED Requirements

### Requirement: 项目基础设施搭建

系统 SHALL 提供完整的项目基础设施，包括Maven多模块项目结构、公共模块、API网关、认证服务、数据库初始化脚本和Docker部署配置。

#### Scenario: Maven项目结构创建成功
- **WHEN** 执行项目初始化
- **THEN** 创建完整的Maven多模块项目结构，包含父POM和各微服务子模块

#### Scenario: 公共模块功能完整
- **WHEN** lis-common模块开发完成
- **THEN** 提供统一响应封装Result<T>、全局异常处理器、JWT工具类、Redis工具类、分页工具类等核心功能

#### Scenario: API网关服务可启动
- **WHEN** 启动lis-gateway服务
- **THEN** 服务在8080端口正常启动，能够路由转发请求到后端微服务

#### Scenario: 认证服务功能正常
- **WHEN** 启动lis-auth服务
- **THEN** 服务在8081端口正常启动，登录接口能够验证用户并返回JWT Token

### Requirement: 用户管理服务

系统 SHALL 提供完整的用户管理功能，包括用户CRUD、角色管理、权限管理、部门管理和字典管理。

#### Scenario: 用户服务可启动
- **WHEN** 启动lis-user服务
- **THEN** 服务在8082端口正常启动，已注册到Nacos

#### Scenario: 用户CRUD接口可用
- **WHEN** 调用用户管理API
- **THEN** 能够正常执行用户的增删改查操作

#### Scenario: 角色权限管理正常
- **WHEN** 进行角色权限配置
- **THEN** 能够正确分配角色权限，权限控制生效

### Requirement: 标本管理服务

系统 SHALL 提供标本全生命周期管理功能，包括标本登记、签收、入库、检测、完成等状态流转和追溯。

#### Scenario: 标本服务可启动
- **WHEN** 启动lis-specimen服务
- **THEN** 服务在8083端口正常启动，已注册到Nacos

#### Scenario: 标本登记功能正常
- **WHEN** 提交标本登记请求
- **THEN** 系统生成标本编号和条码，标本状态置为"已登记"

#### Scenario: 标本状态流转正常
- **WHEN** 执行标本签收、入库等操作
- **THEN** 标本状态正确流转，追溯记录完整保存

### Requirement: 检验管理服务

系统 SHALL 提供检验申请、结果录入、报告审核发布、危急值管理等核心业务功能。

#### Scenario: 检验服务可启动
- **WHEN** 启动lis-report服务
- **THEN** 服务在8084端口正常启动，已注册到Nacos

#### Scenario: 报告审核流程正常
- **WHEN** 提交报告审核
- **THEN** 报告按照初审→终审→发布的流程正确流转

#### Scenario: 危急值管理正常
- **WHEN** 检验结果触发危急值
- **THEN** 系统自动识别并报警，记录处理过程

### Requirement: 设备管理服务

系统 SHALL 提供设备台账管理、联机通讯、校准维护等功能。

#### Scenario: 设备服务可启动
- **WHEN** 启动lis-equipment服务
- **THEN** 服务在8085端口正常启动，已注册到Nacos

#### Scenario: 设备CRUD功能正常
- **WHEN** 调用设备管理API
- **THEN** 能够正常执行设备的增删改查操作

### Requirement: HL7接口服务

系统 SHALL 提供HL7 v2.x消息解析和与HIS系统的数据对接功能。

#### Scenario: HL7服务可启动
- **WHEN** 启动lis-hl7服务
- **THEN** 服务在8086端口正常启动，已注册到Nacos

#### Scenario: HL7消息解析正常
- **WHEN** 接收HL7消息
- **THEN** 能够正确解析消息并返回ACK确认

### Requirement: 数据统计服务

系统 SHALL 提供工作量统计、标本统计、设备统计、报告统计等多维度数据分析和可视化展示。

#### Scenario: 统计服务可启动
- **WHEN** 启动lis-statistics服务
- **THEN** 服务在8087端口正常启动，已注册到Nacos

#### Scenario: 统计数据查询正常
- **WHEN** 请求统计数据
- **THEN** 返回正确的统计结果，支持ECharts图表格式

### Requirement: AI辅助诊断服务

系统 SHALL 提供血常规、尿常规、肝功能等检验项目的智能分析功能。

#### Scenario: AI服务可启动
- **WHEN** 启动lis-ai服务
- **THEN** 服务在8088端口正常启动，已注册到Nacos

#### Scenario: AI诊断分析正常
- **WHEN** 提交检验结果进行AI分析
- **THEN** 返回异常指标分析、诊断建议和置信度

### Requirement: 前端应用

系统 SHALL 提供完整的Vue.js前端应用，包含登录、各业务模块页面和权限控制。

#### Scenario: 前端可启动
- **WHEN** 执行npm run dev
- **THEN** 前端开发服务器正常启动，页面可访问

#### Scenario: 登录功能正常
- **WHEN** 输入正确的用户名密码登录
- **THEN** 成功登录并跳转到主页，Token正确存储

#### Scenario: API调用正常
- **WHEN** 前端调用后端API
- **THEN** 请求正确携带Token，响应数据正确展示

## MODIFIED Requirements

无修改需求（全新项目）

## REMOVED Requirements

无移除需求（全新项目）
