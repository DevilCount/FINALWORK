<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>危急值处理</span>
          <el-tag type="danger" v-if="pendingCount > 0">待处理: {{ pendingCount }}</el-tag>
        </div>
      </template>

      <el-form :model="queryParams" inline class="search-form">
        <el-form-item label="标本编号">
          <el-input v-model="queryParams.specimenNo" placeholder="请输入标本编号" clearable @keyup.enter="handleSearch" />
        </el-form-item>
        <el-form-item label="患者姓名">
          <el-input v-model="queryParams.patientName" placeholder="请输入患者姓名" clearable @keyup.enter="handleSearch" />
        </el-form-item>
        <el-form-item label="危急类型">
          <el-select v-model="queryParams.panicType" placeholder="请选择" clearable style="width: 120px">
            <el-option label="偏高" value="high" />
            <el-option label="偏低" value="low" />
          </el-select>
        </el-form-item>
        <el-form-item label="处理状态">
          <el-select v-model="queryParams.handleStatus" placeholder="请选择" clearable style="width: 120px">
            <el-option label="待处理" :value="0" />
            <el-option label="已通知" :value="1" />
            <el-option label="已处理" :value="2" />
            <el-option label="已确认" :value="3" />
          </el-select>
        </el-form-item>
        <el-form-item label="报告时间">
          <el-date-picker
            v-model="reportTimeRange"
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
        <el-table-column prop="specimenNo" label="标本编号" width="150" />
        <el-table-column prop="patientName" label="患者姓名" width="100" />
        <el-table-column prop="patientGender" label="性别" width="60">
          <template #default="{ row }">
            {{ row.patientGender === 'male' ? '男' : '女' }}
          </template>
        </el-table-column>
        <el-table-column prop="deptName" label="科室" width="120" />
        <el-table-column prop="bedNo" label="床号" width="80" />
        <el-table-column prop="itemName" label="检验项目" width="120" />
        <el-table-column label="检验结果" width="120">
          <template #default="{ row }">
            <span class="critical-value">{{ row.resultValue }} {{ row.unit }}</span>
          </template>
        </el-table-column>
        <el-table-column label="危急范围" width="120">
          <template #default="{ row }">
            {{ row.panicLow }} ~ {{ row.panicHigh }}
          </template>
        </el-table-column>
        <el-table-column prop="panicTypeName" label="危急类型" width="100">
          <template #default="{ row }">
            <el-tag :type="row.panicType === 'high' ? 'danger' : 'warning'" effect="dark">
              {{ row.panicTypeName }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="reportTime" label="报告时间" width="160" />
        <el-table-column prop="handleStatusName" label="处理状态" width="100">
          <template #default="{ row }">
            <el-tag :type="getHandleStatusType(row.handleStatus)">
              {{ row.handleStatusName }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" link size="small" @click="handleDetail(row)">详情</el-button>
            <el-button v-if="row.handleStatus === 0" type="warning" link size="small" @click="handleNotify(row)">通知</el-button>
            <el-button v-if="[0, 1].includes(row.handleStatus)" type="success" link size="small" @click="handleHandle(row)">处理</el-button>
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

    <el-dialog v-model="detailDialogVisible" title="危急值详情" width="800px">
      <template v-if="currentCriticalValue">
        <el-descriptions title="基本信息" :column="3" border>
          <el-descriptions-item label="标本编号">{{ currentCriticalValue.specimenNo }}</el-descriptions-item>
          <el-descriptions-item label="报告编号">{{ currentCriticalValue.reportNo }}</el-descriptions-item>
          <el-descriptions-item label="处理状态">
            <el-tag :type="getHandleStatusType(currentCriticalValue.handleStatus)">
              {{ currentCriticalValue.handleStatusName }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="患者姓名">{{ currentCriticalValue.patientName }}</el-descriptions-item>
          <el-descriptions-item label="性别">{{ currentCriticalValue.patientGender === 'male' ? '男' : '女' }}</el-descriptions-item>
          <el-descriptions-item label="年龄">{{ currentCriticalValue.patientAge }}</el-descriptions-item>
          <el-descriptions-item label="科室">{{ currentCriticalValue.deptName }}</el-descriptions-item>
          <el-descriptions-item label="床号">{{ currentCriticalValue.bedNo || '-' }}</el-descriptions-item>
          <el-descriptions-item label="危急类型">
            <el-tag :type="currentCriticalValue.panicType === 'high' ? 'danger' : 'warning'" effect="dark">
              {{ currentCriticalValue.panicTypeName }}
            </el-tag>
          </el-descriptions-item>
        </el-descriptions>

        <div class="section-title">危急值信息</div>
        <el-descriptions :column="2" border>
          <el-descriptions-item label="检验项目">{{ currentCriticalValue.itemName }}</el-descriptions-item>
          <el-descriptions-item label="项目编码">{{ currentCriticalValue.itemCode }}</el-descriptions-item>
          <el-descriptions-item label="检验结果">
            <span class="critical-value">{{ currentCriticalValue.resultValue }} {{ currentCriticalValue.unit }}</span>
          </el-descriptions-item>
          <el-descriptions-item label="危急范围">{{ currentCriticalValue.panicLow }} ~ {{ currentCriticalValue.panicHigh }}</el-descriptions-item>
          <el-descriptions-item label="报告时间">{{ currentCriticalValue.reportTime }}</el-descriptions-item>
          <el-descriptions-item label="处理时间">{{ currentCriticalValue.handleTime || '-' }}</el-descriptions-item>
        </el-descriptions>

        <div class="section-title">处理记录</div>
        <el-timeline v-if="handleRecords.length > 0">
          <el-timeline-item
            v-for="record in handleRecords"
            :key="record.id"
            :timestamp="record.operationTime"
            placement="top"
          >
            <el-card>
              <div class="record-content">
                <el-tag>{{ record.operationTypeName }}</el-tag>
                <span class="operator">{{ record.operatorName }}</span>
                <p class="desc">{{ record.operationDesc }}</p>
              </div>
            </el-card>
          </el-timeline-item>
        </el-timeline>
        <el-empty v-else description="暂无处理记录" />
      </template>
    </el-dialog>

    <el-dialog v-model="notifyDialogVisible" title="危急值通知" width="500px">
      <el-form :model="notifyForm" label-width="100px">
        <el-form-item label="通知医生" required>
          <el-input v-model="notifyForm.receiveUserName" placeholder="请输入通知医生姓名" />
        </el-form-item>
        <el-form-item label="通知时间" required>
          <el-date-picker
            v-model="notifyForm.notifyTime"
            type="datetime"
            placeholder="选择通知时间"
            value-format="YYYY-MM-DD HH:mm:ss"
            style="width: 100%"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="notifyDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="confirmNotify">确认通知</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="handleDialogVisible" title="危急值处理" width="500px">
      <el-form :model="handleForm" label-width="100px">
        <el-form-item label="通知医生" required>
          <el-input v-model="handleForm.receiveUserName" placeholder="请输入通知医生姓名" />
        </el-form-item>
        <el-form-item label="通知时间" required>
          <el-date-picker
            v-model="handleForm.notifyTime"
            type="datetime"
            placeholder="选择通知时间"
            value-format="YYYY-MM-DD HH:mm:ss"
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="处理说明" required>
          <el-input v-model="handleForm.handleResult" type="textarea" :rows="3" placeholder="请输入处理说明" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="handleDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="confirmHandle">确认处理</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, computed } from 'vue'
import { ElMessage } from 'element-plus'
import { Search, Refresh } from '@element-plus/icons-vue'
import type { CriticalValue, CriticalValueQueryParams, CriticalValueHandleForm, CriticalValueRecord } from '@/types/report'
import { getCriticalValueList, getCriticalValueDetail, handleCriticalValue, notifyCriticalValue, getCriticalValueRecords } from '@/api/report'

const loading = ref(false)
const tableData = ref<CriticalValue[]>([])
const total = ref(0)

const reportTimeRange = ref<[string, string] | null>(null)

const queryParams = reactive<CriticalValueQueryParams>({
  pageNum: 1,
  pageSize: 10,
  specimenNo: '',
  patientName: '',
  panicType: '',
  handleStatus: '',
  reportTimeStart: '',
  reportTimeEnd: '',
})

const pendingCount = computed(() => {
  return tableData.value.filter(item => item.handleStatus === 0).length
})

const detailDialogVisible = ref(false)
const notifyDialogVisible = ref(false)
const handleDialogVisible = ref(false)
const currentCriticalValue = ref<CriticalValue | null>(null)
const handleRecords = ref<CriticalValueRecord[]>([])

const notifyForm = reactive({
  id: '',
  receiveUserName: '',
  notifyTime: '',
})

const handleForm = reactive<CriticalValueHandleForm>({
  id: '',
  receiveUserName: '',
  notifyTime: '',
  handleResult: '',
})

const getHandleStatusType = (status: number): 'success' | 'warning' | 'danger' => {
  const typeMap: Record<number, 'success' | 'warning' | 'danger'> = {
    0: 'danger',
    1: 'warning',
    2: 'success',
    3: 'success',
  }
  return typeMap[status] || 'danger'
}

const fetchData = async () => {
  loading.value = true
  try {
    if (reportTimeRange.value) {
      queryParams.reportTimeStart = reportTimeRange.value[0]
      queryParams.reportTimeEnd = reportTimeRange.value[1]
    } else {
      queryParams.reportTimeStart = ''
      queryParams.reportTimeEnd = ''
    }
    const res = await getCriticalValueList(queryParams)
    tableData.value = res.records
    total.value = res.total
  } catch (error) {
    console.error('获取危急值列表失败:', error)
  } finally {
    loading.value = false
  }
}

const handleSearch = () => {
  queryParams.pageNum = 1
  fetchData()
}

const handleReset = () => {
  queryParams.specimenNo = ''
  queryParams.patientName = ''
  queryParams.panicType = ''
  queryParams.handleStatus = ''
  reportTimeRange.value = null
  queryParams.reportTimeStart = ''
  queryParams.reportTimeEnd = ''
  handleSearch()
}

const handleSizeChange = (size: number) => {
  queryParams.pageSize = size
  fetchData()
}

const handleCurrentChange = (page: number) => {
  queryParams.pageNum = page
  fetchData()
}

const handleDetail = async (row: CriticalValue) => {
  try {
    currentCriticalValue.value = await getCriticalValueDetail(row.id)
    handleRecords.value = await getCriticalValueRecords(row.id)
    detailDialogVisible.value = true
  } catch (error) {
    console.error('获取危急值详情失败:', error)
  }
}

const handleNotify = (row: CriticalValue) => {
  notifyForm.id = row.id
  notifyForm.receiveUserName = ''
  notifyForm.notifyTime = new Date().toISOString().replace('T', ' ').slice(0, 19)
  notifyDialogVisible.value = true
}

const confirmNotify = async () => {
  if (!notifyForm.receiveUserName.trim()) {
    ElMessage.warning('请输入通知医生姓名')
    return
  }
  if (!notifyForm.notifyTime) {
    ElMessage.warning('请选择通知时间')
    return
  }

  try {
    await notifyCriticalValue(notifyForm.id, notifyForm.receiveUserName)
    ElMessage.success('通知成功')
    notifyDialogVisible.value = false
    fetchData()
  } catch (error) {
    console.error('通知失败:', error)
  }
}

const handleHandle = (row: CriticalValue) => {
  handleForm.id = row.id
  handleForm.receiveUserName = row.receiveUserName || ''
  handleForm.notifyTime = row.notifyTime || new Date().toISOString().replace('T', ' ').slice(0, 19)
  handleForm.handleResult = ''
  handleDialogVisible.value = true
}

const confirmHandle = async () => {
  if (!handleForm.receiveUserName.trim()) {
    ElMessage.warning('请输入通知医生姓名')
    return
  }
  if (!handleForm.notifyTime) {
    ElMessage.warning('请选择通知时间')
    return
  }
  if (!handleForm.handleResult.trim()) {
    ElMessage.warning('请输入处理说明')
    return
  }

  try {
    await handleCriticalValue(handleForm)
    ElMessage.success('处理成功')
    handleDialogVisible.value = false
    fetchData()
  } catch (error) {
    console.error('处理失败:', error)
  }
}

onMounted(() => {
  fetchData()
})
</script>

<style scoped lang="scss">
.page-container {
  padding: 0;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.search-form {
  margin-bottom: 16px;
}

.pagination-container {
  display: flex;
  justify-content: flex-end;
  margin-top: 16px;
}

.critical-value {
  color: var(--el-color-danger);
  font-weight: 600;
  font-size: 15px;
}

.section-title {
  font-size: 16px;
  font-weight: 600;
  margin: 20px 0 16px;
  padding-left: 10px;
  border-left: 3px solid var(--el-color-primary);
}

.record-content {
  .operator {
    margin-left: 12px;
    color: var(--el-text-color-secondary);
  }

  .desc {
    margin-top: 8px;
    color: var(--el-text-color-regular);
  }
}
</style>
