import request from '@/utils/request'

export function getArticle(id) {
  return request({
    url: `/detail/${id}`,
    method: 'get',
  })
}

export function getArticleList(query) {
  return request({
    url: '/admin/article/list',
    method: 'get',
    params: query
  })
}

export function getArticleDetail(id) {
  return request({
    url: '/admin/article/detail',
    method: 'get',
    params: {id: id}
  })
}

export function updateArticle(data) {
  return request({
    url: '/admin/article/update',
    method: 'post',
    data
  })
}

export function createArticle(data) {
  return request({
    url: '/admin/article/create',
    method: 'post',
    data
  })
}

export function publishArticle(id) {
  return request({
    url: `/admin/article/${id}`,
    method: 'put'
  })
}

export function deleteArticle(id) {
  return request({
    url: `/admin/article/${id}`,
    method: 'delete'
  })
}