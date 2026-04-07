-- =============================================
-- LIS报告服务数据库初始化脚本
-- 数据库: lis_report
-- 包含: 报告、患者、危急值表
-- =============================================

CREATE DATABASE IF NOT EXISTS `lis_report` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE `lis_report`;

-- ---------------------------------------------
-- 患者信息表
-- ---------------------------------------------
DROP TABLE IF EXISTS `patient`;
CREATE TABLE `patient` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '患者ID',
    `patient_no` VARCHAR(50) DEFAULT NULL COMMENT '患者编号',
    `patient_name` VARCHAR(50) NOT NULL COMMENT '患者姓名',
    `gender` CHAR(1) DEFAULT '0' COMMENT '性别（0男 1女 2未知）',
    `birth_date` DATE DEFAULT NULL COMMENT '出生日期',
    `age` VARCHAR(20) DEFAULT NULL COMMENT '年龄',
    `id_type` VARCHAR(20) DEFAULT '01' COMMENT '证件类型（01身份证 02护照 03军官证 04其他）',
    `id_no` VARCHAR(50) DEFAULT NULL COMMENT '证件号码',
    `phone` VARCHAR(20) DEFAULT NULL COMMENT '联系电话',
    `email` VARCHAR(100) DEFAULT NULL COMMENT '电子邮箱',
    `address` VARCHAR(500) DEFAULT NULL COMMENT '家庭住址',
    `nation` VARCHAR(50) DEFAULT NULL COMMENT '民族',
    `marriage` VARCHAR(10) DEFAULT NULL COMMENT '婚姻状况',
    `occupation` VARCHAR(50) DEFAULT NULL COMMENT '职业',
    `work_unit` VARCHAR(200) DEFAULT NULL COMMENT '工作单位',
    `contact_name` VARCHAR(50) DEFAULT NULL COMMENT '联系人姓名',
    `contact_phone` VARCHAR(20) DEFAULT NULL COMMENT '联系人电话',
    `contact_relation` VARCHAR(50) DEFAULT NULL COMMENT '联系人关系',
    `blood_type` VARCHAR(10) DEFAULT NULL COMMENT '血型',
    `allergy_history` TEXT COMMENT '过敏史',
    `medical_history` TEXT COMMENT '既往病史',
    `status` TINYINT DEFAULT 0 COMMENT '状态（0正常 1停用）',
    `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_patient_no` (`patient_no`),
    KEY `idx_id_no` (`id_no`),
    KEY `idx_patient_name` (`patient_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='患者信息表';

-- ---------------------------------------------
-- 患者就诊记录表
-- ---------------------------------------------
DROP TABLE IF EXISTS `patient_visit`;
CREATE TABLE `patient_visit` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '就诊ID',
    `visit_no` VARCHAR(50) NOT NULL COMMENT '就诊号',
    `patient_id` BIGINT NOT NULL COMMENT '患者ID',
    `patient_name` VARCHAR(50) DEFAULT NULL COMMENT '患者姓名',
    `visit_type` VARCHAR(20) DEFAULT 'outpatient' COMMENT '就诊类型（outpatient门诊 inpatient住院 emergency急诊 physical体检）',
    `dept_id` BIGINT DEFAULT NULL COMMENT '科室ID',
    `dept_name` VARCHAR(100) DEFAULT NULL COMMENT '科室名称',
    `ward_id` BIGINT DEFAULT NULL COMMENT '病区ID',
    `ward_name` VARCHAR(100) DEFAULT NULL COMMENT '病区名称',
    `bed_no` VARCHAR(20) DEFAULT NULL COMMENT '床号',
    `doctor_id` BIGINT DEFAULT NULL COMMENT '主治医生ID',
    `doctor_name` VARCHAR(50) DEFAULT NULL COMMENT '主治医生姓名',
    `diagnosis` VARCHAR(500) DEFAULT NULL COMMENT '诊断',
    `visit_time` DATETIME DEFAULT NULL COMMENT '就诊时间',
    `discharge_time` DATETIME DEFAULT NULL COMMENT '出院时间',
    `status` TINYINT DEFAULT 0 COMMENT '状态（0就诊中 1已出院）',
    `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_visit_no` (`visit_no`),
    KEY `idx_patient_id` (`patient_id`),
    KEY `idx_visit_type` (`visit_type`),
    KEY `idx_visit_time` (`visit_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='患者就诊记录表';

-- ---------------------------------------------
-- 报告主表
-- ---------------------------------------------
DROP TABLE IF EXISTS `report`;
CREATE TABLE `report` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '报告ID',
    `report_no` VARCHAR(50) NOT NULL COMMENT '报告编号',
    `report_type` VARCHAR(50) DEFAULT 'routine' COMMENT '报告类型（routine常规 stat急诊 review复查）',
    `specimen_id` BIGINT NOT NULL COMMENT '标本ID',
    `specimen_no` VARCHAR(50) NOT NULL COMMENT '标本编号',
    `patient_id` BIGINT DEFAULT NULL COMMENT '患者ID',
    `patient_name` VARCHAR(50) DEFAULT NULL COMMENT '患者姓名',
    `patient_gender` CHAR(1) DEFAULT NULL COMMENT '患者性别',
    `patient_age` VARCHAR(20) DEFAULT NULL COMMENT '患者年龄',
    `patient_id_no` VARCHAR(50) DEFAULT NULL COMMENT '患者身份证号',
    `dept_id` BIGINT DEFAULT NULL COMMENT '科室ID',
    `dept_name` VARCHAR(100) DEFAULT NULL COMMENT '科室名称',
    `ward_name` VARCHAR(100) DEFAULT NULL COMMENT '病区名称',
    `bed_no` VARCHAR(20) DEFAULT NULL COMMENT '床号',
    `doctor_name` VARCHAR(50) DEFAULT NULL COMMENT '送检医生',
    `specimen_type_name` VARCHAR(100) DEFAULT NULL COMMENT '标本类型',
    `collect_time` DATETIME DEFAULT NULL COMMENT '采集时间',
    `receive_time` DATETIME DEFAULT NULL COMMENT '接收时间',
    `test_time` DATETIME DEFAULT NULL COMMENT '检验时间',
    `audit_time` DATETIME DEFAULT NULL COMMENT '审核时间',
    `report_time` DATETIME DEFAULT NULL COMMENT '报告时间',
    `test_user_id` BIGINT DEFAULT NULL COMMENT '检验人ID',
    `test_user_name` VARCHAR(50) DEFAULT NULL COMMENT '检验人姓名',
    `audit_user_id` BIGINT DEFAULT NULL COMMENT '审核人ID',
    `audit_user_name` VARCHAR(50) DEFAULT NULL COMMENT '审核人姓名',
    `report_user_id` BIGINT DEFAULT NULL COMMENT '报告人ID',
    `report_user_name` VARCHAR(50) DEFAULT NULL COMMENT '报告人姓名',
    `status` VARCHAR(20) DEFAULT 'draft' COMMENT '报告状态（draft草稿 audited已审核 reported已报告 printed已打印 cancelled已取消）',
    `is_stat` TINYINT DEFAULT 0 COMMENT '是否急诊（0否 1是）',
    `is_panic` TINYINT DEFAULT 0 COMMENT '是否含危急值（0否 1是）',
    `is_abnormal` TINYINT DEFAULT 0 COMMENT '是否异常（0否 1是）',
    `print_count` INT DEFAULT 0 COMMENT '打印次数',
    `last_print_time` DATETIME DEFAULT NULL COMMENT '最后打印时间',
    `clinical_diagnosis` VARCHAR(500) DEFAULT NULL COMMENT '临床诊断',
    `report_conclusion` TEXT COMMENT '报告结论',
    `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_report_no` (`report_no`),
    KEY `idx_specimen_id` (`specimen_id`),
    KEY `idx_specimen_no` (`specimen_no`),
    KEY `idx_patient_id` (`patient_id`),
    KEY `idx_status` (`status`),
    KEY `idx_report_time` (`report_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='报告主表';

-- ---------------------------------------------
-- 报告明细表
-- ---------------------------------------------
DROP TABLE IF EXISTS `report_item`;
CREATE TABLE `report_item` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `report_id` BIGINT NOT NULL COMMENT '报告ID',
    `report_no` VARCHAR(50) NOT NULL COMMENT '报告编号',
    `specimen_test_item_id` BIGINT DEFAULT NULL COMMENT '标本检验项目ID',
    `item_code` VARCHAR(50) NOT NULL COMMENT '项目编码',
    `item_name` VARCHAR(200) NOT NULL COMMENT '项目名称',
    `item_name_en` VARCHAR(200) DEFAULT NULL COMMENT '项目英文名',
    `result_value` VARCHAR(200) DEFAULT NULL COMMENT '结果值',
    `result_text` VARCHAR(500) DEFAULT NULL COMMENT '结果文本',
    `result_flag` VARCHAR(10) DEFAULT NULL COMMENT '结果标志（N正常 H偏高 L偏低 HH极高 LL极低）',
    `unit` VARCHAR(50) DEFAULT NULL COMMENT '单位',
    `reference_low` DECIMAL(18,4) DEFAULT NULL COMMENT '参考值下限',
    `reference_high` DECIMAL(18,4) DEFAULT NULL COMMENT '参考值上限',
    `reference_text` VARCHAR(500) DEFAULT NULL COMMENT '参考值文本',
    `is_panic` TINYINT DEFAULT 0 COMMENT '是否危急值（0否 1是）',
    `is_abnormal` TINYINT DEFAULT 0 COMMENT '是否异常（0否 1是）',
    `method` VARCHAR(200) DEFAULT NULL COMMENT '检验方法',
    `equipment_name` VARCHAR(100) DEFAULT NULL COMMENT '检验设备',
    `sort` INT DEFAULT 0 COMMENT '排序',
    `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    KEY `idx_report_id` (`report_id`),
    KEY `idx_report_no` (`report_no`),
    KEY `idx_item_code` (`item_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='报告明细表';

-- ---------------------------------------------
-- 危急值记录表
-- ---------------------------------------------
DROP TABLE IF EXISTS `panic_value`;
CREATE TABLE `panic_value` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `panic_no` VARCHAR(50) NOT NULL COMMENT '危急值编号',
    `report_id` BIGINT DEFAULT NULL COMMENT '报告ID',
    `report_no` VARCHAR(50) DEFAULT NULL COMMENT '报告编号',
    `specimen_id` BIGINT DEFAULT NULL COMMENT '标本ID',
    `specimen_no` VARCHAR(50) DEFAULT NULL COMMENT '标本编号',
    `patient_id` BIGINT DEFAULT NULL COMMENT '患者ID',
    `patient_name` VARCHAR(50) DEFAULT NULL COMMENT '患者姓名',
    `dept_id` BIGINT DEFAULT NULL COMMENT '科室ID',
    `dept_name` VARCHAR(100) DEFAULT NULL COMMENT '科室名称',
    `ward_name` VARCHAR(100) DEFAULT NULL COMMENT '病区名称',
    `bed_no` VARCHAR(20) DEFAULT NULL COMMENT '床号',
    `item_code` VARCHAR(50) NOT NULL COMMENT '项目编码',
    `item_name` VARCHAR(200) NOT NULL COMMENT '项目名称',
    `result_value` VARCHAR(200) DEFAULT NULL COMMENT '结果值',
    `unit` VARCHAR(50) DEFAULT NULL COMMENT '单位',
    `panic_low` DECIMAL(18,4) DEFAULT NULL COMMENT '危急值下限',
    `panic_high` DECIMAL(18,4) DEFAULT NULL COMMENT '危急值上限',
    `panic_type` VARCHAR(20) DEFAULT 'high' COMMENT '危急值类型（high偏高 low偏低）',
    `find_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '发现时间',
    `find_user_id` BIGINT DEFAULT NULL COMMENT '发现人ID',
    `find_user_name` VARCHAR(50) DEFAULT NULL COMMENT '发现人姓名',
    `notify_time` DATETIME DEFAULT NULL COMMENT '通知时间',
    `notify_user_id` BIGINT DEFAULT NULL COMMENT '通知人ID',
    `notify_user_name` VARCHAR(50) DEFAULT NULL COMMENT '通知人姓名',
    `notify_method` VARCHAR(50) DEFAULT NULL COMMENT '通知方式',
    `receive_user_name` VARCHAR(50) DEFAULT NULL COMMENT '接收人姓名',
    `receive_time` DATETIME DEFAULT NULL COMMENT '接收时间',
    `handle_status` TINYINT DEFAULT 0 COMMENT '处理状态（0待处理 1已通知 2已处理 3已确认）',
    `handle_time` DATETIME DEFAULT NULL COMMENT '处理时间',
    `handle_user_id` BIGINT DEFAULT NULL COMMENT '处理人ID',
    `handle_user_name` VARCHAR(50) DEFAULT NULL COMMENT '处理人姓名',
    `handle_result` VARCHAR(500) DEFAULT NULL COMMENT '处理结果',
    `confirm_time` DATETIME DEFAULT NULL COMMENT '确认时间',
    `confirm_user_id` BIGINT DEFAULT NULL COMMENT '确认人ID',
    `confirm_user_name` VARCHAR(50) DEFAULT NULL COMMENT '确认人姓名',
    `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_panic_no` (`panic_no`),
    KEY `idx_report_id` (`report_id`),
    KEY `idx_specimen_id` (`specimen_id`),
    KEY `idx_patient_id` (`patient_id`),
    KEY `idx_handle_status` (`handle_status`),
    KEY `idx_find_time` (`find_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='危急值记录表';

-- ---------------------------------------------
-- 报告打印记录表
-- ---------------------------------------------
DROP TABLE IF EXISTS `report_print_log`;
CREATE TABLE `report_print_log` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `report_id` BIGINT NOT NULL COMMENT '报告ID',
    `report_no` VARCHAR(50) NOT NULL COMMENT '报告编号',
    `print_user_id` BIGINT DEFAULT NULL COMMENT '打印人ID',
    `print_user_name` VARCHAR(50) DEFAULT NULL COMMENT '打印人姓名',
    `print_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '打印时间',
    `print_copies` INT DEFAULT 1 COMMENT '打印份数',
    `printer_name` VARCHAR(100) DEFAULT NULL COMMENT '打印机名称',
    `print_ip` VARCHAR(50) DEFAULT NULL COMMENT '打印IP',
    `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_report_id` (`report_id`),
    KEY `idx_report_no` (`report_no`),
    KEY `idx_print_time` (`print_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='报告打印记录表';

-- ---------------------------------------------
-- 报告修改记录表
-- ---------------------------------------------
DROP TABLE IF EXISTS `report_modify_log`;
CREATE TABLE `report_modify_log` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `report_id` BIGINT NOT NULL COMMENT '报告ID',
    `report_no` VARCHAR(50) NOT NULL COMMENT '报告编号',
    `modify_type` VARCHAR(50) DEFAULT NULL COMMENT '修改类型',
    `modify_field` VARCHAR(100) DEFAULT NULL COMMENT '修改字段',
    `old_value` TEXT COMMENT '原值',
    `new_value` TEXT COMMENT '新值',
    `modify_reason` VARCHAR(500) DEFAULT NULL COMMENT '修改原因',
    `modify_user_id` BIGINT DEFAULT NULL COMMENT '修改人ID',
    `modify_user_name` VARCHAR(50) DEFAULT NULL COMMENT '修改人姓名',
    `modify_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `audit_status` TINYINT DEFAULT 0 COMMENT '审核状态（0待审核 1已通过 2已拒绝）',
    `audit_user_id` BIGINT DEFAULT NULL COMMENT '审核人ID',
    `audit_user_name` VARCHAR(50) DEFAULT NULL COMMENT '审核人姓名',
    `audit_time` DATETIME DEFAULT NULL COMMENT '审核时间',
    `audit_remark` VARCHAR(500) DEFAULT NULL COMMENT '审核备注',
    `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_report_id` (`report_id`),
    KEY `idx_report_no` (`report_no`),
    KEY `idx_modify_time` (`modify_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='报告修改记录表';

-- ---------------------------------------------
-- 报告模板表
-- ---------------------------------------------
DROP TABLE IF EXISTS `report_template`;
CREATE TABLE `report_template` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '模板ID',
    `template_code` VARCHAR(50) NOT NULL COMMENT '模板编码',
    `template_name` VARCHAR(100) NOT NULL COMMENT '模板名称',
    `template_type` VARCHAR(50) DEFAULT NULL COMMENT '模板类型',
    `template_content` TEXT COMMENT '模板内容',
    `template_style` TEXT COMMENT '模板样式',
    `is_default` TINYINT DEFAULT 0 COMMENT '是否默认（0否 1是）',
    `status` TINYINT DEFAULT 0 COMMENT '状态（0正常 1停用）',
    `sort` INT DEFAULT 0 COMMENT '排序',
    `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_template_code` (`template_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='报告模板表';
