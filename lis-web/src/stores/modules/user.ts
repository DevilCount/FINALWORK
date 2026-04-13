// 用户状态管理模块，包含登录、用户信息获取和权限管理等功能
import { defineStore } from 'pinia'
import { login, logout, getUserInfo } from '@/api/auth'
import { getToken, setToken, removeToken, getRefreshToken, setRefreshToken, removeRefreshToken, setUserInfo, removeUserInfo } from '@/utils/auth'
import type { UserInfo } from '@/types/user'
import router from '@/router'
import { usePermissionStore } from '@/stores/modules/permission'

// 用户状态接口定义
interface UserState {
  token: string
  refreshToken: string
  userInfo: UserInfo | null
  roles: string[]
  permissions: string[]
}

// 定义用户状态 store
export const useUserStore = defineStore('user', {
  // 状态定义
  state: (): UserState => ({
    token: getToken() || '',
    refreshToken: getRefreshToken() || '',
    userInfo: null,
    roles: [],
    permissions: [],
  }),

  // 计算属性
  getters: {
    isLoggedIn: (state) => !!state.token,
    username: (state) => state.userInfo?.username || '',
    realName: (state) => state.userInfo?.realName || '',
    avatar: (state) => state.userInfo?.avatar || '',
    userId: (state) => state.userInfo?.id || 0,
  },

  // 动作方法
  actions: {
    /**
     * 登录操作
     * @param loginForm 登录表单数据
     */
    async loginAction(loginForm: { username: string; password: string; code?: string; uuid?: string }) {
      const data = await login(loginForm)
      this.token = data.accessToken
      this.refreshToken = data.refreshToken
      setToken(data.accessToken)
      setRefreshToken(data.refreshToken)
      return data
    },

    /**
     * 获取用户信息
     */
    async getUserInfoAction() {
      const data = await getUserInfo()
      this.userInfo = data.user
      this.roles = data.roles
      this.permissions = data.permissions
      setUserInfo(data.user)
      return data
    },

    /**
     * 登出操作
     */
    async logoutAction() {
      try {
        await logout()
      } finally {
        this.resetStateAction()
        router.push('/login')
      }
    },

    /**
     * 重置用户状态
     */
    resetStateAction() {
      this.token = ''
      this.refreshToken = ''
      this.userInfo = null
      this.roles = []
      this.permissions = []
      removeToken()
      removeRefreshToken()
      removeUserInfo()
      const permissionStore = usePermissionStore()
      permissionStore.resetRoutesAction()
    },

    /**
     * 设置访问令牌
     * @param accessToken 访问令牌
     * @param newRefreshToken 刷新令牌（可选）
     */
    setTokenAction(accessToken: string, newRefreshToken?: string) {
      this.token = accessToken
      if (newRefreshToken) {
        this.refreshToken = newRefreshToken
        setRefreshToken(newRefreshToken)
      }
      setToken(accessToken)
    },

    /**
     * 检查是否拥有单个权限
     * @param permission 权限标识
     */
    hasPermission(permission: string): boolean {
      if (this.roles && this.roles.includes('admin')) return true
      return this.permissions && this.permissions.includes(permission)
    },

    /**
     * 检查是否拥有指定权限中的任意一个
     * @param permissions 权限标识数组
     */
    hasPermissions(permissions: string[]): boolean {
      if (this.roles && this.roles.includes('admin')) return true
      return permissions && permissions.some((p) => this.permissions && this.permissions.includes(p))
    },

    /**
     * 检查是否拥有指定角色
     * @param role 角色标识
     */
    hasRole(role: string): boolean {
      return this.roles && this.roles.includes(role)
    },

    /**
     * 检查是否拥有指定角色中的任意一个
     * @param roles 角色标识数组
     */
    hasRoles(roles: string[]): boolean {
      return roles && roles.some((r) => this.roles && this.roles.includes(r))
    },
  },
})
