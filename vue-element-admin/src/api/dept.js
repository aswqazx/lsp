import request from '@/utils/request'

export function getDeptList(data) {
  return request({
    url: '/dept/list',
    method: 'post',
    data
  })
}

export function deptAddOrUpdate(data) {
  return request({
    url: '/dept/addOrUpdate',
    method: 'post',
    data
  })
}

export function deptDelete(data) {
  return request({
    url: '/dept/delete',
    method: 'post',
    data
  })
}
