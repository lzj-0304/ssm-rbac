package com.powernode.service;

import com.powernode.domain.SysDictData;

import java.util.List;

/**
 * @Author: Mr.T
 * @Date: 2023/3/9
 */
public interface CacheService {

    public void loadDictTypeData();

    public List<SysDictData> getDictDataByDictType(String dictType);


}
