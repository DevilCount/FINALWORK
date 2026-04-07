import { get, post, put, del } from '@/utils/request'

export interface Dept {
  id: number
  parentId: number
  name: string
  code: string
  leader: string
  phone: string
  email: string
  status: 'normal' | 'disable'
  sort: number
  createTime: string
  updateTime: string
  children?: Dept[]
}

export interface DeptForm {
  id?: number
  parentId: number
  name: string
  code: string
  leader: string
  phone: string
  email: string
  status: 'normal' | 'disable'
  sort: number
}

export interface DeptQuery {
  name?: string
  status?: string
}

export interface DeptTree {
  id: number
  label: string
  parentId: number
  children?: DeptTree[]
}

export function getDeptList(params?: DeptQuery): Promise<Dept[]> {
  return get('/system/dept/list', params)
}

export function getDeptTree(): Promise<DeptTree[]> {
  return get('/system/dept/tree')
}

export function getDeptById(id: number): Promise<Dept> {
  return get(`/system/dept/${id}`)
}

export function createDept(data: DeptForm): Promise<void> {
  return post('/system/dept', data)
}

export function updateDept(data: DeptForm): Promise<void> {
  return put('/system/dept', data)
}

export function deleteDept(id: number): Promise<void> {
  return del(`/system/dept/${id}`)
}

export function updateDeptStatus(id: number, status: 'normal' | 'disable'): Promise<void> {
  return put(`/system/dept/${id}/status`, { status })
}

export function getDeptUsers(id: number): Promise<{ id: number; realName: string }[]> {
  return get(`/system/dept/${id}/users`)
}
