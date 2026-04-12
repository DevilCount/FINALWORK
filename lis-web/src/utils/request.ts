import axios, { type AxiosInstance, type AxiosRequestConfig, type AxiosResponse, type InternalAxiosRequestConfig, type AxiosError } from 'axios'
import { ElMessage } from 'element-plus'
import { useUserStore } from '@/stores/modules/user'
import router from '@/router'
import type { Result } from '@/types'
import { authService, specimenService, reportService, systemService } from './mockService'

// 存储请求取消控制器
const cancelControllers = new Map<string, AbortController>()

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

// 取消重复请求
function cancelDuplicateRequest(config: AxiosRequestConfig): void {
  const key = generateRequestKey(config)
  const existingController = cancelControllers.get(key)
  if (existingController) {
    existingController.abort()
    cancelControllers.delete(key)
  }
}

// 添加请求取消控制器
function addCancelController(config: AxiosRequestConfig): void {
  const key = generateRequestKey(config)
  const controller = new AbortController()
  config.signal = controller.signal
  cancelControllers.set(key, controller)
}

// 移除请求取消控制器
function removeCancelController(config: AxiosRequestConfig): void {
  const key = generateRequestKey(config)
  cancelControllers.delete(key)
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

// 错误信息映射
const errorMessages: Record<ErrorType, string> = {
  [ErrorType.NETWORK_ERROR]: '网络连接失败，请检查网络',
  [ErrorType.TIMEOUT_ERROR]: '请求超时，请稍后重试',
  [ErrorType.SERVER_ERROR]: '服务器内部错误',
  [ErrorType.CLIENT_ERROR]: '请求参数错误',
  [ErrorType.AUTH_ERROR]: '登录已过期，请重新登录',
  [ErrorType.FORBIDDEN_ERROR]: '没有权限访问该资源',
  [ErrorType.NOT_FOUND_ERROR]: '请求的资源不存在',
  [ErrorType.UNKNOWN_ERROR]: '请求失败'
}

// 错误日志记录
function logError(error: any, config?: AxiosRequestConfig) {
  console.error('API Request Error:', {
    url: config?.url,
    method: config?.method,
    params: config?.params,
    data: config?.data,
    error: {
      message: error.message,
      code: error.code,
      status: error.response?.status,
      data: error.response?.data
    }
  })
}

const service: AxiosInstance = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || '/api',
  timeout: import.meta.env.VITE_API_TIMEOUT || 30000,
  headers: {
    'Content-Type': 'application/json',
  },
})

let isRefreshing = false
let refreshSubscribers: ((token: string) => void)[] = []
let refreshTokenPromise: Promise<string> | null = null

function subscribeTokenRefresh(callback: (token: string) => void) {
  refreshSubscribers.push(callback)
}

function onRefreshed(token: string) {
  refreshSubscribers.forEach((callback) => callback(token))
  refreshSubscribers = []
}

// 检查token是否即将过期
function isTokenExpiring(token: string): boolean {
  try {
    const payload = JSON.parse(atob(token.split('.')[1]))
    const exp = payload.exp * 1000
    const now = Date.now()
    // 提前5分钟刷新token
    return exp - now < 5 * 60 * 1000
  } catch {
    return true
  }
}

// 主动刷新token
async function refreshToken(): Promise<string> {
  if (refreshTokenPromise) {
    return refreshTokenPromise
  }

  const userStore = useUserStore()
  const refreshTokenValue = userStore.refreshToken

  if (!refreshTokenValue) {
    throw new Error('No refresh token available')
  }

  refreshTokenPromise = service.post<Result<{ accessToken: string; refreshToken: string }>>(
    '/auth/refresh',
    { refreshToken: refreshTokenValue }
  )
    .then((res: any) => {
      const tokenData = res.data || res
      const { accessToken, refreshToken: newRefreshToken } = tokenData
      userStore.setTokenAction(accessToken, newRefreshToken)
      onRefreshed(accessToken)
      return accessToken
    })
    .catch((error) => {
      logError(error)
      userStore.resetStateAction()
      router.push({ name: 'Login' })
      throw error
    })
    .finally(() => {
      refreshTokenPromise = null
    })

  return refreshTokenPromise
}

// 模拟请求处理函数
function handleMockRequest(config: InternalAxiosRequestConfig): Promise<any> {
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
  if (url.includes('/report/audit/approve') || url.includes('/report/audit/reject')) {
    return reportService.auditReport(data)
  }
  if (url.includes('/report/publish')) {
    return reportService.publishReport(data)
  }

  // 系统相关
  if (url.includes('/user/dept/tree')) {
    return systemService.getDepartments()
  }

  // 默认返回空数据
  return Promise.resolve({})
}

service.interceptors.request.use(
  async (config: InternalAxiosRequestConfig) => {
    // 直接返回模拟数据，不发送真实请求
    return handleMockRequest(config).then((mockData) => {
      // 创建一个假的响应
      return Promise.resolve({
        data: {
          code: 200,
          message: 'success',
          data: mockData
        },
        status: 200,
        statusText: 'OK',
        headers: {},
        config: config
      } as AxiosResponse)
    })
  },
  (error) => {
    logError(error)
    return Promise.reject(error)
  }
)

service.interceptors.response.use(
  (response: AxiosResponse<Result>) => {
    // 移除请求取消控制器
    removeCancelController(response.config)
    
    const { data } = response
    const { code, message, data: result } = data

    if (code === 200) {
      return result
    }

    // 处理业务错误
    let errorType: ErrorType = ErrorType.UNKNOWN_ERROR
    switch (code) {
      case 401:
        errorType = ErrorType.AUTH_ERROR
        break
      case 403:
        errorType = ErrorType.FORBIDDEN_ERROR
        break
      case 404:
        errorType = ErrorType.NOT_FOUND_ERROR
        break
      case 500:
        errorType = ErrorType.SERVER_ERROR
        break
      default:
        if (code >= 400 && code < 500) {
          errorType = ErrorType.CLIENT_ERROR
        } else if (code >= 500) {
          errorType = ErrorType.SERVER_ERROR
        }
    }

    const errorMessage = message || errorMessages[errorType]
    ElMessage.error(errorMessage)
    
    // 处理认证错误
    if (code === 401) {
      const userStore = useUserStore()
      userStore.resetStateAction()
      router.push({ name: 'Login' })
    }

    return Promise.reject(new Error(errorMessage))
  },
  async (error: AxiosError) => {
    // 移除请求取消控制器
    if (error.config) {
      removeCancelController(error.config)
    }
    
    const { response, config, code: errorCode } = error
    
    // 处理请求取消错误
    if (errorCode === 'ERR_CANCELED') {
      return Promise.reject(new Error('请求已取消'))
    }
    
    // 记录错误日志
    logError(error, config)

    // 处理网络错误和超时错误
    if (!response) {
      let errorType: ErrorType = ErrorType.NETWORK_ERROR
      if (errorCode === 'ECONNABORTED') {
        errorType = ErrorType.TIMEOUT_ERROR
      }
      ElMessage.error(errorMessages[errorType])
      return Promise.reject(error)
    }

    const { status, data: respData } = response

    switch (status) {
      case 401:
        if (!isRefreshing) {
          isRefreshing = true
          try {
            const newToken = await refreshToken()
            config!.headers.Authorization = `Bearer ${newToken}`
            return service(config!)
          } catch (refreshError) {
            logError(refreshError)
            ElMessage.error(errorMessages[ErrorType.AUTH_ERROR])
          } finally {
            isRefreshing = false
          }
        } else {
          return new Promise((resolve) => {
            subscribeTokenRefresh((token) => {
              config!.headers.Authorization = `Bearer ${token}`
              resolve(service(config!))
            })
          })
        }
        break

      case 403:
        ElMessage.error(errorMessages[ErrorType.FORBIDDEN_ERROR])
        break

      case 404:
        ElMessage.error(errorMessages[ErrorType.NOT_FOUND_ERROR])
        break

      case 500:
        ElMessage.error((respData as any)?.message || errorMessages[ErrorType.SERVER_ERROR])
        break

      default:
        if (status >= 400 && status < 500) {
          ElMessage.error((respData as any)?.message || errorMessages[ErrorType.CLIENT_ERROR])
        } else if (status >= 500) {
          ElMessage.error((respData as any)?.message || errorMessages[ErrorType.SERVER_ERROR])
        } else {
          ElMessage.error((respData as any)?.message || errorMessages[ErrorType.UNKNOWN_ERROR])
        }
    }

    return Promise.reject(error)
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
  const { retry = 0, retryDelay = 1000, retryableStatusCodes = [429, 500, 502, 503, 504], retryableErrorTypes = ['ECONNABORTED', 'ETIMEDOUT', 'ENOTFOUND', 'ECONNRESET', 'EAI_AGAIN'], cache = false, cacheExpire = 5 * 60 * 1000, cacheKey } = config
  
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
        console.log('Using cached data for:', key)
        return Promise.resolve(cachedData.data as T)
      }
    }
  }
  
  let retryCount = 0
  
  function attemptRequest(): Promise<T> {
    return service.request<any, T>(config)
      .then((response) => {
        // 缓存响应数据
        if (cache) {
          requestCache.set(key, {
            data: response,
            timestamp: Date.now()
          })
        }
        return response
      })
      .catch((error: AxiosError) => {
        // 检查是否应该重试
        const shouldRetry = retryCount < retry && (
          // 检查状态码
          (error.response && retryableStatusCodes.includes(error.response.status)) ||
          // 检查错误类型
          (error.code && retryableErrorTypes.includes(error.code))
        )
        
        if (shouldRetry) {
          retryCount++
          console.log(`Request failed, retrying (${retryCount}/${retry})...`, error.message)
          // 延迟重试
          return new Promise((resolve) => {
            setTimeout(() => {
              resolve(attemptRequest())
            }, retryDelay * retryCount) // 指数退避
          })
        }
        
        // 不重试，直接抛出错误
        return Promise.reject(error)
      })
  }
  
  return attemptRequest()
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
