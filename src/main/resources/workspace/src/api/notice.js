import request from '@/utils/request'

export function getNotice(id) {
  return request({
    url: `/detail/${id}`,
    method: 'get',
  })
}

export function getNoticeList(query) {
  return request({
    url: '/notice/list',
    method: 'get',
    params: query
  })
}

export function getNoticeDetail(id) {
  return request({
    url: '/admin/notice/detail',
    method: 'get',
    params: {id: id}
  })
}

export function updateNotice(data) {
  return request({
    url: '/notice/update',
    method: 'post',
    data
  })
}

export function createNotice(data) {
  return request({
    url: '/notice',
    method: 'post',
    data
  })
}

export function publishNotice(id) {
  return request({
    url: `/admin/notice/${id}`,
    method: 'put'
  })
}

export function deleteNotice(id) {
  return request({
    url: `/admin/notice/${id}`,
    method: 'delete'
  })
}