export interface UserInfo {
  id: number
  username: string
  realName: string
  avatar?: string
  phone?: string
  email?: string
  deptId?: number
  deptName?: string
  employeeNo?: string
  status: number
  createTime?: string
  roles?: string[]
  permissions?: string[]
}

export interface LoginForm {
  username: string
  password: string
  captcha?: string
  uuid?: string
  rememberMe?: boolean
}

export interface LoginResult {
  accessToken: string
  refreshToken: string
  tokenType: string
  expiresIn: number
  userId: number
  username: string
  realName: string
  roles: string[]
  permissions: string[]
}

export interface CaptchaResult {
  uuid: string
  captchaImage: string
}

export interface MenuVO {
  id: number
  menuName: string
  parentId: number
  path?: string
  component?: string
  redirect?: string
  icon?: string
  sort: number
  status: number
  visible: number
  type: number
  perms?: string
  children?: MenuVO[]
}
