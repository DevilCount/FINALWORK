import { defineStore } from 'pinia'
import type { AppRouteRecordRaw } from '@/types/router'
import { constantRoutes } from '@/router/routes'

interface PermissionState {
  routes: AppRouteRecordRaw[]
  dynamicRoutes: AppRouteRecordRaw[]
  isRoutesLoaded: boolean
}

function hasPermission(route: AppRouteRecordRaw, roles: string[], permissions: string[]): boolean {
  if (route.meta?.hidden) return true
  if (roles && roles.includes('admin')) return true
  const permission = route.meta?.permission
  if (!permission) return true
  if (typeof permission === 'string') {
    return permissions && permissions.includes(permission)
  }
  if (Array.isArray(permission)) {
    return permissions && permission.some(p => permissions.includes(p))
  }
  return true
}

function doFilterRoutes(routes: AppRouteRecordRaw[], roles: string[], permissions: string[]): AppRouteRecordRaw[] {
  const result: AppRouteRecordRaw[] = []
  for (const route of routes) {
    const tmp = { ...route }
    if (hasPermission(tmp, roles, permissions)) {
      if (tmp.children && tmp.children.length > 0) {
        tmp.children = doFilterRoutes(tmp.children, roles, permissions)
      }
      result.push(tmp)
    }
  }
  return result
}

export const usePermissionStore = defineStore('permission', {
  state: (): PermissionState => ({
    routes: [],
    dynamicRoutes: [],
    isRoutesLoaded: false,
  }),

  getters: {
    menuRoutes: (state) => state.routes.filter((route) => !route.meta?.hidden),
  },

  actions: {
    async generateRoutesAction(roles: string[]): Promise<AppRouteRecordRaw[]> {
      let accessedRoutes: AppRouteRecordRaw[] = []
      
      if (roles && roles.includes('admin')) {
        accessedRoutes = await this.getAsyncRoutesAction()
      } else {
        accessedRoutes = await this.filterAsyncRoutesAction(roles || [])
      }
      
      this.routes = [...constantRoutes, ...accessedRoutes]
      this.dynamicRoutes = accessedRoutes
      this.isRoutesLoaded = true
      
      return accessedRoutes
    },

    async getAsyncRoutesAction(): Promise<AppRouteRecordRaw[]> {
      const Layout = () => import('@/components/Layout/index.vue')
      return [
        {
          path: '/dashboard',
          name: 'Dashboard',
          component: Layout,
          redirect: '/dashboard/index',
          meta: { title: '首页概览', icon: 'HomeFilled', affix: true },
          children: [
            {
              path: 'index',
              name: 'DashboardIndex',
              component: () => import('@/views/dashboard/index.vue'),
              meta: { title: '首页概览', icon: 'HomeFilled', affix: true },
            },
          ],
        },
        {
          path: '/system',
          name: 'System',
          component: Layout,
          redirect: '/system/user',
          meta: { title: '系统管理', icon: 'Setting' },
          children: [
            {
              path: 'user',
              name: 'UserManage',
              component: () => import('@/views/system/user/index.vue'),
              meta: { title: '用户管理', icon: 'User', permission: 'system:user:list' },
            },
            {
              path: 'role',
              name: 'RoleManage',
              component: () => import('@/views/system/role/index.vue'),
              meta: { title: '角色管理', icon: 'UserFilled', permission: 'system:role:list' },
            },
            {
              path: 'menu',
              name: 'MenuManage',
              component: () => import('@/views/system/menu/index.vue'),
              meta: { title: '菜单管理', icon: 'Menu', permission: 'system:menu:list' },
            },
            {
              path: 'dept',
              name: 'DeptManage',
              component: () => import('@/views/system/dept/index.vue'),
              meta: { title: '部门管理', icon: 'OfficeBuilding', permission: 'system:dept:list' },
            },
            {
              path: 'dict',
              name: 'DictManage',
              component: () => import('@/views/system/dict/index.vue'),
              meta: { title: '字典管理', icon: 'Collection', permission: 'system:dict:list' },
            },
          ],
        },
        {
          path: '/specimen',
          name: 'Specimen',
          component: Layout,
          redirect: '/specimen/register',
          meta: { title: '标本管理', icon: 'FirstAidKit' },
          children: [
            {
              path: 'register',
              name: 'SpecimenRegister',
              component: () => import('@/views/specimen/register/index.vue'),
              meta: { title: '标本登记', icon: 'DocumentAdd', permission: 'specimen:register:list' },
            },
            {
              path: 'receive',
              name: 'SpecimenReceive',
              component: () => import('@/views/specimen/receive/index.vue'),
              meta: { title: '标本签收', icon: 'Finished', permission: 'specimen:receive:list' },
            },
            {
              path: 'list',
              name: 'SpecimenList',
              component: () => import('@/views/specimen/list/index.vue'),
              meta: { title: '标本列表', icon: 'List', permission: 'specimen:query:list' },
            },
            {
              path: 'trace',
              name: 'SpecimenTrace',
              component: () => import('@/views/specimen/trace/index.vue'),
              meta: { title: '标本追溯', icon: 'View', permission: 'specimen:query:list' },
            },
          ],
        },
        {
          path: '/report',
          name: 'Report',
          component: Layout,
          redirect: '/report/apply',
          meta: { title: '检验管理', icon: 'Document' },
          children: [
            {
              path: 'apply',
              name: 'ReportApply',
              component: () => import('@/views/report/apply/index.vue'),
              meta: { title: '检验申请', icon: 'DocumentAdd', permission: 'report:entry:list' },
            },
            {
              path: 'result',
              name: 'ResultEntry',
              component: () => import('@/views/report/result/index.vue'),
              meta: { title: '结果录入', icon: 'Edit', permission: 'report:entry:list' },
            },
            {
              path: 'audit',
              name: 'ReportAudit',
              component: () => import('@/views/report/audit/index.vue'),
              meta: { title: '报告审核', icon: 'Checked', permission: 'report:audit:list' },
            },
            {
              path: 'query',
              name: 'ReportQuery',
              component: () => import('@/views/report/query/index.vue'),
              meta: { title: '报告查询', icon: 'Search', permission: 'report:query:list' },
            },
            {
              path: 'critical',
              name: 'CriticalValue',
              component: () => import('@/views/report/critical/index.vue'),
              meta: { title: '危急值管理', icon: 'Warning', permission: 'panic:list:list' },
            },
          ],
        },
        {
          path: '/equipment',
          name: 'Equipment',
          component: Layout,
          redirect: '/equipment/list',
          meta: { title: '设备管理', icon: 'Monitor' },
          children: [
            {
              path: 'list',
              name: 'EquipmentList',
              component: () => import('@/views/equipment/list/index.vue'),
              meta: { title: '设备列表', icon: 'List', permission: 'equipment:list:list' },
            },
            {
              path: 'monitor',
              name: 'EquipmentMonitor',
              component: () => import('@/views/equipment/monitor/index.vue'),
              meta: { title: '设备监控', icon: 'DataLine', permission: 'equipment:list:list' },
            },
            {
              path: 'maintenance',
              name: 'EquipmentMaintenance',
              component: () => import('@/views/equipment/maintenance/index.vue'),
              meta: { title: '维护保养', icon: 'Tools', permission: 'equipment:maintenance:list' },
            },
          ],
        },
        {
          path: '/statistics',
          name: 'Statistics',
          component: Layout,
          redirect: '/statistics/workload',
          meta: { title: '数据统计', icon: 'DataAnalysis' },
          children: [
            {
              path: 'workload',
              name: 'WorkloadStats',
              component: () => import('@/views/statistics/workload/index.vue'),
              meta: { title: '工作量统计', icon: 'TrendCharts', permission: 'statistics:workload:list' },
            },
            {
              path: 'specimen',
              name: 'SpecimenStats',
              component: () => import('@/views/statistics/specimen/index.vue'),
              meta: { title: '标本统计', icon: 'PieChart', permission: 'statistics:workload:list' },
            },
            {
              path: 'equipment',
              name: 'EquipmentStats',
              component: () => import('@/views/statistics/equipment/index.vue'),
              meta: { title: '设备统计', icon: 'DataBoard', permission: 'statistics:workload:list' },
            },
          ],
        },
        {
          path: '/ai',
          name: 'AIDiagnosis',
          component: Layout,
          redirect: '/ai/analysis',
          meta: { title: 'AI辅助', icon: 'MagicStick' },
          children: [
            {
              path: 'analysis',
              name: 'AIAnalysis',
              component: () => import('@/views/ai/analysis/index.vue'),
              meta: { title: '诊断分析', icon: 'Cpu', permission: 'ai:analysis' },
            },
            {
              path: 'records',
              name: 'AIRecords',
              component: () => import('@/views/ai/records/index.vue'),
              meta: { title: '诊断记录', icon: 'Tickets', permission: 'ai:records' },
            },
            {
              path: 'rules',
              name: 'AIRules',
              component: () => import('@/views/ai/rules/index.vue'),
              meta: { title: '诊断规则', icon: 'Operation', permission: 'ai:rules' },
            },
          ],
        },
        {
          path: '/his',
          name: 'HisIntegration',
          component: Layout,
          redirect: '/his/order',
          meta: { title: 'HIS集成', icon: 'Connection' },
          children: [
            {
              path: 'order',
              name: 'HisOrder',
              component: () => import('@/views/his/order/index.vue'),
              meta: { title: '检验申请', icon: 'Connection', permission: 'interface:config:list' },
            },
          ],
        },
        {
          path: '/monitor',
          name: 'Monitor',
          component: Layout,
          redirect: '/monitor/interfacelog',
          meta: { title: '日志管理', icon: 'Notebook' },
          children: [
            {
              path: 'interfacelog',
              name: 'InterfaceLog',
              component: () => import('@/views/monitor/interfacelog/index.vue'),
              meta: { title: '接口日志', icon: 'Connection', permission: 'interface:monitor:list' },
            },
            {
              path: 'errorlog',
              name: 'ErrorLog',
              component: () => import('@/views/monitor/errorlog/index.vue'),
              meta: { title: '系统错误', icon: 'Warning', permission: 'monitor:operlog:list' },
            },
            {
              path: 'operlog',
              name: 'OperLog',
              component: () => import('@/views/monitor/operlog/index.vue'),
              meta: { title: '操作日志', icon: 'Tickets', permission: 'monitor:operlog:list' },
            },
            {
              path: 'auditlog',
              name: 'AuditLog',
              component: () => import('@/views/monitor/auditlog/index.vue'),
              meta: { title: '审计日志', icon: 'Stamp', permission: 'monitor:operlog:list' },
            },
            {
              path: 'logininfor',
              name: 'LoginInfor',
              component: () => import('@/views/monitor/logininfor/index.vue'),
              meta: { title: '登录日志', icon: 'User', permission: 'monitor:logininfor:list' },
            },
          ],
        },
        {
          path: '/specimen/detail/:id',
          name: 'SpecimenDetail',
          component: Layout,
          meta: { title: '标本详情', hidden: true },
          children: [
            {
              path: '',
              name: 'SpecimenDetailIndex',
              component: () => import('@/views/specimen/detail/index.vue'),
              meta: { title: '标本详情' }
            }
          ]
        },
        {
          path: '/equipment/detail/:id',
          name: 'EquipmentDetail',
          component: Layout,
          meta: { title: '设备详情', hidden: true },
          children: [
            {
              path: '',
              name: 'EquipmentDetailIndex',
              component: () => import('@/views/equipment/detail/index.vue'),
              meta: { title: '设备详情' }
            }
          ]
        },
        {
          path: '/equipment/edit/:id',
          name: 'EquipmentEdit',
          component: Layout,
          meta: { title: '编辑设备', hidden: true },
          children: [
            {
              path: '',
              name: 'EquipmentEditIndex',
              component: () => import('@/views/equipment/list/index.vue'),
              meta: { title: '编辑设备' }
            }
          ]
        },
        {
          path: '/equipment/maintenance/add',
          name: 'EquipmentMaintenanceAdd',
          component: Layout,
          meta: { title: '添加维护记录', hidden: true },
          children: [
            {
              path: '',
              name: 'EquipmentMaintenanceAddIndex',
              component: () => import('@/views/equipment/list/index.vue'),
              meta: { title: '添加维护记录' }
            }
          ]
        },
        {
          path: '/profile',
          name: 'Profile',
          component: Layout,
          meta: { title: '个人中心', hidden: true },
          children: [
            {
              path: '',
              name: 'ProfileIndex',
              component: () => import('@/views/system/user/index.vue'),
              meta: { title: '个人中心' }
            }
          ]
        },
        {
          path: '/password',
          name: 'Password',
          component: Layout,
          meta: { title: '修改密码', hidden: true },
          children: [
            {
              path: '',
              name: 'PasswordIndex',
              component: () => import('@/views/system/user/index.vue'),
              meta: { title: '修改密码' }
            }
          ]
        },
      ]
    },

    async filterAsyncRoutesAction(roles: string[]): Promise<AppRouteRecordRaw[]> {
      const { useUserStore } = await import('@/stores/modules/user')
      const userStore = useUserStore()
      const permissions = userStore.permissions
      const routes = await this.getAsyncRoutesAction()
      return doFilterRoutes(routes, roles, permissions)
    },

    resetRoutesAction() {
      this.routes = []
      this.dynamicRoutes = []
      this.isRoutesLoaded = false
    },
  },
})
