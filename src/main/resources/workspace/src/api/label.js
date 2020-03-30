import request from '@/utils/request'

export function getLabels() {
    return request({
      url: '/admin/labels',
      method: 'get'
    })
  }