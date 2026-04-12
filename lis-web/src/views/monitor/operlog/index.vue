<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>操作日志</span>
          <el-button type="danger" @click="handleClean">清空日志</el-button>
        </div>
      </template>

      <el-form :model="queryParams" inline class="search-form">
        <el-form-item label="操作模块">
          <el-input v-model="queryParams.title" placeholder="请输入操作模块" clearable @keyup.enter="handleSearch" />
        </el-form-item>
        <el-form-item label="操作人">
          <el-input v-model="queryParams.operName" placeholder="请输入操作人" clearable @keyup.enter="handleSearch" />
        </el-form-item>
        <el-form-item label="操作时间">
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
        <el-table-column prop="title" label="操作模块" width="150" />
        <el-table-column prop="businessType" label="操作类型" width="100">
          <template #default="{ row }">
            <el-tag :type="getBusinessTypeTag(row.businessType)">{{ getBusinessTypeLabel(row.businessType) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="operName" label="操作人" width="100" />
        <el-table-column prop="operUrl" label="请求地址" min-width="200" show-overflow-tooltip />
        <el-table-column prop="operMethod" label="请求方式" width="100" />
        <el-table-column prop="operIp" label="操作IP" width="130" />
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'danger'">{{ row.status === 1 ? '成功' : '失败' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="operTime" label="操作时间" width="160" />
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

    <el-dialog v-model="detailVisible" title="操作日志详情" width="700px">
      <el-descriptions :column="2" border v-if="currentRow">
        <el-descriptions-item label="操作模块">{{ currentRow.title }}</el-descriptions-item>
        <el-descriptions-item label="操作类型">{{ getBusinessTypeLabel(currentRow.businessType) }}</el-descriptions-item>
        <el-descriptions-item label="操作人">{{ currentRow.operName }}</el-descriptions-item>
        <el-descriptions-item label="操作IP">{{ currentRow.operIp }}</el-descriptions-item>
        <el-descriptions-item label="请求地址" :span="2">{{ currentRow.operUrl }}</el-descriptions-item>
        <el-descriptions-item label="请求方式">{{ currentRow.operMethod }}</el-descriptions-item>
        <el-descriptions-item label="操作状态">{{ currentRow.status === 1 ? '成功' : '失败' }}</el-descriptions-item>
        <el-descriptions-item label="操作时间" :span="2">{{ currentRow.operTime }}</el-descriptions-item>
        <el-descriptions-item label="请求参数" :span="2">
          <pre class="json-content">{{ currentRow.operParam }}</pre>
        </el-descriptions-item>
        <el-descriptions-item label="返回结果" :span="2">
          <pre class="json-content">{{ currentRow.operResult }}</pre>
        </el-descriptions-item>
      </el-descriptions>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Search, Refresh } from '@element-plus/icons-vue'
import { getOperLogList, getOperLogDetail, cleanOperLog } from '@/api/system/operlog'

const loading = ref(false)
const tableData = ref<any[]>([])
const total = ref(0)
const timeRange = ref<[string, string] | null>(null)
const detailVisible = ref(false)
const currentRow = ref<any>(null)

const queryParams = reactive({
  pageNum: 1,
  pageSize: 10,
  title: '',
  operName: '',
  startTime: '',
  endTime: '',
})

const getBusinessTypeLabel = (type: number) => {
  const map: Record<number, string> = { 1: '新增', 2: '修改', 3: '删除', 4: '查询', 5: '导出', 6: '导入', 7: '其他' }
  return map[type] || '其他'
}

const getBusinessTypeTag = (type: number): 'success' | 'warning' | 'danger' | 'info' | undefined => {
  const map: Record<number, 'success' | 'warning' | 'danger' | 'info' | undefined> = { 1: 'success', 2: 'warning', 3: 'danger', 4: undefined, 5: 'info', 6: 'info', 7: 'info' }
  return map[type] || 'info'
}

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
    const res = await getOperLogList(queryParams)
    tableData.value = res.records ?? []
    total.value = res.total
  } catch (error) {
    console.error('获取操作日志失败:', error)
  } finally {
    loading.value = false
  }
}

const handleSearch = () => { queryParams.pageNum = 1; fetchData() }
const handleReset = () => { queryParams.title = ''; queryParams.operName = ''; timeRange.value = null; queryParams.startTime = ''; queryParams.endTime = ''; handleSearch() }
const handleSizeChange = (size: number) => { queryParams.pageSize = size; fetchData() }
const handleCurrentChange = (page: number) => { queryParams.pageNum = page; fetchData() }

const handleDetail = async (row: any) => {
  try {
    currentRow.value = await getOperLogDetail(row.id)
    detailVisible.value = true
  } catch { currentRow.value = row; detailVisible.value = true }
}

const handleClean = async () => {
  try {
    await ElMessageBox.confirm('确认清空所有操作日志？此操作不可恢复！', '警告', { type: 'warning' })
    await cleanOperLog()
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
.json-content { white-space: pre-wrap; word-break: break-all; font-size: 12px; max-height: 200px; overflow-y: auto; background: var(--el-fill-color-light); padding: 8px; border-radius: 4px; }
</style>
