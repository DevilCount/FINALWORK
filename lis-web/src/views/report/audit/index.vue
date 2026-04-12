<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>报告审核</span>
        </div>
      </template>

      <el-tabs v-model="activeTab" @tab-change="handleTabChange">
        <el-tab-pane label="初审" name="first" />
        <el-tab-pane label="终审" name="final" />
      </el-tabs>

      <el-form :model="queryParams" inline class="search-form">
        <el-form-item label="报告编号">
          <el-input v-model="queryParams.reportNo" placeholder="请输入报告编号" clearable @keyup.enter="handleSearch" />
        </el-form-item>
        <el-form-item label="患者姓名">
          <el-input v-model="queryParams.patientName" placeholder="请输入患者姓名" clearable @keyup.enter="handleSearch" />
        </el-form-item>
        <el-form-item label="检验时间">
          <el-date-picker
            v-model="testTimeRange"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            value-format="YYYY-MM-DD"
            style="width: 240px"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">
            <el-icon><Search /></el-icon>
            搜索
          </el-button>
          <el-button @click="handleReset">
            <el-icon><Refresh /></el-icon>
            重置
          </el-button>
        </el-form-item>
      </el-form>

      <el-table v-loading="loading" :data="tableData" border stripe @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="50" />
        <el-table-column prop="reportNo" label="报告编号" width="150" />
        <el-table-column prop="specimenNo" label="标本编号" width="150" />
        <el-table-column prop="patientName" label="患者姓名" width="100" />
        <el-table-column prop="patientGender" label="性别" width="60">
          <template #default="{ row }">
            {{ row.patientGender === 'male' ? '男' : '女' }}
          </template>
        </el-table-column>
        <el-table-column prop="deptName" label="科室" width="120" />
        <el-table-column prop="testTime" label="检验时间" width="160" />
        <el-table-column prop="testUserName" label="检验人" width="100" />
        <el-table-column label="异常" width="80">
          <template #default="{ row }">
            <el-tag v-if="row.isAbnormal" type="danger">异常</el-tag>
            <span v-else>-</span>
          </template>
        </el-table-column>
        <el-table-column label="危急值" width="80">
          <template #default="{ row }">
            <el-tag v-if="row.isPanic" type="danger" effect="dark">危急</el-tag>
            <span v-else>-</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" link size="small" @click="handleView(row)">查看</el-button>
            <el-button v-if="activeTab === 'first'" type="success" link size="small" @click="handlePass(row)">初审通过</el-button>
            <el-button v-if="activeTab === 'first'" type="danger" link size="small" @click="handleReject(row)">初审驳回</el-button>
            <el-button v-if="activeTab === 'final'" type="success" link size="small" @click="handlePass(row)">终审通过</el-button>
            <el-button v-if="activeTab === 'final'" type="danger" link size="small" @click="handleReject(row)">终审驳回</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination-container">
        <div class="batch-actions">
          <el-button v-if="activeTab === 'first'" type="success" :disabled="selectedRows.length === 0" @click="handleBatchPass">
            批量初审通过
          </el-button>
          <el-button v-if="activeTab === 'first'" type="danger" :disabled="selectedRows.length === 0" @click="handleBatchReject">
            批量初审驳回
          </el-button>
          <el-button v-if="activeTab === 'final'" type="success" :disabled="selectedRows.length === 0" @click="handleBatchPass">
            批量终审通过
          </el-button>
          <el-button v-if="activeTab === 'final'" type="danger" :disabled="selectedRows.length === 0" @click="handleBatchReject">
            批量终审驳回
          </el-button>
        </div>
        <el-pagination
          v-model:current-page="queryParams.pageNum"
          v-model:page-size="queryParams.pageSize"
          :page-sizes="[10, 20, 50, 100]"
          :total="total"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </el-card>

    <el-dialog v-model="viewDialogVisible" title="报告详情" width="900px">
      <template v-if="currentReport">
        <el-descriptions title="基本信息" :column="3" border>
          <el-descriptions-item label="报告编号">{{ currentReport.reportNo }}</el-descriptions-item>
          <el-descriptions-item label="标本编号">{{ currentReport.specimenNo }}</el-descriptions-item>
          <el-descriptions-item label="患者姓名">{{ currentReport.patientName }}</el-descriptions-item>
          <el-descriptions-item label="性别">{{ currentReport.patientGender === 'male' ? '男' : '女' }}</el-descriptions-item>
          <el-descriptions-item label="年龄">{{ currentReport.patientAge }}</el-descriptions-item>
          <el-descriptions-item label="科室">{{ currentReport.deptName }}</el-descriptions-item>
          <el-descriptions-item label="床号">{{ currentReport.bedNo || '-' }}</el-descriptions-item>
          <el-descriptions-item label="诊断" :span="2">{{ currentReport.clinicalDiagnosis || '-' }}</el-descriptions-item>
          <el-descriptions-item label="检验人">{{ currentReport.testUserName }}</el-descriptions-item>
          <el-descriptions-item label="检验时间">{{ currentReport.testTime }}</el-descriptions-item>
        </el-descriptions>

        <div class="section-title">检验结果</div>
        <el-table :data="currentReportDetail?.results || []" border stripe>
          <el-table-column prop="testItemCode" label="项目编码" width="100" />
          <el-table-column prop="testItemName" label="项目名称" min-width="120" />
          <el-table-column prop="resultValue" label="结果" width="100">
            <template #default="{ row }">
              <span :class="{ 'abnormal-result': row.isAbnormal, 'critical-result': row.isCritical }">
                {{ row.resultValue }}
              </span>
            </template>
          </el-table-column>
          <el-table-column prop="resultUnit" label="单位" width="80" />
          <el-table-column prop="referenceRange" label="参考范围" width="120" />
          <el-table-column prop="resultFlagName" label="标识" width="80">
            <template #default="{ row }">
              <el-tag v-if="row.isCritical" type="danger" size="small">危急</el-tag>
              <el-tag v-else-if="row.isAbnormal" type="warning" size="small">异常</el-tag>
              <el-tag v-else type="success" size="small">正常</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="testerName" label="检验人" width="80" />
          <el-table-column prop="testTime" label="检验时间" width="160" />
        </el-table>

        <el-descriptions v-if="currentReport.firstAuditTime" title="初审信息" :column="2" border class="mt-20">
          <el-descriptions-item label="初审人">{{ currentReport.firstAuditUserName }}</el-descriptions-item>
          <el-descriptions-item label="初审时间">{{ currentReport.firstAuditTime }}</el-descriptions-item>
          <el-descriptions-item label="初审备注" :span="2">{{ currentReport.firstAuditRemark || '-' }}</el-descriptions-item>
        </el-descriptions>

        <el-descriptions v-if="currentReport.finalAuditTime" title="终审信息" :column="2" border class="mt-20">
          <el-descriptions-item label="终审人">{{ currentReport.finalAuditUserName }}</el-descriptions-item>
          <el-descriptions-item label="终审时间">{{ currentReport.finalAuditTime }}</el-descriptions-item>
          <el-descriptions-item label="终审备注" :span="2">{{ currentReport.finalAuditRemark || '-' }}</el-descriptions-item>
        </el-descriptions>

        <el-form :model="auditForm" label-width="80px" class="mt-20">
          <el-form-item label="审核备注">
            <el-input v-model="auditForm.auditRemark" type="textarea" :rows="3" placeholder="请输入审核备注" />
          </el-form-item>
        </el-form>
      </template>
      <template #footer>
        <el-button @click="viewDialogVisible = false">取消</el-button>
        <el-button type="danger" @click="confirmRejectFromView">{{ activeTab === 'first' ? '初审驳回' : '终审驳回' }}</el-button>
        <el-button type="success" @click="confirmPassFromView">{{ activeTab === 'first' ? '初审通过' : '终审通过' }}</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="rejectDialogVisible" :title="activeTab === 'first' ? '初审驳回原因' : '终审驳回原因'" width="500px">
      <el-form :model="rejectForm" label-width="80px">
        <el-form-item label="驳回原因" required>
          <el-input v-model="rejectForm.auditRemark" type="textarea" :rows="3" placeholder="请输入驳回原因" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="rejectDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="confirmReject">确定驳回</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Search, Refresh } from '@element-plus/icons-vue'
import type { Report, ReportDetail, ReportQueryParams, AuditForm } from '@/types/report'
import { getReportList, getReportDetail, firstApproveReport, firstRejectReport, finalApproveReport, finalRejectReport, batchAuditReports } from '@/api/report'

const route = useRoute()

const activeTab = ref<'first' | 'final'>('first')
const loading = ref(false)
const tableData = ref<Report[]>([])
const total = ref(0)
const selectedRows = ref<Report[]>([])

const testTimeRange = ref<[string, string] | null>(null)

const queryParams = reactive<ReportQueryParams>({
  pageNum: 1,
  pageSize: 10,
  reportNo: '',
  patientName: '',
  status: 'SUBMITTED',
  testTimeStart: '',
  testTimeEnd: '',
})

const viewDialogVisible = ref(false)
const rejectDialogVisible = ref(false)
const currentReport = ref<Report | null>(null)
const currentReportDetail = ref<ReportDetail | null>(null)
const auditForm = reactive<AuditForm>({
  reportId: '',
  auditResult: 'pass',
  auditRemark: '',
  auditLevel: 'first',
})

const rejectForm = reactive({
  reportId: '',
  reportIds: [] as string[],
  auditRemark: '',
})

const handleTabChange = (tab: string | number) => {
  if (tab === 'first') {
    queryParams.status = 'SUBMITTED'
  } else {
    queryParams.status = 'FIRST_AUDITED'
  }
  queryParams.pageNum = 1
  fetchData()
}

const fetchData = async () => {
  loading.value = true
  try {
    if (testTimeRange.value) {
      queryParams.testTimeStart = testTimeRange.value[0]
      queryParams.testTimeEnd = testTimeRange.value[1]
    } else {
      queryParams.testTimeStart = ''
      queryParams.testTimeEnd = ''
    }
    const res = await getReportList(queryParams)
    tableData.value = res.records
    total.value = res.total
  } catch (error) {
    console.error('获取报告列表失败:', error)
  } finally {
    loading.value = false
  }
}

const handleSearch = () => {
  queryParams.pageNum = 1
  fetchData()
}

const handleReset = () => {
  queryParams.reportNo = ''
  queryParams.patientName = ''
  testTimeRange.value = null
  queryParams.testTimeStart = ''
  queryParams.testTimeEnd = ''
  handleSearch()
}

const handleSizeChange = (size: number) => {
  queryParams.pageSize = size
  fetchData()
}

const handleCurrentChange = (page: number) => {
  queryParams.pageNum = page
  fetchData()
}

const handleSelectionChange = (rows: Report[]) => {
  selectedRows.value = rows
}

const handleView = async (row: Report) => {
  try {
    currentReport.value = row
    currentReportDetail.value = await getReportDetail(row.id)
    auditForm.reportId = row.id
    auditForm.auditResult = 'pass'
    auditForm.auditRemark = ''
    auditForm.auditLevel = activeTab.value
    viewDialogVisible.value = true
  } catch (error) {
    console.error('获取报告详情失败:', error)
  }
}

const handlePass = async (row: Report) => {
  try {
    await ElMessageBox.confirm(
      activeTab.value === 'first' ? '确认初审通过该报告？' : '确认终审通过该报告？',
      '提示',
      { type: 'warning' }
    )
    if (activeTab.value === 'first') {
      await firstApproveReport({ reportId: row.id, auditResult: 'pass', auditRemark: '' })
    } else {
      await finalApproveReport({ reportId: row.id, auditResult: 'pass', auditRemark: '' })
    }
    ElMessage.success(activeTab.value === 'first' ? '初审通过' : '终审通过')
    fetchData()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('审核失败:', error)
    }
  }
}

const handleReject = (row: Report) => {
  rejectForm.reportId = row.id
  rejectForm.reportIds = []
  rejectForm.auditRemark = ''
  rejectDialogVisible.value = true
}

const confirmReject = async () => {
  if (!rejectForm.auditRemark.trim()) {
    ElMessage.warning('请输入驳回原因')
    return
  }

  try {
    if (rejectForm.reportIds.length > 0) {
      await batchAuditReports({
        reportIds: rejectForm.reportIds,
        auditResult: 'reject',
        auditRemark: rejectForm.auditRemark,
        auditLevel: activeTab.value,
      })
      ElMessage.success('批量驳回成功')
    } else {
      if (activeTab.value === 'first') {
        await firstRejectReport({ reportId: rejectForm.reportId, auditResult: 'reject', auditRemark: rejectForm.auditRemark })
      } else {
        await finalRejectReport({ reportId: rejectForm.reportId, auditResult: 'reject', auditRemark: rejectForm.auditRemark })
      }
      ElMessage.success('驳回成功')
    }
    rejectDialogVisible.value = false
    fetchData()
  } catch (error) {
    console.error('驳回失败:', error)
  }
}

const confirmPassFromView = async () => {
  try {
    if (activeTab.value === 'first') {
      await firstApproveReport({ reportId: auditForm.reportId, auditResult: 'pass', auditRemark: auditForm.auditRemark })
    } else {
      await finalApproveReport({ reportId: auditForm.reportId, auditResult: 'pass', auditRemark: auditForm.auditRemark })
    }
    ElMessage.success(activeTab.value === 'first' ? '初审通过' : '终审通过')
    viewDialogVisible.value = false
    fetchData()
  } catch (error) {
    console.error('审核失败:', error)
  }
}

const confirmRejectFromView = async () => {
  if (!auditForm.auditRemark.trim()) {
    ElMessage.warning('请输入驳回原因')
    return
  }

  try {
    if (activeTab.value === 'first') {
      await firstRejectReport({ reportId: auditForm.reportId, auditResult: 'reject', auditRemark: auditForm.auditRemark })
    } else {
      await finalRejectReport({ reportId: auditForm.reportId, auditResult: 'reject', auditRemark: auditForm.auditRemark })
    }
    ElMessage.success('驳回成功')
    viewDialogVisible.value = false
    fetchData()
  } catch (error) {
    console.error('驳回失败:', error)
  }
}

const handleBatchPass = async () => {
  try {
    await ElMessageBox.confirm(
      activeTab.value === 'first'
        ? `确认批量初审通过 ${selectedRows.value.length} 份报告？`
        : `确认批量终审通过 ${selectedRows.value.length} 份报告？`,
      '提示',
      { type: 'warning' }
    )
    const ids = selectedRows.value.map(r => r.id)
    if (activeTab.value === 'first') {
      await Promise.all(ids.map(id => firstApproveReport({ reportId: id, auditResult: 'pass', auditRemark: '' })))
    } else {
      await Promise.all(ids.map(id => finalApproveReport({ reportId: id, auditResult: 'pass', auditRemark: '' })))
    }
    ElMessage.success(activeTab.value === 'first' ? '批量初审通过' : '批量终审通过')
    fetchData()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('批量审核失败:', error)
    }
  }
}

const handleBatchReject = () => {
  rejectForm.reportId = ''
  rejectForm.reportIds = selectedRows.value.map(r => r.id)
  rejectForm.auditRemark = ''
  rejectDialogVisible.value = true
}

onMounted(() => {
  const reportId = route.query.reportId as string
  if (reportId) {
    getReportDetail(reportId).then(report => {
      currentReport.value = report
      currentReportDetail.value = report
      auditForm.reportId = report.id
      viewDialogVisible.value = true
    })
  }
  fetchData()
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

.pagination-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 16px;
}

.batch-actions {
  display: flex;
  gap: 8px;
}

.section-title {
  font-size: 16px;
  font-weight: 600;
  margin: 20px 0 16px;
  padding-left: 10px;
  border-left: 3px solid var(--el-color-primary);
}

.mt-20 {
  margin-top: 20px;
}

.abnormal-result {
  color: var(--el-color-warning);
  font-weight: 600;
}

.critical-result {
  color: var(--el-color-danger);
  font-weight: 600;
}
</style>
