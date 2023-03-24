package com.powernode.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.powernode.mapper.SysDeptMapper;
import com.powernode.domain.SysDept;
import com.powernode.service.SysDeptService;

import java.util.List;

/**
 * @Author: Mr.T
 * @Date:   2023/3/10
 */
@Service
public class SysDeptServiceImpl implements SysDeptService{

    @Resource
    private SysDeptMapper sysDeptMapper;

    @Override
    public int deleteByPrimaryKey(Long deptId) {
        return sysDeptMapper.deleteByPrimaryKey(deptId);
    }

    @Override
    public int insert(SysDept record) {
        return sysDeptMapper.insert(record);
    }

    @Override
    public int insertSelective(SysDept record) {
        return sysDeptMapper.insertSelective(record);
    }

    @Override
    public SysDept selectByPrimaryKey(Long deptId) {
        return sysDeptMapper.selectByPrimaryKey(deptId);
    }

    @Override
    public int updateByPrimaryKeySelective(SysDept record) {
        return sysDeptMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SysDept record) {
        return sysDeptMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<SysDept> listDept(SysDept sysDept) {
        return sysDeptMapper.listDept(sysDept);
    }

    @Override
    public Long countChildrenByParentId(Long deptId) {
        return sysDeptMapper.countChildrenByParentId(deptId);
    }

}
