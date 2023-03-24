package com.powernode.service.impl;

import com.powernode.common.Page;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.powernode.domain.SysOperLog;
import com.powernode.mapper.SysOperLogMapper;
import com.powernode.service.SysOperLogService;

import java.util.List;

@Service
public class SysOperLogServiceImpl extends BaseService implements SysOperLogService{

    @Resource
    private SysOperLogMapper sysOperLogMapper;

    @Override
    public int deleteByPrimaryKey(Long operId) {
        return sysOperLogMapper.deleteByPrimaryKey(operId);
    }

    @Override
    public int insert(SysOperLog record) {
        return sysOperLogMapper.insert(record);
    }

    @Override
    public int insertSelective(SysOperLog record) {
        return sysOperLogMapper.insertSelective(record);
    }

    @Override
    public SysOperLog selectByPrimaryKey(Long operId) {
        return sysOperLogMapper.selectByPrimaryKey(operId);
    }

    @Override
    public int updateByPrimaryKeySelective(SysOperLog record) {
        return sysOperLogMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SysOperLog record) {
        return sysOperLogMapper.updateByPrimaryKey(record);
    }

    @Override
    public Page<SysOperLog> getByPage(Page<SysOperLog> page) {
        com.github.pagehelper.Page<SysOperLog> sysLoginInfoPage=startPage(page.getPageNumber(),page.getPageSize());
        List<SysOperLog> sysOperLogs=this.sysOperLogMapper.queryAllOperLog(page.getParams());
        page.setTotalCount(sysLoginInfoPage.getTotal());
        page.setList(sysLoginInfoPage.getResult());
        return page;
    }

    @Override
    public void deleteByPrimaryKeys(Long[] ids) {
        this.sysOperLogMapper.deleteByPrimaryKeys(ids);
    }

    @Override
    public void deleteAllOperLog() {
        this.sysOperLogMapper.deleteAllOperLog();
    }

}