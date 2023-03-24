import request from "@/utils/request";


const moduleName = "sysDept"

export default {

  getAllDeptList(params) {
    return request({
      url: `/${moduleName}/listDept`,
      method: "get",
      params: params
    })
  },


  save(data) {
    return request({
      url: `/${moduleName}/save`,
      method: "post",
      data: data
    })
  },


  getById(deptId) {
    return request({
      url: `/${moduleName}/get/` + deptId,
      method: "get"
    })
  },


  update(data) {
    return request({
      url: `/${moduleName}/update`,
      method: "put",
      data: data
    })
  },
  delete(deptId) {
    return request({
      url: `/${moduleName}/delete/`+deptId,
      method: "delete"
    })
  },

}
