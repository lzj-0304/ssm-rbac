package com.powernode.service;

import com.powernode.common.Page;
import com.powernode.domain.SysDictData;

import java.util.List;

/**
 * @Author: Mr.T
 * @Date: 2023/3/9
 */
public interface SysDictDataService {


    int deleteByPrimaryKey(Long dictCode);

    int insert(SysDictData record);

    int insertSelective(SysDictData record);

    SysDictData selectByPrimaryKey(Long dictCode);

    int updateByPrimaryKeySelective(SysDictData record);

    int updateByPrimaryKey(SysDictData record);

    Page getByPage(Page page);

    List<SysDictData> getDictDataByDictType(String dictType);
}
