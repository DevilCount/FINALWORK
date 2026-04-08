import axios, { type AxiosInstance, type AxiosRequestConfig, type AxiosResponse, type InternalAxiosRequestConfig } from 'axios'
import { ElMessage } from 'element-plus'
import { useUserStore } from '@/stores/modules/user'
import router from '@/router'
import type { Result } from '@/types'

const service: AxiosInstance = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || '/api',
  timeout: import.meta.env.VITE_API_TIMEOUT || 30000,
  headers: {
    'Content-Type': 'application/json',
  },
})

let isRefreshing = false
let refreshSubscribers: ((token: string) => void)[] = []

function subscribeTokenRefresh(callback: (token: string) => void) {
  refreshSubscribers.push(callback)
}

function onRefreshed(token: string) {
  refreshSubscribers.forEach((callback) => callback(token))
  refreshSubscribers = []
}

service.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    const userStore = useUserStore()
    const token = userStore.token

    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }

    return config
  },
  (error) => {
    console.error('Request error:', error)
    return Promise.reject(error)
  }
)

service.interceptors.response.use(
  (response: AxiosResponse<Result>) => {
    const { data } = response
    const { code, message, data: result } = data

    if (code === 200) {
      return result as unknown as AxiosResponse
    }

    if (code === 401) {
      const userStore = useUserStore()
      userStore.resetStateAction()
      router.push({ name: 'Login' })
      ElMessage.error('登录已过期，请重新登录')
      return Promise.reject(new Error(message || '未授权'))
    }

    if (code === 403) {
      ElMessage.error('没有权限访问该资源')
      return Promise.reject(new Error(message || '无权限'))
    }

    ElMessage.error(message || '请求失败')
    return Promise.reject(new Error(message || '请求失败'))
  },
  async (error) => {
    const { response, config } = error

    if (!response) {
      ElMessage.error('网络连接失败，请检查网络')
      return Promise.reject(error)
    }

    const { status, data } = response
    const userStore = useUserStore()

    switch (status) {
      case 401:
        if (!isRefreshing) {
          isRefreshing = true
          try {
            const refreshTokenValue = userStore.refreshToken
            if (refreshTokenValue) {
              const res = await axios.post<Result<{ accessToken: string; refreshToken: string }>>('/api/auth/refresh', {
                refreshToken: refreshTokenValue,
              })
              const { accessToken, refreshToken: newRefreshToken } = res.data.data
              userStore.token = accessToken
              userStore.refreshToken = newRefreshToken
              onRefreshed(accessToken)
              config.headers.Authorization = `Bearer ${accessToken}`
              return service(config)
            }
          } catch {
            userStore.resetStateAction()
            router.push({ name: 'Login' })
            ElMessage.error('登录已过期，请重新登录')
          } finally {
            isRefreshing = false
          }
        } else {
          return new Promise((resolve) => {
            subscribeTokenRefresh((token) => {
              config.headers.Authorization = `Bearer ${token}`
              resolve(service(config))
            })
          })
        }
        break

      case 403:
        ElMessage.error('没有权限访问该资源')
        break

      case 404:
        ElMessage.error('请求的资源不存在')
        break

      case 500:
        ElMessage.error(data?.message || '服务器内部错误')
        break

      default:
        ElMessage.error(data?.message || `请求失败: ${status}`)
    }

    return Promise.reject(error)
  }
)

export interface RequestOptions extends AxiosRequestConfig {
  showLoading?: boolean
  showError?: boolean
}

export function request<T = unknown>(config: RequestOptions): Promise<T> {
  return service.request<any, T>(config)
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

export function del<T = unknown>(url: string, params?: object, config?: RequestOptions): Promise<T> {
  return request<T>({ ...config, method: 'DELETE', url, params })
}

export default service
