import { get, post, put, del } from '@/utils/request'
import type { PageResult } from '@/types/api'

export interface AIAnalysisQuery {
  pageNum?: number
  pageSize?: number
  diagnosisNo?: string
  reportNo?: string
  specimenNo?: string
  patientName?: string
  diagnosisType?: string
  riskLevel?: string
  isAbnormal?: number
  reviewStatus?: number
  startTime?: string
  endTime?: string
}

export interface DiagnosisRecord {
  id: number
  diagnosisNo: string
  reportId: number
  reportNo: string
  patientName: string
  diagnosisType: string
  reviewStatus: number
  riskLevel: string
  diagnosisResult: string
  result: string
  summary: string
  createTime: string
  reviewTime: string
}

export type AIAnalysis = DiagnosisRecord

export interface DiagnosisResult {
  id: number
  reportId: number
  diagnosisType: string
  riskLevel: string
  summary: string
  details: any[]
  suggestions: string[]
  confidence: number
}

export interface DiagnosisRequest {
  reportId: number
  diagnosisType?: string
}

export interface DiagnosisReview {
  diagnosisId: number
  reviewStatus: number
  reviewRemark: string
}

export interface ReportForAnalysis {
  id: number
  reportNo: string
  patientName: string
  specimenType: string
  reportType: string
  status: string
  createTime: string
}

export type ReportQuery = AIAnalysisQuery

export interface AIRuleQuery {
  pageNum?: number
  pageSize?: number
  ruleName?: string
  category?: string
  isEnabled?: number
}

export interface AIRule {
  id: number
  ruleCode: string
  ruleName: string
  category: string
  description: string
  conditions: any
  actions: any
  priority: number
  isEnabled: number
  confidenceThreshold: number
  createTime: string
  updateTime: string
}

export type RuleCondition = any
export type RuleAction = any

export interface AIRuleForm {
  id?: number
  ruleCode: string
  ruleName: string
  category: string
  description: string
  conditions: any
  actions: any
  priority: number
  isEnabled: number
  confidenceThreshold?: number
}

export function getDiagnosisRecords(params: AIAnalysisQuery): Promise<PageResult<DiagnosisRecord>> {
  return get('/ai/diagnosis/records', params)
}

export const getAIAnalysisList = getDiagnosisRecords

export function getDiagnosisRecord(id: number): Promise<DiagnosisRecord> {
  return get(`/ai/diagnosis/record/${id}`)
}

export const getAIAnalysisDetail = getDiagnosisRecord

export function getDiagnosisRecordByNo(diagnosisNo: string): Promise<DiagnosisRecord> {
  return get(`/ai/diagnosis/record/no/${diagnosisNo}`)
}

export function createDiagnosis(data: DiagnosisRequest): Promise<DiagnosisResult> {
  return post('/ai/diagnosis/analyze', data)
}

export const createAIAnalysis = createDiagnosis

export function reviewDiagnosis(data: DiagnosisReview): Promise<void> {
  return post('/ai/diagnosis/review', data)
}

export function cancelAIAnalysis(id: number): Promise<void> {
  return reviewDiagnosis({ diagnosisId: id, reviewStatus: 2, reviewRemark: '取消分析' })
}

export function deleteDiagnosisRecord(id: number): Promise<void> {
  return del(`/ai/diagnosis/record/${id}`)
}

export function getReportsForAnalysis(params?: ReportQuery): Promise<PageResult<ReportForAnalysis>> {
  return post('/report/apply/query', params || {})
}

export function getAIRules(params: AIRuleQuery): Promise<PageResult<AIRule>> {
  return get('/ai/rule/page', params)
}

export function getAIRuleDetail(id: number): Promise<AIRule> {
  return get(`/ai/rule/${id}`)
}

export function getAIRuleByCode(ruleCode: string): Promise<AIRule> {
  return get(`/ai/rule/code/${ruleCode}`)
}

export function createAIRule(data: any): Promise<number> {
  return post('/ai/rule', data)
}

export function updateAIRule(id: number | any, data?: any): Promise<void> {
  const formData = typeof id === 'object' ? id : { ...data, id }
  return put('/ai/rule', formData)
}

export function deleteAIRule(id: number): Promise<void> {
  return del(`/ai/rule/${id}`)
}

export function toggleAIRule(id: number, isActive?: boolean): Promise<void> {
  if (isActive === false) {
    return disableAIRule(id)
  }
  return enableAIRule(id)
}

export function enableAIRule(id: number): Promise<void> {
  return put(`/ai/rule/${id}/enable`)
}

export function disableAIRule(id: number): Promise<void> {
  return put(`/ai/rule/${id}/disable`)
}

export function testAIRule(id: number, data?: any): Promise<any> {
  return post(`/ai/diagnosis/analyze`, { reportId: id, testItems: data })
}

export function getAIRulesByCategory(category: string): Promise<AIRule[]> {
  return get(`/ai/rule/category/${category}`)
}

export function getAIRuleCategories(): Promise<string[]> {
  return get('/ai/rule/enabled').then((rules: unknown) => {
    const typedRules = rules as AIRule[]
    const categories = [...new Set(typedRules.map(r => r.category).filter(Boolean))]
    return categories
  })
}

export function getEnabledAIRules(): Promise<AIRule[]> {
  return get('/ai/rule/enabled')
}
