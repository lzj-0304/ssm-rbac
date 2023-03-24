package com.powernode.service.impl;

import com.powernode.common.Page;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.powernode.domain.SysNotice;
import com.powernode.mapper.SysNoticeMapper;
import com.powernode.service.SysNoticeService;
/**
 * @Author: Mr.T
 * @Date:   2023/3/17
 */
@Service
public class SysNoticeServiceImpl extends BaseService implements SysNoticeService{

    @Resource
    private SysNoticeMapper sysNoticeMapper;

    @Override
    public int deleteByPrimaryKey(Long noticeId) {
        return sysNoticeMapper.deleteByPrimaryKey(noticeId);
    }

    @Override
    public int insert(SysNotice record) {
        return sysNoticeMapper.insert(record);
    }

    @Override
    public int insertSelective(SysNotice record) {
        return sysNoticeMapper.insertSelective(record);
    }

    @Override
    public SysNotice selectByPrimaryKey(Long noticeId) {
        return sysNoticeMapper.selectByPrimaryKey(noticeId);
    }

    @Override
    public int updateByPrimaryKeySelective(SysNotice record) {
        return sysNoticeMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SysNotice record) {
        return sysNoticeMapper.updateByPrimaryKey(record);
    }

    @Override
    public Page getByPage(Page page) {
        com.github.pagehelper.Page pageHelperPage = startPage(page.getPageNumber(), page.getPageSize());
        sysNoticeMapper.getByPage(page.getParams());
        page.setList(pageHelperPage.getResult());
        page.setTotalCount(pageHelperPage.getTotal());
        return page;
    }

}
