<template>
  <div class="page-container">
    <el-card v-loading="loading">
      <template #header>
        <div class="card-header">
          <span>标本详情</span>
          <div class="header-actions">
            <el-button type="primary" @click="handleTrace">
              <el-icon><View /></el-icon>
              查看追溯
            </el-button>
            <el-button @click="handleBack">
              <el-icon><Back /></el-icon>
              返回
            </el-button>
          </div>
        </div>
      </template>

      <template v-if="specimenDetail">
        <el-descriptions title="基本信息" :column="3" border>
          <el-descriptions-item label="标本编号">
            <el-tag type="primary">{{ specimenDetail.specimenCode }}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="标本类型">{{ specimenDetail.specimenTypeName }}</el-descriptions-item>
          <el-descriptions-item label="状态">
            <el-tag :type="getStatusTagType(specimenDetail.status)">{{ specimenDetail.statusName }}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="创建时间">{{ specimenDetail.createTime }}</el-descriptions-item>
          <el-descriptions-item label="更新时间">{{ specimenDetail.updateTime }}</el-descriptions-item>
        </el-descriptions>

        <el-descriptions title="患者信息" :column="3" border class="mt-20">
          <el-descriptions-item label="患者姓名">{{ specimenDetail.patientName }}</el-descriptions-item>
          <el-descriptions-item label="性别">
            {{ specimenDetail.patientGender === 'male' ? '男' : '女' }}
          </el-descriptions-item>
          <el-descriptions-item label="年龄">{{ specimenDetail.patientAge }}岁</el-descriptions-item>
          <el-descriptions-item label="联系电话">{{ specimenDetail.patientPhone || '-' }}</el-descriptions-item>
          <el-descriptions-item label="身份证号">{{ specimenDetail.patientIdCard || '-' }}</el-descriptions-item>
          <el-descriptions-item label="科室">{{ specimenDetail.departmentName }}</el-descriptions-item>
          <el-descriptions-item label="床号">{{ specimenDetail.bedNo || '-' }}</el-descriptions-item>
          <el-descriptions-item label="诊断" :span="2">{{ specimenDetail.diagnosis || '-' }}</el-descriptions-item>
        </el-descriptions>

        <el-descriptions title="采集信息" :column="3" border class="mt-20">
          <el-descriptions-item label="采集时间">{{ specimenDetail.collectTime || '-' }}</el-descriptions-item>
          <el-descriptions-item label="采集人">{{ specimenDetail.collectUserName || '-' }}</el-descriptions-item>
          <el-descriptions-item label="接收时间">{{ specimenDetail.receiveTime || '-' }}</el-descriptions-item>
          <el-descriptions-item label="接收人">{{ specimenDetail.receiveUserName || '-' }}</el-descriptions-item>
          <el-descriptions-item label="备注" :span="2">{{ specimenDetail.remark || '-' }}</el-descriptions-item>
        </el-descriptions>

        <div class="section-title">检验项目</div>
        <el-table :data="specimenDetail.testItems" border stripe>
          <el-table-column prop="testItemCode" label="项目编码" width="120" />
          <el-table-column prop="testItemName" label="项目名称" min-width="150" />
          <el-table-column prop="price" label="价格" width="100">
            <template #default="{ row }">
              ¥{{ row.price.toFixed(2) }}
            </template>
          </el-table-column>
          <el-table-column prop="statusName" label="状态" width="100">
            <template #default="{ row }">
              <el-tag :type="getTestItemStatusType(row.status)">{{ row.statusName }}</el-tag>
            </template>
          </el-table-column>
        </el-table>

        <div class="section-title">流转记录</div>
        <el-timeline v-if="specimenDetail.flowRecords?.length > 0">
          <el-timeline-item
            v-for="record in specimenDetail.flowRecords"
            :key="record.id"
            :timestamp="record.flowTime"
            placement="top"
          >
            <el-card>
              <div class="flow-record">
                <div class="flow-type">
                  <el-tag>{{ record.flowTypeName }}</el-tag>
                </div>
                <div class="flow-info">
                  <p><strong>操作人：</strong>{{ record.operatorName }}</p>
                  <p v-if="record.fromLocation"><strong>来源：</strong>{{ record.fromLocation }}</p>
                  <p v-if="record.toLocation"><strong>去向：</strong>{{ record.toLocation }}</p>
                  <p v-if="record.remark"><strong>备注：</strong>{{ record.remark }}</p>
                </div>
              </div>
            </el-card>
          </el-timeline-item>
        </el-timeline>
        <el-empty v-else description="暂无流转记录" />

        <div class="action-bar">
          <el-button v-if="specimenDetail.status === 'pending_collect'" type="success" @click="handleCollect">
            <el-icon><Check /></el-icon>
            采集标本
          </el-button>
          <el-button v-if="specimenDetail.status === 'collected'" type="warning" @click="handleReceive">
            <el-icon><Download /></el-icon>
            接收标本
          </el-button>
          <el-button v-if="['pending_collect', 'collected'].includes(specimenDetail.status)" type="danger" @click="handleReject">
            <el-icon><Close /></el-icon>
            拒收标本
          </el-button>
          <el-button v-if="specimenDetail.status === 'received'" type="primary" @click="handleEntryResult">
            <el-icon><Edit /></el-icon>
            录入结果
          </el-button>
        </div>
      </template>
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
import { useRoute, useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { View, Back, Check, Download, Close, Edit } from '@element-plus/icons-vue'
import type { SpecimenDetail, SpecimenStatus } from '@/types/specimen'
import { getSpecimenDetail, collectSpecimen, receiveSpecimen, rejectSpecimen } from '@/api/specimen'

const route = useRoute()
const router = useRouter()

const loading = ref(false)
const specimenDetail = ref<SpecimenDetail | null>(null)

const rejectDialogVisible = ref(false)
const rejectForm = reactive({
  reason: '',
})

const getStatusTagType = (status: SpecimenStatus): 'success' | 'primary' | 'warning' | 'info' | 'danger' | undefined => {
  const typeMap: Record<SpecimenStatus, 'success' | 'primary' | 'warning' | 'info' | 'danger' | undefined> = {
    pending_collect: 'info',
    collected: 'warning',
    received: 'primary',
    testing: undefined,
    completed: 'success',
    rejected: 'danger',
  }
  return typeMap[status]
}

const getTestItemStatusType = (status: string): 'success' | 'primary' | 'warning' | 'info' | 'danger' | undefined => {
  const typeMap: Record<string, 'success' | 'primary' | 'warning' | 'info' | 'danger' | undefined> = {
    pending: 'info',
    testing: undefined,
    completed: 'success',
  }
  return typeMap[status]
}

const fetchDetail = async () => {
  const id = route.params.id as string
  if (!id) {
    ElMessage.error('标本ID不存在')
    return
  }
  
  loading.value = true
  try {
    specimenDetail.value = await getSpecimenDetail(id)
  } catch (error) {
    console.error('获取标本详情失败:', error)
  } finally {
    loading.value = false
  }
}

const handleTrace = () => {
  router.push(`/specimen/trace?specimenId=${specimenDetail.value?.id}`)
}

const handleBack = () => {
  router.back()
}

const handleCollect = async () => {
  try {
    await ElMessageBox.confirm('确认采集该标本？', '提示', { type: 'warning' })
    await collectSpecimen(specimenDetail.value!.id)
    ElMessage.success('采集成功')
    fetchDetail()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('采集失败:', error)
    }
  }
}

const handleReceive = async () => {
  try {
    await ElMessageBox.confirm('确认接收该标本？', '提示', { type: 'warning' })
    await receiveSpecimen(specimenDetail.value!.id)
    ElMessage.success('接收成功')
    fetchDetail()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('接收失败:', error)
    }
  }
}

const handleReject = () => {
  rejectForm.reason = ''
  rejectDialogVisible.value = true
}

const confirmReject = async () => {
  if (!rejectForm.reason.trim()) {
    ElMessage.warning('请输入拒收原因')
    return
  }
  try {
    await rejectSpecimen(specimenDetail.value!.id, rejectForm.reason)
    ElMessage.success('拒收成功')
    rejectDialogVisible.value = false
    fetchDetail()
  } catch (error) {
    console.error('拒收失败:', error)
  }
}

const handleEntryResult = () => {
  router.push(`/report/result?specimenId=${specimenDetail.value?.id}`)
}

onMounted(() => {
  fetchDetail()
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

.header-actions {
  display: flex;
  gap: 8px;
}

.mt-20 {
  margin-top: 20px;
}

.section-title {
  font-size: 16px;
  font-weight: 600;
  margin: 24px 0 16px;
  padding-left: 10px;
  border-left: 3px solid var(--el-color-primary);
}

.flow-record {
  .flow-type {
    margin-bottom: 8px;
  }

  .flow-info {
    p {
      margin: 4px 0;
      color: var(--el-text-color-regular);
    }
  }
}

.action-bar {
  margin-top: 24px;
  padding-top: 16px;
  border-top: 1px solid var(--el-border-color-light);
  display: flex;
  gap: 12px;
}
</style>
