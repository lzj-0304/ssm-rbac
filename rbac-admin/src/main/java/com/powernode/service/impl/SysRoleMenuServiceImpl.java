package com.powernode.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.powernode.mapper.SysRoleMenuMapper;
import com.powernode.domain.SysRoleMenu;
import com.powernode.service.SysRoleMenuService;
/**
 * @Author: Mr.T
 * @Date:   2023/3/13
 */
@Service
public class SysRoleMenuServiceImpl implements SysRoleMenuService{

    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public int deleteByPrimaryKey(Long roleId,Long menuId) {
        return sysRoleMenuMapper.deleteByPrimaryKey(roleId,menuId);
    }

    @Override
    public int insert(SysRoleMenu record) {
        return sysRoleMenuMapper.insert(record);
    }

    @Override
    public int insertSelective(SysRoleMenu record) {
        return sysRoleMenuMapper.insertSelective(record);
    }

}
