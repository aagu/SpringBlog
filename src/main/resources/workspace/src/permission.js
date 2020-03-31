import router from './router'
import store from './store'
import { getToken } from '@/utils/auth'

const blackUrl = '/admin'

router.beforeEach(async(to, from, next) => {
  document.title = to.meta.title

  const hasToken = getToken()

  if (hasToken) {
     if (to.path === '/login') {
        next({ path: '/admin' })
     } else {
        const hasGetUserInfo = store.getters.name
        if (hasGetUserInfo) {
          next()
        } else {
          try {
            await store.dispatch('getInfo')
            next()
          } catch (error) {
            await store.dispatch('resetToken')
            next(`login?redirect=${to.path}`)
        }
      }
    }
  } else {
    if (to.path.indexOf(blackUrl) !== -1) {
      next(`/login?redirect=${to.path}`)
    } else {
      next()
    }
  }
})

router.afterEach(async (to, from, next) => {
  await store.dispatch('setToolbar', to.meta.title)
})