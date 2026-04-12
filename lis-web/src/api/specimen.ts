import { get, post } from '@/utils/request'
import type { PageResult } from '@/types/api'
import type {
  Specimen,
  SpecimenQueryParams,
  SpecimenRegisterForm,
  SpecimenTraceVO,
  SpecimenDetail,
  TestItem,
  SpecimenType,
} from '@/types/specimen'

const SPECIMEN_STATUS_TO_BACKEND: Record<string, string> = {
  REGISTERED: 'pending',
  RECEIVED: 'received',
  STORAGE: 'stored',
  TESTING: 'testing',
  COMPLETED: 'completed',
  REJECTED: 'rejected',
  CANCELLED: 'cancelled',
}

const SPECIMEN_STATUS_FROM_BACKEND: Record<string, string> = {
  pending: 'REGISTERED',
  received: 'RECEIVED',
  stored: 'STORAGE',
  testing: 'TESTING',
  completed: 'COMPLETED',
  rejected: 'REJECTED',
  cancelled: 'CANCELLED',
}

function mapSpecimenStatusToBackend(status: string | undefined): string | undefined {
  if (!status) return undefined
  return SPECIMEN_STATUS_TO_BACKEND[status] || status.toLowerCase()
}

export function mapSpecimenStatusFromBackend(status: string | undefined): string | undefined {
  if (!status) return undefined
  return SPECIMEN_STATUS_FROM_BACKEND[status] || status.toUpperCase()
}

function mapSpecimenRegisterToBackend(data: SpecimenRegisterForm): any {
  return {
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
  }
}

function mapSpecimenQueryToBackend(data: SpecimenQueryParams): any {
  return {
    pageNum: data.pageNum,
    pageSize: data.pageSize,
    specimenNo: data.specimenNo,
    patientName: data.patientName,
    patientIdNo: data.patientIdNo,
    status: mapSpecimenStatusToBackend(data.status || undefined),
    specimenTypeId: data.specimenTypeId,
    deptId: data.deptId,
    collectTimeStart: data.collectTimeStart,
    collectTimeEnd: data.collectTimeEnd,
  }
}

export function getSpecimenList(data: SpecimenQueryParams): Promise<PageResult<Specimen>> {
  return post('/specimen/list', mapSpecimenQueryToBackend(data))
}

export function getSpecimenPage(data: SpecimenQueryParams): Promise<PageResult<Specimen>> {
  return post('/specimen/list', mapSpecimenQueryToBackend(data))
}

export function getSpecimenDetail(id: string): Promise<SpecimenDetail> {
  return get(`/specimen/getById/${id}`)
}

export function registerSpecimen(data: SpecimenRegisterForm): Promise<SpecimenDetail> {
  return post('/specimen/register', mapSpecimenRegisterToBackend(data))
}

export function getSpecimenByBarcode(barcode: string): Promise<SpecimenDetail> {
  return get('/specimen/getByBarcode', { barcode })
}

export function getSpecimenByBarcodePath(barcode: string): Promise<SpecimenDetail> {
  // 后端实际路径是 GET /specimen/getByBarcode?barcode=xxx，与 getSpecimenByBarcode 相同
  return getSpecimenByBarcode(barcode)
}

export function getSpecimenBySpecimenNo(specimenNo: string): Promise<SpecimenDetail> {
  return get('/specimen/getBySpecimenNo', { specimenNo })
}

export function receiveSpecimen(barcode: string): Promise<void> {
  return post('/specimen/receive', { barcode })
}

export function rejectSpecimen(barcode: string, rejectReason: string): Promise<void> {
  return post('/specimen/reject', { barcode, isReject: 1, rejectReason })
}

export function collectSpecimen(id: number | string): Promise<void> {
  return updateSpecimenStatus({ specimenId: Number(id), status: 'RECEIVED' })
}

export function updateSpecimenStatus(data: { specimenId: number; status: string; remark?: string }): Promise<void> {
  return post('/specimen/updateStatus', data)
}

export function storageSpecimen(data: { specimenId: number; storageLocation: string; operatorId?: number }): Promise<void> {
  return post('/specimen/storage', data)
}

export function getSpecimenTraceBySpecimenId(specimenId: string): Promise<SpecimenTraceVO[]> {
  return get(`/specimen/trace/${specimenId}`)
}

export function getSpecimenTraceByNo(specimenNo: string): Promise<SpecimenTraceVO[]> {
  return get('/specimen/traceByNo', { specimenNo })
}

export function getSpecimenStatistics(data: { startDate?: string; endDate?: string; deptId?: number }): Promise<any> {
  return post('/specimen/statistics', data)
}

export function addSpecimenTestItems(data: { specimenId: number; testItemIds: number[] }): Promise<void> {
  return post('/specimen/addition', data)
}

export function printSpecimenBarcode(specimenId: string): Promise<void> {
  return post(`/specimen/printBarcode/${specimenId}`)
}

export function getSpecimenTypes(): Promise<SpecimenType[]> {
  return get('/specimen/types')
}

export function getTestItems(): Promise<TestItem[]> {
  return get('/specimen/testItems')
}

export function batchCollectSpecimen(ids: (number | string)[]): Promise<void> {
  return Promise.all(ids.map(id => collectSpecimen(id))).then(() => {})
}

export function batchReceiveSpecimen(barcodes: string[]): Promise<void> {
  return Promise.all(barcodes.map(barcode => receiveSpecimen(barcode))).then(() => {})
}

export function exportSpecimenList(params: any): Promise<Blob> {
  return get('/specimen/export', { ...params, responseType: 'blob' } as any)
}

export function getSpecimenTraceRecords(specimenNo: string): Promise<SpecimenTraceVO[]> {
  return getSpecimenTraceByNo(specimenNo)
}

export function getAllTestItems(): Promise<TestItem[]> {
  return getTestItems()
}

export function getTestItemCategories(): Promise<string[]> {
  return get('/specimen/testItemCategories')
}

export function getDepartments(): Promise<any[]> {
  return get('/user/dept/tree')
}
