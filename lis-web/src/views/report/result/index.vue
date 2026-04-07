<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>结果录入</span>
        </div>
      </template>

      <el-form :model="searchForm" inline class="search-form">
        <el-form-item label="标本编号">
          <el-input v-model="searchForm.specimenCode" placeholder="请输入标本编号" clearable @keyup.enter="handleSearch" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">
            <el-icon><Search /></el-icon>
            查询
          </el-button>
        </el-form-item>
      </el-form>

      <template v-if="specimenInfo">
        <el-descriptions title="标本信息" :column="4" border>
          <el-descriptions-item label="标本编号">
            <el-tag type="primary">{{ specimenInfo.specimenCode }}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="标本类型">{{ specimenInfo.specimenTypeName }}</el-descriptions-item>
          <el-descriptions-item label="患者姓名">{{ specimenInfo.patientName }}</el-descriptions-item>
          <el-descriptions-item label="性别">{{ specimenInfo.patientGender === 'male' ? '男' : '女' }}</el-descriptions-item>
          <el-descriptions-item label="年龄">{{ specimenInfo.patientAge }}岁</el-descriptions-item>
          <el-descriptions-item label="科室">{{ specimenInfo.departmentName }}</el-descriptions-item>
          <el-descriptions-item label="床号">{{ specimenInfo.bedNo || '-' }}</el-descriptions-item>
          <el-descriptions-item label="诊断">{{ specimenInfo.diagnosis || '-' }}</el-descriptions-item>
        </el-descriptions>

        <div class="section-title">检验项目结果录入</div>
        
        <el-table :data="resultItems" border stripe>
          <el-table-column prop="testItemCode" label="项目编码" width="120" />
          <el-table-column prop="testItemName" label="项目名称" min-width="150" />
          <el-table-column label="结果值" width="150">
            <template #default="{ row }">
              <el-input v-model="row.resultValue" placeholder="请输入结果" @change="handleResultChange(row)" />
            </template>
          </el-table-column>
          <el-table-column prop="resultUnit" label="单位" width="80" />
          <el-table-column prop="referenceRange" label="参考范围" width="120" />
          <el-table-column label="结果标识" width="120">
            <template #default="{ row }">
              <el-select v-model="row.resultFlag" placeholder="标识" size="small">
                <el-option label="正常" value="normal" />
                <el-option label="偏高" value="high" />
                <el-option label="偏低" value="low" />
                <el-option label="危急高" value="critical_high" />
                <el-option label="危急低" value="critical_low" />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="异常标记" width="80">
            <template #default="{ row }">
              <el-checkbox v-model="row.isAbnormal" />
            </template>
          </el-table-column>
          <el-table-column label="危急值" width="80">
            <template #default="{ row }">
              <el-checkbox v-model="row.isCritical" @change="handleCriticalChange(row)" />
            </template>
          </el-table-column>
          <el-table-column label="备注" min-width="150">
            <template #default="{ row }">
              <el-input v-model="row.remark" placeholder="备注" size="small" />
            </template>
          </el-table-column>
        </el-table>

        <div class="action-bar">
          <el-button type="primary" :loading="saving" @click="handleSave">
            <el-icon><Check /></el-icon>
            保存结果
          </el-button>
          <el-button type="success" :loading="submitting" @click="handleSubmit">
            <el-icon><Upload /></el-icon>
            提交审核
          </el-button>
          <el-button @click="handleReset">
            <el-icon><Refresh /></el-icon>
            重置
          </el-button>
        </div>
      </template>

      <el-empty v-else description="请输入标本编号查询" />
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Search, Check, Upload, Refresh } from '@element-plus/icons-vue'
import type { Specimen, SpecimenTestItem } from '@/types/specimen'
import type { ResultEntryItem } from '@/types/report'
import { getSpecimenByCode, getSpecimenDetail } from '@/api/specimen'
import { saveResultEntry, submitReport, getReportBySpecimenId } from '@/api/report'

const route = useRoute()
const router = useRouter()

const searchForm = reactive({
  specimenCode: '',
})

const specimenInfo = ref<Specimen | null>(null)
const resultItems = ref<(SpecimenTestItem & ResultEntryItem)[]>([])
const saving = ref(false)
const submitting = ref(false)

const handleSearch = async () => {
  if (!searchForm.specimenCode) {
    ElMessage.warning('请输入标本编号')
    return
  }

  try {
    specimenInfo.value = await getSpecimenByCode(searchForm.specimenCode)
    
    if (!['received', 'testing'].includes(specimenInfo.value.status)) {
      ElMessage.warning('该标本状态不允许录入结果')
      return
    }

    resultItems.value = specimenInfo.value.testItems.map(item => ({
      ...item,
      resultValue: '',
      resultFlag: 'normal' as const,
      isAbnormal: false,
      isCritical: false,
      remark: '',
    }))

    try {
      const report = await getReportBySpecimenId(specimenInfo.value.id)
      if (report && report.results) {
        report.results.forEach(r => {
          const item = resultItems.value.find(i => i.testItemId === r.testItemId)
          if (item) {
            item.resultValue = r.resultValue
            item.resultFlag = r.resultFlag
            item.isAbnormal = r.isAbnormal
            item.isCritical = r.isCritical
            item.remark = r.remark
          }
        })
      }
    } catch {
      // 报告不存在，使用默认值
    }
  } catch (error) {
    console.error('查询标本失败:', error)
    specimenInfo.value = null
    resultItems.value = []
  }
}

const handleResultChange = (row: SpecimenTestItem & ResultEntryItem) => {
  const value = parseFloat(row.resultValue)
  if (isNaN(value)) return

  const range = row.referenceRange || ''
  const match = range.match(/(\d+\.?\d*)\s*[-~]\s*(\d+\.?\d*)/)
  if (match) {
    const min = parseFloat(match[1])
    const max = parseFloat(match[2])
    row.isAbnormal = value < min || value > max
    row.resultFlag = value < min ? 'low' : value > max ? 'high' : 'normal'
  }
}

const handleCriticalChange = (row: SpecimenTestItem & ResultEntryItem) => {
  if (row.isCritical) {
    row.isAbnormal = true
    if (row.resultFlag === 'high') {
      row.resultFlag = 'critical_high'
    } else if (row.resultFlag === 'low') {
      row.resultFlag = 'critical_low'
    }
  }
}

const validateResults = () => {
  const emptyItems = resultItems.value.filter(item => !item.resultValue)
  if (emptyItems.length > 0) {
    ElMessage.warning(`请录入所有检验项目的结果，还有 ${emptyItems.length} 项未录入`)
    return false
  }
  return true
}

const handleSave = async () => {
  if (!specimenInfo.value) return

  saving.value = true
  try {
    await saveResultEntry({
      specimenId: specimenInfo.value.id,
      results: resultItems.value.map(item => ({
        testItemId: item.testItemId,
        resultValue: item.resultValue,
        resultFlag: item.resultFlag,
        isAbnormal: item.isAbnormal,
        isCritical: item.isCritical,
        remark: item.remark,
      })),
    })
    ElMessage.success('保存成功')
  } catch (error) {
    console.error('保存失败:', error)
  } finally {
    saving.value = false
  }
}

const handleSubmit = async () => {
  if (!specimenInfo.value) return
  if (!validateResults()) return

  try {
    await ElMessageBox.confirm('确认提交审核？提交后将无法修改结果。', '提示', { type: 'warning' })
    
    submitting.value = true
    
    await saveResultEntry({
      specimenId: specimenInfo.value.id,
      results: resultItems.value.map(item => ({
        testItemId: item.testItemId,
        resultValue: item.resultValue,
        resultFlag: item.resultFlag,
        isAbnormal: item.isAbnormal,
        isCritical: item.isCritical,
        remark: item.remark,
      })),
    })

    const report = await getReportBySpecimenId(specimenInfo.value.id)
    await submitReport(report.id)
    
    ElMessage.success('提交成功')
    router.push('/report/query')
  } catch (error) {
    if (error !== 'cancel') {
      console.error('提交失败:', error)
    }
  } finally {
    submitting.value = false
  }
}

const handleReset = () => {
  resultItems.value.forEach(item => {
    item.resultValue = ''
    item.resultFlag = 'normal'
    item.isAbnormal = false
    item.isCritical = false
    item.remark = ''
  })
}

onMounted(() => {
  const specimenId = route.query.specimenId as string
  if (specimenId) {
    getSpecimenDetail(specimenId).then(specimen => {
      searchForm.specimenCode = specimen.specimenCode
      handleSearch()
    }).catch(error => {
      console.error('获取标本信息失败:', error)
    })
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

.search-form {
  margin-bottom: 16px;
}

.section-title {
  font-size: 16px;
  font-weight: 600;
  margin: 20px 0 16px;
  padding-left: 10px;
  border-left: 3px solid var(--el-color-primary);
}

.action-bar {
  margin-top: 24px;
  padding-top: 16px;
  border-top: 1px solid var(--el-border-color-light);
  display: flex;
  gap: 12px;
}
</style>
