import { get, post } from '@/utils/request'
import type { HisLabOrderForm } from '@/types/his'

export function submitLabOrder(data: HisLabOrderForm): Promise<any> {
  const { interfaceCode, ...rest } = data
  return post('/hl7/his-integration/lab-order', {
    patientId: rest.patientIdNo,
    patientName: rest.patientName,
    gender: rest.patientGender,
    idCardNo: rest.patientIdNo,
    phone: rest.patientPhone,
    visitClass: rest.visitType,
    department: rest.deptName,
    bedNo: rest.bedNo,
    visitNo: rest.visitNo,
    attendingDoctor: rest.doctorName,
    clinicalInfo: rest.clinicalDiagnosis,
    interfaceCode: interfaceCode,
    orderItems: rest.testItemIds.map(id => ({
      orderItemCode: String(id),
      specimenType: rest.specimenTypeName,
      clinicalInfo: rest.clinicalDiagnosis,
    })),
  }, { params: { interfaceCode } })
}

export function getHl7MessageDetail(id: string): Promise<any> {
  return get(`/hl7/hl7-message/${id}`)
}

export function getInterfaceConfigList(params?: any): Promise<any> {
  return get('/hl7/interface-config/page', { pageNum: 1, pageSize: 100, ...params })
}
