import { createRouter, createWebHistory } from 'vue-router'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import { constantRoutes } from './routes'
import { useUserStore } from '@/stores/modules/user'
import { usePermissionStore } from '@/stores/modules/permission'
import { getToken } from '@/utils/auth'

NProgress.configure({ showSpinner: false })

const router = createRouter({
  history: createWebHistory(),
  routes: constantRoutes as any,
  scrollBehavior: () => ({ left: 0, top: 0 }),
})

const whiteList = ['/login', '/404', '/403']

router.beforeEach(async (to, _from, next) => {
  NProgress.start()
  
  const title = to.meta?.title as string | undefined
  document.title = title 
    ? `${title} - LIS实验室信息管理系统`
    : 'LIS实验室信息管理系统'

  const token = getToken()

  if (token) {
    if (to.path === '/login') {
      next({ path: '/' })
      NProgress.done()
    } else {
      const userStore = useUserStore()
      const permissionStore = usePermissionStore()
      
      if (userStore.roles.length === 0) {
        try {
          await userStore.getUserInfoAction()
          
          if (!permissionStore.isRoutesLoaded) {
            const accessRoutes = await permissionStore.generateRoutesAction(userStore.roles)
            accessRoutes.forEach((route) => {
              router.addRoute(route as any)
            })
            router.addRoute({
              path: '/:pathMatch(.*)*',
              redirect: '/404',
              meta: { hidden: true },
            } as any)
            next({ ...to, replace: true })
          } else {
            next()
          }
        } catch (error) {
          userStore.resetStateAction()
          next(`/login?redirect=${to.path}`)
          NProgress.done()
        }
      } else {
        next()
      }
    }
  } else {
    if (whiteList.includes(to.path)) {
      next()
    } else {
      next(`/login?redirect=${to.path}`)
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  NProgress.done()
})

export default router
