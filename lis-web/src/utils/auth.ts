// 认证相关工具函数，用于管理本地存储中的用户登录信息
const TOKEN_KEY = 'lis_token'
const REFRESH_TOKEN_KEY = 'lis_refresh_token'
const USER_INFO_KEY = 'lis_user_info'

/**
 * 获取访问令牌
 * @returns {string | null} 访问令牌或 null
 */
export function getToken(): string | null {
  return localStorage.getItem(TOKEN_KEY)
}

/**
 * 存储访问令牌
 * @param token 访问令牌
 */
export function setToken(token: string): void {
  localStorage.setItem(TOKEN_KEY, token)
}

/**
 * 移除访问令牌
 */
export function removeToken(): void {
  localStorage.removeItem(TOKEN_KEY)
}

/**
 * 获取刷新令牌
 * @returns {string | null} 刷新令牌或 null
 */
export function getRefreshToken(): string | null {
  return localStorage.getItem(REFRESH_TOKEN_KEY)
}

/**
 * 存储刷新令牌
 * @param token 刷新令牌
 */
export function setRefreshToken(token: string): void {
  localStorage.setItem(REFRESH_TOKEN_KEY, token)
}

/**
 * 移除刷新令牌
 */
export function removeRefreshToken(): void {
  localStorage.removeItem(REFRESH_TOKEN_KEY)
}

/**
 * 获取用户信息
 * @returns {any} 用户信息对象或 null
 */
export function getUserInfo(): any {
  const info = localStorage.getItem(USER_INFO_KEY)
  return info ? JSON.parse(info) : null
}

/**
 * 存储用户信息
 * @param info 用户信息对象
 */
export function setUserInfo(info: any): void {
  localStorage.setItem(USER_INFO_KEY, JSON.stringify(info))
}

/**
 * 移除用户信息
 */
export function removeUserInfo(): void {
  localStorage.removeItem(USER_INFO_KEY)
}

/**
 * 清除所有认证相关信息
 */
export function clearAuth(): void {
  removeToken()
  removeRefreshToken()
  removeUserInfo()
}
