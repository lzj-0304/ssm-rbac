import request from "@/utils/request";


const moduleName = "sysNotice"

export default {

  getByPage(params) {
    return request({
      url: `/${moduleName}/getPage`,
      method: "post",
      data: params
    })
  },


  save(data) {
    return request({
      url: `/${moduleName}/save`,
      method: "post",
      data: data
    })
  },


  update(data) {
    return request({
      url: `/${moduleName}/update`,
      method: "put",
      data: data
    })
  },




  delete(noticeId) {
    return request({
      url: `/${moduleName}/delete/` + noticeId,
      method: "delete"
    })
  },


  getById(noticeId){
    return request({
      url:`/${moduleName}/get/`+noticeId,
      method:"get"
    })
  },




}
