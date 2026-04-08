-- =============================================
-- LIS设备服务数据库初始化脚本
-- 数据库: lis_equipment
-- 包含: 设备、校准、维护表
-- =============================================

CREATE DATABASE IF NOT EXISTS `lis_equipment` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE `lis_equipment`;

-- ---------------------------------------------
-- 设备类型表
-- ---------------------------------------------
DROP TABLE IF EXISTS `equipment_type`;
CREATE TABLE `equipment_type` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '设备类型ID',
    `type_code` VARCHAR(50) NOT NULL COMMENT '类型编码',
    `type_name` VARCHAR(100) NOT NULL COMMENT '类型名称',
    `type_desc` VARCHAR(500) DEFAULT NULL COMMENT '类型描述',
    `parent_id` BIGINT DEFAULT 0 COMMENT '父类型ID',
    `status` TINYINT DEFAULT 0 COMMENT '状态（0正常 1停用）',
    `sort` INT DEFAULT 0 COMMENT '排序',
    `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_type_code` (`type_code`),
    KEY `idx_parent_id` (`parent_id`)
    `del_flag` TINYINT DEFAULT 0 COMMENT '逻辑删除标记（0存在 1删除）',
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='设备类型表';

-- ---------------------------------------------
-- 设备主表
-- ---------------------------------------------
DROP TABLE IF EXISTS `equipment`;
CREATE TABLE `equipment` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '设备ID',
    `equipment_code` VARCHAR(50) NOT NULL COMMENT '设备编码',
    `equipment_name` VARCHAR(200) NOT NULL COMMENT '设备名称',
    `equipment_name_en` VARCHAR(200) DEFAULT NULL COMMENT '设备英文名',
    `equipment_type_id` BIGINT DEFAULT NULL COMMENT '设备类型ID',
    `equipment_type_name` VARCHAR(100) DEFAULT NULL COMMENT '设备类型名称',
    `brand` VARCHAR(100) DEFAULT NULL COMMENT '品牌',
    `model` VARCHAR(100) DEFAULT NULL COMMENT '型号',
    `serial_no` VARCHAR(100) DEFAULT NULL COMMENT '序列号',
    `manufacturer` VARCHAR(200) DEFAULT NULL COMMENT '生产厂家',
    `supplier` VARCHAR(200) DEFAULT NULL COMMENT '供应商',
    `purchase_date` DATE DEFAULT NULL COMMENT '购置日期',
    `install_date` DATE DEFAULT NULL COMMENT '安装日期',
    `warranty_expire_date` DATE DEFAULT NULL COMMENT '保修到期日期',
    `use_life` INT DEFAULT NULL COMMENT '使用年限（年）',
    `original_value` DECIMAL(18,2) DEFAULT NULL COMMENT '原值',
    `net_value` DECIMAL(18,2) DEFAULT NULL COMMENT '净值',
    `location` VARCHAR(200) DEFAULT NULL COMMENT '存放位置',
    `lab_id` BIGINT DEFAULT NULL COMMENT '所属实验室ID',
    `lab_name` VARCHAR(100) DEFAULT NULL COMMENT '所属实验室名称',
    `responsible_user_id` BIGINT DEFAULT NULL COMMENT '负责人ID',
    `responsible_user_name` VARCHAR(50) DEFAULT NULL COMMENT '负责人姓名',
    `contact_phone` VARCHAR(20) DEFAULT NULL COMMENT '联系电话',
    `status` VARCHAR(20) DEFAULT 'normal' COMMENT '设备状态（normal正常 maintenance维护中 repair维修中 scrap报废 calibration校准中）',
    `is_online` TINYINT DEFAULT 0 COMMENT '是否在线（0离线 1在线）',
    `ip_address` VARCHAR(50) DEFAULT NULL COMMENT 'IP地址',
    `port` INT DEFAULT NULL COMMENT '端口号',
    `communication_protocol` VARCHAR(50) DEFAULT NULL COMMENT '通信协议',
    `baud_rate` INT DEFAULT NULL COMMENT '波特率',
    `last_comm_time` DATETIME DEFAULT NULL COMMENT '最后通信时间',
    `last_calibration_date` DATE DEFAULT NULL COMMENT '最后校准日期',
    `next_calibration_date` DATE DEFAULT NULL COMMENT '下次校准日期',
    `last_maintenance_date` DATE DEFAULT NULL COMMENT '最后维护日期',
    `next_maintenance_date` DATE DEFAULT NULL COMMENT '下次维护日期',
    `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_equipment_code` (`equipment_code`),
    KEY `idx_equipment_type_id` (`equipment_type_id`),
    KEY `idx_status` (`status`),
    KEY `idx_lab_id` (`lab_id`)
    `del_flag` TINYINT DEFAULT 0 COMMENT '逻辑删除标记（0存在 1删除）',
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='设备主表';

-- ---------------------------------------------
-- 设备检验项目关联表
-- ---------------------------------------------
DROP TABLE IF EXISTS `equipment_test_item`;
CREATE TABLE `equipment_test_item` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `equipment_id` BIGINT NOT NULL COMMENT '设备ID',
    `equipment_code` VARCHAR(50) NOT NULL COMMENT '设备编码',
    `test_item_id` BIGINT NOT NULL COMMENT '检验项目ID',
    `item_code` VARCHAR(50) NOT NULL COMMENT '项目编码',
    `item_name` VARCHAR(200) DEFAULT NULL COMMENT '项目名称',
    `channel_no` VARCHAR(50) DEFAULT NULL COMMENT '通道号',
    `is_enabled` TINYINT DEFAULT 1 COMMENT '是否启用（0否 1是）',
    `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_equipment_item` (`equipment_id`, `test_item_id`),
    KEY `idx_equipment_id` (`equipment_id`),
    KEY `idx_test_item_id` (`test_item_id`)
    `del_flag` TINYINT DEFAULT 0 COMMENT '逻辑删除标记（0存在 1删除）',
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='设备检验项目关联表';

-- ---------------------------------------------
-- 设备校准记录表
-- ---------------------------------------------
DROP TABLE IF EXISTS `equipment_calibration`;
CREATE TABLE `equipment_calibration` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '校准ID',
    `calibration_no` VARCHAR(50) NOT NULL COMMENT '校准编号',
    `equipment_id` BIGINT NOT NULL COMMENT '设备ID',
    `equipment_code` VARCHAR(50) DEFAULT NULL COMMENT '设备编码',
    `equipment_name` VARCHAR(200) DEFAULT NULL COMMENT '设备名称',
    `calibration_type` VARCHAR(50) DEFAULT 'routine' COMMENT '校准类型（routine常规 calibration校准 verification验证）',
    `calibration_date` DATE NOT NULL COMMENT '校准日期',
    `calibration_org` VARCHAR(200) DEFAULT NULL COMMENT '校准机构',
    `calibration_user_id` BIGINT DEFAULT NULL COMMENT '校准人ID',
    `calibration_user_name` VARCHAR(50) DEFAULT NULL COMMENT '校准人姓名',
    `calibration_method` VARCHAR(200) DEFAULT NULL COMMENT '校准方法',
    `calibration_result` VARCHAR(20) DEFAULT 'qualified' COMMENT '校准结果（qualified合格 unqualified不合格）',
    `calibration_certificate` VARCHAR(200) DEFAULT NULL COMMENT '校准证书号',
    `valid_start_date` DATE DEFAULT NULL COMMENT '有效期开始日期',
    `valid_end_date` DATE DEFAULT NULL COMMENT '有效期结束日期',
    `calibration_report` VARCHAR(500) DEFAULT NULL COMMENT '校准报告路径',
    `calibration_cost` DECIMAL(18,2) DEFAULT NULL COMMENT '校准费用',
    `status` TINYINT DEFAULT 0 COMMENT '状态（0待校准 1校准中 2已完成）',
    `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_calibration_no` (`calibration_no`),
    KEY `idx_equipment_id` (`equipment_id`),
    KEY `idx_calibration_date` (`calibration_date`),
    KEY `idx_valid_end_date` (`valid_end_date`)
    `del_flag` TINYINT DEFAULT 0 COMMENT '逻辑删除标记（0存在 1删除）',
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='设备校准记录表';

-- ---------------------------------------------
-- 设备校准明细表
-- ---------------------------------------------
DROP TABLE IF EXISTS `equipment_calibration_item`;
CREATE TABLE `equipment_calibration_item` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `calibration_id` BIGINT NOT NULL COMMENT '校准ID',
    `test_item_id` BIGINT DEFAULT NULL COMMENT '检验项目ID',
    `item_code` VARCHAR(50) DEFAULT NULL COMMENT '项目编码',
    `item_name` VARCHAR(200) DEFAULT NULL COMMENT '项目名称',
    `standard_value` DECIMAL(18,4) DEFAULT NULL COMMENT '标准值',
    `measured_value` DECIMAL(18,4) DEFAULT NULL COMMENT '测量值',
    `error_value` DECIMAL(18,4) DEFAULT NULL COMMENT '误差值',
    `error_limit` DECIMAL(18,4) DEFAULT NULL COMMENT '误差限',
    `unit` VARCHAR(50) DEFAULT NULL COMMENT '单位',
    `is_qualified` TINYINT DEFAULT 1 COMMENT '是否合格（0否 1是）',
    `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    KEY `idx_calibration_id` (`calibration_id`)
    `del_flag` TINYINT DEFAULT 0 COMMENT '逻辑删除标记（0存在 1删除）',
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='设备校准明细表';

-- ---------------------------------------------
-- 设备维护计划表
-- ---------------------------------------------
DROP TABLE IF EXISTS `equipment_maintenance_plan`;
CREATE TABLE `equipment_maintenance_plan` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '计划ID',
    `plan_no` VARCHAR(50) NOT NULL COMMENT '计划编号',
    `plan_name` VARCHAR(200) NOT NULL COMMENT '计划名称',
    `equipment_id` BIGINT NOT NULL COMMENT '设备ID',
    `equipment_code` VARCHAR(50) DEFAULT NULL COMMENT '设备编码',
    `equipment_name` VARCHAR(200) DEFAULT NULL COMMENT '设备名称',
    `maintenance_type` VARCHAR(50) DEFAULT 'routine' COMMENT '维护类型（routine日常维护 periodic周期维护 preventive预防性维护）',
    `maintenance_cycle` INT DEFAULT NULL COMMENT '维护周期（天）',
    `maintenance_content` TEXT COMMENT '维护内容',
    `plan_date` DATE DEFAULT NULL COMMENT '计划日期',
    `next_date` DATE DEFAULT NULL COMMENT '下次计划日期',
    `responsible_user_id` BIGINT DEFAULT NULL COMMENT '负责人ID',
    `responsible_user_name` VARCHAR(50) DEFAULT NULL COMMENT '负责人姓名',
    `status` TINYINT DEFAULT 0 COMMENT '状态（0待执行 1执行中 2已完成 3已取消）',
    `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_plan_no` (`plan_no`),
    KEY `idx_equipment_id` (`equipment_id`),
    KEY `idx_plan_date` (`plan_date`)
    `del_flag` TINYINT DEFAULT 0 COMMENT '逻辑删除标记（0存在 1删除）',
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='设备维护计划表';

-- ---------------------------------------------
-- 设备维护记录表
-- ---------------------------------------------
DROP TABLE IF EXISTS `equipment_maintenance`;
CREATE TABLE `equipment_maintenance` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '维护ID',
    `maintenance_no` VARCHAR(50) NOT NULL COMMENT '维护编号',
    `plan_id` BIGINT DEFAULT NULL COMMENT '计划ID',
    `plan_no` VARCHAR(50) DEFAULT NULL COMMENT '计划编号',
    `equipment_id` BIGINT NOT NULL COMMENT '设备ID',
    `equipment_code` VARCHAR(50) DEFAULT NULL COMMENT '设备编码',
    `equipment_name` VARCHAR(200) DEFAULT NULL COMMENT '设备名称',
    `maintenance_type` VARCHAR(50) DEFAULT 'routine' COMMENT '维护类型（routine日常维护 periodic周期维护 preventive预防性维护 repair维修）',
    `maintenance_date` DATE NOT NULL COMMENT '维护日期',
    `start_time` DATETIME DEFAULT NULL COMMENT '开始时间',
    `end_time` DATETIME DEFAULT NULL COMMENT '结束时间',
    `maintenance_content` TEXT COMMENT '维护内容',
    `maintenance_result` VARCHAR(500) DEFAULT NULL COMMENT '维护结果',
    `maintenance_status` VARCHAR(20) DEFAULT 'normal' COMMENT '维护后状态（normal正常 abnormal异常）',
    `maintenance_user_id` BIGINT DEFAULT NULL COMMENT '维护人ID',
    `maintenance_user_name` VARCHAR(50) DEFAULT NULL COMMENT '维护人姓名',
    `cost` DECIMAL(18,2) DEFAULT NULL COMMENT '维护费用',
    `status` TINYINT DEFAULT 0 COMMENT '状态（0待维护 1维护中 2已完成）',
    `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_maintenance_no` (`maintenance_no`),
    KEY `idx_plan_id` (`plan_id`),
    KEY `idx_equipment_id` (`equipment_id`),
    KEY `idx_maintenance_date` (`maintenance_date`)
    `del_flag` TINYINT DEFAULT 0 COMMENT '逻辑删除标记（0存在 1删除）',
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='设备维护记录表';

-- ---------------------------------------------
-- 设备故障记录表
-- ---------------------------------------------
DROP TABLE IF EXISTS `equipment_fault`;
CREATE TABLE `equipment_fault` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '故障ID',
    `fault_no` VARCHAR(50) NOT NULL COMMENT '故障编号',
    `equipment_id` BIGINT NOT NULL COMMENT '设备ID',
    `equipment_code` VARCHAR(50) DEFAULT NULL COMMENT '设备编码',
    `equipment_name` VARCHAR(200) DEFAULT NULL COMMENT '设备名称',
    `fault_type` VARCHAR(50) DEFAULT NULL COMMENT '故障类型',
    `fault_level` VARCHAR(20) DEFAULT 'minor' COMMENT '故障级别（minor轻微 moderate中等 major严重 critical危急）',
    `fault_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '故障时间',
    `fault_desc` TEXT COMMENT '故障描述',
    `fault_reason` VARCHAR(500) DEFAULT NULL COMMENT '故障原因',
    `find_user_id` BIGINT DEFAULT NULL COMMENT '发现人ID',
    `find_user_name` VARCHAR(50) DEFAULT NULL COMMENT '发现人姓名',
    `report_time` DATETIME DEFAULT NULL COMMENT '报修时间',
    `repair_user_id` BIGINT DEFAULT NULL COMMENT '维修人ID',
    `repair_user_name` VARCHAR(50) DEFAULT NULL COMMENT '维修人姓名',
    `repair_start_time` DATETIME DEFAULT NULL COMMENT '维修开始时间',
    `repair_end_time` DATETIME DEFAULT NULL COMMENT '维修结束时间',
    `repair_content` TEXT COMMENT '维修内容',
    `repair_cost` DECIMAL(18,2) DEFAULT NULL COMMENT '维修费用',
    `repair_result` VARCHAR(500) DEFAULT NULL COMMENT '维修结果',
    `status` TINYINT DEFAULT 0 COMMENT '状态（0待处理 1处理中 2已修复 3无法修复）',
    `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_fault_no` (`fault_no`),
    KEY `idx_equipment_id` (`equipment_id`),
    KEY `idx_fault_time` (`fault_time`),
    KEY `idx_status` (`status`)
    `del_flag` TINYINT DEFAULT 0 COMMENT '逻辑删除标记（0存在 1删除）',
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='设备故障记录表';

-- ---------------------------------------------
-- 设备质控表
-- ---------------------------------------------
DROP TABLE IF EXISTS `equipment_qc`;
CREATE TABLE `equipment_qc` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '质控ID',
    `qc_no` VARCHAR(50) NOT NULL COMMENT '质控编号',
    `equipment_id` BIGINT NOT NULL COMMENT '设备ID',
    `equipment_code` VARCHAR(50) DEFAULT NULL COMMENT '设备编码',
    `equipment_name` VARCHAR(200) DEFAULT NULL COMMENT '设备名称',
    `test_item_id` BIGINT DEFAULT NULL COMMENT '检验项目ID',
    `item_code` VARCHAR(50) DEFAULT NULL COMMENT '项目编码',
    `item_name` VARCHAR(200) DEFAULT NULL COMMENT '项目名称',
    `qc_level` VARCHAR(20) DEFAULT 'level1' COMMENT '质控水平（level1水平1 level2水平2 level3水平3）',
    `qc_lot_no` VARCHAR(50) DEFAULT NULL COMMENT '质控品批号',
    `qc_date` DATE NOT NULL COMMENT '质控日期',
    `qc_time` DATETIME DEFAULT NULL COMMENT '质控时间',
    `target_value` DECIMAL(18,4) DEFAULT NULL COMMENT '靶值',
    `sd_value` DECIMAL(18,4) DEFAULT NULL COMMENT '标准差',
    `measured_value` DECIMAL(18,4) DEFAULT NULL COMMENT '测量值',
    `cv_value` DECIMAL(10,4) DEFAULT NULL COMMENT 'CV值(%)',
    `qc_result` VARCHAR(20) DEFAULT 'pass' COMMENT '质控结果（pass通过 fail未通过 warning警告）',
    `is_in_control` TINYINT DEFAULT 1 COMMENT '是否在控（0失控 1在控）',
    `violation_rule` VARCHAR(100) DEFAULT NULL COMMENT '违反规则',
    `qc_user_id` BIGINT DEFAULT NULL COMMENT '质控人ID',
    `qc_user_name` VARCHAR(50) DEFAULT NULL COMMENT '质控人姓名',
    `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_qc_no` (`qc_no`),
    KEY `idx_equipment_id` (`equipment_id`),
    KEY `idx_test_item_id` (`test_item_id`),
    KEY `idx_qc_date` (`qc_date`)
    `del_flag` TINYINT DEFAULT 0 COMMENT '逻辑删除标记（0存在 1删除）',
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='设备质控表';

-- ---------------------------------------------
-- 设备试剂表
-- ---------------------------------------------
DROP TABLE IF EXISTS `equipment_reagent`;
CREATE TABLE `equipment_reagent` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '试剂ID',
    `reagent_code` VARCHAR(50) NOT NULL COMMENT '试剂编码',
    `reagent_name` VARCHAR(200) NOT NULL COMMENT '试剂名称',
    `reagent_spec` VARCHAR(100) DEFAULT NULL COMMENT '试剂规格',
    `manufacturer` VARCHAR(200) DEFAULT NULL COMMENT '生产厂家',
    `lot_no` VARCHAR(50) DEFAULT NULL COMMENT '批号',
    `valid_date` DATE DEFAULT NULL COMMENT '有效期',
    `equipment_id` BIGINT DEFAULT NULL COMMENT '关联设备ID',
    `equipment_name` VARCHAR(200) DEFAULT NULL COMMENT '关联设备名称',
    `test_item_id` BIGINT DEFAULT NULL COMMENT '关联检验项目ID',
    `item_name` VARCHAR(200) DEFAULT NULL COMMENT '关联检验项目名称',
    `stock_quantity` DECIMAL(18,4) DEFAULT 0 COMMENT '库存数量',
    `unit` VARCHAR(50) DEFAULT NULL COMMENT '单位',
    `warning_quantity` DECIMAL(18,4) DEFAULT NULL COMMENT '预警数量',
    `status` TINYINT DEFAULT 0 COMMENT '状态（0正常 1停用）',
    `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_reagent_code` (`reagent_code`),
    KEY `idx_equipment_id` (`equipment_id`),
    KEY `idx_valid_date` (`valid_date`)
    `del_flag` TINYINT DEFAULT 0 COMMENT '逻辑删除标记（0存在 1删除）',
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='设备试剂表';
