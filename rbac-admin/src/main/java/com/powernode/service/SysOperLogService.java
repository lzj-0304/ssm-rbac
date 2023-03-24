package com.powernode.service;

import com.powernode.common.Page;
import com.powernode.domain.SysOperLog;

/**
 */
public interface SysOperLogService {


    int deleteByPrimaryKey(Long operId);

    int insert(SysOperLog record);

    int insertSelective(SysOperLog record);

    SysOperLog selectByPrimaryKey(Long operId);

    int updateByPrimaryKeySelective(SysOperLog record);

    int updateByPrimaryKey(SysOperLog record);

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    Page<SysOperLog> getByPage(Page<SysOperLog> page);

    /**
     * 批量删除
     * @param ids
     */
    void deleteByPrimaryKeys(Long[] ids);

    /**
     * 清空操作日志
     */
    void deleteAllOperLog();
}
