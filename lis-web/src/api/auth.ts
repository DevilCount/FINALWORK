import { get, post } from '@/utils/request'
import type { LoginForm, LoginResult, CaptchaResult, UserInfo } from '@/types/user'

export function login(data: LoginForm): Promise<LoginResult> {
  return post('/api/auth/login', data)
}

export function logout(): Promise<void> {
  return post('/api/auth/logout')
}

export function refreshToken(refreshToken: string): Promise<LoginResult> {
  return post('/api/auth/refresh', { refreshToken })
}

export function getCaptcha(): Promise<CaptchaResult> {
  return get('/api/auth/captcha')
}

export function getUserInfo(): Promise<{ user: UserInfo; roles: string[]; permissions: string[] }> {
  return get('/api/auth/user-info')
}

export function updatePassword(data: { oldPassword: string; newPassword: string }): Promise<void> {
  return post('/api/auth/password', data)
}
