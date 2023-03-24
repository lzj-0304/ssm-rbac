package com.powernode.controller;

import com.powernode.common.Page;
import com.powernode.common.Result;
import com.powernode.domain.SysNotice;
import com.powernode.enums.ResultEnums;
import com.powernode.enums.StateEnums;
import com.powernode.service.SysNoticeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author: Mr.T
 * @Date: 2023/3/17
 */
@RestController
@RequestMapping("sysNotice")
public class SysNoticeController extends BaseController{

    
    @Resource
    private SysNoticeService sysNoticeService;

    /**
     * 分页查询数据列表
     *
     * @param page
     * @return
     */
    @PostMapping("/getPage")
    public Result<Page<SysNotice>> getPage(@RequestBody Page page) {
        page = sysNoticeService.getByPage(page);
        return new Result(ResultEnums.SUCCESS.getCode(), "记录查询成功", page);
    }


    /**
     * 添加数据
     *
     * @param sysNotice
     * @return
     */
    @PostMapping("save")
    public Result save(@RequestBody SysNotice sysNotice) {
        sysNotice.setCreateBy(getUserName());
        sysNotice.setCreateTime(new Date());
        sysNoticeService.insert(sysNotice);
        return new Result(ResultEnums.SUCCESS, "记录添加成功");
    }


    /**
     * 更新数据
     *
     * @param sysNotice
     * @return
     */
    @PutMapping("update")
    public Result update(@RequestBody SysNotice sysNotice) {
        sysNotice.setUpdateBy(getUserName());
        sysNotice.setUpdateTime(new Date());
        sysNoticeService.updateByPrimaryKeySelective(sysNotice);
        return new Result(ResultEnums.SUCCESS, "记录更新成功");
    }


    /**
     * 根据主键查询记录
     *
     * @param noticeId
     * @return
     */
    @GetMapping("get/{noticeId}")
    public Result<SysNotice> getById(@PathVariable Long noticeId) {
        return new Result<SysNotice>(ResultEnums.SUCCESS.getCode(), "记录查询成功", sysNoticeService.selectByPrimaryKey(noticeId));
    }


    /**
     * 删除记录
     *
     * @param noticeId
     * @return
     */
    @DeleteMapping("/delete/{noticeId}")
    public Result delete(@PathVariable Long noticeId) {
        sysNoticeService.deleteByPrimaryKey(noticeId);
        return new Result(ResultEnums.SUCCESS, "记录删除成功");
    }


}
