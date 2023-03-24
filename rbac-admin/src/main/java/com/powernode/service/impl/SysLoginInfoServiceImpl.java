package com.powernode.service.impl;

import com.powernode.common.Page;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.powernode.domain.SysLoginInfo;
import com.powernode.mapper.SysLoginInfoMapper;
import com.powernode.service.SysLoginInfoService;

import java.util.List;

/**
 * 作者：雷哥
 * 时间：2022/5/30
 * 邮箱: 78414842@qq.com
 */
@Service
public class SysLoginInfoServiceImpl extends BaseService implements SysLoginInfoService{

    @Resource
    private SysLoginInfoMapper sysLoginInfoMapper;

    @Override
    public int deleteByPrimaryKey(Long infoId) {
        return sysLoginInfoMapper.deleteByPrimaryKey(infoId);
    }

    @Override
    public int insert(SysLoginInfo record) {
        return sysLoginInfoMapper.insert(record);
    }

    @Override
    public int insertSelective(SysLoginInfo record) {
        return sysLoginInfoMapper.insertSelective(record);
    }

    @Override
    public SysLoginInfo selectByPrimaryKey(Long infoId) {
        return sysLoginInfoMapper.selectByPrimaryKey(infoId);
    }

    @Override
    public int updateByPrimaryKeySelective(SysLoginInfo record) {
        return sysLoginInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SysLoginInfo record) {
        return sysLoginInfoMapper.updateByPrimaryKey(record);
    }

    /**
     * 分页查询
     * @param page
     * @return
     */
    @Override
    public Page<SysLoginInfo> getByPage(Page<SysLoginInfo> page) {
        com.github.pagehelper.Page<SysLoginInfo> sysLoginInfoPage=startPage(page.getPageNumber(),page.getPageSize());
        List<SysLoginInfo> sysLoginInfos=this.sysLoginInfoMapper.queryAllSysLoginInfo(page.getParams());
        page.setTotalCount(sysLoginInfoPage.getTotal());
        page.setList(sysLoginInfoPage.getResult());
        return page;
    }

    /**
     * 批量删除
     * @param id
     */
    @Override
    public void deleteByPrimaryKeys(Long[] id) {
        this.sysLoginInfoMapper.deleteByPrimaryKeys(id);
    }

    /**
     * 清空登陆日志
     */
    @Override
    public void deleteAllLoginInfo() {
        this.sysLoginInfoMapper.deleteAllLoginInfo();
    }

}