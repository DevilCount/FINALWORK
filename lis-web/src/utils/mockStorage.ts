// 模拟数据存储服务

// 存储键名
const STORAGE_KEYS = {
  USERS: 'lis_mock_users',
  SPECIMENS: 'lis_mock_specimens',
  REPORTS: 'lis_mock_reports',
  DEPARTMENTS: 'lis_mock_departments',
  TEST_ITEMS: 'lis_mock_test_items',
  SPECIMEN_TYPES: 'lis_mock_specimen_types',
  TOKEN: 'lis_mock_token',
  CURRENT_USER: 'lis_mock_current_user'
}

// 基础存储服务
class StorageService {
  private prefix: string = 'lis_mock_'

  // 存储数据
  set(key: string, data: any): void {
    try {
      const jsonData = JSON.stringify(data)
      localStorage.setItem(`${this.prefix}${key}`, jsonData)
    } catch (error) {
      // 静默处理 localStorage 存储错误
    }
  }

  // 获取数据
  get(key: string): any {
    try {
      const jsonData = localStorage.getItem(`${this.prefix}${key}`)
      return jsonData ? JSON.parse(jsonData) : null
    } catch (error) {
      return null
    }
  }

  // 删除数据
  remove(key: string): void {
    try {
      localStorage.removeItem(`${this.prefix}${key}`)
    } catch (error) {
      // 静默处理 localStorage 删除错误
    }
  }

  // 清除所有数据
  clear(): void {
    try {
      Object.values(STORAGE_KEYS).forEach(key => {
        localStorage.removeItem(key)
      })
    } catch (error) {
      // 静默处理 localStorage 清空错误
    }
  }
}

const storageService = new StorageService()

// 初始化默认数据
function initDefaultData() {
  // 初始化用户数据
  if (!storageService.get('users')) {
    const defaultUsers = [
      {
        id: 1,
        username: 'admin',
        password: '123456',
        realName: '管理员',
        avatar: '',
        phone: '13800138000',
        email: 'admin@example.com',
        deptId: 1,
        deptName: '检验科',
        employeeNo: 'EMP001',
        status: 1,
        roles: ['admin'],
        permissions: ['*']
      },
      {
        id: 2,
        username: 'user',
        password: '123456',
        realName: '普通用户',
        avatar: '',
        phone: '13900139000',
        email: 'user@example.com',
        deptId: 1,
        deptName: '检验科',
        employeeNo: 'EMP002',
        status: 1,
        roles: ['user'],
        permissions: ['specimen:read', 'report:read']
      },
      {
        id: 3,
        username: 'technician',
        password: '123456',
        realName: '检验技师',
        avatar: '',
        phone: '13700137000',
        email: 'technician@example.com',
        deptId: 1,
        deptName: '检验科',
        employeeNo: 'EMP003',
        status: 1,
        roles: ['technician'],
        permissions: ['specimen:read', 'specimen:write', 'report:read', 'report:write']
      },
      {
        id: 4,
        username: 'doctor',
        password: '123456',
        realName: '审核医生',
        avatar: '',
        phone: '13600136000',
        email: 'doctor@example.com',
        deptId: 2,
        deptName: '内科',
        employeeNo: 'EMP004',
        status: 1,
        roles: ['doctor'],
        permissions: ['specimen:read', 'report:read', 'report:audit']
      }
    ]
    storageService.set('users', defaultUsers)
  }

  // 初始化科室数据
  if (!storageService.get('departments')) {
    const defaultDepartments = [
      { id: 1, deptName: '检验科', parentId: 0, sort: 1 },
      { id: 2, deptName: '内科', parentId: 0, sort: 2 },
      { id: 3, deptName: '外科', parentId: 0, sort: 3 },
      { id: 4, deptName: '儿科', parentId: 0, sort: 4 },
      { id: 5, deptName: '妇科', parentId: 0, sort: 5 },
      { id: 6, deptName: '血液科', parentId: 0, sort: 6 },
      { id: 7, deptName: '急诊科', parentId: 0, sort: 7 }
    ]
    storageService.set('departments', defaultDepartments)
  }

  // 初始化标本类型数据
  if (!storageService.get('specimen_types')) {
    const defaultSpecimenTypes = [
      { id: 1, specimenTypeName: '血液', sort: 1 },
      { id: 2, specimenTypeName: '尿液', sort: 2 },
      { id: 3, specimenTypeName: '粪便', sort: 3 },
      { id: 4, specimenTypeName: '脑脊液', sort: 4 },
      { id: 5, specimenTypeName: '胸水', sort: 5 },
      { id: 6, specimenTypeName: '腹水', sort: 6 },
      { id: 7, specimenTypeName: '痰液', sort: 7 },
      { id: 8, specimenTypeName: '分泌物', sort: 8 }
    ]
    storageService.set('specimen_types', defaultSpecimenTypes)
  }

  // 初始化检验项目数据
  if (!storageService.get('test_items')) {
    const defaultTestItems = [
      { id: 1, testItemName: '血常规', category: '血液检查', sort: 1 },
      { id: 2, testItemName: '尿常规', category: '尿液检查', sort: 2 },
      { id: 3, testItemName: '肝功能', category: '生化检查', sort: 3 },
      { id: 4, testItemName: '肾功能', category: '生化检查', sort: 4 },
      { id: 5, testItemName: '血脂', category: '生化检查', sort: 5 },
      { id: 6, testItemName: '血糖', category: '生化检查', sort: 6 },
      { id: 7, testItemName: '电解质', category: '生化检查', sort: 7 },
      { id: 8, testItemName: '心肌酶', category: '生化检查', sort: 8 },
      { id: 9, testItemName: '凝血功能', category: '血液检查', sort: 9 },
      { id: 10, testItemName: '血型鉴定', category: '血液检查', sort: 10 },
      { id: 11, testItemName: '乙肝五项', category: '免疫检查', sort: 11 },
      { id: 12, testItemName: '甲肝抗体', category: '免疫检查', sort: 12 }
    ]
    storageService.set('test_items', defaultTestItems)
  }

  // 初始化标本数据
  if (!storageService.get('specimens')) {
    const defaultSpecimens = [
      {
        id: 1,
        specimenNo: 'SP20260412001',
        barcode: 'BC20260412001',
        patientId: 'P12345',
        patientName: '张三',
        patientGender: '男',
        patientAge: 30,
        patientIdNo: '110101199601011234',
        patientPhone: '13800138000',
        deptId: 1,
        deptName: '检验科',
        wardId: 1,
        wardName: '内科病房',
        bedNo: '101',
        doctorId: 1,
        doctorName: '王医生',
        specimenTypeId: 1,
        specimenTypeName: '血液',
        collectTime: new Date().toISOString(),
        collectUserId: 1,
        collectUserName: '管理员',
        isStat: false,
        clinicalDiagnosis: '健康体检',
        remark: '',
        testItemIds: [1, 3, 5],
        testItems: [
          { id: 1, testItemName: '血常规' },
          { id: 3, testItemName: '肝功能' },
          { id: 5, testItemName: '血脂' }
        ],
        status: 'REGISTERED',
        createTime: new Date().toISOString(),
        updateTime: new Date().toISOString()
      },
      {
        id: 2,
        specimenNo: 'SP20260412002',
        barcode: 'BC20260412002',
        patientId: 'P12346',
        patientName: '李四',
        patientGender: '女',
        patientAge: 25,
        patientIdNo: '110101200101011234',
        patientPhone: '13900139000',
        deptId: 2,
        deptName: '内科',
        wardId: 2,
        wardName: '外科病房',
        bedNo: '201',
        doctorId: 2,
        doctorName: '李医生',
        specimenTypeId: 2,
        specimenTypeName: '尿液',
        collectTime: new Date().toISOString(),
        collectUserId: 2,
        collectUserName: '普通用户',
        isStat: true,
        clinicalDiagnosis: '尿路感染',
        remark: '加急',
        testItemIds: [2, 4],
        testItems: [
          { id: 2, testItemName: '尿常规' },
          { id: 4, testItemName: '肾功能' }
        ],
        status: 'RECEIVED',
        createTime: new Date().toISOString(),
        updateTime: new Date().toISOString()
      },
      {
        id: 3,
        specimenNo: 'SP20260412003',
        barcode: 'BC20260412003',
        patientId: 'P12347',
        patientName: '王五',
        patientGender: '男',
        patientAge: 45,
        patientIdNo: '110101198101011234',
        patientPhone: '13700137000',
        deptId: 3,
        deptName: '外科',
        wardId: 3,
        wardName: '儿科病房',
        bedNo: '301',
        doctorId: 3,
        doctorName: '张医生',
        specimenTypeId: 1,
        specimenTypeName: '血液',
        collectTime: new Date().toISOString(),
        collectUserId: 3,
        collectUserName: '检验技师',
        isStat: false,
        clinicalDiagnosis: '术前检查',
        remark: '',
        testItemIds: [1, 7, 8],
        testItems: [
          { id: 1, testItemName: '血常规' },
          { id: 7, testItemName: '电解质' },
          { id: 8, testItemName: '心肌酶' }
        ],
        status: 'TESTING',
        createTime: new Date().toISOString(),
        updateTime: new Date().toISOString()
      }
    ]
    storageService.set('specimens', defaultSpecimens)
  }

  // 初始化报告数据
  if (!storageService.get('reports')) {
    const defaultReports = [
      {
        id: 1,
        reportNo: 'RP20260412001',
        specimenId: 1,
        specimenNo: 'SP20260412001',
        patientId: 'P12345',
        patientName: '张三',
        patientGender: '男',
        patientAge: 30,
        patientIdNo: '110101199601011234',
        deptId: 1,
        deptName: '检验科',
        doctorId: 1,
        doctorName: '王医生',
        status: 'PENDING',
        reportType: '常规',
        testTime: new Date().toISOString(),
        reportTime: null,
        items: [
          {
            id: 1,
            reportId: 1,
            testItemId: 1,
            testItemName: '血常规',
            result: null,
            referenceRange: '4.0-10.0',
            unit: '10^9/L',
            status: 'PENDING',
            operatorId: null,
            operatorName: null,
            operateTime: null
          },
          {
            id: 2,
            reportId: 1,
            testItemId: 3,
            testItemName: '肝功能',
            result: null,
            referenceRange: '0-40',
            unit: 'U/L',
            status: 'PENDING',
            operatorId: null,
            operatorName: null,
            operateTime: null
          },
          {
            id: 3,
            reportId: 1,
            testItemId: 5,
            testItemName: '血脂',
            result: null,
            referenceRange: '0-5.2',
            unit: 'mmol/L',
            status: 'PENDING',
            operatorId: null,
            operatorName: null,
            operateTime: null
          }
        ],
        createTime: new Date().toISOString(),
        updateTime: new Date().toISOString()
      },
      {
        id: 2,
        reportNo: 'RP20260412002',
        specimenId: 2,
        specimenNo: 'SP20260412002',
        patientId: 'P12346',
        patientName: '李四',
        patientGender: '女',
        patientAge: 25,
        patientIdNo: '110101200101011234',
        deptId: 2,
        deptName: '内科',
        doctorId: 2,
        doctorName: '李医生',
        status: 'TESTING',
        reportType: '常规',
        testTime: new Date().toISOString(),
        reportTime: null,
        items: [
          {
            id: 4,
            reportId: 2,
            testItemId: 2,
            testItemName: '尿常规',
            result: '5.5',
            referenceRange: '4.5-8.0',
            unit: 'pH',
            status: 'COMPLETED',
            operatorId: 3,
            operatorName: '检验技师',
            operateTime: new Date().toISOString()
          },
          {
            id: 5,
            reportId: 2,
            testItemId: 4,
            testItemName: '肾功能',
            result: '75',
            referenceRange: '44-133',
            unit: 'μmol/L',
            status: 'COMPLETED',
            operatorId: 3,
            operatorName: '检验技师',
            operateTime: new Date().toISOString()
          }
        ],
        createTime: new Date().toISOString(),
        updateTime: new Date().toISOString()
      }
    ]
    storageService.set('reports', defaultReports)
  }
}

// 初始化数据
initDefaultData()

export { storageService, STORAGE_KEYS }
export default storageService