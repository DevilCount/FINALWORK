<template>
  <div class="page-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>菜单管理</span>
          <el-button type="primary" icon="Plus" @click="handleAdd">新增菜单</el-button>
        </div>
      </template>
      
      <el-form :model="queryParams" inline class="search-form">
        <el-form-item label="菜单名称">
          <el-input
            v-model="queryParams.name"
            placeholder="请输入菜单名称"
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
        :data="menuList"
        row-key="id"
        border
        :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
        default-expand-all
      >
        <el-table-column prop="name" label="菜单名称" min-width="160" />
        <el-table-column label="图标" width="80" align="center">
          <template #default="{ row }">
            <el-icon v-if="row.icon">
              <component :is="row.icon" />
            </el-icon>
          </template>
        </el-table-column>
        <el-table-column prop="sort" label="排序" width="80" align="center" />
        <el-table-column prop="path" label="路由路径" min-width="140" show-overflow-tooltip />
        <el-table-column prop="component" label="组件路径" min-width="160" show-overflow-tooltip />
        <el-table-column prop="perms" label="权限标识" min-width="140" show-overflow-tooltip />
        <el-table-column label="类型" width="90" align="center">
          <template #default="{ row }">
            <el-tag v-if="row.type === 'directory'" type="primary">目录</el-tag>
            <el-tag v-else-if="row.type === 'menu'" type="success">菜单</el-tag>
            <el-tag v-else type="warning">按钮</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="可见" width="80" align="center">
          <template #default="{ row }">
            <el-tag v-if="row.visible === 'show'" type="success">是</el-tag>
            <el-tag v-else type="info">否</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="状态" width="80" align="center">
          <template #default="{ row }">
            <el-tag v-if="row.status === 'normal'" type="success">正常</el-tag>
            <el-tag v-else type="danger">禁用</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180" fixed="right" align="center">
          <template #default="{ row }">
            <el-button link type="primary" icon="Edit" @click="handleEdit(row)">编辑</el-button>
            <el-button link type="primary" icon="Plus" @click="handleAddChild(row)">新增</el-button>
            <el-button link type="danger" icon="Delete" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <MenuFormDialog
      v-model:visible="formDialogVisible"
      :menu-id="currentMenuId"
      :parent-id="currentParentId"
      @success="handleQuery"
    />
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import {
  getMenuList,
  deleteMenu,
  type Menu,
  type MenuQuery,
} from '@/api/system/menu'
import MenuFormDialog from './components/MenuFormDialog.vue'

const loading = ref(false)
const menuList = ref<Menu[]>([])
const formDialogVisible = ref(false)
const currentMenuId = ref<number | undefined>(undefined)
const currentParentId = ref<number>(0)

const queryParams = reactive<MenuQuery>({
  name: '',
  status: '',
})

const loadMenuList = async () => {
  loading.value = true
  try {
    menuList.value = await getMenuList(queryParams)
  } catch (error) {
    console.error('加载菜单列表失败', error)
  } finally {
    loading.value = false
  }
}

const handleQuery = () => {
  loadMenuList()
}

const handleReset = () => {
  queryParams.name = ''
  queryParams.status = ''
  handleQuery()
}

const handleAdd = () => {
  currentMenuId.value = undefined
  currentParentId.value = 0
  formDialogVisible.value = true
}

const handleAddChild = (row: Menu) => {
  currentMenuId.value = undefined
  currentParentId.value = row.id
  formDialogVisible.value = true
}

const handleEdit = (row: Menu) => {
  currentMenuId.value = row.id
  currentParentId.value = row.parentId
  formDialogVisible.value = true
}

const handleDelete = async (row: Menu) => {
  if (row.children && row.children.length > 0) {
    ElMessage.warning('存在子菜单，不能删除')
    return
  }
  try {
    await ElMessageBox.confirm(`确定要删除菜单"${row.name}"吗？`, '提示', {
      type: 'warning',
    })
    await deleteMenu(row.id)
    ElMessage.success('删除成功')
    handleQuery()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('删除菜单失败', error)
    }
  }
}

onMounted(() => {
  loadMenuList()
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
