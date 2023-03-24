package com.powernode.aspectj;

import cn.hutool.core.collection.CollectionUtil;
import com.powernode.annotations.AppPermission;
import com.powernode.constants.CoreConstant;
import com.powernode.enums.StateEnums;
import com.powernode.exceptions.BusinessException;
import com.powernode.utils.ServletUtils;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: Mr.T
 * @Date: 2023/3/16
 */
@Component
@Aspect
@EnableAspectJAutoProxy
public class PermissionAspect {


    @Before(value = "@annotation(appPermission)")
    public void before(AppPermission appPermission){
        String  permission = appPermission.value();
        System.out.println("权限判断开始..................");
        List<String> permissions = (List<String>) ServletUtils.getSession().getAttribute(CoreConstant.PERMISSIONS_KEY);
        if(CollectionUtil.isEmpty(permissions) || !(permissions.contains(permission))){
            throw  new BusinessException(CoreConstant.NO_PERMISSION);
        }
    }



}
