import request from '@/utils/request'

export function statistic() {
  return request({
    url: '/admin/dashboard/statistic',
    method: 'get'
  })
}