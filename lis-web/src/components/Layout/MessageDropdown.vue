<template>
  <el-dropdown trigger="click" class="message-dropdown">
    <template #default>
      <span></span>
    </template>
    <template #dropdown>
      <el-dropdown-menu class="message-menu">
        <el-tabs v-model="activeTab">
          <el-tab-pane label="通知" name="notice">
            <div class="message-list">
              <div
                v-for="msg in noticeList"
                :key="msg.id"
                class="message-item"
                @click="handleRead(msg)"
              >
                <div class="message-content">
                  <div class="message-title">{{ msg.title }}</div>
                  <div class="message-time">{{ msg.time }}</div>
                </div>
                <el-tag v-if="!msg.read" type="danger" size="small">未读</el-tag>
              </div>
              <el-empty v-if="noticeList.length === 0" description="暂无通知" />
            </div>
          </el-tab-pane>
          <el-tab-pane label="危急值" name="critical">
            <div class="message-list">
              <div
                v-for="msg in criticalList"
                :key="msg.id"
                class="message-item critical"
                @click="handleRead(msg)"
              >
                <div class="message-content">
                  <div class="message-title">
                    <el-icon class="warning-icon"><Warning /></el-icon>
                    {{ msg.title }}
                  </div>
                  <div class="message-time">{{ msg.time }}</div>
                </div>
                <el-tag type="danger" size="small">待处理</el-tag>
              </div>
              <el-empty v-if="criticalList.length === 0" description="暂无危急值" />
            </div>
          </el-tab-pane>
        </el-tabs>
        <div class="message-footer">
          <el-button type="primary" link @click="handleViewAll">查看全部</el-button>
        </div>
      </el-dropdown-menu>
    </template>
  </el-dropdown>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'

interface Message {
  id: number
  title: string
  time: string
  read: boolean
  type: string
}

const router = useRouter()

const activeTab = ref('notice')

const noticeList = ref<Message[]>([
  { id: 1, title: '系统维护通知：今晚22:00进行系统升级', time: '10分钟前', read: false, type: 'notice' },
  { id: 2, title: '新用户注册审核待处理', time: '1小时前', read: false, type: 'notice' },
  { id: 3, title: '设备校准提醒：血细胞分析仪', time: '2小时前', read: true, type: 'notice' },
])

const criticalList = ref<Message[]>([
  { id: 1, title: '患者张三 血钾危急值 6.8mmol/L', time: '5分钟前', read: false, type: 'critical' },
  { id: 2, title: '患者李四 血糖危急值 28.5mmol/L', time: '15分钟前', read: false, type: 'critical' },
])

const handleRead = (msg: Message) => {
  msg.read = true
  if (msg.type === 'critical') {
    router.push('/report/critical')
  }
}

const handleViewAll = () => {
  router.push('/message')
}
</script>

<style lang="scss" scoped>
$text-primary: #303133;
$text-secondary: #909399;
$border-color-lighter: #ebeef5;
$bg-color: #f5f7fa;
$danger-color: #f56c6c;

.message-dropdown {
  display: none;
}

.message-menu {
  width: 360px;
  padding: 0;
  
  :deep(.el-dropdown-menu__item) {
    padding: 0;
  }
}

.message-list {
  max-height: 300px;
  overflow-y: auto;
}

.message-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 12px 16px;
  cursor: pointer;
  border-bottom: 1px solid $border-color-lighter;
  
  &:hover {
    background-color: $bg-color;
  }
  
  &:last-child {
    border-bottom: none;
  }
  
  &.critical {
    background-color: #fef0f0;
    
    &:hover {
      background-color: #fde2e2;
    }
  }
}

.message-content {
  flex: 1;
  min-width: 0;
}

.message-title {
  font-size: 14px;
  color: $text-primary;
  margin-bottom: 4px;
  display: flex;
  align-items: center;
  gap: 4px;
  
  .warning-icon {
    color: $danger-color;
  }
}

.message-time {
  font-size: 12px;
  color: $text-secondary;
}

.message-footer {
  padding: 10px;
  text-align: center;
  border-top: 1px solid $border-color-lighter;
}

:deep(.el-tabs__header) {
  margin: 0;
  padding: 0 16px;
}

:deep(.el-tabs__nav-wrap::after) {
  display: none;
}

:deep(.el-tabs__item) {
  font-size: 14px;
}
</style>
