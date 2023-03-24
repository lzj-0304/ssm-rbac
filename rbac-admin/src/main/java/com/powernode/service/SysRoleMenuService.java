package com.powernode.service;

import com.powernode.domain.SysRoleMenu;
    /**
 * @Author: Mr.T
 * @Date:   2023/3/13
 */
public interface SysRoleMenuService{


    int deleteByPrimaryKey(Long roleId,Long menuId);

    int insert(SysRoleMenu record);

    int insertSelective(SysRoleMenu record);

}
