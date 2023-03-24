package com.powernode.mapper;

import com.powernode.common.Page;
import com.powernode.domain.SysDictData;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: Mr.T
 * @Date:   2023/3/9
 */
public interface SysDictDataMapper {
    int deleteByPrimaryKey(Long dictCode);

    int insert(SysDictData record);

    int insertSelective(SysDictData record);

    SysDictData selectByPrimaryKey(Long dictCode);

    int updateByPrimaryKeySelective(SysDictData record);

    int updateByPrimaryKey(SysDictData record);


    List<SysDictData> getByPage(@Param("params") Map<String,Object> params);

    List<SysDictData> getDictDataByDictType(@Param("dictType") String dictType);
}