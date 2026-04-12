import { get, post, put, del } from '@/utils/request'

export interface Dept {
  id: number
  parentId: number
  deptName: string
  deptCode: string
  leader: string
  phone: string
  email: string
  status: number
  sort: number
  createTime: string
  updateTime: string
  children?: Dept[]
}

export interface DeptForm {
  id?: number
  parentId: number
  deptName: string
  deptCode: string
  leader: string
  phone: string
  email: string
  status: number
  sort: number
}

export interface DeptQuery {
  deptName?: string
  status?: number
}

export interface DeptTree {
  id: number
  label: string
  parentId: number
  children?: DeptTree[]
}

export function getDeptList(params?: DeptQuery): Promise<Dept[]> {
  return get('/user/dept/list', params)
}

export function getDeptTree(): Promise<Dept[]> {
  return get('/user/dept/tree')
}

export function getDeptTreeNodes(): Promise<DeptTree[]> {
  return get('/user/dept/tree-nodes')
}

export function getDeptById(id: number): Promise<Dept> {
  return get(`/user/dept/${id}`)
}

export function createDept(data: DeptForm): Promise<number> {
  return post('/user/dept', data)
}

export function updateDept(data: DeptForm): Promise<void> {
  return put('/user/dept', data)
}

export function deleteDept(id: number): Promise<void> {
  return del(`/user/dept/${id}`)
}

export function getRoleDeptIds(roleId: number): Promise<number[]> {
  return get(`/user/dept/role/${roleId}`)
}
