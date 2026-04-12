import { post, get, del } from '@/utils/request'
import type { PageResult } from '@/types/api'

export interface OperLogQuery {
  pageNum?: number
  pageSize?: number
  title?: string
  operName?: string
  businessType?: number
  status?: number
  startTime?: string
  endTime?: string
}

export function getOperLogList(params: OperLogQuery): Promise<PageResult<any>> {
  return post('/user/oper-log/list', params)
}

export function getOperLogDetail(id: number): Promise<any> {
  return get(`/user/oper-log/${id}`)
}

export function cleanOperLog(): Promise<void> {
  return del('/user/oper-log/clean')
}
