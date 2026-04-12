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
              <div class="stat-value">{{ summary.totalEquipment }}</div>
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
              <div class="stat-value">{{ summary.totalTests }}</div>
              <div class="stat-label">累计检测数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-content">
            <div class="stat-icon" style="background-color: #e6a23c">
              <el-icon :size="28"><TrendCharts /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ summary.avgUptime }}%</div>
              <div class="stat-label">平均在线率</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-content">
            <div class="stat-icon" style="background-color: #f56c6c">
              <el-icon :size="28"><Warning /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ summary.errorCount }}</div>
              <div class="stat-label">故障总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 20px">
      <el-col :span="12">
        <el-card>
          <template #header>
            <span>设备检测量趋势</span>
          </template>
          <div ref="testChartRef" class="chart-container"></div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <template #header>
            <span>设备在线率趋势</span>
          </template>
          <div ref="uptimeChartRef" class="chart-container"></div>
        </el-card>
      </el-col>
    </el-row>

    <el-card style="margin-top: 20px">
      <template #header>
        <div class="card-header">
          <span>设备统计明细</span>
        </div>
      </template>

      <el-table v-loading="loading" :data="tableData" border stripe>
        <el-table-column prop="equipmentName" label="设备名称" min-width="150" />
        <el-table-column prop="totalTestCount" label="累计检测数" width="120" />
        <el-table-column prop="todayTestCount" label="今日检测数" width="120" />
        <el-table-column prop="errorCount" label="故障次数" width="100">
          <template #default="{ row }">
            <span :style="{ color: row.errorCount > 0 ? '#f56c6c' : '' }">{{ row.errorCount }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="uptimeRate" label="在线率" width="100">
          <template #default="{ row }">
            <span>{{ row.uptimeRate != null ? row.uptimeRate + '%' : '-' }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="lastMaintenanceDate" label="最近维护" width="160" />
        <el-table-column prop="nextMaintenanceDate" label="下次维护" width="160" />
      </el-table>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, onBeforeUnmount, nextTick } from 'vue'
import * as echarts from 'echarts'
import { Monitor, CircleCheck, TrendCharts, Warning } from '@element-plus/icons-vue'
import { getEquipmentStatPage, getEquipmentTestChart, getEquipmentUptimeChart } from '@/api/statistics'

const loading = ref(false)
const tableData = ref<any[]>([])

const summary = reactive({
  totalEquipment: 0,
  totalTests: 0,
  avgUptime: 0,
  errorCount: 0,
})

const testChartRef = ref<HTMLElement>()
const uptimeChartRef = ref<HTMLElement>()
let testChart: echarts.ECharts | null = null
let uptimeChart: echarts.ECharts | null = null

const fetchTableData = async () => {
  loading.value = true
  try {
    const res = await getEquipmentStatPage({ pageNum: 1, pageSize: 100 })
    if (res) {
      tableData.value = res.records ?? res.list ?? []
      summary.totalEquipment = res.total ?? tableData.value.length
      summary.totalTests = tableData.value.reduce((sum: number, r: any) => sum + (r.totalTestCount || 0), 0)
      summary.errorCount = tableData.value.reduce((sum: number, r: any) => sum + (r.errorCount || 0), 0)
      const uptimes = tableData.value.filter((r: any) => r.uptimeRate != null).map((r: any) => r.uptimeRate)
      summary.avgUptime = uptimes.length > 0 ? Math.round(uptimes.reduce((a: number, b: number) => a + b, 0) / uptimes.length) : 0
    }
  } catch (error) {
    console.error('获取设备统计数据失败:', error)
  } finally {
    loading.value = false
  }
}

const initTestChart = async () => {
  if (!testChartRef.value) return
  testChart = echarts.init(testChartRef.value)
  try {
    const data = await getEquipmentTestChart({})
    if (data) {
      testChart.setOption({
        tooltip: { trigger: 'axis' },
        legend: { data: data.legend?.data ?? ['检测量'] },
        grid: { left: '3%', right: '4%', bottom: '3%', containLabel: true },
        xAxis: { type: 'category', data: data.xAxis?.data ?? data.categories ?? [] },
        yAxis: { type: 'value' },
        series: data.series ?? [{ name: '检测量', type: 'bar', data: [] }],
      })
    }
  } catch {
    testChart.setOption({
      tooltip: { trigger: 'axis' },
      grid: { left: '3%', right: '4%', bottom: '3%', containLabel: true },
      xAxis: { type: 'category', data: [] },
      yAxis: { type: 'value' },
      series: [{ name: '检测量', type: 'bar', data: [] }],
    })
  }
}

const initUptimeChart = async () => {
  if (!uptimeChartRef.value) return
  uptimeChart = echarts.init(uptimeChartRef.value)
  try {
    const data = await getEquipmentUptimeChart({})
    if (data) {
      uptimeChart.setOption({
        tooltip: { trigger: 'axis' },
        legend: { data: data.legend?.data ?? ['在线率'] },
        grid: { left: '3%', right: '4%', bottom: '3%', containLabel: true },
        xAxis: { type: 'category', data: data.xAxis?.data ?? data.categories ?? [] },
        yAxis: { type: 'value', max: 100 },
        series: data.series ?? [{ name: '在线率', type: 'line', smooth: true, data: [] }],
      })
    }
  } catch {
    uptimeChart.setOption({
      tooltip: { trigger: 'axis' },
      grid: { left: '3%', right: '4%', bottom: '3%', containLabel: true },
      xAxis: { type: 'category', data: [] },
      yAxis: { type: 'value', max: 100 },
      series: [{ name: '在线率', type: 'line', smooth: true, data: [] }],
    })
  }
}

const handleResize = () => {
  testChart?.resize()
  uptimeChart?.resize()
}

onMounted(async () => {
  await fetchTableData()
  await nextTick()
  initTestChart()
  initUptimeChart()
  window.addEventListener('resize', handleResize)
})

onBeforeUnmount(() => {
  window.removeEventListener('resize', handleResize)
  testChart?.dispose()
  uptimeChart?.dispose()
})
</script>

<style scoped lang="scss">
.page-container {
  padding: 0;
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

.chart-container {
  height: 300px;
  width: 100%;
}
</style>
