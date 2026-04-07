<template>
  <div class="page-container">
    <el-card class="filter-card">
      <el-form :model="queryParams" inline>
        <el-form-item label="日期范围">
          <el-date-picker
            v-model="dateRange"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            value-format="YYYY-MM-DD"
            @change="handleDateChange"
          />
        </el-form-item>
        <el-form-item label="科室">
          <el-select v-model="queryParams.department" placeholder="请选择科室" clearable>
            <el-option v-for="dept in departments" :key="dept.value" :label="dept.label" :value="dept.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="统计周期">
          <el-select v-model="queryParams.groupBy" placeholder="请选择">
            <el-option label="按天" value="day" />
            <el-option label="按周" value="week" />
            <el-option label="按月" value="month" />
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
          <el-button type="success" @click="handleExport">
            <el-icon><Download /></el-icon>
            导出
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <el-row :gutter="20" class="summary-row">
      <el-col :span="6">
        <el-card class="summary-card">
          <div class="summary-content">
            <div class="summary-icon samples">
              <el-icon><Document /></el-icon>
            </div>
            <div class="summary-info">
              <div class="summary-value">{{ summaryData.totalSamples }}</div>
              <div class="summary-label">总样本数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="summary-card">
          <div class="summary-content">
            <div class="summary-icon completed">
              <el-icon><CircleCheck /></el-icon>
            </div>
            <div class="summary-info">
              <div class="summary-value">{{ summaryData.completedSamples }}</div>
              <div class="summary-label">已完成样本</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="summary-card">
          <div class="summary-content">
            <div class="summary-icon reports">
              <el-icon><Tickets /></el-icon>
            </div>
            <div class="summary-info">
              <div class="summary-value">{{ summaryData.totalReports }}</div>
              <div class="summary-label">报告总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="summary-card">
          <div class="summary-content">
            <div class="summary-icon time">
              <el-icon><Timer /></el-icon>
            </div>
            <div class="summary-info">
              <div class="summary-value">{{ summaryData.avgTurnaroundTime }}h</div>
              <div class="summary-label">平均周转时间</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20">
      <el-col :span="16">
        <el-card class="chart-card">
          <template #header>
            <div class="card-header">
              <span>工作量趋势</span>
            </div>
          </template>
          <div ref="trendChartRef" class="chart-container"></div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card class="chart-card">
          <template #header>
            <div class="card-header">
              <span>样本状态分布</span>
            </div>
          </template>
          <div ref="pieChartRef" class="chart-container"></div>
        </el-card>
      </el-col>
    </el-row>

    <el-card class="table-card">
      <template #header>
        <div class="card-header">
          <span>详细数据</span>
        </div>
      </template>
      <el-table v-loading="loading" :data="statisticsData" border stripe max-height="400">
        <el-table-column prop="date" label="日期" width="120" />
        <el-table-column prop="totalSamples" label="总样本数" width="100" />
        <el-table-column prop="completedSamples" label="已完成" width="100" />
        <el-table-column prop="pendingSamples" label="待处理" width="100" />
        <el-table-column prop="totalReports" label="报告数" width="100" />
        <el-table-column label="完成率" width="120">
          <template #default="{ row }">
            <el-progress :percentage="getCompletionRate(row)" :color="getProgressColor(getCompletionRate(row))" />
          </template>
        </el-table-column>
        <el-table-column label="平均周转时间" width="140">
          <template #default="{ row }">
            {{ row.avgTurnaroundTime ? `${row.avgTurnaroundTime}小时` : '-' }}
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, onUnmounted, nextTick } from 'vue'
import { ElMessage } from 'element-plus'
import { Search, Refresh, Download, Document, CircleCheck, Tickets, Timer } from '@element-plus/icons-vue'
import * as echarts from 'echarts'
import type { WorkloadQuery, WorkloadStatistics, WorkloadChart } from '@/api/statistics'
import { getWorkloadStatistics, getWorkloadChart, exportWorkloadReport } from '@/api/statistics'

const loading = ref(false)
const statisticsData = ref<WorkloadStatistics[]>([])
const chartData = ref<WorkloadChart | null>(null)

const dateRange = ref<string[]>([])
const getDefaultDateRange = () => {
  const end = new Date()
  const start = new Date()
  start.setDate(start.getDate() - 30)
  return [formatDate(start), formatDate(end)]
}

const formatDate = (date: Date) => {
  return date.toISOString().split('T')[0]
}

const queryParams = reactive<WorkloadQuery>({
  startDate: getDefaultDateRange()[0],
  endDate: getDefaultDateRange()[1],
  department: '',
  groupBy: 'day'
})

dateRange.value = getDefaultDateRange()

const summaryData = reactive({
  totalSamples: 0,
  completedSamples: 0,
  pendingSamples: 0,
  totalReports: 0,
  avgTurnaroundTime: 0
})

const departments = [
  { label: '检验科', value: '检验科' },
  { label: '生化室', value: '生化室' },
  { label: '免疫室', value: '免疫室' },
  { label: '微生物室', value: '微生物室' },
  { label: '门诊检验', value: '门诊检验' }
]

const trendChartRef = ref<HTMLElement>()
const pieChartRef = ref<HTMLElement>()
let trendChart: echarts.ECharts | null = null
let pieChart: echarts.ECharts | null = null

const handleDateChange = (val: string[] | null) => {
  if (val) {
    queryParams.startDate = val[0]
    queryParams.endDate = val[1]
  } else {
    queryParams.startDate = ''
    queryParams.endDate = ''
  }
}

const handleQuery = () => {
  fetchStatistics()
}

const handleReset = () => {
  const defaultRange = getDefaultDateRange()
  dateRange.value = defaultRange
  queryParams.startDate = defaultRange[0]
  queryParams.endDate = defaultRange[1]
  queryParams.department = ''
  queryParams.groupBy = 'day'
  handleQuery()
}

const handleExport = async () => {
  try {
    const blob = await exportWorkloadReport(queryParams)
    const url = window.URL.createObjectURL(blob)
    const link = document.createElement('a')
    link.href = url
    link.download = `工作量统计报告_${queryParams.startDate}_${queryParams.endDate}.xlsx`
    link.click()
    window.URL.revokeObjectURL(url)
    ElMessage.success('导出成功')
  } catch {
    ElMessage.error('导出失败')
  }
}

const getCompletionRate = (row: WorkloadStatistics) => {
  if (!row.totalSamples) return 0
  return Math.round((row.completedSamples / row.totalSamples) * 100)
}

const getProgressColor = (percentage: number) => {
  if (percentage < 60) return '#f56c6c'
  if (percentage < 80) return '#e6a23c'
  return '#67c23a'
}

const fetchStatistics = async () => {
  loading.value = true
  try {
    const [statsRes, chartRes] = await Promise.all([
      getWorkloadStatistics(queryParams),
      getWorkloadChart(queryParams)
    ])
    statisticsData.value = statsRes
    chartData.value = chartRes
    updateSummary(statsRes)
    await nextTick()
    renderCharts()
  } catch {
    ElMessage.error('获取统计数据失败')
  } finally {
    loading.value = false
  }
}

const updateSummary = (data: WorkloadStatistics[]) => {
  summaryData.totalSamples = data.reduce((sum, item) => sum + item.totalSamples, 0)
  summaryData.completedSamples = data.reduce((sum, item) => sum + item.completedSamples, 0)
  summaryData.pendingSamples = data.reduce((sum, item) => sum + item.pendingSamples, 0)
  summaryData.totalReports = data.reduce((sum, item) => sum + item.totalReports, 0)
  const avgTime = data.reduce((sum, item) => sum + (item.avgTurnaroundTime || 0), 0)
  summaryData.avgTurnaroundTime = data.length ? Math.round(avgTime / data.length) : 0
}

const renderCharts = () => {
  renderTrendChart()
  renderPieChart()
}

const renderTrendChart = () => {
  if (!trendChartRef.value || !chartData.value) return

  if (!trendChart) {
    trendChart = echarts.init(trendChartRef.value)
  }

  const option: echarts.EChartsOption = {
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'cross'
      }
    },
    legend: {
      data: chartData.value.series.map(s => s.name)
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      containLabel: true
    },
    xAxis: {
      type: 'category',
      boundaryGap: false,
      data: chartData.value.categories
    },
    yAxis: [
      {
        type: 'value',
        name: '数量',
        position: 'left'
      },
      {
        type: 'value',
        name: '时间(h)',
        position: 'right'
      }
    ],
    series: chartData.value.series.map((s, index) => ({
      name: s.name,
      type: index === chartData.value!.series.length - 1 ? 'line' : 'bar',
      yAxisIndex: index === chartData.value!.series.length - 1 ? 1 : 0,
      data: s.data,
      smooth: true,
      itemStyle: {
        color: getSeriesColor(index)
      }
    }))
  }

  trendChart.setOption(option)
}

const renderPieChart = () => {
  if (!pieChartRef.value) return

  if (!pieChart) {
    pieChart = echarts.init(pieChartRef.value)
  }

  const pieData = [
    { name: '已完成', value: summaryData.completedSamples },
    { name: '待处理', value: summaryData.pendingSamples }
  ]

  const option: echarts.EChartsOption = {
    tooltip: {
      trigger: 'item',
      formatter: '{b}: {c} ({d}%)'
    },
    legend: {
      orient: 'vertical',
      left: 'left'
    },
    series: [
      {
        name: '样本状态',
        type: 'pie',
        radius: ['40%', '70%'],
        avoidLabelOverlap: false,
        itemStyle: {
          borderRadius: 10,
          borderColor: '#fff',
          borderWidth: 2
        },
        label: {
          show: true,
          formatter: '{b}: {c}'
        },
        emphasis: {
          label: {
            show: true,
            fontSize: 16,
            fontWeight: 'bold'
          }
        },
        data: pieData.map((item, index) => ({
          ...item,
          itemStyle: {
            color: index === 0 ? '#67c23a' : '#e6a23c'
          }
        }))
      }
    ]
  }

  pieChart.setOption(option)
}

const getSeriesColor = (index: number) => {
  const colors = ['#409eff', '#67c23a', '#e6a23c', '#f56c6c', '#909399']
  return colors[index % colors.length]
}

const handleResize = () => {
  trendChart?.resize()
  pieChart?.resize()
}

onMounted(() => {
  fetchStatistics()
  window.addEventListener('resize', handleResize)
})

onUnmounted(() => {
  window.removeEventListener('resize', handleResize)
  trendChart?.dispose()
  pieChart?.dispose()
})
</script>

<style scoped lang="scss">
.page-container {
  padding: 0;
}

.filter-card {
  margin-bottom: 20px;
}

.summary-row {
  margin-bottom: 20px;
}

.summary-card {
  .summary-content {
    display: flex;
    align-items: center;
    padding: 10px;
  }

  .summary-icon {
    width: 60px;
    height: 60px;
    border-radius: 12px;
    display: flex;
    align-items: center;
    justify-content: center;
    margin-right: 16px;

    .el-icon {
      font-size: 28px;
      color: #fff;
    }

    &.samples {
      background: linear-gradient(135deg, #409eff, #66b1ff);
    }

    &.completed {
      background: linear-gradient(135deg, #67c23a, #85ce61);
    }

    &.reports {
      background: linear-gradient(135deg, #e6a23c, #f5d442);
    }

    &.time {
      background: linear-gradient(135deg, #909399, #b4b4b4);
    }
  }

  .summary-info {
    .summary-value {
      font-size: 28px;
      font-weight: bold;
      color: #303133;
      line-height: 1.2;
    }

    .summary-label {
      font-size: 14px;
      color: #909399;
      margin-top: 4px;
    }
  }
}

.chart-card {
  margin-bottom: 20px;

  .card-header {
    font-weight: bold;
  }

  .chart-container {
    height: 350px;
  }
}

.table-card {
  .card-header {
    font-weight: bold;
  }
}
</style>
