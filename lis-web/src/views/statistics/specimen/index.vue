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
          <el-select v-model="queryParams.deptId" placeholder="请选择科室" clearable>
            <el-option v-for="dept in departments" :key="dept.value" :label="dept.label" :value="dept.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="标本类型">
          <el-select v-model="queryParams.specimenTypeId" placeholder="请选择类型" clearable>
            <el-option v-for="type in specimenTypes" :key="type.value" :label="type.label" :value="type.value" />
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
              <el-icon><Box /></el-icon>
            </div>
            <div class="summary-info">
              <div class="summary-value">{{ summaryData.totalCount }}</div>
              <div class="summary-label">标本总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="summary-card">
          <div class="summary-content">
            <div class="summary-icon qualified">
              <el-icon><CircleCheck /></el-icon>
            </div>
            <div class="summary-info">
              <div class="summary-value">{{ summaryData.receivedCount }}</div>
              <div class="summary-label">已接收</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="summary-card">
          <div class="summary-content">
            <div class="summary-icon unqualified">
              <el-icon><CircleClose /></el-icon>
            </div>
            <div class="summary-info">
              <div class="summary-value">{{ summaryData.rejectedCount }}</div>
              <div class="summary-label">已拒收</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="summary-card">
          <div class="summary-content">
            <div class="summary-icon rate">
              <el-icon><TrendCharts /></el-icon>
            </div>
            <div class="summary-info">
              <div class="summary-value">{{ summaryData.completionRate }}%</div>
              <div class="summary-label">完成率</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20">
      <el-col :span="12">
        <el-card class="chart-card">
          <template #header>
            <div class="card-header">
              <span>标本类型分布</span>
            </div>
          </template>
          <div ref="pieChartRef" class="chart-container"></div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card class="chart-card">
          <template #header>
            <div class="card-header">
              <span>标本趋势分析</span>
            </div>
          </template>
          <div ref="trendChartRef" class="chart-container"></div>
        </el-card>
      </el-col>
    </el-row>

    <el-card class="table-card">
      <template #header>
        <div class="card-header">
          <span>标本统计详情</span>
        </div>
      </template>
      <el-table v-loading="loading" :data="statisticsData" border stripe max-height="400">
        <el-table-column prop="statDate" label="日期" width="150" />
        <el-table-column prop="totalCount" label="总数" width="100" />
        <el-table-column prop="receivedCount" label="接收数" width="100" />
        <el-table-column prop="completedCount" label="完成数" width="100" />
        <el-table-column prop="rejectedCount" label="拒收数" width="100" />
        <el-table-column prop="statCount" label="急诊数" width="100" />
        <el-table-column label="完成率" width="150">
          <template #default="{ row }">
            <el-progress :percentage="row.totalCount ? Math.round((row.completedCount / row.totalCount) * 100) : 0" :color="getProgressColor(row.totalCount ? Math.round((row.completedCount / row.totalCount) * 100) : 0)" />
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, onUnmounted, nextTick } from 'vue'
import { ElMessage } from 'element-plus'
import { Search, Refresh, Download, Box, CircleCheck, CircleClose, TrendCharts } from '@element-plus/icons-vue'
import * as echarts from 'echarts'
import type { SpecimenQuery, SpecimenStatistics, SpecimenChart } from '@/api/statistics'
import { getSpecimenStatistics, getSpecimenChart, exportSpecimenReport } from '@/api/statistics'

const loading = ref(false)
const statisticsData = ref<SpecimenStatistics[]>([])
const chartData = ref<SpecimenChart | null>(null)

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

const queryParams = reactive<SpecimenQuery>({
  startDate: getDefaultDateRange()[0],
  endDate: getDefaultDateRange()[1],
  deptId: undefined,
  specimenTypeId: undefined
})

dateRange.value = getDefaultDateRange()

const summaryData = reactive({
  totalCount: 0,
  receivedCount: 0,
  rejectedCount: 0,
  completedCount: 0,
  completionRate: 0
})

const departments = [
  { label: '检验科', value: '检验科' },
  { label: '生化室', value: '生化室' },
  { label: '免疫室', value: '免疫室' },
  { label: '微生物室', value: '微生物室' },
  { label: '门诊检验', value: '门诊检验' }
]

const specimenTypes = [
  { label: '血液', value: '血液' },
  { label: '尿液', value: '尿液' },
  { label: '粪便', value: '粪便' },
  { label: '痰液', value: '痰液' },
  { label: '胸腹水', value: '胸腹水' },
  { label: '脑脊液', value: '脑脊液' },
  { label: '其他', value: '其他' }
]

const pieChartRef = ref<HTMLElement>()
const trendChartRef = ref<HTMLElement>()
let pieChart: echarts.ECharts | null = null
let trendChart: echarts.ECharts | null = null

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
  queryParams.deptId = undefined
  queryParams.specimenTypeId = undefined
  handleQuery()
}

const handleExport = async () => {
  try {
    const blob = await exportSpecimenReport(queryParams)
    const url = window.URL.createObjectURL(blob)
    const link = document.createElement('a')
    link.href = url
    link.download = `标本统计报告_${queryParams.startDate}_${queryParams.endDate}.xlsx`
    link.click()
    window.URL.revokeObjectURL(url)
    ElMessage.success('导出成功')
  } catch {
    ElMessage.error('导出失败')
  }
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
      getSpecimenStatistics(queryParams),
      getSpecimenChart(queryParams)
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

const updateSummary = (data: SpecimenStatistics[]) => {
  summaryData.totalCount = data.reduce((sum, item) => sum + item.totalCount, 0)
  summaryData.receivedCount = data.reduce((sum, item) => sum + item.receivedCount, 0)
  summaryData.rejectedCount = data.reduce((sum, item) => sum + item.rejectedCount, 0)
  summaryData.completedCount = data.reduce((sum, item) => sum + item.completedCount, 0)
  summaryData.completionRate = summaryData.totalCount
    ? Math.round((summaryData.completedCount / summaryData.totalCount) * 100)
    : 0
}

const renderCharts = () => {
  renderPieChart()
  renderTrendChart()
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
        name: '标本类型',
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
        data: chartData.value.series.map((s, index) => ({
          name: s.name,
          value: s.data.reduce((sum, val) => sum + val, 0),
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
      data: chartData.value.series.map(s => s.name),
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
      data: chartData.value.xAxis.data
    },
    yAxis: {
      type: 'value',
      name: '数量'
    },
    series: chartData.value.series.map((s, index) => ({
      name: s.name,
      type: 'line',
      stack: 'Total',
      smooth: true,
      data: s.data,
      itemStyle: {
        color: getSeriesColor(index)
      },
      areaStyle: {
        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
          { offset: 0, color: getSeriesColor(index) + '80' },
          { offset: 1, color: getSeriesColor(index) + '20' }
        ])
      }
    }))
  }

  trendChart.setOption(option)
}

const getSeriesColor = (index: number) => {
  const colors = ['#409eff', '#67c23a', '#e6a23c', '#f56c6c', '#909399', '#9c27b0', '#00bcd4']
  return colors[index % colors.length]
}

const handleResize = () => {
  pieChart?.resize()
  trendChart?.resize()
}

onMounted(() => {
  fetchStatistics()
  window.addEventListener('resize', handleResize)
})

onUnmounted(() => {
  window.removeEventListener('resize', handleResize)
  pieChart?.dispose()
  trendChart?.dispose()
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

    &.qualified {
      background: linear-gradient(135deg, #67c23a, #85ce61);
    }

    &.unqualified {
      background: linear-gradient(135deg, #f56c6c, #fab6b6);
    }

    &.rate {
      background: linear-gradient(135deg, #e6a23c, #f5d442);
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
