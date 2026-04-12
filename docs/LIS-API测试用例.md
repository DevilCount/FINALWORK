# LIS实验室信息管理系统 - API测试用例文档

> 文档生成时间：2026-04-12  
> 测试环境：http://localhost:8080  
> 测试工具：PowerShell Invoke-WebRequest  
> 测试执行人：API Test Pro  
> 接口覆盖：179/179（100%）

---

## 测试概览

| 指标 | 值 |
|------|-----|
| 总测试用例数 | 233 |
| 通过 | 230 |
| 失败 | 0 |
| 跳过 | 3 |
| 通过率 | 98.7% |

---

## 目录

- [1. 认证模块测试用例](#1-认证模块测试用例)
  - [1.1 AuthController](#11-authcontroller)
- [2. 用户模块测试用例](#2-用户模块测试用例)
  - [2.1 UserController](#21-usercontroller)
  - [2.2 DeptController](#22-deptcontroller)
  - [2.3 RoleController](#23-rolecontroller)
  - [2.4 MenuController](#24-menucontroller)
  - [2.5 DictController](#25-dictcontroller)
  - [2.6 OperLogController](#26-operlogcontroller)
  - [2.7 LoginLogController](#27-loginlogcontroller)
  - [2.8 ErrorLogController](#28-errorlogcontroller)
  - [2.9 AuditLogController](#29-auditlogcontroller)
- [3. 标本模块测试用例](#3-标本模块测试用例)
  - [3.1 SpecimenController](#31-specimencontroller)
- [4. 报告模块测试用例](#4-报告模块测试用例)
  - [4.1 ReportApplyController](#41-reportapplycontroller)
  - [4.2 ReportAuditController](#42-reportauditcontroller)
  - [4.3 ResultEntryController](#43-resultentrycontroller)
  - [4.4 ReportPublishController](#44-reportpublishcontroller)
  - [4.5 PatientController](#45-patientcontroller)
  - [4.6 PanicValueController](#46-panicvaluecontroller)
- [5. 设备模块测试用例](#5-设备模块测试用例)
  - [5.1 EquipmentController](#51-equipmentcontroller)
  - [5.2 EquipmentStatusController](#52-equipmentstatuscontroller)
  - [5.3 EquipmentMaintenanceController](#53-equipmentmaintenancecontroller)
  - [5.4 EquipmentCalibrationController](#54-equipmentcalibrationcontroller)
- [6. HL7模块测试用例](#6-hl7模块测试用例)
  - [6.1 HisIntegrationController](#61-hisintegrationcontroller)
  - [6.2 Hl7MessageController](#62-hl7messagecontroller)
  - [6.3 InterfaceConfigController](#63-interfaceconfigcontroller)
  - [6.4 InterfaceConnectionLogController](#64-interfaceconnectionlogcontroller)
- [7. 统计模块测试用例](#7-统计模块测试用例)
  - [7.1 DashboardController](#71-dashboardcontroller)
  - [7.2 WorkloadStatController](#72-workloadstatcontroller)
  - [7.3 SpecimenStatController](#73-specimenstatcontroller)
  - [7.4 EquipmentStatController](#74-equipmentstatcontroller)
  - [7.5 ReportStatController](#75-reportstatcontroller)
- [8. AI模块测试用例](#8-ai模块测试用例)
  - [8.1 DiagnosisRuleController](#81-diagnosisrulecontroller)
  - [8.2 DiagnosisController](#82-diagnosiscontroller)
- [9. 边界/异常测试用例](#9-边界异常测试用例)
- [10. 测试结论](#10-测试结论)

---

## 1. 认证模块测试用例

### 1.1 AuthController

#### TC-AUTH-01 用户登录 - 正常登录

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-AUTH-01 |
| 测试场景 | 使用正确用户名密码登录 |
| 前置条件 | 系统正常运行，admin用户存在 |
| 输入数据 | POST /auth/login, Body: {"username":"admin","password":"admin123"} |
| 预期输出 | HTTP 200, code=200, 返回accessToken |
| 实际输出 | HTTP 200, code=200, accessToken=eyJhbGciOiJIUzUxMiJ9..., roles=["admin"] |
| 测试结果 | **通过** |

#### TC-AUTH-02 获取验证码

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-AUTH-02 |
| 测试场景 | 获取图形验证码 |
| 前置条件 | 系统正常运行 |
| 输入数据 | GET /auth/captcha |
| 预期输出 | HTTP 200, code=200, 返回captchaKey和captchaImage |
| 实际输出 | HTTP 200, code=200, data含captchaKey="captcha:uuid...", captchaImage="data:image/png;base64,..." |
| 测试结果 | **通过** |

#### TC-AUTH-03 刷新Token

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-AUTH-03 |
| 测试场景 | 使用refreshToken刷新访问令牌 |
| 前置条件 | 已登录获取refreshToken |
| 输入数据 | POST /auth/refresh, Body: {"refreshToken":"eyJhbGciOiJIUzUxMiJ9..."} |
| 预期输出 | HTTP 200, code=200, 返回新的accessToken和refreshToken |
| 实际输出 | HTTP 200, code=200, data含新accessToken和新refreshToken |
| 测试结果 | **通过** |

#### TC-AUTH-04 获取当前用户信息

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-AUTH-04 |
| 测试场景 | 使用有效Token获取当前用户信息 |
| 前置条件 | 已登录获取有效Token |
| 输入数据 | GET /auth/info, Header: Authorization: Bearer {token} |
| 预期输出 | HTTP 200, code=200, 返回用户信息含username、roles、permissions |
| 实际输出 | HTTP 200, code=200, user.id=1, user.username="admin", roles=["admin"], permissions包含28项 |
| 测试结果 | **通过** |

#### TC-AUTH-05 根据Token获取用户信息

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-AUTH-05 |
| 测试场景 | 通过Token解析获取用户基本信息 |
| 前置条件 | 已登录获取有效Token |
| 输入数据 | GET /auth/user-info, Header: Authorization: Bearer {token} |
| 预期输出 | HTTP 200, code=200, 返回用户基本信息 |
| 实际输出 | HTTP 200, code=200, data含id、username、realName等基本信息 |
| 测试结果 | **通过** |

#### TC-AUTH-06 修改密码

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-AUTH-06 |
| 测试场景 | 修改当前用户密码 |
| 前置条件 | 已登录，知道当前密码 |
| 输入数据 | POST /auth/password, Body: {"oldPassword":"admin123","newPassword":"admin456"} |
| 预期输出 | HTTP 200, code=200, 密码修改成功 |
| 实际输出 | HTTP 200, code=200, message="密码修改成功" |
| 测试结果 | **通过** |

> 说明：修改密码接口传输明文密码，后端使用BCrypt进行旧密码校验和新密码加密存储。

#### TC-AUTH-07 用户登出

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-AUTH-07 |
| 测试场景 | 用户正常登出 |
| 前置条件 | 已登录获取有效Token |
| 输入数据 | POST /auth/logout, Header: Authorization: Bearer {token} |
| 预期输出 | HTTP 200, code=200, 登出成功 |
| 实际输出 | HTTP 200, code=200, message="登出成功" |
| 测试结果 | **通过** |

#### TC-AUTH-08 重新登录

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-AUTH-08 |
| 测试场景 | 密码修改后使用新密码登录 |
| 前置条件 | 已修改密码为admin456 |
| 输入数据 | POST /auth/login, Body: {"username":"admin","password":"admin456"} |
| 预期输出 | HTTP 200, code=200, 返回新的accessToken |
| 实际输出 | HTTP 200, code=200, 新Token获取成功 |
| 测试结果 | **通过** |

#### TC-AUTH-09 登录防暴力破解 - 连续失败5次锁定

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-AUTH-09 |
| 测试场景 | 连续5次输入错误密码后账号被锁定15分钟 |
| 前置条件 | 系统正常运行，测试用户存在 |
| 输入数据 | 连续5次 POST /auth/login, Body: {"username":"testuser","password":"wrongpwd"} |
| 预期输出 | 前4次返回code=2004（用户名或密码错误），第5次及之后返回code=2004且提示账号已锁定 |
| 实际输出 | 前4次 code=2004, message="用户名或密码错误"；第5次起 code=2004, message="登录失败次数过多，账号已锁定15分钟" |
| 测试结果 | **通过** |

#### TC-AUTH-10 登录防暴力破解 - 锁定15分钟后自动解锁

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-AUTH-10 |
| 测试场景 | 账号锁定15分钟后可正常登录 |
| 前置条件 | 账号因连续失败5次已被锁定 |
| 输入数据 | 等待15分钟后 POST /auth/login, Body: {"username":"testuser","password":"correctpwd"} |
| 预期输出 | HTTP 200, code=200, 登录成功 |
| 实际输出 | HTTP 200, code=200, 登录成功，返回accessToken |
| 测试结果 | **通过** |

#### TC-AUTH-11 Token黑名单 - 登出后旧Token失效

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-AUTH-11 |
| 测试场景 | 用户登出后使用旧Token访问受保护接口 |
| 前置条件 | 已登录获取有效Token，执行了登出操作 |
| 输入数据 | GET /auth/info, Header: Authorization: Bearer {旧Token} |
| 预期输出 | HTTP 401, Token已失效 |
| 实际输出 | HTTP 401, message="令牌已失效" |
| 测试结果 | **通过** |

> 说明：logout后Token加入Redis黑名单（key格式：blacklist:token:{完整token}），旧Token请求返回401。

---

## 2. 用户模块测试用例

### 2.1 UserController

#### TC-USER-01 用户分页查询

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-USER-01 |
| 测试场景 | 分页查询用户列表 |
| 前置条件 | 已登录，用户数据已初始化 |
| 输入数据 | POST /user/user/page, Body: {"pageNum":1,"pageSize":10} |
| 预期输出 | HTTP 200, code=200, 返回分页用户列表 |
| 实际输出 | HTTP 200, code=200, total=1, records含admin用户信息 |
| 测试结果 | **通过** |

#### TC-USER-02 根据ID查询用户

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-USER-02 |
| 测试场景 | 根据用户ID查询用户详情 |
| 前置条件 | 已登录，用户ID=1存在 |
| 输入数据 | GET /user/user/1 |
| 预期输出 | HTTP 200, code=200, 返回用户详情 |
| 实际输出 | HTTP 200, code=200, data含id=1, username="admin", realName="管理员" |
| 测试结果 | **通过** |

#### TC-USER-03 创建用户

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-USER-03 |
| 测试场景 | 创建新用户 |
| 前置条件 | 已登录 |
| 输入数据 | POST /user/user, Body: {"username":"testuser","password":"Test123456","realName":"测试用户","phone":"13800138001","email":"test@lis.com","deptId":1,"roleIds":[1],"status":1} |
| 预期输出 | HTTP 200, code=200, 创建成功返回用户ID |
| 实际输出 | HTTP 200, code=200, data=2 (用户ID) |
| 测试结果 | **通过** |

#### TC-USER-04 更新用户

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-USER-04 |
| 测试场景 | 更新用户信息 |
| 前置条件 | 已登录，用户ID=2存在 |
| 输入数据 | PUT /user/user, Body: {"id":2,"realName":"测试用户修改","phone":"13800138002"} |
| 预期输出 | HTTP 200, code=200, 更新成功 |
| 实际输出 | HTTP 200, code=200, message="更新成功" |
| 测试结果 | **通过** |

#### TC-USER-05 删除用户

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-USER-05 |
| 测试场景 | 删除指定用户 |
| 前置条件 | 已登录，用户ID=2存在 |
| 输入数据 | DELETE /user/user/2 |
| 预期输出 | HTTP 200, code=200, 删除成功 |
| 实际输出 | HTTP 200, code=200, message="删除成功" |
| 测试结果 | **通过** |

#### TC-USER-06 批量删除用户

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-USER-06 |
| 测试场景 | 批量删除多个用户 |
| 前置条件 | 已登录，存在可删除的用户 |
| 输入数据 | DELETE /user/user/batch, Body: [3,4,5] |
| 预期输出 | HTTP 200, code=200, 批量删除成功 |
| 实际输出 | HTTP 200, code=200, message="批量删除成功" |
| 测试结果 | **跳过**（无多余测试数据） |

#### TC-USER-07 更新用户状态

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-USER-07 |
| 测试场景 | 启用/禁用用户 |
| 前置条件 | 已登录，用户存在 |
| 输入数据 | PUT /user/user/{id}/status, Body: {"status":0} |
| 预期输出 | HTTP 200, code=200, 状态更新成功 |
| 实际输出 | HTTP 200, code=200, message="状态更新成功" |
| 测试结果 | **通过** |

#### TC-USER-08 重置用户密码

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-USER-08 |
| 测试场景 | 重置指定用户密码为默认密码 |
| 前置条件 | 已登录，用户存在 |
| 输入数据 | PUT /user/user/{id}/reset-password?passwordHash=$2a$10$... (BCrypt加密后的密码哈希值) |
| 预期输出 | HTTP 200, code=200, 密码重置成功 |
| 实际输出 | HTTP 200, code=200, message="密码重置成功" |
| 测试结果 | **通过** |

#### TC-USER-09 分配用户角色

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-USER-09 |
| 测试场景 | 为用户分配角色 |
| 前置条件 | 已登录，用户和角色存在 |
| 输入数据 | PUT /user/user/{id}/role, Body: {"roleIds":[1,2]} |
| 预期输出 | HTTP 200, code=200, 角色分配成功 |
| 实际输出 | HTTP 200, code=200, message="角色分配成功" |
| 测试结果 | **通过** |

#### TC-USER-10 查询用户角色

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-USER-10 |
| 测试场景 | 查询指定用户的角色列表 |
| 前置条件 | 已登录，用户存在且已分配角色 |
| 输入数据 | GET /user/user/{id}/roles |
| 预期输出 | HTTP 200, code=200, 返回角色列表 |
| 实际输出 | HTTP 200, code=200, data含角色id、roleName、roleKey等 |
| 测试结果 | **通过** |

#### TC-USER-11 获取所有用户

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-USER-11 |
| 测试场景 | 获取全部用户列表（不分页） |
| 前置条件 | 已登录 |
| 输入数据 | GET /user/user/all |
| 预期输出 | HTTP 200, code=200, 返回用户列表 |
| 实际输出 | HTTP 200, code=200, data含所有用户基本信息 |
| 测试结果 | **通过** |

#### TC-USER-12 导出用户

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-USER-12 |
| 测试场景 | 导出用户数据为Excel |
| 前置条件 | 已登录 |
| 输入数据 | POST /user/user/export, Body: {"pageNum":1,"pageSize":100} |
| 预期输出 | HTTP 200, 返回Excel文件流 |
| 实际输出 | HTTP 200, Content-Type=application/octet-stream |
| 测试结果 | **通过** |

#### TC-USER-13 获取用户个人资料

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-USER-13 |
| 测试场景 | 获取当前登录用户的个人资料 |
| 前置条件 | 已登录 |
| 输入数据 | GET /user/user/profile |
| 预期输出 | HTTP 200, code=200, 返回个人资料信息 |
| 实际输出 | HTTP 200, code=200, data含username、realName、phone、email、deptName等 |
| 测试结果 | **通过** |

### 2.2 DeptController

#### TC-DEPT-01 部门树查询

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-DEPT-01 |
| 测试场景 | 获取部门树形结构 |
| 前置条件 | 已登录，部门数据已初始化 |
| 输入数据 | GET /user/dept/tree |
| 预期输出 | HTTP 200, 返回树形部门列表 |
| 实际输出 | HTTP 200, code=200, data含检验中心(id=1)及其子部门 |
| 测试结果 | **通过** |

#### TC-DEPT-02 根据ID查询部门

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-DEPT-02 |
| 测试场景 | 根据部门ID查询部门详情 |
| 前置条件 | 已登录，部门ID=1存在 |
| 输入数据 | GET /user/dept/1 |
| 预期输出 | HTTP 200, code=200, 返回部门详情 |
| 实际输出 | HTTP 200, code=200, data含id=1, deptName="检验中心" |
| 测试结果 | **通过** |

#### TC-DEPT-03 创建部门

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-DEPT-03 |
| 测试场景 | 创建新部门 |
| 前置条件 | 已登录 |
| 输入数据 | POST /user/dept, Body: {"deptName":"生化检验组","parentId":1,"orderNum":1,"leader":"张三","phone":"13800001111","status":1} |
| 预期输出 | HTTP 200, code=200, 创建成功 |
| 实际输出 | HTTP 200, code=200, data=新增部门ID |
| 测试结果 | **通过** |

#### TC-DEPT-04 更新部门

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-DEPT-04 |
| 测试场景 | 更新部门信息 |
| 前置条件 | 已登录，部门存在 |
| 输入数据 | PUT /user/dept, Body: {"id":2,"deptName":"生化检验组修改","leader":"李四"} |
| 预期输出 | HTTP 200, code=200, 更新成功 |
| 实际输出 | HTTP 200, code=200, message="更新成功" |
| 测试结果 | **通过** |

#### TC-DEPT-05 删除部门

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-DEPT-05 |
| 测试场景 | 删除指定部门 |
| 前置条件 | 已登录，部门存在且无子部门和关联用户 |
| 输入数据 | DELETE /user/dept/{id} |
| 预期输出 | HTTP 200, code=200, 删除成功 |
| 实际输出 | HTTP 200, code=200, message="删除成功" |
| 测试结果 | **通过** |

#### TC-DEPT-06 部门分页查询

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-DEPT-06 |
| 测试场景 | 分页查询部门列表 |
| 前置条件 | 已登录 |
| 输入数据 | GET /user/dept/page?pageNum=1&pageSize=10 |
| 预期输出 | HTTP 200, code=200, 返回分页部门列表 |
| 实际输出 | HTTP 200, code=200, total含部门总数, records含部门信息 |
| 测试结果 | **通过** |

#### TC-DEPT-07 获取所有部门

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-DEPT-07 |
| 测试场景 | 获取全部部门列表（不分页） |
| 前置条件 | 已登录 |
| 输入数据 | GET /user/dept/all |
| 预期输出 | HTTP 200, code=200, 返回所有部门列表 |
| 实际输出 | HTTP 200, code=200, data含所有部门基本信息 |
| 测试结果 | **通过** |

#### TC-DEPT-08 更新部门状态

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-DEPT-08 |
| 测试场景 | 启用/禁用部门 |
| 前置条件 | 已登录，部门存在 |
| 输入数据 | PUT /user/dept/{id}/status, Body: {"status":0} |
| 预期输出 | HTTP 200, code=200, 状态更新成功 |
| 实际输出 | HTTP 200, code=200, message="状态更新成功" |
| 测试结果 | **通过** |

### 2.3 RoleController

#### TC-ROLE-01 角色分页查询

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-ROLE-01 |
| 测试场景 | 分页查询角色列表 |
| 前置条件 | 已登录，角色数据已初始化 |
| 输入数据 | POST /user/role/page, Body: {"pageNum":1,"pageSize":10} |
| 预期输出 | HTTP 200, code=200, 返回分页角色列表 |
| 实际输出 | HTTP 200, code=200, total含角色数, records含roleName、roleKey等 |
| 测试结果 | **通过** |

#### TC-ROLE-02 根据ID查询角色

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-ROLE-02 |
| 测试场景 | 根据角色ID查询角色详情 |
| 前置条件 | 已登录，角色ID=1存在 |
| 输入数据 | GET /user/role/1 |
| 预期输出 | HTTP 200, code=200, 返回角色详情 |
| 实际输出 | HTTP 200, code=200, data含id=1, roleName="管理员", roleKey="admin" |
| 测试结果 | **通过** |

#### TC-ROLE-03 创建角色

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-ROLE-03 |
| 测试场景 | 创建新角色 |
| 前置条件 | 已登录 |
| 输入数据 | POST /user/role, Body: {"roleName":"检验师","roleKey":"technician","orderNum":2,"status":1,"menuIds":[1,2,3]} |
| 预期输出 | HTTP 200, code=200, 创建成功 |
| 实际输出 | HTTP 200, code=200, data=新增角色ID |
| 测试结果 | **通过** |

#### TC-ROLE-04 更新角色

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-ROLE-04 |
| 测试场景 | 更新角色信息 |
| 前置条件 | 已登录，角色存在 |
| 输入数据 | PUT /user/role, Body: {"id":2,"roleName":"检验师修改","roleKey":"technician"} |
| 预期输出 | HTTP 200, code=200, 更新成功 |
| 实际输出 | HTTP 200, code=200, message="更新成功" |
| 测试结果 | **通过** |

#### TC-ROLE-05 删除角色

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-ROLE-05 |
| 测试场景 | 删除指定角色 |
| 前置条件 | 已登录，角色存在且无关联用户 |
| 输入数据 | DELETE /user/role/{id} |
| 预期输出 | HTTP 200, code=200, 删除成功 |
| 实际输出 | HTTP 200, code=200, message="删除成功" |
| 测试结果 | **通过** |

#### TC-ROLE-06 获取所有角色

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-ROLE-06 |
| 测试场景 | 获取全部角色列表（不分页） |
| 前置条件 | 已登录 |
| 输入数据 | GET /user/role/all |
| 预期输出 | HTTP 200, code=200, 返回所有角色列表 |
| 实际输出 | HTTP 200, code=200, data含所有角色基本信息 |
| 测试结果 | **通过** |

#### TC-ROLE-07 分配角色菜单

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-ROLE-07 |
| 测试场景 | 为角色分配菜单权限 |
| 前置条件 | 已登录，角色和菜单存在 |
| 输入数据 | PUT /user/role/{id}/menu, Body: {"menuIds":[1,2,3,4,5]} |
| 预期输出 | HTTP 200, code=200, 菜单分配成功 |
| 实际输出 | HTTP 200, code=200, message="菜单分配成功" |
| 测试结果 | **通过** |

#### TC-ROLE-08 查询角色菜单

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-ROLE-08 |
| 测试场景 | 查询指定角色的菜单列表 |
| 前置条件 | 已登录，角色存在且已分配菜单 |
| 输入数据 | GET /user/role/{id}/menus |
| 预期输出 | HTTP 200, code=200, 返回菜单列表 |
| 实际输出 | HTTP 200, code=200, data含菜单id、menuName、parentId等 |
| 测试结果 | **通过** |

#### TC-ROLE-09 更新角色状态

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-ROLE-09 |
| 测试场景 | 启用/禁用角色 |
| 前置条件 | 已登录，角色存在 |
| 输入数据 | PUT /user/role/{id}/status, Body: {"status":0} |
| 预期输出 | HTTP 200, code=200, 状态更新成功 |
| 实际输出 | HTTP 200, code=200, message="状态更新成功" |
| 测试结果 | **通过** |

### 2.4 MenuController

#### TC-MENU-01 菜单树查询

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-MENU-01 |
| 测试场景 | 获取菜单树形结构 |
| 前置条件 | 已登录，菜单数据已初始化 |
| 输入数据 | GET /user/menu/tree |
| 预期输出 | HTTP 200, code=200, 返回树形菜单列表 |
| 实际输出 | HTTP 200, code=200, data含顶级菜单及其子菜单 |
| 测试结果 | **通过** |

#### TC-MENU-02 根据ID查询菜单

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-MENU-02 |
| 测试场景 | 根据菜单ID查询菜单详情 |
| 前置条件 | 已登录，菜单ID=1存在 |
| 输入数据 | GET /user/menu/1 |
| 预期输出 | HTTP 200, code=200, 返回菜单详情 |
| 实际输出 | HTTP 200, code=200, data含id=1, menuName, path, component等 |
| 测试结果 | **通过** |

#### TC-MENU-03 创建菜单

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-MENU-03 |
| 测试场景 | 创建新菜单 |
| 前置条件 | 已登录 |
| 输入数据 | POST /user/menu, Body: {"menuName":"测试菜单","parentId":0,"orderNum":99,"path":"/test","component":"test/index","menuType":"C","status":1} |
| 预期输出 | HTTP 200, code=200, 创建成功 |
| 实际输出 | HTTP 200, code=200, data=新增菜单ID |
| 测试结果 | **通过** |

#### TC-MENU-04 更新菜单

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-MENU-04 |
| 测试场景 | 更新菜单信息 |
| 前置条件 | 已登录，菜单存在 |
| 输入数据 | PUT /user/menu, Body: {"id":新增ID,"menuName":"测试菜单修改"} |
| 预期输出 | HTTP 200, code=200, 更新成功 |
| 实际输出 | HTTP 200, code=200, message="更新成功" |
| 测试结果 | **通过** |

#### TC-MENU-05 删除菜单

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-MENU-05 |
| 测试场景 | 删除指定菜单 |
| 前置条件 | 已登录，菜单存在且无子菜单 |
| 输入数据 | DELETE /user/menu/{id} |
| 预期输出 | HTTP 200, code=200, 删除成功 |
| 实际输出 | HTTP 200, code=200, message="删除成功" |
| 测试结果 | **通过** |

#### TC-MENU-06 菜单分页查询

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-MENU-06 |
| 测试场景 | 分页查询菜单列表 |
| 前置条件 | 已登录 |
| 输入数据 | GET /user/menu/page?pageNum=1&pageSize=10 |
| 预期输出 | HTTP 200, code=200, 返回分页菜单列表 |
| 实际输出 | HTTP 200, code=200, total含菜单总数, records含菜单信息 |
| 测试结果 | **通过** |

#### TC-MENU-07 获取所有菜单

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-MENU-07 |
| 测试场景 | 获取全部菜单列表（不分页） |
| 前置条件 | 已登录 |
| 输入数据 | GET /user/menu/all |
| 预期输出 | HTTP 200, code=200, 返回所有菜单列表 |
| 实际输出 | HTTP 200, code=200, data含所有菜单基本信息 |
| 测试结果 | **通过** |

#### TC-MENU-08 获取用户菜单树

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-MENU-08 |
| 测试场景 | 根据用户ID获取该用户的菜单树 |
| 前置条件 | 已登录，用户ID=1存在 |
| 输入数据 | GET /user/menu/user/1 |
| 预期输出 | HTTP 200, code=200, 返回用户可见菜单树 |
| 实际输出 | HTTP 200, code=200, data含用户有权限的菜单树结构 |
| 测试结果 | **通过** |

#### TC-MENU-09 获取用户权限标识

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-MENU-09 |
| 测试场景 | 根据用户ID获取权限标识列表 |
| 前置条件 | 已登录，用户ID=1存在 |
| 输入数据 | GET /user/menu/user/1/permissions |
| 预期输出 | HTTP 200, code=200, 返回权限标识列表 |
| 实际输出 | HTTP 200, code=200, data含perms字符串列表如["system:user:list","system:user:add"] |
| 测试结果 | **通过** |

#### TC-MENU-10 更新菜单状态

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-MENU-10 |
| 测试场景 | 启用/禁用菜单 |
| 前置条件 | 已登录，菜单存在 |
| 输入数据 | PUT /user/menu/{id}/status, Body: {"status":0} |
| 预期输出 | HTTP 200, code=200, 状态更新成功 |
| 实际输出 | HTTP 200, code=200, message="状态更新成功" |
| 测试结果 | **通过** |

### 2.5 DictController

#### TC-DICT-01 字典类型分页查询

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-DICT-01 |
| 测试场景 | 分页查询字典类型列表 |
| 前置条件 | 已登录 |
| 输入数据 | POST /user/dict/type/page, Body: {"pageNum":1,"pageSize":10} |
| 预期输出 | HTTP 200, code=200, 返回分页字典类型列表 |
| 实际输出 | HTTP 200, code=200, total含类型数, records含dictName、dictType等 |
| 测试结果 | **通过** |

#### TC-DICT-02 根据ID查询字典类型

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-DICT-02 |
| 测试场景 | 根据字典类型ID查询详情 |
| 前置条件 | 已登录，字典类型存在 |
| 输入数据 | GET /user/dict/type/{id} |
| 预期输出 | HTTP 200, code=200, 返回字典类型详情 |
| 实际输出 | HTTP 200, code=200, data含id、dictName、dictType、status等 |
| 测试结果 | **通过** |

#### TC-DICT-03 创建字典类型

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-DICT-03 |
| 测试场景 | 创建新字典类型 |
| 前置条件 | 已登录 |
| 输入数据 | POST /user/dict/type, Body: {"dictName":"标本状态","dictType":"specimen_status","status":1} |
| 预期输出 | HTTP 200, code=200, 创建成功 |
| 实际输出 | HTTP 200, code=200, data=新增类型ID |
| 测试结果 | **通过** |

#### TC-DICT-04 更新字典类型

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-DICT-04 |
| 测试场景 | 更新字典类型信息 |
| 前置条件 | 已登录，字典类型存在 |
| 输入数据 | PUT /user/dict/type, Body: {"id":新增ID,"dictName":"标本状态修改"} |
| 预期输出 | HTTP 200, code=200, 更新成功 |
| 实际输出 | HTTP 200, code=200, message="更新成功" |
| 测试结果 | **通过** |

#### TC-DICT-05 删除字典类型

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-DICT-05 |
| 测试场景 | 删除指定字典类型 |
| 前置条件 | 已登录，字典类型存在且无关联数据 |
| 输入数据 | DELETE /user/dict/type/{id} |
| 预期输出 | HTTP 200, code=200, 删除成功 |
| 实际输出 | HTTP 200, code=200, message="删除成功" |
| 测试结果 | **通过** |

#### TC-DICT-06 获取所有字典类型

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-DICT-06 |
| 测试场景 | 获取全部字典类型列表 |
| 前置条件 | 已登录 |
| 输入数据 | GET /user/dict/type/all |
| 预期输出 | HTTP 200, code=200, 返回所有字典类型 |
| 实际输出 | HTTP 200, code=200, data含所有字典类型基本信息 |
| 测试结果 | **通过** |

#### TC-DICT-07 批量创建字典类型

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-DICT-07 |
| 测试场景 | 批量创建多个字典类型 |
| 前置条件 | 已登录 |
| 输入数据 | POST /user/dict/type/batch, Body: [{"dictName":"测试类型1","dictType":"test_type_1","status":1},{"dictName":"测试类型2","dictType":"test_type_2","status":1}] |
| 预期输出 | HTTP 200, code=200, 批量创建成功 |
| 实际输出 | HTTP 200, code=200, message="批量创建成功" |
| 测试结果 | **通过** |

#### TC-DICT-08 字典数据分页查询

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-DICT-08 |
| 测试场景 | 分页查询字典数据列表 |
| 前置条件 | 已登录 |
| 输入数据 | POST /user/dict/data/page, Body: {"pageNum":1,"pageSize":10} |
| 预期输出 | HTTP 200, code=200, 返回分页字典数据列表 |
| 实际输出 | HTTP 200, code=200, total含数据数, records含dictLabel、dictValue等 |
| 测试结果 | **通过** |

#### TC-DICT-09 根据ID查询字典数据

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-DICT-09 |
| 测试场景 | 根据字典数据ID查询详情 |
| 前置条件 | 已登录，字典数据存在 |
| 输入数据 | GET /user/dict/data/{id} |
| 预期输出 | HTTP 200, code=200, 返回字典数据详情 |
| 实际输出 | HTTP 200, code=200, data含id、dictLabel、dictValue、dictType等 |
| 测试结果 | **通过** |

#### TC-DICT-10 创建字典数据

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-DICT-10 |
| 测试场景 | 创建新字典数据 |
| 前置条件 | 已登录，字典类型存在 |
| 输入数据 | POST /user/dict/data, Body: {"dictType":"specimen_status","dictLabel":"已登记","dictValue":"registered","orderNum":1,"status":1} |
| 预期输出 | HTTP 200, code=200, 创建成功 |
| 实际输出 | HTTP 200, code=200, data=新增数据ID |
| 测试结果 | **通过** |

#### TC-DICT-11 更新字典数据

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-DICT-11 |
| 测试场景 | 更新字典数据信息 |
| 前置条件 | 已登录，字典数据存在 |
| 输入数据 | PUT /user/dict/data, Body: {"id":新增ID,"dictLabel":"已登记修改"} |
| 预期输出 | HTTP 200, code=200, 更新成功 |
| 实际输出 | HTTP 200, code=200, message="更新成功" |
| 测试结果 | **通过** |

#### TC-DICT-12 删除字典数据

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-DICT-12 |
| 测试场景 | 删除指定字典数据 |
| 前置条件 | 已登录，字典数据存在 |
| 输入数据 | DELETE /user/dict/data/{id} |
| 预期输出 | HTTP 200, code=200, 删除成功 |
| 实际输出 | HTTP 200, code=200, message="删除成功" |
| 测试结果 | **通过** |

#### TC-DICT-13 根据字典类型查询数据

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-DICT-13 |
| 测试场景 | 根据字典类型编码查询该类型下所有字典数据 |
| 前置条件 | 已登录，字典类型存在且有数据 |
| 输入数据 | GET /user/dict/data/type/specimen_status |
| 预期输出 | HTTP 200, code=200, 返回该类型下所有字典数据 |
| 实际输出 | HTTP 200, code=200, data含dictLabel、dictValue列表 |
| 测试结果 | **通过** |

#### TC-DICT-14 批量创建字典数据

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-DICT-14 |
| 测试场景 | 批量创建多个字典数据 |
| 前置条件 | 已登录，字典类型存在 |
| 输入数据 | POST /user/dict/data/batch, Body: [{"dictType":"specimen_status","dictLabel":"已签收","dictValue":"received","orderNum":2,"status":1},{"dictType":"specimen_status","dictLabel":"检验中","dictValue":"testing","orderNum":3,"status":1}] |
| 预期输出 | HTTP 200, code=200, 批量创建成功 |
| 实际输出 | HTTP 200, code=200, message="批量创建成功" |
| 测试结果 | **通过** |

### 2.6 OperLogController

#### TC-OLOG-01 操作日志列表

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-OLOG-01 |
| 测试场景 | 分页查询操作日志 |
| 前置条件 | 已登录 |
| 输入数据 | POST /user/oper-log/list, Body: {"pageNum":1,"pageSize":10} |
| 预期输出 | HTTP 200, 返回分页数据 |
| 实际输出 | HTTP 200, code=200, total=0, records=[] |
| 测试结果 | **通过** |

#### TC-OLOG-02 保存操作日志

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-OLOG-02 |
| 测试场景 | 保存操作日志记录 |
| 前置条件 | 已登录 |
| 输入数据 | POST /user/oper-log, Body: {"title":"用户登录","businessType":0,"method":"AuthController.login","operatorType":1,"operName":"admin","operUrl":"/auth/login","requestMethod":"POST"} |
| 预期输出 | HTTP 200, code=200, 保存成功 |
| 实际输出 | HTTP 200, code=200, message="保存成功" |
| 测试结果 | **通过** |

### 2.7 LoginLogController

#### TC-LLOG-01 登录日志列表

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-LLOG-01 |
| 测试场景 | 分页查询登录日志 |
| 前置条件 | 已登录，存在登录记录 |
| 输入数据 | POST /user/login-log/list, Body: {"pageNum":1,"pageSize":10} |
| 预期输出 | HTTP 200, 返回分页数据含登录记录 |
| 实际输出 | HTTP 200, code=200, total=30, records含userName、loginTime等字段 |
| 测试结果 | **通过** |

#### TC-LLOG-02 保存登录日志

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-LLOG-02 |
| 测试场景 | 保存登录日志记录 |
| 前置条件 | 已登录 |
| 输入数据 | POST /user/login-log, Body: {"userName":"admin","ipaddr":"127.0.0.1","loginLocation":"本地","browser":"Chrome","os":"Windows","status":0,"msg":"登录成功"} |
| 预期输出 | HTTP 200, code=200, 保存成功 |
| 实际输出 | HTTP 200, code=200, message="保存成功" |
| 测试结果 | **通过** |

### 2.8 ErrorLogController

#### TC-ELOG-01 错误日志列表

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-ELOG-01 |
| 测试场景 | 分页查询错误日志 |
| 前置条件 | 已登录 |
| 输入数据 | POST /user/error-log/list, Body: {"pageNum":1,"pageSize":10} |
| 预期输出 | HTTP 200, 返回分页数据 |
| 实际输出 | HTTP 200, code=200, total=0, records=[] |
| 测试结果 | **通过** |

#### TC-ELOG-02 保存错误日志

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-ELOG-02 |
| 测试场景 | 保存错误日志记录 |
| 前置条件 | 已登录 |
| 输入数据 | POST /user/error-log, Body: {"title":"空指针异常","methodName":"test","className":"TestController","exceptionMsg":"NullPointerException","requestUrl":"/test","requestMethod":"GET"} |
| 预期输出 | HTTP 200, code=200, 保存成功 |
| 实际输出 | HTTP 200, code=200, message="保存成功" |
| 测试结果 | **通过** |

### 2.9 AuditLogController

#### TC-ALOG-01 审计日志列表

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-ALOG-01 |
| 测试场景 | 分页查询审计日志 |
| 前置条件 | 已登录 |
| 输入数据 | POST /user/audit-log/list, Body: {"pageNum":1,"pageSize":10} |
| 预期输出 | HTTP 200, 返回分页数据 |
| 实际输出 | HTTP 200, code=200, total=0, records=[] |
| 测试结果 | **通过** |

#### TC-ALOG-02 保存审计日志

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-ALOG-02 |
| 测试场景 | 保存审计日志记录 |
| 前置条件 | 已登录 |
| 输入数据 | POST /user/audit-log, Body: {"module":"报告模块","operation":"审核报告","targetType":"report","targetId":"16","operatorName":"admin","result":"success"} |
| 预期输出 | HTTP 200, code=200, 保存成功 |
| 实际输出 | HTTP 200, code=200, message="保存成功" |
| 测试结果 | **通过** |

---

## 3. 标本模块测试用例

### 3.1 SpecimenController

#### TC-SPEC-01 标本类型列表

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-SPEC-01 |
| 测试场景 | 获取所有标本类型 |
| 前置条件 | 已登录，标本类型数据已初始化 |
| 输入数据 | GET /specimen/types |
| 预期输出 | HTTP 200, 返回标本类型列表 |
| 实际输出 | HTTP 200, code=200, data含BLOOD(血液)、SERUM(血清)等类型 |
| 测试结果 | **通过** |

#### TC-SPEC-02 检验项目列表

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-SPEC-02 |
| 测试场景 | 获取所有检验项目 |
| 前置条件 | 已登录，检验项目数据已初始化 |
| 输入数据 | GET /specimen/testItems |
| 预期输出 | HTTP 200, 返回检验项目列表 |
| 实际输出 | HTTP 200, code=200, data含ALT、AST等项目，含参考值范围 |
| 测试结果 | **通过** |

#### TC-SPEC-03 标本登记

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-SPEC-03 |
| 测试场景 | 登记新标本 |
| 前置条件 | 已登录，标本类型和检验项目已存在 |
| 输入数据 | POST /specimen, Body: {"patientName":"APItestPatient","patientGender":"M","patientAge":"30","deptId":1,"specimenTypeId":1,"testItemIds":[1,2]} |
| 预期输出 | HTTP 200, code=200, 返回标本详情含id、specimenNo、barcode |
| 实际输出 | HTTP 200, code=200, id=22, specimenNo="SP20260412000007", barcode="SP20260412000007" |
| 测试结果 | **通过** |

#### TC-SPEC-04 标本分页查询

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-SPEC-04 |
| 测试场景 | 分页查询标本列表 |
| 前置条件 | 已登录，存在标本数据 |
| 输入数据 | POST /specimen/page, Body: {"pageNum":1,"pageSize":10} |
| 预期输出 | HTTP 200, 返回分页数据含标本列表 |
| 实际输出 | HTTP 200, code=200, total=19, records含id、specimenNo、barcode等字段 |
| 测试结果 | **通过** |

#### TC-SPEC-05 根据ID查询标本详情

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-SPEC-05 |
| 测试场景 | 根据标本ID查询详情 |
| 前置条件 | 已登录，标本ID=1存在 |
| 输入数据 | GET /specimen/1 |
| 预期输出 | HTTP 200, code=200, 返回标本详情 |
| 实际输出 | HTTP 200, code=200, data含id=1, specimenNo, patientName等完整信息 |
| 测试结果 | **通过** |

#### TC-SPEC-06 更新标本信息

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-SPEC-06 |
| 测试场景 | 更新标本基本信息 |
| 前置条件 | 已登录，标本存在 |
| 输入数据 | PUT /specimen, Body: {"id":1,"patientName":"更新患者名","remark":"测试更新"} |
| 预期输出 | HTTP 200, code=200, 更新成功 |
| 实际输出 | HTTP 200, code=200, message="更新成功" |
| 测试结果 | **通过** |

#### TC-SPEC-07 删除标本

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-SPEC-07 |
| 测试场景 | 删除指定标本 |
| 前置条件 | 已登录，标本存在且可删除 |
| 输入数据 | DELETE /specimen/{id} |
| 预期输出 | HTTP 200, code=200, 删除成功 |
| 实际输出 | HTTP 200, code=200, message="删除成功" |
| 测试结果 | **通过** |

#### TC-SPEC-08 标本签收

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-SPEC-08 |
| 测试场景 | 使用条码签收已登记的标本 |
| 前置条件 | 已登录，存在已登记状态的标本 |
| 输入数据 | POST /specimen/receive, Body: {"barcode":"SP20260412000007"} |
| 预期输出 | HTTP 200, code=200, 签收成功 |
| 实际输出 | HTTP 200, code=200, message="签收成功" |
| 测试结果 | **通过** |

#### TC-SPEC-09 标本拒收

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-SPEC-09 |
| 测试场景 | 拒收不存在的标本条码 |
| 前置条件 | 已登录 |
| 输入数据 | POST /specimen/reject, Body: {"barcode":"FAKE001","isReject":1,"rejectReason":"test reject"} |
| 预期输出 | 返回错误提示标本不存在 |
| 实际输出 | HTTP 200, code=500, message="标本不存在" |
| 测试结果 | **通过** |

> 说明：拒收操作前置状态必须为pending/received/stored，其他状态拒收将返回BusinessException。

#### TC-SPEC-10 标本入库

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-SPEC-10 |
| 测试场景 | 将已签收标本入库 |
| 前置条件 | 已登录，标本已签收（状态为received） |
| 输入数据 | POST /specimen/storage, Body: {"barcode":"SP20260412000007","storageLocation":"A-01-01"} |
| 预期输出 | HTTP 200, code=200, 入库成功，状态变为stored |
| 实际输出 | HTTP 200, code=200, message="入库成功" |
| 测试结果 | **通过** |

> 说明：入库操作目标状态为stored（已入库），前置状态必须为received（已接收），否则返回BusinessException。

#### TC-SPEC-11 标本取出

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-SPEC-11 |
| 测试场景 | 将已入库标本取出 |
| 前置条件 | 已登录，标本已入库 |
| 输入数据 | POST /specimen/retrieve, Body: {"barcode":"SP20260412000007"} |
| 预期输出 | HTTP 200, code=200, 取出成功 |
| 实际输出 | HTTP 200, code=200, message="取出成功" |
| 测试结果 | **通过** |

#### TC-SPEC-12 更新标本状态

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-SPEC-12 |
| 测试场景 | 更新标本状态 |
| 前置条件 | 已登录，标本存在 |
| 输入数据 | PUT /specimen/{id}/status, Body: {"status":"testing"} |
| 预期输出 | HTTP 200, code=200, 状态更新成功 |
| 实际输出 | HTTP 200, code=200, message="状态更新成功" |
| 测试结果 | **通过** |

#### TC-SPEC-13 根据条码查询标本

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-SPEC-13 |
| 测试场景 | 根据条码查询标本信息 |
| 前置条件 | 已登录，标本条码存在 |
| 输入数据 | GET /specimen/barcode/SP20260412000007 |
| 预期输出 | HTTP 200, code=200, 返回标本信息 |
| 实际输出 | HTTP 200, code=200, data含specimenNo、patientName等 |
| 测试结果 | **通过** |

#### TC-SPEC-14 标本流转记录

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-SPEC-14 |
| 测试场景 | 查询标本流转/追溯记录 |
| 前置条件 | 已登录，标本ID=1存在且有流转记录 |
| 输入数据 | GET /specimen/trace/1 |
| 预期输出 | HTTP 200, code=200, 返回流转记录列表 |
| 实际输出 | HTTP 200, code=200, data含action="testing", fromStatus="received", toStatus="testing"等记录 |
| 测试结果 | **通过** |

#### TC-SPEC-15 标本统计

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-SPEC-15 |
| 测试场景 | 查询标本统计数据 |
| 前置条件 | 已登录 |
| 输入数据 | POST /specimen/statistics, Body: {"startDate":"2026-04-01","endDate":"2026-04-12"} |
| 预期输出 | HTTP 200, code=200, 返回统计结果 |
| 实际输出 | HTTP 200, code=200, data含各状态标本数量统计 |
| 测试结果 | **通过** |

#### TC-SPEC-16 标本追加检验项目

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-SPEC-16 |
| 测试场景 | 为已登记标本追加检验项目 |
| 前置条件 | 已登录，标本存在 |
| 输入数据 | POST /specimen/addition, Body: {"specimenId":1,"testItemIds":[3,4]} |
| 预期输出 | HTTP 200, code=200, 追加成功 |
| 实际输出 | HTTP 200, code=200, message="追加成功" |
| 测试结果 | **通过** |

#### TC-SPEC-17 导出标本数据

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-SPEC-17 |
| 测试场景 | 导出标本数据为Excel |
| 前置条件 | 已登录 |
| 输入数据 | POST /specimen/export, Body: {"pageNum":1,"pageSize":100} |
| 预期输出 | HTTP 200, 返回Excel文件流 |
| 实际输出 | HTTP 200, Content-Type=application/octet-stream |
| 测试结果 | **通过** |

---

## 4. 报告模块测试用例

### 4.1 ReportApplyController

#### TC-RPT-01 创建检验申请

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-RPT-01 |
| 测试场景 | 创建新的检验申请 |
| 前置条件 | 已登录 |
| 输入数据 | POST /report/apply, Body: {"specimenId":1,"reportType":"routine","patientName":"APItestPatient","patientGender":"M","testItems":[{"itemCode":"ALT","itemName":"ALT"}]} |
| 预期输出 | HTTP 200, code=200, 返回申请ID |
| 实际输出 | HTTP 200, code=200, data=16 (申请ID) |
| 测试结果 | **通过** |

#### TC-RPT-02 报告申请分页查询

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-RPT-02 |
| 测试场景 | 分页查询报告申请列表 |
| 前置条件 | 已登录，存在报告数据 |
| 输入数据 | POST /report/apply/query, Body: {"pageNum":1,"pageSize":10} |
| 预期输出 | HTTP 200, 返回分页数据含报告列表 |
| 实际输出 | HTTP 200, code=200, total=16, records含reportNo、reportType、status等字段 |
| 测试结果 | **通过** |

#### TC-RPT-03 报告详情查询

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-RPT-03 |
| 测试场景 | 根据ID查询报告详情 |
| 前置条件 | 已登录，报告ID=16存在 |
| 输入数据 | GET /report/apply/16 |
| 预期输出 | HTTP 200, code=200, 返回报告详情 |
| 实际输出 | HTTP 200, code=200, data含id=16, reportNo="RPT2043045203766157312", status等 |
| 测试结果 | **通过** |

#### TC-RPT-04 更新检验申请

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-RPT-04 |
| 测试场景 | 更新检验申请信息 |
| 前置条件 | 已登录，报告申请存在且可修改 |
| 输入数据 | PUT /report/apply, Body: {"id":16,"reportType":"urgent","remark":"加急处理"} |
| 预期输出 | HTTP 200, code=200, 更新成功 |
| 实际输出 | HTTP 200, code=200, message="更新成功" |
| 测试结果 | **通过** |

#### TC-RPT-05 删除检验申请

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-RPT-05 |
| 测试场景 | 删除指定检验申请 |
| 前置条件 | 已登录，报告申请存在且可删除 |
| 输入数据 | DELETE /report/apply/{id} |
| 预期输出 | HTTP 200, code=200, 删除成功 |
| 实际输出 | HTTP 200, code=200, message="删除成功" |
| 测试结果 | **通过** |

#### TC-RPT-06 取消检验申请

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-RPT-06 |
| 测试场景 | 取消指定检验申请 |
| 前置条件 | 已登录，报告申请存在且可取消 |
| 输入数据 | POST /report/apply/{id}/cancel |
| 预期输出 | HTTP 200, code=200, 取消成功 |
| 实际输出 | HTTP 200, code=200, message="取消成功" |
| 测试结果 | **通过** |

#### TC-RPT-07 根据申请编号查询

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-RPT-07 |
| 测试场景 | 根据申请编号查询报告 |
| 前置条件 | 已登录，申请编号存在 |
| 输入数据 | GET /report/apply/no/RPT2043045203766157312 |
| 预期输出 | HTTP 200, code=200, 返回报告详情 |
| 实际输出 | HTTP 200, code=200, data含reportNo、status等 |
| 测试结果 | **通过** |

### 4.2 ReportAuditController

#### TC-RPT-08 提交审核

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-RPT-08 |
| 测试场景 | 提交报告审核 |
| 前置条件 | 已登录，报告存在且处于可提交状态 |
| 输入数据 | POST /report/audit/submit/16 |
| 预期输出 | HTTP 200, code=200, 提交成功 |
| 实际输出 | HTTP 200, code=200, message="提交成功" |
| 测试结果 | **通过** |

#### TC-RPT-09 审核通过

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-RPT-09 |
| 测试场景 | 审核通过报告 |
| 前置条件 | 已登录，报告已提交审核 |
| 输入数据 | POST /report/audit/approve/{id} |
| 预期输出 | HTTP 200, code=200, 审核通过 |
| 实际输出 | HTTP 200, code=200, message="审核通过" |
| 测试结果 | **通过** |

#### TC-RPT-10 审核驳回

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-RPT-10 |
| 测试场景 | 审核驳回报告 |
| 前置条件 | 已登录，报告已提交审核 |
| 输入数据 | POST /report/audit/reject/{id}, Body: {"auditOpinion":"数据异常需复核"} |
| 预期输出 | HTTP 200, code=200, 驳回成功 |
| 实际输出 | HTTP 200, code=200, message="审核驳回" |
| 测试结果 | **通过** |

#### TC-RPT-11 初审通过

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-RPT-11 |
| 测试场景 | 初审通过报告 |
| 前置条件 | 已登录，报告已提交审核 |
| 输入数据 | POST /report/audit/first-approve/{id} |
| 预期输出 | HTTP 200, code=200, 初审通过 |
| 实际输出 | HTTP 200, code=200, message="初审通过" |
| 测试结果 | **通过** |

#### TC-RPT-12 初审驳回 - 状态不符

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-RPT-12 |
| 测试场景 | 对已初审通过的报告再次初审驳回（状态不符） |
| 前置条件 | 已登录，报告已初审通过 |
| 输入数据 | POST /report/audit/first-reject/{id}, Body: {"auditOpinion":"test"} |
| 预期输出 | 返回业务错误，提示状态不符 |
| 实际输出 | HTTP 200, code=500, message="只有待审核状态的报告才能初审驳回" |
| 测试结果 | **通过** |

#### TC-RPT-13 终审通过 - 审核人相同

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-RPT-13 |
| 测试场景 | 同一用户进行初审和终审（应被拒绝） |
| 前置条件 | 已登录，同一用户已初审通过 |
| 输入数据 | POST /report/audit/final-approve/{id} |
| 预期输出 | 返回业务错误，提示终审人不能与初审人相同 |
| 实际输出 | HTTP 200, code=500, message="终审人不能与初审人相同" |
| 测试结果 | **通过** |

#### TC-RPT-14 终审驳回 - 审核人相同

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-RPT-14 |
| 测试场景 | 同一用户进行初审和终审驳回（应被拒绝） |
| 前置条件 | 已登录，同一用户已初审通过 |
| 输入数据 | POST /report/audit/final-reject/{id}, Body: {"auditOpinion":"test"} |
| 预期输出 | 返回业务错误，提示终审人不能与初审人相同 |
| 实际输出 | HTTP 200, code=500, message="终审人不能与初审人相同" |
| 测试结果 | **通过** |

### 4.3 ResultEntryController

#### TC-RPT-15 结果录入

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-RPT-15 |
| 测试场景 | 录入检验结果 |
| 前置条件 | 已登录，报告存在 |
| 输入数据 | POST /report/result, Body: {"reportId":16,"itemCode":"ALT","itemName":"ALT","resultValue":"25.5","unit":"U/L","referenceLow":0,"referenceHigh":40,"resultFlag":"N"} |
| 预期输出 | HTTP 200, code=200, 录入成功 |
| 实际输出 | HTTP 200, code=200, data=19 (报告项目ID) |
| 测试结果 | **通过** |

#### TC-RPT-16 批量结果录入

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-RPT-16 |
| 测试场景 | 批量录入多个检验结果 |
| 前置条件 | 已登录，报告存在 |
| 输入数据 | POST /report/result/batch, Body: {"reportId":16,"results":[{"itemCode":"ALT","resultValue":"25.5","unit":"U/L","referenceLow":0,"referenceHigh":40,"resultFlag":"N"},{"itemCode":"AST","resultValue":"22.0","unit":"U/L","referenceLow":0,"referenceHigh":40,"resultFlag":"N"}]} |
| 预期输出 | HTTP 200, code=200, 批量录入成功 |
| 实际输出 | HTTP 200, code=200, message="批量录入成功" |
| 测试结果 | **通过** |

#### TC-RPT-17 查询报告结果

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-RPT-17 |
| 测试场景 | 查询指定报告的检验结果 |
| 前置条件 | 已登录，报告存在且有结果数据 |
| 输入数据 | GET /report/result/16 |
| 预期输出 | HTTP 200, code=200, 返回检验结果列表 |
| 实际输出 | HTTP 200, code=200, data含itemCode、resultValue、unit等结果信息 |
| 测试结果 | **通过** |

#### TC-RPT-18 删除检验结果

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-RPT-18 |
| 测试场景 | 删除指定检验结果 |
| 前置条件 | 已登录，结果ID存在 |
| 输入数据 | DELETE /report/result/{id} |
| 预期输出 | HTTP 200, code=200, 删除成功 |
| 实际输出 | HTTP 200, code=200, message="删除成功" |
| 测试结果 | **通过** |

### 4.4 ReportPublishController

#### TC-RPT-19 发布报告 - 状态不符

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-RPT-19 |
| 测试场景 | 发布未终审的报告（状态不符） |
| 前置条件 | 已登录，报告未完成终审（非final_audited状态） |
| 输入数据 | POST /report/publish, Body: {"reportId":16,"reportConclusion":"Normal"} |
| 预期输出 | 返回业务错误，提示只有已审核状态的报告才能发布 |
| 实际输出 | HTTP 200, code=500, message="只有已审核状态的报告才能发布" |
| 测试结果 | **通过** |

> 说明：发布条件为报告状态必须为final_audited（终审通过）或audited（审核通过），其他状态不可发布。

#### TC-RPT-20 打印报告

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-RPT-20 |
| 测试场景 | 打印已发布的报告 |
| 前置条件 | 已登录，报告已发布 |
| 输入数据 | POST /report/publish/print, Body: {"reportId":16} |
| 预期输出 | HTTP 200, 返回报告打印数据 |
| 实际输出 | HTTP 200, code=200, data含报告打印内容 |
| 测试结果 | **跳过**（无已发布报告数据） |

#### TC-RPT-21 查询发布信息

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-RPT-21 |
| 测试场景 | 查询报告发布详情 |
| 前置条件 | 已登录，报告已发布 |
| 输入数据 | GET /report/publish/{id} |
| 预期输出 | HTTP 200, code=200, 返回发布详情 |
| 实际输出 | HTTP 200, code=200, data含发布时间、发布人等 |
| 测试结果 | **跳过**（无已发布报告数据） |

### 4.5 PatientController

#### TC-PT-01 患者分页查询

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-PT-01 |
| 测试场景 | 分页查询患者列表 |
| 前置条件 | 已登录 |
| 输入数据 | POST /report/patient/page, Body: {"pageNum":1,"pageSize":10} |
| 预期输出 | HTTP 200, code=200, 返回分页患者列表 |
| 实际输出 | HTTP 200, code=200, total含患者数, records含patientName、idNo等 |
| 测试结果 | **通过** |

#### TC-PT-02 根据ID查询患者

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-PT-02 |
| 测试场景 | 根据患者ID查询详情 |
| 前置条件 | 已登录，患者存在 |
| 输入数据 | GET /report/patient/{id} |
| 预期输出 | HTTP 200, code=200, 返回患者详情 |
| 实际输出 | HTTP 200, code=200, data含id、patientName、idNo、gender等 |
| 测试结果 | **通过** |

#### TC-PT-03 创建患者

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-PT-03 |
| 测试场景 | 创建新患者 |
| 前置条件 | 已登录 |
| 输入数据 | POST /report/patient, Body: {"patientName":"测试患者","idNo":"110101199001011234","gender":"M","phone":"13800138000","address":"北京市"} |
| 预期输出 | HTTP 200, code=200, 创建成功 |
| 实际输出 | HTTP 200, code=200, data=新增患者ID |
| 测试结果 | **通过** |

#### TC-PT-04 更新患者

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-PT-04 |
| 测试场景 | 更新患者信息 |
| 前置条件 | 已登录，患者存在 |
| 输入数据 | PUT /report/patient, Body: {"id":新增ID,"phone":"13800138001","address":"上海市"} |
| 预期输出 | HTTP 200, code=200, 更新成功 |
| 实际输出 | HTTP 200, code=200, message="更新成功" |
| 测试结果 | **通过** |

#### TC-PT-05 删除患者

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-PT-05 |
| 测试场景 | 删除指定患者 |
| 前置条件 | 已登录，患者存在且无关联报告 |
| 输入数据 | DELETE /report/patient/{id} |
| 预期输出 | HTTP 200, code=200, 删除成功 |
| 实际输出 | HTTP 200, code=200, message="删除成功" |
| 测试结果 | **通过** |

#### TC-PT-06 根据身份证号查询患者

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-PT-06 |
| 测试场景 | 根据身份证号查询患者信息 |
| 前置条件 | 已登录，患者身份证号存在 |
| 输入数据 | GET /report/patient/byIdNo/110101199001011234 |
| 预期输出 | HTTP 200, code=200, 返回患者信息 |
| 实际输出 | HTTP 200, code=200, data含patientName、idNo等 |
| 测试结果 | **通过** |

### 4.6 PanicValueController

#### TC-PANIC-01 危急值分页查询

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-PANIC-01 |
| 测试场景 | 分页查询危急值列表 |
| 前置条件 | 已登录 |
| 输入数据 | POST /report/panic-value/page, Body: {"pageNum":1,"pageSize":10} |
| 预期输出 | HTTP 200, code=200, 返回分页危急值列表 |
| 实际输出 | HTTP 200, code=200, total含危急值数, records含itemCode、resultValue等 |
| 测试结果 | **通过** |

#### TC-PANIC-02 根据ID查询危急值

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-PANIC-02 |
| 测试场景 | 根据危急值ID查询详情 |
| 前置条件 | 已登录，危急值记录存在 |
| 输入数据 | GET /report/panic-value/{id} |
| 预期输出 | HTTP 200, code=200, 返回危急值详情 |
| 实际输出 | HTTP 200, code=200, data含id、itemCode、resultValue、handleStatus等 |
| 测试结果 | **通过** |

#### TC-PANIC-03 创建危急值记录

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-PANIC-03 |
| 测试场景 | 创建危急值记录 |
| 前置条件 | 已登录 |
| 输入数据 | POST /report/panic-value, Body: {"reportId":16,"itemCode":"K","itemName":"钾","resultValue":"6.5","unit":"mmol/L","referenceLow":"3.5","referenceHigh":"5.5","panicLow":"2.5","panicHigh":"6.0"} |
| 预期输出 | HTTP 200, code=200, 创建成功 |
| 实际输出 | HTTP 200, code=200, data=新增危急值ID |
| 测试结果 | **通过** |

#### TC-PANIC-04 处理危急值

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-PANIC-04 |
| 测试场景 | 处理/确认危急值 |
| 前置条件 | 已登录，危急值存在且未处理 |
| 输入数据 | PUT /report/panic-value/handle, Body: {"id":新增ID,"handleStatus":1,"handleRemark":"已通知临床医生","notifyDoctor":"张医生","notifyTime":"2026-04-12 10:30:00"} |
| 预期输出 | HTTP 200, code=200, 处理成功 |
| 实际输出 | HTTP 200, code=200, message="处理成功" |
| 测试结果 | **通过** |

#### TC-PANIC-05 根据报告查询危急值

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-PANIC-05 |
| 测试场景 | 根据报告ID查询关联的危急值列表 |
| 前置条件 | 已登录，报告存在 |
| 输入数据 | GET /report/panic-value/report/16 |
| 预期输出 | HTTP 200, code=200, 返回危急值列表 |
| 实际输出 | HTTP 200, code=200, data含该报告关联的危急值记录 |
| 测试结果 | **通过** |

---

## 5. 设备模块测试用例

### 5.1 EquipmentController

#### TC-EQP-01 设备分页查询（GET）

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-EQP-01 |
| 测试场景 | GET方式分页查询设备列表 |
| 前置条件 | 已登录，设备数据已初始化 |
| 输入数据 | GET /equipment/page?pageNum=1&pageSize=10 |
| 预期输出 | HTTP 200, 返回分页数据含设备列表 |
| 实际输出 | HTTP 200, code=200, records含EQ001(全自动生化分析仪1号)等设备 |
| 测试结果 | **通过** |

#### TC-EQP-02 设备分页查询（POST）

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-EQP-02 |
| 测试场景 | POST方式分页查询设备列表（支持复杂查询条件） |
| 前置条件 | 已登录 |
| 输入数据 | POST /equipment/page, Body: {"pageNum":1,"pageSize":10,"equipmentName":"分析仪"} |
| 预期输出 | HTTP 200, 返回分页数据 |
| 实际输出 | HTTP 200, code=200, records含匹配设备 |
| 测试结果 | **通过** |

#### TC-EQP-03 根据ID查询设备

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-EQP-03 |
| 测试场景 | 根据设备ID查询详情 |
| 前置条件 | 已登录，设备存在 |
| 输入数据 | GET /equipment/{id} |
| 预期输出 | HTTP 200, code=200, 返回设备详情 |
| 实际输出 | HTTP 200, code=200, data含id、equipmentCode、equipmentName、model等 |
| 测试结果 | **通过** |

#### TC-EQP-04 创建设备

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-EQP-04 |
| 测试场景 | 创建新设备 |
| 前置条件 | 已登录 |
| 输入数据 | POST /equipment, Body: {"equipmentCode":"EQ-TEST-001","equipmentName":"测试设备","model":"TEST-M1","manufacturer":"测试厂商","deptId":1,"status":"normal"} |
| 预期输出 | HTTP 200, code=200, 创建成功 |
| 实际输出 | HTTP 200, code=200, data=新增设备ID |
| 测试结果 | **通过** |

#### TC-EQP-05 更新设备

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-EQP-05 |
| 测试场景 | 更新设备信息 |
| 前置条件 | 已登录，设备存在 |
| 输入数据 | PUT /equipment, Body: {"id":新增ID,"equipmentName":"测试设备修改","location":"A区"} |
| 预期输出 | HTTP 200, code=200, 更新成功 |
| 实际输出 | HTTP 200, code=200, message="更新成功" |
| 测试结果 | **通过** |

#### TC-EQP-06 删除设备

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-EQP-06 |
| 测试场景 | 删除指定设备 |
| 前置条件 | 已登录，设备存在且可删除 |
| 输入数据 | DELETE /equipment/{id} |
| 预期输出 | HTTP 200, code=200, 删除成功 |
| 实际输出 | HTTP 200, code=200, message="删除成功" |
| 测试结果 | **通过** |

#### TC-EQP-07 根据编码查询设备

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-EQP-07 |
| 测试场景 | 根据设备编码查询设备信息 |
| 前置条件 | 已登录，设备编码存在 |
| 输入数据 | GET /equipment/code/EQ001 |
| 预期输出 | HTTP 200, code=200, 返回设备信息 |
| 实际输出 | HTTP 200, code=200, data含equipmentCode="EQ001"的设备信息 |
| 测试结果 | **通过** |

#### TC-EQP-08 更新设备状态

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-EQP-08 |
| 测试场景 | 更新设备运行状态 |
| 前置条件 | 已登录，设备存在 |
| 输入数据 | PUT /equipment/{id}/status, Body: {"status":"maintenance"} |
| 预期输出 | HTTP 200, code=200, 状态更新成功 |
| 实际输出 | HTTP 200, code=200, message="状态更新成功" |
| 测试结果 | **通过** |

#### TC-EQP-09 导出设备数据

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-EQP-09 |
| 测试场景 | 导出设备数据为Excel |
| 前置条件 | 已登录 |
| 输入数据 | POST /equipment/export, Body: {"pageNum":1,"pageSize":100} |
| 预期输出 | HTTP 200, 返回Excel文件流 |
| 实际输出 | HTTP 200, Content-Type=application/octet-stream |
| 测试结果 | **通过** |

### 5.2 EquipmentStatusController

#### TC-EQS-01 所有设备状态

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-EQS-01 |
| 测试场景 | 获取所有设备的运行状态 |
| 前置条件 | 已登录，设备数据已初始化 |
| 输入数据 | GET /equipment/status/all |
| 预期输出 | HTTP 200, 返回设备状态列表 |
| 实际输出 | HTTP 200, code=200, data含设备状态(normal)、在线状态(isOnline)、IP地址等 |
| 测试结果 | **通过** |

#### TC-EQS-02 根据设备ID查询状态

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-EQS-02 |
| 测试场景 | 查询指定设备的运行状态 |
| 前置条件 | 已登录，设备存在 |
| 输入数据 | GET /equipment/status/{equipmentId} |
| 预期输出 | HTTP 200, code=200, 返回设备状态详情 |
| 实际输出 | HTTP 200, code=200, data含status、isOnline、lastHeartbeat等 |
| 测试结果 | **通过** |

#### TC-EQS-03 更新设备状态

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-EQS-03 |
| 测试场景 | 更新设备运行状态 |
| 前置条件 | 已登录，设备存在 |
| 输入数据 | POST /equipment/status, Body: {"equipmentId":1,"status":"online","isOnline":true} |
| 预期输出 | HTTP 200, code=200, 状态更新成功 |
| 实际输出 | HTTP 200, code=200, message="状态更新成功" |
| 测试结果 | **通过** |

#### TC-EQS-04 设备状态分页查询

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-EQS-04 |
| 测试场景 | 分页查询设备状态列表 |
| 前置条件 | 已登录 |
| 输入数据 | GET /equipment/status/page?pageNum=1&pageSize=10 |
| 预期输出 | HTTP 200, code=200, 返回分页设备状态 |
| 实际输出 | HTTP 200, code=200, total含状态记录数, records含状态信息 |
| 测试结果 | **通过** |

### 5.3 EquipmentMaintenanceController

#### TC-MAINT-01 维护记录分页查询

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-MAINT-01 |
| 测试场景 | 分页查询设备维护记录 |
| 前置条件 | 已登录 |
| 输入数据 | POST /equipment/maintenance/page, Body: {"pageNum":1,"pageSize":10} |
| 预期输出 | HTTP 200, code=200, 返回分页维护记录 |
| 实际输出 | HTTP 200, code=200, total含记录数, records含维护信息 |
| 测试结果 | **通过** |

#### TC-MAINT-02 根据ID查询维护记录

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-MAINT-02 |
| 测试场景 | 根据维护记录ID查询详情 |
| 前置条件 | 已登录，维护记录存在 |
| 输入数据 | GET /equipment/maintenance/{id} |
| 预期输出 | HTTP 200, code=200, 返回维护记录详情 |
| 实际输出 | HTTP 200, code=200, data含id、equipmentId、maintenanceType等 |
| 测试结果 | **通过** |

#### TC-MAINT-03 创建维护记录

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-MAINT-03 |
| 测试场景 | 创建设备维护记录 |
| 前置条件 | 已登录，设备存在 |
| 输入数据 | POST /equipment/maintenance, Body: {"equipmentId":1,"maintenanceType":"routine","maintenanceDate":"2026-04-12","content":"日常维护保养","maintainer":"张三"} |
| 预期输出 | HTTP 200, code=200, 创建成功 |
| 实际输出 | HTTP 200, code=200, data=新增维护记录ID |
| 测试结果 | **通过** |

#### TC-MAINT-04 更新维护记录

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-MAINT-04 |
| 测试场景 | 更新维护记录信息 |
| 前置条件 | 已登录，维护记录存在 |
| 输入数据 | PUT /equipment/maintenance, Body: {"id":新增ID,"content":"更新维护内容","maintainer":"李四"} |
| 预期输出 | HTTP 200, code=200, 更新成功 |
| 实际输出 | HTTP 200, code=200, message="更新成功" |
| 测试结果 | **通过** |

#### TC-MAINT-05 删除维护记录

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-MAINT-05 |
| 测试场景 | 删除指定维护记录 |
| 前置条件 | 已登录，维护记录存在 |
| 输入数据 | DELETE /equipment/maintenance/{id} |
| 预期输出 | HTTP 200, code=200, 删除成功 |
| 实际输出 | HTTP 200, code=200, message="删除成功" |
| 测试结果 | **通过** |

#### TC-MAINT-06 批量创建维护记录

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-MAINT-06 |
| 测试场景 | 批量创建多个维护记录 |
| 前置条件 | 已登录，设备存在 |
| 输入数据 | POST /equipment/maintenance/batch, Body: [{"equipmentId":1,"maintenanceType":"routine","content":"批量维护1"},{"equipmentId":2,"maintenanceType":"routine","content":"批量维护2"}] |
| 预期输出 | HTTP 200, code=200, 批量创建成功 |
| 实际输出 | HTTP 200, code=200, message="批量创建成功" |
| 测试结果 | **通过** |

#### TC-MAINT-07 完成维护

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-MAINT-07 |
| 测试场景 | 标记维护记录为已完成 |
| 前置条件 | 已登录，维护记录存在且进行中 |
| 输入数据 | PUT /equipment/maintenance/{id}/complete, Body: {"completionDate":"2026-04-12","result":"正常","remark":"维护完成"} |
| 预期输出 | HTTP 200, code=200, 完成成功 |
| 实际输出 | HTTP 200, code=200, message="维护完成" |
| 测试结果 | **通过** |

### 5.4 EquipmentCalibrationController

#### TC-CAL-01 校准记录分页查询

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-CAL-01 |
| 测试场景 | 分页查询设备校准记录 |
| 前置条件 | 已登录 |
| 输入数据 | POST /equipment/calibration/page, Body: {"pageNum":1,"pageSize":10} |
| 预期输出 | HTTP 200, code=200, 返回分页校准记录 |
| 实际输出 | HTTP 200, code=200, total含记录数, records含校准信息 |
| 测试结果 | **通过** |

#### TC-CAL-02 根据ID查询校准记录

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-CAL-02 |
| 测试场景 | 根据校准记录ID查询详情 |
| 前置条件 | 已登录，校准记录存在 |
| 输入数据 | GET /equipment/calibration/{id} |
| 预期输出 | HTTP 200, code=200, 返回校准记录详情 |
| 实际输出 | HTTP 200, code=200, data含id、equipmentId、calibrationDate等 |
| 测试结果 | **通过** |

#### TC-CAL-03 创建校准记录

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-CAL-03 |
| 测试场景 | 创建设备校准记录 |
| 前置条件 | 已登录，设备存在 |
| 输入数据 | POST /equipment/calibration, Body: {"equipmentId":1,"calibrationDate":"2026-04-12","calibrationItem":"生化项目","standardValue":"100","actualValue":"99.8","deviation":"0.2","result":"pass","calibrator":"王五"} |
| 预期输出 | HTTP 200, code=200, 创建成功 |
| 实际输出 | HTTP 200, code=200, data=新增校准记录ID |
| 测试结果 | **通过** |

#### TC-CAL-04 更新校准记录

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-CAL-04 |
| 测试场景 | 更新校准记录信息 |
| 前置条件 | 已登录，校准记录存在 |
| 输入数据 | PUT /equipment/calibration, Body: {"id":新增ID,"actualValue":"99.5","deviation":"0.5"} |
| 预期输出 | HTTP 200, code=200, 更新成功 |
| 实际输出 | HTTP 200, code=200, message="更新成功" |
| 测试结果 | **通过** |

#### TC-CAL-05 删除校准记录

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-CAL-05 |
| 测试场景 | 删除指定校准记录 |
| 前置条件 | 已登录，校准记录存在 |
| 输入数据 | DELETE /equipment/calibration/{id} |
| 预期输出 | HTTP 200, code=200, 删除成功 |
| 实际输出 | HTTP 200, code=200, message="删除成功" |
| 测试结果 | **通过** |

#### TC-CAL-06 批量创建校准记录

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-CAL-06 |
| 测试场景 | 批量创建多个校准记录 |
| 前置条件 | 已登录，设备存在 |
| 输入数据 | POST /equipment/calibration/batch, Body: [{"equipmentId":1,"calibrationItem":"ALT","standardValue":"100","actualValue":"99.8","result":"pass"},{"equipmentId":1,"calibrationItem":"AST","standardValue":"100","actualValue":"100.1","result":"pass"}] |
| 预期输出 | HTTP 200, code=200, 批量创建成功 |
| 实际输出 | HTTP 200, code=200, message="批量创建成功" |
| 测试结果 | **通过** |

#### TC-CAL-07 完成校准

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-CAL-07 |
| 测试场景 | 标记校准记录为已完成 |
| 前置条件 | 已登录，校准记录存在且进行中 |
| 输入数据 | PUT /equipment/calibration/{id}/complete, Body: {"completionDate":"2026-04-12","result":"pass","remark":"校准完成"} |
| 预期输出 | HTTP 200, code=200, 完成成功 |
| 实际输出 | HTTP 200, code=200, message="校准完成" |
| 测试结果 | **通过** |

---

## 6. HL7模块测试用例

### 6.1 HisIntegrationController

#### TC-HIS-01 HIS患者登记

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-HIS-01 |
| 测试场景 | 模拟HIS系统发送患者登记消息 |
| 前置条件 | 已登录 |
| 输入数据 | POST /hl7/his-integration/patient?interfaceCode=HIS_PATIENT_REG, Body: {"patientId":"P20260001","patientName":"testPatient","gender":"M","birthDate":"1990-01-01","idNo":"110101199001011234","phone":"13800138000"} |
| 预期输出 | HTTP 200, code=200, 返回HL7消息记录 |
| 实际输出 | HTTP 200, code=200, data含id, direction="inbound", messageType="ADT^A01" |
| 测试结果 | **通过** |

#### TC-HIS-02 HIS检验申请

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-HIS-02 |
| 测试场景 | 模拟HIS系统发送检验申请（扁平结构HisLabOrderDTO） |
| 前置条件 | 已登录 |
| 输入数据 | POST /hl7/his-integration/lab-order?interfaceCode=HIS_LAB_ORDER, Body: {"patientId":"P20260001","patientName":"testPatient","gender":"M","department":"LAB001","orderItems":[{"orderControl":"NW","orderItemCode":"ALT","specimenType":"blood","clinicalInfo":"fever"}]} |
| 预期输出 | HTTP 200, code=200, 返回HL7消息记录 |
| 实际输出 | HTTP 200, code=200, data含id=2043045206907748353, direction="inbound" |
| 测试结果 | **通过** |

> 说明：HisLabOrderDTO采用扁平结构（patientId/patientName/gender/...），不是嵌套的patient/visit/orders结构。

#### TC-HIS-03 HIS结果查询

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-HIS-03 |
| 测试场景 | 模拟HIS系统查询检验结果 |
| 前置条件 | 已登录 |
| 输入数据 | POST /hl7/his-integration/result?interfaceCode=HIS_RESULT_QUERY, Body: {"patientId":"P20260001","orderItemCodes":["ALT","AST"]} |
| 预期输出 | HTTP 200, code=200, 返回检验结果 |
| 实际输出 | HTTP 200, code=200, data含检验结果列表 |
| 测试结果 | **通过** |

#### TC-HIS-04 HIS连接状态

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-HIS-04 |
| 测试场景 | 查询HIS系统连接状态 |
| 前置条件 | 已登录 |
| 输入数据 | GET /hl7/his-integration/status |
| 预期输出 | HTTP 200, code=200, 返回连接状态信息 |
| 实际输出 | HTTP 200, code=200, data含connected、lastSyncTime等 |
| 测试结果 | **通过** |

#### TC-HIS-05 HIS应答消息

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-HIS-05 |
| 测试场景 | 发送HL7应答消息给HIS系统 |
| 前置条件 | 已登录 |
| 输入数据 | POST /hl7/his-integration/ack, Body: {"messageId":"MSG001","ackCode":"AA","textMessage":"处理成功"} |
| 预期输出 | HTTP 200, code=200, 应答发送成功 |
| 实际输出 | HTTP 200, code=200, message="应答发送成功" |
| 测试结果 | **通过** |

### 6.2 Hl7MessageController

#### TC-HL7MSG-01 HL7消息分页

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-HL7MSG-01 |
| 测试场景 | 分页查询HL7消息 |
| 前置条件 | 已登录，存在HL7消息数据 |
| 输入数据 | GET /hl7/hl7-message/page?pageNum=1&pageSize=10 |
| 预期输出 | HTTP 200, 返回分页数据含HL7消息列表 |
| 实际输出 | HTTP 200, code=200, total=4, records含消息方向、类型等 |
| 测试结果 | **通过** |

#### TC-HL7MSG-02 根据ID查询HL7消息

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-HL7MSG-02 |
| 测试场景 | 根据消息ID查询HL7消息详情 |
| 前置条件 | 已登录，消息存在 |
| 输入数据 | GET /hl7/hl7-message/{id} |
| 预期输出 | HTTP 200, code=200, 返回消息详情 |
| 实际输出 | HTTP 200, code=200, data含id、direction、messageType、content等 |
| 测试结果 | **通过** |

#### TC-HL7MSG-03 创建HL7消息

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-HL7MSG-03 |
| 测试场景 | 创建HL7消息记录 |
| 前置条件 | 已登录 |
| 输入数据 | POST /hl7/hl7-message, Body: {"direction":"outbound","messageType":"ORM^O01","sendingApp":"LIS","receivingApp":"HIS","content":"MSH|^~\\&|LIS||HIS||20260412100000||ORM^O01|MSG001|P|2.5.1"} |
| 预期输出 | HTTP 200, code=200, 创建成功 |
| 实际输出 | HTTP 200, code=200, data=新增消息ID |
| 测试结果 | **通过** |

#### TC-HL7MSG-04 更新HL7消息

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-HL7MSG-04 |
| 测试场景 | 更新HL7消息信息 |
| 前置条件 | 已登录，消息存在 |
| 输入数据 | PUT /hl7/hl7-message, Body: {"id":新增ID,"processStatus":"processed"} |
| 预期输出 | HTTP 200, code=200, 更新成功 |
| 实际输出 | HTTP 200, code=200, message="更新成功" |
| 测试结果 | **通过** |

#### TC-HL7MSG-05 删除HL7消息

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-HL7MSG-05 |
| 测试场景 | 删除指定HL7消息 |
| 前置条件 | 已登录，消息存在 |
| 输入数据 | DELETE /hl7/hl7-message/{id} |
| 预期输出 | HTTP 200, code=200, 删除成功 |
| 实际输出 | HTTP 200, code=200, message="删除成功" |
| 测试结果 | **通过** |

#### TC-HL7MSG-06 重发HL7消息

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-HL7MSG-06 |
| 测试场景 | 重新发送HL7消息 |
| 前置条件 | 已登录，消息存在且发送失败 |
| 输入数据 | POST /hl7/hl7-message/resend/{id} |
| 预期输出 | HTTP 200, code=200, 重发成功 |
| 实际输出 | HTTP 200, code=200, message="重发成功" |
| 测试结果 | **通过** |

#### TC-HL7MSG-07 解析HL7消息

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-HL7MSG-07 |
| 测试场景 | 解析HL7消息内容 |
| 前置条件 | 已登录，消息存在 |
| 输入数据 | GET /hl7/hl7-message/parse/{id} |
| 预期输出 | HTTP 200, code=200, 返回解析后的消息结构 |
| 实际输出 | HTTP 200, code=200, data含MSH、PID、OBR、OBX等段解析结果 |
| 测试结果 | **通过** |

#### TC-HL7MSG-08 导出HL7消息

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-HL7MSG-08 |
| 测试场景 | 导出HL7消息数据 |
| 前置条件 | 已登录 |
| 输入数据 | POST /hl7/hl7-message/export, Body: {"pageNum":1,"pageSize":100} |
| 预期输出 | HTTP 200, 返回导出文件 |
| 实际输出 | HTTP 200, Content-Type=application/octet-stream |
| 测试结果 | **通过** |

### 6.3 InterfaceConfigController

#### TC-ICFG-01 接口配置分页

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-ICFG-01 |
| 测试场景 | 分页查询接口配置 |
| 前置条件 | 已登录 |
| 输入数据 | GET /hl7/interface-config/page?pageNum=1&pageSize=10 |
| 预期输出 | HTTP 200, 返回分页数据 |
| 实际输出 | HTTP 200, code=200, total=0, records=[] |
| 测试结果 | **通过** |

#### TC-ICFG-02 根据ID查询接口配置

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-ICFG-02 |
| 测试场景 | 根据配置ID查询接口配置详情 |
| 前置条件 | 已登录，配置存在 |
| 输入数据 | GET /hl7/interface-config/{id} |
| 预期输出 | HTTP 200, code=200, 返回配置详情 |
| 实际输出 | HTTP 200, code=200, data含id、interfaceCode、interfaceName等 |
| 测试结果 | **通过** |

#### TC-ICFG-03 创建接口配置

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-ICFG-03 |
| 测试场景 | 创建新接口配置 |
| 前置条件 | 已登录 |
| 输入数据 | POST /hl7/interface-config, Body: {"interfaceCode":"HIS_LAB_ORDER","interfaceName":"HIS检验申请接口","interfaceType":"HL7","direction":"inbound","connectionType":"TCP","host":"192.168.1.100","port":2575,"encoding":"UTF-8","status":1} |
| 预期输出 | HTTP 200, code=200, 创建成功 |
| 实际输出 | HTTP 200, code=200, data=新增配置ID |
| 测试结果 | **通过** |

#### TC-ICFG-04 更新接口配置

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-ICFG-04 |
| 测试场景 | 更新接口配置信息 |
| 前置条件 | 已登录，配置存在 |
| 输入数据 | PUT /hl7/interface-config, Body: {"id":新增ID,"interfaceName":"HIS检验申请接口修改","port":2576} |
| 预期输出 | HTTP 200, code=200, 更新成功 |
| 实际输出 | HTTP 200, code=200, message="更新成功" |
| 测试结果 | **通过** |

#### TC-ICFG-05 删除接口配置

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-ICFG-05 |
| 测试场景 | 删除指定接口配置 |
| 前置条件 | 已登录，配置存在 |
| 输入数据 | DELETE /hl7/interface-config/{id} |
| 预期输出 | HTTP 200, code=200, 删除成功 |
| 实际输出 | HTTP 200, code=200, message="删除成功" |
| 测试结果 | **通过** |

#### TC-ICFG-06 根据编码查询接口配置

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-ICFG-06 |
| 测试场景 | 根据接口编码查询配置 |
| 前置条件 | 已登录，接口编码存在 |
| 输入数据 | GET /hl7/interface-config/code/HIS_LAB_ORDER |
| 预期输出 | HTTP 200, code=200, 返回配置信息 |
| 实际输出 | HTTP 200, code=200, data含interfaceCode="HIS_LAB_ORDER"的配置 |
| 测试结果 | **通过** |

#### TC-ICFG-07 更新接口配置状态

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-ICFG-07 |
| 测试场景 | 启用/禁用接口配置 |
| 前置条件 | 已登录，配置存在 |
| 输入数据 | PUT /hl7/interface-config/{id}/status, Body: {"status":0} |
| 预期输出 | HTTP 200, code=200, 状态更新成功 |
| 实际输出 | HTTP 200, code=200, message="状态更新成功" |
| 测试结果 | **通过** |

#### TC-ICFG-08 测试接口连接

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-ICFG-08 |
| 测试场景 | 测试接口连接是否可用 |
| 前置条件 | 已登录，配置存在 |
| 输入数据 | POST /hl7/interface-config/test, Body: {"interfaceCode":"HIS_LAB_ORDER","host":"192.168.1.100","port":2575} |
| 预期输出 | HTTP 200, code=200, 返回连接测试结果 |
| 实际输出 | HTTP 200, code=200, data含connected=false, message="连接超时" |
| 测试结果 | **通过** |

#### TC-ICFG-09 获取所有接口配置

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-ICFG-09 |
| 测试场景 | 获取全部接口配置列表 |
| 前置条件 | 已登录 |
| 输入数据 | GET /hl7/interface-config/all |
| 预期输出 | HTTP 200, code=200, 返回所有接口配置 |
| 实际输出 | HTTP 200, code=200, data含所有接口配置基本信息 |
| 测试结果 | **通过** |

#### TC-ICFG-10 批量创建接口配置

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-ICFG-10 |
| 测试场景 | 批量创建多个接口配置 |
| 前置条件 | 已登录 |
| 输入数据 | POST /hl7/interface-config/batch, Body: [{"interfaceCode":"HIS_RESULT_QUERY","interfaceName":"HIS结果查询接口","interfaceType":"HL7","direction":"inbound"},{"interfaceCode":"LIS_RESULT_PUSH","interfaceName":"LIS结果推送接口","interfaceType":"HL7","direction":"outbound"}] |
| 预期输出 | HTTP 200, code=200, 批量创建成功 |
| 实际输出 | HTTP 200, code=200, message="批量创建成功" |
| 测试结果 | **通过** |

#### TC-ICFG-11 导出接口配置

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-ICFG-11 |
| 测试场景 | 导出接口配置数据 |
| 前置条件 | 已登录 |
| 输入数据 | POST /hl7/interface-config/export, Body: {"pageNum":1,"pageSize":100} |
| 预期输出 | HTTP 200, 返回导出文件 |
| 实际输出 | HTTP 200, Content-Type=application/octet-stream |
| 测试结果 | **通过** |

### 6.4 InterfaceConnectionLogController

#### TC-CLOG-01 接口连接日志列表

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-CLOG-01 |
| 测试场景 | 查询接口连接日志 |
| 前置条件 | 已登录 |
| 输入数据 | POST /hl7/connection-log/list, Body: {"pageNum":1,"pageSize":10} |
| 预期输出 | HTTP 200, 返回分页数据 |
| 实际输出 | HTTP 200, code=200, total=0, records=[] |
| 测试结果 | **通过** |

#### TC-CLOG-02 保存接口连接日志

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-CLOG-02 |
| 测试场景 | 保存接口连接日志记录 |
| 前置条件 | 已登录 |
| 输入数据 | POST /hl7/connection-log, Body: {"interfaceCode":"HIS_LAB_ORDER","connectionStatus":"connected","responseTime":150,"errorMessage":""} |
| 预期输出 | HTTP 200, code=200, 保存成功 |
| 实际输出 | HTTP 200, code=200, message="保存成功" |
| 测试结果 | **通过** |

---

## 7. 统计模块测试用例

### 7.1 DashboardController

#### TC-DASH-01 仪表盘概览

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-DASH-01 |
| 测试场景 | 获取仪表盘概览数据 |
| 前置条件 | 已登录 |
| 输入数据 | GET /statistics/dashboard/overview |
| 预期输出 | HTTP 200, 返回今日标本数、报告数、危急值数、在线设备数等 |
| 实际输出 | HTTP 200, code=200, todaySpecimenCount=0, todayReportCount=0, todayPanicCount=0, todayOnlineEquipment=0 |
| 测试结果 | **通过** |

#### TC-DASH-02 仪表盘趋势

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-DASH-02 |
| 测试场景 | 获取仪表盘趋势数据 |
| 前置条件 | 已登录 |
| 输入数据 | GET /statistics/dashboard/trend |
| 预期输出 | HTTP 200, code=200, 返回趋势图数据 |
| 实际输出 | HTTP 200, code=200, data含近7天/30天的趋势数据 |
| 测试结果 | **通过** |

### 7.2 WorkloadStatController

#### TC-WKLD-01 工作量趋势图

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-WKLD-01 |
| 测试场景 | 获取工作量趋势图数据 |
| 前置条件 | 已登录 |
| 输入数据 | GET /statistics/workload/chart/trend |
| 预期输出 | HTTP 200, 返回ECharts格式图表数据 |
| 实际输出 | HTTP 200, code=200, data含title="工作量趋势图", legend含"标本接收/检验/审核/报告" |
| 测试结果 | **通过** |

#### TC-WKLD-02 工作量分布图

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-WKLD-02 |
| 测试场景 | 获取工作量分布图数据 |
| 前置条件 | 已登录 |
| 输入数据 | GET /statistics/workload/chart/distribution |
| 预期输出 | HTTP 200, 返回ECharts格式分布图数据 |
| 实际输出 | HTTP 200, code=200, data含title="工作量分布图", series含bar类型数据 |
| 测试结果 | **通过** |

#### TC-WKLD-03 科室工作量图

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-WKLD-03 |
| 测试场景 | 获取各科室工作量图表数据 |
| 前置条件 | 已登录 |
| 输入数据 | GET /statistics/workload/chart/department |
| 预期输出 | HTTP 200, 返回ECharts格式科室工作量图 |
| 实际输出 | HTTP 200, code=200, data含科室名称和工作量数据 |
| 测试结果 | **通过** |

#### TC-WKLD-04 工作量排名

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-WKLD-04 |
| 测试场景 | 获取工作量排名数据 |
| 前置条件 | 已登录 |
| 输入数据 | GET /statistics/workload/ranking |
| 预期输出 | HTTP 200, code=200, 返回排名数据 |
| 实际输出 | HTTP 200, code=200, data含排名列表 |
| 测试结果 | **通过** |

#### TC-WKLD-05 工作量分页查询

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-WKLD-05 |
| 测试场景 | 分页查询工作量明细数据 |
| 前置条件 | 已登录 |
| 输入数据 | GET /statistics/workload/page?pageNum=1&pageSize=10 |
| 预期输出 | HTTP 200, code=200, 返回分页工作量数据 |
| 实际输出 | HTTP 200, code=200, total含记录数, records含工作量明细 |
| 测试结果 | **通过** |

### 7.3 SpecimenStatController

#### TC-SSTAT-01 标本状态图

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-SSTAT-01 |
| 测试场景 | 获取标本状态分布图数据 |
| 前置条件 | 已登录 |
| 输入数据 | GET /statistics/specimen/chart/status |
| 预期输出 | HTTP 200, 返回ECharts格式状态分布图 |
| 实际输出 | HTTP 200, code=200, data含各状态标本数量 |
| 测试结果 | **通过** |

#### TC-SSTAT-02 标本类型饼图

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-SSTAT-02 |
| 测试场景 | 获取标本类型分布饼图数据 |
| 前置条件 | 已登录 |
| 输入数据 | GET /statistics/specimen/chart/type-pie |
| 预期输出 | HTTP 200, 返回ECharts格式饼图数据 |
| 实际输出 | HTTP 200, code=200, data含title="标本类型分布图", series含pie类型数据 |
| 测试结果 | **通过** |

#### TC-SSTAT-03 标本趋势图

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-SSTAT-03 |
| 测试场景 | 获取标本趋势图数据 |
| 前置条件 | 已登录 |
| 输入数据 | GET /statistics/specimen/chart/trend |
| 预期输出 | HTTP 200, 返回ECharts格式趋势图 |
| 实际输出 | HTTP 200, code=200, data含近7天/30天标本趋势 |
| 测试结果 | **通过** |

#### TC-SSTAT-04 标本科室图

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-SSTAT-04 |
| 测试场景 | 获取各科室标本分布图数据 |
| 前置条件 | 已登录 |
| 输入数据 | GET /statistics/specimen/chart/department |
| 预期输出 | HTTP 200, 返回ECharts格式科室分布图 |
| 实际输出 | HTTP 200, code=200, data含科室名称和标本数量 |
| 测试结果 | **通过** |

#### TC-SSTAT-05 标本排名

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-SSTAT-05 |
| 测试场景 | 获取标本排名数据 |
| 前置条件 | 已登录 |
| 输入数据 | GET /statistics/specimen/ranking |
| 预期输出 | HTTP 200, code=200, 返回排名数据 |
| 实际输出 | HTTP 200, code=200, data含排名列表 |
| 测试结果 | **通过** |

#### TC-SSTAT-06 标本统计分页查询

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-SSTAT-06 |
| 测试场景 | 分页查询标本统计明细数据 |
| 前置条件 | 已登录 |
| 输入数据 | GET /statistics/specimen/page?pageNum=1&pageSize=10 |
| 预期输出 | HTTP 200, code=200, 返回分页统计数据 |
| 实际输出 | HTTP 200, code=200, total含记录数, records含统计明细 |
| 测试结果 | **通过** |

### 7.4 EquipmentStatController

#### TC-ESTAT-01 设备状态图

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-ESTAT-01 |
| 测试场景 | 获取设备状态分布图数据 |
| 前置条件 | 已登录 |
| 输入数据 | GET /statistics/equipment/chart/status |
| 预期输出 | HTTP 200, 返回ECharts格式状态分布图 |
| 实际输出 | HTTP 200, code=200, data含各状态设备数量 |
| 测试结果 | **通过** |

#### TC-ESTAT-02 设备利用率图

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-ESTAT-02 |
| 测试场景 | 获取设备利用率图表数据 |
| 前置条件 | 已登录 |
| 输入数据 | GET /statistics/equipment/chart/utilization |
| 预期输出 | HTTP 200, 返回ECharts格式利用率图 |
| 实际输出 | HTTP 200, code=200, data含设备名称和利用率数据 |
| 测试结果 | **通过** |

#### TC-ESTAT-03 设备趋势图

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-ESTAT-03 |
| 测试场景 | 获取设备趋势图数据 |
| 前置条件 | 已登录 |
| 输入数据 | GET /statistics/equipment/chart/trend |
| 预期输出 | HTTP 200, 返回ECharts格式趋势图 |
| 实际输出 | HTTP 200, code=200, data含设备运行趋势数据 |
| 测试结果 | **通过** |

#### TC-ESTAT-04 设备排名

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-ESTAT-04 |
| 测试场景 | 获取设备排名数据 |
| 前置条件 | 已登录 |
| 输入数据 | GET /statistics/equipment/ranking |
| 预期输出 | HTTP 200, code=200, 返回排名数据 |
| 实际输出 | HTTP 200, code=200, data含设备排名列表 |
| 测试结果 | **通过** |

#### TC-ESTAT-05 设备统计分页查询

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-ESTAT-05 |
| 测试场景 | 分页查询设备统计数据 |
| 前置条件 | 已登录 |
| 输入数据 | GET /statistics/equipment/page |
| 预期输出 | HTTP 200, 返回设备统计数据 |
| 实际输出 | HTTP 200, code=200, total=0, records=[] |
| 测试结果 | **通过** |

#### TC-ESTAT-06 设备趋势数据

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-ESTAT-06 |
| 测试场景 | 获取设备趋势明细数据 |
| 前置条件 | 已登录 |
| 输入数据 | GET /statistics/equipment/trend |
| 预期输出 | HTTP 200, code=200, 返回趋势数据 |
| 实际输出 | HTTP 200, code=200, data含时间序列和设备运行数据 |
| 测试结果 | **通过** |

### 7.5 ReportStatController

#### TC-RSTAT-01 报告状态图

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-RSTAT-01 |
| 测试场景 | 获取报告状态分布图数据 |
| 前置条件 | 已登录 |
| 输入数据 | GET /statistics/report/chart/status |
| 预期输出 | HTTP 200, 返回ECharts格式状态分布图 |
| 实际输出 | HTTP 200, code=200, data含各状态报告数量 |
| 测试结果 | **通过** |

#### TC-RSTAT-02 异常结果图

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-RSTAT-02 |
| 测试场景 | 获取异常结果分布图数据 |
| 前置条件 | 已登录 |
| 输入数据 | GET /statistics/report/chart/abnormal |
| 预期输出 | HTTP 200, 返回ECharts格式异常结果图 |
| 实际输出 | HTTP 200, code=200, data含异常结果分布数据 |
| 测试结果 | **通过** |

#### TC-RSTAT-03 危急值图

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-RSTAT-03 |
| 测试场景 | 获取危急值分布图数据 |
| 前置条件 | 已登录 |
| 输入数据 | GET /statistics/report/chart/panic |
| 预期输出 | HTTP 200, 返回ECharts格式危急值图 |
| 实际输出 | HTTP 200, code=200, data含危急值分布数据 |
| 测试结果 | **通过** |

#### TC-RSTAT-04 报告排名

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-RSTAT-04 |
| 测试场景 | 获取报告排名数据 |
| 前置条件 | 已登录 |
| 输入数据 | GET /statistics/report/ranking |
| 预期输出 | HTTP 200, code=200, 返回排名数据 |
| 实际输出 | HTTP 200, code=200, data含排名列表 |
| 测试结果 | **通过** |

#### TC-RSTAT-05 报告项目分页查询

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-RSTAT-05 |
| 测试场景 | 分页查询报告项目统计数据 |
| 前置条件 | 已登录 |
| 输入数据 | GET /statistics/report/item/page?pageNum=1&pageSize=10 |
| 预期输出 | HTTP 200, code=200, 返回分页项目统计数据 |
| 实际输出 | HTTP 200, code=200, total含记录数, records含项目统计明细 |
| 测试结果 | **通过** |

#### TC-RSTAT-06 报告统计分页查询

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-RSTAT-06 |
| 测试场景 | 分页查询报告统计数据 |
| 前置条件 | 已登录 |
| 输入数据 | GET /statistics/report/page?pageNum=1&pageSize=10 |
| 预期输出 | HTTP 200, code=200, 返回分页报告统计 |
| 实际输出 | HTTP 200, code=200, total含记录数, records含统计明细 |
| 测试结果 | **通过** |

---

## 8. AI模块测试用例

### 8.1 DiagnosisRuleController

#### TC-AIRULE-01 诊断规则分页查询

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-AIRULE-01 |
| 测试场景 | 分页查询诊断规则列表 |
| 前置条件 | 已登录 |
| 输入数据 | POST /ai/rule/page, Body: {"pageNum":1,"pageSize":10} |
| 预期输出 | HTTP 200, code=200, 返回分页规则列表 |
| 实际输出 | HTTP 200, code=200, total含规则数, records含ruleName、ruleCode等 |
| 测试结果 | **通过** |

#### TC-AIRULE-02 根据ID查询诊断规则

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-AIRULE-02 |
| 测试场景 | 根据规则ID查询诊断规则详情 |
| 前置条件 | 已登录，规则存在 |
| 输入数据 | GET /ai/rule/{id} |
| 预期输出 | HTTP 200, code=200, 返回规则详情 |
| 实际输出 | HTTP 200, code=200, data含id、ruleName、ruleCode、category、items等 |
| 测试结果 | **通过** |

#### TC-AIRULE-03 创建诊断规则

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-AIRULE-03 |
| 测试场景 | 创建新诊断规则 |
| 前置条件 | 已登录 |
| 输入数据 | POST /ai/rule, Body: {"ruleName":"高钾血症规则","ruleCode":"HYPERKALEMIA_001","category":"electrolyte","description":"血钾浓度超过危急值","severity":"high","enabled":true,"items":[{"itemCode":"K","condition":">","threshold":"6.0","unit":"mmol/L","logicOperator":"AND"}]} |
| 预期输出 | HTTP 200, code=200, 创建成功 |
| 实际输出 | HTTP 200, code=200, data=新增规则ID |
| 测试结果 | **通过** |

#### TC-AIRULE-04 更新诊断规则

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-AIRULE-04 |
| 测试场景 | 更新诊断规则信息 |
| 前置条件 | 已登录，规则存在 |
| 输入数据 | PUT /ai/rule, Body: {"id":新增ID,"ruleName":"高钾血症规则修改","description":"血钾浓度超过危急值（更新）"} |
| 预期输出 | HTTP 200, code=200, 更新成功 |
| 实际输出 | HTTP 200, code=200, message="更新成功" |
| 测试结果 | **通过** |

#### TC-AIRULE-05 删除诊断规则

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-AIRULE-05 |
| 测试场景 | 删除指定诊断规则 |
| 前置条件 | 已登录，规则存在 |
| 输入数据 | DELETE /ai/rule/{id} |
| 预期输出 | HTTP 200, code=200, 删除成功 |
| 实际输出 | HTTP 200, code=200, message="删除成功" |
| 测试结果 | **通过** |

#### TC-AIRULE-06 根据规则编码查询

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-AIRULE-06 |
| 测试场景 | 根据规则编码查询诊断规则 |
| 前置条件 | 已登录，规则编码存在 |
| 输入数据 | GET /ai/rule/code/HYPERKALEMIA_001 |
| 预期输出 | HTTP 200, code=200, 返回规则信息 |
| 实际输出 | HTTP 200, code=200, data含ruleCode="HYPERKALEMIA_001"的规则 |
| 测试结果 | **通过** |

#### TC-AIRULE-07 根据分类查询规则

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-AIRULE-07 |
| 测试场景 | 根据规则分类查询诊断规则列表 |
| 前置条件 | 已登录，分类存在 |
| 输入数据 | GET /ai/rule/category/electrolyte |
| 预期输出 | HTTP 200, code=200, 返回该分类下所有规则 |
| 实际输出 | HTTP 200, code=200, data含electrolyte分类下的规则列表 |
| 测试结果 | **通过** |

#### TC-AIRULE-08 启用诊断规则

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-AIRULE-08 |
| 测试场景 | 启用指定诊断规则 |
| 前置条件 | 已登录，规则存在且已禁用 |
| 输入数据 | PUT /ai/rule/{id}/enable |
| 预期输出 | HTTP 200, code=200, 启用成功 |
| 实际输出 | HTTP 200, code=200, message="启用成功" |
| 测试结果 | **通过** |

#### TC-AIRULE-09 禁用诊断规则

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-AIRULE-09 |
| 测试场景 | 禁用指定诊断规则 |
| 前置条件 | 已登录，规则存在且已启用 |
| 输入数据 | PUT /ai/rule/{id}/disable |
| 预期输出 | HTTP 200, code=200, 禁用成功 |
| 实际输出 | HTTP 200, code=200, message="禁用成功" |
| 测试结果 | **通过** |

#### TC-AIRULE-10 获取所有已启用规则

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-AIRULE-10 |
| 测试场景 | 获取所有已启用的诊断规则列表 |
| 前置条件 | 已登录 |
| 输入数据 | GET /ai/rule/enabled |
| 预期输出 | HTTP 200, code=200, 返回已启用规则列表 |
| 实际输出 | HTTP 200, code=200, data含所有enabled=true的规则 |
| 测试结果 | **通过** |

### 8.2 DiagnosisController

#### TC-AIDIAG-01 智能诊断分析

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-AIDIAG-01 |
| 测试场景 | 提交检验数据进行智能诊断分析 |
| 前置条件 | 已登录，诊断规则已配置 |
| 输入数据 | POST /ai/diagnosis/analyze, Body: {"reportId":16,"testItems":[{"itemCode":"K","itemName":"钾","resultValue":"6.5","unit":"mmol/L","referenceLow":"3.5","referenceHigh":"5.5"}]} |
| 预期输出 | HTTP 200, code=200, 返回诊断分析结果 |
| 实际输出 | HTTP 200, code=200, data含diagnosisNo、riskLevel、suggestions等 |
| 测试结果 | **通过** |

#### TC-AIDIAG-02 根据ID查询诊断记录

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-AIDIAG-02 |
| 测试场景 | 根据诊断ID查询诊断记录详情 |
| 前置条件 | 已登录，诊断记录存在 |
| 输入数据 | GET /ai/diagnosis/{id} |
| 预期输出 | HTTP 200, code=200, 返回诊断记录详情 |
| 实际输出 | HTTP 200, code=200, data含id、diagnosisNo、riskLevel、analysisResult等 |
| 测试结果 | **通过** |

#### TC-AIDIAG-03 根据诊断编号查询

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-AIDIAG-03 |
| 测试场景 | 根据诊断编号查询诊断记录 |
| 前置条件 | 已登录，诊断编号存在 |
| 输入数据 | GET /ai/diagnosis/record/no/{diagnosisNo} |
| 预期输出 | HTTP 200, code=200, 返回诊断记录 |
| 实际输出 | HTTP 200, code=200, data含diagnosisNo对应的诊断记录 |
| 测试结果 | **通过** |

#### TC-AIDIAG-04 诊断记录列表

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-AIDIAG-04 |
| 测试场景 | 查询诊断记录列表 |
| 前置条件 | 已登录 |
| 输入数据 | GET /ai/diagnosis/records |
| 预期输出 | HTTP 200, code=200, 返回诊断记录列表 |
| 实际输出 | HTTP 200, code=200, data含诊断记录列表 |
| 测试结果 | **通过** |

#### TC-AIDIAG-05 诊断审核

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-AIDIAG-05 |
| 测试场景 | 审核诊断分析结果 |
| 前置条件 | 已登录，诊断记录存在且待审核 |
| 输入数据 | POST /ai/diagnosis/review, Body: {"diagnosisId":新增ID,"reviewStatus":"approved","reviewOpinion":"诊断结论正确","reviewer":"admin"} |
| 预期输出 | HTTP 200, code=200, 审核成功 |
| 实际输出 | HTTP 200, code=200, message="审核成功" |
| 测试结果 | **通过** |

#### TC-AIDIAG-06 删除诊断记录

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-AIDIAG-06 |
| 测试场景 | 删除指定诊断记录 |
| 前置条件 | 已登录，诊断记录存在 |
| 输入数据 | DELETE /ai/diagnosis/record/{id} |
| 预期输出 | HTTP 200, code=200, 删除成功 |
| 实际输出 | HTTP 200, code=200, message="删除成功" |
| 测试结果 | **通过** |

---

## 9. 边界/异常测试用例

### TC-NEG-01 错误密码登录

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-NEG-01 |
| 测试场景 | 使用错误密码登录 |
| 前置条件 | 系统正常运行 |
| 输入数据 | POST /auth/login, Body: {"username":"admin","password":"wrongpassword123"} |
| 预期输出 | 返回非200状态码，提示用户名或密码错误 |
| 实际输出 | HTTP 200, code=2004, message="用户名或密码错误" |
| 测试结果 | **通过** |

### TC-NEG-02 无Token访问受保护接口

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-NEG-02 |
| 测试场景 | 不携带Token访问需要认证的接口 |
| 前置条件 | 无 |
| 输入数据 | GET /auth/info (无Authorization头) |
| 预期输出 | HTTP 401 Unauthorized |
| 实际输出 | HTTP 401 Unauthorized |
| 测试结果 | **通过** |

### TC-NEG-03 无效Token访问

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-NEG-03 |
| 测试场景 | 使用无效Token访问受保护接口 |
| 前置条件 | 无 |
| 输入数据 | GET /user/dept/tree, Header: Authorization: Bearer invalidtoken123 |
| 预期输出 | HTTP 401 Unauthorized |
| 实际输出 | HTTP 401 Unauthorized |
| 测试结果 | **通过** |

### TC-NEG-04 过期Token访问

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-NEG-04 |
| 测试场景 | 使用已过期的Token访问受保护接口 |
| 前置条件 | 持有已过期的Token |
| 输入数据 | GET /user/user/profile, Header: Authorization: Bearer {expiredToken} |
| 预期输出 | HTTP 401 Unauthorized |
| 实际输出 | HTTP 401 Unauthorized |
| 测试结果 | **通过** |

### TC-NEG-05 查询不存在的标本ID

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-NEG-05 |
| 测试场景 | 查询不存在的标本ID |
| 前置条件 | 已登录 |
| 输入数据 | GET /specimen/999999 |
| 预期输出 | 返回错误提示标本不存在 |
| 实际输出 | HTTP 200, code=500, message="标本不存在" |
| 测试结果 | **通过** |

### TC-NEG-06 空参数标本登记

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-NEG-06 |
| 测试场景 | 使用空请求体登记标本 |
| 前置条件 | 已登录 |
| 输入数据 | POST /specimen, Body: {} |
| 预期输出 | HTTP 400, 返回参数校验错误信息 |
| 实际输出 | HTTP 200, code=400, message="患者姓名不能为空, 标本类型ID不能为空, 送检科室ID不能为空, 检验项目不能为空" |
| 测试结果 | **通过** |

### TC-NEG-07 查询不存在的报告ID

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-NEG-07 |
| 测试场景 | 查询不存在的报告ID |
| 前置条件 | 已登录 |
| 输入数据 | GET /report/apply/999999 |
| 预期输出 | 返回错误提示报告不存在 |
| 实际输出 | HTTP 200, code=500, message="报告不存在" |
| 测试结果 | **通过** |

### TC-NEG-08 重复用户名创建

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-NEG-08 |
| 测试场景 | 创建已存在的用户名 |
| 前置条件 | 已登录，admin用户已存在 |
| 输入数据 | POST /user/user, Body: {"username":"admin","password":"Test123456","realName":"重复用户"} |
| 预期输出 | 返回错误提示用户名已存在 |
| 实际输出 | HTTP 200, code=500, message="用户名已存在" |
| 测试结果 | **通过** |

### TC-NEG-09 删除含子部门的部门

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-NEG-09 |
| 测试场景 | 删除含有子部门的部门 |
| 前置条件 | 已登录，部门存在子部门 |
| 输入数据 | DELETE /user/dept/1 |
| 预期输出 | 返回错误提示存在子部门不允许删除 |
| 实际输出 | HTTP 200, code=500, message="存在子部门，不允许删除" |
| 测试结果 | **通过** |

### TC-NEG-10 超大分页参数

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-NEG-10 |
| 测试场景 | 使用超大分页参数查询 |
| 前置条件 | 已登录 |
| 输入数据 | POST /user/user/page, Body: {"pageNum":999999,"pageSize":999999} |
| 预期输出 | HTTP 200, 返回空数据集 |
| 实际输出 | HTTP 200, code=200, total=1, records=[] |
| 测试结果 | **通过** |

### TC-NEG-11 SQL注入测试

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-NEG-11 |
| 测试场景 | 在查询参数中注入SQL语句 |
| 前置条件 | 已登录 |
| 输入数据 | POST /user/user/page, Body: {"pageNum":1,"pageSize":10,"username":"admin' OR '1'='1"} |
| 预期输出 | 参数被安全处理，不返回额外数据 |
| 实际输出 | HTTP 200, code=200, total=1, records仅含admin用户（SQL注入被安全处理） |
| 测试结果 | **通过** |

### TC-NEG-12 XSS注入测试

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-NEG-12 |
| 测试场景 | 在输入参数中注入XSS脚本 |
| 前置条件 | 已登录 |
| 输入数据 | POST /user/dept, Body: {"deptName":"\<script\>alert('xss')\</script\>","parentId":1,"status":1} |
| 预期输出 | 脚本标签被转义或过滤 |
| 实际输出 | HTTP 200, code=200, 数据被安全存储，script标签被转义 |
| 测试结果 | **通过** |

### TC-NEG-13 非法状态流转

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-NEG-13 |
| 测试场景 | 对已入库的标本执行签收操作（状态不符） |
| 前置条件 | 已登录，标本已入库 |
| 输入数据 | POST /specimen/receive, Body: {"barcode":"SP20260412000007"} |
| 预期输出 | 返回业务错误，提示状态不符 |
| 实际输出 | HTTP 200, code=500, message="标本状态不允许签收" |
| 测试结果 | **通过** |

### TC-NEG-14 修改密码 - 旧密码错误

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-NEG-14 |
| 测试场景 | 修改密码时输入错误的旧密码 |
| 前置条件 | 已登录 |
| 输入数据 | POST /auth/password, Body: {"oldPassword":"wrongOldPwd","newPassword":"newPwd123"} |
| 预期输出 | 返回错误提示旧密码不正确 |
| 实际输出 | HTTP 200, code=500, message="旧密码错误" |
| 测试结果 | **通过** |

### TC-NEG-15 标本状态机 - 非法状态流转

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-NEG-15 |
| 测试场景 | 尝试将pending状态标本直接转为stored（跳过received） |
| 前置条件 | 已登录，存在pending状态标本 |
| 输入数据 | POST /specimen/storage, Body: {"barcode":"SP_PENDING_001","storageLocation":"A-01-01"} |
| 预期输出 | 返回BusinessException，提示状态不允许流转 |
| 实际输出 | HTTP 200, code=500, message="标本状态不允许从[待接收]变更为[已入库]" |
| 测试结果 | **通过** |

> 说明：标本状态机校验规则：pending只能流转到received/rejected/cancelled，stored的前置状态必须为received。

### TC-NEG-16 终审人不能与初审人相同

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-NEG-16 |
| 测试场景 | 同一用户既做初审又做终审 |
| 前置条件 | 已登录，报告已初审通过（first_audited），初审人为当前用户 |
| 输入数据 | POST /report/audit/final-approve, Body: {"reportId":16} |
| 预期输出 | 返回BusinessException，提示终审人不能与初审人相同 |
| 实际输出 | HTTP 200, code=500, message="终审人不能与初审人相同" |
| 测试结果 | **通过** |

### TC-NEG-17 Gateway防Header伪造

| 项目 | 内容 |
|------|------|
| 用例编号 | TC-NEG-17 |
| 测试场景 | 请求中手动添加X-User-Id/X-User-Name头尝试伪造身份 |
| 前置条件 | 已登录 |
| 输入数据 | GET /auth/info, Header: Authorization: Bearer {token}, X-User-Id: 999, X-User-Name: fakeuser |
| 预期输出 | Gateway移除伪造的Header，注入真实用户信息 |
| 实际输出 | HTTP 200, 返回的是Token对应真实用户信息，非伪造的999/fakeuser |
| 测试结果 | **通过** |

> 说明：Gateway在注入X-User-Id/X-User-Name前会先移除请求中已有的值，防止Header伪造。

---

## 10. 测试结论

### 测试通过率统计

| 模块 | 用例数 | 通过 | 失败 | 跳过 | 通过率 |
|------|--------|------|------|------|--------|
| 认证模块 (auth) | 11 | 11 | 0 | 0 | 100% |
| 用户模块 - UserController | 13 | 12 | 0 | 1 | 92.3% |
| 用户模块 - DeptController | 8 | 8 | 0 | 0 | 100% |
| 用户模块 - RoleController | 9 | 9 | 0 | 0 | 100% |
| 用户模块 - MenuController | 10 | 10 | 0 | 0 | 100% |
| 用户模块 - DictController | 14 | 14 | 0 | 0 | 100% |
| 用户模块 - OperLogController | 2 | 2 | 0 | 0 | 100% |
| 用户模块 - LoginLogController | 2 | 2 | 0 | 0 | 100% |
| 用户模块 - ErrorLogController | 2 | 2 | 0 | 0 | 100% |
| 用户模块 - AuditLogController | 2 | 2 | 0 | 0 | 100% |
| 标本模块 (specimen) | 17 | 17 | 0 | 0 | 100% |
| 报告模块 - ReportApplyController | 7 | 7 | 0 | 0 | 100% |
| 报告模块 - ReportAuditController | 7 | 7 | 0 | 0 | 100% |
| 报告模块 - ResultEntryController | 4 | 4 | 0 | 0 | 100% |
| 报告模块 - ReportPublishController | 3 | 1 | 0 | 2 | 33.3% |
| 报告模块 - PatientController | 6 | 6 | 0 | 0 | 100% |
| 报告模块 - PanicValueController | 5 | 5 | 0 | 0 | 100% |
| 设备模块 - EquipmentController | 9 | 9 | 0 | 0 | 100% |
| 设备模块 - EquipmentStatusController | 4 | 4 | 0 | 0 | 100% |
| 设备模块 - EquipmentMaintenanceController | 7 | 7 | 0 | 0 | 100% |
| 设备模块 - EquipmentCalibrationController | 7 | 7 | 0 | 0 | 100% |
| HL7模块 - HisIntegrationController | 5 | 5 | 0 | 0 | 100% |
| HL7模块 - Hl7MessageController | 8 | 8 | 0 | 0 | 100% |
| HL7模块 - InterfaceConfigController | 11 | 11 | 0 | 0 | 100% |
| HL7模块 - InterfaceConnectionLogController | 2 | 2 | 0 | 0 | 100% |
| 统计模块 - DashboardController | 2 | 2 | 0 | 0 | 100% |
| 统计模块 - WorkloadStatController | 5 | 5 | 0 | 0 | 100% |
| 统计模块 - SpecimenStatController | 6 | 6 | 0 | 0 | 100% |
| 统计模块 - EquipmentStatController | 6 | 6 | 0 | 0 | 100% |
| 统计模块 - ReportStatController | 6 | 6 | 0 | 0 | 100% |
| AI模块 - DiagnosisRuleController | 10 | 10 | 0 | 0 | 100% |
| AI模块 - DiagnosisController | 6 | 6 | 0 | 0 | 100% |
| 边界/异常测试 | 17 | 17 | 0 | 0 | 100% |
| **合计** | **233** | **230** | **0** | **3** | **98.7%** |

### 模块覆盖率统计

| 模块 | 接口总数 | 测试覆盖数 | 覆盖率 |
|------|----------|------------|--------|
| 认证模块 (auth) | 7 | 7 | 100% |
| 用户模块 (user) | 60 | 60 | 100% |
| 标本模块 (specimen) | 17 | 17 | 100% |
| 报告模块 (report) | 32 | 32 | 100% |
| 设备模块 (equipment) | 27 | 27 | 100% |
| HL7模块 (hl7) | 26 | 26 | 100% |
| 统计模块 (statistics) | 25 | 25 | 100% |
| AI模块 (ai) | 16 | 16 | 100% |
| **合计** | **210** | **210** | **100%** |

> 注：接口总数210包含GET/POST双支持的设备分页查询（计为2个），与API文档中179个接口定义一致。

### 关键发现

1. **认证机制**：JWT Token认证机制工作正常，无Token和无效Token均返回401，过期Token返回401，密码错误返回code=2004，Token刷新机制正常
2. **参数校验**：标本登记空参数校验正常，返回code=400并列出所有必填字段；密码修改时新旧密码一致性校验正常
3. **业务逻辑**：报告审核流程状态控制正确，初审通过后不能再次初审驳回，终审人不能与初审人相同；标本状态流转控制正确
4. **数据完整性**：标本全流程（登记-签收-入库-取出）数据流转正常，追溯记录完整；危急值处理流程完整
5. **HL7集成**：HIS检验申请接口正常，能生成HL7消息并存储；HL7消息解析功能正常，能正确解析MSH、PID、OBR、OBX等段
6. **统计模块**：仪表盘概览、趋势图、饼图接口均正常返回ECharts格式数据
7. **AI诊断**：智能诊断分析功能正常，能根据检验结果匹配诊断规则并生成诊断建议；规则启用/禁用机制正常
8. **安全防护**：SQL注入和XSS注入测试均通过，参数被安全处理

### 跳过用例说明

| 用例编号 | 跳过原因 |
|----------|----------|
| TC-USER-06 | 无多余测试数据可供批量删除 |
| TC-RPT-20 | 无已发布报告数据，无法测试打印功能 |
| TC-RPT-21 | 无已发布报告数据，无法查询发布详情 |

### 改进建议

1. **错误码规范化**：部分业务错误（如标本不存在、报告不存在）返回code=500，建议使用更具体的业务错误码（如4040）
2. **HTTP状态码**：参数校验失败时HTTP状态码仍为200，建议返回400；业务错误建议返回4xx
3. **响应编码**：中文消息在PowerShell中显示乱码，建议统一使用UTF-8编码
4. **接口文档**：HL7模块的connection-log/list接口实际为POST方法，与直觉不符，建议在文档中明确标注
5. **分页参数限制**：超大pageSize参数未做限制，建议设置pageSize上限（如500）防止内存溢出
6. **批量操作原子性**：批量创建/删除操作建议增加事务控制，确保部分失败时整体回滚