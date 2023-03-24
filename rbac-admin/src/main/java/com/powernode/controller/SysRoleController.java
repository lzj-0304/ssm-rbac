package com.powernode.controller;

import com.powernode.common.Page;
import com.powernode.common.Result;
import com.powernode.domain.SysDictData;
import com.powernode.domain.SysRole;
import com.powernode.enums.ResultEnums;
import com.powernode.enums.StateEnums;
import com.powernode.service.SysDictDataService;
import com.powernode.service.SysRoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.management.relation.Role;
import java.util.Date;
import java.util.List;

/**
 * @Author: Mr.T
 * @Date: 2023/3/9
 */
@RestController
@RequestMapping("sysRole")
public class SysRoleController extends BaseController {

    @Resource
    private SysRoleService sysRoleService;


    /**
     * 分页查询数据列表
     *
     * @param page
     * @return
     */
    @PostMapping("/getByPage")
    public Result<Page<SysRole>> getByPage(@RequestBody Page page) {
        page = sysRoleService.getByPage(page);
        return new Result(ResultEnums.SUCCESS.getCode(), "记录查询成功", page);
    }


    /**
     * 添加数据
     *
     * @param sysRole
     * @return
     */
    @PostMapping("save")
    public Result save(@RequestBody SysRole sysRole) {
        sysRole.setCreateBy(getUserName());
        sysRole.setCreateTime(new Date());
        sysRole.setDelFlag(StateEnums.NOT_DELETED.getCode());
        sysRoleService.insert(sysRole);
        return new Result(ResultEnums.SUCCESS, "记录添加成功");
    }


    /**
     * 更新数据
     *
     * @param sysRole
     * @return
     */
    @PutMapping("update")
    public Result update(@RequestBody SysRole sysRole) {
        sysRole.setUpdateBy(getUserName());
        sysRole.setUpdateTime(new Date());
        sysRoleService.updateByPrimaryKeySelective(sysRole);
        return new Result(ResultEnums.SUCCESS, "记录更新成功");
    }


    /**
     * 根据主键查询记录
     *
     * @param roleId
     * @return
     */
    @GetMapping("get/{roleId}")
    public Result<SysRole> getById(@PathVariable Long roleId) {
        return new Result<SysRole>(ResultEnums.SUCCESS.getCode(), "记录查询成功", sysRoleService.selectByPrimaryKey(roleId));
    }


    /**
     * 删除记录
     *
     * @param roleId
     * @return
     */
    @DeleteMapping("/delete/{roleId}")
    public Result delete(@PathVariable Long roleId) {
        SysRole sysRole = sysRoleService.selectByPrimaryKey(roleId);
        sysRole.setDelFlag(StateEnums.DELETED.getCode());
        sysRoleService.updateByPrimaryKeySelective(sysRole);
        return new Result(ResultEnums.SUCCESS, "记录删除成功");
    }


    /**
     * 保存角色菜单记录
     * @param roleId
     * @param menuIds
     * @return
     */
    @PostMapping("saveRoleMenu")
    public Result saveRoleMenus(Long roleId,Long[] menuIds){
        sysRoleService.saveRoleMenus(roleId,menuIds);
        return new Result(ResultEnums.SUCCESS, "菜单权限分配成功");
    }


    /**
     * 查询角色拥有的菜单id
     * @param roleId
     * @return
     */
    @GetMapping("getCurrentRoleHasMenuIdsByRoleId/{roleId}")
    public Result getCurrentRoleHasMenuIdsByRoleId(@PathVariable  Long roleId){
       List menuIds=  sysRoleService.getCurrentRoleHasMenuIdsByRoleId(roleId);
       return new Result(ResultEnums.SUCCESS.getCode(),"记录查询成功",menuIds);
    }



    @GetMapping("getAllRoles")
    public Result<List<SysRole>>  getAllRoles(){
        List<SysRole> sysRoles= sysRoleService.getAllRoles();
        return new Result<>(ResultEnums.SUCCESS.getCode(),"记录查询成功",sysRoles);
    }



    @PostMapping("saveUserRoles")
    public Result saveUserRoles(Long userId,Long[] roleIds){
        sysRoleService.saveUserRoles(userId,roleIds);
        return new Result(ResultEnums.SUCCESS, "用户角色分配成功");
    }


    @GetMapping("/getRoleIdsByUserId/{userId}")
    public Result<List<Long>> getRoleIdsByUserId(@PathVariable Long userId){
       List<Long> roleIds =  sysRoleService.getRoleIdsByUserId(userId);
        return new Result<>(ResultEnums.SUCCESS.getCode(),"记录查询成功",roleIds);
    }


}
