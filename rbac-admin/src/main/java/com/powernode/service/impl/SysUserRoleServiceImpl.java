package com.powernode.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.powernode.mapper.SysUserRoleMapper;
import com.powernode.domain.SysUserRole;
import com.powernode.service.SysUserRoleService;
/**
 * @Author: Mr.T
 * @Date:   2023/3/14
 */
@Service
public class SysUserRoleServiceImpl implements SysUserRoleService{

    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public int deleteByPrimaryKey(Long userId,Long roleId) {
        return sysUserRoleMapper.deleteByPrimaryKey(userId,roleId);
    }

    @Override
    public int insert(SysUserRole record) {
        return sysUserRoleMapper.insert(record);
    }

    @Override
    public int insertSelective(SysUserRole record) {
        return sysUserRoleMapper.insertSelective(record);
    }

}
