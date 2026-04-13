// 应用全局状态管理，包含侧边栏、设备、主题和尺寸等配置
import { defineStore } from 'pinia'

type DeviceType = 'desktop' | 'mobile'
type ThemeType = 'light' | 'dark'

// 应用状态接口定义
interface AppState {
  sidebarCollapsed: boolean
  device: DeviceType
  theme: ThemeType
  size: 'default' | 'small' | 'large'
}

// 定义应用状态 store
export const useAppStore = defineStore('app', {
  // 状态定义
  state: (): AppState => ({
    sidebarCollapsed: false,
    device: 'desktop',
    theme: 'light',
    size: 'default',
  }),

  // 计算属性
  getters: {
    isMobile: (state) => state.device === 'mobile',
    isDark: (state) => state.theme === 'dark',
  },

  // 动作方法
  actions: {
    /**
     * 切换侧边栏折叠状态
     */
    toggleSidebarAction() {
      this.sidebarCollapsed = !this.sidebarCollapsed
    },

    /**
     * 设置侧边栏折叠状态
     * @param collapsed 是否折叠
     */
    setSidebarCollapsedAction(collapsed: boolean) {
      this.sidebarCollapsed = collapsed
    },

    /**
     * 设置设备类型
     * @param device 设备类型
     */
    setDeviceAction(device: DeviceType) {
      this.device = device
      if (device === 'mobile') {
        this.sidebarCollapsed = true
      }
    },

    /**
     * 设置主题
     * @param theme 主题类型
     */
    setThemeAction(theme: ThemeType) {
      this.theme = theme
      document.documentElement.setAttribute('data-theme', theme)
    },

    /**
     * 切换主题（在浅色和深色之间切换）
     */
    toggleThemeAction() {
      this.setThemeAction(this.theme === 'light' ? 'dark' : 'light')
    },

    /**
     * 设置组件尺寸
     * @param size 尺寸类型
     */
    setSizeAction(size: 'default' | 'small' | 'large') {
      this.size = size
    },
  },

  persist: true,
})
