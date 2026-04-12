![logo](media/image1.png){width="2.2916666666666665in" height="0.5631944444444444in"} **保密等级（勾选）：**

  ---------- -------
  **公开**   
  **受控**   **√**
  **秘密**   
  ---------- -------

**卫宁健康科技集团股份有限公司**

**文档修订历史**

  -------- ---------- ---------- ------------ --------
  版本号   修订类别   简要说明   日期         变更人
  1.0      C          创建       2020-02-03   史雪东
  1.1      M          修改       2020-02-14   史雪东
  1.2      M          修改       2020-02-28   史雪东
  1.3      M          修改       2020-03-16   史雪东
  2.0      M          修改       2020-03-27   史雪东
  -------- ---------- ---------- ------------ --------

**修订类别：C = 创建，A = 增加，M = 修改，D = 删除**

**目录**

1文件描述 3

[2原型设计 3](\l)

[3界面原型设计 5](\l)

[3.1部门管理 5](\l)

[3.2 用户维护 6](\l)

[3.3 菜单管理 6](\l)

[3.4角色管理 7](\l)

[3.5权限管理 7](\l)

[3.5系统参数 8](\l)

[3.6机构管理 8](\l)

[3.7检验项目 9](\l)

[3.8仪器配置 9](\l)

[3.9收费项目 10](\l)

[3.10诊断字典 10](\l)

[3.11辅助字典 11](\l)

[3.12报告管理 11](\l)

[3.13标本签收 12](\l)

[3.14标本入库 13](\l)

[3.15报告查询 13](\l)

[3.16通讯测试 14](\l)

[3.17查询统计 15](\l)

[3.18门诊采集 15](\l)

[3.19门诊服务台 16](\l)

[3.20检验申请 16](\l)

[3.21护工签收 17](\l)

[3.22标本查询 17](\l)

[3.23体检采集 18](\l)

[3.24住院采集 18](\l)

[3.25采集时间更新 19](\l)

[3.26病区集中打印 19](\l)

[4数据库设计 20](\l)

文件描述
========

  ---------- -------- ---------- ------------ -------- ---------- ------ ------------
  项目经理   周洪涛   系统设计   曹志强等                                
  审核者     周洪涛   日期       2020-02-03   批准者   专家小组   日期   2020-02-03
  ---------- -------- ---------- ------------ -------- ---------- ------ ------------

原型设计
========

  ---------- ----------------------- ------------------- ---------------------------------------------------------------------------------------------------
             **原 型 附 加 说 明**   **模块**            **存放路径**
  系统配置   部门管理                deptmaintain        \$/合肥研发中心/040 LIS/03 LIS6.0/02 源代码/01 前端源代码/src/modules/deptmaintain
             用户维护                usermaintain        \$/合肥研发中心/040 LIS/03 LIS6.0/02 源代码/01 前端源代码/src/modules/usermaintain
             菜单管理                menumaintain        \$/合肥研发中心/040 LIS/03 LIS6.0/02 源代码/01 前端源代码/src/modules/menumaintain
             角色管理                rolemanagement      \$/合肥研发中心/040 LIS/03 LIS6.0/02 源代码/01 前端源代码/src/modules/userrightsetting/components
             权限管理                userrightsetting    \$/合肥研发中心/040 LIS/03 LIS6.0/02 源代码/01 前端源代码/src/modules/userrightsetting
             系统参数                sysparmsetting      \$/合肥研发中心/040 LIS/03 LIS6.0/02 源代码/01 前端源代码/src/modules/sysparmsetting
             机构管理                organkeep           \$/合肥研发中心/040 LIS/03 LIS6.0/02 源代码/01 前端源代码/src/modules/organkeep
  常规配置   检验项目                item                \$/合肥研发中心/040 LIS/03 LIS6.0/02 源代码/01 前端源代码/src/modules/item
             仪器配置                instrument          \$/合肥研发中心/040 LIS/03 LIS6.0/02 源代码/01 前端源代码/src/modules/instrument
             收费项目                hisitem             \$/合肥研发中心/040 LIS/03 LIS6.0/02 源代码/01 前端源代码/src/modules/hisitem
             诊断字典                clinicaldiagnose    \$/合肥研发中心/040 LIS/03 LIS6.0/02 源代码/01 前端源代码/src/modules/clinicaldiagnose
             辅助字典                slave               \$/合肥研发中心/040 LIS/03 LIS6.0/02 源代码/01 前端源代码/src/modules/slave
             报告管理                reportinput         \$/合肥研发中心/040 LIS/03 LIS6.0/02 源代码/01 前端源代码/src/modules/reportinput
             标本签收                sampleReg           \$/合肥研发中心/040 LIS/03 LIS6.0/02 源代码/01 前端源代码/src/modules/sampleReg
             标本入库                samplingInput       \$/合肥研发中心/040 LIS/03 LIS6.0/02 源代码/01 前端源代码/src/modules/samplingInput
             报告查询                reportquery         \$/合肥研发中心/040 LIS/03 LIS6.0/02 源代码/01 前端源代码/src/modules/reportquery
             通讯测试                instcommunication   \$/合肥研发中心/040 LIS/03 LIS6.0/02 源代码/01 前端源代码/src/modules/instcommunication
             查询统计                querystatistics     \$/合肥研发中心/040 LIS/03 LIS6.0/02 源代码/01 前端源代码/src/modules/querystatistics
  标本采集   门诊采集                sampling            \$/合肥研发中心/040 LIS/03 LIS6.0/02 源代码/01 前端源代码/src/modules/sampling
             门诊服务台              outpatientservice   \$/合肥研发中心/040 LIS/03 LIS6.0/02 源代码/01 前端源代码/src/modules/outpatientservice
             检验申请                sampleapply         \$/合肥研发中心/040 LIS/03 LIS6.0/02 源代码/01 前端源代码/src/modules/sampleapply
             护工签收                sampleworkerreg     \$/合肥研发中心/040 LIS/03 LIS6.0/02 源代码/01 前端源代码/src/modules/sampleworkerreg
             标本查询                samplequery         \$/合肥研发中心/040 LIS/03 LIS6.0/02 源代码/01 前端源代码/src/modules/samplequery
             体检采集                sampling            \$/合肥研发中心/040 LIS/03 LIS6.0/02 源代码/01 前端源代码/src/modules/sampling
             住院采集                zysampling          \$/合肥研发中心/040 LIS/03 LIS6.0/02 源代码/01 前端源代码/src/modules/zysampling
             采集时间更新            updatesampletime    \$/合肥研发中心/040 LIS/03 LIS6.0/02 源代码/01 前端源代码/src/modules/updatesampletime
             病区集中打印            patientareaprint    \$/合肥研发中心/040 LIS/03 LIS6.0/02 源代码/01 前端源代码/src/modules/patientareaprint
  ---------- ----------------------- ------------------- ---------------------------------------------------------------------------------------------------

业务流程
========

界面原型设计
============

 公共及框架
----------

### 部门管理

（1）功能说明

对科室信息进行新增、修改、删除等

(2)原型界面

![](media/image3.png){width="6.497222222222222in" height="2.9479166666666665in"}

### 用户维护

（1）功能说明

对医技科室及HIS的员工信息进行维护，可进行新增、修改、删除等操作。

(2)原型界面

![](media/image4.png){width="6.496527777777778in" height="2.96875in"}

### 菜单管理

（1）功能说明

对系统全部菜单、子菜单进行操作，可以方便的增加菜单，删除菜单及控制每个菜单的内部权限等。

(2)原型界面

![](media/image5.png){width="6.488194444444445in" height="2.9743055555555555in"}

### 角色管理

（1）功能说明

为系统增加不同的权限组。根据用户职位（如操作员、管理员）的不同设置不同的权限组，方便用户的权限的管理。

(2)原型界面

![](media/image6.png){width="6.228472222222222in" height="2.638888888888889in"}

### 权限管理

（1）功能说明

为用户分配有不同权限的角色，方便用户的权限管理。

(2)原型界面

![](media/image7.png){width="6.4847222222222225in" height="2.995138888888889in"}

### 系统参数

（1）功能说明

设置当前系统的参数。

(2)原型界面

![](media/image8.png){width="6.488194444444445in" height="2.986111111111111in"}

### 机构管理

（1）功能说明

**该功能主要用于**对医疗机构的级别及机构信息进行定义，支持新增、修改、删除等。

(2)原型界面

![](media/image9.png){width="6.490277777777778in" height="2.7881944444444446in"}

条码流程管理
------------

### 门急诊条码管理

1.  功能说明

通过与HIS系统对接，完成申请信息执行、条码与申请信息的绑定、申请信息执行确认、收费确认等操作，同时门急诊标本采集系统集成采样时间更新、标本查询、检验360、检验申请功能，方便护士操作。

2.  原型界面

    ![](media/image10.png){width="6.222916666666666in" height="2.68125in"}

### 住院条码管理

(1) 功能说明

> 患者信息按照病区进行分类，方便护士对患者和病区的管理。系统支持与HIS系统对接，完成申请信息执行、条码与申请信息的绑定、申请信息执行确认等操作。同时住院标本采集系统集成采样时间更新、标本查询、检验360、检验申请功能，方便护士操作。

(2) 原型界面

    ![](media/image11.png){width="6.232638888888889in" height="2.6902777777777778in"}

### 体检条码管理

(1) 功能说明

> 通过与HIS系统对接，完成申请信息执行、条码与申请信息的绑定、申请信息执行确认、收费确认等操作，同时门急诊标本采集系统集成采样时间更新、标本查询、检验360、检验申请功能，方便护士操作。

(2) 原型界面

![](media/image10.png){width="6.222916666666666in" height="2.68125in"}

### 检验设备条码双工通讯

1.  功能说明

> 自动接收仪器检验结果，还能向仪器发送检验项目，以便仪器按指定项目检验样本；系统能与多种双向仪器通讯；系统可以在没有条码情况下，按试管架和试管位置进行双向通讯；系统可实现条码标本直接上仪器试管架，自动核收的双向通讯。

2.  原型界面

    ![](media/image12.png){width="6.491666666666666in" height="2.995138888888889in"}

### 标本查询

(1) 功能说明

查询条码信息并对条码进行补打、撤消等操作。

(2) 原型界面

    ![](media/image13.png){width="6.228472222222222in" height="2.5875in"}

常规检验管理
------------

### 常规设备联机

（1）功能说明

系统从检验仪器自动接收检验结果；包括对常规、急诊、质控数据接收；对数据转换、偏移、计算等处理，以及常规转质控的自动处理；同时支持与生化、血常规、电解质、尿常规、血凝、血流变的检验设备对接。

（2）界面原型

![](media/image12.png){width="6.491666666666666in" height="2.995138888888889in"}

### 标本登记及收费

（1）功能说明

检验科医生对门急诊、住院、体检等采集部门运送过来的标本进行登记签收并确费。实验室对标本接收类型包含：条码标本接收登记、微生物标本接收、外来标本接收登记、手工单标本接收登记；实验室对标本进行集中、小组核收、标本的让步接收、不合格标本（损毁、凝集、采集量少）拒收，记录不合格原因并通知护士进行处理，对需要补充检验申请的添加检验申请并计费，记录签收人、签收时间、生成签收号；支持手工计费及二次计费，支持检验费用核对功能，提供免费检验、绿色通道管理功能；对标本重复、漏检、送检超时、送检地错误等问题进行智能提醒。

（2）界面原型

![1599115665(1)](media/image14.png){width="5.714583333333334in" height="2.638888888888889in"}

### 检验结果处理模块

（1）功能说明

> 检验标本签收后，检验科入库老师进行检验入库并上机检验，包含自动下载病人信息、执行医嘱、多种采集入库方式、计算项目、常规检验、参考值分析判断、复做标本管理、复做结果管理、报告管理、审核条件界面、筛选等，同时在报告管理界面集成跳转终审、标本签收、标本入库、批量操作、报告复制、报告查询、检验360功能，通过功能集成提高检验科医生工作效率。

（2）界面原型

![1600933253(1)](media/image15.png){width="5.573611111111111in" height="2.560416666666667in"}

### 检验报告发布回收

（1）功能说明

对已审核的报告可发布到临床，供临床医生查看。对于有问题的报告，系统可进行回收，回收后临床无法进行调阅。再次发布后才能进行调阅。

（2）原型界面

![](media/image16.png){width="6.249305555555556in" height="2.921527777777778in"}

![](media/image17.png){width="6.2659722222222225in" height="2.925in"}

### 检验报告临床调阅

1.  功能说明

临床可调阅的内容包含检验结果、报告单、化验报告，与HIS医嘱匹配或按实验室完整报告方式调阅；提供扩展调阅历史报告功能。

（2）原型界面

![](media/image18.png){width="5.666666666666667in" height="3.7263888888888888in"}

### 标本全流程跟踪管理

1.  功能说明

在报告管理界面提供报告详情，报告医生可对报告全流程查看，点击"报告详情"可以一目了然在同一界面查询报告的详情信息，包括全流程明细、报告汇总、检验项目、修改记录-报告、修改记录-结果、危急值项目、危急值流程、标本全流程。

2.  原型界面

    ![1599115948(1)](media/image19.png){width="5.643055555555556in" height="2.95in"}

### 检验报告360视图管理

1.  功能说明

在报告审核时，为报告医生提供检验360视图，该视图在同一界面可视化的显示报告视图、样本全景及检验报告。包含该病人的全部历史报告以及可按检验分类查看检验信息、按仪器或者检验分类等多种检索条件查看该患者的历史报告。

2.  原型界面

    ![1600932703(1)](media/image20.png){width="5.6819444444444445in" height="2.410416666666667in"}

### 查询及统计管理

（1）功能说明

提供记录查询系列模块，包含系统的病人记录查询模块，可以提供简单、快捷地查询病人记录的功能；可以使用多种查询方式，在任意时间范围，迅速查询病人历次的检验结果记录。

（2）原型界面

![1599114398(1)](media/image21.png){width="5.6722222222222225in" height="2.9652777777777777in"}

### 报告查询

(1) 功能说明

    **按照设置的查询条件查询病人信息，包括病人类别、报告状态、打印状态、检验类别及检验项目等信息，可对查询出的病人信息进打印、预览、批量打印、查看报告、仅查看异常指标、查看实验室建议等操作**。

(2)原型界面

![](media/image22.png){width="6.5in" height="2.9722222222222223in"}

### 标本入库

1.  功能说明

    已签收的条码进行统一排号、入库等操作，也可对已入库的条码进行撤消入库、汇总打印等

(2)原型界面

![](media/image23.png){width="6.497222222222222in" height="2.988888888888889in"}

自动化室内质控管理
------------------

### 质控图

（1）功能说明

质控图分为9种：Z-分数图、L-J图、柱状图、尤顿图、±质控图、定性质控图、累计和图、双区法质控图、滴度质控图。质控图绘制可按月按天描绘，不同月份的质控点可绘制在同一图上进行对比。

（2）界面原型

![1598942509(1)](media/image24.png){width="5.517361111111111in" height="2.7083333333333335in"}

**Z-分数图**

![](media/image25.png){width="5.6in" height="3.2305555555555556in"}

**L-J图**

![1598942567(1)](media/image26.png){width="5.510416666666667in" height="2.689583333333333in"}

**柱状图**

![1598942538(1)](media/image27.png){width="5.583333333333333in" height="2.792361111111111in"}

**尤顿图**

![](media/image28.png){width="5.665277777777778in" height="3.2305555555555556in"}

**±质控图**

![](media/image29.png){width="5.742361111111111in" height="3.2305555555555556in"}

**定性质控图**

![](media/image30.png){width="5.654861111111111in" height="3.23125in"}

**累计和图**

![1600917852(1)](media/image31.png){width="5.6194444444444445in" height="2.6465277777777776in"}

**双区法质控**

![](media/image32.png){width="5.6715277777777775in" height="3.1597222222222223in"}

**滴度质控图**

### 质控规则及组合

1.  功能说明

提供了11大类常用的质控规则，标准差倍数规则、极差规则、趋势规则、平均数控制规则、比例控制规则、±半定量规则、数字半定量规则、定性控制规则、累积和控制、滴度半定量和经典多规则组合，如WESTGARD质控规则。

（2）界面原型

![](media/image33.png){width="6.256944444444445in" height="2.904166666666667in"}

### 靶值配置

（1）功能说明

可设置低水平、中水平、高水平的靶值，针对靶值设置周期突破老质控系统中靶值必须每个月设置一次的限制，可以在质控品有效期内自由设置靶值的有效期限。

2.  界面原型

    ![](media/image34.png){width="5.763888888888889in" height="2.8131944444444446in"}

检验危急值提醒
--------------

1.  功能说明

出现危急值时系统自动给出报警提示（文字提示、声音提示），不局限于当前样本号的提示。

（2）界面原型

![](media/image35.png){width="6.267361111111111in" height="3.922222222222222in"}

危急值临床推送
--------------

1.  功能说明

提供发布危急值通知到临床工作站的功能，同时记录通知人、被通知人、发生时间等信息。

2.  界面原型

    ![](media/image36.png){width="5.128472222222222in" height="2.9916666666666667in"}

标本状态提醒
------------

1.  功能说明

检验科医生护士通过大屏幕监控或工作站消息窗口报警和提醒对检验全过程中的异常情况加以关注。系统提供急诊标本提醒，不合格标本提醒，危急标本提醒，实验室过程监控、异常标本监控、标本流转监控。

2.  界面原型

    ![](media/image37.png){width="6.2652777777777775in" height="3.3520833333333333in"}

自动审核管理
------------

1.  功能说明

    系统根据设置审核条件进行有效需要人工审核的报告，减少审核人员的功能量，缩短报告时间。自动审核包含五种审核条件：异常值验证、历史差值验证、结果区间验证、自定义逻辑验证，参考值验证（参考值根据性别、标本种类和年龄不同而不同，年龄支持岁、月、周、天、小时形式；处理特殊生理指征的参考值）。

2.  界面原型

    ![](media/image38.png){width="6.25625in" height="3.6118055555555557in"}

微生物检验
----------

### 报告管理

（1）功能说明

实现对样本的涂片、培养、鉴定及药敏的独立报告以及综合报告，同时可查看历史回顾及反查功能，对手工输入抑菌圈进行耐药判断，及不合理结果进行审核，传染病自动提醒，并上报临床，罕见菌种的提醒功能，并于保存菌株，涂片、接种、培养拍照功能。

（2）界面原型

![1621585202(1)](media/image39.png){width="6.479861111111111in" height="3.1625in"}

### 危急值管理

1.  功能说明

提供发布危急值通知到临床工作站的功能，同时记录通知人、被通知人、发生时间等信息。实时通知临床，并可记录临床处理及临床意见，支持临床反馈机制；提供危急值报告的流程管理，实现危急值全过程管理流程闭环。

（2）界面原型

![](media/image40.png){width="6.263888888888889in" height="3.092361111111111in"}

### 检测方案管理

1.  功能说明

代替传统手工选择培养路径、检测方法、预计报告时间，标签打印；系统按照医嘱和标本种类（血标本、痰标本、尿标本等）实现自动检测鉴定路径，最终生成培养记录。通过生成的培养记录，医生只需点击"下一步"即可完成所有关键路径的操作。

2.  界面原型

    ![1620980310(1)](media/image41.png){width="6.479861111111111in" height="3.1625in"}

### whonet管理

1.  功能说明

可与WHONET的 5.6等主流版本的数据对接，在药敏报告完成后，可将病人信息、细菌信息、药敏信息自动导入WHONET中进行统计分析。系统中只需要在抗生素药物和细菌种类模块配置符合WHONET规则的WHONET代码。然后在WHONET模块，通过条件查询出需要导入WHONET软件的数据，点击导入即可。打开WHONET软件后，就能浏览到我们导入的数据，并且进行数据分析。

2.  界面原型

    ![b71d073cf8d3b5d38381a869d90f88c](media/image42.png){width="6.25625in" height="3.51875in"}

### 常规药敏统计/分析

1.  功能说明

查询统计及自定义查询方案提供强大的数据统计分析功能和报告查询方案自定义功能，方便实验室进行统计分析工作。支持院感分析，药敏百分比以及超级细菌统计等功能。并且高级查询的模式，可以任意的去组合查询条件。可以保存查询分析方案，下次使用查询的时候就不需要在去选择查询分析条件，为形成统计分析知识库。

2.  界面原型

    ![d7120df7756b4845a2f82c063cc79f1](media/image43.png){width="6.25625in" height="3.51875in"}

### 实验流程电子化管理

1.  功能说明

    实现微生物全流程的三级报告的电子化管理。

2.  界面原型

![1621585409(1)](media/image44.png){width="6.479861111111111in" height="3.1625in"}

**培养**

![1621585553(1)](media/image45.png){width="6.479861111111111in" height="3.1625in"}

**初鉴**

![1621585615(1)](media/image46.png){width="6.479861111111111in" height="3.1625in"}

**药敏**

### 微生物质控

1.  功能说明

可满足手工药敏质控、仪器药敏质控、手工生化反应质控、仪器生化反应质控、染色液质控。

2.  界面原型

    ![](media/image47.png){width="6.250694444444444in" height="2.875in"}

科室管理
--------

### 实验室质控指标分析（28项）

1.  功能说明

主要功能包括临床实验室概况、检验前质量指标项、检验中质量指标项、检验后质量指标项以及支持过程质量指标项五个部分的BI数据展示。

系统首页展示实验室概况，集中展示实验室管理者比较关注的关键指标，数据支持下钻和多级下钻、图表下钻等，支持折线图、柱状图、饼图、仪表盘、列表、横向柱状图、卡片、地图、雷达图、流向图等展现形式。支持图表或列表数据的导出功能。

2.  界面原型

    ![图片包含 屏幕截图, 监视器 描述已自动生成](media/image48.png){width="5.229166666666667in" height="3.526388888888889in"}

### 检验科数据BI分析

1.  功能说明

    多模态的数据动态展示，包含标本签收量、危急值报告数、报告发布量、门诊化验费、住院化验费、当日标签标本签收量、患者次数、当日门诊标本采集量、报告发布数量、危急值报告。数据可以以Excel文件导出；数据能够按照业务场景、业务类型进行不同进行下钻展示，具体展示深度根据医院需求进行调整；同一类型数据，不同时期数据可在同一展示框内显示。统计数据可按柱状图、饼状图、折线图、卫星图等进行展示。

2.  界面原型

    ![图片包含 屏幕截图, 户外, 监视器, 道路 描述已自动生成](media/image49.jpeg){width="5.770833333333333in" height="3.2493055555555554in"}

### 检验科驾驶舱监控

1.  功能说明

通过在同一界面展示实时的数据管理（危急值监控、TAT报警监控、不合格标本数、室内质控失控、门诊抽血人流趋势图、工作组报告分布），加上数据分析组件，可形成对于检验科的全流程的实时监控平台，主任医生在办公室就可调控科室资源。通过大量的数据分析，可形成对于科室资源的调度统一管理。

（2）界面原型

![图片包含 监视器, 黑色 描述已自动生成](media/image50.png){width="5.438194444444444in" height="3.0590277777777777in"}

### 试剂耗材管理

（1）功能说明

试剂管理系统支持两种管理模式：精细化标签管理，粗放化流程管理。精细化标签管理支持试剂入库时以最小拆分单位为基准生成试剂条码，贯穿整个试剂的生命周期。支持试剂多级库存管理，如：检验科统一库存一级库存，各检验小组领取管理的二级库存，并支持各级库存的有效期、库存及盘存预警功能。

2.  界面原型

    入库单

    ![库房管理-入库单](media/image51.png){width="6.259722222222222in" height="3.5215277777777776in"}

    出库单

    ![库房管理-出库单](media/image52.png){width="6.259722222222222in" height="3.5215277777777776in"}

    采购单

    ![库房管理-订购单](media/image53.png){width="6.259722222222222in" height="3.5215277777777776in"}

    申领单

    ![库房管理-申领单](media/image54.png){width="6.259722222222222in" height="3.5215277777777776in"}

    试剂使用

    ![库房管理-试剂使用](media/image55.png){width="6.259722222222222in" height="3.5215277777777776in"}

    试剂基本信息

    ![字典信息-试剂信息配置](media/image56.png){width="6.259722222222222in" height="3.5215277777777776in"}

    库房管理

    ![字典信息-库房信息维护](media/image57.png){width="6.259722222222222in" height="3.5215277777777776in"}

### 人员管理

1.  功能说明

管理科室人员的各项信息及档案信息，包括授权、考核、奖惩、科研、学习、进修、教学、健康等。

（2）界面原型

![](media/image58.png){width="6.260416666666667in" height="2.9472222222222224in"}

### 设备管理

1.  功能说明

    对检验科内的设备信息进行管理，包括档案、组件、培训、保养、授权、验收、校验、调动、使用、校准等信息

![图片包含 屏幕截图 描述已自动生成](media/image59.png){width="4.927083333333333in" height="3.095138888888889in"}

**设备管理**

![图片包含 监视器 描述已自动生成](media/image60.png){width="5.040972222222222in" height="3.095138888888889in"}

### 科室文档管理

1.  功能说明

科室文档管理用于管理各类电子文档，进行集中分类存储、浏览、导出管理；

主要分为论文管理、实验室文件、个人文件、SOP文件、文件目录，其中所有文件集中存储在文件存储表中，文件存储表对应到文件目录表中相应的目录。

2.  界面原型

    ![图片包含 监视器 描述已自动生成](media/image61.png){width="5.466666666666667in" height="2.8465277777777778in"}

危急值流转
----------

1.  功能说明

    检查检验科室确认存在危急值时，危急值信息通过医技消息平台中危急值管理系统将信息发送到医生工作站或护士工作站，进行危急值接收和确认，并将确认信息通过医技消息平台反馈至科室，实现科室和临床的互动，确保危急值信息的正确性提高患者诊治的效率，实现危急值闭环，并且发布的危急值报告可通过移动端（短信、微信等）通知临床。

2.  界面原型

    ![](media/image62.png){width="5.821527777777778in" height="3.842361111111111in"}

    ![](media/image63.png){width="5.780555555555556in" height="4.335416666666666in"}

    ![](media/image64.png){width="5.745833333333334in" height="4.309722222222222in"}

系统配置
--------

### 检验项目

（1）功能说明

**该模块主要用于维护检验科的检验项目。**

(2)原型界面

![](media/image65.png){width="6.49375in" height="3.017361111111111in"}

### 仪器配置

（1）功能说明

维护仪器设备、仪器对应的检验项目各项条件（包括特殊参考、常规参考、计算公式、转义等）、审核条件、组合项目、属性等信息。

(2)原型界面

![](media/image12.png){width="6.491666666666666in" height="2.995138888888889in"}

### 收费项目

（1）功能说明

配置收费项目对应的仪器、条码分组、回执单规则、TAT规则、材料费、标本种类等信息。

(2)原型界面

![](media/image66.png){width="6.498611111111111in" height="2.984722222222222in"}

### 辅助字典

（1）功能说明

**该模块主要用于系统中下拉框内容和系统的初始化数据的维护。**

(2)原型界面

![](media/image67.png){width="6.4847222222222225in" height="3.0in"}

数据库设计
==========

采集常规系统
------------

### 双工主记录表 LIS\_WORKLISTMASTER 

  -------------- ---------- ------ ------------ ---------------------
  字段名         数据类型   长度   作用         说明
  SERIALNO       numeric    9      顺序号       
  SECTION        varchar    20     盘号         
  CUP            varchar    20     杯号         
  SAMPLEID       varchar    20     样本号       
  TESTTYPE       varchar    20     测试类型     0常规；1急诊；2质控
  SAMPLETYPE     varchar    20     样本类型     
  INSTID         int        4      仪器代号     
  INPUTITEMS     varchar    250    输入项目名   
  EXECUTETIME    datetime   8      执行日期     
  EXECFLAG       char       2      状态标志     0:发送 1：已执行
  RERUNFLAG      char       1      重做标志     
  ORGAPPLYNO     varchar    20     条形码       
  CREATETIME     datetime          创建时间     
  HOSPITALCODE   varchar    50     机构代码     
  -------------- ---------- ------ ------------ ---------------------

### 双工结果表 LIS\_WORKLISTMASTER 

  -------------- ---------- ------ ------------ -------------
  字段名         数据类型   长度   作用         说明
  SERIALNO       numeric    9      顺序号       
  ITEMCODE       varchar    20     项目代号     
  INSTITEMCODE   varchar    20     项目通道号   
  BARCODE        varchar    20     条形码       
  FZBZ           int        4      复做标志     0正常,1复做
  FSBZ           char       1      发送标志     
  DILUTION       int        4      稀释倍数     
  HOSPITALCODE   varchar    50     机构代码     
  -------------- ---------- ------ ------------ -------------

### 病人信息表 LIS\_LIST

  ------------------- ----------- ------ ---------------------------- -------------------------------------------------------------------------------------------------------
  字段名              数据类型    长度   作用                         说明
  HOSPITALCODE        varchar     50     医疗机构代码                 
  APPLYNO             numeric     9      报告单号                     
  EXECTIME            datetime    8      检测日期                     格式：yyyy-MM-DD
  INSTID              int         4      仪器ID                       
  INSTCODE            varchar     20     仪器代码                     
  INSTNAME            varchar     64     仪器名称                     
  SAMPLETYPE          char        2      样本分组                     
  TECHNO              int         4      样本号                       
  CURENO              varchar     64     就诊号                       
  PATIENTID           varchar     64     病人唯一ID                   
  CARDTYPE            varchar     10     卡类型                       
  CARDNO              varchar     64     磁卡号                       
  HOSPNO              varchar     64     住院号或门诊号               
  PATNAME             varchar     64     患者姓名                     
  EMPIID              varchar     32     EMPI主索引ID                 
  IMECODE             varchar     64     拼音码                       
  SEX                 varchar     10     性别                         1-男 2-女 3-未知
  SEXDESC             varchar     10     性别文字描述                 
  BIRTHDAY            datetime    8      出生日期                     
  AGE                 int         4      年龄                         
  AGEUNIT             varchar     6      年龄单位                     
  AGE2                int         4      年龄2                        
  AGEUNIT2            varchar     6      年龄单位2                    
  AGEDESC             varchar     20     年龄描述                     
  INvOICENO           varchar     32     发票号                       
  IDNUM               varchar     20     身份证号                     
  INWARDTIMES         int         4      住院次数                     
  BABYFLAG            int         4      婴儿标志                     0-非婴儿 1-婴儿
  CHARGETYPE          varchar     20     费别代码                     
  CHARGETYPENAME      varchar     64     费别名称                     
  WARDORREG           varchar     20     病人类别代码                 
  WARDORREGNAME       varchar     64     病人类别名称                 
  BRLX                varchar     10     病人类型                     0-门诊 1-住院 3、4-体检
  WARD                varchar     20     病区代码                     
  WARDNAME            varchar     64     病区名称                     
  BEDNO               varchar     20     床号                         
  CLINICDESC          varchar     500    临床诊断                     
  ICD10               varchar     20     ICD10编码                    
  ICDNAME             varchar     255    ICD10名称                    
  M\_PHONE            varchar     32     联系电话                     
  ADDRESS             varchar     255    联系地址                     
  NATION              varchar     64     民族                         
  YEXH                varchar     20     婴儿序号                     
  TIMES               int         4      住院次数                     
  ORGAPPLYNO          varchar     20     条形码                       
  APPLYDEPTCODE       varchar     20     申请科室代码                 
  APPLYDEPTNAME       varchar     64     申请科室名称                 
  APPLYDOCCODE        varchar     20     申请医生代码                 
  APPLYDOCNAME        varchar     64     申请医生姓名                 
  SAMPLECODE          varchar     20     标本种类代码                 
  SAMPLENAME          varchar     64     标本种类名称                 
  SAMPLEDESCCODE      varchar     20     标本说明代码                 
  SAMPLEDESCNAME      varchar     40     标本说明名称                 
  APPLYTIME           datetime    8      申请时间                     
  SAMPLETIME          datetime    8      采样时间                     
  WORKERSIGNTIME      datetime    8      护工签收时间                 
  RECEIVETIME         datetime    8      检验科接收、签收时间         
  GROUPSIGNTIME       datetime    8      小组签收时间                 
  GROUPSECSIGNTIME    datetime    8      小组二级签收时间             
  PATPROPCODE         varchar     20     病人特征代码                 
  PATPROPNAME         varchar     64     病人特征名称                 
  REGISTERCODE        varchar     20     登记医生代码                 
  REGISTERNAME        varchar     64     登记医生姓名                 
  REGISTERTIME        datetime    8      检验开始时间                 
  REGISTERPCNAME      varchar     255    登记计算机名                 
  EXECDEPTCODE        varchar     20     执行科室代码                 
  EXECDEPTNAME        varchar     64     执行科室名称                 
  EXECDOCCODE         varchar     20     检验医生代码                 
  EXECDOCNAME         varchar     64     检验医生姓名                 
  STATUS              int         4      报告状态                     10-初始报告 20-部分指标检测完成 30-已完成检测（含多做） 40-初审通过 45-审核不通过 50-终审通过 60-发布
  NOPASSREASON        varchar     255    审核不通过原因               
  PREVERIFIERCODE     varchar     20     初审医生代码                 
  PREVERIFIERNAME     varchar     64     初审医生姓名                 
  PREVERIFYPCNAME     varchar     255    初审计算机名称               
  PREVERIFYDATE       datetime    8      初审时间                     
  VERIFIERCODE        varchar     20     终审医生代码                 
  VERIFIERNAME        varchar     64     终审医生姓名                 
  VERIFYPCNAME        varchar     255    终审计算机名称               
  VERIFYDATE          datetime    8      终审时间                     
  REPORTTIME          datetime    8      报告时间                     
  PUBCODE             varchar     20     发布人代码                   
  PUBNAME             varchar     64     发布人姓名                   
  PUBDATETIME         datetime    8      报告发布时间                 
  PRINTSTATUS         int         4      报告打印状态                 0-未打印 1-已打印
  PRINTCOUNT          int         4      自助机打印次数               
  PRINTCOMPUTER       varchar     255    最近一次打印的计算机名       
  PRINTBYCODE         varchar     20     最近一次打印人的工号         
  PRINTBYNAME         varchar     64     最近一次打印人的姓名         
  PRINTDATE           datetime    8      最近一次打印报告的时间       
  MJZBZ               int         4      门急诊标志                   0-常规 1-加急
  REDO                int         4      复做标志                     0-未复做 1-复做
  REDOSAMPLE          char        1      标本来源                     0-当前标本 1-重新采样
  REDOSAMPLEDESC      varchar     255    复做标本说明                 
  REDOREASON          varchar     255    复做原因                     
  MOBILESTATUS        int         4      短信或移动通知               0-未通知 1-已通知
  SCOPICFLAG          int         4      镜检复片标志                 1-镜检复片
  PANICFLAG           int         4      危急值报告                   0-正常 1-危急值报告
  BIOHAZARDFLAG       int         4      传染病标志                   0-正常 1-传染病报告
  TATVOILATE          int         4      是否违反TAT规则              0:正常 1:违反TAT规则 9-违反TAT规则
  ESTREPORTTIME       datetime    8      预计出报告时间               
  REPORTONTIME        int         4      是否按时出报告               -1-不做判断 0-未按时出报告 1-按时出报告
  DELAYRPTFLAG        int         4      延时取报告标志               1-申请延迟出报告
  DELAYRPTREASON      varchar     255    延迟取报告原因               
  DELAYRPTTIME        varchar     64     延迟到出报告时间             
  DELAYRPTBYCODE      varchar     20     延迟取报告操作人代码         
  DELAYRPTBYNAME      varchar     64     延迟取报告操作人姓名         
  DELAYRPTDATE        datetime    8      延迟取报告操作人时间         
  EXAMRESULT          varchar     1000   检验印象                     
  REMARK              varchar     1000   检验备注                     
  LABDIAGNOSIS        varchar     1000   实验室诊断最终的建议和解释   
  SCIENTIFICFLAG      int         4      科研标志                     
  SECTION             varchar     20     架号                         
  CUP                 varchar     20     杯号                         
  HISORDERCODE        varchar     500    检测项目代码                 
  HISORDERNAME        varchar     4000   检测项目名称                 
  CHARGEAMOUNT        numeric     5      收费金额                     
  EXAMCODE            varchar     20     检验分类代码                 
  EXAMNAME            varchar     64     检验分类名称                 
  REPORTTITLE         varchar     64     报告单标题                   
  SRCLAB              varchar     50     标本送检机构代码             
  TARGETLAB           varchar     50     检验机构代码                 
  TARGETREPORTNO      varchar     64     检验机构报告单号             
  TIMETEMP            timestamp   8      时间戳                       
  RESULTANALYZE       varchar     1000   结果建议与解释               
  CLINICREMARK        varchar     1000   临床备注                     
  SAMPLESHAPECODE     varchar     20     标本性状代码                 
  SAMPLESHAPENAME     varchar     64     标本性状名称                 
  ARCHIVINGPOSITION   varchar     32     标本归档位置                 
  TESTPOSITION        varchar     50     上机仪器检测位置坐标         
  INSTVERIFYFLAG      char        1      仪器审核标志                 0-无仪器审核 1-仪器审核通过 2-仪器审核不通过
  INSTVERIFYCONTENT   varchar     500    仪器审核说明                 
  AUTORULEFLAG        char        1      自动审核标志                 0-无规则判断 1-自动审核规则判断通过 2-自动审核规则判断不通过
  AUTORULEDESC        varchar     500    自动审核规则说明             
  MANUALRULEFLAG      char        1      手工审核标志                 0-无规则判断 7-手工审核规则判断通过 8-手工审核规则判断不通过 2-表示用户确认审核不通过
  MANUALRULEDESC      varchar     500    手工审核规则说明             
  MANUALCONFIRMCODE   varchar     20     手工审核确认人代码           
  MANUALCONFIRMNAME   varchar     64     手工审核确认人姓名           
  MANUALCONFIRMDATE   datetime    8      手工审核确认时间             
  MANUALCONFIRMPC     varchar     255    手工确认PC                   
  AUTOVERIFYFLAG      char        1      当前报告是否时自动审核通过   
  ANALYZEACCEPT       char        1      系统分析结果采纳标志         
  ------------------- ----------- ------ ---------------------------- -------------------------------------------------------------------------------------------------------

### 病人信息修改记录表 LIS\_LISTMOD

  ------------------- ---------- ------ ---------------------------- ---------------------------------------------------------------------------------------------------------
  字段名              数据类型   长度   作用                         说明
  SERIALNO            numeric    9      流水号                       
  HOSPITALCODE        varchar    50     医疗机构代码                 
  APPLYNO             numeric    9      报告单号                     
  EXECTIME            datetime   8      检测日期                     格式：yyyy-MM-DD
  INSTID              int        4      仪器ID                       
  INSTCODE            varchar    20     仪器代码                     
  INSTNAME            varchar    64     仪器名称                     
  SAMPLETYPE          char       2      样本分组                     
  TECHNO              int        4      样本号                       
  CURENO              varchar    64     就诊号                       
  PATIENTID           varchar    64     病人唯一ID                   
  CARDTYPE            varchar    10     卡类型                       
  CARDNO              varchar    64     磁卡号                       
  HOSPNO              varchar    64     住院号或门诊号               
  PATNAME             varchar    64     患者姓名                     
  EMPIID              varchar    32     EMPI主索引ID                 
  IMECODE             varchar    64     拼音码                       
  SEX                 varchar    10     性别                         1-男 2-女 3-未知
  SEXDESC             varchar    10     性别文字描述                 
  BIRTHDAY            datetime   8      出生日期                     
  AGE                 int        4      年龄                         
  AGEUNIT             varchar    6      年龄单位                     
  AGE2                int        4      年龄2                        
  AGEUNIT2            varchar    6      年龄单位2                    
  AGEDESC             varchar    20     年龄描述                     
  INVOICENO           varchar    32     发票号                       
  IDNUM               varchar    20     身份证号                     
  INWARDTIMES         int        4      住院次数                     
  BABYFLAG            int        4      婴儿标志                     0-非婴儿 1-婴儿
  CHARGETYPE          varchar    20     费别代码                     
  CHARGETYPENAME      varchar    64     费别名称                     
  WARDORREG           varchar    20     病人类别代码                 
  WARDORREGNAME       varchar    64     病人类别名称                 
  BRLX                varchar    10     病人类型                     0-门诊 1-住院 3、4-体检
  WARD                varchar    20     病区代码                     
  WARDNAME            varchar    64     病区名称                     
  BEDNO               varchar    20     床号                         
  CLINICDESC          varchar    500    临床诊断                     
  ICD10               varchar    20     ICD10编码                    
  ICDNAME             varchar    255    ICD10名称                    
  M\_PHONE            varchar    32     联系电话                     
  ADDRESS             varchar    255    联系地址                     
  NATION              varchar    64     民族                         
  YEXH                varchar    20     婴儿序号                     
  TIMES               int        4      住院次数                     
  ORGAPPLYNO          varchar    20     条形码                       
  APPLYDEPTCODE       varchar    20     申请科室代码                 
  APPLYDEPTNAME       varchar    64     申请科室名称                 
  APPLYDOCCODE        varchar    20     申请医生代码                 
  APPLYDOCNAME        varchar    64     申请医生姓名                 
  SAMPLECODE          varchar    20     标本种类代码                 
  SAMPLENAME          varchar    64     标本种类名称                 
  SAMPLEDESCCODE      varchar    20     标本说明代码                 
  SAMPLEDESCNAME      varchar    40     标本说明名称                 
  APPLYTIME           datetime   8      申请时间                     
  SAMPLETIME          datetime   8      采样时间                     
  WORKERSIGNTIME      datetime   8      护工签收时间                 
  RECEIVETIME         datetime   8      检验科接收、签收时间         
  GROUPSIGNTIME       datetime   8      小组签收时间                 
  GROUPSECSIGNTIME    datetime   8      小组二级签收时间             
  PATPROPCODE         varchar    20     病人特征代码                 
  PATPROPNAME         varchar    64     病人特征名称                 
  REGISTERCODE        varchar    20     登记医生代码                 
  REGISTERNAME        varchar    64     登记医生姓名                 
  REGISTERTIME        datetime   8      检验开始时间                 
  REGISTERPCNAME      varchar    255    登记计算机名                 
  EXECDEPTCODE        varchar    20     执行科室代码                 
  EXECDEPTNAME        varchar    64     执行科室名称                 
  EXECDOCCODE         varchar    20     检验医生代码                 
  EXECDOCNAME         varchar    64     检验医生姓名                 
  STATUS              int        4      报告状态                     10-初始报告 20-部分指标检测完成 30-已完成检测（含多做） 40-初审通过 45-审核不通过 50-终审通过 60-发布
  NOPASSREASON        varchar    255    审核不通过原因               
  PREVERIFIERCODE     varchar    20     初审医生代码                 
  PREVERIFIERNAME     varchar    64     初审医生姓名                 
  PREVERIFYDATE       datetime   8      初审时间                     
  VERIFIERCODE        varchar    20     终审医生代码                 
  VERIFIERNAME        varchar    64     终审医生姓名                 
  VERIFYDATE          datetime   8      终审时间                     
  REPORTTIME          datetime   8      报告时间                     
  PUBCODE             varchar    20     发布人代码                   
  PUBNAME             varchar    64     发布人姓名                   
  PUBDATETIME         datetime   8      报告发布时间                 
  PRINTSTATUS         int        4      报告打印状态                 0-未打印 1-已打印
  PRINTCOUNT          int        4      自助机打印次数               
  PRINTCOMPUTER       varchar    255    最近一次打印的计算机名       
  PRINTBYCODE         varchar    20     最近一次打印人的工号         
  PRINTBYNAME         varchar    64     最近一次打印人的姓名         
  PRINTDATE           datetime   8      最近一次打印报告的时间       
  MJZBZ               int        4      门急诊标志                   0-常规 1-加急
  REDO                int        4      复做标志                     0-未复做 1-复做
  REDOSAMPLE          char       1      标本来源                     0-当前标本 1-重新采样
  REDOSAMPLEDESC      varchar    255    复做标本说明                 
  REDOREASON          varchar    255    复做原因                     
  MOBILESTATUS        int        4      短信或移动通知               0-未通知 1-已通知
  SCOPICFLAG          int        4      镜检复片标志                 1-镜检复片
  PANICFLAG           int        4      危急值报告                   0-正常 1-危急值报告
  BIOHAZARDFLAG       int        4      传染病标志                   0-正常 1-传染病报告
  TATVOILATE          int        4      是否违反TAT规则              0:正常 1:违反TAT规则 9-违反TAT规则
  ESTREPORTTIME       datetime   8      预计出报告时间               
  REPORTONTIME        int        4      是否按时出报告               -1-不做判断 0-未按时出报告 1-按时出报告
  DELAYRPTFLAG        int        4      延时取报告标志               1-申请延迟出报告
  DELAYRPTREASON      varchar    255    延迟取报告原因               
  DELAYRPTTIME        varchar    64     延迟到出报告时间             
  DELAYRPTBYCODE      varchar    20     延迟取报告操作人代码         
  DELAYRPTBYNAME      varchar    64     延迟取报告操作人姓名         
  DELAYRPTDATE        datetime   8      延迟取报告操作人时间         
  EXAMRESULT          varchar    1000   检验印象                     
  REMARK              varchar    1000   检验备注                     
  LABDIAGNOSIS        varchar    1000   实验室诊断最终的建议和解释   
  SCIENTIFICFLAG      int        4      科研标志                     
  SECTION             varchar    20     架号                         
  CUP                 varchar    20     杯号                         
  HISORDERCODE        varchar    500    检测项目代码                 
  HISORDERNAME        varchar    4000   检测项目名称                 
  CHARGEAMOUNT        numeric    5      收费金额                     
  EXAMCODE            varchar    20     检验分类代码                 
  EXAMNAME            varchar    64     检验分类名称                 
  REPORTTITLE         varchar    64     报告单标题                   
  SRCLAB              varchar    50     标本送检机构代码             
  TARGETLAB           varchar    50     检验机构代码                 
  MODTIME             datetime   8      修改时间                     
  MODTYPE             varchar    20     修改类型                     1-患者登记 2-报告编辑 3-报告删除 4-报告被转移 5-报告转移 6-报告被复制 7-报告复制 9-条码入库 10-撤销入库
  MODCODE             varchar    20     修改人工号                   
  MODNAME             varchar    64     修改人姓名                   
  MODPCNAME           varchar    255    修改机器名称                 格式：计算机名(Ip地址)
  MODREMARK           varchar    255    修改说明信息                 
  RESULTANALYZE       varchar    1000   结果建议与解释               
  CLINICREMARK        varchar    1000   临床备注                     
  SAMPLESHAPECODE     varchar    20     标本性状代码                 
  SAMPLESHAPENAME     varchar    64     标本性状名称                 
  ARCHIVINGPOSITION   varchar    32     标本归档位置                 
  TESTPOSITION        varchar    50     上机仪器检测位置坐标         
  ------------------- ---------- ------ ---------------------------- ---------------------------------------------------------------------------------------------------------

### 结果项目表LIS\_RESULT

  --------------------- ----------- ------ ------------------------ ------------------------------------------------------
  字段名                数据类型    长度   作用                     说明
  SERIALNO              numeric     9      结果唯一号               
  APPLYNO               numeric     9      报告单号                 
  ITEMCODE              varchar     20     项目代码                 
  ITEMNAME              varchar     64     项目名称                 
  INSTID                int         4      报告仪器ID               
  INSTCODE              varchar     20     报告仪器代码             
  INSTNAME              varchar     64     报告仪器名称             
  RESULTTIME            datetime    8      结果时间                 
  RESULTVALUE           numeric     13     定量结果                 
  RESULTCHAR            varchar     255    定性结果                 
  RESULT                varchar     255    报告结果                 
  HINTINFO              varchar     255    提示信息                 
  DISPLAYFLAG           int         4      显示状态                 0:正常状态;1:显示但不打印
  SCIENTIFICFLAG        int         4      科研标志                 0:非科研项目 1:科研项目
  ODRESULT              numeric     13     OD值                     
  ODRESULTCHAR          varchar     50     OD值字符表示             
  CUTOFFVALUE           numeric     13     CUTOFF比值               
  CUTOFFVALUECHAR       varchar     50     CUTOFF比值字符表示       
  SCOVALUE              numeric     13     SCO比值                  
  SCOVALUECHAR          varchar     50     SCO比值字符表示          
  TESTINSTID            int         4      检测仪器ID               
  TESTINSTCODE          varchar     20     检测仪器代码             
  TESTINSTNAME          varchar     64     检测仪器名称             
  PRINTORDER            varchar     20     打印序号                 
  HIGHLOWFLAG           varchar     10     高低标志                 P-阳性或异常值 H-偏高 L-偏低 HH-显著偏高 LL-显著偏低
  REFERENCERANGE        varchar     500    参考区间                 
  UNIT                  varchar     20     单位                     
  RESULTTYPE            char        1      结果类型                 
  REDO                  int         4      复做标记                 0-正常 1-复做
  PANICFLAG             int         4      危急值项目               
  PANICREFERENCERANGE   varchar     500    危急值参考区间           
  BIOHAZARDFLAG         int         4      传染病标志               0-正常 1-传染病报告
  INSTVERIFYFLAG        varchar     10     仪器审核标志             
  INSTVERIFYCONTENT     varchar     255    仪器审核内容描述         
  RESULTSOURCE          varchar     10     结果来源                 I-仪器入库 F-计算项目 S-手工项目
  FORMULARID            int         4      计算公式ID               
  FORMULARDESC          varchar     255    计算公式                 
  FORMULAROPCODE        varchar     20     记录选择公式的用户工号   
  FORMULAROPNAME        varchar     64     记录选择公式的用户姓名   
  FORMULAROPTIME        datetime    8      记录选择公式的操作时间   
  SCOPICFLAG            int         4      镜检复片标志             
  HOSPITALCODE          varchar     50     医疗机构代码             
  TIMETEMP              timestamp   8      时间戳                   
  ITEMNUM               varchar     20     项目编码                 
  TESTMETHOD            varchar     64     检测方法                 
  HIDEFLAG              int         4      隐藏标志                 0-正常 1-隐藏
  ISDEFVALUE            char        1      是否是默认结果           
  --------------------- ----------- ------ ------------------------ ------------------------------------------------------

### 结果项目修改表LIS\_RESULTMOD

  --------------------- ---------- ------ ---------------------------------------------- ------------------------------------------------------------------------------------------------------------------
  字段名                数据类型   长度   作用                                           说明
  SERIALNO              numeric    9      结果唯一号                                     
  HOSPITALCODE          varchar    50     医疗机构代码                                   
  RESULTID              numeric    9      结果ID流水号                                   对应LIS\_RESULT.SERIALNO
  APPLYNO               numeric    9      报告单号                                       
  ITEMCODE              varchar    20     项目代码                                       
  ITEMNUM               varchar    20     项目编码                                       
  ITEMNAME              varchar    64     项目名称                                       
  INSTID                int        4      报告仪器ID                                     
  INSTCODE              varchar    20     报告仪器代码                                   
  INSTNAME              varchar    64     报告仪器名称                                   
  RESULTTIME            datetime   8      仪器结果入库时间                               格式：yyyy-MM-dd HH:mm:ss
  RESULTVALUE           numeric    13     定量结果                                       
  RESULTCHAR            varchar    255    定性结果                                       
  RESULT                varchar    255    报告结果                                       
  HINTINFO              varchar    255    提示信息                                       
  DISPLAYFLAG           int        4      显示状态                                       0:正常状态;1:显示但不打印和向网上发布;
  HIDEFLAG              int        4      隐藏标志                                       0:正常状态;1:隐藏 外部调用时，不能访问该项目结果;
  SCIENTIFICFLAG        int        4      科研标志                                       0:非科研项目 1:科研项目
  ODRESULT              numeric    13     OD值                                           
  ODRESULTCHAR          varchar    50     OD值字符表示                                   
  CUTOFFVALUE           numeric    13     CUTOFF比值                                     
  CUTOFFVALUECHAR       varchar    50     CUTOFF比值字符表示                             
  SCOVALUE              numeric    13     SCO比值                                        
  SCOVALUECHAR          varchar    50     SCO比值字符表示                                
  TESTINSTID            int        4      检测仪器ID                                     
  TESTINSTCODE          varchar    20     检测仪器代码                                   
  TESTINSTNAME          varchar    64     检测仪器名称                                   
  TESTMETHOD            varchar    64     检测方法                                       
  PRINTORDER            varchar    20     打印序号                                       
  HIGHLOWFLAG           varchar    10     高低标志                                       P-阳性或异常值 H-偏高 L-偏低 HH-显著偏高 LL-显著偏低
  REFERENCERANGE        varchar    500    参考区间                                       
  UNIT                  varchar    20     单位                                           
  RESULTTYPE            char       1      结果类型                                       
  REDO                  int        4      复做标记                                       0-正常 1-复做
  PANICFLAG             int        4      危急值项目                                     0-正常 1-危急值项目
  PANICREFERENCERANGE   varchar    500    危急值参考区间                                 
  BIOHAZARDFLAG         int        4      传染病标志                                     0-正常 1-传染病报告
  INSTVERIFYFLAG        varchar    10     仪器审核标志                                   
  INSTVERIFYCONTENT     varchar    255    仪器审核内容描述                               
  RESULTSOURCE          varchar    10     结果来源                                       I-仪器入库 F-计算项目 S-手工项目
  FORMULARID            int        4      计算公式ID                                     
  FORMULARDESC          varchar    255    计算公式                                       
  FORMULAROPCODE        varchar    20     记录选择公式的用户工号                         
  FORMULAROPNAME        varchar    64     记录选择公式的用户姓名                         
  FORMULAROPTIME        datetime   8      记录选择公式的操作时间                         格式：yyyy-MM-dd HH:mm:ss
  SCOPICFLAG            int        4      镜检复片标志 1-镜检复片项目 0-非镜检复片项目   
  ISDEFVALUE            char       1      是否是默认结果                                 
  MODTIME               datetime   8      修改时间                                       
  MODTYPE               int        4      修改类型                                       0-手工输入 1-编辑 2-删除 5-仪器入库 9-复做修改 10-镜检复片 11-被转移 12-转移 13-被复制 14-复制 15-被合并 16-合并
  MODCODE               varchar    20     修改人工号                                     
  MODNAME               varchar    64     修改人姓名                                     
  MODPCNAME             varchar    255    修改机器名称                                   格式：计算机名(Ip地址)
  MODREMARK             varchar    255    修改说明信息                                   
  --------------------- ---------- ------ ---------------------------------------------- ------------------------------------------------------------------------------------------------------------------

### 检验项目信息表LIS\_ITEM

  -------------- ---------- ------ -------------- ---------------
  字段名         数据类型   长度   作用           说明
  HOSPITALCODE   varchar    50     医疗机构代码   
  ITEMCODE       varchar    20     项目代码       
  ITEMNAME       varchar    64     项目名称       
  ITEMNUM        varchar    20     标准编码       
  ENGSHORTNAME   varchar    20     英文缩写       
  ENGNAME        varchar    255    英文名称       
  LOINCCODE      varchar    20     LOINC编码      
  EXAMCODE       varchar    20     检验分类       
  EXAMNAME       varchar    64     检验分类名称   
  RESULTTYPE     char       1      结果类型       
  SAMPLECODE     varchar    20     标本种类       
  ITEMTYPEID     int        4      项目分类ID     
  DEFVALUE       varchar    20     缺省值         
  MEMCODE1       varchar    20     输入码一       
  MEMCODE2       varchar    20     输入码二       
  REMARK         varchar    255    备注说明       
  PREVALUE       numeric    13     精度           
  ITEMPRICE      numeric    9      单价           
  ITEMINFO       varchar    500    项目说明       
  MEANOFCLINIC   varchar    500    临床意义       
  STOPFLAG       char       1      停用标志       0-正常 1-停用
  -------------- ---------- ------ -------------- ---------------

### 检验项目字典表LIS\_ITEM\_P

  -------------- ---------- ------ -------------- ---------------
  字段名         数据类型   长度   作用           说明
  HOSPITALCODE   varchar    50     医疗机构代码   
  ITEMCODE       varchar    20     项目代码       
  ITEMNAME       varchar    64     项目名称       
  ITEMNUM        varchar    20     标准编码       
  ENGSHORTNAME   varchar    20     英文缩写       
  ENGNAME        varchar    255    英文名称       
  LOINCCODE      varchar    20     LOINC编码      
  EXAMCODE       varchar    20     检验分类       
  EXAMNAME       varchar    64     检验分类名称   
  RESULTTYPE     char       1      结果类型       
  SAMPLECODE     varchar    20     标本种类       
  ITEMTYPEID     int        4      项目分类ID     
  DEFVALUE       varchar    20     缺省值         
  MEMCODE1       varchar    20     输入码一       
  MEMCODE2       varchar    20     输入码二       
  REMARK         varchar    255    备注说明       
  PREVALUE       numeric    13     精度           
  ITEMPRICE      numeric    9      单价           
  ITEMINFO       varchar    500    项目说明       
  MEANOFCLINIC   varchar    500    临床意义       
  STOPFLAG       char       1      停用标志       0-正常 1-停用
  -------------- ---------- ------ -------------- ---------------

### 医嘱下载表LIS\_ACCEPTITEMS

  ----------------- ----------- ------ ----------------------------- ------------------------------------
  字段名            数据类型    长度   作用                          说明
  SERIALNO          numeric     9      流水号                        
  BRLX              varchar     10     病人类型                      0:门诊 1:住院 3:体检
  PATIENTID         varchar     64     病人内码                      
  CURENO            varchar     64     病人一次治疗的唯一号          
  APPLYNO           int         4      医嘱申请号                    -2 表示未入库未关联报告的条码项目
  ORDERNO           numeric     9      LIS\_ORDERMASTER 的关联主键   
  TXM               varchar     20     条形码                        
  HISAPPLYNO        varchar     20     医嘱申请号                    
  LISORDERCODE      varchar     20     LIS代码                       
  HISORDERCODE      varchar     20     医嘱项目代号                  
  HISORDERNAME      varchar     100    医嘱项目名称                  
  ITEMTYPE          char        1      项目类别                      0:临床项目 1:收费项目
  ITEMUNIT          varchar     20     项目单位                      
  APPLYTIME         datetime    8      申请时间                      
  ACCEPTTIME        datetime    8      接收时间                      
  MJZBZ             int         4      门急诊标志                    0:常规 1:加急
  PRICE             numeric     9      价格                          
  XMSL              numeric     9      数量                          
  SUMPRICE          numeric     9      汇总价格                      
  APPLYDEPTCODE     varchar     20     开方科室代码                  
  APPLYDEPTNAME     varchar     64     开方科室姓名                  
  APPLYDOCCODE      varchar     20     开方医生代码                  
  APPLYDOCNAME      varchar     64     开方医生姓名                  
  GROUPNO           varchar     20     组序号或处方序号              
  HISXXH            varchar     40     明细序号                      
  EXECDEPTCODE      varchar     20     执行科室代码                  
  EXECDEPTNAME      varchar     64     执行科室名称                  
  ENTRUST           varchar     1000   嘱托                          
  OPERATORCODE      varchar     20     操作员                        
  OPERATORNAME      varchar     64     操作员名字                    
  PLATE             varchar     20     盘号                          
  CUP               varchar     10     杯号                          
  TXMSTATUS         char        1      条形码状态                    5:绑定 0:签收 1:入库
  ADDTYPE           char        1      上传下载类型                  0:下载 1:上传
  STATUS            char        1      状态                          0:未确认 1:已确认 2:已拒绝
  CHARGEFLAG        char        1      收费标志                      0:未收费 1:已收费 2:已退费
  SPECIMEN          varchar     20     标本种类代码                  
  SPECIMENDESC      varchar     64     标本种类名称                  
  REMARK            varchar     255    备注                          
  EXAMCODE          varchar     20     检验类型代码                  
  EXAMNAME          varchar     64     检验类型名称                  
  BAREXAMCODE       varchar     20     条码分组代码                  
  BAREXAMNAME       varchar     40     条码分组名称                  
  GROUPSIGNFLAG     char        1      小组签收                      0-未签收 1-已签收
  GROUPSIGNCODE     varchar     20     小组签收人工号                
  GROUPSIGNNAME     varchar     64     小组签收人姓名                
  GROUPSIGNTIME     datetime    8      小组签收时间                  
  GROUPSIGNPCNAME   varchar     255    小组签收计算机名              
  RKINSTID          int         4      入库仪器ID                    
  RKSJ              datetime    8      入库时间                      
  RKRDM             varchar     20     入库人代码                    
  RKRXM             varchar     64     入库人姓名                    
  RKPCNAME          varchar     255    入库计算机名                  
  PH                int         4      排号                          
  PHSJ              datetime    8      排号时间                      
  PHRDM             varchar     20     排号人代码                    
  PHRXM             varchar     64     排号人姓名                    
  PHPCNAME          varchar     255    排号计算机名                  
  PRINTSTATUS       char        1      回执单打印标志                
  GHXH              varchar     30     挂号序号                      
  EXCUTETIME        datetime    8      执行时间                      
  ACCOUNTTYPE       varchar     1      记账方式                      默认为空 1 本地医嘱,费用可剔除统计
  PROVIDEBLOOD      varchar     100    血袋                          
  ICD10             varchar     20     疾病分类代码                  
  ICD10NAME         varchar     64     疾病分类名称                  
  CLINICDESC        varchar     500    临床诊断                      
  HOSPITALCODE      varchar     50     医疗机构代码                  
  RETURNDAY         datetime    8      取报告时间                    
  RETURNCONTENT     varchar     100    取报告统一说明                
  TIMETEMP          timestamp   8      时间戳                        
  EXAMGROUPNO       varchar     20     签收小组代码                  
  EXAMGROUPNAME     varchar     64     签收小组名称                  
  INVOICENO         varchar     32     发票号                        
  PRINTORDER        varchar     20     打印顺序                      
  ----------------- ----------- ------ ----------------------------- ------------------------------------

### 预约主记录表 LIS\_ORDERMASTER

  --------------------- ----------- ------ ----------------------------- ----------------------------------------------------
  字段名                数据类型    长度   作用                          说明
  ORDERNO               numeric     9      流水号                        与LIS\_ACCEPTITEMS明细表关联
  APPLYNO               numeric     9      预约号                        门诊：-1普通病人-2银医通病人；住院：-1普通住院病人
  ORDERDATE             datetime    8      预约时间                      
  PATIENTID             varchar     64     病人内码                      
  CURENO                varchar     64     病人一次治疗的唯一号          
  CARDTYPE              varchar     10     卡类型                        
  CARDNO                varchar     64     磁卡号                        
  HOSPNO                varchar     64     病员号                        
  INVOICENO             varchar     32     发票号                        
  PATNAME               varchar     64     病人姓名                      
  IMECODE               varchar     20     拼音码                        
  SEX                   varchar     10     性别                          1-男 2-女 3-未知
  SEXDESC               varchar     10     性别文字描述                  
  AGE                   int         4      年龄1                         
  AGEUNIT               varchar     10     年龄单位1                     
  AGE2                  int         4      年龄2                         
  AGEUNIT2              varchar     10     年龄单位2                     
  AGEDESC               varchar     20     年龄描述                      
  CHARGETYPE            varchar     20     费别代码                      
  CHARGETYPENAME        varchar     64     费别名称                      
  SAMPLECODE            varchar     20     标本代码                      
  SAMPLENAME            varchar     64     标本名称                      
  WARDORREG             varchar     20     病人类别代码                  
  WARDORREGNAME         varchar     64     病人类别名称                  
  BRLX                  varchar     10     病人类型                      0-门诊 1-住院 3、4-体检
  WARD                  varchar     20     病区代码                      
  WARDNAME              varchar     64     病区名称                      
  BEDNO                 varchar     20     床号                          
  INSTID                int         4      仪器ID                        
  INSTNAME              varchar     64     仪器名称                      
  SAMPLETYPE            char        2      样本号前缀                    
  SAMPLEID              varchar     10     样本号                        
  ORGAPPLYNO            varchar     20     原始申请号                    
  STATUS                char        1      状态                          0未预约，1已预约
  SAMPLEDESCCODE        varchar     20     标本说明代码                  
  SAMPLEDESCNAME        varchar     40     标本说明名称                  
  APPLYDEPTCODE         varchar     20     申请科室代码                  
  APPLYDEPTNAME         varchar     64     申请科室名称                  
  APPLYDOCCODE          varchar     20     申请医生代码                  
  APPLYDOCNAME          varchar     64     申请医生姓名                  
  EXAMCODE              int         4      检验分类代码                  
  EXAMNAME              varchar     20     检验分类名称                  
  PATPROPCODE           varchar     20     病人特征代码                  
  PATPROPNAME           varchar     64     病人特征名称                  
  IDNUM                 varchar     20     身份证号                      
  ADDRESS               varchar     255    联系地址                      
  NOTES                 varchar     800    绑定备注                      
  BIRTHDAY              datetime    8      出生日期                      
  CLINICDESC            varchar     500    临床诊断                      
  M\_PHONE              varchar     32     手机号码                      
  REMARK                varchar     255    备注说明                      
  PRINTSTATUS           char        1      打印状态                      
  SRCTXM                varchar     20     条码拆解源条码                
  TXM                   varchar     20     条形码                        
  SAMPLEPLACE           varchar     20     采样站点                      
  SAMPLEPLACENAME       varchar     64     采样站点名称                  
  CXSJ                  datetime    8      绑定时间                      
  CXRDM                 varchar     20     绑定人代码                    
  CXRXM                 varchar     64     绑定人姓名                    
  CXPCNAME              varchar     255    绑定计算机名称                
  CXBATCHNO             varchar     20     打印批号                      
  SAMPLECHECKSTATUS     char        1      标本信息核对状态              0-未核对 1-已核对
  SAMPLECHECKBYCODE     varchar     20     标本信息核对人工号            
  SAMPLECHECKBYNAME     varchar     64     标本信息核对人姓名            
  SAMPLECHECKDATE       datetime    8      标本信息核对时间              
  SAMPLECHECKPCNAME     varchar     255    标本信息核对计算机名称        
  HASUPDATEDRAWDATE     int         4      是否更新采样时间              0-否，1-是
  DRAWDATE              datetime    8      采样时间                      
  DRAWUSERCODE          varchar     20     采样人代码                    
  DRAWUSERNAME          varchar     20     采样人名称                    
  UPOPERUSERID          varchar     20     更新采样时间人工号            
  UPOPERUSERNAME        varchar     64     更新采样时间人姓名            
  UPOPERPCNAME          varchar     255    更新采样时间计算机名称        移动护理则更新为\"移动护理\"
  NURSECONFIRMBATCHNO   varchar     20     护士派发批号                  hspf+yyyymmdd+流水号
  NURSECONFIRMDATE      datetime    8      护士派发日期                  
  NURSECONFIRMCODE      varchar     20     护士派发代码                  
  NURSECONFIRMNAME      varchar     50     护士派发姓名                  
  NURSECONFIRMPCNAME    varchar     50     护士派发计算机名称            
  WORKERPLATE           varchar     255    护工工号牌扫描                未扫描工号牌则为空
  WORKERBATCHNO         varchar     20     护工签收批号                  
  WORKERDATE            datetime    8      护工签收日期                  
  WORKERCODE            varchar     20     护工签收工号                  
  WORKERNAME            varchar     64     护工签收姓名                  
  WORKERPCNAME          varchar     255    护工签收计算机                
  WORKERPLATE2          varchar     255    护工工号牌扫描                未扫描工号牌则为空
  WORKERDATE2           datetime    8      护工确认时间                  
  WORKERCODE2           varchar     20     护工确认员工代码              
  WORKERNAME2           varchar     64     护工确认员工姓名              
  WORKERPCNAME2         varchar     255    护工确认计算机                
  BATCHNOQRBZ           char        1      批号确认标志                  0 未签收 1 已签收
  BATCHNOQRSJ           datetime    8      批号确认时间                  
  BATCHNOQRR            varchar     20     批号确认人工号                
  BATCHNOQRRXM          varchar     64     批号确认人姓名                
  QSSJ                  datetime    8      签收时间                      
  QSRDM                 varchar     20     签收人代码                    
  QSRXM                 varchar     64     签收人姓名                    
  QSBATCHNO             varchar     20     签收批号                      
  QSXH                  int         4      签收序号                      
  QSPCNAME              varchar     255    签收计算机名称                
  SPLITBYCODE           varchar     20     条码拆解人                    
  SPLITBYNAME           varchar     64     条码拆解人姓名                
  SPLITDATE             datetime    8      条码拆解时间                  
  FAILFLAG              varchar     20     撤销拒签收标志                撤销签收、拒绝签收
  FAILTYPE              varchar     50     退回原因分类                  
  FAILMEMO              varchar     250    退回原因                      
  TUBERACK              varchar     50     杯号                          
  RACKROW               int         4      行                            
  RACKCOL               int         4      列                            
  GROUPBARCOUNT         int         4      一组条码中条码数量            
  GROUPBARNO            int         4      一组条码中条码序号            
  BARGROUPNO            varchar     20     一次绑定条码的组号            
  NATION                varchar     64     民族                          
  EMPIID                varchar     20     EMPIID                        
  PATHDIAGNOSIS         varchar     500    病理诊断(检验所用) 文字描述   
  SMOKINGHISTORY        varchar     200    吸烟史(检验所用) 文字描述     
  COLLECTTYPE           varchar     200    采集方式(检验所用) 文字描述   
  MEDICATION            varchar     200    用药(检验所用) 文字描述       
  PRECAUTIONS           varchar     500    注意事项                      
  COLLECTSOURCE         varchar     64     采集标本来源                  
  MJZBZ                 int         4      门急诊标志                    
  HOSPITALCODE          varchar     50     0:常规 1:加急                 
  SRCLAB                varchar     50     送检机构代码                  
  TARGETLAB             varchar     50     检验机构代码                  
  HISORDERCODE          varchar     500    检测项目代码                  
  HISORDERNAME          varchar     4000   检测项目名称                  
  CHARGEAMOUNT          numeric     5      收费金额                      
  BAREXAMCODE           varchar     20     条码分组代码                  
  BAREXAMNAME           varchar     64     条码分组名称                  
  BIOHAZARDFLAG         int         4      传染病标志                    0-正常 1-传染病报告
  TIMETEMP              timestamp   8      时间戳                        
  IMAGEID               int         4      采集时照片ID                  对应LIS\_SAMPLEPHOTO.IMAGEID
  MANUALREGISTE         char        1      手工登记标志                  0-正常 1-手工登记
  --------------------- ----------- ------ ----------------------------- ----------------------------------------------------

### 特殊参考值表LIS\_ITEMREFERANCE

  --------------- ---------- ------ ---------------------- -------------------------------------------------------------------------------------------------------
  字段名          数据类型   长度   作用                   说明
  SERIALNO        int        4      序号                   
  INSTID          int        4      仪器ID                 
  ITEMCODE        varchar    20     项目代码               
  REFERTYPE       varchar    20     参考类型               NORMAL-常规参考值 OvERLIMIT-极限参考值 ALARM-报警参考值 PANIC-危急值参考范围 INFECTIOUS-传染病
  RULETYPE        char       1      验证类型               0 - 参考上下限验证 1-字符参考验证 2-逻辑表达式验证
  LOWLIMIT        numeric    13     参考下限               
  UPPLIMIT        numeric    13     参考上限               
  CHARREFER       varchar    40     字符参考值             
  CONTAINBOUND    int        4      是否包含边界值         数字结果:1-包含边界 0-不包含边界 2-包含左边界 3-包含右边界 字符结果:1-包含 2-全匹配 3-不等于 4-不包含
  REFERDESC       varchar    255    参考区间描述           
  CONDITION       varchar    255    验证表达式             \[RULETYPE\] = 2 时有效
  CONDITIONDESC   varchar    1000   验证表达式描述         
  ITEMIDLIST      varchar    255    审核条件对应的项目ID   
  PRINTREFER      varchar    255    参考打印格式           
  EFFCONID        int        4      生效条件ID             
  STOPFLAG        char       1      停用标志               0-正常 1-停用
  --------------- ---------- ------ ---------------------- -------------------------------------------------------------------------------------------------------

### 计算公式表LIS\_FORMULAR

  --------------- ---------- ------ ------------------ ---------------
  字段名          数据类型   长度   作用               说明
  SERIALNO        int        4      自增ID             
  INSTID          int        4      仪器ID             
  ITEMCODE        varchar    20     检验项目代码       
  FORMULAR        varchar    1000   计算公式           
  FORMULARDESC    varchar    2000   计算公式描述       
  ITEMIDLIST      varchar    255    项目ID             
  CONDITION       varchar    255    项目的生效条件     
  CONDITIONDESC   varchar    1000   项目生效条件描述   
  EFFCONID        int        4      生效条件ID         
  STOPFLAG        char       1      停用标志           0-正常 1-停用
  --------------- ---------- ------ ------------------ ---------------

### 审核条件表LIS\_CONDITION

  -------------------- ---------- ------ ---------------------- ----------------------------------
  字段名               数据类型   长度   作用                   说明
  RULEID               int        4      流水号                 
  RULENAME             varchar    64     审核规则名称           
  INSTID               int        4      仪器ID                 
  ITEMCODE             varchar    20     主项目代码             
  ORDERNO              int        4      序号                   
  GRADE                int        4      处理级别               1-严重级别 2-警告级别 3-普通级别
  CONDITIONTYPE        varchar    20     审核类型               
  DIAGNOSE             varchar    255    审核提示               
  ITEMIDLIST           varchar    255    审核条件对应的项目ID   
  CONDITION            varchar    2000   审核条件               
  CONDITIONDESC        varchar    4000   审核条件描述           
  CONDITIONITEM        varchar    255    条件项目               
  COMPUTERVERIFYFLAG   char       1      电脑审核标志           
  HANDVERIFYFLAG       char       1      手工审核标志           
  EFFCONID             int        4      生效条件ID             
  STOPFLAG             char       1      停用标志               0-正常 1-停用
  -------------------- ---------- ------ ---------------------- ----------------------------------

### 危急值追踪表Lab\_PanicRepTrace

  --------------------- ----------- ------ -------------------------- ------------------------------------------------------------------------
  字段名                数据类型    长度   作用                       说明
  SERIALNO              numeric     9      流水记录号                 
  REPORTNO              numeric     9      报告单号                   
  SUBSYSCODE            varchar     20     子系统代码                 
  SYSTYPE               varchar     20     业务类型                   LIS-常规 MIS-微生物 XK-血库
  SUBREPORTNO           int         4      微生物报告ID               
  STATUS                varchar     20     处理状态                   未发布-\>已发布-\>接收确认(已接收）-\>通知医生-\>医生确认-\>检验科确认
  REPORTDESC            varchar     2000   危急值描述                 
  FIRSTCHECKTIME        datetime    8      首次监测出危急值的时间     
  UPLOADUSERCODE        varchar     20     危急值上传人员工号         
  UPLOADUSERNAME        varchar     64     危急值上传人员姓名         
  MANUALFLAG            char        1      手工危急值标志             0-自动判断 1-手动危急值
  WEBPUB                char        1      网络回报标志               0-非网络回报 1-网络回报
  PHONECALLPUB          char        1      电话回报标志               0-非电话回报 1-电话回报
  OUTRECFLAG            char        1      超时接收标志               0-正常接收 1-超时接收
  CUSTODYNO             varchar     20     留样编号                   
  CALLERCODE            varchar     20     打电话通知临床的人员工号   
  CALLERNAME            varchar     64     打电话通知临床的人员姓名   
  CALLDATE              datetime    8      打电话通知临床的时间       
  ACKPERSON             varchar     20     接听人工号                 
  ACKPERSONNAME         varchar     64     接听人姓名                 
  ACKPHONE              varchar     20     接听电话                   
  ACKREPEAT             char        1      接听者复述                 0-未复述 1-复述
  REMARK                varchar     200    备注                       
  PUBDATE               datetime    8      发布危急值到临床时间       
  PUBERCODE             varchar     20     发布人工号                 
  PUBERNAME             varchar     64     发布人姓名                 
  PUBERPCNAME           varchar     255    发布计算机名               
  CONFIRMDEPT           varchar     20     接收确认科室代码           
  CONFIRMDEPTNAME       varchar     64     接收确认科室名称           
  CONFIRMUSER           varchar     20     接收确认人工号             
  CONFIRMUSERNAME       varchar     64     接收确认人姓名             
  CONFIRMDATE           datetime    8      接收确认时间               
  CONFIRMPCNAME         varchar     255    处理计算机名               
  CONFIRMMEMO           varchar     200    接收确认备注说明           
  NOTIFYDOCTOR          varchar     20     护士通知医生工号           
  NOTIFYDOCTORNAME      varchar     64     护士通知医生姓名           
  NOTIFYDOCTORTIME      datetime    8      护士通知医生时间           
  NOTIFYNURSE           varchar     20     通知医生护士工号           
  NOTIFYNURSENAME       varchar     64     通知医生护士姓名           
  NOTIFYPCNAME          varchar     255    通知医生计算机名           
  HANDLEDOCCODE         varchar     20     处理医生工号               
  HANDLEDOCNAME         varchar     64     处理医生姓名               
  HANDLETIME            datetime    8      处理时间                   
  HANDLEPCNAME          varchar     255    处理计算机名               
  HANDLEMEMO            varchar     200    处理意见                   
  RECALLSTATUS          varchar     20     召回状态                   
  RECALLTIME            datetime    8      召回时间                   
  RECALLMEMO            varchar     200    召回备注                   
  RECALLUSER            varchar     20     召回人工号                 
  RECALLUSERNAME        varchar     64     召回人姓名                 
  RECALLPCNAME          varchar     255    召回计算机名称             
  EXECCONFIRMTIME       datetime    8      检验科反馈确认时间         
  EXECCONFIRMMEMO       varchar     200    检验科反馈确认说明         
  EXECCONFIRMUSER       varchar     20     检验科反馈确认工号         
  EXECCONFIRMUSERNAME   varchar     64     检验科反馈确认姓名         
  EXECCONFIRMPCNAME     varchar     255    检验科反馈确认计算机名称   
  ISREDO                char        1      是否复做                   
  ISINSTLOSE            char        1      是否仪器在控               
  SAMPLEDESC            varchar     40     标本说明                   
  FIRSTRESULT           varchar     200    初次结果（微生物）         
  REDORESULT            varchar     200    复核结果（微生物）         
  DIFFWITHCLIN          char        1      结果与临床诊断不符         
  EXECCONFIRMDEPT       varchar     20     检验科反馈确认科室代码     
  EXECCONFIRMDEPTNAME   varchar     64     检验科反馈确认科室名称     
  NOTIFYDOCTORMEMO      varchar     30     通知备注                   
  INSTID                int         4      仪器ID                     
  EXECTIME              datetime    8      检测日期                   
  TECHNO                int         4      样本号                     
  REPTIMETEMP           varbinary   32     对应报告单的时间戳         
  --------------------- ----------- ------ -------------------------- ------------------------------------------------------------------------

### 材料费明细表 LIS\_MATFEEDETAIL

  ----------------- ---------- ------ -------------------- --------------------------
  字段名            数据类型   长度   作用                 说明
  MATNO             int        4      流水号               
  HOSPITALCODE      varchar    20     机构代码             
  MATORDERCODE      varchar    40     LIS材料费项目代码    
  HISORDERCODE      varchar    40     HIS材料费项目代码    
  HISORDERNAME      varchar    100    HIS材料费项目名称    
  PRICE             numeric    5      单价                 
  SQTY              numeric    5      数量                 
  TXM               varchar    500    条形码               
  LOGNO             varchar    40     医嘱序号             
  HISAPPLYNO        varchar    20     申请单号             
  GROUPNO           varchar    20     处方序号             
  STATUS            char       1      状态                 1-已确认 0-未确认 2-拒绝
  CHARGEFLAG        char       1      收费标志             1：已收 2：已退
  BRLX              varchar    10     病人类型             0-门诊 1-住院 3、4-体检
  PATIENTID         varchar    64     病人ID               
  CURENO            varchar    64     治疗号               
  PATNAME           varchar    64     患者姓名             
  CARDNO            varchar    64     磁卡号               
  HOSPNO            varchar    64     病员号               
  APPLYTIME         datetime   8      申请时间             
  MATEXECDEPT       varchar    20     材料费执行科室       
  MATEXECDEPTNAME   varchar    64     材料费执行科室名称   
  OPERATOR          varchar    20     操作员               
  OPERATORNAME      varchar    64     操作员姓名           
  OPERATETIME       datetime   8      操作时间             
  OPERATEPCNAME     varchar    255    客户端名称           
  ----------------- ---------- ------ -------------------- --------------------------

### 材料费对应收费项目 LIS\_MATERIALTOORDER

  ----------------- ---------- ------ ----------------- -------------------------------
  字段名            数据类型   长度   作用              说明
  HOSPITALCODE      varchar    50     机构代码          
  MATORDERCODE      varchar    20     LIS材料费代码     对应LIS\_HISITEM.LISORDERCODE
  MATHISORDERCODE   varchar    20     HIS材料费代码     对应LIS\_HISITEM.HISORDERCODE
  MATHISORDERNAME   varchar    100    材料费名称        对应LIS\_HISITEM.HISORDERNAME
  LISORDERCODE      varchar    20     LIS收费项目代码   
  HISORDERCODE      varchar    20     HIS收费项目代码   
  HISORDERNAME      varchar    100    收费项目名称      
  SUPPLIESCODE      varchar    40     供应商代码        
  ----------------- ---------- ------ ----------------- -------------------------------

### 条码分组表LIS\_BAREXAMCLASS

  ----------------- ---------- ------ ---------------------------- ---------------------------------------------------------------
  字段名            数据类型   长度   作用                         说明
  BAREXAMCODE       int        4      条形码分组代码               
  BAREXAMNUM        varchar    20     条码分组编码                 
  BAREXAMNAME       varchar    64     条形码分组名称               
  MEMCODE1          varchar    20     输入码一                     
  MEMCODE2          varchar    20     输入码二                     
  BARCOLOR          varchar    20     颜色                         
  BARSECONDCOLOR    varchar    20     第二颜色                     
  IMAGEURL          varchar    255    试管图标相对地址             
  BARPREPART        varchar    2      条形码前缀                   
  BARSINGLE         int        4      单独标志                     0：同一个类别捆绑一根条码；1：每个HIS医嘱项目单独捆绑一根条码
  BARPRIORITY       varchar    2      优先次序                     
  BARLENGTH         int        4      条码长度                     
  BARADDITIVE       varchar    20     添加剂                       
  BARADDITIVENAME   varchar    64     添加剂名称                   
  BARCUBAGE         varchar    20     试管采血量                   
  BARRANGE          varchar    100    适用范围                     
  BARDESCRIBE       varchar    40     分组描述                     
  BARNOTICE         varchar    40     采集事项                     
  EXAMGROUPCODE     varchar    20     检测小组                     
  SAMPLECODE        varchar    20     标本种类                     
  CHECKSTATUS       char       1      在采集界面默认是否勾选状态   
  BARSECPREPART     varchar    2      第二前缀                     
  PRINTORDER        varchar    20     打印序号                     
  ZYPRINTCOUNT      int        4      住院打印条码份数             
  MZPRINTCOUNT      int        4      门诊打印条码份数             
  TJPRINTCOUNT      int        4      体检打印条码份数             
  MATEXECDEPT       varchar    20     材料费执行科室               
  SUBMISSIONPLACE   varchar    255    送检地点                     
  BARGENRULE        varchar    20     条码规则编码                 
  COLLECTNOTICE     varchar    2000   采集注意事项                 
  HOSPBARPRE        varchar    2      医疗机构简码                 
  HOSPITALCODE      varchar    50     医疗机构代码                 
  ----------------- ---------- ------ ---------------------------- ---------------------------------------------------------------

### 条码对应收费项目LIS\_BARORDER

  -------------- ---------- ------ ---------------- -------------------------
  字段名         数据类型   长度   作用             说明
  HOSPITALCODE   varchar    50     医疗机构代码     
  BAREXAMCODE    int        4      条形码分组代码   
  LISORDERCODE   varchar    20     LIS代码          
  HISORDERCODE   varchar    20     医嘱项目代码     
  HISORDERNAME   varchar    200    医嘱项目名称     
  BARSINGLE      int        4      单独绑定标志     0-不单独绑定 1-单独绑定
  PRINTORDER     int        4      打印分组         
  PRINTGROUP     char       1      打印顺序         
  -------------- ---------- ------ ---------------- -------------------------

质控系统
--------

### 质控规则字典 QC\_RULE\_P

  ----------- ---------- ------ ---------------------- -----------------------------------------------------------------
  字段名      数据类型   长度   作用                   说明
  RULECODE    varchar    20     规则代码               
  RULENAME    varchar    40     规则名称               
  RULEDESC    varchar    200    规则描述               
  COUNTN      int        4      结果数                 
  COUNTM      int        4      控制数                 
  MULTX       numeric    9      标准差倍数             
  MULTY       numeric    9      区间刻度数             
  RULETYPE    int        4      质控规则分类           1-标准差 2极差3趋势4平均数5比例 6百分比 7正负N 8区间 10累积规则
  JUDGEFLAG   int        4      判断标志(平均数控制)   1 不要求失控点出2SD ;0 要求失控点出2SD;
  WARNTYPE    int        4      规则类型               0警告规则，1失控规则
  RULECODE    varchar    20     规则代码               
  ----------- ---------- ------ ---------------------- -----------------------------------------------------------------

### 质控规则 QC\_RULE

  -------------- ---------- ------ ---------------------- ------------------------------------------------------------------------
  字段名         数据类型   长度   作用                   说明
  HOSPITALCODE   varchar    64     医疗机构代码           
  RULEID         int        4      规则ID                 
  RULECODE       varchar    20     规则代码               
  RULENAME       varchar    40     规则名称               
  RULEDESC       varchar    200    规则描述               
  COUNTN         int        4      结果数                 
  COUNTM         int        4      控制数                 
  MULTX          numeric    9      标准差倍数             
  MULTY          numeric    9      区间刻度数             
  RULETYPE       int        4      质控规则分类           1-标准差 2极差3趋势4平均数5比例 6百分比 7正负N 8区间 9 定性 10累积规则
  JUDGEFLAG      int        4      判断标志(平均数控制)   1 不要求失控点出2SD ;0 要求失控点出2SD;
  WARNTYPE       int        4      规则类型               0警告规则，1失控规则
  STOPFLAG       int        4      停用标志               0在用 1停用
  -------------- ---------- ------ ---------------------- ------------------------------------------------------------------------

### 质控计划\--基本信息表 QC\_LOTBASEINFO

  ------------------- ---------- ------ --------------------- ----------------
  字段名              数据类型   长度   作用                  说明
  HOSPITALCODE        varchar    64     医疗机构代码          
  LOTBASEID           int        4      质控品ID 主键流水号   
  INSTID              int        4      仪器ID                
  INSTNAME            varchar    64     仪器名称              
  LOTBASECODE         varchar    20     质控品编码            
  LOTBASENAME         varchar    60     质控品名称            
  EXPDATE             datetime   8      有效期                
  LEVELNUM            int        4      水平数量              比如1，2,3
  MATRIX              varchar    20     基质代码              
  MATRIXNAME          varchar    20     基质名字              
  PRODUCER            varchar    100    生产商                
  LOTSTATUS           varchar    20     状态                  干粉/液态/其他
  SPECIFICATIONS      varchar    20     规格                  5ML/5G等
  STORAGECONDITIONS   varchar    50     储存条件              -40摄氏度等
  STOPFLAG            int        4      停用标志              0在用 1停用
  STARTDATE           varchar    16     排序序号              
  ORDERNO             int        4      启用年份              
  GRUBBSFLAG          int        4      即刻法仪器自动传送    
  ------------------- ---------- ------ --------------------- ----------------

### 质控品\--水平表 QC\_LOT

  -------------- ---------- ------ -------------- -----------------------------------
  字段名         数据类型   长度   作用           说明
  HOSPITALCODE   varchar    64     医疗机构代码   
  LOTBASEID      int        4      质控品ID       关联QC\_LOTBASEINFO表字段
  LOTID          int        4      水平 ID        
  BATCHNO        varchar    20     水平 批号      
  LEVELCODE      int        4      水平代码       比如 1 2,3 浓度（1 低 2 中 3 高）
  EXTERNCODE     varchar    20     外部系统批号   
  TECHNO         varchar    20     QC样本号       多样本用逗号分割
  WAVELENGTH     varchar    50     波长           
  PRODUCER       varchar    100    有效期         
  EXPDATE        datetime   8      生产商         
  -------------- ---------- ------ -------------- -----------------------------------

### 质控品对应项目表 QC\_LOTITEM

  ---------------- ---------- ------ ------------------------- -------------------------------------------------------------------------------
  字段名           数据类型   长度   作用                      说明
  LOTITEMID        int        4      流水号                    
  HOSPITALCODE     varchar    64     医疗机构代码              
  LOTBASEID        varchar    20     质控品ID                  关联QC\_LOTBASEINFO表字段
  INSTID           int        4      仪器ID                    
  ITEMCODE         varchar    20     项目代码                  关联LIS\_ITEM表字段
  ITEMNUM          varchar    20     项目编码                  
  ITEMNAME         varchar    64     项目名称                  
  TESTMETHODCODE   varchar    20     测试方法代码              
  TESTMETHODNAME   varchar    50     测试方法名称              
  UNIT             varchar    20     项目单位                  
  PREVALUE         varchar    60     精度                      分子项目每个水平精度不一样，用\|分割水平顺序为低中高比如： 0.01\|1000\|1000\|
  CALIBRATION      varchar    50     校准品\--生产商名         
  REAGENT          varchar    50     试剂\--生产商名           
  EXTERNCODE       varchar    20     外部代码                  
  ORDERNO          int        4      排序序号                  
  RULETYPE         varchar    20     质控图形类型              1定量多规则 2半定量规则 3定性规则 4定性双区法 5区间刻度 6.结果范围规则
  RULEID           varchar    200    质控规则ID                
  RULECODE         varchar    200    质控规则代码              
  RULEIDWARN       varchar    50     (警告规则）质控规则ID     RULETYPE=1 时使用该字段。
  RULECODEWARN     varchar    50     (警告规则）质控规则代码   RULETYPE=1 时使用该字段。
  CUMULATE         int        4      累积和图规则              只给累积和图的作图规则
  COTOFF           varchar    200    临界CotOff值              RULETYPE=4（定性双区法） 时使用该字段。
  ---------------- ---------- ------ ------------------------- -------------------------------------------------------------------------------

### 质控项目靶值 QC\_TESTTARGET

  ------------ ------------ ------ -------------- ---------------------------
  字段名       数据类型     长度   作用           说明
  TESTID       int          4      ID             
  LOTBASEID    varchar      20     质控品ID       关联QC\_LOTBASEINFO表字段
  LOTID        varchar      20     质控品水平ID   关联QC\_LOT表字段
  INSTID       int          4      仪器ID         
  ITEMCODE     varchar      20     项目代码       
  STARTDATE    date         3      启用日期       
  TARGET       numeric      9      靶值(均值)     
  SD           numeric      9      SD(标准差)     
  CV           numeric      9      Cv(变异系数)   =SD(标准差)/靶值\*100%
  POINTS       int          4      初始化点数     
  MODIFYCODE   varchar      20     修改者         
  MODIFYTIME   datetime     8      修改时间       
  TIEMTEMP     rowversion          时间戳         
  ------------ ------------ ------ -------------- ---------------------------

### 质控结果信息 QC\_RESULT

  --------------- ------------ ------ ------------------------ ---------------------------------------------------------------------
  字段名          数据类型     长度   作用                     说明
  RESULTNO        int          4      流水号                   
  HOSPITALCODE    vARCHAR      64     医疗机构代码             
  TESTDATE        DATE         3      测试日期                 年月日
  TESTTIME        TIME         5      测试时间                 时分秒
  TESTNUM         INT          4      当日测试次数             
  INSTID          INT          4      仪器ID                   
  TECHNO          vARCHAR      20     QC样本号                 
  LOTID           INT          4      质控品水平ID             关联QC\_LOT表
  ITEMCODE        vARCHAR      20     项目代码                 
  ORIGINALVALUE   NUMERIC      9      原始数据                 仪器自动传入的数据
  ORIGINALCHAR    vARCHAR      9      原始数据用于显示         如果数字跟ORIGINALvALUE一样，如果是+++ 就不一样
  USERVALUE       NUMERIC      20     用户数据用于绘图         用户输入或修改数据
  USERCHAR        vARCHAR      20     用户数据用于显示         用户输入或修改数据。如果数字型跟USERvALUE一样，如果是是+++ 就不一样
  LOG10VALUE      NUMERIC      4      分子项目会用对数值画图   
  OPERATORCODE    vARCHAR      4      数据输入人员工号ID       仪器传入默认AOTO
  OPERATORNAME    vARCHAR      100    数据输入人员姓名         仪器传入默认AOTO
  STATUS          INT          4      数据状态                 0 有效 1 失效
  QCSTATUS        INT          20     质控结果状态             -1 初始值 ,判断 0 在控 1 警告 2 失控
  QCMULRULE       vARCHAR      20     违反多规则               (是多个规则组合，逗号分开)
  QC3SD           INT          8      3SD标志                  0 未超过 1 超过3SD值
  CHECKBYCODE     vARCHAR      1      审核者工号ID             
  CHECKBYNAME     vARCHAR      20     审核者姓名               
  CHECKTIME       DATETIME     20     审核时间                 
  LOCKFLAG        CHAR         8      数据锁定状态             0 数据未锁 1 数据已锁
  UPDATEBYCODE    vARCHAR      20     最后更新人工号ID         
  UPDATEBYNAME    vARCHAR      20     最后更新人姓名           
  UPDATETIME      DATETIME            最后更新时间             
  TIMETEMP        rowversion          时间戳                   
  --------------- ------------ ------ ------------------------ ---------------------------------------------------------------------

### 质控数据修改历史记录 QC\_RESULT\_HISTORY

  --------------- ---------- ------ ------------------------ ---------------------------------------------------------------------
  字段名          数据类型   长度   作用                     说明
  SERIALNO        int        4      流水号                   
  RESULTNO        int        4      医疗机构代码             
  OPERCODE        varchar    20     测试日期                 年月日
  OPERNAME        varchar    40     测试时间                 时分秒
  OPERDATE        datetime   8      当日测试次数             
  OPERTYPE        int        4      仪器ID                   
  ORIGINALVALUE   numeric    9      QC样本号                 
  USERVALUE       numeric    9      质控品水平ID             关联QC\_LOT表
  STATUS          int        4      项目代码                 
  QCSTATUS        int        4      原始数据                 仪器自动传入的数据
  QCMULRULE       varchar    100    原始数据用于显示         如果数字跟ORIGINALvALUE一样，如果是+++ 就不一样
  HOSPITALCODE    varchar    64     用户数据用于绘图         用户输入或修改数据
  TESTDATE        date       3      用户数据用于显示         用户输入或修改数据。如果数字型跟USERvALUE一样，如果是是+++ 就不一样
  TESTTIME        time       3      分子项目会用对数值画图   
  TESTNUM         int        4      数据输入人员工号ID       仪器传入默认AOTO
  INSTID          int        4      数据输入人员姓名         仪器传入默认AOTO
  TECHNO          varchar    20     数据状态                 0 有效 1 失效
  LOTID           int        4      质控结果状态             -1 初始值 ,判断 0 在控 1 警告 2 失控
  ITEMCODE        varchar    20     违反多规则               (是多个规则组合，逗号分开)
  QC3SD           int        4      3SD标志                  0 未超过 1 超过3SD值
  CHECKBYCODE     varchar    20     审核者工号ID             
  CHECKBYNAME     varchar    20     审核者姓名               
  CHECKTIME       datetime   8      审核时间                 
  LOCKFLAG        char       1      数据锁定状态             0 数据未锁 1 数据已锁
  OPERCODE        vARCHAR    20     操作员代码               
  OPERNAME        vARCHAR    40     操作员姓名               
  OPERDATE        DATETIME   8      操作日期                 
  OPERTYPE        INT               操作类型                 0新增,1修改，2删除
  --------------- ---------- ------ ------------------------ ---------------------------------------------------------------------

### 失控处理 QC\_LOSECONTROL

  -------------------- ---------- ------ ------------------ -------------------
  字段名               数据类型   长度   作用               说明
  REJECTNO             int        4      流水号             
  RESULTNO             int        4      关联结果表         
  REJECTYPE            int        4      操作类型           
  REJECTDESC           varchar    500    失控描述           
  REJECTREASON         varchar    500    失控原因分析       
  CORRECTACTION        varchar    500    纠正处理措施       
  CORRECTRESULT        varchar    500    纠正处理结果       
  REvIEWADVICE         varchar    500    纠正措施评价       
  CORRECTOTHER         varchar    500    失控影响范围分析   
  PREMUNITION          varchar    500    预防措施           
  PREMUNITIONEXCUTE    varchar    500    预防措施执行情况   
  ENVICONDITION        varchar    500    环境条件           
  OPERATORCODE         varchar    20     操作人             
  OPERATORNAME         varchar    20     操作名称           
  OPERATORDATE         datetime   8      操作时间           
  CALIBRATORNAME       varchar    50     校准品信息         
  CALIBRATORBATCHNO    varchar    50     校准品批号         
  CALIBRATORDATE       date       3      校准品有效期       
  CALIBRATOROPENDATE   date       3      校准品开瓶日期     
  CALIBRATOROPENEXPD   date       3      校准品开瓶效期     
  LOTNAME              varchar    50     质控品信息         
  LOTBATCHNO           varchar    50     质控品批号         
  LOTDATE              date       3      质控品有效期       
  LOTOPENDATE          date       3      质控品开瓶日期     
  LOTOPENEXPDATE       date       3      质控品开瓶效期     
  REAGENTNAME          varchar    50     试剂信息           
  REAGENTBATCHNO       varchar    50     试剂批号           
  REAGENTDATE          date       3      试剂有效期         
  REAGENTOPENDATE      date       3      试剂开瓶日期       
  REAGENTOPENEXPDATE   date       3      试剂开瓶效期       
  CHECKSTATUS          int        4      失控处理审核状态   0 未审核 1 已审核
  CHECKBYCODE          varchar    20     审核者工号ID       
  CHECKBYNAME          varchar    20     审核者姓名         
  CHECKTIME            datetime   8      审核时间           
  -------------------- ---------- ------ ------------------ -------------------

### 失控处理明细表 QC\_LOSECONTROLDETAILS

  -------------- ---------- ------ ---------------- --------------------------------------
  字段名         数据类型   长度   作用             说明
  DETAILNO       int        4      关联失控处理表   
  REJECTNO       int        4      关联质控结果表   
  RESULTNO       int        4      字段类型         对应HTML标签类型 如 radio checkbox等
  FIELDCODE      varchar    50     字段代码         
  FIELDNAME      varchar    50     字段名称         
  FIELDCONTENT   varchar    1000   字段内容         
  -------------- ---------- ------ ---------------- --------------------------------------

### 质控即刻法结果信息 QC\_GRUBBS

  --------------- ---------- ------ ------------ ----------------------
  字段名          数据类型   长度   作用         说明
  RESULTNO        int        4      ID           
  LOTITEMID       int        4      测试项目ID   
  LOTID           int        4      水平ID       
  ITEMCODE        varchar    20     项目代码     
  RECEIVEDATE     datetime   8      接收日期     
  RECEIVETIME     datetime   8      接收时间     
  TESTNUM         int        4      当日次数     
  ORIGINALVALUE   numeric    9      原始数据     
  USERVALUE       numeric    9      用户数据     
  STATUS          int        4      数据状态     0 有效，1报警，2失控
  OPERATOR        varchar    20     操作员工号   
  OPERATORNAME    varchar    50     操作员姓名   
  --------------- ---------- ------ ------------ ----------------------

### 质控行为记录 QC\_RECORDDETAILS

  -------------- ---------- ------ ------------ ------
  字段名         数据类型   长度   作用         说明
  SERIALNO       int        4      仪器ID       
  INSTID         int        4      项目代码     
  ITEMCODE       varchar    20     创建日期     
  CREATEDATE     datetime   8      行为ID       
  ACTION\_ID     datetime   8      行为类型     
  ACTION\_TYPE   varchar    50     备注内容     
  REMARK         varchar    200    操作员工号   
  OPERATOR       varchar    20     操作员姓名   
  OPERATORNAME   varchar    50     仪器ID       
  -------------- ---------- ------ ------------ ------

### 质量目标TEA\--字典表 QC\_ITEMTEA\_DIC

  ------------ ---------- ------ ---------------- ------
  字段名       数据类型   长度   作用             说明
  SERIALNO     int        4      流水号           
  ITEMCODE     varchar    20     项目代码         
  ITEMNUM      varchar    20     项目编码         
  ITEMNAME     varchar    50     项目名称         
  ITEMUNIT     varchar    20     项目单位         
  MATRIXCODE   varchar    20     基质代码         
  MATRIXNAME   varchar    50     基质名称         
  SCHEME       varchar    200    TEA类型,         
  TEACV        varchar    20     TEA百分比        
  TEASD        varchar    20     TEA绝对值        
  CV           varchar    20     允许精密度Cv     
  BIAS         varchar    20     允许正确度BIAS   
  ------------ ---------- ------ ---------------- ------

### 仪器项目质量目标表 QC\_ITEMTEA

  ------------- ---------- ------ ------------------ ------
  字段名        数据类型   长度   作用               说明
  SERIALNO      int        4      流水号             
  INSTID        int        4      仪器代码           
  ITEMCODE      varchar    20     项目代码           
  ITEMNUM       varchar    20     项目编码           
  ITEMNAME      varchar    50     项目名称           
  ITEMUNIT      varchar    20     项目单位           
  SCHEME        varchar    200    TEA类型,           
  TEACV         varchar    20     TEA百分比          
  TEASD         varchar    20     TEA绝对值          
  CV            varchar    10     允许精密度Cv       
  CV1           varchar    10     允许Cv（低水平）   
  CV2           varchar    10     允许Cv（中水平）   
  CV3           varchar    10     允许Cv（高水平）   
  SD            varchar    10     允许正确度BIAS     
  SIGMA         varchar    10     西格玛值           
  DICITEMCODE   varchar    20     字典ITEMCODE       
  ------------- ---------- ------ ------------------ ------

微生物系统
----------

### WHONET标本种类表 MIS\_WHONET\_SAMPLE

  ------------- ---------- ------ -------------------- ------
  字段名        数据类型   长度   作用                 说明
  SAMPLECODE    VARCHAR    50     中文名称             
  SAMPLENAME    VARCHAR    50     中文名称             
  SAMPLENUM     VARCHAR    50     标准编码，英文缩写   
  ENGLISHNAME   VARCHAR    100    英文名称             
  ------------- ---------- ------ -------------------- ------

### WHONET部门分类表 MIS\_WHONET\_DEPTTYPE

  ------------- ---------- ------ ---------- ------
  字段名        数据类型   长度   作用       说明
  DEPTCODE      VARCHAR    50     中文名称   
  DEPTNAME      VARCHAR    50     中文名称   
  SAMPLENUM     VARCHAR    50     标准编码   
  ENGLISHNAME   VARCHAR    100    英文名称   
  ------------- ---------- ------ ---------- ------

### WHONET细菌表 MIS\_WHONET\_ORG

  ------------- ---------- ------ ---------- ------
  字段名        数据类型   长度   作用       说明
  ID            INT               ID         
  ORGCODE       VARCHAR    3      细菌代码   
  ENGLISHNAME   VARCHAR    150    细菌名称   
  ORGNAME       VARCHAR    150    细菌名称   
  GRAMCODE      VARCHAR    1      革兰氏     
  GROUPCODE     VARCHAR    10     细菌分组   
  SUB\_GROUP    VARCHAR    10     子分组     
  GENUSCODE     VARCHAR    30     菌属代码   
  GENUS         VARCHAR    150    菌属名称   
  STATUS        VARCHAR    1      系统状态   
  ------------- ---------- ------ ---------- ------

### WHONET细菌分类表 MIS\_WHONET\_ORGTYPE

  ------------- ---------- ------ -------------- ----------------------------------
  字段名        数据类型   长度   作用           说明
  ORGTYPECODE   VARCHAR    60     类别代号       
  ORGTYPENAME   VARCHAR    125    类别中文名称   
  ENGLISHNAME   VARCHAR    100    类别英文名     
  WHONETCODE    VARCHAR    20     WHONET代码     
  NTYPE         INT               分类等级       1 一级分类 2 二级分类 3 三级分类
  MEMCODE1      VARCHAR    20     助记符         
  MEMCODE2      VARCHAR    20     助记符         
  ORDERNO       INT                              
  ------------- ---------- ------ -------------- ----------------------------------

### WHONET抗生素表 MIS\_WHONET\_ANTI

  --------------- ---------- ------ -------------------------- ------
  字段名          数据类型   长度   作用                       说明
  WHON5\_CODE     VARCHAR    60     抗生素代码                 
  ANTIBIOTIC      VARCHAR    125    抗生素中文名称             
  ENGLISHNAME     VARCHAR    100    抗生素英文名称             
  GUIDELINES      VARCHAR    50     指导方针 CLSI 或 EUCAST    
  CLSI            VARCHAR    20     指导方针 CLSI机构 使用     
  EUCAST          VARCHAR    20     指导方针 EUCAST机构 使用   
  POTENCY         VARCHAR    40     抗生素浓度。               
  BETALACTAM      VARCHAR    20     Β-内酰胺类抗生素 标志      
  CLASSCODE       VARCHAR    50     1 一级分类                 
  SUBCLASS        VARCHAR    50     2 二级分类                 
  PROF\_CLASS     VARCHAR    50     3 三级分类                 
  CLSI\_ORDER     VARCHAR    20     显示排序                   
  HUMAN           VARCHAR    20     人类使用                   
  VETERINARY      VARCHAR    20     动物使用                   
  WHONET\_DISK    VARCHAR    20     WHONET代码 DISK代码        
  WHONET\_MIC     VARCHAR    20     WHONET代码 MIC代码         
  WHONET\_ETEST   VARCHAR    20     WHONET代码 ETEST代码       
  LOINC\_DISK     VARCHAR    20     LOINC代码 DISK代码         
  LOINC\_MIC      VARCHAR    20     LOINC代码 MIC代码          
  LOINC\_ETEST    VARCHAR    20     LOINC代码 ETEST代码        
  DOSAGE1         VARCHAR    200    剂量及用法一               
  DOSAGE2         VARCHAR    200    剂量及用法二               
  BLOODTHICK1     VARCHAR    100    血药浓度一                 
  BLOODTHICK2     VARCHAR    100    血药浓度二                 
  URINETHICK1     VARCHAR    100    尿药浓度一                 
  URINETHICK2     VARCHAR    100    尿药浓度二                 
  STATUS          VARCHAR    1      状态                       
  UPDATETIEM      DATETIME          更新时间                   
                                                               
  --------------- ---------- ------ -------------------------- ------

### WHONET抗生素折点表 MIS\_WHONET\_ANTIREFER

  ------------- ---------- ------ ---------------------------- ------
  字段名        数据类型   长度   作用                         说明
  GUIDELINES    VARCHAR    60     折点指南版本                 
  WHON5\_CODE   VARCHAR    20     抗生素代码                   
  POTENCY       VARCHAR    40     抗生素浓度                   
  TESTMETHOD    VARCHAR    125    方法名称                     
  ORGCODE       VARCHAR    20     细菌代码                     
  ORGTYPE       VARCHAR    2      细菌代码类型 -1 默认参考值   
  DISK\_R       VARCHAR    20     DISK 耐药值 DISK R\<=2       
  DISK\_I       VARCHAR    20     DISK 中介值 DISK 中间值2-1   
  DISK\_S       VARCHAR    20     DISK 敏感值 DISK S\>=10      
  MIC\_S        VARCHAR    20     MIC 敏感值 MIC 方法 S\<=2    
  MIC\_I        VARCHAR    20     MIC 中介值 MIC 有SDD值       
  MIC\_R        VARCHAR    20     MIC 耐药值 MIC 方法 R\>=10   
  ------------- ---------- ------ ---------------------------- ------

### WHONET抗生素分类表 MIS\_WHONET\_ANTITYPE

  -------------- ---------- ------ ---------------------------------- ------
  字段名         数据类型   长度   作用                               说明
  ANTITYPECODE   VARCHAR    50     类别代号                           
  ANTITYPENAME   VARCHAR    100    类别中文名称                       
  ENGLISHNAME    VARCHAR    100    类别英文名                         
  WHONETCODE     VARCHAR    50     WHONET代码                         
  NTYPE          INT               1 一级分类 2 二级分类 3 三级分类   
  ORDERNO        INT                                                  
  -------------- ---------- ------ ---------------------------------- ------

### WHONET质控菌 MIS\_WHONET\_QCRANGE

  ------------- ---------- ------ ------------------ ------
  字段名        数据类型   长度   作用               说明
  GUIDELINE     VARCHAR    60     折点指南版本       
  GUIDELINES    VARCHAR    60     折点指南           
  ORGANISM      VARCHAR    20     标准质控菌株代码   
  STRAIN        VARCHAR    50     菌株编码           
  REF\_TABLE    VARCHAR    20     鉴定卡             
  WHON5\_CODE   VARCHAR    20     抗生素代码         
  METHOD        VARCHAR    60     方法名称           
  ABX\_TEST     VARCHAR    60     ABX抗生素代码      
  MEDIUM        VARCHAR    60     方法               
  QC\_RANGE     VARCHAR    20     质控范围           
  LOWERLIMIT    VARCHAR    20     质控下限           
  UPPERLIMIT    VARCHAR    20     质控范围           
  ------------- ---------- ------ ------------------ ------

### 细菌类别MIS\_ORGANISMTYPE 

  -------------- ---------- ------ --------------------------------- ------
  字段名         数据类型   长度   作用                              说明
  HOSPITALCODE   VARCHAR    64     机构代码                          
  ORGTYPECODE    VARCHAR    60     类别代号                          
  ORGTYPENAME    VARCHAR    125    类别中文名称                      
  ENGLISHNAME    VARCHAR    100    类别英文名                        
  WHONETCODE     VARCHAR    20     WHONET代码                        
  NTYPE          INT               1一级分类 2 二级分类 3 三级分类   
  MEMCODE1       VARCHAR    20     助记符                            
  MEMCODE2       VARCHAR    20     助记符                            
  UPLINK         VARCHAR    60     指向ORGTYPECODE，构成树形结构     
  ORDERNO        INT               排序                              
  -------------- ---------- ------ --------------------------------- ------

### 细菌字典表MIS\_ORGANISM 

  -------------- ---------- ------ ----------------------------------- ------
  字段名         数据类型   长度   作用                                说明
  HOSPITALCODE   VARCHAR    64     机构代码                            
  ORGCODE        VARCHAR    60     细菌代号                            
  ENGLISHNAME    VARCHAR    100    细菌英文名                          
  SHORTNAME      VARCHAR    60     细菌简称                            
  ORGNAME        VARCHAR    200    细菌中文名                          
  MEMCODE1       VARCHAR    20     助记符                              
  MEMCODE2       VARCHAR    20     助记符                              
  GRAMCODE       VARCHAR    20     革兰氏分类                          
  GROUPCODE      VARCHAR    20     菌组分类                            
  GENUSCODE      VARCHAR    20     菌属分类                            
  WHONETCODE     VARCHAR    10     WHONET代码                          
  LOINCCODE      VARCHAR    10     LOINCCODE代码                       
  SENSIFLAG      INT               0鉴定结果未细菌，=1鉴定结果是细菌   
  STOPFLAG       CHAR       1      停用标志 0-正常 1-停用              
  -------------- ---------- ------ ----------------------------------- ------

### 抗生素类别MIS\_ANTIBIOTICTYPE

  -------------- ---------- ------ ---------------------------------- ------
  字段名         数据类型   长度   作用                               说明
  HOSPITALCODE   VARCHAR    64     机构代码                           
  ANTITYPECODE   VARCHAR    50     类别代号                           
  ANTITYPENAME   VARCHAR    125    类别中文名称                       
  ENGLISHNAME    VARCHAR    100    类别英文名                         
  WHONETCODE     VARCHAR    50     WHONET代码                         
  NTYPE          INT               1 一级分类 2 二级分类 3 三级分类   
  MEMCODE1       VARCHAR    20     助记符                             
  MEMCODE2       VARCHAR    20     助记符                             
  ORDERNO        INT               排序                               
  -------------- ---------- ------ ---------------------------------- ------

### 抗生素药物表MIS\_ANTIBIOTIC

  --------------- ---------- ------ ----------------------------- ------
  字段名          数据类型   长度   作用                          说明
  HOSPITALCODE    VARCHAR    64     机构代码                      
  ANTICODE        VARCHAR    60     抗生素代码                    
  ENGLISHNAME     VARCHAR    100    抗生素英文名                  
  SHORTNAME       VARCHAR    60     简称                          
  ANTINAME        VARCHAR    200    抗生素中文名                  
  POTENCY         VARCHAR    50     抗生素浓度                    
  BETALACTAM      CHAR       1      Β-内酰胺类抗生素 0 不是 1是   
  CLASSCODE       VARCHAR    50     一级分类                      
  SUBCLASS        VARCHAR    50     二级分类                      
  PROF\_CLASS     VARCHAR    50     三级分类                      
  ORDERNO         VARCHAR           抗生素显示顺序                
  WHONET\_DISK    VARCHAR    20     WHONET DISK法代码             
  WHONET\_MIC     VARCHAR    20     WHONET MIC法代码              
  WHONET\_ETEST   VARCHAR    20     WHONET ETEST法代码            
  LOINC\_DISK     VARCHAR    20     LOINC DISK法代码              
  LOINC\_MIC      VARCHAR    20     LOINC MIC法代码               
  LOINC\_ETEST    VARCHAR    20     LOINC ETEST法代码             
  DOSAGE1         VARCHAR    200    剂量及用法一                  
  DOSAGE2         VARCHAR    200    剂量及用法二                  
  BLOODTHICK1     VARCHAR    100    血药浓度一                    
  BLOODTHICK2     VARCHAR    100    血药浓度二                    
  URINETHICK1     VARCHAR    100    尿药浓度一                    
  URINETHICK2     VARCHAR    100    尿药浓度二                    
  MEMCODE1        VARCHAR    20     助记符                        
                                                                  
                                                                  
                                                                  
  --------------- ---------- ------ ----------------------------- ------

### 抗生素折点信息表MIS\_ANTITURNING

  -------------- ---------- ------ ---------------- ------
  字段名         数据类型   长度   作用             说明
  SERIALNO       INT               主键ID           
  HOSPITALCODE   VARCHAR    64     机构代码         
  ANTICODE       VARCHAR    20     抗生素代码       
  TESTMETHOD     VARCHAR    125    方法名称         
  ORGCODE        VARCHAR    20     细菌代码         
  ORGTYPE        VARCHAR    4      细菌代码类型     
  SAMPLECODES    VARCHAR    1000   标本种类集       
  SAMPLENAMES    VARCHAR    1000   标本种类集       
  GUIDELINES     VARCHAR    60     折点指南版本     
  DISK\_R        VARCHAR    20     DISK 耐药值      
  DISK\_I        VARCHAR    20     DISK 中介值      
  DISK\_S        VARCHAR    20     DISK 敏感值      
  DISK\_REFER    VARCHAR    60     DISK 参考范围    
  MIC\_S         VARCHAR    20     MIC 敏感值       
  MIC\_I         VARCHAR    20     MIC 中介值       
  MIC\_R         VARCHAR    20     MIC 耐药值       
  MIC\_REFER     VARCHAR    60     MIC 参考范围     
  MIC\_SDD       VARCHAR    20     剂量依赖性敏感   
  DISK\_SDD      VARCHAR    20     剂量依赖性敏感   
  MIC\_WT        VARCHAR    20     野生型折点       
  DISK\_WT       VARCHAR    20     野生型折点       
  SERIALNO       INT               主键ID           
  -------------- ---------- ------ ---------------- ------

### 涂片方法表MIS\_SMEARMETHOD

  --------------- ---------- ------ -------------------------------------- ------
  字段名          数据类型   长度   作用                                   说明
  HOSPITALCODE    VARCHAR    64     机构代码                               
  METHODCODE      VARCHAR    20     涂片方法代码                           
  METHODNAME      VARCHAR    64     涂片方法名称                           
  MEMCODE1        VARCHAR    20     助记符                                 
  MEMCODE2        VARCHAR    20     助记符                                 
  LABELPREFIX     VARCHAR    2      标签前缀                               
  LABELSUFFIX     VARCHAR    2      标签后缀                               
  LABELLENGTH     INT               标签长度                               
  CULTUREFLAG     CHAR       1      培养使用标志 0-初鉴不使用 1-初鉴使用   
  TENTATIVEFLAG   CHAR       1      初鉴使用标志 0-鉴定不使用 1-鉴定使用   
  REMARK          VARCHAR    255    备注                                   
  STOPFLAG        CHAR       1      停用标志 0-正常 1-停用                 
                                                                           
  --------------- ---------- ------ -------------------------------------- ------

### 耐药机制判断表MIS\_ORGANISMHINTDTL

  -------------- ---------- ---------- ---------------- ------
  字段名         数据类型   长度       作用             说明
  ORGHINTID      INT        \#VALUE!   流水号           
  HOSPITALCODE   VARCHAR    64         机构代码         
  MDRCODE        VARCHAR    20         耐药机制代码     
  MDRNAME        VARCHAR    100        耐药机制名称     
  MDRTYPE        VARCHAR    50         耐药机制类型     
  ESBLS          CHAR       1          ESBLS标志        
  PANICFLAG      CHAR       1          危急值标志       
  HINTMEMO       VARCHAR    500        提示备注说明     
  SAMPLECODE     VARCHAR    100        标本种类代码     
  SAMPLENAME     VARCHAR    500        标本种类名称     
  ORGTYPE        INT                   细菌代码类型     
  ORGCODE        VARCHAR    500        细菌或菌属代码   
  ORGNAME        VARCHAR    1000       细菌或菌属名称   
  CHECKTYPE      INT                   校验类型         
  RELATION       INT                   条件设置         
  REFERNUM       INT                   校验抗生素数量   
  STOPFLAG       CHAR       1          停用标志         
  -------------- ---------- ---------- ---------------- ------

### 培养基信息表MIS\_CULTUREMEDIUM

  ------------------- ---------- ------ ------------ ------
  字段名              数据类型   长度   作用         说明
  HOSPITALCODE        VARCHAR    50     机构代码     
  CULTUREMEDIUMCODE   VARCHAR    20     培养基编码   
  CULTUREMEDIUMNAME   VARCHAR    64     培养基名称   
  CULTUREMEDIUMTYPE   VARCHAR    20     培养基类型   
  SHORTNAME           VARCHAR    20     简称         
  MEMCODE1            VARCHAR    20     助记符       
  MEMCODE2            VARCHAR    20     助记符       
  CONDITION           VARCHAR    64     培养环境     
  TEMPERATURE         VARCHAR    64     培养温度     
  TEMPERATUREUNIT     VARCHAR    10     温度单位     
  PERIOD              VARCHAR    64     培养周期     
  PERIODUNIT          VARCHAR    10     周期单位     
  HUMIDITY            VARCHAR    64     湿度         
  LABELPREFIX         VARCHAR    6      标签前缀     
  LABELSUFFIX         VARCHAR    6      标签后缀     
  LABELLENGTH         INT               标签长度     
  MEDIUMCOLOR         VARCHAR    20     培养基颜色   
  REMARK              VARCHAR    255    备注         
  STOPFLAG            CHAR       1      停用标志     
  ------------------- ---------- ------ ------------ ------

### 流程方案设定表MIS\_FLOW\_SCHEME

  ---------------- ---------- ------ ---------------------------------- ------
  字段名           数据类型   长度   作用                               说明
  SCHEMEID         INT               流水号                             
  HOSPITALCODE     VARCHAR    50     机构代码                           
  INSTID           INT               仪器ＩＤ                           
  LISORDERCODE     VARCHAR    20     收费代码                           
  SCHEMENAME       VARCHAR    64     方案名称                           
  SAMPLECODE       VARCHAR    255    标本代码集合                       
  SAMPLENAME       VARCHAR           标本名称集合                       
  EXAMGOALCODE     VARCHAR    20     送检目的代码                       
  WORKGROUPCODE    VARCHAR    20     微生物工作组CODE                   
  WORKGROUPNAME    VARCHAR    1000   微生物工作组                       
  BARPREFIX        VARCHAR    20     样本号前缀                         
  BARLENGTH        INT               样本号长度                         
  BEGINNUM         INT               起始位                             
  REPORTTIME       INT               预计报告时间                       
  TIMEUNIT         VARCHAR    20     预计报告时间单位                   
  OBSERVE          CHAR       1      涂片镜检-检前                      
  INSTVACCINATE    CHAR       1      上机培养                           
  VACCINATE        CHAR       1      接种培养                           
  TRANSVACCINATE   CHAR       1      转种培养                           
  TENTATIVE        CHAR       1      涂片镜检-初鉴                      
  APPRAISAL        CHAR       1      鉴定/药敏                          
  STOPFLAG         CHAR       1      停用标志 0-正常 1-停用             
  AUTOPRINTFLAG    CHAR       1      自动打印 1-自动打印 0-不自动打印   
  REMARK           VARCHAR    255    备注                               
                                                                        
  ---------------- ---------- ------ ---------------------------------- ------

### 质控药敏测试表MIS\_QC\_TEST\_ANTI

  ---------------- ---------- ------ -------------- ------
  字段名           数据类型   长度   作用           说明
  QCTESTANTIID     INT               流水号         
  QCTESTID         INT               质控测试ID     
  ANTICODE         VARCHAR    20     药敏细菌代码   
  ANTINAME         VARCHAR    100    药敏细菌名称   
  GUIDELINE        VARCHAR    64     指南           
  TESTMETHODCODE   VARCHAR    20     药敏方法       
  POTENCY          VARCHAR    40     抗生素浓度     
  LOWLIMIT         NUMERIC    20,4   参考下限       
  UPPLIMIT         NUMERIC    20,4   参考上限       
  REFERDESC        VARCHAR    255    参考范围描述   
  ORDERNO          INT               显示顺序       
  STOPFLAG         CHAR       1      停用标志       
  ---------------- ---------- ------ -------------- ------

### 微生物主表信息MIS\_SAMPLE

  ------------------- ------------ ------ ---------------------------------------------------------------------------------------------- ------
  字段名              数据类型     长度   作用                                                                                           说明
  HOSPITALCODE        VARCHAR      50     医疗机构代码                                                                                   
  APPLYNO             NUMERIC      18,0   报告单号                                                                                       
  EXECTIME            DATETIME            检测日期，格式：YYYY-MM-DD                                                                     
  INSTID              INT                 仪器ID                                                                                         
  INSTCODE            VARCHAR      20     仪器代码                                                                                       
  INSTNAME            VARCHAR      64     仪器名称                                                                                       
  SAMPLETYPE          CHAR         2      样本分组                                                                                       
  TECHNO              VARCHAR      20     样本号                                                                                         
  CURENO              VARCHAR      64     就诊号                                                                                         
  PATIENTID           VARCHAR      64     病人唯一ID                                                                                     
  CARDTYPE            VARCHAR      10     卡类型                                                                                         
  CARDNO              VARCHAR      64     磁卡号                                                                                         
  HOSPNO              VARCHAR      64     住院号或门诊号                                                                                 
  PATNAME             VARCHAR      64     患者姓名                                                                                       
  EMPIID              VARCHAR      32     EMPI主索引ID                                                                                   
  IMECODE             VARCHAR      64     拼音码                                                                                         
  SEX                 VARCHAR      10     性别 1-男 2-女 3-未知                                                                          
  SEXDESC             VARCHAR      10     性别文字描述 男 、女                                                                           
  BIRTHDAY            DATETIME            出生日期                                                                                       
  AGE                 INT                 年龄                                                                                           
  AGEUNIT             VARCHAR      6      年龄单位，如：天、月、岁                                                                       
  AGE2                INT                 年龄2                                                                                          
  AGEUNIT2            VARCHAR      6      年龄单位2，如：天、月                                                                          
  AGEDESC             VARCHAR      20     年龄描述，如：23岁、1 月10天等                                                                 
  INVOICENO           VARCHAR      32     发票号                                                                                         
  IDNUM               VARCHAR      20     身份证号                                                                                       
  INWARDTIMES         INT                 住院次数                                                                                       
  BABYFLAG            INT                 婴儿标志 0-非婴儿 1-婴儿                                                                       
  CHARGETYPE          VARCHAR      20     费别代码                                                                                       
  CHARGETYPENAME      VARCHAR      64     费别名称                                                                                       
  WARDORREG           VARCHAR      20     病人类别代码                                                                                   
  WARDORREGNAME       VARCHAR      64     病人类别名称                                                                                   
  BRLX                VARCHAR      10     HIS病人类型：0-门诊 1-住院 3、4-体检                                                           
  WARD                VARCHAR      20     病区代码                                                                                       
  WARDNAME            VARCHAR      64     病区名称                                                                                       
  BEDNO               VARCHAR      20     床号，如：+5床                                                                                 
  CLINICDESC          VARCHAR      500    临床诊断 中文描述                                                                              
  ICD10               VARCHAR      20     ICD10编码                                                                                      
  ICDNAME             VARCHAR      255    ICD10名称                                                                                      
  M\_PHONE            VARCHAR      32     联系电话                                                                                       
  ADDRESS             VARCHAR      255    联系地址                                                                                       
  NATION              VARCHAR      64     民族 中文描述 如:汉族                                                                          
  YEXH                VARCHAR      20     婴儿序号                                                                                       
  TIMES               INT                 住院次数                                                                                       
  PATTYPE             VARCHAR      20     患者标识1-疑似新型肺炎                                                                         
  PATTYPEHINT         VARCHAR      64     患者标识提示信息                                                                               
  ORGAPPLYNO          VARCHAR      20     条形码，标本条码                                                                               
  APPLYDEPTCODE       VARCHAR      20     申请科室代码                                                                                   
  APPLYDEPTNAME       VARCHAR      64     申请科室名称                                                                                   
  APPLYDOCCODE        VARCHAR      20     申请医生代码                                                                                   
  APPLYDOCNAME        VARCHAR      64     申请医生姓名 也称：送检医生                                                                    
  SAMPLECODE          VARCHAR      20     标本种类代码                                                                                   
  SAMPLENAME          VARCHAR      64     标本种类名称                                                                                   
  SAMPLEDESCCODE      VARCHAR      20     标本说明代码                                                                                   
  SAMPLEDESCNAME      VARCHAR      40     标本说明名称                                                                                   
  APPLYTIME           DATETIME            申请时间 格式：YYYY-MM-DD HH:MM:SS                                                             
  SAMPLETIME          DATETIME            采样时间 格式：YYYY-MM-DD HH:MM:SS                                                             
  WORKERSIGNTIME      DATETIME            护工签收时间 格式：YYYY-MM-DD HH:MM:SS                                                         
  RECEIVETIME         DATETIME            检验科接收、签收时间 格式：YYYY-MM-DD HH:MM:SS                                                 
  GROUPSIGNTIME       DATETIME            小组签收 格式：YYYY-MM-DD HH:MM:SS                                                             
  GROUPSECSIGNTIME    DATETIME            小组二级签收时间 格式：YYYY-MM-DD HH:MM:SS                                                     
  PATPROPCODE         VARCHAR      20     病人特征代码                                                                                   
  PATPROPNAME         VARCHAR      64     病人特征名称                                                                                   
  COLLECTPARTCODE     VARCHAR      20     采集部位代码                                                                                   
  COLLECTPARTNAME     VARCHAR      64     采集部位名称                                                                                   
  VACCINATETIME       DATETIME            接种时间                                                                                       
  REGISTERCODE        VARCHAR      20     登记医生代码                                                                                   
  REGISTERNAME        VARCHAR      64     登记医生姓名                                                                                   
  REGISTERTIME        DATETIME            登记时间/检验开始时间                                                                          
  REGISTERPCNAME      VARCHAR      255    登记计算机名 格式：计算机名                                                                    
  EXECDEPTCODE        VARCHAR      20     执行科室代码                                                                                   
  EXECDEPTNAME        VARCHAR      64     执行科室名称                                                                                   
  EXECDOCCODE         VARCHAR      20     检验医生代码                                                                                   
  EXECDOCNAME         VARCHAR      64     检验医生姓名                                                                                   
  EXAMGOAL            VARCHAR      20     送检目的代码                                                                                   
  EXAMGOALNAME        VARCHAR      64     送检目的名称                                                                                   
  STATUS              INT                 报告状态 10-初始报告 20-进行中 30-待审 40-初审通过 45-审核不通过 50-终审通过 60-发布           
  NOPASSREASON        VARCHAR      255    审核不通过原因                                                                                 
  PREVERIFIERCODE     VARCHAR      20     初审医生代码                                                                                   
  PREVERIFIERNAME     VARCHAR      64     初审医生姓名                                                                                   
  PREVERIFYPCNAME     VARCHAR      255    初审计算机名称 格式：计算机名                                                                  
  PREVERIFYDATE       DATETIME            初审时间 格式：YYYY-MM-DD HH:MM:SS                                                             
  VERIFIERCODE        VARCHAR      20     终审医生代码                                                                                   
  VERIFIERNAME        VARCHAR      64     终审医生姓名                                                                                   
  VERIFYPCNAME        VARCHAR      255    终审计算机名称 格式：计算机名                                                                  
  VERIFYDATE          DATETIME            终审时间 格式：YYYY-MM-DD HH:MM:SS                                                             
  REPORTTIME          DATETIME            报告时间 格式：YYYY-MM-DD HH:MM:SS                                                             
  PUBCODE             VARCHAR      20     发布人代码                                                                                     
  PUBNAME             VARCHAR      64     发布人姓名                                                                                     
  PUBDATETIME         DATETIME            报告发布时间 格式：YYYY-MM-DD HH:MM:SS                                                         
  PRINTSTATUS         INT                 报告打印状态 0-未打印 1-已打印                                                                 
  PRINTCOUNT          INT                 自助机打印次数 0-自助机未打印 \>0-自助机已打印                                                 
  PRINTCOMPUTER       VARCHAR      255    最近一次打印的计算机名 格式：计算机名                                                          
  PRINTBYCODE         VARCHAR      20     最近一次打印人的工号，自助机打印为空                                                           
  PRINTBYNAME         VARCHAR      64     最近一次打印人的姓名，自助机打印为\"自助打印\"                                                 
  PRINTDATE           DATETIME            最近一次打印报告的时间 格式：YYYY-MM-DD HH:MM:SS                                               
  MJZBZ               INT                 门急诊标志 0-常规 1-加急                                                                       
  REDO                INT                 复做标志 0-未复做 1-复做                                                                       
  REDOSAMPLE          CHAR         1      标本来源 0-当前标本 1-重新采样                                                                 
  REDOSAMPLEDESC      VARCHAR      255    复做标本说明 中文描述                                                                          
  REDOREASON          VARCHAR      255    复做原因 中文描述                                                                              
  MOBILESTATUS        INT                 短信或移动通知 0-未通知 1-已通知                                                               
  SCOPICFLAG          INT                 镜检复片标志 1-镜检复片                                                                        
  PANICFLAG           INT                 危急值报告 0-正常 1-危急值报告                                                                 
  BIOHAZARDFLAG       INT                 传染病标志 0-正常 1-传染病报告                                                                 
  MDRFLAG             INT                 多重耐药标志 0-正常 1-多重耐药细菌                                                             
  TATVOILATE          INT                 是否违反TAT规则 0:正常 1:违反TAT规则 9-违反TAT规则，但不参与统计                               
  ESTREPORTTIME       DATETIME            预计出报告时间 格式：YYYY-MM-DD HH:MM:SS                                                       
  REPORTONTIME        INT                 是否按时出报告 -1-不做判断 0-未按时出报告 1-按时出报告                                         
  DELAYRPTFLAG        INT                 延时取报告标志                                                                                 
  DELAYRPTREASON      VARCHAR      255    延迟取报告原因                                                                                 
  DELAYRPTTIME        VARCHAR      64     延迟到出报告时间 可能是具体时间，也可能是文字描述                                              
  DELAYRPTBYCODE      VARCHAR      20     延迟取报告操作人代码                                                                           
  DELAYRPTBYNAME      VARCHAR      64     延迟取报告操作人姓名                                                                           
  DELAYRPTDATE        DATETIME            延迟取报告操作人时间 格式：YYYY-MM-DD HH:MM:SS                                                 
  EXAMRESULT          VARCHAR      1000   检验印象                                                                                       
  REMARK              VARCHAR      1000   检验备注                                                                                       
  LABDIAGNOSIS        VARCHAR      1000   实验室诊断最终的建议和解释                                                                     
  RESULTANALYZE       VARCHAR      1000   系统分析结果计算出来的建议与解释，需要用户确认采纳                                             
  ANALYZEACCEPT       CHAR                系统分析结果采纳标志 0-未采纳 1-已采纳 2-不采纳 标志为1或2时表示用户已确认，系统不再进行分析   
  SCIENTIFICFLAG      INT                 科研标志                                                                                       
  SAMPLESHAPECODE     VARCHAR      20     标本性状代码                                                                                   
  SAMPLESHAPENAME     VARCHAR      64     标本性状名称                                                                                   
  SECTION             VARCHAR      20     架号                                                                                           
  CUP                 VARCHAR      20     杯号                                                                                           
  TESTPOSITION        VARCHAR      50     上机仪器检测位置坐标，也称作杯架号                                                             
  ARCHIVINGPOSITION   VARCHAR      50     标本归档位置，后处理中，标本检测完后，归档到的位置                                             
  HISORDERCODE        VARCHAR      500    检测项目代码 多个用逗号分隔                                                                    
  HISORDERNAME        VARCHAR      4000   检测项目名称 多个用逗号分隔                                                                    
  CHARGEAMOUNT        NUMERIC      9,2    收费金额 费用操作时需更新                                                                      
  EXAMCODE            VARCHAR      20     检验分类代码                                                                                   
  EXAMNAME            VARCHAR      64     检验分类名称                                                                                   
  REPORTTITLE         VARCHAR      64     报告单标题                                                                                     
  INSTVERIFYFLAG      CHAR         1      仪器审核标志                                                                                   
  INSTVERIFYCONTENT   VARCHAR      500    仪器审核说明                                                                                   
  AUTORULEFLAG        CHAR         1      LIS自动审核标志 0-无规则判断 1-自动审核规则判断通过 2-自动审核规则判断不通过                   
  AUTORULEDESC        VARCHAR      500    LIS自动审核规则说明                                                                            
  MANUALRULEFLAG      CHAR         1      LIS手工审核标志                                                                                
  MANUALRULEDESC      VARCHAR      500    LIS手工审核规则说明                                                                            
  MANUALCONFIRMCODE   VARCHAR      20     LIS手工审核确认人代码                                                                          
  MANUALCONFIRMNAME   VARCHAR      64     LIS手工审核确认人姓名                                                                          
  MANUALCONFIRMDATE   DATETIME            LIS手工审核确认时间                                                                            
  MANUALCONFIRMPC     VARCHAR      255    LIS手工确认PC 格式：计算机名                                                                   
  AUTOVERIFYFLAG      CHAR         1      当前报告是否时自动审核通过                                                                     
  SRCLAB              VARCHAR      50     标本送检机构代码 院内情况时更新为检验机构代码                                                  
  TARGETLAB           VARCHAR      50     检验机构代码                                                                                   
  TARGETREPORTNO      VARCHAR      64     检验机构报告单号                                                                               
  UPDATEFLAG          INT                 1:标本作废，0:正常数据，-1:为删除数据，系统控制                                                
  WORKGROUPCODE       VARCHAR      255    微生物工作组CODE                                                                               
  WORKGROUPNAME       VARCHAR      1000   微生物工作组                                                                                   
  SCHEMEID            VARCHAR      255    方案ID                                                                                         
  SCHEMENAME          VARCHAR      2000   方案名称                                                                                       
  MEDICALNO           VARCHAR      64     病案号                                                                                         
  TIMETEMP            ROWVERSION          时间戳                                                                                         
  ------------------- ------------ ------ ---------------------------------------------------------------------------------------------- ------

### 检前观察表MIS\_OBSERVATION

  --------------------- ---------- ------ ----------------------------------- ------
  字段名                数据类型   长度   作用                                说明
  OBSERVEID             INT               主键ID                              
  APPLYNO               NUMERIC    18,0   申请单ID，指向MIS\_SAMPLE           
  SMEARLABELID          VARCHAR    20     标签条码ID                          
  SMEARMETHOD           VARCHAR    20     涂片染色方法,指向MIS\_SMEARMETHOD   
  SMEARMETHODNAME       VARCHAR    64     涂片染色方法名称                    
  SMEARBATCHNO          VARCHAR    20     染液批号                            
  SMEARRESULT           VARCHAR    500    染色结果,指向MIS\_SMEARMETHOD.SME   
  SAMPLESHAPECODE       VARCHAR    20     标本性状 对应SLAVE.SLAVENO CLASS    
  SAMPLESHAPENAME       VARCHAR    64     标本性状名称                        
  SAMPLEQUALIFIED       CHAR       1      标本是否合格 0-不合格 1-标本合格    
  OBSERVEUSERID         VARCHAR    20     观察人代码                          
  OBSERVEUSERNAME       VARCHAR    64     观察人姓名                          
  OBSERVETIME           DATETIME          观察时间                            
  OBSERVEPCNAME         VARCHAR    255    观察客户端                          
  NOTICECLINICAL        CHAR       1      通知临床 0-未通知临床 1-通知临床    
  NOTICEURSE            VARCHAR    20     通知临床工号                        
  NOTICEURSENAME        VARCHAR    64     通知临床姓名                        
  NOTICETIME            DATETIME          通知临床日期                        
  REMARK                VARCHAR    255    备注                                
  SAMPLEDESCCODE        VARCHAR    29     标本说明                            
  SAMPLEDESCNAME        VARCHAR    64     标本说明                            
  SUMMINGUP             VARCHAR    1000   综合评价                            
  SMEARRESULTQUANTITY   VARCHAR    64     染色菌量，关联SLAVE表               
  PANICFLAG             INT               危急值标志 0-正常 1-危急值          
  BIOHAZARDFLAG         INT               传染病标志 0-正常 1-传染病报告      
  --------------------- ---------- ------ ----------------------------------- ------

### 接种培养表MIS\_CULTURE

  ------------------- --------------- ------ ------------------------------------ ------
  字段名              数据类型        长度   作用                                 说明
  CULTUREID           INT                    主键ID                               
  APPLYNO             NUMERIC         18,0   申请单ID，指向MIS\_SAMPLE            
  PARENTTYPE          INT                    父级类型 10-涂片镜检-检前 20-上机    
  PARENTID            INT                    父级ID                               
  CULTUREDESC         VARCHAR         255    培养基描述                           
  CULTURELABELID      VARCHAR         20     标签条码ID                           
  CULTUREMEDIUMCODE   VARCHAR         20     培养基编码 对应MIS\_CULTUREMEDIUM.   
  CULTUREMEDIUMNAME   VARCHAR         64     培养基名称                           
  INSTID              INT                    培养仪器ID                           
  INSTCODE            VARCHAR         20     培养仪器代码                         
  INSTNAME            VARCHAR         64     培养仪器名称                         
  CULTUREPLACE        VARCHAR         64     培养基位置 血培养瓶的话，则为瓶子    
  VACCINATETIME       DATETIME               接种时间                             
  CULTURETIME         DATETIME               培养时间                             
  COMPLETETIME        DATETIME               培养完成时间                         
  TOTALHOUR           VARCHAR         64     累计小时                             
  RESULT              VARCHAR         20     生长情况 对应SLAVE\_P.SLAVENO CLAS   
  RESULTTIME          DATETIME               仪器传回结果时间                     
  CULTUREITEMCODE     VARCHAR         40     培养结果代码 对应 MIS\_CULTUREITEM   
  CULTUREITEMNAME     VARCHAR         255    培养结果名称 对应 MIS\_CULTUREITEM   
  CULTURETYPE         INT                    培养类型                             
  QUANLITY            VARCHAR         64     菌落数量                             
  PANICFLAG           INT DEFAULT 0          危急值标志 0-正常 1-危急值           
  BIOHAZARDFLAG       INT DEFAULT 0          传染病标志 0-正常 1-传染病报告       
  SYNCFLAG            INT                    培养与仪器同步双工标志，0:向仪器传   
  VACCINATEUSERID     VARCHAR         20     接种人代码                           
  VACCINATEUSERNAME   VARCHAR         64     接种人姓名                           
  VACCINATEPCNAME     VARCHAR         255    接种客户端                           
  CULTUREUSERID       VARCHAR         20     培养结果填写人代码                   
  CULTUREUSERNAME     VARCHAR         64     培养结果填写人姓名                   
  CULTURERESULTTIME   DATETIME               培养结果填写时间                     
  CULTUREPCNAME       VARCHAR         255    培养结果填写客户端                   
  NOTICECLINICAL      CHAR            1      通知临床 0-未通知临床 1-通知临床     
  NOTICEURSE          VARCHAR         20     通知临床工号                         
  NOTICEURSENAME      VARCHAR         64     通知临床姓名                         
  NOTICETIME          DATETIME               通知临床日期                         
  SUMMINGUP           VARCHAR         1000   综合评价                             
  FPOSITIVEFLAG       VARCHAR         10     假阳 标记 1 假阳 2假阴               
  FPOSITIVETIME       DATETIME               假阳标记时间                         
  FPOSITIVEUSERID     VARCHAR         20     假阳标记人ID                         
  FPOSITIVEUSERNAME   VARCHAR         64     假阳标记人姓名                       
  POLLUTEFLAG         VARCHAR         10     血污染标记                           
  POLLUTETIME         DATETIME               血污染标记时间                       
  POLLUTEUSERID       VARCHAR         20     血污染标记人ID                       
  POLLUTEUSERNAME     VARCHAR         64     血污染标记人姓名                     
  ------------------- --------------- ------ ------------------------------------ ------

运行环境要求
============

  ------------------------ ------------------ ------ ---------------------------------------------------------------------- ------------------------------------------------------------------------------------------------------ ---------------------------------------------
  **大型三甲医院**                                                                                                                                                                                                                 
  序号                     名称               数量   硬件配置                                                               软件环境                                                                                               备注
  1                        核心数据库服务器   2      CPU：4路10核 内存：256g及以上 存储：1T及以上                           操作系统：Windows Server 2008 R2-中文版-企业版-OEM-25用户-64位； 数据库：SqlServer2008R2版本及以上；   热备
  2                        核心应用服务器     2      CPU：2路10核 内存：128GB内存及以上 存储：容量1T及以上（根目录挂载）    推荐：Linux系统（版本号：CentOS-7-x86 64-Minimal-1810.iso）                                            热备 基础服务、业务服务、知识服务、接口服务
  3                        核心计算服务器     2      CPU：4路10核 内存：64GB内存及以上 存储：容量500G及以上（根目录挂载）   推荐：Linux系统（版本号：CentOS-7-x86 64-Minimal-1810.iso）                                            热备 技术服务、接口服务
  **二甲及常规三级医院**                                                                                                                                                                                                           
  序号                     名称               数量   硬件配置                                                               软件环境                                                                                               备注
  1                        核心数据库服务器   2      CPU：2路8核 内存：128G及以上 存储：1T及以上                            操作系统：Windows Server 2008 R2-中文版-企业版-OEM-25用户-64位； 数据库：SqlServer2008R2版本及以上；   热备
  2                        核心应用服务器     2      CPU：2路8核 内存：64GB内存及以上 存储：容量500G及以上（根目录挂载）    推荐：Linux系统（版本号：CentOS-7-x86 64-Minimal-1810.iso）                                            热备 基础服务、业务服务、知识服务、接口服务
  3                        核心计算服务器     1      CPU：2路8核 内存：64GB内存及以上 存储：容量300G及以上（根目录挂载）    推荐：Linux系统（版本号：CentOS-7-x86 64-Minimal-1810.iso）                                            技术服务、接口服务
  ------------------------ ------------------ ------ ---------------------------------------------------------------------- ------------------------------------------------------------------------------------------------------ ---------------------------------------------

系统运行性能说明
================

系统利用多线程、高效缓存、异步队列、ElasticSearch分布式搜索引擎、实时运算服务等主流的互联网先进技术保障高效的服务器性能，采用最流行的vue前端技术框架， 运行于基于chromium内核浏览器，全面支持HTML5，让交互体验更加贴近日常，更加高效。引入localstorage、websocket等前端前沿技术，保障前后端交互更加高效和服务通知更加主动，更加智能。通过先进技术和业务流程优化设计使系统万份报告批量审核速度30秒，查询速度10秒，检验全流程追溯1分钟，界面一体化设计0次跳转，系统运维采用自动检测工具实现系统自检。

接口设计
========

读取病人基本信息（usp\_yjjk\_getbrxx）
--------------------------------------

> **业务描述：**
>
> 在门诊标本采集或者报告管理对病人进行登记时，可根据病人的病历号、磁卡号、条码号、发票号等信息准确的获取到该病人的各项信息。
>
> **接口描述：**

接口入参：

+---------+------------+----------+----------+--------------------------------------------------------+
| **NO.** | **变量名** | **含义** | **类型** | **备注说明**                                           |
+---------+------------+----------+----------+--------------------------------------------------------+
| 1       | brlb       | 病人类别 | int      | 0门诊 1住院 3 体检                                     |
+---------+------------+----------+----------+--------------------------------------------------------+
| 2       | codetype   | 号码类型 | int      | 1住院/门诊号指病历号，2卡号，                          |
|         |            |          |          |                                                        |
|         |            |          |          | 4 病人本次检查号cureno， 5 条码号，9 发票号10 身份证号 |
+---------+------------+----------+----------+--------------------------------------------------------+
| 3       | code       | 号码值   | varchar  | 传入与codetype对应的值                                 |
+---------+------------+----------+----------+--------------------------------------------------------+

> 接口出参（如果传入的参数错误或者不存在病人信息则返回 "F,错误信息"。否则返回以下信息）：

+---------+-------------+--------------------------------+----------+----------------------------------------+
| **NO.** | **变量名**  | **含义**                       | **类型** | **备注说明**                           |
+---------+-------------+--------------------------------+----------+----------------------------------------+
| 1       | WardOrReg   | 病人类别                       | Int      | 0门诊 1住院 3体检                      |
+---------+-------------+--------------------------------+----------+----------------------------------------+
| 2       | HospNo      | 病历号                         | varchar  |                                        |
+---------+-------------+--------------------------------+----------+----------------------------------------+
| 3       | PatientID   | 病人唯一标识                   | varchar  | 病人唯一标识                           |
+---------+-------------+--------------------------------+----------+----------------------------------------+
| 4       | CureNo      | 病人就诊流水号                 | varchar  | 门诊：与PatientID一致                  |
|         |             |                                |          |                                        |
|         |             |                                |          | 住院：就诊流水号（当前住院的首页序号） |
+---------+-------------+--------------------------------+----------+----------------------------------------+
| 5       | PatName     | 病人姓名                       | varchar  | 可按姓名查找（为空代表全部）           |
+---------+-------------+--------------------------------+----------+----------------------------------------+
| 6       | Age         | 年龄                           | Int      |                                        |
+---------+-------------+--------------------------------+----------+----------------------------------------+
| 7       | Age1        | 年龄1                          | varchar  | 可传空，年龄的扩充                     |
+---------+-------------+--------------------------------+----------+----------------------------------------+
| 8       | AgeUnit     | 年龄单位                       | char(1)  | '岁'、'月'、'天'、'小时'               |
+---------+-------------+--------------------------------+----------+----------------------------------------+
| 9       | AgeUnit1    | 年龄单位1                      | varchar  | 可传空，年龄单位的扩充                 |
+---------+-------------+--------------------------------+----------+----------------------------------------+
| 10      | Sex         | 病人性别                       | varchar  | 1 男，2 女 3 未知                      |
+---------+-------------+--------------------------------+----------+----------------------------------------+
| 11      | ApplyDept   | 病人所在科室代码或门诊挂号科室 | varchar  | 门诊如果一天挂2个科室返回其中一个科室  |
+---------+-------------+--------------------------------+----------+----------------------------------------+
| 12      | Ward        | 病人所在病区代码               | varchar  | 门诊可以为空                           |
+---------+-------------+--------------------------------+----------+----------------------------------------+
| 13      | BedNo       | 病人所在病区的床号             | varchar  | 门诊可以为空                           |
+---------+-------------+--------------------------------+----------+----------------------------------------+
| 14      | CardNo      | 病人的卡号                     | varchar  | 病人的卡号                             |
+---------+-------------+--------------------------------+----------+----------------------------------------+
| 15      | chargeType  | 病人的收费类别代码             | varchar  |                                        |
+---------+-------------+--------------------------------+----------+----------------------------------------+
| 16      | ApplyDoctor | 病人的主治医生代码             | varchar  |                                        |
+---------+-------------+--------------------------------+----------+----------------------------------------+
| 17      | Address     | 病人的地址                     | varchar  | 病人的地址                             |
+---------+-------------+--------------------------------+----------+----------------------------------------+
| 18      | Phone       | 病人的电话                     | varchar  | 病人的电话                             |
+---------+-------------+--------------------------------+----------+----------------------------------------+
| 19      | IDNum       | 病人身份证号                   | varchar  | 病人身份证号                           |
+---------+-------------+--------------------------------+----------+----------------------------------------+
| 20      | ClincDesc   | 病人的临床诊断                 | varchar  | 病人的临床诊断                         |
+---------+-------------+--------------------------------+----------+----------------------------------------+
| 21      | Nation      | 病人的民族                     | varchar  | 病人的民族                             |
+---------+-------------+--------------------------------+----------+----------------------------------------+
| 22      | Birthday    | 生日                           | varchar  | 生日                                   |
+---------+-------------+--------------------------------+----------+----------------------------------------+
| 23      | islcljbz    | 临床路径标志                   | int      | 临床路径标志                           |
+---------+-------------+--------------------------------+----------+----------------------------------------+
| 24      | Invoice     | 发票号                         | varchar  | 可以为空                               |
+---------+-------------+--------------------------------+----------+----------------------------------------+
| 25      | icd10       | 疾病类型代码                   | varchar  |                                        |
+---------+-------------+--------------------------------+----------+----------------------------------------+
| 26      | icd10Name   | 疾病类型名称                   | varchar  |                                        |
+---------+-------------+--------------------------------+----------+----------------------------------------+
| 27      | Zip         | 邮政编码                       | varchar  |                                        |
+---------+-------------+--------------------------------+----------+----------------------------------------+
| 28      | Career      | 职业                           | varchar  |                                        |
+---------+-------------+--------------------------------+----------+----------------------------------------+
| 29      | yexh        | 婴儿序号                       | varchar  |                                        |
+---------+-------------+--------------------------------+----------+----------------------------------------+

获取在院病人信息列表(usp\_yjjk\_getbrlist)
------------------------------------------

> **业务描述：**
>
> 对病人进行登记时，当无法通过卡号或住院号调取病人基本信息时，需要通过查询病人列表进行查找，因此需要HIS提供通过多种条件进行查询病人的接口。
>
> **接口描述：**
>
> 接口入参：

  --------- ------------ -------------- ---------- --------------------------------
  **NO.**   **变量名**   **含义**       **类型**   **备注说明**
  1         brlb         病人类别       Int        0门诊 1住院
  2         hzxm         病人姓名       varchar    可按姓名查找（为空代表全部）
  3         ksdm         科室代码       varchar    可按科室查找（为空代表全部）
  4         bqdm         病区代码       varchar    可按病区查找（为空代表全部）
  5         cwdm         床位代码       varchar    可按床号查找（为空代表全部）
  6         rq1          开始日期       DateTime   门诊指挂号时间，住院不处理
  7         rq2          结束日期       DateTime   门诊指挂号时间，住院不处理
  8         fph          发票号         varchar    可以为空
  9         codetype     卡类型         int        0 磁卡；1 体检号
  10        zxksdm       执行科室代码   varchar    执行科室代码比如检验科，放射科
  11        jzbrbz       急诊标志       int        默认为0 -0-全部，1-急诊
  --------- ------------ -------------- ---------- --------------------------------

> 接口出参（如果传入的参数错误或者不存在病人信息则返回 "F,错误信息"。否则返回以下信息）：

+---------+--------------+----------------+----------+----------------------------------------+
| **NO.** | **变量名**   | **含义**       | **类型** | **备注说明**                           |
+---------+--------------+----------------+----------+----------------------------------------+
| 1       | 病人姓名     | 病人姓名       | varchar  | 可按姓名查找（为空代表全部）           |
+---------+--------------+----------------+----------+----------------------------------------+
| 2       | 年龄         | 病人年龄       | Int      |                                        |
+---------+--------------+----------------+----------+----------------------------------------+
| 3       | 性别         | 病人性别       | varchar  |                                        |
+---------+--------------+----------------+----------+----------------------------------------+
| 4       | 科室         | 所在科室代码   | varchar  | 可按科室查找（为空代表全部）           |
+---------+--------------+----------------+----------+----------------------------------------+
| 5       | 病区         | 所在病区代码   | varchar  | 可按病区查找（为空代表全部）           |
+---------+--------------+----------------+----------+----------------------------------------+
| 6       | 床位号       | 所在病区的床号 | varchar  |                                        |
+---------+--------------+----------------+----------+----------------------------------------+
| 7       | 病历号       | 门诊/住院号    | varchar  | 门诊传门诊病案号，住院传住院号         |
+---------+--------------+----------------+----------+----------------------------------------+
| 8       | 卡号         | 病人的卡号     | varchar  |                                        |
+---------+--------------+----------------+----------+----------------------------------------+
| 9       | 地址         | 病人的地址     | varchar  |                                        |
+---------+--------------+----------------+----------+----------------------------------------+
| 10      | 电话         | 病人的电话     | varchar  |                                        |
+---------+--------------+----------------+----------+----------------------------------------+
| 11      | PatientID    | 病人唯一标识   | varchar  | 病人唯一标识                           |
+---------+--------------+----------------+----------+----------------------------------------+
| 12      | CureNo       | 病人就诊流水号 | varchar  | 门诊：与PatientID一致                  |
|         |              |                |          |                                        |
|         |              |                |          | 住院：就诊流水号（当前住院的首页序号） |
+---------+--------------+----------------+----------+----------------------------------------+
| 13      | 医保类型     | 医保类型       | varchar  |                                        |
+---------+--------------+----------------+----------+----------------------------------------+
| 14      | 临床路径标志 | 临床路径标志   | int      |                                        |
+---------+--------------+----------------+----------+----------------------------------------+
| 15      | PatNameSpell | 姓名简拼       | varchar  |                                        |
+---------+--------------+----------------+----------+----------------------------------------+
| 16      | 日期         | 申请日期       | varchar  | yyyy-MM-dd HH:mm:ss                    |
+---------+--------------+----------------+----------+----------------------------------------+

获取病人未执行医嘱项目（usp\_yjjk\_getwzxxm）
---------------------------------------------

**业务描述：**

> 在医技中对病人进行登记时，在调出病人基本信息的同时还将调用出病人的医嘱项目信息，以供医技科室医生进行核对并作确认。这里的接口只需返回某一个病人在一段时间内的所有医嘱项目，不须区分科室，医技系统会处理相关的过滤。

**接口描述：**

接口入参：

+---------+------------+----------------+----------+----------------------------------------+
| **NO.** | **变量名** | **含义**       | **类型** | **备注说明**                           |
+---------+------------+----------------+----------+----------------------------------------+
| 1       | brlb       | 病人类别       | int      | 0门诊 1住院 3体检                      |
+---------+------------+----------------+----------+----------------------------------------+
| 2       | patid      | 病人唯一标识   | varchar  | 病人唯一标识                           |
+---------+------------+----------------+----------+----------------------------------------+
| 3       | curno      | 病人就诊流水号 | varchar  | 门诊：与PatientID一致                  |
|         |            |                |          |                                        |
|         |            |                |          | 住院：就诊流水号（当前住院的首页序号） |
+---------+------------+----------------+----------+----------------------------------------+
| 4       | rq1        | 开始日期       | varchar  | 收费项目的开始申请日期                 |
+---------+------------+----------------+----------+----------------------------------------+
| 5       | rq2        | 结束日期       | varchar  | 收费项目的结束申请日期                 |
+---------+------------+----------------+----------+----------------------------------------+
| 6       | sqdxh      | 申请单号       | varchar  | 默认0，不一定会传                      |
+---------+------------+----------------+----------+----------------------------------------+
| 7       | yexh       | 婴儿序号       | varchar  | 默认0                                  |
+---------+------------+----------------+----------+----------------------------------------+

> 接口出参（如果传入的参数错误或者不存在病人信息则返回 "F,错误信息"。否则返回以下信息）：

+---------+--------------+------------------+----------+----------------------------------------------------------------+
| **NO.** | **变量名**   | **含义**         | **类型** | **备注说明**                                                   |
+---------+--------------+------------------+----------+----------------------------------------------------------------+
| 1       | PatientID    | 病人唯一标识     | varchar  | 病人唯一标识                                                   |
+---------+--------------+------------------+----------+----------------------------------------------------------------+
| 2       | CureNo       | 病人就诊流水号   | varchar  | 门诊：与PatientID一致                                          |
|         |              |                  |          |                                                                |
|         |              |                  |          | 住院：就诊流水号（当前住院的首页序号）                         |
+---------+--------------+------------------+----------+----------------------------------------------------------------+
| 3       | LogNo        | 医嘱序号         | varchar  | 医嘱的唯一序号，代表住院医嘱或门诊每条收费项目或组合的唯一序号 |
+---------+--------------+------------------+----------+----------------------------------------------------------------+
| 4       | HisApplyNo   | 申请单号         | varchar  | 医嘱所属的申请单号                                             |
+---------+--------------+------------------+----------+----------------------------------------------------------------+
| 5       | GroupNo      | 分组号           | varchar  | 医嘱的分组序号，代表哪些医嘱是在一起的                         |
+---------+--------------+------------------+----------+----------------------------------------------------------------+
| 6       | ItemCode     | 项目代码         | varchar  | ris，lis共用，都代表xmdm                                       |
+---------+--------------+------------------+----------+----------------------------------------------------------------+
| 7       | ItemName     | 项目名称         | varchar  | ris，lis共用，都代表xmmc                                       |
+---------+--------------+------------------+----------+----------------------------------------------------------------+
| 8       | Price        | 单价             | money    |                                                                |
+---------+--------------+------------------+----------+----------------------------------------------------------------+
| 9       | ItemQty      | 数量             | float    |                                                                |
+---------+--------------+------------------+----------+----------------------------------------------------------------+
| 10      | ItemUnit     | 单位             | varchar  |                                                                |
+---------+--------------+------------------+----------+----------------------------------------------------------------+
| 11      | ApplyTime    | 申请时间         | datetime | 格式：yyyy-MM-dd HH:mm:ss,不能返回null                         |
+---------+--------------+------------------+----------+----------------------------------------------------------------+
| 12      | ReceiveTime  | 确认时间         | datetime | 医嘱的确认时间，不能返回nul                                    |
+---------+--------------+------------------+----------+----------------------------------------------------------------+
| 13      | CheckTime    | 执行时间         | datetime |                                                                |
+---------+--------------+------------------+----------+----------------------------------------------------------------+
| 14      | ApplyDocCode | 申请医生（代码） | varchar  |                                                                |
+---------+--------------+------------------+----------+----------------------------------------------------------------+
| 15      | ApplyDept    | 申请科室（代码） | varchar  |                                                                |
+---------+--------------+------------------+----------+----------------------------------------------------------------+
| 16      | ExecDept     | 执行科室（代码） | varchar  |                                                                |
+---------+--------------+------------------+----------+----------------------------------------------------------------+
| 17      | Status       | 状态             | varchar  | 0：未确认 1：已确认                                            |
+---------+--------------+------------------+----------+----------------------------------------------------------------+
| 18      | ItemType     | 收费类型         | varchar  | 0临床项目1收费项目2药品项目                                    |
+---------+--------------+------------------+----------+----------------------------------------------------------------+
| 19      | AddType      | 上传标志         | varchar  | 0：下载 1上传                                                  |
+---------+--------------+------------------+----------+----------------------------------------------------------------+
| 20      | chargeFlag   | 收费标志         | varchar  | 0：未收费，1：已收费;住院一般都为0，普通门诊病人为1            |
+---------+--------------+------------------+----------+----------------------------------------------------------------+
| 21      | Mjzbz        | 急诊标志         | Int      | 0：普通项目；1：急诊项目                                       |
+---------+--------------+------------------+----------+----------------------------------------------------------------+
| 22      | Specimen     | 标本代码         | varchar  |                                                                |
+---------+--------------+------------------+----------+----------------------------------------------------------------+
| 23      | SpecimenDesc | 标本描述         | varchar  |                                                                |
+---------+--------------+------------------+----------+----------------------------------------------------------------+
| 24      | ItemMemo     | 医嘱说明         | varchar  |                                                                |
+---------+--------------+------------------+----------+----------------------------------------------------------------+
| 25      | fph          | 发票号           | varchar  | 可以为空                                                       |
+---------+--------------+------------------+----------+----------------------------------------------------------------+
| 26      | ClinicDesc   | 临床诊断         | varchar  |                                                                |
+---------+--------------+------------------+----------+----------------------------------------------------------------+
| 27      | ghxh         | 挂号序号         | varchar  |                                                                |
+---------+--------------+------------------+----------+----------------------------------------------------------------+
| 28      | SerialNo     | 序号             | Varchar  | 没有返回值时，默认返回-1                                       |
+---------+--------------+------------------+----------+----------------------------------------------------------------+
| 29      | ApplyNo      | 报告单号         | Varchar  | 没有返回值时，默认返回-1                                       |
+---------+--------------+------------------+----------+----------------------------------------------------------------+
| 30      | OperatorCode | 操作者代码       | Varchar  |                                                                |
+---------+--------------+------------------+----------+----------------------------------------------------------------+
| 31      | OperatorName | 操作者           | varchar  |                                                                |
+---------+--------------+------------------+----------+----------------------------------------------------------------+
| 32      | jzghbz       | 急诊挂号标志     | int      |                                                                |
+---------+--------------+------------------+----------+----------------------------------------------------------------+
| 33      | jlzt         | 记录状态         | int      |                                                                |
+---------+--------------+------------------+----------+----------------------------------------------------------------+

医技确认执行病人收费项目（usp\_yjjk\_yjqr）
-------------------------------------------

**业务描述：**

> 在医技系统中对病人进行登记时，调出病人未执行医嘱项目后将对医嘱项目作确认操作。

接口入参：

+---------+------------+------------------+----------+-----------------------------------------------------------------------+
| **NO.** | **变量名** | **含义**         | **类型** | **备注说明**                                                          |
+---------+------------+------------------+----------+-----------------------------------------------------------------------+
| 1       | brlb       | 病人类别         | varchar  | 0门诊 1住院 3体检                                                     |
+---------+------------+------------------+----------+-----------------------------------------------------------------------+
| 2       | patid      | 病人唯一标识     | varchar  | 病人唯一标识                                                          |
+---------+------------+------------------+----------+-----------------------------------------------------------------------+
| 3       | curno      | 病人就诊流水号   | varchar  | 门诊：与PatientID一致                                                 |
|         |            |                  |          |                                                                       |
|         |            |                  |          | 住院：就诊流水号（当前住院的首页序号）                                |
+---------+------------+------------------+----------+-----------------------------------------------------------------------+
| 4       | zxksdm     | 执行科室（代码） | varchar  |                                                                       |
+---------+------------+------------------+----------+-----------------------------------------------------------------------+
| 5       | zxysdm     | 执行医生（代码） | varchar  |                                                                       |
+---------+------------+------------------+----------+-----------------------------------------------------------------------+
| 6       | logno      | 医嘱序号         | varchar  | 医嘱的唯一序号，代表住院医嘱或门诊每条收费项目或组合的唯一序号        |
+---------+------------+------------------+----------+-----------------------------------------------------------------------+
| 7       | applyno    | 申请单号         | varchar  | 字符型                                                                |
+---------+------------+------------------+----------+-----------------------------------------------------------------------+
| 8       | groupno    | 分组号           | varchar  | 医嘱的分组序号，代表哪些医嘱是在一起的                                |
+---------+------------+------------------+----------+-----------------------------------------------------------------------+
| 9       | xmlb       | 项目类别         | varchar  |                                                                       |
+---------+------------+------------------+----------+-----------------------------------------------------------------------+
| 10      | xmdm       | 项目代码         | money    |                                                                       |
+---------+------------+------------------+----------+-----------------------------------------------------------------------+
| 11      | xmdj       | 项目单价         | money    | 在存储过程中要判断，如原来项目为零，此价格才有用,否则要取项目中的价格 |
+---------+------------+------------------+----------+-----------------------------------------------------------------------+
| 12      | xmsl       | 项目数量         | float    | 一般默认为1                                                           |
+---------+------------+------------------+----------+-----------------------------------------------------------------------+
| 13      | xmstatus   | 项目状态         | Int      | 0不处理1确认2拒绝3撤销(增加撤销处理)                                  |
+---------+------------+------------------+----------+-----------------------------------------------------------------------+
| 14      | sfflag     | 收费标志         | Int      | 0不收费1收费2退费                                                     |
+---------+------------+------------------+----------+-----------------------------------------------------------------------+
| 15      | bgdh       | 报告单号         | varchar  | ris，lis共用                                                          |
+---------+------------+------------------+----------+-----------------------------------------------------------------------+
| 16      | bglx       | 报告类型         | varchar  | 血库为空，ris，lis共用，ris里代表bgxl，lis代表xmlbdm                  |
+---------+------------+------------------+----------+-----------------------------------------------------------------------+

> 接口出参：

  --------- ------------ ---------- ---------- --------------------------
  **NO.**   **变量名**   **含义**   **类型**   **备注说明**
  1         BZ           成功标志   varchar    成功返回"T"，错误返回"F"
  2         errmsg       提示信息   varchar    返回F时显示的错误信息
  --------- ------------ ---------- ---------- --------------------------

获取HIS字典信息（usp\_yjjk\_getpzxxlist）
-----------------------------------------

**业务描述：**

> 从HIS获取科室、病区、人员等信息时调用的接口，用于医技系统从HIS导入部门、人员等信息。

**接口描述：**

传入参数：

  --------- ------------ ---------- ---------- -------------------------
  **NO.**   **变量名**   **含义**   **类型**   **备注说明**
  1         fl           字典类型   int        0：科室 1：病区 2：员工
  --------- ------------ ---------- ---------- -------------------------

> 传出参数：（如果传入的参数错误或者不存在病人信息则返回 "F,错误信息"。否则返回以下信息）

  --------- ------------ ---------- ---------- --------------
  **NO.**   **变量名**   **含义**   **类型**   **备注说明**
  1         Code         代码       varchar    
  2         Name         名称       varchar    
  3         MemCode1     拼音码     varchar    
  4         MemCode2     五笔码     varchar    
  --------- ------------ ---------- ---------- --------------

获取HIS医嘱字典信息（usp\_yjjk\_getxmlist）
-------------------------------------------

**业务描述：**

传出

**接口描述：**

传入参数：

  --------- ------------ ---------- ---------- --------------------------------------
  **NO.**   **变量名**   **含义**   **类型**   **备注说明**
  1         xmdm         项目代码   varchar    默认空
  2         xmtype       查询码     varchar    默认空
  3         lb           医嘱类别   int        类别0：RIS、4：LIS，不传查询返回项目
  --------- ------------ ---------- ---------- --------------------------------------

> 传出参数（如果传入的参数错误或者不存在病人信息则返回 "F,错误信息"。否则返回以下信息）：

  --------- ------------ ---------- ---------- ---------------------------------
  **NO.**   **变量名**   **含义**   **类型**   **备注说明**
  1         ItemCode     项目代码   varchar    
  2         ItemType     项目类型   varchar    返回0,1。0 临床项目、1 收费项目
  3         ItemName     项目名称   varchar    
  4         Price        价格       money      
  5         Unit         单位       char       比如：次 项或空
  6         MemCode1     拼音码     varchar    
  7         MemCode2     五笔码     varchar    
  --------- ------------ ---------- ---------- ---------------------------------

病人信息发布（usp\_yjjk\_jcbrfb）
---------------------------------

**业务描述：**

发布医技病人信息时调取的接口

**接口描述**：

传入参数：

  --------- ------------ ---------------- ---------- ---------------------------------------------------------------------------------------
  **NO.**   **变量名**   **含义**         **类型**   **备注说明**
  1         repno        报告单号         varchar    ris，lis共用
  2         syxh         病人本次检查号   varchar    CureNo，代表病人本次检查号，HIS接口一定会传，来源于usp\_yjjk\_getbrlist病人本次检查号
  3         patid        病人唯一标识     varchar    病人唯一标识
  4         blh          病员号           varchar    HospNo
  5         CardNo       卡号             varchar    
  6         hzxm         姓名             varchar    
  7         Sex          性别             varchar    
  8         Age          年龄             varchar    
  9         sjksdm       送检科室代码     varchar    送检科室代码
  10        sjksmc       送检科室名称     varchar    
  11        bqdm         病区代码         varchar    可按病区查找（为空代表全部）
  12        bqmc         病区名称         varchar    病人所属病区名称
  13        cwdm         床号             varchar    床号
  14        sjysdm       送检医生工号     varchar    送检医生工号
  15        sjysxm       送检医生姓名     varchar    送检医生姓名
  16        sjrq         送检时间         DateTime   格式：yyyy-MM-dd HH:mm:ss
  17        reprq        报告时间         DateTime   格式：yyyy-MM-dd HH:mm:ss
  18        replb        报告类别         varchar    
  19        replbmc      报告类别名称     varchar    
  20        xtbz         病人类别         varchar    0门诊 1住院 3体检
  21        jcbw         标本类型         varchar    
  22        jcysdm       检验医生工号     varchar    
  23        jcysxm       检验医生姓名     varchar    
  24        jcksdm       检验科室工号     varchar    
  25        jcksmc       检验科室姓名     varchar    
  26        wjbz         危急值标志       varchar    0,1，0没有危急值，1有危急值
  27        instname     仪器名称         varchar    
  28        techno       样本号           varchar    
  29        cyrq         采样日期         varchar    格式：yyyy-MM-dd HH:mm:ss
  30        shys         审核人姓名       varchar    
  31        crbz         传染标志         int        
  32        shysdm       审核人代码       varchar    
  33        bbjsrq       标本接收日期     char       格式：yyyy-MM-dd HH:mm:ss
  34        memo         备注             varchar    
  35        ghxh         挂号序号         varchar    
  --------- ------------ ---------------- ---------- ---------------------------------------------------------------------------------------

> 传出参数：（如果传入的参数错误或者不存在病人信息则返回 "F,错误信息"。否则返回以 下病人信息）

  --------- ------------ ---------- ---------- --------------------------
  **NO.**   **变量名**   **含义**   **类型**   **备注说明**
  1         BZ           成功标志   varchar    成功返回"T"，错误返回"F"
  2         errmsg       提示信息   varchar    返回F时显示的错误信息
  --------- ------------ ---------- ---------- --------------------------

病人结果发布（usp\_yjjk\_yjjgfb）
---------------------------------

**业务描述：**

发布医技病人报告结果时调取的接口，一般多用于体检病人

**接口描述：**

传入参数：

  --------- ------------ -------------- ---------- --------------------------------------------------------
  **NO.**   **变量名**   **含义**       **类型**   **备注说明**
  1         syscode      子系统代码     varchar    LIS/RIS
  2         cflb         病人类别       varchar    0门诊 1住院 3体检
  3         patid        病人唯一标识   varchar    病人唯一标识
  4         cfxh         处方序号       varchar    ris，lis共用，ris里代表bgdh，lis代表cfxh
  5         xmlbdm       报告类型       varchar    血库为空，ris，lis共用，ris里代表bglx，lis代表xmlbdm
  6         xmlbmc       报告类型名称   varchar    血库为空，ris，lis共用，ris里代表bglxmc，lis代表xmlbmc
  7         xmdm         项目代码       varchar    ris，lis共用，都代表xmdm
  8         xmmc         项目名称       varchar    ris，lis共用，都代表xmmc
  9         xmjg         项目结果       varchar    ris，lis共用，都代表xmjg
  10        ksdm         科室代码       varchar    病人所在科室代码
  11        ysdm         医生代码       varchar    
  12        wcsj         检验时间       DateTime   
  13        xmdw         单位           varchar    lis用，代表xmdw
  14        jglx         结果类型       varchar    lis用，代表结果类型，数字，阴阳性
  15        jgckz        结果参考值     varchar    lis用，代表参考值
  16        gdbz         高低标志       varchar    lis用，代表高低标志
  17        dyxh         打印序号       varchar    lis用，代表打印序号
  18        applyno      报告单号       varchar    ris，lis共用
  19        crbz         传染标志       int        
  --------- ------------ -------------- ---------- --------------------------------------------------------

传出参数：（如果传入的参数错误或者不存在病人信息则返回 "F,错误信息"。否则返回以 下病人信息）

  --------- ------------ ---------- ---------- --------------------------
  **NO.**   **变量名**   **含义**   **类型**   **备注说明**
  1         BZ           成功标志   varchar    成功返回"T"，错误返回"F"
  2         errmsg       提示信息   varchar    返回F时显示的错误信息
  --------- ------------ ---------- ---------- --------------------------

病人报告回收（usp\_yjjk\_jg\_huishou）
--------------------------------------

**业务描述：**

对已发布的病人报告进行回收。

**接口描述：**

传入参数：

  --------- ------------ ------------ ---------- --------------
  **NO.**   **变量名**   **含义**     **类型**   **备注说明**
  1         repno        报告单号     varchar    ris，lis共用
  2         replb        报告类别     varchar    
  3         syscode      子系统系统   varchar    LIS/RIS
  --------- ------------ ------------ ---------- --------------

传出参数：（如果传入的参数错误或者不存在病人信息则返回 "F,错误信息"。否则返回以 下病人信息）

  --------- ------------ ---------- --------------------------
  **NO.**   **变量名**   **含义**   **备注说明**
  1         BZ           成功标志   成功返回"T"，错误返回"F"
  2         errmsg       提示信息   返回F时显示的错误信息
  --------- ------------ ---------- --------------------------

获取病人卡内余额信息（usp\_yjjk\_getbrjexx）
--------------------------------------------

**业务描述：**

获取病人余额信息

**接口描述：**

传入参数：

+---------+------------+----------------+----------+----------------------------------------+
| **NO.** | **变量名** | **含义**       | **类型** | **备注说明**                           |
+---------+------------+----------------+----------+----------------------------------------+
| 1       | brlb       | 病人类别       | varchar  | 0门诊 1住院 3体检                      |
+---------+------------+----------------+----------+----------------------------------------+
| 2       | patid      | 病人唯一标识   | varchar  | 病人唯一标识                           |
+---------+------------+----------------+----------+----------------------------------------+
| 3       | curno      | 病人就诊流水号 | varchar  | 门诊：与PatientID一致                  |
|         |            |                |          |                                        |
|         |            |                |          | 住院：就诊流水号（当前住院的首页序号） |
+---------+------------+----------------+----------+----------------------------------------+
| 4       | sfje       | 收费金额       | money    | 默认为0                                |
+---------+------------+----------------+----------+----------------------------------------+

传出参数：（如果传入的参数错误或者不存在病人信息则返回 "F,错误信息"。否则返回以 下病人信息）

  --------- ------------ ---------- ------------------------------------
  **NO.**   **变量名**   **含义**   **备注说明**
  1         BZ           成功标志   成功返回"T"，错误返回"F"加错误信息
  2         总金额       总金额     
  3         自费金额     自费金额   
  4         自付金额     自付金额   
  5         预交金       预交金     
  6         余额         余额       余额信息，精度小数点后两位
  --------- ------------ ---------- ------------------------------------

微生物病人结果发布（usp\_yjjk\_yjjgfb\_wsw）
--------------------------------------------

**业务描述：**

微生物系统发布医技病人报告结果时调取的接口，一般多用于体检病人

**接口描述：**

传入参数：

  --------- ------------ -------------- ---------- -----------------------------------------------------------------
  **NO.**   **变量名**   **含义**       **类型**   **备注说明**
  1         repno        申请单号       varchar    
  2         cflb         病人类别       varchar    0门诊 1 住院 3体检
  3         reporttype   报告类型       varchar    1-培养报告,2-涂片报告,3-鉴定阴性报告,4-药敏阳性报告
  4         reportid     报告id         varchar    老微生物传ApplyNo,新微生物传ReportID,一个申请单号可能有多份报告
  5         patid        病人唯一标识   varchar    病人唯一标识
  6         Replb        报告类型代码   varchar    
  7         replbmc      报告类型名称   varchar    
  8         jyff         检验方法       varchar    
  9         pyjg         培养结果       varchar    
  10        jdjgdm       结果代码       varchar    
  11        jdjgmc       结果名称       varchar    
  12        jgyyx        结果阴阳性     DateTime   
  13        ymff         药敏方法       varchar    
  14        jglx         结果类型       varchar    
  15        nyjz         耐药机制       varchar    
  16        kssdm        抗生素代码     varchar    
  17        kssmc        抗生素名称     varchar    
  18        jcjg         检测结果       varchar    
  19        ymjg         药敏结果       varchar    
  20        jgckz        结果参考值     varchar    
  21        ysdm         医生代码       varchar    
  22        jysj         检验时间       char       
  23        jljs         菌落计数       varchar    
  24        xjbz         备注           varchar    
  --------- ------------ -------------- ---------- -----------------------------------------------------------------

传出参数：（如果传入的参数错误或者不存在病人信息则返回 "F,错误信息"。否则返回以 下病人信息）

  --------- ------------ ---------- --------------------------
  **NO.**   **变量名**   **含义**   **备注说明**
  1         BZ           成功标志   成功返回"T"，错误返回"F"
  2         errmsg       提示信息   返回F时显示的错误信息
  --------- ------------ ---------- --------------------------

获取当前病区有未执行医嘱的病人信息列表(usp\_yjjk\_hybrcx) 
---------------------------------------------------------

**业务描述：**

在住院病人列表，只有存在检验医嘱的病人（住院采集系统调用）

**接口描述：**

传入参数：

  --------- ------------ -------------- ---------- --------------------------------
  **NO.**   **变量名**   **含义**       **类型**   **备注说明**
  1         bqdm         病区代码       varchar    可按病区查找（为空代表全部）
  2         ksdm         执行科室代码   varchar    执行科室代码比如检验科，放射科
  3         ksrq         开始日期       DateTime   
  4         jsrq         结束日期       DateTime   
  --------- ------------ -------------- ---------- --------------------------------

传出参数：（如果传入的参数错误或者不存在病人信息则返回 "F,错误信息"。否则返回以 下病人信息）

  --------- ------------ -------------- -------------------------------------------------------------
  **NO.**   **变量名**   **含义**       **备注说明**
  1         syxh         医嘱号         每条医嘱的唯一号
  2         xmdm         项目代码       
  3         ItemType     项目类型       返回0,1。0 临床项目、1 收费项目
  4         blh          病历号         门诊传门诊病案号，住院传住院号，可调usp\_yjjk\_getbrxx 信息
  5         hzxm         病人姓名       可按姓名查找（为空代表全部）
  6         cwdm         床位代码       可按床号查找（为空代表全部）
  7         bqdm         病区代码       可按病区查找（为空代表全部）
  8         bqmc         病区名称       
  9         sex          性别           1男，2女，3不详
  10        age          年龄           年为单位
  11        ageUnit      年龄单位       '岁'、'月'、'天'、'小时'
  12        yebz         婴儿标志       1婴儿，0非婴儿
  13        yexh         婴儿序号       
  14        cardno       卡号           
  15        fbdm         费别代码       
  16        ClinicDesc   临床诊断       
  17        patid        病人内码       如果大人是病人内码，小孩是婴儿序号
  18        IDNum        身份证号       身份证号
  19        islcljbz     临床路径标志   1临床路径病人 0 非临床路径病人
  20        lxdz         联系地址       
  21        nl           年龄           
  22        Mjzbz        急诊标志       0 非急诊 1急诊
  23        icd10        icd10代码      
  24        icd10name    icd10名称      
  --------- ------------ -------------- -------------------------------------------------------------

获取病人未收费申请项目(usp\_yjjk\_getwsfxm) 
-------------------------------------------

**业务描述：**

获取病人未收费的申请单项目，目前用于门诊用血申请单接收

**接口描述：**

传入参数：

+---------+------------+----------------+----------+----------------------------------------+
| **NO.** | **变量名** | **含义**       | **类型** | **备注说明**                           |
+---------+------------+----------------+----------+----------------------------------------+
| 1       | brlb       | 病人类别       | varchar  | 0门诊 1 住院 3体检                     |
+---------+------------+----------------+----------+----------------------------------------+
| 2       | patid      | 病人唯一标识   | varchar  | 病人唯一标识                           |
+---------+------------+----------------+----------+----------------------------------------+
| 3       | curno      | 病人就诊流水号 | varchar  | 门诊：与patntid一致                    |
|         |            |                |          |                                        |
|         |            |                |          | 住院：就诊流水号（当前住院的首页序号） |
+---------+------------+----------------+----------+----------------------------------------+
| 4       | rq1        | 开始日期       | DateTime |                                        |
+---------+------------+----------------+----------+----------------------------------------+
| 5       | rq2        | 结束日期       | DateTime |                                        |
+---------+------------+----------------+----------+----------------------------------------+
| 6       | sqdxh      | 申请单序号     | varchar  |                                        |
+---------+------------+----------------+----------+----------------------------------------+
| 7       | ksdm       | 科室代码       | varchar  | 执行科室代码比如检验科，放射科         |
+---------+------------+----------------+----------+----------------------------------------+
| 8       | yexh       | 婴儿序号       | Int      |                                        |
+---------+------------+----------------+----------+----------------------------------------+
| 9       | bqdm       | 病区代码       | varchar  | 可按病区查找（为空代表全部）           |
+---------+------------+----------------+----------+----------------------------------------+

传出参数：（如果传入的参数错误或者不存在病人信息则返回 "F,错误信息"。否则返回以 下病人信息）

+---------+--------------+------------------+----------+----------------------------------------------------------------+
| **NO.** | **变量名**   | **含义**         |          | **备注说明**                                                   |
+---------+--------------+------------------+----------+----------------------------------------------------------------+
| 1       | SerialNo     | 流水号           | varchar  | 每条医嘱的唯一号                                               |
+---------+--------------+------------------+----------+----------------------------------------------------------------+
| 2       | PatientID    | 病人唯一标识     | varchar  | 病人唯一标识                                                   |
+---------+--------------+------------------+----------+----------------------------------------------------------------+
| 3       | CureNo       | 病人就诊流水号   | varchar  | 门诊：与PatientID一致                                          |
|         |              |                  |          |                                                                |
|         |              |                  |          | 住院：就诊流水号（当前住院的首页序号）                         |
+---------+--------------+------------------+----------+----------------------------------------------------------------+
| 4       | ApplyNo      | 报告单号         | varchar  |                                                                |
+---------+--------------+------------------+----------+----------------------------------------------------------------+
| 5       | LogNo        | 医嘱序号         | varchar  | 医嘱的唯一序号，代表住院医嘱或门诊每条收费项目或组合的唯一序号 |
+---------+--------------+------------------+----------+----------------------------------------------------------------+
| 6       | HisApplyNo   | 申请单号         | varchar  | 医嘱所属的申请单号                                             |
+---------+--------------+------------------+----------+----------------------------------------------------------------+
| 7       | GroupNo      | 分组号           | varchar  | 医嘱的分组序号，代表哪些医嘱是在一起的                         |
+---------+--------------+------------------+----------+----------------------------------------------------------------+
| 8       | ItemCode     | 项目代码         | varchar  | ris，lis共用，都代表xmdm                                       |
+---------+--------------+------------------+----------+----------------------------------------------------------------+
| 9       | ItemName     | 项目名称         | varchar  | ris，lis共用，都代表xmmc                                       |
+---------+--------------+------------------+----------+----------------------------------------------------------------+
| 10      | Price        | 单价             | money    |                                                                |
+---------+--------------+------------------+----------+----------------------------------------------------------------+
| 11      | ItemQty      | 数量             | float    |                                                                |
+---------+--------------+------------------+----------+----------------------------------------------------------------+
| 12      | ItemUnit     | 单位             | varchar  |                                                                |
+---------+--------------+------------------+----------+----------------------------------------------------------------+
| 13      | ApplyTime    | 申请时间         | datetime | 格式：yyyy-MM-dd HH:mm:ss,不能返回null                         |
+---------+--------------+------------------+----------+----------------------------------------------------------------+
| 14      | ReceiveTime  | 确认时间         | datetime | 医嘱的确认时间，不能返回nul                                    |
+---------+--------------+------------------+----------+----------------------------------------------------------------+
| 15      | ApplyDocCode | 申请医生（代码） | varchar  |                                                                |
+---------+--------------+------------------+----------+----------------------------------------------------------------+
| 16      | ApplyDept    | 申请科室（代码） | varchar  |                                                                |
+---------+--------------+------------------+----------+----------------------------------------------------------------+
| 17      | ExecDept     | 执行科室（代码） | varchar  |                                                                |
+---------+--------------+------------------+----------+----------------------------------------------------------------+
| 18      | OperatorCode | 操作者代码       | Varchar  |                                                                |
+---------+--------------+------------------+----------+----------------------------------------------------------------+
| 19      | OperatorName | 操作者           | varchar  |                                                                |
+---------+--------------+------------------+----------+----------------------------------------------------------------+
| 20      | ItemType     | 收费类型         | varchar  | 0临床项目1收费项目2药品项目                                    |
+---------+--------------+------------------+----------+----------------------------------------------------------------+
| 21      | Status       | 状态             | varchar  | 0：未确认 1：已确认                                            |
+---------+--------------+------------------+----------+----------------------------------------------------------------+
| 22      | ChargeFlag   | 收费标志         | varchar  | 0：未收费，1：已收费;住院一般都为0，普通门诊病人为1            |
+---------+--------------+------------------+----------+----------------------------------------------------------------+
| 23      | AddType      | 上传标志         | varchar  | 0：下载 1上传                                                  |
+---------+--------------+------------------+----------+----------------------------------------------------------------+

获取申请单信息(usp\_yjjk\_getsqdxx)
-----------------------------------

**业务描述：**

医技系统获取申请单信息时调用的接口，检查用于检查登记，检验用于用血申请单接收

**接口描述：**

接口入参：

+---------+------------+----------------+----------+----------------------------------------+
| **NO.** | **变量名** | **含义**       | **类型** | **备注说明**                           |
+---------+------------+----------------+----------+----------------------------------------+
| 1       | brlb       | 病人类别       | varchar  | 0门诊 1住院 3体检                      |
+---------+------------+----------------+----------+----------------------------------------+
| 2       | patid      | 病人唯一标识   | varchar  | 病人唯一标识                           |
+---------+------------+----------------+----------+----------------------------------------+
| 3       | curno      | 病人就诊流水号 | varchar  | 门诊：与PatientID一致                  |
|         |            |                |          |                                        |
|         |            |                |          | 住院：就诊流水号（当前住院的首页序号） |
+---------+------------+----------------+----------+----------------------------------------+
| 4       | sqdh       | 申请单号       | Int      |                                        |
+---------+------------+----------------+----------+----------------------------------------+

> 接口出参：（如果传入的参数错误或者不存在病人信息则返回 "F,错误信息"。否则返回以下信息）

+---------+------------+----------------+----------+-------------------------------------------------------------------------------------+
| **NO.** | **变量名** | **含义**       | **类型** | **备注说明**                                                                        |
+---------+------------+----------------+----------+-------------------------------------------------------------------------------------+
| 1       | brlb       | 病人类别       | Int      | 0门诊 1住院 3体检                                                                   |
+---------+------------+----------------+----------+-------------------------------------------------------------------------------------+
| 2       | patid      | 病人唯一标识   | varchar  | 病人唯一标识                                                                        |
+---------+------------+----------------+----------+-------------------------------------------------------------------------------------+
| 3       | cureno     | 病人就诊流水号 | varchar  | 门诊：与PatientID一致                                                               |
|         |            |                |          |                                                                                     |
|         |            |                |          | 住院：就诊流水号（当前住院的首页序号）                                              |
+---------+------------+----------------+----------+-------------------------------------------------------------------------------------+
| 4       | memo       | 备注           | varchar  |                                                                                     |
+---------+------------+----------------+----------+-------------------------------------------------------------------------------------+
| 5       | 收费项目   | 收费项目总合   | varchar  | 每个收费项目，用回车符号隔开                                                        |
+---------+------------+----------------+----------+-------------------------------------------------------------------------------------+
| 6       | 总体描述   | 申请单总体描述 | varchar  | 申请单上有很多键值，所有的键值可以(caption)+\':\'+rtrim(value)+回车符，累计在一起。 |
+---------+------------+----------------+----------+-------------------------------------------------------------------------------------+

未执行医嘱病人的列表(usp\_yjjk\_jcbrcx)
---------------------------------------

**业务描述：**

此接口用于住院类病人检查项目的数据查询，住院类数据一般以列表形式展现，需要同时返回病人信息和项目信息，一般应用于住院预约和用血申请单的接收，需要注意的是如果用于用血申请单的接收，则要将用血申请单上的项目设置为检查类别。\
**接口描述：**

传入参数：

  --------- ------------ ---------- ---------- --------------
  **NO.**   **变量名**   **含义**   **类型**   **备注说明**
  1         bqdm         病区代码   varchar    
  2         ksdm         科室代码   varchar    默认可为空
  3         cwdm         床位代码   varchar    默认可为空
  4         hzxm         病人姓名   varchar    默认可为空
  5         ksrq         开始日期   varchar    
  6         jsrq         结束日期   varchar    
  --------- ------------ ---------- ---------- --------------

传出参数：

  --------- -------------- -------------------------------- ----------------------------------------------------------------
  **NO.**   **变量名**     **含义**                         **备注说明**
  1         ItemCode       项目代码                         
  2         ItemName       项目名称                         
  3         ItemType       项目类别                         0 临床 1 收费
  4         ApplyNo        报告单号                         默认为-1
  5         LogNo          医嘱序号                         医嘱的唯一序号，代表住院医嘱或门诊每条收费项目或组合的唯一序号
  6         HisApplyNo     申请单序号                       医嘱所属的申请单号
  7         GroupNo        分组号                           医嘱的分组序号，代表哪些医嘱是在一起的
  8         Price          单价                             
  9         ItemQty        项目数量                         
  10        ItemUnit       项目单位                         
  11        ApplyTime      申请时间                         
  12        ReceiveTime    确认时间                         
  13        ApplyDocCode   申请医师代码                     
  14        ApplyDept      申请科室代码                     
  15        ExecDept       执行科室代码                     
  16        OperatorCode   操作医生代码                     可默认为空
  17        OperatorName   操作医生姓名                     可默认为空
  18        Status         确认状态                         0：未确认 1：已确认
  19        ChargeFlag     收费状态                         0：未收费，1：已收费;住院一般都为0，普通门诊病人为1
  20        AddType        上传标志                         0：下载 1上传
  21        Mjzbz          急诊标志                         0：普通项目；1：急诊项目
  22        Specimen       标本种类代码                     
  23        SpecimenDesc   标本种类名称                     
  24        ItemMemo       项目备注                         
  25        ClinicDesc     临床诊断                         
  26        LabelID        申请单条码号                     
  27        CarryWay       运送方式                         
  28        ReportName     申请单名称                       
  29        brbs           病人病史                         
  30        jcxm           检查项目                         
  31        Ycyrq          预出院日期                       
  32        HospNo         病历号                           
  33        PatientID      病人标志号                       
  34        CureNo         病人就诊唯一号                   
  35        PatName        病人姓名                         
  36        Age            病人年龄                         
  37        AgeUnit        病人年龄单位                     
  38        Sex            病人性别                         
  39        ApplyDept      病人所在科室代码或门诊挂号科室   
  40        Ward           病人所在病区代码                 
  41        bqmc           病人所在病区名称                 
  42        BedNo          病人所在病区的床号               
  43        CardNo         病人的卡号                       
  44        ChargeType     病人的收费类别代码               
  45        ApplyDoctor    病人的主治医生代码               
  46        Address        病人的地址                       
  47        Zip            邮编                             
  48        Career         职业                             
  49        Nation         民族                             
  50        Phone          病人的电话                       
  51        IDNum          病人身份证号                     
  52        ClincDesc      病人的临床诊断                   
  53        yebz           婴儿标志                         
  54        yexh           婴儿序号                         
  55        WardOrReg      病人类别                         0门诊 1住院
  56        Syxh           首页序号                         
  --------- -------------- -------------------------------- ----------------------------------------------------------------
