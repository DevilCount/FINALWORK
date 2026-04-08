export interface UserInfo {
  id: number
  username: string
  realName: string
  avatar?: string
  phone?: string
  email?: string
  deptId?: number
  deptName?: string
  status: number
  createTime?: string
  roles?: string[]
  permissions?: string[]
}

export interface LoginForm {
  username: string
  password: string
  captcha: string
  uuid: string
  rememberMe?: boolean
}

export interface LoginResult {
  accessToken: string
  refreshToken: string
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
  icon?: string
  sort: number
  menuType: 'directory' | 'menu' | 'button'
  visible: number
  permission?: string
  children?: MenuVO[]
}
