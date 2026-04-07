import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useAppStore = defineStore(
  'app',
  () => {
    const sidebar = ref(true)
    const device = ref<'desktop' | 'mobile'>('desktop')
    const size = ref<'default' | 'small' | 'large'>('default')
    const theme = ref<'light' | 'dark'>('light')

    function toggleSidebar() {
      sidebar.value = !sidebar.value
    }

    function closeSidebar() {
      sidebar.value = false
    }

    function toggleDevice(val: 'desktop' | 'mobile') {
      device.value = val
    }

    function setSize(val: 'default' | 'small' | 'large') {
      size.value = val
    }

    function toggleTheme() {
      theme.value = theme.value === 'light' ? 'dark' : 'light'
    }

    return {
      sidebar,
      device,
      size,
      theme,
      toggleSidebar,
      closeSidebar,
      toggleDevice,
      setSize,
      toggleTheme,
    }
  },
  {
    persist: {
      key: 'lis-app',
      storage: localStorage,
    },
  }
)
