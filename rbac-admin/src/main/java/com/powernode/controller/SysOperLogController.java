package com.powernode.controller;

import com.powernode.annotation.Log;
import com.powernode.common.Page;
import com.powernode.common.Result;
import com.powernode.domain.SysOperLog;
import com.powernode.enums.BusinessType;
import com.powernode.enums.ResultEnums;
import com.powernode.service.SysOperLogService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 操作日志管理控制器
 */
@RestController
@RequestMapping("/sysOperLog")
public class SysOperLogController extends BaseController {
    @Resource
    private SysOperLogService sysOperLogService;
    /**
     * 分页查询
     * @param page
     * @return
     */
    @RequestMapping(value = "/getByPage", method = RequestMethod.POST)
    public Result<Page<SysOperLog>> getByPage(@RequestBody Page<SysOperLog> page) {
        page = sysOperLogService.getByPage(page);
        return new Result<>(page);
    }
    /**
     * 根据id删除
     * @param id
     * @return
     */
    @Log(title = "操作日志管理", businessType = BusinessType.DELETE)
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public Result<?> delete(@PathVariable Long id) {
        sysOperLogService.deleteByPrimaryKey(id);
        return new Result<>("删除成功");
    }
    /**
     * 批量删除
     * @param ids
     * @return
     */
    @Log(title = "操作日志管理-批量删除", businessType = BusinessType.DELETE)
    @RequestMapping(value = "/batchDelete/{ids}", method = RequestMethod.DELETE)
    public Result<?> batchDelete(@PathVariable Long[] ids) {
        if(ids!=null&&ids.length==0){
            return new Result<>(ResultEnums.ERROR);
        }
        sysOperLogService.deleteByPrimaryKeys(ids);
        return new Result<>("删除成功");
    }
    /**
     * 清空操作日志
     * @return
     */
    @Log(title = "操作日志管理", businessType = BusinessType.CLEAN)
    @RequestMapping(value = "/cleanOperLog", method = RequestMethod.DELETE)
    public Result<?> cleanOperLog() {
        sysOperLogService.deleteAllOperLog();
        return new Result<>("删除成功");
    }
}
