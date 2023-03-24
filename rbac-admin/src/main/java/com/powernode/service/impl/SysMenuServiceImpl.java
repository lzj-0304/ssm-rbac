package com.powernode.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.powernode.constants.CoreConstant;
import com.powernode.domain.SysUser;
import com.powernode.enums.StateEnums;
import com.powernode.vo.RouterVo;
import com.powernode.vo.SysMenuVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.powernode.domain.SysMenu;
import com.powernode.mapper.SysMenuMapper;
import com.powernode.service.SysMenuService;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Mr.T
 * @Date:   2023/3/11
 */
@Service
public class SysMenuServiceImpl implements SysMenuService{

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Override
    public int deleteByPrimaryKey(Long menuId) {
        return sysMenuMapper.deleteByPrimaryKey(menuId);
    }

    @Override
    public int insert(SysMenu record) {
        return sysMenuMapper.insert(record);
    }

    @Override
    public  int insertSelective(SysMenu record) {

        /**
         * 获取菜单类型 & 上级菜单id parentId
         */
        Integer menuType = record.getMenuType();
        Long parentId = record.getParentId();
        int result= 0;
            /**
             * 自旋锁
             */
            while(true){
                try {
                    Long maxId = sysMenuMapper.queryMaxIdByMenuTypeAndParentId(menuType,parentId);
                    if(maxId !=null){
                        // 非空  主键值+1 即可
                        record.setMenuId(maxId+1L);
                        //record.setMenuId(1L);
                    }else{
                        if(menuType.equals(StateEnums.MENU_M.getCode())){
                            // 如果为一级菜单
                            record.setMenuId(1L);
                        }else if(menuType.equals(StateEnums.MENU_C.getCode())){
                            record.setMenuId(Long.parseLong(parentId+"01"));
                        }else if(menuType.equals(StateEnums.MENU_F.getCode())){
                            record.setMenuId(Long.parseLong(parentId+"01"));
                        }
                    }
                    result = sysMenuMapper.insertSelective(record);
                    System.out.println("添加记录测试......");
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        return result;
    }

    @Override
    public SysMenu selectByPrimaryKey(Long menuId) {
        return sysMenuMapper.selectByPrimaryKey(menuId);
    }

    @Override
    public int updateByPrimaryKeySelective(SysMenu record) {
        return sysMenuMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SysMenu record) {
        return sysMenuMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<SysMenu> queryAllMenuList(SysMenu sysMenu, SysUser currentUser) {
        /**
         * 如果用户为管理员 直接查询所有菜单记录
         * 如果用户为普通用户  根据用户扮演的角色查询菜单记录
         */
        List<SysMenu> sysMenuList=null;
        if(currentUser.getUserType().equals(StateEnums.USER_TYPE_ADMIN.getCode())){
            /**
             * 查询所有记录
             */
            sysMenuList=sysMenuMapper.queryAllMenuList(sysMenu);
        }else{
            /**
             * 如果用户为普通用户  根据用户扮演的角色查询菜单记录
             */
            sysMenuList = sysMenuMapper.queryAllMenuListByUserId(sysMenu,currentUser.getUserId());
        }
        return sysMenuList;
    }

    @Override
    public Long countChildrenByParentId(Long menuId) {
        return sysMenuMapper.countChildrenByParentId(menuId);
    }

    @Override
    public List<SysMenu> listMenuMC(SysUser sysUser) {
        List<SysMenu> result=null;
        if(sysUser.getUserType().equals(StateEnums.USER_TYPE_ADMIN.getCode())){
            // 查询所有菜单目录
          result=  sysMenuMapper.queryAllMenuMC();
        }else{
            result = sysMenuMapper.queryAllMenuMCByUserId(sysUser.getUserId());
        }
        return result;
    }





    @Override
    public List<RouterVo> getRouters(SysUser currentUser) {
        /**
         * 1.判断用户类型
         *    如果管理员 查询所有菜单记录
         *    如果普通用户 根据用户扮演的角色查询指定菜单记录
         *  结果   List<SysMenu>
         * 2.根据List<SysMenu>  ---->构建 List<SysMenuVo>
         *      先构建一级菜单(目录)  topMenuList =   系统管理  日志管理
         *      根据顶级菜单获取二级菜单  children
         * 3.根据第二步结果 构建 List<RouterVo>
         */
        // 转换查询结果
        List<SysMenu> allSysMenuList =  this.listMenuMC(currentUser);
        List<SysMenuVo>  sysMenuVoList = new ArrayList<>();
        allSysMenuList.forEach(menu->{
            SysMenuVo sysMenuVo=new SysMenuVo();
            BeanUtils.copyProperties(menu,sysMenuVo);
            sysMenuVoList.add(sysMenuVo);
        });

        // 获取顶级菜单
        List<SysMenuVo> topMenuVoList=new ArrayList<>();
        sysMenuVoList.forEach(sysMenuVo -> {
            if(sysMenuVo.getMenuType().equals(StateEnums.MENU_M.getCode())){
                topMenuVoList.add(sysMenuVo);
            }
        });


        // 获取每个顶级菜单下子菜单
        sysMenuVoList.forEach(sysMenuVo -> {
            topMenuVoList.forEach(topMenuVo->{
                if(sysMenuVo.getParentId().equals(topMenuVo.getMenuId())){
                    topMenuVo.getChildren().add(sysMenuVo);
                }
            });
        });

        return buildRouterVoList(topMenuVoList);
    }



    private List<RouterVo> buildRouterVoList(List<SysMenuVo> topMenuVoList) {
        List<RouterVo> result = new ArrayList<>();
        for (SysMenuVo sysMenuVo : topMenuVoList) {
            RouterVo routerVo=new RouterVo();
            routerVo.setMeta(new RouterVo.MetaVo(sysMenuVo.getMenuName(),sysMenuVo.getIcon()));
            routerVo.setAlwaysShow(sysMenuVo.getMenuType().equals(StateEnums.MENU_M.getCode())?true:false);
            routerVo.setComponent(sysMenuVo.getMenuType().equals(StateEnums.MENU_M.getCode())?CoreConstant.DEFAULT_COMPONENT:sysMenuVo.getComponent());
            routerVo.setHidden(sysMenuVo.getVisible()==0?false:true);
            routerVo.setName(sysMenuVo.getMenuName());
            routerVo.setPath(sysMenuVo.getMenuType().equals(StateEnums.MENU_M.getCode())?CoreConstant.URL_SPLIT+sysMenuVo.getPath():sysMenuVo.getPath());
            routerVo.setRedirect(sysMenuVo.getMenuType().equals(StateEnums.MENU_M.getCode())?CoreConstant.NO_REDIRECT:null);
            List<SysMenuVo> children= sysMenuVo.getChildren();
            if(CollectionUtil.isNotEmpty(children)){
                routerVo.setChildren(buildRouterVoList(children));
            }
            result.add(routerVo);
        }
        return result;
    }

}
