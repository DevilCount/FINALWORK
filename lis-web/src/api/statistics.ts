import { get } from '@/utils/request'

// 基础统计查询参数，与后端 DTO 对齐
export interface BaseStatQuery {
  startDate?: string
  endDate?: string
  deptId?: number
  pageNum?: number
  pageSize?: number
}

export interface WorkloadQuery extends BaseStatQuery {
  userId?: number
}

export interface SpecimenStatQuery extends BaseStatQuery {
  specimenTypeId?: number
}

export interface ReportStatQuery extends BaseStatQuery {
  testItemId?: number
  itemCode?: string
  reportStatus?: string
}

export interface EquipmentStatQuery extends BaseStatQuery {
  equipmentId?: number
}

export type SpecimenQuery = SpecimenStatQuery
export type StatisticsReportQuery = ReportStatQuery

export interface WorkloadStatistics {
  statDate: string
  userId: number
  userName: string
  deptId: number
  deptName: string
  specimenReceiveCount: number
  testCount: number
  auditCount: number
  reportCount: number
  workHours: number
  totalWorkload: number
}

export interface WorkloadChart {
  title: { text: string }
  xAxis: { type: string; data: string[] }
  yAxis: { type: string } | { type: string; name: string; position: string }[]
  series: { name: string; type: string; data: number[] }[]
}

export interface SpecimenStatistics {
  statDate: string
  deptId: number
  deptName: string
  specimenTypeId: number
  specimenTypeName: string
  totalCount: number
  receivedCount: number
  completedCount: number
  rejectedCount: number
  statCount: number
  tatAvg: number
  tatWithinRate: number
  completionRate: number
}

export interface SpecimenChart {
  title: { text: string }
  xAxis: { type: string; data: string[] }
  yAxis: { type: string; name: string }
  series: { name: string; type: string; data: number[] }[]
}

export interface ReportStatistics {
  statDate: string
  deptId: number
  deptName: string
  testItemId: number
  itemCode: string
  itemName: string
  totalCount: number
  normalCount: number
  abnormalCount: number
  panicCount: number
  positiveCount: number
  negativeCount: number
  abnormalRate: number
  positiveRate: number
}

export interface ReportChart {
  title: { text: string }
  xAxis: { type: string; data: string[] }
  yAxis: { type: string; name: string }
  series: { name: string; type: string; data: number[] }[]
}

// ==================== 仪表盘 ====================

export function getDashboardOverview(): Promise<any> {
  return get('/statistics/dashboard/overview')
}

export function getDashboardOverviewByRange(params: WorkloadQuery): Promise<any> {
  return get('/statistics/dashboard/overview/range', params)
}

// ==================== 工作量统计 ====================

export function getWorkloadStatistics(params: WorkloadQuery): Promise<WorkloadStatistics[]> {
  return getWorkloadDaily(params)
}

export function getWorkloadDaily(params: WorkloadQuery): Promise<any[]> {
  return get('/statistics/workload/daily', params)
}

export function getWorkloadUserPage(params: WorkloadQuery): Promise<any> {
  return get('/statistics/workload/user/page', params)
}

export function getWorkloadDept(params: WorkloadQuery): Promise<any[]> {
  return get('/statistics/workload/dept', params)
}

export function getWorkloadChart(params: WorkloadQuery): Promise<WorkloadChart> {
  return getWorkloadTrendChart(params)
}

export function getWorkloadTrendChart(params: WorkloadQuery): Promise<any> {
  return get('/statistics/workload/chart/trend', params)
}

export function getWorkloadDistributionChart(params: WorkloadQuery): Promise<any> {
  return get('/statistics/workload/chart/distribution', params)
}

export function exportWorkloadReport(params: WorkloadQuery): Promise<Blob> {
  return get('/statistics/workload/export', { ...params, responseType: 'blob' } as any)
}

// ==================== 标本统计 ====================

export function getSpecimenStatistics(params: SpecimenStatQuery): Promise<SpecimenStatistics[]> {
  return getSpecimenDaily(params)
}

export function getSpecimenDaily(params: SpecimenStatQuery): Promise<any[]> {
  return get('/statistics/specimen/daily', params)
}

export function getSpecimenDeptPage(params: SpecimenStatQuery): Promise<any> {
  return get('/statistics/specimen/dept/page', params)
}

export function getSpecimenTypeStat(params: SpecimenStatQuery): Promise<any[]> {
  return get('/statistics/specimen/type', params)
}

export function getSpecimenChart(params: SpecimenStatQuery): Promise<SpecimenChart> {
  return getSpecimenTrendChart(params)
}

export function getSpecimenTrendChart(params: SpecimenStatQuery): Promise<any> {
  return get('/statistics/specimen/chart/trend', params)
}

export function getSpecimenDistributionChart(params: SpecimenStatQuery): Promise<any> {
  return get('/statistics/specimen/chart/distribution', params)
}

export function getSpecimenTypePieChart(params: SpecimenStatQuery): Promise<any> {
  return get('/statistics/specimen/chart/type-pie', params)
}

export function exportSpecimenReport(params: SpecimenStatQuery): Promise<Blob> {
  return get('/statistics/specimen/export', { ...params, responseType: 'blob' } as any)
}

// ==================== 报告统计 ====================

export function getReportStatistics(params: ReportStatQuery): Promise<ReportStatistics[]> {
  return getReportDaily(params)
}

export function getReportDaily(params: ReportStatQuery): Promise<any[]> {
  return get('/statistics/report/daily', params)
}

export function getReportItemPage(params: ReportStatQuery): Promise<any> {
  return get('/statistics/report/item/page', params)
}

export function getReportDeptStat(params: ReportStatQuery): Promise<any[]> {
  return get('/statistics/report/dept', params)
}

export function getReportChart(params: ReportStatQuery): Promise<ReportChart> {
  return getReportTrendChart(params)
}

export function getReportTrendChart(params: ReportStatQuery): Promise<any> {
  return get('/statistics/report/chart/trend', params)
}

export function getReportAbnormalRateChart(params: ReportStatQuery): Promise<any> {
  return get('/statistics/report/chart/abnormal', params)
}

export function getReportPanicChart(params: ReportStatQuery): Promise<any> {
  return get('/statistics/report/chart/panic', params)
}

export function exportReportReport(params: ReportStatQuery): Promise<Blob> {
  return get('/statistics/report/export', { ...params, responseType: 'blob' } as any)
}

// ==================== 设备统计 ====================

export function getEquipmentDaily(params: EquipmentStatQuery): Promise<any[]> {
  return get('/statistics/equipment/daily', params)
}

export function getEquipmentStatPage(params: EquipmentStatQuery): Promise<any> {
  return get('/statistics/equipment/page', params)
}

export function getEquipmentTrend(params: EquipmentStatQuery): Promise<any[]> {
  return get('/statistics/equipment/trend', params)
}

export function getEquipmentTestChart(params: EquipmentStatQuery): Promise<any> {
  return get('/statistics/equipment/chart/test', params)
}

export function getEquipmentQcChart(params: EquipmentStatQuery): Promise<any> {
  return get('/statistics/equipment/chart/qc', params)
}

export function getEquipmentUptimeChart(params: EquipmentStatQuery): Promise<any> {
  return get('/statistics/equipment/chart/uptime', params)
}
