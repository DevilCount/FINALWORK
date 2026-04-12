<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>接口日志</span>
        </div>
      </template>

      <el-form :model="queryParams" inline class="search-form">
        <el-form-item label="接口编码">
          <el-input v-model="queryParams.interfaceCode" placeholder="请输入接口编码" clearable @keyup.enter="handleSearch" />
        </el-form-item>
        <el-form-item label="方向">
          <el-select v-model="queryParams.direction" placeholder="请选择" clearable style="width: 120px">
            <el-option label="发送" value="send" />
            <el-option label="接收" value="receive" />
          </el-select>
        </el-form-item>
        <el-form-item label="处理状态">
          <el-select v-model="queryParams.processStatus" placeholder="请选择" clearable style="width: 120px">
            <el-option label="待处理" value="pending" />
            <el-option label="处理中" value="processing" />
            <el-option label="已处理" value="processed" />
            <el-option label="处理失败" value="failed" />
          </el-select>
        </el-form-item>
        <el-form-item label="时间">
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
        <el-table-column prop="interfaceCode" label="接口编码" width="120" />
        <el-table-column prop="messageType" label="消息类型" width="120" />
        <el-table-column prop="triggerEvent" label="触发事件" width="100" />
        <el-table-column prop="direction" label="方向" width="80">
          <template #default="{ row }">
            <el-tag :type="row.direction === 'send' ? 'success' : 'primary'" size="small">{{ row.direction === 'send' ? '发送' : '接收' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="messageControlId" label="消息控制ID" width="150" />
        <el-table-column prop="patientName" label="患者" width="100" />
        <el-table-column prop="processStatus" label="处理状态" width="100">
          <template #default="{ row }">
            <el-tag :type="getProcessStatusTag(row.processStatus)" size="small">{{ getProcessStatusLabel(row.processStatus) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="ackStatus" label="ACK状态" width="100">
          <template #default="{ row }">
            <el-tag v-if="row.ackStatus" :type="row.ackStatus === 'AA' ? 'success' : row.ackStatus === 'AE' ? 'danger' : 'info'" size="small">{{ row.ackStatus }}</el-tag>
            <span v-else>-</span>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="160" />
        <el-table-column label="操作" width="120" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" link size="small" @click="handleDetail(row)">详情</el-button>
            <el-button v-if="row.processStatus === 'failed'" type="warning" link size="small" @click="handleReprocess(row)">重试</el-button>
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

    <el-dialog v-model="detailVisible" title="接口日志详情" width="800px">
      <el-descriptions :column="2" border v-if="currentRow">
        <el-descriptions-item label="接口编码">{{ currentRow.interfaceCode }}</el-descriptions-item>
        <el-descriptions-item label="消息类型">{{ currentRow.messageType }}^{{ currentRow.triggerEvent }}</el-descriptions-item>
        <el-descriptions-item label="方向">{{ currentRow.direction === 'send' ? '发送' : '接收' }}</el-descriptions-item>
        <el-descriptions-item label="消息控制ID">{{ currentRow.messageControlId }}</el-descriptions-item>
        <el-descriptions-item label="患者ID">{{ currentRow.patientId }}</el-descriptions-item>
        <el-descriptions-item label="患者姓名">{{ currentRow.patientName }}</el-descriptions-item>
        <el-descriptions-item label="处理状态">{{ getProcessStatusLabel(currentRow.processStatus) }}</el-descriptions-item>
        <el-descriptions-item label="ACK状态">{{ currentRow.ackStatus || '-' }}</el-descriptions-item>
        <el-descriptions-item label="创建时间" :span="2">{{ currentRow.createTime }}</el-descriptions-item>
        <el-descriptions-item label="原始消息" :span="2">
          <pre class="hl7-content">{{ currentRow.rawMessage }}</pre>
        </el-descriptions-item>
        <el-descriptions-item label="错误信息" :span="2" v-if="currentRow.errorMessage">
          <pre class="error-content">{{ currentRow.errorMessage }}</pre>
        </el-descriptions-item>
      </el-descriptions>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Search, Refresh } from '@element-plus/icons-vue'
import { getInterfaceLogList, getInterfaceLogDetail, reprocessInterfaceLog } from '@/api/system/interfacelog'

const loading = ref(false)
const tableData = ref<any[]>([])
const total = ref(0)
const timeRange = ref<[string, string] | null>(null)
const detailVisible = ref(false)
const currentRow = ref<any>(null)

const queryParams = reactive({
  pageNum: 1,
  pageSize: 10,
  interfaceCode: '',
  direction: '',
  processStatus: '',
  startTime: '',
  endTime: '',
})

const getProcessStatusLabel = (status: string) => {
  const map: Record<string, string> = { pending: '待处理', processing: '处理中', processed: '已处理', failed: '处理失败' }
  return map[status] || status
}

const getProcessStatusTag = (status: string): 'success' | 'warning' | 'danger' | 'info' | undefined => {
  const map: Record<string, 'success' | 'warning' | 'danger' | 'info' | undefined> = { pending: 'info', processing: undefined, processed: 'success', failed: 'danger' }
  return map[status] || 'info'
}

const fetchData = async () => {
  loading.value = true
  try {
    if (timeRange.value) {
      queryParams.startTime = timeRange.value[0] + 'T00:00:00'
      queryParams.endTime = timeRange.value[1] + 'T23:59:59'
    } else {
      queryParams.startTime = ''
      queryParams.endTime = ''
    }
    const res = await getInterfaceLogList(queryParams)
    tableData.value = res.records ?? []
    total.value = res.total
  } catch (error) {
    console.error('获取接口日志失败:', error)
  } finally {
    loading.value = false
  }
}

const handleSearch = () => { queryParams.pageNum = 1; fetchData() }
const handleReset = () => { queryParams.interfaceCode = ''; queryParams.direction = ''; queryParams.processStatus = ''; timeRange.value = null; queryParams.startTime = ''; queryParams.endTime = ''; handleSearch() }
const handleSizeChange = (size: number) => { queryParams.pageSize = size; fetchData() }
const handleCurrentChange = (page: number) => { queryParams.pageNum = page; fetchData() }

const handleDetail = async (row: any) => {
  try {
    currentRow.value = await getInterfaceLogDetail(row.id)
    detailVisible.value = true
  } catch { currentRow.value = row; detailVisible.value = true }
}

const handleReprocess = async (row: any) => {
  try {
    await ElMessageBox.confirm('确认重新处理该消息？', '提示', { type: 'warning' })
    await reprocessInterfaceLog(row.id)
    ElMessage.success('已重新提交处理')
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
.hl7-content { white-space: pre-wrap; word-break: break-all; font-size: 12px; max-height: 300px; overflow-y: auto; background: var(--el-fill-color-light); padding: 8px; border-radius: 4px; }
.error-content { white-space: pre-wrap; word-break: break-all; font-size: 12px; max-height: 150px; overflow-y: auto; background: #fef0f0; padding: 8px; border-radius: 4px; color: #f56c6c; }
</style>
