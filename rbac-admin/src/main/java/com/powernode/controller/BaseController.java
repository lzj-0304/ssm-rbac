package com.powernode.controller;

import com.powernode.constants.CoreConstant;
import com.powernode.domain.SysUser;
import com.powernode.utils.ServletUtils;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;

/**
 * @Author: Mr.T
 * @Date: 2023/3/7
 */
@Controller
public class BaseController {

    /**
     * 获取用户信息
     * @return
     */
    public SysUser getCurrentUser() {
        HttpSession session = ServletUtils.getSession();
        return (SysUser) session.getAttribute(CoreConstant.SESSION_KEY);
    }


    public String getUserName(){
        return getCurrentUser().getUserName();
    }


}
