package com.powernode.mapper;

import com.powernode.domain.SysOperLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: Mr.T
 * @Date:   2023/3/17
 */
public interface SysOperLogMapper {
    int deleteByPrimaryKey(Long operId);

    int insert(SysOperLog record);

    int insertSelective(SysOperLog record);

    SysOperLog selectByPrimaryKey(Long operId);

    int updateByPrimaryKeySelective(SysOperLog record);

    int updateByPrimaryKey(SysOperLog record);


    /**
     * 查询
     * @param params
     * @return
     */
    List<SysOperLog> queryAllOperLog(@Param("params") Map<String, Object> params);

    /**
     * 根据ID集合删除
     * @param ids
     */
    void deleteByPrimaryKeys(@Param("ids") Long[] ids);

    /**
     * 清空
     */
    void deleteAllOperLog();

}