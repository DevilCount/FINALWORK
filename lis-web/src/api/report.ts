import { get, post } from '@/utils/request'
import type { PageResult } from '@/types/api'
import type {
  Report,
  ReportQueryParams,
  ReportResult,
  ResultEntryForm,
  AuditForm,
  CriticalValue,
  CriticalValueQueryParams,
  CriticalValueHandleForm,
  CriticalValueRecord,
} from '@/types/report'

export function getReportList(params: ReportQueryParams): Promise<PageResult<Report>> {
  return get('/api/report/list', params)
}

export function getReportDetail(id: string): Promise<Report> {
  return get(`/api/report/detail/${id}`)
}

export function getReportBySpecimenId(specimenId: string): Promise<Report> {
  return get(`/api/report/specimen/${specimenId}`)
}

export function getReportResults(reportId: string): Promise<ReportResult[]> {
  return get(`/api/report/results/${reportId}`)
}

export function saveResultEntry(data: ResultEntryForm): Promise<Report> {
  return post('/api/report/save-result', data)
}

export function submitReport(reportId: string): Promise<Report> {
  return post(`/api/report/submit/${reportId}`)
}

export function auditReport(data: AuditForm): Promise<Report> {
  return post('/api/report/audit', data)
}

export function batchAuditReports(
  reportIds: string[],
  auditResult: 'pass' | 'reject',
  auditRemark: string
): Promise<{ success: number; failed: number }> {
  return post('/api/report/batch-audit', { reportIds, auditResult, auditRemark })
}

export function printReport(reportId: string): Promise<Blob> {
  return get(`/api/report/print/${reportId}`, {}, { responseType: 'blob' })
}

export function getReportPreview(reportId: string): Promise<string> {
  return get(`/api/report/preview/${reportId}`)
}

export function retractReport(reportId: string, reason: string): Promise<Report> {
  return post(`/api/report/retract/${reportId}`, { reason })
}

export function getCriticalValueList(params: CriticalValueQueryParams): Promise<PageResult<CriticalValue>> {
  return get('/api/critical-value/list', params)
}

export function getCriticalValueDetail(id: string): Promise<CriticalValue> {
  return get(`/api/critical-value/detail/${id}`)
}

export function handleCriticalValue(data: CriticalValueHandleForm): Promise<CriticalValue> {
  return post('/api/critical-value/handle', data)
}

export function notifyCriticalValue(id: string, doctorName: string): Promise<CriticalValue> {
  return post(`/api/critical-value/notify/${id}`, { doctorName })
}

export function getCriticalValueRecords(criticalValueId: string): Promise<CriticalValueRecord[]> {
  return get(`/api/critical-value/records/${criticalValueId}`)
}

export function exportReportList(params: ReportQueryParams): Promise<Blob> {
  return get('/api/report/export', params, { responseType: 'blob' })
}

export function getReportStatistics(params: {
  startTime: string
  endTime: string
  departmentId?: string
}): Promise<{
  totalCount: number
  auditedCount: number
  pendingCount: number
  abnormalCount: number
}> {
  return get('/api/report/statistics', params)
}
