import request from '@/utils/request'

export function getALiPay(data) {
  return request({
    url: '/alipay/webPay',
    method: 'post',
    data
  })
}
