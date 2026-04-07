import { get, post, put, del } from '@/utils/request'
import type { PageParams, PageResult } from '@/types/api'

export interface DictType {
  id: number
  name: string
  code: string
  status: 'normal' | 'disable'
  remark: string
  createTime: string
  updateTime: string
}

export interface DictTypeForm {
  id?: number
  name: string
  code: string
  status: 'normal' | 'disable'
  remark: string
}

export interface DictTypeQuery extends PageParams {
  name?: string
  code?: string
  status?: string
}

export interface DictData {
  id: number
  typeId: number
  label: string
  value: string
  status: 'normal' | 'disable'
  sort: number
  remark: string
  cssClass: string
  listClass: string
  createTime: string
  updateTime: string
}

export interface DictDataForm {
  id?: number
  typeId: number
  label: string
  value: string
  status: 'normal' | 'disable'
  sort: number
  remark: string
  cssClass: string
  listClass: string
}

export interface DictDataQuery extends PageParams {
  typeId?: number
  label?: string
  value?: string
  status?: string
}

export function getDictTypeList(params: DictTypeQuery): Promise<PageResult<DictType>> {
  return get('/system/dict/type/list', params)
}

export function getDictTypeById(id: number): Promise<DictType> {
  return get(`/system/dict/type/${id}`)
}

export function getDictTypeByCode(code: string): Promise<DictData[]> {
  return get(`/system/dict/type/code/${code}`)
}

export function createDictType(data: DictTypeForm): Promise<void> {
  return post('/system/dict/type', data)
}

export function updateDictType(data: DictTypeForm): Promise<void> {
  return put('/system/dict/type', data)
}

export function deleteDictType(id: number): Promise<void> {
  return del(`/system/dict/type/${id}`)
}

export function batchDeleteDictTypes(ids: number[]): Promise<void> {
  return del('/system/dict/type/batch', { ids })
}

export function updateDictTypeStatus(id: number, status: 'normal' | 'disable'): Promise<void> {
  return put(`/system/dict/type/${id}/status`, { status })
}

export function getDictDataList(params: DictDataQuery): Promise<PageResult<DictData>> {
  return get('/system/dict/data/list', params)
}

export function getDictDataByType(typeId: number): Promise<DictData[]> {
  return get(`/system/dict/data/type/${typeId}`)
}

export function getDictDataById(id: number): Promise<DictData> {
  return get(`/system/dict/data/${id}`)
}

export function createDictData(data: DictDataForm): Promise<void> {
  return post('/system/dict/data', data)
}

export function updateDictData(data: DictDataForm): Promise<void> {
  return put('/system/dict/data', data)
}

export function deleteDictData(id: number): Promise<void> {
  return del(`/system/dict/data/${id}`)
}

export function batchDeleteDictData(ids: number[]): Promise<void> {
  return del('/system/dict/data/batch', { ids })
}

export function updateDictDataStatus(id: number, status: 'normal' | 'disable'): Promise<void> {
  return put(`/system/dict/data/${id}/status`, { status })
}
