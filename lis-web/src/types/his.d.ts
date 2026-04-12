export interface HisLabOrderForm {
  patientName: string
  patientGender: string
  patientAge: string
  patientPhone: string
  patientIdNo: string
  deptId: number
  deptName: string
  bedNo: string
  doctorName: string
  visitNo: string
  visitType: string
  specimenTypeId: number
  specimenTypeName: string
  clinicalDiagnosis: string
  testItemIds: number[]
  isStat: number
  remark: string
  interfaceCode: string
}

export interface HisLabOrderItem {
  id: number
  itemCode: string
  itemName: string
  itemNameEn: string
  categoryId: number
  specimenTypeId: number
  method: string
  unit: string
  referenceLow: number
  referenceHigh: number
  price: number
}
