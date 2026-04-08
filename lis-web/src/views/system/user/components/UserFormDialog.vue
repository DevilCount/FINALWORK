<template>
  <el-dialog
    v-model="dialogVisible"
    :title="isEdit ? '编辑用户' : '新增用户'"
    width="600px"
    :close-on-click-modal="false"
    @close="handleClose"
  >
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="80px"
    >
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="用户名" prop="username">
            <el-input
              v-model="formData.username"
              placeholder="请输入用户名"
              :disabled="isEdit"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="姓名" prop="realName">
            <el-input v-model="formData.realName" placeholder="请输入姓名" />
          </el-form-item>
        </el-col>
      </el-row>
      
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="密码" prop="password">
            <el-input
              v-model="formData.password"
              type="password"
              :placeholder="isEdit ? '留空则不修改密码' : '请输入密码'"
              show-password
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="性别" prop="gender">
            <el-select v-model="formData.gender" placeholder="请选择性别">
              <el-option label="男" value="male" />
              <el-option label="女" value="female" />
              <el-option label="未知" value="unknown" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="手机号" prop="phone">
            <el-input v-model="formData.phone" placeholder="请输入手机号" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="formData.email" placeholder="请输入邮箱" />
          </el-form-item>
        </el-col>
      </el-row>
      
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="部门" prop="deptId">
            <el-tree-select
          v-model="formData.deptId"
          :data="deptTree"
          :props="{ label: 'label', children: 'children' }"
          placeholder="请选择部门"
          check-strictly
          clearable
          default-expand-all
        />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="状态" prop="status">
            <el-select v-model="formData.status" placeholder="请选择状态">
              <el-option label="正常" value="normal" />
              <el-option label="禁用" value="disable" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      
      <el-form-item label="角色" prop="roleIds">
        <el-select
          v-model="formData.roleIds"
          multiple
          placeholder="请选择角色"
          style="width: 100%"
        >
          <el-option
            v-for="role in roleList"
            :key="role.id"
            :label="role.name"
            :value="role.id"
          />
        </el-select>
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
  getUserById,
  createUser,
  updateUser,
  type UserForm,
} from '@/api/system/user'
import { getDeptTree, type DeptTree } from '@/api/system/dept'
import { getAllRoles, type Role } from '@/api/system/role'

const props = defineProps<{
  visible: boolean
  userId?: number
  deptId?: number
}>()

const emit = defineEmits<{
  'update:visible': [value: boolean]
  success: []
}>()

const formRef = ref<FormInstance>()
const submitLoading = ref(false)
const deptTree = ref<DeptTree[]>([])
const roleList = ref<Role[]>([])

const dialogVisible = computed({
  get: () => props.visible,
  set: (val) => emit('update:visible', val),
})

const isEdit = computed(() => !!props.userId)

const formData = reactive<UserForm>({
  username: '',
  realName: '',
  email: '',
  phone: '',
  gender: 'unknown',
  status: 'normal',
  deptId: null,
  roleIds: [],
  password: '',
})

const formRules: FormRules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 20, message: '用户名长度为3-20个字符', trigger: 'blur' },
  ],
  realName: [
    { required: true, message: '请输入姓名', trigger: 'blur' },
  ],
  password: [
    {
      validator: (_rule, value, callback) => {
        if (!isEdit.value && !value) {
          callback(new Error('请输入密码'))
        } else if (value && (value.length < 6 || value.length > 20)) {
          callback(new Error('密码长度为6-20个字符'))
        } else {
          callback()
        }
      },
      trigger: 'blur',
    },
  ],
  phone: [
    {
      pattern: /^1[3-9]\d{9}$/,
      message: '请输入正确的手机号',
      trigger: 'blur',
    },
  ],
  email: [
    {
      type: 'email',
      message: '请输入正确的邮箱地址',
      trigger: 'blur',
    },
  ],
  deptId: [
    { required: true, message: '请选择部门', trigger: 'change' },
  ],
}

const loadDeptTree = async () => {
  try {
    deptTree.value = await getDeptTree()
  } catch (error) {
    console.error('加载部门树失败', error)
  }
}

const loadRoleList = async () => {
  try {
    roleList.value = await getAllRoles()
  } catch (error) {
    console.error('加载角色列表失败', error)
  }
}

const loadUserInfo = async () => {
  if (!props.userId) return
  try {
    const user = await getUserById(props.userId)
    formData.username = user.username
    formData.realName = user.realName
    formData.email = user.email
    formData.phone = user.phone
    formData.gender = user.gender
    formData.status = user.status
    formData.deptId = user.deptId
    formData.roleIds = user.roleIds || []
    formData.password = ''
  } catch (error) {
    console.error('加载用户信息失败', error)
  }
}

const resetForm = () => {
  formData.username = ''
  formData.realName = ''
  formData.email = ''
  formData.phone = ''
  formData.gender = 'unknown'
  formData.status = 'normal'
  formData.deptId = props.deptId || null
  formData.roleIds = []
  formData.password = ''
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
          await updateUser({ ...formData, id: props.userId })
          ElMessage.success('修改成功')
        } else {
          await createUser(formData)
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
      loadDeptTree()
      loadRoleList()
      if (props.userId) {
        loadUserInfo()
      } else {
        formData.deptId = props.deptId || null
      }
    }
  }
)
</script>
