<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>校准维护记录</span>
          <el-button type="primary" @click="handleAdd">
            <el-icon><Plus /></el-icon>
            新增记录
          </el-button>
        </div>
      </template>

      <el-form :model="queryParams" inline class="search-form">
        <el-form-item label="设备">
          <el-select v-model="queryParams.equipmentId" placeholder="请选择设备" clearable filterable>
            <el-option v-for="item in equipmentOptions" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="维护类型">
          <el-select v-model="queryParams.type" placeholder="请选择类型" clearable>
            <el-option label="校准" value="calibration" />
            <el-option label="维护" value="maintenance" />
            <el-option label="维修" value="repair" />
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
        <el-table-column prop="equipmentName" label="设备名称" min-width="150" />
        <el-table-column label="维护类型" width="100">
          <template #default="{ row }">
            <el-tag :type="getMaintenanceType(row.type)">
              {{ getMaintenanceLabel(row.type) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="content" label="维护内容" min-width="200" show-overflow-tooltip />
        <el-table-column prop="technician" label="技术人员" width="100" />
        <el-table-column label="维护结果" width="100">
          <template #default="{ row }">
            <el-tag :type="getResultType(row.result)">
              {{ getResultLabel(row.result) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="startTime" label="开始时间" width="160" />
        <el-table-column prop="endTime" label="结束时间" width="160" />
        <el-table-column label="费用" width="100">
          <template #default="{ row }">
            {{ row.cost ? `¥${row.cost}` : '-' }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" link @click="handleView(row)">查看</el-button>
            <el-button type="primary" link @click="handleEdit(row)">编辑</el-button>
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

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="700px" destroy-on-close>
      <el-form ref="formRef" :model="formData" :rules="formRules" label-width="100px">
        <el-form-item label="设备" prop="equipmentId">
          <el-select v-model="formData.equipmentId" placeholder="请选择设备" filterable :disabled="!!formData.id">
            <el-option v-for="item in equipmentOptions" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="维护类型" prop="type">
          <el-select v-model="formData.type" placeholder="请选择类型">
            <el-option label="校准" value="calibration" />
            <el-option label="维护" value="maintenance" />
            <el-option label="维修" value="repair" />
          </el-select>
        </el-form-item>
        <el-form-item label="维护内容" prop="content">
          <el-input v-model="formData.content" type="textarea" :rows="3" placeholder="请输入维护内容" />
        </el-form-item>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="开始时间" prop="startTime">
              <el-date-picker v-model="formData.startTime" type="datetime" placeholder="请选择时间" value-format="YYYY-MM-DD HH:mm:ss" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="结束时间" prop="endTime">
              <el-date-picker v-model="formData.endTime" type="datetime" placeholder="请选择时间" value-format="YYYY-MM-DD HH:mm:ss" style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="技术人员" prop="technician">
              <el-input v-model="formData.technician" placeholder="请输入技术人员" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="维护结果" prop="result">
              <el-select v-model="formData.result" placeholder="请选择结果">
                <el-option label="成功" value="success" />
                <el-option label="部分成功" value="partial" />
                <el-option label="失败" value="failed" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="费用" prop="cost">
              <el-input-number v-model="formData.cost" :min="0" :precision="2" placeholder="请输入费用" style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="附件" prop="attachments">
          <el-upload
            v-model:file-list="fileList"
            action="/api/upload"
            :limit="5"
            :on-success="handleUploadSuccess"
            :on-remove="handleUploadRemove"
          >
            <el-button type="primary">上传附件</el-button>
            <template #tip>
              <div class="el-upload__tip">最多上传5个附件</div>
            </template>
          </el-upload>
        </el-form-item>
        <el-form-item label="备注" prop="notes">
          <el-input v-model="formData.notes" type="textarea" :rows="2" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" :loading="submitLoading" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="viewDialogVisible" title="维护记录详情" width="700px">
      <el-descriptions :column="2" border>
        <el-descriptions-item label="设备名称">{{ currentRecord?.equipmentName }}</el-descriptions-item>
        <el-descriptions-item label="维护类型">{{ getMaintenanceLabel(currentRecord?.type) }}</el-descriptions-item>
        <el-descriptions-item label="技术人员">{{ currentRecord?.technician }}</el-descriptions-item>
        <el-descriptions-item label="维护结果">{{ getResultLabel(currentRecord?.result) }}</el-descriptions-item>
        <el-descriptions-item label="开始时间">{{ currentRecord?.startTime }}</el-descriptions-item>
        <el-descriptions-item label="结束时间">{{ currentRecord?.endTime }}</el-descriptions-item>
        <el-descriptions-item label="费用">{{ currentRecord?.cost ? `¥${currentRecord.cost}` : '-' }}</el-descriptions-item>
        <el-descriptions-item label="创建时间">{{ currentRecord?.createdAt }}</el-descriptions-item>
        <el-descriptions-item label="维护内容" :span="2">{{ currentRecord?.content }}</el-descriptions-item>
        <el-descriptions-item label="备注" :span="2">{{ currentRecord?.notes || '-' }}</el-descriptions-item>
        <el-descriptions-item v-if="currentRecord?.attachments?.length" label="附件" :span="2">
          <el-link v-for="(url, index) in currentRecord.attachments" :key="index" :href="url" target="_blank" type="primary">
            附件{{ index + 1 }}
          </el-link>
        </el-descriptions-item>
      </el-descriptions>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, computed, onMounted } from 'vue'
import { ElMessage, ElMessageBox, type FormInstance, type FormRules, type UploadUserFile } from 'element-plus'
import { Plus, Search, Refresh } from '@element-plus/icons-vue'
import type { MaintenanceRecord, MaintenanceQuery, Equipment } from '@/api/equipment'
import { getMaintenanceRecords, getMaintenanceDetail, createMaintenanceRecord, updateMaintenanceRecord, deleteMaintenanceRecord, getEquipmentList } from '@/api/equipment'

const loading = ref(false)
const submitLoading = ref(false)
const recordList = ref<MaintenanceRecord[]>([])
const total = ref(0)
const equipmentOptions = ref<Equipment[]>([])

const dateRange = ref<string[]>([])

const queryParams = reactive<MaintenanceQuery>({
  page: 1,
  pageSize: 10,
  equipmentId: '',
  type: '',
  startDate: '',
  endDate: ''
})

const dialogVisible = ref(false)
const dialogTitle = computed(() => (formData.id ? '编辑维护记录' : '新增维护记录'))
const formRef = ref<FormInstance>()
const fileList = ref<UploadUserFile[]>([])
const formData = reactive<Partial<MaintenanceRecord>>({
  equipmentId: '',
  type: 'maintenance',
  content: '',
  technician: '',
  startTime: '',
  endTime: '',
  result: 'success',
  cost: 0,
  notes: '',
  attachments: []
})

const formRules: FormRules = {
  equipmentId: [{ required: true, message: '请选择设备', trigger: 'change' }],
  type: [{ required: true, message: '请选择维护类型', trigger: 'change' }],
  content: [{ required: true, message: '请输入维护内容', trigger: 'blur' }],
  technician: [{ required: true, message: '请输入技术人员', trigger: 'blur' }],
  startTime: [{ required: true, message: '请选择开始时间', trigger: 'change' }],
  result: [{ required: true, message: '请选择维护结果', trigger: 'change' }]
}

const viewDialogVisible = ref(false)
const currentRecord = ref<MaintenanceRecord | null>(null)

const getMaintenanceType = (type: string): 'success' | 'info' | 'warning' | 'danger' | 'primary' => {
  const map: Record<string, 'success' | 'info' | 'warning' | 'danger' | 'primary'> = {
    calibration: 'primary',
    maintenance: 'success',
    repair: 'warning'
  }
  return map[type] || 'info'
}

const getMaintenanceLabel = (type?: string) => {
  const map: Record<string, string> = {
    calibration: '校准',
    maintenance: '维护',
    repair: '维修'
  }
  return map[type || ''] || type
}

const getResultType = (result: string): 'success' | 'info' | 'warning' | 'danger' | 'primary' => {
  const map: Record<string, 'success' | 'info' | 'warning' | 'danger' | 'primary'> = {
    success: 'success',
    partial: 'warning',
    failed: 'danger'
  }
  return map[result] || 'info'
}

const getResultLabel = (result?: string) => {
  const map: Record<string, string> = {
    success: '成功',
    partial: '部分成功',
    failed: '失败'
  }
  return map[result || ''] || result
}

const fetchEquipmentOptions = async () => {
  try {
    const res = await getEquipmentList({ page: 1, pageSize: 1000 })
    equipmentOptions.value = res.list
  } catch {
    console.error('获取设备列表失败')
  }
}

const fetchRecordList = async () => {
  loading.value = true
  try {
    const res = await getMaintenanceRecords(queryParams)
    recordList.value = res.list
    total.value = res.total
  } catch {
    ElMessage.error('获取维护记录失败')
  } finally {
    loading.value = false
  }
}

const handleDateChange = (val: string[] | null) => {
  if (val) {
    queryParams.startDate = val[0]
    queryParams.endDate = val[1]
  } else {
    queryParams.startDate = ''
    queryParams.endDate = ''
  }
}

const handleQuery = () => {
  queryParams.page = 1
  fetchRecordList()
}

const handleReset = () => {
  queryParams.equipmentId = ''
  queryParams.type = ''
  queryParams.startDate = ''
  queryParams.endDate = ''
  dateRange.value = []
  handleQuery()
}

const resetForm = () => {
  Object.assign(formData, {
    id: undefined,
    equipmentId: '',
    type: 'maintenance',
    content: '',
    technician: '',
    startTime: '',
    endTime: '',
    result: 'success',
    cost: 0,
    notes: '',
    attachments: []
  })
  fileList.value = []
}

const handleAdd = () => {
  resetForm()
  dialogVisible.value = true
}

const handleEdit = async (row: MaintenanceRecord) => {
  try {
    const detail = await getMaintenanceDetail(row.id)
    Object.assign(formData, detail)
    if (detail.attachments?.length) {
      fileList.value = detail.attachments.map((url, index) => ({
        name: `附件${index + 1}`,
        url
      }))
    }
    dialogVisible.value = true
  } catch {
    ElMessage.error('获取记录详情失败')
  }
}

const handleView = async (row: MaintenanceRecord) => {
  try {
    currentRecord.value = await getMaintenanceDetail(row.id)
    viewDialogVisible.value = true
  } catch {
    ElMessage.error('获取记录详情失败')
  }
}

const handleDelete = async (row: MaintenanceRecord) => {
  try {
    await ElMessageBox.confirm('确定要删除该维护记录吗？', '提示', {
      type: 'warning'
    })
    await deleteMaintenanceRecord(row.id)
    ElMessage.success('删除成功')
    fetchRecordList()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败')
    }
  }
}

const handleUploadSuccess = (response: { url: string }) => {
  if (!formData.attachments) {
    formData.attachments = []
  }
  formData.attachments.push(response.url)
}

const handleUploadRemove = (file: UploadUserFile) => {
  const index = formData.attachments?.findIndex(url => url === file.url) ?? -1
  if (index > -1 && formData.attachments) {
    formData.attachments.splice(index, 1)
  }
}

const handleSubmit = async () => {
  const valid = await formRef.value?.validate()
  if (!valid) return

  submitLoading.value = true
  try {
    if (formData.id) {
      await updateMaintenanceRecord(formData.id, formData)
      ElMessage.success('更新成功')
    } else {
      await createMaintenanceRecord(formData)
      ElMessage.success('创建成功')
    }
    dialogVisible.value = false
    fetchRecordList()
  } catch {
    ElMessage.error('操作失败')
  } finally {
    submitLoading.value = false
  }
}

onMounted(() => {
  fetchEquipmentOptions()
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
</style>
