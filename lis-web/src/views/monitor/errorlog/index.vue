<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>系统错误日志</span>
          <el-button type="danger" @click="handleClean">清空日志</el-button>
        </div>
      </template>

      <el-form :model="queryParams" inline class="search-form">
        <el-form-item label="异常类名">
          <el-input v-model="queryParams.className" placeholder="请输入异常类名" clearable @keyup.enter="handleSearch" />
        </el-form-item>
        <el-form-item label="方法名">
          <el-input v-model="queryParams.methodName" placeholder="请输入方法名" clearable @keyup.enter="handleSearch" />
        </el-form-item>
        <el-form-item label="发生时间">
          <el-date-picker
            v-model="timeRange"
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

      <el-table v-loading="loading" :data="tableData" border stripe>
        <el-table-column prop="className" label="异常类名" min-width="200" show-overflow-tooltip />
        <el-table-column prop="methodName" label="方法名" width="150" show-overflow-tooltip />
        <el-table-column prop="exceptionType" label="异常类型" width="150" show-overflow-tooltip />
        <el-table-column prop="requestUrl" label="请求URL" min-width="200" show-overflow-tooltip />
        <el-table-column prop="operName" label="操作人" width="100" />
        <el-table-column prop="createTime" label="发生时间" width="160" />
        <el-table-column label="操作" width="80" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" link size="small" @click="handleDetail(row)">详情</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination-container">
        <el-pagination
          v-model:current-page="queryParams.pageNum"
          v-model:page-size="queryParams.pageSize"
          :page-sizes="[10, 20, 50, 100]"
          :total="total"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </el-card>

    <el-dialog v-model="detailVisible" title="错误日志详情" width="700px">
      <el-descriptions :column="2" border v-if="currentRow">
        <el-descriptions-item label="异常类名" :span="2">{{ currentRow.className }}</el-descriptions-item>
        <el-descriptions-item label="方法名">{{ currentRow.methodName }}</el-descriptions-item>
        <el-descriptions-item label="异常类型">{{ currentRow.exceptionType }}</el-descriptions-item>
        <el-descriptions-item label="请求URL" :span="2">{{ currentRow.requestUrl }}</el-descriptions-item>
        <el-descriptions-item label="操作人">{{ currentRow.operName }}</el-descriptions-item>
        <el-descriptions-item label="发生时间">{{ currentRow.createTime }}</el-descriptions-item>
        <el-descriptions-item label="异常堆栈" :span="2">
          <pre class="stack-content">{{ currentRow.exceptionStack }}</pre>
        </el-descriptions-item>
      </el-descriptions>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Search, Refresh } from '@element-plus/icons-vue'
import { getErrorLogList, getErrorLogDetail, cleanErrorLog } from '@/api/system/errorlog'

const loading = ref(false)
const tableData = ref<any[]>([])
const total = ref(0)
const timeRange = ref<[string, string] | null>(null)
const detailVisible = ref(false)
const currentRow = ref<any>(null)

const queryParams = reactive({
  pageNum: 1,
  pageSize: 10,
  className: '',
  methodName: '',
  startTime: '',
  endTime: '',
})

const fetchData = async () => {
  loading.value = true
  try {
    if (timeRange.value) {
      queryParams.startTime = timeRange.value[0]
      queryParams.endTime = timeRange.value[1]
    } else {
      queryParams.startTime = ''
      queryParams.endTime = ''
    }
    const res = await getErrorLogList(queryParams)
    tableData.value = res.records ?? []
    total.value = res.total
  } catch (error) {
    console.error('获取错误日志失败:', error)
  } finally {
    loading.value = false
  }
}

const handleSearch = () => { queryParams.pageNum = 1; fetchData() }
const handleReset = () => { queryParams.className = ''; queryParams.methodName = ''; timeRange.value = null; queryParams.startTime = ''; queryParams.endTime = ''; handleSearch() }
const handleSizeChange = (size: number) => { queryParams.pageSize = size; fetchData() }
const handleCurrentChange = (page: number) => { queryParams.pageNum = page; fetchData() }

const handleDetail = async (row: any) => {
  try {
    currentRow.value = await getErrorLogDetail(row.id)
    detailVisible.value = true
  } catch { currentRow.value = row; detailVisible.value = true }
}

const handleClean = async () => {
  try {
    await ElMessageBox.confirm('确认清空所有错误日志？此操作不可恢复！', '警告', { type: 'warning' })
    await cleanErrorLog()
    ElMessage.success('清空成功')
    fetchData()
  } catch (error) { if (error !== 'cancel') console.error(error) }
}

onMounted(() => { fetchData() })
</script>

<style scoped lang="scss">
.page-container { padding: 0; }
.card-header { display: flex; justify-content: space-between; align-items: center; }
.search-form { margin-bottom: 16px; }
.pagination-container { display: flex; justify-content: flex-end; margin-top: 16px; }
.stack-content { white-space: pre-wrap; word-break: break-all; font-size: 12px; max-height: 300px; overflow-y: auto; background: var(--el-fill-color-light); padding: 8px; border-radius: 4px; }
</style>
