package com.powernode.service;

import com.powernode.common.Page;
import com.powernode.domain.SysLoginInfo;

/**

 */
public interface SysLoginInfoService {


    int deleteByPrimaryKey(Long infoId);

    int insert(SysLoginInfo record);

    int insertSelective(SysLoginInfo record);

    SysLoginInfo selectByPrimaryKey(Long infoId);

    int updateByPrimaryKeySelective(SysLoginInfo record);

    int updateByPrimaryKey(SysLoginInfo record);

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    Page<SysLoginInfo> getByPage(Page<SysLoginInfo> page);

    /**
     * 批量删除
     * @param id
     */
    void deleteByPrimaryKeys(Long[] id);

    /**
     * 清空登陆日志
     */
    void deleteAllLoginInfo();
}