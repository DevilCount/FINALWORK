import { createRouter, createWebHistory, type RouteRecordRaw } from 'vue-router'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import { useUserStore } from '@/stores/modules/user'
import { usePermissionStore } from '@/stores/modules/permission'
import { getToken } from '@/utils/auth'
import { constantRoutes } from './routes'

NProgress.configure({ showSpinner: false })

const router = createRouter({
  history: createWebHistory(),
  routes: constantRoutes as unknown as RouteRecordRaw[],
  scrollBehavior: () => ({ top: 0 }),
})

const whiteList = ['/login', '/404', '/403']

/**
 * 递归添加路由
 */
function addRouteRecursive(route: RouteRecordRaw) {
  const tmpRoute = { ...route } as RouteRecordRaw
  if (tmpRoute.children && tmpRoute.children.length > 0) {
    const children = [...tmpRoute.children]
    tmpRoute.children = []
    router.addRoute(tmpRoute)
    children.forEach(child => {
      const childRoute = { ...child } as RouteRecordRaw
      if (childRoute.children && childRoute.children.length > 0) {
        addRouteRecursive(childRoute)
      } else {
        router.addRoute(tmpRoute.name as string, childRoute)
      }
    })
  } else {
    router.addRoute(tmpRoute)
  }
}

/**
 * 添加路由到路由表
 */
function addDynamicRoutes(routes: any[]) {
  routes.forEach((route) => {
    addRouteRecursive(route as RouteRecordRaw)
  })
  // 动态路由添加完毕后，添加 CatchAll 路由（确保在最后）
  router.addRoute({
    path: '/:pathMatch(.*)*',
    name: 'CatchAll',
    redirect: '/404',
    meta: { hidden: true },
  } as RouteRecordRaw)
}

router.beforeEach(async (to, _from, next) => {
  NProgress.start()

  document.title = `${to.meta.title || ''} - LIS实验室信息管理系统`

  const token = getToken()
  const userStore = useUserStore()
  const permissionStore = usePermissionStore()

  if (token) {
    if (to.path === '/login') {
      next({ path: '/dashboard/index' })
    } else {
      if (!permissionStore.isRoutesLoaded) {
        try {
          // 1. 获取用户信息
          await userStore.getUserInfoAction()
          // 2. 根据角色和权限生成有权限的路由
          const accessRoutes = await permissionStore.generateRoutesAction(userStore.roles, userStore.permissions)
          // 3. 添加动态路由
          addDynamicRoutes(accessRoutes)
          // 4. 使用 replace 模式切换路由，避免历史堆积
          next({ ...to, replace: true })
        } catch (error) {
          userStore.resetStateAction()
          next(`/login?redirect=${to.path}`)
        }
      } else {
        // 路由已加载，直接放行
        next()
      }
    }
  } else {
    // 未登录
    if (whiteList.includes(to.path)) {
      next()
    } else {
      next(`/login?redirect=${to.path}`)
    }
  }
})

router.afterEach(() => {
  NProgress.done()
})

export default router
