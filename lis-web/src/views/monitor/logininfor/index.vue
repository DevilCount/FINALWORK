<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>登录日志</span>
          <el-button type="danger" @click="handleClean">清空日志</el-button>
        </div>
      </template>

      <el-form :model="queryParams" inline class="search-form">
        <el-form-item label="用户名">
          <el-input v-model="queryParams.username" placeholder="请输入用户名" clearable @keyup.enter="handleSearch" />
        </el-form-item>
        <el-form-item label="IP地址">
          <el-input v-model="queryParams.ipaddr" placeholder="请输入IP地址" clearable @keyup.enter="handleSearch" />
        </el-form-item>
        <el-form-item label="登录时间">
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
        <el-table-column prop="username" label="用户名" width="120" />
        <el-table-column prop="ipaddr" label="登录IP" width="140" />
        <el-table-column prop="loginLocation" label="登录地点" width="150" />
        <el-table-column prop="browser" label="浏览器" width="120" />
        <el-table-column prop="os" label="操作系统" width="120" />
        <el-table-column prop="status" label="登录状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'danger'">{{ row.status === 1 ? '成功' : '失败' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="msg" label="提示消息" min-width="150" />
        <el-table-column prop="loginTime" label="登录时间" width="160" />
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
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Search, Refresh } from '@element-plus/icons-vue'
import { getLoginLogList, cleanLoginLog } from '@/api/system/loginlog'

const loading = ref(false)
const tableData = ref<any[]>([])
const total = ref(0)
const timeRange = ref<[string, string] | null>(null)

const queryParams = reactive({
  pageNum: 1,
  pageSize: 10,
  username: '',
  ipaddr: '',
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
    const res = await getLoginLogList(queryParams)
    tableData.value = res.records ?? []
    total.value = res.total
  } catch (error) {
    console.error('获取登录日志失败:', error)
  } finally {
    loading.value = false
  }
}

const handleSearch = () => { queryParams.pageNum = 1; fetchData() }
const handleReset = () => { queryParams.username = ''; queryParams.ipaddr = ''; timeRange.value = null; queryParams.startTime = ''; queryParams.endTime = ''; handleSearch() }
const handleSizeChange = (size: number) => { queryParams.pageSize = size; fetchData() }
const handleCurrentChange = (page: number) => { queryParams.pageNum = page; fetchData() }

const handleClean = async () => {
  try {
    await ElMessageBox.confirm('确认清空所有登录日志？此操作不可恢复！', '警告', { type: 'warning' })
    await cleanLoginLog()
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
</style>
