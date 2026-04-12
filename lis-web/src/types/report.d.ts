export interface Report {
  id: string
  reportNo: string
  specimenId: string
  specimenNo: string
  patientId: string
  patientName: string
  patientGender: string
  patientAge: string
  deptId: number
  deptName: string
  bedNo: string
  clinicalDiagnosis: string
  status: ReportStatus
  reportType: string
  reportTypeName: string
  testTime: string | null
  testUserId: string | null
  testUserName: string | null
  auditTime: string | null
  auditUserId: string | null
  auditUserName: string | null
  auditRemark: string
  firstAuditTime: string | null
  firstAuditUserId: string | null
  firstAuditUserName: string | null
  firstAuditRemark: string
  finalAuditTime: string | null
  finalAuditUserId: string | null
  finalAuditUserName: string | null
  finalAuditRemark: string
  printCount: number
  lastPrintTime: string | null
  createTime: string
  updateTime: string
  isAbnormal: boolean
  isPanic: boolean
}

export interface ReportDetail extends Report {
  results: ReportResult[]
}

export type ReportStatus =
  | 'PENDING'
  | 'TESTING'
  | 'SUBMITTED'
  | 'FIRST_AUDITED'
  | 'FINAL_AUDITED'
  | 'APPROVED'
  | 'REJECTED'
  | 'PUBLISHED'
  | 'CANCELLED'

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
  | 'N'
  | 'H'
  | 'L'
  | 'HH'
  | 'LL'

export interface ReportQueryParams {
  pageNum: number
  pageSize: number
  reportNo?: string
  specimenNo?: string
  patientName?: string
  patientIdNo?: string
  status?: ReportStatus | ''
  reportType?: string
  deptId?: number
  testTimeStart?: string
  testTimeEnd?: string
}

export interface ResultEntryForm {
  reportId: number
  reportItemId?: number
  specimenTestItemId?: number
  itemCode: string
  itemName: string
  itemNameEn?: string
  resultValue: string
  resultText?: string
  resultFlag: ResultFlag
  unit?: string
  referenceLow?: number
  referenceHigh?: number
  referenceText?: string
  panicLow?: number
  panicHigh?: number
  method?: string
  equipmentName?: string
  sort?: number
  remark: string
}

export interface AuditForm {
  reportId: string
  auditResult: 'pass' | 'reject'
  auditRemark: string
  auditLevel?: 'first' | 'final'
}

export interface CriticalValue {
  id: string
  specimenId: string
  specimenNo: string
  reportId: string
  reportNo: string
  patientId: string
  patientName: string
  patientGender: string
  patientAge: string
  deptId: number
  deptName: string
  bedNo: string
  testItemId: string
  itemName: string
  itemCode: string
  resultValue: string
  unit: string
  panicLow: number
  panicHigh: number
  panicType: PanicType
  panicTypeName: string
  handleStatus: number
  handleStatusName: string
  reportTime: string
  handleTime: string | null
  handleUserId: string | null
  handleUserName: string | null
  handleResult: string
  receiveUserName: string
  notifyTime: string | null
  createTime: string
}

export type PanicType = 'high' | 'low'

export interface CriticalValueQueryParams {
  pageNum: number
  pageSize: number
  specimenNo?: string
  patientName?: string
  panicType?: PanicType | ''
  handleStatus?: number | ''
  deptId?: number
  reportTimeStart?: string
  reportTimeEnd?: string
}

export interface CriticalValueHandleForm {
  id: string
  receiveUserName: string
  notifyTime: string
  handleResult: string
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
