import request from "@/utils/request";

const moduleName = "sysMenu"

export default {

  getAllMenuList(params) {
    return request({
      url: `/${moduleName}/listMenu`,
      params: params,
      method: "get"
    })
  },

  getMenuMC(){
    return request({
      url:`/${moduleName}/listMenuMC`,
      method:"get"
    })
  },


  save(data) {
    return request({
      url: `/${moduleName}/save`,
      method: "post",
      data: data
    })
  },


  getById(menuId) {
    return request({
      url: `/${moduleName}/get/` + menuId,
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
  delete(menuId) {
    return request({
      url: `/${moduleName}/delete/`+menuId,
      method: "delete"
    })
  },

  getRouters() {
    return request({
      url: `/${moduleName}/getRouters`,
      method: "get"
    })
  }


}
