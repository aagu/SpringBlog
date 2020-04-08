import router from './router'
import store from './store'
import NProgress from 'nprogress'
import {getToken} from '@/utils/auth'
import getPageTitle from "@/utils/get-page-title";

const blackUrl = '/admin'

router.beforeEach(async(to, from, next) => {
  NProgress.start()

  document.title = getPageTitle(to.meta.title)

  if (to.path.indexOf(blackUrl) !== -1) {
    const hasToken = getToken()

    if (hasToken) {
      if (to.path === '/login') {
        next({ path: '/admin' })
      } else {
        next()
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
  await store.dispatch('setToolbar', to.meta.title)
})