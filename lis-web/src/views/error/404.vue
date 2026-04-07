<template>
  <div class="not-found">
    <div class="content">
      <h1>404</h1>
      <p>抱歉，您访问的页面不存在</p>
      <el-button type="primary" @click="goHome">返回首页</el-button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'

const router = useRouter()

function goHome() {
  router.push('/')
}

defineOptions({
  name: 'NotFound',
})
</script>

<style scoped>
.not-found {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #f0f2f5;
}

.content {
  text-align: center;
}

.content h1 {
  font-size: 120px;
  color: #409eff;
  margin-bottom: 20px;
}

.content p {
  font-size: 18px;
  color: #606266;
  margin-bottom: 30px;
}
</style>
