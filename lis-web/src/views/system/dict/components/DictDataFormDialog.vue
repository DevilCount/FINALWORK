<template>
  <el-dialog
    v-model="dialogVisible"
    :title="isEdit ? '编辑字典数据' : '新增字典数据'"
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
      <el-form-item label="字典标签" prop="dataLabel">
        <el-input v-model="formData.dataLabel" placeholder="请输入字典标签" />
      </el-form-item>
      <el-form-item label="字典值" prop="dataValue">
        <el-input v-model="formData.dataValue" placeholder="请输入字典值" />
      </el-form-item>
      <el-form-item label="排序" prop="sort">
        <el-input-number v-model="formData.sort" :min="0" :max="999" />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-radio-group v-model="formData.status">
          <el-radio :value="0">正常</el-radio>
          <el-radio :value="2">禁用</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="样式属性" prop="cssClass">
        <el-input v-model="formData.cssClass" placeholder="请输入样式属性" />
      </el-form-item>
      <el-form-item label="列表样式" prop="listClass">
        <el-select v-model="formData.listClass" placeholder="请选择列表样式" clearable>
          <el-option label="默认" value="" />
          <el-option label="主要" value="primary" />
          <el-option label="成功" value="success" />
          <el-option label="信息" value="info" />
          <el-option label="警告" value="warning" />
          <el-option label="危险" value="danger" />
        </el-select>
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input
          v-model="formData.remark"
          type="textarea"
          :rows="2"
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
  getDictDataById,
  createDictData,
  updateDictData,
  type DictDataForm,
} from '@/api/system/dict'

const props = defineProps<{
  visible: boolean
  dataId?: number
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

const isEdit = computed(() => !!props.dataId)

const formData = reactive<DictDataForm>({
  dictTypeId: 0,
  dictType: '',
  dataLabel: '',
  dataValue: '',
  status: 0,
  sort: 0,
  remark: '',
  cssClass: '',
  listClass: '',
})

const formRules: FormRules = {
  dataLabel: [
    { required: true, message: '请输入字典标签', trigger: 'blur' },
  ],
  dataValue: [
    { required: true, message: '请输入字典值', trigger: 'blur' },
  ],
  dictTypeId: [
    { required: true, message: '请选择字典类型', trigger: 'change' },
  ],
}

const loadDataInfo = async () => {
  if (!props.dataId) return
  try {
    const data = await getDictDataById(props.dataId)
    formData.dictTypeId = data.dictTypeId
    formData.dictType = data.dictType
    formData.dataLabel = data.dataLabel
    formData.dataValue = data.dataValue
    formData.status = data.status
    formData.sort = data.sort
    formData.remark = data.remark
    formData.cssClass = data.cssClass
    formData.listClass = data.listClass
  } catch (error) {
    console.error('加载字典数据信息失败', error)
  }
}

const resetForm = () => {
  formData.dictTypeId = props.typeId || 0
  formData.dictType = ''
  formData.dataLabel = ''
  formData.dataValue = ''
  formData.status = 0
  formData.sort = 0
  formData.remark = ''
  formData.cssClass = ''
  formData.listClass = ''
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
          await updateDictData({ ...formData, id: props.dataId })
          ElMessage.success('修改成功')
        } else {
          await createDictData(formData)
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
      if (props.dataId) {
        loadDataInfo()
      } else {
        formData.dictTypeId = props.typeId || 0
      }
    }
  }
)
</script>
