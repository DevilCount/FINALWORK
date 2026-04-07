import { get } from '@/utils/request'

export interface WorkloadStatistics {
  date: string
  totalSamples: number
  completedSamples: number
  pendingSamples: number
  totalReports: number
  avgTurnaroundTime: number
}

export interface WorkloadQuery {
  startDate: string
  endDate: string
  department?: string
  groupBy: 'day' | 'week' | 'month'
}

export interface WorkloadChart {
  categories: string[]
  series: {
    name: string
    data: number[]
  }[]
}

export interface SpecimenStatistics {
  specimenType: string
  totalCount: number
  qualifiedCount: number
  unqualifiedCount: number
  qualificationRate: number
}

export interface SpecimenQuery {
  startDate: string
  endDate: string
  department?: string
  specimenType?: string
}

export interface SpecimenTrend {
  date: string
  count: number
  type: string
}

export interface SpecimenChart {
  pieData: { name: string; value: number }[]
  trendData: {
    categories: string[]
    series: { name: string; data: number[] }[]
  }
}

export interface ReportStatistics {
  reportType: string
  totalCount: number
  normalCount: number
  abnormalCount: number
  criticalCount: number
  avgReviewTime: number
}

export interface StatisticsReportQuery {
  startDate: string
  endDate: string
  department?: string
  reportType?: string
}

export interface ReportChart {
  pieData: { name: string; value: number }[]
  barData: {
    categories: string[]
    series: { name: string; data: number[] }[]
  }
  trendData: {
    categories: string[]
    series: { name: string; data: number[] }[]
  }
}

export interface DashboardSummary {
  totalEquipment: number
  onlineEquipment: number
  offlineEquipment: number
  maintenanceEquipment: number
  todaySamples: number
  todayReports: number
  pendingReports: number
  criticalReports: number
  avgTurnaroundTime: number
}

export function getWorkloadStatistics(params: WorkloadQuery): Promise<WorkloadStatistics[]> {
  return get('/statistics/workload', params)
}

export function getWorkloadChart(params: WorkloadQuery): Promise<WorkloadChart> {
  return get('/statistics/workload/chart', params)
}

export function getSpecimenStatistics(params: SpecimenQuery): Promise<SpecimenStatistics[]> {
  return get('/statistics/specimen', params)
}

export function getSpecimenChart(params: SpecimenQuery): Promise<SpecimenChart> {
  return get('/statistics/specimen/chart', params)
}

export function getReportStatistics(params: StatisticsReportQuery): Promise<ReportStatistics[]> {
  return get('/statistics/report', params)
}

export function getReportChart(params: StatisticsReportQuery): Promise<ReportChart> {
  return get('/statistics/report/chart', params)
}

export function getDashboardSummary(): Promise<DashboardSummary> {
  return get('/statistics/dashboard/summary')
}

export function exportWorkloadReport(params: WorkloadQuery): Promise<Blob> {
  return get('/statistics/workload/export', params)
}

export function exportSpecimenReport(params: SpecimenQuery): Promise<Blob> {
  return get('/statistics/specimen/export', params)
}

export function exportReportReport(params: StatisticsReportQuery): Promise<Blob> {
  return get('/statistics/report/export', params)
}
