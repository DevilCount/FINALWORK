import { get, post } from '@/utils/request'
import type { PageResult } from '@/types/api'

export interface InterfaceLogQuery {
  pageNum?: number
  pageSize?: number
  interfaceCode?: string
  direction?: string
  processStatus?: string
  ackStatus?: string
  startTime?: string
  endTime?: string
}

export function getInterfaceLogList(params: InterfaceLogQuery): Promise<PageResult<any>> {
  return get('/hl7/hl7-message/page', params)
}

export function getInterfaceLogDetail(id: number): Promise<any> {
  return get(`/hl7/hl7-message/${id}`)
}

export function reprocessInterfaceLog(id: number): Promise<void> {
  return post(`/hl7/hl7-message/${id}/reprocess`)
}
