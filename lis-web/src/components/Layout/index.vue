<template>
  <el-container class="layout-container">
    <Sidebar />
    <el-container class="main-container" :class="{ 'sidebar-collapsed': sidebarCollapsed }">
      <Navbar />
      <TagsView v-if="showTagsView" />
      <el-main class="main-content">
        <router-view v-slot="{ Component, route }">
          <transition name="fade-transform" mode="out-in">
            <keep-alive :include="cachedViews">
              <component :is="Component" :key="route.path" />
            </keep-alive>
          </transition>
        </router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useAppStore } from '@/stores/modules/app'
import Sidebar from './Sidebar.vue'
import Navbar from './Navbar.vue'
import TagsView from './TagsView.vue'

const appStore = useAppStore()

const sidebarCollapsed = computed(() => appStore.sidebarCollapsed)
const showTagsView = computed(() => true)
const cachedViews = computed(() => [])
</script>

<style lang="scss" scoped>
$sidebar-width: 210px;
$sidebar-collapsed-width: 64px;
$navbar-height: 50px;
$transition-duration: 0.3s;
$bg-color-page: #f2f3f5;

.layout-container {
  width: 100%;
  height: 100vh;
  overflow: hidden;
}

.main-container {
  margin-left: $sidebar-width;
  transition: margin-left $transition-duration;
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  
  &.sidebar-collapsed {
    margin-left: $sidebar-collapsed-width;
  }
}

.main-content {
  flex: 1;
  padding: 20px;
  background-color: $bg-color-page;
  overflow: auto;
}
</style>
