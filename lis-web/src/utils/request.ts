import axios, { type AxiosInstance, type AxiosRequestConfig, type InternalAxiosRequestConfig } from 'axios'
import { ElMessage } from 'element-plus'
import { authService, specimenService, reportService, systemService } from './mockService'

// 存储请求缓存
const requestCache = new Map<string, { data: any; timestamp: number }>()

// 生成请求唯一标识
function generateRequestKey(config: AxiosRequestConfig): string {
  const { method, url, params, data } = config
  return `${method || 'GET'}-${url}-${JSON.stringify(params || {})}-${JSON.stringify(data || {})}`
}

// 清除过期缓存
function clearExpiredCache() {
  const now = Date.now()
  requestCache.forEach((value, key) => {
    if (value.timestamp < now - 5 * 60 * 1000) { // 5分钟过期
      requestCache.delete(key)
    }
  })
}

// 清除所有缓存
export function clearCache() {
  requestCache.clear()
}

// 定义错误类型
export enum ErrorType {
  NETWORK_ERROR = 'NETWORK_ERROR',
  TIMEOUT_ERROR = 'TIMEOUT_ERROR',
  SERVER_ERROR = 'SERVER_ERROR',
  CLIENT_ERROR = 'CLIENT_ERROR',
  AUTH_ERROR = 'AUTH_ERROR',
  FORBIDDEN_ERROR = 'FORBIDDEN_ERROR',
  NOT_FOUND_ERROR = 'NOT_FOUND_ERROR',
  UNKNOWN_ERROR = 'UNKNOWN_ERROR'
}

// 错误日志记录
function logError(_error: any, _config?: AxiosRequestConfig) {
  // 生产环境下可以考虑集成错误监控服务，如 Sentry 等
}

const service: AxiosInstance = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || '/api',
  timeout: import.meta.env.VITE_API_TIMEOUT || 30000,
  headers: {
    'Content-Type': 'application/json',
  },
})

// 模拟请求处理函数
function handleMockRequest(config: InternalAxiosRequestConfig): Promise<any> {
  // 确保config对象存在
  if (!config) {
    return Promise.reject(new Error('Request config is undefined'));
  }
  
  const url = config.url || ''
  const method = config.method?.toLowerCase() || 'get'
  const data = config.data || config.params || {}

  // 认证相关
  if (url.includes('/auth/login')) {
    return authService.login(data.username, data.password)
  }
  if (url.includes('/auth/logout')) {
    return authService.logout()
  }
  if (url.includes('/auth/refresh')) {
    return authService.refreshToken(data.refreshToken)
  }
  if (url.includes('/auth/captcha')) {
    return authService.getCaptcha()
  }
  if (url.includes('/auth/info')) {
    return authService.getUserInfo()
  }
  if (url.includes('/auth/password')) {
    return authService.updatePassword(data.oldPassword, data.newPassword)
  }

  // 标本相关
  if (url.includes('/specimen/list')) {
    return specimenService.getSpecimenList(data)
  }
  if (url.includes('/specimen/getById/')) {
    const id = url.split('/').pop() || ''
    return specimenService.getSpecimenDetail(id)
  }
  if (url.includes('/specimen/register')) {
    return specimenService.registerSpecimen(data)
  }
  if (url.includes('/specimen/getByBarcode')) {
    return specimenService.getSpecimenByBarcode(data.barcode)
  }
  if (url.includes('/specimen/getBySpecimenNo')) {
    return specimenService.getSpecimenBySpecimenNo(data.specimenNo)
  }
  if (url.includes('/specimen/receive')) {
    return specimenService.receiveSpecimen(data.barcode)
  }
  if (url.includes('/specimen/reject')) {
    return specimenService.rejectSpecimen(data.barcode, data.rejectReason)
  }
  if (url.includes('/specimen/updateStatus')) {
    return specimenService.updateSpecimenStatus(data)
  }
  if (url.includes('/specimen/storage')) {
    return specimenService.storageSpecimen(data)
  }
  if (url.includes('/specimen/trace/')) {
    const id = url.split('/').pop() || ''
    return specimenService.getSpecimenTraceBySpecimenId(id)
  }
  if (url.includes('/specimen/traceByNo')) {
    return specimenService.getSpecimenTraceBySpecimenId(data.specimenNo)
  }
  if (url.includes('/specimen/types')) {
    return specimenService.getSpecimenTypes()
  }
  if (url.includes('/specimen/testItems')) {
    return specimenService.getTestItems()
  }
  if (url.includes('/specimen/testItemCategories')) {
    return specimenService.getTestItemCategories()
  }
  if (url.includes('/specimen/statistics')) {
    return specimenService.getSpecimenStatistics(data)
  }

  // 报告相关
  if (url.includes('/report/apply/query')) {
    return reportService.getReportList(data)
  }
  if (url.includes('/report/apply/') && url.includes('/detail')) {
    const id = url.split('/').filter(Boolean).pop() || ''
    return reportService.getReportDetail(id)
  }
  if (url.includes('/report/result')) {
    if (method === 'post') {
      return reportService.saveResultEntry(data)
    }
  }
  if (url.includes('/report/result/batch')) {
    return reportService.batchSaveResultEntry(data)
  }
  if (url.includes('/report/audit/submit/')) {
    const id = url.split('/').pop() || ''
    return reportService.submitReport(id)
  }
  if (url.includes('/report/audit/first-approve')) {
    return reportService.firstApproveReport(data)
  }
  if (url.includes('/report/audit/first-reject')) {
    return reportService.firstRejectReport(data)
  }
  if (url.includes('/report/audit/final-approve')) {
    return reportService.finalApproveReport(data)
  }
  if (url.includes('/report/audit/final-reject')) {
    return reportService.finalRejectReport(data)
  }
  if (url.includes('/report/audit/approve')) {
    return reportService.auditReport(data)
  }
  if (url.includes('/report/audit/reject')) {
    return reportService.auditReport({ ...data, auditResult: 'reject' })
  }
  if (url.includes('/report/publish')) {
    return reportService.publishReport(data)
  }
  if (url.includes('/report/apply/') && url.includes('/cancel')) {
    const id = url.split('/').filter(Boolean).pop() || ''
    return reportService.cancelReport(id, data?.reason)
  }

  // 系统相关
  if (url.includes('/user/dept/tree')) {
    return systemService.getDepartments()
  }

  // 默认返回空数据
  return Promise.resolve({})
}

// 修改axios实例，直接返回模拟数据
(service as any).request = function(config: any) {
  // 确保config对象存在
  if (!config) {
    return Promise.reject(new Error('Request config is undefined'));
  }
  
  return handleMockRequest(config).then((mockData) => {
    return Promise.resolve({
      code: 200,
      message: 'success',
      data: mockData
    });
  }).catch((error) => {
    return Promise.reject(error);
  });
};

// 保持请求拦截器的结构，但不做实际操作
service.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    return config;
  },
  (error) => {
    logError(error)
    return Promise.reject(error)
  }
)

// 简化响应拦截器，因为我们直接返回的是处理后的数据
service.interceptors.response.use(
  (response: any) => {
    // 如果已经是直接返回的数据，直接返回
    if (response.code !== undefined) {
      if (response.code === 200) {
        return response.data;
      } else {
        const errorMessage = response.message || '请求失败';
        ElMessage.error(errorMessage);
        return Promise.reject(new Error(errorMessage));
      }
    }
    
    // 处理传统的Axios响应（以防万一）
    try {
      const { data } = response;
      const { code, message, data: result } = data;

      if (code === 200) {
        return result;
      }

      const errorMessage = message || '请求失败';
      ElMessage.error(errorMessage);
      return Promise.reject(new Error(errorMessage));
    } catch (error) {
      return response;
    }
  },
  (error) => {
    logError(error);
    ElMessage.error('请求失败');
    return Promise.reject(error);
  }
)

export interface RequestOptions extends AxiosRequestConfig {
  showLoading?: boolean // 是否显示加载状态
  showError?: boolean // 是否显示错误信息
  retry?: number // 重试次数
  retryDelay?: number // 重试延迟时间（毫秒）
  retryableStatusCodes?: number[] // 可重试的状态码
  retryableErrorTypes?: string[] // 可重试的错误类型
  cache?: boolean // 是否启用缓存
  cacheExpire?: number // 缓存过期时间（毫秒）
  cacheKey?: string // 自定义缓存键
  timeout?: number // 超时时间（毫秒）
  withCredentials?: boolean // 是否携带凭证
  responseType?: 'arraybuffer' | 'blob' | 'document' | 'json' | 'text' | 'stream' // 响应类型
  baseURL?: string // 基础URL
  headers?: Record<string, string> // 自定义请求头
  paramsSerializer?: (params: any) => string // 参数序列化器
  validateStatus?: (status: number) => boolean // 状态码验证器
}

export function request<T = unknown>(config: RequestOptions): Promise<T> {
  const { cache = false, cacheExpire = 5 * 60 * 1000, cacheKey } = config
  
  // 清除过期缓存
  clearExpiredCache()
  
  // 生成缓存键
  const key = cacheKey || generateRequestKey(config)
  
  // 检查缓存
  if (cache) {
    const cachedData = requestCache.get(key)
    if (cachedData) {
      const now = Date.now()
      if (now - cachedData.timestamp < cacheExpire) {
        return Promise.resolve(cachedData.data as T)
      }
    }
  }
  
  return service.request(config) as Promise<T>
}

export function get<T = unknown>(url: string, params?: object, config?: RequestOptions): Promise<T> {
  return request<T>({ ...config, method: 'GET', url, params })
}

export function post<T = unknown>(url: string, data?: object, config?: RequestOptions): Promise<T> {
  return request<T>({ ...config, method: 'POST', url, data })
}

export function put<T = unknown>(url: string, data?: object, config?: RequestOptions): Promise<T> {
  return request<T>({ ...config, method: 'PUT', url, data })
}

export function del<T = unknown>(url: string, data?: object, config?: RequestOptions): Promise<T> {
  return request<T>({ ...config, method: 'DELETE', url, data })
}

export default service
