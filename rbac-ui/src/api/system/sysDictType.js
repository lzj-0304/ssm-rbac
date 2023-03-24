import request from "@/utils/request";


const moduleName = "sysDictType"

export default {

  /**
   * 分页查询字典类型记录
   * @param params
   * @returns {AxiosPromise}
   */
  getByPage(data) {
    return request({
      url: `/${moduleName}/getByPage`,
      method: "post",
      data: data
    })
  },

  /**
   * 添加
   * @param data
   * @returns {AxiosPromise}
   */
  save(data) {
    return request({
      url: `/${moduleName}/save`,
      method: "post",
      data: data
    })
  },


  /**
   * 更新
   * @param data
   * @returns {AxiosPromise}
   */
  update(data) {
    return request({
      url: `/${moduleName}/update`,
      method: "put",
      data: data
    })
  },


  /**
   * 详情查询
   * @param dictId
   * @returns {AxiosPromise}
   */
  getById(dictId) {
    return request({
      url: `/${moduleName}/get/` + dictId,
      method: "get"
    })
  },


  /**
   * 删除
   * @param dictId
   * @returns {AxiosPromise}
   */
  delete(dictId) {
    return request({
      url: `/${moduleName}/delete/` + dictId,
      method: "delete"
    })
  },


  /**
   * 刷新缓存
   * @returns {AxiosPromise}
   */
  refreshCache(){
     return request({
       url:`/${moduleName}/refreshCache`,
       method:"get"
     })
  }


}
