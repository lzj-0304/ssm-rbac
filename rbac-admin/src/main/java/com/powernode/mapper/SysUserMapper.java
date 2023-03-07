package com.powernode.mapper;

import com.powernode.domain.SysUser;

/**
 * @Author: Mr.T
 * @Date:   2023/3/7
 */
public interface SysUserMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);
}