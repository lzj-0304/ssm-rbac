package com.powernode.service;

import com.powernode.domain.SysUserRole;
    /**
 * @Author: Mr.T
 * @Date:   2023/3/14
 */
public interface SysUserRoleService{


    int deleteByPrimaryKey(Long userId,Long roleId);

    int insert(SysUserRole record);

    int insertSelective(SysUserRole record);

}
