<template>
  <div class="navbar">
    <div class="navbar-left">
      <div class="hamburger" @click="toggleSidebar">
        <el-icon :size="20">
          <Fold v-if="!sidebarCollapsed" />
          <Expand v-else />
        </el-icon>
      </div>
      <Breadcrumb class="breadcrumb" />
    </div>
    
    <div class="navbar-right">
      <div class="navbar-item" @click="toggleFullscreen">
        <el-tooltip content="全屏" placement="bottom">
          <el-icon :size="18">
            <FullScreen />
          </el-icon>
        </el-tooltip>
      </div>
      
      <div class="navbar-item message-item">
        <el-tooltip content="消息通知" placement="bottom">
          <el-badge :value="unreadCount" :hidden="unreadCount === 0" :max="99">
            <el-icon :size="18">
              <Bell />
            </el-icon>
          </el-badge>
        </el-tooltip>
        <MessageDropdown />
      </div>
      
      <el-dropdown class="user-dropdown" trigger="click" @command="handleCommand">
        <div class="user-info">
          <el-avatar :size="32" :src="avatar">
            <el-icon :size="20"><UserFilled /></el-icon>
          </el-avatar>
          <span class="username">{{ realName }}</span>
          <el-icon class="dropdown-icon"><ArrowDown /></el-icon>
        </div>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item command="profile">
              <el-icon><User /></el-icon>
              个人中心
            </el-dropdown-item>
            <el-dropdown-item command="password">
              <el-icon><Lock /></el-icon>
              修改密码
            </el-dropdown-item>
            <el-dropdown-item divided command="logout">
              <el-icon><SwitchButton /></el-icon>
              退出登录
            </el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessageBox, ElMessage } from 'element-plus'
import { useAppStore } from '@/stores/modules/app'
import { useUserStore } from '@/stores/modules/user'
import Breadcrumb from './Breadcrumb.vue'
import MessageDropdown from './MessageDropdown.vue'

const router = useRouter()
const appStore = useAppStore()
const userStore = useUserStore()

const sidebarCollapsed = computed(() => appStore.sidebarCollapsed)
const realName = computed(() => userStore.realName)
const avatar = computed(() => userStore.avatar)
const unreadCount = computed(() => 3)

const toggleSidebar = () => {
  appStore.toggleSidebarAction()
}

const toggleFullscreen = () => {
  if (!document.fullscreenElement) {
    document.documentElement.requestFullscreen()
  } else {
    document.exitFullscreen()
  }
}

const handleCommand = async (command: string) => {
  switch (command) {
    case 'profile':
      router.push('/profile')
      break
    case 'password':
      router.push('/password')
      break
    case 'logout':
      ElMessageBox.confirm('确定要退出登录吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(async () => {
        await userStore.logoutAction()
        ElMessage.success('退出成功')
      }).catch(() => {})
      break
  }
}
</script>

<style lang="scss" scoped>
$navbar-height: 50px;
$text-primary: #303133;
$text-secondary: #909399;

.navbar {
  height: $navbar-height;
  background-color: #fff;
  box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
  position: sticky;
  top: 0;
  z-index: 100;
}

.navbar-left {
  display: flex;
  align-items: center;
  
  .hamburger {
    cursor: pointer;
    padding: 10px;
    display: flex;
    align-items: center;
    justify-content: center;
    
    &:hover {
      background-color: rgba(0, 0, 0, 0.05);
      border-radius: 4px;
    }
  }
  
  .breadcrumb {
    margin-left: 10px;
  }
}

.navbar-right {
  display: flex;
  align-items: center;
  gap: 10px;
}

.navbar-item {
  padding: 8px 12px;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 4px;
  
  &:hover {
    background-color: rgba(0, 0, 0, 0.05);
  }
}

.message-item {
  position: relative;
}

.user-dropdown {
  cursor: pointer;
  
  .user-info {
    display: flex;
    align-items: center;
    gap: 8px;
    padding: 5px 10px;
    border-radius: 4px;
    
    &:hover {
      background-color: rgba(0, 0, 0, 0.05);
    }
  }
  
  .username {
    font-size: 14px;
    color: $text-primary;
    max-width: 100px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
  
  .dropdown-icon {
    color: $text-secondary;
    font-size: 12px;
  }
}
</style>
