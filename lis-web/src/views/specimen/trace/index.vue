<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>标本追溯</span>
        </div>
      </template>

      <el-form :model="queryParams" inline class="search-form">
        <el-form-item label="标本编号">
          <el-input v-model="queryParams.specimenNo" placeholder="请输入标本编号" clearable @keyup.enter="handleSearch" />
        </el-form-item>
        <el-form-item label="患者姓名">
          <el-input v-model="queryParams.patientName" placeholder="请输入患者姓名" clearable @keyup.enter="handleSearch" />
        </el-form-item>
        <el-form-item label="操作类型">
          <el-select v-model="queryParams.action" placeholder="请选择操作类型" clearable style="width: 150px">
            <el-option v-for="item in actionTypes" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="操作时间">
          <el-date-picker
            v-model="operateTimeRange"
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

      <div v-if="traceRecords.length > 0" class="trace-content">
        <div class="specimen-info" v-if="specimenInfo">
          <el-descriptions :column="4" border size="small">
            <el-descriptions-item label="标本编号">
              <el-tag type="primary">{{ specimenInfo.specimenNo }}</el-tag>
            </el-descriptions-item>
            <el-descriptions-item label="患者姓名">{{ specimenInfo.patientName }}</el-descriptions-item>
            <el-descriptions-item label="科室">{{ specimenInfo.deptName }}</el-descriptions-item>
            <el-descriptions-item label="当前状态">
              <el-tag :type="getStatusTagType(specimenInfo.status)">{{ specimenInfo.statusName }}</el-tag>
            </el-descriptions-item>
          </el-descriptions>
        </div>

        <div class="timeline-container">
          <el-timeline>
            <el-timeline-item
              v-for="record in traceRecords"
              :key="record.id"
              :timestamp="record.operateTime"
              placement="top"
              :type="getTimelineType(record.action)"
              :hollow="false"
              size="large"
            >
              <el-card class="timeline-card">
                <div class="record-header">
                  <el-tag :type="getActionTagType(record.action)" size="large">
                    {{ record.actionName }}
                  </el-tag>
                  <span class="operator">操作人: {{ record.operatorName }}</span>
                </div>
                <div class="record-content">
                  <div class="status-change" v-if="record.fromStatus && record.toStatus">
                    <span class="from-status">{{ record.fromStatusName }}</span>
                    <el-icon class="arrow"><ArrowRight /></el-icon>
                    <span class="to-status">{{ record.toStatusName }}</span>
                  </div>
                  <div class="operation-desc">{{ record.operationDesc }}</div>
                  <div class="remark" v-if="record.remark">
                    <el-icon><InfoFilled /></el-icon>
                    {{ record.remark }}
                  </div>
                </div>
              </el-card>
            </el-timeline-item>
          </el-timeline>
        </div>
      </div>

      <el-empty v-else description="暂无追溯记录，请输入标本编号查询" />
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import { Search, Refresh, ArrowRight, InfoFilled } from '@element-plus/icons-vue'
import type { SpecimenTraceVO, SpecimenTraceQueryParams, Specimen, SpecimenStatus } from '@/types/specimen'
import { getSpecimenTraceRecords, getSpecimenTraceBySpecimenId, getSpecimenDetail, getSpecimenBySpecimenNo } from '@/api/specimen'

const route = useRoute()

const operateTimeRange = ref<[string, string] | null>(null)

const queryParams = reactive<SpecimenTraceQueryParams>({
  specimenNo: '',
  patientName: '',
  action: '',
  operateTimeStart: '',
  operateTimeEnd: '',
})

const actionTypes = [
  { label: '登记', value: 'register' },
  { label: '采集', value: 'collect' },
  { label: '接收', value: 'receive' },
  { label: '拒收', value: 'reject' },
  { label: '入库', value: 'storage' },
  { label: '送检', value: 'submit' },
  { label: '检验', value: 'test' },
  { label: '完成', value: 'complete' },
  { label: '取消', value: 'cancel' },
]

const traceRecords = ref<SpecimenTraceVO[]>([])
const specimenInfo = ref<Specimen | null>(null)

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

const getTimelineType = (action: string): 'primary' | 'success' | 'warning' | 'danger' | 'info' => {
  const typeMap: Record<string, 'primary' | 'success' | 'warning' | 'danger' | 'info'> = {
    register: 'primary',
    collect: 'success',
    receive: 'primary',
    reject: 'danger',
    storage: 'warning',
    submit: 'warning',
    test: 'info',
    complete: 'success',
    cancel: 'info',
  }
  return typeMap[action] || 'info'
}

const getActionTagType = (action: string): 'success' | 'primary' | 'warning' | 'info' | 'danger' | undefined => {
  const typeMap: Record<string, 'success' | 'primary' | 'warning' | 'info' | 'danger' | undefined> = {
    register: undefined,
    collect: 'success',
    receive: 'primary',
    reject: 'danger',
    storage: 'warning',
    submit: 'warning',
    test: 'info',
    complete: 'success',
    cancel: 'info',
  }
  return typeMap[action]
}

const fetchTraceRecords = async () => {
  if (!queryParams.specimenNo && !queryParams.patientName) {
    ElMessage.warning('请输入标本编号或患者姓名进行查询')
    return
  }

  try {
    if (operateTimeRange.value) {
      queryParams.operateTimeStart = operateTimeRange.value[0]
      queryParams.operateTimeEnd = operateTimeRange.value[1]
    } else {
      queryParams.operateTimeStart = ''
      queryParams.operateTimeEnd = ''
    }

    // 先通过 specimenNo 查询标本获取 specimenId，再调用追溯接口
    if (queryParams.specimenNo) {
      try {
        const specimen = await getSpecimenBySpecimenNo(queryParams.specimenNo)
        specimenInfo.value = specimen
        traceRecords.value = await getSpecimenTraceBySpecimenId(specimen.id)
      } catch {
        // 标本不存在，尝试直接用 specimenNo 查追溯
        traceRecords.value = await getSpecimenTraceRecords(queryParams.specimenNo)
        if (traceRecords.value.length > 0) {
          const specimenId = traceRecords.value[0].specimenId
          specimenInfo.value = await getSpecimenDetail(specimenId)
        } else {
          specimenInfo.value = null
        }
      }
    } else {
      traceRecords.value = []
      specimenInfo.value = null
    }
  } catch (error) {
    console.error('获取追溯记录失败:', error)
  }
}

const fetchTraceBySpecimenId = async (specimenId: string) => {
  try {
    traceRecords.value = await getSpecimenTraceBySpecimenId(specimenId)
    specimenInfo.value = await getSpecimenDetail(specimenId)
    if (specimenInfo.value) {
      queryParams.specimenNo = specimenInfo.value.specimenNo
    }
  } catch (error) {
    console.error('获取追溯记录失败:', error)
  }
}

const handleSearch = () => {
  fetchTraceRecords()
}

const handleReset = () => {
  queryParams.specimenNo = ''
  queryParams.patientName = ''
  queryParams.action = ''
  operateTimeRange.value = null
  queryParams.operateTimeStart = ''
  queryParams.operateTimeEnd = ''
  traceRecords.value = []
  specimenInfo.value = null
}

onMounted(() => {
  const specimenId = route.query.specimenId as string
  if (specimenId) {
    fetchTraceBySpecimenId(specimenId)
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

.trace-content {
  margin-top: 20px;
}

.specimen-info {
  margin-bottom: 24px;
}

.timeline-container {
  padding: 20px 0;
}

.timeline-card {
  .record-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 12px;

    .operator {
      color: var(--el-text-color-secondary);
      font-size: 14px;
    }
  }

  .record-content {
    .status-change {
      display: flex;
      align-items: center;
      gap: 8px;
      margin-bottom: 8px;
      font-size: 14px;

      .from-status {
        color: var(--el-text-color-secondary);
      }

      .arrow {
        color: var(--el-color-primary);
      }

      .to-status {
        color: var(--el-color-primary);
        font-weight: 600;
      }
    }

    .operation-desc {
      color: var(--el-text-color-regular);
      margin-bottom: 8px;
    }

    .remark {
      display: flex;
      align-items: center;
      gap: 4px;
      padding: 8px 12px;
      background-color: var(--el-fill-color-light);
      border-radius: 4px;
      font-size: 13px;
      color: var(--el-text-color-secondary);
    }
  }
}
</style>
