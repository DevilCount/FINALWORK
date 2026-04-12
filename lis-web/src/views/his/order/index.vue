<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>模拟HIS检验申请</span>
        </div>
      </template>

      <el-steps :active="activeStep" finish-status="success" align-center class="steps-bar">
        <el-step title="患者信息" />
        <el-step title="就诊信息" />
        <el-step title="标本信息" />
        <el-step title="检验项目" />
        <el-step title="HL7预览" />
      </el-steps>

      <div class="step-content">
        <div v-show="activeStep === 0">
          <el-form :model="orderForm" label-width="100px" class="form-section">
            <el-row :gutter="20">
              <el-col :span="8">
                <el-form-item label="患者姓名" required>
                  <el-input v-model="orderForm.patientName" placeholder="请输入患者姓名" />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="性别" required>
                  <el-select v-model="orderForm.patientGender" placeholder="请选择性别" style="width: 100%">
                    <el-option label="男" value="male" />
                    <el-option label="女" value="female" />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="年龄" required>
                  <el-input v-model="orderForm.patientAge" placeholder="如：30岁" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="8">
                <el-form-item label="身份证号">
                  <el-input v-model="orderForm.patientIdNo" placeholder="请输入身份证号" />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="联系电话">
                  <el-input v-model="orderForm.patientPhone" placeholder="请输入联系电话" />
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </div>

        <div v-show="activeStep === 1">
          <el-form :model="orderForm" label-width="100px" class="form-section">
            <el-row :gutter="20">
              <el-col :span="8">
                <el-form-item label="就诊号" required>
                  <el-input v-model="orderForm.visitNo" placeholder="请输入就诊号" />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="就诊类型" required>
                  <el-select v-model="orderForm.visitType" placeholder="请选择就诊类型" style="width: 100%">
                    <el-option label="门诊" value="outpatient" />
                    <el-option label="住院" value="inpatient" />
                    <el-option label="急诊" value="emergency" />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="科室" required>
                  <el-select v-model="orderForm.deptId" placeholder="请选择科室" style="width: 100%" @change="handleDeptChange">
                    <el-option v-for="dept in deptList" :key="dept.id" :label="dept.deptName" :value="dept.id" />
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="8">
                <el-form-item label="床号">
                  <el-input v-model="orderForm.bedNo" placeholder="请输入床号" />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="送检医生">
                  <el-input v-model="orderForm.doctorName" placeholder="请输入送检医生" />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="临床诊断">
                  <el-input v-model="orderForm.clinicalDiagnosis" placeholder="请输入临床诊断" />
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </div>

        <div v-show="activeStep === 2">
          <el-form :model="orderForm" label-width="100px" class="form-section">
            <el-row :gutter="20">
              <el-col :span="8">
                <el-form-item label="标本类型" required>
                  <el-select v-model="orderForm.specimenTypeId" placeholder="请选择标本类型" style="width: 100%" @change="handleSpecimenTypeChange">
                    <el-option v-for="st in specimenTypes" :key="st.id" :label="st.typeName" :value="st.id" />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="是否急诊">
                  <el-radio-group v-model="orderForm.isStat">
                    <el-radio :value="1">是</el-radio>
                    <el-radio :value="0">否</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="16">
                <el-form-item label="备注">
                  <el-input v-model="orderForm.remark" type="textarea" :rows="2" placeholder="请输入备注" />
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </div>

        <div v-show="activeStep === 3">
          <div class="form-section">
            <el-transfer
              v-model="orderForm.testItemIds"
              :data="transferTestItems"
              :titles="['可选检验项目', '已选检验项目']"
              filterable
              filter-placeholder="搜索项目"
              style="width: 100%"
            />
          </div>
        </div>

        <div v-show="activeStep === 4">
          <div class="form-section">
            <el-form :model="orderForm" label-width="100px">
              <el-form-item label="接口配置">
                <el-select v-model="orderForm.interfaceCode" placeholder="请选择接口" style="width: 300px">
                  <el-option v-for="cfg in interfaceConfigs" :key="cfg.id" :label="cfg.interfaceName" :value="cfg.interfaceCode" />
                </el-select>
              </el-form-item>
            </el-form>
            <div class="hl7-preview">
              <div class="preview-title">HL7消息预览</div>
              <pre class="preview-content">{{ hl7Preview }}</pre>
            </div>
          </div>
        </div>
      </div>

      <div class="step-actions">
        <el-button v-if="activeStep > 0" @click="activeStep--">上一步</el-button>
        <el-button v-if="activeStep < 4" type="primary" @click="handleNextStep">下一步</el-button>
        <el-button v-if="activeStep === 4" type="success" :loading="submitting" @click="handleSubmit">提交申请</el-button>
      </div>

      <el-dialog v-model="resultDialogVisible" title="提交结果" width="600px">
        <el-result v-if="submitResult.success" icon="success" title="提交成功" :sub-title="submitResult.message">
          <template #extra>
            <el-button type="primary" @click="handleReset">继续申请</el-button>
          </template>
        </el-result>
        <el-result v-else icon="error" title="提交失败" :sub-title="submitResult.message">
          <template #extra>
            <el-button type="primary" @click="resultDialogVisible = false">返回修改</el-button>
          </template>
        </el-result>
      </el-dialog>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, computed, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import type { HisLabOrderForm } from '@/types/his'
import { submitLabOrder, getInterfaceConfigList } from '@/api/his'
import { getSpecimenTypes, getTestItems, getDepartments } from '@/api/specimen'
import type { SpecimenType } from '@/types/specimen'

const activeStep = ref(0)
const submitting = ref(false)
const resultDialogVisible = ref(false)

const orderForm = reactive<HisLabOrderForm>({
  patientName: '',
  patientGender: 'male',
  patientAge: '',
  patientPhone: '',
  patientIdNo: '',
  deptId: 0,
  deptName: '',
  bedNo: '',
  doctorName: '',
  visitNo: '',
  visitType: 'outpatient',
  specimenTypeId: 0,
  specimenTypeName: '',
  clinicalDiagnosis: '',
  testItemIds: [],
  isStat: 0,
  remark: '',
  interfaceCode: '',
})

const submitResult = reactive({
  success: false,
  message: '',
})

const specimenTypes = ref<SpecimenType[]>([])
const deptList = ref<any[]>([])
const allTestItems = ref<any[]>([])
const interfaceConfigs = ref<any[]>([])

const transferTestItems = computed(() => {
  return allTestItems.value.map(item => ({
    key: item.id,
    label: `${item.itemName} (${item.itemCode})`,
    disabled: false,
  }))
})

const hl7Preview = computed(() => {
  const lines: string[] = []
  lines.push('MSH|^~\\&|LIS|LIS_LAB|HIS|HIS_HOSP|' + new Date().toISOString().replace(/[-:T]/g, '').substring(0, 14) + '||ORM^O01|MSG00001|P|2.5.1')
  lines.push(`PID|1||${orderForm.patientIdNo || 'PID001'}||${orderForm.patientName}||${orderForm.patientAge}|${orderForm.patientGender === 'male' ? 'M' : 'F'}`)
  lines.push(`PV1|1|${orderForm.visitType === 'inpatient' ? 'I' : orderForm.visitType === 'emergency' ? 'E' : 'O'}|${orderForm.deptName}^${orderForm.bedNo}||||${orderForm.doctorName}`)
  const selectedItems = allTestItems.value.filter(item => orderForm.testItemIds.includes(item.id))
  selectedItems.forEach((item, index) => {
    lines.push(`ORC|NW|${item.itemCode}|||CM||^^^${new Date().toISOString().substring(0, 10)}|||||||${orderForm.doctorName}`)
    lines.push(`OBR|${index + 1}|${item.itemCode}||${item.itemName}^^${item.itemCode}|||${new Date().toISOString().substring(0, 10)}||||||||${orderForm.doctorName}`)
  })
  return lines.join('\n')
})

const handleDeptChange = (val: number) => {
  const dept = deptList.value.find(d => d.id === val)
  if (dept) {
    orderForm.deptName = dept.deptName
  }
}

const handleSpecimenTypeChange = (val: number) => {
  const st = specimenTypes.value.find(s => s.id === val)
  if (st) {
    orderForm.specimenTypeName = st.typeName
  }
}

const handleNextStep = () => {
  if (activeStep.value === 0) {
    if (!orderForm.patientName || !orderForm.patientGender || !orderForm.patientAge) {
      ElMessage.warning('请填写完整的患者信息')
      return
    }
  } else if (activeStep.value === 1) {
    if (!orderForm.visitNo || !orderForm.visitType || !orderForm.deptId) {
      ElMessage.warning('请填写完整的就诊信息')
      return
    }
  } else if (activeStep.value === 2) {
    if (!orderForm.specimenTypeId) {
      ElMessage.warning('请选择标本类型')
      return
    }
  } else if (activeStep.value === 3) {
    if (orderForm.testItemIds.length === 0) {
      ElMessage.warning('请至少选择一个检验项目')
      return
    }
  }
  activeStep.value++
}

const handleSubmit = async () => {
  if (!orderForm.interfaceCode) {
    ElMessage.warning('请选择接口配置')
    return
  }
  submitting.value = true
  try {
    const result = await submitLabOrder(orderForm)
    submitResult.success = true
    submitResult.message = result?.messageControlId ? `消息控制ID: ${result.messageControlId}` : '检验申请已成功提交'
    resultDialogVisible.value = true
  } catch (error: any) {
    submitResult.success = false
    submitResult.message = error?.message || '提交失败，请稍后重试'
    resultDialogVisible.value = true
  } finally {
    submitting.value = false
  }
}

const handleReset = () => {
  activeStep.value = 0
  orderForm.patientName = ''
  orderForm.patientGender = 'male'
  orderForm.patientAge = ''
  orderForm.patientPhone = ''
  orderForm.patientIdNo = ''
  orderForm.deptId = 0
  orderForm.deptName = ''
  orderForm.bedNo = ''
  orderForm.doctorName = ''
  orderForm.visitNo = ''
  orderForm.visitType = 'outpatient'
  orderForm.specimenTypeId = 0
  orderForm.specimenTypeName = ''
  orderForm.clinicalDiagnosis = ''
  orderForm.testItemIds = []
  orderForm.isStat = 0
  orderForm.remark = ''
  orderForm.interfaceCode = ''
  resultDialogVisible.value = false
}

onMounted(async () => {
  try {
    const [types, items, depts] = await Promise.all([
      getSpecimenTypes(),
      getTestItems(),
      getDepartments(),
    ])
    specimenTypes.value = types || []
    allTestItems.value = items || []
    deptList.value = depts || []
  } catch (error) {
    console.error('加载数据失败:', error)
  }

  try {
    const res = await getInterfaceConfigList()
    interfaceConfigs.value = res?.records ?? res?.list ?? res ?? []
  } catch (error) {
    console.error('加载接口配置失败:', error)
  }
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

.steps-bar {
  margin-bottom: 30px;
}

.step-content {
  min-height: 300px;
  padding: 20px 0;
}

.form-section {
  max-width: 1000px;
}

.step-actions {
  display: flex;
  justify-content: center;
  gap: 12px;
  margin-top: 20px;
  padding-top: 20px;
  border-top: 1px solid var(--el-border-color-light);
}

.hl7-preview {
  margin-top: 16px;
}

.preview-title {
  font-size: 14px;
  font-weight: 600;
  margin-bottom: 8px;
  color: var(--el-text-color-primary);
}

.preview-content {
  background-color: var(--el-fill-color-light);
  border: 1px solid var(--el-border-color);
  border-radius: 4px;
  padding: 16px;
  font-size: 13px;
  line-height: 1.6;
  overflow-x: auto;
  white-space: pre-wrap;
  word-break: break-all;
}
</style>
