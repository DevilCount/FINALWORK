declare module 'nprogress' {
  interface NProgressOptions {
    minimum?: number
    template?: string
    easing?: string
    positionUsing?: string
    speed?: number
    trickle?: boolean
    trickleSpeed?: number
    showSpinner?: boolean
    barSelector?: string
    spinnerSelector?: string
    parent?: string
  }

  interface NProgress {
    version: string
    settings: NProgressOptions
    configure(options: NProgressOptions): NProgress
    set(number: number): NProgress
    start(): NProgress
    done(force?: boolean): NProgress
    remove(): void
    isStarted(): boolean
    status: number | null
  }

  const nprogress: NProgress
  export default nprogress
}
