import { get, post, put, del } from '@/utils/request'

export interface AIAnalysis {
  id: string
  reportId: string
  reportNo: string
  patientName: string
  patientId: string
  analysisType: 'auto' | 'manual'
  status: 'pending' | 'analyzing' | 'completed' | 'failed'
  result: AIAnalysisResult | null
  suggestions: string[]
  riskLevel: 'low' | 'medium' | 'high' | 'critical'
  createdAt: string
  completedAt: string | null
}

export interface AIAnalysisResult {
  summary: string
  abnormalities: AbnormalityItem[]
  recommendations: string[]
  riskFactors: string[]
  followUpRequired: boolean
  confidence: number
}

export interface AbnormalityItem {
  indicator: string
  value: string
  unit: string
  referenceRange: string
  deviation: number
  severity: 'mild' | 'moderate' | 'severe'
  possibleCauses: string[]
}

export interface AIAnalysisQuery {
  page: number
  pageSize: number
  keyword?: string
  status?: string
  riskLevel?: string
  startDate?: string
  endDate?: string
}

export interface AIAnalysisListResult {
  list: AIAnalysis[]
  total: number
}

export interface ReportForAnalysis {
  id: string
  reportNo: string
  patientName: string
  patientId: string
  reportType: string
  status: string
  createdAt: string
}

export interface ReportQuery {
  page: number
  pageSize: number
  keyword?: string
  reportType?: string
  status?: string
}

export interface ReportListResult {
  list: ReportForAnalysis[]
  total: number
}

export interface AIRule {
  id: string
  name: string
  code: string
  category: string
  description: string
  conditions: RuleCondition[]
  actions: RuleAction[]
  priority: number
  isActive: boolean
  createdAt: string
  updatedAt: string
}

export interface RuleCondition {
  field: string
  operator: 'eq' | 'ne' | 'gt' | 'gte' | 'lt' | 'lte' | 'contains' | 'between'
  value: string | number | [number, number]
  logic?: 'and' | 'or'
}

export interface RuleAction {
  type: 'alert' | 'suggest' | 'flag' | 'notify'
  content: string
  severity: 'info' | 'warning' | 'error' | 'critical'
}

export interface AIRuleQuery {
  page: number
  pageSize: number
  keyword?: string
  category?: string
  isActive?: boolean
}

export interface AIRuleListResult {
  list: AIRule[]
  total: number
}

export function getAIAnalysisList(params: AIAnalysisQuery): Promise<AIAnalysisListResult> {
  return get('/ai/analysis', params)
}

export function getAIAnalysisDetail(id: string): Promise<AIAnalysis> {
  return get(`/ai/analysis/${id}`)
}

export function createAIAnalysis(reportId: string): Promise<AIAnalysis> {
  return post('/ai/analysis', { reportId })
}

export function cancelAIAnalysis(id: string): Promise<void> {
  return put(`/ai/analysis/${id}/cancel`)
}

export function getReportsForAnalysis(params: ReportQuery): Promise<ReportListResult> {
  return get('/ai/reports', params)
}

export function getAIRules(params: AIRuleQuery): Promise<AIRuleListResult> {
  return get('/ai/rules', params)
}

export function getAIRuleDetail(id: string): Promise<AIRule> {
  return get(`/ai/rules/${id}`)
}

export function createAIRule(data: Partial<AIRule>): Promise<AIRule> {
  return post('/ai/rules', data)
}

export function updateAIRule(id: string, data: Partial<AIRule>): Promise<AIRule> {
  return put(`/ai/rules/${id}`, data)
}

export function deleteAIRule(id: string): Promise<void> {
  return del(`/ai/rules/${id}`)
}

export function toggleAIRule(id: string, isActive: boolean): Promise<AIRule> {
  return put(`/ai/rules/${id}/toggle`, { isActive })
}

export function testAIRule(id: string, testData: Record<string, unknown>): Promise<{ matched: boolean; result: AIAnalysisResult }> {
  return post(`/ai/rules/${id}/test`, testData)
}

export function getAIRuleCategories(): Promise<{ code: string; name: string }[]> {
  return get('/ai/rules/categories')
}
