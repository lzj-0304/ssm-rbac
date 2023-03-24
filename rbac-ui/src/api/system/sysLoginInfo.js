import request from '@/utils/request'

const groupName = 'sysLoginInfo'
export default {
  /**
   * 分页
   */
  getByPage(page) {
    return request({
      url: `/${groupName}/getByPage`,
      method: 'post',
      data: page
    })
  },
  /**
   * 删除
   */
  deleteById(id) {
    return request({
      url: `/${groupName}/delete/${id}`,
      method: 'delete'
    })
  },
  /**
   * 批量删除
   */
  batchDelete(ids) {
    return request({
      url: `/${groupName}/batchDelete/${ids}`,
      method: 'delete'
    })
  },
  /**
   * 清空所有
   */
  cleanLogininfor() {
    return request({
      url: `/${groupName}/cleanLogininfor`,
      method: 'delete'
    })
  }

}
