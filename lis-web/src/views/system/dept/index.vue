<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>部门管理</span>
          <el-button type="primary" icon="Plus" @click="handleAdd">新增部门</el-button>
        </div>
      </template>
      
      <el-form :model="queryParams" inline class="search-form">
        <el-form-item label="部门名称">
          <el-input
            v-model="queryParams.name"
            placeholder="请输入部门名称"
            clearable
            @keyup.enter="handleQuery"
          />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
            <el-option label="正常" value="normal" />
            <el-option label="禁用" value="disable" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
          <el-button icon="Refresh" @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>

      <el-table
        v-loading="loading"
        :data="deptList"
        row-key="id"
        border
        :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
        default-expand-all
      >
        <el-table-column prop="name" label="部门名称" min-width="160" />
        <el-table-column prop="code" label="部门编码" min-width="120" />
        <el-table-column prop="leader" label="负责人" width="100" />
        <el-table-column prop="phone" label="联系电话" width="120" />
        <el-table-column prop="email" label="邮箱" min-width="160" />
        <el-table-column prop="sort" label="排序" width="80" align="center" />
        <el-table-column label="状态" width="80" align="center">
          <template #default="{ row }">
            <el-tag v-if="row.status === 'normal'" type="success">正常</el-tag>
            <el-tag v-else type="danger">禁用</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="170" />
        <el-table-column label="操作" width="180" fixed="right" align="center">
          <template #default="{ row }">
            <el-button link type="primary" icon="Edit" @click="handleEdit(row)">编辑</el-button>
            <el-button link type="primary" icon="Plus" @click="handleAddChild(row)">新增</el-button>
            <el-button link type="danger" icon="Delete" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <DeptFormDialog
      v-model:visible="formDialogVisible"
      :dept-id="currentDeptId"
      :parent-id="currentParentId"
      @success="handleQuery"
    />
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import {
  getDeptList,
  deleteDept,
  type Dept,
  type DeptQuery,
} from '@/api/system/dept'
import DeptFormDialog from './components/DeptFormDialog.vue'

const loading = ref(false)
const deptList = ref<Dept[]>([])
const formDialogVisible = ref(false)
const currentDeptId = ref<number | undefined>(undefined)
const currentParentId = ref<number>(0)

const queryParams = reactive<DeptQuery>({
  name: '',
  status: '',
})

const loadDeptList = async () => {
  loading.value = true
  try {
    deptList.value = await getDeptList(queryParams)
  } catch (error) {
    console.error('加载部门列表失败', error)
  } finally {
    loading.value = false
  }
}

const handleQuery = () => {
  loadDeptList()
}

const handleReset = () => {
  queryParams.name = ''
  queryParams.status = ''
  handleQuery()
}

const handleAdd = () => {
  currentDeptId.value = undefined
  currentParentId.value = 0
  formDialogVisible.value = true
}

const handleAddChild = (row: Dept) => {
  currentDeptId.value = undefined
  currentParentId.value = row.id
  formDialogVisible.value = true
}

const handleEdit = (row: Dept) => {
  currentDeptId.value = row.id
  currentParentId.value = row.parentId
  formDialogVisible.value = true
}

const handleDelete = async (row: Dept) => {
  if (row.children && row.children.length > 0) {
    ElMessage.warning('存在子部门，不能删除')
    return
  }
  try {
    await ElMessageBox.confirm(`确定要删除部门"${row.name}"吗？`, '提示', {
      type: 'warning',
    })
    await deleteDept(row.id)
    ElMessage.success('删除成功')
    handleQuery()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('删除部门失败', error)
    }
  }
}

onMounted(() => {
  loadDeptList()
})
</script>

<style lang="scss" scoped>
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
</style>
