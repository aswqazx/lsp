import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/user/login',
    method: 'post',
    data
  })
}

export function getInfo(token) {
  return request({
    url: '/user/info',
    method: 'get',
    params: { token }
  })
}

export function logout() {
  return request({
    url: '/user/logout',
    method: 'post'
  })
}

export function getUserList(data) {
  return request({
    url: '/user/list',
    method: 'post',
    data
  })
}

export function userAddOrUpdate(data) {
  return request({
    url: '/user/addOrUpdate',
    method: 'post',
    data
  })
}

export function userDelete(data) {
  return request({
    url: '/user/delete',
    method: 'post',
    data
  })
}
