<template>
  <div class="dashboard">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-content">
            <div class="stat-icon" style="background-color: #409eff">
              <el-icon :size="32"><Document /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ overview.totalReports }}</div>
              <div class="stat-label">报告总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-content">
            <div class="stat-icon" style="background-color: #67c23a">
              <el-icon :size="32"><Finished /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ overview.completedReports }}</div>
              <div class="stat-label">已完成</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-content">
            <div class="stat-icon" style="background-color: #e6a23c">
              <el-icon :size="32"><Clock /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ overview.pendingReports }}</div>
              <div class="stat-label">待审核</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-content">
            <div class="stat-icon" style="background-color: #f56c6c">
              <el-icon :size="32"><Warning /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ overview.panicValues }}</div>
              <div class="stat-label">危急值</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20" style="margin-top: 20px">
      <el-col :span="16">
        <el-card shadow="hover">
          <template #header>
            <span>检验趋势</span>
          </template>
          <div ref="trendChartRef" class="chart-container"></div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="hover">
          <template #header>
            <span>待办事项</span>
          </template>
          <el-timeline>
            <el-timeline-item
              v-for="(item, index) in todos"
              :key="index"
              :timestamp="item.time"
              placement="top"
            >
              {{ item.content }}
            </el-timeline-item>
          </el-timeline>
          <el-empty v-if="todos.length === 0" description="暂无待办事项" />
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20" style="margin-top: 20px">
      <el-col :span="12">
        <el-card shadow="hover">
          <template #header>
            <span>标本状态分布</span>
          </template>
          <div ref="specimenPieRef" class="chart-container"></div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card shadow="hover">
          <template #header>
            <span>科室工作量排名</span>
          </template>
          <div ref="deptBarRef" class="chart-container"></div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount, nextTick } from 'vue'
import * as echarts from 'echarts'
import { getDashboardOverview, getWorkloadTrendChart, getSpecimenTypePieChart } from '@/api/statistics'

defineOptions({
  name: 'Dashboard',
})

const overview = ref({
  totalReports: 0,
  completedReports: 0,
  pendingReports: 0,
  panicValues: 0,
  todaySpecimens: 0,
  todayReports: 0,
  onlineEquipment: 0,
  totalEquipment: 0,
})

const todos = ref<{ content: string; time: string }[]>([])

const trendChartRef = ref<HTMLElement>()
const specimenPieRef = ref<HTMLElement>()
const deptBarRef = ref<HTMLElement>()

let trendChart: echarts.ECharts | null = null
let specimenPieChart: echarts.ECharts | null = null
let deptBarChart: echarts.ECharts | null = null

const fetchOverview = async () => {
  try {
    const data = await getDashboardOverview()
    if (data) {
      overview.value = {
        totalReports: data.totalReports ?? 0,
        completedReports: data.completedReports ?? 0,
        pendingReports: data.pendingReports ?? 0,
        panicValues: data.panicValues ?? 0,
        todaySpecimens: data.todaySpecimens ?? 0,
        todayReports: data.todayReports ?? 0,
        onlineEquipment: data.onlineEquipment ?? 0,
        totalEquipment: data.totalEquipment ?? 0,
      }
      if (data.todos) {
        todos.value = data.todos
      }
    }
  } catch (error) {
    console.error('获取仪表盘数据失败:', error)
  }
}

const initTrendChart = async () => {
  if (!trendChartRef.value) return
  trendChart = echarts.init(trendChartRef.value)
  try {
    const data = await getWorkloadTrendChart({ startDate: getDaysAgo(7), endDate: getToday() })
    if (data) {
      trendChart.setOption({
        tooltip: { trigger: 'axis' },
        legend: { data: data.legend?.data ?? ['标本数', '报告数'] },
        grid: { left: '3%', right: '4%', bottom: '3%', containLabel: true },
        xAxis: {
          type: 'category',
          data: data.xAxis?.data ?? data.categories ?? [],
        },
        yAxis: { type: 'value' },
        series: data.series ?? [
          { name: '标本数', type: 'line', smooth: true, data: [] },
          { name: '报告数', type: 'line', smooth: true, data: [] },
        ],
      })
    }
  } catch {
    trendChart.setOption({
      tooltip: { trigger: 'axis' },
      legend: { data: ['标本数', '报告数'] },
      grid: { left: '3%', right: '4%', bottom: '3%', containLabel: true },
      xAxis: { type: 'category', data: [] },
      yAxis: { type: 'value' },
      series: [
        { name: '标本数', type: 'line', smooth: true, data: [] },
        { name: '报告数', type: 'line', smooth: true, data: [] },
      ],
    })
  }
}

const initSpecimenPieChart = async () => {
  if (!specimenPieRef.value) return
  specimenPieChart = echarts.init(specimenPieRef.value)
  try {
    const data = await getSpecimenTypePieChart({})
    if (data) {
      specimenPieChart.setOption({
        tooltip: { trigger: 'item' },
        legend: { orient: 'vertical', left: 'left' },
        series: [
          {
            type: 'pie',
            radius: '60%',
            data: data.series?.[0]?.data ?? data.data ?? [],
            emphasis: {
              itemStyle: { shadowBlur: 10, shadowOffsetX: 0, shadowColor: 'rgba(0, 0, 0, 0.5)' },
            },
          },
        ],
      })
    }
  } catch {
    specimenPieChart.setOption({
      tooltip: { trigger: 'item' },
      legend: { orient: 'vertical', left: 'left' },
      series: [{ type: 'pie', radius: '60%', data: [] }],
    })
  }
}

const initDeptBarChart = async () => {
  if (!deptBarRef.value) return
  deptBarChart = echarts.init(deptBarRef.value)
  try {
    const data = await getWorkloadTrendChart({ startDate: getDaysAgo(30), endDate: getToday() })
    if (data) {
      deptBarChart.setOption({
        tooltip: { trigger: 'axis' },
        grid: { left: '3%', right: '4%', bottom: '3%', containLabel: true },
        xAxis: { type: 'category', data: data.xAxis?.data ?? data.categories ?? [] },
        yAxis: { type: 'value' },
        series: data.series ?? [{ type: 'bar', data: [] }],
      })
    }
  } catch {
    deptBarChart.setOption({
      tooltip: { trigger: 'axis' },
      grid: { left: '3%', right: '4%', bottom: '3%', containLabel: true },
      xAxis: { type: 'category', data: [] },
      yAxis: { type: 'value' },
      series: [{ type: 'bar', data: [] }],
    })
  }
}

function getToday(): string {
  return new Date().toISOString().split('T')[0]
}

function getDaysAgo(days: number): string {
  const d = new Date()
  d.setDate(d.getDate() - days)
  return d.toISOString().split('T')[0]
}

const handleResize = () => {
  trendChart?.resize()
  specimenPieChart?.resize()
  deptBarChart?.resize()
}

onMounted(async () => {
  await fetchOverview()
  await nextTick()
  initTrendChart()
  initSpecimenPieChart()
  initDeptBarChart()
  window.addEventListener('resize', handleResize)
})

onBeforeUnmount(() => {
  window.removeEventListener('resize', handleResize)
  trendChart?.dispose()
  specimenPieChart?.dispose()
  deptBarChart?.dispose()
})
</script>

<style scoped>
.dashboard {
  width: 100%;
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
  gap: 20px;
}

.stat-icon {
  width: 60px;
  height: 60px;
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
  font-size: 28px;
  font-weight: bold;
  color: #303133;
}

.stat-label {
  font-size: 14px;
  color: #909399;
  margin-top: 5px;
}

.chart-container {
  height: 300px;
  width: 100%;
}
</style>
