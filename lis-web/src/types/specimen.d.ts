export interface Specimen {
  id: string
  specimenNo: string
  specimenTypeId: number
  specimenTypeName: string
  patientId: string
  patientName: string
  patientGender: string
  patientAge: string
  patientPhone: string
  patientIdNo: string
  deptId: number
  deptName: string
  bedNo: string
  clinicalDiagnosis: string
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
  | 'REGISTERED'
  | 'RECEIVED'
  | 'REJECTED'
  | 'STORAGE'
  | 'TESTING'
  | 'COMPLETED'
  | 'CANCELLED'

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
  specimenNo?: string
  patientName?: string
  patientIdNo?: string
  status?: SpecimenStatus | ''
  specimenTypeId?: number
  deptId?: number
  collectTimeStart?: string
  collectTimeEnd?: string
}

export interface SpecimenRegisterForm {
  patientId?: number
  patientName: string
  patientGender: string
  patientAge: string
  patientPhone: string
  patientIdNo: string
  deptId: number
  deptName?: string
  wardId?: number
  wardName?: string
  bedNo: string
  doctorId?: number
  doctorName?: string
  specimenTypeId: number
  specimenTypeName?: string
  collectTime?: string
  collectUserId?: number
  collectUserName?: string
  isStat?: number
  clinicalDiagnosis: string
  testItemIds: number[]
  remark: string
}

export interface SpecimenTraceVO {
  id: string
  specimenId: string
  specimenNo: string
  action: string
  actionName: string
  operationDesc: string
  operatorId: string
  operatorName: string
  operateTime: string
  fromStatus: string
  fromStatusName: string
  toStatus: string
  toStatusName: string
  remark: string
}

export interface SpecimenTraceQueryParams {
  specimenNo?: string
  patientName?: string
  action?: string
  operateTimeStart?: string
  operateTimeEnd?: string
}

export interface TestItem {
  id: number
  itemCode: string
  itemName: string
  itemNameEn: string
  itemShort: string
  categoryId: number
  specimenTypeId: number
  method: string
  unit: string
  referenceLow: number
  referenceHigh: number
  referenceText: string
  price: number
  tat: number
  isStat: number
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
  id: number
  typeCode: string
  typeName: string
  typeDesc: string
  colorCode: string
  containerType: string
  storageCondition: string
  validityPeriod: number
  status: number
}

export interface SpecimenDetail extends Specimen {
  traces: SpecimenTraceVO[]
}
