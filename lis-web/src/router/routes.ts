import type { AppRouteRecordRaw } from '@/types/router'
import Layout from '@/components/Layout/index.vue'

export const constantRoutes: AppRouteRecordRaw[] = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/login/index.vue'),
    meta: { title: '登录', hidden: true },
  },
  {
    path: '/404',
    name: 'NotFound',
    component: () => import('@/views/error/404.vue'),
    meta: { title: '404', hidden: true },
  },
  {
    path: '/403',
    name: 'Forbidden',
    component: () => import('@/views/error/403.vue'),
    meta: { title: '403', hidden: true },
  },
  {
    path: '/',
    name: 'Root',
    component: Layout,
    redirect: '/dashboard/index',
    children: [],
  },
]

export const asyncRoutes: AppRouteRecordRaw[] = []

export default constantRoutes
