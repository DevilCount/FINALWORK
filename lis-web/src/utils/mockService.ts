// 模拟数据服务
import storageService from './mockStorage'
import type { LoginResult, UserInfo, CaptchaResult } from '@/types/user'

// 生成随机ID
function generateId(): number {
  return Math.floor(Math.random() * 1000000)
}

// 生成随机条码
function generateBarcode(): string {
  const timestamp = Date.now().toString().slice(-6)
  const random = Math.floor(Math.random() * 1000).toString().padStart(3, '0')
  return `BC${timestamp}${random}`
}

// 生成随机标本编号
function generateSpecimenNo(): string {
  const date = new Date().toISOString().slice(0, 10).replace(/-/g, '')
  const random = Math.floor(Math.random() * 1000).toString().padStart(3, '0')
  return `SP${date}${random}`
}

// 生成随机报告编号
function generateReportNo(): string {
  const date = new Date().toISOString().slice(0, 10).replace(/-/g, '')
  const random = Math.floor(Math.random() * 1000).toString().padStart(3, '0')
  return `RP${date}${random}`
}

// 认证服务
class AuthService {
  // 登录
  login(username: string, password: string): Promise<LoginResult> {
    return new Promise((resolve, reject) => {
      const users = storageService.get('users') || []
      const user = users.find((u: any) => u.username === username && u.password === password)
      
      if (user) {
        const token = `mock_token_${Date.now()}`
        const refreshToken = `mock_refresh_token_${Date.now()}`
        
        storageService.set('token', token)
        storageService.set('current_user', user)
        
        const result: LoginResult = {
          accessToken: token,
          refreshToken: refreshToken,
          tokenType: 'Bearer',
          expiresIn: 86400,
          userId: user.id,
          username: user.username,
          realName: user.realName,
          roles: user.roles || [],
          permissions: user.permissions || []
        }
        
        resolve(result)
      } else {
        reject(new Error('用户名或密码错误'))
      }
    })
  }

  // 登出
  logout(): Promise<void> {
    return new Promise((resolve) => {
      storageService.remove('token')
      storageService.remove('current_user')
      resolve()
    })
  }

  // 刷新token
  refreshToken(refreshToken: string): Promise<LoginResult> {
    return new Promise((resolve) => {
      const currentUser = storageService.get('current_user')
      
      if (currentUser) {
        const token = `mock_token_${Date.now()}`
        const newRefreshToken = `mock_refresh_token_${Date.now()}`
        
        storageService.set('token', token)
        
        const result: LoginResult = {
          accessToken: token,
          refreshToken: newRefreshToken,
          tokenType: 'Bearer',
          expiresIn: 86400,
          userId: currentUser.id,
          username: currentUser.username,
          realName: currentUser.realName,
          roles: currentUser.roles || [],
          permissions: currentUser.permissions || []
        }
        
        resolve(result)
      } else {
        throw new Error('未登录')
      }
    })
  }

  // 获取验证码
  getCaptcha(): Promise<CaptchaResult> {
    return new Promise((resolve) => {
      const result: CaptchaResult = {
        uuid: `mock_uuid_${Date.now()}`,
        captchaImage: 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mNkYPhfDwAChwGA60e6kgAAAABJRU5ErkJggg=='
      }
      resolve(result)
    })
  }

  // 获取用户信息
  getUserInfo(): Promise<{ user: UserInfo; roles: string[]; permissions: string[] }> {
    return new Promise((resolve) => {
      const currentUser = storageService.get('current_user')
      
      if (currentUser) {
        resolve({
          user: currentUser,
          roles: currentUser.roles || [],
          permissions: currentUser.permissions || []
        })
      } else {
        throw new Error('未登录')
      }
    })
  }

  // 修改密码
  updatePassword(oldPassword: string, newPassword: string): Promise<void> {
    return new Promise((resolve, reject) => {
      const currentUser = storageService.get('current_user')
      const users = storageService.get('users') || []
      
      if (currentUser) {
        const userIndex = users.findIndex((u: any) => u.id === currentUser.id)
        
        if (userIndex !== -1) {
          if (users[userIndex].password === oldPassword) {
            users[userIndex].password = newPassword
            storageService.set('users', users)
            storageService.set('current_user', users[userIndex])
            resolve()
          } else {
            reject(new Error('原密码错误'))
          }
        } else {
          reject(new Error('用户不存在'))
        }
      } else {
        reject(new Error('未登录'))
      }
    })
  }
}

// 标本服务
class SpecimenService {
  // 获取标本列表
  getSpecimenList(params: any): Promise<any> {
    return new Promise((resolve) => {
      let specimens = storageService.get('specimens') || []
      
      // 过滤
      if (params.specimenNo) {
        specimens = specimens.filter((s: any) => s.specimenNo.includes(params.specimenNo))
      }
      if (params.patientName) {
        specimens = specimens.filter((s: any) => s.patientName.includes(params.patientName))
      }
      if (params.patientIdNo) {
        specimens = specimens.filter((s: any) => s.patientIdNo.includes(params.patientIdNo))
      }
      if (params.status) {
        specimens = specimens.filter((s: any) => s.status === params.status)
      }
      
      // 分页
      const pageNum = params.pageNum || 1
      const pageSize = params.pageSize || 10
      const start = (pageNum - 1) * pageSize
      const end = start + pageSize
      const paginatedSpecimens = specimens.slice(start, end)
      
      resolve({
        total: specimens.length,
        list: paginatedSpecimens,
        pageNum,
        pageSize
      })
    })
  }

  // 获取标本详情
  getSpecimenDetail(id: string): Promise<any> {
    return new Promise((resolve, reject) => {
      const specimens = storageService.get('specimens') || []
      const specimen = specimens.find((s: any) => s.id === parseInt(id))
      
      if (specimen) {
        resolve(specimen)
      } else {
        reject(new Error('标本不存在'))
      }
    })
  }

  // 登记标本
  registerSpecimen(data: any): Promise<any> {
    return new Promise((resolve) => {
      const specimens = storageService.get('specimens') || []
      const testItems = storageService.get('test_items') || []
      
      const newSpecimen = {
        id: generateId(),
        specimenNo: generateSpecimenNo(),
        barcode: generateBarcode(),
        patientId: data.patientId,
        patientName: data.patientName,
        patientGender: data.patientGender,
        patientAge: data.patientAge,
        patientIdNo: data.patientIdNo,
        patientPhone: data.patientPhone,
        deptId: data.deptId,
        deptName: data.deptName,
        wardId: data.wardId,
        wardName: data.wardName,
        bedNo: data.bedNo,
        doctorId: data.doctorId,
        doctorName: data.doctorName,
        specimenTypeId: data.specimenTypeId,
        specimenTypeName: data.specimenTypeName,
        collectTime: data.collectTime,
        collectUserId: data.collectUserId,
        collectUserName: data.collectUserName,
        isStat: data.isStat,
        clinicalDiagnosis: data.clinicalDiagnosis,
        remark: data.remark,
        testItemIds: data.testItemIds,
        testItems: data.testItemIds.map((id: number) => {
          const testItem = testItems.find((t: any) => t.id === id)
          return testItem ? { id: testItem.id, testItemName: testItem.testItemName } : { id, testItemName: `检验项目${id}` }
        }),
        status: 'REGISTERED',
        createTime: new Date().toISOString(),
        updateTime: new Date().toISOString()
      }
      
      specimens.push(newSpecimen)
      storageService.set('specimens', specimens)
      
      // 创建对应的报告
      this.createReportForSpecimen(newSpecimen)
      
      resolve(newSpecimen)
    })
  }

  // 为标本创建报告
  private createReportForSpecimen(specimen: any): void {
    const reports = storageService.get('reports') || []
    
    const newReport = {
      id: generateId(),
      reportNo: generateReportNo(),
      specimenId: specimen.id,
      specimenNo: specimen.specimenNo,
      patientId: specimen.patientId,
      patientName: specimen.patientName,
      patientGender: specimen.patientGender,
      patientAge: specimen.patientAge,
      patientIdNo: specimen.patientIdNo,
      deptId: specimen.deptId,
      deptName: specimen.deptName,
      doctorId: specimen.doctorId,
      doctorName: specimen.doctorName,
      status: 'PENDING',
      reportType: '常规',
      testTime: new Date().toISOString(),
      reportTime: null,
      items: specimen.testItemIds.map((testItemId: number, index: number) => {
        const testItem = specimen.testItems.find((t: any) => t.id === testItemId)
        return {
          id: generateId(),
          reportId: 0, // 会在下面设置
          testItemId,
          testItemName: testItem ? testItem.testItemName : `检验项目${testItemId}`,
          result: null,
          referenceRange: this.getReferenceRange(testItemId),
          unit: this.getUnit(testItemId),
          status: 'PENDING',
          operatorId: null,
          operatorName: null,
          operateTime: null
        }
      }),
      createTime: new Date().toISOString(),
      updateTime: new Date().toISOString()
    }
    
    // 设置报告ID到项目中
    newReport.items.forEach((item: any) => {
      item.reportId = newReport.id
    })
    
    reports.push(newReport)
    storageService.set('reports', reports)
  }

  // 获取参考范围
  private getReferenceRange(testItemId: number): string {
    const ranges: Record<number, string> = {
      1: '4.0-10.0',
      2: '0-10',
      3: '0-40',
      4: '0-100',
      5: '0-5.2',
      6: '3.9-6.1',
      7: '135-145',
      8: '0-25'
    }
    return ranges[testItemId] || '0-100'
  }

  // 获取单位
  private getUnit(testItemId: number): string {
    const units: Record<number, string> = {
      1: '10^9/L',
      2: '个/HP',
      3: 'U/L',
      4: 'μmol/L',
      5: 'mmol/L',
      6: 'mmol/L',
      7: 'mmol/L',
      8: 'U/L'
    }
    return units[testItemId] || 'mg/L'
  }

  // 通过条码获取标本
  getSpecimenByBarcode(barcode: string): Promise<any> {
    return new Promise((resolve, reject) => {
      const specimens = storageService.get('specimens') || []
      const specimen = specimens.find((s: any) => s.barcode === barcode)
      
      if (specimen) {
        resolve(specimen)
      } else {
        reject(new Error('标本不存在'))
      }
    })
  }

  // 通过标本编号获取标本
  getSpecimenBySpecimenNo(specimenNo: string): Promise<any> {
    return new Promise((resolve, reject) => {
      const specimens = storageService.get('specimens') || []
      const specimen = specimens.find((s: any) => s.specimenNo === specimenNo)
      
      if (specimen) {
        resolve(specimen)
      } else {
        reject(new Error('标本不存在'))
      }
    })
  }

  // 接收标本
  receiveSpecimen(barcode: string): Promise<void> {
    return new Promise((resolve, reject) => {
      const specimens = storageService.get('specimens') || []
      const specimenIndex = specimens.findIndex((s: any) => s.barcode === barcode)
      
      if (specimenIndex !== -1) {
        specimens[specimenIndex].status = 'RECEIVED'
        specimens[specimenIndex].updateTime = new Date().toISOString()
        storageService.set('specimens', specimens)
        resolve()
      } else {
        reject(new Error('标本不存在'))
      }
    })
  }

  // 拒收标本
  rejectSpecimen(barcode: string, rejectReason: string): Promise<void> {
    return new Promise((resolve, reject) => {
      const specimens = storageService.get('specimens') || []
      const specimenIndex = specimens.findIndex((s: any) => s.barcode === barcode)
      
      if (specimenIndex !== -1) {
        specimens[specimenIndex].status = 'REJECTED'
        specimens[specimenIndex].rejectReason = rejectReason
        specimens[specimenIndex].updateTime = new Date().toISOString()
        storageService.set('specimens', specimens)
        resolve()
      } else {
        reject(new Error('标本不存在'))
      }
    })
  }

  // 更新标本状态
  updateSpecimenStatus(data: { specimenId: number; status: string; remark?: string }): Promise<void> {
    return new Promise((resolve, reject) => {
      const specimens = storageService.get('specimens') || []
      const specimenIndex = specimens.findIndex((s: any) => s.id === data.specimenId)
      
      if (specimenIndex !== -1) {
        specimens[specimenIndex].status = data.status
        if (data.remark) {
          specimens[specimenIndex].remark = data.remark
        }
        specimens[specimenIndex].updateTime = new Date().toISOString()
        storageService.set('specimens', specimens)
        resolve()
      } else {
        reject(new Error('标本不存在'))
      }
    })
  }

  // 存储标本
  storageSpecimen(data: { specimenId: number; storageLocation: string; operatorId?: number }): Promise<void> {
    return new Promise((resolve, reject) => {
      const specimens = storageService.get('specimens') || []
      const specimenIndex = specimens.findIndex((s: any) => s.id === data.specimenId)
      
      if (specimenIndex !== -1) {
        specimens[specimenIndex].status = 'STORAGE'
        specimens[specimenIndex].storageLocation = data.storageLocation
        specimens[specimenIndex].updateTime = new Date().toISOString()
        storageService.set('specimens', specimens)
        resolve()
      } else {
        reject(new Error('标本不存在'))
      }
    })
  }

  // 获取标本追踪信息
  getSpecimenTraceBySpecimenId(specimenId: string): Promise<any[]> {
    return new Promise((resolve) => {
      const specimens = storageService.get('specimens') || []
      const specimen = specimens.find((s: any) => s.id === parseInt(specimenId))
      
      if (specimen) {
        const traces = [
          {
            id: 1,
            specimenId: specimen.id,
            specimenNo: specimen.specimenNo,
            action: 'REGISTER',
            actionName: '登记',
            operatorId: specimen.collectUserId,
            operatorName: specimen.collectUserName,
            actionTime: specimen.createTime,
            remark: '标本登记'
          },
          {
            id: 2,
            specimenId: specimen.id,
            specimenNo: specimen.specimenNo,
            action: 'COLLECT',
            actionName: '采集',
            operatorId: specimen.collectUserId,
            operatorName: specimen.collectUserName,
            actionTime: specimen.collectTime,
            remark: '标本采集'
          }
        ]
        
        if (specimen.status === 'RECEIVED') {
          traces.push({
            id: 3,
            specimenId: specimen.id,
            specimenNo: specimen.specimenNo,
            action: 'RECEIVE',
            actionName: '接收',
            operatorId: 1,
            operatorName: '管理员',
            actionTime: specimen.updateTime,
            remark: '标本接收'
          })
        }
        
        resolve(traces)
      } else {
        resolve([])
      }
    })
  }

  // 获取标本类型
  getSpecimenTypes(): Promise<any[]> {
    return new Promise((resolve) => {
      const specimenTypes = storageService.get('specimen_types') || []
      resolve(specimenTypes)
    })
  }

  // 获取检验项目
  getTestItems(): Promise<any[]> {
    return new Promise((resolve) => {
      const testItems = storageService.get('test_items') || []
      resolve(testItems)
    })
  }

  // 获取检验项目分类
  getTestItemCategories(): Promise<string[]> {
    return new Promise((resolve) => {
      const testItems = storageService.get('test_items') || []
      const categories = [...new Set(testItems.map((t: any) => t.category))]
      resolve(categories)
    })
  }
}

// 报告服务
class ReportService {
  // 获取报告列表
  getReportList(params: any): Promise<any> {
    return new Promise((resolve) => {
      let reports = storageService.get('reports') || []
      
      // 过滤
      if (params.reportNo) {
        reports = reports.filter((r: any) => r.reportNo.includes(params.reportNo))
      }
      if (params.specimenNo) {
        reports = reports.filter((r: any) => r.specimenNo.includes(params.specimenNo))
      }
      if (params.patientName) {
        reports = reports.filter((r: any) => r.patientName.includes(params.patientName))
      }
      if (params.patientIdNo) {
        reports = reports.filter((r: any) => r.patientIdNo.includes(params.patientIdNo))
      }
      if (params.status) {
        reports = reports.filter((r: any) => r.status === params.status)
      }
      
      // 分页
      const pageNum = params.pageNum || 1
      const pageSize = params.pageSize || 10
      const start = (pageNum - 1) * pageSize
      const end = start + pageSize
      const paginatedReports = reports.slice(start, end)
      
      resolve({
        total: reports.length,
        list: paginatedReports,
        pageNum,
        pageSize
      })
    })
  }

  // 获取报告详情
  getReportDetail(id: string): Promise<any> {
    return new Promise((resolve, reject) => {
      const reports = storageService.get('reports') || []
      const report = reports.find((r: any) => r.id === parseInt(id))
      
      if (report) {
        resolve(report)
      } else {
        reject(new Error('报告不存在'))
      }
    })
  }

  // 录入结果
  saveResultEntry(data: any): Promise<number> {
    return new Promise((resolve, reject) => {
      const reports = storageService.get('reports') || []
      const reportIndex = reports.findIndex((r: any) => r.id === data.reportId)
      
      if (reportIndex !== -1) {
        const report = reports[reportIndex]
        const itemIndex = report.items.findIndex((i: any) => i.id === data.id)
        
        if (itemIndex !== -1) {
          report.items[itemIndex].result = data.result
          report.items[itemIndex].status = 'COMPLETED'
          report.items[itemIndex].operatorId = 1
          report.items[itemIndex].operatorName = '管理员'
          report.items[itemIndex].operateTime = new Date().toISOString()
          
          // 检查是否所有项目都已完成
          const allCompleted = report.items.every((i: any) => i.status === 'COMPLETED')
          if (allCompleted) {
            report.status = 'TESTING'
          }
          
          report.updateTime = new Date().toISOString()
          storageService.set('reports', reports)
          resolve(data.id)
        } else {
          reject(new Error('报告项目不存在'))
        }
      } else {
        reject(new Error('报告不存在'))
      }
    })
  }

  // 批量录入结果
  batchSaveResultEntry(data: { reportId: number; items: any[] }): Promise<void> {
    return new Promise((resolve, reject) => {
      const reports = storageService.get('reports') || []
      const reportIndex = reports.findIndex((r: any) => r.id === data.reportId)
      
      if (reportIndex !== -1) {
        const report = reports[reportIndex]
        
        data.items.forEach((itemData) => {
          const itemIndex = report.items.findIndex((i: any) => i.id === itemData.id)
          if (itemIndex !== -1) {
            report.items[itemIndex].result = itemData.result
            report.items[itemIndex].status = 'COMPLETED'
            report.items[itemIndex].operatorId = 1
            report.items[itemIndex].operatorName = '管理员'
            report.items[itemIndex].operateTime = new Date().toISOString()
          }
        })
        
        // 检查是否所有项目都已完成
        const allCompleted = report.items.every((i: any) => i.status === 'COMPLETED')
        if (allCompleted) {
          report.status = 'TESTING'
        }
        
        report.updateTime = new Date().toISOString()
        storageService.set('reports', reports)
        resolve()
      } else {
        reject(new Error('报告不存在'))
      }
    })
  }

  // 提交报告
  submitReport(reportId: string): Promise<void> {
    return new Promise((resolve, reject) => {
      const reports = storageService.get('reports') || []
      const reportIndex = reports.findIndex((r: any) => r.id === parseInt(reportId))
      
      if (reportIndex !== -1) {
        reports[reportIndex].status = 'SUBMITTED'
        reports[reportIndex].updateTime = new Date().toISOString()
        storageService.set('reports', reports)
        resolve()
      } else {
        reject(new Error('报告不存在'))
      }
    })
  }

  // 审核报告
  auditReport(data: any): Promise<void> {
    return new Promise((resolve, reject) => {
      const reports = storageService.get('reports') || []
      const reportIndex = reports.findIndex((r: any) => r.id === data.reportId)
      
      if (reportIndex !== -1) {
        if (data.auditResult === 'pass' || data.auditResult === 'approved') {
          reports[reportIndex].status = 'APPROVED'
        } else {
          reports[reportIndex].status = 'REJECTED'
        }
        reports[reportIndex].auditRemark = data.auditRemark
        reports[reportIndex].updateTime = new Date().toISOString()
        storageService.set('reports', reports)
        resolve()
      } else {
        reject(new Error('报告不存在'))
      }
    })
  }

  // 发布报告
  publishReport(data: any): Promise<void> {
    return new Promise((resolve, reject) => {
      const reports = storageService.get('reports') || []
      const reportIndex = reports.findIndex((r: any) => r.id === data.reportId)
      
      if (reportIndex !== -1) {
        reports[reportIndex].status = 'PUBLISHED'
        reports[reportIndex].reportTime = new Date().toISOString()
        reports[reportIndex].updateTime = new Date().toISOString()
        storageService.set('reports', reports)
        resolve()
      } else {
        reject(new Error('报告不存在'))
      }
    })
  }
}

// 系统服务
class SystemService {
  // 获取科室树
  getDepartments(): Promise<any[]> {
    return new Promise((resolve) => {
      const departments = storageService.get('departments') || []
      resolve(departments)
    })
  }
}

// 导出服务实例
const authService = new AuthService()
const specimenService = new SpecimenService()
const reportService = new ReportService()
const systemService = new SystemService()

export { authService, specimenService, reportService, systemService }
export default {
  auth: authService,
  specimen: specimenService,
  report: reportService,
  system: systemService
}