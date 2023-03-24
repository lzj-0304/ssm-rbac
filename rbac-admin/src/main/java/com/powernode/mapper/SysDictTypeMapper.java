package com.powernode.mapper;

import com.powernode.domain.SysDictType;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: Mr.T
 * @Date:   2023/3/7
 */
public interface SysDictTypeMapper {
    int deleteByPrimaryKey(Long dictId);

    int insert(SysDictType record);

    int insertSelective(SysDictType record);

    SysDictType selectByPrimaryKey(Long dictId);

    int updateByPrimaryKeySelective(SysDictType record);

    int updateByPrimaryKey(SysDictType record);


    List<SysDictType> getSysDictTypeByPage(@Param("params") Map params);


    List<SysDictType> getAllDictType(@Param("status") Integer status);
}