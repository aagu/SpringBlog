import request from '@/utils/request'

export function statistic() {
  return request({
    url: '/admin/dashboard/statistic',
    method: 'get'
  })
}

export function getLogs(query) {
  return request({
    url: 'logs',
    method: 'get',
    params: query
  })
}