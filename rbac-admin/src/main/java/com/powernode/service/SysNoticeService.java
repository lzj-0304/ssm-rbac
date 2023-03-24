package com.powernode.service;

import com.powernode.common.Page;
import com.powernode.domain.SysNotice;
    /**
 * @Author: Mr.T
 * @Date:   2023/3/17
 */
public interface SysNoticeService{


    int deleteByPrimaryKey(Long noticeId);

    int insert(SysNotice record);

    int insertSelective(SysNotice record);

    SysNotice selectByPrimaryKey(Long noticeId);

    int updateByPrimaryKeySelective(SysNotice record);

    int updateByPrimaryKey(SysNotice record);

        Page getByPage(Page page);
    }
