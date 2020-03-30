import request from '@/utils/request'

export function archiveItems() {
  return request({
    url: '/archives',
    method: 'get',
  })
}