package com.powernode.service.impl;

import com.github.pagehelper.PageHelper;
import com.powernode.common.Page;
import com.powernode.service.CacheService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.powernode.domain.SysDictData;
import com.powernode.mapper.SysDictDataMapper;
import com.powernode.service.SysDictDataService;

import java.util.List;

/**
 * @Author: Mr.T
 * @Date:   2023/3/9
 */
@Service
public class SysDictDataServiceImpl extends BaseService implements SysDictDataService{

    @Resource
    private SysDictDataMapper sysDictDataMapper;


    @Resource
    private CacheService cacheService;

    @Override
    public int deleteByPrimaryKey(Long dictCode) {
        return sysDictDataMapper.deleteByPrimaryKey(dictCode);
    }

    @Override
    public int insert(SysDictData record) {
        return sysDictDataMapper.insert(record);
    }

    @Override
    public int insertSelective(SysDictData record) {
        return sysDictDataMapper.insertSelective(record);
    }

    @Override
    public SysDictData selectByPrimaryKey(Long dictCode) {
        return sysDictDataMapper.selectByPrimaryKey(dictCode);
    }

    @Override
    public int updateByPrimaryKeySelective(SysDictData record) {
        return sysDictDataMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SysDictData record) {
        return sysDictDataMapper.updateByPrimaryKey(record);
    }

    @Override
    public Page getByPage(Page page) {
        com.github.pagehelper.Page pageHelperPage = startPage(page.getPageNumber(),page.getPageSize());
        sysDictDataMapper.getByPage(page.getParams());
        page.setList(pageHelperPage.getResult());
        page.setTotalCount(pageHelperPage.getTotal());
        return page;
    }

    @Override
    public List<SysDictData> getDictDataByDictType(String dictType) {
       return  cacheService.getDictDataByDictType(dictType);
    }

}
