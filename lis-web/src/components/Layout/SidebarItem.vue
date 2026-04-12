<template>
  <template v-if="!item.meta?.hidden">
    <el-sub-menu
      v-if="hasChildren"
      :index="resolvePath(item.path)"
      :popper-class="collapsed ? 'sidebar-popper' : ''"
    >
      <template #title>
        <el-icon v-if="item.meta?.icon">
          <component :is="item.meta.icon" />
        </el-icon>
        <span>{{ item.meta?.title }}</span>
      </template>
      <SidebarItem
        v-for="child in item.children"
        :key="child.path"
        :item="child"
        :base-path="resolvePath(item.path)"
      />
    </el-sub-menu>
    
    <el-menu-item
      v-else
      :index="resolvePath(item.path)"
    >
      <el-icon v-if="item.meta?.icon">
        <component :is="item.meta.icon" />
      </el-icon>
      <template #title>
        <span>{{ item.meta?.title }}</span>
      </template>
    </el-menu-item>
  </template>
</template>

<script setup lang="ts">
import { computed, inject } from 'vue'
import type { AppRouteRecordRaw } from '@/types/router'

interface Props {
  item: AppRouteRecordRaw
  basePath?: string
}

const props = withDefaults(defineProps<Props>(), {
  basePath: '',
})

const collapsed = inject('collapsed', false)

const hasChildren = computed(() => {
  const children = props.item.children?.filter((child) => !child.meta?.hidden)
  return children && children.length > 0
})

const resolvePath = (path: string): string => {
  if (path.startsWith('/')) {
    return path
  }
  if (props.basePath.endsWith('/')) {
    return props.basePath + path
  }
  return props.basePath + '/' + path
}
</script>

<style lang="scss" scoped>
.el-icon {
  margin-right: 8px;
  font-size: 18px;
}
</style>
