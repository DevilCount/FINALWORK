<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>角色管理</span>
          <el-button type="primary" icon="Plus" @click="handleAdd">新增角色</el-button>
        </div>
      </template>
      
      <el-form :model="queryParams" inline class="search-form">
        <el-form-item label="角色名称">
          <el-input
            v-model="queryParams.name"
            placeholder="请输入角色名称"
            clearable
            @keyup.enter="handleQuery"
          />
        </el-form-item>
        <el-form-item label="角色编码">
          <el-input
            v-model="queryParams.code"
            placeholder="请输入角色编码"
            clearable
            @keyup.enter="handleQuery"
          />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
            <el-option label="正常" :value="0" />
            <el-option label="禁用" :value="2" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
          <el-button icon="Refresh" @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>

      <el-table
        v-loading="loading"
        :data="roleList"
        border
        stripe
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column prop="name" label="角色名称" min-width="120" />
        <el-table-column prop="code" label="角色编码" min-width="120" />
        <el-table-column prop="sort" label="排序" width="80" align="center" />
        <el-table-column label="状态" width="100" align="center">
          <template #default="{ row }">
            <el-switch
              v-model="row.status"
              :active-value="0"
              :inactive-value="2"
              @change="handleStatusChange(row)"
            />
          </template>
        </el-table-column>
        <el-table-column prop="remark" label="备注" min-width="160" show-overflow-tooltip />
        <el-table-column prop="createTime" label="创建时间" width="170" />
        <el-table-column label="操作" width="240" fixed="right" align="center">
          <template #default="{ row }">
            <el-button link type="primary" icon="Edit" @click="handleEdit(row)">编辑</el-button>
            <el-button link type="primary" icon="Menu" @click="handleAssignMenu(row)">分配权限</el-button>
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

    <RoleFormDialog
      v-model:visible="formDialogVisible"
      :role-id="currentRoleId"
      @success="handleQuery"
    />

    <el-dialog
      v-model="menuDialogVisible"
      title="分配菜单权限"
      width="500px"
    >
      <el-tree
        ref="menuTreeRef"
        :data="menuTree"
        :props="{ label: 'label', children: 'children' }"
        show-checkbox
        node-key="id"
        default-expand-all
        check-strictly
      />
      <template #footer>
        <el-button @click="menuDialogVisible = false">取消</el-button>
        <el-button type="primary" :loading="menuSubmitLoading" @click="handleMenuSubmit">
          确定
        </el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import type { ElTree } from 'element-plus'
import {
  getRoleList,
  deleteRole,
  batchDeleteRoles,
  updateRoleStatus,
  getRoleMenus,
  assignRoleMenus,
  type Role,
  type RoleQuery,
} from '@/api/system/role'
import { getMenuTreeNodes, type MenuTree } from '@/api/system/menu'
import RoleFormDialog from './components/RoleFormDialog.vue'

const menuTreeRef = ref<InstanceType<typeof ElTree>>()
const loading = ref(false)
const roleList = ref<Role[]>([])
const total = ref(0)
const selectedIds = ref<number[]>([])
const formDialogVisible = ref(false)
const currentRoleId = ref<number | undefined>(undefined)
const menuDialogVisible = ref(false)
const menuTree = ref<MenuTree[]>([])
const menuSubmitLoading = ref(false)
const currentRoleForMenu = ref<Role | null>(null)

const queryParams = reactive<RoleQuery>({
  pageNum: 1,
  pageSize: 10,
  name: '',
  code: '',
  status: '',
})

const loadRoleList = async () => {
  loading.value = true
  try {
    const result = await getRoleList(queryParams)
    roleList.value = result.records
    total.value = result.total
  } catch (error) {
    console.error('加载角色列表失败', error)
  } finally {
    loading.value = false
  }
}

const loadMenuTree = async () => {
  try {
    menuTree.value = await getMenuTreeNodes()
  } catch (error) {
    console.error('加载菜单树失败', error)
  }
}

const handleQuery = () => {
  loadRoleList()
}

const handleReset = () => {
  queryParams.name = ''
  queryParams.code = ''
  queryParams.status = ''
  handleQuery()
}

const handleAdd = () => {
  currentRoleId.value = undefined
  formDialogVisible.value = true
}

const handleEdit = (row: Role) => {
  currentRoleId.value = row.id
  formDialogVisible.value = true
}

const handleDelete = async (row: Role) => {
  try {
    await ElMessageBox.confirm(`确定要删除角色"${row.name}"吗？`, '提示', {
      type: 'warning',
    })
    await deleteRole(row.id)
    ElMessage.success('删除成功')
    handleQuery()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('删除角色失败', error)
    }
  }
}

const handleBatchDelete = async () => {
  if (selectedIds.value.length === 0) {
    ElMessage.warning('请选择要删除的角色')
    return
  }
  try {
    await ElMessageBox.confirm(`确定要删除选中的${selectedIds.value.length}个角色吗？`, '提示', {
      type: 'warning',
    })
    await batchDeleteRoles(selectedIds.value)
    ElMessage.success('批量删除成功')
    handleQuery()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('批量删除角色失败', error)
    }
  }
}

const handleSelectionChange = (selection: Role[]) => {
  selectedIds.value = selection.map((item) => item.id)
}

const handleStatusChange = async (row: Role) => {
  const text = row.status === 0 ? '启用' : '禁用'
  try {
    await updateRoleStatus(row.id, row.status)
    ElMessage.success(`${text}成功`)
  } catch (error) {
    row.status = row.status === 0 ? 2 : 0
    console.error('更新角色状态失败', error)
  }
}

const handleAssignMenu = async (row: Role) => {
  currentRoleForMenu.value = row
  await loadMenuTree()
  try {
    const menuIds = await getRoleMenus(row.id)
    menuTreeRef.value?.setCheckedKeys(menuIds)
  } catch (error) {
    console.error('加载角色菜单失败', error)
  }
  menuDialogVisible.value = true
}

const handleMenuSubmit = async () => {
  if (!currentRoleForMenu.value) return
  const menuIds = [
    ...menuTreeRef.value!.getCheckedKeys(),
    ...menuTreeRef.value!.getHalfCheckedKeys(),
  ] as number[]
  menuSubmitLoading.value = true
  try {
    await assignRoleMenus(currentRoleForMenu.value.id, menuIds)
    ElMessage.success('分配权限成功')
    menuDialogVisible.value = false
  } catch (error) {
    console.error('分配权限失败', error)
  } finally {
    menuSubmitLoading.value = false
  }
}

onMounted(() => {
  loadRoleList()
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

.pagination-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 16px;
}
</style>
