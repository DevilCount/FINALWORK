import type { RouteRecordRaw } from 'vue-router'

export interface RouteMeta {
  title?: string
  icon?: string
  hidden?: boolean
  keepAlive?: boolean
  affix?: boolean
  breadcrumb?: boolean
  permission?: string | string[]
  requiresAuth?: boolean
  [key: string]: unknown
}

export interface AppRouteRecordRaw extends Omit<RouteRecordRaw, 'children' | 'meta'> {
  meta?: RouteMeta
  children?: AppRouteRecordRaw[]
}

export interface TagView {
  title?: string
  fullPath: string
  path: string
  name?: string
  meta?: RouteMeta
  query?: Record<string, string>
}
