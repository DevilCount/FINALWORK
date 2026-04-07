import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import type { UserInfo } from '@/types'

export const useUserStore = defineStore(
  'user',
  () => {
    const token = ref<string>('')
    const refreshToken = ref<string>('')
    const userInfo = ref<UserInfo | null>(null)

    const isLoggedIn = computed(() => !!token.value)
    const username = computed(() => userInfo.value?.username || '')
    const realName = computed(() => userInfo.value?.realName || '')
    const roles = computed(() => userInfo.value?.roles || [])
    const permissions = computed(() => userInfo.value?.permissions || [])

    function setToken(accessToken: string, refresh: string) {
      token.value = accessToken
      refreshToken.value = refresh
    }

    function setUserInfo(info: UserInfo) {
      userInfo.value = info
    }

    function logout() {
      token.value = ''
      refreshToken.value = ''
      userInfo.value = null
    }

    function hasPermission(permission: string): boolean {
      return permissions.value.includes(permission) || permissions.value.includes('*:*:*')
    }

    function hasRole(role: string): boolean {
      return roles.value.includes(role) || roles.value.includes('admin')
    }

    return {
      token,
      refreshToken,
      userInfo,
      isLoggedIn,
      username,
      realName,
      roles,
      permissions,
      setToken,
      setUserInfo,
      logout,
      hasPermission,
      hasRole,
    }
  },
  {
    persist: {
      key: 'lis-user',
      storage: localStorage,
      pick: ['token', 'refreshToken', 'userInfo'],
    },
  }
)
