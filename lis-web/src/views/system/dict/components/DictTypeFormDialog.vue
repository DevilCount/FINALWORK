<template>
  <el-dialog
    v-model="dialogVisible"
    :title="isEdit ? '编辑字典类型' : '新增字典类型'"
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
      <el-form-item label="字典名称" prop="dictName">
        <el-input v-model="formData.dictName" placeholder="请输入字典名称" />
      </el-form-item>
      <el-form-item label="字典编码" prop="dictType">
        <el-input
          v-model="formData.dictType"
          placeholder="请输入字典编码"
          :disabled="isEdit"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-radio-group v-model="formData.status">
          <el-radio :value="0">正常</el-radio>
          <el-radio :value="2">禁用</el-radio>
        </el-radio-group>
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
  getDictTypeById,
  createDictType,
  updateDictType,
  type DictTypeForm,
} from '@/api/system/dict'

const props = defineProps<{
  visible: boolean
  typeId?: number
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

const isEdit = computed(() => !!props.typeId)

const formData = reactive<DictTypeForm>({
  dictName: '',
  dictType: '',
  status: 0,
  remark: '',
})

const formRules: FormRules = {
  dictName: [
    { required: true, message: '请输入字典名称', trigger: 'blur' },
  ],
  dictType: [
    { required: true, message: '请输入字典编码', trigger: 'blur' },
    { pattern: /^[a-zA-Z][a-zA-Z0-9_]*$/, message: '字典编码必须以字母开头，只能包含字母、数字和下划线', trigger: 'blur' },
  ],
}

const loadTypeInfo = async () => {
  if (!props.typeId) return
  try {
    const type = await getDictTypeById(props.typeId)
    formData.dictName = type.dictName
    formData.dictType = type.dictType
    formData.status = type.status
    formData.remark = type.remark
  } catch (error) {
    console.error('加载字典类型信息失败', error)
  }
}

const resetForm = () => {
  formData.dictName = ''
  formData.dictType = ''
  formData.status = 0
  formData.remark = ''
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
          await updateDictType({ ...formData, id: props.typeId })
          ElMessage.success('修改成功')
        } else {
          await createDictType(formData)
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
    if (val && props.typeId) {
      loadTypeInfo()
    }
  }
)
</script>
