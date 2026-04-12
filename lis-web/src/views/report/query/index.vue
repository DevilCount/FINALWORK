<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>报告查询</span>
        </div>
      </template>

      <el-form :model="queryParams" inline class="search-form">
        <el-form-item label="报告编号">
          <el-input v-model="queryParams.reportNo" placeholder="请输入报告编号" clearable @keyup.enter="handleSearch" />
        </el-form-item>
        <el-form-item label="标本编号">
          <el-input v-model="queryParams.specimenNo" placeholder="请输入标本编号" clearable @keyup.enter="handleSearch" />
        </el-form-item>
        <el-form-item label="患者姓名">
          <el-input v-model="queryParams.patientName" placeholder="请输入患者姓名" clearable @keyup.enter="handleSearch" />
        </el-form-item>
        <el-form-item label="报告状态">
          <el-select v-model="queryParams.status" placeholder="请选择状态" clearable style="width: 150px">
            <el-option v-for="item in statusOptions" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
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
          <el-button type="success" @click="handleExport">
            <el-icon><Download /></el-icon>
            导出
          </el-button>
        </el-form-item>
      </el-form>

      <el-table v-loading="loading" :data="tableData" border stripe>
        <el-table-column prop="reportNo" label="报告编号" width="150" />
        <el-table-column prop="specimenNo" label="标本编号" width="150" />
        <el-table-column prop="patientName" label="患者姓名" width="100" />
        <el-table-column prop="patientGender" label="性别" width="60">
          <template #default="{ row }">
            {{ row.patientGender === '男' ? '男' : '女' }}
          </template>
        </el-table-column>
        <el-table-column prop="patientAge" label="年龄" width="70" />
        <el-table-column prop="deptName" label="科室" width="120" />
        <el-table-column label="异常" width="80">
          <template #default="{ row }">
            <el-tag v-if="row.isAbnormal" type="danger" size="small">异常</el-tag>
            <span v-else>-</span>
          </template>
        </el-table-column>
        <el-table-column label="危急" width="80">
          <template #default="{ row }">
            <el-tag v-if="row.isPanic" type="danger" effect="dark" size="small">危急</el-tag>
            <span v-else>-</span>
          </template>
        </el-table-column>
        <el-table-column prop="testTime" label="检验时间" width="160" />
        <el-table-column prop="testUserName" label="检验人" width="100" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="getStatusTagType(row.status)">{{ getStatusLabel(row.status) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" link size="small" @click="handleDetail(row)">详情</el-button>
            <el-button type="primary" link size="small" @click="handlePrint(row)">打印</el-button>
            <el-button v-if="row.status === 'FINAL_AUDITED'" type="success" link size="small" @click="handleAudit(row)">审核</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination-container">
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

    <el-dialog v-model="detailDialogVisible" title="报告详情" width="900px">
      <template v-if="currentReport">
        <el-descriptions title="基本信息" :column="3" border>
          <el-descriptions-item label="报告编号">{{ currentReport.reportNo }}</el-descriptions-item>
          <el-descriptions-item label="标本编号">{{ currentReport.specimenNo }}</el-descriptions-item>
          <el-descriptions-item label="报告状态">
            <el-tag :type="getStatusTagType(currentReport.status)">{{ getStatusLabel(currentReport.status) }}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="患者姓名">{{ currentReport.patientName }}</el-descriptions-item>
          <el-descriptions-item label="性别">{{ currentReport.patientGender === '男' ? '男' : '女' }}</el-descriptions-item>
          <el-descriptions-item label="年龄">{{ currentReport.patientAge }}</el-descriptions-item>
          <el-descriptions-item label="科室">{{ currentReport.deptName }}</el-descriptions-item>
          <el-descriptions-item label="床号">{{ currentReport.bedNo || '-' }}</el-descriptions-item>
          <el-descriptions-item label="诊断">{{ currentReport.clinicalDiagnosis || '-' }}</el-descriptions-item>
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
          <el-table-column prop="testMethod" label="检测方法" width="100" />
          <el-table-column prop="testerName" label="检验人" width="80" />
          <el-table-column prop="testTime" label="检验时间" width="160" />
        </el-table>

        <el-descriptions title="审核信息" :column="2" border class="mt-20" v-if="currentReport.auditTime">
          <el-descriptions-item label="审核人">{{ currentReport.auditUserName }}</el-descriptions-item>
          <el-descriptions-item label="审核时间">{{ currentReport.auditTime }}</el-descriptions-item>
          <el-descriptions-item label="审核备注" :span="2">{{ currentReport.auditRemark || '-' }}</el-descriptions-item>
        </el-descriptions>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { Search, Refresh, Download } from '@element-plus/icons-vue'
import type { Report, ReportDetail, ReportQueryParams, ReportStatus } from '@/types/report'
import { getReportList, getReportDetail, printReport, exportReportList } from '@/api/report'

const router = useRouter()

const loading = ref(false)
const tableData = ref<Report[]>([])
const total = ref(0)

const testTimeRange = ref<[string, string] | null>(null)

const queryParams = reactive<ReportQueryParams>({
  pageNum: 1,
  pageSize: 10,
  reportNo: '',
  specimenNo: '',
  patientName: '',
  status: '',
  reportType: '',
  testTimeStart: '',
  testTimeEnd: '',
})

const statusOptions = [
  { label: '待检验', value: 'PENDING' },
  { label: '检验中', value: 'TESTING' },
  { label: '已提交', value: 'SUBMITTED' },
  { label: '初审通过', value: 'FIRST_AUDITED' },
  { label: '终审通过', value: 'FINAL_AUDITED' },
  { label: '已审核', value: 'APPROVED' },
  { label: '已驳回', value: 'REJECTED' },
  { label: '已发布', value: 'PUBLISHED' },
  { label: '已作废', value: 'CANCELLED' },
]

const detailDialogVisible = ref(false)
const currentReport = ref<Report | null>(null)
const currentReportDetail = ref<ReportDetail | null>(null)

const getStatusTagType = (status: ReportStatus): 'success' | 'warning' | 'info' | 'danger' | undefined => {
  const typeMap: Record<ReportStatus, 'success' | 'warning' | 'info' | 'danger' | undefined> = {
    PENDING: 'info',
    TESTING: undefined,
    SUBMITTED: 'warning',
    FIRST_AUDITED: undefined,
    FINAL_AUDITED: 'success',
    APPROVED: 'success',
    REJECTED: 'danger',
    PUBLISHED: 'success',
    CANCELLED: 'info',
  }
  return typeMap[status]
}

const getStatusLabel = (status: ReportStatus): string => {
  const labelMap: Record<ReportStatus, string> = {
    PENDING: '待检验',
    TESTING: '检验中',
    SUBMITTED: '已提交',
    FIRST_AUDITED: '初审通过',
    FINAL_AUDITED: '终审通过',
    APPROVED: '已审核',
    REJECTED: '已驳回',
    PUBLISHED: '已发布',
    CANCELLED: '已作废',
  }
  return labelMap[status] || status
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
  queryParams.specimenNo = ''
  queryParams.patientName = ''
  queryParams.status = ''
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

const handleDetail = async (row: Report) => {
  try {
    currentReport.value = row
    currentReportDetail.value = await getReportDetail(row.id)
    detailDialogVisible.value = true
  } catch (error) {
    console.error('获取报告详情失败:', error)
  }
}

const handlePrint = async (row: Report) => {
  try {
    const blob = await printReport(row.id)
    const url = window.URL.createObjectURL(blob)
    const link = document.createElement('a')
    link.href = url
    link.download = `报告_${row.reportNo}.pdf`
    link.click()
    window.URL.revokeObjectURL(url)
    ElMessage.success('报告打印成功')
  } catch (error) {
    console.error('打印报告失败:', error)
  }
}

const handleAudit = (row: Report) => {
  router.push(`/report/audit?reportId=${row.id}`)
}

const handleExport = async () => {
  try {
    const blob = await exportReportList(queryParams)
    const url = window.URL.createObjectURL(blob)
    const link = document.createElement('a')
    link.href = url
    link.download = `报告列表_${new Date().toISOString().split('T')[0]}.xlsx`
    link.click()
    window.URL.revokeObjectURL(url)
    ElMessage.success('导出成功')
  } catch (error) {
    console.error('导出失败:', error)
  }
}

onMounted(() => {
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
  justify-content: flex-end;
  margin-top: 16px;
}

.mr-1 {
  margin-right: 4px;
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
