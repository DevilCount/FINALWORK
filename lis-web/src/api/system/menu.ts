import { get, post, put, del } from '@/utils/request'

export interface Menu {
  id: number
  parentId: number
  name: string
  path: string
  component: string
  redirect: string
  icon: string
  sort: number
  status: 'normal' | 'disable'
  visible: 'show' | 'hidden'
  type: 'directory' | 'menu' | 'button'
  perms: string
  createTime: string
  updateTime: string
  children?: Menu[]
}

export interface MenuForm {
  id?: number
  parentId: number
  name: string
  path: string
  component: string
  redirect: string
  icon: string
  sort: number
  status: 'normal' | 'disable'
  visible: 'show' | 'hidden'
  type: 'directory' | 'menu' | 'button'
  perms: string
}

export interface MenuQuery {
  name?: string
  status?: string
  type?: string
}

export interface MenuTree {
  id: number
  label: string
  parentId: number
  children?: MenuTree[]
}

export function getMenuList(params?: MenuQuery): Promise<Menu[]> {
  return get('/system/menu/list', params)
}

export function getMenuTree(): Promise<MenuTree[]> {
  return get('/system/menu/tree')
}

export function getMenuById(id: number): Promise<Menu> {
  return get(`/system/menu/${id}`)
}

export function createMenu(data: MenuForm): Promise<void> {
  return post('/system/menu', data)
}

export function updateMenu(data: MenuForm): Promise<void> {
  return put('/system/menu', data)
}

export function deleteMenu(id: number): Promise<void> {
  return del(`/system/menu/${id}`)
}

export function updateMenuStatus(id: number, status: 'normal' | 'disable'): Promise<void> {
  return put(`/system/menu/${id}/status`, { status })
}

export function getUserMenus(): Promise<Menu[]> {
  return get('/system/menu/user')
}

export function getMenuButtons(menuId: number): Promise<Menu[]> {
  return get(`/system/menu/${menuId}/buttons`)
}
