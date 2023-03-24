package com.powernode.controller;

import com.powernode.common.Page;
import com.powernode.common.Result;
import com.powernode.domain.SysLoginInfo;
import com.powernode.enums.ResultEnums;
import com.powernode.service.SysLoginInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 */
@RestController
@RequestMapping("/sysLoginInfo")
public class SysLoginLogInfoController extends BaseController {
    @Resource
    private SysLoginInfoService sysLoginInfoService;
    /**
     * 分页查询
     * @param page
     * @return
     */
    @RequestMapping(value = "/getByPage", method = RequestMethod.POST)
    public Result<Page<SysLoginInfo>> getByPage(@RequestBody Page<SysLoginInfo> page) {
        page = sysLoginInfoService.getByPage(page);
        return new Result<>(page);
    }
    /**
     * 根据id删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public Result<?> delete(@PathVariable Long id) {
        sysLoginInfoService.deleteByPrimaryKey(id);
        return new Result<>("删除成功");
    }
    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping(value = "/batchDelete/{ids}", method = RequestMethod.DELETE)
    public Result<?> batchDelete(@PathVariable Long[] ids) {
        if(ids!=null&&ids.length==0){
            return new Result<>(ResultEnums.ERROR);
        }
        sysLoginInfoService.deleteByPrimaryKeys(ids);
        return new Result<>("删除成功");
    }

    /**
     * 清空登陆日志
     * @return
     */
    @RequestMapping(value = "/cleanLogininfor", method = RequestMethod.DELETE)
    public Result<?> cleanLogininfor() {
        sysLoginInfoService.deleteAllLoginInfo();
        return new Result<>("删除成功");
    }

}