import { get, post } from '@/utils/request'
import type { LoginResult, CaptchaResult, UserInfo } from '@/types/user'

export function login(data: { username: string; password: string; code?: string; uuid?: string }): Promise<LoginResult> {
  return post('/auth/login', data)
}

export function logout(): Promise<void> {
  return post('/auth/logout')
}

export function refreshToken(refreshToken: string): Promise<LoginResult> {
  return post('/auth/refresh', { refreshToken })
}

export function getCaptcha(): Promise<CaptchaResult> {
  return get('/auth/captcha')
}

export function getUserInfo(): Promise<{ user: UserInfo; roles: string[]; permissions: string[] }> {
  return get('/auth/info')
}

export function updatePassword(data: { oldPassword: string; newPassword: string }): Promise<void> {
  return post('/auth/password', data)
}
