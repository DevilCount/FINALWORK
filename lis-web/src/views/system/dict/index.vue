<template>
  <div class="page-container">
    <el-row :gutter="20">
      <el-col :span="10">
        <el-card class="type-card">
          <template #header>
            <div class="card-header">
              <span>字典类型</span>
              <el-button type="primary" icon="Plus" @click="handleAddType">新增</el-button>
            </div>
          </template>
          
          <el-form :model="typeQueryParams" inline class="search-form">
            <el-form-item>
              <el-input
                v-model="typeQueryParams.name"
                placeholder="字典名称"
                clearable
                @keyup.enter="handleTypeQuery"
              />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" icon="Search" @click="handleTypeQuery">搜索</el-button>
            </el-form-item>
          </el-form>

          <el-table
            v-loading="typeLoading"
            :data="typeList"
            border
            stripe
            highlight-current-row
            @current-change="handleTypeSelect"
          >
            <el-table-column prop="name" label="字典名称" min-width="120" />
            <el-table-column prop="code" label="字典编码" min-width="120" />
            <el-table-column label="状态" width="80" align="center">
              <template #default="{ row }">
                <el-tag v-if="row.status === 'normal'" type="success">正常</el-tag>
                <el-tag v-else type="danger">禁用</el-tag>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="120" align="center">
              <template #default="{ row }">
                <el-button link type="primary" icon="Edit" @click.stop="handleEditType(row)">编辑</el-button>
                <el-button link type="danger" icon="Delete" @click.stop="handleDeleteType(row)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>

          <div class="pagination-container">
            <el-pagination
              v-model:current-page="typeQueryParams.pageNum"
              v-model:page-size="typeQueryParams.pageSize"
              :page-sizes="[10, 20, 50]"
              :total="typeTotal"
              layout="total, prev, pager, next"
              small
              @size-change="handleTypeQuery"
              @current-change="handleTypeQuery"
            />
          </div>
        </el-card>
      </el-col>
      
      <el-col :span="14">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>字典数据{{ currentType ? ` - ${currentType.name}` : '' }}</span>
              <el-button
                type="primary"
                icon="Plus"
                :disabled="!currentType"
                @click="handleAddData"
              >
                新增
              </el-button>
            </div>
          </template>
          
          <el-empty v-if="!currentType" description="请选择左侧字典类型" />
          
          <template v-else>
            <el-form :model="dataQueryParams" inline class="search-form">
              <el-form-item>
                <el-input
                  v-model="dataQueryParams.label"
                  placeholder="字典标签"
                  clearable
                  @keyup.enter="handleDataQuery"
                />
              </el-form-item>
              <el-form-item>
                <el-button type="primary" icon="Search" @click="handleDataQuery">搜索</el-button>
              </el-form-item>
            </el-form>

            <el-table
              v-loading="dataLoading"
              :data="dataList"
              border
              stripe
            >
              <el-table-column prop="label" label="字典标签" min-width="120" />
              <el-table-column prop="value" label="字典值" min-width="100" />
              <el-table-column prop="sort" label="排序" width="80" align="center" />
              <el-table-column label="状态" width="80" align="center">
                <template #default="{ row }">
                  <el-tag v-if="row.status === 'normal'" type="success">正常</el-tag>
                  <el-tag v-else type="danger">禁用</el-tag>
                </template>
              </el-table-column>
              <el-table-column prop="remark" label="备注" min-width="120" show-overflow-tooltip />
              <el-table-column label="操作" width="120" align="center">
                <template #default="{ row }">
                  <el-button link type="primary" icon="Edit" @click="handleEditData(row)">编辑</el-button>
                  <el-button link type="danger" icon="Delete" @click="handleDeleteData(row)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>

            <div class="pagination-container">
              <el-pagination
                v-model:current-page="dataQueryParams.pageNum"
                v-model:page-size="dataQueryParams.pageSize"
                :page-sizes="[10, 20, 50]"
                :total="dataTotal"
                layout="total, prev, pager, next"
                small
                @size-change="handleDataQuery"
                @current-change="handleDataQuery"
              />
            </div>
          </template>
        </el-card>
      </el-col>
    </el-row>

    <DictTypeFormDialog
      v-model:visible="typeFormVisible"
      :type-id="currentTypeId"
      @success="handleTypeQuery"
    />

    <DictDataFormDialog
      v-model:visible="dataFormVisible"
      :data-id="currentDataId"
      :type-id="currentType?.id"
      @success="handleDataQuery"
    />
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import {
  getDictTypeList,
  deleteDictType,
  type DictType,
  type DictTypeQuery,
} from '@/api/system/dict'
import {
  getDictDataList,
  deleteDictData,
  type DictData,
  type DictDataQuery,
} from '@/api/system/dict'
import DictTypeFormDialog from './components/DictTypeFormDialog.vue'
import DictDataFormDialog from './components/DictDataFormDialog.vue'

const typeLoading = ref(false)
const typeList = ref<DictType[]>([])
const typeTotal = ref(0)
const typeFormVisible = ref(false)
const currentTypeId = ref<number | undefined>(undefined)
const currentType = ref<DictType | null>(null)

const dataLoading = ref(false)
const dataList = ref<DictData[]>([])
const dataTotal = ref(0)
const dataFormVisible = ref(false)
const currentDataId = ref<number | undefined>(undefined)

const typeQueryParams = reactive<DictTypeQuery>({
  pageNum: 1,
  pageSize: 10,
  name: '',
})

const dataQueryParams = reactive<DictDataQuery>({
  pageNum: 1,
  pageSize: 10,
  typeId: undefined,
  label: '',
})

const loadTypeList = async () => {
  typeLoading.value = true
  try {
    const result = await getDictTypeList(typeQueryParams)
    typeList.value = result.records
    typeTotal.value = result.total
  } catch (error) {
    console.error('加载字典类型列表失败', error)
  } finally {
    typeLoading.value = false
  }
}

const loadDataList = async () => {
  if (!currentType.value) return
  dataLoading.value = true
  dataQueryParams.typeId = currentType.value.id
  try {
    const result = await getDictDataList(dataQueryParams)
    dataList.value = result.records
    dataTotal.value = result.total
  } catch (error) {
    console.error('加载字典数据列表失败', error)
  } finally {
    dataLoading.value = false
  }
}

const handleTypeQuery = () => {
  loadTypeList()
}

const handleDataQuery = () => {
  loadDataList()
}

const handleTypeSelect = (row: DictType | null) => {
  currentType.value = row
  if (row) {
    dataQueryParams.typeId = row.id
    dataQueryParams.pageNum = 1
    loadDataList()
  } else {
    dataList.value = []
    dataTotal.value = 0
  }
}

const handleAddType = () => {
  currentTypeId.value = undefined
  typeFormVisible.value = true
}

const handleEditType = (row: DictType) => {
  currentTypeId.value = row.id
  typeFormVisible.value = true
}

const handleDeleteType = async (row: DictType) => {
  try {
    await ElMessageBox.confirm(`确定要删除字典类型"${row.name}"吗？`, '提示', {
      type: 'warning',
    })
    await deleteDictType(row.id)
    ElMessage.success('删除成功')
    if (currentType.value?.id === row.id) {
      currentType.value = null
      dataList.value = []
    }
    handleTypeQuery()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('删除字典类型失败', error)
    }
  }
}

const handleAddData = () => {
  currentDataId.value = undefined
  dataFormVisible.value = true
}

const handleEditData = (row: DictData) => {
  currentDataId.value = row.id
  dataFormVisible.value = true
}

const handleDeleteData = async (row: DictData) => {
  try {
    await ElMessageBox.confirm(`确定要删除字典数据"${row.label}"吗？`, '提示', {
      type: 'warning',
    })
    await deleteDictData(row.id)
    ElMessage.success('删除成功')
    handleDataQuery()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('删除字典数据失败', error)
    }
  }
}

onMounted(() => {
  loadTypeList()
})
</script>

<style lang="scss" scoped>
.page-container {
  padding: 0;
}

.type-card {
  height: calc(100vh - 180px);
  display: flex;
  flex-direction: column;

  :deep(.el-card__body) {
    flex: 1;
    overflow: hidden;
    display: flex;
    flex-direction: column;
  }
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.search-form {
  margin-bottom: 12px;
}

.pagination-container {
  margin-top: 12px;
  display: flex;
  justify-content: flex-end;
}

.el-table {
  flex: 1;
  overflow: auto;
}
</style>
