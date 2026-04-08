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
        <el-form-item label="报告类型">
          <el-select v-model="queryParams.reportType" placeholder="请选择类型" clearable>
            <el-option v-for="type in reportTypes" :key="type.value" :label="type.label" :value="type.value" />
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
            <div class="summary-icon total">
              <el-icon><Tickets /></el-icon>
            </div>
            <div class="summary-info">
              <div class="summary-value">{{ summaryData.totalCount }}</div>
              <div class="summary-label">报告总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="summary-card">
          <div class="summary-content">
            <div class="summary-icon normal">
              <el-icon><CircleCheck /></el-icon>
            </div>
            <div class="summary-info">
              <div class="summary-value">{{ summaryData.normalCount }}</div>
              <div class="summary-label">正常报告</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="summary-card">
          <div class="summary-content">
            <div class="summary-icon abnormal">
              <el-icon><Warning /></el-icon>
            </div>
            <div class="summary-info">
              <div class="summary-value">{{ summaryData.abnormalCount }}</div>
              <div class="summary-label">异常报告</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="summary-card">
          <div class="summary-content">
            <div class="summary-icon critical">
              <el-icon><Bell /></el-icon>
            </div>
            <div class="summary-info">
              <div class="summary-value">{{ summaryData.criticalCount }}</div>
              <div class="summary-label">危急值报告</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20">
      <el-col :span="8">
        <el-card class="chart-card">
          <template #header>
            <div class="card-header">
              <span>报告类型分布</span>
            </div>
          </template>
          <div ref="pieChartRef" class="chart-container"></div>
        </el-card>
      </el-col>
      <el-col :span="16">
        <el-card class="chart-card">
          <template #header>
            <div class="card-header">
              <span>报告数量趋势</span>
            </div>
          </template>
          <div ref="trendChartRef" class="chart-container"></div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20">
      <el-col :span="12">
        <el-card class="chart-card">
          <template #header>
            <div class="card-header">
              <span>各类型报告统计</span>
            </div>
          </template>
          <div ref="barChartRef" class="chart-container"></div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card class="table-card">
          <template #header>
            <div class="card-header">
              <span>报告统计详情</span>
            </div>
          </template>
          <el-table v-loading="loading" :data="statisticsData" border stripe max-height="300">
            <el-table-column prop="reportType" label="报告类型" width="120" />
            <el-table-column prop="totalCount" label="总数" width="80" />
            <el-table-column prop="normalCount" label="正常" width="80" />
            <el-table-column prop="abnormalCount" label="异常" width="80" />
            <el-table-column prop="criticalCount" label="危急值" width="80" />
            <el-table-column label="平均审核时间" width="120">
              <template #default="{ row }">
                {{ row.avgReviewTime ? `${row.avgReviewTime}分钟` : '-' }}
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, onUnmounted, nextTick } from 'vue'
import { ElMessage } from 'element-plus'
import { Search, Refresh, Download, Tickets, CircleCheck, Warning, Bell } from '@element-plus/icons-vue'
import * as echarts from 'echarts'
import type { StatisticsReportQuery, ReportStatistics, ReportChart } from '@/api/statistics'
import { getReportStatistics, getReportChart, exportReportReport } from '@/api/statistics'

const loading = ref(false)
const statisticsData = ref<ReportStatistics[]>([])
const chartData = ref<ReportChart | null>(null)

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

const queryParams = reactive<StatisticsReportQuery>({
  startDate: getDefaultDateRange()[0],
  endDate: getDefaultDateRange()[1],
  department: '',
  reportType: ''
})

dateRange.value = getDefaultDateRange()

const summaryData = reactive({
  totalCount: 0,
  normalCount: 0,
  abnormalCount: 0,
  criticalCount: 0
})

const departments = [
  { label: '检验科', value: '检验科' },
  { label: '生化室', value: '生化室' },
  { label: '免疫室', value: '免疫室' },
  { label: '微生物室', value: '微生物室' },
  { label: '门诊检验', value: '门诊检验' }
]

const reportTypes = [
  { label: '血常规', value: '血常规' },
  { label: '生化检验', value: '生化检验' },
  { label: '免疫检验', value: '免疫检验' },
  { label: '微生物检验', value: '微生物检验' },
  { label: '尿液分析', value: '尿液分析' },
  { label: '凝血功能', value: '凝血功能' }
]

const pieChartRef = ref<HTMLElement>()
const trendChartRef = ref<HTMLElement>()
const barChartRef = ref<HTMLElement>()
let pieChart: echarts.ECharts | null = null
let trendChart: echarts.ECharts | null = null
let barChart: echarts.ECharts | null = null

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
  queryParams.reportType = ''
  handleQuery()
}

const handleExport = async () => {
  try {
    const blob = await exportReportReport(queryParams)
    const url = window.URL.createObjectURL(blob)
    const link = document.createElement('a')
    link.href = url
    link.download = `报告统计报告_${queryParams.startDate}_${queryParams.endDate}.xlsx`
    link.click()
    window.URL.revokeObjectURL(url)
    ElMessage.success('导出成功')
  } catch {
    ElMessage.error('导出失败')
  }
}

const fetchStatistics = async () => {
  loading.value = true
  try {
    const [statsRes, chartRes] = await Promise.all([
      getReportStatistics(queryParams),
      getReportChart(queryParams)
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

const updateSummary = (data: ReportStatistics[]) => {
  summaryData.totalCount = data.reduce((sum, item) => sum + item.totalCount, 0)
  summaryData.normalCount = data.reduce((sum, item) => sum + item.normalCount, 0)
  summaryData.abnormalCount = data.reduce((sum, item) => sum + item.abnormalCount, 0)
  summaryData.criticalCount = data.reduce((sum, item) => sum + item.criticalCount, 0)
}

const renderCharts = () => {
  renderPieChart()
  renderTrendChart()
  renderBarChart()
}

const renderPieChart = () => {
  if (!pieChartRef.value || !chartData.value) return

  if (!pieChart) {
    pieChart = echarts.init(pieChartRef.value)
  }

  const option: echarts.EChartsOption = {
    tooltip: {
      trigger: 'item',
      formatter: '{b}: {c} ({d}%)'
    },
    legend: {
      orient: 'vertical',
      left: 'left',
      top: 'center'
    },
    series: [
      {
        name: '报告类型',
        type: 'pie',
        radius: ['40%', '70%'],
        center: ['60%', '50%'],
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
        data: chartData.value.pieData.map((item, index) => ({
          ...item,
          itemStyle: {
            color: getSeriesColor(index)
          }
        }))
      }
    ]
  }

  pieChart.setOption(option)
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
      data: chartData.value.trendData.series.map(s => s.name),
      top: 0
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      top: '40px',
      containLabel: true
    },
    xAxis: {
      type: 'category',
      boundaryGap: false,
      data: chartData.value.trendData.categories
    },
    yAxis: {
      type: 'value',
      name: '数量'
    },
    series: chartData.value.trendData.series.map((s, index) => ({
      name: s.name,
      type: 'line',
      smooth: true,
      data: s.data,
      itemStyle: {
        color: getSeriesColor(index)
      }
    }))
  }

  trendChart.setOption(option)
}

const renderBarChart = () => {
  if (!barChartRef.value || !chartData.value) return

  if (!barChart) {
    barChart = echarts.init(barChartRef.value)
  }

  const option: echarts.EChartsOption = {
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'shadow'
      }
    },
    legend: {
      data: chartData.value.barData.series.map(s => s.name),
      top: 0
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      top: '40px',
      containLabel: true
    },
    xAxis: {
      type: 'category',
      data: chartData.value.barData.categories
    },
    yAxis: {
      type: 'value',
      name: '数量'
    },
    series: chartData.value.barData.series.map((s) => ({
      name: s.name,
      type: 'bar',
      stack: 'total',
      data: s.data,
      itemStyle: {
        color: getStatusColor(s.name)
      }
    }))
  }

  barChart.setOption(option)
}

const getSeriesColor = (index: number) => {
  const colors = ['#409eff', '#67c23a', '#e6a23c', '#f56c6c', '#909399', '#9c27b0']
  return colors[index % colors.length]
}

const getStatusColor = (name: string) => {
  const colorMap: Record<string, string> = {
    '正常': '#67c23a',
    '异常': '#e6a23c',
    '危急值': '#f56c6c'
  }
  return colorMap[name] || '#409eff'
}

const handleResize = () => {
  pieChart?.resize()
  trendChart?.resize()
  barChart?.resize()
}

onMounted(() => {
  fetchStatistics()
  window.addEventListener('resize', handleResize)
})

onUnmounted(() => {
  window.removeEventListener('resize', handleResize)
  pieChart?.dispose()
  trendChart?.dispose()
  barChart?.dispose()
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

    &.total {
      background: linear-gradient(135deg, #409eff, #66b1ff);
    }

    &.normal {
      background: linear-gradient(135deg, #67c23a, #85ce61);
    }

    &.abnormal {
      background: linear-gradient(135deg, #e6a23c, #f5d442);
    }

    &.critical {
      background: linear-gradient(135deg, #f56c6c, #fab6b6);
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
    height: 300px;
  }
}

.table-card {
  margin-bottom: 20px;

  .card-header {
    font-weight: bold;
  }
}
</style>
