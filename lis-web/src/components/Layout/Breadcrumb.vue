<template>
  <el-breadcrumb class="breadcrumb" separator="/">
    <el-breadcrumb-item v-for="(item, index) in breadcrumbs" :key="item.path">
      <span v-if="index === breadcrumbs.length - 1" class="no-redirect">
        {{ item.meta?.title }}
      </span>
      <a v-else @click.prevent="handleLink(item)">
        {{ item.meta?.title }}
      </a>
    </el-breadcrumb-item>
  </el-breadcrumb>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue'
import { useRoute, useRouter, type RouteLocationMatched } from 'vue-router'

const route = useRoute()
const router = useRouter()

const breadcrumbs = ref<RouteLocationMatched[]>([])

const getBreadcrumbs = () => {
  const matched = route.matched.filter(
    (item) => item.meta && item.meta.title && !item.meta.hidden
  )
  
  const first = matched[0]
  if (first && first.path !== '/dashboard') {
    matched.unshift({
      path: '/dashboard',
      meta: { title: '首页' },
    } as any)
  }
  
  breadcrumbs.value = matched
}

const handleLink = (item: RouteLocationMatched) => {
  const { path, redirect } = item
  if (redirect) {
    router.push(redirect as string)
    return
  }
  router.push(path)
}

watch(
  () => route.path,
  () => {
    getBreadcrumbs()
  },
  { immediate: true }
)
</script>

<style lang="scss" scoped>
$text-primary: #303133;
$text-regular: #606266;
$primary-color: #409eff;

.breadcrumb {
  font-size: 14px;
  
  a {
    color: $text-regular;
    text-decoration: none;
    cursor: pointer;
    
    &:hover {
      color: $primary-color;
    }
  }
  
  .no-redirect {
    color: $text-primary;
    cursor: text;
  }
}
</style>
