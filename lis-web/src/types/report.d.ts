export interface Report {
  id: string
  reportCode: string
  specimenId: string
  specimenCode: string
  patientId: string
  patientName: string
  patientGender: string
  patientAge: number
  departmentId: string
  departmentName: string
  bedNo: string
  diagnosis: string
  reportStatus: ReportStatus
  reportStatusName: string
  reportType: string
  reportTypeName: string
  submitTime: string | null
  submitUserId: string | null
  submitUserName: string | null
  auditTime: string | null
  auditUserId: string | null
  auditUserName: string | null
  auditRemark: string
  printCount: number
  lastPrintTime: string | null
  createTime: string
  updateTime: string
  results: ReportResult[]
}

export type ReportStatus = 
  | 'draft'
  | 'submitted'
  | 'audited'
  | 'rejected'
  | 'printed'

export interface ReportResult {
  id: string
  reportId: string
  testItemId: string
  testItemCode: string
  testItemName: string
  resultValue: string
  resultUnit: string
  referenceRange: string
  resultFlag: ResultFlag
  resultFlagName: string
  isAbnormal: boolean
  isCritical: boolean
  remark: string
  testMethod: string
  testEquipment: string
  testerId: string
  testerName: string
  testTime: string
}

export type ResultFlag = 
  | 'normal'
  | 'high'
  | 'low'
  | 'critical_high'
  | 'critical_low'

export interface ReportQueryParams {
  pageNum: number
  pageSize: number
  reportCode?: string
  specimenCode?: string
  patientName?: string
  patientIdCard?: string
  reportStatus?: ReportStatus | ''
  reportType?: string
  departmentId?: string
  submitTimeStart?: string
  submitTimeEnd?: string
}

export interface ResultEntryForm {
  specimenId: string
  results: ResultEntryItem[]
}

export interface ResultEntryItem {
  testItemId: string
  resultValue: string
  resultFlag: ResultFlag
  isAbnormal: boolean
  isCritical: boolean
  remark: string
}

export interface AuditForm {
  reportId: string
  auditResult: 'pass' | 'reject'
  auditRemark: string
}

export interface CriticalValue {
  id: string
  specimenId: string
  specimenCode: string
  reportId: string
  reportCode: string
  patientId: string
  patientName: string
  patientGender: string
  patientAge: number
  departmentId: string
  departmentName: string
  bedNo: string
  testItemId: string
  testItemName: string
  testItemCode: string
  resultValue: string
  resultUnit: string
  referenceRange: string
  criticalLevel: CriticalLevel
  criticalLevelName: string
  handleStatus: CriticalHandleStatus
  handleStatusName: string
  reportTime: string
  handleTime: string | null
  handlerId: string | null
  handlerName: string | null
  handleRemark: string
  notifyDoctorName: string
  notifyTime: string | null
  createTime: string
}

export type CriticalLevel = 'high' | 'low'

export type CriticalHandleStatus = 
  | 'pending'
  | 'notified'
  | 'handled'

export interface CriticalValueQueryParams {
  pageNum: number
  pageSize: number
  specimenCode?: string
  patientName?: string
  criticalLevel?: CriticalLevel | ''
  handleStatus?: CriticalHandleStatus | ''
  departmentId?: string
  reportTimeStart?: string
  reportTimeEnd?: string
}

export interface CriticalValueHandleForm {
  id: string
  notifyDoctorName: string
  notifyTime: string
  handleRemark: string
}

export interface CriticalValueRecord {
  id: string
  criticalValueId: string
  operationType: string
  operationTypeName: string
  operatorId: string
  operatorName: string
  operationTime: string
  operationDesc: string
}
