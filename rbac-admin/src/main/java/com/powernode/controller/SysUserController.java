package com.powernode.controller;

import cn.hutool.crypto.digest.DigestUtil;
import com.powernode.annotations.AppPermission;
import com.powernode.common.Page;
import com.powernode.common.Result;
import com.powernode.constants.CoreConstant;
import com.powernode.domain.SysUser;
import com.powernode.enums.BusinessType;
import com.powernode.enums.ResultEnums;
import com.powernode.enums.StateEnums;
import com.powernode.service.SysUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author: Mr.T
 * @Date: 2023/3/7
 */
@RestController
@RequestMapping("/sysUser")

public class SysUserController extends BaseController{

    @Resource
    private SysUserService sysUserService;








    /**
     * 分页查询数据列表
     *
     * @param page
     * @return
     */
    @PostMapping("/getByPage")
    @AppPermission(value = "system:user:query")
    @com.powernode.annotation.Log(title = "用户管理",businessType = BusinessType.QUERY)
    public Result<Page<SysUser>> getByPage(@RequestBody Page page) {
        page = sysUserService.getByPage(page);
        return new Result(ResultEnums.SUCCESS.getCode(), "记录查询成功", page);
    }


    /**
     * 添加数据
     *
     * @param sysUser
     * @return
     */
    @PostMapping("save")
    @AppPermission(value = "system:user:add")
    @com.powernode.annotation.Log(title = "用户管理",businessType = BusinessType.INSERT)
    public Result save(@RequestBody SysUser sysUser) {
        /**
         * 用户名 唯一
         */
        SysUser temp = sysUserService.queryUserByUserName(sysUser.getUserName());
        if(null !=temp){
            return new Result(ResultEnums.ERROR, "用户记录已存在!");
        }
        sysUser.setPassword(DigestUtil.md5Hex(CoreConstant.DEFAULT_PASSWORD));
        sysUser.setCreateBy(getUserName());
        sysUser.setCreateTime(new Date());
        sysUser.setDelFlag(StateEnums.NOT_DELETED.getCode());
        sysUser.setUserType(StateEnums.USER_TYPE_NORMAL.getCode());
        sysUserService.insertSelective(sysUser);
        return new Result(ResultEnums.SUCCESS, "记录添加成功");
    }



    /**
     * 更新数据
     *
     * @param sysUser
     * @return
     */
    @PutMapping("update")
    @AppPermission(value = "system:user:update")
    @com.powernode.annotation.Log(title = "用户管理",businessType = BusinessType.UPDATE)
    public Result update(@RequestBody SysUser sysUser) {
        /**
         * 用户名唯一
         */
        SysUser temp = sysUserService.queryUserByUserName(sysUser.getUserName());
        if(null !=temp && !(temp.getUserId().equals(sysUser.getUserId()))){
            return new Result(ResultEnums.ERROR, "用户记录已存在!");
        }
        sysUser.setUpdateTime(new Date());
        sysUser.setUpdateBy(getUserName());
        sysUserService.updateByPrimaryKeySelective(sysUser);
        return new Result(ResultEnums.SUCCESS, "记录更新成功");
    }

    @GetMapping("get/{userId}")
    @com.powernode.annotation.Log(title = "用户管理",businessType = BusinessType.QUERY)
    public Result<SysUser> getUserByUserId(@PathVariable Long  userId){
        return new  Result<SysUser>(ResultEnums.SUCCESS.getCode(),"记录查询成功",sysUserService.selectByPrimaryKey(userId));
    }


    /**
     * 删除记录
     *
     * @param userId
     * @return
     */
    @DeleteMapping("/delete/{userId}")
    @AppPermission(value = "system:user:remove")
    @com.powernode.annotation.Log(title = "用户管理",businessType = BusinessType.DELETE)
    public Result delete(@PathVariable Long userId) {
        SysUser sysUser = sysUserService.selectByPrimaryKey(userId);
        sysUser.setDelFlag(StateEnums.DELETED.getCode());
        sysUserService.updateByPrimaryKeySelective(sysUser);
        return new Result(ResultEnums.SUCCESS, "记录删除成功");
    }



    @GetMapping("resetPwd/{userId}")
    @com.powernode.annotation.Log(title = "用户管理",businessType = BusinessType.OTHER)
    public Result resetPwd(@PathVariable Long  userId){
        SysUser sysUser=sysUserService.selectByPrimaryKey(userId);
        sysUser.setPassword(DigestUtil.md5Hex(CoreConstant.DEFAULT_PASSWORD));
        sysUserService.updateByPrimaryKeySelective(sysUser);
        return new Result(ResultEnums.SUCCESS, "密码重置成功");
    }














}
