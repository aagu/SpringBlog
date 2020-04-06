import axios from 'axios'
import Notification from '@/components/Notification/Notification'
import store from "@/store";
import router from '@/router'
import {getToken} from '@/utils/auth'

// create an axios instance
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API, // url = base url + request url
  // withCredentials: true, // send cookies when cross-domain requests
  timeout: 5000 // request timeout
})

// request interceptor
service.interceptors.request.use(
  config => {
    // do something before request is sent

    if (store.getters.token) {
      config.headers['token'] = getToken()
    }
    return config
  },
  error => {
    // do something with request error
    Notification.error(error)
    return Promise.reject(error)
  }
)

// response interceptor
service.interceptors.response.use(
  /**
   * If you want to get http information such as headers or status
   * Please return  response => response
  */

  /**
   * Determine the request status by custom code
   * Here is just an example
   * You can also judge the status by HTTP Status Code
   */
  response => {
    const code = response.data.code
    if (code === 41000) {
      store.dispatch('resetToken').then(() => {
        location.reload()
      })
      return Promise.reject(new Error('Token Expired, please login again'))
    }
    if (code === 40400) {
      router.push({ name: "404"})
    }
    return response
  },
  error => {
    Notification.error(error)
    return Promise.reject(error)
  }
)

export default service
