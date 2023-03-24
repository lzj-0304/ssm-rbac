package com.powernode.service.impl;

import com.github.pagehelper.PageHelper;
import com.powernode.common.Page;
import com.powernode.enums.StateEnums;
import com.powernode.service.CacheService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.powernode.domain.SysDictType;
import com.powernode.mapper.SysDictTypeMapper;
import com.powernode.service.SysDictTypeService;

import java.util.List;

/**
 * @Author: Mr.T
 * @Date:   2023/3/7
 */
@Service
public class SysDictTypeServiceImpl implements SysDictTypeService{

    @Resource
    private SysDictTypeMapper sysDictTypeMapper;


    @Resource
    private CacheService cacheService;

    @Override
    public int deleteByPrimaryKey(Long dictId) {
        return sysDictTypeMapper.deleteByPrimaryKey(dictId);
    }

    @Override
    public int insert(SysDictType record) {
        return sysDictTypeMapper.insert(record);
    }

    @Override
    public int insertSelective(SysDictType record) {
        return sysDictTypeMapper.insertSelective(record);
    }

    @Override
    public SysDictType selectByPrimaryKey(Long dictId) {
        return sysDictTypeMapper.selectByPrimaryKey(dictId);
    }

    @Override
    public int updateByPrimaryKeySelective(SysDictType record) {
        return sysDictTypeMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SysDictType record) {
        return sysDictTypeMapper.updateByPrimaryKey(record);
    }

    @Override
    public Page getSysDictTypeByPage(Page page) {
        com.github.pagehelper.Page pageHelperPage = PageHelper.startPage(page.getPageNumber(),page.getPageSize());
        sysDictTypeMapper.getSysDictTypeByPage(page.getParams());
        // 设置当前页列表数据
        page.setList(pageHelperPage.getResult());
        // 设置总数&页数
        page.setTotalCount(pageHelperPage.getTotal());
        return page;
    }

    @Override
    public List<SysDictType> getAllDictType() {
        return sysDictTypeMapper.getAllDictType(StateEnums.ENABLED.getCode());
    }

    @Override
    public void refreshCache() {
        /**
         *   引入缓存
         *     字典数据 变化频率较低  每次获取字典数据 没有必要到数据库来查询  可以使用缓存来缓存字典数据
         *        如果客户端需要某个字典类型数据 后端提供一个接口 接收字典类型值 根据字典类型值到缓存查找即可
         *   刷新缓存   实际上就是将数据库字典数据查出来放入缓存
         *        将每个字典类型的字典记录存入一个特殊的数据结构中  后续使用时从这个数据结构取即可
         *           sys_user_sex---->List<SysDictData>
         *           sys_notice_type--->List<SysDictData>
         *           sys_login_status--->List<SysDictData>
         *          这个数据结构选着 Map<String,List<SysDictData>>
         *
         */
        cacheService.loadDictTypeData();
    }

}
