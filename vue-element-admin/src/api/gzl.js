import request from '@/utils/request'

export function fblc(query) {
  return request({
    url: '/gzl/lc/fblc',
    method: 'post',
    data: query
  })
}

export function getLcslList(query) {
  return request({
    url: '/gzl/lc/lcsllb',
    method: 'post',
    data: query
  })
}

export function lckg(query) {
  return request({
    url: '/gzl/lc/lckg',
    method: 'post',
    data: query
  })
}

export function lct(query) {
  return request({
    url: '/gzl/lc/lct',
    method: 'post',
    data: query
  })
}

export function getWdsqList(query) {
  return request({
    url: '/gzl/lc/wdsq',
    method: 'post',
    data: query
  })
}

export function hqdqjd(query) {
  return request({
    url: '/gzl/lc/hqdqjd',
    method: 'post',
    data: query
  })
}

export function getWddbList(query) {
  return request({
    url: '/gzl/lc/wddb',
    method: 'post',
    data: query
  })
}

export function sh(query) {
  return request({
    url: '/gzl/lc/sh',
    method: 'post',
    data: query
  })
}

export function getWdybList(query) {
  return request({
    url: '/gzl/lc/wdyb',
    method: 'post',
    data: query
  })
}

export function bmks(query) {
  return request({
    url: '/gzl/bm/kslc',
    method: 'post',
    data: query
  })
}

export function lcks(query) {
  return request({
    url: '/gzl/lc/lcks',
    method: 'post',
    data: query
  })
}

export function hqjd(query) {
  return request({
    url: '/gzl/lc/hqjd',
    method: 'post',
    data: query
  })
}

export function rwfpry(query) {
  return request({
    url: '/gzl/lc/rwfpry',
    method: 'post',
    data: query
  })
}

export function lcch(query) {
  return request({
    url: '/gzl/lc/lcch',
    method: 'post',
    data: query
  })
}

export function lczz(query) {
  return request({
    url: '/gzl/lc/lczz',
    method: 'post',
    data: query
  })
}

export function lccq(query) {
  return request({
    url: '/gzl/lc/lccq',
    method: 'post',
    data: query
  })
}

export function lctz(query) {
  return request({
    url: '/gzl/lc/lctz',
    method: 'post',
    data: query
  })
}

export function lcslsc(query) {
  return request({
    url: '/gzl/lc/lcslsc',
    method: 'post',
    data: query
  })
}

/**
 * 查询所有正在运行的流程
 * @param query
 */
export function selectRunningProcess(query) {
  return request({
    url: '/gzl/lc/selectRunningProcess',
    method: 'post',
    data: query
  })
}

export function lcbh(query) {
  return request({
    url: '/gzl/lc/lcbh',
    method: 'post',
    data: query
  })
}
