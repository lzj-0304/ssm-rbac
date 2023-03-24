package com.powernode.service;

import com.powernode.domain.SysMenu;
import com.powernode.domain.SysUser;
import com.powernode.vo.RouterVo;

import java.util.List;

/**
 * @Author: Mr.T
 * @Date:   2023/3/11
 */
public interface SysMenuService{


    int deleteByPrimaryKey(Long menuId);

    int insert(SysMenu record);

    int insertSelective(SysMenu record);

    SysMenu selectByPrimaryKey(Long menuId);

    int updateByPrimaryKeySelective(SysMenu record);

    int updateByPrimaryKey(SysMenu record);

        List<SysMenu> queryAllMenuList(SysMenu sysMenu, SysUser currentUser);

    Long countChildrenByParentId(Long menuId);

    List<SysMenu> listMenuMC(SysUser user);

    List<RouterVo> getRouters(SysUser currentUser);
}
