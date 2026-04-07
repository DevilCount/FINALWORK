# Agent-00 架构师Agent - 开发任务书

## 文档信息

| 项目名称 | 基于微服务架构的实验室管理系统（LIS） |
|---------|--------------------------------------|
| 文档版本 | V1.0.0 |
| 编写日期 | 2026年4月 |
| 任务书编号 | LIS-AGENT-00 |
| Agent编号 | Agent-00 |
| Agent角色 | 架构师Agent |
| 负责模块 | 全局架构设计、公共组件开发、网关配置、数据库初始化、模块集成 |
| 文档状态 | 正式发布 |

---

## 一、Agent基本信息

### 1.1 Agent概述

Agent-00（架构师Agent）是整个LIS系统开发团队的核心角色，承担着全局架构设计与技术底座搭建的关键职责。作为系统的"总设计师"，架构师Agent需要率先完成项目基础框架的搭建，为后续各业务Agent（Agent-01至Agent-08）的开发工作提供坚实的技术基础和规范约束。架构师Agent的工作质量直接决定了整个系统的可维护性、可扩展性和稳定性。

### 1.2 Agent职责范围

架构师Agent的职责覆盖以下核心领域：

1. **全局架构设计**：制定系统的整体技术架构方案，包括微服务划分、通信协议、数据流转方案等。
2. **公共组件开发**：开发各微服务共享的基础组件和工具类，降低重复开发成本。
3. **API网关开发**：构建统一的API入口，实现路由转发、鉴权、限流等横切关注点。
4. **认证服务开发**：实现统一的身份认证与授权机制，保障系统安全性。
5. **数据库初始化**：设计并初始化各业务数据库，确保数据层的一致性。
6. **Docker部署配置**：编写容器化部署方案，简化系统的交付与运维。
7. **模块集成**：协调各微服务之间的集成工作，确保系统整体联通。

### 1.3 协作关系

| 协作对象 | 协作内容 |
|---------|---------|
| Agent-01（前端工程师） | 提供API网关配置、跨域策略、接口文档 |
| Agent-02（用户服务） | 提供lis-common公共模块、Feign客户端接口 |
| Agent-03（标本服务） | 提供数据库初始化脚本、服务注册配置 |
| Agent-04（检验服务） | 提供公共组件、统一异常处理机制 |
| Agent-05（设备服务） | 提供HL7通信基础组件 |
| Agent-06（HL7服务） | 提供消息队列配置、网关路由规则 |
| Agent-07（统计服务） | 提供数据聚合基础组件 |
| Agent-08（AI服务） | 提供服务间调用配置、API网关路由 |

---

## 二、技术栈与版本约束

### 2.1 核心技术栈

| 技术组件 | 版本要求 | 用途说明 |
|---------|---------|---------|
| JDK | 17 | Java运行环境，采用LTS长期支持版本 |
| Maven | 3.8+ | 项目构建与依赖管理工具 |
| Spring Boot | 2.7.18 | 微服务基础框架 |
| Spring Cloud Alibaba | 2022.0.0.0 | 微服务治理框架 |
| Nacos | 2.2.3 | 服务注册与配置中心 |
| Spring Cloud Gateway | 3.1.x | API网关 |
| Sentinel | 1.8.6 | 流量控制与熔断降级 |
| MySQL | 8.0 | 关系型数据库 |
| Redis | 7.0 | 缓存与分布式锁 |
| Docker | 24.x | 容器化部署 |
| Docker Compose | 2.20+ | 多容器编排 |
| Nginx | 1.24+ | 反向代理与静态资源服务 |
| JWT (jjwt) | 0.11.5 | JSON Web Token认证 |
| MyBatis-Plus | 3.5.5 | ORM框架 |
| Hutool | 5.8.25 | Java工具类库 |
| MapStruct | 1.5.5.Final | 对象映射工具 |

### 2.2 环境要求

| 环境 | 配置要求 |
|------|---------|
| 开发环境 | JDK 17、Maven 3.8+、IDEA、MySQL 8.0、Redis 7.0、Nacos 2.2.3 |
| 测试环境 | Docker部署，4核8G以上服务器 |
| 生产环境 | Docker部署，8核16G以上服务器，MySQL主从、Redis哨兵 |

### 2.3 参考文档

| 文档名称 | 路径 |
|---------|------|
| API接口设计规范 | /workspace/lis-project/docs/02-设计文档/API接口设计规范.md |
| 数据库设计说明书 | /workspace/lis-project/docs/02-设计文档/数据库设计说明书.md |
| 编码规范 | /workspace/lis-project/docs/05-开发规范/编码规范.md |
| 概要设计说明书 | /workspace/lis-project/docs/02-设计文档/概要设计说明书.md |
| 详细设计说明书 | /workspace/lis-project/docs/02-设计文档/详细设计说明书.md |

---

## 三、任务清单

### 任务一：项目初始化

#### 1.1 创建Maven多模块项目结构

**目标**：搭建LIS系统的Maven多模块项目骨架，建立规范的工程目录结构。

**具体要求**：

在 `/workspace/lis-project/src/backend/` 目录下创建如下项目结构：

```
src/backend/
├── pom.xml                          # 父POM，统一依赖管理
├── lis-common/                      # 公共模块
│   ├── pom.xml
│   └── src/main/java/com/lis/common/
├── lis-gateway/                     # API网关
│   ├── pom.xml
│   └── src/main/java/com/lis/gateway/
├── lis-auth/                        # 认证服务
│   ├── pom.xml
│   └── src/main/java/com/lis/auth/
├── lis-user/                        # 用户服务（Agent-02开发）
├── lis-specimen/                    # 标本服务（Agent-03开发）
├── lis-report/                      # 检验服务（Agent-04开发）
├── lis-equipment/                   # 设备服务（Agent-05开发）
├── lis-hl7/                         # HL7服务（Agent-06开发）
├── lis-statistics/                  # 统计服务（Agent-07开发）
└── lis-ai/                          # AI服务（Agent-08开发）
```

**父POM依赖管理配置要求**：

- 使用 `spring-boot-starter-parent 2.7.18` 作为父级依赖。
- 在 `<dependencyManagement>` 中统一管理 Spring Cloud Alibaba 2022.0.0.0、Nacos 2.2.3、Sentinel、MyBatis-Plus、Hutool、MapStruct、jjwt 等核心依赖版本。
- 配置 `maven-compiler-plugin` 指定 JDK 17 编译级别。
- 配置 `spring-boot-maven-plugin` 支持各微服务的独立打包。
- 统一编码格式为 UTF-8。
- 配置各子模块的 `<modules>` 声明。

#### 1.2 创建lis-common公共模块

**目标**：开发各微服务共享的公共组件，避免重复代码，统一技术规范。

**具体要求**：

lis-common模块需包含以下核心组件：

**（1）统一响应封装 `Result<T>`**

```java
// 位置：com.lis.common.response.Result
// 功能：统一API响应格式
// 字段：code（业务状态码）、message（提示信息）、data（响应数据）、timestamp（时间戳）
// 方法：success()、success(data)、error()、error(code, message)
```

遵循API接口设计规范中的统一响应格式，确保所有微服务的接口返回格式一致。状态码定义需与规范文档保持同步。

**（2）全局异常处理 `GlobalExceptionHandler`**

```java
// 位置：com.lis.common.exception.handler.GlobalExceptionHandler
// 功能：统一异常捕获与处理
// 处理异常类型：
//   - BusinessException（自定义业务异常）
//   - MethodArgumentNotValidException（参数校验异常）
//   - HttpRequestMethodNotSupportedException（请求方法不支持）
//   - HttpMessageNotReadableException（请求体解析异常）
//   - AccessDeniedException（权限不足）
//   - Exception（兜底异常处理）
```

**（3）JWT工具类 `JwtUtils`**

```java
// 位置：com.lis.common.util.JwtUtils
// 功能：JWT Token的生成、解析、验证
// 核心方法：
//   - generateAccessToken(userId, username, roles) -> String
//   - generateRefreshToken(userId) -> String
//   - parseToken(token) -> Claims
//   - isTokenExpired(token) -> boolean
//   - validateToken(token) -> boolean
// 配置参数（从Nacos读取）：
//   - jwt.secret：签名密钥（Base64编码）
//   - jwt.access-token-expiration：访问令牌有效期（默认2小时）
//   - jwt.refresh-token-expiration：刷新令牌有效期（默认7天）
```

**（4）Redis工具类 `RedisUtils`**

```java
// 位置：com.lis.common.util.RedisUtils
// 功能：Redis常用操作封装
// 核心方法：
//   - set(key, value, timeout) -> void
//   - get(key) -> String
//   - delete(key) -> Boolean
//   - hasKey(key) -> Boolean
//   - expire(key, timeout) -> Boolean
//   - increment(key) -> Long（用于分布式ID生成）
//   - setIfAbsent(key, value, timeout) -> Boolean（用于分布式锁）
```

**（5）分页工具类 `PageUtils`**

```java
// 位置：com.lis.common.util.PageUtils
// 功能：分页参数处理与结果封装
// 核心方法：
//   - getPageParam(request) -> PageParam（解析前端分页参数）
//   - buildPageResult(page, records) -> PageResult（构建分页响应）
```

**（6）自定义业务异常 `BusinessException`**

```java
// 位置：com.lis.common.exception.BusinessException
// 功能：业务逻辑异常
// 字段：code（业务错误码）、message（错误信息）
// 构造方法：BusinessException(code)、BusinessException(code, message)
```

**（7）公共常量类 `CommonConstants`**

```java
// 位置：com.lis.common.constant.CommonConstants
// 功能：定义系统级常量
// 常量包括：
//   - HTTP Header常量（AUTHORIZATION_HEADER、TOKEN_PREFIX）
//   - 用户上下文Key（USER_CONTEXT_KEY）
//   - 缓存Key前缀（CACHE_USER_PREFIX、CACHE_TOKEN_PREFIX）
//   - 通用状态码（SUCCESS_CODE、ERROR_CODE）
```

**（8）自定义注解**

```java
// 位置：com.lis.common.annotation
// - @RequirePermission：权限校验注解（value指定所需权限标识）
// - @RateLimiter：限流注解（value指定限流Key，count指定阈值）
// - @OperationLog：操作日志注解（value指定操作描述）
```

**验收标准**：
- 所有公共组件编写完成并通过单元测试。
- 公共模块可被其他微服务正常引用。
- 代码风格符合编码规范.md中的要求。
- 所有工具类提供完整的Javadoc注释。

---

### 任务二：API网关开发（lis-gateway，端口8080）

#### 2.1 Spring Cloud Gateway路由配置

**目标**：构建统一的API入口，实现请求的路由转发、负载均衡和服务发现。

**具体要求**：

在 `lis-gateway` 模块中实现以下功能：

**（1）基础路由配置**

通过Nacos配置中心动态管理路由规则，支持路由的动态刷新。路由配置格式如下：

| 路由ID | 路径匹配规则 | 目标服务 | 目标端口 | 说明 |
|--------|-------------|---------|---------|------|
| auth-route | `/api/auth/**` | lis-auth | 8081 | 认证服务 |
| user-route | `/api/user/**` | lis-user | 8082 | 用户服务 |
| specimen-route | `/api/specimen/**` | lis-specimen | 8083 | 标本服务 |
| report-route | `/api/report/**` | lis-report | 8084 | 检验服务 |
| equipment-route | `/api/equipment/**` | lis-equipment | 8085 | 设备服务 |
| hl7-route | `/api/hl7/**` | lis-hl7 | 8086 | HL7服务 |
| statistics-route | `/api/statistics/**` | lis-statistics | 8087 | 统计服务 |
| ai-route | `/api/ai/**` | lis-ai | 8088 | AI服务 |

**（2）路由策略要求**

- 使用 `Path` 断言进行路径匹配，支持前缀通配符。
- 使用 `lb://` 协议实现基于Nacos服务发现的负载均衡。
- 配置 `StripPrefix` 过滤器，去除路由前缀后转发给下游服务。
- 配置路由重试机制，当下游服务不可用时自动重试（最多3次，间隔1秒）。
- 配置超时时间：连接超时5秒，读取超时30秒。

**（3）Nacos动态路由**

实现基于Nacos配置的动态路由刷新机制：
- 监听Nacos配置变更事件。
- 配置变更时自动更新Gateway路由表。
- 支持路由的动态添加、修改和删除，无需重启网关服务。

#### 2.2 JWT鉴权过滤器

**目标**：在网关层实现统一的JWT Token鉴权，拦截未认证的请求。

**具体要求**：

```java
// 位置：com.lis.gateway.filter.AuthGlobalFilter
// 类型：GlobalFilter（全局过滤器）
// 执行顺序：Ordered.HIGHEST_PRECEDENCE（最高优先级）
```

**鉴权逻辑**：

1. 从HTTP请求头 `Authorization` 中提取Bearer Token。
2. 白名单路径直接放行，白名单包括：
   - `POST /api/auth/login`（登录接口）
   - `POST /api/auth/refresh`（Token刷新接口）
   - `GET /api/auth/captcha`（验证码接口，如有）
   - `/actuator/**`（监控端点）
   - Swagger/OpenAPI相关路径
3. 对非白名单请求，调用 `JwtUtils.validateToken()` 验证Token有效性。
4. Token有效时，将用户信息（userId、username、roles）写入请求头，传递给下游服务。
5. Token无效或过期时，返回 `401 Unauthorized` 错误响应。
6. Token即将过期（剩余有效期不足30分钟）时，在响应头中添加 `X-Token-Expiring: true` 提示。

**错误响应格式**（遵循API接口设计规范）：

```json
{
  "code": 401,
  "message": "认证失败，Token无效或已过期",
  "data": null,
  "timestamp": "2026-04-06T10:00:00.000Z"
}
```

#### 2.3 跨域配置

**目标**：配置CORS策略，允许前端开发环境跨域访问API。

**具体要求**：

```java
// 位置：com.lis.gateway.config.CorsConfig
```

- 允许的源（AllowedOrigins）：开发环境配置为 `http://localhost:5173`（Vite默认端口），生产环境配置为实际域名。
- 允许的方法：GET、POST、PUT、DELETE、OPTIONS。
- 允许的头：`*`（所有请求头）。
- 允许携带凭证（AllowCredentials）：true。
- 预检请求缓存时间：3600秒。
- 暴露的头：`Authorization`、`X-Token-Expiring`。

#### 2.4 Sentinel限流配置

**目标**：集成Sentinel实现网关层的流量控制，保护后端微服务。

**具体要求**：

- 引入 `sentinel-spring-cloud-gateway-adapter` 依赖。
- 配置Sentinel Dashboard连接地址（Nacos配置中管理）。
- 实现基于路由ID的限流规则：
  - 登录接口：100次/秒（防止暴力破解）。
  - 普通查询接口：500次/秒。
  - 数据写入接口：200次/秒。
  - 文件上传接口：50次/秒。
- 实现基于IP地址的限流规则：单IP每秒最多100次请求。
- 限流后的响应格式：

```json
{
  "code": 429,
  "message": "请求过于频繁，请稍后再试",
  "data": null,
  "timestamp": "2026-04-06T10:00:00.000Z"
}
```

- 配置熔断降级规则：当下游服务错误率超过50%时触发熔断，熔断时长30秒。

#### 2.5 全局日志过滤器

**目标**：在网关层记录所有请求的访问日志，便于问题排查和审计。

**具体要求**：

```java
// 位置：com.lis.gateway.filter.AccessLogGlobalFilter
```

- 记录请求信息：请求ID（TraceId）、请求时间、客户端IP、请求方法、请求路径、响应状态码、响应耗时。
- 使用MDC（Mapped Diagnostic Context）传递TraceId，确保同一请求在网关和下游服务中共享相同的TraceId。
- 日志格式统一为JSON格式，便于ELK等日志平台采集。
- 敏感信息脱敏处理：请求参数中的密码、Token等字段需脱敏。

**验收标准**：
- 网关服务可正常启动并注册到Nacos。
- 所有路由规则可正确转发请求到下游服务。
- JWT鉴权过滤器可正确拦截未认证请求。
- 跨域配置可满足前端开发环境需求。
- Sentinel限流规则可正常生效。
- 访问日志可完整记录请求信息。

---

### 任务三：认证服务开发（lis-auth，端口8081）

#### 3.1 JWT登录接口

**接口定义**：

```
POST /api/auth/login
Content-Type: application/json
```

**请求参数**：

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| username | String | 是 | 用户名 |
| password | String | 是 | 密码（RSA加密传输） |
| captchaCode | String | 否 | 验证码（如启用） |
| captchaKey | String | 否 | 验证码Key |

**响应参数**：

| 字段 | 类型 | 说明 |
|------|------|------|
| accessToken | String | 访问令牌（有效期2小时） |
| refreshToken | String | 刷新令牌（有效期7天） |
| expiresIn | Long | 访问令牌过期时间（秒） |
| tokenType | String | 令牌类型（Bearer） |

**业务逻辑**：

1. 接收登录请求，参数校验。
2. 根据用户名查询用户信息（通过Feign调用lis-user服务）。
3. 验证密码（BCrypt比对）。
4. 检查用户状态（是否禁用、是否锁定）。
5. 验证通过后生成 `accessToken` 和 `refreshToken`。
6. 将Token信息存入Redis（Key为 `lis:token:{userId}`，Value为Token详情，设置过期时间）。
7. 记录登录日志（登录时间、IP地址、设备信息）。
8. 返回Token信息给客户端。

**安全要求**：

- 密码传输采用RSA加密，前端使用公钥加密，后端使用私钥解密。
- 连续登录失败5次后锁定账户30分钟。
- 登录成功后清除失败计数。
- Token中不存储敏感信息，仅包含userId、username、roles等必要字段。

#### 3.2 Token刷新接口

**接口定义**：

```
POST /api/auth/refresh
Content-Type: application/json
```

**请求参数**：

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| refreshToken | String | 是 | 刷新令牌 |

**业务逻辑**：

1. 验证refreshToken的有效性。
2. 检查Redis中是否存在对应的Token记录（防止Token被主动注销后仍被使用）。
3. 验证通过后生成新的accessToken，refreshToken保持不变。
4. 更新Redis中的Token记录。
5. 返回新的accessToken。

#### 3.3 获取用户信息接口

**接口定义**：

```
GET /api/auth/userinfo
Authorization: Bearer {accessToken}
```

**响应参数**：

| 字段 | 类型 | 说明 |
|------|------|------|
| userId | Long | 用户ID |
| username | String | 用户名 |
| realName | String | 真实姓名 |
| avatar | String | 头像URL |
| roles | List<String> | 角色列表 |
| permissions | List<String> | 权限标识列表 |
| departmentId | Long | 部门ID |
| departmentName | String | 部门名称 |

**业务逻辑**：

1. 从请求头中解析Token获取userId。
2. 通过Feign调用lis-user服务获取用户详细信息。
3. 查询用户的角色和权限列表。
4. 组装用户信息返回。

#### 3.4 退出登录接口

**接口定义**：

```
POST /api/auth/logout
Authorization: Bearer {accessToken}
```

**业务逻辑**：

1. 从请求头中解析Token获取userId。
2. 从Redis中删除对应的Token记录。
3. 将当前Token加入黑名单（设置过期时间为Token的剩余有效期）。
4. 返回退出成功响应。

**验收标准**：
- 登录接口可正确验证用户身份并返回Token。
- Token刷新接口可正常续期。
- 用户信息接口可返回正确的用户详情。
- 退出登录后Token立即失效。
- 所有接口返回格式符合API接口设计规范。
- 安全机制（密码加密、登录失败锁定）正常工作。

---

### 任务四：数据库初始化

#### 4.1 创建数据库

**目标**：按照数据库设计说明书创建7个业务数据库。

**具体要求**：

创建以下数据库实例：

| 数据库名称 | 用途 | 字符集 | 排序规则 |
|-----------|------|--------|---------|
| lis_user | 用户与权限管理 | utf8mb4 | utf8mb4_general_ci |
| lis_specimen | 标本管理 | utf8mb4 | utf8mb4_general_ci |
| lis_report | 检验报告管理 | utf8mb4 | utf8mb4_general_ci |
| lis_equipment | 设备管理 | utf8mb4 | utf8mb4_general_ci |
| lis_hl7 | HL7消息管理 | utf8mb4 | utf8mb4_general_ci |
| lis_statistics | 数据统计 | utf8mb4 | utf8mb4_general_ci |
| lis_ai | AI辅助诊断 | utf8mb4 | utf8mb4_general_ci |

#### 4.2 初始化SQL脚本

**目标**：编写完整的数据库初始化SQL脚本。

**具体要求**：

在 `/workspace/lis-project/sql/` 目录下创建以下SQL文件：

| 文件名 | 内容说明 |
|--------|---------|
| `01-create-databases.sql` | 创建所有数据库的DDL语句 |
| `02-lis-user.sql` | 用户服务所有表的建表语句和初始数据 |
| `03-lis-specimen.sql` | 标本服务所有表的建表语句和初始数据 |
| `04-lis-report.sql` | 检验服务所有表的建表语句和初始数据 |
| `05-lis-equipment.sql` | 设备服务所有表的建表语句和初始数据 |
| `06-lis-hl7.sql` | HL7服务所有表的建表语句和初始数据 |
| `07-lis-statistics.sql` | 统计服务所有表的建表语句和初始数据 |
| `08-lis-ai.sql` | AI服务所有表的建表语句和初始数据 |
| `09-init-data.sql` | 公共初始数据（字典数据、系统配置等） |

**建表要求**：

- 所有表必须包含公共审计字段：`id`（BIGINT，主键，雪花算法）、`create_by`（创建人）、`create_time`（创建时间）、`update_by`（更新人）、`update_time`（更新时间）、`deleted`（逻辑删除标识，0-未删除，1-已删除）、`remark`（备注）。
- 所有表名使用对应业务前缀，如 `sys_user`、`specimen`。
- 所有字段添加适当的注释（COMMENT）。
- 按照数据库设计说明书中定义的索引策略创建索引。
- 初始数据包括：管理员账户、基础角色、基础权限、字典数据等。

#### 4.3 Nacos配置初始化

**目标**：在Nacos配置中心初始化各微服务的配置信息。

**具体要求**：

在Nacos中创建以下配置（命名空间：`lis-dev`）：

| 配置Data ID | 配置Group | 内容说明 |
|-------------|----------|---------|
| `lis-gateway.yml` | `DEFAULT_GROUP` | 网关路由、限流、跨域配置 |
| `lis-auth.yml` | `DEFAULT_GROUP` | 认证服务配置（JWT密钥、Redis配置） |
| `lis-user.yml` | `DEFAULT_GROUP` | 用户服务配置 |
| `lis-common.yml` | `DEFAULT_GROUP` | 公共配置（MySQL、Redis、日志等） |
| `application.yml` | `DEFAULT_GROUP` | 全局公共配置 |

**公共配置内容**（lis-common.yml）需包含：

```yaml
# MySQL数据源配置
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://${MYSQL_HOST:localhost}:${MYSQL_PORT:3306}/${MYSQL_DATABASE:lis_user}?useUnicode=true&characterEncoding=utf8mb4&serverTimezone=Asia/Shanghai
    username: ${MYSQL_USERNAME:root}
    password: ${MYSQL_PASSWORD:root123}

# Redis配置
  redis:
    host: ${REDIS_HOST:localhost}
    port: ${REDIS_PORT:6379}
    password: ${REDIS_PASSWORD:}
    database: 0
    timeout: 5000ms
    lettuce:
      pool:
        max-active: 20
        max-idle: 10
        min-idle: 5

# MyBatis-Plus配置
mybatis-plus:
  mapper-locations: classpath*:/mapper/**/*.xml
  configuration:
    map-underscore-to-camel-case: true
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
  global-config:
    db-config:
      id-type: assign_id
      logic-delete-field: deleted
      logic-delete-value: 1
      logic-not-delete-value: 0

# 日志配置
logging:
  level:
    com.lis: debug
  pattern:
    console: "%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] [%X{traceId}] %-5level %logger{50} - %msg%n"
```

**验收标准**：
- 所有数据库创建成功，字符集和排序规则正确。
- 所有建表语句执行无误，表结构符合数据库设计说明书。
- 初始数据插入成功，管理员账户可正常登录。
- Nacos配置创建完成，各微服务可正常读取配置。

---

### 任务五：Docker部署配置

#### 5.1 各微服务Dockerfile

**目标**：为每个微服务编写标准化的Dockerfile。

**具体要求**：

在 `/workspace/lis-project/docker/` 目录下创建以下文件：

**通用Dockerfile模板**（各微服务共用，通过构建参数区分）：

```dockerfile
# 多阶段构建
# 第一阶段：Maven构建
FROM maven:3.8-openjdk-17 AS builder
WORKDIR /app
COPY pom.xml .
COPY lis-common/pom.xml lis-common/
COPY lis-${SERVICE_NAME}/pom.xml lis-${SERVICE_NAME}/
RUN mvn dependency:go-offline -pl lis-${SERVICE_NAME} -am
COPY . .
RUN mvn package -pl lis-${SERVICE_NAME} -am -DskipTests

# 第二阶段：运行时镜像
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/lis-${SERVICE_NAME}/target/*.jar app.jar
EXPOSE ${SERVICE_PORT}
ENV JAVA_OPTS="-Xms256m -Xmx512m -XX:+UseG1GC"
ENV NACOS_ADDR="localhost:8848"
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar --spring.cloud.nacos.discovery.server-addr=${NACOS_ADDR} --spring.cloud.nacos.config.server-addr=${NACOS_ADDR}"]
```

**优化要求**：
- 使用多阶段构建，减小最终镜像体积。
- 基础镜像使用 `openjdk:17-jdk-slim`，生产环境可替换为 `eclipse-temurin:17-jre`。
- 设置合理的JVM参数（根据服务重要性调整内存配置）。
- 通过环境变量注入Nacos地址、数据库连接等配置。
- 设置时区为 `Asia/Shanghai`。

#### 5.2 docker-compose.yml编排

**目标**：编写Docker Compose编排文件，实现一键部署整个系统。

**具体要求**：

```yaml
# 文件路径：/workspace/lis-project/docker/docker-compose.yml
# 包含以下服务：
# 1. mysql：MySQL 8.0数据库
# 2. redis：Redis 7.0缓存
# 3. nacos：Nacos 2.2.3服务注册与配置中心
# 4. lis-gateway：API网关（端口8080）
# 5. lis-auth：认证服务（端口8081）
# 6. lis-user：用户服务（端口8082）
# 7. lis-specimen：标本服务（端口8083）
# 8. lis-report：检验服务（端口8084）
# 9. lis-equipment：设备服务（端口8085）
# 10. lis-hl7：HL7服务（端口8086）
# 11. lis-statistics：统计服务（端口8087）
# 12. lis-ai：AI服务（端口8088）
# 13. nginx：Nginx反向代理（端口80/443）
```

**编排要求**：

- 使用 `depends_on` 确保服务启动顺序（MySQL -> Redis -> Nacos -> 各微服务）。
- 配置 `healthcheck` 健康检查，确保依赖服务就绪后再启动下游服务。
- 配置数据卷挂载：MySQL数据目录、Redis数据目录、Nacos数据目录。
- 配置网络：所有服务加入同一Docker网络 `lis-network`。
- 配置环境变量：数据库密码、Redis密码、Nacos地址等。
- 配置日志驱动：使用 `json-file`，限制单个日志文件大小和数量。
- 各微服务配置 `restart: on-failure` 策略。

#### 5.3 Nginx配置

**目标**：配置Nginx作为前端静态资源服务器和API反向代理。

**具体要求**：

```nginx
# 文件路径：/workspace/lis-project/docker/nginx/conf.d/lis.conf
```

**配置内容**：

1. **前端静态资源服务**：
   - 监听80端口。
   - 静态文件根目录指向 `/usr/share/nginx/html`。
   - 配置 `try_files $uri $uri/ /index.html` 支持Vue Router的history模式。
   - 配置静态资源缓存策略（JS/CSS文件缓存1年，图片缓存30天）。
   - 开启Gzip压缩。

2. **API反向代理**：
   - 将 `/api/**` 请求代理到 `http://lis-gateway:8080`。
   - 配置代理请求头（X-Real-IP、X-Forwarded-For、X-Forwarded-Proto）。
   - 配置WebSocket代理支持（用于实时通知功能）。
   - 配置上传文件大小限制（`client_max_body_size 50m`）。

3. **HTTPS配置**（生产环境）：
   - 监听443端口。
   - 配置SSL证书路径。
   - 配置HTTP到HTTPS的重定向。

#### 5.4 MySQL初始化脚本

**目标**：配置MySQL容器启动时自动执行初始化SQL脚本。

**具体要求**：

- 将 `/workspace/lis-project/sql/` 目录下的所有SQL文件挂载到MySQL容器的 `/docker-entrypoint-initdb.d/` 目录。
- SQL文件按文件名前缀数字顺序执行。
- 配置MySQL字符集为 `utf8mb4`。
- 创建专用数据库用户（非root），授予对应数据库的权限。

**验收标准**：
- 所有Dockerfile可成功构建镜像。
- docker-compose up可一键启动整个系统。
- Nginx可正确代理前端页面和API请求。
- MySQL初始化脚本在容器首次启动时自动执行。
- 所有微服务正常注册到Nacos并可通过网关访问。

---

### 任务六：模块集成

#### 6.1 服务注册与发现

**目标**：确保所有微服务正确注册到Nacos，并可通过服务名进行调用。

**具体要求**：

- 每个微服务在 `bootstrap.yml` 中配置Nacos服务发现。
- 服务名规范：`lis-{模块名}`，如 `lis-auth`、`lis-user`。
- 配置服务元数据：版本号、环境标识、服务分组。
- 配置Nacos命名空间隔离（dev/test/prod）。
- 确保服务注册成功后，可在Nacos控制台查看服务列表。

#### 6.2 Feign调用配置

**目标**：配置服务间的Feign远程调用，实现跨服务的数据交互。

**具体要求**：

在 `lis-common` 模块中创建各服务的Feign客户端接口：

```java
// 位置：com.lis.common.feign
// 接口定义：
// - UserFeignClient：调用用户服务（获取用户信息、校验权限）
// - SpecimenFeignClient：调用标本服务（查询标本状态）
// - ReportFeignClient：调用检验服务（查询报告信息）
// - EquipmentFeignClient：调用设备服务（查询设备状态）
```

**Feign配置要求**：

- 配置请求超时：连接超时5秒，读取超时10秒。
- 配置熔断降级：调用失败时返回兜底数据，避免级联故障。
- 配置请求拦截器：在Feign请求头中传递用户上下文信息（userId、username、Token）。
- 配置日志级别：生产环境为BASIC，开发环境为FULL。

#### 6.3 统一日志格式

**目标**：建立统一的日志规范，便于日志采集和问题排查。

**具体要求**：

- 日志格式统一为：`时间 [线程名] [TraceId] 日志级别 类名 - 消息内容`。
- 使用Logback作为日志框架。
- 日志级别规范：ERROR（系统异常）、WARN（业务告警）、INFO（关键业务操作）、DEBUG（调试信息）。
- 关键业务操作（登录、标本签收、报告审核等）必须记录INFO级别日志。
- 所有异常必须记录ERROR级别日志，包含完整的异常堆栈。
- 敏感信息（密码、身份证号、手机号）在日志中脱敏处理。

#### 6.4 跨服务异常处理

**目标**：建立统一的跨服务异常处理机制，确保异常信息可正确传递。

**具体要求**：

- 自定义Feign错误解码器 `FeignErrorDecoder`，将下游服务的异常响应解析为 `BusinessException`。
- 在网关层统一捕获下游服务的异常响应，转换为标准错误格式返回给前端。
- 定义统一的业务错误码体系：

| 错误码范围 | 说明 |
|-----------|------|
| 10000-19999 | 通用错误（参数校验、系统异常等） |
| 20000-29999 | 认证与授权错误 |
| 30000-39999 | 用户管理错误 |
| 40000-49999 | 标本管理错误 |
| 50000-59999 | 检验报告错误 |
| 60000-69999 | 设备管理错误 |
| 70000-79999 | HL7接口错误 |
| 80000-89999 | 统计分析错误 |
| 90000-99999 | AI诊断错误 |

**验收标准**：
- 所有微服务成功注册到Nacos并可在控制台查看。
- Feign调用可正常工作，请求头中的用户上下文可正确传递。
- 日志格式统一，TraceId可在网关和下游服务间正确传递。
- 跨服务异常可被正确捕获和处理，返回标准错误格式。

---

## 四、输出路径与交付物

### 4.1 源代码输出

| 输出内容 | 路径 |
|---------|------|
| lis-common公共模块 | `/workspace/lis-project/src/backend/lis-common/` |
| lis-gateway网关服务 | `/workspace/lis-project/src/backend/lis-gateway/` |
| lis-auth认证服务 | `/workspace/lis-project/src/backend/lis-auth/` |
| 父POM及项目骨架 | `/workspace/lis-project/src/backend/pom.xml` |

### 4.2 SQL脚本输出

| 输出内容 | 路径 |
|---------|------|
| 数据库初始化脚本 | `/workspace/lis-project/sql/` |

### 4.3 Docker配置输出

| 输出内容 | 路径 |
|---------|------|
| Dockerfile文件 | `/workspace/lis-project/docker/` |
| docker-compose编排 | `/workspace/lis-project/docker/docker-compose.yml` |
| Nginx配置 | `/workspace/lis-project/docker/nginx/` |

### 4.4 开发报告

| 输出内容 | 路径 |
|---------|------|
| 架构师开发报告 | `/workspace/lis-project/reports/agent-reports/Agent-00-架构师-开发报告.md` |

---

## 五、技术约束与规范要求

### 5.1 编码规范

- 所有Java代码必须符合 `/workspace/lis-project/docs/05-开发规范/编码规范.md` 中的后端编码规范。
- 包命名使用 `com.lis.{模块名}` 格式。
- 类命名使用大驼峰（UpperCamelCase），方法命名使用小驼峰（lowerCamelCase）。
- 所有公共方法必须提供完整的Javadoc注释。
- 代码中禁止出现魔法值，所有常量必须定义在常量类中。

### 5.2 接口规范

- 所有API接口必须符合 `/workspace/lis-project/docs/02-设计文档/API接口设计规范.md`。
- 接口URL遵循RESTful风格，以名词为主，通过HTTP方法区分操作类型。
- 统一使用 `Result<T>` 封装响应结果。
- 统一异常处理，返回标准错误格式。

### 5.3 数据库规范

- 所有数据库设计必须符合 `/workspace/lis-project/docs/02-设计文档/数据库设计说明书.md`。
- 表名使用对应业务前缀，字段名使用下划线命名法。
- 所有表必须包含公共审计字段。
- 禁止使用物理删除，统一使用逻辑删除。

### 5.4 安全规范

- 所有密码使用BCrypt加密存储。
- JWT Token签名密钥使用Base64编码的随机字符串，长度不少于256位。
- 数据库连接密码、Redis密码等敏感配置通过环境变量注入，不硬编码在配置文件中。
- SQL语句使用参数化查询，防止SQL注入。

---

## 六、验收标准

### 6.1 功能验收

- [ ] Maven多模块项目结构创建完成，父POM依赖管理配置正确。
- [ ] lis-common公共模块所有组件开发完成并通过单元测试。
- [ ] API网关服务可正常启动，路由转发、JWT鉴权、跨域、限流功能正常。
- [ ] 认证服务四个接口（登录、刷新、用户信息、退出）功能正常。
- [ ] 7个数据库创建成功，所有建表语句和初始数据执行无误。
- [ ] Nacos配置初始化完成，各微服务可正常读取配置。
- [ ] Docker部署配置完成，docker-compose up可一键启动系统。
- [ ] 所有微服务注册到Nacos，Feign调用正常工作。

### 6.2 质量验收

- [ ] 代码符合编码规范，无明显的代码坏味道。
- [ ] 所有公共组件提供完整的Javadoc注释。
- [ ] 单元测试覆盖率不低于70%。
- [ ] 无已知的Critical和High级别安全漏洞。

### 6.3 文档验收

- [ ] 架构师开发报告编写完成，内容详实。
- [ ] SQL脚本文件命名规范，包含完整的注释。
- [ ] Docker配置文件包含使用说明。

---

## 七、开发进度安排

| 阶段 | 任务内容 | 预计工时 |
|------|---------|---------|
| 第一阶段 | 项目初始化（Maven结构、父POM、lis-common） | 4小时 |
| 第二阶段 | API网关开发（路由、鉴权、跨域、限流） | 4小时 |
| 第三阶段 | 认证服务开发（登录、刷新、用户信息、退出） | 3小时 |
| 第四阶段 | 数据库初始化（建库、建表、初始数据、Nacos配置） | 3小时 |
| 第五阶段 | Docker部署配置（Dockerfile、Compose、Nginx） | 2小时 |
| 第六阶段 | 模块集成（服务注册、Feign、日志、异常处理） | 2小时 |
| 第七阶段 | 测试与文档（集成测试、开发报告编写） | 2小时 |
| **合计** | | **20小时** |

---

## 八、风险与注意事项

### 8.1 技术风险

| 风险项 | 风险等级 | 应对措施 |
|--------|---------|---------|
| Spring Cloud Alibaba版本兼容性问题 | 中 | 使用官方推荐的版本组合，提前进行兼容性验证 |
| Nacos配置中心连接不稳定 | 低 | 配置重连机制，本地缓存兜底 |
| JWT Token安全性 | 中 | 使用强密钥、设置合理过期时间、支持Token注销 |
| Docker网络通信问题 | 低 | 使用自定义网络，确保容器间可通过服务名通信 |

### 8.2 注意事项

1. 架构师Agent的工作是后续所有Agent开发的基础，必须优先完成。
2. lis-common模块的任何变更都需要通知所有下游Agent，避免兼容性问题。
3. API网关的路由规则变更需要同步更新前端API调用地址。
4. 数据库表结构的变更需要经过评审，避免影响其他Agent的开发。
5. Docker部署配置需要考虑开发环境和生产环境的差异，通过环境变量进行区分。

---

*本文档由项目架构组编写，作为Agent-00（架构师Agent）的开发任务书，指导其完成LIS系统基础架构的搭建工作。*

---

## 【Git工作流规范】

> 本章节为V3.0新增，所有规则具有强制约束力。详细规范参见 `/workspace/lis-project/docs/05-开发规范/Git版本控制规范.md`。

### 分支管理

- **工作分支**：`feature/agent-00-architecture`
- **修复分支**：`bugfix/agent-00-{description}`
- **禁止**直接在`main`或`develop`分支上提交任何代码
- 分支由项目经理（PM）创建，你只能在分配的分支上工作

### 提交规范

- **格式**：`[Agent-00] {类型}({范围}): {简述}`
- **类型**：feat（新功能）、fix（修复）、refactor（重构）、docs（文档）、test（测试）、chore（构建/配置）
- **示例**：`[Agent-00] feat(架构搭建): 新增XXX功能`
- 每个功能点至少一次提交，禁止积压大量变更后一次性提交
- 提交前必须确保代码可编译、功能可运行

### 过程文档要求

每个功能点完成后，必须同步提交过程文档：

| 文档类型 | 存放路径 | 提交时机 |
|---------|---------|---------|
| 功能开发记录 | `/workspace/lis-project/docs/process/Agent-00/{功能名称}-开发记录.md` | 每个功能点完成时 |
| 变更日志 | `/workspace/lis-project/docs/process/Agent-00/CHANGELOG.md` | 每次提交时更新 |

过程文档模板参见：`/workspace/lis-project/docs/process/开发记录模板.md`

### 阶段交付要求

完成本阶段全部任务后，必须提交以下交付物（缺一不可）：

1. ✅ 阶段开发报告（按D09《开发报告模板》格式）
2. ✅ CHANGELOG变更日志
3. ✅ 所有功能点的开发记录（`docs/process/Agent-00/`目录下）
4. ✅ 数据库变更脚本（如有数据库结构变更，含回滚脚本）
5. ✅ 通知项目经理进行合并审查

### PM汇报机制

- **每完成一个功能点**：Git commit + 过程文档 + 通知PM（附commit hash）
- **遇到阻塞问题**：立即通知PM，不得自行跳过或绕过
- **每日结束前**：提交当日进度总结到CHANGELOG
- **合并请求**：全部完成后，等待PM审查代码和文档，经PM审批后方可合并到develop

### 违规后果

| 违规行为 | 后果 |
|---------|------|
| 直接在main/develop上提交 | PM执行git reset回退 + 警告 |
| 提交信息不规范 | PM驳回提交，要求修改 |
| 过程文档缺失 | PM驳回合并请求 |
| 未经审批自行合并 | PM回退合并 + 暂停Agent |
| 虚假代码/文档 | PM回退分支 + 按红线禁令R1处理 |
