# Agent-02 用户服务Agent - 开发任务书

## 文档信息

| 项目名称 | 基于微服务架构的实验室管理系统（LIS） |
|---------|--------------------------------------|
| 文档版本 | V1.0.0 |
| 编写日期 | 2026年4月 |
| 任务书编号 | LIS-AGENT-02 |
| Agent编号 | Agent-02 |
| Agent角色 | 用户服务Agent |
| 负责模块 | 用户管理微服务（lis-user，端口8082） |
| 文档状态 | 正式发布 |

---

## 一、Agent基本信息

### 1.1 Agent概述

Agent-02（用户服务Agent）是LIS系统开发团队中负责用户与权限管理的核心角色。作为系统的"安全守门员"，用户服务Agent承担着整个平台的用户管理、角色权限控制、菜单管理、部门组织架构维护以及数据字典管理等基础性工作。用户服务Agent所构建的RBAC（基于角色的访问控制）权限体系，是系统安全运行的基础保障，直接决定了系统的安全性、灵活性和可管理性。所有业务微服务的权限校验均依赖于本Agent提供的用户身份与权限数据。

### 1.2 Agent职责范围

用户服务Agent的职责覆盖以下核心领域：

1. **用户管理**：实现系统用户的完整生命周期管理，包括用户的创建、查询、修改、删除、启用/停用以及密码重置等功能。
2. **角色管理**：实现系统角色的CRUD操作以及角色与菜单权限的分配管理，构建灵活的RBAC权限模型。
3. **菜单管理**：实现系统菜单的树形结构管理，支持多级菜单和按钮级权限的配置。
4. **部门管理**：实现组织部门的树形结构管理，支持多级部门层级和部门人员关联。
5. **字典管理**：实现数据字典类型和数据字典项的管理，为系统各模块提供统一的下拉选项数据源。
6. **用户参数设置**：实现用户个性化参数配置，包括密码策略、会话超时等系统级参数管理。

### 1.3 协作关系

| 协作对象 | 协作内容 |
|---------|---------|
| Agent-00（架构师） | 依赖lis-common公共模块、Feign客户端接口、数据库初始化脚本 |
| Agent-01（前端工程师） | 提供用户管理、角色管理、部门管理、字典管理相关REST API接口 |
| Agent-03（标本服务） | 提供用户身份校验、操作权限验证接口（Feign调用） |
| Agent-04（检验服务） | 提供用户身份校验、审核权限验证接口（Feign调用） |
| Agent-05（设备服务） | 提供用户身份校验、设备管理权限验证接口（Feign调用） |
| Agent-06（HL7服务） | 提供用户身份校验接口（Feign调用） |
| Agent-07（统计服务） | 提供部门组织架构数据、用户信息查询接口（Feign调用） |
| Agent-08（AI服务） | 提供用户身份校验接口（Feign调用） |

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
| MyBatis-Plus | 3.5.5 | ORM框架，简化数据库操作 |
| MySQL | 8.0 | 关系型数据库（lis_user库） |
| Redis | 7.0 | 缓存用户会话、权限数据、字典数据 |
| BCrypt | Spring Security内置 | 密码加密与验证算法 |
| MapStruct | 1.5.5.Final | 对象映射工具，DTO与Entity转换 |
| Hutool | 5.8.25 | Java工具类库 |
| Lombok | 1.18.30 | 代码简化工具 |

### 2.2 数据库信息

| 数据库名称 | 字符集 | 排序规则 | 说明 |
|-----------|--------|---------|------|
| lis_user | utf8mb4 | utf8mb4_general_ci | 用户与权限管理数据库 |

**核心数据表**：

| 表名 | 说明 | 关键字段 |
|------|------|---------|
| sys_user | 系统用户表 | id, username, password, real_name, phone, email, dept_id, status |
| sys_role | 系统角色表 | id, role_name, role_code, sort, status |
| sys_menu | 系统菜单表 | id, menu_name, parent_id, path, component, menu_type, permission, sort |
| sys_user_role | 用户角色关联表 | id, user_id, role_id |
| sys_role_menu | 角色菜单关联表 | id, role_id, menu_id |
| sys_dept | 系统部门表 | id, dept_name, parent_id, sort, leader, status |
| sys_dict | 字典类型表 | id, dict_name, dict_type, status |
| sys_dict_item | 字典数据表 | id, dict_type, dict_label, dict_value, sort, status |

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

### 任务一：用户CRUD管理

#### 1.1 用户列表分页查询

**目标**：实现系统用户的分页查询功能，支持多条件组合搜索。

**接口定义**：

```
GET /api/user/users
Authorization: Bearer {accessToken}
```

**请求参数**：

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| username | String | 否 | 用户名（模糊搜索） |
| realName | String | 否 | 真实姓名（模糊搜索） |
| phone | String | 否 | 手机号（模糊搜索） |
| status | Integer | 否 | 状态（0-禁用，1-正常） |
| deptId | Long | 否 | 部门ID（精确匹配，含子部门） |
| beginTime | String | 否 | 创建时间起始（yyyy-MM-dd HH:mm:ss） |
| endTime | String | 否 | 创建时间结束（yyyy-MM-dd HH:mm:ss） |
| pageNum | Integer | 否 | 页码（默认1） |
| pageSize | Integer | 否 | 每页条数（默认10） |

**响应参数**：

| 字段 | 类型 | 说明 |
|------|------|------|
| total | Long | 总记录数 |
| rows | List\<UserVO\> | 用户列表 |
| pageNum | Integer | 当前页码 |
| pageSize | Integer | 每页条数 |

**UserVO字段**：

| 字段 | 类型 | 说明 |
|------|------|------|
| id | Long | 用户ID |
| username | String | 用户名 |
| realName | String | 真实姓名 |
| phone | String | 手机号 |
| email | String | 邮箱 |
| deptId | Long | 部门ID |
| deptName | String | 部门名称 |
| roleList | List\<RoleVO\> | 角色列表 |
| status | Integer | 状态 |
| createTime | String | 创建时间 |

**技术要点**：

- 使用MyBatis-Plus的 `Page` 对象实现分页查询。
- 部门查询需递归查询所有子部门ID，使用 `IN` 条件匹配。
- 用户名和真实姓名使用 `LIKE` 模糊查询，前后加 `%`。
- 手机号使用 `LIKE` 右模糊查询（`phone%`），利用索引提升性能。
- 查询结果需关联查询部门名称和角色列表，使用MyBatis-Plus的 `@TableField(exist = false)` 标注非数据库字段。
- 密码字段在查询结果中不得返回，使用 `@JsonIgnore` 注解排除。

#### 1.2 用户新增

**接口定义**：

```
POST /api/user/users
Authorization: Bearer {accessToken}
Content-Type: application/json
```

**请求参数**：

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| username | String | 是 | 用户名（4-20位字母数字下划线，唯一） |
| password | String | 是 | 密码（不少于8位，包含字母和数字） |
| realName | String | 是 | 真实姓名 |
| phone | String | 否 | 手机号（11位，唯一） |
| email | String | 否 | 邮箱（唯一） |
| deptId | Long | 是 | 部门ID |
| roleIds | List\<Long\> | 是 | 角色ID列表 |
| status | Integer | 否 | 状态（默认0-启用） |

**业务逻辑**：

1. 参数校验：用户名格式、密码强度、手机号格式、邮箱格式。
2. 用户名唯一性校验：查询 `sys_user` 表中是否已存在相同用户名。
3. 手机号唯一性校验（如提供）：查询 `sys_user` 表中是否已存在相同手机号。
4. 邮箱唯一性校验（如提供）：查询 `sys_user` 表中是否已存在相同邮箱。
5. 部门存在性校验：查询 `sys_dept` 表中是否存在指定部门。
6. 角色存在性校验：查询 `sys_role` 表中是否存在所有指定的角色。
7. 密码加密：使用BCrypt算法对密码进行加密（`BCryptPasswordEncoder.encode()`）。
8. 插入 `sys_user` 表，记录创建人和创建时间。
9. 批量插入 `sys_user_role` 关联表，建立用户与角色的关联关系。
10. 清除该用户相关的缓存数据（如有）。

**技术要点**：

- 使用 `@Validated` 注解实现参数校验，自定义校验注解处理唯一性验证。
- BCrypt加密每次生成不同的盐值，同一密码多次加密结果不同，安全性高。
- 用户创建和角色关联需在同一事务中完成，使用 `@Transactional` 注解。
- 创建人信息从请求头的用户上下文中获取（通过 `ThreadLocal` 或请求头传递）。

#### 1.3 用户修改

**接口定义**：

```
PUT /api/user/users/{userId}
Authorization: Bearer {accessToken}
Content-Type: application/json
```

**请求参数**：

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| realName | String | 否 | 真实姓名 |
| phone | String | 否 | 手机号 |
| email | String | 否 | 邮箱 |
| deptId | Long | 否 | 部门ID |
| roleIds | List\<Long\> | 否 | 角色ID列表 |
| status | Integer | 否 | 状态 |

**业务逻辑**：

1. 校验用户ID是否存在。
2. 手机号唯一性校验（如修改了手机号）：排除当前用户后检查。
3. 邮箱唯一性校验（如修改了邮箱）：排除当前用户后检查。
4. 更新 `sys_user` 表，记录更新人和更新时间。
5. 如角色列表有变更，先删除原有关联记录，再批量插入新的关联记录。
6. 清除该用户的权限缓存。

**技术要点**：

- 修改操作需使用乐观锁或版本号机制防止并发修改冲突。
- 角色变更属于敏感操作，需记录操作日志。
- 修改后需同步更新Redis中该用户的权限缓存。

#### 1.4 用户删除

**接口定义**：

```
DELETE /api/user/users/{userId}
Authorization: Bearer {accessToken}
```

**业务逻辑**：

1. 校验用户ID是否存在。
2. 不允许删除超级管理员账户（userId = 1）。
3. 不允许删除当前登录用户自身。
4. 执行逻辑删除：将 `sys_user` 表的 `deleted` 字段设置为1。
5. 同步逻辑删除 `sys_user_role` 关联记录。
6. 清除该用户的缓存数据和Token。

**技术要点**：

- 使用MyBatis-Plus的逻辑删除功能，配置 `@TableLogic` 注解。
- 逻辑删除后，该用户将无法登录系统。
- 删除操作需记录审计日志，包含操作人、操作时间、被删除用户信息。

#### 1.5 用户启用/停用

**接口定义**：

```
PUT /api/user/users/{userId}/status
Authorization: Bearer {accessToken}
Content-Type: application/json
```

**请求参数**：

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| status | Integer | 是 | 目标状态（0-禁用，1-正常） |

**业务逻辑**：

1. 校验用户ID是否存在。
2. 不允许停用超级管理员账户。
3. 不允许停用当前登录用户自身。
4. 更新用户状态。
5. 停用用户时，清除其Token和权限缓存，强制其重新登录。
6. 记录状态变更日志。

#### 1.6 密码重置

**接口定义**：

```
PUT /api/user/users/{userId}/reset-password
Authorization: Bearer {accessToken}
```

**业务逻辑**：

1. 校验用户ID是否存在。
2. 生成默认密码（如：`Lis@2026`），使用BCrypt加密。
3. 更新 `sys_user` 表的密码字段。
4. 清除该用户的Token缓存，强制其使用新密码重新登录。
5. 记录密码重置日志（操作人、被重置用户、重置时间）。
6. 返回新密码（仅返回一次，前端提示用户妥善保管）。

**技术要点**：

- 密码重置为高危操作，建议增加二次确认机制。
- 密码重置后应通过安全渠道通知用户（如系统消息），提醒其尽快修改密码。
- 密码历史记录校验（可选）：新密码不得与最近N次密码相同。

**验收标准**：

- 用户列表分页查询功能正常，多条件组合搜索结果准确。
- 用户新增功能正常，参数校验完整，密码加密存储。
- 用户修改功能正常，唯一性校验有效。
- 用户删除为逻辑删除，删除后用户无法登录。
- 用户启用/停用功能正常，停用后强制重新登录。
- 密码重置功能正常，新密码加密存储，旧Token失效。
- 所有接口返回格式符合API接口设计规范。

---

### 任务二：角色管理

#### 2.1 角色CRUD

**目标**：实现系统角色的增删改查功能。

**角色列表查询接口**：

```
GET /api/user/roles
```

**请求参数**：

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| roleName | String | 否 | 角色名称（模糊搜索） |
| roleCode | String | 否 | 角色编码（精确匹配） |
| status | Integer | 否 | 状态 |
| pageNum | Integer | 否 | 页码 |
| pageSize | Integer | 否 | 每页条数 |

**角色新增接口**：

```
POST /api/user/roles
Content-Type: application/json
```

**请求参数**：

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| roleName | String | 是 | 角色名称（2-20字符，唯一） |
| roleCode | String | 是 | 角色编码（如admin、doctor，唯一） |
| sort | Integer | 否 | 排序号（默认0） |
| status | Integer | 否 | 状态（默认0-启用） |
| remark | String | 否 | 备注 |

**业务逻辑**：

1. 角色名称和角色编码唯一性校验。
2. 角色编码格式校验（小写字母、数字、下划线）。
3. 插入 `sys_role` 表。
4. 记录操作日志。

**角色修改接口**：

```
PUT /api/user/roles/{roleId}
```

**角色删除接口**：

```
DELETE /api/user/roles/{roleId}
```

**删除约束**：

- 不允许删除超级管理员角色（roleId = 1）。
- 不允许删除已分配给用户的角色（需先解除用户关联）。
- 执行逻辑删除。
- 同步删除 `sys_role_menu` 关联记录。

#### 2.2 角色菜单权限分配

**目标**：实现角色与菜单权限的关联管理，支持树形权限分配。

**获取角色已分配菜单接口**：

```
GET /api/user/roles/{roleId}/menus
```

**响应参数**：

| 字段 | 类型 | 说明 |
|------|------|------|
| checkedKeys | List\<Long\> | 已选中的菜单ID列表（叶子节点） |
| halfCheckedKeys | List\<Long\> | 半选中的菜单ID列表（父节点） |

**保存角色菜单权限接口**：

```
PUT /api/user/roles/{roleId}/menus
Content-Type: application/json
```

**请求参数**：

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| menuIds | List\<Long\> | 是 | 菜单ID列表（包含父节点和子节点） |

**业务逻辑**：

1. 校验角色ID是否存在。
2. 校验所有菜单ID是否有效。
3. 先删除 `sys_role_menu` 表中该角色的所有关联记录。
4. 批量插入新的角色菜单关联记录。
5. 清除拥有该角色的所有用户的权限缓存。
6. 记录权限变更日志。

**技术要点**：

- 前端使用Element Plus的Tree组件展示菜单权限树，支持全选/半选。
- 保存时需同时保存父节点和子节点的ID，以便正确还原树的选中状态。
- 权限缓存使用Redis存储，Key格式为 `lis:permission:{userId}`，Value为权限标识列表。
- 权限变更后需通过Redis的发布/订阅机制通知相关用户刷新权限。

**验收标准**：

- 角色CRUD功能正常，唯一性校验有效。
- 角色删除时正确检查用户关联约束。
- 菜单权限树正确展示，全选/半选/取消选中功能正常。
- 权限保存后，拥有该角色的用户权限实时更新。
- 权限缓存正确维护，缓存失效后自动刷新。

---

### 任务三：菜单管理

#### 3.1 菜单树形结构CRUD

**目标**：实现系统菜单的树形结构管理，支持多级菜单和按钮级权限。

**菜单树查询接口**：

```
GET /api/user/menus/tree
```

**响应参数**：

返回树形结构的菜单列表，每个节点包含：

| 字段 | 类型 | 说明 |
|------|------|------|
| id | Long | 菜单ID |
| menuName | String | 菜单名称 |
| parentId | Long | 父菜单ID（顶级菜单为0） |
| path | String | 路由路径 |
| component | String | 前端组件路径 |
| menuType | String | 菜单类型（M-目录，C-菜单，F-按钮） |
| permission | String | 权限标识（如user:list、user:create） |
| icon | String | 菜单图标 |
| sort | Integer | 排序号 |
| visible | Integer | 是否可见（0-显示，1-隐藏） |
| status | Integer | 状态 |
| children | List\<MenuVO\> | 子菜单列表 |

**菜单新增接口**：

```
POST /api/user/menus
Content-Type: application/json
```

**请求参数**：

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| menuName | String | 是 | 菜单名称 |
| parentId | Long | 是 | 父菜单ID（顶级菜单传0） |
| path | String | 否 | 路由路径 |
| component | String | 否 | 前端组件路径 |
| menuType | String | 是 | 菜单类型（M/C/F） |
| permission | String | 否 | 权限标识（按钮类型必填） |
| icon | String | 否 | 菜单图标 |
| sort | Integer | 否 | 排序号（默认0） |
| visible | Integer | 否 | 是否可见（默认0） |
| status | Integer | 否 | 状态（默认0） |

**业务逻辑**：

1. 父菜单存在性校验（parentId不为0时）。
2. 菜单类型校验：目录类型不能有component和permission；菜单类型必须有path和component；按钮类型必须有permission。
3. 权限标识唯一性校验（如提供）。
4. 插入 `sys_menu` 表。
5. 如修改了菜单结构，需清除所有角色的菜单缓存。

**菜单修改接口**：

```
PUT /api/user/menus/{menuId}
```

**菜单删除接口**：

```
DELETE /api/user/menus/{menuId}
```

**删除约束**：

- 存在子菜单时不允许删除，需先删除所有子菜单。
- 存在角色关联时不允许删除，需先解除角色关联。
- 执行物理删除（菜单表不使用逻辑删除）。

**技术要点**：

- 树形结构查询使用递归算法或MyBatis-Plus的 `Tree` 工具类。
- 菜单层级建议不超过4级（目录 -> 菜单 -> 子菜单 -> 按钮）。
- 菜单排序按 `sort` 字段升序排列，同级菜单按排序号显示。
- 按钮类型菜单（menuType = F）在前端不显示为导航项，仅作为权限标识使用。
- 菜单数据变更频率低，建议在Redis中缓存完整的菜单树，设置较长的过期时间（如2小时），变更时主动清除缓存。

**验收标准**：

- 菜单树正确展示多级层级关系。
- 菜单CRUD功能正常，类型校验完整。
- 菜单删除时正确检查子菜单和角色关联约束。
- 菜单排序功能正常，同级菜单按排序号正确排列。
- 菜单缓存正确维护。

---

### 任务四：部门管理

#### 4.1 部门树形结构CRUD

**目标**：实现组织部门的树形结构管理，支持多级部门层级。

**部门树查询接口**：

```
GET /api/user/depts/tree
```

**响应参数**：

返回树形结构的部门列表，每个节点包含：

| 字段 | 类型 | 说明 |
|------|------|------|
| id | Long | 部门ID |
| deptName | String | 部门名称 |
| parentId | Long | 父部门ID（顶级部门为0） |
| deptCode | String | 部门编码 |
| leader | String | 负责人 |
| phone | String | 联系电话 |
| email | String | 邮箱 |
| sort | Integer | 排序号 |
| status | Integer | 状态 |
| children | List\<DeptVO\> | 子部门列表 |

**部门新增接口**：

```
POST /api/user/depts
Content-Type: application/json
```

**请求参数**：

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| deptName | String | 是 | 部门名称（2-30字符） |
| parentId | Long | 是 | 父部门ID（顶级部门传0） |
| deptCode | String | 是 | 部门编码（唯一） |
| leader | String | 否 | 负责人 |
| phone | String | 否 | 联系电话 |
| email | String | 否 | 邮箱 |
| sort | Integer | 否 | 排序号（默认0） |
| status | Integer | 否 | 状态（默认0） |

**业务逻辑**：

1. 部门名称在同一父部门下唯一性校验。
2. 部门编码全局唯一性校验。
3. 父部门存在性校验（parentId不为0时）。
4. 插入 `sys_dept` 表。

**部门修改接口**：

```
PUT /api/user/depts/{deptId}
```

**部门删除接口**：

```
DELETE /api/user/depts/{deptId}
```

**删除约束**：

- 存在子部门时不允许删除，需先删除或迁移所有子部门。
- 存在关联用户时不允许删除，需先将用户迁移到其他部门。
- 执行逻辑删除。

**部门用户查询接口**：

```
GET /api/user/depts/{deptId}/users
```

**功能说明**：查询指定部门下的所有用户列表（包含子部门的用户），支持分页。

**技术要点**：

- 部门树查询使用递归算法，对于大数据量可考虑使用闭包表（Closure Table）优化查询性能。
- 部门编码建议使用层级编码规则（如：01、01.01、01.01.01），便于排序和层级识别。
- 部门数据变更频率低，建议缓存部门树数据。
- 部门删除需级联检查子部门和关联用户，确保数据完整性。

**验收标准**：

- 部门树正确展示多级层级关系。
- 部门CRUD功能正常，唯一性校验有效。
- 部门删除时正确检查子部门和用户关联约束。
- 部门用户查询功能正常，包含子部门用户。
- 部门缓存正确维护。

---

### 任务五：字典管理

#### 5.1 字典类型CRUD

**目标**：实现数据字典类型的管理，为系统各模块提供统一的下拉选项分类。

**字典类型列表查询接口**：

```
GET /api/user/dicts
```

**请求参数**：

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| dictName | String | 否 | 字典名称（模糊搜索） |
| dictType | String | 否 | 字典类型（精确匹配） |
| status | Integer | 否 | 状态 |
| pageNum | Integer | 否 | 页码 |
| pageSize | Integer | 否 | 每页条数 |

**字典类型新增接口**：

```
POST /api/user/dicts
Content-Type: application/json
```

**请求参数**：

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| dictName | String | 是 | 字典名称（如"用户状态"、"标本类型"） |
| dictType | String | 是 | 字典类型（如"sys_user_status"、"specimen_type"，唯一） |
| status | Integer | 否 | 状态（默认0） |
| remark | String | 否 | 备注 |

**业务逻辑**：

1. 字典类型唯一性校验。
2. 字典类型格式校验（小写字母、数字、下划线）。
3. 插入 `sys_dict` 表。
4. 清除字典缓存。

**字典类型修改接口**：

```
PUT /api/user/dicts/{dictId}
```

**字典类型删除接口**：

```
DELETE /api/user/dicts/{dictId}
```

**删除约束**：

- 存在字典数据项时不允许删除，需先删除所有字典数据项。
- 执行逻辑删除。

#### 5.2 字典项CRUD

**目标**：实现数据字典项的管理，为系统各模块提供具体的下拉选项数据。

**字典项列表查询接口**：

```
GET /api/user/dict-items
```

**请求参数**：

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| dictType | String | 是 | 字典类型 |
| dictLabel | String | 否 | 字典标签（模糊搜索） |
| status | Integer | 否 | 状态 |

**字典项新增接口**：

```
POST /api/user/dict-items
Content-Type: application/json
```

**请求参数**：

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| dictType | String | 是 | 字典类型 |
| dictLabel | String | 是 | 字典标签（显示值） |
| dictValue | String | 是 | 字典值（存储值） |
| sort | Integer | 否 | 排序号（默认0） |
| status | Integer | 否 | 状态（默认0） |
| remark | String | 否 | 备注 |

**业务逻辑**：

1. 字典类型存在性校验。
2. 同一字典类型下，字典值唯一性校验。
3. 插入 `sys_dict_item` 表。
4. 清除该字典类型的缓存。

**字典项修改接口**：

```
PUT /api/user/dict-items/{itemId}
```

**字典项删除接口**：

```
DELETE /api/user/dict-items/{itemId}
```

**字典数据缓存刷新接口**：

```
DELETE /api/user/dicts/cache
```

**功能说明**：清除Redis中所有字典数据的缓存，下次查询时自动从数据库加载并缓存。

**技术要点**：

- 字典数据使用Redis缓存，Key格式为 `lis:dict:{dictType}`，Value为该类型下的所有字典项列表。
- 字典数据变更时需同步更新缓存，确保数据一致性。
- 提供根据字典类型批量查询字典项的接口，供其他微服务通过Feign调用。
- 字典数据初始化：系统启动时预加载常用字典数据到Redis。

**验收标准**：

- 字典类型CRUD功能正常，唯一性校验有效。
- 字典项CRUD功能正常，同类型下字典值唯一性校验有效。
- 字典删除时正确检查关联数据项约束。
- 字典缓存正确维护，变更后缓存实时更新。
- 缓存刷新接口功能正常。

---

### 任务六：用户参数设置

#### 6.1 系统参数管理

**目标**：实现系统级参数的配置管理，包括密码策略、会话超时等。

**参数查询接口**：

```
GET /api/user/configs
```

**响应参数**：

返回系统参数键值对列表：

| 字段 | 类型 | 说明 |
|------|------|------|
| configKey | String | 参数键名 |
| configValue | String | 参数值 |
| configName | String | 参数中文名称 |
| configType | String | 参数类型（system-系统内置，custom-自定义） |

**参数修改接口**：

```
PUT /api/user/configs
Content-Type: application/json
```

**请求参数**：

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| configKey | String | 是 | 参数键名 |
| configValue | String | 是 | 参数值 |

**预置系统参数**：

| 参数键名 | 参数名称 | 默认值 | 说明 |
|---------|---------|--------|------|
| sys.password.min-length | 密码最小长度 | 8 | 密码最少字符数 |
| sys.password.require-uppercase | 密码要求大写字母 | false | 是否要求包含大写字母 |
| sys.password.require-number | 密码要求数字 | true | 是否要求包含数字 |
| sys.password.require-special | 密码要求特殊字符 | false | 是否要求包含特殊字符 |
| sys.password.validity-days | 密码有效期（天） | 90 | 密码过期天数，0为永不过期 |
| sys.login.max-retry | 登录最大重试次数 | 5 | 连续登录失败锁定阈值 |
| sys.login.lock-minutes | 登录锁定时间（分钟） | 30 | 锁定持续时间 |
| sys.session.timeout-minutes | 会话超时时间（分钟） | 120 | Token有效期 |
| sys.user.init-password | 用户初始密码 | Lis@2026 | 新用户或密码重置后的默认密码 |

**技术要点**：

- 系统参数使用Redis缓存，启动时加载到内存。
- 参数变更时同步更新Redis缓存。
- 内置参数（configType = system）不允许删除，仅允许修改值。
- 参数变更需记录审计日志。

#### 6.2 用户个人信息管理

**目标**：实现用户查看和修改个人基本信息的功能。

**获取当前用户信息接口**：

```
GET /api/user/profile
Authorization: Bearer {accessToken}
```

**修改个人信息接口**：

```
PUT /api/user/profile
Authorization: Bearer {accessToken}
Content-Type: application/json
```

**请求参数**：

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| realName | String | 否 | 真实姓名 |
| phone | String | 否 | 手机号 |
| email | String | 否 | 邮箱 |
| avatar | String | 否 | 头像URL |

**修改密码接口**：

```
PUT /api/user/profile/password
Authorization: Bearer {accessToken}
Content-Type: application/json
```

**请求参数**：

| 字段 | 类型 | 必填 | 说明 |
|------|------|------|------|
| oldPassword | String | 是 | 原密码 |
| newPassword | String | 是 | 新密码（需符合密码策略） |
| confirmPassword | String | 是 | 确认密码（需与新密码一致） |

**业务逻辑**：

1. 验证原密码是否正确（BCrypt比对）。
2. 新密码需符合系统密码策略（长度、复杂度）。
3. 新密码不得与原密码相同。
4. 使用BCrypt加密新密码并更新。
5. 清除当前用户的Token缓存，强制重新登录。
6. 记录密码修改日志。

**验收标准**：

- 系统参数查询和修改功能正常。
- 内置参数不可删除，仅允许修改值。
- 用户个人信息查看和修改功能正常。
- 修改密码功能正常，原密码验证、密码策略校验、密码加密存储均有效。
- 修改密码后强制重新登录。

---

## 四、输出路径与交付物

### 4.1 源代码输出

| 输出内容 | 路径 |
|---------|------|
| 用户服务源代码 | `/workspace/lis-project/src/backend/lis-user/` |
| Controller层 | `/workspace/lis-project/src/backend/lis-user/src/main/java/com/lis/user/controller/` |
| Service层 | `/workspace/lis-project/src/backend/lis-user/src/main/java/com/lis/user/service/` |
| Mapper层 | `/workspace/lis-project/src/backend/lis-user/src/main/java/com/lis/user/mapper/` |
| Entity层 | `/workspace/lis-project/src/backend/lis-user/src/main/java/com/lis/user/entity/` |
| DTO/VO层 | `/workspace/lis-project/src/backend/lis-user/src/main/java/com/lis/user/dto/` |
| Mapper XML | `/workspace/lis-project/src/backend/lis-user/src/main/resources/mapper/` |

### 4.2 项目结构要求

```
lis-user/
├── pom.xml
└── src/main/java/com/lis/user/
    ├── LisUserApplication.java          # 启动类
    ├── controller/
    │   ├── UserController.java          # 用户管理接口
    │   ├── RoleController.java          # 角色管理接口
    │   ├── MenuController.java          # 菜单管理接口
    │   ├── DeptController.java          # 部门管理接口
    │   ├── DictController.java          # 字典管理接口
    │   └── ProfileController.java       # 个人信息接口
    ├── service/
    │   ├── SysUserService.java          # 用户服务接口
    │   ├── SysRoleService.java          # 角色服务接口
    │   ├── SysMenuService.java          # 菜单服务接口
    │   ├── SysDeptService.java          # 部门服务接口
    │   ├── SysDictService.java          # 字典服务接口
    │   └── impl/
    │       ├── SysUserServiceImpl.java
    │       ├── SysRoleServiceImpl.java
    │       ├── SysMenuServiceImpl.java
    │       ├── SysDeptServiceImpl.java
    │       └── SysDictServiceImpl.java
    ├── mapper/
    │   ├── SysUserMapper.java
    │   ├── SysRoleMapper.java
    │   ├── SysMenuMapper.java
    │   ├── SysDeptMapper.java
    │   ├── SysDictMapper.java
    │   ├── SysDictItemMapper.java
    │   ├── SysUserRoleMapper.java
    │   └── SysRoleMenuMapper.java
    ├── entity/
    │   ├── SysUser.java
    │   ├── SysRole.java
    │   ├── SysMenu.java
    │   ├── SysDept.java
    │   ├── SysDict.java
    │   ├── SysDictItem.java
    │   ├── SysUserRole.java
    │   └── SysRoleMenu.java
    ├── dto/
    │   ├── UserDTO.java
    │   ├── RoleDTO.java
    │   ├── MenuDTO.java
    │   ├── DeptDTO.java
    │   ├── DictDTO.java
    │   └── DictItemDTO.java
    ├── vo/
    │   ├── UserVO.java
    │   ├── RoleVO.java
    │   ├── MenuVO.java
    │   ├── DeptVO.java
    │   ├── DictVO.java
    │   └── DictItemVO.java
    ├── config/
    │   └── MyBatisPlusConfig.java       # MyBatis-Plus配置
    └── converter/
        ├── UserConverter.java            # MapStruct转换器
        ├── RoleConverter.java
        ├── MenuConverter.java
        ├── DeptConverter.java
        └── DictConverter.java
```

### 4.3 开发报告

| 输出内容 | 路径 |
|---------|------|
| 用户服务开发报告 | `/workspace/lis-project/reports/agent-reports/Agent-02-用户服务-开发报告.md` |

---

## 五、技术约束与规范要求

### 5.1 编码规范

- 所有Java代码必须符合 `/workspace/lis-project/docs/05-开发规范/编码规范.md` 中的后端编码规范。
- 包命名使用 `com.lis.user` 格式。
- 类命名使用大驼峰（UpperCamelCase），方法命名使用小驼峰（lowerCamelCase）。
- 所有公共方法必须提供完整的Javadoc注释。
- 代码中禁止出现魔法值，所有常量必须定义在常量类中。
- 使用Lombok简化实体类代码（`@Data`、`@Builder`、`@NoArgsConstructor`、`@AllArgsConstructor`）。

### 5.2 接口规范

- 所有API接口必须符合 `/workspace/lis-project/docs/02-设计文档/API接口设计规范.md`。
- 接口URL遵循RESTful风格，统一使用 `/api/user/` 前缀。
- 统一使用 `Result<T>` 封装响应结果。
- 统一异常处理，返回标准错误格式。
- 接口需添加 `@RequirePermission` 注解进行权限控制。

### 5.3 数据库规范

- 所有数据库设计必须符合 `/workspace/lis-project/docs/02-设计文档/数据库设计说明书.md`。
- 使用MyBatis-Plus作为ORM框架，简单CRUD使用内置方法，复杂查询使用自定义XML。
- 禁止使用物理删除，统一使用逻辑删除（`@TableLogic`）。
- 主键使用雪花算法（`@TableId(type = IdType.ASSIGN_ID)`）。
- 所有表必须包含公共审计字段（create_by, create_time, update_by, update_time, deleted, remark）。

### 5.4 安全规范

- 所有密码使用BCrypt加密存储，禁止明文存储。
- 用户查询接口不得返回密码字段，使用 `@JsonIgnore` 注解排除。
- 敏感操作（删除用户、重置密码、权限变更）需记录审计日志。
- 接口参数需进行严格的输入校验，防止SQL注入和XSS攻击。
- 分页查询需限制最大每页条数（不超过100条），防止大量数据查询导致性能问题。

### 5.5 缓存规范

- 用户权限数据使用Redis缓存，Key格式为 `lis:permission:{userId}`。
- 字典数据使用Redis缓存，Key格式为 `lis:dict:{dictType}`。
- 菜单树数据使用Redis缓存，Key格式为 `lis:menu:tree`。
- 缓存过期时间：权限缓存2小时，字典缓存24小时，菜单缓存2小时。
- 数据变更时需主动清除相关缓存，确保数据一致性。

---

## 六、验收标准

### 6.1 功能验收

- [ ] 用户CRUD功能完整：列表分页查询、新增、修改、删除、启用/停用、密码重置均正常工作。
- [ ] 角色管理功能完整：角色CRUD、角色菜单权限分配均正常工作。
- [ ] 菜单管理功能完整：菜单树形结构CRUD正常工作，支持目录/菜单/按钮三种类型。
- [ ] 部门管理功能完整：部门树形结构CRUD正常工作，部门用户查询正常。
- [ ] 字典管理功能完整：字典类型CRUD、字典项CRUD均正常工作，缓存维护正确。
- [ ] 用户参数设置功能完整：系统参数查询和修改正常，个人信息管理和密码修改正常。
- [ ] RBAC权限模型正确实现：用户-角色-菜单三级关联关系正确，权限校验有效。
- [ ] 所有接口返回格式符合API接口设计规范。

### 6.2 质量验收

- [ ] 代码符合编码规范，无明显的代码坏味道。
- [ ] 所有公共方法提供完整的Javadoc注释。
- [ ] 单元测试覆盖率不低于70%，核心业务逻辑（用户创建、权限校验、密码加密）覆盖率不低于90%。
- [ ] BCrypt密码加密正确，密码比对验证通过。
- [ ] 逻辑删除功能正确，已删除数据不出现在查询结果中。
- [ ] 树形结构查询正确，层级关系无误。
- [ ] 缓存数据一致性保障有效。

### 6.3 安全验收

- [ ] 密码字段在所有查询接口中均不可见。
- [ ] SQL注入防护有效，所有查询使用参数化。
- [ ] 权限校验有效，未授权用户无法访问受限接口。
- [ ] 审计日志完整记录敏感操作。

### 6.4 文档验收

- [ ] 用户服务开发报告编写完成，内容详实。
- [ ] 接口文档完整，覆盖所有API端点。

---

## 七、开发进度安排

| 阶段 | 任务内容 | 预计工时 |
|------|---------|---------|
| 第一阶段 | 项目搭建（模块初始化、依赖配置、MyBatis-Plus配置、数据库连接） | 2小时 |
| 第二阶段 | 用户CRUD开发（列表查询、新增、修改、删除、启用/停用、密码重置） | 4小时 |
| 第三阶段 | 角色管理开发（角色CRUD、角色菜单权限分配、权限缓存） | 3小时 |
| 第四阶段 | 菜单管理开发（菜单树CRUD、树形结构查询、菜单缓存） | 2小时 |
| 第五阶段 | 部门管理开发（部门树CRUD、部门用户查询、树形结构查询） | 2小时 |
| 第六阶段 | 字典管理开发（字典类型CRUD、字典项CRUD、字典缓存） | 2小时 |
| 第七阶段 | 用户参数设置（系统参数管理、个人信息管理、密码修改） | 2小时 |
| 第八阶段 | Feign接口开发（为其他微服务提供的Feign客户端接口） | 1小时 |
| 第九阶段 | 测试与文档（单元测试、集成测试、开发报告编写） | 2小时 |
| **合计** | | **20小时** |

---

## 八、风险与注意事项

### 8.1 技术风险

| 风险项 | 风险等级 | 应对措施 |
|--------|---------|---------|
| RBAC权限模型设计复杂度较高 | 中 | 参考成熟的开源权限框架（如RuoYi），采用标准的五表设计 |
| 树形结构查询性能问题 | 低 | 使用递归算法配合缓存，数据量大时考虑闭包表优化 |
| 权限缓存一致性 | 中 | 数据变更时主动清除缓存，设置合理的过期时间作为兜底 |
| BCrypt加密性能开销 | 低 | BCrypt加密耗时约100ms，仅在密码创建和验证时使用，不影响查询性能 |

### 8.2 注意事项

1. 用户服务是系统安全的基础，所有权限校验逻辑必须严谨，不可存在绕过漏洞。
2. 密码安全是重中之重，必须使用BCrypt加密，禁止使用MD5、SHA1等不安全算法。
3. 超级管理员账户（userId = 1）和超级管理员角色（roleId = 1）为系统内置，不允许修改和删除。
4. 权限缓存的一致性至关重要，任何角色权限变更都必须及时清除相关用户的权限缓存。
5. 字典数据被系统各模块广泛引用，字典数据的变更需谨慎，建议增加变更审批机制。
6. 用户服务需为其他微服务提供Feign调用接口，接口设计需考虑跨服务调用的性能和安全性。
7. 逻辑删除的字段在所有查询条件中需自动追加 `deleted = 0` 条件，MyBatis-Plus的 `@TableLogic` 注解可自动处理。
8. 部门树的层级深度需合理控制，建议不超过5级，过深的层级会影响查询性能和用户体验。

---

*本文档由项目架构组编写，作为Agent-02（用户服务Agent）的开发任务书，指导其完成LIS系统用户与权限管理微服务的开发工作。*

---

## 【Git工作流规范】

> 本章节为V3.0新增，所有规则具有强制约束力。详细规范参见 `/workspace/lis-project/docs/05-开发规范/Git版本控制规范.md`。

### 分支管理

- **工作分支**：`feature/agent-02-user-service`
- **修复分支**：`bugfix/agent-02-{description}`
- **禁止**直接在`main`或`develop`分支上提交任何代码
- 分支由项目经理（PM）创建，你只能在分配的分支上工作

### 提交规范

- **格式**：`[Agent-02] {类型}({范围}): {简述}`
- **类型**：feat（新功能）、fix（修复）、refactor（重构）、docs（文档）、test（测试）、chore（构建/配置）
- **示例**：`[Agent-02] feat(用户服务): 新增XXX功能`
- 每个功能点至少一次提交，禁止积压大量变更后一次性提交
- 提交前必须确保代码可编译、功能可运行

### 过程文档要求

每个功能点完成后，必须同步提交过程文档：

| 文档类型 | 存放路径 | 提交时机 |
|---------|---------|---------|
| 功能开发记录 | `/workspace/lis-project/docs/process/Agent-02/{功能名称}-开发记录.md` | 每个功能点完成时 |
| 变更日志 | `/workspace/lis-project/docs/process/Agent-02/CHANGELOG.md` | 每次提交时更新 |

过程文档模板参见：`/workspace/lis-project/docs/process/开发记录模板.md`

### 阶段交付要求

完成本阶段全部任务后，必须提交以下交付物（缺一不可）：

1. ✅ 阶段开发报告（按D09《开发报告模板》格式）
2. ✅ CHANGELOG变更日志
3. ✅ 所有功能点的开发记录（`docs/process/Agent-02/`目录下）
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
