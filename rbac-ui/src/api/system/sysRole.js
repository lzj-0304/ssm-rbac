import request from "@/utils/request";


const moduleName = "sysRole"

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




  delete(dictCode) {
    return request({
      url: `/${moduleName}/delete/` + dictCode,
      method: "delete"
    })
  },


  getById(roleId){
    return request({
      url:`/${moduleName}/get/`+roleId,
      method:"get"
    })
  },


  getCurrentRoleHasMenuIdsByRoleId(roleId){
    return request({
      url:`/${moduleName}/getCurrentRoleHasMenuIdsByRoleId/`+roleId,
      method:"get"
    })
  },

  saveRoleMenu(roleId,menuIds){
    let params = "roleId="+roleId
    /**
     * springmvc 接收参数为数组
     *    menuIds=1&menuIds=2&menuIds=100
     */
    menuIds.filter(menuId=>{
      params = params+"&menuIds="+menuId
    })
    console.log(params)
    /**
     * roleId=105&menuIds=1&menuIds=100&menuIds=10001&menuIds=10002
     */
    return request({
      url:`/${moduleName}/saveRoleMenu?`+params,
      method:"post",
    })
  },



  /**
   * 查询所有角色不分页
   */
  getAllRoles() {
    return request({
      url: `/${moduleName}/getAllRoles`,
      method: 'get',
    })
  },
  /**
   * 查询当前用户已拥有的角色IDS
   */
  getRoleIdsByUserId(userId) {
    return request({
      url: `/${moduleName}/getRoleIdsByUserId/`+userId,
      method: 'get',
    })
  },
  /**
   * 保存用户和角色之间的关系
   */
  saveUserRoles(userId,roleIds){
    let params="userId="+userId
    roleIds.filter(roleId=>{
      params+="&roleIds="+roleId
    })
    return request({
      url: `/${moduleName}/saveUserRoles?`+params,
      method: 'post'
    })
  }




}
