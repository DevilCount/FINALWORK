<template>
  <div class="tags-view">
    <el-scrollbar class="tags-scroll">
      <router-link
        v-for="tag in visitedViews"
        :key="tag.path"
        :to="{ path: tag.path, query: tag.query as any }"
        class="tags-item"
        :class="{ active: isActive(tag) }"
        @contextmenu.prevent="openMenu(tag, $event)"
      >
        <span class="tag-title">{{ tag.title }}</span>
        <el-icon
          v-if="!isAffix(tag)"
          class="tag-close"
          @click.prevent.stop="closeTag(tag)"
        >
          <Close />
        </el-icon>
      </router-link>
    </el-scrollbar>
    
    <ul v-show="visible" class="context-menu" :style="{ left: left + 'px', top: top + 'px' }">
      <li @click="refreshSelectedTag">刷新</li>
      <li v-if="!isAffix(selectedTag)" @click="closeTag(selectedTag)">关闭</li>
      <li @click="closeOtherTags">关闭其他</li>
      <li @click="closeAllTags">关闭所有</li>
    </ul>
  </div>
</template>

<script setup lang="ts">
import { ref, watch, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import type { TagView } from '@/types/router'

const route = useRoute()
const router = useRouter()

const visitedViews = ref<TagView[]>([])
const selectedTag = ref<TagView>({} as TagView)
const visible = ref(false)
const top = ref(0)
const left = ref(0)

const isActive = (tag: TagView) => {
  return tag.path === route.path
}

const isAffix = (tag: TagView) => {
  return tag.meta?.affix
}

const addViewTags = () => {
  if (route.name && !route.meta?.hidden) {
    const { path, name, meta, query } = route
    const tag: TagView = {
      path,
      name: name as string,
      meta,
      query: query as Record<string, string>,
      title: meta?.title as string || 'no-name',
      fullPath: route.fullPath,
    }
    
    const isExist = visitedViews.value.some((v) => v.path === path)
    if (!isExist) {
      visitedViews.value.push(tag)
    }
  }
}

const closeTag = (tag: TagView) => {
  const index = visitedViews.value.findIndex((v) => v.path === tag.path)
  visitedViews.value.splice(index, 1)
  
  if (isActive(tag)) {
    const prevTag = visitedViews.value[index - 1]
    const nextTag = visitedViews.value[index]
    if (nextTag) {
      router.push(nextTag.path)
    } else if (prevTag) {
      router.push(prevTag.path)
    } else {
      router.push('/dashboard/index')
    }
  }
}

const closeOtherTags = () => {
  visitedViews.value = visitedViews.value.filter(
    (tag) => isAffix(tag) || tag.path === selectedTag.value.path
  )
}

const closeAllTags = () => {
  visitedViews.value = visitedViews.value.filter((tag) => isAffix(tag))
  router.push('/dashboard/index')
}

const refreshSelectedTag = () => {
  router.replace({ path: '/redirect' + selectedTag.value.path })
}

const openMenu = (tag: TagView, e: MouseEvent) => {
  const menuMinWidth = 105
  const offsetLeft = document.body.getBoundingClientRect().left
  const offsetWidth = document.body.offsetWidth
  const maxLeft = offsetWidth - menuMinWidth
  const l = e.clientX - offsetLeft + 15
  
  left.value = l > maxLeft ? maxLeft : l
  top.value = e.clientY
  visible.value = true
  selectedTag.value = tag
}

const closeMenu = () => {
  visible.value = false
}

watch(
  () => route.path,
  () => {
    addViewTags()
  }
)

watch(visible, (value) => {
  if (value) {
    document.body.addEventListener('click', closeMenu)
  } else {
    document.body.removeEventListener('click', closeMenu)
  }
})

onMounted(() => {
  addViewTags()
  
  visitedViews.value.push({
    path: '/dashboard/index',
    name: 'Dashboard',
    meta: { title: '首页', affix: true },
    title: '首页',
    fullPath: '/dashboard/index',
  })
})
</script>

<style lang="scss" scoped>
$primary-color: #409eff;
$text-primary: #303133;
$text-regular: #606266;
$text-secondary: #909399;
$border-color-light: #e4e7ed;
$border-color-lighter: #ebeef5;
$bg-color: #f5f7fa;

.tags-view {
  height: 34px;
  background: #fff;
  border-bottom: 1px solid $border-color-lighter;
  box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.04);
  position: relative;
}

.tags-scroll {
  height: 100%;
  
  :deep(.el-scrollbar__view) {
    display: flex;
    align-items: center;
    height: 100%;
    padding: 0 10px;
  }
}

.tags-item {
  display: inline-flex;
  align-items: center;
  height: 26px;
  padding: 0 8px;
  margin-right: 5px;
  font-size: 12px;
  color: $text-regular;
  background: #fff;
  border: 1px solid $border-color-light;
  border-radius: 3px;
  text-decoration: none;
  cursor: pointer;
  
  &:hover {
    color: $primary-color;
  }
  
  &.active {
    color: #fff;
    background-color: $primary-color;
    border-color: $primary-color;
    
    .tag-close {
      color: #fff;
    }
  }
  
  .tag-title {
    max-width: 100px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
  
  .tag-close {
    margin-left: 5px;
    font-size: 12px;
    border-radius: 50%;
    
    &:hover {
      background-color: rgba(0, 0, 0, 0.1);
    }
  }
}

.context-menu {
  position: absolute;
  margin: 0;
  padding: 5px 0;
  background: #fff;
  border-radius: 4px;
  box-shadow: 2px 2px 3px 0 rgba(0, 0, 0, 0.3);
  list-style-type: none;
  z-index: 3000;
  
  li {
    padding: 7px 16px;
    font-size: 12px;
    color: $text-primary;
    cursor: pointer;
    
    &:hover {
      background: $bg-color;
    }
  }
}
</style>
