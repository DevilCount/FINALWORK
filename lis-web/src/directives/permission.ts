import type { Directive, DirectiveBinding } from 'vue'
import { useUserStore } from '@/stores/modules/user'

function checkPermission(el: HTMLElement, binding: DirectiveBinding<string | string[]>) {
  const { value } = binding
  if (!value) return

  const userStore = useUserStore()
  const roles = userStore.roles
  const permissions = userStore.permissions

  if (roles && roles.includes('admin')) return

  const requiredPermissions = Array.isArray(value) ? value : [value]
  const hasPermission = requiredPermissions.some(p => permissions && permissions.includes(p))

  if (!hasPermission) {
    el.parentNode?.removeChild(el)
  }
}

export const vPermission: Directive<HTMLElement, string | string[]> = {
  mounted(el, binding) {
    checkPermission(el, binding)
  },
  updated(el, binding) {
    checkPermission(el, binding)
  },
}
