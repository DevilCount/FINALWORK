import { get, post, put, del } from '@/utils/request'
import type { PageParams, PageResult } from '@/types/api'

export interface Role {
  id: number
  name: string
  code: string
  status: 'normal' | 'disable'
  sort: number
  remark: string
  menuIds: number[]
  createTime: string
  updateTime: string
}

export interface RoleForm {
  id?: number
  name: string
  code: string
  status: 'normal' | 'disable'
  sort: number
  remark: string
  menuIds: number[]
}

export interface RoleQuery extends PageParams {
  name?: string
  code?: string
  status?: string
}

export function getRoleList(params: RoleQuery): Promise<PageResult<Role>> {
  return get('/system/role/list', params)
}

export function getAllRoles(): Promise<Role[]> {
  return get('/system/role/all')
}

export function getRoleById(id: number): Promise<Role> {
  return get(`/system/role/${id}`)
}

export function createRole(data: RoleForm): Promise<void> {
  return post('/system/role', data)
}

export function updateRole(data: RoleForm): Promise<void> {
  return put('/system/role', data)
}

export function deleteRole(id: number): Promise<void> {
  return del(`/system/role/${id}`)
}

export function batchDeleteRoles(ids: number[]): Promise<void> {
  return del('/system/role/batch', { ids })
}

export function updateRoleStatus(id: number, status: 'normal' | 'disable'): Promise<void> {
  return put(`/system/role/${id}/status`, { status })
}

export function getRoleMenus(id: number): Promise<number[]> {
  return get(`/system/role/${id}/menus`)
}

export function assignRoleMenus(id: number, menuIds: number[]): Promise<void> {
  return put(`/system/role/${id}/menus`, { menuIds })
}
