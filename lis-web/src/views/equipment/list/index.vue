<template>
  <div class="page-container">
    <el-row :gutter="20">
      <el-col :span="24">
        <el-card class="status-overview">
          <template #header>
            <div class="card-header">
              <span>设备状态概览</span>
              <el-button type="primary" size="small" @click="refreshStatus">
                <el-icon><Refresh /></el-icon>
                刷新
              </el-button>
            </div>
          </template>
          <el-row :gutter="20">
            <el-col :span="6">
              <div class="status-card online">
                <div class="status-icon">
                  <el-icon><CircleCheck /></el-icon>
                </div>
                <div class="status-info">
                  <div class="status-count">{{ statusOverview.online }}</div>
                  <div class="status-label">在线设备</div>
                </div>
              </div>
            </el-col>
            <el-col :span="6">
              <div class="status-card offline">
                <div class="status-icon">
                  <el-icon><CircleClose /></el-icon>
                </div>
                <div class="status-info">
                  <div class="status-count">{{ statusOverview.offline }}</div>
                  <div class="status-label">离线设备</div>
                </div>
              </div>
            </el-col>
            <el-col :span="6">
              <div class="status-card maintenance">
                <div class="status-icon">
                  <el-icon><Tools /></el-icon>
                </div>
                <div class="status-info">
                  <div class="status-count">{{ statusOverview.maintenance }}</div>
                  <div class="status-label">维护中</div>
                </div>
              </div>
            </el-col>
            <el-col :span="6">
              <div class="status-card fault">
                <div class="status-icon">
                  <el-icon><Warning /></el-icon>
                </div>
                <div class="status-info">
                  <div class="status-count">{{ statusOverview.fault }}</div>
                  <div class="status-label">故障设备</div>
                </div>
              </div>
            </el-col>
          </el-row>
        </el-card>
      </el-col>
    </el-row>

    <el-card class="table-card">
      <template #header>
        <div class="card-header">
          <span>设备台账</span>
          <el-button type="primary" @click="handleAdd">
            <el-icon><Plus /></el-icon>
            新增设备
          </el-button>
        </div>
      </template>

      <el-form :model="queryParams" inline class="search-form">
        <el-form-item label="关键词">
          <el-input
            v-model="queryParams.keyword"
            placeholder="设备名称/编号/型号"
            clearable
            @keyup.enter="handleQuery"
          />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
            <el-option label="在线" value="online" />
            <el-option label="离线" value="offline" />
            <el-option label="维护中" value="maintenance" />
            <el-option label="故障" value="fault" />
          </el-select>
        </el-form-item>
        <el-form-item label="科室">
          <el-select v-model="queryParams.department" placeholder="请选择科室" clearable>
            <el-option v-for="dept in departments" :key="dept.value" :label="dept.label" :value="dept.value" />
          </el-select>
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

      <el-table v-loading="loading" :data="equipmentList" border stripe>
        <el-table-column prop="code" label="设备编号" width="120" />
        <el-table-column prop="name" label="设备名称" min-width="150" />
        <el-table-column prop="model" label="型号" width="120" />
        <el-table-column prop="manufacturer" label="厂商" width="120" />
        <el-table-column prop="department" label="所属科室" width="120" />
        <el-table-column prop="location" label="位置" width="100" />
        <el-table-column prop="responsiblePerson" label="负责人" width="100" />
        <el-table-column label="状态" width="100" align="center">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.status)">
              {{ getStatusLabel(row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="下次校准" width="120">
          <template #default="{ row }">
            <span :class="{ 'text-danger': isNearExpiry(row.nextCalibrationDate) }">
              {{ row.nextCalibrationDate || '-' }}
            </span>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" link @click="handleDetail(row)">详情</el-button>
            <el-button type="primary" link @click="handleEdit(row)">编辑</el-button>
            <el-button type="warning" link @click="handleMaintenance(row)">维护</el-button>
            <el-button type="danger" link @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        v-model:current-page="queryParams.page"
        v-model:page-size="queryParams.pageSize"
        :total="total"
        :page-sizes="[10, 20, 50, 100]"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleQuery"
        @current-change="handleQuery"
      />
    </el-card>

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="600px" destroy-on-close>
      <el-form ref="formRef" :model="formData" :rules="formRules" label-width="100px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="设备编号" prop="code">
              <el-input v-model="formData.code" placeholder="请输入设备编号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="设备名称" prop="name">
              <el-input v-model="formData.name" placeholder="请输入设备名称" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="型号" prop="model">
              <el-input v-model="formData.model" placeholder="请输入型号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="厂商" prop="manufacturer">
              <el-input v-model="formData.manufacturer" placeholder="请输入厂商" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="序列号" prop="serialNumber">
              <el-input v-model="formData.serialNumber" placeholder="请输入序列号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态" prop="status">
              <el-select v-model="formData.status" placeholder="请选择状态">
                <el-option label="在线" value="online" />
                <el-option label="离线" value="offline" />
                <el-option label="维护中" value="maintenance" />
                <el-option label="故障" value="fault" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="所属科室" prop="department">
              <el-select v-model="formData.department" placeholder="请选择科室">
                <el-option v-for="dept in departments" :key="dept.value" :label="dept.label" :value="dept.value" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="位置" prop="location">
              <el-input v-model="formData.location" placeholder="请输入位置" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="负责人" prop="responsiblePerson">
              <el-input v-model="formData.responsiblePerson" placeholder="请输入负责人" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="购买日期" prop="purchaseDate">
              <el-date-picker v-model="formData.purchaseDate" type="date" placeholder="请选择日期" value-format="YYYY-MM-DD" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="保修到期" prop="warrantyExpiry">
              <el-date-picker v-model="formData.warrantyExpiry" type="date" placeholder="请选择日期" value-format="YYYY-MM-DD" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="下次校准" prop="nextCalibrationDate">
              <el-date-picker v-model="formData.nextCalibrationDate" type="date" placeholder="请选择日期" value-format="YYYY-MM-DD" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" :loading="submitLoading" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="maintenanceDialogVisible" title="新增维护记录" width="600px" destroy-on-close>
      <el-form ref="maintenanceFormRef" :model="maintenanceFormData" :rules="maintenanceFormRules" label-width="100px">
        <el-form-item label="设备" prop="equipmentId">
          <el-input :model-value="currentEquipment?.name" disabled />
        </el-form-item>
        <el-form-item label="维护类型" prop="type">
          <el-select v-model="maintenanceFormData.type" placeholder="请选择类型">
            <el-option label="校准" value="calibration" />
            <el-option label="维护" value="maintenance" />
            <el-option label="维修" value="repair" />
          </el-select>
        </el-form-item>
        <el-form-item label="维护内容" prop="content">
          <el-input v-model="maintenanceFormData.content" type="textarea" :rows="3" placeholder="请输入维护内容" />
        </el-form-item>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="开始时间" prop="startTime">
              <el-date-picker v-model="maintenanceFormData.startTime" type="datetime" placeholder="请选择时间" value-format="YYYY-MM-DD HH:mm:ss" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="结束时间" prop="endTime">
              <el-date-picker v-model="maintenanceFormData.endTime" type="datetime" placeholder="请选择时间" value-format="YYYY-MM-DD HH:mm:ss" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="技术人员" prop="technician">
              <el-input v-model="maintenanceFormData.technician" placeholder="请输入技术人员" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="维护结果" prop="result">
              <el-select v-model="maintenanceFormData.result" placeholder="请选择结果">
                <el-option label="成功" value="success" />
                <el-option label="部分成功" value="partial" />
                <el-option label="失败" value="failed" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="费用" prop="cost">
          <el-input-number v-model="maintenanceFormData.cost" :min="0" :precision="2" placeholder="请输入费用" />
        </el-form-item>
        <el-form-item label="备注" prop="notes">
          <el-input v-model="maintenanceFormData.notes" type="textarea" :rows="2" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="maintenanceDialogVisible = false">取消</el-button>
        <el-button type="primary" :loading="submitLoading" @click="handleMaintenanceSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox, type FormInstance, type FormRules } from 'element-plus'
import { Plus, Search, Refresh, CircleCheck, CircleClose, Tools, Warning } from '@element-plus/icons-vue'
import type { Equipment, EquipmentQuery, MaintenanceRecord } from '@/api/equipment'
import { getEquipmentList, createEquipment, updateEquipment, deleteEquipment, createMaintenanceRecord } from '@/api/equipment'

const router = useRouter()

const loading = ref(false)
const submitLoading = ref(false)
const equipmentList = ref<Equipment[]>([])
const total = ref(0)

const queryParams = reactive<EquipmentQuery>({
  page: 1,
  pageSize: 10,
  keyword: '',
  status: '',
  department: ''
})

const statusOverview = reactive({
  online: 0,
  offline: 0,
  maintenance: 0,
  fault: 0
})

const departments = [
  { label: '检验科', value: '检验科' },
  { label: '生化室', value: '生化室' },
  { label: '免疫室', value: '免疫室' },
  { label: '微生物室', value: '微生物室' },
  { label: '门诊检验', value: '门诊检验' }
]

const dialogVisible = ref(false)
const dialogTitle = computed(() => (formData.id ? '编辑设备' : '新增设备'))
const formRef = ref<FormInstance>()
const formData = reactive<Partial<Equipment>>({
  code: '',
  name: '',
  model: '',
  manufacturer: '',
  serialNumber: '',
  status: 'online',
  department: '',
  location: '',
  responsiblePerson: '',
  purchaseDate: '',
  warrantyExpiry: '',
  nextCalibrationDate: ''
})

const formRules: FormRules = {
  code: [{ required: true, message: '请输入设备编号', trigger: 'blur' }],
  name: [{ required: true, message: '请输入设备名称', trigger: 'blur' }],
  model: [{ required: true, message: '请输入型号', trigger: 'blur' }],
  manufacturer: [{ required: true, message: '请输入厂商', trigger: 'blur' }],
  status: [{ required: true, message: '请选择状态', trigger: 'change' }],
  department: [{ required: true, message: '请选择科室', trigger: 'change' }]
}

const maintenanceDialogVisible = ref(false)
const maintenanceFormRef = ref<FormInstance>()
const currentEquipment = ref<Equipment | null>(null)
const maintenanceFormData = reactive<Partial<MaintenanceRecord>>({
  equipmentId: '',
  type: 'maintenance',
  content: '',
  technician: '',
  startTime: '',
  endTime: '',
  result: 'success',
  cost: 0,
  notes: ''
})

const maintenanceFormRules: FormRules = {
  type: [{ required: true, message: '请选择维护类型', trigger: 'change' }],
  content: [{ required: true, message: '请输入维护内容', trigger: 'blur' }],
  technician: [{ required: true, message: '请输入技术人员', trigger: 'blur' }],
  startTime: [{ required: true, message: '请选择开始时间', trigger: 'change' }],
  result: [{ required: true, message: '请选择维护结果', trigger: 'change' }]
}

const getStatusType = (status: string): 'success' | 'info' | 'warning' | 'danger' | 'primary' => {
  const map: Record<string, 'success' | 'info' | 'warning' | 'danger' | 'primary'> = {
    online: 'success',
    offline: 'info',
    maintenance: 'warning',
    fault: 'danger'
  }
  return map[status] || 'info'
}

const getStatusLabel = (status: string) => {
  const map: Record<string, string> = {
    online: '在线',
    offline: '离线',
    maintenance: '维护中',
    fault: '故障'
  }
  return map[status] || status
}

const isNearExpiry = (date: string) => {
  if (!date) return false
  const targetDate = new Date(date)
  const today = new Date()
  const diff = targetDate.getTime() - today.getTime()
  return diff < 7 * 24 * 60 * 60 * 1000
}

const fetchEquipmentList = async () => {
  loading.value = true
  try {
    const res = await getEquipmentList(queryParams)
    equipmentList.value = res.list
    total.value = res.total
    updateStatusOverview(res.list)
  } catch {
    ElMessage.error('获取设备列表失败')
  } finally {
    loading.value = false
  }
}

const updateStatusOverview = (list: Equipment[]) => {
  statusOverview.online = list.filter(item => item.status === 'online').length
  statusOverview.offline = list.filter(item => item.status === 'offline').length
  statusOverview.maintenance = list.filter(item => item.status === 'maintenance').length
  statusOverview.fault = list.filter(item => item.status === 'fault').length
}

const refreshStatus = () => {
  fetchEquipmentList()
}

const handleQuery = () => {
  queryParams.page = 1
  fetchEquipmentList()
}

const handleReset = () => {
  queryParams.keyword = ''
  queryParams.status = ''
  queryParams.department = ''
  handleQuery()
}

const resetForm = () => {
  Object.assign(formData, {
    id: undefined,
    code: '',
    name: '',
    model: '',
    manufacturer: '',
    serialNumber: '',
    status: 'online',
    department: '',
    location: '',
    responsiblePerson: '',
    purchaseDate: '',
    warrantyExpiry: '',
    nextCalibrationDate: ''
  })
}

const handleAdd = () => {
  resetForm()
  dialogVisible.value = true
}

const handleEdit = (row: Equipment) => {
  Object.assign(formData, row)
  dialogVisible.value = true
}

const handleDetail = (row: Equipment) => {
  router.push(`/equipment/detail/${row.id}`)
}

const handleDelete = async (row: Equipment) => {
  try {
    await ElMessageBox.confirm('确定要删除该设备吗？', '提示', {
      type: 'warning'
    })
    await deleteEquipment(row.id)
    ElMessage.success('删除成功')
    fetchEquipmentList()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败')
    }
  }
}

const handleSubmit = async () => {
  const valid = await formRef.value?.validate()
  if (!valid) return

  submitLoading.value = true
  try {
    if (formData.id) {
      await updateEquipment(formData.id, formData)
      ElMessage.success('更新成功')
    } else {
      await createEquipment(formData)
      ElMessage.success('创建成功')
    }
    dialogVisible.value = false
    fetchEquipmentList()
  } catch {
    ElMessage.error('操作失败')
  } finally {
    submitLoading.value = false
  }
}

const handleMaintenance = (row: Equipment) => {
  currentEquipment.value = row
  maintenanceFormData.equipmentId = row.id
  maintenanceFormData.type = 'maintenance'
  maintenanceFormData.content = ''
  maintenanceFormData.technician = ''
  maintenanceFormData.startTime = ''
  maintenanceFormData.endTime = ''
  maintenanceFormData.result = 'success'
  maintenanceFormData.cost = 0
  maintenanceFormData.notes = ''
  maintenanceDialogVisible.value = true
}

const handleMaintenanceSubmit = async () => {
  const valid = await maintenanceFormRef.value?.validate()
  if (!valid) return

  submitLoading.value = true
  try {
    await createMaintenanceRecord(maintenanceFormData)
    ElMessage.success('维护记录创建成功')
    maintenanceDialogVisible.value = false
    fetchEquipmentList()
  } catch {
    ElMessage.error('创建失败')
  } finally {
    submitLoading.value = false
  }
}

onMounted(() => {
  fetchEquipmentList()
})
</script>

<style scoped lang="scss">
.page-container {
  padding: 0;
}

.status-overview {
  margin-bottom: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.status-card {
  display: flex;
  align-items: center;
  padding: 20px;
  border-radius: 8px;
  background: #f5f7fa;

  .status-icon {
    width: 60px;
    height: 60px;
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
    margin-right: 16px;

    .el-icon {
      font-size: 28px;
      color: #fff;
    }
  }

  .status-info {
    .status-count {
      font-size: 28px;
      font-weight: bold;
      line-height: 1.2;
    }

    .status-label {
      font-size: 14px;
      color: #909399;
    }
  }

  &.online .status-icon {
    background: linear-gradient(135deg, #67c23a, #85ce61);
  }

  &.offline .status-icon {
    background: linear-gradient(135deg, #909399, #b4b4b4);
  }

  &.maintenance .status-icon {
    background: linear-gradient(135deg, #e6a23c, #f5d442);
  }

  &.fault .status-icon {
    background: linear-gradient(135deg, #f56c6c, #fab6b6);
  }
}

.table-card {
  .search-form {
    margin-bottom: 16px;
  }

  .el-pagination {
    margin-top: 16px;
    justify-content: flex-end;
  }
}

.text-danger {
  color: #f56c6c;
}
</style>
