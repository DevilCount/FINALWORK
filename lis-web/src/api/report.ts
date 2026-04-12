import { get, post, put, del } from '@/utils/request'
import type { PageResult } from '@/types/api'
import type {
  Report,
  ReportDetail,
  ReportQueryParams,
  ResultEntryForm,
  CriticalValue,
  CriticalValueQueryParams,
  CriticalValueHandleForm,
} from '@/types/report'

const REPORT_STATUS_TO_BACKEND: Record<string, string> = {
  PENDING: 'draft',
  TESTING: 'testing',
  SUBMITTED: 'pending_review',
  FIRST_AUDITED: 'first_audited',
  FINAL_AUDITED: 'final_audited',
  APPROVED: 'audited',
  REJECTED: 'draft',
  PUBLISHED: 'reported',
  CANCELLED: 'cancelled',
}

const REPORT_STATUS_FROM_BACKEND: Record<string, string> = {
  draft: 'PENDING',
  testing: 'TESTING',
  pending_review: 'SUBMITTED',
  first_audited: 'FIRST_AUDITED',
  final_audited: 'FINAL_AUDITED',
  audited: 'APPROVED',
  reported: 'PUBLISHED',
  cancelled: 'CANCELLED',
}

function mapReportStatusToBackend(status: string | undefined): string | undefined {
  if (!status) return undefined
  return REPORT_STATUS_TO_BACKEND[status] || status.toLowerCase()
}

export function mapReportStatusFromBackend(status: string | undefined): string | undefined {
  if (!status) return undefined
  return REPORT_STATUS_FROM_BACKEND[status] || status.toUpperCase()
}

function mapReportQueryToBackend(data: ReportQueryParams): any {
  return {
    pageNum: data.pageNum,
    pageSize: data.pageSize,
    reportNo: data.reportNo,
    specimenNo: data.specimenNo,
    patientName: data.patientName,
    patientIdNo: data.patientIdNo,
    status: mapReportStatusToBackend(data.status || undefined),
    reportType: data.reportType,
    deptId: data.deptId,
    startTime: data.testTimeStart,
    endTime: data.testTimeEnd,
  }
}

function mapAuditToBackend(data: any): any {
  return {
    ...data,
    approved: data.auditResult === 'pass' || data.auditResult === 'approved',
    auditOpinion: data.auditRemark || data.auditOpinion,
  }
}

function mapCriticalValueQueryToBackend(data: CriticalValueQueryParams): any {
  return {
    pageNum: data.pageNum,
    pageSize: data.pageSize,
    panicNo: undefined,
    reportNo: undefined,
    specimenNo: data.specimenNo,
    patientName: data.patientName,
    handleStatus: data.handleStatus !== '' ? data.handleStatus : undefined,
    deptId: data.deptId,
    startTime: data.reportTimeStart,
    endTime: data.reportTimeEnd,
  }
}

function mapCriticalValueHandleToBackend(data: CriticalValueHandleForm): any {
  return {
    panicValueId: Number(data.id),
    handleResult: data.handleResult,
  }
}

export function getReportList(data: ReportQueryParams): Promise<PageResult<Report>> {
  return post('/report/apply/query', mapReportQueryToBackend(data))
}

export function getReportDetail(id: string): Promise<ReportDetail> {
  return get(`/report/apply/${id}/detail`)
}

export function getReportById(id: string): Promise<Report> {
  return get(`/report/apply/${id}`)
}

export function createReportApply(data: any): Promise<number> {
  return post('/report/apply', data)
}

export function updateReportApply(id: string, data: any): Promise<void> {
  return put(`/report/apply/${id}`, data)
}

export function deleteReportApply(id: string): Promise<void> {
  return del(`/report/apply/${id}`)
}

export function submitReportApply(id: string): Promise<void> {
  return post(`/report/audit/submit/${id}`)
}

export function getReportApplyItems(): Promise<any[]> {
  return get('/specimen/testItems')
}

export function cancelReport(reportId: string, reason?: string): Promise<void> {
  return post(`/report/apply/${reportId}/cancel`, {}, { params: { reason } })
}

export function getResultItem(reportItemId: string): Promise<any> {
  return get(`/report/result/${reportItemId}`)
}

export function saveResultEntry(data: ResultEntryForm): Promise<number> {
  return post('/report/result', data)
}

export function batchSaveResultEntry(data: { reportId: number; items: ResultEntryForm[] }): Promise<void> {
  return post('/report/result/batch', data)
}

export function deleteResultItem(reportItemId: string): Promise<void> {
  return del(`/report/result/${reportItemId}`)
}

export function submitReport(reportId: string): Promise<void> {
  return post(`/report/audit/submit/${reportId}`)
}

export function auditReport(data: any): Promise<void> {
  if (data.auditResult === 'pass' || data.auditResult === 'approved') {
    return approveReport(data)
  } else {
    return rejectReport(data)
  }
}

export function firstApproveReport(data: any): Promise<void> {
  return post('/report/audit/first-approve', mapAuditToBackend(data))
}

export function firstRejectReport(data: any): Promise<void> {
  return post('/report/audit/first-reject', mapAuditToBackend(data))
}

export function finalApproveReport(data: any): Promise<void> {
  return post('/report/audit/final-approve', mapAuditToBackend(data))
}

export function finalRejectReport(data: any): Promise<void> {
  return post('/report/audit/final-reject', mapAuditToBackend(data))
}

export function approveReport(data: any): Promise<void> {
  return post('/report/audit/approve', mapAuditToBackend(data))
}

export function rejectReport(data: any): Promise<void> {
  return post('/report/audit/reject', mapAuditToBackend(data))
}

export function batchAuditReports(data: { reportIds: string[]; auditResult: string; auditRemark: string; auditLevel?: 'first' | 'final' }): Promise<void> {
  return Promise.all(data.reportIds.map(id =>
    auditReport({ reportId: id, auditResult: data.auditResult, auditRemark: data.auditRemark, auditLevel: data.auditLevel })
  )).then(() => {})
}

export function publishReport(data: any): Promise<void> {
  return post('/report/publish', data)
}

export function printReport(data: any): Promise<Blob> {
  return post('/report/publish/print', typeof data === 'string' ? { reportId: data } : data, { responseType: 'blob' })
}

export function getPrintLogs(reportId: string): Promise<any[]> {
  return get(`/report/publish/printLogs/${reportId}`)
}

export function getCriticalValueList(data: CriticalValueQueryParams): Promise<PageResult<CriticalValue>> {
  return post('/report/panic/query', mapCriticalValueQueryToBackend(data))
}

export function getCriticalValueDetail(id: string): Promise<CriticalValue> {
  return get(`/report/panic/${id}`)
}

export function handleCriticalValue(data: CriticalValueHandleForm): Promise<void> {
  return post('/report/panic/handle', mapCriticalValueHandleToBackend(data))
}

export function notifyCriticalValue(id: string, doctorName: string, notifyMethod?: string): Promise<void> {
  return post(`/report/panic/notify/${id}`, {}, { params: { notifyMethod: notifyMethod || 'PHONE', receiveUserName: doctorName } })
}

export function confirmCriticalValue(id: string): Promise<void> {
  return post(`/report/panic/confirm/${id}`)
}

export function getCriticalValueRecords(criticalValueId: string): Promise<any[]> {
  return get(`/report/panic/${criticalValueId}/records`)
}

export function exportReportList(params: any): Promise<Blob> {
  return get('/report/export', { ...params, responseType: 'blob' } as any)
}

export function getReportBySpecimenId(specimenId: string): Promise<any> {
  return post('/report/apply/query', { specimenId } as any)
}
