package com.powernode.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Mr.T
 * @Date:   2023/3/17
 */
/**
    * 系统访问记录
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysLoginInfo implements Serializable {
    /**
    * 访问ID
    */
    private Long infoId;

    /**
    * 用户账号
    */
    private String userName;

    /**
    * 登录IP地址
    */
    private String ipAddr;

    /**
    * 登录地点
    */
    private String loginLocation;

    /**
    * 浏览器类型
    */
    private String browser;

    /**
    * 操作系统
    */
    private String os;

    /**
    * 登录状态（0成功 1失败）
    */
    private Integer status;

    /**
    * 提示消息
    */
    private String msg;

    /**
    * 访问时间
    */
    private Date loginTime;

    private static final long serialVersionUID = 1L;
}