<template>
  <div class="page-container">
    <el-row :gutter="20">
      <el-col :span="10">
        <el-card class="report-list-card">
          <template #header>
            <div class="card-header">
              <span>选择报告</span>
            </div>
          </template>
          <el-form :model="reportQuery" inline class="search-form">
            <el-form-item>
              <el-input
                v-model="reportQuery.keyword"
                placeholder="报告编号/患者姓名"
                clearable
                @keyup.enter="handleSearchReports"
              />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleSearchReports">搜索</el-button>
            </el-form-item>
          </el-form>
          <el-table
            v-loading="reportLoading"
            :data="reportList"
            border
            stripe
            highlight-current-row
            @current-change="handleSelectReport"
          >
            <el-table-column prop="reportNo" label="报告编号" width="140" />
            <el-table-column prop="patientName" label="患者姓名" width="100" />
            <el-table-column prop="reportType" label="报告类型" width="100" />
            <el-table-column prop="createdAt" label="报告日期" width="160" />
          </el-table>
          <el-pagination
            v-model:current-page="reportQuery.page"
            v-model:page-size="reportQuery.pageSize"
            :total="reportTotal"
            :page-sizes="[10, 20, 50]"
            layout="total, prev, pager, next"
            small
            @size-change="fetchReports"
            @current-change="fetchReports"
          />
        </el-card>
      </el-col>

      <el-col :span="14">
        <el-card v-if="!selectedReport" class="empty-card">
          <el-empty description="请选择一份报告进行分析" />
        </el-card>

        <template v-else>
          <el-card class="report-info-card">
            <template #header>
              <div class="card-header">
                <span>报告信息</span>
                <el-button type="primary" :loading="analyzing" @click="handleStartAnalysis">
                  <el-icon><Cpu /></el-icon>
                  开始AI分析
                </el-button>
              </div>
            </template>
            <el-descriptions :column="2" border>
              <el-descriptions-item label="报告编号">{{ selectedReport.reportNo }}</el-descriptions-item>
              <el-descriptions-item label="患者姓名">{{ selectedReport.patientName }}</el-descriptions-item>
              <el-descriptions-item label="患者ID">{{ selectedReport.patientId }}</el-descriptions-item>
              <el-descriptions-item label="报告类型">{{ selectedReport.reportType }}</el-descriptions-item>
              <el-descriptions-item label="报告日期" :span="2">{{ selectedReport.createdAt }}</el-descriptions-item>
            </el-descriptions>
          </el-card>

          <el-card v-if="analysisResult" class="analysis-result-card">
            <template #header>
              <div class="card-header">
                <span>AI分析结果</span>
                <el-tag :type="getRiskLevelType(analysisResult.riskLevel)">
                  {{ getRiskLevelLabel(analysisResult.riskLevel) }}风险
                </el-tag>
              </div>
            </template>

            <el-alert
              v-if="analysisResult.riskLevel === 'critical' || analysisResult.riskLevel === 'high'"
              :title="analysisResult.riskLevel === 'critical' ? '危急值预警' : '高风险预警'"
              type="error"
              :closable="false"
              show-icon
              style="margin-bottom: 20px"
            />

            <el-collapse v-model="activeCollapse">
              <el-collapse-item title="诊断摘要" name="summary">
                <div class="summary-content">{{ analysisResult.result?.summary }}</div>
                <div v-if="analysisResult.result?.confidence" class="confidence">
                  置信度: {{ (analysisResult.result.confidence * 100).toFixed(1) }}%
                </div>
              </el-collapse-item>

              <el-collapse-item title="异常指标" name="abnormalities">
                <el-table :data="analysisResult.result?.abnormalities || []" border stripe>
                  <el-table-column prop="indicator" label="指标名称" width="150" />
                  <el-table-column label="检测值" width="120">
                    <template #default="{ row }">
                      <span :class="{ 'text-danger': row.severity !== 'mild' }">
                        {{ row.value }} {{ row.unit }}
                      </span>
                    </template>
                  </el-table-column>
                  <el-table-column prop="referenceRange" label="参考范围" width="120" />
                  <el-table-column label="偏离程度" width="100">
                    <template #default="{ row }">
                      <el-tag :type="getSeverityType(row.severity)" size="small">
                        {{ getSeverityLabel(row.severity) }}
                      </el-tag>
                    </template>
                  </el-table-column>
                  <el-table-column label="可能原因" min-width="200">
                    <template #default="{ row }">
                      <el-tag v-for="(cause, index) in row.possibleCauses" :key="index" size="small" style="margin-right: 4px">
                        {{ cause }}
                      </el-tag>
                    </template>
                  </el-table-column>
                </el-table>
              </el-collapse-item>

              <el-collapse-item title="诊断建议" name="recommendations">
                <el-timeline>
                  <el-timeline-item
                    v-for="(item, index) in analysisResult.result?.recommendations || []"
                    :key="index"
                    :type="getRecommendationType(index)"
                  >
                    {{ item }}
                  </el-timeline-item>
                </el-timeline>
              </el-collapse-item>

              <el-collapse-item title="风险因素" name="riskFactors">
                <el-tag
                  v-for="(factor, index) in analysisResult.result?.riskFactors || []"
                  :key="index"
                  type="warning"
                  style="margin-right: 8px; margin-bottom: 8px"
                >
                  {{ factor }}
                </el-tag>
              </el-collapse-item>

              <el-collapse-item title="建议随访" name="followUp">
                <el-tag :type="analysisResult.result?.followUpRequired ? 'warning' : 'success'">
                  {{ analysisResult.result?.followUpRequired ? '需要随访' : '无需随访' }}
                </el-tag>
              </el-collapse-item>
            </el-collapse>
          </el-card>
        </template>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { Cpu } from '@element-plus/icons-vue'
import type { ReportForAnalysis, ReportQuery, AIAnalysis } from '@/api/ai'
import { getReportsForAnalysis, createAIAnalysis, getAIAnalysisDetail } from '@/api/ai'

const reportLoading = ref(false)
const analyzing = ref(false)
const reportList = ref<ReportForAnalysis[]>([])
const reportTotal = ref(0)
const selectedReport = ref<ReportForAnalysis | null>(null)
const analysisResult = ref<AIAnalysis | null>(null)
const activeCollapse = ref(['summary', 'abnormalities', 'recommendations'])

const reportQuery = reactive<ReportQuery>({
  page: 1,
  pageSize: 10,
  keyword: '',
  reportType: '',
  status: ''
})

const fetchReports = async () => {
  reportLoading.value = true
  try {
    const res = await getReportsForAnalysis(reportQuery)
    reportList.value = res.list
    reportTotal.value = res.total
  } catch {
    ElMessage.error('获取报告列表失败')
  } finally {
    reportLoading.value = false
  }
}

const handleSearchReports = () => {
  reportQuery.page = 1
  fetchReports()
}

const handleSelectReport = async (row: ReportForAnalysis | null) => {
  selectedReport.value = row
  analysisResult.value = null
  if (row) {
    try {
      const existingAnalysis = await getAIAnalysisDetail(row.id)
      if (existingAnalysis && existingAnalysis.status === 'completed') {
        analysisResult.value = existingAnalysis
      }
    } catch {
      // No existing analysis
    }
  }
}

const handleStartAnalysis = async () => {
  if (!selectedReport.value) return

  analyzing.value = true
  try {
    const result = await createAIAnalysis(selectedReport.value.id)
    analysisResult.value = result
    ElMessage.success('AI分析完成')
  } catch {
    ElMessage.error('AI分析失败')
  } finally {
    analyzing.value = false
  }
}

const getRiskLevelType = (level?: string): 'success' | 'warning' | 'danger' | 'info' | 'primary' => {
  const map: Record<string, 'success' | 'warning' | 'danger' | 'info' | 'primary'> = {
    low: 'success',
    medium: 'warning',
    high: 'danger',
    critical: 'danger'
  }
  return map[level || ''] || 'info'
}

const getRiskLevelLabel = (level?: string) => {
  const map: Record<string, string> = {
    low: '低',
    medium: '中',
    high: '高',
    critical: '危急'
  }
  return map[level || ''] || level
}

const getSeverityType = (severity: string): 'success' | 'warning' | 'danger' | 'info' | 'primary' => {
  const map: Record<string, 'success' | 'warning' | 'danger' | 'info' | 'primary'> = {
    mild: 'info',
    moderate: 'warning',
    severe: 'danger'
  }
  return map[severity] || 'info'
}

const getSeverityLabel = (severity: string) => {
  const map: Record<string, string> = {
    mild: '轻度',
    moderate: '中度',
    severe: '重度'
  }
  return map[severity] || severity
}

const getRecommendationType = (index: number) => {
  const types: Array<'primary' | 'success' | 'warning' | 'danger' | 'info'> = ['primary', 'success', 'warning', 'danger', 'info']
  return types[index % types.length]
}

onMounted(() => {
  fetchReports()
})
</script>

<style scoped lang="scss">
.page-container {
  padding: 0;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.report-list-card {
  .search-form {
    margin-bottom: 12px;
  }

  .el-pagination {
    margin-top: 12px;
    justify-content: flex-end;
  }
}

.empty-card {
  min-height: 400px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.report-info-card {
  margin-bottom: 20px;
}

.analysis-result-card {
  .summary-content {
    font-size: 14px;
    line-height: 1.8;
    color: #303133;
  }

  .confidence {
    margin-top: 12px;
    font-size: 12px;
    color: #909399;
  }
}

.text-danger {
  color: #f56c6c;
  font-weight: bold;
}
</style>
