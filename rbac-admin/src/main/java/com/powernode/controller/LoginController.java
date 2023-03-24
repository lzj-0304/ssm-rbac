package com.powernode.controller;

import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.crypto.digest.DigestUtil;
import com.powernode.async.AsyncSysLoginInfoService;
import com.powernode.common.Result;
import com.powernode.constants.CoreConstant;
import com.powernode.domain.SysLoginInfo;
import com.powernode.domain.SysUser;
import com.powernode.enums.ResultEnums;
import com.powernode.enums.StateEnums;
import com.powernode.service.SysLoginInfoService;
import com.powernode.service.SysUserService;
import com.powernode.utils.HttpUtils;
import com.powernode.utils.ServletUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Mr.T
 * @Date: 2023/3/7
 */
@RestController
public class LoginController extends BaseController{


    @Resource
    private SysUserService sysUserService;


    @Resource
    private AsyncSysLoginInfoService asyncSysLoginInfoService;


    @PostMapping("/login")
    public Result login(String username,String password,String code){
        /**
         * 登录逻辑处理
         *    1.校验验证码
         *       验证码为空 不正确 方法结束
         *       验证码如果正确 session 删除验证码key
         *    2.根据用户名 & 密码 到数据库查询用户记录
         *       用户记录不存在 方法结束
         *    3.用户存在  将用户信息存入session 返回result 携带用户信息
         */

        SysLoginInfo sysLoginInfo =new SysLoginInfo();
        SysUser sysUser;
        try {
            sysLoginInfo.setUserName(username);
            sysLoginInfo.setBrowser(HttpUtils.getBrowserName(ServletUtils.getRequest()));
            sysLoginInfo.setLoginTime(new Date());
            sysLoginInfo.setOs(HttpUtils.getOsName(ServletUtils.getRequest()));
            sysLoginInfo.setIpAddr(HttpUtils.getIpAddr(ServletUtils.getRequest()));
            HttpSession session = ServletUtils.getSession();
            CircleCaptcha captcha = (CircleCaptcha) session.getAttribute(CoreConstant.CAPTCHA_KEY);
            if(null == captcha){
                sysLoginInfo.setMsg("验证码不存在或已过期!");
                sysLoginInfo.setStatus(StateEnums.LOGIN_ERROR.getCode());
                return new Result(ResultEnums.ERROR,"验证码不存在或已过期!");
            }
            if(!captcha.verify(code)){
                sysLoginInfo.setMsg("验证码不正确!");
                sysLoginInfo.setStatus(StateEnums.LOGIN_ERROR.getCode());
                return new Result(ResultEnums.ERROR,"验证码不正确!");
            }
            // 移除验证码
            session.removeAttribute(CoreConstant.CAPTCHA_KEY);

            password = DigestUtil.md5Hex(password);
            sysUser = sysUserService.login(username,password);
            if(sysUser ==null){
                sysLoginInfo.setMsg("用户不存在或密码错误!");
                sysLoginInfo.setStatus(StateEnums.LOGIN_ERROR.getCode());
                return new Result(ResultEnums.ERROR,"用户不存在或密码错误!");
            }

            // 将用户信息存入会话
            session.setAttribute(CoreConstant.SESSION_KEY,sysUser);
            sysUser.setPassword("");
            sysLoginInfo.setStatus(StateEnums.LOGIN_SUCCESS.getCode());
            sysLoginInfo.setMsg("用户登录成功");
        } finally {
            // 异步保存
            asyncSysLoginInfoService.saveLoginLogInfo(sysLoginInfo);
        }
        return new Result("用户登录成功",sysUser);
    }


    /**
     * 用户退出
     * @return
     */
    @GetMapping("/logout")
    public Result logout(){
        HttpSession session = ServletUtils.getSession();
        session.removeAttribute(CoreConstant.SESSION_KEY);
        session.invalidate();
        return new Result(ResultEnums.SUCCESS,"退出成功");
    }


    /**
     * 获取用户信息
     * @return
     */
    @GetMapping("/getUserInfo")
    public Result<Map<String,Object>> getUserInfo(){
        SysUser currentUser = getCurrentUser();
        currentUser.setPassword("");
        //"user":currentUser
        List<String> permissions = sysUserService.queryUserHasPermissions(currentUser.getUserId());
        Map<String,Object> map=new HashMap<>();
        map.put("user",currentUser);
        map.put("permissions",permissions);
        // 将用户扮演的角色拥有的权限放入会话
        ServletUtils.getSession().setAttribute(CoreConstant.PERMISSIONS_KEY,permissions);
        return new Result<>(ResultEnums.SUCCESS.getMsg(),map);
    }







}
