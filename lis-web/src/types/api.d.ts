export interface Result<T = any> {
  code: number
  message: string
  data: T
  timestamp: number
}

export interface PageResult<T = any> {
  records: T[]
  total: number
  pageSize: number
  pageNum: number
  totalPages: number
}

export interface PageParams {
  pageNum?: number
  pageSize?: number
}

export interface KeyValue {
  label: string
  value: string | number
}
