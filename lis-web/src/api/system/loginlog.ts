import { post, del } from '@/utils/request'
import type { PageResult } from '@/types/api'

export interface LoginLogQuery {
  pageNum?: number
  pageSize?: number
  username?: string
  ipaddr?: string
  status?: number
  startTime?: string
  endTime?: string
}

export function getLoginLogList(params: LoginLogQuery): Promise<PageResult<any>> {
  return post('/user/login-log/list', params)
}

export function cleanLoginLog(): Promise<void> {
  return del('/user/login-log/clean')
}
