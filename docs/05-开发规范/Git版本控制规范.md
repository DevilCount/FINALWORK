# Git版本控制规范

> **版本**：V1.0
> **适用项目**：基于微服务架构的实验室管理系统（LIS）
> **适用人员**：AI项目经理（PM）、9个AI子Agent
> **核心目标**：通过Git实现代码版本管理、变更追溯、质量监督和版本回退

---

## 1. 总则

### 1.1 版本控制基本原则

1. **所有代码和文档必须纳入Git版本控制**，禁止在版本控制之外修改任何项目文件
2. **每次变更必须有明确的提交记录**，提交信息必须符合本规范定义的格式
3. **项目经理（PM）拥有最终审批权**，所有合并到`main`和`develop`分支的代码必须经PM审批
4. **版本可追溯、可回退**，任何时间点都可以恢复到之前的稳定状态

### 1.2 仓库结构

```
/workspace/lis-project/                    # 项目根目录（Git仓库根）
├── docs/                                   # 项目文档
│   ├── 01-规划文档/
│   ├── 02-设计文档/
│   ├── 03-运维文档/
│   ├── 04-收尾文档/
│   ├── 05-开发规范/
│   └── process/                            # 过程文档（各Agent开发记录）
│       ├── Agent-00/
│       ├── Agent-01/
│       ├── ...
│       └── Agent-08/
├── src/                                    # 源代码
│   ├── backend/                            # 后端微服务
│   └── frontend/                           # 前端项目
├── sql/                                    # 数据库脚本
├── docker/                                 # Docker配置
├── reports/                                # 开发报告
│   ├── agent-reports/                      # Agent开发报告
│   └── templates/                          # 任务书模板
└── .gitignore                              # Git忽略配置
```

---

## 2. 分支策略

### 2.1 分支体系

本项目采用**简化的Git Flow**分支策略，适配AI多Agent并行开发场景：

```
main（生产稳定分支）
  │
  └── develop（开发集成分支）
        │
        ├── feature/agent-00-architecture     ← Agent-00 工作分支
        ├── feature/agent-01-frontend         ← Agent-01 工作分支
        ├── feature/agent-02-user-service     ← Agent-02 工作分支
        ├── feature/agent-03-specimen-service ← Agent-03 工作分支
        ├── feature/agent-04-report-service   ← Agent-04 工作分支
        ├── feature/agent-05-equipment-service← Agent-05 工作分支
        ├── feature/agent-06-hl7-service      ← Agent-06 工作分支
        ├── feature/agent-07-statistics-service← Agent-07 工作分支
        ├── feature/agent-08-ai-service       ← Agent-08 工作分支
        │
        ├── bugfix/agent-02-login-error       ← 缺陷修复分支
        ├── bugfix/agent-04-report-pdf        ← 缺陷修复分支
        │
        ├── release/v1.0.0                    ← 发布分支
        └── hotfix/critical-value-alert       ← 紧急修复分支
```

### 2.2 分支定义与权限

| 分支类型 | 命名规范 | 创建权限 | 合并权限 | 用途 |
|---------|---------|---------|---------|------|
| `main` | `main` | 仅PM | 仅PM | 生产稳定版本，每阶段验收通过后合并 |
| `develop` | `develop` | 仅PM | 仅PM | 开发集成分支，所有Agent功能的集成目标 |
| `feature/*` | `feature/{agent-id}-{module-name}` | PM创建/Agent使用 | 仅PM | 各Agent的功能开发分支 |
| `bugfix/*` | `bugfix/{agent-id}-{description}` | PM创建/Agent使用 | 仅PM | 缺陷修复分支 |
| `release/*` | `release/v{version}` | 仅PM | 仅PM | 发布准备分支 |
| `hotfix/*` | `hotfix/{description}` | 仅PM | 仅PM | 紧急修复分支 |

### 2.3 分支生命周期

```
阶段开始 → PM创建feature分支 → Agent在分支上开发 → Agent提交合并请求
    → PM审查代码+文档 → PM合并到develop → 阶段验收通过 → PM合并到main → 打Tag
```

### 2.4 各Agent分支分配

| Agent | 功能分支名 | 说明 |
|-------|-----------|------|
| Agent-00 | `feature/agent-00-architecture` | 架构搭建阶段 |
| Agent-01 | `feature/agent-01-frontend` | 前端开发阶段 |
| Agent-02 | `feature/agent-02-user-service` | 用户服务开发 |
| Agent-03 | `feature/agent-03-specimen-service` | 标本服务开发 |
| Agent-04 | `feature/agent-04-report-service` | 检验服务开发 |
| Agent-05 | `feature/agent-05-equipment-service` | 设备服务开发 |
| Agent-06 | `feature/agent-06-hl7-service` | HL7服务开发 |
| Agent-07 | `feature/agent-07-statistics-service` | 统计服务开发 |
| Agent-08 | `feature/agent-08-ai-service` | AI服务开发 |

---

## 3. 提交规范

### 3.1 提交信息格式

采用 **Conventional Commits** 规范的中文适配版：

```
[{Agent编号}] {类型}({范围}): {简述}

{详细描述（可选）}

{关联信息（可选）}
```

### 3.2 提交类型

| 类型 | 说明 | 示例 |
|------|------|------|
| `feat` | 新功能 | `[Agent-02] feat(user): 新增用户批量导入功能` |
| `fix` | 缺陷修复 | `[Agent-03] fix(specimen): 修复标本签收状态未更新问题` |
| `refactor` | 代码重构（不改变功能） | `[Agent-04] refactor(report): 重构报告审核流程` |
| `docs` | 文档变更 | `[Agent-00] docs(architecture): 更新系统架构图` |
| `test` | 测试相关 | `[Agent-02] test(user): 新增用户服务单元测试` |
| `chore` | 构建/工具/配置变更 | `[Agent-01] chore(deps): 升级Element Plus到2.5.0` |
| `style` | 代码格式调整（不影响逻辑） | `[Agent-01] style(login): 调整登录页样式` |
| `perf` | 性能优化 | `[Agent-07] perf(statistics): 优化统计查询SQL性能` |
| `revert` | 回退提交 | `[PM] revert: 回退Agent-02登录接口变更` |

### 3.3 提交范围

| 范围 | 说明 |
|------|------|
| `user` | 用户管理模块 |
| `specimen` | 标本管理模块 |
| `report` | 检验报告模块 |
| `equipment` | 设备管理模块 |
| `hl7` | HL7接口模块 |
| `statistics` | 数据统计模块 |
| `ai` | AI诊断模块 |
| `auth` | 认证授权模块 |
| `gateway` | API网关模块 |
| `common` | 公共模块 |
| `architecture` | 架构相关 |
| `database` | 数据库相关 |
| `docker` | Docker/部署相关 |
| `docs` | 文档相关 |
| `deps` | 依赖相关 |

### 3.4 提交规则

1. **原子性**：每次提交只做一件事（一个功能点、一个Bug修复、一次文档更新）
2. **完整性**：提交前确保代码可编译、测试通过
3. **规范性**：提交信息必须符合上述格式，PM有权驳回不符合规范的提交
4. **及时性**：每完成一个功能点立即提交，禁止积压大量变更后一次性提交
5. **文档同步**：代码变更和对应的过程文档应在同一次提交中（或紧邻的提交中）

### 3.5 提交示例

```bash
# 好的提交
git commit -m "[Agent-02] feat(user): 新增用户批量导入功能

- 实现Excel文件解析（支持xlsx格式）
- 支持用户数据校验（手机号、邮箱格式）
- 导入结果反馈（成功数、失败数、失败原因）

关联任务书：Agent-02-用户服务-开发任务书.md 第3.2节"

# 不好的提交
git commit -m "修改代码"          # 信息不明确
git commit -m "update"            # 未使用中文
git commit -m "fix bug"           # 未说明修复了什么Bug
git commit -m ""                  # 空提交信息（禁止）
```

---

## 4. Tag管理

### 4.1 Tag命名规范

```
v{主版本}.{次版本}.{修订号}[-{阶段标识}]
```

| 版本位 | 含义 | 触发条件 |
|--------|------|---------|
| 主版本 | 架构重大调整 | V1.0 → V2.0 |
| 次版本 | 新增功能模块/阶段完成 | V0.1 → V0.2 |
| 修订号 | Bug修复/小优化 | V0.2.0 → V0.2.1 |
| 阶段标识（可选） | 标识里程碑 | `-phase1-infra`、`-agent02-user` |

### 4.2 Tag规划

| Tag | 触发时机 | 说明 |
|-----|---------|------|
| `v0.1.0-phase1-infra` | 阶段1验收通过 | 基础设施搭建完成 |
| `v0.2.0-phase2-core` | 阶段2验收通过 | 核心后端服务完成 |
| `v0.3.0-phase3-extend` | 阶段3验收通过 | 扩展后端服务完成 |
| `v0.4.0-phase4-frontend` | 阶段4验收通过 | 前端开发完成 |
| `v0.5.0-phase5-test` | 阶段5验收通过 | 集成测试通过 |
| `v1.0.0-release` | 阶段6验收通过 | 正式发布 |

### 4.3 Tag管理规则

1. **仅PM有权限创建Tag**
2. Tag必须附有详细的标注信息（`git tag -a`）
3. Tag创建后不可修改（禁止`git tag -f`强制覆盖）
4. 每个Tag对应`main`分支上的一个稳定提交

```bash
# PM创建Tag示例
git tag -a v0.1.0-phase1-infra -m "阶段1完成：基础设施搭建
- Maven多模块项目搭建完成
- lis-common/lis-gateway/lis-auth可启动
- 7个数据库初始化完成
- Docker配置完成
验收人：AI项目经理Agent
验收时间：2026-XX-XX"
```

---

## 5. 合并策略

### 5.1 合并流程

```
Agent功能分支 ──→ develop ──→ main
     │                │           │
     │                │           └── PM验收通过后合并
     │                └── PM审查通过后合并
     └── Agent提交合并请求
```

### 5.2 合并规则

| 源分支 | 目标分支 | 合并方式 | 审批要求 |
|--------|---------|---------|---------|
| `feature/*` | `develop` | `--no-ff`（保留合并记录） | PM审查通过 |
| `develop` | `main` | `--no-ff` | PM验收通过 |
| `hotfix/*` | `main` + `develop` | `--no-ff` | PM立即执行 |
| `bugfix/*` | `develop` | `--no-ff` | PM审查通过 |
| `release/*` | `main` + `develop` | `--no-ff` | PM验收通过 |

### 5.3 合并前检查清单（PM执行）

- [ ] 提交信息符合规范
- [ ] 代码编译无错误
- [ ] 单元测试全部通过
- [ ] 过程文档已提交
- [ ] 无合并冲突（或冲突已正确解决）
- [ ] 数据库变更脚本（含回滚脚本）已提交
- [ ] 代码符合D08《编码规范》

### 5.4 冲突解决

1. **同Agent分支冲突**：由该Agent自行解决
2. **跨Agent冲突**（如多个Agent修改了lis-common）：由PM协调解决
3. **冲突解决后**：必须重新运行全量测试
4. **冲突记录**：PM在问题台账中记录冲突原因和解决方案

---

## 6. 版本回退机制

### 6.1 回退策略

| 回退方式 | 命令 | 适用场景 | 影响 |
|---------|------|---------|------|
| **提交级回退（推荐）** | `git revert <hash>` | 撤销特定提交，保留历史 | 创建新的反向提交，安全 |
| **分支级回退** | `git reset --hard <hash>` | Agent分支整体回退 | 丢弃提交历史，仅限独立分支 |
| **Tag回退** | 删除旧Tag，在新位置重建 | Tag打错位置 | 需PM审批 |
| **数据库回退** | 执行逆向SQL脚本 | 数据库结构/数据回退 | 需PM验证后执行 |

### 6.2 回退决策矩阵

| 问题严重度 | 回退方式 | 执行权限 | 通知范围 | 时限 |
|-----------|---------|---------|---------|------|
| **阻断级**（系统崩溃/数据丢失） | 分支级回退 + 数据库回退 | PM立即执行 | 全体Agent | 立即 |
| **严重级**（核心功能异常） | `git revert` 指定提交 | PM审批后执行 | 相关Agent | 30分钟内 |
| **一般级**（非核心功能异常） | 新建 `bugfix/*` 分支修复 | PM记录后执行 | 相关Agent | 当日内 |
| **建议级**（优化建议） | 记录到问题台账 | PM记录 | 无 | 下一迭代 |

### 6.3 回退操作规范

```bash
# 1. 提交级回退（推荐，保留历史）
git revert <commit-hash>                    # 回退单个提交
git revert <hash1> <hash2>                  # 回退多个提交
git revert --no-commit <hash>               # 回退但不自动提交（用于调整）

# 2. 分支级回退（仅在Agent独立分支使用）
git checkout feature/agent-02-user-service
git reset --hard <commit-hash>              # 回退到指定提交
git push -f origin feature/agent-02-user-service  # 强制推送（需PM确认）

# 3. 紧急回退develop分支
git checkout develop
git revert <problematic-commit-hash>        # 使用revert而非reset
git push origin develop

# 4. 数据库回退
# 执行Agent提供的逆向SQL脚本
mysql -u root -p lis_user < /workspace/lis-project/sql/rollback/V2.1.0__rollback.sql
```

### 6.4 回退后必做事项

1. PM通知所有相关Agent回退原因和影响范围
2. PM在问题台账中记录回退详情
3. PM指定负责Agent重新开发/修复
4. 修复完成后重新走完整的校验流程

---

## 7. PM监督工具箱

### 7.1 日常监控命令

```bash
# 查看整体提交历史（图形化）
git log --all --oneline --graph --decorate --date=short --pretty=format:"%h %ad %an %s"

# 查看所有分支状态
git branch -avv

# 查看工作区状态
git status

# 查看各Agent的提交统计
git shortlog -sn --all
```

### 7.2 Agent产出审查命令

```bash
# 查看某Agent的全部提交
git log --author="Agent-02" --oneline --date=short

# 查看某Agent的代码变更量
git log --author="Agent-02" --pretty=tformat: --numstat | \
  awk '{add+=$1; del+=$2} END {print "新增行数:", add, "删除行数:", del, "净增:", add-del}'

# 审查某次提交的详细变更
git show <commit-hash> --stat
git show <commit-hash>                     # 查看完整diff

# 查看某Agent分支与develop的差异
git diff develop...feature/agent-02-user-service --stat

# 查看某Agent最近一次提交的文件清单
git diff HEAD~1 HEAD --name-only --author="Agent-02"
```

### 7.3 质量检查命令

```bash
# 检查提交信息规范（查找不符合规范的提交）
git log --all --oneline | grep -vE "^\w+ \[Agent-\d+\] (feat|fix|refactor|docs|test|chore|style|perf|revert)\("

# 检查是否有直接在main/develop上的未授权提交
git log main --oneline | grep -v "\[PM\]"

# 检查大文件提交（可能包含非代码文件）
git log --all --diff-filter=A --summary | grep "create mode" | \
  awk '{print $NF}' | xargs -I{} du -h {} 2>/dev/null | sort -rh | head -20

# 检查是否有合并冲突未解决的残留
grep -r "<<<<<<< HEAD" /workspace/lis-project/src/ 2>/dev/null
grep -r "=======" /workspace/lis-project/src/ 2>/dev/null
grep -r ">>>>>>>" /workspace/lis-project/src/ 2>/dev/null
```

### 7.4 回退操作命令

```bash
# 查看回退目标（找到要回退到的稳定提交）
git log --oneline --all | head -20

# 查看某次提交的详细信息（确认回退目标）
git show <commit-hash> --stat

# 执行回退
git revert <commit-hash>                   # 安全回退
git revert --no-commit <commit-hash>       # 回退但暂不提交（用于批量回退）

# 查看回退结果
git log --oneline -5
git diff HEAD~1 HEAD --stat
```

---

## 8. 过程文档管理

### 8.1 过程文档要求

每个Agent在开发过程中必须生成过程文档，与代码同步提交到Git：

| 文档类型 | 存放路径 | 提交时机 | 负责人 |
|---------|---------|---------|--------|
| 功能开发记录 | `docs/process/{Agent编号}/{功能名}-开发记录.md` | 每个功能点完成时 | 各Agent |
| 阶段开发报告 | `reports/agent-reports/{Agent编号}-{模块名}-开发报告.md` | 每个阶段完成时 | 各Agent |
| 变更日志 | `docs/process/{Agent编号}/CHANGELOG.md` | 每次提交时更新 | 各Agent |
| 数据库变更脚本 | `sql/{agent-id}/V{version}__{description}.sql` | 数据库变更时 | 各Agent |
| 数据库回滚脚本 | `sql/{agent-id}/V{version}__rollback.sql` | 数据库变更时 | 各Agent |

### 8.2 过程文档模板

参见 `docs/process/开发记录模板.md`

### 8.3 过程文档校验

PM在审查Agent合并请求时，必须检查：
1. 过程文档是否与代码同步提交
2. 过程文档内容是否完整（开发时间、实现方案、测试结果）
3. 变更日志是否更新
4. 数据库变更是否附带回滚脚本

---

## 9. .gitignore配置

```gitignore
# Java
*.class
*.jar
*.war
target/
!.mvn/wrapper/maven-wrapper.jar

# Node.js
node_modules/
dist/
.cache/

# IDE
.idea/
*.iml
.vscode/
*.swp
*.swo

# OS
.DS_Store
Thumbs.db

# 日志
*.log
logs/

# 临时文件
*.tmp
*.bak
*.swp

# 环境配置（敏感信息）
application-local.yml
application-dev.yml
*.env
.env.local

# 数据库备份
*.sql.bak
*.dump

# 构建产物
/build/
/out/
```

---

## 10. Git初始化与首次提交

### 10.1 初始化命令（PM在项目启动时执行）

```bash
# 进入项目根目录
cd /workspace/lis-project

# 初始化Git仓库
git init

# 创建main分支
git checkout -b main

# 创建develop分支
git checkout -b develop

# 首次提交（项目文档和目录结构）
git add .
git commit -m "[PM] chore(init): 项目初始化

- 提交全部规划文档（D01-D03）
- 提交全部设计文档（D04-D07）
- 提交全部开发规范（D08-D10）
- 提交全部Agent任务书
- 创建项目目录结构"

# 回到main分支，同步首次提交
git checkout main
git merge develop --no-ff -m "[PM] chore(init): 合并初始项目文档到main"

# 打初始Tag
git tag -a v0.0.0-init -m "项目初始化：全部文档就绪"
```

### 10.2 Agent分支创建（PM在每个阶段开始时执行）

```bash
# 切换到develop
git checkout develop

# 创建Agent工作分支
git checkout -b feature/agent-00-architecture
# Agent-00开始开发...

# Agent-00完成后，PM审查并合并
git checkout develop
git merge --no-ff feature/agent-00-architecture -m "[PM] merge: 合并Agent-00架构搭建成果"

# 打阶段Tag
git tag -a v0.1.0-phase1-infra -m "阶段1完成：基础设施搭建"
```

---

## 11. 违规处理

| 违规行为 | 处理方式 |
|---------|---------|
| 提交信息不符合规范 | PM驳回提交，要求修改后重新提交 |
| 直接在main/develop上提交 | PM执行`git reset`回退，要求在feature分支上重新提交 |
| 未提交过程文档 | PM驳回合并请求，要求补齐文档 |
| 未提供数据库回滚脚本 | PM驳回合并请求，要求补齐回滚脚本 |
| 提交虚假代码/文件 | PM执行分支级回退，按红线禁令R1处理 |
| 合并冲突未解决就提交 | PM驳回合并请求，要求解决冲突后重新提交 |
| 未经审批合并分支 | PM执行`git reset`回退合并，警告Agent |

---

> **文档维护**：本规范由AI项目经理（PM）负责维护。如需修改，须经PM审批后更新版本号。
>
> **关联文档**：
> - D08《编码规范》— 代码风格标准
> - D09《开发报告模板》— 报告格式标准
> - AI项目经理提示词 V3.0 — PM工作流程
