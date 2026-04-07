import { get, post, put, del } from '@/utils/request'

export interface Equipment {
  id: string
  code: string
  name: string
  model: string
  manufacturer: string
  serialNumber: string
  purchaseDate: string
  warrantyExpiry: string
  status: 'online' | 'offline' | 'maintenance' | 'fault'
  department: string
  location: string
  responsiblePerson: string
  lastMaintenanceDate: string
  nextMaintenanceDate: string
  lastCalibrationDate: string
  nextCalibrationDate: string
  createdAt: string
  updatedAt: string
}

export interface EquipmentQuery {
  page: number
  pageSize: number
  keyword?: string
  status?: string
  department?: string
}

export interface EquipmentListResult {
  list: Equipment[]
  total: number
}

export interface MaintenanceRecord {
  id: string
  equipmentId: string
  equipmentName: string
  type: 'calibration' | 'maintenance' | 'repair'
  content: string
  result: 'success' | 'failed' | 'partial'
  technician: string
  startTime: string
  endTime: string
  cost: number
  notes: string
  attachments: string[]
  createdAt: string
}

export interface MaintenanceQuery {
  page: number
  pageSize: number
  equipmentId?: string
  type?: string
  startDate?: string
  endDate?: string
}

export interface MaintenanceListResult {
  list: MaintenanceRecord[]
  total: number
}

export interface EquipmentStatus {
  id: string
  equipmentId: string
  equipmentName: string
  status: 'online' | 'offline' | 'maintenance' | 'fault'
  runningTime: number
  sampleCount: number
  errorCount: number
  cpuUsage: number
  memoryUsage: number
  diskUsage: number
  lastHeartbeat: string
  alerts: EquipmentAlert[]
}

export interface EquipmentAlert {
  id: string
  equipmentId: string
  type: 'warning' | 'error' | 'info'
  message: string
  timestamp: string
  isRead: boolean
}

export function getEquipmentList(params: EquipmentQuery): Promise<EquipmentListResult> {
  return get('/equipment/list', params)
}

export function getEquipmentDetail(id: string): Promise<Equipment> {
  return get(`/equipment/${id}`)
}

export function createEquipment(data: Partial<Equipment>): Promise<Equipment> {
  return post('/equipment', data)
}

export function updateEquipment(id: string, data: Partial<Equipment>): Promise<Equipment> {
  return put(`/equipment/${id}`, data)
}

export function deleteEquipment(id: string): Promise<void> {
  return del(`/equipment/${id}`)
}

export function getMaintenanceRecords(params: MaintenanceQuery): Promise<MaintenanceListResult> {
  return get('/equipment/maintenance', params)
}

export function getMaintenanceDetail(id: string): Promise<MaintenanceRecord> {
  return get(`/equipment/maintenance/${id}`)
}

export function createMaintenanceRecord(data: Partial<MaintenanceRecord>): Promise<MaintenanceRecord> {
  return post('/equipment/maintenance', data)
}

export function updateMaintenanceRecord(id: string, data: Partial<MaintenanceRecord>): Promise<MaintenanceRecord> {
  return put(`/equipment/maintenance/${id}`, data)
}

export function deleteMaintenanceRecord(id: string): Promise<void> {
  return del(`/equipment/maintenance/${id}`)
}

export function getEquipmentStatus(equipmentId?: string): Promise<EquipmentStatus[]> {
  return get('/equipment/status', { equipmentId })
}

export function getEquipmentAlerts(equipmentId?: string): Promise<EquipmentAlert[]> {
  return get('/equipment/alerts', { equipmentId })
}

export function markAlertAsRead(alertId: string): Promise<void> {
  return put(`/equipment/alerts/${alertId}/read`)
}
