import request from '@/utils/request'
import * as qiniu from 'qiniu-js'

export function getList() {
  return request({
    url: 'resources',
    method: 'get'
  })
}

export function getToken(key) {
  return request({
    url: 'resources/token',
    method: 'get',
    params: { oldKey: key }
  })
}

export function upload(file, next, error, complete) {
  getToken(null).then(resp => {
    const token = resp.data.data
    let observable = qiniu.upload(file, file.name, token, {}, config)
    const observer = {
      next: next,
      error: error,
      complete: complete
    }
    observable.subscribe(observer)
  })
}