package com.powernode.async;

import com.powernode.domain.SysLoginInfo;
import com.powernode.service.SysLoginInfoService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**

   异步业务处理类
 */
@Component
@EnableAsync
@Async
public class AsyncSysLoginInfoService {

    @Resource
    private SysLoginInfoService sysLoginInfoService;

    /**
     * 添加登陆日志
     */
    public void saveLoginLogInfo(SysLoginInfo loginInfo){
        String ipAddr = loginInfo.getIpAddr();
        //处理IP
        if (ipAddr!=null){
            if(ipAddr.equalsIgnoreCase("127.0.0.1")||ipAddr.equalsIgnoreCase("localhost")){
                loginInfo.setLoginLocation("本地");
            }else{
                //大家可以去网上找对应的IP查询API，去发请求得到IP的所在地址
            }
        }
        this.sysLoginInfoService.insertSelective(loginInfo);
    }



}
