<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>标本签收</span>
        </div>
      </template>

      <!-- 条码扫描签收区域 -->
      <el-card shadow="never" class="scan-card">
        <template #header>
          <div class="scan-header">
            <el-icon size="20"><Camera /></el-icon>
            <span>扫描签收</span>
          </div>
        </template>
        <el-form :model="scanForm" inline class="scan-form" @submit.prevent="handleScanReceive">
          <el-form-item label="标本条码">
            <el-input
              ref="barcodeInputRef"
              v-model="scanForm.barcode"
              placeholder="请扫描或输入标本条码"
              clearable
              style="width: 300px"
              @keyup.enter="handleScanReceive"
            >
              <template #prefix>
                <el-icon><Barcode /></el-icon>
              </template>
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" :loading="scanLoading" @click="handleScanReceive">
              <el-icon><Check /></el-icon>
              签收
            </el-button>
            <el-button type="danger" @click="handleScanReject">
              <el-icon><Close /></el-icon>
              拒收
            </el-button>
          </el-form-item>
        </el-form>

        <!-- 扫描到的标本信息预览 -->
        <div v-if="scannedSpecimen" class="scanned-info">
          <el-descriptions :column="4" border size="small">
            <el-descriptions-item label="标本编号">{{ scannedSpecimen.specimenNo }}</el-descriptions-item>
            <el-descriptions-item label="患者姓名">{{ scannedSpecimen.patientName }}</el-descriptions-item>
            <el-descriptions-item label="科室">{{ scannedSpecimen.deptName }}</el-descriptions-item>
            <el-descriptions-item label="状态">
              <el-tag :type="getStatusTagType(scannedSpecimen.status)">{{ scannedSpecimen.statusName }}</el-tag>
            </el-descriptions-item>
            <el-descriptions-item label="标本类型">{{ scannedSpecimen.specimenTypeName }}</el-descriptions-item>
            <el-descriptions-item label="性别">{{ scannedSpecimen.patientGender === 'male' ? '男' : '女' }}</el-descriptions-item>
            <el-descriptions-item label="年龄">{{ scannedSpecimen.patientAge }}</el-descriptions-item>
            <el-descriptions-item label="诊断">{{ scannedSpecimen.clinicalDiagnosis || '-' }}</el-descriptions-item>
          </el-descriptions>
          <div class="test-items-preview">
            <span class="label">检验项目：</span>
            <el-tag v-for="item in scannedSpecimen.testItems" :key="item.id" size="small" class="mr-1">
              {{ item.testItemName }}
            </el-tag>
          </div>
        </div>
      </el-card>

      <!-- 待签收标本列表 -->
      <el-form :model="queryParams" inline class="search-form">
        <el-form-item label="标本编号">
          <el-input v-model="queryParams.specimenNo" placeholder="请输入标本编号" clearable @keyup.enter="handleSearch" />
        </el-form-item>
        <el-form-item label="标本状态">
          <el-select v-model="queryParams.status" placeholder="请选择状态" clearable style="width: 150px">
            <el-option v-for="item in statusOptions" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="科室">
          <el-select v-model="queryParams.deptId" placeholder="请选择科室" clearable style="width: 180px">
            <el-option v-for="item in departments" :key="item.id" :label="item.deptName" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="签收时间">
          <el-date-picker
            v-model="dateRange"
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
          <el-button @click="handleResetQuery">
            <el-icon><Refresh /></el-icon>
            重置
          </el-button>
        </el-form-item>
      </el-form>

      <div class="batch-actions-bar">
        <el-button type="success" :disabled="selectedRows.length === 0" @click="handleBatchReceive">
          <el-icon><Check /></el-icon>
          批量签收 ({{ selectedRows.length }})
        </el-button>
        <el-button type="danger" :disabled="selectedRows.length === 0" @click="handleBatchReject">
          <el-icon><Close /></el-icon>
          批量拒收 ({{ selectedRows.length }})
        </el-button>
      </div>

      <el-table v-loading="loading" :data="tableData" border stripe @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="50" />
        <el-table-column prop="specimenNo" label="标本编号" width="150" />
        <el-table-column prop="specimenTypeName" label="标本类型" width="100" />
        <el-table-column prop="patientName" label="患者姓名" width="100" />
        <el-table-column prop="patientGender" label="性别" width="60">
          <template #default="{ row }">
            {{ row.patientGender === 'male' ? '男' : '女' }}
          </template>
        </el-table-column>
        <el-table-column prop="patientAge" label="年龄" width="70" />
        <el-table-column prop="deptName" label="科室" width="120" />
        <el-table-column label="检验项目" min-width="200">
          <template #default="{ row }">
            <el-tag v-for="item in row.testItems?.slice(0, 3)" :key="item.id" size="small" class="mr-1">
              {{ item.testItemName }}
            </el-tag>
            <el-tag v-if="row.testItems?.length > 3" size="small" type="info">
              +{{ row.testItems.length - 3 }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="collectTime" label="采集时间" width="160" />
        <el-table-column prop="statusName" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="getStatusTagType(row.status)">{{ row.statusName }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button v-if="row.status === 'REGISTERED'" type="success" link size="small" @click="handleReceiveRow(row)">签收</el-button>
            <el-button v-if="['REGISTERED', 'RECEIVED'].includes(row.status)" type="danger" link size="small" @click="handleRejectRow(row)">拒收</el-button>
            <el-button type="primary" link size="small" @click="handleDetail(row)">详情</el-button>
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

    <!-- 拒收对话框 -->
    <el-dialog v-model="rejectDialogVisible" title="标本拒收" width="500px">
      <el-form :model="rejectForm" :rules="rejectRules" ref="rejectFormRef" label-width="80px">
        <el-form-item label="标本编号">
          <el-input :model-value="rejectForm.barcode || rejectForm.specimenNo" disabled />
        </el-form-item>
        <el-form-item label="拒收原因" prop="rejectReason">
          <el-input v-model="rejectForm.rejectReason" type="textarea" :rows="4" placeholder="请输入拒收原因" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="rejectDialogVisible = false">取消</el-button>
        <el-button type="danger" :loading="rejectLoading" @click="confirmReject">确认拒收</el-button>
      </template>
    </el-dialog>

    <!-- 标本详情对话框 -->
    <el-dialog v-model="detailDialogVisible" title="标本详情" width="800px">
      <template v-if="currentSpecimen">
        <el-descriptions title="基本信息" :column="3" border>
          <el-descriptions-item label="标本编号">{{ currentSpecimen.specimenNo }}</el-descriptions-item>
          <el-descriptions-item label="标本类型">{{ currentSpecimen.specimenTypeName }}</el-descriptions-item>
          <el-descriptions-item label="状态">
            <el-tag :type="getStatusTagType(currentSpecimen.status)">{{ currentSpecimen.statusName }}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="患者姓名">{{ currentSpecimen.patientName }}</el-descriptions-item>
          <el-descriptions-item label="性别">{{ currentSpecimen.patientGender === 'male' ? '男' : '女' }}</el-descriptions-item>
          <el-descriptions-item label="年龄">{{ currentSpecimen.patientAge }}</el-descriptions-item>
          <el-descriptions-item label="科室">{{ currentSpecimen.deptName }}</el-descriptions-item>
          <el-descriptions-item label="床号">{{ currentSpecimen.bedNo || '-' }}</el-descriptions-item>
          <el-descriptions-item label="诊断">{{ currentSpecimen.clinicalDiagnosis || '-' }}</el-descriptions-item>
          <el-descriptions-item label="采集时间">{{ currentSpecimen.collectTime || '-' }}</el-descriptions-item>
          <el-descriptions-item label="采集人">{{ currentSpecimen.collectUserName || '-' }}</el-descriptions-item>
          <el-descriptions-item label="接收时间">{{ currentSpecimen.receiveTime || '-' }}</el-descriptions-item>
        </el-descriptions>

        <div class="section-title">检验项目</div>
        <el-table :data="currentSpecimen.testItems || []" border stripe>
          <el-table-column prop="testItemCode" label="项目编码" width="120" />
          <el-table-column prop="testItemName" label="项目名称" min-width="200" />
          <el-table-column prop="statusName" label="状态" width="100">
            <template #default="{ row }">
              <el-tag size="small">{{ row.statusName }}</el-tag>
            </template>
          </el-table-column>
        </el-table>

        <el-descriptions v-if="currentSpecimen.remark" title="备注" :column="1" border class="mt-20">
          <el-descriptions-item>{{ currentSpecimen.remark }}</el-descriptions-item>
        </el-descriptions>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, nextTick } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Camera, Check, Close, Search, Refresh } from '@element-plus/icons-vue'
import type { FormInstance, FormRules } from 'element-plus'
import type { Specimen, SpecimenStatus, SpecimenDetail, SpecimenQueryParams } from '@/types/specimen'
import {
  getSpecimenList,
  getSpecimenDetail,
  receiveSpecimen,
  rejectSpecimen,
  getSpecimenByBarcode,
  batchReceiveSpecimen,
  getDepartments,
} from '@/api/specimen'

const Barcode = Camera // 复用 Camera 图标作为条码图标

const loading = ref(false)
const scanLoading = ref(false)
const rejectLoading = ref(false)
const tableData = ref<Specimen[]>([])
const total = ref(0)
const selectedRows = ref<Specimen[]>([])
const dateRange = ref<[string, string] | null>(null)

const departments = ref<{ id: string | number; deptName: string }[]>([])
const scannedSpecimen = ref<SpecimenDetail | null>(null)

const barcodeInputRef = ref<InstanceType<typeof import('element-plus')['ElInput']>>()

const scanForm = reactive({
  barcode: '',
})

const queryParams = reactive<SpecimenQueryParams>({
  pageNum: 1,
  pageSize: 10,
  specimenNo: '',
  status: 'REGISTERED',
  deptId: undefined,
  collectTimeStart: '',
  collectTimeEnd: '',
})

const statusOptions = [
  { label: '已登记', value: 'REGISTERED' },
  { label: '已接收', value: 'RECEIVED' },
  { label: '检验中', value: 'TESTING' },
  { label: '已完成', value: 'COMPLETED' },
  { label: '已拒收', value: 'REJECTED' },
  { label: '已入库', value: 'STORAGE' },
]

// 拒收对话框
const rejectDialogVisible = ref(false)
const rejectFormRef = ref<FormInstance>()
const isBatchReject = ref(false)
const batchRejectBarcodes = ref<string[]>([])
const rejectForm = reactive({
  barcode: '',
  specimenNo: '',
  specimenId: '',
  rejectReason: '',
})

const rejectRules: FormRules = {
  rejectReason: [{ required: true, message: '请输入拒收原因', trigger: 'blur' }],
}

// 详情对话框
const detailDialogVisible = ref(false)
const currentSpecimen = ref<SpecimenDetail | null>(null)

const getStatusTagType = (status: SpecimenStatus): 'success' | 'primary' | 'warning' | 'info' | 'danger' | undefined => {
  const typeMap: Record<SpecimenStatus, 'success' | 'primary' | 'warning' | 'info' | 'danger' | undefined> = {
    REGISTERED: 'info',
    RECEIVED: 'primary',
    REJECTED: 'danger',
    STORAGE: 'warning',
    TESTING: undefined,
    COMPLETED: 'success',
    CANCELLED: 'info',
  }
  return typeMap[status]
}

// 获取科室列表
const fetchDepartments = async () => {
  try {
    departments.value = await getDepartments()
  } catch (error) {
    console.error('获取科室列表失败:', error)
  }
}

// 获取标本列表
const fetchData = async () => {
  loading.value = true
  try {
    if (dateRange.value) {
      queryParams.collectTimeStart = dateRange.value[0]
      queryParams.collectTimeEnd = dateRange.value[1]
    } else {
      queryParams.collectTimeStart = ''
      queryParams.collectTimeEnd = ''
    }
    const res = await getSpecimenList(queryParams)
    tableData.value = res.records
    total.value = res.total
  } catch (error) {
    console.error('获取标本列表失败:', error)
  } finally {
    loading.value = false
  }
}

// 扫描条码查询标本
const handleScanReceive = async () => {
  const barcode = scanForm.barcode.trim()
  if (!barcode) {
    ElMessage.warning('请输入标本条码')
    return
  }

  scanLoading.value = true
  try {
    // 先查询标本信息
    const specimen = await getSpecimenByBarcode(barcode)
    scannedSpecimen.value = specimen

    if (specimen.status !== 'REGISTERED') {
      ElMessage.warning(`该标本当前状态为"${specimen.statusName}"，无法签收`)
      scanLoading.value = false
      return
    }

    // 执行签收
    await receiveSpecimen(barcode)
    ElMessage.success(`标本 ${barcode} 签收成功`)
    scanForm.barcode = ''
    scannedSpecimen.value = null
    fetchData()

    // 重新聚焦输入框
    await nextTick()
    barcodeInputRef.value?.focus()
  } catch (error) {
    console.error('签收失败:', error)
  } finally {
    scanLoading.value = false
  }
}

// 扫描条码拒收标本
const handleScanReject = () => {
  const barcode = scanForm.barcode.trim()
  if (!barcode) {
    ElMessage.warning('请输入标本条码')
    return
  }

  // 先查询标本信息显示预览
  getSpecimenByBarcode(barcode).then((specimen: SpecimenDetail) => {
    scannedSpecimen.value = specimen
    rejectForm.barcode = barcode
    rejectForm.specimenNo = specimen.specimenNo
    rejectForm.specimenId = specimen.id
    rejectForm.rejectReason = ''
    rejectDialogVisible.value = true
  }).catch((error: unknown) => {
    console.error('查询标本失败:', error)
  })
}

// 列表行签收
const handleReceiveRow = async (row: Specimen) => {
  try {
    await ElMessageBox.confirm(`确认签收标本 ${row.specimenNo}？`, '签收确认', { confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning' })
    await receiveSpecimen(row.specimenNo)
    ElMessage.success('签收成功')
    fetchData()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('签收失败:', error)
    }
  }
}

// 列表行拒收
const handleRejectRow = (row: Specimen) => {
  rejectForm.barcode = row.specimenNo
  rejectForm.specimenNo = row.specimenNo
  rejectForm.specimenId = row.id
  rejectForm.rejectReason = ''
  rejectDialogVisible.value = true
}

// 确认拒收
const confirmReject = async () => {
  if (!rejectFormRef.value) return

  await rejectFormRef.value.validate(async (valid) => {
    if (!valid) return

    rejectLoading.value = true
    try {
      if (isBatchReject.value) {
        await Promise.all(batchRejectBarcodes.value.map(barcode =>
          rejectSpecimen(barcode, rejectForm.rejectReason)
        ))
        ElMessage.success(`成功拒收 ${batchRejectBarcodes.value.length} 个标本`)
      } else {
        await rejectSpecimen(rejectForm.barcode, rejectForm.rejectReason)
        ElMessage.success('拒收成功')
      }
      rejectDialogVisible.value = false
      scanForm.barcode = ''
      scannedSpecimen.value = null
      isBatchReject.value = false
      batchRejectBarcodes.value = []
      fetchData()
    } catch (error) {
      console.error('拒收失败:', error)
    } finally {
      rejectLoading.value = false
    }
  })
}

// 批量签收
const handleBatchReceive = async () => {
  const receivableRows = selectedRows.value.filter(r => r.status === 'REGISTERED')
  if (receivableRows.length === 0) {
    ElMessage.warning('请选择已采集(待签收)状态的标本')
    return
  }

  try {
    await ElMessageBox.confirm(`确认批量签收 ${receivableRows.length} 个标本？`, '批量签收', { confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning' })
    const barcodes = receivableRows.map(r => r.specimenNo)
    await batchReceiveSpecimen(barcodes)
    ElMessage.success(`成功签收 ${receivableRows.length} 个标本`)
    fetchData()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('批量签收失败:', error)
    }
  }
}

// 批量拒收
const handleBatchReject = () => {
  const rejectableRows = selectedRows.value.filter(r => ['REGISTERED', 'RECEIVED'].includes(r.status))
  if (rejectableRows.length === 0) {
    ElMessage.warning('请选择可拒收状态的标本')
    return
  }

  isBatchReject.value = true
  batchRejectBarcodes.value = rejectableRows.map(r => r.specimenNo)
  rejectForm.barcode = ''
  rejectForm.specimenNo = `${rejectableRows.length} 个标本`
  rejectForm.specimenId = ''
  rejectForm.rejectReason = ''
  rejectDialogVisible.value = true
}

// 查看详情
const handleDetail = async (row: Specimen) => {
  try {
    currentSpecimen.value = await getSpecimenDetail(row.id)
    detailDialogVisible.value = true
  } catch (error) {
    console.error('获取标本详情失败:', error)
  }
}

const handleSearch = () => {
  queryParams.pageNum = 1
  fetchData()
}

const handleResetQuery = () => {
  queryParams.specimenNo = ''
  queryParams.status = 'REGISTERED'
  queryParams.deptId = undefined
  dateRange.value = null
  queryParams.collectTimeStart = ''
  queryParams.collectTimeEnd = ''
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

const handleSelectionChange = (rows: Specimen[]) => {
  selectedRows.value = rows
}

onMounted(() => {
  fetchDepartments()
  fetchData()
  // 自动聚焦条码输入框
  nextTick(() => {
    barcodeInputRef.value?.focus()
  })
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

.scan-card {
  margin-bottom: 16px;

  .scan-header {
    display: flex;
    align-items: center;
    gap: 8px;
    font-size: 16px;
    font-weight: 600;
  }

  .scan-form {
    display: flex;
    align-items: center;
  }
}

.scanned-info {
  margin-top: 16px;
  padding: 12px;
  background-color: var(--el-fill-color-light);
  border-radius: 4px;

  .test-items-preview {
    margin-top: 12px;
    display: flex;
    align-items: center;
    flex-wrap: wrap;
    gap: 4px;

    .label {
      font-weight: 500;
      color: var(--el-text-color-regular);
      margin-right: 4px;
    }
  }
}

.search-form {
  margin-bottom: 16px;
}

.batch-actions-bar {
  display: flex;
  gap: 8px;
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
</style>
