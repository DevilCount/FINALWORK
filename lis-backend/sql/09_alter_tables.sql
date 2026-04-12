-- =============================================
-- LIS 改造增量SQL脚本
-- 包含: ALTER TABLE report, CREATE TABLE sys_error_log/sys_audit_log, INSERT 字典数据
-- 执行顺序: 在基础建表脚本之后执行
-- =============================================

-- ---------------------------------------------
-- 1. report 表新增审核字段（lis_report 数据库）
-- ---------------------------------------------
USE `lis_report`;

ALTER TABLE report
  ADD COLUMN `first_audit_user_id` BIGINT DEFAULT NULL COMMENT '初审人ID',
  ADD COLUMN `first_audit_user_name` VARCHAR(50) DEFAULT NULL COMMENT '初审人姓名',
  ADD COLUMN `first_audit_time` DATETIME DEFAULT NULL COMMENT '初审时间',
  ADD COLUMN `first_audit_opinion` VARCHAR(500) DEFAULT NULL COMMENT '初审意见',
  ADD COLUMN `final_audit_user_id` BIGINT DEFAULT NULL COMMENT '终审人ID',
  ADD COLUMN `final_audit_user_name` VARCHAR(50) DEFAULT NULL COMMENT '终审人姓名',
  ADD COLUMN `final_audit_time` DATETIME DEFAULT NULL COMMENT '终审时间',
  ADD COLUMN `final_audit_opinion` VARCHAR(500) DEFAULT NULL COMMENT '终审意见';

-- ---------------------------------------------
-- 2. 新建 sys_error_log 表（lis_user 数据库）
-- ---------------------------------------------
USE `lis_user`;

CREATE TABLE IF NOT EXISTS `sys_error_log` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `error_time` DATETIME NOT NULL COMMENT '错误发生时间',
    `error_level` VARCHAR(10) DEFAULT 'ERROR' COMMENT '错误级别(ERROR/WARN/FATAL)',
    `error_class` VARCHAR(255) DEFAULT '' COMMENT '错误类名',
    `error_method` VARCHAR(200) DEFAULT '' COMMENT '错误方法名',
    `error_line` INT DEFAULT NULL COMMENT '错误行号',
    `error_message` VARCHAR(2000) DEFAULT '' COMMENT '错误消息',
    `error_stack` TEXT COMMENT '错误堆栈',
    `request_url` VARCHAR(500) DEFAULT '' COMMENT '请求URL',
    `request_method` VARCHAR(10) DEFAULT '' COMMENT '请求方式',
    `request_params` TEXT COMMENT '请求参数',
    `user_id` BIGINT DEFAULT NULL COMMENT '当前登录用户ID',
    `user_name` VARCHAR(50) DEFAULT '' COMMENT '当前登录用户工号',
    `user_ip` VARCHAR(128) DEFAULT '' COMMENT '用户IP',
    `server_node` VARCHAR(100) DEFAULT '' COMMENT '服务节点',
    `thread_name` VARCHAR(200) DEFAULT '' COMMENT '线程名',
    `del_flag` TINYINT DEFAULT 0 COMMENT '逻辑删除标记',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_error_time` (`error_time`),
    KEY `idx_error_level` (`error_level`),
    KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统错误日志';

-- ---------------------------------------------
-- 3. 新建 sys_audit_log 表（lis_user 数据库）
-- ---------------------------------------------
CREATE TABLE IF NOT EXISTS `sys_audit_log` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `audit_time` DATETIME NOT NULL COMMENT '审计时间',
    `audit_type` VARCHAR(50) NOT NULL COMMENT '审计类型(CONFIG_CHANGE/PERMISSION_CHANGE/ROLE_CHANGE/USER_CHANGE/DATA_EXPORT)',
    `target_type` VARCHAR(50) DEFAULT '' COMMENT '操作对象类型(USER/ROLE/MENU/DEPT/DICT/EQUIPMENT/INTERFACE)',
    `target_id` BIGINT DEFAULT NULL COMMENT '操作对象ID',
    `target_name` VARCHAR(200) DEFAULT '' COMMENT '操作对象名称',
    `action` VARCHAR(50) NOT NULL COMMENT '操作动作(CREATE/UPDATE/DELETE/ASSIGN/EXPORT/ENABLE/DISABLE)',
    `old_value` TEXT COMMENT '变更前值(JSON)',
    `new_value` TEXT COMMENT '变更后值(JSON)',
    `diff_summary` VARCHAR(500) DEFAULT '' COMMENT '变更摘要',
    `operator_id` BIGINT DEFAULT NULL COMMENT '操作人ID',
    `operator_name` VARCHAR(50) DEFAULT '' COMMENT '操作人姓名',
    `operator_ip` VARCHAR(128) DEFAULT '' COMMENT '操作人IP',
    `result` VARCHAR(20) DEFAULT 'SUCCESS' COMMENT '操作结果(SUCCESS/FAILED)',
    `remark` VARCHAR(500) DEFAULT '' COMMENT '备注',
    `del_flag` TINYINT DEFAULT 0 COMMENT '逻辑删除标记',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_audit_time` (`audit_time`),
    KEY `idx_audit_type` (`audit_type`),
    KEY `idx_operator_id` (`operator_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='审计日志';

-- ---------------------------------------------
-- 4. 字典数据更新（lis_user 数据库）
-- ---------------------------------------------
INSERT INTO sys_dict_data (dict_type, dict_label, dict_value, dict_sort, status) VALUES
('report_status', '已初审', 'first_audited', 35, 0),
('report_status', '已终审', 'final_audited', 45, 0);

INSERT INTO sys_dict_data (dict_type, dict_label, dict_value, dict_sort, status) VALUES
('specimen_status', '已入库', 'stored', 25, 0);

INSERT INTO sys_dict_type (dict_name, dict_type, status) VALUES
('日志类型', 'log_type', 0),
('审计类型', 'audit_type', 0),
('错误级别', 'error_level', 0);

INSERT INTO sys_dict_data (dict_type, dict_label, dict_value, dict_sort, status) VALUES
('log_type', '接口日志', 'interface', 1, 0),
('log_type', '系统日志', 'system', 2, 0),
('log_type', '操作日志', 'oper', 3, 0),
('log_type', '审计日志', 'audit', 4, 0),
('log_type', '登录日志', 'login', 5, 0),
('audit_type', '配置变更', 'CONFIG_CHANGE', 1, 0),
('audit_type', '权限变更', 'PERMISSION_CHANGE', 2, 0),
('audit_type', '角色变更', 'ROLE_CHANGE', 3, 0),
('audit_type', '用户变更', 'USER_CHANGE', 4, 0),
('audit_type', '数据导出', 'DATA_EXPORT', 5, 0),
('error_level', '致命', 'FATAL', 1, 0),
('error_level', '错误', 'ERROR', 2, 0),
('error_level', '警告', 'WARN', 3, 0);
