-- =============================================
-- LIS系统初始化数据脚本
-- 包含: 管理员账号、角色、菜单、字典数据
-- =============================================

-- =============================================
-- lis_user 数据库初始化数据
-- =============================================
USE `lis_user`;

-- ---------------------------------------------
-- 初始化部门数据
-- ---------------------------------------------
INSERT INTO `sys_dept` (`id`, `parent_id`, `dept_name`, `dept_code`, `leader`, `phone`, `email`, `sort`, `status`, `create_by`, `remark`) VALUES
(1, 0, '检验中心', 'LAB001', '管理员', '010-12345678', 'lab@hospital.com', 0, 0, 'admin', '检验中心'),
(2, 1, '生化室', 'LAB002', '张三', '010-12345679', 'sh@hospital.com', 1, 0, 'admin', '生化检验室'),
(3, 1, '免疫室', 'LAB003', '李四', '010-12345680', 'my@hospital.com', 2, 0, 'admin', '免疫检验室'),
(4, 1, '临检室', 'LAB004', '王五', '010-12345681', 'lj@hospital.com', 3, 0, 'admin', '临检检验室'),
(5, 1, '微生物室', 'LAB005', '赵六', '010-12345682', 'wsw@hospital.com', 4, 0, 'admin', '微生物检验室'),
(6, 1, '分子生物室', 'LAB006', '钱七', '010-12345683', 'fzsw@hospital.com', 5, 0, 'admin', '分子生物检验室'),
(7, 1, '门急诊检验', 'LAB007', '孙八', '010-12345684', 'mzj@hospital.com', 6, 0, 'admin', '门急诊检验室');

-- ---------------------------------------------
-- 初始化用户数据（密码为: admin123 使用BCrypt加密）
-- ---------------------------------------------
INSERT INTO `sys_user` (`id`, `dept_id`, `user_name`, `nick_name`, `user_type`, `email`, `phone`, `gender`, `password`, `status`, `create_by`, `remark`) VALUES
(1, 1, 'admin', '系统管理员', '00', 'admin@hospital.com', '13800138000', '0', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', 0, 'admin', '系统管理员'),
(2, 2, 'zhangsan', '张三', '03', 'zhangsan@hospital.com', '13800138001', '0', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', 0, 'admin', '生化室主任'),
(3, 3, 'lisi', '李四', '03', 'lisi@hospital.com', '13800138002', '1', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', 0, 'admin', '免疫室主任'),
(4, 4, 'wangwu', '王五', '03', 'wangwu@hospital.com', '13800138003', '0', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', 0, 'admin', '临检室主任'),
(5, 7, 'sunba', '孙八', '03', 'sunba@hospital.com', '13800138004', '0', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', 0, 'admin', '门急诊检验师');

-- ---------------------------------------------
-- 初始化角色数据
-- ---------------------------------------------
INSERT INTO `sys_role` (`id`, `role_name`, `role_code`, `role_key`, `role_sort`, `data_scope`, `status`, `create_by`, `remark`) VALUES
(1, '超级管理员', 'SUPER_ADMIN', 'admin', 1, 1, 0, 'admin', '超级管理员角色'),
(2, '检验科主任', 'LAB_DIRECTOR', 'lab_director', 2, 1, 0, 'admin', '检验科主任角色'),
(3, '检验师', 'LAB_TECHNICIAN', 'lab_technician', 3, 3, 0, 'admin', '检验师角色'),
(4, '审核员', 'AUDITOR', 'auditor', 4, 3, 0, 'admin', '报告审核员角色'),
(5, '医生', 'DOCTOR', 'doctor', 5, 5, 0, 'admin', '临床医生角色'),
(6, '护士', 'NURSE', 'nurse', 6, 5, 0, 'admin', '护士角色');

-- ---------------------------------------------
-- 初始化用户角色关联
-- ---------------------------------------------
INSERT INTO `sys_user_role` (`user_id`, `role_id`) VALUES
(1, 1),
(2, 2),
(2, 3),
(3, 2),
(3, 3),
(4, 2),
(4, 3),
(5, 3);

-- ---------------------------------------------
-- 初始化菜单数据
-- ---------------------------------------------
INSERT INTO `sys_menu` (`id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `route_name`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `remark`) VALUES
(1, '系统管理', 0, 1, 'system', NULL, NULL, '', 1, 0, 'M', 0, 0, '', 'system', 'admin', '系统管理目录'),
(2, '用户管理', 1, 1, 'user', 'system/user/index', NULL, '', 1, 0, 'C', 0, 0, 'system:user:list', 'user', 'admin', '用户管理菜单'),
(3, '角色管理', 1, 2, 'role', 'system/role/index', NULL, '', 1, 0, 'C', 0, 0, 'system:role:list', 'peoples', 'admin', '角色管理菜单'),
(4, '菜单管理', 1, 3, 'menu', 'system/menu/index', NULL, '', 1, 0, 'C', 0, 0, 'system:menu:list', 'tree-table', 'admin', '菜单管理菜单'),
(5, '部门管理', 1, 4, 'dept', 'system/dept/index', NULL, '', 1, 0, 'C', 0, 0, 'system:dept:list', 'tree', 'admin', '部门管理菜单'),
(6, '字典管理', 1, 5, 'dict', 'system/dict/index', NULL, '', 1, 0, 'C', 0, 0, 'system:dict:list', 'dict', 'admin', '字典管理菜单'),
(7, '参数设置', 1, 6, 'config', 'system/config/index', NULL, '', 1, 0, 'C', 0, 0, 'system:config:list', 'edit', 'admin', '参数设置菜单'),
(8, '日志管理', 1, 7, 'log', '', NULL, '', 1, 0, 'M', 0, 0, '', 'log', 'admin', '日志管理目录'),
(9, '操作日志', 8, 1, 'operlog', 'monitor/operlog/index', NULL, '', 1, 0, 'C', 0, 0, 'monitor:operlog:list', 'form', 'admin', '操作日志菜单'),
(10, '登录日志', 8, 2, 'logininfor', 'monitor/logininfor/index', NULL, '', 1, 0, 'C', 0, 0, 'monitor:logininfor:list', 'logininfor', 'admin', '登录日志菜单'),
(11, '标本管理', 0, 2, 'specimen', NULL, NULL, '', 1, 0, 'M', 0, 0, '', 'documentation', 'admin', '标本管理目录'),
(12, '标本登记', 11, 1, 'register', 'specimen/register/index', NULL, '', 1, 0, 'C', 0, 0, 'specimen:register:list', 'edit', 'admin', '标本登记菜单'),
(13, '标本接收', 11, 2, 'receive', 'specimen/receive/index', NULL, '', 1, 0, 'C', 0, 0, 'specimen:receive:list', 'checkbox', 'admin', '标本接收菜单'),
(14, '标本查询', 11, 3, 'query', 'specimen/query/index', NULL, '', 1, 0, 'C', 0, 0, 'specimen:query:list', 'search', 'admin', '标本查询菜单'),
(15, '报告管理', 0, 3, 'report', NULL, NULL, '', 1, 0, 'M', 0, 0, '', 'documentation', 'admin', '报告管理目录'),
(16, '报告录入', 15, 1, 'entry', 'report/entry/index', NULL, '', 1, 0, 'C', 0, 0, 'report:entry:list', 'edit', 'admin', '报告录入菜单'),
(17, '报告审核', 15, 2, 'audit', 'report/audit/index', NULL, '', 1, 0, 'C', 0, 0, 'report:audit:list', 'checkbox', 'admin', '报告审核菜单'),
(18, '报告查询', 15, 3, 'query', 'report/query/index', NULL, '', 1, 0, 'C', 0, 0, 'report:query:list', 'search', 'admin', '报告查询菜单'),
(19, '危急值管理', 0, 4, 'panic', NULL, NULL, '', 1, 0, 'M', 0, 0, '', 'message', 'admin', '危急值管理目录'),
(20, '危急值列表', 19, 1, 'list', 'panic/list/index', NULL, '', 1, 0, 'C', 0, 0, 'panic:list:list', 'list', 'admin', '危急值列表菜单'),
(21, '危急值处理', 19, 2, 'handle', 'panic/handle/index', NULL, '', 1, 0, 'C', 0, 0, 'panic:handle:list', 'edit', 'admin', '危急值处理菜单'),
(22, '设备管理', 0, 5, 'equipment', NULL, NULL, '', 1, 0, 'M', 0, 0, '', 'monitor', 'admin', '设备管理目录'),
(23, '设备列表', 22, 1, 'list', 'equipment/list/index', NULL, '', 1, 0, 'C', 0, 0, 'equipment:list:list', 'list', 'admin', '设备列表菜单'),
(24, '设备校准', 22, 2, 'calibration', 'equipment/calibration/index', NULL, '', 1, 0, 'C', 0, 0, 'equipment:calibration:list', 'time-range', 'admin', '设备校准菜单'),
(25, '设备维护', 22, 3, 'maintenance', 'equipment/maintenance/index', NULL, '', 1, 0, 'C', 0, 0, 'equipment:maintenance:list', 'tool', 'admin', '设备维护菜单'),
(26, '质控管理', 22, 4, 'qc', 'equipment/qc/index', NULL, '', 1, 0, 'C', 0, 0, 'equipment:qc:list', 'chart', 'admin', '质控管理菜单'),
(27, '接口管理', 0, 6, 'interface', NULL, NULL, '', 1, 0, 'M', 0, 0, '', 'link', 'admin', '接口管理目录'),
(28, '接口配置', 27, 1, 'config', 'interface/config/index', NULL, '', 1, 0, 'C', 0, 0, 'interface:config:list', 'edit', 'admin', '接口配置菜单'),
(29, '消息监控', 27, 2, 'monitor', 'interface/monitor/index', NULL, '', 1, 0, 'C', 0, 0, 'interface:monitor:list', 'monitor', 'admin', '消息监控菜单'),
(30, '统计分析', 0, 7, 'statistics', NULL, NULL, '', 1, 0, 'M', 0, 0, '', 'chart', 'admin', '统计分析目录'),
(31, '工作量统计', 30, 1, 'workload', 'statistics/workload/index', NULL, '', 1, 0, 'C', 0, 0, 'statistics:workload:list', 'chart', 'admin', '工作量统计菜单'),
(32, 'TAT分析', 30, 2, 'tat', 'statistics/tat/index', NULL, '', 1, 0, 'C', 0, 0, 'statistics:tat:list', 'time-range', 'admin', 'TAT分析菜单'),
(33, '阳性率统计', 30, 3, 'positive', 'statistics/positive/index', NULL, '', 1, 0, 'C', 0, 0, 'statistics:positive:list', 'chart', 'admin', '阳性率统计菜单'),
(34, 'AI诊断', 0, 8, 'ai', NULL, NULL, '', 1, 0, 'M', 0, 0, '', 'skill', 'admin', 'AI诊断目录'),
(35, '诊断规则', 34, 1, 'rule', 'ai/rule/index', NULL, '', 1, 0, 'C', 0, 0, 'ai:rule:list', 'edit', 'admin', '诊断规则菜单'),
(36, '诊断记录', 34, 2, 'record', 'ai/record/index', NULL, '', 1, 0, 'C', 0, 0, 'ai:record:list', 'list', 'admin', '诊断记录菜单'),
(37, '模型管理', 34, 3, 'model', 'ai/model/index', NULL, '', 1, 0, 'C', 0, 0, 'ai:model:list', 'component', 'admin', '模型管理菜单');

-- ---------------------------------------------
-- 初始化角色菜单关联
-- ---------------------------------------------
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`) VALUES
(1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6), (1, 7), (1, 8), (1, 9), (1, 10),
(1, 11), (1, 12), (1, 13), (1, 14), (1, 15), (1, 16), (1, 17), (1, 18), (1, 19), (1, 20),
(1, 21), (1, 22), (1, 23), (1, 24), (1, 25), (1, 26), (1, 27), (1, 28), (1, 29), (1, 30),
(1, 31), (1, 32), (1, 33), (1, 34), (1, 35), (1, 36), (1, 37);

-- ---------------------------------------------
-- 初始化字典类型数据
-- ---------------------------------------------
INSERT INTO `sys_dict_type` (`id`, `dict_name`, `dict_type`, `status`, `create_by`, `remark`) VALUES
(1, '用户性别', 'sys_user_sex', 0, 'admin', '用户性别列表'),
(2, '菜单状态', 'sys_show_hide', 0, 'admin', '菜单状态列表'),
(3, '系统开关', 'sys_normal_disable', 0, 'admin', '系统开关列表'),
(4, '任务状态', 'sys_job_status', 0, 'admin', '任务状态列表'),
(5, '任务分组', 'sys_job_group', 0, 'admin', '任务分组列表'),
(6, '通知类型', 'sys_notice_type', 0, 'admin', '通知类型列表'),
(7, '通知状态', 'sys_notice_status', 0, 'admin', '通知状态列表'),
(8, '操作类型', 'sys_oper_type', 0, 'admin', '操作类型列表'),
(9, '系统是否', 'sys_yes_no', 0, 'admin', '系统是否列表'),
(10, '标本状态', 'specimen_status', 0, 'admin', '标本状态列表'),
(11, '标本类型', 'specimen_type', 0, 'admin', '标本类型列表'),
(12, '报告状态', 'report_status', 0, 'admin', '报告状态列表'),
(13, '危急值状态', 'panic_status', 0, 'admin', '危急值状态列表'),
(14, '设备状态', 'equipment_status', 0, 'admin', '设备状态列表'),
(15, '检验项目分类', 'test_item_category', 0, 'admin', '检验项目分类列表'),
(16, '结果标志', 'result_flag', 0, 'admin', '结果标志列表'),
(17, '用户类型', 'user_type', 0, 'admin', '用户类型列表'),
(18, '就诊类型', 'visit_type', 0, 'admin', '就诊类型列表'),
(19, '证件类型', 'id_type', 0, 'admin', '证件类型列表'),
(20, '接口类型', 'interface_type', 0, 'admin', '接口类型列表'),
(21, '消息方向', 'message_direction', 0, 'admin', '消息方向列表'),
(22, '质控水平', 'qc_level', 0, 'admin', '质控水平列表'),
(23, '质控结果', 'qc_result', 0, 'admin', '质控结果列表'),
(24, '故障级别', 'fault_level', 0, 'admin', '故障级别列表'),
(25, '维护类型', 'maintenance_type', 0, 'admin', '维护类型列表'),
(26, '校准结果', 'calibration_result', 0, 'admin', '校准结果列表'),
(27, '风险等级', 'risk_level', 0, 'admin', '风险等级列表'),
(28, '诊断状态', 'diagnosis_status', 0, 'admin', '诊断状态列表');

-- ---------------------------------------------
-- 初始化字典数据
-- ---------------------------------------------
INSERT INTO `sys_dict_data` (`id`, `dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `remark`) VALUES
(1, 1, '男', '0', 'sys_user_sex', '', '', 'Y', 0, 'admin', '性别男'),
(2, 2, '女', '1', 'sys_user_sex', '', '', 'N', 0, 'admin', '性别女'),
(3, 3, '未知', '2', 'sys_user_sex', '', '', 'N', 0, 'admin', '性别未知'),
(4, 1, '显示', '0', 'sys_show_hide', '', 'primary', 'Y', 0, 'admin', '显示菜单'),
(5, 2, '隐藏', '1', 'sys_show_hide', '', 'danger', 'N', 0, 'admin', '隐藏菜单'),
(6, 1, '正常', '0', 'sys_normal_disable', '', 'primary', 'Y', 0, 'admin', '正常状态'),
(7, 2, '停用', '1', 'sys_normal_disable', '', 'danger', 'N', 0, 'admin', '停用状态'),
(8, 1, '是', 'Y', 'sys_yes_no', '', 'primary', 'Y', 0, 'admin', '系统默认是'),
(9, 2, '否', 'N', 'sys_yes_no', '', 'danger', 'N', 0, 'admin', '系统默认否'),
(10, 1, '待接收', 'pending', 'specimen_status', '', 'info', 'Y', 0, 'admin', '标本待接收'),
(11, 2, '已接收', 'received', 'specimen_status', '', 'primary', 'N', 0, 'admin', '标本已接收'),
(12, 3, '检验中', 'testing', 'specimen_status', '', 'warning', 'N', 0, 'admin', '标本检验中'),
(13, 4, '已完成', 'completed', 'specimen_status', '', 'success', 'N', 0, 'admin', '标本已完成'),
(14, 5, '已拒收', 'rejected', 'specimen_status', '', 'danger', 'N', 0, 'admin', '标本已拒收'),
(15, 1, '血液', 'blood', 'specimen_type', '', 'danger', 'Y', 0, 'admin', '血液标本'),
(16, 2, '尿液', 'urine', 'specimen_type', '', 'warning', 'N', 0, 'admin', '尿液标本'),
(17, 3, '粪便', 'stool', 'specimen_type', '', 'info', 'N', 0, 'admin', '粪便标本'),
(18, 4, '痰液', 'sputum', 'specimen_type', '', 'primary', 'N', 0, 'admin', '痰液标本'),
(19, 5, '脑脊液', 'csf', 'specimen_type', '', 'success', 'N', 0, 'admin', '脑脊液标本'),
(20, 6, '胸腹水', 'pleural', 'specimen_type', '', 'warning', 'N', 0, 'admin', '胸腹水标本'),
(21, 1, '草稿', 'draft', 'report_status', '', 'info', 'Y', 0, 'admin', '报告草稿'),
(22, 2, '已审核', 'audited', 'report_status', '', 'primary', 'N', 0, 'admin', '报告已审核'),
(23, 3, '已报告', 'reported', 'report_status', '', 'success', 'N', 0, 'admin', '报告已发布'),
(24, 4, '已打印', 'printed', 'report_status', '', 'warning', 'N', 0, 'admin', '报告已打印'),
(25, 5, '已取消', 'cancelled', 'report_status', '', 'danger', 'N', 0, 'admin', '报告已取消'),
(26, 1, '待处理', '0', 'panic_status', '', 'danger', 'Y', 0, 'admin', '危急值待处理'),
(27, 2, '已通知', '1', 'panic_status', '', 'warning', 'N', 0, 'admin', '危急值已通知'),
(28, 3, '已处理', '2', 'panic_status', '', 'primary', 'N', 0, 'admin', '危急值已处理'),
(29, 4, '已确认', '3', 'panic_status', '', 'success', 'N', 0, 'admin', '危急值已确认'),
(30, 1, '正常', 'normal', 'equipment_status', '', 'success', 'Y', 0, 'admin', '设备正常'),
(31, 2, '维护中', 'maintenance', 'equipment_status', '', 'warning', 'N', 0, 'admin', '设备维护中'),
(32, 3, '维修中', 'repair', 'equipment_status', '', 'danger', 'N', 0, 'admin', '设备维修中'),
(33, 4, '报废', 'scrap', 'equipment_status', '', 'info', 'N', 0, 'admin', '设备已报废'),
(34, 5, '校准中', 'calibration', 'equipment_status', '', 'primary', 'N', 0, 'admin', '设备校准中'),
(35, 1, '生化检验', 'biochemistry', 'test_item_category', '', 'primary', 'Y', 0, 'admin', '生化检验项目'),
(36, 2, '免疫检验', 'immunity', 'test_item_category', '', 'success', 'N', 0, 'admin', '免疫检验项目'),
(37, 3, '临检', 'clinical', 'test_item_category', '', 'warning', 'N', 0, 'admin', '临检项目'),
(38, 4, '微生物', 'microbiology', 'test_item_category', '', 'info', 'N', 0, 'admin', '微生物项目'),
(39, 5, '分子生物', 'molecular', 'test_item_category', '', 'danger', 'N', 0, 'admin', '分子生物项目'),
(40, 1, '正常', 'N', 'result_flag', '', 'success', 'Y', 0, 'admin', '结果正常'),
(41, 2, '偏高', 'H', 'result_flag', '', 'warning', 'N', 0, 'admin', '结果偏高'),
(42, 3, '偏低', 'L', 'result_flag', '', 'info', 'N', 0, 'admin', '结果偏低'),
(43, 4, '极高', 'HH', 'result_flag', '', 'danger', 'N', 0, 'admin', '结果极高'),
(44, 5, '极低', 'LL', 'result_flag', '', 'danger', 'N', 0, 'admin', '结果极低'),
(45, 1, '系统用户', '00', 'user_type', '', 'primary', 'Y', 0, 'admin', '系统用户'),
(46, 2, '医生', '01', 'user_type', '', 'success', 'N', 0, 'admin', '医生用户'),
(47, 3, '护士', '02', 'user_type', '', 'warning', 'N', 0, 'admin', '护士用户'),
(48, 4, '检验师', '03', 'user_type', '', 'info', 'N', 0, 'admin', '检验师用户'),
(49, 1, '门诊', 'outpatient', 'visit_type', '', 'primary', 'Y', 0, 'admin', '门诊就诊'),
(50, 2, '住院', 'inpatient', 'visit_type', '', 'success', 'N', 0, 'admin', '住院就诊'),
(51, 3, '急诊', 'emergency', 'visit_type', '', 'danger', 'N', 0, 'admin', '急诊就诊'),
(52, 4, '体检', 'physical', 'visit_type', '', 'info', 'N', 0, 'admin', '体检'),
(53, 1, '身份证', '01', 'id_type', '', 'primary', 'Y', 0, 'admin', '身份证'),
(54, 2, '护照', '02', 'id_type', '', 'success', 'N', 0, 'admin', '护照'),
(55, 3, '军官证', '03', 'id_type', '', 'warning', 'N', 0, 'admin', '军官证'),
(56, 4, '其他', '04', 'id_type', '', 'info', 'N', 0, 'admin', '其他证件'),
(57, 1, 'HL7', 'hl7', 'interface_type', '', 'primary', 'Y', 0, 'admin', 'HL7接口'),
(58, 2, 'ASTM', 'astm', 'interface_type', '', 'success', 'N', 0, 'admin', 'ASTM接口'),
(59, 3, 'XML', 'xml', 'interface_type', '', 'warning', 'N', 0, 'admin', 'XML接口'),
(60, 4, 'JSON', 'json', 'interface_type', '', 'info', 'N', 0, 'admin', 'JSON接口'),
(61, 5, 'WebSocket', 'websocket', 'interface_type', '', 'danger', 'N', 0, 'admin', 'WebSocket接口'),
(62, 1, '入站', 'inbound', 'message_direction', '', 'primary', 'Y', 0, 'admin', '入站消息'),
(63, 2, '出站', 'outbound', 'message_direction', '', 'success', 'N', 0, 'admin', '出站消息'),
(64, 3, '双向', 'bidirectional', 'message_direction', '', 'warning', 'N', 0, 'admin', '双向消息'),
(65, 1, '水平1', 'level1', 'qc_level', '', 'primary', 'Y', 0, 'admin', '质控水平1'),
(66, 2, '水平2', 'level2', 'qc_level', '', 'success', 'N', 0, 'admin', '质控水平2'),
(67, 3, '水平3', 'level3', 'qc_level', '', 'warning', 'N', 0, 'admin', '质控水平3'),
(68, 1, '通过', 'pass', 'qc_result', '', 'success', 'Y', 0, 'admin', '质控通过'),
(69, 2, '失败', 'fail', 'qc_result', '', 'danger', 'N', 0, 'admin', '质控失败'),
(70, 3, '警告', 'warning', 'qc_result', '', 'warning', 'N', 0, 'admin', '质控警告'),
(71, 1, '轻微', 'minor', 'fault_level', '', 'info', 'Y', 0, 'admin', '轻微故障'),
(72, 2, '中等', 'moderate', 'fault_level', '', 'warning', 'N', 0, 'admin', '中等故障'),
(73, 3, '严重', 'major', 'fault_level', '', 'danger', 'N', 0, 'admin', '严重故障'),
(74, 4, '危急', 'critical', 'fault_level', '', 'danger', 'N', 0, 'admin', '危急故障'),
(75, 1, '日常维护', 'routine', 'maintenance_type', '', 'primary', 'Y', 0, 'admin', '日常维护'),
(76, 2, '周期维护', 'periodic', 'maintenance_type', '', 'success', 'N', 0, 'admin', '周期维护'),
(77, 3, '预防性维护', 'preventive', 'maintenance_type', '', 'warning', 'N', 0, 'admin', '预防性维护'),
(78, 1, '合格', 'qualified', 'calibration_result', '', 'success', 'Y', 0, 'admin', '校准合格'),
(79, 2, '不合格', 'unqualified', 'calibration_result', '', 'danger', 'N', 0, 'admin', '校准不合格'),
(80, 1, '低', 'low', 'risk_level', '', 'success', 'Y', 0, 'admin', '低风险'),
(81, 2, '中', 'medium', 'risk_level', '', 'warning', 'N', 0, 'admin', '中风险'),
(82, 3, '高', 'high', 'risk_level', '', 'danger', 'N', 0, 'admin', '高风险'),
(83, 4, '危急', 'critical', 'risk_level', '', 'danger', 'N', 0, 'admin', '危急风险'),
(84, 1, '待审核', '0', 'diagnosis_status', '', 'info', 'Y', 0, 'admin', '待审核'),
(85, 2, '已确认', '1', 'diagnosis_status', '', 'success', 'N', 0, 'admin', '已确认'),
(86, 3, '已拒绝', '2', 'diagnosis_status', '', 'danger', 'N', 0, 'admin', '已拒绝');

-- =============================================
-- lis_specimen 数据库初始化数据
-- =============================================
USE `lis_specimen`;

-- ---------------------------------------------
-- 初始化标本类型数据
-- ---------------------------------------------
INSERT INTO `specimen_type` (`id`, `type_code`, `type_name`, `type_desc`, `color_code`, `container_type`, `storage_condition`, `validity_period`, `status`, `sort`, `create_by`) VALUES
(1, 'BLOOD', '血液', '全血标本', 'red', '真空采血管', '室温', 24, 0, 1, 'admin'),
(2, 'SERUM', '血清', '血清标本', 'yellow', '促凝管', '室温', 8, 0, 2, 'admin'),
(3, 'PLASMA', '血浆', '血浆标本', 'green', '肝素管', '室温', 8, 0, 3, 'admin'),
(4, 'URINE', '尿液', '尿液标本', 'yellow', '尿杯', '室温', 2, 0, 4, 'admin'),
(5, 'STOOL', '粪便', '粪便标本', 'brown', '便盒', '室温', 4, 0, 5, 'admin'),
(6, 'SPUTUM', '痰液', '痰液标本', 'white', '痰杯', '室温', 2, 0, 6, 'admin'),
(7, 'CSF', '脑脊液', '脑脊液标本', 'blue', '无菌管', '室温', 1, 0, 7, 'admin'),
(8, 'PLEURAL', '胸腹水', '胸腹水标本', 'purple', '无菌管', '室温', 2, 0, 8, 'admin');

-- ---------------------------------------------
-- 初始化检验项目分类数据
-- ---------------------------------------------
INSERT INTO `test_item_category` (`id`, `parent_id`, `category_code`, `category_name`, `category_desc`, `status`, `sort`, `create_by`) VALUES
(1, 0, 'BIOCHEMISTRY', '生化检验', '生化检验项目分类', 0, 1, 'admin'),
(2, 0, 'IMMUNITY', '免疫检验', '免疫检验项目分类', 0, 2, 'admin'),
(3, 0, 'CLINICAL', '临检', '临检项目分类', 0, 3, 'admin'),
(4, 0, 'MICROBIOLOGY', '微生物', '微生物项目分类', 0, 4, 'admin'),
(5, 0, 'MOLECULAR', '分子生物', '分子生物项目分类', 0, 5, 'admin'),
(6, 1, 'LIVER', '肝功能', '肝功能检查', 0, 1, 'admin'),
(7, 1, 'KIDNEY', '肾功能', '肾功能检查', 0, 2, 'admin'),
(8, 1, 'LIPID', '血脂', '血脂检查', 0, 3, 'admin'),
(9, 1, 'GLUCOSE', '血糖', '血糖检查', 0, 4, 'admin'),
(10, 2, 'TUMOR', '肿瘤标志物', '肿瘤标志物检查', 0, 1, 'admin'),
(11, 2, 'THYROID', '甲状腺功能', '甲状腺功能检查', 0, 2, 'admin'),
(12, 2, 'HEPATITIS', '肝炎标志物', '肝炎标志物检查', 0, 3, 'admin'),
(13, 3, 'BLOOD_ROUTINE', '血常规', '血常规检查', 0, 1, 'admin'),
(14, 3, 'URINE_ROUTINE', '尿常规', '尿常规检查', 0, 2, 'admin'),
(15, 3, 'COAGULATION', '凝血功能', '凝血功能检查', 0, 3, 'admin');

-- ---------------------------------------------
-- 初始化检验项目数据
-- ---------------------------------------------
INSERT INTO `test_item` (`id`, `item_code`, `item_name`, `item_name_en`, `item_short`, `category_id`, `specimen_type_id`, `method`, `unit`, `reference_low`, `reference_high`, `panic_low`, `panic_high`, `price`, `tat`, `is_print`, `is_stat`, `status`, `sort`, `create_by`) VALUES
(1, 'ALT', '丙氨酸氨基转移酶', 'Alanine Aminotransferase', 'ALT', 6, 2, '酶法', 'U/L', 0, 40, NULL, 400, 10.00, 60, 1, 1, 0, 1, 'admin'),
(2, 'AST', '天门冬氨酸氨基转移酶', 'Aspartate Aminotransferase', 'AST', 6, 2, '酶法', 'U/L', 0, 40, NULL, 400, 10.00, 60, 1, 1, 0, 2, 'admin'),
(3, 'TBIL', '总胆红素', 'Total Bilirubin', 'TBIL', 6, 2, '氧化法', 'umol/L', 3.4, 17.1, NULL, 340, 10.00, 60, 1, 1, 0, 3, 'admin'),
(4, 'DBIL', '直接胆红素', 'Direct Bilirubin', 'DBIL', 6, 2, '氧化法', 'umol/L', 0, 6.8, NULL, 170, 10.00, 60, 1, 1, 0, 4, 'admin'),
(5, 'ALB', '白蛋白', 'Albumin', 'ALB', 6, 2, 'BCG法', 'g/L', 35, 55, 20, NULL, 10.00, 60, 1, 1, 0, 5, 'admin'),
(6, 'TP', '总蛋白', 'Total Protein', 'TP', 6, 2, '双缩脲法', 'g/L', 65, 85, 40, NULL, 10.00, 60, 1, 1, 0, 6, 'admin'),
(7, 'GGT', 'γ-谷氨酰转移酶', 'Gamma-Glutamyl Transferase', 'GGT', 6, 2, '酶法', 'U/L', 0, 50, NULL, 500, 10.00, 60, 1, 1, 0, 7, 'admin'),
(8, 'ALP', '碱性磷酸酶', 'Alkaline Phosphatase', 'ALP', 6, 2, '酶法', 'U/L', 45, 125, NULL, 500, 10.00, 60, 1, 1, 0, 8, 'admin'),
(9, 'BUN', '尿素氮', 'Blood Urea Nitrogen', 'BUN', 7, 2, '酶法', 'mmol/L', 2.9, 8.2, 1.0, 30, 10.00, 60, 1, 1, 0, 1, 'admin'),
(10, 'CREA', '肌酐', 'Creatinine', 'CREA', 7, 2, '酶法', 'umol/L', 44, 133, 20, 800, 10.00, 60, 1, 1, 0, 2, 'admin'),
(11, 'UA', '尿酸', 'Uric Acid', 'UA', 7, 2, '酶法', 'umol/L', 150, 416, 100, 800, 10.00, 60, 1, 1, 0, 3, 'admin'),
(12, 'GLU', '葡萄糖', 'Glucose', 'GLU', 9, 2, '氧化酶法', 'mmol/L', 3.9, 6.1, 2.2, 28, 10.00, 60, 1, 1, 0, 1, 'admin'),
(13, 'TC', '总胆固醇', 'Total Cholesterol', 'TC', 8, 2, '酶法', 'mmol/L', 3.0, 5.7, NULL, 10, 10.00, 60, 1, 1, 0, 1, 'admin'),
(14, 'TG', '甘油三酯', 'Triglycerides', 'TG', 8, 2, '酶法', 'mmol/L', 0.4, 1.8, NULL, 5, 10.00, 60, 1, 1, 0, 2, 'admin'),
(15, 'HDL', '高密度脂蛋白胆固醇', 'HDL Cholesterol', 'HDL-C', 8, 2, '酶法', 'mmol/L', 1.0, 1.9, NULL, NULL, 15.00, 60, 1, 1, 0, 3, 'admin'),
(16, 'LDL', '低密度脂蛋白胆固醇', 'LDL Cholesterol', 'LDL-C', 8, 2, '酶法', 'mmol/L', 0, 3.4, NULL, 5, 15.00, 60, 1, 1, 0, 4, 'admin'),
(17, 'AFP', '甲胎蛋白', 'Alpha Fetoprotein', 'AFP', 10, 2, '化学发光', 'ng/mL', 0, 20, NULL, 500, 50.00, 120, 1, 1, 0, 1, 'admin'),
(18, 'CEA', '癌胚抗原', 'Carcinoembryonic Antigen', 'CEA', 10, 2, '化学发光', 'ng/mL', 0, 5, NULL, 100, 50.00, 120, 1, 1, 0, 2, 'admin'),
(19, 'CA125', '糖类抗原125', 'Cancer Antigen 125', 'CA125', 10, 2, '化学发光', 'U/mL', 0, 35, NULL, 500, 60.00, 120, 1, 1, 0, 3, 'admin'),
(20, 'CA199', '糖类抗原199', 'Cancer Antigen 199', 'CA199', 10, 2, '化学发光', 'U/mL', 0, 37, NULL, 500, 60.00, 120, 1, 1, 0, 4, 'admin'),
(21, 'TSH', '促甲状腺激素', 'Thyroid Stimulating Hormone', 'TSH', 11, 2, '化学发光', 'mIU/L', 0.27, 4.2, 0.01, 100, 40.00, 120, 1, 1, 0, 1, 'admin'),
(22, 'FT3', '游离三碘甲状腺原氨酸', 'Free T3', 'FT3', 11, 2, '化学发光', 'pmol/L', 3.1, 6.8, NULL, 30, 40.00, 120, 1, 1, 0, 2, 'admin'),
(23, 'FT4', '游离甲状腺素', 'Free T4', 'FT4', 11, 2, '化学发光', 'pmol/L', 12, 22, NULL, 100, 40.00, 120, 1, 1, 0, 3, 'admin'),
(24, 'HBsAg', '乙肝表面抗原', 'Hepatitis B Surface Antigen', 'HBsAg', 12, 2, '化学发光', 'COI', 0, 1, NULL, NULL, 30.00, 120, 1, 1, 0, 1, 'admin'),
(25, 'HBsAb', '乙肝表面抗体', 'Hepatitis B Surface Antibody', 'HBsAb', 12, 2, '化学发光', 'mIU/mL', 0, 10, NULL, NULL, 30.00, 120, 1, 1, 0, 2, 'admin'),
(26, 'HBeAg', '乙肝e抗原', 'Hepatitis B e Antigen', 'HBeAg', 12, 2, '化学发光', 'COI', 0, 1, NULL, NULL, 30.00, 120, 1, 1, 0, 3, 'admin'),
(27, 'HBeAb', '乙肝e抗体', 'Hepatitis B e Antibody', 'HBeAb', 12, 2, '化学发光', 'COI', 0, 1, NULL, NULL, 30.00, 120, 1, 1, 0, 4, 'admin'),
(28, 'HBcAb', '乙肝核心抗体', 'Hepatitis B Core Antibody', 'HBcAb', 12, 2, '化学发光', 'COI', 0, 1, NULL, NULL, 30.00, 120, 1, 1, 0, 5, 'admin'),
(29, 'WBC', '白细胞计数', 'White Blood Cell Count', 'WBC', 13, 1, '电阻抗法', '10^9/L', 4, 10, 1, 30, 15.00, 30, 1, 1, 0, 1, 'admin'),
(30, 'RBC', '红细胞计数', 'Red Blood Cell Count', 'RBC', 13, 1, '电阻抗法', '10^12/L', 4, 5.5, 1.5, 8, 15.00, 30, 1, 1, 0, 2, 'admin'),
(31, 'HGB', '血红蛋白', 'Hemoglobin', 'HGB', 13, 1, '比色法', 'g/L', 120, 160, 50, 250, 15.00, 30, 1, 1, 0, 3, 'admin'),
(32, 'PLT', '血小板计数', 'Platelet Count', 'PLT', 13, 1, '电阻抗法', '10^9/L', 100, 300, 20, 800, 15.00, 30, 1, 1, 0, 4, 'admin'),
(33, 'HCT', '红细胞压积', 'Hematocrit', 'HCT', 13, 1, '计算法', '%', 37, 50, 15, 70, 15.00, 30, 1, 1, 0, 5, 'admin'),
(34, 'PT', '凝血酶原时间', 'Prothrombin Time', 'PT', 15, 3, '凝固法', '秒', 11, 14, NULL, 30, 30.00, 60, 1, 1, 0, 1, 'admin'),
(35, 'APTT', '活化部分凝血活酶时间', 'Activated Partial Thromboplastin Time', 'APTT', 15, 3, '凝固法', '秒', 25, 37, NULL, 100, 30.00, 60, 1, 1, 0, 2, 'admin'),
(36, 'TT', '凝血酶时间', 'Thrombin Time', 'TT', 15, 3, '凝固法', '秒', 14, 21, NULL, 60, 30.00, 60, 1, 1, 0, 3, 'admin'),
(37, 'FIB', '纤维蛋白原', 'Fibrinogen', 'FIB', 15, 3, 'Clauss法', 'g/L', 2, 4, 0.5, 10, 30.00, 60, 1, 1, 0, 4, 'admin'),
(38, 'K', '钾', 'Potassium', 'K+', 1, 2, '离子选择电极法', 'mmol/L', 3.5, 5.3, 2.5, 7, 10.00, 60, 1, 1, 0, 10, 'admin'),
(39, 'NA', '钠', 'Sodium', 'Na+', 1, 2, '离子选择电极法', 'mmol/L', 136, 145, 120, 160, 10.00, 60, 1, 1, 0, 11, 'admin'),
(40, 'CL', '氯', 'Chloride', 'Cl-', 1, 2, '离子选择电极法', 'mmol/L', 96, 108, 80, 125, 10.00, 60, 1, 1, 0, 12, 'admin');

-- =============================================
-- lis_equipment 数据库初始化数据
-- =============================================
USE `lis_equipment`;

-- ---------------------------------------------
-- 初始化设备类型数据
-- ---------------------------------------------
INSERT INTO `equipment_type` (`id`, `type_code`, `type_name`, `type_desc`, `parent_id`, `status`, `sort`, `create_by`) VALUES
(1, 'BIOCHEMISTRY', '生化分析仪', '生化检验设备', 0, 0, 1, 'admin'),
(2, 'IMMUNOLOGY', '免疫分析仪', '免疫检验设备', 0, 0, 2, 'admin'),
(3, 'HEMATOLOGY', '血液分析仪', '血液检验设备', 0, 0, 3, 'admin'),
(4, 'COAGULATION', '凝血分析仪', '凝血检验设备', 0, 0, 4, 'admin'),
(5, 'URINALYSIS', '尿液分析仪', '尿液检验设备', 0, 0, 5, 'admin'),
(6, 'MICROBIOLOGY', '微生物分析仪', '微生物检验设备', 0, 0, 6, 'admin');

-- ---------------------------------------------
-- 初始化设备数据
-- ---------------------------------------------
INSERT INTO `equipment` (`id`, `equipment_code`, `equipment_name`, `equipment_name_en`, `equipment_type_id`, `equipment_type_name`, `brand`, `model`, `serial_no`, `manufacturer`, `supplier`, `purchase_date`, `install_date`, `warranty_expire_date`, `use_life`, `original_value`, `location`, `status`, `is_online`, `ip_address`, `port`, `communication_protocol`, `create_by`) VALUES
(1, 'EQ001', '全自动生化分析仪1号', 'Automatic Biochemistry Analyzer', 1, '生化分析仪', 'Roche', 'Cobas 8000', 'SN20230001', 'Roche Diagnostics', '罗氏诊断', '2023-01-01', '2023-02-01', '2026-02-01', 10, 2000000.00, '生化室', 'normal', 1, '192.168.1.101', 5000, 'HL7', 'admin'),
(2, 'EQ002', '全自动生化分析仪2号', 'Automatic Biochemistry Analyzer', 1, '生化分析仪', 'Beckman', 'AU5800', 'SN20230002', 'Beckman Coulter', '贝克曼库尔特', '2023-01-01', '2023-02-01', '2026-02-01', 10, 1800000.00, '生化室', 'normal', 1, '192.168.1.102', 5000, 'HL7', 'admin'),
(3, 'EQ003', '全自动免疫分析仪1号', 'Automatic Immunoassay Analyzer', 2, '免疫分析仪', 'Roche', 'e801', 'SN20230003', 'Roche Diagnostics', '罗氏诊断', '2023-01-01', '2023-02-01', '2026-02-01', 10, 2500000.00, '免疫室', 'normal', 1, '192.168.1.103', 5000, 'HL7', 'admin'),
(4, 'EQ004', '全自动血液分析仪', 'Automatic Hematology Analyzer', 3, '血液分析仪', 'Sysmex', 'XN-1000', 'SN20230004', 'Sysmex Corporation', '希森美康', '2023-01-01', '2023-02-01', '2026-02-01', 10, 800000.00, '临检室', 'normal', 1, '192.168.1.104', 5000, 'HL7', 'admin'),
(5, 'EQ005', '全自动凝血分析仪', 'Automatic Coagulation Analyzer', 4, '凝血分析仪', 'Werfen', 'ACL TOP 750', 'SN20230005', 'Werfen', '沃芬', '2023-01-01', '2023-02-01', '2026-02-01', 10, 600000.00, '临检室', 'normal', 1, '192.168.1.105', 5000, 'HL7', 'admin'),
(6, 'EQ006', '全自动尿液分析仪', 'Automatic Urinalysis Analyzer', 5, '尿液分析仪', 'Sysmex', 'UF-5000', 'SN20230006', 'Sysmex Corporation', '希森美康', '2023-01-01', '2023-02-01', '2026-02-01', 10, 400000.00, '临检室', 'normal', 1, '192.168.1.106', 5000, 'HL7', 'admin');
