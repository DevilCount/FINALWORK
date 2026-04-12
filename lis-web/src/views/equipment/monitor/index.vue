<template>
  <div class="page-container">
    <el-row :gutter="20" class="stat-row">
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-content">
            <div class="stat-icon" style="background-color: #409eff">
              <el-icon :size="28"><Monitor /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ stats.total }}</div>
              <div class="stat-label">设备总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-content">
            <div class="stat-icon" style="background-color: #67c23a">
              <el-icon :size="28"><CircleCheck /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ stats.online }}</div>
              <div class="stat-label">在线设备</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-content">
            <div class="stat-icon" style="background-color: #e6a23c">
              <el-icon :size="28"><WarningFilled /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ stats.offline }}</div>
              <div class="stat-label">离线设备</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-content">
            <div class="stat-icon" style="background-color: #f56c6c">
              <el-icon :size="28"><CircleClose /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ stats.error }}</div>
              <div class="stat-label">异常设备</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-card style="margin-top: 20px">
      <template #header>
        <div class="card-header">
          <span>设备状态监控</span>
          <div class="header-actions">
            <el-switch v-model="autoRefresh" active-text="自动刷新" @change="handleAutoRefreshChange" />
            <el-button type="primary" @click="fetchData">
              <el-icon><Refresh /></el-icon>
              刷新
            </el-button>
          </div>
        </div>
      </template>

      <el-table v-loading="loading" :data="tableData" border stripe>
        <el-table-column prop="equipmentName" label="设备名称" min-width="150" />
        <el-table-column prop="equipmentId" label="设备ID" width="100" />
        <el-table-column label="在线状态" width="120">
          <template #default="{ row }">
            <el-tag :type="row.isOnline === 1 ? 'success' : 'danger'">
              {{ row.isOnline === 1 ? '在线' : '离线' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="lastCommTime" label="最后通信时间" width="180" />
        <el-table-column prop="totalTestCount" label="累计检测数" width="120" />
        <el-table-column prop="todayTestCount" label="今日检测数" width="120" />
        <el-table-column prop="errorCount" label="错误次数" width="100">
          <template #default="{ row }">
            <span :style="{ color: row.errorCount > 0 ? '#f56c6c' : '' }">{{ row.errorCount }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="updateTime" label="更新时间" width="180" />
        <el-table-column label="操作" width="120" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" link size="small" @click="handleToggleOnline(row)">
              {{ row.isOnline === 1 ? '下线' : '上线' }}
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, onBeforeUnmount } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Monitor, CircleCheck, WarningFilled, CircleClose, Refresh } from '@element-plus/icons-vue'
import { getAllEquipmentStatus, updateEquipmentOnlineStatus } from '@/api/equipment'
import type { EquipmentStatus } from '@/api/equipment'

const loading = ref(false)
const tableData = ref<EquipmentStatus[]>([])
const autoRefresh = ref(false)
let refreshTimer: ReturnType<typeof setInterval> | null = null

const stats = reactive({
  total: 0,
  online: 0,
  offline: 0,
  error: 0,
})

const fetchData = async () => {
  loading.value = true
  try {
    const data = await getAllEquipmentStatus()
    tableData.value = data || []
    stats.total = data.length
    stats.online = data.filter((d: EquipmentStatus) => d.isOnline === 1).length
    stats.offline = data.filter((d: EquipmentStatus) => d.isOnline === 0).length
    stats.error = data.reduce((sum: number, d: EquipmentStatus) => sum + (d.errorCount || 0), 0)
  } catch (error) {
    console.error('获取设备状态失败:', error)
  } finally {
    loading.value = false
  }
}

const handleToggleOnline = async (row: EquipmentStatus) => {
  try {
    const newStatus = row.isOnline === 1 ? 0 : 1
    await ElMessageBox.confirm(
      newStatus === 1 ? `确认将设备「${row.equipmentName}」上线？` : `确认将设备「${row.equipmentName}」下线？`,
      '提示',
      { type: 'warning' }
    )
    await updateEquipmentOnlineStatus(row.equipmentId, newStatus)
    ElMessage.success(newStatus === 1 ? '设备已上线' : '设备已下线')
    fetchData()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('操作失败:', error)
    }
  }
}

const handleAutoRefreshChange = (val: string | number | boolean) => {
  if (val) {
    refreshTimer = setInterval(() => {
      fetchData()
    }, 30000)
  } else {
    if (refreshTimer) {
      clearInterval(refreshTimer)
      refreshTimer = null
    }
  }
}

onMounted(() => {
  fetchData()
})

onBeforeUnmount(() => {
  if (refreshTimer) {
    clearInterval(refreshTimer)
    refreshTimer = null
  }
})
</script>

<style scoped lang="scss">
.page-container {
  padding: 0;
}

.stat-row {
  margin-bottom: 0;
}

.stat-card {
  cursor: pointer;
  transition: transform 0.3s;
}

.stat-card:hover {
  transform: translateY(-5px);
}

.stat-content {
  display: flex;
  align-items: center;
  gap: 16px;
}

.stat-icon {
  width: 50px;
  height: 50px;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
}

.stat-info {
  flex: 1;
}

.stat-value {
  font-size: 24px;
  font-weight: bold;
  color: #303133;
}

.stat-label {
  font-size: 13px;
  color: #909399;
  margin-top: 4px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.header-actions {
  display: flex;
  align-items: center;
  gap: 16px;
}
</style>
