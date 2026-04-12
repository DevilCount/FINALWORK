import { get, post, put, del } from '@/utils/request'

export interface Menu {
  id: number
  parentId: number
  menuName: string
  path: string
  component: string
  redirect: string
  icon: string
  sort: number
  status: number
  visible: number
  type: number
  perms: string
  createTime: string
  updateTime: string
  children?: Menu[]
}

export interface MenuForm {
  id?: number
  parentId: number
  menuName: string
  path: string
  component: string
  redirect: string
  icon: string
  sort: number
  status: number
  visible: number
  type: number
  perms: string
}

export interface MenuQuery {
  menuName?: string
  status?: number
  type?: number
}

export interface MenuTree {
  id: number
  label: string
  parentId: number
  children?: MenuTree[]
}

export function getMenuList(params?: MenuQuery): Promise<Menu[]> {
  return get('/user/menu/list', params)
}

export function getMenuTree(): Promise<Menu[]> {
  return get('/user/menu/tree')
}

export function getMenuTreeNodes(): Promise<MenuTree[]> {
  return get('/user/menu/tree-nodes')
}

export function getMenuById(id: number): Promise<Menu> {
  return get(`/user/menu/${id}`)
}

export function createMenu(data: MenuForm): Promise<number> {
  const mapped = { ...data, menuType: data.type, orderNum: data.sort }
  return post('/user/menu', mapped)
}

export function updateMenu(data: MenuForm): Promise<void> {
  const mapped = { ...data, menuType: data.type, orderNum: data.sort }
  return put('/user/menu', mapped)
}

export function deleteMenu(id: number): Promise<void> {
  return del(`/user/menu/${id}`)
}

export function getUserMenus(userId: number): Promise<Menu[]> {
  return get(`/user/menu/user/${userId}`)
}

export function getRoleMenus(roleId: number): Promise<Menu[]> {
  return get(`/user/menu/role/${roleId}`)
}

export function getUserPermissions(userId: number): Promise<string[]> {
  return get(`/user/menu/user/${userId}/permissions`)
}
