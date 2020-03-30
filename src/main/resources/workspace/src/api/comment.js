import request from '@/utils/request'

export function getCommentList(query) {
  return request({
    url: '/admin/comment/list',
    method: 'get',
    params: query
  })
}

export function postComment(data) {
  return request({
    url: '/comments',
    method: 'post',
    data
  })
}

export function getArticleComment(query) {
  return request({
    url: '/comments',
    method: 'get',
    params: query
  })
}