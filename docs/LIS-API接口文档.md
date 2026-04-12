# LIS实验室信息管理系统 - API接口文档

> 文档生成时间：2026-04-12（最新更新）  
> 网关地址：http://localhost:8080  
> 认证方式：Bearer Token (JWT)  
> 请求头：Authorization: Bearer {accessToken}  
> 端点总数：179

---

## 目录

- [1. 认证模块 (auth) - 7个端点](#1-认证模块-auth)
- [2. 用户模块 (user) - 62个端点](#2-用户模块-user)
- [3. 标本模块 (specimen) - 17个端点](#3-标本模块-specimen)
- [4. 报告模块 (report) - 32个端点](#4-报告模块-report)
- [5. 设备模块 (equipment) - 27个端点](#5-设备模块-equipment)
- [6. HL7模块 (hl7) - 26个端点](#6-hl7模块-hl7)
- [7. 统计模块 (statistics) - 25个端点](#7-统计模块-statistics)
- [8. AI模块 (ai) - 16个端点](#8-ai模块-ai)
- [9. 通用错误码](#9-通用错误码)

---

## 1. 认证模块 (auth)

> 请求前缀：`/auth` | 白名单接口（无需认证）：`/auth/login`、`/auth/captcha`、`/auth/refresh`

### 1.1 用户登录

- **接口名称**：用户登录
- **路径**：`POST /auth/login`
- **是否需要认证**：否

**请求参数**：

| 字段名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| username | String | 是 | 用户名（2-50个字符） |
| password | String | 是 | 密码（6-100个字符） |
| code | String | 否 | 验证码 |
| uuid | String | 否 | 验证码UUID |

**防暴力破解机制**：同一用户名连续登录失败5次后，账号将被锁定15分钟，锁定期间无法登录。

**请求示例**：
```json
{
  "username": "admin",
  "password": "admin123"
}
```

**响应格式**：

| 字段名 | 类型 | 说明 |
|--------|------|------|
| code | Integer | 状态码，200表示成功 |
| message | String | 提示信息 |
| data.accessToken | String | 访问令牌 |
| data.refreshToken | String | 刷新令牌 |
| data.tokenType | String | 令牌类型，固定为"Bearer" |
| data.expiresIn | Long | 过期时间（毫秒），7200000=2小时 |
| data.userId | Long | 用户ID |
| data.username | String | 用户名 |
| data.realName | String | 真实姓名 |
| data.roles | Array | 角色列表 |
| data.permissions | Array | 权限标识列表 |

**响应示例**：
```json
{
  "code": 200,
  "message": "登录成功",
  "data": {
    "accessToken": "eyJhbGciOiJIUzUxMiJ9...",
    "refreshToken": "eyJhbGciOiJIUzUxMiJ9...",
    "tokenType": "Bearer",
    "expiresIn": 7200000,
    "userId": 1,
    "username": "admin",
    "realName": "系统管理员",
    "roles": ["admin"],
    "permissions": ["system:user:list", "system:role:list", "..."]
  },
  "success": true
}
```

**错误码**：

| 错误码 | 说明 |
|--------|------|
| 2004 | 用户名或密码错误 |
| 2005 | 账号已锁定（连续5次登录失败后锁定15分钟） |
| 400 | 密码长度必须在6-100个字符之间 |

---

### 1.2 刷新Token

- **接口名称**：刷新Token
- **路径**：`POST /auth/refresh`
- **是否需要认证**：否

**请求参数**：

| 字段名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| refreshToken | String | 是 | 刷新令牌 |

**请求示例**：
```json
{
  "refreshToken": "eyJhbGciOiJIUzUxMiJ9..."
}
```

**响应格式**：

| 字段名 | 类型 | 说明 |
|--------|------|------|
| data.accessToken | String | 新的访问令牌 |
| data.refreshToken | String | 新的刷新令牌 |
| data.tokenType | String | 令牌类型，固定为"Bearer" |
| data.expiresIn | Long | 过期时间（毫秒） |

**响应示例**：
```json
{
  "code": 200,
  "message": "Token刷新成功",
  "data": {
    "accessToken": "eyJhbGciOiJIUzUxMiJ9...",
    "refreshToken": "eyJhbGciOiJIUzUxMiJ9...",
    "tokenType": "Bearer",
    "expiresIn": 7200000
  },
  "success": true
}
```

**错误码**：

| 错误码 | 说明 |
|--------|------|
| 401 | 刷新令牌无效或已过期 |

---

### 1.3 获取验证码

- **接口名称**：获取验证码
- **路径**：`GET /auth/captcha`
- **是否需要认证**：否

**请求参数**：无

**响应格式**：

| 字段名 | 类型 | 说明 |
|--------|------|------|
| data.uuid | String | 验证码唯一标识 |
| data.captchaImage | String | Base64编码的验证码图片（含data:image/png;base64前缀） |

**响应示例**：
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "uuid": "a1b2c3d4e5f6...",
    "captchaImage": "data:image/png;base64,iVBORw0KGgo..."
  },
  "success": true
}
```

---

### 1.4 获取用户信息

- **接口名称**：获取当前登录用户信息
- **路径**：`GET /auth/info`
- **是否需要认证**：是

**请求参数**：无（通过Token识别用户）

**响应格式**：

| 字段名 | 类型 | 说明 |
|--------|------|------|
| data.user.id | Long | 用户ID |
| data.user.username | String | 用户名 |
| data.user.realName | String | 真实姓名 |
| data.user.deptId | Long | 部门ID |
| data.user.deptName | String | 部门名称 |
| data.user.phone | String | 手机号 |
| data.user.email | String | 邮箱 |
| data.user.status | Integer | 状态（0正常 1禁用） |
| data.user.avatar | String | 头像 |
| data.roles | Array | 角色列表 |
| data.permissions | Array | 权限标识列表 |

**响应示例**：
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "user": {
      "id": 1,
      "username": "admin",
      "realName": "系统管理员",
      "deptId": 1,
      "deptName": "检验中心",
      "phone": "13800138000",
      "email": "admin@hospital.com",
      "status": 0,
      "avatar": null
    },
    "roles": ["admin"],
    "permissions": ["system:user:list", "..."]
  },
  "success": true
}
```

**错误码**：

| 错误码 | 说明 |
|--------|------|
| 401 | 未认证（Token缺失或无效） |

---

### 1.5 获取用户信息(别名)

- **接口名称**：获取当前登录用户信息（别名路径）
- **路径**：`GET /auth/user-info`
- **是否需要认证**：是

**请求参数**：无（通过Token识别用户）

**响应格式**：同1.4获取用户信息

---

### 1.6 用户登出

- **接口名称**：用户登出
- **路径**：`POST /auth/logout`
- **是否需要认证**：是

**请求参数**：无

**Token黑名单机制**：登出后，当前AccessToken将被加入Redis黑名单（key格式：`blacklist:token:{完整token}`），使用该旧Token请求将返回401。

**响应示例**：
```json
{
  "code": 200,
  "message": "登出成功",
  "data": null,
  "success": true
}
```

---

### 1.7 修改密码

- **接口名称**：修改当前用户密码
- **路径**：`POST /auth/password`
- **是否需要认证**：是

**请求参数**：

| 字段名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| oldPassword | String | 是 | 旧密码（明文传输，后端BCrypt校验） |
| newPassword | String | 是 | 新密码（明文传输，后端BCrypt加密存储） |

**请求示例**：
```json
{
  "oldPassword": "admin123",
  "newPassword": "newPassword456"
}
```

**响应示例**：
```json
{
  "code": 200,
  "message": "密码修改成功",
  "data": null,
  "success": true
}
```

**错误码**：

| 错误码 | 说明 |
|--------|------|
| 401 | 未登录 |
| 400 | 旧密码错误 |

---

## 2. 用户模块 (user)

> 请求前缀：`/user` | 共62个端点

### 2.1 用户管理 (UserController) - 13个端点

#### 2.1.1 分页查询用户列表

- **路径**：`GET /user/list`
- **是否需要认证**：是

**请求参数**：

| 字段名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| userName | String | 否 | 用户名 |
| nickName | String | 否 | 昵称 |
| phone | String | 否 | 手机号 |
| status | Integer | 否 | 状态 |
| deptId | Long | 否 | 部门ID |
| userType | String | 否 | 用户类型 |
| pageNum | Integer | 否 | 页码，默认1 |
| pageSize | Integer | 否 | 每页条数，默认10 |

**响应格式**：分页结构，records含id、userName、nickName、deptId、phone、email、status等字段

---

#### 2.1.2 根据ID查询用户

- **路径**：`GET /user/{id}`
- **是否需要认证**：是

**路径参数**：id (Long, 必填, 用户ID)

**响应格式**：用户完整信息

---

#### 2.1.3 创建用户

- **路径**：`POST /user`
- **是否需要认证**：是

**请求参数**：

| 字段名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| userName | String | 是 | 用户名（2-50字符） |
| nickName | String | 是 | 昵称（1-50字符） |
| deptId | Long | 否 | 部门ID |
| userType | String | 否 | 用户类型 |
| email | String | 否 | 邮箱 |
| phone | String | 否 | 手机号 |
| gender | String | 否 | 性别 |
| avatar | String | 否 | 头像 |
| password | String | 是 | 密码（6-100字符） |
| status | Integer | 否 | 状态 |
| roleIds | Array\<Long\> | 否 | 角色ID列表 |
| remark | String | 否 | 备注 |

**响应格式**：data = Long (新创建的用户ID)

**错误码**：400 - 用户名不能为空, 密码不能为空

---

#### 2.1.4 更新用户

- **路径**：`PUT /user`
- **是否需要认证**：是

**请求参数**：同2.1.3，增加id字段(Long, 必填, 用户ID)

---

#### 2.1.5 删除用户

- **路径**：`DELETE /user/{id}`
- **是否需要认证**：是

**路径参数**：id (Long, 必填, 用户ID)

---

#### 2.1.6 批量删除用户

- **路径**：`DELETE /user/batch`
- **是否需要认证**：是

**请求参数**：Body = Array\<Long\> (用户ID列表)

---

#### 2.1.7 修改密码

- **路径**：`PUT /user/password`
- **是否需要认证**：是

**请求参数**：

| 字段名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| userId | Long | 是 | 用户ID |
| oldPassword | String | 是 | 旧密码 |
| newPassword | String | 是 | 新密码 |

---

#### 2.1.8 重置密码

- **路径**：`PUT /user/{id}/reset-password`
- **是否需要认证**：是

**路径参数**：id (Long, 必填, 用户ID)

**查询参数**：passwordHash (String, 必填, BCrypt加密后的密码哈希值)

---

#### 2.1.9 更新用户状态

- **路径**：`PUT /user/{id}/status`
- **是否需要认证**：是

**路径参数**：id (Long, 必填, 用户ID)

**查询参数**：status (Integer, 必填, 状态 0正常 1禁用)

---

#### 2.1.10 分配角色

- **路径**：`POST /user/assign-roles`
- **是否需要认证**：是

**请求参数**：

| 字段名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| userId | Long | 是 | 用户ID |
| roleIds | Array\<Long\> | 否 | 角色ID列表 |

---

#### 2.1.11 获取用户角色列表

- **路径**：`GET /user/{userId}/roles`
- **是否需要认证**：是

**路径参数**：userId (Long, 必填, 用户ID)

**响应格式**：data = Array\<String\> (角色编码列表)

---

#### 2.1.12 获取用户权限列表

- **路径**：`GET /user/{userId}/permissions`
- **是否需要认证**：是

**路径参数**：userId (Long, 必填, 用户ID)

**响应格式**：data = Array\<String\> (权限标识列表)

---

#### 2.1.13 根据用户名获取用户信息

- **路径**：`GET /user/username/{username}`
- **是否需要认证**：是

**路径参数**：username (String, 必填, 用户名)

**响应格式**：data = Map\<String, Object\> (用户信息键值对)

---

### 2.2 部门管理 (DeptController) - 8个端点

#### 2.2.1 查询部门列表

- **路径**：`GET /user/dept/list`
- **是否需要认证**：是

**请求参数**：deptName(String, 否), status(Integer, 否)

**响应格式**：List，含id、parentId、deptName、deptCode、leader、phone、sort、status等字段

---

#### 2.2.2 根据ID查询部门

- **路径**：`GET /user/dept/{id}`
- **是否需要认证**：是

**路径参数**：id (Long, 必填, 部门ID)

---

#### 2.2.3 创建部门

- **路径**：`POST /user/dept`
- **是否需要认证**：是

**请求参数**：

| 字段名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| parentId | Long | 否 | 父部门ID |
| deptName | String | 是 | 部门名称（最大50字符） |
| deptCode | String | 否 | 部门编码（最大50字符） |
| leader | String | 否 | 负责人（最大50字符） |
| phone | String | 否 | 联系电话（最大20字符） |
| email | String | 否 | 邮箱（最大100字符） |
| sort | Integer | 否 | 显示顺序 |
| status | Integer | 否 | 状态 |
| remark | String | 否 | 备注 |

**响应格式**：data = Long (新创建的部门ID)

---

#### 2.2.4 更新部门

- **路径**：`PUT /user/dept`
- **是否需要认证**：是

**请求参数**：同2.2.3，增加id字段(Long, 必填, 部门ID)

---

#### 2.2.5 删除部门

- **路径**：`DELETE /user/dept/{id}`
- **是否需要认证**：是

**路径参数**：id (Long, 必填, 部门ID)

---

#### 2.2.6 获取部门树

- **路径**：`GET /user/dept/tree`
- **是否需要认证**：是

**响应格式**：树形结构，含id、parentId、deptName、deptCode、leader、phone、email、sort、status、children

**响应示例**：
```json
{
  "code": 200,
  "message": "操作成功",
  "data": [
    {
      "id": 1,
      "parentId": 0,
      "deptName": "检验中心",
      "deptCode": "LAB001",
      "leader": "管理员",
      "phone": "010-12345678",
      "email": "lab@hospital.com",
      "sort": 0,
      "status": 0,
      "children": []
    }
  ],
  "success": true
}
```

---

#### 2.2.7 获取部门树节点

- **路径**：`GET /user/dept/tree-nodes`
- **是否需要认证**：是

**响应格式**：List，含id、label、parentId、children（用于前端选择器）

---

#### 2.2.8 获取角色部门ID列表

- **路径**：`GET /user/dept/role/{roleId}`
- **是否需要认证**：是

**路径参数**：roleId (Long, 必填, 角色ID)

**响应格式**：data = Array\<Long\> (部门ID列表)

---

### 2.3 角色管理 (RoleController) - 9个端点

#### 2.3.1 分页查询角色列表

- **路径**：`GET /user/role/list`
- **是否需要认证**：是

**请求参数**：roleName(String, 否), roleCode(String, 否), status(Integer, 否), pageNum(Integer, 否), pageSize(Integer, 否)

**响应格式**：分页结构，records含id、roleName、roleCode、roleKey、roleSort、dataScope、status等字段

---

#### 2.3.2 根据ID查询角色

- **路径**：`GET /user/role/{id}`
- **是否需要认证**：是

**路径参数**：id (Long, 必填, 角色ID)

---

#### 2.3.3 创建角色

- **路径**：`POST /user/role`
- **是否需要认证**：是

**请求参数**：

| 字段名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| roleName | String | 是 | 角色名称（最大50字符） |
| roleCode | String | 是 | 角色编码（最大50字符） |
| roleKey | String | 是 | 角色权限字符（最大100字符） |
| roleSort | Integer | 否 | 显示顺序 |
| dataScope | Integer | 否 | 数据范围 |
| menuCheckStrictly | Integer | 否 | 菜单树选择项是否关联显示 |
| deptCheckStrictly | Integer | 否 | 部门树选择项是否关联显示 |
| status | Integer | 否 | 状态 |
| menuIds | Array\<Long\> | 否 | 菜单ID列表 |
| deptIds | Array\<Long\> | 否 | 部门ID列表 |
| remark | String | 否 | 备注 |

**响应格式**：data = Long (新创建的角色ID)

---

#### 2.3.4 更新角色

- **路径**：`PUT /user/role`
- **是否需要认证**：是

**请求参数**：同2.3.3，增加id字段(Long, 必填, 角色ID)

---

#### 2.3.5 删除角色

- **路径**：`DELETE /user/role/{id}`
- **是否需要认证**：是

**路径参数**：id (Long, 必填, 角色ID)

---

#### 2.3.6 批量删除角色

- **路径**：`DELETE /user/role/batch`
- **是否需要认证**：是

**请求参数**：Body = Array\<Long\> (角色ID列表)

---

#### 2.3.7 分配菜单

- **路径**：`POST /user/role/assign-menus`
- **是否需要认证**：是

**请求参数**：

| 字段名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| roleId | Long | 是 | 角色ID |
| menuIds | Array\<Long\> | 否 | 菜单ID列表 |

---

#### 2.3.8 获取角色菜单ID列表

- **路径**：`GET /user/role/{roleId}/menus`
- **是否需要认证**：是

**路径参数**：roleId (Long, 必填, 角色ID)

**响应格式**：data = Array\<Long\> (菜单ID列表)

---

#### 2.3.9 获取所有角色

- **路径**：`GET /user/role/all`
- **是否需要认证**：是

**响应格式**：List，含id、roleName、roleCode、roleKey、status等字段

---

### 2.4 菜单权限管理 (MenuController) - 10个端点

#### 2.4.1 查询菜单列表

- **路径**：`GET /user/menu/list`
- **是否需要认证**：是

**请求参数**：menuName(String, 否), status(Integer, 否)

**响应格式**：List，含id、menuName、parentId、orderNum、path、component、menuType、visible、status、perms、icon等字段

---

#### 2.4.2 根据ID查询菜单

- **路径**：`GET /user/menu/{id}`
- **是否需要认证**：是

**路径参数**：id (Long, 必填, 菜单ID)

---

#### 2.4.3 创建菜单

- **路径**：`POST /user/menu`
- **是否需要认证**：是

**请求参数**：

| 字段名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| menuName | String | 是 | 菜单名称（最大50字符） |
| parentId | Long | 否 | 父菜单ID |
| orderNum | Integer | 否 | 显示顺序 |
| path | String | 否 | 路由地址（最大200字符） |
| component | String | 否 | 组件路径（最大255字符） |
| query | String | 否 | 路由参数 |
| routeName | String | 否 | 路由名称（最大50字符） |
| isFrame | Integer | 否 | 是否为外链 |
| isCache | Integer | 否 | 是否缓存 |
| menuType | String | 是 | 菜单类型（M目录 C菜单 F按钮） |
| visible | Integer | 否 | 是否显示 |
| status | Integer | 否 | 状态 |
| perms | String | 否 | 权限标识（最大100字符） |
| icon | String | 否 | 菜单图标（最大100字符） |
| remark | String | 否 | 备注 |

**响应格式**：data = Long (新创建的菜单ID)

---

#### 2.4.4 更新菜单

- **路径**：`PUT /user/menu`
- **是否需要认证**：是

**请求参数**：同2.4.3，增加id字段(Long, 必填, 菜单ID)

---

#### 2.4.5 删除菜单

- **路径**：`DELETE /user/menu/{id}`
- **是否需要认证**：是

**路径参数**：id (Long, 必填, 菜单ID)

---

#### 2.4.6 获取菜单树

- **路径**：`GET /user/menu/tree`
- **是否需要认证**：是

**响应格式**：树形结构，含children嵌套

---

#### 2.4.7 获取菜单树节点

- **路径**：`GET /user/menu/tree-nodes`
- **是否需要认证**：是

**响应格式**：List，含id、label、parentId、children（用于前端选择器）

---

#### 2.4.8 获取用户菜单

- **路径**：`GET /user/menu/user/{userId}`
- **是否需要认证**：是

**路径参数**：userId (Long, 必填, 用户ID)

---

#### 2.4.9 获取角色菜单

- **路径**：`GET /user/menu/role/{roleId}`
- **是否需要认证**：是

**路径参数**：roleId (Long, 必填, 角色ID)

---

#### 2.4.10 获取用户权限列表

- **路径**：`GET /user/menu/user/{userId}/permissions`
- **是否需要认证**：是

**路径参数**：userId (Long, 必填, 用户ID)

**响应格式**：data = Array\<String\> (权限标识列表)

---

### 2.5 字典管理 (DictController) - 14个端点

#### 2.5.1 分页查询字典类型列表

- **路径**：`GET /user/dict/type/list`
- **是否需要认证**：是

**请求参数**：dictName(String, 否), dictType(String, 否), status(Integer, 否), pageNum(Integer, 否), pageSize(Integer, 否)

**响应格式**：分页结构，records含id、dictName、dictType、status、remark

---

#### 2.5.2 根据ID查询字典类型

- **路径**：`GET /user/dict/type/{id}`
- **是否需要认证**：是

**路径参数**：id (Long, 必填, 字典类型ID)

---

#### 2.5.3 根据字典类型编码查询

- **路径**：`GET /user/dict/type/code/{dictType}`
- **是否需要认证**：是

**路径参数**：dictType (String, 必填, 字典类型编码)

---

#### 2.5.4 创建字典类型

- **路径**：`POST /user/dict/type`
- **是否需要认证**：是

**请求参数**：

| 字段名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| dictName | String | 是 | 字典名称（最大100字符） |
| dictType | String | 是 | 字典类型（最大100字符） |
| status | Integer | 否 | 状态 |
| remark | String | 否 | 备注 |

**响应格式**：data = Long (新创建的字典类型ID)

---

#### 2.5.5 更新字典类型

- **路径**：`PUT /user/dict/type`
- **是否需要认证**：是

**请求参数**：同2.5.4，增加id字段(Long, 必填, 字典类型ID)

---

#### 2.5.6 删除字典类型

- **路径**：`DELETE /user/dict/type/{id}`
- **是否需要认证**：是

**路径参数**：id (Long, 必填, 字典类型ID)

---

#### 2.5.7 批量删除字典类型

- **路径**：`DELETE /user/dict/type/batch`
- **是否需要认证**：是

**请求参数**：Body = Array\<Long\> (字典类型ID列表)

---

#### 2.5.8 分页查询字典数据列表

- **路径**：`GET /user/dict/data/list`
- **是否需要认证**：是

**请求参数**：dictType(String, 否), dictLabel(String, 否), status(Integer, 否), pageNum(Integer, 否), pageSize(Integer, 否)

**响应格式**：分页结构，records含id、dictSort、dictLabel、dictValue、dictType、cssClass、listClass、isDefault、status

---

#### 2.5.9 根据ID查询字典数据

- **路径**：`GET /user/dict/data/{id}`
- **是否需要认证**：是

**路径参数**：id (Long, 必填, 字典数据ID)

---

#### 2.5.10 创建字典数据

- **路径**：`POST /user/dict/data`
- **是否需要认证**：是

**请求参数**：

| 字段名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| dictSort | Integer | 否 | 排序 |
| dictLabel | String | 是 | 字典标签（最大100字符） |
| dictValue | String | 是 | 字典键值（最大100字符） |
| dictType | String | 是 | 字典类型（最大100字符） |
| cssClass | String | 否 | 样式属性（最大100字符） |
| listClass | String | 否 | 表格回显样式（最大100字符） |
| isDefault | Integer | 否 | 是否默认 |
| status | Integer | 否 | 状态 |
| remark | String | 否 | 备注 |

**响应格式**：data = Long (新创建的字典数据ID)

---

#### 2.5.11 更新字典数据

- **路径**：`PUT /user/dict/data`
- **是否需要认证**：是

**请求参数**：同2.5.10，增加id字段(Long, 必填, 字典数据ID)

---

#### 2.5.12 删除字典数据

- **路径**：`DELETE /user/dict/data/{id}`
- **是否需要认证**：是

**路径参数**：id (Long, 必填, 字典数据ID)

---

#### 2.5.13 批量删除字典数据

- **路径**：`DELETE /user/dict/data/batch`
- **是否需要认证**：是

**请求参数**：Body = Array\<Long\> (字典数据ID列表)

---

#### 2.5.14 根据字典类型获取字典数据

- **路径**：`GET /user/dict/data/type/{dictType}`
- **是否需要认证**：是

**路径参数**：dictType (String, 必填, 字典类型编码)

**响应格式**：List，字典数据列表

---

### 2.6 日志管理 - 8个端点

#### 2.6.1 操作日志列表

- **路径**：`POST /user/oper-log/list`
- **是否需要认证**：是

**请求参数**：pageNum(Integer, 否), pageSize(Integer, 否)

**响应格式**：分页结构，含total、pageNum、pageSize、pages、records

---

#### 2.6.2 保存操作日志

- **路径**：`POST /user/oper-log/save`
- **是否需要认证**：是

**请求参数**：操作日志实体对象

---

#### 2.6.3 登录日志列表

- **路径**：`POST /user/login-log/list`
- **是否需要认证**：是

**请求参数**：pageNum(Integer, 否), pageSize(Integer, 否)

**响应格式**：分页结构，records含id、userName、ip、browser、os、status、msg、loginTime

**响应示例**：
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "total": 30,
    "pageNum": 1,
    "pageSize": 10,
    "pages": 3,
    "records": [
      {
        "id": 28,
        "userName": "admin",
        "ip": "",
        "browser": "",
        "os": "",
        "status": 0,
        "msg": "登录成功",
        "loginTime": "2026-04-12T03:15:08"
      }
    ]
  },
  "success": true
}
```

---

#### 2.6.4 保存登录日志

- **路径**：`POST /user/login-log/save`
- **是否需要认证**：是

**请求参数**：登录日志实体对象

---

#### 2.6.5 错误日志列表

- **路径**：`POST /user/error-log/list`
- **是否需要认证**：是

**请求参数**：同2.6.1

**响应格式**：分页结构

---

#### 2.6.6 保存错误日志

- **路径**：`POST /user/error-log/save`
- **是否需要认证**：是

**请求参数**：错误日志实体对象

---

#### 2.6.7 审计日志列表

- **路径**：`POST /user/audit-log/list`
- **是否需要认证**：是

**请求参数**：同2.6.1

**响应格式**：分页结构

---

#### 2.6.8 保存审计日志

- **路径**：`POST /user/audit-log/save`
- **是否需要认证**：是

**请求参数**：审计日志实体对象

---

## 3. 标本模块 (specimen)

> 请求前缀：`/specimen` | 共17个端点

### 3.1 标本登记

- **路径**：`POST /specimen/register`
- **是否需要认证**：是

**请求参数**：

| 字段名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| patientId | Long | 否 | 患者ID |
| patientName | String | 是 | 患者姓名 |
| patientGender | String | 否 | 患者性别（M男 F女） |
| patientAge | String | 否 | 患者年龄 |
| patientIdNo | String | 否 | 身份证号 |
| patientPhone | String | 否 | 联系电话 |
| deptId | Long | 是 | 送检科室ID |
| deptName | String | 否 | 送检科室名称 |
| wardId | Long | 否 | 病区ID |
| wardName | String | 否 | 病区名称 |
| bedNo | String | 否 | 床号 |
| doctorId | Long | 否 | 送检医生ID |
| doctorName | String | 否 | 送检医生姓名 |
| specimenTypeId | Long | 是 | 标本类型ID |
| specimenTypeName | String | 否 | 标本类型名称 |
| collectTime | String | 否 | 采集时间 |
| collectUserId | Long | 否 | 采集人ID |
| collectUserName | String | 否 | 采集人姓名 |
| isStat | Integer | 否 | 是否急诊（0否 1是） |
| clinicalDiagnosis | String | 否 | 临床诊断 |
| remark | String | 否 | 备注 |
| testItemIds | Array\<Long\> | 是 | 检验项目ID列表 |

**请求示例**：
```json
{
  "patientName": "张三",
  "patientGender": "M",
  "patientAge": "30",
  "deptId": 1,
  "deptName": "检验中心",
  "specimenTypeId": 1,
  "specimenTypeName": "血液",
  "testItemIds": [1, 2],
  "clinicalDiagnosis": "发热",
  "isStat": 0
}
```

**响应格式**：

| 字段名 | 类型 | 说明 |
|--------|------|------|
| data.id | Long | 标本ID |
| data.specimenNo | String | 标本编号 |
| data.barcode | String | 条码号 |
| data.patientName | String | 患者姓名 |
| data.status | String | 标本状态 |
| data.isStat | Integer | 是否急诊 |

**响应示例**：
```json
{
  "code": 200,
  "message": "标本登记成功",
  "data": {
    "id": 22,
    "specimenNo": "SP20260412000007",
    "barcode": "SP20260412000007",
    "patientName": "APItestPatient",
    "status": "registered",
    "isStat": 0
  },
  "success": true
}
```

**错误码**：400 - 患者姓名不能为空, 标本类型ID不能为空, 送检科室ID不能为空, 检验项目不能为空

---

### 3.2 根据条码查询标本

- **路径**：`GET /specimen/getByBarcode`
- **是否需要认证**：是

**查询参数**：barcode (String, 必填, 标本条码)

**响应格式**：同3.1登记响应中的data结构

**错误码**：500 - 标本不存在

---

### 3.3 根据编号查询标本

- **路径**：`GET /specimen/getBySpecimenNo`
- **是否需要认证**：是

**查询参数**：specimenNo (String, 必填, 标本编号)

**响应格式**：同3.1登记响应中的data结构

---

### 3.4 根据ID查询标本详情

- **路径**：`GET /specimen/getById/{id}`
- **是否需要认证**：是

**路径参数**：id (Long, 必填, 标本ID)

**响应格式**：同3.1登记响应中的data结构

**错误码**：500 - 标本不存在

---

### 3.5 标本列表

- **路径**：`POST /specimen/list`
- **是否需要认证**：是

**请求参数**：pageNum(Integer, 否), pageSize(Integer, 否)

**响应格式**：分页结构，records含id、specimenNo、barcode、patientName、deptName、specimenTypeName、status

---

### 3.6 标本签收

- **路径**：`POST /specimen/receive`
- **是否需要认证**：是

**请求参数**：

| 字段名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| barcode | String | 是 | 标本条码 |
| receiveUserId | Long | 否 | 接收人ID |
| receiveUserName | String | 否 | 接收人姓名 |
| remark | String | 否 | 备注 |

**错误码**：500 - 标本不存在; 400 - 标本条码不能为空

---

### 3.7 标本入库

- **路径**：`POST /specimen/storage`
- **是否需要认证**：是

**请求参数**：

| 字段名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| specimenId | Long | 否 | 标本ID（与barcode二选一） |
| barcode | String | 否 | 标本条码（与specimenId二选一） |
| storageLocation | String | 是 | 存储位置 |
| operatorId | Long | 否 | 操作人ID |
| operatorName | String | 否 | 操作人姓名 |
| remark | String | 否 | 备注 |

**状态机校验**：入库操作目标状态为`stored`（已入库），前置状态必须为`received`（已接收）。非法状态流转将返回BusinessException。

**标本状态流转规则**：
- pending -> received / rejected / cancelled
- received -> stored / testing / rejected / cancelled
- stored -> testing / rejected / cancelled
- testing -> completed / cancelled
- completed / rejected / cancelled 为终态，不可变更

---

### 3.8 更新标本状态

- **路径**：`POST /specimen/updateStatus`
- **是否需要认证**：是

**请求参数**：

| 字段名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| specimenId | Long | 是 | 标本ID |
| targetStatus | String | 是 | 目标状态（pending/received/stored/testing/completed/rejected/cancelled） |
| operatorId | Long | 否 | 操作人ID |
| operatorName | String | 否 | 操作人姓名 |
| remark | String | 否 | 备注 |

**状态机校验**：非法状态流转将返回BusinessException，具体规则见3.7入库接口说明。

---

### 3.9 标本拒收

- **路径**：`POST /specimen/reject`
- **是否需要认证**：是

**请求参数**：

| 字段名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| barcode | String | 是 | 标本条码 |
| isReject | Integer | 否 | 是否拒收（1是） |
| rejectReason | String | 否 | 拒收原因 |
| rejectType | String | 否 | 拒收类型 |
| remark | String | 否 | 备注 |

**前置状态校验**：只有`pending`（待接收）/`received`（已接收）/`stored`（已入库）状态的标本才能拒收。

---

### 3.10 标本流转记录

- **路径**：`GET /specimen/trace/{specimenId}`
- **是否需要认证**：是

**路径参数**：specimenId (Long, 必填, 标本ID)

**响应格式**：List，含id、specimenId、specimenNo、action、actionName、fromStatus、fromStatusName、toStatus、toStatusName、fromLocation、toLocation、operatorId、operatorName、operateTime

**响应示例**：
```json
{
  "code": 200,
  "message": "操作成功",
  "data": [
    {
      "id": 4,
      "specimenId": 1,
      "specimenNo": "SP20260411000003",
      "action": "testing",
      "actionName": "开始检验",
      "fromStatus": "received",
      "fromStatusName": "已接收",
      "toStatus": "testing",
      "toStatusName": "检验中"
    }
  ],
  "success": true
}
```

---

### 3.11 根据编号获取追溯记录

- **路径**：`GET /specimen/traceByNo`
- **是否需要认证**：是

**查询参数**：specimenNo (String, 必填, 标本编号)

**响应格式**：同3.10流转记录结构

---

### 3.12 标本统计信息

- **路径**：`POST /specimen/statistics`
- **是否需要认证**：是

**请求参数**：

| 字段名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| startTime | String | 否 | 统计开始时间 |
| endTime | String | 否 | 统计结束时间 |
| deptId | Long | 否 | 送检科室ID |
| specimenTypeId | Long | 否 | 标本类型ID |
| statisticsType | String | 否 | 统计类型（day按天 week按周 month按月） |

**响应格式**：含totalCount、registeredCount、receivedCount、testingCount、completedCount等统计字段

---

### 3.13 附加检验项目

- **路径**：`POST /specimen/addition`
- **是否需要认证**：是

**请求参数**：

| 字段名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| specimenId | Long | 是 | 标本ID |
| specimenNo | String | 是 | 标本编号 |
| testItemIds | Array\<Long\> | 是 | 附加检验项目ID列表 |
| addReason | String | 否 | 附加原因 |
| addUserId | Long | 否 | 附加人ID |
| addUserName | String | 否 | 附加人姓名 |
| remark | String | 否 | 备注 |

---

### 3.14 打印条码标记

- **路径**：`POST /specimen/printBarcode/{specimenId}`
- **是否需要认证**：是

**路径参数**：specimenId (Long, 必填, 标本ID)

---

### 3.15 标本类型列表

- **路径**：`GET /specimen/types`
- **是否需要认证**：是

**响应格式**：List，含id、typeCode、typeName、typeDesc、colorCode、containerType、storageCondition、validityPeriod、status

**响应示例**：
```json
{
  "code": 200,
  "message": "操作成功",
  "data": [
    {
      "id": 1,
      "typeCode": "BLOOD",
      "typeName": "血液",
      "typeDesc": "静脉血标本",
      "colorCode": "red",
      "containerType": "真空采血管",
      "storageCondition": "室温",
      "validityPeriod": 24,
      "status": 0
    }
  ],
  "success": true
}
```

---

### 3.16 检验项目列表

- **路径**：`GET /specimen/testItems`
- **是否需要认证**：是

**响应格式**：List，含id、itemCode、itemName、itemNameEn、itemShort、categoryId、specimenTypeId、method、unit、referenceLow、referenceHigh、panicLow、panicHigh

---

### 3.17 检验项目分类列表

- **路径**：`GET /specimen/testItemCategories`
- **是否需要认证**：是

**响应格式**：data = Array\<String\> (分类名称列表)

---

## 4. 报告模块 (report)

> 请求前缀：`/report` | 共32个端点

### 4.1 检验申请管理 (ReportApplyController) - 7个端点

#### 4.1.1 创建检验申请

- **路径**：`POST /report/apply`
- **是否需要认证**：是

**请求参数**：

| 字段名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| specimenId | Long | 否 | 标本ID |
| specimenNo | String | 否 | 标本编号 |
| reportType | String | 否 | 报告类型（routine常规 stat急诊 review复查） |
| patientId | Long | 否 | 患者ID |
| patientName | String | 否 | 患者姓名 |
| patientGender | String | 否 | 患者性别（M男 F女，不是male/female） |
| patientAge | String | 否 | 患者年龄 |
| patientIdNo | String | 否 | 患者身份证号 |
| deptId | Long | 否 | 科室ID（为null时不发送，不是0） |
| deptName | String | 否 | 科室名称 |
| wardName | String | 否 | 病区名称 |
| bedNo | String | 否 | 床号 |
| doctorName | String | 否 | 送检医生 |
| specimenTypeName | String | 否 | 标本类型名称 |
| collectTime | String | 否 | 采集时间 |
| isStat | Integer | 否 | 是否急诊 |
| clinicalDiagnosis | String | 否 | 临床诊断 |
| remark | String | 否 | 备注 |
| testItems | Array | 否 | 检验项目列表 |
| testItems[].itemCode | String | 是 | 项目编码 |
| testItems[].itemName | String | 是 | 项目名称 |
| testItems[].itemNameEn | String | 否 | 英文名称 |
| testItems[].unit | String | 否 | 单位 |
| testItems[].referenceLow | BigDecimal | 否 | 参考值下限 |
| testItems[].referenceHigh | BigDecimal | 否 | 参考值上限 |

**请求示例**：
```json
{
  "specimenId": 1,
  "reportType": "routine",
  "patientName": "张三",
  "patientGender": "M",
  "deptId": 1,
  "testItems": [
    {"itemCode": "ALT", "itemName": "丙氨酸氨基转移酶"},
    {"itemCode": "AST", "itemName": "天冬氨酸氨基转移酶"}
  ]
}
```

**响应格式**：data = Long (报告申请ID)

**响应示例**：
```json
{
  "code": 200,
  "message": "创建成功",
  "data": 16,
  "success": true
}
```

---

#### 4.1.2 更新检验申请

- **路径**：`PUT /report/apply/{reportId}`
- **是否需要认证**：是

**路径参数**：reportId (Long, 必填, 报告ID)

**请求参数**：同4.1.1

---

#### 4.1.3 删除检验申请

- **路径**：`DELETE /report/apply/{reportId}`
- **是否需要认证**：是

**路径参数**：reportId (Long, 必填, 报告ID)

---

#### 4.1.4 取消报告

- **路径**：`POST /report/apply/{reportId}/cancel`
- **是否需要认证**：是

**路径参数**：reportId (Long, 必填, 报告ID)

**查询参数**：reason (String, 否, 取消原因)

---

#### 4.1.5 根据ID获取报告

- **路径**：`GET /report/apply/{reportId}`
- **是否需要认证**：是

**路径参数**：reportId (Long, 必填, 报告ID)

**响应格式**：含id、reportNo、reportType、specimenId、patientName、status、items等

**错误码**：500 - 报告不存在

---

#### 4.1.6 获取报告详情(含明细)

- **路径**：`GET /report/apply/{reportId}/detail`
- **是否需要认证**：是

**路径参数**：reportId (Long, 必填, 报告ID)

**响应格式**：报告完整信息含检验结果明细（items含结果值）

---

#### 4.1.7 报告列表查询

- **路径**：`POST /report/apply/query`
- **是否需要认证**：是

**请求参数**：pageNum(Integer, 否), pageSize(Integer, 否)

**响应格式**：分页结构，records含id、reportNo、reportType、specimenId、patientName、status

---

### 4.2 报告审核管理 (ReportAuditController) - 7个端点

> 双级审核流程：提交审核 -> 初审（first-approve/first-reject） -> 终审（final-approve/final-reject） -> 发布
> 终审人不能与初审人相同，系统通过Gateway注入的X-User-Id自动校验。

#### 4.2.1 提交审核

- **路径**：`POST /report/audit/submit/{reportId}`
- **是否需要认证**：是

**路径参数**：reportId (Long, 必填, 报告ID)

---

#### 4.2.2 审核通过

- **路径**：`POST /report/audit/approve`
- **是否需要认证**：是

**请求参数**：

| 字段名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| reportId | Long | 是 | 报告ID |
| auditLevel | Integer | 否 | 审核级别 |
| auditOpinion | String | 否 | 审核意见 |

---

#### 4.2.3 审核驳回

- **路径**：`POST /report/audit/reject`
- **是否需要认证**：是

**请求参数**：同4.2.2

---

#### 4.2.4 初审通过

- **路径**：`POST /report/audit/first-approve`
- **是否需要认证**：是

**请求参数**：

| 字段名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| reportId | Long | 是 | 报告ID |
| approved | Boolean | 否 | 是否通过 |
| auditLevel | Integer | 否 | 审核级别（1初审） |
| auditOpinion | String | 否 | 审核意见 |

**说明**：初审人ID和姓名由Gateway通过X-User-Id/X-User-Name请求头自动注入，无需前端传递。前置状态必须为`pending_review`（待审核）。

**请求示例**：
```json
{
  "reportId": 16,
  "approved": true,
  "auditLevel": 1
}
```

---

#### 4.2.5 初审驳回

- **路径**：`POST /report/audit/first-reject`
- **是否需要认证**：是

**请求参数**：同4.2.4

**说明**：前置状态必须为`pending_review`（待审核），驳回后状态回退为`draft`（草稿）。

**错误码**：500 - 只有待审核状态的报告才能初审驳回

---

#### 4.2.6 终审通过

- **路径**：`POST /report/audit/final-approve`
- **是否需要认证**：是

**请求参数**：

| 字段名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| reportId | Long | 是 | 报告ID |
| approved | Boolean | 否 | 是否通过 |
| auditLevel | Integer | 否 | 审核级别（2终审） |

**说明**：前置状态必须为`first_audited`（初审通过）。终审人不能与初审人相同，系统自动校验。

**错误码**：500 - 终审人不能与初审人相同; 500 - 只有初审通过状态的报告才能终审

---

#### 4.2.7 终审驳回

- **路径**：`POST /report/audit/final-reject`
- **是否需要认证**：是

**请求参数**：同4.2.6

**说明**：前置状态必须为`first_audited`（初审通过），驳回后状态回退为`pending_review`（待审核）。终审人不能与初审人相同。

**错误码**：500 - 终审人不能与初审人相同

---

### 4.3 结果录入管理 (ResultEntryController) - 4个端点

#### 4.3.1 录入结果

- **路径**：`POST /report/result`
- **是否需要认证**：是

**请求参数**：

| 字段名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| reportId | Long | 是 | 报告ID |
| reportItemId | Long | 否 | 报告明细ID |
| specimenTestItemId | Long | 否 | 标本检验项目ID |
| itemCode | String | 是 | 项目编码 |
| itemName | String | 是 | 项目名称 |
| itemNameEn | String | 否 | 英文名称 |
| resultValue | String | 否 | 结果值 |
| resultText | String | 否 | 结果文本 |
| resultFlag | String | 否 | 结果标志（N正常 H偏高 L偏低 HH极高 LL极低） |
| unit | String | 否 | 单位 |
| referenceLow | BigDecimal | 否 | 参考值下限 |
| referenceHigh | BigDecimal | 否 | 参考值上限 |
| referenceText | String | 否 | 参考值文本 |
| panicLow | BigDecimal | 否 | 危急值下限 |
| panicHigh | BigDecimal | 否 | 危急值上限 |
| method | String | 否 | 检验方法 |
| equipmentName | String | 否 | 检验设备 |
| sort | Integer | 否 | 排序 |
| remark | String | 否 | 备注 |

**请求示例**：
```json
{
  "reportId": 16,
  "itemCode": "ALT",
  "itemName": "丙氨酸氨基转移酶",
  "resultValue": "25.5",
  "unit": "U/L",
  "referenceLow": 0,
  "referenceHigh": 40,
  "resultFlag": "N"
}
```

**响应格式**：data = Long (报告项目ID)

**错误码**：400 - 报告ID不能为空, 项目编码不能为空, 项目名称不能为空

---

#### 4.3.2 批量录入结果

- **路径**：`POST /report/result/batch`
- **是否需要认证**：是

**请求参数**：

| 字段名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| reportId | Long | 是 | 报告ID |
| results | Array | 是 | 结果列表（结构同4.3.1录入结果） |

**请求示例**：
```json
{
  "reportId": 16,
  "results": [
    {"itemCode": "ALT", "itemName": "ALT", "resultValue": "25.5", "unit": "U/L", "resultFlag": "N"},
    {"itemCode": "AST", "itemName": "AST", "resultValue": "30.0", "unit": "U/L", "resultFlag": "N"}
  ]
}
```

---

#### 4.3.3 删除结果

- **路径**：`DELETE /report/result/{reportItemId}`
- **是否需要认证**：是

**路径参数**：reportItemId (Long, 必填, 报告项目ID)

---

#### 4.3.4 获取结果明细

- **路径**：`GET /report/result/{reportItemId}`
- **是否需要认证**：是

**路径参数**：reportItemId (Long, 必填, 报告项目ID)

**响应格式**：含id、reportId、itemCode、itemName、resultValue、resultFlag、unit、referenceLow、referenceHigh

---

### 4.4 报告发布管理 (ReportPublishController) - 3个端点

#### 4.4.1 发布报告

- **路径**：`POST /report/publish`
- **是否需要认证**：是

**请求参数**：

| 字段名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| reportId | Long | 是 | 报告ID |
| reportConclusion | String | 否 | 报告结论 |

**错误码**：400 - 报告ID不能为空; 500 - 只有已审核状态（final_audited或audited）的报告才能发布

---

#### 4.4.2 打印报告

- **路径**：`POST /report/publish/print`
- **是否需要认证**：是

**请求参数**：

| 字段名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| reportId | Long | 是 | 报告ID |
| printCopies | Integer | 否 | 打印份数，默认1 |
| printerName | String | 否 | 打印机名称 |
| printIp | String | 否 | 打印IP |

---

#### 4.4.3 获取打印记录

- **路径**：`GET /report/publish/printLogs/{reportId}`
- **是否需要认证**：是

**路径参数**：reportId (Long, 必填, 报告ID)

**响应格式**：List，含id、reportId、printCopies、printerName、printIp、printUserName、printTime

---

### 4.5 患者管理 (PatientController) - 6个端点

#### 4.5.1 创建患者

- **路径**：`POST /report/patient`
- **是否需要认证**：是

**请求参数**：

| 字段名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| patientName | String | 是 | 患者姓名 |
| gender | String | 否 | 性别（0男 1女 2未知） |
| birthDate | String | 否 | 出生日期 |
| age | String | 否 | 年龄 |
| idType | String | 否 | 证件类型（01身份证 02护照 03军官证 04其他） |
| idNo | String | 否 | 证件号码 |
| phone | String | 否 | 联系电话 |
| email | String | 否 | 电子邮箱 |
| address | String | 否 | 家庭住址 |
| nation | String | 否 | 民族 |
| marriage | String | 否 | 婚姻状况 |
| occupation | String | 否 | 职业 |
| workUnit | String | 否 | 工作单位 |
| contactName | String | 否 | 联系人姓名 |
| contactPhone | String | 否 | 联系人电话 |
| contactRelation | String | 否 | 联系人关系 |
| bloodType | String | 否 | 血型 |
| allergyHistory | String | 否 | 过敏史 |
| medicalHistory | String | 否 | 既往病史 |
| remark | String | 否 | 备注 |

**响应格式**：data = Long (新创建的患者ID)

---

#### 4.5.2 更新患者

- **路径**：`PUT /report/patient`
- **是否需要认证**：是

**请求参数**：同4.5.1，增加id字段(Long, 必填, 患者ID)

---

#### 4.5.3 删除患者

- **路径**：`DELETE /report/patient/{id}`
- **是否需要认证**：是

**路径参数**：id (Long, 必填, 患者ID)

---

#### 4.5.4 根据ID获取患者

- **路径**：`GET /report/patient/{id}`
- **是否需要认证**：是

**路径参数**：id (Long, 必填, 患者ID)

---

#### 4.5.5 根据证件号码获取患者

- **路径**：`GET /report/patient/byIdNo/{idNo}`
- **是否需要认证**：是

**路径参数**：idNo (String, 必填, 证件号码)

---

#### 4.5.6 分页查询患者

- **路径**：`POST /report/patient/query`
- **是否需要认证**：是

**请求参数**：patientName(String, 否), idNo(String, 否), phone(String, 否), pageNum(Integer, 否), pageSize(Integer, 否)

**响应格式**：分页结构，包含患者列表

---

### 4.6 危急值管理 (PanicValueController) - 5个端点

#### 4.6.1 分页查询危急值

- **路径**：`POST /report/panic/query`
- **是否需要认证**：是

**请求参数**：

| 字段名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| panicNo | String | 否 | 危急值编号 |
| reportNo | String | 否 | 报告编号 |
| patientName | String | 否 | 患者姓名 |
| deptId | Long | 否 | 科室ID |
| handleStatus | Integer | 否 | 处理状态（0待处理 1已通知 2已处理 3已确认） |
| itemCode | String | 否 | 项目编码 |
| startTime | String | 否 | 开始时间 |
| endTime | String | 否 | 结束时间 |
| pageNum | Integer | 否 | 页码，默认1 |
| pageSize | Integer | 否 | 每页条数，默认10 |

**响应格式**：分页结构，records含id、panicNo、reportId、patientName、itemCode、itemName、resultValue、panicLow、panicHigh、handleStatus

---

#### 4.6.2 获取危急值详情

- **路径**：`GET /report/panic/{panicValueId}`
- **是否需要认证**：是

**路径参数**：panicValueId (Long, 必填, 危急值ID)

---

#### 4.6.3 处理危急值

- **路径**：`POST /report/panic/handle`
- **是否需要认证**：是

**请求参数**：

| 字段名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| panicValueId | Long | 是 | 危急值ID |
| notifyMethod | String | 否 | 通知方式 |
| receiveUserName | String | 否 | 接收人姓名 |
| handleResult | String | 否 | 处理结果 |
| remark | String | 否 | 备注 |

---

#### 4.6.4 通知危急值

- **路径**：`POST /report/panic/notify/{panicValueId}`
- **是否需要认证**：是

**路径参数**：panicValueId (Long, 必填, 危急值ID)

**查询参数**：notifyMethod (String, 必填, 通知方式), receiveUserName (String, 必填, 接收人姓名)

---

#### 4.6.5 确认危急值

- **路径**：`POST /report/panic/confirm/{panicValueId}`
- **是否需要认证**：是

**路径参数**：panicValueId (Long, 必填, 危急值ID)

---

## 5. 设备模块 (equipment)

> 请求前缀：`/equipment` | 共27个端点

### 5.1 设备台账管理 (EquipmentController) - 9个端点

#### 5.1.1 分页查询设备列表(GET)

- **路径**：`GET /equipment/page`
- **是否需要认证**：是

**查询参数**：pageNum(Integer, 否), pageSize(Integer, 否), equipmentCode(String, 否), equipmentName(String, 否), status(String, 否)

**响应格式**：分页结构，records含id、equipmentCode、equipmentName、equipmentNameEn、equipmentTypeId、equipmentTypeName、brand、model、status

**响应示例**：
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "total": 1,
    "records": [
      {
        "id": 1,
        "equipmentCode": "EQ001",
        "equipmentName": "全自动生化分析仪1号",
        "status": "normal"
      }
    ]
  },
  "success": true
}
```

---

#### 5.1.2 分页查询设备列表(POST)

- **路径**：`POST /equipment/page`
- **是否需要认证**：是

**请求参数**：同5.1.1查询参数，以JSON Body方式提交

---

#### 5.1.3 根据ID查询设备

- **路径**：`GET /equipment/{id}`
- **是否需要认证**：是

**路径参数**：id (Long, 必填, 设备ID)

---

#### 5.1.4 根据编码查询设备

- **路径**：`GET /equipment/code/{equipmentCode}`
- **是否需要认证**：是

**路径参数**：equipmentCode (String, 必填, 设备编码)

---

#### 5.1.5 新增设备

- **路径**：`POST /equipment`
- **是否需要认证**：是

**请求参数**：

| 字段名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| equipmentCode | String | 是 | 设备编码 |
| equipmentName | String | 是 | 设备名称 |
| equipmentNameEn | String | 否 | 设备英文名 |
| equipmentTypeId | Long | 否 | 设备类型ID |
| equipmentTypeName | String | 否 | 设备类型名称 |
| brand | String | 否 | 品牌 |
| model | String | 否 | 型号 |
| serialNo | String | 否 | 序列号 |
| manufacturer | String | 否 | 生产厂家 |
| supplier | String | 否 | 供应商 |
| purchaseDate | String | 否 | 购置日期 |
| installDate | String | 否 | 安装日期 |
| warrantyExpireDate | String | 否 | 保修到期日期 |
| useLife | Integer | 否 | 使用年限（年） |
| originalValue | BigDecimal | 否 | 原值 |
| netValue | BigDecimal | 否 | 净值 |
| location | String | 否 | 存放位置 |
| labId | Long | 否 | 所属实验室ID |
| labName | String | 否 | 所属实验室名称 |
| responsibleUserId | Long | 否 | 负责人ID |
| responsibleUserName | String | 否 | 负责人姓名 |
| contactPhone | String | 否 | 联系电话 |
| status | String | 否 | 设备状态 |
| ipAddress | String | 否 | IP地址 |
| port | Integer | 否 | 端口号 |
| communicationProtocol | String | 否 | 通信协议 |
| baudRate | Integer | 否 | 波特率 |
| remark | String | 否 | 备注 |

**响应格式**：data = Long (新创建的设备ID)

---

#### 5.1.6 更新设备

- **路径**：`PUT /equipment`
- **是否需要认证**：是

**请求参数**：同5.1.5，增加id字段(设备ID)

---

#### 5.1.7 删除设备

- **路径**：`DELETE /equipment/{id}`
- **是否需要认证**：是

**路径参数**：id (Long, 必填, 设备ID)

---

#### 5.1.8 批量删除设备

- **路径**：`DELETE /equipment/batch`
- **是否需要认证**：是

**请求参数**：Body = Array\<Long\> (设备ID数组)

---

#### 5.1.9 更新设备状态

- **路径**：`PUT /equipment/{id}/status`
- **是否需要认证**：是

**路径参数**：id (Long, 必填, 设备ID)

**查询参数**：status (String, 必填, 设备状态 normal正常 maintenance维护 offline离线)

---

### 5.2 设备状态监控 (EquipmentStatusController) - 4个端点

#### 5.2.1 获取所有设备状态

- **路径**：`GET /equipment/status/all`
- **是否需要认证**：是

**响应格式**：List，含id、equipmentCode、equipmentName、status、statusDesc、isOnline、ipAddress、port、lastCommTime、commStatus、commStatusDesc

**响应示例**：
```json
{
  "code": 200,
  "message": "操作成功",
  "data": [
    {
      "id": 1,
      "equipmentCode": "EQ001",
      "equipmentName": "全自动生化分析仪1号",
      "status": "normal",
      "statusDesc": "正常",
      "isOnline": 1,
      "ipAddress": "192.168.1.101",
      "port": 5000,
      "commStatus": "unknown",
      "commStatusDesc": "未知"
    }
  ],
  "success": true
}
```

---

#### 5.2.2 获取单个设备状态

- **路径**：`GET /equipment/status/{equipmentId}`
- **是否需要认证**：是

**路径参数**：equipmentId (Long, 必填, 设备ID)

---

#### 5.2.3 更新设备在线状态

- **路径**：`PUT /equipment/status/{equipmentId}/online`
- **是否需要认证**：是

**路径参数**：equipmentId (Long, 必填, 设备ID)

**查询参数**：isOnline (Integer, 必填, 是否在线 0否 1是)

---

#### 5.2.4 更新设备通信时间

- **路径**：`PUT /equipment/status/{equipmentId}/comm`
- **是否需要认证**：是

**路径参数**：equipmentId (Long, 必填, 设备ID)

---

### 5.3 设备维护记录管理 (EquipmentMaintenanceController) - 7个端点

#### 5.3.1 分页查询维护记录

- **路径**：`GET /equipment/maintenance/page`
- **是否需要认证**：是

**查询参数**：pageNum(Integer, 否), pageSize(Integer, 否), equipmentId(Long, 否), maintenanceType(String, 否)

**响应格式**：分页结构，records含id、maintenanceNo、equipmentId、equipmentName、maintenanceType、maintenanceDate、maintenanceUserName、status

---

#### 5.3.2 根据ID查询维护记录

- **路径**：`GET /equipment/maintenance/{id}`
- **是否需要认证**：是

**路径参数**：id (Long, 必填, 维护记录ID)

---

#### 5.3.3 新增维护记录

- **路径**：`POST /equipment/maintenance`
- **是否需要认证**：是

**请求参数**：

| 字段名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| maintenanceNo | String | 是 | 维护编号 |
| planId | Long | 否 | 计划ID |
| planNo | String | 否 | 计划编号 |
| equipmentId | Long | 是 | 设备ID |
| equipmentCode | String | 否 | 设备编码 |
| equipmentName | String | 否 | 设备名称 |
| maintenanceType | String | 否 | 维护类型 |
| maintenanceDate | String | 是 | 维护日期 |
| startTime | String | 否 | 开始时间 |
| endTime | String | 否 | 结束时间 |
| maintenanceContent | String | 否 | 维护内容 |
| maintenanceResult | String | 否 | 维护结果 |
| maintenanceStatus | String | 否 | 维护后状态 |
| maintenanceUserId | Long | 否 | 维护人ID |
| maintenanceUserName | String | 否 | 维护人姓名 |
| cost | BigDecimal | 否 | 维护费用 |
| status | Integer | 否 | 状态 |
| remark | String | 否 | 备注 |

**响应格式**：data = Long (新创建的维护记录ID)

---

#### 5.3.4 更新维护记录

- **路径**：`PUT /equipment/maintenance`
- **是否需要认证**：是

**请求参数**：同5.3.3，增加id字段

---

#### 5.3.5 删除维护记录

- **路径**：`DELETE /equipment/maintenance/{id}`
- **是否需要认证**：是

**路径参数**：id (Long, 必填, 维护记录ID)

---

#### 5.3.6 批量删除维护记录

- **路径**：`DELETE /equipment/maintenance/batch`
- **是否需要认证**：是

**请求参数**：Body = Array\<Long\> (维护记录ID数组)

---

#### 5.3.7 完成维护

- **路径**：`PUT /equipment/maintenance/{id}/complete`
- **是否需要认证**：是

**路径参数**：id (Long, 必填, 维护记录ID)

---

### 5.4 设备校准记录管理 (EquipmentCalibrationController) - 7个端点

#### 5.4.1 分页查询校准记录

- **路径**：`GET /equipment/calibration/page`
- **是否需要认证**：是

**查询参数**：pageNum(Integer, 否), pageSize(Integer, 否), equipmentId(Long, 否), calibrationType(String, 否)

**响应格式**：分页结构，records含id、calibrationNo、equipmentId、equipmentName、calibrationType、calibrationDate、calibrationUserName、validEndDate、status

---

#### 5.4.2 根据ID查询校准记录

- **路径**：`GET /equipment/calibration/{id}`
- **是否需要认证**：是

**路径参数**：id (Long, 必填, 校准记录ID)

---

#### 5.4.3 新增校准记录

- **路径**：`POST /equipment/calibration`
- **是否需要认证**：是

**请求参数**：

| 字段名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| calibrationNo | String | 是 | 校准编号 |
| equipmentId | Long | 是 | 设备ID |
| equipmentCode | String | 否 | 设备编码 |
| equipmentName | String | 否 | 设备名称 |
| calibrationType | String | 否 | 校准类型 |
| calibrationDate | String | 是 | 校准日期 |
| calibrationOrg | String | 否 | 校准机构 |
| calibrationUserId | Long | 否 | 校准人ID |
| calibrationUserName | String | 否 | 校准人姓名 |
| calibrationMethod | String | 否 | 校准方法 |
| calibrationResult | String | 否 | 校准结果 |
| calibrationCertificate | String | 否 | 校准证书号 |
| validStartDate | String | 否 | 有效期开始日期 |
| validEndDate | String | 否 | 有效期结束日期 |
| calibrationReport | String | 否 | 校准报告路径 |
| calibrationCost | BigDecimal | 否 | 校准费用 |
| status | Integer | 否 | 状态 |
| remark | String | 否 | 备注 |

**响应格式**：data = Long (新创建的校准记录ID)

---

#### 5.4.4 更新校准记录

- **路径**：`PUT /equipment/calibration`
- **是否需要认证**：是

**请求参数**：同5.4.3，增加id字段

---

#### 5.4.5 删除校准记录

- **路径**：`DELETE /equipment/calibration/{id}`
- **是否需要认证**：是

**路径参数**：id (Long, 必填, 校准记录ID)

---

#### 5.4.6 批量删除校准记录

- **路径**：`DELETE /equipment/calibration/batch`
- **是否需要认证**：是

**请求参数**：Body = Array\<Long\> (校准记录ID数组)

---

#### 5.4.7 完成校准

- **路径**：`PUT /equipment/calibration/{id}/complete`
- **是否需要认证**：是

**路径参数**：id (Long, 必填, 校准记录ID)

---

## 6. HL7模块 (hl7)

> 请求前缀：`/hl7` | 共26个端点

### 6.1 HIS系统集成 (HisIntegrationController) - 5个端点

#### 6.1.1 发送患者入院消息

- **路径**：`POST /hl7/his-integration/patient-admit`
- **是否需要认证**：是

**查询参数**：interfaceCode (String, 必填, 接口编码)

**请求参数**：

| 字段名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| patient | Object | 否 | 患者信息 |
| visit | Object | 否 | 就诊信息 |

**响应格式**：含id、direction、messageType、processingStatus、rawMessage

---

#### 6.1.2 发送检验结果

- **路径**：`POST /hl7/his-integration/lab-result`
- **是否需要认证**：是

**查询参数**：interfaceCode (String, 必填, 接口编码)

**请求参数**：

| 字段名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| patient | Object | 否 | 患者信息 |
| visit | Object | 否 | 就诊信息 |
| observations | Array | 否 | 检验结果列表 |

---

#### 6.1.3 发送HIS检验申请

- **路径**：`POST /hl7/his-integration/lab-order`
- **是否需要认证**：是

**查询参数**：interfaceCode (String, 必填, 接口编码)

**请求参数**（HisLabOrderDTO - 扁平结构）：

| 字段名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| patientId | String | 否 | 患者ID |
| patientName | String | 否 | 患者姓名 |
| gender | String | 否 | 性别 |
| birthDate | String | 否 | 出生日期 |
| idCardNo | String | 否 | 身份证号 |
| phone | String | 否 | 联系电话 |
| address | String | 否 | 地址 |
| visitClass | String | 否 | 就诊类型 |
| department | String | 否 | 科室 |
| ward | String | 否 | 病区 |
| bedNo | String | 否 | 床号 |
| visitNo | String | 否 | 就诊号 |
| attendingDoctor | String | 否 | 主治医生 |
| clinicalInfo | String | 否 | 临床信息 |
| interfaceCode | String | 否 | 接口编码 |
| orderItems | Array | 否 | 检验项目列表（HisLabOrderItemDTO） |
| orderItems[].orderControl | String | 否 | 医嘱控制码（NW新医嘱） |
| orderItems[].placerOrderNo | String | 否 | 医嘱号 |
| orderItems[].orderItemCode | String | 否 | 检验项目编码 |
| orderItems[].orderItemName | String | 否 | 检验项目名称 |
| orderItems[].specimenType | String | 否 | 标本类型 |
| orderItems[].priority | String | 否 | 优先级 |
| orderItems[].collectionTime | String | 否 | 采集时间 |
| orderItems[].clinicalInfo | String | 否 | 临床信息 |
| orderItems[].orderingDoctor | String | 否 | 开单医生 |

**说明**：HisLabOrderDTO采用扁平结构（patientIdNo/patientName/patientGender/...），不是嵌套的patient/visit/orders结构。orders中每个item结构包含orderItemCode/specimenType/clinicalInfo等字段。

**请求示例**：
```json
{
  "patientId": "P20260001",
  "patientName": "testPatient",
  "gender": "1",
  "department": "LAB001",
  "clinicalInfo": "fever",
  "interfaceCode": "HIS_LAB_ORDER",
  "orderItems": [
    {
      "orderControl": "NW",
      "orderItemCode": "ALT",
      "orderItemName": "ALT",
      "specimenType": "blood",
      "priority": "R"
    }
  ]
}
```

**响应示例**：
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "id": 2043045206907748353,
    "direction": "inbound",
    "processingStatus": "pending",
    "rawMessage": "MSH|..."
  },
  "success": true
}
```

---

#### 6.1.4 发送自定义HL7消息

- **路径**：`POST /hl7/his-integration/custom-message`
- **是否需要认证**：是

**查询参数**：interfaceCode (String, 必填), messageType (String, 必填), triggerEvent (String, 必填)

**请求参数**：

| 字段名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| patient | Object | 否 | 患者信息 |
| visit | Object | 否 | 就诊信息 |
| orders | Array | 否 | 医嘱列表 |
| observations | Array | 否 | 检验结果列表 |

---

#### 6.1.5 发送原始HL7消息

- **路径**：`POST /hl7/his-integration/raw-message`
- **是否需要认证**：是

**查询参数**：interfaceCode (String, 必填, 接口编码)

**请求参数**：Body = String (原始HL7消息文本)

---

### 6.2 HL7消息管理 (Hl7MessageController) - 8个端点

#### 6.2.1 分页查询HL7消息

- **路径**：`GET /hl7/hl7-message/page`
- **是否需要认证**：是

**查询参数**：pageNum(Integer, 否), pageSize(Integer, 否), interfaceCode(String, 否), direction(String, 否), messageType(String, 否), triggerEvent(String, 否), messageControlId(String, 否), patientId(String, 否), patientName(String, 否), visitNo(String, 否), processStatus(String, 否), ackStatus(String, 否), startTime(String, 否), endTime(String, 否)

**响应格式**：分页结构，包含HL7消息列表

---

#### 6.2.2 获取HL7消息详情

- **路径**：`GET /hl7/hl7-message/{id}`
- **是否需要认证**：是

**路径参数**：id (Long, 必填, 消息ID)

**响应格式**：含id、interfaceCode、direction、messageType、triggerEvent、messageControlId、patientId、patientName、visitNo、processingStatus、rawMessage、segments（消息段列表）

---

#### 6.2.3 根据消息控制ID查询

- **路径**：`GET /hl7/hl7-message/by-control-id/{messageControlId}`
- **是否需要认证**：是

**路径参数**：messageControlId (String, 必填, 消息控制ID)

---

#### 6.2.4 根据患者ID查询消息列表

- **路径**：`GET /hl7/hl7-message/by-patient/{patientId}`
- **是否需要认证**：是

**路径参数**：patientId (String, 必填, 患者ID)

---

#### 6.2.5 根据就诊号查询消息列表

- **路径**：`GET /hl7/hl7-message/by-visit/{visitNo}`
- **是否需要认证**：是

**路径参数**：visitNo (String, 必填, 就诊号)

---

#### 6.2.6 重新处理消息

- **路径**：`POST /hl7/hl7-message/{id}/reprocess`
- **是否需要认证**：是

**路径参数**：id (Long, 必填, 消息ID)

---

#### 6.2.7 删除消息

- **路径**：`DELETE /hl7/hl7-message/{id}`
- **是否需要认证**：是

**路径参数**：id (Long, 必填, 消息ID)

---

#### 6.2.8 解析HL7消息

- **路径**：`POST /hl7/hl7-message/parse`
- **是否需要认证**：是

**请求参数**：

| 字段名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| rawMessage | String | 是 | 原始HL7消息文本 |

**响应格式**：data = Object (解析后的消息结构)

---

### 6.3 接口配置管理 (InterfaceConfigController) - 11个端点

#### 6.3.1 分页查询接口配置

- **路径**：`GET /hl7/interface-config/page`
- **是否需要认证**：是

**查询参数**：pageNum(Integer, 否), pageSize(Integer, 否)

**响应格式**：分页结构，包含接口配置列表

---

#### 6.3.2 获取接口配置详情

- **路径**：`GET /hl7/interface-config/{id}`
- **是否需要认证**：是

**路径参数**：id (Long, 必填, 配置ID)

---

#### 6.3.3 创建接口配置

- **路径**：`POST /hl7/interface-config`
- **是否需要认证**：是

**请求参数**：

| 字段名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| interfaceCode | String | 是 | 接口编码 |
| interfaceName | String | 是 | 接口名称 |
| interfaceType | String | 否 | 接口类型 |
| direction | String | 否 | 方向 |
| protocol | String | 否 | 协议 |
| host | String | 否 | 主机地址 |
| port | Integer | 否 | 端口 |
| charset | String | 否 | 字符集 |
| encoding | String | 否 | 编码 |
| connectionTimeout | Integer | 否 | 连接超时 |
| readTimeout | Integer | 否 | 读取超时 |
| retryCount | Integer | 否 | 重试次数 |
| retryInterval | Integer | 否 | 重试间隔 |
| ackMode | String | 否 | 应答模式 |
| messageType | String | 否 | 消息类型 |
| triggerEvent | String | 否 | 触发事件 |
| processingRuleId | String | 否 | 处理规则ID |
| transformRuleId | String | 否 | 转换规则ID |
| validationRuleId | String | 否 | 验证规则ID |
| isEnabled | Integer | 否 | 是否启用 |
| isAutoStart | Integer | 否 | 是否自动启动 |
| remark | String | 否 | 备注 |

**响应格式**：data = Long (新创建的配置ID)

---

#### 6.3.4 更新接口配置

- **路径**：`PUT /hl7/interface-config`
- **是否需要认证**：是

**请求参数**：同6.3.3，增加id字段

---

#### 6.3.5 删除接口配置

- **路径**：`DELETE /hl7/interface-config/{id}`
- **是否需要认证**：是

**路径参数**：id (Long, 必填, 配置ID)

---

#### 6.3.6 测试接口连接

- **路径**：`POST /hl7/interface-config/{id}/test`
- **是否需要认证**：是

**路径参数**：id (Long, 必填, 配置ID)

**响应格式**：data = Boolean (连接是否成功)

---

#### 6.3.7 启动接口

- **路径**：`POST /hl7/interface-config/{id}/start`
- **是否需要认证**：是

**路径参数**：id (Long, 必填, 配置ID)

---

#### 6.3.8 停止接口

- **路径**：`POST /hl7/interface-config/{id}/stop`
- **是否需要认证**：是

**路径参数**：id (Long, 必填, 配置ID)

---

#### 6.3.9 重启接口

- **路径**：`POST /hl7/interface-config/{id}/restart`
- **是否需要认证**：是

**路径参数**：id (Long, 必填, 配置ID)

---

#### 6.3.10 启用接口

- **路径**：`PUT /hl7/interface-config/{id}/enable`
- **是否需要认证**：是

**路径参数**：id (Long, 必填, 配置ID)

---

#### 6.3.11 禁用接口

- **路径**：`PUT /hl7/interface-config/{id}/disable`
- **是否需要认证**：是

**路径参数**：id (Long, 必填, 配置ID)

---

### 6.4 接口连接日志管理 (InterfaceConnectionLogController) - 2个端点

#### 6.4.1 分页查询接口连接日志

- **路径**：`POST /hl7/connection-log/list`
- **是否需要认证**：是

**查询参数**：pageNum(Integer, 否, 默认1), pageSize(Integer, 否, 默认10), interfaceCode(String, 否), eventType(String, 否)

**响应格式**：分页结构，包含连接日志列表

---

#### 6.4.2 根据接口编码查询连接日志

- **路径**：`GET /hl7/connection-log/byCode/{interfaceCode}`
- **是否需要认证**：是

**路径参数**：interfaceCode (String, 必填, 接口编码)

**响应格式**：List，连接日志列表

---

## 7. 统计模块 (statistics)

> 请求前缀：`/statistics` | 共25个端点

### 7.1 仪表盘统计 (DashboardController) - 2个端点

#### 7.1.1 获取仪表盘概览数据

- **路径**：`GET /statistics/dashboard/overview`
- **是否需要认证**：是

**请求参数**：无

**响应格式**：

| 字段名 | 类型 | 说明 |
|--------|------|------|
| data.todaySpecimenCount | Integer | 今日标本数 |
| data.todayReportCount | Integer | 今日报告数 |
| data.todayPanicCount | Integer | 今日危急值数 |
| data.todayOnlineEquipment | Integer | 今日在线设备数 |
| data.weeklySpecimenTrend | Array | 周标本趋势 |
| data.weeklyReportTrend | Array | 周报告趋势 |
| data.deptSpecimenDistribution | Array | 科室标本分布 |
| data.equipmentUsageRank | Array | 设备使用排名 |

**响应示例**：
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "todaySpecimenCount": 0,
    "todayReportCount": 0,
    "todayPanicCount": 0,
    "todayOnlineEquipment": 0,
    "weeklySpecimenTrend": [],
    "weeklyReportTrend": [],
    "deptSpecimenDistribution": [],
    "equipmentUsageRank": []
  },
  "success": true
}
```

---

#### 7.1.2 获取指定日期范围的概览数据

- **路径**：`GET /statistics/dashboard/overview/range`
- **是否需要认证**：是

**请求参数**：startTime(String, 否), endTime(String, 否)

**响应格式**：同7.1.1

---

### 7.2 工作量统计 (WorkloadStatController) - 5个端点

#### 7.2.1 获取每日工作量统计

- **路径**：`GET /statistics/workload/daily`
- **是否需要认证**：是

**请求参数**：startTime(String, 否), endTime(String, 否)

**响应格式**：List，工作量统计数据

---

#### 7.2.2 分页获取用户工作量统计

- **路径**：`GET /statistics/workload/user/page`
- **是否需要认证**：是

**请求参数**：startTime(String, 否), endTime(String, 否), pageNum(Integer, 否), pageSize(Integer, 否)

**响应格式**：分页结构

---

#### 7.2.3 获取科室工作量统计

- **路径**：`GET /statistics/workload/dept`
- **是否需要认证**：是

**请求参数**：startTime(String, 否), endTime(String, 否)

**响应格式**：List，科室工作量统计

---

#### 7.2.4 获取工作量趋势图表

- **路径**：`GET /statistics/workload/chart/trend`
- **是否需要认证**：是

**请求参数**：startTime(String, 否), endTime(String, 否)

**响应格式**：ECharts格式图表数据，含title、subtitle、legend、series

**响应示例**：
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "title": "工作量趋势图",
    "subtitle": "null 至 null",
    "legend": {
      "data": ["标本接收", "检验", "审核", "报告"]
    },
    "series": [
      {"name": "标本接收", "type": "line", "data": []}
    ]
  },
  "success": true
}
```

---

#### 7.2.5 获取工作量分布图表

- **路径**：`GET /statistics/workload/chart/distribution`
- **是否需要认证**：是

**请求参数**：startTime(String, 否), endTime(String, 否)

**响应格式**：ECharts格式图表数据

---

### 7.3 标本统计 (SpecimenStatController) - 6个端点

#### 7.3.1 获取每日标本统计

- **路径**：`GET /statistics/specimen/daily`
- **是否需要认证**：是

**请求参数**：startTime(String, 否), endTime(String, 否)

---

#### 7.3.2 分页获取科室标本统计

- **路径**：`GET /statistics/specimen/dept/page`
- **是否需要认证**：是

**请求参数**：startTime(String, 否), endTime(String, 否), pageNum(Integer, 否), pageSize(Integer, 否)

---

#### 7.3.3 获取标本类型统计

- **路径**：`GET /statistics/specimen/type`
- **是否需要认证**：是

**请求参数**：startTime(String, 否), endTime(String, 否)

---

#### 7.3.4 获取标本趋势图表

- **路径**：`GET /statistics/specimen/chart/trend`
- **是否需要认证**：是

**请求参数**：startTime(String, 否), endTime(String, 否)

**响应格式**：ECharts格式图表数据

---

#### 7.3.5 获取科室标本分布图表

- **路径**：`GET /statistics/specimen/chart/distribution`
- **是否需要认证**：是

**请求参数**：startTime(String, 否), endTime(String, 否)

**响应格式**：ECharts格式图表数据

---

#### 7.3.6 获取标本类型饼图

- **路径**：`GET /statistics/specimen/chart/type-pie`
- **是否需要认证**：是

**请求参数**：startTime(String, 否), endTime(String, 否)

**响应格式**：ECharts格式饼图数据

**响应示例**：
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "title": "标本类型分布图",
    "subtitle": "标本类型占比统计",
    "legend": {"data": [], "orient": "vertical"},
    "series": [{"name": "标本类型分布图", "type": "pie", "data": []}]
  },
  "success": true
}
```

---

### 7.4 设备统计 (EquipmentStatController) - 6个端点

#### 7.4.1 获取每日设备统计

- **路径**：`GET /statistics/equipment/daily`
- **是否需要认证**：是

**请求参数**：startTime(String, 否), endTime(String, 否)

---

#### 7.4.2 分页获取设备统计

- **路径**：`GET /statistics/equipment/page`
- **是否需要认证**：是

**请求参数**：startTime(String, 否), endTime(String, 否), pageNum(Integer, 否), pageSize(Integer, 否)

---

#### 7.4.3 获取设备趋势统计

- **路径**：`GET /statistics/equipment/trend`
- **是否需要认证**：是

**请求参数**：startTime(String, 否), endTime(String, 否)

---

#### 7.4.4 获取设备检验量图表

- **路径**：`GET /statistics/equipment/chart/test`
- **是否需要认证**：是

**请求参数**：startTime(String, 否), endTime(String, 否)

**响应格式**：ECharts格式图表数据

---

#### 7.4.5 获取设备质控图表

- **路径**：`GET /statistics/equipment/chart/qc`
- **是否需要认证**：是

**请求参数**：startTime(String, 否), endTime(String, 否)

**响应格式**：ECharts格式图表数据

---

#### 7.4.6 获取设备可用率图表

- **路径**：`GET /statistics/equipment/chart/uptime`
- **是否需要认证**：是

**请求参数**：startTime(String, 否), endTime(String, 否)

**响应格式**：ECharts格式图表数据

---

### 7.5 报告统计 (ReportStatController) - 6个端点

#### 7.5.1 获取每日报告统计

- **路径**：`GET /statistics/report/daily`
- **是否需要认证**：是

**请求参数**：startTime(String, 否), endTime(String, 否)

---

#### 7.5.2 分页获取检验项目统计

- **路径**：`GET /statistics/report/item/page`
- **是否需要认证**：是

**请求参数**：startTime(String, 否), endTime(String, 否), pageNum(Integer, 否), pageSize(Integer, 否)

---

#### 7.5.3 获取科室报告统计

- **路径**：`GET /statistics/report/dept`
- **是否需要认证**：是

**请求参数**：startTime(String, 否), endTime(String, 否)

---

#### 7.5.4 获取报告趋势图表

- **路径**：`GET /statistics/report/chart/trend`
- **是否需要认证**：是

**请求参数**：startTime(String, 否), endTime(String, 否)

**响应格式**：ECharts格式图表数据

---

#### 7.5.5 获取异常率图表

- **路径**：`GET /statistics/report/chart/abnormal`
- **是否需要认证**：是

**请求参数**：startTime(String, 否), endTime(String, 否)

**响应格式**：ECharts格式图表数据

---

#### 7.5.6 获取危急值分布图表

- **路径**：`GET /statistics/report/chart/panic`
- **是否需要认证**：是

**请求参数**：startTime(String, 否), endTime(String, 否)

**响应格式**：ECharts格式图表数据

---

## 8. AI模块 (ai)

> 请求前缀：`/ai` | 共16个端点

### 8.1 诊断规则管理 (DiagnosisRuleController) - 10个端点

#### 8.1.1 创建诊断规则

- **路径**：`POST /ai/rule`
- **是否需要认证**：是

**请求参数**：

| 字段名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| ruleCode | String | 是 | 规则编码 |
| ruleName | String | 是 | 规则名称 |
| ruleType | String | 否 | 规则类型（single单项 combined组合 panel组合） |
| category | String | 是 | 规则分类（anemia贫血 infection感染 liver肝脏 kidney肾脏等） |
| modelId | Long | 否 | 关联模型ID |
| testItemIds | String | 否 | 关联检验项目ID（多个逗号分隔） |
| ruleCondition | String | 否 | 规则条件（JSON格式） |
| ruleExpr | String | 否 | 规则表达式 |
| diagnosisTemplate | String | 否 | 诊断模板 |
| suggestionTemplate | String | 否 | 建议模板 |
| confidenceThreshold | BigDecimal | 否 | 置信度阈值，默认0.8 |
| priority | Integer | 否 | 优先级，默认0 |
| isEnabled | Integer | 否 | 是否启用（0否 1是），默认1 |
| remark | String | 否 | 备注 |
| items | Array | 否 | 规则明细列表 |
| items[].testItemId | Long | 否 | 检验项目ID |
| items[].itemCode | String | 否 | 项目编码 |
| items[].itemName | String | 否 | 项目名称 |
| items[].conditionType | String | 否 | 条件类型（range范围 compare比较 formula公式） |
| items[].conditionExpr | String | 否 | 条件表达式 |
| items[].weight | BigDecimal | 否 | 权重，默认1 |
| items[].sort | Integer | 否 | 排序，默认0 |

**响应格式**：data = Long (新创建的规则ID)

---

#### 8.1.2 更新诊断规则

- **路径**：`PUT /ai/rule`
- **是否需要认证**：是

**请求参数**：同8.1.1，增加id字段(Long, 规则ID)

---

#### 8.1.3 删除诊断规则

- **路径**：`DELETE /ai/rule/{id}`
- **是否需要认证**：是

**路径参数**：id (Long, 必填, 规则ID)

---

#### 8.1.4 获取规则详情

- **路径**：`GET /ai/rule/{id}`
- **是否需要认证**：是

**路径参数**：id (Long, 必填, 规则ID)

---

#### 8.1.5 根据规则编码获取详情

- **路径**：`GET /ai/rule/code/{ruleCode}`
- **是否需要认证**：是

**路径参数**：ruleCode (String, 必填, 规则编码)

---

#### 8.1.6 根据分类查询规则列表

- **路径**：`GET /ai/rule/category/{category}`
- **是否需要认证**：是

**路径参数**：category (String, 必填, 规则分类)

**响应格式**：List，规则列表

---

#### 8.1.7 获取所有启用的规则

- **路径**：`GET /ai/rule/enabled`
- **是否需要认证**：是

**响应格式**：List，启用的规则列表

---

#### 8.1.8 分页查询规则

- **路径**：`GET /ai/rule/page`
- **是否需要认证**：是

**查询参数**：ruleName(String, 否), category(String, 否), isEnabled(Integer, 否), pageNum(Integer, 否, 默认1), pageSize(Integer, 否, 默认10)

**响应格式**：分页结构，包含规则列表

---

#### 8.1.9 启用规则

- **路径**：`PUT /ai/rule/{id}/enable`
- **是否需要认证**：是

**路径参数**：id (Long, 必填, 规则ID)

---

#### 8.1.10 停用规则

- **路径**：`PUT /ai/rule/{id}/disable`
- **是否需要认证**：是

**路径参数**：id (Long, 必填, 规则ID)

---

### 8.2 AI诊断管理 (DiagnosisController) - 6个端点

#### 8.2.1 执行诊断分析

- **路径**：`POST /ai/diagnosis/analyze`
- **是否需要认证**：是

**请求参数**：

| 字段名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| reportId | Long | 否 | 报告ID |
| reportNo | String | 否 | 报告编号 |
| specimenId | Long | 否 | 标本ID |
| specimenNo | String | 否 | 标本编号 |
| patientId | Long | 否 | 患者ID |
| patientName | String | 否 | 患者姓名 |
| patientGender | String | 否 | 患者性别 |
| patientAge | String | 否 | 患者年龄 |
| diagnosisType | String | 是 | 诊断类型（blood_routine血常规 urine_routine尿常规 liver_function肝功能） |
| testData | Array | 是 | 检验项目数据列表 |
| testData[].itemId | Long | 否 | 检验项目ID |
| testData[].itemCode | String | 否 | 项目编码 |
| testData[].itemName | String | 否 | 项目名称 |
| testData[].resultValue | String | 是 | 结果值 |
| testData[].unit | String | 否 | 单位 |
| testData[].referenceLow | Double | 否 | 参考值下限 |
| testData[].referenceHigh | Double | 否 | 参考值上限 |
| testData[].isAbnormal | Integer | 否 | 是否异常（0正常 1异常） |
| saveRecord | Boolean | 否 | 是否保存诊断记录，默认true |

**请求示例**：
```json
{
  "reportId": 16,
  "diagnosisType": "blood_routine",
  "testData": [
    {
      "itemCode": "WBC",
      "itemName": "白细胞",
      "resultValue": "15.5",
      "unit": "10^9/L",
      "referenceLow": 4.0,
      "referenceHigh": 10.0,
      "isAbnormal": 1
    }
  ]
}
```

**响应格式**：

| 字段名 | 类型 | 说明 |
|--------|------|------|
| data.diagnosisNo | String | 诊断编号 |
| data.diagnosisType | String | 诊断类型 |
| data.conclusions | Array | 诊断结论列表 |
| data.conclusions[].ruleName | String | 规则名称 |
| data.conclusions[].conclusion | String | 结论描述 |
| data.conclusions[].confidence | BigDecimal | 置信度 |
| data.conclusions[].suggestion | String | 建议 |

---

#### 8.2.2 获取诊断记录详情

- **路径**：`GET /ai/diagnosis/record/{id}`
- **是否需要认证**：是

**路径参数**：id (Long, 必填, 诊断记录ID)

---

#### 8.2.3 根据诊断编号获取记录

- **路径**：`GET /ai/diagnosis/record/no/{diagnosisNo}`
- **是否需要认证**：是

**路径参数**：diagnosisNo (String, 必填, 诊断编号)

---

#### 8.2.4 查询诊断记录列表

- **路径**：`GET /ai/diagnosis/records`
- **是否需要认证**：是

**请求参数**：diagnosisType(String, 否), patientName(String, 否), reviewStatus(Integer, 否), pageNum(Integer, 否), pageSize(Integer, 否)

**响应格式**：分页结构，包含诊断记录列表

---

#### 8.2.5 审核诊断记录

- **路径**：`POST /ai/diagnosis/review`
- **是否需要认证**：是

**请求参数**：

| 字段名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| diagnosisId | Long | 是 | 诊断ID |
| reviewStatus | Integer | 是 | 审核状态（1已确认 2已拒绝） |
| reviewRemark | String | 否 | 审核备注 |

---

#### 8.2.6 删除诊断记录

- **路径**：`DELETE /ai/diagnosis/record/{id}`
- **是否需要认证**：是

**路径参数**：id (Long, 必填, 诊断记录ID)

---

## 9. 通用错误码

| 错误码 | 说明 |
|--------|------|
| 200 | 操作成功 |
| 400 | 请求参数错误 |
| 401 | 未认证/Token无效/Token已加入黑名单 |
| 403 | 无权限访问 |
| 404 | 资源不存在 |
| 500 | 服务器内部错误/BusinessException |
| 503 | 服务暂时不可用（Feign降级） |
| 2004 | 用户名或密码错误 |
| 2005 | 账号已锁定（连续5次登录失败后锁定15分钟） |

**Gateway安全机制**：
- Gateway在注入X-User-Id/X-User-Name请求头前会先移除已有的值（防伪造）
- logout后Token加入Redis黑名单（key格式：`blacklist:token:{完整token}`），旧Token请求返回401

**通用响应格式**：
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {},
  "timestamp": 1775934127059,
  "success": true
}
```

---

## 端点统计

| 模块 | 子模块 | 端点数 |
|------|--------|--------|
| 认证模块 (auth) | AuthController | 7 |
| 用户模块 (user) | UserController | 13 |
| 用户模块 (user) | DeptController | 8 |
| 用户模块 (user) | RoleController | 9 |
| 用户模块 (user) | MenuController | 10 |
| 用户模块 (user) | DictController | 14 |
| 用户模块 (user) | 日志管理 | 8 |
| 标本模块 (specimen) | SpecimenController | 17 |
| 报告模块 (report) | ReportApplyController | 7 |
| 报告模块 (report) | ReportAuditController | 7 |
| 报告模块 (report) | ResultEntryController | 4 |
| 报告模块 (report) | ReportPublishController | 3 |
| 报告模块 (report) | PatientController | 6 |
| 报告模块 (report) | PanicValueController | 5 |
| 设备模块 (equipment) | EquipmentController | 9 |
| 设备模块 (equipment) | EquipmentStatusController | 4 |
| 设备模块 (equipment) | EquipmentMaintenanceController | 7 |
| 设备模块 (equipment) | EquipmentCalibrationController | 7 |
| HL7模块 (hl7) | HisIntegrationController | 5 |
| HL7模块 (hl7) | Hl7MessageController | 8 |
| HL7模块 (hl7) | InterfaceConfigController | 11 |
| HL7模块 (hl7) | InterfaceConnectionLogController | 2 |
| 统计模块 (statistics) | DashboardController | 2 |
| 统计模块 (statistics) | WorkloadStatController | 5 |
| 统计模块 (statistics) | SpecimenStatController | 6 |
| 统计模块 (statistics) | EquipmentStatController | 6 |
| 统计模块 (statistics) | ReportStatController | 6 |
| AI模块 (ai) | DiagnosisRuleController | 10 |
| AI模块 (ai) | DiagnosisController | 6 |
| **合计** | | **179** |
