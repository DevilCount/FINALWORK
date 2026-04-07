<template>
  <div class="error-page">
    <div class="error-content">
      <div class="error-code">403</div>
      <div class="error-desc">
        <h2>无访问权限</h2>
        <p>抱歉，您没有权限访问此页面</p>
      </div>
      <div class="error-actions">
        <el-button type="primary" @click="goHome">返回首页</el-button>
        <el-button @click="goBack">返回上一页</el-button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'

const router = useRouter()

const goHome = () => {
  router.push('/')
}

const goBack = () => {
  router.go(-1)
}
</script>

<style lang="scss" scoped>
.error-page {
  width: 100%;
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #f5f7fa 0%, #e4e7ed 100%);
}

.error-content {
  text-align: center;
}

.error-code {
  font-size: 120px;
  font-weight: 700;
  color: #e6a23c;
  line-height: 1;
  margin-bottom: 20px;
  text-shadow: 2px 2px 4px rgba(230, 162, 60, 0.2);
}

.error-desc {
  h2 {
    font-size: 24px;
    color: #303133;
    margin: 0 0 10px;
  }
  
  p {
    font-size: 14px;
    color: #909399;
    margin: 0 0 30px;
  }
}

.error-actions {
  display: flex;
  gap: 15px;
  justify-content: center;
}
</style>
