package com.powernode.annotations;

import java.lang.annotation.*;

/**
   权限注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface AppPermission {

    // 配置权限标识符
    String value();
}
