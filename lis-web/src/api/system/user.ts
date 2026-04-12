import { get, post, put, del } from '@/utils/request'
import type { PageParams, PageResult } from '@/types/api'

export interface User {
  id: number
  username: string
  realName: string
  email: string
  phone: string
  gender: number
  status: number
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
  gender: number
  status: number
  deptId: number | null
  roleIds: number[]
  password?: string
}

export interface UserQuery extends PageParams {
  username?: string
  realName?: string
  phone?: string
  status?: number | string
  deptId?: number
  startTime?: string
  endTime?: string
}

function mapStatus(status: number | string): number {
  if (typeof status === 'number') return status
  if (status === 'normal') return 0
  if (status === 'disable') return 2
  return parseInt(status) || 0
}

function mapUserToBackend(data: UserForm): any {
  return {
    ...data,
    userName: data.username,
    nickName: data.realName,
  }
}

function mapUserQueryToBackend(params: UserQuery): any {
  return {
    ...params,
    userName: params.username,
    nickName: params.realName,
  }
}

function mapUserFromBackend(data: any): User {
  return {
    ...data,
    username: data.userName || data.username,
    realName: data.nickName || data.realName,
  }
}

function mapUserListFromBackend(result: unknown): PageResult<User> {
  const r = result as PageResult<any>
  return {
    ...r,
    records: r.records?.map(mapUserFromBackend) || [],
  }
}

export function getUserList(params: UserQuery): Promise<PageResult<User>> {
  const mappedParams = mapUserQueryToBackend(params)
  mappedParams.status = params.status !== undefined && params.status !== '' ? mapStatus(params.status) : undefined
  return get('/user/list', mappedParams).then(mapUserListFromBackend)
}

export function getUserById(id: number): Promise<User> {
  return get(`/user/${id}`).then(mapUserFromBackend)
}

export function createUser(data: UserForm): Promise<number> {
  return post('/user', mapUserToBackend(data))
}

export function updateUser(data: UserForm): Promise<void> {
  return put('/user', mapUserToBackend(data))
}

export function deleteUser(id: number): Promise<void> {
  return del(`/user/${id}`)
}

export function batchDeleteUsers(ids: number[]): Promise<void> {
  return del('/user/batch', { ids })
}

export function updateUserStatus(id: number, status: number | string): Promise<void> {
  return put(`/user/${id}/status`, {}, { params: { status: mapStatus(status) } })
}

export function resetUserPassword(id: number, newPassword?: string): Promise<void> {
  return put(`/user/${id}/reset-password`, {}, { params: { newPassword: newPassword || '123456' } })
}

export function assignUserRoles(data: { userId: number; roleIds: number[] }): Promise<void> {
  return post('/user/assign-roles', data)
}

export function getUserRoles(userId: number): Promise<string[]> {
  return get(`/user/${userId}/roles`)
}

export function getUserPermissions(userId: number): Promise<string[]> {
  return get(`/user/${userId}/permissions`)
}
