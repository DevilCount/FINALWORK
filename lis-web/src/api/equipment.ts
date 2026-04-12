import { get, post, put, del } from '@/utils/request'
import type { PageResult } from '@/types/api'

export interface Equipment {
  id: number
  equipmentCode: string
  equipmentName: string
  equipmentNameEn: string
  equipmentTypeId: number
  equipmentTypeName: string
  brand: string
  model: string
  serialNo: string
  manufacturer: string
  supplier: string
  purchaseDate: string
  installDate: string
  warrantyExpireDate: string
  useLife: number
  originalValue: number
  netValue: number
  location: string
  labId: number
  labName: string
  responsibleUserId: number
  responsibleUserName: string
  contactPhone: string
  status: string
  statusDesc: string
  isOnline: number
  ipAddress: string
  port: number
  communicationProtocol: string
  baudRate: number
  lastCommTime: string
  lastCalibrationDate: string
  nextCalibrationDate: string
  lastMaintenanceDate: string
  nextMaintenanceDate: string
  createTime: string
  updateTime: string
  remark: string
}

export interface EquipmentQuery {
  pageNum?: number
  pageSize?: number
  keyword?: string
  equipmentCode?: string
  equipmentName?: string
  equipmentTypeId?: number
  brand?: string
  model?: string
  status?: string
  isOnline?: number
  labId?: number
  responsibleUserId?: number
}

export interface EquipmentForm {
  id?: number
  equipmentCode: string
  equipmentName: string
  equipmentNameEn?: string
  equipmentTypeId?: number
  equipmentTypeName?: string
  brand?: string
  model?: string
  serialNo?: string
  manufacturer?: string
  supplier?: string
  purchaseDate?: string
  installDate?: string
  warrantyExpireDate?: string
  useLife?: number
  originalValue?: number
  netValue?: number
  location?: string
  labId?: number
  labName?: string
  responsibleUserId?: number
  responsibleUserName?: string
  contactPhone?: string
  status?: string
  ipAddress?: string
  port?: number
  communicationProtocol?: string
  baudRate?: number
  remark?: string
}

export interface MaintenanceRecord {
  id: number
  equipmentId: number
  equipmentName: string
  maintenanceType: string
  maintenanceContent: string
  maintenanceResult: string
  technician: string
  startTime: string
  endTime: string
  cost: number
  remark: string
  status: string
  createTime: string
}

export interface MaintenanceQuery {
  pageNum?: number
  pageSize?: number
  maintenanceNo?: string
  equipmentId?: number
  equipmentCode?: string
  equipmentName?: string
  maintenanceType?: string
  maintenanceStatus?: string
  status?: number
}

export interface MaintenanceForm {
  id?: number
  equipmentId: number
  maintenanceType: string
  maintenanceContent: string
  maintenanceResult?: string
  technician: string
  startTime: string
  endTime?: string
  cost?: number
  remark?: string
}

export interface CalibrationRecord {
  id: number
  equipmentId: number
  equipmentName: string
  calibrationType: string
  calibrationResult: string
  calibrator: string
  calibrationDate: string
  nextCalibrationDate: string
  remark: string
  status: string
  createTime: string
}

export interface CalibrationQuery {
  pageNum?: number
  pageSize?: number
  calibrationNo?: string
  equipmentId?: number
  equipmentCode?: string
  equipmentName?: string
  calibrationType?: string
  calibrationResult?: string
  status?: number
}

export interface CalibrationForm {
  id?: number
  equipmentId: number
  calibrationType: string
  calibrationResult?: string
  calibrator: string
  calibrationDate: string
  nextCalibrationDate: string
  remark?: string
}

export interface EquipmentStatus {
  id: number
  equipmentId: number
  equipmentName: string
  isOnline: number
  lastCommTime: string
  totalTestCount: number
  todayTestCount: number
  errorCount: number
  updateTime: string
}

function mapEquipmentToBackend(data: any): any {
  return {
    id: data.id,
    equipmentCode: data.equipmentCode,
    equipmentName: data.equipmentName,
    equipmentNameEn: data.equipmentNameEn,
    equipmentTypeId: data.equipmentTypeId,
    equipmentTypeName: data.equipmentTypeName,
    brand: data.brand,
    model: data.model || data.equipmentModel,
    serialNo: data.serialNo || data.serialNumber,
    manufacturer: data.manufacturer,
    supplier: data.supplier,
    purchaseDate: data.purchaseDate,
    installDate: data.installDate,
    warrantyExpireDate: data.warrantyExpireDate || data.warrantyExpiry,
    useLife: data.useLife,
    originalValue: data.originalValue,
    netValue: data.netValue,
    location: data.location,
    labId: data.labId || data.departmentId,
    labName: data.labName,
    responsibleUserId: data.responsibleUserId,
    responsibleUserName: data.responsibleUserName || data.responsiblePerson,
    contactPhone: data.contactPhone || data.responsiblePhone,
    status: data.status,
    ipAddress: data.ipAddress,
    port: data.port,
    communicationProtocol: data.communicationProtocol,
    baudRate: data.baudRate,
    remark: data.remark,
  }
}

function mapMaintenanceToBackend(data: any): any {
  return {
    id: data.id,
    equipmentId: data.equipmentId,
    maintenanceType: data.maintenanceType || data.type,
    maintenanceContent: data.maintenanceContent || data.content,
    maintenanceResult: data.maintenanceResult || data.result,
    technician: data.technician,
    startTime: data.startTime,
    endTime: data.endTime,
    cost: data.cost,
    remark: data.remark || data.notes,
  }
}

export function getEquipmentList(params: EquipmentQuery): Promise<PageResult<Equipment>> {
  const { keyword, ...rest } = params
  const mappedParams = {
    ...rest,
    equipmentName: rest.equipmentName || keyword || undefined,
  }
  return post('/equipment/page', mappedParams)
}

export function getEquipmentDetail(id: number): Promise<Equipment> {
  return get(`/equipment/${id}`)
}

export function getEquipmentById(id: number): Promise<Equipment> {
  return get(`/equipment/${id}`)
}

export function getEquipmentByCode(code: string): Promise<Equipment> {
  return get(`/equipment/code/${code}`)
}

export function createEquipment(data: any): Promise<number> {
  return post('/equipment', mapEquipmentToBackend(data))
}

export function updateEquipment(id: number | any, data?: any): Promise<void> {
  const formData = typeof id === 'object' ? mapEquipmentToBackend(id) : mapEquipmentToBackend({ ...data, id })
  return put('/equipment', formData)
}

export function deleteEquipment(id: number): Promise<void> {
  return del(`/equipment/${id}`)
}

export function batchDeleteEquipment(ids: number[]): Promise<void> {
  return del('/equipment/batch', ids as any)
}

export function updateEquipmentStatus(id: number, status: string): Promise<void> {
  return put(`/equipment/${id}/status`, {}, { params: { status } })
}

export function getEquipmentStatusById(equipmentId: number): Promise<EquipmentStatus> {
  return get(`/equipment/status/${equipmentId}`)
}

export function getEquipmentStatus(equipmentId: number): Promise<EquipmentStatus> {
  return getEquipmentStatusById(equipmentId)
}

export function getAllEquipmentStatus(): Promise<EquipmentStatus[]> {
  return get('/equipment/status/all')
}

export function updateEquipmentOnlineStatus(equipmentId: number, isOnline: number): Promise<void> {
  return put(`/equipment/status/${equipmentId}/online`, {}, { params: { isOnline } })
}

export function updateEquipmentCommTime(equipmentId: number): Promise<void> {
  return put(`/equipment/status/${equipmentId}/comm`)
}

export function getMaintenanceRecords(params: MaintenanceQuery): Promise<PageResult<MaintenanceRecord>> {
  return getMaintenanceList(params)
}

export function getMaintenanceList(params: MaintenanceQuery): Promise<PageResult<MaintenanceRecord>> {
  return get('/equipment/maintenance/page', params)
}

export function getMaintenanceDetail(id: number): Promise<MaintenanceRecord> {
  return get(`/equipment/maintenance/${id}`)
}

export function createMaintenanceRecord(data: any): Promise<number> {
  return createMaintenance(data)
}

export function createMaintenance(data: any): Promise<number> {
  return post('/equipment/maintenance', mapMaintenanceToBackend(data))
}

export function updateMaintenanceRecord(id: number | any, data?: any): Promise<void> {
  return updateMaintenance(typeof id === 'object' ? id : { ...data, id })
}

export function updateMaintenance(data: any): Promise<void> {
  return put('/equipment/maintenance', mapMaintenanceToBackend(data))
}

export function deleteMaintenanceRecord(id: number): Promise<void> {
  return deleteMaintenance(id)
}

export function deleteMaintenance(id: number): Promise<void> {
  return del(`/equipment/maintenance/${id}`)
}

export function batchDeleteMaintenance(ids: number[]): Promise<void> {
  return del('/equipment/maintenance/batch', ids as any)
}

export function completeMaintenance(id: number): Promise<void> {
  return put(`/equipment/maintenance/${id}/complete`)
}

export function getCalibrationList(params: CalibrationQuery): Promise<PageResult<CalibrationRecord>> {
  return get('/equipment/calibration/page', params)
}

export function getCalibrationDetail(id: number): Promise<CalibrationRecord> {
  return get(`/equipment/calibration/${id}`)
}

export function createCalibration(data: CalibrationForm): Promise<number> {
  return post('/equipment/calibration', data)
}

export function updateCalibration(data: CalibrationForm): Promise<void> {
  return put('/equipment/calibration', data)
}

export function deleteCalibration(id: number): Promise<void> {
  return del(`/equipment/calibration/${id}`)
}

export function batchDeleteCalibration(ids: number[]): Promise<void> {
  return del('/equipment/calibration/batch', ids as any)
}

export function completeCalibration(id: number): Promise<void> {
  return put(`/equipment/calibration/${id}/complete`)
}

export interface EquipmentAlert {
  id: number
  equipmentId: number
  equipmentName: string
  alertType: string
  alertLevel: string
  message: string
  isRead: number
  createTime: string
}

export function getEquipmentAlerts(equipmentId: number): Promise<EquipmentAlert[]> {
  return get(`/equipment/${equipmentId}/alerts`)
}

export function markAlertAsRead(alertId: number): Promise<void> {
  return put(`/equipment/alert/${alertId}/read`)
}
