// LIS 实验室信息管理系统主入口文件
import { createApp } from 'vue'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

import App from './App.vue'
import router from './router'
import pinia from './stores'
import { vPermission } from './directives/permission'
import 'element-plus/theme-chalk/el-message.css'
import 'element-plus/theme-chalk/el-message-box.css'
import 'element-plus/theme-chalk/el-notification.css'
import 'element-plus/theme-chalk/el-loading.css'
import './styles/index.css'

const app = createApp(App)

// 注册所有 Element Plus 图标组件
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}

// 注册权限指令
app.directive('permission', vPermission)

// 安装路由和状态管理插件
app.use(router)
app.use(pinia)

// 挂载应用到 DOM
app.mount('#app')
