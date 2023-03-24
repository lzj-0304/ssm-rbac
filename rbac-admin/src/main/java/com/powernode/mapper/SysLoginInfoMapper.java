package com.powernode.mapper;

import com.powernode.domain.SysLoginInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: Mr.T
 * @Date:   2023/3/17
 */
public interface SysLoginInfoMapper {
    int deleteByPrimaryKey(Long infoId);

    int insert(SysLoginInfo record);

    int insertSelective(SysLoginInfo record);

    SysLoginInfo selectByPrimaryKey(Long infoId);

    int updateByPrimaryKeySelective(SysLoginInfo record);

    int updateByPrimaryKey(SysLoginInfo record);


    /**
     * 查询登陆日志
     *
     * @param params
     * @return
     */
    List<SysLoginInfo> queryAllSysLoginInfo(@Param("params") Map<String, Object> params);

    /**
     * 批量删除
     *
     * @param ids
     */
    void deleteByPrimaryKeys(@Param("ids") Long[] ids);

    /**
     * 清空登陆日志
     */
    void deleteAllLoginInfo();
}