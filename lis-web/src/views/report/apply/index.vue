<template>
  <div class="page-container">
    <!-- 申请列表视图 -->
    <el-card v-if="viewMode === 'list'">
      <template #header>
        <div class="card-header">
          <span>检验申请</span>
          <el-button type="primary" @click="handleCreate">
            <el-icon><Plus /></el-icon>
            新建申请
          </el-button>
        </div>
      </template>

      <el-form :model="queryParams" inline class="search-form">
        <el-form-item label="患者姓名">
          <el-input v-model="queryParams.patientName" placeholder="请输入患者姓名" clearable @keyup.enter="handleSearch" />
        </el-form-item>
        <el-form-item label="申请状态">
          <el-select v-model="queryParams.status" placeholder="请选择状态" clearable style="width: 150px">
            <el-option v-for="item in statusOptions" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="申请时间">
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

      <el-table v-loading="loading" :data="tableData" border stripe>
        <el-table-column prop="reportNo" label="申请编号" width="150" />
        <el-table-column prop="patientName" label="患者姓名" width="100" />
        <el-table-column prop="patientGender" label="性别" width="60">
          <template #default="{ row }">
            {{ row.patientGender === 'M' ? '男' : row.patientGender === 'F' ? '女' : row.patientGender }}
          </template>
        </el-table-column>
        <el-table-column prop="patientAge" label="年龄" width="70" />
        <el-table-column prop="deptName" label="科室" width="120" />
        <el-table-column prop="clinicalDiagnosis" label="临床诊断" min-width="150" show-overflow-tooltip />
        <el-table-column prop="reportTypeName" label="报告类型" width="100" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="getStatusTagType(row.status)">{{ getStatusLabel(row.status) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="160" />
        <el-table-column label="操作" width="240" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" link size="small" @click="handleView(row)">查看</el-button>
            <el-button v-if="row.status === 'draft'" type="warning" link size="small" @click="handleEdit(row)">编辑</el-button>
            <el-button v-if="row.status === 'draft'" type="success" link size="small" @click="handleSubmitApply(row)">提交</el-button>
            <el-button v-if="row.status === 'draft'" type="danger" link size="small" @click="handleDelete(row)">删除</el-button>
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

    <!-- 新建/编辑申请表单视图 -->
    <el-card v-if="viewMode === 'form'">
      <template #header>
        <div class="card-header">
          <span>{{ isEdit ? '编辑检验申请' : '新建检验申请' }}</span>
          <el-button @click="handleBack">
            <el-icon><ArrowLeft /></el-icon>
            返回列表
          </el-button>
        </div>
      </template>

      <el-form ref="formRef" :model="formData" :rules="formRules" label-width="100px" class="apply-form">
        <el-divider content-position="left">患者信息</el-divider>

        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="患者姓名" prop="patientName">
              <el-input v-model="formData.patientName" placeholder="请输入患者姓名" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="身份证号" prop="patientIdNo">
              <el-input v-model="formData.patientIdNo" placeholder="请输入身份证号" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="性别" prop="patientGender">
              <el-radio-group v-model="formData.patientGender">
                <el-radio value="M">男</el-radio>
                <el-radio value="F">女</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="年龄" prop="patientAge">
              <el-input v-model="formData.patientAge" placeholder="请输入年龄" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="联系电话" prop="patientPhone">
              <el-input v-model="formData.patientPhone" placeholder="请输入联系电话" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="科室" prop="deptId">
              <el-select v-model="formData.deptId" placeholder="请选择科室" style="width: 100%">
                <el-option v-for="item in departments" :key="item.id" :label="item.deptName" :value="item.id" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-divider content-position="left">标本与诊断信息</el-divider>

        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="标本类型" prop="specimenTypeId">
              <el-select v-model="formData.specimenTypeId" placeholder="请选择标本类型" style="width: 100%" @change="handleSpecimenTypeChange">
                <el-option v-for="item in specimenTypes" :key="item.id" :label="item.typeName" :value="item.id" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="16">
            <el-form-item label="临床诊断" prop="clinicalDiagnosis">
              <el-input v-model="formData.clinicalDiagnosis" placeholder="请输入临床诊断" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-divider content-position="left">检验项目</el-divider>

        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="检验项目" prop="items">
              <div class="item-selector">
                <div class="search-box">
                  <el-input v-model="itemSearchKey" placeholder="搜索检验项目（名称/编码）" clearable @input="handleItemSearch">
                    <template #prefix>
                      <el-icon><Search /></el-icon>
                    </template>
                  </el-input>
                </div>
                <div class="item-list">
                  <el-checkbox-group v-model="selectedItemCodes">
                    <el-checkbox v-for="item in filteredItems" :key="item.itemCode" :value="item.itemCode" class="item-checkbox">
                      <span class="item-name">{{ item.itemName }}</span>
                      <span class="item-code">({{ item.itemCode }})</span>
                    </el-checkbox>
                  </el-checkbox-group>
                </div>
              </div>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="已选项目">
              <div class="selected-items">
                <el-tag
                  v-for="code in selectedItemCodes"
                  :key="code"
                  closable
                  size="large"
                  class="selected-tag"
                  @close="removeItem(code)"
                >
                  {{ getItemName(code) }}
                </el-tag>
                <span v-if="selectedItemCodes.length === 0" class="empty-tip">请选择检验项目</span>
              </div>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="备注">
              <el-input v-model="formData.remark" type="textarea" :rows="3" placeholder="请输入备注信息" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item>
          <el-button type="primary" :loading="submitting" @click="handleSave">
            <el-icon><Check /></el-icon>
            {{ isEdit ? '保存修改' : '创建申请' }}
          </el-button>
          <el-button @click="handleResetForm">
            <el-icon><Refresh /></el-icon>
            重置
          </el-button>
          <el-button @click="handleBack">取消</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 查看详情对话框 -->
    <el-dialog v-model="detailDialogVisible" title="申请详情" width="800px">
      <template v-if="currentDetail">
        <el-descriptions title="患者信息" :column="3" border>
          <el-descriptions-item label="患者姓名">{{ currentDetail.patientName }}</el-descriptions-item>
          <el-descriptions-item label="性别">{{ currentDetail.patientGender === 'M' ? '男' : '女' }}</el-descriptions-item>
          <el-descriptions-item label="年龄">{{ currentDetail.patientAge }}岁</el-descriptions-item>
          <el-descriptions-item label="科室">{{ currentDetail.deptName || '-' }}</el-descriptions-item>
          <el-descriptions-item label="床号">{{ currentDetail.bedNo || '-' }}</el-descriptions-item>
          <el-descriptions-item label="临床诊断">{{ currentDetail.clinicalDiagnosis || '-' }}</el-descriptions-item>
        </el-descriptions>

        <el-descriptions title="申请信息" :column="2" border class="mt-20">
          <el-descriptions-item label="申请编号">{{ currentDetail.reportNo }}</el-descriptions-item>
          <el-descriptions-item label="标本编号">{{ currentDetail.specimenNo || '-' }}</el-descriptions-item>
          <el-descriptions-item label="申请状态">
            <el-tag :type="getStatusTagType(currentDetail.status)">{{ getStatusLabel(currentDetail.status) }}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="创建时间">{{ currentDetail.createTime || '-' }}</el-descriptions-item>
        </el-descriptions>

        <div v-if="currentDetail.results && currentDetail.results.length > 0" class="section-title">检验项目</div>
        <el-table v-if="currentDetail.results && currentDetail.results.length > 0" :data="currentDetail.results" border stripe>
          <el-table-column prop="testItemCode" label="项目编码" width="120" />
          <el-table-column prop="testItemName" label="项目名称" min-width="200" />
        </el-table>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, computed, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Plus, Search, Refresh, Check, ArrowLeft } from '@element-plus/icons-vue'
import type { FormInstance, FormRules } from 'element-plus'
import { getReportList, getReportDetail, createReportApply, updateReportApply, deleteReportApply, submitReportApply, getReportApplyItems } from '@/api/report'
import { getSpecimenTypes, getDepartments } from '@/api/specimen'
import type { Report, ReportDetail, ReportStatus, ReportQueryParams } from '@/types/report'
import type { SpecimenType } from '@/types/specimen'

interface TestItemOption {
  itemCode: string
  itemName: string
  itemCategory?: string
}

// 视图模式：list 列表 / form 表单
const viewMode = ref<'list' | 'form'>('list')
const isEdit = ref(false)
const editingId = ref('')

const loading = ref(false)
const submitting = ref(false)
const tableData = ref<Report[]>([])
const total = ref(0)
const dateRange = ref<[string, string] | null>(null)

const departments = ref<{ id: string | number; deptName: string }[]>([])
const specimenTypes = ref<SpecimenType[]>([])
const testItemOptions = ref<TestItemOption[]>([])
const itemSearchKey = ref('')
const selectedItemCodes = ref<string[]>([])

const queryParams = reactive<ReportQueryParams>({
  pageNum: 1,
  pageSize: 10,
  patientName: '',
  status: '',
})

const statusOptions = [
  { label: '草稿', value: 'draft' },
  { label: '已提交', value: 'submitted' },
  { label: '已审核', value: 'audited' },
  { label: '已驳回', value: 'rejected' },
]

const formData = reactive({
  patientName: '',
  patientIdNo: '',
  patientGender: '',
  patientAge: '',
  patientPhone: '',
  deptId: null as number | null,
  clinicalDiagnosis: '',
  specimenTypeId: null as number | null,
  specimenTypeName: '',
  remark: '',
})

const formRef = ref<FormInstance>()

const formRules: FormRules = {
  patientName: [{ required: true, message: '请输入患者姓名', trigger: 'blur' }],
  patientGender: [{ required: true, message: '请选择性别', trigger: 'change' }],
  patientAge: [{ required: true, message: '请输入年龄', trigger: 'blur' }],
  deptId: [{ required: true, message: '请选择科室', trigger: 'change' }],
  specimenTypeId: [{ required: true, message: '请选择标本类型', trigger: 'change' }],
  clinicalDiagnosis: [{ required: true, message: '请输入临床诊断', trigger: 'blur' }],
}

const detailDialogVisible = ref(false)
const currentDetail = ref<ReportDetail | null>(null)

const filteredItems = computed(() => {
  let items = testItemOptions.value
  if (itemSearchKey.value) {
    const search = itemSearchKey.value.toLowerCase()
    items = items.filter(item =>
      item.itemName.toLowerCase().includes(search) ||
      item.itemCode.toLowerCase().includes(search)
    )
  }
  return items
})

const getStatusTagType = (status: ReportStatus | string): 'success' | 'warning' | 'info' | 'danger' | undefined => {
  const typeMap: Record<string, 'success' | 'warning' | 'info' | 'danger' | undefined> = {
    draft: 'info',
    submitted: 'warning',
    audited: 'success',
    rejected: 'danger',
    printed: undefined,
  }
  return typeMap[status] || 'info'
}

const getStatusLabel = (status: string): string => {
  const labelMap: Record<string, string> = {
    draft: '草稿',
    submitted: '已提交',
    audited: '已审核',
    rejected: '已驳回',
    printed: '已打印',
  }
  return labelMap[status] || status
}

const getItemName = (code: string) => {
  const item = testItemOptions.value.find(t => t.itemCode === code)
  return item ? `${item.itemName}(${item.itemCode})` : code
}

const handleSpecimenTypeChange = (val: number) => {
  const type = specimenTypes.value.find(t => t.id === val)
  formData.specimenTypeName = type ? type.typeName : ''
}

const removeItem = (code: string) => {
  const index = selectedItemCodes.value.indexOf(code)
  if (index > -1) {
    selectedItemCodes.value.splice(index, 1)
  }
}

const handleItemSearch = () => {}

// 获取科室列表
const fetchDepartments = async () => {
  try {
    departments.value = await getDepartments()
  } catch (error) {
    console.error('获取科室列表失败:', error)
  }
}

// 获取标本类型
const fetchSpecimenTypes = async () => {
  try {
    specimenTypes.value = await getSpecimenTypes()
  } catch (error) {
    console.error('获取标本类型失败:', error)
  }
}

// 获取检验项目列表
const fetchTestItems = async () => {
  try {
    testItemOptions.value = await getReportApplyItems()
  } catch (error) {
    console.error('获取检验项目列表失败:', error)
  }
}

// 获取申请列表
const fetchData = async () => {
  loading.value = true
  try {
    if (dateRange.value) {
      queryParams.testTimeStart = dateRange.value[0]
      queryParams.testTimeEnd = dateRange.value[1]
    } else {
      queryParams.testTimeStart = undefined
      queryParams.testTimeEnd = undefined
    }
    const res = await getReportList(queryParams)
    tableData.value = res.records
    total.value = res.total
  } catch (error) {
    console.error('获取申请列表失败:', error)
  } finally {
    loading.value = false
  }
}

const handleSearch = () => {
  queryParams.pageNum = 1
  fetchData()
}

const handleResetQuery = () => {
  queryParams.patientName = ''
  queryParams.status = ''
  dateRange.value = null
  queryParams.testTimeStart = undefined
  queryParams.testTimeEnd = undefined
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

const handleCreate = () => {
  isEdit.value = false
  editingId.value = ''
  resetFormData()
  selectedItemCodes.value = []
  viewMode.value = 'form'
}

const handleEdit = async (row: Report) => {
  try {
    const detail = await getReportDetail(row.id)
    isEdit.value = true
    editingId.value = row.id
    formData.patientName = detail.patientName
    formData.patientIdNo = (detail as any).patientIdNo || ''
    formData.patientGender = detail.patientGender
    formData.patientAge = detail.patientAge || ''
    formData.patientPhone = (detail as any).patientPhone || ''
    formData.deptId = detail.deptId
    formData.clinicalDiagnosis = detail.clinicalDiagnosis
    formData.specimenTypeId = (detail as any).specimenTypeId || 0
    formData.specimenTypeName = (detail as any).specimenTypeName || ''
    formData.remark = (detail as any).remark || ''
    selectedItemCodes.value = detail.results ? detail.results.map(item => item.testItemCode) : []
    viewMode.value = 'form'
  } catch (error) {
    console.error('获取申请详情失败:', error)
  }
}

const handleView = async (row: Report) => {
  try {
    currentDetail.value = await getReportDetail(row.id)
    detailDialogVisible.value = true
  } catch (error) {
    console.error('获取申请详情失败:', error)
  }
}

const handleSubmitApply = async (row: Report) => {
  try {
    await ElMessageBox.confirm('确认提交该检验申请？提交后将无法修改。', '提示', { confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning' })
    await submitReportApply(row.id)
    ElMessage.success('提交成功')
    fetchData()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('提交失败:', error)
    }
  }
}

const handleDelete = async (row: Report) => {
  try {
    await ElMessageBox.confirm('确认删除该检验申请？删除后不可恢复。', '提示', { confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning' })
    await deleteReportApply(row.id)
    ElMessage.success('删除成功')
    fetchData()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('删除失败:', error)
    }
  }
}

const handleSave = async () => {
  if (!formRef.value) return

  await formRef.value.validate(async (valid) => {
    if (!valid) return

    if (selectedItemCodes.value.length === 0) {
      ElMessage.warning('请选择至少一个检验项目')
      return
    }

    submitting.value = true
    try {
      const items = selectedItemCodes.value.map(code => {
        const item = testItemOptions.value.find(t => t.itemCode === code)
        return {
          itemCode: code,
          itemName: item?.itemName || '',
          unit: (item as any)?.unit || '',
          referenceLow: (item as any)?.referenceLow || null,
          referenceHigh: (item as any)?.referenceHigh || null,
        }
      })

      const submitData: Record<string, any> = {
        ...formData,
        testItems: items,
      }
      if (!submitData.deptId) delete submitData.deptId
      if (!submitData.specimenTypeId) delete submitData.specimenTypeId
      if (!submitData.patientIdNo) delete submitData.patientIdNo

      if (isEdit.value) {
        await updateReportApply(editingId.value, submitData)
        ElMessage.success('修改成功')
      } else {
        await createReportApply(submitData)
        ElMessage.success('创建成功')
      }
      viewMode.value = 'list'
      fetchData()
    } catch (error) {
      console.error('保存失败:', error)
    } finally {
      submitting.value = false
    }
  })
}

const resetFormData = () => {
  formData.patientName = ''
  formData.patientIdNo = ''
  formData.patientGender = ''
  formData.patientAge = ''
  formData.patientPhone = ''
  formData.deptId = null
  formData.clinicalDiagnosis = ''
  formData.specimenTypeId = null
  formData.specimenTypeName = ''
  formData.remark = ''
}

const handleResetForm = () => {
  formRef.value?.resetFields()
  resetFormData()
  selectedItemCodes.value = []
  itemSearchKey.value = ''
}

const handleBack = () => {
  viewMode.value = 'list'
}

onMounted(() => {
  fetchDepartments()
  fetchSpecimenTypes()
  fetchTestItems()
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

.apply-form {
  max-width: 1200px;
}

.item-selector {
  width: 100%;
  border: 1px solid var(--el-border-color);
  border-radius: 4px;
  padding: 16px;

  .search-box {
    margin-bottom: 16px;
  }

  .item-list {
    max-height: 300px;
    overflow-y: auto;
    border: 1px solid var(--el-border-color-light);
    border-radius: 4px;
    padding: 12px;
  }

  .item-checkbox {
    display: inline-flex;
    width: calc(33.33% - 8px);
    margin-right: 8px;
    margin-bottom: 8px;

    .item-name {
      font-weight: 500;
    }

    .item-code {
      color: var(--el-text-color-secondary);
      margin-left: 4px;
    }
  }
}

.selected-items {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
  align-items: center;

  .selected-tag {
    margin: 0;
  }

  .empty-tip {
    color: var(--el-text-color-placeholder);
    font-size: 14px;
  }
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
