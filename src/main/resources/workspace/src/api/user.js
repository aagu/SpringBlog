import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/admin/user/login',
    method: 'post',
    data
  })
}

export function logout() {
  return request({
    url: '/admin/user/logout',
    method: 'post'
  })
}

export function getInfo(token) {
  return request({
    url: '/admin/user/info',
    method: 'get',
    params: { token: token }
  })
}

export function sec(name) {
  return request({
    url: '/user/sec',
    method: 'get',
    params: { name: name }
  })
}

export function getUser() {
  return request({
    url: '/user/info',
    method: 'get',
  })
}