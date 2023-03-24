import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/login',
    method: 'post',
    // 普通表单数据
    params: data
  })
}


export function getCaptcha(){
  return request({
    url:"/captcha/getCaptcha",
    method:"get"
  })
}




export function getInfo(token) {
  return request({
    url: '/getUserInfo',
    method: "get"
  })
}

export function logout() {
  return request({
    url: '/logout',
    method: 'get'
  })
}
