-- =============================================
-- LIS AI服务数据库初始化脚本
-- 数据库: lis_ai
-- 包含: AI诊断规则、诊断记录表
-- =============================================

CREATE DATABASE IF NOT EXISTS `lis_ai` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE `lis_ai`;

-- ---------------------------------------------
-- AI模型配置表
-- ---------------------------------------------
DROP TABLE IF EXISTS `ai_model`;
CREATE TABLE `ai_model` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '模型ID',
    `model_code` VARCHAR(50) NOT NULL COMMENT '模型编码',
    `model_name` VARCHAR(200) NOT NULL COMMENT '模型名称',
    `model_type` VARCHAR(50) DEFAULT 'classification' COMMENT '模型类型（classification分类 regression回归 clustering聚类）',
    `model_version` VARCHAR(50) DEFAULT NULL COMMENT '模型版本',
    `model_desc` VARCHAR(500) DEFAULT NULL COMMENT '模型描述',
    `algorithm_type` VARCHAR(50) DEFAULT NULL COMMENT '算法类型（decision_tree random_forest neural_network svm xgboost等）',
    `model_path` VARCHAR(500) DEFAULT NULL COMMENT '模型文件路径',
    `model_params` TEXT COMMENT '模型参数（JSON格式）',
    `feature_config` TEXT COMMENT '特征配置（JSON格式）',
    `train_config` TEXT COMMENT '训练配置（JSON格式）',
    `accuracy` DECIMAL(5,4) DEFAULT NULL COMMENT '准确率',
    `precision_rate` DECIMAL(5,4) DEFAULT NULL COMMENT '精确率',
    `recall_rate` DECIMAL(5,4) DEFAULT NULL COMMENT '召回率',
    `f1_score` DECIMAL(5,4) DEFAULT NULL COMMENT 'F1分数',
    `auc_score` DECIMAL(5,4) DEFAULT NULL COMMENT 'AUC分数',
    `train_time` DATETIME DEFAULT NULL COMMENT '训练时间',
    `train_samples` INT DEFAULT NULL COMMENT '训练样本数',
    `is_default` TINYINT DEFAULT 0 COMMENT '是否默认（0否 1是）',
    `is_enabled` TINYINT DEFAULT 1 COMMENT '是否启用（0否 1是）',
    `status` VARCHAR(20) DEFAULT 'draft' COMMENT '状态（draft草稿 training训练中 trained已训练 deployed已部署 deprecated已废弃）',
    `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_model_code` (`model_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='AI模型配置表';

-- ---------------------------------------------
-- AI诊断规则表
-- ---------------------------------------------
DROP TABLE IF EXISTS `ai_diagnosis_rule`;
CREATE TABLE `ai_diagnosis_rule` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '规则ID',
    `rule_code` VARCHAR(50) NOT NULL COMMENT '规则编码',
    `rule_name` VARCHAR(200) NOT NULL COMMENT '规则名称',
    `rule_type` VARCHAR(50) DEFAULT 'single' COMMENT '规则类型（single单项 combined组合 panel组合）',
    `category` VARCHAR(50) DEFAULT NULL COMMENT '规则分类（anemia贫血 infection感染 liver肝脏 kidney肾脏等）',
    `model_id` BIGINT DEFAULT NULL COMMENT '关联模型ID',
    `test_item_ids` VARCHAR(500) DEFAULT NULL COMMENT '关联检验项目ID（多个逗号分隔）',
    `rule_condition` TEXT COMMENT '规则条件（JSON格式）',
    `rule_expr` VARCHAR(1000) DEFAULT NULL COMMENT '规则表达式',
    `diagnosis_template` TEXT COMMENT '诊断模板',
    `suggestion_template` TEXT COMMENT '建议模板',
    `confidence_threshold` DECIMAL(5,4) DEFAULT 0.8000 COMMENT '置信度阈值',
    `priority` INT DEFAULT 0 COMMENT '优先级',
    `is_enabled` TINYINT DEFAULT 1 COMMENT '是否启用（0否 1是）',
    `status` TINYINT DEFAULT 0 COMMENT '状态（0正常 1停用）',
    `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_rule_code` (`rule_code`),
    KEY `idx_model_id` (`model_id`),
    KEY `idx_category` (`category`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='AI诊断规则表';

-- ---------------------------------------------
-- AI诊断规则明细表
-- ---------------------------------------------
DROP TABLE IF EXISTS `ai_diagnosis_rule_item`;
CREATE TABLE `ai_diagnosis_rule_item` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `rule_id` BIGINT NOT NULL COMMENT '规则ID',
    `test_item_id` BIGINT NOT NULL COMMENT '检验项目ID',
    `item_code` VARCHAR(50) DEFAULT NULL COMMENT '项目编码',
    `item_name` VARCHAR(200) DEFAULT NULL COMMENT '项目名称',
    `condition_type` VARCHAR(50) DEFAULT 'range' COMMENT '条件类型（range范围 compare比较 formula公式）',
    `condition_expr` VARCHAR(500) DEFAULT NULL COMMENT '条件表达式',
    `weight` DECIMAL(5,4) DEFAULT 1.0000 COMMENT '权重',
    `sort` INT DEFAULT 0 COMMENT '排序',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_rule_id` (`rule_id`),
    KEY `idx_test_item_id` (`test_item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='AI诊断规则明细表';

-- ---------------------------------------------
-- AI诊断记录表
-- ---------------------------------------------
DROP TABLE IF EXISTS `ai_diagnosis_record`;
CREATE TABLE `ai_diagnosis_record` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '诊断ID',
    `diagnosis_no` VARCHAR(50) NOT NULL COMMENT '诊断编号',
    `report_id` BIGINT DEFAULT NULL COMMENT '报告ID',
    `report_no` VARCHAR(50) DEFAULT NULL COMMENT '报告编号',
    `specimen_id` BIGINT DEFAULT NULL COMMENT '标本ID',
    `specimen_no` VARCHAR(50) DEFAULT NULL COMMENT '标本编号',
    `patient_id` BIGINT DEFAULT NULL COMMENT '患者ID',
    `patient_name` VARCHAR(50) DEFAULT NULL COMMENT '患者姓名',
    `patient_gender` CHAR(1) DEFAULT NULL COMMENT '患者性别',
    `patient_age` VARCHAR(20) DEFAULT NULL COMMENT '患者年龄',
    `model_id` BIGINT DEFAULT NULL COMMENT '使用模型ID',
    `model_name` VARCHAR(200) DEFAULT NULL COMMENT '使用模型名称',
    `rule_id` BIGINT DEFAULT NULL COMMENT '匹配规则ID',
    `rule_name` VARCHAR(200) DEFAULT NULL COMMENT '匹配规则名称',
    `input_data` LONGTEXT COMMENT '输入数据（JSON格式）',
    `diagnosis_result` TEXT COMMENT '诊断结果',
    `diagnosis_code` VARCHAR(50) DEFAULT NULL COMMENT '诊断编码（ICD-10）',
    `diagnosis_name` VARCHAR(200) DEFAULT NULL COMMENT '诊断名称',
    `confidence` DECIMAL(5,4) DEFAULT NULL COMMENT '置信度',
    `probability` DECIMAL(5,4) DEFAULT NULL COMMENT '概率',
    `suggestion` TEXT COMMENT '建议',
    `risk_level` VARCHAR(20) DEFAULT 'low' COMMENT '风险等级（low低 medium中 high高 critical危急）',
    `is_abnormal` TINYINT DEFAULT 0 COMMENT '是否异常（0否 1是）',
    `is_panic` TINYINT DEFAULT 0 COMMENT '是否危急（0否 1是）',
    `diagnosis_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '诊断时间',
    `diagnosis_duration` BIGINT DEFAULT NULL COMMENT '诊断耗时（毫秒）',
    `review_status` TINYINT DEFAULT 0 COMMENT '审核状态（0待审核 1已确认 2已拒绝）',
    `review_user_id` BIGINT DEFAULT NULL COMMENT '审核人ID',
    `review_user_name` VARCHAR(50) DEFAULT NULL COMMENT '审核人姓名',
    `review_time` DATETIME DEFAULT NULL COMMENT '审核时间',
    `review_remark` VARCHAR(500) DEFAULT NULL COMMENT '审核备注',
    `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_diagnosis_no` (`diagnosis_no`),
    KEY `idx_report_id` (`report_id`),
    KEY `idx_specimen_id` (`specimen_id`),
    KEY `idx_patient_id` (`patient_id`),
    KEY `idx_model_id` (`model_id`),
    KEY `idx_diagnosis_time` (`diagnosis_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='AI诊断记录表';

-- ---------------------------------------------
-- AI诊断明细表
-- ---------------------------------------------
DROP TABLE IF EXISTS `ai_diagnosis_detail`;
CREATE TABLE `ai_diagnosis_detail` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `diagnosis_id` BIGINT NOT NULL COMMENT '诊断ID',
    `test_item_id` BIGINT DEFAULT NULL COMMENT '检验项目ID',
    `item_code` VARCHAR(50) DEFAULT NULL COMMENT '项目编码',
    `item_name` VARCHAR(200) DEFAULT NULL COMMENT '项目名称',
    `result_value` VARCHAR(200) DEFAULT NULL COMMENT '结果值',
    `unit` VARCHAR(50) DEFAULT NULL COMMENT '单位',
    `reference_low` DECIMAL(18,4) DEFAULT NULL COMMENT '参考值下限',
    `reference_high` DECIMAL(18,4) DEFAULT NULL COMMENT '参考值上限',
    `is_abnormal` TINYINT DEFAULT 0 COMMENT '是否异常',
    `abnormal_degree` DECIMAL(5,4) DEFAULT NULL COMMENT '异常程度',
    `feature_importance` DECIMAL(5,4) DEFAULT NULL COMMENT '特征重要性',
    `contribution_score` DECIMAL(5,4) DEFAULT NULL COMMENT '贡献分数',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_diagnosis_id` (`diagnosis_id`),
    KEY `idx_test_item_id` (`test_item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='AI诊断明细表';

-- ---------------------------------------------
-- AI诊断知识库表
-- ---------------------------------------------
DROP TABLE IF EXISTS `ai_knowledge_base`;
CREATE TABLE `ai_knowledge_base` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '知识ID',
    `knowledge_code` VARCHAR(50) NOT NULL COMMENT '知识编码',
    `knowledge_name` VARCHAR(200) NOT NULL COMMENT '知识名称',
    `knowledge_type` VARCHAR(50) DEFAULT 'disease' COMMENT '知识类型（disease疾病 symptom症状 test检验 treatment治疗）',
    `category` VARCHAR(50) DEFAULT NULL COMMENT '知识分类',
    `icd_code` VARCHAR(50) DEFAULT NULL COMMENT 'ICD编码',
    `description` TEXT COMMENT '描述',
    `clinical_features` TEXT COMMENT '临床特征（JSON格式）',
    `lab_indicators` TEXT COMMENT '实验室指标（JSON格式）',
    `diagnosis_criteria` TEXT COMMENT '诊断标准（JSON格式）',
    `treatment_suggestion` TEXT COMMENT '治疗建议',
    `reference_links` TEXT COMMENT '参考链接（JSON格式）',
    `source` VARCHAR(100) DEFAULT NULL COMMENT '知识来源',
    `version` VARCHAR(20) DEFAULT NULL COMMENT '版本号',
    `is_enabled` TINYINT DEFAULT 1 COMMENT '是否启用（0否 1是）',
    `status` TINYINT DEFAULT 0 COMMENT '状态（0正常 1停用）',
    `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_knowledge_code` (`knowledge_code`),
    KEY `idx_knowledge_type` (`knowledge_type`),
    KEY `idx_icd_code` (`icd_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='AI诊断知识库表';

-- ---------------------------------------------
-- AI训练数据集表
-- ---------------------------------------------
DROP TABLE IF EXISTS `ai_training_dataset`;
CREATE TABLE `ai_training_dataset` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '数据集ID',
    `dataset_code` VARCHAR(50) NOT NULL COMMENT '数据集编码',
    `dataset_name` VARCHAR(200) NOT NULL COMMENT '数据集名称',
    `dataset_type` VARCHAR(50) DEFAULT 'train' COMMENT '数据集类型（train训练 valid验证 test测试）',
    `total_samples` INT DEFAULT 0 COMMENT '样本总数',
    `positive_samples` INT DEFAULT 0 COMMENT '阳性样本数',
    `negative_samples` INT DEFAULT 0 COMMENT '阴性样本数',
    `feature_count` INT DEFAULT 0 COMMENT '特征数量',
    `data_source` VARCHAR(200) DEFAULT NULL COMMENT '数据来源',
    `data_range_start` DATE DEFAULT NULL COMMENT '数据范围开始',
    `data_range_end` DATE DEFAULT NULL COMMENT '数据范围结束',
    `preprocess_config` TEXT COMMENT '预处理配置（JSON格式）',
    `feature_list` TEXT COMMENT '特征列表（JSON格式）',
    `label_config` TEXT COMMENT '标签配置（JSON格式）',
    `is_balanced` TINYINT DEFAULT 0 COMMENT '是否平衡（0否 1是）',
    `status` VARCHAR(20) DEFAULT 'created' COMMENT '状态（created已创建 preprocessing预处理中 ready就绪 error错误）',
    `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_dataset_code` (`dataset_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='AI训练数据集表';

-- ---------------------------------------------
-- AI训练任务表
-- ---------------------------------------------
DROP TABLE IF EXISTS `ai_training_task`;
CREATE TABLE `ai_training_task` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '任务ID',
    `task_code` VARCHAR(50) NOT NULL COMMENT '任务编码',
    `task_name` VARCHAR(200) NOT NULL COMMENT '任务名称',
    `model_id` BIGINT DEFAULT NULL COMMENT '模型ID',
    `dataset_id` BIGINT DEFAULT NULL COMMENT '数据集ID',
    `algorithm_type` VARCHAR(50) DEFAULT NULL COMMENT '算法类型',
    `train_params` TEXT COMMENT '训练参数（JSON格式）',
    `hyper_params` TEXT COMMENT '超参数（JSON格式）',
    `train_ratio` DECIMAL(5,2) DEFAULT 0.80 COMMENT '训练集比例',
    `valid_ratio` DECIMAL(5,2) DEFAULT 0.10 COMMENT '验证集比例',
    `test_ratio` DECIMAL(5,2) DEFAULT 0.10 COMMENT '测试集比例',
    `start_time` DATETIME DEFAULT NULL COMMENT '开始时间',
    `end_time` DATETIME DEFAULT NULL COMMENT '结束时间',
    `duration` BIGINT DEFAULT NULL COMMENT '耗时（秒）',
    `status` VARCHAR(20) DEFAULT 'pending' COMMENT '状态（pending待执行 running执行中 completed已完成 failed失败 cancelled已取消）',
    `progress` INT DEFAULT 0 COMMENT '进度(%)',
    `accuracy` DECIMAL(5,4) DEFAULT NULL COMMENT '准确率',
    `precision_rate` DECIMAL(5,4) DEFAULT NULL COMMENT '精确率',
    `recall_rate` DECIMAL(5,4) DEFAULT NULL COMMENT '召回率',
    `f1_score` DECIMAL(5,4) DEFAULT NULL COMMENT 'F1分数',
    `auc_score` DECIMAL(5,4) DEFAULT NULL COMMENT 'AUC分数',
    `loss_value` DECIMAL(10,6) DEFAULT NULL COMMENT '损失值',
    `epochs` INT DEFAULT NULL COMMENT '训练轮次',
    `best_epoch` INT DEFAULT NULL COMMENT '最佳轮次',
    `error_msg` VARCHAR(2000) DEFAULT NULL COMMENT '错误信息',
    `result_path` VARCHAR(500) DEFAULT NULL COMMENT '结果文件路径',
    `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_task_code` (`task_code`),
    KEY `idx_model_id` (`model_id`),
    KEY `idx_dataset_id` (`dataset_id`),
    KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='AI训练任务表';

-- ---------------------------------------------
-- AI预测任务表
-- ---------------------------------------------
DROP TABLE IF EXISTS `ai_prediction_task`;
CREATE TABLE `ai_prediction_task` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '任务ID',
    `task_code` VARCHAR(50) NOT NULL COMMENT '任务编码',
    `task_name` VARCHAR(200) DEFAULT NULL COMMENT '任务名称',
    `model_id` BIGINT NOT NULL COMMENT '模型ID',
    `model_name` VARCHAR(200) DEFAULT NULL COMMENT '模型名称',
    `input_data` LONGTEXT COMMENT '输入数据（JSON格式）',
    `output_data` LONGTEXT COMMENT '输出数据（JSON格式）',
    `prediction_type` VARCHAR(50) DEFAULT 'batch' COMMENT '预测类型（single单次 batch批量 realtime实时）',
    `total_count` INT DEFAULT 0 COMMENT '总数',
    `success_count` INT DEFAULT 0 COMMENT '成功数',
    `fail_count` INT DEFAULT 0 COMMENT '失败数',
    `start_time` DATETIME DEFAULT NULL COMMENT '开始时间',
    `end_time` DATETIME DEFAULT NULL COMMENT '结束时间',
    `duration` BIGINT DEFAULT NULL COMMENT '耗时（毫秒）',
    `status` VARCHAR(20) DEFAULT 'pending' COMMENT '状态（pending待执行 running执行中 completed已完成 failed失败）',
    `error_msg` VARCHAR(2000) DEFAULT NULL COMMENT '错误信息',
    `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_task_code` (`task_code`),
    KEY `idx_model_id` (`model_id`),
    KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='AI预测任务表';

-- ---------------------------------------------
-- AI特征工程表
-- ---------------------------------------------
DROP TABLE IF EXISTS `ai_feature_engineering`;
CREATE TABLE `ai_feature_engineering` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '特征ID',
    `feature_code` VARCHAR(50) NOT NULL COMMENT '特征编码',
    `feature_name` VARCHAR(200) NOT NULL COMMENT '特征名称',
    `feature_type` VARCHAR(50) DEFAULT 'numeric' COMMENT '特征类型（numeric数值 category类别 text文本 datetime日期）',
    `source_field` VARCHAR(100) DEFAULT NULL COMMENT '源字段',
    `transform_type` VARCHAR(50) DEFAULT NULL COMMENT '转换类型（normalize归一化 standardize标准化 onehot独热编码 label标签编码等）',
    `transform_expr` VARCHAR(500) DEFAULT NULL COMMENT '转换表达式',
    `default_value` VARCHAR(200) DEFAULT NULL COMMENT '默认值',
    `missing_strategy` VARCHAR(50) DEFAULT 'mean' COMMENT '缺失值处理策略（mean均值 median中位数 mode众数 constant常数）',
    `is_selected` TINYINT DEFAULT 1 COMMENT '是否选中（0否 1是）',
    `importance_score` DECIMAL(5,4) DEFAULT NULL COMMENT '重要性分数',
    `sort` INT DEFAULT 0 COMMENT '排序',
    `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` VARCHAR(64) DEFAULT '' COMMENT '更新者',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_feature_code` (`feature_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='AI特征工程表';

-- ---------------------------------------------
-- AI模型评估记录表
-- ---------------------------------------------
DROP TABLE IF EXISTS `ai_model_evaluation`;
CREATE TABLE `ai_model_evaluation` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '评估ID',
    `model_id` BIGINT NOT NULL COMMENT '模型ID',
    `model_name` VARCHAR(200) DEFAULT NULL COMMENT '模型名称',
    `dataset_id` BIGINT DEFAULT NULL COMMENT '数据集ID',
    `dataset_name` VARCHAR(200) DEFAULT NULL COMMENT '数据集名称',
    `evaluation_type` VARCHAR(50) DEFAULT 'test' COMMENT '评估类型（train训练 valid验证 test测试）',
    `sample_count` INT DEFAULT 0 COMMENT '样本数',
    `accuracy` DECIMAL(5,4) DEFAULT NULL COMMENT '准确率',
    `precision_rate` DECIMAL(5,4) DEFAULT NULL COMMENT '精确率',
    `recall_rate` DECIMAL(5,4) DEFAULT NULL COMMENT '召回率',
    `f1_score` DECIMAL(5,4) DEFAULT NULL COMMENT 'F1分数',
    `auc_score` DECIMAL(5,4) DEFAULT NULL COMMENT 'AUC分数',
    `specificity` DECIMAL(5,4) DEFAULT NULL COMMENT '特异性',
    `sensitivity` DECIMAL(5,4) DEFAULT NULL COMMENT '敏感性',
    `ppv` DECIMAL(5,4) DEFAULT NULL COMMENT '阳性预测值',
    `npv` DECIMAL(5,4) DEFAULT NULL COMMENT '阴性预测值',
    `confusion_matrix` TEXT COMMENT '混淆矩阵（JSON格式）',
    `roc_data` TEXT COMMENT 'ROC曲线数据（JSON格式）',
    `pr_data` TEXT COMMENT 'PR曲线数据（JSON格式）',
    `evaluation_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '评估时间',
    `create_by` VARCHAR(64) DEFAULT '' COMMENT '创建者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    KEY `idx_model_id` (`model_id`),
    KEY `idx_dataset_id` (`dataset_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='AI模型评估记录表';
