import request from '@/utils/request'

export function getLabels() {
  return request({
    url: '/admin/labels',
    method: 'get'
  })
}

export function addLabel(data) {
  return request({
    url: '/admin/label',
    method: 'post',
    data
  })
}

export function deleteLabel(id) {
  return request({
    url: '/admin/label',
    method: 'delete',
    params: {id: id}
  })
}

export function editLabel(label) {
  return request({
    url: '/admin/label',
    method: 'put',
    data: label
  })
}