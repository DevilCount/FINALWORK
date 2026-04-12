<template>
  <el-dialog
    v-model="dialogVisible"
    :title="isEdit ? '编辑菜单' : '新增菜单'"
    width="650px"
    :close-on-click-modal="false"
    @close="handleClose"
  >
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
    >
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="上级菜单" prop="parentId">
            <el-tree-select
              v-model="formData.parentId"
              :data="menuTree"
              :props="{ label: 'label', children: 'children' }"
              placeholder="请选择上级菜单"
              check-strictly
              clearable
              default-expand-all
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="菜单类型" prop="type">
            <el-radio-group v-model="formData.type">
              <el-radio value="M">目录</el-radio>
              <el-radio value="C">菜单</el-radio>
              <el-radio value="F">按钮</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>
      
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="菜单名称" prop="menuName">
            <el-input v-model="formData.menuName" placeholder="请输入菜单名称" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="菜单图标" prop="icon">
            <el-input v-model="formData.icon" placeholder="请输入图标名称" />
          </el-form-item>
        </el-col>
      </el-row>
      
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="路由路径" prop="path">
            <el-input v-model="formData.path" placeholder="请输入路由路径" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="组件路径" prop="component">
            <el-input v-model="formData.component" placeholder="请输入组件路径" />
          </el-form-item>
        </el-col>
      </el-row>
      
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="权限标识" prop="perms">
            <el-input v-model="formData.perms" placeholder="请输入权限标识" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="排序" prop="sort">
            <el-input-number v-model="formData.sort" :min="0" :max="999" />
          </el-form-item>
        </el-col>
      </el-row>
      
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="是否可见" prop="visible">
            <el-radio-group v-model="formData.visible">
              <el-radio :value="0">显示</el-radio>
              <el-radio :value="1">隐藏</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="状态" prop="status">
            <el-radio-group v-model="formData.status">
              <el-radio :value="0">正常</el-radio>
              <el-radio :value="2">禁用</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>
      
      <el-form-item label="重定向" prop="redirect">
        <el-input v-model="formData.redirect" placeholder="请输入重定向路径" />
      </el-form-item>
    </el-form>
    
    <template #footer>
      <el-button @click="handleClose">取消</el-button>
      <el-button type="primary" :loading="submitLoading" @click="handleSubmit">
        确定
      </el-button>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import { ref, reactive, computed, watch } from 'vue'
import { ElMessage, type FormInstance, type FormRules } from 'element-plus'
import {
  getMenuById,
  createMenu,
  updateMenu,
  type MenuForm,
} from '@/api/system/menu'
import { getMenuTreeNodes, type MenuTree } from '@/api/system/menu'

const props = defineProps<{
  visible: boolean
  menuId?: number
  parentId: number
}>()

const emit = defineEmits<{
  'update:visible': [value: boolean]
  success: []
}>()

const formRef = ref<FormInstance>()
const submitLoading = ref(false)
const menuTree = ref<MenuTree[]>([])

const dialogVisible = computed({
  get: () => props.visible,
  set: (val) => emit('update:visible', val),
})

const isEdit = computed(() => !!props.menuId)

const formData = reactive<MenuForm>({
  parentId: 0,
  menuName: '',
  path: '',
  component: '',
  redirect: '',
  icon: '',
  sort: 0,
  status: 0,
  visible: 0,
  type: 0,
  perms: '',
})

const formRules: FormRules = {
  menuName: [
    { required: true, message: '请输入菜单名称', trigger: 'blur' },
  ],
  type: [
    { required: true, message: '请选择菜单类型', trigger: 'change' },
  ],
  path: [
    { required: true, message: '请输入路由路径', trigger: 'blur' },
  ],
}

const loadMenuTree = async () => {
  try {
    const tree = await getMenuTreeNodes()
    menuTree.value = [{ id: 0, label: '根目录', parentId: -1, children: tree }]
  } catch (error) {
    console.error('加载菜单树失败', error)
  }
}

const loadMenuInfo = async () => {
  if (!props.menuId) return
  try {
    const menu = await getMenuById(props.menuId)
    formData.parentId = menu.parentId
    formData.menuName = menu.menuName
    formData.path = menu.path
    formData.component = menu.component
    formData.redirect = menu.redirect
    formData.icon = menu.icon
    formData.sort = menu.sort
    formData.status = menu.status
    formData.visible = menu.visible
    formData.type = menu.type
    formData.perms = menu.perms
  } catch (error) {
    console.error('加载菜单信息失败', error)
  }
}

const resetForm = () => {
  formData.parentId = props.parentId || 0
  formData.menuName = ''
  formData.path = ''
  formData.component = ''
  formData.redirect = ''
  formData.icon = ''
  formData.sort = 0
  formData.status = 0
  formData.visible = 0
  formData.type = 0
  formData.perms = ''
  formRef.value?.resetFields()
}

const handleClose = () => {
  resetForm()
  dialogVisible.value = false
}

const handleSubmit = async () => {
  if (!formRef.value) return
  
  await formRef.value.validate(async (valid) => {
    if (valid) {
      submitLoading.value = true
      try {
        if (isEdit.value) {
          await updateMenu({ ...formData, id: props.menuId })
          ElMessage.success('修改成功')
        } else {
          await createMenu(formData)
          ElMessage.success('新增成功')
        }
        emit('success')
        handleClose()
      } catch (error) {
        console.error('提交失败', error)
      } finally {
        submitLoading.value = false
      }
    }
  })
}

watch(
  () => props.visible,
  (val) => {
    if (val) {
      loadMenuTree()
      if (props.menuId) {
        loadMenuInfo()
      } else {
        formData.parentId = props.parentId || 0
      }
    }
  }
)
</script>
