package com.powernode.service.impl;

import com.powernode.common.Page;
import com.powernode.domain.SysRoleMenu;
import com.powernode.domain.SysUserRole;
import com.powernode.mapper.SysRoleMenuMapper;
import com.powernode.mapper.SysUserRoleMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import com.powernode.mapper.SysRoleMapper;
import com.powernode.domain.SysRole;
import com.powernode.service.SysRoleService;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Mr.T
 * @Date: 2023/3/13
 */
@Service
public class SysRoleServiceImpl extends BaseService implements SysRoleService {

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public int deleteByPrimaryKey(Long roleId) {
        return sysRoleMapper.deleteByPrimaryKey(roleId);
    }

    @Override
    public int insert(SysRole record) {
        return sysRoleMapper.insert(record);
    }

    @Override
    public int insertSelective(SysRole record) {
        return sysRoleMapper.insertSelective(record);
    }

    @Override
    public SysRole selectByPrimaryKey(Long roleId) {
        return sysRoleMapper.selectByPrimaryKey(roleId);
    }

    @Override
    public int updateByPrimaryKeySelective(SysRole record) {
        return sysRoleMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SysRole record) {
        return sysRoleMapper.updateByPrimaryKey(record);
    }

    @Override
    public Page getByPage(Page page) {
        com.github.pagehelper.Page pageHelperPage = startPage(page.getPageNumber(), page.getPageSize());
        sysRoleMapper.getByPage(page.getParams());
        page.setList(pageHelperPage.getResult());
        page.setTotalCount(pageHelperPage.getTotal());
        return page;
    }


    @Override
    public void saveRoleMenus(Long roleId, Long[] menuIds) {
        /**
         * 先删除角色原始 权限记录(角色菜单记录)
         * 批量添加 新的角色菜单记录
         */
        Long count = sysRoleMenuMapper.countRoleMenusByRoleId(roleId);
        if (count > 0) {
            sysRoleMenuMapper.deleteRoleMenusByRoleId(roleId);
        }


        /*for (Long menuId : menuIds) {
            SysRoleMenu sysRoleMenu=new SysRoleMenu();
            sysRoleMenu.setRoleId(roleId);
            sysRoleMenu.setMenuId(menuId);
            sysRoleMenuMapper.insert(sysRoleMenu);
        }*/


        List<SysRoleMenu> sysRoleMenuList = new ArrayList<SysRoleMenu>();
        for (Long menuId : menuIds) {
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setRoleId(roleId);
            sysRoleMenu.setMenuId(menuId);
            sysRoleMenuList.add(sysRoleMenu);
        }
        sysRoleMenuMapper.saveSysRoleMenusBatch(sysRoleMenuList);
    }

    @Override
    public List getCurrentRoleHasMenuIdsByRoleId(Long roleId) {
        return sysRoleMenuMapper.getCurrentRoleHasMenuIdsByRoleId(roleId);
    }

    @Override
    public List<SysRole> getAllRoles() {
        return sysRoleMapper.getAllRoles();
    }

    @Override
    public void saveUserRoles(Long userId, Long[] roleIds) {
        Long count = sysUserRoleMapper.countUserRolesByUserId(userId);
        if (count > 0) {
            sysUserRoleMapper.deleteUserRolesByUserId(userId);
        }

        List<SysUserRole> sysUserRoles = new ArrayList<>();
        for (Long roleId : roleIds) {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setRoleId(roleId);
            sysUserRole.setUserId(userId);
            sysUserRoles.add(sysUserRole);
        }
        sysUserRoleMapper.saveUserRolesBatch(sysUserRoles);
    }

    @Override
    public List<Long> getRoleIdsByUserId(Long userId) {
        return sysUserRoleMapper.getRoleIdsByUserId(userId);
    }

}
