<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>诊断记录列表</span>
        </div>
      </template>

      <el-form :model="queryParams" inline class="search-form">
        <el-form-item label="患者姓名">
          <el-input
            v-model="queryParams.patientName"
            placeholder="患者姓名"
            clearable
            @keyup.enter="handleQuery"
          />
        </el-form-item>
        <el-form-item label="审核状态">
          <el-select v-model="queryParams.reviewStatus" placeholder="请选择状态" clearable>
            <el-option label="待审核" :value="0" />
            <el-option label="已确认" :value="1" />
            <el-option label="已拒绝" :value="2" />
          </el-select>
        </el-form-item>
        <el-form-item label="风险等级">
          <el-select v-model="queryParams.riskLevel" placeholder="请选择等级" clearable>
            <el-option label="低风险" value="low" />
            <el-option label="中风险" value="medium" />
            <el-option label="高风险" value="high" />
            <el-option label="危急" value="critical" />
          </el-select>
        </el-form-item>
        <el-form-item label="日期范围">
          <el-date-picker
            v-model="dateRange"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            value-format="YYYY-MM-DD"
            @change="handleDateChange"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleQuery">
            <el-icon><Search /></el-icon>
            查询
          </el-button>
          <el-button @click="handleReset">
            <el-icon><Refresh /></el-icon>
            重置
          </el-button>
        </el-form-item>
      </el-form>

      <el-table v-loading="loading" :data="recordList" border stripe>
        <el-table-column prop="reportNo" label="报告编号" width="140" />
        <el-table-column prop="patientName" label="患者姓名" width="100" />
        <el-table-column prop="reportId" label="报告ID" width="120" />
        <el-table-column label="审核状态" width="100">
          <template #default="{ row }">
            <el-tag :type="getReviewStatusType(row.reviewStatus)">
              {{ getReviewStatusLabel(row.reviewStatus) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="风险等级" width="100">
          <template #default="{ row }">
            <el-tag v-if="row.riskLevel" :type="getRiskLevelType(row.riskLevel)">
              {{ getRiskLevelLabel(row.riskLevel) }}
            </el-tag>
            <span v-else>-</span>
          </template>
        </el-table-column>
        <el-table-column label="置信度" width="100">
          <template #default>
            <span>-</span>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="160" />
        <el-table-column prop="reviewTime" label="完成时间" width="160" />
        <el-table-column label="操作" width="150" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" link @click="handleView(row)">查看</el-button>
            <el-button v-if="row.reviewStatus === 0" type="warning" link @click="handleCancel(row)">取消</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        v-model:current-page="queryParams.pageNum"
        v-model:page-size="queryParams.pageSize"
        :total="total"
        :page-sizes="[10, 20, 50, 100]"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleQuery"
        @current-change="handleQuery"
      />
    </el-card>

    <el-dialog v-model="detailDialogVisible" title="诊断详情" width="800px" destroy-on-close>
      <template v-if="currentRecord">
        <el-descriptions :column="2" border>
          <el-descriptions-item label="报告编号">{{ currentRecord.reportNo }}</el-descriptions-item>
          <el-descriptions-item label="患者姓名">{{ currentRecord.patientName }}</el-descriptions-item>
          <el-descriptions-item label="报告ID">{{ currentRecord.reportId }}</el-descriptions-item>
          <el-descriptions-item label="审核状态">
            <el-tag :type="getReviewStatusType(currentRecord.reviewStatus)">
              {{ getReviewStatusLabel(currentRecord.reviewStatus) }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="风险等级">
            <el-tag v-if="currentRecord.riskLevel" :type="getRiskLevelType(currentRecord.riskLevel)">
              {{ getRiskLevelLabel(currentRecord.riskLevel) }}
            </el-tag>
            <span v-else>-</span>
          </el-descriptions-item>
          <el-descriptions-item label="置信度">
            -
          </el-descriptions-item>
          <el-descriptions-item label="创建时间">{{ currentRecord.createTime }}</el-descriptions-item>
          <el-descriptions-item label="完成时间">{{ currentRecord.reviewTime || '-' }}</el-descriptions-item>
        </el-descriptions>

        <template v-if="currentRecord.result">
          <el-divider content-position="left">诊断摘要</el-divider>
          <div class="detail-section">{{ currentRecord.summary }}</div>

          <template v-if="(currentRecord.result as any).abnormalities">
            <el-divider content-position="left">异常指标</el-divider>
            <el-table :data="(currentRecord.result as any).abnormalities" border stripe size="small">
              <el-table-column prop="indicator" label="指标" width="120" />
              <el-table-column label="检测值" width="100">
                <template #default="{ row }">
                  {{ row.value }} {{ row.unit }}
                </template>
              </el-table-column>
              <el-table-column prop="referenceRange" label="参考范围" width="100" />
              <el-table-column label="严重程度" width="80">
                <template #default="{ row }">
                  <el-tag :type="getSeverityType(row.severity)" size="small">
                    {{ getSeverityLabel(row.severity) }}
                  </el-tag>
                </template>
              </el-table-column>
            </el-table>
          </template>

          <template v-if="(currentRecord.result as any).recommendations">
            <el-divider content-position="left">诊断建议</el-divider>
            <ul class="detail-list">
              <li v-for="(item, index) in (currentRecord.result as any).recommendations" :key="index">{{ item }}</li>
            </ul>
          </template>

          <template v-if="(currentRecord.result as any).riskFactors">
            <el-divider content-position="left">风险因素</el-divider>
            <div>
              <el-tag v-for="(factor, index) in (currentRecord.result as any).riskFactors" :key="index" type="warning" style="margin-right: 8px">
                {{ factor }}
              </el-tag>
            </div>
          </template>
        </template>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Search, Refresh } from '@element-plus/icons-vue'
import type { AIAnalysis, AIAnalysisQuery } from '@/api/ai'
import { getAIAnalysisList, getAIAnalysisDetail, cancelAIAnalysis } from '@/api/ai'

const loading = ref(false)
const recordList = ref<AIAnalysis[]>([])
const total = ref(0)
const dateRange = ref<string[]>([])

const queryParams = reactive<AIAnalysisQuery>({
  pageNum: 1,
  pageSize: 10,
  patientName: '',
  reviewStatus: undefined,
  riskLevel: '',
  startTime: '',
  endTime: ''
})

const detailDialogVisible = ref(false)
const currentRecord = ref<AIAnalysis | null>(null)

const getReviewStatusType = (status: number): 'success' | 'info' | 'warning' | 'danger' | 'primary' => {
  const map: Record<number, 'success' | 'info' | 'warning' | 'danger' | 'primary'> = {
    0: 'info',
    1: 'success',
    2: 'danger'
  }
  return map[status] || 'info'
}

const getReviewStatusLabel = (status: number) => {
  const map: Record<number, string> = {
    0: '待审核',
    1: '已确认',
    2: '已拒绝'
  }
  return map[status] || '未知'
}

const getRiskLevelType = (level: string): 'success' | 'warning' | 'danger' | 'info' | 'primary' => {
  const map: Record<string, 'success' | 'warning' | 'danger' | 'info' | 'primary'> = {
    low: 'success',
    medium: 'warning',
    high: 'danger',
    critical: 'danger'
  }
  return map[level] || 'info'
}

const getRiskLevelLabel = (level: string) => {
  const map: Record<string, string> = {
    low: '低风险',
    medium: '中风险',
    high: '高风险',
    critical: '危急'
  }
  return map[level] || level
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

const handleDateChange = (val: string[] | null) => {
  if (val) {
    queryParams.startTime = val[0]
    queryParams.endTime = val[1]
  } else {
    queryParams.startTime = ''
    queryParams.endTime = ''
  }
}

const handleQuery = () => {
  queryParams.pageNum = 1
  fetchRecordList()
}

const handleReset = () => {
  queryParams.patientName = ''
  queryParams.reviewStatus = undefined
  queryParams.riskLevel = ''
  queryParams.startTime = ''
  queryParams.endTime = ''
  dateRange.value = []
  handleQuery()
}

const fetchRecordList = async () => {
  loading.value = true
  try {
    const res = await getAIAnalysisList(queryParams)
    recordList.value = res.records
    total.value = res.total
  } catch {
    ElMessage.error('获取诊断记录失败')
  } finally {
    loading.value = false
  }
}

const handleView = async (row: AIAnalysis) => {
  try {
    currentRecord.value = await getAIAnalysisDetail(row.id)
    detailDialogVisible.value = true
  } catch {
    ElMessage.error('获取详情失败')
  }
}

const handleCancel = async (row: AIAnalysis) => {
  try {
    await ElMessageBox.confirm('确定要取消该分析任务吗？', '提示', { type: 'warning' })
    await cancelAIAnalysis(row.id)
    ElMessage.success('已取消')
    fetchRecordList()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('取消失败')
    }
  }
}

onMounted(() => {
  fetchRecordList()
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

.search-form {
  margin-bottom: 16px;
}

.el-pagination {
  margin-top: 16px;
  justify-content: flex-end;
}

.detail-section {
  padding: 12px;
  background: #f5f7fa;
  border-radius: 4px;
  line-height: 1.8;
}

.detail-list {
  padding-left: 20px;
  line-height: 2;
}
</style>
