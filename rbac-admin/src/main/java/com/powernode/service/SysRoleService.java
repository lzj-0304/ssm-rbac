package com.powernode.service;

import com.powernode.common.Page;
import com.powernode.domain.SysRole;

import java.util.List;

/**
 * @Author: Mr.T
 * @Date:   2023/3/13
 */
public interface SysRoleService{


    int deleteByPrimaryKey(Long roleId);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Long roleId);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

        Page getByPage(Page page);

        void saveRoleMenus(Long roleId, Long[] menuIds);

        List getCurrentRoleHasMenuIdsByRoleId(Long roleId);

    List<SysRole> getAllRoles();

    void saveUserRoles(Long userId, Long[] roleIds);

    List<Long> getRoleIdsByUserId(Long userId);
}
