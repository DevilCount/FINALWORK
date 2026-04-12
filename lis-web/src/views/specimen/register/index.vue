<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>标本登记</span>
        </div>
      </template>

      <el-form ref="formRef" :model="formData" :rules="rules" label-width="100px" class="register-form">
        <el-divider content-position="left">患者信息</el-divider>
        
        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="患者姓名" prop="patientName">
              <el-input v-model="formData.patientName" placeholder="请输入患者姓名" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="性别" prop="patientGender">
              <el-radio-group v-model="formData.patientGender">
                <el-radio value="男">男</el-radio>
                <el-radio value="女">女</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="年龄" prop="patientAge">
              <el-input v-model="formData.patientAge" placeholder="请输入年龄" style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="联系电话" prop="patientPhone">
              <el-input v-model="formData.patientPhone" placeholder="请输入联系电话" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="身份证号" prop="patientIdNo">
              <el-input v-model="formData.patientIdNo" placeholder="请输入身份证号" />
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

        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="床号">
              <el-input v-model="formData.bedNo" placeholder="请输入床号" />
            </el-form-item>
          </el-col>
          <el-col :span="16">
            <el-form-item label="诊断">
              <el-input v-model="formData.clinicalDiagnosis" placeholder="请输入诊断信息" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-divider content-position="left">标本信息</el-divider>

        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="标本类型" prop="specimenTypeId">
              <el-select v-model="formData.specimenTypeId" placeholder="请选择标本类型" style="width: 100%">
                <el-option v-for="item in specimenTypes" :key="item.id" :label="item.typeName" :value="item.id" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-divider content-position="left">检验项目</el-divider>

        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="检验项目" prop="testItemIds">
              <div class="test-item-selector">
                <div class="category-tabs">
                  <el-radio-group v-model="selectedCategory" @change="handleCategoryChange">
                    <el-radio-button value="">全部</el-radio-button>
                    <el-radio-button v-for="cat in testItemCategories" :key="cat" :value="cat">
                      {{ cat }}
                    </el-radio-button>
                  </el-radio-group>
                </div>
                <div class="search-box">
                  <el-input v-model="testItemSearch" placeholder="搜索检验项目" clearable @input="handleTestItemSearch">
                    <template #prefix>
                      <el-icon><Search /></el-icon>
                    </template>
                  </el-input>
                </div>
                <div class="test-item-list">
                  <el-checkbox-group v-model="formData.testItemIds">
                    <el-checkbox v-for="item in filteredTestItems" :key="item.id" :value="item.id" class="test-item-checkbox">
                      <span class="item-name">{{ item.itemName }}</span>
                      <span class="item-code">({{ item.itemCode }})</span>
                      <span class="item-price">¥{{ item.price }}</span>
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
                  v-for="id in formData.testItemIds"
                  :key="id"
                  closable
                  size="large"
                  class="selected-tag"
                  @close="removeTestItem(id)"
                >
                  {{ getTestItemName(id) }}
                </el-tag>
                <span v-if="formData.testItemIds.length > 0" class="total-price">
                  合计: ¥{{ totalPrice.toFixed(2) }}
                </span>
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
          <el-button type="primary" :loading="submitting" @click="handleSubmit">
            <el-icon><Check /></el-icon>
            提交登记
          </el-button>
          <el-button @click="handleReset">
            <el-icon><Refresh /></el-icon>
            重置
          </el-button>
          <el-button @click="handleCancel">返回</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { Search, Check, Refresh } from '@element-plus/icons-vue'
import type { FormInstance, FormRules } from 'element-plus'
import type { SpecimenRegisterForm, TestItem, SpecimenType } from '@/types/specimen'
import { registerSpecimen, getAllTestItems, getTestItemCategories, getSpecimenTypes, getDepartments } from '@/api/specimen'

const router = useRouter()

const formRef = ref<FormInstance>()
const submitting = ref(false)

const departments = ref<{ id: number; deptName: string }[]>([])
const specimenTypes = ref<SpecimenType[]>([])
const testItems = ref<TestItem[]>([])
const testItemCategories = ref<string[]>([])
const selectedCategory = ref('')
const testItemSearch = ref('')

const formData = reactive<SpecimenRegisterForm>({
  patientName: '',
  patientGender: '男',
  patientAge: '',
  patientPhone: '',
  patientIdNo: '',
  deptId: 0,
  bedNo: '',
  clinicalDiagnosis: '',
  specimenTypeId: 0,
  testItemIds: [] as number[],
  remark: '',
})

const rules: FormRules = {
  patientName: [{ required: true, message: '请输入患者姓名', trigger: 'blur' }],
  patientGender: [{ required: true, message: '请选择性别', trigger: 'change' }],
  patientAge: [{ required: true, message: '请输入年龄', trigger: 'blur' }],
  deptId: [{ required: true, message: '请选择科室', trigger: 'change' }],
  specimenTypeId: [{ required: true, message: '请选择标本类型', trigger: 'change' }],
  testItemIds: [{ required: true, message: '请选择检验项目', trigger: 'change', type: 'array', min: 1 }],
}

const filteredTestItems = computed(() => {
  let items = testItems.value
  if (selectedCategory.value) {
    items = items.filter(item => item.itemCode === selectedCategory.value)
  }
  if (testItemSearch.value) {
    const search = testItemSearch.value.toLowerCase()
    items = items.filter(item => 
      item.itemName.toLowerCase().includes(search) || 
      item.itemCode.toLowerCase().includes(search)
    )
  }
  return items
})

const totalPrice = computed(() => {
  return formData.testItemIds.reduce((sum, id) => {
    const item = testItems.value.find(t => t.id === id)
    return sum + (item?.price || 0)
  }, 0)
})

const getTestItemName = (id: number) => {
  const item = testItems.value.find(t => t.id === id)
  return item ? `${item.itemName}(${item.itemCode})` : String(id)
}

const removeTestItem = (id: number) => {
  const index = formData.testItemIds.indexOf(id)
  if (index > -1) {
    formData.testItemIds.splice(index, 1)
  }
}

const handleCategoryChange = () => {
}

const handleTestItemSearch = () => {
}

const fetchDepartments = async () => {
  try {
    departments.value = await getDepartments()
  } catch (error) {
    console.error('获取科室列表失败:', error)
  }
}

const fetchSpecimenTypes = async () => {
  try {
    specimenTypes.value = await getSpecimenTypes()
  } catch (error) {
    console.error('获取标本类型失败:', error)
  }
}

const fetchTestItems = async () => {
  try {
    testItems.value = await getAllTestItems()
  } catch (error) {
    console.error('获取检验项目失败:', error)
  }
}

const fetchTestItemCategories = async () => {
  try {
    testItemCategories.value = await getTestItemCategories()
  } catch (error) {
    console.error('获取检验项目分类失败:', error)
  }
}

const handleSubmit = async () => {
  if (!formRef.value) return
  
  await formRef.value.validate(async (valid) => {
    if (!valid) return
    
    submitting.value = true
    try {
      const result = await registerSpecimen(formData)
      ElMessage.success('标本登记成功')
      router.push(`/specimen/detail/${result.id}`)
    } catch (error) {
      console.error('标本登记失败:', error)
    } finally {
      submitting.value = false
    }
  })
}

const handleReset = () => {
  formRef.value?.resetFields()
  formData.testItemIds = []
  selectedCategory.value = ''
  testItemSearch.value = ''
}

const handleCancel = () => {
  router.back()
}

onMounted(() => {
  fetchDepartments()
  fetchSpecimenTypes()
  fetchTestItems()
  fetchTestItemCategories()
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

.register-form {
  max-width: 1200px;
}

.test-item-selector {
  width: 100%;
  border: 1px solid var(--el-border-color);
  border-radius: 4px;
  padding: 16px;

  .category-tabs {
    margin-bottom: 16px;
  }

  .search-box {
    margin-bottom: 16px;
  }

  .test-item-list {
    max-height: 300px;
    overflow-y: auto;
    border: 1px solid var(--el-border-color-light);
    border-radius: 4px;
    padding: 12px;
  }

  .test-item-checkbox {
    display: flex;
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

    .item-price {
      color: var(--el-color-danger);
      margin-left: auto;
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

  .total-price {
    margin-left: auto;
    font-size: 16px;
    font-weight: 600;
    color: var(--el-color-danger);
  }
}
</style>
