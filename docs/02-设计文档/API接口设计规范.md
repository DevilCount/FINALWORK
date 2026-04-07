# API 接口设计规范

## 文档信息

| 项目名称 | 基于微服务架构的实验室管理系统（LIS） |
|---------|--------------------------------------|
| 文档版本 | V1.0.0 |
| 技术栈   | Spring Boot + Spring Cloud Alibaba |
| 网关端口 | 8080 |
| API前缀  | /api |
| 认证方式 | JWT Token |

---

## 1 概述

### 1.1 设计原则

本系统 API 接口设计遵循以下核心原则：

1. **RESTful 风格**：所有接口严格按照 REST（Representational State Transfer）架构风格设计，以资源（Resource）为核心，通过 HTTP 方法（GET、POST、PUT、DELETE）表达对资源的操作语义。
2. **统一规范**：所有接口遵循统一的 URL 命名规范、请求格式、响应格式和错误处理机制，降低前端对接成本。
3. **面向资源**：URL 路径以名词（资源）为主，避免在 URL 中使用动词。操作类型通过 HTTP 方法区分。
4. **无状态**：每个请求应包含处理该请求所需的全部信息，服务端不依赖会话状态。
5. **版本控制**：API 通过 URL 前缀进行版本管理，便于后续升级和兼容。
6. **安全性**：所有接口（除登录等公开接口外）均需携带 JWT Token 进行身份认证，敏感操作需进行权限校验。

### 1.2 协议与数据格式

| 项目       | 说明                          |
|-----------|-------------------------------|
| 通信协议   | HTTPS（生产环境强制）          |
| 数据格式   | JSON（Content-Type: application/json） |
| 字符编码   | UTF-8                         |
| 时间格式   | ISO 8601（yyyy-MM-dd'T'HH:mm:ss.SSS'Z'） |
| 时区       | UTC（协调世界时）              |

### 1.3 认证机制

系统采用 **JWT（JSON Web Token）** 进行身份认证，具体流程如下：

1. 用户通过 `/api/auth/login` 接口提交用户名和密码，服务端验证通过后返回 `access_token` 和 `refresh_token`。
2. 后续所有需要认证的接口，客户端须在 HTTP 请求头中携带 Token：

```
Authorization: Bearer {access_token}
```

3. `access_token` 有效期为 2 小时，过期后可使用 `refresh_token`（有效期 7 天）调用 `/api/auth/refresh` 接口获取新的 `access_token`。
4. Token 过期或无效时，服务端返回 `401 Unauthorized` 状态码。

### 1.4 版本控制

API 版本通过 URL 路径前缀进行管理：

```
/api/v1/{服务名}/{资源名}
```

当前系统默认使用 **v1** 版本，为简化文档描述，后续接口路径中省略版本号前缀。如需引入不兼容变更，将发布 v2 版本，同时保持 v1 版本在过渡期内可用。

### 1.5 跨域策略

系统通过 Spring Cloud Gateway 统一配置 CORS（跨域资源共享）策略，允许前端开发环境跨域访问。生产环境建议前后端同域部署，避免跨域问题。

---

## 2 统一规范

### 2.1 URL 规范

**基本格式：**

```
/api/{服务名}/{资源名}/{操作或标识}
```

**命名规则：**

| 规则         | 说明                                       | 示例                          |
|-------------|--------------------------------------------|-------------------------------|
| 全部小写     | URL 路径统一使用小写字母                    | `/api/user/users`             |
| 连字符分隔   | 多个单词使用连字符（-）分隔                 | `/api/report/critical-values` |
| 资源名复数   | 资源名使用复数形式                          | `/api/specimen/specimens`     |
| 路径参数     | 使用 `{id}` 表示具体资源标识                | `/api/user/users/{id}`        |
| 层级关系     | 子资源放在父资源路径下                      | `/api/user/roles/{id}/menus`  |

**HTTP 方法语义：**

| 方法     | 用途     | 幂等性 | 是否有请求体 |
|---------|---------|--------|-------------|
| GET     | 查询资源 | 是     | 否           |
| POST    | 创建资源 | 否     | 是           |
| PUT     | 修改资源 | 是     | 是           |
| DELETE  | 删除资源 | 是     | 否           |

### 2.2 请求规范

**通用请求头：**

| 请求头              | 是否必须 | 说明                                      |
|--------------------|---------|-------------------------------------------|
| Content-Type       | 是      | `application/json;charset=UTF-8`          |
| Authorization      | 否*     | `Bearer {token}`，除公开接口外必须携带     |
| Accept             | 否      | `application/json`                        |
| X-Request-Id       | 否      | 请求追踪ID，用于链路追踪（客户端生成UUID） |

**请求参数传递方式：**

- **GET 请求**：查询参数通过 URL Query String 传递，如 `?pageNum=1&pageSize=10&keyword=张三`
- **POST / PUT 请求**：请求体以 JSON 格式传递
- **路径参数**：资源标识通过 URL 路径传递，如 `/api/user/users/123`

### 2.3 统一响应格式

所有接口响应均使用统一的 JSON 包装格式：

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {},
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

**字段说明：**

| 字段        | 类型     | 说明                                     |
|------------|---------|------------------------------------------|
| code       | Integer | 业务状态码（200 表示成功，其他表示失败）   |
| message    | String  | 响应消息描述                              |
| data       | Object  | 响应数据，可为对象、数组或 null            |
| timestamp  | String  | 服务端响应时间戳（ISO 8601 格式）          |

### 2.4 HTTP 状态码与业务错误码

**HTTP 状态码使用规范：**

| 状态码 | 含义       | 使用场景                               |
|-------|-----------|----------------------------------------|
| 200   | 成功       | 请求处理成功                            |
| 201   | 创建成功   | 资源创建成功（POST 请求）               |
| 204   | 无内容     | 删除成功（DELETE 请求）                 |
| 400   | 请求错误   | 请求参数校验失败                        |
| 401   | 未授权     | Token 缺失或过期                        |
| 403   | 禁止访问   | 权限不足                                |
| 404   | 未找到     | 资源不存在                              |
| 409   | 冲突       | 资源状态冲突（如重复操作）              |
| 500   | 服务器错误 | 服务端内部异常                          |

**业务错误码（code 字段）定义：**

| 错误码 | 含义             | 说明                       |
|-------|-----------------|----------------------------|
| 200   | 操作成功         | 请求处理成功               |
| 400   | 请求参数错误     | 参数校验不通过             |
| 401   | 未认证           | Token 无效或已过期         |
| 403   | 权限不足         | 无权访问该资源             |
| 404   | 资源不存在       | 查询的资源数据不存在       |
| 409   | 业务冲突         | 数据状态冲突               |
| 500   | 服务器内部错误   | 系统异常                   |
| 10001 | 用户名或密码错误 | 登录失败                   |
| 10002 | 账号已被禁用     | 用户状态为停用             |
| 10003 | Token已过期      | access_token 过期          |
| 10004 | 刷新Token失败    | refresh_token 无效         |
| 20001 | 标本状态异常     | 标本当前状态不允许该操作   |
| 20002 | 报告审核失败     | 审核不通过                 |
| 30001 | 设备离线         | 设备未联机                 |
| 30002 | 校准失败         | 设备校准异常               |

**错误响应示例：**

```json
{
  "code": 400,
  "message": "请求参数错误：用户名不能为空",
  "data": null,
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

### 2.5 分页规范

所有列表查询接口统一使用以下分页参数：

**请求参数：**

| 参数名    | 类型    | 必须 | 默认值 | 说明                   |
|----------|--------|------|-------|------------------------|
| pageNum  | Integer | 否   | 1     | 当前页码（从1开始）     |
| pageSize | Integer | 否   | 10    | 每页记录数（最大100）   |

**分页响应结构：**

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "total": 128,
    "pageNum": 1,
    "pageSize": 10,
    "pages": 13,
    "list": []
  },
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

**分页响应字段说明：**

| 字段      | 类型     | 说明                     |
|----------|---------|--------------------------|
| total    | Long    | 总记录数                 |
| pageNum  | Integer | 当前页码                 |
| pageSize | Integer | 每页记录数               |
| pages    | Integer | 总页数                   |
| list     | Array   | 当前页数据列表           |

---

## 3 接口清单

### 3.1 认证服务（/api/auth）

| 序号 | URL                          | 方法   | 描述         | 是否需要认证 |
|------|------------------------------|--------|-------------|-------------|
| 1    | /api/auth/login              | POST   | 用户登录     | 否          |
| 2    | /api/auth/logout             | POST   | 退出登录     | 是          |
| 3    | /api/auth/userinfo           | GET    | 获取当前用户信息 | 是       |
| 4    | /api/auth/refresh            | POST   | 刷新Token    | 否*         |

> *注：/api/auth/refresh 接口使用 refresh_token 进行认证，无需 access_token。

### 3.2 用户服务（/api/user）

| 序号 | URL                                      | 方法   | 描述           | 权限标识              |
|------|------------------------------------------|--------|---------------|-----------------------|
| 1    | /api/user/users                          | GET    | 用户列表       | system:user:list      |
| 2    | /api/user/users                          | POST   | 新增用户       | system:user:add       |
| 3    | /api/user/users/{id}                     | PUT    | 修改用户       | system:user:edit      |
| 4    | /api/user/users/{id}                     | DELETE | 删除用户       | system:user:delete    |
| 5    | /api/user/users/{id}/status              | PUT    | 启用/停用用户  | system:user:status    |
| 6    | /api/user/users/{id}/password            | PUT    | 修改密码       | system:user:password  |
| 7    | /api/user/users/{id}/roles               | PUT    | 分配角色       | system:user:role      |
| 8    | /api/user/roles                          | GET    | 角色列表       | system:role:list      |
| 9    | /api/user/roles                          | POST   | 新增角色       | system:role:add       |
| 10   | /api/user/roles/{id}                     | PUT    | 修改角色       | system:role:edit      |
| 11   | /api/user/roles/{id}                     | DELETE | 删除角色       | system:role:delete    |
| 12   | /api/user/menus                          | GET    | 菜单树         | system:menu:list      |
| 13   | /api/user/roles/{id}/menus               | PUT    | 角色菜单分配   | system:role:menu      |
| 14   | /api/user/depts                          | GET    | 部门列表       | system:dept:list      |
| 15   | /api/user/dicts                          | GET    | 字典数据       | -                     |

### 3.3 标本服务（/api/specimen）

| 序号 | URL                                       | 方法   | 描述         | 权限标识               |
|------|-------------------------------------------|--------|-------------|------------------------|
| 1    | /api/specimen/specimens                   | GET    | 标本列表     | specimen:list          |
| 2    | /api/specimen/specimens                   | POST   | 标本登记     | specimen:register      |
| 3    | /api/specimen/specimens/{id}              | GET    | 标本详情     | specimen:detail        |
| 4    | /api/specimen/specimens/{id}/receive      | PUT    | 标本签收     | specimen:receive       |
| 5    | /api/specimen/specimens/{id}/store        | PUT    | 标本入库     | specimen:store         |
| 6    | /api/specimen/specimens/{id}/complete     | PUT    | 标本完成     | specimen:complete      |
| 7    | /api/specimen/specimens/{id}/trace        | GET    | 标本追溯     | specimen:trace         |
| 8    | /api/specimen/barcode                     | POST   | 生成条码     | specimen:barcode       |
| 9    | /api/specimen/statistics                  | GET    | 标本统计     | specimen:statistics    |

### 3.4 检验服务（/api/report）

| 序号 | URL                                           | 方法   | 描述         | 权限标识              |
|------|-----------------------------------------------|--------|-------------|-----------------------|
| 1    | /api/report/reports                           | GET    | 报告列表     | report:list           |
| 2    | /api/report/reports                           | POST   | 创建报告     | report:create         |
| 3    | /api/report/reports/{id}                      | GET    | 报告详情     | report:detail         |
| 4    | /api/report/reports/{id}/result               | PUT    | 录入结果     | report:result         |
| 5    | /api/report/reports/{id}/audit                | PUT    | 报告审核     | report:audit          |
| 6    | /api/report/reports/{id}/publish              | PUT    | 报告发布     | report:publish        |
| 7    | /api/report/reports/{id}/recall               | PUT    | 报告回收     | report:recall         |
| 8    | /api/report/patients                          | GET    | 患者列表     | patient:list          |
| 9    | /api/report/patients                          | POST   | 患者登记     | patient:register      |
| 10   | /api/report/critical-values                   | GET    | 危急值列表   | critical:list         |
| 11   | /api/report/critical-values/{id}/handle       | PUT    | 危急值处理   | critical:handle       |

### 3.5 设备服务（/api/equipment）

| 序号 | URL                                            | 方法   | 描述         | 权限标识               |
|------|------------------------------------------------|--------|-------------|------------------------|
| 1    | /api/equipment/equipments                      | GET    | 设备列表     | equipment:list         |
| 2    | /api/equipment/equipments                      | POST   | 新增设备     | equipment:add          |
| 3    | /api/equipment/equipments/{id}                 | PUT    | 修改设备     | equipment:edit         |
| 4    | /api/equipment/equipments/{id}                 | DELETE | 删除设备     | equipment:delete       |
| 5    | /api/equipment/equipments/{id}/status          | GET    | 设备状态     | equipment:status       |
| 6    | /api/equipment/equipments/{id}/calibrate       | POST   | 校准记录     | equipment:calibrate    |
| 7    | /api/equipment/equipments/{id}/maintain        | GET    | 维护记录     | equipment:maintain     |
| 8    | /api/equipment/equipments/{id}/communicate     | POST   | 联机测试     | equipment:communicate  |

### 3.6 HL7 服务（/api/hl7）

| 序号 | URL                           | 方法   | 描述         | 权限标识         |
|------|-------------------------------|--------|-------------|------------------|
| 1    | /api/hl7/messages/send        | POST   | 发送HL7消息  | hl7:message:send |
| 2    | /api/hl7/messages             | GET    | 消息日志     | hl7:message:list |
| 3    | /api/hl7/config               | GET    | 接口配置     | hl7:config:list  |
| 4    | /api/hl7/config/{id}          | PUT    | 修改配置     | hl7:config:edit  |

### 3.7 统计服务（/api/statistics）

| 序号 | URL                          | 方法   | 描述         | 权限标识             |
|------|------------------------------|--------|-------------|----------------------|
| 1    | /api/statistics/workload     | GET    | 工作量统计   | statistics:workload  |
| 2    | /api/statistics/specimen     | GET    | 标本统计     | statistics:specimen  |
| 3    | /api/statistics/equipment    | GET    | 设备统计     | statistics:equipment |
| 4    | /api/statistics/report       | GET    | 报告统计     | statistics:report    |

### 3.8 AI 服务（/api/ai）

| 序号 | URL                                 | 方法   | 描述         | 权限标识          |
|------|-------------------------------------|--------|-------------|-------------------|
| 1    | /api/ai/diagnosis                   | POST   | AI诊断       | ai:diagnosis      |
| 2    | /api/ai/diagnosis/records           | GET    | 诊断记录     | ai:record:list    |
| 3    | /api/ai/diagnosis/records/{id}/audit| PUT   | 审核诊断     | ai:record:audit   |
| 4    | /api/ai/rules                       | GET    | 诊断规则     | ai:rule:list      |
| 5    | /api/ai/rules                       | POST   | 新增规则     | ai:rule:add       |
| 6    | /api/ai/rules/{id}                  | PUT    | 修改规则     | ai:rule:edit      |

---

## 4 接口详细定义

### 4.1 认证服务（/api/auth）

#### 4.1.1 用户登录

**POST** `/api/auth/login`

**描述：** 用户通过用户名和密码进行登录认证，成功后返回 JWT Token。

**请求参数：**

| 参数名   | 类型   | 必须 | 说明     |
|---------|--------|------|----------|
| username| String | 是   | 用户名   |
| password| String | 是   | 密码     |

**请求示例：**

```json
{
  "username": "admin",
  "password": "123456"
}
```

**成功响应示例：**

```json
{
  "code": 200,
  "message": "登录成功",
  "data": {
    "access_token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTc0MzkyNDgwMCwiZXhwIjoxNzQzOTMxNjAwfQ.xxxxx",
    "refresh_token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTc0MzkyNDgwMCwiZXhwIjoxNzQ0NTI5NjAwfQ.yyyyy",
    "token_type": "Bearer",
    "expires_in": 7200
  },
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

**失败响应示例：**

```json
{
  "code": 10001,
  "message": "用户名或密码错误",
  "data": null,
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

---

#### 4.1.2 退出登录

**POST** `/api/auth/logout`

**描述：** 用户退出登录，服务端将当前 Token 加入黑名单使其失效。

**请求头：**

```
Authorization: Bearer {access_token}
```

**请求参数：** 无

**成功响应示例：**

```json
{
  "code": 200,
  "message": "退出成功",
  "data": null,
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

---

#### 4.1.3 获取当前用户信息

**GET** `/api/auth/userinfo`

**描述：** 根据当前请求中的 Token 获取已登录用户的详细信息，包括角色和权限。

**请求头：**

```
Authorization: Bearer {access_token}
```

**请求参数：** 无

**成功响应示例：**

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "userId": 1,
    "username": "admin",
    "realName": "系统管理员",
    "avatar": "https://example.com/avatar/admin.png",
    "email": "admin@lis.com",
    "phone": "13800138000",
    "deptId": 1,
    "deptName": "信息科",
    "roles": [
      {
        "roleId": 1,
        "roleName": "超级管理员",
        "roleCode": "SUPER_ADMIN"
      }
    ],
    "permissions": [
      "system:user:list",
      "system:user:add",
      "system:user:edit",
      "system:user:delete",
      "specimen:list",
      "specimen:register",
      "report:list",
      "report:audit"
    ]
  },
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

---

#### 4.1.4 刷新Token

**POST** `/api/auth/refresh`

**描述：** 使用 refresh_token 获取新的 access_token，用于在 access_token 过期后无感续期。

**请求参数：**

| 参数名        | 类型   | 必须 | 说明          |
|--------------|--------|------|---------------|
| refresh_token| String | 是   | 刷新令牌      |

**请求示例：**

```json
{
  "refresh_token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTc0MzkyNDgwMCwiZXhwIjoxNzQ0NTI5NjAwfQ.yyyyy"
}
```

**成功响应示例：**

```json
{
  "code": 200,
  "message": "刷新成功",
  "data": {
    "access_token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTc0MzkzMjAwMCwiZXhwIjoxNzQzOTM4ODAwfQ.zzzzz",
    "refresh_token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTc0MzkzMjAwMCwiZXhwIjoxNzQ0NTM2ODAwfQ.wwwww",
    "token_type": "Bearer",
    "expires_in": 7200
  },
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

**失败响应示例：**

```json
{
  "code": 10004,
  "message": "刷新Token已失效，请重新登录",
  "data": null,
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

---

### 4.2 用户服务（/api/user）

#### 4.2.1 用户列表

**GET** `/api/user/users`

**描述：** 分页查询用户列表，支持按用户名、手机号、状态等条件搜索。

**请求参数（Query String）：**

| 参数名   | 类型    | 必须 | 说明                         |
|---------|---------|------|------------------------------|
| pageNum | Integer | 否   | 页码，默认1                  |
| pageSize| Integer | 否   | 每页数量，默认10             |
| keyword | String  | 否   | 搜索关键词（用户名/姓名）    |
| status  | Integer | 否   | 状态：0-停用，1-启用         |
| deptId  | Long    | 否   | 部门ID                       |
| roleId  | Long    | 否   | 角色ID                       |

**成功响应示例：**

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "total": 56,
    "pageNum": 1,
    "pageSize": 10,
    "pages": 6,
    "list": [
      {
        "userId": 1,
        "username": "admin",
        "realName": "系统管理员",
        "email": "admin@lis.com",
        "phone": "13800138000",
        "status": 1,
        "deptId": 1,
        "deptName": "信息科",
        "roles": [
          {
            "roleId": 1,
            "roleName": "超级管理员"
          }
        ],
        "createTime": "2026-01-01T00:00:00.000Z",
        "updateTime": "2026-04-01T10:30:00.000Z"
      },
      {
        "userId": 2,
        "username": "zhangsan",
        "realName": "张三",
        "email": "zhangsan@lis.com",
        "phone": "13800138001",
        "status": 1,
        "deptId": 2,
        "deptName": "检验科",
        "roles": [
          {
            "roleId": 2,
            "roleName": "检验员"
          }
        ],
        "createTime": "2026-01-15T08:00:00.000Z",
        "updateTime": "2026-03-20T14:00:00.000Z"
      }
    ]
  },
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

---

#### 4.2.2 新增用户

**POST** `/api/user/users`

**描述：** 创建新用户账号。

**请求参数：**

| 参数名    | 类型     | 必须 | 说明                     |
|----------|---------|------|--------------------------|
| username | String  | 是   | 用户名（唯一，4-20位）   |
| password | String  | 是   | 密码（6-20位）           |
| realName | String  | 是   | 真实姓名                 |
| email    | String  | 否   | 邮箱地址                 |
| phone    | String  | 否   | 手机号码                 |
| deptId   | Long    | 是   | 所属部门ID               |
| roleIds  | Long[]  | 是   | 角色ID数组               |
| status   | Integer | 否   | 状态：0-停用，1-启用，默认1 |

**请求示例：**

```json
{
  "username": "lisi",
  "password": "Abc@123456",
  "realName": "李四",
  "email": "lisi@lis.com",
  "phone": "13800138002",
  "deptId": 2,
  "roleIds": [2, 3],
  "status": 1
}
```

**成功响应示例：**

```json
{
  "code": 201,
  "message": "用户新增成功",
  "data": {
    "userId": 3
  },
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

---

#### 4.2.3 修改用户

**PUT** `/api/user/users/{id}`

**描述：** 修改指定用户的基本信息。

**路径参数：**

| 参数名 | 类型 | 说明    |
|-------|------|---------|
| id    | Long | 用户ID  |

**请求参数：**

| 参数名    | 类型     | 必须 | 说明       |
|----------|---------|------|------------|
| realName | String  | 否   | 真实姓名   |
| email    | String  | 否   | 邮箱地址   |
| phone    | String  | 否   | 手机号码   |
| deptId   | Long    | 否   | 所属部门ID |
| status   | Integer | 否   | 状态       |

**请求示例：**

```json
{
  "realName": "李四（已更新）",
  "email": "lisi_new@lis.com",
  "phone": "13900139002",
  "deptId": 3
}
```

**成功响应示例：**

```json
{
  "code": 200,
  "message": "用户修改成功",
  "data": null,
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

---

#### 4.2.4 删除用户

**DELETE** `/api/user/users/{id}`

**描述：** 删除指定用户（逻辑删除）。

**路径参数：**

| 参数名 | 类型 | 说明    |
|-------|------|---------|
| id    | Long | 用户ID  |

**成功响应示例：**

```json
{
  "code": 200,
  "message": "用户删除成功",
  "data": null,
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

---

#### 4.2.5 启用/停用用户

**PUT** `/api/user/users/{id}/status`

**描述：** 修改指定用户的启用/停用状态。

**路径参数：**

| 参数名 | 类型 | 说明    |
|-------|------|---------|
| id    | Long | 用户ID  |

**请求参数：**

| 参数名 | 类型    | 必须 | 说明                  |
|-------|--------|------|-----------------------|
| status| Integer| 是   | 状态：0-停用，1-启用  |

**请求示例：**

```json
{
  "status": 0
}
```

**成功响应示例：**

```json
{
  "code": 200,
  "message": "用户状态修改成功",
  "data": null,
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

---

#### 4.2.6 修改密码

**PUT** `/api/user/users/{id}/password`

**描述：** 修改指定用户的密码。管理员可修改任意用户密码，普通用户仅可修改自身密码。

**路径参数：**

| 参数名 | 类型 | 说明    |
|-------|------|---------|
| id    | Long | 用户ID  |

**请求参数：**

| 参数名       | 类型   | 必须 | 说明              |
|-------------|--------|------|-------------------|
| oldPassword | String | 否   | 原密码（管理员可省略）|
| newPassword | String | 是   | 新密码（6-20位）   |

**请求示例：**

```json
{
  "oldPassword": "Abc@123456",
  "newPassword": "Xyz@789012"
}
```

**成功响应示例：**

```json
{
  "code": 200,
  "message": "密码修改成功",
  "data": null,
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

---

#### 4.2.7 分配角色

**PUT** `/api/user/users/{id}/roles`

**描述：** 为指定用户分配一个或多个角色。

**路径参数：**

| 参数名 | 类型 | 说明    |
|-------|------|---------|
| id    | Long | 用户ID  |

**请求参数：**

| 参数名 | 类型    | 必须 | 说明       |
|-------|--------|------|------------|
| roleIds| Long[] | 是   | 角色ID数组 |

**请求示例：**

```json
{
  "roleIds": [2, 3, 5]
}
```

**成功响应示例：**

```json
{
  "code": 200,
  "message": "角色分配成功",
  "data": null,
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

---

#### 4.2.8 角色列表

**GET** `/api/user/roles`

**描述：** 查询系统角色列表。

**请求参数（Query String）：**

| 参数名   | 类型    | 必须 | 说明           |
|---------|---------|------|----------------|
| pageNum | Integer | 否   | 页码，默认1    |
| pageSize| Integer | 否   | 每页数量，默认10|
| keyword | String  | 否   | 角色名称关键词  |
| status  | Integer | 否   | 状态           |

**成功响应示例：**

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "total": 8,
    "pageNum": 1,
    "pageSize": 10,
    "pages": 1,
    "list": [
      {
        "roleId": 1,
        "roleName": "超级管理员",
        "roleCode": "SUPER_ADMIN",
        "status": 1,
        "remark": "拥有系统所有权限",
        "menuIds": [1, 2, 3, 4, 5],
        "createTime": "2026-01-01T00:00:00.000Z"
      },
      {
        "roleId": 2,
        "roleName": "检验员",
        "roleCode": "LAB_TECHNICIAN",
        "status": 1,
        "remark": "检验科操作人员",
        "menuIds": [6, 7, 8],
        "createTime": "2026-01-01T00:00:00.000Z"
      }
    ]
  },
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

---

#### 4.2.9 新增角色

**POST** `/api/user/roles`

**描述：** 创建新角色。

**请求参数：**

| 参数名    | 类型     | 必须 | 说明                     |
|----------|---------|------|--------------------------|
| roleName | String  | 是   | 角色名称                 |
| roleCode | String  | 是   | 角色编码（唯一，大写+下划线）|
| status   | Integer | 否   | 状态，默认1              |
| remark   | String  | 否   | 备注说明                 |
| menuIds  | Long[]  | 否   | 关联菜单ID数组           |

**请求示例：**

```json
{
  "roleName": "审核员",
  "roleCode": "AUDITOR",
  "status": 1,
  "remark": "负责报告审核",
  "menuIds": [7, 8, 9]
}
```

**成功响应示例：**

```json
{
  "code": 201,
  "message": "角色新增成功",
  "data": {
    "roleId": 9
  },
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

---

#### 4.2.10 修改角色

**PUT** `/api/user/roles/{id}`

**描述：** 修改指定角色信息。

**路径参数：**

| 参数名 | 类型 | 说明    |
|-------|------|---------|
| id    | Long | 角色ID  |

**请求参数：**

| 参数名    | 类型     | 必须 | 说明       |
|----------|---------|------|------------|
| roleName | String  | 否   | 角色名称   |
| roleCode | String  | 否   | 角色编码   |
| status   | Integer | 否   | 状态       |
| remark   | String  | 否   | 备注说明   |
| menuIds  | Long[]  | 否   | 菜单ID数组 |

**请求示例：**

```json
{
  "roleName": "高级审核员",
  "remark": "负责重要报告审核",
  "menuIds": [7, 8, 9, 10]
}
```

**成功响应示例：**

```json
{
  "code": 200,
  "message": "角色修改成功",
  "data": null,
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

---

#### 4.2.11 删除角色

**DELETE** `/api/user/roles/{id}`

**描述：** 删除指定角色。

**路径参数：**

| 参数名 | 类型 | 说明    |
|-------|------|---------|
| id    | Long | 角色ID  |

**成功响应示例：**

```json
{
  "code": 200,
  "message": "角色删除成功",
  "data": null,
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

---

#### 4.2.12 菜单树

**GET** `/api/user/menus`

**描述：** 获取系统菜单树结构，用于前端渲染导航菜单和权限控制。

**请求参数：** 无

**成功响应示例：**

```json
{
  "code": 200,
  "message": "操作成功",
  "data": [
    {
      "menuId": 1,
      "menuName": "系统管理",
      "parentId": 0,
      "path": "/system",
      "component": null,
      "icon": "setting",
      "sort": 1,
      "children": [
        {
          "menuId": 2,
          "menuName": "用户管理",
          "parentId": 1,
          "path": "/system/user",
          "component": "system/user/index",
          "icon": "user",
          "sort": 1,
          "children": [
            {
              "menuId": 10,
              "menuName": "用户新增",
              "parentId": 2,
              "path": null,
              "component": null,
              "icon": null,
              "sort": 1,
              "permission": "system:user:add",
              "type": 2,
              "children": []
            }
          ],
          "permission": null,
          "type": 1,
          "children": []
        },
        {
          "menuId": 3,
          "menuName": "角色管理",
          "parentId": 1,
          "path": "/system/role",
          "component": "system/role/index",
          "icon": "role",
          "sort": 2,
          "permission": null,
          "type": 1,
          "children": []
        }
      ],
      "permission": null,
      "type": 0,
      "children": []
    },
    {
      "menuId": 6,
      "menuName": "标本管理",
      "parentId": 0,
      "path": "/specimen",
      "component": null,
      "icon": "specimen",
      "sort": 2,
      "children": [],
      "permission": null,
      "type": 0
    }
  ],
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

---

#### 4.2.13 角色菜单分配

**PUT** `/api/user/roles/{id}/menus`

**描述：** 为指定角色分配菜单权限。

**路径参数：**

| 参数名 | 类型 | 说明    |
|-------|------|---------|
| id    | Long | 角色ID  |

**请求参数：**

| 参数名  | 类型    | 必须 | 说明         |
|--------|--------|------|--------------|
| menuIds| Long[] | 是   | 菜单ID数组   |

**请求示例：**

```json
{
  "menuIds": [1, 2, 3, 6, 7, 8, 10]
}
```

**成功响应示例：**

```json
{
  "code": 200,
  "message": "菜单分配成功",
  "data": null,
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

---

#### 4.2.14 部门列表

**GET** `/api/user/depts`

**描述：** 获取部门列表（树形结构）。

**请求参数：** 无

**成功响应示例：**

```json
{
  "code": 200,
  "message": "操作成功",
  "data": [
    {
      "deptId": 1,
      "deptName": "信息科",
      "parentId": 0,
      "sort": 1,
      "leader": "王主任",
      "phone": "13800000001",
      "children": []
    },
    {
      "deptId": 2,
      "deptName": "检验科",
      "parentId": 0,
      "sort": 2,
      "leader": "李主任",
      "phone": "13800000002",
      "children": [
        {
          "deptId": 5,
          "deptName": "生化组",
          "parentId": 2,
          "sort": 1,
          "leader": "赵组长",
          "phone": "13800000005",
          "children": []
        },
        {
          "deptId": 6,
          "deptName": "免疫组",
          "parentId": 2,
          "sort": 2,
          "leader": "钱组长",
          "phone": "13800000006",
          "children": []
        }
      ]
    }
  ],
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

---

#### 4.2.15 字典数据

**GET** `/api/user/dicts`

**描述：** 获取系统字典数据列表，用于下拉框等场景。

**请求参数（Query String）：**

| 参数名 | 类型   | 必须 | 说明         |
|-------|--------|------|--------------|
| type  | String | 否   | 字典类型编码 |

**成功响应示例（按类型查询）：**

请求：`GET /api/user/dicts?type=specimen_type`

```json
{
  "code": 200,
  "message": "操作成功",
  "data": [
    {
      "dictCode": 1,
      "dictLabel": "血液",
      "dictValue": "blood",
      "dictType": "specimen_type",
      "sort": 1,
      "remark": "血液标本"
    },
    {
      "dictCode": 2,
      "dictLabel": "尿液",
      "dictValue": "urine",
      "dictType": "specimen_type",
      "sort": 2,
      "remark": "尿液标本"
    },
    {
      "dictCode": 3,
      "dictLabel": "粪便",
      "dictValue": "stool",
      "dictType": "specimen_type",
      "sort": 3,
      "remark": "粪便标本"
    }
  ],
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

---

### 4.3 标本服务（/api/specimen）

#### 4.3.1 标本列表

**GET** `/api/specimen/specimens`

**描述：** 分页查询标本列表，支持按条码号、患者姓名、标本状态等条件筛选。

**请求参数（Query String）：**

| 参数名      | 类型    | 必须 | 说明                              |
|------------|---------|------|-----------------------------------|
| pageNum    | Integer | 否   | 页码，默认1                       |
| pageSize   | Integer | 否   | 每页数量，默认10                  |
| barcode    | String  | 否   | 条码号（精确匹配）                |
| patientName| String  | 否   | 患者姓名（模糊搜索）              |
| status     | String  | 否   | 标本状态：registered/received/stored/testing/completed |
| specimenType| String | 否   | 标本类型                          |
| startDate  | String  | 否   | 登记开始日期（yyyy-MM-dd）        |
| endDate    | String  | 否   | 登记结束日期（yyyy-MM-dd）        |

**成功响应示例：**

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "total": 230,
    "pageNum": 1,
    "pageSize": 10,
    "pages": 23,
    "list": [
      {
        "specimenId": 1,
        "barcode": "SP202604060001",
        "patientName": "张三",
        "patientId": "P20260001",
        "specimenType": "blood",
        "specimenTypeName": "血液",
        "status": "received",
        "statusName": "已签收",
        "registerTime": "2026-04-06T08:30:00.000Z",
        "receiveTime": "2026-04-06T09:00:00.000Z",
        "operatorName": "李检验"
      }
    ]
  },
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

---

#### 4.3.2 标本登记

**POST** `/api/specimen/specimens`

**描述：** 登记新的检验标本，生成条码并记录标本信息。

**请求参数：**

| 参数名        | 类型    | 必须 | 说明                     |
|--------------|---------|------|--------------------------|
| patientId    | String  | 是   | 患者ID                   |
| patientName  | String  | 是   | 患者姓名                 |
| specimenType | String  | 是   | 标本类型编码             |
| source       | String  | 是   | 标本来源：outpatient/inpatient/emergency/health_checkup |
| testItems    | String[]| 是   | 检验项目编码数组         |
| remark       | String  | 否   | 备注                     |

**请求示例：**

```json
{
  "patientId": "P20260001",
  "patientName": "张三",
  "specimenType": "blood",
  "source": "outpatient",
  "testItems": ["BLOOD_ROUTINE", "LIVER_FUNCTION", "KIDNEY_FUNCTION"],
  "remark": "空腹采血"
}
```

**成功响应示例：**

```json
{
  "code": 201,
  "message": "标本登记成功",
  "data": {
    "specimenId": 1,
    "barcode": "SP202604060001"
  },
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

---

#### 4.3.3 标本详情

**GET** `/api/specimen/specimens/{id}`

**描述：** 查询指定标本的详细信息。

**路径参数：**

| 参数名 | 类型 | 说明     |
|-------|------|----------|
| id    | Long | 标本ID   |

**成功响应示例：**

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "specimenId": 1,
    "barcode": "SP202604060001",
    "patientId": "P20260001",
    "patientName": "张三",
    "gender": "男",
    "age": 45,
    "specimenType": "blood",
    "specimenTypeName": "血液",
    "source": "outpatient",
    "sourceName": "门诊",
    "status": "received",
    "statusName": "已签收",
    "testItems": [
      {
        "itemCode": "BLOOD_ROUTINE",
        "itemName": "血常规",
        "status": "pending"
      },
      {
        "itemCode": "LIVER_FUNCTION",
        "itemName": "肝功能",
        "status": "pending"
      }
    ],
    "registerTime": "2026-04-06T08:30:00.000Z",
    "receiveTime": "2026-04-06T09:00:00.000Z",
    "storeTime": null,
    "completeTime": null,
    "operatorName": "李检验",
    "remark": "空腹采血"
  },
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

---

#### 4.3.4 标本签收

**PUT** `/api/specimen/specimens/{id}/receive`

**描述：** 对已登记的标本进行签收确认。

**路径参数：**

| 参数名 | 类型 | 说明     |
|-------|------|----------|
| id    | Long | 标本ID   |

**请求参数：**

| 参数名  | 类型   | 必须 | 说明         |
|--------|--------|------|--------------|
| remark | String | 否   | 签收备注     |

**请求示例：**

```json
{
  "remark": "标本质量合格，已签收"
}
```

**成功响应示例：**

```json
{
  "code": 200,
  "message": "标本签收成功",
  "data": null,
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

---

#### 4.3.5 标本入库

**PUT** `/api/specimen/specimens/{id}/store`

**描述：** 将已签收的标本入库，分配存储位置。

**路径参数：**

| 参数名 | 类型 | 说明     |
|-------|------|----------|
| id    | Long | 标本ID   |

**请求参数：**

| 参数名       | 类型   | 必须 | 说明         |
|-------------|--------|------|--------------|
| storageLocation| String| 否 | 存储位置     |
| remark      | String | 否   | 入库备注     |

**请求示例：**

```json
{
  "storageLocation": "A区-01架-03层",
  "remark": "已入库待检测"
}
```

**成功响应示例：**

```json
{
  "code": 200,
  "message": "标本入库成功",
  "data": null,
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

---

#### 4.3.6 标本完成

**PUT** `/api/specimen/specimens/{id}/complete`

**描述：** 标记标本所有检验项目已完成。

**路径参数：**

| 参数名 | 类型 | 说明     |
|-------|------|----------|
| id    | Long | 标本ID   |

**请求参数：**

| 参数名  | 类型   | 必须 | 说明         |
|--------|--------|------|--------------|
| remark | String | 否   | 完成备注     |

**成功响应示例：**

```json
{
  "code": 200,
  "message": "标本已完成",
  "data": null,
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

---

#### 4.3.7 标本追溯

**GET** `/api/specimen/specimens/{id}/trace`

**描述：** 查询指定标本的全生命周期追溯记录。

**路径参数：**

| 参数名 | 类型 | 说明     |
|-------|------|----------|
| id    | Long | 标本ID   |

**成功响应示例：**

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "specimenId": 1,
    "barcode": "SP202604060001",
    "patientName": "张三",
    "currentStatus": "completed",
    "traces": [
      {
        "traceId": 1,
        "action": "REGISTER",
        "actionName": "标本登记",
        "operatorName": "王登记",
        "operatorId": 5,
        "description": "标本登记，条码SP202604060001",
        "timestamp": "2026-04-06T08:30:00.000Z"
      },
      {
        "traceId": 2,
        "action": "RECEIVE",
        "actionName": "标本签收",
        "operatorName": "李检验",
        "operatorId": 6,
        "description": "标本质量合格，已签收",
        "timestamp": "2026-04-06T09:00:00.000Z"
      },
      {
        "traceId": 3,
        "action": "STORE",
        "actionName": "标本入库",
        "operatorName": "李检验",
        "operatorId": 6,
        "description": "入库位置：A区-01架-03层",
        "timestamp": "2026-04-06T09:15:00.000Z"
      },
      {
        "traceId": 4,
        "action": "TEST",
        "actionName": "开始检测",
        "operatorName": "赵检测",
        "operatorId": 7,
        "description": "开始上机检测",
        "timestamp": "2026-04-06T10:00:00.000Z"
      },
      {
        "traceId": 5,
        "action": "COMPLETE",
        "actionName": "检测完成",
        "operatorName": "赵检测",
        "operatorId": 7,
        "description": "所有检验项目已完成",
        "timestamp": "2026-04-06T11:30:00.000Z"
      }
    ]
  },
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

---

#### 4.3.8 生成条码

**POST** `/api/specimen/barcode`

**描述：** 批量生成标本条码。

**请求参数：**

| 参数名  | 类型 | 必须 | 说明       |
|--------|------|------|------------|
| count  | Integer| 是  | 生成数量   |

**请求示例：**

```json
{
  "count": 5
}
```

**成功响应示例：**

```json
{
  "code": 200,
  "message": "条码生成成功",
  "data": {
    "barcodes": [
      "SP202604060002",
      "SP202604060003",
      "SP202604060004",
      "SP202604060005",
      "SP202604060006"
    ]
  },
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

---

#### 4.3.9 标本统计

**GET** `/api/specimen/statistics`

**描述：** 获取标本统计数据，包括各状态标本数量、趋势数据等。

**请求参数（Query String）：**

| 参数名     | 类型   | 必须 | 说明                      |
|-----------|--------|------|---------------------------|
| startDate | String | 否   | 统计开始日期（yyyy-MM-dd）|
| endDate   | String | 否   | 统计结束日期（yyyy-MM-dd）|
| groupBy   | String | 否   | 分组维度：day/week/month  |

**成功响应示例：**

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "total": 1520,
    "statusSummary": [
      { "status": "registered", "statusName": "已登记", "count": 45 },
      { "status": "received", "statusName": "已签收", "count": 32 },
      { "status": "stored", "statusName": "已入库", "count": 18 },
      { "status": "testing", "statusName": "检测中", "count": 25 },
      { "status": "completed", "statusName": "已完成", "count": 1400 }
    ],
    "typeSummary": [
      { "type": "blood", "typeName": "血液", "count": 860 },
      { "type": "urine", "typeName": "尿液", "count": 380 },
      { "type": "stool", "typeName": "粪便", "count": 120 },
      { "type": "other", "typeName": "其他", "count": 160 }
    ],
    "trend": [
      { "date": "2026-04-01", "count": 210 },
      { "date": "2026-04-02", "count": 195 },
      { "date": "2026-04-03", "count": 230 },
      { "date": "2026-04-04", "count": 180 },
      { "date": "2026-04-05", "count": 160 },
      { "date": "2026-04-06", "count": 145 }
    ]
  },
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

---

### 4.4 检验服务（/api/report）

#### 4.4.1 报告列表

**GET** `/api/report/reports`

**描述：** 分页查询检验报告列表。

**请求参数（Query String）：**

| 参数名       | 类型    | 必须 | 说明                                    |
|-------------|---------|------|-----------------------------------------|
| pageNum     | Integer | 否   | 页码，默认1                             |
| pageSize    | Integer | 否   | 每页数量，默认10                        |
| patientName | String  | 否   | 患者姓名（模糊搜索）                    |
| status      | String  | 否   | 报告状态：draft/reviewed/published/recalled |
| reportType  | String  | 否   | 报告类型                                |
| startDate   | String  | 否   | 开始日期                                |
| endDate     | String  | 否   | 结束日期                                |

**成功响应示例：**

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "total": 89,
    "pageNum": 1,
    "pageSize": 10,
    "pages": 9,
    "list": [
      {
        "reportId": 1,
        "reportNo": "RPT202604060001",
        "patientName": "张三",
        "specimenBarcode": "SP202604060001",
        "reportType": "blood_routine",
        "reportTypeName": "血常规报告",
        "status": "reviewed",
        "statusName": "已审核",
        "createTime": "2026-04-06T12:00:00.000Z",
        "auditorName": "孙审核"
      }
    ]
  },
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

---

#### 4.4.2 创建报告

**POST** `/api/report/reports`

**描述：** 根据标本检验结果创建检验报告。

**请求参数：**

| 参数名          | 类型   | 必须 | 说明             |
|----------------|--------|------|------------------|
| specimenId     | Long   | 是   | 标本ID           |
| reportType     | String | 是   | 报告类型编码     |
| results        | Array  | 是   | 检验结果数组     |
| conclusion     | String | 否   | 检验结论         |

**请求示例：**

```json
{
  "specimenId": 1,
  "reportType": "blood_routine",
  "results": [
    {
      "itemCode": "WBC",
      "itemName": "白细胞计数",
      "result": "6.5",
      "unit": "10^9/L",
      "referenceRange": "3.5-9.5",
      "isAbnormal": false
    },
    {
      "itemCode": "RBC",
      "itemName": "红细胞计数",
      "result": "4.8",
      "unit": "10^12/L",
      "referenceRange": "4.3-5.8",
      "isAbnormal": false
    },
    {
      "itemCode": "HGB",
      "itemName": "血红蛋白",
      "result": "105",
      "unit": "g/L",
      "referenceRange": "130-175",
      "isAbnormal": true
    },
    {
      "itemCode": "PLT",
      "itemName": "血小板计数",
      "result": "220",
      "unit": "10^9/L",
      "referenceRange": "125-350",
      "isAbnormal": false
    }
  ],
  "conclusion": "血红蛋白偏低，建议进一步检查"
}
```

**成功响应示例：**

```json
{
  "code": 201,
  "message": "报告创建成功",
  "data": {
    "reportId": 1,
    "reportNo": "RPT202604060001"
  },
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

---

#### 4.4.3 报告详情

**GET** `/api/report/reports/{id}`

**描述：** 查询指定报告的详细信息，包含所有检验结果。

**路径参数：**

| 参数名 | 类型 | 说明     |
|-------|------|----------|
| id    | Long | 报告ID   |

**成功响应示例：**

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "reportId": 1,
    "reportNo": "RPT202604060001",
    "patientId": "P20260001",
    "patientName": "张三",
    "gender": "男",
    "age": 45,
    "specimenBarcode": "SP202604060001",
    "specimenType": "血液",
    "reportType": "blood_routine",
    "reportTypeName": "血常规报告",
    "status": "reviewed",
    "statusName": "已审核",
    "results": [
      {
        "itemCode": "WBC",
        "itemName": "白细胞计数",
        "result": "6.5",
        "unit": "10^9/L",
        "referenceRange": "3.5-9.5",
        "isAbnormal": false,
        "arrow": ""
      },
      {
        "itemCode": "HGB",
        "itemName": "血红蛋白",
        "result": "105",
        "unit": "g/L",
        "referenceRange": "130-175",
        "isAbnormal": true,
        "arrow": "↓"
      }
    ],
    "conclusion": "血红蛋白偏低，建议进一步检查",
    "createBy": "赵检测",
    "createTime": "2026-04-06T12:00:00.000Z",
    "auditorName": "孙审核",
    "auditTime": "2026-04-06T14:00:00.000Z",
    "publishTime": null
  },
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

---

#### 4.4.4 录入结果

**PUT** `/api/report/reports/{id}/result`

**描述：** 录入或修改报告的检验结果。

**路径参数：**

| 参数名 | 类型 | 说明     |
|-------|------|----------|
| id    | Long | 报告ID   |

**请求参数：**

| 参数名    | 类型   | 必须 | 说明       |
|----------|--------|------|------------|
| results  | Array  | 是   | 检验结果   |
| conclusion| String| 否   | 检验结论   |

**请求示例：**

```json
{
  "results": [
    {
      "itemCode": "WBC",
      "result": "7.2",
      "unit": "10^9/L",
      "referenceRange": "3.5-9.5",
      "isAbnormal": false
    },
    {
      "itemCode": "HGB",
      "result": "108",
      "unit": "g/L",
      "referenceRange": "130-175",
      "isAbnormal": true
    }
  ],
  "conclusion": "血红蛋白偏低，建议进一步检查贫血原因"
}
```

**成功响应示例：**

```json
{
  "code": 200,
  "message": "结果录入成功",
  "data": null,
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

---

#### 4.4.5 报告审核

**PUT** `/api/report/reports/{id}/audit`

**描述：** 对报告进行审核操作（通过或驳回）。

**路径参数：**

| 参数名 | 类型 | 说明     |
|-------|------|----------|
| id    | Long | 报告ID   |

**请求参数：**

| 参数名   | 类型    | 必须 | 说明                         |
|---------|--------|------|------------------------------|
| passed  | Boolean| 是   | 是否通过：true-通过，false-驳回 |
| opinion | String | 否   | 审核意见（驳回时必填）       |

**请求示例（通过）：**

```json
{
  "passed": true,
  "opinion": "审核通过"
}
```

**请求示例（驳回）：**

```json
{
  "passed": false,
  "opinion": "HGB结果与质控数据偏差较大，请复核"
}
```

**成功响应示例：**

```json
{
  "code": 200,
  "message": "报告审核通过",
  "data": null,
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

---

#### 4.4.6 报告发布

**PUT** `/api/report/reports/{id}/publish`

**描述：** 将已审核通过的报告发布，患者和临床科室可查看。

**路径参数：**

| 参数名 | 类型 | 说明     |
|-------|------|----------|
| id    | Long | 报告ID   |

**请求参数：** 无

**成功响应示例：**

```json
{
  "code": 200,
  "message": "报告发布成功",
  "data": null,
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

---

#### 4.4.7 报告回收

**PUT** `/api/report/reports/{id}/recall`

**描述：** 回收已发布的报告（需填写回收原因）。

**路径参数：**

| 参数名 | 类型 | 说明     |
|-------|------|----------|
| id    | Long | 报告ID   |

**请求参数：**

| 参数名  | 类型   | 必须 | 说明       |
|--------|--------|------|------------|
| reason | String | 是   | 回收原因   |

**请求示例：**

```json
{
  "reason": "发现数据录入错误，需修正后重新发布"
}
```

**成功响应示例：**

```json
{
  "code": 200,
  "message": "报告回收成功",
  "data": null,
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

---

#### 4.4.8 患者列表

**GET** `/api/report/patients`

**描述：** 分页查询患者列表。

**请求参数（Query String）：**

| 参数名       | 类型    | 必须 | 说明              |
|-------------|---------|------|-------------------|
| pageNum     | Integer | 否   | 页码，默认1       |
| pageSize    | Integer | 否   | 每页数量，默认10  |
| keyword     | String  | 否   | 搜索关键词        |
| patientId   | String  | 否   | 患者编号          |

**成功响应示例：**

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "total": 320,
    "pageNum": 1,
    "pageSize": 10,
    "pages": 32,
    "list": [
      {
        "patientId": "P20260001",
        "patientName": "张三",
        "gender": "男",
        "age": 45,
        "idCard": "320102198105****",
        "phone": "13800138001",
        "source": "outpatient",
        "sourceName": "门诊",
        "specimenCount": 3,
        "reportCount": 2,
        "lastVisitTime": "2026-04-06T08:30:00.000Z"
      }
    ]
  },
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

---

#### 4.4.9 患者登记

**POST** `/api/report/patients`

**描述：** 登记新患者信息。

**请求参数：**

| 参数名      | 类型    | 必须 | 说明                          |
|------------|---------|------|-------------------------------|
| patientName| String  | 是   | 患者姓名                      |
| gender     | String  | 是   | 性别：male/female             |
| birthDate  | String  | 是   | 出生日期（yyyy-MM-dd）        |
| idCard     | String  | 否   | 身份证号                      |
| phone      | String  | 否   | 联系电话                      |
| address    | String  | 否   | 联系地址                      |
| source     | String  | 否   | 来源：outpatient/inpatient/emergency/health_checkup |
| medicalNo  | String  | 否   | 门诊号/住院号                 |

**请求示例：**

```json
{
  "patientName": "王五",
  "gender": "male",
  "birthDate": "1990-05-15",
  "idCard": "320102199005150012",
  "phone": "13800138003",
  "address": "江苏省南京市鼓楼区XX路XX号",
  "source": "outpatient",
  "medicalNo": "MZ20260406001"
}
```

**成功响应示例：**

```json
{
  "code": 201,
  "message": "患者登记成功",
  "data": {
    "patientId": "P20260010"
  },
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

---

#### 4.4.10 危急值列表

**GET** `/api/report/critical-values`

**描述：** 查询危急值记录列表。

**请求参数（Query String）：**

| 参数名   | 类型    | 必须 | 说明                    |
|---------|---------|------|-------------------------|
| pageNum | Integer | 否   | 页码，默认1             |
| pageSize| Integer | 否   | 每页数量，默认10        |
| status  | String  | 否   | 处理状态：pending/handled |

**成功响应示例：**

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "total": 5,
    "pageNum": 1,
    "pageSize": 10,
    "pages": 1,
    "list": [
      {
        "id": 1,
        "reportNo": "RPT202604060005",
        "patientName": "赵六",
        "itemCode": "K",
        "itemName": "血钾",
        "result": "6.8",
        "unit": "mmol/L",
        "criticalRange": ">6.5 或 <3.0",
        "status": "pending",
        "statusName": "待处理",
        "reporterName": "赵检测",
        "reportTime": "2026-04-06T10:30:00.000Z"
      }
    ]
  },
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

---

#### 4.4.11 危急值处理

**PUT** `/api/report/critical-values/{id}/handle`

**描述：** 对危急值进行处理，记录通知临床科室的结果。

**路径参数：**

| 参数名 | 类型 | 说明        |
|-------|------|-------------|
| id    | Long | 危急值记录ID |

**请求参数：**

| 参数名       | 类型   | 必须 | 说明                       |
|-------------|--------|------|----------------------------|
| handlerName | String | 是   | 处理人姓名                 |
| notifiedPerson| String| 是  | 被通知人（临床医生姓名）   |
| notifiedPhone| String | 是   | 被通知人联系电话           |
| handleResult| String | 是   | 处理结果描述               |
| handleTime  | String | 否   | 处理时间（默认当前时间）   |

**请求示例：**

```json
{
  "handlerName": "孙审核",
  "notifiedPerson": "周医生",
  "notifiedPhone": "13800000010",
  "handleResult": "已电话通知周医生，患者血钾偏高，建议立即复查并处理",
  "handleTime": "2026-04-06T10:45:00.000Z"
}
```

**成功响应示例：**

```json
{
  "code": 200,
  "message": "危急值处理成功",
  "data": null,
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

---

### 4.5 设备服务（/api/equipment）

#### 4.5.1 设备列表

**GET** `/api/equipment/equipments`

**描述：** 分页查询设备列表。

**请求参数（Query String）：**

| 参数名      | 类型    | 必须 | 说明              |
|------------|---------|------|-------------------|
| pageNum    | Integer | 否   | 页码，默认1       |
| pageSize   | Integer | 否   | 每页数量，默认10  |
| keyword    | String  | 否   | 设备名称关键词    |
| status     | String  | 否   | 设备状态：online/offline/maintenance/fault |
| categoryId | Long    | 否   | 设备分类ID        |

**成功响应示例：**

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "total": 25,
    "pageNum": 1,
    "pageSize": 10,
    "pages": 3,
    "list": [
      {
        "equipmentId": 1,
        "equipmentName": "全自动生化分析仪",
        "equipmentCode": "EQ-BIO-001",
        "brand": "罗氏",
        "model": "cobas 8000",
        "categoryName": "生化分析仪",
        "status": "online",
        "statusName": "在线",
        "location": "生化室A区",
        "installDate": "2025-06-15",
        "lastCalibrateDate": "2026-03-20",
        "nextCalibrateDate": "2026-06-20"
      }
    ]
  },
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

---

#### 4.5.2 新增设备

**POST** `/api/equipment/equipments`

**描述：** 登记新设备信息。

**请求参数：**

| 参数名         | 类型   | 必须 | 说明             |
|---------------|--------|------|------------------|
| equipmentName | String | 是   | 设备名称         |
| equipmentCode | String | 是   | 设备编号（唯一） |
| brand         | String | 否   | 品牌             |
| model         | String | 否   | 型号             |
| categoryId    | Long   | 是   | 设备分类ID       |
| location      | String | 否   | 安装位置         |
| installDate   | String | 否   | 安装日期         |
| manufacturer  | String | 否   | 生产厂商         |
| serialNumber  | String | 否   | 序列号           |
| remark        | String | 否   | 备注             |

**请求示例：**

```json
{
  "equipmentName": "全自动血细胞分析仪",
  "equipmentCode": "EQ-BCA-002",
  "brand": "希森美康",
  "model": "XN-9000",
  "categoryId": 2,
  "location": "血液室B区",
  "installDate": "2026-01-10",
  "manufacturer": "希森美康株式会社",
  "serialNumber": "SN20260110001",
  "remark": "五分类血细胞分析仪"
}
```

**成功响应示例：**

```json
{
  "code": 201,
  "message": "设备新增成功",
  "data": {
    "equipmentId": 2
  },
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

---

#### 4.5.3 修改设备

**PUT** `/api/equipment/equipments/{id}`

**描述：** 修改指定设备信息。

**路径参数：**

| 参数名 | 类型 | 说明     |
|-------|------|----------|
| id    | Long | 设备ID   |

**请求参数：** 与新增设备相同，所有字段均为可选。

**请求示例：**

```json
{
  "location": "血液室C区（已搬迁）",
  "remark": "五分类血细胞分析仪，已搬迁至C区"
}
```

**成功响应示例：**

```json
{
  "code": 200,
  "message": "设备修改成功",
  "data": null,
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

---

#### 4.5.4 删除设备

**DELETE** `/api/equipment/equipments/{id}`

**描述：** 删除指定设备（逻辑删除）。

**路径参数：**

| 参数名 | 类型 | 说明     |
|-------|------|----------|
| id    | Long | 设备ID   |

**成功响应示例：**

```json
{
  "code": 200,
  "message": "设备删除成功",
  "data": null,
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

---

#### 4.5.5 设备状态

**GET** `/api/equipment/equipments/{id}/status`

**描述：** 获取指定设备的实时运行状态。

**路径参数：**

| 参数名 | 类型 | 说明     |
|-------|------|----------|
| id    | Long | 设备ID   |

**成功响应示例：**

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "equipmentId": 1,
    "equipmentName": "全自动生化分析仪",
    "status": "online",
    "statusName": "在线",
    "connectionStatus": "connected",
    "connectionStatusName": "已连接",
    "currentWorkload": {
      "todayCount": 85,
      "capacity": 200,
      "utilizationRate": 42.5
    },
    "lastHeartbeat": "2026-04-06T07:58:30.000Z",
    "temperature": 25.3,
    "humidity": 45.2
  },
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

---

#### 4.5.6 校准记录

**POST** `/api/equipment/equipments/{id}/calibrate`

**描述：** 记录设备校准信息。

**路径参数：**

| 参数名 | 类型 | 说明     |
|-------|------|----------|
| id    | Long | 设备ID   |

**请求参数：**

| 参数名        | 类型    | 必须 | 说明               |
|--------------|--------|------|--------------------|
| calibrateDate| String | 是   | 校准日期           |
| calibrateType| String | 是   | 校准类型：daily/monthly/yearly |
| result       | String | 是   | 校准结果：pass/fail |
| operatorName | String | 是   | 校准操作人         |
| remark       | String | 否   | 备注               |

**请求示例：**

```json
{
  "calibrateDate": "2026-04-06",
  "calibrateType": "daily",
  "result": "pass",
  "operatorName": "李检验",
  "remark": "日常校准，各项指标正常"
}
```

**成功响应示例：**

```json
{
  "code": 200,
  "message": "校准记录添加成功",
  "data": {
    "recordId": 10
  },
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

---

#### 4.5.7 维护记录

**GET** `/api/equipment/equipments/{id}/maintain`

**描述：** 查询指定设备的维护保养记录。

**路径参数：**

| 参数名 | 类型 | 说明     |
|-------|------|----------|
| id    | Long | 设备ID   |

**请求参数（Query String）：**

| 参数名   | 类型    | 必须 | 说明       |
|---------|---------|------|------------|
| pageNum | Integer | 否   | 页码，默认1|
| pageSize| Integer | 否   | 每页数量   |

**成功响应示例：**

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "total": 12,
    "pageNum": 1,
    "pageSize": 10,
    "pages": 2,
    "list": [
      {
        "recordId": 1,
        "maintainType": "routine",
        "maintainTypeName": "日常保养",
        "content": "清洗探针，更换清洗液，检查光源",
        "operatorName": "李检验",
        "maintainDate": "2026-04-05",
        "nextMaintainDate": "2026-04-12",
        "cost": 350.00,
        "remark": "正常保养"
      },
      {
        "recordId": 2,
        "maintainType": "repair",
        "maintainTypeName": "故障维修",
        "content": "更换加样针密封圈",
        "operatorName": "厂商工程师",
        "maintainDate": "2026-03-15",
        "nextMaintainDate": null,
        "cost": 2800.00,
        "remark": "加样针漏液，更换密封圈后恢复正常"
      }
    ]
  },
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

---

#### 4.5.8 联机测试

**POST** `/api/equipment/equipments/{id}/communicate`

**描述：** 测试与设备的通信连接，发送测试指令并返回设备响应。

**路径参数：**

| 参数名 | 类型 | 说明     |
|-------|------|----------|
| id    | Long | 设备ID   |

**请求参数：**

| 参数名     | 类型   | 必须 | 说明               |
|-----------|--------|------|--------------------|
| testType  | String | 否   | 测试类型：query_status/sample_test/default（默认query_status）|

**请求示例：**

```json
{
  "testType": "query_status"
}
```

**成功响应示例：**

```json
{
  "code": 200,
  "message": "联机测试成功",
  "data": {
    "equipmentId": 1,
    "equipmentName": "全自动生化分析仪",
    "connected": true,
    "responseTime": 125,
    "responseTimeUnit": "ms",
    "deviceStatus": {
      "ready": true,
      "sampleCount": 3,
      "reagentStatus": "normal",
      "errorCodes": []
    },
    "testTime": "2026-04-06T08:00:00.000Z"
  },
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

**失败响应示例（设备离线）：**

```json
{
  "code": 30001,
  "message": "设备离线，无法建立通信连接",
  "data": {
    "equipmentId": 1,
    "connected": false,
    "error": "Connection refused: 192.168.1.100:8080"
  },
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

---

### 4.6 HL7 服务（/api/hl7）

#### 4.6.1 发送HL7消息

**POST** `/api/hl7/messages/send`

**描述：** 向外部系统（如HIS系统）发送 HL7 标准消息。

**请求参数：**

| 参数名     | 类型   | 必须 | 说明                                    |
|-----------|--------|------|-----------------------------------------|
| messageType| String | 是   | HL7消息类型：ADT^A01/ORM^O01/ORU^R01等 |
| receiver  | String | 是   | 接收系统标识                            |
| content   | Object | 是   | 消息内容（JSON格式，将转换为HL7 v2.x）  |
| priority  | String | 否   | 优先级：normal/urgent，默认normal       |

**请求示例：**

```json
{
  "messageType": "ORU^R01",
  "receiver": "HIS_SYSTEM",
  "content": {
    "patientId": "P20260001",
    "patientName": "张三",
    "reportNo": "RPT202604060001",
    "specimenBarcode": "SP202604060001",
    "results": [
      {
        "itemCode": "WBC",
        "itemName": "白细胞计数",
        "result": "6.5",
        "unit": "10^9/L",
        "referenceRange": "3.5-9.5",
        "isAbnormal": false
      }
    ],
    "conclusion": "血常规结果正常"
  },
  "priority": "normal"
}
```

**成功响应示例：**

```json
{
  "code": 200,
  "message": "消息发送成功",
  "data": {
    "messageId": "MSG20260406000001",
    "messageType": "ORU^R01",
    "status": "sent",
    "ackCode": "AA",
    "ackMessage": "接收成功",
    "sendTime": "2026-04-06T08:00:00.000Z"
  },
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

---

#### 4.6.2 消息日志

**GET** `/api/hl7/messages`

**描述：** 查询 HL7 消息发送/接收日志。

**请求参数（Query String）：**

| 参数名       | 类型    | 必须 | 说明                                |
|-------------|---------|------|-------------------------------------|
| pageNum     | Integer | 否   | 页码，默认1                         |
| pageSize    | Integer | 否   | 每页数量，默认10                    |
| messageType | String  | 否   | 消息类型                            |
| direction   | String  | 否   | 方向：send/receive                  |
| status      | String  | 否   | 状态：success/fail/pending          |
| startDate   | String  | 否   | 开始日期                            |
| endDate     | String  | 否   | 结束日期                            |

**成功响应示例：**

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "total": 156,
    "pageNum": 1,
    "pageSize": 10,
    "pages": 16,
    "list": [
      {
        "messageId": "MSG20260406000001",
        "messageType": "ORU^R01",
        "direction": "send",
        "receiver": "HIS_SYSTEM",
        "status": "success",
        "ackCode": "AA",
        "sendTime": "2026-04-06T08:00:00.000Z",
        "responseTime": "2026-04-06T08:00:01.200Z"
      }
    ]
  },
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

---

#### 4.6.3 接口配置

**GET** `/api/hl7/config`

**描述：** 获取 HL7 接口通信配置列表。

**请求参数：** 无

**成功响应示例：**

```json
{
  "code": 200,
  "message": "操作成功",
  "data": [
    {
      "configId": 1,
      "systemName": "HIS系统",
      "systemCode": "HIS_SYSTEM",
      "protocol": "MLLP",
      "host": "192.168.1.200",
      "port": 2575,
      "sendingApplication": "LIS",
      "sendingFacility": "LAB",
      "receivingApplication": "HIS",
      "receivingFacility": "HOSPITAL",
      "enabled": true,
      "heartbeatInterval": 60,
      "timeout": 30000,
      "retryCount": 3
    },
    {
      "configId": 2,
      "systemName": "PACS系统",
      "systemCode": "PACS_SYSTEM",
      "protocol": "HTTP",
      "host": "192.168.1.201",
      "port": 8080,
      "sendingApplication": "LIS",
      "sendingFacility": "LAB",
      "receivingApplication": "PACS",
      "receivingFacility": "RADIOLOGY",
      "enabled": true,
      "heartbeatInterval": 120,
      "timeout": 60000,
      "retryCount": 3
    }
  ],
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

---

#### 4.6.4 修改配置

**PUT** `/api/hl7/config/{id}`

**描述：** 修改指定 HL7 接口配置。

**路径参数：**

| 参数名   | 类型 | 说明       |
|---------|------|------------|
| id      | Long | 配置ID     |

**请求参数：**

| 参数名               | 类型    | 必须 | 说明                     |
|---------------------|--------|------|--------------------------|
| systemName          | String | 否   | 系统名称                 |
| protocol            | String | 否   | 通信协议                 |
| host                | String | 否   | 主机地址                 |
| port                | Integer| 否   | 端口号                   |
| sendingApplication  | String | 否   | 发送应用标识             |
| sendingFacility     | String | 否   | 发送设施标识             |
| receivingApplication| String | 否   | 接收应用标识             |
| receivingFacility   | String | 否   | 接收设施标识             |
| enabled             | Boolean| 否   | 是否启用                 |
| heartbeatInterval   | Integer| 否   | 心跳间隔（秒）           |
| timeout             | Integer| 否   | 超时时间（毫秒）         |
| retryCount          | Integer| 否   | 重试次数                 |

**请求示例：**

```json
{
  "host": "192.168.1.210",
  "port": 2575,
  "timeout": 45000,
  "retryCount": 5
}
```

**成功响应示例：**

```json
{
  "code": 200,
  "message": "配置修改成功",
  "data": null,
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

---

### 4.7 统计服务（/api/statistics）

#### 4.7.1 工作量统计

**GET** `/api/statistics/workload`

**描述：** 统计各操作人员的工作量数据。

**请求参数（Query String）：**

| 参数名     | 类型   | 必须 | 说明                      |
|-----------|--------|------|---------------------------|
| startDate | String | 否   | 统计开始日期（yyyy-MM-dd）|
| endDate   | String | 否   | 统计结束日期（yyyy-MM-dd）|
| deptId    | Long   | 否   | 部门ID                    |
| groupBy   | String | 否   | 分组维度：person/dept/day |

**成功响应示例：**

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "totalSpecimens": 1520,
    "totalReports": 1480,
    "totalAudits": 1450,
    "details": [
      {
        "operatorId": 6,
        "operatorName": "李检验",
        "deptName": "检验科-生化组",
        "specimenCount": 320,
        "reportCount": 310,
        "auditCount": 0
      },
      {
        "operatorId": 7,
        "operatorName": "赵检测",
        "deptName": "检验科-免疫组",
        "specimenCount": 280,
        "reportCount": 275,
        "auditCount": 0
      },
      {
        "operatorId": 8,
        "operatorName": "孙审核",
        "deptName": "检验科",
        "specimenCount": 0,
        "reportCount": 0,
        "auditCount": 1450
      }
    ]
  },
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

---

#### 4.7.2 标本统计

**GET** `/api/statistics/specimen`

**描述：** 获取标本相关的统计数据。

**请求参数（Query String）：**

| 参数名     | 类型   | 必须 | 说明                      |
|-----------|--------|------|---------------------------|
| startDate | String | 否   | 统计开始日期              |
| endDate   | String | 否   | 统计结束日期              |
| groupBy   | String | 否   | 分组维度：type/status/source/day |

**成功响应示例：**

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "total": 1520,
    "averageDaily": 253,
    "peakDay": {
      "date": "2026-04-03",
      "count": 310
    },
    "bySource": [
      { "source": "outpatient", "sourceName": "门诊", "count": 860, "percentage": 56.6 },
      { "source": "inpatient", "sourceName": "住院", "count": 420, "percentage": 27.6 },
      { "source": "emergency", "sourceName": "急诊", "count": 150, "percentage": 9.9 },
      { "source": "health_checkup", "sourceName": "体检", "count": 90, "percentage": 5.9 }
    ],
    "byType": [
      { "type": "blood", "typeName": "血液", "count": 860 },
      { "type": "urine", "typeName": "尿液", "count": 380 },
      { "type": "stool", "typeName": "粪便", "count": 120 },
      { "type": "other", "typeName": "其他", "count": 160 }
    ]
  },
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

---

#### 4.7.3 设备统计

**GET** `/api/statistics/equipment`

**描述：** 获取设备使用统计和运行状况数据。

**请求参数（Query String）：**

| 参数名     | 类型   | 必须 | 说明       |
|-----------|--------|------|------------|
| startDate | String | 否   | 开始日期   |
| endDate   | String | 否   | 结束日期   |

**成功响应示例：**

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "totalEquipments": 25,
    "onlineCount": 20,
    "offlineCount": 2,
    "maintenanceCount": 2,
    "faultCount": 1,
    "averageUtilizationRate": 68.5,
    "topEquipments": [
      {
        "equipmentId": 1,
        "equipmentName": "全自动生化分析仪",
        "totalTests": 3200,
        "utilizationRate": 85.2,
        "faultCount": 0,
        "maintainCount": 3
      },
      {
        "equipmentId": 2,
        "equipmentName": "全自动血细胞分析仪",
        "totalTests": 2800,
        "utilizationRate": 76.5,
        "faultCount": 1,
        "maintainCount": 2
      }
    ]
  },
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

---

#### 4.7.4 报告统计

**GET** `/api/statistics/report`

**描述：** 获取检验报告相关的统计数据。

**请求参数（Query String）：**

| 参数名     | 类型   | 必须 | 说明       |
|-----------|--------|------|------------|
| startDate | String | 否   | 开始日期   |
| endDate   | String | 否   | 结束日期   |
| groupBy   | String | 否   | 分组维度   |

**成功响应示例：**

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "totalReports": 1480,
    "publishedCount": 1420,
    "recalledCount": 15,
    "pendingAuditCount": 45,
    "averageTurnaroundTime": 145,
    "turnaroundTimeUnit": "分钟",
    "abnormalRate": 18.5,
    "criticalValueCount": 8,
    "criticalValueHandledCount": 8,
    "criticalValueHandleRate": 100.0,
    "byType": [
      { "type": "blood_routine", "typeName": "血常规", "count": 520 },
      { "type": "liver_function", "typeName": "肝功能", "count": 310 },
      { "type": "kidney_function", "typeName": "肾功能", "count": 280 },
      { "type": "blood_lipid", "typeName": "血脂", "count": 200 },
      { "type": "other", "typeName": "其他", "count": 170 }
    ]
  },
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

---

### 4.8 AI 服务（/api/ai）

#### 4.8.1 AI诊断

**POST** `/api/ai/diagnosis`

**描述：** 基于检验结果数据，调用 AI 模型进行辅助诊断分析。

**请求参数：**

| 参数名      | 类型   | 必须 | 说明                     |
|------------|--------|------|--------------------------|
| reportId   | Long   | 是   | 关联的报告ID             |
| patientInfo| Object | 否   | 患者基本信息             |
| results    | Array  | 是   | 检验结果数据             |

**请求示例：**

```json
{
  "reportId": 1,
  "patientInfo": {
    "patientName": "张三",
    "gender": "男",
    "age": 45
  },
  "results": [
    {
      "itemCode": "ALT",
      "itemName": "谷丙转氨酶",
      "result": "85",
      "unit": "U/L",
      "referenceRange": "9-50",
      "isAbnormal": true
    },
    {
      "itemCode": "AST",
      "itemName": "谷草转氨酶",
      "result": "72",
      "unit": "U/L",
      "referenceRange": "15-40",
      "isAbnormal": true
    },
    {
      "itemCode": "GGT",
      "itemName": "谷氨酰转肽酶",
      "result": "95",
      "unit": "U/L",
      "referenceRange": "10-60",
      "isAbnormal": true
    },
    {
      "itemCode": "TBIL",
      "itemName": "总胆红素",
      "result": "28.5",
      "unit": "umol/L",
      "referenceRange": "5.1-22.2",
      "isAbnormal": true
    }
  ]
}
```

**成功响应示例：**

```json
{
  "code": 200,
  "message": "AI诊断完成",
  "data": {
    "diagnosisId": 1,
    "reportId": 1,
    "analysisResult": {
      "summary": "肝功能指标明显异常，ALT、AST、GGT均升高，提示肝细胞损伤",
      "possibleDiseases": [
        {
          "diseaseName": "病毒性肝炎",
          "probability": 0.35,
          "description": "转氨酶升高常见于病毒性肝炎，建议结合乙肝五项、丙肝抗体等进一步检查"
        },
        {
          "diseaseName": "药物性肝损伤",
          "probability": 0.28,
          "description": "需询问患者近期用药史，某些药物可导致肝损伤"
        },
        {
          "diseaseName": "脂肪肝",
          "probability": 0.22,
          "description": "GGT升高伴转氨酶升高，需结合腹部B超检查排除脂肪肝"
        }
      ],
      "suggestions": [
        "建议完善乙肝五项、丙肝抗体检查",
        "建议行腹部超声检查",
        "建议询问患者用药史及饮酒史",
        "建议复查肝功能，动态观察指标变化"
      ],
      "confidence": 0.78
    },
    "createTime": "2026-04-06T08:00:00.000Z"
  },
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

---

#### 4.8.2 诊断记录

**GET** `/api/ai/diagnosis/records`

**描述：** 分页查询 AI 诊断记录。

**请求参数（Query String）：**

| 参数名     | 类型    | 必须 | 说明                          |
|-----------|---------|------|-------------------------------|
| pageNum   | Integer | 否   | 页码，默认1                   |
| pageSize  | Integer | 否   | 每页数量，默认10              |
| auditStatus| String | 否   | 审核状态：pending/approved/rejected |
| startDate | String  | 否   | 开始日期                      |
| endDate   | String  | 否   | 结束日期                      |

**成功响应示例：**

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "total": 45,
    "pageNum": 1,
    "pageSize": 10,
    "pages": 5,
    "list": [
      {
        "diagnosisId": 1,
        "reportId": 1,
        "reportNo": "RPT202604060001",
        "patientName": "张三",
        "summary": "肝功能指标明显异常，ALT、AST、GGT均升高",
        "confidence": 0.78,
        "auditStatus": "pending",
        "auditStatusName": "待审核",
        "createTime": "2026-04-06T08:00:00.000Z"
      }
    ]
  },
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

---

#### 4.8.3 审核诊断

**PUT** `/api/ai/diagnosis/records/{id}/audit`

**描述：** 对 AI 诊断结果进行人工审核。

**路径参数：**

| 参数名 | 类型 | 说明        |
|-------|------|-------------|
| id    | Long | 诊断记录ID  |

**请求参数：**

| 参数名   | 类型    | 必须 | 说明                                  |
|---------|--------|------|---------------------------------------|
| passed  | Boolean| 是   | 是否通过：true-采纳，false-不采纳     |
| opinion | String | 否   | 审核意见                              |
| correctDiagnosis| String| 否 | 审核人给出的正确诊断（不采纳时建议填写）|

**请求示例：**

```json
{
  "passed": true,
  "opinion": "AI诊断分析合理，建议采纳，已安排进一步检查"
}
```

**成功响应示例：**

```json
{
  "code": 200,
  "message": "诊断审核完成",
  "data": null,
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

---

#### 4.8.4 诊断规则

**GET** `/api/ai/rules`

**描述：** 查询 AI 诊断规则列表。

**请求参数（Query String）：**

| 参数名   | 类型    | 必须 | 说明       |
|---------|---------|------|------------|
| pageNum | Integer | 否   | 页码，默认1|
| pageSize| Integer | 否   | 每页数量   |
| enabled | Boolean | 否   | 是否启用   |
| category| String  | 否   | 规则分类   |

**成功响应示例：**

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "total": 18,
    "pageNum": 1,
    "pageSize": 10,
    "pages": 2,
    "list": [
      {
        "ruleId": 1,
        "ruleName": "肝功能异常判断规则",
        "category": "liver_function",
        "categoryName": "肝功能",
        "description": "当ALT、AST同时升高超过参考值上限2倍时，判定为肝功能明显异常",
        "conditions": {
          "items": ["ALT", "AST"],
          "operator": "AND",
          "threshold": "2x_upper_limit"
        },
        "enabled": true,
        "priority": 1,
        "updateTime": "2026-03-15T10:00:00.000Z"
      },
      {
        "ruleId": 2,
        "ruleName": "肾功能异常判断规则",
        "category": "kidney_function",
        "categoryName": "肾功能",
        "description": "当肌酐超过参考值上限时，提示肾功能异常",
        "conditions": {
          "items": ["CREA"],
          "operator": "GT",
          "threshold": "upper_limit"
        },
        "enabled": true,
        "priority": 2,
        "updateTime": "2026-03-10T14:00:00.000Z"
      }
    ]
  },
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

---

#### 4.8.5 新增规则

**POST** `/api/ai/rules`

**描述：** 创建新的 AI 诊断规则。

**请求参数：**

| 参数名       | 类型    | 必须 | 说明                     |
|-------------|--------|------|--------------------------|
| ruleName    | String | 是   | 规则名称                 |
| category    | String | 是   | 规则分类编码             |
| description | String | 否   | 规则描述                 |
| conditions  | Object | 是   | 规则条件（JSON结构）     |
| priority    | Integer| 否   | 优先级（数字越小优先级越高）|
| enabled     | Boolean| 否   | 是否启用，默认true       |

**请求示例：**

```json
{
  "ruleName": "血糖异常判断规则",
  "category": "blood_glucose",
  "description": "空腹血糖超过7.0mmol/L时，提示血糖异常，需考虑糖尿病可能",
  "conditions": {
    "items": ["GLU"],
    "operator": "GT",
    "threshold": "7.0",
    "unit": "mmol/L"
  },
  "priority": 5,
  "enabled": true
}
```

**成功响应示例：**

```json
{
  "code": 201,
  "message": "规则新增成功",
  "data": {
    "ruleId": 19
  },
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

---

#### 4.8.6 修改规则

**PUT** `/api/ai/rules/{id}`

**描述：** 修改指定的 AI 诊断规则。

**路径参数：**

| 参数名 | 类型 | 说明     |
|-------|------|----------|
| id    | Long | 规则ID   |

**请求参数：** 与新增规则相同，所有字段均为可选。

**请求示例：**

```json
{
  "ruleName": "血糖异常判断规则（已更新）",
  "description": "空腹血糖超过7.0mmol/L或随机血糖超过11.1mmol/L时，提示血糖异常",
  "conditions": {
    "items": ["GLU"],
    "operator": "OR",
    "rules": [
      { "item": "GLU", "type": "fasting", "operator": "GT", "threshold": "7.0" },
      { "item": "GLU", "type": "random", "operator": "GT", "threshold": "11.1" }
    ],
    "unit": "mmol/L"
  },
  "enabled": true
}
```

**成功响应示例：**

```json
{
  "code": 200,
  "message": "规则修改成功",
  "data": null,
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

---

## 5 数据模型

### 5.1 通用响应体（ApiResponse）

所有接口响应的统一包装结构：

```json
{
  "code": 200,
  "message": "操作成功",
  "data": null,
  "timestamp": "2026-04-06T08:00:00.000Z"
}
```

| 字段       | 类型     | 说明               |
|-----------|---------|--------------------|
| code      | Integer | 业务状态码         |
| message   | String  | 响应消息           |
| data      | Object  | 业务数据（泛型）   |
| timestamp | String  | 响应时间戳         |

### 5.2 分页请求参数（PageRequest）

| 字段     | 类型    | 必须 | 默认值 | 说明            |
|---------|--------|------|-------|-----------------|
| pageNum | Integer | 否   | 1     | 当前页码        |
| pageSize| Integer | 否   | 10    | 每页记录数      |

### 5.3 分页响应体（PageResult）

```json
{
  "total": 100,
  "pageNum": 1,
  "pageSize": 10,
  "pages": 10,
  "list": []
}
```

| 字段     | 类型     | 说明       |
|---------|---------|------------|
| total   | Long    | 总记录数   |
| pageNum | Integer | 当前页码   |
| pageSize| Integer | 每页记录数 |
| pages   | Integer | 总页数     |
| list    | Array   | 数据列表   |

### 5.4 用户模型（User）

```json
{
  "userId": 1,
  "username": "admin",
  "realName": "系统管理员",
  "avatar": "https://example.com/avatar/admin.png",
  "email": "admin@lis.com",
  "phone": "13800138000",
  "status": 1,
  "deptId": 1,
  "deptName": "信息科",
  "roles": [],
  "permissions": [],
  "createTime": "2026-01-01T00:00:00.000Z",
  "updateTime": "2026-04-01T10:30:00.000Z"
}
```

### 5.5 角色模型（Role）

```json
{
  "roleId": 1,
  "roleName": "超级管理员",
  "roleCode": "SUPER_ADMIN",
  "status": 1,
  "remark": "拥有系统所有权限",
  "menuIds": [],
  "createTime": "2026-01-01T00:00:00.000Z"
}
```

### 5.6 菜单模型（Menu）

```json
{
  "menuId": 1,
  "menuName": "系统管理",
  "parentId": 0,
  "path": "/system",
  "component": null,
  "icon": "setting",
  "sort": 1,
  "permission": null,
  "type": 0,
  "children": []
}
```

| 字段       | 类型     | 说明                                          |
|-----------|---------|-----------------------------------------------|
| menuId    | Long    | 菜单ID                                        |
| menuName  | String  | 菜单名称                                      |
| parentId  | Long    | 父菜单ID（0表示顶级）                         |
| path      | String  | 路由路径                                      |
| component | String  | 前端组件路径                                  |
| icon      | String  | 菜单图标                                      |
| sort      | Integer | 排序号                                        |
| permission| String  | 权限标识（按钮级权限）                        |
| type      | Integer | 类型：0-目录，1-菜单，2-按钮                  |
| children  | Array   | 子菜单列表                                    |

### 5.7 标本模型（Specimen）

```json
{
  "specimenId": 1,
  "barcode": "SP202604060001",
  "patientId": "P20260001",
  "patientName": "张三",
  "specimenType": "blood",
  "specimenTypeName": "血液",
  "source": "outpatient",
  "sourceName": "门诊",
  "status": "received",
  "statusName": "已签收",
  "testItems": [],
  "registerTime": "2026-04-06T08:30:00.000Z",
  "receiveTime": "2026-04-06T09:00:00.000Z",
  "storeTime": null,
  "completeTime": null,
  "operatorName": "李检验",
  "remark": "空腹采血"
}
```

**标本状态流转：**

```
registered（已登记） → received（已签收） → stored（已入库） → testing（检测中） → completed（已完成）
```

### 5.8 报告模型（Report）

```json
{
  "reportId": 1,
  "reportNo": "RPT202604060001",
  "patientId": "P20260001",
  "patientName": "张三",
  "specimenBarcode": "SP202604060001",
  "reportType": "blood_routine",
  "reportTypeName": "血常规报告",
  "status": "reviewed",
  "statusName": "已审核",
  "results": [],
  "conclusion": "血红蛋白偏低",
  "createBy": "赵检测",
  "createTime": "2026-04-06T12:00:00.000Z",
  "auditorName": "孙审核",
  "auditTime": "2026-04-06T14:00:00.000Z",
  "publishTime": null
}
```

**报告状态流转：**

```
draft（草稿） → reviewed（已审核） → published（已发布） → recalled（已回收） → draft（草稿）
```

### 5.9 检验结果项模型（ResultItem）

```json
{
  "itemCode": "WBC",
  "itemName": "白细胞计数",
  "result": "6.5",
  "unit": "10^9/L",
  "referenceRange": "3.5-9.5",
  "isAbnormal": false,
  "arrow": ""
}
```

| 字段           | 类型    | 说明                               |
|---------------|--------|------------------------------------|
| itemCode      | String | 检验项目编码                       |
| itemName      | String | 检验项目名称                       |
| result        | String | 检验结果值                         |
| unit          | String | 单位                               |
| referenceRange| String | 参考范围                           |
| isAbnormal    | Boolean| 是否异常                           |
| arrow         | String | 异常标识箭头：↑偏高，↓偏低，空字符串正常 |

### 5.10 设备模型（Equipment）

```json
{
  "equipmentId": 1,
  "equipmentName": "全自动生化分析仪",
  "equipmentCode": "EQ-BIO-001",
  "brand": "罗氏",
  "model": "cobas 8000",
  "categoryName": "生化分析仪",
  "status": "online",
  "statusName": "在线",
  "location": "生化室A区",
  "installDate": "2025-06-15",
  "lastCalibrateDate": "2026-03-20",
  "nextCalibrateDate": "2026-06-20"
}
```

### 5.11 患者模型（Patient）

```json
{
  "patientId": "P20260001",
  "patientName": "张三",
  "gender": "男",
  "age": 45,
  "birthDate": "1981-05-10",
  "idCard": "320102198105****",
  "phone": "13800138001",
  "address": "江苏省南京市鼓楼区XX路XX号",
  "source": "outpatient",
  "sourceName": "门诊",
  "medicalNo": "MZ20260406001",
  "specimenCount": 3,
  "reportCount": 2,
  "lastVisitTime": "2026-04-06T08:30:00.000Z"
}
```

### 5.12 危急值模型（CriticalValue）

```json
{
  "id": 1,
  "reportNo": "RPT202604060005",
  "patientName": "赵六",
  "itemCode": "K",
  "itemName": "血钾",
  "result": "6.8",
  "unit": "mmol/L",
  "criticalRange": ">6.5 或 <3.0",
  "status": "pending",
  "statusName": "待处理",
  "reporterName": "赵检测",
  "reportTime": "2026-04-06T10:30:00.000Z",
  "handlerName": null,
  "notifiedPerson": null,
  "handleTime": null,
  "handleResult": null
}
```

### 5.13 HL7消息模型（Hl7Message）

```json
{
  "messageId": "MSG20260406000001",
  "messageType": "ORU^R01",
  "direction": "send",
  "receiver": "HIS_SYSTEM",
  "status": "success",
  "ackCode": "AA",
  "sendTime": "2026-04-06T08:00:00.000Z",
  "responseTime": "2026-04-06T08:00:01.200Z"
}
```

### 5.14 AI诊断模型（AiDiagnosis）

```json
{
  "diagnosisId": 1,
  "reportId": 1,
  "analysisResult": {
    "summary": "分析摘要",
    "possibleDiseases": [],
    "suggestions": [],
    "confidence": 0.78
  },
  "auditStatus": "pending",
  "createTime": "2026-04-06T08:00:00.000Z"
}
```

### 5.15 AI诊断规则模型（AiRule）

```json
{
  "ruleId": 1,
  "ruleName": "肝功能异常判断规则",
  "category": "liver_function",
  "categoryName": "肝功能",
  "description": "规则描述",
  "conditions": {},
  "enabled": true,
  "priority": 1,
  "updateTime": "2026-03-15T10:00:00.000Z"
}
```

---

## 附录

### A. 枚举值定义

**A.1 标本来源（SpecimenSource）**

| 值              | 说明   |
|-----------------|--------|
| outpatient      | 门诊   |
| inpatient       | 住院   |
| emergency       | 急诊   |
| health_checkup  | 体检   |

**A.2 标本状态（SpecimenStatus）**

| 值        | 说明   |
|-----------|--------|
| registered| 已登记 |
| received  | 已签收 |
| stored    | 已入库 |
| testing   | 检测中 |
| completed | 已完成 |

**A.3 报告状态（ReportStatus）**

| 值       | 说明   |
|----------|--------|
| draft    | 草稿   |
| reviewed | 已审核 |
| published| 已发布 |
| recalled | 已回收 |

**A.4 设备状态（EquipmentStatus）**

| 值          | 说明   |
|-------------|--------|
| online      | 在线   |
| offline     | 离线   |
| maintenance | 维护中 |
| fault       | 故障   |

**A.5 菜单类型（MenuType）**

| 值 | 说明     |
|----|----------|
| 0  | 目录     |
| 1  | 菜单     |
| 2  | 按钮/权限|

### B. 接口变更记录

| 版本号 | 日期       | 变更内容     | 变更人 |
|-------|-----------|-------------|--------|
| V1.0.0| 2026-04-06| 初始版本     | -      |
