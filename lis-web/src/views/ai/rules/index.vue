<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>诊断规则配置</span>
          <el-button type="primary" @click="handleAdd">
            <el-icon><Plus /></el-icon>
            新增规则
          </el-button>
        </div>
      </template>

      <el-form :model="queryParams" inline class="search-form">
        <el-form-item label="关键词">
          <el-input
            v-model="queryParams.keyword"
            placeholder="规则名称/编码"
            clearable
            @keyup.enter="handleQuery"
          />
        </el-form-item>
        <el-form-item label="规则分类">
          <el-select v-model="queryParams.category" placeholder="请选择分类" clearable>
            <el-option v-for="cat in categories" :key="cat.code" :label="cat.name" :value="cat.code" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="queryParams.isActive" placeholder="请选择状态" clearable>
            <el-option label="启用" :value="true" />
            <el-option label="禁用" :value="false" />
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

      <el-table v-loading="loading" :data="ruleList" border stripe>
        <el-table-column prop="code" label="规则编码" width="120" />
        <el-table-column prop="name" label="规则名称" min-width="150" />
        <el-table-column prop="category" label="分类" width="120" />
        <el-table-column prop="priority" label="优先级" width="80" />
        <el-table-column label="状态" width="80">
          <template #default="{ row }">
            <el-switch
              v-model="row.isActive"
              @change="handleToggleStatus(row)"
            />
          </template>
        </el-table-column>
        <el-table-column prop="description" label="描述" min-width="200" show-overflow-tooltip />
        <el-table-column prop="updatedAt" label="更新时间" width="160" />
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" link @click="handleEdit(row)">编辑</el-button>
            <el-button type="primary" link @click="handleTest(row)">测试</el-button>
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

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="800px" destroy-on-close>
      <el-form ref="formRef" :model="formData" :rules="formRules" label-width="100px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="规则编码" prop="code">
              <el-input v-model="formData.code" placeholder="请输入规则编码" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="规则名称" prop="name">
              <el-input v-model="formData.name" placeholder="请输入规则名称" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="规则分类" prop="category">
              <el-select v-model="formData.category" placeholder="请选择分类" style="width: 100%">
                <el-option v-for="cat in categories" :key="cat.code" :label="cat.name" :value="cat.code" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="优先级" prop="priority">
              <el-input-number v-model="formData.priority" :min="1" :max="100" style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="规则描述" prop="description">
          <el-input v-model="formData.description" type="textarea" :rows="2" placeholder="请输入规则描述" />
        </el-form-item>

        <el-divider content-position="left">触发条件</el-divider>
        <div v-for="(condition, index) in formData.conditions" :key="index" class="condition-item">
          <el-row :gutter="10">
            <el-col :span="6">
              <el-select v-model="condition.field" placeholder="选择字段">
                <el-option label="白细胞计数" value="wbc" />
                <el-option label="红细胞计数" value="rbc" />
                <el-option label="血红蛋白" value="hgb" />
                <el-option label="血小板计数" value="plt" />
                <el-option label="血糖" value="glu" />
                <el-option label="肌酐" value="cr" />
                <el-option label="尿素氮" value="bun" />
              </el-select>
            </el-col>
            <el-col :span="4">
              <el-select v-model="condition.operator" placeholder="运算符">
                <el-option label="等于" value="eq" />
                <el-option label="不等于" value="ne" />
                <el-option label="大于" value="gt" />
                <el-option label="大于等于" value="gte" />
                <el-option label="小于" value="lt" />
                <el-option label="小于等于" value="lte" />
                <el-option label="包含" value="contains" />
                <el-option label="区间" value="between" />
              </el-select>
            </el-col>
            <el-col :span="8">
              <el-input v-if="condition.operator !== 'between'" v-model="condition.value" placeholder="输入值" />
              <el-row v-else :gutter="5">
                <el-col :span="12">
                  <el-input v-model="(condition.value as [number, number])[0]" placeholder="最小值" />
                </el-col>
                <el-col :span="12">
                  <el-input v-model="(condition.value as [number, number])[1]" placeholder="最大值" />
                </el-col>
              </el-row>
            </el-col>
            <el-col :span="4">
              <el-select v-if="index < formData.conditions!.length - 1" v-model="condition.logic" placeholder="逻辑">
                <el-option label="且" value="and" />
                <el-option label="或" value="or" />
              </el-select>
            </el-col>
            <el-col :span="2">
              <el-button type="danger" link @click="removeCondition(index)">
                <el-icon><Delete /></el-icon>
              </el-button>
            </el-col>
          </el-row>
        </div>
        <el-button type="primary" link @click="addCondition">
          <el-icon><Plus /></el-icon>
          添加条件
        </el-button>

        <el-divider content-position="left">执行动作</el-divider>
        <div v-for="(action, index) in formData.actions" :key="index" class="action-item">
          <el-row :gutter="10">
            <el-col :span="6">
              <el-select v-model="action.type" placeholder="动作类型">
                <el-option label="告警" value="alert" />
                <el-option label="建议" value="suggest" />
                <el-option label="标记" value="flag" />
                <el-option label="通知" value="notify" />
              </el-select>
            </el-col>
            <el-col :span="6">
              <el-select v-model="action.severity" placeholder="严重程度">
                <el-option label="信息" value="info" />
                <el-option label="警告" value="warning" />
                <el-option label="错误" value="error" />
                <el-option label="危急" value="critical" />
              </el-select>
            </el-col>
            <el-col :span="10">
              <el-input v-model="action.content" placeholder="动作内容" />
            </el-col>
            <el-col :span="2">
              <el-button type="danger" link @click="removeAction(index)">
                <el-icon><Delete /></el-icon>
              </el-button>
            </el-col>
          </el-row>
        </div>
        <el-button type="primary" link @click="addAction">
          <el-icon><Plus /></el-icon>
          添加动作
        </el-button>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" :loading="submitLoading" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="testDialogVisible" title="规则测试" width="600px">
      <el-form label-width="100px">
        <el-form-item label="测试数据">
          <el-input v-model="testDataJson" type="textarea" :rows="10" placeholder="请输入JSON格式测试数据" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="testDialogVisible = false">取消</el-button>
        <el-button type="primary" :loading="testLoading" @click="handleRunTest">执行测试</el-button>
      </template>
      <el-divider v-if="testResult" />
      <div v-if="testResult" class="test-result">
        <el-alert :title="testResult.matched ? '规则匹配成功' : '规则未匹配'" :type="testResult.matched ? 'success' : 'info'" show-icon />
        <div v-if="testResult.matched && testResult.result" style="margin-top: 12px">
          <el-descriptions :column="1" border>
            <el-descriptions-item label="诊断摘要">{{ testResult.result.summary }}</el-descriptions-item>
          </el-descriptions>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, computed, onMounted } from 'vue'
import { ElMessage, ElMessageBox, type FormInstance, type FormRules } from 'element-plus'
import { Plus, Search, Refresh, Delete } from '@element-plus/icons-vue'
import type { AIRule, AIRuleQuery, RuleCondition, RuleAction } from '@/api/ai'
import { getAIRules, getAIRuleDetail, createAIRule, updateAIRule, deleteAIRule, toggleAIRule, testAIRule, getAIRuleCategories } from '@/api/ai'

const loading = ref(false)
const submitLoading = ref(false)
const testLoading = ref(false)
const ruleList = ref<AIRule[]>([])
const total = ref(0)
const categories = ref<{ code: string; name: string }[]>([])

const queryParams = reactive<AIRuleQuery>({
  page: 1,
  pageSize: 10,
  keyword: '',
  category: '',
  isActive: undefined
})

const dialogVisible = ref(false)
const dialogTitle = computed(() => (formData.id ? '编辑规则' : '新增规则'))
const formRef = ref<FormInstance>()
const formData = reactive<Partial<AIRule>>({
  code: '',
  name: '',
  category: '',
  description: '',
  priority: 50,
  isActive: true,
  conditions: [],
  actions: []
})

const formRules: FormRules = {
  code: [{ required: true, message: '请输入规则编码', trigger: 'blur' }],
  name: [{ required: true, message: '请输入规则名称', trigger: 'blur' }],
  category: [{ required: true, message: '请选择规则分类', trigger: 'change' }],
  priority: [{ required: true, message: '请输入优先级', trigger: 'blur' }]
}

const testDialogVisible = ref(false)
const testDataJson = ref('')
const testResult = ref<{ matched: boolean; result?: { summary: string } } | null>(null)
const currentTestRule = ref<AIRule | null>(null)

const fetchCategories = async () => {
  try {
    categories.value = await getAIRuleCategories()
  } catch {
    console.error('获取分类失败')
  }
}

const fetchRuleList = async () => {
  loading.value = true
  try {
    const res = await getAIRules(queryParams)
    ruleList.value = res.list
    total.value = res.total
  } catch {
    ElMessage.error('获取规则列表失败')
  } finally {
    loading.value = false
  }
}

const handleQuery = () => {
  queryParams.page = 1
  fetchRuleList()
}

const handleReset = () => {
  queryParams.keyword = ''
  queryParams.category = ''
  queryParams.isActive = undefined
  handleQuery()
}

const resetForm = () => {
  Object.assign(formData, {
    id: undefined,
    code: '',
    name: '',
    category: '',
    description: '',
    priority: 50,
    isActive: true,
    conditions: [],
    actions: []
  })
}

const handleAdd = () => {
  resetForm()
  dialogVisible.value = true
}

const handleEdit = async (row: AIRule) => {
  try {
    const detail = await getAIRuleDetail(row.id)
    Object.assign(formData, detail)
    dialogVisible.value = true
  } catch {
    ElMessage.error('获取规则详情失败')
  }
}

const handleDelete = async (row: AIRule) => {
  try {
    await ElMessageBox.confirm('确定要删除该规则吗？', '提示', { type: 'warning' })
    await deleteAIRule(row.id)
    ElMessage.success('删除成功')
    fetchRuleList()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败')
    }
  }
}

const handleToggleStatus = async (row: AIRule) => {
  try {
    await toggleAIRule(row.id, row.isActive)
    ElMessage.success(row.isActive ? '已启用' : '已禁用')
  } catch {
    row.isActive = !row.isActive
    ElMessage.error('操作失败')
  }
}

const handleTest = (row: AIRule) => {
  currentTestRule.value = row
  testDataJson.value = JSON.stringify({
    wbc: 12.5,
    rbc: 4.5,
    hgb: 140,
    plt: 250,
    glu: 6.5
  }, null, 2)
  testResult.value = null
  testDialogVisible.value = true
}

const handleRunTest = async () => {
  if (!currentTestRule.value) return

  testLoading.value = true
  try {
    const testData = JSON.parse(testDataJson.value)
    testResult.value = await testAIRule(currentTestRule.value.id, testData)
  } catch {
    ElMessage.error('测试数据格式错误或测试失败')
  } finally {
    testLoading.value = false
  }
}

const addCondition = () => {
  if (!formData.conditions) {
    formData.conditions = []
  }
  formData.conditions.push({
    field: '',
    operator: 'gt',
    value: '',
    logic: 'and'
  })
}

const removeCondition = (index: number) => {
  formData.conditions?.splice(index, 1)
}

const addAction = () => {
  if (!formData.actions) {
    formData.actions = []
  }
  formData.actions.push({
    type: 'alert',
    content: '',
    severity: 'warning'
  })
}

const removeAction = (index: number) => {
  formData.actions?.splice(index, 1)
}

const handleSubmit = async () => {
  const valid = await formRef.value?.validate()
  if (!valid) return

  submitLoading.value = true
  try {
    if (formData.id) {
      await updateAIRule(formData.id, formData)
      ElMessage.success('更新成功')
    } else {
      await createAIRule(formData)
      ElMessage.success('创建成功')
    }
    dialogVisible.value = false
    fetchRuleList()
  } catch {
    ElMessage.error('操作失败')
  } finally {
    submitLoading.value = false
  }
}

onMounted(() => {
  fetchCategories()
  fetchRuleList()
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

.condition-item,
.action-item {
  margin-bottom: 12px;
  padding: 12px;
  background: #f5f7fa;
  border-radius: 4px;
}

.test-result {
  padding: 12px;
  background: #f5f7fa;
  border-radius: 4px;
}
</style>
