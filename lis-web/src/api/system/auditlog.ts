import { post, get, del } from '@/utils/request'
import type { PageResult } from '@/types/api'

export interface AuditLogQuery {
  pageNum?: number
  pageSize?: number
  operName?: string
  businessType?: string
  operResult?: number
  startTime?: string
  endTime?: string
}

export function getAuditLogList(params: AuditLogQuery): Promise<PageResult<any>> {
  return post('/user/audit-log/list', params)
}

export function getAuditLogDetail(id: number): Promise<any> {
  return get(`/user/audit-log/${id}`)
}

export function cleanAuditLog(): Promise<void> {
  return del('/user/audit-log/clean')
}
