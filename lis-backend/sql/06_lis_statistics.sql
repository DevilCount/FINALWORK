-- =============================================
-- LIS统计服务数据库初始化脚本
-- 数据库: lis_statistics
-- 包含: 统计数据表
-- =============================================

CREATE DATABASE IF NOT EXISTS `lis_statistics` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE `lis_statistics`;

-- ---------------------------------------------
-- 统计维度配置表
-- ---------------------------------------------
DROP TABLE IF EXISTS `stat_dimension`;
CREATE TABLE `stat_dimension` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '维度ID',
    `dimension_code` VARCHAR(50) NOT NULL COMMENT '维度编码',
    `dimension_name` VARCHAR(100) NOT NULL COMMENT '维度名称',
    `dimension_type` VARCHAR(50) DEFAULT 'category' COMMENT '维度类型（category分类 time时间 region地区）',
    `dimension_field` VARCHAR(100) DEFAULT NULL COMMENT '维度字段',
    `dimension_table` VARCHAR(100) DEFAULT NULL COMMENT '维度表',
    `parent_id` BIGINT DEFAULT 0 COMMENT '父维度ID',
    `status` TINYINT DEFAULT 0 COMMENT '状态（0正常 1停用）',
    `sort` INT DEFAULT 0 COMMENT '排序',
    `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_dimension_code` (`dimension_code`)
    `del_flag` TINYINT DEFAULT 0 COMMENT '逻辑删除标记（0存在 1删除）',
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='统计维度配置表';

-- ---------------------------------------------
-- 统计指标配置表
-- ---------------------------------------------
DROP TABLE IF EXISTS `stat_indicator`;
CREATE TABLE `stat_indicator` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '指标ID',
    `indicator_code` VARCHAR(50) NOT NULL COMMENT '指标编码',
    `indicator_name` VARCHAR(100) NOT NULL COMMENT '指标名称',
    `indicator_type` VARCHAR(50) DEFAULT 'count' COMMENT '指标类型（count计数 sum求和 avg平均 max最大 min最小）',
    `indicator_field` VARCHAR(100) DEFAULT NULL COMMENT '指标字段',
    `indicator_table` VARCHAR(100) DEFAULT NULL COMMENT '指标表',
    `unit` VARCHAR(50) DEFAULT NULL COMMENT '单位',
    `decimal_places` INT DEFAULT 0 COMMENT '小数位数',
    `calculation_expr` VARCHAR(500) DEFAULT NULL COMMENT '计算表达式',
    `status` TINYINT DEFAULT 0 COMMENT '状态（0正常 1停用）',
    `sort` INT DEFAULT 0 COMMENT '排序',
    `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_indicator_code` (`indicator_code`)
    `del_flag` TINYINT DEFAULT 0 COMMENT '逻辑删除标记（0存在 1删除）',
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='统计指标配置表';

-- ---------------------------------------------
-- 统计报表配置表
-- ---------------------------------------------
DROP TABLE IF EXISTS `stat_report_config`;
CREATE TABLE `stat_report_config` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '报表ID',
    `report_code` VARCHAR(50) NOT NULL COMMENT '报表编码',
    `report_name` VARCHAR(100) NOT NULL COMMENT '报表名称',
    `report_type` VARCHAR(50) DEFAULT 'table' COMMENT '报表类型（table表格 chart图表 mixed混合）',
    `chart_type` VARCHAR(50) DEFAULT NULL COMMENT '图表类型（line折线 bar柱状 pie饼图 radar雷达）',
    `stat_type` VARCHAR(50) DEFAULT 'daily' COMMENT '统计类型（daily日统计 weekly周统计 monthly月统计 yearly年统计）',
    `data_source` VARCHAR(100) DEFAULT NULL COMMENT '数据源',
    `query_sql` TEXT COMMENT '查询SQL',
    `dimensions` TEXT COMMENT '维度配置（JSON格式）',
    `indicators` TEXT COMMENT '指标配置（JSON格式）',
    `filters` TEXT COMMENT '过滤条件（JSON格式）',
    `sort_config` TEXT COMMENT '排序配置（JSON格式）',
    `is_cache` TINYINT DEFAULT 1 COMMENT '是否缓存（0否 1是）',
    `cache_expire` INT DEFAULT 3600 COMMENT '缓存过期时间（秒）',
    `is_scheduled` TINYINT DEFAULT 0 COMMENT '是否定时统计（0否 1是）',
    `cron_expr` VARCHAR(100) DEFAULT NULL COMMENT '定时表达式',
    `status` TINYINT DEFAULT 0 COMMENT '状态（0正常 1停用）',
    `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_report_code` (`report_code`)
    `del_flag` TINYINT DEFAULT 0 COMMENT '逻辑删除标记（0存在 1删除）',
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='统计报表配置表';

-- ---------------------------------------------
-- 标本统计日表
-- ---------------------------------------------
DROP TABLE IF EXISTS `stat_specimen_daily`;
CREATE TABLE `stat_specimen_daily` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `stat_date` DATE NOT NULL COMMENT '统计日期',
    `dept_id` BIGINT DEFAULT NULL COMMENT '科室ID',
    `dept_name` VARCHAR(100) DEFAULT NULL COMMENT '科室名称',
    `specimen_type_id` BIGINT DEFAULT NULL COMMENT '标本类型ID',
    `specimen_type_name` VARCHAR(100) DEFAULT NULL COMMENT '标本类型名称',
    `total_count` INT DEFAULT 0 COMMENT '标本总数',
    `received_count` INT DEFAULT 0 COMMENT '已接收数',
    `completed_count` INT DEFAULT 0 COMMENT '已完成数',
    `rejected_count` INT DEFAULT 0 COMMENT '拒收数',
    `stat_count` INT DEFAULT 0 COMMENT '急诊数',
    `tat_avg` DECIMAL(10,2) DEFAULT NULL COMMENT '平均周转时间（分钟）',
    `tat_within_rate` DECIMAL(5,2) DEFAULT NULL COMMENT '周转时间达标率(%)',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_stat_date_dept_type` (`stat_date`, `dept_id`, `specimen_type_id`),
    KEY `idx_stat_date` (`stat_date`),
    KEY `idx_dept_id` (`dept_id`)
    `del_flag` TINYINT DEFAULT 0 COMMENT '逻辑删除标记（0存在 1删除）',
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='标本统计日表';

-- ---------------------------------------------
-- 检验项目统计日表
-- ---------------------------------------------
DROP TABLE IF EXISTS `stat_test_item_daily`;
CREATE TABLE `stat_test_item_daily` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `stat_date` DATE NOT NULL COMMENT '统计日期',
    `dept_id` BIGINT DEFAULT NULL COMMENT '科室ID',
    `dept_name` VARCHAR(100) DEFAULT NULL COMMENT '科室名称',
    `test_item_id` BIGINT DEFAULT NULL COMMENT '检验项目ID',
    `item_code` VARCHAR(50) DEFAULT NULL COMMENT '项目编码',
    `item_name` VARCHAR(200) DEFAULT NULL COMMENT '项目名称',
    `total_count` INT DEFAULT 0 COMMENT '检验总数',
    `normal_count` INT DEFAULT 0 COMMENT '正常数',
    `abnormal_count` INT DEFAULT 0 COMMENT '异常数',
    `panic_count` INT DEFAULT 0 COMMENT '危急值数',
    `positive_count` INT DEFAULT 0 COMMENT '阳性数',
    `negative_count` INT DEFAULT 0 COMMENT '阴性数',
    `abnormal_rate` DECIMAL(5,2) DEFAULT NULL COMMENT '异常率(%)',
    `positive_rate` DECIMAL(5,2) DEFAULT NULL COMMENT '阳性率(%)',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_stat_date_dept_item` (`stat_date`, `dept_id`, `test_item_id`),
    KEY `idx_stat_date` (`stat_date`),
    KEY `idx_dept_id` (`dept_id`),
    KEY `idx_test_item_id` (`test_item_id`)
    `del_flag` TINYINT DEFAULT 0 COMMENT '逻辑删除标记（0存在 1删除）',
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='检验项目统计日表';

-- ---------------------------------------------
-- 科室统计日表
-- ---------------------------------------------
DROP TABLE IF EXISTS `stat_dept_daily`;
CREATE TABLE `stat_dept_daily` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `stat_date` DATE NOT NULL COMMENT '统计日期',
    `dept_id` BIGINT NOT NULL COMMENT '科室ID',
    `dept_name` VARCHAR(100) DEFAULT NULL COMMENT '科室名称',
    `specimen_count` INT DEFAULT 0 COMMENT '标本数',
    `test_item_count` INT DEFAULT 0 COMMENT '检验项目数',
    `report_count` INT DEFAULT 0 COMMENT '报告数',
    `panic_count` INT DEFAULT 0 COMMENT '危急值数',
    `tat_avg` DECIMAL(10,2) DEFAULT NULL COMMENT '平均周转时间（分钟）',
    `tat_within_rate` DECIMAL(5,2) DEFAULT NULL COMMENT '周转时间达标率(%)',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_stat_date_dept` (`stat_date`, `dept_id`),
    KEY `idx_stat_date` (`stat_date`),
    KEY `idx_dept_id` (`dept_id`)
    `del_flag` TINYINT DEFAULT 0 COMMENT '逻辑删除标记（0存在 1删除）',
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='科室统计日表';

-- ---------------------------------------------
-- 设备统计日表
-- ---------------------------------------------
DROP TABLE IF EXISTS `stat_equipment_daily`;
CREATE TABLE `stat_equipment_daily` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `stat_date` DATE NOT NULL COMMENT '统计日期',
    `equipment_id` BIGINT NOT NULL COMMENT '设备ID',
    `equipment_code` VARCHAR(50) DEFAULT NULL COMMENT '设备编码',
    `equipment_name` VARCHAR(200) DEFAULT NULL COMMENT '设备名称',
    `test_count` INT DEFAULT 0 COMMENT '检验次数',
    `test_item_count` INT DEFAULT 0 COMMENT '检验项目数',
    `qc_count` INT DEFAULT 0 COMMENT '质控次数',
    `qc_pass_count` INT DEFAULT 0 COMMENT '质控通过次数',
    `qc_fail_count` INT DEFAULT 0 COMMENT '质控失败次数',
    `qc_pass_rate` DECIMAL(5,2) DEFAULT NULL COMMENT '质控通过率(%)',
    `fault_count` INT DEFAULT 0 COMMENT '故障次数',
    `fault_duration` INT DEFAULT 0 COMMENT '故障时长（分钟）',
    `uptime_rate` DECIMAL(5,2) DEFAULT NULL COMMENT '设备可用率(%)',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_stat_date_equipment` (`stat_date`, `equipment_id`),
    KEY `idx_stat_date` (`stat_date`),
    KEY `idx_equipment_id` (`equipment_id`)
    `del_flag` TINYINT DEFAULT 0 COMMENT '逻辑删除标记（0存在 1删除）',
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='设备统计日表';

-- ---------------------------------------------
-- 危急值统计日表
-- ---------------------------------------------
DROP TABLE IF EXISTS `stat_panic_daily`;
CREATE TABLE `stat_panic_daily` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `stat_date` DATE NOT NULL COMMENT '统计日期',
    `dept_id` BIGINT DEFAULT NULL COMMENT '科室ID',
    `dept_name` VARCHAR(100) DEFAULT NULL COMMENT '科室名称',
    `test_item_id` BIGINT DEFAULT NULL COMMENT '检验项目ID',
    `item_code` VARCHAR(50) DEFAULT NULL COMMENT '项目编码',
    `item_name` VARCHAR(200) DEFAULT NULL COMMENT '项目名称',
    `panic_count` INT DEFAULT 0 COMMENT '危急值数',
    `notify_count` INT DEFAULT 0 COMMENT '通知数',
    `confirm_count` INT DEFAULT 0 COMMENT '确认数',
    `handle_count` INT DEFAULT 0 COMMENT '处理数',
    `notify_avg_time` DECIMAL(10,2) DEFAULT NULL COMMENT '平均通知时间（分钟）',
    `handle_avg_time` DECIMAL(10,2) DEFAULT NULL COMMENT '平均处理时间（分钟）',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_stat_date_dept_item` (`stat_date`, `dept_id`, `test_item_id`),
    KEY `idx_stat_date` (`stat_date`),
    KEY `idx_dept_id` (`dept_id`)
    `del_flag` TINYINT DEFAULT 0 COMMENT '逻辑删除标记（0存在 1删除）',
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='危急值统计日表';

-- ---------------------------------------------
-- 工作量统计日表
-- ---------------------------------------------
DROP TABLE IF EXISTS `stat_workload_daily`;
CREATE TABLE `stat_workload_daily` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `stat_date` DATE NOT NULL COMMENT '统计日期',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `user_name` VARCHAR(50) DEFAULT NULL COMMENT '用户姓名',
    `dept_id` BIGINT DEFAULT NULL COMMENT '科室ID',
    `dept_name` VARCHAR(100) DEFAULT NULL COMMENT '科室名称',
    `specimen_receive_count` INT DEFAULT 0 COMMENT '标本接收数',
    `test_count` INT DEFAULT 0 COMMENT '检验数',
    `audit_count` INT DEFAULT 0 COMMENT '审核数',
    `report_count` INT DEFAULT 0 COMMENT '报告数',
    `work_hours` DECIMAL(5,2) DEFAULT NULL COMMENT '工作时长（小时）',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_stat_date_user` (`stat_date`, `user_id`),
    KEY `idx_stat_date` (`stat_date`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_dept_id` (`dept_id`)
    `del_flag` TINYINT DEFAULT 0 COMMENT '逻辑删除标记（0存在 1删除）',
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='工作量统计日表';

-- ---------------------------------------------
-- 质控统计日表
-- ---------------------------------------------
DROP TABLE IF EXISTS `stat_qc_daily`;
CREATE TABLE `stat_qc_daily` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `stat_date` DATE NOT NULL COMMENT '统计日期',
    `equipment_id` BIGINT DEFAULT NULL COMMENT '设备ID',
    `equipment_name` VARCHAR(200) DEFAULT NULL COMMENT '设备名称',
    `test_item_id` BIGINT DEFAULT NULL COMMENT '检验项目ID',
    `item_code` VARCHAR(50) DEFAULT NULL COMMENT '项目编码',
    `item_name` VARCHAR(200) DEFAULT NULL COMMENT '项目名称',
    `qc_level` VARCHAR(20) DEFAULT NULL COMMENT '质控水平',
    `qc_count` INT DEFAULT 0 COMMENT '质控次数',
    `pass_count` INT DEFAULT 0 COMMENT '通过次数',
    `fail_count` INT DEFAULT 0 COMMENT '失败次数',
    `warning_count` INT DEFAULT 0 COMMENT '警告次数',
    `pass_rate` DECIMAL(5,2) DEFAULT NULL COMMENT '通过率(%)',
    `cv_value` DECIMAL(10,4) DEFAULT NULL COMMENT 'CV值(%)',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_stat_date_equip_item_level` (`stat_date`, `equipment_id`, `test_item_id`, `qc_level`),
    KEY `idx_stat_date` (`stat_date`),
    KEY `idx_equipment_id` (`equipment_id`)
    `del_flag` TINYINT DEFAULT 0 COMMENT '逻辑删除标记（0存在 1删除）',
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='质控统计日表';

-- ---------------------------------------------
-- 费用统计日表
-- ---------------------------------------------
DROP TABLE IF EXISTS `stat_revenue_daily`;
CREATE TABLE `stat_revenue_daily` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `stat_date` DATE NOT NULL COMMENT '统计日期',
    `dept_id` BIGINT DEFAULT NULL COMMENT '科室ID',
    `dept_name` VARCHAR(100) DEFAULT NULL COMMENT '科室名称',
    `total_amount` DECIMAL(18,2) DEFAULT 0.00 COMMENT '总金额',
    `specimen_amount` DECIMAL(18,2) DEFAULT 0.00 COMMENT '标本费',
    `test_amount` DECIMAL(18,2) DEFAULT 0.00 COMMENT '检验费',
    `stat_amount` DECIMAL(18,2) DEFAULT 0.00 COMMENT '急诊费',
    `specimen_count` INT DEFAULT 0 COMMENT '标本数',
    `test_item_count` INT DEFAULT 0 COMMENT '检验项目数',
    `avg_amount` DECIMAL(10,2) DEFAULT NULL COMMENT '人均费用',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_stat_date_dept` (`stat_date`, `dept_id`),
    KEY `idx_stat_date` (`stat_date`),
    KEY `idx_dept_id` (`dept_id`)
    `del_flag` TINYINT DEFAULT 0 COMMENT '逻辑删除标记（0存在 1删除）',
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='费用统计日表';

-- ---------------------------------------------
-- 统计报表结果表
-- ---------------------------------------------
DROP TABLE IF EXISTS `stat_report_result`;
CREATE TABLE `stat_report_result` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `report_config_id` BIGINT NOT NULL COMMENT '报表配置ID',
    `report_code` VARCHAR(50) DEFAULT NULL COMMENT '报表编码',
    `stat_period` VARCHAR(20) DEFAULT NULL COMMENT '统计周期',
    `stat_start_time` DATETIME DEFAULT NULL COMMENT '统计开始时间',
    `stat_end_time` DATETIME DEFAULT NULL COMMENT '统计结束时间',
    `result_data` LONGTEXT COMMENT '结果数据（JSON格式）',
    `result_count` INT DEFAULT 0 COMMENT '结果记录数',
    `execute_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '执行时间',
    `execute_duration` BIGINT DEFAULT NULL COMMENT '执行耗时（毫秒）',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_report_config_id` (`report_config_id`),
    KEY `idx_stat_period` (`stat_period`),
    KEY `idx_execute_time` (`execute_time`)
    `del_flag` TINYINT DEFAULT 0 COMMENT '逻辑删除标记（0存在 1删除）',
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='统计报表结果表';

-- ---------------------------------------------
-- 统计任务执行日志表
-- ---------------------------------------------
DROP TABLE IF EXISTS `stat_task_log`;
CREATE TABLE `stat_task_log` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '日志ID',
    `task_name` VARCHAR(200) NOT NULL COMMENT '任务名称',
    `task_type` VARCHAR(50) DEFAULT NULL COMMENT '任务类型',
    `stat_date` DATE DEFAULT NULL COMMENT '统计日期',
    `start_time` DATETIME DEFAULT NULL COMMENT '开始时间',
    `end_time` DATETIME DEFAULT NULL COMMENT '结束时间',
    `duration` BIGINT DEFAULT NULL COMMENT '耗时（毫秒）',
    `status` VARCHAR(20) DEFAULT 'running' COMMENT '执行状态（running执行中 success成功 failed失败）',
    `record_count` INT DEFAULT 0 COMMENT '处理记录数',
    `error_msg` VARCHAR(2000) DEFAULT NULL COMMENT '错误信息',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_task_name` (`task_name`),
    KEY `idx_stat_date` (`stat_date`),
    KEY `idx_start_time` (`start_time`)
    `del_flag` TINYINT DEFAULT 0 COMMENT '逻辑删除标记（0存在 1删除）',
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='统计任务执行日志表';
