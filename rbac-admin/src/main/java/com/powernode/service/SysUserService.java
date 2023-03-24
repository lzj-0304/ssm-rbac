package com.powernode.service;

import com.powernode.common.Page;
import com.powernode.domain.SysUser;

import java.util.List;

/**
 * @Author: Mr.T
 * @Date: 2023/3/7
 */
public interface SysUserService {


    int deleteByPrimaryKey(Long userId);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    SysUser login(String username, String password);

    List<String> queryUserHasPermissions(Long userId);

    Page getByPage(Page page);

    SysUser queryUserByUserName(String userName);
}
