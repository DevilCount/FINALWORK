import { get, post, put, del } from '@/utils/request'
import type { PageParams, PageResult } from '@/types/api'

export interface User {
  id: number
  username: string
  realName: string
  email: string
  phone: string
  gender: 'male' | 'female' | 'unknown'
  status: 'normal' | 'disable'
  deptId: number
  deptName: string
  roleIds: number[]
  roleNames: string[]
  createTime: string
  updateTime: string
  avatar?: string
}

export interface UserForm {
  id?: number
  username: string
  realName: string
  email: string
  phone: string
  gender: 'male' | 'female' | 'unknown'
  status: 'normal' | 'disable'
  deptId: number | null
  roleIds: number[]
  password?: string
  avatar?: string
}

export interface UserQuery extends PageParams {
  username?: string
  realName?: string
  phone?: string
  status?: string
  deptId?: number
  startTime?: string
  endTime?: string
}

export function getUserList(params: UserQuery): Promise<PageResult<User>> {
  return get('/system/user/list', params)
}

export function getUserById(id: number): Promise<User> {
  return get(`/system/user/${id}`)
}

export function createUser(data: UserForm): Promise<void> {
  return post('/system/user', data)
}

export function updateUser(data: UserForm): Promise<void> {
  return put('/system/user', data)
}

export function deleteUser(id: number): Promise<void> {
  return del(`/system/user/${id}`)
}

export function batchDeleteUsers(ids: number[]): Promise<void> {
  return del('/system/user/batch', { ids })
}

export function updateUserStatus(id: number, status: 'normal' | 'disable'): Promise<void> {
  return put(`/system/user/${id}/status`, { status })
}

export function resetUserPassword(id: number): Promise<void> {
  return put(`/system/user/${id}/reset-password`)
}

export function getUserProfile(): Promise<User> {
  return get('/system/user/profile')
}

export function updateUserProfile(data: Partial<User>): Promise<void> {
  return put('/system/user/profile', data)
}

export function uploadAvatar(file: File): Promise<{ url: string }> {
  const formData = new FormData()
  formData.append('file', file)
  return post('/system/user/avatar', formData)
}
