import router from './router'
import store from './store'
import NProgress from 'nprogress'
import {getToken} from '@/utils/auth'

const blackUrl = '/admin'

router.beforeEach(async(to, from, next) => {
  NProgress.start()

  if (to.path.indexOf(blackUrl) !== -1) {
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
            store.dispatch('getInfo').then(() => {
              next()
            }).catch(reason => {
              next(`login?redirect=${to.path}`)
            })
          } catch (error) {
            await store.dispatch('resetToken')
            next(`login?redirect=${to.path}`)
          }
        }
      }
    } else {
      next(`login?redirect=${to.path}`)
    }
  } else {
    next()
  }
})

router.afterEach(async (to, from, next) => {
  NProgress.done()
  document.title = to.meta.title
  await store.dispatch('setToolbar', to.meta.title)
})