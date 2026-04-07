import { defineStore } from 'pinia'
import { login, logout, getUserInfo } from '@/api/auth'
import { getToken, setToken, removeToken, getRefreshToken, setRefreshToken, removeRefreshToken, setUserInfo, removeUserInfo } from '@/utils/auth'
import type { UserInfo, LoginForm } from '@/types/user'
import router from '@/router'

interface UserState {
  token: string
  refreshToken: string
  userInfo: UserInfo | null
  roles: string[]
  permissions: string[]
}

export const useUserStore = defineStore('user', {
  state: (): UserState => ({
    token: getToken() || '',
    refreshToken: getRefreshToken() || '',
    userInfo: null,
    roles: [],
    permissions: [],
  }),

  getters: {
    isLoggedIn: (state) => !!state.token,
    username: (state) => state.userInfo?.username || '',
    realName: (state) => state.userInfo?.realName || '',
    avatar: (state) => state.userInfo?.avatar || '',
    userId: (state) => state.userInfo?.id || 0,
  },

  actions: {
    async loginAction(loginForm: LoginForm) {
      const data = await login(loginForm)
      this.token = data.accessToken
      this.refreshToken = data.refreshToken
      setToken(data.accessToken)
      setRefreshToken(data.refreshToken)
      return data
    },

    async getUserInfoAction() {
      const data = await getUserInfo()
      this.userInfo = data.user
      this.roles = data.roles
      this.permissions = data.permissions
      setUserInfo(data.user)
      return data
    },

    async logoutAction() {
      try {
        await logout()
      } finally {
        this.resetStateAction()
        router.push('/login')
      }
    },

    resetStateAction() {
      this.token = ''
      this.refreshToken = ''
      this.userInfo = null
      this.roles = []
      this.permissions = []
      removeToken()
      removeRefreshToken()
      removeUserInfo()
    },

    hasPermission(permission: string): boolean {
      if (this.roles.includes('admin')) return true
      return this.permissions.includes(permission)
    },

    hasPermissions(permissions: string[]): boolean {
      if (this.roles.includes('admin')) return true
      return permissions.some((p) => this.permissions.includes(p))
    },

    hasRole(role: string): boolean {
      return this.roles.includes(role)
    },

    hasRoles(roles: string[]): boolean {
      return roles.some((r) => this.roles.includes(r))
    },
  },

  persist: true,
})
