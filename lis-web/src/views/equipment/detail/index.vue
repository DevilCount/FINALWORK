<template>
  <div class="page-container">
    <el-page-header @back="goBack">
      <template #content>
        <span class="text-large font-600 mr-3">设备详情</span>
      </template>
    </el-page-header>

    <el-card v-loading="loading" class="info-card">
      <template #header>
        <div class="card-header">
          <span>基本信息</span>
          <el-button type="primary" @click="handleEdit">编辑</el-button>
        </div>
      </template>
      <el-descriptions :column="3" border>
        <el-descriptions-item label="设备编号">{{ equipment?.equipmentCode }}</el-descriptions-item>
        <el-descriptions-item label="设备名称">{{ equipment?.equipmentName }}</el-descriptions-item>
        <el-descriptions-item label="型号">{{ equipment?.model }}</el-descriptions-item>
        <el-descriptions-item label="厂商">{{ equipment?.manufacturer }}</el-descriptions-item>
        <el-descriptions-item label="序列号">{{ equipment?.serialNo }}</el-descriptions-item>
        <el-descriptions-item label="状态">
          <el-tag :type="getStatusType(equipment?.status)">
            {{ getStatusLabel(equipment?.status) }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="所属科室">{{ equipment?.labName }}</el-descriptions-item>
        <el-descriptions-item label="位置">{{ equipment?.location }}</el-descriptions-item>
        <el-descriptions-item label="负责人">{{ equipment?.responsibleUserName }}</el-descriptions-item>
        <el-descriptions-item label="购买日期">{{ equipment?.purchaseDate || '-' }}</el-descriptions-item>
        <el-descriptions-item label="保修到期">{{ equipment?.warrantyExpireDate || '-' }}</el-descriptions-item>
        <el-descriptions-item label="创建时间">{{ equipment?.createTime }}</el-descriptions-item>
      </el-descriptions>
    </el-card>

    <el-card class="status-card">
      <template #header>
        <span>设备状态监控</span>
      </template>
      <el-row :gutter="20">
        <el-col :span="6">
          <div class="monitor-item">
            <div class="monitor-label">在线状态</div>
            <div class="monitor-value">{{ equipmentStatus?.isOnline ? '在线' : '离线' }}</div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="monitor-item">
            <div class="monitor-label">今日检测数</div>
            <div class="monitor-value">{{ equipmentStatus?.todayTestCount || 0 }}</div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="monitor-item">
            <div class="monitor-label">累计检测数</div>
            <div class="monitor-value">{{ equipmentStatus?.totalTestCount || 0 }}</div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="monitor-item">
            <div class="monitor-label">错误次数</div>
            <div class="monitor-value error">{{ equipmentStatus?.errorCount || 0 }}</div>
          </div>
        </el-col>
      </el-row>
      <el-row :gutter="20" style="margin-top: 20px">
        <el-col :span="12">
          <div class="monitor-item">
            <div class="monitor-label">最后通讯时间</div>
            <div class="monitor-value">{{ equipmentStatus?.lastCommTime || '-' }}</div>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="monitor-item">
            <div class="monitor-label">状态更新时间</div>
            <div class="monitor-value">{{ equipmentStatus?.updateTime || '-' }}</div>
          </div>
        </el-col>
      </el-row>
    </el-card>

    <el-card class="calibration-card">
      <template #header>
        <div class="card-header">
          <span>校准维护信息</span>
        </div>
      </template>
      <el-row :gutter="20">
        <el-col :span="6">
          <div class="calibration-item">
            <div class="calibration-label">上次维护日期</div>
            <div class="calibration-value">{{ equipment?.lastMaintenanceDate || '-' }}</div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="calibration-item">
            <div class="calibration-label">下次维护日期</div>
            <div class="calibration-value" :class="{ warning: isNearExpiry(equipment?.nextMaintenanceDate) }">
              {{ equipment?.nextMaintenanceDate || '-' }}
            </div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="calibration-item">
            <div class="calibration-label">上次校准日期</div>
            <div class="calibration-value">{{ equipment?.lastCalibrationDate || '-' }}</div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="calibration-item">
            <div class="calibration-label">下次校准日期</div>
            <div class="calibration-value" :class="{ warning: isNearExpiry(equipment?.nextCalibrationDate) }">
              {{ equipment?.nextCalibrationDate || '-' }}
            </div>
          </div>
        </el-col>
      </el-row>
    </el-card>

    <el-card class="records-card">
      <template #header>
        <div class="card-header">
          <span>维护记录</span>
          <el-button type="primary" size="small" @click="handleAddMaintenance">新增记录</el-button>
        </div>
      </template>
      <el-table v-loading="recordsLoading" :data="maintenanceRecords" border stripe>
        <el-table-column label="维护类型" width="100">
          <template #default="{ row }">
            <el-tag :type="getMaintenanceType(row.maintenanceType)">
              {{ getMaintenanceLabel(row.maintenanceType) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="content" label="维护内容" min-width="200" show-overflow-tooltip />
        <el-table-column prop="technician" label="技术人员" width="100" />
        <el-table-column label="维护结果" width="100">
          <template #default="{ row }">
            <el-tag :type="getResultType(row.maintenanceResult)">
              {{ getResultLabel(row.maintenanceResult) }}
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
        <el-table-column label="操作" width="100" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" link @click="handleViewRecord(row)">查看</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        v-model:current-page="recordQuery.pageNum"
        v-model:page-size="recordQuery.pageSize"
        :total="recordTotal"
        :page-sizes="[10, 20, 50]"
        layout="total, sizes, prev, pager, next"
        @size-change="fetchMaintenanceRecords"
        @current-change="fetchMaintenanceRecords"
      />
    </el-card>

    <el-card class="alerts-card">
      <template #header>
        <span>告警记录</span>
      </template>
      <el-table v-loading="alertsLoading" :data="alerts" border stripe max-height="300">
        <el-table-column label="告警级别" width="100">
          <template #default="{ row }">
            <el-tag :type="getAlertType(row.type)">
              {{ getAlertLabel(row.type) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="message" label="告警信息" min-width="300" show-overflow-tooltip />
        <el-table-column prop="timestamp" label="告警时间" width="180" />
        <el-table-column label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.isRead ? 'info' : 'warning'">
              {{ row.isRead ? '已读' : '未读' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="100" fixed="right">
          <template #default="{ row }">
            <el-button v-if="!row.isRead" type="primary" link @click="handleMarkRead(row)">标记已读</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="recordDialogVisible" title="维护记录详情" width="600px">
      <el-descriptions :column="2" border>
        <el-descriptions-item label="维护类型">{{ getMaintenanceLabel(currentRecord?.maintenanceType) }}</el-descriptions-item>
        <el-descriptions-item label="维护结果">{{ getResultLabel(currentRecord?.maintenanceResult) }}</el-descriptions-item>
        <el-descriptions-item label="技术人员">{{ currentRecord?.technician }}</el-descriptions-item>
        <el-descriptions-item label="费用">{{ currentRecord?.cost ? `¥${currentRecord.cost}` : '-' }}</el-descriptions-item>
        <el-descriptions-item label="开始时间">{{ currentRecord?.startTime }}</el-descriptions-item>
        <el-descriptions-item label="结束时间">{{ currentRecord?.endTime }}</el-descriptions-item>
        <el-descriptions-item label="维护内容" :span="2">{{ currentRecord?.maintenanceContent }}</el-descriptions-item>
        <el-descriptions-item label="备注" :span="2">{{ currentRecord?.remark || '-' }}</el-descriptions-item>
      </el-descriptions>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import type { Equipment, EquipmentStatus, EquipmentAlert, MaintenanceRecord, MaintenanceQuery } from '@/api/equipment'
import { getEquipmentDetail, getEquipmentStatus, getEquipmentAlerts, getMaintenanceRecords, markAlertAsRead } from '@/api/equipment'

const route = useRoute()
const router = useRouter()

const loading = ref(false)
const recordsLoading = ref(false)
const alertsLoading = ref(false)
const equipment = ref<Equipment | null>(null)
const equipmentStatus = ref<EquipmentStatus | null>(null)
const maintenanceRecords = ref<MaintenanceRecord[]>([])
const recordTotal = ref(0)
const alerts = ref<EquipmentAlert[]>([])

const recordQuery = reactive<MaintenanceQuery>({
  pageNum: 1,
  pageSize: 10,
  equipmentId: Number(route.params.id)
})

const recordDialogVisible = ref(false)
const currentRecord = ref<MaintenanceRecord | null>(null)

const equipmentId = Number(route.params.id)

const getStatusType = (status?: string): 'success' | 'info' | 'warning' | 'danger' | 'primary' => {
  const map: Record<string, 'success' | 'info' | 'warning' | 'danger' | 'primary'> = {
    online: 'success',
    offline: 'info',
    maintenance: 'warning',
    fault: 'danger'
  }
  return map[status || ''] || 'info'
}

const getStatusLabel = (status?: string) => {
  const map: Record<string, string> = {
    online: '在线',
    offline: '离线',
    maintenance: '维护中',
    fault: '故障'
  }
  return map[status || ''] || status
}

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

const getAlertType = (type: string): 'success' | 'info' | 'warning' | 'danger' | 'primary' => {
  const map: Record<string, 'success' | 'info' | 'warning' | 'danger' | 'primary'> = {
    info: 'info',
    warning: 'warning',
    error: 'danger'
  }
  return map[type] || 'info'
}

const getAlertLabel = (type?: string) => {
  const map: Record<string, string> = {
    info: '信息',
    warning: '警告',
    error: '错误'
  }
  return map[type || ''] || type
}

const isNearExpiry = (date?: string) => {
  if (!date) return false
  const targetDate = new Date(date)
  const today = new Date()
  const diff = targetDate.getTime() - today.getTime()
  return diff < 7 * 24 * 60 * 60 * 1000
}

const fetchEquipmentDetail = async () => {
  loading.value = true
  try {
    equipment.value = await getEquipmentDetail(equipmentId)
  } catch {
    ElMessage.error('获取设备详情失败')
  } finally {
    loading.value = false
  }
}

const fetchEquipmentStatus = async () => {
  try {
    const res = await getEquipmentStatus(equipmentId)
    equipmentStatus.value = res
  } catch {
    console.error('获取设备状态失败')
  }
}

const fetchMaintenanceRecords = async () => {
  recordsLoading.value = true
  try {
    const res = await getMaintenanceRecords(recordQuery)
    maintenanceRecords.value = res.records
    recordTotal.value = res.total
  } catch {
    ElMessage.error('获取维护记录失败')
  } finally {
    recordsLoading.value = false
  }
}

const fetchAlerts = async () => {
  alertsLoading.value = true
  try {
    alerts.value = await getEquipmentAlerts(equipmentId)
  } catch {
    console.error('获取告警记录失败')
  } finally {
    alertsLoading.value = false
  }
}

const goBack = () => {
  router.back()
}

const handleEdit = () => {
  router.push(`/equipment/edit/${equipmentId}`)
}

const handleAddMaintenance = () => {
  router.push(`/equipment/maintenance/add?equipmentId=${equipmentId}`)
}

const handleViewRecord = (row: MaintenanceRecord) => {
  currentRecord.value = row
  recordDialogVisible.value = true
}

const handleMarkRead = async (row: EquipmentAlert) => {
  try {
    await markAlertAsRead(row.id)
    row.isRead = 1
    ElMessage.success('已标记为已读')
  } catch {
    ElMessage.error('操作失败')
  }
}

onMounted(() => {
  fetchEquipmentDetail()
  fetchEquipmentStatus()
  fetchMaintenanceRecords()
  fetchAlerts()
})
</script>

<style scoped lang="scss">
.page-container {
  padding: 0;
}

.el-page-header {
  margin-bottom: 20px;
}

.info-card,
.status-card,
.calibration-card,
.records-card,
.alerts-card {
  margin-bottom: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.monitor-item {
  text-align: center;
  padding: 20px;
  background: #f5f7fa;
  border-radius: 8px;

  .monitor-label {
    font-size: 14px;
    color: #909399;
    margin-bottom: 8px;
  }

  .monitor-value {
    font-size: 24px;
    font-weight: bold;
    color: #303133;

    &.error {
      color: #f56c6c;
    }
  }
}

.progress-item {
  padding: 10px 20px;

  .progress-label {
    font-size: 14px;
    color: #606266;
    margin-bottom: 8px;
  }
}

.calibration-item {
  text-align: center;
  padding: 20px;
  background: #f5f7fa;
  border-radius: 8px;

  .calibration-label {
    font-size: 14px;
    color: #909399;
    margin-bottom: 8px;
  }

  .calibration-value {
    font-size: 18px;
    font-weight: bold;
    color: #303133;

    &.warning {
      color: #e6a23c;
    }
  }
}

.el-pagination {
  margin-top: 16px;
  justify-content: flex-end;
}
</style>
