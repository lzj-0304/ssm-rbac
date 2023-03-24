package com.powernode.mapper;

import com.powernode.domain.SysDept;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Mr.T
 * @Date:   2023/3/10
 */
public interface SysDeptMapper {
    int deleteByPrimaryKey(Long deptId);

    int insert(SysDept record);

    int insertSelective(SysDept record);

    SysDept selectByPrimaryKey(Long deptId);

    int updateByPrimaryKeySelective(SysDept record);

    int updateByPrimaryKey(SysDept record);


    List<SysDept> listDept(SysDept sysDept);

    Long countChildrenByParentId(@Param("deptId") Long deptId);
}