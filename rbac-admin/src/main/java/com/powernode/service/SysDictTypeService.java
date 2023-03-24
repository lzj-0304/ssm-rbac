package com.powernode.service;

import com.powernode.common.Page;
import com.powernode.domain.SysDictType;

import java.util.List;

/**
 * @Author: Mr.T
 * @Date: 2023/3/7
 */
public interface SysDictTypeService {


    int deleteByPrimaryKey(Long dictId);

    int insert(SysDictType record);

    int insertSelective(SysDictType record);

    SysDictType selectByPrimaryKey(Long dictId);

    int updateByPrimaryKeySelective(SysDictType record);

    int updateByPrimaryKey(SysDictType record);

    Page getSysDictTypeByPage(Page page);

    List<SysDictType> getAllDictType();

    void refreshCache();
}
