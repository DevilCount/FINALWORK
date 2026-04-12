<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>标本列表</span>
          <el-button type="primary" @click="handleRegister">
            <el-icon><Plus /></el-icon>
            标本登记
          </el-button>
        </div>
      </template>

      <el-form :model="queryParams" inline class="search-form">
        <el-form-item label="标本编号">
          <el-input v-model="queryParams.specimenNo" placeholder="请输入标本编号" clearable @keyup.enter="handleSearch" />
        </el-form-item>
        <el-form-item label="患者姓名">
          <el-input v-model="queryParams.patientName" placeholder="请输入患者姓名" clearable @keyup.enter="handleSearch" />
        </el-form-item>
        <el-form-item label="身份证号">
          <el-input v-model="queryParams.patientIdNo" placeholder="请输入身份证号" clearable @keyup.enter="handleSearch" />
        </el-form-item>
        <el-form-item label="标本状态">
          <el-select v-model="queryParams.status" placeholder="请选择状态" clearable style="width: 150px">
            <el-option v-for="item in statusOptions" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="标本类型">
          <el-select v-model="queryParams.specimenTypeId" placeholder="请选择类型" clearable style="width: 150px">
            <el-option v-for="item in specimenTypes" :key="item.id" :label="item.typeName" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="采集时间">
          <el-date-picker
            v-model="collectTimeRange"
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
        <el-table-column prop="bedNo" label="床号" width="80" />
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
        <el-table-column prop="createTime" label="创建时间" width="160" />
        <el-table-column label="操作" width="280" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" link size="small" @click="handleDetail(row)">详情</el-button>
            <el-button type="primary" link size="small" @click="handleTrace(row)">追溯</el-button>
            <el-button v-if="row.status === 'REGISTERED'" type="success" link size="small" @click="handleReceive(row)">接收</el-button>
            <el-button v-if="['REGISTERED', 'RECEIVED'].includes(row.status)" type="danger" link size="small" @click="handleReject(row)">拒收</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination-container">
        <div class="batch-actions">
          <el-button v-if="selectedRows.some(r => r.status === 'REGISTERED')" type="success" @click="handleBatchReceive">
          批量接收
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

    <el-dialog v-model="rejectDialogVisible" title="标本拒收" width="500px">
      <el-form :model="rejectForm" label-width="80px">
        <el-form-item label="拒收原因" required>
          <el-input v-model="rejectForm.reason" type="textarea" :rows="3" placeholder="请输入拒收原因" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="rejectDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="confirmReject">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Plus, Search, Refresh } from '@element-plus/icons-vue'
import type { Specimen, SpecimenQueryParams, SpecimenStatus, SpecimenType } from '@/types/specimen'
import { getSpecimenList, receiveSpecimen, rejectSpecimen, batchReceiveSpecimen, getSpecimenTypes } from '@/api/specimen'

const router = useRouter()

const loading = ref(false)
const tableData = ref<Specimen[]>([])
const total = ref(0)
const selectedRows = ref<Specimen[]>([])
const specimenTypes = ref<SpecimenType[]>([])

const collectTimeRange = ref<[string, string] | null>(null)

const queryParams = reactive<SpecimenQueryParams>({
  pageNum: 1,
  pageSize: 10,
  specimenNo: '',
  patientName: '',
  patientIdNo: '',
  status: '',
  specimenTypeId: undefined,
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
  { label: '已取消', value: 'CANCELLED' },
]

const rejectDialogVisible = ref(false)
const rejectForm = reactive({
  id: '',
  specimenNo: '',
  reason: '',
})

const getStatusTagType = (status: SpecimenStatus): 'success' | 'primary' | 'warning' | 'info' | 'danger' | undefined => {
  const typeMap: Record<SpecimenStatus, 'success' | 'primary' | 'warning' | 'info' | 'danger' | undefined> = {
    REGISTERED: 'info',
    RECEIVED: 'primary',
    STORAGE: 'warning',
    TESTING: undefined,
    COMPLETED: 'success',
    REJECTED: 'danger',
    CANCELLED: 'info',
  }
  return typeMap[status]
}

const fetchSpecimenTypes = async () => {
  try {
    specimenTypes.value = await getSpecimenTypes()
  } catch (error) {
    console.error('获取标本类型失败:', error)
  }
}

const fetchData = async () => {
  loading.value = true
  try {
    if (collectTimeRange.value) {
      queryParams.collectTimeStart = collectTimeRange.value[0]
      queryParams.collectTimeEnd = collectTimeRange.value[1]
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

const handleSearch = () => {
  queryParams.pageNum = 1
  fetchData()
}

const handleReset = () => {
  queryParams.specimenNo = ''
  queryParams.patientName = ''
  queryParams.patientIdNo = ''
  queryParams.status = ''
  queryParams.specimenTypeId = undefined
  collectTimeRange.value = null
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

const handleRegister = () => {
  router.push('/specimen/register')
}

const handleDetail = (row: Specimen) => {
  router.push(`/specimen/detail/${row.id}`)
}

const handleTrace = (row: Specimen) => {
  router.push(`/specimen/trace?specimenId=${row.id}`)
}

const handleReceive = async (row: Specimen) => {
  try {
    await ElMessageBox.confirm('确认接收该标本？', '提示', { type: 'warning' })
    await receiveSpecimen(row.id)
    ElMessage.success('接收成功')
    fetchData()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('接收失败:', error)
    }
  }
}

const handleReject = (row: Specimen) => {
  rejectForm.id = row.id
  rejectForm.specimenNo = row.specimenNo
  rejectForm.reason = ''
  rejectDialogVisible.value = true
}

const confirmReject = async () => {
  if (!rejectForm.reason.trim()) {
    ElMessage.warning('请输入拒收原因')
    return
  }
  try {
    await rejectSpecimen(rejectForm.id, rejectForm.reason)
    ElMessage.success('拒收成功')
    rejectDialogVisible.value = false
    fetchData()
  } catch (error) {
    console.error('拒收失败:', error)
  }
}

const handleBatchReceive = async () => {
  const ids = selectedRows.value.filter(r => r.status === 'REGISTERED').map(r => r.id)
  if (ids.length === 0) {
    ElMessage.warning('请选择已登记的标本')
    return
  }
  try {
    await ElMessageBox.confirm(`确认批量接收 ${ids.length} 个标本？`, '提示', { type: 'warning' })
    await batchReceiveSpecimen(ids)
    ElMessage.success(`成功接收 ${ids.length} 个标本`)
    fetchData()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('批量接收失败:', error)
    }
  }
}

onMounted(() => {
  fetchSpecimenTypes()
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

.mr-1 {
  margin-right: 4px;
}
</style>
