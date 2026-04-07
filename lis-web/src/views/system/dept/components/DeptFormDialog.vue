<template>
  <el-dialog
    v-model="dialogVisible"
    :title="isEdit ? '编辑部门' : '新增部门'"
    width="550px"
    :close-on-click-modal="false"
    @close="handleClose"
  >
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="80px"
    >
      <el-form-item label="上级部门" prop="parentId">
        <el-tree-select
          v-model="formData.parentId"
          :data="deptTree"
          :props="{ label: 'label', children: 'children', value: 'id' }"
          placeholder="请选择上级部门"
          check-strictly
          clearable
          default-expand-all
        />
      </el-form-item>
      <el-form-item label="部门名称" prop="name">
        <el-input v-model="formData.name" placeholder="请输入部门名称" />
      </el-form-item>
      <el-form-item label="部门编码" prop="code">
        <el-input v-model="formData.code" placeholder="请输入部门编码" />
      </el-form-item>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="负责人" prop="leader">
            <el-input v-model="formData.leader" placeholder="请输入负责人" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="联系电话" prop="phone">
            <el-input v-model="formData.phone" placeholder="请输入联系电话" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item label="邮箱" prop="email">
        <el-input v-model="formData.email" placeholder="请输入邮箱" />
      </el-form-item>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="排序" prop="sort">
            <el-input-number v-model="formData.sort" :min="0" :max="999" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="状态" prop="status">
            <el-radio-group v-model="formData.status">
              <el-radio value="normal">正常</el-radio>
              <el-radio value="disable">禁用</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>
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
  getDeptById,
  createDept,
  updateDept,
  type DeptForm,
} from '@/api/system/dept'
import { getDeptTree, type DeptTree } from '@/api/system/dept'

const props = defineProps<{
  visible: boolean
  deptId?: number
  parentId: number
}>()

const emit = defineEmits<{
  'update:visible': [value: boolean]
  success: []
}>()

const formRef = ref<FormInstance>()
const submitLoading = ref(false)
const deptTree = ref<DeptTree[]>([])

const dialogVisible = computed({
  get: () => props.visible,
  set: (val) => emit('update:visible', val),
})

const isEdit = computed(() => !!props.deptId)

const formData = reactive<DeptForm>({
  parentId: 0,
  name: '',
  code: '',
  leader: '',
  phone: '',
  email: '',
  status: 'normal',
  sort: 0,
})

const formRules: FormRules = {
  name: [
    { required: true, message: '请输入部门名称', trigger: 'blur' },
  ],
  code: [
    { required: true, message: '请输入部门编码', trigger: 'blur' },
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
}

const loadDeptTree = async () => {
  try {
    const tree = await getDeptTree()
    deptTree.value = [{ id: 0, label: '根部门', parentId: -1, children: tree }]
  } catch (error) {
    console.error('加载部门树失败', error)
  }
}

const loadDeptInfo = async () => {
  if (!props.deptId) return
  try {
    const dept = await getDeptById(props.deptId)
    formData.parentId = dept.parentId
    formData.name = dept.name
    formData.code = dept.code
    formData.leader = dept.leader
    formData.phone = dept.phone
    formData.email = dept.email
    formData.status = dept.status
    formData.sort = dept.sort
  } catch (error) {
    console.error('加载部门信息失败', error)
  }
}

const resetForm = () => {
  formData.parentId = props.parentId || 0
  formData.name = ''
  formData.code = ''
  formData.leader = ''
  formData.phone = ''
  formData.email = ''
  formData.status = 'normal'
  formData.sort = 0
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
          await updateDept({ ...formData, id: props.deptId })
          ElMessage.success('修改成功')
        } else {
          await createDept(formData)
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
      if (props.deptId) {
        loadDeptInfo()
      } else {
        formData.parentId = props.parentId || 0
      }
    }
  }
)
</script>
