文档修订历史

| 版本号 | 修订类别 | 简要说明 | 日期 | 变更人 |
| --- | --- | --- | --- | --- |
| 1.0 | C | 创建 | 2020-03-30 | 史雪东 |
| 1.1 | A | 增加 | 2020-04-23 | 史雪东 |
| 1.2 | M | 修改 | 2020-04-24 | 史雪东 |
| 1.3 | A | 增加 | 2020-05-28 | 史雪东 |
| 1.4 | M | 修改 | 2020-05-29 | 史雪东 |
| 1.5 | A | 增加 | 2020-06-18 | 史雪东 |
| 1.6 | M | 修改 | 2020-06-19 | 史雪东 |
| 2.0 | M | 修改 | 2020-07-03 | 史雪东 |



修订类别：C = 创建，A = 增加，M = 修改，D = 删除







目录



1. 文件描述	5

2. 模块详细设计	5

2.1. 公共及框架模块设计	5

2.1.1前后端交互	5

2.1.1. 公共接口	6

2.1.2. 业务相关	27

2.1.3. 智能提醒接口	39

2.2. 检验项目	44

2.2.1. V 模块接口视图	44

2.2.2. A 初始化数据	45

2.2.3. B 业务类	49

2.3. 仪器配置	79

2.3.1. V 模块接口视图	79

2.3.2. A 对外公布方法	80

2.3.3. B 业务类	85

2.3.4. 仪器基础信息维护	113

2.3.5. 仪器项目危急值维护	142

2.3.6. 仪器项目传染病维护	155

2.3.7. 仪器项目计算公式维护	167

2.3.8. 仪器项目转义结果维护	178

2.3.9. 仪器项目异常值设置和结果建议与解释维护	186

2.3.10. 仪器项目组合输入维护	197

2.3.11. 仪器属性维护	220

2.3.12. 仪器配置审核条件	233

2.3.13. 收费项目条码项目、急诊、停用状态设置	253

2.3.14. 收费项目条码项目、急诊、停用状态设置	253

2.4. 收费项目	285

2.4.1. V 模块接口视图	285

2.4.2. 收费项目条码项目、急诊、停用状态设置	285

2.4.3. 收费项目条码项目、急诊、停用状态设置	285

2.4.4. A 对外公布方法	285

2.4.5. B 业务类	294

2.4.6. 条码分组项目维护	317

2.4.7. 回执单收费项目维护	365

2.4.8. 添加和修改回执单规则及明细	365

2.4.9. TAT规则收费项目维护	388

2.4.10. 收费项目检验指标维护	415

2.4.11. 收费项目对应材料费维护	430

2.4.12. 收费项目对应标本种类维护	450

2.4.13. 收费项目对应外送机构维护	461

2.5. 辅助字典	475

2.5.1. V 模块接口视图	475

2.5.2. A 对外公布方法	475

2.5.3. B 业务类	480

2.6. 报告管理	496

2.6.1. 模块接口视图	496

2.6.2. 初始化数据	497

2.6.3. 业务类	499

2.6.4. 患者登记	515

2.6.5. 报告审核	539

2.6.6. 报告删除	543

2.6.7. 危急值处理	562

2.6.8. 报告复做	577

2.6.9. 报告详情	598

2.6.10. 批量打印	627

2.7. 标本签收	636

2.7.1. 模块接口视图	636

2.7.2. 模块规范	637

2.7.3. 请求初始化数据	637

2.7.4. 业务类	640

2.8. 标本入库	659

2.8.1. 模块接口视图	659

2.8.2. 初始化数据	660

2.8.3. 业务类	664

2.9. 报告查询	686

2.9.1. V 模块接口视图	686

2.9.2. A 初始化数据	686

2.9.3. B 业务类	689

2.10. 查询统计	703

2.10.1. 模块接口视图	703

2.10.2. A01	画面初始化	703

2.11. 门诊采集	704

2.11.1. 模块接口视图	704

2.11.2. 初始化数据	706

2.11.3. 业务类	710

2.12. 标本查询	732

2.12.1. V 模块接口视图	732

2.12.2. A 初始化数据	732

2.12.3. B 业务类	737

2.13. 住院采集	748

2.13.1. V 模块接口视图	748

2.13.2. A 初始化数据	749

2.13.3. B 业务类	753

2.14. 采样时间更新	771

2.14.1. V 模块接口视图	771

2.14.2. A 初始化数据	772

2.14.3. B 业务类	777

2.15. 条码绑定接口	789

2.16. 外部接口模块设计	811

2.2.1 前后端交互	811

2.16.1. 患者360	812











# 文件描述



| 项目经理 | 周洪涛 | 周洪涛 | 周洪涛 | 系统设计 | 曹志强等 | 曹志强等 | 曹志强等 |
| --- | --- | --- | --- | --- | --- | --- | --- |
| 审核者 | 周洪涛 | 日期 | 2020-03-30 | 批准者 | 专家小组 | 日期 | 2020-03-31 |







# 模块详细设计

## 公共及框架模块设计

### 2.1.1前后端交互

#### 2.1.1.1请求数据返回 ResposeMessage

接口说明：前端与服务端交互请求，后端返回数据格式结构统一为JSON格式，基本格式如下，对应描述请看“响应参数列表”，在前端获取到响应数据后，首先要判断type是否为“SUCCESS”，是表示成功，否则按错误\失败逻辑处理 { "data":"", "failtCode":"", "link":"", "message":"获取成功", "messageCode":"", "type":"SUCCESS" }，后端返回前端的数据类型均为ResposeMessage。

数据结构

| NO. | 变量名 | 含义 | 类型 | 说明 |
| --- | --- | --- | --- | --- |
| 1 | type | 消息类型 | string | SUCCESS-成功 
ERROR-系统出现异常或错误
FAILT-业务逻辑处理失败 |
| 2 | messageCode | 消息代码 | string | 用于区分消息代码 |
| 3 | message | 消息内容 | string | type=ERROR时则为系统错误消息提示内容
type=FAILT时则为业务提示信息 |
| 4 | link | URL链接 | string | 用于提示用户后可以点击查看详情信息，默认为空 |
| 5 | data | 数据集合 | object | 用于存储返回的数据 |



messageCode定义与规范





### 公共接口

#### 字典视图

| NO. | 字典分类 | 系统类 | 可见 | 字典分组 | 说明 |
| --- | --- | --- | --- | --- | --- |
| 1 | 报告备注 | N | Y | 常规字典 | 病人报告字典信息；如：血清不足 |
| 2 | 报告回收原因 | N | Y | 常规字典 | 病人报告回收字典信息；如：发布错误，审核不通过 |
| 3 | 备注 | N | Y | 常规字典 | 备注字典信息；如：测试 |
| 4 | 备注信息 | N | Y | 常规字典 | 备注信息字典信息；如：请到辅助库中维护 |
| 5 | 标本说明 | N | Y | 常规字典 | 标本说明字典信息；如：严重黄胆 |
| 6 | 病人类型 | N | Y | 常规字典 | 病人类型字典信息；如：门诊、住院 |
| 7 | 病人特征 | N | Y | 常规字典 | 病人特征字典信息；如：接触过苯，太肥 |
| 8 | 测定方法 | N | Y | 常规字典 | 测定方法字典信息；如：目测 |
| 9 | 付费类别 | N | Y | 常规字典 | 付费类别字典信息；如：本院离休，本院退休，部分事业医保，高干退休急观.... |
| 10 | 机构级别 | N | Y | 常规字典 | 机构级别字典信息；如：省市级，县级，乡镇级 |
| 11 | 机构类别 | N | Y | 常规字典 | 机构类别字典信息；如：卫生局，卫生所，卫生厅，卫生院 |
| 12 | 检测小组 | N | Y | 常规字典 | 检测小组字典信息；如：核医学，化学免疫 |
| 13 | 临床诊断 | N | Y | 常规字典 | 临床诊断字典信息； |
| 14 | 民族 | N | Y | 常规字典 | 民族字典信息；如：阿昌族，白族， |
| 15 | 年龄单位 | N | Y | 常规字典 | 年龄单位字典信息；如：岁，天，小时， |
| 16 | 签收拒绝或撤销原因 | N | Y | 常规字典 | 签收拒绝或撤销原因字典信息；如：标本损坏 |
| 17 | 送检小组 | N | Y | 常规字典 | 送检小组字典信息；如：骨质疏松，核医学， |
| 18 | 退回原因 | N | Y | 常规字典 | 退回原因字典信息； |
| 19 | 项目单位 | N | Y | 常规字典 | 项目单位字典信息；如：%，/HP，/LP |
| 20 | 性别 | N | Y | 常规字典 | 性别字典信息；如：男，女，未知 |
| 21 | 样本类型 | N | Y | 常规字典 | 标本种类字典信息；如：血、血清等 |
| 系统内置类字典 | 系统内置类字典 | 系统内置类字典 | 系统内置类字典 | 系统内置类字典 | 系统内置类字典 |
| 1 | 住院绑定条码动作 | Y | N | 系统内置 | 住院绑定条码动作字典信息；如：绑定并确认收费，绑定不确认收费，签收并确认收费 |
| 2 | 病人匹配方式 | Y | N | 系统内置 | 病人匹配方式字典信息；如：病员号+磁卡号+姓名+性别或病员号+姓名+性别 |
| 3 | 流程节点 | Y | N | 系统内置 | 流程节点字典信息；如：绑定时间，报告时间，采样时间 |
| 4 | 门诊绑定条码动作 | Y | N | 系统内置 | 门诊绑定条码动作字典信息；如：绑定并确认收费，签收并确认收费 |
| 5 | 住院绑定条码动作 | Y | N | 系统内置 | 住院绑定条码动作字典信息；如：绑定并确认收费，绑定不确认收费，签收并确认收费 |
| 6 | 下载病人信息方式 | Y | N | 系统内置 | 下载病人信息方式字典信息；如：病员号，磁卡号，发票号，条码号 |
| 7 | 下载医嘱天数 | Y | N | 系统内置 | 下载医嘱天数字典信息；如：15天，1月，3月，7天 |
| 8 | 标本不合格分类 | Y | N | 系统内置 | 标本不合格原因分类
如：样本类型错误，标本溶血等 |



####  字典配置

##### 获取字典数据

接口说明：用于获取模块对应的快捷菜单，含菜单名称及图标

请求URL：../common/request/getdicdata

示例URL：http://192.168.14.253:8080/lis/common/request/getdicdata?dictype=样本类型,病人类型,申请科室

场景描述

初始化模块时，获取字典数据

接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | dictype | 字典类型 | string | 传入字典类型集合，多个用逗号分隔
如：样本类型,病人类型,申请科室 |
| 2 | modulecode | 模块代码 | string | 传入当前模块代码，如：LIS_REPORTINPUT |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：[字典类型]  类型：array<object> | 节点名称：[字典类型]  类型：array<object> | 节点名称：[字典类型]  类型：array<object> | 节点名称：[字典类型]  类型：array<object> | 节点名称：[字典类型]  类型：array<object> |
| 1 | CODE | 字典代码 | string | 字典代码，如：001 |
| 2 | NAME | 字典名称 | string | 字典名称，如：血清 |
| 3 | DICID | 字典ID | string | 字典ID，如：001 |
| 4 | DICTYPE | 字典类型 | string | 字典类型，如：样本类型 |
| 5 | EXTERNCODE | 外部码 | string | HIS外部代码 |
| 6 | MEMCODE1 | 输入码一 | string | 输入码一 |
| 7 | MEMCODE2 | 输入码二 | string | 输入码二 |
| 8 | SUBSYSCODE | 系统代码 | string | 系统代码，LIS默认为LIMS |
| 9 | ICONCLASS | 图标类型 | string | 可为空 |
| 10 | ORDERNO | 序号 | number | 排序序号，根据此字段来显示展示顺序 |



后台返回示例：



JSON返回示例：

“样本类型”:

[

{

    "CODE":"1",

    "NAME":"血",

    "DICID":"1",

    "DICTYPE":"样本类型",

    "EXTERNCODE":"01",

    "MEMCODE1":"1",

    "MEMCODE2":"X",

    "SUBSYSCODE":"LIMS",

    "ICONCLASS":"",

    "ORDERNO":"1"

},

{

    "CODE":"10",

    "NAME":"血清",

    "DICID":"10",

    "DICTYPE":"样本类型",

    "EXTERNCODE":"02",

    "MEMCODE1":"",

    "MEMCODE2":"XQ",

    "SUBSYSCODE":"LIMS",

    "ICONCLASS":"",

    "ORDERNO":"1"

}

]

“病人类型”:

[

{

    "CODE":"0",

    "NAME":"门诊",

    "DICID":"0",

    "DICTYPE":"病人类型",

    "EXTERNCODE":"0",

    "MEMCODE1":"0",

    "MEMCODE2":"",

    "SUBSYSCODE":"LIMS",

    "ICONCLASS":"",

    "ORDERNO":"1"

},

{

    "CODE":"1",

    "NAME":"住院",

    "DICID":"1",

    "DICTYPE":"病人类型",

    "EXTERNCODE":"1",

    "MEMCODE1":"1",

    "MEMCODE2":"",

    "SUBSYSCODE":"LIMS",

    "ICONCLASS":"",

    "ORDERNO":"1"

}

]

代码实现：

表结构：SLAVE\SYS_DEPT\SYS_USERS等

参考SQL：

select top 2 SLAVENO AS CODE,SLAVENAME AS NAME,SLAVENO AS DICID,CLASSCODE AS DICTYPE,EXTERNCODE,MEMCODE1,MEMCODE2,SUBSYSCODE,'' as ICONCLASS,ORDERNO

FROM SLAVE

WHERE HOSPITALCODE='9999' AND SUBSYSCODE='LIMS' AND CLASSCODE = '样本类型'

#####  获取字符拼音码、五笔码、全拼 65616

接口说明：获取字符串的拼音码、五笔码、全拼

请求URL：../common/request/getchinesecode

示例URL：http://192.168.14.253:8080/lis/common/request/getchinesecode?str=字符串&codetype=0

场景描述

初始化模块时，获取字典数据

接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | str | 字符串 | string | 需要解析转换的字符串 |
| 2 | codetype | 解码类型 | string | 0-全拼
1-首拼
2-五笔 |



接口出参【ResposeMessage】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：[字典类型]  类型：array<object> | 节点名称：[字典类型]  类型：array<object> | 节点名称：[字典类型]  类型：array<object> | 节点名称：[字典类型]  类型：array<object> | 节点名称：[字典类型]  类型：array<object> |
| 1 | type | 消息类型 | string | SUCCESS-成功 
ERROR-系统出现异常或错误
FAILT-业务逻辑处理失败 |
| 2 | messageCode | 消息代码 | string | 用于区分消息代码 |
| 3 | message | 消息内容 | string | type=ERROR时则为系统错误消息提示内容
type=FAILT时则为业务提示信息
type=SUCCESS时则为解析后的编码 |
| 4 | link | URL链接 | string | 用于提示用户后可以点击查看详情信息，默认为空 |
| 5 | data | 数据集合 | object | 用于存储返回的数据 |



代码实现：

通过程序实现解码处理

程序实现不了，则调用存储csp_getchinesecode实现

代码单元：winning.lis.common.utils.StringUtils

#####  获取参数值

接口说明：用于获取参数值的信息

请求URL：../common/request/readsettingsvalue

示例URL：http://192.168.14.253:8080/lis/common/request/readsettingsvalue?paramcode=100.BG.SH.001,100.BG.SH.002

场景描述

业务功能应用时，需判断参数来执行不同的代码及流程

接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 项目集合：paramcodelist类型 Array<String> | 项目集合：paramcodelist类型 Array<String> | 项目集合：paramcodelist类型 Array<String> | 项目集合：paramcodelist类型 Array<String> | 项目集合：paramcodelist类型 Array<String> |
| 1 | paramcode | 参数代码 | string | 参数代码，如：100.BG.SH.001 |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：paramlist  类型：array<object> | 节点名称：paramlist  类型：array<object> | 节点名称：paramlist  类型：array<object> | 节点名称：paramlist  类型：array<object> | 节点名称：paramlist  类型：array<object> |
| 1 | PARAMCODE | 参数代码 | string | 参数代码 |
| 2 | PARAMDESC | 参数描述 | string | 参数描述 |
| 3 | SECTION | SECTION | string | SECTION |
| 4 | ENTRY | ENTRY | string | ENTRY |
| 5 | VALUE | 参数值 | string | 参数值，字符型 boolean时，0-false，1-true |
| 6 | REMARK | 备注信息 | string | 参数备注说明 |



后台返回示例：



JSON返回示例：

"paramlist" :[

{

    "PARAMCODE":"MZCX.BBCJ.BRPPFS",

    "PARAMDESC":"门诊采血.标本采集.病人匹配方式",

    "SECTION":"门诊采集",

    "ENTRY":"病人匹配方式",

    "VALUE":"0",

    "REMARK":"选择病人的匹配方式，用于获取一些历史数据比对是否是同一个患者"

},

{

    "PARAMCODE":"MZCX.BBCJ.TMDZLX",

    "PARAMDESC":"门诊采血.标本采集.绑定条码动作类型",

    "SECTION":"门诊采集",

    "ENTRY":"绑定条码动作类型",

    "VALUE":"1",

    "REMARK":"用于选择绑定条码时的类型，主要为了确定绑定之后条码的状态及是否收费"

}

]

代码实现：

表结构：SETTINGSDIC\SETTINGS

逻辑关系：

先取SETTINGSDIC中的参数，判断DOMAIN

根据DOMAIN的值再到SETTINGS中获取，DOMAIN不同，条件也不同

DOMAIN=1 存储系统代码；DOMAIN=2 存储计算机名(Ip)；DOMAIN=3 存储用户ID

SETTINGS获取不到参数时，则获取SETTINGSDIC表中的默认值

参考SQL：

Select PARAMCODE,PARAMDESC,SECTION,ENTRY,VALUE,REMARK from SETTINGSDIC

Select PARAMCODE,PARAMDESC,SECTION,ENTRY,VALUE,REMARK,DOMAIN,DOMAINNAME from SETTINGS

#####  获取单个参数结果

接口说明：用于获取指定参数设置的信息

请求URL：内部接口使用

返回值：String

代码文件：winning.lis.common.ComDicData.java

场景描述

业务功能应用时，需判断参数来执行不同的代码及流程

接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | paramcode | 参数代码 | string | 参数代码，如：100.BG.SH.001 |



接口出参：String

代码实现：

表结构：SETTINGSDIC\SETTINGS

逻辑关系：

先取SETTINGSDIC中的参数，判断DOMAIN

根据DOMAIN的值再到SETTINGS中获取，DOMAIN不同，条件也不同

DOMAIN=1 存储系统代码；DOMAIN=2 存储计算机名(Ip)；DOMAIN=3 存储用户ID

SETTINGS获取不到参数时，则获取SETTINGSDIC表中的默认值

参考SQL：

Select PARAMCODE,PARAMDESC,SECTION,ENTRY,VALUE,REMARK from SETTINGSDIC

Select PARAMCODE,PARAMDESC,SECTION,ENTRY,VALUE,REMARK,DOMAIN,DOMAINNAME from SETTINGS



#####  获取页面动态控件列表

接口说明：传入模块代码获取动态表单控件，含控制属性

请求URL：../common/request/getpageconfiglist

示例URL：http://192.168.14.253:8080/lis/common/request/getpageconfiglist?modulecode=LIS_REPORTINPUT.PATREG&instid=518

场景描述

动态编辑控件，用于设置用户关注的控件及内容

接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | modulecode | 模块代码 | string | 传入当前模块代码，如：LIS_REPORTINPUT.PATREG |
| 2 | instid | 仪器id | string | 非必要传，后续可能考虑会根据仪器过滤 |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- | --- |
| 节点名称：[controllist]  类型：array<object> | 节点名称：[controllist]  类型：array<object> | 节点名称：[controllist]  类型：array<object> | 节点名称：[controllist]  类型：array<object> | 节点名称：[controllist]  类型：array<object> | 节点名称：[controllist]  类型：array<object> |
| 1 | MODULECODE | MODULECODE | 模块代码 | string | 模块代码 格式:模块代码+子组件 |
| 2 | MODULENAME | MODULENAME | 模块名称 | string | 模块名称 格式:模块名称+子组件 如:报告管理.病人信息 |
| 3 | TABLENAME | TABLENAME | 表名 | string | 表名 |
| 4 | FIELDNAME | FIELDNAME | 字段名 | string | 字段名 |
| 5 | FIELDINTER | FIELDINTER | 字段内码 | string | 内部字段名 ComboBox时存储代码 |
| 6 | FIELDDESC | FIELDDESC | 字段标题 | string | 字段名称 |
| 7 | EDITOR | EDITOR | 编辑器 | string | 编辑器 label-显示、text-文本编辑、combobox-下拉、textarea-富文本框、numberbox-spinner、combotree-下拉树、datetime-日期时间、date-日期、text-search -文本框（可检索) |
| 8 | CLASSCODE | CLASSCODE | 字典分类 | string | 字典分类 如：病人类别、申请科室等 |
| 9 | MEMCODE1 | MEMCODE1 | 输入码一 | string | 输入码一 |
| 10 | MEMCODE2 | MEMCODE2 | 输入码二 | string | 输入码二 |
| 11 | IMENAME | IMENAME | 输入法 | string | 输入法 |
| 12 | KEEPVALUE | KEEPVALUE | 保留上一次值 | string | 保留上一次的值 |
| 13 | READONLY | READONLY | 只读属性 | string | 只读标记 |
| 14 | DEFVALUE | DEFVALUE | 缺省值 | string | 缺省值 |
| 15 | HINTCONTENT | HINTCONTENT | 提示内容 | string | 提示信息，未输入内容时的提示信息 |
| 16 | ISCANADD | ISCANADD | 允许添加保存 | string | 是否可添加保存 针对text-search类的 |
| 17 | ISSTRICT | ISSTRICT | 严格 | string | 是否严格 针对combobox、combotree及text-search |
| 18 | REMARK | REMARK | 备注说明 | string | 备注说明 |
| 19 | ORDERNO | ORDERNO | 排序 | number | 排序 |
| 20 | RESERVEFIELD1 | RESERVEFIELD1 | 预留字段1 | string | 预留字段1 |
| 21 | RESERVEFIELD2 | RESERVEFIELD2 | 预留字段2 | string | 预留字段2 |
| 22 | RESERVEFIELD3 | RESERVEFIELD3 | 预留字段3 | string | 预留字段3 |
| 23 | RESERVEFIELD4 | RESERVEFIELD4 | 预留字段4 | string | 预留字段4 |
| 24 | RESERVEFIELD5 | RESERVEFIELD5 | 预留字段5 | string | 预留字段5 |
| 25 | VISIBLE | VISIBLE | 是否显示 | string | 是否显示 0-不显示 1-显示 |



后台返回示例：



JSON返回示例：

"controllist":[

{

    "ORDERNO":"1",

    "MODULECODE":"LIS_REPORTINPUT.PATREG",

    "MODULENAME":"报告管理.患者登记",

    "TABLENAME":"LIS_LIST",

    "FIELDNAME":"WARDORREGNAME",

    "FIELDINTER":"WARDORREG",

    "FIELDDESC":"病人类型",

    "EDITOR":"combobox",

    "CLASSCODE":"病人类型",

    "MEMCODE1":"brlx",

    "MEMCODE2":"WARDORREG",

    "IMENAME":"英文",

    "KEEPVALUE":"1",

    "READONLY":"0",

    "DEFVALUE":"1",

    "HINTCONTENT":"",

    "ISCANADD":"0",

    "ISSTRICT":"0",

    "REMARK":"病人类型",

    "RESERVEFIELD1":"",

    "RESERVEFIELD2":"",

    "RESERVEFIELD3":"",

    "RESERVEFIELD4":"",

    "RESERVEFIELD5":"",

    "VISIBLE":"1"

},

{

    "ORDERNO":"2",

    "MODULECODE":"LIS_REPORTINPUT.PATREG",

    "MODULENAME":"报告管理.患者登记",

    "TABLENAME":"LIS_LIST",

    "FIELDNAME":"CARDNO",

    "FIELDINTER":"CARDNO",

    "FIELDDESC":"磁卡号",

    "EDITOR":"text",

    "CLASSCODE":"",

    "MEMCODE1":"ckh",

    "MEMCODE2":"CARDNO",

    "IMENAME":"英文",

    "KEEPVALUE":"0",

    "READONLY":"0",

    "DEFVALUE":"",

    "HINTCONTENT":"刷卡或按F8读卡",

    "ISCANADD":"0",

    "ISSTRICT":"0",

    "REMARK":"磁卡号 F8-读卡",

    "RESERVEFIELD1":"",

    "RESERVEFIELD2":"",

    "RESERVEFIELD3":"",

    "RESERVEFIELD4":"",

    "RESERVEFIELD5":"",

    "VISIBLE":"1"

}

]

代码实现：

表结构：LIS_PAGECONFIG

处理逻辑：个人设置 > 全局设置 > 出厂设置（仪器过滤待考虑）

参考SQL：

select  ORDERNO,MODULECODE,MODULENAME,TABLENAME,FIELDNAME,FIELDINTER,FIELDDESC,EDITOR,CLASSCODE,MEMCODE1,MEMCODE2,IMENAME,KEEPVALUE,READONLY,DEFVALUE,HINTCONTENT,ISCANADD,ISSTRICT,REMARK,RESERVEFIELD1,RESERVEFIELD2,RESERVEFIELD3,RESERVEFIELD4,RESERVEFIELD5,VISIBLE

from LIS_PAGECONFIG 

WHERE MODULECODE = 'LIS_REPORTINPUT.PATREG'

ORDER BY ORDERNO ASC

#####  获取收费项目标本对照

接口说明：获取传入指定项目的标本类型集合

请求URL：../common/request/getordersamplelist

代码文件：winning.lis.common.ComDicData.java

示例URL：http://192.168.14.253:8080/lis/common/request/getordersamplelist?hospitalcode=9999&lisordercode=120001

场景描述

标本采集界面，标本对照或开错了，需要手动更改样本类型，根据医嘱来过滤

接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 机构代码 | string | 允许传空，传空则为取当前用户所在机构 |
| 2 | lisordercode | 检测项目 | string | 不允许传空，对应医嘱信息里面的LISORDERCODE |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- | --- |
| 节点名称：[samplelist]  类型：array<object> | 节点名称：[samplelist]  类型：array<object> | 节点名称：[samplelist]  类型：array<object> | 节点名称：[samplelist]  类型：array<object> | 节点名称：[samplelist]  类型：array<object> | 节点名称：[samplelist]  类型：array<object> |
| 1 | CODE | CODE | 字典代码 | string | 字典代码，如：001 |
| 2 | NAME | NAME | 字典名称 | string | 字典名称，如：血清 |
| 3 | DICID | DICID | 字典ID | string | 字典ID，如：001 |
| 4 | DICTYPE | DICTYPE | 字典类型 | string | 字典类型，如：样本类型 |
| 5 | EXTERNCODE | EXTERNCODE | 外部码 | string | HIS外部代码 |
| 6 | MEMCODE1 | MEMCODE1 | 输入码一 | string | 输入码一 |
| 7 | MEMCODE2 | MEMCODE2 | 输入码二 | string | 输入码二 |
| 8 | SUBSYSCODE | SUBSYSCODE | 系统代码 | string | 系统代码，LIS默认为LIMS |
| 10 | ORDERNO | ORDERNO | 序号 | number | 排序序号，根据此字段来显示展示顺序 |



后台返回示例：



JSON返回示例：

“samplelist”:

[

{

    "CODE":"1",

    "NAME":"血",

    "DICID":"1",

    "DICTYPE":"样本类型",

    "EXTERNCODE":"01",

    "MEMCODE1":"1",

    "MEMCODE2":"X",

    "SUBSYSCODE":"LIMS",

    "ICONCLASS":"",

    "ORDERNO":"1"

},

{

    "CODE":"10",

    "NAME":"血清",

    "DICID":"10",

    "DICTYPE":"样本类型",

    "EXTERNCODE":"02",

    "MEMCODE1":"",

    "MEMCODE2":"XQ",

    "SUBSYSCODE":"LIMS",

    "ICONCLASS":"",

    "ORDERNO":"1"

}

]

代码实现：

表结构：SLAVE\LIS_ORDERSAMPLE

实现逻辑：

判断是否配置了项目对应的标本，查看LIS_ORDERSAMPLE

未配置项目对应的标本时，获取所有的标本类型返回

参考SQL：

SELECT SAMPLECODE from LIS_ORDERSAMPLE WHERE HOSPITALCODE='9999' AND LISORDERCODE = '120001'



select top 2 SLAVENO AS CODE,SLAVENAME AS NAME,SLAVENO AS DICID,CLASSCODE AS DICTYPE,EXTERNCODE,MEMCODE1,MEMCODE2,SUBSYSCODE,'' as ICONCLASS,ORDERNO

FROM SLAVE

WHERE HOSPITALCODE='9999' AND SUBSYSCODE='LIMS' AND CLASSCODE = '样本类型'

##### 生成节假日数据

接口说明：生成近两个月内的周六日节假日排班表

内部方法：genExecDays

代码文件：winning.lis.common.ComDicData.java

场景描述

生成回执单时，会调用此方法生成节假日顺延时间

接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | dateType | 日期类型 | string | 0-节假日设置 |
| 2 | genDays | 生成天数 | number | 生成天数 |



接口出参：void

代码实现：

表结构：LIS_SETEXECDAYS

实现要求：

每天只生成一次，不能重复生成

已存在的日期设定不允许删除，未生成的自动生成，处理周六日

##### 相关指标获取

接口说明：根据当前指标获取相关指标集合

请求URL：../common/request/getrelativeitems 

示例URL：http://192.168.14.253:8080/lis/common/request/getrelativeitems?instid=518itemcode=WBC&samplecode=0001&hospitalcode=

业务场景：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | instid | 仪器ID | string | 不允许为空，如：10000 |
| 2 | itemcode | 项目代码 | string | 不允许为空，如：WBC |
| 3 | samplecode | 标本代码 | string | 不允许为空，如：0001 |
| 4 | hospitalcode | 机构代码 | string | 允许为空，为空取当前用户的医疗机构，如：9999 |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：relativeitemlist  类型：array<object> | 节点名称：relativeitemlist  类型：array<object> | 节点名称：relativeitemlist  类型：array<object> | 节点名称：relativeitemlist  类型：array<object> | 节点名称：relativeitemlist  类型：array<object> |
| 1 | HOSPITALCODE | 机构代码 | string | 如：9999 |
| 2 | ITEMCODE | 项目代码 | string | 如：WBC |
| 3 | ITEMNAME | 项目名称 | string | 如：白细胞计数 |
| 4 | ITEMNUM | 标准编码 | string | 如：WBC |
| 5 | SAMPLECODE | 样本代码 | string | 如：0001 |
| 6 | INSTID | 仪器ID | string | 如：518 |
| 7 | RELATEITEMCODE | 相关指标代码 | string | 如：RBC |
| 8 | RELATEITEMNAME | 指标项目名称 | string | 如：红细胞 |
| 9 | RELATEITEMNUM | 指标标准编码 | string | 如：RBC |
| 10 | RELATESAMPLECODE | 标本种类 | string | 标本种类代码 |
| 11 | RELATESAMPLENAME | 标本种类名称 | string | 标本种类名称，如：血清 |
| 11 | RELATEINSTID | 相关指标仪器ID | string | 如：158 |
| 12 | RELATEINSTCODE | 相关指标仪器编码 | string | 如：AU5100 |
| 13 | RELATEINSTNAME | 相关指标仪器名称 | string | 如：AU5100 |
| 14 | ORDERNO | 显示顺序 | int | 如：1 |



JSON格式示例:

" relativeitemlist ":[

{

    "HOSPITALCODE":"9999",

    ..............

"RELATESAMPLECODE":"0001"，

"RELATEINSTID":"518"

},

{

    "HOSPITALCODE":"9999",

    ..............

"RELATESAMPLECODE":"0001"，

"RELATEINSTID":"518"

}

]

代码实现：

表结构：LIS_INSTRUMENT\LIS_ RELATIVEITEM\LIS_ITEM

##### 获取仪器对应的收费项目

接口说明：获取仪器可检测的收费项目

请求URL：../common/request/getinstexamorder

示例URL：http://192.168.14.253:8080/lis/common/request/getinstexamorder?instid=518&itemcodelist=[]

场景描述：

下载医嘱项目时，需要根据仪器的收费项目进行过滤；

上传医嘱的时候，需要获取最近上传的医嘱信息；

标本入库时，根据仪器过滤可入库的项目；

接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | instid | 仪器id | string | 传入当前操作的仪器id，如：518 |
| 项目集合：itemcodelist 类型 array<String>
允许为空，为空时获取当前仪器所有的检测项目 | 项目集合：itemcodelist 类型 array<String>
允许为空，为空时获取当前仪器所有的检测项目 | 项目集合：itemcodelist 类型 array<String>
允许为空，为空时获取当前仪器所有的检测项目 | 项目集合：itemcodelist 类型 array<String>
允许为空，为空时获取当前仪器所有的检测项目 | 项目集合：itemcodelist 类型 array<String>
允许为空，为空时获取当前仪器所有的检测项目 |
| 1 | lisordercode | LIS项目代码 | string | 项目代码 |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- | --- |
| 节点名称：orderlist   类型：array<object> | 节点名称：orderlist   类型：array<object> | 节点名称：orderlist   类型：array<object> | 节点名称：orderlist   类型：array<object> | 节点名称：orderlist   类型：array<object> | 节点名称：orderlist   类型：array<object> |
| 1 | LISORDERCODE | LISORDERCODE | LIS代码 | string | LIS项目代码，如：120001 |
| 2 | HISORDERCODE | HISORDERCODE | HIS代码 | string | HIS项目代码，如：20001 |
| 3 | HISORDERNAME | HISORDERNAME | HIS名称 | string | HIS项目名称，如：全血 |
| 4 | ITEMTYPE | ITEMTYPE | 项目类型 | string | 0-临床 1-收费 2-药品 |
| 5 | MEMCODE1 | MEMCODE1 | 输入码一 | string | 输入码一 |
| 6 | MEMCODE2 | MEMCODE2 | 输入码二 | string | 输入码二 |
| 7 | SAMPLECODE | SAMPLECODE | 标本代码 | string | 标本代码 |
| 8 | SAMPLENAME | SAMPLENAME | 标本名称 | string | 标本名称 |
| 9 | REMARK | REMARK | 备注 | string | 备注 |
| 10 | PXXH | PXXH | 排序序号 | string | 排序序号 |
| 11 | QSYBH | QSYBH | 起始样本 | string | 起始样本号 |
| 12 | INSTID | INSTID | 仪器ID | string | 仪器ID |



后台返回示例：



JSON返回示例：

"orderlist":[

{

    "INSTID":"2",

    "LISORDERCODE":"033904",

    "ITEMTYPE":"1",

    "HISORDERCODE":"33904",

"HISORDERNAME":"各种标本霉菌培养及鉴定（进口）",

"MEMCODE1":"",

"MEMCODE2":"",

"SAMPLECODE":"",

"SAMPLENAME":"",

    "REMARK":"",

    "PXXH":"",

    "QSYBH":""

},

{

    "INSTID":"2",

    "LISORDERCODE":"064210",

    "ITEMTYPE":"0",

    "HISORDERCODE":"64210",

    "HISORDERNAME":"血培养鉴定（仪器、需氧）",

"MEMCODE1":"",

"MEMCODE2":"",

"SAMPLECODE":"",

"SAMPLENAME":"",

    "REMARK":"",

    "PXXH":"",

    "QSYBH":""

}

]

相关表结构：LIS_INSTORDER

参考语句

SELECT * from LIS_INSTORDER

##### 获取仪器对应的收费项目

接口说明：获取仪器可检测的收费项目

请求URL：../common/request/getinstexamorder

示例URL：http://192.168.14.253:8080/lis/common/request/getinstexamorder?instid=518&itemcodelist=[]

场景描述：

下载医嘱项目时，需要根据仪器的收费项目进行过滤；

上传医嘱的时候，需要获取最近上传的医嘱信息；

标本入库时，根据仪器过滤可入库的项目；

接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | instid | 仪器id | string | 传入当前操作的仪器id，如：518 |
| 项目集合：itemcodelist 类型 array<String>
允许为空，为空时获取当前仪器所有的检测项目 | 项目集合：itemcodelist 类型 array<String>
允许为空，为空时获取当前仪器所有的检测项目 | 项目集合：itemcodelist 类型 array<String>
允许为空，为空时获取当前仪器所有的检测项目 | 项目集合：itemcodelist 类型 array<String>
允许为空，为空时获取当前仪器所有的检测项目 | 项目集合：itemcodelist 类型 array<String>
允许为空，为空时获取当前仪器所有的检测项目 |
| 1 | lisordercode | LIS项目代码 | string | 项目代码 |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- | --- |
| 节点名称：orderlist   类型：array<object> | 节点名称：orderlist   类型：array<object> | 节点名称：orderlist   类型：array<object> | 节点名称：orderlist   类型：array<object> | 节点名称：orderlist   类型：array<object> | 节点名称：orderlist   类型：array<object> |
| 1 | LISORDERCODE | LISORDERCODE | LIS代码 | string | LIS项目代码，如：120001 |
| 2 | HISORDERCODE | HISORDERCODE | HIS代码 | string | HIS项目代码，如：20001 |
| 3 | HISORDERNAME | HISORDERNAME | HIS名称 | string | HIS项目名称，如：全血 |
| 4 | ITEMTYPE | ITEMTYPE | 项目类型 | string | 0-临床 1-收费 2-药品 |
| 5 | MEMCODE1 | MEMCODE1 | 输入码一 | string | 输入码一 |
| 6 | MEMCODE2 | MEMCODE2 | 输入码二 | string | 输入码二 |
| 7 | SAMPLECODE | SAMPLECODE | 标本代码 | string | 标本代码 |
| 8 | SAMPLENAME | SAMPLENAME | 标本名称 | string | 标本名称 |
| 9 | REMARK | REMARK | 备注 | string | 备注 |
| 10 | PXXH | PXXH | 排序序号 | string | 排序序号 |
| 11 | QSYBH | QSYBH | 起始样本 | string | 起始样本号 |
| 12 | INSTID | INSTID | 仪器ID | string | 仪器ID |



后台返回示例：



JSON返回示例：

"orderlist":[

{

    "INSTID":"2",

    "LISORDERCODE":"033904",

    "ITEMTYPE":"1",

    "HISORDERCODE":"33904",

"HISORDERNAME":"各种标本霉菌培养及鉴定（进口）",

"MEMCODE1":"",

"MEMCODE2":"",

"SAMPLECODE":"",

"SAMPLENAME":"",

    "REMARK":"",

    "PXXH":"",

    "QSYBH":""

},

{

    "INSTID":"2",

    "LISORDERCODE":"064210",

    "ITEMTYPE":"0",

    "HISORDERCODE":"64210",

    "HISORDERNAME":"血培养鉴定（仪器、需氧）",

"MEMCODE1":"",

"MEMCODE2":"",

"SAMPLECODE":"",

"SAMPLENAME":"",

    "REMARK":"",

    "PXXH":"",

    "QSYBH":""

}

]

相关表结构：LIS_INSTORDER

参考语句

SELECT * from LIS_INSTORDER



###  业务相关



#### 用户行为及配置

##### 获取模块快捷栏菜单

接口说明：用于获取模块对应的快捷菜单，含菜单名称及图标

请求URL：../common/request/getmoduleshortcut

示例URL：http://192.168.14.253:8080/lis/common/request/getmoduleshortcut?modulecode=LIS_REPORTINPUT&modulename=报告管理

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | modulecode | 模块代码 | string | 传入当前模块代码，如：LIS_REPORTINPUT |
| 2 | modulename | 模块名称 | string | 传入当前模块名称，如：报告管理 |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：menulist  类型：array<object> | 节点名称：menulist  类型：array<object> | 节点名称：menulist  类型：array<object> | 节点名称：menulist  类型：array<object> | 节点名称：menulist  类型：array<object> |
| 1 | MODULECODE | 模块代码 | string | 模块代码，如：LIS_REPORTINPUT |
| 2 | MODULENAME | 模块名称 | string | 模块名称，如：报告管理 |
| 3 | MENUCODE | 菜单代码 | string | 菜单代码，如：LIS_SAMPLEREG |
| 4 | MENUNAME | 菜单名称 | string | 菜单名称，如：标本签收 |
| 5 | ICONCLASS | 按钮图标 | string | 字体库名称 |
| 7 | REMARK | 备注说明 | string | 备注说明信息 |
| 8 | ORDERNO | 序号 | number | 排序序号，根据此字段来显示快捷菜单的顺序 |



后台返回示例：



JSON返回示例：

[

{

    "MODULECODE":"LIS_REPORTINPUT",

    "MODULENAME":"报告管理",

    "MENUCODE":"LIS_SAMPLEREG",

    "MENUNAME":"标本签收",

    "ICONCLASS":"icon-samplereg",

    "REMARK":"",

    "ORDERNO":"1"

},

{

    "MODULECODE":"LIS_REPORTINPUT",

    "MODULENAME":"报告管理",

    "MENUCODE":"LIS_SAMPLEINPUT",

    "MENUNAME":"标本入库",

    "ICONCLASS":"icon-sampleinput",

    "REMARK":"",

    "ORDERNO":"2"

},

{

    "MODULECODE":"LIS_REPORTINPUT",

    "MODULENAME":"报告管理",

    "MENUCODE":"LIS_PATQUERY",

    "MENUNAME":"患者360",

    "ICONCLASS":"icon-patquery",

    "REMARK":"",

    "ORDERNO":"3"

}

]

代码实现：

表结构：LIS_MODULESHORTCUT

实现流程：个人配置 > 全局配置 > 出厂配置

参考SQL：

SELECT MODULECODE,MODULENAME,MENUCODE,MENUNAME,ICONCLASS,REMARK,ORDERNO 

FROM LIS_MODULESHORTCUT

WHERE HOSPITALCODE = '9999' AND USERID = '001' AND DOMAIN = 2



##### 获取表格显示列信息

接口说明：传入模块代码获取表格显示列信息，含控制属性

请求URL：../common/request/getgridconfiglist

示例URL：http://192.168.14.253:8080/lis/common/request/getgridconfiglist?modulecode=LIS_SAMPLEREG.SAMPLELIST

场景描述

动态显示用户列信息，用于设置用户关注的列的信息及内容

接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | modulecode | 模块代码 | string | 传入当前模块代码，如：LIS_SAMPLEREG.SAMPLELIST |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- | --- |
| 节点名称：[collist]  类型：array<object> | 节点名称：[collist]  类型：array<object> | 节点名称：[collist]  类型：array<object> | 节点名称：[collist]  类型：array<object> | 节点名称：[collist]  类型：array<object> | 节点名称：[collist]  类型：array<object> |
| 1 | MODULECODE | MODULECODE | 模块代码 | string | 模块代码 格式:模块代码+子组件 |
| 2 | MODULENAME | MODULENAME | 模块名称 | string | 模块名称 格式:模块名称+子组件 如:报告管理.病人信息 |
| 3 | ORDERNO | ORDERNO | 序号 | number | 列显示顺序 |
| 4 | CAPTION | CAPTION | 列标题 | string | 列标题 |
| 5 | FIELDNAME | FIELDNAME | 字段名 | string | 字段名 |
| 6 | COLWIDTH | COLWIDTH | 列宽 | number | 显示字段列的宽度 |
| 7 | SORTED | SORTED | 排序 | string | NONE-不排序ASC-升序DESC-降序 |
| 8 | REMARK | REMARK | 备注说明 | string | 备注说明 |
| 9 | RESERVEFIELD1 | RESERVEFIELD1 | 预留字段1 | string | 预留字段1 |
| 10 | RESERVEFIELD2 | RESERVEFIELD2 | 预留字段2 | string | 预留字段2 |
| 11 | RESERVEFIELD3 | RESERVEFIELD3 | 预留字段3 | string | 预留字段3 |
| 12 | RESERVEFIELD4 | RESERVEFIELD4 | 预留字段4 | string | 预留字段4 |
| 13 | RESERVEFIELD5 | RESERVEFIELD5 | 预留字段5 | string | 预留字段5 |



后台返回示例：



JSON返回示例：

[

{

    "MODULECODE":"LIS_SAMPLEREG.SAMPLELIST",

    "MODULENAME":"标本签收.标本列表",

    "ORDERNO":"1",

    "CAPTION":"状态",

    "FIELDNAME":"REGSTATUS",

    "COLWIDTH":"40",

    "SORTED":"NONE",

    "REMARK":"0-未签收；1-已签收；2-签收失败",

    "RESERVEFIELD1":"",

    "RESERVEFIELD2":"",

    "RESERVEFIELD3":"",

    "RESERVEFIELD4":"",

    "RESERVEFIELD5":""

},

{

    "MODULECODE":"LIS_SAMPLEREG.SAMPLELIST",

    "MODULENAME":"标本签收.标本列表",

    "ORDERNO":"2",

    "CAPTION":"条码号",

    "FIELDNAME":"TXM",

    "COLWIDTH":"100",

    "SORTED":"NONE",

    "REMARK":"条形码",

    "RESERVEFIELD1":"",

    "RESERVEFIELD2":"",

    "RESERVEFIELD3":"",

    "RESERVEFIELD4":"",

    "RESERVEFIELD5":""

}

]

代码实现：

表结构：LIS_GRIDCONFIG

处理逻辑：个人设置 > 全局设置 > 出厂设置

参考SQL：

SELECT 			MODULECODE,MODULENAME,ORDERNO,CAPTION,FIELDNAME,COLWIDTH,SORTED,REMARK,RESERVEFIELD1,RESE	RVEFIELD2,RESERVEFIELD3,RESERVEFIELD4,RESERVEFIELD5 FROM LIS_GRIDCONFIG WHERE VISIBLE='1'  AND MODULECODE = 'LIS_SAMPLEREG.SAMPLELIST' 

#####  设置用户参数

接口说明：传入模块代码、参数代码、当前用户、用户参数设置（发生修改时），完成修改或者增加该设置

请求URL：../common/operate/setusersettings

示例URL:  http://192.168.14.253:8080/lis/common/operate/setusersettings

场景描述:用户操作界面的参数配置，保存用户操作习惯或者根据机器IP来记录当前主机的操作

接口入参：

| NO. | 变0量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | paramcode | 系统参数编码 | string | 传入当前模块代码，如：MZCX.BBCJ.BRPPFS |
| 2 | hospitalcode | 机构代码 | string | 机构代码 默认为9999 可为空 |
| 3 | value | 值 | string | 参数值 |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |



后台返回示例：



##### 设置用户行为默认值

接口说明：用于获取模块对应的快捷菜单，含菜单名称及图标

	请求URL：../common/request/setuserdefaultactiondata



示例URL：

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | module | 模块代码 | string | 传入当前模块代码，如：LIS_BATCHPRINT |
| 2 | paramcode | 参数编码 | string | 传入当前模块名称，如：打印排序 |
|  | value | 设置值 | string | techno,wardorreg |
|  | valuedesc | 设置值描述 | string | 样本号+病人类型 |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 无特殊返回，见公共返回结构体 | 无特殊返回，见公共返回结构体 | 无特殊返回，见公共返回结构体 | 无特殊返回，见公共返回结构体 | 无特殊返回，见公共返回结构体 |



后台返回示例：



JSON返回示例：

{

	"data":null,

	"failtCode":"",

	"funName":"",

	"link":"",

	"message":"",

	"messageCode":"",

	"requstUrl":"",

	"serverTime":"2018-08-31 11:15:22",

	"type":"SUCCESS"

}



代码实现：

表结构：LIS_USERSACTION 、LIS_USERSACTION_P

实现流程：个人配置  > 出厂配置

参考SQL：

##### 验证用户登录信息（用户名密码验证）

接口说明：传入用户名及密码进行验证用户。

URL：../usersvr/request/checkuser

代码文件：winning.lis.framesvr.service.UserSvrSerivce

实例URL:http://...../lis/usersvr/request/checkuser

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 机构代码 | string | 传入当前护工所在的机构代码 |
| 2 | usercode | 用户代码 | string | 用户代码 |
| 3 | userpswd | 用户密码 | string | 用户密码 |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：userinfo 类型：object | 节点名称：userinfo 类型：object | 节点名称：userinfo 类型：object | 节点名称：userinfo 类型：object | 节点名称：userinfo 类型：object |
| 1 | USERCODE | 用户代码 | string | 护工代码，对应SYS_USERS.USERCODE |
| 2 | USERNAME | 用户姓名 | string | 护工姓名，对应SYS_USERS.USERNAME 
如：李荣 |
| 3 | USERPHOTOURL | 用户头像URL | string | 用户头像URL地址
格式：..\机构代码\users\guid.jpg |
| 4 | USERPHOTO2URL | 工作照片URL | string | 工作照片URL
格式：..\机构代码\users\guid.jpg |
| 5 | LOGNO | 登录账号 | string | 登录账号 |
| 6 | DEPTCODE | 部门代码 | string | 部门代码 |
| 7 | DEPTNAME | 部门名称 | string | 部门名称 |
| 8 | EXTERNCODE | 外部代码 | string | 外部代码 |



JSON返回示例：

"userinfo":{

    "USERCODE":"23012",

    "USERNAME":"李立川",

    "USERPHOTOURL":"..\机构代码\users\照片id.jpg",

    ......................

    "DEPTNAME":"检验科",

    "EXTERNCODE":"23012"

}

代码实现：

表结构：SYS_USERS

验证通过，返回用户信息；

验证失败，返回提示信息；

用户不存在或验证失败：当前登录账号【USERCODE】不存在或密码错误；

用户停用：当前登录账号【USERCODE-USERNAME】已停用，请联系系统管理员开通！

##### 验证用户登录信息（扫描二维码）

接口说明：扫描用户二维码，通过解析用户二维码进行登录验证。

URL：../usersvr/request/checkuserbyqrcode

代码文件：winning.lis.framesvr.service.UserSvrSerivce

实例URL:http://...../lis/usersvr/request/checkuserbyqrcode

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 机构代码 | string | 传入当前护工所在的机构代码 |
| 2 | qrcode | 二维码字符串 | string | 二维码字符串 |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：userinfo 类型：object | 节点名称：userinfo 类型：object | 节点名称：userinfo 类型：object | 节点名称：userinfo 类型：object | 节点名称：userinfo 类型：object |
| 1 | USERCODE | 用户代码 | string | 护工代码，对应SYS_USERS.USERCODE |
| 2 | USERNAME | 用户姓名 | string | 护工姓名，对应SYS_USERS.USERNAME 
如：李荣 |
| 3 | USERPHOTOURL | 用户头像URL | string | 用户头像URL地址
格式：..\机构代码\users\guid.jpg |
| 4 | USERPHOTO2URL | 工作照片URL | string | 工作照片URL
格式：..\机构代码\users\guid.jpg |
| 5 | LOGNO | 登录账号 | string | 登录账号 |
| 6 | DEPTCODE | 部门代码 | string | 部门代码 |
| 7 | DEPTNAME | 部门名称 | string | 部门名称 |
| 8 | EXTERNCODE | 外部代码 | string | 外部代码 |



JSON返回示例：

"userinfo":{

    "USERCODE":"23012",

    "USERNAME":"李立川",

    "USERPHOTOURL":"..\机构代码\users\照片id.jpg",

    ......................

    "DEPTNAME":"检验科",

    "EXTERNCODE":"23012"

}

代码实现：

表结构：SYS_USERS

解析二维码，获取患者信息；

验证通过，返回用户信息；

验证失败，返回提示信息；

用户不存在或验证失败：当前登录账号【USERCODE】不存在或密码错误；

用户停用：当前登录账号【USERCODE-USERNAME】已停用，请联系系统管理员开通！



##### 获取护工相关信息

接口说明：获取护工信息，包含运送标本情况汇总。

URL：../usersvr/request/getworkerinfo

代码文件：winning.lis.framesvr.service.UserSvrSerivce

实例URL:http://...../lis/usersvr/request/getworkerinfo

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 机构代码 | string | 传入当前护工所在的机构代码 |
| 2 | workercode | 护工代码 | string | 护工代码信息 |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：workerinfo类型：object | 节点名称：workerinfo类型：object | 节点名称：workerinfo类型：object | 节点名称：workerinfo类型：object | 节点名称：workerinfo类型：object |
| 1 | WORKERCODE | 护工代码 | string | 护工代码，对应SYS_USERS.USERCODE |
| 2 | WORKERNAME | 护工姓名 | string | 护工姓名，对应SYS_USERS.USERNAME 
如：李荣 |
| 3 | USERPHOTOURL | 护工头像URL | string | 护工头像URL地址
格式：..\机构代码\users\guid.jpg |
| 4 | USERPHOTO2URL | 工作照片URL | string | 工作照片URL
格式：..\机构代码\users\guid.jpg |
| 4 | TRANSDATE | 开始运送时间 | string | 格式：yyyy-MM-dd HHH:mm:ss |
| 5 | LASTTRANSDATE | 最近一次运送时间 | string | 格式：yyyy-MM-dd HHH:mm:ss |
| 6 | SPECIMEN_TOTAL | 运送标本总量 | number | 累计标本运送总量，数字型 |
| 7 | SPECIMEN_TOTALDESC | 运送标本总量描述 | string | 标本运送总量，文字描述，如：10.3W+
算法规则：
>超出1w以上标本量，保留1位小数，显示格式：1.2w+
>1w以下标本，显示具体标本数字 |
| 8 | SPECIMEN_LOSE | 丢失数量 | number | 累计标本丢失总数量 |
| 9 | SPECIMEN_LOSERATE | 丢失率 | string | 标本丢失比率（最多两位小数），如：2.5% |
| 10 | SPECIMEN_OUTTIME | 运送标本未准时送达 | number | 累计超时送达数量 |
| 11 | SPECIMEN_OUTTIMERATE | 运送标本未按时送达率 | string | 超时送达比率（最多两位小数），如：2.5% |
| 12 | SPECIMEN_INTIMERATE | 运送标本按时送达率 | string | 按时送达比率；1- SPECIMENOUTTIMERATE
前端规范：
<80%，红色字体显示
>90%，绿色字体显示
80%到90%之间，黄色字体显示 |
| 13 | SPECIMEN_TEMP | 温度不当数量 | number | 累计温度不当数量 |
| 14 | SPECIMEN_TEMPRATE | 温度不当比率 | string | 温度不当比率（最多两位小数），如：2.5% |



JSON返回示例：

"workerinfo":{

    "WORKERCODE":"23012",

    "WORKERCODE":"李立川",

    "USERPHOTOURL":"..\机构代码\users\照片id.jpg",

    ......................

    "SPECIMEN_TEMP":223,

    "SPECIMEN_TEMPRATE":10%

}

代码实现：

表结构：LIS_NURSERWORKER\SYS_USERS

### 智能提醒接口

#### 报告相关服务

##### 待审核报告-后台服务

接口说明：根据检测出来的指标结果与报告检测项目比较，结果全部出来了，则为待审核报告；

URL：../intelligence/report/auditreportlist

代码文件：winning.lis.intelligence.service.ReportSerivce

实例URL:http://...../lis/intelligence/report/auditreportlist

业务场景描述：

加急报告结果出来之后，要及时提醒操作员审核；

仪器结果出来之后，需要主动提示操作员审核报告；

接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 后端服务类，无须传参，自动根据时间戳判断报告状态 | 后端服务类，无须传参，自动根据时间戳判断报告状态 | 后端服务类，无须传参，自动根据时间戳判断报告状态 | 后端服务类，无须传参，自动根据时间戳判断报告状态 | 后端服务类，无须传参，自动根据时间戳判断报告状态 |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 后端服务类，无须传参，将计算的结果存入定义的表结构中，便于使用的时候获取 | 后端服务类，无须传参，将计算的结果存入定义的表结构中，便于使用的时候获取 | 后端服务类，无须传参，将计算的结果存入定义的表结构中，便于使用的时候获取 | 后端服务类，无须传参，将计算的结果存入定义的表结构中，便于使用的时候获取 | 后端服务类，无须传参，将计算的结果存入定义的表结构中，便于使用的时候获取 |



代码实现：

表结构：LIS_LIST\LIS_RESULT\LIS_ACCEPTITEMS\LIS_ORDERTOITEM

针对报告对象：STATUS < 50 

什么类的报告属于待审核报告：

结果表中有结果的指标与收费项目对应项目一致；

多检测项目；

没有收费项目，但是有指标结果。

更新报告状态（针对未审核的报告，STATUS < 40）：

--报告状态 10-初始报告 20-部分指标检测完成 30-已完成检测（含多做） 40-初审通过 50-终审通过 60-发布

指标多检测或与收费对应项目一致时，更新STATUS为30，表示完成指标检测；

没有收费项目，但是有指标结果，更新STATUS为30，表示完成指标检测，提示使用，需人为判断；

存在结果结果不为空的指标，但是项目少做，更新STATUS为20；

没有任何检验指标结果，表示尚未做检测，更新STATUS为10；

未解决疑难问题：插入结果表并且配置了项目默认值，标本一入库系统可能就会判断标本结果已出，失去了意义！

##### 危急值报告（单报告判断）

接口说明：判断当前报告是否存在危急值；

URL：../intelligence/report/checkpanicitems

代码文件：winning.lis.intelligence.service.ReportSerivce

实例URL:http://...../lis/intelligence/report/checkpanicitems

业务场景：

浏览报告，提示该报告存在危急项目，并可以查看危急值项目

接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | applyno | 报告单号 | string | 传入的报告单号必须>0，否则不允许操作 |
| 2 | operatortype | 操作类型 | string | '0'-判断危急值并生成记录，'1'-查看危急值项目 |
| 4 | systype | 系统类型 | string | C-常规 W-微生物 X-血库；默认为C |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：panicitemlist  类型：array<object> | 节点名称：panicitemlist  类型：array<object> | 节点名称：panicitemlist  类型：array<object> | 节点名称：panicitemlist  类型：array<object> | 节点名称：panicitemlist  类型：array<object> |
| 1 | APPLYNO | 报告单号 | string | 如：892832 |
| 2 | INSTID | 仪器ID | string |  |
| 3 | INSTNAME | 仪器名称 | string |  |
| 4 | TECHNO | 样本号 | string |  |
| 5 | EXECTIME | 检验日期 | string | 格式：yyyy-MM-dd |
| 6 | ITEMCODE | 项目代码 | string |  |
| 7 | ITEMNAME | 项目名称 | string | 如：条码入库 |
| 8 | RESULT | 项目结果 | string | 项目结果 |
| 9 | PANICLOW | 项目危急参考值下限 | string | 危急值的参考下限，如：14 |
| 10 | PANICHIGH | 项目危急参考值上限 | string | 危急值的参考值。25.0 |
| 11 | PANICCHAR | 项目危急参考字符 | string | 危急值的字符型的参考值 |
| 12 | PANICREFERENCERANGE | 参考范围 | string | 危急值的参考值 如 14.0—25.0 |
| 13 | UNIT | 项目单位 | string | 项目单位 |
| 14 | REDO | 复做标志 | string | 危急值项目是否已经复做0 未复做,1 复做 |
|  |  |  |  |  |
|  |  |  |  |  |
|  |  |  |  |  |





#### 标本TAT相关服务

#####  根据当前标本节点计算TAT规则及违反情况

接口说明：根据传入的标本及节点信息更新标本TAT规则信息；

URL：../intelligence/tat/checksampletatrule

代码文件：winning.lis.intelligence.service.TatSerivce

实例URL:http://...../lis/intelligence/tat/checksampletatrule

业务场景描述：

标本每流转到一个节点时，需要判断相关的TAT规则违反情况；

TAT监控及智能提醒使用；

接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | txm | 标本条形码 | string |  |
| 2 | applyno | 报告单号 | number | 入库检测阶段，传具体报告单号
检测前，传-1 |
| 3 | flownode | 流程节点 | string | CY-采样时间 
BD-绑定时间 
QS-签收时间 
RK-入库时间 
HG-护工签收 
BG-报告时间 
对应SLAVE.SLAVENO CLASSCODE=流程节点 |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：tatlist类型：array<object> | 节点名称：tatlist类型：array<object> | 节点名称：tatlist类型：array<object> | 节点名称：tatlist类型：array<object> | 节点名称：tatlist类型：array<object> |
| 1 | TXM | 标本条形码 | string | 标本条形码，如：13000028923 |
| 2 | LISORDERCODE | 项目代码 | string | LIS项目代码 |
| 3 | HISORDERCODE | HIS项目代码 | string | HIS项目代码 |
| 4 | HISORDERNAME | HIS项目名称 | string | HIS项目名称 |
| 5 | SOURCENODE | 起始节点 | string | CY-采样时间 
BD-绑定时间 
QS-签收时间 
RK-入库时间 
HG-护工签收 
BG-报告时间 
对应SLAVE.SLAVENO CLASSCODE=流程节点 |
| 6 | TARGETNODE | 目标节点 | string | CY-采样时间 
BD-绑定时间 
QS-签收时间 
RK-入库时间 
HG-护工签收 
BG-报告时间 
对应SLAVE.SLAVENO CLASSCODE=流程节点 |
| 7 | NODEBEGDATE | 节点起始时间 | string | 格式：yyyy-MM-dd HH:mm:ss |
| 8 | NODEENDDATE | 节点结束时间 | string | 格式：yyyy-MM-dd HH:mm:ss |
| 9 | ESTENDDATE | 预计结束时间 | string | 格式：yyyy-MM-dd HH:mm:ss |
| 10 | NODEINTERVAL | 时间间隔（分钟） | number | 时间间隔（分钟） |
| 11 | TATRULENO | TAT规则ID | string | TAT规则ID |
| 12 | RULEDETAILNO | 明细规则ID | number | 明细规则ID |
| 13 | TATRULEDESC | TAT规则描述 | string | 违反的TAT规则名称 |
| 14 | HINTMINUTES | 预警时间（分钟） | string | 预警时间（分钟） |
| 15 | TATVOILATE | 违反TAT规则 | string | -1:预估截止时间 
0:正常 
1:违反TAT规则 
2:在预警时间范围内(警告) 
9-违反TAT规则，但不参与统计(延迟取报告一类的)
默认为0 |



代码实现：

表结构：LIS_TATRECORD\LIS_REPTIMERULE\LIS_ACCEPTITEMS\LIS_ORDERMASTER\LIS_REPTIMERULEDETAIL\LIS_REPTIMERULEORDER

获取流程节点为开始节点的规则，计算预计的时间；

获取流程节点为终止节点的规则，将节点时间与预计时间比较，计算TAT规则违反情况；

TAT计算相关结果存储到表LIS_TATRECORD中，TAT监控可直接从此表中获取数据；

##### TAT监控服务

接口说明：检测标本是否违反TAT规则，更新TAT规则状态

请求URL：../intelligence/tat/updatetatstatus

代码位置：winning.lis.intelligence.service.TatSerivce

示例URL：http://192.168.14.253:8080/lis/intelligence/tat/updatetatstatus

原型参考：

场景描述：所有标本的TAT规则是否违反的判断

接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 更新表LIS_TATRECORD状态 | 更新表LIS_TATRECORD状态 | 更新表LIS_TATRECORD状态 | 更新表LIS_TATRECORD状态 | 更新表LIS_TATRECORD状态 |



JSON返回示例：

相关表结构：LIS_TATRECORD

参考语句：

## 检验项目

### V 模块接口视图

| NO. | 接口编号 | 接口名称 | 说明 |
| --- | --- | --- | --- |
| 1 | A01 | 检验项目初始化数据
../item/request/getinitdata | 检验项目配置模块初始化数据 |
| 2 | B01 | 新增项目分类
../item/request/additemtype | 新增项目分类 （根节点和子节点添加） |
| 3 | B02 | 项目分类删除
../item/request/deleteitemtype | 项目分类删除（根节点和子节点） |
| 4 | B03 | 项目分类修改
../item/request/alteritemtype | 项目分类修改（根节点和子节点） |
| 5 | B04 | 获取项目分类的检验项目集合
../item/request/getitemtyperesult | 单击项目分类节点获取对应检测项目集合 |
| 6 | B05 | 新增检验项目
../item/request/addexamitem | 项目分类节点下检验项目添加 |
| 7 | B06 | 检验项目修改
../item/request/alterexamitem | 检验项目修改 |
| 8 | B07 | 检验项目停用与启用
../item/request/checkitemflag | 检验项目停用与启用（颜色区分停用与启用状态） |
| 9 | B08 | 分类代码自动获取
../item/request/getitemtypenum | 分类代码自动获取 |
| 10 | B09 | 检验项目的检索
../item/request/searchitems | 检验项目的检索 |



### A 初始化数据

#### A01	检验项目初始化数据

接口说明：检验项目配置模块初始化数据

请求URL：../item/request/getinitdata

代码文件：winning.lis.item.service.itemService

示例URL：http://192.168.11.211:15011/lis/item/request/getinitdata?hospitalcode=& subsyscode=LIMS

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码 |
| 2 | subsyscode | 子系统代码 | string | 传入子系统代码，允许为空
传入为空时默认为LIMS |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：controlsparams类型：object | 节点名称：controlsparams类型：object | 节点名称：controlsparams类型：object | 节点名称：controlsparams类型：object | 节点名称：controlsparams类型：object |
| 1 | OP_EDITITEMTYPE | 编辑项目分类 | boolean | true-允许添加、修改、删除项目分类
false-不允许编辑项目分类 |
| 2 | OP_EDITITEM | 编辑检验项目 | boolean | true-允许添加、修改、删除项目分类
false-不允许编辑项目分类 |
| 节点名称：diclist 类型：array<object> | 节点名称：diclist 类型：array<object> | 节点名称：diclist 类型：array<object> | 节点名称：diclist 类型：array<object> | 节点名称：diclist 类型：array<object> |
| 1 | HOSPITALCODE | 医疗结构 | string | 如：9999 |
| 2 | SUBSYSCODE | 子系统代码 | string | 如：LIMS |
| 3 | CLASSCODE | 字典分类 | string | 如：检验分组 |
| 4 | NODEID | 字典ID | int | 如：8 |
| 5 | SLAVENO | 字典代码 | string | 如：01 |
| 6 | SLAVENAME | 字典名称 | string | 如：血液体液 |
| 7 | PARENTID | 节点ID | int | 节点ID，-1表示根节点 |
| 8 | SLAVECODE | 编码规则 | string | 编码规则 区分层级关系，如：03.01 |
| 9 | MEMCODE1 | 输入码一 | string | 如：XYTY |
| 10 | MEMCODE2 | 输入码二 | string | 如：01 |
| 11 | ORDERNO | 排序 | int | 如：1 |
| 12 | REMARK | 备注说明 | string | 如： |
| 13 | CHILDNODES | 子节点 | array<object> | 子节点数组 |
|  | 子节点的详细信息 | 子节点的详细信息 | 子节点的详细信息 | 子节点的详细信息 |
| 1 | HOSPITALCODE | 医疗结构 | string | 如：9999 |
| 2 | SUBSYSCODE | 子系统代码 | string | 如：LIMS |
| 3 | CLASSCODE | 字典分类 | string | 如：检验分组 |
| 4 | NODEID | 字典ID | int | 如：8 |
| 5 | SLAVENO | 字典代码 | string | 如：01 |
| 6 | SLAVENAME | 字典名称 | string | 如：血液体液 |
| 7 | PARENTID | 节点ID | int | 节点ID，-1表示根节点 |
| 8 | SLAVECODE | 编码规则 | string | 编码规则 区分层级关系，如：03.01 |
| 9 | MEMCODE1 | 输入码一 | string | 如：XYTY |
| 10 | MEMCODE2 | 输入码二 | string | 如：01 |
| 11 | ORDERNO | 排序 | int | 如：1 |
| 12 | REMARK | 备注说明 | string | 如： |
| 13 | FATHERSLAVENO | 上级节点代码 | string | 如：01 |
| 14 | CHILDNODES | 子节点 | array<object> | 子节点数组 |
|  | 子节点的详细信息同上 | 子节点的详细信息同上 | 子节点的详细信息同上 | 子节点的详细信息同上 |



JSON返回示例：

"controlsparams":{

"OP_EDITITEMTYPE":TRUE,

"OP_EDITITEM": TRUE,

},

"diclist":{

  	 "HOSPITALCODE":"9999",

"SUBSYSCODE":"LIMS",

"CLASSCODE":"检验分组",

"NODEID":8,

"SLAVENO":" 01",

"SLAVENAME":"血液体液",

"PARENTID":- 1,

"SLAVECODE":"01",

"MEMCODE1":"XYTY",

"MEMCODE2":"01",

"ORDERNO":1,

"REMARK":" ",

"CHILDNODES":[

{

			"HOSPITALCODE":"9999",

"SUBSYSCODE":"LIMS",

"CLASSCODE":"检验分组",

"NODEID":12,

"SLAVENO":"0101",

"SLAVENAME":"血液检查",

"PARENTID":8,

"SLAVECODE":"01.01",

"MEMCODE1":"XYJC",

"MEMCODE2":"0101",

"ORDERNO":2,

"REMARK":"",

"FATHERSLAVENO":"",

"CHILDNODES":null

	},

{

		"HOSPITALCODE":"9999",

"SUBSYSCODE":"LIMS",

"CLASSCODE":"检验分组",

"NODEID":13,

"SLAVENO":"0102",

"SLAVENAME":"尿液检查",

"PARENTID":8,

"SLAVECODE":"01.02",

"MEMCODE1":"NYJC",

"MEMCODE2":"0102",

"ORDERNO":2,

"REMARK":"",

"FATHERSLAVENO":"", 

"CHILDNODES":null

	}

]

},

{

   "HOSPITALCODE":"9999",

"SUBSYSCODE":"LIMS",

"CLASSCODE":"检验分组",

"NODEID":10,

"SLAVENO":" 02",

"SLAVENAME":"临床化学",

"PARENTID":-1,

"SLAVECODE":"02",

"MEMCODE1":"LCHX",

"MEMCODE2":"02",

"ORDERNO":1,

"REMARK":" ",

"FATHERSLAVENO":"",

"CHILDNODES":null

}

代码实现：

查询表LIS_TREEDICT，递归实现树结构节点

表结构：LIS_TREEDICT

### B 业务类

#### B01	新增项目分类

接口说明：项目分类添加（根节点和子节点添加）

请求URL：../item/request/additemtype

代码文件：winning.lis.item.service.itemService

示例URL：http://192.168.11.211:15011/lis/item/request/additemtype?hospitalcode=& subsyscode=&classcode=&slaveno=01&slavename=血液检查&level=0&fatherno=-1&fathernodeid=11&parented=-1

原型参考：





接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码 |
| 2 | subsyscode | 子系统代码 | string | 传入子系统代码，允许为空
传入为空时默认为LIMS |
| 3 | classcode | 字典分类 | string | 传入项目字典分类，如：检验分组 |
| 4 | slaveno | 分类代码 | string | 不允许为空，取B08接口的SLAVENO |
| 5 | slavecode | 分类编码规则 | string | 不允许为空，取B08接口的SLAVECODE |
| 6 | parentid | 父级节点ID | string | 不允许为空，取取B08接口的PARENTID |
| 7 | slavename | 分类名称 | string | 不允许为空，如：血液检查 |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- | --- |
| 节点名称：diclist 类型：array<object> | 节点名称：diclist 类型：array<object> | 节点名称：diclist 类型：array<object> | 节点名称：diclist 类型：array<object> | 节点名称：diclist 类型：array<object> | 节点名称：diclist 类型：array<object> |
| 1 | HOSPITALCODE | 医疗结构 | 医疗结构 | string | 如：9999 |
| 2 | SUBSYSCODE | 子系统代码 | 子系统代码 | string | 如：LIMS |
| 3 | CLASSCODE | 字典分类 | 字典分类 | string | 如：检验分组 |
| 4 | NODEID | 字典ID | 字典ID | int | 如：8 |
| 5 | SLAVENO | 字典代码 | 字典代码 | string | 如：01 |
| 6 | SLAVENAME | 字典名称 | 字典名称 | string | 如：血液体液 |
| 7 | PARENTID | 节点ID | 节点ID | int | 节点ID，-1表示根节点 |
| 8 | SLAVECODE | 编码规则 | 编码规则 | string | 编码规则 区分层级关系，如：03.01 |
| 9 | MEMCODE1 | 输入码一 | 输入码一 | string | 如：XYTY |
| 10 | MEMCODE2 | 输入码二 | 输入码二 | string | 如：01 |
| 11 | ORDERNO | 排序 | 排序 | int | 如：1 |
| 12 | REMARK | 备注说明 | 备注说明 | string | 如： |
| 13 | CHILDNODES | 子节点 | 子节点 | array<object> | 子节点数组 |
|  | 子节点的详细信息 | 子节点的详细信息 | 子节点的详细信息 | 子节点的详细信息 | 子节点的详细信息 |
| 1 | HOSPITALCODE | 医疗结构 | string | string | 如：9999 |
| 2 | SUBSYSCODE | 子系统代码 | string | string | 如：LIMS |
| 3 | CLASSCODE | 字典分类 | string | string | 如：检验分组 |
| 4 | NODEID | 字典ID | int | int | 如：8 |
| 5 | SLAVENO | 字典代码 | string | string | 如：01 |
| 6 | SLAVENAME | 字典名称 | string | string | 如：血液体液 |
| 7 | PARENTID | 节点ID | int | int | 节点ID，-1表示根节点 |
| 8 | SLAVECODE | 编码规则 | string | string | 编码规则 区分层级关系，如：03.01 |
| 9 | MEMCODE1 | 输入码一 | string | string | 如：XYTY |
| 10 | MEMCODE2 | 输入码二 | string | string | 如：01 |
| 11 | ORDERNO | 排序 | int | int | 如：1 |
| 12 | REMARK | 备注说明 | string | string | 如： |
| 13 | CHILDNODES | 子节点 | array<object> | array<object> | 子节点数组 |
|  | 子节点的详细信息同上 | 子节点的详细信息同上 | 子节点的详细信息同上 | 子节点的详细信息同上 | 子节点的详细信息同上 |
| 节点名称：additemtypeinfo 类型：object | 节点名称：additemtypeinfo 类型：object | 节点名称：additemtypeinfo 类型：object | 节点名称：additemtypeinfo 类型：object | 节点名称：additemtypeinfo 类型：object | 节点名称：additemtypeinfo 类型：object |
| 1 | HOSPITALCODE | 医疗结构 | string | string | 如：9999 |
| 2 | SUBSYSCODE | 子系统代码 | string | string | 如：LIMS |
| 3 | CLASSCODE | 字典分类 | string | string | 如：检验分组 |
| 4 | NODEID | 字典ID | int | int | 如：8 |
| 5 | SLAVENO | 字典代码 | string | string | 如：01 |
| 6 | SLAVENAME | 字典名称 | string | string | 如：血液体液 |
| 7 | PARENTID | 节点ID | int | int | 节点ID，-1表示根节点 |
| 8 | SLAVECODE | 编码规则 | string | string | 编码规则 区分层级关系，如：03.01 |
| 9 | MEMCODE1 | 输入码一 | string | string | 如：XYTY |
| 10 | MEMCODE2 | 输入码二 | string | string | 如：01 |
| 11 | ORDERNO | 排序 | int | int | 如：1 |
| 12 | REMARK | 备注说明 | string | string | 如： |



JSON返回示例：

[

"diclist":{

  	 "HOSPITALCODE":"9999",

"SUBSYSCODE":"LIMS",

"CLASSCODE":"检验分组",

"NODEID":8,

"SLAVENO":" 01",

"SLAVENAME":"血液体液",

"PARENTID":-1,

"SLAVECODE":"01",

"MEMCODE1":"XYTY",

"MEMCODE2":"01",

"ORDERNO":1,

"REMARK":" ",

"CHILDNODES":[

{

			"HOSPITALCODE":"9999",

"SUBSYSCODE":"LIMS",

"CLASSCODE":"检验分组",

"NODEID":12,

"SLAVENO":"0101",

"SLAVENAME":"血液检查",

"PARENTID":8,

"SLAVECODE":"01.01",

"MEMCODE1":"XYJC",

"MEMCODE2":"0101",

"ORDERNO":2,

"REMARK":"",

"CHILDNODES":null

	},

{

		"HOSPITALCODE":"9999",

"SUBSYSCODE":"LIMS",

"CLASSCODE":"检验分组",

"NODEID":13,

"SLAVENO":"0102",

"SLAVENAME":"尿液检查",

"PARENTID":8,

"SLAVECODE":"01.02",

"MEMCODE1":"NYJC",

"MEMCODE2":"0102",

"ORDERNO":2,

"REMARK":"",

"CHILDNODES":null

	}

]

},

{

   "HOSPITALCODE":"9999",

"SUBSYSCODE":"LIMS",

"CLASSCODE":"检验分组",

"NODEID":10,

"SLAVENO":" 02",

"SLAVENAME":"临床化学",

"PARENTID":-1,

"SLAVECODE":"02",

"MEMCODE1":"LCHX",

"MEMCODE2":"02",

"ORDERNO":1,

"REMARK":" ",

"CHILDNODES":null

},

"additemtypeinfo":{

"HOSPITALCODE":"9999",

"SUBSYSCODE":"LIMS",

"CLASSCODE":"检验分组",

"NODEID":10,

"SLAVENO":" 02",

"SLAVENAME":"临床化学",

"PARENTID":-1,

"SLAVECODE":"02",

"MEMCODE1":"LCHX",

"MEMCODE2":"02",

"ORDERNO":1,

"REMARK":" " 

}

]

代码实现：

判断添加的结构级别，根节点还是子节点

插入LIS_TREEDICT表中

获取LIS_TREEDICT表中项目分类数据

表结构：LIS_TREEDICT

#### B02	项目分类删除

接口说明：项目分类删除（根节点和子节点）（如果是根节点，需要将其下面的所有子节点的nodeid带过来）

请求URL：../item/request/deleteitemtype

代码文件：winning.lis.item.service.itemService

示例URL：http://192.168.11.211:15011/lis/item/request/deleteitemtype?hospitalcode=& subsyscode=&classcode=&nodeid=[8,9]

原型参考：

接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | nodeid | 删除节点的ID | array<object> | 不允许为空，如：[8,9] |
| 2 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码 |
| 3 | subsyscode | 子系统代码 | string | 传入子系统代码，允许为空
传入为空时默认为LIMS |
| 4 | classcode | 字典分类 | string | 传入项目字典分类，如：检验分组 |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage |
| 1 | type | 消息类型 | string | SUCCESS-成功 
ERROR-系统出现异常或错误
FAILT-业务逻辑处理失败 |
| 2 | messageCode | 消息代码 | string | 用于区分消息代码 |
| 3 | message | 消息内容 | string | type=ERROR时则为系统错误消息提示内容
type=FAILT时则为业务提示信息 |
| 4 | link | URL链接 | string | 用于提示用户后可以点击查看详情信息，默认为空 |
| 5 | data | 数据集合 | object | 用于存储返回的数据 |



代码实现：

判断对应项目分类在LIS_TREEDICT表中的状态，已经删除，提示前端，否则下一步

根据数组nodeid，判断表lis_item中是否存在检验项目，如果没有检验项目，直接在LIS_TREEDICT表中删除；如果存在配置的检验项目，判断相应的项目是否被使用过，没有，LIS_TREEDICT/LIS_ITEM表中所有信息一次删除，否则提示不允许删除

获取LIS_TREEDICT表中项目分类数据

表结构：LIS_TREEDICT/LIS_ITEM

#### B03	项目分类修改

接口说明：项目分类修改（根节点和子节点）

请求URL：../item/request/alteritemtype

代码文件：winning.lis.item.service.itemService

示例URL：http://192.168.11.211:15011/lis/item/request/alteritemtype?hospitalcode=& subsyscode=&classcode=&nodeid=8&modifyno=6&modifyname=自身抗体检测

业务场景： 

接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | nodeid | 被修改节点ID | string | 不允许为空，如：8 |
| 2 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码 |
| 3 | subsyscode | 子系统代码 | string | 传入子系统代码，允许为空
传入为空时默认为LIMS |
| 4 | classcode | 字典分类 | string | 传入项目字典分类，如：检验分组 |
| 5 | modifyslavename | 修改后的分类名称 | string | 不允许为空，传入修改后的分类名称 |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- | --- |
| 节点名称：diclist 类型：array<object> | 节点名称：diclist 类型：array<object> | 节点名称：diclist 类型：array<object> | 节点名称：diclist 类型：array<object> | 节点名称：diclist 类型：array<object> | 节点名称：diclist 类型：array<object> |
| 1 | HOSPITALCODE | 医疗结构 | 医疗结构 | string | 如：9999 |
| 2 | SUBSYSCODE | 子系统代码 | 子系统代码 | string | 如：LIMS |
| 3 | CLASSCODE | 字典分类 | 字典分类 | string | 如：检验分组 |
| 4 | NODEID | 字典ID | 字典ID | int | 如：8 |
| 5 | SLAVENO | 字典代码 | 字典代码 | string | 如：01 |
| 6 | SLAVENAME | 字典名称 | 字典名称 | string | 如：血液体液 |
| 7 | PARENTID | 节点ID | 节点ID | int | 节点ID，-1表示根节点 |
| 8 | SLAVECODE | 编码规则 | 编码规则 | string | 编码规则 区分层级关系，如：03.01 |
| 9 | MEMCODE1 | 输入码一 | 输入码一 | string | 如：XYTY |
| 10 | MEMCODE2 | 输入码二 | 输入码二 | string | 如：01 |
| 11 | ORDERNO | 排序 | 排序 | int | 如：1 |
| 12 | REMARK | 备注说明 | 备注说明 | string | 如： |
| 13 | CHILDNODES | 子节点 | 子节点 | array<object> | 子节点数组 |
|  | 子节点的详细信息 | 子节点的详细信息 | 子节点的详细信息 | 子节点的详细信息 | 子节点的详细信息 |
| 1 | HOSPITALCODE | 医疗结构 | string | string | 如：9999 |
| 2 | SUBSYSCODE | 子系统代码 | string | string | 如：LIMS |
| 3 | CLASSCODE | 字典分类 | string | string | 如：检验分组 |
| 4 | NODEID | 字典ID | string | string | 如：8 |
| 5 | SLAVENO | 字典代码 | string | string | 如：01 |
| 6 | SLAVENAME | 字典名称 | string | string | 如：血液体液 |
| 7 | PARENTID | 节点ID | string | string | 节点ID，-1表示根节点 |
| 8 | SLAVECODE | 编码规则 | string | string | 编码规则 区分层级关系，如：03.01 |
| 9 | MEMCODE1 | 输入码一 | string | string | 如：XYTY |
| 10 | MEMCODE2 | 输入码二 | string | string | 如：01 |
| 11 | ORDERNO | 排序 | string | string | 如：1 |
| 12 | REMARK | 备注说明 | string | string | 如： |
| 13 | CHILDNODES | 子节点 | array<object> | array<object> | 子节点数组 |
|  | 子节点的详细信息同上 | 子节点的详细信息同上 | 子节点的详细信息同上 | 子节点的详细信息同上 | 子节点的详细信息同上 |



JSON返回示例：

[

"diclist":{

  	 "HOSPITALCODE":"9999",

"SUBSYSCODE":"LIMS",

"CLASSCODE":"检验分组",

"NODEID":"8",

"SLAVENO":" 01",

"SLAVENAME":"血液体液",

"PARENTID":"- 1",

"SLAVECODE":"01",

"MEMCODE1":"XYTY",

"MEMCODE2":"01",

"ORDERNO":"1",

"REMARK":" ",

"CHILDNODES":[

{

			"HOSPITALCODE":"9999",

"SUBSYSCODE":"LIMS",

"CLASSCODE":"检验分组",

"NODEID":"12",

"SLAVENO":"0101",

"SLAVENAME":"血液检查",

"PARENTID":"8",

"SLAVECODE":"01.01",

"MEMCODE1":"XYJC",

"MEMCODE2":"0101",

"ORDERNO":"2",

"REMARK":"",

"CHILDNODES":null

	},

{

		"HOSPITALCODE":"9999",

"SUBSYSCODE":"LIMS",

"CLASSCODE":"检验分组",

"NODEID":"13",

"SLAVENO":"0102",

"SLAVENAME":"尿液检查",

"PARENTID":"8",

"SLAVECODE":"01.02",

"MEMCODE1":"NYJC",

"MEMCODE2":"0102",

"ORDERNO":"2",

"REMARK":"",

"CHILDNODES":null

	}

]

},

{

   "HOSPITALCODE":"9999",

"SUBSYSCODE":"LIMS",

"CLASSCODE":"检验分组",

"NODEID":"10",

"SLAVENO":" 02",

"SLAVENAME":"临床化学",

"PARENTID":"- 1",

"SLAVECODE":"02",

"MEMCODE1":"LCHX",

"MEMCODE2":"02",

"ORDERNO":"1",

"REMARK":" ",

"CHILDNODES":null

}

]

代码实现：

判断对应项目分类的存在状态，已经删除提示前端，否则下一步

更新表LIS_TREEDICT

获取LIS_TREEDICT表中项目分类数据

表结构：LIS_TREEDICT

#### B04	获取项目分类的检验项目集合

接口说明：单击项目分类获取对应检测项目集合

请求URL：../item/request/getitemtypelist

代码文件：winning.lis.item.service.itemService

示例URL：http://192.168.11.211:15011/lis/item/request/getitemtypelist?hospitalcode=& subsyscode=&classcode=&nodeid=8

业务场景：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码 |
| 2 | slavecode | 编码规则编号 | String | 不允许为空，传入对应节点的规则编号，如：01.01 |
| 3 | subsyscode | 子系统代码 | string | 传入子系统代码，允许为空
传入为空时默认为LIMS |
| 4 | classcode | 字典分类 | string | 传入项目字典分类，如：检验分组 |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- | --- |
| 节点名称：itemlist 汇总信息 类型：array<object> | 节点名称：itemlist 汇总信息 类型：array<object> | 节点名称：itemlist 汇总信息 类型：array<object> | 节点名称：itemlist 汇总信息 类型：array<object> | 节点名称：itemlist 汇总信息 类型：array<object> | 节点名称：itemlist 汇总信息 类型：array<object> |
| 1 | HOSPITALCODE | 医疗机构代码 | 医疗机构代码 | string | 如：9999 |
| 2 | ITEMCODE | 项目代码 | 项目代码 | string | 如：#Bas |
| 3 | ITEMNAME | 项目名称 | 项目名称 | string | 如; 嗜碱性细胞数 |
| 4 | ITEMNUM | 标准编码 | 标准编码 | string | 如：#Bas |
| 5 | ENGSHORTNAME | 英文缩写 | 英文缩写 | string | 如：JC |
| 6 | ENGNAME | 英文名称 | 英文名称 | string | 如：WBC |
| 7 | LOINCCODE | LOINC编码 | LOINC编码 | string | 如： |
| 8 | EXAMCODE | 检验分类 | 检验分类 | string | 如：3 |
| 9 | EXAMNAME | 检验分类名称 | 检验分类名称 | string | 如：免疫 |
| 10 | RESULTTYPE | 结果类型 | 结果类型 | string | 如：3 |
| 11 | RESULTTYPEDES | 结果类型描述 | 结果类型描述 | string | 如：阴阳 |
| 12 | DEFVALUE | 缺省值 | 缺省值 | string | 如：阴性 |
| 13 | SAMPLECODE | 标本种类 | 标本种类 | string | 如：10 |
| 14 | SAMPLENAME | 标本名称 | 标本名称 | string | 如：关节腔液 |
| 15 | MEMCODE1 | 输入码一 | 输入码一 | string | 如：SJXXBS |
| 16 | MEMCODE2 | 输入码二 | 输入码二 | string | 如：245d |
| 17 | REMARK | 备注说明 | 备注说明 | string | 如：项目再用 |
| 18 | PREVALUE | 精度 | 精度 | string | 如：0.01 |
| 19 | ITEMPRICE | 单价 | 单价 | string | 如：14.5 |
| 20 | ITEMINFO | 项目说明 | 项目说明 | string | 如：血常规项目 |
| 21 | MEANOFCLINIC | 临床意义 | 临床意义 | string | 如：无 |
| 22 | ITEMTYPEID | 项目分类ID | 项目分类ID | string | 项目分类ID，对应lis_treediect的nodeid |
| 23 | STOPFLAG | 项目停用标志 | 项目停用标志 | string | 标识该项目是否停用，0-启用，1-停用 |



代码实现：

判断项目分类在表中的状态

依据条件nodeid查询 LIS_ITEM，获取对应的项目分类的检验项目集合

表结构：LIS_ITEM/SLAVE

#### B05 新增检验项目

接口说明：子节点下检验项目添加

请求URL：../item/request/addexamitem

代码文件：winning.lis.item.service.itemService

示例URL：http://192.168.11.211:15011/lis/item/request/addexamitem?hospitalcode=& nodeid=8&itemcode=121111&itemname=嗜碱性细胞数&engshortname=#Bas&itemnum=#Bas

&engname=#Bas&memcode1=SJXSBS&remark=

业务场景：





接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | itemcode | 项目代码 | string | 不允许为空，如：121111 |
| 2 | itemname | 项目名称 | string | 不允许为空，如：嗜碱性细胞数 |
| 3 | engshortname | 英文缩写 | string | 不允许为空，如：#Bas |
| 4 | engname | 英文名称 | string | 如：#Bas |
| 5 | memcode1 | 拼音码 | string | 如：SJXSBS |
| 6 | remark | 备注说明 | string | 如： |
| 7 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码 |
| 8 | nodeid | 项目分类的ID | string | 不允许为空，传入对应节点的项目分类ID，如：8 |
| 9 | itemnum | 项目标准编码 | string | 不允许为空，如：#Bas |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- | --- |
| 节点名称：iteminfo 汇总信息 类型：object | 节点名称：iteminfo 汇总信息 类型：object | 节点名称：iteminfo 汇总信息 类型：object | 节点名称：iteminfo 汇总信息 类型：object | 节点名称：iteminfo 汇总信息 类型：object | 节点名称：iteminfo 汇总信息 类型：object |
| 1 | HOSPITALCODE | 医疗机构代码 | 医疗机构代码 | string | 如：9999 |
| 2 | ITEMCODE | 项目代码 | 项目代码 | string | 如：#Bas |
| 3 | ITEMNAME | 项目名称 | 项目名称 | string | 如; 嗜碱性细胞数 |
| 4 | ITEMNUM | 标准编码 | 标准编码 | string | 如：#Bas |
| 5 | ENGSHORTNAME | 英文缩写 | 英文缩写 | string | 如：JC |
| 6 | ENGNAME | 英文名称 | 英文名称 | string | 如：WBC |
| 7 | LOINCCODE | LOINC编码 | LOINC编码 | string | 如： |
| 8 | EXAMCODE | 检验分类 | 检验分类 | string | 如：3 |
| 9 | EXAMNAME | 检验分类名称 | 检验分类名称 | string | 如：免疫 |
| 10 | RESULTTYPE | 结果类型 | 结果类型 | string | 如：3 |
| 11 | RESULTTYPEDES | 结果类型描述 | 结果类型描述 | string | 如：阴阳 |
| 12 | DEFVALUE | 缺省值 | 缺省值 | string | 如：阴性 |
| 13 | SAMPLECODE | 标本种类 | 标本种类 | string | 如：10 |
| 14 | SAMPLENAME | 标本名称 | 标本名称 | string | 如：关节腔液 |
| 15 | MEMCODE1 | 输入码一 | 输入码一 | string | 如：SJXXBS |
| 16 | MEMCODE2 | 输入码二 | 输入码二 | string | 如：245d |
| 17 | REMARK | 备注说明 | 备注说明 | string | 如：项目再用 |
| 18 | PREVALUE | 精度 | 精度 | string | 如：0.01 |
| 19 | ITEMPRICE | 单价 | 单价 | string | 如：14.5 |
| 20 | ITEMINFO | 项目说明 | 项目说明 | string | 如：血常规项目 |
| 21 | MEANOFCLINIC | 临床意义 | 临床意义 | string | 如：无 |
| 22 | ITEMTYPEID | 项目分类ID | 项目分类ID | string | 项目分类ID，对应lis_treediect的nodeid |
| 23 | STOPFLAG | 项目停用标志 | 项目停用标志 | string | 标识该项目是否停用，0-启用，1-停用 |



代码实现：

验证要添加的项目代码在LIS_ITEM表中是否存在，存在提示前端，否则插入LIS_ITEM

获取对应修改项目的信息

表结构：LIS_ITEM/SLAVE

#### B06	检验项目修改

接口说明：检验项目修改

请求URL：../item/request/alterexamitem

代码文件：winning.lis.item.service.itemService

示例URL：http://192.168.11.211:15011/lis/item/request/addexamitem?hospitalcode=& nodeid=8&itemcode=121111&itemname=嗜碱性细胞数&engshortname=#Bas&itemnum=#Bas

&engname=#Bas&memcode1=SJXSBS&examcode=白带常规&remark=&inititemcode=121111

业务场景：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | itemcode | 修改后项目代码 | string | 不允许为空，如：121111 |
| 2 | itemname | 修改后项目名称 | string | 不允许为空，如：嗜碱性细胞数 |
| 3 | engshortname | 修改后英文缩写 | string | 不允许为空，如：#Bas |
| 4 | engname | 修改后英文名称 | string | 如：#Bas |
| 5 | memcode1 | 修改后拼音码 | string | 如：SJXSBS |
| 6 | examcode | 修改后组合归类 | string | 如：白带常规 |
| 7 | remark | 修改后备注说明 | string | 如： |
| 8 | itemnum | 项目标准编码 | string | 不允许为空，如：#Bas |
| 9 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码 |
| 10 | nodeid | 修改后项目分类的ID | string | 不允许为空，传入对应节点的项目分类ID，如：8 |
| 11 | inititemcode | 修改前的项目代码 | string | 不允许为空，如：12111 |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- | --- |
| 节点名称：iteminfo 汇总信息 类型：object | 节点名称：iteminfo 汇总信息 类型：object | 节点名称：iteminfo 汇总信息 类型：object | 节点名称：iteminfo 汇总信息 类型：object | 节点名称：iteminfo 汇总信息 类型：object | 节点名称：iteminfo 汇总信息 类型：object |
| 1 | HOSPITALCODE | 医疗机构代码 | 医疗机构代码 | string | 如：9999 |
| 2 | ITEMCODE | 项目代码 | 项目代码 | string | 如：#Bas |
| 3 | ITEMNAME | 项目名称 | 项目名称 | string | 如; 嗜碱性细胞数 |
| 4 | ITEMNUM | 标准编码 | 标准编码 | string | 如：#Bas |
| 5 | ENGSHORTNAME | 英文缩写 | 英文缩写 | string | 如：JC |
| 6 | ENGNAME | 英文名称 | 英文名称 | string | 如：WBC |
| 7 | LOINCCODE | LOINC编码 | LOINC编码 | string | 如： |
| 8 | EXAMCODE | 检验分类 | 检验分类 | string | 如：3 |
| 9 | EXAMNAME | 检验分类名称 | 检验分类名称 | string | 如：免疫 |
| 10 | RESULTTYPE | 结果类型 | 结果类型 | string | 如：3 |
| 11 | RESULTTYPEDES | 结果类型描述 | 结果类型描述 | string | 如：阴阳 |
| 12 | DEFVALUE | 缺省值 | 缺省值 | string | 如：阴性 |
| 13 | SAMPLECODE | 标本种类 | 标本种类 | string | 如：10 |
| 14 | SAMPLENAME | 标本名称 | 标本名称 | string | 如：关节腔液 |
| 15 | MEMCODE1 | 输入码一 | 输入码一 | string | 如：SJXXBS |
| 16 | MEMCODE2 | 输入码二 | 输入码二 | string | 如：245d |
| 17 | REMARK | 备注说明 | 备注说明 | string | 如：项目再用 |
| 18 | PREVALUE | 精度 | 精度 | string | 如：0.01 |
| 19 | ITEMPRICE | 单价 | 单价 | string | 如：14.5 |
| 20 | ITEMINFO | 项目说明 | 项目说明 | string | 如：血常规项目 |
| 21 | MEANOFCLINIC | 临床意义 | 临床意义 | string | 如：无 |
| 22 | ITEMTYPEID | 项目分类ID | 项目分类ID | string | 项目分类ID，对应lis_treediect的nodeid |
| 23 | STOPFLAG | 项目停用标志 | 项目停用标志 | string | 标识该项目是否停用，0-启用，1-停用 |



代码实现：

验证修改后的项目代码和类别在LIS_ITEM表中是否存在，存在提示前端，否则更新LIS_ITEM

获取对应修改项目的信息

表结构：LIS_ITEM/SLAVE

#### B07	检验项目停用与启用

接口说明：检验项目停用与启用（颜色区分停用与启用状态）

请求URL：../item/request/setitemstatus

代码文件：winning.lis.item.service.itemService

示例URL：

http://192.168.11.211:15011/lis/item/request/setitemstatus?hospitalcode=9999&stopflag=true&itemcode=12001

业务场景：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码 |
| 2 | itemcode | 项目代码 | string | 不允许为空，如：121111 |
| 3 | stopflag | 项目停用启用标识 | string | 允许为空，默认为0，0-表示启用，1-停用 |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage |
| 1 | type | 消息类型 | string | SUCCESS-成功 
ERROR-系统出现异常或错误
FAILT-业务逻辑处理失败 |
| 2 | messageCode | 消息代码 | string | 用于区分消息代码 |
| 3 | message | 消息内容 | string | type=ERROR时则为系统错误消息提示内容
type=FAILT时则为业务提示信息 |
| 4 | link | URL链接 | string | 用于提示用户后可以点击查看详情信息，默认为空 |
| 5 | data | 数据集合 | object | 用于存储返回的数据 |



代码实现： 

判断对应检验项目的存在

更新表LIS_ITEM

返回处理结果

表结构：LIS_ITEM

#### B08	分类代码自动获取

接口说明：分类代码自动获取

请求URL：../item/request/getitemtypenum

代码文件：winning.lis.item.service.itemService

示例URL：

http://192.168.11.211:15011/lis/item/request/getitemtypenum?hospitalcode=9999&stopflag=true&itemcode=12001

业务场景：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码 |
| 2 | classcode | 字典分类 | string | 传入字典分类代码，允许为空
传入为空时获取当前字典分类代码 |
| 3 | subsyscode | 子系统代码 | string | 传入子系统代码，允许为空
传入为空时默认为LIMS |
| 4 | parentid | 父节点ID | string | 不允许为空，传入项目父级节点，没有传入-1 |
| 5 | nodeid | 当前节点ID | string | 不允许为空，没有时传-1，否则为当前节点的nodeid |
| 6 | level | 添加级别 | string | 允许为空，0-同级添加，1-下级添加，默认为0 |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- | --- |
| 节点名称：itemtypenum 汇总信息 类型：object | 节点名称：itemtypenum 汇总信息 类型：object | 节点名称：itemtypenum 汇总信息 类型：object | 节点名称：itemtypenum 汇总信息 类型：object | 节点名称：itemtypenum 汇总信息 类型：object | 节点名称：itemtypenum 汇总信息 类型：object |
| 1 | SLAVENO | 字典代码 | 字典代码 | string | 如：01 |
| 2 | SLAVECODE | 编码规则 | 编码规则 | string | 如：01.01 |
| 3 | PARENTID | 父级节点ID | 父级节点ID | string | 如：117 |



代码实现：

根据条件获取最大的字典代码，对编码号加一后返回，并生成对应的编码规则

表结构：LIS_ITEM

#### B09	项目检索

接口说明：检验项目的检索

请求URL：../item/request/searchitems

代码文件：winning.lis.item.service.itemService

示例URL：

http://192.168.11.211:15011/lis/item/request/searchtitems?hospitalcode=9999&searchfield= 12001

业务场景：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码 |
| 2 | searchfield | 检索字段 | string | 不允许为空，如：121 |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- | --- |
| 节点名称：itemlist 汇总信息 类型：array<object> | 节点名称：itemlist 汇总信息 类型：array<object> | 节点名称：itemlist 汇总信息 类型：array<object> | 节点名称：itemlist 汇总信息 类型：array<object> | 节点名称：itemlist 汇总信息 类型：array<object> | 节点名称：itemlist 汇总信息 类型：array<object> |
| 1 | HOSPITALCODE | 医疗机构代码 | 医疗机构代码 | string | 如：9999 |
| 2 | ITEMCODE | 项目代码 | 项目代码 | string | 如：#Bas |
| 3 | ITEMNAME | 项目名称 | 项目名称 | string | 如; 嗜碱性细胞数 |
| 4 | ITEMNUM | 标准编码 | 标准编码 | string | 如：#Bas |
| 5 | ENGSHORTNAME | 英文缩写 | 英文缩写 | string | 如：JC |
| 6 | ENGNAME | 英文名称 | 英文名称 | string | 如：WBC |
| 7 | LOINCCODE | LOINC编码 | LOINC编码 | string | 如： |
| 8 | EXAMCODE | 检验分类 | 检验分类 | string | 如：3 |
| 9 | EXAMNAME | 检验分类名称 | 检验分类名称 | string | 如：免疫 |
| 10 | RESULTTYPE | 结果类型 | 结果类型 | string | 如：3 |
| 11 | RESULTTYPEDES | 结果类型描述 | 结果类型描述 | string | 如：阴阳 |
| 12 | DEFVALUE | 缺省值 | 缺省值 | string | 如：阴性 |
| 13 | SAMPLECODE | 标本种类 | 标本种类 | string | 如：10 |
| 14 | SAMPLENAME | 标本名称 | 标本名称 | string | 如：关节腔液 |
| 15 | MEMCODE1 | 输入码一 | 输入码一 | string | 如：SJXXBS |
| 16 | MEMCODE2 | 输入码二 | 输入码二 | string | 如：245d |
| 17 | REMARK | 备注说明 | 备注说明 | string | 如：项目再用 |
| 18 | PREVALUE | 精度 | 精度 | string | 如：0.01 |
| 19 | ITEMPRICE | 单价 | 单价 | string | 如：14.5 |
| 20 | ITEMINFO | 项目说明 | 项目说明 | string | 如：血常规项目 |
| 21 | MEANOFCLINIC | 临床意义 | 临床意义 | string | 如：无 |
| 22 | ITEMTYPEID | 项目分类ID | 项目分类ID | string | 项目分类ID，对应lis_treediect的nodeid |
| 23 | STOPFLAG | 项目停用标志 | 项目停用标志 | string | 标识该项目是否停用，0-启用，1-停用 |



代码实现：

依据条件searchfield查询 LIS_ITEM，获取对应的项目分类的检验项目集合

表结构：LIS_ITEM/SLAVE

#### B10	项目使用情况

接口说明：项目使用情况 

请求URL：../item/request/getitemuseinfo

代码文件：winning.lis.item.service.itemService

示例URL：

http://192.168.10.73:10004/lis/item/request/getitemuseinfo?hospitalcode=9999&itemcode=CHE&itemname=胆碱酯酶

业务场景：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码 |
| 2 | itemcode | 项目代码 | string | 不允许为空，如：121 |
| 3 | itemname | 项目名称 | string | 不允许为空，如：血小板分布宽度 |
| 4 | itemnum | 标准编码 | string | 允许为空，如：#VA |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- | --- |
| 节点名称：institeminfo 汇总信息 类型：array<object> | 节点名称：institeminfo 汇总信息 类型：array<object> | 节点名称：institeminfo 汇总信息 类型：array<object> | 节点名称：institeminfo 汇总信息 类型：array<object> | 节点名称：institeminfo 汇总信息 类型：array<object> | 节点名称：institeminfo 汇总信息 类型：array<object> |
| 1 | HOSPITALCODE | 医疗机构代码 | 医疗机构代码 | string | 如：9999 |
| 2 | ITEMCODE | 项目代码 | 项目代码 | string | 如; 132511 |
| 3 | ITEMNAME | 项目名称 | 项目名称 | string | 如; 嗜碱性细胞数 |
| 4 | INSTID | 仪器ID | 仪器ID | string | 如：3 |
| 5 | INSTCODE | 仪器编码 | 仪器编码 | string | 如：HX-21 |
| 6 | INSTNAME | 仪器名称 | 仪器名称 | string | 如：HX-21 |
| 7 | EXAMCODE | 检验分类 | 检验分类 | string | 如：3 |
| 8 | EXAMNAME | 检验分类名称 | 检验分类名称 | string | 如：免疫 |
| 9 | INSTGROUP | 专业组ID | 专业组ID | string | 如：3 |
| 10 | INSTGROUPNAME | 专业组名称 | 专业组名称 | string | 如：临检组 |



代码实现：

依据条件itemcode查询 LIS_INSTITEM，SLAVE表获取检验分类信息和专业组信息

表结构：LIS_INSTITEM/SLAVE

#### B11	获取项目对应仪器参考值

接口说明：获取项目对应仪器的参考值 

请求URL：../item/request/getitemnormal

代码文件：winning.lis.item.service.itemService

示例URL：

http://192.168.10.73:10004/lis/item/request/getitemnormal?hospitalcode=9999&itemcode=CHE&itemname=胆碱酯酶&itemnum=CHE&instid=10010

业务场景：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码 |
| 2 | itemcode | 项目代码 | string | 不允许为空，如：121 |
| 3 | itemname | 项目名称 | string | 不允许为空，如：血小板分布宽度 |
| 4 | itemnum | 项目标准编码 | string | 不允许为空，如：#BDD |
| 5 | instid | 仪器ID | string | 不允许为空，如：3 |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称： itemnormal 汇总信息 类型：array<object> | 节点名称： itemnormal 汇总信息 类型：array<object> | 节点名称： itemnormal 汇总信息 类型：array<object> | 节点名称： itemnormal 汇总信息 类型：array<object> | 节点名称： itemnormal 汇总信息 类型：array<object> |
| 1 | ITEMCODE | 项目代码 | string | 如：9999 |
| 2 | ITEMNAME | 项目名称 | string | 如： NORMAL-常规参考值 |
| 3 | ITEMNUM | 项目代码 |  |  |
| 4 | REFERTYPE | 参考值描述 | string | 如：5-8 |
| 5 | REFERTYPEDESC | 标本描述 | string | 如：标本：血清 年龄：2-8 |



代码实现：

参考值分为默认值和特殊参考值两种，默认值在表LIS_INSTITEM中，特殊参考值在LIS_ITEMREFERANCE表中，其中REFERTYPE为参考值类型，COM_EFFCON_LIST为参考值的条件表，关联字段为EFFCONID 

表结构： LIS_INSTITEM\LIS_ITEMREFERANCE\COM_EFFCON_LIST

#### B12	获取项目对应仪器危急值

接口说明：获取项目对应仪器的危急值 

请求URL：../item/request/getitempanic

代码文件：winning.lis.item.service.itemService

示例URL：

http://192.168.10.73:10004/lis/item/request/getitempanic?hospitalcode=9999&itemcode=CHE&itemname=胆碱酯酶&itemnum=CHE&instid=10010

业务场景：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码 |
| 2 | itemcode | 项目代码 | string | 不允许为空，如：121 |
| 3 | itemname | 项目名称 | string | 不允许为空，如：血小板分布宽度 |
| 4 | itemnum | 项目标准编码 | string | 不允许为空，如：#BDD |
| 5 | instid | 仪器ID | string | 不允许为空，如：3 |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称： itemnormal 汇总信息 类型：array<object> | 节点名称： itemnormal 汇总信息 类型：array<object> | 节点名称： itemnormal 汇总信息 类型：array<object> | 节点名称： itemnormal 汇总信息 类型：array<object> | 节点名称： itemnormal 汇总信息 类型：array<object> |
| 1 | ITEMCODE | 项目代码 | string | 如：9999 |
| 2 | ITEMNAME | 项目名称 | string | 如： NORMAL-常规参考值 |
| 3 | ITEMNUM | 项目代码 |  |  |
| 4 | REFERTYPE | 危急值描述 | string | 如：5-8 |
| 5 | REFERTYPEDESC | 标本描述 | string | 如：标本：血清 年龄：2-8 |



代码实现：

危急值分为默认值和特殊危急值两种，默认值在表LIS_INSTITEM中，特殊危急值在LIS_ITEMREFERANCE表中，其中REFERTYPE为危急值，COM_EFFCON_LIST为危急值的条件表，关联字段为EFFCONID 

表结构： LIS_INSTITEM\LIS_ITEMREFERANCE\COM_EFFCON_LIST

#### B13	获取项目对应仪器传染病

接口说明：获取项目对应仪器传染病

请求URL：../item/request/getiteminfect

代码文件：winning.lis.item.service.itemService

示例URL：

http://192.168.10.73:10004/lis/item/request/getiteminfect?hospitalcode=9999&itemcode=CHE&itemname=胆碱酯酶&itemnum=CHE&instid=10010

业务场景：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码 |
| 2 | itemcode | 项目代码 | string | 不允许为空，如：121 |
| 3 | itemname | 项目名称 | string | 不允许为空，如：血小板分布宽度 |
| 4 | itemnum | 项目标准编码 | string | 不允许为空，如：#BDD |
| 5 | instid | 仪器ID | string | 不允许为空，如：3 |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称： itemnormal 汇总信息 类型：array<object> | 节点名称： itemnormal 汇总信息 类型：array<object> | 节点名称： itemnormal 汇总信息 类型：array<object> | 节点名称： itemnormal 汇总信息 类型：array<object> | 节点名称： itemnormal 汇总信息 类型：array<object> |
| 1 | ITEMCODE | 项目代码 | string | 如：9999 |
| 2 | ITEMNAME | 项目名称 | string | 如： NORMAL-常规参考值 |
| 3 | ITEMNUM | 项目代码 |  |  |
| 4 | REFERTYPE | 危急值描述 | string | 如：5-8 |
| 5 | REFERTYPEDESC | 标本描述 | string | 如：标本：血清 年龄：2-8 |



代码实现：

传染病值在LIS_ITEMREFERANCE表中，其中REFERTYPE为危急值，COM_EFFCON_LIST为危急值的条件表，关联字段为EFFCONID 

表结构： LIS_ITEMREFERANCE

#### B14	获取项目对应仪器的计算公式

接口说明：获取项目对应仪器的计算公式

请求URL：../item/request/getitemformular

代码文件：winning.lis.item.service.itemService

示例URL：

http://192.168.11.211:15011/lis/item/request/getitemformular?hospitalcode=9999&itemcode= 12001& itemname=血小板分布宽度

业务场景：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码 |
| 2 | itemcode | 项目代码 | string | 不允许为空，如：121 |
| 3 | itemname | 项目名称 | string | 不允许为空，如：血小板分布宽度 |
| 4 | itemnum | 项目标准编码 | string | 不允许为空，如：#BDD |
| 5 | instid | 仪器ID | string | 不允许为空，如：3 |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称： itemnormal 汇总信息 类型：array<object> | 节点名称： itemnormal 汇总信息 类型：array<object> | 节点名称： itemnormal 汇总信息 类型：array<object> | 节点名称： itemnormal 汇总信息 类型：array<object> | 节点名称： itemnormal 汇总信息 类型：array<object> |
| 1 | ITEMCODE | 项目代码 | string | 如：9999 |
| 2 | ITEMNAME | 项目名称 | string | 如： NORMAL-常规参考值 |
| 3 | ITEMNUM | 项目代码 |  |  |
| 4 | REFERTYPE | 危急值描述 | string | 如：5-8 |
| 5 | REFERTYPEDESC | 标本描述 | string | 如：标本：血清 年龄：2-8 |



代码实现：

传染病值在LIS_FORMULAR表中， 

表结构： LIS_FORMULAR

#### B15	获取项目对应仪器的结果转义

接口说明：获取项目对应仪器的结果转义

请求URL：../item/request/getitemchangemean

代码文件：winning.lis.item.service.itemService

示例URL：

http://192.168.10.73:10004/lis/item/request/getitemchangemean?hospitalcode=9999&itemcode=CHE&itemname=胆碱酯酶&instid=10010

业务场景：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码 |
| 2 | itemcode | 项目代码 | string | 不允许为空，如：121 |
| 3 | itemname | 项目名称 | string | 不允许为空，如：血小板分布宽度 |
| 4 | itemnum | 项目标准编码 | string | 不允许为空，如：#BDD |
| 5 | instid | 仪器ID | string | 不允许为空，如：3 |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称： itemnormal 汇总信息 类型：array<object> | 节点名称： itemnormal 汇总信息 类型：array<object> | 节点名称： itemnormal 汇总信息 类型：array<object> | 节点名称： itemnormal 汇总信息 类型：array<object> | 节点名称： itemnormal 汇总信息 类型：array<object> |
| 1 | ITEMCODE | 项目代码 | string | 如：9999 |
| 2 | ITEMNAME | 项目名称 | string | 如： NORMAL-常规参考值 |
| 3 | ITEMNUM | 项目代码 | string | 如：CHE |
| 4 | INSTID | 仪器ID | string | 如：5-8 |
| 5 | SYMBOL | 标本描述 | string | 如：标注值（结果值） |
| 6 | ATTRIBE | 特性 | string | 特性 0:未标注 1:左标注 2:右标注 |
| 7 | ATTRIBEMARK | 特征标识 | string | 0:无标识，1:[],2:【】,3:{},4:(),5:<>,6:《》 |
| 8 | CHARREFER | 字符参考 | string | 如：xde |
| 9 | LOWLIMIT | 下限 | string | 如：22 |
| 10 | UPPLIMIT | 上限 | string | 如：29 |



代码实现：

传染病值在LIS_CHANGEMEAN表中， 

表结构：LIS_CHANGEMEAN

#### B16	获取项目对应仪器的组合

接口说明：获取项目对应仪器的组合

请求URL：../item/request/getitemgroup

代码文件：winning.lis.item.service.itemService

示例URL：

http://192.168.11.211:15011/lis/item/request/getitemgroup?hospitalcode=9999&itemcode= 12001& itemname=血小板分布宽度

业务场景：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码 |
| 2 | itemcode | 项目代码 | string | 不允许为空，如：121 |
| 3 | itemname | 项目名称 | string | 不允许为空，如：血小板分布宽度 |
| 4 | instid | 仪器ID | string | 不允许为空，如：3 |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称： itemnormal 汇总信息 类型：array<object> | 节点名称： itemnormal 汇总信息 类型：array<object> | 节点名称： itemnormal 汇总信息 类型：array<object> | 节点名称： itemnormal 汇总信息 类型：array<object> | 节点名称： itemnormal 汇总信息 类型：array<object> |
| 1 | HOSPITALCODE | 医疗机构 | string | 如：9999 |
| 2 | GROUPCODE | 组合代码 | string | 如： 762 |
| 3 | GROUPNAME | 项目名称 | string | 如白带常规 |
| 4 | MEMCODE1 | 输入码一 | string | 如：5-8 |
| 5 | MEMCODE2 | 输入码二 | string | 年龄：2-8 |
| 6 | EXAMCODE | 检验分类 | string | 如：2 |
| 7 | EXAMCODENAME | 检验分类名称 | string | 如：临检 |
| 8 | SAMPLECODE | 默认标本 | string | 如：血清 |
| 9 | ITEMCODE | 项目代码 | string | 如：CHE |



代码实现：

LIS_GROUPITEM表中获取组合明细，LIS_GROUPITEMDETAIL过滤本项目的信息，SLAVE中获取检验分类名称 

表结构：LIS_GROUPITEM\LIS_GROUPITEMDETAIL\SLAVE

## 仪器配置

### V 模块接口视图

| NO. | 接口编号 | 接口名称 | 说明 |
| --- | --- | --- | --- |
| 1 | A01 | 仪器下拉框初始化
../instrumentitem/request/getinitdata | 仪器下拉框初始化 |
| 2 | B01 | 获取仪器项目列表
../instrumentitem/request/getinstitemlist | 获取仪器项目列表 |
| 3 | B02 | 修改仪器项目
../instrumentitem/request/alterinstitem | 修改仪器项目 |
| 4 | B03 | 删除仪器项目
../instrumentitem/request/deleteinstitem | 删除仪器项目 |
| 5 | B04 | 修改仪器项目顺序（后期确认）
../instrumentitem/request/alterinstitem | 修改仪器项目顺序 |
| 6 | B05 | 获取仪器项目参考值
../instrumentitem/request/getinstitemnormal | 获取仪器项目参考值 |
| 7 | B06 | 修改仪器项目默认参考值
../instrumentitem/request/alterdefaultnormal | 修改仪器项目默认参考值 |
| 8 | B07 | 修改仪器项目特殊参考值
../instrumentitem/request/alterothernormal | 修改仪器项目特殊参考值 |
| 9 | B08 | 删除仪器项目特殊参考值
../instrumentitem/request/deleteothernormal | 删除仪器项目特殊参考值 |
| 10 | B09 | 添加仪器项目特殊参考值
../instrumentitem/request/addothernormal | 添加仪器项目特殊参考值 |



### A 对外公布方法

#### A01	下拉框信息初始化

接口说明：下来框信息初始化

请求URL：../instrumentitem/request/getinitdata

代码文件：winning.lis.instrumentitem.service.InstrumentItemService

示例URL： 

原型参考：





接口入参： 

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码 |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：sampletype项目结果类型 类型： array<object> | 节点名称：sampletype项目结果类型 类型： array<object> | 节点名称：sampletype项目结果类型 类型： array<object> | 节点名称：sampletype项目结果类型 类型： array<object> | 节点名称：sampletype项目结果类型 类型： array<object> |
| 1 | CODE | 字典代码 | string | 如：1 |
| 2 | NAME | 字典名称 | string | 如：血 |
| 3 | MEMCODE1 | 输入码一 | string | 如：1 |
| 4 | MEMCODE2 | 输入码二 | string | 如：X |
| 5 | DICID | 标准编码 | string | 如：1 |
| 6 | DICTYPE | 字典分类 | string | 如：样本类型 |
| 7 | SUBSYSCODE | 子系统分类 | string | 如：LIMS |
| 8 | EXTERNCODE | 外部代码 | string | 如：10 |
| 9 | ORDERNO | 排序 | string | 如：0 |
| 10 | RESERVEFIELD1 | 扩展字段信息 | string | 如：null |
| 节点名称：unit 项目单位   类型： array<object> | 节点名称：unit 项目单位   类型： array<object> | 节点名称：unit 项目单位   类型： array<object> | 节点名称：unit 项目单位   类型： array<object> | 节点名称：unit 项目单位   类型： array<object> |
| 字段同上 | 字段同上 | 字段同上 | 字段同上 | 字段同上 |
| 节点名称：sex 性别   类型： array<object> | 节点名称：sex 性别   类型： array<object> | 节点名称：sex 性别   类型： array<object> | 节点名称：sex 性别   类型： array<object> | 节点名称：sex 性别   类型： array<object> |
| 字段同上 | 字段同上 | 字段同上 | 字段同上 | 字段同上 |
| 节点名称：applydeptcode  申请科室   类型： array<object> | 节点名称：applydeptcode  申请科室   类型： array<object> | 节点名称：applydeptcode  申请科室   类型： array<object> | 节点名称：applydeptcode  申请科室   类型： array<object> | 节点名称：applydeptcode  申请科室   类型： array<object> |
| 字段同上 | 字段同上 | 字段同上 | 字段同上 | 字段同上 |
| 节点名称：ward     病区类型： array<object> | 节点名称：ward     病区类型： array<object> | 节点名称：ward     病区类型： array<object> | 节点名称：ward     病区类型： array<object> | 节点名称：ward     病区类型： array<object> |
| 字段同上 | 字段同上 | 字段同上 | 字段同上 | 字段同上 |
| 节点名称：samplecode   标本种类   类型： array<object> | 节点名称：samplecode   标本种类   类型： array<object> | 节点名称：samplecode   标本种类   类型： array<object> | 节点名称：samplecode   标本种类   类型： array<object> | 节点名称：samplecode   标本种类   类型： array<object> |
| 字段同上 | 字段同上 | 字段同上 | 字段同上 | 字段同上 |
| 节点名称：wardorreg  病人类型   类型： array<object> | 节点名称：wardorreg  病人类型   类型： array<object> | 节点名称：wardorreg  病人类型   类型： array<object> | 节点名称：wardorreg  病人类型   类型： array<object> | 节点名称：wardorreg  病人类型   类型： array<object> |
| 字段同上 | 字段同上 | 字段同上 | 字段同上 | 字段同上 |
| 节点名称：patpropname   病人特征   类型： array<object> | 节点名称：patpropname   病人特征   类型： array<object> | 节点名称：patpropname   病人特征   类型： array<object> | 节点名称：patpropname   病人特征   类型： array<object> | 节点名称：patpropname   病人特征   类型： array<object> |
| 字段同上 | 字段同上 | 字段同上 | 字段同上 | 字段同上 |
| 节点名称：sampledesc  标本说明   类型： array<object> | 节点名称：sampledesc  标本说明   类型： array<object> | 节点名称：sampledesc  标本说明   类型： array<object> | 节点名称：sampledesc  标本说明   类型： array<object> | 节点名称：sampledesc  标本说明   类型： array<object> |
| 字段同上 | 字段同上 | 字段同上 | 字段同上 | 字段同上 |
| 节点名称：clinicdesc   临床诊断   类型： array<object> | 节点名称：clinicdesc   临床诊断   类型： array<object> | 节点名称：clinicdesc   临床诊断   类型： array<object> | 节点名称：clinicdesc   临床诊断   类型： array<object> | 节点名称：clinicdesc   临床诊断   类型： array<object> |
| 字段同上 | 字段同上 | 字段同上 | 字段同上 | 字段同上 |
| 节点名称：resultaccuracy   结果精度   类型： array<object> | 节点名称：resultaccuracy   结果精度   类型： array<object> | 节点名称：resultaccuracy   结果精度   类型： array<object> | 节点名称：resultaccuracy   结果精度   类型： array<object> | 节点名称：resultaccuracy   结果精度   类型： array<object> |
| 字段同上 | 字段同上 | 字段同上 | 字段同上 | 字段同上 |
| 节点名称：testmethod   检测方法   类型： array<object> | 节点名称：testmethod   检测方法   类型： array<object> | 节点名称：testmethod   检测方法   类型： array<object> | 节点名称：testmethod   检测方法   类型： array<object> | 节点名称：testmethod   检测方法   类型： array<object> |
| 字段同上 | 字段同上 | 字段同上 | 字段同上 | 字段同上 |
| 节点名称：resulttype   结果类型   类型： array<object> | 节点名称：resulttype   结果类型   类型： array<object> | 节点名称：resulttype   结果类型   类型： array<object> | 节点名称：resulttype   结果类型   类型： array<object> | 节点名称：resulttype   结果类型   类型： array<object> |
| 字段同上 | 字段同上 | 字段同上 | 字段同上 | 字段同上 |
|  |  |  |  |  |



Json返回数据格式：

代码实现：

直接调用comDicData中公共方法getSlaveByCode方法。

表结构：SLAVE_P\SLAVE

#### A02	检验指标分类初始化-修改

接口说明：检验指标分类初始化 

请求URL：../instrumentitem/request/getsortinitdata

代码文件：winning.lis.instrumentitem.service.InstrumentItemService

示例URL：

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码 |
| 2 | subsyscode | 子系统代码 | string | 传入子系统代码，允许为空
传入为空时默认为LIMS |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | NODEID | 节点编码 | string | 如：ORDERTOITEM_ALL |
| 2 | NODENAME | 节点名称 | string | 如：全部指标 |
| 3 | CHILDNODES | 子节点名称 | array<object> |  |
|  |  |  |  |  |
| 1 | NODEID | 节点编码 | string | 如：ORDERTOITEM_EXECGROUP |
| 2 | NODENAME | 节点名称 | string | 如：检验分组 |
| 3 | CHILDNODES | 子节点名称 | array<object> |  |
| 1 | HOSPITALCODE | 医疗结构 | string | 如：9999 |
| 2 | SUBSYSCODE | 子系统代码 | string | 如：LIMS |
| 3 | CLASSCODE | 字典分类 | string | 如：检验分组 |
| 4 | NODEID | 字典ID | int | 如：8 |
| 5 | SLAVENO | 字典代码 | string | 如：01 |
| 6 | SLAVENAME | 字典名称 | string | 如：血液体液 |
| 7 | PARENTID | 节点ID | int | 节点ID，-1表示根节点 |
| 8 | SLAVECODE | 编码规则 | string | 编码规则 区分层级关系，如：03.01 |
| 9 | MEMCODE1 | 输入码一 | string | 如：XYTY |
| 10 | MEMCODE2 | 输入码二 | string | 如：01 |
| 11 | ORDERNO | 排序 | int | 如：1 |
| 12 | REMARK | 备注说明 | string | 如： |
| 13 | CHILDNODES | 子节点 | array<object> | 子节点数组 |
|  | 子节点的详细信息 | 子节点的详细信息 | 子节点的详细信息 | 子节点的详细信息 |
| 1 | HOSPITALCODE | 医疗结构 | string | 如：9999 |
| 2 | SUBSYSCODE | 子系统代码 | string | 如：LIMS |
| 3 | CLASSCODE | 字典分类 | string | 如：检验分组 |
| 4 | NODEID | 字典ID | int | 如：8 |
| 5 | SLAVENO | 字典代码 | string | 如：01 |
| 6 | SLAVENAME | 字典名称 | string | 如：血液体液 |
| 7 | PARENTID | 节点ID | int | 节点ID，-1表示根节点 |
| 8 | SLAVECODE | 编码规则 | string | 编码规则 区分层级关系，如：03.01 |
| 9 | MEMCODE1 | 输入码一 | string | 如：XYTY |
| 10 | MEMCODE2 | 输入码二 | string | 如：01 |
| 11 | ORDERNO | 排序 | int | 如：1 |
| 12 | REMARK | 备注说明 | string | 如： |
| 13 | FATHERSLAVENO | 上级节点代码 | string | 如：01 |
| 14 | CHILDNODES | 子节点 | array<object> | 子节点数组 |
|  | 子节点的详细信息同上 | 子节点的详细信息同上 | 子节点的详细信息同上 | 子节点的详细信息同上 |
|  |  |  |  |  |
| 1 | NODEID | 节点编码 | string | 如：ORDERTOITEM_ITEMGROPU |
| 2 | NODENAME | 节点名称 | string | 如：仪器分类 |
| 3 | CHILDNODES | 子节点名称 | array<object> |  |
| 1 | INSTID | 仪器ID | string | 如：3，对应INSTID |
| 2 | INSTCODE | 仪器编码 | string | 如：HX-21，INSTCODE |
| 3 | INSTNAME | 仪器名称 | string | 如：HX-21，INSTNAME |
| 4 | EXECGROUPCODE | 执行小组代码 | string | 如：SH |
| 5 | EXECGROUPNAME | 执行小组名称 | string | 如：3 |
|  |  |  |  |  |
| 1 | NODEID | 节点编码 | string | 如：HISITEM_INSTORDER |
| 2 | NODENAME | 节点名称 | string | 如：组合项目 |
| 3 | CHILDNODES | 子节点名称 | array<object> |  |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | GROUPID | 组合模块序号 | int | 如：1 |
| 3 | GROUPCODE | 组合项目代码 | string | 如：bdjj |
| 4 | COMNAME | 组合项目名称 | string | 如; 组合1 |
| 5 | MEMCODE1 | 输入码1 | string | 如：zh |
| 6 | MEMCODE2 | 输入码2 | string | 如：zh |





### B 业务类

#### B01	获取仪器项目列表

接口说明：获取仪器项目列表

请求URL：../instrumentitem/request/getinstitemlist

代码文件：winning.lis.instrumentitem.service.InstrumentItemService

示例URL： http://192.168.10.73:10004/lis/instrumentitem/request/getinstitemlist?hospitalcode=9999&instid=16&instname=大便常规

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | instid | 仪器ID | string | 不允许为空，如：5 |
| 3 | instname | 仪器名称 | string | 不允许为空，如：AU5800 |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | SIZE | 项目数量 | string | 如：10 |
| 节点名称：INFO 项目信息 类型： array<object> | 节点名称：INFO 项目信息 类型： array<object> | 节点名称：INFO 项目信息 类型： array<object> | 节点名称：INFO 项目信息 类型： array<object> | 节点名称：INFO 项目信息 类型： array<object> |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | INSTID | 仪器ID | int | 如：3 |
| 3 | ITEMCODE | 项目代码 | string | 如：0101002 |
| 4 | ITEMNAME | 项目名称 | string | 如：中性粒细胞绝对值 |
| 5 | ITEMNUM | 项目标准编码 | string | 如：NEU# |
| 6 | ENGSHORTNAME | 英文缩写 | string | 如：NEU# |
| 7 | ENGNAME | 英文名称 | int | 如：dd |
| 8 | LOINCCODE | LOINC编码 | string | 如：123 |
| 9 | RESULTTYPE | 结果类型 | string | 如：2 |
| 10 | RESULTTYPENAME | 结果类型名称 | string | 如：字符 |
| 11 | RESULTTYPEFLAG | 结果类型选择 | string | 如：false |
| 12 | INSTITEMCODE | 项目通道号 | string | 如：ZHUMX |
| 13 | EXAMMETHOD | 测试方法 | string | 如：02 |
| 14 | EXAMMETHODFLAG | 测试方法选择 | string | 如：false |
| 15 | PRINTGROUP | 打印分组 | string | 如：A..Z |
| 16 | PRINTORDER | 打印序号 | string | 如：001 |
| 17 | PREVALUE | 精度 | string | 如：001 |
| 18 | PREVALUENAME | 精度名称 | string | 如：1 |
| 19 | PREVALUEFLAG | 精度选择 | string | 如：false |
| 20 | UNIT | 单位 | string | 如：001 |
|  | UNITCODE | 单位代码 | string | 如：001 |
| 21 | UNITFLAG | 单位 | string | 如：false |
| 22 | DEFVALUE | 缺省值（默认结果） | string | 如：001 |
| 23 | CORRECTOR | 校正公式 | string | [X]*100 |
| 24 | WORKLIST | 双工项目 | string | 如：0 |
| 25 | IMPORTANT | 重要项目 | string | 如：0 |
| 26 | ITEMPRICE | 单价 | string | 如：18.5 |
| 27 | REPORTINSTR | 报告仪器 | string | 如：12 |
| 28 | REPORTINSTRNAME | 报告仪器名称 | string | 如：HS-201 |
| 29 | REPORTINSTRFLAG | 报告仪器选择 | string | 如：false |
| 30 | DISPLAYFLAG | 打印标识 | string | 如： 0 |
| 31 | QCEVALUATION | 互认项目 | string | 如： |
| 32 | PRIORITY | 入库优先项目 | string | 如：0 |
| 33 | REQUIRED | 必要项目 | string | 如：0 |
| 34 | HIDEFLAG | 隐藏项目 | string | 如：0 |
| 35 | SCIENTIFICFLAG | 科研标志 | string | 如：0 |



Json返回数据格式：

{

		"SIZE":12,

		"INFO":[

			{

				"HOSPITALCODE":"9999",

				"INSTID":16,

				"INSTITEMCODE":"bxb(db)",

				"ITEMCODE":"bxb(db)",

				"CORRECTOR":"",

				"REQUIRED":"1",

				"EXAMMETHOD":"",

				"WORKLIST":" ",

				"UNIT":"/HP",

				"PRINTGROUP":" ",

				"PRINTORDER":55,

				"REPORTINSTR":null,

				"DISPLAYFLAG":"0",

				"HIDEFLAG":null,

				"PREVALUE":null,

				"DEFVALUE":"阴性",

				"IMPORTANT":null,

				"ITEMPRICE":null,

				"SCIENTIFICFLAG":"0",

				"PRIORITY":null,

				"QCEVALUATION":"0",

				"RESULTTYPE":null,

				"REPORTINSTID":16,

				"REPORTINSTCODE":"DBCG",

				"REPORTINSTNAME":"大便常规",

				"ITEMNAME":"白细胞",

				"ITEMNUM":"bxb(db)",

				"ENGNAME":null,

				"LOINCCODE":null

			}

]

}

代码实现：

检测方法和项目单位存的是名字，不是代码

LIS_INSTITEM表中获取信息，LIS_ITEM获取指标的信息，LIS_INSTRUMENT获取报告仪器的信息

表结构：LIS_INSTRUMENT\LIS_INSTITEM\LIS_ITEM

#### B02	修改仪器项目

接口说明：修改仪器项目

请求URL：../instrumentitem/request/alterinstitem

代码文件：winning.lis.instrumentitem.service.InstrumentItemService

示例URL： http://192.168.10.73:10004/lis/instrumentitem/request/alterinstitem?hospitalcode=9999&instid=16&instname=大便常规&itemcode=hxb(db)&itemname=%Mon&institemcode=%Mon&preinstitemcode=%Mon&iteminfo={"exammethod":"12","printorder":2,"prevalue":"1.0000","unit":"/HP","defvalue":"阴性","corrector":"","worklist":"","important":"0","itemprice":"50.0","reportinstr":"11","displayflag":"0","qcevaluation":"0","priority":1}

原型参考：





接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | instid | 仪器ID | string | 不允许为空，如：5 |
| 3 | instname | 仪器名称 | string | 不允许为空，如：AU5800 |
| 7 | iteminfolist | 修改项目信息 | array<object> |  |
|  |  |  |  |  |
| 1 | itemcode | 项目代码 | string | 不允许为空，如：0101002 |
| 2 | itemname | 项目名称 | string | 不允许为空，如：白细胞计数 |
| 3 | institemcode | 修改后项目通道号 | string | 不允许为空，如：255 |
| 4 | preinstitemcode | 修改前项目通道号 | string | 不允许为空，如：255 |
| 5 | exammethod | 测试方法 | string | 允许为空，如：02 |
| 6 | printorder | 打印序号 | string | 允许为空，如：001 |
| 7 | printgroup | 打印分组 | string | 允许为空，A..Z |
| 8 | prevalue | 精度 | string | 允许为空，如：001 |
| 9 | unit | 单位 | string | 允许为空，如：001 |
| 10 | defvalue | 缺省值（默认结果） | string | 允许为空，如：001 |
| 11 | corrector | 校正公式 | string | 允许为空，如：[X]*100 |
| 12 | worklist | 双工项目 | string | 允许为空，如：0 |
| 13 | important | 重要项目 | string | 允许为空，如：0 |
| 14 | itemprice | 单价 | string | 允许为空，如：18.5 |
| 15 | reportinstr | 报告仪器 | string | 允许为空，如：12 |
| 16 | displayflag | 打印标识 | string | 允许为空，如： 0 |
| 17 | qcevaluation | 互认项目 | string | 允许为空，如： 0 |
| 18 | priority | 入库优先项目 | string | 允许为空，如：0 |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | INSTID | 仪器ID | int | 如：3 |
| 3 | ITEMCODE | 项目代码 | string | 如：0101002 |
| 4 | ITEMNAME | 项目名称 | string | 如：中性粒细胞绝对值 |
| 5 | ITEMNUM | 项目标准编码 | string | 如：NEU# |
| 6 | ENGSHORTNAME | 英文缩写 | string | 如：NEU# |
| 7 | ENGNAME | 英文名称 | int | 如：dd |
| 8 | LOINCCODE | LOINC编码 | string | 如：123 |
| 9 | RESULTTYPE | 结果类型 | string | 如：2 |
| 10 | RESULTTYPE | 结果类型名称 | string | 如：字符 |
| 11 | INSTITEMCODE | 项目通道号 | string | 如：ZHUMX |
| 12 | EXAMMETHOD | 测试方法 | string | 如：02 |
| 13 | PRINTGROUP | 打印分组 | string | A..Z |
| 14 | PRINTORDER | 打印序号 | string | 如：001 |
| 15 | PREVALUE | 精度 | string | 如：001 |
| 16 | UNIT | 单位 | string | 如：001 |
| 17 | DEFVALUE | 缺省值（默认结果） | string | 如：001 |
| 18 | CORRECTOR | 校正公式 | string | [X]*100 |
| 19 | WORKLIST | 双工项目 | string | 如：0 |
| 20 | IMPORTANT | 重要项目 | string | 如：0 |
| 21 | ITEMPRICE | 单价 | string | 如：18.5 |
| 22 | REPORTINSTR | 报告仪器 | string | 如：12 |
| 23 | REPORTINSTRNAME | 报告仪器名称 | string | 如：HS-201 |
| 24 | DISPLAYFLAG | 打印标识 | string | 如： 0 |
| 25 | QCEVALUATION | 互认项目 | string | 如： 0 |
| 26 | PRIORITY | 入库优先项目 | string | 如：0 |
| 27 | REQUIRED | 必要项目 | string | 如：0 |
| 28 | HIDEFLAG | 隐藏项目 | string | 如：0 |
| 29 | SCIENTIFICFLAG | 科研标志 | string | 如：0 |



Json返回数据格式：



代码实现：

如果项目通道号修改，需要判断对应仪器下是否存在相同的通道号，如果存在，提示通道号已经存在，不允许修改，需要重新修改项目通道号；

LIS_INSTITEM表中获取信息，LIS_ITEM获取指标的信息，LIS_INSTRUMENT获取报告仪器的信息

表结构：LIS_INSTRUMENT\LIS_INSTITEM\LIS_ITEM

#### B03	删除仪器项目

接口说明：删除仪器项目

请求URL：../instrumentitem/request/deleteinstitem

代码文件：winning.lis.instrumentitem.service.InstrumentItemService

示例URL： 

原型参考：





接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | instid | 仪器ID | string | 不允许为空，如：5 |
| 3 | instname | 仪器名称 | string | 不允许为空，如：HX-21 |
| 4 | itemcode | 项目代码 | string | 不允许为空，如：0101002 |
| 5 | itemname | 项目名称 | string | 不允许为空，如：白细胞计数 |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage |
| 1 | type | 消息类型 | string | SUCCESS-成功 
ERROR-系统出现异常或错误
FAILT-业务逻辑处理失败 |
| 2 | messageCode | 消息代码 | string | 用于区分消息代码 |
| 3 | message | 消息内容 | string | type=ERROR时则为系统错误消息提示内容
type=FAILT时则为业务提示信息 |
| 4 | link | URL链接 | string | 用于提示用户后可以点击查看详情信息，默认为空 |
| 5 | data | 数据集合 | object | 用于存储返回的数据 |



代码实现：

前端要提示用户：您要删除【】以及对应的参考值、危急值等

删除仪器项目，对应的参考值，危急值等一系列的值都要删除

表结构：LIS_INSTITEM\LIS_ITEMREFERANCE（REFERTYPE=NORMAL-常规参考值，PANIC-危急值参考范围，INFECTIOUS-传染病）

#### B04	添加仪器项目

接口说明：添加仪器项目顺序

请求URL：../instrumentitem/request/addinstitem

代码文件：winning.lis.instrumentitem.service.InstrumentItemService

示例URL： 

原型参考：

接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | instid | 仪器ID | string | 不允许为空，如：5 |
| 3 | instname | 仪器名称 | string | 不允许为空，如：au900 |
| 节点名称：institem 仪器项目信息 类型： array<object> | 节点名称：institem 仪器项目信息 类型： array<object> | 节点名称：institem 仪器项目信息 类型： array<object> | 节点名称：institem 仪器项目信息 类型： array<object> | 节点名称：institem 仪器项目信息 类型： array<object> |
| 1 | itemcode | 项目代码 | string | 不允许为空，如：0101002 |
| 2 | itemname | 项目名称 | string | 不允许为空，如：白细胞计数 |
| 3 | instidnew | 仪器的ID | string | 允许为空，选择项目的所属仪器ID |
| 4 | flag | 选择操作 | string | 不允许为空，添加—add，删除—delete |
| 节点名称：copyinfo    同步的复制信息   类型：array<string> | 节点名称：copyinfo    同步的复制信息   类型：array<string> | 节点名称：copyinfo    同步的复制信息   类型：array<string> | 节点名称：copyinfo    同步的复制信息   类型：array<string> | 节点名称：copyinfo    同步的复制信息   类型：array<string> |
| 1 | institem | 仪器项目 | string | 允许为空，为空，默认为0，不选中--0，选中为--1 |
| 2 | normal | 报告参考值 | string | 允许为空，为空，默认为0，不选中--0，选中为--1 |
| 3 | panic | 危急值参考值 | string | 允许为空，为空，默认为0，不选中--0，选中为--1 |
| 4 | infectious | 传染病参考值 | string | 允许为空，为空，默认为0，不选中--0，选中为--1 |
| 5 | formular | 计算公式 | string | 允许为空，为空，默认为0，不选中--0，选中为--1 |
| 6 | changemean | 转义项目 | string | 允许为空，为空，默认为0，不选中--0，选中为--1 |
| 7 | condition | 审核条件 | string | 允许为空，为空，默认为0，不选中--0，选中为--1 |
| 8 | groupitem | 组合项目 | string | 允许为空，为空，默认为0，不选中--0，选中为--1 |



接口出参【ResposeMessage.data-> array<object>】：出参见B01	获取仪器项目列表

 Json返回数据格式：



代码实现：

如果项目通道号修改，需要判断对应仪器下是否存在相同的通道号，如果存在，提示通道号已经存在，不允许修改，需要重新修改项目通道号；（批量添加时，不考虑）

LIS_INSTITEM表中获取信息，LIS_ITEM获取指标的信息，LIS_INSTRUMENT获取报告仪器的信息

表结构：LIS_INSTRUMENT\LIS_INSTITEM\LIS_ITEM

#### B05	修改仪器项目顺序

接口说明：修改仪器项目顺序

请求URL：../instrumentitem/request/updateprintorder

代码文件：winning.lis.instrumentitem.service.InstrumentItemService

示例URL： http://192.168.10.73:10004/lis/instrumentitem/request/updateprintorder?hospitalcode=9999&instid=5&itemname=MPV&itemcode=MPV&printorder=11&newinstid=6&newitemcode=WBC&newitemname=WBC&newprintorder=1

原型参考：

接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | instid | 仪器ID | string | 不允许为空，如：5 |
| 3 | instname | 仪器名称 | string | 不允许为空，如：au5800 |
| 4 | itemcode | 项目代码 | string | 不允许为空，如：0101002 |
| 5 | itemname | 项目名称 | string | 不允许为空，如：白细胞计数 |
| 6 | printorder | 打印序号 | string | 不允许为空，如：9 |
| 7 | printgroup | 打印分组 | string | 允许为空，如：A..Z |
| 8 | newitemcode | 被修改项目代码 | string | 不允许为空，如：255 |
| 9 | newitemname | 被修改的项目名称 | string | 不允许为空，如：红细胞计数 |
| 10 | newprintorder | 被修改的打印序号 | string | 不允许为空，如：4 |
| 11 | newprintgroup | 被修改打印分组 | string | 允许为空，如：A..Z |
| 12 | moveorder | 移动顺序 | string | 允许为空，默认上移，上移-0，下移-1 |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：institem 仪器信息 类型： object | 节点名称：institem 仪器信息 类型： object | 节点名称：institem 仪器信息 类型： object | 节点名称：institem 仪器信息 类型： object | 节点名称：institem 仪器信息 类型： object |
| 1 | INSTID | 仪器ID | int | 如：3 |
| 2 | ITEMCODE | 项目代码 | string | 如：0101002 |
| 3 | PRINTORDER | 打印序号 | string | 如：001 |
| 节点名称：newinstitem 被调整仪器信息 类型： object | 节点名称：newinstitem 被调整仪器信息 类型： object | 节点名称：newinstitem 被调整仪器信息 类型： object | 节点名称：newinstitem 被调整仪器信息 类型： object | 节点名称：newinstitem 被调整仪器信息 类型： object |
| 1 | INSTID | 仪器ID | int | 如：3 |
| 2 | ITEMCODE | 项目代码 | string | 如：0101002 |
| 3 | PRINTORDER | 打印序号 | string | 如：001 |



Json返回数据格式：

"institem": {

            "INSTID": 5,

            "ITEMCODE": "MPV",

            "PRINTORDER": 11

        },

        "newinstitem": {

            "NEWINSTID": "6",

            "NEWITEMCODE": "WBC",

            "NEWPRINTORDER": "11"

        }

代码实现：

表结构： LIS_INSTITEM

#### B06	获取仪器项目参考值

接口说明：获取仪器项目参考值

请求URL：../instrumentitem/request/getnormal

代码文件：winning.lis.instrumentitem.service.InstrumentItemService

示例URL： http://192.168.10.73:10004/lis/instrumentitem/request/getnormal?hospitalcode=9999&instid=5&instname=大便常规&itemcode=0101005&itemname=%Mon&itemnum=%Mon

原型参考：

接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | instid | 仪器ID | string | 不允许为空，如：5 |
| 3 | instname | 仪器名称 | string | 不允许为空，如;AU5800 |
| 4 | itemcode | 项目代码 | string | 不允许为空，如：0101002 |
| 5 | itemname | 项目名称 | string | 不允许为空，如：中性粒细胞绝对值 |
| 6 | itemnum | 项目标准编码 | string | 不允许为空，如：NEU# |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：iteminfo  项目信息    类型： object | 节点名称：iteminfo  项目信息    类型： object | 节点名称：iteminfo  项目信息    类型： object | 节点名称：iteminfo  项目信息    类型： object | 节点名称：iteminfo  项目信息    类型： object |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | INSTID | 仪器ID | int | 如：3 |
| 3 | ITEMCODE | 项目代码 | string | 如：0101002 |
| 4 | ITEMNAME | 项目名称 | string | 如：中性粒细胞绝对值 |
| 5 | ITEMNUM | 项目标准编码 | string | 如：NEU# |
| 节点名称：refertypedef   默认参考值    类型： object | 节点名称：refertypedef   默认参考值    类型： object | 节点名称：refertypedef   默认参考值    类型： object | 节点名称：refertypedef   默认参考值    类型： object | 节点名称：refertypedef   默认参考值    类型： object |
| 1 | LOWLIMIT | 参考下限 | string | 如：12.00 |
| 2 | UPPLIMIT | 参考上限 | string | 如：12.00 |
| 3 | CHARREFER | 字符参考值 | string | 如：0 |
| 4 | CONTAINBOUND | 是否包含边界值 | string | 数字结果:1-包含边界 0-不包含边界 字符结果:1-包含 2-全匹配 |
| 5 | PRINTREFER | 多行参考 | string | 参考打印 ;分隔 |
| 节点名称：refertypelist   特殊参考值    类型： array<object> | 节点名称：refertypelist   特殊参考值    类型： array<object> | 节点名称：refertypelist   特殊参考值    类型： array<object> | 节点名称：refertypelist   特殊参考值    类型： array<object> | 节点名称：refertypelist   特殊参考值    类型： array<object> |
| 1 | SERIALNO | 序号 | string | 如：1 |
| 2 | LOWLIMIT | 参考下限 | string | 如：12.00 |
| 3 | UPPLIMIT | 参考上限 | string | 如：12.00 |
| 4 | CHARREFER | 字符参考值 | string | 如：0 |
| 5 | CONTAINBOUND | 是否包含边界值 | string | 数字结果:1-包含边界 0-不包含边界 字符结果:1-包含 2-全匹配 |
| 6 | REFERDESC | 参考区间描述 | string | 如：6.00-12.00 |
| 7 | PRINTREFER | 参考打印格式 | string | 以分号区分多个参考 |
| 8 | STOPFLAG | 停用标志 | string | 如：0 |
| 9 | EFFCONID | 生效条件ID | string | 如：22 |
| 10 | REFERTYPEDETAIL    参考值明细       array<object> | REFERTYPEDETAIL    参考值明细       array<object> | REFERTYPEDETAIL    参考值明细       array<object> | REFERTYPEDETAIL    参考值明细       array<object> |
|  |  |  |  |  |
| 1 | ROUTINESERIALNO | 主键ID | string | 如：22 |
| 2 | ORDERNO | 序号 | int | 如：33 |
| 3 | LOGIC | 逻辑运算符 | string | 选择列表：和、或（AND,OR） |
| 4 | LOGICDESC | 运算符描述 | string | 如：三岁以前 |
| 5 | CONFIELDLIST | 查询子句分组表示 | string | 如：1-3表示子句1与子句3之间用括号组合起来,多个分组用','分割 |
| 6 | FIELDDEFINEID | 字段定义ID | string | 如：3 |
| 7 | FIELDEN | 字段英文名称 | string | 如：SEX |
| 8 | FIELDCN | 字段中文名称 | string | 如：性别 |
| 9 | RELATION | 关系运算符 | string | 选择列表：=、<>、>、<、>=、<=、包含、不包含（=、!=、>、<、>=、<=、IN、NOT IN） |
| 10 | RELATIONDESC | 关系运算符描述 | string | 如：大于 |
| 11 | VALUE | 值 | string | 如：2 |
| 12 | VALUEDESC | 值描述 | string | 如：女 |
| 13 | ISVARIABLE | 是否为动态值 | string | 只有日期字段才有动态值，1：为动态值 |
| 14 | VARIABLEVALUE | 动态内容 | string | 动态内容，分两组选择列表：本年、本季、本月、本周、前年、前季、前月、前周；第一天、最后一天、当前天 |
| 15 | ISHIGH | 查询方案级别 | int | 1是高级查询方案，=0是简单查询默认方案 |
| 16 | UPDATEFLAG | 数据访问级别 | string | 0:正常数据，-1:为删除数据，系统控制 |
| 17 | FATHEREFFCONID | 条件ID | int | 如：33 |



Json返回数据格式：

{

		"iteminfo": {

			"HOSPITALCODE": "9999",

			"INSTID": "5",

			"ITEMCODE": "0101005",

			"ITEMNAME": "%Mon",

			"ITEMNUM": "%Mon"

		},

		"refertypedef": {

			"LOWLIMIT": "37.0000",

			"UPPLIMIT": "44.0000",

			"CHARREFER": "",

			"REFERCONTAINBOUND": ""

		},

		"refertypelist": [

			{

				"SERIALNO": "5",

				"INSTID": "5",

				"ITEMCODE": "0101005",

				"REFERTYPE": "NORMAL",

				"LOWLIMIT": "56.0000",

				"UPPLIMIT": "102.0000",

				"CHARREFER": "",

				"CONTAINBOUND": "0",

				"REFERDESC": "56<X<102",

				"PRINTREFER": "56～102",

				"EFFCONID": "3",

				"STOPFLAG": "0",

				"REFERTYPEDETAIL": [

					{

						"RPTTYPE": "NORMAL",

						"ORDERNO": "1",

						"LOGIC": "''",

						"LOGICDESC": "且",

						"CONFIELDLIST": "1-3",

						"FIELDDEFINEID": "1",

						"FIELDEN": "SEX",

						"FIELDCN": "性别",

						"RELATION": "=",

						"RELATIONDESC": "等于",

						"VALUE": "1",

						"VALUEDESC": "男",

						"ISVARIABLE": "0",

						"VARIABLEVALUE": "",

						"ISHIGH": "0",

						"UPDATEBYCODE": "001·",

						"UPDATETIME": "2017-08-31 00:00:00",

						"UPDATEFLAG": "0",

						"FATHEREFFCONID": "3",

						"ROUTINESERIALNO": "10"

					}

				]

			}

		]

	}

代码实现：

参考值分为默认值和特殊参考值两种，默认值在表LIS_INSTITEM中，特殊参考值在LIS_ITEMREFERANCE表中，其中REFERTYPE为常规参考值，COM_EFFCON_LIST为参考值的条件表，关联字段为EFFCONID，COM_EFFCON_ROUTINE为条件的明细表

表结构： LIS_INSTITEM\LIS_ITEMREFERANCE\COM_EFFCON_LIST\COM_EFFCON_ROUTINE

#### B07	修改仪器项目默认参考值

接口说明：修改仪器项目参考值

请求URL：../instrumentitem/request/updatedefaultnormal

代码文件：winning.lis.instrumentitem.service.InstrumentItemService

示例URL： http://192.168.10.73:10004/lis/instrumentitem/request/updatedefaultnormal?hospitalcode=9999&instid=7&instname=大便常规&itemcode=0102030&itemname=上皮细胞&itemnum=EC&lowlimit=15&upplimit=20&charrefer&containbound=1

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | instid | 仪器ID | string | 不允许为空，如：5 |
| 3 | instname | 仪器名称 | string | 不允许为空，如;AU5800 |
| 4 | itemcode | 项目代码 | string | 不允许为空，如：0101002 |
| 5 | itemname | 项目名称 | string | 不允许为空，如：中性粒细胞绝对值 |
| 6 | itemnum | 项目标准编码 | string | 不允许为空，如：NEU# |
| 7 | lowlimit | 参考下限 | string | 如：12.00 |
| 8 | upplimit | 参考上限 | string | 如：12.00 |
| 9 | charrefer | 字符参考值 | string | 如：0 |
| 10 | containbound | 是否包含边界值 | string | 数字结果:1-包含边界 0-不包含边界 字符结果:1-包含 2-全匹配 |
| 11 | printrefer | 多行参考 | string | 允许为空，参考打印 ;分隔 |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | INSTID | 仪器ID | int | 如：3 |
| 3 | INSTNAME | 仪器名称 | string | 如：AU800 |
| 4 | ITEMCODE | 项目代码 | string | 如：0101002 |
| 5 | ITEMNAME | 项目名称 | string | 如：中性粒细胞绝对值 |
| 6 | ITEMNUM | 项目标准编码 | string | 如：NEU# |
| 7 | LOWLIMIT | 参考下限 | string | 如：12.00 |
| 8 | UPPLIMIT | 参考上限 | string | 如：12.00 |
| 9 | CHARREFER | 字符参考值 | string | 如：0 |
| 10 | CONTAINBOUND | 是否包含边界值 | string | 数字结果:1-包含边界 0-不包含边界 字符结果:1-包含 2-全匹配 |
| 11 | PRINTREFER | 多行参考 | string | 参考打印 ;分隔 |
| 12 | VALUEINFODESC | 参考值描述 | string | 如：3-55 |
| 13 | SAMPLEINFODESC | 标本信息描述 | string | 如：性别：女 标本：血清 |



Json返回数据格式：

{

		"HOSPITALCODE":"9999",

		"INSTID":"7",

		"INSTNAME":"大便常规",

		"ITEMCODE":"0102030",

		"ITEMNAME":"上皮细胞",

		"ITEMNUM":"EC",

		"LOWLIMIT":15.0000,

		"UPPLIMIT":20.0000,

		"CHARREFER":"",

		"REFERCONTAINBOUND":1

		"VALUEINFODESC":4-6,

		"SAMPLEINFODESC": 性别：女 标本：血清

	}

代码实现：

默认值在表LIS_INSTITEM中，直接修改

表结构： LIS_INSTITEM

#### B08	修改仪器项目特殊参考值

接口说明：修改仪器项目特殊参考值

请求URL：../instrumentitem/request/updateothernormal

代码文件：winning.lis.instrumentitem.service.InstrumentItemService

示例URL： http://192.168.10.73:10004/lis/instrumentitem/request/updateothernormal?hospitalcode=9999&instid=8&instname=SF810-1&itemcode=0102034&itemname=红细胞高倍视野&itemnum=RBC-M&itemreferance={"serialno":"21","lowlimit":"12.32","upplimit":"200","charrefer":"","referdesc":"12.32-23","effconid":"9"}&effconroutine=[{"routineserialno":"25","fielden":"SEX","fieldcn":"性别","value":"2","valuedesc":"女1"},{"routineserialno":"0","fielden":"APPLYDEPTCODE","fieldcn":"申请科室","value":"1411","valuedesc":"儿三科"}]

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | instid | 仪器ID | string | 不允许为空，如：5 |
| 3 | instname | 仪器名称 | string | 不允许为空，如;AU5800 |
| 4 | itemcode | 项目代码 | string | 不允许为空，如：0101002 |
| 5 | itemname | 项目名称 | string | 不允许为空，如：中性粒细胞绝对值 |
| 6 | itemnum | 项目标准编码 | string | 不允许为空，如：NEU# |
| 节点名称：itemreferance  参考值条件 类型： object  不能为空 | 节点名称：itemreferance  参考值条件 类型： object  不能为空 | 节点名称：itemreferance  参考值条件 类型： object  不能为空 | 节点名称：itemreferance  参考值条件 类型： object  不能为空 | 节点名称：itemreferance  参考值条件 类型： object  不能为空 |
| 1 | serialno | 序号 | int | 不允许为空，如：12 |
| 2 | lowlimit | 参考下限 | string | 允许为空，如：12.00 |
| 3 | upplimit | 参考上限 | string | 允许为空，如：12.00 |
| 4 | charrefer | 字符参考值 | string | 允许为空，如：0 |
| 5 | containbound | 是否包含边界值 | string | 允许为空，数字结果:1-包含边界 0-不包含边界 字符结果:1-包含 2-全匹配 |
| 6 | referdesc | 参考区间描述 | string | 允许为空，如：6.00-12.00 |
| 7 | printrefer | 参考打印格式 | string | 允许为空，以；分号区分多个参考 |
| 8 | effconid | 条件的ID | string | 允许为空，如：12 |
| 节点名称：effconroutine   参考值的明细规则 类型： array<object>  有数据存在就直接传递，不为空按规则传 | 节点名称：effconroutine   参考值的明细规则 类型： array<object>  有数据存在就直接传递，不为空按规则传 | 节点名称：effconroutine   参考值的明细规则 类型： array<object>  有数据存在就直接传递，不为空按规则传 | 节点名称：effconroutine   参考值的明细规则 类型： array<object>  有数据存在就直接传递，不为空按规则传 | 节点名称：effconroutine   参考值的明细规则 类型： array<object>  有数据存在就直接传递，不为空按规则传 |
| 1 | routineserialno | 主键ID | int | 不允许为空，新增条件，传0，修改传明细的SERIALNO |
| 2 | fielden | 字段英文名称 | string | 不允许为空，如：12 |
| 3 | fieldcn | 字段中文名称 | string | 不允许为空，如：12 |
| 4 | value | 值 | string | 不允许为空，如：12 |
| 5 | valuedesc | 值描述 | string | 不允许为空，如：12 |
| 节点名称：ageunit   年龄明细规则 类型： object  有数据存在就直接传递，不为空按规则传 | 节点名称：ageunit   年龄明细规则 类型： object  有数据存在就直接传递，不为空按规则传 | 节点名称：ageunit   年龄明细规则 类型： object  有数据存在就直接传递，不为空按规则传 | 节点名称：ageunit   年龄明细规则 类型： object  有数据存在就直接传递，不为空按规则传 | 节点名称：ageunit   年龄明细规则 类型： object  有数据存在就直接传递，不为空按规则传 |
| 1 | agelow | 年龄下限 | string | 允许为空，如：12 |
| 2 | ageupp | 年龄上限 | string | 允许为空，如：12 |
| 3 | ageunit | 年龄单位 | string | 允许为空，如：12 |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：iteminfo  项目信息    类型： object | 节点名称：iteminfo  项目信息    类型： object | 节点名称：iteminfo  项目信息    类型： object | 节点名称：iteminfo  项目信息    类型： object | 节点名称：iteminfo  项目信息    类型： object |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | INSTID | 仪器ID | int | 如：3 |
| 3 | ITEMCODE | 项目代码 | string | 如：0101002 |
| 4 | ITEMNAME | 项目名称 | string | 如：中性粒细胞绝对值 |
| 5 | ITEMNUM | 项目标准编码 | string | 如：NEU# |
| 节点名称：refertypelist   特殊参考值    类型： array<object> | 节点名称：refertypelist   特殊参考值    类型： array<object> | 节点名称：refertypelist   特殊参考值    类型： array<object> | 节点名称：refertypelist   特殊参考值    类型： array<object> | 节点名称：refertypelist   特殊参考值    类型： array<object> |
| 1 | SERIALNO | 序号 | string | 如：1 |
| 2 | LOWLIMIT | 参考下限 | string | 如：12.00 |
| 3 | UPPLIMIT | 参考上限 | string | 如：12.00 |
| 4 | CHARREFER | 字符参考值 | string | 如：0 |
| 5 | CONTAINBOUND | 是否包含边界值 | string | 数字结果:1-包含边界 0-不包含边界 字符结果:1-包含 2-全匹配 |
| 6 | REFERDESC | 参考区间描述 | string | 如：6.00-12.00 |
| 7 | PRINTREFER | 参考打印格式 | string | 以分号区分多个参考 |
| 8 | STOPFLAG | 停用标志 | string | 如：0 |
| 9 | EFFCONID | 生效条件ID | string | 如：22 |
| 10 | REFERTYPEDETAIL    参考值明细       array<object> | REFERTYPEDETAIL    参考值明细       array<object> | REFERTYPEDETAIL    参考值明细       array<object> | REFERTYPEDETAIL    参考值明细       array<object> |
|  |  |  |  |  |
| 1 | ROUTINESERIALNO | 主键ID | string | 如：22 |
| 2 | ORDERNO | 序号 | int | 如：33 |
| 3 | LOGIC | 逻辑运算符 | string | 选择列表：和、或（AND,OR） |
| 4 | LOGICDESC | 运算符描述 | string | 如：三岁以前 |
| 5 | CONFIELDLIST | 查询子句分组表示 | string | 如：1-3表示子句1与子句3之间用括号组合起来,多个分组用','分割 |
| 6 | FIELDDEFINEID | 字段定义ID | string | 如：3 |
| 7 | FIELDEN | 字段英文名称 | string | 如：SEX |
| 8 | FIELDCN | 字段中文名称 | string | 如：性别 |
| 9 | RELATION | 关系运算符 | string | 选择列表：=、<>、>、<、>=、<=、包含、不包含（=、!=、>、<、>=、<=、IN、NOT IN） |
| 10 | RELATIONDESC | 关系运算符描述 | string | 如：大于 |
| 11 | VALUE | 值 | string | 如：2 |
| 12 | VALUEDESC | 值描述 | string | 如：女 |
| 13 | ISVARIABLE | 是否为动态值 | string | 只有日期字段才有动态值，1：为动态值 |
| 14 | VARIABLEVALUE | 动态内容 | string | 动态内容，分两组选择列表：本年、本季、本月、本周、前年、前季、前月、前周；第一天、最后一天、当前天 |
| 15 | ISHIGH | 查询方案级别 | int | 1是高级查询方案，=0是简单查询默认方案 |
| 16 | UPDATEFLAG | 数据访问级别 | string | 0:正常数据，-1:为删除数据，系统控制 |
| 17 | FATHEREFFCONID | 条件ID | int | 如：33 |



Json返回数据格式：

[

		{

			"SERIALNO":"21",

			"LOWLIMIT":"12.3200",

			"UPPLIMIT":"23.0000",

			"CHARREFER":"",

			"CONTAINBOUND":"0",

			"REFERDESC":"12.32-23",

			"PRINTREFER":"",

			"EFFCONID":"9",

			"STOPFLAG":"0",

			"REFERTYPEDETAIL":[

				{

					"RPTTYPE":"NORMAL",

					"ORDERNO":"1",

					"LOGIC":"AND",

					"LOGICDESC":"和",

					"CONFIELDLIST":"",

					"FIELDDEFINEID":"0",

					"FIELDEN":"SEX",

					"FIELDCN":"性别",

					"RELATION":"=",

					"RELATIONDESC":"等于",

					"VALUE":"2",

					"VALUEDESC":"女1",

					"ISVARIABLE":"0",

					"VARIABLEVALUE":"",

					"ISHIGH":"0",

					"UPDATEBYCODE":"1047",

					"UPDATETIME":"2018-03-02 09:04:00",

					"UPDATEFLAG":"0",

					"FATHEREFFCONID":"9",

					"ROUTINESERIALNO":"25"

				}

			]

		}

	]

代码实现：

在表LIS_ITEMREFERANCE修改规则，

如果LIS_ITEMREFERANCE中EFFCONID为空，需要添加COM_EFFCON_LIST，并将EFFCONID更新到LIS_ITEMREFERANCE中。

如果如果LIS_ITEMREFERANCE中EFFCONID不为空，则更新COM_EFFCON_ROUTINE表或者插入数据，并更新COM_EFFCON_LIST表

表结构：LIS_ITEMREFERANCE\COM_EFFCON_LIST\COM_EFFCON_ROUTINE

#### B09	删除仪器项目特殊参考值

接口说明：删除仪器项目特殊参考值（默认参考值不允许删除）

请求URL：../instrumentitem/request/deleteothernormal

代码文件：winning.lis.instrumentitem.service.InstrumentItemService

示例URL： http://192.168.10.73:10004/lis/instrumentitem/request/deleteothernormal?hospitalcode=9999&instname=SF810-1&itemnum=RBC-M&serialno=18&effconid=6

原型参考：

接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | instname | 仪器名称 | string | 不允许为空，如;AU5800 |
| 3 | itemnum | 项目名称 | string | 不允许为空，如：中性粒细胞绝对值 |
| 4 | serialno | 参考值序号 | string | 不允许为空，如：1 |
| 5 | effconid | 条件值序号 | string | 允许为空，如：1 |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage |
| 1 | type | 消息类型 | string | SUCCESS-成功 
ERROR-系统出现异常或错误
FAILT-业务逻辑处理失败 |
| 2 | messageCode | 消息代码 | string | 用于区分消息代码 |
| 3 | message | 消息内容 | string | type=ERROR时则为系统错误消息提示内容
type=FAILT时则为业务提示信息 |
| 4 | link | URL链接 | string | 用于提示用户后可以点击查看详情信息，默认为空 |
| 5 | data | 数据集合 | object | 用于存储返回的数据 |



Json返回数据格式：



代码实现：

参考值分为默认值和特殊参考值两种，默认值不允许删除，只能修改，特殊参考值在LIS_ITEMREFERANCE表中可以删除，对表中的字段的状态更新，包括条件列表集合和

表结构：LIS_ITEMREFERANCE

#### B10	添加仪器项目特殊参考值-修改

接口说明：修改仪器项目特殊参考值

请求URL：../instrumentitem/request/addothernormal

代码文件：winning.lis.instrumentitem.service.InstrumentItemService

示例URL： http://192.168.10.73:10004/lis/instrumentitem/request/addothernormal?hospitalcode=9999&instid=8&instname=SF810-1&itemcode=0102034&itemname=红细胞高倍视野&itemnum=RBC-M&itemreferance={"lowlimit":"12.32","upplimit":"23","charrefer":"","referdesc":"12.32-23"}&effconroutine=[{"fielden":"SEX","fieldcn":"性别","value":"2","valuedesc":"女"},{"fielden":"APPLYDEPTCODE","fieldcn":"申请科室","value":"1411","valuedesc":"儿一科"}]

原型参考：

接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | instid | 仪器ID | string | 不允许为空，如：5 |
| 3 | instname | 仪器名称 | string | 不允许为空，如;AU5800 |
| 4 | itemcode | 项目代码 | string | 不允许为空，如：0101002 |
| 5 | itemname | 项目名称 | string | 不允许为空，如：中性粒细胞绝对值 |
| 6 | itemnum | 项目标准编码 | string | 不允许为空，如：NEU# |
| 节点名称：itemreferance  参考值条件 类型： object   不能为空 | 节点名称：itemreferance  参考值条件 类型： object   不能为空 | 节点名称：itemreferance  参考值条件 类型： object   不能为空 | 节点名称：itemreferance  参考值条件 类型： object   不能为空 | 节点名称：itemreferance  参考值条件 类型： object   不能为空 |
| 1 | lowlimit | 参考下限 | string | 允许为空，如：12.00 |
| 2 | upplimit | 参考上限 | string | 允许为空，如：12.00 |
| 3 | charrefer | 字符参考值 | string | 允许为空，如：0 |
| 4 | containbound | 是否包含边界值 | string | 允许为空，数字结果:1-包含边界 0-不包含边界 字符结果:1-包含 2-全匹配 |
| 5 | referdesc | 参考区间描述 | string | 允许为空，如：4-34 |
| 6 | printrefer | 多行参考值 | string | 允许为空，如：wec>20; |
| 节点名称：effconroutine   参考值明细规则 类型：array<object> 可以为空，如果不为空，按规则传 | 节点名称：effconroutine   参考值明细规则 类型：array<object> 可以为空，如果不为空，按规则传 | 节点名称：effconroutine   参考值明细规则 类型：array<object> 可以为空，如果不为空，按规则传 | 节点名称：effconroutine   参考值明细规则 类型：array<object> 可以为空，如果不为空，按规则传 | 节点名称：effconroutine   参考值明细规则 类型：array<object> 可以为空，如果不为空，按规则传 |
| 1 | fielden | 字段英文名称 | string | 不允许为空，如：12 |
| 2 | fieldcn | 字段中文名称 | string | 不允许为空，如：12 |
| 3 | value | 值 | string | 不允许为空，如：12 |
| 4 | valuedesc | 值描述 | string | 不允许为空，如：12 |
| 节点名称：ageunit   年龄明细规则 类型： object  有数据存在就直接传递，不为空按规则传 | 节点名称：ageunit   年龄明细规则 类型： object  有数据存在就直接传递，不为空按规则传 | 节点名称：ageunit   年龄明细规则 类型： object  有数据存在就直接传递，不为空按规则传 | 节点名称：ageunit   年龄明细规则 类型： object  有数据存在就直接传递，不为空按规则传 | 节点名称：ageunit   年龄明细规则 类型： object  有数据存在就直接传递，不为空按规则传 |
| 1 | agelow | 年龄下限 | string | 允许为空，如：12 |
| 2 | ageupp | 年龄上限 | string | 允许为空，如：12 |
| 3 | ageunit | 年龄单位 | string | 允许为空，如：12 |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：iteminfo  项目信息    类型： object | 节点名称：iteminfo  项目信息    类型： object | 节点名称：iteminfo  项目信息    类型： object | 节点名称：iteminfo  项目信息    类型： object | 节点名称：iteminfo  项目信息    类型： object |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | INSTID | 仪器ID | int | 如：3 |
| 3 | ITEMCODE | 项目代码 | string | 如：0101002 |
| 4 | ITEMNAME | 项目名称 | string | 如：中性粒细胞绝对值 |
| 5 | ITEMNUM | 项目标准编码 | string | 如：NEU# |
| 节点名称：refertypelist   特殊参考值    类型： array<object> | 节点名称：refertypelist   特殊参考值    类型： array<object> | 节点名称：refertypelist   特殊参考值    类型： array<object> | 节点名称：refertypelist   特殊参考值    类型： array<object> | 节点名称：refertypelist   特殊参考值    类型： array<object> |
| 1 | SERIALNO | 序号 | string | 如：1 |
| 2 | LOWLIMIT | 参考下限 | string | 如：12.00 |
| 3 | UPPLIMIT | 参考上限 | string | 如：12.00 |
| 4 | CHARREFER | 字符参考值 | string | 如：0 |
| 5 | CONTAINBOUND | 是否包含边界值 | string | 数字结果:1-包含边界 0-不包含边界 字符结果:1-包含 2-全匹配 |
| 6 | REFERDESC | 参考区间描述 | string | 如：6.00-12.00 |
| 7 | PRINTREFER | 参考打印格式 | string | 以分号区分多个参考 |
| 8 | STOPFLAG | 停用标志 | string | 如：0 |
| 9 | EFFCONID | 生效条件ID | string | 如：22 |
| 10 | REFERTYPEDETAIL    参考值明细       array<object> | REFERTYPEDETAIL    参考值明细       array<object> | REFERTYPEDETAIL    参考值明细       array<object> | REFERTYPEDETAIL    参考值明细       array<object> |
|  |  |  |  |  |
| 1 | ROUTINESERIALNO | 主键ID | string | 如：22 |
| 2 | ORDERNO | 序号 | int | 如：33 |
| 3 | LOGIC | 逻辑运算符 | string | 选择列表：和、或（AND,OR） |
| 4 | LOGICDESC | 运算符描述 | string | 如：三岁以前 |
| 5 | CONFIELDLIST | 查询子句分组表示 | string | 如：1-3表示子句1与子句3之间用括号组合起来,多个分组用','分割 |
| 6 | FIELDDEFINEID | 字段定义ID | string | 如：3 |
| 7 | FIELDEN | 字段英文名称 | string | 如：SEX |
| 8 | FIELDCN | 字段中文名称 | string | 如：性别 |
| 9 | RELATION | 关系运算符 | string | 选择列表：=、<>、>、<、>=、<=、包含、不包含（=、!=、>、<、>=、<=、IN、NOT IN） |
| 10 | RELATIONDESC | 关系运算符描述 | string | 如：大于 |
| 11 | VALUE | 值 | string | 如：2 |
| 12 | VALUEDESC | 值描述 | string | 如：女 |
| 13 | ISVARIABLE | 是否为动态值 | string | 只有日期字段才有动态值，1：为动态值 |
| 14 | VARIABLEVALUE | 动态内容 | string | 动态内容，分两组选择列表：本年、本季、本月、本周、前年、前季、前月、前周；第一天、最后一天、当前天 |
| 15 | ISHIGH | 查询方案级别 | int | 1是高级查询方案，=0是简单查询默认方案 |
| 16 | UPDATEFLAG | 数据访问级别 | string | 0:正常数据，-1:为删除数据，系统控制 |
| 17 | FATHEREFFCONID | 条件ID | int | 如：33 |



Json返回数据格式：



代码实现：

在LIS_ITEMREFERANCE表中插入记录，其中REFERTYPE为常规参考值，COM_EFFCON_LIST插入参考值的条件表，并将生成的EFFCONID更新到LIS_ITEMREFERANCE表中，在COM_EFFCON_ROUTINE表中插入数据，并将对应的信息拼接成对应的格式更新到LIS_ITEMREFERANCE表中。

表结构： LIS_ITEMREFERANCE\COM_EFFCON_LIST\COM_EFFCON_ROUTINE

#### B11	获取检验指标列表

接口说明：获取检验指标列表

请求URL：../instrumentitem/request/getorderitemlist

代码文件：winning.lis.ordertoitem.service.OrderToItemService

示例URL： http://192.168.10.73:15011/lis/ordertoitem/request/getorderitemlist?hospitalcode=9999&nodeno=ORDERTOITEM_EXECGROUP&selectvalue=244

原型参考：



接口入参： 

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | nodeno | 选项分类的nodeno | string | 不允许为空，如：ORDERTOITEM_ITEMGROPU |
| 3 | selectvalue | 选择参数 | string | 不允许为空，菜单下为当前选中项的ID，没有传0 |
| 4 | instid | 仪器ID | string | 不允许为空，当前添加项目的仪器ID |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- | --- |
| 节点名称：orderitemlist 汇总信息 类型：array<object> | 节点名称：orderitemlist 汇总信息 类型：array<object> | 节点名称：orderitemlist 汇总信息 类型：array<object> | 节点名称：orderitemlist 汇总信息 类型：array<object> | 节点名称：orderitemlist 汇总信息 类型：array<object> | 节点名称：orderitemlist 汇总信息 类型：array<object> |
| 1 | ITEMCODE | 项目代码 | 项目代码 | string | 如：#Bas |
| 2 | ITEMNAME | 项目名称 | 项目名称 | string | 如; 嗜碱性细胞数 |
| 3 | ITEMNUM | 标准编码 | 标准编码 | string | 如：#Bas |
| 4 | ENGSHORTNAME | 英文缩写 | 英文缩写 | string | 如：JC |
| 5 | ENGNAME | 英文名称 | 英文名称 | string | 如：WBC |
| 6 | LOINCCODE | LOINC编码 | LOINC编码 | string | 如： |
| 7 | EXAMCODE | 检验分类 | 检验分类 | string | 如：3 |
| 8 | EXAMNAME | 检验分类名称 | 检验分类名称 | string | 如：免疫 |
| 9 | RESULTTYPE | 结果类型 | 结果类型 | string | 如：3 |
| 10 | RESULTTYPEDES | 结果类型描述 | 结果类型描述 | string | 如：阴阳 |
| 11 | DEFVALUE | 缺省值 | 缺省值 | string | 如：阴性 |
| 12 | MEMCODE1 | 输入码一 | 输入码一 | string | 如：SJXXBS |
| 13 | MEMCODE2 | 输入码二 | 输入码二 | string | 如：245d |
| 14 | REMARK | 备注说明 | 备注说明 | string | 如：项目再用 |
| 15 | PREVALUE | 精度 | 精度 | string | 如：0.01 |
| 16 | ITEMPRICE | 单价 | 单价 | string | 如：14.5 |
| 17 | ITEMINFO | 项目说明 | 项目说明 | string | 如：血常规项目 |
| 18 | ITEMTYPEID | 项目分类ID | 项目分类ID | string | 项目分类ID，对应lis_treediect的nodeid |
| 19 | STOPFLAG | 项目停用标志 | 项目停用标志 | string | 标识该项目是否停用，0-启用，1-停用 |



#### B12	仪器项目对应指标的存在判断

接口说明：仪器项目对应指标存在情况判断，用于区分对应指标的存在情况

请求URL：../instrumentitem/request/existsindex

代码文件：winning.lis.ordertoitem.service.OrderToItemService

示例URL： 

原型参考：



接口入参： 

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | instid | 仪器ID | string | 不允许为空，如：5 |
| 3 | itemcode | 项目代码 | string | 不允许为空，如：0101002 |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- | --- |
| 1 | NORMAL | 参考值数量 | 参考值数量 | int | 如：1 |
| 2 | PANIC | 危急值数量 | 危急值数量 | int | 如：1 |
| 3 | INFECTIOUS | 传染病数量 | 传染病数量 | int | 如：1 |
| 4 | FORMULAR | 计算公式数量 | 计算公式数量 | int | 如：1 |
| 5 | CHANGEMEAN | 结果转换数量 | 结果转换数量 | int | 如：1 |
| 6 | HIGHLOWVALUE | 异常值数量 | 异常值数量 | int | 如：0 |
| 7 | RESULTINTERPRETATION | 结果建议与解释数量 | 结果建议与解释数量 | int | 如：1 |



Json返回数据格式：



代码实现：

判断各个指标的存在情况，用于指示对应的标签的颜色的变化

表结构：

### 仪器基础信息维护

#### V 模块接口视图

| NO. | 接口编号 | 接口名称 | 说明 |
| --- | --- | --- | --- |
| 1 | A01 | 仪器筛选分类初始化
../instrument/request/getsortinitdata | 获取仪器筛选分类初始化信息 |
| 2 | A02 | 仪器下拉框初始化
../instrument/request/getinitdata | 仪器下拉框初始化 |
| 3 | A03 | 报告单格式初始化
../instrument/request/getreportinitdata | 报告单格式初始化 |
| 4 | B01 | 获取分类仪器列表
../instrument/request/getinstrumentlist | 获取分类仪器列表 |
| 5 | B02 | 仪器搜索
../instrument/request/searchinst | 仪器搜索 |
| 6 | B03 | 仪器停用与启用
../instrument/request/setinststatus | 仪器停用与启用 |
| 7 | B04 | 仪器删除
../instrument/request/deleteinstrument | 仪器删除 |
| 8 | B05 | 仪器添加
../instrument/request/addinstrument | 仪器添加 |
| 9 | B06 | 获取被复制仪器列表
../instrument/request/getcopyinst | 获取被复制仪器列表 |
| 10 | B07 | 复制仪器
../instrument/request/copyinst | 复制仪器 |
| 11 | B08 | 获取仪器信息
../instrument/request/getinstinfo | 获取仪器信息 |
| 12 | B09 | 修改仪器信息
../instrument/request/alterinstinfo | 修改仪器信息 |



#### A 对外公布方法

##### A01	仪器筛选分类初始化

接口说明：获取仪器筛选分类初始化信息

请求URL：../instrument/request/getsortinitdata

代码文件：winning.lis.instrument.service.InstrumentService

示例URL： 

http://192.168.10.73:15011/lis/instrument/request/getsortinitdata?hospitalcode=9999

原型参考：





接口入参： 

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码 |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | NODENO | 节点编码 | string | 如：HISITEM_STATUS |
| 2 | NODENAME | 节点名称 | string | 如：状态 |
| 3 | CHILDNODES | 子节点名称 | array<object> |  |
|  |  |  |  |  |
| 1 | CODE | 字典代码 | string | 如：1 |
| 2 | NAME | 字典名称 | string | 如：血 |
| 3 | MEMCODE1 | 输入码一 | string | 如：1 |
| 4 | MEMCODE2 | 输入码二 | string | 如：X |
| 5 | DICID | 标准编码 | string | 如：1 |
| 6 | DICTYPE | 字典分类 | string | 如：样本类型 |
| 7 | SUBSYSCODE | 子系统分类 | string | 如：LIMS |
| 8 | EXTERNCODE | 外部代码 | string | 如：10 |
| 9 | ORDERNO | 排序 | string | 如：0 |
| 10 | RESERVEFIELD1 | 扩展字段信息 | string | 如：null |



Json返回数据格式：

[

    {

      "NODENO": "INSTITEM_STATUS",

      "NODENAME": "状态",

      "CHILDNODES": [

        {

          "CODE": "1",

          "NAME": "全部",

          "DICID": "1",

          "DICTYPE": "仪器列表状态",

          "EXTERNCODE": "1",

          "MEMCODE1": "qb",

          "MEMCODE2": null,

          "SUBSYSCODE": "LIMS",

          "ORDERNO": 1,

          "RESERVEFIELD1": null

        }

      ]

    },

    {

      "NODENO": "INSTITEM_EXECDEPT",

      "NODENAME": "专业组",

      "CHILDNODES": [

        {

          "CODE": "1",

          "NAME": "生化室",

          "DICID": null,

          "DICTYPE": "检测小组",

          "EXTERNCODE": null,

          "MEMCODE1": "1",

          "MEMCODE2": "shs",

          "SUBSYSCODE": "LIMS",

          "ORDERNO": 0,

          "RESERVEFIELD1": null

        }

      ]

    },

    {

      "NODENO": "INSTITEM_EXECDEPT",

      "NODENAME": "检验科室",

      "CHILDNODES": [

        {

          "HOSPITALCODE": "9999",

          "EXTERNCODE": "2110",

          "MEMCODE1": "jyk",

          "MEMCODE2": "sct",

          "SUBSYSCODE": "LIMS",

          "DICID": "2110",

          "CODE": "2110",

          "NAME": "医学检验科",

          "DICTYPE": "3",

          "ORDERNO": ""

        }

      ]

    },

   ]

代码实现：

直接调用comDicData中的getIstrumentSortStatus方法。

表结构：SLAVE_P\SLAVE\SYS_DEPT

##### A02	仪器下拉框初始化

接口说明：仪器下拉框初始化

请求URL：../instrument/request/getinitdata

代码文件：winning.lis.instrument.service.InstrumentService

示例URL： 

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：samplelist 汇总信息 类型：array< object> 默认标本 | 节点名称：samplelist 汇总信息 类型：array< object> 默认标本 | 节点名称：samplelist 汇总信息 类型：array< object> 默认标本 | 节点名称：samplelist 汇总信息 类型：array< object> 默认标本 | 节点名称：samplelist 汇总信息 类型：array< object> 默认标本 |
| 1 | CODE | 字典代码 | string | 如：1 |
| 2 | NAME | 字典名称 | string | 如：血 |
| 3 | MEMCODE1 | 输入码一 | string | 如：1 |
| 4 | MEMCODE2 | 输入码二 | string | 如：X |
| 5 | DICID | 标准编码 | string | 如：1 |
| 6 | DICTYPE | 字典分类 | string | 如：样本类型 |
| 7 | SUBSYSCODE | 子系统分类 | string | 如：LIMS |
| 8 | EXTERNCODE | 外部代码 | string | 如：10 |
| 9 | ORDERNO | 排序 | string | 如：0 |
| 10 | RESERVEFIELD1 | 扩展字段信息 | string | 如：null |
| 节点名称：examgrouplist 汇总信息 类型：array< object> 检测小组 | 节点名称：examgrouplist 汇总信息 类型：array< object> 检测小组 | 节点名称：examgrouplist 汇总信息 类型：array< object> 检测小组 | 节点名称：examgrouplist 汇总信息 类型：array< object> 检测小组 | 节点名称：examgrouplist 汇总信息 类型：array< object> 检测小组 |
| 1 | CODE | 字典代码 | string | 如：4 |
| 2 | NAME | 字典名称 | string | 如：体液室 |
| 3 | MEMCODE1 | 输入码一 | string | 如：4 |
| 4 | MEMCODE2 | 输入码二 | string | 如：tys |
| 5 | DICID | 标准编码 | string | 如：10 |
| 6 | DICTYPE | 字典分类 | string | 如：检测小组 |
| 7 | SUBSYSCODE | 子系统分类 | string | 如：LIMS |
| 8 | EXTERNCODE | 外部代码 | string | 如：10 |
| 9 | ORDERNO | 排序 | string | 如：10 |
| 10 | RESERVEFIELD1 | 扩展字段信息 | string | 如：null |
| 节点名称：examinelist 汇总信息 类型：array< object> 检验分类 | 节点名称：examinelist 汇总信息 类型：array< object> 检验分类 | 节点名称：examinelist 汇总信息 类型：array< object> 检验分类 | 节点名称：examinelist 汇总信息 类型：array< object> 检验分类 | 节点名称：examinelist 汇总信息 类型：array< object> 检验分类 |
| 1 | CODE | 字典代码 | string | 如：02 |
| 2 | NAME | 字典名称 | string | 如：生化 |
| 3 | MEMCODE1 | 输入码一 | string | 如：njj |
| 4 | MEMCODE2 | 输入码二 | string | 如：1 |
| 5 | DICID | 标准编码 | string | 如：02 |
| 6 | DICTYPE | 字典分类 | string | 如：检验分类 |
| 7 | SUBSYSCODE | 子系统分类 | string | 如：LIMS |
| 8 | EXTERNCODE | 外部代码 | string | 如：10 |
| 9 | ORDERNO | 排序 | string | 如：10 |
| 10 | RESERVEFIELD1 | 扩展字段信息 | string | 如：null |
| 节点名称：insttypelist 汇总信息 类型：array< object> 仪器分类（界面类型） | 节点名称：insttypelist 汇总信息 类型：array< object> 仪器分类（界面类型） | 节点名称：insttypelist 汇总信息 类型：array< object> 仪器分类（界面类型） | 节点名称：insttypelist 汇总信息 类型：array< object> 仪器分类（界面类型） | 节点名称：insttypelist 汇总信息 类型：array< object> 仪器分类（界面类型） |
| 1 | CODE | 字典代码 | string | 如：1 |
| 2 | NAME | 字典名称 | string | 如：仪器分类 |
| 3 | MEMCODE1 | 输入码一 | string | 如：QZLSH |
| 4 | MEMCODE2 | 输入码二 | string | 如：1 |
| 5 | DICID | 标准编码 | string | 如：10 |
| 6 | DICTYPE | 字典分类 | string | 如：仪器分类 |
| 7 | SUBSYSCODE | 子系统分类 | string | 如：LIMS |
| 8 | EXTERNCODE | 外部代码 | string | 如：10 |
| 9 | ORDERNO | 排序 | string | 如：10 |
| 10 | RESERVEFIELD1 | 扩展字段信息 | string | 如：null |
| 节点名称：execdeptlist 汇总信息类型：array< object> 执行科室（所在部门） | 节点名称：execdeptlist 汇总信息类型：array< object> 执行科室（所在部门） | 节点名称：execdeptlist 汇总信息类型：array< object> 执行科室（所在部门） | 节点名称：execdeptlist 汇总信息类型：array< object> 执行科室（所在部门） | 节点名称：execdeptlist 汇总信息类型：array< object> 执行科室（所在部门） |
| 1 | CODE | 字典代码 | string | 如：2110 |
| 2 | NAME | 字典名称 | string | 如：医学检验科 |
| 3 | MEMCODE1 | 输入码一 | string | 如：jyk |
| 4 | MEMCODE2 | 输入码二 | string | 如：1 |
| 5 | DICID | 标准编码 | string | 如：2110 |
| 6 | DICTYPE | 字典分类 | string | 如：3 |
| 7 | SUBSYSCODE | 子系统分类 | string | 如：LIMS |
| 8 | EXTERNCODE | 外部代码 | string | 如：10 |
| 9 | ORDERNO | 排序 | string | 如：10 |
| 10 | RESERVEFIELD1 | 扩展字段信息 | string | 如： |
| 节点名称：instlist汇总信息类型：array< object> 报告仪器 | 节点名称：instlist汇总信息类型：array< object> 报告仪器 | 节点名称：instlist汇总信息类型：array< object> 报告仪器 | 节点名称：instlist汇总信息类型：array< object> 报告仪器 | 节点名称：instlist汇总信息类型：array< object> 报告仪器 |
| 1 | NODENO | 节点编码 | string | 如：HISITEM_STATUS |
| 2 | NODENAME | 节点名称 | string | 如：状态 |
| 3 | CHILDNODES | 子节点名称 | array<object> |  |
| 1 | CODE | 字典代码 | string | 如：1 |
| 2 | NAME | 字典名称 | string | 如：血 |
| 3 | MEMCODE1 | 输入码一 | string | 如：1 |
| 4 | MEMCODE2 | 输入码二 | string | 如：X |
| 5 | DICID | 标准编码 | string | 如：1 |
| 6 | DICTYPE | 字典分类 | string | 如：样本类型 |
| 7 | SUBSYSCODE | 子系统分类 | string | 如：LIMS |
| 8 | EXTERNCODE | 外部代码 | string | 如：10 |
| 9 | ORDERNO | 排序 | string | 如：0 |
| 10 | RESERVEFIELD1 | 扩展字段信息 | string | 如：null |



Json返回数据格式：

代码实现：

SLAVE获取检验小组（classcode=检测小组）、检验分类（classcode=检验分类）、默认标本（classcode=样本类型），slave_p中界面类型（classcode=仪器分类），SYS_DEPT获取科室名称（DEPTTYPE='3'），仪器分类使用comDicData中的getIstrumentSortStatus方法（都有公共方法，使用公共方法）

表结构：SLAVE _P\SLAVE\SYS_DEPT

##### A03	报告单格式初始化

接口说明：获取报告单格式初始化

请求URL：../instrument/request/getreportinitdata

代码文件：winning.lis.instrument.service.InstrumentService

示例URL： 

http://192.168.10.73:15011/lis/instrument/request/getreportinitdata?hospitalcode=9999

原型参考：



接口入参： 

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码 |
| 2 | subsyscode | 子系统分类 | string | 传入字典分类代码，允许为空
传入为空时获取当前登录者部门代码 |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | REPORTSTYLEID | 流水号 | string | 如：1 |
| 2 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 3 | SUBSYSCODE | 子系统分类 | string | 如：LIMS |
| 4 | REPORTTYPE | 类型 | string | 1 报告打印格式，2标签打印格式，3统计报表打印格式 |
| 5 | REPORTFILENAME | 报表文件名 | string | 如：模板一 |
| 6 | TEMPLATE | 报表文件流存储字段 | string | 如： |
| 7 | MEMO | 备注 | string | 如：测试 |
| 8 | MODIFYDATE | 修改时间 | string | 如：2018:12:22 20:33:34 |
| 9 | MODIFYUSERID | 修改ID | string | 如：1 |
| 10 | MODIFYUSERNAME | 修改者 | string | 如：张三 |
| 11 | MODIFYBYCOMPUTERNAME | 修改电脑名 | string | 如：zs |
| 12 | MODIFYCOMPUTERIP | 修改IP | string | 如：127.0.01 |



Json返回数据格式：

代码实现：

LIS_REPORTSTYLE表中获取信息

#### B 业务类

##### B01	获取分类仪器列表

接口说明：获取分类仪器列表

请求URL：../instrument/request/getinstrumentlist

代码文件：winning.lis.instrument.service.InstrumentService

示例URL： 

http://192.168.10.73:15011/lis/instrument/request/getinstrumentlist?hospitalcode=9999&nodeno=INSTITEM_STATUS&selectvalue=1

原型参考：





接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | nodeno | 选项ID | string | 不允许为空，如：INSTITEM_EXAMINE |
| 3 | selectvalue | 选择参数 | string | 不允许为空，在一级菜单“状态”下，1-全部项目、2-在用项目，3-已停用项目，其他菜单下为当前选中项的code |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：instrumentlist 汇总信息 类型： array<object> | 节点名称：instrumentlist 汇总信息 类型： array<object> | 节点名称：instrumentlist 汇总信息 类型： array<object> | 节点名称：instrumentlist 汇总信息 类型： array<object> | 节点名称：instrumentlist 汇总信息 类型： array<object> |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | INSTID | 仪器ID | int | 如：3 |
| 3 | INSTCODE | 仪器编码 | string | 如：HX-21 |
| 4 | INSTNAME | 仪器名称 | string | 如：HX-21 |
| 5 | DEPTNO | 部门代码 | string | 如：2110 |
| 6 | DEPTNAME | 部门名称 | string | 如：医学检验科 |
| 7 | EXAMCODE | 检验分类 | int | 如：10 |
| 8 | EXAMNAME | 检验分类名称 | string | 如：微生物 |
| 9 | COMPUTER_NAME | 主机名 | string | 如：ZHUMX |
| 10 | INSTGROUP | 仪器分组 | string | 如：02 |
| 11 | INSTGROUPNAME | 仪器分组名称 | string | 如：检验组 |
| 12 | STOPFLAG | 停用标志 | string | 0 在用 1 停用 |



Json返回数据格式：



代码实现：

LIS_INSTRUMENT表中获取信息，SLAVE获取检验分类（classcode=‘检验分类’）和仪器分组（classcode=‘检测小组’），SYS_DEPT获取科室名称

表结构：LIS_INSTRUMENT\SLAVE\SYS_DEPT

##### B02	仪器搜索

接口说明： 仪器搜索

请求URL：../instrument/request/searchinst

代码文件：winning.lis.instrument.service.InstrumentService

示例URL： 

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | searchtext | 搜索内容 | string | 允许为空 ，为空，默认获取全部 |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：instrumentlist 汇总信息 类型： array<object> | 节点名称：instrumentlist 汇总信息 类型： array<object> | 节点名称：instrumentlist 汇总信息 类型： array<object> | 节点名称：instrumentlist 汇总信息 类型： array<object> | 节点名称：instrumentlist 汇总信息 类型： array<object> |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | INSTID | 仪器ID | int | 如：3 |
| 3 | INSTCODE | 仪器编码 | string | 如：HX-21 |
| 4 | INSTNAME | 仪器名称 | string | 如：HX-21 |
| 5 | DEPTNO | 部门代码 | string | 如：2110 |
| 6 | DEPTNAME | 部门名称 | string | 如：医学检验科 |
| 7 | EXAMCODE | 检验分类 | int | 如：10 |
| 8 | EXAMNAME | 检验分类名称 | string | 如：微生物 |
| 9 | COMPUTER_NAME | 主机名 | string | 如：ZHUMX |
| 10 | INSTGROUP | 仪器分组 | string | 如：02 |
| 11 | INSTGROUPNAME | 仪器分组名称 | string | 如：检验组 |
| 12 | STOPFLAG | 停用标志 | string | 0 在用 1 停用 |



Json返回数据格式：

代码实现：

依据仪器编码、仪器名称、仪器ID、主机名检索仪器信息

表结构：LIS_INSTRUMENT\SLAVE\SYS_DEPT

##### B03 仪器停用与启用

接口说明：仪器停用与启用

请求URL：../instrument/request/setinststatus

代码文件：winning.lis.instrument.service.InstrumentService

示例URL： 

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | instid | 仪器ID | string | 不允许为空，如：5 |
| 3 | instname | 仪器名称 | string | 不允许为空，如：生化1 |
| 4 | stopflag | 停用标志 | string | 不允许为空，0 在用，1 停用 |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage |
| 1 | type | 消息类型 | string | SUCCESS-成功 
ERROR-系统出现异常或错误
FAILT-业务逻辑处理失败 |
| 2 | messageCode | 消息代码 | string | 用于区分消息代码 |
| 3 | message | 消息内容 | string | type=ERROR时则为系统错误消息提示内容
type=FAILT时则为业务提示信息 |
| 4 | link | URL链接 | string | 用于提示用户后可以点击查看详情信息，默认为空 |
| 5 | data | 数据集合 | object | 用于存储返回的数据 |



代码实现：

LIS_INSTRUMENT表中对应仪器的停用状态设置

表结构：LIS_INSTRUMENT

##### B04	仪器删除

接口说明：仪器删除

请求URL：../instrument/request/deleteinstrument

代码文件：winning.lis.instrument.service.InstrumentService

示例URL： http://192.168.10.73:15011/lis/instrument/request/addinstrument?hospitalcode=9999&searchtext=10

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | instid | 仪器编码 | string | 不允许为空，如：PA-900 |
| 3 | instname | 仪器名称 | string | 不允许为空，如：PA-900 |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage |
| 1 | type | 消息类型 | string | SUCCESS-成功 
ERROR-系统出现异常或错误
FAILT-业务逻辑处理失败 |
| 2 | messageCode | 消息代码 | string | 用于区分消息代码 |
| 3 | message | 消息内容 | string | type=ERROR时则为系统错误消息提示内容
type=FAILT时则为业务提示信息 |
| 4 | link | URL链接 | string | 用于提示用户后可以点击查看详情信息，默认为空 |
| 5 | data | 数据集合 | object | 用于存储返回的数据 |



Json返回数据格式：



代码实现：

首先判断仪器有没有配置了收费项目，如果配置了收费项目，提示不允许删除，如果没有配置对应的收费项目，直接删除

表结构：LIS_INSTRUMENT\ LIS_INSTORDER

##### B05	仪器添加

接口说明：仪器添加

请求URL：../instrument/request/addinstrument

代码文件：winning.lis.instrument.service.InstrumentService

示例URL：

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | instcode | 仪器编码 | string | 不允许为空，如：PA-900 |
| 3 | instname | 仪器名称 | string | 不允许为空，如：PA-900 |
| 4 | instinfo | 仪器信息 | object | 插入的仪器的信息 |
| 1 | computer_name | 电脑主机名称 | string | 允许为空，如：sct |
| 2 | reportinstr | 报告仪器ID | string | 允许为空，如：2 |
| 3 | examcode | 检验分类 | string | 允许为空，如：2 |
| 4 | instgroup | 工作组别 | string | 允许为空，如：8 |
| 5 | defsample | 默认标本 | string | 允许为空，如：6 |
| 6 | deptno | 部门代码 | string | 允许为空，如：2 |
| 7 | insttype | 界面类型 | string | 允许为空，如：CG |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：instrumentlist 汇总信息 类型： array<object> | 节点名称：instrumentlist 汇总信息 类型： array<object> | 节点名称：instrumentlist 汇总信息 类型： array<object> | 节点名称：instrumentlist 汇总信息 类型： array<object> | 节点名称：instrumentlist 汇总信息 类型： array<object> |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | INSTID | 仪器ID | int | 如：3 |
| 3 | INSTCODE | 仪器编码 | string | 如：HX-21 |
| 4 | INSTNAME | 仪器名称 | string | 如：HX-21 |
| 5 | DEPTNO | 部门代码 | string | 如：2110 |
| 6 | DEPTNAME | 部门名称 | string | 如：医学检验科 |
| 7 | EXAMCODE | 检验分类 | int | 如：10 |
| 8 | EXAMNAME | 检验分类名称 | string | 如：微生物 |
| 9 | COMPUTER_NAME | 主机名 | string | 如：ZHUMX |
| 10 | INSTGROUP | 仪器分组 | string | 如：02 |
| 11 | INSTGROUPNAME | 仪器分组名称 | string | 如：检验组 |
| 12 | STOPFLAG | 停用标志 | string | 0 在用 1 停用 |



Json返回数据格式：



代码实现：

判断仪器编码是否存在，存在提示不允许添加，判断仪器名称是否存在，存在提示不允许添加

LIS_INSTRUMENT表中获取信息，SLAVE获取检验小组和检验分类，SYS_DEPT获取科室名称

表结构：LIS_INSTRUMENT\SLAVE\SYS_DEPT

##### B06	获取被复制仪器列表-（暂时不用，调其他接口）

接口说明：获取复制仪器列表

请求URL：../instrument/request/getcopyinst

代码文件：winning.lis.instrument.service.InstrumentService

示例URL： 



原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | copytype | 复制类型 | string | 不允许为空，复制本地仪器--0，复制标准库仪器--1 |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：instrumentinfo 汇总信息 类型： array<object> | 节点名称：instrumentinfo 汇总信息 类型： array<object> | 节点名称：instrumentinfo 汇总信息 类型： array<object> | 节点名称：instrumentinfo 汇总信息 类型： array<object> | 节点名称：instrumentinfo 汇总信息 类型： array<object> |
| 1 | INSTID | 仪器ID | int | 如：3 |
| 2 | INSTCODE | 仪器编码 | string | 如：HX-21 |
| 3 | INSTNAME | 仪器名称 | string | 如：HX-21 |



Json返回数据格式：



代码实现：

根据复制类型，获取仪器列表；如果复制类型为复制本地仪器，从LIS_INSTRUMENT中获取仪器，如果复制类型为复制标准库仪器，从LIS_INSTRUMENT_P中获取仪器

表结构：LIS_INSTRUMENT\ LIS_INSTRUMENT_P

##### B07	复制仪器

接口说明：获取复制仪器列表

请求URL：../instrument/request/copyinst

代码文件：winning.lis.instrument.service.InstrumentService

示例URL： 

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | copytype | 复制类型 | string | 不允许为空，复制本地仪器--0，复制标准库仪器--1 |
| 节点名称：instinfo    仪器信息节点    类型：array<string>  不能为空 | 节点名称：instinfo    仪器信息节点    类型：array<string>  不能为空 | 节点名称：instinfo    仪器信息节点    类型：array<string>  不能为空 | 节点名称：instinfo    仪器信息节点    类型：array<string>  不能为空 | 节点名称：instinfo    仪器信息节点    类型：array<string>  不能为空 |
| 1 | instid | 被复制仪器ID | string | 不允许为空，如：AU5800 |
| 2 | instcode | 被复制仪器编码 | string | 不允许为空，如：AU5800 |
| 3 | instname | 被复制仪器名称 | string | 不允许为空，如：生化 |
| 4 | newinstcode | 新仪器编码 | string | 不允许为空，如：AU581 |
| 5 | newinstname | 新仪器名称 | string | 不允许为空，如：生化2 |
| 节点名称：copyinfo     同步的复制信息   类型：object  可以为空 | 节点名称：copyinfo     同步的复制信息   类型：object  可以为空 | 节点名称：copyinfo     同步的复制信息   类型：object  可以为空 | 节点名称：copyinfo     同步的复制信息   类型：object  可以为空 | 节点名称：copyinfo     同步的复制信息   类型：object  可以为空 |
| 1 | institem | 仪器项目 | string | 允许为空，为空，默认为0，不选中--0，选中为--1 |
| 2 | normal | 报告参考值 | string | 允许为空，为空，默认为0，不选中--0，选中为--1 |
| 3 | panic | 危急值参考值 | string | 允许为空，为空，默认为0，不选中--0，选中为--1 |
| 4 | infectious | 传染病参考值 | string | 允许为空，为空，默认为0，不选中--0，选中为--1 |
| 5 | formular | 计算公式 | string | 允许为空，为空，默认为0，不选中--0，选中为--1 |
| 6 | changemean | 转义项目 | string | 允许为空，为空，默认为0，不选中--0，选中为--1 |
| 7 | condition | 审核条件 | string | 允许为空，为空，默认为0，不选中--0，选中为--1 |
| 8 | groupitem | 组合项目 | string | 允许为空，为空，默认为0，不选中--0，选中为--1 |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：instrumentinfo 汇总信息 类型： array<object> | 节点名称：instrumentinfo 汇总信息 类型： array<object> | 节点名称：instrumentinfo 汇总信息 类型： array<object> | 节点名称：instrumentinfo 汇总信息 类型： array<object> | 节点名称：instrumentinfo 汇总信息 类型： array<object> |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | INSTID | 仪器ID | int | 如：3 |
| 3 | INSTCODE | 仪器编码 | string | 如：HX-21 |
| 4 | INSTNAME | 仪器名称 | string | 如：HX-21 |
| 5 | DEPTNO | 部门代码 | string | 如：2110 |
| 6 | DEPTNAME | 部门名称 | string | 如：医学检验科 |
| 7 | EXAMCODE | 检验分类 | int | 如：10 |
| 8 | EXAMNAME | 检验分类名称 | string | 如：微生物 |
| 9 | COMPUTER_NAME | 主机名 | string | 如：ZHUMX |
| 10 | INSTGROUP | 仪器分组 | string | 如：02 |
| 11 | INSTGROUPNAME | 仪器分组名称 | string | 如：检验组 |
| 12 | STOPFLAG | 停用标志 | string | 0 在用 1 停用 |



Json返回数据格式：



代码实现：

前端需要控制新增加的仪器编码和仪器名称不允许重复，后端校验仪器编码和仪器名称是否已经存在，如果存在提示前端重新输入仪器编码或者仪器名称

根据复制类型，如果复制类型为复制本地仪器，从LIS_INSTRUMENT中获取仪器，如果复制类型为复制标准库仪器，从LIS_INSTRUMENT_P中获取仪器，根据复制类型从不同的同步信息表中复制对应的仪器配置条件到对应的表中

表结构：（表结构不全，暂留）

##### B08	获取仪器信息

接口说明：获取仪器信息

请求URL：../instrument/request/getinstinfo

代码文件：winning.lis.instrument.service.InstrumentService

示例URL： 



原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | instid | 仪器ID | string | 不允许为空，如：AU5800 |
| 3 | instname | 仪器名称 | string | 不允许为空，如：生化1 |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：instrumentinfo 汇总信息 类型： object | 节点名称：instrumentinfo 汇总信息 类型： object | 节点名称：instrumentinfo 汇总信息 类型： object | 节点名称：instrumentinfo 汇总信息 类型： object | 节点名称：instrumentinfo 汇总信息 类型： object |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | INSTID | 仪器ID | int | 如：3 |
| 3 | INSTCODE | 仪器编码 | string | 如：HX-21 |
| 4 | INSTNAME | 仪器名称 | string | 如：HX-21 |
| 5 | COMPUTER_NAME | 主机名 | string | 如：xx21 |
| 6 | DEPTNO | 部门代码 | string | 如：2110 |
| 7 | DEPTNAME | 部门名称 | string | 如：医学检验科 |
| 8 | EXAMCODE | 检验分类 | int | 如：10 |
| 9 | EXAMNAME | 检验分类名称 | string | 如：微生物 |
| 10 | INSTTYPE | 仪器分类（界面类型） | string | 如：02 |
| 11 | INSTTYPENAME | 仪器分类名称 | string | 如：6 |
| 12 | REPORTINSTR | 报告仪器ID | string | 如：3 |
| 13 | REPORTINSTRNAME | 报告仪器名称 | string | 如：生化2 |
| 14 | REPORTINSTRCODE | 报告仪器编码 | string | 如：333 |
| 15 | DEFSAMPLE | 默认标本 | string | 如：2 |
| 16 | DEFSAMPLENAME | 默认标本名称 | string | 如：血 |
| 17 | INSTGROUP | 仪器分组 | string | 如：3 |
| 18 | INSTGROUPNAME | 仪器分组名称 | string | 如：检验组 |
| 19 | STOPFLAG | 停用标志 | string | 0 在用 1 停用 |
| 20 | REPORTTITLE | 报告标题 | string | 如：上海六院 |
| 21 | MZQSH | 门诊起始号 | string | 如：12 |
| 22 | ZYQSH | 住院起始号 | string | 如：12 |
| 23 | JZQSH | 急诊起始样本号 | string | 如：12 |
| 24 | TJQSH | 体检起始号 | string | 如：12 |
| 25 | COMM_PORT | COM口 | string |  |
| 26 | BAUD_RATE | 波特率 | string |  |
| 27 | BYTE_SIZE | 数据位 | string |  |
| 28 | PARITY | 奇偶校验位 | string |  |
| 29 | STOP_BITS | 停止位 | string |  |
| 30 | F_HARDWARE | 硬件握手 | string |  |
| 31 | F_OUTX | 传送握手 | string |  |
| 32 | F_INX | 接收握手 | string |  |
| 33 | XOFF_LIM | XOFF值阈值 | string |  |
| 34 | XON_LIM | XON值阈值 | string |  |
| 35 | IP | IP地址 | string |  |
| 36 | PORT | IP端口地址 | string |  |
| 37 | FILENAME | 文件路径 | string |  |
| 38 | DLLNAME | 驱动文件 | string |  |
| 节点名称：reportstyle    打印描述设置  类型： array<string> | 节点名称：reportstyle    打印描述设置  类型： array<string> | 节点名称：reportstyle    打印描述设置  类型： array<string> | 节点名称：reportstyle    打印描述设置  类型： array<string> | 节点名称：reportstyle    打印描述设置  类型： array<string> |
| 1 | SERIALNO | 流水号 | string | 如：12 |
| 2 | INSTID | 仪器ID | string | 如：XH-21 |
| 3 | NROW | 行数 | string | 如：3 |
| 4 | NCOLUMN | 列数 | string | 如：6 |
| 5 | REPORTSTYLEID | 报表文件ID | string | 如：2 |
| 6 | REPORTFILENAME | 报表文件名 | string | 如：模板一 |
| 节点名称：instsetting 仪器通讯设置  类型： object  --暂时不定， | 节点名称：instsetting 仪器通讯设置  类型： object  --暂时不定， | 节点名称：instsetting 仪器通讯设置  类型： object  --暂时不定， | 节点名称：instsetting 仪器通讯设置  类型： object  --暂时不定， | 节点名称：instsetting 仪器通讯设置  类型： object  --暂时不定， |
|  | INSTID |  |  |  |
|  | COMM_PORT | COM口 |  |  |
|  | BAUD_RATE | 波特率 |  |  |
|  | BYTE_SIZE | 数据位 |  |  |
|  | PARITY | 奇偶校验位 |  |  |
|  | STOP_BITS | 停止位 |  |  |
|  | F_HARDWARE | 硬件握手 |  |  |
|  | XON_CHAR | 指定发送和接收XON值 |  |  |
|  | XOFF_CHAR | 指定发送和接收XOFF值 |  |  |
|  | DRIVER_PROG | 驱动路径 |  |  |
|  |  |  |  |  |
|  |  |  |  |  |
|  |  |  |  |  |



Json返回数据格式：



代码实现：

根据仪器ID获取仪器，LIS_REPORTSTYLE表中获取仪器打印模板的设置， SLAVE获取检验小组（classcode=检测小组）、检验分类（classcode=检验分类）、默认标本（classcode=样本类型），SLAVE_P中界面类型（classcode=仪器分类），SYS_DEPT获取科室名称（DEPTTYPE='3'）

表结构：LIS_INSTRUMENT\LIS_REPORTSTYLE\SLAVE\SLAVE_P\SYS_DEPT

##### B09	修改仪器信息

接口说明：修改仪器信息

请求URL：../instrument/request/alterinstinfo

代码文件：winning.lis.instrument.service.InstrumentService

示例URL： 



原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：instrumentinfo 汇总信息 类型： object | 节点名称：instrumentinfo 汇总信息 类型： object | 节点名称：instrumentinfo 汇总信息 类型： object | 节点名称：instrumentinfo 汇总信息 类型： object | 节点名称：instrumentinfo 汇总信息 类型： object |
| 1 | hospitalcode | 医疗机构代码 | string | 如：9999 |
| 2 | instid | 仪器ID | int | 不允许为空，如：3 |
| 3 | instcode | 仪器编码 | string | 不允许为空，如：HX-21 |
| 4 | instname | 仪器名称 | string | 不允许为空，如：HX-21 |
| 5 | computer_name | 主机名 | string | 允许为空，如：xx21 |
| 6 | deptno | 部门代码 | string | 允许为空，如：2110 |
| 7 | examcode | 检验分类 | int | 允许为空，如：10 |
| 8 | insttype | 仪器分类（界面类型） | string | 允许为空，如：02 |
| 9 | reportinstr | 报告仪器ID | string | 允许为空，如：3 |
| 10 | defsample | 默认标本 | string | 允许为空，如：2 |
| 11 | instgroup | 仪器分组 | string | 允许为空，如：3 |
| 12 | stopflag | 停用标志 | string | 0 在用 1 停用 |
| 13 | reporttitle | 报告标题 | string | 允许为空，如：上海六院 |
| 14 | mzqsh | 门诊起始号 | string | 允许为空，如：12 |
| 15 | zyqsh | 住院起始号 | string | 允许为空，如：12 |
| 16 | jzqsh | 急诊起始样本号 | string | 允许为空，如：12 |
| 17 | tjqsh | 体检起始号 | string | 允许为空，如：12 |
| 节点名称：reportstyle  打印描述设置  类型：array<string> 节点可以为空，不为空对应字段必须存在 | 节点名称：reportstyle  打印描述设置  类型：array<string> 节点可以为空，不为空对应字段必须存在 | 节点名称：reportstyle  打印描述设置  类型：array<string> 节点可以为空，不为空对应字段必须存在 | 节点名称：reportstyle  打印描述设置  类型：array<string> 节点可以为空，不为空对应字段必须存在 | 节点名称：reportstyle  打印描述设置  类型：array<string> 节点可以为空，不为空对应字段必须存在 |
| 1 | serialno | 流水号 | string | 不允许为空，如果是新增模板，传0，如：12 |
| 2 | instid | 仪器ID | string | 不允许为空，如：XH-21 |
| 3 | nrow | 行数 | string | 允许为空，如：3 |
| 4 | ncolumn | 列数 | string | 允许为空，如：6 |
| 5 | reportstyleid | 报表文件ID | string | 不允许为空，如：2 |
| 6 | reportfilename | 报表文件名 | string | 不允许为空，如：模板一 |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：instrumentinfo 汇总信息 类型： object | 节点名称：instrumentinfo 汇总信息 类型： object | 节点名称：instrumentinfo 汇总信息 类型： object | 节点名称：instrumentinfo 汇总信息 类型： object | 节点名称：instrumentinfo 汇总信息 类型： object |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | INSTID | 仪器ID | int | 如：3 |
| 3 | INSTCODE | 仪器编码 | string | 如：HX-21 |
| 4 | INSTNAME | 仪器名称 | string | 如：HX-21 |
| 5 | COMPUTER_NAME | 主机名 | string | 如：xx21 |
| 6 | DEPTNO | 部门代码 | string | 如：2110 |
| 7 | DEPTNAME | 部门名称 | string | 如：医学检验科 |
| 8 | EXAMCODE | 检验分类 | int | 如：10 |
| 9 | EXAMNAME | 检验分类名称 | string | 如：微生物 |
| 10 | INSTTYPE | 仪器分类（界面类型） | string | 如：02 |
| 11 | INSTTYPENAME | 仪器分类名称 | string | 如：6 |
| 12 | REPORTINSTR | 报告仪器ID | string | 如：3 |
| 13 | REPORTINSTRNAME | 报告仪器名称 | string | 如：生化2 |
| 14 | REPORTINSTRCODE | 报告仪器编码 | string | 如：333 |
| 15 | DEFSAMPLE | 默认标本 | string | 如：2 |
| 16 | DEFSAMPLENAME | 默认标本名称 | string | 如：血 |
| 17 | INSTGROUP | 仪器分组 | string | 如：3 |
| 18 | INSTGROUPNAME | 仪器分组名称 | string | 如：检验组 |
| 19 | STOPFLAG | 停用标志 | string | 0 在用 1 停用 |
| 20 | REPORTTITLE | 报告标题 | string | 如：上海六院 |
| 21 | MZQSH | 门诊起始号 | string | 如：12 |
| 22 | ZYQSH | 住院起始号 | string | 如：12 |
| 23 | JZQSH | 急诊起始样本号 | string | 如：12 |
| 24 | TJQSH | 体检起始号 | string | 如：12 |
| 节点名称：REPORTSTYLE    打印描述设置  类型： array<string> | 节点名称：REPORTSTYLE    打印描述设置  类型： array<string> | 节点名称：REPORTSTYLE    打印描述设置  类型： array<string> | 节点名称：REPORTSTYLE    打印描述设置  类型： array<string> | 节点名称：REPORTSTYLE    打印描述设置  类型： array<string> |
| 1 | SERIALNO | 流水号 | string | 如：12 |
| 2 | INSTID | 仪器ID | string | 如：XH-21 |
| 3 | NROW | 行数 | string | 如：3 |
| 4 | NCOLUMN | 列数 | string | 如：6 |
| 5 | REPORTSTYLEID | 报表文件ID | string | 如：2 |
| 6 | REPORTFILENAME | 报表文件名 | string | 如：模板一 |



Json返回数据格式：



代码实现：

根据仪器ID获取仪器，LIS_REPORTSTYLE表中获取仪器打印模板的设置， SLAVE获取检验小组（classcode=检测小组）、检验分类（classcode=检验分类）、默认标本（classcode=样本类型），SLAVE_P中界面类型（classcode=仪器分类），SYS_DEPT获取科室名称（DEPTTYPE='3'）

表结构：LIS_INSTRUMENT\LIS_REPORTSTYLE\SLAVE\SLAVE_P\SYS_DEPT

##### B10	添加报告单格式（删除不用）

接口说明：添加报告单

请求URL：../instrument/request/addreport

代码文件：winning.lis.instrument.service.InstrumentService

示例URL： 

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 如：9999 |
| 2 | instid | 仪器ID | int | 不允许为空，如：3 |
| 3 | instname | 仪器名称 | string | 不允许为空，如：HX-21 |
| 4 | instcode | 仪器编码 | string | 如：HX-21 |
| 5 | nrow | 行数 | string | 允许为空，如：3 |
| 6 | ncolumn | 列数 | string | 允许为空，如：6 |
| 7 | reportstyleid | 报表文件ID | string | 不允许为空，如：2 |
| 8 | reportfilename | 报表文件名 | string | 不允许为空，如：模板一 |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | INSTID | 仪器ID | int | 如：3 |
| 3 | INSTCODE | 仪器编码 | string | 如：HX-21 |
| 4 | INSTNAME | 仪器名称 | string | 如：HX-21 |
| 5 | SERIALNO | 流水号 | string | 如：12 |
| 6 | INSTID | 仪器ID | string | 如：XH-21 |
| 7 | NROW | 行数 | string | 如：3 |
| 8 | NCOLUMN | 列数 | string | 如：6 |
| 9 | REPORTSTYLEID | 报表文件ID | string | 如：2 |
| 10 | REPORTFILENAME | 报表文件名 | string | 如：模板一 |



Json返回数据格式：



代码实现：

根据仪器ID获取仪器，LIS_INSTREPORTSTYLE表中获取仪器打印模板的设置

表结构：LIS_INSTREPORTSTYLE

##### B10	修改仪器通讯信息

接口说明：修改仪器通讯信息

请求URL：../instrument/request/updateinstcominfo

代码文件：winning.lis.instrument.service.InstrumentService

示例URL： 

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 如：9999 |
| 2 | instid | 仪器ID | int | 不允许为空，如：3 |
| 3 | instname | 仪器名称 | string | 不允许为空，如：HX-21 |
| 4 | comm_port | COM口 | string | 允许为空，如：HX-21 |
| 5 | baud_rate | 波特率 | string | 允许为空，如：HX-21 |
| 6 | byte_size | 数据位 | string | 允许为空，如：HX-21 |
| 7 | parity | 奇偶校验位 | string | 允许为空，如：HX-21 |
| 8 | stop_bits | 停止位 | string | 允许为空，如：HX-21 |
| 9 | f_hardware | 硬件握手 | string | 允许为空，如：HX-21 |
| 10 | f_outx | 传送握手 | string | 允许为空，如：HX-21 |
| 11 | f_inx | 接收握手 | string | 允许为空，如：HX-21 |
| 12 | xoff_lim | XOFF值阈值 | string | 允许为空，如：HX-21 |
| 13 | xon_lim | XON值阈值 | string | 允许为空，如：HX-21 |
| 14 | ip | IP地址 | string | 允许为空，如：HX-21 |
| 15 | port | IP端口地址 | string | 允许为空，如：HX-21 |
| 16 | filename | 文件路径 | string | 允许为空，如：HX-21 |
| 17 | dllname | 驱动文件 | string | 允许为空，如：HX-21 |



接口出参【ResposeMessage.data-> object】：见B08  获取仪器信息



### 仪器项目危急值维护

#### V 模块接口视图

| NO. | 接口编号 | 接口名称 | 说明 |
| --- | --- | --- | --- |
| 1 | A01 | 下拉框信息初始化
../instrumentitem/request/getinstitemnormal | 下拉框信息初始化 |
| 2 | B01 | 获取仪器项目危急值
../instrumentitempanic/request/getinstitempanic | 获取仪器项目危急值 |
| 3 | B02 | 修改仪器项目默认危急值
../instrumentitempanic/request/alterdefaultpanic | 修改仪器项目默认危急值 |
| 4 | B03 | 修改仪器项目特殊危急值
../instrumentitempanic/request/alterotherpanic | 修改仪器项目特殊危急值 |
| 5 | B04 | 删除仪器项目特殊危急值
../instrumentitempanic/request/deleteotherpanic | 删除仪器项目特殊危急值 |
| 6 | B05 | 添加仪器项目特殊危急值
../instrumentitempanic/request/addotherpanic | 添加仪器项目特殊危急值 |



#### A 对外公布方法

##### A01	下拉框信息初始化

原型参考：





调用仪器项目的411.A01接口

#### B 业务类

##### B01	获取仪器项目危急值

接口说明：获取仪器项目危急值

请求URL：../institempanic/request/getinstitempanic

代码文件：winning.cmp.instrumentitempanic.service.InstrumentItemPanicService

示例URL： http://192.168.10.73:10004/lis/institempanic/request/getinstitempanic?hospitalcode=9999&instid=22&instname=SF810-1&itemcode=0101005&itemname=淋巴细胞百分比&itemnum=LYM%

原型参考：

接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | instid | 仪器ID | string | 不允许为空，如：5 |
| 3 | instname | 仪器名称 | string | 不允许为空，如;AU5800 |
| 4 | itemcode | 项目代码 | string | 不允许为空，如：0101002 |
| 5 | itemname | 项目名称 | string | 不允许为空，如：中性粒细胞绝对值 |
| 6 | itemnum | 项目标准编码 | string | 不允许为空，如：NEU# |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：iteminfo  项目信息    类型： object | 节点名称：iteminfo  项目信息    类型： object | 节点名称：iteminfo  项目信息    类型： object | 节点名称：iteminfo  项目信息    类型： object | 节点名称：iteminfo  项目信息    类型： object |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | INSTID | 仪器ID | int | 如：3 |
| 3 | ITEMCODE | 项目代码 | string | 如：0101002 |
| 4 | ITEMNAME | 项目名称 | string | 如：中性粒细胞绝对值 |
| 5 | ITEMNUM | 项目标准编码 | string | 如：NEU# |
| 节点名称：refertypedef   默认危急值    类型： object | 节点名称：refertypedef   默认危急值    类型： object | 节点名称：refertypedef   默认危急值    类型： object | 节点名称：refertypedef   默认危急值    类型： object | 节点名称：refertypedef   默认危急值    类型： object |
| 1 | LOWLIMIT | 危急下限 | string | 如：12.00 |
| 2 | UPPLIMIT | 危急上限 | string | 如：12.00 |
| 3 | CHARREFER | 危急字符 | string | 如：0 |
| 4 | CONTAINBOUND | 是否包含边界值 | string | 数字结果:1-包含边界 0-不包含边界 字符结果:1-包含 2-全匹配 |
| 节点名称：refertypelist   特殊危急值    类型： array<object> | 节点名称：refertypelist   特殊危急值    类型： array<object> | 节点名称：refertypelist   特殊危急值    类型： array<object> | 节点名称：refertypelist   特殊危急值    类型： array<object> | 节点名称：refertypelist   特殊危急值    类型： array<object> |
| 1 | SERIALNO | 序号 | string | 如：1 |
| 2 | LOWLIMIT | 参考下限 | string | 如：12.00 |
| 3 | UPPLIMIT | 参考上限 | string | 如：12.00 |
| 4 | CHARREFER | 字符参考值 | string | 如：0 |
| 5 | CONTAINBOUND | 是否包含边界值 | string | 数字结果:1-包含边界 0-不包含边界 字符结果:1-包含 2-全匹配 |
| 6 | REFERDESC | 参考区间描述 | string | 如：6.00-12.00 |
| 7 | PRINTREFER | 参考打印格式 | string | 以分号区分多个参考 |
| 8 | STOPFLAG | 停用标志 | string | 如：0 |
| 9 | EFFCONID | 生效条件ID | string | 如：22 |
| 10 | REFERTYPEDETAIL    危急值明细       array<object> | REFERTYPEDETAIL    危急值明细       array<object> | REFERTYPEDETAIL    危急值明细       array<object> | REFERTYPEDETAIL    危急值明细       array<object> |
|  |  |  |  |  |
| 1 | SERIALNODETAIL | 主键ID | string | 如：22 |
| 2 | ORDERNO | 序号 | int | 如：33 |
| 3 | LOGIC | 逻辑运算符 | string | 选择列表：和、或（AND,OR） |
| 4 | LOGICDESC | 运算符描述 | string | 如：三岁以前 |
| 5 | CONFIELDLIST | 查询子句分组表示 | string | 如：1-3表示子句1与子句3之间用括号组合起来,多个分组用','分割 |
| 6 | FIELDDEFINEID | 字段定义ID | string | 如：3 |
| 7 | FIELDEN | 字段英文名称 | string | 如：SEX |
| 8 | FIELDCN | 字段中文名称 | string | 如：性别 |
| 9 | RELATION | 关系运算符 | string | 选择列表：=、<>、>、<、>=、<=、包含、不包含（=、!=、>、<、>=、<=、IN、NOT IN） |
| 10 | RELATIONDESC | 关系运算符描述 | string | 如：大于 |
| 11 | VALUE | 值 | string | 如：2 |
| 12 | VALUEDESC | 值描述 | string | 如：女 |
| 13 | ISVARIABLE | 是否为动态值 | string | 只有日期字段才有动态值，1：为动态值 |
| 14 | VARIABLEVALUE | 动态内容 | string | 动态内容，分两组选择列表：本年、本季、本月、本周、前年、前季、前月、前周；第一天、最后一天、当前天 |
| 15 | ISHIGH | 查询方案级别 | int | 1是高级查询方案，=0是简单查询默认方案 |
| 16 | UPDATEFLAG | 数据访问级别 | string | 0:正常数据，-1:为删除数据，系统控制 |
| 17 | FATHERSERIALNO | 条件ID | int | 如：33 |



Json返回数据格式：



代码实现：

危急值分为默认值和特殊危急值两种，默认值在表LIS_INSTITEM中，特殊危急值在LIS_ITEMREFERANCE表中，其中REFERTYPE为危急值，COM_EFFCON_LIST为危急值的条件表，关联字段为EFFCONID，COM_EFFCON_ROUTINE为条件的明细表

表结构： LIS_INSTITEM\LIS_ITEMREFERANCE\COM_EFFCON_LIST\COM_EFFCON_ROUTINE

##### B02	修改仪器项目默认危急值

接口说明：修改仪器项目危急值

请求URL：../institempanic/request/updatedefaultpanic

代码文件：winning.cmp.institempanic.service.InstItemPanicService

示例URL：http://192.168.10.73:10004/lis/institempanic/request/updatedefaultpanic?hospitalcode=9999&instid=22&instname=SF810-1&itemcode=0101005&itemname=淋巴细胞百分比&itemnum=LYM%&lowlimit=15&upplimit=20&charrefer&containbound=1 

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | instid | 仪器ID | string | 不允许为空，如：5 |
| 3 | instname | 仪器名称 | string | 不允许为空，如;AU5800 |
| 4 | itemcode | 项目代码 | string | 不允许为空，如：0101002 |
| 5 | itemname | 项目名称 | string | 不允许为空，如：中性粒细胞绝对值 |
| 6 | itemnum | 项目标准编码 | string | 不允许为空，如：NEU# |
| 7 | lowlimit | 危急下限 | string | 如：12.00 |
| 8 | upplimit | 危急上限 | string | 如：12.00 |
| 9 | charrefer | 危急字符 | string | 如：0 |
| 10 | containbound | 是否包含边界值 | string | 数字结果:1-包含边界 0-不包含边界 字符结果:1-包含 2-全匹配 |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | INSTID | 仪器ID | int | 如：3 |
| 3 | ITEMCODE | 项目代码 | string | 如：0101002 |
| 4 | ITEMNAME | 项目名称 | string | 如：中性粒细胞绝对值 |
| 5 | ITEMNUM | 项目标准编码 | string | 如：NEU# |
| 6 | PANICLOW | 危急下限 | string | 如：12.00 |
| 7 | PANICHIGH | 危急上限 | string | 如：12.00 |
| 8 | PANICCHAR | 危急字符 | string | 如：0 |
| 9 | PANICCONTAINBOUND | 是否包含边界值 | string | 数字结果:1-包含边界 0-不包含边界 字符结果:1-包含 2-全匹配 |



Json返回数据格式：



代码实现：

默认值在表LIS_INSTITEM中，直接修改

表结构： LIS_INSTITEM

##### B03	修改仪器项目特殊危急值

接口说明：修改仪器项目特殊危急值

请求URL：../institempanic/request/updateotherpanic

代码文件：winning.cmp.institempanic.service.InstItemPanicService

示例URL： http://192.168.10.73:10004/lis/institempanic/request/updateotherpanic?hospitalcode=9999&instid=22&instname=SF810-1&itemcode=0101005&itemname=淋巴细胞百分比&itemnum=LYM%&itemreferance={"serialno":"8","lowlimit":"12.32","upplimit":"200","charrefer":"","referdesc":"12.32-23","effconid":"2"}&effconroutine=[{"routineserialno":"7","fielden":"SEX","fieldcn":"性别","value":"1","valuedesc":"男"}]

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | instid | 仪器ID | string | 不允许为空，如：5 |
| 3 | instname | 仪器名称 | string | 不允许为空，如;AU5800 |
| 4 | itemcode | 项目代码 | string | 不允许为空，如：0101002 |
| 5 | itemname | 项目名称 | string | 不允许为空，如：中性粒细胞绝对值 |
| 6 | itemnum | 项目标准编码 | string | 不允许为空，如：NEU# |
| 节点名称：itemreferance  危急值条件 类型： object  不能为空 | 节点名称：itemreferance  危急值条件 类型： object  不能为空 | 节点名称：itemreferance  危急值条件 类型： object  不能为空 | 节点名称：itemreferance  危急值条件 类型： object  不能为空 | 节点名称：itemreferance  危急值条件 类型： object  不能为空 |
| 1 | serialno | 序号 | int | 不允许为空，如：12 |
| 2 | lowlimit | 参考下限 | string | 允许为空，如：12.00 |
| 3 | upplimit | 参考上限 | string | 允许为空，如：12.00 |
| 4 | charrefer | 字符参考值 | string | 允许为空，如：0 |
| 5 | containbound | 是否包含边界值 | string | 允许为空，数字结果:1-包含边界 0-不包含边界 字符结果:1-包含 2-全匹配 |
| 6 | referdesc | 参考区间描述 | string | 允许为空，如：6.00-12.00 |
| 7 | printrefer | 参考打印格式 | string | 允许为空，以分号区分多个参考 |
| 8 | effconid | 条件ID | int | 允许为空，如：12 |
| 节点名称：effconroutine   危急值的明细规则 类型： array<object> 如果不添加明细或者明细没有变化，可以为空（下面节点字段可以没有），不为空按规则传 | 节点名称：effconroutine   危急值的明细规则 类型： array<object> 如果不添加明细或者明细没有变化，可以为空（下面节点字段可以没有），不为空按规则传 | 节点名称：effconroutine   危急值的明细规则 类型： array<object> 如果不添加明细或者明细没有变化，可以为空（下面节点字段可以没有），不为空按规则传 | 节点名称：effconroutine   危急值的明细规则 类型： array<object> 如果不添加明细或者明细没有变化，可以为空（下面节点字段可以没有），不为空按规则传 | 节点名称：effconroutine   危急值的明细规则 类型： array<object> 如果不添加明细或者明细没有变化，可以为空（下面节点字段可以没有），不为空按规则传 |
| 1 | routineserialno | 主键ID | int | 不允许为空，新增条件，传0，修改传明细的SERIALNO |
| 2 | fielden | 字段英文名称 | string | 不允许为空，如：12 |
| 3 | fieldcn | 字段中文名称 | string | 不允许为空，如：12 |
| 4 | value | 值 | string | 不允许为空，如：12 |
| 5 | valuedesc | 值描述 | string | 不允许为空，如：12 |
| 节点名称：ageunit   年龄明细规则 类型： object  有数据存在就直接传递，不为空按规则传 | 节点名称：ageunit   年龄明细规则 类型： object  有数据存在就直接传递，不为空按规则传 | 节点名称：ageunit   年龄明细规则 类型： object  有数据存在就直接传递，不为空按规则传 | 节点名称：ageunit   年龄明细规则 类型： object  有数据存在就直接传递，不为空按规则传 | 节点名称：ageunit   年龄明细规则 类型： object  有数据存在就直接传递，不为空按规则传 |
| 1 | agelow | 年龄下限 | string | 允许为空，如：12 |
| 2 | ageupp | 年龄上限 | string | 允许为空，如：12 |
| 3 | ageunit | 年龄单位 | string | 允许为空，如：12 |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：iteminfo  项目信息    类型： object | 节点名称：iteminfo  项目信息    类型： object | 节点名称：iteminfo  项目信息    类型： object | 节点名称：iteminfo  项目信息    类型： object | 节点名称：iteminfo  项目信息    类型： object |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | INSTID | 仪器ID | int | 如：3 |
| 3 | ITEMCODE | 项目代码 | string | 如：0101002 |
| 4 | ITEMNAME | 项目名称 | string | 如：中性粒细胞绝对值 |
| 5 | ITEMNUM | 项目标准编码 | string | 如：NEU# |
| 节点名称：refertypelist   特殊参考值    类型： array<object> | 节点名称：refertypelist   特殊参考值    类型： array<object> | 节点名称：refertypelist   特殊参考值    类型： array<object> | 节点名称：refertypelist   特殊参考值    类型： array<object> | 节点名称：refertypelist   特殊参考值    类型： array<object> |
| 1 | SERIALNO | 序号 | string | 如：1 |
| 2 | LOWLIMIT | 参考下限 | string | 如：12.00 |
| 3 | UPPLIMIT | 参考上限 | string | 如：12.00 |
| 4 | CHARREFER | 字符参考值 | string | 如：0 |
| 5 | CONTAINBOUND | 是否包含边界值 | string | 数字结果:1-包含边界 0-不包含边界 字符结果:1-包含 2-全匹配 |
| 6 | REFERDESC | 参考区间描述 | string | 如：6.00-12.00 |
| 7 | PRINTREFER | 参考打印格式 | string | 以分号区分多个参考 |
| 8 | STOPFLAG | 停用标志 | string | 如：0 |
| 9 | EFFCONID | 生效条件ID | string | 如：22 |
| 10 | REFERTYPEDETAIL    参考值明细       array<object> | REFERTYPEDETAIL    参考值明细       array<object> | REFERTYPEDETAIL    参考值明细       array<object> | REFERTYPEDETAIL    参考值明细       array<object> |
|  |  |  |  |  |
| 1 | SERIALNODETAIL | 主键ID | string | 如：22 |
| 2 | ORDERNO | 序号 | int | 如：33 |
| 3 | LOGIC | 逻辑运算符 | string | 选择列表：和、或（AND,OR） |
| 4 | LOGICDESC | 运算符描述 | string | 如：三岁以前 |
| 5 | CONFIELDLIST | 查询子句分组表示 | string | 如：1-3表示子句1与子句3之间用括号组合起来,多个分组用','分割 |
| 6 | FIELDDEFINEID | 字段定义ID | string | 如：3 |
| 7 | FIELDEN | 字段英文名称 | string | 如：SEX |
| 8 | FIELDCN | 字段中文名称 | string | 如：性别 |
| 9 | RELATION | 关系运算符 | string | 选择列表：=、<>、>、<、>=、<=、包含、不包含（=、!=、>、<、>=、<=、IN、NOT IN） |
| 10 | RELATIONDESC | 关系运算符描述 | string | 如：大于 |
| 11 | VALUE | 值 | string | 如：2 |
| 12 | VALUEDESC | 值描述 | string | 如：女 |
| 13 | ISVARIABLE | 是否为动态值 | string | 只有日期字段才有动态值，1：为动态值 |
| 14 | VARIABLEVALUE | 动态内容 | string | 动态内容，分两组选择列表：本年、本季、本月、本周、前年、前季、前月、前周；第一天、最后一天、当前天 |
| 15 | ISHIGH | 查询方案级别 | int | 1是高级查询方案，=0是简单查询默认方案 |
| 16 | UPDATEFLAG | 数据访问级别 | string | 0:正常数据，-1:为删除数据，系统控制 |
| 17 | FATHERSERIALNO | 条件ID | int | 如：33 |



Json返回数据格式：



代码实现：

在表LIS_ITEMREFERANCE修改规则，

如果LIS_ITEMREFERANCE中EFFCONID为空，需要添加COM_EFFCON_LIST，并将EFFCONID更新到LIS_ITEMREFERANCE中。

如果如果LIS_ITEMREFERANCE中EFFCONID不为空，则更新COM_EFFCON_ROUTINE表或者插入数据，并更新COM_EFFCON_LIST表

表结构：ITEMREFERANCE\COM_EFFCON_LIST\COM_EFFCON_ROUTINE

##### B04	删除仪器项目特殊危急值

接口说明：删除仪器项目特殊危急值（默认危急值不允许删除）

请求URL：../institempanic/request/deleteotherpanic

代码文件：winning.cmp.institempanic.service.InstItemPanicService

示例URL： http://192.168.10.73:10004/lis/institempanic/request/deleteotherpanic?hospitalcode=9999&instname=SF810-1&itemnum=LYM%&serialno=8&effconid=2

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | instname | 仪器名称 | string | 不允许为空，如;AU5800 |
| 3 | itemname | 项目名称 | string | 不允许为空，如：中性粒细胞绝对值 |
| 4 | serialno | 危急值序号 | string | 不允许为空，如：1 |
| 5 | effconid | 条件值序号 | string | 允许为空，如：1 |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage |
| 1 | type | 消息类型 | string | SUCCESS-成功 
ERROR-系统出现异常或错误
FAILT-业务逻辑处理失败 |
| 2 | messageCode | 消息代码 | string | 用于区分消息代码 |
| 3 | message | 消息内容 | string | type=ERROR时则为系统错误消息提示内容
type=FAILT时则为业务提示信息 |
| 4 | link | URL链接 | string | 用于提示用户后可以点击查看详情信息，默认为空 |
| 5 | data | 数据集合 | object | 用于存储返回的数据 |



Json返回数据格式：



代码实现：

危急值分为默认值和特殊危急值两种，默认值不允许删除，只能修改，特殊危急值在LIS_ITEMREFERANCE表中可以删除

表结构：LIS_ITEMREFERANCE

##### B05	添加仪器项目特殊危急值

接口说明：修改仪器项目特殊危急值

请求URL：../institempanic/request/addotherpanic

代码文件：winning.cmp.institempanic.service.InstItemPanicService

示例URL： http://192.168.10.73:10004/lis/institempanic/request/addotherpanic?hospitalcode=9999&instid=9&instname=SF810-1&itemcode=0101005&itemname=淋巴细胞百分比&itemnum=LYM%&itemreferance={"lowlimit":"12.98","upplimit":"45.32","charrefer":"","referdesc":"12.32-23"}&effconroutine=[{"fielden":"SEX","fieldcn":"性别","value":"2","valuedesc":"女"},{"fielden":"APPLYDEPTCODE","fieldcn":"申请科室","value":"1411","valuedesc":"儿一科"}]

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | instid | 仪器ID | string | 不允许为空，如：5 |
| 3 | instname | 仪器名称 | string | 不允许为空，如;AU5800 |
| 4 | itemcode | 项目代码 | string | 不允许为空，如：0101002 |
| 5 | itemname | 项目名称 | string | 不允许为空，如：中性粒细胞绝对值 |
| 6 | itemnum | 项目标准编码 | string | 不允许为空，如：NEU# |
| 节点名称：itemreferance   危急值条件 类型： object | 节点名称：itemreferance   危急值条件 类型： object | 节点名称：itemreferance   危急值条件 类型： object | 节点名称：itemreferance   危急值条件 类型： object | 节点名称：itemreferance   危急值条件 类型： object |
| 1 | lowlimit | 参考下限 | string | 允许为空，如：12.00 |
| 2 | upplimit | 参考上限 | string | 允许为空，如：12.00 |
| 3 | charrefer | 字符参考值 | string | 允许为空，如：0 |
| 4 | containbound | 是否包含边界值 | string | 允许为空，数字结果:1-包含边界 0-不包含边界 字符结果:1-包含 2-全匹配 |
| 5 | referdesc | 参考区间描述 | string | 允许为空，如：6.00-12.00 |
| 6 | printrefer | 多行参考值 | string | 允许为空，如：wec>20; |
| 节点名称：effconroutine   参考值明细规则 类型：array<object> 可以为空，如果不为空，按规则传 | 节点名称：effconroutine   参考值明细规则 类型：array<object> 可以为空，如果不为空，按规则传 | 节点名称：effconroutine   参考值明细规则 类型：array<object> 可以为空，如果不为空，按规则传 | 节点名称：effconroutine   参考值明细规则 类型：array<object> 可以为空，如果不为空，按规则传 | 节点名称：effconroutine   参考值明细规则 类型：array<object> 可以为空，如果不为空，按规则传 |
| 1 | fielden | 字段英文名称 | string | 不允许为空，如：12 |
| 2 | fieldcn | 字段中文名称 | string | 不允许为空，如：12 |
| 3 | value | 值 | string | 不允许为空，如：12 |
| 4 | valuedesc | 值描述 | string | 不允许为空，如：12 |
| 节点名称：ageunit   年龄明细规则 类型： object  有数据存在就直接传递，不为空按规则传 | 节点名称：ageunit   年龄明细规则 类型： object  有数据存在就直接传递，不为空按规则传 | 节点名称：ageunit   年龄明细规则 类型： object  有数据存在就直接传递，不为空按规则传 | 节点名称：ageunit   年龄明细规则 类型： object  有数据存在就直接传递，不为空按规则传 | 节点名称：ageunit   年龄明细规则 类型： object  有数据存在就直接传递，不为空按规则传 |
| 1 | agelow | 年龄下限 | string | 允许为空，如：12 |
| 2 | ageupp | 年龄上限 | string | 允许为空，如：12 |
| 3 | ageunit | 年龄单位 | string | 允许为空，如：12 |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：iteminfo  项目信息    类型： object | 节点名称：iteminfo  项目信息    类型： object | 节点名称：iteminfo  项目信息    类型： object | 节点名称：iteminfo  项目信息    类型： object | 节点名称：iteminfo  项目信息    类型： object |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | INSTID | 仪器ID | int | 如：3 |
| 3 | ITEMCODE | 项目代码 | string | 如：0101002 |
| 4 | ITEMNAME | 项目名称 | string | 如：中性粒细胞绝对值 |
| 5 | ITEMNUM | 项目标准编码 | string | 如：NEU# |
| 节点名称：refertypelist   特殊参考值    类型： array<object> | 节点名称：refertypelist   特殊参考值    类型： array<object> | 节点名称：refertypelist   特殊参考值    类型： array<object> | 节点名称：refertypelist   特殊参考值    类型： array<object> | 节点名称：refertypelist   特殊参考值    类型： array<object> |
| 1 | SERIALNO | 序号 | string | 如：1 |
| 2 | LOWLIMIT | 参考下限 | string | 如：12.00 |
| 3 | UPPLIMIT | 参考上限 | string | 如：12.00 |
| 4 | CHARREFER | 字符参考值 | string | 如：0 |
| 5 | CONTAINBOUND | 是否包含边界值 | string | 数字结果:1-包含边界 0-不包含边界 字符结果:1-包含 2-全匹配 |
| 6 | REFERDESC | 参考区间描述 | string | 如：6.00-12.00 |
| 7 | PRINTREFER | 参考打印格式 | string | 以分号区分多个参考 |
| 8 | STOPFLAG | 停用标志 | string | 如：0 |
| 9 | EFFCONID | 生效条件ID | string | 如：22 |
| 10 | REFERTYPEDETAIL    参考值明细       array<object> | REFERTYPEDETAIL    参考值明细       array<object> | REFERTYPEDETAIL    参考值明细       array<object> | REFERTYPEDETAIL    参考值明细       array<object> |
|  |  |  |  |  |
| 1 | ROUTINESERIALNO | 主键ID | string | 如：22 |
| 2 | ORDERNO | 序号 | int | 如：33 |
| 3 | LOGIC | 逻辑运算符 | string | 选择列表：和、或（AND,OR） |
| 4 | LOGICDESC | 运算符描述 | string | 如：三岁以前 |
| 5 | CONFIELDLIST | 查询子句分组表示 | string | 如：1-3表示子句1与子句3之间用括号组合起来,多个分组用','分割 |
| 6 | FIELDDEFINEID | 字段定义ID | string | 如：3 |
| 7 | FIELDEN | 字段英文名称 | string | 如：SEX |
| 8 | FIELDCN | 字段中文名称 | string | 如：性别 |
| 9 | RELATION | 关系运算符 | string | 选择列表：=、<>、>、<、>=、<=、包含、不包含（=、!=、>、<、>=、<=、IN、NOT IN） |
| 10 | RELATIONDESC | 关系运算符描述 | string | 如：大于 |
| 11 | VALUE | 值 | string | 如：2 |
| 12 | VALUEDESC | 值描述 | string | 如：女 |
| 13 | ISVARIABLE | 是否为动态值 | string | 只有日期字段才有动态值，1：为动态值 |
| 14 | VARIABLEVALUE | 动态内容 | string | 动态内容，分两组选择列表：本年、本季、本月、本周、前年、前季、前月、前周；第一天、最后一天、当前天 |
| 15 | ISHIGH | 查询方案级别 | int | 1是高级查询方案，=0是简单查询默认方案 |
| 16 | UPDATEFLAG | 数据访问级别 | string | 0:正常数据，-1:为删除数据，系统控制 |
| 17 | FATHEREFFCONID | 条件ID | int | 如：33 |



Json返回数据格式：



代码实现：

在LIS_ITEMREFERANCE表中插入记录，其中REFERTYPE为常规参考值，COM_EFFCON_LIST插入参考值的条件表，并将生成的EFFCONID更新到LIS_ITEMREFERANCE表中，在COM_EFFCON_ROUTINE表中插入数据，并将对应的信息拼接成对应的格式更新到LIS_ITEMREFERANCE表中。

表结构： LIS_ITEMREFERANCE\COM_EFFCON_LIST\COM_EFFCON_ROUTINE



### 仪器项目传染病维护

#### V 模块接口视图

| NO. | 接口编号 | 接口名称 | 说明 |
| --- | --- | --- | --- |
| 1 | A01 | 下拉框信息初始化 | 下拉框信息初始化 |
| 2 | B01 | 获取仪器项目传染病
../institeminfect/request/getinstiteminfect | 获取仪器项目传染值 |
| 3 | B02 | 修改仪器项目传染病
../institeminfect/request/updateinfect | 修改仪器项目传染病 |
| 4 | B03 | 删除仪器项目的传染病
../institeminfect/request/deleteinfect | 删除仪器项目的传染病 |
| 5 | B04 | 添加仪器项目传染病值
../institeminfect/request/addinfect | 添加仪器项目传染病值 |



#### A 对外公布方法

##### A01	下拉框信息初始化

原型参考：





调用仪器项目的411.A01接口

#### B 业务类

##### B01	获取仪器项目传染病

接口说明：获取仪器项目传染值

请求URL：../institeminfect/request/getinstiteminfect

代码文件：winning.cmp.institeminfect.service.InstItemInfectService

示例URL： 

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | instid | 仪器ID | string | 不允许为空，如：5 |
| 3 | instname | 仪器名称 | string | 不允许为空，如;AU5800 |
| 4 | itemcode | 项目代码 | string | 不允许为空，如：0101002 |
| 5 | itemname | 项目名称 | string | 不允许为空，如：中性粒细胞绝对值 |
| 6 | itemnum | 项目标准编码 | string | 不允许为空，如：NEU# |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：iteminfo  项目信息    类型： object | 节点名称：iteminfo  项目信息    类型： object | 节点名称：iteminfo  项目信息    类型： object | 节点名称：iteminfo  项目信息    类型： object | 节点名称：iteminfo  项目信息    类型： object |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | INSTID | 仪器ID | int | 如：3 |
| 3 | ITEMCODE | 项目代码 | string | 如：0101002 |
| 4 | ITEMNAME | 项目名称 | string | 如：中性粒细胞绝对值 |
| 5 | ITEMNUM | 项目标准编码 | string | 如：NEU# |
| 节点名称：refertypelist 传染值    类型： array<object> | 节点名称：refertypelist 传染值    类型： array<object> | 节点名称：refertypelist 传染值    类型： array<object> | 节点名称：refertypelist 传染值    类型： array<object> | 节点名称：refertypelist 传染值    类型： array<object> |
| 1 | SERIALNO | 序号 | string | 如：1 |
| 2 | LOWLIMIT | 参考下限 | string | 如：12.00 |
| 3 | UPPLIMIT | 参考上限 | string | 如：12.00 |
| 4 | CHARREFER | 字符参考值 | string | 如：0 |
| 5 | CONTAINBOUND | 是否包含边界值 | string | 数字结果:1-包含边界 0-不包含边界 字符结果:1-包含 2-全匹配 |
| 6 | REFERDESC | 参考区间描述 | string | 如：6.00-12.00 |
| 7 | PRINTREFER | 参考打印格式 | string | 以分号区分多个参考 |
| 8 | STOPFLAG | 停用标志 | string | 如：0 |
| 9 | EFFCONID | 生效条件ID | string | 如：22 |
| 10 | REFERTYPEDETAIL    传染病明细       array<object> | REFERTYPEDETAIL    传染病明细       array<object> | REFERTYPEDETAIL    传染病明细       array<object> | REFERTYPEDETAIL    传染病明细       array<object> |
|  |  |  |  |  |
| 1 | SERIALNODETAIL | 主键ID | string | 如：22 |
| 2 | ORDERNO | 序号 | int | 如：33 |
| 3 | LOGIC | 逻辑运算符 | string | 选择列表：和、或（AND,OR） |
| 4 | LOGICDESC | 运算符描述 | string | 如：三岁以前 |
| 5 | CONFIELDLIST | 查询子句分组表示 | string | 如：1-3表示子句1与子句3之间用括号组合起来,多个分组用','分割 |
| 6 | FIELDDEFINEID | 字段定义ID | string | 如：3 |
| 7 | FIELDEN | 字段英文名称 | string | 如：SEX |
| 8 | FIELDCN | 字段中文名称 | string | 如：性别 |
| 9 | RELATION | 关系运算符 | string | 选择列表：=、<>、>、<、>=、<=、包含、不包含（=、!=、>、<、>=、<=、IN、NOT IN） |
| 10 | RELATIONDESC | 关系运算符描述 | string | 如：大于 |
| 11 | VALUE | 值 | string | 如：2 |
| 12 | VALUEDESC | 值描述 | string | 如：女 |
| 13 | ISVARIABLE | 是否为动态值 | string | 只有日期字段才有动态值，1：为动态值 |
| 14 | VARIABLEVALUE | 动态内容 | string | 动态内容，分两组选择列表：本年、本季、本月、本周、前年、前季、前月、前周；第一天、最后一天、当前天 |
| 15 | ISHIGH | 查询方案级别 | int | 1是高级查询方案，=0是简单查询默认方案 |
| 16 | UPDATEFLAG | 数据访问级别 | string | 0:正常数据，-1:为删除数据，系统控制 |
| 17 | FATHERSERIALNO | 条件ID | int | 如：33 |



Json返回数据格式：



代码实现：

传染病在LIS_ITEMREFERANCE表中，其中REFERTYPE为传染病，COM_EFFCON_LIST为传染病的条件表，关联字段为EFFCONID，COM_EFFCON_ROUTINE为条件的明细表

表结构： LIS_ITEMREFERANCE\COM_EFFCON_LIST\COM_EFFCON_ROUTINE

##### B02	修改仪器项目传染病-修改

接口说明：修改仪器项目传染病

请求URL：../institeminfect/request/updateinfect

代码文件：winning.cmp.institeminfect.service.InstItemInfectService

示例URL： 

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | instid | 仪器ID | string | 不允许为空，如：5 |
| 3 | instname | 仪器名称 | string | 不允许为空，如;AU5800 |
| 4 | itemcode | 项目代码 | string | 不允许为空，如：0101002 |
| 5 | itemname | 项目名称 | string | 不允许为空，如：中性粒细胞绝对值 |
| 6 | itemnum | 项目标准编码 | string | 不允许为空，如：NEU# |
| 节点名称：itemreferance  传染病条件    类型： object  不能为空 | 节点名称：itemreferance  传染病条件    类型： object  不能为空 | 节点名称：itemreferance  传染病条件    类型： object  不能为空 | 节点名称：itemreferance  传染病条件    类型： object  不能为空 | 节点名称：itemreferance  传染病条件    类型： object  不能为空 |
| 1 | serialno | 序号 | int | 不允许为空，如：12 |
| 2 | lowlimit | 参考下限 | string | 允许为空，如：12.00 |
| 3 | upplimit | 参考上限 | string | 允许为空，如：12.00 |
| 4 | charrefer | 字符参考值 | string | 允许为空，如：0 |
| 5 | containbound | 是否包含边界值 | string | 允许为空，数字结果:1-包含边界 0-不包含边界 字符结果:1-包含 2-全匹配 |
| 6 | referdesc | 参考区间描述 | string | 允许为空，如：6.00-12.00 |
| 7 | printrefer | 参考打印格式 | string | 允许为空，以分号区分多个参考 |
| 8 | effconid | 条件ID | int | 允许为空，如：12 |
| 节点名称：effconroutine   危急值的明细规则 类型： array<object> 如果不添加明细或者明细没有变化，可以为空（下面节点字段可以没有），不为空按规则传 | 节点名称：effconroutine   危急值的明细规则 类型： array<object> 如果不添加明细或者明细没有变化，可以为空（下面节点字段可以没有），不为空按规则传 | 节点名称：effconroutine   危急值的明细规则 类型： array<object> 如果不添加明细或者明细没有变化，可以为空（下面节点字段可以没有），不为空按规则传 | 节点名称：effconroutine   危急值的明细规则 类型： array<object> 如果不添加明细或者明细没有变化，可以为空（下面节点字段可以没有），不为空按规则传 | 节点名称：effconroutine   危急值的明细规则 类型： array<object> 如果不添加明细或者明细没有变化，可以为空（下面节点字段可以没有），不为空按规则传 |
| 1 | routineserialno | 主键ID | int | 不允许为空，新增条件，传0，修改传明细的SERIALNO |
| 2 | fielden | 字段英文名称 | string | 不允许为空，如：12 |
| 3 | fieldcn | 字段中文名称 | string | 不允许为空，如：12 |
| 4 | value | 值 | string | 不允许为空，如：12 |
| 5 | valuedesc | 值描述 | string | 不允许为空，如：12 |
| 节点名称：ageunit   年龄明细规则 类型： object  有数据存在就直接传递，不为空按规则传 | 节点名称：ageunit   年龄明细规则 类型： object  有数据存在就直接传递，不为空按规则传 | 节点名称：ageunit   年龄明细规则 类型： object  有数据存在就直接传递，不为空按规则传 | 节点名称：ageunit   年龄明细规则 类型： object  有数据存在就直接传递，不为空按规则传 | 节点名称：ageunit   年龄明细规则 类型： object  有数据存在就直接传递，不为空按规则传 |
| 1 | agelow | 年龄下限 | string | 允许为空，如：12 |
| 2 | ageupp | 年龄上限 | string | 允许为空，如：12 |
| 3 | ageunit | 年龄单位 | string | 允许为空，如：12 |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：iteminfo  项目信息    类型： object | 节点名称：iteminfo  项目信息    类型： object | 节点名称：iteminfo  项目信息    类型： object | 节点名称：iteminfo  项目信息    类型： object | 节点名称：iteminfo  项目信息    类型： object |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | INSTID | 仪器ID | int | 如：3 |
| 3 | ITEMCODE | 项目代码 | string | 如：0101002 |
| 4 | ITEMNAME | 项目名称 | string | 如：中性粒细胞绝对值 |
| 5 | ITEMNUM | 项目标准编码 | string | 如：NEU# |
| 节点名称：refertypelist   传染病条件    类型： array<object> | 节点名称：refertypelist   传染病条件    类型： array<object> | 节点名称：refertypelist   传染病条件    类型： array<object> | 节点名称：refertypelist   传染病条件    类型： array<object> | 节点名称：refertypelist   传染病条件    类型： array<object> |
| 1 | SERIALNO | 序号 | string | 如：1 |
| 2 | LOWLIMIT | 参考下限 | string | 如：12.00 |
| 3 | UPPLIMIT | 参考上限 | string | 如：12.00 |
| 4 | CHARREFER | 字符参考值 | string | 如：0 |
| 5 | CONTAINBOUND | 是否包含边界值 | string | 数字结果:1-包含边界 0-不包含边界 字符结果:1-包含 2-全匹配 |
| 6 | REFERDESC | 参考区间描述 | string | 如：6.00-12.00 |
| 7 | PRINTREFER | 参考打印格式 | string | 以分号区分多个参考 |
| 8 | STOPFLAG | 停用标志 | string | 如：0 |
| 9 | EFFCONID | 生效条件ID | string | 如：22 |
| 10 | REFERTYPEDETAIL    传染病明细       array<object> | REFERTYPEDETAIL    传染病明细       array<object> | REFERTYPEDETAIL    传染病明细       array<object> | REFERTYPEDETAIL    传染病明细       array<object> |
|  |  |  |  |  |
| 1 | SERIALNODETAIL | 主键ID | string | 如：22 |
| 2 | ORDERNO | 序号 | int | 如：33 |
| 3 | LOGIC | 逻辑运算符 | string | 选择列表：和、或（AND,OR） |
| 4 | LOGICDESC | 运算符描述 | string | 如：三岁以前 |
| 5 | CONFIELDLIST | 查询子句分组表示 | string | 如：1-3表示子句1与子句3之间用括号组合起来,多个分组用','分割 |
| 6 | FIELDDEFINEID | 字段定义ID | string | 如：3 |
| 7 | FIELDEN | 字段英文名称 | string | 如：SEX |
| 8 | FIELDCN | 字段中文名称 | string | 如：性别 |
| 9 | RELATION | 关系运算符 | string | 选择列表：=、<>、>、<、>=、<=、包含、不包含（=、!=、>、<、>=、<=、IN、NOT IN） |
| 10 | RELATIONDESC | 关系运算符描述 | string | 如：大于 |
| 11 | VALUE | 值 | string | 如：2 |
| 12 | VALUEDESC | 值描述 | string | 如：女 |
| 13 | ISVARIABLE | 是否为动态值 | string | 只有日期字段才有动态值，1：为动态值 |
| 14 | VARIABLEVALUE | 动态内容 | string | 动态内容，分两组选择列表：本年、本季、本月、本周、前年、前季、前月、前周；第一天、最后一天、当前天 |
| 15 | ISHIGH | 查询方案级别 | int | 1是高级查询方案，=0是简单查询默认方案 |
| 16 | UPDATEFLAG | 数据访问级别 | string | 0:正常数据，-1:为删除数据，系统控制 |
| 17 | FATHERSERIALNO | 条件ID | int | 如：33 |



Json返回数据格式：



代码实现：

在表LIS_ITEMREFERANCE修改规则，

如果LIS_ITEMREFERANCE中EFFCONID为空，需要添加COM_EFFCON_LIST表中数据（有限制条件时），并将EFFCONID更新到LIS_ITEMREFERANCE中。

如果LIS_ITEMREFERANCE中EFFCONID不为空，则更新COM_EFFCON_ROUTINE表或者插入数据，并更新COM_EFFCON_LIST表（表中的一些描述的信息）

表结构：LIS_ITEMREFERANCE\COM_EFFCON_LIST\COM_EFFCON_ROUTINE

##### B03	删除仪器项目传染病

接口说明：删除仪器项目的传染病

请求URL：../institeminfect/request/deleteinfect

代码文件：winning.cmp.institeminfect.service.InstItemInfectService

示例URL： 

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | instname | 仪器名称 | string | 不允许为空，如;AU5800 |
| 3 | itemname | 项目名称 | string | 不允许为空，如：中性粒细胞绝对值 |
| 4 | serialno | 传染病序号 | string | 不允许为空，如：1 |
| 5 | effconid | 条件值序号 | string | 允许为空，如：1 |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage |
| 1 | type | 消息类型 | string | SUCCESS-成功 
ERROR-系统出现异常或错误
FAILT-业务逻辑处理失败 |
| 2 | messageCode | 消息代码 | string | 用于区分消息代码 |
| 3 | message | 消息内容 | string | type=ERROR时则为系统错误消息提示内容
type=FAILT时则为业务提示信息 |
| 4 | link | URL链接 | string | 用于提示用户后可以点击查看详情信息，默认为空 |
| 5 | data | 数据集合 | object | 用于存储返回的数据 |



Json返回数据格式：



代码实现：

危急传染病在LIS_ITEMREFERANCE表中可以删除

表结构：LIS_ITEMREFERANCE

##### B04	添加仪器项目传染病值-修改

接口说明：添加仪器项目传染病值

请求URL：../institeminfect/request/addinfect

代码文件：winning.cmp.institeminfect.service.InstItemInfectService

示例URL： 

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | instid | 仪器ID | string | 不允许为空，如：5 |
| 3 | instname | 仪器名称 | string | 不允许为空，如;AU5800 |
| 4 | itemcode | 项目代码 | string | 不允许为空，如：0101002 |
| 5 | itemname | 项目名称 | string | 不允许为空，如：中性粒细胞绝对值 |
| 6 | itemnum | 项目标准编码 | string | 不允许为空，如：NEU# |
| 节点名称：itemreferance   传染病条件 类型： object | 节点名称：itemreferance   传染病条件 类型： object | 节点名称：itemreferance   传染病条件 类型： object | 节点名称：itemreferance   传染病条件 类型： object | 节点名称：itemreferance   传染病条件 类型： object |
| 1 | lowlimit | 参考下限 | string | 允许为空，如：12.00 |
| 2 | upplimit | 参考上限 | string | 允许为空，如：12.00 |
| 3 | charrefer | 字符参考值 | string | 允许为空，如：0 |
| 4 | containbound | 是否包含边界值 | string | 允许为空，数字结果:1-包含边界 0-不包含边界 字符结果:1-包含 2-全匹配 |
| 5 | referdesc | 参考区间描述 | string | 允许为空，如：6.00-12.00 |
| 6 | printrefer | 多行参考值 | string | 允许为空，如：wec>20; |
| 节点名称：effconroutine   传染病明细规则 类型：array<object> 可以为空，如果不为空，按规则传 | 节点名称：effconroutine   传染病明细规则 类型：array<object> 可以为空，如果不为空，按规则传 | 节点名称：effconroutine   传染病明细规则 类型：array<object> 可以为空，如果不为空，按规则传 | 节点名称：effconroutine   传染病明细规则 类型：array<object> 可以为空，如果不为空，按规则传 | 节点名称：effconroutine   传染病明细规则 类型：array<object> 可以为空，如果不为空，按规则传 |
| 1 | fielden | 字段英文名称 | string | 不允许为空，如：12 |
| 2 | fieldcn | 字段中文名称 | string | 不允许为空，如：12 |
| 3 | value | 值 | string | 不允许为空，如：12 |
| 4 | valuedesc | 值描述 | string | 不允许为空，如：12 |
| 节点名称：ageunit   年龄明细规则 类型： object  有数据存在就直接传递，不为空按规则传 | 节点名称：ageunit   年龄明细规则 类型： object  有数据存在就直接传递，不为空按规则传 | 节点名称：ageunit   年龄明细规则 类型： object  有数据存在就直接传递，不为空按规则传 | 节点名称：ageunit   年龄明细规则 类型： object  有数据存在就直接传递，不为空按规则传 | 节点名称：ageunit   年龄明细规则 类型： object  有数据存在就直接传递，不为空按规则传 |
| 1 | agelow | 年龄下限 | string | 允许为空，如：12 |
| 2 | ageupp | 年龄上限 | string | 允许为空，如：12 |
| 3 | ageunit | 年龄单位 | string | 允许为空，如：12 |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：iteminfo  项目信息    类型： object | 节点名称：iteminfo  项目信息    类型： object | 节点名称：iteminfo  项目信息    类型： object | 节点名称：iteminfo  项目信息    类型： object | 节点名称：iteminfo  项目信息    类型： object |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | INSTID | 仪器ID | int | 如：3 |
| 3 | ITEMCODE | 项目代码 | string | 如：0101002 |
| 4 | ITEMNAME | 项目名称 | string | 如：中性粒细胞绝对值 |
| 5 | ITEMNUM | 项目标准编码 | string | 如：NEU# |
| 节点名称：refertypelist   传染病值    类型： array<object> | 节点名称：refertypelist   传染病值    类型： array<object> | 节点名称：refertypelist   传染病值    类型： array<object> | 节点名称：refertypelist   传染病值    类型： array<object> | 节点名称：refertypelist   传染病值    类型： array<object> |
| 1 | SERIALNO | 序号 | string | 如：1 |
| 2 | LOWLIMIT | 参考下限 | string | 如：12.00 |
| 3 | UPPLIMIT | 参考上限 | string | 如：12.00 |
| 4 | CHARREFER | 字符参考值 | string | 如：0 |
| 5 | CONTAINBOUND | 是否包含边界值 | string | 数字结果:1-包含边界 0-不包含边界 字符结果:1-包含 2-全匹配 |
| 6 | REFERDESC | 参考区间描述 | string | 如：6.00-12.00 |
| 7 | PRINTREFER | 参考打印格式 | string | 以分号区分多个参考 |
| 8 | STOPFLAG | 停用标志 | string | 如：0 |
| 9 | EFFCONID | 生效条件ID | string | 如：22 |
| 10 | REFERTYPEDETAIL    传染病明细       array<object> | REFERTYPEDETAIL    传染病明细       array<object> | REFERTYPEDETAIL    传染病明细       array<object> | REFERTYPEDETAIL    传染病明细       array<object> |
|  |  |  |  |  |
| 1 | SERIALNODETAIL | 主键ID | string | 如：22 |
| 2 | ORDERNO | 序号 | int | 如：33 |
| 3 | LOGIC | 逻辑运算符 | string | 选择列表：和、或（AND,OR） |
| 4 | LOGICDESC | 运算符描述 | string | 如：三岁以前 |
| 5 | CONFIELDLIST | 查询子句分组表示 | string | 如：1-3表示子句1与子句3之间用括号组合起来,多个分组用','分割 |
| 6 | FIELDDEFINEID | 字段定义ID | string | 如：3 |
| 7 | FIELDEN | 字段英文名称 | string | 如：SEX |
| 8 | FIELDCN | 字段中文名称 | string | 如：性别 |
| 9 | RELATION | 关系运算符 | string | 选择列表：=、<>、>、<、>=、<=、包含、不包含（=、!=、>、<、>=、<=、IN、NOT IN） |
| 10 | RELATIONDESC | 关系运算符描述 | string | 如：大于 |
| 11 | VALUE | 值 | string | 如：2 |
| 12 | VALUEDESC | 值描述 | string | 如：女 |
| 13 | ISVARIABLE | 是否为动态值 | string | 只有日期字段才有动态值，1：为动态值 |
| 14 | VARIABLEVALUE | 动态内容 | string | 动态内容，分两组选择列表：本年、本季、本月、本周、前年、前季、前月、前周；第一天、最后一天、当前天 |
| 15 | ISHIGH | 查询方案级别 | int | 1是高级查询方案，=0是简单查询默认方案 |
| 16 | UPDATEFLAG | 数据访问级别 | string | 0:正常数据，-1:为删除数据，系统控制 |
| 17 | FATHERSERIALNO | 条件ID | int | 如：33 |



Json返回数据格式：



代码实现：

在LIS_ITEMREFERANCE表中插入记录，其中REFERTYPE为传染病值，COM_EFFCON_LIST插入传染病值的条件表，并将生成的EFFCONID更新到LIS_ITEMREFERANCE表中，在COM_EFFCON_ROUTINE表中插入数据，并将对应的信息拼接成对应的格式更新到LIS_ITEMREFERANCE表中。

表结构： LIS_ITEMREFERANCE\COM_EFFCON_LIST\COM_EFFCON_ROUTINE

### 仪器项目计算公式维护

#### V 模块接口视图

| NO. | 接口编号 | 接口名称 | 说明 |
| --- | --- | --- | --- |
| 1 | A01 | 下拉框信息初始化 | 下拉框信息初始化 |
| 2 | A02 | 获取仪器项目列表
../instrumentitemformular/request/getinstitemlist | 获取仪器项目列表 |
| 3 | B01 | 获取仪器项目计算公式
../instrumentitemformular/request/getinstitemformular | 获取仪器项目计算公式 |
| 4 | B02 | 修改仪器项目计算公式
../instrumentitemformular/request/updateformular | 修改仪器项目计算公式 |
| 5 | B03 | 删除仪器项目计算公式
../instrumentitemformular/request/deleteformular | 删除仪器项目计算公式 |
| 6 | B04 | 添加仪器项目计算公式
../instrumentitemformular/request/addotherformular | 添加仪器项目计算公式 |



#### A 对外公布方法

##### A01	下拉框信息初始化

接口说明：获取仪器项目列表

请求URL：

代码文件：

示例URL： 

原型参考：





调用仪器项目的411.A01接口

##### A02	获取仪器项目列表

接口说明：获取仪器项目列表

请求URL：

代码文件：winning.cmp.institemformular.service.InstItemFormularService

示例URL： 

原型参考：



调用411.B01的接口

#### B 业务类

##### B01	获取仪器项目计算公式

接口说明：获取仪器项目计算公式

请求URL：../institemformular/request/getinstitemformular

代码文件：winning.cmp.institemformular.service.InstItemFormularService

示例URL： http://192.168.10.73:10004/lis/institemformular/request/getinstitemformular?hospitalcode=9999&instid=9&instname=大便常规&itemcode=HDL&itemname=HDL&itemnum=HDL

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | instid | 仪器ID | string | 不允许为空，如：5 |
| 3 | instname | 仪器名称 | string | 不允许为空，如;AU5800 |
| 4 | itemcode | 项目代码 | string | 不允许为空，如：0101002 |
| 5 | itemname | 项目名称 | string | 不允许为空，如：中性粒细胞绝对值 |
| 6 | itemnum | 项目标准编码 | string | 不允许为空，如：NEU# |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：iteminfo  项目信息    类型： object | 节点名称：iteminfo  项目信息    类型： object | 节点名称：iteminfo  项目信息    类型： object | 节点名称：iteminfo  项目信息    类型： object | 节点名称：iteminfo  项目信息    类型： object |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | INSTID | 仪器ID | int | 如：3 |
| 3 | ITEMCODE | 项目代码 | string | 如：0101002 |
| 4 | ITEMNAME | 项目名称 | string | 如：中性粒细胞绝对值 |
| 5 | ITEMNUM | 项目标准编码 | string | 如：NEU# |
| 节点名称：refertypelist计算公式    类型： array<object> | 节点名称：refertypelist计算公式    类型： array<object> | 节点名称：refertypelist计算公式    类型： array<object> | 节点名称：refertypelist计算公式    类型： array<object> | 节点名称：refertypelist计算公式    类型： array<object> |
| 1 | SERIALNO | 序号 | string | 如：1 |
| 2 | FORMULAR | 计算公式 | string | 如：0 |
| 3 | STOPFLAG | 停用标志 | string | 如：0 |
| 4 | EFFCONID | 生效条件ID | string | 如：22 |
| 5 | REFERTYPEDETAIL    计算公式明细       array<object> | REFERTYPEDETAIL    计算公式明细       array<object> | REFERTYPEDETAIL    计算公式明细       array<object> | REFERTYPEDETAIL    计算公式明细       array<object> |
|  |  |  |  |  |
| 1 | SERIALNODETAIL | 主键ID | string | 如：22 |
| 2 | ORDERNO | 序号 | int | 如：33 |
| 3 | LOGIC | 逻辑运算符 | string | 选择列表：和、或（AND,OR） |
| 4 | LOGICDESC | 运算符描述 | string | 如：三岁以前 |
| 5 | CONFIELDLIST | 查询子句分组表示 | string | 如：1-3表示子句1与子句3之间用括号组合起来,多个分组用','分割 |
| 6 | FIELDDEFINEID | 字段定义ID | string | 如：3 |
| 7 | FIELDEN | 字段英文名称 | string | 如：SEX |
| 8 | FIELDCN | 字段中文名称 | string | 如：性别 |
| 9 | RELATION | 关系运算符 | string | 选择列表：=、<>、>、<、>=、<=、包含、不包含（=、!=、>、<、>=、<=、IN、NOT IN） |
| 10 | RELATIONDESC | 关系运算符描述 | string | 如：大于 |
| 11 | VALUE | 值 | string | 如：2 |
| 12 | VALUEDESC | 值描述 | string | 如：女 |
| 13 | ISVARIABLE | 是否为动态值 | string | 只有日期字段才有动态值，1：为动态值 |
| 14 | VARIABLEVALUE | 动态内容 | string | 动态内容，分两组选择列表：本年、本季、本月、本周、前年、前季、前月、前周；第一天、最后一天、当前天 |
| 15 | ISHIGH | 查询方案级别 | int | 1是高级查询方案，=0是简单查询默认方案 |
| 16 | UPDATEFLAG | 数据访问级别 | string | 0:正常数据，-1:为删除数据，系统控制 |
| 17 | FATHERSERIALNO | 条件ID | int | 如：33 |



Json返回数据格式：



代码实现：

LIS_FORMULAR为计算公式表，COM_EFFCON_LIST为计算公式的条件表，关联字段为EFFCONID，COM_EFFCON_ROUTINE为条件的明细表

表结构：LIS_FORMULAR\COM_EFFCON_LIST\COM_EFFCON_ROUTINE

##### B02	修改仪器项目计算公式

接口说明：修改仪器项目计算公式

请求URL：../institemformular/request/updateformular

代码文件：winning.cmp.institemformular.service.InstItemFormularService

示例URL： http://192.168.10.73:10004/lis/institemformular/request/updateformular?hospitalcode=9999&instid=9&instname=SF810-1&itemcode=HDL&itemname=HDL&itemnum=HDL&itemreferance ={"serialno":"2","formular":"[HDL]*5<43","effconid":"17"}&effconroutine=[{"routineserialno":"45","fielden":"APPLYDEPTCODE","fieldcn":"申请科室","value":"1411","valuedesc":"儿一23科"}]

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | instid | 仪器ID | string | 不允许为空，如：5 |
| 3 | instname | 仪器名称 | string | 不允许为空，如;AU5800 |
| 4 | itemcode | 项目代码 | string | 不允许为空，如：0101002 |
| 5 | itemname | 项目名称 | string | 不允许为空，如：中性粒细胞绝对值 |
| 6 | itemnum | 项目标准编码 | string | 不允许为空，如：NEU# |
| 节点名称：itemreferance  计算公式条件    类型： object  不能为空 | 节点名称：itemreferance  计算公式条件    类型： object  不能为空 | 节点名称：itemreferance  计算公式条件    类型： object  不能为空 | 节点名称：itemreferance  计算公式条件    类型： object  不能为空 | 节点名称：itemreferance  计算公式条件    类型： object  不能为空 |
| 1 | serialno | 序号 | string | 如：1 |
| 2 | formular | 计算公式 | string | 如：0 |
| 3 | effconid | 生效条件ID | string | 如：22 |
| 节点名称：effconroutine  计算公式的明细规则 类型： array<object> 如果不添加明细或者明细没有变化，可以为空（下面节点字段可以没有），不为空按规则传 | 节点名称：effconroutine  计算公式的明细规则 类型： array<object> 如果不添加明细或者明细没有变化，可以为空（下面节点字段可以没有），不为空按规则传 | 节点名称：effconroutine  计算公式的明细规则 类型： array<object> 如果不添加明细或者明细没有变化，可以为空（下面节点字段可以没有），不为空按规则传 | 节点名称：effconroutine  计算公式的明细规则 类型： array<object> 如果不添加明细或者明细没有变化，可以为空（下面节点字段可以没有），不为空按规则传 | 节点名称：effconroutine  计算公式的明细规则 类型： array<object> 如果不添加明细或者明细没有变化，可以为空（下面节点字段可以没有），不为空按规则传 |
| 1 | routineserialno | 主键ID | int | 不允许为空，新增条件，传0，修改传明细的SERIALNO |
| 2 | fielden | 字段英文名称 | string | 不允许为空，如：12 |
| 3 | fieldcn | 字段中文名称 | string | 不允许为空，如：12 |
| 4 | value | 值 | string | 不允许为空，如：12 |
| 5 | valuedesc | 值描述 | string | 不允许为空，如：12 |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：iteminfo  项目信息    类型： object | 节点名称：iteminfo  项目信息    类型： object | 节点名称：iteminfo  项目信息    类型： object | 节点名称：iteminfo  项目信息    类型： object | 节点名称：iteminfo  项目信息    类型： object |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | INSTID | 仪器ID | int | 如：3 |
| 3 | ITEMCODE | 项目代码 | string | 如：0101002 |
| 4 | ITEMNAME | 项目名称 | string | 如：中性粒细胞绝对值 |
| 5 | ITEMNUM | 项目标准编码 | string | 如：NEU# |
| 节点名称：formularlist   计算公式    类型： array<object> | 节点名称：formularlist   计算公式    类型： array<object> | 节点名称：formularlist   计算公式    类型： array<object> | 节点名称：formularlist   计算公式    类型： array<object> | 节点名称：formularlist   计算公式    类型： array<object> |
| 1 | SERIALNO | 序号 | string | 如：1 |
| 2 | FORMULAR | 计算公式 | string | 如：0 |
| 3 | STOPFLAG | 停用标志 | string | 如：0 |
| 4 | EFFCONID | 生效条件ID | string | 如：22 |
| 5 | REFERTYPEDETAIL    计算公式明细       array<object> | REFERTYPEDETAIL    计算公式明细       array<object> | REFERTYPEDETAIL    计算公式明细       array<object> | REFERTYPEDETAIL    计算公式明细       array<object> |
|  |  |  |  |  |
| 1 | SERIALNODETAIL | 主键ID | string | 如：22 |
| 2 | ORDERNO | 序号 | int | 如：33 |
| 3 | LOGIC | 逻辑运算符 | string | 选择列表：和、或（AND,OR） |
| 4 | LOGICDESC | 运算符描述 | string | 如：三岁以前 |
| 5 | CONFIELDLIST | 查询子句分组表示 | string | 如：1-3表示子句1与子句3之间用括号组合起来,多个分组用','分割 |
| 6 | FIELDDEFINEID | 字段定义ID | string | 如：3 |
| 7 | FIELDEN | 字段英文名称 | string | 如：SEX |
| 8 | FIELDCN | 字段中文名称 | string | 如：性别 |
| 9 | RELATION | 关系运算符 | string | 选择列表：=、<>、>、<、>=、<=、包含、不包含（=、!=、>、<、>=、<=、IN、NOT IN） |
| 10 | RELATIONDESC | 关系运算符描述 | string | 如：大于 |
| 11 | VALUE | 值 | string | 如：2 |
| 12 | VALUEDESC | 值描述 | string | 如：女 |
| 13 | ISVARIABLE | 是否为动态值 | string | 只有日期字段才有动态值，1：为动态值 |
| 14 | VARIABLEVALUE | 动态内容 | string | 动态内容，分两组选择列表：本年、本季、本月、本周、前年、前季、前月、前周；第一天、最后一天、当前天 |
| 15 | ISHIGH | 查询方案级别 | int | 1是高级查询方案，=0是简单查询默认方案 |
| 16 | UPDATEFLAG | 数据访问级别 | string | 0:正常数据，-1:为删除数据，系统控制 |
| 17 | FATHERSERIALNO | 条件ID | int | 如：33 |



Json返回数据格式：



代码实现：

在表LIS_FORMULAR修改计算公式，

如果LIS_FORMULAR中EFFCONID为空，需要添加COM_EFFCON_LIST表中数据（有限制条件时），并将EFFCONID更新到LIS_FORMULAR中。

如果LIS_FORMULAR中EFFCONID不为空，则更新COM_EFFCON_ROUTINE表或者插入数据，并更新COM_EFFCON_LIST表（表中的一些描述的信息）

表结构：LIS_FORMULAR\COM_EFFCON_LIST\COM_EFFCON_ROUTINE

##### B03	删除仪器项目计算公式

接口说明：删除仪器项目的计算公式

请求URL：../institemformular/request/deleteformular

代码文件：winning.cmp.institemformular.service.InstItemFormularService

示例URL： 

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | instname | 仪器名称 | string | 不允许为空，如;AU5800 |
| 3 | itemname | 项目名称 | string | 不允许为空，如：中性粒细胞绝对值 |
| 4 | serialno | 计算公式序号 | string | 不允许为空，如：1 |
| 5 | effconid | 条件值序号 | string | 允许为空，如：1 |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage |
| 1 | type | 消息类型 | string | SUCCESS-成功 
ERROR-系统出现异常或错误
FAILT-业务逻辑处理失败 |
| 2 | messageCode | 消息代码 | string | 用于区分消息代码 |
| 3 | message | 消息内容 | string | type=ERROR时则为系统错误消息提示内容
type=FAILT时则为业务提示信息 |
| 4 | link | URL链接 | string | 用于提示用户后可以点击查看详情信息，默认为空 |
| 5 | data | 数据集合 | object | 用于存储返回的数据 |



Json返回数据格式：



代码实现：

危急传染病在LIS_FORMULAR表中可以删除

表结构：LIS_FORMULAR

##### B04	添加仪器项目计算公式

接口说明：添加仪器项目计算公式

请求URL：../institemformular/request/addformular

代码文件：winning.cmp.institemformular.service.InstItemFormularService

示例URL： http://192.168.10.73:10004/lis/institemformular/request/addformular?hospitalcode=9999&instid=9&instname=SF810-1&itemcode=HDL&itemname=HDL&itemnum=HDL&formular=[HDL]<435&effconroutine=[{"fielden":"SEX","fieldcn":"性别","value":"1","valuedesc":"男"},{"fielden":"APPLYDEPTCODE","fieldcn":"申请科室","value":"1411","valuedesc":"儿一科"},{"fielden":"SEX","fieldcn":"性别","value":"2","valuedesc":"女"}]

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | instid | 仪器ID | string | 不允许为空，如：5 |
| 3 | instname | 仪器名称 | string | 不允许为空，如;AU5800 |
| 4 | itemcode | 项目代码 | string | 不允许为空，如：0101002 |
| 5 | itemname | 项目名称 | string | 不允许为空，如：中性粒细胞绝对值 |
| 6 | itemnum | 项目标准编码 | string | 不允许为空，如：NEU# |
| 7 | formular | 计算公式 | string | 如：0 |
| 节点名称：effconroutine   传染病明细规则 类型：array<object> 可以为空，如果不为空，按规则传 | 节点名称：effconroutine   传染病明细规则 类型：array<object> 可以为空，如果不为空，按规则传 | 节点名称：effconroutine   传染病明细规则 类型：array<object> 可以为空，如果不为空，按规则传 | 节点名称：effconroutine   传染病明细规则 类型：array<object> 可以为空，如果不为空，按规则传 | 节点名称：effconroutine   传染病明细规则 类型：array<object> 可以为空，如果不为空，按规则传 |
| 1 | fielden | 字段英文名称 | string | 不允许为空，如：12 |
| 2 | fieldcn | 字段中文名称 | string | 不允许为空，如：12 |
| 3 | value | 值 | string | 不允许为空，如：12 |
| 4 | valuedesc | 值描述 | string | 不允许为空，如：12 |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：iteminfo  项目信息    类型： object | 节点名称：iteminfo  项目信息    类型： object | 节点名称：iteminfo  项目信息    类型： object | 节点名称：iteminfo  项目信息    类型： object | 节点名称：iteminfo  项目信息    类型： object |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | INSTID | 仪器ID | int | 如：3 |
| 3 | ITEMCODE | 项目代码 | string | 如：0101002 |
| 4 | ITEMNAME | 项目名称 | string | 如：中性粒细胞绝对值 |
| 5 | ITEMNUM | 项目标准编码 | string | 如：NEU# |
| 节点名称：formularlist   计算公式    类型： array<object> | 节点名称：formularlist   计算公式    类型： array<object> | 节点名称：formularlist   计算公式    类型： array<object> | 节点名称：formularlist   计算公式    类型： array<object> | 节点名称：formularlist   计算公式    类型： array<object> |
| 1 | SERIALNO | 序号 | string | 如：1 |
| 2 | FORMULAR | 计算公式 | string | 如：0 |
| 3 | STOPFLAG | 停用标志 | string | 如：0 |
| 4 | EFFCONID | 生效条件ID | string | 如：22 |
| 5 | REFERTYPEDETAIL    计算公式明细       array<object> | REFERTYPEDETAIL    计算公式明细       array<object> | REFERTYPEDETAIL    计算公式明细       array<object> | REFERTYPEDETAIL    计算公式明细       array<object> |
|  |  |  |  |  |
| 1 | SERIALNODETAIL | 主键ID | string | 如：22 |
| 2 | ORDERNO | 序号 | int | 如：33 |
| 3 | LOGIC | 逻辑运算符 | string | 选择列表：和、或（AND,OR） |
| 4 | LOGICDESC | 运算符描述 | string | 如：三岁以前 |
| 5 | CONFIELDLIST | 查询子句分组表示 | string | 如：1-3表示子句1与子句3之间用括号组合起来,多个分组用','分割 |
| 6 | FIELDDEFINEID | 字段定义ID | string | 如：3 |
| 7 | FIELDEN | 字段英文名称 | string | 如：SEX |
| 8 | FIELDCN | 字段中文名称 | string | 如：性别 |
| 9 | RELATION | 关系运算符 | string | 选择列表：=、<>、>、<、>=、<=、包含、不包含（=、!=、>、<、>=、<=、IN、NOT IN） |
| 10 | RELATIONDESC | 关系运算符描述 | string | 如：大于 |
| 11 | VALUE | 值 | string | 如：2 |
| 12 | VALUEDESC | 值描述 | string | 如：女 |
| 13 | ISVARIABLE | 是否为动态值 | string | 只有日期字段才有动态值，1：为动态值 |
| 14 | VARIABLEVALUE | 动态内容 | string | 动态内容，分两组选择列表：本年、本季、本月、本周、前年、前季、前月、前周；第一天、最后一天、当前天 |
| 15 | ISHIGH | 查询方案级别 | int | 1是高级查询方案，=0是简单查询默认方案 |
| 16 | UPDATEFLAG | 数据访问级别 | string | 0:正常数据，-1:为删除数据，系统控制 |
| 17 | FATHERSERIALNO | 条件ID | int | 如：33 |



Json返回数据格式：



代码实现：

在LIS_FORMULAR表中插入记录，COM_EFFCON_LIST插入计算公式的条件表，并将生成的EFFCONID更新到LIS_FORMULAR表中，在COM_EFFCON_ROUTINE表中插入数据，并将对应的信息拼接成对应的格式更新到LIS_FORMULAR表中。

表结构： LIS_FORMULAR\COM_EFFCON_LIST\COM_EFFCON_ROUTINE



### 仪器项目转义结果维护

#### V 模块接口视图

| NO. | 接口编号 | 接口名称 | 说明 |
| --- | --- | --- | --- |
| 1 | B01 | 获取仪器项目结果转换
../instrumentitemchangemean/request/getinstitemchangemean | 获取仪器项目结果转换 |
| 2 | B02 | 修改仪器项目转义结果
../instrumentitemchangemean/request/updatechangemean | 修改仪器项目转义结果 |
| 3 | B03 | 删除仪器项目转义结果
../instrumentitemchangemean/request/deletechangemean | 删除仪器项目转义结果 |
| 4 | B04 | 添加仪器项目结果转换
../instrumentitemchangemean/request/addchangemean | 添加仪器项目结果转换 |



#### A 对外公布方法

#### B 业务类

##### B01	获取仪器项目结果转换

接口说明：获取仪器项目结果转换

请求URL：../institemchangemean/request/getinstitemchangemean

代码文件：winning.cmp.institemchangemean.service.InstrumentItemChangemeanService

示例URL： http://192.168.10.73:10004/lis/institemchangemean/request/getinstitemchangemean?hospitalcode=9999&instid=12&instname=AIA-360&itemcode=TB&itemname=TB&itemnum=TB

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | instid | 仪器ID | string | 不允许为空，如：5 |
| 3 | instname | 仪器名称 | string | 不允许为空，如;AU5800 |
| 4 | itemcode | 项目代码 | string | 不允许为空，如：0101002 |
| 5 | itemname | 项目名称 | string | 不允许为空，如：中性粒细胞绝对值 |
| 6 | itemnum | 项目标准编码 | string | 不允许为空，如：NEU# |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：iteminfo  项目信息    类型： object | 节点名称：iteminfo  项目信息    类型： object | 节点名称：iteminfo  项目信息    类型： object | 节点名称：iteminfo  项目信息    类型： object | 节点名称：iteminfo  项目信息    类型： object |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | INSTID | 仪器ID | int | 如：3 |
| 3 | ITEMCODE | 项目代码 | string | 如：0101002 |
| 4 | ITEMNAME | 项目名称 | string | 如：中性粒细胞绝对值 |
| 5 | ITEMNUM | 项目标准编码 | string | 如：NEU# |
| 节点名称：changemeanlist   计算公式    类型： array<object> | 节点名称：changemeanlist   计算公式    类型： array<object> | 节点名称：changemeanlist   计算公式    类型： array<object> | 节点名称：changemeanlist   计算公式    类型： array<object> | 节点名称：changemeanlist   计算公式    类型： array<object> |
| 1 | SERIALNO | 序号 | string | 如：1 |
| 2 | SYMBOL | 标注值（结果值） | string | 如; 阳性（+） |
| 3 | ATTRIBE | 特性 | string | 1 |
| 4 | ATTRIBEMARK | 特征标识 | string | 如：22 |
| 5 | CHARREFER | 字符参考 | string | 如：+ |
| 6 | LOWLIMIT | 下限 | string | 如：3 |
| 7 | UPPLIMIT | 上限 | string | 如：30 |
| 8 | ATTRIBEMARKNAME | 特征标识名称 | string | 如：【】 |
| 9 | CONTAINBOUND | 取值范围 | string | 如：1 |
| 10 | RESULTDESC | 结果描述 | string | 如：》=30 |



Json返回数据格式：



代码实现：

LIS_CHANGEMEAN表中获取转义结果的信息，特征标识符存在SLAVE_P表中，classcode=特征标识。

表结构： LIS_CHANGEMEAN\SLAVE_P

##### B02	修改仪器项目转义结果

接口说明：修改仪器项目转义结果

请求URL：../institemchangemean/request/updatechangemean

代码文件：winning.cmp.institemchangemean.service.InstItemChangemeanService

示例URL： 

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | instid | 仪器ID | string | 不允许为空，如：5 |
| 3 | instname | 仪器名称 | string | 不允许为空，如：AU5800 |
| 4 | itemcode | 项目代码 | string | 不允许为空，如：0101002 |
| 5 | itemname | 项目名称 | string | 不允许为空，如：中性粒细胞绝对值 |
| 6 | itemnum | 项目标准编码 | string | 不允许为空，如：WBS |
| 节点名称：changemean  转义结果    类型： object  不能为空 | 节点名称：changemean  转义结果    类型： object  不能为空 | 节点名称：changemean  转义结果    类型： object  不能为空 | 节点名称：changemean  转义结果    类型： object  不能为空 | 节点名称：changemean  转义结果    类型： object  不能为空 |
| 1 | symbol | 标注值（结果值） | string | 不允许为空，如：阳性（+）， |
| 2 | attribe | 特性 | string | 不允许为空，如：1，0:未标注 1:左标注 2:右标注 |
| 3 | attribemark | 特征标识 | string | 不允许为空，如：2，0:无标识，1:[],2:【】,3:{},4:(),5:<>,6:《》 |
| 4 | charrefer | 字符参考 | string | 允许为空，如：+， |
| 5 | lowlimit | 下限 | string | 允许为空，如：12，对应原型上原始结果的数字结果 |
| 6 | upplimit | 上限 | string | 允许为空，如：40，对应原型上原始结果的数字结果 |
| 7 | containbound | 取值范围 | string |  |
| 8 | serialno | 序号 | string | 不允许为空，如：1 |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：iteminfo  项目信息    类型： object | 节点名称：iteminfo  项目信息    类型： object | 节点名称：iteminfo  项目信息    类型： object | 节点名称：iteminfo  项目信息    类型： object | 节点名称：iteminfo  项目信息    类型： object |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | INSTID | 仪器ID | int | 如：3 |
| 3 | ITEMCODE | 项目代码 | string | 如：0101002 |
| 4 | ITEMNAME | 项目名称 | string | 如：中性粒细胞绝对值 |
| 5 | ITEMNUM | 项目标准编码 | string | 如：NEU# |
| 节点名称：changemean   结果转义    类型： object | 节点名称：changemean   结果转义    类型： object | 节点名称：changemean   结果转义    类型： object | 节点名称：changemean   结果转义    类型： object | 节点名称：changemean   结果转义    类型： object |
| 1 | SERIALNO | 序号 | string | 如：1 |
| 2 | SYMBOL | 标注值（结果值） | string | 如; 阳性（+） |
| 3 | ATTRIBE | 特性 | string | 1 |
| 4 | ATTRIBEMARK | 特征标识 | string | 如：22 |
| 5 | CHARREFER | 字符参考 | string | 如：+ |
| 6 | LOWLIMIT | 下限 | string | 如：3 |
| 7 | UPPLIMIT | 上限 | string | 如：30 |
| 8 | ATTRIBEMARKNAME | 特征标识名称 | string | 如：【】 |
| 9 | CONTAINBOUND | 取值范围 | string | 如：1 |
| 10 | RESULTDESC | 原始结果 | string | 如：>=7 and <=30，对应原型上原始数字结果 |



Json返回数据格式：



代码实现：

LIS_CHANGEMEAN表中获取转义结果的信息，特征标识符存在SLAVE_P表中，classcode=特征标识。

表结构： LIS_CHANGEMEAN\SLAVE_P

##### B03	删除仪器项目转义结果

接口说明：删除仪器项目转义结果

请求URL：../institemchangemean/request/deletechangemean

代码文件：winning.cmp.institemchangemean.service.InstItemChangemeanService

示例URL： http://192.168.10.73:10004/lis/institemchangemean/request/deletechangemean?hospitalcode=9999&instname=AIA-360&itemnum=TB&serialno=463

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | instname | 仪器名称 | string | 不允许为空，如;AU5800 |
| 3 | itemnum | 项目标准编码 | string | 不允许为空，如：中性粒细胞绝对值 |
| 4 | serialno | 序号 | string | 不允许为空，如：1 |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage |
| 1 | type | 消息类型 | string | SUCCESS-成功 
ERROR-系统出现异常或错误
FAILT-业务逻辑处理失败 |
| 2 | messageCode | 消息代码 | string | 用于区分消息代码 |
| 3 | message | 消息内容 | string | type=ERROR时则为系统错误消息提示内容
type=FAILT时则为业务提示信息 |
| 4 | link | URL链接 | string | 用于提示用户后可以点击查看详情信息，默认为空 |
| 5 | data | 数据集合 | object | 用于存储返回的数据 |



Json返回数据格式：



代码实现：

LIS_CHANGEMEAN表删除转义结果的信息。

表结构： LIS_CHANGEMEAN

##### B04	添加仪器项目结果转换

接口说明：添加仪器项目结果转换

请求URL：../institemchangemean/request/addchangemean

代码文件：winning.cmp.institemchangemean.service.InstItemChangemeanService

示例URL： http://192.168.10.73:10004/lis/institemchangemean/request/addchangemean?hospitalcode=9999&instid=12&instname=AIA-360&itemcode=TB&itemname=TB&itemnum=TB&changemean={"symbol":"黄色","resultflag":"2","attribe":"1","attribemark":"1","charrefer":"","lowlimit":"14","upplimit":"46","containbound":"1"}

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | instid | 仪器ID | string | 不允许为空，如：5 |
| 3 | instname | 仪器名称 | string | 不允许为空，如;AU5800 |
| 4 | itemcode | 项目代码 | string | 不允许为空，如：0101002 |
| 5 | itemname | 项目名称 | string | 不允许为空，如：中性粒细胞绝对值 |
| 6 | itemnum | 项目标准编码 | string | 不允许为空，如：NEU# |
| 节点名称：changemean   转换结果 类型： object | 节点名称：changemean   转换结果 类型： object | 节点名称：changemean   转换结果 类型： object | 节点名称：changemean   转换结果 类型： object | 节点名称：changemean   转换结果 类型： object |
| 1 | symbol | 标注值（结果值） | string | 不允许为空，如：阳性（+）， |
| 2 | attribe | 特性 | string | 不允许为空，如：1，0:未标注 1:左标注 2:右标注 |
| 3 | attribemark | 特征标识 | string | 不允许为空，如：2，0:无标识，1:[],2:【】,3:{},4:(),5:<>,6:《》 |
| 4 | charrefer | 字符参考 | string | 允许为空，如：+， |
| 5 | lowlimit | 下限 | string | 允许为空，如：12，对应原型上原始结果的数字结果 |
| 6 | upplimit | 上限 | string | 允许为空，如：40，对应原型上原始结果的数字结果 |
| 7 | containbound | 取值范围 | string |  |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：iteminfo  项目信息    类型： object | 节点名称：iteminfo  项目信息    类型： object | 节点名称：iteminfo  项目信息    类型： object | 节点名称：iteminfo  项目信息    类型： object | 节点名称：iteminfo  项目信息    类型： object |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | INSTID | 仪器ID | int | 如：3 |
| 3 | ITEMCODE | 项目代码 | string | 如：0101002 |
| 4 | ITEMNAME | 项目名称 | string | 如：中性粒细胞绝对值 |
| 5 | ITEMNUM | 项目标准编码 | string | 如：NEU# |
| 节点名称：changemean   结果转义    类型： array<object> | 节点名称：changemean   结果转义    类型： array<object> | 节点名称：changemean   结果转义    类型： array<object> | 节点名称：changemean   结果转义    类型： array<object> | 节点名称：changemean   结果转义    类型： array<object> |
| 1 | SERIALNO | 序号 | string | 如：1 |
| 2 | SYMBOL | 标注值（结果值） | string | 如; 阳性（+） |
| 3 | ATTRIBE | 特性 | string | 1 |
| 4 | ATTRIBEMARK | 特征标识 | string | 如：22 |
| 5 | CHARREFER | 字符参考 | string | 如：+ |
| 6 | LOWLIMIT | 下限 | string | 如：3 |
| 7 | UPPLIMIT | 上限 | string | 如：30 |
| 8 | ATTRIBEMARKNAME | 特征标识名称 | string | 如：【】 |
| 9 | CONTAINBOUND | 取值范围 | string |  |
| 10 | RESULTDESC | 原始结果 | string | 如：>=7 and <=30，对应原型上原始数字结果 |



Json返回数据格式：



代码实现：

LIS_CHANGEMEAN表中获取转义结果的信息，特征标识符存在SLAVE_P表中，classcode=特征标识。

表结构： LIS_CHANGEMEAN\SLAVE_P

### 仪器项目异常值设置和结果建议与解释维护

#### V 模块接口视图

| NO. | 接口编号 | 接口名称 | 说明 |
| --- | --- | --- | --- |
| 1 | B01 | 获取仪器项目异常值设置
../institemhighlow/request/getinstitemhighlow | 获取仪器项目异常值设置 |
| 2 | B02 | 修改仪器项目异常值
../institemchangemean/request/updatehighlow | 修改仪器项目异常值 |
| 3 | B03 | 删除仪器项目异常值
../institemchangemean/request/deletehighlow | 删除仪器项目异常值 |
| 4 | B04 | 添加仪器项目异常值
../institemchangemean/request/addhighlow | 添加仪器项目异常值 |



#### A 对外公布方法

#### B 业务类

##### B01	获取仪器项目异常值设置

接口说明：获取仪器项目异常值设置

请求URL：../institemhighlow/request/getinstitemhighlow

代码文件：winning.cmp.institemhighlow.service.InstrumentItemHighLowService

示例URL： http://192.168.10.73:10004/lis/institemhighlow/request/getinstitemhighlow?hospitalcode=9999&instid=12&instname=AIA-360&itemcode=TB&itemname=TB&itemnum=TB

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | instid | 仪器ID | string | 不允许为空，如：5 |
| 3 | instname | 仪器名称 | string | 不允许为空，如;AU5800 |
| 4 | itemcode | 项目代码 | string | 不允许为空，如：0101002 |
| 5 | itemname | 项目名称 | string | 不允许为空，如：中性粒细胞绝对值 |
| 6 | itemnum | 项目标准编码 | string | 如：NEU# |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：iteminfo  项目信息    类型： object | 节点名称：iteminfo  项目信息    类型： object | 节点名称：iteminfo  项目信息    类型： object | 节点名称：iteminfo  项目信息    类型： object | 节点名称：iteminfo  项目信息    类型： object |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | INSTID | 仪器ID | int | 如：3 |
| 3 | ITEMCODE | 项目代码 | string | 如：0101002 |
| 4 | ITEMNAME | 项目名称 | string | 如：中性粒细胞绝对值 |
| 5 | ITEMNUM | 项目标准编码 | string | 如：NEU# |
| 节点名称：refertypelist异常值    类型： array<object> | 节点名称：refertypelist异常值    类型： array<object> | 节点名称：refertypelist异常值    类型： array<object> | 节点名称：refertypelist异常值    类型： array<object> | 节点名称：refertypelist异常值    类型： array<object> |
| 1 | SERIALNO | 序号 | string | 如：1 |
| 2 | VALUE | 结果值 | string | 如：3 |
| 3 | CONTAINBOUND | 字符结果 | string | 1-包含 2-全匹配 |
| 4 | HIGHLOWFLAG | 结果标志 | string | H 偏高 L 偏低 N 正常 P异常 1 阳性 0阴性 |
| 5 | HIGHLOWFLAGNAME | 结果标志名称 | string | 如：偏高 |



Json返回数据格式：



代码实现：

LIS_HIGHLOWVALUE表中获取异常结果值设置的信息，结果标志存在SLAVE_P表中，classcode=结果标志，结果标志按照序号排序。

表结构：LIS_HIGHLOWVALUE\SLAVE_P

##### B02	修改仪器项目异常值

接口说明：修改仪器项目异常值

请求URL：../institemchangemean/request/updatehighlow

代码文件：winning.cmp.institemhighlow.service.InstrumentItemHighLowService

示例URL： http://192.168.10.73:10004/lis/institemhighlow/request/updatehighlow?hospitalcode=9999&instname=AIA-360&itemname=TB&value=18&containbound=1&highlowflag=0&serialno=2

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | instname | 仪器名称 | string | 不允许为空，如：AU5800 |
| 2 | itemname | 项目名称 | string | 不允许为空，如：中性粒细胞绝对值 |
| 3 | serialno | 序号 | string | 不允许为空，如：1 |
| 4 | value | 结果值 | string | 不允许为空，如：3 |
| 5 | containbound | 字符结果 | string | 不允许为空，如：1 |
| 6 | highlowflag | 结果标志 | string | 不允许为空，如：H |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | SERIALNO | 序号 | string | 如：1 |
| 2 | VALUE | 结果值 | string | 如：3 |
| 3 | CONTAINBOUND | 字符结果 | string | 1-包含 2-全匹配 |
| 4 | HIGHLOWFLAG | 结果标志 | string | H |
| 5 | HIGHLOWFLAGNAME | 结果标志名称 | string | 如：偏高 |



Json返回数据格式：



代码实现：

根据主键，更新LIS_HIGHLOWVALUE表中信息

表结构：LIS_HIGHLOWVALUE\SLAVE_P

##### B03	删除仪器项目异常值

接口说明：删除仪器项目异常值

请求URL：../institemchangemean/request/deletehighlow

代码文件：winning.cmp.institemhighlow.service.InstrumentItemHighLowService

示例URL： http://192.168.10.73:10004/lis/institemhighlow/request/deletehighlow?hospitalcode=9999&instname=AIA-360&itemname=TB&serialno=2

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | instname | 仪器名称 | string | 不允许为空，如;AU5800 |
| 2 | itemname | 项目名称 | string | 不允许为空，如：中性粒细胞绝对值 |
| 3 | serialno | 序号 | string | 不允许为空，如：1 |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage |
| 1 | type | 消息类型 | string | SUCCESS-成功 
ERROR-系统出现异常或错误
FAILT-业务逻辑处理失败 |
| 2 | messageCode | 消息代码 | string | 用于区分消息代码 |
| 3 | message | 消息内容 | string | type=ERROR时则为系统错误消息提示内容
type=FAILT时则为业务提示信息 |
| 4 | link | URL链接 | string | 用于提示用户后可以点击查看详情信息，默认为空 |
| 5 | data | 数据集合 | object | 用于存储返回的数据 |



Json返回数据格式：



代码实现：

根据主键，删除LIS_HIGHLOWVALUE表中信息

表结构：LIS_HIGHLOWVALUE

##### B04	添加仪器项目异常值

接口说明：添加仪器项目异常值

请求URL：../institemchangemean/request/addhighlow

代码文件：winning.cmp.institemhighlow.service.InstrumentItemHighLowService

示例URL： http://192.168.10.73:10004/lis/institemhighlow/request/getinstitemhighlow?hospitalcode=9999&instid=12&instname=AIA-360&itemcode=TB&itemname=TB&itemnum=TB

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | instid | 仪器ID | string | 不允许为空，如：5 |
| 2 | instname | 仪器名称 | string | 不允许为空，如;AU5800 |
| 3 | itemcode | 项目代码 | string | 不允许为空，如：0101002 |
| 4 | itemnum | 项目标准编码 | string | 不允许为空，如：NEU# |
| 5 | itemname | 项目名称 | string | 如：中性粒细胞绝对值 |
| 6 | value | 结果值 | string | 不允许为空，如：3 |
| 7 | containbound | 字符结果 | string | 不允许为空，如：1 |
| 8 | highlowflag | 结果标志 | string | 不允许为空，如：H |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | SERIALNO | 序号 | string | 如：1 |
| 2 | INSTID | 仪器ID | string | 如：23 |
| 3 | ITEMCODE | 特性 | string | 1 |
| 4 | ITEMNAME | 项目名称 | string | 如：中性粒细胞绝对值 |
| 5 | ITEMNUM | 项目标准编码 | string | 如：NEU# |
| 6 | VALUE | 结果值 | string | 如：3 |
| 7 | CONTAINBOUND | 字符结果 | string | 1-包含 2-全匹配 |
| 8 | HIGHLOWFLAG | 结果标志 | string | H 偏高 L 偏低 N 正常 P异常 1 阳性 0阴性 |
| 9 | HIGHLOWFLAGNAME | 结果标志名称 | string | 如：偏高 |



Json返回数据格式：



代码实现：

插入LIS_HIGHLOWVALUE表中。

表结构：LIS_HIGHLOWVALUE\SLAVE_P

##### B05	获取仪器项目结果建议与解释

接口说明：获取仪器项目结果建议与解释

请求URL：../institemhighlow/request/getinterpretation

代码文件：winning.cmp.institemhighlow.service.InstrumentItemHighLowService

示例URL： http://192.168.10.73:10004/lis/institemhighlow/request/getinterpretation?hospitalcode=9999&instid=12&instname=AIA-360&itemcode=TB&itemname=TB&itemnum=TB

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | instid | 仪器ID | string | 不允许为空，如：5 |
| 3 | instname | 仪器名称 | string | 不允许为空，如;AU5800 |
| 4 | itemcode | 项目代码 | string | 不允许为空，如：0101002 |
| 5 | itemname | 项目名称 | string | 不允许为空，如：中性粒细胞绝对值 |
| 6 | itemnum | 项目标准编码 | string | 如：NEU# |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | SERIALNO | 序号 | string | 如：1 |
| 2 | INSTID | 仪器ID | string | 如：23 |
| 3 | ITEMCODE | 特性 | string | 1 |
| 4 | INTERPRETATION | 结果解释内容 | string | 前端需要这种形式显示，自定义一个分割符，可以按照输入的格式显示。
如：PG>=20;提示胃溃疡；
PG<10;提示正常； |



Json返回数据格式：



代码实现：

LIS_RESULTINTERPRETATION表中获取结果建议与解释的信息。

表结构：LIS_RESULTINTERPRETATION

##### B06	修改仪器项目结果建议与解释

接口说明：修改仪器项目结果建议与解释

请求URL：../institemchangemean/request/updateinterpretation

代码文件：winning.cmp.institemhighlow.service.InstrumentItemHighLowService

示例URL： 

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | serialno | 序号 | string | 不允许为空，如：1 |
| 2 | instname | 仪器名称 | string | 不允许为空，如：1 |
| 3 | itemname | 项目名称 | string | 不允许为空，如：1 |
| 4 | interpretation | 结果解释内容 | string | 不允许为空，前端需要这种形式显示，自定义一个分割符，可以按照输入的格式显示。
如：PG>=20;提示胃溃疡；
PG<10;提示正常； |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | SERIALNO | 序号 | string | 如：1 |
| 2 | INSTID | 仪器ID | string | 如：23 |
| 3 | ITEMCODE | 特性 | string | 1 |
| 4 | INTERPRETATION | 结果解释内容 | string | 前端需要这种形式显示，自定义一个分割符，可以按照输入的格式显示。
如：PG>=20;提示胃溃疡；
PG<10;提示正常； |



Json返回数据格式：



代码实现：

LIS_RESULTINTERPRETATION表中获取结果建议与解释的信息。

表结构：LIS_RESULTINTERPRETATION

##### B07	删除仪器项目结果建议与解释

接口说明：删除仪器项目结果建议与解释

请求URL：../institemchangemean/request/deleteinterpretation

代码文件：winning.cmp.institemhighlow.service.InstrumentItemHighLowService

示例URL： http://192.168.10.73:10004/lis/institemhighlow/request/deleteinterpretation?instname=AIA-360&itemname=TB&serialno=3

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | serialno | 序号 | string | 不允许为空，如：1 |
| 2 | itemname | 项目名称 | string | 不允许为空，如：1 |
| 3 | instname | 仪器名称 | string | 不允许为空，如;AU5800 |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage |
| 1 | type | 消息类型 | string | SUCCESS-成功 
ERROR-系统出现异常或错误
FAILT-业务逻辑处理失败 |
| 2 | messageCode | 消息代码 | string | 用于区分消息代码 |
| 3 | message | 消息内容 | string | type=ERROR时则为系统错误消息提示内容
type=FAILT时则为业务提示信息 |
| 4 | link | URL链接 | string | 用于提示用户后可以点击查看详情信息，默认为空 |
| 5 | data | 数据集合 | object | 用于存储返回的数据 |



Json返回数据格式：



代码实现：

LIS_RESULTINTERPRETATION表中删除结果建议与解释的信息。

表结构：LIS_RESULTINTERPRETATION

##### B08	添加仪器项目结果建议与解释

接口说明：添加仪器项目结果建议与解释

请求URL：../institemchangemean/request/addinterpretation

代码文件：winning.cmp.institemhighlow.service.InstrumentItemHighLowService

示例URL： http://192.168.10.73:10004/lis/institemhighlow/request/addinterpretation?hospitalcode=9999&instid=12&instname=AIA-360&itemcode=TB&itemname=TB&interpretation=PG>=20;提示胃溃疡;PG<=10;提示胃没有什么问题;

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | instid | 仪器ID | string | 不允许为空，如：5 |
| 2 | instname | 仪器名称 | string | 不允许为空，如;AU5800 |
| 3 | itemcode | 项目代码 | string | 不允许为空，如：0101002 |
| 4 | itemname | 项目名称 | string | 不允许为空，如：中性粒细胞绝对值 |
| 5 | interpretation | 结果解释内容 | string | 前端需要这种形式显示，自定义一个分割符，可以按照输入的格式显示。
如：PG>=20;提示胃溃疡；
PG<10;提示正常； |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | SERIALNO | 序号 | string | 如：1 |
| 2 | INSTID | 仪器ID | string | 如：23 |
| 3 | ITEMCODE | 特性 | string | 1 |
| 4 | INTERPRETATION | 结果解释内容 | string | 前端需要这种形式显示，自定义一个分割符，可以按照输入的格式显示。
如：PG>=20;提示胃溃疡；
PG<10;提示正常； |



Json返回数据格式：



代码实现：

LIS_RESULTINTERPRETATION表中获取结果建议与解释的信息。

表结构：LIS_RESULTINTERPRETATION

Json返回数据格式：



代码实现：

LIS_RESULTINTERPRETATION表中获取结果建议与解释的信息。

表结构：LIS_RESULTINTERPRETATION

### 仪器项目组合输入维护

#### V 模块接口视图

| NO. | 接口编号 | 接口名称 | 说明 |
| --- | --- | --- | --- |
| 1 | B01 | 获取仪器组合项目
../institemgroup/request/getinstitemgroup | 获取仪器组合项目 |
| 2 | B02 | 获取仪器组合项目明细
../institemgroup/request/getinstitemgroupdetail | 获取仪器组合项目明细 |
| 3 | B03 | 修改仪器组合项目信息
../institemgroup/request/updateinstitemgroup | 修改仪器组合项目信息 |
| 4 | B04 | 删除仪器组合项目
../institemgroup/request/deleteinstitemgroup | 删除仪器组合项目 |
| 5 |  | 手工添加仪器组合项目信息
../institemgroup/request/addinstitemgroup | 手工添加仪器组合项目信息 |
| 6 |  | 获取存在的仪器组合项目
../institemgroup/request/getexistsgroup | 获取存在的仪器组合项目 |
| 7 |  | 复制仪器组合项目
../institemgroup/request/copyinstitemgroup | 复制仪器组合项目 |



#### A 对外公布方法

##### A01	下拉框信息初始化

接口说明： 

请求URL：../institemgroup/request/getinitdata

代码文件：

示例URL： 

原型参考：



接口入参： 

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码 |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：samplecode   标本种类   类型： array<object> | 节点名称：samplecode   标本种类   类型： array<object> | 节点名称：samplecode   标本种类   类型： array<object> | 节点名称：samplecode   标本种类   类型： array<object> | 节点名称：samplecode   标本种类   类型： array<object> |
| 1 | CODE | 字典代码 | string | 如：1 |
| 2 | NAME | 字典名称 | string | 如：血 |
| 3 | MEMCODE1 | 输入码一 | string | 如：1 |
| 4 | MEMCODE2 | 输入码二 | string | 如：X |
| 5 | DICID | 标准编码 | string | 如：1 |
| 6 | DICTYPE | 字典分类 | string | 如：样本类型 |
| 7 | SUBSYSCODE | 子系统分类 | string | 如：LIMS |
| 8 | EXTERNCODE | 外部代码 | string | 如：10 |
| 9 | ORDERNO | 排序 | string | 如：0 |
| 10 | RESERVEFIELD1 | 扩展字段信息 | string | 如：null |
| 节点名称：examtype 检验分类   类型： array<object> | 节点名称：examtype 检验分类   类型： array<object> | 节点名称：examtype 检验分类   类型： array<object> | 节点名称：examtype 检验分类   类型： array<object> | 节点名称：examtype 检验分类   类型： array<object> |
| 字段同上 | 字段同上 | 字段同上 | 字段同上 | 字段同上 |





#### B 业务类

##### B01	获取仪器组合项目

接口说明：获取仪器组合项目

请求URL：../institemgroup/request/getinstitemgroup

代码文件：winning.lis.institemgroup.service.InstItemGroupService

示例URL： http://192.168.10.73:10004/lis/institemgroup/request/getinstitemgroup?hospitalcode=9999&instid=5&instname=AIA-360

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | instid | 仪器ID | string | 不允许为空，如：5 |
| 3 | instname | 仪器名称 | string | 不允许为空，如;AU5800 |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | INSTID | 仪器ID | int | 如：3 |
| 4 | GROUPID | 组合序号 | string | 如：1 |
| 5 | AUTOINPUT | 是否自动载入 | string | 如：1 |
| 6 | BEGINSAMPLEID | 起始样本号 | string | 如：1 |
| 7 | ENDSAMPLEID | 终止样本号 | string | 如：200 |
| 8 | SAMPLECODE | 标本种类代码 | string | 如：1 |
| 9 | SAMPLECODENAME | 标本种类名称 | string | 如：血 |
| 10 | GROUPCODE | 组合项目代码 | string | 如：1 |
| 11 | GROUPNAME | 组合项目名称 | string | 如：血常规 |
| 12 | MEMCODE1 | 输入码一 | string | 如：xcg |
| 13 | MEMCODE2 | 输入码二 | string | 如：1e2 |
| 14 | EXAMCODE | 检验分类代码 | string | 如：2 |
| 15 | EXAMCODENAME | 检验分类名称 | string | 如：肝功能 |



Json返回数据格式：

{

            "SERIALNO": 1,

            "INSTID": 5,

            "AUTOINPUT": "0",

            "BEGINSAMPLEID": 20,

            "ENDSAMPLEID": 500,

            "SAMPLECODE": "2",

            "GROUPID": 144,

            "HOSPITALCODE": "9999",

            "GROUPCODE": "ygwxdl",

            "GROUPNAME": "乙肝五项定量",

            "MEMCODE1": "ygwxdl",

            "MEMCODE2": "YGWXDL",

            "EXAMCODE": "3",

            "EXAMCODENAME": "胸水",

            "SAMPLECODENAME": "免疫"

        },

        {

            "SERIALNO": 2,

            "INSTID": 5,

            "AUTOINPUT": "0",

            "BEGINSAMPLEID": 10,

            "ENDSAMPLEID": 20,

            "SAMPLECODE": "1",

            "GROUPID": 142,

            "HOSPITALCODE": "9999",

            "GROUPCODE": "bdjj2",

            "GROUPNAME": "白带镜检2",

            "MEMCODE1": "bdjj2",

            "MEMCODE2": "BDJJ2",

            "EXAMCODE": "2",

            "EXAMCODENAME": "血",

            "SAMPLECODENAME": "临检"

        }

代码实现：

LIS_GROUPITEM表中获取对应的组合名称及对应的组合信息

表结构： LIS_GROUPITEM

##### B02	获取仪器组合项目明细

接口说明：获取仪器组合项目明细

请求URL：../institemgroup/request/getinstitemgroupdetail

代码文件：winning.lis.institemgroup.service.InstItemGroupService

示例URL： 172.17.13.165:10004/lis/institemgroup/request/getinstitemgroupdetail?instid=30&groupid=155&groupname=颜色浊度

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | groupname | 组合项目名称 | string | 不允许为空，如;AU5800 |
| 2 | groupid | 组合序号 | string | 不允许为空，如：2 |
| 3 | instid | 仪器ID | string | 不允许为空，如：4 |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | INSTID | 仪器ID | int | 如：3 |
| 3 | GROUPID | 组合序号 | string | 如：1 |
| 4 | ITEMCODE | 项目代码 | string | 如：1 |
| 5 | DEFAULTVALUE | 默认结果 | string | 如：1 |
| 6 | ITEMNAME | 项目名称 | string | 如：200 |
| 7 | ITEMNUM | 标准编码 | string | 如：1 |
| 8 | DEFVALUE | 默认参考值 | string | 如：血 |
| 9 | PREVALUE | 精度 | string | 如：1 |
| 10 | ITEMPRICE | 单价 | string | 如：40 |
| 11 | PRINTORDER | 打印序号 | string | 如：4 |
| 12 | UNIT | 单位 | string | 如：1 |
| 13 | UNITNAME | 单位名称 | string | 如：次 |
| 14 | HINTDESC | 提示 | string | 如：未配置仪器 |



Json返回数据格式：

{

            "GROUPID": 96,

            "ITEMCODE": "YS(c)               ",

            "DEFAULTVALUE": null,

            "HOSPITALCODE": "0",

            "ITEMNAME": "颜色",

            "ITEMNUM": "YS",

            "UNIT": null,

            "PRINTORDER": null,

            "PREVALUE": null,

            "DEFVALUE": null,

            "ITEMPRICE": null,

            "UNITNAME": null,

            "HINTDESC": "未配置仪器项目"

        },

        {

            "GROUPID": 96,

            "ITEMCODE": "ZD(c)               ",

            "DEFAULTVALUE": null,

            "HOSPITALCODE": "0",

            "ITEMNAME": "浊度",

            "ITEMNUM": "ZD",

            "UNIT": null,

            "PRINTORDER": null,

            "PREVALUE": null,

            "DEFVALUE": null,

            "ITEMPRICE": null,

            "UNITNAME": null,

            "HINTDESC": "未配置仪器项目"

        }

代码实现：

LIS_GROUPITEM表中获取对应的组合名称及对应的组合信息，LIS_GROUPITEMDETAIL对应的明细信息，LIS_ITEM获取项目信息，LIS_INSTITEM获取仪器项目的默认值和序号等一类信息。

需要判断项目是否在仪器项目中，不存在就提示未配置仪器。

表结构：LIS_GROUPITEM\LIS_GROUPITEMDETAIL\LIS_ITEM\LIS_INSTITEM

##### B03	修改仪器组合项目信息

接口说明：修改仪器组合项目信息

请求URL：../institemgroup/request/updateinstitemgroup

代码文件：winning.lis.institemgroup.service.InstItemGroupService

示例URL： 172.17.13.165:10004/lis/institemgroup/request/updateinstitemgroup?instid=10010&instname=AU5800&groupname=测试的组合002&groupcode=test002&autoinput=1&beginsampleid=20&endsampleid=300&samplecode=1&examcode=2&memcode1&itemcodeinfo =[{'itemcode':'AFU','defaultvalue':'2'}]&groupid=149

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | instid | 仪器ID | int | 不允许为空，如：3 |
| 2 | instname | 仪器名称 | string | 不允许为空，如：AU5800 |
| 3 | groupid | 组合序号 | string | 不允许为空，如：1 |
| 4 | autoinput | 是否自动载入 | string | 允许为空，如：1 |
| 5 | beginsampleid | 起始样本号 | string | 允许为空，如：1 |
| 6 | endsampleid | 终止样本号 | string | 允许为空，如：200 |
| 7 | samplecode | 标本种类代码 | string | 允许为空，如：1 |
| 8 | groupcode | 组合项目代码 | string | 不允许为空，如：12 |
| 9 | groupname | 组合项目名称 | string | 不允许为空，如：血常规 |
| 10 | examcode | 检验种类 | string | 允许为空，如：2 |
| 11 | memcode1 | 输入码一 | string | 允许为空，如：2 |
| 节点名称：itemcodeinfo  加项目信息 array<object> 不添加项目时可以为空，不为空时，按规则传 | 节点名称：itemcodeinfo  加项目信息 array<object> 不添加项目时可以为空，不为空时，按规则传 | 节点名称：itemcodeinfo  加项目信息 array<object> 不添加项目时可以为空，不为空时，按规则传 | 节点名称：itemcodeinfo  加项目信息 array<object> 不添加项目时可以为空，不为空时，按规则传 | 节点名称：itemcodeinfo  加项目信息 array<object> 不添加项目时可以为空，不为空时，按规则传 |
| 1 | itemcode | 项目代码 | string | 不允许为空，如：120003 |
| 2 | defaultvalue | 默认结果 | string | 允许为空，如：4 |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：groupinfo  项目信息 object | 节点名称：groupinfo  项目信息 object | 节点名称：groupinfo  项目信息 object | 节点名称：groupinfo  项目信息 object | 节点名称：groupinfo  项目信息 object |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | INSTID | 仪器ID | int | 如：3 |
| 4 | GROUPID | 组合序号 | string | 如：1 |
| 5 | AUTOINPUT | 是否自动载入 | string | 如：1 |
| 6 | BEGINSAMPLEID | 起始样本号 | string | 如：1 |
| 7 | ENDSAMPLEID | 终止样本号 | string | 如：200 |
| 8 | SAMPLECODE | 标本种类代码 | string | 如：1 |
| 9 | SAMPLECODENAME | 标本种类名称 | string | 如：血 |
| 10 | GROUPCODE | 组合项目代码 | string | 如：1 |
| 11 | GROUPNAME | 组合项目名称 | string | 如：血常规 |
| 12 | MEMCODE1 | 输入码一 | string | 如：xcg |
| 13 | MEMCODE2 | 输入码二 | string | 如：1e2 |
| 14 | EXAMCODE | 检验分类代码 | string | 如：2 |
| 15 | EXAMCODENAME | 检验分类名称 | string | 如：肝功能 |
| 节点名称：itemcodeinfo  组合项目明细信息 array<object> | 节点名称：itemcodeinfo  组合项目明细信息 array<object> | 节点名称：itemcodeinfo  组合项目明细信息 array<object> | 节点名称：itemcodeinfo  组合项目明细信息 array<object> | 节点名称：itemcodeinfo  组合项目明细信息 array<object> |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | INSTID | 仪器ID | int | 如：3 |
| 3 | GROUPID | 组合序号 | string | 如：1 |
| 4 | ITEMCODE | 项目代码 | string | 如：1 |
| 5 | DEFAULTVALUE | 默认结果 | string | 如：1 |
| 6 | ITEMNAME | 项目名称 | string | 如：200 |
| 7 | ITEMNUM | 标准编码 | string | 如：1 |
| 8 | DEFVALUE | 默认参考值 | string | 如：血 |
| 9 | PREVALUE | 精度 | string | 如：1 |
| 10 | ITEMPRICE | 单价 | string | 如：40 |
| 11 | PRINTORDER | 打印序号 | string | 如：4 |
| 12 | UNIT | 单位 | string | 如：1 |
| 13 | UNITNAME | 单位名称 | string | 如：次 |
| 14 | HINTDESC | 提示 | string | 如：未配置仪器 |



 Json返回数据格式：

{

        "groupinfo": {

            "GROUPID": 152,

            "HOSPITALCODE": "0",

            "GROUPCODE": "test002",

            "GROUPNAME": "测试的组合002",

            "MEMCODE1": "231",

            "MEMCODE2": "IYRXW002",

            "EXAMCODE": "2",

            "INSTID": 10010,

            "SAMPLECODE": "1",

            "AUTOINPUT": "1",

            "BEGINSAMPLEID": 20,

            "ENDSAMPLEID": 300,

            "SAMPLECODENAME": null,

            "EXAMCODENAME": "临检"

        },

        "itemcodeinfo": [

            {

                "GROUPID": 152,

                "ITEMCODE": "C-BS",

                "DEFAULTVALUE": "2",

                "HOSPITALCODE": "0",

                "ITEMNAME": "葡萄糖",

                "ITEMNUM": "C-BS",

                "UNIT": null,

                "PRINTORDER": null,

                "PREVALUE": 0.1,

                "DEFVALUE": "",

                "ITEMPRICE": null,

                "UNITNAME": null,

                "HINTDESC": ""

            },

            {

                "GROUPID": 152,

                "ITEMCODE": "CAF-TP",

                "DEFAULTVALUE": "2",

                "HOSPITALCODE": "0",

                "ITEMNAME": "胸腹水蛋白",

                "ITEMNUM": "CAF-TP",

                "UNIT": "g/L",

                "PRINTORDER": 630,

                "PREVALUE": 0.01,

                "DEFVALUE": "",

                "ITEMPRICE": null,

                "UNITNAME": null,

                "HINTDESC": ""

            }

        ]

    }

代码实现：

如果添加的项目信息为空，删除对应组合下面的所有的检验项目，如果不为空，删除不在传输列表中的其他所有的检验项目（注意：未配置的检验项目不删除），前端要注意，如果点开后没有信息修改，不要调用接口

LIS_GROUPITEM表中更新对应的组合名称及对应的组合信息，LIS_GROUPITEMDETAIL表中更新组合内的项目代码

表结构：LIS_GROUPITEM\LIS_GROUPITEMDETAIL

##### B04	获取仪器组合项目的信息列表

接口说明：获取仪器组合项目列表

请求URL：../institemgroup/request/getinstitemlist

代码文件：winning.lis.institemgroup.service.InstItemGroupService

示例URL： 172.17.13.165:10004/lis/institemgroup/request/getinstitemlist?instid=10010&instname=AU5800&beginsampleid=20&groupid=152

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | instid | 仪器ID | string | 不允许为空，如：5 |
| 3 | instname | 仪器名称 | string | 不允许为空，如：AU5800 |
| 4 | groupid | 组合序号 | string | 允许为空，添加组合时可以为空，修改组合时，传递组合的ID |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | SIZE | 项目数量 | string | 如：10 |
| 节点名称：INFO 项目信息 类型： array<object> | 节点名称：INFO 项目信息 类型： array<object> | 节点名称：INFO 项目信息 类型： array<object> | 节点名称：INFO 项目信息 类型： array<object> | 节点名称：INFO 项目信息 类型： array<object> |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | INSTID | 仪器ID | int | 如：3 |
| 3 | ITEMCODE | 项目代码 | string | 如：0101002 |
| 4 | ITEMNAME | 项目名称 | string | 如：中性粒细胞绝对值 |
| 5 | ITEMNUM | 项目标准编码 | string | 如：NEU# |
| 6 | ENGSHORTNAME | 英文缩写 | string | 如：NEU# |
| 7 | ENGNAME | 英文名称 | int | 如：dd |
| 8 | LOINCCODE | LOINC编码 | string | 如：123 |
| 9 | RESULTTYPE | 结果类型 | string | 如：2 |
| 10 | RESULTTYPENAME | 结果类型名称 | string | 如：字符 |
| 11 | RESULTTYPEFLAG | 结果类型选择 | string | 如：false |
| 12 | INSTITEMCODE | 项目通道号 | string | 如：ZHUMX |
| 13 | EXAMMETHOD | 测试方法 | string | 如：02 |
| 14 | EXAMMETHODFLAG | 测试方法选择 | string | 如：false |
| 15 | PRINTGROUP | 打印分组 | string | A..Z |
| 16 | PRINTORDER | 打印序号 | string | 如：001 |
| 17 | PREVALUE | 精度 | string | 如：001 |
| 18 | PREVALUENAME | 精度 | string | 如：1 |
| 19 | PREVALUEFLAG | 精度选择 | string | 如：false |
| 20 | UNIT | 单位 | string | 如：001 |
| 21 | UNITFLAG | 单位 | string | 如：false |
| 22 | DEFVALUE | 缺省值（默认结果） | string | 如：001 |
| 23 | CORRECTOR | 校正公式 | string | [X]*100 |
| 24 | WORKLIST | 双工项目 | string | 如：0 |
| 25 | IMPORTANT | 重要项目 | string | 如：0 |
| 26 | ITEMPRICE | 单价 | string | 如：18.5 |
| 27 | REPORTINSTR | 报告仪器 | string | 如：12 |
| 28 | REPORTINSTRNAME | 报告仪器名称 | string | 如：HS-201 |
| 29 | REPORTINSTRFLAG | 报告仪器选择 | string | 如：false |
| 30 | DISPLAYFLAG | 打印标识 | string | 如： 0 |
| 31 | QCEVALUATION | 互认项目 | string | 如： |
| 32 | PRIORITY | 入库优先项目 | string | 如：0 |
| 33 | REQUIRED | 必要项目 | string | 如：0 |
| 34 | HIDEFLAG | 隐藏项目 | string | 如：0 |
| 35 | SCIENTIFICFLAG | 科研标志 | string | 如：0 |



Json返回数据格式：

[

        {

            "HOSPITALCODE": "0",

            "ITEMNAME": "5‘-核苷酸酶",

            "ITEMNUM": "5-NT",

            "ITEMCODE": "48",

            "PRINTORDER": null,

            "PREVALUE": null,

            "DEFVALUE": "",

            "ITEMPRICE": null,

            "HINTDESC": ""

        },

        {

            "HOSPITALCODE": "0",

            "ITEMNAME": "实际碳酸氢根",

            "ITEMNUM": "AB",

            "ITEMCODE": "AB",

            "PRINTORDER": null,

            "PREVALUE": 0.1,

            "DEFVALUE": "",

            "ITEMPRICE": null,

            "HINTDESC": ""

        }

]

代码实现：

#### B05	删除仪器组合项目

接口说明：删除仪器组合项目

请求URL：../institemgroup/request/deleteinstitemgroup

代码文件：winning.lis.institemgroup.service.InstItemGroupService

示例URL： 172.17.13.165:10004/lis/institemgroup/request/deleteinstitemgroup?hospitalcode=0&groupid=150&instname=AU5800&groupname=测试的组合004

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | instname | 仪器名称 | string | 不允许为空，如;AU5800 |
| 3 | groupid | 项目组合序号 | string | 不允许为空，如：3 |
| 4 | groupname | 项目组合名称 | string | 允许为空，如：白带常规 |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage |
| 1 | type | 消息类型 | string | SUCCESS-成功 
ERROR-系统出现异常或错误
FAILT-业务逻辑处理失败 |
| 2 | messageCode | 消息代码 | string | 用于区分消息代码 |
| 3 | message | 消息内容 | string | type=ERROR时则为系统错误消息提示内容
type=FAILT时则为业务提示信息 |
| 4 | link | URL链接 | string | 用于提示用户后可以点击查看详情信息，默认为空 |
| 5 | data | 数据集合 | object | 用于存储返回的数据 |



Json返回数据格式：



代码实现：

根据serialno删除仪器组合记录表LIS_INSTITEMGROUP的数据，根据groupid删除LIS_GROUPITEM表中的组合项目名称信息，根据groupid删除LIS_GROUPITEMDETAIL中的明细记录

表结构：LIS_INSTITEMGROUP\LIS_GROUPITEM\LIS_GROUPITEMDETAIL

#### B06	删除仪器组合项目中的检验项目

接口说明：删除仪器组合项目

请求URL：../institemgroup/request/deleteinstitemgroupdetail

代码文件：winning.lis.institemgroup.service.InstItemGroupService

示例URL： 172.17.13.165:10004/lis/institemgroup/request/deleteinstitemgroupdetail?hospitalcode=0&groupid=151&itemcode=CAF-BS&groupname=测试的组合004

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | groupid | 项目组合序号 | string | 不允许为空，如：3 |
| 3 | groupname | 项目组合名称 | string | 允许为空，如：白带常规 |
| 4 | itemcode | 检验项目代码 | string | 不允许为空，如：WBC |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage |
| 1 | type | 消息类型 | string | SUCCESS-成功 
ERROR-系统出现异常或错误
FAILT-业务逻辑处理失败 |
| 2 | messageCode | 消息代码 | string | 用于区分消息代码 |
| 3 | message | 消息内容 | string | type=ERROR时则为系统错误消息提示内容
type=FAILT时则为业务提示信息 |
| 4 | link | URL链接 | string | 用于提示用户后可以点击查看详情信息，默认为空 |
| 5 | data | 数据集合 | object | 用于存储返回的数据 |



Json返回数据格式：

{

    "data": [],

    "failtCode": "",

    "funName": "",

    "link": "",

    "message": "删除组合【测试的组合004】检验项目信息成功",

    "messageCode": "",

    "requstUrl": "",

    "serverTime": "2018-05-07 16:07:53",

    "type": "SUCCESS"

}

代码实现：

根据serialno删除仪器组合记录表LIS_INSTITEMGROUP的数据，根据groupid删除LIS_GROUPITEM表中的组合项目名称信息，根据groupid删除LIS_GROUPITEMDETAIL中的明细记录

表结构：LIS_INSTITEMGROUP\LIS_GROUPITEM\LIS_GROUPITEMDETAIL



#### B07	手动添加仪器组合项目

接口说明：手工添加仪器组合项目信息

请求URL：../institemgroup/request/addinstitemgroup

代码文件：winning.lis.institemgroup.service.InstItemGroupService

示例URL： 172.17.13.165:10004/lis/institemgroup/request/addinstitemgroup?instid=10010&instname=AU5800&groupname=测试的组合006&groupcode=test006&autoinput=1&beginsampleid=20&endsampleid=300&samplecode=1&examcode=2&memcode1&itemcodeinfo

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | instid | 仪器ID | int | 不允许为空，如：3 |
| 2 | instname | 仪器名称 | string | 不允许为空，如：AU5800 |
| 3 | autoinput | 是否自动载入 | string | 不允许为空，如：1 |
| 4 | beginsampleid | 起始样本号 | string | 允许为空，如：1 |
| 5 | endsampleid | 终止样本号 | string | 允许为空，如：200 |
| 6 | samplecode | 标本种类代码 | string | 允许为空，如：1 |
| 7 | groupcode | 组合项目代码 | string | 不允许为空，如：12 |
| 8 | groupname | 组合项目名称 | string | 不允许为空，如：血常规 |
| 9 | examcode | 检验种类 | string | 允许为空，如：2 |
| 10 | memcode1 | 输入码一 | string | 允许为空，如：2 |
| 节点名称：itemcodeinfo  加项目信息 array<object> 不添加项目时可以为空，不为空时，按规则传 | 节点名称：itemcodeinfo  加项目信息 array<object> 不添加项目时可以为空，不为空时，按规则传 | 节点名称：itemcodeinfo  加项目信息 array<object> 不添加项目时可以为空，不为空时，按规则传 | 节点名称：itemcodeinfo  加项目信息 array<object> 不添加项目时可以为空，不为空时，按规则传 | 节点名称：itemcodeinfo  加项目信息 array<object> 不添加项目时可以为空，不为空时，按规则传 |
| 1 | itemcode | 项目代码 | string | 不允许为空，如：120003 |
| 2 | defaultvalue | 默认结果 | string | 允许为空，如：4 |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：groupinfo  项目信息 object | 节点名称：groupinfo  项目信息 object | 节点名称：groupinfo  项目信息 object | 节点名称：groupinfo  项目信息 object | 节点名称：groupinfo  项目信息 object |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | INSTID | 仪器ID | int | 如：3 |
| 4 | GROUPID | 组合序号 | string | 如：1 |
| 5 | AUTOINPUT | 是否自动载入 | string | 如：1 |
| 6 | BEGINSAMPLEID | 起始样本号 | string | 如：1 |
| 7 | ENDSAMPLEID | 终止样本号 | string | 如：200 |
| 8 | SAMPLECODE | 标本种类代码 | string | 如：1 |
| 9 | SAMPLECODENAME | 标本种类名称 | string | 如：血 |
| 10 | GROUPCODE | 组合项目代码 | string | 如：1 |
| 11 | GROUPNAME | 组合项目名称 | string | 如：血常规 |
| 12 | MEMCODE1 | 输入码一 | string | 如：xcg |
| 13 | MEMCODE2 | 输入码二 | string | 如：1e2 |
| 14 | EXAMCODE | 检验分类代码 | string | 如：2 |
| 15 | EXAMCODENAME | 检验分类名称 | string | 如：肝功能 |
| 节点名称：itemcodeinfo  组合项目明细信息 array<object> | 节点名称：itemcodeinfo  组合项目明细信息 array<object> | 节点名称：itemcodeinfo  组合项目明细信息 array<object> | 节点名称：itemcodeinfo  组合项目明细信息 array<object> | 节点名称：itemcodeinfo  组合项目明细信息 array<object> |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | INSTID | 仪器ID | int | 如：3 |
| 3 | GROUPID | 组合序号 | string | 如：1 |
| 4 | ITEMCODE | 项目代码 | string | 如：1 |
| 5 | DEFAULTVALUE | 默认结果 | string | 如：1 |
| 6 | ITEMNAME | 项目名称 | string | 如：200 |
| 7 | ITEMNUM | 标准编码 | string | 如：1 |
| 8 | DEFVALUE | 默认参考值 | string | 如：血 |
| 9 | PREVALUE | 精度 | string | 如：1 |
| 10 | ITEMPRICE | 单价 | string | 如：40 |
| 11 | PRINTORDER | 打印序号 | string | 如：4 |
| 12 | UNIT | 单位 | string | 如：1 |
| 13 | UNITNAME | 单位名称 | string | 如：次 |
| 14 | HINTDESC | 提示 | string | 如：未配置仪器 |



Json返回数据格式：

{

        "groupinfo": {

            "GROUPID": 152,

            "HOSPITALCODE": "0",

            "GROUPCODE": "test006",

            "GROUPNAME": "测试的组合006",

            "MEMCODE1": "csdzh006",

            "MEMCODE2": "IYRXW006",

            "EXAMCODE": "2",

            "INSTID": 10010,

            "SAMPLECODE": "1",

            "AUTOINPUT": "1",

            "BEGINSAMPLEID": 20,

            "ENDSAMPLEID": 300,

            "SAMPLECODENAME": null,

            "EXAMCODENAME": "临检"

        },

        "itemcodeinfo": null

    }

代码实现：

LIS_GROUPITEM表中更新对应的组合名称及对应的组合信息，LIS_GROUPITEMDETAIL表中更新组合内的项目代码

表结构：LIS_GROUPITEM\LIS_GROUPITEMDETAIL

#### B08	获取存在的仪器组合项目

接口说明：获取存在的仪器组合项目

请求URL：../institemgroup/request/getexistsgroup

代码文件：winning.lis.institemgroup.service.InstItemGroupService

示例URL： 172.17.13.165:10004/lis/institemgroup/request/getexistsgroup?hospitalcode=0&instid=10010&instname=AU5800&flag=0

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | instid | 仪器ID | string | 不允许为空，如：12 |
| 3 | instname | 仪器名称 | string | 允许为空，如：AU5800 |
| 3 | flag | 适合仪器标志 | string | 不允许为空，0—适合本仪器，1—全部仪器 |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | GROUPID | 组合序号 | string | 如：1 |
| 2 | GROUPCODE | 组合项目代码 | string | 如：1 |
| 3 | GROUPNAME | 组合项目名称 | string | 如：血常规 |
| 4 | SAMPLECODE | 标本种类代码 | string | 如：1 |
| 5 | SAMPLECODENAME | 标本种类名称 | string | 如：血 |
| 6 | ENDSAMPLEID | 终止样本号 | string | 如：200 |
| 7 | BEGINSAMPLEID | 开始样本号 | string | 如：200 |
| 8 | HINTDESC | 提示信息 | string | 如：【白细胞】未配置仪器 |
| 9 | SIZE | 数量 | string |  |
| 10 | ITEMCODELIST | 项目信息 | array<object> |  |
| 1 | ITEMCODE | 项目代码 | string | 如：YS(c) |
| 2 | ITEMNAME | 项目名称 | string | 如：颜色 |
| 3 | ITEMNUM | 项目标准编码 | string | 如：YS |



Json返回数据格式：

[

         {

            "GROUPID": "96",

            "SAMPLECODENAME": "",

            "GROUPCODE": "762",

            "GROUPNAME": "颜色浊度",

            "SAMPLECODE": "",

            "BEGINSAMPLEID": "",

            "ENDSAMPLEID": "",

            "HINTDESC": "本仪器未配置项目【颜色】【浊度】",

            "size": 2,

            "itemcodelist": [

                {

                    "ITEMCODE": "YS(c)",

                    "ITEMNAME": "颜色",

                    "ITEMNUM": "YS"

                },

                {

                    "ITEMCODE": "ZD(c)",

                    "ITEMNAME": "浊度",

                    "ITEMNUM": "ZD"

                }

            ]

        }

]

代码实现：

LIS_INSTITEMGROUP表中更新仪器组合信息，LIS_GROUPITEM表中更新对应的组合名称及对应的组合信息，LIS_GROUPITEMDETAIL表中更新组合内的项目代码

根据仪器ID找到本仪器的项目itemcode

判断对应组合的项目是否在目前的仪器的项目中，存在，符合本仪器，不存在或者多余目前仪器的项目，不符合本仪器

表结构：LIS_INSTITEMGROUP\LIS_GROUPITEM\LIS_GROUPITEMDETAIL

#### B09	复制仪器组合项目

接口说明：复制仪器组合项目

请求URL：../institemgroup/request/copyinstitemgroup

代码文件：winning.lis.institemgroup.service.InstItemGroupService

示例URL： 172.17.13.165:10004/lis/institemgroup/request/copyinstitemgroup?hospitalcode=0&instid=30&instname=e411&groupid=['152']

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | instid | 仪器ID | string | 不允许为空，如：12 |
| 2 | instname | 仪器名称 | string | 不允许为空，如：12 |
| 3 | hospitalcode | 医疗机构代码 | string | 如：9999 |
| 4 | groupid | 组合序号 | array<string> | 不允许为空，如：5,4 |



接口出参：B01	获取仪器组合项目

 Json返回数据格式：



代码实现：

LIS_GROUPITEM表中插入对应的组合名称及对应的组合信息，LIS_GROUPITEMDETAIL表中插入组合内的项目代码

表结构：LIS_INSTITEMGROUP\LIS_GROUPITEM\LIS_GROUPITEMDETAIL

#### B10	仪器组合项目自动输入设置

接口说明：仪器组合项目自动输入设置

请求URL：../institemgroup/request/updateinstitemgroupinput

代码文件：winning.lis.institemgroup.service.InstItemGroupService

示例URL： 

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | instid | 仪器ID | string | 不允许为空，如：12 |
| 2 | instname | 仪器名称 | string | 不允许为空，如：12 |
| 3 | hospitalcode | 医疗机构代码 | string | 如：9999 |
| 4 | groupid | 组合序号 | string | 不允许为空，如：5 |
| 5 | autoinput | 自动输入 | string | 不允许为空，如：0 |



接口出参：B01	获取仪器组合项目

 Json返回数据格式：



代码实现：

表结构：LIS_GROUPITEM

### 仪器属性维护

#### V 模块接口视图

| NO. | 接口编号 | 接口名称 | 说明 |
| --- | --- | --- | --- |
| 1 | B01 | 获取仪器组合项目
../institemgroup/request/getinstitemgroup | 获取仪器组合项目 |
| 2 | B02 | 获取仪器组合项目明细
../institemgroup/request/getinstitemgroupdetail | 获取仪器组合项目明细 |
| 3 | B03 | 修改仪器组合项目信息
../institemgroup/request/updateinstitemgroup | 修改仪器组合项目信息 |
| 4 | B04 | 删除仪器组合项目
../institemgroup/request/deleteinstitemgroup | 删除仪器组合项目 |
| 5 |  | 手工添加仪器组合项目信息
../institemgroup/request/addinstitemgroup | 手工添加仪器组合项目信息 |
| 6 |  | 获取存在的仪器组合项目
../institemgroup/request/getexistsgroup | 获取存在的仪器组合项目 |
| 7 |  | 复制仪器组合项目
../institemgroup/request/copyinstitemgroup | 复制仪器组合项目 |



#### A 对外公布方法

##### A01	仪器属性初始化

接口说明：仪器属性初始化

请求URL：../instproperty/request/getpropertylist

代码文件：winning.lis. instproperty.service.InstPropertyService

示例URL： 172.17.13.165:10004/lis/instproperty/request/getpropertylist

原型参考：



接口入参： 

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码 |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | SERIALNO | 序号 | string | 如：1 |
| 2 | SYSTYPE | 系统类型 | string | C-常规 W-微生物 X-血库 |
| 3 | TYPE | 仪器属类型 | string | 如：1 |
| 4 | PROPERTY | 仪器属性 | string | 如：X |
| 5 | REMARK | 备注 | string | 如：1 |
| 6 | STOPFLAG | 停用标志 | string | 0-正常 1-停用 |



Json返回数据格式：

[

        {

            "SERIALNO": 60,

            "SYSTYPE": "C",

            "TYPE": "报告操作",

            "PROPERTY": "报告输入弹出查看全部图像",

            "REMARK": null,

            "STOPFLAG": "0"

        }

]

代码实现：

表结构：LIS_INSTPROPERTY_DIC

#### B 业务类

##### B01	获取仪器属性

接口说明：获取仪器属性

请求URL：../instproperty/request/getinstpropertylist

代码文件：winning.lis. instproperty.service.InstPropertyService

示例URL： 172.17.13.165:10004/lis/instproperty/request/getinstpropertylist?hospitalcode=&instid=3&instname=HX-21

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | instid | 仪器ID | string | 不允许为空，如：5 |
| 3 | instname | 仪器名称 | string | 不允许为空，如;AU5800 |
| 4 | dataflag | 仪器属性过滤标志 | string | 不允许为空，true—查看已启用，false—查看全部 |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | TYPE | 操作类型 | string |  |
| 节点名称：TYPELIST   项目信息 类型： array<object> | 节点名称：TYPELIST   项目信息 类型： array<object> | 节点名称：TYPELIST   项目信息 类型： array<object> | 节点名称：TYPELIST   项目信息 类型： array<object> | 节点名称：TYPELIST   项目信息 类型： array<object> |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | INSTID | 仪器ID | int | 如：3 |
| 4 | PROPERTY | 仪器属性 | string | 如：发布报告不能再接收仪器数据 |
| 5 | DATA | 属性值 | string | 如：TRUE |
| 6 | SERIALNO | 序号 | string | 如：1 |
| 7 | SYSTYPE | 系统类型 | string | C-常规 W-微生物 X-血库 |
| 8 | TYPE | 仪器属类型 | string | 如：1 |
| 9 | REMARK | 备注 | string | 如：血 |
| 10 | STOPFLAG | 停用标志 | string | 如：1 |



Json返回数据格式：

[

        {

            "TYPE": "报告操作",

            "TYPELIST": [

                {

                    "INSTID": 3,

                    "PROPERTY": "发布报告不能再接收仪器数据",

                    "DATA": "True",

                    "HOSPITALCODE": "9999",

                    "SERIALNO": 40,

                    "SYSTYPE": "C",

                    "TYPE": "报告操作",

                    "REMARK": null,

                    "STOPFLAG": "0"

                },

                {

                    "INSTID": 3,

                    "PROPERTY": "发布报告不能再接收仪器数据",

                    "DATA": "True",

                    "HOSPITALCODE": "9999",

                    "SERIALNO": 82,

                    "SYSTYPE": "W",

                    "TYPE": "报告操作",

                    "REMARK": null,

                    "STOPFLAG": "0"

                }

            ]

        }

]

代码实现：

LIS_INSTPROPERTY表中获取对应的仪器属性信息，LIS_INSTPROPERTY_DIC表中获取属性的分类和属性值，

表结构： LIS_INSTPROPERTY\LIS_INSTPROPERTY_DIC

##### B02	修改仪器属性

接口说明：修改仪器属性

请求URL：../instproperty/request/updateinstproperty

代码文件：winning.lis. instproperty.service.InstPropertyService

示例URL： 

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | instid | 仪器ID | string | 不允许为空，如：5 |
| 3 | instname | 仪器名称 | string | 不允许为空，如:AU5800 |
| 4 | property | 仪器属性 | string | 不允许为空，如: 保存后自动翻页 |
| 5 | data | 属性值 | string | 不允许为空，TRUE—开启，FALSE—关闭 |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | INSTID | 仪器ID | int | 如：3 |
| 4 | PROPERTY | 仪器属性 | string | 如：发布报告不能再接收仪器数据 |
| 5 | DATA | 属性值 | string | 如：TRUE |



Json返回数据格式：

代码实现：

LIS_INSTPROPERTY表中获取对应的仪器属性信息，LIS_INSTPROPERTY_DIC表中获取属性的分类和属性值，

表结构： LIS_INSTPROPERTY\LIS_INSTPROPERTY_DIC

##### B03	获取属性的配置仪器列表

接口说明：获取属性的配置仪器列表

请求URL：../instproperty/request/getpropertyinstlist

代码文件：winning.lis. instproperty.service.InstPropertyService

示例URL： 

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | property | 仪器属性 | string | 不允许为空，如: 保存后自动翻页 |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 2 | INSTID | 仪器ID | int | 如：3 |
| 4 | INSTCODE | 仪器编码 | string | 如：1 |
| 5 | INSTNAME | 仪器名称 | string | 如：1 |



Json返回数据格式：

代码实现：

LIS_INSTPROPERTY表中获取对应的属性信息，LIS_INSTRUMENT表中获取仪器信息，

表结构： LIS_INSTPROPERTY\LIS_INSTRUMENT\

##### B04	添加属性对应的配置仪器

接口说明：添加属性对应的配置仪器

请求URL：../instproperty/request/addpropertyinst

代码文件：winning.lis. instproperty.service.InstPropertyService

示例URL： 

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | property | 仪器属性 | string | 不允许为空，如: 保存后自动翻页 |
| 2 | lstInstInfo |  |  |  |
| 节点名称：instinfo  添加仪器信息 array<object> 不允许为空 | 节点名称：instinfo  添加仪器信息 array<object> 不允许为空 | 节点名称：instinfo  添加仪器信息 array<object> 不允许为空 | 节点名称：instinfo  添加仪器信息 array<object> 不允许为空 | 节点名称：instinfo  添加仪器信息 array<object> 不允许为空 |
| 1 | instid | 仪器ID | string | 不允许为空，如：5 |
| 2 | instname | 仪器名称 | string | 不允许为空，如:AU5800 |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | INSTID | 仪器ID | int | 如：3 |
| 4 | INSTNAME | 组合序号 | string | 如：1 |
| 5 | INSTNAME | 是否自动载入 | string | 如：1 |
| 6 | PROPERTY | 仪器属性 | string | 如：1 |



Json返回数据格式：

代码实现：

LIS_INSTPROPERTY表中获取对应的属性信息，LIS_INSTRUMENT表中获取仪器信息，

表结构： LIS_INSTPROPERTY\LIS_INSTRUMENT

##### B05	获取属性的未配置仪器

接口说明：获取属性的未配置仪器

请求URL：../instproperty/request/getnopropertyinstlist

代码文件：winning.lis. instproperty.service.InstPropertyService

示例URL： 

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | property | 仪器属性 | string | 不允许为空，如: 保存后自动翻页 |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | INSTID | 仪器ID | int | 如：3 |
| 2 | INSTCODE | 仪器编码 | string | 如：1 |
| 3 | INSTNAME | 仪器名称 | string | 如：1 |



Json返回数据格式：

代码实现：

LIS_INSTPROPERTY表中获取对应的属性信息，LIS_INSTRUMENT表中获取仪器信息，LIS_INSTPROPERTY_DIC属性字典信息

表结构： LIS_INSTPROPERTY\LIS_INSTRUMENT\ LIS_INSTPROPERTY_DIC

##### B06	删除属性的配置仪器

接口说明：删除属性的配置仪器

请求URL：../instproperty/request/deletepropertyinst

代码文件：winning.lis. instproperty.service.InstPropertyService

示例URL： 

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | property | 仪器属性 | string | 不允许为空，如: 保存后自动翻页 |
| 节点名称：instinfo  添加项目信息 array<object> 不允许为空 | 节点名称：instinfo  添加项目信息 array<object> 不允许为空 | 节点名称：instinfo  添加项目信息 array<object> 不允许为空 | 节点名称：instinfo  添加项目信息 array<object> 不允许为空 | 节点名称：instinfo  添加项目信息 array<object> 不允许为空 |
| 1 | instid | 仪器ID | string | 不允许为空，如：5 |
| 2 | instname | 仪器名称 | string | 不允许为空，如:AU5800 |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage |
| 1 | type | 消息类型 | string | SUCCESS-成功 
ERROR-系统出现异常或错误
FAILT-业务逻辑处理失败 |
| 2 | messageCode | 消息代码 | string | 用于区分消息代码 |
| 3 | message | 消息内容 | string | type=ERROR时则为系统错误消息提示内容
type=FAILT时则为业务提示信息 |
| 4 | link | URL链接 | string | 用于提示用户后可以点击查看详情信息，默认为空 |
| 5 | data | 数据集合 | object | 用于存储返回的数据 |



Json返回数据格式：

代码实现：

LIS_INSTPROPERTY表中获取对应的属性信息，LIS_INSTRUMENT表中获取仪器信息，

表结构： LIS_INSTPROPERTY\LIS_INSTRUMENT

##### B07	获取属性名称列表

接口说明：获取属性名称列表

请求URL：../instproperty/request/getpropertynamelist

代码文件：winning.lis. instproperty.service.InstPropertyService

示例URL： 

原型参考：



接口入参：无

接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | TYPE | 操作类型 | string |  |



Json返回数据格式：

[ 

            "TYPE": "报告操作"

]

代码实现：

LIS_INSTPROPERTY_DIC表中获取属性的分类和属性值，

表结构： LIS_INSTPROPERTY_DIC



### 仪器配置审核条件

参数说明：入参全小写，出参全大写

#### V 模块接口视图

| NO. | 接口编号 | 接口名称 | 说明 |
| --- | --- | --- | --- |
| 1 | B01 | 获取仪器项目 | 获取仪器项目 |
| 2 | B02 | 获取仪器项目审核规则 | 获取仪器项目审核规则 |



#### A 对外公布方法

##### A01	分类初始化

接口说明： 

请求URL：../ institemcondition/request/getinitdata

代码文件：

示例URL： 

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |





接口出参【ResposeMessage.data. instconditionlist -> array<object>】：



##### A02	分类初始化

接口说明： 

请求URL：../ institemcondition/request/getinitdata

代码文件：

示例URL： 

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |





接口出参【ResposeMessage.data. conruletypelist -> array<object>】：





#### B 业务类

##### B01	获取仪器项目

接口说明：获取仪器项目

请求URL：../ institemcondition/request/getinstitemlist

代码文件：

示例URL： 

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | instid | 仪器ID | string | 不允许为空，如：5 |
| 3 | nodeno | 分类节点代码 | string | 不允许为空，如; INSTRUMENT_CONDITION_STATUS状态
或INSTRUMENT_CONDITION_RULES 已配置规则类型
或INSTRUMENT_RESULTE_TYPES结果类型 |
| 4 | code | 分类内容代码 | string | 不允许为空，如全部：1 |



接口出参【ResposeMessage.data-> array<object>】：

Json返回数据格式：



代码实现：

##### B02	获取仪器项目审核规则

接口说明：获取仪器项目审核规则

请求URL：../ institemcondition/request/ getitemconditionlist

代码文件：

示例URL： 

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | instid | 仪器ID | string | 不允许为空，如：5 |
| 2 | itemcode | 项目编码 | string | WBC |
| 3 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |



接口出参【ResposeMessage.data-> array<object>】：

Json返回数据格式：



代码实现：

##### B03	添加项目异常值审核规则

接口说明：获取仪器项目审核规则

请求URL：../ institemcondition/request/ additemcondition

代码文件：

示例URL： 

原型参考：



接口入参：

审核条件部分

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | INSTID | 仪器ID | string | 不允许为空，如：5 |
| 2 | INSTCODE | 仪器代码 | string | KX21 |
| 3 | ITEMCODE | 项目代码 | string | WBC |
| 4 | RULENAME | 规则名称 | string | 例如：结果包含异常字符 |
| 5 | RULEVALUE | 异常字符 | String | #，%，* |
| 6 | GRADE | 规则等级 | string | 2 |
| 7 | CODE | 审核规则类型 | String | 1-异常值验证，2-历史差值，3-结果区间及标识，4-仪器报警，数据来源institemcondition/request/getinitdata的instconditionlist的INSTRUMENT_CONDITION_RULES（已配置规则类型） |
| 8 | RULEBYCODE | 依据代码 | String | CONTAIN-包含，EQUAL-等于，NOTCONTAIN-不包含，数据来源于institemcondition/request/getinitdata的conruletypelist |
| 9 | RULEBYNAME | 依据名称 | String | 非必传 |
| 10 | DIAGNOSE | 审核提示 | String | 例如：异常字符 |
| 11 | COMPUTERVERIFYFLAG | 电脑审核标志：true勾，false或空未勾选 | Boolean | true |
| 12 | HANDVERIFYFLAG | 手动审核标志 |  | true |



组件参数

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 13 | TYPE | 条件类型 | String | VERIFY |
| 14 | DICTIONDESC | 条件描述 | String |  |
| 节点名称：comeffconjsonstr类型：array <object> | 节点名称：comeffconjsonstr类型：array <object> | 节点名称：comeffconjsonstr类型：array <object> | 节点名称：comeffconjsonstr类型：array <object> | 节点名称：comeffconjsonstr类型：array <object> |
| 1 | LOGIC | 逻辑运算符 | String | 逻辑运算符，如and |
| 2 | LOGICDESC | 逻辑运算符描叙 | String | 逻辑运算符描叙,如 与 |
| 3 | CONFIELDLIST | 分组表示 | String | 分组表示，如1-2 |
| 4 | FIELDDEFINEID | 字段定义ID | String | 字段定义ID 如：22 |
| 5 | FIELDEN | 字段英文描叙 | String | 字段英文描叙： Age |
| 6 | FIELDCN | 字段中文描叙 | String | 字段中文描叙：年龄 |
| 7 | RELATION | 运算符 | String | 运算符, 如< |
| 8 | RELATIONDESC | 运算符描叙 | String | 运算符描叙,如 包含 |
| 9 | VALUE | 值 | String | 值 ，如 AMD |
| 10 | VALUEDESC | 值描叙 | String | 值描叙，如：氮卓脒青霉素 |





接口出参【ResposeMessage ->根据type判断是否成功】：

Json返回数据格式：

{

  "data": true,

  "failtCode": "",

  "funName": "",

  "link": "",

  "message": "",

  "messageCode": "",

  "requstUrl": "",

  "serverTime": "2018-11-09 16:02:02",

  "type": "SUCCESS"

}

代码实现：



##### B04	获取搜索选项

请求URL：../ supersearch/request/searchcondition

接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | subsyscode | 子系统代码 | String | 子系统代码，如：LIMS |
| 2 | rpttype | 模块代码 | String | 报表类型，如：LIS_CONDITION |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：[搜索选项]  类型：array<object> | 节点名称：[搜索选项]  类型：array<object> | 节点名称：[搜索选项]  类型：array<object> | 节点名称：[搜索选项]  类型：array<object> | 节点名称：[搜索选项]  类型：array<object> |
| 1 | CODE | 字段简拼代码 | String | 字段简拼代码，如：bdyz |
| 2 | NAME | 字段名称 | String | 字典名称，如：本地医嘱 |
| 3 | MEMCODE1 | 字段描叙 | String | 字段英文描叙ID，如：OrderCode |
| 4 | MEMCODE2 | 字段描叙 | String | 字段描叙，可为空 |
| 5 | VALUETYPE | 展示类型 | String | 选项值展示类型：0:EDIT,1:SQL下拉 通过存储过程获取下拉内容，2多选 |
| 6 | FIELDDEFINEID | 字段定义ID | String | 指向报表字段定义 |
| 7 | CONVALUEPROVIDER | 条件值 | String | 根据条件值获取辅助字典数据 |







##### B05	获取运算符

请求URL：../ supersearch/request/searchoperator

接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | subsyscode | 子系统代码 | String | 子系统代码，如：LIMS |
| 2 | rpttype | 报表类型 | String | 报表类型，如：LIS_CONDITION |
| 3 | fielden | 搜索选项字段 | String | 搜索选项字段 如sex 对应B04 MEMCODE2值 |





接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | operator | 消息类型 | String | 运算符，如 “<” |
| 2 | operatordesc | 消息代码 | String | 运算符描叙，如 “包含” |







##### B06	获取审核规则

请求URL：../ institemcondition/request/ getitemconditioninfo

接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | RULEID | 审核规则id | String |  |





接口出参【ResposeMessage.data->】：

##### B07	删除审核规则

请求URL：../ institemcondition//request/ deleteitemconditioninfo

接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | RULEID | 审核规则id | String |  |





接口出参【ResposeMessage.data->】：





##### B08	更新审核规则

接口说明：获取仪器项目审核规则

请求URL：../ institemcondition/request/ updateitemconditioninfo

代码文件：

示例URL： 

原型参考：

接口入参：

审核条件部分

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 3 | RULEID | 规则id | string |  |
| 4 | RULENAME | 规则名称 | string | 例如：结果包含异常字符 |
| 5 | RULEVALUE | 异常字符 | String | #，%，* |
| 6 | GRADE | 规则等级 | string | 2 |
| 7 | CODE | 审核规则类型 | String | 1-异常值验证，2-历史差值，3-结果区间及标识，4-仪器报警，数据来源institemcondition/request/getinitdata的instconditionlist的INSTRUMENT_CONDITION_RULES（已配置规则类型） |
| 8 | RULEBYCODE | 依据代码 | String | CONTAIN-包含，EQUAL-等于，NOTCONTAIN-不包含，数据来源于institemcondition/request/getinitdata的conruletypelist |
| 9 | RULEBYNAME | 依据名称 | String | 非必传 |
| 10 | DIAGNOSE | 审核提示 | String | 例如：异常字符 |
| 11 | COMPUTERVERIFYFLAG | 电脑审核标志：true勾，false或空未勾选 | Boolean | true |
| 12 | HANDVERIFYFLAG | 手动审核标志 |  | true |



组件参数

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 13 | TYPE | 条件类型 | String | VERIFY |
| 14 | DICTIONDESC | 条件描述 | String |  |
| 节点名称：comeffconjsonstr类型：array <object> | 节点名称：comeffconjsonstr类型：array <object> | 节点名称：comeffconjsonstr类型：array <object> | 节点名称：comeffconjsonstr类型：array <object> | 节点名称：comeffconjsonstr类型：array <object> |
| 1 | LOGIC | 逻辑运算符 | String | 逻辑运算符，如and |
| 2 | LOGICDESC | 逻辑运算符描叙 | String | 逻辑运算符描叙,如 与 |
| 3 | CONFIELDLIST | 分组表示 | String | 分组表示，如1-2 |
| 4 | FIELDDEFINEID | 字段定义ID | String | 字段定义ID 如：22 |
| 5 | FIELDEN | 字段英文描叙 | String | 字段英文描叙： Age |
| 6 | FIELDCN | 字段中文描叙 | String | 字段中文描叙：年龄 |
| 7 | RELATION | 运算符 | String | 运算符, 如< |
| 8 | RELATIONDESC | 运算符描叙 | String | 运算符描叙,如 包含 |
| 9 | VALUE | 值 | String | 值 ，如 AMD |
| 10 | VALUEDESC | 值描叙 | String | 值描叙，如：氮卓脒青霉素 |





接口出参【ResposeMessage ->根据type判断是否成功】：



##### B09	逻辑验证 项目定义

一、获取仪器列表（410.B01 获取分类仪器列表）

请求URL：获取仪器类列表:institemcondition/request/getinstlist

参数：hospitalcode 

原型参考：







二、获取仪器对应项目列表（411.B01 获取仪器项目列表）

请求URL：获取仪器属性列表institemcondition/request/getitemlist

参数：hospitalcode,instid

原型参考：





##### B10	逻辑验证 添加

请求URL：../ institemcondition/request/ addlogiccondition 

原型参考：



接口入参：

审核条件部分

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | INSTID | 仪器ID | string | 不允许为空，如：5 |
| 2 | INSTCODE | 仪器代码 | string | KX21 |
| 3 | ITEMCODE | 项目代码 | string | WBC |
| 4 | RULENAME | 规则名称 | string | 例如：结果包含异常字符 |
| 5 | RULEVALUE | 异常字符 | String | #，%，* |
| 6 | GRADE | 规则等级 | string | 2 |
| 7 | CODE | 审核规则类型 | String | 1-异常值验证，2-历史差值，3-结果区间及标识，4-仪器报警，数据来源institemcondition/request/getinitdata的instconditionlist的INSTRUMENT_CONDITION_RULES（已配置规则类型） |
| 8 | STOPFLAG | 停用标志 | String | false-正常，true-停用 |
| 10 | DIAGNOSE | 审核提示 | String | 例如：异常字符 |
| 11 | COMPUTERVERIFYFLAG | 电脑审核标志：true勾，false或空未勾选 | Boolean | true |
| 12 | HANDVERIFYFLAG | 手动审核标志 |  | true |
| 13 | TYPE | 条件类型 | String | VERIFY |
| 14 | DICTIONDESC | 条件描述 | String |  |



项目定义参数

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：comeffconjsonstr类型：array <object> | 节点名称：comeffconjsonstr类型：array <object> | 节点名称：comeffconjsonstr类型：array <object> | 节点名称：comeffconjsonstr类型：array <object> | 节点名称：comeffconjsonstr类型：array <object> |
| 1 | INSTID | 仪器ID | string | 不允许为空，如：5 |
| 2 | INSTCODE | 仪器代码 | string | KX21 |
| 3 | ITEMCODE | 项目代码 | string | WBC |
| 4 | TIMERANGE | 时效 | string | 7 |
| 5 | TIMEUNIT | 时效单位 | String | 天 |





接口出参【ResposeMessage ->根据type判断是否成功】：



##### B11	逻辑验证 获取详情

接口说明：获取仪器项目审核规则

请求URL：../ institemcondition/request/ getlogicconditioninfo

代码文件：

示例URL： 

原型参考：



接口入参： 

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | RULEID | 规则id | string | 不允许为空 |



接口出参【ResposeMessage ->】：

Json返回数据格式：

代码实现：



##### B12	逻辑验证 删除

接口说明：获取仪器项目审核规则

请求URL：../ institemcondition/request/ deletelogicconditioninfo

代码文件：

示例URL： 

原型参考：



接口入参： 

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | RULEID | 规则id | string | 不允许为空 |



接口出参【ResposeMessage ->】：

Json返回数据格式：

代码实现：

##### B13	逻辑验证 修改

接口说明：获取仪器项目审核规则

请求URL：../ institemcondition/request/ updatelogicconditioninfo

代码文件：

示例URL： 

原型参考：



接口入参： 

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | RULEID | 规则Id | string | 不允许为空 |
| 4 | RULENAME | 规则名称 | string | 例如：结果包含异常字符 |
| 5 | RULEVALUE | 异常字符 | String | #，%，* |
| 6 | GRADE | 规则等级 | string | 2 |
| 7 | CODE | 审核规则类型 | String | 1-异常值验证，2-历史差值，3-结果区间及标识，4-仪器报警，数据来源institemcondition/request/getinitdata的instconditionlist的INSTRUMENT_CONDITION_RULES（已配置规则类型） |
| 8 | STOPFLAG | 停用标志 | String | false-正常，true-停用 |
| 10 | DIAGNOSE | 审核提示 | String | 例如：异常字符 |
| 11 | COMPUTERVERIFYFLAG | 电脑审核标志：true勾，false或空未勾选 | Boolean | true |
| 12 | HANDVERIFYFLAG | 手动审核标志 |  | true |



项目定义参数

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：comeffconjsonstr类型：array <object> | 节点名称：comeffconjsonstr类型：array <object> | 节点名称：comeffconjsonstr类型：array <object> | 节点名称：comeffconjsonstr类型：array <object> | 节点名称：comeffconjsonstr类型：array <object> |
| 1 | INSTID | 仪器ID | string | 不允许为空，如：5 |
| 2 | INSTCODE | 仪器代码 | string | KX21 |
| 3 | ITEMCODE | 项目代码 | string | WBC |
| 4 | TIMERANGE | 时效 | string | 7 |
| 5 | TIMEUNIT | 时效单位 | String | 天 |



接口出参【ResposeMessage ->】：

Json返回数据格式：

代码实现：

##### B14	逻辑验证 获取项目定义

接口说明：获取仪器项目审核规则

请求URL：../ institemcondition/request/ getlogicitemdefinelist

代码文件：

示例URL： 

原型参考：



接口入参： 

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | RULEID | 规则Id | string | 不允许为空 |



接口出参【ResposeMessage ->】：

Json返回数据格式：

代码实现：



#### 收费项目

##### V 模块接口视图

| NO. | 接口编号 | 接口名称 | 说明 |
| --- | --- | --- | --- |
| 1 | A01 | 获取收费筛选列表信息
../hisitem/request/gethisitemstatuslist | 获取收费筛选列表信息 |
| 2 | A02 | A02 *添加收费项目初始化
../hisitem/request/geinitdata | 添加收费项目界面数据初始化 |
| 3 | B01 | 获取收费列表的项目信息
../hisitem/request/gethisitemlist | 单击收费项目列表，获取对应列表的项目信息 |
| 4 | B02 | 搜索收费项目
../hisitem/request/searchhisitemlist | 收费项目搜索 |
| 5 | B03 | 添加收费项目
../hisitem/request/addhisitem | 添加收费项目 |
| 6 | B04 | 修改收费项目
../hisitem/request/alterhisitem | 修改收费项目 |
| 7 | B05 | his接口导入收费项目
../hisitem/request/getitemfromhis | 修改收费项目 |
| 8 | B08 | 收费项目条码项目、急诊、停用状态设置
../hisitem/request/sethisitemstatus | 收费项目条码项目、急诊、停用状态设置 |



##### A 对外公布方法

A01 获取收费筛选列表信息

接口说明：获取收费筛选列表信息

请求URL：../hisitem/request/gethisitemstatuslist

代码文件：winning.lis.hisitem.service.HisItemService

示例URL： 

http://192.168.10.73:15011/lis/hisitem/request/gethisitemstatuslist?hospitalcode=9999&subsyscode=LIMS

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码 |
| 2 | subsyscode | 子系统代码 | string | 传入子系统代码，允许为空
传入为空时默认为LIMS |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | NODENO | 节点编码 | string | 如：HISITEM_STATUS |
| 2 | NODENAME | 节点名称 | string | 如：状态 |
| 3 | CHILDNODES | 子节点名称 | array<object> |  |
|  |  |  |  |  |
| 1 | CODE | 字典代码 | string | 如：1 |
| 2 | NAME | 字典名称 | string | 如：生化 |
| 3 | DICID | 标准编码 | string | 如：SH |
| 4 | MEMCODE1 | 输入码一 | string | 如：SH |
| 5 | MEMCODE2 | 输入码二 | string | 如：3 |
| 6 | ORDERNO | 排序 | int | 如：1 |
|  |  |  |  |  |
| 1 | NODENO | 节点编码 | string | 如：HISITEM_EXAMINE |
| 2 | NODENAME | 节点名称 | string | 如：检验分类 |
| 3 | CHILDNODES | 子节点名称 | array<object> |  |
|  |  |  |  |  |
| 1 | CODE | 字典代码 | string | 如：1 |
| 2 | NAME | 字典名称 | string | 如：生化 |
| 3 | DICID | 标准编码 | string | 如：SH |
| 4 | MEMCODE1 | 输入码一 | string | 如：SH |
| 5 | MEMCODE2 | 输入码二 | string | 如：3 |
| 6 | ORDERNO | 排序 | int | 如：1 |
|  |  |  |  |  |
| 1 | NODEID | 节点编码 | string | 如：HISITEM_INSTORDER |
| 2 | NODENAME | 节点名称 | string | 如：仪器分类 |
| 3 | CHILDNODES | 子节点名称 | array<object> |  |
|  |  |  |  |  |
| 1 | INSTID | 仪器ID | string | 如：3，对应INSTID |
| 2 | INSTCODE | 仪器编码 | string | 如：HX-21，INSTCODE |
| 3 | INSTNAME | 仪器名称 | string | 如：HX-21，INSTNAME |
| 4 | EXECGROUPCODE | 执行小组代码 | string | 如：SH |
| 5 | EXECGROUPNAME | 执行小组名称 | string | 如：3 |
|  |  |  |  |  |
| 1 | NODEID | 节点编码 | string | 如：HISITEM_BARORDER |
| 2 | NODENAME | 节点名称 | string | 如：条码分组 |
| 3 | CHILDNODES | 子节点名称 | array<object> |  |
|  |  |  |  |  |
| 1 | CODE | 条形码分组代码 | string | 如：4，BAREXAMCODE |
| 2 | NAME | 条形码分组名称 | string | 如：体液（尿），BAREXAMNAME |
| 3 | DICID | 条码分组编码 | string | 如：2，BAREXAMCODE |
| 4 | MEMCODE1 | 输入码一 | string | 如：SH |
| 5 | MEMCODE2 | 输入码二 | string | 如：3 |
| 6 | ORDERNO | 排序 | int | 如：1，BARPRIORITY |
|  |  |  |  |  |
| 1 | NODEID | 节点编码 | string | 如：HISITEM_RETURNRULE |
| 2 | NODENAME | 节点名称 | string | 如：回执单规则 |
| 3 | CHILDNODES | 子节点名称 | array<object> |  |
|  |  |  |  |  |
| 1 | CODE | 规则ID | string | 如：23，RULENO |
| 2 | NAME | 规则名称 | string | 如：23，RULENAME |
| 3 | DICID | 条码分组编码 | string | 如：2，RULENO |
| 4 | MEMCODE1 | 输入码一 | string | 如：333 |
| 5 | MEMCODE2 | 输入码二 | string | 如：2 |
| 6 | ORDERNO | 排序 | string | 如：1 |



Json返回数据格式:

[

    {

      "NODENO": "HISITEM_STATUS",

      "NODENAME": "状态",

      "CHILDNODES": [

        {

          "CODE": "1",

          "NAME": "全部项目",

          "DICID": "全部项目",

          "DICTYPE": "状态",

          "EXTERNCODE": "1",

          "MEMCODE1": "qbxm",

          "MEMCODE2": null,

          "SUBSYSCODE": "LIMS",

          "ORDERNO": 1,

          "RESERVEFIELD1": null

        }

      ]

    },

    {

      "NODENO": "HISITEM_EXAMINE",

      "NODENAME": "检验分类",

      "CHILDNODES": [

        {

          "CODE": "1",

          "NAME": "生化",

          "DICID": "1",

          "DICTYPE": "检验分类",

          "EXTERNCODE": "1",

          "MEMCODE1": "1",

          "MEMCODE2": "TGDWXN",

          "SUBSYSCODE": "LIMS",

          "ORDERNO": null,

          "RESERVEFIELD1": null

        }

      ]

    },

    {

      "NODENO": "HISITEM_INSTORDER",

      "NODENAME": "仪器分类",

      "CHILDNODES": [

        {

          "NODENAME": "未分组",

          "CHILDNODE": [

            {

              "INSTID": 12,

              "INSTCODE": "AIA-360",

              "INSTNAME": "AIA-360",

              "EXECGROUPCODE": null,

              "EXECGROUPNAME": null

            }

          ]

        },

        {

          "NODENAME": "生化室",

          "CHILDNODE": [

            {

              "INSTID": 11,

              "INSTCODE": "AVE762B",

              "INSTNAME": "AVE762B",

              "EXECGROUPCODE": "1",

              "EXECGROUPNAME": "生化室"

            }

          ]

        }

      ]

    },

    {

      "NODENO": "HISITEM_BARORDER",

      "NODENAME": "条码分组",

      "CHILDNODES": [

        {

          "CODE": 1,

          "NAME": "生化",

          "DICID": 1,

          "MEMCODE1": "1",

          "MEMCODE2": "2",

          "ORDERNO": "1"

        }

      ]

    },

    {

      "NODENO": "HISITEM_RETURNRULE",

      "NODENAME": "回执单规则",

      "CHILDNODES": [

        {

          "CODE": 1,

          "NAME": "测试1",

          "DICID": 1,

          "MEMCODE1": "测试1",

          "MEMCODE2": "测试1",

          "ORDERNO": "1"

        }

      ]

    }

  ]

代码实现：

从SLAVE_P\SLAVE\LIS_INSTRUMENT\LIS_BAREXAMCLASS\

LIS_RETURNRULE\ LIS_HISITEM\LIS_INSTORDER\LIS_BARORDER\LIS_INSTORDER\

LIS_RETURNRULEORDER表中读取数据

表结构：SLAVE_P\SLAVE\LIS_INSTRUMENT\LIS_BAREXAMCLASS\

LIS_RETURNRULE\ LIS_HISITEM\LIS_INSTORDER\LIS_BARORDER\LIS_INSTORDER\

LIS_RETURNRULEORDER

A02 添加收费项目初始化

接口说明：添加收费项目界面数据初始化

请求URL：../hisitem/request/geinitdata

代码文件：winning.lis.hisitem.service.HisItemService

示例URL： 

http://192.168.10.73:15011/lis/hisitem/request/getinitdata?hospitalcode=9999&subsyscode=LIMS

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码 |
| 2 | subsyscode | 子系统代码 | string | 传入子系统代码，允许为空
传入为空时默认为LIMS |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：itemtype 项目类别 类型： array<object> | 节点名称：itemtype 项目类别 类型： array<object> | 节点名称：itemtype 项目类别 类型： array<object> | 节点名称：itemtype 项目类别 类型： array<object> | 节点名称：itemtype 项目类别 类型： array<object> |
| 1 | CODE | 字典代码 | string | 如：1 |
| 2 | NAME | 字典名称 | string | 如：生化 |
| 3 | DICID | 标准编码 | string | 如：SH |
| 4 | DICTYPE | 字典类型 | string | 如：SH |
| 5 | EXTERNCODE | 外部代码 | string | 如：SH |
| 6 | MEMCODE1 | 输入码一 | string | 如：SH |
| 7 | MEMCODE2 | 输入码二 | string | 如：3 |
| 8 | ORDERNO | 排序 | int | 如：1 |
| 9 | SUBSYSCODE | 子系统代码 | string | 如：3 |
| 10 | RESERVEFIELD1 | 扩展字段 | string | 如：3 |
| 节点名称：examine 检验分类 类型： array<object> | 节点名称：examine 检验分类 类型： array<object> | 节点名称：examine 检验分类 类型： array<object> | 节点名称：examine 检验分类 类型： array<object> | 节点名称：examine 检验分类 类型： array<object> |
| 1 | CODE | 字典代码 | string | 如：1 |
| 2 | NAME | 字典名称 | string | 如：生化 |
| 3 | DICID | 标准编码 | string | 如：SH |
| 4 | DICTYPE | 字典类型 | string | 如：SH |
| 5 | EXTERNCODE | 外部代码 | string | 如：SH |
| 6 | MEMCODE1 | 输入码一 | string | 如：SH |
| 7 | MEMCODE2 | 输入码二 | string | 如：3 |
| 8 | ORDERNO | 排序 | int | 如：1 |
| 9 | SUBSYSCODE | 子系统代码 | string | 如：3 |
| 10 | RESERVEFIELD1 | 扩展字段 | string | 如：3 |
| 节点名称：samplelist默认标本 类型： array<object> | 节点名称：samplelist默认标本 类型： array<object> | 节点名称：samplelist默认标本 类型： array<object> | 节点名称：samplelist默认标本 类型： array<object> | 节点名称：samplelist默认标本 类型： array<object> |
| 1 | CODE | 字典代码 | string | 如：1 |
| 2 | NAME | 字典名称 | string | 如：生化 |
| 3 | DICID | 标准编码 | string | 如：SH |
| 4 | DICTYPE | 字典类型 | string | 如：SH |
| 5 | EXTERNCODE | 外部代码 | string | 如：SH |
| 6 | MEMCODE1 | 输入码一 | string | 如：SH |
| 7 | MEMCODE2 | 输入码二 | string | 如：3 |
| 8 | ORDERNO | 排序 | int | 如：1 |
| 9 | SUBSYSCODE | 子系统代码 | string | 如：3 |
| 10 | RESERVEFIELD1 | 扩展字段 | string | 如：3 |



Json返回数据格式:

{

		"itemtype":[

			{

				"CODE":"0",

				"NAME":"临床项目",

				"DICID":"",

				"DICTYPE":"项目类别",

				"EXTERNCODE":"",

				"MEMCODE1":"",

				"MEMCODE2":"",

				"SUBSYSCODE":"LIMS",

				"ORDERNO":1,

				"RESERVEFIELD1":null

			}

		],

		"examine":[

			{

				"CODE":"1",

				"NAME":"生化",

				"DICID":"1",

				"DICTYPE":"检验分类",

				"EXTERNCODE":"1",

				"MEMCODE1":"1",

				"MEMCODE2":"TGDWXN",

				"SUBSYSCODE":"LIMS",

				"ORDERNO":null,

				"RESERVEFIELD1":null

			}

		],

		"samplelist":[

			{

				"CODE":"1",

				"NAME":"血",

				"DICID":"2",

				"DICTYPE":"样本类型",

				"EXTERNCODE":null,

				"MEMCODE1":"1",

				"MEMCODE2":"X",

				"SUBSYSCODE":"LIMS",

				"ORDERNO":0,

				"RESERVEFIELD1":null

			}

		]

	}

代码实现：



表结构：SLAVE_P\SLAVE\LIS_ 

##### B 业务类

B01	获取收费列表的项目信息

接口说明：单击收费项目列表，获取对应列表的项目信息

请求URL：../hisitem/request/gethisitemlist

代码文件：winning.lis.hisitem.service.HisItemService

示例URL： 

http://192.168.10.73:15011/lis/hisitem/request/gethisitemlist?hospitalcode=9999&nodeno=HISITEM_STATUS&selectvalue=2

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | nodeno | 选项分类的nodeno | string | 不允许为空，如：HISITEM_RETURNRULE |
| 3 | selectvalue | 选择参数 | string | 不允许为空，在一级菜单“状态”下，1-全部项目、2-在用项目，3-已停用项目、4-未配置条码分组、5-未配置检验仪器、6-未配置回执单规则、7-未配置检验项目，其他菜单下为当前选中项的ID |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | LISORDERCODE | LIS标准收费代码 | string | 如：0509 |
| 3 | HISORDERCODE | HIS收费代码 | string | 如：509 |
| 4 | HISORDERNAME | HIS收费名称 | string | 如：入院治疗费 |
| 5 | BARCODELABELNAME | 收费简称 | string | 如：生化 |
| 6 | ITEMTYPE | HIS项目类别 | string | 0 临床项目 1 收费项目 9组合项目 |
| 7 | MEMCODE1 | 输入码一 | string | 如：SH |
| 8 | MEMCODE2 | 输入码二 | string | 如：3 |
| 9 | PRICE | 单价 | int | 如：1 |
| 10 | UNIT | 单位 | string | 如： |
| 11 | EXAMCODE | 检验分类代码 | string | 如：2 |
| 12 | SAMPLECODE | 默认标本种类 | string | 如：血 |
| 13 | SAMPLEDESC | 标本说明 | string | 如： |
| 14 | MJZBZ | 急诊标志 | string | 如：1 |
| 15 | BARCODEFLAG | 条码标志 | string | 如：0 |
| 16 | STOPFLAG | 停用标志 | string | 如：0 |
| 17 | MATERIALFLAG | 材料费标志 | string | 如：0 |
| 18 | MATERIALTYPE | 材料收费方式 | string | 如： |
| 19 | MATBACKFLAG | 材料费只收不退 | string | 如： |
| 20 | MATWARDORREG | 收取材料费的病人类别 | string | 如： |
| 20 | MATSAMPLE | 收取材料费的标本种类 | string | 如： |
| 21 | MATAGELOW | MATAGEUPP | string | 如： |
| 22 | MATAGEUNIT | 收取材料费年龄单位 | string | 如： |
| 23 | SAMPLECOMMENT | 采集说明 | string | 如： |
| 24 | CXREMARK | 采集注意事项 | string | 如： |
| 25 | APPLYLIMITDAYS | 申请时间预警天数 | string | 如： |
| 26 | BAREXAMCODE | 条码分组 | string | 如： |
| 27 | BAREXAMNAME | 条码分组名称 | string | 如： |
| 28 | INSTID | 仪器ID | string | 如： |
| 29 | INSTNAME | 仪器名称 | string | 如： |
| 30 | TATNAME | TAT规则字典 | string | 如： |
| 31 | TATENO | TAT规则字典代码 | string | 如： |
| 32 | MATORDERNAME | 材料费 | string | 如： |
| 33 | MATORDERCODE | 材料费字典代码 | string | 如： |
| 34 | SAMPLENAME2 | 标本类型 | string | 如： |
| 35 | SAMPLECODE2 | 标本类型代码 | string | 如： |
| 36 | SOURCELISORDERCODE | 外送的项目代码 | string | 如： |
| 37 | SOURCEHISITEMNAME | 外送项目名称 | string | 如： |
| 38 | RULENO | 回执单分组 | string | 如： |
| 39 | RULENAME | 回执单名称 | string | 如： |
| 40 | EXHOSPFLAG | 外送标志 | string | 如： |
| 41 | EXHOSPITALCODE | 外送医疗机构代码 | string | 如：8888 |



JSON返回示例：

"hisitemlist": [

      {

        "HOSPITALCODE": "9999",

        "LISORDERCODE": "062",

        "HISORDERCODE": "62",

        "HISORDERNAME": "阴道分泌物检查",

        "BARCODELABELNAME": "",

        "ITEMTYPE": "0",

        "ITEMTYPENAME": "临床项目",

        "MEMCODE1": "ydfmwjc",

        "MEMCODE2": "buwitss",

        "PRICE": 5,

        "UNIT": "",

        "EXAMCODE": "11",

        "EXAMNAME": "体液",

        "SAMPLECODE": null,

        "SAMPLEDESC": null,

        "MJZBZ": "0",

        "BARCODEFLAG": "1",

        "STOPFLAG": "0",

        "MATERIALFLAG": " ",

        "MATERIALTYPE": " ",

        "EXHOSPFLAG": "0",

        "EXHOSPITALCODE": null

      }

    ]

代码实现：

从SLAVE\LIS_INSTRUMENT\LIS_BAREXAMCLASS\

LIS_RETURNRULE\ LIS_HISITEM\LIS_INSTORDER\LIS_BARORDER\LIS_INSTORDER\

LIS_RETURNRULEORDER表中读取数据

表结构：SLAVE_P\SLAVE\LIS_INSTRUMENT\LIS_BAREXAMCLASS\

LIS_RETURNRULE\ LIS_HISITEM\LIS_INSTORDER\LIS_BARORDER\LIS_INSTORDER\

LIS_RETURNRULEORDER

B02	搜索收费项目

接口说明：收费项目搜索

请求URL：../hisitem/request/searchhisitem

代码文件：winning.lis.hisitem.service.HisItemService

示例URL： 

http://192.168.10.73:15011/lis/hisitem/request/searchhisitem?hospitalcode=9999&searchtext=23

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | searchtext | 搜索内容 | string | 允许为空，为空默认搜索全部 |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | LISORDERCODE | LIS标准收费代码 | string | 如：0509 |
| 3 | HISORDERCODE | HIS收费代码 | string | 如： 10 |
| 4 | HISORDERNAME | HIS收费名称 | string | 如：B型钠尿肽前体 |
| 5 | BARCODELABELNAME | 收费简称 | string | 如： |
| 6 | ITEMTYPE | HIS项目类别 | string | 0 临床项目 1 收费项目 9组合项目 |
| 7 | MEMCODE1 | 输入码一 | string | 如：SH |
| 8 | MEMCODE2 | 输入码二 | string | 如：3 |
| 9 | PRICE | 单价 | int | 如：1 |
| 10 | UNIT | 单位 | string | 如： |
| 11 | EXAMCODE | 检验分类代码 | string | 0-不可见 1-可见 |
| 12 | EXAMNAME | 检验分类名称 | string | 如：血流变 |
| 13 | SAMPLECODE | 默认标本种类 | string |  |
| 14 | SAMPLEDESC | 标本说明 | string | 如：常规字典 |
| 15 | ITEMTYPENAME | 项目类别名称 | string | 如：临床项目 |
| 16 | MJZBZ | 急诊标志 | string | 如：1 |
| 17 | BARCODEFLAG | 条码标志 | string | 如：0 |
| 18 | STOPFLAG | 停用标志 | string | 如：0 |
| 19 | MATERIALFLAG | 材料费标志 | string | 如：0 |
| 20 | MATERIALTYPE | 材料收费方式 | string | 如： |
| 21 | EXHOSPFLAG | 外送标志 | string | 如：0 |
| 22 | EXHOSPITALCODE | 外送医疗机构代码 | string | 如：8888 |



代码实现：

依据字段从LIS_HISITEM表中获取数据

表结构：LIS_HISITEM

B03	添加收费项目

接口说明：添加收费项目

请求URL：../hisitem/request/addhisitem

代码文件：winning.lis.hisitem.service.HisItemService

示例URL： 

原型参考：





（项目类别下拉框由前端写死，0-临床项目，1-收费项目，9-组合项目），（检验分类从A01中取）

接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | hisordercode | HIS收费代码 | string | 不允许为空，如：10，对应原型的收费代码 |
| 3 | hisordername | HIS收费名称 | string | 允许为空，如：B型钠尿肽前体，对应原型收费名称 |
| 4 | barcodelabelname | 收费简称 | string | 允许为空，如：谷氨酸，对应原型收费别名 |
| 5 | itemtype | 项目类别 | string | 不允许为空，0 临床项目 1 收费项目 9组合项目 |
| 6 | memcode1 | 拼音码 | string | 允许为空，如：SH |
| 7 | memcode2 | 五笔码 | string | 允许为空，如：3 |
| 8 | price | 价格 | string | 允许为空，如：1 |
| 9 | examcode | 检验分类代码 | string | 允许为空， |
| 10 | samplecode | 默认标本种类 | string | 允许为空，如：血 |
| 11 | mjzbz | 急诊标志 | string | 允许为空，如：1 |
| 12 | barcodeflag | 条码标志 | string | 允许为空，如：0 |
| 13 | stopflag | 停用标志 | string | 允许为空，如：0 |
| 14 | samplecomment | 采集说明 | string | 允许为空，如：123 |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：hisitem 汇总信息 类型： object | 节点名称：hisitem 汇总信息 类型： object | 节点名称：hisitem 汇总信息 类型： object | 节点名称：hisitem 汇总信息 类型： object | 节点名称：hisitem 汇总信息 类型： object |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | LISORDERCODE | LIS标准收费代码 | string | 如：LIMS |
| 3 | HISORDERCODE | HIS收费代码 | string | 如; 检验类别 |
| 4 | HISORDERNAME | HIS收费名称 | string | 如：1 |
| 5 | BARCODELABELNAME | 收费简称 | string | 如：生化 |
| 6 | ITEMTYPE | HIS项目类别 | string | 0 临床项目 1 收费项目 9组合项目 |
| 7 | MEMCODE1 | 输入码一 | string | 如：SH |
| 8 | MEMCODE2 | 输入码二 | string | 如：3 |
| 9 | PRICE | 单价 | int | 如：1 |
| 10 | UNIT | 单位 | string | 如： |
| 11 | EXAMCODE | 检验分类代码 | string | 0-不可见 1-可见 |
| 12 | EXAMNAME | 检验分类名称 | string | 如：血流变 |
| 13 | SAMPLECODE | 默认标本种类 | string |  |
| 14 | SAMPLEDESC | 标本说明 | string | 如：常规字典 |
| 15 | ITEMTYPENAME | 项目类别名称 | string | 如：临床项目 |
| 16 | MJZBZ | 急诊标志 | string | 如：1 |
| 17 | BARCODEFLAG | 条码标志 | string | 如：0 |
| 18 | STOPFLAG | 停用标志 | string | 如：0 |
| 19 | MATERIALFLAG | 材料费标志 | string | 如：0 |
| 20 | MATERIALTYPE | 材料收费方式 | string | 如： |
| 21 | EXHOSPFLAG | 外送标志 | string | 如：0 |
| 22 | EXHOSPITALCODE | 外送医疗机构代码 | string | 如：8888 |



代码实现：

从LIS_HISITEM表中查询添加LISORDERCODE的存在情况，存在就提示前端，不存在就直接插入数据

表结构：LIS_HISITEM

B04	修改收费项目

接口说明：修改收费项目

请求URL：../hisitem/request/alterhisitem

代码文件：winning.lis.hisitem.service.HisItemService

示例URL： 

http://192.168.10.73:15011/lis/hisitem/request/alterhisitem?hospitalcode=9999&hisordercode=123ab&hisordername=测&barcodelabelname=&itemtype=0&memcode1=cssj&memcode2=13411&price=13311

&examcode=3&samplecode=1&mjzbz=0&barcodeflag=0&stopflag=0&samplecomment=册数数据的&lisordercode=0123ab

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | lisordercode | LIS标准收费代码 | string | 不允许为空，如：010 |
| 3 | hisordercode | HIS收费代码 | string | 不允许为空，如：10，对应原型的收费代码 |
| 4 | hisordername | HIS收费名称 | string | 允许为空，如：B型钠尿肽前体，对应原型收费名称 |
| 5 | barcodelabelname | 收费简称 | string | 允许为空，如：谷氨酸，对应原型收费别名 |
| 6 | itemtype | 项目类别 | string | 不允许为空，0 临床项目 1 收费项目 9组合项目 |
| 7 | memcode1 | 拼音码 | string | 允许为空，如：SH |
| 8 | memcode2 | 五笔码 | string | 允许为空，如：3 |
| 9 | price | 价格 | string | 允许为空，如：1 |
| 10 | examcode | 检验分类代码 | string | 允许为空， |
| 11 | samplecode | 默认标本种类 | string | 允许为空，如：血 |
| 12 | mjzbz | 急诊标志 | string | 允许为空，如：1 |
| 13 | barcodeflag | 条码标志 | string | 允许为空，如：0 |
| 14 | stopflag | 停用标志 | string | 允许为空，如：0 |
| 15 | samplecomment | 采集说明 | string | 允许为空，如：123 |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- | --- |
| 节点名称：hisitem 汇总信息 类型： object | 节点名称：hisitem 汇总信息 类型： object | 节点名称：hisitem 汇总信息 类型： object | 节点名称：hisitem 汇总信息 类型： object | 节点名称：hisitem 汇总信息 类型： object | 节点名称：hisitem 汇总信息 类型： object |
| 1 | HOSPITALCODE | 医疗机构代码 | 医疗机构代码 | string | 如：9999 |
| 2 | LISORDERCODE | LIS标准收费代码 | LIS标准收费代码 | string | 如：01000 |
| 3 | HISORDERCODE | HIS收费代码 | HIS收费代码 | string | 如：1000 |
| 4 | HISORDERNAME | HIS收费名称 | HIS收费名称 | string | 如：总前列腺特异性抗原测定(体检) |
| 5 | BARCODELABELNAME | 收费简称 | 收费简称 | string | 如： |
| 6 | ITEMTYPE | HIS项目类别 | HIS项目类别 | string | 0 临床项目 1 收费项目 9组合项目 |
| 7 | MEMCODE1 | 输入码一 | 输入码一 | string | 如：SH |
| 8 | MEMCODE2 | 输入码二 | 输入码二 | string | 如：3 |
| 9 | PRICE | 单价 | 单价 | numerical | 如：15.00 |
| 10 | UNIT | 单位 | 单位 | string | 如： |
| 11 | EXAMCODE | 检验分类代码 | 检验分类代码 | string | 0-不可见 1-可见 |
| 12 | EXAMNAME | 检验分类名称 | 检验分类名称 | string | 如：血流变 |
| 13 | SAMPLECODE | 默认标本种类 | 默认标本种类 | string | 0-非系统内置 1-系统内置 前台允许查看,不允许编辑和删除 |
| 14 | SAMPLEDESC | 标本说明 | 标本说明 | string | 如：常规字典 |
| 15 | SEXRANGE | 性别适应范围 | 性别适应范围 | string | 1-男，2-女 空表示均适应 |
| 16 | MJZBZ | 急诊标志 | 急诊标志 | string | 如：1 |
| 17 | BARCODEFLAG | 条码标志 | 条码标志 | string | 如：0 |
| 18 | STOPFLAG | 停用标志 | 停用标志 | string | 如：0 |
| 19 | MATERIALFLAG | 材料费标志 | 材料费标志 | string | 如：0 |
| 20 | MATERIALTYPE | 材料收费方式 | 材料收费方式 | string | 如： |
| 21 | EXHOSPFLAG | 外送标志 | 外送标志 | string | 如：0 |
| 22 | EXHOSPITALCODE | 外送医疗机构代码 | 外送医疗机构代码 | string | 如：8888 |



代码实现：

从LIS_HISITEM表查询当前项目的信息，不存在提示前端，存在，则判断修改后的LISORDERCODE在表中的存在情况，然后获取数据

表结构：LIS_HISITEM

B05	his接口获取收费项目

接口说明：his接口获取收费项目

请求URL：../hisitem/request/getinitdatafromhis

代码文件：winning.lis.hisitem.service.HisItemService

示例URL： 

原型参考：





(从HIS接口导入，界面有一个动画效果)

接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | ORGCODE | 医疗机构代码 | string | 如：9999 |
| 2 | ITEMCODE | HIS收费代码 | string | 如：1000 |
| 3 | ITEMNAME | HIS收费名称 | string | 如：总前列腺特异性抗原测定(体检) |
| 4 | ITEMTYPE | HIS项目类别 | string | 0 临床项目 1 收费项目 9组合项目 |
| 5 | MEMCODE1 | 输入码一 | string | 如：SH |
| 6 | MEMCODE2 | 输入码二 | string | 如：3 |
| 7 | PRICE | 单价 | numerical | 如：15.00 |
| 8 | ITEMUNIT | 单位 | string | 如： |
| 9 | ITEMTYPENAME | HIS项目类别名称 | string | 如：临床项目 |



代码实现：

从his接口获取对应的收费项目信息

表结构：LIS_HISITEM

B06	获取收费项目信息

接口说明：获取收费项目信息

请求URL：../hisitem/request/gethisiteminfo

代码文件：winning.lis.hisitem.service.HisItemService

示例URL： 

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | lisordercode | LIS标准收费代码 | string | 不允许为空，如：010 |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：lisiteminfo项目信息 类型： object | 节点名称：lisiteminfo项目信息 类型： object | 节点名称：lisiteminfo项目信息 类型： object | 节点名称：lisiteminfo项目信息 类型： object | 节点名称：lisiteminfo项目信息 类型： object |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | LISORDERCODE | LIS收费代码 | string | 如：014534 |
| 3 | HISORDERCODE | HIS收费代码 | string | 如：234 |
| 4 | HISORDERNAME | HIS收费名称 | string | 如：血 |
| 5 | BARCODELABELNAME | 收费简称 | string | 如：9999 |
| 6 | ITEMTYPE | HIS项目类别 | string | 如：014534 |
| 7 | MEMCODE1 | 输入码一 | string | 如：234 |
| 8 | MEMCODE2 | 输入码二 | string | 如：血 |
| 9 | PRICE | 单价 | string | 如：232 |
| 10 | UNIT | 单位 | string | 如：元 |
| 11 | EXAMCODE | 检验分类代码 | string | 如：014534 |
| 12 | SAMPLECODE | 默认标本种类 | string | 如：2 |
| 13 | SAMPLEDESC | 标本说明 | string | 如：血 |
| 14 | OVERTIME | 采样时间超过送检时间 | string | 如2 |
| 15 | MJZBZ | 急诊标志 | string | 如：1 |
| 16 | BARCODEFLAG | 条码标志 | string | 如：1 |
| 17 | STOPFLAG | 停用标志 | string | 如：1 |
| 18 | MATERIALFLAG | 材料费标志 | string | 如：1 |
| 19 | MATERIALTYPE | 材料收费方式 | string | 如：只收不退 |
| 20 | MATBACKFLAG | 此材料费项目只收不退 | string | 如：9999 |
| 21 | MATWARDORREG | 收取材料费的病人类别 | string | 如：014534 |
| 22 | MATSAMPLE | 收取材料费的标本种类 | string | 如：234 |
| 23 | MATAGELOW | 收取材料费年龄下限 | string | 如：1 |
| 24 | MATAGEUPP | 收取材料费年龄上限 | string | 如：3 |
| 25 | MATAGEUNIT | 收取材料费年龄单位 | string | 如：次 |
| 26 | SAMPLECOMMENT | 采集说明 | string | 如：空腹 |
| 27 | CXREMARK | 采集注意事项 | string | 如：空腹 |
| 28 | EXHOSPFLAG | 外送标志 | string | 如：1 |
| 29 | EXHOSPITALCODE | 外送医疗机构代码 | string | 如：8888 |
| 30 | APPLYLIMITDAYS | 申请时间预警天数 | string | 如：2 |
| 31 | EXAMNAME | 检验分类名称 | string | 如：生化 |
| 31 | SAMPLENAME | 默认样本名称 | string | 如：血 |
| 节点名称：barorderinfo条码分组 类型： object | 节点名称：barorderinfo条码分组 类型： object | 节点名称：barorderinfo条码分组 类型： object | 节点名称：barorderinfo条码分组 类型： object | 节点名称：barorderinfo条码分组 类型： object |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | BAREXAMCODE | 规则ID | string | 如：014534 |
| 3 | LISORDERCODE | lis代码 | string | 如：234 |
| 4 | HISORDERCODE | his代码 | string | 如：29 |
| 5 | HISORDERNAME | his名称 | string | 如：妇科彩超(一个部位) |
| 6 | BARSINGLE | 单独绑定标志 | string | 如：0 |
| 7 | BAREXAMNAME | 规则名称 | string | 如：血液 |
| 节点名称：returnruleorderinfo 回执单 类型： object | 节点名称：returnruleorderinfo 回执单 类型： object | 节点名称：returnruleorderinfo 回执单 类型： object | 节点名称：returnruleorderinfo 回执单 类型： object | 节点名称：returnruleorderinfo 回执单 类型： object |
| 1 | LISORDERCODE | lis代码 | string | 如：04545 |
| 2 | RULENO | 规则ID | string | 如：23 |
| 3 | HOSPITALCODE | 医疗机构代码 | string | 如：999 |
| 4 | RULENAME | 规则名称 | string | 如：血 |
| 5 | MEMCODE1 | 输入码一 | string | 如;34 |
| 6 | MEMCODE2 | 输入码一 | string | 如：44 |
| 7 | STOPFLAG | 停用标志 | string | 如：1 |
| 节点名称：reptimeorderinfo  TAT规则 类型： array<object> | 节点名称：reptimeorderinfo  TAT规则 类型： array<object> | 节点名称：reptimeorderinfo  TAT规则 类型： array<object> | 节点名称：reptimeorderinfo  TAT规则 类型： array<object> | 节点名称：reptimeorderinfo  TAT规则 类型： array<object> |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | RULENO | TAT规则ID | string | 如：9 |
| 3 | LISORDERCODE | lis代码 | string | 如：01243 |
| 4 | RULENAME | TAT规则名称 | string | 如：测试 |
| 5 | SOURCENODE | 开始目标节点 | string | 如：CY |
| 6 | TARGETNODE | 开始目标结束节点 | string | 如：BD |
| 7 | MEMCODE1 | 输入码一 | string | 如：CY |
| 8 | MEMCODE2 | 输入码一 | string | 如：123 |
| 9 | STOPFLAG | 停用标志 | string | 如：1 |
| 节点名称：ordersampleinfo  标本种类 类型： array<object> | 节点名称：ordersampleinfo  标本种类 类型： array<object> | 节点名称：ordersampleinfo  标本种类 类型： array<object> | 节点名称：ordersampleinfo  标本种类 类型： array<object> | 节点名称：ordersampleinfo  标本种类 类型： array<object> |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | LISORDERCODE | lis代码 | string | 如：014534 |
| 3 | CODE | 样本类型代码 | string | 如：234 |
| 4 | NAME | 样本类型名称 | string | 如：血 |
| 5 | DEFAULTFLAG | 默认标本标志 | string | 0-非默认标本 1-默认标本 |
| 节点名称：instorderinfo   检验仪器 类型： array<object> | 节点名称：instorderinfo   检验仪器 类型： array<object> | 节点名称：instorderinfo   检验仪器 类型： array<object> | 节点名称：instorderinfo   检验仪器 类型： array<object> | 节点名称：instorderinfo   检验仪器 类型： array<object> |
| 1 | LISORDERCODE | lis代码 | string | 如：014534 |
| 2 | INSTID | 仪器ID | string | 如： 34 |
| 3 | INSTCODE | 仪器编码 | string | 如：04534 |
| 4 | INSTNAME | 仪器名称 | string | 如：测试 |
| 节点名称：ordertoiteminfo检验指标 类型： array<object> | 节点名称：ordertoiteminfo检验指标 类型： array<object> | 节点名称：ordertoiteminfo检验指标 类型： array<object> | 节点名称：ordertoiteminfo检验指标 类型： array<object> | 节点名称：ordertoiteminfo检验指标 类型： array<object> |
|  | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
|  | LISORDERCODE | lis代码 | string | 如：014534 |
|  | ITEMCODE | 检验项目代码 | string | 如：124534 |
|  | ITEMNAME | 项目名称 | string | 如：中性粒细胞绝对值 |
|  | ITEMNUM | 标准编码 | string | 如：NEU# |
|  | ENGSHORTNAME | 英文缩写 | string | 如：NEU# |
|  | ENGNAME | 英文名称 | string | 如：NEU# |
|  | LOINCCODE | LOINC编码 | string | 如：NEU# |
|  | CHARGEORDER | 收费顺序 | string | 如：1 |
|  | CHECKITEM | 校验项目 | string | 如：1 |
|  | CHECKITEMNAME | 校验项目名称 | string | 如：淋巴细胞绝对值1 |
|  | CHECKITEMNUM | 校验项目标准编码 | string | 如：LYM# |
|  | 节点名称 ：INSTLISTMUST   校验仪器               array<string> | 节点名称 ：INSTLISTMUST   校验仪器               array<string> | 节点名称 ：INSTLISTMUST   校验仪器               array<string> | 节点名称 ：INSTLISTMUST   校验仪器               array<string> |
|  | INSTID | 仪器ID | string | 如：5 |
|  | INSTCODE | 仪器编码 | string | 如：KX21 |
|  | INSTNAME | 仪器名称 | string | 如：KX21 |
|  | 节点名称 ：NOTINSERTRESULT  不需要入库仪器               array<string> | 节点名称 ：NOTINSERTRESULT  不需要入库仪器               array<string> | 节点名称 ：NOTINSERTRESULT  不需要入库仪器               array<string> | 节点名称 ：NOTINSERTRESULT  不需要入库仪器               array<string> |
|  | INSTID | 仪器ID | string | 如：5 |
|  | INSTCODE | 仪器编码 | string | 如：KX21 |
|  | INSTNAME | 仪器名称 | string | 如：KX21 |
|  | CHECKFLAG | 校验标志 | string | 0-校验，1-校验 |
| 节点名称：materialtoorderinfo 材料费指标 类型： array<object> | 节点名称：materialtoorderinfo 材料费指标 类型： array<object> | 节点名称：materialtoorderinfo 材料费指标 类型： array<object> | 节点名称：materialtoorderinfo 材料费指标 类型： array<object> | 节点名称：materialtoorderinfo 材料费指标 类型： array<object> |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | MATORDERCODE | LIS材料费代码 | string | 如：0123323 |
| 3 | MATHISORDERCODE | HIS材料费代码 | string | 如：1001 |
| 4 | MATHISORDERNAME | 料费名称 | string | 如：泌尿系彩超(一个部位) |
| 5 | LISORDERCODE | LIS收费项目代码 | string | 如：028 |
| 6 | HISORDERCODE | HIS收费项目代码 | string | 如：28 |
| 7 | HISORDERNAME | 收费项目名称 | string | 如：泌尿系彩超(一个部位) |
| 节点名称：exhospitalorderinfo 外送机构 类型： array<object> | 节点名称：exhospitalorderinfo 外送机构 类型： array<object> | 节点名称：exhospitalorderinfo 外送机构 类型： array<object> | 节点名称：exhospitalorderinfo 外送机构 类型： array<object> | 节点名称：exhospitalorderinfo 外送机构 类型： array<object> |
| 1 | SOURCEHOSPITALCODE | 送检医疗机构代码 | string | 如：9999 |
| 2 | SOURCELISORDERCODE | 送检机构收费代码 | string | 如：034222 |
| 3 | TARGETHOSPITALCODE | 检测医疗机构代码 | string | 如：8888 |
| 4 | TARGETHOSPITALCODENAME | 检测医疗机构名称 | string | 如：合肥附属医院 |
| 5 | TARGETLISORDERCODE | 检测机构收费代码 | string | 如：0642344 |
| 6 | TARGETHISORDERCODE | 检测机构收费his代码 | string | 如：55555 |
| 7 | TARGETHISORDERNAME | 检测机构收费his名称 | string | 如：55555 |



代码实现：

表结构：LIS_HISITEM

B07	his收费项目导入

接口说明：his收费项目导入

请求URL：../hisitem/request/getitemfromhis

代码文件：winning.lis.hisitem.service.HisItemService

示例URL： 

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | hisitem | 收费项目信息 | array<object> | 收费项目信息 |
| 1 | hisordercode | HIS收费代码 | string | 不允许为空，如：10，对应原型的收费代码 |
| 2 | hisordername | HIS收费名称 | string | 允许为空，如：B型钠尿肽前体，对应原型收费名称 |
| 3 | itemtype | 项目类别 | string | 不允许为空，0 临床项目 1 收费项目 9组合项目 |
| 4 | memcode1 | 拼音码 | string | 允许为空，如：SH |
| 5 | memcode2 | 五笔码 | string | 允许为空，如：3 |
| 6 | price | 价格 | string | 允许为空，如：1 |
| 7 | unit | 价格单位 | string | 允许为空，如：次 |
| 8 | barcodeflag | 条码标志 | string | 允许为空，0—表示非条码项目，1—条码项目，默认为0 |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage |
| 1 | type | 消息类型 | string | SUCCESS-成功 
ERROR-系统出现异常或错误
FAILT-业务逻辑处理失败 |
| 2 | messageCode | 消息代码 | string | 用于区分消息代码 |
| 3 | message | 消息内容 | string | type=ERROR时则为系统错误消息提示内容
type=FAILT时则为业务提示信息 |
| 4 | link | URL链接 | string | 用于提示用户后可以点击查看详情信息，默认为空 |
| 5 | data | 数据集合 | object | 用于存储返回的数据 |



返回消息提示：Type为success时，提示信息获取message中信息

代码实现：

获取本地项目信息，获取本地不存在的收费项目信息

插入到LIS_HISITEM

表结构：LIS_HISITEM

B08	收费项目条码项目、急诊、停用状态设置

接口说明：his收费项目导入

请求URL：../hisitem/request/sethisitemstatus

代码文件：winning.lis.hisitem.service.HisItemService

示例URL： 

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | lisordercode | 价格 | string | 不允许为空，如：01001 |
| 3 | selecttext | 选中类别 | string | 不允许为空，条码项目—BARCODEFLAG，急诊项目—MJZBZ，STOPFLAG—停用 |
| 4 | flag | 选中标识 | string | 不允许为空，条码项目/急诊项目中，1表示选中，停用相反 |



接口出参【ResposeMessage.data->object】：

接口出参见B06

代码实现：

即使接口调用成功，需要读取message中的消息

表结构：LIS_HISITEM

B09	修改检验指标的检验项目

接口说明：修改检验指标检验项目

请求URL：../hisitem/request/altercheckitem

代码文件：winning.lis.ordertoitem.service.OrderToItemService

示例URL： http://192.168.10.73:10004/lis/hisitem/request/altercheckitem?hospitalcode=9999&lisordercode=01&itemcode=%Neu&itemnum=%Neu&checkitem=0101004&checkitemnum=LYM#

原型参考：

接口入参： 

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | lisordercode | lis代码 | string | 不允许为空，如：0126243 |
| 3 | itemcode | 检验项目代码 | string | 不允许为空，如：2343222 |
| 4 | itemnum | 项目标准编码 | string | 如; 嗜碱性细胞数 |
| 5 | checkitem | 检验项目 | string | 不允许为空，如：012222 |
| 6 | checkitemnum | 检验项目编码 | string | 不允许为空，如：#BSA |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | LISORDERCODE | LIS标准收费代码 | string | 如：0509 |
| 3 | ITEMCODE | 项目代码 | string | 如：ALT |
| 4 | CHECKITEM | 校验项目 | string | 如：0101004 |
| 5 | CHECKITEMNAME | 检验项目名称 | string | 如：白细胞计数 |
| 6 | CHECKITEMNUM | 校验项目标准编码 | string | 如：LYM# |



JSON返回示例：

{

		"HOSPITALCODE":"9999",

		"LISORDERCODE":"01",

		"ITEMCODE":"%Neu",

		"CHECKITEM":"0101004",

		"CHECKITEMNAME":"淋巴细胞绝对值1",

		"CHECKITEMNUM":"LYM#"

	} 

代码实现：

表结构：LIS_ORDERTOITEM\LIS_ITEM

B10	修改检验指标的检验或不入库仪器

接口说明：修改检验指标检验仪器

请求URL：../hisitem/request/alterinstlist

代码文件：winning.lis.ordertoitem.service.OrderToItemService

示例URL：http://192.168.10.73:10004/lis/hisitem/request/alterinstlist?hospitalcode=9999&lisordercode=01&itemcode=%Neu&itemnum=%Neu&instlist=5,22,23&insttype=0

原型参考：

接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | lisordercode | lis代码 | string | 不允许为空，如：0126243 |
| 3 | itemcode | 检验项目代码 | string | 不允许为空，如：2343222 |
| 4 | itemnum | 项目标准编码 | string | 如; 嗜碱性细胞数 |
| 5 | instlist | 仪器ID列表 | string | 允许为空，如：“3,4,2” |
| 6 | insttype | 校验的仪器类型 | string | 不允许为空，0-校验仪器类型，1-不入库仪器类型 |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：instlist 汇总信息 类型： array<object> | 节点名称：instlist 汇总信息 类型： array<object> | 节点名称：instlist 汇总信息 类型： array<object> | 节点名称：instlist 汇总信息 类型： array<object> | 节点名称：instlist 汇总信息 类型： array<object> |
| 1 | INSTID | 仪器ID | string | 如：2 |
| 2 | INSTNAME | 仪器名称 | string | 如：AU5800 |
| 3 | INSTCODE | 仪器编码 | string | 如：04534 |



JSON返回示例：

{

		"instlist":[

			{

				"INSTID":5,

				"INSTCODE":"KX21",

				"INSTNAME":"KX21"

			},

			{

				"INSTID":22,

				"INSTCODE":"SF-8000",

				"INSTNAME":"SF-8000"

			},

			{

				"INSTID":23,

				"INSTCODE":"xs500i",

				"INSTNAME":"xs500i"

			}

		]

	}

代码实现：

LIS_ORDERTOITEM 

B11	获取检验指标列表

接口说明：获取检验指标列表

请求URL：../hisitem/request/getorderitemlist

代码文件：winning.lis.hisitem.service.HisItemService

示例URL： 

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | lisordercode | LIS收费代码 | string | 不允许为空，如：010 |
| 3 | hisordername | HIS收费名称 | string | 允许为空，如：血 |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | LISORDERCODE | lis代码 | string | 如：014534 |
| 3 | ITEMCODE | 检验项目代码 | string | 如：124534 |
| 4 | ITEMNAME | 项目名称 | string | 如：中性粒细胞绝对值 |
| 5 | ITEMNUM | 标准编码 | string | 如：NEU# |
| 6 | ENGSHORTNAME | 英文缩写 | string | 如：NEU# |
| 7 | ENGNAME | 英文名称 | string | 如：NEU# |
| 8 | LOINCCODE | LOINC编码 | string | 如：NEU# |



代码实现：

返回参数说明：如果data中的的数据为空，需要获取对应的message中的说明

表结构：LIS_ORDERTOITEM

B12	获取收费项目仪器

接口说明：获取收费项目仪器

请求URL：../hisitem/request/getinstlist

代码文件：winning.lis.hisitem.service.HisItemService

示例URL： 

原型参考：

接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | lisordercode | LIS收费代码 | string | 不允许为空，如：010 |
| 3 | hisordername | HIS收费名称 | string | 允许为空，如：血 |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | LISORDERCODE | lis代码 | string | 如：014534 |
| 2 | INSTID | 仪器ID | string | 如： 34 |
| 3 | INSTCODE | 仪器编码 | string | 如：04534 |
| 4 | INSTNAME | 仪器名称 | string | 如：测试 |



代码实现：

返回参数说明：如果data中的的数据为空，需要获取对应的message中的说明

表结构：LIS_INSTORDER、LIS_INSTRUMENT

## 收费项目

### V 模块接口视图

| NO. | 接口编号 | 接口名称 | 说明 |
| --- | --- | --- | --- |
| 1 | A01 | 获取收费筛选列表信息
../hisitem/request/gethisitemstatuslist | 获取收费筛选列表信息 |
| 2 | A02 | A02 *添加收费项目初始化
../hisitem/request/geinitdata | 添加收费项目界面数据初始化 |
| 3 | B01 | 获取收费列表的项目信息
../hisitem/request/gethisitemlist | 单击收费项目列表，获取对应列表的项目信息 |
| 4 | B02 | 搜索收费项目
../hisitem/request/searchhisitemlist | 收费项目搜索 |
| 5 | B03 | 添加收费项目
../hisitem/request/addhisitem | 添加收费项目 |
| 6 | B04 | 修改收费项目
../hisitem/request/alterhisitem | 修改收费项目 |
| 7 | B05 | his接口导入收费项目
../hisitem/request/getitemfromhis | 修改收费项目 |
| 8 | B08 | 收费项目条码项目、急诊、停用状态设置
../hisitem/request/sethisitemstatus | 收费项目条码项目、急诊、停用状态设置 |



### A 对外公布方法

#### A01 获取收费筛选列表信息

接口说明：获取收费筛选列表信息

请求URL：../hisitem/request/gethisitemstatuslist

代码文件：winning.lis.hisitem.service.HisItemService

示例URL： 

http://192.168.10.73:15011/lis/hisitem/request/gethisitemstatuslist?hospitalcode=9999&subsyscode=LIMS

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码 |
| 2 | subsyscode | 子系统代码 | string | 传入子系统代码，允许为空
传入为空时默认为LIMS |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | NODENO | 节点编码 | string | 如：HISITEM_STATUS |
| 2 | NODENAME | 节点名称 | string | 如：状态 |
| 3 | CHILDNODES | 子节点名称 | array<object> |  |
|  |  |  |  |  |
| 1 | CODE | 字典代码 | string | 如：1 |
| 2 | NAME | 字典名称 | string | 如：生化 |
| 3 | DICID | 标准编码 | string | 如：SH |
| 4 | MEMCODE1 | 输入码一 | string | 如：SH |
| 5 | MEMCODE2 | 输入码二 | string | 如：3 |
| 6 | ORDERNO | 排序 | int | 如：1 |
|  |  |  |  |  |
| 1 | NODENO | 节点编码 | string | 如：HISITEM_EXAMINE |
| 2 | NODENAME | 节点名称 | string | 如：检验分类 |
| 3 | CHILDNODES | 子节点名称 | array<object> |  |
|  |  |  |  |  |
| 1 | CODE | 字典代码 | string | 如：1 |
| 2 | NAME | 字典名称 | string | 如：生化 |
| 3 | DICID | 标准编码 | string | 如：SH |
| 4 | MEMCODE1 | 输入码一 | string | 如：SH |
| 5 | MEMCODE2 | 输入码二 | string | 如：3 |
| 6 | ORDERNO | 排序 | int | 如：1 |
|  |  |  |  |  |
| 1 | NODEID | 节点编码 | string | 如：HISITEM_INSTORDER |
| 2 | NODENAME | 节点名称 | string | 如：仪器分类 |
| 3 | CHILDNODES | 子节点名称 | array<object> |  |
|  |  |  |  |  |
| 1 | INSTID | 仪器ID | string | 如：3，对应INSTID |
| 2 | INSTCODE | 仪器编码 | string | 如：HX-21，INSTCODE |
| 3 | INSTNAME | 仪器名称 | string | 如：HX-21，INSTNAME |
| 4 | EXECGROUPCODE | 执行小组代码 | string | 如：SH |
| 5 | EXECGROUPNAME | 执行小组名称 | string | 如：3 |
|  |  |  |  |  |
| 1 | NODEID | 节点编码 | string | 如：HISITEM_BARORDER |
| 2 | NODENAME | 节点名称 | string | 如：条码分组 |
| 3 | CHILDNODES | 子节点名称 | array<object> |  |
|  |  |  |  |  |
| 1 | CODE | 条形码分组代码 | string | 如：4，BAREXAMCODE |
| 2 | NAME | 条形码分组名称 | string | 如：体液（尿），BAREXAMNAME |
| 3 | DICID | 条码分组编码 | string | 如：2，BAREXAMCODE |
| 4 | MEMCODE1 | 输入码一 | string | 如：SH |
| 5 | MEMCODE2 | 输入码二 | string | 如：3 |
| 6 | ORDERNO | 排序 | int | 如：1，BARPRIORITY |
|  |  |  |  |  |
| 1 | NODEID | 节点编码 | string | 如：HISITEM_RETURNRULE |
| 2 | NODENAME | 节点名称 | string | 如：回执单规则 |
| 3 | CHILDNODES | 子节点名称 | array<object> |  |
|  |  |  |  |  |
| 1 | CODE | 规则ID | string | 如：23，RULENO |
| 2 | NAME | 规则名称 | string | 如：23，RULENAME |
| 3 | DICID | 条码分组编码 | string | 如：2，RULENO |
| 4 | MEMCODE1 | 输入码一 | string | 如：333 |
| 5 | MEMCODE2 | 输入码二 | string | 如：2 |
| 6 | ORDERNO | 排序 | string | 如：1 |



Json返回数据格式:

[

    {

      "NODENO": "HISITEM_STATUS",

      "NODENAME": "状态",

      "CHILDNODES": [

        {

          "CODE": "1",

          "NAME": "全部项目",

          "DICID": "全部项目",

          "DICTYPE": "状态",

          "EXTERNCODE": "1",

          "MEMCODE1": "qbxm",

          "MEMCODE2": null,

          "SUBSYSCODE": "LIMS",

          "ORDERNO": 1,

          "RESERVEFIELD1": null

        }

      ]

    },

    {

      "NODENO": "HISITEM_EXAMINE",

      "NODENAME": "检验分类",

      "CHILDNODES": [

        {

          "CODE": "1",

          "NAME": "生化",

          "DICID": "1",

          "DICTYPE": "检验分类",

          "EXTERNCODE": "1",

          "MEMCODE1": "1",

          "MEMCODE2": "TGDWXN",

          "SUBSYSCODE": "LIMS",

          "ORDERNO": null,

          "RESERVEFIELD1": null

        }

      ]

    },

    {

      "NODENO": "HISITEM_INSTORDER",

      "NODENAME": "仪器分类",

      "CHILDNODES": [

        {

          "NODENAME": "未分组",

          "CHILDNODE": [

            {

              "INSTID": 12,

              "INSTCODE": "AIA-360",

              "INSTNAME": "AIA-360",

              "EXECGROUPCODE": null,

              "EXECGROUPNAME": null

            }

          ]

        },

        {

          "NODENAME": "生化室",

          "CHILDNODE": [

            {

              "INSTID": 11,

              "INSTCODE": "AVE762B",

              "INSTNAME": "AVE762B",

              "EXECGROUPCODE": "1",

              "EXECGROUPNAME": "生化室"

            }

          ]

        }

      ]

    },

    {

      "NODENO": "HISITEM_BARORDER",

      "NODENAME": "条码分组",

      "CHILDNODES": [

        {

          "CODE": 1,

          "NAME": "生化",

          "DICID": 1,

          "MEMCODE1": "1",

          "MEMCODE2": "2",

          "ORDERNO": "1"

        }

      ]

    },

    {

      "NODENO": "HISITEM_RETURNRULE",

      "NODENAME": "回执单规则",

      "CHILDNODES": [

        {

          "CODE": 1,

          "NAME": "测试1",

          "DICID": 1,

          "MEMCODE1": "测试1",

          "MEMCODE2": "测试1",

          "ORDERNO": "1"

        }

      ]

    }

  ]

代码实现：

从SLAVE_P\SLAVE\LIS_INSTRUMENT\LIS_BAREXAMCLASS\

LIS_RETURNRULE\ LIS_HISITEM\LIS_INSTORDER\LIS_BARORDER\LIS_INSTORDER\

LIS_RETURNRULEORDER表中读取数据

表结构：SLAVE_P\SLAVE\LIS_INSTRUMENT\LIS_BAREXAMCLASS\

LIS_RETURNRULE\ LIS_HISITEM\LIS_INSTORDER\LIS_BARORDER\LIS_INSTORDER\

LIS_RETURNRULEORDER

#### A02 添加收费项目初始化

接口说明：添加收费项目界面数据初始化

请求URL：../hisitem/request/geinitdata

代码文件：winning.lis.hisitem.service.HisItemService

示例URL： 

http://192.168.10.73:15011/lis/hisitem/request/getinitdata?hospitalcode=9999&subsyscode=LIMS

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码 |
| 2 | subsyscode | 子系统代码 | string | 传入子系统代码，允许为空
传入为空时默认为LIMS |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：itemtype 项目类别 类型： array<object> | 节点名称：itemtype 项目类别 类型： array<object> | 节点名称：itemtype 项目类别 类型： array<object> | 节点名称：itemtype 项目类别 类型： array<object> | 节点名称：itemtype 项目类别 类型： array<object> |
| 1 | CODE | 字典代码 | string | 如：1 |
| 2 | NAME | 字典名称 | string | 如：生化 |
| 3 | DICID | 标准编码 | string | 如：SH |
| 4 | DICTYPE | 字典类型 | string | 如：SH |
| 5 | EXTERNCODE | 外部代码 | string | 如：SH |
| 6 | MEMCODE1 | 输入码一 | string | 如：SH |
| 7 | MEMCODE2 | 输入码二 | string | 如：3 |
| 8 | ORDERNO | 排序 | int | 如：1 |
| 9 | SUBSYSCODE | 子系统代码 | string | 如：3 |
| 10 | RESERVEFIELD1 | 扩展字段 | string | 如：3 |
| 节点名称：examine 检验分类 类型： array<object> | 节点名称：examine 检验分类 类型： array<object> | 节点名称：examine 检验分类 类型： array<object> | 节点名称：examine 检验分类 类型： array<object> | 节点名称：examine 检验分类 类型： array<object> |
| 1 | CODE | 字典代码 | string | 如：1 |
| 2 | NAME | 字典名称 | string | 如：生化 |
| 3 | DICID | 标准编码 | string | 如：SH |
| 4 | DICTYPE | 字典类型 | string | 如：SH |
| 5 | EXTERNCODE | 外部代码 | string | 如：SH |
| 6 | MEMCODE1 | 输入码一 | string | 如：SH |
| 7 | MEMCODE2 | 输入码二 | string | 如：3 |
| 8 | ORDERNO | 排序 | int | 如：1 |
| 9 | SUBSYSCODE | 子系统代码 | string | 如：3 |
| 10 | RESERVEFIELD1 | 扩展字段 | string | 如：3 |
| 节点名称：samplelist默认标本 类型： array<object> | 节点名称：samplelist默认标本 类型： array<object> | 节点名称：samplelist默认标本 类型： array<object> | 节点名称：samplelist默认标本 类型： array<object> | 节点名称：samplelist默认标本 类型： array<object> |
| 1 | CODE | 字典代码 | string | 如：1 |
| 2 | NAME | 字典名称 | string | 如：生化 |
| 3 | DICID | 标准编码 | string | 如：SH |
| 4 | DICTYPE | 字典类型 | string | 如：SH |
| 5 | EXTERNCODE | 外部代码 | string | 如：SH |
| 6 | MEMCODE1 | 输入码一 | string | 如：SH |
| 7 | MEMCODE2 | 输入码二 | string | 如：3 |
| 8 | ORDERNO | 排序 | int | 如：1 |
| 9 | SUBSYSCODE | 子系统代码 | string | 如：3 |
| 10 | RESERVEFIELD1 | 扩展字段 | string | 如：3 |



Json返回数据格式:

{

		"itemtype":[

			{

				"CODE":"0",

				"NAME":"临床项目",

				"DICID":"",

				"DICTYPE":"项目类别",

				"EXTERNCODE":"",

				"MEMCODE1":"",

				"MEMCODE2":"",

				"SUBSYSCODE":"LIMS",

				"ORDERNO":1,

				"RESERVEFIELD1":null

			}

		],

		"examine":[

			{

				"CODE":"1",

				"NAME":"生化",

				"DICID":"1",

				"DICTYPE":"检验分类",

				"EXTERNCODE":"1",

				"MEMCODE1":"1",

				"MEMCODE2":"TGDWXN",

				"SUBSYSCODE":"LIMS",

				"ORDERNO":null,

				"RESERVEFIELD1":null

			}

		],

		"samplelist":[

			{

				"CODE":"1",

				"NAME":"血",

				"DICID":"2",

				"DICTYPE":"样本类型",

				"EXTERNCODE":null,

				"MEMCODE1":"1",

				"MEMCODE2":"X",

				"SUBSYSCODE":"LIMS",

				"ORDERNO":0,

				"RESERVEFIELD1":null

			}

		]

	}

代码实现：



表结构：SLAVE_P\SLAVE\LIS_ 

### B 业务类

#### B01	获取收费列表的项目信息

接口说明：单击收费项目列表，获取对应列表的项目信息

请求URL：../hisitem/request/gethisitemlist

代码文件：winning.lis.hisitem.service.HisItemService

示例URL： 

http://192.168.10.73:15011/lis/hisitem/request/gethisitemlist?hospitalcode=9999&nodeno=HISITEM_STATUS&selectvalue=2

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | nodeno | 选项分类的nodeno | string | 不允许为空，如：HISITEM_RETURNRULE |
| 3 | selectvalue | 选择参数 | string | 不允许为空，在一级菜单“状态”下，1-全部项目、2-在用项目，3-已停用项目、4-未配置条码分组、5-未配置检验仪器、6-未配置回执单规则、7-未配置检验项目，其他菜单下为当前选中项的ID |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | LISORDERCODE | LIS标准收费代码 | string | 如：0509 |
| 3 | HISORDERCODE | HIS收费代码 | string | 如：509 |
| 4 | HISORDERNAME | HIS收费名称 | string | 如：入院治疗费 |
| 5 | BARCODELABELNAME | 收费简称 | string | 如：生化 |
| 6 | ITEMTYPE | HIS项目类别 | string | 0 临床项目 1 收费项目 9组合项目 |
| 7 | MEMCODE1 | 输入码一 | string | 如：SH |
| 8 | MEMCODE2 | 输入码二 | string | 如：3 |
| 9 | PRICE | 单价 | int | 如：1 |
| 10 | UNIT | 单位 | string | 如： |
| 11 | EXAMCODE | 检验分类代码 | string | 如：2 |
| 12 | SAMPLECODE | 默认标本种类 | string | 如：血 |
| 13 | SAMPLEDESC | 标本说明 | string | 如： |
| 14 | MJZBZ | 急诊标志 | string | 如：1 |
| 15 | BARCODEFLAG | 条码标志 | string | 如：0 |
| 16 | STOPFLAG | 停用标志 | string | 如：0 |
| 17 | MATERIALFLAG | 材料费标志 | string | 如：0 |
| 18 | MATERIALTYPE | 材料收费方式 | string | 如： |
| 19 | MATBACKFLAG | 材料费只收不退 | string | 如： |
| 20 | MATWARDORREG | 收取材料费的病人类别 | string | 如： |
| 20 | MATSAMPLE | 收取材料费的标本种类 | string | 如： |
| 21 | MATAGELOW | MATAGEUPP | string | 如： |
| 22 | MATAGEUNIT | 收取材料费年龄单位 | string | 如： |
| 23 | SAMPLECOMMENT | 采集说明 | string | 如： |
| 24 | CXREMARK | 采集注意事项 | string | 如： |
| 25 | APPLYLIMITDAYS | 申请时间预警天数 | string | 如： |
| 26 | BAREXAMCODE | 条码分组 | string | 如： |
| 27 | BAREXAMNAME | 条码分组名称 | string | 如： |
| 28 | INSTID | 仪器ID | string | 如： |
| 29 | INSTNAME | 仪器名称 | string | 如： |
| 30 | TATNAME | TAT规则字典 | string | 如： |
| 31 | TATENO | TAT规则字典代码 | string | 如： |
| 32 | MATORDERNAME | 材料费 | string | 如： |
| 33 | MATORDERCODE | 材料费字典代码 | string | 如： |
| 34 | SAMPLENAME2 | 标本类型 | string | 如： |
| 35 | SAMPLECODE2 | 标本类型代码 | string | 如： |
| 36 | SOURCELISORDERCODE | 外送的项目代码 | string | 如： |
| 37 | SOURCEHISITEMNAME | 外送项目名称 | string | 如： |
| 38 | RULENO | 回执单分组 | string | 如： |
| 39 | RULENAME | 回执单名称 | string | 如： |
| 40 | EXHOSPFLAG | 外送标志 | string | 如： |
| 41 | EXHOSPITALCODE | 外送医疗机构代码 | string | 如：8888 |



JSON返回示例：

"hisitemlist": [

      {

        "HOSPITALCODE": "9999",

        "LISORDERCODE": "062",

        "HISORDERCODE": "62",

        "HISORDERNAME": "阴道分泌物检查",

        "BARCODELABELNAME": "",

        "ITEMTYPE": "0",

        "ITEMTYPENAME": "临床项目",

        "MEMCODE1": "ydfmwjc",

        "MEMCODE2": "buwitss",

        "PRICE": 5,

        "UNIT": "",

        "EXAMCODE": "11",

        "EXAMNAME": "体液",

        "SAMPLECODE": null,

        "SAMPLEDESC": null,

        "MJZBZ": "0",

        "BARCODEFLAG": "1",

        "STOPFLAG": "0",

        "MATERIALFLAG": " ",

        "MATERIALTYPE": " ",

        "EXHOSPFLAG": "0",

        "EXHOSPITALCODE": null

      }

    ]

代码实现：

从SLAVE\LIS_INSTRUMENT\LIS_BAREXAMCLASS\

LIS_RETURNRULE\ LIS_HISITEM\LIS_INSTORDER\LIS_BARORDER\LIS_INSTORDER\

LIS_RETURNRULEORDER表中读取数据

表结构：SLAVE_P\SLAVE\LIS_INSTRUMENT\LIS_BAREXAMCLASS\

LIS_RETURNRULE\ LIS_HISITEM\LIS_INSTORDER\LIS_BARORDER\LIS_INSTORDER\

LIS_RETURNRULEORDER

#### B02	搜索收费项目

接口说明：收费项目搜索

请求URL：../hisitem/request/searchhisitem

代码文件：winning.lis.hisitem.service.HisItemService

示例URL： 

http://192.168.10.73:15011/lis/hisitem/request/searchhisitem?hospitalcode=9999&searchtext=23

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | searchtext | 搜索内容 | string | 允许为空，为空默认搜索全部 |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | LISORDERCODE | LIS标准收费代码 | string | 如：0509 |
| 3 | HISORDERCODE | HIS收费代码 | string | 如： 10 |
| 4 | HISORDERNAME | HIS收费名称 | string | 如：B型钠尿肽前体 |
| 5 | BARCODELABELNAME | 收费简称 | string | 如： |
| 6 | ITEMTYPE | HIS项目类别 | string | 0 临床项目 1 收费项目 9组合项目 |
| 7 | MEMCODE1 | 输入码一 | string | 如：SH |
| 8 | MEMCODE2 | 输入码二 | string | 如：3 |
| 9 | PRICE | 单价 | int | 如：1 |
| 10 | UNIT | 单位 | string | 如： |
| 11 | EXAMCODE | 检验分类代码 | string | 0-不可见 1-可见 |
| 12 | EXAMNAME | 检验分类名称 | string | 如：血流变 |
| 13 | SAMPLECODE | 默认标本种类 | string |  |
| 14 | SAMPLEDESC | 标本说明 | string | 如：常规字典 |
| 15 | ITEMTYPENAME | 项目类别名称 | string | 如：临床项目 |
| 16 | MJZBZ | 急诊标志 | string | 如：1 |
| 17 | BARCODEFLAG | 条码标志 | string | 如：0 |
| 18 | STOPFLAG | 停用标志 | string | 如：0 |
| 19 | MATERIALFLAG | 材料费标志 | string | 如：0 |
| 20 | MATERIALTYPE | 材料收费方式 | string | 如： |
| 21 | EXHOSPFLAG | 外送标志 | string | 如：0 |
| 22 | EXHOSPITALCODE | 外送医疗机构代码 | string | 如：8888 |



代码实现：

依据字段从LIS_HISITEM表中获取数据

表结构：LIS_HISITEM

#### B03	添加收费项目

接口说明：添加收费项目

请求URL：../hisitem/request/addhisitem

代码文件：winning.lis.hisitem.service.HisItemService

示例URL： 

原型参考：





（项目类别下拉框由前端写死，0-临床项目，1-收费项目，9-组合项目），（检验分类从A01中取）

接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | hisordercode | HIS收费代码 | string | 不允许为空，如：10，对应原型的收费代码 |
| 3 | hisordername | HIS收费名称 | string | 允许为空，如：B型钠尿肽前体，对应原型收费名称 |
| 4 | barcodelabelname | 收费简称 | string | 允许为空，如：谷氨酸，对应原型收费别名 |
| 5 | itemtype | 项目类别 | string | 不允许为空，0 临床项目 1 收费项目 9组合项目 |
| 6 | memcode1 | 拼音码 | string | 允许为空，如：SH |
| 7 | memcode2 | 五笔码 | string | 允许为空，如：3 |
| 8 | price | 价格 | string | 允许为空，如：1 |
| 9 | examcode | 检验分类代码 | string | 允许为空， |
| 10 | samplecode | 默认标本种类 | string | 允许为空，如：血 |
| 11 | mjzbz | 急诊标志 | string | 允许为空，如：1 |
| 12 | barcodeflag | 条码标志 | string | 允许为空，如：0 |
| 13 | stopflag | 停用标志 | string | 允许为空，如：0 |
| 14 | samplecomment | 采集说明 | string | 允许为空，如：123 |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：hisitem 汇总信息 类型： object | 节点名称：hisitem 汇总信息 类型： object | 节点名称：hisitem 汇总信息 类型： object | 节点名称：hisitem 汇总信息 类型： object | 节点名称：hisitem 汇总信息 类型： object |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | LISORDERCODE | LIS标准收费代码 | string | 如：LIMS |
| 3 | HISORDERCODE | HIS收费代码 | string | 如; 检验类别 |
| 4 | HISORDERNAME | HIS收费名称 | string | 如：1 |
| 5 | BARCODELABELNAME | 收费简称 | string | 如：生化 |
| 6 | ITEMTYPE | HIS项目类别 | string | 0 临床项目 1 收费项目 9组合项目 |
| 7 | MEMCODE1 | 输入码一 | string | 如：SH |
| 8 | MEMCODE2 | 输入码二 | string | 如：3 |
| 9 | PRICE | 单价 | int | 如：1 |
| 10 | UNIT | 单位 | string | 如： |
| 11 | EXAMCODE | 检验分类代码 | string | 0-不可见 1-可见 |
| 12 | EXAMNAME | 检验分类名称 | string | 如：血流变 |
| 13 | SAMPLECODE | 默认标本种类 | string |  |
| 14 | SAMPLEDESC | 标本说明 | string | 如：常规字典 |
| 15 | ITEMTYPENAME | 项目类别名称 | string | 如：临床项目 |
| 16 | MJZBZ | 急诊标志 | string | 如：1 |
| 17 | BARCODEFLAG | 条码标志 | string | 如：0 |
| 18 | STOPFLAG | 停用标志 | string | 如：0 |
| 19 | MATERIALFLAG | 材料费标志 | string | 如：0 |
| 20 | MATERIALTYPE | 材料收费方式 | string | 如： |
| 21 | EXHOSPFLAG | 外送标志 | string | 如：0 |
| 22 | EXHOSPITALCODE | 外送医疗机构代码 | string | 如：8888 |



代码实现：

从LIS_HISITEM表中查询添加LISORDERCODE的存在情况，存在就提示前端，不存在就直接插入数据

表结构：LIS_HISITEM

#### B04	修改收费项目

接口说明：修改收费项目

请求URL：../hisitem/request/alterhisitem

代码文件：winning.lis.hisitem.service.HisItemService

示例URL： 

http://192.168.10.73:15011/lis/hisitem/request/alterhisitem?hospitalcode=9999&hisordercode=123ab&hisordername=测&barcodelabelname=&itemtype=0&memcode1=cssj&memcode2=13411&price=13311

&examcode=3&samplecode=1&mjzbz=0&barcodeflag=0&stopflag=0&samplecomment=册数数据的&lisordercode=0123ab

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | lisordercode | LIS标准收费代码 | string | 不允许为空，如：010 |
| 3 | hisordercode | HIS收费代码 | string | 不允许为空，如：10，对应原型的收费代码 |
| 4 | hisordername | HIS收费名称 | string | 允许为空，如：B型钠尿肽前体，对应原型收费名称 |
| 5 | barcodelabelname | 收费简称 | string | 允许为空，如：谷氨酸，对应原型收费别名 |
| 6 | itemtype | 项目类别 | string | 不允许为空，0 临床项目 1 收费项目 9组合项目 |
| 7 | memcode1 | 拼音码 | string | 允许为空，如：SH |
| 8 | memcode2 | 五笔码 | string | 允许为空，如：3 |
| 9 | price | 价格 | string | 允许为空，如：1 |
| 10 | examcode | 检验分类代码 | string | 允许为空， |
| 11 | samplecode | 默认标本种类 | string | 允许为空，如：血 |
| 12 | mjzbz | 急诊标志 | string | 允许为空，如：1 |
| 13 | barcodeflag | 条码标志 | string | 允许为空，如：0 |
| 14 | stopflag | 停用标志 | string | 允许为空，如：0 |
| 15 | samplecomment | 采集说明 | string | 允许为空，如：123 |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- | --- |
| 节点名称：hisitem 汇总信息 类型： object | 节点名称：hisitem 汇总信息 类型： object | 节点名称：hisitem 汇总信息 类型： object | 节点名称：hisitem 汇总信息 类型： object | 节点名称：hisitem 汇总信息 类型： object | 节点名称：hisitem 汇总信息 类型： object |
| 1 | HOSPITALCODE | 医疗机构代码 | 医疗机构代码 | string | 如：9999 |
| 2 | LISORDERCODE | LIS标准收费代码 | LIS标准收费代码 | string | 如：01000 |
| 3 | HISORDERCODE | HIS收费代码 | HIS收费代码 | string | 如：1000 |
| 4 | HISORDERNAME | HIS收费名称 | HIS收费名称 | string | 如：总前列腺特异性抗原测定(体检) |
| 5 | BARCODELABELNAME | 收费简称 | 收费简称 | string | 如： |
| 6 | ITEMTYPE | HIS项目类别 | HIS项目类别 | string | 0 临床项目 1 收费项目 9组合项目 |
| 7 | MEMCODE1 | 输入码一 | 输入码一 | string | 如：SH |
| 8 | MEMCODE2 | 输入码二 | 输入码二 | string | 如：3 |
| 9 | PRICE | 单价 | 单价 | numerical | 如：15.00 |
| 10 | UNIT | 单位 | 单位 | string | 如： |
| 11 | EXAMCODE | 检验分类代码 | 检验分类代码 | string | 0-不可见 1-可见 |
| 12 | EXAMNAME | 检验分类名称 | 检验分类名称 | string | 如：血流变 |
| 13 | SAMPLECODE | 默认标本种类 | 默认标本种类 | string | 0-非系统内置 1-系统内置 前台允许查看,不允许编辑和删除 |
| 14 | SAMPLEDESC | 标本说明 | 标本说明 | string | 如：常规字典 |
| 15 | SEXRANGE | 性别适应范围 | 性别适应范围 | string | 1-男，2-女 空表示均适应 |
| 16 | MJZBZ | 急诊标志 | 急诊标志 | string | 如：1 |
| 17 | BARCODEFLAG | 条码标志 | 条码标志 | string | 如：0 |
| 18 | STOPFLAG | 停用标志 | 停用标志 | string | 如：0 |
| 19 | MATERIALFLAG | 材料费标志 | 材料费标志 | string | 如：0 |
| 20 | MATERIALTYPE | 材料收费方式 | 材料收费方式 | string | 如： |
| 21 | EXHOSPFLAG | 外送标志 | 外送标志 | string | 如：0 |
| 22 | EXHOSPITALCODE | 外送医疗机构代码 | 外送医疗机构代码 | string | 如：8888 |



代码实现：

从LIS_HISITEM表查询当前项目的信息，不存在提示前端，存在，则判断修改后的LISORDERCODE在表中的存在情况，然后获取数据

表结构：LIS_HISITEM

#### B05	his接口获取收费项目

接口说明：his接口获取收费项目

请求URL：../hisitem/request/getinitdatafromhis

代码文件：winning.lis.hisitem.service.HisItemService

示例URL： 

原型参考：





(从HIS接口导入，界面有一个动画效果)

接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | ORGCODE | 医疗机构代码 | string | 如：9999 |
| 2 | ITEMCODE | HIS收费代码 | string | 如：1000 |
| 3 | ITEMNAME | HIS收费名称 | string | 如：总前列腺特异性抗原测定(体检) |
| 4 | ITEMTYPE | HIS项目类别 | string | 0 临床项目 1 收费项目 9组合项目 |
| 5 | MEMCODE1 | 输入码一 | string | 如：SH |
| 6 | MEMCODE2 | 输入码二 | string | 如：3 |
| 7 | PRICE | 单价 | numerical | 如：15.00 |
| 8 | ITEMUNIT | 单位 | string | 如： |
| 9 | ITEMTYPENAME | HIS项目类别名称 | string | 如：临床项目 |



代码实现：

从his接口获取对应的收费项目信息

表结构：LIS_HISITEM

#### B06	获取收费项目信息

接口说明：获取收费项目信息

请求URL：../hisitem/request/gethisiteminfo

代码文件：winning.lis.hisitem.service.HisItemService

示例URL： 

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | lisordercode | LIS标准收费代码 | string | 不允许为空，如：010 |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：lisiteminfo项目信息 类型： object | 节点名称：lisiteminfo项目信息 类型： object | 节点名称：lisiteminfo项目信息 类型： object | 节点名称：lisiteminfo项目信息 类型： object | 节点名称：lisiteminfo项目信息 类型： object |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | LISORDERCODE | LIS收费代码 | string | 如：014534 |
| 3 | HISORDERCODE | HIS收费代码 | string | 如：234 |
| 4 | HISORDERNAME | HIS收费名称 | string | 如：血 |
| 5 | BARCODELABELNAME | 收费简称 | string | 如：9999 |
| 6 | ITEMTYPE | HIS项目类别 | string | 如：014534 |
| 7 | MEMCODE1 | 输入码一 | string | 如：234 |
| 8 | MEMCODE2 | 输入码二 | string | 如：血 |
| 9 | PRICE | 单价 | string | 如：232 |
| 10 | UNIT | 单位 | string | 如：元 |
| 11 | EXAMCODE | 检验分类代码 | string | 如：014534 |
| 12 | SAMPLECODE | 默认标本种类 | string | 如：2 |
| 13 | SAMPLEDESC | 标本说明 | string | 如：血 |
| 14 | OVERTIME | 采样时间超过送检时间 | string | 如2 |
| 15 | MJZBZ | 急诊标志 | string | 如：1 |
| 16 | BARCODEFLAG | 条码标志 | string | 如：1 |
| 17 | STOPFLAG | 停用标志 | string | 如：1 |
| 18 | MATERIALFLAG | 材料费标志 | string | 如：1 |
| 19 | MATERIALTYPE | 材料收费方式 | string | 如：只收不退 |
| 20 | MATBACKFLAG | 此材料费项目只收不退 | string | 如：9999 |
| 21 | MATWARDORREG | 收取材料费的病人类别 | string | 如：014534 |
| 22 | MATSAMPLE | 收取材料费的标本种类 | string | 如：234 |
| 23 | MATAGELOW | 收取材料费年龄下限 | string | 如：1 |
| 24 | MATAGEUPP | 收取材料费年龄上限 | string | 如：3 |
| 25 | MATAGEUNIT | 收取材料费年龄单位 | string | 如：次 |
| 26 | SAMPLECOMMENT | 采集说明 | string | 如：空腹 |
| 27 | CXREMARK | 采集注意事项 | string | 如：空腹 |
| 28 | EXHOSPFLAG | 外送标志 | string | 如：1 |
| 29 | EXHOSPITALCODE | 外送医疗机构代码 | string | 如：8888 |
| 30 | APPLYLIMITDAYS | 申请时间预警天数 | string | 如：2 |
| 31 | EXAMNAME | 检验分类名称 | string | 如：生化 |
| 31 | SAMPLENAME | 默认样本名称 | string | 如：血 |
| 节点名称：barorderinfo条码分组 类型： object | 节点名称：barorderinfo条码分组 类型： object | 节点名称：barorderinfo条码分组 类型： object | 节点名称：barorderinfo条码分组 类型： object | 节点名称：barorderinfo条码分组 类型： object |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | BAREXAMCODE | 规则ID | string | 如：014534 |
| 3 | LISORDERCODE | lis代码 | string | 如：234 |
| 4 | HISORDERCODE | his代码 | string | 如：29 |
| 5 | HISORDERNAME | his名称 | string | 如：妇科彩超(一个部位) |
| 6 | BARSINGLE | 单独绑定标志 | string | 如：0 |
| 7 | BAREXAMNAME | 规则名称 | string | 如：血液 |
| 节点名称：returnruleorderinfo 回执单 类型： object | 节点名称：returnruleorderinfo 回执单 类型： object | 节点名称：returnruleorderinfo 回执单 类型： object | 节点名称：returnruleorderinfo 回执单 类型： object | 节点名称：returnruleorderinfo 回执单 类型： object |
| 1 | LISORDERCODE | lis代码 | string | 如：04545 |
| 2 | RULENO | 规则ID | string | 如：23 |
| 3 | HOSPITALCODE | 医疗机构代码 | string | 如：999 |
| 4 | RULENAME | 规则名称 | string | 如：血 |
| 5 | MEMCODE1 | 输入码一 | string | 如;34 |
| 6 | MEMCODE2 | 输入码一 | string | 如：44 |
| 7 | STOPFLAG | 停用标志 | string | 如：1 |
| 节点名称：reptimeorderinfo  TAT规则 类型： array<object> | 节点名称：reptimeorderinfo  TAT规则 类型： array<object> | 节点名称：reptimeorderinfo  TAT规则 类型： array<object> | 节点名称：reptimeorderinfo  TAT规则 类型： array<object> | 节点名称：reptimeorderinfo  TAT规则 类型： array<object> |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | RULENO | TAT规则ID | string | 如：9 |
| 3 | LISORDERCODE | lis代码 | string | 如：01243 |
| 4 | RULENAME | TAT规则名称 | string | 如：测试 |
| 5 | SOURCENODE | 开始目标节点 | string | 如：CY |
| 6 | TARGETNODE | 开始目标结束节点 | string | 如：BD |
| 7 | MEMCODE1 | 输入码一 | string | 如：CY |
| 8 | MEMCODE2 | 输入码一 | string | 如：123 |
| 9 | STOPFLAG | 停用标志 | string | 如：1 |
| 节点名称：ordersampleinfo  标本种类 类型： array<object> | 节点名称：ordersampleinfo  标本种类 类型： array<object> | 节点名称：ordersampleinfo  标本种类 类型： array<object> | 节点名称：ordersampleinfo  标本种类 类型： array<object> | 节点名称：ordersampleinfo  标本种类 类型： array<object> |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | LISORDERCODE | lis代码 | string | 如：014534 |
| 3 | CODE | 样本类型代码 | string | 如：234 |
| 4 | NAME | 样本类型名称 | string | 如：血 |
| 5 | DEFAULTFLAG | 默认标本标志 | string | 0-非默认标本 1-默认标本 |
| 节点名称：instorderinfo   检验仪器 类型： array<object> | 节点名称：instorderinfo   检验仪器 类型： array<object> | 节点名称：instorderinfo   检验仪器 类型： array<object> | 节点名称：instorderinfo   检验仪器 类型： array<object> | 节点名称：instorderinfo   检验仪器 类型： array<object> |
| 1 | LISORDERCODE | lis代码 | string | 如：014534 |
| 2 | INSTID | 仪器ID | string | 如： 34 |
| 3 | INSTCODE | 仪器编码 | string | 如：04534 |
| 4 | INSTNAME | 仪器名称 | string | 如：测试 |
| 节点名称：ordertoiteminfo检验指标 类型： array<object> | 节点名称：ordertoiteminfo检验指标 类型： array<object> | 节点名称：ordertoiteminfo检验指标 类型： array<object> | 节点名称：ordertoiteminfo检验指标 类型： array<object> | 节点名称：ordertoiteminfo检验指标 类型： array<object> |
|  | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
|  | LISORDERCODE | lis代码 | string | 如：014534 |
|  | ITEMCODE | 检验项目代码 | string | 如：124534 |
|  | ITEMNAME | 项目名称 | string | 如：中性粒细胞绝对值 |
|  | ITEMNUM | 标准编码 | string | 如：NEU# |
|  | ENGSHORTNAME | 英文缩写 | string | 如：NEU# |
|  | ENGNAME | 英文名称 | string | 如：NEU# |
|  | LOINCCODE | LOINC编码 | string | 如：NEU# |
|  | CHARGEORDER | 收费顺序 | string | 如：1 |
|  | CHECKITEM | 校验项目 | string | 如：1 |
|  | CHECKITEMNAME | 校验项目名称 | string | 如：淋巴细胞绝对值1 |
|  | CHECKITEMNUM | 校验项目标准编码 | string | 如：LYM# |
|  | 节点名称 ：INSTLISTMUST   校验仪器               array<string> | 节点名称 ：INSTLISTMUST   校验仪器               array<string> | 节点名称 ：INSTLISTMUST   校验仪器               array<string> | 节点名称 ：INSTLISTMUST   校验仪器               array<string> |
|  | INSTID | 仪器ID | string | 如：5 |
|  | INSTCODE | 仪器编码 | string | 如：KX21 |
|  | INSTNAME | 仪器名称 | string | 如：KX21 |
|  | 节点名称 ：NOTINSERTRESULT  不需要入库仪器               array<string> | 节点名称 ：NOTINSERTRESULT  不需要入库仪器               array<string> | 节点名称 ：NOTINSERTRESULT  不需要入库仪器               array<string> | 节点名称 ：NOTINSERTRESULT  不需要入库仪器               array<string> |
|  | INSTID | 仪器ID | string | 如：5 |
|  | INSTCODE | 仪器编码 | string | 如：KX21 |
|  | INSTNAME | 仪器名称 | string | 如：KX21 |
|  | CHECKFLAG | 校验标志 | string | 0-校验，1-校验 |
| 节点名称：materialtoorderinfo 材料费指标 类型： array<object> | 节点名称：materialtoorderinfo 材料费指标 类型： array<object> | 节点名称：materialtoorderinfo 材料费指标 类型： array<object> | 节点名称：materialtoorderinfo 材料费指标 类型： array<object> | 节点名称：materialtoorderinfo 材料费指标 类型： array<object> |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | MATORDERCODE | LIS材料费代码 | string | 如：0123323 |
| 3 | MATHISORDERCODE | HIS材料费代码 | string | 如：1001 |
| 4 | MATHISORDERNAME | 料费名称 | string | 如：泌尿系彩超(一个部位) |
| 5 | LISORDERCODE | LIS收费项目代码 | string | 如：028 |
| 6 | HISORDERCODE | HIS收费项目代码 | string | 如：28 |
| 7 | HISORDERNAME | 收费项目名称 | string | 如：泌尿系彩超(一个部位) |
| 节点名称：exhospitalorderinfo 外送机构 类型： array<object> | 节点名称：exhospitalorderinfo 外送机构 类型： array<object> | 节点名称：exhospitalorderinfo 外送机构 类型： array<object> | 节点名称：exhospitalorderinfo 外送机构 类型： array<object> | 节点名称：exhospitalorderinfo 外送机构 类型： array<object> |
| 1 | SOURCEHOSPITALCODE | 送检医疗机构代码 | string | 如：9999 |
| 2 | SOURCELISORDERCODE | 送检机构收费代码 | string | 如：034222 |
| 3 | TARGETHOSPITALCODE | 检测医疗机构代码 | string | 如：8888 |
| 4 | TARGETHOSPITALCODENAME | 检测医疗机构名称 | string | 如：合肥附属医院 |
| 5 | TARGETLISORDERCODE | 检测机构收费代码 | string | 如：0642344 |
| 6 | TARGETHISORDERCODE | 检测机构收费his代码 | string | 如：55555 |
| 7 | TARGETHISORDERNAME | 检测机构收费his名称 | string | 如：55555 |



代码实现：

表结构：LIS_HISITEM

#### B07	his收费项目导入

接口说明：his收费项目导入

请求URL：../hisitem/request/getitemfromhis

代码文件：winning.lis.hisitem.service.HisItemService

示例URL： 

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | hisitem | 收费项目信息 | array<object> | 收费项目信息 |
| 1 | hisordercode | HIS收费代码 | string | 不允许为空，如：10，对应原型的收费代码 |
| 2 | hisordername | HIS收费名称 | string | 允许为空，如：B型钠尿肽前体，对应原型收费名称 |
| 3 | itemtype | 项目类别 | string | 不允许为空，0 临床项目 1 收费项目 9组合项目 |
| 4 | memcode1 | 拼音码 | string | 允许为空，如：SH |
| 5 | memcode2 | 五笔码 | string | 允许为空，如：3 |
| 6 | price | 价格 | string | 允许为空，如：1 |
| 7 | unit | 价格单位 | string | 允许为空，如：次 |
| 8 | barcodeflag | 条码标志 | string | 允许为空，0—表示非条码项目，1—条码项目，默认为0 |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage |
| 1 | type | 消息类型 | string | SUCCESS-成功 
ERROR-系统出现异常或错误
FAILT-业务逻辑处理失败 |
| 2 | messageCode | 消息代码 | string | 用于区分消息代码 |
| 3 | message | 消息内容 | string | type=ERROR时则为系统错误消息提示内容
type=FAILT时则为业务提示信息 |
| 4 | link | URL链接 | string | 用于提示用户后可以点击查看详情信息，默认为空 |
| 5 | data | 数据集合 | object | 用于存储返回的数据 |



返回消息提示：Type为success时，提示信息获取message中信息

代码实现：

获取本地项目信息，获取本地不存在的收费项目信息

插入到LIS_HISITEM

表结构：LIS_HISITEM

#### B08	收费项目条码项目、急诊、停用状态设置

接口说明：his收费项目导入

请求URL：../hisitem/request/sethisitemstatus

代码文件：winning.lis.hisitem.service.HisItemService

示例URL： 

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | lisordercode | 价格 | string | 不允许为空，如：01001 |
| 3 | selecttext | 选中类别 | string | 不允许为空，条码项目—BARCODEFLAG，急诊项目—MJZBZ，STOPFLAG—停用 |
| 4 | flag | 选中标识 | string | 不允许为空，条码项目/急诊项目中，1表示选中，停用相反 |



接口出参【ResposeMessage.data->object】：

接口出参见B06

代码实现：

即使接口调用成功，需要读取message中的消息

表结构：LIS_HISITEM

#### B09	修改检验指标的检验项目

接口说明：修改检验指标检验项目

请求URL：../hisitem/request/altercheckitem

代码文件：winning.lis.ordertoitem.service.OrderToItemService

示例URL： http://192.168.10.73:10004/lis/hisitem/request/altercheckitem?hospitalcode=9999&lisordercode=01&itemcode=%Neu&itemnum=%Neu&checkitem=0101004&checkitemnum=LYM#

原型参考：

接口入参： 

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | lisordercode | lis代码 | string | 不允许为空，如：0126243 |
| 3 | itemcode | 检验项目代码 | string | 不允许为空，如：2343222 |
| 4 | itemnum | 项目标准编码 | string | 如; 嗜碱性细胞数 |
| 5 | checkitem | 检验项目 | string | 不允许为空，如：012222 |
| 6 | checkitemnum | 检验项目编码 | string | 不允许为空，如：#BSA |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | LISORDERCODE | LIS标准收费代码 | string | 如：0509 |
| 3 | ITEMCODE | 项目代码 | string | 如：ALT |
| 4 | CHECKITEM | 校验项目 | string | 如：0101004 |
| 5 | CHECKITEMNAME | 检验项目名称 | string | 如：白细胞计数 |
| 6 | CHECKITEMNUM | 校验项目标准编码 | string | 如：LYM# |



JSON返回示例：

{

		"HOSPITALCODE":"9999",

		"LISORDERCODE":"01",

		"ITEMCODE":"%Neu",

		"CHECKITEM":"0101004",

		"CHECKITEMNAME":"淋巴细胞绝对值1",

		"CHECKITEMNUM":"LYM#"

	} 

代码实现：

表结构：LIS_ORDERTOITEM\LIS_ITEM

#### B10	修改检验指标的检验或不入库仪器

接口说明：修改检验指标检验仪器

请求URL：../hisitem/request/alterinstlist

代码文件：winning.lis.ordertoitem.service.OrderToItemService

示例URL：http://192.168.10.73:10004/lis/hisitem/request/alterinstlist?hospitalcode=9999&lisordercode=01&itemcode=%Neu&itemnum=%Neu&instlist=5,22,23&insttype=0

原型参考：

接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | lisordercode | lis代码 | string | 不允许为空，如：0126243 |
| 3 | itemcode | 检验项目代码 | string | 不允许为空，如：2343222 |
| 4 | itemnum | 项目标准编码 | string | 如; 嗜碱性细胞数 |
| 5 | instlist | 仪器ID列表 | string | 允许为空，如：“3,4,2” |
| 6 | insttype | 校验的仪器类型 | string | 不允许为空，0-校验仪器类型，1-不入库仪器类型 |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：instlist 汇总信息 类型： array<object> | 节点名称：instlist 汇总信息 类型： array<object> | 节点名称：instlist 汇总信息 类型： array<object> | 节点名称：instlist 汇总信息 类型： array<object> | 节点名称：instlist 汇总信息 类型： array<object> |
| 1 | INSTID | 仪器ID | string | 如：2 |
| 2 | INSTNAME | 仪器名称 | string | 如：AU5800 |
| 3 | INSTCODE | 仪器编码 | string | 如：04534 |



JSON返回示例：

{

		"instlist":[

			{

				"INSTID":5,

				"INSTCODE":"KX21",

				"INSTNAME":"KX21"

			},

			{

				"INSTID":22,

				"INSTCODE":"SF-8000",

				"INSTNAME":"SF-8000"

			},

			{

				"INSTID":23,

				"INSTCODE":"xs500i",

				"INSTNAME":"xs500i"

			}

		]

	}

代码实现：

LIS_ORDERTOITEM 

#### B11	获取检验指标列表

接口说明：获取检验指标列表

请求URL：../hisitem/request/getorderitemlist

代码文件：winning.lis.hisitem.service.HisItemService

示例URL： 

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | lisordercode | LIS收费代码 | string | 不允许为空，如：010 |
| 3 | hisordername | HIS收费名称 | string | 允许为空，如：血 |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | LISORDERCODE | lis代码 | string | 如：014534 |
| 3 | ITEMCODE | 检验项目代码 | string | 如：124534 |
| 4 | ITEMNAME | 项目名称 | string | 如：中性粒细胞绝对值 |
| 5 | ITEMNUM | 标准编码 | string | 如：NEU# |
| 6 | ENGSHORTNAME | 英文缩写 | string | 如：NEU# |
| 7 | ENGNAME | 英文名称 | string | 如：NEU# |
| 8 | LOINCCODE | LOINC编码 | string | 如：NEU# |



代码实现：

返回参数说明：如果data中的的数据为空，需要获取对应的message中的说明

表结构：LIS_ORDERTOITEM

#### B12	获取收费项目仪器

接口说明：获取收费项目仪器

请求URL：../hisitem/request/getinstlist

代码文件：winning.lis.hisitem.service.HisItemService

示例URL： 

原型参考：

接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | lisordercode | LIS收费代码 | string | 不允许为空，如：010 |
| 3 | hisordername | HIS收费名称 | string | 允许为空，如：血 |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | LISORDERCODE | lis代码 | string | 如：014534 |
| 2 | INSTID | 仪器ID | string | 如： 34 |
| 3 | INSTCODE | 仪器编码 | string | 如：04534 |
| 4 | INSTNAME | 仪器名称 | string | 如：测试 |



代码实现：

返回参数说明：如果data中的的数据为空，需要获取对应的message中的说明

表结构：LIS_INSTORDER、LIS_INSTRUMENT



### 条码分组项目维护

#### V 模块接口视图

| NO. | 接口编号 | 接口名称 | 说明 |
| --- | --- | --- | --- |
| 1 | A01 | A01	条码分组请求数据初始化
../barexamclass/request/getinitdata | 请求条码分组界面默认标本、送检小组、绑定类型、材料执行科室、添加剂初始化数据 |
| 2 | B01 | 获取条码分组列表
../barexamclass/request/ getbarexamclasslist | 获取收费项目的条码分组列表 |
| 3 | B02 | 添加条码分组
../barexamclass/request/addbarexamclass | 条码分组信息修改 |
| 4 | B03 | 修改条码分组信息
../barexamclass/request/alterbarexamclass | 修改条码分组信息 |
| 5 | B04 | 删除条码分组
../barexamclassr/request/dropbarexamclass | 删除条码分组 |
| 6 | B05 | 添加条码分组的收费项目
../barexamclass/request/addhisitem | 添加条码分组的收费项目 |
| 7 | B06 | 删除条码分组的收费项目
../barexamclass/request/drophisitem | 删除条码分组的收费项目 |



#### A 对外公布方法

##### A01	条码分组请求数据初始化

接口说明：请求条码分组界面默认标本、送检小组、绑定类型、材料执行科室、添加剂初始化数据

请求URL：../barexamclass/request/getinitdata

代码文件：winning.lis.barexamclass.service.BarExamClassService

示例URL： 

原型参考：



接口入参： 

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：samplelist 汇总信息 类型：array< object> 默认标本 | 节点名称：samplelist 汇总信息 类型：array< object> 默认标本 | 节点名称：samplelist 汇总信息 类型：array< object> 默认标本 | 节点名称：samplelist 汇总信息 类型：array< object> 默认标本 | 节点名称：samplelist 汇总信息 类型：array< object> 默认标本 |
| 1 | CODE | 字典代码 | string | 如：1 |
| 2 | NAME | 字典名称 | string | 如：血 |
| 3 | MEMCODE1 | 输入码一 | string | 如：1 |
| 4 | MEMCODE2 | 输入码二 | string | 如：X |
| 5 | DICID | 标准编码 | string | 如：1 |
| 6 | DICTYPE | 字典分类 | string | 如：样本类型 |
| 7 | SUBSYSCODE | 子系统分类 | string | 如：LIMS |
| 8 | EXTERNCODE | 外部代码 | string | 如：10 |
| 9 | ORDERNO | 排序 | string | 如：0 |
| 10 | RESERVEFIELD1 | 扩展字段信息 | string | 如：null |
| 节点名称：examgrouplist 汇总信息 类型：array< object> 检测小组 | 节点名称：examgrouplist 汇总信息 类型：array< object> 检测小组 | 节点名称：examgrouplist 汇总信息 类型：array< object> 检测小组 | 节点名称：examgrouplist 汇总信息 类型：array< object> 检测小组 | 节点名称：examgrouplist 汇总信息 类型：array< object> 检测小组 |
| 1 | CODE | 字典代码 | string | 如：4 |
| 2 | NAME | 字典名称 | string | 如：体液室 |
| 3 | MEMCODE1 | 输入码一 | string | 如：4 |
| 4 | MEMCODE2 | 输入码二 | string | 如：tys |
| 5 | DICID | 标准编码 | string | 如：10 |
| 6 | DICTYPE | 字典分类 | string | 如：检测小组 |
| 7 | SUBSYSCODE | 子系统分类 | string | 如：LIMS |
| 8 | EXTERNCODE | 外部代码 | string | 如：10 |
| 9 | ORDERNO | 排序 | string | 如：10 |
| 10 | RESERVEFIELD1 | 扩展字段信息 | string | 如：null |
| 节点名称：baradditivelist 汇总信息 类型：array< object> 添加剂 | 节点名称：baradditivelist 汇总信息 类型：array< object> 添加剂 | 节点名称：baradditivelist 汇总信息 类型：array< object> 添加剂 | 节点名称：baradditivelist 汇总信息 类型：array< object> 添加剂 | 节点名称：baradditivelist 汇总信息 类型：array< object> 添加剂 |
| 1 | CODE | 字典代码 | string | 如：02 |
| 2 | NAME | 字典名称 | string | 如：凝胶剂 |
| 3 | MEMCODE1 | 输入码一 | string | 如：njj |
| 4 | MEMCODE2 | 输入码二 | string | 如：1 |
| 5 | DICID | 标准编码 | string | 如：02 |
| 6 | DICTYPE | 字典分类 | string | 如：添加剂 |
| 7 | SUBSYSCODE | 子系统分类 | string | 如：LIMS |
| 8 | EXTERNCODE | 外部代码 | string | 如：10 |
| 9 | ORDERNO | 排序 | string | 如：10 |
| 10 | RESERVEFIELD1 | 扩展字段信息 | string | 如：null |
| 节点名称：printrulelist 汇总信息 类型：array< object> 打印规则 | 节点名称：printrulelist 汇总信息 类型：array< object> 打印规则 | 节点名称：printrulelist 汇总信息 类型：array< object> 打印规则 | 节点名称：printrulelist 汇总信息 类型：array< object> 打印规则 | 节点名称：printrulelist 汇总信息 类型：array< object> 打印规则 |
| 1 | CODE | 字典代码 | string | 如：QZ+LSH |
| 2 | NAME | 字典名称 | string | 如：分组前缀+流水号 |
| 3 | MEMCODE1 | 输入码一 | string | 如：QZLSH |
| 4 | MEMCODE2 | 输入码二 | string | 如：1 |
| 5 | DICID | 标准编码 | string | 如：10 |
| 6 | DICTYPE | 字典分类 | string | 如：条码生成规则 |
| 7 | SUBSYSCODE | 子系统分类 | string | 如：LIMS |
| 8 | EXTERNCODE | 外部代码 | string | 如：10 |
| 9 | ORDERNO | 排序 | string | 如：10 |
| 10 | RESERVEFIELD1 | 扩展字段信息 | string | 如：null |
| 节点名称：execdeptlist 汇总信息类型：array< object> 执行科室 | 节点名称：execdeptlist 汇总信息类型：array< object> 执行科室 | 节点名称：execdeptlist 汇总信息类型：array< object> 执行科室 | 节点名称：execdeptlist 汇总信息类型：array< object> 执行科室 | 节点名称：execdeptlist 汇总信息类型：array< object> 执行科室 |
| 1 | CODE | 字典代码 | string | 如：2110 |
| 2 | NAME | 字典名称 | string | 如：医学检验科 |
| 3 | MEMCODE1 | 输入码一 | string | 如：jyk |
| 4 | MEMCODE2 | 输入码二 | string | 如：1 |
| 5 | DICID | 标准编码 | string | 如：2110 |
| 6 | DICTYPE | 字典分类 | string | 如：3 |
| 7 | SUBSYSCODE | 子系统分类 | string | 如：LIMS |
| 8 | EXTERNCODE | 外部代码 | string | 如：10 |
| 9 | ORDERNO | 排序 | string | 如：10 |
| 10 | RESERVEFIELD1 | 扩展字段信息 | string | 如： |
| 节点名称：barpicturelist 汇总信息类型：array< object> 试管图标 | 节点名称：barpicturelist 汇总信息类型：array< object> 试管图标 | 节点名称：barpicturelist 汇总信息类型：array< object> 试管图标 | 节点名称：barpicturelist 汇总信息类型：array< object> 试管图标 | 节点名称：barpicturelist 汇总信息类型：array< object> 试管图标 |
| 1 | CODE | 字典代码 | string | 如：01 |
| 2 | NAME | 字典名称 | string | 如：灰色 |
| 3 | MEMCODE1 | 输入码一 | string | 如：gray |
| 4 | MEMCODE2 | 输入码二 | string | 如：1 |
| 5 | DICID | 标准编码 | string | 如：orange.png |
| 6 | DICTYPE | 字典分类 | string | 如：试管图标 |
| 7 | SUBSYSCODE | 子系统分类 | string | 如：LIMS |
| 8 | EXTERNCODE | 外部代码 | string | 如：10 |
| 9 | ORDERNO | 排序 | string | 如：10 |
| 10 | RESERVEFIELD1 | 色标 | string | 如：1235322 |
| 节点名称：barsinglelist 汇总信息类型：array< object> 绑定类型 | 节点名称：barsinglelist 汇总信息类型：array< object> 绑定类型 | 节点名称：barsinglelist 汇总信息类型：array< object> 绑定类型 | 节点名称：barsinglelist 汇总信息类型：array< object> 绑定类型 | 节点名称：barsinglelist 汇总信息类型：array< object> 绑定类型 |
| 1 | CODE | 字典代码 | string | 如：01 |
| 2 | NAME | 字典名称 | string | 如：按条码组绑定 |
| 3 | MEMCODE1 | 输入码一 | string | 如：tmzbd |
| 4 | MEMCODE2 | 输入码二 | string | 如：1 |
| 5 | DICID | 标准编码 | string | 如：tmzbd |
| 6 | DICTYPE | 字典分类 | string | 如：条码绑定类型 |
| 7 | SUBSYSCODE | 子系统分类 | string | 如：LIMS |
| 8 | EXTERNCODE | 外部代码 | string | 如：10 |
| 9 | ORDERNO | 排序 | string | 如：10 |
| 10 | RESERVEFIELD1 | 扩展字段信息 | string | 如：null |



Json数据返回格式：

{

    "samplelist": [

      {

        "CODE": "1",

        "NAME": "血",

        "DICID": "2",

        "DICTYPE": "样本类型",

        "EXTERNCODE": null,

        "MEMCODE1": "1",

        "MEMCODE2": "X",

        "SUBSYSCODE": "LIMS",

        "ORDERNO": 0,

        "RESERVEFIELD1": null

      },

      {

        "CODE": "10",

        "NAME": "关节腔液",

        "DICID": "",

        "DICTYPE": "样本类型",

        "EXTERNCODE": "",

        "MEMCODE1": "10",

        "MEMCODE2": "gjqy",

        "SUBSYSCODE": "LIMS",

        "ORDERNO": 0,

        "RESERVEFIELD1": null

      }

    ],

    "examgrouplist": [

      {

        "CODE": "1",

        "NAME": "生化室",

        "DICID": null,

        "DICTYPE": "检测小组",

        "EXTERNCODE": null,

        "MEMCODE1": "1",

        "MEMCODE2": "shs",

        "SUBSYSCODE": "LIMS",

        "ORDERNO": 0,

        "RESERVEFIELD1": null

      }

    ],

    "baradditivelist": [

      {

        "CODE": "02",

        "NAME": "凝胶剂",

        "DICID": "njj",

        "DICTYPE": "添加剂",

        "EXTERNCODE": "",

        "MEMCODE1": "njj",

        "MEMCODE2": "",

        "SUBSYSCODE": "LIMS",

        "ORDERNO": 0,

        "RESERVEFIELD1": null

      }

    ],

    "printrulelist": [

      {

        "CODE": "QZ+LSH",

        "NAME": "分组前缀+流水号",

        "DICID": "",

        "DICTYPE": "条码生成规则",

        "EXTERNCODE": "",

        "MEMCODE1": "QZLSH",

        "MEMCODE2": "",

        "SUBSYSCODE": "LIMS",

        "ORDERNO": 1,

        "RESERVEFIELD1": null

      }

    ],

    "execdeptlist": [

      {

        "HOSPITALCODE": "9999",

        "EXTERNCODE": "2110",

        "MEMCODE1": "jyk",

        "MEMCODE2": "sct",

        "SUBSYSCODE": "LIMS",

        "DICID": "2110",

        "CODE": "2110",

        "NAME": "医学检验科",

        "DICTYPE": "3",

        "ORDERNO": ""

      }

    ],

    "barpicturelist": [

      {

        "CODE": "01",

        "NAME": "灰色",

        "DICID": "gray.png",

        "DICTYPE": "试管图标",

        "EXTERNCODE": "",

        "MEMCODE1": "gray",

        "MEMCODE2": "",

        "SUBSYSCODE": "LIMS",

        "ORDERNO": 0,

        "RESERVEFIELD1": "1235322"

      }

    ],

    "barsinglelist": [

      {

        "CODE": "01",

        "NAME": "按条码组绑定",

        "DICID": "tmzbd",

        "DICTYPE": "条码绑定类型",

        "EXTERNCODE": "",

        "MEMCODE1": "tmzbd",

        "MEMCODE2": "",

        "SUBSYSCODE": "LIMS",

        "ORDERNO": 0,

        "RESERVEFIELD1": null

      }

    ]

  }

代码实现：

从SLAVE、 SLAVE_P获取的信息，SYS_DEPT获取部门信息 

表结构：SLAVE、SLAVE_P、SYS_DEPT

##### A02 获取收费筛选列表

接口说明：获取收费筛选列表

请求URL：../barexamclass/request/getstatuslist

代码文件：winning.lis.hisitem.service.HisItemService

示例URL： 

http://192.168.10.73:15011/lis/barexamclass/request/getstatuslist?hospitalcode=9999&subsyscode=LIMS

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码 |
| 2 | subsyscode | 子系统代码 | string | 传入子系统代码，允许为空
传入为空时默认为LIMS |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | NODENO | 节点编码 | string | 如：HISITEM_STATUS |
| 2 | NODENAME | 节点名称 | string | 如：状态 |
| 3 | CHILDNODES | 子节点名称 | array<object> |  |
|  |  |  |  |  |
| 1 | CODE | 字典代码 | string | 如：1 |
| 2 | NAME | 字典名称 | string | 如：生化 |
| 3 | DICID | 标准编码 | string | 如：SH |
| 4 | MEMCODE1 | 输入码一 | string | 如：SH |
| 5 | MEMCODE2 | 输入码二 | string | 如：3 |
| 6 | ORDERNO | 排序 | int | 如：1 |
|  |  |  |  |  |
| 1 | NODENO | 节点编码 | string | 如：HISITEM_EXAMINE |
| 2 | NODENAME | 节点名称 | string | 如：检验分类 |
| 3 | CHILDNODES | 子节点名称 | array<object> |  |
|  |  |  |  |  |
| 1 | CODE | 字典代码 | string | 如：1 |
| 2 | NAME | 字典名称 | string | 如：生化 |
| 3 | DICID | 标准编码 | string | 如：SH |
| 4 | MEMCODE1 | 输入码一 | string | 如：SH |
| 5 | MEMCODE2 | 输入码二 | string | 如：3 |
| 6 | ORDERNO | 排序 | int | 如：1 |
|  |  |  |  |  |
| 1 | NODEID | 节点编码 | string | 如：HISITEM_INSTORDER |
| 2 | NODENAME | 节点名称 | string | 如：仪器分类 |
| 3 | CHILDNODES | 子节点名称 | array<object> |  |
|  |  |  |  |  |
| 1 | INSTID | 仪器ID | string | 如：3，对应INSTID |
| 2 | INSTCODE | 仪器编码 | string | 如：HX-21，INSTCODE |
| 3 | INSTNAME | 仪器名称 | string | 如：HX-21，INSTNAME |
| 4 | EXECGROUPCODE | 执行小组代码 | string | 如：SH |
| 5 | EXECGROUPNAME | 执行小组名称 | string | 如：3 |
|  |  |  |  |  |
| 1 | NODEID | 节点编码 | string | 如：HISITEM_BARORDER |
| 2 | NODENAME | 节点名称 | string | 如：条码分组 |
| 3 | CHILDNODES | 子节点名称 | array<object> |  |
|  |  |  |  |  |
| 1 | CODE | 条形码分组代码 | string | 如：4，BAREXAMCODE |
| 2 | NAME | 条形码分组名称 | string | 如：体液（尿），BAREXAMNAME |
| 3 | DICID | 条码分组编码 | string | 如：2，BAREXAMCODE |
| 4 | MEMCODE1 | 输入码一 | string | 如：SH |
| 5 | MEMCODE2 | 输入码二 | string | 如：3 |
| 6 | ORDERNO | 排序 | int | 如：1，BARPRIORITY |
|  |  |  |  |  |
| 1 | NODEID | 节点编码 | string | 如：HISITEM_RETURNRULE |
| 2 | NODENAME | 节点名称 | string | 如：回执单规则 |
| 3 | CHILDNODES | 子节点名称 | array<object> |  |
|  |  |  |  |  |
| 1 | CODE | 规则ID | string | 如：23，RULENO |
| 2 | NAME | 规则名称 | string | 如：23，RULENAME |
| 3 | DICID | 条码分组编码 | string | 如：2，RULENO |
| 4 | MEMCODE1 | 输入码一 | string | 如：333 |
| 5 | MEMCODE2 | 输入码二 | string | 如：2 |
| 6 | ORDERNO | 排序 | string | 如：1 |





#### B 业务类

##### B01	获取条码分组列表

接口说明：获取收费项目的条码分组列表

请求URL：../barexamclass/request/getbarexamclasslist

代码文件：winning.lis.barexamclass.service.BarExamBlassService

示例URL： 

http://192.168.10.73:15011/lis/barexamclass/request/getbarexamclasslist?hospitalcode=9999

原型参考：



接口入参： 

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：barexamclassinfo 汇总信息 类型：array< object> | 节点名称：barexamclassinfo 汇总信息 类型：array< object> | 节点名称：barexamclassinfo 汇总信息 类型：array< object> | 节点名称：barexamclassinfo 汇总信息 类型：array< object> | 节点名称：barexamclassinfo 汇总信息 类型：array< object> |
| 1 | BAREXAMCODE | 条形码分组代码 | string | 如：1 |
| 2 | BAREXAMNAME | 条形码分组名称 | string | 如; 生化 |
| 3 | BARCOLOR | 颜色 | string | 如：8454143 |
| 4 | BARSECONDCOLOR | 第二颜色 | string | 一般指条形码的颜色，如：16777215 |
| 5 | BARPREPART | 条形码前缀 | string | 如：01 |
| 6 | BARSINGLE | 单独标志 | int | 0：同一个类别捆绑一根条码；1：每个HIS医嘱项目单独捆绑一根条码 |
| 7 | BARPRIORITY | 优先次序 | string | 如：1 |
| 8 | BARLENGTH | 条码长度 | int | 如：10 |
| 9 | BARADDITIVE | 添加剂 | string | 如：肝素锂抗凝管 |
| 10 | BARCUBAGE | 试管采血量 | string | 如：5ml |
| 11 | BARRANGE | 适用范围 | string | 如：肝素锂抗凝管 |
| 12 | BARDESCRIBE | 分组描述 | string | 如：管道血 |
| 13 | EXAMGROUPCODE | 检测小组代码 | string | 如：1 |
| 14 | EXAMGROUPNAME | 检测小组名称 | string | 如：生化室 |
| 15 | SAMPLECODE | 标本种类代码 | string | 如：1 |
| 16 | SAMPLENAME | 标本种类名称 | string | 如：血 |
| 17 | CHECKSTATUS | 在采集界面默认是否勾选状态 | string | 如：1 |
| 18 | BARSECPREPART | 第二前缀 | string | 如： |
| 19 | PRINTORDER | 打印序号 | string | 如：1 |
| 20 | ZYPRINTCOUNT | 住院打印条码份数 | int | 如：1 |
| 21 | MZPRINTCOUNT | 门诊打印条码份数 | int | 如：1 |
| 22 | TJPRINTCOUNT | 体检打印条码份数 | int | 如：1 |
| 23 | MATEXECDEPT | 材料费执行科室 | string | 如：生化室 |
| 24 | SUBMISSIONPLACE | 送检地点 文字描述 | string | 如：检验科室 |
| 25 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 26 | BARNOTICE | 采集事项 | string | 如：混匀5次 |
| 27 | BARGENRULE | 条码规则编码 | string | 如：QZ+LSH |
| 28 | HOSPBARPRE | 医疗机构简码 | string | 如：SQ |
| 29 | IMAGEURL | 试管图标相对地址 | string | 如：orange.png |
| 30 | COLLECTNOTICE | 采集注意事项 | string | 如： |
| 31 | BAREXAMNUM | 条码分组编码 | string | 如：11 |
| 32 | COLORNAME | 颜色名称 | string | 如：黄色 |
| 33 | COLOR | 颜色编码 | string | 如：#008000 |
| 34 | COLOR1 | 颜色编码1 | string | 如：#008000 |
| 35 | BARGENRULENAME | 条码生成规则名称 | string | 如：分组前缀+流水号 |
| 36 | MATEXECDEPTNAME | 材料费执行科室名称 | string | 如：生化室 |
| 37 | BARSINGLENAME | 条码绑定类型名称 | string | 如： |



Json返回数据格式：

[

    {

      "BAREXAMCODE": 1,

      "BAREXAMNAME": "生化",

      "BARCOLOR": "8454143",

      "BARSECONDCOLOR": "16777215",

      "BARPREPART": "01",

      "BARSINGLE": 0,

      "BARPRIORITY": "1",

      "BARLENGTH": 10,

      "BARADDITIVE": "肝素锂抗凝管",

      "BARCUBAGE": "5ml",

      "BARRANGE": "肝素锂抗凝管",

      "BARDESCRIBE": "生化室",

      "EXAMGROUPCODE": "1",

      "SAMPLECODE": "1",

      "CHECKSTATUS": "1",

      "BARSECPREPART": "",

      "PRINTORDER": "",

      "ZYPRINTCOUNT": 1,

      "MZPRINTCOUNT": 1,

      "TJPRINTCOUNT": 1,

      "MATEXECDEPT": null,

      "SUBMISSIONPLACE": null,

      "HOSPITALCODE": "9999",

      "BARNOTICE": "混匀5次",

      "BARGENRULE": "QZ+LSH",

      "HOSPBARPRE": "SQ",

      "IMAGEURL": "orange.png",

      "COLLECTNOTICE": null,

      "EXAMGROUPNAME": "1",

      "SAMPLENAME": null

    },

    {

      "BAREXAMCODE": 2,

      "BAREXAMNAME": "免疫",

      "BARCOLOR": "255",

      "BARSECONDCOLOR": "255",

      "BARPREPART": "02",

      "BARSINGLE": 0,

      "BARPRIORITY": "2",

      "BARLENGTH": 12,

      "BARADDITIVE": "肝素锂抗凝管",

      "BARCUBAGE": "5ml",

      "BARRANGE": "肝素锂抗凝管",

      "BARDESCRIBE": "免疫室",

      "EXAMGROUPCODE": "3",

      "SAMPLECODE": "1",

      "CHECKSTATUS": "1",

      "BARSECPREPART": null,

      "PRINTORDER": null,

      "ZYPRINTCOUNT": 1,

      "MZPRINTCOUNT": 1,

      "TJPRINTCOUNT": 1,

      "MATEXECDEPT": null,

      "SUBMISSIONPLACE": null,

      "HOSPITALCODE": "9999",

      "BARNOTICE": "混匀5次",

      "BARGENRULE": "QZ+LSH",

      "HOSPBARPRE": "SQ",

      "IMAGEURL": "red.png",

      "COLLECTNOTICE": null,

      "EXAMGROUPNAME": "3",

      "SAMPLENAME": null

    }

]

代码实现：

从LIS_BAREXAMCLASS获取的信息

表结构：LIS_BAREXAMCLASS\SLAVE

##### B02	添加条码分组

接口说明：条码分组信息修改

请求URL：../barexamclass/request/addbarexamclass

代码文件：winning.lis.barexamclass.service.BarExamClassService

示例URL：

http://192.168.10.73:15011/lis/barexamclass/request/addbarexamclass?barexamclassinfo={"barexamnum": "12","hospitalcode":"9999","barexamname":"测试条码分组修改数据12","samplecode":"12","examgroupcode":"3","barrange":"肝素锂抗凝管","baradditive":"肝素锂","barprepart":"cs","barsecprepart":"sh","barlength":"12","barcubage":"5ml","barpriority":"1","barnotice":"混匀5次","bargenrule":"JG+RQ+LSH","zyprintcount":"1","mzprintcount":"1",

"tjprintcount":"1","matexecdept":"2110","imageurl":"","barcolor":"1231212","barsecondcolor":"16777215","bardescribe":"检验科","barsingle":"1","hospbarpre":"ws","submissionplace":"2",

"checkstatus":"1","collectnotice":"注意空腹等","barexamcode":"95"}

原型参考：





接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：barexamclassinfo 汇总信息 类型： object | 节点名称：barexamclassinfo 汇总信息 类型： object | 节点名称：barexamclassinfo 汇总信息 类型： object | 节点名称：barexamclassinfo 汇总信息 类型： object | 节点名称：barexamclassinfo 汇总信息 类型： object |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | barexamname | 分组名称 | string | 不允许为空，如：尿尿酸 |
| 3 | samplecode | 默认标本 | string | 允许为空， 如：血 |
| 4 | examgroupcode | 检测小组 | string | 允许为空，如：1 |
| 5 | barrange | 适用范围 | string | 允许为空，如：肝素锂抗凝管 |
| 6 | baradditive | 添加剂代码 | string | 允许为空，如： |
| 7 | barprepart | 条形码前缀 | string | 允许为空，如：07 |
| 8 | barsecprepart | 第二前缀 | string | 允许为空，如： |
| 9 | barlength | 条码长度 | string | 允许为空，如：12 |
| 10 | barcubage | 采血量 | string | 允许为空，如：5ml |
| 11 | barpriority | 采血次序 | string | 允许为空，如：1 |
| 12 | barnotice | 采集事项 | string | 允许为空，如：混匀5次 |
| 13 | bargenrule | 打印规则 | string | 条码规则编码，允许为空，如：JG+RQ+LSH |
| 14 | zyprintcount | 住院打印条码份数 | string | 允许为空，如：1 |
| 15 | mzprintcount | 门诊打印条码份数 | string | 允许为空，如：1 |
| 16 | tjprintcount | 体检打印条码份数 | string | 允许为空，如：1 |
| 17 | matexecdept | 材料费执行科室 | string | 允许为空，如：2110 |
| 18 | imageurl | 试管图标相对地址 | string | 允许为空，如：red.png |
| 19 | barcolor | 颜色 | string | 允许为空，如：1 |
| 20 | bardescribe | 分组描述 | string | 允许为空，如：生化室 |
| 21 | barsingle | 绑定类型 | string | 允许为空，0:同一个类别捆绑一根条码；1:每个HIS医嘱项目单独捆绑一根条码 |
| 22 | hospbarpre | 医疗机构简码 | string | 允许为空，如：ws |
| 23 | submissionplace | 送检地点 | string | 允许为空，如：检验科 |
| 24 | checkstatus | 在采集界面默认是否勾选状态 | string | 允许为空，如：1 |
| 25 | collectnotice | 采集注意事项 | string | 允许为空，如：注意空腹等 |
| 26 | barexamnum | 条码分组编码 | string | 不允许为空，如：11 |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：barexamclassinfo 汇总信息 类型： object | 节点名称：barexamclassinfo 汇总信息 类型： object | 节点名称：barexamclassinfo 汇总信息 类型： object | 节点名称：barexamclassinfo 汇总信息 类型： object | 节点名称：barexamclassinfo 汇总信息 类型： object |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | BAREXAMCODE | 分组代码 | string | 如：1 |
| 3 | BAREXAMNAME | 分组名称 | string | 如：尿尿酸 |
| 4 | SAMPLECODE | 默认标本 | string | 如：血 |
| 5 | EXAMGROUPCODE | 检测小组 | string | 如：1 |
| 6 | BARRANGE | 适用范围 | string | 如：肝素锂抗凝管 |
| 7 | BARADDITIVE | 添加剂代码 | string | 如： |
| 8 | BARPREPART | 条形码前缀 | string | 如：07 |
| 9 | BARSECPREPART | 第二前缀 | string | 如： |
| 10 | BARLENGTH | 条码长度 | int | 如：12 |
| 11 | BARCUBAGE | 采血量 | string | 如：5ml |
| 12 | BARPRIORITY | 采血次序 | string | 如：1 |
| 13 | BARNOTICE | 采集事项 | string | 如：混匀5次 |
| 14 | BARGENRULE | 打印规则 | string | 条码规则编码，如：JG+RQ+LSH |
| 15 | ZYPRINTCOUNT | 住院打印条码份数 | int | 如：1 |
| 16 | MZPRINTCOUNT | 门诊打印条码份数 | int | 如：1 |
| 17 | TJPRINTCOUNT | 体检打印条码份数 | int | 如：1 |
| 18 | MATEXECDEPT | 材料费执行科室 | string | 如：检验科 |
| 19 | IMAGEURL | 试管图标相对地址 | string | 如：red.png |
| 20 | BARCOLOR | 颜色 | string | 如：1 |
| 21 | BARSECONDCOLOR | 第二颜色 | string | 如：16777215 |
| 22 | BARDESCRIBE | 分组描述 | string | 如：生化室 |
| 23 | BARSINGLE | 绑定类型 | int | 0:同一个类别捆绑一根条码；1:每个HIS医嘱项目单独捆绑一根条码 |
| 24 | HOSPBARPRE | 医疗机构简码 | string | 如：ws |
| 25 | SUBMISSIONPLACE | 送检地点 | string | 如：检验科 |
| 26 | CHECKSTATUS | 在采集界面默认是否勾选状态 | string | 如：1 |
| 27 | COLLECTNOTICE | 采集注意事项 | string | 如：注意空腹等 |
| 28 | PRINTORDER | 打印序号 | string | 如：1 |
| 29 | BAREXAMNUM | 条码分组编码 | string | 如：11 |



Json返回数据格式：

“barexamclassinfo”:

{

      "BAREXAMCODE": 91,

      "BAREXAMNAME": "测试条码分组",

      "BARCOLOR": "1231212",

      "BARSECONDCOLOR": "16777215",

      "BARPREPART": "cs",

      "BARSINGLE": 1,

      "BARPRIORITY": "1",

      "BARLENGTH": 12,

      "BARADDITIVE": "肝素锂",

      "BARCUBAGE": "5ml",

      "BARRANGE": "肝素锂抗凝管",

      "BARDESCRIBE": "检验科",

      "EXAMGROUPCODE": "3",

      "SAMPLECODE": "12",

      "CHECKSTATUS": "1",

      "BARSECPREPART": "sh",

      "PRINTORDER": null,

      "ZYPRINTCOUNT": 1,

      "MZPRINTCOUNT": 1,

      "TJPRINTCOUNT": 1,

      "MATEXECDEPT": "2110",

      "SUBMISSIONPLACE": "2",

      "HOSPITALCODE": "9999",

      "BARNOTICE": "混匀5次",

      "BARGENRULE": "JG RQ LSH",

      "HOSPBARPRE": "ws",

      "IMAGEURL": "",

      "COLLECTNOTICE": "注意空腹等",

      "EXAMGROUPNAME": "3",

      "SAMPLENAME": null

    }

代码实现：

查询LIS_BAREXAMCLASS，如过没有对应的条码分组，插入数据，如果有对应的条码分组，则提示前端

表结构：LIS_BAREXAMCLASS \SLAVE

##### B03	修改条码分组信息

接口说明：条码分组信息修改

请求URL：../barexamclass/request/alterbarexamclass

代码文件：winning.lis.barexamclass.service.BarExamClassService

示例URL： 

http://192.168.10.73:15011/lis/barexamclass/request/alterbarexamclass?barexamclassinfo={"barexamnum": "133332","hospitalcode":"9999","barexamname":"测试条码分组修改数据12","samplecode":"12","examgroupcode":"3","barrange":"肝素锂抗凝管","baradditive":"肝素锂","barprepart":"cs","barsecprepart":"sh","barlength":"12","barcubage":"5ml","barpriority":"1","barnotice":"混匀5次","bargenrule":"JG+RQ+LSH","zyprintcount":"1","mzprintcount":"1",

"tjprintcount":"1","matexecdept":"2110","imageurl":"","barcolor":"1231212","barsecondcolor":"16777215","bardescribe":"检验科","barsingle":"1","hospbarpre":"ws","submissionplace":"2",

"checkstatus":"1","collectnotice":"注意空腹等","barexamcode":"101"}

原型参考：





接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：barexamclassinfo 汇总信息 类型： object | 节点名称：barexamclassinfo 汇总信息 类型： object | 节点名称：barexamclassinfo 汇总信息 类型： object | 节点名称：barexamclassinfo 汇总信息 类型： object | 节点名称：barexamclassinfo 汇总信息 类型： object |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | barexamcode | 分组代码 | string | 不允许为空，如：1 |
| 3 | prebarexamname | 分组名称 | string | 不允许为空，如：尿尿酸 |
| 4 | barexamname | 修改后分组名称 | string | 不允许为空，如：尿尿酸 |
| 5 | samplecode | 修改后默认标本 | string | 允许为空， 如：血 |
| 6 | examgroupcode | 修改后检测小组 | string | 允许为空，如：1 |
| 7 | barrange | 修改后适用范围 | string | 允许为空，如：肝素锂抗凝管 |
| 8 | baradditive | 修改后添加剂代码 | string | 允许为空，如： |
| 9 | barprepart | 修改后条形码前缀 | string | 允许为空，如：07 |
| 10 | barsecprepart | 修改后第二前缀 | string | 允许为空，如： |
| 11 | barlength | 修改后条码长度 | string | 允许为空，如：12 |
| 12 | barcubage | 修改后采血量 | string | 允许为空，如：5ml |
| 13 | barpriority | 修改后采血次序 | string | 允许为空，如：1 |
| 14 | barnotice | 修改后采集事项 | string | 允许为空，如：混匀5次 |
| 15 | bargenrule | 修改后打印规则 | string | 条码规则编码，允许为空，如：JG+RQ+LSH |
| 16 | zyprintcount | 修改后住院打印条码份数 | string | 允许为空，如：1 |
| 17 | mzprintcount | 修改后门诊打印条码份数 | string | 允许为空，如：1 |
| 18 | tjprintcount | 修改后体检打印条码份数 | string | 允许为空，如：1 |
| 19 | matexecdept | 修改后材料费执行科室 | string | 允许为空，如：检验科 |
| 20 | imageurl | 修改后试管图标相对地址 | string | 允许为空，如：red.png |
| 21 | barcolor | 修改后颜色 | string | 允许为空，如：1 |
| 22 | bardescribe | 修改后分组描述 | string | 允许为空，如：生化室 |
| 23 | barsingle | 修改后绑定类型 | string | 允许为空，0:同一个类别捆绑一根条码；1:每个HIS医嘱项目单独捆绑一根条码 |
| 24 | hospbarpre | 修改后医疗机构简码 | string | 允许为空，如：ws |
| 25 | submissionplace | 修改后送检地点 | string | 允许为空，如：检验科 |
| 26 | checkstatus | 修改后在采集界面勾选状态 | string | 允许为空，如：1 |
| 27 | collectnotice | 修改后采集注意事项 | string | 允许为空，如：注意空腹等 |
| 28 | barexamnum | 条码分组代码 | string | 不允许为空，如：11 |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：barexamclassinfo 汇总信息 类型： object | 节点名称：barexamclassinfo 汇总信息 类型： object | 节点名称：barexamclassinfo 汇总信息 类型： object | 节点名称：barexamclassinfo 汇总信息 类型： object | 节点名称：barexamclassinfo 汇总信息 类型： object |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | BAREXAMCODE | 分组代码 | string | 如：1 |
| 3 | BAREXAMNAME | 分组名称 | string | 如：尿尿酸 |
| 4 | SAMPLECODE | 默认标本 | string | 如：血 |
| 5 | EXAMGROUPCODE | 检测小组 | string | 如：1 |
| 6 | BARRANGE | 适用范围 | string | 如：肝素锂抗凝管 |
| 7 | BARADDITIVE | 添加剂代码 | string | 如： |
| 8 | BARPREPART | 条形码前缀 | string | 如：07 |
| 9 | BARSECPREPART | 第二前缀 | string | 如： |
| 10 | BARLENGTH | 条码长度 | int | 如：12 |
| 11 | BARCUBAGE | 采血量 | string | 如：5ml |
| 12 | BARPRIORITY | 采血次序 | string | 如：1 |
| 13 | BARNOTICE | 采集事项 | string | 如：混匀5次 |
| 14 | BARGENRULE | 打印规则 | string | 条码规则编码，如：JG+RQ+LSH |
| 15 | ZYPRINTCOUNT | 住院打印条码份数 | int | 如：1 |
| 16 | MZPRINTCOUNT | 门诊打印条码份数 | int | 如：1 |
| 17 | TJPRINTCOUNT | 体检打印条码份数 | int | 如：1 |
| 18 | MATEXECDEPT | 材料费执行科室 | string | 如：检验科 |
| 19 | IMAGEURL | 试管图标相对地址 | string | 如：red.png |
| 20 | BARCOLOR | 颜色 | string | 如：1 |
| 21 | BARSECONDCOLOR | 第二颜色 | string | 如：16777215 |
| 22 | BARDESCRIBE | 分组描述 | string | 如：生化室 |
| 23 | BARSINGLE | 绑定类型 | int | 0:同一个类别捆绑一根条码；1:每个HIS医嘱项目单独捆绑一根条码 |
| 24 | HOSPBARPRE | 医疗机构简码 | string | 如：ws |
| 25 | SUBMISSIONPLACE | 送检地点 | string | 如：检验科 |
| 26 | CHECKSTATUS | 在采集界面默认是否勾选状态 | string | 如：1 |
| 27 | COLLECTNOTICE | 采集注意事项 | string | 如：注意空腹等 |
| 28 | PRINTORDER | 打印序号 | string | 如：1 |
| 29 | BAREXAMNUM | 条码分组编码 | string | 如：11 |



Json格式返回数据：

{

    "barexamclassinfo": [

      {

        "BAREXAMCODE": 95,

        "BAREXAMNAME": "测试条码分组修改数据12",

        "BARCOLOR": "1231212",

        "BARSECONDCOLOR": "16777215",

        "BARPREPART": "cs",

        "BARSINGLE": 1,

        "BARPRIORITY": "1",

        "BARLENGTH": 12,

        "BARADDITIVE": "肝素锂",

        "BARCUBAGE": "5ml",

        "BARRANGE": "肝素锂抗凝管",

        "BARDESCRIBE": "检验科",

        "EXAMGROUPCODE": "3",

        "SAMPLECODE": "12",

        "CHECKSTATUS": "1",

        "BARSECPREPART": "sh",

        "PRINTORDER": null,

        "ZYPRINTCOUNT": 1,

        "MZPRINTCOUNT": 1,

        "TJPRINTCOUNT": 1,

        "MATEXECDEPT": "2110",

        "SUBMISSIONPLACE": "2",

        "HOSPITALCODE": "9999",

        "BARNOTICE": "混匀5次",

        "BARGENRULE": "JG+RQ+LSH",

        "HOSPBARPRE": "ws",

        "IMAGEURL": "",

        "COLLECTNOTICE": "注意空腹等",

        "EXAMGROUPNAME": "3",

        "SAMPLENAME": null

      }

    ]

  }

代码实现：

查询LIS_BAREXAMCLASS，如过没有对应的条码分组，提示前端，如果有对应的条码分组，则更新数据获取信息

表结构：LIS_BAREXAMCLASS \SLAVE

##### B04	删除条码分组

接口说明：删除条码分组

请求URL：../barexamclassr/request/deletebarexamclass

代码文件：winning.lis.barexamclass.service BarExamClassService

示例URL：

http://192.168.10.73:15011/lis/barexamclass/request/deletebarexamclass?hospitalcode=9999&

barexamcode=83

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | barexamcode | 条形码分组代码 | string | 不允许为空，如：1 |
| 3 | barexamname | 条形码分组名称 | string | 不允许为空，如：血液一组 |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage |
| 1 | type | 消息类型 | string | SUCCESS-成功 
ERROR-系统出现异常或错误
FAILT-业务逻辑处理失败 |
| 2 | messageCode | 消息代码 | string | 用于区分消息代码 |
| 3 | message | 消息内容 | string | type=ERROR时则为系统错误消息提示内容
type=FAILT时则为业务提示信息 |
| 4 | link | URL链接 | string | 用于提示用户后可以点击查看详情信息，默认为空 |
| 5 | data | 数据集合 | object | 用于存储返回的数据 |



代码实现：

LIS_BAREXAMCLASS表中删除对应信息

表结构：LIS_BAREXAMCLASS

##### B05	添加条码分组的收费项目

接口说明：添加条码分组的收费项目

请求URL：../barexamclass/request/addhisitem

代码文件：winning.lis.barexamclass.service.BarExamClassService

示例URL： http://192.168.10.73:15011/lis/barexamclass/request/addhisitem?hospitalcode=9999&flag=1&barexamitem=[{"barexamcode": "16","lisordercode": "010089","hisordercode":"10089",

"hisordername": "肝功能三项（体检）","barsingle":""},{"barexamcode": "16","lisordercode": "01008999","hisordercode": "1008999","hisordername": "肝功能100项（体检）","barsingle":"1"}]

原型参考：

或者



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | flag | 主从配置控制 | string | 项目配置条码规则时，传0；条码规则配置项目时，传1 |
| 3 | 条码和项目信息   barexamitem    array<string> | 条码和项目信息   barexamitem    array<string> | 条码和项目信息   barexamitem    array<string> | 条码和项目信息   barexamitem    array<string> |
| 1 | barexamcode | 条形码分组代码 | string | 不允许为空，如：1 |
| 2 | lisordercode | LIS代码 | string | 不允许为空，如：1 |
| 3 | hisordercode | 医嘱项目代码 | string | 允许为空，如：尿尿酸 |
| 4 | hisordername | 医嘱项目名称 | string | 允许为空， 如：血 |
| 5 | barsingle | 单独绑定标志 | string | 允许为空，0-不单独绑定 1-单独绑定，默认为0 |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：barorderinfo 汇总信息 类型： array<object> | 节点名称：barorderinfo 汇总信息 类型： array<object> | 节点名称：barorderinfo 汇总信息 类型： array<object> | 节点名称：barorderinfo 汇总信息 类型： array<object> | 节点名称：barorderinfo 汇总信息 类型： array<object> |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | BAREXAMCODE | 分组代码 | string | 如：1 |
| 3 | BAREXAMNAME | 分组名称 | string | 如：尿尿酸 |
| 4 | BAREXAMNUM | 条码分组编码 | string | 如：11 |
| 5 | LISORDERCODE | LIS代码 | string | 如：ws |
| 6 | HISORDERCODE | 医嘱项目代码 | string | 如：检验科 |
| 7 | HISORDERNAME | 医嘱项目名称 | string | 如：1 |
| 8 | BARSINGLE | 单独绑定标志 | string | 0-不单独绑定 1-单独绑定 |
| 9 | PRINTORDER | 打印序号 | string | 如：1 |



Json返回数据格式：

{

        "barorderinfo": [

            {

                "HOSPITALCODE": "9999",

                "BAREXAMCODE": 16,

                "BAREXAMNAME": null,

                "BAREXAMNUM": null,

                "LISORDERCODE": "010089",

                "HISORDERCODE": "10089",

                "HISORDERNAME": "肝功能三项（体检）",

                "BARSINGLE": 0,

                "PRINTORDER": null

            },

            {

                "HOSPITALCODE": "9999",

                "BAREXAMCODE": 16,

                "BAREXAMNAME": null,

                "BAREXAMNUM": null,

                "LISORDERCODE": "01008999",

                "HISORDERCODE": "1008999",

                "HISORDERNAME": "肝功能100项（体检）",

                "BARSINGLE": 1,

                "PRINTORDER": null

            }

        ]

    }

代码实现：

依据lisordercode删除LIS_BARORDER中数据，重新插入新的数据

表结构：LIS_BARORDER\ LIS_BAREXAMCLASS

##### B06	删除条码分组的收费项目

接口说明：删除条码分组的收费项目

请求URL：../barexamclass/request/deletehisitem

代码文件：winning.lis.barexamclass.service.BarExamClassService

示例URL： 

原型参考：

或者



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | flag | 配置方式 | string | 项目删除条码分组，传0（lis代码相同），从条码分组删除项目，传1（条码分组代码相同，lis代码不同） |
| 3 | bareliscode     array<string>   条码分组和项目信息 | bareliscode     array<string>   条码分组和项目信息 | bareliscode     array<string>   条码分组和项目信息 | bareliscode     array<string>   条码分组和项目信息 |
| 1 | barexamcode | 条形码分组代码 | string | 不允许为空，如：1 |
| 2 | lisordercode | LIS代码 | string | 不允许为空，如：1 |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage |
| 1 | type | 消息类型 | string | SUCCESS-成功 
ERROR-系统出现异常或错误
FAILT-业务逻辑处理失败 |
| 2 | messageCode | 消息代码 | string | 用于区分消息代码 |
| 3 | message | 消息内容 | string | type=ERROR时则为系统错误消息提示内容
type=FAILT时则为业务提示信息 |
| 4 | link | URL链接 | string | 用于提示用户后可以点击查看详情信息，默认为空 |
| 5 | data | 数据集合 | object | 用于存储返回的数据 |



代码实现：

依据主键lisordercode删除LIS_BARORDER中数据，或者删除后重新插入新的数据

表结构：LIS_BARORDER

##### B07	获取收费列表的项目信息

接口说明：单击收费项目列表，获取对应列表的项目信息

请求URL：../barexamclass/request/gethisitemlist

代码文件：winning.lis.hisitem.service.HisItemService

示例URL： 

http://192.168.10.73:15011/lis/barexamclass/request/gethisitemlist?hospitalcode=9999&nodeno=HISITEM_STATUS&selectvalue=2

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | nodeno | 选项分类的nodeno | string | 不允许为空，如：HISITEM_RETURNRULE |
| 3 | selectvalue | 选择参数 | string | 不允许为空，在一级菜单“状态”下，1-全部项目、2-在用项目，3-已停用项目、4-未配置条码分组、5-未配置检验仪器、6-未配置回执单规则、7-未配置检验项目，其他菜单下为当前选中项的ID |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | LISORDERCODE | LIS收费代码 | string | 如：0509 |
| 3 | HISORDERCODE | HIS收费代码 | string | 如：509 |
| 4 | HISORDERNAME | HIS收费名称 | string | 如：入院治疗费 |
| 5 | BARCODELABELNAME | 收费简称 | string | 如：生化 |
| 6 | ITEMTYPE | HIS项目类别 | string | 0 临床项目 1 收费项目 9组合项目 |
| 7 | MEMCODE1 | 输入码一 | string | 如：SH |
| 8 | MEMCODE2 | 输入码二 | string | 如：3 |
| 9 | PRICE | 单价 | int | 如：1 |
| 10 | UNIT | 单位 | string | 如： |
| 11 | EXAMCODE | 检验分类代码 | string | 如：2 |
| 12 | BAREXAMCODE | 条形分组代码集合 | string | 如：2,4 |
| 13 | BAREXAMNUM | 条码分组编码 | string | 如：232 |
| 14 | BAREXAMNAME | 条形分组名称集合 | string | 如：生化1，生化1 |
| 15 | SELECTFLAG | 选择标识 | boolean | 如：false |
| 16 | BARSINGLE | 单独绑定标识 | boolean | 如：false |



代码实现：

根据选择的不同分类状态，获取对应的项目信息

表结构：LIS_BARORDER\ LIS_BAREXAMCLASS



##### B08	搜索收费项目

接口说明：搜索收费项目信息

请求URL：../barexamclass/request/searchhisitem

代码文件：winning.lis.hisitem.service.HisItemService

示例URL： 

http://192.168.10.73:15011/lis/barexamclass/request/searchhisitem?hospitalcode=9999&searchtext=23

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | searchtext | 搜索内容 | string | 允许为空，为空默认搜索全部 |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | LISORDERCODE | LIS收费代码 | string | 如：0509 |
| 3 | HISORDERCODE | HIS收费代码 | string | 如：509 |
| 4 | HISORDERNAME | HIS收费名称 | string | 如：入院治疗费 |
| 5 | BARCODELABELNAME | 收费简称 | string | 如：生化 |
| 6 | ITEMTYPE | HIS项目类别 | string | 0 临床项目 1 收费项目 9组合项目 |
| 7 | MEMCODE1 | 输入码一 | string | 如：SH |
| 8 | MEMCODE2 | 输入码二 | string | 如：3 |
| 9 | PRICE | 单价 | int | 如：1 |
| 10 | UNIT | 单位 | string | 如： |
| 11 | EXAMCODE | 检验分类代码 | string | 如：2 |
| 12 | BAREXAMCODE | 条形分组代码集合 | string | 如：2,4 |
| 13 | BAREXAMNUM | 条码分组编码 | string | 如：232 |
| 14 | BAREXAMNAME | 条形分组名称集合 | string | 如：生化1，生化1 |
| 15 | SELECTFLAG | 选择标识 | boolean | 如：false |
| 16 | BARSINGLE | 单独绑定标识 | boolean | 如：false |



##### B09	条码单独绑定

接口说明：收费项目条码单独绑定

请求URL：../barexamclass/request/barsinglehisitem

代码文件：winning.lis.hisitem.service.HisItemService

示例URL： 

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | barexamcode | 条码分组代码 | string | 不允许为空，如：021 |
| 3 | lisordercode | lis代码 | string | 不允许为空，如：021 |
| 4 | barsingle | 单独绑定标志 | string | 不允许为空，0-不单独绑定 1-单独绑定 |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage |
| 1 | type | 消息类型 | string | SUCCESS-成功 
ERROR-系统出现异常或错误
FAILT-业务逻辑处理失败 |
| 2 | messageCode | 消息代码 | string | 用于区分消息代码 |
| 3 | message | 消息内容 | string | type=ERROR时则为系统错误消息提示内容
type=FAILT时则为业务提示信息 |
| 4 | link | URL链接 | string | 用于提示用户后可以点击查看详情信息，默认为空 |
| 5 | data | 数据集合 | object | 用于存储返回的数据 |



代码实现：

LIS_BARORDER表中更新对应信息

表结构：LIS_BARORDER

403 仪器收费项目维护

#### V 模块接口视图

| NO. | 接口编号 | 接口名称 | 说明 |
| --- | --- | --- | --- |
| 1 | A01 | 仪器项目筛选列表初始化
../institem/request/getinitdata | 仪器项目筛选列表初始化 |
| 2 | B01 | 获取仪器分类的项目
../institem/request/getinstitemlist | 获取仪器分类的项目 |
| 3 | B02 | 添加仪器的收费项目
../institem/request/addinstitem | 添加仪器的收费项目 |
| 4 | B03 | 删除仪器分组的收费项目
../institem/request/dropinsthisitem | 删除仪器分组的收费项目 |
| 5 | B04 | 搜索仪器分组
../institem/request/searchinstitem | 搜索仪器分组 |



#### A 对外公布方法

##### A01	仪器项目筛选列表初始化

接口说明：获取仪器项目的列表初始化信息

请求URL：../institem/request/getinitdata

代码文件：winning.lis.institem.service.InstItemService

示例URL： 

http://192.168.10.73:15011/lis/institem/request/getinitdata?hospitalcode=9999

原型参考：





接口入参： 

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码 |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | NODENO | 节点编码 | string | 如：HISITEM_STATUS |
| 2 | NODENAME | 节点名称 | string | 如：状态 |
| 3 | CHILDNODES | 子节点名称 | array<object> |  |
|  |  |  |  |  |
| 1 | CODE | 字典代码 | string | 如：1 |
| 2 | NAME | 字典名称 | string | 如：血 |
| 3 | MEMCODE1 | 输入码一 | string | 如：1 |
| 4 | MEMCODE2 | 输入码二 | string | 如：X |
| 5 | DICID | 标准编码 | string | 如：1 |
| 6 | DICTYPE | 字典分类 | string | 如：样本类型 |
| 7 | SUBSYSCODE | 子系统分类 | string | 如：LIMS |
| 8 | EXTERNCODE | 外部代码 | string | 如：10 |
| 9 | ORDERNO | 排序 | string | 如：0 |
| 10 | RESERVEFIELD1 | 扩展字段信息 | string | 如：null |



Json返回数据格式：

[

    {

      "NODENO": "INSTITEM_STATUS",

      "NODENAME": "状态",

      "CHILDNODES": [

        {

          "CODE": "1",

          "NAME": "全部",

          "DICID": "1",

          "DICTYPE": "仪器列表状态",

          "EXTERNCODE": "1",

          "MEMCODE1": "qb",

          "MEMCODE2": null,

          "SUBSYSCODE": "LIMS",

          "ORDERNO": 1,

          "RESERVEFIELD1": null

        }

      ]

    },

    {

      "NODENO": "INSTITEM_EXECDEPT",

      "NODENAME": "专业组",

      "CHILDNODES": [

        {

          "CODE": "1",

          "NAME": "生化室",

          "DICID": null,

          "DICTYPE": "检测小组",

          "EXTERNCODE": null,

          "MEMCODE1": "1",

          "MEMCODE2": "shs",

          "SUBSYSCODE": "LIMS",

          "ORDERNO": 0,

          "RESERVEFIELD1": null

        }

      ]

    },

    {

      "NODENO": "INSTITEM_EXECDEPT",

      "NODENAME": "检验科室",

      "CHILDNODES": [

        {

          "HOSPITALCODE": "9999",

          "EXTERNCODE": "2110",

          "MEMCODE1": "jyk",

          "MEMCODE2": "sct",

          "SUBSYSCODE": "LIMS",

          "DICID": "2110",

          "CODE": "2110",

          "NAME": "医学检验科",

          "DICTYPE": "3",

          "ORDERNO": ""

        }

      ]

    },

    {

      "NODENO": "INSTITEM_EXAMINE",

      "NODENAME": "检验分类",

      "CHILDNODES": [

        {

          "CODE": "1",

          "NAME": "生化",

          "DICID": "1",

          "DICTYPE": "检验分类",

          "EXTERNCODE": "1",

          "MEMCODE1": "1",

          "MEMCODE2": "TGDWXN",

          "SUBSYSCODE": "LIMS",

          "ORDERNO": null,

          "RESERVEFIELD1": null

        }

      ]

    }

  ]

代码实现：

从SLAVE_P中获取分类，SLAVE\SYS_DEPT获取对应的分组信息

表结构：SLAVE_P\SLAVE\SYS_DEPT

##### A02 获取收费筛选列表

接口说明：获取收费筛选列表

请求URL：../institem/request/getstatuslist

代码文件：winning.lis.institem.service.InstItemService

示例URL： 

http://192.168.10.73:15011/lis/institem/request/getstatuslist?hospitalcode=9999&subsyscode=LIMS

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码 |
| 2 | subsyscode | 子系统代码 | string | 传入子系统代码，允许为空
传入为空时默认为LIMS |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | NODENO | 节点编码 | string | 如：HISITEM_STATUS |
| 2 | NODENAME | 节点名称 | string | 如：状态 |
| 3 | CHILDNODES | 子节点名称 | array<object> |  |
|  |  |  |  |  |
| 1 | CODE | 字典代码 | string | 如：1 |
| 2 | NAME | 字典名称 | string | 如：生化 |
| 3 | DICID | 标准编码 | string | 如：SH |
| 4 | MEMCODE1 | 输入码一 | string | 如：SH |
| 5 | MEMCODE2 | 输入码二 | string | 如：3 |
| 6 | ORDERNO | 排序 | int | 如：1 |
|  |  |  |  |  |
| 1 | NODENO | 节点编码 | string | 如：HISITEM_EXAMINE |
| 2 | NODENAME | 节点名称 | string | 如：检验分类 |
| 3 | CHILDNODES | 子节点名称 | array<object> |  |
|  |  |  |  |  |
| 1 | CODE | 字典代码 | string | 如：1 |
| 2 | NAME | 字典名称 | string | 如：生化 |
| 3 | DICID | 标准编码 | string | 如：SH |
| 4 | MEMCODE1 | 输入码一 | string | 如：SH |
| 5 | MEMCODE2 | 输入码二 | string | 如：3 |
| 6 | ORDERNO | 排序 | int | 如：1 |
|  |  |  |  |  |
| 1 | NODEID | 节点编码 | string | 如：HISITEM_INSTORDER |
| 2 | NODENAME | 节点名称 | string | 如：仪器分类 |
| 3 | CHILDNODES | 子节点名称 | array<object> |  |
|  |  |  |  |  |
| 1 | INSTID | 仪器ID | string | 如：3，对应INSTID |
| 2 | INSTCODE | 仪器编码 | string | 如：HX-21，INSTCODE |
| 3 | INSTNAME | 仪器名称 | string | 如：HX-21，INSTNAME |
| 4 | EXECGROUPCODE | 执行小组代码 | string | 如：SH |
| 5 | EXECGROUPNAME | 执行小组名称 | string | 如：3 |
|  |  |  |  |  |
| 1 | NODEID | 节点编码 | string | 如：HISITEM_BARORDER |
| 2 | NODENAME | 节点名称 | string | 如：条码分组 |
| 3 | CHILDNODES | 子节点名称 | array<object> |  |
|  |  |  |  |  |
| 1 | CODE | 条形码分组代码 | string | 如：4，BAREXAMCODE |
| 2 | NAME | 条形码分组名称 | string | 如：体液（尿），BAREXAMNAME |
| 3 | DICID | 条码分组编码 | string | 如：2，BAREXAMCODE |
| 4 | MEMCODE1 | 输入码一 | string | 如：SH |
| 5 | MEMCODE2 | 输入码二 | string | 如：3 |
| 6 | ORDERNO | 排序 | int | 如：1，BARPRIORITY |
|  |  |  |  |  |
| 1 | NODEID | 节点编码 | string | 如：HISITEM_RETURNRULE |
| 2 | NODENAME | 节点名称 | string | 如：回执单规则 |
| 3 | CHILDNODES | 子节点名称 | array<object> |  |
|  |  |  |  |  |
| 1 | CODE | 规则ID | string | 如：23，RULENO |
| 2 | NAME | 规则名称 | string | 如：23，RULENAME |
| 3 | DICID | 条码分组编码 | string | 如：2，RULENO |
| 4 | MEMCODE1 | 输入码一 | string | 如：333 |
| 5 | MEMCODE2 | 输入码二 | string | 如：2 |
| 6 | ORDERNO | 排序 | string | 如：1 |





#### B 业务类

##### B01	获取仪器分类的列表

接口说明：获取仪器分类列表的项目列表

请求URL：../institem/request/getinstitemlist

代码文件：winning.lis.institem.service.InstItemService

示例URL： 

http://192.168.10.73:15011/lis/institem/request/getinstitemlist?hospitalcode=9999&nodeno=INSTITEM_STATUS&selectvalue=1

原型参考：





接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | nodeno | 选项ID | string | 不允许为空，如：INSTITEM_EXAMINE |
| 3 | selectvalue | 选择参数 | string | 不允许为空，在一级菜单“状态”下，1-全部项目、2-在用项目，3-已停用项目，其他菜单下为当前选中项的code |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：institemlist 汇总信息 类型： array<object> | 节点名称：institemlist 汇总信息 类型： array<object> | 节点名称：institemlist 汇总信息 类型： array<object> | 节点名称：institemlist 汇总信息 类型： array<object> | 节点名称：institemlist 汇总信息 类型： array<object> |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | INSTID | 仪器ID | int | 如：3 |
| 3 | INSTCODE | 仪器编码 | string | 如：HX-21 |
| 4 | INSTNAME | 仪器名称 | string | 如：HX-21 |
| 5 | DEPTNO | 部门代码 | string | 如：2110 |
| 6 | DEPTNAME | 部门名称 | string | 如：医学检验科 |
| 7 | INSTGROUP | 检验小组代码 | string | 如：1 |
| 8 | INSTGROUPNAME | 检验小组名称 | string | 如：生化室 |
| 9 | PRIORITY | 优先级 | string | 如：1 |
| 10 | EXAMCODE | 检验分类 | int | 如：10 |
| 11 | EXAMNAME | 检验分类名称 | string | 如：微生物 |
| 12 | COMPUTER_NAME | 主机名 | string | 如：ZHUMX |
| 13 | INSTTYPE | 仪器类型 | string | 如：CG |
| 14 | STOPFLAG | 停用标志 | string | 如：1 |



Json返回数据格式：

{

    "institemlist": [

      {

        "INSTID": 3,

        "DEPTNO": "2110",

        "INSTCODE": "HX-21",

        "INSTNAME": "HX-21",

        "COMPUTER_NAME": "ZHUMX                         ",

        "EXAMCODE": 10,

        "PRIORITY": null,

        "INSTGROUP": null,

        "STOPFLAG": "0",

        "HOSPITALCODE": "9999",

        "INSTTYPE": "CG",

        "INSTGROUPNAME": null,

        "EXAMNAME": "微生物",

        "DEPTNAME": "医学检验科"

      } }]

代码实现：

LIS_INSTRUMENT表中获取信息，SLAVE获取检验小组和检验分类，SYS_DEPT获取科室名称

表结构：LIS_INSTRUMENT\SLAVE\SYS_DEPT

##### B02	添加仪器的收费项目

接口说明： 添加仪器的收费项目

请求URL：../institem/request/addinstitem

代码文件：winning.lis.institem.service.InstItemService

示例URL： http://192.168.10.73:15011/lis/institem/request/addinstitem?hospitalcode=9999&institemlist=[{     "instid": "1",     "lisordercode": "01007",     "hisordercode": "1007",     "hisordername": "12" }]

原型参考：



或者



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | flag | 仪器收费项目配置方式 | string | 不允许为空，项目配置仪器时，传0；仪器配置项目时，传1 |
| 仪器项目集合 institemlist 类型 array<String> | 仪器项目集合 institemlist 类型 array<String> | 仪器项目集合 institemlist 类型 array<String> | 仪器项目集合 institemlist 类型 array<String> | 仪器项目集合 institemlist 类型 array<String> |
| 1 | instid | 仪器ID | string | 不允许为空，如：1 |
| 2 | lisordercode | LIS代码 | string | 不允许为空，如：01095 |
| 3 | hisordercode | 医嘱项目代码 | string | 允许为空，如：1095 |
| 4 | hisordername | 医嘱项目名称 | string | 允许为空，如：尿尿酸 |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：institemlist 汇总信息 类型： array<object> | 节点名称：institemlist 汇总信息 类型： array<object> | 节点名称：institemlist 汇总信息 类型： array<object> | 节点名称：institemlist 汇总信息 类型： array<object> | 节点名称：institemlist 汇总信息 类型： array<object> |
| 1 | INSTID | 仪器代码 | string | 如：1 |
| 2 | INSTNAME | 仪器名称 | string | 如：au5800 |
| 3 | INSTCODE | 仪器编码 | string | 如：au5800 |
| 4 | LISORDERCODE | LIS代码 | string | 如：0121001 |
| 5 | HISORDERCODE | 医嘱项目代码 | string | 如：1 |
| 6 | HISORDERNAME | 医嘱项目名称 | string | 如：肝素锂抗凝管 |



Json返回数据格式：

{

    "institemlist": [

      {

        "INSTID": 1,

        "LISORDERCODE": "01007",

        "HISORDERCODE": "1007",

        "HISORDERNAME": "12",

        "INSTCODE": null,

        "INSTNAME": null

      }

    ]

  }

代码实现：

依据lisordercode删除LIS_INSTORDER表中数据，然后插入新的数据

表LIS_INSTRUMENT获取仪器编码，slave获取标本名称

表结构：LIS_INSTORDER \SLAVE\LIS_INSTRUMENT

##### B03 删除仪器分组的收费项目

接口说明：删除仪器分组的收费项目

请求URL：../institem/request/dropinsthisitem

代码文件：winning.lis.institem.service.InstItemService

示例URL： 

http://192.168.10.73:15011/lis/institem/request/dropinsthisitem?hospitalcode=9999&flag=0&instliscode=[{"lisordercode": "0155","instid": "7"}]

原型参考：

或者



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | flag | 配置方式 | string | 项目删除仪器，传0（lis代码相同），从仪器分组删除项目，传1（仪器ID相同，lis代码不同） |
| 3 | instliscode     array<string>   仪器分组和项目信息 | instliscode     array<string>   仪器分组和项目信息 | instliscode     array<string>   仪器分组和项目信息 | instliscode     array<string>   仪器分组和项目信息 |
| 1 | lisordercode | LIS代码 | string | 不允许为空，如：01095 |
| 2 | instid | 仪器ID | string | 不允许为空，如：1 |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage |
| 1 | type | 消息类型 | string | SUCCESS-成功 
ERROR-系统出现异常或错误
FAILT-业务逻辑处理失败 |
| 2 | messageCode | 消息代码 | string | 用于区分消息代码 |
| 3 | message | 消息内容 | string | type=ERROR时则为系统错误消息提示内容
type=FAILT时则为业务提示信息 |
| 4 | link | URL链接 | string | 用于提示用户后可以点击查看详情信息，默认为空 |
| 5 | data | 数据集合 | object | 用于存储返回的数据 |



代码实现：

LIS_INSTORDER表中删除对应信息

表结构：LIS_INSTORDER

##### B04	搜索仪器分组

接口说明：搜索仪器分组

请求URL：../institem/request/searchinstitem

代码文件：winning.lis. institem.service.InstItemService

示例URL： http://192.168.10.73:15011/lis/institem/request/searchinstitem?hospitalcode=9999&searchtext=10

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | searchtext | 搜索内容 | string | 允许为空 ，为空，默认获取全部 |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：institemlist 汇总信息 类型： array<object> | 节点名称：institemlist 汇总信息 类型： array<object> | 节点名称：institemlist 汇总信息 类型： array<object> | 节点名称：institemlist 汇总信息 类型： array<object> | 节点名称：institemlist 汇总信息 类型： array<object> |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | INSTID | 仪器ID | int | 如：3 |
| 3 | INSTCODE | 仪器编码 | string | 如：HX-21 |
| 4 | INSTNAME | 仪器名称 | string | 如：HX-21 |
| 5 | DEPTNO | 部门代码 | string | 如：2110 |
| 6 | DEPTNAME | 部门名称 | string | 如：医学检验科 |
| 7 | INSTGROUP | 检验小组代码 | string | 如：1 |
| 8 | INSTGROUPNAME | 检验小组名称 | string | 如：生化室 |
| 9 | PRIORITY | 优先级 | string | 如：1 |
| 10 | EXAMCODE | 检验分类 | int | 如：10 |
| 11 | EXAMNAME | 检验分类名称 | string | 如：微生物 |
| 12 | COMPUTER_NAME | 主机名 | string | 如：ZHUMX |
| 13 | INSTTYPE | 仪器类型 | string | 如：CG |
| 14 | STOPFLAG | 停用标志 | string | 如：1 |



Json返回数据格式：

{

    "institemlist": [

      {

        "INSTID": 10000,

        "DEPTNO": "2110",

        "INSTCODE": "13",

        "INSTNAME": "血型检查",

        "COMPUTER_NAME": "1-PC                          ",

        "EXAMCODE": 13,

        "PRIORITY": null,

        "INSTGROUP": null,

        "STOPFLAG": "0",

        "HOSPITALCODE": "9999",

        "INSTTYPE": "CG",

        "INSTGROUPNAME": "生化室",

        "EXAMNAME": "血型检查",

        "DEPTNAME": "医学检验科"

      }

    ]

  }

代码实现：

LIS_INSTRUMENT表中获取信息，SLAVE获取检验小组和检验分类，SYS_DEPT获取科室名称

表结构：LIS_INSTRUMENT\SLAVE\SYS_DEPT

##### B05	获取收费列表的项目信息

接口说明：单击收费项目列表，获取对应列表的项目信息

请求URL：../institem/request/gethisitemlist

代码文件：winning.lis.institem.service.InstItemService

示例URL： 

http://192.168.10.73:15011/lis/institem/request/gethisitemlist?hospitalcode=9999&nodeno=HISITEM_STATUS&selectvalue=2

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | nodeno | 选项分类的nodeno | string | 不允许为空，如：HISITEM_RETURNRULE |
| 3 | selectvalue | 选择参数 | string | 不允许为空，在一级菜单“状态”下，1-全部项目、2-在用项目，3-已停用项目、4-未配置条码分组、5-未配置检验仪器、6-未配置回执单规则、7-未配置检验项目，其他菜单下为当前选中项的ID |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | LISORDERCODE | LIS收费代码 | string | 如：0509 |
| 3 | HISORDERCODE | HIS收费代码 | string | 如：509 |
| 4 | HISORDERNAME | HIS收费名称 | string | 如：入院治疗费 |
| 5 | BARCODELABELNAME | 收费简称 | string | 如：生化 |
| 6 | ITEMTYPE | HIS项目类别 | string | 0 临床项目 1 收费项目 9组合项目 |
| 7 | MEMCODE1 | 输入码一 | string | 如：SH |
| 8 | MEMCODE2 | 输入码二 | string | 如：3 |
| 9 | PRICE | 单价 | int | 如：1 |
| 10 | UNIT | 单位 | string | 如： |
| 11 | EXAMCODE | 检验分类代码 | string | 如：2 |
| 12 | INSTID | 仪器代码集合 | string | 如： 2,4 |
| 13 | INSTNAME | 仪器名称集合 | string | 如：AU5800，FT430 |
| 14 | SELECTFLAG | 选择标识 | boolean | 如：false |



##### B06	搜索收费项目

接口说明：搜索收费项目信息

请求URL：../institem/request/searchhisitem

代码文件：winning.lis.institem.service.InstItemService

示例URL： 

http://192.168.10.73:15011/lis/institem/request/searchhisitem?hospitalcode=9999&searchtext=23

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | searchtext | 搜索内容 | string | 允许为空，为空默认搜索全部 |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | LISORDERCODE | LIS收费代码 | string | 如：0509 |
| 3 | HISORDERCODE | HIS收费代码 | string | 如：509 |
| 4 | HISORDERNAME | HIS收费名称 | string | 如：入院治疗费 |
| 5 | BARCODELABELNAME | 收费简称 | string | 如：生化 |
| 6 | ITEMTYPE | HIS项目类别 | string | 0 临床项目 1 收费项目 9组合项目 |
| 7 | MEMCODE1 | 输入码一 | string | 如：SH |
| 8 | MEMCODE2 | 输入码二 | string | 如：3 |
| 9 | PRICE | 单价 | int | 如：1 |
| 10 | UNIT | 单位 | string | 如： |
| 11 | EXAMCODE | 检验分类代码 | string | 如：2 |
| 12 | INSTID | 仪器代码集合 | string | 如： 2,4 |
| 13 | INSTNAME | 仪器名称集合 | string | 如：AU5800，FT430 |
| 14 | SELECTFLAG | 选择标识 | boolean | 如：false |



### 回执单收费项目维护

#### V 模块接口视图

| NO. | 接口编号 | 接口名称 | 说明 |
| --- | --- | --- | --- |
| 1 | A01 | 回执单数据初始化
../returnrule/request/getinitdata | 请求回执单界面报告天数、周末顺延、采血点初始化数据 |
| 2 | B01 | 获取回执单列表
../returnrule/request/getreturnrulelist | 获取回执单列表 |
| 3 | B02 | 添加和修改回执单规则及明细
../returnrule/request/addreturnrule | 添加回执单规则 |
| 4 | B03 | 删除回执单规则
../returnrule/request/dropreturnrule | 删除回执单规则 |
| 5 | B04 | 删除回执单规则明细
../returnrule/request/dropreturnruledetail | 删除回执单规则明细 |
| 6 | B05 | 添加回执单的收费项目
../returnrule/request/addreturnrulehisitem | 添加回执单的收费项目 |
| 7 | B06 | 删除回执单的收费项目
../returnrule/request/dropreturnrulehisitem | 删除回执单的收费项目 |
| 8 | B07 | 修改回执单规则
../returnrule/request/alterreturnrule | 修改回执单规则 |



#### A 对外公布方法

##### A01	回执单数据初始化

接口说明：请求回执单界面报告天数、周末顺延、采血点初始化数据

请求URL：../returnrule/request/getinitdata

代码文件：winning.lis.returnrule.service.ReturnRuleService

示例URL： http://192.168.10.73:15011/lis/returnrule/request/getinitdata?hospitalcode=9999

原型参考：



接口入参： 

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称： reportdatelist 汇总信息 类型：array< object> 报告天数 | 节点名称： reportdatelist 汇总信息 类型：array< object> 报告天数 | 节点名称： reportdatelist 汇总信息 类型：array< object> 报告天数 | 节点名称： reportdatelist 汇总信息 类型：array< object> 报告天数 | 节点名称： reportdatelist 汇总信息 类型：array< object> 报告天数 |
| 1 | CODE | 字典代码 | string | 如：1 |
| 2 | NAME | 字典名称 | string | 如：血 |
| 3 | MEMCODE1 | 输入码一 | string | 如：1 |
| 4 | MEMCODE2 | 输入码二 | string | 如：X |
| 5 | DICID | 标准编码 | string | 如：1 |
| 6 | DICTYPE | 字典分类 | string | 如：样本类型 |
| 7 | SUBSYSCODE | 子系统分类 | string | 如：LIMS |
| 8 | EXTERNCODE | 外部代码 | string | 如：10 |
| 9 | ORDERNO | 排序 | string | 如：0 |
| 10 | RESERVEFIELD1 | 扩展字段信息 | string | 如：null |
| 节点名称：skipweekendlist 汇总信息 类型：array< object> 周末顺延 | 节点名称：skipweekendlist 汇总信息 类型：array< object> 周末顺延 | 节点名称：skipweekendlist 汇总信息 类型：array< object> 周末顺延 | 节点名称：skipweekendlist 汇总信息 类型：array< object> 周末顺延 | 节点名称：skipweekendlist 汇总信息 类型：array< object> 周末顺延 |
| 1 | CODE | 字典代码 | string | 如：1 |
| 2 | NAME | 字典名称 | string | 如：血 |
| 3 | MEMCODE1 | 输入码一 | string | 如：1 |
| 4 | MEMCODE2 | 输入码二 | string | 如：X |
| 5 | DICID | 标准编码 | string | 如：1 |
| 6 | DICTYPE | 字典分类 | string | 如：样本类型 |
| 7 | SUBSYSCODE | 子系统分类 | string | 如：LIMS |
| 8 | EXTERNCODE | 外部代码 | string | 如：10 |
| 9 | ORDERNO | 排序 | string | 如：0 |
| 10 | RESERVEFIELD1 | 扩展字段信息 | string | 如：null |
| 节点名称：sampleplace 汇总信息 类型：array< object> 采血点 | 节点名称：sampleplace 汇总信息 类型：array< object> 采血点 | 节点名称：sampleplace 汇总信息 类型：array< object> 采血点 | 节点名称：sampleplace 汇总信息 类型：array< object> 采血点 | 节点名称：sampleplace 汇总信息 类型：array< object> 采血点 |
| 1 | CODE | 字典代码 | string | 如：1 |
| 2 | NAME | 字典名称 | string | 如：血 |
| 3 | MEMCODE1 | 输入码一 | string | 如：1 |
| 4 | MEMCODE2 | 输入码二 | string | 如：X |
| 5 | DICID | 标准编码 | string | 如：1 |
| 6 | DICTYPE | 字典分类 | string | 如：样本类型 |
| 7 | SUBSYSCODE | 子系统分类 | string | 如：LIMS |
| 8 | EXTERNCODE | 外部代码 | string | 如：10 |
| 9 | ORDERNO | 排序 | string | 如：0 |
| 10 | RESERVEFIELD1 | 扩展字段信息 | string | 如：null |



Json返回数据格式：

{

    "reportdatelist": [

      {

        "CODE": "0",

        "NAME": "当天",

        "DICID": "0",

        "DICTYPE": "报告时间周期",

        "EXTERNCODE": "0",

        "MEMCODE1": "0",

        "MEMCODE2": "",

        "SUBSYSCODE": "LIMS",

        "ORDERNO": 1,

        "RESERVEFIELD1": null

      }

    ],

    "skipweekendlist": null,

    "sampleplace": null

  }

代码实现：

从SLAVE、 SLAVE_P获取的信息

表结构：SLAVE\SLAVE_P

##### A02 获取收费筛选列表

接口说明：获取收费筛选列表

请求URL：../returnrule/request/getstatuslist

代码文件：winning.lis.returnrule.service.ReturnRuleService

示例URL： 

http://192.168.10.73:15011/lis/returnrule/request/getstatuslist?hospitalcode=9999&subsyscode=LIMS

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码 |
| 2 | subsyscode | 子系统代码 | string | 传入子系统代码，允许为空
传入为空时默认为LIMS |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | NODENO | 节点编码 | string | 如：HISITEM_STATUS |
| 2 | NODENAME | 节点名称 | string | 如：状态 |
| 3 | CHILDNODES | 子节点名称 | array<object> |  |
|  |  |  |  |  |
| 1 | CODE | 字典代码 | string | 如：1 |
| 2 | NAME | 字典名称 | string | 如：生化 |
| 3 | DICID | 标准编码 | string | 如：SH |
| 4 | MEMCODE1 | 输入码一 | string | 如：SH |
| 5 | MEMCODE2 | 输入码二 | string | 如：3 |
| 6 | ORDERNO | 排序 | int | 如：1 |
|  |  |  |  |  |
| 1 | NODENO | 节点编码 | string | 如：HISITEM_EXAMINE |
| 2 | NODENAME | 节点名称 | string | 如：检验分类 |
| 3 | CHILDNODES | 子节点名称 | array<object> |  |
|  |  |  |  |  |
| 1 | CODE | 字典代码 | string | 如：1 |
| 2 | NAME | 字典名称 | string | 如：生化 |
| 3 | DICID | 标准编码 | string | 如：SH |
| 4 | MEMCODE1 | 输入码一 | string | 如：SH |
| 5 | MEMCODE2 | 输入码二 | string | 如：3 |
| 6 | ORDERNO | 排序 | int | 如：1 |
|  |  |  |  |  |
| 1 | NODEID | 节点编码 | string | 如：HISITEM_INSTORDER |
| 2 | NODENAME | 节点名称 | string | 如：仪器分类 |
| 3 | CHILDNODES | 子节点名称 | array<object> |  |
|  |  |  |  |  |
| 1 | INSTID | 仪器ID | string | 如：3，对应INSTID |
| 2 | INSTCODE | 仪器编码 | string | 如：HX-21，INSTCODE |
| 3 | INSTNAME | 仪器名称 | string | 如：HX-21，INSTNAME |
| 4 | EXECGROUPCODE | 执行小组代码 | string | 如：SH |
| 5 | EXECGROUPNAME | 执行小组名称 | string | 如：3 |
|  |  |  |  |  |
| 1 | NODEID | 节点编码 | string | 如：HISITEM_BARORDER |
| 2 | NODENAME | 节点名称 | string | 如：条码分组 |
| 3 | CHILDNODES | 子节点名称 | array<object> |  |
|  |  |  |  |  |
| 1 | CODE | 条形码分组代码 | string | 如：4，BAREXAMCODE |
| 2 | NAME | 条形码分组名称 | string | 如：体液（尿），BAREXAMNAME |
| 3 | DICID | 条码分组编码 | string | 如：2，BAREXAMCODE |
| 4 | MEMCODE1 | 输入码一 | string | 如：SH |
| 5 | MEMCODE2 | 输入码二 | string | 如：3 |
| 6 | ORDERNO | 排序 | int | 如：1，BARPRIORITY |
|  |  |  |  |  |
| 1 | NODEID | 节点编码 | string | 如：HISITEM_RETURNRULE |
| 2 | NODENAME | 节点名称 | string | 如：回执单规则 |
| 3 | CHILDNODES | 子节点名称 | array<object> |  |
|  |  |  |  |  |
| 1 | CODE | 规则ID | string | 如：23，RULENO |
| 2 | NAME | 规则名称 | string | 如：23，RULENAME |
| 3 | DICID | 条码分组编码 | string | 如：2，RULENO |
| 4 | MEMCODE1 | 输入码一 | string | 如：333 |
| 5 | MEMCODE2 | 输入码二 | string | 如：2 |
| 6 | ORDERNO | 排序 | string | 如：1 |





#### B 业务类

##### B01	获取回执单规则列表

接口说明：获取回执单规则列表

请求URL：../returnrule/request/getreturnrulelist

代码文件：winning.lis.returnrule.service.ReturnRuleService

示例URL： http://192.168.10.73:15011/lis/returnrule/request/getreturnrulelist?hospitalcode=9999

原型参考：



接口入参： 

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码 |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | HOSPITALCODE | 机构代码 | string | 如：9999 |
| 2 | RULENO | 规则ID | string | 如：1 |
| 3 | RULENAME | 规则名称 | string | 如：30分钟 |
| 4 | MEMCODE1 | 输入码一 | string | 如：12 |
| 5 | MEMCODE2 | 输入码二 | string | 如：30 |
| 6 | STOPFLAG | 停用标志 | string | 0-正常 1-停用 |
| 7 | 回执单明细：RETURNRULEDETAIL  类型 Array<Object> | 回执单明细：RETURNRULEDETAIL  类型 Array<Object> | 回执单明细：RETURNRULEDETAIL  类型 Array<Object> | 回执单明细：RETURNRULEDETAIL  类型 Array<Object> |
| 1 | RULEDETAILNO | 明细ID | string | 如：3 |
| 2 | WEEKDAY | 采血时间 | string | （星期几）1,3,5 |
| 3 | WEEKDAYDESC | 采血时间描述 | string | 如：周一、三、五 |
| 4 | BEGINTIME | 开始时间（时,分秒） | string | 如：00:00 |
| 5 | ENDINTIME | 结束时间（时,分秒） | string | 如：22:00 |
| 6 | SAMPLEPLACE | 采样站点 | string | 如：3 |
| 7 | SAMPLEPLACENAME | 采样站点名称 | string | 如：社区 |
| 8 | WARDORREG | 病人类型 | string | 如：1 |
| 9 | MJZBZ | 加急标志 | string | 如：1 |
| 10 | RETURNDESC | 回执统一领取说明 | string | 如：3天后9点领取 |
| 11 | MINUTES | 报告周期 | string | 分钟 |
| 12 | REPORTDATE | 报告日期单位 | string | 分、天、 周 |
| 13 | EXAMDAYS | 检测日程 | string | 如：1,3,5 |
| 14 | SKIPWEEKEND | 周末是否顺延 | string | 如：1 |
| 15 | CXOUTTIME | 当天抽血截止时间 | string | (小时 分钟)  (小时 分钟)，截止后下一个检测日程开始算 |
| 16 | ENDRETURNDESC | 截止前领取说明 | string | 如： |
| 17 | RETURNTIMEDESC | 统一领取时间 | string | 如： |
| 18 | VALIDBEGDATE | 生效开始时间 | string | 如： |
| 19 | VALIDENDDATE | 生效结束时间 | string | 如： |
| 20 | PRINTORDER | 打印顺序 | string | 格式：A001 |
| 21 | STOPFLAG | 停用标志 | string | 0-正常 1-停用 |
| 22 | TIMEOUTDAYS | 顺延天数 | string | 如：2 |
| 23 | LEVEL | 选择级别 | string | 1—统一说明，2—当天小时后，3—报告日程 |
| 24 | RULEDESC | 规则描述 | string | 如：采血时间:星期 1,2,3,4,5,6,7 时间段: 03:07--17:07 |



Json返回数据格式：

[

    {

      "HOSPITALCODE": "9999",

      "RULENO": "1",

      "RULENAME": "测试1",

      "MEMCODE1": "测试1",

      "MEMCODE2": "测试1",

      "STOPFLAG": "0",

      "RETURNRULEDETAIL": [

        {

          "FATHERRULENO": "1",

          "RULEDETAILNO": "1",

          "WEEKDAY": "1,2,3,4,5,6,7",

          "WEEKDAYDESC": "周一、二、三、四、五、六、日",

          "BEGINTIME": "00:00",

          "ENDINTIME": "23:59",

          "SAMPLEPLACE": "",

          "SAMPLEPLACENAME": "",

          "WARDORREG": "",

          "MJZBZ": "",

          "RETURNDESC": "",

          "MINUTES": "60",

          "REPORTDATE": "10",

          "EXAMDAYS": "1,3,5",

          "SKIPWEEKEND": "2",

          "CXOUTTIME": "09:00",

          "ENDRETURNDESC": "",

          "RETURNTIMEDESC": "09:00",

          "VALIDBEGDATE": "",

          "VALIDENDDATE": "",

          "PRINTORDER": "",

          "STOPFLAG": "0",

          "TIMEOUTDAYS": ""

		   "LEVEL":"3",

		            "RULEDESC":"采血时间:星期 1,2,3,4,5,6,7 时间段: 03:07--17:07\n回执单内容描述: 1天之后,检测日程星期3\n特定开始时间:1900-01-01 00:00:00\n特定结束时间:1900-01-01 00:00:00"

        }

      ]

    }，

    {

      "HOSPITALCODE": "9999",

      "RULENO": "4",

      "RULENAME": "测试3",

      "MEMCODE1": "测试3",

      "MEMCODE2": "测试3",

      "STOPFLAG": "0",

      "RETURNRULEDETAIL": null

    }

  ]

代码实现：

LIS_RETURNRULE和LIS_RETURNRULEDETAIL表中获取信息，SLAVE获取采样站点

表结构：LIS_RETURNRULE\LIS_RETURNRULEDETAIL\SLAVE

##### B02	添加和修改回执单规则及明细

接口说明： 添加和修改回执单规则及明细

请求URL：../returnrule/request/addreturnrule

代码文件：winning.lis.reutrnrule.service.ReturnRuleService

示例URL： 

http://192.168.10.73:15011/lis/returnrule/request/addreturnrule?hospitalcode=9999&rulename=测试回执单1&ruleno=&returnruledetail={     "weekday": "1,2,3",     "weekdaydesc": "周一、周二",     "begintime": "09:00",     "endintime": "23:00",     "mjzbz": "1",     "returndesc": "123123",     "minutes": "10",     "reportdate": "10",     "returntimedesc": "09:00",     "examdays": "1,4,6",     "skipweekend": "1",     "sampleplace": "",     "validbegdate": "",     "validenddate": "",     "timeoutdays": "",     "cxouttime": "",     "ruledetailno": "" }

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | rulename | 规则名称 | string | 不允许为空，如：01095 |
| 3 | ruleno | 规则ID | string | 允许为空.为空时，则新增新的规则和规则明细数据;不为空，表示规则已存在，只需要新增明细数据 |
| 4 | 回执单明细：returnruledetail  类型 Object | 回执单明细：returnruledetail  类型 Object | 回执单明细：returnruledetail  类型 Object | 回执单明细：returnruledetail  类型 Object |
| 1 | weekday | 采血时间 | string | 不允许为空， 1,3,5 |
| 2 | weekdaydesc | 采血时间描述 | string | 允许为空，如：周一、三、五 |
| 3 | begintime | 开始时间 | string | 不允许为空，（时,分秒） |
| 4 | endintime | 结束时间 | string | 不允许为空，（时,分秒） |
| 5 | mjzbz | 加急标志 | string | 允许为空， |
| 6 | returndesc | 回执统一领取说明 | string | 允许为空， |
| 7 | minutes | 报告周期 | string | 允许为空，单位分钟 |
| 8 | reportdate | 报告日期单位 | string | 允许为空，分、天、 周 |
| 9 | returntimedesc | 统一领取时间 | string | 允许为空，如：16：00 |
| 10 | examdays | 检测日程 | string | 如：1,3,5 |
| 11 | skipweekend | 周末是否顺延 | string | 允许为空， |
| 12 | sampleplace | 采样站点 | string | 允许为空， |
| 13 | validbegdate | 生效开始时间 | string | 允许为空， |
| 14 | validenddate | 生效结束时间 | string | 允许为空， |
| 15 | timeoutdays | 顺延天数 | string | 允许为空， |
| 16 | cxouttime | 截止时间 | string | 允许为空， |
| 17 | ruledetailno | 回执单规则明细ID | string | 允许为空，为空表示添加，否则为修改明细 |



接口出参【ResposeMessage.data->array< object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | HOSPITALCODE | 机构代码 | string | 如：9999 |
| 2 | RULENO | 规则ID | string | 如：1 |
| 3 | RULENAME | 规则名称 | string | 如：30分钟 |
| 4 | MEMCODE1 | 输入码一 | string | 如：12 |
| 5 | MEMCODE2 | 输入码二 | string | 如：30 |
| 6 | STOPFLAG | 停用标志 | string | 0-正常 1-停用 |
| 7 | RETURNRULEDETAIL回执单规则明细   array<object> | RETURNRULEDETAIL回执单规则明细   array<object> | RETURNRULEDETAIL回执单规则明细   array<object> | RETURNRULEDETAIL回执单规则明细   array<object> |
| 1 | HOSPITALCODE | 机构代码 | string | 如：9999 |
| 2 | RULEDETAILNO | 明细ID | string | 如：3 |
| 3 | RULENO | 规则ID | string | 如：2 |
| 4 | WEEKDAY | 采血时间 | string | （星期几）1,3,5 |
| 5 | WEEKDAYDESC | 采血时间描述 | string | 如：周一、三、五 |
| 6 | BEGINTIME | 开始时间（时,分秒） | string | 如：00:00 |
| 7 | ENDINTIME | 结束时间（时,分秒） | string | 如：22:00 |
| 8 | SAMPLEPLACE | 采样站点 | string | 如：3 |
| 9 | SAMPLEPLACENAME | 采样站点名称 | string | 如：社区 |
| 10 | WARDORREG | 病人类型 | string | 如：1 |
| 11 | MJZBZ | 加急标志 | string | 如：1 |
| 12 | RETURNDESC | 回执统一领取说明 | string | 如：3天后9点领取 |
| 13 | MINUTES | 报告周期 | string | 分钟 |
| 14 | REPORTDATE | 报告日期单位 | string | 分、天、 周 |
| 15 | EXAMDAYS | 检测日程 | string | 如：1,3,5 |
| 16 | SKIPWEEKEND | 周末是否顺延 | string | 如：1 |
| 17 | CXOUTTIME | 当天截止时间 | string | 当天截止时间 |
| 18 | ENDRETURNDESC | 截止前领取说明 | string | 如： |
| 19 | RETURNTIMEDESC | 统一领取时间 | string | 如： |
| 20 | VALIDBEGDATE | 生效开始时间 | string | 如： |
| 21 | VALIDENDDATE | 生效结束时间 | string | 如： |
| 22 | PRINTORDER | 打印顺序 | string | 格式：A001 |
| 23 | STOPFLAG | 停用标志 | string | 0-正常 1-停用 |
| 24 | TIMEOUTDAYS | 顺延天数 | string | 如：2 |
| 25 | LEVEL | 选择级别 | string | 1—统一说明，2—当天小时后，3—报告日程 |
| 26 | RULEDESC | 规则描述 | string | 如：采血时间:星期 1,2,3,4,5,6,7 时间段: 03:07--17:07 |



Json返回数据格式：

[

    {

      "HOSPITALCODE": "9999",

      "RULENO": "12",

      "RULENAME": "测试回执单1",

      "MEMCODE1": "cshzd1",

      "MEMCODE2": "cshzd1",

      "STOPFLAG": "0",

      "RETURNRULEDETAIL": [

        {

          "FATHERRULENO": "12",

          "RULEDETAILNO": "19",

          "WEEKDAY": "1,2,3",

          "WEEKDAYDESC": "周一、周二",

          "BEGINTIME": "09:00",

          "ENDINTIME": "23:00",

          "SAMPLEPLACE": "",

          "SAMPLEPLACENAME": "",

          "WARDORREG": "",

          "MJZBZ": "1",

          "RETURNDESC": "123123",

          "MINUTES": "10",

          "REPORTDATE": "10",

          "EXAMDAYS": "1,4,6",

          "SKIPWEEKEND": "1",

          "CXOUTTIME": "",

          "ENDRETURNDESC": "",

          "RETURNTIMEDESC": "09:00",

          "VALIDBEGDATE": "1900-01-01 00:00:00",

          "VALIDENDDATE": "1900-01-01 00:00:00",

          "PRINTORDER": "",

          "STOPFLAG": "0",

          "TIMEOUTDAYS": "0"

        }

      ]

    }

  ]

代码实现：

根据ruleNo和ruleNodetail判断是添加规则和明细还是修改规则和明细 

根据添加条件判断是LIS_RETURNRULE、 LIS_RETURNRULEDETAIL插入新的规则还是更新表中对应的规则

查询获取当前规则下的所有的规则信息

自动生成规则的输入码（汉字的拼音首字母）

表结构：LIS_RETURNRULE和LIS_RETURNRULEDETAIL\SLAVE

##### B03	删除回执单规则

接口说明： 删除回执单规则

请求URL：../returnrule/request/deletereturnrule

代码文件：winning.lis.reutrnrule.service.ReturnRuleService

示例URL： http://192.168.10.73:15011/lis/returnrule/request/deletereturnrule?hospitalcode=9999&ruleno=12

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | ruleno | 规则ID | string | 不允许为空，如：1 |
| 3 | rulename | 规则名称 | string | 不允许为空，如：120分钟 |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage |
| 1 | type | 消息类型 | string | SUCCESS-成功 
ERROR-系统出现异常或错误
FAILT-业务逻辑处理失败 |
| 2 | messageCode | 消息代码 | string | 用于区分消息代码 |
| 3 | message | 消息内容 | string | type=ERROR时则为系统错误消息提示内容
type=FAILT时则为业务提示信息 |
| 4 | link | URL链接 | string | 用于提示用户后可以点击查看详情信息，默认为空 |
| 5 | data | 数据集合 | object | 用于存储返回的数据 |



代码实现：

删除LIS_RETURNRULE数据

删除表LIS_RETURNRULEDETAIL中已经存在配置的规则

表结构：LIS_RETURNRULE \ LIS_RETURNRULEDETAIL

##### B04	删除回执单规则明细

接口说明： 删除回执单规则明细

请求URL：../returnrule/request/deletereturnruledetail

代码文件：winning.lis.reutrnrule.service.ReturnRuleService

示例URL：

http://192.168.10.73:15011/lis/returnrule/request/deletereturnruledetail?hospitalcode=9999&ruledetailno=5 

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | ruledetailno | 规则明细ID | string | 不允许为空，如：1 |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage |
| 1 | type | 消息类型 | string | SUCCESS-成功 
ERROR-系统出现异常或错误
FAILT-业务逻辑处理失败 |
| 2 | messageCode | 消息代码 | string | 用于区分消息代码 |
| 3 | message | 消息内容 | string | type=ERROR时则为系统错误消息提示内容
type=FAILT时则为业务提示信息 |
| 4 | link | URL链接 | string | 用于提示用户后可以点击查看详情信息，默认为空 |
| 5 | data | 数据集合 | object | 用于存储返回的数据 |



代码实现：

删除LIS_RETURNRULEDETAIL中回执单规则明细 

表结构：LIS_RETURNRULEDETAIL

##### B05	添加回执单的收费项目

接口说明： 添加回执单的收费项目

请求URL：../returnrule/request/addhisitem

代码文件：winning.lis.reutrnrule.service.ReturnRuleService

示例URL： 

http://192.168.10.73:15011/lis/returnrule/request/addhisitem?hospitalcode=9999&ruleno=11&lisorderlist=[{     "lisordercode": "064531",     "hisordercode": "64531",     "hisordername": "凝血四项测试1" },{     "lisordercode": "069763",     "hisordercode": "69763",     "hisordername": "凝血四项测试453" }]

原型参考：



或者



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | ruleno | 规则ID | string | 不允许为空，如：1 |
| 2 | hospitalcode | 机构代码 | string | 允许为空，如：9999 |
| 3 | 项目信息 lisorderlist  类型：array<object> | 项目信息 lisorderlist  类型：array<object> | 项目信息 lisorderlist  类型：array<object> | 项目信息 lisorderlist  类型：array<object> |
|  | lisordercode | LIS代码 | string | 不允许为空，如：01095 |
|  | hisordercode | 医嘱项目代码 | string | 允许为空，如：1095 |
|  | hisordername | 医嘱项目名称 | string | 允许为空，如：尿尿酸 |



接口出参【ResposeMessage.data->array< object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | LISORDERCODE | LIS代码 | string | 如：2323 |
| 2 | HISORDERCODE | 医嘱项目代码 | string | 如：1 |
| 3 | HISORDERNAME | 医嘱项目名称 | string | 如：肝素锂抗凝管 |
| 4 | RULENO | 规则ID | string | 如：1 |
| 5 | RULENAME | 规则名称 | string | 如：1 |
| 6 | MEMCODE1 | 输入码一 | string | 如：232 |
| 7 | STOPFLAG | 停用标志 | string | 如：1 |



Json返回数据格式：

[

    {

      "RULENO": 11,

      "LISORDERCODE": "064531",

      "HISORDERCODE": "64531",

      "HISORDERNAME": "凝血四项测试1",

      "RULENAME": "测试回执单1",

      "MEMCODE1": "cshzd1",

      "STOPFLAG": "0"

    },

    {

      "RULENO": 11,

      "LISORDERCODE": "069763",

      "HISORDERCODE": "69763",

      "HISORDERNAME": "凝血四项测试453",

      "RULENAME": "测试回执单1",

      "MEMCODE1": "cshzd1",

      "STOPFLAG": "0"

    }

  ]

代码实现：

依据lisordercode删除LIS_RETURNRULEORDER表中数据，然后插入新的数据

表LIS_RETURNRULEORDER和LIS_RETURNRULE中获取数据

表结构：LIS_RETURNRULEORDER\LIS_RETURNRULE

##### B06	删除回执单的收费项目

接口说明： 删除回执单的收费项目

请求URL：../returnrule/request/deletehisitem

代码文件：winning.lis.reutrnrule.service.ReturnRuleService

示例URL： http://192.168.10.73:15011/lis/returnrule/request/deletehisitem?ruleno=11&lisordercode=064531

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | ruleno | 规则ID | string | 不允许为空，如：1 |
| 2 | lisordercode | LIS代码 | array<string> | 不允许为空，如：01095 |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage |
| 1 | type | 消息类型 | string | SUCCESS-成功 
ERROR-系统出现异常或错误
FAILT-业务逻辑处理失败 |
| 2 | messageCode | 消息代码 | string | 用于区分消息代码 |
| 3 | message | 消息内容 | string | type=ERROR时则为系统错误消息提示内容
type=FAILT时则为业务提示信息 |
| 4 | link | URL链接 | string | 用于提示用户后可以点击查看详情信息，默认为空 |
| 5 | data | 数据集合 | object | 用于存储返回的数据 |



代码实现：

依据lisordercode删除LIS_RETURNRULEORDER表中数据， 

表结构：LIS_RETURNRULEORDER

##### B07	修改回执单规则

接口说明： 修改回执单规则

请求URL：../returnrule/request/alterreturnrule

代码文件：winning.lis.reutrnrule.service.ReturnRuleService

示例URL： http://192.168.10.73:15011/lis/returnrule/request/alterreturnrule?hospitalcode=9999&ruleno=50&rulename=测试回执单规则修改

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | ruleno | 规则ID | string | 不允许为空，如：1 |
| 3 | rulename | 规则名称 | string | 不允许为空，如：20分钟 |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | RULENO | 规则ID | string | 如：2323 |
| 2 | HOSPITALCODE | 医嘱项目代码 | string | 如：1 |
| 3 | RULENAME | 规则ID名称 | string | 如：肝素锂抗凝管 |
| 4 | MEMCODE1 | 输入码一 | string | 如：1 |
| 5 | MEMCODE2 | 输入码一 | string | 如：1 |
| 6 | STOPFLAG | 停用标志 | string | 如：1 |



Json返回数据格式：

{

        "HOSPITALCODE": "9999",

        "RULENO": 50,

        "RULENAME": "测试回执单规则修改",

        "MEMCODE1": "cshzdgzxg",

        "MEMCODE2": "cshzdgzxg",

        "STOPFLAG": "0"

    }

代码实现：

更新LIS_RETURNRULE数据

获取LIS_RETURNRULEDETAIL中的规则

表结构：LIS_RETURNRULE 

##### B08	获取收费列表的项目信息

接口说明：单击收费项目列表，获取对应列表的项目信息

请求URL：../returnrule/request/gethisitemlist

代码文件：winning.lis.returnrule.service.ReturnRuleService

示例URL： 

http://192.168.10.73:15011/lis/returnrule/request/gethisitemlist?hospitalcode=9999&nodeno=HISITEM_STATUS&selectvalue=2

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | nodeno | 选项分类的nodeno | string | 不允许为空，如：HISITEM_RETURNRULE |
| 3 | selectvalue | 选择参数 | string | 不允许为空，在一级菜单“状态”下，1-全部项目、2-在用项目，3-已停用项目、4-未配置条码分组、5-未配置检验仪器、6-未配置回执单规则、7-未配置检验项目，其他菜单下为当前选中项的ID |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | LISORDERCODE | LIS收费代码 | string | 如：0509 |
| 3 | HISORDERCODE | HIS收费代码 | string | 如：509 |
| 4 | HISORDERNAME | HIS收费名称 | string | 如：入院治疗费 |
| 5 | BARCODELABELNAME | 收费简称 | string | 如：生化 |
| 6 | ITEMTYPE | HIS项目类别 | string | 0 临床项目 1 收费项目 9组合项目 |
| 7 | MEMCODE1 | 输入码一 | string | 如：SH |
| 8 | MEMCODE2 | 输入码二 | string | 如：3 |
| 9 | PRICE | 单价 | int | 如：1 |
| 10 | UNIT | 单位 | string | 如： |
| 11 | RULENO | 回执单规则ID | string | 如：2 |
| 12 | RULENAME | 回执单规则名称 | string | 如： |
| 13 | SELECTFLAG | 选择标识 | boolean | 如：false |



##### B09	搜索收费项目

接口说明：搜索收费项目信息

请求URL：../returnrule/request/searchhisitem

代码文件：winning.lis.returnrule.service.ReturnRuleService

示例URL： 

http://192.168.10.73:15011/lis/returnrule/request/searchhisitem?hospitalcode=9999&searchtext=23

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | searchtext | 搜索内容 | string | 允许为空，为空默认搜索全部 |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | LISORDERCODE | LIS收费代码 | string | 如：0509 |
| 3 | HISORDERCODE | HIS收费代码 | string | 如：509 |
| 4 | HISORDERNAME | HIS收费名称 | string | 如：入院治疗费 |
| 5 | BARCODELABELNAME | 收费简称 | string | 如：生化 |
| 6 | ITEMTYPE | HIS项目类别 | string | 0 临床项目 1 收费项目 9组合项目 |
| 7 | MEMCODE1 | 输入码一 | string | 如：SH |
| 8 | MEMCODE2 | 输入码二 | string | 如：3 |
| 9 | PRICE | 单价 | int | 如：1 |
| 10 | UNIT | 单位 | string | 如： |
| 11 | RULENO | 回执单ID集合 | string | 如：2，5 |
| 12 | RULENAME | 回执单名称集合 | string | 如：110分钟，10分钟 |
| 13 | SELECTFLAG | 选择标识 | boolean | 如：false |



### TAT规则收费项目维护

#### V 模块接口视图

| NO. | 接口编号 | 接口名称 | 说明 |
| --- | --- | --- | --- |
| 1 | A01 | TAT规则数据初始化
../reptimerule/request/getinitdata | 请求TAT规则界面报告天数、周末顺延、采血点、仪器名称、病人类型、标本类型初始化数据 |
| 2 | B01 | 获取TAT规则列表
../reptimerule/request/getreptimerulelist | 获取TAT规则列表 |
| 3 | B02 | 添加和修改TAT规则及明细
../reptimerule/request/addreptimerule | 添加TAT规则 |
| 4 | B03 | 删除TAT规则
../reptimerule/request/dropreptimerule | 删除TAT规则 |
| 5 | B04 | 修改TAT规则
../reptimerule/request/alterreptimerule | 修改TAT规则 |
| 6 | B05 | 删除TAT规则明细
../reptimerule/request/dropreptimeruledetail | 删除TAT规则明细 |
| 7 | B06 | 搜索TAT规则
../reptimerule/request/searchreptimerule | 搜索TAT规则 |
| 8 | B07 | 添加TAT规则的收费项目
../reptimerule/request/addhisitem | 添加TAT规则的收费项目 |
| 9 | B08 | 删除TAT规则的收费项目
../reptimerule/request/drophisitem | 删除TAT规则的收费项目 |



#### A 对外公布方法

##### A01	TAT规则收费项目数据初始化

接口说明：请求TAT规则界面报告天数、周末顺延、采血点、仪器名称、病人类型、标本类型初始化数据

请求URL：../reptimerule/request/getinitdata

代码文件：winning.lis.reptimerule.service.RepTimeRuleService

示例URL： http://192.168.10.73:15011/lis/reptimerule/request/getinitdata?hospitalcode=9999

原型参考：



接口入参： 

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：reportdatelist 汇总信息 类型：array< object>报告时间周期 | 节点名称：reportdatelist 汇总信息 类型：array< object>报告时间周期 | 节点名称：reportdatelist 汇总信息 类型：array< object>报告时间周期 | 节点名称：reportdatelist 汇总信息 类型：array< object>报告时间周期 | 节点名称：reportdatelist 汇总信息 类型：array< object>报告时间周期 |
| 1 | CODE | 字典代码 | string | 如：1 |
| 2 | NAME | 字典名称 | string | 如：血 |
| 3 | MEMCODE1 | 输入码一 | string | 如：1 |
| 4 | MEMCODE2 | 输入码二 | string | 如：X |
| 5 | DICID | 标准编码 | string | 如：1 |
| 6 | DICTYPE | 字典分类 | string | 如：样本类型 |
| 7 | SUBSYSCODE | 子系统分类 | string | 如：LIMS |
| 8 | EXTERNCODE | 外部代码 | string | 如：10 |
| 9 | ORDERNO | 排序 | string | 如：0 |
| 10 | RESERVEFIELD1 | 扩展字段信息 | string | 如：null |
| 节点名称：skipweekendlist 汇总信息 类型：array< object>顺延类型 | 节点名称：skipweekendlist 汇总信息 类型：array< object>顺延类型 | 节点名称：skipweekendlist 汇总信息 类型：array< object>顺延类型 | 节点名称：skipweekendlist 汇总信息 类型：array< object>顺延类型 | 节点名称：skipweekendlist 汇总信息 类型：array< object>顺延类型 |
| 1 | CODE | 字典代码 | string | 如：1 |
| 2 | NAME | 字典名称 | string | 如：血 |
| 3 | MEMCODE1 | 输入码一 | string | 如：1 |
| 4 | MEMCODE2 | 输入码二 | string | 如：X |
| 5 | DICID | 标准编码 | string | 如：1 |
| 6 | DICTYPE | 字典分类 | string | 如：样本类型 |
| 7 | SUBSYSCODE | 子系统分类 | string | 如：LIMS |
| 8 | EXTERNCODE | 外部代码 | string | 如：10 |
| 9 | ORDERNO | 排序 | string | 如：0 |
| 10 | RESERVEFIELD1 | 扩展字段信息 | string | 如：null |
| 节点名称：sampleplacelist 汇总信息 类型：array< object>采样站点 | 节点名称：sampleplacelist 汇总信息 类型：array< object>采样站点 | 节点名称：sampleplacelist 汇总信息 类型：array< object>采样站点 | 节点名称：sampleplacelist 汇总信息 类型：array< object>采样站点 | 节点名称：sampleplacelist 汇总信息 类型：array< object>采样站点 |
| 1 | CODE | 字典代码 | string | 如：1 |
| 2 | NAME | 字典名称 | string | 如：血 |
| 3 | MEMCODE1 | 输入码一 | string | 如：1 |
| 4 | MEMCODE2 | 输入码二 | string | 如：X |
| 5 | DICID | 标准编码 | string | 如：1 |
| 6 | DICTYPE | 字典分类 | string | 如：样本类型 |
| 7 | SUBSYSCODE | 子系统分类 | string | 如：LIMS |
| 8 | EXTERNCODE | 外部代码 | string | 如：10 |
| 9 | ORDERNO | 排序 | string | 如：0 |
| 10 | RESERVEFIELD1 | 扩展字段信息 | string | 如：null |
| 节点名称：instlist 汇总信息 类型：array< object> 仪器名称 | 节点名称：instlist 汇总信息 类型：array< object> 仪器名称 | 节点名称：instlist 汇总信息 类型：array< object> 仪器名称 | 节点名称：instlist 汇总信息 类型：array< object> 仪器名称 | 节点名称：instlist 汇总信息 类型：array< object> 仪器名称 |
| 1 | NODEID | 节点编码 | string | 如：HISITEM_INSTORDER |
| 2 | NODENAME | 节点名称 | string | 如：仪器分类 |
| 3 | CHILDNODES | 子节点名称 | array<object> |  |
|  |  |  |  |  |
| 1 | INSTID | 仪器ID | string | 如：3，对应INSTID |
| 2 | INSTCODE | 仪器编码 | string | 如：HX-21，INSTCODE |
| 3 | INSTNAME | 仪器名称 | string | 如：HX-21，INSTNAME |
| 4 | EXECGROUPCODE | 执行小组代码 | string | 如：SH |
| 5 | EXECGROUPNAME | 执行小组名称 | string | 如：3 |
| 节点名称：brlxlist 汇总信息 类型：array< object> 病人类型 | 节点名称：brlxlist 汇总信息 类型：array< object> 病人类型 | 节点名称：brlxlist 汇总信息 类型：array< object> 病人类型 | 节点名称：brlxlist 汇总信息 类型：array< object> 病人类型 | 节点名称：brlxlist 汇总信息 类型：array< object> 病人类型 |
| 1 | CODE | 字典代码 | string | 如：1 |
| 2 | NAME | 字典名称 | string | 如：血 |
| 3 | MEMCODE1 | 输入码一 | string | 如：1 |
| 4 | MEMCODE2 | 输入码二 | string | 如：X |
| 5 | DICID | 标准编码 | string | 如：1 |
| 6 | DICTYPE | 字典分类 | string | 如：样本类型 |
| 7 | SUBSYSCODE | 子系统分类 | string | 如：LIMS |
| 8 | EXTERNCODE | 外部代码 | string | 如：10 |
| 9 | ORDERNO | 排序 | string | 如：0 |
| 10 | RESERVEFIELD1 | 扩展字段信息 | string | 如：null |
| 节点名称：samplelist 汇总信息 类型：array< object> 标本类型 | 节点名称：samplelist 汇总信息 类型：array< object> 标本类型 | 节点名称：samplelist 汇总信息 类型：array< object> 标本类型 | 节点名称：samplelist 汇总信息 类型：array< object> 标本类型 | 节点名称：samplelist 汇总信息 类型：array< object> 标本类型 |
| 1 | CODE | 字典代码 | string | 如：1 |
| 2 | NAME | 字典名称 | string | 如：血 |
| 3 | MEMCODE1 | 输入码一 | string | 如：1 |
| 4 | MEMCODE2 | 输入码二 | string | 如：X |
| 5 | DICID | 标准编码 | string | 如：1 |
| 6 | DICTYPE | 字典分类 | string | 如：样本类型 |
| 7 | SUBSYSCODE | 子系统分类 | string | 如：LIMS |
| 8 | EXTERNCODE | 外部代码 | string | 如：10 |
| 9 | ORDERNO | 排序 | string | 如：0 |
| 10 | RESERVEFIELD1 | 扩展字段信息 | string | 如：null |
| 节点名称：sourcenodelist 汇总信息 类型：array< object> 流程节点 | 节点名称：sourcenodelist 汇总信息 类型：array< object> 流程节点 | 节点名称：sourcenodelist 汇总信息 类型：array< object> 流程节点 | 节点名称：sourcenodelist 汇总信息 类型：array< object> 流程节点 | 节点名称：sourcenodelist 汇总信息 类型：array< object> 流程节点 |
| 1 | CODE | 字典代码 | string | 如：1 |
| 2 | NAME | 字典名称 | string | 如：血 |
| 3 | MEMCODE1 | 输入码一 | string | 如：1 |
| 4 | MEMCODE2 | 输入码二 | string | 如：X |
| 5 | DICID | 标准编码 | string | 如：1 |
| 6 | DICTYPE | 字典分类 | string | 如：样本类型 |
| 7 | SUBSYSCODE | 子系统分类 | string | 如：LIMS |
| 8 | EXTERNCODE | 外部代码 | string | 如：10 |
| 9 | ORDERNO | 排序 | string | 如：0 |
| 10 | RESERVEFIELD1 | 扩展字段信息 | string | 如：null |



Json返回数据格式：

    {

      "reportdatelist": [

        {

          "CODE": "0",

          "NAME": "当天",

          "DICID": "0",

          "DICTYPE": "报告时间周期",

          "EXTERNCODE": "0",

          "MEMCODE1": "0",

          "MEMCODE2": "",

          "SUBSYSCODE": "LIMS",

          "ORDERNO": 1,

          "RESERVEFIELD1": null

        }

      ],

      "skipweekendlist": null,

      "sampleplacelist": null,

      "instlist": [

        {

          "NODENAME": "未分组",

          "CHILDNODE": [

            {

              "INSTID": 12,

              "INSTCODE": "AIA-360",

              "INSTNAME": "AIA-360",

              "EXECGROUPCODE": null,

              "EXECGROUPNAME": null

            }

          ]

       }

      ],

      "brlxlist": [

        {

          "CODE": "1",

          "NAME": "住院",

          "DICID": null,

          "DICTYPE": "病人类型",

          "EXTERNCODE": "1",

          "MEMCODE1": "1",

          "MEMCODE2": "",

          "SUBSYSCODE": "LIMS",

          "ORDERNO": 0,

          "RESERVEFIELD1": null

        }

      ],

      "samplelist": [

        {

          "CODE": "SQ",

          "NAME": "申请时间",

          "DICID": "",

          "DICTYPE": "流程节点",

          "EXTERNCODE": "",

          "MEMCODE1": "SQSJ",

          "MEMCODE2": "",

          "SUBSYSCODE": "LIMS",

          "ORDERNO": 1,

          "RESERVEFIELD1": "医嘱申请"

        }

      ]

    }

代码实现：

从SLAVE、 SLAVE_P获取的信息

表结构：SLAVE\SLAVE_P

##### A02 获取收费筛选列表

接口说明：获取收费筛选列表

请求URL：../reptimerule/request/getstatuslist

代码文件：winning.lis.reptimerule.service.RepTimeRuleService

示例URL： 

http://192.168.10.73:15011/lis/reptimerule/request/getstatuslist?hospitalcode=9999&subsyscode=LIMS

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码 |
| 2 | subsyscode | 子系统代码 | string | 传入子系统代码，允许为空
传入为空时默认为LIMS |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | NODENO | 节点编码 | string | 如：HISITEM_STATUS |
| 2 | NODENAME | 节点名称 | string | 如：状态 |
| 3 | CHILDNODES | 子节点名称 | array<object> |  |
|  |  |  |  |  |
| 1 | CODE | 字典代码 | string | 如：1 |
| 2 | NAME | 字典名称 | string | 如：生化 |
| 3 | DICID | 标准编码 | string | 如：SH |
| 4 | MEMCODE1 | 输入码一 | string | 如：SH |
| 5 | MEMCODE2 | 输入码二 | string | 如：3 |
| 6 | ORDERNO | 排序 | int | 如：1 |
|  |  |  |  |  |
| 1 | NODENO | 节点编码 | string | 如：HISITEM_EXAMINE |
| 2 | NODENAME | 节点名称 | string | 如：检验分类 |
| 3 | CHILDNODES | 子节点名称 | array<object> |  |
|  |  |  |  |  |
| 1 | CODE | 字典代码 | string | 如：1 |
| 2 | NAME | 字典名称 | string | 如：生化 |
| 3 | DICID | 标准编码 | string | 如：SH |
| 4 | MEMCODE1 | 输入码一 | string | 如：SH |
| 5 | MEMCODE2 | 输入码二 | string | 如：3 |
| 6 | ORDERNO | 排序 | int | 如：1 |
|  |  |  |  |  |
| 1 | NODEID | 节点编码 | string | 如：HISITEM_INSTORDER |
| 2 | NODENAME | 节点名称 | string | 如：仪器分类 |
| 3 | CHILDNODES | 子节点名称 | array<object> |  |
|  |  |  |  |  |
| 1 | INSTID | 仪器ID | string | 如：3，对应INSTID |
| 2 | INSTCODE | 仪器编码 | string | 如：HX-21，INSTCODE |
| 3 | INSTNAME | 仪器名称 | string | 如：HX-21，INSTNAME |
| 4 | EXECGROUPCODE | 执行小组代码 | string | 如：SH |
| 5 | EXECGROUPNAME | 执行小组名称 | string | 如：3 |
|  |  |  |  |  |
| 1 | NODEID | 节点编码 | string | 如：HISITEM_BARORDER |
| 2 | NODENAME | 节点名称 | string | 如：条码分组 |
| 3 | CHILDNODES | 子节点名称 | array<object> |  |
|  |  |  |  |  |
| 1 | CODE | 条形码分组代码 | string | 如：4，BAREXAMCODE |
| 2 | NAME | 条形码分组名称 | string | 如：体液（尿），BAREXAMNAME |
| 3 | DICID | 条码分组编码 | string | 如：2，BAREXAMCODE |
| 4 | MEMCODE1 | 输入码一 | string | 如：SH |
| 5 | MEMCODE2 | 输入码二 | string | 如：3 |
| 6 | ORDERNO | 排序 | int | 如：1，BARPRIORITY |
|  |  |  |  |  |
| 1 | NODEID | 节点编码 | string | 如：HISITEM_RETURNRULE |
| 2 | NODENAME | 节点名称 | string | 如：回执单规则 |
| 3 | CHILDNODES | 子节点名称 | array<object> |  |
|  |  |  |  |  |
| 1 | CODE | 规则ID | string | 如：23，RULENO |
| 2 | NAME | 规则名称 | string | 如：23，RULENAME |
| 3 | DICID | 条码分组编码 | string | 如：2，RULENO |
| 4 | MEMCODE1 | 输入码一 | string | 如：333 |
| 5 | MEMCODE2 | 输入码二 | string | 如：2 |
| 6 | ORDERNO | 排序 | string | 如：1 |





#### B 业务类

##### B01	获取TAT规则列表

接口说明：获取TAT规则列表

请求URL：../reptimerule/request/getreptimerulelist

代码文件：winning.lis.reptimerule.service.RepTimeRuleService

示例URL： http://192.168.10.73:15011/lis/reptimerule/request/getreptimerulelist?hospitalcode=9999

原型参考：

 或者  

接口入参： 

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码 |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | HOSPITALCODE | 机构代码 | string | 如：9999 |
| 2 | RULENO | 规则ID | string | 如：1 |
| 3 | RULENAME | 规则名称 | string | 如：30分钟 |
| 4 | MEMCODE1 | 输入码一 | string | 如：12 |
| 5 | MEMCODE2 | 输入码二 | string | 如：30 |
| 6 | STOPFLAG | 停用标志 | string | 0-正常 1-停用 |
| 7 | SOURCENODE | 参照时间 | string | CY-采样时间 BD-绑定时间 QS-签收时间 RK-入库时间 HG-护工签收 BG-报告时间 |
| 8 | SOURCENODENAME | 参照时间名称 | string | 如：采样时间 |
| 9 | TARGETNODE | 参照时间目标节点 | string | CY-采样时间 BD-绑定时间 QS-签收时间 RK-入库时间 HG-护工签收 BG-报告时间 |
| 10 | TARGETNODENAME | 参照时间目标节点名称 | string | 如：绑定时间 |
| 11 | REPTIMERULEDETAIL | TAT规则明细 | array<object> |  |
|  |  |  |  |  |
| 1 | HOSPITALCODE | 机构代码 | string | 如：9999 |
| 2 | RULEDETAILNO | 明细ID | string | 如：3 |
| 3 | FATHERRULENO | 规则ID | string | 如：2 |
| 4 | WEEKDAY | 采血时间 | string | （星期几）1,3,5 |
| 5 | WEEKDAYDESC | 采血时间描述 | string | 如：周一、三、五 |
| 6 | BEGINTIME | 开始时间（时,分秒） | string | 如：00:00 |
| 7 | ENDINTIME | 结束时间（时,分秒） | string | 如：22:00 |
| 8 | SAMPLEPLACE | 采样站点 | string | 如：3 |
| 9 | SAMPLEPLACENAME | 采样站点名称 | string | 如：社区 |
| 10 | WARDORREG | 病人类型 | string | 如：1 |
| 11 | MJZBZ | 加急标志 | string | 如：1 |
| 12 | SAMPLECODE | 标本种类 | string | 如：3 |
| 13 | SAMPLECODENAME | 标本种类名称 | string | 如：血 |
| 14 | INSTID | 仪器ID | string | 如：123 |
| 15 | RULETYPE | 规则类型 | string | 如：1 |
| 16 | NODEINTERVEL | 目标节点周期 | string | 如：3天后9点领取 |
| 17 | INTERVELUNIT | 目标节点周期单位 | string | 分钟、小时、天、周 |
| 18 | TARGETTIME | 目标节点周期 | string | 分、天、 周 |
| 19 | EXAMDAYS | 检测日程 | string | 如：1,3,5 |
| 20 | EXAMDAYSDESC | 检测日程描述 | string | 如：周一、三、五 |
| 21 | SKIPWEEKEND | 周末是否顺延 | string | 如：1 |
| 22 | NODEOUTTIME | 节点截止时间 | string | 如：09:00 |
| 23 | HINTMINUTES | 提前几分钟提醒 | string | 如：10 |
| 24 | REMARK | 备注说明 | string | 如： |
| 25 | STOPFLAG | 停用标志 | string | 0-正常 1-停用 |
| 26 | TIMEOUTDAYS | 顺延天数 | string | 如：2 |



代码实现：

LIS_RETURNRULE和LIS_RETURNRULEDETAIL表中获取信息，SLAVE获取标本种类和采样站点，参照时间节点和参照时间目的节点

表结构：LIS_RETURNRULE和LIS_RETURNRULEDETAIL\SLAVE

##### B02	添加和修改TAT规则及明细

接口说明：添加和修改TAT规则及明细

请求URL：../reptimerule/request/addreptimeruledetail

代码文件：winning.lis.reptimerule.service.RepTimeRuleService

示例URL： 

http://192.168.10.73:15011/lis/reptimerule/request/addreptimeruledetail?hospitalcode=9999&rulename=测试节点12&sourcenode=BD&targetnode=BG&ruleno=&reptimeruledetaillist={"weekday":"1、2、3", "weekdaydesc":"周一、二、三", "begintime":"00:00", "endintime":"23:59", "nodeintervel":"", "intervelunit":"", "targettime":"", "examdays":"", "examdaysdesc":"", "skipweekend":"", "nodeouttime":"", "hintminutes":"", "timeoutdays":"", "wardorreg":"", "mjzbz":"", "samplecode":"", "sampleplace":"", "instid":"","ruledetailno":"51"}

原型参考：



接口入参： 

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | ruleno | 规则ID | string | 允许为空，为空时，则新增新的规则和规则明细数据；不为空，表示规则已存在，只需要新增或者修改明细数据 |
| 3 | rulename | 规则名称 | string | 不允许为空，如：2小时 |
| 4 | sourcenode | 参照时间 | string | 不允许为空，如：BD |
| 5 | targetnode | 参照时间目标节点 | string | 不允许为空，如：CY |
| 6 | TAT明细：reptimeruledetaillist  类型 Object | TAT明细：reptimeruledetaillist  类型 Object | TAT明细：reptimeruledetaillist  类型 Object | TAT明细：reptimeruledetaillist  类型 Object |
| 1 | weekday | 采血时间 | string | 不允许为空，如：1,3,5 |
| 2 | weekdaydesc | 采血时间描述 | string | 允许为空，如：周一、三、五 |
| 3 | begintime | 开始时间（时,分秒） | string | 不允许为空，如：00:00 |
| 4 | endintime | 结束时间（时,分秒） | string | 不允许为空，如：22:00 |
| 5 | nodeintervel | 目标节点周期 | string | 允许为空，如：10 |
| 6 | intervelunit | 目标节点周期单位 | string | 允许为空，分钟、小时、天、周 |
| 7 | targettime | 目标节点周期 | string | 允许为空，分、天、 周 |
| 8 | examdays | 检测日程 | string | 允许为空，如：1,3,5 |
| 9 | examdaysdesc | 检测日程描述 | string | 允许为空，如：周一、三、五 |
| 10 | skipweekend | 周末是否顺延 | string | 允许为空，如：1 |
| 11 | nodeouttime | 节点截止时间 | string | 允许为空，如：09:00 |
| 12 | hintminutes | 提前几分钟提醒 | string | 允许为空，如：10 |
| 13 | timeoutdays | 顺延天数 | string | 允许为空，如：2 |
| 14 | wardorreg | 病人类型 | string | 允许为空，如：1 |
| 15 | mjzbz | 加急标志 | string | 允许为空，如：1 |
| 16 | samplecode | 标本种类 | string | 允许为空，如：3 |
| 17 | sampleplace | 采样站点 | string | 允许为空，如：3 |
| 18 | instid | 仪器ID | string | 允许为空，如：123 |
| 19 | ruledetailno | 明细ID | string | 可以为空，为空，表示添加明细，不为空表示修改TAT规则明细 |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | HOSPITALCODE | 机构代码 | string | 如：9999 |
| 2 | RULENO | 规则ID | string | 如：1 |
| 3 | RULENAME | 规则名称 | string | 如：30分钟 |
| 4 | MEMCODE1 | 输入码一 | string | 如：12 |
| 5 | MEMCODE2 | 输入码二 | string | 如：30 |
| 6 | STOPFLAG | 停用标志 | string | 0-正常 1-停用 |
| 7 | SOURCENODE | 参照时间 | string | CY-采样时间 BD-绑定时间 QS-签收时间 RK-入库时间 HG-护工签收 BG-报告时间 |
| 8 | TARGETNODE | 参照时间目标节点 | string | CY-采样时间 BD-绑定时间 QS-签收时间 RK-入库时间 HG-护工签收 BG-报告时间 |
| 9 | REPTIMERULEDETAIL | TAT规则明细 | array<object> |  |
|  |  |  |  |  |
| 1 | HOSPITALCODE | 机构代码 | string | 如：9999 |
| 2 | RULEDETAILNO | 明细ID | string | 如：3 |
| 3 | RULENO | 规则ID | string | 如：2 |
| 4 | WEEKDAY | 采血时间 | string | （星期几）1,3,5 |
| 5 | WEEKDAYDESC | 采血时间描述 | string | 如：周一、三、五 |
| 6 | BEGINTIME | 开始时间（时,分秒） | string | 如：00:00 |
| 7 | ENDINTIME | 结束时间（时,分秒） | string | 如：22:00 |
| 8 | SAMPLEPLACE | 采样站点 | string | 如：3 |
| 9 | SAMPLEPLACENAME | 采样站点名称 | string | 如：社区 |
| 10 | WARDORREG | 病人类型 | string | 如：1 |
| 11 | MJZBZ | 加急标志 | string | 如：1 |
| 12 | SAMPLECODE | 标本种类 | string | 如：3 |
| 13 | SAMPLECODENAME | 标本种类名称 | string | 如：血 |
| 14 | INSTID | 仪器ID | string | 如：123 |
| 15 | RULETYPE | 规则类型 | string | 如：1 |
| 16 | NODEINTERVEL | 目标节点周期 | string | 如：3 |
| 17 | INTERVELUNIT | 目标节点周期单位 | string | 分钟、小时、天、周 |
| 18 | TARGETTIME | 目标节点周期 | string | 分、天、 周 |
| 19 | EXAMDAYS | 检测日程 | string | 如：1,3,5 |
| 20 | EXAMDAYSDESC | 检测日程描述 | string | 如：周一、三、五 |
| 21 | SKIPWEEKEND | 周末是否顺延 | string | 如：1 |
| 22 | NODEOUTTIME | 节点截止时间 | string | 如：09:00 |
| 23 | HINTMINUTES | 提前几分钟提醒 | string | 如：10 |
| 24 | REMARK | 备注说明 | string | 如： |
| 25 | STOPFLAG | 停用标志 | string | 0-正常 1-停用 |
| 26 | TIMEOUTDAYS | 顺延天数 | string | 如：2 |



代码实现：

判断对应的规则是否为添加新的规则，是，在LIS_REPTIMERULE中插入对应的规则，自动生成对应的输入码，否则在LIS_RETURNRULEDETAIL中插入新的规则明细

LIS_RETURNRULEDETAIL表中获取信息，SLAVE获取标本种类和采样站点，参照时间节点和参照时间目的节点

表结构：LIS_RETURNRULEDETAIL\SLAVE\ LIS_REPTIMERULE

##### B03 删除TAT规则

接口说明： 删除TAT规则

请求URL：../reptimerule/request/deletereptimerule

代码文件：winning.lis.reptimerule.service.RepTimeRuleService

示例URL：

http://192.168.10.73:15011/lis/reptimerule/request/deletereptimerule?hospitalcode=9999&ruleno=14

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | ruleno | 规则编号 | string | 不允许为空，如：01095 |
| 3 | rulename | 规则编号名称 | string | 不允许为空，如：120分钟 |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage |
| 1 | type | 消息类型 | string | SUCCESS-成功 
ERROR-系统出现异常或错误
FAILT-业务逻辑处理失败 |
| 2 | messageCode | 消息代码 | string | 用于区分消息代码 |
| 3 | message | 消息内容 | string | type=ERROR时则为系统错误消息提示内容
type=FAILT时则为业务提示信息 |
| 4 | link | URL链接 | string | 用于提示用户后可以点击查看详情信息，默认为空 |
| 5 | data | 数据集合 | object | 用于存储返回的数据 |



代码实现：

LIS_REPTIMERULE表中判断状态，删除数据，如果存在项目、就提示不允许删除

LIS_REPTIMERULEORDER表中数据删除

表结构：LIS_RETURNRULE\ LIS_REPTIMERULEORDER

##### B04	修改TAT规则

接口说明： 修改TAT规则

请求URL：../reptimerule/request/alterreptimerule

代码文件：winning.lis.reutrnrule.service.ReturnRuleService

示例URL： 

http://192.168.10.73:15011/lis/reptimerule/request/alterreptimerule?hospitalcode=9999&rulename=测试节点9712&sourcenode=BD&targetnode=CY&ruleno=13

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | rulename | 规则名称 | string | 不允许为空，如：01095 |
| 3 | sourcenode | 参照时间 | string | 不允许为空，如：CY |
| 4 | targetnode | 参照时间目标节点 | string | 不允许为空，如：BD |
| 5 | ruleno | 规则编号 | string | 不允许为空，如：20 |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | HOSPITALCODE | 机构代码 | string | 如：9999 |
| 2 | RULENO | 规则ID | string | 如：1 |
| 3 | RULENAME | 规则名称 | string | 如：30分钟 |
| 4 | SOURCENODE | 参照时间 | string | 如：BD |
| 5 | SOURCENODENAME | 参照时间名称 | string | 如：绑定 |
| 6 | TARGETNODE | 参照时间目标节点 | string | 如：CY |
| 7 | TARGETNODENAME | 参照时间目标节点名称 | string | 如：采样 |
| 8 | MEMCODE1 | 输入码一 | string | 如：12 |
| 9 | MEMCODE2 | 输入码二 | string | 如：30 |
| 10 | STOPFLAG | 停用标志 | string | 0-正常 1-停用 |



代码实现：

LIS_REPTIMERULE表中判断数据的存在状态，修改数据，slave表中获取节点的名称

自动生成规则的输入码（汉字的拼音首字母）替换输入码

表结构：LIS_RETURNRULE \SLAVE

##### B05 删除TAT规则明细

接口说明： 删除TAT规则明细

请求URL：../reptimerule/request/deletereptimeruledetail

代码文件：winning.lis.reptimerule.service.RepTimeRuleService

示例URL： 

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | ruledetailno | 规则明细ID | string | 不允许为空，如：01095 |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage |
| 1 | type | 消息类型 | string | SUCCESS-成功 
ERROR-系统出现异常或错误
FAILT-业务逻辑处理失败 |
| 2 | messageCode | 消息代码 | string | 用于区分消息代码 |
| 3 | message | 消息内容 | string | type=ERROR时则为系统错误消息提示内容
type=FAILT时则为业务提示信息 |
| 4 | link | URL链接 | string | 用于提示用户后可以点击查看详情信息，默认为空 |
| 5 | data | 数据集合 | object | 用于存储返回的数据 |



代码实现：

LIS_REPTIMERULEDETAIL表中判断状态，删除数据

表结构：LIS_REPTIMERULEDETAIL

##### B06	搜索TAT规则

接口说明：由监控时间段搜索TAT监控规则

请求URL：../reptimerule/request/searchreptimerule

代码文件：winning.lis.reptimerule.service.RepTimeRuleService

示例URL：

http://192.168.10.73:15011/lis/reptimerule/request/searchreptimerule?hospitalcode=9999&sourcenode=CY&targetnode=QS

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | sourcenode | 参照时间 | string | 不允许为空，如：CY |
| 3 | targetnode | 参照时间目标节点 | string | 不允许为空，如：BD |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | HOSPITALCODE | 机构代码 | string | 如：9999 |
| 2 | RULENO | 规则ID | string | 如：1 |
| 3 | RULENAME | 规则名称 | string | 如：30分钟 |
| 4 | SOURCENODE | 参照时间 | string | 如：BD |
| 5 | TARGETNODE | 参照时间目标节点 | string | 如：CY |
| 6 | MEMCODE1 | 输入码一 | string | 如：12 |
| 7 | MEMCODE2 | 输入码二 | string | 如：30 |
| 8 | STOPFLAG | 停用标志 | string | 0-正常 1-停用 |



Json返回数据格式：

[

    {

      "RULENO": 1,

      "RULENAME": "半小时",

      "SOURCENODE": "CY",

      "TARGETNODE": "QS",

      "HOSPITALCODE": "9999",

      "MEMCODE1": "",

      "MEMCODE2": "",

      "STOPFLAG": "0",

      "SOURCENODENAME": "采样时间",

      "TARGETNODENAME": "签收时间"

    },

    {

      "RULENO": 4,

      "RULENAME": "20分钟",

      "SOURCENODE": "CY",

      "TARGETNODE": "QS",

      "HOSPITALCODE": "9999",

      "MEMCODE1": "",

      "MEMCODE2": "",

      "STOPFLAG": "0",

      "SOURCENODENAME": "采样时间",

      "TARGETNODENAME": "签收时间"

    }

  ]

代码实现：

搜索LIS_REPTIMERULE\SLAVE表中信息

表结构：LIS_RETURNRULE\SLAVE

##### B07	添加TAT规则的收费项目

接口说明：添加TAT规则的收费项目

请求URL：../reptimerule/request/addhisitem

代码文件：winning.lis.reptimerule.service.RepTimeRuleService

示例URL：http://192.168.10.73:15011/lis/reptimerule/request/addhisitem?hospitalcode=9999&flag=1&repruleitem=[{"ruleno": "16","lisordercode": "010089","hisordercode": "10089",

"hisordername": "肝功能三项（体检）"},{"ruleno": "16","lisordercode": "01008999","hisordercode": "1008999","hisordername": "肝功能100项（体检）"}]

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | flag | 主从配置控制 | string | 项目配置TAT规则时，传0；TAT规则配置项目时，传1 |
| 3 | 项目规则信息   repruleitem    array<string> | 项目规则信息   repruleitem    array<string> | 项目规则信息   repruleitem    array<string> | 项目规则信息   repruleitem    array<string> |
| 1 | ruleno | 规则ID | string | 不允许为空，如：1 |
| 2 | lisordercode | LIS代码 | string | 不允许为空，如：012001 |
| 3 | hisordercode | HIS代码 | string | 允许为空，如：12001 |
| 4 | hisordername | 检测项目名称 | string | 允许为空，如：白细胞计数 |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | HOSPITALCODE | 机构代码 | string | 如：9999 |
| 2 | RULENO | 规则ID | int | 如：1 |
| 3 | RULENAME | 规则ID名称 | string | 如：测试节点12 |
| 4 | MEMCODE1 | 规则ID输入码 | string | 如：csjd12 |
| 5 | LISORDERCODE | 规则名称 | string | 如：30分钟 |
| 6 | HISORDERCODE | HIS代码 | string | 如：BD |
| 7 | HISORDERNAME | 检测项目名称 | string | 如：CY |



Json返回数据格式：

    [

        {

            "HOSPITALCODE": "9999",

            "RULENO": 16,

            "LISORDERCODE": "010089",

            "HISORDERCODE": "10089",

            "HISORDERNAME": "肝功能三项（体检）",

            "RULENAME": "测试节点12",

            "MEMCODE1": "csjd12"

        },

        {

            "HOSPITALCODE": "9999",

            "RULENO": 16,

            "LISORDERCODE": "01008999",

            "HISORDERCODE": "1008999",

            "HISORDERNAME": "肝功能100项（体检）",

            "RULENAME": "测试节点12",

            "MEMCODE1": "csjd12"

        }

]

代码实现：

删除LIS_REPTIMERULEORDER表中的lisordercode的选项，重新插入新的对应规则

表结构：LIS_RETURNRULE

##### B08	删除TAT规则的收费项目

接口说明：删除TAT规则的收费项目

请求URL：../reptimerule/request/ddeletehisitem

代码文件：winning.lis.reptimerule.service.RepTimeRuleService

示例URL： http://192.168.10.73:15011/lis/reptimerule/request/dropreptimerulehisitem?hospitalcode=9999&ruleno=16&lisordercode=01001

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | flag | 主从配置控制 | string | 项目配置TAT规则时，传0；TAT规则配置项目时，传1 |
| 3 | 项目规则信息   repruleitem    array<string> | 项目规则信息   repruleitem    array<string> | 项目规则信息   repruleitem    array<string> | 项目规则信息   repruleitem    array<string> |
| 1 | ruleno | 规则ID | string | 不允许为空，如：1 |
| 2 | lisordercode | LIS代码 | string | 不允许为空，如：012001 |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage |
| 1 | type | 消息类型 | string | SUCCESS-成功 
ERROR-系统出现异常或错误
FAILT-业务逻辑处理失败 |
| 2 | messageCode | 消息代码 | string | 用于区分消息代码 |
| 3 | message | 消息内容 | string | type=ERROR时则为系统错误消息提示内容
type=FAILT时则为业务提示信息 |
| 4 | link | URL链接 | string | 用于提示用户后可以点击查看详情信息，默认为空 |
| 5 | data | 数据集合 | object | 用于存储返回的数据 |



代码实现：

删除LIS_REPTIMERULEORDER表中的lisordercode的选项

表结构：LIS_RETURNRULE

##### B09	获取收费列表的项目信息

接口说明：单击收费项目列表，获取对应列表的项目信息

请求URL：../reptimerule/request/gethisitemlist

代码文件：winning.lis.reptimerule.service.RepTimeRuleService

示例URL： 

http://192.168.10.73:15011/lis/reptimerule/request/gethisitemlist?hospitalcode=9999&nodeno=HISITEM_STATUS&selectvalue=2

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | nodeno | 选项分类的nodeno | string | 不允许为空，如：HISITEM_RETURNRULE |
| 3 | selectvalue | 选择参数 | string | 不允许为空，在一级菜单“状态”下，1-全部项目、2-在用项目，3-已停用项目、4-未配置条码分组、5-未配置检验仪器、6-未配置回执单规则、7-未配置检验项目，其他菜单下为当前选中项的ID |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | LISORDERCODE | LIS收费代码 | string | 如：0509 |
| 3 | HISORDERCODE | HIS收费代码 | string | 如：509 |
| 4 | HISORDERNAME | HIS收费名称 | string | 如：入院治疗费 |
| 5 | BARCODELABELNAME | 收费简称 | string | 如：生化 |
| 6 | ITEMTYPE | HIS项目类别 | string | 0 临床项目 1 收费项目 9组合项目 |
| 7 | MEMCODE1 | 输入码一 | string | 如：SH |
| 8 | MEMCODE2 | 输入码二 | string | 如：3 |
| 9 | PRICE | 单价 | int | 如：1 |
| 10 | UNIT | 单位 | string | 如： |
| 11 | RULENO | TAT规则ID集合 | string | 如：2,5 |
| 12 | RULENAME | TAT名称集合 | string | 如：120分钟，40分钟 |
| 13 | SELECTFLAG | 选择标识 | boolean | 如：false |



##### B10	搜索收费项目

接口说明：搜索收费项目信息

请求URL：../reptimerule/request/searchhisitem

代码文件：winning.lis.reptimerule.service.RepTimeRuleService

示例URL： 

http://192.168.10.73:15011/lis/reptimerule/request/searchhisitem?hospitalcode=9999&searchtext=23

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | searchtext | 搜索内容 | string | 允许为空，为空默认搜索全部 |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | LISORDERCODE | LIS收费代码 | string | 如：0509 |
| 3 | HISORDERCODE | HIS收费代码 | string | 如：509 |
| 4 | HISORDERNAME | HIS收费名称 | string | 如：入院治疗费 |
| 5 | BARCODELABELNAME | 收费简称 | string | 如：生化 |
| 6 | ITEMTYPE | HIS项目类别 | string | 0 临床项目 1 收费项目 9组合项目 |
| 7 | MEMCODE1 | 输入码一 | string | 如：SH |
| 8 | MEMCODE2 | 输入码二 | string | 如：3 |
| 9 | PRICE | 单价 | int | 如：1 |
| 10 | UNIT | 单位 | string | 如： |
| 11 | RULENO | TAT规则ID集合 | string | 如：2,5 |
| 12 | RULENAME | TAT名称集合 | string | 如：120分钟，40分钟 |
| 13 | SELECTFLAG | 选择标识 | boolean | 如：false |



### 收费项目检验指标维护

#### V 模块接口视图

| NO. | 接口编号 | 接口名称 | 说明 |
| --- | --- | --- | --- |
| 1 | A01 | 检验指标分类初始化
../ordertoitem/request/getinitdata | 检验指标分类初始化 |
| 2 | B01 | 获取检验指标列表
../ordertoitem/request/getorderitemlist | 获取检验指标列表 |
| 3 | B02 | 添加收费项目对应检验指标
../ordertoitem/request/addorderitem | 添加收费项目的医嘱检验指标 |
| 4 | B03 | 修改检验指标审核检验
../ordertoitem/request/alterorderitemflag | 修改检验指标审核检验 |
| 5 | B04 | 删除收费项目对应的检验指标
../ordertoitem/request/droporderitem | 删除收费项目对应的检验指标 |



#### A 对外公布方法

##### A01	检验指标分类初始化

接口说明：检验指标分类初始化 

请求URL：../ordertoitem/request/getinitdata

代码文件：winning.lis.ordertoitem.service.OrderToItemService

示例URL：http://192.168.10.73:15011/lis/ordertoitem/request/getinitdata?hospitalcode=9999&subsyscode=

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码 |
| 2 | subsyscode | 子系统代码 | string | 传入子系统代码，允许为空
传入为空时默认为LIMS |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | NODEID | 节点编码 | string | 如：ORDERTOITEM_ALL |
| 2 | NODENAME | 节点名称 | string | 如：全部指标 |
| 3 | CHILDNODES | 子节点名称 | array<object> |  |
|  |  |  |  |  |
| 1 | NODEID | 节点编码 | string | 如：ORDERTOITEM_EXECGROUP |
| 2 | NODENAME | 节点名称 | string | 如：检验分组 |
| 3 | CHILDNODES | 子节点名称 | array<object> |  |
| 1 | HOSPITALCODE | 医疗结构 | string | 如：9999 |
| 2 | SUBSYSCODE | 子系统代码 | string | 如：LIMS |
| 3 | CLASSCODE | 字典分类 | string | 如：检验分组 |
| 4 | NODEID | 字典ID | int | 如：8 |
| 5 | SLAVENO | 字典代码 | string | 如：01 |
| 6 | SLAVENAME | 字典名称 | string | 如：血液体液 |
| 7 | PARENTID | 节点ID | int | 节点ID，-1表示根节点 |
| 8 | SLAVECODE | 编码规则 | string | 编码规则 区分层级关系，如：03.01 |
| 9 | MEMCODE1 | 输入码一 | string | 如：XYTY |
| 10 | MEMCODE2 | 输入码二 | string | 如：01 |
| 11 | ORDERNO | 排序 | int | 如：1 |
| 12 | REMARK | 备注说明 | string | 如： |
| 13 | CHILDNODES | 子节点 | array<object> | 子节点数组 |
|  | 子节点的详细信息 | 子节点的详细信息 | 子节点的详细信息 | 子节点的详细信息 |
| 1 | HOSPITALCODE | 医疗结构 | string | 如：9999 |
| 2 | SUBSYSCODE | 子系统代码 | string | 如：LIMS |
| 3 | CLASSCODE | 字典分类 | string | 如：检验分组 |
| 4 | NODEID | 字典ID | int | 如：8 |
| 5 | SLAVENO | 字典代码 | string | 如：01 |
| 6 | SLAVENAME | 字典名称 | string | 如：血液体液 |
| 7 | PARENTID | 节点ID | int | 节点ID，-1表示根节点 |
| 8 | SLAVECODE | 编码规则 | string | 编码规则 区分层级关系，如：03.01 |
| 9 | MEMCODE1 | 输入码一 | string | 如：XYTY |
| 10 | MEMCODE2 | 输入码二 | string | 如：01 |
| 11 | ORDERNO | 排序 | int | 如：1 |
| 12 | REMARK | 备注说明 | string | 如： |
| 13 | FATHERSLAVENO | 上级节点代码 | string | 如：01 |
| 14 | CHILDNODES | 子节点 | array<object> | 子节点数组 |
|  | 子节点的详细信息同上 | 子节点的详细信息同上 | 子节点的详细信息同上 | 子节点的详细信息同上 |
|  |  |  |  |  |
| 1 | NODEID | 节点编码 | string | 如：ORDERTOITEM_ITEMGROPU |
| 2 | NODENAME | 节点名称 | string | 如：仪器分类 |
| 3 | CHILDNODES | 子节点名称 | array<object> |  |
| 1 | INSTID | 仪器ID | string | 如：3，对应INSTID |
| 2 | INSTCODE | 仪器编码 | string | 如：HX-21，INSTCODE |
| 3 | INSTNAME | 仪器名称 | string | 如：HX-21，INSTNAME |
| 4 | EXECGROUPCODE | 执行小组代码 | string | 如：SH |
| 5 | EXECGROUPNAME | 执行小组名称 | string | 如：3 |
|  |  |  |  |  |
| 1 | NODEID | 节点编码 | string | 如：HISITEM_INSTORDER |
| 2 | NODENAME | 节点名称 | string | 如：组合项目 |
| 3 | CHILDNODES | 子节点名称 | array<object> |  |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | GROUPID | 组合模块序号 | int | 如：1 |
| 3 | GROUPCODE | 组合项目代码 | string | 如：bdjj |
| 4 | COMNAME | 组合项目名称 | string | 如; 组合1 |
| 5 | MEMCODE1 | 输入码1 | string | 如：zh |
| 6 | MEMCODE2 | 输入码2 | string | 如：zh |



JSON返回示例：

[

    {

      "NODEID": "ORDERTOITEM_ALL",

      "NODENAME": "全部项目",

      "CHILDNODES": null

    },

    {

      "NODEID": "ORDERTOITEM_EXECGROUP",

      "NODENAME": "检验分组",

      "CHILDNODES": [

        {

          "HOSPITALCODE": "9999",

          "SUBSYSCODE": "LIMS",

          "CLASSCODE": "检验分组",

          "NODEID": 232,

          "SLAVENO": "01",

          "SLAVENAME": "血液检查",

          "PARENTID": -1,

          "SLAVECODE": "01",

          "MEMCODE1": null,

          "MEMCODE2": null,

          "REMARK": null,

          "ORDERNO": null,

          "VISIBLE": "1",

          "SYSFLAG": "0",

          "STOPFLAG": "0",

          "CHILDNODES": [

            {

              "HOSPITALCODE": "9999",

              "SUBSYSCODE": "LIMS",

              "CLASSCODE": "检验分组",

              "NODEID": 244,

              "SLAVENO": "0101",

              "SLAVENAME": "血液检查",

              "PARENTID": 232,

              "SLAVECODE": "01.01",

              "MEMCODE1": null,

              "MEMCODE2": null,

              "REMARK": null,

              "ORDERNO": null,

              "VISIBLE": "1",

              "SYSFLAG": "0",

              "STOPFLAG": "0",

              "FATHERSLAVENO": "01",

              "CHILDNODES": null

            }

          ]

        }

      ]

    },

    {

      "NODEID": "ORDERTOITEM_ITEMGROPU",

      "NODENAME": "组合项目",

      "CHILDNODES": null

    },

    {

      "NODEID": "ORDERTOITEM_ITEMGROPU",

      "NODENAME": "检验仪器",

      "CHILDNODES": [

        {

          "NODENAME": "未分组",

          "CHILDNODE": [

            {

              "INSTID": 12,

              "INSTCODE": "AIA-360",

              "INSTNAME": "AIA-360",

              "EXECGROUPCODE": null,

              "EXECGROUPNAME": null

            }

          ]

        },

        {

          "NODENAME": "生化室",

          "CHILDNODE": [

            {

              "INSTID": 11,

              "INSTCODE": "AVE762B",

              "INSTNAME": "AVE762B",

              "EXECGROUPCODE": "1",

              "EXECGROUPNAME": "生化室"

            }

          ]

        }

      ]

    }

  ]

代码实现：

从slave_p表中获取对应的检验指标的分类

查询表LIS_TREEDICT，递归实现树结构节点获取检验分组的数据

LIS_ITEMGROPU和LIS_ITEMGROPUDETAIL获取组合项目明细

查询仪器表LIS_INSTRUMENT获取仪器分类

表结构：LIS_TREEDICT\LIS_INSTRUMENT\LIS_ITEMGROPU\LIS_ITEMGROPUDETAIL

#### B 业务类

##### B01	获取检验指标列表

接口说明：获取检验指标列表

请求URL：../ordertoitem/request/getorderitemlist

代码文件：winning.lis.ordertoitem.service.OrderToItemService

示例URL： http://192.168.10.73:15011/lis/ordertoitem/request/getorderitemlist?hospitalcode=9999&nodeno=ORDERTOITEM_EXECGROUP&selectvalue=244

原型参考：



接口入参： 

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | nodeno | 选项分类的nodeno | string | 不允许为空，如：ORDERTOITEM_ITEMGROPU |
| 3 | selectvalue | 选择参数 | string | 不允许为空，菜单下为当前选中项的ID，没有传0 |
| 4 | lisordercode | LIS标准收费代码 | String | 不允许为空，如018 |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- | --- |
| 节点名称：orderitemlist 汇总信息 类型：array<object> | 节点名称：orderitemlist 汇总信息 类型：array<object> | 节点名称：orderitemlist 汇总信息 类型：array<object> | 节点名称：orderitemlist 汇总信息 类型：array<object> | 节点名称：orderitemlist 汇总信息 类型：array<object> | 节点名称：orderitemlist 汇总信息 类型：array<object> |
| 1 | ITEMCODE | 项目代码 | 项目代码 | string | 如：#Bas |
| 2 | ITEMNAME | 项目名称 | 项目名称 | string | 如; 嗜碱性细胞数 |
| 3 | ITEMNUM | 标准编码 | 标准编码 | string | 如：#Bas |
| 4 | ENGSHORTNAME | 英文缩写 | 英文缩写 | string | 如：JC |
| 5 | ENGNAME | 英文名称 | 英文名称 | string | 如：WBC |
| 6 | LOINCCODE | LOINC编码 | LOINC编码 | string | 如： |
| 7 | EXAMCODE | 检验分类 | 检验分类 | string | 如：3 |
| 8 | EXAMNAME | 检验分类名称 | 检验分类名称 | string | 如：免疫 |
| 9 | RESULTTYPE | 结果类型 | 结果类型 | string | 如：3 |
| 10 | RESULTTYPEDES | 结果类型描述 | 结果类型描述 | string | 如：阴阳 |
| 11 | DEFVALUE | 缺省值 | 缺省值 | string | 如：阴性 |
| 12 | MEMCODE1 | 输入码一 | 输入码一 | string | 如：SJXXBS |
| 13 | MEMCODE2 | 输入码二 | 输入码二 | string | 如：245d |
| 14 | REMARK | 备注说明 | 备注说明 | string | 如：项目再用 |
| 15 | PREVALUE | 精度 | 精度 | string | 如：0.01 |
| 16 | ITEMPRICE | 单价 | 单价 | string | 如：14.5 |
| 17 | ITEMINFO | 项目说明 | 项目说明 | string | 如：血常规项目 |
| 18 | ITEMTYPEID | 项目分类ID | 项目分类ID | string | 项目分类ID，对应lis_treediect的nodeid |
| 19 | STOPFLAG | 项目停用标志 | 项目停用标志 | string | 标识该项目是否停用，0-启用，1-停用 |



JSON返回示例：

 {

        "orderitemlist": [

            {

                "ITEMCODE": "0101003",

                "ITEMNAME": "中性粒细胞百分比",

                "ITEMNUM": "NEU%",

                "ENGSHORTNAME": "NEU%",

                "ENGNAME": null,

                "EXAMCODE": null,

                "EXAMNAME": null,

                "RESULTTYPE": null,

                "MEMCODE1": "ZXLXBBFB",

                "MEMCODE2": null,

                "DEFVALUE": null,

                "PREVALUE": null,

                "ITEMPRICE": null,

                "ITEMTYPEID": 244,

                "ITEMINFO": null,

                "STOPFLAG": "0",

                "REMARK": "血常规",

                "RESULTTYPEDES": "数字"

            },

            {

                "ITEMCODE": "0101004",

                "ITEMNAME": "淋巴细胞绝对值",

                "ITEMNUM": "LYM#",

                "ENGSHORTNAME": "LYM#",

                "ENGNAME": null,

                "EXAMCODE": null,

                "EXAMNAME": null,

                "RESULTTYPE": null,

                "MEMCODE1": "LBXBJDZ",

                "MEMCODE2": null,

                "DEFVALUE": null,

                "PREVALUE": null,

                "ITEMPRICE": null,

                "ITEMTYPEID": 244,

                "ITEMINFO": null,

                "STOPFLAG": "0",

                "REMARK": "血常规",

                "RESULTTYPEDES": "数字"

            }

        ]

    }

代码实现：

表结构： LIS_ITEM\ LIS_INSTITEM L\ LIS_ITEMGROPU

##### B02	添加收费项目对应检验指标

接口说明：添加收费项目对应检验指标

请求URL：../ordertoitem/request/addorderitem

代码文件：winning.lis.ordertoitem.service.OrderToItemService

示例URL： 

http://192.168.10.73:15011/lis/ordertoitem/request/addorderitem?hospitalcode=9999&lisordercode=0129&hisordercode=测试数据&hisordername=测试数据&itemcodelist=["0101030","0101032"]

原型参考：



接口入参： 

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | lisordercode | lis代码 | string | 不允许为空，如：0126243 |
| 3 | hisordercode | his代码 | string | 允许为空，如： 126243 |
| 4 | hisordername | his代码名称 | string | 允许为空，如：肝功十一项 |
| 5 | itemcodelist | 检验项目代码集合 | array<object> |  |
| 1 | itemcode | 检验项目代码 | string | 不允许为空，如：2343222 |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | LISORDERCODE | LIS收费代码 | string | 如：0509 |
| 3 | HISORDERCODE | HIS收费名称 | string | 如：1001 |
| 4 | HISORDERNAME | 收费名称 | string | 如：肝功三项(体检) |
| 节点名称：orderitemlist 汇总信息 类型： array<object> | 节点名称：orderitemlist 汇总信息 类型： array<object> | 节点名称：orderitemlist 汇总信息 类型： array<object> | 节点名称：orderitemlist 汇总信息 类型： array<object> | 节点名称：orderitemlist 汇总信息 类型： array<object> |
| 1 | CHARGEORDER | 收费顺序 | string | 如：1 |
| 2 | CHECKITEM | 校验项目 | string | 如：ALT |
| 3 | CHECKFLAG | 校验标志 | string | 如：0 |
| 4 | NOTINSTLIST | 不需要校验的仪器ID集合 | string | 如：716,897 |
| 5 | NOTINSERTRESULT | 不需要结果入库的仪器ID集合 | int | 如：716,897 |
| 6 | ITEMCODE | 项目代码 | string | 如：0101005 |
| 7 | ITEMNAME | 项目名称 | string | 如：淋巴细胞百分比 |
| 8 | ITEMNUM | 标准编码 | string | 如：LYM% |
| 9 | ENGSHORTNAME | 英文缩写 | string | 如：LYM% |
| 10 | ENGNAME | 英文名称 | string | 如：xlb |
| 11 | LOINCCODE | LOINC编码 | string | 如： xlb |
| 12 | MEMCODE1 | 输入码一 | string | 如：LBXBBFB |
| 13 | MEMCODE2 | 输入码二 | string | 如：xlb |
| 14 | PREVALUE | 精度 | string | 如：0.0 |
| 15 | ITEMPRICE | 单价 | string | 如：150 |



JSON返回示例：

{

        "HOSPITALCODE": "9999",

        "LISORDERCODE": "0129",

        "HISORDERCODE": "测试数据",

        "HISORDERNAME": "测试数据",

        "orderitemlist": [

            {

                "HOSPITALCODE": "9999",

                "LISORDERCODE": "0129",

                "ITEMCODE": "0101030",

                "HISORDERCODE": "测试数据",

                "HISORDERNAME": "测试数据",

                "CHARGEORDER": null,

                "CHECKITEM": null,

                "CHECKFLAG": null,

                "INSTLISTMUST": null,

                "NOTINSERTSAMPLEINFO": null,

                "ITEMNAME": "有核红细胞计数",

                "ITEMNUM": "NRBC#",

                "ENGSHORTNAME": "NRBC#",

                "ENGNAME": null,

                "EXAMCODE": null,

                "EXAMNAME": null,

                "MEMCODE1": "YHHXBJS",

                "MEMCODE2": null,

                "REMARK": null

            },

            {

                "HOSPITALCODE": "9999",

                "LISORDERCODE": "0129",

                "ITEMCODE": "0101032",

                "HISORDERCODE": "测试数据",

                "HISORDERNAME": "测试数据",

                "CHARGEORDER": null,

                "CHECKITEM": null,

                "CHECKFLAG": null,

                "INSTLISTMUST": null,

                "NOTINSERTSAMPLEINFO": null,

                "ITEMNAME": "异型淋巴细胞绝对值",

                "ITEMNUM": "ALY#",

                "ENGSHORTNAME": "ALY#",

                "ENGNAME": null,

                "EXAMCODE": null,

                "EXAMNAME": null,

                "MEMCODE1": "YXLBXBJDZ",

                "MEMCODE2": null,

                "REMARK": null

            }

        ]

    }

代码实现：

删除表中已经存在的itemcode的项目信息

用itemcode关联从LIS_ORDERTOITEM和LIS_ITEM获取未停用的指标信息，关联组合的信息

表结构：LIS_ORDERTOITEM\ LIS_ITEM

##### B03	修改检验指标的审核检验

接口说明：修改检验指标审核检验

请求URL：../ordertoitem/request/alterorderitemflag

代码文件：winning.lis.ordertoitem.service.OrderToItemService

示例URL： 

http://192.168.10.73:15011/lis/ordertoitem/request/alterorderitemflag?hospitalcode=9999&lisordercode=0129&checkflag=0&itemcode=0101030

原型参考：



接口入参： 

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | lisordercode | lis代码 | string | 不允许为空，如：0126243 |
| 3 | hisordername | his名称 | string | 不允许为空，如：肝功三项 |
| 4 | itemcode | 检验项目代码 | string | 不允许为空，如：2343222 |
| 5 | itemname | 项目名称 | string | 如; 嗜碱性细胞数 |
| 6 | checkflag | 检验标志 | string | 允许为空，默认为0,0—不校验，1—检验 |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | LISORDERCODE | LIS标准收费代码 | string | 如：0509 |
| 3 | HISORDERCODE | HIS收费名称 | string | 如：1001 |
| 4 | HISORDERNAME | 收费名称 | string | 如：肝功三项(体检) |
| 5 | CHARGEORDER | 收费顺序 | string | 如：1 |
| 6 | CHECKITEM | 校验项目 | string | 如：ALT |
| 7 | CHECKFLAG | 校验标志 | string | 如：0 |
| 8 | NOTINSTLIST | 不需要校验的仪器ID集合 | string | 如：716,897 |
| 9 | NOTINSERTRESULT | 不需要结果入库的仪器ID集合 | int | 如：716,897 |
| 10 | ITEMCODE | 项目代码 | string | 如：0101005 |
| 11 | ITEMNAME | 项目名称 | string | 如：淋巴细胞百分比 |
| 12 | ITEMNUM | 标准编码 | string | 如：LYM% |
| 13 | ENGSHORTNAME | 英文缩写 | string | 如：LYM% |
| 14 | ENGNAME | 英文名称 | string | 如：xlb |
| 15 | LOINCCODE | LOINC编码 | string | 如： xlb |
| 16 | MEMCODE1 | 输入码一 | string | 如：LBXBBFB |
| 17 | MEMCODE2 | 输入码二 | string | 如：xlb |
| 18 | PREVALUE | 精度 | string | 如：0.0 |
| 19 | ITEMPRICE | 单价 | string | 如：150 |



JSON返回示例：

{

        "HOSPITALCODE": "9999",

        "LISORDERCODE": "0129",

        "ITEMCODE": "0101030",

        "HISORDERCODE": "测试数据",

        "HISORDERNAME": "测试数据",

        "CHARGEORDER": null,

        "CHECKITEM": null,

        "CHECKFLAG": "0",

        "INSTLISTMUST": null,

        "NOTINSERTSAMPLEINFO": null,

        "ITEMNAME": "有核红细胞计数",

        "ITEMNUM": "NRBC#",

        "ENGSHORTNAME": "NRBC#",

        "ENGNAME": null,

        "EXAMCODE": null,

        "EXAMNAME": null,

        "MEMCODE1": "YHHXBJS",

        "MEMCODE2": null,

        "REMARK": null

    }

代码实现：

用itemcode关联从LIS_ORDERTOITEM和LIS_ITEM获取未停用的指标信息

表结构：LIS_ORDERTOITEM\LIS_ITEM

##### B04	删除收费项目对应的检验指标

接口说明：删除收费项目对应的检验指标

请求URL：../ordertoitem/request/deleteorderitem

代码文件：winning.lis.ordertoitem.service.OrderToItemService

示例URL： 

http://192.168.10.73:15011/lis/ordertoitem/request/deleteorderitem?hospitalcode=9999&lisordercode=0129&itemcode=0101030

原型参考：



接口入参： 

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | lisordercode | lis代码 | string | 不允许为空，如：0126243 |
| 3 | hisordername | his名称 | string | 不允许为空，如：肝功三项 |
| 4 | itemcode | 检验项目代码 | string | 不允许为空，如：2343222 |
| 5 | itemname | 项目名称 | string | 如; 嗜碱性细胞数 |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage |
| 1 | type | 消息类型 | string | SUCCESS-成功 
ERROR-系统出现异常或错误
FAILT-业务逻辑处理失败 |
| 2 | messageCode | 消息代码 | string | 用于区分消息代码 |
| 3 | message | 消息内容 | string | type=ERROR时则为系统错误消息提示内容
type=FAILT时则为业务提示信息 |
| 4 | link | URL链接 | string | 用于提示用户后可以点击查看详情信息，默认为空 |
| 5 | data | 数据集合 | object | 用于存储返回的数据 |



代码实现：

判断对应的指标信息是否存在

从LIS_ORDERTOITEM中删除指标信息

表结构：LIS_ORDERTOITEM

### 收费项目对应材料费维护

#### V 模块接口视图

| NO. | 接口编号 | 接口名称 | 说明 |
| --- | --- | --- | --- |
| 1 | A01 | 材料费初始化
../materialtoorder/request/getinitdata | 材料费项目初始化 |
| 2 | B01 | 获取材料费项目列表
../materialtoorder/request/getmatorderlist | 获取材料费项目列表 |
| 3 | B02 | 添加和修改材料费项目
../materialtoorder/request/addmatorder | 添加材料费项目 |
| 4 | B03 | 删除材料费项目
../ordertoitem/request/dropordertoitem | 删除材料费项目 |
| 5 | B04 | 添加材料费对应的收费项目
../materialtoorder/request/addmatorderitem | 添加材料费对应的收费项目 |
| 6 | B05 | 删除材料费对应的收费项目
../materialtoorder/request/dropmatorderitem | 删除材料费对应的收费项目 |



#### A 对外公布方法

##### A01	材料费初始化

接口说明：材料费项目初始化 

请求URL：../materialtoorder/request/getinitdata

代码文件：winning.lis. materialtoorder.service.MaterialToOrderService

示例URL： 

http://localhost:15011/lis/materialtoorder/request/getinitdata?hospitalcode=9999

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码 |
| 2 | subsyscode | 子系统代码 | string | 传入子系统代码，允许为空
传入为空时默认为LIMS |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称： itemtypelist 汇总信息 类型：array< object> 项目类型 | 节点名称： itemtypelist 汇总信息 类型：array< object> 项目类型 | 节点名称： itemtypelist 汇总信息 类型：array< object> 项目类型 | 节点名称： itemtypelist 汇总信息 类型：array< object> 项目类型 | 节点名称： itemtypelist 汇总信息 类型：array< object> 项目类型 |
| 1 | CODE | 字典代码 | string | 如：1 |
| 2 | NAME | 字典名称 | string | 如：血 |
| 3 | MEMCODE1 | 输入码一 | string | 如：1 |
| 4 | MEMCODE2 | 输入码二 | string | 如：X |
| 5 | DICID | 标准编码 | string | 如：1 |
| 6 | DICTYPE | 字典分类 | string | 如：样本类型 |
| 7 | SUBSYSCODE | 子系统分类 | string | 如：LIMS |
| 8 | EXTERNCODE | 外部代码 | string | 如：10 |
| 9 | ORDERNO | 排序 | string | 如：0 |
| 10 | RESERVEFIELD1 | 扩展字段信息 | string | 如：null |
| 节点名称：barsinglelist 汇总信息 类型：array< object> 收费方式 | 节点名称：barsinglelist 汇总信息 类型：array< object> 收费方式 | 节点名称：barsinglelist 汇总信息 类型：array< object> 收费方式 | 节点名称：barsinglelist 汇总信息 类型：array< object> 收费方式 | 节点名称：barsinglelist 汇总信息 类型：array< object> 收费方式 |
| 1 | CODE | 字典代码 | string | 如：1 |
| 2 | NAME | 字典名称 | string | 如：血 |
| 3 | MEMCODE1 | 输入码一 | string | 如：1 |
| 4 | MEMCODE2 | 输入码二 | string | 如：X |
| 5 | DICID | 标准编码 | string | 如：1 |
| 6 | DICTYPE | 字典分类 | string | 如：样本类型 |
| 7 | SUBSYSCODE | 子系统分类 | string | 如：LIMS |
| 8 | EXTERNCODE | 外部代码 | string | 如：10 |
| 9 | ORDERNO | 排序 | string | 如：0 |
| 10 | RESERVEFIELD1 | 扩展字段信息 | string | 如：null |
| 节点名称：samplelist 汇总信息 类型：array< object> 默认标本 | 节点名称：samplelist 汇总信息 类型：array< object> 默认标本 | 节点名称：samplelist 汇总信息 类型：array< object> 默认标本 | 节点名称：samplelist 汇总信息 类型：array< object> 默认标本 | 节点名称：samplelist 汇总信息 类型：array< object> 默认标本 |
| 1 | CODE | 字典代码 | string | 如：1 |
| 2 | NAME | 字典名称 | string | 如：血 |
| 3 | MEMCODE1 | 输入码一 | string | 如：1 |
| 4 | MEMCODE2 | 输入码二 | string | 如：X |
| 5 | DICID | 标准编码 | string | 如：1 |
| 6 | DICTYPE | 字典分类 | string | 如：样本类型 |
| 7 | SUBSYSCODE | 子系统分类 | string | 如：LIMS |
| 8 | EXTERNCODE | 外部代码 | string | 如：10 |
| 9 | ORDERNO | 排序 | string | 如：0 |
| 10 | RESERVEFIELD1 | 扩展字段信息 | string | 如：null |
| 节点名称：brlxlist汇总信息 类型：array< object> 病人类型 | 节点名称：brlxlist汇总信息 类型：array< object> 病人类型 | 节点名称：brlxlist汇总信息 类型：array< object> 病人类型 | 节点名称：brlxlist汇总信息 类型：array< object> 病人类型 | 节点名称：brlxlist汇总信息 类型：array< object> 病人类型 |
| 1 | CODE | 字典代码 | string | 如：1 |
| 2 | NAME | 字典名称 | string | 如：血 |
| 3 | MEMCODE1 | 输入码一 | string | 如：1 |
| 4 | MEMCODE2 | 输入码二 | string | 如：X |
| 5 | DICID | 标准编码 | string | 如：1 |
| 6 | DICTYPE | 字典分类 | string | 如：样本类型 |
| 7 | SUBSYSCODE | 子系统分类 | string | 如：LIMS |
| 8 | EXTERNCODE | 外部代码 | string | 如：10 |
| 9 | ORDERNO | 排序 | string | 如：0 |
| 10 | RESERVEFIELD1 | 扩展字段信息 | string | 如：null |



JSON返回示例：

{

        "barsinglelist": [

            {

                "CODE": "001 ",

                "NAME": "自费",

                "DICID": null,

                "DICTYPE": "付费类别",

                "EXTERNCODE": "001 ",

                "MEMCODE1": "001 ",

                "MEMCODE2": "",

                "SUBSYSCODE": "LIMS",

                "ORDERNO": 0,

                "RESERVEFIELD1": null

            }

        ],

        "itemtypelist": [

            {

                "CODE": "0",

                "NAME": "临床项目",

                "DICID": null,

                "DICTYPE": "项目类别",

                "EXTERNCODE": "0",

                "MEMCODE1": null,

                "MEMCODE2": null,

                "SUBSYSCODE": "LIMS",

                "ORDERNO": 1,

                "RESERVEFIELD1": null

            }

        ],

        "brlxlist": [

            {

                "CODE": "1",

                "NAME": "住院",

                "DICID": null,

                "DICTYPE": "病人类型",

                "EXTERNCODE": "1",

                "MEMCODE1": "1",

                "MEMCODE2": "",

                "SUBSYSCODE": "LIMS",

                "ORDERNO": 0,

                "RESERVEFIELD1": null

            }

        ],

        "samplelist": [

            {

                "CODE": "1",

                "NAME": "血",

                "DICID": "2",

                "DICTYPE": "样本类型",

                "EXTERNCODE": null,

                "MEMCODE1": "1",

                "MEMCODE2": "X",

                "SUBSYSCODE": "LIMS",

                "ORDERNO": 0,

                "RESERVEFIELD1": null

            }

        ]

    }

代码实现：

依据类型查询结果

表结构：SLAVE

##### A02 获取收费筛选列表

接口说明：获取收费筛选列表

请求URL：../materialtoorder/request/getstatuslist

代码文件：winning.lis. materialtoorder.service.MaterialToOrderService

示例URL： 

http://192.168.10.73:15011/lis/materialtoorder/request/getstatuslist?hospitalcode=9999&subsyscode=LIMS

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码 |
| 2 | subsyscode | 子系统代码 | string | 传入子系统代码，允许为空
传入为空时默认为LIMS |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | NODENO | 节点编码 | string | 如：HISITEM_STATUS |
| 2 | NODENAME | 节点名称 | string | 如：状态 |
| 3 | CHILDNODES | 子节点名称 | array<object> |  |
|  |  |  |  |  |
| 1 | CODE | 字典代码 | string | 如：1 |
| 2 | NAME | 字典名称 | string | 如：生化 |
| 3 | DICID | 标准编码 | string | 如：SH |
| 4 | MEMCODE1 | 输入码一 | string | 如：SH |
| 5 | MEMCODE2 | 输入码二 | string | 如：3 |
| 6 | ORDERNO | 排序 | int | 如：1 |
|  |  |  |  |  |
| 1 | NODENO | 节点编码 | string | 如：HISITEM_EXAMINE |
| 2 | NODENAME | 节点名称 | string | 如：检验分类 |
| 3 | CHILDNODES | 子节点名称 | array<object> |  |
|  |  |  |  |  |
| 1 | CODE | 字典代码 | string | 如：1 |
| 2 | NAME | 字典名称 | string | 如：生化 |
| 3 | DICID | 标准编码 | string | 如：SH |
| 4 | MEMCODE1 | 输入码一 | string | 如：SH |
| 5 | MEMCODE2 | 输入码二 | string | 如：3 |
| 6 | ORDERNO | 排序 | int | 如：1 |
|  |  |  |  |  |
| 1 | NODEID | 节点编码 | string | 如：HISITEM_INSTORDER |
| 2 | NODENAME | 节点名称 | string | 如：仪器分类 |
| 3 | CHILDNODES | 子节点名称 | array<object> |  |
|  |  |  |  |  |
| 1 | INSTID | 仪器ID | string | 如：3，对应INSTID |
| 2 | INSTCODE | 仪器编码 | string | 如：HX-21，INSTCODE |
| 3 | INSTNAME | 仪器名称 | string | 如：HX-21，INSTNAME |
| 4 | EXECGROUPCODE | 执行小组代码 | string | 如：SH |
| 5 | EXECGROUPNAME | 执行小组名称 | string | 如：3 |
|  |  |  |  |  |
| 1 | NODEID | 节点编码 | string | 如：HISITEM_BARORDER |
| 2 | NODENAME | 节点名称 | string | 如：条码分组 |
| 3 | CHILDNODES | 子节点名称 | array<object> |  |
|  |  |  |  |  |
| 1 | CODE | 条形码分组代码 | string | 如：4，BAREXAMCODE |
| 2 | NAME | 条形码分组名称 | string | 如：体液（尿），BAREXAMNAME |
| 3 | DICID | 条码分组编码 | string | 如：2，BAREXAMCODE |
| 4 | MEMCODE1 | 输入码一 | string | 如：SH |
| 5 | MEMCODE2 | 输入码二 | string | 如：3 |
| 6 | ORDERNO | 排序 | int | 如：1，BARPRIORITY |
|  |  |  |  |  |
| 1 | NODEID | 节点编码 | string | 如：HISITEM_RETURNRULE |
| 2 | NODENAME | 节点名称 | string | 如：回执单规则 |
| 3 | CHILDNODES | 子节点名称 | array<object> |  |
|  |  |  |  |  |
| 1 | CODE | 规则ID | string | 如：23，RULENO |
| 2 | NAME | 规则名称 | string | 如：23，RULENAME |
| 3 | DICID | 条码分组编码 | string | 如：2，RULENO |
| 4 | MEMCODE1 | 输入码一 | string | 如：333 |
| 5 | MEMCODE2 | 输入码二 | string | 如：2 |
| 6 | ORDERNO | 排序 | string | 如：1 |



#### B 业务类

##### B01	获取材料费项目列表

接口说明：获取材料费项目列表

请求URL：../materialtoorder/request/getmatorderlist

代码文件：winning.lis. materialtoorder.service.MaterialToOrderService

示例URL： http://localhost:15011/lis/materialtoorder/request/getmatorderlist?hospitalcode=9999

原型参考：

接口入参： 

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：matorder 汇总信息 类型： array<object> | 节点名称：matorder 汇总信息 类型： array<object> | 节点名称：matorder 汇总信息 类型： array<object> | 节点名称：matorder 汇总信息 类型： array<object> | 节点名称：matorder 汇总信息 类型： array<object> |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | LISORDERCODE | LIS标准收费代码 | string | 如：0509 |
| 3 | HISORDERCODE | HIS收费代码 | string | 如：509 |
| 4 | HISORDERNAME | HIS收费名称 | string | 如：入院治疗费 |
| 5 | BARCODELABELNAME | 收费简称 | string | 如：生化 |
| 6 | ITEMTYPE | HIS项目类别 | string | 0 临床项目 1 收费项目 9组合项目 |
| 7 | ITEMTYPENAME | 项目类别名称 | string | 如：临床项目 |
| 8 | MEMCODE1 | 输入码一 | string | 如：SH |
| 9 | MEMCODE2 | 输入码二 | string | 如：3 |
| 10 | PRICE | 单价 | int | 如：1 |
| 11 | UNIT | 单位 | string | 如： |
| 12 | EXAMCODE | 检验分类代码 | string | 如：2 |
| 13 | EXAMNAME | 检验分类名称 | string | 如：血流变 |
| 14 | SAMPLECODE | 默认标本种类 | string | 如：血 |
| 15 | SAMPLEDESC | 标本说明 | string | 如： |
| 16 | MJZBZ | 急诊标志 | string | 如：1 |
| 17 | BARCODEFLAG | 条码标志 | string | 如：0 |
| 18 | STOPFLAG | 停用标志 | string | 如：0 |
| 19 | MATERIALFLAG | 材料费标志 | string | 如：0 |
| 20 | MATERIALTYPE | 材料收费方式 | string | 如：1 |
| 21 | MATERIALTYPENAME | 材料收费方式名称 | string | 如：按条码个数收费 |
| 22 | MATBACKFLAG | 此材料费项目只收不退 | string | 如：0 |
| 23 | MATWARDORREG | 收取材料费的病人类别 | string | 如：2 |
| 24 | MATWARDORREGNAME | 收取材料费的病人类别名称 | string | 如：住院 |
| 25 | MATSAMPLE | 收取材料费的标本种类 | string | 如：12 |
| 26 | MATSAMPLENAME | 收取材料费的标本种类名称 | string | 如：血 |
| 27 | MATAGELOW | 收取材料费年龄下限 | string | 如：33 |
| 28 | MATAGEUPP | 收取材料费年龄上限 | string | 如：3 |
| 29 | MATAGEUNIT | 收取材料费年龄单位 | string | 如：岁 |
| 30 | EXHOSPFLAG | 外送标志 | string | 如：0 |
| 31 | EXHOSPITALCODE | 外送医疗机构代码 | string | 如：8888 |



JSON返回示例：

{

        "matorderlist": [

            {

                "HOSPITALCODE": "9999",

                "LISORDERCODE": "010011",

                "HISORDERCODE": "10011",

                "HISORDERNAME": "1",

                "BARCODELABELNAME": null,

                "ITEMTYPE": "0",

                "MEMCODE1": "1",

                "MEMCODE2": "1",

                "PRICE": 1,

                "UNIT": null,

                "EXAMCODE": null,

                "SAMPLECODE": null,

                "SAMPLEDESC": null,

                "MJZBZ": "0",

                "BARCODEFLAG": "0",

                "STOPFLAG": "0",

                "MATERIALFLAG": "1",

                "MATERIALTYPE": "1",

                "MATBACKFLAG": "1",

                "MATWARDORREG": "1",

                "MATSAMPLE": "1",

                "MATAGELOW": 1,

                "MATAGEUPP": 1,

                "MATAGEUNIT": null,

                "EXHOSPFLAG": "0",

                "EXHOSPITALCODE": null,

                "EXAMNAME": null,

                "ITEMTYPENAME": "临床项目",

                "MATWARDORREGNAME": "住院",

                "MATSAMPLENAME": "血"

            }

        ]

    }

代码实现：

依据材料费标志，从LIS_HISITEM中获取材料费项目信息，才SLAVE表中获取材料费的默认标本，获取病人的类型

表结构： LIS_HISITEM\SLAVE

##### B02	添加和修改材料费项目

接口说明：添加材料费项目

请求URL：../materialtoorder/request/addmatorder

代码文件：winning.lis. materialtoorder.service.MaterialToOrderService

示例URL： 

http://localhost:15011/lis/materialtoorder/request/addmatorder?hospitalcode=9999&hisordercode=1001&itemtype=0&hisorderinfo={"hisordername":"1","price":"1","materialtype":"1","matbackflag":"1","matsample":"1","matwardorreg":"1","matageupp":"1","matagelow":"1"}

原型参考：



接口入参： 

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | hisordercode | his代码 | string | 不允许为空，如： 126243 |
| 3 | itemtype | 项目类型 | string | 不允许为空，如：0 |
| 4 | 节点名称  hisorderinfo 类型 object | 节点名称  hisorderinfo 类型 object | 节点名称  hisorderinfo 类型 object | 节点名称  hisorderinfo 类型 object |
| 1 | hisordername | his代码名称 | string | 允许为空，如：肝功十一项 |
| 2 | price | 价格 | string | 允许为空，如：0.0 |
| 3 | materialtype | 材料收费方式 | string | 允许为空，如：1 |
| 4 | matbackflag | 此材料费项目只收不退 | string | 允许为空，如：0 |
| 5 | matsample | 收取材料费的标本种类 | string | 允许为空，如：12 |
| 6 | matwardorreg | 收取材料费的病人类别 | string | 允许为空，如：1 |
| 7 | matageupp | 收取材料费年龄上限 | string | 允许为空，如：66 |
| 8 | matagelow | 收取材料费年龄下限 | string | 允许为空，如：1 |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：matorder 汇总信息 类型： array<object> | 节点名称：matorder 汇总信息 类型： array<object> | 节点名称：matorder 汇总信息 类型： array<object> | 节点名称：matorder 汇总信息 类型： array<object> | 节点名称：matorder 汇总信息 类型： array<object> |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | LISORDERCODE | LIS标准收费代码 | string | 如：0509 |
| 3 | HISORDERCODE | HIS收费代码 | string | 如：509 |
| 4 | HISORDERNAME | HIS收费名称 | string | 如：入院治疗费 |
| 5 | BARCODELABELNAME | 收费简称 | string | 如：生化 |
| 6 | ITEMTYPE | HIS项目类别 | string | 0 临床项目 1 收费项目 9组合项目 |
| 7 | ITEMTYPENAME | 项目类别名称 | string | 如：临床项目 |
| 8 | MEMCODE1 | 输入码一 | string | 如：SH |
| 9 | MEMCODE2 | 输入码二 | string | 如：3 |
| 10 | PRICE | 单价 | int | 如：1 |
| 11 | UNIT | 单位 | string | 如： |
| 12 | EXAMCODE | 检验分类代码 | string | 如：2 |
| 13 | EXAMNAME | 检验分类名称 | string | 如：血流变 |
| 14 | SAMPLECODE | 默认标本种类 | string | 如：血 |
| 15 | SAMPLEDESC | 标本说明 | string | 如： |
| 16 | MJZBZ | 急诊标志 | string | 如：1 |
| 17 | BARCODEFLAG | 条码标志 | string | 如：0 |
| 18 | STOPFLAG | 停用标志 | string | 如：0 |
| 19 | MATERIALFLAG | 材料费标志 | string | 如：0 |
| 20 | MATERIALTYPE | 材料收费方式 | string | 如：1 |
| 21 | MATERIALTYPENAME | 材料收费方式名称 | string | 如：按条码个数收费 |
| 22 | MATBACKFLAG | 此材料费项目只收不退 | string | 如：0 |
| 23 | MATWARDORREG | 收取材料费的病人类别 | string | 如：2 |
| 24 | MATWARDORREGNAME | 收取材料费的病人类别名称 | string | 如：住院 |
| 25 | MATSAMPLE | 收取材料费的标本种类 | string | 如：12 |
| 26 | MATSAMPLENAME | 收取材料费的标本种类名称 | string | 如：血 |
| 27 | MATAGELOW | 收取材料费年龄下限 | string | 如：33 |
| 28 | MATAGEUPP | 收取材料费年龄上限 | string | 如：3 |
| 29 | MATAGEUNIT | 收取材料费年龄单位 | string | 如：岁 |
| 30 | EXHOSPFLAG | 外送标志 | string | 如：0 |
| 31 | EXHOSPITALCODE | 外送医疗机构代码 | string | 如：8888 |



JSON返回示例：

{

        "matorderlist": [

            {

                "HOSPITALCODE": "9999",

                "LISORDERCODE": "01001",

                "HISORDERCODE": "1001",

                "HISORDERNAME": "1",

                "BARCODELABELNAME": null,

                "ITEMTYPE": "0",

                "MEMCODE1": "1",

                "MEMCODE2": "1",

                "PRICE": 1,

                "UNIT": null,

                "EXAMCODE": null,

                "SAMPLECODE": null,

                "SAMPLEDESC": null,

                "MJZBZ": "0",

                "BARCODEFLAG": "0",

                "STOPFLAG": "0",

                "MATERIALFLAG": "1",

                "MATERIALTYPE": "1",

                "MATBACKFLAG": "1",

                "MATWARDORREG": "1",

                "MATSAMPLE": "1",

                "MATAGELOW": 1,

                "MATAGEUPP": 1,

                "MATAGEUNIT": null,

                "EXHOSPFLAG": "0",

                "EXHOSPITALCODE": null,

                "EXAMNAME": null,

                "ITEMTYPENAME": "临床项目",

                "MATWARDORREGNAME": "住院",

                "MATSAMPLENAME": "血"

            }

        ]

    }

代码实现：（存在更新）

依据lisordercode判断项目是否存在，存在就更新，不存在插入

插入到表LIS_HISITEM中

获取添加的项目信息，slave表中获取默认标本信息

表结构：LIS_ITEM

##### B03	删除材料费项目

接口说明：删除材料费项目

请求URL：../materialtoorder/request/dropmatorder

代码文件：winning.lis. materialtoorder.service.MaterialToOrderService

示例URL： 

http://localhost:15011/lis/materialtoorder/request/dropmatorder?hospitalcode=9999&lisordercode=09999

原型参考：

接口入参： 

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | lisordercode | lis代码 | string | 不允许为空，如：0126243 |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage |
| 1 | type | 消息类型 | string | SUCCESS-成功 
ERROR-系统出现异常或错误
FAILT-业务逻辑处理失败 |
| 2 | messageCode | 消息代码 | string | 用于区分消息代码 |
| 3 | message | 消息内容 | string | type=ERROR时则为系统错误消息提示内容
type=FAILT时则为业务提示信息 |
| 4 | link | URL链接 | string | 用于提示用户后可以点击查看详情信息，默认为空 |
| 5 | data | 数据集合 | object | 用于存储返回的数据 |



代码实现：

判断是否已经配置了收费项目，配置收费项目就不让删除

没有配置收费项目就把LIS_HISITEM表中MATERIALFLAG修改为0

表结构：LIS_HISITEM

##### B04	添加材料费对应的收费项目

接口说明：添加材料费对应的收费项目

请求URL：../materialtoorder/request/addmatorderitem

代码文件：winning.lis. materialtoorder.service.MaterialToOrderService

示例URL： 

http://localhost:15011/lis/materialtoorder/request/addmatorderitem?hospitalcode=9999&matordercode=24000&mathisordercode=20171127&mathisordername=测试数据&hisorderlist=[{"lisordercode":"20171127","hisordercode":"20171127","hisordername":"20171127"}]

原型参考：



接口入参： 

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | 节点名称  hisorderlist 类型   array<string> | 节点名称  hisorderlist 类型   array<string> | 节点名称  hisorderlist 类型   array<string> | 节点名称  hisorderlist 类型   array<string> |
| 1 | lisordercode | lis代码 | string | 不允许为空，如：013523 |
| 2 | hisordercode | his代码 | string | 不允许为空，如：13523 |
| 3 | hisordername | his代码名称 | string | 不允许为空，如：his代码名称 |
| 4 | matordercode | LIS材料费代码 | string | 不允许为空，对应材料费的LISORDERCODE，如： 126243 |
| 5 | mathisordercode | HIS材料费代码 | string | 不允许为空，对应材料费的HISORDERCODE，如：0 |
| 6 | mathisordername | 材料费名称 | string | 不允许为空，对应材料费的HISORDERNAME，如：肝功能三项 |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：matorderitemlist 汇总信息 类型： array<object> | 节点名称：matorderitemlist 汇总信息 类型： array<object> | 节点名称：matorderitemlist 汇总信息 类型： array<object> | 节点名称：matorderitemlist 汇总信息 类型： array<object> | 节点名称：matorderitemlist 汇总信息 类型： array<object> |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | MATORDERCODE | LIS材料费代码 | string | 如：0509 |
| 3 | MATHISORDERCODE | HIS材料费代码 | string | 如：509 |
| 4 | MATHISORDERNAME | 材料费名称 | string | 如：入院治疗费 |
| 5 | BARCODELABELNAME | 收费简称 | string | 如：生化 |
| 6 | HISORDERCODE | his代码 | string | 如：13523 |
| 7 | HISORDERNAME | his代码名称 | string | 如：his代码名称 |
| 8 | LISORDERCODE | lis代码 | string | 如：013523 |



JSON返回示例：

{

        "matorderitemlist": [

            {

                "HOSPITALCODE": "9999",

                "MATORDERCODE": "24000",

                "MATHISORDERCODE": "20171127",

                "MATHISORDERNAME": "测试数据",

                "LISORDERCODE": "20171127",

                "HISORDERCODE": "20171127",

                "HISORDERNAME": "20171127",

                "SUPPLIESCODE": null

            }

        ]

    }

代码实现：

LIS_MATERIALTOORDER获取信息

表结构：LIS_MATERIALTOORDER

##### B05	删除材料费对应的收费项目

接口说明：删除材料费对应的收费项目

请求URL：../materialtoorder/request/deletematorderitem

代码文件：winning.lis. materialtoorder.service.MaterialToOrderService

示例URL： 

http://localhost:15011/lis/materialtoorder/request/dropmatorderitem?hospitalcode=9999&matordercode=24000&lisordercodelist=[20171127]

原型参考：

接口入参： 

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | 节点名称 hisorderlist 类型 array<object> | 节点名称 hisorderlist 类型 array<object> | 节点名称 hisorderlist 类型 array<object> | 节点名称 hisorderlist 类型 array<object> |
| 1 | lisordercode | lis代码 | string | 不允许为空，如：013523 |
| 2 | matordercode | LIS材料费代码 | string | 不允许为空，对应材料费的LISORDERCODE，如： 126243 |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage |
| 1 | type | 消息类型 | string | SUCCESS-成功 
ERROR-系统出现异常或错误
FAILT-业务逻辑处理失败 |
| 2 | messageCode | 消息代码 | string | 用于区分消息代码 |
| 3 | message | 消息内容 | string | type=ERROR时则为系统错误消息提示内容
type=FAILT时则为业务提示信息 |
| 4 | link | URL链接 | string | 用于提示用户后可以点击查看详情信息，默认为空 |
| 5 | data | 数据集合 | object | 用于存储返回的数据 |



代码实现：

LIS_MATERIALTOORDER表中删除对应的信息

表结构：LIS_MATERIALTOORDER

##### B06	获取收费列表的项目信息

接口说明：单击收费项目列表，获取对应列表的项目信息

请求URL：../materialtoorder/request/gethisitemlist

代码文件：winning.lis. materialtoorder.service.MaterialToOrderService

示例URL： 

http://192.168.10.73:15011/lis/materialtoorder/request/gethisitemlist?hospitalcode=9999&nodeno=HISITEM_STATUS&selectvalue=2

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | nodeno | 选项分类的nodeno | string | 不允许为空，如：HISITEM_RETURNRULE |
| 3 | selectvalue | 选择参数 | string | 不允许为空，在一级菜单“状态”下，1-全部项目、2-在用项目，3-已停用项目、4-未配置条码分组、5-未配置检验仪器、6-未配置回执单规则、7-未配置检验项目，其他菜单下为当前选中项的ID |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | LISORDERCODE | LIS收费代码 | string | 如：0509 |
| 3 | HISORDERCODE | HIS收费代码 | string | 如：509 |
| 4 | HISORDERNAME | HIS收费名称 | string | 如：入院治疗费 |
| 5 | BARCODELABELNAME | 收费简称 | string | 如：生化 |
| 6 | ITEMTYPE | HIS项目类别 | string | 0 临床项目 1 收费项目 9组合项目 |
| 7 | MEMCODE1 | 输入码一 | string | 如：SH |
| 8 | MEMCODE2 | 输入码二 | string | 如：3 |
| 9 | PRICE | 单价 | int | 如：1 |
| 10 | UNIT | 单位 | string | 如： |
| 11 | MATORDERCODE | 材料费ID集合 | string | 如：2，5,33 |
| 12 | MATHISORDERNAME | 材料费名称集合 | string | 如：肝功三项，肝功十一项 |
| 13 | SELECTFLAG | 选择标识 | boolean | 如：false |



##### B07 搜索收费项目

接口说明：搜索收费项目信息

请求URL：../materialtoorder/request/searchhisitem

代码文件：winning.lis. materialtoorder.service.MaterialToOrderService

示例URL： 

http://192.168.10.73:15011/lis/materialtoorder/request/searchhisitem?hospitalcode=9999&searchtext=23

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | searchtext | 搜索内容 | string | 允许为空，为空默认搜索全部 |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | LISORDERCODE | LIS收费代码 | string | 如：0509 |
| 3 | HISORDERCODE | HIS收费代码 | string | 如：509 |
| 4 | HISORDERNAME | HIS收费名称 | string | 如：入院治疗费 |
| 5 | BARCODELABELNAME | 收费简称 | string | 如：生化 |
| 6 | ITEMTYPE | HIS项目类别 | string | 0 临床项目 1 收费项目 9组合项目 |
| 7 | MEMCODE1 | 输入码一 | string | 如：SH |
| 8 | MEMCODE2 | 输入码二 | string | 如：3 |
| 9 | PRICE | 单价 | int | 如：1 |
| 10 | UNIT | 单位 | string | 如： |
| 11 | MATORDERCODE | 材料费ID集合 | string | 如：2，5,33 |
| 12 | MATHISORDERNAME | 材料费名称集合 | string | 如：肝功三项，肝功十一项 |
| 13 | SELECTFLAG | 选择标识 | boolean | 如：false |



### 收费项目对应标本种类维护

#### V 模块接口视图

| NO. | 接口编号 | 接口名称 | 说明 |
| --- | --- | --- | --- |
| 1 | B01 | 获取标本种类列表
../sampletoitem/request/getsampleitemlist | 获取标本种类列表 |
| 2 | B02 | 添加收费项目的标本种类
../sampletoitem/request/addsampleitem | 添加收费项目的标本种类 |
| 3 | B03 | 删除收费项目的标本种类
../materialtoorder/request/altermatorder | 删除收费项目的标本种类 |



#### A 对外公布方法

##### A01 获取收费筛选列表

接口说明：获取收费筛选列表

请求URL：../sampletoitem/request/getstatuslist

代码文件：winning.lis.sampletoitem.service.SampleToItemService

示例URL： 

http://192.168.10.73:15011/lis/sampletoitem/request/getstatuslist?hospitalcode=9999&subsyscode=LIMS

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码 |
| 2 | subsyscode | 子系统代码 | string | 传入子系统代码，允许为空
传入为空时默认为LIMS |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | NODENO | 节点编码 | string | 如：HISITEM_STATUS |
| 2 | NODENAME | 节点名称 | string | 如：状态 |
| 3 | CHILDNODES | 子节点名称 | array<object> |  |
|  |  |  |  |  |
| 1 | CODE | 字典代码 | string | 如：1 |
| 2 | NAME | 字典名称 | string | 如：生化 |
| 3 | DICID | 标准编码 | string | 如：SH |
| 4 | MEMCODE1 | 输入码一 | string | 如：SH |
| 5 | MEMCODE2 | 输入码二 | string | 如：3 |
| 6 | ORDERNO | 排序 | int | 如：1 |
|  |  |  |  |  |
| 1 | NODENO | 节点编码 | string | 如：HISITEM_EXAMINE |
| 2 | NODENAME | 节点名称 | string | 如：检验分类 |
| 3 | CHILDNODES | 子节点名称 | array<object> |  |
|  |  |  |  |  |
| 1 | CODE | 字典代码 | string | 如：1 |
| 2 | NAME | 字典名称 | string | 如：生化 |
| 3 | DICID | 标准编码 | string | 如：SH |
| 4 | MEMCODE1 | 输入码一 | string | 如：SH |
| 5 | MEMCODE2 | 输入码二 | string | 如：3 |
| 6 | ORDERNO | 排序 | int | 如：1 |
|  |  |  |  |  |
| 1 | NODEID | 节点编码 | string | 如：HISITEM_INSTORDER |
| 2 | NODENAME | 节点名称 | string | 如：仪器分类 |
| 3 | CHILDNODES | 子节点名称 | array<object> |  |
|  |  |  |  |  |
| 1 | INSTID | 仪器ID | string | 如：3，对应INSTID |
| 2 | INSTCODE | 仪器编码 | string | 如：HX-21，INSTCODE |
| 3 | INSTNAME | 仪器名称 | string | 如：HX-21，INSTNAME |
| 4 | EXECGROUPCODE | 执行小组代码 | string | 如：SH |
| 5 | EXECGROUPNAME | 执行小组名称 | string | 如：3 |
|  |  |  |  |  |
| 1 | NODEID | 节点编码 | string | 如：HISITEM_BARORDER |
| 2 | NODENAME | 节点名称 | string | 如：条码分组 |
| 3 | CHILDNODES | 子节点名称 | array<object> |  |
|  |  |  |  |  |
| 1 | CODE | 条形码分组代码 | string | 如：4，BAREXAMCODE |
| 2 | NAME | 条形码分组名称 | string | 如：体液（尿），BAREXAMNAME |
| 3 | DICID | 条码分组编码 | string | 如：2，BAREXAMCODE |
| 4 | MEMCODE1 | 输入码一 | string | 如：SH |
| 5 | MEMCODE2 | 输入码二 | string | 如：3 |
| 6 | ORDERNO | 排序 | int | 如：1，BARPRIORITY |
|  |  |  |  |  |
| 1 | NODEID | 节点编码 | string | 如：HISITEM_RETURNRULE |
| 2 | NODENAME | 节点名称 | string | 如：回执单规则 |
| 3 | CHILDNODES | 子节点名称 | array<object> |  |
|  |  |  |  |  |
| 1 | CODE | 规则ID | string | 如：23，RULENO |
| 2 | NAME | 规则名称 | string | 如：23，RULENAME |
| 3 | DICID | 条码分组编码 | string | 如：2，RULENO |
| 4 | MEMCODE1 | 输入码一 | string | 如：333 |
| 5 | MEMCODE2 | 输入码二 | string | 如：2 |
| 6 | ORDERNO | 排序 | string | 如：1 |



#### B 业务类

##### B01	获取标本种类列表

接口说明：获取标本种类列表

请求URL：../sampletoitem/request/getsampleitemlist

代码文件：winning.lis.sampletoitem.service.SampleToItemService

示例URL： 

原型参考：

或者

接口入参： 

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | subsyscode | 子系统代码 | string | 传入子系统代码，允许为空
传入为空时默认为LIMS |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：sampleitenlist 汇总信息 类型： array<object> | 节点名称：sampleitenlist 汇总信息 类型： array<object> | 节点名称：sampleitenlist 汇总信息 类型： array<object> | 节点名称：sampleitenlist 汇总信息 类型： array<object> | 节点名称：sampleitenlist 汇总信息 类型： array<object> |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | SUBSYSCODE | 子系统代码 | string | 如：LIMS |
| 3 | CLASSCODE | 字典分类 | string | 如：样本类型 |
| 4 | SLAVENO | 字典代码 | string | 如：1 |
| 5 | SLAVENAME | 字典名称 | string | 如：血 |
| 6 | REMARK | 备注说明 | string | 如：空腹 |
| 7 | EXTERNCODE | HIS代码 | string | 如：临床项目 |
| 8 | ORDERNO | 排序 | string | 如：2 |
| 9 | MEMCODE1 | 输入码一 | string | 如：x |
| 10 | MEMCODE2 | 输入码二 | int | 如：xue |
| 11 | SLAVENUM | 标准编码 | string | 如：12xue |
| 12 | VISIBLE | 是否可见 | string | 0-不可见 1-可见 |
| 13 | SYSFLAG | 系统内置标志 | string | 0-非系统内置 1-系统内置 |
| 14 | STOPFLAG | 停用标志 | string | 0-正常 1-停用 |
| 15 | GROUPCODE | 标本分组 | string | 如： 常规字典 |



JSON返回示例：

{

        "sampleitenlist": [

            {

                "CODE": "1",

                "NAME": "血",

                "DICID": "2",

                "DICTYPE": "样本类型",

                "EXTERNCODE": null,

                "MEMCODE1": "1",

                "MEMCODE2": "X",

                "SUBSYSCODE": "LIMS",

                "ORDERNO": 0,

                "RESERVEFIELD1": null

            }

        ]

    }

代码实现：

从SLAVE或者SLAVE_P表中获取标本种类列表，字典分类是样本类型

表结构： SLAVE

##### B02	添加收费项目的标本种类

接口说明：添加收费项目的标本种类

请求URL：../sampletoitem/request/addsampleitem

代码文件：winning.lis.sampletoitem.service.SampleToItemService

示例URL： 

http://192.168.10.73:15011/lis/sampletoitem/request/addsampleitem?flag=0&hospitalcode=9999&sampleitemlist=[{"lisordercode":"2342323abc","hisordercode":"测试数据12","hisordername":"测试数据12","samplecode":"1","samplename":"血清血","defaultflag":"1"}

,{"lisordercode":"2342323abc","hisordercode":"测试数据12","hisordername":"测试数据12","samplecode":"2","samplename":"测试血清血","defaultflag":"0"}]

原型参考：

或者 

接口入参： 

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | flag | 主从配置控制 | string | lisordercode配置samplecode时，传0；samplecode配置lisordercode时，传1 |
| 3 | 节点名称  sampleitemlist 类型 array<object> | 节点名称  sampleitemlist 类型 array<object> | 节点名称  sampleitemlist 类型 array<object> | 节点名称  sampleitemlist 类型 array<object> |
| 1 | lisordercode | lis代码 | string | 不允许为空，如：012345 |
| 2 | hisordercode | his代码 | string | 不允许为空，如：12345 |
| 3 | hisordername | his代码名称 | string | 允许为空，如：肝功能三项 |
| 4 | samplecode | 样本代码 | string | 不允许为空，如：2 |
| 5 | samplename | 样本名称 | string | 允许为空，如：血 |
| 6 | defaultflag | 默认标本标志 | string | 允许为空，0-非默认标本 1-默认标本 |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：sampleitemlist   汇总信息 类型： array<object> | 节点名称：sampleitemlist   汇总信息 类型： array<object> | 节点名称：sampleitemlist   汇总信息 类型： array<object> | 节点名称：sampleitemlist   汇总信息 类型： array<object> | 节点名称：sampleitemlist   汇总信息 类型： array<object> |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | LISORDERCODE | LIS标准代码 | string | 如：0509 |
| 3 | HISORDERCODE | HIS收费代码 | string | 如：509 |
| 4 | HISORDERNAME | HIS收费名称 | string | 如：入院治疗费 |
| 5 | SAMPLECODE | 样本代码 | string | 如：2 |
| 6 | SAMPLENAME | 样本名称 | string | 如：生化 |
| 7 | DEFAULTFLAG | 默认标本标志 | string | 0-非默认标本 1-默认标本 |



JSON返回示例：

{

        "sampleitenlist": [

            {

                "HOSPITALCODE": "9999",

                "LISORDERCODE": "2342323abc",

                "HISORDERCODE": "测试数据12",

                "HISORDERNAME": "测试数据12",

                "SAMPLECODE": "1",

                "SAMPLENAME": "血清血",

                "DEFAULTFLAG": "1"

            },

            {

                "HOSPITALCODE": "9999",

                "LISORDERCODE": "2342323abc",

                "HISORDERCODE": "测试数据12",

                "HISORDERNAME": "测试数据12",

                "SAMPLECODE": "2",

                "SAMPLENAME": "测试血清血",

                "DEFAULTFLAG": "0"

            }

        ]

    }

代码实现：

插入到LIS_ORDERSAMPLE表中

LIS_ORDERSAMPLE表中获取项目和标本信息，SLAVE或者SLAVE_P中获取标本信息

表结构：LIS_ORDERSAMPLE\LIS_ITEM

##### B03	删除收费项目的标本种类

接口说明：删除收费项目的标本种类

请求URL：../sampletoitem/request/deletesampleitem

代码文件：winning.lis.sampletoitem.service.SampleToItemService

示例URL： 

http://192.168.10.73:15011/lis/sampletoitem/request/deletesampleitem?flag=0&hospitalcode=9999&sampleitemlist=[{"lisordercode":"2342323abc","samplecode":"1"},{"lisordercode":"2342323abc","samplecode":"2"}]

原型参考：

或者

接口入参： 

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | flag | 主从配置控制 | string | lisordercode配置samplecode时，传0；samplecode配置lisordercode时，传1 |
| 3 | 节点名称  sampleitemlist 类型 array<object> | 节点名称  sampleitemlist 类型 array<object> | 节点名称  sampleitemlist 类型 array<object> | 节点名称  sampleitemlist 类型 array<object> |
| 1 | lisordercode | lis代码 | string | 不允许为空，如：012345 |
| 2 | samplecode | 样本代码 | string | 不允许为空，如：2 |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage |
| 1 | type | 消息类型 | string | SUCCESS-成功 
ERROR-系统出现异常或错误
FAILT-业务逻辑处理失败 |
| 2 | messageCode | 消息代码 | string | 用于区分消息代码 |
| 3 | message | 消息内容 | string | type=ERROR时则为系统错误消息提示内容
type=FAILT时则为业务提示信息 |
| 4 | link | URL链接 | string | 用于提示用户后可以点击查看详情信息，默认为空 |
| 5 | data | 数据集合 | object | 用于存储返回的数据 |



代码实现：

直接删除LIS_ORDERSAMPLE表中数据

表结构：LIS_ORDERSAMPLE

##### B04	获取收费列表的项目信息

接口说明：单击收费项目列表，获取对应列表的项目信息

请求URL：../sampletoitem/request/gethisitemlist

代码文件：winning.lis.sampletoitem.service.SampleToItemService

示例URL： 

http://192.168.10.73:15011/lis/sampletoitem/request/gethisitemlist?hospitalcode=9999&nodeno=HISITEM_STATUS&selectvalue=2

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | nodeno | 选项分类的nodeno | string | 不允许为空，如：HISITEM_RETURNRULE |
| 3 | selectvalue | 选择参数 | string | 不允许为空，在一级菜单“状态”下，1-全部项目、2-在用项目，3-已停用项目、4-未配置条码分组、5-未配置检验仪器、6-未配置回执单规则、7-未配置检验项目，其他菜单下为当前选中项的ID |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | LISORDERCODE | LIS收费代码 | string | 如：0509 |
| 3 | HISORDERCODE | HIS收费代码 | string | 如：509 |
| 4 | HISORDERNAME | HIS收费名称 | string | 如：入院治疗费 |
| 5 | BARCODELABELNAME | 收费简称 | string | 如：生化 |
| 6 | ITEMTYPE | HIS项目类别 | string | 0 临床项目 1 收费项目 9组合项目 |
| 7 | MEMCODE1 | 输入码一 | string | 如：SH |
| 8 | MEMCODE2 | 输入码二 | string | 如：3 |
| 9 | PRICE | 单价 | int | 如：1 |
| 10 | UNIT | 单位 | string | 如： |
| 11 | SAMPLECODE | 标本代码集合 | string | 如：2,45,6 |
| 12 | SAMPLENAME | 标本名称集合 | string | 如： 血，尿 |
| 13 | SELECTFLAG | 选择标识 | boolean | 如：false |



##### B05 搜索收费项目

接口说明：搜索收费项目信息

请求URL：../sampletoitem/request/searchhisitem

代码文件：winning.lis.sampletoitem.service.SampleToItemService

示例URL： 

http://192.168.10.73:15011/lis/sampletoitem/request/searchhisitem?hospitalcode=9999&searchtext=23

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | searchtext | 搜索内容 | string | 允许为空，为空默认搜索全部 |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | LISORDERCODE | LIS收费代码 | string | 如：0509 |
| 3 | HISORDERCODE | HIS收费代码 | string | 如：509 |
| 4 | HISORDERNAME | HIS收费名称 | string | 如：入院治疗费 |
| 5 | BARCODELABELNAME | 收费简称 | string | 如：生化 |
| 6 | ITEMTYPE | HIS项目类别 | string | 0 临床项目 1 收费项目 9组合项目 |
| 7 | MEMCODE1 | 输入码一 | string | 如：SH |
| 8 | MEMCODE2 | 输入码二 | string | 如：3 |
| 9 | PRICE | 单价 | int | 如：1 |
| 10 | UNIT | 单位 | string | 如： |
| 11 | SAMPLECODE | 标本代码集合 | string | 如：2,45,6 |
| 12 | SAMPLENAME | 标本名称集合 | string | 如： 血，尿 |
| 13 | SELECTFLAG | 选择标识 | boolean | 如：false |



### 收费项目对应外送机构维护

#### V 模块接口视图

| NO. | 接口编号 | 接口名称 | 说明 |
| --- | --- | --- | --- |
| 1 | A01 | 外送检测机构列表
../exhospitalitem/request/getexhospitallist | 外送机构列表 |
| 2 | B01 | 获取送检机构收费项目列表
../exhospitalitem/request/getexhospitalitem | 获取送检机构收费项目列表 |
| 3 | B02 | 添加收费项目的外送机构项目
../exhospitalitem/request/addexhospitalitem | 添加收费项目的外送机构项目 |
|  | B03 | 删除收费项目的外送机构项目
../exhospitalitem/request/dropexhospital | 删除收费项目的外送机构项目 |



#### A 对外公布方法

##### A01	外送检测机构列表

接口说明：获取外送检测机构列表

请求URL：../exhospitalitem/request/getexhospitallist

代码文件：winning.lis.exhospitalitem.service.ExHospitalService

示例URL： http://192.168.10.73:15011/lis/exhospitalitem/request/getexhospitallist?hospitalcode=9998

原型参考：



接口入参： 

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：exhospitallist 汇总信息 类型： array<object> | 节点名称：exhospitallist 汇总信息 类型： array<object> | 节点名称：exhospitallist 汇总信息 类型： array<object> | 节点名称：exhospitallist 汇总信息 类型： array<object> | 节点名称：exhospitallist 汇总信息 类型： array<object> |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | HOSPITALNAME | 医疗机构名称 | string | 如：上海7院 |
| 3 | HOSPSHORTNAME | 机构简称 | string | 如：7院 |
| 4 | HOSPBARPRE | 机构简码 | string | 如：shqy |
| 5 | PARENTCODE | 上级机构代码 | string | 如：54 |
| 6 | PARENTNAME | 上级机构名称 | string | 如：蜀山区 |
| 7 | AREA | 上级辖区 | string | 如：临床项目 |
| 8 | HOSPITALLEVAL | 机构级别 | string | 如： |
| 9 | HOSPITALTYPE | 机构类别 | string | 如：社区 |
| 10 | ORGCODE | 组织机构代码 | int | 如：xue |



JSON返回示例：

{

        "exhospitallist": [

            {

                "HOSPITALCODE": "9999",

                "HOSPITALNAME": "上海市第六人民医院",

                "HOSPSHORTNAME": "上海六院",

                "HOSPBARPRE": null,

                "PARENTCODE": null,

                "PARENTNAME": null,

                "AREA": null,

                "HOSPITALLEVAL": null,

                "HOSPITALTYPE": null,

                "ORGCODE": null

            }

        ]

    }

代码实现：

从SYS_HOSPITALINFO中获取所有的医疗机构名称列表，过滤掉hospital的机构

表结构：SYS_HOSPITALINFO

##### A02 获取收费筛选列表

接口说明：获取收费筛选列表

请求URL：../exhospitalitem/request/getstatuslist

代码文件：winning.lis.exhospitalitem.service.ExHospitalService

示例URL： 

http://192.168.10.73:15011/lis/exhospitalitem/request/getstatuslist?hospitalcode=9999&subsyscode=LIMS

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码 |
| 2 | subsyscode | 子系统代码 | string | 传入子系统代码，允许为空
传入为空时默认为LIMS |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | NODENO | 节点编码 | string | 如：HISITEM_STATUS |
| 2 | NODENAME | 节点名称 | string | 如：状态 |
| 3 | CHILDNODES | 子节点名称 | array<object> |  |
|  |  |  |  |  |
| 1 | CODE | 字典代码 | string | 如：1 |
| 2 | NAME | 字典名称 | string | 如：生化 |
| 3 | DICID | 标准编码 | string | 如：SH |
| 4 | MEMCODE1 | 输入码一 | string | 如：SH |
| 5 | MEMCODE2 | 输入码二 | string | 如：3 |
| 6 | ORDERNO | 排序 | int | 如：1 |
|  |  |  |  |  |
| 1 | NODENO | 节点编码 | string | 如：HISITEM_EXAMINE |
| 2 | NODENAME | 节点名称 | string | 如：检验分类 |
| 3 | CHILDNODES | 子节点名称 | array<object> |  |
|  |  |  |  |  |
| 1 | CODE | 字典代码 | string | 如：1 |
| 2 | NAME | 字典名称 | string | 如：生化 |
| 3 | DICID | 标准编码 | string | 如：SH |
| 4 | MEMCODE1 | 输入码一 | string | 如：SH |
| 5 | MEMCODE2 | 输入码二 | string | 如：3 |
| 6 | ORDERNO | 排序 | int | 如：1 |
|  |  |  |  |  |
| 1 | NODEID | 节点编码 | string | 如：HISITEM_INSTORDER |
| 2 | NODENAME | 节点名称 | string | 如：仪器分类 |
| 3 | CHILDNODES | 子节点名称 | array<object> |  |
|  |  |  |  |  |
| 1 | INSTID | 仪器ID | string | 如：3，对应INSTID |
| 2 | INSTCODE | 仪器编码 | string | 如：HX-21，INSTCODE |
| 3 | INSTNAME | 仪器名称 | string | 如：HX-21，INSTNAME |
| 4 | EXECGROUPCODE | 执行小组代码 | string | 如：SH |
| 5 | EXECGROUPNAME | 执行小组名称 | string | 如：3 |
|  |  |  |  |  |
| 1 | NODEID | 节点编码 | string | 如：HISITEM_BARORDER |
| 2 | NODENAME | 节点名称 | string | 如：条码分组 |
| 3 | CHILDNODES | 子节点名称 | array<object> |  |
|  |  |  |  |  |
| 1 | CODE | 条形码分组代码 | string | 如：4，BAREXAMCODE |
| 2 | NAME | 条形码分组名称 | string | 如：体液（尿），BAREXAMNAME |
| 3 | DICID | 条码分组编码 | string | 如：2，BAREXAMCODE |
| 4 | MEMCODE1 | 输入码一 | string | 如：SH |
| 5 | MEMCODE2 | 输入码二 | string | 如：3 |
| 6 | ORDERNO | 排序 | int | 如：1，BARPRIORITY |
|  |  |  |  |  |
| 1 | NODEID | 节点编码 | string | 如：HISITEM_RETURNRULE |
| 2 | NODENAME | 节点名称 | string | 如：回执单规则 |
| 3 | CHILDNODES | 子节点名称 | array<object> |  |
|  |  |  |  |  |
| 1 | CODE | 规则ID | string | 如：23，RULENO |
| 2 | NAME | 规则名称 | string | 如：23，RULENAME |
| 3 | DICID | 条码分组编码 | string | 如：2，RULENO |
| 4 | MEMCODE1 | 输入码一 | string | 如：333 |
| 5 | MEMCODE2 | 输入码二 | string | 如：2 |
| 6 | ORDERNO | 排序 | string | 如：1 |







#### B 业务类

##### B01	获取送检机构收费项目列表

接口说明：获取送检机构收费项目列表

请求URL：../exhospitalitem/request/getexhospitalitem

代码文件：winning.lis.exhospitalitem.service.ExHospitalService

示例URL： http://192.168.10.73:15011/lis/exhospitalitem/request/getexhospitalitem?hospitalcode=9999

原型参考：



接口入参： 

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 不允许为空，如：9999 |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：sampleitenlist 汇总信息 类型： array<object> | 节点名称：sampleitenlist 汇总信息 类型： array<object> | 节点名称：sampleitenlist 汇总信息 类型： array<object> | 节点名称：sampleitenlist 汇总信息 类型： array<object> | 节点名称：sampleitenlist 汇总信息 类型： array<object> |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | LISORDERCODE | lis代码 | string | 如：LIMS |
| 3 | HISORDERCODE | his代码 | string | 如：样本类型 |
| 4 | HISORDERNAME | his代码名称 | string | 如：1 |
| 5 | BARCODELABELNAME | 收费简称 | string | 如：血 |
| 6 | ITEMTYPE | HIS项目类别 | string | 0 临床项目 1 收费项目 9组合项目 |
| 7 | ITEMTYPENAME | HIS项目类别名称 | string | 如：临床项目 |
| 8 | MEMCODE1 | 输入码一 | string | 如：2 |
| 9 | MEMCODE2 | 输入码二 | string | 如：x |
| 10 | PRICE | 价格 | int | 如：100 |
| 11 | UNIT | 单位 | string | 如：元 |
| 12 | MJZBZ | 急诊标志 | string | 如：1 |
| 13 | BARCODEFLAG | 条码标志 | string | 如：0 |
| 14 | STOPFLAG | 停用标志 | string | 0-正常 1-停用 |
| 15 | SAMPLECOMMENT | 采集说明 | string | 如：空腹 |
| 16 | CXREMARK | 采集注意事项 | string | 如：空腹 |
| 17 | EXHOSPFLAG | 外送标志 | string | 如：0 |
| 18 | EXHOSPITALCODE | 外送医疗机构代码 | string | 如：8888 |
| 19 | APPLYLIMITDAYS | 申请时间预警天数 | string | 如：0 |



JSON返回示例：

{

        "sampleitenlist": [

            {

                "HOSPITALCODE": "9999",

                "LISORDERCODE": "01001",

                "HISORDERCODE": "1001",

                "HISORDERNAME": "1",

                "BARCODELABELNAME": null,

                "ITEMTYPE": "0",

                "MEMCODE1": "1",

                "MEMCODE2": "1",

                "PRICE": 1,

                "UNIT": null,

                "MJZBZ": "0",

                "BARCODEFLAG": "0",

                "STOPFLAG": "0",

                "SAMPLECOMMENT": null,

                "CXREMARK": null,

                "EXHOSPFLAG": "0",

                "EXHOSPITALCODE": null,

                "APPLYLIMITDAYS": null,

                "ITEMTYPENAME": "临床项目"

            }

]

}

代码实现：

从LIS_HISITEM中获取对应医疗机构的项目列表

表结构：LIS_HISITEM

##### B02	添加收费项目的外送机构项目

接口说明：添加收费项目的外送机构项目

请求URL：../exhospitalitem/request/addexhospitalitem

代码文件：winning.lis.exhospitalitem.service.ExHospitalService

示例URL： 

原型参考：

或者 

接口入参： 

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | flag | 主从配置控制 | string | lisordercode配置外送机构时，传0；外送机构配置lisordercode时，传1 |
| 2 | 节点名称  exhospitalitem 类型 array<object> | 节点名称  exhospitalitem 类型 array<object> | 节点名称  exhospitalitem 类型 array<object> | 节点名称  exhospitalitem 类型 array<object> |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | lisordercode | lis代码 | string | 不允许为空，如：012345 |
| 3 | targethospitalcode | 检测医疗机构代码 | string | 不允许为空，如：8888 |
| 4 | targetlisordercode | 检测机构收费代码 | string | 不允许为空，如：0125645 |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：exhospitalitem     汇总信息 类型： array<object> | 节点名称：exhospitalitem     汇总信息 类型： array<object> | 节点名称：exhospitalitem     汇总信息 类型： array<object> | 节点名称：exhospitalitem     汇总信息 类型： array<object> | 节点名称：exhospitalitem     汇总信息 类型： array<object> |
| 1 | SOURCEHOSPITALCODE | 送检医疗机构代码 | string | 如：9999 |
| 2 | SOURCELISORDERCODE | 送检机构收费代码 | string | 如：034222 |
| 3 | TARGETHOSPITALCODE | 检测医疗机构代码 | string | 如：8888 |
| 4 | TARGETHOSPITALCODENAME | 检测医疗机构名称 | string | 如：合肥附属医院 |
| 5 | TARGETLISORDERCODE | 检测机构收费代码 | string | 如：0642344 |
| 6 | TARGETHISORDERCODE | 检测机构收费his代码 | string | 如：55555 |
| 7 | TARGETHISORDERNAME | 检测机构收费his名称 | string | 如：55555 |



JSON返回示例：

代码实现：

LIS_HOSPITALTOORDER表中获取配置的外送机构的项目信息，LIS_HISITEM获取配置信息的项目信息

表结构：LIS_HOSPITALTOORDER\LIS_HISITEM

##### B03	删除收费项目的外送机构项目

接口说明：删除收费项目的外送机构项目

请求URL：../exhospitalitem/request/dropexhospital

代码文件：winning.lis.exhospitalitem.service.ExHospitalService

示例URL： 

原型参考：

或者

接口入参： 

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | flag | 主从配置控制 | string | lisordercode配置外送机构时，传0；外送机构配置lisordercode时，传1 |
| 2 | 节点名称  exhospitalitem 类型 array<object> | 节点名称  exhospitalitem 类型 array<object> | 节点名称  exhospitalitem 类型 array<object> | 节点名称  exhospitalitem 类型 array<object> |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | lisordercode | lis代码 | string | 不允许为空，如：012345 |
| 3 | targethospitalcode | 检测医疗机构代码 | string | 不允许为空，如：8888 |
| 4 | targetlisordercode | 检测机构收费代码 | string | 不允许为空，如：0125645 |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage |
| 1 | type | 消息类型 | string | SUCCESS-成功 
ERROR-系统出现异常或错误
FAILT-业务逻辑处理失败 |
| 2 | messageCode | 消息代码 | string | 用于区分消息代码 |
| 3 | message | 消息内容 | string | type=ERROR时则为系统错误消息提示内容
type=FAILT时则为业务提示信息 |
| 4 | link | URL链接 | string | 用于提示用户后可以点击查看详情信息，默认为空 |
| 5 | data | 数据集合 | object | 用于存储返回的数据 |



代码实现：

LIS_HOSPITALTOORDER表中获取配置的外送机构的项目信息

表结构：LIS_HOSPITALTOORDER

##### B04	获取收费列表的项目信息

接口说明：单击收费项目列表，获取对应列表的项目信息

请求URL：../exhospitalitem/request/gethisitemlist

代码文件：winning.lis.exhospitalitem.service.ExHospitalService

示例URL： 

http://192.168.10.73:15011/lis/exhospitalitem/request/gethisitemlist?hospitalcode=9999&nodeno=HISITEM_STATUS&selectvalue=2

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | nodeno | 选项分类的nodeno | string | 不允许为空，如：HISITEM_RETURNRULE |
| 3 | selectvalue | 选择参数 | string | 不允许为空，在一级菜单“状态”下，1-全部项目、2-在用项目，3-已停用项目、4-未配置条码分组、5-未配置检验仪器、6-未配置回执单规则、7-未配置检验项目，其他菜单下为当前选中项的ID |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | LISORDERCODE | LIS收费代码 | string | 如：0509 |
| 3 | HISORDERCODE | HIS收费代码 | string | 如：509 |
| 4 | HISORDERNAME | HIS收费名称 | string | 如：入院治疗费 |
| 5 | BARCODELABELNAME | 收费简称 | string | 如：生化 |
| 6 | ITEMTYPE | HIS项目类别 | string | 0 临床项目 1 收费项目 9组合项目 |
| 7 | MEMCODE1 | 输入码一 | string | 如：SH |
| 8 | MEMCODE2 | 输入码二 | string | 如：3 |
| 9 | PRICE | 单价 | int | 如：1 |
| 10 | UNIT | 单位 | string | 如： |
| 11 | TARGETLISORDERCODE | 检测机构收费代码集合 | string | 如：2,3,6,2 |
| 12 | TARGETLISORDERNAME | 检测机构收费名称集合 | string | 如： 血，尿 |
| 13 | SELECTFLAG | 选择标识 | boolean | 如：false |



##### B05 搜索收费项目

接口说明：搜索收费项目信息

请求URL：../exhospitalitem/request/searchhisitem

代码文件：winning.lis.exhospitalitem.service.ExHospitalService

示例URL： 

http://192.168.10.73:15011/lis/exhospitalitem/request/searchhisitem?hospitalcode=9999&searchtext=23

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码，如：9999 |
| 2 | searchtext | 搜索内容 | string | 允许为空，为空默认搜索全部 |



接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> | 节点名称：hisitemlist 汇总信息 类型： array<object> |
| 1 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 2 | LISORDERCODE | LIS收费代码 | string | 如：0509 |
| 3 | HISORDERCODE | HIS收费代码 | string | 如：509 |
| 4 | HISORDERNAME | HIS收费名称 | string | 如：入院治疗费 |
| 5 | BARCODELABELNAME | 收费简称 | string | 如：生化 |
| 6 | ITEMTYPE | HIS项目类别 | string | 0 临床项目 1 收费项目 9组合项目 |
| 7 | MEMCODE1 | 输入码一 | string | 如：SH |
| 8 | MEMCODE2 | 输入码二 | string | 如：3 |
| 9 | PRICE | 单价 | int | 如：1 |
| 10 | UNIT | 单位 | string | 如： |
| 11 | TARGETLISORDERCODE | 检测机构收费代码集合 | string | 如：2,3,6,2 |
| 12 | TARGETLISORDERNAME | 检测机构收费名称集合 | string | 如： 血，尿 |
| 13 | SELECTFLAG | 选择标识 | boolean | 如：false |





## 辅助字典

### V 模块接口视图

| NO. | 接口编号 | 接口名称 | 说明 |
| --- | --- | --- | --- |
| 1 | A01 | 新增辅助字典数据（业务模块调用使用）
../common/request/adddictdata | 业务模块使用辅助字典时，可以直接将字典信息添加到SLAVE表中 |
| 2 | A02 | 辅助字典初始化
../slave/request/getinitdata | 辅助字典初始化 |
| 3 | B01 | 获取字典项目列表
../slave/request/getslaveitemlist | 获取字典分类项目列表 |
| 4 | B02 | 新增辅助字典项目
../slave/request/alterslavetypeitem | 字典项目添加 |
| 5 | B03 | 修改字典项目
../item/request/deleteslavetypeitem | 字典分类项目删除 |
| 6 | B04 | 删除字典项目
../slave/request/dropslaveitem | 字典项目删除 |
| 7 | B05 | 字典项目停用与启用
../slave/request/setslaveitemstatus | 字典项目停用与启用 |
| 8 | B06 | 数据导入初始化
../slave/request/setinitdata | 项目导入初始化 |
| 9 | B07 | 删除辅助字典列表
../slave/request/dropslave | 删除辅助字典列表 |



### A 对外公布方法

#### A01 新增辅助字典数据（业务模块调用使用）

接口说明：业务模块使用辅助字典时，可以直接将字典信息添加到SLAVE表中

请求URL：../common/request/adddictdata

示例URL：http://192.168.14.253:8080/lis/common/request/adddictdata?dictype=报告备注&dictname=血清不足

场景描述

业务模块使用辅助字典时，可以直接将字典信息添加到SLAVE表中，无需到专门的配置界面进行配置

接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | dictype | 字典类型 | string | 报告备注 |
| 2 | dictname | 字典名称 | string | 字典名称 |



接口出参【ResposeMessage】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage |
| 1 | type | 消息类型 | string | SUCCESS-成功 
ERROR-系统出现异常或错误
FAILT-业务逻辑处理失败 |
| 2 | messageCode | 消息代码 | string | 用于区分消息代码 |
| 3 | message | 消息内容 | string | type=ERROR时则为系统错误消息提示内容
type=FAILT时则为业务提示信息 |
| 4 | link | URL链接 | string | 用于提示用户后可以点击查看详情信息，默认为空 |
| 5 | data | 数据集合 | object | 用于存储返回的数据 |



代码实现：

表结构：SLAVE

SLAVENO编号生成规则：

编号长度最小为四位，首字母为Z

数字位获取最大数字+1，编号不能重复

输入码一：

通过公共函数获取首拼码 参见：900.A02 获取字符拼音码、五笔码、全拼

ORDERNO：

取最大ORDERNO+1

#### A02	辅助字典初始化

接口说明：基础字典配置模块初始化数据

请求URL：../slave/request/getinitdata

代码文件：winning.lis.slave.service.slaveService

示例URL：http://192.168.11.211:15011/lis/slave/request/getinitdata?hospitalcode=& subsyscode=LIMS

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码 |
| 2 | subsyscode | 子系统代码 | string | 传入子系统代码，允许为空
传入为空时默认为LIMS |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：controlsparams类型：object | 节点名称：controlsparams类型：object | 节点名称：controlsparams类型：object | 节点名称：controlsparams类型：object | 节点名称：controlsparams类型：object |
| 1 | OP_EDITITEMTYPE | 编辑项目分类 | boolean | true-允许添加、修改、删除项目分类
false-不允许编辑项目分类 |
| 2 | OP_EDITITEM | 编辑检验项目 | boolean | true-允许添加、修改、删除项目分类
false-不允许编辑项目分类 |
| 节点名称：diclist 类型：array<object> | 节点名称：diclist 类型：array<object> | 节点名称：diclist 类型：array<object> | 节点名称：diclist 类型：array<object> | 节点名称：diclist 类型：array<object> |
| 1 | GROUPCODE | 字典分组 | string | 如：微生物 |
| 2 | SYSFLAG | 系统内置标志 | string | 0-非系统内置 1-系统内置 |
| 3 | CLASSCODE | 字典分类 | array<string> | 如：LIMS |
| 1 | CLASSCODE | 字典名称 | string | 如：标本采集量错误 |
| 2 | SYSFLAG | 系统内置标志 | string | 如：1 |



JSON返回示例：

{

        "controlsparams": {

            "OP_EDITITEMTYPE": true,

            "OP_EDITITEM": true

        },

        "diclist": [

            {

                "GROUPCODE": "常规字典",

                "SYSFLAG": "0",

                "CLASSCODE": [

                    {

                        "CLASSCODE": "报告备注",

                        "SYSFLAG": "0"

                    }

                ]

            },

            {

                "GROUPCODE": "系统内置",

                "SYSFLAG": "1",

                "CLASSCODE": [

                    {

                        "CLASSCODE": "TAT时间周期",

                        "SYSFLAG": "1"

                    }

                ]

            }

        ]

    }

代码实现：

查询表slave和slave_p获取数据

表结构：slave和slave_p

#### A03	辅助字典差异分类获取

接口说明：基础字典配置模块初始化界面中分类导航数据获取

请求URL：../slave/request/getsortdata

代码文件：winning.lis.slave.service.slaveService

示例URL：

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码 |
| 2 | subsyscode | 子系统代码 | string | 传入子系统代码，允许为空
传入为空时默认为LIMS |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | CLASSCODE | 字典分类 | string | 如：微生物 |
| 2 | NUM | 字典分类数量 | string | 如：20 |



JSON返回示例：

[

        {

            "NUM": 92,

            "CLASSCODE": "标本种类"

        },

        {

            "NUM": 3,

            "CLASSCODE": "采集方式"

        },

        {

            "NUM": 4,

            "CLASSCODE": "打印排序"

        },

        {

            "NUM": 8,

            "CLASSCODE": "质控规则"

        }

    ]

代码实现：

查询表slave和slave_p获取数据

表结构：slave和slave_p



### B 业务类

#### B01	获取字典项目列表

接口说明：字典分类对应项目

请求URL：../slave/request/getslaveitemlist

代码文件：winning.lis.slave.service.slaveService

示例URL：http://192.168.11.211:15011/lis/slave/request/getslaveitemlist?hospitalcode=& subsyscode=LIMS&classcode=检验分类&groupcode=常规字典

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码 |
| 2 | subsyscode | 子系统代码 | string | 传入子系统代码，允许为空
传入为空时默认为LIMS |
| 3 | classcode | 字典分类 | string | 不允许为空，传入字典分类， 如检验类别 |
| 4 | sysflag | 系统内置标志 | string | 不允许为空，如：1 |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- | --- |
| 节点名称：itemlist 汇总信息 类型： array<object> | 节点名称：itemlist 汇总信息 类型： array<object> | 节点名称：itemlist 汇总信息 类型： array<object> | 节点名称：itemlist 汇总信息 类型： array<object> | 节点名称：itemlist 汇总信息 类型： array<object> | 节点名称：itemlist 汇总信息 类型： array<object> |
| 1 | HOSPITALCODE | 医疗机构代码 | 医疗机构代码 | string | 如：9999 |
| 2 | SUBSYSCODE | 子系统代码 | 子系统代码 | string | 如：LIMS |
| 3 | CLASSCODE | 字典分类 | 字典分类 | string | 如; 检验类别 |
| 4 | SLAVENO | 字典代码 | 字典代码 | string | 如：1 |
| 5 | SLAVENAME | 字典名称 | 字典名称 | string | 如：生化 |
| 6 | SLAVENUM | 标准编码 | 标准编码 | string | 如：SH |
| 7 | MEMCODE1 | 输入码一 | 输入码一 | string | 如：SH |
| 8 | MEMCODE2 | 输入码二 | 输入码二 | string | 如：3 |
| 9 | EXTERNCODE | HIS代码 | HIS代码 | string | 如：1 |
| 10 | ORDERNO | 排序 | 排序 | int | 如：1 |
| 11 | REMARK | 备注说明 | 备注说明 | string | 如： |
| 12 | VISIBLE | 是否可见 | 是否可见 | string | 0-不可见 1-可见 |
| 13 | SYSFLAG | 系统内置标志 | 系统内置标志 | string | 0-非系统内置 1-系统内置 |
| 14 | GROUPCODE | 字典分组 | 字典分组 | string | 如：常规字典 |
| 15 | STOPFLAG | 项目停用标志 | 项目停用标志 | string | 标识该项目是否停用，0-启用，1-停用 |



代码实现：

从SLAVE表中读取数据

表结构： SLAVE

#### B02	新增辅助字典项目

接口说明：字典项目添加

请求URL：../slave/request/addslaveitem

代码文件：winning.lis.slave.service.slaveService

示例URL：http://192.168.10.73:15011/lis/slave/request/addslaveitem?hospitalcode=& subsyscode=LIMS&classcode=检验类别&slaveno=0013&slavename=测试数据13 &groupcode=常规字典

原型参考：





接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码 |
| 2 | subsyscode | 子系统代码 | string | 传入子系统代码，允许为空
传入为空时默认为LIMS |
| 3 | classcode | 选中节点名称 | string | 不允许为空，如：检验类别 |
| 4 | slaveno | 代码 | string | 不允许为空，如：11，对应原型的代码 |
| 5 | slavename | 名称 | string | 不允许为空，如：标本种类错误，对应原型的名称 |
| 6 | slavenum | 标准编码 | string | 允许为空，如：标本种类错误，对应原型的标准编码 |
| 7 | externcode | HIS编码 | string | 允许为空，如：血液检查，对应原型的外部编码 |
| 8 | memcode1 | 输入码一 | string | 允许为空，如：血液检查，对应原型的输入码一 |
| 9 | memcode2 | 输入码二 | string | 允许为空，如：血液检查，对应原型的输入码二 |
| 10 | orderno | 序号 | string | 允许为空，如：1 |
| 11 | remark | 备注说明 | string | 允许为空，如：血液检查，对应原型的备注说明 |
| 12 | groupcode | 根节点名称 | string | 不允许为空，如：常规字典 |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- | --- |
| 节点名称：iteminfo 汇总信息 类型： object | 节点名称：iteminfo 汇总信息 类型： object | 节点名称：iteminfo 汇总信息 类型： object | 节点名称：iteminfo 汇总信息 类型： object | 节点名称：iteminfo 汇总信息 类型： object | 节点名称：iteminfo 汇总信息 类型： object |
| 1 | HOSPITALCODE | 医疗机构代码 | 医疗机构代码 | string | 如：9999 |
| 2 | SUBSYSCODE | 子系统代码 | 子系统代码 | string | 如：LIMS |
| 3 | CLASSCODE | 字典分类 | 字典分类 | string | 如; 检验类别 |
| 4 | SLAVENO | 字典代码 | 字典代码 | string | 如：001 |
| 5 | SLAVENAME | 字典名称 | 字典名称 | string | 如：标本种类错误 |
| 6 | SLAVENUM | 标准编码 | 标准编码 | string | 如：WBC |
| 7 | MEMCODE1 | 输入码一 | 输入码一 | string | 如： |
| 8 | MEMCODE2 | 输入码二 | 输入码二 | string | 如：3 |
| 9 | EXTERNCODE | HIS代码 | HIS代码 | string | 如： |
| 10 | ORDERNO | 排序 | 排序 | int | 如：1 |
| 11 | REMARK | 备注说明 | 备注说明 | string | 如：项目再用 |
| 12 | VISIBLE | 是否可见 | 是否可见 | string | 0-不可见 1-可见 |
| 13 | SYSFLAG | 系统内置标志 | 系统内置标志 | string | 0-非系统内置 1-系统内置 前台允许查看,不允许编辑和删除 |
| 14 | GROUPCODE | 字典分类 | 字典分类 | string | 如：常规字典 |
| 15 | STOPFLAG | 项目停用标志 | 项目停用标志 | string | 标识该项目是否停用，0-启用，1-停用 |



代码实现：

判断SLAVE表中的项目代码是否存在，不存在添加，否则提示前端

表结构：SLAVE

#### B03	修改字典项目

接口说明：修改字典项目

请求URL：../slave/request/modifyslaveitem

代码文件：winning.lis.slave.service.slaveService

示例URL：

http://192.168.11.211:15011/lis/slave/request/modifyslaveitem?hospitalcode=9999&slavenum=&memcode2=&memcode1=&orderno=1&externcode=&subsyscode=LIMS&classcode=检验类别&remark=修改测试&slaveno=003&preslaveno=003&slavename=修改测试数据

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码 |
| 2 | subsyscode | 子系统代码 | string | 传入子系统代码，允许为空
传入为空时默认为LIMS |
| 3 | classcode | 字典分类 | string | 不允许为空，如：检验类别 |
| 4 | preslaveno | 原字典代码 | string | 不允许为空，如：11，对应原型的代码 |
| 5 | slaveno | 字典代码 | string | 不允许为空，如：11，对应原型的代码 |
| 6 | slavename | 字典名称 | string | 不允许为空，如：标本种类错误，对应原型的名称 |
| 7 | preslavename | 原字典名称 | string | 不允许为空，如：标本种类错误，对应原型的名称 |
| 8 | slavenum | 标准编码 | string | 允许为空，如：SH，对应原型的标准编码 |
| 9 | externcode | 外部编码 | string | 允许为空，如：11，对应原型的外部编码 |
| 10 | memcode1 | 输入码一 | string | 允许为空，如：11，对应原型的输入码一 |
| 11 | memcode2 | 输入码二 | string | 允许为空，如：SH，对应原型的输入码二 |
| 12 | remark | 备注说明 | string | 允许为空，对应原型的备注说明 |
| 13 | orderno | 排序 | string | 排列顺序，如：2 |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- | --- |
| 节点名称：slaveiteminfo 汇总信息 类型： object | 节点名称：slaveiteminfo 汇总信息 类型： object | 节点名称：slaveiteminfo 汇总信息 类型： object | 节点名称：slaveiteminfo 汇总信息 类型： object | 节点名称：slaveiteminfo 汇总信息 类型： object | 节点名称：slaveiteminfo 汇总信息 类型： object |
| 1 | HOSPITALCODE | 医疗机构代码 | 医疗机构代码 | string | 如：9999 |
| 2 | SUBSYSCODE | 子系统代码 | 子系统代码 | string | 如：LIMS |
| 3 | CLASSCODE | 字典分类 | 字典分类 | string | 如; 检验类别 |
| 4 | SLAVENO | 字典代码 | 字典代码 | string | 如：001 |
| 5 | SLAVENAME | 字典名称 | 字典名称 | string | 如：标本种类错误 |
| 6 | SLAVENUM | 标准编码 | 标准编码 | string | 如：WBC |
| 7 | MEMCODE1 | 输入码一 | 输入码一 | string | 如： |
| 8 | MEMCODE2 | 输入码二 | 输入码二 | string | 如：3 |
| 9 | EXTERNCODE | HIS代码 | HIS代码 | string | 如： |
| 10 | ORDERNO | 排序 | 排序 | int | 如：1 |
| 11 | REMARK | 备注说明 | 备注说明 | string | 如：项目再用 |
| 12 | VISIBLE | 是否可见 | 是否可见 | string | 0-不可见 1-可见 |
| 13 | SYSFLAG | 系统内置标志 | 系统内置标志 | string | 0-非系统内置 1-系统内置 前台允许查看,不允许编辑和删除 |
| 14 | GROUPCODE | 字典分类 | 字典分类 | string | 如：常规字典 |
| 15 | STOPFLAG | 项目停用标志 | 项目停用标志 | string | 标识该项目是否停用，0-启用，1-停用 |



代码实现：

判断SLAVE表中的项目代码是否存在，不存在提示前端，否则修改

表结构： SLAVE

#### B04	删除字典项目

接口说明：字典项目删除

请求URL：../slave/request/deleteslaveitem

代码文件：winning.lis.slave.service.slaveService

示例URL： 

http://192.168.11.211:15011/lis/slave/request/deleteslaveitem?hospitalcode=9999&subsyscode=LIMS&classcode=检验类别&slaveno=009

业务场景： 



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码 |
| 2 | subsyscode | 子系统代码 | string | 传入子系统代码，允许为空
传入为空时默认为LIMS |
| 3 | classcode | 字典分类 | string | 传入项目字典分类，如：检验类别 |
| 4 | slaveno | 字典代码 | string | 不允许为空，如：11，对应原型的代码 |
| 5 | slavename | 字典名称 | string | 不允许为空，如：体液 |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage |
| 1 | type | 消息类型 | string | SUCCESS-成功 
ERROR-系统出现异常或错误
FAILT-业务逻辑处理失败 |
| 2 | messageCode | 消息代码 | string | 用于区分消息代码 |
| 3 | message | 消息内容 | string | type=ERROR时则为系统错误消息提示内容
type=FAILT时则为业务提示信息 |
| 4 | link | URL链接 | string | 用于提示用户后可以点击查看详情信息，默认为空 |
| 5 | data | 数据集合 | object | 用于存储返回的数据 |



特殊说明

即使type为success，也需要判断message是否为空，不为空，显示其内容。

代码实现：

判断SLAVE表中的项目代码是否存在，不存在提示前端，否则删除

表结构：SLAVE

#### B05	字典项目停用与启用

接口说明：字典项目停用与启用

请求URL：../slave/request/setslaveitemstatus

代码文件：winning.lis.slave.service.slaveService

示例URL： 

http://192.168.11.211:15011/lis/slave/request/setslaveitemstatus?hospitalcode=9999&subsyscode=LIMS&classcode=检验类别&slaveno=0010&stopflag=1

业务场景：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码 |
| 2 | subsyscode | 子系统代码 | string | 传入子系统代码，允许为空
传入为空时默认为LIMS |
| 3 | classcode | 字典分类 | string | 不允许为空，如：检验类别 |
| 4 | slaveno | 字典代码 | string | 不允许为空，如：11，对应原型的代码 |
| 5 | stopflag | 项目停用启用标识 | string | 允许为空，默认为0，0-表示启用，1-停用 |
| 6 | slavename | 字典名称 | string | 不允许为空，如：体液 |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- | --- |
| 节点名称：slaveiteminfo 汇总信息 类型： object | 节点名称：slaveiteminfo 汇总信息 类型： object | 节点名称：slaveiteminfo 汇总信息 类型： object | 节点名称：slaveiteminfo 汇总信息 类型： object | 节点名称：slaveiteminfo 汇总信息 类型： object | 节点名称：slaveiteminfo 汇总信息 类型： object |
| 1 | HOSPITALCODE | 医疗机构代码 | 医疗机构代码 | string | 如：9999 |
| 2 | SUBSYSCODE | 子系统代码 | 子系统代码 | string | 如：LIMS |
| 3 | CLASSCODE | 字典分类 | 字典分类 | string | 如; 检验类别 |
| 4 | SLAVENO | 字典代码 | 字典代码 | string | 如：001 |
| 5 | SLAVENAME | 字典名称 | 字典名称 | string | 如：标本种类错误 |
| 6 | SLAVENUM | 标准编码 | 标准编码 | string | 如：WBC |
| 7 | MEMCODE1 | 输入码一 | 输入码一 | string | 如： |
| 8 | MEMCODE2 | 输入码二 | 输入码二 | string | 如：3 |
| 9 | EXTERNCODE | HIS代码 | HIS代码 | string | 如： |
| 10 | ORDERNO | 排序 | 排序 | int | 如：1 |
| 11 | REMARK | 备注说明 | 备注说明 | string | 如：项目再用 |
| 12 | VISIBLE | 是否可见 | 是否可见 | string | 0-不可见 1-可见 |
| 13 | SYSFLAG | 系统内置标志 | 系统内置标志 | string | 0-非系统内置 1-系统内置 前台允许查看,不允许编辑和删除 |
| 14 | GROUPCODE | 字典分类 | 字典分类 | string | 如：常规字典 |
| 15 | STOPFLAG | 项目停用标志 | 项目停用标志 | string | 标识该项目是否停用，0-启用，1-停用 |



代码实现：

判断SLAVE表中的项目代码是否存在，不存在提示前端，否则设置状态

表结构： SLAVE

#### B06	数据导入初始化—废弃不应，设计修改

接口说明：项目导入初始化

请求URL：../slave/request/setinitdata

代码文件：winning.lis.slave.service.slaveService

示例URL：

业务场景：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码 |
| 2 | subsyscode | 子系统代码 | string | 传入子系统代码，允许为空
传入为空时默认为LIMS |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：controlsparams类型：object | 节点名称：controlsparams类型：object | 节点名称：controlsparams类型：object | 节点名称：controlsparams类型：object | 节点名称：controlsparams类型：object |
| 1 | OP_EDITITEMTYPE | 编辑项目分类 | boolean | true-允许添加、修改、删除项目分类
false-不允许编辑项目分类 |
| 2 | OP_EDITITEM | 编辑检验项目 | boolean | true-允许添加、修改、删除项目分类
false-不允许编辑项目分类 |
| 节点名称：diclist 类型：array<object> | 节点名称：diclist 类型：array<object> | 节点名称：diclist 类型：array<object> | 节点名称：diclist 类型：array<object> | 节点名称：diclist 类型：array<object> |
| 1 | GROUPCODE | 字典分组 | string | 如：微生物 |
| 2 | CLASSCODE | 字典分类 | array<string> | 如：LIMS |



代码实现：

获取SLAVE表中已经存在的数据，去掉已经存在的数据，然后从SLAVE_P表中获取数据

插入到SLAVE表中

表结构： SLAVE\SLAVE_P

#### B07	删除辅助字典

接口说明：删除辅助字典

请求URL：../slave/request/deleteslave

代码文件：winning.lis.slave.service.slaveService

示例URL：http://192.168.11.211:15011/lis/slave/request/deleteslave?hospitalcode=& subsyscode=LIMS&classcode=检验类别 

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码 |
| 2 | subsyscode | 子系统代码 | string | 传入子系统代码，允许为空
传入为空时默认为LIMS |
| 3 | classcode | 字典分类 | string | 不允许为空，传入字典分类， 如检验类别 |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage |
| 1 | type | 消息类型 | string | SUCCESS-成功 
ERROR-系统出现异常或错误
FAILT-业务逻辑处理失败 |
| 2 | messageCode | 消息代码 | string | 用于区分消息代码 |
| 3 | message | 消息内容 | string | type=ERROR时则为系统错误消息提示内容
type=FAILT时则为业务提示信息 |
| 4 | link | URL链接 | string | 用于提示用户后可以点击查看详情信息，默认为空 |
| 5 | data | 数据集合 | object | 用于存储返回的数据 |



删除提示信息：

即使type为success，也需要判断message是否为空，不为空，显示其内容。

您要删除的是: 【classcode】分类及其所有的字典项目 是否继续？

代码实现：

将SLAVE表中的数据设置停用

表结构：SLAVE

#### B08	获取分类字典项目

接口说明：获取分类字典项目

请求URL：../slave/request/getclasscodeitems

代码文件：winning.lis.slave.service.slaveService

示例URL： 

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码 |
| 2 | classcode | 字典分类 | string | 不允许为空，如：检验类别 |
| 3 | subsyscode | 子系统代码 | string | 传入子系统代码，允许为空
传入为空时默认为LIMS |



接口出参【ResposeMessage.data-> object】：

| NO. | 变量名 | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- | --- |
| 1 | subsyscode | 子系统代码 | 子系统代码 | string | 如：LIMS |
| 2 | classcode | 字典分类 | 字典分类 | string | 如; 检验类别 |
| 3 | slaveno | 字典代码 | 字典代码 | string | 如：001 |
| 4 | slavename | 字典名称 | 字典名称 | string | 如：标本种类错误 |
| 5 | slavenum | 标准编码 | 标准编码 | string | 如：WBC |
| 6 | memcode1 | 输入码一 | 输入码一 | string | 如： |
| 7 | memcode2 | 输入码二 | 输入码二 | string | 如：3 |
| 8 | externcode | HIS代码 | HIS代码 | string | 如： |
| 9 | orderno | 排序 | 排序 | int | 如：1 |
| 10 | remark | 备注说明 | 备注说明 | string | 如：项目再用 |
| 11 | visible | 是否可见 | 是否可见 | string | 0-不可见 1-可见 |
| 12 | sysflag | 系统内置标志 | 系统内置标志 | string | 0-非系统内置 1-系统内置 前台允许查看,不允许编辑和删除 |
| 13 | groupcode | 字典分类 | 字典分类 | string | 如：常规字典 |



Json返回数据

[

        {

            "classcode": "质控规则",

            "engname": "",

            "externcode": "1",

            "groupcode": "",

            "memcode1": "",

            "memcode2": "",

            "orderno": "",

            "remark": "N个连续的质控测定结果同时超过（平均数）±（x倍标准差），为违背此规则",

            "reservefield1": "",

            "reservefield2": "",

            "reservefield3": "",

            "reservefield4": "",

            "reservefield5": "",

            "slavename": "标准差倍数 N-xS[连续N个结果超过x倍标准差]",

            "slaveno": "1",

            "slavenum": "0",

            "subsyscode": "LIMS",

            "sysflag": "0",

            "visible": "1"

        }

]

代码实现：

表结构： SLAVE\SLAVE_P

#### B09	导入字典分类项目

接口说明：导入字典分类项目

请求URL：../slave/request/addclasscodeitems

代码文件：winning.lis.slave.service.slaveService

示例URL： 

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码 |
| 2 | classcode | 字典分类 | string | 不允许为空，如：检验类别 |
| 3 | subsyscode | 子系统代码 | string | 传入子系统代码，允许为空
传入为空时默认为LIMS |
| 4 | slaveno | 字典代码 | array<string> | 不允许为空，如：001 |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage |
| 1 | type | 消息类型 | string | SUCCESS-成功 
ERROR-系统出现异常或错误
FAILT-业务逻辑处理失败 |
| 2 | messageCode | 消息代码 | string | 用于区分消息代码 |
| 3 | message | 消息内容 | string | type=ERROR时则为系统错误消息提示内容
type=FAILT时则为业务提示信息 |
| 4 | link | URL链接 | string | 用于提示用户后可以点击查看详情信息，默认为空 |
| 5 | data | 数据集合 | object | 用于存储返回的数据 |



代码实现：

表结构： SLAVE



#### B10	批量导入字典分类项目

接口说明：批量导入字典分类项目

请求URL：../slave/request/importclasscodeitems

代码文件：winning.lis.slave.service.slaveService

示例URL： 

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码 |
| 2 | subsyscode | 子系统代码 | string | 传入子系统代码，允许为空
传入为空时默认为LIMS |
| 3 | classcode | 字典分类 | array<string> | 不允许为空，如：检验类别 |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage |
| 1 | type | 消息类型 | string | SUCCESS-成功 
ERROR-系统出现异常或错误
FAILT-业务逻辑处理失败 |
| 2 | messageCode | 消息代码 | string | 用于区分消息代码 |
| 3 | message | 消息内容 | string | type=ERROR时则为系统错误消息提示内容
type=FAILT时则为业务提示信息 |
| 4 | link | URL链接 | string | 用于提示用户后可以点击查看详情信息，默认为空 |
| 5 | data | 数据集合 | object | 用于存储返回的数据 |



代码实现：

表结构： SLAVE

## 报告管理

### 模块接口视图

| NO. | 接口编号 | 接口名称 | 说明 |
| --- | --- | --- | --- |
| 1 | 100.A01 | 获取当前计算机默认管理的仪器列表
../reportinput/request/getclientinstlist | 获取当前电脑管理的仪器列表，用于快速切换仪器使用 |
| 2 | 900.B01 | 获取模块快捷栏菜单
../common/request/getmoduleshortcut | 用于获取模块对应的快捷菜单，含菜单名称及图标 |
| 3 | 100.B01 | 检测报告项目多做或少做
../reportinput/request/checkreportitems | 检测当前报告项目多做或少做，判断当前项目数与开单项目要求的检测项目是否一致 |
| 4 | 300.B01 | 获取患者最近检验报告（按检验分类）
../patview/request/getlabreportbyexamclass | 获取患者最近检验报告，按检验分类返回报告及报告检查天数 |
| 5 | B02 | 获取报告信息
../reportinput/request/getreportinfo | 根据仪器id、样本号、检测日期或报告单号获取报告信息 |
| 6 | 100.R01 | 获取最大样本号
../reportinput/request/getmaxavailabletechno | 根据仪器及检测日期获取最大可用样本号 |
| 7 | 100.R02 | 获取仪器属性及控制
../reportinput/request/getinstproperty | 获取仪器属性及控制属性 |
| 8 | 100.B03 | 判断当前保存的报告与后台存储是否是同一个人
../reportinput/request/checkissamereport | 保存报告时，判断当前的患者信息与后台存储是否一致，不一致需要提示 |
| 9 | 100.R03 | 获取病人医嘱
../reportinput/request/getpatorderlist | 获取当前病人未确认医嘱和已确认医嘱，医嘱需通过仪器进行过滤 |
| 10 | 100.B04 | 记录报告日志
../reportinput/request/writereportlog | 保存报告的一系统的操作日志，一般在报告某个操作成功后进行记录，比如保存成功后记录 |
| 11 | 100.B05 | 查询报告日志
../reportinput/request/getreportlog | 查询报告详细信息时，获取报告详细操作日志 |
| 12 | 100.B06 | 记录报告修改记录
内部服务调用 | 保存报告后成功后，记录当前报告结果到修改记录中 |
| 13 | 100.B07 | 撤销条码入库
../reportinput/request/cancelbbrk | 条码入库后，可能会报告进行撤销入库 |
| 14 | 100.R04 | 获取仪器对应的收费项目
../reportinput/request/getinstexamorder | 获取仪器可检测的收费项目 |
| 15 | 100.R05 | 保存报告及收费项目
../reportinput/request/savereportdata | 保存报告的病人信息及收费项目操作 |
| 16 | 100.V01 | 判断报告必须通过的审核条件
../reportinput/request/verifymustcondition | 审核报告时，需判断当前报告的审核条件是否满足，校验判断 |





### 初始化数据

#### 获取当前计算机默认管理的仪器列表

接口说明：获取当前电脑管理的仪器列表，用于快速切换仪器使用

请求URL：../reportinput/request/getclientinstlist

示例URL：http://192.168.14.253:8080/lis/reportinput/request/getclientinstlist

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：clientinstlist  类型：array<object> | 节点名称：clientinstlist  类型：array<object> | 节点名称：clientinstlist  类型：array<object> | 节点名称：clientinstlist  类型：array<object> | 节点名称：clientinstlist  类型：array<object> |
| 1 | CLIENTPCNAME | 客户端 | string | 格式：计算机名（ip地址） |
| 2 | INSTID | 仪器ID | string | 仪器id，如：909001 |
| 3 | INSTCODE | 仪器代码 | string | 仪器代码，如：XE-2100 |
| 4 | INSTNAME | 仪器名称 | string | 仪器名称，如：XE-2100 |
| 5 | DEFAULTFLAG | 默认标志 | string | 0-不默认
1-默认仪器，一台客户端只能设置一台默认仪器，用于初始化时，默认加载的仪器 |
| 6 | ORDERNO | 序号 | number | 排序序号，根据此字段来显示快捷菜单的顺序 |



后台返回示例：



JSON返回示例：

[

{

    "CLIENTPCNAME":"CZQ(192.168.14.212)",

    "INSTID":"518",

    "INSTCODE":"XE-2100",

    "INSTNAME":"XE-2100",

    "DEFAULTFLAG":"1",

    "ORDERNO":"1"

},

{

    "CLIENTPCNAME":"CZQ(192.168.14.212)",

    "INSTID":"529",

    "INSTCODE":"CA7000",

    "INSTNAME":"CA7000",

    "DEFAULTFLAG":"0",

    "ORDERNO":"2"

}

]

代码实现：

表结构：LIS_CLIENTINST

代码实现：

停用仪器不能获取出来

只能获取用户有权限的仪器(待框架设计处理）

参考SQL：

SELECT CLIENTPCNAME,A.INSTID,B.INSTCODE,B.INSTNAME,A.DEFAULTFLAG,A.ORDERNO

FROM LIS_CLIENTINST A

INNER JOIN LIS_INSTRUMENT B ON A.INSTID = B.INSTID 

WHERE A.HOSPITALCODE = '9999' AND A.USERID = '001' AND B.STOPFLAG <> '1'

###  业务类

####  检测报告项目多做或少做

接口说明：检测当前报告项目多做或少做，判断当前项目数与开单项目要求的检测项目是否一致

请求URL：../reportinput/request/checkreportitems

示例URL：http://192.168.14.253:8080/lis/reportinput/request/checkreportitems?applyno=122333

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | applyno | 报告单号 | string | 传入当前报告单号，如果报告单号<=0，则不需要交互后端，直接清除项目提示信息 |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：checkreportitems   类型：array<object> | 节点名称：checkreportitems   类型：array<object> | 节点名称：checkreportitems   类型：array<object> | 节点名称：checkreportitems   类型：array<object> | 节点名称：checkreportitems   类型：array<object> |
| 1 | APPLYNO | 报告单号 | string | 报告单号 |
| 2 | HISORDERCODE | 项目代码 | string | 项目代码集合，多项目时，逗号分隔。
如：1782001,1892830 |
| 3 | HISORDERNAME | 项目名称 | string | 项目名称集合，多项目时，逗号分隔。
如：血常规，C反应蛋白 |
| 4 | CHARGEAMOUNT | 收费金额 | string | 收费总金额，如：50.0 |
| 5 | HINTCONTENT | 提示内容 | string | 提示信息，存在空的情况
如：多检测2项(RET#、RDW) |
| 6 | HINTTYPE | 提示类型 | string | W-警告类型，字体变黄色
N-正常提示信息，字体蓝色 |



提示信息规范：



后台返回示例：



JSON返回示例：

[

{

    "APPLYNO":"89382923",

    "HISORDERCODE":"1782001,1892830",

    "HISORDERNAME":"血常规，C反应蛋白",

    "CHARGEAMOUNT":"50",

    "HINTCONTENT":"多检测2项(RET#、RDW)",

    "HINTTYPE":"W"

}

]

代码实现：

表结构：LIS_LIST\LIS_ACCEPTITEMS\LIS_RESULT\LIS_ORDERTOITEM

表结构关系：

LIS_LIST.APPLYNO = LIS_ACCEPTITEMS.APPLYNO

LIS_ACCEPTITEMS.LISORDERCODE = LIS_ORDERTOITEM.LISORDERCODE

LIS_LIST.APPLYNO = LIS_RESULT.APPLYNO

#### 获取报告信息

接口说明：根据仪器id、样本号、检测日期或报告单号获取报告信息

请求URL：../reportinput/request/getreportinfo

示例URL：http://192.168.14.253:8080/lis/reportinput/request/getreportinfo?applyno=-1&instid=518&exectime=2017-06-23&sampletype&techno=520

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | applyno | 报告单号 | string | 传入当前报告单号，如果报告单号<=0,表示当前为新报告，通过仪器+检测日期+样本分组+样本号来处理
如果>0，则优先通过applyno获取，但需比对仪器样本号等信息，避免串号 |
| 2 | instid | 仪器id | string | 仪器id，如：518 |
| 3 | exectime | 检测日期 | string | 检测日期，如：2017-06-23 |
| 4 | sampletype | 样本分组 | string | 样本分组，如：G |
| 5 | techno | 样本号 | string | 样本号，如：1001 |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：checkcontrol   类型：object | 节点名称：checkcontrol   类型：object | 节点名称：checkcontrol   类型：object | 节点名称：checkcontrol   类型：object | 节点名称：checkcontrol   类型：object |
| 报告控制信息 | 报告控制信息 | 报告控制信息 | 报告控制信息 | 报告控制信息 |
| 1 | EDITENABLE | 允许编辑 | string | true-允许编辑 false-不允许编辑
如：审核发布的报告、超出仪器修改天数的报告等 |
| 2 | EDITFIELD | 编辑字段 | string | a、ALL-所有字段均允许编辑
具体可编辑字段集合，如：PATNAME,REMARK-患者姓名及备注内容允许编辑 |
| 3 | ITEMADD | 上传项目 | string | true-允许上传收费或临床项目
false-不允许上传收费或临床项目，上传收费区域不显示 |
| 4 | ITEMCONFIRM | 允许确认 | string | true-允许确认收费或临床项目
false-不允许确认收费或临床项目 |
| 5 | ITEMREFUSE | 拒绝医嘱 | string | true-允许拒绝收费或临床项目 |
| 6 | ITEMCHARGE | 允许收费 | string | true-允许收取收费或临床项目费用
false-不允许收取收费或临床项目费用 |
| 7 | ITEMDISCHARGE | 允许退费 | string | true-允许退费
false-不允许退费 |
| 8 | VIEWRESULT | 查看结果 | string | true-允许查看结果
false-不允许查看结果 |
| 9 | VIEWPATINFO | 查看病人信息 | string | true-允许查看病人信息
false-不允许查看病人信息 |
| 节点名称：reportinfo   类型：object | 节点名称：reportinfo   类型：object | 节点名称：reportinfo   类型：object | 节点名称：reportinfo   类型：object | 节点名称：reportinfo   类型：object |
| 报告详细信息 | 报告详细信息 | 报告详细信息 | 报告详细信息 | 报告详细信息 |
| 1 | APPLYNO | 报告单号 | string | 报告单号 |
| 2 | HOSPITALCODE | 机构代码 | string | 机构代码 |
| 3 | EXECTIME | 检测日期 | string | 检测日期，如：2017-06-23 |
| 4 | INSTID | 仪器ID | string | 仪器ID，如：518 |
| ........中间内容部分见LIS_LIST表结构内容，以下为额外返回数据部分 | ........中间内容部分见LIS_LIST表结构内容，以下为额外返回数据部分 | ........中间内容部分见LIS_LIST表结构内容，以下为额外返回数据部分 | ........中间内容部分见LIS_LIST表结构内容，以下为额外返回数据部分 | ........中间内容部分见LIS_LIST表结构内容，以下为额外返回数据部分 |
|  | RESULTCNT | 检测结果数 | number | 检测结果数量 |
|  | RESULTABNCNT | 异常结果数 | number | 异常结果数量 |
|  | STATUSDESC | 报告状态 | string | 如：初始报告、已审核、已发布、已打印等 |



提示信息规范：



JSON返回示例：

checkcontrol:{

"EDITENABLE":"true",

    "EDITFIELD":"PATNAME,REMARK",

    "ITEMADD":"true",

    "ITEMCONFIRM":"true",

    "ITEMREFUSE":"true",

    "ITEMCHARGE":"true",

    "ITEMDISCHARGE":"true",

    "VIEWRESULT":"true",

    "VIEWPATINFO":"true"

},

reportinfo:{

    "APPLYNO":8293782,

    "报告信息":"见上述表格说明",

    "RESULTCNT":"10",

    "RESULTABNCNT":"2",

    "STATUSDESC":"初始报告"

}



代码实现：

表结构：LIS_LIST\LIS_RESULT

返回数据：

找到报告信息时，返回报告的相关信息

未找到报告信息时，返回报告的结构，能确定的信息直接使用确定的信息，如：仪器、日期、样本号等

表结构关系：

LIS_LIST.APPLYNO = LIS_RESULT.APPLYNO

#### 判断当前保存的报告与后台存储是否是同一个人

接口说明：保存报告时，判断当前的患者信息与后台存储是否一致，不一致需要提示

请求URL：../reportinput/request/checkissamereport

示例URL：http://192.168.14.253:8080/lis/reportinput/request/checkissamereport?applyno=-1&instid=518&exectime=2017-06-26&sampletype=&techno=78&hospno=00282321&cardno=D83937283&patname=张三&orgapplyno=83494273812392

业务场景：

可能两台电脑同时操作同一个样本号，导致两次保存的结果是不同患者的，需要做判断和提示

接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | applyno | 报告单号 | string | 传入当前报告单号，如果报告单号<=0,表示当前为新报告，通过仪器+检测日期+样本分组+样本号来处理
如果>0，则优先通过applyno获取，但需比对仪器样本号等信息，避免串号 |
| 2 | instid | 仪器id | string | 仪器id，如：518 |
| 3 | exectime | 检测日期 | string | 格式：yyyy-MM-dd；如：2017-06-26 |
| 4 | sampletype | 样本分组 | string | 样本分组，如：G |
| 5 | techno | 样本号 | string | 样本号，如：1001 |
| 6 | hospno | 病员号 | string | 病员号，如：00282301 |
| 7 | cardno | 磁卡号 | string | 磁卡号，如：D2389303 |
| 8 | patname | 姓名 | string | 姓名，如：维客佳 |
| 9 | orgapplyno | 条形码 | string | 申请条码 |



接口出参【ResposeMessage】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage |
| 1 | type | 消息类型 | string | SUCCESS-成功 
ERROR-系统出现异常或错误
FAILT-业务逻辑处理失败 |
| 2 | messageCode | 消息代码 | string | 用于区分消息代码 |
| 3 | message | 消息内容 | string | type=ERROR时则为系统错误消息提示内容
type=FAILT时则为业务提示信息 |
| 4 | link | URL链接 | string | 用于提示用户后可以点击查看详情信息，默认为空 |
| 5 | data | 数据集合 | object | 用于存储错误或逻辑失败的详细信息 |





提示信息规范：



代码实现：

参照存储过程：lsp_isSampleSample

相关表结构：Lis_List

调用时机：患者登记点击保存或保存患者信息时

后台存储为空时则不需要校验

####  记录报告日志

接口说明：保存报告的一系统的操作日志，一般在报告某个操作成功后进行记录，比如保存成功后记录

请求URL：../reportinput/request/writereportlog

示例URL：http://192.168.14.253:8080/lis/reportinput/request/writereportlog?applyno=34343232&lognode=标本入库&logcontent=报告单号(标本入库):7364289条形码:10012885018P ;入库项目:血常规+C反应蛋白

业务场景：

操作动作包含：条码入库、手工登记、报告删除、报告审核、报告发布、报告回收、报告打印等

接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | applyno | 报告单号 | string | 传入的报告单号必须>0，否则不允许操作 |
| 2 | lognode | 节点内容 | string | 如：条码入库 |
| 3 | logcontent | 日志内容 | string | 描述日志关键信息
如：报告单号(标本入库):7364289条形码:10012885018P ;入库项目:血常规+C反应蛋白 |
| 4 | systype | 系统类型 | string | C-常规 W-微生物 X-血库；默认为C |



接口出参【ResposeMessage】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage |
| 1 | type | 消息类型 | string | SUCCESS-成功 
ERROR-系统出现异常或错误
FAILT-业务逻辑处理失败 |
| 2 | messageCode | 消息代码 | string | 用于区分消息代码 |
| 3 | message | 消息内容 | string | type=ERROR时则为系统错误消息提示内容
type=FAILT时则为业务提示信息 |
| 4 | link | URL链接 | string | 用于提示用户后可以点击查看详情信息，默认为空 |
| 5 | data | 数据集合 | object | 用于存储错误或逻辑失败的详细信息 |





代码实现：

表结构：LIS_REPORTLOG

调用时机：条码入库、手工登记、报告删除、报告审核、报告发布、报告回收、报告打印

#### 查询报告日志

接口说明：查询报告详细信息时，获取报告详细操作日志

请求URL：../reportinput/request/getreportlog

示例URL：http://192.168.14.253:8080/lis/reportinput/request/getreportlog?applyno=34343232

业务场景：查看报告操作日志

接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | applyno | 报告单号 | string | 传入的报告单号必须>0，否则不允许操作 |
| 2 | systype | 系统类型 | string | C-常规 W-微生物 X-血库；默认为C |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：loglist  类型：array<object> | 节点名称：loglist  类型：array<object> | 节点名称：loglist  类型：array<object> | 节点名称：loglist  类型：array<object> | 节点名称：loglist  类型：array<object> |
| 1 | APPLYNO | 报告单号 | string | 如：892832 |
| 2 | LOGTIME | 日志时间 | string | 格式：yyyy-MM-dd HH:mm:ss |
| 3 | LOGNODE | 日志节点 | string | 如：条码入库 |
| 4 | LOGCONTENT | 日志内容 | string | 如：日志内容 |
| 5 | LOGUSERID | 用户ID | string | 操作人员ID |
| 6 | LOGUSERNAME | 用户名称 | string | 操作人员姓名 |
| 7 | COMPUTERNAME | 计算机名称 | string | 操作计算机名称 格式：计算机名(ip) |





后台返回示例：



代码实现：

表结构：LIS_REPORTLOG

调用时机：查看修改记录时

参考SQL：

SELECT APPLYNO,LOGTIME,LOGNODE,LOGCONTENT,LOGUSERID,LOGUSERNAME,COMPUTERNAME

FROM LIS_REPORTLOG

####  记录报告修改记录 

接口说明：保存报告后成功后，记录当前报告结果到修改记录中

接口类型：public 供内部服务调用，不提供前端调用

业务场景：

条码入库成功后，保存修改记录到报告修改记录表

患者登记成功后，保存修改记录到报告修改记录表

.....

报告管理、患者登记等任何对报告有修改的部分均应保存到修改记录表

接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | applyno | 报告单号 | string | 传入的报告单号必须>0，否则不允许操作 |
| 2 | lognode | 节点内容 | string | 如：条码入库 |
| 3 | logcontent | 日志内容 | string | 描述日志关键信息
如：报告单号(标本入库):7364289条形码:10012885018P ;入库项目:血常规+C反应蛋白 |
| 4 | systype | 系统类型 | string | C-常规 W-微生物 X-血库；默认为C |



接口出参【ResposeMessage】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage |
| 1 | type | 消息类型 | string | SUCCESS-成功 
ERROR-系统出现异常或错误
FAILT-业务逻辑处理失败 |
| 2 | messageCode | 消息代码 | string | 用于区分消息代码 |
| 3 | message | 消息内容 | string | type=ERROR时则为系统错误消息提示内容
type=FAILT时则为业务提示信息 |
| 4 | link | URL链接 | string | 用于提示用户后可以点击查看详情信息，默认为空 |
| 5 | data | 数据集合 | object | 用于存储错误或逻辑失败的详细信息 |





代码实现：

相关表结构：LIS_LISTMOD、LIS_LIST

调用时机：患者登记 条码入库 报告编辑 报告删除 报告被转移 报告转移 报告被复制 报告复制

审核、打印时修改状态时不需要记录，审核打印操作记录保存在操作记录LIS_REPORTLOG里面

#### 撤销条码入库

接口说明：条码入库后，可能会报告进行撤销入库

请求URL：../reportinput/request/cancelbbrk

示例URL：http://192.168.14.253:8080/lis/reportinput/request/cancelbbrk?applyno=34343232

业务场景：

条码入库失败后，可以撤销入库回到原始状态

接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | applyno | 报告单号 | string | 传入的报告单号必须>0，否则不允许操作 |
| 4 | systype | 系统类型 | string | C-常规 W-微生物 X-血库；默认为C |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage |
| 1 | type | 消息类型 | string | SUCCESS-成功 
ERROR-系统出现异常或错误
FAILT-业务逻辑处理失败 |
| 2 | messageCode | 消息代码 | string | 用于区分消息代码 |
| 3 | message | 消息内容 | string | type=ERROR时则为系统错误消息提示内容
type=FAILT时则显示当前报告的汇总信息，提示内容由ResposeMessage.Data来返回 |
| 4 | link | URL链接 | string | 用于提示用户后可以点击查看详情信息，默认为空 |
| 节点名称：messagelist 类型：array<object> 校验为FAILT时，存在消息队列 | 节点名称：messagelist 类型：array<object> 校验为FAILT时，存在消息队列 | 节点名称：messagelist 类型：array<object> 校验为FAILT时，存在消息队列 | 节点名称：messagelist 类型：array<object> 校验为FAILT时，存在消息队列 | 节点名称：messagelist 类型：array<object> 校验为FAILT时，存在消息队列 |
| 1 | MESSAGE | 消息内容 | string | 业务逻辑判断的消息内容 |
| 2 | MSGTYPE | 消息类型 | string | infomation-消息提示
confirm-需确认？继续\|\|不通过
error-错误，不能继续往下进行
alert-警告信息 |
| 3 | USERVERIFY | 用户密码确认 | boolean | false-不需要用户密码确认
true-需要用户密码确认 |
| 4 | MSGSTATUS | 消息状态 | string | 0-未通过
1-验证通过 |





代码实现：

相关存储：lsp_txm_cancelbbrk（参考-转换为服务）

记录报告日志：见100.B04 lognode=撤销入库

#### 报告删除

接口说明：删除当前报告及结果信息

请求URL：../reportinput/request/deletecurreport

示例URL：http://192.168.14.253:8080/lis/reportinput/request/deletecurreport?applyno=34343232

业务场景：

手工添加的患者，需要做删除报告的动作。

接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | applyno | 报告单号 | string | 传入的报告单号必须>0，否则不允许操作 |
| 4 | systype | 系统类型 | string | C-常规 W-微生物 X-血库；默认为C |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage |
| 1 | type | 消息类型 | string | SUCCESS-成功 
ERROR-系统出现异常或错误
FAILT-业务逻辑处理失败 |
| 2 | messageCode | 消息代码 | string | 用于区分消息代码 |
| 3 | message | 消息内容 | string | type=ERROR时则为系统错误消息提示内容
type=FAILT时则显示当前报告的汇总信息，提示内容由ResposeMessage.Data来返回 |
| 4 | link | URL链接 | string | 用于提示用户后可以点击查看详情信息，默认为空 |
| 节点名称：messagelist 类型：array<object> 校验为FAILT时，存在消息队列 | 节点名称：messagelist 类型：array<object> 校验为FAILT时，存在消息队列 | 节点名称：messagelist 类型：array<object> 校验为FAILT时，存在消息队列 | 节点名称：messagelist 类型：array<object> 校验为FAILT时，存在消息队列 | 节点名称：messagelist 类型：array<object> 校验为FAILT时，存在消息队列 |
| 1 | MESSAGE | 消息内容 | string | 业务逻辑判断的消息内容 |
| 2 | MSGTYPE | 消息类型 | string | infomation-消息提示
confirm-需确认？继续\|\|不通过
error-错误，不能继续往下进行
alert-警告信息 |
| 3 | USERVERIFY | 用户密码确认 | boolean | false-不需要用户密码确认
true-需要用户密码确认 |
| 4 | MSGSTATUS | 消息状态 | string | 0-未通过
1-验证通过 |





代码实现：

相关存储：lsp_deleteCurReport（参考-转换为服务）

历史记录：

报告历史记录 100.B06

结果历史记录 存储：lsp_InsertLisResultMod

报告日志：100.B04 lognode=报告删除

#### 危急值判断

接口说明：判断当前报告是否存在危急值

请求URL：../reportinput/request/checkpanicitems 

示例URL：http://192.168.14.253:8080/lis/reportinput/request/checkpanicitems?applyno=34343232

业务场景：

浏览报告，提示该报告存在危急项目，并可以查看危急值项目

接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | applyno | 报告单号 | string | 传入的报告单号必须>0，否则不允许操作 |
| 2 | operatortype | 操作类型 | string | '0'-判断危急值并生成记录，'1'-查看危急值项目 |
| 4 | systype | 系统类型 | string | C-常规 W-微生物 X-血库；默认为C |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：panicitemlist  类型：array<object> | 节点名称：panicitemlist  类型：array<object> | 节点名称：panicitemlist  类型：array<object> | 节点名称：panicitemlist  类型：array<object> | 节点名称：panicitemlist  类型：array<object> |
| 1 | APPLYNO | 报告单号 | string | 如：892832 |
| 2 | INSTID | 仪器ID | string |  |
| 3 | INSTNAME | 仪器名字 | string |  |
| 4 | TECHNO | 样本号 | string |  |
| 5 | EXECTIME | 检验日期 | string | 格式：yyyy-MM-dd |
| 6 | ITEMCODE | 项目代码 | string |  |
| 7 | ITEMNAME | 项目名称 | string | 如：条码入库 |
| 8 | RESULT | 项目结果 | string | 危急值结果 |
| 9 | PANICLOW | 项目危急参考值下限 | string | 危急值的参考值。14 |
| 10 | PANICHIGH | 项目危急参考值上限 | string | 危急值的参考值。25.0 |
| 11 | PANICCHAR | 项目危急参考字符 | string | 危急值的字符型的参考值 |
| 12 | PANICREFERENCERANGE | 参考范围 | string | 危急值的参考值 如 14.0—25.0 |
| 13 | UNIT | 项目单位 | string | 项目单位 |
| 14 | REDO | 复做标志 | string | 危急值项目是否已经复做0 未复做,1 复做 |
|  |  |  |  |  |
|  |  |  |  |  |
|  |  |  |  |  |



#### 延迟发布报告功能

接口说明：报告的延迟发布

请求URL：../reportinput/request/delayreleasereport

示例URL：http://192.168.14.253:8080/lis/reportinput/request/delayreleasereport

场景描述：

由于设备仪器维护等导致报告不能正常发布，让报告延期发布

接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | applyno | 报告单号 | string | 传入当前的报告单号 |
| 2 | thedelaytime | 延时到指定时间 | Datetime | 延时到指定的时间发布;格式说明：(yyyy-mm-dd hh:mm:ss)
允许为空 |
| 3 | delayrptreason | 延迟原因 | string | 延迟的具体原因 |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- | --- |
| ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage |
| 1 | type | 消息类型 | 消息类型 | string | SUCCESS-成功 
ERROR-系统出现异常或错误
FAILT-业务逻辑处理失败 |
| 2 | messageCode | 消息代码 | 消息代码 | string | 用于区分消息代码 |
| 3 | message | 消息内容 | 消息内容 | string | type=ERROR时则为系统错误消息提示内容
type=FAILT时则失败的具体原因 |
| 4 | link | URL链接 | URL链接 | string | 用于提示用户后可以点击查看详情信息，默认为空 |





相关表结构：Lis_List ，Lis_ListMod

开发思路或代码实现

>将延迟报告的相关信息更新到LIS_LIST表，字段有：

DELAYRPTFLAG,DELAYRPTREASON,DELAYRPTTIME,DELAYRPTBYCODE,DELAYRPTBYNAME,   DELAYRPTDATE,将对应的延迟信息保存到表中对应的字段;

将操作相关信息保存到Lis_ListMod表中，字段有：

DELAYRPTFLAG,DELAYRPTREASON,DELAYRPTTIME,DELAYRPTBYCODE,DELAYRPTBYNAME,D	ELAYRPTDATE字段。

>保存报告修改记录：调用B06

>记录报告操作日志：调用B04

   >日志内容参考：报告单号【applyno】延迟出报告，预计出报告时间【thedelaytime】，延迟原因：	delayrptreason



### 患者登记

#### 获取最大样本号

接口说明：根据仪器及检测日期获取最大可用样本号

请求URL：../reportinput/request/getmaxavailabletechno

示例URL：http://192.168.14.253:8080/lis/reportinput/request/getmaxavailabletechno?instid=518&exectime=2017-06-23

原型参考：



场景描述：

切换仪器时，获取最大可用样本号，并刷新报告数据

点击新病人时，获取最大可用样本号并刷新报告数据

接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | instid | 仪器id | string | 传入当前操作的仪器id，如：518 |
| 2 | exectime | 检测日期 | string | 传入当前选择的检测日期，如：2017-06-23 |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：data   类型：array<object> | 节点名称：data   类型：array<object> | 节点名称：data   类型：array<object> | 节点名称：data   类型：array<object> | 节点名称：data   类型：array<object> |
| 1 | MAXTECHNO | 可用样本号 | string | 最大可用样本号 |
| 2 | INSTID | 仪器ID | string | 同入参 |
| 3 | EXECTIME | 检测日期 | string | 同入参 |



后台返回示例：



JSON返回示例：

[

{

    "MAXTECHNO":"601",

    "INSTID":"518",

    "EXECTIME":"2017-06-23"

}

]

代码实现：

表结构：LIS_LIST\LIS_LISTAPPLYNO

程序实现：

先从LIS_LISTAPPLYNO中获取最大样本号

获取不到时，再从LIS_LIST获取最大样本号

条件为INSTID和EXECTIME



#### 获取仪器属性及控制

接口说明：获取仪器属性及控制属性

请求URL：../reportinput/request/getinstproperty

示例URL：http://192.168.14.253:8080/lis/reportinput/request/getinstproperty?instid=518

原型参考：



场景描述：

切换仪器时，获取切换后的仪器的属性及控制属性

属性包含：默认标本，样本分组等

接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | instid | 仪器id | string | 传入当前操作的仪器id，如：518 |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- | --- |
| 节点名称：data   类型：object | 节点名称：data   类型：object | 节点名称：data   类型：object | 节点名称：data   类型：object | 节点名称：data   类型：object | 节点名称：data   类型：object |
| 控制属性 | 控制属性 | 控制属性 | 控制属性 | 控制属性 | 控制属性 |
| 1 | SAVEBYUPDOWN | SAVEBYUPDOWN | 翻页保存 | string | true-上下翻样本号时自动保存（报告修改了的情况下）
false-上下翻页不自动保存（报告修改了需提示用户是否保存）
仪器属性：上下翻页时自动保存 |
| 2 | SAVEANDUPDOWN | SAVEANDUPDOWN | 保存翻页 | string | true-点击保存，保存完成后自动翻到下一个样本号
false-保存完成后，继续停留在当前报告，报告信息需刷新
仪器属性：保存后自动翻页 |
| 仪器属性 | 仪器属性 | 仪器属性 | 仪器属性 | 仪器属性 | 仪器属性 |
| 1 | INSTID | INSTID | 仪器ID | string | 仪器ID，如：518 |
| 2 | INSTCODE | INSTCODE | 仪器编码 | string | 仪器代码 |
| 3 | INSTNAME | INSTNAME | 仪器名称 | string | 仪器名称 |
| 4 | SAMPLECODE | SAMPLECODE | 默认标本 | string | 标本代码，界面上的标本种类默认为仪器的标本 |
| 5 | SAMPLENAME | SAMPLENAME | 标本名称 | string | 标本名称 |
| 6 | SAMPLETYPE | SAMPLETYPE | 样本分组 | string | 样本分组 |
| 7 | SGBZ | SGBZ | 双工标志 | string | 双工标志 0-非双工 1-双工仪器 |
| 8 | EXAMCODE | EXAMCODE | 分类代码 | string | 分类代码 |
| 9 | EXAMNAME | EXAMNAME | 分类名称 | string | 分类名称 |
| 10 | EXECDEPTCODE | EXECDEPTCODE | 执行科室 | string | 执行科室代码，界面上的执行科室默认为仪器的科室 |
| 11 | EXECDEPTNAME | EXECDEPTNAME | 科室名称 | string | 执行科室代码，界面上的执行科室默认为仪器的科室 |
| 12 | MODDATE | MODDATE | 修改天数 | number | 允许修改天数 |
| 12 | STOPFLAG | STOPFLAG | 停用标志 | string | 0-正常，1-停用 停用的仪器不允许操作 |



后台返回示例：



JSON返回示例：

{

"SAVEBYUPDOWN":"false",

"SAVEANDUPDOWN":"false",

    "INSTID":"518",

    "INSTCODE":"XE-2100",

    "INSTNAME":"XE-2100",

    "SAMPLECODE":"1",

    "SAMPLENAME":"血",

    "SAMPLETYPE":"",

    "SGBZ":"0",

    "EXAMCODE":"15",

    "EXAMNAME":"血常规",

    "EXECDEPTCODE":"9990",

    "EXECDEPTNAME":"临检室",

    "MODDATE":"",

    "STOPFLAG":"0"

}

相关表结构：LIS_INSTRUMENT\LIS_INSTPROPERTY

参考语句

SELECT I.INSTID,I.INSTCODE,I.INSTNAME,I.DEFSAMPLE AS SAMPLECODE,S.SLAVENAME AS 	SAMPLENAME,I.SAMPLETYPE,ISNULL(I.SGBZ,0) AS SGBZ,I.EXAMCODE,E.EXAMNAME,I.DEPTNO AS 	EXECDEPTCODE,D.DEPTNAME AS EXECDEPTNAME,I.MODDATE,ISNULL(I.STOPFLAG,'0') AS 	STOPFLAG

FROM LIS_INSTRUMENT I

INNER JOIN LIS_EXAMCLASS E ON I.EXAMCODE = E.EXAMCODE

LEFT JOIN SYS_DEPT D ON I.DEPTNO = D.DEPTCODE

LEFT JOIN SLAVE S ON I.HOSPITALCODE = S.HOSPITALCODE AND S.CLASSCODE = '样本类型' AND 	S.SLAVENO = I.DEFSAMPLE

WHERE I.INSTID = 518

#### 获取病人医嘱 

接口说明：获取当前病人未确认医嘱和已确认医嘱，医嘱需通过仪器进行过滤

请求URL：../reportinput/request/getpatorderlist

示例URL：http://192.168.14.253:8080/lis/reportinput/request/getpatorderlist?instid=518&applyno=738823&brlx=1&patientid=783373&cureno=223321&patname=张三&yexh=0&times=1

原型参考：



场景描述：

切换报告或样本号时获取患者未执行医嘱或已确认医嘱信息

接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | instid | 仪器id | string | 传入当前操作的仪器id，如：518
主要用于过滤下载医嘱中的项目 |
| 2 | applyno | 报告单号 | string | 传入当前报告单号，如果报告单号<=0,表示当前为新报告；如果>0，则通过applyNo获取患者已确认医嘱信息 |
| 3 | brlx | 病人类型 | string | 传病人类型外码，0-门诊，1-住院，3、4-体检 |
| 4 | patientid | 病人唯一号 | string | 病人唯一号，不能为空，为空表示手工登记患者，不获取医嘱信息 |
| 5 | cureno | 就诊号 | string | 就诊号，不能为空，为空表示手工登记患者，不获取医嘱信息 |
| 6 | patname | 患者姓名 | string | 患者姓名 |
| 7 | yexh | 婴儿序号 | string | 婴儿序号，非婴儿传0 |
| 8 | times | 住院次数 | string | 住院次数，非住院传0 |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- | --- |
| 节点名称：orderlist类型：array<object> | 节点名称：orderlist类型：array<object> | 节点名称：orderlist类型：array<object> | 节点名称：orderlist类型：array<object> | 节点名称：orderlist类型：array<object> | 节点名称：orderlist类型：array<object> |
| 操作属性（确认、拒绝、撤销、收费、退费） | 操作属性（确认、拒绝、撤销、收费、退费） | 操作属性（确认、拒绝、撤销、收费、退费） | 操作属性（确认、拒绝、撤销、收费、退费） | 操作属性（确认、拒绝、撤销、收费、退费） | 操作属性（确认、拒绝、撤销、收费、退费） |
| 1 | OP_ACCEPT | OP_ACCEPT | 确认 | string | 0-不处理
1-确认，对应HIS为yjqrbz=1 |
| 2 | OP_CANCEL | OP_CANCEL | 撤销 | string | 0-不处理
1-撤销
住院撤销后为未确认、未收费
门诊撤销后为未确认、已收费 |
| 3 | OP_CHARGE | OP_CHARGE | 收费 | string | 0-不处理
1-收费 项目为未收费的时候可用 |
| 4 | OP_DISCHARGE | OP_DISCHARGE | 退费 | string | 0-不处理
1-退费，仅在住院+已收费的情况可用 |
| 5 | OP_REJECT | OP_REJECT | 拒绝 | string | 0-不处理
1-拒绝，仅在未确认未收费的情况下可用 |
| 6 | OP_CHECKLIST | OP_CHECKLIST | 操作按钮 | string | 操作栏的集合，如：OP_CHARGE\|OP_REJECT表示显示【收费】和【拒绝】两个操作 |
| 医嘱信息 | 医嘱信息 | 医嘱信息 | 医嘱信息 | 医嘱信息 | 医嘱信息 |
| 1 | APPLYNO | APPLYNO | 报告单号 | string | HIS下载未确认的医嘱默认为-2 |
| 2 | SERIALNO | SERIALNO | 序号 | string | HIS下载未确认的默认为-1
已确认的对应LIS_ACCEPTITEMS.SERIALNO |
| 3 | PATIENTID | PATIENTID | 病人唯一号 | string | 病人唯一号 |
| 4 | CURENO | CURENO | 就诊号 | string | 就诊号 |
| 5 | LOGNO | LOGNO | 医嘱序号 | string | 医嘱序号，对应LIS_ACCEPTITEMS.HISXXH |
| 6 | HISAPPLYNO | HISAPPLYNO | 申请单号 | string | 申请单号，不是开申请单则为0 |
| 7 | GROUPNO | GROUPNO | 处方序号 | string | 处方序号 |
| 8 | HISORDERCODE | HISORDERCODE | 项目代码 | string | HIS项目代码，对应LIS_ACCEPTITEMS.HISORDERCODE |
| 9 | HISORDERNAME | HISORDERNAME | 项目名称 | string | 对应LIS_ACCEPTITEMS.HISORDERNAME |
| 10 | ITEMTYPE | ITEMTYPE | 项目分类 | string | 0-临床 1-收费 |
| 11 | LISORDERCODE | LISORDERCODE | LIS代码 | string | 对应LIS_ACCEPTITEMS.LISORDERCODE |
| 12 | ITEMQTY | ITEMQTY | 项目数量 | string | 项目数量，如：1.5 |
| 13 | PRICE | PRICE | 单价 | string | 单价，如：45.00 |
| 14 | ITEMUNIT | ITEMUNIT | 单位 | string | 如：次 |
| 15 | APPLYTIME | APPLYTIME | 申请时间 | string | 如：2017-06-26 09:34:23 |
| 16 | RECEIVETIME | RECEIVETIME | 接收时间 | string | HIS下载未确认时，为null，否则取LIS_ACCEPTITEMS.ACCEPTTIME |
| 17 | APPLYDEPTCODE | APPLYDEPTCODE | 申请科室 | string | 申请科室代码(LIS内码），如：2353 |
| 18 | APPLYDEPTNAME | APPLYDEPTNAME | 科室名称 | string | 申请科室名称(LIS内码），如：内科 |
| 19 | APPLYDOCCODE | APPLYDOCCODE | 申请医生 | string | 申请医生代码(LIS内码)，如：2839 |
| 20 | APPLYDOCNAME | APPLYDOCNAME | 医生姓名 | string | 申请医生姓名(LIS内码)，如：万一阁 |
| 21 | EXECDEPTCODE | EXECDEPTCODE | 执行科室 | string | 执行科室代码(LIS内码)，如：4300 |
| 22 | EXECDEPTNAME | EXECDEPTNAME | 科室名称 | string | 执行科室名称(LIS内码)，如：检验科 |
| 23 | OPERATORCODE | OPERATORCODE | 操作员代码 | string | 当前操作员工号 |
| 24 | OPERATORNAME | OPERATORNAME | 操作员姓名 | string | 当前操作员姓名 |
| 25 | ADDTYPE | ADDTYPE | 医嘱类型 | string | 0-HIS下载
1-LIS上传 |
| 26 | ADDTYPEDESC | ADDTYPEDESC | 类型描述 | string | 医嘱类型中文描述，如：下载或上传 |
| 27 | STATUS | STATUS | 状态 | string | 0-未确认 1-已确认 2-已拒绝 |
| 28 | STATUSDESC | STATUSDESC | 状态 | string | 状态中文描述，如：未确认、已确认、已拒绝 |
| 29 | CHARGEFLAG | CHARGEFLAG | 收费 | string | 0-未收费 1-已收费 2-退费 |
| 30 | CHARGEFLAGDESC | CHARGEFLAGDESC | 收费描述 | string | 收费中文描述，如：未收费、已收费、已退费 |
| 31 | CLINICDESC | CLINICDESC | 临床诊断 | string | 临床诊断 |
| 32 | SAMPLECODE | SAMPLECODE | 样本类型 | string | 样本类型代码 |
| 33 | SAMPLENAME | SAMPLENAME | 样本名称 | string | 样本类型名称 |
| 34 | GHXH | GHXH | 挂号序号 | string | 挂号序号，针对门诊 |
| 35 | TXM | TXM | 条形码 | string | 走条码流程，返回LIS_ACCEPTITEMS.TXM |
| 36 | ORDERNO | ORDERNO | 预约号 | string | 走条码流程，返回LIS_ACCEPTITEMS.ORDERNO |
| 37 | TXMSTATUS | TXMSTATUS | 条码状态 | string | 0-签收 1-入库 5-绑定 |
| 38 | ACCOUNTTYPE | ACCOUNTTYPE | 记账类型 | string | 0-全院记账 1-本地记账 |
| 39 | ACCOUNTTYPEDESC | ACCOUNTTYPEDESC | 记账描述 | string | 全院记账、本地记账 |
| 40 | REASON | REASON | 原因 | string | 退费原因 |
| 41 | ENTRUST | ENTRUST | 嘱托 | string | 嘱托内容 |
| 42 | MJZBZ | MJZBZ | 加急标志 | string | 0-常规 1-加急 |





医嘱操作栏位说明:



收费项目表格显示列：



后台返回示例：



JSON返回示例：

"orderlist":[

{

    "OP_ACCEPT":"0",

    "OP_CANCEL":"0",

    "OP_CHARGE":"0",

    "OP_DISCHARGE":"0",

    "OP_REJECT":"0",

    "OP_CHECKLIST":"OP_CANCEL|OP_DISCHARGE",

    "APPLYNO":"41162463",

    "SERIALNO":"279031",

    "PATIENTID":"",

    "CURENO":"815163",

    "LOGNO":"15095016",

    "HISAPPLYNO":"16274102",

    "GROUPNO":"46575741",

    "HISORDERCODE":"64202",

    "HISORDERNAME":"急诊尿常规",

    "ITEMTYPE":"0",

    "LISORDERCODE":"064202",

    "ITEMQTY":"1",

    "PRICE":"30",

    "ITEMUNIT":"",

    "APPLYTIME":"2015-08-01 00:33:59",

    "RECEIVETIME":"2015-08-01 00:35:16",

    "APPLYDEPTCODE":"2034",

    "APPLYDEPTNAME":"外科三",

    "APPLYDOCCODE":"11995",

    "APPLYDOCNAME":"李仲南",

    "EXECDEPTCODE":"",

    "EXECDEPTNAME":"",

    "OPERATORCODE":"78181",

    "OPERATORNAME":"叶雪蓉",

    "ADDTYPE":"0",

    "ADDTYPEDESC":"下载",

    "STATUS":"1",

    "STATUSDESC":"已确认",

    "CHARGEFLAG":"1",

    "CHARGEFLAGDESC":"已收费",

    "CLINICDESC":"",

    "SAMPLECODE":"2",

    "SAMPLENAME":"尿",

    "GHXH":"",

    "TXM":"48012882180P",

    "ORDERNO":"41162392",

    "TXMSTATUS":"0",

    "ACCOUNTTYPE":"",

    "ACCOUNTTYPEDESC":"",

    "REASON":"",

    "ENTRUST":"",

    "MJZBZ":"1"

},

{

    "OP_ACCEPT":"0",

    "OP_CANCEL":"0",

    "OP_CHARGE":"0",

    "OP_DISCHARGE":"0",

    "OP_REJECT":"0",

    "OP_CHECKLIST":"OP_CANCEL|OP_DISCHARGE",

    "APPLYNO":"41162453",

    "SERIALNO":"279032",

    "PATIENTID":"",

    "CURENO":"815163",

    "LOGNO":"15095017",

    "HISAPPLYNO":"16274103",

    "GROUPNO":"46575742",

    "HISORDERCODE":"64245",

    "HISORDERNAME":"血气分析",

    "ITEMTYPE":"0",

    "LISORDERCODE":"064245",

    "ITEMQTY":"1",

    "PRICE":"52",

    "ITEMUNIT":"",

    "APPLYTIME":"2015-08-01 00:33:59",

    "RECEIVETIME":"2015-08-01 00:35:17",

    "APPLYDEPTCODE":"2034",

    "APPLYDEPTNAME":"外科三",

    "APPLYDOCCODE":"11995",

    "APPLYDOCNAME":"李仲南",

    "EXECDEPTCODE":"",

    "EXECDEPTNAME":"",

    "OPERATORCODE":"78181",

    "OPERATORNAME":"叶雪蓉",

    "ADDTYPE":"0",

    "ADDTYPEDESC":"下载",

    "STATUS":"1",

    "STATUSDESC":"已确认",

    "CHARGEFLAG":"1",

    "CHARGEFLAGDESC":"已收费",

    "CLINICDESC":"",

    "SAMPLECODE":"1000",

    "SAMPLENAME":"动脉血",

    "GHXH":"",

    "TXM":"08012882181P",

    "ORDERNO":"41162393",

    "TXMSTATUS":"1",

    "ACCOUNTTYPE":"",

    "ACCOUNTTYPEDESC":"",

    "REASON":"",

    "ENTRUST":"",

    "MJZBZ":"1"

}

]

相关表结构：LIS_ACCEPTITEM\LIS_INSTORDER

程序逻辑：

获取当前仪器能做的检测项目，LIS_INSTORDER

从HIS下载医嘱，获取天数取参数控制，然后通过LIS_INSTORDER来过滤，参考方法：100.R04 获取仪器对应的收费项目

applyNo>0时，从本地获取收费项目拼凑起来返回前台

注意显示顺序，尽可能不要操作的时候变化乱掉

参考语句

SELECT top 2

'0' AS OP_ACCEPT,

'0' AS OP_CANCEL,

'0' AS OP_CHARGE,

'0' AS OP_DISCHARGE,

'0' AS OP_REJECT,

'OP_CANCEL|OP_DISCHARGE' AS OP_CHECKLIST,

APPLYNO,

SERIALNO,

PATIENTID,

CURENO,

HISXXH AS LOGNO,

HISAPPLYNO,

GROUPNO,

HISORDERCODE,

HISORDERNAME,

ITEMTYPE,

LISORDERCODE,

XMSL AS ITEMQTY,

PRICE,

ITEMUNIT,

APPLYTIME,

ACCEPTTIME AS RECEIVETIME,

APPLYDEPTCODE,

APPLYDEPTNAME,

APPLYDOCCODE,

APPLYDOCNAME,

EXECDEPTCODE,

EXECDEPTNAME,

OPERATORCODE,

OPERATORNAME,

ADDTYPE,

CASE ADDTYPE WHEN '0' THEN '下载' ELSE '上传' END AS ADDTYPEDESC,

STATUS,

CASE STATUS WHEN '0' THEN '未确认' WHEN '1' THEN '已确认' ELSE '已拒绝' END STATUSDESC,

CHARGEFLAG,

CASE CHARGEFLAG WHEN '0' THEN '未收费'WHEN '1' THEN '已收费' ELSE '已退费' END CHARGEFLAGDESC,

CLINICDESC,

SPECIMEN AS SAMPLECODE,

SPECIMENDESC AS SAMPLENAME,

GHXH,

TXM,

ORDERNO,

TXMSTATUS,

ACCOUNTTYPE,

CASE ACCOUNTTYPE WHEN '0' THEN '全院记账'WHEN '1' THEN '本地记账' END  ACCOUNTTYPEDESC,

'' AS REASON,

ENTRUST,

MJZBZ

FROM LIS_ACCEPTITEMS A

#### 获取仪器对应的收费项目

接口说明：获取仪器可检测的收费项目

请求URL：../reportinput/request/getinstexamorder

示例URL：http://192.168.14.253:8080/lis/reportinput/request/getinstexamorder?instid=518&itemcodelist=[]

原型参考：



场景描述：

下载医嘱项目时，需要根据仪器的收费项目进行过滤

上传医嘱的时候，需要获取最近上传的医嘱信息

接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | instid | 仪器id | string | 传入当前操作的仪器id，如：518 |
| 项目集合：itemcodelist 类型 Array<String> | 项目集合：itemcodelist 类型 Array<String> | 项目集合：itemcodelist 类型 Array<String> | 项目集合：itemcodelist 类型 Array<String> | 项目集合：itemcodelist 类型 Array<String> |
| 1 | lisordercode | LIS项目代码 | string | 项目代码 |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- | --- |
| 节点名称：orderlist   类型：array<object> | 节点名称：orderlist   类型：array<object> | 节点名称：orderlist   类型：array<object> | 节点名称：orderlist   类型：array<object> | 节点名称：orderlist   类型：array<object> | 节点名称：orderlist   类型：array<object> |
| 1 | LISORDERCODE | LISORDERCODE | LIS代码 | string | LIS项目代码，如：120001 |
| 2 | HISORDERCODE | HISORDERCODE | HIS代码 | string | HIS项目代码，如：20001 |
| 3 | HISORDERNAME | HISORDERNAME | HIS名称 | string | HIS项目名称，如：全血 |
| 4 | ITEMTYPE | ITEMTYPE | 项目类型 | string | 0-临床 1-收费 2-药品 |
| 5 | MEMCODE1 | MEMCODE1 | 输入码一 | string | 输入码一 |
| 6 | MEMCODE2 | MEMCODE2 | 输入码二 | string | 输入码二 |
| 7 | SAMPLECODE | SAMPLECODE | 标本代码 | string | 标本代码 |
| 8 | SAMPLENAME | SAMPLENAME | 标本名称 | string | 标本名称 |
| 9 | REMARK | REMARK | 备注 | string | 备注 |
| 10 | PXXH | PXXH | 排序序号 | string | 排序序号 |
| 11 | QSYBH | QSYBH | 起始样本 | string | 起始样本号 |
| 12 | INSTID | INSTID | 仪器ID | string | 仪器ID |
| 最近上传的医嘱项目 节点名称：recentorderlist   类型：array<object>--待设计 | 最近上传的医嘱项目 节点名称：recentorderlist   类型：array<object>--待设计 | 最近上传的医嘱项目 节点名称：recentorderlist   类型：array<object>--待设计 | 最近上传的医嘱项目 节点名称：recentorderlist   类型：array<object>--待设计 | 最近上传的医嘱项目 节点名称：recentorderlist   类型：array<object>--待设计 | 最近上传的医嘱项目 节点名称：recentorderlist   类型：array<object>--待设计 |
| 1 | LISORDERCODE | LISORDERCODE | LIS代码 | string | LIS项目代码，如：120001 |
| 2 | HISORDERCODE | HISORDERCODE | HIS代码 | string | HIS项目代码，如：20001 |
| 3 | HISORDERNAME | HISORDERNAME | HIS名称 | string | HIS项目名称，如：全血 |
| 4 | MEMCODE1 | MEMCODE1 | 输入码一 | string | 输入码一 |
| 5 | MEMCODE2 | MEMCODE2 | 输入码二 | string | 输入码二 |
| 6 | ITEMTYPE | ITEMTYPE | 项目类型 | string | 0-临床 1-收费 2-药品 |
| 7 | LASTTIME | LASTTIME | 最近使用时间 | string | 格式：yyyy-MM-dd HH:mm:ss |
| 8 | INSTID | INSTID | 仪器ID | string | 仪器ID |



后台返回示例：



JSON返回示例：

"orderlist":[

{

    "INSTID":"2",

    "LISORDERCODE":"033904",

    "ITEMTYPE":"1",

    "HISORDERCODE":"33904",

"HISORDERNAME":"各种标本霉菌培养及鉴定（进口）",

"MEMCODE1":"",

"MEMCODE2":"",

"SAMPLECODE":"",

"SAMPLENAME":"",

    "REMARK":"",

    "PXXH":"",

    "QSYBH":""

},

{

    "INSTID":"2",

    "LISORDERCODE":"064210",

    "ITEMTYPE":"0",

    "HISORDERCODE":"64210",

    "HISORDERNAME":"血培养鉴定（仪器、需氧）",

"MEMCODE1":"",

"MEMCODE2":"",

"SAMPLECODE":"",

"SAMPLENAME":"",

    "REMARK":"",

    "PXXH":"",

    "QSYBH":""

}

]

相关表结构：LIS_INSTORDER

参考语句

SELECT * from LIS_INSTORDER

####  保存报告及收费项目

接口说明：保存报告的病人信息及收费项目操作

请求URL：../reportinput/request/savereportdata

示例URL：http://192.168.14.253:8080/lis/reportinput/request/savereportdata?report={}&orderlist={}

原型参考：



场景描述：

上下翻页自动保存

点击【保存】按钮实现报告及收费项目的保存

接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1、报告主信息 见100.B02部分结构 | 1、报告主信息 见100.B02部分结构 | 1、报告主信息 见100.B02部分结构 | 1、报告主信息 见100.B02部分结构 | 1、报告主信息 见100.B02部分结构 |
| 2、报告检测项目信息 见 100.R03 | 2、报告检测项目信息 见 100.R03 | 2、报告检测项目信息 见 100.R03 | 2、报告检测项目信息 见 100.R03 | 2、报告检测项目信息 见 100.R03 |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1、节点名称：reportifo 报告信息 见100.B02 | 1、节点名称：reportifo 报告信息 见100.B02 | 1、节点名称：reportifo 报告信息 见100.B02 | 1、节点名称：reportifo 报告信息 见100.B02 | 1、节点名称：reportifo 报告信息 见100.B02 |
| 2、节点名称：checkcontrol 控制信息 见100.B02 | 2、节点名称：checkcontrol 控制信息 见100.B02 | 2、节点名称：checkcontrol 控制信息 见100.B02 | 2、节点名称：checkcontrol 控制信息 见100.B02 | 2、节点名称：checkcontrol 控制信息 见100.B02 |
| 3、节点名称：orderlist  医嘱信息 见100.R03 | 3、节点名称：orderlist  医嘱信息 见100.R03 | 3、节点名称：orderlist  医嘱信息 见100.R03 | 3、节点名称：orderlist  医嘱信息 见100.R03 | 3、节点名称：orderlist  医嘱信息 见100.R03 |



后台返回示例：

见方法100.B02 和 100.R03

JSON返回示例：



"reportinfo":

{

    ............

},

"checkcontrol":

{

    ............

},

"orderlist":[

{

    ............

},

{

    ............

}

]



Json格式见各方法出参：100.B02 及 100.R03

相关表结构：LIS_LIST\LIS_ACCEPTITEMS

程序实现：

调用100.B03判断当前保存的报告是否与后台相同

调用接口100.B04记录报告日志

调用接口100.B06记录报告修改记录

#### 上传医嘱项目

接口说明：手工上传医嘱项目到HIS，再从HIS下载项目信息

请求URL：../reportinput/request/addpatorder

代码文件：winning.lis.reportinput.service.PatientRegisterService

示例URL：http://192.168.14.253:8080/lis/reportinput/request/addpatorder?instid=518&applyno=738823&brlx=1&patientid=783373&cureno=223321&patname=张三&yexh=0&times=1&hisordercode=20001&itemtype=1&xmsl=1&orderlist=[]

原型参考：



场景描述：

检验科进行补费操作

接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | instid | 仪器id | string | 传入当前操作的仪器id，如：518
主要用于过滤下载医嘱中的项目 |
| 2 | applyno | 报告单号 | string | 传入当前报告单号，如果报告单号<=0,表示当前为新报告；如果>0，则通过applyNo获取患者已确认医嘱信息 |
| 3 | brlx | 病人类型 | string | 传病人类型外码，0-门诊，1-住院，3、4-体检 |
| 4 | patientid | 病人唯一号 | string | 病人唯一号，不能为空，为空表示手工登记患者，不获取医嘱信息 |
| 5 | cureno | 就诊号 | string | 就诊号，不能为空，为空表示手工登记患者，不获取医嘱信息 |
| 6 | patname | 患者姓名 | string | 患者姓名 |
| 7 | yexh | 婴儿序号 | string | 婴儿序号，非婴儿传0 |
| 8 | times | 住院次数 | string | 住院次数，非住院传0 |
| 9 | hisordercode | 项目代码 | string | 项目代码 对应HISORDERCODE |
| 10 | itemtype | 项目类型 | string | 项目类型 对应ITEMTYPE |
| 11 | xmsl | 项目数量 | number | 默认为1 |
| 12 | orderlist | 医嘱列表 | array<object> | 对应当前操作的医嘱列表信息
新添加的医嘱下载并添加到列表中，操作的状态不变化 |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- | --- |
| 节点名称：orderlist类型：array<object> | 节点名称：orderlist类型：array<object> | 节点名称：orderlist类型：array<object> | 节点名称：orderlist类型：array<object> | 节点名称：orderlist类型：array<object> | 节点名称：orderlist类型：array<object> |
| 操作属性（确认、拒绝、撤销、收费、退费） | 操作属性（确认、拒绝、撤销、收费、退费） | 操作属性（确认、拒绝、撤销、收费、退费） | 操作属性（确认、拒绝、撤销、收费、退费） | 操作属性（确认、拒绝、撤销、收费、退费） | 操作属性（确认、拒绝、撤销、收费、退费） |
| 1 | OP_ACCEPT | OP_ACCEPT | 确认 | string | 0-不处理
1-确认，对应HIS为yjqrbz=1 |
| 2 | OP_CANCEL | OP_CANCEL | 撤销 | string | 0-不处理
1-撤销
住院撤销后为未确认、未收费
门诊撤销后为未确认、已收费 |
| 3 | OP_CHARGE | OP_CHARGE | 收费 | string | 0-不处理
1-收费 项目为未收费的时候可用 |
| 4 | OP_DISCHARGE | OP_DISCHARGE | 退费 | string | 0-不处理
1-退费，仅在住院+已收费的情况可用 |
| 5 | OP_REJECT | OP_REJECT | 拒绝 | string | 0-不处理
1-拒绝，仅在未确认未收费的情况下可用 |
| 6 | OP_CHECKLIST | OP_CHECKLIST | 操作按钮 | string | 操作栏的集合，如：OP_CHARGE\|OP_REJECT表示显示【收费】和【拒绝】两个操作 |
| 医嘱信息 | 医嘱信息 | 医嘱信息 | 医嘱信息 | 医嘱信息 | 医嘱信息 |
| 1 | APPLYNO | APPLYNO | 报告单号 | string | HIS下载未确认的医嘱默认为-2 |
| 2 | SERIALNO | SERIALNO | 序号 | string | HIS下载未确认的默认为-1
已确认的对应LIS_ACCEPTITEMS.SERIALNO |
| 3 | PATIENTID | PATIENTID | 病人唯一号 | string | 病人唯一号 |
| 4 | CURENO | CURENO | 就诊号 | string | 就诊号 |
| 5 | LOGNO | LOGNO | 医嘱序号 | string | 医嘱序号，对应LIS_ACCEPTITEMS.HISXXH |
| 6 | HISAPPLYNO | HISAPPLYNO | 申请单号 | string | 申请单号，不是开申请单则为0 |
| 7 | GROUPNO | GROUPNO | 处方序号 | string | 处方序号 |
| 8 | HISORDERCODE | HISORDERCODE | 项目代码 | string | HIS项目代码，对应LIS_ACCEPTITEMS.HISORDERCODE |
| 9 | HISORDERNAME | HISORDERNAME | 项目名称 | string | 对应LIS_ACCEPTITEMS.HISORDERNAME |
| 10 | ITEMTYPE | ITEMTYPE | 项目分类 | string | 0-临床 1-收费 |
| 11 | LISORDERCODE | LISORDERCODE | LIS代码 | string | 对应LIS_ACCEPTITEMS.LISORDERCODE |
| 12 | ITEMQTY | ITEMQTY | 项目数量 | string | 项目数量，如：1.5 |
| 13 | PRICE | PRICE | 单价 | string | 单价，如：45.00 |
| 14 | ITEMUNIT | ITEMUNIT | 单位 | string | 如：次 |
| 15 | APPLYTIME | APPLYTIME | 申请时间 | string | 如：2017-06-26 09:34:23 |
| 16 | RECEIVETIME | RECEIVETIME | 接收时间 | string | HIS下载未确认时，为null，否则取LIS_ACCEPTITEMS.ACCEPTTIME |
| 17 | APPLYDEPTCODE | APPLYDEPTCODE | 申请科室 | string | 申请科室代码(LIS内码），如：2353 |
| 18 | APPLYDEPTNAME | APPLYDEPTNAME | 科室名称 | string | 申请科室名称(LIS内码），如：内科 |
| 19 | APPLYDOCCODE | APPLYDOCCODE | 申请医生 | string | 申请医生代码(LIS内码)，如：2839 |
| 20 | APPLYDOCNAME | APPLYDOCNAME | 医生姓名 | string | 申请医生姓名(LIS内码)，如：万一阁 |
| 21 | EXECDEPTCODE | EXECDEPTCODE | 执行科室 | string | 执行科室代码(LIS内码)，如：4300 |
| 22 | EXECDEPTNAME | EXECDEPTNAME | 科室名称 | string | 执行科室名称(LIS内码)，如：检验科 |
| 23 | OPERATORCODE | OPERATORCODE | 操作员代码 | string | 当前操作员工号 |
| 24 | OPERATORNAME | OPERATORNAME | 操作员姓名 | string | 当前操作员姓名 |
| 25 | ADDTYPE | ADDTYPE | 医嘱类型 | string | 0-HIS下载
1-LIS上传 |
| 26 | ADDTYPEDESC | ADDTYPEDESC | 类型描述 | string | 医嘱类型中文描述，如：下载或上传 |
| 27 | STATUS | STATUS | 状态 | string | 0-未确认 1-已确认 2-已拒绝 |
| 28 | STATUSDESC | STATUSDESC | 状态 | string | 状态中文描述，如：未确认、已确认、已拒绝 |
| 29 | CHARGEFLAG | CHARGEFLAG | 收费 | string | 0-未收费 1-已收费 2-退费 |
| 30 | CHARGEFLAGDESC | CHARGEFLAGDESC | 收费描述 | string | 收费中文描述，如：未收费、已收费、已退费 |
| 31 | CLINICDESC | CLINICDESC | 临床诊断 | string | 临床诊断 |
| 32 | SAMPLECODE | SAMPLECODE | 样本类型 | string | 样本类型代码 |
| 33 | SAMPLENAME | SAMPLENAME | 样本名称 | string | 样本类型名称 |
| 34 | GHXH | GHXH | 挂号序号 | string | 挂号序号，针对门诊 |
| 35 | TXM | TXM | 条形码 | string | 走条码流程，返回LIS_ACCEPTITEMS.TXM |
| 36 | ORDERNO | ORDERNO | 预约号 | string | 走条码流程，返回LIS_ACCEPTITEMS.ORDERNO |
| 37 | TXMSTATUS | TXMSTATUS | 条码状态 | string | 0-签收 1-入库 5-绑定 |
| 38 | ACCOUNTTYPE | ACCOUNTTYPE | 记账类型 | string | 0-全院记账 1-本地记账 |
| 39 | ACCOUNTTYPEDESC | ACCOUNTTYPEDESC | 记账描述 | string | 全院记账、本地记账 |
| 40 | REASON | REASON | 原因 | string | 退费原因 |
| 41 | ENTRUST | ENTRUST | 嘱托 | string | 嘱托内容 |
| 42 | MJZBZ | MJZBZ | 加急标志 | string | 0-常规 1-加急 |





医嘱操作栏位说明:



收费项目表格显示列：



后台返回示例：



JSON返回示例：

"orderlist":[

{

    "OP_ACCEPT":"0",

    "OP_CANCEL":"0",

    "OP_CHARGE":"0",

    "OP_DISCHARGE":"0",

    "OP_REJECT":"0",

    "OP_CHECKLIST":"OP_CANCEL|OP_DISCHARGE",

    "APPLYNO":"41162463",

    "SERIALNO":"279031",

    "PATIENTID":"",

    "CURENO":"815163",

    "LOGNO":"15095016",

    "HISAPPLYNO":"16274102",

    "GROUPNO":"46575741",

    "HISORDERCODE":"64202",

    "HISORDERNAME":"急诊尿常规",

    "ITEMTYPE":"0",

    "LISORDERCODE":"064202",

    "ITEMQTY":"1",

    "PRICE":"30",

    "ITEMUNIT":"",

    "APPLYTIME":"2015-08-01 00:33:59",

    "RECEIVETIME":"2015-08-01 00:35:16",

    "APPLYDEPTCODE":"2034",

    "APPLYDEPTNAME":"外科三",

    "APPLYDOCCODE":"11995",

    "APPLYDOCNAME":"李仲南",

    "EXECDEPTCODE":"",

    "EXECDEPTNAME":"",

    "OPERATORCODE":"78181",

    "OPERATORNAME":"叶雪蓉",

    "ADDTYPE":"0",

    "ADDTYPEDESC":"下载",

    "STATUS":"1",

    "STATUSDESC":"已确认",

    "CHARGEFLAG":"1",

    "CHARGEFLAGDESC":"已收费",

    "CLINICDESC":"",

    "SAMPLECODE":"2",

    "SAMPLENAME":"尿",

    "GHXH":"",

    "TXM":"48012882180P",

    "ORDERNO":"41162392",

    "TXMSTATUS":"0",

    "ACCOUNTTYPE":"",

    "ACCOUNTTYPEDESC":"",

    "REASON":"",

    "ENTRUST":"",

    "MJZBZ":"1"

},

{

    "OP_ACCEPT":"0",

    "OP_CANCEL":"0",

    "OP_CHARGE":"0",

    "OP_DISCHARGE":"0",

    "OP_REJECT":"0",

    "OP_CHECKLIST":"OP_CANCEL|OP_DISCHARGE",

    "APPLYNO":"41162453",

    "SERIALNO":"279032",

    "PATIENTID":"",

    "CURENO":"815163",

    "LOGNO":"15095017",

    "HISAPPLYNO":"16274103",

    "GROUPNO":"46575742",

    "HISORDERCODE":"64245",

    "HISORDERNAME":"血气分析",

    "ITEMTYPE":"0",

    "LISORDERCODE":"064245",

    "ITEMQTY":"1",

    "PRICE":"52",

    "ITEMUNIT":"",

    "APPLYTIME":"2015-08-01 00:33:59",

    "RECEIVETIME":"2015-08-01 00:35:17",

    "APPLYDEPTCODE":"2034",

    "APPLYDEPTNAME":"外科三",

    "APPLYDOCCODE":"11995",

    "APPLYDOCNAME":"李仲南",

    "EXECDEPTCODE":"",

    "EXECDEPTNAME":"",

    "OPERATORCODE":"78181",

    "OPERATORNAME":"叶雪蓉",

    "ADDTYPE":"0",

    "ADDTYPEDESC":"下载",

    "STATUS":"1",

    "STATUSDESC":"已确认",

    "CHARGEFLAG":"1",

    "CHARGEFLAGDESC":"已收费",

    "CLINICDESC":"",

    "SAMPLECODE":"1000",

    "SAMPLENAME":"动脉血",

    "GHXH":"",

    "TXM":"08012882181P",

    "ORDERNO":"41162393",

    "TXMSTATUS":"1",

    "ACCOUNTTYPE":"",

    "ACCOUNTTYPEDESC":"",

    "REASON":"",

    "ENTRUST":"",

    "MJZBZ":"1"

}

]

相关表结构：LIS_ACCEPTITEMS\LIS_INSTORDER

程序逻辑：

调用HIS接口ADDORDER上传医嘱信息到HIS

从HIS获取上传的医嘱信息并在最后添加到当前列表orderlist中返回

注意显示顺序，尽可能不要操作的时候变化乱掉

参考语句

SELECT top 2

'0' AS OP_ACCEPT,

'0' AS OP_CANCEL,

'0' AS OP_CHARGE,

'0' AS OP_DISCHARGE,

'0' AS OP_REJECT,

'OP_CANCEL|OP_DISCHARGE' AS OP_CHECKLIST,

APPLYNO,

SERIALNO,

PATIENTID,

CURENO,

HISXXH AS LOGNO,

HISAPPLYNO,

GROUPNO,

HISORDERCODE,

HISORDERNAME,

ITEMTYPE,

LISORDERCODE,

XMSL AS ITEMQTY,

PRICE,

ITEMUNIT,

APPLYTIME,

ACCEPTTIME AS RECEIVETIME,

APPLYDEPTCODE,

APPLYDEPTNAME,

APPLYDOCCODE,

APPLYDOCNAME,

EXECDEPTCODE,

EXECDEPTNAME,

OPERATORCODE,

OPERATORNAME,

ADDTYPE,

CASE ADDTYPE WHEN '0' THEN '下载' ELSE '上传' END AS ADDTYPEDESC,

STATUS,

CASE STATUS WHEN '0' THEN '未确认' WHEN '1' THEN '已确认' ELSE '已拒绝' END STATUSDESC,

CHARGEFLAG,

CASE CHARGEFLAG WHEN '0' THEN '未收费'WHEN '1' THEN '已收费' ELSE '已退费' END CHARGEFLAGDESC,

CLINICDESC,

SPECIMEN AS SAMPLECODE,

SPECIMENDESC AS SAMPLENAME,

GHXH,

TXM,

ORDERNO,

TXMSTATUS,

ACCOUNTTYPE,

CASE ACCOUNTTYPE WHEN '0' THEN '全院记账'WHEN '1' THEN '本地记账' END  ACCOUNTTYPEDESC,

'' AS REASON,

ENTRUST,

MJZBZ

FROM LIS_ACCEPTITEMS A

### 报告审核

#### 判断报告必须通过的审核条件 65802

接口说明：审核报告时，需判断当前报告的审核条件是否满足，校验判断

请求URL：../reportinput/request/verifymustcondition

代码文件：winning.lis.reportinput.service.VerifyService

示例URL：http://192.168.14.253:8080/lis/reportinput/request/verifymustcondition?applyno=5183343

场景描述：

审核报告时，需判断当前报告是否完全符合审核的条件

接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | applyno | 报告单号 | string | 传入当前操作的仪器id，如：518 |



接口出参【ResposeMessage】：

| NO. | 变量名 | 含义 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- | --- |
| 节点名称：messagelist   类型：array<object>  Type=FAILT的时候才返回 | 节点名称：messagelist   类型：array<object>  Type=FAILT的时候才返回 | 节点名称：messagelist   类型：array<object>  Type=FAILT的时候才返回 | 节点名称：messagelist   类型：array<object>  Type=FAILT的时候才返回 | 节点名称：messagelist   类型：array<object>  Type=FAILT的时候才返回 | 节点名称：messagelist   类型：array<object>  Type=FAILT的时候才返回 |
| 1 | MESSAGE | MESSAGE | 提示信息 | string | 报告没有检测项目 |
| 节点名称：ResposeMessage.message 消息汇总信息 | 节点名称：ResposeMessage.message 消息汇总信息 | 节点名称：ResposeMessage.message 消息汇总信息 | 节点名称：ResposeMessage.message 消息汇总信息 | 节点名称：ResposeMessage.message 消息汇总信息 | 节点名称：ResposeMessage.message 消息汇总信息 |



JSON返回示例：

"messagelist":[

{

    "MESSAGE":"报告没有检测项目"

},

{

     "MESSAGE":"报告没有检测项目"

}

]

相关表结构：LIS_LIST\LIS_RESULT

参考语句
		lsp_MustVerify

代码实现：

如果是条码病人，则判断当前报告与条码病人信息是否一致（LIS_LIST.ORGAPPLYNO）

仪器属性：报告无检测项目允许审核、标本种类为空时不允许审核报告、标本说明为空时不允许审核报告

姓名不允许为空

校验收费项目与当前患者是否是同一个人（存在非条码流程）

时间判断：申请时间(APPLYTIME) < 采样时间（SAMPLETIME） < 送检时间(RECEIVETIME) < 报告时间(REPORTTIME)

#### 根据审核条件进行判断报告是否通过

接口说明：获取仪器可检测的收费项目

请求URL：../reportinput/request/getinstexamorder

示例URL：http://192.168.14.253:8080/lis/reportinput/request/getinstexamorder?instid=518

原型参考：



场景描述：

下载医嘱项目时，需要根据仪器的收费项目进行过滤

上传医嘱的时候，需要获取最近上传的医嘱信息

接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | instid | 仪器id | string | 传入当前操作的仪器id，如：518 |
| 项目集合：itemcodelist 类型 Array<String> | 项目集合：itemcodelist 类型 Array<String> | 项目集合：itemcodelist 类型 Array<String> | 项目集合：itemcodelist 类型 Array<String> | 项目集合：itemcodelist 类型 Array<String> |
| 1 | lisordercode | LIS项目代码 | string | 项目代码 |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- | --- |
| 节点名称：orderlist   类型：array<object> | 节点名称：orderlist   类型：array<object> | 节点名称：orderlist   类型：array<object> | 节点名称：orderlist   类型：array<object> | 节点名称：orderlist   类型：array<object> | 节点名称：orderlist   类型：array<object> |
| 1 | LISORDERCODE | LISORDERCODE | LIS代码 | string | LIS项目代码，如：120001 |
| 2 | HISORDERCODE | HISORDERCODE | HIS代码 | string | HIS项目代码，如：20001 |
| 3 | HISORDERNAME | HISORDERNAME | HIS名称 | string | HIS项目名称，如：全血 |
| 4 | ITEMTYPE | ITEMTYPE | 项目类型 | string | 0-临床 1-收费 2-药品 |
| 5 | MEMCODE1 | MEMCODE1 | 输入码一 | string | 输入码一 |
| 6 | MEMCODE2 | MEMCODE2 | 输入码二 | string | 输入码二 |
| 7 | SAMPLECODE | SAMPLECODE | 标本代码 | string | 标本代码 |
| 8 | SAMPLENAME | SAMPLENAME | 标本名称 | string | 标本名称 |
| 9 | REMARK | REMARK | 备注 | string | 备注 |
| 10 | PXXH | PXXH | 排序序号 | string | 排序序号 |
| 11 | QSYBH | QSYBH | 起始样本 | string | 起始样本号 |
| 12 | INSTID | INSTID | 仪器ID | string | 仪器ID |
| 最近上传的医嘱项目 节点名称：recentorderlist   类型：array<object>--待设计 | 最近上传的医嘱项目 节点名称：recentorderlist   类型：array<object>--待设计 | 最近上传的医嘱项目 节点名称：recentorderlist   类型：array<object>--待设计 | 最近上传的医嘱项目 节点名称：recentorderlist   类型：array<object>--待设计 | 最近上传的医嘱项目 节点名称：recentorderlist   类型：array<object>--待设计 | 最近上传的医嘱项目 节点名称：recentorderlist   类型：array<object>--待设计 |
| 1 | LISORDERCODE | LISORDERCODE | LIS代码 | string | LIS项目代码，如：120001 |
| 2 | HISORDERCODE | HISORDERCODE | HIS代码 | string | HIS项目代码，如：20001 |
| 3 | HISORDERNAME | HISORDERNAME | HIS名称 | string | HIS项目名称，如：全血 |
| 4 | MEMCODE1 | MEMCODE1 | 输入码一 | string | 输入码一 |
| 5 | MEMCODE2 | MEMCODE2 | 输入码二 | string | 输入码二 |
| 6 | ITEMTYPE | ITEMTYPE | 项目类型 | string | 0-临床 1-收费 2-药品 |
| 7 | LASTTIME | LASTTIME | 最近使用时间 | string | 格式：yyyy-MM-dd HH:mm:ss |
| 8 | INSTID | INSTID | 仪器ID | string | 仪器ID |



后台返回示例：



JSON返回示例：

"orderlist":[

{

    "INSTID":"2",

    "LISORDERCODE":"033904",

    "ITEMTYPE":"1",

    "HISORDERCODE":"33904",

"HISORDERNAME":"各种标本霉菌培养及鉴定（进口）",

"MEMCODE1":"",

"MEMCODE2":"",

"SAMPLECODE":"",

"SAMPLENAME":"",

    "REMARK":"",

    "PXXH":"",

    "QSYBH":""

},

{

    "INSTID":"2",

    "LISORDERCODE":"064210",

    "ITEMTYPE":"0",

    "HISORDERCODE":"64210",

    "HISORDERNAME":"血培养鉴定（仪器、需氧）",

"MEMCODE1":"",

"MEMCODE2":"",

"SAMPLECODE":"",

"SAMPLENAME":"",

    "REMARK":"",

    "PXXH":"",

    "QSYBH":""

}

]

相关表结构：LIS_INSTORDER

参考语句

SELECT * from LIS_INSTORDER















### 报告删除

#### 模块接口视图

| NO. | 接口编号 | 接口名称 | 说明 |
| --- | --- | --- | --- |
| 1 | A01 | 初始化数据
../reportdelete/request/getinitdata | 报告删除模块初始化数据 |
| 2 | B01 | 获取报告列表
./reportdelete/request/getreportlist | 根据用户选择的参数，获取lis_list表中报告信息 |
| 3 | B02 | 报告（结果）删除
../reportdelete/request/reportdel | 删除报告数据和报告结果数据 |
| 4 | B03 | 获取指定仪器对应的项目信息
../reportdelete/request/getitemlist | 获取指定仪器对应的项目信息 |
| 5 | B04 | 报告指定项目删除
../reportdelete/request/designitemdel | 删除报告中指定项目结果数据 |



#### 初始化数据

接口说明：报告删除模块初始化数据

请求URL：../reportdelete/request/getinitdata

代码文件：winning.lis.reportdelete.service.ReportDeleteService

示例URL：http://192.168.11.211:15011/lis/reportdelete/request/getinitdata

原型参考：



接口入参：

接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- | --- |
| 节点名称：controlsparams类型：object | 节点名称：controlsparams类型：object | 节点名称：controlsparams类型：object | 节点名称：controlsparams类型：object | 节点名称：controlsparams类型：object | 节点名称：controlsparams类型：object |
| 1 | OP_DELREPORT | OP_DELREPORT | 报告删除按钮 | boolean | 用于配置该用户是否拥有“报告删除”的权限
true-允许删除
false-不允许删除 |
| 2 | OP_DELRESULT | OP_DELRESULT | 报告结果删除按钮 | boolean | 用于配置该用户是否拥有“报告结果删除”的权限
true-允许删除
false-不允许删除 |
| 3 | OP_DELMARKITEM | OP_DELMARKITEM | 特定报告项目删除按钮 | boolean | 用于配置该用户是否拥有“报告特定项目删除”的权限
true-允许删除
false-不允许删除 |
| 节点名称：初始化仪器集合instlist  类型：array<object> | 节点名称：初始化仪器集合instlist  类型：array<object> | 节点名称：初始化仪器集合instlist  类型：array<object> | 节点名称：初始化仪器集合instlist  类型：array<object> | 节点名称：初始化仪器集合instlist  类型：array<object> | 节点名称：初始化仪器集合instlist  类型：array<object> |
| 1 | 1 | CODE | 字典代码 | string | 字典代码，如：KX-21 |
| 2 | 2 | NAME | 字典名称 | string | 字典名称，如：KX-21 |
| 3 | 3 | DICID | 字典ID | string | 字典ID，如：1001 |
| 4 | 4 | DICTYPE | 字典类型 | string | 字典类型，如：生化 |
| 5 | 5 | EXTERNCODE | 外部码 | string | 外部码，如：KX-21 |
| 6 | 6 | MEMCODE1 | 输入码一 | string | 输入码一 |
| 7 | 7 | MEMCODE2 | 输入码二 | string | 输入码二 |
| 8 | 8 | SUBSYSCODE | 系统代码 | string | 系统代码，LIS默认为LIMS |
| 9 | 9 | ICONCLASS | 图标类型 | string | 可为空 |
| 10 | 10 | ORDERNO | 序号 | number | 排序序号，根据此字段来显示展示顺序 |





JSON返回示例：

"controlsparams":{

"OP_DELREPORT":TRUE,

"OP_DELRESULT": TRUE,

"OP_DELMARKITEM": TRUE,

},

"instlist":[

{

    "CODE":"10010",

    "NAME":"AU5800",

    "DICID":"10010",

    "DICTYPE":"生化",

    "EXTERNCODE":"10010",

    "MEMCODE1":"10010",

    "MEMCODE2":"10010",

    "SUBSYSCODE":"LIMS",

    "ORDERNO":"1"

},

{

    "CODE":"10010",

    "NAME":"AU5800",

    "DICID":"10010",

    "DICTYPE":"生化",

    "EXTERNCODE":"10010",

    "MEMCODE1":"10010",

    "MEMCODE2":"10010",

    "SUBSYSCODE":"LIMS",

    "ORDERNO":"1"

}]



代码实现：

调用时机：用户进入报告删除模块，页面初始化时调用

表结构：SYS_DEPT\SETTINGS\SETTINGSDIC

代码实现：

分别从接口表中获取对应的初始化参数信息



#### B 业务类

##### B01	获取报告列表

接口说明：根据用户选择的参数，获取lis_list表中报告信息

请求URL：../reportdelete/request/getreportlist

代码文件：winning.lis.reportdelete.service.ReportDeleteService

示例URL：http://192.168.11.211:15011/lis/reportdelete/request/getreportlist?'instid'=''&exectime=’’&specimenrange=[]



原型参考：





接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | instid | 仪器ID | string | 仪器ID 如 10010（必传） |
| 2 | exectime | 检测时间 | string | 检测时间 ，如：2017-06-23（必传） |
| 3 | specimenrange | 样本号范围 | string | 样本号集合采用“，”“-”进行拼接
如：1,3,5,6-20(必传) |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | REPORTSUM | 报告数量 | int | 如：20 |
| 2 | 节点名称：reportlist  报告列表  类型：array<object> | 节点名称：reportlist  报告列表  类型：array<object> | 节点名称：reportlist  报告列表  类型：array<object> | 节点名称：reportlist  报告列表  类型：array<object> |
|  | OP_CHECK | 选择状态 | boolean | true -选中 false –未选中 |
|  | SIGN | 标识 | string | 0：删除失败  1：删除成功  默认状态为-1 |
|  | FAILTREASON | 删除失败原因 | string | 当SIGN=0,获取该字段，展示在页面 |
|  | APPLYNO | 申请单号 | string | 如：87272 |
|  | TECHNO | 样本号 | string | 样本号  LIS_LIST.TECHNO |
|  | ITEMCNT | 项目数量 | int | 表示一份报告中检验项目的数量 |
|  | STATUS | 报告状态 | int | 报告状态 LIS_LIST.STATUS |
|  | STATUSNAME | 报告状态名称 | string | 10-初始报告 20-部分指标检测完成 30-已完成检测（含多做） 40-初审通过 50-终审通过 60-发布 |
|  | BRLX | 病人类型 | string | 0-门诊 1-住院 3、4-体检 |
|  | PATIENTID | 病人唯一号 | string | 取患者属性：PATIENTID |
|  | CURENO | 就诊号 | string | 取患者属性：CURENO |
|  | CARDNO | 磁卡号 | string | 取患者属性：CARDNO |
|  | HOSPNO | 病员号\|住院号 | string | 门诊-病员号
住院-住院号
取患者属性：HOSPNO |
|  | PATNAME | 患者姓名 | string | 取患者属性：PATNAME |
|  | SEX | 性别 | string | 取患者属性：SEX |
|  | IDNUM | 身份证号 | string | 取患者属性：IDNUM |
|  | SEXDESC | 性别描述 | string | 性别描述  如：男 |
|  | AGEDESC | 年龄 | string | 年龄描述   如：17岁 |
|  | WARD | 病区代码 | string | 病区代码 |
|  | WARDNAME | 病区名称 | string | 病区名称 |
|  | BEDNO | 床号 | string | 床号，如：+10床 |
|  | APPLYDEPTCODE | 申请科室代码 | string | 申请科室 |
|  | APPLYDEPTNAME | 申请科室名称 | string | 申请科室名称 |
|  | SAMPLEDESCCODE | 标本说明代码 | string | 标本说明 |
|  | SAMPLEDESCNAME | 标本说明名称 | string | 标本说明名称 |
|  | SAMPLECODE | 标本种类 | string | 标本种类  如：1 |
|  | SAMPLENAME | 标本种类名称 | Double | 标本种类名称  如：血 |
|  | APPLYDOCCODE | 申请医生代码 | string | 申请医生代码  如：1067 |
|  | APPLYDOCNAME | 申请医生名称 | string | 申请医生名称  如：张三 |
|  | CHARGETYPE | 费用类别 | string |  |
|  | EXECDOCCODE | 检验医生 | string | 检验医生代码  如：1067 |
|  | EXECDOCNAME | 检验医生名称 | string | 检验医生名称  如：张三 |
|  | CLINICDESC | 临床诊断 | string | 临床诊断，如：感冒 |
|  | APPLYTIME | 申请时间 | string | 如：2017-09-09 |
|  | SAMPLETIME | 采样时间 | string | 如：2017-09-09 |
|  | WORKERSIGNTIME | 护工签收时间 | string | 如：2017-09-09 |
|  | REMARK | 备注 | string |  |





JSON返回示例：

REPORTSUM：10,

“reportlist”:[

{

"OP_CHECK":"false",

“SIGN”:””,

“FAILTREASON”:””,

"APPLYNO":"334324",

"TECHNO":""12,

"ITEMCNT":10,

"STATUS":50,

"STATUSNAME":"已审核",

"BRLX":"1",

...

},

{

"OP_CHECK":"false",

“SIGN”:””,

“FAILTREASON”:””,

"APPLYNO":"334324",

"TECHNO":""12,

"ITEMCNT":10,

"STATUS":50,

"STATUSNAME":"已审核",

"BRLX":"1",

...

}]



调用时机：用户选择仪器名称和检测时间，样本号范围，点击查询，获取报告列表信息

相关表结构：LIS_list\Lis_RESULT

代码实现：

根据查询条件从lis_list表中查询对应的报告数据



##### B02	报告（结果）删除

接口说明：删除报告数据和报告结果数据

请求URL：../reportdelete/request/reportdel

代码文件：winning.lis.reportdelete.service.ReportDeleteService

示例URL：http://192.168.11.211:15011/lis/reportdelete/request/reportdel?reportlist=[]

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | oprtype | 操作类型 | string | 0：删除报告&结果
1：仅删除结果 |
| 2 | 节点名称：reportlist  类型：array<object>  见103.B01 接口出参reportlist节点 | 节点名称：reportlist  类型：array<object>  见103.B01 接口出参reportlist节点 | 节点名称：reportlist  类型：array<object>  见103.B01 接口出参reportlist节点 | 节点名称：reportlist  类型：array<object>  见103.B01 接口出参reportlist节点 |
|  | APPLYNO | 申请单号 | boolean | 如：87272 |
|  | MJZBZ | 门急诊标志 | string | 门急诊标志 0-常规 1-加急 |
|  | STATUS | 病人类型 | string | 10-初始报告 20-部分指标检测完成 30-已完成检测（含多做） 40-初审通过 50-终审通过 60-发布 |
|  | EXECTIME | 检测时间 | string | 如：2017-09-09 |
|  | TECHNO | 样本号 | string | 如：1 |
|  | BRLX | 病人类型 | string | 0-门诊 1-住院 3、4-体检 |
|  | PATNAME | 患者姓名 | string | 患者姓名 |
|  | HOSPNO | 住院号 | string | 住院号 |
|  | CARDNO | 磁卡号 | string | 磁卡号 |
|  | SEX | 性别 | string | 性别 1-男 2-女 3-未知 |
|  | SEXDESC | 性别描述 | string | 性别描述 |
|  | AGE | 年龄 | string | 年龄 |
|  | AGEUNIT | 年龄单位 | number | 年龄单位，如：岁 |
|  | AGEDESC | 年龄 | string | 年龄描述 |
|  | WARD | 病区代码 | string | 病区代码 |
|  | WARDNAME | 病区名称 | string | 病区名称 |
|  | BEDNO | 床号 | string | 床号，如：+10床 |
|  | CLINICDESC | 临床诊断 | string | 临床诊断，如：感冒 |
|  | INSTID | 仪器ID | string | 如：10010 |
|  | INSTNAME | 仪器名称 | string | 如：AU5800 |
|  | HISORDERCODE | 项目代码 | string | HIS项目代码+连接 |
|  | HISORDERNAME | 项目名称 | string | HIS项目名称+连接 |
|  | CHARGEAMOUNT | 收费金额 | Double | 如：100.00 |
|  | REMARK | 备注 | string |  |





接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：reportlist  类型：array<object>  删除成功的报告信息 | 节点名称：reportlist  类型：array<object>  删除成功的报告信息 | 节点名称：reportlist  类型：array<object>  删除成功的报告信息 | 节点名称：reportlist  类型：array<object>  删除成功的报告信息 | 节点名称：reportlist  类型：array<object>  删除成功的报告信息 |
| 1 | APPLYNO | 申请单号 | boolean | 如：87272 |
| 2 | SIGN | 标识 | string | 0：删除失败  1：删除成功  默认状态为空 |
| 3 | FAILTREASON | 删除失败原因 | string | 当SIGN=0,获取该字段，展示在页面 |
| 2 | MJZBZ | 门急诊标志 | string | 门急诊标志 0-常规 1-加急 |
| 3 | STATUS | 病人类型 | string | 10-初始报告 20-部分指标检测完成 30-已完成检测（含多做） 40-初审通过 50-终审通过 60-发布 |
| 4 | EXECTIME | 检测时间 | string | 如：2017-09-09 |
| 5 | TECHNO | 样本号 | string | 如：1 |
| 6 | BRLX | 病人类型 | string | 0-门诊 1-住院 3、4-体检 |
| 7 | PATNAME | 患者姓名 | string | 患者姓名 |
| 8 | HOSPNO | 住院号 | string | 住院号 |
| 9 | CARDNO | 磁卡号 | string | 磁卡号 |
| 10 | SEX | 性别 | string | 性别 1-男 2-女 3-未知 |
| 11 | SEXDESC | 性别描述 | string | 性别描述 |
| 12 | AGE | 年龄 | string | 年龄 |
| 13 | AGEUNIT | 年龄单位 | number | 年龄单位，如：岁 |
| 14 | AGEDESC | 年龄 | string | 年龄描述 |
| 15 | WARD | 病区代码 | string | 病区代码 |
| 16 | WARDNAME | 病区名称 | string | 病区名称 |
| 17 | BEDNO | 床号 | string | 床号，如：+10床 |
| 18 | CLINICDESC | 临床诊断 | string | 临床诊断，如：感冒 |
| 19 | INSTID | 仪器ID | string | 如：10010 |
| 20 | INSTNAME | 仪器名称 | string | 如：AU5800 |
| 21 | HISORDERCODE | 项目代码 | string | HIS项目代码+连接 |
| 22 | HISORDERNAME | 项目名称 | string | HIS项目名称+连接 |
| 23 | CHARGEAMOUNT | 收费金额 | Double | 如：100.00 |
| 24 | REMARK | 备注 | string |  |



JSON格式

“reportlist”:[

{

"OP_CHECK":"false",

“SIGN”:”0”,

“FAILTREASON”:””,

"APPLYNO":"334324",

"TECHNO":""12,

"ITEMCNT":10,

"STATUS":50,

"STATUSNAME":"已审核",

"BRLX":"1",

...

},

{

"OP_CHECK":"false",

“SIGN”:”0”,

“FAILTREASON”:””,

"APPLYNO":"334324",

"TECHNO":""12,

"ITEMCNT":10,

"STATUS":50,

"STATUSNAME":"已审核",

"BRLX":"1",

...

}]

代码实现：

代码参考存储过程：lsp_BatchDelSample

代码逻辑：

			1、根据入参中的操作类型字段，判断是否删除报告&结果信息；

	2、如果全都删除，则需要判断该报告是否存在收费项目，如果存在，不允许删除；

	3、校验“已审核报告是否允许删除”参数，过滤当前报告是否允许删除；

	4、根据“已打印报告是否允许删除”参数，过滤当前报告是否允许删除；

	5、删除前，报告信息插入Lis_ResultMod表

	6、删除is_list和lis_result表中数据

	7、记录日志（lis_reportlog）

表结构：lis_list\lis_result\LIS_ResultMod

调用时机：用户选中页面上的报告列表，点击“删除报告”按钮，被删除的报告不再显示在页面上；

##### B03	获取指定仪器对应的项目信息

接口说明：获取指定仪器对应的项目信息

请求URL：../reportdelete/request/getitemlist

代码文件：winning.lis.reportdelete.service.ReportDeleteService

示例URL：http://192.168.11.211:15011/lis/reportdelete/request/getitemlist?instid=10010&hospitalcode=9999

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | instid | 仪器ID | string | 仪器ID 如：10010 （必传） |
| 2 | hospitalcode | 机构代码 | string | 可空 |





接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：itemllist  项目信息  类型：array<object> | 节点名称：itemllist  项目信息  类型：array<object> | 节点名称：itemllist  项目信息  类型：array<object> | 节点名称：itemllist  项目信息  类型：array<object> | 节点名称：itemllist  项目信息  类型：array<object> |
| 1 | ITEMCODE | 项目代码 | string | 项目代码  如：FIB |
| 2 | ITEMNAME | 项目名称 | string | 项目名称  如：纤维蛋白原 |



JSON格式

“itemlist”:[

{

"ITEMCODE":"FIB",

"ITEMNAME":"纤维蛋白原"

},

{

"ITEMCODE":"FIB",

"ITEMNAME":"纤维蛋白原"

}]

代码实现：

代码逻辑：

		1、根据仪器ID从lis_institem表中获取该仪器下所有对应的项目信息	

表结构：lis_institem

调用时机：用户选中页面上的报告列表，点击“删除制定项目”按钮，弹出弹窗，默认调用该接口，将出参数据展示在弹窗列表中；



##### B04	报告指定项目删除

接口说明：删除报告中指定项目结果数据

请求URL：../reportdelete/request/designitemdel

代码文件：winning.lis.reportdelete.service.ReportDeleteService

示例URL：http://192.168.11.211:15011/lis/reportdelete/request/designitemdel?itemlist=[]&reportlist=[]

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | 节点名称：itemlist  类型：array<object>   见103.B03出参 | 节点名称：itemlist  类型：array<object>   见103.B03出参 | 节点名称：itemlist  类型：array<object>   见103.B03出参 | 节点名称：itemlist  类型：array<object>   见103.B03出参 |
|  | ITEMCODE | 项目代码 | string | 项目代码  如：FIB |
|  | ITEMNAME | 项目名称 | string | 项目名称  如：纤维蛋白原 |
| 2 | 节点名称：reportlist  类型：array<object>  103.B01 接口出参 | 节点名称：reportlist  类型：array<object>  103.B01 接口出参 | 节点名称：reportlist  类型：array<object>  103.B01 接口出参 | 节点名称：reportlist  类型：array<object>  103.B01 接口出参 |
|  | OP_CHECK | 选择状态 | boolean | true -选中 false –未选中 |
|  | APPLYNO | 申请单号 | boolean | 如：87272 |
|  | MJZBZ | 门急诊标志 | string | 门急诊标志 0-常规 1-加急 |
|  | STATUS | 病人类型 | string | 10-初始报告 20-部分指标检测完成 30-已完成检测（含多做） 40-初审通过 50-终审通过 60-发布 |
|  | EXECTIME | 检测时间 | string | 如：2017-09-09 |
|  | TECHNO | 样本号 | string | 如：1 |
|  | BRLX | 病人类型 | string | 0-门诊 1-住院 3、4-体检 |
|  | PATNAME | 患者姓名 | string | 患者姓名 |
|  | HOSPNO | 住院号 | string | 住院号 |
|  | CARDNO | 磁卡号 | string | 磁卡号 |
|  | SEX | 性别 | string | 性别 1-男 2-女 3-未知 |
|  | SEXDESC | 性别描述 | string | 性别描述 |
|  | AGE | 年龄 | string | 年龄 |
|  | AGEUNIT | 年龄单位 | number | 年龄单位，如：岁 |
|  | AGEDESC | 年龄 | string | 年龄描述 |
|  | WARD | 病区代码 | string | 病区代码 |
|  | WARDNAME | 病区名称 | string | 病区名称 |
|  | BEDNO | 床号 | string | 床号，如：+10床 |
|  | CLINICDESC | 临床诊断 | string | 临床诊断，如：感冒 |
|  | INSTID | 仪器ID | string | 如：10010 |
|  | INSTNAME | 仪器名称 | string | 如：AU5800 |
|  | HISORDERCODE | 项目代码 | string | HIS项目代码+连接 |
|  | HISORDERNAME | 项目名称 | string | HIS项目名称+连接 |
|  | CHARGEAMOUNT | 收费金额 | Double | 如：100.00 |
|  | REMARK | 备注 | string |  |





接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：reportlist  类型：array<object>  待确定 | 节点名称：reportlist  类型：array<object>  待确定 | 节点名称：reportlist  类型：array<object>  待确定 | 节点名称：reportlist  类型：array<object>  待确定 | 节点名称：reportlist  类型：array<object>  待确定 |
| 1 | OP_CHECK | 选择状态 | boolean | true -选中 false –未选中 |
| 2 | SIGN | 标识 | string | 0：删除失败  1：删除成功  默认状态为空 |
| 3 | FAILTREASON | 删除失败原因 | string | 当SIGN=0,获取该字段，展示在页面 |
| 3 | APPLYNO | 申请单号 | boolean | 如：87272 |
| 4 | MJZBZ | 门急诊标志 | string | 门急诊标志 0-常规 1-加急 |
| 5 | STATUS | 病人类型 | string | 10-初始报告 20-部分指标检测完成 30-已完成检测（含多做） 40-初审通过 50-终审通过 60-发布 |
| 6 | EXECTIME | 检测时间 | string | 如：2017-09-09 |
| 7 | TECHNO | 样本号 | string | 如：1 |
| 8 | BRLX | 病人类型 | string | 0-门诊 1-住院 3、4-体检 |
| 9 | PATNAME | 患者姓名 | string | 患者姓名 |
| 10 | HOSPNO | 住院号 | string | 住院号 |
| 11 | CARDNO | 磁卡号 | string | 磁卡号 |
| 12 | SEX | 性别 | string | 性别 1-男 2-女 3-未知 |
| 13 | SEXDESC | 性别描述 | string | 性别描述 |
| 14 | AGE | 年龄 | string | 年龄 |
| 15 | AGEUNIT | 年龄单位 | number | 年龄单位，如：岁 |
| 16 | AGEDESC | 年龄 | string | 年龄描述 |
| 17 | WARD | 病区代码 | string | 病区代码 |
| 18 | WARDNAME | 病区名称 | string | 病区名称 |
| 19 | BEDNO | 床号 | string | 床号，如：+10床 |
| 20 | CLINICDESC | 临床诊断 | string | 临床诊断，如：感冒 |
| 21 | INSTID | 仪器ID | string | 如：10010 |
| 22 | INSTNAME | 仪器名称 | string | 如：AU5800 |
| 23 | HISORDERCODE | 项目代码 | string | HIS项目代码+连接 |
| 24 | HISORDERNAME | 项目名称 | string | HIS项目名称+连接 |
| 25 | CHARGEAMOUNT | 收费金额 | Double | 如：100.00 |
| 26 | REMARK | 备注 | string |  |



JSON格式

“reportlist”:[

{

"OP_CHECK":"false",

“SIGN”:”0”,

“FAILTREASON”:””,

"APPLYNO":"334324",

"TECHNO":"12,

"ITEMCNT":10,

"STATUS":50,

"STATUSNAME":"已审核",

"BRLX":"1",

...

},

{

"OP_CHECK":"false",

“SIGN”:”0”,

“FAILTREASON”:””,

"APPLYNO":"334324",

"TECHNO":""12,

"ITEMCNT":10,

"STATUS":50,

"STATUSNAME":"已审核",

"BRLX":"1",

...

}]

代码实现：

代码参考存储过程：lsp_BatchDelResult

代码逻辑：

			1、根据报告状态，判断该报告是否允许删除；

	2、将报告结果数据插入到Lis_ResultMod；

	3、删除lis_result表中中指定报告和项目的数据；

	4、根据“审核后的报告是否允许再修改”参数，判断，是否需要修改lis_list.Status字段；

	5、记录日志（lis_reportlog）

表结构：lis_list\lis_result

调用时机：用户点击项目信息弹窗上的确定按钮，调用该接口；



### 危急值处理

#### 模块接口视图

| NO. | 接口编号 | 接口名称 | 说明 |
| --- | --- | --- | --- |
| 1 | A01 | 获取初始化参数
/panicvalue/request/getinitdata | 获取危急值处理的初始化参数报告信息，检测项目，诊断信息，标本说明等 |
| 2 | B01 | 危急值发布
/panicvalue/request/publishpanicinfo | 根据输入框的信息，发布危急值 |
| 3 | B02 | 危急值召回
/panicvalue/request/recallpanicinfo | 将当前危急值报告召回 |
| 4 | B03 | 不发布
/panicvalue/request/unpublish | 将当前危急值报告状态修改为不发布 |
| 5 | B04 | 危急值电话回报
/panicvalue/request/phonerecall | 危机值电话回报信息存储 |
| 6 | B05 | 获取危急值流程和汇总信息
/panicvalue/request/getpanicflowinfo | 回去危急值流程图和汇总信息展示 |
| 7 | B06 | 获取一周内危急值项目历史明细
/panicvalue/request/getpanicitems | 点击一周内汇总获取项目明细 |





#### 初始化数据

接口说明：获取危急值处理的初始化参数报告信息，检测项目，诊断信息，标本说明等

URL：. /panicvalue/request/getinitdata

实例URL: /panicvalue/request/getinitdata

原型参考：  



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | applyno | 报告单号 | string | 返回当前报告的报告单号 |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 含义 | 类型 | 类型 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- | --- | --- | --- |
| 危急值信息节点名称：paniclist类型：array<object> | 危急值信息节点名称：paniclist类型：array<object> | 危急值信息节点名称：paniclist类型：array<object> | 危急值信息节点名称：paniclist类型：array<object> | 危急值信息节点名称：paniclist类型：array<object> | 危急值信息节点名称：paniclist类型：array<object> | 危急值信息节点名称：paniclist类型：array<object> | 危急值信息节点名称：paniclist类型：array<object> |
| 1 | STATUS | 危急值发布状态 | 危急值发布状态 | string | string | string | 危急值发布状态，如：未发布 |
| 2 | REPORTDESC | 危急值描述 | 危急值描述 | string | string | string | 危急值描述，可能为空 |
| 3 | ISREDO | 结果复做 | 结果复做 | string | string | string | 结果复做，0-否 1-是 |
| 4 | ISINSTLOSE | 仪器在控 | 仪器在控 | string | string | string | 仪器在控，0-否 1-是 |
| 5 | SAMPLEDESC | 标本说明 | 标本说明 | string | string | string | 标本说明，如：标本溶血 |
| 6 | DIFFWITHCLIN | 结果与诊断不符 | 结果与诊断不符 | string | string | string | 结果与诊断不符，如：0-否，1-是 |
| 7 | CALLERCODE | 打电话通知临床的人员工号 | 打电话通知临床的人员工号 | string | string | string | 通知人工号，如：1047 |
| 8 | CALLERNAME | 打电话通知临床的人员姓名 | 打电话通知临床的人员姓名 | string | string | string | 通知人姓名，如：李庆华 |
| 9 | CALLDATE | 打电话通知临床的时间 | 打电话通知临床的时间 | string | string | string | 通知时间，如： |
| 10 | ACKPERSON | 接听人员代码 | 接听人员代码 | string | string | string | 接听人员姓名，如：张三 |
| 11 | ACKPERSONNAME | 接听人姓名 | 接听人姓名 | string | string | string | 接听电话，如：15645621256 |
| 12 | ACKREPEAT | 接听者复述 | 接听者复述 | string | string | string | 接听者复述，0-否 1-是 |
| 13 | ACKPHONE | 接听电话 | 接听电话 | string | string | string | 接听电话，如：15645621256 |
| 报告信息节点名称：reportlist类型：array<object> | 报告信息节点名称：reportlist类型：array<object> | 报告信息节点名称：reportlist类型：array<object> | 报告信息节点名称：reportlist类型：array<object> | 报告信息节点名称：reportlist类型：array<object> | 报告信息节点名称：reportlist类型：array<object> | 报告信息节点名称：reportlist类型：array<object> | 报告信息节点名称：reportlist类型：array<object> |
| 1 | PANICFLAG | 危急值标志 | 危急值标志 | string | string | string | 危急值标志，如：0-否 1-是 |
| 2 | APPLYNO | 报告单号 | 报告单号 | string | string | string | 报告单号，如：469148 |
| 3 | PATNAME | 患者姓名 | 患者姓名 | string | string | string | 患者姓名，如：夏秀珍 |
| 4 | AGE | 年龄 | 年龄 | string | string | string | 年龄，如：12 |
| 5 | AGEDESC | 年龄描述 | 年龄描述 | string | string | string | 年龄描述，如：12岁 |
| 6 | AGEUNIT | 年龄单位 | 年龄单位 | string | string | string | 年龄单位，如：岁 |
| 7 | M_PHONE | 电话号码 | 电话号码 | string | string | string | 电话号码，如：13656423565 |
| 8 | ORGAPPLYNO | 条形码 | 条形码 | string | string | string | 条形码，如：07562865621 |
| 9 | SAMPLENAME | 标本种类 | 标本种类 | string | string | string | 标本种类，如：血 |
| 10 | APPLYDEPTCODE | 申请科室代码 | 申请科室代码 | string | string | string | 申请科室代码，如：1232 |
| 11 | APPLYDEPTNAME | 申请科室名称 | 申请科室名称 | string | string | string | 申请科室名称，如：心内科 |
| 12 | WARD | 病区代码 | 病区代码 | string | string | string | 病区代码，如：1009 |
| 13 | WARDNAME | 病区名称 | 病区名称 | string | string | string | 病区名称，如：心血管内科病房 |
| 14 | BEDNO | 床号 | 床号 | string | string | string | 床号，如：36 |
| 15 | HISORDERCODE | 收费项目代码 | 收费项目代码 | string | string | string | 收费项目代码，如：72,73,626 |
| 16 | HISORDERNAME | 收费项目名称 | 收费项目名称 | string | string | string | 收费项目名称，如：心肌酶谱,心梗三项,急诊生化全套 |
| 17 | CHARGEAMOUNT | 收费金额 | 收费金额 | string | string | string | 收费金额，如：382.00 |
| 18 | CLINICDESC | 临床诊断 | 临床诊断 | string | string | string | 临床诊断，如：急性支气管炎 |
| 19 | SEXDESC | 性别 | 性别 | string | string | string | 性别，如：男，女，未知 |
| 标本描述节点名称：sampledescribelist类型：array<object> | 标本描述节点名称：sampledescribelist类型：array<object> | 标本描述节点名称：sampledescribelist类型：array<object> | 标本描述节点名称：sampledescribelist类型：array<object> | 标本描述节点名称：sampledescribelist类型：array<object> | 标本描述节点名称：sampledescribelist类型：array<object> | 标本描述节点名称：sampledescribelist类型：array<object> | 标本描述节点名称：sampledescribelist类型：array<object> |
| 1 | CODE | CODE | 字典代码 | 字典代码 | string | 字典代码，如：KX-21 | 字典代码，如：KX-21 |
| 2 | NAME | NAME | 字典名称 | 字典名称 | string | 字典名称，如：KX-21 | 字典名称，如：KX-21 |
| 3 | DICID | DICID | 字典ID | 字典ID | string | 字典ID，如：1001 | 字典ID，如：1001 |
| 4 | DICTYPE | DICTYPE | 字典类型 | 字典类型 | string | 字典类型，如：生化 | 字典类型，如：生化 |
| 5 | EXTERNCODE | EXTERNCODE | 外部码 | 外部码 | string | 外部码，如：KX-21 | 外部码，如：KX-21 |
| 6 | MEMCODE1 | MEMCODE1 | 输入码一 | 输入码一 | string | 输入码一 | 输入码一 |
| 7 | MEMCODE2 | MEMCODE2 | 输入码二 | 输入码二 | string | 输入码二 | 输入码二 |
| 8 | SUBSYSCODE | SUBSYSCODE | 系统代码 | 系统代码 | string | 系统代码，LIS默认为LIMS | 系统代码，LIS默认为LIMS |
| 9 | ICONCLASS | ICONCLASS | 图标类型 | 图标类型 | string | 可为空 | 可为空 |
| 10 | ORDERNO | ORDERNO | 序号 | 序号 | number | 排序序号，根据此字段来显示展示顺序 | 排序序号，根据此字段来显示展示顺序 |
| 通知人员信息节点：noticemanlist类型：array<object> | 通知人员信息节点：noticemanlist类型：array<object> | 通知人员信息节点：noticemanlist类型：array<object> | 通知人员信息节点：noticemanlist类型：array<object> | 通知人员信息节点：noticemanlist类型：array<object> | 通知人员信息节点：noticemanlist类型：array<object> | 通知人员信息节点：noticemanlist类型：array<object> | 通知人员信息节点：noticemanlist类型：array<object> |
| 1 | CODE | CODE | 字典代码 | 字典代码 | string | 字典代码，如：KX-21 | 字典代码，如：KX-21 |
| 2 | NAME | NAME | 字典名称 | 字典名称 | string | 字典名称，如：KX-21 | 字典名称，如：KX-21 |
| 3 | DICID | DICID | 字典ID | 字典ID | string | 字典ID，如：1001 | 字典ID，如：1001 |
| 4 | DICTYPE | DICTYPE | 字典类型 | 字典类型 | string | 字典类型，如：生化 | 字典类型，如：生化 |
| 5 | EXTERNCODE | EXTERNCODE | 外部码 | 外部码 | string | 外部码，如：KX-21 | 外部码，如：KX-21 |
| 6 | MEMCODE1 | MEMCODE1 | 输入码一 | 输入码一 | string | 输入码一 | 输入码一 |
| 7 | MEMCODE2 | MEMCODE2 | 输入码二 | 输入码二 | string | 输入码二 | 输入码二 |
| 8 | SUBSYSCODE | SUBSYSCODE | 系统代码 | 系统代码 | string | 系统代码，LIS默认为LIMS | 系统代码，LIS默认为LIMS |
| 9 | ICONCLASS | ICONCLASS | 图标类型 | 图标类型 | string | 可为空 | 可为空 |
| 10 | ORDERNO | ORDERNO | 序号 | 序号 | number | 排序序号，根据此字段来显示展示顺序 | 排序序号，根据此字段来显示展示顺序 |
| 接听人员信息节点：answermanlist类型：array<object> | 接听人员信息节点：answermanlist类型：array<object> | 接听人员信息节点：answermanlist类型：array<object> | 接听人员信息节点：answermanlist类型：array<object> | 接听人员信息节点：answermanlist类型：array<object> | 接听人员信息节点：answermanlist类型：array<object> | 接听人员信息节点：answermanlist类型：array<object> | 接听人员信息节点：answermanlist类型：array<object> |
| 1 | CODE | CODE | 字典代码 | 字典代码 | string | 字典代码，如：KX-21 | 字典代码，如：KX-21 |
| 2 | NAME | NAME | 字典名称 | 字典名称 | string | 字典名称，如：KX-21 | 字典名称，如：KX-21 |
| 3 | DICID | DICID | 字典ID | 字典ID | string | 字典ID，如：1001 | 字典ID，如：1001 |
| 4 | DICTYPE | DICTYPE | 字典类型 | 字典类型 | string | 字典类型，如：生化 | 字典类型，如：生化 |
| 5 | EXTERNCODE | EXTERNCODE | 外部码 | 外部码 | string | 外部码，如：KX-21 | 外部码，如：KX-21 |
| 6 | MEMCODE1 | MEMCODE1 | 输入码一 | 输入码一 | string | 输入码一 | 输入码一 |
| 7 | MEMCODE2 | MEMCODE2 | 输入码二 | 输入码二 | string | 输入码二 | 输入码二 |
| 8 | SUBSYSCODE | SUBSYSCODE | 系统代码 | 系统代码 | string | 系统代码，LIS默认为LIMS | 系统代码，LIS默认为LIMS |
| 9 | ICONCLASS | ICONCLASS | 图标类型 | 图标类型 | string | 可为空 | 可为空 |
| 10 | ORDERNO | ORDERNO | 序号 | 序号 | number | 排序序号，根据此字段来显示展示顺序 | 排序序号，根据此字段来显示展示顺序 |
| 当前报告危急值项目明细：panicitemlist类型：array<object> | 当前报告危急值项目明细：panicitemlist类型：array<object> | 当前报告危急值项目明细：panicitemlist类型：array<object> | 当前报告危急值项目明细：panicitemlist类型：array<object> | 当前报告危急值项目明细：panicitemlist类型：array<object> | 当前报告危急值项目明细：panicitemlist类型：array<object> | 当前报告危急值项目明细：panicitemlist类型：array<object> | 当前报告危急值项目明细：panicitemlist类型：array<object> |
| 1 | APPLYNO | APPLYNO | 报告单号 | 报告单号 | string | 做核对使用 | 做核对使用 |
| 2 | ITEMCODE | ITEMCODE | 项目代码 | 项目代码 | string |  |  |
| 3 | ITEMNAME | ITEMNAME | 项目名称 | 项目名称 | string |  |  |
| 4 | RESULTTIME | RESULTTIME | 结果时间 | 结果时间 | string | yyyy-MM-dd hh:mm:ss | yyyy-MM-dd hh:mm:ss |
| 5 | RESULT | RESULT | 结果值 | 结果值 | string |  |  |
| 6 | HIGHLOWFLAG | HIGHLOWFLAG | 高低标志 | 高低标志 | string |  |  |
| 7 | REFERENCERANGE | REFERENCERANGE | 参考值 | 参考值 | string |  |  |
| 8 | UNIT | UNIT | 单位 | 单位 | string |  |  |
| 9 | REDO | REDO | 复做标志 | 复做标志 | string | 1-复做 0-未复做 | 1-复做 0-未复做 |
| 10 | PANICFLAG | PANICFLAG | 危急值标志 | 危急值标志 | string | 1-危急值 | 1-危急值 |
| 11 | PANICREFERENCERANGE | PANICREFERENCERANGE | 危急值参考值 | 危急值参考值 | string |  |  |





说明：

JSON返回示例：

"paniclist": {

       "STATUS": "未发布",

                "REPORTDESC": "心血管内科病房【36床】夏秀珍 病员号495778样本种类【血】 检验项目：\r钙的结果:1.52 mmol/L大于或等于3.5000 mmol/L；,

      "ISREDO": null,

      "ISINSTLOSE": null,

      "SAMPLEDESC": null,

      "DIFFWITHCLIN": null,

      "REMARK": null,

      "CALLERCODE": "1047",

      "CALLERNAME": "李庆华",

      "CALLDATE": "2018-03-06 14:57:52",

      "ACKPERSON": null,

      "ACKPERSONNAME": null    },

    "reportlist": [

      {

        "PANICFLAG": 1,

        "APPLYNO": 469148,

        "PATNAME": "夏秀珍",

        "AGE": 75,

        "AGEDESC": "75岁    ",

        "AGEUNIT": "岁    ",

        "M_PHONE": "",

        "ORGAPPLYNO": "01000166290P",

        "SAMPLENAME": "血",

        "APPLYDEPTCODE": "1411",

        "APPLYDEPTNAME": "心内科",

        "WARD": "1009",

        "WARDNAME": "心血管内科病房",

        "BEDNO": "36",

        "HISORDERCODE": "72,73,626",

        "HISORDERNAME": "心肌酶谱,心梗三项,急诊生化全套",

        "CHARGEAMOUNT": 382,

        "CLINICDESC": "急性支气管炎"

      }

    ],

    "sampledescribelist": [

      {

        "CODE": "1",

        "NAME": "标本溶血",

        "DICID": "1",

        "DICTYPE": "标本说明",

        "EXTERNCODE": "",

        "MEMCODE1": "1",

        "MEMCODE2": "RX",

        "SUBSYSCODE": "LIMS",

        "ORDERNO": 0,

        "HOSPITALCODE": "9999"

      },{

        "CODE": "9",

        "NAME": "标本溶血，脂血",

        "DICID": "9",

        "DICTYPE": "标本说明",

        "EXTERNCODE": null,

        "MEMCODE1": "9",

        "MEMCODE2": "RXZX",

        "SUBSYSCODE": "LIMS",

        "ORDERNO": 0,

        "HOSPITALCODE": "9999"

      }

],

panicitemlist:[

……

]

代码实现：

接听人员 usertype = 1 or 2 通知人员 usertype = 3 接听人员选择的时候要带出来电话

#### 业务类

#####  危急值发布

接口说明：点击危急值发布按钮，处理危急值发布业务

请求URL：/panicvalue/request/publishpanicinfo

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | ackrepeat | 接听者复述 | string | 接听者复述，如1-是0否 |
| 2 | isinstlose | 仪器在控 | string | 仪器在控，如：1-是0否 |
| 3 | applyno | 报告单号 | string | 报告单号，如：469148 |
| 4 | isredo | 是否复做 | string | 是否复做，如：1-是0否 |
| 5 | sampledesc | 标本说明 | string | 标本说明：如：标本溶血 |
| 6 | diffwithclin | 结果与临床诊断不符 | string | 结果与临床诊断不符，如：1-是0否 |
| 7 | ackphone | 电话号码 | string | 电话号码，如：13656465623 |
| 8 | reportdesc | 危机值描述 | string | 危急值描述，如；xxx白细胞计数333，大于22mol/ml |
| 9 | callername | 通知人员 | string | 通知人员，如：张三 |
| 10 | callercode | 通知人员代码 | string | 通知人员代码，如：1244 |
| 11 | calldate | 通知日期 | string | 通知日期，如：2018-03-07 |
| 12 | ackpersonname | 接听人姓名 | string | 接听人姓名，如：张三 |
| 13 | ackperson | 接听人代码 | string | 接听人代码，如：2333 |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 含义 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- | --- |
| 1 | MESSAGE | MESSAGE | 提示信息 | string | 报告没有检测项目 |
| 2 | TYPE | TYPE | 消息类型 | string | ERROR-错误
INFO-提示信息
WARN-警告信息
CONFIRM-确认信息
VERIFY-验证信息 |
| 节点名称：ResposeMessage.message 消息汇总信息 | 节点名称：ResposeMessage.message 消息汇总信息 | 节点名称：ResposeMessage.message 消息汇总信息 | 节点名称：ResposeMessage.message 消息汇总信息 | 节点名称：ResposeMessage.message 消息汇总信息 | 节点名称：ResposeMessage.message 消息汇总信息 |



代码实现：

##### 危急值召回

接口说明：点击危急值召回按钮，处理危急值召回业务

请求URL：/panicvalue/request/ recallpanicinfo

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | applyno | 报告单号 | string | 报告单号，如：469148 |
| 2 | recallmemo | 召回备注 | string | 召回备注，如：报告发布有误，现在召回 |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 含义 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- | --- |
| 节点名称：messagelist   类型：array<object>  Type=FAILT的时候才返回 | 节点名称：messagelist   类型：array<object>  Type=FAILT的时候才返回 | 节点名称：messagelist   类型：array<object>  Type=FAILT的时候才返回 | 节点名称：messagelist   类型：array<object>  Type=FAILT的时候才返回 | 节点名称：messagelist   类型：array<object>  Type=FAILT的时候才返回 | 节点名称：messagelist   类型：array<object>  Type=FAILT的时候才返回 |
| 1 | MESSAGE | MESSAGE | 提示信息 | string | 报告没有检测项目 |
| 2 | TYPE | TYPE | 消息类型 | string | ERROR-错误
INFO-提示信息
WARN-警告信息
CONFIRM-确认信息
VERIFY-验证信息 |
| 节点名称：ResposeMessage.message 消息汇总信息 | 节点名称：ResposeMessage.message 消息汇总信息 | 节点名称：ResposeMessage.message 消息汇总信息 | 节点名称：ResposeMessage.message 消息汇总信息 | 节点名称：ResposeMessage.message 消息汇总信息 | 节点名称：ResposeMessage.message 消息汇总信息 |



代码实现：

点击召回按钮，处理召回业务，将危急值状态修改为：未发布 

相关表结构：LAB_PANICREPTRACE

##### B03 不发布



接口说明：点击危急值不发布按钮，处理危急值不发布业务

请求URL：/panicvalue/request/unpublish

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | applyno | 报告单号 | string | 报告单号，如：469148 |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 含义 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- | --- |
| 节点名称：messagelist   类型：array<object>  Type=FAILT的时候才返回 | 节点名称：messagelist   类型：array<object>  Type=FAILT的时候才返回 | 节点名称：messagelist   类型：array<object>  Type=FAILT的时候才返回 | 节点名称：messagelist   类型：array<object>  Type=FAILT的时候才返回 | 节点名称：messagelist   类型：array<object>  Type=FAILT的时候才返回 | 节点名称：messagelist   类型：array<object>  Type=FAILT的时候才返回 |
| 1 | MESSAGE | MESSAGE | 提示信息 | string | 报告没有检测项目 |
| 2 | TYPE | TYPE | 消息类型 | string | ERROR-错误
INFO-提示信息
WARN-警告信息
CONFIRM-确认信息
VERIFY-验证信息 |
| 节点名称：ResposeMessage.message 消息汇总信息 | 节点名称：ResposeMessage.message 消息汇总信息 | 节点名称：ResposeMessage.message 消息汇总信息 | 节点名称：ResposeMessage.message 消息汇总信息 | 节点名称：ResposeMessage.message 消息汇总信息 | 节点名称：ResposeMessage.message 消息汇总信息 |



代码实现：

点击召回按钮，处理召回业务，将危急值状态修改为：未发布 

相关表结构：LAB_PANICREPTRACE



##### B04 危急值电话回报

接口说明：点击危急值召回按钮，处理危急值召回业务

请求URL：/panicvalue/request/ recallpanicinfo

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | applyno | 报告单号 | string | 报告单号，如：469148 |
| 2 | ackrepeat | 接听者复述 | string | 接听者复述，如1-是0否 |
| 3 | ackphone | 电话号码 | string | 电话号码，如：13656465623 |
| 4 | callername | 通知人员 | string | 通知人员，如：张三 |
| 5 | callercode | 通知人员代码 | string | 通知人员代码，如：1244 |
| 6 | calldate | 通知日期 | string | 通知日期，如：2018-03-07 |
| 7 | ackpersonname | 接听人姓名 | string | 接听人姓名，如：张三 |
| 8 | ackperson | 接听人代码 | string | 接听人代码，如：2333 |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 含义 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- | --- |
| 节点名称：messagelist   类型：array<object>  Type=FAILT的时候才返回 | 节点名称：messagelist   类型：array<object>  Type=FAILT的时候才返回 | 节点名称：messagelist   类型：array<object>  Type=FAILT的时候才返回 | 节点名称：messagelist   类型：array<object>  Type=FAILT的时候才返回 | 节点名称：messagelist   类型：array<object>  Type=FAILT的时候才返回 | 节点名称：messagelist   类型：array<object>  Type=FAILT的时候才返回 |
| 1 | MESSAGE | MESSAGE | 提示信息 | string | 报告没有检测项目 |
| 2 | TYPE | TYPE | 消息类型 | string | ERROR-错误
INFO-提示信息
WARN-警告信息
CONFIRM-确认信息
VERIFY-验证信息 |
| 节点名称：ResposeMessage.message 消息汇总信息 | 节点名称：ResposeMessage.message 消息汇总信息 | 节点名称：ResposeMessage.message 消息汇总信息 | 节点名称：ResposeMessage.message 消息汇总信息 | 节点名称：ResposeMessage.message 消息汇总信息 | 节点名称：ResposeMessage.message 消息汇总信息 |



代码实现：

点击召回按钮，处理召回业务，将危急值状态修改为：未发布 

相关表结构：LAB_PANICREPTRACE

##### B05 获取危急值汇总信息

接口说明：业务初始化加载，根据报告单号，获取当前报告的危急值流程和汇总信息

请求URL：/panicvalue/request/ getpanicflowinfo

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | applyno | 报告单号 | string | 报告单号，如：469148 |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 含义 | 含义 | 类型 | 类型 | 备注说明 | 备注说明 |
| --- | --- | --- | --- | --- | --- | --- | --- |
| 危急值处理流程节点列表：panicflowlist类型：array<object> | 危急值处理流程节点列表：panicflowlist类型：array<object> | 危急值处理流程节点列表：panicflowlist类型：array<object> | 危急值处理流程节点列表：panicflowlist类型：array<object> | 危急值处理流程节点列表：panicflowlist类型：array<object> | 危急值处理流程节点列表：panicflowlist类型：array<object> | 危急值处理流程节点列表：panicflowlist类型：array<object> | 危急值处理流程节点列表：panicflowlist类型：array<object> |
| 1 | nodeCode | nodeCode | 节点代码 | 节点代码 | string | string | 前端用不到 |
| 2 | nodeName | nodeName | 节点名称 | 节点名称 | string | string | 用作展示 |
| 3 | nodeStatus | nodeStatus | 节点状态 | 节点状态 | bool | bool | 表示节点是否激活：true-已有，false-未有 |
| 4 | operateCode | operateCode | 操作员代码 | 操作员代码 | string | string |  |
| 5 | operateName | operateName | 操作员名称 | 操作员名称 | string | string | 用作展示 |
| 6 | operateTime | operateTime | 操作时间 | 操作时间 | string | string | 格式：yyyy-MM-dd hh:mm:ss |
| 危急值汇总节点名称：panicsummarylist 类型：array<object> | 危急值汇总节点名称：panicsummarylist 类型：array<object> | 危急值汇总节点名称：panicsummarylist 类型：array<object> | 危急值汇总节点名称：panicsummarylist 类型：array<object> | 危急值汇总节点名称：panicsummarylist 类型：array<object> | 危急值汇总节点名称：panicsummarylist 类型：array<object> | 危急值汇总节点名称：panicsummarylist 类型：array<object> | 危急值汇总节点名称：panicsummarylist 类型：array<object> |
| 1 | ITEMNAME | ITEMNAME | 项目名称 | 项目名称 | string | string | 项目名称，如：白细胞计数 |
| 2 | ITEMCODE | ITEMCODE | 项目代码 | 项目代码 | string | string | 项目代码，如：%Eos |
| 3 | TIMES | TIMES | 出现次数 | 出现次数 | string | string | 出现次数，如：3 |











##### B06 危急值项目明细

接口说明：点击汇总数据，获取该项目的历史信息明细

请求URL：/panicvalue/request/getpanicitems

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | applyno | 报告单号 | string | 报告单号，如：469148 |
| 2 | itemcode | 项目代码 | string | 项目代码，如：%Eos |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 含义 | 含义 | 类型 | 类型 | 备注说明 | 备注说明 |
| --- | --- | --- | --- | --- | --- | --- | --- |
| 检测项目节点名称：itemlist类型：array<object> | 检测项目节点名称：itemlist类型：array<object> | 检测项目节点名称：itemlist类型：array<object> | 检测项目节点名称：itemlist类型：array<object> | 检测项目节点名称：itemlist类型：array<object> | 检测项目节点名称：itemlist类型：array<object> | 检测项目节点名称：itemlist类型：array<object> | 检测项目节点名称：itemlist类型：array<object> |
| 1 | APPLYNO | APPLYNO | 报告单号 | 报告单号 | string | string | 报告单号，如：235422 |
| 2 | EXECTIME | EXECTIME | 检测日期 | 检测日期 | string | string | 是否有危急值，如：true |
| 3 | ITEMCODE | ITEMCODE | 检验项目代码 | 检验项目代码 | string | string | 检验项目代码，如：CREA |
| 4 | ITEMNAME | ITEMNAME | 检验项目名称 | 检验项目名称 | string | string | 检验项目名称，如：肌酐 |
| 5 | INSTID | INSTID | 仪器ID | 仪器ID | string | string | 仪器ID，如：10010 |
| 6 | INSTCODE | INSTCODE | 仪器代码 | 仪器代码 | string | string | 仪器代码，如：AU5800 |
| 7 | INSTNAME | INSTNAME | 仪器名称 | 仪器名称 | string | string | 仪器名称，如：AU5800 |
| 8 | RESULT | RESULT | 检测结果 | 检测结果 | string | string | 检测结果，如：5089.77 |
| 9 | TECHNO | TECHNO | 样本号 | 样本号 | string | string | 样本号，如：23 |
| 10 | STATUS | STATUS | 危急值状态 | 危急值状态 | string | string | 危急值状态，0-否 1-是 |







JSON返回示例：

{

  "data": {

    "itemlist": [

      {

        "APPLYNO": 6337450,

        "ITEMCODE": "CREA",

        "ITEMNAME": "肌酐",

        "RESULT": "1253",

        "INSTCODE": "AU2700",

        "INSTID": 15,

        "INSTNAME": "AU2700                                  ",

        "TECHNO": 34,

        "EXECTIME": "2018-03-15 00:00:00",

        "STATUS": "未发布"

      },

      {

        "APPLYNO": 6337476,

        "ITEMCODE": "CREA",

        "ITEMNAME": "肌酐",

        "RESULT": "1594",

        "INSTCODE": "dubang",

        "INSTID": 7,

        "INSTNAME": "dubang                                  ",

        "TECHNO": 29,

        "EXECTIME": "2018-03-15 00:00:00",

        "STATUS": "未发布"

      }

    ]

  },

### 报告复做

#### V 模块接口视图

| NO. | 接口编号 | 接口名称 | 说明 |
| --- | --- | --- | --- |
| 1 | A01 | 初始化数据
../reportredo/request/getinitdata | 报告复做模块初始化数据 |
| 2 | B01 | 通过申请单号获取病人信息
../reportredo/request/getorderinfo | 根据申请单号获取病人的详细信息 |
| 3 | B02 | 报告结果信息查询
../reportredo/request/getreportresultlist | 根据申请单号获取病人报告的所有结果信息 |
| 4 | B03 | 标记报告结果项目
../reportredo/request/markreportitem | 对病人报告结果中的项目添加复做标识 |
| 5 | B10 | 获取报告项目的所有复做结果
../reportredo/request/getredoresult | 汇总查看——获取报告检测项目所有的复做结果信息 |
| 6 | B11 | 更新当前报告项目结果细信息
../reportredo/request/modresultdata | 汇总查看——将覆盖结果信息作为当前报告项目的结果信息 |
| 7 | B20 | 手动更新当前报告项目结果信息
../reportredo/request/manualmodresultdata | 手工录入——将用户手动输入的结果信息作为当前报告项目的结果 |
| 8 | B30 | 获取当前报告的结果信息和源样本报告结果信息
../reportredo/request/getsourcedatalist | 结果导入——获取当前报告结果信息和源样本报告结果信息 |
| 9 | B31 | 批量导入报告复做结果
../reportredo/request/batchimportresultdata | 结果导入——用户批量将另一份报告的相同项目信息更新到当前报告 |



#### 初始化数据

接口说明：报告复做模块初始化数据

请求URL：../reportredo/request/getinitdata

代码文件：winning.lis.reportredo.service.ReportRedoService

示例URL：http://192.168.11.211:10002/lis/reportredo/request/getinitdata

原型参考：



接口入参：

接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- | --- |
| 节点名称：标本说明 集合sampledesclist类型：array<object> | 节点名称：标本说明 集合sampledesclist类型：array<object> | 节点名称：标本说明 集合sampledesclist类型：array<object> | 节点名称：标本说明 集合sampledesclist类型：array<object> | 节点名称：标本说明 集合sampledesclist类型：array<object> | 节点名称：标本说明 集合sampledesclist类型：array<object> |
| 1 | 1 | CODE | 字典代码 | string | 字典代码，如：1 |
| 2 | 2 | NAME | 字典名称 | string | 字典名称，如：标本溶血 |
| 3 | 3 | DICID | 字典ID | string | 字典ID，如：1 |
| 4 | 4 | DICTYPE | 字典类型 | string | 字典类型，如：标本溶血 |
| 5 | 5 | EXTERNCODE | 外部码 | string | 外部码，如： |
| 6 | 6 | MEMCODE1 | 输入码一 | string | 输入码一 |
| 7 | 7 | MEMCODE2 | 输入码二 | string | 输入码二 |
| 8 | 8 | SUBSYSCODE | 系统代码 | string | 系统代码，LIS默认为LIMS |
| 9 | 9 | ICONCLASS | 图标类型 | string | 可为空 |
| 10 | 10 | ORDERNO | 序号 | number | 排序序号，根据此字段来显示展示顺序 |
| 节点名称：复做原因 集合redoreasonlist类型：array<object> | 节点名称：复做原因 集合redoreasonlist类型：array<object> | 节点名称：复做原因 集合redoreasonlist类型：array<object> | 节点名称：复做原因 集合redoreasonlist类型：array<object> | 节点名称：复做原因 集合redoreasonlist类型：array<object> | 节点名称：复做原因 集合redoreasonlist类型：array<object> |
| 1 | 1 | CODE | 字典代码 | string | 字典代码，如：1 |
| 2 | 2 | NAME | 字典名称 | string | 字典名称，如： |
| 3 | 3 | DICID | 字典ID | string | 字典ID，如：1 |
| 4 | 4 | DICTYPE | 字典类型 | string | 字典类型 |
| 5 | 5 | EXTERNCODE | 外部码 | string | 外部码 |
| 6 | 6 | MEMCODE1 | 输入码一 | string | 输入码一 |
| 7 | 7 | MEMCODE2 | 输入码二 | string | 输入码二 |
| 8 | 8 | SUBSYSCODE | 系统代码 | string | 系统代码，LIS默认为LIMS |
| 9 | 9 | ICONCLASS | 图标类型 | string | 可为空 |
| 10 | 10 | ORDERNO | 序号 | number | 排序序号，根据此字段来显示展示顺序 |
| 节点名称：初始化仪器集合instlist  类型：array<object> | 节点名称：初始化仪器集合instlist  类型：array<object> | 节点名称：初始化仪器集合instlist  类型：array<object> | 节点名称：初始化仪器集合instlist  类型：array<object> | 节点名称：初始化仪器集合instlist  类型：array<object> | 节点名称：初始化仪器集合instlist  类型：array<object> |
| 1 | 1 | CODE | 字典代码 | string | 字典代码，如：KX-21 |
| 2 | 2 | NAME | 字典名称 | string | 字典名称，如：KX-21 |
| 3 | 3 | DICID | 字典ID | string | 字典ID，如：1001 |
| 4 | 4 | DICTYPE | 字典类型 | string | 字典类型，如：生化 |
| 5 | 5 | EXTERNCODE | 外部码 | string | 外部码，如：KX-21 |
| 6 | 6 | MEMCODE1 | 输入码一 | string | 输入码一 |
| 7 | 7 | MEMCODE2 | 输入码二 | string | 输入码二 |
| 8 | 8 | SUBSYSCODE | 系统代码 | string | 系统代码，LIS默认为LIMS |
| 9 | 9 | ICONCLASS | 图标类型 | string | 可为空 |
| 10 | 10 | ORDERNO | 序号 | number | 排序序号，根据此字段来显示展示顺序 |
| 节点名称：标本来源 集合samplesourcelist 类型：array<object> | 节点名称：标本来源 集合samplesourcelist 类型：array<object> | 节点名称：标本来源 集合samplesourcelist 类型：array<object> | 节点名称：标本来源 集合samplesourcelist 类型：array<object> | 节点名称：标本来源 集合samplesourcelist 类型：array<object> | 节点名称：标本来源 集合samplesourcelist 类型：array<object> |
| 1 | 1 | CODE | 字典代码 | string | 0:当前标本  1：重新采样 |
| 2 | 2 | NAME | 字典名称 | string | 字典名称，如：KX-21 |





JSON返回示例：

"controlsparams":{

"OP_DELREPORT":TRUE,

"OP_DELRESULT": TRUE,

"OP_DELMARKITEM": TRUE,

},

"sampledesc":[

{

    "CODE":"1",

    "NAME":"标本溶血",

    "DICID":"1",

    "DICTYPE":"标本溶血",

    "EXTERNCODE":"",

    "MEMCODE1":"10010",

    "MEMCODE2":"",

    "SUBSYSCODE":"LIMS",

    "ORDERNO":"0"

},

{

    "CODE":"2",

    "NAME":"标本脂血",

    "DICID":"2",

    "DICTYPE":"标本脂血",

    "EXTERNCODE":"",

    "MEMCODE1":"",

    "MEMCODE2":"",

    "SUBSYSCODE":"LIMS",

    "ORDERNO":"0"

}]，

"redoreason":[

{

    "CODE":"1",

    "NAME":"结果与历史结果差异太大,

    "DICID":"1",

    "DICTYPE":"结果与历史结果差异太大",

    "EXTERNCODE":"",

    "MEMCODE1":"",

    "MEMCODE2":"",

    "SUBSYSCODE":"LIMS",

    "ORDERNO":"0"

},

{

    "CODE":"2",

    "NAME":"结果与历史结果差异太大",

    "DICID":"2",

    "DICTYPE":"结果与历史结果差异太大",

    "EXTERNCODE":"",

    "MEMCODE1":"",

    "MEMCODE2":"",

    "SUBSYSCODE":"LIMS",

    "ORDERNO":"0"

}]，

"instlist":[

{

    "CODE":"10010",

    "NAME":"AU5800",

    "DICID":"10010",

    "DICTYPE":"生化",

    "EXTERNCODE":"10010",

    "MEMCODE1":"10010",

    "MEMCODE2":"10010",

    "SUBSYSCODE":"LIMS",

    "ORDERNO":"1"

},

{

    "CODE":"10010",

    "NAME":"AU5800",

    "DICID":"10010",

    "DICTYPE":"生化",

    "EXTERNCODE":"10010",

    "MEMCODE1":"10010",

    "MEMCODE2":"10010",

    "SUBSYSCODE":"LIMS",

    "ORDERNO":"1"

}]





代码实现：

调用时机：用户进入报告复做模块，页面初始化时调用

表结构：SYS_DEPT\SETTINGS\SETTINGSDIC

代码实现：

分别从接口表中获取对应的初始化参数信息



#### B 业务类

##### B01	通过申请单号获取病人信息

接口说明：根据申请单号获取病人的详细信息

请求URL：../reportredo/request/getorderinfo

代码文件：winning.lis.reportredo.service.ReportRedoService

示例URL：http://192.168.11.211:10002/lis/reportredo/request/getorderinfo?applyno=7365907



原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | applyno | 申请单号 | string | 如：39545620 |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：orderinfo  报告列表  类型：object | 节点名称：orderinfo  报告列表  类型：object | 节点名称：orderinfo  报告列表  类型：object | 节点名称：orderinfo  报告列表  类型：object | 节点名称：orderinfo  报告列表  类型：object |
| 1 | PATNAME | 病人名称 | string | 如：张三 |
| 2 | SEX | 性别 | string | 0 |
| 3 | SERDESC | 性别描述 | string | 男 |
| 4 | AGEDESC | 年龄 | string | 34岁 |
| 5 | ORGAPPLYNO | 条形码 | string | 32432423434P |
| 6 | SAMPLECODE | 标本种类 | string | 1 |
| 7 | SAMPLENAME | 标本类型名称 | string | 血 |
| 8 | APPLYDEPTCODE | 科室 | string | 1002 |
| 9 | APPLYDEPTNAME | 报告科室名称 | string | 消化科 |
| 10 | WARD | 病区 | string | 1001 |
| 11 | WARDNAME | 病区名称 | string | 消化一病区 |
| 12 | BEDNO | 床号 | string | 12床 |
| 13 | HISORDERCODE | 检测项目 | string | 肝功能四项 |
| 14 | CHARGEAMOUNT | 收费金额 | string | 499.00 |
| 15 | UNPANI | 临床诊断 | string | 消化不良 |
| 16 | REDOREASON | 复做原因 | string | 取结果与历史结果差异太大 |





JSON返回示例：

“orderinfo”:[

{

"PATNAME":"张三",

"SEX":"0",

"SERDESC":"男",

"AGEDESC":"34岁",

"TXM":"938492402P",

"SAMPLECODE":"0",

"SAMPLENAME":"血",

"APPLYDEPTCODE":"1004”,

"APPLYDEPTNAME":"消化科",

"WARD":"1001"

...

}]



调用时机：模块进行初始化后，通过报告管理模块传过来的applyno作为入参，调用该服务，查询出病人信息。

相关表结构：lis_list

代码实现：

根据查询条件从lis_list表中查询病人信息



##### B02	报告结果信息查询

接口说明：根据申请单号获取病人报告的所有结果信息

请求URL：../reportredo/request/getreportresultlist

代码文件：winning.lis.reportredo.service.ReportRedoService

示例URL：http://192.168.11.211:10002/lis/reportredo/request/getreportresultlist?applyno=7365907

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | applyno | 申请单号 | string | 如：3499354 |





接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 含义 | 类型 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- | --- | --- |
| 1 | PANICCNT | 危急值项目数量 | 危急值项目数量 | int | int | 如：3 |
| 节点名称：reportresultlist  类型：array<object> | 节点名称：reportresultlist  类型：array<object> | 节点名称：reportresultlist  类型：array<object> | 节点名称：reportresultlist  类型：array<object> | 节点名称：reportresultlist  类型：array<object> | 节点名称：reportresultlist  类型：array<object> | 节点名称：reportresultlist  类型：array<object> |
| 1 | REDOFLAG | REDOFLAG | 复做标志 | string | 0：否   1：是 | 0：否   1：是 |
| 2 | PANICFLAG | PANICFLAG | 危急值标识 | string | 0-正常 1-危急值报告 | 0-正常 1-危急值报告 |
| 3 | RESULTCHAR | RESULTCHAR | 字符结果 | string | 如：37.60 | 如：37.60 |
| 3 | ITEMCODE | ITEMCODE | 项目代码 | string | 项目代码   如：APTT | 项目代码   如：APTT |
| 4 | ITEMNAME | ITEMNAME | 项目名称 | string | 项目名称  如：活化部分凝血活酶时间 | 项目名称  如：活化部分凝血活酶时间 |
| 5 | RESULT | RESULT | 报告结果 | string | 报告结果   如：37.60 | 报告结果   如：37.60 |
| 6 | REFERENCERANGE | REFERENCERANGE | 参考区间 | string | 如：22.00～37.00 | 如：22.00～37.00 |
| 7 | HINTINFO | HINTINFO | 提示 | string |  |  |
| 8 | UNIT | UNIT | 单位 | string | 参考区间单位   如：秒 | 参考区间单位   如：秒 |
| 9 | HISRESULT1 | HISRESULT1 | 历史结果1 | string | 历史报告结果1(相同检验项目的历史记录) | 历史报告结果1(相同检验项目的历史记录) |
| 10 | HISRESULT2 | HISRESULT2 | 历史结果2 | string | 历史报告结果2(相同检验项目的历史记录) | 历史报告结果2(相同检验项目的历史记录) |
| 11 | HISRESULT3 | HISRESULT3 | 历史结果3 | string | 历史报告结果3(相同检验项目的历史记录) | 历史报告结果3(相同检验项目的历史记录) |
| 12 | HISRESULT4 | HISRESULT4 | 历史结果4 | string | 历史报告结果4(相同检验项目的历史记录) | 历史报告结果4(相同检验项目的历史记录) |
| 13 | HISRESULT5 | HISRESULT5 | 历史结果5 | string | 历史报告结果5(相同检验项目的历史记录) | 历史报告结果5(相同检验项目的历史记录) |
| 14 | INSTID | INSTID | 仪器ID | string | 仪器ID  如：10010 | 仪器ID  如：10010 |
| 15 | INSTNAME | INSTNAME | 仪器名称 | string | 仪器名称  如：AU5800 | 仪器名称  如：AU5800 |
| 16 | HIGHLOWFLAG | HIGHLOWFLAG | 高低标志 | string | P-阳性或异常值 H-偏高 L-偏低 HH-显著偏高 LL-显著偏低 | P-阳性或异常值 H-偏高 L-偏低 HH-显著偏高 LL-显著偏低 |
| 17 | REDO | REDO | 复做标志 | Int, | 复做标记 0-正常 1-复做 | 复做标记 0-正常 1-复做 |
| 18 | ODRESULT | ODRESULT | OD值 | string | OD值 | OD值 |
| 19 | ODRESULTCHAR | ODRESULTCHAR | OD值字符表示 | string | OD值字符表示 | OD值字符表示 |
| 20 | CUTOFFVALUE | CUTOFFVALUE | CUTOFF比值 | string | CUTOFF比值 | CUTOFF比值 |
| 21 | CUTOFFVALUECHAR | CUTOFFVALUECHAR | CUTOFF比值字符表示 | string | CUTOFF比值字符表示 | CUTOFF比值字符表示 |
| 22 | SCOVALUE | SCOVALUE | SCO比值 | string | SCO比值 | SCO比值 |
| 23 | SCOVALUECHAR | SCOVALUECHAR | SCO比值字符表示 | string | SCO比值字符表示 | SCO比值字符表示 |





JSON格式

“reportresultlist”:[

{

‘REDOFLAG’:’0’,

‘PANICFLAG’:’0’,

'ITEMCODE':'APTT',

'ITEMNAME':'活化部分凝血活酶时间',

'RESULT':'23.00',

'REFERENCERANGE':'22.00～37.00',

‘HINTINFO’:’’,

'UNIT':'秒',

HISRESULT1:'24.00',

HISRESULT2:'22.00',

HISRESULT3:'30.00',

HISRESULT4:'26.00',

HISRESULT5:'34.00',

'INSTID':'10010',

'INSTNAME':'AU5800',

...

},

{

‘REDOFLAG’:’0’,

‘PANICFLAG’:’0’,

'ITEMCODE':'APTT',

'ITEMNAME':'活化部分凝血活酶时间',

'RESULT':'23.00',

'REFERENCERANGE':'22.00～37.00',

‘HINTINFO’:’’,

'UNIT':'秒',

HISRESULT1:'24.00',

HISRESULT2:'22.00',

HISRESULT3:'30.00',

HISRESULT4:'26.00',

HISRESULT5:'34.00',

'INSTID':'10010',

'INSTNAME':'AU5800',

...

}]

代码实现:

相关表结构：LIS_result\Lis_list\lis_resultMod

调用时机：报告复做页面初始化服务调用后，该用该服务，查询该份报告的所有检测结果，展示在前台页面；



##### B03	标记报告结果项目

接口说明：对病人报告结果中的项目添加复做标识

请求URL：../reportredo/request/markreportitem

代码文件：winning.lis.reportredo.service.ReportRedoService

示例URL：http://192.168.11.211:10002/lis/reportredo/request/markreportitem?applyno=7365907&samplesourcecode=0&sampledesc=''&redoreason=结果与历史结果差异太大&itemlist=[{'itemcode':'%Bas'}]

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | applyno | 申请单号 | string | 申请单号 如：10010 （必传） |
| 2 | samplesourcecode | 标本来源 | string | 0:当前标本   1:重新采样 |
| 3 | sampledesc | 标本说明 | string | 如：稀释5倍（文字说明） |
| 4 | redoreason | 复做原因 | string | 如：结果与历史结果差异太大（文字说明） |
| 节点名称：itemlist  项目信息  类型：array<object> | 节点名称：itemlist  项目信息  类型：array<object> | 节点名称：itemlist  项目信息  类型：array<object> | 节点名称：itemlist  项目信息  类型：array<object> | 节点名称：itemlist  项目信息  类型：array<object> |
| 1 | itemcode | 项目代码 | string | 项目代码  如：FIB |
| 2 | itemname | 项目名称 | string | 项目名称  如：纤维蛋白原 |





接口出参【ResposeMessage.data->array<object>】：

JSON格式

代码实现：

代码逻辑：

根据前台传入的额参数，获取报告在lis_list\lis_result表中是否存在数据

向lis_resultmod\lis_reportlog表中插入复做记录日志	

修改lis_list\lis_result表中数据的复做标志和复做原因字段

表结构：lis_list\lis_result\lis_resultmod\lis_reportlog

调用时机：用户选中列表中需要复做的项目信息，点击【确定】按钮，调用该服务；



##### B10	获取报告项目的所有复做结果

接口说明：汇总查看——获取报告检测项目所有的复做结果信息

请求URL：../reportredo/request/getredoresult

代码文件：winning.lis.reportredo.service.ReportRedoservice

示例URL：http://192.168.11.211:10002/lis/reportredo/request/getredoresult?applyno=6443362

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | applyno | 申请单号 | string | 如：230203 |





接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：resultdatalist  类型：array<object> | 节点名称：resultdatalist  类型：array<object> | 节点名称：resultdatalist  类型：array<object> | 节点名称：resultdatalist  类型：array<object> | 节点名称：resultdatalist  类型：array<object> |
| 1 | ITEMCODE | 项目代码 | string | 如：AMB |
| 2 | ITEMNAME | 项目名称 | string | 如：阿米巴 |
| 3 | RESULT | 报告结果 | string | 21 |
| 4 | REFERENCERANGE | 参考区间 | string | 如：22.00～37.00 |
| 5 | UNIT | 单位 | string | 参考区间单位   如：秒 |
| 6 | HINTINFO | 提示 | string |  |
| 7 | 节点名称 redoresultlist  类型：array<object> | 节点名称 redoresultlist  类型：array<object> | 节点名称 redoresultlist  类型：array<object> | 节点名称 redoresultlist  类型：array<object> |
|  | REDORESULT | 复做结果 | string | 如：12 |
|  | INSTID | 仪器ID | string | 如：10010 |
|  | INSTNAME | 仪器名称 | string | 如：AU5800 |
|  | RESULTTIME | 结果时间 | string | 如：2017-05-09 10:19:33 |
|  | MODNAME | 复查人 | string | 如：张三 |
|  | MODCODE | 复查人代码 | string | 如：1001 |



JSON格式

“iteminfolist”:[

{

"ITEMCODE":"AMB",

“ITEMNAME:”阿米巴”,

“RESULT”:”12”,

“REFERENCERANGE”:10~13,

“redolist”:[{

“REDORESULT”:”12”,

“INSTID”:”10010”,

“INSTNAME”:”AU5800”,

“RESULTTIME”:”2017-05-09 10:19:33”,

“MODNAME”:”张三”,

“MODCODE”:”1001”

},

{

“REDORESULT”:”11”,

“INSTID”:”10010”,

“INSTNAME”:”AU5800”,

“RESULTTIME”:”2017-05-12 10:19:33”,

“MODNAME”:”李四”,

“MODCODE”:”1001”

},

...]

},

{

"ITEMCODE":"AMB",

“ITEMNAME:”阿米巴”,

“redolist”:[{

“REDORESULT”:”12”,

“INSTID”:”10010”,

“INSTNAME”:”AU5800”,

“RESULTTIME”:”2017-05-09 10:19:33”,

“MODNAME”:”张三”,

“MODCODE”:”1001”

},

{

“REDORESULT”:”11”,

“INSTID”:”10010”,

“INSTNAME”:”AU5800”,

“RESULTTIME”:”2017-05-12 10:19:33”,

“MODNAME”:”李四”,

“MODCODE”:”1001”

},

...]

}]

代码实现：

代码逻辑：

表结构：lis_result\lis_resultmod

调用时机：用户进入报告复做——汇总查看页面，根据上一步骤选中传过来的需要复做的项目和申请单号，调用该服务；

##### B20	手动更新当前报告项目结果信息

接口说明：手工录入——将用户手动输入的结果信息作为当前报告项目的结果

请求URL：../reportredo/request/manualmodresultdata

代码文件：winning.lis.reportredo.service.ReportRedoervice

示例URL：http://192.168.11.211:15011/lis/reportredo/request/manualmodresultdata?applyno=&iscover=1&iteminfolist=[]

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | applyno | 申请单号 | string | 如：28818923 |
| 2 | itemcode | 项目代码 | string | 如：AMB |
| 3 | resultvalue | 更新后结果 | string | 如：90 |
| 4 | resultchar | 原结果 | string | 如：99 |
| 5 | iscover | 是否覆盖原结果 | string | 0：不覆盖   1：覆盖 |





代码实现：

代码逻辑：

将当前报告结果数据插入到lis_resultmod表

将复做结果更新当前当前结果

表结构：lis_result\lis_resultmod\lis_list

调用时机：用户操作手工录入页面的报告列表，调用该服务，将复做结果更新当前当前结果



##### B30	获取当前报告的结果信息和源样本报告结果信息

接口说明：结果导入——获取当前报告结果信息和源样本报告结果信息

请求URL：../reportredo/request/getsourcedatalist

代码文件：winning.lis.reportredo.service.ReportRedoervice

示例URL：http://192.168.11.211:15011/lis/reportredo/request/getsourcedatalist?applyno=&instid=&techno=&exectime=

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | applyno | 申请单号 | string | 如：3031232 |
| 2 | instid | 仪器ID | string | 如：10010 |
| 3 | exectime | 检测时间 | string | 如：208-01-01 |
| 4 | techno | 样本号 | string | 如：21 |





接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：报告结果集合    resultlist  类型：array<object> | 节点名称：报告结果集合    resultlist  类型：array<object> | 节点名称：报告结果集合    resultlist  类型：array<object> | 节点名称：报告结果集合    resultlist  类型：array<object> | 节点名称：报告结果集合    resultlist  类型：array<object> |
| 1 | ITEMCODE | 项目代码 | string | 如：AMB   待修改项目的项目代码 |
| 2 | ITEMNAME | 项目名称 | string | 如：阿米巴  待修改项目的项目名称 |
| 3 | INSTID | 仪器ID | string | 10   待修改项目的仪器ID |
| 4 | INSTNAME | 仪器名称 | string | AU2700 |
| 5 | RESULT | 当前结果 | string | 12 |
| 6 | REFERENCERANGE | 参考值范围 | string | 13 |
| 7 | UNIT | 参考值单位 | string | 2018-09-09 09:09:00 |
| 8 | HINTINFO | 提示 | string | 10010 |
| 9 | EXECTIME | 检测时间 | string | AU5800 |
| 10 | TECHNO | 样本号 | string | 如：2 |
| 11 | SAMPLETYPE | 标本类型 | string |  |
| 12 | SOURCERESULT | 源样本结果 | string | 如：12.0 |
| 13 | SOURCETIME | 源样本时间 | string | 2018-09-09 09:09:00 |
| 14 | SOURCEINSTID | 源样本仪器 | string | 15 |
| 15 | SOURCEINSTNAME | 源样本仪器名称 | string | AU2700 |





JSON格式：

“resultlist”:[{

“ITEMCODE”:”AMB”,

“ITEMNAME”:”阿米巴”,

“CURRESULT”:”12”,

“SOURCERESULT”:”14”,

“SOURCETIME”:”2018-09-09 09:09:00”,

“INSTID”:”10010”,

“INSTNAME”:”AU5800”,

...

},

{

“ITEMCODE”:”AMB”,

“ITEMNAME”:”阿米巴”,

“CURRESULT”:”12”,

“SOURCERESULT”:”14”,

“SOURCETIME”:”2018-09-09 09:09:00”,

“INSTID”:”10010”,

“INSTNAME”:”AU5800”,

...

}]

代码实现：

代码逻辑：

1、根据查询条件在lis_result表中查询特定项目的报告结果

表结构：lis_result\lis_list

调用时机：用户点击结果导入页面的查询按钮，调用该服务



##### B31	批量导入报告复做结果

接口说明：结果导入——用户批量将另一份报告的相同项目信息更新到当前报告

请求URL：../reportredo/request/batchimportresultdata

代码文件：winning.lis.reportredo.service.ReportRedoervice

示例URL：http://192.168.11.211:15011/lis/reportredo/request/batchimportresultdata?applyno=&iscover=1&iteminfolist=[]

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：报告结果集合    resultlist  类型：array<object>  参考105.B30接口出参 | 节点名称：报告结果集合    resultlist  类型：array<object>  参考105.B30接口出参 | 节点名称：报告结果集合    resultlist  类型：array<object>  参考105.B30接口出参 | 节点名称：报告结果集合    resultlist  类型：array<object>  参考105.B30接口出参 | 节点名称：报告结果集合    resultlist  类型：array<object>  参考105.B30接口出参 |





接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：报告结果集合    resultlist  类型：array<object> | 节点名称：报告结果集合    resultlist  类型：array<object> | 节点名称：报告结果集合    resultlist  类型：array<object> | 节点名称：报告结果集合    resultlist  类型：array<object> | 节点名称：报告结果集合    resultlist  类型：array<object> |





JSON格式

“resultlist”:[{

“ITEMCODE”:”AMB”,

“ITEMNAME”:”阿米巴”,

“CURRESULT”:”12”,

“SOURCERESULT”:”14”,

“SOURCETIME”:”2018-09-09 09:09:00”,

“INSTID”:”10010”,

“INSTNAME”:”AU5800”,

...

},

{

“ITEMCODE”:”AMB”,

“ITEMNAME”:”阿米巴”,

“CURRESULT”:”12”,

“SOURCERESULT”:”14”,

“SOURCETIME”:”2018-09-09 09:09:00”,

“INSTID”:”10010”,

“INSTNAME”:”AU5800”,

...

}]



代码实现：

代码逻辑：

1、将当前报告结果数据备份到lis_resultmod表中

2、使用源样本的报告结果覆盖当前报告结果

表结构：lis_result\lis_resultmod\lis_list

调用时机：用户调用结果导入页面的导入按钮，对于选中的报告作为入参调用该服务





### 报告详情

#### V 模块接口视图

| NO. | 接口编号 | 接口名称 | 说明 |
| --- | --- | --- | --- |
| 1 | B01 | 报告总览
../reportdetails/request/getreportglobal | 根据报告单号获取报告概况，包含收费项目信息，报告结果信息，流程信息，过程人员信息，TAT以及报告状态信息 |
| 2 | B02 | 报告信息
../reportdetails/request/getreportinfo | 根据报告单号查询报告信息 |
| 3 | B03 | 报告结果
../reportdetails/request/getreportresult |  |
| 4 | B04 | 原始结果
../reportdetails/request/getoriginalresult |  |
| 6 | B05 | 检测项目
../reportdetails/request/getitem |  |
| 7 | B06 | 修改记录
../reportdetails/request/get |  |
| 8 | B07 | 复查结果
../reportdetails/request/get |  |
| 9 | B08 | 危急项目
../reportdetails/request/get |  |







#### B 业务类

##### B11	更新当前报告项目结果细信息

接口说明：汇总查看——将覆盖结果信息作为当前报告项目的结果信息

请求URL：../reportredo/request/modresultdata

代码文件：winning.lis.reportredo.service.ReportRedoervice

示例URL：http://192.168.11.211:15011/lis/reportredo/request/modresultdata?applyno=&itemcode=&orgresult=&result=

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | applyno | 申请单号 | string | 如：230203 |
| 2 | itemcode | 项目代码 | string | 如：AMB |
| 3 | orgresult | 原报告结果 | string | 如：10 |
| 4 | result | 待更新报告结果 | string | 如：11 |





JSON格式

代码实现：

代码逻辑：

将当前报告结果数据插入到lis_resultmod表

将复做结果更新当前当前结果(报告结果、检测仪器、结果时间)

表结构：lis_result\lis_resultmod\lis_list

调用时机：用户操作汇总查看页面的报告列表，调用该服务，将复做结果更新当前当前结果

##### B01	报告总览

接口说明：根据报告单号获取报告概况，包含收费项目信息，报告结果信息，流程信息，过程人员信息，TAT以及报告状态信息。

请求URL：../reportdetails/request/getreportglobal

代码文件：winning.lis.reportdetails.service.ReportDetailsService

示例URL：http://192.168.11.211:10002/lis/reportredo/request/getreportglobal?applyno=6545434



原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | applyno | 报告单号 | string | 如：6545434 |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：reportinfo类型：object   从上往下 标题信息以及 最后一栏的状态相关信息 | 节点名称：reportinfo类型：object   从上往下 标题信息以及 最后一栏的状态相关信息 | 节点名称：reportinfo类型：object   从上往下 标题信息以及 最后一栏的状态相关信息 | 节点名称：reportinfo类型：object   从上往下 标题信息以及 最后一栏的状态相关信息 | 节点名称：reportinfo类型：object   从上往下 标题信息以及 最后一栏的状态相关信息 |
| 1 | APPLYNO | 报告单号 | string |  |
| 2 | PATNAME | 病人姓名 | string |  |
| 3 | SEXDESC | 性别描述 | string |  |
| 4 | AGEDESC | 年龄描述 | string |  |
| 5 | ORGAPPLYNO | 条形码 | string |  |
| 6 | SAMPLENAME | 样本种类 | string |  |
| 7 | APPLYDEPTNAME | 申请科室 | string |  |
| 8 | WARDNAME | 病区 | string |  |
| 9 | BEDNO | 床号 | string |  |
| 10 | STATUS | 报告状态 | string | 10-初始报告 20-部分指标检测完成 30-已完成检测 40-初审通过 50-已审核 60-发布 |
| 11 | STATUSDESC | 报告状态描述 | string | 已审核 |
| 13 | PRINTSTATUS | 打印状态 | string | 报告打印状态 0-未打印 1-已打印 |
| 14 | PRINTCOUNT | 打印情况 | string | 报告打印情况 7 |
| 15 | REDO | 复做状况 | string | 复做标志 0-未复做 1-复做 |
| 16 | PANICFLAG | 危机标记 | string | 危急值报告 0-正常 1-危急值报告 |
| 节点名称：hisorderinfo 类型：object 第一栏 检测项目相关信息 | 节点名称：hisorderinfo 类型：object 第一栏 检测项目相关信息 | 节点名称：hisorderinfo 类型：object 第一栏 检测项目相关信息 | 节点名称：hisorderinfo 类型：object 第一栏 检测项目相关信息 | 节点名称：hisorderinfo 类型：object 第一栏 检测项目相关信息 |
| 17 | CHARGEAMOUNT | 收费金额 | string | 499.00 |
| 18 | HISORDERNAME | 收费项目 | string | 收费项目，肝功能三项，肾功能三项 |
|  |  |  |  |  |
|  |  |  |  |  |
| 节点名称： resultinfo 类型：object  结果项 从上数 第二栏 | 节点名称： resultinfo 类型：object  结果项 从上数 第二栏 | 节点名称： resultinfo 类型：object  结果项 从上数 第二栏 | 节点名称： resultinfo 类型：object  结果项 从上数 第二栏 | 节点名称： resultinfo 类型：object  结果项 从上数 第二栏 |
| 14 | RESULTCNT | 结果数量 | string |  |
| 15 | RESULTUPDCNT | 结果修改项 | string |  |
| 16 | PANNICCNT | 危机项 | string |  |
| 17 | REDOCNT | 复做项目 | string |  |
| 18 | RESULTABNCNT | 异常结果项 | string |  |
| 节点名称：docinfo类型：object   从上往下 上方第三栏 | 节点名称：docinfo类型：object   从上往下 上方第三栏 | 节点名称：docinfo类型：object   从上往下 上方第三栏 | 节点名称：docinfo类型：object   从上往下 上方第三栏 | 节点名称：docinfo类型：object   从上往下 上方第三栏 |
| 1 | APPLYDOCNAME | 申请医生 | string | 王建峰(1035) 申请医生与申请医生代码组合 lis_ordermaster表 |
| 3 | DRAWUSERNAME | 采样护士名称 | string | 张晓 |
| 5 | EXECDOCNAME | 检验医生名字 | string | 赵茜茜 lis_list表 |
| 7 | VERIFIERNAME | 审核医生 | string | 李元吉 |
|  |  |  |  |  |
| 节点名称： flowinfo 类型：object  样本报告流转信息  第四栏 | 节点名称： flowinfo 类型：object  样本报告流转信息  第四栏 | 节点名称： flowinfo 类型：object  样本报告流转信息  第四栏 | 节点名称： flowinfo 类型：object  样本报告流转信息  第四栏 | 节点名称： flowinfo 类型：object  样本报告流转信息  第四栏 |
| 1 | SQCYTIME | 申请采样时间 | string |  |
| 2 | CYQSTIME | 采样签收时间 | string |  |
| 3 | QSRKTIME | 签收入库时间 | string |  |
| 4 | RKSHTIME | 入库审核时间 | string |  |
| 5 | SHFBTIME | 审核发布时间 | string |  |
|  |  |  |  |  |
| 节点名称： tatinfo类型：array<object>最后一栏信息 | 节点名称： tatinfo类型：array<object>最后一栏信息 | 节点名称： tatinfo类型：array<object>最后一栏信息 | 节点名称： tatinfo类型：array<object>最后一栏信息 | 节点名称： tatinfo类型：array<object>最后一栏信息 |
|  | TATINFO | Tat分析信息 | string | 一组，顺序已经排列 按照 数组 0，1，2顺序 |
|  |  |  |  |  |
|  |  |  |  |  |





JSON返回示例：

{

    "reportinfo": {

      "APPLYNO": 6545434,

      "PATNAME": "吴智国",

      "SEXDESC": "男",

      "AGEDESC": "47岁    ",

      "ORGAPPLYNO": "132787838P",

      "SAMPLENAME": "血",

      "APPLYTIME": "2017-07-03 07:34:57",

      "SAMPLETIME": "2017-07-03 07:42:16",

      "RECEIVETIME": "2017-07-03 09:18:43",

      "VERIFYDATE": "2017-07-03 10:19:57",

      "REPORTTIME": "2017-07-03 10:19:57",

      "STATUS": 10,

      "PRINTCOUNT": 0,

      "REDO": 0,

      "PANICFLAG": 0,

      "APPLYDOCNAME": "甘淑芳",

      "EXECDOCNAME": "潘倩",

      "VERIFIERNAME": "张新伟",

      "APPLYDOCCODE": "1196",

      "EXECDOCCODE": "5145",

      "VERIFIERCODE": "51020",

      "CHARGEAMOUNT": 64,

      "HISORDERNAME": "糖化血红蛋白测定",

      "APPLYDEPTNAME": "风湿内分泌科",

      "WARDNAME": null,

      "BEDNO": ""

    },

    "tatinfo": [],

    "docinfo": {

      "VERIFIERNAME": "张新伟",

      "APPLYDOCNAME": "甘淑芳",

      "EXECDOCCODE": "5145",

      "APPLYDOCCODE": "1196",

      "DRAWUSERNAME": "",

      "VERIFIERCODE": "51020",

      "EXECDOCNAME": "潘倩",

      "DRAWUSERCODE": ""

    },

    "hisorderinfo ": {

      "HISORDERNAME": "糖化血红蛋白测定",

      "CHARGEAMOUNT": "64.00"

    },

    "flowinfo": {

      "RKSHTIME": "1小时1分钟",

      "SQCYTIME": "0小时7分钟",

      "QSRKTIME": "0小时0分钟",

      "SHFBTIME": "0小时0分钟",

      "CYQSTIME": "1小时36分钟"

    },

    "resultinfo  ": {

      "REDOCNT": 0,

      "RESULTABNCNT": 0,

      "ITEMCNT": 1,

      "PANNICCNT": 0,

      "RESULTUPDCNT": 0

    }

  }



调用时机：模块进行初始化后，通过报告管理模块传过来的applyno作为入参，调用该服务，查询出病人信息。

相关表结构： lis_list lis_tatrecord, lis_acceptitems，lis_result,lis_resultmod

代码实现：



##### B02	报告信息

接口说明：根据报告单号获取报告基本信息

请求URL：../reportdetails/request/getreportinfo

代码文件：winning.lis.reportdetails.service.ReportDetailsService

示例URL：http://192.168.11.211:10002/lis/reportdetails/request/getreportinfo?applyno=7365907



原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | applyno | 报告单号 | string | 如：39545620 |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：baseinfo  类型：object | 节点名称：baseinfo  类型：object | 节点名称：baseinfo  类型：object | 节点名称：baseinfo  类型：object | 节点名称：baseinfo  类型：object |
| 1 | BRLX | 病人类型 | string | 如：HIS病人类型：0-门诊 1-住院 3、4-体检 |
|  | BRLXDESC | 病人类型描述 | string | 如：HIS病人类型：0-门诊 1-住院 3、4-体检 |
| 2 | PATNAME | 患者姓名 | string | 如：张三 |
| 3 | SEXDESC | 性别描述 | string | 男 |
| 4 | AGEDESC | 年龄描述 | string | 34岁 |
| 5 | CARDNO | 磁卡号 | string | 磁卡号 |
| 7 | HOSPNO | 病员号 | string |  |
| 8 | WARDNAME | 病区名称 | string | 消化一病区 |
| 9 | CHARGETYPENAME | 付费类别 | string |  |
|  |  |  |  |  |
| 10 | ORGAPPLYNO | 标本条形码 | string |  |
| 10 | SAMPLENAME | 标本种类 | string |  |
| 11 | CXSJ | 绑定时间 | string |  |
| 12 | SAMPLETIME | 采样时间 | string |  |
| 13 | RECEIVETIME | 签收时间 | string |  |
| 14 | RKSJ | 入库时间 | string |  |
|  |  |  |  |  |
| 15 | APPLYDEPTNAME | 申请科室 | string |  |
| 16 | APPLYDOCNAME | 申请医生 | string |  |
| 17 | APPLYTIME | 申请时间 | string |  |
| 18 | CLINICDESC | 临床诊断 | string |  |
| 19 | HISORDERNAME | 检验项目 | string |  |
| 20 | EXECDOCNAME | 检验医生 | string | 张倩 |
| 21 | EXECDOCCODE | 检验医生代码 | string | 3023 |
| 22 | VERIFIERNAME | 审核医生 | string |  |
| 23 | VERIFIERCODE | 审核医生代码 | string |  |
| 24 | VERIFYDATE | 审核时间 | string |  |
| 25 | SAMPLEDESCNAME | 标本说明 | string |  |
| 26 | PATPROPNAME | 病人特征 | string |  |
| 27 | REMARK | 备注 | string |  |
|  |  |  |  |  |





JSON返回示例：

{

    "BRLX": "1",

    "PATNAME": "张祥梅",

    "SEXDESC": "女",

    "AGEDESC": "70岁    ",

    "CARDNO": "",

    "WARDNAME": "肿瘤一科病房",

    "CHARGETYPENAME": null,

    "ORGAPPLYNO": "01000453914P",

    "SAMPLENAME": "血",

    "SAMPLETIME": "2012-06-09 16:00:45",

    "RECEIVETIME": "2012-06-09 16:00:49",

    "APPLYDEPTNAME": "肿瘤科",

    "APPLYDOCNAME": "殷学惠",

    "APPLYTIME": "2012-06-09 14:27:04",

    "CLINICDESC": "直肠恶性肿瘤",

    "HISORDERNAME": "平诊生化全套",

    "EXECDOCNAME": "张新伟",

    "VERIFIERNAME": "张新伟",

    "VERIFYDATE": "2012-06-10 10:49:19",

    "SAMPLEDESCNAME": null,

    "PATPROPNAME": null,

    "REMARK": "",

    "BRLXDESC": "住院",

    "RKSJ": "2012-06-10 09:49:28",

    "CXSJ": "2012-06-09 16:00:49"

  },



调用时机：点击”报告信息”节点的时候触发

相关表结构：LIS_LIST

代码实现：

根据查询条件从LIS_ORDERMASTER 表中查询病人信息



##### B03	报告结果

接口说明：根据报告单号获取报告结果

请求URL：../reportdetails/request/getreportresult

代码文件：winning.lis.reportdetails.service.ReportDetailsService

示例URL：http://192.168.11.211:10002/lis//reportdetails/request/getreportresult?applyno=1005



原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | applyno | 报告单号 | string | 如：1005 |



接口出参【ResposeMessage.data->object】：

| 节点名称：collect 类型：object  标题栏 | 节点名称：collect 类型：object  标题栏 | 节点名称：collect 类型：object  标题栏 | 节点名称：collect 类型：object  标题栏 | 节点名称：collect 类型：object  标题栏 | 节点名称：collect 类型：object  标题栏 | 节点名称：collect 类型：object  标题栏 |
| --- | --- | --- | --- | --- | --- | --- |
| 1 | ITEMCNT | 项目数量 | 项目数量 | string | string |  |
| 2 | RESULTABNCNT | 异常结果 | 异常结果 | string | string |  |
| 3 | HCNT | 偏高数量 | 偏高数量 | string | string |  |
| 4 | LCNT | 偏低数量 | 偏低数量 | string | string |  |
| 节点名称：reportresultlist  类型：array<object> | 节点名称：reportresultlist  类型：array<object> | 节点名称：reportresultlist  类型：array<object> | 节点名称：reportresultlist  类型：array<object> | 节点名称：reportresultlist  类型：array<object> | 节点名称：reportresultlist  类型：array<object> | 节点名称：reportresultlist  类型：array<object> |
| 1 | REDOFLAG | REDOFLAG | 复做标志 | string | 0：否   1：是 | 0：否   1：是 |
| 2 | PANICFLAG | PANICFLAG | 危急值标识 | string | 0-正常 1-危急值报告 | 0-正常 1-危急值报告 |
| 3 | RESULTCHAR | RESULTCHAR | 字符结果 | string | 如：37.60 | 如：37.60 |
| 3 | ITEMCODE | ITEMCODE | 项目代码 | string | 项目代码   如：APTT | 项目代码   如：APTT |
| 4 | ITEMNAME | ITEMNAME | 项目名称 | string | 项目名称  如：活化部分凝血活酶时间 | 项目名称  如：活化部分凝血活酶时间 |
| 5 | RESULT | RESULT | 报告结果 | string | 报告结果   如：37.60 | 报告结果   如：37.60 |
| 6 | REFERENCERANGE | REFERENCERANGE | 参考区间 | string | 如：22.00～37.00 | 如：22.00～37.00 |
| 7 | HINTINFO | HINTINFO | 提示 | string |  |  |
| 8 | UNIT | UNIT | 单位 | string | 参考区间单位   如：秒 | 参考区间单位   如：秒 |
| 9 | HISRESULT1 | HISRESULT1 | 历史结果1 | string | 历史报告结果1(相同检验项目的历史记录) | 历史报告结果1(相同检验项目的历史记录) |
| 10 | HISRESULT2 | HISRESULT2 | 历史结果2 | string | 历史报告结果2(相同检验项目的历史记录) | 历史报告结果2(相同检验项目的历史记录) |
| 11 | HISRESULT3 | HISRESULT3 | 历史结果3 | string | 历史报告结果3(相同检验项目的历史记录) | 历史报告结果3(相同检验项目的历史记录) |
| 12 | HISRESULT4 | HISRESULT4 | 历史结果4 | string | 历史报告结果4(相同检验项目的历史记录) | 历史报告结果4(相同检验项目的历史记录) |
| 13 | HISRESULT5 | HISRESULT5 | 历史结果5 | string | 历史报告结果5(相同检验项目的历史记录) | 历史报告结果5(相同检验项目的历史记录) |
| 14 | INSTID | INSTID | 仪器ID | string | 仪器ID  如：10010 | 仪器ID  如：10010 |
| 15 | INSTNAME | INSTNAME | 仪器名称 | string | 仪器名称  如：AU5800 | 仪器名称  如：AU5800 |
| 16 | HIGHLOWFLAG | HIGHLOWFLAG | 高低标志 | string | P-阳性或异常值 H-偏高 L-偏低 HH-显著偏高 LL-显著偏低 | P-阳性或异常值 H-偏高 L-偏低 HH-显著偏高 LL-显著偏低 |
| 17 | REDO | REDO | 复做标志 | Int, | 复做标记 0-正常 1-复做 | 复做标记 0-正常 1-复做 |
| 18 | ODRESULT | ODRESULT | OD值 | string | OD值 | OD值 |
| 19 | ODRESULTCHAR | ODRESULTCHAR | OD值字符表示 | string | OD值字符表示 | OD值字符表示 |
| 20 | CUTOFFVALUE | CUTOFFVALUE | CUTOFF比值 | string | CUTOFF比值 | CUTOFF比值 |
| 21 | CUTOFFVALUECHAR | CUTOFFVALUECHAR | CUTOFF比值字符表示 | string | CUTOFF比值字符表示 | CUTOFF比值字符表示 |
| 22 | SCOVALUE | SCOVALUE | SCO比值 | string | SCO比值 | SCO比值 |
| 23 | SCOVALUECHAR | SCOVALUECHAR | SCO比值字符表示 | string | SCO比值字符表示 | SCO比值字符表示 |
| 24 | RESULTABN | RESULTABN | 是否异常结果 | string | 0 是异常结果，1 正常结果 | 0 是异常结果，1 正常结果 |





JSON返回示例： 

"collect": {

        "LCNT": 0,

        "RESULTABNCNT": 4,

      "HCNT": 4,

      "ITEMCNT": 7

}，

"reportresultlist": [

         {

        "APPLYNO": 1005,

        "ITEMCODE": "FIB",

        "ITEMNAME": "纤维蛋白原",

        "INSTID": 8,

        "INSTCODE": "SF810-1",

        "INSTNAME": "SF810-1                                 ",

        "RESULTTIME": "2009-06-23 09:35:57",

        "RESULTVALUE": 213,

        "RESULTCHAR": "",

        "RESULTTYPE": "1",

        "RESULT": "213.00",

        "HINTINFO": "",

        "DISPLAYFLAG": 0,

        "SCIENTIFICFLAG": 0,

        "ODRESULT": "",

        "ODRESULTCHAR": "",

        "CUTOFFVALUE": "",

        "CUTOFFVALUECHAR": "",

        "SCOVALUE": "",

        "SCOVALUECHAR": "",

        "TESTINSTID": "",

        "TESTINSTCODE": "",

        "TESTINSTNAME": "",

        "PRINTORDER": "A0000",

        "HIGHLOWFLAG": "H",

        "REFERENCERANGE": "2.00～4.00 g/L                 ",

        "UNIT": "g/L                 ",

        "REDO": "",

        "PANICFLAG": 0,

        "INSTVERIFYFLAG": "",

        "INSTVERIFYCONTENT": "",

        "SCOPICFLAG": 0,

        "HOSPITALCODE": "9999",

        "HISRESULT1": "",

        "HISEXECTIME1": "",

        "HISHIGHLOWFLAG1": "",

        "HISPANICFLAG1": "",

        "LIFELIMITFLAG1": "",

        "HISRESULT2": "",

        "HISEXECTIME2": "",

        "HISHIGHLOWFLAG2": "",

        "HISPANICFLAG2": "",

        "LIFELIMITFLAG2": "",

        "HISRESULT3": "",

        "HISEXECTIME3": "",

        "HISHIGHLOWFLAG3": "",

        "HISPANICFLAG3": "",

        "LIFELIMITFLAG3": "",

        "HISRESULT4": "",

        "HISEXECTIME4": "",

        "HISHIGHLOWFLAG4": "",

        "HISPANICFLAG4": "",

        "LIFELIMITFLAG4": "",

        "HISRESULT5": "",

        "HISEXECTIME5": "",

        "HISHIGHLOWFLAG5": "",

        "HISPANICFLAG5": "",

        "LIFELIMITFLAG5": "",

        "IMPORTANT": "",

        "RESULTABN": "0"

      }] }



调用时机：点击”报告信息”节点的时候触发

相关表结构：LIS_ORDERMASTER

代码实现：

根据查询条件从lsp_GetResultData 存储过程中查询报告结果

##### B04	原始结果

接口说明：根据报告单号获取报告原始结果

请求URL：../reportdetails/request/getreportrawresult

代码文件：winning.lis.reportdetails.service.ReportDetailsService

示例URL：http://192.168.11.211:10002/lis//reportdetails/request/getreportrawtresult?applyno=1005



原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | applyno | 报告单号 | string | 如：1005 |



接口出参【ResposeMessage.data->object】：

| 节点名称：collect 类型：object  标题栏 | 节点名称：collect 类型：object  标题栏 | 节点名称：collect 类型：object  标题栏 | 节点名称：collect 类型：object  标题栏 | 节点名称：collect 类型：object  标题栏 | 节点名称：collect 类型：object  标题栏 | 节点名称：collect 类型：object  标题栏 |
| --- | --- | --- | --- | --- | --- | --- |
| 1 | ITEMCNT | 项目数量 | 项目数量 | string | string |  |
| 2 | DIFFRITEMCNT | 与当前结果报告不同 | 与当前结果报告不同 | string | string |  |
|  |  |  |  |  |  |  |
|  |  |  |  |  |  |  |
| 节点名称：reportresultlist  类型：array<object> | 节点名称：reportresultlist  类型：array<object> | 节点名称：reportresultlist  类型：array<object> | 节点名称：reportresultlist  类型：array<object> | 节点名称：reportresultlist  类型：array<object> | 节点名称：reportresultlist  类型：array<object> | 节点名称：reportresultlist  类型：array<object> |
| 3 | ITEMCODE | ITEMCODE | 项目代码 | string | 项目代码   如：APTT | 项目代码   如：APTT |
| 4 | ITEMNAME | ITEMNAME | 项目名称 | string | 项目名称  如：活化部分凝血活酶时间 | 项目名称  如：活化部分凝血活酶时间 |
| 5 | RESULT | RESULT | 当前结果 | string | 报告结果   如：37.60 | 报告结果   如：37.60 |
| 7 | RAWRESULT | RAWRESULT | 原始结果 | string |  |  |
| 6 | REFERENCERANGE | REFERENCERANGE | 参考区间 | string | 如：22.00～37.00 | 如：22.00～37.00 |
| 14 | INSTID | INSTID | 仪器ID | string | 仪器ID  如：10010 | 仪器ID  如：10010 |
| 15 | INSTNAME | INSTNAME | 仪器名称 | string | 仪器名称  如：AU5800 | 仪器名称  如：AU5800 |
| 16 | HINTINFO | HINTINFO | 提示信息 | string |  |  |
| 24 | DIFFRESULT | DIFFRESULT | 与当前结果不同项目 | string | 0 是相同，1 不同，当=1时，就是”仅查看与当前结果不同的项目” | 0 是相同，1 不同，当=1时，就是”仅查看与当前结果不同的项目” |





JSON返回示例： 

{

    "reportresultlist  ": [

      {

        "ITEMCODE": 400,

        "RESULT": "27.8",

        "HINTINFO": "测试数据",

        "INSTNAME": "A2700",

        "INSTID": "3",

        "RAWRESULT": "13.5",

        "REFERENCERANGE": "10~19%",

        "ITEMNAME": "血常规0",

        "DIFFRITEMCNT": 1

      },

      {

        "ITEMCODE": 401,

        "RESULT": "27.8",

        "HINTINFO": "测试数据",

        "INSTNAME": "A2700",

        "INSTID": "3",

        "RAWRESULT": "13.5",

        "REFERENCERANGE": "10~19%",

        "ITEMNAME": "血常规1",

        "DIFFRITEMCNT": 1

      },

      {

        "ITEMCODE": 402,

        "RESULT": "27.8",

        "HINTINFO": "测试数据",

        "INSTNAME": "A2700",

        "INSTID": "3",

        "RAWRESULT": "13.5",

        "REFERENCERANGE": "10~19%",

        "ITEMNAME": "血常规2",

        "DIFFRITEMCNT": 1

      },

      {

        "ITEMCODE": 403,

        "RESULT": "27.8",

        "HINTINFO": "测试数据",

        "INSTNAME": "A2700",

        "INSTID": "3",

        "RAWRESULT": "13.5",

        "REFERENCERANGE": "10~19%",

        "ITEMNAME": "血常规3",

        "DIFFRITEMCNT": 1

      }

    ],

    "collect  ": {

      "DIFFRESULT": 1,

      "ITEMCNT": 4

    }

  } 	 

调用时机：点击”报告信息”节点的时候触发

相关表结构：LIS_ORDERMASTER

代码实现：

根据查询条件从LIS_RESULT，LIS_RESULTMOD表中报告原始结果信息



##### B05	检测结果

接口说明：根据报告单号获取检测结果相关信息

请求URL：../reportdetails/request/getitem

代码文件：winning.lis.reportdetails.service.ReportDetailsService

示例URL：http://192.168.11.211:10002/lis//reportdetails/request/getitem?applyno=6545434



原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | applyno | 报告单号 | string | 如：6545434 |



接口出参【ResposeMessage.data->object】：

| 节点名称：collect 类型：object  标题栏 | 节点名称：collect 类型：object  标题栏 | 节点名称：collect 类型：object  标题栏 | 节点名称：collect 类型：object  标题栏 | 节点名称：collect 类型：object  标题栏 | 节点名称：collect 类型：object  标题栏 | 节点名称：collect 类型：object  标题栏 |
| --- | --- | --- | --- | --- | --- | --- |
| 1 | ITEMCNT | 项目数量 | 项目数量 | string | string |  |
| 2 | CHARGEAMOUNT | 费用金额 | 费用金额 | string | string |  |
| 3 | REFUNDS | 退费记录 | 退费记录 | string | string |  |
| 4 | ADDITEMS | 医技上传项目 | 医技上传项目 | string | string |  |
| 节点名称：iteminfos类型：array<object> | 节点名称：iteminfos类型：array<object> | 节点名称：iteminfos类型：array<object> | 节点名称：iteminfos类型：array<object> | 节点名称：iteminfos类型：array<object> | 节点名称：iteminfos类型：array<object> | 节点名称：iteminfos类型：array<object> |
| 5 | HISORDERNAME | HISORDERNAME | 项目名称 | string | 项目名称:肝功能八项 | 项目名称:肝功能八项 |
| 6 | XMSL | XMSL | 数量 | string | 1 | 1 |
| 7 | PRICE | PRICE | 价格 | string | 项目名称:肝功能八项 89.0 | 项目名称:肝功能八项 89.0 |
| 8 | APPLYDOCNAME | APPLYDOCNAME | 医生姓名 | string | 赵奇 | 赵奇 |
| 9 | APPLYTIME | APPLYTIME | 时间 | string |  |  |
| 10 | APPLYDEPTNAME | APPLYDEPTNAME | 开方科室 | string | 小儿科 | 小儿科 |
| 11 | CLINICDESC | CLINICDESC | 临床诊断 | string | 感冒发烧 | 感冒发烧 |
| 12 | CHARGEFLAG | CHARGEFLAG | 费用标记 | string | 收费标志 0:未收费 1:已收费 2:已退费 | 收费标志 0:未收费 1:已收费 2:已退费 |
| 13 | CHARGEFLAGDESC | CHARGEFLAGDESC | 费用标记中文描述 | string | 收费标志 未收费 已收费 已退费 | 收费标志 未收费 已收费 已退费 |
|  |  |  |  |  |  |  |





JSON返回示例： 

{

    "reportresultlist  ": [

      {

        "ITEMCODE": 400,

        "RESULT": "27.8",

        "HINTINFO": "测试数据",

        "INSTNAME": "A2700",

        "INSTID": "3",

        "RAWRESULT": "13.5",

        "REFERENCERANGE": "10~19%",

        "ITEMNAME": "血常规0",

        "DIFFRITEMCNT": 1

      },

      {

        "ITEMCODE": 401,

        "RESULT": "27.8",

        "HINTINFO": "测试数据",

        "INSTNAME": "A2700",

        "INSTID": "3",

        "RAWRESULT": "13.5",

        "REFERENCERANGE": "10~19%",

        "ITEMNAME": "血常规1",

        "DIFFRITEMCNT": 1

      },

      {

        "ITEMCODE": 402,

        "RESULT": "27.8",

        "HINTINFO": "测试数据",

        "INSTNAME": "A2700",

        "INSTID": "3",

        "RAWRESULT": "13.5",

        "REFERENCERANGE": "10~19%",

        "ITEMNAME": "血常规2",

        "DIFFRITEMCNT": 1

      },

      {

        "ITEMCODE": 403,

        "RESULT": "27.8",

        "HINTINFO": "测试数据",

        "INSTNAME": "A2700",

        "INSTID": "3",

        "RAWRESULT": "13.5",

        "REFERENCERANGE": "10~19%",

        "ITEMNAME": "血常规3",

        "DIFFRITEMCNT": 1

      }

    ],

    "collect  ": {

      "DIFFRESULT": 1,

      "ITEMCNT": 4

    }

  } 	 

调用时机：点击”报告信息”节点的时候触发

相关表结构：LIS_ACCEPTITEMS 检测项信息

代码实现：

根据查询条件从LIS_ORDERMASTER 表中查询病人信息



##### B06	修改记录

接口说明：根据报告单号获取检测结果相关信息

请求URL：../reportdetails/request/updaterecord

代码文件：winning.lis.reportdetails.service.ReportDetailsService

示例URL：http://192.168.11.211:10002/lis//reportdetails/request/updaterecord?applyno=6545434



原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | applyno | 报告单号 | string | 如：6545434 |



接口出参【ResposeMessage.data->object】：

| 节点名称：record  类型：array<object> 记录 | 节点名称：record  类型：array<object> 记录 | 节点名称：record  类型：array<object> 记录 | 节点名称：record  类型：array<object> 记录 | 节点名称：record  类型：array<object> 记录 | 节点名称：record  类型：array<object> 记录 |
| --- | --- | --- | --- | --- | --- |
| 1 | MODNAME | 修改人姓名 | 修改人姓名 | string |  |
| 2 | MODTYPE | 修改类型 | 修改类型 | string |  |
| 3 | MODTIME | 修改时间 | 修改时间 | string |  |
| 4 | MODPCNAME | 客户端名称(IP) | 客户端名称(IP) | string |  |
|  |  |  |  |  |  |
| 节点名称：update 类型：array<object>  record 的子项 | 节点名称：update 类型：array<object>  record 的子项 | 节点名称：update 类型：array<object>  record 的子项 | 节点名称：update 类型：array<object>  record 的子项 | 节点名称：update 类型：array<object>  record 的子项 | 节点名称：update 类型：array<object>  record 的子项 |
| 2 | ATTRIBUTE | ATTRIBUTE | 属性 | string |  |
| 3 | SRCVALUE | SRCVALUE | 原始值 | string |  |
| 4 | TARGETVALUE | TARGETVALUE | 目标值 | string |  |
| 节点名称：cnt类型：object  数量 | 节点名称：cnt类型：object  数量 | 节点名称：cnt类型：object  数量 | 节点名称：cnt类型：object  数量 | 节点名称：cnt类型：object  数量 | 节点名称：cnt类型：object  数量 |
|  |  |  |  |  |  |
|  |  |  |  |  |  |
|  |  |  |  |  |  |
|  |  |  |  |  |  |
|  |  |  |  |  |  |





JSON返回示例： 



调用时机：点击”报告信息”节点的时候触发

相关表结构：LIS_ACCEPTITEMS 检测项信息

代码实现：

根据查询条件从LIS_ORDERMASTER 表中查询病人信息



##### B07复查结果

接口说明：根据报告单号获取检测结果相关信息

请求URL：../reportdetails/request/getredoresult

代码文件：winning.lis.reportdetails.service.ReportDetailsService

示例URL：http://192.168.11.211:10002/lis//reportdetails/request/getredoresult?applyno=6545434

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | applyno | 报告单号 | string | 如：6545434 |



接口出参【ResposeMessage.data->object】：

| 节点名称：collect 类型：object  标题栏 | 节点名称：collect 类型：object  标题栏 | 节点名称：collect 类型：object  标题栏 | 节点名称：collect 类型：object  标题栏 | 节点名称：collect 类型：object  标题栏 | 节点名称：collect 类型：object  标题栏 | 节点名称：collect 类型：object  标题栏 |
| --- | --- | --- | --- | --- | --- | --- |
| 1 | REDOITEMCNT | 复查项目数量 | 复查项目数量 | string | string |  |
| 2 | REDOREASON | 复做原因 | 复做原因 |  |  |  |
| 节点名称：hisiteminfos类型：array<object> | 节点名称：hisiteminfos类型：array<object> | 节点名称：hisiteminfos类型：array<object> | 节点名称：hisiteminfos类型：array<object> | 节点名称：hisiteminfos类型：array<object> | 节点名称：hisiteminfos类型：array<object> | 节点名称：hisiteminfos类型：array<object> |
| 2 | HISORDERNAME | HISORDERNAME | 项目名称 | string | 项目名称:肝功能八项 | 项目名称:肝功能八项 |
| 3 | HRESULT | HRESULT | 偏高值 | string |  |  |
| 4 | HISORDERCODE | HISORDERCODE | 项目编码 | string |  |  |
|  | HIGHLOWFLAG | HIGHLOWFLAG | 偏高/偏低 | string | H偏高,L偏低 | H偏高,L偏低 |
| 节点名称：resultinfos类型：array<object>   hisiteminfos的子节点信息 | 节点名称：resultinfos类型：array<object>   hisiteminfos的子节点信息 | 节点名称：resultinfos类型：array<object>   hisiteminfos的子节点信息 | 节点名称：resultinfos类型：array<object>   hisiteminfos的子节点信息 | 节点名称：resultinfos类型：array<object>   hisiteminfos的子节点信息 | 节点名称：resultinfos类型：array<object>   hisiteminfos的子节点信息 | 节点名称：resultinfos类型：array<object>   hisiteminfos的子节点信息 |
| 5 | RESULT | RESULT | 检验结果 | string | 23.6 | 23.6 |
| 6 | RESULTTIME | RESULTTIME | 结果时间 | string |  |  |
| 7 | INSTNAME | INSTNAME | 仪器名称 | string |  |  |
| 8 | REFERENCERANGE | REFERENCERANGE | 参考范围 | string | 18.00~35.00 | 18.00~35.00 |
| 9 | UNIT | UNIT | 单位 | string | ug/ml | ug/ml |
| 10 | HIGHLOWFLAG | HIGHLOWFLAG | 高低标志 | string | H 偏高,L偏低 | H 偏高,L偏低 |
| 11 | REDOREASON | REDOREASON | 复做原因 | string |  |  |
| 12 | EXECDOCCODE | EXECDOCCODE | 检查医生代码 | string |  |  |
| 13 | EXECDOCNAME | EXECDOCNAME | 检查医生名名称 | string |  |  |





JSON返回示例： 

 {

    "hisiteminfos": [

      {

        "HISORDERCODE": "81",

        "HISORDERNAME": "糖化血红蛋白测定",

        "HRESULT": "H",

        "resultinfos": [

          {

            "REDOREASON": null,

            "RESULTTIME": "2017-07-03 10:19:02",

            "ITEMCODE": "HbA1c",

            "ITEMNAME": "糖化血红蛋白",

            "INSTNAME": "HA8180                                  ",

            "RESULT": "6.40",

            "HIGHLOWFLAG": "H",

            "REFERENCERANGE": "3.60～6.30",

            "UNIT": "%                   ",

            "EXECDOCCODE": "5145",

            "EXECDOCNAME": "潘倩",

            "HISORDERNAME": "糖化血红蛋白测定",

            "HISORDERCODE": "81"

          }

        ]

      }

    ],

    "collect": {

      "REDOITEMCNT": 1,

"REDOREASON":"潘倩",

    }

  }

调用时机：点击”复做结果”节点的时候触发

相关表结构：LIS_LIST,LIS_RESULT

代码实现：



##### B08危机项目

接口说明：根据报告单号获取危机项目信息

请求URL：../reportdetails/request/getpanicitem

代码文件：winning.lis.reportdetails.service.ReportDetailsService

示例URL：http://192.168.11.211:10002/lis//reportdetails/request/getpanicitem?applyno=6545434



原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | applyno | 报告单号 | string | 如：6545434 |



接口出参【ResposeMessage.data->object】：

| 节点名称：collect 类型：object  标题栏 | 节点名称：collect 类型：object  标题栏 | 节点名称：collect 类型：object  标题栏 | 节点名称：collect 类型：object  标题栏 | 节点名称：collect 类型：object  标题栏 | 节点名称：collect 类型：object  标题栏 | 节点名称：collect 类型：object  标题栏 |
| --- | --- | --- | --- | --- | --- | --- |
| 1 | PANICCNT | 危机项目数量 | 危机项目数量 | string | string |  |
| 2 | STATUSDESC | 报告状态描述 | 报告状态描述 | string | string | 中文描述 -已发布 |
|  |  |  |  |  |  |  |
|  |  |  |  |  |  |  |
| 节点名称：iteminfos类型：array<object> | 节点名称：iteminfos类型：array<object> | 节点名称：iteminfos类型：array<object> | 节点名称：iteminfos类型：array<object> | 节点名称：iteminfos类型：array<object> | 节点名称：iteminfos类型：array<object> | 节点名称：iteminfos类型：array<object> |
| 5 | ITEMNAME | ITEMNAME | 项目名称 | string | 项目名称:肝功能八项 | 项目名称:肝功能八项 |
| 6 | ITEMCODE | ITEMCODE | 项目编码 | string | 1 | 1 |
| 7 | RESULT | RESULT | 结果 | string |  |  |
| 8 | EXECDOCNAME | EXECDOCNAME | 检验医生姓名 | string | 赵奇 | 赵奇 |
| 9 | EXECDOCCODE | EXECDOCCODE | 检验医生代码 | string | 6509 | 6509 |
| 10 | REFERENCERANGE | REFERENCERANGE | 参考区间 | string | 小儿科 | 小儿科 |
| 11 | UNIT | UNIT | 单位 | string | 参考区间单位   如：秒 | 参考区间单位   如：秒 |
| 12 | REDO | REDO | 复做项目 | string | 复做标记 0-正常 1-复做 | 复做标记 0-正常 1-复做 |
| 13 | HIGHLOWFLAG | HIGHLOWFLAG | 高低标记 | string | H-偏高 L-偏低 HH-显著偏高 LL-显著偏低（显著也在范围） | H-偏高 L-偏低 HH-显著偏高 LL-显著偏低（显著也在范围） |
| 14 | RESULTTIME | RESULTTIME | 检查项目时间 | string | 检查时间 | 检查时间 |
| 15 | INSTNAME | INSTNAME | 仪器名称 | string | 仪器名称 | 仪器名称 |





JSON返回示例： 



调用时机：点击“危机项目”节点的时候触发

相关表结构：LIS_LIST,LIS_RESULT 检测项信息

代码实现：

##### B09报告全跟踪

接口说明：根据报告单号获取检测结果相关信息

请求URL：../reportdetails/request/reporttrac

代码文件：winning.lis.reportdetails.service.ReportDetailsService

示例URL：http://192.168.11.211:10002/lis/reportdetails/request/reporttrac?applyno=343432



原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | applyno | 报告单号 | string | 如：343432 |



接口出参【ResposeMessage.data->object】：

| 节点名称：iteminfos类型：array<object>   按照时间与节点顺序已经排列好 | 节点名称：iteminfos类型：array<object>   按照时间与节点顺序已经排列好 | 节点名称：iteminfos类型：array<object>   按照时间与节点顺序已经排列好 | 节点名称：iteminfos类型：array<object>   按照时间与节点顺序已经排列好 | 节点名称：iteminfos类型：array<object>   按照时间与节点顺序已经排列好 |
| --- | --- | --- | --- | --- |
| 5 | LOGTIME | 时间 | string |  |
| 6 | LOGNODE | 节点 | string | 节点中文描述，与原型中对应的5个节点，NODE字段同时也对应 英文拼写首字符大写 |
| 7 | LOGCONTENT | 日志内容 | string | 返回前台日志内容 |
| 8 | TXM | 条形码 | string |  |
| 9 | ITEMCNT | 项目总数 | string | 仅仅 NODE= BBRK时才有值 |
| 10 | HISORDERNAME | 收费项目名称 | string | 仅仅 NODE= BBRK时才有值 |
| 11 | NODE | 节点标记 | string | 如果 为 BBRK(标本入库)则，TXM不为空，则内容中多了TXM 超链接，并且HISORDERNAME不为空，
同时，LOGCONTENT的部分需要前端 重新组装 将TXM增加下划线。可以出发弹框 |





JSON返回示例： 

	 {

    "iteminfos": [

      {

        "LOGTIME": 1498652790000,

        "LOGNODE": "标本入库",

        "LOGCONTENT": "标本入库:由【史雪东】在客户端【SXD(127.0.0.1)上进行条码入库",

        "ITEMCNT": 1,

        "HISORDERNAME": "凝血四项",

        "TXM": "09000119066P",

        "NODE": "BBRK"

      },

      {

        "LOGTIME": 1498652993000,

        "LOGNODE": "报告审核",

        "LOGCONTENT": "报告审核:由【史雪东】在客户端【SXD(127.0.0.1)设备终端进行审核",

        "ITEMCNT": "",

        "HISORDERNAME": "",

        "TXM": "",

        "NODE": "BGSH"

      },

      {

        "LOGTIME": 1498703716000,

        "LOGNODE": "报告发布",

        "LOGCONTENT": "报告发布:由【史雪东】在客户端【SXD(127.0.0.1)设备终端进行报告发布",

        "ITEMCNT": "",

        "HISORDERNAME": "",

        "TXM": "",

        "NODE": "BGFB"

      },

      {

        "LOGTIME": 1498703808000,

        "LOGNODE": "报告发布",

        "LOGCONTENT": "报告发布:由【史雪东】在客户端【SXD(127.0.0.1)设备终端进行报告发布",

        "ITEMCNT": "",

        "HISORDERNAME": "",

        "TXM": "",

        "NODE": "BGFB"

      },

      {

        "LOGTIME": 1498721363000,

        "LOGNODE": "报告打印",

        "LOGCONTENT": "报告打印:由患者通过卡机在自助机【SXD(127.0.0.1)】上打印报告份",

        "ITEMCNT": "",

        "HISORDERNAME": "",

        "TXM": "",

        "NODE": "BGDY"

      },

      {

        "LOGTIME": 1498738069000,

        "LOGNODE": "报告调阅",

        "LOGCONTENT": "报告调阅:由临床医生【孙华斌-1101】通过【SXD(127.0.0.1)】查看报告",

        "ITEMCNT": "",

        "HISORDERNAME": "",

        "TXM": "",

        "NODE": "BGSY"

      }

    ]

  }

调用时机：点击”报告全跟踪”节点的时候触发

相关表结构：LIS_LIST,LIS_REPORTLOG

代码实现：

根据查询条件从LIS_ORDERMASTER 表中查询病人信息



##### B10 TAT分析

接口说明：根据报告单号获取检测结果相关信息

请求URL：../reportdetails/request/gettatanalysis

代码文件：winning.lis.reportdetails.service.ReportDetailsService

示例URL：http://192.168.11.211:10002/lis//reportdetails/request/gettatanalysis?applyno=6545434



原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | applyno | 报告单号 | string | 如：6530299 |



接口出参【ResposeMessage.data->object】：

| 节点名称：collect 类型：object  标题栏 | 节点名称：collect 类型：object  标题栏 | 节点名称：collect 类型：object  标题栏 | 节点名称：collect 类型：object  标题栏 | 节点名称：collect 类型：object  标题栏 | 节点名称：collect 类型：object  标题栏 | 节点名称：collect 类型：object  标题栏 |
| --- | --- | --- | --- | --- | --- | --- |
| 1 | TATITEMCNT | 违反TAT规则数量 | 违反TAT规则数量 | string | string |  |
| 节点名称：tatinfos类型：array<object> | 节点名称：tatinfos类型：array<object> | 节点名称：tatinfos类型：array<object> | 节点名称：tatinfos类型：array<object> | 节点名称：tatinfos类型：array<object> | 节点名称：tatinfos类型：array<object> | 节点名称：tatinfos类型：array<object> |
| 5 | HISORDERNAME | HISORDERNAME | 项目名称 | string | 项目名称:肝功能八项 | 项目名称:肝功能八项 |
| 6 | TATNODEDESC | TATNODEDESC | TAT节点描述 | string | 采样到签收20分钟 | 采样到签收20分钟 |
| 7 | TATNODETIMEDESC | TATNODETIMEDESC | TAT节点时间描述 | string | 2017-03-09(采样) ->2017-03-09(签收) | 2017-03-09(采样) ->2017-03-09(签收) |
| 8 | TATVOILATE | TATVOILATE | TAT标识 | string | 1 违反规则，9合理违反 | 1 违反规则，9合理违反 |
|  |  |  |  |  |  |  |





JSON返回示例： 



调用时机：点击”TAT分析”节点的时候触发

相关表结构： LIS_TATRECORD

代码实现：

根据查询条件从LIS_ORDERMASTER 表中查询病人信息



##### B11 报告图片

接口说明：根据报告单号获取报告图片

请求URL：../reportdetails/request/reportimage

代码文件：winning.lis.reportdetails.service.ReportDetailsService

示例URL：http://192.168.11.211:10002/lis//reportdetails/request/reportimage?applyno=6545434



原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | applyno | 报告单号 | string | 如：6545434 |



接口出参【ResposeMessage.data->object】：

| 节点名称：imageinfos类型：array<object> | 节点名称：imageinfos类型：array<object> | 节点名称：imageinfos类型：array<object> | 节点名称：imageinfos类型：array<object> | 节点名称：imageinfos类型：array<object> |
| --- | --- | --- | --- | --- |
| 5 | FILENAME | 文件名称 | string | WBC,RBC |
| 6 | IMAGEFORMAT | 文件格式 | string | 文件格式类型 目前不明确 |
| 7 | IMAGEDATA | 文件二进制数据 | Byte[] |  |
| 8 | PRINTFLAG | 打印标识 | string | 允许打印标志 |
|  | IMAGEURL | 文件URL |  |  |





JSON返回示例： 



调用时机：点击”报告图片”节点的时候触发

相关表结构： LIS_IMAGEDATA

代码实现：

根据查询条件从LIS_ORDERMASTER 表中查询病人信息







### 批量打印

#### V 模块接口视图

| NO. | 接口编号 | 接口名称 | 说明 |
| --- | --- | --- | --- |
| 1 | B01 | 获取打印报告信息
../cmp/printreport/getprintreportinfo | 根据报告单号，仪器ID，打印模式获取打印报告信息 |
| 2 | B02 |  |  |
| 3 | B03 |  |  |
| 4 | B04 |  |  |
| 6 | B05 |  |  |
| 7 | B06 |  |  |
| 8 | B07 |  |  |
| 9 | B08 |  |  |









#### B 业务类

##### B01	获取打印报告信息

接口说明：根据报告单号，仪器ID，打印模式获取打印报告信息

请求URL：../cmp/printreport/getprintreportinfo

代码文件：winning.cmp.lis.printreport.controller.PrintReportController

示例URL：http://192.168.11.211:10002/lis/cmp/printreport/getprintreportinfo



原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | applynolist | 报告单号列表 | array |  |
| 2 | instid | 仪器ID |  |  |
| 3 | printmode | 打印模式 |  | 0 预览，2 打印 |
| 4 |  |  |  |  |
| 5 |  |  |  |  |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：patinfo类型：array<object>   患者信息 | 节点名称：patinfo类型：array<object>   患者信息 | 节点名称：patinfo类型：array<object>   患者信息 | 节点名称：patinfo类型：array<object>   患者信息 | 节点名称：patinfo类型：array<object>   患者信息 |
|  |  |  |  |  |
|  |  |  |  |  |
|  |  |  |  |  |
|  |  |  |  |  |
|  |  |  |  |  |
|  |  |  |  |  |
|  |  |  |  |  |
|  |  |  |  |  |
|  |  |  |  |  |
|  |  |  |  |  |
|  |  |  |  |  |
| 节点名称：resultinfo类型：array<object>   结果信息 | 节点名称：resultinfo类型：array<object>   结果信息 | 节点名称：resultinfo类型：array<object>   结果信息 | 节点名称：resultinfo类型：array<object>   结果信息 | 节点名称：resultinfo类型：array<object>   结果信息 |
|  |  |  |  |  |
|  |  |  |  |  |
|  |  |  |  |  |
|  |  |  |  |  |
|  |  |  |  |  |
|  |  |  |  |  |
|  |  |  |  |  |
|  |  |  |  |  |
|  |  |  |  |  |
|  |  |  |  |  |
|  |  |  |  |  |
|  |  |  |  |  |
|  |  |  |  |  |
|  |  |  |  |  |
|  |  |  |  |  |
|  |  |  |  |  |
|  |  |  |  |  |
|  |  |  |  |  |
|  |  |  |  |  |
|  |  |  |  |  |
|  |  |  |  |  |
|  |  |  |  |  |
|  |  |  |  |  |
|  |  |  |  |  |
|  |  |  |  |  |
|  |  |  |  |  |
|  |  |  |  |  |





JSON返回示例：

  {

    "template": "",

    "patinfo": [

      {

        "APPLYNO_PAGE": 1,

        "REPORTCAPTION": "产科",

        "TODOCCODE": "5555",

        "TODOCNAME": "李开元",

        "INVOICE": "",

        "APPLYNO": 6544150,

        "EXECTIME": 1499011200000,

        "INSTID": 8,

        "SAMPLETYPE": "  ",

        "TECHNO": 211,

        "CURENO": "494266",

        "CARDNO": "0555520",

        "HOSPNO": "915616",

        "PATNAME": "李娟娟",

        "IMECODE": "LJJ",

        "SEX": "2",

        "BIRTHDAY": "",

        "AGE": 28,

        "AGEUNIT": "岁    ",

        "CHARGETYPE": "001         ",

        "CHARGETYPENAME": "自费",

        "WARDORREG": "1",

        "WARDORREGNAME": "住院",

        "WARD": "1008",

        "WARDNAME": "产科病房",

        "BEDNO": "32",

        "APPLYDEPT": "1163",

        "APPLYDEPTNAME": "产科",

        "APPLYTIME": 1499003571000,

        "CLINICDESC": "",

        "SAMPLE": "1",

        "SAMPLENAME": "血",

        "SAMPLETIME": 1499004065963,

        "RECEIVETIME": 1499011365447,

        "EXECDEPT": "",

        "EXECDEPTNAME": "",

        "REPORTTIME": 1499011407460,

        "ORGAPPLYNO": "09002787691P",

        "TXM": "09002787691P",

        "PATPROPNO": "",

        "PATPROPNAME": "",

        "REGISTERCODE": "5555",

        "REGISTERNAME": "李开元",

        "EXECDOCCODE": "5555",

        "EXECDOCNAME": "李开元",

        "SAMPLEDESC": "",

        "SAMPLEDESCNAME": "",

        "STATUS": 50,

        "PRINTSTATUS": 1,

        "EXAMRESULT": "",

        "NOPASSREASON": "",

        "REMARK": "",

        "CHARGESTATUS": "001         ",

        "REDO": 0,

        "M_PHONE": "",

        "MOBILESTATUS": 0,

        "PUBDATETIME": 1499011408000,

        "PRINTCOUNT": 0,

        "PUBCODE": "5555",

        "PUBNAME": "李开元",

        "VERIFIERCODE": "51330",

        "VERIFIERNAME": "谢迎春",

        "SEXNAME": "女",

        "CLINICDESCNAME": "",

        "HISORDERCODE": "39",

        "HISORDERNAME": "凝血四项",

        "QSSJ": "",

        "RKSJ": "",

        "ACCEPTTIME": "",

        "INST_NAME": "SF810-1                                 ",

        "EXAMNAME": "临检",

        "PANICITEMS": "",

        "REGISTERTIME": 1499011339033,

        "IDNUM": "",

        "TEMPLATE": "",

        "PRINTCOL": 2,

        "PRINTROW": 12,

        "DOUBLETEMPLATE": 0

      },

      {

        "APPLYNO_PAGE": 1,

        "REPORTCAPTION": "风湿内分泌科",

        "TODOCCODE": "5555",

        "TODOCNAME": "李开元",

        "INVOICE": "",

        "APPLYNO": 6544157,

        "EXECTIME": 1499011200000,

        "INSTID": 8,

        "SAMPLETYPE": "  ",

        "TECHNO": 212,

        "CURENO": "494299",

        "CARDNO": "",

        "HOSPNO": "915649",

        "PATNAME": "雷珍梅",

        "IMECODE": "LZM",

        "SEX": "2",

        "BIRTHDAY": "",

        "AGE": 63,

        "AGEUNIT": "岁    ",

        "CHARGETYPE": "001         ",

        "CHARGETYPENAME": "自费",

        "WARDORREG": "1",

        "WARDORREGNAME": "住院",

        "WARD": "1021",

        "WARDNAME": "风湿内分泌科病房",

        "BEDNO": "301",

        "APPLYDEPT": "1171",

        "APPLYDEPTNAME": "风湿内分泌科",

        "APPLYTIME": 1499010065000,

        "CLINICDESC": "低血糖性昏迷",

        "SAMPLE": "1",

        "SAMPLENAME": "血",

        "SAMPLETIME": 1499010483910,

        "RECEIVETIME": 1499014477130,

        "EXECDEPT": "",

        "EXECDEPTNAME": "",

        "REPORTTIME": 1499014484820,

        "ORGAPPLYNO": "09002787743P",

        "TXM": "09002787743P",

        "PATPROPNO": "",

        "PATPROPNAME": "",

        "REGISTERCODE": "5555",

        "REGISTERNAME": "李开元",

        "EXECDOCCODE": "5555",

        "EXECDOCNAME": "李开元",

        "SAMPLEDESC": "",

        "SAMPLEDESCNAME": "",

        "STATUS": 50,

        "PRINTSTATUS": 1,

        "EXAMRESULT": "",

        "NOPASSREASON": "",

        "REMARK": "",

        "CHARGESTATUS": "001         ",

        "REDO": 0,

        "M_PHONE": "",

        "MOBILESTATUS": 0,

        "PUBDATETIME": 1499014485000,

        "PRINTCOUNT": 0,

        "PUBCODE": "5555",

        "PUBNAME": "李开元",

        "VERIFIERCODE": "51330",

        "VERIFIERNAME": "谢迎春",

        "SEXNAME": "女",

        "CLINICDESCNAME": "低血糖性昏迷",

        "HISORDERCODE": "795",

        "HISORDERNAME": "凝血五项",

        "QSSJ": "",

        "RKSJ": "",

        "ACCEPTTIME": "",

        "INST_NAME": "SF810-1                                 ",

        "EXAMNAME": "临检",

        "PANICITEMS": "",

        "REGISTERTIME": 1499014460660,

        "IDNUM": "",

        "TEMPLATE": "",

        "PRINTCOL": 2,

        "PRINTROW": 12,

        "DOUBLETEMPLATE": 0

      }

    ],

    "resultinfo": [

      {

        "line": 1,

        "applyno_page": 1,

        "applyno": 6544150,

        "number1": "1",

        "itemcode1": "PT                  ",

        "itemnum1": "PT",

        "itemname1": "凝血酶原时间",

        "odresult1": "",

        "cutoffvalue1": "",

        "scovalue1": "",

        "result1": "9.70",

        "refer1": "10.00～17.00",

        "hint1": "",

        "unit1": "秒",

        "highflag1": "",

        "printgroup": "A",

        "printorder": 1,

        "TestMethod": "",

        "TestMethodName": "",

        "number2": "",

        "itemcode2": "",

        "itemnum2": "",

        "itemname2": "",

        "odresult2": "",

        "cutoffvalue2": "",

        "scovalue2": "",

        "result2": "",

        "refer2": "",

        "hint2": "",

        "unit2": "",

        "highflag2": "",

        "PageCount": "",

        "method1": "",

        "method2": ""

      },

      {

        "line": 6,

        "applyno_page": 1,

        "applyno": 6544157,

        "number1": "11",

        "itemcode1": "TT                  ",

        "itemnum1": "TT",

        "itemname1": "凝血酶时间",

        "odresult1": "",

        "cutoffvalue1": "",

        "scovalue1": "",

        "result1": "18.30",

        "refer1": "10.00～20.00",

        "hint1": "",

        "unit1": "秒",

        "highflag1": "",

        "printgroup": "A",

        "printorder": 10,

        "TestMethod": "",

        "TestMethodName": "",

        "number2": "",

        "itemcode2": "",

        "itemnum2": "",

        "itemname2": "",

        "odresult2": "",

        "cutoffvalue2": "",

        "scovalue2": "",

        "result2": "",

        "refer2": "",

        "hint2": "",

        "unit2": "",

        "highflag2": "",

        "PageCount": "",

        "method1": "",

        "method2": ""

      }

    ]

  }



调用时机：模块进行初始化后，通过报告管理模块传过来的applyno作为入参，调用该服务，查询出病人信息。

相关表结构： lis_list

代码实现：



## 标本签收

###  模块接口视图

| NO. | 接口编号 | 接口名称 | 对接人 | 说明 |
| --- | --- | --- | --- | --- |
| 1 | 101.A01 | 请求初始化数据
../ samplereg/request/getinitdata | 潘发龙 | 获取标本签收的初始化数据，该用户可以签收的执行科室/用户信息等 |
| 2 | 101.B01 | 扫条码签收\查询（批号/工号/条码）
../samplereg/request/samplereg | 潘发龙 | 通过扫条码获取条码信息，若扫的码是条码就完成签收动作（得校验条码是否已经绑定）； |
| 3 | 901.U01 | 获取护工相关信息
../usersvr/request/getworkerinfo | 潘发龙 | 获取护工信息，包含运送标本情况汇总 |
| 4 | 101.B02 | 批量签收
../samplereg/operate/batchsamplereg | 潘发龙 | 对当前列表中未签收的标本进行签收操作； |
| 5 | 101.B03 | 护工确认
../samplereg/request/workerconfirm | 潘发龙 | 扫描批号后确认标本送达；、护工在将样本送到检验科签收时，先扫码获取到一个批号的所有样本，确认无误之后，会进行一个护工确认动作，将所有样本进行一次护工确认； |
| 6 | 101.B04 | 撤销签收
../samplereg/request/cancelsamplereg | 潘发龙 | 当签收后需要取消签收动作的时候要调用该接口进行撤销签收； |
| 7 | 101.B05 | 拒绝签收
../samplereg/request/cancelsamplereg | 潘发龙 | 当签收后发现标本不合格时使用拒绝签收 |
| 8 | 202.B03 | 获取条形码信息（打印使用）
../barcodebinding/request/getsamplelist | 赵贤云 | 条码补打列表，打印条形码获取条形码列表数据 |
| 9 | 202.B04 | 获取回执单信息
../barcodebinding/request/getsamplereturn | 胡吉安 | 获取标本取报告时间 |
| 10 | 101.B06 | 设置加急或常规标本
../samplereg/request/updatemjzbz | 潘发龙 | 设置当前标本为加急或常规标本 |
| 11 | 901.B01 | 验证用户登录信息（用户名密码验证）
../usersvr/request/checkuser | 潘发龙 | 传入用户名及密码进行验证用户 |
| 12 | 901.B02 | 验证用户登录信息（扫描二维码）
../usersvr/request/checkuserbyqrcode | 潘发龙 | 扫描用户二维码，通过解析用户二维码进行登录验证 |
|  |  |  |  |  |



###  模块规范

#### 表格显示规范

| 标本采集-查找患者信息 | 标本采集-查找患者信息 | 标本采集-查找患者信息 | 标本采集-查找患者信息 | 标本采集-查找患者信息 | 标本采集-查找患者信息 | 标本采集-查找患者信息 |
| --- | --- | --- | --- | --- | --- | --- |
| 序号 | 列名 | 对应字段 | 可否编辑 | 默认列宽（px） | 编辑说明 | 标记说明 |
| 1 | 状态 | REGSTATUS | 否 | 40px |  |  |
| 2 | 条码号 | TXM | 否 | 100px |  |  |
| 3 | 姓名 | PATNAME | 否 | 80px |  |  |
| 4 | 性别 | SEXDESC | 否 | 60px |  |  |
| 5 | 年龄 | AGEDESC | 否 | 60px |  |  |
| 6 | 标本种类 | SAMPLENAME | 否 | 80px |  |  |
| 7 | 申请科室 | APPLYDEPTNAME | 否 | 100px |  |  |
| 8 | 申请医生 | APPLYDOCNANE | 否 | 80px |  |  |
| 9 | 病区 | WARDNAME | 否 | 100px |  |  |
| 10 | 床号 | BEDNO | 否 | 60px |  |  |
| 11 | 检测项目 | LISORDERNAME | 否 | 150px |  |  |
| 12 | 采样时间 | DRAWDATE | 否 | 120px |  |  |
| 13 | 临床诊断 | PHONE | 否 | 100px |  |  |
| 14 | 磁卡号 | CARDNO | 否 | 100px |  |  |
| 15 | 病员号 | HOSPNO | 否 | 100px |  |  |
| 16 | 绑定时间 | CXSJ | 否 | 100px |  |  |





###  请求初始化数据

接口说明：获取标本签收的初始化数据，该用户可以签收的执行科室/用户信息等

URL：../ samplereg/request/getinitdata

实例URL:http://192.168.11.211:8098/lis/samplereg/request/getinitdata

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：初始化数据及权限集合controlsparams类型：object | 节点名称：初始化数据及权限集合controlsparams类型：object | 节点名称：初始化数据及权限集合controlsparams类型：object | 节点名称：初始化数据及权限集合controlsparams类型：object | 节点名称：初始化数据及权限集合controlsparams类型：object |
| 1 | EXECDEPTCODE | 签收执行科室代码集合 | string | 多个时，逗号分隔；如：2301,2304 |
| 2 | EXECDEPTNAME | 签收执行科室名称集合 | String | 多个时，逗号分隔；如：检验科,内分泌实验室 |
| 3 | SAMPLETOTAL | 当日累计签收 | Number | 如：10000 |
| 4 | BATCHNOTOTAL | 当次批号数量 | Number | 此处为0，扫批号或条码时返回 |
| 5 | OP_NURSECONFIRM | 护工确认 | string | true-有护工确认的权限，护工确认操作按钮显示
false-不需要或没有护工确认的权限，不显示护工确认操作按钮 |
| 6 | OP_MULTIREG | 批量签收 | string | true-有批量签收的权限，批量签收操作按钮显示
false-不需要或没有批量签收的权限，不显示批量签收操作按钮 |
| 7 | OP_ADDORDER | 医嘱操作->增加医嘱 | string | true-有增加医嘱的权限
false-没有增加医嘱的权限 |
| 8 | OP_REJECTORDER | 医嘱操作->拒绝医嘱 | string | true-有拒绝医嘱的权限
false-没有拒绝医嘱的权限 |
| 9 | OP_CANCELORDER | 医嘱操作->撤销医嘱 | string | true-有撤销医嘱的权限
false-没有撤销医嘱的权限 |
| 10 | OP_CANCELREG | 撤销签收 | string | true-有撤销签收的权限，撤销签收菜单可用
false-没有撤销签收的权限，撤销签收菜单不可用 |
| 11 | OP_REJECTREG | 拒绝签收 | string | true-有拒绝签收的权限，拒绝签收菜单可用
false-没有拒绝签收的权限，拒绝签收菜单不可用 |
| 12 | OP_FORCEREG | 强制签收 | string | 标本存在问题时，经确认后有权限人员可以强制签收
true-有强制签收的权限，强制签收菜单可用
false-没有强制签收的权限，强制签收菜单不可用 |
| 13 | OP_SPLITTXM | 条码拆分 | string | true-有条码拆分的权限，条码拆分菜单可用
false-没有条码拆分的权限，条码拆分菜单不可用 |
| 执行科室字典 节点名称：execdeptlist类型：array <object> | 执行科室字典 节点名称：execdeptlist类型：array <object> | 执行科室字典 节点名称：execdeptlist类型：array <object> | 执行科室字典 节点名称：execdeptlist类型：array <object> | 执行科室字典 节点名称：execdeptlist类型：array <object> |
| 1 | CODE | 字典代码 | string | 字典代码，如：2110 |
| 2 | NAME | 字典名称 | string | 字典名称，如：检验科 |
| 3 | DICID | 字典ID | string | 字典ID，如：2110 |
| 4 | DICTYPE | 字典类型 | string | 字典类型，如：执行科室 |
| 5 | EXTERNCODE | 外部码 | string | HIS外部代码 |
| 6 | MEMCODE1 | 输入码一 | string | 输入码一 |
| 7 | MEMCODE2 | 输入码二 | string | 输入码二 |
| 8 | SUBSYSCODE | 系统代码 | string | 系统代码，LIS默认为LIMS |
| 9 | ORDERNO | 序号 | number | 排序序号，根据此字段来显示展示顺序 |





说明：获取可签收执行科室信息，用于后面的扫码签收

JSON返回示例：

"controlsparams":{

"EXECDEPTCODE":"2301,2302",

"EXECDEPTNAME":"检验科,内分泌实验室",

"SAMPLETOTAL":352,

"BATCHNOTOTAL":0,

.....................................

"OP_REJECTREG":"true",

"OP_FORCEREG":"false",

"OP_SPLITTXM":"false"

},

"execdeptlist":[

{

    "CODE":"2301",

    "NAME":"检验科",

    "DICID":"2301",

    "DICTYPE":"执行科室",

    "EXTERNCODE":"2301",

    "MEMCODE1":"2301",

    "MEMCODE2":"JYK",

    "SUBSYSCODE":"LIMS",

    "ORDERNO":"1"

},

{

    "CODE":"2302",

    "NAME":"内分泌实验室",

    "DICID":"2302",

    "DICTYPE":"执行科室",

    "EXTERNCODE":"2302",

    "MEMCODE1":"2302",

    "MEMCODE2":"NFMSYS",

    "SUBSYSCODE":"LIMS",

    "ORDERNO":"1"

}

]

代码实现：

表结构：SYS_DEPT\SETTINGS\SETTINGSDIC

代码实现：

获取执行科室列表，调用接口：900.A01，字典分类为：执行科室

获取签收默认执行科室参数paramcode=bbqs-zxks-qszxks，对应接口：900.A03.1

###  业务类

#### 扫条码签收\查询（批号/工号/条码）

接口说明：通过扫条码获取条码信息，若扫的码是条码就完成签收动作（得校验条码是否已经绑定）；

URL：../samplereg/request/samplereg

实例URL:http://...../lis/samplereg/request/samplereg

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | barcode | 扫描的条形码 | string | 传入的条形码，规则判断：
hgqs-yyMMdd-4位流水号：护工签收批号
wcard-userid：工号牌
其他为标本条形码 |
| 2 | execdeptcode | 执行科室集合 | string | 执行科室代码集合，多个使用逗号分隔
对应签收界面的签收科室集合，为空时表示不过滤，可签收所有科室 |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：sampleinfo   类型：object | 节点名称：sampleinfo   类型：object | 节点名称：sampleinfo   类型：object | 节点名称：sampleinfo   类型：object | 节点名称：sampleinfo   类型：object |
| 1 | CODETYPE | 出入的号码类型 | string | hgqs-护工签收批号
wcard-护工工号牌 
txm-条形码 |
| 2 | OP_ CLEAR | 清除标本列表 | string | true-清除列表后追加
false-不清除列表 |
| 节点名称：samplelist类型：array<object> | 节点名称：samplelist类型：array<object> | 节点名称：samplelist类型：array<object> | 节点名称：samplelist类型：array<object> | 节点名称：samplelist类型：array<object> |
| 1 | TXM | 条形码 | string | 样本条码 |
| 2 | REGSTATUS | 签收状态 | string | 0 -未签收 
1- 签收成功 
2- 签收失败 |
| 3 | MJZBZ | 加急标志 | string | 0-常规 
1-加急；加急时，条形码字段字体红色 |
| 4 | TAT | TAT | string | 0-正常 
1-违反TAT规则 
2-在预警时间范围内(警告)
9-违反TAT规则，但不参与统计(延迟取报告一类的) |
| 5 | BIOHAZARDFLAG | 传染病标志 | string | U-有生物危害,N-正常 |
| 6 | WRONGDEPT | 送错科室 | string | 0-未送错科室
1-送错科室 |
| 7 | PATIENTID | 病人唯一号 | string | 病人唯一号 |
| 8 | CURENO | 就诊号 | string | 就诊号 |
| 9 | PATNAME | 病人姓名 | string | 样本病人名称 |
| 10 | CARDNO | 磁卡号 | string | 磁卡号 |
| 11 | HOSPNO | 病员号 | string | 病员号 |
| 12 | SEX | 性别 | string | 1-男 
2-女 
3-未知 |
| 13 | SEXDESC | 性别描述 | string | 男、女 |
| 14 | AGE | 年龄 | string | 年龄 如：20 |
| 15 | AGEUNIT | 年龄单位 | string | 年龄单位 如 ：岁 |
| 16 | AGE2 | 年龄2 | string | 年龄   如 ：1 |
| 17 | AGEUNIT2 | 年龄单位2 | string | 年龄单位2 如：月 |
| 18 | AGEDESC | 年龄描述 | string | 年龄描述如：1岁10天 |
| 19 | BRLX | 病人类型 | string | 0-门诊
1-住院
3、4-体检 |
| 20 | WARDORREG | 病人类型代码 | string | 病人类型代码，如：0 |
| 21 | WARDORREGNAME | 病人类型名称 | string | 病人类型名称，如：门诊 |
| 22 | WARD | 病区代码 | string | 申请病区代码，如：2309 |
| 23 | WARDNAME | 病区名称 | string | 申请病区名称，如：儿一科病区 |
| 24 | BEDNO | 床号 | string | 床号 |
| 25 | APPLYDEPTCODE | 申请科室代码 | string | 如：4300 |
| 26 | APPLYDEPTNAME | 申请科室名称 | string | 如：儿一科 |
| 27 | APPLYDOCCODE | 申请医生代码 | string | 如：1302 |
| 28 | APPLYDOCNAME | 申请医生名称 | string | 如：李天华 |
| 29 | SAMPLECODE | 标本代码 | string | 如：0001 |
| 30 | SAMPLENAME | 标本名称 | string | 如：血清 |
| 31 | SAMPLEPLACE | 采集站点代码 | string | 如：0001 |
| 32 | SAMPLEPLACENAME | 采集站点名称 | string | 如：绩溪路门诊 |
| 33 | CXSJ | 绑定时间 | string | 格式：yyyy-MM-dd HH:mm:ss |
| 34 | CXRDM | 绑定人代码 | string | 如：32012 |
| 35 | CXRXM | 绑定人姓名 | string | 如：张三 |
| 36 | CXPCNAME | 绑定计算机名称 | string | 格式：客户端名称(Ip地址) |
| 37 | WORKERBATCHNO | 护工签收批号 | string | 格式：hgqs-yyMMdd-4位流水号
如：hgqs-170825-0001 |
| 38 | WORKERCODE | 护工签收工号 | string | 如：32011 |
| 39 | WORKERNAME | 护工签收姓名 | string | 如：李四 |
| 40 | WORKERDATE | 护工签收时间 | string | 格式：客户端名称(Ip地址) |
| 41 | DRAWDATE | 采样时间 | string | 格式：yyyy-MM-dd HH:mm:ss |
| 42 | DRAWUSERCODE | 采样人代码 | string | 如：32010 |
| 43 | DRAWUSERNAME | 采样人名称 | string | 如：李春天 |
| 44 | LISORDERCODE | 项目代码 | string | 多个项目用逗号分隔 |
| 45 | LISORDERNAME | 项目名称 | string | 多个项目用逗号分隔 |
| 46 | CLINICDESC | 临床诊断 | string | 临床诊断 |
| 47 | NOTES | 备注信息 | string | 绑定备注 |
| 48 | EXAMGROUPCODE | 送检小组代码 | string | 送检小组代码，如：0002 |
| 49 | EXAMGROUPNAME | 送检小组名称 | string | 送检小组，如：检验科 |
| 50 | REASON | 撤销/拒绝原因 | string | 撤销或拒绝签收原因 |
| 51 | TXMSTATUS | 条码状态 | string | 0-签收 5-绑定 1-入库 |
| 52 | TXMSTATUSDESC | 条码状态描述 | string | 已签收、已绑定、已入库 |





说明：该接口触发后返回前端数据和B03数据结构一样，接收数据之后先判断txm是否已经出现在前端，如果出现了，证明是该批号下的，用返回数据替换B03中该条样本数据；如果并未出现在BO3返回结果，那就在最前面增加一条记录；

JSON返回示例：

"sampleinfo":

{

"CODETYPE":"hgqs",

"OP_CLEAR":"true"

},

"samplelist":[

{

     "TXM":"130000834943",

"REGSTATUS":"0",

..................

"REASON":"",

"TXMSTATUS":"5",

"TXMSTATUSDESC":"已绑定"



},

{

     "TXM":"130000834942",

"REGSTATUS":"0",

..................

"REASON":"",

"TXMSTATUS":"5",

"TXMSTATUSDESC":"已绑定"

}

]

相关表结构：LIS_ORDERMASTER\LIS_ACCEPTITEMS\SLAVE\

代码实现：

解析传入号码类型，规则见定义

根据规则获取或操作数据

护工签收批号：通过批号获取标本数据返回前端，签收状态根据TXMSTATUS判断；

护工工号牌：通过护工工号信息获取当前护工未签收标本；

标本条码：判断条形码状态并执行标本签收动作，签收成功或失败均要返回标本数据；

标本签收部分代码参见LIS50部分代码

提示信息：

护工签收批号：签收批号【hgqs-170825-0002】，运送标本总量：135份，请核查标本数量；

护工工号牌：标本运送护工【李四-2309】，本次运送标本总量：32份，运送批号：【hgqs-170825-0002（23份）】，【hgqs-170825-0005（9份）】

条形码：当前标本【13000843032-患者姓名】，签收成功！



#### 批量签收

接口说明：对当前列表中未签收的标本进行签收操作；

URL：../samplereg/request/batchsamplereg

实例URL:http://...../lis/samplereg/request/batchsamplereg

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | execdeptcode | 执行科室集合 | string | 执行科室代码集合，多个使用逗号分隔
对应签收界面的签收科室集合，为空时表示不过滤，可签收所有科室 |
| 2 | userid | 用户id | string | 允许为空，空时取登录用户 |
| samplelist 类型：array<object> 见 101.B01 对应samplelist 的出参 | samplelist 类型：array<object> 见 101.B01 对应samplelist 的出参 | samplelist 类型：array<object> 见 101.B01 对应samplelist 的出参 | samplelist 类型：array<object> 见 101.B01 对应samplelist 的出参 | samplelist 类型：array<object> 见 101.B01 对应samplelist 的出参 |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 变量名 | 含义 | 类型 | 类型 | 备注说明 | 备注说明 |
| --- | --- | --- | --- | --- | --- | --- | --- |
| 节点名称：samplelist类型：array<object> | 节点名称：samplelist类型：array<object> | 节点名称：samplelist类型：array<object> | 节点名称：samplelist类型：array<object> | 节点名称：samplelist类型：array<object> | 节点名称：samplelist类型：array<object> | 节点名称：samplelist类型：array<object> | 节点名称：samplelist类型：array<object> |
| 1 | TXM | 条形码 | 条形码 | 条形码 | string | string | 样本条码 |
| 2 | REGSTATUS | 签收状态 | 签收状态 | 签收状态 | string | string | 0 -未签收 
1- 签收成功 
2- 签收失败 |
| 3 | MJZBZ | 加急标志 | 加急标志 | 加急标志 | string | string | 0-常规 
1-加急；加急时，条形码字段字体红色 |
| 4 | TAT | TAT | TAT | TAT | string | string | 0-正常 
1-违反TAT规则 
2-在预警时间范围内(警告)
9-违反TAT规则，但不参与统计(延迟取报告一类的) |
| 5 | BIOHAZARDFLAG | 传染病标志 | 传染病标志 | 传染病标志 | string | string | U-有生物危害,N-正常 |
| 6 | WRONGDEPT | 送错科室 | 送错科室 | 送错科室 | string | string | 0-未送错科室
1-送错科室 |
| 7 | PATIENTID | 病人唯一号 | 病人唯一号 | 病人唯一号 | string | string | 病人唯一号 |
| 8 | CURENO | 就诊号 | 就诊号 | 就诊号 | string | string | 就诊号 |
| 9 | PATNAME | 病人姓名 | 病人姓名 | 病人姓名 | string | string | 样本病人名称 |
| 10 | CARDNO | 磁卡号 | 磁卡号 | 磁卡号 | string | string | 磁卡号 |
| 11 | HOSPNO | 病员号 | 病员号 | 病员号 | string | string | 病员号 |
| 12 | SEX | 性别 | 性别 | 性别 | string | string | 1-男 
2-女 
3-未知 |
| 13 | SEXDESC | 性别描述 | 性别描述 | 性别描述 | string | string | 男、女 |
| 14 | AGE | 年龄 | 年龄 | 年龄 | string | string | 年龄 如：20 |
| 15 | AGEUNIT | 年龄单位 | 年龄单位 | 年龄单位 | string | string | 年龄单位 如 ：岁 |
| 16 | AGE2 | 年龄2 | 年龄2 | 年龄2 | string | string | 年龄   如 ：1 |
| 17 | AGEUNIT2 | 年龄单位2 | 年龄单位2 | 年龄单位2 | string | string | 年龄单位2 如：月 |
| 18 | AGEDESC | 年龄描述 | 年龄描述 | 年龄描述 | string | string | 年龄描述如：1岁10天 |
| 19 | BRLX | 病人类型 | 病人类型 | 病人类型 | string | string | 0-门诊
1-住院
3、4-体检 |
| 20 | WARDORREG | 病人类型代码 | 病人类型代码 | 病人类型代码 | string | string | 病人类型代码，如：0 |
| 21 | WARDORREGNAME | 病人类型名称 | 病人类型名称 | 病人类型名称 | string | string | 病人类型名称，如：门诊 |
| 22 | WARD | 病区代码 | 病区代码 | 病区代码 | string | string | 申请病区代码，如：2309 |
| 23 | WARDNAME | 病区名称 | 病区名称 | 病区名称 | string | string | 申请病区名称，如：儿一科病区 |
| 24 | BEDNO | 床号 | 床号 | 床号 | string | string | 床号 |
| 25 | APPLYDEPTCODE | 申请科室代码 | 申请科室代码 | 申请科室代码 | string | string | 如：4300 |
| 26 | APPLYDEPTNAME | 申请科室名称 | 申请科室名称 | 申请科室名称 | string | string | 如：儿一科 |
| 27 | APPLYDOCCODE | 申请医生代码 | 申请医生代码 | 申请医生代码 | string | string | 如：1302 |
| 28 | APPLYDOCNAME | 申请医生名称 | 申请医生名称 | 申请医生名称 | string | string | 如：李天华 |
| 29 | SAMPLECODE | 标本代码 | 标本代码 | 标本代码 | string | string | 如：0001 |
| 30 | SAMPLENAME | 标本名称 | 标本名称 | 标本名称 | string | string | 如：血清 |
| 31 | SAMPLEPLACE | 采集站点代码 | 采集站点代码 | 采集站点代码 | string | string | 如：0001 |
| 32 | SAMPLEPLACENAME | 采集站点名称 | 采集站点名称 | 采集站点名称 | string | string | 如：绩溪路门诊 |
| 33 | CXSJ | 绑定时间 | 绑定时间 | 绑定时间 | string | string | 格式：yyyy-MM-dd HH:mm:ss |
| 34 | CXRDM | 绑定人代码 | 绑定人代码 | 绑定人代码 | string | string | 如：32012 |
| 35 | CXRXM | 绑定人姓名 | 绑定人姓名 | 绑定人姓名 | string | string | 如：张三 |
| 36 | CXPCNAME | 绑定计算机名称 | 绑定计算机名称 | 绑定计算机名称 | string | string | 格式：客户端名称(Ip地址) |
| 37 | WORKERBATCHNO | 护工签收批号 | 护工签收批号 | 护工签收批号 | string | string | 格式：hgqs-yyMMdd-4位流水号
如：hgqs-170825-0001 |
| 38 | WORKERCODE | 护工签收工号 | 护工签收工号 | 护工签收工号 | string | string | 如：32011 |
| 39 | WORKERNAME | 护工签收姓名 | 护工签收姓名 | 护工签收姓名 | string | string | 如：李四 |
| 40 | WORKERDATE | 护工签收时间 | 护工签收时间 | 护工签收时间 | string | string | 格式：客户端名称(Ip地址) |
| 41 | DRAWDATE | 采样时间 | 采样时间 | 采样时间 | string | string | 格式：yyyy-MM-dd HH:mm:ss |
| 42 | DRAWUSERCODE | 采样人代码 | 采样人代码 | 采样人代码 | string | string | 如：32010 |
| 43 | DRAWUSERNAME | 采样人名称 | 采样人名称 | 采样人名称 | string | string | 如：李春天 |
| 44 | LISORDERCODE | 项目代码 | 项目代码 | 项目代码 | string | string | 多个项目用逗号分隔 |
| 45 | LISORDERNAME | 项目名称 | 项目名称 | 项目名称 | string | string | 多个项目用逗号分隔 |
| 46 | CLINICDESC | 临床诊断 | 临床诊断 | 临床诊断 | string | string | 临床诊断 |
| 47 | NOTES | 备注信息 | 备注信息 | 备注信息 | string | string | 绑定备注 |
| 48 | EXAMGROUPCODE | 送检小组代码 | 送检小组代码 | 送检小组代码 | string | string | 送检小组代码，如：0002 |
| 49 | EXAMGROUPNAME | 送检小组名称 | 送检小组名称 | 送检小组名称 | string | string | 送检小组，如：检验科 |
| 50 | REASON | 撤销/拒绝原因 | 撤销/拒绝原因 | 撤销/拒绝原因 | string | string | 撤销或拒绝签收原因 |
| 51 | TXMSTATUS | 条码状态 | 条码状态 | 条码状态 | string | string | 0-签收 5-绑定 1-入库 |
| 52 | TXMSTATUSDESC | 条码状态描述 | 条码状态描述 | 条码状态描述 | string | string | 已签收、已绑定、已入库 |





说明：批量签收就是将目前缓存在前端的所有样本信息全部传送到服务端。服务器对所有样本进行签收动作，并将处理结果返回到前端。

JSON返回示例：

"samplelist":[

{

     "TXM":"130000834943",

"REGSTATUS":"0",

..................

"REASON":"",

"TXMSTATUS":"5",

"TXMSTATUSDESC":"已绑定"



},

{

     "TXM":"130000834942",

"REGSTATUS":"0",

..................

"REASON":"",

"TXMSTATUS":"5",

"TXMSTATUSDESC":"已绑定"

}

]

相关表结构：LIS_ORDERMASTER\LIS_ACCEPTITEMS\SLAVE\LIS_ORDERMASTER_HISTORY\LIS_ACCEPTITEMS_HISTORY\LIS_SPECIMENFLOW

代码实现：

循环列表，未签收的标本执行签收操作，TXMSTATUS判断

签收的标本应分类整理提示信息，异常的标本及原因需要提醒给操作员，落实到每个样本；

批量操作完成后，有个汇总信息提示，提示到操作员，比如：成功和失败的数量，谁签收的；

标本签收部分代码参见LIS50部分代码；

记录签收的历史记录及标本全流程跟踪（LIS_SPECIMENFLOW），调用接口：301.B01

#### 撤销签收

接口说明：当签收后需要取消签收动作的时候要调用该接口进行撤销签收；

URL：../samplereg/request/cancelsamplereg

实例URL：http://...../lis/samplereg/request/cancelsamplereg

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | txm | 条形码 | string | 当前撤销签收的条形码 |
| 2 | reasontype | 原因分类代码 | string | 对应SLAVE.CLASSCODE=标本不合格分类’ |
| 3 | reason | 撤销原因 | string | 撤销原因 |
| 4 | disposecontent | 处理建议 | string | 处理建议 |
| 5 | examgroupcode | 工作组代码 | string | 工作组代码，如：0001 |
| 6 | examgroupname | 工作组名称 | string | 工作组名称，如：生化一组 |
| 7 | answercode | 电话接听者代码 | string | 电话接听者代码，如：2308 |
| 8 | answername | 电话接听者姓名 | string | 电话接听者形码，如：李四 |
| 9 | answertime | 通话时间 | string | 格式：yyyy-MM-dd HH:mm:ss |
| 10 | answerphone | 电话号码 | string | 电话号码，如：63257232 |
| 11 | printreceipt | 打印回执 | string | 0-不打回执
1-打印回执
打印回执时，要将数据返回给前端 |
| 12 | remark | 备注说明 | string | 允许为空 |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 变量名 | 含义 | 类型 | 类型 | 备注说明 | 备注说明 |
| --- | --- | --- | --- | --- | --- | --- | --- |
| 节点名称：sampleinfo类型：object | 节点名称：sampleinfo类型：object | 节点名称：sampleinfo类型：object | 节点名称：sampleinfo类型：object | 节点名称：sampleinfo类型：object | 节点名称：sampleinfo类型：object | 节点名称：sampleinfo类型：object | 节点名称：sampleinfo类型：object |
| 1 | TXM | 条形码 | 条形码 | 条形码 | string | string | 样本条码 |
| 2 | PATIENTID | 病人唯一号 | 病人唯一号 | 病人唯一号 | string | string | 病人唯一号 |
| 3 | CURENO | 就诊号 | 就诊号 | 就诊号 | string | string | 就诊号 |
| 4 | PATNAME | 病人姓名 | 病人姓名 | 病人姓名 | string | string | 样本病人名称 |
| 5 | CARDNO | 磁卡号 | 磁卡号 | 磁卡号 | string | string | 磁卡号 |
| 6 | HOSPNO | 病员号 | 病员号 | 病员号 | string | string | 病员号 |
| 7 | SEX | 性别 | 性别 | 性别 | string | string | 1-男 
2-女 
3-未知 |
| 8 | SEXDESC | 性别描述 | 性别描述 | 性别描述 | string | string | 男、女 |
| 9 | AGE | 年龄 | 年龄 | 年龄 | string | string | 年龄 如：20 |
| 10 | AGEUNIT | 年龄单位 | 年龄单位 | 年龄单位 | string | string | 年龄单位 如 ：岁 |
| 11 | AGE2 | 年龄2 | 年龄2 | 年龄2 | string | string | 年龄   如 ：1 |
| 12 | AGEUNIT2 | 年龄单位2 | 年龄单位2 | 年龄单位2 | string | string | 年龄单位2 如：月 |
| 13 | AGEDESC | 年龄描述 | 年龄描述 | 年龄描述 | string | string | 年龄描述如：1岁10天 |
| 14 | BRLX | 病人类型 | 病人类型 | 病人类型 | string | string | 0-门诊
1-住院
3、4-体检 |
| 15 | WARDORREG | 病人类型代码 | 病人类型代码 | 病人类型代码 | string | string | 病人类型代码，如：0 |
| 16 | WARDORREGNAME | 病人类型名称 | 病人类型名称 | 病人类型名称 | string | string | 病人类型名称，如：门诊 |
| 17 | WARD | 病区代码 | 病区代码 | 病区代码 | string | string | 申请病区代码，如：2309 |
| 18 | WARDNAME | 病区名称 | 病区名称 | 病区名称 | string | string | 申请病区名称，如：儿一科病区 |
| 19 | BEDNO | 床号 | 床号 | 床号 | string | string | 床号 |
| 20 | APPLYDEPTCODE | 申请科室代码 | 申请科室代码 | 申请科室代码 | string | string | 如：4300 |
| 21 | APPLYDEPTNAME | 申请科室名称 | 申请科室名称 | 申请科室名称 | string | string | 如：儿一科 |
| 22 | APPLYDOCCODE | 申请医生代码 | 申请医生代码 | 申请医生代码 | string | string | 如：1302 |
| 23 | APPLYDOCNAME | 申请医生名称 | 申请医生名称 | 申请医生名称 | string | string | 如：李天华 |
| 24 | SAMPLECODE | 标本代码 | 标本代码 | 标本代码 | string | string | 如：0001 |
| 25 | SAMPLENAME | 标本名称 | 标本名称 | 标本名称 | string | string | 如：血清 |
| 26 | SAMPLEPLACE | 采集站点代码 | 采集站点代码 | 采集站点代码 | string | string | 如：0001 |
| 27 | SAMPLEPLACENAME | 采集站点名称 | 采集站点名称 | 采集站点名称 | string | string | 如：绩溪路门诊 |
| 28 | CXSJ | 绑定时间 | 绑定时间 | 绑定时间 | string | string | 格式：yyyy-MM-dd HH:mm:ss |
| 29 | CXRDM | 绑定人代码 | 绑定人代码 | 绑定人代码 | string | string | 如：32012 |
| 30 | CXRXM | 绑定人姓名 | 绑定人姓名 | 绑定人姓名 | string | string | 如：张三 |
| 31 | CXPCNAME | 绑定计算机名称 | 绑定计算机名称 | 绑定计算机名称 | string | string | 格式：客户端名称(Ip地址) |
| 32 | WORKERBATCHNO | 护工签收批号 | 护工签收批号 | 护工签收批号 | string | string | 格式：hgqs-yyMMdd-4位流水号
如：hgqs-170825-0001 |
| 33 | WORKERCODE | 护工签收工号 | 护工签收工号 | 护工签收工号 | string | string | 如：32011 |
| 34 | WORKERNAME | 护工签收姓名 | 护工签收姓名 | 护工签收姓名 | string | string | 如：李四 |
| 35 | WORKERDATE | 护工签收时间 | 护工签收时间 | 护工签收时间 | string | string | 格式：客户端名称(Ip地址) |
| 36 | DRAWDATE | 采样时间 | 采样时间 | 采样时间 | string | string | 格式：yyyy-MM-dd HH:mm:ss |
| 37 | DRAWUSERCODE | 采样人代码 | 采样人代码 | 采样人代码 | string | string | 如：32010 |
| 38 | DRAWUSERNAME | 采样人名称 | 采样人名称 | 采样人名称 | string | string | 如：李春天 |
| 39 | LISORDERCODE | 项目代码 | 项目代码 | 项目代码 | string | string | 多个项目用逗号分隔 |
| 40 | LISORDERNAME | 项目名称 | 项目名称 | 项目名称 | string | string | 多个项目用逗号分隔 |
| 41 | CLINICDESC | 临床诊断 | 临床诊断 | 临床诊断 | string | string | 临床诊断 |
| 42 | NOTES | 备注信息 | 备注信息 | 备注信息 | string | string | 绑定备注 |
| 43 | TXMSTATUS | 条码状态 | 条码状态 | 条码状态 | string | string | 0-签收 5-绑定 1-入库 |
| 44 | TXMSTATUSDESC | 条码状态描述 | 条码状态描述 | 条码状态描述 | string | string | 已签收、已绑定、已入库 |
| 45 | REASON | 撤销/拒绝原因 | 撤销/拒绝原因 | 撤销/拒绝原因 | string | string | 撤销或拒绝签收原因 |
| 46 | REASONTYPE | 原因分类代码 | 原因分类代码 | 原因分类代码 | string | string | 原因分类代码，如：1001 |
| 47 | REASONTYPENAME | 原因分类名称 | 原因分类名称 | 原因分类名称 | string | string | 原因分类名称，如：标本类型错误 |
| 48 | EXAMGROUPCODE | 工作组代码 | 工作组代码 | 工作组代码 | string | string | 送检小组代码，如：0002 |
| 49 | EXAMGROUPNAME | 工作组名称 | 工作组名称 | 工作组名称 | string | string | 送检小组，如：检验科 |
| 50 | ANSWERCODE | 接听者代码 | 接听者代码 | 接听者代码 | string | string | 电话接听者代码。如：2308 |
| 51 | ANSWERNAME | 接听者姓名 | 接听者姓名 | 接听者姓名 | string | string | 电话接听者姓名，如：李四 |
| 52 | ANSWERTIME | 通话时间 | 通话时间 | 通话时间 | string | string | 格式：yyyy-MM-dd HH:mm:ss |
| 53 | ANSWERPHONE | 电话号码 | 电话号码 | 电话号码 | string | string | 电话号码，如：63823232 |
| 54 | REMARK | 备注说明 | 备注说明 | 备注说明 | string | string | 备注说明 |
| 55 | OPERATE | 操作描述 | 操作描述 | 操作描述 | string | string | 操作：撤销签收、拒绝签收 |



说明：撤销成功之后返回该样本处理状态及提示信息，如需打印回执单，则返回标本数据，否则前端匹配到该标本条码，并移除掉；

JSON返回示例：

"sampleinfo":

{

     "TXM":"130000834943",

..................

"ANSWERPHONE":"",

"REMARK":"5",

"OPERATE":"撤销签收"

}

相关表结构：LIS_ORDERMASTER\LIS_ACCEPTITEMS\SLAVE\LIS_ORDERMASTER_HISTORY\LIS_ACCEPTITEMS_HISTORY\LIS_SPECIMENFLOW\LIS_SPECIMENRECORD

代码实现：

对当前标本进行撤销签收动作，住院标本需调用HIS接口部分；

撤销签收需填写原因和原因分类；

撤销签收详细信息记录到LIS_SPECIMENRECORD,标本流程记录到LIS_SPECIMENFLOW；

LIS_ORDERMASTER记录到FAILFLAG，并保存修改记录到LIS_ORDERMASTER_HISTORY；

#### 拒绝签收

接口说明：当签收后发现标本不合格时使用拒绝签收；

URL：../ samplereg/request/refusesamplereg

实例URL：http://...../lis/samplereg/request/refusesamplereg

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | txm | 条形码 | string | 当前拒绝签收的条形码 |
| 2 | reasontype | 原因分类代码 | string | 对应SLAVE.CLASSCODE=标本不合格分类’ |
| 3 | reason | 拒绝原因 | string | 拒绝原因 |
| 4 | disposecontent | 处理建议 | string | 处理建议 |
| 5 | examgroupcode | 工作组代码 | string | 工作组代码，如：0001 |
| 6 | examgroupname | 工作组名称 | string | 工作组名称，如：生化一组 |
| 7 | answercode | 电话接听者代码 | string | 电话接听者代码，如：2308 |
| 8 | answername | 电话接听者姓名 | string | 电话接听者形码，如：李四 |
| 9 | answertime | 通话时间 | string | 格式：yyyy-MM-dd HH:mm:ss |
| 10 | answerphone | 电话号码 | string | 电话号码，如：63257232 |
| 11 | printreceipt | 打印回执 | string | 0-不打回执
1-打印回执
打印回执时，要将数据返回给前端 |
| 12 | remark | 备注说明 | string | 允许为空 |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 变量名 | 含义 | 类型 | 类型 | 备注说明 | 备注说明 |
| --- | --- | --- | --- | --- | --- | --- | --- |
| 节点名称：sampleinfo类型：object | 节点名称：sampleinfo类型：object | 节点名称：sampleinfo类型：object | 节点名称：sampleinfo类型：object | 节点名称：sampleinfo类型：object | 节点名称：sampleinfo类型：object | 节点名称：sampleinfo类型：object | 节点名称：sampleinfo类型：object |
| 1 | TXM | 条形码 | 条形码 | 条形码 | string | string | 样本条码 |
| 2 | PATIENTID | 病人唯一号 | 病人唯一号 | 病人唯一号 | string | string | 病人唯一号 |
| 3 | CURENO | 就诊号 | 就诊号 | 就诊号 | string | string | 就诊号 |
| 4 | PATNAME | 病人姓名 | 病人姓名 | 病人姓名 | string | string | 样本病人名称 |
| 5 | CARDNO | 磁卡号 | 磁卡号 | 磁卡号 | string | string | 磁卡号 |
| 6 | HOSPNO | 病员号 | 病员号 | 病员号 | string | string | 病员号 |
| 7 | SEX | 性别 | 性别 | 性别 | string | string | 1-男 
2-女 
3-未知 |
| 8 | SEXDESC | 性别描述 | 性别描述 | 性别描述 | string | string | 男、女 |
| 9 | AGE | 年龄 | 年龄 | 年龄 | string | string | 年龄 如：20 |
| 10 | AGEUNIT | 年龄单位 | 年龄单位 | 年龄单位 | string | string | 年龄单位 如 ：岁 |
| 11 | AGE2 | 年龄2 | 年龄2 | 年龄2 | string | string | 年龄   如 ：1 |
| 12 | AGEUNIT2 | 年龄单位2 | 年龄单位2 | 年龄单位2 | string | string | 年龄单位2 如：月 |
| 13 | AGEDESC | 年龄描述 | 年龄描述 | 年龄描述 | string | string | 年龄描述如：1岁10天 |
| 14 | BRLX | 病人类型 | 病人类型 | 病人类型 | string | string | 0-门诊
1-住院
3、4-体检 |
| 15 | WARDORREG | 病人类型代码 | 病人类型代码 | 病人类型代码 | string | string | 病人类型代码，如：0 |
| 16 | WARDORREGNAME | 病人类型名称 | 病人类型名称 | 病人类型名称 | string | string | 病人类型名称，如：门诊 |
| 17 | WARD | 病区代码 | 病区代码 | 病区代码 | string | string | 申请病区代码，如：2309 |
| 18 | WARDNAME | 病区名称 | 病区名称 | 病区名称 | string | string | 申请病区名称，如：儿一科病区 |
| 19 | BEDNO | 床号 | 床号 | 床号 | string | string | 床号 |
| 20 | APPLYDEPTCODE | 申请科室代码 | 申请科室代码 | 申请科室代码 | string | string | 如：4300 |
| 21 | APPLYDEPTNAME | 申请科室名称 | 申请科室名称 | 申请科室名称 | string | string | 如：儿一科 |
| 22 | APPLYDOCCODE | 申请医生代码 | 申请医生代码 | 申请医生代码 | string | string | 如：1302 |
| 23 | APPLYDOCNAME | 申请医生名称 | 申请医生名称 | 申请医生名称 | string | string | 如：李天华 |
| 24 | SAMPLECODE | 标本代码 | 标本代码 | 标本代码 | string | string | 如：0001 |
| 25 | SAMPLENAME | 标本名称 | 标本名称 | 标本名称 | string | string | 如：血清 |
| 26 | SAMPLEPLACE | 采集站点代码 | 采集站点代码 | 采集站点代码 | string | string | 如：0001 |
| 27 | SAMPLEPLACENAME | 采集站点名称 | 采集站点名称 | 采集站点名称 | string | string | 如：绩溪路门诊 |
| 28 | CXSJ | 绑定时间 | 绑定时间 | 绑定时间 | string | string | 格式：yyyy-MM-dd HH:mm:ss |
| 29 | CXRDM | 绑定人代码 | 绑定人代码 | 绑定人代码 | string | string | 如：32012 |
| 30 | CXRXM | 绑定人姓名 | 绑定人姓名 | 绑定人姓名 | string | string | 如：张三 |
| 31 | CXPCNAME | 绑定计算机名称 | 绑定计算机名称 | 绑定计算机名称 | string | string | 格式：客户端名称(Ip地址) |
| 32 | WORKERBATCHNO | 护工签收批号 | 护工签收批号 | 护工签收批号 | string | string | 格式：hgqs-yyMMdd-4位流水号
如：hgqs-170825-0001 |
| 33 | WORKERCODE | 护工签收工号 | 护工签收工号 | 护工签收工号 | string | string | 如：32011 |
| 34 | WORKERNAME | 护工签收姓名 | 护工签收姓名 | 护工签收姓名 | string | string | 如：李四 |
| 35 | WORKERDATE | 护工签收时间 | 护工签收时间 | 护工签收时间 | string | string | 格式：客户端名称(Ip地址) |
| 36 | DRAWDATE | 采样时间 | 采样时间 | 采样时间 | string | string | 格式：yyyy-MM-dd HH:mm:ss |
| 37 | DRAWUSERCODE | 采样人代码 | 采样人代码 | 采样人代码 | string | string | 如：32010 |
| 38 | DRAWUSERNAME | 采样人名称 | 采样人名称 | 采样人名称 | string | string | 如：李春天 |
| 39 | LISORDERCODE | 项目代码 | 项目代码 | 项目代码 | string | string | 多个项目用逗号分隔 |
| 40 | LISORDERNAME | 项目名称 | 项目名称 | 项目名称 | string | string | 多个项目用逗号分隔 |
| 41 | CLINICDESC | 临床诊断 | 临床诊断 | 临床诊断 | string | string | 临床诊断 |
| 42 | NOTES | 备注信息 | 备注信息 | 备注信息 | string | string | 绑定备注 |
| 43 | TXMSTATUS | 条码状态 | 条码状态 | 条码状态 | string | string | 0-签收 5-绑定 1-入库 |
| 44 | TXMSTATUSDESC | 条码状态描述 | 条码状态描述 | 条码状态描述 | string | string | 已签收、已绑定、已入库 |
| 45 | REASON | 撤销/拒绝原因 | 撤销/拒绝原因 | 撤销/拒绝原因 | string | string | 撤销或拒绝签收原因 |
| 46 | REASONTYPE | 原因分类代码 | 原因分类代码 | 原因分类代码 | string | string | 原因分类代码，如：1001 |
| 47 | REASONTYPENAME | 原因分类名称 | 原因分类名称 | 原因分类名称 | string | string | 原因分类名称，如：标本类型错误 |
| 48 | EXAMGROUPCODE | 工作组代码 | 工作组代码 | 工作组代码 | string | string | 送检小组代码，如：0002 |
| 49 | EXAMGROUPNAME | 工作组名称 | 工作组名称 | 工作组名称 | string | string | 送检小组，如：检验科 |
| 50 | ANSWERCODE | 接听者代码 | 接听者代码 | 接听者代码 | string | string | 电话接听者代码。如：2308 |
| 51 | ANSWERNAME | 接听者姓名 | 接听者姓名 | 接听者姓名 | string | string | 电话接听者姓名，如：李四 |
| 52 | ANSWERTIME | 通话时间 | 通话时间 | 通话时间 | string | string | 格式：yyyy-MM-dd HH:mm:ss |
| 53 | ANSWERPHONE | 电话号码 | 电话号码 | 电话号码 | string | string | 电话号码，如：63823232 |
| 54 | REMARK | 备注说明 | 备注说明 | 备注说明 | string | string | 备注说明 |
| 55 | OPERATE | 操作描述 | 操作描述 | 操作描述 | string | string | 操作：撤销签收、拒绝签收 |



说明：拒绝成功之后返回该样本处理状态及提示信息，如需打印回执单，则返回标本数据，否则前端匹配到该标本条码，并移除掉；

JSON返回示例：

"sampleinfo":

{

     "TXM":"130000834943",

..................

"ANSWERPHONE":"",

"REMARK":"5",

"OPERATE":"拒绝签收"

}

相关表结构：LIS_ORDERMASTER\LIS_ACCEPTITEMS\SLAVE\LIS_ORDERMASTER_HISTORY\LIS_ACCEPTITEMS_HISTORY\LIS_SPECIMENFLOW\LIS_SPECIMENRECORD

代码实现：

对当前标本进行拒绝签收动作，住院标本需调用HIS接口部分；

拒绝签收需填写原因和原因分类；

拒绝签收详细信息记录到LIS_SPECIMENRECORD,标本流程记录到LIS_SPECIMENFLOW；

LIS_ORDERMASTER记录到FAILFLAG，并保存修改记录到LIS_ORDERMASTER_HISTORY；

#### 设置加急/常规标本

接口说明：设置当前标本为加急或常规标本

URL：../samplereg/request/updatemjzbz

实例URL:http://...../lis/samplereg/request/updatemjzbz

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | txm | 标本条码 | string | 标本条形码，如：13000238832 |
| 2 | mjzbz | 加急标记 | string | 0-常规
1-加急 |



接口出参【ResposeMessage】：说明：修改当前样本的门急诊标志

相关表结构：LIS_ORDERMASTER\LIS_ACCEPTITEMS\LIS_ORDERMASTER_HISTORY\LIS_ACCEPTITEMS_HISTORY\LIS_SPECIMENFLOW

代码实现：

设置当前标本为加急或常规标本；

操作记录记录到LIS_SPECIMENFLOW,备注说明：将标本由【常规标本】调整为【加急标本】；

#### 护工确认

接口说明：扫描批号后确认标本送达；、护工在将样本送到检验科签收时，先扫码获取到一个批号的所有样本，确认无误之后，会进行一个护工确认动作，将所有样本进行一次护工确认；

URL：../samplereg/request/workerconfirm

实例URL:http://...../lis/samplereg/request/workerconfirm

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | workercode | 护工代码 | string | 护工代码，如：2308 |
| 2 | workername | 护工姓名 | string | 护工姓名，如：李四 |
| 3 | verifymode | 验证模式 | string | 验证方式
UP-用户密码验证
QR-二维码验证 |
| 4 | workerplate | 护工二维码 | string | 说明是扫描二维码验证/或扫描工号牌 |
| samplelist 类型：array<object> 见 101.B01 对应samplelist 的出参 | samplelist 类型：array<object> 见 101.B01 对应samplelist 的出参 | samplelist 类型：array<object> 见 101.B01 对应samplelist 的出参 | samplelist 类型：array<object> 见 101.B01 对应samplelist 的出参 | samplelist 类型：array<object> 见 101.B01 对应samplelist 的出参 |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- | --- |
| 节点名称：messagelist   类型：array<object>  Type=FAILT的时候才返回 | 节点名称：messagelist   类型：array<object>  Type=FAILT的时候才返回 | 节点名称：messagelist   类型：array<object>  Type=FAILT的时候才返回 | 节点名称：messagelist   类型：array<object>  Type=FAILT的时候才返回 | 节点名称：messagelist   类型：array<object>  Type=FAILT的时候才返回 | 节点名称：messagelist   类型：array<object>  Type=FAILT的时候才返回 |
| 1 | MESSAGE | MESSAGE | 提示信息 | string | 报告没有检测项目 |
| 2 | TYPE | TYPE | 消息类型 | string | ERROR-错误
INFO-提示信息
WARN-警告信息
CONFIRM-确认信息
VERIFY-验证信息 |
| 节点名称：ResposeMessage.message 消息汇总信息 | 节点名称：ResposeMessage.message 消息汇总信息 | 节点名称：ResposeMessage.message 消息汇总信息 | 节点名称：ResposeMessage.message 消息汇总信息 | 节点名称：ResposeMessage.message 消息汇总信息 | 节点名称：ResposeMessage.message 消息汇总信息 |



JSON返回示例：

"messagelist":[

{

"MESSAGE":"报告没有检测项目",

"TYPE":"INFO"

},

{

     "MESSAGE":"报告没有检测项目",

"TYPE":"INFO"

}

]

相关表结构：LIS_ORDERMASTER\LIS_ORDERMASTER_HISTORY

代码实现：

只确认由自己签收运送的标本，如果不是本人签收，则不允许确认，提示操作员；

记录确认标志信息到LIS_ORDERMASTER并记录历史信息；

记录验证方式：密码验证还是二维码验证，表字段扩展：

[WORKERPLATE2]	[VARCHAR](255)	NULL,   --护工工号牌扫描 未扫描工号牌则为空

## 标本入库

### 模块接口视图

| NO. | 接口编号 | 接口名称 | 对接人 | 说明 |
| --- | --- | --- | --- | --- |
| 1 | 102.A01 | 请求初始化数据
../ sampleinput/request/getinitdata | 胡吉安 | 获取默认仪器列表，用于用户选择仪器，获取对应的条形码信息 |
| 2 | 102.B01 | 获取仪器对应的收费项目
../sampleinput/request/getinstorderinfo | s_xd | 获取仪器对应的收费项目和入库方案 |
| 3 | 102.B02 | B02 标本查询
../sampleinput/request/querysamplelist | s_xd | 根据不同条件查询标本列表 |
| 4 | 102.B03 | B03 扫条码入库
../sampleinput/request/insertsampleinfo | s_xd | 扫描条码入库服务 |
| 5 | 102.B04 | B04 预约排号生成预排号标本列表
../sampleinput/request/pregeneratesamplelist | s_xd | 预约排号生成预排号标本列表 |
| 6 | 102.B05 | B05 排号入库
../sampleinput/request/prebatchinsertsampleinfo | s_xd | 预约排号入库服务 |
| 7 | 102.B06 | B06 根据标本获取标本对应的收费项目信息
../sampleinput/request/getsampleoforderinfo | s_xd | 根据标本信息获取对应的收费项目信息 |
| 8 | 102.B07 | B07 撤销入库
../sampleinput/request/cancelinputsample | s_xd | 撤销入库 |
| 9 | 102.B08 | B08 修改标本种类
../sampleinput/request/changesampletype | s_xd | 修改标本种类 |
| 10 | 102.B09 | B09 获取仪器收费项目及入库方案
../sampleinput/request/getinstorderinfo | s_xd | 获取仪器收费项目及入库方案 |
| 11 | 102.B10 | B010 根据入库方案ID获取方案明细
../sampleinput/request/getinstschemedetail | s_xd | 根据入库方案ID获取方案明细 |
| 12 | 102.B11 | B11 保存入库方案
../sampleinput/request/savescheme | s_xd | 保存入库方案（增删改） |
|  |  |  |  |  |







###  初始化数据

接口说明：获取默认仪器列表，用于用户选择仪器，获取对应的条形码信息

请求URL：../sampleinput/request/getinitdata

代码文件：winning.lis.sampleinput.service.SampleInputService

示例URL：

http://192.168.14.253:8080/lis/sampleinput/request/getinitdata?subsyscode=lims&hospitalcode=9999

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：初始化仪器集合instlist  类型：array<object> | 节点名称：初始化仪器集合instlist  类型：array<object> | 节点名称：初始化仪器集合instlist  类型：array<object> | 节点名称：初始化仪器集合instlist  类型：array<object> | 节点名称：初始化仪器集合instlist  类型：array<object> |
| 1 | CODE | 字典代码 | string | 字典代码，如：KX-21 |
| 2 | NAME | 字典名称 | string | 字典名称，如：KX-21 |
| 3 | DICID | 字典ID | string | 字典ID，如：1001 |
| 4 | DICTYPE | 字典类型 | string | 字典类型，如：生化 |
| 5 | EXTERNCODE | 外部码 | string | 外部码，如：KX-21 |
| 6 | MEMCODE1 | 输入码一 | string | 输入码一 |
| 7 | MEMCODE2 | 输入码二 | string | 输入码二 |
| 8 | SUBSYSCODE | 系统代码 | string | 系统代码，LIS默认为LIMS |
| 9 | ICONCLASS | 图标类型 | string | 可为空 |
| 10 | ORDERNO | 序号 | number | 排序序号，根据此字段来显示展示顺序 |
| 节点名称：模块权限集合controlsparams类型：object | 节点名称：模块权限集合controlsparams类型：object | 节点名称：模块权限集合controlsparams类型：object | 节点名称：模块权限集合controlsparams类型：object | 节点名称：模块权限集合controlsparams类型：object |
| 1 | DEFTIMETYPE | 默认时间类型 | string | 参照时间，默认为2：签收时间
参数：bbrk-mrcx-cxfs

0：采样时间 
1：绑定时间 
2：签收时间 |
| 2 | DEFOPTTYPE（作废） | 默认操作类型 | string | 扫条码默认的动作，默认为1-条码入库
参数：bbrk-mrcs-tmdzlx

0：条码查询 
1：条码入库 |
| 3 | DEFSORTCODE | 默认排序字段 | string | 排号规则代码
对应参数：bbrk-mrcs-yphfa |
| 6 | OP_BATCHRK | 批量入库 | Boolean | true-有批量入库的权限，批量入库操作按钮显示
false-没有批量入库的权限，不显示批量入库操作按钮 |
| 7 | OP_CANCELRK | 撤销入库 | Boolean | true-有撤销入库的权限，撤销入库操作按钮显示
false-没有撤销入库的权限，不显示撤销入库操作按钮 |
| 8 | OP_MODSAMPLE | 修改标本种类 | Boolean | true-有修改标本种类的权限，修改标本操作按钮显示
false-没有修改标本种类的权限，不显示修改标本操作按钮 |
| 9 | OP_PRETECHNO | 预排号 | Boolean | true-有预排号的权限，预排号操作按钮显示
false-没有预排号的权限，不显示预排号操作按钮 |
| 10 | OP_REFUSEREG | 不合格标本登记 | Boolean | true-有拒绝签收的权限，拒绝签收操作按钮显示
false-没有拒绝签收的权限，不显示拒绝签收操作按钮 |
| 11 | OP_TXMPRINT | 条码补打 | Boolean | true-有条码补打的权限，条码补打操作按钮显示
false-没有条码补打的权限，不显示条码补打操作按钮 |



后台返回示例：



JSON返回示例：

“instlist”: [

{

    "CODE":"KX-21",

    "NAME":"KX-21",

    "DICID":"1001",

    "DICTYPE":"生化",

    "EXTERNCODE":"1001",

    "MEMCODE1":"1001",

    "MEMCODE2":"KX-21",

    "SUBSYSCODE":"LIMS",

    "ICONCLASS":"",

    "ORDERNO":"1"

},

{

    "CODE":"AU5800",

    "NAME":"AU5800",

    "DICID":"1002",

    "DICTYPE":"生化",

    "EXTERNCODE":"1002",

    "MEMCODE1":"1002",

    "MEMCODE2":"AU5800",

    "SUBSYSCODE":"LIMS",

    "ICONCLASS":"",

    "ORDERNO":"2"

}

],

controlsparams:{

“DEFQUERYTYPE”:2,

“DEFOPTTYPE”:1,

“DEFSORTCODE”:”0001’,

“OP_BATCHRK”:true,

“OP_CANCELRK”:true,

......

“OP_TXMPRINT”:true



}

代码实现：

表结构：LIS_INSTRUMENT\LIS_EXAMCLASS\参数表

代码实现：

停用仪器不能获取出来

只能获取用户有权限的仪器(待框架设计处理）

参考SQL：

SELECT A.HOSPITALCODE,

       A.SUBSYSCODE,

       A.INSTID,

       A.INSTCODE,

       A.INSTNAME,

       B.EXAMNAME  AS DICTYPE

FROM   LIS_INSTRUMENT A

LEFT JOIN LIS_EXAMCLASS B  ON  A.EXAMCODE = B.EXAMCODE 

WHERE  A.STOPFLAG = 0

### 业务类

枚举说明：用于过滤汇总过信息

| 代码 | 名称 |  |
| --- | --- | --- |
| all | 全部 |  |
| input | 已入库 |  |
| uninput | 待入库 |  |
| emergency | 急诊、加急 |  |
| biohazard | 生化危机的、传染病 |  |



#### 根据仪器+日期获取当前条件下标本汇总信息

接口说明：根据仪器+时间获取当前条件下标本汇总信息

使用场景：切换仪器、时间时触发;得到汇总信息数组后，可动态生成汇总信息。

服务返回数组，可以v-for生成多个类型的汇总信息

请求URL：../sampleinput/request/getsamplesummaryinfo

示例URL：

原型参考：





接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | instid | 仪器ID | string | 传入当前仪器的ID |
| 2 | exectime | 检测日期 | string | yyyy-MM-dd |



接口出参【ResposeMessage.data->】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：summaryinfo   类型：array<object> 汇总信息，用作汇总显示 | 节点名称：summaryinfo   类型：array<object> 汇总信息，用作汇总显示 | 节点名称：summaryinfo   类型：array<object> 汇总信息，用作汇总显示 | 节点名称：summaryinfo   类型：array<object> 汇总信息，用作汇总显示 | 节点名称：summaryinfo   类型：array<object> 汇总信息，用作汇总显示 |
|  | keycode | 键代码 |  | 枚举：all-全部、input-入库、uninput-待入库、emergency-加急、biohazard-传染病 |
|  | keyname | 名称 | string | 全部、入库、待入库、加急、传染病 |
|  | number | 数量 | number |  |
|  | orderno | 序号 | int |  |









#### 标本查询

接口说明：查询标本列表，根据查询条件，将查询的列表显示到列表

使用场景：

点击汇总图标时：type=汇总类型，即汇总信息中的keycode

请求URL：../sampleinput/request/querysamplelist

示例URL：

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | instid | 仪器ID | string | 传入当前仪器的ID |
| 2 | exectime | 检测日期 | string | yyyy-MM-dd |
| 3 | type | 类型类型 | string | 见枚举部分 |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- | --- |
| 节点名称：summaryinfo   类型：object 汇总信息，用作汇总显示 | 节点名称：summaryinfo   类型：object 汇总信息，用作汇总显示 | 节点名称：summaryinfo   类型：object 汇总信息，用作汇总显示 | 节点名称：summaryinfo   类型：object 汇总信息，用作汇总显示 | 节点名称：summaryinfo   类型：object 汇总信息，用作汇总显示 | 节点名称：summaryinfo   类型：object 汇总信息，用作汇总显示 |
|  | total | 总数 | 总数 |  |  |
| 节点名称：samplelist   类型：array<object>：标本列表，用于列表展示,做前端分页，不做后端分页 | 节点名称：samplelist   类型：array<object>：标本列表，用于列表展示,做前端分页，不做后端分页 | 节点名称：samplelist   类型：array<object>：标本列表，用于列表展示,做前端分页，不做后端分页 | 节点名称：samplelist   类型：array<object>：标本列表，用于列表展示,做前端分页，不做后端分页 | 节点名称：samplelist   类型：array<object>：标本列表，用于列表展示,做前端分页，不做后端分页 | 节点名称：samplelist   类型：array<object>：标本列表，用于列表展示,做前端分页，不做后端分页 |
| 1 | TXM | TXM | 条形码 | string | 条形码，如：13000384934 |
| 4 | RKSTATUS | RKSTATUS | 入库状态 | string | 0 -待入库 
1- 入库成功 
2- 入库失败 |
| 5 | MJZBZ | MJZBZ | 加急标志 | string | 0-常规 
1-加急；加急时，条形码字段字体红色 |
| 6 | TAT | TAT | TAT | string | 0-正常 
1-违反TAT规则 
2-在预警时间范围内(警告)
9-违反TAT规则，但不参与统计(延迟取报告一类的) |
| 7 | BIOHAZARDFLAG | 传染病标志 | 传染病标志 | string | U-有生物危害,N-正常 |
| 8 | UPDATEDRAWDATE | 是否更新采样时间 | 是否更新采样时间 | string | 0-未更新采样时间
1-已更新采样时间 |
| 9 | INSTMARK | 其他仪器标志 | 其他仪器标志 | string | 0-无其他仪器检测的项目
1-存在其他仪器检测的项目 |
| 10 | APPLYNO | 报告单号 | 报告单号 | string | 报告单号，未入库默认为-2 |
| 11 | INSTID | 仪器ID | 仪器ID | string | 仪器ID |
| 12 | INSTCODE | 仪器代码 | 仪器代码 | string | 仪器代码 |
| 13 | INSTNAME | 仪器名称 | 仪器名称 | string | 仪器名称 |
| 14 | TECHNO | 样本号 | 样本号 | string | 由系统生成 |
| 15 | PATIENTID | 病人id | 病人id | string | 病人唯一号 |
| 16 | CURENO | 病员号 | 病员号 | string | 就诊号 |
| 17 | CARDNO | 磁卡号 | 磁卡号 | string | 磁卡号 |
| 18 | HOSPNO | 就诊号 | 就诊号 | string | 住院号
Lis_ordermaster表中的HOSPNO |
| 19 | PATNAME | 病人名称 | 病人名称 | string | 病人姓名，如：张三 |
| 20 | SEXDESC | 性别描述 | 性别描述 | string | 性别，如：男、女、未知 |
| 21 | AGEDESC | 年龄描述 | 年龄描述 | string | 年龄描述 |
| 22 | BRLX | 病人类型 | 病人类型 | string | 0-门诊
1-住院
3、4-体检 |
| 23 | WARDORREG | 病人类型代码 | 病人类型代码 | string | 病人类型代码，如：0 |
| 24 | WARDORREGNAME | 病人类型名称 | 病人类型名称 | string | 病人类型名称，如：门诊 |
| 25 | WARD | 病区代码 | 病区代码 | string | 申请病区代码，如：2309 |
| 26 | WARDNAME | 病区名称 | 病区名称 | string | 申请病区名称，如：儿一科病区 |
| 27 | BEDNO | 床号 | 床号 | string | 床号 |
| 28 | APPLYDEPTCODE | 申请科室代码 | 申请科室代码 | string | 如：4300 |
| 29 | APPLYDEPTNAME | 申请科室名称 | 申请科室名称 | string | 如：儿一科 |
| 30 | APPLYDOCCODE | 申请医生代码 | 申请医生代码 | string | 如：1302 |
| 31 | APPLYDOCNAME | 申请医生名称 | 申请医生名称 | string | 如：李天华 |
| 32 | SAMPLECODE | 标本代码 | 标本代码 | string | 如：0001 |
| 33 | SAMPLENAME | 标本名称 | 标本名称 | string | 如：血清 |
| 34 | SAMPLEPLACE | 采集站点代码 | 采集站点代码 | string | 如：0001 |
| 35 | SAMPLEPLACENAME | 采集站点名称 | 采集站点名称 | string | 如：绩溪路门诊 |
| 36 | CXSJ | 绑定时间 | 绑定时间 | string | 格式：yyyy-MM-dd HH:mm:ss |
| 37 | CXRDM | 绑定人代码 | 绑定人代码 | string | 如：32012 |
| 38 | CXRXM | 绑定人姓名 | 绑定人姓名 | string | 如：张三 |
| 39 | CXPCNAME | 绑定计算机名称 | 绑定计算机名称 | string | 格式：客户端名称(Ip地址) |
| 40 | DRAWDATE | 采样时间 | 采样时间 | string | 格式：yyyy-MM-dd HH:mm:ss |
| 41 | DRAWUSERCODE | 采样人代码 | 采样人代码 | string | 如：32010 |
| 42 | DRAWUSERNAME | 采样人名称 | 采样人名称 | string | 如：李春天 |
| 43 | LISORDERCODE | 项目代码 | 项目代码 | string | 多个项目用逗号分隔 |
| 44 | LISORDERNAME | 项目名称 | 项目名称 | string | 多个项目用逗号分隔 |
| 45 | QSSJ | 签收时间 | 签收时间 | string | 格式：yyyy-MM-dd HH:mm:ss |
| 46 | QSRDM | 签收人代码 | 签收人代码 | string | 签收人代码 如：009 |
| 47 | QSRXM | 签收人姓名 | 签收人姓名 | string | 签收人姓名 如：王五 |
| 48 | QSBATCHNO | 签收批次号 | 签收批次号 | string | 签收批次号 |
| 49 | QSXH | 签收序号 | 签收序号 | string | 签收序号 |
| 50 | ENTRUST | 医嘱 | 医嘱 | string | 医嘱 |
| 51 | BAREXAMCODE | 条形码分组代码 | 条形码分组代码 | string | 条码分组代码 |
| 52 | BAREXAMNAME | 条形码分组名称 | 条形码分组名称 | string | 条码分组名称 |
| 53 | BARCOLOR | 条码颜色 | 条码颜色 | string | 十六进制颜色值 |





JSON返回示例：

summaryinfo：{

total:100,

……

},

samplelist:[

{

"TXM":"89382923",

"TXMSTATUS":"0",

.........................

"BAREXAMNAME":"生化",

"BARCOLOR":""



}

]

代码实现：

参照存储过程：lsp_wzh_rcbbrk_cx

表结构：LIS_ACCEPTITEMS\LIS_ORDERTOITEM\LIS_INSTORDER\LIS_BAREXAMCLASS\LIS_SPECIMENFLOW\LIS_ACCEPTITEM_HISTORY

调用时机：进行标本入库模块时，选择仪器，根据开始结束时间查询出该仪器能做的待入库项目；

入库时，住院标本需要判断是否收费，未收费标本需要调用HIS接口进行收费操作；

标本入库成功后，需记录到标本流程表LIS_SPECIMENFLOW中，调用接口：301.B01

LIS_ORDERMASTER和LIS_ACCEPTITEMS的任何更改均要记录历史记录到历史表中；



#### 扫条码入库

接口说明：扫描条码入库或查询标本信息

请求URL：../sampleinput/request/insertsampleinfo

示例URL：

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | txm | 条形码 | string | 条形码，如：13000283921 |
| 2 | type | 类型 | string | 0-手工、1-自动分配 |
| 3 | instid | 仪器ID | string | 传入当前仪器的ID |
| 4 | exectime | 检测日期 | string | 检测日期，格式：yyyy-MM-dd
非当天前端需要提示操作员，警示操作员 |
| 5 | techno | 样本号 | string | 允许为空，为空时表示自动获取可用样本号 |
| 项目集合：orderlist类型 Array<object> | 项目集合：orderlist类型 Array<object> | 项目集合：orderlist类型 Array<object> | 项目集合：orderlist类型 Array<object> | 项目集合：orderlist类型 Array<object> |
| 1 | lisordercode | LIS项目代码 | string | LIS项目代码 |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：sampleinfo   类型：object
格式同：B01 标本查询：节点名称：samplelist   类型：array<object>：标本列表中单个对象的定义 | 节点名称：sampleinfo   类型：object
格式同：B01 标本查询：节点名称：samplelist   类型：array<object>：标本列表中单个对象的定义 | 节点名称：sampleinfo   类型：object
格式同：B01 标本查询：节点名称：samplelist   类型：array<object>：标本列表中单个对象的定义 | 节点名称：sampleinfo   类型：object
格式同：B01 标本查询：节点名称：samplelist   类型：array<object>：标本列表中单个对象的定义 | 节点名称：sampleinfo   类型：object
格式同：B01 标本查询：节点名称：samplelist   类型：array<object>：标本列表中单个对象的定义 |
| 2 | currentTechNo | 当前入库位置 |  |  |
| 3 | nextechNo | 下一个样本号 |  |  |





JSON返回示例：

{

currentTechNo：12，

nextechNo：13，

samplelist:

{

"TXM":"89382923",

"TXMSTATUS":"0",

.........................

"BAREXAMNAME":"生化",

"BARCOLOR":""



}

}

代码实现：

调用存储过程：lsp_InsertSampleInfo

表结构：LIS_ACCEPTITEMS\LIS_ORDERTOITEM\LIS_INSTORDER\LIS_BAREXAMCLASS\LIS_SPECIMENFLOW\LIS_ACCEPTITEM_HISTORY\LIS_LISTMOD

调用时机：进行标本入库模块时，选择仪器，根据开始结束时间查询出该仪器能做的待入库项目；

入库时，住院标本需要判断是否收费，未收费标本需要调用HIS接口进行收费操作；

标本入库成功后，需记录到标本流程表LIS_SPECIMENFLOW中，调用接口：301.B01

LIS_ORDERMASTER和LIS_ACCEPTITEMS的任何更改均要记录历史记录到历史表中；

标本入库成功后需记录历史记录到LIS_LISTMOD中；

#### 预约排号生成预排号标本列表

接口说明：根据条件进行预排号

使用说明：在预排号选择条件，点击开始排号时，获取列表后展示到列表中

请求URL：../sampleinput/request/pregeneratesamplelist

示例URL：

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | instid | 仪器ID | string |  |
| 2 | exectime | 排号日期 | string | yyyy-MM-dd |
| 3 | querytimetype | 仪器ID | string | 0-采样时间、1、绑定时间、2-签收时间、 |
| 4 | begintime | 开始时间 | string | 格式：yyyy-MM-dd hh:mm:ss |
| 5 | endtime | 结束时间 | string | 格式：yyyy-MM-dd hh:mm:ss |
| 6 | begintechno | 起始号码 |  | 排号范围-开始号码 |
| 7 | endtechno | 结束号码 |  | 排号范围-结束号码 |
| 8 | order | 排序规则 |  |  |
| 9 | schemeid | 方案id |  |  |





接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：samplelist   类型：object
格式同：
B01 标本查询：节点名称：samplelist   类型：array<object>：标本列表 | 节点名称：samplelist   类型：object
格式同：
B01 标本查询：节点名称：samplelist   类型：array<object>：标本列表 | 节点名称：samplelist   类型：object
格式同：
B01 标本查询：节点名称：samplelist   类型：array<object>：标本列表 | 节点名称：samplelist   类型：object
格式同：
B01 标本查询：节点名称：samplelist   类型：array<object>：标本列表 | 节点名称：samplelist   类型：object
格式同：
B01 标本查询：节点名称：samplelist   类型：array<object>：标本列表 |



代码实现：

参照存储过程：lsp_InsertSampleInfo_ByPxxh、lsp_GetNextTechNo、

Sql语句参考：

SELECT b.* 

FROM   Lis_AcceptItems     a,

       lis_ordermaster     b

WHERE  lisordercode IN (SELECT lisordercode

                        FROM   lis_instorder

                        WHERE  instid = '29')

       AND a.orderno = b.OrderNo

       AND CONVERT(VARCHAR(10), a.qssj, 120) BETWEEN '2008-04-06' AND 

           '2017-04-21'

       AND a.TxmStatus='0'  

ORDER BY a.Qssj



相关表结构：Lis_ordermaster、lis_acceptitems、lis_instorder

调用时机：用户进行系统选择仪器，点击“排号”，将该仪器能做的项目赋值对应的样本号

注意：已分配的样本号不能再次分配给其他样本



#### 排号入库

接口说明：将预排号生成的列表数据进行批量入库；

请求URL：../sampleinput/request/prebatchinsertsampleinfo



示例URL：

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：samplelist   类型：array<object> | 节点名称：samplelist   类型：array<object> | 节点名称：samplelist   类型：array<object> | 节点名称：samplelist   类型：array<object> | 节点名称：samplelist   类型：array<object> |
|  | txm | 条形码 | string |  |
|  | instid | 仪器id | string |  |
|  | exectime | 检测日期 | string | yyyy-MM-dd |
|  | techno | 样本号 | string |  |
|  | schemeid | 入库方案id | string |  |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- | --- |
| 节点名称：samplelist   类型：array<object>
循环操作，成功的加入到samplelist中，失败的插入到faultsamplelist中
操作成功数据：
格式同：
B01 标本查询：节点名称：samplelist   类型：array<object>：标本列表 | 节点名称：samplelist   类型：array<object>
循环操作，成功的加入到samplelist中，失败的插入到faultsamplelist中
操作成功数据：
格式同：
B01 标本查询：节点名称：samplelist   类型：array<object>：标本列表 | 节点名称：samplelist   类型：array<object>
循环操作，成功的加入到samplelist中，失败的插入到faultsamplelist中
操作成功数据：
格式同：
B01 标本查询：节点名称：samplelist   类型：array<object>：标本列表 | 节点名称：samplelist   类型：array<object>
循环操作，成功的加入到samplelist中，失败的插入到faultsamplelist中
操作成功数据：
格式同：
B01 标本查询：节点名称：samplelist   类型：array<object>：标本列表 | 节点名称：samplelist   类型：array<object>
循环操作，成功的加入到samplelist中，失败的插入到faultsamplelist中
操作成功数据：
格式同：
B01 标本查询：节点名称：samplelist   类型：array<object>：标本列表 | 节点名称：samplelist   类型：array<object>
循环操作，成功的加入到samplelist中，失败的插入到faultsamplelist中
操作成功数据：
格式同：
B01 标本查询：节点名称：samplelist   类型：array<object>：标本列表 |
| 节点名称：faultsamplelist   类型：array<object>
操作失败数据列表 | 节点名称：faultsamplelist   类型：array<object>
操作失败数据列表 | 节点名称：faultsamplelist   类型：array<object>
操作失败数据列表 | 节点名称：faultsamplelist   类型：array<object>
操作失败数据列表 | 节点名称：faultsamplelist   类型：array<object>
操作失败数据列表 | 节点名称：faultsamplelist   类型：array<object>
操作失败数据列表 |
|  | txm | txm | 条形码 |  |  |
|  | faultmessage | faultmessage | 失败原因 |  |  |



JSON返回示例：

代码实现：

参照存储过程：lsp_InsertSampleInfo

单个条码入库，封装单独的接口，调用存储过程lsp_InsertSampleInfo实现

表结构：LIS_ACCEPTITEMS\LIS_ORDERTOITEM\LIS_INSTORDER\LIS_BAREXAMCLASS\LIS_SPECIMENFLOW\LIS_ACCEPTITEM_HISTORY\LIS_LISTMOD

针对OP_CHECK=true的进行入库

入库时，住院标本需要判断是否收费，未收费标本需要调用HIS接口进行收费操作；

标本入库成功后，需记录到标本流程表LIS_SPECIMENFLOW中，调用接口：301.B01

LIS_ORDERMASTER和LIS_ACCEPTITEMS的任何更改均要记录历史记录到历史表中；

标本入库成功后需记录历史记录到LIS_LISTMOD中；

#### 根据标本获取标本对应的收费项目信息

接口说明：获取标本对应的收费项目信息

使用说明：在点击列表或者入库成功后默认选中记录时

已经入库的，相当于查询，调用该接口查询显示。

预约排号尚未入库前的时候，点击列表，逻辑是根据所有的收费项目和方案中已经勾选的做对照，在方案中的为本次可入库，不在勾选范围内，为剩余收费项目

请求URL：../sampleinput/request/getsampleoforderinfo

示例URL：

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | instid | 仪器id | string |  |
| 1 | exectime | 检测日期 | string | yyyy-MM-dd |
| 2 | techno | 样本号 |  |  |
| 3 | txm | 条形码 |  |  |
|  | recordtype | 数据类型 |  | 1-已入库；0-未入库 |
|  | schemeid | 入库方案id |  | 预排号时选择的方案id |
|  |  |  |  |  |
|  |  |  |  |  |





接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 含义 | 类型 | 类型 | 备注说明 | 备注说明 |
| --- | --- | --- | --- | --- | --- | --- |
| 节点名称：orderlist array<object> 收费项目列表 | 节点名称：orderlist array<object> 收费项目列表 | 节点名称：orderlist array<object> 收费项目列表 | 节点名称：orderlist array<object> 收费项目列表 | 节点名称：orderlist array<object> 收费项目列表 | 节点名称：orderlist array<object> 收费项目列表 | 节点名称：orderlist array<object> 收费项目列表 |
|  | TXM | 方案id | 方案id | string | string |  |
|  | LISORDERCODE | lis收费项目代码 | lis收费项目代码 | string | string |  |
|  | HISORDERCODE | his收费项目代码 | his收费项目代码 | string | string |  |
|  | HISORDERNAME | 收费项目名称 | 收费项目名称 | string | string |  |
|  | INSTID | 仪器id | 仪器id |  |  |  |
|  | INSTNAME | 仪器名称 | 仪器名称 |  |  |  |
|  | TXMSTATUS | 条码状态 | 条码状态 | string | string | 条码当前状态
0-待入库 
5-已绑定 
1-已入库 |
|  | ENTRUST | 嘱托 | 嘱托 |  |  |  |
| 节点名称：notbelongcurrentinstt array<object> 不属于当前仪器的收费项目列表
字段同上述列表 | 节点名称：notbelongcurrentinstt array<object> 不属于当前仪器的收费项目列表
字段同上述列表 | 节点名称：notbelongcurrentinstt array<object> 不属于当前仪器的收费项目列表
字段同上述列表 | 节点名称：notbelongcurrentinstt array<object> 不属于当前仪器的收费项目列表
字段同上述列表 | 节点名称：notbelongcurrentinstt array<object> 不属于当前仪器的收费项目列表
字段同上述列表 | 节点名称：notbelongcurrentinstt array<object> 不属于当前仪器的收费项目列表
字段同上述列表 | 节点名称：notbelongcurrentinstt array<object> 不属于当前仪器的收费项目列表
字段同上述列表 |





提示信息规范：

JSON返回示例：



表结构关系：



#### 撤销入库

接口说明：将已入库标本进行撤销入库操作；

请求URL：../sampleinput/request/cancelinputsample

示例URL：

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | applyno | 报告单号 | string | 报告单号，如：33493842 |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：sampleinfo   类型：object
格式同：B01 标本查询：节点名称：samplelist   类型：array<object>：标本列表中单个对象 | 节点名称：sampleinfo   类型：object
格式同：B01 标本查询：节点名称：samplelist   类型：array<object>：标本列表中单个对象 | 节点名称：sampleinfo   类型：object
格式同：B01 标本查询：节点名称：samplelist   类型：array<object>：标本列表中单个对象 | 节点名称：sampleinfo   类型：object
格式同：B01 标本查询：节点名称：samplelist   类型：array<object>：标本列表中单个对象 | 节点名称：sampleinfo   类型：object
格式同：B01 标本查询：节点名称：samplelist   类型：array<object>：标本列表中单个对象 |



提示信息规范：

JSON返回示例：

"sampleinfo":

{

"TXM":"89382923",

"TXMSTATUS":"0",

.........................

"BAREXAMNAME":"生化",

"BARCOLOR":""



}

代码实现：

参考存储过程过程：lsp_Txm_CancelBbrk

通过调用存储过程进行撤销入库；

表结构：lis_list\lis_result\lis_worklistmaster\lis_worklistretail\lis_acceptitems

表结构：LIS_ACCEPTITEMS\LIS_ORDERTOITEM\LIS_INSTORDER\LIS_BAREXAMCLASS\LIS_SPECIMENFLOW\LIS_ACCEPTITEM_HISTORY\LIS_LISTMOD

撤销入库后，需记录到标本流程表LIS_SPECIMENFLOW中，调用接口：301.B01

LIS_ORDERMASTER和LIS_ACCEPTITEMS的任何更改均要记录历史记录到历史表中；

撤销入库成功后需记录历史记录到LIS_LISTMOD中；





#### 获取仪器收费项目及入库方案

接口说明：获取仪器对应的收费项目、以及入库方案列表

使用说明：切换仪器时，获取该仪器对应的收费项目，及收费入库方案

请求URL：../sampleinput/request/getinstorderinfo

示例URL：

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | instid | 仪器id | string | 仪器id |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 含义 | 类型 | 类型 | 备注说明 | 备注说明 |
| --- | --- | --- | --- | --- | --- | --- |
| 节点名称：orderlist array<object> 收费项目列表 | 节点名称：orderlist array<object> 收费项目列表 | 节点名称：orderlist array<object> 收费项目列表 | 节点名称：orderlist array<object> 收费项目列表 | 节点名称：orderlist array<object> 收费项目列表 | 节点名称：orderlist array<object> 收费项目列表 | 节点名称：orderlist array<object> 收费项目列表 |
|  | INSTID | 仪器id | 仪器id | string | string |  |
|  | LISORDERCODE | lis收费项目代码 | lis收费项目代码 | string | string |  |
|  | HISORDERCODE | his收费项目代码 | his收费项目代码 | string | string |  |
|  | HISORDERNAME | 收费项目名称 | 收费项目名称 | string | string |  |
|  | PRICE | 价格 | 价格 |  |  |  |
| 节点名称：schemelist array<object> 入库方案列表 | 节点名称：schemelist array<object> 入库方案列表 | 节点名称：schemelist array<object> 入库方案列表 | 节点名称：schemelist array<object> 入库方案列表 | 节点名称：schemelist array<object> 入库方案列表 | 节点名称：schemelist array<object> 入库方案列表 | 节点名称：schemelist array<object> 入库方案列表 |
|  | INSTID | 仪器id | 仪器id | string | string |  |
|  | RKSCHEMEID | 方案id | 方案id | string | string |  |
|  | RKSCHEMENAME | 方案名称 | 方案名称 | string | string |  |





提示信息规范：

JSON返回示例：



表结构关系：

 仪器收费项目

SELECT a.INSTID,b.LISORDERCODE,b.HISORDERCODE,b.HISORDERNAME FROM dbo.LIS_INSTORDER a

INNER JOIN dbo.LIS_HISITEM b ON a.LISORDERCODE = b.LISORDERCODE

WHERE INSTID=1



 仪器入库方案列表



SELECT a.INSTID,a.RKSCHEMEID,a.RKSCHEMENAME,a.INSTCODE FROM dbo.LIS_INSTSCHEME  a WHERE a.INSTID=1





#### 修改标本种类

接口说明：修改病人待入库\已入库的项目信息中的标本种类

请求URL：../sampleinput/request/changesampletype

示例URL：

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | txm | 条形码 | string | 标本条形码 |
| 2 | samplecode | 标本种类 | string | 更改后的标本种类 |
| 3 | samplename | 标本名称 | string | 更改后的标本名称 |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：sampleinfo   类型：object
格式同：B01 标本查询：节点名称：samplelist   类型：array<object>：标本列表中单个对象 | 节点名称：sampleinfo   类型：object
格式同：B01 标本查询：节点名称：samplelist   类型：array<object>：标本列表中单个对象 | 节点名称：sampleinfo   类型：object
格式同：B01 标本查询：节点名称：samplelist   类型：array<object>：标本列表中单个对象 | 节点名称：sampleinfo   类型：object
格式同：B01 标本查询：节点名称：samplelist   类型：array<object>：标本列表中单个对象 | 节点名称：sampleinfo   类型：object
格式同：B01 标本查询：节点名称：samplelist   类型：array<object>：标本列表中单个对象 |





提示信息规范：

JSON返回示例：



代码实现：

代码逻辑：更新lis_ordermatser表中的samplecode和samplename

表结构：lis_ordermaster\lis_acceptitems\lis_list

表结构关系：





#### 根据入库方案ID获取方案明细

接口说明：根据入库方案id,获取方案明细

使用说明：切换入库方案时，取到方案明细，根据明细去勾选列表中项目

请求URL：../sampleinput/request/getinstschemedetail

示例URL：

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | schemeid | 方案id | string |  |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 含义 | 类型 | 类型 | 备注说明 | 备注说明 |
| --- | --- | --- | --- | --- | --- | --- |
| 节点名称：orderlist array<object> 收费项目列表 | 节点名称：orderlist array<object> 收费项目列表 | 节点名称：orderlist array<object> 收费项目列表 | 节点名称：orderlist array<object> 收费项目列表 | 节点名称：orderlist array<object> 收费项目列表 | 节点名称：orderlist array<object> 收费项目列表 | 节点名称：orderlist array<object> 收费项目列表 |
|  | RKSCHEMEID | 方案id | 方案id | string | string |  |
|  | LISORDERCODE | lis收费项目代码 | lis收费项目代码 | string | string |  |
|  | HISORDERCODE | his收费项目代码 | his收费项目代码 | string | string |  |
|  | HISORDERNAME | 收费项目名称 | 收费项目名称 | string | string |  |





提示信息规范：

JSON返回示例：



表结构关系：

 入库方案明细收费项目

SELECT a.RKSCHEMEID,b.LISORDERCODE,c.HISORDERCODE,c.HISORDERNAME FROM LIS_INSTSCHEME a 

INNER JOIN dbo.LIS_SCHEMEDETAIL b

INNER JOIN dbo.LIS_HISITEM c ON b.LISORDERCODE = c.LISORDERCODE AND c.HOSPITALCODE ='42505175101'

ON a.RKSCHEMEID = a.RKSCHEMEID

WHERE a.RKSCHEMEID =1



#### 】添加入库方案

接口说明：添加入库方案

使用说明：添加入库方案，返回新方案的明细数据

请求URL：../sampleinput/request/addscheme

示例URL：

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 2 | schemename | 名称 | string | 不可为空 |
| 3 | orderlist | 收费项目列表 | array<Object> | 收费项目列表,添加 |
|  | LISORDERCODE |  | string |  |
|  | HISORDERCODE |  | string |  |
|  | HISORDERNAME |  | string |  |





接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 含义 | 类型 | 类型 | 备注说明 | 备注说明 |
| --- | --- | --- | --- | --- | --- | --- |
|  | RKSCHEMEID | 方案id | string | string |  |  |
|  | RKSCHEMENAME | 方案名称 | string | string |  |  |
| 节点名称：orderlist array<object> 方案对应的收费项目列表 | 节点名称：orderlist array<object> 方案对应的收费项目列表 | 节点名称：orderlist array<object> 方案对应的收费项目列表 | 节点名称：orderlist array<object> 方案对应的收费项目列表 | 节点名称：orderlist array<object> 方案对应的收费项目列表 | 节点名称：orderlist array<object> 方案对应的收费项目列表 | 节点名称：orderlist array<object> 方案对应的收费项目列表 |
|  | RKSCHEMEID | 方案id | 方案id | string | string |  |
|  | LISORDERCODE | lis收费项目代码 | lis收费项目代码 | string | string |  |
|  | HISORDERCODE | his收费项目代码 | his收费项目代码 | string | string |  |
|  | HISORDERNAME | 收费项目名称 | 收费项目名称 | string | string |  |







#### 修改入库方案

接口说明：修改入库方案

使用说明：修改入库方案

请求URL：../sampleinput/request/updatescheme

示例URL：

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | schemeid | 方案id | string | 修改和删除时为主键 |
| 2 | schemename | 名称 | string | 不可为空 |
| 3 | orderlist | 收费项目列表 | array<Object> | 收费项目列表,添加、删除 |
|  | RECORDTYPE | 记录状态 | string | ADD,DELETE |
|  | LISORDERCODE |  | string |  |
|  | HISORDERCODE |  | string |  |
|  | HISORDERNAME |  | string |  |





接口出参【ResposeMessage.data->object】：



#### 删除入库方案

接口说明：根据入库方案id,删除方案

使用说明：删除方案

请求URL：../sampleinput/request/deletescheme

示例URL：

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | schemeid | 仪器ID | string | 新增时为空\修改和删除时为主键 |
| 2 | schemename | 名称 | string | 不可为空,做校对 |





接口出参【ResposeMessage.data->object】：









## 报告查询

### V 模块接口视图

| NO. | 接口编号 | 接口名称 | 说明 |
| --- | --- | --- | --- |
| 1 | B01 | 患者查询场景-查询
../reportquerytab/request/getpatientreportinfo | 根据日期区间 磁卡号，病原号，姓名，首拼等查询字段查询患者的信息以及该患者日期区间内做的报告 |
| 2 | B02 | ../reportquerytab/request/getreportresultInfo | 获取报告结果信息 |
| 3 | B03 | ../reportquerytab/request/getpatientreporttop | 患者查询高级查询 |
| 4 | B04 | ../reportquerytab/request/getpatientreportinfo | 病区场景患者查询 |
| 6 | B05 |  |  |
| 7 | B06 |  |  |
| 8 | B07 | 复查结果
../reportdetails/request/get |  |
| 9 | B08 | 危急项目
../reportdetails/request/get |  |





### A 初始化数据



接口说明：报告复做模块初始化数据

请求URL：../reportquerytab/request/getinitdata

代码文件：winning.lis.reportquerytab.controller.ReportQueryTabController

示例URL：http://192.168.11.211:10002/lis/reportquerytab/request/getinitdata

原型参考：



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- | --- |
| 节点名称：wardorreglist类型：array<object> 病人类型 | 节点名称：wardorreglist类型：array<object> 病人类型 | 节点名称：wardorreglist类型：array<object> 病人类型 | 节点名称：wardorreglist类型：array<object> 病人类型 | 节点名称：wardorreglist类型：array<object> 病人类型 | 节点名称：wardorreglist类型：array<object> 病人类型 |
| 1 | 1 | CODE | 病人类型编码 | string | 0-门诊 1-住院 3-体检、4-干部体检 |
| 2 | 2 | NAME | 病人类型名称 | string |  |
|  |  |  |  |  |  |
| 节点名称：applydoclist 类型：array<object> 申请医生 | 节点名称：applydoclist 类型：array<object> 申请医生 | 节点名称：applydoclist 类型：array<object> 申请医生 | 节点名称：applydoclist 类型：array<object> 申请医生 | 节点名称：applydoclist 类型：array<object> 申请医生 | 节点名称：applydoclist 类型：array<object> 申请医生 |
|  |  | CODE | 申请医生代码 |  |  |
|  |  | NAME | 申请医生名字 |  |  |
|  |  |  |  |  |  |
| 节点名称：applydeptlist类型：array<object> 申请科室 | 节点名称：applydeptlist类型：array<object> 申请科室 | 节点名称：applydeptlist类型：array<object> 申请科室 | 节点名称：applydeptlist类型：array<object> 申请科室 | 节点名称：applydeptlist类型：array<object> 申请科室 | 节点名称：applydeptlist类型：array<object> 申请科室 |
|  |  | CODE | 申请科室代码 |  |  |
|  |  | NAME | 申请科室名称 |  |  |
|  |  |  |  |  |  |
| 节点名称：wardlist类型：array<object> 病区 | 节点名称：wardlist类型：array<object> 病区 | 节点名称：wardlist类型：array<object> 病区 | 节点名称：wardlist类型：array<object> 病区 | 节点名称：wardlist类型：array<object> 病区 | 节点名称：wardlist类型：array<object> 病区 |
|  |  | CODE | 病区代码 |  | 1001 |
|  |  | NAME | 病区名称 |  | 呼吸内科病房 |
|  |  |  |  |  |  |
| 节点名称：samplelist类型：array<object>  标本种类 | 节点名称：samplelist类型：array<object>  标本种类 | 节点名称：samplelist类型：array<object>  标本种类 | 节点名称：samplelist类型：array<object>  标本种类 | 节点名称：samplelist类型：array<object>  标本种类 | 节点名称：samplelist类型：array<object>  标本种类 |
| 1 | 1 | CODE | 标本种类代码 | string | 1 |
| 2 | 2 | NAME | 标本种类名称 | string | 血 |
|  |  |  |  |  |  |
| 节点名称：reportstatus  类型：array<object>  报告状态 | 节点名称：reportstatus  类型：array<object>  报告状态 | 节点名称：reportstatus  类型：array<object>  报告状态 | 节点名称：reportstatus  类型：array<object>  报告状态 | 节点名称：reportstatus  类型：array<object>  报告状态 | 节点名称：reportstatus  类型：array<object>  报告状态 |
| 1 | 1 | CODE | 报告状态 | string | 字典代码，如：KX-21 |
| 2 | 2 | NAME | 报告状态名称 | string | 字典名称，如：KX-21 |
|  |  |  |  |  |  |
| 节点名称：printstatus 类型：array<object>  打印状态 | 节点名称：printstatus 类型：array<object>  打印状态 | 节点名称：printstatus 类型：array<object>  打印状态 | 节点名称：printstatus 类型：array<object>  打印状态 | 节点名称：printstatus 类型：array<object>  打印状态 | 节点名称：printstatus 类型：array<object>  打印状态 |
| 1 | 1 | CODE | 打印状态 | string | 0:未打印 1：已打印 |
| 2 | 2 | NAME | 打印状态名称 | string | 字典名称，如：KX-21 |





JSON返回示例：

{

    "wardorreglist": [

      {

        "CODE": "0",

        "NAME": "门诊",

        "DICID": "",

        "DICTYPE": "病人类型",

        "EXTERNCODE": "0",

        "MEMCODE1": "MZ",

        "MEMCODE2": "MZ",

        "SUBSYSCODE": "LIMS",

        "ORDERNO": 1,

        "RESERVEFIELD1": ""

      }],"printstatus": [

      {

        "NAME": "未打印",

        "CODE": "0"

      },

      {

        "NAME": "已打印",

        "CODE": "1"

      }

    ]}





代码实现：

调用时机：用户进入报告复做模块，页面初始化时调用

表结构：SYS_DEPT\SETTINGS\SETTINGSDIC\SYS_USER

代码实现：

分别从接口表中获取对应的初始化参数信息



### B 业务类

#### B01	患者查询场景-查询

接口说明：根据日期区间 磁卡号，病原号，姓名，首拼等查询字段查询患者的信息以及该患者日期区间内做的报告

请求URL：../reportquerytab/request/getpatientreportinfo

代码文件：winning.lis.reportquerytab.controller.ReportQueryTabController

示例URL：http://192.168.11.211:10002/lis/reportquerytab/request/getpatientreportinfo



原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | timerange | 时间范围 | string | 0:一天内，1:两天内，2:三天内，3:一周内，4:两周内，5:一个月 |
| 2 | cardno | 磁卡号 | string | 01751417 |
| 3 | hospno | 病员号 | string | 20170000104609 |
| 4 | patname | 姓名 | string | 田姗姗 |
| 5 | imecode | 首拼 | string | TSS |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：patientinfo类型：array<object>   列表信息 | 节点名称：patientinfo类型：array<object>   列表信息 | 节点名称：patientinfo类型：array<object>   列表信息 | 节点名称：patientinfo类型：array<object>   列表信息 | 节点名称：patientinfo类型：array<object>   列表信息 |
| 1 | PATNAME | 病人姓名 | string |  |
| 2 | SEXDESC | 性别描述 | string |  |
| 3 | AGEDESC | 年龄描述 | string |  |
| 4 | BRLX | 病人类型 | string | HIS病人类型：0-门诊 1-住院 3-体检 4-体检 |
| 5 | WARDNAME | 病区 | string |  |
| 6 | BEDNO | 床号 | string |  |
| 7 | STATUS | 报告状态 | string | 10-初始报告 20-部分完成 30-已完成检测 40-初审通过 50-已审核 60-发布 |
| 8 | PRINTSTATUS | 打印状态 | string | 报告打印状态 0-未打印 1-已打印 |
| 9 | APPLYNO | 报告单号 | string |  |
| 10 | EXAMNAME | 检验分类 | string | 生化 |
| 11 | ISSELECTED | 是否选中 | string | false默认 |
| 节点名称：collect类型：object   底部汇总 | 节点名称：collect类型：object   底部汇总 | 节点名称：collect类型：object   底部汇总 | 节点名称：collect类型：object   底部汇总 | 节点名称：collect类型：object   底部汇总 |
|  | UNPRINTCNT | 未打印 | int |  |
|  | PRINTCNT | 已打印 | int |  |
|  | CNT | 全部 | int |  |





JSON返回示例：

 {

    "patientinfo": [

      {

        "PATNAME": null,

        "SEXDESC": null,

        "AGEDESC": null,

        "BRLX": null,

        "CARDNO": null,

        "WARDNAME": null,

        "BEDNO": null,

        "ORGAPPLYNO": null,

        "STATUS": 10,

        "PRINTSTATUS": 0,

        "APPLYNO": 7366566,

        "EXAMNAME": null

      },

      {

        "PATNAME": "434",

        "SEXDESC": "男",

        "AGEDESC": "岁",

        "BRLX": "1",

        "CARDNO": "34",

        "WARDNAME": "",

        "BEDNO": "",

        "ORGAPPLYNO": "43",

        "STATUS": 10,

        "PRINTSTATUS": 0,

        "APPLYNO": 7366570,

        "EXAMNAME": null

      },

      {

        "PATNAME": "45",

        "SEXDESC": "男",

        "AGEDESC": "岁",

        "BRLX": "1",

        "CARDNO": "45",

        "WARDNAME": "",

        "BEDNO": "",

        "ORGAPPLYNO": "",

        "STATUS": 10,

        "PRINTSTATUS": 0,

        "APPLYNO": 7366571,

        "EXAMNAME": null

      }

    ],

    "collect": {

      "PRINTCNT": 0,

      "UNPRINTCNT": 11,

      "CNT": 11

    }

  }



调用时机：模块进行初始化后，通过报告管理模块传过来的applyno作为入参，调用该服务，查询出病人信息。

相关表结构： lis_list

代码实现：



#### B02	报告结果

接口说明：根据报告单号获取报告结果

请求URL：../reportquerytab/request/getreportresultInfo

代码文件：winning.lis.reportquerytab.controller.ReportQueryTabController

示例URL：http://192.168.11.211:10002/lis/reportquerytab/request/getreportresultInfo



原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | applyno | 报告单号 | string | 如：6338574 |
| 2 |  |  |  |  |
| 3 |  |  |  |  |
|  |  |  |  |  |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：sampleinfo类型：object 顶端标题信息 | 节点名称：sampleinfo类型：object 顶端标题信息 | 节点名称：sampleinfo类型：object 顶端标题信息 | 节点名称：sampleinfo类型：object 顶端标题信息 | 节点名称：sampleinfo类型：object 顶端标题信息 |
| 1 | SAMPLENAME | 样本种类 | string |  |
| 2 | ORGAPPLYNO | 条形码 | string |  |
| 3 | SAMPLETIME | 采样时间 | string | 如：张三 |
| 4 | RECEIVETIME | 送检时间 | string | 男 |
| 5 | APPLYDEPTNAME | 申请科室 | string | 产科 |
| 6 | APPLYDOCNAME | 申请医生 | string |  |
| 7 | CLINICDESC | 临床诊断 | string |  |
| 8 | WARDNAME | 病区名称 | string |  |
| 9 | LABDIAGNOSIS | 实验室诊断与建议 | string |  |
| 10 | ONEXAMCNT | 检测中报告数量 | string |  |
| 节点名称：resultlist  类型：array<object> 中间结果信息 | 节点名称：resultlist  类型：array<object> 中间结果信息 | 节点名称：resultlist  类型：array<object> 中间结果信息 | 节点名称：resultlist  类型：array<object> 中间结果信息 | 节点名称：resultlist  类型：array<object> 中间结果信息 |
| 10 | ITEMNAME | 项目名称 | string | 项目名称  如：活化部分凝血活酶时间 |
| 11 | RESULT | 结果 | string | 报告结果   如：37.60 |
| 12 | REFERENCERANGE | 参考区间 | string | 如：22.00～37.00 |
| 13 | REDOFLAG | 复做标志 | string | 0：否   1：是 |
| 14 | HISRESULT1 | 历史结果1 |  |  |
| 15 | HISEXECTIME1 | 历史时间1 |  |  |
| 16 | HISRESULT2 | 历史结果2 | string | 历史报告结果2(相同检验项目的历史记录) |
| 17 | HISEXECTIME2 | 历史时间2 |  | 对应第二个时间 |
| 18 | HISRESULT3 | 历史结果3 | string | 历史报告结果3(相同检验项目的历史记录) |
| 19 | HISEXECTIME3 | 历史时间3 |  | 对应第三个时间 |
| 20 | HISRESULT4 | 历史结果4 | string | 历史报告结果4(相同检验项目的历史记录) |
| 21 | HISEXECTIME4 | 历史时间4 |  | 对应第四个时间 |
| 22 | HISRESULT5 | 历史结果5 | string | 历史报告结果5(相同检验项目的历史记录) |
| 23 | REDOFLAG | 复做标志 | string | 0：否   1：是 |
| 24 | PANICFLAG | 危急值标识 | string | 0-正常 1-危急值报告 |
| 25 | RESULTCHAR | 字符结果 | string | 如：37.60 |
| 26 | ITEMCODE | 项目代码 | string | 项目代码   如：APTT |
| 27 | HINTINFO | 提示 | string |  |
| 28 | UNIT | 单位 | string | 参考区间单位   如：秒 |
| 29 | INSTID | 仪器ID | string | 仪器ID  如：10010 |
| 30 | INSTNAME | 仪器名称 | string | 仪器名称  如：AU5800 |
| 31 | HIGHLOWFLAG | 高低标志 | string | P-阳性或异常值 H-偏高 L-偏低 HH-显著偏高 LL-显著偏低 |
| 32 | REDO | 复做标志 | Int, | 复做标记 0-正常 1-复做 |
| 33 | ODRESULT | OD值 | string | OD值 |
| 34 | ODRESULTCHAR | OD值字符表示 | string | OD值字符表示 |
| 35 | CUTOFFVALUE | CUTOFF比值 | string | CUTOFF比值 |
| 36 | CUTOFFVALUECHAR | CUTOFF比值字符表示 | string | CUTOFF比值字符表示 |
| 37 | SCOVALUE | SCO比值 | string | SCO比值 |
| 38 | SCOVALUECHAR | SCO比值字符表示 | string | SCO比值字符表示 |
| 39 | RESULTABN | 是否异常结果 | string | 0 是异常结果，1 正常结果 |
|  |  |  |  |  |
| 节点名称：inexaminfo类型：array<object>  检测中报告信息 | 节点名称：inexaminfo类型：array<object>  检测中报告信息 | 节点名称：inexaminfo类型：array<object>  检测中报告信息 | 节点名称：inexaminfo类型：array<object>  检测中报告信息 | 节点名称：inexaminfo类型：array<object>  检测中报告信息 |
| 1 | INSTNAME | 检测仪器名称 | string |  |
|  | RECEIVETIME | 检测日期 | string |  |
|  | TECHNO | 样本号 | string |  |
|  | ESTREPORTTIME | 预计取报告日期 |  |  |
|  | HISORDERNAME | 检测项目 |  |  |
|  |  |  |  |  |





JSON返回示例：

"resultinfo": [

      {

        "APPLYNO": 6338574,

        "ITEMCODE": "CP",

        "ITEMNAME": "肺炎衣原体IgM抗体",

        "INSTID": 12,

        "INSTCODE": "AIA-360",

        "INSTNAME": "AIA-360",

        "RESULTTIME": 1521789866577,

        "RESULTVALUE": "",

        "RESULTCHAR": "阴性（-）",

        "RESULTTYPE": "3",

        "RESULT": "阴性（-）",

        "HINTINFO": "",

        "DISPLAYFLAG": 0,

        "SCIENTIFICFLAG": 0,

        "ODRESULT": "",

        "ODRESULTCHAR": "",

        "CUTOFFVALUE": "",

        "CUTOFFVALUECHAR": "",

        "SCOVALUE": "",

        "SCOVALUECHAR": "",

        "TESTINSTID": 12,

        "TESTINSTCODE": "AIA-360",

        "TESTINSTNAME": "AIA-360",

        "PRINTORDER": " 0009",

        "HIGHLOWFLAG": "",

        "REFERENCERANGE": "阴性 ",

        "UNIT": "",

        "REDO": "",

        "PANICFLAG": 0,

        "INSTVERIFYFLAG": "",

        "INSTVERIFYCONTENT": "",

        "SCOPICFLAG": 0,

        "HOSPITALCODE": "9999",

        "HISRESULT1": "",

        "HISEXECTIME1": "",

        "HISHIGHLOWFLAG1": "",

        "HISPANICFLAG1": "",

        "LIFELIMITFLAG1": "",

        "HISRESULT2": "",

        "HISEXECTIME2": "",

        "HISHIGHLOWFLAG2": "",

        "HISPANICFLAG2": "",

        "LIFELIMITFLAG2": "",

        "HISRESULT3": "",

        "HISEXECTIME3": "",

        "HISHIGHLOWFLAG3": "",

        "HISPANICFLAG3": "",

        "LIFELIMITFLAG3": "",

        "HISRESULT4": "",

        "HISEXECTIME4": "",

        "HISHIGHLOWFLAG4": "",

        "HISPANICFLAG4": "",

        "LIFELIMITFLAG4": "",

        "HISRESULT5": "",

        "HISEXECTIME5": "",

        "HISHIGHLOWFLAG5": "",

        "HISPANICFLAG5": "",

        "LIFELIMITFLAG5": "",

        "IMPORTANT": " "

      }

    ],

    "sampleinfo": {

      "ORGAPPLYNO": "40002689849P",

      "SAMPLENAME": "血",

      "HOSPNO": "16023718",

      "CARDNO": "",

      "SEX": "1",

      "PATNAME": "刘建刚",

      "PATIENTID": "16023718",

      "SAMPLETIME": 1493941429000,

      "RECEIVETIME": 1494207557000,

      "APPLYDEPTNAME": "",

      "APPLYDOCNAME": "",

      "CLINICDESC": "",

      "LABDIAGNOSIS": null,

      "ONEXAMCNT": 0

    },

    "inexaminfo": []

  }  	 

调用时机：点击”报告信息”节点的时候触发

相关表结构：LIS_LIST， function : lsp_GetResultData

代码实现：

根据查询条件从LIS_ORDERMASTER 表中查询病人信息



#### B03	患者查询场景-高级查询

接口说明：根据日期区间 磁卡号，病原号，姓名，首拼等查询字段查询患者的信息以及该患者日期区间内做的报告

请求URL：../reportquerytab/request/getpatientreporttop

代码文件：winning.lis.reportquerytab.controller.ReportQueryTabController

示例URL：http://192.168.11.211:10002/lis/reportquerytab/request/getpatientreporttop



原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | timetype | 日期类型 | string | 报告日期 0，申请时间 1，发布日期 2 |
| 2 | startdate | 开始日期 | string |  |
| 3 | enddate | 结束日期 | string |  |
| 4 | brlx | 病人类型 | string |  |
| 5 | instid | 检测仪器ID | string |  |
| 6 | cardno | 磁卡号 | string | 20170000104609 |
| 7 | hospno | 病员号 | string | 20170000104609 |
| 8 | patname | 姓名 | string | 田姗姗 |
| 9 | ward | 病区 | string |  |
|  | bedno | 床号 |  |  |
| 10 | imecode | 拼音码 | string |  |
| 11 | applydeptcode | 申请科室 | string |  |
| 12 | applydoccode | 申请医生 | string |  |
| 13 | printstatus | 打印状态 | string |  |
| 14 | orgapplyno | 条码号 | string |  |
| 15 | samplecode | 标本种类 | string |  |
| 16 | status | 报告状态 | string |  |
| 17 | remark | 备注 | string |  |
| 18 | fuzzytype | 姓名匹配 | string | 0 匹配 1 不匹配 (必填) |
|  |  |  |  |  |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：patientinfo类型：array<object>   列表信息 | 节点名称：patientinfo类型：array<object>   列表信息 | 节点名称：patientinfo类型：array<object>   列表信息 | 节点名称：patientinfo类型：array<object>   列表信息 | 节点名称：patientinfo类型：array<object>   列表信息 |
| 1 | PATNAME | 病人姓名 | string |  |
| 2 | SEXDESC | 性别描述 | string |  |
| 3 | AGEDESC | 年龄描述 | string |  |
| 4 | BRLX | 病人类型 | string |  |
| 5 | WARDNAME | 病区 | string |  |
| 6 | BEDNO | 床号 | string |  |
| 7 | STATUS | 报告状态 | string | 10-初始报告 20-部分完成 30-已完成检测 40-初审通过 50-已审核 60-发布 |
| 8 | PRINTSTATUS | 打印状态 | string | 报告打印状态 0-未打印 1-已打印 |
| 9 | APPLYNO | 报告单号 | string |  |
| 10 | EXAMNAME | 检验分类 | string | 生化 |
| 节点名称：collect类型：object   底部汇总 | 节点名称：collect类型：object   底部汇总 | 节点名称：collect类型：object   底部汇总 | 节点名称：collect类型：object   底部汇总 | 节点名称：collect类型：object   底部汇总 |
|  | UNPRINTCNT | 未打印 | int |  |
|  | PRINTCNT | 已打印 | int |  |
|  | CNT | 全部 | int |  |





JSON返回示例：

 {

    "patientinfo": [

      {

        "PATNAME": null,

        "SEXDESC": null,

        "AGEDESC": null,

        "BRLX": null,

        "CARDNO": null,

        "WARDNAME": null,

        "BEDNO": null,

        "ORGAPPLYNO": null,

        "STATUS": 10,

        "PRINTSTATUS": 0,

        "APPLYNO": 7366566,

        "EXAMNAME": null

      },

      {

        "PATNAME": "434",

        "SEXDESC": "男",

        "AGEDESC": "岁",

        "BRLX": "1",

        "CARDNO": "34",

        "WARDNAME": "",

        "BEDNO": "",

        "ORGAPPLYNO": "43",

        "STATUS": 10,

        "PRINTSTATUS": 0,

        "APPLYNO": 7366570,

        "EXAMNAME": null

      },

      {

        "PATNAME": "45",

        "SEXDESC": "男",

        "AGEDESC": "岁",

        "BRLX": "1",

        "CARDNO": "45",

        "WARDNAME": "",

        "BEDNO": "",

        "ORGAPPLYNO": "",

        "STATUS": 10,

        "PRINTSTATUS": 0,

        "APPLYNO": 7366571,

        "EXAMNAME": null

      }

    ],

    "collect": {

      "PRINTCNT": 0,

      "UNPRINTCNT": 11,

      "CNT": 11

    }

  }



调用时机：模块进行初始化后，通过报告管理模块传过来的applyno作为入参，调用该服务，查询出病人信息。

相关表结构： lis_list

代码实现：











#### B01	病区查询场景-查询

接口说明：根据日期区间 磁卡号，病原号，姓名，首拼等查询字段查询患者的信息以及该患者日期区间内做的报告

请求URL：../reportquerytab/request/getpatientreportinfo

代码文件：winning.lis.reportquerytab.controller.ReportQueryTabController

示例URL：http://192.168.11.211:10002/lis/reportquerytab/request/getpatientreportinfo



原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | timerange | 时间范围 | string | 0:一天内，1:两天内，2:三天内，3:一周内，4:两周内，5:一个月 |
| 2 | ward | 病区编码 | string |  |
| 3 | bedno | 床号编码 | string |  |
|  |  |  |  |  |
|  |  |  |  |  |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：patientinfo类型：array<object>   列表信息 | 节点名称：patientinfo类型：array<object>   列表信息 | 节点名称：patientinfo类型：array<object>   列表信息 | 节点名称：patientinfo类型：array<object>   列表信息 | 节点名称：patientinfo类型：array<object>   列表信息 |
| 1 | PATNAME | 病人姓名 | string |  |
| 2 | SEXDESC | 性别描述 | string |  |
| 3 | AGEDESC | 年龄描述 | string |  |
| 4 | BRLX | 病人类型 | string | HIS病人类型：0-门诊 1-住院 3-体检 4-体检 |
| 5 | WARDNAME | 病区 | string |  |
| 6 | BEDNO | 床号 | string |  |
| 7 | STATUS | 报告状态 | string | 10-初始报告 20-部分完成 30-已完成检测 40-初审通过 50-已审核 60-发布 |
| 8 | PRINTSTATUS | 打印状态 | string | 报告打印状态 0-未打印 1-已打印 |
| 9 | APPLYNO | 报告单号 | string |  |
| 10 | EXAMNAME | 检验分类 | string | 生化 |
| 11 | ISSELECTED | 是否选中 | string | false默认 |
| 节点名称：collect类型：object   底部汇总 | 节点名称：collect类型：object   底部汇总 | 节点名称：collect类型：object   底部汇总 | 节点名称：collect类型：object   底部汇总 | 节点名称：collect类型：object   底部汇总 |
|  | UNPRINTCNT | 未打印 | int |  |
|  | PRINTCNT | 已打印 | int |  |
|  | CNT | 全部 | int |  |





JSON返回示例：

 {

    "patientinfo": [

      {

        "PATNAME": null,

        "SEXDESC": null,

        "AGEDESC": null,

        "BRLX": null,

        "CARDNO": null,

        "WARDNAME": null,

        "BEDNO": null,

        "ORGAPPLYNO": null,

        "STATUS": 10,

        "PRINTSTATUS": 0,

        "APPLYNO": 7366566,

        "EXAMNAME": null

      },

      {

        "PATNAME": "434",

        "SEXDESC": "男",

        "AGEDESC": "岁",

        "BRLX": "1",

        "CARDNO": "34",

        "WARDNAME": "",

        "BEDNO": "",

        "ORGAPPLYNO": "43",

        "STATUS": 10,

        "PRINTSTATUS": 0,

        "APPLYNO": 7366570,

        "EXAMNAME": null

      },

      {

        "PATNAME": "45",

        "SEXDESC": "男",

        "AGEDESC": "岁",

        "BRLX": "1",

        "CARDNO": "45",

        "WARDNAME": "",

        "BEDNO": "",

        "ORGAPPLYNO": "",

        "STATUS": 10,

        "PRINTSTATUS": 0,

        "APPLYNO": 7366571,

        "EXAMNAME": null

      }

    ],

    "collect": {

      "PRINTCNT": 0,

      "UNPRINTCNT": 11,

      "CNT": 11

    }

  }



调用时机：模块进行初始化后，通过报告管理模块传过来的applyno作为入参，调用该服务，查询出病人信息。

相关表结构： lis_list

代码实现：

## 查询统计

### 模块接口视图

| NO. | 接口编号 | 接口名称 | 说明 |
| --- | --- | --- | --- |
| 1 | A01 | 查询统计画面初始化
../querystatistics/request/getinitdata | 获取树信息 |



### A01	画面初始化

接口说明：获取初始化信息

请求URL：../querystatistics/request/getinitdata

代码文件：winning.lis.querystatistics.service.QueryStatisticsService

示例URL： 

http://192.168.10.73:15011/lis/querystatistics/request/getinitdata

原型参考：

接口入参： 无

         接口出参【ResposeMessage.data-> array<object>】：

| NO. | 变量名 | 变量名 | 含义 | 含义 | 类型 | 类型 | 备注说明 | 备注说明 |
| --- | --- | --- | --- | --- | --- | --- | --- | --- |
| 节点名称：brlxList病人类型下拉框信息 类型：array< object> 默认标本 | 节点名称：brlxList病人类型下拉框信息 类型：array< object> 默认标本 | 节点名称：brlxList病人类型下拉框信息 类型：array< object> 默认标本 | 节点名称：brlxList病人类型下拉框信息 类型：array< object> 默认标本 | 节点名称：brlxList病人类型下拉框信息 类型：array< object> 默认标本 | 节点名称：brlxList病人类型下拉框信息 类型：array< object> 默认标本 | 节点名称：brlxList病人类型下拉框信息 类型：array< object> 默认标本 | 节点名称：brlxList病人类型下拉框信息 类型：array< object> 默认标本 | 节点名称：brlxList病人类型下拉框信息 类型：array< object> 默认标本 |
| 1 | PARENTNAME | PARENTNAME | 名称 | 名称 | string | string |  |  |
| 2 | 2 | CHILDNODES | CHILDNODES | 子节点信息 | 子节点信息 | array<object> | array<object> |  |  |
|  |  |  |  |  |  |  |  |  |  |
| 1 | 1 | NAME | NAME | 子节点名称 | 子节点名称 | string | string | 如： | 如： |









## 门诊采集



###  模块接口视图

| NO. | 接口编号 | 接口名称 | 对接人 | 说明 |
| --- | --- | --- | --- | --- |
| 1 | 200.A01 | 请求初始化数据
../sampling/request/getinitdata | 赵贤云 | 请求门诊采集需要的初始化数据及控制参数信息
http://localhost:8080/lis/sampling/request/getinitdata?hospitalcode=&userid=&pcname= |
| 2 | 200.B01 | 获取病人信息
../sampling/request/getpatientinfo | 潘发龙 | 根据磁卡号或病员号等关键字段获取患者的病人信息
http://localhost:8080/lis/sampling/request/getpatientinfo?brlx=0&code=01770350&hospitalcode=9999&codetype=2 |
| 3 | 200.B02 | 下载医嘱并分组
../sampling/request/getpatorders | 胡吉安 | 根据患者的医嘱并进行条码分组
http://localhost:8080/lis/sampling/request/getpatorders?patientinfo={}&downloaddays=7&balance=0 |
| 4 | 900.A05 | 获取收费项目标本对照
../common/request/getordersamplelist | 赵贤云 | 获取传入指定项目的标本类型集合
http://localhost:8080/lis/common/request/getordersamplelist?hospitalcode=9999&lisordercode=066 |
| 5 | 900.B01 | 获取模块快捷栏菜单
../common/request/getmoduleshortcut | 孙万达 | 用于获取模块对应的快捷菜单，含菜单名称及图标
http://localhost:8080/lis/common/request/getmoduleshortcut?modulecode=LIS_SAMPLING&modulename=门诊采集 |
| 6 | 200.B03 | 医嘱分组
../sampling/request/grouppatorders | 胡吉安 | 更改医嘱信息后需要重新对医嘱数据进行分组 |
| 7 | 202.B01 | 条码打印-预印条码
../barcodebinding/operate/barbinding | 潘发龙 | 预印条码绑定条码 |
| 8 | 202.B02 | 条码打印-打印条码
../barcodebinding/operate/newbarandbinding | 潘发龙 | 按规则自动生成条码并绑定条码 |
| 10 | 202.B03 | 获取条形码信息（打印使用）
../barcodebinding/request/getsamplelist | 赵贤云 | 打印条形码获取条形码列表数据
http://localhost:8080/lis/barcodebinding/request/getsamplelist?barlist=["012789624P","29002789627P","21002789622P"]&type=0&hospitalcode=9999&printmode=2 |
| 11 | 200.B04 | 获取医嘱提示信息
../sampling/request/summarypatorders | 胡吉安 | 选择会更改选择医嘱后汇总提示信息 |
| 12 | 200.B05 | 查找病人信息
../sampling/request/querypatientlist | 孙万达 | 根据姓名或科室等关键字段查找定位患者 |
| 13 | 300.B03 | 获取患者生物危害信息
../patview/request/getpatsensitiveflag | 赵贤云 | 获取指定患者的生物危害标志
http://localhost:8080/lis/patview/request/getpatsensitiveflag?brlx=0&patientid=1697293&cureno=1697293&cardno=01771524&hospno=20170000121201&patname=胡蝶&sex=2&idnum= |
| 14 | 300.B04 | 获取患者生物危害指标信息
../patview/request/getpatsensitiveresult | 赵贤云 | 获取指定患者的生物危害指标信息
http://localhost:8080/lis/patview/request/getpatsensitiveresult?brlx=0&patientid=1697293&cureno=1697293&cardno=01771524&hospno=20170000121201&patname=胡蝶&sex=2&idnum= |
| 15 | 301.B03 | 查询患者标本列表
../sampleview/request/getsamplelist | 孙万达 | 查询一段时间内患者的标本信息 |
| 16 | 202.B04 | 获取回执单信息
../barcodebinding/request/getsamplereturn | 胡吉安 | 获取标本取报告时间 |



### 初始化数据

接口说明：请求住院采集需要的初始化数据及控制参数信息

请求URL：../sampling/request/getinitdata

代码文件：winning.lis.sampling.service.SamplingServerice

示例URL：http://192.168.14.253:8080/lis/sampling/request/getinitdata

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码 |
| 2 | userid | 用户id | string | 用户id，允许为空
传入为空时获取当前用户userid |
| 3 | pcname | 计算机信息 | string | 格式：计算机名(ip地址），允许为空
传入为空时获取当前用户所属的计算机 |



说明：传入医疗机构主要是考虑后续住院采集会挂入其他系统中使用，需传入医疗机构代码和用户等信息

接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：controlsparams类型：object | 节点名称：controlsparams类型：object | 节点名称：controlsparams类型：object | 节点名称：controlsparams类型：object | 节点名称：controlsparams类型：object |
| 1 | DOWNLOADDAYS | 下载天数 | number | 默认为：30天 |
| 2 | GETPATMETHOD | 获取病人方式 | string | 默认获取病人信息方式 |
| 3 | TXMMODE | 条码模式 | string | P-打印条码 Y-预制条码 A-打印和预印条码兼有
预印(Y\|A)-
打印（P\|A）- |
| 4 | EDITDOWNDAYS | 编辑下载天数 | boolean | false-不允许编辑（可不显示）
true-允许编辑（显示并能选择更改） |
| 节点名称：diclist 类型：array<object> | 节点名称：diclist 类型：array<object> | 节点名称：diclist 类型：array<object> | 节点名称：diclist 类型：array<object> | 节点名称：diclist 类型：array<object> |
|  | diclist[0] 节点名称：下载医嘱天数  类型：array<object> | diclist[0] 节点名称：下载医嘱天数  类型：array<object> | diclist[0] 节点名称：下载医嘱天数  类型：array<object> | diclist[0] 节点名称：下载医嘱天数  类型：array<object> |
| 1 | CODE | 字典代码 | string | 字典代码，如：7 |
| 2 | NAME | 字典名称 | string | 字典名称，如：7天 |
| 3 | DICID | 字典ID | string | 字典ID，如：7 |
| 4 | DICTYPE | 字典类型 | string | 字典类型，如：下载医嘱天数 |
| 5 | EXTERNCODE | 外部码 | string | HIS外部代码 |
| 6 | MEMCODE1 | 输入码一 | string | 输入码一 |
| 7 | MEMCODE2 | 输入码二 | string | 输入码二 |
| 8 | SUBSYSCODE | 系统代码 | string | 系统代码，LIS默认为LIMS |
| 9 | ORDERNO | 序号 | number | 排序序号，根据此字段来显示展示顺序 |
|  | diclist[1] 节点名称：下载病人信息方式  类型：array<object> | diclist[1] 节点名称：下载病人信息方式  类型：array<object> | diclist[1] 节点名称：下载病人信息方式  类型：array<object> | diclist[1] 节点名称：下载病人信息方式  类型：array<object> |
| 1 | CODE | 字典代码 | string | 字典代码，如：2309 |
| 2 | NAME | 字典名称 | string | 字典名称，如：骨科病区 |
| 3 | DICID | 字典ID | string | 字典ID，如：2309 |
| 4 | DICTYPE | 字典类型 | string | 字典类型，如：申请病区 |
| 5 | EXTERNCODE | 外部码 | string | HIS外部代码 |
| 6 | MEMCODE1 | 输入码一 | string | 输入码一 |
| 7 | MEMCODE2 | 输入码二 | string | 输入码二 |
| 8 | SUBSYSCODE | 系统代码 | string | 系统代码，LIS默认为LIMS |
| 9 | ORDERNO | 序号 | number | 排序序号，根据此字段来显示展示顺序 |
| 10 | ICONCLASS | 对应ICON图标 | string | 对应ICON图标 |





说明：申请病区列表获取需按用户权限来划分，如果当前用户管理多个病区，则只返回管理的病区即可，否则根据参数来判断病区获取方式；

JSON返回示例：

"controlsparams":{

"DOWNLOADDAYS":"30",

"GETPATMETHOD":"2309",

"TXMMODE":"P",

"EDITDOWNDAYS":"true"

},

"diclist":{

"下载医嘱天数":[

{

    "CODE":"7",

    "NAME":"7天",

    "DICID":"7",

    "DICTYPE":"下载医嘱天数",

    "EXTERNCODE":"7",

    "MEMCODE1":"7",

    "MEMCODE2":"7t",

    "SUBSYSCODE":"LIMS",

    "ORDERNO":"1"

},

{

    "CODE":"15",

    "NAME":"15天",

    "DICID":"15",

    "DICTYPE":"下载医嘱天数",

    "EXTERNCODE":"15",

    "MEMCODE1":"15",

    "MEMCODE2":"15t",

    "SUBSYSCODE":"LIMS",

    "ORDERNO":"2"

}

]

,

"下载病人信息方式":[

{

    "CODE":"1",

    "NAME":"病员号",

    "DICID":"1",

    "DICTYPE":"下载病人信息方式",

    "EXTERNCODE":"1",

    "MEMCODE1":"1",

    "MEMCODE2":"byh",

    "SUBSYSCODE":"LIMS",

    "ORDERNO":"1"

},

{

    "CODE":"2310",

    "NAME":"骨科二病区",

    "DICID":"2310",

    "DICTYPE":"申请病区",

    "EXTERNCODE":"2310",

    "MEMCODE1":"2310",

    "MEMCODE2":"gkebq",

    "SUBSYSCODE":"LIMS",

    "ORDERNO":"2"

}

]

}

代码实现：

表结构：SLAVE\SLAVE_P

代码实现：

下载医嘱天数：SLAVE.CLASSCODE = 下载医嘱天数

下载病人信息方式：：SLAVE.CLASSCODE = 下载病人信息方式

如果SLAVE表没有的话，取SLAVE_P中的数据

参考SQL：

select * from SLAVE where CLASSCODE=’下载医嘱天数’ and HOSPITALCODE=.......

select * from SLAVE_P where CLASSCODE=’下载医嘱天数’ 

select * from SLAVE where CLASSCODE=’下载医嘱天数’ and HOSPITALCODE=.......

select * from SLAVE_P where CLASSCODE=’下载医嘱天数’ 



### 业务类

#### B01 获取病人信息

接口说明：根据磁卡号或病员号等关键字段获取患者的病人信息

请求URL：../sampling/request/getpatientinfo

代码文件：winning.lis.sampling.service.SamplingServerice

示例URL：http://192.168.14.253:8080/lis/sampling/request/getpatientinfo?brlx=0&code=D93038232&hospitalcode=9999&codetype=2

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | brlx | 病人类型 | string | 0-门诊 1-住院 3、4-体检
门诊采集传 0 |
| 2 | codetype | 查询方式 | string | 1-病员号 2-磁卡号 5-条码号 9-发票号 |
| 3 | code | 查询值 | string | 对应的查询关键内容 |
| 4 | hospitalcode | 机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码 |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- | --- |
| 节点名称：patprop  患者属性 类型：object | 节点名称：patprop  患者属性 类型：object | 节点名称：patprop  患者属性 类型：object | 节点名称：patprop  患者属性 类型：object | 节点名称：patprop  患者属性 类型：object | 节点名称：patprop  患者属性 类型：object |
| 1 | BIOHAZARDFLAG | 传染病标志 | 传染病标志 | string | U-有生物危害,N-正常 |
| 2 | BALANCE | 余额 | 余额 | number | 余额信息 |
| 节点名称：patientlist类型：array<object> | 节点名称：patientlist类型：array<object> | 节点名称：patientlist类型：array<object> | 节点名称：patientlist类型：array<object> | 节点名称：patientlist类型：array<object> | 节点名称：patientlist类型：array<object> |
| 1 | BRLX | BRLX | 病人类型 | string | 0-门诊 1-住院 3、4-体检
门诊采集默认为 1，可以用入参的brlx |
| 2 | PATIENTID | PATIENTID | 病人唯一号 | string | 病人唯一号
对应HIS接口：PatientID |
| 3 | CURENO | CURENO | 就诊号 | string | 就诊号
对应HIS接口：CureNo |
| 4 | PATNAME | PATNAME | 患者姓名 | string | 患者姓名
对应HIS接口：PatName |
| 5 | HOSPNO | HOSPNO | 住院号 | string | 住院号
对应HIS接口：blh |
| 6 | CARDNO | CARDNO | 磁卡号 | string | 磁卡号
对应HIS接口：cardno |
| 7 | SEX | SEX | 性别 | string | 性别 1-男 2-女 3-未知
对应HIS接口：sex |
| 8 | SEXDESC | SEXDESC | 性别描述 | string | 性别描述
对应HIS接口：sex - 需转换成中文 |
| 9 | AGE | AGE | 年龄 | string | 年龄
对应HIS接口：age |
| 10 | AGEUNIT | AGEUNIT | 年龄单位 | number | 年龄单位，如：岁
对应HIS接口：ageunit |
| 11 | AGE2 | AGE2 | 年龄2 | string | 年龄2
对应HIS接口：age2（需判断属性是否存在并且是数字型） |
| 12 | AGEUNIT2 | AGEUNIT2 | 年龄单位2 | number | 年龄单位，如：日
对应HIS接口：ageunit2（需判断属性是否存在） |
| 13 | AGEDESC | AGEDESC | 年龄 | string | 年龄描述，如：23岁
对应HIS接口：agedesc（需判断属性是否存在）
如果不存在，则组装Age+AgeUnit |
| 14 | WARD | WARD | 病区代码 | string | 病区代码
对应HIS接口：ward（需转换为内码） |
| 15 | WARDNAME | WARDNAME | 病区名称 | string | 病区名称 由病区代码获取名称 |
| 16 | BEDNO | BEDNO | 床号 | string | 床号，如：+10床
对应HIS接口：bedno |
| 17 | CLINICDESC | CLINICDESC | 临床诊断 | string | 临床诊断，如：感冒
对应HIS接口：clinicdesc |
| 18 | CHARGETYPECODE | CHARGETYPECODE | 费别代码 | string | 费别代码
对应HIS接口：chargeType（需转换为内码） |
| 19 | CHARGETYPENAME | CHARGETYPENAME | 费别名称 | string | 费别名称 由费别代码获取名称 |
| 20 | YEBZ | YEBZ | 婴儿标志 | string | 对应HIS接口：yebz（需判断属性是否存在） |
| 21 | YEXH | YEXH | 婴儿序号 | string | 对应HIS接口：yexh （需判断属性是否存在） |
| 22 | TIMES | TIMES | 住院次数 | string | 对应HIS接口：times（需判断属性是否存在） |
| 23 | PHONE | PHONE | 联系电话 | string | 对应HIS接口：phone（需判断属性是否存在） |
| 24 | ADDRESS | ADDRESS | 地址 | string | 对应HIS接口：address（需判断属性是否存在） |
| 25 | ZIP | ZIP | 邮编 | string | 对应HIS接口：zip（需判断属性是否存在） |
| 26 | IDNUM | IDNUM | 身份证 | string | 对应HIS接口：idnum（需判断属性是否存在） |
| 27 | BIRTHDAY | BIRTHDAY | 生日 | string | 对应HIS接口：birthday（需判断属性是否存在） |
| 28 | NATION | NATION | 民族 | string | 对应HIS接口：nation（需判断属性是否存在） |
| 29 | EMPIID | EMPIID | EMPID | string | 对应HIS接口：empiid（需判断属性是否存在） |
| 30 | ICD10 | ICD10 | ICD10 | string | 对应HIS接口：icd10（需判断属性是否存在） |
| 31 | ICD10NAME | ICD10NAME | ICD名称 | string | 对应HIS接口：icd10name（需判断属性是否存在） |
| 32 | APPLYDEPTCODE | APPLYDEPTCODE | 申请科室 | string | 对应HIS接口：applyDept
转换成内码 |
| 33 | APPLYDEPTNAME | APPLYDEPTNAME | 科室名称 | string | 申请科室名称，有申请科室代码获取 |
| 34 | APPLYDOCCODE | APPLYDOCCODE | 申请医生 | string | 对应HIS接口：applydoctor
转换成内码 |
| 35 | APPLYDOCNAME | APPLYDOCNAME | 医生姓名 | string | 申请医生姓名，有申请医生代码获取 |
| 36 | CAREER | CAREER | 学历 | string | 对应HIS接口：career（需判断属性是否存在） |
| 37 | YJJBR | YJJBR | 预交金病人 | string | 对应HIS接口：yjjbr（需判断属性是否存在） |
| 38 | CARDTYPE | CARDTYPE | 卡类型 | string | 卡类型 对应HIS接口：cardtype
（需判断属性是否存在） |
| 39 | WARDORREG | WARDORREG | 病人类型内码 | string | SLAVE表中转换 |
| 40 | WARDORREGNAME | WARDORREGNAME | 病人类型名称 | string | SLAVE表中转换 |





JSON返回示例：

"patprop":[

{

  "BIOHAZARDFLAG":"U",

"BALANCE":"109.23"

},

"patientlist":[

{

    "BRLX":"0",

"PATIENTID":2344321",

"CURENO":2344321",

...................

    "EMPIID":"12220",

    "ICD10":"B11.022",

"ICD10NAME":"霍乱"

},

{

    "BRLX":"0",

"PATIENTID":2344321",

"CURENO":2344321",

...................

    "EMPIID":"12220",

    "ICD10":"B11.022",

"ICD10NAME":"霍乱"

}

]

代码实现：

调用HIS接口：getPatientInfo

需要进行内部转换的进行数据转换,转换成内部码

相关表结构：SLAVE\SYS_DEPT\SYS_USERS

#### B02 下载医嘱并分组

接口说明：根据患者的医嘱并进行条码分组

请求URL：../sampling/request/getpatorders

代码文件：winning.lis.sampling.service.SamplingServerice

示例URL：http://192.168.14.253:8080/lis/sampling/request/getpatorders?patientinfo={}&downloaddays=7&balance=0

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | patientinfo-当前患者信息的对象 object类型 将页面中的患者对象传入后台 字段见200.B01 | patientinfo-当前患者信息的对象 object类型 将页面中的患者对象传入后台 字段见200.B01 | patientinfo-当前患者信息的对象 object类型 将页面中的患者对象传入后台 字段见200.B01 | patientinfo-当前患者信息的对象 object类型 将页面中的患者对象传入后台 字段见200.B01 |
| 2 | downloaddays | 下载医嘱天数 | string | 前端选择的下载医嘱天数，默认为30 |
| 3 | balance | 患者余额 | number | 患者余额信息 |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：orderinfo  汇总信息 类型：object | 节点名称：orderinfo  汇总信息 类型：object | 节点名称：orderinfo  汇总信息 类型：object | 节点名称：orderinfo  汇总信息 类型：object | 节点名称：orderinfo  汇总信息 类型：object |
| 1 | HINTCONTENT | 提示 | string | 提示信息，文字形式 |
| 2 | HINTCONTENTHTML | 提示 | string | 提示信息，文字形式 |
| 3 | ITEMSUMPRICE | 项目金额 | number | 项目应收费金额 |
| 4 | MATERIALSUMPRICE | 材料费金额 | number | 应收取的材料费金额 |
| 5 | BALANCE | 余额 | number | 患者余额 |
| 6 | DIFFSUMPRICE | 差额 | number | 差额 |
| 7 | WARNINGFLAG | 预警标志 | string | N-正常；L-余额低；LL-余额不足 |
| 节点名称：orderlist类型：array<object> | 节点名称：orderlist类型：array<object> | 节点名称：orderlist类型：array<object> | 节点名称：orderlist类型：array<object> | 节点名称：orderlist类型：array<object> |
| 条码分组及控制信息 | 条码分组及控制信息 | 条码分组及控制信息 | 条码分组及控制信息 | 条码分组及控制信息 |
| 1 | OP_CHECK | 选择 | string | 0-未选择
1-选择
默认为1 |
| 2 | OP_CHECKENABLE | 允许选择 | string | true-允许选择或取消选择
false-不允许选择或取消选择，按默认的操作 |
| 3 | BARGROUP | 条码分组 | string | 用于区分是否允许绑定在一起，是否只采样一个单位
格式：BAREXAMCODE_流水号 |
| 4 | BARCODE | 条形码 | string | 初始条码为空，绑定成功后更改此属性 |
| 5 | BAREXAMCODE | 分组代码 | string | 条码分组代码 |
| 6 | BAREXAMNAME | 分组名称 | string | 条码分组名称 |
| 7 | IMAGEURL | 试管地址 | string | 用于加载试管图片，相对地址 |
| 8 | BARLENGTH | 条码长度 | number | 条码总长度 |
| 9 | BARCOLOR | 试管颜色 | string | 十六进制颜色值 |
| 10 | BARPREPART | 条码前缀 | string | 条码前缀，最多两位 |
| 11 | BARSECONDCOLOR | 第二颜色 | string | 十六进制颜色值 |
| 12 | BARSECPREPART | 第二前缀 | string | 条码第二前缀，最多两位 |
| 13 | BARPRIORITY | 优先次序 | number | 根据此条件来排序显示 |
| 14 | SAMPLECODE | 样本类型 | string | 样本类型代码 |
|  | 试管区域显示内容字段对应 | 试管区域显示内容字段对应 | 试管区域显示内容字段对应 | 试管区域显示内容字段对应 |
| 15 | SAMPLENAME | 样本名称 | string | 样本类型名称 |
| 16 | BARDESCRIBE | 分组描述 | string | 用于配置试管颜色 如：紫色 |
| 17 | BARCUBAGE | 采血量 | string | 采血量，如：50ml |
| 18 | BARNOTICE | 注意事项 | string | 采集注意事项，如：混匀5次 |
| 19 | EXAMGROUPNAME | 送检小组 | string | 送检小组，如：检验科 |
| 20 | EXAMGROUPCODE | 送检小组代码 | string | 送检小组代码，如：0002 |
| 21 | BARGENRULE | 条码规则 | string | 对应SLAVE中的条码生成规则 |
| 需要采血的病人列表 | 需要采血的病人列表 | 需要采血的病人列表 | 需要采血的病人列表 | 需要采血的病人列表 |
| 1 | APPLYNO | 报告单号 | string | HIS下载未确认的医嘱默认为-2 |
| 2 | SERIALNO | 序号 | string | HIS下载未确认的默认为-1 |
| 3 | PATIENTID | 病人唯一号 | string | 病人唯一号 |
| 4 | CURENO | 就诊号 | string | 就诊号 |
| 5 | LOGNO | 医嘱序号 | string | 医嘱序号，对应LIS_ACCEPTITEMS.HISXXH |
| 6 | HISAPPLYNO | 申请单号 | string | 申请单号，不是开申请单则为0 |
| 7 | GROUPNO | 处方序号 | string | 处方序号 |
| 8 | HISORDERCODE | 项目代码 | string | HIS项目代码 |
| 9 | HISORDERNAME | 项目名称 | string | HIS项目名称 |
| 10 | ITEMTYPE | 项目分类 | string | 0-临床 1-收费 |
| 11 | LISORDERCODE | LIS代码 | string | 对应LIS_HISITEM.LISORDERCODE |
| 12 | ITEMQTY | 项目数量 | string | 项目数量，如：1.5 |
| 13 | PRICE | 单价 | string | 单价，如：45.00 |
| 14 | ITEMUNIT | 单位 | string | 如：次 |
| 15 | APPLYTIME | 申请时间 | string | 如：2017-06-26 09:34:23 |
| 16 | APPLYDAYS | 申请天数 | number | 申请时间到当前时间的天数，如：6 |
| 17 | BEYONDTIMELIMIT | 超出天数 | string | 申请时间到当前时间超出设定范围
B-超出范围（申请时间后面需显示APPLYDAYS并红色标识）
其他只显示正常内容 |
| 17 | APPLYDEPTCODE | 申请科室 | string | 申请科室代码(LIS内码），如：2353 |
| 18 | APPLYDEPTNAME | 科室名称 | string | 申请科室名称(LIS内码），如：内科 |
| 19 | APPLYDOCCODE | 申请医生 | string | 申请医生代码(LIS内码)，如：2839 |
| 20 | APPLYDOCNAME | 医生姓名 | string | 申请医生姓名(LIS内码)，如：万一阁 |
| 21 | EXECDEPTCODE | 执行科室 | string | 执行科室代码(LIS内码)，如：4300 |
| 22 | EXECDEPTNAME | 科室名称 | string | 执行科室名称(LIS内码)，如：检验科 |
| 25 | ADDTYPE | 医嘱类型 | string | 0-HIS下载
1-LIS上传 |
| 26 | ADDTYPEDESC | 类型描述 | string | 医嘱类型中文描述，如：下载或上传 |
| 27 | STATUS | 状态 | string | 0-未确认 1-已确认 2-已拒绝 |
| 28 | STATUSDESC | 状态 | string | 状态中文描述，如：未确认、已确认、已拒绝 |
| 29 | CHARGEFLAG | 收费 | string | 0-未收费 1-已收费 2-退费 |
| 30 | CHARGEFLAGDESC | 收费描述 | string | 收费中文描述，如：未收费、已收费、已退费 |
| 31 | CLINICDESC | 临床诊断 | string | 临床诊断 |
| 32 | SAMPLECODE | 样本类型 | string | 样本类型代码 |
| 33 | SAMPLENAME | 样本名称 | string | 样本类型名称 |
| 34 | GHXH | 挂号序号 | string | 挂号序号，针对门诊 |
| 41 | ENTRUST | 嘱托 | string | 嘱托内容 |
| 42 | MJZBZ | 加急标志 | string | 0-常规 1-加急 |
| 43 | EXCUTETIME | 预约时间 | string | 预约检测的日期 |



提示信息规范：





JSON返回示例：

"orderinfo":{

"HINTCONTENT":"选择3个项目，金额 235.00 元",

"HINTCONTENTHTML":"选择3个项目，金额 235.00 元",

"ITEMSUMPRICE":78.90,

"MATERIALSUMPRICE":10.00,

"BALANCE":80.00,

"DIFFSUMPRICE":8.9,

"WARNINGFLAG":"LL"

},

"orderlist":[

{

    "OP_CHECK":"1",

"OP_CHECKENABLE":"true",

...................

    "ENTRUST":"嘱托",

    "MJZBZ":"0",

    "EXCUTETIME":"2017-07-21"

},

{

    "OP_CHECK":"1",

"OP_CHECKENABLE":"true",

...................

    "ENTRUST":"嘱托",

    "MJZBZ":"0",

    "EXCUTETIME":"2017-07-21"

}

]

代码实现：

调用HIS接口：getpatientorder

需要进行内部转换的进行数据转换,转换成内部码

相关表结构：SLAVE\SYS_DEPT\SYS_USERS

#### B03 医嘱分组

接口说明：更改医嘱信息后需要重新对医嘱数据进行分组

请求URL：../sampling/request/grouppatorders

代码文件：winning.lis.sampling.service.SamplingServerice

示例URL：http://192.168.14.253:8080/lis/sampling/request/grouppatorders?orderlist=[]

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | orderlist array<object>  见 200.B02的出参orderlist | orderlist array<object>  见 200.B02的出参orderlist | orderlist array<object>  见 200.B02的出参orderlist | orderlist array<object>  见 200.B02的出参orderlist |
| 2 | patientinfo-当前患者信息的对象 object类型 将页面中的患者对象传入后台 字段见200.B01 | patientinfo-当前患者信息的对象 object类型 将页面中的患者对象传入后台 字段见200.B01 | patientinfo-当前患者信息的对象 object类型 将页面中的患者对象传入后台 字段见200.B01 | patientinfo-当前患者信息的对象 object类型 将页面中的患者对象传入后台 字段见200.B01 |
| 3 | balance | 患者余额 | number | 患者余额信息 |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：orderinfo  汇总信息 类型：object | 节点名称：orderinfo  汇总信息 类型：object | 节点名称：orderinfo  汇总信息 类型：object | 节点名称：orderinfo  汇总信息 类型：object | 节点名称：orderinfo  汇总信息 类型：object |
| 1 | HINTCONTENT | 提示 | string | 提示信息，文字形式 |
| 2 | HINTCONTENTHTML | 提示 | string | 提示信息，文字形式 |
| 3 | ITEMSUMPRICE | 项目金额 | number | 项目应收费金额 |
| 4 | MATERIALSUMPRICE | 材料费金额 | number | 应收取的材料费金额 |
| 5 | BALANCE | 余额 | number | 患者余额 |
| 6 | DIFFSUMPRICE | 差额 | number | 差额 |
| 7 | WARNINGFLAG | 预警标志 | string | N-正常；L-余额低；LL-余额不足 |
| 节点名称：orderlist类型：array<object> | 节点名称：orderlist类型：array<object> | 节点名称：orderlist类型：array<object> | 节点名称：orderlist类型：array<object> | 节点名称：orderlist类型：array<object> |
| 条码分组及控制信息 | 条码分组及控制信息 | 条码分组及控制信息 | 条码分组及控制信息 | 条码分组及控制信息 |
| 1 | OP_CHECK | 选择 | string | 0-未选择
1-选择
默认为1 |
| 2 | OP_CHECKENABLE | 允许选择 | string | true-允许选择或取消选择
false-不允许选择或取消选择，按默认的操作 |
| 3 | BARGROUP | 条码分组 | string | 用于区分是否允许绑定在一起，是否只采样一个单位
格式：BAREXAMCODE_流水号 |
| 4 | BARCODE | 条形码 | string | 初始条码为空，绑定成功后更改此属性 |
| 5 | BAREXAMCODE | 分组代码 | string | 条码分组代码 |
| 6 | BAREXAMNAME | 分组名称 | string | 条码分组名称 |
| 7 | IMAGEURL | 试管地址 | string | 用于加载试管图片，相对地址 |
| 8 | BARLENGTH | 条码长度 | number | 条码总长度 |
| 9 | BARCOLOR | 试管颜色 | string | 十六进制颜色值 |
| 10 | BARPREPART | 条码前缀 | string | 条码前缀，最多两位 |
| 11 | BARSECONDCOLOR | 第二颜色 | string | 十六进制颜色值 |
| 12 | BARSECPREPART | 第二前缀 | string | 条码第二前缀，最多两位 |
| 13 | BARPRIORITY | 优先次序 | number | 根据此条件来排序显示 |
| 14 | SAMPLECODE | 样本类型 | string | 样本类型代码 |
|  | 试管区域显示内容字段对应 | 试管区域显示内容字段对应 | 试管区域显示内容字段对应 | 试管区域显示内容字段对应 |
| 15 | SAMPLENAME | 样本名称 | string | 样本类型名称 |
| 16 | BARDESCRIBE | 分组描述 | string | 用于配置试管颜色 如：紫色 |
| 17 | BARCUBAGE | 采血量 | string | 采血量，如：50ml |
| 18 | BARNOTICE | 注意事项 | string | 采集注意事项，如：混匀5次 |
| 19 | EXAMGROUPNAME | 送检小组 | string | 送检小组，如：检验科 |
| 20 | EXAMGROUPCODE | 送检小组代码 | string | 送检小组代码，如：0002 |
| 需要采血的医嘱列表 | 需要采血的医嘱列表 | 需要采血的医嘱列表 | 需要采血的医嘱列表 | 需要采血的医嘱列表 |
| 1 | APPLYNO | 报告单号 | string | HIS下载未确认的医嘱默认为-2 |
| 2 | SERIALNO | 序号 | string | HIS下载未确认的默认为-1 |
| 3 | PATIENTID | 病人唯一号 | string | 病人唯一号 |
| 4 | CURENO | 就诊号 | string | 就诊号 |
| 5 | LOGNO | 医嘱序号 | string | 医嘱序号，对应LIS_ACCEPTITEMS.HISXXH |
| 6 | HISAPPLYNO | 申请单号 | string | 申请单号，不是开申请单则为0 |
| 7 | GROUPNO | 处方序号 | string | 处方序号 |
| 8 | HISORDERCODE | 项目代码 | string | HIS项目代码 |
| 9 | HISORDERNAME | 项目名称 | string | HIS项目名称 |
| 10 | ITEMTYPE | 项目分类 | string | 0-临床 1-收费 |
| 11 | LISORDERCODE | LIS代码 | string | 对应LIS_HISITEM.LISORDERCODE |
| 12 | ITEMQTY | 项目数量 | string | 项目数量，如：1.5 |
| 13 | PRICE | 单价 | string | 单价，如：45.00 |
| 14 | ITEMUNIT | 单位 | string | 如：次 |
| 15 | APPLYTIME | 申请时间 | string | 如：2017-06-26 09:34:23 |
| 16 | APPLYDAYS | 申请天数 | number | 申请时间到当前时间的天数，如：6 |
| 17 | BEYONDTIMELIMIT | 超出天数 | string | 申请时间到当前时间超出设定范围
B-超出范围（申请时间后面需显示APPLYDAYS并红色标识）
其他只显示正常内容 |
| 17 | APPLYDEPTCODE | 申请科室 | string | 申请科室代码(LIS内码），如：2353 |
| 18 | APPLYDEPTNAME | 科室名称 | string | 申请科室名称(LIS内码），如：内科 |
| 19 | APPLYDOCCODE | 申请医生 | string | 申请医生代码(LIS内码)，如：2839 |
| 20 | APPLYDOCNAME | 医生姓名 | string | 申请医生姓名(LIS内码)，如：万一阁 |
| 21 | EXECDEPTCODE | 执行科室 | string | 执行科室代码(LIS内码)，如：4300 |
| 22 | EXECDEPTNAME | 科室名称 | string | 执行科室名称(LIS内码)，如：检验科 |
| 25 | ADDTYPE | 医嘱类型 | string | 0-HIS下载
1-LIS上传 |
| 26 | ADDTYPEDESC | 类型描述 | string | 医嘱类型中文描述，如：下载或上传 |
| 27 | STATUS | 状态 | string | 0-未确认 1-已确认 2-已拒绝 |
| 28 | STATUSDESC | 状态 | string | 状态中文描述，如：未确认、已确认、已拒绝 |
| 29 | CHARGEFLAG | 收费 | string | 0-未收费 1-已收费 2-退费 |
| 30 | CHARGEFLAGDESC | 收费描述 | string | 收费中文描述，如：未收费、已收费、已退费 |
| 31 | CLINICDESC | 临床诊断 | string | 临床诊断 |
| 32 | SAMPLECODE | 样本类型 | string | 样本类型代码 |
| 33 | SAMPLENAME | 样本名称 | string | 样本类型名称 |
| 34 | GHXH | 挂号序号 | string | 挂号序号，针对门诊 |
| 41 | ENTRUST | 嘱托 | string | 嘱托内容 |
| 42 | MJZBZ | 加急标志 | string | 0-常规 1-加急 |
| 43 | EXCUTETIME | 预约时间 | string | 预约检测的日期 |



提示信息规范：





JSON返回示例：

"orderinfo":{

"HINTCONTENT":"选择3个项目，金额 235.00 元",

"HINTCONTENTHTML":"选择3个项目，金额 235.00 元",

"ITEMSUMPRICE":78.90,

"MATERIALSUMPRICE":10.00,

"BALANCE":80.00,

"DIFFSUMPRICE":8.9,

"WARNINGFLAG":"LL"

},

"orderlist":[

{

    "OP_CHECK":"1",

"OP_CHECKENABLE":"true",

...................

    "ENTRUST":"嘱托",

    "MJZBZ":"0",

    "EXCUTETIME":"2017-07-21"

},

{

    "OP_CHECK":"1",

"OP_CHECKENABLE":"true",

...................

    "ENTRUST":"嘱托",

    "MJZBZ":"0",

    "EXCUTETIME":"2017-07-21"

}

]

代码实现：

参数控制

医嘱属性

相关表结构：LIS_BAREXAMCLASS\MJZBZ....

#### B04 医嘱提示

接口说明：选择会更改选择医嘱后汇总提示信息

请求URL：../sampling/request/summarypatorders

代码文件：winning.lis.zysampling.service.ZySamplingServerice

示例URL：http://192.168.14.253:8080/lis/sampling/request/summarypatorders?orderlist=[]

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | orderlist array<object>  见 200.B02的出参orderlist | orderlist array<object>  见 200.B02的出参orderlist | orderlist array<object>  见 200.B02的出参orderlist | orderlist array<object>  见 200.B02的出参orderlist |
| 2 | patientinfo-当前患者信息的对象 object类型 将页面中的患者对象传入后台 字段见200.B01 | patientinfo-当前患者信息的对象 object类型 将页面中的患者对象传入后台 字段见200.B01 | patientinfo-当前患者信息的对象 object类型 将页面中的患者对象传入后台 字段见200.B01 | patientinfo-当前患者信息的对象 object类型 将页面中的患者对象传入后台 字段见200.B01 |
| 3 | balance | 患者余额 | number | 患者余额信息 |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：orderinfo  汇总信息 类型：object | 节点名称：orderinfo  汇总信息 类型：object | 节点名称：orderinfo  汇总信息 类型：object | 节点名称：orderinfo  汇总信息 类型：object | 节点名称：orderinfo  汇总信息 类型：object |
| 1 | HINTCONTENT | 提示 | string | 提示信息，文字形式 |
| 2 | HINTCONTENTHTML | 提示 | string | 提示信息，文字形式 |
| 3 | ITEMSUMPRICE | 项目金额 | number | 项目应收费金额 |
| 4 | MATERIALSUMPRICE | 材料费金额 | number | 应收取的材料费金额 |
| 5 | BALANCE | 余额 | number | 患者余额 |
| 6 | DIFFSUMPRICE | 差额 | number | 差额 |
| 7 | WARNINGFLAG | 预警标志 | string | N-正常；L-余额低；LL-余额不足 |



JSON返回示例：

"orderinfo":{

"HINTCONTENT":"选择3个项目，金额 235.00 元",

"HINTCONTENTHTML":"选择3个项目，金额 235.00 元",

"ITEMSUMPRICE":78.90,

"MATERIALSUMPRICE":10.00,

"BALANCE":80.00,

"DIFFSUMPRICE":8.9,

"WARNINGFLAG":"LL"

}

代码实现：

汇总选择项目数量及总金额

参照接口200.B02或200.B03

#### B05 查找病人信息

接口说明：根据姓名或科室等关键字段查找定位患者

请求URL：../sampling/request/querypatientlist

代码文件：winning.lis.sampling.service.SamplingServerice

示例URL：http://192.168.14.253:8080/lis/sampling/request/querypatientlist?brlx=0&patname=张三&applydeptcode=&ward=&bedno=&begtime=2017-07-01&endtime=2017-07-31&invoiceno=&codetype=1&code=&hospitalcode=9999

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | brlx | 病人类型 | string | 0-门诊 1-住院 3、4-体检
门诊采集传 0 |
| 2 | patname | 患者姓名 | string | 允许传空 |
| 3 | applydeptcode | 科室代码 | string | 申请科室代码，允许传空 |
| 4 | ward | 病区代码 | string | 病区代码，允许传空 |
| 5 | bedno | 床号 | string | 床号，允许传空 |
| 6 | begtime | 开始时间 | string | 开始时间，格式：yyyy-MM-dd HH:mm:ss
门诊为挂号时间，不允许为空
住院时间不做处理 |
| 7 | endtime | 结束时间 | string | 开始时间，格式：yyyy-MM-dd HH:mm:ss
门诊为挂号时间，不允许为空
住院时间不做处理 |
| 8 | invoiceno | 发票号 | string | 发票号
，允许传空 |
| 9 | codetype | 查询方式 | string | 1-病员号 2-磁卡号 5-条码号 9-发票号
允许传空 |
| 10 | code | 查询值 | string | 对应的查询关键内容
允许传空，传空表示不做处理 |
| 11 | hospitalcode | 机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码 |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- | --- |
| 节点名称：patientlist类型：array<object> | 节点名称：patientlist类型：array<object> | 节点名称：patientlist类型：array<object> | 节点名称：patientlist类型：array<object> | 节点名称：patientlist类型：array<object> | 节点名称：patientlist类型：array<object> |
| 1 | BRLX | BRLX | 病人类型 | string | 0-门诊 1-住院 3、4-体检
门诊采集默认为 1，可以用入参的brlx |
| 2 | PATIENTID | PATIENTID | 病人唯一号 | string | 病人唯一号
对应HIS接口：PatientID |
| 3 | CURENO | CURENO | 就诊号 | string | 就诊号
对应HIS接口：CureNo |
| 4 | PATNAME | PATNAME | 患者姓名 | string | 患者姓名
对应HIS接口：PatName |
| 5 | HOSPNO | HOSPNO | 住院号 | string | 住院号
对应HIS接口：blh |
| 6 | CARDNO | CARDNO | 磁卡号 | string | 磁卡号
对应HIS接口：cardno |
| 7 | SEXDESC | SEXDESC | 性别描述 | string | 性别描述
对应HIS接口：性别- 需转换成中文 |
| 8 | AGEDESC | AGEDESC | 年龄 | string | 年龄描述，如：23岁
对应HIS接口：年龄（需判断属性是否存在） |
| 9 | WARDNAME | WARDNAME | 病区名称 | string | 病区名称 对应HIS接口：病区 |
| 10 | APPLYDEPTNAME | APPLYDEPTNAME | 科室名称 | string | 科室名称 对应HIS接口：科室 |
| 10 | BEDNO | BEDNO | 床号 | string | 床号，如：+10床
对应HIS接口：bedno |
| 11 | YEXH | YEXH | 婴儿序号 | string | 对应HIS接口：yexh （需判断属性是否存在） |
| 12 | TIMES | TIMES | 住院次数 | string | 对应HIS接口：times（需判断属性是否存在） |
| 13 | PHONE | PHONE | 联系电话 | string | 对应HIS接口：电话（需判断属性是否存在） |
| 14 | ADDRESS | ADDRESS | 地址 | string | 对应HIS接口：地址（需判断属性是否存在） |
| 15 | IDNUM | IDNUM | 身份证 | string | 对应HIS接口：idnum（需判断属性是否存在） |
| 16 | BIRTHDAY | BIRTHDAY | 生日 | string | 对应HIS接口：birthday（需判断属性是否存在） |
| 17 | NATION | NATION | 民族 | string | 对应HIS接口：nation（需判断属性是否存在） |
| 18 | EMPIID | EMPIID | EMPID | string | 对应HIS接口：empiid（需判断属性是否存在） |
| 19 | ICD10 | ICD10 | ICD10 | string | 对应HIS接口：icd10（需判断属性是否存在） |
| 20 | ICD10NAME | ICD10NAME | ICD名称 | string | 对应HIS接口：icd10name（需判断属性是否存在） |





表格展示规范：



JSON返回示例：

"patientlist":[

{

    "BRLX":"0",

"PATIENTID":2344321",

"CURENO":2344321",

...................

    "EMPIID":"12220",

    "ICD10":"B11.022",

"ICD10NAME":"霍乱"

},

{

    "BRLX":"0",

"PATIENTID":2344321",

"CURENO":2344321",

...................

    "EMPIID":"12220",

    "ICD10":"B11.022",

"ICD10NAME":"霍乱"

}

]

代码实现：

调用HIS接口：getPatientList

将中文字段转换成对应的英文字段名

#### B06 获取患者生物危害指标信息

接口说明：获取指定患者的生物危害指标信息

请求URL：../patview/request/getpatsensitiveresult

代码文件：winning.lis.patview.service.PatViewServerice

示例URL：http://192.168.14.253:8080/lis/patview/request/getpatsensitiveresult

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 说明 |
| --- | --- | --- | --- | --- |
| 1 | brlx | 病人类型 | string | 0-门诊 1-住院 3、4-体检 |
| 2 | patientid | 病人唯一号 | string | 取患者属性：PATIENTID |
| 3 | cureno | 就诊号 | string | 取患者属性：CURENO |
| 4 | cardno | 磁卡号 | string | 取患者属性：CARDNO |
| 5 | hospno | 病员号\|住院号 | string | 门诊-病员号
住院-住院号
取患者属性：HOSPNO |
| 6 | patname | 患者姓名 | string | 取患者属性：PATNAME |
| 7 | sex | 性别 | string | 取患者属性：SEX |
| 8 | idnum | 身份证号 | string | 取患者属性：IDNUM |



接口出参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：sensitiveresultlist   类型：array<object> | 节点名称：sensitiveresultlist   类型：array<object> | 节点名称：sensitiveresultlist   类型：array<object> | 节点名称：sensitiveresultlist   类型：array<object> | 节点名称：sensitiveresultlist   类型：array<object> |
| 1 | APPLYNO | 报告单号 | string | 对应的结果的报告单号 |
| 2 | INSTID | 仪器ID | number | 仪器ID，对应LIS_LIST.INSTID |
| 3 | INSTCODE | 仪器编码 | string | 仪器编码 |
| 4 | INSTNAME | 仪器名称 | string | 仪器名称 |
| 2 | EXECTIME | 检测日期 | string | 格式：yyyy-mm-dd 如：2017-06-27 |
| 3 | TECHNO | 样本号 | number | 样本号 |
| 3 | SAMPLECODE | 标本代码 | string | 标本代码 |
| 4 | SAMPLENAME | 标本名称 | string | 标本名称 |
| 5 | ITEMCODE | 项目代码 | string | 项目代码 |
| 6 | ITEMNAME | 项目名称 | string | 项目名称 |
| 7 | RESULTTYPE | 结果类型 | string | 1-数字 2-字符 |
| 8 | RESULT | 结果 | string | 项目结果 |
| 9 | REFERENCERANGE | 参考范围 | string | 如：12.3~23.00 |
| 10 | UNIT | 结果单位 | string | 如：^10E9mg/l |
| 11 | HIGHLOWFLAG | 高低标志 | string | H-偏高 L-偏低 HH-显著偏高 LL-显著偏低 P-阳性 |
| 12 | ISPANIC | 危急标志 | string | 0-非危急值项目 1-危急值项目 |
| 13 | BIOHAZARDFLAG | 传染病标志 | string | 0-非传染病项目 1-传染病项目 |



代码实现：

判断LIS_LIST.BIOHAZARDFLAG = 1 and STATUS >= 50

判断LIS_RESULT.BIOHAZARDFLAG = 1

根据参数判断：病人匹配方式（封装成公共方法组装SQL）

## 标本查询

### V 模块接口视图

| NO. | 接口编号 | 接口名称 | 说明 |
| --- | --- | --- | --- |
| 1 | 203.A01 | 请求初始化数据
../samplequery/request/getinitdata | 获取标本查询的初始化数据，科室/用户信息/病区/采集人等下拉列表数据 |
| 2 | 203.B01 | 标本查询
.samplequery/request/getsamplelist | 获取根据相关条件查询的样本信息列表 |
| 3 | 203.B02 | 条码替换
samplequery/operate/replasampletxm | 分为替换条码和替换并重新采样两种 |
| 4 | 202.B03 | 条码补打
../barcodebinding/operate/getsamplelist | 条码补打列表，打印条形码获取条形码列表数据 |
| 5 | 202.B06 | 取消绑定
./barcodebinding/operate/Cancelbarbinding | 分为根据条码和项目进行取消绑定 |
| 6 | 203.B03 | 条码替换/条码补打原因
.samplequery/request/getcanclereason | 替换条码/条码补打原因列表信息获取（含模糊查找） |
| 7 | 202.B07 | 获取新条码 /barcodebinding/request/createtxm | 替换条码时点击条码图标触发 |
| 8 | 202.B04 | 获取回执单信息
../barcodebinding/request/getsamplereturn | 获取标本取报告时间 |



### A 初始化数据

接口说明：获取标本签收的初始化数据，该用户可以签收的执行科室/用户信息等

URL：../ samplequery /request/getinitdata

实例URL:http://192.168.11.211:8098/lis/ sample /request/getinitdata

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 变量名 | 含义 | 含义 | 类型 | 备注说明 | 备注说明 |
| --- | --- | --- | --- | --- | --- | --- | --- |
| 节点名称：初始化数据及权限集合controlsparams类型：object | 节点名称：初始化数据及权限集合controlsparams类型：object | 节点名称：初始化数据及权限集合controlsparams类型：object | 节点名称：初始化数据及权限集合controlsparams类型：object | 节点名称：初始化数据及权限集合controlsparams类型：object | 节点名称：初始化数据及权限集合controlsparams类型：object | 节点名称：初始化数据及权限集合controlsparams类型：object | 节点名称：初始化数据及权限集合controlsparams类型：object |
| 1 | OP_TXMREPRINT | OP_TXMREPRINT | 条码补打 | 条码补打 | boolean | true-有条码补打的权限
false-没条码补打的权限 | true-有条码补打的权限
false-没条码补打的权限 |
| 2 | OP_TXMREPLACE | OP_TXMREPLACE | 条码替换 | 条码替换 | boolean | true-有条码替换的权限
false-没条替换的权限 | true-有条码替换的权限
false-没条替换的权限 |
| 3 | OP_CANCELBINDING | OP_CANCELBINDING | 取消绑定 | 取消绑定 | boolean | true-有取消绑定的权限
false-没有取消绑定的权限 | true-有取消绑定的权限
false-没有取消绑定的权限 |
| 4 | OP_ RECEIPTREPRINT | OP_ RECEIPTREPRINT | 回执单补打 | 回执单补打 | boolean | true-有回执单补打的权限
false-没有回执单补打的权限 | true-有回执单补打的权限
false-没有回执单补打的权限 |
| 执行科室字典 节点名称：applydeptlist类型：array <object> | 执行科室字典 节点名称：applydeptlist类型：array <object> | 执行科室字典 节点名称：applydeptlist类型：array <object> | 执行科室字典 节点名称：applydeptlist类型：array <object> | 执行科室字典 节点名称：applydeptlist类型：array <object> | 执行科室字典 节点名称：applydeptlist类型：array <object> | 执行科室字典 节点名称：applydeptlist类型：array <object> | 执行科室字典 节点名称：applydeptlist类型：array <object> |
| 1 | CODE | CODE | 字典代码 | 字典代码 | string | 字典代码，如：2110 | 字典代码，如：2110 |
| 2 | NAME | NAME | 字典名称 | 字典名称 | string | 字典名称，如：检验科 | 字典名称，如：检验科 |
| 3 | DICID | DICID | 字典ID | 字典ID | string | 字典ID，如：2110 | 字典ID，如：2110 |
| 4 | DICTYPE | DICTYPE | 字典类型 | 字典类型 | string | 字典类型，如：执行科室 | 字典类型，如：执行科室 |
| 5 | EXTERNCODE | EXTERNCODE | 外部码 | 外部码 | string | HIS外部代码 | HIS外部代码 |
| 6 | MEMCODE1 | MEMCODE1 | 输入码一 | 输入码一 | string | 输入码一 | 输入码一 |
| 7 | MEMCODE2 | MEMCODE2 | 输入码二 | 输入码二 | string | 输入码二 | 输入码二 |
| 8 | SUBSYSCODE | SUBSYSCODE | 系统代码 | 系统代码 | string | 系统代码，LIS默认为LIMS | 系统代码，LIS默认为LIMS |
| 9 | ORDERNO | ORDERNO | 序号 | 序号 | number | 排序序号，根据此字段来显示展示顺序 | 排序序号，根据此字段来显示展示顺序 |
| 病区节点名称：wardlist类型：array <object> | 病区节点名称：wardlist类型：array <object> | 病区节点名称：wardlist类型：array <object> | 病区节点名称：wardlist类型：array <object> | 病区节点名称：wardlist类型：array <object> | 病区节点名称：wardlist类型：array <object> | 病区节点名称：wardlist类型：array <object> | 病区节点名称：wardlist类型：array <object> |
| 1 | CODE | CODE | 字典代码 | 字典代码 | string | 字典代码，如：1411 | 字典代码，如：1411 |
| 2 | NAME | NAME | 字典名称 | 字典名称 | string | 字典名称，如：儿一科病区 | 字典名称，如：儿一科病区 |
| 3 | DICID | DICID | 字典ID | 字典ID | string | 字典ID，如：1411 | 字典ID，如：1411 |
| 4 | DICTYPE | DICTYPE | 字典类型 | 字典类型 | string | 字典类型，如：儿一科病区 | 字典类型，如：儿一科病区 |
| 5 | EXTERNCODE | EXTERNCODE | 外部码 | 外部码 | string | HIS外部代码 | HIS外部代码 |
| 6 | MEMCODE1 | MEMCODE1 | 输入码一 | 输入码一 | string | 输入码一 | 输入码一 |
| 7 | MEMCODE2 | MEMCODE2 | 输入码二 | 输入码二 | string | 输入码二 | 输入码二 |
| 8 | SUBSYSCODE | SUBSYSCODE | 系统代码 | 系统代码 | string | 系统代码，LIS默认为LIMS | 系统代码，LIS默认为LIMS |
| 9 | ORDERNO | ORDERNO | 序号 | 序号 | number | 排序序号，根据此字段来显示展示顺序 | 排序序号，根据此字段来显示展示顺序 |
| 标本状态节点名称：samplestatuslist类型：array <object> | 标本状态节点名称：samplestatuslist类型：array <object> | 标本状态节点名称：samplestatuslist类型：array <object> | 标本状态节点名称：samplestatuslist类型：array <object> | 标本状态节点名称：samplestatuslist类型：array <object> | 标本状态节点名称：samplestatuslist类型：array <object> | 标本状态节点名称：samplestatuslist类型：array <object> | 标本状态节点名称：samplestatuslist类型：array <object> |
| 1 | SAMPLESTATUSCODE | SAMPLESTATUSCODE | 标本状态code | 标本状态code | string | 标本状态代码，如：0 | 标本状态代码，如：0 |
| 2 | SAMPLESTATUSNAME | SAMPLESTATUSNAME | 标本状态名称 | 标本状态名称 | string | 标本状态名称，如：已签收 | 标本状态名称，如：已签收 |
| 抽血人节点名称：drawuserlist类型：array <object> | 抽血人节点名称：drawuserlist类型：array <object> | 抽血人节点名称：drawuserlist类型：array <object> | 抽血人节点名称：drawuserlist类型：array <object> | 抽血人节点名称：drawuserlist类型：array <object> | 抽血人节点名称：drawuserlist类型：array <object> | 抽血人节点名称：drawuserlist类型：array <object> | 抽血人节点名称：drawuserlist类型：array <object> |
| 1 | USERCODE | USERCODE | 字典代码 | 字典代码 | string | 字典代码，如：7104 | 字典代码，如：7104 |
| 2 | USERNAME | USERNAME | 字典名称 | 字典名称 | string | 字典名称，如：赵珊 | 字典名称，如：赵珊 |
| 3 | DICID | DICID | 字典ID | 字典ID | string | 字典ID，如：7104 | 字典ID，如：7104 |
| 4 | DICTYPE | DICTYPE | 字典类型 | 字典类型 | string | 字典类型，如：赵珊 | 字典类型，如：赵珊 |
| 5 | EXTERNCODE | EXTERNCODE | 外部码 | 外部码 | string | HIS外部代码 | HIS外部代码 |
| 6 | MEMCODE1 | MEMCODE1 | 输入码一 | 输入码一 | string | 输入码一 | 输入码一 |
| 7 | MEMCODE2 | MEMCODE2 | 输入码二 | 输入码二 | string | 输入码二 | 输入码二 |
| 8 | SUBSYSCODE | SUBSYSCODE | 系统代码 | 系统代码 | string | 系统代码，LIS默认为LIMS | 系统代码，LIS默认为LIMS |
| 9 | ORDERNO | ORDERNO | 序号 | 序号 | number | 排序序号，根据此字段来显示展示顺序 | 排序序号，根据此字段来显示展示顺序 |
| 节点名称：countlist类型：array<object> | 节点名称：countlist类型：array<object> | 节点名称：countlist类型：array<object> | 节点名称：countlist类型：array<object> | 节点名称：countlist类型：array<object> | 节点名称：countlist类型：array<object> | 节点名称：countlist类型：array<object> | 节点名称：countlist类型：array<object> |
| 1 | CODE | 节点code | 节点code | string | string | string | 如：001 |
| 2 | NODETYPE | 节点类型 | 节点类型 | string | string | string | 如：UNQUALIFIED |
| 3 | NODENAME | 节点描述 | 节点描述 | string | string | string | 如：不合格样本 |
| 4 | SUM | 数量 | 数量 | string | string | string | 如：10份 |
| 5 | PERCENT | 百分比 | 百分比 | string | string | string | 如：5% |
| 6 | AVAILABLE | 是否可用 | 是否可用 | boolean | boolean | boolean | true – 是 false -否(置灰)  默认为false |





说明：获取可签收执行科室信息，用于后面的扫码签收

JSON返回示例：

"controlsparams":{

" OP_CANCELBINDING ":TRUE

" OP_ TXMREPRINT”:"true",

" OP_ TXMREPLACE ":"false",

" OP_ RECEIPTREPRINT ":"false"

},

"execdeptlist":[

{

    "CODE":"2301",

    "NAME":"检验科",

    "DICID":"2301",

    "DICTYPE":"执行科室",

    "EXTERNCODE":"2301",

    "MEMCODE1":"2301",

    "MEMCODE2":"JYK",

    "SUBSYSCODE":"LIMS",

    "ORDERNO":"1"

},

{

    "CODE":"2302",

    "NAME":"内分泌实验室",

    "DICID":"2302",

    "DICTYPE":"执行科室",

    "EXTERNCODE":"2302",

    "MEMCODE1":"2302",

    "MEMCODE2":"NFMSYS",

    "SUBSYSCODE":"LIMS",

    "ORDERNO":"1"

}

"wardlist":[

{

    "CODE":"2301",

    "NAME":"检验科",

    "DICID":"2301",

    "DICTYPE":"执行科室",

    "EXTERNCODE":"2301",

    "MEMCODE1":"2301",

    "MEMCODE2":"JYK",

    "SUBSYSCODE":"LIMS",

    "ORDERNO":"1"

},

{

    "CODE":"2302",

    "NAME":"内分泌实验室",

    "DICID":"2302",

    "DICTYPE":"执行科室",

    "EXTERNCODE":"2302",

    "MEMCODE1":"2302",

    "MEMCODE2":"NFMSYS",

    "SUBSYSCODE":"LIMS",

    "ORDERNO":"1"

}

…….

]

"countlist":[

{

    "CODE":"001",

    " NODETYPE ":"TAT",

    " NODENAME ":"违反TAT规则",

    " SUM ":"0份",

    " PERCENT ":"",

    " AVAILABLE ":"false",



},

{



    "CODE":"002",

    " NODETYPE ":"UNQUALIFIED",

    " NODENAME ":"不合格样本",

    " SUM ":"0份",

    " PERCENT ":"",

    " AVAILABLE ":"false",

}

…….

]



代码实现：

表结构：SYS_DEPT\SETTINGS\SETTINGSDIC

代码实现：

获取执行科室列表，调用接口：900.A01，字典分类为：执行科室

### B 业务类

#### B01 标本查询

接口说明：通过点击查询标本列表按钮，获取符合选择条件的标本信息；

URL：../ samplequery /request/getsamplelist

实例URL:http://.....samplequery / samplequery /request/ getsamplelist

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | txm | 扫描的条形码 | string | 传入的条形码 |
| 2 | datetype | 时间类型 | string | 1-申请时间 2-绑定时间3-采样时间 |
| 3 | begindate | 开始时间 | string | 开始时间 ，如：2017-06-23 00:00:00 |
| 4 | enddate | 结束时间 | string | 结束时间， 如：2017-06-23 23:59:59 |
| 5 | cardno | 磁卡号 | string | 就诊号 对应HIS接口：syxh |
| 6 | hospno | 病员号 | string | 病人唯一号 对应HIS接口：patid |
| 7 | patname | 姓名 | string | 患者姓名 对应HIS接口：hzxm |
| 8 | samplestatuscode | 标本状态 | string | 标本状态代码，如：5-绑定 0-签收 1-入库 2-撤销签收 3-拒绝签收 |
| 9 | sampleconfirm | 采样确认 | string | 采样确认 1-已确认 0 -未确认 |
| 10 | ward | 病区代码 | string | 病区代码 如-2001 |
| 11 | bedno | 床号 | string | 床号 如 10 |
| 12 | applydeptcode | 申请科室代码 | string | 科室代码 如 2110 |
| 13 | drawusercode | 抽血人代码 | string | 抽血人代码 如 7104 |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 含义 | 含义 | 类型 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- | --- | --- | --- |
| 节点名称：countlist类型：array<object> | 节点名称：countlist类型：array<object> | 节点名称：countlist类型：array<object> | 节点名称：countlist类型：array<object> | 节点名称：countlist类型：array<object> | 节点名称：countlist类型：array<object> | 节点名称：countlist类型：array<object> | 节点名称：countlist类型：array<object> |
| 1 | CODE | 节点code | 节点code | 节点code | string | string | 如：001 |
| 2 | NODETYPE | 节点类型 | 节点类型 | 节点类型 | string | string | 如：UNQUALIFIED |
| 3 | NODENAME | 节点描述 | 节点描述 | 节点描述 | string | string | 如：不合格样本 |
| 4 | SUM | 数量 | 数量 | 数量 | string | string | 如：10份 |
| 5 | PERCENT | 百分比 | 百分比 | 百分比 | string | string | 如：5% |
| 6 | AVAILABLE | 是否可用 | 是否可用 | 是否可用 | boolean | boolean | true – 是 false -否(置灰)  默认为false |
| 节点名称：samplelist类型：array<object> | 节点名称：samplelist类型：array<object> | 节点名称：samplelist类型：array<object> | 节点名称：samplelist类型：array<object> | 节点名称：samplelist类型：array<object> | 节点名称：samplelist类型：array<object> | 节点名称：samplelist类型：array<object> | 节点名称：samplelist类型：array<object> |
| 1 | TXM | TXM | 条形码 | string | string | 初始条码为空，绑定成功后更改此属性 | 初始条码为空，绑定成功后更改此属性 |
| 2 | BRLX | BRLX | 病人类型 | string | string | 0-门诊 1-住院 3、4-体检 | 0-门诊 1-住院 3、4-体检 |
| 3 | PATIENTID | PATIENTID | 病人唯一号 | string | string | 病人唯一号 | 病人唯一号 |
| 4 | CURENO | CURENO | 就诊号 | string | string | 就诊号 | 就诊号 |
| 5 | PATNAME | PATNAME | 患者姓名 | string | string | 患者姓名 | 患者姓名 |
| 6 | HOSPNO | HOSPNO | 住院号 | string | string | 住院号 | 住院号 |
| 7 | CARDNO | CARDNO | 磁卡号 | string | string | 磁卡号 | 磁卡号 |
| 8 | SEX | SEX | 性别 | string | string | 性别 1-男 2-女 3-未知 | 性别 1-男 2-女 3-未知 |
| 9 | SEXDESC | SEXDESC | 性别描述 | string | string | 性别描述 | 性别描述 |
| 10 | AGE | AGE | 年龄 | string | string | 年龄 | 年龄 |
| 11 | AGEUNIT | AGEUNIT | 年龄单位 | number | number | 年龄单位，如：岁 | 年龄单位，如：岁 |
| 12 | AGE2 | AGE2 | 年龄2 | string | string | 年龄2 | 年龄2 |
| 13 | AGEUNIT2 | AGEUNIT2 | 年龄单位2 | number | number | 年龄单位 | 年龄单位 |
| 14 | AGEDESC | AGEDESC | 年龄 | string | string | 年龄描述 | 年龄描述 |
| 15 | WARD | WARD | 病区代码 | string | string | 病区代码 | 病区代码 |
| 16 | WARDNAME | WARDNAME | 病区名称 | string | string | 病区名称 | 病区名称 |
| 17 | BEDNO | BEDNO | 床号 | string | string | 床号，如：+10床 | 床号，如：+10床 |
| 18 | CLINICDESC | CLINICDESC | 临床诊断 | string | string | 临床诊断，如：感冒 | 临床诊断，如：感冒 |
| 19 | CHARGETYPECODE | CHARGETYPECODE | 费别代码 | string | string | 费别代码 | 费别代码 |
| 20 | CHARGETYPENAME | CHARGETYPENAME | 费别名称 | string | string | 费别名称 | 费别名称 |
| 21 | BAREXAMCODE | BAREXAMCODE | 分组代码 | string | string | 条码分组代码 | 条码分组代码 |
| 21 | BAREXAMNAME | BAREXAMNAME | 分组名称 | string | string | 条码分组名称 | 条码分组名称 |
| 22 | IMAGEURL | IMAGEURL | 试管地址 | string | string | 用于加载试管图片，相对地址 | 用于加载试管图片，相对地址 |
| 23 | BARCOLOR | BARCOLOR | 试管颜色 | string | string | 十六进制颜色值 | 十六进制颜色值 |
| 24 | SAMPLECODE | SAMPLECODE | 样本类型 | string | string | 样本类型代码 | 样本类型代码 |
| 25 | SAMPLENAME | SAMPLENAME | 样本名称 | string | string | 样本类型名称 | 样本类型名称 |
| 26 | EXAMGROUPNAME | EXAMGROUPNAME | 送检小组 | string | string | 送检小组，如：检验科 | 送检小组，如：检验科 |
| 27 | EXAMGROUPCODE | EXAMGROUPCODE | 送检小组代码 | string | string | 送检小组代码，如：0002 | 送检小组代码，如：0002 |
| 28 | UNQUALIFIED | UNQUALIFIED | 不合格样本 | string | string | 1-是不合格样本  0-不是不合格样本 | 1-是不合格样本  0-不是不合格样本 |
| 29 | TAT | TAT | 违反TAT规则 | string | string | 1-是 0-否 | 1-是 0-否 |
| 30 | CANCELREGSAMPLE | CANCELREGSAMPLE | 撤销签收标本 | string | string | 1-是 0-否 | 1-是 0-否 |
| 31 | REPORTDELAY | REPORTDELAY | 延迟出报告 | string | string | 1-是 0-否 | 1-是 0-否 |
| 32 | TESTING | TESTING | 检测中的报告 | string | string | 1-是 0-否 | 1-是 0-否 |
| 33 | UNTEST | UNTEST | 未上机标本 | string | string | 1-是 0-否 | 1-是 0-否 |
| 34 | UNUPDATE | UNUPDATE | 未采样更新标本 | string | string | 1-是 0-否 | 1-是 0-否 |
| 27 | ITEMLIST | ITEMLIST | 收费项目 | array | array | 收集项目列表 | 收集项目列表 |
| 子节点内容： | 子节点内容： | 子节点内容： | 子节点内容： | 子节点内容： | 子节点内容： | 子节点内容： | 子节点内容： |
| 1 | HISORDERCODE | HISORDERCODE | 项目代码 | string | string | HIS项目代码 | HIS项目代码 |
| 2 | HISORDERNAME | HISORDERNAME | 项目名称 | string | string | HIS项目名称 | HIS项目名称 |
| 3 | ITEMTYPE | ITEMTYPE | 项目分类 | string | string | 0-临床 1-收费 | 0-临床 1-收费 |
| 4 | LISORDERCODE | LISORDERCODE | LIS代码 | string | string | 对应LIS_HISITEM.LISORDERCODE | 对应LIS_HISITEM.LISORDERCODE |
| 5 | ITEMQTY | ITEMQTY | 项目数量 | string | string | 项目数量，如：1.5 | 项目数量，如：1.5 |
| 6 | PRICE | PRICE | 单价 | string | string | 单价，如：45.00 | 单价，如：45.00 |
| 7 | APPLYTIME | APPLYTIME | 申请时间 | string | string | 如：2017-06-26 09:34:23 | 如：2017-06-26 09:34:23 |
| 8 | APPLYDEPTCODE | APPLYDEPTCODE | 申请科室 | string | string | 申请科室代码(LIS内码），如：2353 | 申请科室代码(LIS内码），如：2353 |
| 9 | APPLYDEPTNAME | APPLYDEPTNAME | 科室名称 | string | string | 申请科室名称(LIS内码），如：内科 | 申请科室名称(LIS内码），如：内科 |
| 10 | APPLYDOCCODE | APPLYDOCCODE | 申请医生 | string | string | 申请医生代码(LIS内码)，如：2839 | 申请医生代码(LIS内码)，如：2839 |
| 11 | APPLYDOCNAME | APPLYDOCNAME | 医生姓名 | string | string | 申请医生姓名(LIS内码)，如：万一阁 | 申请医生姓名(LIS内码)，如：万一阁 |
| 12 | EXECDEPTCODE | EXECDEPTCODE | 执行科室 | string | string | 执行科室代码(LIS内码)，如：4300 | 执行科室代码(LIS内码)，如：4300 |
| 13 | EXECDEPTNAME | EXECDEPTNAME | 科室名称 | string | string | 执行科室名称(LIS内码)，如：检验科 | 执行科室名称(LIS内码)，如：检验科 |
| 14 | CHARGEFLAG | CHARGEFLAG | 收费 | string | string | 0-未收费 1-已收费 2-退费 | 0-未收费 1-已收费 2-退费 |
| 15 | CHARGEFLAGDESC | CHARGEFLAGDESC | 收费描述 | string | string | 收费中文描述，如：未收费、已收费、已退费 | 收费中文描述，如：未收费、已收费、已退费 |
| 16 | SAMPLECODE | SAMPLECODE | 样本类型 | string | string | 样本类型代码 | 样本类型代码 |
| 17 | SAMPLENAME | SAMPLENAME | 样本名称 | string | string | 样本类型名称 | 样本类型名称 |
| 18 | GHXH | GHXH | 挂号序号 | string | string | 挂号序号，针对门诊 | 挂号序号，针对门诊 |
| 19 | ENTRUST | ENTRUST | 嘱托 | string | string | 嘱托内容 | 嘱托内容 |





 JSON返回示例：

"countlist":[

{

    "CODE":"001",

    " NODETYPE ":"TAT",

    " NODENAME ":"违反TAT规则",

    " SUM ":"10份",

    " PERCENT ":"5%",

    " AVAILABLE ":"true",



},

{



    "CODE":"002",

    " NODETYPE ":"UNQUALIFIED",

    " NODENAME ":"不合格样本",

    " SUM ":"10份",

    " PERCENT ":"5%",

    " AVAILABLE ":"true",

}

…….

]

"sampleviewlist":[

{

    "TXM":"130000834945",

..................

" ITEMLIST ":[

{

" HISORDERCODE ":"065",

..................

" GHXH ":"65",

" ENTRUST ":"嘱托1"

},

{

" HISORDERCODE ":"066",

..................

" GHXH ":"65",

" ENTRUST ":"嘱托2"

},

]

},

{

    "TXM":"130000834946",

..................

" ITEMLIST ":[

{

" HISORDERCODE ":"067",

..................

" GHXH ":"65",

" ENTRUST ":"嘱托3"

},

{

" HISORDERCODE ":"068",

..................

" GHXH ":"65",

" ENTRUST ":"嘱托4"

},

]

}

]

相关表结构：LIS_ORDERMASTER\LIS_ACCEPTITEMS\SLAVE\

代码实现：

根据条件从标本信息表中获取

#### B02 条码替换

接口说明：通过点击查询标本列表按钮，获取符合选择条件的标本信息；

URL：../ samplequery /request/replacesampletxm

实例URL:http://...../lis/ samplequery /request/replacesampletxm

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | txm | 条形码 | string | 传入的条形码 |
| 2 | newtxm | 新的条形码 | string | 条形码 |
| 3 | reason | 替换原因 | string | 替换原因 |
| 4 | replacetype | 替换类型 | string | 1-替换并重新采样 
0-仅替换条码 |
| 5 | printtxm | 替换后打印条码 | string | 0-不打印 1-打印 默认为1 |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：barlist 打印的条码信息 类型：array<object> 一个条码占一行记录 | 节点名称：barlist 打印的条码信息 类型：array<object> 一个条码占一行记录 | 节点名称：barlist 打印的条码信息 类型：array<object> 一个条码占一行记录 | 节点名称：barlist 打印的条码信息 类型：array<object> 一个条码占一行记录 | 节点名称：barlist 打印的条码信息 类型：array<object> 一个条码占一行记录 |
| 1 | WARDORREG | 病人类型代码 | string | 病人类型代码，如：1 |
| 2 | WARDORREGNAME | 病人类型名称 | string | 病人类型名称，如：住院 |
| 3 | BRLX | 病人类型 | string | HIS病人类型 |
| 4 | PATIENTID | 病人唯一号 | string | 病人唯一号 |
| 5 | CURENO | 就诊号 | string | 就诊号 |
| 6 | PATNAME | 患者姓名 | string | 患者姓名 |
| 7 | HOSPNO | 住院号 | string | 住院号 |
| 8 | CARDNO | 磁卡号 | string | 磁卡号 |
| 9 | TXM | 条形码 | string | 条形码 |
| 10 | SEXDESC | 性别 | string | 性别 |
| 11 | AGEDESC | 年龄 | string | 年龄描述 |
| 12 | WARD | 病区代码 | string | 病区代码 |
| 13 | WARDNAME | 病区名称 | string | 病区名称 |
| 14 | BEDNO | 床号 | string | 床号 |
| 15 | SAMPLECODE | 标本代码 | string | 标本代码 |
| 16 | SAMPLENAME | 标本名称 | string | 标本名称 |
| 17 | ITEMNUM | 项目总数 | number | 当前条码下的条码总数 |
| 18 | SUMPRICE | 项目金额 | number | 当前条码下的总金额 |
| 19 | ITEMNAME | 项目汇总 | string | 项目名称集合，HISORDERNAME+CHAR(13) |
| 20 | ITEMNAME_HOR | 项目汇总 | string | 项目名称集合_横向，HISORDERNAME；+分隔 |
| 21 | ITEMNAMEJC | 项目汇总 | string | 项目简称汇总，BARCODELABELNAME+CHAR(13)
BARCODELABELNAME为空时取HISORDERNAME |
| 22 | ITEMNAMEJC_HOR | 项目汇总 | string | 项目简称汇总，BARCODELABELNAME+CHAR(13)
BARCODELABELNAME为空时取HISORDERNAME |
| 23 | MJZBZ | 加急标志 | string | 0-常规 
1-加急 |
| 24 | ENTRUST | 嘱托 | string | 嘱托内容，用逗号分隔；取LIS_ACCEPTITEMS.ENTRUST |
| 25 | CLINICDESC | 临床诊断 | string | 临床诊断内容，LIS_ORDERMASTER.CLINICDESC |
| 26 | CXSJ | 绑定时间 | string | 格式：yyyy-MM-dd HH:mm:ss |
| 27 | DRAWDATE | 采样时间 | string | 格式：yyyy-MM-dd HH:mm:ss |
| 28 | DRAWUSERCODE | 采样人工号 | string | 采样人工号 |
| 29 | DRAWUSERNAME | 采样人姓名 | string | 采样人姓名 |
| 30 | EXAMGROUPCODE | 送检小组代码 | string | 送检小组代码 |
| 31 | EXAMGROUPNAME | 送检小组名称 | string | 送检小组名称 |
| 32 | BAREXAMCODE | 分组代码 | string | 分组代码 |
| 33 | BAREXAMNAME | 分组名称 | string | 分组名称 |
| 34 | BARDESCRIBE | 分组描述 | string | 如：紫色 |
| 35 | BARRANGE | 适用范围 | string | 适用范围 |
| 36 | BARCUBAGE | 采血量 | string | 如：5ml |
| 37 | BARADDITIVENAME | 添加剂 | string | 如：抗凝剂 |
| 38 | BARNOTICE | 采集事项 | string | 如：空腹，混匀5次 |
| 39 | BARPRIORITY | 优先次序 | number | 用于排序，按此字段先后顺序进行排序打印 |
| 40 | SUBMISSIONPLACE | 送检地点 | string | 送检地点 |
| 41 | PRINTCOUNT | 打印份数 | number | 打印份数
BRLX+LIS_BAREXAMCLASS |
| 42 | GROUPBARCOUNT | 组条码数 | number | 一组条码中的条码数量 |
| 43 | GROUPBARNO | 组条码序号 | number | 一组条码中的序号 |
| 44 | BARGROUPNO | 组号 | string | 格式：yyyyMMDD+流水号
按人员区分 |
| 45 | TARGETLAB | 检测机构 | string | 检测机构代码 LIS_ORDERMASTER.TARGETLAB |
| 46 | TARGETLABNAME | 机构名称 | string | 检测机构名称 SYS_HOSPITALINFO.HOSPITALNAME |
| 47 | TAGETLABJC | 机构简称 | string | 检测机构简称
SYS_HOSPITALINFO.HOSPITALSHORTNAME |





JSON返回示例：

"barlist":[

{

"WARDORREG":"1",

"WARDORREG":"住院",

    "BRLX":"1",

"PATIENTID":2344321",

"CURENO":2344321",

...................

"TARGETLAB":"43023002322",

"TARGETLABNAME":"上海市第六人民医院",

"TARGETLABJC":"上海六院"

},

{

"WARDORREG":"1",

"WARDORREG":"住院",

    "BRLX":"1",

"PATIENTID":2344321",

"CURENO":2344321",

...................

"TARGETLAB":"43023002322",

"TARGETLABNAME":"上海市第六人民医院",

"TARGETLABJC":"上海六院"

}

]

代码实现：

相关表结构：LIS_ORDERMASTER\LIS_ACCEPTITEMS\SLAVE\

printtxm=1时，调用接口 202.B03获取条形码数据

		当选择了左下角‘替换成功后自动打印条码’返回数据，并调用打印条码接口

	替换并重新采样：替换当前条码，并将条码状态置为绑定状态

替换条码：仅替换当前条码，不处理当前样本的状态

#### B03 条码替换/补打原因

接口说明：通过点击查询标本列表按钮，获取符合选择条件的标本信息；

URL：../ samplequery /request/getcanclereason

实例URL:http://...../lis/samplequery /request/getcanclereason

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | classcode | 字典的类型 | string | 字典类型； 如：条码替换原因/条码补打原因 |
| 2 | brlx | 病人类型 | string | 0-门诊 1-住院 3、4-体检 |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 含义 | 类型 | 类型 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- | --- | --- | --- |
| 节点名称：commonreasonlist类型：array<object> | 节点名称：commonreasonlist类型：array<object> | 节点名称：commonreasonlist类型：array<object> | 节点名称：commonreasonlist类型：array<object> | 节点名称：commonreasonlist类型：array<object> | 节点名称：commonreasonlist类型：array<object> | 节点名称：commonreasonlist类型：array<object> | 节点名称：commonreasonlist类型：array<object> |
| 1 | CODE | CODE | 字典代码 | 字典代码 | string | 字典代码，如：01 | 字典代码，如：01 |
| 2 | NAME | NAME | 字典名称 | 字典名称 | string | 字典名称，如：条码不清晰 | 字典名称，如：条码不清晰 |
| 3 | DICID | DICID | 字典ID | 字典ID | string | 字典ID，如：01 | 字典ID，如：01 |
| 4 | DICTYPE | DICTYPE | 字典类型 | 字典类型 | string | 字典类型，如：标本替换原因 | 字典类型，如：标本替换原因 |
| 5 | EXTERNCODE | EXTERNCODE | 外部码 | 外部码 | string | HIS外部代码 | HIS外部代码 |
| 6 | MEMCODE1 | MEMCODE1 | 输入码一 | 输入码一 | string | 输入码一 | 输入码一 |
| 7 | MEMCODE2 | MEMCODE2 | 输入码二 | 输入码二 | string | 输入码二 | 输入码二 |
| 8 | SUBSYSCODE | SUBSYSCODE | 系统代码 | 系统代码 | string | 系统代码，LIS默认为LIMS | 系统代码，LIS默认为LIMS |
| 9 | ORDERNO | ORDERNO | 序号 | 序号 | number | 排序序号，根据此字段来显示展示顺序 | 排序序号，根据此字段来显示展示顺序 |
| 节点名称：reasonlist类型：array<object> | 节点名称：reasonlist类型：array<object> | 节点名称：reasonlist类型：array<object> | 节点名称：reasonlist类型：array<object> | 节点名称：reasonlist类型：array<object> | 节点名称：reasonlist类型：array<object> | 节点名称：reasonlist类型：array<object> | 节点名称：reasonlist类型：array<object> |
| 1 | CODE | CODE | 字典代码 | 字典代码 | string | 字典代码，如：01 | 字典代码，如：01 |
| 2 | NAME | NAME | 字典名称 | 字典名称 | string | 字典名称，如：条码不清晰 | 字典名称，如：条码不清晰 |
| 3 | DICID | DICID | 字典ID | 字典ID | string | 字典ID，如：01 | 字典ID，如：01 |
| 4 | DICTYPE | DICTYPE | 字典类型 | 字典类型 | string | 字典类型，如：标本替换原因 | 字典类型，如：标本替换原因 |
| 5 | EXTERNCODE | EXTERNCODE | 外部码 | 外部码 | string | HIS外部代码 | HIS外部代码 |
| 6 | MEMCODE1 | MEMCODE1 | 输入码一 | 输入码一 | string | 输入码一 | 输入码一 |
| 7 | MEMCODE2 | MEMCODE2 | 输入码二 | 输入码二 | string | 输入码二 | 输入码二 |
| 8 | SUBSYSCODE | SUBSYSCODE | 系统代码 | 系统代码 | string | 系统代码，LIS默认为LIMS | 系统代码，LIS默认为LIMS |
| 9 | ORDERNO | ORDERNO | 序号 | 序号 | number | 排序序号，根据此字段来显示展示顺序 | 排序序号，根据此字段来显示展示顺序 |





JSON返回示例：

"commonreasonlist":[

{

     " CODE ":"23",

..................

" NAME ":"条码不规范",

}

{

     " CODE ":"24",

..................

" NAME ":"条码不规范2",

}

]

"reasonlist":[

{

     " CODE ":"23",

..................

" NAME ":"条码不规范",

}

{

     " CODE ":"24",

..................

" NAME ":"条码不规范2",

}

]





相关表结构：LIS_ORDERMASTER\LIS_ACCEPTITEMS\SLAVE\

代码实现：

## 住院采集



### V 模块接口视图

| NO. | 接口编号 | 接口名称 | 说明 |
| --- | --- | --- | --- |
| 1 | 201.A01 | 请求初始化数据
../zysampling/request/getinitdata | 请求住院采集需要的初始化数据及控制参数信息 |
| 2 | 201.B01 | 化验病人查询
../zysampling/request/getsicklist | 根据病区或护理单元获取需要采集样本的患者列表 |
| 3 | 201.B02 | 下载医嘱并分组
../zysampling/request/getpatorders | 根据患者的医嘱并进行条码分组 |
| 4 | 900.A05 | 获取收费项目标本对照
../common/request/getordersamplelist | 获取传入指定项目的标本类型集合 |
| 5 | 900.B01 | 获取模块快捷栏菜单
../common/request/getmoduleshortcut | 用于获取模块对应的快捷菜单，含菜单名称及图标 |
| 6 | 201.B03 | 医嘱分组
../zysampling/request/grouppatorders | 更改医嘱信息后需要重新对医嘱数据进行分组 |
| 7 | 202.B01 | 条码打印-预印条码
../barcodebinding/operate/barbinding | 预印条码绑定条码 |
| 8 | 202.B02 | 条码打印-打印条码
../barcodebinding/operate/newbarandbinding | 按规则自动生成条码并绑定条码 |
| 9 | 201.B04 | 批量打印条码
../zysampling/request/multiconfirmpatorders | 根据选择的患者列表信息批量打印患者条码 |
| 10 | 202.B03 | 获取条形码信息（打印使用）
../barcodebinding/request/getsamplelist | 打印条形码获取条形码列表数据 |
| 11 | 201.B05 | 获取医嘱提示信息
../zysampling/request/summarypatorders | 选择会更改选择医嘱后汇总提示信息 |



### A 初始化数据

接口说明：请求住院采集需要的初始化数据及控制参数信息

请求URL：../zysampling/request/getinitdata

示例URL：http://192.168.14.253:8080/lis/zysampling/request/getinitdata

原型参考：





接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码 |
| 2 | userid | 用户id | string | 用户id，允许为空
传入为空时获取当前用户userid |
| 3 | pcname | 计算机信息 | string | 格式：计算机名(ip地址），允许为空
传入为空时获取当前用户所属的计算机 |



说明：传入医疗机构主要是考虑后续住院采集会挂入其他系统中使用，需传入医疗机构代码和用户等信息

接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：controlsparams类型：object | 节点名称：controlsparams类型：object | 节点名称：controlsparams类型：object | 节点名称：controlsparams类型：object | 节点名称：controlsparams类型：object |
| 1 | DOWNLOADDAYS | 下载天数 | number | 默认为：30天 |
| 2 | WARDCAPTION | 过滤标题 | string | 如：病区、护理单元

默认为：病区 |
| 3 | DEFWARDVALUE | 默认病区 | string | 默认病区或护理单元代码 |
| 4 | CANSELECT | 是否允许选择 | boolean | false-不允许选择
true-允许选择 |
| 5 | SINGLECONFIRM | 单人确认 | boolean | false-没有单人确认操作权限，按钮-不可用
true-有单人确认操作权限，按钮-可用 |
| 6 | MULTICONFIRM | 多人确认 | boolean | false-没有多人确认操作权限，按钮-不可用
true-有多人确认操作权限，按钮-可用
没有多人确认的权限，不允许全选或选择操作 |
| 7 | TXMMODE | 条码模式 | string | P-打印条码 Y-预制条码 A-打印和预印条码兼有
预印(Y\|A)-
打印（P\|A）- |
| 8 | EDITDOWNDAYS | 编辑下载天数 | boolean | false-不允许编辑（可不显示）
true-允许编辑（显示并能选择更改） |
| 节点名称：diclist 类型：array<object> | 节点名称：diclist 类型：array<object> | 节点名称：diclist 类型：array<object> | 节点名称：diclist 类型：array<object> | 节点名称：diclist 类型：array<object> |
|  | diclist[0] 节点名称：下载医嘱天数  类型：array<object> | diclist[0] 节点名称：下载医嘱天数  类型：array<object> | diclist[0] 节点名称：下载医嘱天数  类型：array<object> | diclist[0] 节点名称：下载医嘱天数  类型：array<object> |
| 1 | CODE | 字典代码 | string | 字典代码，如：7 |
| 2 | NAME | 字典名称 | string | 字典名称，如：7天 |
| 3 | DICID | 字典ID | string | 字典ID，如：7 |
| 4 | DICTYPE | 字典类型 | string | 字典类型，如：下载医嘱天数 |
| 5 | EXTERNCODE | 外部码 | string | HIS外部代码 |
| 6 | MEMCODE1 | 输入码一 | string | 输入码一 |
| 7 | MEMCODE2 | 输入码二 | string | 输入码二 |
| 8 | SUBSYSCODE | 系统代码 | string | 系统代码，LIS默认为LIMS |
| 9 | ORDERNO | 序号 | number | 排序序号，根据此字段来显示展示顺序 |
|  | diclist[1] 节点名称：申请病区  类型：array<object> | diclist[1] 节点名称：申请病区  类型：array<object> | diclist[1] 节点名称：申请病区  类型：array<object> | diclist[1] 节点名称：申请病区  类型：array<object> |
| 1 | CODE | 字典代码 | string | 字典代码，如：2309 |
| 2 | NAME | 字典名称 | string | 字典名称，如：骨科病区 |
| 3 | DICID | 字典ID | string | 字典ID，如：2309 |
| 4 | DICTYPE | 字典类型 | string | 字典类型，如：申请病区 |
| 5 | EXTERNCODE | 外部码 | string | HIS外部代码 |
| 6 | MEMCODE1 | 输入码一 | string | 输入码一 |
| 7 | MEMCODE2 | 输入码二 | string | 输入码二 |
| 8 | SUBSYSCODE | 系统代码 | string | 系统代码，LIS默认为LIMS |
| 9 | ORDERNO | 序号 | number | 排序序号，根据此字段来显示展示顺序 |





说明：申请病区列表获取需按用户权限来划分，如果当前用户管理多个病区，则只返回管理的病区即可，否则根据参数来判断病区获取方式；

JSON返回示例：

"controlsparams":{

"DOWNLOADDAYS":"30",

"WARDCAPTION":"病区",

"DEFWARDVALUE":"2309",

"CANSELECT":"false",

"SINGLECONFIRM":"true",

"MULTICONFIRM":"true",

"TXMMODE":"P",

"EDITDOWNDAYS":"true"

},

"diclist":{

"下载医嘱天数":[

{

    "CODE":"7",

    "NAME":"7天",

    "DICID":"7",

    "DICTYPE":"下载医嘱天数",

    "EXTERNCODE":"7",

    "MEMCODE1":"7",

    "MEMCODE2":"7t",

    "SUBSYSCODE":"LIMS",

    "ORDERNO":"1"

},

{

    "CODE":"15",

    "NAME":"15天",

    "DICID":"15",

    "DICTYPE":"下载医嘱天数",

    "EXTERNCODE":"15",

    "MEMCODE1":"15",

    "MEMCODE2":"15t",

    "SUBSYSCODE":"LIMS",

    "ORDERNO":"2"

}

]

,

"申请病区":[

{

    "CODE":"2309",

    "NAME":"骨科一病区",

    "DICID":"2309",

    "DICTYPE":"申请病区",

    "EXTERNCODE":"2309",

    "MEMCODE1":"2309",

    "MEMCODE2":"gkybq",

    "SUBSYSCODE":"LIMS",

    "ORDERNO":"1"

},

{

    "CODE":"2310",

    "NAME":"骨科二病区",

    "DICID":"2310",

    "DICTYPE":"申请病区",

    "EXTERNCODE":"2310",

    "MEMCODE1":"2310",

    "MEMCODE2":"gkebq",

    "SUBSYSCODE":"LIMS",

    "ORDERNO":"2"

}

]

}

代码实现：

表结构：SLAVE\SYS_DEPT

代码实现：

申请病区：SYS_DEPT.DEPTTYPE = 2

下载医嘱天数：SLAVE.CLASSCODE = 下载医嘱天数

停用的病区和字典不返回

参考SQL：

select * from SLAVE where CLASSCODE=’下载医嘱天数’ and HOSPITALCODE=.......

select * from SYS_DEPT WHERE DEPTTYPE=’2’ and HOSPITALCODE=.......

### B 业务类

#### ※B01 化验病人查询

接口说明：根据病区或护理单元获取需要采集样本的患者列表

请求URL：../zysampling/request/getsicklist

代码文件：winning.lis.zysampling.service.ZySamplingServerice

示例URL：http://192.168.14.253:8080/lis/zysampling/request/getsicklist?ward=2908&downloaddays=30&hospitalcode=9999&brlx=1

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | ward | 病区代码 | string | 病区或护理单元代码 |
| 2 | downloaddays | 下载天数 | string | 如：30 |
| 3 | brlx | 病人类型 | string | 0-门诊 1-住院 3、4-体检
住院采集传 1 |
| 4 | hospitalcode | 机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码 |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- | --- |
| 节点名称：sickinfo  汇总信息 类型：object | 节点名称：sickinfo  汇总信息 类型：object | 节点名称：sickinfo  汇总信息 类型：object | 节点名称：sickinfo  汇总信息 类型：object | 节点名称：sickinfo  汇总信息 类型：object | 节点名称：sickinfo  汇总信息 类型：object |
| 1 | MJZCNT | 加急数量 | 加急数量 | number | 加急采样数量 |
| 2 | NORCNT | 常规数量 | 常规数量 | number | 常规采样数量 |
| 3 | ALLCNT | 所有采样数 | 所有采样数 | number | 所有采样数量 |
| 节点名称：sicklist类型：array<object> | 节点名称：sicklist类型：array<object> | 节点名称：sicklist类型：array<object> | 节点名称：sicklist类型：array<object> | 节点名称：sicklist类型：array<object> | 节点名称：sicklist类型：array<object> |
| 操作属性 | 操作属性 | 操作属性 | 操作属性 | 操作属性 | 操作属性 |
| 1 | OP_CHECK | OP_CHECK | 选择 | string | 0-未选择
1-选择
默认为1 |
| 需要采血的病人列表 | 需要采血的病人列表 | 需要采血的病人列表 | 需要采血的病人列表 | 需要采血的病人列表 | 需要采血的病人列表 |
| 0 | BRLX | BRLX | 病人类型 | string | 0-门诊 1-住院 3、4-体检
住院采集默认为 1 |
| 1 | PATIENTID | PATIENTID | 病人唯一号 | string | 病人唯一号
对应HIS接口：patid |
| 2 | CURENO | CURENO | 就诊号 | string | 就诊号
对应HIS接口：syxh |
| 3 | PATNAME | PATNAME | 患者姓名 | string | 患者姓名
对应HIS接口：hzxm |
| 4 | HOSPNO | HOSPNO | 住院号 | string | 住院号
对应HIS接口：blh |
| 5 | CARDNO | CARDNO | 磁卡号 | string | 磁卡号
对应HIS接口：cardno |
| 6 | SEX | SEX | 性别 | string | 性别 1-男 2-女 3-未知
对应HIS接口：sex - 需转换 |
| 7 | SEXDESC | SEXDESC | 性别描述 | string | 性别描述
对应HIS接口：sex |
| 8 | AGE | AGE | 年龄 | string | 年龄
对应HIS接口：nl或age（需判断属性是否存在并且是数字型） |
| 9 | AGEUNIT | AGEUNIT | 年龄单位 | number | 年龄单位，如：岁
对应HIS接口：ageunit（需判断属性是否存在） |
| 10 | AGE2 | AGE2 | 年龄2 | string | 年龄2
对应HIS接口：age2（需判断属性是否存在并且是数字型） |
| 11 | AGEUNIT2 | AGEUNIT2 | 年龄单位2 | number | 年龄单位，如：日
对应HIS接口：ageunit2（需判断属性是否存在） |
| 12 | AGEDESC | AGEDESC | 年龄 | string | 年龄描述，如：23岁
对应HIS接口：agedesc（需判断属性是否存在） |
| 13 | WARD | WARD | 病区代码 | string | 病区代码
对应HIS接口：bqdm（需转换为内码） |
| 14 | WARDNAME | WARDNAME | 病区名称 | string | 病区名称
对应HIS接口：bqmc（需转换为内码） |
| 15 | BEDNO | BEDNO | 床号 | string | 床号，如：+10床
对应HIS接口：cw |
| 16 | CLINICDESC | CLINICDESC | 临床诊断 | string | 临床诊断，如：感冒
对应HIS接口：clinicdesc |
| 17 | CHARGETYPECODE | CHARGETYPECODE | 费别代码 | string | 费别代码
对应HIS接口：fbdm（需转换为内码） |
| 18 | CHARGETYPENAME | CHARGETYPENAME | 费别名称 | string | 费别名称
对应HIS接口：fbdm（需转换为内码） |
| 19 | YEBZ | YEBZ | 婴儿标志 | string | 对应HIS接口：yebz |
| 20 | YEXH | YEXH | 婴儿序号 | string | 对应HIS接口：yexh |
| 21 | TIMES | TIMES | 住院次数 | string | 对应HIS接口：times（需判断属性是否存在） |
| 22 | PHONE | PHONE | 联系电话 | string | 对应HIS接口：phone |
| 23 | MJZBZ | MJZBZ | 加急标志 | string | 对应HIS接口：mjzbz（需判断属性是否存在）
MJZBZ=1 或 -1，患者姓名显示红色
1-加急 0-常规 -1-既有加急也有常规 |
| 24 | ADDRESS | ADDRESS | 地址 | string | 对应HIS接口：address（需判断属性是否存在） |
| 25 | IDNUM | IDNUM | 身份证 | string | 对应HIS接口：idnum（需判断属性是否存在） |
| 26 | BIRTHDAY | BIRTHDAY | 生日 | string | 对应HIS接口：birthday（需判断属性是否存在） |
| 27 | NATION | NATION | 民族 | string | 对应HIS接口：nation（需判断属性是否存在） |
| 28 | EMPIID | EMPIID | EMPID | string | 对应HIS接口：empiid（需判断属性是否存在） |
| 29 | ICD10 | ICD10 | ICD10 | string | 对应HIS接口：icd10（需判断属性是否存在） |
| 30 | ICD10NAME | ICD10NAME | ICD名称 | string | 对应HIS接口：icd10name（需判断属性是否存在） |





JSON返回示例：

"sickinfo":[

{

    "MJZCNT":"5",

"NORCNT":6",

"ALLCNT":"11"

},

"sicklist":[

{

    "OP_CHECK":"1",

"PATIENTID":2344321",

"CURENO":2344321",

...................

    "EMPIID":"12220",

    "ICD10":"B11.022",

    "ICD10NAME":"霍乱"

},

{

    "OP_CHECK":"1",

"PATIENTID":2344321",

"CURENO":2344321",

...................

    "EMPIID":"12220",

    "ICD10":"B11.022",

    "ICD10NAME":"霍乱"

}

]

代码实现：

调用HIS接口：getLabPatientList

需要进行内部转换的进行数据转换,转换成内部码

相关表结构：SLAVE\SYS_DEPT

#### ※B02 下载医嘱并分组

接口说明：根据患者的医嘱并进行条码分组

请求URL：../zysampling/request/getpatorders

代码文件：winning.lis.zysampling.service.ZySamplingServerice

示例URL：http://192.168.14.253:8080/lis/zysampling/request/getpatorders?brlx=1&patientid=329323&cureno=239232&times=1&yexh=0&downloaddays=30&hospitalcode=9999&mjzbz=-1

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | brlx | 病人类型 | string | 0-门诊 1-住院 3、4-体检
取201.B01.BRLX |
| 2 | patientid | 病人唯一号 | string | 取201.B01.PATIENTID |
| 3 | cureno | 就诊号 | string | 取201.B01.CURENO |
| 4 | times | 住院次数 | string | 住院次数，根据患者信息返回来确定
门诊为0 ，住院默认为1 |
| 5 | yexh | 婴儿序号 | string | 0-非婴儿
>0-婴儿序号，表示为婴儿 |
| 6 | hospitalcode | 机构代码 | string | 取当前机构代码 |
| 7 | downloaddays | 下载医嘱天数 | string | 前端选择的下载医嘱天数，默认为30 |
| 8 | mjzbz | 加急标志 | number | -1-表示显示所有的医嘱，含加急+常规
0-表示仅显示常规项目
1-表示仅显示加急项目
默认为-1 |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：orderinfo  汇总信息 类型：object | 节点名称：orderinfo  汇总信息 类型：object | 节点名称：orderinfo  汇总信息 类型：object | 节点名称：orderinfo  汇总信息 类型：object | 节点名称：orderinfo  汇总信息 类型：object |
| 1 | HINTCONTENT | 提示 | string | 提示信息，文字形式 |
| 2 | HINTCONTENTHTML | 提示 | string | 提示信息，文字形式 |
| 节点名称：orderlist类型：array<object> | 节点名称：orderlist类型：array<object> | 节点名称：orderlist类型：array<object> | 节点名称：orderlist类型：array<object> | 节点名称：orderlist类型：array<object> |
| 条码分组及控制信息 | 条码分组及控制信息 | 条码分组及控制信息 | 条码分组及控制信息 | 条码分组及控制信息 |
| 1 | OP_CHECK | 选择 | string | 0-未选择
1-选择
默认为1 |
| 2 | OP_CHECKENABLE | 允许选择 | string | true-允许选择或取消选择
false-不允许选择或取消选择，按默认的操作 |
| 3 | BARGROUP | 条码分组 | string | 用于区分是否允许绑定在一起，是否只采样一个单位
格式：BAREXAMCODE_流水号 |
| 4 | BARCODE | 条形码 | string | 初始条码为空，绑定成功后更改此属性 |
| 5 | BAREXAMCODE | 分组代码 | string | 条码分组代码 |
| 6 | BAREXAMNAME | 分组名称 | string | 条码分组名称 |
| 7 | IMAGEURL | 试管地址 | string | 用于加载试管图片，相对地址 |
| 8 | BARLENGTH | 条码长度 | number | 条码总长度 |
| 9 | BARCOLOR | 试管颜色 | string | 十六进制颜色值 |
| 10 | BARPREPART | 条码前缀 | string | 条码前缀，最多两位 |
| 11 | BARSECONDCOLOR | 第二颜色 | string | 十六进制颜色值 |
| 12 | BARSECPREPART | 第二前缀 | string | 条码第二前缀，最多两位 |
| 13 | BARPRIORITY | 优先次序 | number | 根据此条件来排序显示 |
| 14 | SAMPLECODE | 样本类型 | string | 样本类型代码 |
|  | 试管区域显示内容字段对应 | 试管区域显示内容字段对应 | 试管区域显示内容字段对应 | 试管区域显示内容字段对应 |
| 15 | SAMPLENAME | 样本名称 | string | 样本类型名称 |
| 16 | BARDESCRIBE | 分组描述 | string | 用于配置试管颜色 如：紫色 |
| 17 | BARCUBAGE | 采血量 | string | 采血量，如：50ml |
| 18 | BARNOTICE | 注意事项 | string | 采集注意事项，如：混匀5次 |
| 19 | EXAMGROUPNAME | 送检小组 | string | 送检小组，如：检验科 |
| 20 | EXAMGROUPCODE | 送检小组代码 | string | 送检小组代码，如：0002 |
| 21 | BARGENRULE | 条码规则 | string | 对应SLAVE中的条码生成规则 |
| 需要采血的病人列表 | 需要采血的病人列表 | 需要采血的病人列表 | 需要采血的病人列表 | 需要采血的病人列表 |
| 1 | APPLYNO | 报告单号 | string | HIS下载未确认的医嘱默认为-2 |
| 2 | SERIALNO | 序号 | string | HIS下载未确认的默认为-1 |
| 3 | PATIENTID | 病人唯一号 | string | 病人唯一号 |
| 4 | CURENO | 就诊号 | string | 就诊号 |
| 5 | LOGNO | 医嘱序号 | string | 医嘱序号，对应LIS_ACCEPTITEMS.HISXXH |
| 6 | HISAPPLYNO | 申请单号 | string | 申请单号，不是开申请单则为0 |
| 7 | GROUPNO | 处方序号 | string | 处方序号 |
| 8 | HISORDERCODE | 项目代码 | string | HIS项目代码 |
| 9 | HISORDERNAME | 项目名称 | string | HIS项目名称 |
| 10 | ITEMTYPE | 项目分类 | string | 0-临床 1-收费 |
| 11 | LISORDERCODE | LIS代码 | string | 对应LIS_HISITEM.LISORDERCODE |
| 12 | ITEMQTY | 项目数量 | string | 项目数量，如：1.5 |
| 13 | PRICE | 单价 | string | 单价，如：45.00 |
| 14 | ITEMUNIT | 单位 | string | 如：次 |
| 15 | APPLYTIME | 申请时间 | string | 如：2017-06-26 09:34:23 |
| 16 | APPLYDAYS | 申请天数 | number | 申请时间到当前时间的天数，如：6 |
| 17 | BEYONDTIMELIMIT | 超出天数 | string | 申请时间到当前时间超出设定范围
B-超出范围（申请时间后面需显示APPLYDAYS并红色标识）
其他只显示正常内容 |
| 17 | APPLYDEPTCODE | 申请科室 | string | 申请科室代码(LIS内码），如：2353 |
| 18 | APPLYDEPTNAME | 科室名称 | string | 申请科室名称(LIS内码），如：内科 |
| 19 | APPLYDOCCODE | 申请医生 | string | 申请医生代码(LIS内码)，如：2839 |
| 20 | APPLYDOCNAME | 医生姓名 | string | 申请医生姓名(LIS内码)，如：万一阁 |
| 21 | EXECDEPTCODE | 执行科室 | string | 执行科室代码(LIS内码)，如：4300 |
| 22 | EXECDEPTNAME | 科室名称 | string | 执行科室名称(LIS内码)，如：检验科 |
| 25 | ADDTYPE | 医嘱类型 | string | 0-HIS下载
1-LIS上传 |
| 26 | ADDTYPEDESC | 类型描述 | string | 医嘱类型中文描述，如：下载或上传 |
| 27 | STATUS | 状态 | string | 0-未确认 1-已确认 2-已拒绝 |
| 28 | STATUSDESC | 状态 | string | 状态中文描述，如：未确认、已确认、已拒绝 |
| 29 | CHARGEFLAG | 收费 | string | 0-未收费 1-已收费 2-退费 |
| 30 | CHARGEFLAGDESC | 收费描述 | string | 收费中文描述，如：未收费、已收费、已退费 |
| 31 | CLINICDESC | 临床诊断 | string | 临床诊断 |
| 32 | SAMPLECODE | 样本类型 | string | 样本类型代码 |
| 33 | SAMPLENAME | 样本名称 | string | 样本类型名称 |
| 34 | GHXH | 挂号序号 | string | 挂号序号，针对门诊 |
| 41 | ENTRUST | 嘱托 | string | 嘱托内容 |
| 42 | MJZBZ | 加急标志 | string | 0-常规 1-加急 |
| 43 | EXCUTETIME | 预约时间 | string | 预约检测的日期 |



提示信息规范：





JSON返回示例：

"orderinfo":{

"HINTCONTENT":"选择3个项目，金额 235.00 元",

"HINTCONTENTHTML":"选择3个项目，金额 235.00 元"

},

"orderlist":[

{

    "OP_CHECK":"1",

"OP_CHECKENABLE":"true",

...................

    "ENTRUST":"嘱托",

    "MJZBZ":"0",

    "EXCUTETIME":"2017-07-21"

},

{

    "OP_CHECK":"1",

"OP_CHECKENABLE":"true",

...................

    "ENTRUST":"嘱托",

    "MJZBZ":"0",

    "EXCUTETIME":"2017-07-21"

}

]

代码实现：

调用HIS接口：getpatientorder

需要进行内部转换的进行数据转换,转换成内部码

相关表结构：SLAVE\SYS_DEPT\SYS_USERS

#### ※B03 医嘱分组

接口说明：更改医嘱信息后需要重新对医嘱数据进行分组

请求URL：../zysampling/request/grouppatorders

代码文件：winning.lis.zysampling.service.ZySamplingServerice

示例URL：http://192.168.14.253:8080/lis/zysampling/request/grouppatorders?orderlist=[]

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| orderlist array<object>  见 201.B02的出参orderlist | orderlist array<object>  见 201.B02的出参orderlist | orderlist array<object>  见 201.B02的出参orderlist | orderlist array<object>  见 201.B02的出参orderlist | orderlist array<object>  见 201.B02的出参orderlist |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：orderinfo  汇总信息 类型：object | 节点名称：orderinfo  汇总信息 类型：object | 节点名称：orderinfo  汇总信息 类型：object | 节点名称：orderinfo  汇总信息 类型：object | 节点名称：orderinfo  汇总信息 类型：object |
| 1 | HINTCONTENT | 提示 | string | 提示信息，文字形式 |
| 2 | HINTCONTENTHTML | 提示 | string | 提示信息，文字形式(HTML格式） |
| 节点名称：orderlist类型：array<object> | 节点名称：orderlist类型：array<object> | 节点名称：orderlist类型：array<object> | 节点名称：orderlist类型：array<object> | 节点名称：orderlist类型：array<object> |
| 条码分组及控制信息 | 条码分组及控制信息 | 条码分组及控制信息 | 条码分组及控制信息 | 条码分组及控制信息 |
| 1 | OP_CHECK | 选择 | string | 0-未选择
1-选择
默认为1 |
| 2 | OP_CHECKENABLE | 允许选择 | string | true-允许选择或取消选择
false-不允许选择或取消选择，按默认的操作 |
| 3 | BARGROUP | 条码分组 | string | 用于区分是否允许绑定在一起，是否只采样一个单位
格式：BAREXAMCODE_流水号 |
| 4 | BARCODE | 条形码 | string | 初始条码为空，绑定成功后更改此属性 |
| 5 | BAREXAMCODE | 分组代码 | string | 条码分组代码 |
| 6 | BAREXAMNAME | 分组名称 | string | 条码分组名称 |
| 7 | IMAGEURL | 试管地址 | string | 用于加载试管图片，相对地址 |
| 8 | BARLENGTH | 条码长度 | number | 条码总长度 |
| 9 | BARCOLOR | 试管颜色 | string | 十六进制颜色值 |
| 10 | BARPREPART | 条码前缀 | string | 条码前缀，最多两位 |
| 11 | BARSECONDCOLOR | 第二颜色 | string | 十六进制颜色值 |
| 12 | BARSECPREPART | 第二前缀 | string | 条码第二前缀，最多两位 |
| 13 | BARPRIORITY | 优先次序 | number | 根据此条件来排序显示 |
| 14 | SAMPLECODE | 样本类型 | string | 样本类型代码 |
|  | 试管区域显示内容字段对应 | 试管区域显示内容字段对应 | 试管区域显示内容字段对应 | 试管区域显示内容字段对应 |
| 15 | SAMPLENAME | 样本名称 | string | 样本类型名称 |
| 16 | BARDESCRIBE | 分组描述 | string | 用于配置试管颜色 如：紫色 |
| 17 | BARCUBAGE | 采血量 | string | 采血量，如：50ml |
| 18 | BARNOTICE | 注意事项 | string | 采集注意事项，如：混匀5次 |
| 19 | EXAMGROUPNAME | 送检小组 | string | 送检小组，如：检验科 |
| 20 | EXAMGROUPCODE | 送检小组代码 | string | 送检小组代码，如：0002 |
| 需要采血的医嘱列表 | 需要采血的医嘱列表 | 需要采血的医嘱列表 | 需要采血的医嘱列表 | 需要采血的医嘱列表 |
| 1 | APPLYNO | 报告单号 | string | HIS下载未确认的医嘱默认为-2 |
| 2 | SERIALNO | 序号 | string | HIS下载未确认的默认为-1 |
| 3 | PATIENTID | 病人唯一号 | string | 病人唯一号 |
| 4 | CURENO | 就诊号 | string | 就诊号 |
| 5 | LOGNO | 医嘱序号 | string | 医嘱序号，对应LIS_ACCEPTITEMS.HISXXH |
| 6 | HISAPPLYNO | 申请单号 | string | 申请单号，不是开申请单则为0 |
| 7 | GROUPNO | 处方序号 | string | 处方序号 |
| 8 | HISORDERCODE | 项目代码 | string | HIS项目代码 |
| 9 | HISORDERNAME | 项目名称 | string | HIS项目名称 |
| 10 | ITEMTYPE | 项目分类 | string | 0-临床 1-收费 |
| 11 | LISORDERCODE | LIS代码 | string | 对应LIS_HISITEM.LISORDERCODE |
| 12 | ITEMQTY | 项目数量 | string | 项目数量，如：1.5 |
| 13 | PRICE | 单价 | string | 单价，如：45.00 |
| 14 | ITEMUNIT | 单位 | string | 如：次 |
| 15 | APPLYTIME | 申请时间 | string | 如：2017-06-26 09:34:23 |
| 16 | APPLYDAYS | 申请天数 | number | 申请时间到当前时间的天数，如：6 |
| 17 | BEYONDTIMELIMIT | 超出天数 | string | 申请时间到当前时间超出设定范围
B-超出范围（申请时间后面需显示APPLYDAYS并红色标识）
其他只显示正常内容 |
| 17 | APPLYDEPTCODE | 申请科室 | string | 申请科室代码(LIS内码），如：2353 |
| 18 | APPLYDEPTNAME | 科室名称 | string | 申请科室名称(LIS内码），如：内科 |
| 19 | APPLYDOCCODE | 申请医生 | string | 申请医生代码(LIS内码)，如：2839 |
| 20 | APPLYDOCNAME | 医生姓名 | string | 申请医生姓名(LIS内码)，如：万一阁 |
| 21 | EXECDEPTCODE | 执行科室 | string | 执行科室代码(LIS内码)，如：4300 |
| 22 | EXECDEPTNAME | 科室名称 | string | 执行科室名称(LIS内码)，如：检验科 |
| 25 | ADDTYPE | 医嘱类型 | string | 0-HIS下载
1-LIS上传 |
| 26 | ADDTYPEDESC | 类型描述 | string | 医嘱类型中文描述，如：下载或上传 |
| 27 | STATUS | 状态 | string | 0-未确认 1-已确认 2-已拒绝 |
| 28 | STATUSDESC | 状态 | string | 状态中文描述，如：未确认、已确认、已拒绝 |
| 29 | CHARGEFLAG | 收费 | string | 0-未收费 1-已收费 2-退费 |
| 30 | CHARGEFLAGDESC | 收费描述 | string | 收费中文描述，如：未收费、已收费、已退费 |
| 31 | CLINICDESC | 临床诊断 | string | 临床诊断 |
| 32 | SAMPLECODE | 样本类型 | string | 样本类型代码 |
| 33 | SAMPLENAME | 样本名称 | string | 样本类型名称 |
| 34 | GHXH | 挂号序号 | string | 挂号序号，针对门诊 |
| 41 | ENTRUST | 嘱托 | string | 嘱托内容 |
| 42 | MJZBZ | 加急标志 | string | 0-常规 1-加急 |
| 43 | EXCUTETIME | 预约时间 | string | 预约检测的日期 |



提示信息规范：





JSON返回示例：

"orderinfo":{

"HINTCONTENT":"选择3个项目，金额 235.00 元",

"HINTCONTENTHTML":"选择3个项目，金额 235.00 元"

},

"orderlist":[

{

    "OP_CHECK":"1",

"OP_CHECKENABLE":"true",

...................

    "ENTRUST":"嘱托",

    "MJZBZ":"0",

    "EXCUTETIME":"2017-07-21"

},

{

    "OP_CHECK":"1",

"OP_CHECKENABLE":"true",

...................

    "ENTRUST":"嘱托",

    "MJZBZ":"0",

    "EXCUTETIME":"2017-07-21"

}

]

代码实现：

参数控制

医嘱属性

相关表结构：LIS_BAREXAMCLASS\MJZBZ....

#### ※B04 批量打印条码

接口说明：根据选择的患者列表信息批量打印患者条码

请求URL：../zysampling/request/multiconfirmpatorders

代码文件：winning.lis.zysampling.service.ZySamplingServerice

示例URL：http://192.168.14.253:8080/lis/zysampling/request/multiconfirmpatorders?sicklist=[]&orderby=&downloaddays=30&mjzbz=-1

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| sicklist array<object>  见 201.B01的出参sicklist | sicklist array<object>  见 201.B01的出参sicklist | sicklist array<object>  见 201.B01的出参sicklist | sicklist array<object>  见 201.B01的出参sicklist | sicklist array<object>  见 201.B01的出参sicklist |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- | --- |
| 节点名称：barlist 打印的条码信息 类型：array<object> | 节点名称：barlist 打印的条码信息 类型：array<object> | 节点名称：barlist 打印的条码信息 类型：array<object> | 节点名称：barlist 打印的条码信息 类型：array<object> | 节点名称：barlist 打印的条码信息 类型：array<object> | 节点名称：barlist 打印的条码信息 类型：array<object> |
| 0 | BRLX | 病人类型 | 病人类型 | string | 0-门诊 1-住院 3、4-体检
住院采集默认为 1 |
| 1 | PATIENTID | 病人唯一号 | 病人唯一号 | string | 病人唯一号 |
| 2 | CURENO | 就诊号 | 就诊号 | string | 就诊号 |
| 3 | PATNAME | 患者姓名 | 患者姓名 | string | 患者姓名 |
| 4 | HOSPNO | 住院号 | 住院号 | string | 住院号 |
| 5 | CARDNO | 磁卡号 | 磁卡号 | string | 磁卡号 |
| 6 | BARLIST | 条码列表 | 条码列表 | array<string> | 条码列表 |
| 节点名称：sickinfo  汇总信息 类型：object | 节点名称：sickinfo  汇总信息 类型：object | 节点名称：sickinfo  汇总信息 类型：object | 节点名称：sickinfo  汇总信息 类型：object | 节点名称：sickinfo  汇总信息 类型：object | 节点名称：sickinfo  汇总信息 类型：object |
| 1 | MJZCNT | 加急数量 | 加急数量 | number | 加急采样数量 |
| 2 | NORCNT | 常规数量 | 常规数量 | number | 常规采样数量 |
| 3 | ALLCNT | 所有采样数 | 所有采样数 | number | 所有采样数量 |
| 节点名称：sicklist类型：array<object> | 节点名称：sicklist类型：array<object> | 节点名称：sicklist类型：array<object> | 节点名称：sicklist类型：array<object> | 节点名称：sicklist类型：array<object> | 节点名称：sicklist类型：array<object> |
| 操作属性 | 操作属性 | 操作属性 | 操作属性 | 操作属性 | 操作属性 |
| 1 | OP_CHECK | OP_CHECK | 选择 | string | 0-未选择
1-选择
默认为1 |
| 需要采血的病人列表 | 需要采血的病人列表 | 需要采血的病人列表 | 需要采血的病人列表 | 需要采血的病人列表 | 需要采血的病人列表 |
| 0 | BRLX | BRLX | 病人类型 | string | 0-门诊 1-住院 3、4-体检
住院采集默认为 1 |
| 1 | PATIENTID | PATIENTID | 病人唯一号 | string | 病人唯一号
对应HIS接口：patid |
| 2 | CURENO | CURENO | 就诊号 | string | 就诊号
对应HIS接口：syxh |
| 3 | PATNAME | PATNAME | 患者姓名 | string | 患者姓名
对应HIS接口：hzxm |
| 4 | HOSPNO | HOSPNO | 住院号 | string | 住院号
对应HIS接口：blh |
| 5 | CARDNO | CARDNO | 磁卡号 | string | 磁卡号
对应HIS接口：cardno |
| 6 | SEX | SEX | 性别 | string | 性别 1-男 2-女 3-未知
对应HIS接口：sex - 需转换 |
| 7 | SEXDESC | SEXDESC | 性别描述 | string | 性别描述
对应HIS接口：sex |
| 8 | AGE | AGE | 年龄 | string | 年龄
对应HIS接口：nl或age（需判断属性是否存在并且是数字型） |
| 9 | AGEUNIT | AGEUNIT | 年龄单位 | number | 年龄单位，如：岁
对应HIS接口：ageunit（需判断属性是否存在） |
| 10 | AGE2 | AGE2 | 年龄2 | string | 年龄2
对应HIS接口：age2（需判断属性是否存在并且是数字型） |
| 11 | AGEUNIT2 | AGEUNIT2 | 年龄单位2 | number | 年龄单位，如：日
对应HIS接口：ageunit2（需判断属性是否存在） |
| 12 | AGEDESC | AGEDESC | 年龄 | string | 年龄描述，如：23岁
对应HIS接口：agedesc（需判断属性是否存在） |
| 13 | WARD | WARD | 病区代码 | string | 病区代码
对应HIS接口：bqdm（需转换为内码） |
| 14 | WARDNAME | WARDNAME | 病区名称 | string | 病区名称
对应HIS接口：bqmc（需转换为内码） |
| 15 | BEDNO | BEDNO | 床号 | string | 床号，如：+10床
对应HIS接口：cw |
| 16 | CLINICDESC | CLINICDESC | 临床诊断 | string | 临床诊断，如：感冒
对应HIS接口：clinicdesc |
| 17 | CHARGETYPECODE | CHARGETYPECODE | 费别代码 | string | 费别代码
对应HIS接口：fbdm（需转换为内码） |
| 18 | CHARGETYPENAME | CHARGETYPENAME | 费别名称 | string | 费别名称
对应HIS接口：fbdm（需转换为内码） |
| 19 | YEBZ | YEBZ | 婴儿标志 | string | 对应HIS接口：yebz |
| 20 | YEXH | YEXH | 婴儿序号 | string | 对应HIS接口：yexh |
| 21 | TIMES | TIMES | 住院次数 | string | 对应HIS接口：times（需判断属性是否存在） |
| 22 | PHONE | PHONE | 联系电话 | string | 对应HIS接口：phone |
| 23 | MJZBZ | MJZBZ | 加急标志 | string | 对应HIS接口：mjzbz（需判断属性是否存在）
MJZBZ=1 或 -1，患者姓名显示红色
1-加急 0-常规 -1-既有加急也有常规 |
| 24 | ADDRESS | ADDRESS | 地址 | string | 对应HIS接口：address（需判断属性是否存在） |
| 25 | IDNUM | IDNUM | 身份证 | string | 对应HIS接口：idnum（需判断属性是否存在） |
| 26 | BIRTHDAY | BIRTHDAY | 生日 | string | 对应HIS接口：birthday（需判断属性是否存在） |
| 27 | NATION | NATION | 民族 | string | 对应HIS接口：nation（需判断属性是否存在） |
| 28 | EMPIID | EMPIID | EMPID | string | 对应HIS接口：empiid（需判断属性是否存在） |
| 29 | ICD10 | ICD10 | ICD10 | string | 对应HIS接口：icd10（需判断属性是否存在） |
| 30 | ICD10NAME | ICD10NAME | ICD名称 | string | 对应HIS接口：icd10name（需判断属性是否存在） |





JSON返回示例：

"barlist":[

{

    "BRLX":"1",

"PATIENTID":2344321",

"CURENO":2344321",

...................

    "BARLIST":["130888840332","140000347283"]

},

{

   "BRLX":"1",

"PATIENTID":2344321",

"CURENO":2344321",

...................

    "BARLIST":["130888840332","140000347283"]

}

],

"sickinfo":[

{

    "MJZCNT":"5",

"NORCNT":6",

"ALLCNT":"11"

},

"sicklist":[

{

    "OP_CHECK":"1",

"PATIENTID":2344321",

"CURENO":2344321",

...................

    "EMPIID":"12220",

    "ICD10":"B11.022",

    "ICD10NAME":"霍乱"

},

{

    "OP_CHECK":"1",

"PATIENTID":2344321",

"CURENO":2344321",

...................

    "EMPIID":"12220",

    "ICD10":"B11.022",

    "ICD10NAME":"霍乱"

}

]

代码实现：

循环传入的患者列表/或前端进行循环传入交互（交互效果-提示当前在处理哪一笔记录）

调用201.B02 进行医嘱分组

调用202.B02进行条码打印

记录条码列表信息及患者信息

返回前端，以备打印

#### ※B05 医嘱提示 

接口说明：选择会更改选择医嘱后汇总提示信息

请求URL：../zysampling/request/summarypatorders

代码文件：winning.lis.zysampling.service.ZySamplingServerice

示例URL：http://192.168.14.253:8080/lis/zysampling/request/summarypatorders?orderlist=[]

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| orderlist array<object>  见 201.B02的出参orderlist | orderlist array<object>  见 201.B02的出参orderlist | orderlist array<object>  见 201.B02的出参orderlist | orderlist array<object>  见 201.B02的出参orderlist | orderlist array<object>  见 201.B02的出参orderlist |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：orderinfo  汇总信息 类型：object | 节点名称：orderinfo  汇总信息 类型：object | 节点名称：orderinfo  汇总信息 类型：object | 节点名称：orderinfo  汇总信息 类型：object | 节点名称：orderinfo  汇总信息 类型：object |
| 1 | HINTCONTENT | 提示 | string | 提示信息，文字形式 |
| 2 | HINTCONTENTHTML | 提示 | string | 提示信息，文字形式(HTML格式） |



JSON返回示例：

"orderinfo":{

"HINTCONTENT":"选择3个项目，金额 235.00 元",

"HINTCONTENTHTML":"选择3个项目，金额 235.00 元"

},

代码实现：

汇总选择项目数量及总金额

参照接口201.B02或201.B03

## 采样时间更新

### V 模块接口视图

| NO. | 接口编号 | 接口名称 | 对接人 | 说明 |
| --- | --- | --- | --- | --- |
| 1 | 204.A01 | 请求初始化数据
../sampledateupdate/request/getinitdata |  | 获取标本采样时间更新的初始化数据，科室/用户信息/病区/采集人等下拉列表数据 |
| 2 | 204.B01 | 标本查询
sampledateupdate/request/getsamplelist |  | 获取根据相关条件查询的样本信息列表 |
| 3 | 204.B02 | 标本查询（扫码）
sampledateupdate/request/getsampleinfo |  | 获取条形码获取样本信息 |
| 4 | 204.B03 | 更新采样时间
sampledateupdate/operate/updatesampledate |  | 根据需要更新的采样时间后台更新采样时间和采样人等信息 |
| 5 | 204.B04 | 护士派发
sampledateupdate/operate/distributebynurse |  | 将当前选中样本或者操作列表中样本进行护士派发操作 |
| 6 | 204.B05 | 获取扫码枪数据
sampledateupdate/operate/getmovescannerinfo |  | 获取扫码枪数据接口 |
| 7 | 202.B03 | 打印
../barcodebinding/operate/getsamplelist | 赵贤云 | 条码补打列表，打印条形码获取条形码列表数据 |
|  |  |  |  |  |
|  |  |  |  |  |



### A 初始化数据

接口说明：获取采样时间更新的初始化数据，执行科室/科室/用户信息/病区/绑定人人等下拉列表数据等

URL：../ updatesampletime/request/getinitdata

实例URL:http://192.168.11.211:8098/lis/updatesampletime/request/getinitdata

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：初始化参数数据及权限集合controlsparams类型：object | 节点名称：初始化参数数据及权限集合controlsparams类型：object | 节点名称：初始化参数数据及权限集合controlsparams类型：object | 节点名称：初始化参数数据及权限集合controlsparams类型：object | 节点名称：初始化参数数据及权限集合controlsparams类型：object |
| 1 | OP_SYSTEMSETTINGS | 系统参数设定 | boolean | true-有系统参数设定的权限
false-没系统参数设定的权限 |
| 2 | OP_NURSEDISTRIBUTE | 护士派发 | boolean | true-有护士派发的权限
false-没有护士派发的权限 |
| 3 | OP_UPDATEDRAWDATE | 更新采样时间操作 | boolean | true-有更新采样时间操作的权限
false-没有更新采样时间操作的权限 |
| 4 | OP_PRINT | 打印操作 | boolean | true-有打印操作的权限
false-没有打印操作的权限 |
| 5 | PM_SAMPLEOPERATORTYPE | 扫码动作类型参数 | string | 1-直接更新采样时间 0-查询并选择标本记录 |
| 6 | PM_SAMPLEDATETYPE | 采样时间参数 | string | 1-指定时间 0-当前时间 |
| 7 | PM_SCANNERSTART | 启动扫码枪参数 | string | 1-启动 0-未启动 |
| 8 | PM_PRINTTYPE | 打印类型参数 | string | 1-打印选择的标本信息 0-打印标本项目 |
| 9 | PM_DISTRIBUTETYPE | 护士派发参数 | string | 1-只派发选择的标本 0-派发所有 |
| 10 | PM_PRINTSAMPLEDETAIL | 打印标本明细参数 | string | 1-派发后打印标本明细 0-不打印标本明细 |
| 执行科室字典 节点名称：execdeptlist类型：array <object> | 执行科室字典 节点名称：execdeptlist类型：array <object> | 执行科室字典 节点名称：execdeptlist类型：array <object> | 执行科室字典 节点名称：execdeptlist类型：array <object> | 执行科室字典 节点名称：execdeptlist类型：array <object> |
| 1 | CODE | 字典代码 | string | 字典代码，如：2110 |
| 2 | NAME | 字典名称 | string | 字典名称，如：检验科 |
| 3 | DICID | 字典ID | string | 字典ID，如：2110 |
| 4 | DICTYPE | 字典类型 | string | 字典类型，如：执行科室 |
| 5 | EXTERNCODE | 外部码 | string | HIS外部代码 |
| 6 | MEMCODE1 | 输入码一 | string | 输入码一 |
| 7 | MEMCODE2 | 输入码二 | string | 输入码二 |
| 8 | SUBSYSCODE | 系统代码 | string | 系统代码，LIS默认为LIMS |
| 9 | ORDERNO | 序号 | number | 排序序号，根据此字段来显示展示顺序 |
| 申请科室字典 节点名称：applydeptlist类型：array <object> | 申请科室字典 节点名称：applydeptlist类型：array <object> | 申请科室字典 节点名称：applydeptlist类型：array <object> | 申请科室字典 节点名称：applydeptlist类型：array <object> | 申请科室字典 节点名称：applydeptlist类型：array <object> |
| 1 | CODE | 字典代码 | string | 字典代码，如：2110 |
| 2 | NAME | 字典名称 | string | 字典名称，如：检验科 |
| 3 | DICID | 字典ID | string | 字典ID，如：2110 |
| 4 | DICTYPE | 字典类型 | string | 字典类型，如：执行科室 |
| 5 | EXTERNCODE | 外部码 | string | HIS外部代码 |
| 6 | MEMCODE1 | 输入码一 | string | 输入码一 |
| 7 | MEMCODE2 | 输入码二 | string | 输入码二 |
| 8 | SUBSYSCODE | 系统代码 | string | 系统代码，LIS默认为LIMS |
| 9 | ORDERNO | 序号 | number | 排序序号，根据此字段来显示展示顺序 |
| 病区节点名称：wardlist类型：array <object> | 病区节点名称：wardlist类型：array <object> | 病区节点名称：wardlist类型：array <object> | 病区节点名称：wardlist类型：array <object> | 病区节点名称：wardlist类型：array <object> |
| 1 | CODE | 字典代码 | string | 字典代码，如：1411 |
| 2 | NAME | 字典名称 | string | 字典名称，如：儿一科病区 |
| 3 | DICID | 字典ID | string | 字典ID，如：1411 |
| 4 | DICTYPE | 字典类型 | string | 字典类型，如：儿一科病区 |
| 5 | EXTERNCODE | 外部码 | string | HIS外部代码 |
| 6 | MEMCODE1 | 输入码一 | string | 输入码一 |
| 7 | MEMCODE2 | 输入码二 | string | 输入码二 |
| 8 | SUBSYSCODE | 系统代码 | string | 系统代码，LIS默认为LIMS |
| 9 | ORDERNO | 序号 | number | 排序序号，根据此字段来显示展示顺序 |
| 标本状态节点名称：samplestatuslist类型：array <object> | 标本状态节点名称：samplestatuslist类型：array <object> | 标本状态节点名称：samplestatuslist类型：array <object> | 标本状态节点名称：samplestatuslist类型：array <object> | 标本状态节点名称：samplestatuslist类型：array <object> |
| 1 | SAMPLESTATUSCODE | 标本状态code | string | 标本状态代码，如：0 |
| 2 | SAMPLESTATUSNAME | 标本状态名称 | string | 标本状态名称，如：已签收 |
| 绑定人节点名称：drawuserlist类型：array <object> | 绑定人节点名称：drawuserlist类型：array <object> | 绑定人节点名称：drawuserlist类型：array <object> | 绑定人节点名称：drawuserlist类型：array <object> | 绑定人节点名称：drawuserlist类型：array <object> |
| 1 | USERCODE | 字典代码 | string | 字典代码，如：7104 |
| 2 | USERNAME | 字典名称 | string | 字典名称，如：赵珊 |
| 3 | DICID | 字典ID | string | 字典ID，如：7104 |
| 4 | DICTYPE | 字典类型 | string | 字典类型，如：赵珊 |
| 5 | EXTERNCODE | 外部码 | string | HIS外部代码 |
| 6 | MEMCODE1 | 输入码一 | string | 输入码一 |
| 7 | MEMCODE2 | 输入码二 | string | 输入码二 |
| 8 | SUBSYSCODE | 系统代码 | string | 系统代码，LIS默认为LIMS |
| 9 | ORDERNO | 序号 | number | 排序序号，根据此字段来显示展示顺序 |
| 病人类型字典 节点名称：wardorreglist类型：array <object> | 病人类型字典 节点名称：wardorreglist类型：array <object> | 病人类型字典 节点名称：wardorreglist类型：array <object> | 病人类型字典 节点名称：wardorreglist类型：array <object> | 病人类型字典 节点名称：wardorreglist类型：array <object> |
| 1 | CODE | 字典代码 | string | 字典代码，如：2110 |
| 2 | NAME | 字典名称 | string | 字典名称，如：检验科 |
| 3 | DICID | 字典ID | string | 字典ID，如：2110 |
| 4 | DICTYPE | 字典类型 | string | 字典类型，如：执行科室 |
| 5 | EXTERNCODE | 外部码 | string | HIS外部代码 |
| 6 | MEMCODE1 | 输入码一 | string | 输入码一 |
| 7 | MEMCODE2 | 输入码二 | string | 输入码二 |
| 8 | SUBSYSCODE | 系统代码 | string | 系统代码，LIS默认为LIMS |
| 9 | ORDERNO | 序号 | number | 排序序号，根据此字段来显示展示顺序 |





说明：获取可签收执行科室信息，用于后面的扫码签收

JSON返回示例：

"controlsparams":{

" OP_ TXMOPERATETYPE ":TRUE

" OP_ MOVESCANNER”:"true",

" OP_ PRINTSETTINGS ":"false",

…………………………

" OP_ PRINTBUTTON ":"false"

},

"execdeptlist":[

{

    "CODE":"2301",

    "NAME":"检验科",

    "DICID":"2301",

    "DICTYPE":"执行科室",

    "EXTERNCODE":"2301",

    "MEMCODE1":"2301",

    "MEMCODE2":"JYK",

    "SUBSYSCODE":"LIMS",

    "ORDERNO":"1"

},

{

    "CODE":"2302",

    "NAME":"内分泌实验室",

    "DICID":"2302",

    "DICTYPE":"执行科室",

    "EXTERNCODE":"2302",

    "MEMCODE1":"2302",

    "MEMCODE2":"NFMSYS",

    "SUBSYSCODE":"LIMS",

    "ORDERNO":"1"

}

"wardlist":[

{

    "CODE":"2301",

    "NAME":"检验科",

    "DICID":"2301",

    "DICTYPE":"执行科室",

    "EXTERNCODE":"2301",

    "MEMCODE1":"2301",

    "MEMCODE2":"JYK",

    "SUBSYSCODE":"LIMS",

    "ORDERNO":"1"

},

{

    "CODE":"2302",

    "NAME":"内分泌实验室",

    "DICID":"2302",

    "DICTYPE":"执行科室",

    "EXTERNCODE":"2302",

    "MEMCODE1":"2302",

    "MEMCODE2":"NFMSYS",

    "SUBSYSCODE":"LIMS",

    "ORDERNO":"1"

}

…….

]



代码实现：

表结构：SYS_DEPT\SETTINGS\SETTINGSDIC

代码实现：

获取执行科室列表，调用接口：900.A01，字典分类为：执行科室

### B 业务类

#### B01 获取标本列表（查询按钮）

接口说明：获取根据相关条件查询的样本信息列表；

URL：../ sampledateupdate /request/getsamplelist

实例URL:http://. sampledateupdate /request/ getsamplelist

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | cxrdm | 绑定人代码 | string | 绑定人代码 如 7104 |
| 2 | begindate | 绑定开始时间 | string | 绑定开始时间 ，如：2017-06-23 00:00:00 |
| 3 | enddate | 绑定结束时间 | string | 绑定结束时间， 如：2017-06-23 23:59:59 |
| 4 | C | 病人类型 | string | 病人类型，：如：1-住院 0 门诊 |
| 5 | samplestatuscode | 标本状态 | string | 标本状态代码，如：5-绑定 0-签收 1-入库 2-撤销签收 3-拒绝签收 |
| 6 | sampleconfirm | 采样确认 | string | 采样确认 1-已确认 0 -未确认 |
| 7 | ward | 病区代码 | string | 病区代码 如-2001 |
| 8 | applydeptcode | 申请科室代码 | string | 科室代码 如 2110 |
| 9 | execdeptcode | 执行科室代码 | string | 执行科室代码 如 2110 |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 含义 | 含义 | 类型 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- | --- | --- | --- |
| 节点名称：countinfo类型： object | 节点名称：countinfo类型： object | 节点名称：countinfo类型： object | 节点名称：countinfo类型： object | 节点名称：countinfo类型： object | 节点名称：countinfo类型： object | 节点名称：countinfo类型： object | 节点名称：countinfo类型： object |
| 1 | SAMPLESUM | 标本总数 | 标本总数 | 标本总数 | string | string | 如：194份 |
| 2 | PATIENTSUM | 患者数量 | 患者数量 | 患者数量 | string | string | 如：92个 |
| 节点名称：samplelist类型：array<object> | 节点名称：samplelist类型：array<object> | 节点名称：samplelist类型：array<object> | 节点名称：samplelist类型：array<object> | 节点名称：samplelist类型：array<object> | 节点名称：samplelist类型：array<object> | 节点名称：samplelist类型：array<object> | 节点名称：samplelist类型：array<object> |
| 1 | TXM | TXM | 条形码 | string | string | 初始条码为空，绑定成功后更改此属性 | 初始条码为空，绑定成功后更改此属性 |
| 2 | OP_CHECK | OP_CHECK | 选择状态 | boolean | boolean | true -选中 false –未选中 | true -选中 false –未选中 |
| 3 | BRLX | BRLX | 病人类型 | string | string | 0-门诊 1-住院 3、4-体检 | 0-门诊 1-住院 3、4-体检 |
| 4 | DRAWDATE | DRAWDATE | 采样时间更新状态 | string | string | 采样状态 如：已更新 未更新 | 采样状态 如：已更新 未更新 |
| 5 | PATIENTID | PATIENTID | 病人唯一号 | string | string | 病人唯一号 | 病人唯一号 |
| 6 | CURENO | CURENO | 就诊号 | string | string | 就诊号 | 就诊号 |
| 7 | PATNAME | PATNAME | 患者姓名 | string | string | 患者姓名 | 患者姓名 |
| 8 | HOSPNO | HOSPNO | 住院号 | string | string | 住院号 | 住院号 |
| 9 | CARDNO | CARDNO | 磁卡号 | string | string | 磁卡号 | 磁卡号 |
| 10 | SEX | SEX | 性别 | string | string | 性别 1-男 2-女 3-未知 | 性别 1-男 2-女 3-未知 |
| 11 | SEXDESC | SEXDESC | 性别描述 | string | string | 性别描述 | 性别描述 |
| 12 | AGE | AGE | 年龄 | string | string | 年龄 | 年龄 |
| 13 | AGEUNIT | AGEUNIT | 年龄单位 | number | number | 年龄单位，如：岁 | 年龄单位，如：岁 |
| 14 | AGEDESC | AGEDESC | 年龄 | string | string | 年龄描述 | 年龄描述 |
| 15 | WARD | WARD | 病区代码 | string | string | 病区代码 | 病区代码 |
| 16 | WARDNAME | WARDNAME | 病区名称 | string | string | 病区名称 | 病区名称 |
| 17 | BEDNO | BEDNO | 床号 | string | string | 床号，如：+10床 | 床号，如：+10床 |
| 18 | CLINICDESC | CLINICDESC | 临床诊断 | string | string | 临床诊断，如：感冒 | 临床诊断，如：感冒 |
| 21 | BAREXAMCODE | BAREXAMCODE | 分组代码 | string | string | 条码分组代码 | 条码分组代码 |
| 21 | BAREXAMNAME | BAREXAMNAME | 分组名称 | string | string | 条码分组名称 | 条码分组名称 |
| 24 | SAMPLECODE | SAMPLECODE | 样本类型 | string | string | 样本类型代码 | 样本类型代码 |
| 25 | SAMPLENAME | SAMPLENAME | 样本名称 | string | string | 样本类型名称 | 样本类型名称 |
| 26 | EXAMGROUPNAME | EXAMGROUPNAME | 送检小组 | string | string | 送检小组，如：检验科 | 送检小组，如：检验科 |
| 27 | EXAMGROUPCODE | EXAMGROUPCODE | 送检小组代码 | string | string | 送检小组代码，如：0002 | 送检小组代码，如：0002 |
| 28 | HISORDERCODE | HISORDERCODE | 项目代码 | string | string | HIS项目代码+连接 | HIS项目代码+连接 |
| 29 | HISORDERNAME | HISORDERNAME | 项目名称 | string | string | HIS项目名称+连接 | HIS项目名称+连接 |
| 32 | EXECDEPTCODE | EXECDEPTCODE | 执行科室 | string | string | 执行科室代码(LIS内码)，如：4300 | 执行科室代码(LIS内码)，如：4300 |
| 33 | EXECDEPTNAME | EXECDEPTNAME | 科室名称 | string | string | 执行科室名称(LIS内码)，如：检验科 | 执行科室名称(LIS内码)，如：检验科 |
| 36 | DRAWDATE | DRAWDATE | 采样时间 | string | string | 采样时间 ，如：2017-06-23 00:00:00 | 采样时间 ，如：2017-06-23 00:00:00 |





 JSON返回示例：

"countinfo":

{

    " SAMPLESUM ":"158",

    " PATIENTSUM ":"85",

},

"sampleviewlist":[

{

    "TXM":"130000834945",

..................

},

{

    "TXM":"130000834946",

..................

]

}

]

相关表结构：LIS_ORDERMASTER\LIS_ACCEPTITEMS\SLAVE\

代码实现：

根据条件从标本信息表中获取

项目



#### B02 获取标本信息（扫码）

接口说明：根据条码获取样本信息；

URL：../ sampledateupdate /request/getsampleinfo

实例URL:http://. sampledateupdate /request/ getsampleinfo

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | txm | 扫描的条形码 | string | 传入的条形码 |
| 2 | sampleoperatortype | 扫码动作类型参数 | string | 1-直接更新采样时间 0-查询并选择标本记录 |
| 3 | usercode | 操作人代码 | string | 操作人代码 如 7104可为空（为空时用当前用户信息） |
| 4 | username | 操作人姓名 | string | 操作人代码 如 张三 可为空 |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 含义 | 含义 | 类型 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- | --- | --- | --- |
| 节点名称：sampleinfo类型： object | 节点名称：sampleinfo类型： object | 节点名称：sampleinfo类型： object | 节点名称：sampleinfo类型： object | 节点名称：sampleinfo类型： object | 节点名称：sampleinfo类型： object | 节点名称：sampleinfo类型： object | 节点名称：sampleinfo类型： object |
| 1 | TXM | TXM | 条形码 | string | string | 初始条码为空，绑定成功后更改此属性 | 初始条码为空，绑定成功后更改此属性 |
| 2 | OP_CHECK | OP_CHECK | 选择状态 | boolean | boolean | true -选中 false –未选中 | true -选中 false –未选中 |
| 3 | BRLX | BRLX | 病人类型 | string | string | 0-门诊 1-住院 3、4-体检 | 0-门诊 1-住院 3、4-体检 |
| 4 | DRAWDATE | DRAWDATE | 采样时间更新状态 | string | string | 采样状态 如：已更新 未更新 | 采样状态 如：已更新 未更新 |
| 5 | PATIENTID | PATIENTID | 病人唯一号 | string | string | 病人唯一号 | 病人唯一号 |
| 6 | CURENO | CURENO | 就诊号 | string | string | 就诊号 | 就诊号 |
| 7 | PATNAME | PATNAME | 患者姓名 | string | string | 患者姓名 | 患者姓名 |
| 8 | HOSPNO | HOSPNO | 住院号 | string | string | 住院号 | 住院号 |
| 9 | CARDNO | CARDNO | 磁卡号 | string | string | 磁卡号 | 磁卡号 |
| 10 | SEX | SEX | 性别 | string | string | 性别 1-男 2-女 3-未知 | 性别 1-男 2-女 3-未知 |
| 11 | SEXDESC | SEXDESC | 性别描述 | string | string | 性别描述 | 性别描述 |
| 12 | AGE | AGE | 年龄 | string | string | 年龄 | 年龄 |
| 13 | AGEUNIT | AGEUNIT | 年龄单位 | number | number | 年龄单位，如：岁 | 年龄单位，如：岁 |
| 14 | AGEDESC | AGEDESC | 年龄 | string | string | 年龄描述 | 年龄描述 |
| 15 | WARD | WARD | 病区代码 | string | string | 病区代码 | 病区代码 |
| 16 | WARDNAME | WARDNAME | 病区名称 | string | string | 病区名称 | 病区名称 |
| 17 | BEDNO | BEDNO | 床号 | string | string | 床号，如：+10床 | 床号，如：+10床 |
| 18 | CLINICDESC | CLINICDESC | 临床诊断 | string | string | 临床诊断，如：感冒 | 临床诊断，如：感冒 |
| 21 | BAREXAMCODE | BAREXAMCODE | 分组代码 | string | string | 条码分组代码 | 条码分组代码 |
| 21 | BAREXAMNAME | BAREXAMNAME | 分组名称 | string | string | 条码分组名称 | 条码分组名称 |
| 24 | SAMPLECODE | SAMPLECODE | 样本类型 | string | string | 样本类型代码 | 样本类型代码 |
| 25 | SAMPLENAME | SAMPLENAME | 样本名称 | string | string | 样本类型名称 | 样本类型名称 |
| 26 | EXAMGROUPNAME | EXAMGROUPNAME | 送检小组 | string | string | 送检小组，如：检验科 | 送检小组，如：检验科 |
| 27 | EXAMGROUPCODE | EXAMGROUPCODE | 送检小组代码 | string | string | 送检小组代码，如：0002 | 送检小组代码，如：0002 |
| 28 | HISORDERCODE | HISORDERCODE | 项目代码 | string | string | HIS项目代码+连接 | HIS项目代码+连接 |
| 29 | HISORDERNAME | HISORDERNAME | 项目名称 | string | string | HIS项目名称+连接 | HIS项目名称+连接 |
| 32 | EXECDEPTCODE | EXECDEPTCODE | 执行科室 | string | string | 执行科室代码(LIS内码)，如：4300 | 执行科室代码(LIS内码)，如：4300 |
| 33 | EXECDEPTNAME | EXECDEPTNAME | 科室名称 | string | string | 执行科室名称(LIS内码)，如：检验科 | 执行科室名称(LIS内码)，如：检验科 |
| 36 | DRAWDATE | DRAWDATE | 采样时间 | string | string | 采样时间 ，如：2017-06-23 00:00:00 | 采样时间 ，如：2017-06-23 00:00:00 |





 JSON返回示例：

"sampleinfo":[

{

    "TXM":"130000834945",

..................

},

{

    "TXM":"130000834946",

..................

]

}

]

相关表结构：LIS_ORDERMASTER\LIS_ACCEPTITEMS\SLAVE\

代码实现：

根据扫码获取到标本信息

#### B03 更新采样时间

接口说明：当点击更新采样时间按钮或者扫码时（需要选择扫码动作类型选框）调用，获取到当前样本信息，完成采样更新操作

URL：../ sampledateupdate / operate / updatesampledate

实例URL:http://. sampledateupdate / operate / updatesampledate

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 含义 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- | --- | --- |
| 204 B01的出参 samplelist类型：array<object> | 204 B01的出参 samplelist类型：array<object> | 204 B01的出参 samplelist类型：array<object> | 204 B01的出参 samplelist类型：array<object> | 204 B01的出参 samplelist类型：array<object> | 204 B01的出参 samplelist类型：array<object> | 204 B01的出参 samplelist类型：array<object> |
| 1 | sampledate | sampledate | 采样时间 | string | string | 采样时间 为空时后端用当前时间处理，如：2017-06-23 00:00:00 |
| 2 | samplelist | samplelist | 样本列表 | array<object> | array<object> | 204 B01的出参 |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 含义 | 含义 | 类型 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- | --- | --- | --- |
| 节点名称：samplelist类型：array<object> | 节点名称：samplelist类型：array<object> | 节点名称：samplelist类型：array<object> | 节点名称：samplelist类型：array<object> | 节点名称：samplelist类型：array<object> | 节点名称：samplelist类型：array<object> | 节点名称：samplelist类型：array<object> | 节点名称：samplelist类型：array<object> |
| 1 | TXM | TXM | 条形码 | string | string | 初始条码为空，绑定成功后更改此属性 | 初始条码为空，绑定成功后更改此属性 |
| 2 | OP_CHECK | OP_CHECK | 选择状态 | boolean | boolean | true -选中 false –未选中 | true -选中 false –未选中 |
| 3 | BRLX | BRLX | 病人类型 | string | string | 0-门诊 1-住院 3、4-体检 | 0-门诊 1-住院 3、4-体检 |
| 4 | DRAWDATE | DRAWDATE | 采样时间更新状态 | string | string | 采样状态 如：已更新 未更新 | 采样状态 如：已更新 未更新 |
| 5 | PATIENTID | PATIENTID | 病人唯一号 | string | string | 病人唯一号 | 病人唯一号 |
| 6 | CURENO | CURENO | 就诊号 | string | string | 就诊号 | 就诊号 |
| 7 | PATNAME | PATNAME | 患者姓名 | string | string | 患者姓名 | 患者姓名 |
| 8 | HOSPNO | HOSPNO | 住院号 | string | string | 住院号 | 住院号 |
| 9 | CARDNO | CARDNO | 磁卡号 | string | string | 磁卡号 | 磁卡号 |
| 10 | SEX | SEX | 性别 | string | string | 性别 1-男 2-女 3-未知 | 性别 1-男 2-女 3-未知 |
| 11 | SEXDESC | SEXDESC | 性别描述 | string | string | 性别描述 | 性别描述 |
| 12 | AGE | AGE | 年龄 | string | string | 年龄 | 年龄 |
| 13 | AGEUNIT | AGEUNIT | 年龄单位 | number | number | 年龄单位，如：岁 | 年龄单位，如：岁 |
| 14 | AGEDESC | AGEDESC | 年龄 | string | string | 年龄描述 | 年龄描述 |
| 15 | WARD | WARD | 病区代码 | string | string | 病区代码 | 病区代码 |
| 16 | WARDNAME | WARDNAME | 病区名称 | string | string | 病区名称 | 病区名称 |
| 17 | BEDNO | BEDNO | 床号 | string | string | 床号，如：+10床 | 床号，如：+10床 |
| 18 | CLINICDESC | CLINICDESC | 临床诊断 | string | string | 临床诊断，如：感冒 | 临床诊断，如：感冒 |
| 21 | BAREXAMCODE | BAREXAMCODE | 分组代码 | string | string | 条码分组代码 | 条码分组代码 |
| 21 | BAREXAMNAME | BAREXAMNAME | 分组名称 | string | string | 条码分组名称 | 条码分组名称 |
| 24 | SAMPLECODE | SAMPLECODE | 样本类型 | string | string | 样本类型代码 | 样本类型代码 |
| 25 | SAMPLENAME | SAMPLENAME | 样本名称 | string | string | 样本类型名称 | 样本类型名称 |
| 26 | EXAMGROUPNAME | EXAMGROUPNAME | 送检小组 | string | string | 送检小组，如：检验科 | 送检小组，如：检验科 |
| 27 | EXAMGROUPCODE | EXAMGROUPCODE | 送检小组代码 | string | string | 送检小组代码，如：0002 | 送检小组代码，如：0002 |
| 28 | HISORDERCODE | HISORDERCODE | 项目代码 | string | string | HIS项目代码+连接 | HIS项目代码+连接 |
| 29 | HISORDERNAME | HISORDERNAME | 项目名称 | string | string | HIS项目名称+连接 | HIS项目名称+连接 |
| 32 | EXECDEPTCODE | EXECDEPTCODE | 执行科室 | string | string | 执行科室代码(LIS内码)，如：4300 | 执行科室代码(LIS内码)，如：4300 |
| 33 | EXECDEPTNAME | EXECDEPTNAME | 科室名称 | string | string | 执行科室名称(LIS内码)，如：检验科 | 执行科室名称(LIS内码)，如：检验科 |
| 36 | DRAWDATE | DRAWDATE | 采样时间 | string | string | 采样时间 ，如：2017-06-23 00:00:00 | 采样时间 ，如：2017-06-23 00:00:00 |





 JSON返回示例：

"samplelist":[

{

    "TXM":"130000834945",

..................

},

{

    "TXM":"130000834946",

..................

]

}

]

相关表结构：LIS_ORDERMASTER\LIS_ACCEPTITEMS\SLAVE\

代码实现：

根据当前操作人信息和需要更新的时间，将LIS_ORDERMASTER相关字段更新，并保存历史记录

#### B04 护士派发

接口说明：获取根据相关条件查询的样本信息列表；

URL：../ sampledateupdate / operate / distributebynurse

实例URL:http://. sampledateupdate / operate / distributebynurse

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 含义 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- | --- | --- |
| 204 B01的出参 samplelist类型：array<object> | 204 B01的出参 samplelist类型：array<object> | 204 B01的出参 samplelist类型：array<object> | 204 B01的出参 samplelist类型：array<object> | 204 B01的出参 samplelist类型：array<object> | 204 B01的出参 samplelist类型：array<object> | 204 B01的出参 samplelist类型：array<object> |
| 1 | sampledate | sampledate | 采样时间 | string | string | 采样时间 ，如：2017-06-23 00:00:00 |
| 2 | samplelist | samplelist | 样本列表 | array<object> | array<object> | 204 B01的出参 |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 含义 | 含义 | 类型 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- | --- | --- | --- |
| 节点名称：samplelist类型：array<object> | 节点名称：samplelist类型：array<object> | 节点名称：samplelist类型：array<object> | 节点名称：samplelist类型：array<object> | 节点名称：samplelist类型：array<object> | 节点名称：samplelist类型：array<object> | 节点名称：samplelist类型：array<object> | 节点名称：samplelist类型：array<object> |
| 1 | TXM | TXM | 条形码 | string | string | 初始条码为空，绑定成功后更改此属性 | 初始条码为空，绑定成功后更改此属性 |
| 2 | BRLX | BRLX | 病人类型 | string | string | 0-门诊 1-住院 3、4-体检 | 0-门诊 1-住院 3、4-体检 |
| 3 | PATIENTID | PATIENTID | 病人唯一号 | string | string | 病人唯一号 | 病人唯一号 |
| 4 | CURENO | CURENO | 就诊号 | string | string | 就诊号 | 就诊号 |
| 5 | PATNAME | PATNAME | 患者姓名 | string | string | 患者姓名 | 患者姓名 |
| 6 | HOSPNO | HOSPNO | 住院号 | string | string | 住院号 | 住院号 |
| 7 | CARDNO | CARDNO | 磁卡号 | string | string | 磁卡号 | 磁卡号 |
| 8 | SEX | SEX | 性别 | string | string | 性别 1-男 2-女 3-未知 | 性别 1-男 2-女 3-未知 |
| 9 | SEXDESC | SEXDESC | 性别描述 | string | string | 性别描述 | 性别描述 |
| 10 | AGE | AGE | 年龄 | string | string | 年龄 | 年龄 |
| 11 | AGEUNIT | AGEUNIT | 年龄单位 | number | number | 年龄单位，如：岁 | 年龄单位，如：岁 |
| 12 | AGE2 | AGE2 | 年龄2 | string | string | 年龄2 | 年龄2 |
| 13 | AGEUNIT2 | AGEUNIT2 | 年龄单位2 | number | number | 年龄单位 | 年龄单位 |
| 14 | AGEDESC | AGEDESC | 年龄 | string | string | 年龄描述 | 年龄描述 |
| 15 | WARD | WARD | 病区代码 | string | string | 病区代码 | 病区代码 |
| 16 | WARDNAME | WARDNAME | 病区名称 | string | string | 病区名称 | 病区名称 |
| 17 | BEDNO | BEDNO | 床号 | string | string | 床号，如：+10床 | 床号，如：+10床 |
| 18 | CLINICDESC | CLINICDESC | 临床诊断 | string | string | 临床诊断，如：感冒 | 临床诊断，如：感冒 |
| 19 | CHARGETYPECODE | CHARGETYPECODE | 费别代码 | string | string | 费别代码 | 费别代码 |
| 20 | CHARGETYPENAME | CHARGETYPENAME | 费别名称 | string | string | 费别名称 | 费别名称 |
| 21 | BAREXAMCODE | BAREXAMCODE | 分组代码 | string | string | 条码分组代码 | 条码分组代码 |
| 21 | BAREXAMNAME | BAREXAMNAME | 分组名称 | string | string | 条码分组名称 | 条码分组名称 |
| 22 | BEDNO | BEDNO | 床号 | string | string | 床号 | 床号 |
| 24 | SAMPLECODE | SAMPLECODE | 样本类型 | string | string | 样本类型代码 | 样本类型代码 |
| 25 | SAMPLENAME | SAMPLENAME | 样本名称 | string | string | 样本类型名称 | 样本类型名称 |
| 26 | EXAMGROUPNAME | EXAMGROUPNAME | 送检小组 | string | string | 送检小组，如：检验科 | 送检小组，如：检验科 |
| 27 | EXAMGROUPCODE | EXAMGROUPCODE | 送检小组代码 | string | string | 送检小组代码，如：0002 | 送检小组代码，如：0002 |
| 28 | HISORDERCODE | HISORDERCODE | 项目代码 | string | string | HIS项目代码 | HIS项目代码 |
| 29 | HISORDERNAME | HISORDERNAME | 项目名称 | string | string | HIS项目名称 | HIS项目名称 |
| 30 | ITEMTYPE | ITEMTYPE | 项目分类 | string | string | 0-临床 1-收费 | 0-临床 1-收费 |
| 31 | LISORDERCODE | LISORDERCODE | LIS代码 | string | string | 对应LIS_HISITEM.LISORDERCODE | 对应LIS_HISITEM.LISORDERCODE |
| 32 | EXECDEPTCODE | EXECDEPTCODE | 执行科室 | string | string | 执行科室代码(LIS内码)，如：4300 | 执行科室代码(LIS内码)，如：4300 |
| 33 | EXECDEPTNAME | EXECDEPTNAME | 科室名称 | string | string | 执行科室名称(LIS内码)，如：检验科 | 执行科室名称(LIS内码)，如：检验科 |
| 34 | SAMPLECODE | SAMPLECODE | 样本类型 | string | string | 样本类型代码 | 样本类型代码 |
| 35 | SAMPLENAME | SAMPLENAME | 样本名称 | string | string | 样本类型名称 | 样本类型名称 |
| 36 | SAMPLEDATE | SAMPLEDATE | 采样时间 | string | string | 样本采集时间 | 样本采集时间 |





 JSON返回示例：

"samplelist":[

{

    "TXM":"130000834945",

..................

},

{

    "TXM":"130000834946",

..................

]

}

]

相关表结构：LIS_ORDERMASTER\LIS_ACCEPTITEMS\SLAVE\

代码实现：

根据当前操作护士信息，生成批号，更新相关表数据



#### B05 获取扫码枪数据（可完成采样时间更新）

接口说明：获取移动扫描枪中条码和采样时间对应数据，并完成采样时间的更新操作；

URL：../ sampledateupdate / operate / getmovescannerinfo

实例URL:http://. sampledateupdate / operate / getmovescannerinfo

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 含义 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- | --- | --- |
| scannerlist类型：array<object> | scannerlist类型：array<object> | scannerlist类型：array<object> | scannerlist类型：array<object> | scannerlist类型：array<object> | scannerlist类型：array<object> | scannerlist类型：array<object> |
| 1 | txm | txm | 条形码 | string | string | 条形码 |
| 2 | drawcode | drawcode | 采样人代码 | string | string | 采样人代码 |
| 3 | drawtime | drawtime | 采样时间 | string | string | 采样时间 |
| type类型：string   0-完成查询 1-完成采样更新 | type类型：string   0-完成查询 1-完成采样更新 | type类型：string   0-完成查询 1-完成采样更新 | type类型：string   0-完成查询 1-完成采样更新 | type类型：string   0-完成查询 1-完成采样更新 | type类型：string   0-完成查询 1-完成采样更新 | type类型：string   0-完成查询 1-完成采样更新 |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 含义 | 含义 | 类型 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- | --- | --- | --- |
| 节点名称：samplelist类型：array<object> | 节点名称：samplelist类型：array<object> | 节点名称：samplelist类型：array<object> | 节点名称：samplelist类型：array<object> | 节点名称：samplelist类型：array<object> | 节点名称：samplelist类型：array<object> | 节点名称：samplelist类型：array<object> | 节点名称：samplelist类型：array<object> |
| 1 | TXM | TXM | 条形码 | string | string | 初始条码为空，绑定成功后更改此属性 | 初始条码为空，绑定成功后更改此属性 |
| 2 | BRLX | BRLX | 病人类型 | string | string | 0-门诊 1-住院 3、4-体检 | 0-门诊 1-住院 3、4-体检 |
| 3 | PATIENTID | PATIENTID | 病人唯一号 | string | string | 病人唯一号 | 病人唯一号 |
| 4 | CURENO | CURENO | 就诊号 | string | string | 就诊号 | 就诊号 |
| 5 | PATNAME | PATNAME | 患者姓名 | string | string | 患者姓名 | 患者姓名 |
| 6 | HOSPNO | HOSPNO | 住院号 | string | string | 住院号 | 住院号 |
| 7 | CARDNO | CARDNO | 磁卡号 | string | string | 磁卡号 | 磁卡号 |
| 8 | SEX | SEX | 性别 | string | string | 性别 1-男 2-女 3-未知 | 性别 1-男 2-女 3-未知 |
| 9 | SEXDESC | SEXDESC | 性别描述 | string | string | 性别描述 | 性别描述 |
| 10 | AGE | AGE | 年龄 | string | string | 年龄 | 年龄 |
| 11 | AGEUNIT | AGEUNIT | 年龄单位 | number | number | 年龄单位，如：岁 | 年龄单位，如：岁 |
| 12 | AGE2 | AGE2 | 年龄2 | string | string | 年龄2 | 年龄2 |
| 13 | AGEUNIT2 | AGEUNIT2 | 年龄单位2 | number | number | 年龄单位 | 年龄单位 |
| 14 | AGEDESC | AGEDESC | 年龄 | string | string | 年龄描述 | 年龄描述 |
| 15 | WARD | WARD | 病区代码 | string | string | 病区代码 | 病区代码 |
| 16 | WARDNAME | WARDNAME | 病区名称 | string | string | 病区名称 | 病区名称 |
| 17 | BEDNO | BEDNO | 床号 | string | string | 床号，如：+10床 | 床号，如：+10床 |
| 18 | CLINICDESC | CLINICDESC | 临床诊断 | string | string | 临床诊断，如：感冒 | 临床诊断，如：感冒 |
| 19 | CHARGETYPECODE | CHARGETYPECODE | 费别代码 | string | string | 费别代码 | 费别代码 |
| 20 | CHARGETYPENAME | CHARGETYPENAME | 费别名称 | string | string | 费别名称 | 费别名称 |
| 21 | BAREXAMCODE | BAREXAMCODE | 分组代码 | string | string | 条码分组代码 | 条码分组代码 |
| 21 | BAREXAMNAME | BAREXAMNAME | 分组名称 | string | string | 条码分组名称 | 条码分组名称 |
| 22 | BEDNO | BEDNO | 床号 | string | string | 床号 | 床号 |
| 24 | SAMPLECODE | SAMPLECODE | 样本类型 | string | string | 样本类型代码 | 样本类型代码 |
| 25 | SAMPLENAME | SAMPLENAME | 样本名称 | string | string | 样本类型名称 | 样本类型名称 |
| 26 | EXAMGROUPNAME | EXAMGROUPNAME | 送检小组 | string | string | 送检小组，如：检验科 | 送检小组，如：检验科 |
| 27 | EXAMGROUPCODE | EXAMGROUPCODE | 送检小组代码 | string | string | 送检小组代码，如：0002 | 送检小组代码，如：0002 |
| 28 | HISORDERCODE | HISORDERCODE | 项目代码 | string | string | HIS项目代码 | HIS项目代码 |
| 29 | HISORDERNAME | HISORDERNAME | 项目名称 | string | string | HIS项目名称 | HIS项目名称 |
| 30 | ITEMTYPE | ITEMTYPE | 项目分类 | string | string | 0-临床 1-收费 | 0-临床 1-收费 |
| 31 | LISORDERCODE | LISORDERCODE | LIS代码 | string | string | 对应LIS_HISITEM.LISORDERCODE | 对应LIS_HISITEM.LISORDERCODE |
| 32 | EXECDEPTCODE | EXECDEPTCODE | 执行科室 | string | string | 执行科室代码(LIS内码)，如：4300 | 执行科室代码(LIS内码)，如：4300 |
| 33 | EXECDEPTNAME | EXECDEPTNAME | 科室名称 | string | string | 执行科室名称(LIS内码)，如：检验科 | 执行科室名称(LIS内码)，如：检验科 |
| 34 | SAMPLECODE | SAMPLECODE | 样本类型 | string | string | 样本类型代码 | 样本类型代码 |
| 35 | SAMPLENAME | SAMPLENAME | 样本名称 | string | string | 样本类型名称 | 样本类型名称 |
| 36 | SAMPLEDATE | SAMPLEDATE | 采样时间 | string | string | 样本采集时间 | 样本采集时间 |





 JSON返回示例：

"samplelist":[

{

    "TXM":"130000834945",

..................

},

{

    "TXM":"130000834946",

..................

]

}

]

相关表结构：LIS_ORDERMASTER\LIS_ACCEPTITEMS\SLAVE\

代码实现：获取移动扫描枪中条码和采样时间对应数据，并完成采样时间的更新操作；



## 条码绑定接口

#### B 业务类

#### B01 条码绑定-预印条码 

接口说明：预印条码绑定条码

请求URL：../barcodebinding/operate/barbinding

代码文件： winning.cmp.barcodebinding.service.BarCodeBindingService

示例URL：http://192.168.14.253:8080/lis/barcodebinding/operate/barbinding?barcode=130000389393&orderlist=[]&patinfo={}&systype=LIS_MZCX

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| orderlist array<object>  见 201.B02的出参orderlist | orderlist array<object>  见 201.B02的出参orderlist | orderlist array<object>  见 201.B02的出参orderlist | orderlist array<object>  见 201.B02的出参orderlist | orderlist array<object>  见 201.B02的出参orderlist |
| patinfo object 当前选择的患者信息对象，结构参见201.B01 | patinfo object 当前选择的患者信息对象，结构参见201.B01 | patinfo object 当前选择的患者信息对象，结构参见201.B01 | patinfo object 当前选择的患者信息对象，结构参见201.B01 | patinfo object 当前选择的患者信息对象，结构参见201.B01 |
| barcode-当前需要绑定的条形码 | barcode-当前需要绑定的条形码 | barcode-当前需要绑定的条形码 | barcode-当前需要绑定的条形码 | barcode-当前需要绑定的条形码 |
| systype-系统类型   门诊标本采集：LIS_MZCX  住院标本采集：LIS_ZYCX | systype-系统类型   门诊标本采集：LIS_MZCX  住院标本采集：LIS_ZYCX | systype-系统类型   门诊标本采集：LIS_MZCX  住院标本采集：LIS_ZYCX | systype-系统类型   门诊标本采集：LIS_MZCX  住院标本采集：LIS_ZYCX | systype-系统类型   门诊标本采集：LIS_MZCX  住院标本采集：LIS_ZYCX |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 | 备注说明 |
| --- | --- | --- | --- | --- | --- |
| 节点名称：orderinfo  汇总信息 类型：object | 节点名称：orderinfo  汇总信息 类型：object | 节点名称：orderinfo  汇总信息 类型：object | 节点名称：orderinfo  汇总信息 类型：object | 节点名称：orderinfo  汇总信息 类型：object | 节点名称：orderinfo  汇总信息 类型：object |
| 1 | HINTCONTENT | 提示 | string | 提示信息，文字形式 | 提示信息，文字形式 |
| 2 | HINTCONTENTHTML | 提示 | string | 提示信息，文字形式，HTML格式 | 提示信息，文字形式，HTML格式 |
| 节点名称：checkcontrol   类型：object | 节点名称：checkcontrol   类型：object | 节点名称：checkcontrol   类型：object | 节点名称：checkcontrol   类型：object | 节点名称：checkcontrol   类型：object | 节点名称：checkcontrol   类型：object |
| 1 | OP_REMOVEPAT | 移除患者 | string | true-将当前的患者信息删除并自动切换到下一患者并刷新医嘱
false-保留并显示患者及医嘱信息 | true-将当前的患者信息删除并自动切换到下一患者并刷新医嘱
false-保留并显示患者及医嘱信息 |
| 2 | OP_REMOVEBAR | 移除已绑条码分组 | string | true-将当前有条码的信息移除掉（动效处理）
false-保留显示医嘱信息 | true-将当前有条码的信息移除掉（动效处理）
false-保留显示医嘱信息 |
| 节点名称：orderlist类型：array<object> | 节点名称：orderlist类型：array<object> | 节点名称：orderlist类型：array<object> | 节点名称：orderlist类型：array<object> | 节点名称：orderlist类型：array<object> | 节点名称：orderlist类型：array<object> |
| 条码分组及控制信息 | 条码分组及控制信息 | 条码分组及控制信息 | 条码分组及控制信息 | 条码分组及控制信息 | 条码分组及控制信息 |
| 1 | OP_CHECK | 选择 | string | string | 0-未选择
1-选择
默认为1

绑定成功后，置为0 |
| 2 | OP_CHECKENABLE | 允许选择 | string | string | true-允许选择或取消选择
false-不允许选择或取消选择，按默认的操作

绑定成功后，置为false |
| 3 | BARGROUP | 条码分组 | string | string | 用于区分是否允许绑定在一起，是否只采样一个单位
格式：BAREXAMCODE_流水号 |
| 4 | BARCODE | 条形码 | string | string | 初始条码为空，绑定成功后更改此属性
绑定成功后，代替送检分组显示 |
| 5 | BAREXAMCODE | 分组代码 | string | string | 条码分组代码 |
| 6 | BAREXAMNAME | 分组名称 | string | string | 条码分组名称 |
| 7 | IMAGEURL | 试管地址 | string | string | 用于加载试管图片，相对地址 |
| 8 | BARLENGTH | 条码长度 | number | number | 条码总长度 |
| 9 | BARCOLOR | 试管颜色 | string | string | 十六进制颜色值 |
| 10 | BARPREPART | 条码前缀 | string | string | 条码前缀，最多两位 |
| 11 | BARSECONDCOLOR | 第二颜色 | string | string | 十六进制颜色值 |
| 12 | BARSECPREPART | 第二前缀 | string | string | 条码第二前缀，最多两位 |
| 13 | BARPRIORITY | 优先次序 | number | number | 根据此条件来排序显示 |
| 14 | SAMPLECODE | 样本类型 | string | string | 样本类型代码 |
|  | 试管区域显示内容字段对应 | 试管区域显示内容字段对应 | 试管区域显示内容字段对应 | 试管区域显示内容字段对应 | 试管区域显示内容字段对应 |
| 15 | SAMPLENAME | 样本名称 | string | string | 样本类型名称 |
| 16 | BARDESCRIBE | 分组描述 | string | string | 用于配置试管颜色 如：紫色 |
| 17 | BARCUBAGE | 采血量 | string | string | 采血量，如：50ml |
| 18 | BARNOTICE | 注意事项 | string | string | 采集注意事项，如：混匀5次 |
| 19 | EXAMGROUPNAME | 送检小组 | string | string | 送检小组，如：检验科
绑定成功后，此处显示的内容为条形码BARCODE |
| 20 | EXAMGROUPCODE | 送检小组代码 | string | string | 送检小组代码，如：0002 |
| 需要采血的医嘱列表 | 需要采血的医嘱列表 | 需要采血的医嘱列表 | 需要采血的医嘱列表 | 需要采血的医嘱列表 | 需要采血的医嘱列表 |
| 1 | APPLYNO | 报告单号 | string | string | HIS下载未确认的医嘱默认为-2 |
| 2 | SERIALNO | 序号 | string | string | HIS下载未确认的默认为-1 |
| 3 | PATIENTID | 病人唯一号 | string | string | 病人唯一号 |
| 4 | CURENO | 就诊号 | string | string | 就诊号 |
| 5 | LOGNO | 医嘱序号 | string | string | 医嘱序号，对应LIS_ACCEPTITEMS.HISXXH |
| 6 | HISAPPLYNO | 申请单号 | string | string | 申请单号，不是开申请单则为0 |
| 7 | GROUPNO | 处方序号 | string | string | 处方序号 |
| 8 | HISORDERCODE | 项目代码 | string | string | HIS项目代码 |
| 9 | HISORDERNAME | 项目名称 | string | string | HIS项目名称 |
| 10 | ITEMTYPE | 项目分类 | string | string | 0-临床 1-收费 |
| 11 | LISORDERCODE | LIS代码 | string | string | 对应LIS_HISITEM.LISORDERCODE |
| 12 | ITEMQTY | 项目数量 | string | string | 项目数量，如：1.5 |
| 13 | PRICE | 单价 | string | string | 单价，如：45.00 |
| 14 | ITEMUNIT | 单位 | string | string | 如：次 |
| 15 | APPLYTIME | 申请时间 | string | string | 如：2017-06-26 09:34:23 |
| 16 | APPLYDAYS | 申请天数 | number | number | 申请时间到当前时间的天数，如：6 |
| 17 | BEYONDTIMELIMIT | 超出天数 | string | string | 申请时间到当前时间超出设定范围
B-超出范围（申请时间后面需显示APPLYDAYS并红色标识）
其他只显示正常内容 |
| 17 | APPLYDEPTCODE | 申请科室 | string | string | 申请科室代码(LIS内码），如：2353 |
| 18 | APPLYDEPTNAME | 科室名称 | string | string | 申请科室名称(LIS内码），如：内科 |
| 19 | APPLYDOCCODE | 申请医生 | string | string | 申请医生代码(LIS内码)，如：2839 |
| 20 | APPLYDOCNAME | 医生姓名 | string | string | 申请医生姓名(LIS内码)，如：万一阁 |
| 21 | EXECDEPTCODE | 执行科室 | string | string | 执行科室代码(LIS内码)，如：4300 |
| 22 | EXECDEPTNAME | 科室名称 | string | string | 执行科室名称(LIS内码)，如：检验科 |
| 25 | ADDTYPE | 医嘱类型 | string | string | 0-HIS下载
1-LIS上传 |
| 26 | ADDTYPEDESC | 类型描述 | string | string | 医嘱类型中文描述，如：下载或上传 |
| 27 | STATUS | 状态 | string | string | 0-未确认 1-已确认 2-已拒绝 |
| 28 | STATUSDESC | 状态 | string | string | 状态中文描述，如：未确认、已确认、已拒绝 |
| 29 | CHARGEFLAG | 收费 | string | string | 0-未收费 1-已收费 2-退费 |
| 30 | CHARGEFLAGDESC | 收费描述 | string | string | 收费中文描述，如：未收费、已收费、已退费 |
| 31 | CLINICDESC | 临床诊断 | string | string | 临床诊断 |
| 32 | SAMPLECODE | 样本类型 | string | string | 样本类型代码 |
| 33 | SAMPLENAME | 样本名称 | string | string | 样本类型名称 |
| 34 | GHXH | 挂号序号 | string | string | 挂号序号，针对门诊 |
| 41 | ENTRUST | 嘱托 | string | string | 嘱托内容 |
| 42 | MJZBZ | 加急标志 | string | string | 0-常规 1-加急 |
| 43 | EXCUTETIME | 预约时间 | string | string | 预约检测的日期 |



提示信息规范：





JSON返回示例：

"orderinfo":{

"HINTCONTENT":"选择3个项目，金额 235.00 元",

"HINTCONTENTHTML":"选择3个项目，金额 235.00 元"

},

"checkcontrol":{

"OP_REMOVEPAT":"false",

"OP_REMOVEBAR":"true"

},

"orderlist":[

{

    "OP_CHECK":"1",

"OP_CHECKENABLE":"true",

...................

    "ENTRUST":"嘱托",

    "MJZBZ":"0",

    "EXCUTETIME":"2017-07-21"

},

{

    "OP_CHECK":"1",

"OP_CHECKENABLE":"true",

...................

    "ENTRUST":"嘱托",

    "MJZBZ":"0",

    "EXCUTETIME":"2017-07-21"

}

]

代码实现：

参照门诊采集的绑定流程

绑定成功后，调用301.B01记录标本日志信息

#### B02 条码绑定-打印条码

接口说明：按规则自动生成条码并绑定条码

请求URL：../barcodebinding/operate/newbarandbinding

代码文件： winning.cmp.barcodebinding.service.BarCodeBindingService

示例URL：http://192.168.14.253:8080/lis/barcodebinding/operate/newbarandbinding?orderlist=[]&patinfo={}&systype=LIS_MZCX

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| orderlist array<object>  见 201.B02的出参orderlist | orderlist array<object>  见 201.B02的出参orderlist | orderlist array<object>  见 201.B02的出参orderlist | orderlist array<object>  见 201.B02的出参orderlist | orderlist array<object>  见 201.B02的出参orderlist |
| patinfo object 当前选择的患者信息对象，结构参见201.B01 | patinfo object 当前选择的患者信息对象，结构参见201.B01 | patinfo object 当前选择的患者信息对象，结构参见201.B01 | patinfo object 当前选择的患者信息对象，结构参见201.B01 | patinfo object 当前选择的患者信息对象，结构参见201.B01 |
| systype-系统类型   门诊标本采集：LIS_MZCX  住院标本采集：LIS_ZYCX | systype-系统类型   门诊标本采集：LIS_MZCX  住院标本采集：LIS_ZYCX | systype-系统类型   门诊标本采集：LIS_MZCX  住院标本采集：LIS_ZYCX | systype-系统类型   门诊标本采集：LIS_MZCX  住院标本采集：LIS_ZYCX | systype-系统类型   门诊标本采集：LIS_MZCX  住院标本采集：LIS_ZYCX |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：orderinfo  汇总信息 类型：object | 节点名称：orderinfo  汇总信息 类型：object | 节点名称：orderinfo  汇总信息 类型：object | 节点名称：orderinfo  汇总信息 类型：object | 节点名称：orderinfo  汇总信息 类型：object |
| 1 | HINTCONTENT | 提示 | string | 提示信息，文字形式 |
| 2 | HINTCONTENTHTML | 提示 | string | 提示信息，文字形式 |
| 3 | BARLIST | 已绑条码 | array<string> | 已绑定条码的数组 |
| 节点名称：checkcontrol   类型：object | 节点名称：checkcontrol   类型：object | 节点名称：checkcontrol   类型：object | 节点名称：checkcontrol   类型：object | 节点名称：checkcontrol   类型：object |
| 1 | OP_REMOVEPAT | 移除患者 | string | true-将当前的患者信息删除并自动切换到下一患者并刷新医嘱
false-保留并显示患者及医嘱信息 |
| 2 | OP_REMOVEBAR | 移除已绑条码分组 | string | true-将当前有条码的信息移除掉（动效处理）
false-保留显示医嘱信息 |
| 节点名称：orderlist类型：array<object> | 节点名称：orderlist类型：array<object> | 节点名称：orderlist类型：array<object> | 节点名称：orderlist类型：array<object> | 节点名称：orderlist类型：array<object> |
| 条码分组及控制信息 | 条码分组及控制信息 | 条码分组及控制信息 | 条码分组及控制信息 | 条码分组及控制信息 |
| 1 | OP_CHECK | 选择 | string | 0-未选择
1-选择
默认为1 |
| 2 | OP_CHECKENABLE | 允许选择 | string | true-允许选择或取消选择
false-不允许选择或取消选择，按默认的操作 |
| 3 | BARGROUP | 条码分组 | string | 用于区分是否允许绑定在一起，是否只采样一个单位
格式：BAREXAMCODE_流水号 |
| 4 | BARCODE | 条形码 | string | 初始条码为空，绑定成功后更改此属性 |
| 5 | BAREXAMCODE | 分组代码 | string | 条码分组代码 |
| 6 | BAREXAMNAME | 分组名称 | string | 条码分组名称 |
| 7 | IMAGEURL | 试管地址 | string | 用于加载试管图片，相对地址 |
| 8 | BARLENGTH | 条码长度 | number | 条码总长度 |
| 9 | BARCOLOR | 试管颜色 | string | 十六进制颜色值 |
| 10 | BARPREPART | 条码前缀 | string | 条码前缀，最多两位 |
| 11 | BARSECONDCOLOR | 第二颜色 | string | 十六进制颜色值 |
| 12 | BARSECPREPART | 第二前缀 | string | 条码第二前缀，最多两位 |
| 13 | BARPRIORITY | 优先次序 | number | 根据此条件来排序显示 |
| 14 | SAMPLECODE | 样本类型 | string | 样本类型代码 |
|  | 试管区域显示内容字段对应 | 试管区域显示内容字段对应 | 试管区域显示内容字段对应 | 试管区域显示内容字段对应 |
| 15 | SAMPLENAME | 样本名称 | string | 样本类型名称 |
| 16 | BARDESCRIBE | 分组描述 | string | 用于配置试管颜色 如：紫色 |
| 17 | BARCUBAGE | 采血量 | string | 采血量，如：50ml |
| 18 | BARNOTICE | 注意事项 | string | 采集注意事项，如：混匀5次 |
| 19 | EXAMGROUPNAME | 送检小组 | string | 送检小组，如：检验科 |
| 20 | EXAMGROUPCODE | 送检小组代码 | string | 送检小组代码，如：0002 |
| 需要采血的医嘱列表 | 需要采血的医嘱列表 | 需要采血的医嘱列表 | 需要采血的医嘱列表 | 需要采血的医嘱列表 |
| 1 | APPLYNO | 报告单号 | string | HIS下载未确认的医嘱默认为-2 |
| 2 | SERIALNO | 序号 | string | HIS下载未确认的默认为-1 |
| 3 | PATIENTID | 病人唯一号 | string | 病人唯一号 |
| 4 | CURENO | 就诊号 | string | 就诊号 |
| 5 | LOGNO | 医嘱序号 | string | 医嘱序号，对应LIS_ACCEPTITEMS.HISXXH |
| 6 | HISAPPLYNO | 申请单号 | string | 申请单号，不是开申请单则为0 |
| 7 | GROUPNO | 处方序号 | string | 处方序号 |
| 8 | HISORDERCODE | 项目代码 | string | HIS项目代码 |
| 9 | HISORDERNAME | 项目名称 | string | HIS项目名称 |
| 10 | ITEMTYPE | 项目分类 | string | 0-临床 1-收费 |
| 11 | LISORDERCODE | LIS代码 | string | 对应LIS_HISITEM.LISORDERCODE |
| 12 | ITEMQTY | 项目数量 | string | 项目数量，如：1.5 |
| 13 | PRICE | 单价 | string | 单价，如：45.00 |
| 14 | ITEMUNIT | 单位 | string | 如：次 |
| 15 | APPLYTIME | 申请时间 | string | 如：2017-06-26 09:34:23 |
| 16 | APPLYDAYS | 申请天数 | number | 申请时间到当前时间的天数，如：6 |
| 17 | BEYONDTIMELIMIT | 超出天数 | string | 申请时间到当前时间超出设定范围
B-超出范围（申请时间后面需显示APPLYDAYS并红色标识）
其他只显示正常内容 |
| 17 | APPLYDEPTCODE | 申请科室 | string | 申请科室代码(LIS内码），如：2353 |
| 18 | APPLYDEPTNAME | 科室名称 | string | 申请科室名称(LIS内码），如：内科 |
| 19 | APPLYDOCCODE | 申请医生 | string | 申请医生代码(LIS内码)，如：2839 |
| 20 | APPLYDOCNAME | 医生姓名 | string | 申请医生姓名(LIS内码)，如：万一阁 |
| 21 | EXECDEPTCODE | 执行科室 | string | 执行科室代码(LIS内码)，如：4300 |
| 22 | EXECDEPTNAME | 科室名称 | string | 执行科室名称(LIS内码)，如：检验科 |
| 25 | ADDTYPE | 医嘱类型 | string | 0-HIS下载
1-LIS上传 |
| 26 | ADDTYPEDESC | 类型描述 | string | 医嘱类型中文描述，如：下载或上传 |
| 27 | STATUS | 状态 | string | 0-未确认 1-已确认 2-已拒绝 |
| 28 | STATUSDESC | 状态 | string | 状态中文描述，如：未确认、已确认、已拒绝 |
| 29 | CHARGEFLAG | 收费 | string | 0-未收费 1-已收费 2-退费 |
| 30 | CHARGEFLAGDESC | 收费描述 | string | 收费中文描述，如：未收费、已收费、已退费 |
| 31 | CLINICDESC | 临床诊断 | string | 临床诊断 |
| 32 | SAMPLECODE | 样本类型 | string | 样本类型代码 |
| 33 | SAMPLENAME | 样本名称 | string | 样本类型名称 |
| 34 | GHXH | 挂号序号 | string | 挂号序号，针对门诊 |
| 41 | ENTRUST | 嘱托 | string | 嘱托内容 |
| 42 | MJZBZ | 加急标志 | string | 0-常规 1-加急 |
| 43 | EXCUTETIME | 预约时间 | string | 预约检测的日期 |



提示信息规范：





JSON返回示例：

"orderinfo":{

"HINTCONTENT":"选择3个项目，金额 235.00 元",

"HINTCONTENTHTML":"选择3个项目，金额 235.00 元"

},

"checkcontrol":{

"OP_REMOVEPAT":"false",

"OP_REMOVEBAR":"true"

},

"orderlist":[

{

    "OP_CHECK":"1",

"OP_CHECKENABLE":"true",

...................

    "ENTRUST":"嘱托",

    "MJZBZ":"0",

    "EXCUTETIME":"2017-07-21"

},

{

    "OP_CHECK":"1",

"OP_CHECKENABLE":"true",

...................

    "ENTRUST":"嘱托",

    "MJZBZ":"0",

    "EXCUTETIME":"2017-07-21"

}

]

代码实现：

代码实现：参照门诊采集的绑定流程

绑定成功后，调用301.B01记录标本日志信息

#### ※B03 获取条形码信息 

接口说明：打印条形码获取条形码列表数据

请求URL：../barcodebinding/request/getsamplemsglist

代码文件： winning.cmp.barcodebinding.service.BarCodeBindingService

示例URL：http://192.168.14.253:8080/lis/barcodebinding/request/getsamplelist?barlist=[]&type=0&hospitalcode=9999&printmode=2

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | barlist | 条码列表 | array<string> | 条形码列表 |
| 2 | type | 类型 | string | 0-条形码所有数据
1-获取未入库的数据 |
| 3 | printmode | 打印模式 | string | 0-预览 1-格式设计 2-打印 |
| 4 | hospitalcode | 机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码 |
| 5 | printtype | 打印类型 | string | 可为空  为空或者1-打印模式  2-补打模式 |
| 6 | reason | 补打原因 | String | 当打印模式的时候原因为空
补打模式的时候为补打原因 |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：barlist 打印的条码信息 类型：array<object> 一个条码占一行记录 | 节点名称：barlist 打印的条码信息 类型：array<object> 一个条码占一行记录 | 节点名称：barlist 打印的条码信息 类型：array<object> 一个条码占一行记录 | 节点名称：barlist 打印的条码信息 类型：array<object> 一个条码占一行记录 | 节点名称：barlist 打印的条码信息 类型：array<object> 一个条码占一行记录 |
| 1 | WARDORREG | 病人类型代码 | string | 病人类型代码，如：1 |
| 2 | WARDORREGNAME | 病人类型名称 | string | 病人类型名称，如：住院 |
| 3 | BRLX | 病人类型 | string | HIS病人类型 |
| 4 | PATIENTID | 病人唯一号 | string | 病人唯一号 |
| 5 | CURENO | 就诊号 | string | 就诊号 |
| 6 | PATNAME | 患者姓名 | string | 患者姓名 |
| 7 | HOSPNO | 住院号 | string | 住院号 |
| 8 | CARDNO | 磁卡号 | string | 磁卡号 |
| 9 | TXM | 条形码 | string | 条形码 |
| 10 | SEXDESC | 性别 | string | 性别 |
| 11 | AGEDESC | 年龄 | string | 年龄描述 |
| 12 | WARD | 病区代码 | string | 病区代码 |
| 13 | WARDNAME | 病区名称 | string | 病区名称 |
| 14 | BEDNO | 床号 | string | 床号 |
| 15 | SAMPLECODE | 标本代码 | string | 标本代码 |
| 16 | SAMPLENAME | 标本名称 | string | 标本名称 |
| 17 | ITEMNUM | 项目总数 | number | 当前条码下的条码总数 |
| 18 | SUMPRICE | 项目金额 | number | 当前条码下的总金额 |
| 19 | ITEMNAME | 项目汇总 | string | 项目名称集合，HISORDERNAME+CHAR(13) |
| 20 | ITEMNAME_HOR | 项目汇总 | string | 项目名称集合_横向，HISORDERNAME；+分隔 |
| 21 | ITEMNAMEJC | 项目汇总 | string | 项目简称汇总，BARCODELABELNAME+CHAR(13)
BARCODELABELNAME为空时取HISORDERNAME |
| 22 | ITEMNAMEJC_HOR | 项目汇总 | string | 项目简称汇总，BARCODELABELNAME+CHAR(13)
BARCODELABELNAME为空时取HISORDERNAME |
| 23 | MJZBZ | 加急标志 | string | 0-常规 
1-加急 |
| 24 | ENTRUST | 嘱托 | string | 嘱托内容，用逗号分隔；取LIS_ACCEPTITEMS.ENTRUST |
| 25 | CLINICDESC | 临床诊断 | string | 临床诊断内容，LIS_ORDERMASTER.CLINICDESC |
| 26 | CXSJ | 绑定时间 | string | 格式：yyyy-MM-dd HH:mm:ss |
| 27 | DRAWDATE | 采样时间 | string | 格式：yyyy-MM-dd HH:mm:ss |
| 28 | DRAWUSERCODE | 采样人工号 | string | 采样人工号 |
| 29 | DRAWUSERNAME | 采样人姓名 | string | 采样人姓名 |
| 30 | EXAMGROUPCODE | 送检小组代码 | string | 送检小组代码 |
| 31 | EXAMGROUPNAME | 送检小组名称 | string | 送检小组名称 |
| 32 | BAREXAMCODE | 分组代码 | string | 分组代码 |
| 33 | BAREXAMNAME | 分组名称 | string | 分组名称 |
| 34 | BARDESCRIBE | 分组描述 | string | 如：紫色 |
| 35 | BARRANGE | 适用范围 | string | 适用范围 |
| 36 | BARCUBAGE | 采血量 | string | 如：5ml |
| 37 | BARADDITIVENAME | 添加剂 | string | 如：抗凝剂 |
| 38 | BARNOTICE | 采集事项 | string | 如：空腹，混匀5次 |
| 39 | BARPRIORITY | 优先次序 | number | 用于排序，按此字段先后顺序进行排序打印 |
| 40 | SUBMISSIONPLACE | 送检地点 | string | 送检地点 |
| 41 | PRINTCOUNT | 打印份数 | number | 打印份数
BRLX+LIS_BAREXAMCLASS |
| 42 | GROUPBARCOUNT | 组条码数 | number | 一组条码中的条码数量 |
| 43 | GROUPBARNO | 组条码序号 | number | 一组条码中的序号 |
| 44 | BARGROUPNO | 组号 | string | 格式：yyyyMMDD+流水号
按人员区分 |
| 45 | TARGETLAB | 检测机构 | string | 检测机构代码 LIS_ORDERMASTER.TARGETLAB |
| 46 | TARGETLABNAME | 机构名称 | string | 检测机构名称 SYS_HOSPITALINFO.HOSPITALNAME |
| 47 | TAGETLABJC | 机构简称 | string | 检测机构简称
SYS_HOSPITALINFO.HOSPITALSHORTNAME |





JSON返回示例：

"barlist":[

{

"WARDORREG":"1",

"WARDORREG":"住院",

    "BRLX":"1",

"PATIENTID":2344321",

"CURENO":2344321",

...................

"TARGETLAB":"43023002322",

"TARGETLABNAME":"上海市第六人民医院",

"TARGETLABJC":"上海六院"

},

{

"WARDORREG":"1",

"WARDORREG":"住院",

    "BRLX":"1",

"PATIENTID":2344321",

"CURENO":2344321",

...................

"TARGETLAB":"43023002322",

"TARGETLABNAME":"上海市第六人民医院",

"TARGETLABJC":"上海六院"

}

]

代码实现：

参照存储过程：lsp_wzh_txmreturn

按项目返回，获取报告时间算法要封装成单独的

打印模式：printmode=2时，调用301.B01记录标本操作日志（回执单打印），并更新标本打印状态

#### B04 获取回执单信息

接口说明：获取标本取报告时间

请求URL：../barcodebinding/request/getsamplereturn

代码文件： winning.cmp.barcodebinding.service.BarCodeBindingService

示例URL：http://192.168.14.253:8080/lis/barcodebinding/request/getsamplereturn?barlist=[]&patientid=1111&brlx=0&type=1&printstatus=0&&hospitalcode=9999&printmode=2&systype=LIS_MZCX

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | barlist | 条码列表 | array<string> | 条形码列表 |
| 2 | patientid | patientid | string | 病人内码 |
| 3 | brlx | 病人类型 | string | 0-门诊 1-住院 3、4-体检 |
| 4 | type | 类型 | string | 1-获取一个条形码项目中最大的报告时间
2-一个项目对应一个报告时间
3-标本签收调用，签收时间做时间判断依据 |
| 5 | printstatus | 打印状态 | string | 0-未打印
1-已打印
-1-所有的 |
| 6 | printmode | 打印模式 | string | 0-打印预览
1-格式设计
2-打印
-1-只计算保存到后台 |
| 7 | hospitalcode | 机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码 |
| 8 | systype | 系统类型 | string | LIS_MZCX-门诊采集
LIS_ZYCX-住院采集 |
| 9 | printtype | 打印类型 | string | 可为空  为空或者1-打印模式  2-补打模式 |
| 10 | reason | 补打原因 | String | 当打印模式的时候原因为空
补打模式的时候为补打原因 |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- | --- |
| 节点名称：repreturnlist 打印的条码信息 类型：array<object> 一个条码占一行记录 | 节点名称：repreturnlist 打印的条码信息 类型：array<object> 一个条码占一行记录 | 节点名称：repreturnlist 打印的条码信息 类型：array<object> 一个条码占一行记录 | 节点名称：repreturnlist 打印的条码信息 类型：array<object> 一个条码占一行记录 | 节点名称：repreturnlist 打印的条码信息 类型：array<object> 一个条码占一行记录 | 节点名称：repreturnlist 打印的条码信息 类型：array<object> 一个条码占一行记录 |
| 1 | WARDORREG | WARDORREG | 病人类型代码 | string | 病人类型代码，如：1 |
| 2 | WARDORREGNAME | WARDORREGNAME | 病人类型名称 | string | 病人类型名称，如：住院 |
| 3 | BRLX | BRLX | 病人类型 | string | HIS病人类型 |
| 4 | PATIENTID | PATIENTID | 病人唯一号 | string | 病人唯一号 |
| 5 | CURENO | CURENO | 就诊号 | string | 就诊号 |
| 6 | PATNAME | PATNAME | 患者姓名 | string | 患者姓名 |
| 7 | HOSPNO | HOSPNO | 住院号 | string | 住院号 |
| 8 | CARDNO | CARDNO | 磁卡号 | string | 磁卡号 |
| 9 | TXM | TXM | 条形码 | string | 条形码 |
| 10 | SEXDESC | SEXDESC | 性别 | string | 性别 |
| 11 | AGEDESC | AGEDESC | 年龄 | string | 年龄描述 |
| 12 | WARD | WARD | 病区代码 | string | 病区代码 |
| 13 | WARDNAME | WARDNAME | 病区名称 | string | 病区名称 |
| 14 | BEDNO | BEDNO | 床号 | string | 床号 |
| 15 | SAMPLECODE | SAMPLECODE | 标本代码 | string | 标本代码 |
| 16 | SAMPLENAME | SAMPLENAME | 标本名称 | string | 标本名称 |
| 18 | SUMPRICE | SUMPRICE | 项目金额 | number | 当前条码下的总金额 |
| 19 | LISORDERCODE | LISORDERCODE | 项目代码 | string | 项目代码，LISORDERCODE |
| 20 | HISORDERCODE | HISORDERCODE | HIS项目代码 | string | HIS项目代码，HISORDERCODE |
| 21 | HISORDERNAME | HISORDERNAME | 项目名称 | string | 项目名称 |
| 22 | HISORDERNAMEJC | HISORDERNAMEJC | 项目简称 | string | 项目简称，BARCODELABELNAME
BARCODELABELNAME为空时取HISORDERNAME |
| 23 | MJZBZ | MJZBZ | 加急标志 | string | 0-常规 
1-加急 |
| 24 | ENTRUST | ENTRUST | 嘱托 | string | 嘱托内容，用逗号分隔；取LIS_ACCEPTITEMS.ENTRUST |
| 25 | CLINICDESC | CLINICDESC | 临床诊断 | string | 临床诊断内容，LIS_ORDERMASTER.CLINICDESC |
| 26 | APPLYTIME | APPLYTIME | 申请时间 | string | 格式：yyyy-MM-dd HH:mm:ss |
| 26 | CXSJ | CXSJ | 绑定时间 | string | 格式：yyyy-MM-dd HH:mm:ss |
| 27 | DRAWDATE | DRAWDATE | 采样时间 | string | 格式：yyyy-MM-dd HH:mm:ss |
| 28 | DRAWUSERCODE | DRAWUSERCODE | 采样人工号 | string | 采样人工号 |
| 29 | DRAWUSERNAME | DRAWUSERNAME | 采样人姓名 | string | 采样人姓名 |
| 30 | EXECDEPTCODE | EXECDEPTCODE | 执行科室代码 | string | 执行科室代码 |
| 31 | EXECDEPTNAME | EXECDEPTNAME | 执行科室名称 | string | 执行科室名称 |
| 32 | BAREXAMCODE | BAREXAMCODE | 分组代码 | string | 分组代码 |
| 33 | BAREXAMNAME | BAREXAMNAME | 分组名称 | string | 分组名称 |
| 34 | APPLYDEPTCODE | APPLYDEPTCODE | 申请科室 | string | 申请科室,LIS_ACCEPTITEMS.APPLYDEPTCODE |
| 35 | APPLYDEPTNAME | APPLYDEPTNAME | 科室名称 | string | LIS_ACCEPTITEMS.APPLYDEPTNAME |
| 36 | APPLYDOCCODE | APPLYDOCCODE | 申请医生 | string | LIS_ACCEPTITEMS.APPLYDOCCODE |
| 37 | APPLYDOCNAME | APPLYDOCNAME | 医生姓名 | string | LIS_ACCEPTITEMS.APPLYDOCNAME |
| 38 | PRINTORDER | PRINTORDER | 打印顺序 | string | 回执单打印顺序 |
| 39 | RETURNDESC | RETURNDESC | 领取说明 | string | 统一领取说明 |
| 40 | RETURNDAY | RETURNDAY | 领取时间 | string | 领取时间 |
| 41 | RETURNTIMEDESC | RETURNTIMEDESC | 领取时间说明 | string | 统一领取时间 |
| 42 | RETURNCONTENT | RETURNCONTENT | 回执内容 | string | 打印回执内容，打印时选取此字段打印即可 |
| 43 | PRINTORDER_GR | PRINTORDER_GR | 分组打印顺序 | string | 回执单打印顺序，按条形码分组取最大取报告时间 |
| 44 | RETURNDESC_GR | RETURNDESC_GR | 分组领取说明 | string | 统一领取说明，按条形码分组取最大取报告时间 |
| 45 | RETUENDAY_GR | RETUENDAY_GR | 分组领取时间 | string | 领取时间，按条形码分组取最大取报告时间 |
| 46 | RETURNTIMEDESC_GR | RETURNTIMEDESC_GR | 分组领取时间说明 | string | 统一领取时间，按条形码分组取最大取报告时间 |
| 47 | RETURNCONTENT_GR | RETURNCONTENT_GR | 分组回执内容 | string | 打印回执内容，打印时选取此字段打印即可，按条形码分组取最大取报告时间 |





JSON返回示例：

"repreturnlist":[

{

"WARDORREG":"1",

"WARDORREG":"住院",

    "BRLX":"1",

"PATIENTID":2344321",

"CURENO":2344321",

...................

"RETUENDAY_GR":"2017-08-19",

"RETURNTIMEDESC_GR":"16:00",

"RETURNCONTENT_GR":"2017-08-19 16:00"

},

{

"WARDORREG":"1",

"WARDORREG":"住院",

    "BRLX":"1",

"PATIENTID":2344321",

"CURENO":2344321",

...................

"RETUENDAY_GR":"2017-08-19",

"RETURNTIMEDESC_GR":"16:00",

"RETURNCONTENT_GR":"2017-08-19 16:00"

}

]

#### ※B05 获取材料费信息

接口说明：根据项目或条码信息获取材料费

请求URL：../barcodebinding/request/getmateriallist

代码文件： winning.cmp.barcodebinding.service.BarCodeBindingService

示例URL：http://192.168.14.253:8080/lis/barcodebinding/request/getmateriallist?codetype=1&codestr=120001;120561&wardorreg=0&hisuseflag=0&systype=LIS_MZCX&age=34&ageunit=岁&hospitalcode=9999&syscode=LIMS

场景描述：

LIS系统调用，传入项目或条码信息

HIS系统收费处调用获取待收取的材料费

接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | codetype | 传值类型 | string | 0-按条码获取 1-按收费项目获取 |
| 2 | codestr | 传值内容 | string | 分号区分，按1-收费项目获取时
项目代码,标本种类,门急诊标志,执行科室,申请单号,条码组号(LIS系统调用时) |
| 3 | wardorreg | 病人类型 | string | 病人类型内码 |
| 4 | hisuseflag | 外部调用 | string | 0-内部调用 1-外部调用
默认为0 |
| 5 | systype | 系统类型 | string | 门诊采集：LIS_MZCX 住院采集：LIS_ZYCX |
| 6 | age | 年龄 | string | 年龄 |
| 7 | ageunit | 年龄单位 | string | 年龄单位 |
| 8 | hospitalcode | 机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码 |
| 9 | syscode | 系统代码 | string | 系统代码；如LIMS |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：materialinfo材料费汇总 类型：array<object> | 节点名称：materialinfo材料费汇总 类型：array<object> | 节点名称：materialinfo材料费汇总 类型：array<object> | 节点名称：materialinfo材料费汇总 类型：array<object> | 节点名称：materialinfo材料费汇总 类型：array<object> |
| 1 | SUMCOUNT | 材料总数量 | number | 材料费总数量 |
| 2 | SUMPRICE | 材料费总额 | number | 材料费总金额 |
| 节点名称：materiallist材料费列表 类型：array<object> | 节点名称：materiallist材料费列表 类型：array<object> | 节点名称：materiallist材料费列表 类型：array<object> | 节点名称：materiallist材料费列表 类型：array<object> | 节点名称：materiallist材料费列表 类型：array<object> |
| 1 | LISORDERCODE | LIS项目代码 | string | LIS项目代码 |
| 2 | HISORDERCODE | HIS代码 | string | HIS代码 |
| 3 | HISORDERNAME | HIS项目名称 | string | HIS项目名称 |
| 4 | ITEMTYPE | 项目类型 | string | 项目类型 |
| 5 | PRICE | 单价 | string | 单价 |
| 6 | SQTY | 数量 | string | 数量 |
| 7 | MATERIALTYPE | 材料费类型 | string | 材料费类型 |
| 8 | TXM | 条形码 | string | 条形码 |
| 9 | MATEXECDEPT | 执行科室代码 | string | 执行科室代码 |
| 10 | SUPPLIESCODE | 物资代码 | string | 物资代码 |





JSON返回示例：

"materialinfo":{

"SUMCOUNT":4.00,

"SUMPRICE":8.9

},

"materiallist":[

{

    "LISORDERCODE":"1900001",

    "HISORDERCODE":"900001",

    "HISORDERNAME":"试管费",

    "ITEMTYPE":"1",

    "PRICE":"1.5",

    "SQTY":"1.0",

    "SUMMATMONEY":"4",

    "MATERIALTYPE":"1",

    "TXM":"z",

    "MATEXECDEPT":"",

    "SUPPLIESCODE":""

},

{

    "LISORDERCODE":"1900001",

    "HISORDERCODE":"900001",

    "HISORDERNAME":"试管费",

    "ITEMTYPE":"1",

    "PRICE":"1.5",

    "SQTY":"1.0",

    "SUMMATMONEY":"4",

    "MATERIALTYPE":"1",

    "TXM":"z2",

    "MATEXECDEPT":"",

    "SUPPLIESCODE":""

},

{

    "LISORDERCODE":"1900002",

    "HISORDERCODE":"900002",

    "HISORDERNAME":"采血针",

    "ITEMTYPE":"1",

    "PRICE":"1",

    "SQTY":"1.0",

    "SUMMATMONEY":"4",

    "MATERIALTYPE":"0",

    "TXM":"",

    "MATEXECDEPT":"",

    "SUPPLIESCODE":""

}

]

代码实现：

参照调用lsp_wzh_getMaterialOrder来实现

#### B06 取消条码绑定

接口说明：界面选择一个条码，进行取消动作。

取消有多种情况。

请求URL：../barcodebinding/operate/Cancelbarbinding

代码文件： winning.cmp.barcodebinding.service.BarCodeBindingService

示例URL：http://192.168.14.253:8080/lis/barcodebinding/operate/Cancelbarbinding?canceltype=barcode=&hisxxh=&canclememo=&systype=LIS_MZCX

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | canceltype | 动作类型 | string | 1 取消绑定
2取消绑定并医嘱作废
3强制取消 |
| 2 | barcode | 条形码 | string | 当前取消绑定的条形码，必须传 |
| 3 | lisordercode | 项目代码 | string | lis项目代码，对应LISORDERCODE
允许为空，为空时表示取消整个条码 |
| 4 | cancelmemo | 取消原因 | string | 取消原因，如：患者不做了 |
| 5 | systype | 系统类型 | string | 门诊采集：LIS_MZCX 住院采集：LIS_ZYCX |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：checkcontrol   类型：object | 节点名称：checkcontrol   类型：object | 节点名称：checkcontrol   类型：object | 节点名称：checkcontrol   类型：object | 节点名称：checkcontrol   类型：object |
| 1 | OP_REMOVEBAR | 移除取消绑定的条码 | string | true - 将当前条码所有医嘱移除
false - 只移除选中的单个医嘱 |
| 2 | TXM | 条形码 | string | 条形码 |
| 3 | LISORDERCODE | 取消的项目 | string | 为空表示取消了整个条码 |



JSON返回示例：

代码实现：

取消成功后，界面上删除单行记录或整个条码记录。

#### B07 创建生成新条码

接口说明：通过点击查询生成新条码，获取该样本规则的新条码；

URL：../ /barcodebinding/request/createtxm

实例URL:http://...../lis/barcodebinding/request/createtxm

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | barexamcode | 条码分组代码 | string | 对应字段BAREXAMCODE |



接口出参【ResposeMessage.data->object】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | TXM | 条形码 | string | 生成新的条形码：1130000834943 |





JSON返回示例：

"txminfo":

{

     "TXM":"130000834943",

}

代码实现：

相关表结构：LIS_ORDERMASTER\LIS_ACCEPTITEMS\SLAVE\

## 外部接口模块设计

### 2.2.1 前后端交互

#### 2.2.1.1请求数据返回 ResposeMessage

接口说明：前端与服务端交互请求，后端返回数据格式结构统一为JSON格式，基本格式如下，对应描述请看“响应参数列表”，在前端获取到响应数据后，首先要判断type是否为“SUCCESS”，是表示成功，否则按错误\失败逻辑处理 { "data":"", "failtCode":"", "link":"", "message":"获取成功", "messageCode":"", "type":"SUCCESS" }，后端返回前端的数据类型均为ResposeMessage。

数据结构

| NO. | 变量名 | 含义 | 类型 | 说明 |
| --- | --- | --- | --- | --- |
| 1 | type | 消息类型 | string | SUCCESS-成功 
ERROR-系统出现异常或错误
FAILT-业务逻辑处理失败 |
| 2 | messageCode | 消息代码 | string | 用于区分消息代码 |
| 3 | message | 消息内容 | string | type=ERROR时则为系统错误消息提示内容
type=FAILT时则为业务提示信息 |
| 4 | link | URL链接 | string | 用于提示用户后可以点击查看详情信息，默认为空 |
| 5 | data | 数据集合 | object | 用于存储返回的数据 |



messageCode定义与规范



### 患者360

 模块接口视图

| NO. | 接口编号 | 接口名称 | 说明 |
| --- | --- | --- | --- |
| 1 | 300.B01 | 获取患者最近检验报告（按检验分类）
../patview/request/getlabreportbyexamclass | 获取患者最近检验报告，按检验分类返回报告及报告检查天数 |
| 2 | 300.B02 | 获取患者指定项目结果集合
../patview/request/getpatlabresults | 获取患者指定项目结果集合，用于趋势分析等使用 |
| 3 | 300.B03 | 获取患者生物危害信息
../patview/request/getpatsensitiveflag | 获取指定患者的生物危害标志 |
| 4 | 300.B04 | 获取患者生物危害指标信息
../patview/request/getpatsensitiveflag | 获取指定患者的生物危害指标信息 |
|  |  |  |  |





####  获取患者最近检验报告（按检验分类）

接口说明：获取患者最近检验报告，按检验分类返回报告及报告检查天数

请求URL：../patview/request/getlabreportbyexamclass

示例URL：http://192.168.14.253:8080/lis/patview/request/getlabreportbyexamclass

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 说明 |
| --- | --- | --- | --- | --- |
| 1 | applyno | 报告单号 | number | 不强制传入，不传入时，按患者获取
传入报告单号时，后续会按报告关联性进行选择性排序显示 |
| 2 | brlx | 病人类型 | string | 0-门诊 1-住院 3、4-体检 |
| 3 | patientid | 病人唯一号 | string | 取患者属性：PATIENTID |
| 4 | cureno | 就诊号 | string | 取患者属性：CURENO |
| 5 | cardno | 磁卡号 | string | 取患者属性：CARDNO |
| 6 | hospno | 病员号\|住院号 | string | 门诊-病员号
住院-住院号
取患者属性：HOSPNO |
| 7 | patname | 患者姓名 | string | 取患者属性：PATNAME |
| 8 | sex | 性别 | string | 取患者属性：SEX |
| 9 | idnum | 身份证号 | string | 取患者属性：IDNUM |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：labreportitems   类型：array<object> | 节点名称：labreportitems   类型：array<object> | 节点名称：labreportitems   类型：array<object> | 节点名称：labreportitems   类型：array<object> | 节点名称：labreportitems   类型：array<object> |
| 1 | APPLYNO | 报告单号 | string | 最近一份报告的报告单号 |
| 1 | EXAMCODE | 分类代码 | string | 检验分类代码 |
| 2 | EXAMNAME | 分类名称 | string | 检验分类名称，如：生化、免疫 |
| 3 | EXAMDAYS | 最近检测 | string | 最近一次离当前时间的天数，如：12天 |
| 4 | ABNORMALCNT | 异常结果
数量 | number | 异常结果数量
异常结果判断依据：HIGHLOWFLAG <> “” |
| 6 | ABNORMALFLAG | 异常结果
标志 | string | 1-存在异常，提示区域红色
0-不存在异常结果，提示区域蓝色 |



提示信息规范：



后台返回示例：



JSON返回示例：

[

{

    "EXAMCODE":"1",

    "EXAMNAME":"生化",

    "EXAMDAYS":"5天",

    "ABNORMALCNT":"0",

    "ABNORMALFLAG":"0"

},

{

    "EXAMCODE":"2",

    "EXAMNAME":"临检",

    "EXAMDAYS":"25天",

    "ABNORMALCNT":"3",

    "ABNORMALFLAG":"1"

},

{

    "EXAMCODE":"3",

    "EXAMNAME":"免疫",

    "EXAMDAYS":"1月",

    "ABNORMALCNT":"4",

    "ABNORMALFLAG":"1"

},

{

    "EXAMCODE":"4",

    "EXAMNAME":"血常规",

    "EXAMDAYS":"3天",

    "ABNORMALCNT":"1",

    "ABNORMALFLAG":"1"

}

]

代码实现：

表结构：LIS_LIST\LIS_RESULT

程序实现：

报告必须为已审核或发布的报告

姓名不能为空，为空时返回为null

其他条件需判断哪个不为空，通过不为空的内容去查询

表结构关系：

LIS_LIST.APPLYNO = LIS_RESULT.APPLYNO

LIS_LIST.EXAMCODE\LIS_LIST.EXAMNAME

#### 获取患者指定项目结果集合

接口说明：获取患者指定项目结果集合，用于趋势分析等使用

请求URL：../patview/request/getpatlabresults

示例URL：http://192.168.14.253:8080/lis/patview/request/getpatlabresults

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 说明 |
| --- | --- | --- | --- | --- |
| 1 | brlx | 病人类型 | string | 0-门诊 1-住院 3、4-体检 |
| 2 | patientid | 病人唯一号 | string | 取患者属性：PATIENTID |
| 3 | cureno | 就诊号 | string | 取患者属性：CURENO |
| 4 | cardno | 磁卡号 | string | 取患者属性：CARDNO |
| 5 | hospno | 病员号\|住院号 | string | 门诊-病员号
住院-住院号
取患者属性：HOSPNO |
| 6 | patname | 患者姓名 | string | 取患者属性：PATNAME |
| 7 | sex | 性别 | string | 取患者属性：SEX |
| 8 | idnum | 身份证号 | string | 取患者属性：IDNUM |
| 9 | begdate | 起始日期 | string | 格式：yyyy-mm-dd 如：2017-06-01 |
| 10 | enddate | 终止日期 | string | 格式：yyyy-mm-dd 如：2017-06-27 |
| 11 | times | 最近次数 | string | 表示取时间段内符合要求的最近几次结果
传0表示不限次数 |
| 12 | applyno | 本次报告单号 | string | 允许为空，如：1005 |
| 项目集合：itemcodelist 类型 Array<object> | 项目集合：itemcodelist 类型 Array<object> | 项目集合：itemcodelist 类型 Array<object> | 项目集合：itemcodelist 类型 Array<object> | 项目集合：itemcodelist 类型 Array<object> |
| 1 | itemcode | 项目代码 | string | 项目代码 |
| 2 | instid | 仪器id | string | 仪器id，允许为空 |
| 3 | samplecode | 标本代码 | string | 标本代码 |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：labreportresults   类型：array<object> | 节点名称：labreportresults   类型：array<object> | 节点名称：labreportresults   类型：array<object> | 节点名称：labreportresults   类型：array<object> | 节点名称：labreportresults   类型：array<object> |
| 1 | APPLYNO | 报告单号 | string | 最近一份报告的报告单号 |
| 2 | EXECTIME | 检测日期 | string | 格式：yyyy-mm-dd 如：2017-06-27 |
| 3 | SAMPLECODE | 标本代码 | string | 标本代码 |
| 4 | SAMPLENAME | 标本名称 | string | 标本名称 |
| 5 | ITEMCODE | 项目代码 | string | 项目代码 |
| 6 | ITEMNAME | 项目名称 | string | 项目名称 |
| 7 | RESULTTYPE | 结果类型 | string | 1-数字 2-字符 |
| 8 | RESULT | 结果 | string | 项目结果 |
| 9 | REFERENCERANGE | 参考范围 | string | 如：12.3~23.00 |
| 10 | UNIT | 结果单位 | string | 如：^10E9mg/l |
| 11 | HIGHLOWFLAG | 高低标志 | string | H-偏高 L-偏低 HH-显著偏高 LL-显著偏低 P-阳性 |
| 12 | PANICFLAG | 危急标志 | string | 0-非危急值项目 1-危急值项目 |
| 13 | LOWLIMIT | 下限 | string | 下限，如：12.3 |
| 14 | UPPLIMIT | 上限 | string | 上限，如：23.00 |
| 15 | APPLYTIME | 申请时间 | string | 格式：yyyy-MM-dd HH:mm:ss |
| 16 | RECEIVETIME | 送检时间 | string | 格式：yyyy-MM-dd HH:mm:ss |
| 17 | REPORTTIME | 报告时间 | string | 格式：yyyy-MM-dd HH:mm:ss |
| 18 | SAMPLETIME | 采样时间 | string | 格式：yyyy-MM-dd HH:mm:ss |
| 19 | EXAMCODE | 分类代码 | string | 检验分类代码 |
| 20 | EXAMNAME | 分类名称 | string | 检验分类名称，如：生化、免疫 |
| 21 | TIMES | 次数 | string | 按项目来获取不同的次数 |



后台返回示例：



JSON返回示例：

"labreportresults": [

{

    "APPLYNO":"15552854",

    "EXECTIME":"2015-01-08",

    "SAMPLECODE":"1",

    "SAMPLENAME":"血",

    "ITEMCODE":"ALT",

    "ITEMNAME":"谷丙转氨酶(ALT)",

    "RESULTTYPE":"1",

    "RESULT":"55.3",

    "REFERENCERANGE":"<65",

    "UNIT":"U/L",

    "HIGHLOWFLAG":" ",

    "ISPANIC":"0",

    "LOWERLIMIT":"",

    "UPPERLIMIT":"",

    "APPLYTIME":"2015-01-07 23:48:19",

    "RECEIVETIME":"2015-01-08 00:02:18",

    "REPORTTIME":"2015-01-08 00:11:24",

    "SAMPLETIME":"2015-01-07 23:51:33",

    "EXAMCODE":"1",

    "EXAMNAME":"生化"

},

{

    "APPLYNO":"15552854",

    "EXECTIME":"2015-01-08",

    "SAMPLECODE":"1",

    "SAMPLENAME":"血",

    "ITEMCODE":"AMYL",

    "ITEMNAME":"淀粉酶",

    "RESULTTYPE":"1",

    "RESULT":"120.6",

    "REFERENCERANGE":"0.0～220.0",

    "UNIT":"U/L",

    "HIGHLOWFLAG":" ",

    "ISPANIC":"0",

    "LOWERLIMIT":"0.0",

    "UPPERLIMIT":"220.0",

    "APPLYTIME":"2015-01-07 23:48:19",

    "RECEIVETIME":"2015-01-08 00:02:18",

    "REPORTTIME":"2015-01-08 00:11:24",

    "SAMPLETIME":"2015-01-07 23:51:33",

    "EXAMCODE":"1",

    "EXAMNAME":"生化"

}

]

代码实现：

表结构：LIS_LIST\LIS_RESULT

程序实现：

报告必须为已审核或发布的报告

姓名不能为空，为空时返回为null

通过病人匹配方式去判断使用什么字段来关联

表结构关系：

LIS_LIST.APPLYNO = LIS_RESULT.APPLYNO

#### 获取患者生物危害信息

接口说明：获取指定患者的生物危害标志

请求URL：../patview/request/getpatsensitiveflag

代码文件：winning.lis.patview.service.PatViewServerice

示例URL：http://192.168.14.253:8080/lis/patview/request/getpatsensitiveflag

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 说明 |
| --- | --- | --- | --- | --- |
| 1 | brlx | 病人类型 | string | 0-门诊 1-住院 3、4-体检 |
| 2 | patientid | 病人唯一号 | string | 取患者属性：PATIENTID |
| 3 | cureno | 就诊号 | string | 取患者属性：CURENO |
| 4 | cardno | 磁卡号 | string | 取患者属性：CARDNO |
| 5 | hospno | 病员号\|住院号 | string | 门诊-病员号
住院-住院号
取患者属性：HOSPNO |
| 6 | patname | 患者姓名 | string | 取患者属性：PATNAME |
| 7 | sex | 性别 | string | 取患者属性：SEX |
| 8 | idnum | 身份证号 | string | 取患者属性：IDNUM |



接口出参：Boolean 

代码实现：

判断LIS_LIST.BIOHAZARDFLAG = 1 and STATUS >= 50

根据参数判断：病人匹配方式（封装成公共方法组装SQL）

####  获取患者生物危害指标信息

接口说明：获取指定患者的生物危害指标信息

请求URL：../patview/request/getpatsensitiveresult

代码文件：winning.lis.patview.service.PatViewServerice

示例URL：http://192.168.14.253:8080/lis/patview/request/getpatsensitiveresult

原型参考：



接口入参：

| NO. | 变量名 | 含义 | 类型 | 说明 |
| --- | --- | --- | --- | --- |
| 1 | brlx | 病人类型 | string | 0-门诊 1-住院 3、4-体检 |
| 2 | patientid | 病人唯一号 | string | 取患者属性：PATIENTID |
| 3 | cureno | 就诊号 | string | 取患者属性：CURENO |
| 4 | cardno | 磁卡号 | string | 取患者属性：CARDNO |
| 5 | hospno | 病员号\|住院号 | string | 门诊-病员号
住院-住院号
取患者属性：HOSPNO |
| 6 | patname | 患者姓名 | string | 取患者属性：PATNAME |
| 7 | sex | 性别 | string | 取患者属性：SEX |
| 8 | idnum | 身份证号 | string | 取患者属性：IDNUM |



接口出参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：sensitiveresultlist   类型：array<object> | 节点名称：sensitiveresultlist   类型：array<object> | 节点名称：sensitiveresultlist   类型：array<object> | 节点名称：sensitiveresultlist   类型：array<object> | 节点名称：sensitiveresultlist   类型：array<object> |
| 1 | APPLYNO | 报告单号 | string | 对应的结果的报告单号 |
| 2 | INSTID | 仪器ID | number | 仪器ID，对应LIS_LIST.INSTID |
| 3 | INSTCODE | 仪器编码 | string | 仪器编码 |
| 4 | INSTNAME | 仪器名称 | string | 仪器名称 |
| 2 | EXECTIME | 检测日期 | string | 格式：yyyy-mm-dd 如：2017-06-27 |
| 3 | TECHNO | 样本号 | number | 样本号 |
| 3 | SAMPLECODE | 标本代码 | string | 标本代码 |
| 4 | SAMPLENAME | 标本名称 | string | 标本名称 |
| 5 | ITEMCODE | 项目代码 | string | 项目代码 |
| 6 | ITEMNAME | 项目名称 | string | 项目名称 |
| 7 | RESULTTYPE | 结果类型 | string | 1-数字 2-字符 |
| 8 | RESULT | 结果 | string | 项目结果 |
| 9 | REFERENCERANGE | 参考范围 | string | 如：12.3~23.00 |
| 10 | UNIT | 结果单位 | string | 如：^10E9mg/l |
| 11 | HIGHLOWFLAG | 高低标志 | string | H-偏高 L-偏低 HH-显著偏高 LL-显著偏低 P-阳性 |
| 12 | ISPANIC | 危急标志 | string | 0-非危急值项目 1-危急值项目 |
| 13 | BIOHAZARDFLAG | 传染病标志 | string | 0-非传染病项目 1-传染病项目 |



代码实现：

判断LIS_LIST.BIOHAZARDFLAG = 1 and STATUS >= 50

判断LIS_RESULT.BIOHAZARDFLAG = 1

根据参数判断：病人匹配方式（封装成公共方法组装SQL）

#### 获取报告结果（历史）信息

接口说明：查询报告的结果信息

请求URL：../reportinput/request/getreportresult

代码文件：winning.lis.reportinput.service.ReportInputerverice

示例URL：http://192.168.11.211:15011/lis/reportinput/request/getreportresult?applyno'=''&'hospitalcode'=''



原型参考：





接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | applyno | 申请单号 | string | 申请单号   如：2123232 |
| 2 | hospitalcode | 医疗机构代码 | string | 传入医疗机构代码，允许为空
传入为空时获取当前医疗机构代码 |





接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：reportresultlist  类型：array<object> | 节点名称：reportresultlist  类型：array<object> | 节点名称：reportresultlist  类型：array<object> | 节点名称：reportresultlist  类型：array<object> | 节点名称：reportresultlist  类型：array<object> |
| 1 | ITEMCODE | 项目代码 | string | 项目代码   如：APTT |
| 2 | ITEMNAME | 项目名称 | string | 项目名称  如：活化部分凝血活酶时间 |
| 3 | RESULT | 报告结果 | string | 报告结果   如：37.60 |
| 4 | REFERENCERANGE | 参考区间 | string | 如：22.00～37.00 |
| 6 | HINTINFO | 提示 | string |  |
| 5 | UNIT | 单位 | string | 参考区间单位   如：秒 |
| 6 | HISRESULT1 | 历史结果1 | string | 历史报告结果1(相同检验项目的历史记录) |
| 7 | HISRESULT2 | 历史结果2 | string | 历史报告结果2(相同检验项目的历史记录) |
| 8 | HISRESULT3 | 历史结果3 | string | 历史报告结果3(相同检验项目的历史记录) |
| 9 | HISRESULT4 | 历史结果4 | string | 历史报告结果4(相同检验项目的历史记录) |
| 10 | HISRESULT5 | 历史结果5 | string | 历史报告结果5(相同检验项目的历史记录) |
| 11 | INSTID | 仪器ID | string | 仪器ID  如：10010 |
| 12 | INSTNAME | 仪器名称 | string | 仪器名称  如：AU5800 |
| 13 | HIGHLOWFLAG | 高低标志 | string | P-阳性或异常值 H-偏高 L-偏低 HH-显著偏高 LL-显著偏低 |
| 14 | REDO | 复做标志 | Int, | 复做标记 0-正常 1-复做 |
| 15 | ODRESULT | OD值 | string | OD值 |
| 16 | ODRESULTCHAR | OD值字符表示 | string | OD值字符表示 |
| 17 | CUTOFFVALUE | CUTOFF比值 | string | CUTOFF比值 |
| 18 | CUTOFFVALUECHAR | CUTOFF比值字符表示 | string | CUTOFF比值字符表示 |
| 19 | SCOVALUE | SCO比值 | string | SCO比值 |
| 20 | SCOVALUECHAR | SCO比值字符表示 | string | SCO比值字符表示 |





JSON返回示例：

“reportresultlist”:[

{

'ITEMCODE':'APTT',

'ITEMNAME':'活化部分凝血活酶时间',

'RESULT':'23.00',

'REFERENCERANGE':'22.00～37.00',

‘HINTINFO’:’’,

'UNIT':'秒',

HISRESULT1:'24.00',

HISRESULT2:'22.00',

HISRESULT3:'30.00',

HISRESULT4:'26.00',

HISRESULT5:'34.00',

'INSTID':'10010',

'INSTNAME':'AU5800',

...

},

{

'ITEMCODE':'APTT',

'ITEMNAME':'活化部分凝血活酶时间',

'RESULT':'23.00',

'REFERENCERANGE':'22.00～37.00',

‘HINTINFO’:’’,

'UNIT':'秒',

HISRESULT1:'24.00',

HISRESULT2:'22.00',

HISRESULT3:'30.00',

HISRESULT4:'26.00',

HISRESULT5:'34.00',

'INSTID':'10010',

'INSTNAME':'AU5800',

...

}]



代码实现：

相关表结构：LIS_result\Lis_list、

代码实现：

 1、调用报告管理模块reportInputService.getReportResult()	

#### 记录标本日志

接口说明：标本的一系列的节点动作都要记录到标本日志表中

接口类型：public 供内部服务调用，不提供前端调用

代码文件：winning.lis.sampleview.service.SampleViewService

业务场景：

操作动作包含：条码绑定、标本采样、护士派发、护工签收、检验签收、小组签收、标本入库....

接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | txm | 条形码 | string | 标本条码信息 |
| 2 | lognode | 节点内容 | string | 如：条码入库 |
| 3 | logcontent | 日志内容 | string | 描述日志关键信息
如：报告单号(标本入库):7364289条形码:10012885018P ;入库项目:血常规+C反应蛋白 |
| 4 | logflag | 节点处理标志 | string | -1-处理失败  1-处理成功 |



接口出参【ResposeMessage】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage | ResposeMessage |
| 1 | type | 消息类型 | string | SUCCESS-成功 
ERROR-系统出现异常或错误
FAILT-业务逻辑处理失败 |
| 2 | messageCode | 消息代码 | string | 用于区分消息代码 |
| 3 | message | 消息内容 | string | type=ERROR时则为系统错误消息提示内容
type=FAILT时则为业务提示信息 |
| 4 | link | URL链接 | string | 用于提示用户后可以点击查看详情信息，默认为空 |
| 5 | data | 数据集合 | object | 用于存储错误或逻辑失败的详细信息 |





代码实现：

表结构：LIS_SPECIMENFLOW

调用时机：条码绑定、标本采样、护士派发、护工签收、检验签收、小组签收、标本入库....

#### 查询标本操作日志

接口说明：查询标本的全流程跟踪详细记录信息

请求URL：../sampleview/request/getsampletrace

代码文件：winning.lis.sampleview.service.SampleViewService

示例URL：http://192.168.14.253:8080/lis/sampleview/request/getsampletrace?txm=13012893232&hospitalcode=9999

业务场景：

标本全流程跟踪等与标本相关的均能使用

接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | txm | 条形码 | string | 条形码 |
| 2 | hospitalcode | 机构代码 | string | 标本机构代码
传空表示为默认当前机构代码 |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：loglist  类型：array<object> 列表按时间顺序升序排序 | 节点名称：loglist  类型：array<object> 列表按时间顺序升序排序 | 节点名称：loglist  类型：array<object> 列表按时间顺序升序排序 | 节点名称：loglist  类型：array<object> 列表按时间顺序升序排序 | 节点名称：loglist  类型：array<object> 列表按时间顺序升序排序 |
| 1 | TXM | 条形码 | string | 如：13012893232 |
| 2 | OPERATETIME | 操作时间 | string | 格式：yyyy-MM-dd HH:mm:ss |
| 3 | FLOWNODE | 流程节点 | string | 如：条码入库 |
| 4 | OPERATERESULT | 日志内容 | string | -1 失败
1 成功 |
| 5 | OPERATECODE | 用户ID | string | 操作人员ID |
| 6 | OPERATENAME | 用户名称 | string | 操作人员姓名 |
| 7 | OPERATEPCNAME | 计算机名称 | string | 操作计算机名称 格式：计算机名(ip) |
| 8 | REMARK | 节点描述 | string | 如：报告单号(标本入库):7364289条形码:10012885018P入库项目:血常规 C反应蛋白 |
| 9 | TATVOILATE | 违反TAT规则 | string | -1:预估截止时间 0:正常 1:违反TAT规则 2:在预警时间范围内(警告) 9-违反TAT规则，但不参与统计(延迟取报告一类的)
默认为0 |
| 10 | HOSPITALCODE | 机构代码 | string | 机构代码 |





后台返回示例：



JSON格式示例:

"loglist":[

{

    "HOSPITALCODE":"9999",

    "TXM":"10012934147P",

    "OPERATETIME":"2017-05-04 09:01:44",

    "FLOWNODE":"标本入库",

    "OPERATERESULT":"1",

    "OPERATECODE":"001",

    "OPERATENAME":"史雪东",

    "OPERATEPCNAME":"SXD(127.0.0.1)",

    "REMARK":"报告单号:7364269 ;入库项目:血常规+C反应蛋白"

},

{

    "HOSPITALCODE":"9999",

    "TXM":"10012934171P",

    "OPERATETIME":"2017-05-04 09:03:27",

    "FLOWNODE":"标本入库",

    "OPERATERESULT":"1",

    "OPERATECODE":"001",

    "OPERATENAME":"史雪东",

    "OPERATEPCNAME":"SXD(127.0.0.1)",

    "REMARK":"报告单号:7364270 ;入库项目:血常规+C反应蛋白"

}

]

代码实现：

表结构：LIS_SPECIMENFLOW

调用时机：标本全流程跟踪查看标本详细记录时

排序：按操作时间升序排序

参考SQL：

SELECT * FROM LIS_SPECIMENFLOW

#### 查询患者标本列表

接口说明：查询一段时间内患者的标本信息

请求URL：../sampleview/request/getsamplelist

代码文件：winning.lis.sampleview.service.SampleViewService

示例URL：http://192.168.14.253:8080/lis/sampleview/request/getsamplelist?patientid=13012893232&cureno=920932&cardno=D903993&hospno=0028312&patname=张三&sex=1&hospitalcode=9999&downloaddays=7

业务场景：

标本查询或标本跟踪查询使用

接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | patientid | 病人唯一号 | string | 病人唯一号 |
| 2 | cureno | 就诊号 | string | 就诊号 |
| 3 | cardno | 磁卡号 | string | 磁卡号 |
| 4 | hospno | 病员号 | string | 病员号或住院号 |
| 5 | patname | 姓名 | string | 姓名 |
| 6 | sex | 性别 | string | 性别 1-男 2-女 3-未知 |
| 7 | hospitalcode | 机构代码 | string | 标本机构代码
传空表示为默认当前机构代码 |
| 8 | downloaddays | 下载天数 | string | 下载天数，此参数与下面两个参数混用，可传其中一组 |
| 9 | begtime | 开始时间 | string | 开始时间，格式：yyyy-MM-dd HH:mm:ss |
| 10 | endtime | 结束时间 | string | 开始时间，格式：yyyy-MM-dd HH:mm:ss |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 节点名称：samplelist  类型：array<object> 列表按时间顺序升序排序 | 节点名称：samplelist  类型：array<object> 列表按时间顺序升序排序 | 节点名称：samplelist  类型：array<object> 列表按时间顺序升序排序 | 节点名称：samplelist  类型：array<object> 列表按时间顺序升序排序 | 节点名称：samplelist  类型：array<object> 列表按时间顺序升序排序 |
| 1 | TXM | 条形码 | string | 如：13012893232 |
| 2 | HISORDERNAME | 检测项目集合 | string | 如：肝功能+肾功能+血脂4项 |
| 3 | HISORDERNAME_JC | 项目简称集合 | string | 如：肝功+肾功+血脂4项 |
| 4 | SAMPLECODE | 标本代码 | string | 如：001 |
| 5 | SAMPLENAME | 标本名称 | string | 如：血清 |
| 6 | ENTRUST | 嘱托内容 | string | 如：空腹 |
| 7 | SUMPRICE | 项目总金额 | number | 项目总金额=Sum(XMSL*PRICE) |
| 8 | MJZBZ | 门急诊标志 | string | 0-常规 1-加急 |
| 9 | CXSJ | 绑定时间 | string | 格式：yyyy-MM-dd HH:mm:ss |
| 10 | CXRXM | 绑定人姓名 | string | 绑定人姓名 |
| 11 | CXRDM | 绑定人代码 | string | 绑定人代码 |
| 12 | DRAWDATE | 采样时间 | string | 格式：yyyy-MM-dd HH:mm:ss |
| 13 | DRAWUSERCODE | 采样人代码 | string | 采样人代码 |
| 14 | DRAWUSERNAME | 采样人姓名 | string | 采样人姓名 |
| 15 | HASUPDATEDRAWDATE | 更新采样时间 | string | 0-未更新 1-已更新 |
| 16 | BAREXAMCODE | 条码分组代码 | string | 条码分组代码 |
| 17 | BAREXAMNAME | 条码分组名称 | string | 条码分组名称 |
| 18 | IMAGEURL | 试管图片 | string | 试管图片地址 |
| 19 | IMAGEURL_SMALL | 试管图片 | string | 试管图片地址_小规格；如：orange_small.png |
| 20 | LASTSTATUS | 最新标本状态 | string | 最新的标本状态，如：采样、签收 |
| 21 | LASTSTATUS_JC | 状态检测 | string | 如：采、签、测、审、发 |
| 22 | RETURNDESC | 取报告回执说明 | string | 如：07-27 16:00、2小时取报告 |





JSON格式示例:

"samplelist":[

{

    "TXM":"10012934147P",

    ..............

    "RETURNDESC":"2小时取报告"

},

{

    "TXM":"10012934147P",

    ..............

    "RETURNDESC":"2小时取报告"



}

]

代码实现：

表结构：LIS_ORDERMASTER\LIS_ACCEPTITEMS

过滤：病人匹配方式+LIS_ORDERMASTER.ORDERDATE

排序：按采样时间降序排序

#### 标本全流程跟踪

接口说明：获取标本的状态及全流程跟踪信息

请求URL：../sampleview/request/getsampleinfo

代码文件：winning.lis.sampleview.service.SampleViewService

示例URL：http://192.168.14.253:8080/lis/sampleview/request/getsampleinfo?txm=01000294860P

业务场景：





接口入参：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 1 | txm | 条形码 | string | 病人标本的条形码，如：01000294860P |



接口出参【ResposeMessage.data->array<object>】：

| NO. | 变量名 | 含义 | 类型 | 备注说明 |
| --- | --- | --- | --- | --- |
| 标本信息  节点名称：sampleinfo  类型：object | 标本信息  节点名称：sampleinfo  类型：object | 标本信息  节点名称：sampleinfo  类型：object | 标本信息  节点名称：sampleinfo  类型：object | 标本信息  节点名称：sampleinfo  类型：object |
| 1 | TXM | 条形码 | string | 如：13012893232 |
| 2 | SAMPLENAME | 标本名称 | string | 如：血清 |
| 3 | SAMPLECODE | 标本代码 | string | 如：11 |
| 4 | BRLX | HIS病人类型 | string | 0-门诊 1-住院 3、4-体检 |
| 5 | WARDORREG | LIS病人类型 | string | LIS病人类型代码 |
| 6 | WARDORREGNAME | 病人类别名称 | string | 如：0-门诊 1-住院 3、4-体检 |
| 7 | PATNAME | 病人姓名 | string | 如：张三 |
| 8 | PATIENTID | 病人唯一号 | string | 如：105362 |
| 9 | CURENO | 就诊号 | string | 如：105360 |
| 10 | CARDNO | 磁卡号 | string | 如：06410101222 |
| 11 | HOSPNO | 病员号 | string | 如：526709 |
| 12 | SEXDESC | 性别 | string | 如：男 |
| 13 | AGE | 年龄 | string | 如：30 |
| 14 | AGEUNIT | 年龄单位 | string | 如：岁 |
| 15 | AGE2 | 年龄2 | string | 年龄2 |
| 16 | AGEUNIT2 | 年龄单位2 | string | 年龄单位，如：日 |
| 17 | AGEDESC | 年龄描述 | string | 如：23岁 |
| 18 | IDNUM | 身份证号 | string | 如：232223222222222223 |
| 19 | M_PHONE | 联系电话 | string | 如：12122345678 |
| 20 | WARD | 病区代码 | string | 如：1007 |
| 21 | WARDNAME | 病区名称 | string | 如：外二科病房 |
| 22 | BEDNO | 床号 | string | 床号，如：+10床 |
| 23 | APPLYDEPTCODE | 申请科室代码 | string | 如：1221 |
| 24 | APPLYDEPTNAME | 申请科室名称 | string | 如：儿一科 |
| 25 | CLINICDESC | 临床诊断 | string | 如：消化不良 |
| 26 | ORDERDATE | 预约时间 | string | 如：2011-10-26 10:04:29.000 |
| 27 | MJZBZ | 门急诊标志 | string | 0-常规 1-加急 |
| 28 | TARGETLAB | 检验机构代码 | string | 如：9999 |
| 29 | HOSPITALCODE | 医疗机构代码 | string | 如：9999 |
| 30 | SRCLAB | 送检机构代码 | string | 如：9999 |
| 31 | REPORTNUM | 报告份数 | string | 如：2 |
| 32 | RPTRETURNDESC | 预计取报告 | string | 预计取报告时间或描述
跟随回执单 |
| 标本流程节点，通过流程节点对照
节点名称：nodelist类型：array<object>（条码绑定 --> 发布） | 标本流程节点，通过流程节点对照
节点名称：nodelist类型：array<object>（条码绑定 --> 发布） | 标本流程节点，通过流程节点对照
节点名称：nodelist类型：array<object>（条码绑定 --> 发布） | 标本流程节点，通过流程节点对照
节点名称：nodelist类型：array<object>（条码绑定 --> 发布） | 标本流程节点，通过流程节点对照
节点名称：nodelist类型：array<object>（条码绑定 --> 发布） |
| 1 | TXM | 条形码 | string | 如：13012893232 |
| 2 | FLOWNODE | 流程节点 | string | 流程节点描述
条码绑定、标本采样、标本签收、标本检测、报告审核、报告发布 |
| 3 | FLOWNODEJC | 流程节点简称 | string | 流程节点简称描述
绑定、采样、签收、检测、审核、发布 |
| 4 | OPERATETIME | 操作时间 | string | 如：2017-05-11 17:29:33 |
| 5 | OPERATECODE | 操作人工号 | string | 如：0001 |
| 6 | OPERATENAME | 操作人员姓名 | string | 如：李四 |
| 7 | OPERATEPCNAME | 操作仪器 | string | 如：iPad（192.168.10.4） |
| 8 | TELPHONE | 电话 | string | 如：13888888888 |
| 9 | TIMEDIFF | 流程间隔（分钟） | string | 如：24 |
| 10 | NODETIME | 节点时间点 | string | 如：2017-05-11 17:29:33 |
| 11 | IDEALTIME | 理论时间点 | string | 如：2017-05-11 17:29:33 |
| 12 | TATRULE | TAT规则ID | string | 如：11 |
| 13 | TATRULEDESC | TAT规则描述 | string | 如：血常规（30分钟） |
| 14 | TATVOILATE | 违反TAT规则 | string | -1:预估截止时间 0:正常 1:违反TAT规则 2:在预警时间范围内(警告) 9-违反TAT规则，但不参与统计(延迟取报告一类的)
默认为0 |
| 节点名称：tracelist  类型：array<object> （标本全流程过程跟踪） | 节点名称：tracelist  类型：array<object> （标本全流程过程跟踪） | 节点名称：tracelist  类型：array<object> （标本全流程过程跟踪） | 节点名称：tracelist  类型：array<object> （标本全流程过程跟踪） | 节点名称：tracelist  类型：array<object> （标本全流程过程跟踪） |
| 1 | TXM | 条形码 | string | 如：13012893232 |
| 2 | FLOWNODE | 流程节点 | string | 如：护士派发 |
| 3 | FLOWNODEJC | 流程节点简称 | string | 流程节点简称描述
绑定、采样、签收、检测、审核、发布 |
| 4 | OPERATECODE | 操作人工号 | string | 如：0001 |
| 5 | OPERATENAME | 操作人员姓名 | string | 如：李四 |
| 6 | OPERATETIME | 操作时间 | string | 如：2017-05-11 17:29:33 |
| 7 | OPERATEPCNAME | 操作仪器 | string | 如：SXD(127.0.0.1) |
| 8 | TELPHONE | 电话 | string | 如：13888888888 |
| 9 | REMARK | 备注说明 | string | 如：报告单号:73642 ;入库项目:血常规+C反应蛋白 |
| 10 | TRACEDESC | 跟踪描述 | string |  |
| 11 | TATRULE | TAT规则ID | string | 如：11 |
| 12 | TATRULEDESC | TAT规则描述 | string | 如：血常规（30分钟） |
| 13 | TATVOILATE | 违反TAT规则 | string | -1:预估截止时间 0:正常 1:违反TAT规则 2:在预警时间范围内(警告) 9-违反TAT规则，但不参与统计(延迟取报告一类的)
默认为0 |
| 节点名称：abnormallist  类型：array<object> （标本异常流程） | 节点名称：abnormallist  类型：array<object> （标本异常流程） | 节点名称：abnormallist  类型：array<object> （标本异常流程） | 节点名称：abnormallist  类型：array<object> （标本异常流程） | 节点名称：abnormallist  类型：array<object> （标本异常流程） |
| 1 | TXM | 条形码 | string | 如：13012893232 |
| 2 | OPERATE | 流程节点 | string | 如：撤销签收 |
| 3 | OPERATECODE | 操作人工号 | string | 如：0001 |
| 4 | OPERATENAME | 操作人员姓名 | string | 如：李四 |
| 5 | OPERATETIME | 操作时间 | string | 如：2017-05-11 17:29:30 |
| 6 | OPERATEPCNAME | 操作仪器 | string | 如：SXD(127.0.0.1) |
| 7 | REASONTYPE | 原因分类 | string | 如：标本不合格 |
| 8 | REASON | 异常原因 | string | 如：标本量偏少 |
| 9 | DISPOSECONTENT | 建议处理内容 | string | 如：标本溶血，需要重新采样 |
| 10 | PRINTRECEIPT | 打印回执 | string | 0-未打回执 1-打印 |
| 11 | EXAMGROUPCODE | 工作组代码 | string | 如：shyz |
| 12 | EXAMGROUPNAME | 工作组名称 | string | 如：生化一组 |
| 13 | ANSWERCODE | 接听人代码 | string | 如：1209 |
| 14 | ANSWERNAME | 接听人姓名 | string | 如：李四 |
| 15 | ANSWERTIME | 通话时间 | string | 格式：yyyy-MM-dd HH:mm:ss |
| 16 | ANSWERPHONE | 电话号码 | string | 电话 |
| 17 | REMARK | 备注说明 | string | 如：标本溶血，需要重新采样 |
| 18 | HOSPITALCODE | 机构代码 | string | 如：430001231 |
| 19 | HOSPITALNAME | 机构名称 | string | 如：上海市第六人民医院 |
| 节点名称：orderlist  类型：array<object> （标本项目信息） | 节点名称：orderlist  类型：array<object> （标本项目信息） | 节点名称：orderlist  类型：array<object> （标本项目信息） | 节点名称：orderlist  类型：array<object> （标本项目信息） | 节点名称：orderlist  类型：array<object> （标本项目信息） |
| 80 | LISORDERCODE | LIS代码 | string | 如：0195 |
| 81 | HISORDERCODE | 医嘱项目代号 | string | 如：195 |
| 82 | HISORDERNAME | 医嘱项目名称 | string | 如：生化全套 |
| 90 | EXECDEPTCODE | 执行科室代码 | string | 如：2110 |
| 91 | EXECDEPTNAME | 执行科室名称 | string | 如：医学检验科 |
| 84 | APPLYDOCCODE | 申请医生代码 | string | 如：1111 |
| 83 | APPLYDOCNAME | 申请医生姓名 | string | 如：李二 |
| 84 | APPLYDEPTCODE | 申请科室代码 | string | 如：1111 |
| 83 | APPLYDEPTNAME | 申请科室姓名 | string | 如：李二 |
| 85 | ITEMTYPE | 项目类别 | string | 0：临床项目 1：收费项目 |
| 86 | SUMPRICE | 汇总价格 | string | 如：194.0000 |
| 87 | PRICE | 价格 | string | 如：194.0000 |
| 88 | XMSL | 数量 | string | 如：1.00 |
| 89 | ENTRUST | 医嘱 | string | 如：空腹 |





