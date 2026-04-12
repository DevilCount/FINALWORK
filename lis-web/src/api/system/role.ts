import { get, post, put, del } from '@/utils/request'
import type { PageParams, PageResult } from '@/types/api'

export interface Role {
  id: number
  name: string
  code: string
  status: number
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
  status: number
  sort: number
  remark: string
  menuIds: number[]
}

export interface RoleQuery extends PageParams {
  name?: string
  code?: string
  status?: number | string
}

function mapStatus(status: number | string): number {
  if (typeof status === 'number') return status
  if (status === 'normal') return 0
  if (status === 'disable') return 2
  return parseInt(status) || 0
}

function mapRoleToBackend(data: RoleForm): any {
  return {
    ...data,
    roleName: data.name,
    roleCode: data.code,
    roleSort: data.sort,
    roleKey: data.code,
  }
}

function mapRoleQueryToBackend(params: RoleQuery): any {
  return {
    ...params,
    roleName: params.name,
    roleCode: params.code,
  }
}

function mapRoleFromBackend(data: any): Role {
  return {
    ...data,
    name: data.roleName || data.name,
    code: data.roleCode || data.code,
    sort: data.roleSort ?? data.sort,
  }
}

function mapRoleListFromBackend(result: unknown): PageResult<Role> {
  const r = result as PageResult<any>
  return {
    ...r,
    records: r.records?.map(mapRoleFromBackend) || [],
  }
}

export function getRoleList(params: RoleQuery): Promise<PageResult<Role>> {
  const mappedParams = mapRoleQueryToBackend(params)
  mappedParams.status = params.status !== undefined && params.status !== '' ? mapStatus(params.status) : undefined
  return get('/user/role/list', mappedParams).then(mapRoleListFromBackend)
}

export function getAllRoles(): Promise<Role[]> {
  return get('/user/role/all').then((data: any) => data?.map?.(mapRoleFromBackend) || data)
}

export function getRoleById(id: number): Promise<Role> {
  return get(`/user/role/${id}`).then(mapRoleFromBackend)
}

export function createRole(data: RoleForm): Promise<number> {
  return post('/user/role', mapRoleToBackend(data))
}

export function updateRole(data: RoleForm): Promise<void> {
  return put('/user/role', mapRoleToBackend(data))
}

export function deleteRole(id: number): Promise<void> {
  return del(`/user/role/${id}`)
}

export function batchDeleteRoles(ids: number[]): Promise<void> {
  return del('/user/role/batch', { ids })
}

export function getRoleMenuIds(roleId: number): Promise<number[]> {
  return get(`/user/role/${roleId}/menus`)
}

export function getRoleMenus(roleId: number): Promise<number[]> {
  return getRoleMenuIds(roleId)
}

export function assignRoleMenus(roleId: number, menuIds: number[]): Promise<void> {
  return post('/user/role/assign-menus', { roleId, menuIds })
}

export function updateRoleStatus(id: number, status: number | string): Promise<void> {
  return put(`/user/role/${id}/status`, {}, { params: { status: mapStatus(status) } })
}
