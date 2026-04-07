<template>
  <div class="sidebar" :class="{ 'is-collapsed': collapsed }">
    <div class="sidebar-logo">
      <router-link to="/" class="logo-link">
        <img src="@/assets/images/logo.svg" alt="LIS" class="logo-img" />
        <span v-show="!collapsed" class="logo-text">LIS系统</span>
      </router-link>
    </div>
    
    <el-scrollbar class="sidebar-menu-container">
      <el-menu
        :default-active="activeMenu"
        :collapse="collapsed"
        :unique-opened="true"
        :collapse-transition="false"
        background-color="#304156"
        text-color="#bfcbd9"
        active-text-color="#409EFF"
        router
      >
        <SidebarItem
          v-for="route in menuRoutes"
          :key="route.path"
          :item="route"
          :base-path="route.path"
        />
      </el-menu>
    </el-scrollbar>
    
    <div class="sidebar-footer">
      <span v-show="!collapsed" class="version">v1.0.0</span>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import { useAppStore } from '@/stores/modules/app'
import { usePermissionStore } from '@/stores/modules/permission'
import SidebarItem from './SidebarItem.vue'

const route = useRoute()
const appStore = useAppStore()
const permissionStore = usePermissionStore()

const collapsed = computed(() => appStore.sidebarCollapsed)
const activeMenu = computed(() => {
  const activeMenu = route.meta?.activeMenu as string
  return activeMenu || route.path
})
const menuRoutes = computed(() => permissionStore.menuRoutes)
</script>

<style lang="scss" scoped>
$sidebar-width: 210px;
$sidebar-collapsed-width: 64px;
$navbar-height: 50px;
$transition-duration: 0.3s;

.sidebar {
  width: $sidebar-width;
  height: 100vh;
  background-color: #304156;
  display: flex;
  flex-direction: column;
  position: fixed;
  left: 0;
  top: 0;
  z-index: 1001;
  transition: width $transition-duration;
  overflow: hidden;
  
  &.is-collapsed {
    width: $sidebar-collapsed-width;
    
    .logo-text,
    .version {
      display: none;
    }
    
    .logo-img {
      margin-right: 0;
    }
  }
}

.sidebar-logo {
  height: $navbar-height;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #263445;
  border-bottom: 1px solid #1f2d3d;
  
  .logo-link {
    display: flex;
    align-items: center;
    text-decoration: none;
  }
  
  .logo-img {
    width: 32px;
    height: 32px;
    margin-right: 10px;
  }
  
  .logo-text {
    font-size: 18px;
    font-weight: 600;
    color: #fff;
    white-space: nowrap;
  }
}

.sidebar-menu-container {
  flex: 1;
  overflow: hidden;
  
  :deep(.el-menu) {
    border-right: none;
  }
  
  :deep(.el-menu-item),
  :deep(.el-sub-menu__title) {
    height: 50px;
    line-height: 50px;
    
    &:hover {
      background-color: #263445 !important;
    }
  }
  
  :deep(.el-menu-item.is-active) {
    background-color: #409EFF !important;
    color: #fff !important;
  }
  
  :deep(.el-sub-menu .el-menu-item) {
    padding-left: 50px !important;
    min-width: auto;
  }
}

.sidebar-footer {
  height: 30px;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #263445;
  border-top: 1px solid #1f2d3d;
  
  .version {
    font-size: 12px;
    color: #8a979e;
  }
}
</style>
