package com.powernode.controller;

import com.powernode.common.Result;
import com.powernode.domain.SysMenu;
import com.powernode.enums.ResultEnums;
import com.powernode.enums.StateEnums;
import com.powernode.service.SysMenuService;
import com.powernode.vo.RouterVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author: Mr.T
 * @Date: 2023/3/11
 */
@RestController
@RequestMapping("sysMenu")
public class SysMenuController extends BaseController {
    @Resource
    private SysMenuService sysMenuService;


    /**
     * 查询所有菜单记录
     *
     * @param sysMenu
     * @return
     */
    @GetMapping("listMenu")
    public Result<List<SysMenu>> listMenu(SysMenu sysMenu) {
        List<SysMenu> sysMenuList = sysMenuService.queryAllMenuList(sysMenu, getCurrentUser());
        return new Result<>(ResultEnums.SUCCESS.getCode(), "记录查询成功", sysMenuList);
    }


    /**
     * 菜单添加
     *
     * @param sysMenu
     * @return
     */
    @PostMapping("save")
    public Result save(@RequestBody SysMenu sysMenu) {
        sysMenu.setCreateBy(getUserName());
        sysMenu.setCreateTime(new Date());
        sysMenuService.insertSelective(sysMenu);
        return new Result(ResultEnums.SUCCESS.getCode(), "记录添加成功");
    }


    /**
     * 更新菜单
     *
     * @param sysMenu
     * @return
     */
    @PutMapping("update")
    public Result update(@RequestBody SysMenu sysMenu) {
        sysMenu.setUpdateBy(getUserName());
        sysMenu.setUpdateTime(new Date());
        sysMenuService.updateByPrimaryKeySelective(sysMenu);
        return new Result(ResultEnums.SUCCESS.getCode(), "记录更新成功");
    }


    @GetMapping("/get/{menuId}")
    public Result<SysMenu> getById(@PathVariable Long menuId) {
        SysMenu sysMenu = sysMenuService.selectByPrimaryKey(menuId);
        return new Result<>(ResultEnums.SUCCESS.getCode(), "记录查询成功", sysMenu);
    }


    @DeleteMapping("delete/{menuId}")
    public Result delete(@PathVariable Long menuId) {
        Long count = sysMenuService.countChildrenByParentId(menuId);
        if (count > 0) {
            return new Result<>(ResultEnums.ERROR.getCode(), "当前菜单存在子菜单，暂不允许删除!");
        }

        // 真实项目 记录删除 执行的时字段更新!!!!
        sysMenuService.deleteByPrimaryKey(menuId);
        return new Result<>(ResultEnums.SUCCESS.getCode(), "记录删除成功");
    }


    /**
     * 只查询菜单与目录 (menuType  0&1)
     * @return
     */
    @GetMapping("listMenuMC")
    public Result<List<SysMenu>> listMenuMC(){
       List<SysMenu> sysMenuList= sysMenuService.listMenuMC(getCurrentUser());
        return new Result<>(ResultEnums.SUCCESS.getCode(), "记录查询成功", sysMenuList);
    }



    @GetMapping("getRouters")
    public Result<List<RouterVo>> getRouters(){
        List<RouterVo> routerVos = sysMenuService.getRouters(getCurrentUser());
        return new Result<>(ResultEnums.SUCCESS.getCode(), "记录查询成功", routerVos);
    }


}
