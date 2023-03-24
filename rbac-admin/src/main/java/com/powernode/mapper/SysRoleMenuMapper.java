package com.powernode.mapper;

import com.powernode.domain.SysRoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Mr.T
 * @Date:   2023/3/13
 */
public interface SysRoleMenuMapper {
    int deleteByPrimaryKey(@Param("roleId") Long roleId, @Param("menuId") Long menuId);

    int insert(SysRoleMenu record);

    int insertSelective(SysRoleMenu record);


    Long countRoleMenusByRoleId(@Param("roleId") Long roleId);

    int deleteRoleMenusByRoleId(@Param("roleId") Long roleId);

    int saveSysRoleMenusBatch(List<SysRoleMenu> sysRoleMenuList);


    List<Long> getCurrentRoleHasMenuIdsByRoleId(@Param("roleId") Long roleId);
}