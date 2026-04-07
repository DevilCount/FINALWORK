export interface LoginDTO {
  username: string
  password: string
  captcha: string
  uuid: string
  rememberMe?: boolean
}

export interface LoginVO {
  accessToken: string
  refreshToken: string
  expiresIn: number
}

export interface TokenVO {
  accessToken: string
  refreshToken: string
  expiresIn: number
}
