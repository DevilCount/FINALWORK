import { post, get, del } from '@/utils/request'
import type { PageResult } from '@/types/api'

export interface ErrorLogQuery {
  pageNum?: number
  pageSize?: number
  className?: string
  methodName?: string
  exceptionType?: string
  startTime?: string
  endTime?: string
}

export function getErrorLogList(params: ErrorLogQuery): Promise<PageResult<any>> {
  return post('/user/error-log/list', params)
}

export function getErrorLogDetail(id: number): Promise<any> {
  return get(`/user/error-log/${id}`)
}

export function cleanErrorLog(): Promise<void> {
  return del('/user/error-log/clean')
}
