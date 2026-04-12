export interface LoginDTO {
  username: string
  password: string
}

export interface LoginVO {
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

export interface TokenVO {
  accessToken: string
  refreshToken: string
  tokenType: string
  expiresIn: number
}

export interface RefreshTokenDTO {
  refreshToken: string
}
