import request from "@/utils/request";


const moduleName = "sysDictData"

export default {

  getByPage(params) {
    return request({
      url: `/${moduleName}/getByPage`,
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


  get(dictCode) {
    return request({
      url: `/${moduleName}/get/` + dictCode,
      method: "get"
    })
  },


  delete(dictCode) {
    return request({
      url: `/${moduleName}/delete/` + dictCode,
      method: "delete"
    })
  },

  getDictDataByDictType(dictType) {
    return request({
      url: `/${moduleName}/getDictDataByDictType/` + dictType,
      method: "get"
    })
  }


}
