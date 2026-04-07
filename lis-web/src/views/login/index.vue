<template>
  <div class="login-container">
    <div class="login-background">
      <div class="login-content">
        <div class="login-header">
          <img src="@/assets/images/logo.svg" alt="LIS" class="logo" />
          <h1 class="title">LIS实验室信息管理系统</h1>
          <p class="subtitle">Laboratory Information System</p>
        </div>
        
        <el-form
          ref="loginFormRef"
          :model="loginForm"
          :rules="loginRules"
          class="login-form"
          size="large"
        >
          <el-form-item prop="username">
            <el-input
              v-model="loginForm.username"
              placeholder="请输入用户名"
              prefix-icon="User"
              clearable
              @keyup.enter="handleLogin"
            />
          </el-form-item>
          
          <el-form-item prop="password">
            <el-input
              v-model="loginForm.password"
              type="password"
              placeholder="请输入密码"
              prefix-icon="Lock"
              show-password
              @keyup.enter="handleLogin"
            />
          </el-form-item>
          
          <el-form-item prop="captcha">
            <div class="captcha-row">
              <el-input
                v-model="loginForm.captcha"
                placeholder="请输入验证码"
                prefix-icon="Picture"
                clearable
                @keyup.enter="handleLogin"
              />
              <div class="captcha-image" @click="refreshCaptcha">
                <img v-if="captchaImage" :src="captchaImage" alt="验证码" />
                <span v-else>点击获取</span>
              </div>
            </div>
          </el-form-item>
          
          <el-form-item>
            <div class="login-options">
              <el-checkbox v-model="loginForm.rememberMe">记住密码</el-checkbox>
              <el-link type="primary" :underline="false">忘记密码？</el-link>
            </div>
          </el-form-item>
          
          <el-form-item>
            <el-button
              type="primary"
              class="login-btn"
              :loading="loading"
              @click="handleLogin"
            >
              {{ loading ? '登录中...' : '登 录' }}
            </el-button>
          </el-form-item>
        </el-form>
        
        <div class="login-footer">
          <p>© 2026 LIS实验室信息管理系统 All Rights Reserved</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage, type FormInstance, type FormRules } from 'element-plus'
import { useUserStore } from '@/stores/modules/user'
import { getCaptcha } from '@/api/auth'
import type { LoginForm } from '@/types/user'

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()

const loginFormRef = ref<FormInstance>()
const loading = ref(false)
const captchaImage = ref('')

const loginForm = reactive<LoginForm>({
  username: '',
  password: '',
  captcha: '',
  uuid: '',
  rememberMe: false,
})

const loginRules: FormRules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 20, message: '用户名长度为3-20个字符', trigger: 'blur' },
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 20, message: '密码长度为6-20个字符', trigger: 'blur' },
  ],
  captcha: [
    { required: true, message: '请输入验证码', trigger: 'blur' },
    { min: 4, max: 4, message: '验证码为4位字符', trigger: 'blur' },
  ],
}

const refreshCaptcha = async () => {
  try {
    const data = await getCaptcha()
    captchaImage.value = data.captchaImage
    loginForm.uuid = data.uuid
  } catch (error) {
    console.error('获取验证码失败', error)
  }
}

const handleLogin = async () => {
  if (!loginFormRef.value) return
  
  await loginFormRef.value.validate(async (valid) => {
    if (valid) {
      loading.value = true
      try {
        await userStore.loginAction(loginForm)
        
        if (loginForm.rememberMe) {
          localStorage.setItem('lis_username', loginForm.username)
          localStorage.setItem('lis_remember', 'true')
        } else {
          localStorage.removeItem('lis_username')
          localStorage.removeItem('lis_remember')
        }
        
        ElMessage.success('登录成功')
        
        const redirectPath = route.query.redirect as string || '/dashboard'
        router.push(redirectPath)
      } catch (error: any) {
        refreshCaptcha()
        loginForm.captcha = ''
        ElMessage.error(error.message || '登录失败')
      } finally {
        loading.value = false
      }
    }
  })
}

onMounted(() => {
  refreshCaptcha()
  
  const savedUsername = localStorage.getItem('lis_username')
  const savedRemember = localStorage.getItem('lis_remember')
  
  if (savedRemember === 'true' && savedUsername) {
    loginForm.username = savedUsername
    loginForm.rememberMe = true
  }
})
</script>

<style lang="scss" scoped>
$primary-color: #409eff;
$text-primary: #303133;
$text-secondary: #909399;
$border-color-base: #dcdfe6;

.login-container {
  width: 100%;
  height: 100vh;
  overflow: hidden;
}

.login-background {
  width: 100%;
  height: 100%;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
}

.login-content {
  width: 420px;
  padding: 40px;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 12px;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.2);
  position: relative;
  z-index: 1;
}

.login-header {
  text-align: center;
  margin-bottom: 30px;
  
  .logo {
    width: 60px;
    height: 60px;
    margin-bottom: 15px;
  }
  
  .title {
    font-size: 24px;
    font-weight: 600;
    color: $text-primary;
    margin: 0 0 8px;
  }
  
  .subtitle {
    font-size: 14px;
    color: $text-secondary;
    margin: 0;
  }
}

.login-form {
  .captcha-row {
    display: flex;
    gap: 12px;
    width: 100%;
    
    .el-input {
      flex: 1;
    }
    
    .captcha-image {
      width: 120px;
      height: 40px;
      border: 1px solid $border-color-base;
      border-radius: 4px;
      cursor: pointer;
      overflow: hidden;
      display: flex;
      align-items: center;
      justify-content: center;
      background: #f5f5f5;
      
      img {
        width: 100%;
        height: 100%;
        object-fit: cover;
      }
      
      span {
        font-size: 12px;
        color: $text-secondary;
      }
      
      &:hover {
        border-color: $primary-color;
      }
    }
  }
  
  .login-options {
    width: 100%;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  
  .login-btn {
    width: 100%;
    height: 44px;
    font-size: 16px;
  }
}

.login-footer {
  text-align: center;
  margin-top: 30px;
  
  p {
    font-size: 12px;
    color: $text-secondary;
    margin: 0;
  }
}
</style>
