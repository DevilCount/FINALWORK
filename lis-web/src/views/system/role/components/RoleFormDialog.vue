<template>
  <el-dialog
    v-model="dialogVisible"
    :title="isEdit ? '编辑角色' : '新增角色'"
    width="500px"
    :close-on-click-modal="false"
    @close="handleClose"
  >
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="80px"
    >
      <el-form-item label="角色名称" prop="name">
        <el-input v-model="formData.name" placeholder="请输入角色名称" />
      </el-form-item>
      <el-form-item label="角色编码" prop="code">
        <el-input
          v-model="formData.code"
          placeholder="请输入角色编码"
          :disabled="isEdit"
        />
      </el-form-item>
      <el-form-item label="排序" prop="sort">
        <el-input-number v-model="formData.sort" :min="0" :max="999" />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="formData.status" placeholder="请选择状态">
          <el-option label="正常" :value="0" />
          <el-option label="禁用" :value="2" />
        </el-select>
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input
          v-model="formData.remark"
          type="textarea"
          :rows="3"
          placeholder="请输入备注"
        />
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
  getRoleById,
  createRole,
  updateRole,
  type RoleForm,
} from '@/api/system/role'

const props = defineProps<{
  visible: boolean
  roleId?: number
}>()

const emit = defineEmits<{
  'update:visible': [value: boolean]
  success: []
}>()

const formRef = ref<FormInstance>()
const submitLoading = ref(false)

const dialogVisible = computed({
  get: () => props.visible,
  set: (val) => emit('update:visible', val),
})

const isEdit = computed(() => !!props.roleId)

const formData = reactive<RoleForm>({
  name: '',
  code: '',
  status: 0,
  sort: 0,
  remark: '',
  menuIds: [],
})

const formRules: FormRules = {
  name: [
    { required: true, message: '请输入角色名称', trigger: 'blur' },
    { min: 2, max: 20, message: '角色名称长度为2-20个字符', trigger: 'blur' },
  ],
  code: [
    { required: true, message: '请输入角色编码', trigger: 'blur' },
    { min: 2, max: 20, message: '角色编码长度为2-20个字符', trigger: 'blur' },
    { pattern: /^[a-zA-Z][a-zA-Z0-9_]*$/, message: '角色编码必须以字母开头，只能包含字母、数字和下划线', trigger: 'blur' },
  ],
}

const loadRoleInfo = async () => {
  if (!props.roleId) return
  try {
    const role = await getRoleById(props.roleId)
    formData.name = role.name
    formData.code = role.code
    formData.status = role.status
    formData.sort = role.sort
    formData.remark = role.remark
    formData.menuIds = role.menuIds || []
  } catch (error) {
    console.error('加载角色信息失败', error)
  }
}

const resetForm = () => {
  formData.name = ''
  formData.code = ''
  formData.status = 0
  formData.sort = 0
  formData.remark = ''
  formData.menuIds = []
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
          await updateRole({ ...formData, id: props.roleId })
          ElMessage.success('修改成功')
        } else {
          await createRole(formData)
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
    if (val && props.roleId) {
      loadRoleInfo()
    }
  }
)
</script>
