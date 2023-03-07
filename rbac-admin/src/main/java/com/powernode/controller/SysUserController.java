package com.powernode.controller;

import com.powernode.domain.SysUser;
import com.powernode.service.SysUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: Mr.T
 * @Date: 2023/3/7
 */
@RestController
@RequestMapping("/sysUser")
public class SysUserController {

    @Resource
    private SysUserService sysUserService;



    @GetMapping("getUserByUserId/{userId}")
    public SysUser getUserByUserId(@PathVariable Long  userId){
        return sysUserService.selectByPrimaryKey(userId);
    }



}
