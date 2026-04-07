import { defineStore } from 'pinia'
import type { AppRouteRecordRaw } from '@/types/router'
import { constantRoutes } from '@/router/routes'

interface PermissionState {
  routes: AppRouteRecordRaw[]
  dynamicRoutes: AppRouteRecordRaw[]
  isRoutesLoaded: boolean
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
      
      if (roles.includes('admin')) {
        accessedRoutes = await this.getAsyncRoutesAction()
      } else {
        accessedRoutes = await this.filterAsyncRoutesAction(roles)
      }
      
      this.routes = [...constantRoutes, ...accessedRoutes]
      this.dynamicRoutes = accessedRoutes
      this.isRoutesLoaded = true
      
      return accessedRoutes
    },

    async getAsyncRoutesAction(): Promise<AppRouteRecordRaw[]> {
      return [
        {
          path: '/dashboard',
          name: 'Dashboard',
          component: () => import('@/views/dashboard/index.vue'),
          meta: { title: '首页概览', icon: 'HomeFilled', affix: true },
        },
        {
          path: '/system',
          name: 'System',
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
          meta: { title: '标本管理', icon: 'FirstAidKit' },
          children: [
            {
              path: 'register',
              name: 'SpecimenRegister',
              component: () => import('@/views/specimen/register/index.vue'),
              meta: { title: '标本登记', icon: 'DocumentAdd', permission: 'specimen:register' },
            },
            {
              path: 'receive',
              name: 'SpecimenReceive',
              component: () => import('@/views/specimen/receive/index.vue'),
              meta: { title: '标本签收', icon: 'Finished', permission: 'specimen:receive' },
            },
            {
              path: 'list',
              name: 'SpecimenList',
              component: () => import('@/views/specimen/list/index.vue'),
              meta: { title: '标本列表', icon: 'List', permission: 'specimen:list' },
            },
            {
              path: 'trace',
              name: 'SpecimenTrace',
              component: () => import('@/views/specimen/trace/index.vue'),
              meta: { title: '标本追溯', icon: 'View', permission: 'specimen:trace' },
            },
          ],
        },
        {
          path: '/report',
          name: 'Report',
          meta: { title: '检验管理', icon: 'Document' },
          children: [
            {
              path: 'apply',
              name: 'ReportApply',
              component: () => import('@/views/report/apply/index.vue'),
              meta: { title: '检验申请', icon: 'DocumentAdd', permission: 'report:apply' },
            },
            {
              path: 'result',
              name: 'ResultEntry',
              component: () => import('@/views/report/result/index.vue'),
              meta: { title: '结果录入', icon: 'Edit', permission: 'report:result' },
            },
            {
              path: 'audit',
              name: 'ReportAudit',
              component: () => import('@/views/report/audit/index.vue'),
              meta: { title: '报告审核', icon: 'Checked', permission: 'report:audit' },
            },
            {
              path: 'query',
              name: 'ReportQuery',
              component: () => import('@/views/report/query/index.vue'),
              meta: { title: '报告查询', icon: 'Search', permission: 'report:query' },
            },
            {
              path: 'critical',
              name: 'CriticalValue',
              component: () => import('@/views/report/critical/index.vue'),
              meta: { title: '危急值管理', icon: 'Warning', permission: 'report:critical' },
            },
          ],
        },
        {
          path: '/equipment',
          name: 'Equipment',
          meta: { title: '设备管理', icon: 'Monitor' },
          children: [
            {
              path: 'list',
              name: 'EquipmentList',
              component: () => import('@/views/equipment/list/index.vue'),
              meta: { title: '设备列表', icon: 'List', permission: 'equipment:list' },
            },
            {
              path: 'monitor',
              name: 'EquipmentMonitor',
              component: () => import('@/views/equipment/monitor/index.vue'),
              meta: { title: '设备监控', icon: 'DataLine', permission: 'equipment:monitor' },
            },
            {
              path: 'maintenance',
              name: 'EquipmentMaintenance',
              component: () => import('@/views/equipment/maintenance/index.vue'),
              meta: { title: '维护保养', icon: 'Tools', permission: 'equipment:maintenance' },
            },
          ],
        },
        {
          path: '/statistics',
          name: 'Statistics',
          meta: { title: '数据统计', icon: 'DataAnalysis' },
          children: [
            {
              path: 'workload',
              name: 'WorkloadStats',
              component: () => import('@/views/statistics/workload/index.vue'),
              meta: { title: '工作量统计', icon: 'TrendCharts', permission: 'statistics:workload' },
            },
            {
              path: 'specimen',
              name: 'SpecimenStats',
              component: () => import('@/views/statistics/specimen/index.vue'),
              meta: { title: '标本统计', icon: 'PieChart', permission: 'statistics:specimen' },
            },
            {
              path: 'equipment',
              name: 'EquipmentStats',
              component: () => import('@/views/statistics/equipment/index.vue'),
              meta: { title: '设备统计', icon: 'DataBoard', permission: 'statistics:equipment' },
            },
          ],
        },
        {
          path: '/ai',
          name: 'AIDiagnosis',
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
      ]
    },

    async filterAsyncRoutesAction(_roles: string[]): Promise<AppRouteRecordRaw[]> {
      const routes = await this.getAsyncRoutesAction()
      const result: AppRouteRecordRaw[] = []
      
      for (const route of routes) {
        const tmp = { ...route }
        if (tmp.children && tmp.children.length > 0) {
          tmp.children = tmp.children.filter(() => true)
        }
        result.push(tmp)
      }
      
      return result
    },

    resetRoutesAction() {
      this.routes = []
      this.dynamicRoutes = []
      this.isRoutesLoaded = false
    },
  },

  persist: true,
})
