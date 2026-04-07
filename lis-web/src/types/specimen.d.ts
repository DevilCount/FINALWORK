export interface Specimen {
  id: string
  specimenCode: string
  specimenType: string
  specimenTypeName: string
  patientId: string
  patientName: string
  patientGender: string
  patientAge: number
  patientPhone: string
  patientIdCard: string
  departmentId: string
  departmentName: string
  bedNo: string
  diagnosis: string
  collectTime: string | null
  collectUserId: string | null
  collectUserName: string | null
  receiveTime: string | null
  receiveUserId: string | null
  receiveUserName: string | null
  status: SpecimenStatus
  statusName: string
  remark: string
  createTime: string
  updateTime: string
  testItems: SpecimenTestItem[]
}

export type SpecimenStatus = 
  | 'pending_collect'
  | 'collected'
  | 'received'
  | 'testing'
  | 'completed'
  | 'rejected'

export interface SpecimenTestItem {
  id: string
  specimenId: string
  testItemId: string
  testItemName: string
  testItemCode: string
  price: number
  status: string
  statusName: string
  referenceRange?: string
  resultUnit?: string
}

export interface SpecimenQueryParams {
  pageNum: number
  pageSize: number
  specimenCode?: string
  patientName?: string
  patientIdCard?: string
  status?: SpecimenStatus | ''
  specimenType?: string
  departmentId?: string
  collectTimeStart?: string
  collectTimeEnd?: string
}

export interface SpecimenRegisterForm {
  patientId?: string
  patientName: string
  patientGender: string
  patientAge: number
  patientPhone: string
  patientIdCard: string
  departmentId: string
  bedNo: string
  diagnosis: string
  specimenType: string
  testItemIds: string[]
  remark: string
}

export interface SpecimenTraceRecord {
  id: string
  specimenId: string
  specimenCode: string
  operationType: string
  operationTypeName: string
  operationDesc: string
  operatorId: string
  operatorName: string
  operationTime: string
  fromStatus: string
  fromStatusName: string
  toStatus: string
  toStatusName: string
  remark: string
}

export interface SpecimenTraceQueryParams {
  specimenCode?: string
  patientName?: string
  operationType?: string
  operationTimeStart?: string
  operationTimeEnd?: string
}

export interface TestItem {
  id: string
  itemCode: string
  itemName: string
  itemCategory: string
  itemCategoryName: string
  sampleType: string
  price: number
  turnaroundTime: number
  referenceRange: string
  unit: string
  status: number
  remark: string
}

export interface TestItemQueryParams {
  pageNum: number
  pageSize: number
  itemCode?: string
  itemName?: string
  itemCategory?: string
  status?: number
}

export interface SpecimenType {
  code: string
  name: string
  description: string
  status: number
}

export interface SpecimenDetail extends Specimen {
  flowRecords: SpecimenFlowRecord[]
}

export interface SpecimenFlowRecord {
  id: string
  specimenId: string
  flowType: string
  flowTypeName: string
  flowTime: string
  operatorId: string
  operatorName: string
  fromLocation: string
  toLocation: string
  remark: string
}
