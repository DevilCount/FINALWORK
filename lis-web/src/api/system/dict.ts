import { get, post, put, del } from '@/utils/request'
import type { PageParams, PageResult } from '@/types/api'

export interface DictType {
  id: number
  dictName: string
  dictType: string
  status: number
  remark: string
  createTime: string
  updateTime: string
}

export interface DictTypeForm {
  id?: number
  dictName: string
  dictType: string
  status: number
  remark: string
}

export interface DictTypeQuery extends PageParams {
  dictName?: string
  dictType?: string
  status?: number
}

export interface DictData {
  id: number
  dictTypeId: number
  dictType: string
  dataLabel: string
  dataValue: string
  status: number
  sort: number
  remark: string
  cssClass: string
  listClass: string
  createTime: string
  updateTime: string
}

export interface DictDataForm {
  id?: number
  dictTypeId: number
  dictType: string
  dataLabel: string
  dataValue: string
  status: number
  sort: number
  remark: string
  cssClass: string
  listClass: string
}

export interface DictDataQuery extends PageParams {
  dictTypeId?: number
  dictType?: string
  dataLabel?: string
  dataValue?: string
  status?: number
}

export function getDictTypeList(params: DictTypeQuery): Promise<PageResult<DictType>> {
  return get('/user/dict/type/list', params)
}

export function getDictTypeById(id: number): Promise<DictType> {
  return get(`/user/dict/type/${id}`)
}

export function getDictTypeByCode(dictType: string): Promise<DictType> {
  return get(`/user/dict/type/code/${dictType}`)
}

export function createDictType(data: DictTypeForm): Promise<number> {
  return post('/user/dict/type', data)
}

export function updateDictType(data: DictTypeForm): Promise<void> {
  return put('/user/dict/type', data)
}

export function deleteDictType(id: number): Promise<void> {
  return del(`/user/dict/type/${id}`)
}

export function batchDeleteDictTypes(ids: number[]): Promise<void> {
  return del('/user/dict/type/batch', { ids })
}

export function getDictDataList(params: DictDataQuery): Promise<PageResult<DictData>> {
  return get('/user/dict/data/list', params)
}

export function getDictDataByType(dictType: string): Promise<DictData[]> {
  return get(`/user/dict/data/type/${dictType}`)
}

export function getDictDataById(id: number): Promise<DictData> {
  return get(`/user/dict/data/${id}`)
}

export function createDictData(data: DictDataForm): Promise<number> {
  const mapped = { ...data, dictLabel: data.dataLabel, dictValue: data.dataValue, dictSort: data.sort }
  return post('/user/dict/data', mapped)
}

export function updateDictData(data: DictDataForm): Promise<void> {
  const mapped = { ...data, dictLabel: data.dataLabel, dictValue: data.dataValue, dictSort: data.sort }
  return put('/user/dict/data', mapped)
}

export function deleteDictData(id: number): Promise<void> {
  return del(`/user/dict/data/${id}`)
}

export function batchDeleteDictData(ids: number[]): Promise<void> {
  return del('/user/dict/data/batch', { ids })
}
