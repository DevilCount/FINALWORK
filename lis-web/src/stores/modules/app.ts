import { defineStore } from 'pinia'

type DeviceType = 'desktop' | 'mobile'
type ThemeType = 'light' | 'dark'

interface AppState {
  sidebarCollapsed: boolean
  device: DeviceType
  theme: ThemeType
  size: 'default' | 'small' | 'large'
}

export const useAppStore = defineStore('app', {
  state: (): AppState => ({
    sidebarCollapsed: false,
    device: 'desktop',
    theme: 'light',
    size: 'default',
  }),

  getters: {
    isMobile: (state) => state.device === 'mobile',
    isDark: (state) => state.theme === 'dark',
  },

  actions: {
    toggleSidebarAction() {
      this.sidebarCollapsed = !this.sidebarCollapsed
    },

    setSidebarCollapsedAction(collapsed: boolean) {
      this.sidebarCollapsed = collapsed
    },

    setDeviceAction(device: DeviceType) {
      this.device = device
      if (device === 'mobile') {
        this.sidebarCollapsed = true
      }
    },

    setThemeAction(theme: ThemeType) {
      this.theme = theme
      document.documentElement.setAttribute('data-theme', theme)
    },

    toggleThemeAction() {
      this.setThemeAction(this.theme === 'light' ? 'dark' : 'light')
    },

    setSizeAction(size: 'default' | 'small' | 'large') {
      this.size = size
    },
  },

  persist: true,
})
