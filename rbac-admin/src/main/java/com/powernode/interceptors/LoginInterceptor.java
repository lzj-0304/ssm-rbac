package com.powernode.interceptors;

import com.alibaba.fastjson.JSON;
import com.powernode.common.Result;
import com.powernode.constants.CoreConstant;
import com.powernode.domain.SysUser;
import com.powernode.enums.ResultEnums;
import com.powernode.exceptions.BusinessException;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * @Author: Mr.T
 * @Date: 2023/3/7
 */
public class LoginInterceptor  implements HandlerInterceptor {



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /**
         * 判断session中是否存在用户信息
         *   存在 方法放行  true
         *   不存在 不在执行方法 false
         */
        HttpSession session = request.getSession();
        SysUser sysUser = (SysUser) session.getAttribute(CoreConstant.SESSION_KEY);
        if(null == sysUser){
            /**
             * 响应json 到浏览器
             */
            response.setContentType("application/json;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(JSON.toJSONString(new Result<>(ResultEnums.USER_NOT_LOGIN)));
            writer.flush();
            writer.close();
            return false;
            //throw  new BusinessException()
        }
        // 放行方法
        return true;
    }
}
