import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/user/login',
    method: 'post',
    data
  })
}

export function getArticle(query) {
  return request({
    url: '/blog',
    method: 'get',
    params: query
  })
}