-- =============================================
-- LIS HL7服务数据库初始化脚本
-- 数据库: lis_hl7
-- 包含: HL7消息、接口配置表
-- =============================================

CREATE DATABASE IF NOT EXISTS `lis_hl7` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE `lis_hl7`;

-- ---------------------------------------------
-- 接口配置表
-- ---------------------------------------------
DROP TABLE IF EXISTS `interface_config`;
CREATE TABLE `interface_config` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '接口ID',
    `interface_code` VARCHAR(50) NOT NULL COMMENT '接口编码',
    `interface_name` VARCHAR(200) NOT NULL COMMENT '接口名称',
    `interface_type` VARCHAR(50) DEFAULT 'hl7' COMMENT '接口类型（hl7 astm xml json websocket）',
    `direction` VARCHAR(20) DEFAULT 'bidirectional' COMMENT '接口方向（inbound入站 outbound出站 bidirectional双向）',
    `protocol` VARCHAR(50) DEFAULT 'TCP' COMMENT '协议类型（TCP HTTP HTTPS MLLP）',
    `host` VARCHAR(100) DEFAULT NULL COMMENT '主机地址',
    `port` INT DEFAULT NULL COMMENT '端口号',
    `charset` VARCHAR(20) DEFAULT 'UTF-8' COMMENT '字符编码',
    `encoding` VARCHAR(50) DEFAULT 'HL7_2.5' COMMENT '消息编码格式',
    `connection_timeout` INT DEFAULT 30000 COMMENT '连接超时时间（毫秒）',
    `read_timeout` INT DEFAULT 60000 COMMENT '读取超时时间（毫秒）',
    `retry_count` INT DEFAULT 3 COMMENT '重试次数',
    `retry_interval` INT DEFAULT 5000 COMMENT '重试间隔（毫秒）',
    `ack_mode` VARCHAR(20) DEFAULT 'auto' COMMENT '确认模式（auto自动 manual手动）',
    `message_type` VARCHAR(100) DEFAULT NULL COMMENT '消息类型（多个用逗号分隔）',
    `trigger_event` VARCHAR(100) DEFAULT NULL COMMENT '触发事件（多个用逗号分隔）',
    `processing_rule_id` VARCHAR(50) DEFAULT NULL COMMENT '处理规则ID',
    `transform_rule_id` VARCHAR(50) DEFAULT NULL COMMENT '转换规则ID',
    `validation_rule_id` VARCHAR(50) DEFAULT NULL COMMENT '验证规则ID',
    `is_enabled` TINYINT DEFAULT 1 COMMENT '是否启用（0否 1是）',
    `is_auto_start` TINYINT DEFAULT 1 COMMENT '是否自动启动（0否 1是）',
    `status` VARCHAR(20) DEFAULT 'stopped' COMMENT '运行状态（stopped已停止 running运行中 error异常）',
    `last_start_time` DATETIME DEFAULT NULL COMMENT '最后启动时间',
    `last_stop_time` DATETIME DEFAULT NULL COMMENT '最后停止时间',
    `total_received` BIGINT DEFAULT 0 COMMENT '累计接收消息数',
    `total_sent` BIGINT DEFAULT 0 COMMENT '累计发送消息数',
    `total_error` BIGINT DEFAULT 0 COMMENT '累计错误数',
    `last_error_time` DATETIME DEFAULT NULL COMMENT '最后错误时间',
    `last_error_msg` VARCHAR(1000) DEFAULT NULL COMMENT '最后错误信息',
    `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_interface_code` (`interface_code`),
    KEY `idx_interface_type` (`interface_type`),
    KEY `idx_direction` (`direction`),
    KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='接口配置表';

-- ---------------------------------------------
-- HL7消息表
-- ---------------------------------------------
DROP TABLE IF EXISTS `hl7_message`;
CREATE TABLE `hl7_message` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '消息ID',
    `message_id` VARCHAR(100) DEFAULT NULL COMMENT '消息唯一标识',
    `interface_id` BIGINT DEFAULT NULL COMMENT '接口ID',
    `interface_code` VARCHAR(50) DEFAULT NULL COMMENT '接口编码',
    `direction` VARCHAR(20) DEFAULT 'inbound' COMMENT '消息方向（inbound入站 outbound出站）',
    `message_type` VARCHAR(20) DEFAULT NULL COMMENT '消息类型（ADT ORM ORU QRY等）',
    `trigger_event` VARCHAR(20) DEFAULT NULL COMMENT '触发事件（A01 A04 R01等）',
    `message_control_id` VARCHAR(100) DEFAULT NULL COMMENT '消息控制ID',
    `processing_id` VARCHAR(10) DEFAULT NULL COMMENT '处理ID（P生产 D调试 T训练）',
    `version_id` VARCHAR(20) DEFAULT NULL COMMENT 'HL7版本',
    `sending_app` VARCHAR(100) DEFAULT NULL COMMENT '发送应用',
    `sending_facility` VARCHAR(100) DEFAULT NULL COMMENT '发送机构',
    `receiving_app` VARCHAR(100) DEFAULT NULL COMMENT '接收应用',
    `receiving_facility` VARCHAR(100) DEFAULT NULL COMMENT '接收机构',
    `message_time` DATETIME DEFAULT NULL COMMENT '消息时间',
    `patient_id` VARCHAR(50) DEFAULT NULL COMMENT '患者ID',
    `patient_name` VARCHAR(100) DEFAULT NULL COMMENT '患者姓名',
    `visit_no` VARCHAR(50) DEFAULT NULL COMMENT '就诊号',
    `specimen_no` VARCHAR(50) DEFAULT NULL COMMENT '标本编号',
    `raw_message` LONGTEXT COMMENT '原始消息',
    `parsed_message` LONGTEXT COMMENT '解析后消息（JSON格式）',
    `transformed_message` LONGTEXT COMMENT '转换后消息',
    `ack_message` LONGTEXT COMMENT '应答消息',
    `ack_status` VARCHAR(20) DEFAULT NULL COMMENT '应答状态（AA成功 AE应用错误 AR拒绝）',
    `ack_code` VARCHAR(20) DEFAULT NULL COMMENT '应答代码',
    `ack_message_text` VARCHAR(500) DEFAULT NULL COMMENT '应答消息文本',
    `process_status` VARCHAR(20) DEFAULT 'pending' COMMENT '处理状态（pending待处理 processing处理中 success成功 failed失败）',
    `process_time` DATETIME DEFAULT NULL COMMENT '处理时间',
    `process_duration` BIGINT DEFAULT NULL COMMENT '处理耗时（毫秒）',
    `error_code` VARCHAR(50) DEFAULT NULL COMMENT '错误代码',
    `error_msg` VARCHAR(2000) DEFAULT NULL COMMENT '错误信息',
    `retry_count` INT DEFAULT 0 COMMENT '重试次数',
    `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    KEY `idx_message_id` (`message_id`),
    KEY `idx_interface_id` (`interface_id`),
    KEY `idx_message_type` (`message_type`),
    KEY `idx_message_control_id` (`message_control_id`),
    KEY `idx_process_status` (`process_status`),
    KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='HL7消息表';

-- ---------------------------------------------
-- HL7消息明细表
-- ---------------------------------------------
DROP TABLE IF EXISTS `hl7_message_segment`;
CREATE TABLE `hl7_message_segment` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `message_id` BIGINT NOT NULL COMMENT '消息ID',
    `segment_name` VARCHAR(10) NOT NULL COMMENT '段名（MSH PID PV1 ORC OBR OBX等）',
    `segment_seq` INT DEFAULT 0 COMMENT '段序号',
    `segment_content` TEXT COMMENT '段内容',
    `parsed_data` LONGTEXT COMMENT '解析数据（JSON格式）',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_message_id` (`message_id`),
    KEY `idx_segment_name` (`segment_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='HL7消息明细表';

-- ---------------------------------------------
-- 消息转换规则表
-- ---------------------------------------------
DROP TABLE IF EXISTS `transform_rule`;
CREATE TABLE `transform_rule` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '规则ID',
    `rule_code` VARCHAR(50) NOT NULL COMMENT '规则编码',
    `rule_name` VARCHAR(200) NOT NULL COMMENT '规则名称',
    `rule_type` VARCHAR(50) DEFAULT 'field' COMMENT '规则类型（field字段 segment段 message消息）',
    `source_type` VARCHAR(50) DEFAULT 'hl7' COMMENT '源类型',
    `target_type` VARCHAR(50) DEFAULT 'json' COMMENT '目标类型',
    `source_path` VARCHAR(200) DEFAULT NULL COMMENT '源路径',
    `target_path` VARCHAR(200) DEFAULT NULL COMMENT '目标路径',
    `transform_expr` VARCHAR(500) DEFAULT NULL COMMENT '转换表达式',
    `default_value` VARCHAR(200) DEFAULT NULL COMMENT '默认值',
    `value_mapping` TEXT COMMENT '值映射（JSON格式）',
    `is_required` TINYINT DEFAULT 0 COMMENT '是否必填（0否 1是）',
    `status` TINYINT DEFAULT 0 COMMENT '状态（0正常 1停用）',
    `sort` INT DEFAULT 0 COMMENT '排序',
    `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_rule_code` (`rule_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='消息转换规则表';

-- ---------------------------------------------
-- 消息验证规则表
-- ---------------------------------------------
DROP TABLE IF EXISTS `validation_rule`;
CREATE TABLE `validation_rule` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '规则ID',
    `rule_code` VARCHAR(50) NOT NULL COMMENT '规则编码',
    `rule_name` VARCHAR(200) NOT NULL COMMENT '规则名称',
    `rule_type` VARCHAR(50) DEFAULT 'field' COMMENT '规则类型（field字段 segment段 message消息）',
    `message_type` VARCHAR(20) DEFAULT NULL COMMENT '消息类型',
    `trigger_event` VARCHAR(20) DEFAULT NULL COMMENT '触发事件',
    `segment_name` VARCHAR(10) DEFAULT NULL COMMENT '段名',
    `field_path` VARCHAR(200) DEFAULT NULL COMMENT '字段路径',
    `validation_type` VARCHAR(50) DEFAULT 'required' COMMENT '验证类型（required必填 format格式 range范围 regex正则 custom自定义）',
    `validation_expr` VARCHAR(500) DEFAULT NULL COMMENT '验证表达式',
    `error_code` VARCHAR(50) DEFAULT NULL COMMENT '错误代码',
    `error_msg` VARCHAR(500) DEFAULT NULL COMMENT '错误信息',
    `is_enabled` TINYINT DEFAULT 1 COMMENT '是否启用（0否 1是）',
    `sort` INT DEFAULT 0 COMMENT '排序',
    `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_rule_code` (`rule_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='消息验证规则表';

-- ---------------------------------------------
-- 消息处理规则表
-- ---------------------------------------------
DROP TABLE IF EXISTS `processing_rule`;
CREATE TABLE `processing_rule` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '规则ID',
    `rule_code` VARCHAR(50) NOT NULL COMMENT '规则编码',
    `rule_name` VARCHAR(200) NOT NULL COMMENT '规则名称',
    `interface_id` BIGINT DEFAULT NULL COMMENT '接口ID',
    `message_type` VARCHAR(20) DEFAULT NULL COMMENT '消息类型',
    `trigger_event` VARCHAR(20) DEFAULT NULL COMMENT '触发事件',
    `condition_expr` VARCHAR(500) DEFAULT NULL COMMENT '条件表达式',
    `action_type` VARCHAR(50) DEFAULT 'route' COMMENT '动作类型（route路由 transform转换 store存储 notify通知）',
    `action_config` TEXT COMMENT '动作配置（JSON格式）',
    `priority` INT DEFAULT 0 COMMENT '优先级',
    `is_enabled` TINYINT DEFAULT 1 COMMENT '是否启用（0否 1是）',
    `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_rule_code` (`rule_code`),
    KEY `idx_interface_id` (`interface_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='消息处理规则表';

-- ---------------------------------------------
-- 消息路由表
-- ---------------------------------------------
DROP TABLE IF EXISTS `message_route`;
CREATE TABLE `message_route` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '路由ID',
    `route_code` VARCHAR(50) NOT NULL COMMENT '路由编码',
    `route_name` VARCHAR(200) NOT NULL COMMENT '路由名称',
    `source_interface_id` BIGINT NOT NULL COMMENT '源接口ID',
    `target_interface_id` BIGINT NOT NULL COMMENT '目标接口ID',
    `message_type` VARCHAR(20) DEFAULT NULL COMMENT '消息类型',
    `trigger_event` VARCHAR(20) DEFAULT NULL COMMENT '触发事件',
    `condition_expr` VARCHAR(500) DEFAULT NULL COMMENT '路由条件',
    `transform_rule_id` BIGINT DEFAULT NULL COMMENT '转换规则ID',
    `is_enabled` TINYINT DEFAULT 1 COMMENT '是否启用（0否 1是）',
    `priority` INT DEFAULT 0 COMMENT '优先级',
    `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_route_code` (`route_code`),
    KEY `idx_source_interface_id` (`source_interface_id`),
    KEY `idx_target_interface_id` (`target_interface_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='消息路由表';

-- ---------------------------------------------
-- 接口连接日志表
-- ---------------------------------------------
DROP TABLE IF EXISTS `interface_connection_log`;
CREATE TABLE `interface_connection_log` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '日志ID',
    `interface_id` BIGINT NOT NULL COMMENT '接口ID',
    `interface_code` VARCHAR(50) DEFAULT NULL COMMENT '接口编码',
    `event_type` VARCHAR(50) NOT NULL COMMENT '事件类型（connect连接 disconnect断开 error错误）',
    `event_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '事件时间',
    `client_ip` VARCHAR(50) DEFAULT NULL COMMENT '客户端IP',
    `client_port` INT DEFAULT NULL COMMENT '客户端端口',
    `event_desc` VARCHAR(500) DEFAULT NULL COMMENT '事件描述',
    `error_code` VARCHAR(50) DEFAULT NULL COMMENT '错误代码',
    `error_msg` VARCHAR(1000) DEFAULT NULL COMMENT '错误信息',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_interface_id` (`interface_id`),
    KEY `idx_event_time` (`event_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='接口连接日志表';

-- ---------------------------------------------
-- HL7段模板表
-- ---------------------------------------------
DROP TABLE IF EXISTS `hl7_segment_template`;
CREATE TABLE `hl7_segment_template` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '模板ID',
    `template_code` VARCHAR(50) NOT NULL COMMENT '模板编码',
    `template_name` VARCHAR(200) NOT NULL COMMENT '模板名称',
    `segment_name` VARCHAR(10) NOT NULL COMMENT '段名',
    `segment_content` TEXT COMMENT '段内容模板',
    `field_mapping` TEXT COMMENT '字段映射（JSON格式）',
    `is_default` TINYINT DEFAULT 0 COMMENT '是否默认（0否 1是）',
    `status` TINYINT DEFAULT 0 COMMENT '状态（0正常 1停用）',
    `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_template_code` (`template_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='HL7段模板表';

-- ---------------------------------------------
-- HL7消息模板表
-- ---------------------------------------------
DROP TABLE IF EXISTS `hl7_message_template`;
CREATE TABLE `hl7_message_template` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '模板ID',
    `template_code` VARCHAR(50) NOT NULL COMMENT '模板编码',
    `template_name` VARCHAR(200) NOT NULL COMMENT '模板名称',
    `message_type` VARCHAR(20) NOT NULL COMMENT '消息类型',
    `trigger_event` VARCHAR(20) NOT NULL COMMENT '触发事件',
    `template_content` LONGTEXT COMMENT '模板内容',
    `segment_list` TEXT COMMENT '段列表（JSON格式）',
    `is_default` TINYINT DEFAULT 0 COMMENT '是否默认（0否 1是）',
    `status` TINYINT DEFAULT 0 COMMENT '状态（0正常 1停用）',
    `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_template_code` (`template_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='HL7消息模板表';
