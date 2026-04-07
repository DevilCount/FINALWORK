# 基于微服务架构的实验室管理系统（LIS）

## 项目介绍

本项目是一个基于微服务架构的实验室信息管理系统（Laboratory Information System，简称LIS），旨在为医疗机构提供高效、安全、智能的实验室信息化解决方案。系统参考卫宁健康LIS 6.0产品的功能设计理念，覆盖实验室标本管理、检验管理、设备管理、HL7接口对接、数据统计和AI辅助诊断等核心业务场景。

### 主要功能

- **用户管理**：用户注册登录、角色权限分配、部门组织管理
- **标本管理**：标本登记、签收、入库、状态流转、条码管理、全流程追溯
- **检验管理**：检验申请、结果录入、报告审核发布、危急值管理
- **设备管理**：设备台账维护、联机通讯、校准维护记录
- **HL7接口对接**：与HIS系统标准化数据交换，支持HL7 v2.x消息协议
- **数据统计**：工作量统计、报告统计、设备使用统计，ECharts可视化展示
- **AI辅助诊断**：血常规、尿常规、肝功能等检验结果智能分析

### 项目特点

- **微服务架构**：采用Spring Cloud Alibaba微服务技术栈，各业务模块独立部署、独立扩展
- **前后端分离**：后端提供RESTful API，前端采用Vue.js 3构建响应式单页应用
- **标准化接口**：遵循HL7 v2.x医疗信息交换标准，支持与HIS系统无缝对接
- **容器化部署**：支持Docker容器化部署，一键式环境搭建

---

## 技术架构

### 系统架构图

```
┌─────────────────────────────────────────────────────────────────┐
│                        前端展示层                                │
│              Vue.js 3 + Element Plus + TypeScript               │
└─────────────────────────────────────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────────┐
│                        API网关层                                 │
│                 lis-gateway (Spring Cloud Gateway)              │
│              路由转发 / 统一鉴权 / 限流熔断                        │
└─────────────────────────────────────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────────┐
│                        微服务层                                  │
│  ┌──────────┐ ┌──────────┐ ┌──────────┐ ┌──────────┐           │
│  │lis-auth  │ │lis-user  │ │lis-speci-│ │lis-report│           │
│  │ 认证服务  │ │ 用户服务  │ │men标本服务│ │ 检验服务  │           │
│  │  :8081   │ │  :8082   │ │  :8083   │ │  :8084   │           │
│  └──────────┘ └──────────┘ └──────────┘ └──────────┘           │
│  ┌──────────┐ ┌──────────┐ ┌──────────┐ ┌──────────┐           │
│  │lis-equip-│ │ lis-hl7  │ │lis-statis-│ │ lis-ai  │           │
│  │ment设备服务│ │ HL7服务  │ │tics统计服务│ │ AI服务   │           │
│  │  :8085   │ │  :8086   │ │  :8087   │ │  :8088   │           │
│  └──────────┘ └──────────┘ └──────────┘ └──────────┘           │
└─────────────────────────────────────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────────┐
│                      服务治理 & 中间件                           │
│  ┌────────┐ ┌────────┐ ┌────────┐ ┌────────┐ ┌────────┐        │
│  │ Nacos  │ │Sentinel│ │ Seata  │ │ Redis  │ │RabbitMQ│        │
│  │注册/配置│ │熔断降级 │ │分布式事务│ │ 缓存   │ │ 消息队列│        │
│  └────────┘ └────────┘ └────────┘ └────────┘ └────────┘        │
└─────────────────────────────────────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────────┐
│                        数据存储层                                │
│     MySQL 8.0 (多数据源) / Redis 6.x / RabbitMQ 3.x            │
└─────────────────────────────────────────────────────────────────┘
```

### 技术栈

#### 后端技术栈

| 技术 | 版本 | 说明 |
|------|------|------|
| Java | JDK 17 | 开发语言 |
| Spring Boot | 2.7.18 | 微服务基础框架 |
| Spring Cloud Alibaba | 2021.0.5.0 | 微服务治理框架 |
| Spring Cloud Gateway | 3.1.x | API网关 |
| Nacos | 2.3.0 | 服务注册与配置中心 |
| Sentinel | 1.8.x | 流量控制与熔断降级 |
| Seata | 1.5.x | 分布式事务 |
| MyBatis-Plus | 3.5.5 | ORM框架 |
| MySQL | 8.0 | 关系型数据库 |
| Redis | 6.x | 分布式缓存 |
| RabbitMQ | 3.10.x | 消息队列 |
| HAPI HL7v2 | 2.5.1 | HL7消息解析 |
| JWT | 0.11.5 | 身份认证令牌 |
| Knife4j | 3.0.3 | API文档 |

#### 前端技术栈

| 技术 | 版本 | 说明 |
|------|------|------|
| Vue.js | 3.5.x | 渐进式JavaScript框架 |
| Vite | 8.x | 前端构建工具 |
| TypeScript | 6.x | 静态类型检查 |
| Element Plus | 2.9.x | UI组件库 |
| Pinia | 3.x | 状态管理 |
| Vue Router | 4.x | 路由管理 |
| Axios | 1.7.x | HTTP客户端 |
| ECharts | 6.x | 数据可视化 |

---

## 目录结构

```
/workspace
├── lis-backend/                    # 后端项目
│   ├── lis-common/                 # 公共模块
│   ├── lis-gateway/                # API网关服务 (:8080)
│   ├── lis-auth/                   # 认证服务 (:8081)
│   ├── lis-user/                   # 用户服务 (:8082)
│   ├── lis-specimen/               # 标本服务 (:8083)
│   ├── lis-report/                 # 检验服务 (:8084)
│   ├── lis-equipment/              # 设备服务 (:8085)
│   ├── lis-hl7/                    # HL7服务 (:8086)
│   ├── lis-statistics/             # 统计服务 (:8087)
│   ├── lis-ai/                     # AI服务 (:8088)
│   ├── sql/                        # 数据库脚本
│   │   ├── 01_lis_user.sql
│   │   ├── 02_lis_specimen.sql
│   │   ├── 03_lis_report.sql
│   │   ├── 04_lis_equipment.sql
│   │   ├── 05_lis_hl7.sql
│   │   ├── 06_lis_statistics.sql
│   │   ├── 07_lis_ai.sql
│   │   └── 08_init_data.sql
│   └── pom.xml                     # Maven父工程配置
│
├── lis-web/                        # 前端项目
│   ├── src/
│   │   ├── api/                    # API接口定义
│   │   ├── assets/                 # 静态资源
│   │   ├── layouts/                # 布局组件
│   │   ├── router/                 # 路由配置
│   │   ├── store/                  # 状态管理
│   │   ├── styles/                 # 全局样式
│   │   ├── types/                  # TypeScript类型定义
│   │   ├── utils/                  # 工具函数
│   │   ├── App.vue                 # 根组件
│   │   └── main.ts                 # 应用入口
│   ├── public/                     # 公共资源
│   ├── package.json                # npm配置
│   ├── vite.config.ts              # Vite配置
│   └── tsconfig.json               # TypeScript配置
│
├── docs/                           # 项目文档
│   ├── 01-规划文档/                 # 可行性分析、需求说明、开发计划
│   ├── 02-设计文档/                 # 概要设计、详细设计、数据库设计、API规范
│   ├── 03-运维文档/                 # 测试计划、测试报告、用户手册
│   ├── 04-收尾文档/                 # 维护手册、项目总结
│   └── 05-开发规范/                 # 编码规范、Git规范、文档模板
│
├── reports/                        # 开发报告
│   └── templates/                  # 任务书模板
│
└── README.md                       # 项目说明文档
```

---

## 快速开始

### 环境要求

- **JDK** 17+
- **Maven** 3.6+
- **Node.js** 16+
- **MySQL** 8.0+
- **Redis** 6.0+
- **Nacos** 2.x（可选，用于服务注册与配置中心）

### 后端启动

1. **克隆项目**

```bash
git clone <repository-url>
cd lis-backend
```

2. **创建数据库**

```bash
# 执行数据库脚本
mysql -u root -p < sql/01_lis_user.sql
mysql -u root -p < sql/02_lis_specimen.sql
mysql -u root -p < sql/03_lis_report.sql
mysql -u root -p < sql/04_lis_equipment.sql
mysql -u root -p < sql/05_lis_hl7.sql
mysql -u root -p < sql/06_lis_statistics.sql
mysql -u root -p < sql/07_lis_ai.sql
mysql -u root -p < sql/08_init_data.sql
```

3. **修改配置**

修改各微服务的配置文件（`application.yml`），配置数据库连接、Redis地址等：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/lis_user_db?useUnicode=true&characterEncoding=utf8
    username: root
    password: your_password
  redis:
    host: localhost
    port: 6379
```

4. **编译项目**

```bash
mvn clean install -DskipTests
```

5. **启动服务**

按以下顺序启动各微服务：

```bash
# 1. 启动网关服务
cd lis-gateway && mvn spring-boot:run

# 2. 启动认证服务
cd lis-auth && mvn spring-boot:run

# 3. 启动用户服务
cd lis-user && mvn spring-boot:run

# 4. 启动其他业务服务...
cd lis-specimen && mvn spring-boot:run
cd lis-report && mvn spring-boot:run
cd lis-equipment && mvn spring-boot:run
cd lis-hl7 && mvn spring-boot:run
cd lis-statistics && mvn spring-boot:run
cd lis-ai && mvn spring-boot:run
```

### 前端启动

1. **安装依赖**

```bash
cd lis-web
npm install
```

2. **配置后端地址**

修改 `.env.development` 文件：

```
VITE_API_BASE_URL=http://localhost:8080
```

3. **启动开发服务器**

```bash
npm run dev
```

4. **访问系统**

打开浏览器访问 `http://localhost:5173`

### 默认账号

| 用户名 | 密码 | 角色 |
|--------|------|------|
| admin | admin123 | 系统管理员 |

---

## API文档

启动后端服务后，访问 Knife4j 接口文档：

- 网关聚合文档：http://localhost:8080/doc.html
- 各服务独立文档：http://localhost:808{x}/doc.html

---

## 项目信息

| 项目信息 | 内容 |
|---------|------|
| 项目名称 | 基于微服务架构的实验室管理系统设计与实现 |
| 开发者 | 孙亚鑫 |
| 所在院校 | 华北水利水电大学 |
| 专业 | 软件工程 |
| 版本 | V1.0 |

---

## 许可证

本项目仅供学习和研究使用。
