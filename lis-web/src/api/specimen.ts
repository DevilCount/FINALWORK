import { get, post, put, del } from '@/utils/request'
import type { PageResult } from '@/types/api'
import type {
  Specimen,
  SpecimenQueryParams,
  SpecimenRegisterForm,
  SpecimenTraceRecord,
  SpecimenTraceQueryParams,
  SpecimenDetail,
  TestItem,
  TestItemQueryParams,
  SpecimenType,
} from '@/types/specimen'

export function getSpecimenList(params: SpecimenQueryParams): Promise<PageResult<Specimen>> {
  return get('/api/specimen/list', params)
}

export function getSpecimenDetail(id: string): Promise<SpecimenDetail> {
  return get(`/api/specimen/detail/${id}`)
}

export function registerSpecimen(data: SpecimenRegisterForm): Promise<Specimen> {
  return post('/api/specimen/register', data)
}

export function updateSpecimen(id: string, data: Partial<SpecimenRegisterForm>): Promise<Specimen> {
  return put(`/api/specimen/update/${id}`, data)
}

export function deleteSpecimen(id: string): Promise<void> {
  return del(`/api/specimen/delete/${id}`)
}

export function collectSpecimen(id: string): Promise<Specimen> {
  return post(`/api/specimen/collect/${id}`)
}

export function receiveSpecimen(id: string): Promise<Specimen> {
  return post(`/api/specimen/receive/${id}`)
}

export function rejectSpecimen(id: string, reason: string): Promise<Specimen> {
  return post(`/api/specimen/reject/${id}`, { reason })
}

export function getSpecimenByCode(specimenCode: string): Promise<Specimen> {
  return get(`/api/specimen/code/${specimenCode}`)
}

export function getSpecimenTraceRecords(params: SpecimenTraceQueryParams): Promise<SpecimenTraceRecord[]> {
  return get('/api/specimen/trace', params)
}

export function getSpecimenTraceBySpecimenId(specimenId: string): Promise<SpecimenTraceRecord[]> {
  return get(`/api/specimen/trace/${specimenId}`)
}

export function getTestItemList(params: TestItemQueryParams): Promise<PageResult<TestItem>> {
  return get('/api/test-item/list', params)
}

export function getAllTestItems(): Promise<TestItem[]> {
  return get('/api/test-item/all')
}

export function getTestItemCategories(): Promise<{ code: string; name: string }[]> {
  return get('/api/test-item/categories')
}

export function getSpecimenTypes(): Promise<SpecimenType[]> {
  return get('/api/specimen/types')
}

export function getDepartments(): Promise<{ id: string; name: string }[]> {
  return get('/api/department/list')
}

export function printSpecimenLabel(id: string): Promise<Blob> {
  return get(`/api/specimen/print-label/${id}`, {}, { responseType: 'blob' })
}

export function batchCollectSpecimen(ids: string[]): Promise<{ success: number; failed: number }> {
  return post('/api/specimen/batch-collect', { ids })
}

export function batchReceiveSpecimen(ids: string[]): Promise<{ success: number; failed: number }> {
  return post('/api/specimen/batch-receive', { ids })
}

export function exportSpecimenList(params: SpecimenQueryParams): Promise<Blob> {
  return get('/api/specimen/export', params, { responseType: 'blob' })
}
