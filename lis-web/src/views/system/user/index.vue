<template>
  <div class="page-container">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card class="dept-card">
          <template #header>
            <div class="card-header">
              <span>部门列表</span>
              <el-tooltip content="刷新">
                <el-button link type="primary" icon="Refresh" @click="loadDeptTree" />
              </el-tooltip>
            </div>
          </template>
          <el-input
            v-model="deptFilterText"
            placeholder="搜索部门"
            clearable
            prefix-icon="Search"
            class="dept-filter"
          />
          <el-tree
            ref="deptTreeRef"
            :data="deptTree"
            :props="{ label: 'label', children: 'children' }"
            :filter-node-method="filterDeptNode"
            :expand-on-click-node="false"
            highlight-current
            default-expand-all
            node-key="id"
            @node-click="handleDeptClick"
          />
        </el-card>
      </el-col>
      <el-col :span="18">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>用户管理</span>
              <el-button type="primary" icon="Plus" @click="handleAdd">新增用户</el-button>
            </div>
          </template>
          
          <el-form :model="queryParams" inline class="search-form">
            <el-form-item label="用户名">
              <el-input
                v-model="queryParams.username"
                placeholder="请输入用户名"
                clearable
                @keyup.enter="handleQuery"
              />
            </el-form-item>
            <el-form-item label="姓名">
              <el-input
                v-model="queryParams.realName"
                placeholder="请输入姓名"
                clearable
                @keyup.enter="handleQuery"
              />
            </el-form-item>
            <el-form-item label="手机号">
              <el-input
                v-model="queryParams.phone"
                placeholder="请输入手机号"
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
            :data="userList"
            border
            stripe
            @selection-change="handleSelectionChange"
          >
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column prop="username" label="用户名" min-width="120" />
            <el-table-column prop="realName" label="姓名" min-width="100" />
            <el-table-column prop="deptName" label="部门" min-width="120" />
            <el-table-column prop="phone" label="手机号" min-width="120" />
            <el-table-column prop="email" label="邮箱" min-width="160" />
            <el-table-column label="性别" width="80" align="center">
              <template #default="{ row }">
                <span v-if="row.gender === 'male'">男</span>
                <span v-else-if="row.gender === 'female'">女</span>
                <span v-else>未知</span>
              </template>
            </el-table-column>
            <el-table-column label="状态" width="100" align="center">
              <template #default="{ row }">
                <el-switch
                  v-model="row.status"
                  active-value="normal"
                  inactive-value="disable"
                  @change="handleStatusChange(row)"
                />
              </template>
            </el-table-column>
            <el-table-column prop="createTime" label="创建时间" width="170" />
            <el-table-column label="操作" width="200" fixed="right" align="center">
              <template #default="{ row }">
                <el-button link type="primary" icon="Edit" @click="handleEdit(row)">编辑</el-button>
                <el-button link type="primary" icon="Key" @click="handleResetPwd(row)">重置密码</el-button>
                <el-button link type="danger" icon="Delete" @click="handleDelete(row)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>

          <div class="pagination-container">
            <el-button
              type="danger"
              :disabled="selectedIds.length === 0"
              @click="handleBatchDelete"
            >
              批量删除
            </el-button>
            <el-pagination
              v-model:current-page="queryParams.pageNum"
              v-model:page-size="queryParams.pageSize"
              :page-sizes="[10, 20, 50, 100]"
              :total="total"
              layout="total, sizes, prev, pager, next, jumper"
              @size-change="handleQuery"
              @current-change="handleQuery"
            />
          </div>
        </el-card>
      </el-col>
    </el-row>

    <UserFormDialog
      v-model:visible="formDialogVisible"
      :user-id="currentUserId"
      :dept-id="queryParams.deptId"
      @success="handleQuery"
    />
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, watch } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import type { ElTree } from 'element-plus'
import {
  getUserList,
  deleteUser,
  batchDeleteUsers,
  updateUserStatus,
  resetUserPassword,
  type User,
  type UserQuery,
} from '@/api/system/user'
import { getDeptTree, type DeptTree } from '@/api/system/dept'
import UserFormDialog from './components/UserFormDialog.vue'

const deptTreeRef = ref<InstanceType<typeof ElTree>>()
const loading = ref(false)
const deptFilterText = ref('')
const deptTree = ref<DeptTree[]>([])
const userList = ref<User[]>([])
const total = ref(0)
const selectedIds = ref<number[]>([])
const formDialogVisible = ref(false)
const currentUserId = ref<number | undefined>(undefined)

const queryParams = reactive<UserQuery>({
  pageNum: 1,
  pageSize: 10,
  username: '',
  realName: '',
  phone: '',
  status: '',
  deptId: undefined,
})

watch(deptFilterText, (val) => {
  deptTreeRef.value?.filter(val)
})

const filterDeptNode = (value: string, data: DeptTree) => {
  if (!value) return true
  return data.label.includes(value)
}

const loadDeptTree = async () => {
  try {
    deptTree.value = await getDeptTree()
  } catch (error) {
    console.error('加载部门树失败', error)
  }
}

const loadUserList = async () => {
  loading.value = true
  try {
    const result = await getUserList(queryParams)
    userList.value = result.records
    total.value = result.total
  } catch (error) {
    console.error('加载用户列表失败', error)
  } finally {
    loading.value = false
  }
}

const handleDeptClick = (data: DeptTree) => {
  queryParams.deptId = data.id
  handleQuery()
}

const handleQuery = () => {
  loadUserList()
}

const handleReset = () => {
  queryParams.username = ''
  queryParams.realName = ''
  queryParams.phone = ''
  queryParams.status = ''
  queryParams.deptId = undefined
  handleQuery()
}

const handleAdd = () => {
  currentUserId.value = undefined
  formDialogVisible.value = true
}

const handleEdit = (row: User) => {
  currentUserId.value = row.id
  formDialogVisible.value = true
}

const handleDelete = async (row: User) => {
  try {
    await ElMessageBox.confirm(`确定要删除用户"${row.realName}"吗？`, '提示', {
      type: 'warning',
    })
    await deleteUser(row.id)
    ElMessage.success('删除成功')
    handleQuery()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('删除用户失败', error)
    }
  }
}

const handleBatchDelete = async () => {
  if (selectedIds.value.length === 0) {
    ElMessage.warning('请选择要删除的用户')
    return
  }
  try {
    await ElMessageBox.confirm(`确定要删除选中的${selectedIds.value.length}个用户吗？`, '提示', {
      type: 'warning',
    })
    await batchDeleteUsers(selectedIds.value)
    ElMessage.success('批量删除成功')
    handleQuery()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('批量删除用户失败', error)
    }
  }
}

const handleSelectionChange = (selection: User[]) => {
  selectedIds.value = selection.map((item) => item.id)
}

const handleStatusChange = async (row: User) => {
  const text = row.status === 'normal' ? '启用' : '禁用'
  try {
    await updateUserStatus(row.id, row.status)
    ElMessage.success(`${text}成功`)
  } catch (error) {
    row.status = row.status === 'normal' ? 'disable' : 'normal'
    console.error('更新用户状态失败', error)
  }
}

const handleResetPwd = async (row: User) => {
  try {
    await ElMessageBox.confirm(`确定要重置用户"${row.realName}"的密码吗？`, '提示', {
      type: 'warning',
    })
    await resetUserPassword(row.id)
    ElMessage.success('密码已重置为默认密码')
  } catch (error) {
    if (error !== 'cancel') {
      console.error('重置密码失败', error)
    }
  }
}

onMounted(() => {
  loadDeptTree()
  loadUserList()
})
</script>

<style lang="scss" scoped>
.page-container {
  padding: 0;
}

.dept-card {
  height: calc(100vh - 180px);
  overflow: hidden;
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

.dept-filter {
  margin-bottom: 12px;
}

.el-tree {
  flex: 1;
  overflow: auto;
}

.search-form {
  margin-bottom: 16px;
}

.pagination-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 16px;
}
</style>
