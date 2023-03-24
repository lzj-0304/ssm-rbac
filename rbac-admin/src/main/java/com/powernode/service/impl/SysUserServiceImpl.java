package com.powernode.service.impl;

import com.powernode.common.Page;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.powernode.domain.SysUser;
import com.powernode.mapper.SysUserMapper;
import com.powernode.service.SysUserService;

import java.util.List;

/**
 * @Author: Mr.T
 * @Date:   2023/3/7
 */
@Service
public class SysUserServiceImpl extends BaseService implements SysUserService{

    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public int deleteByPrimaryKey(Long userId) {
        return sysUserMapper.deleteByPrimaryKey(userId);
    }

    @Override
    public int insert(SysUser record) {
        return sysUserMapper.insert(record);
    }

    @Override
    public int insertSelective(SysUser record) {
        return sysUserMapper.insertSelective(record);
    }

    @Override
    public SysUser selectByPrimaryKey(Long userId) {
        return sysUserMapper.selectByPrimaryKey(userId);
    }

    @Override
    public int updateByPrimaryKeySelective(SysUser record) {
        return sysUserMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SysUser record) {
        return sysUserMapper.updateByPrimaryKey(record);
    }

    @Override
    public SysUser login(String username, String password) {
        return sysUserMapper.login(username,password);
    }

    @Override
    public List<String> queryUserHasPermissions(Long userId) {
        return sysUserMapper.queryUserHasPermissions(userId);
    }

    @Override
    public Page getByPage(Page page) {
        com.github.pagehelper.Page pageHelperPage = startPage(page.getPageNumber(),page.getPageSize());
        sysUserMapper.getByPage(page.getParams());
        page.setList(pageHelperPage.getResult());
        page.setTotalCount(pageHelperPage.getTotal());
        return  page;
    }

    @Override
    public SysUser queryUserByUserName(String userName) {
        return sysUserMapper.queryUserByUserName(userName);
    }

}
