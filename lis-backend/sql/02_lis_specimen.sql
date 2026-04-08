-- =============================================
-- LIS标本服务数据库初始化脚本
-- 数据库: lis_specimen
-- 包含: 标本、标本检验项目表
-- =============================================

CREATE DATABASE IF NOT EXISTS `lis_specimen` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE `lis_specimen`;

-- ---------------------------------------------
-- 标本类型表
-- ---------------------------------------------
DROP TABLE IF EXISTS `specimen_type`;
CREATE TABLE `specimen_type` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '标本类型ID',
    `type_code` VARCHAR(50) NOT NULL COMMENT '类型编码',
    `type_name` VARCHAR(100) NOT NULL COMMENT '类型名称',
    `type_desc` VARCHAR(500) DEFAULT NULL COMMENT '类型描述',
    `color_code` VARCHAR(20) DEFAULT NULL COMMENT '颜色编码（用于标识容器颜色）',
    `container_type` VARCHAR(50) DEFAULT NULL COMMENT '容器类型',
    `storage_condition` VARCHAR(200) DEFAULT NULL COMMENT '存储条件',
    `validity_period` INT DEFAULT NULL COMMENT '有效期（小时）',
    `status` TINYINT DEFAULT 0 COMMENT '状态（0正常 1停用）',
    `sort` INT DEFAULT 0 COMMENT '排序',
    `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_type_code` (`type_code`)
    `del_flag` TINYINT DEFAULT 0 COMMENT '逻辑删除标记（0存在 1删除）',
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='标本类型表';

-- ---------------------------------------------
-- 检验项目表
-- ---------------------------------------------
DROP TABLE IF EXISTS `test_item`;
CREATE TABLE `test_item` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '检验项目ID',
    `item_code` VARCHAR(50) NOT NULL COMMENT '项目编码',
    `item_name` VARCHAR(200) NOT NULL COMMENT '项目名称',
    `item_name_en` VARCHAR(200) DEFAULT NULL COMMENT '项目英文名',
    `item_short` VARCHAR(50) DEFAULT NULL COMMENT '项目简称',
    `category_id` BIGINT DEFAULT NULL COMMENT '项目分类ID',
    `specimen_type_id` BIGINT DEFAULT NULL COMMENT '默认标本类型ID',
    `method` VARCHAR(200) DEFAULT NULL COMMENT '检验方法',
    `unit` VARCHAR(50) DEFAULT NULL COMMENT '计量单位',
    `reference_low` DECIMAL(18,4) DEFAULT NULL COMMENT '参考值下限',
    `reference_high` DECIMAL(18,4) DEFAULT NULL COMMENT '参考值上限',
    `reference_text` VARCHAR(500) DEFAULT NULL COMMENT '参考值文本（用于文本型结果）',
    `panic_low` DECIMAL(18,4) DEFAULT NULL COMMENT '危急值下限',
    `panic_high` DECIMAL(18,4) DEFAULT NULL COMMENT '危急值上限',
    `price` DECIMAL(10,2) DEFAULT 0.00 COMMENT '价格',
    `tat` INT DEFAULT NULL COMMENT '周转时间（分钟）',
    `is_print` TINYINT DEFAULT 1 COMMENT '是否打印（0否 1是）',
    `is_stat` TINYINT DEFAULT 0 COMMENT '是否急诊（0否 1是）',
    `status` TINYINT DEFAULT 0 COMMENT '状态（0正常 1停用）',
    `sort` INT DEFAULT 0 COMMENT '排序',
    `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_item_code` (`item_code`),
    KEY `idx_category_id` (`category_id`),
    KEY `idx_specimen_type_id` (`specimen_type_id`)
    `del_flag` TINYINT DEFAULT 0 COMMENT '逻辑删除标记（0存在 1删除）',
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='检验项目表';

-- ---------------------------------------------
-- 检验项目分类表
-- ---------------------------------------------
DROP TABLE IF EXISTS `test_item_category`;
CREATE TABLE `test_item_category` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '分类ID',
    `parent_id` BIGINT DEFAULT 0 COMMENT '父分类ID',
    `category_code` VARCHAR(50) NOT NULL COMMENT '分类编码',
    `category_name` VARCHAR(100) NOT NULL COMMENT '分类名称',
    `category_desc` VARCHAR(500) DEFAULT NULL COMMENT '分类描述',
    `status` TINYINT DEFAULT 0 COMMENT '状态（0正常 1停用）',
    `sort` INT DEFAULT 0 COMMENT '排序',
    `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_category_code` (`category_code`),
    KEY `idx_parent_id` (`parent_id`)
    `del_flag` TINYINT DEFAULT 0 COMMENT '逻辑删除标记（0存在 1删除）',
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='检验项目分类表';

-- ---------------------------------------------
-- 标本主表
-- ---------------------------------------------
DROP TABLE IF EXISTS `specimen`;
CREATE TABLE `specimen` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '标本ID',
    `specimen_no` VARCHAR(50) NOT NULL COMMENT '标本编号',
    `barcode` VARCHAR(100) DEFAULT NULL COMMENT '条码号',
    `patient_id` BIGINT DEFAULT NULL COMMENT '患者ID',
    `patient_name` VARCHAR(50) DEFAULT NULL COMMENT '患者姓名',
    `patient_gender` CHAR(1) DEFAULT NULL COMMENT '患者性别',
    `patient_age` VARCHAR(20) DEFAULT NULL COMMENT '患者年龄',
    `patient_id_no` VARCHAR(50) DEFAULT NULL COMMENT '患者身份证号',
    `patient_phone` VARCHAR(20) DEFAULT NULL COMMENT '患者电话',
    `dept_id` BIGINT DEFAULT NULL COMMENT '送检科室ID',
    `dept_name` VARCHAR(100) DEFAULT NULL COMMENT '送检科室名称',
    `ward_id` BIGINT DEFAULT NULL COMMENT '病区ID',
    `ward_name` VARCHAR(100) DEFAULT NULL COMMENT '病区名称',
    `bed_no` VARCHAR(20) DEFAULT NULL COMMENT '床号',
    `doctor_id` BIGINT DEFAULT NULL COMMENT '送检医生ID',
    `doctor_name` VARCHAR(50) DEFAULT NULL COMMENT '送检医生姓名',
    `specimen_type_id` BIGINT DEFAULT NULL COMMENT '标本类型ID',
    `specimen_type_name` VARCHAR(100) DEFAULT NULL COMMENT '标本类型名称',
    `collect_time` DATETIME DEFAULT NULL COMMENT '采集时间',
    `collect_user_id` BIGINT DEFAULT NULL COMMENT '采集人ID',
    `collect_user_name` VARCHAR(50) DEFAULT NULL COMMENT '采集人姓名',
    `receive_time` DATETIME DEFAULT NULL COMMENT '接收时间',
    `receive_user_id` BIGINT DEFAULT NULL COMMENT '接收人ID',
    `receive_user_name` VARCHAR(50) DEFAULT NULL COMMENT '接收人姓名',
    `status` VARCHAR(20) DEFAULT 'pending' COMMENT '标本状态（pending待接收 received已接收 testing检验中 completed已完成 rejected已拒收）',
    `reject_reason` VARCHAR(500) DEFAULT NULL COMMENT '拒收原因',
    `is_stat` TINYINT DEFAULT 0 COMMENT '是否急诊（0否 1是）',
    `is_print` TINYINT DEFAULT 0 COMMENT '是否打印条码（0否 1是）',
    `clinical_diagnosis` VARCHAR(500) DEFAULT NULL COMMENT '临床诊断',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_specimen_no` (`specimen_no`),
    KEY `idx_barcode` (`barcode`),
    KEY `idx_patient_id` (`patient_id`),
    KEY `idx_dept_id` (`dept_id`),
    KEY `idx_status` (`status`),
    KEY `idx_collect_time` (`collect_time`)
    `del_flag` TINYINT DEFAULT 0 COMMENT '逻辑删除标记（0存在 1删除）',
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='标本主表';

-- ---------------------------------------------
-- 标本检验项目表
-- ---------------------------------------------
DROP TABLE IF EXISTS `specimen_test_item`;
CREATE TABLE `specimen_test_item` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `specimen_id` BIGINT NOT NULL COMMENT '标本ID',
    `specimen_no` VARCHAR(50) NOT NULL COMMENT '标本编号',
    `test_item_id` BIGINT NOT NULL COMMENT '检验项目ID',
    `item_code` VARCHAR(50) NOT NULL COMMENT '项目编码',
    `item_name` VARCHAR(200) NOT NULL COMMENT '项目名称',
    `result_value` VARCHAR(200) DEFAULT NULL COMMENT '结果值',
    `result_text` VARCHAR(500) DEFAULT NULL COMMENT '结果文本',
    `result_flag` VARCHAR(10) DEFAULT NULL COMMENT '结果标志（N正常 H偏高 L偏低 HH极高 LL极低）',
    `unit` VARCHAR(50) DEFAULT NULL COMMENT '单位',
    `reference_low` DECIMAL(18,4) DEFAULT NULL COMMENT '参考值下限',
    `reference_high` DECIMAL(18,4) DEFAULT NULL COMMENT '参考值上限',
    `reference_text` VARCHAR(500) DEFAULT NULL COMMENT '参考值文本',
    `is_panic` TINYINT DEFAULT 0 COMMENT '是否危急值（0否 1是）',
    `is_abnormal` TINYINT DEFAULT 0 COMMENT '是否异常（0否 1是）',
    `status` VARCHAR(20) DEFAULT 'pending' COMMENT '状态（pending待检验 testing检验中 completed已完成）',
    `equipment_id` BIGINT DEFAULT NULL COMMENT '检验设备ID',
    `equipment_name` VARCHAR(100) DEFAULT NULL COMMENT '检验设备名称',
    `test_time` DATETIME DEFAULT NULL COMMENT '检验时间',
    `test_user_id` BIGINT DEFAULT NULL COMMENT '检验人ID',
    `test_user_name` VARCHAR(50) DEFAULT NULL COMMENT '检验人姓名',
    `audit_time` DATETIME DEFAULT NULL COMMENT '审核时间',
    `audit_user_id` BIGINT DEFAULT NULL COMMENT '审核人ID',
    `audit_user_name` VARCHAR(50) DEFAULT NULL COMMENT '审核人姓名',
    `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    KEY `idx_specimen_id` (`specimen_id`),
    KEY `idx_specimen_no` (`specimen_no`),
    KEY `idx_test_item_id` (`test_item_id`),
    KEY `idx_status` (`status`)
    `del_flag` TINYINT DEFAULT 0 COMMENT '逻辑删除标记（0存在 1删除）',
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='标本检验项目表';

-- ---------------------------------------------
-- 标本流转记录表
-- ---------------------------------------------
DROP TABLE IF EXISTS `specimen_trace`;
CREATE TABLE `specimen_trace` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `specimen_id` BIGINT NOT NULL COMMENT '标本ID',
    `specimen_no` VARCHAR(50) NOT NULL COMMENT '标本编号',
    `action` VARCHAR(50) NOT NULL COMMENT '操作动作',
    `action_name` VARCHAR(100) DEFAULT NULL COMMENT '操作名称',
    `from_status` VARCHAR(20) DEFAULT NULL COMMENT '原状态',
    `to_status` VARCHAR(20) DEFAULT NULL COMMENT '目标状态',
    `from_location` VARCHAR(200) DEFAULT NULL COMMENT '原位置',
    `to_location` VARCHAR(200) DEFAULT NULL COMMENT '目标位置',
    `operator_id` BIGINT DEFAULT NULL COMMENT '操作人ID',
    `operator_name` VARCHAR(50) DEFAULT NULL COMMENT '操作人姓名',
    `operate_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    KEY `idx_specimen_id` (`specimen_id`),
    KEY `idx_specimen_no` (`specimen_no`),
    KEY `idx_operate_time` (`operate_time`)
    `del_flag` TINYINT DEFAULT 0 COMMENT '逻辑删除标记（0存在 1删除）',
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='标本流转记录表';

-- ---------------------------------------------
-- 标本拒收记录表
-- ---------------------------------------------
DROP TABLE IF EXISTS `specimen_reject`;
CREATE TABLE `specimen_reject` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `specimen_id` BIGINT NOT NULL COMMENT '标本ID',
    `specimen_no` VARCHAR(50) NOT NULL COMMENT '标本编号',
    `reject_type` VARCHAR(50) DEFAULT NULL COMMENT '拒收类型',
    `reject_reason` VARCHAR(500) NOT NULL COMMENT '拒收原因',
    `reject_user_id` BIGINT DEFAULT NULL COMMENT '拒收人ID',
    `reject_user_name` VARCHAR(50) DEFAULT NULL COMMENT '拒收人姓名',
    `reject_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '拒收时间',
    `handle_status` TINYINT DEFAULT 0 COMMENT '处理状态（0待处理 1已处理）',
    `handle_result` VARCHAR(500) DEFAULT NULL COMMENT '处理结果',
    `handle_user_id` BIGINT DEFAULT NULL COMMENT '处理人ID',
    `handle_user_name` VARCHAR(50) DEFAULT NULL COMMENT '处理人姓名',
    `handle_time` DATETIME DEFAULT NULL COMMENT '处理时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    KEY `idx_specimen_id` (`specimen_id`),
    KEY `idx_specimen_no` (`specimen_no`)
    `del_flag` TINYINT DEFAULT 0 COMMENT '逻辑删除标记（0存在 1删除）',
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='标本拒收记录表';

-- ---------------------------------------------
-- 标本附加检验项目表
-- ---------------------------------------------
DROP TABLE IF EXISTS `specimen_addition`;
CREATE TABLE `specimen_addition` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `specimen_id` BIGINT NOT NULL COMMENT '标本ID',
    `specimen_no` VARCHAR(50) NOT NULL COMMENT '标本编号',
    `test_item_id` BIGINT NOT NULL COMMENT '检验项目ID',
    `item_code` VARCHAR(50) NOT NULL COMMENT '项目编码',
    `item_name` VARCHAR(200) NOT NULL COMMENT '项目名称',
    `add_reason` VARCHAR(500) DEFAULT NULL COMMENT '附加原因',
    `add_user_id` BIGINT DEFAULT NULL COMMENT '附加人ID',
    `add_user_name` VARCHAR(50) DEFAULT NULL COMMENT '附加人姓名',
    `add_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '附加时间',
    `status` TINYINT DEFAULT 0 COMMENT '状态（0待检验 1已检验）',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    KEY `idx_specimen_id` (`specimen_id`),
    KEY `idx_specimen_no` (`specimen_no`)
    `del_flag` TINYINT DEFAULT 0 COMMENT '逻辑删除标记（0存在 1删除）',
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='标本附加检验项目表';
