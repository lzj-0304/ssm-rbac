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
    * 操作日志记录
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysOperLog implements Serializable {
    /**
    * 日志主键
    */
    private Long operId;

    /**
    * 模块标题
    */
    private String title;

    /**
    * 业务类型（0其它 1新增 2修改 3删除）
    */
    private Integer businessType;

    /**
    * 方法名称
    */
    private String method;

    /**
    * 请求方式
    */
    private String requestMethod;

    /**
    * 操作人员
    */
    private String operName;

    /**
    * 操作人员ID
    */
    private Long userId;

    /**
    * 请求URL
    */
    private String operUrl;

    /**
    * 主机地址
    */
    private String operIp;

    /**
    * 操作地点
    */
    private String operLocation;

    /**
    * 请求参数
    */
    private String operParam;

    /**
    * 返回参数
    */
    private String jsonResult;

    /**
    * 操作状态（0正常 1异常）
    */
    private Integer status;

    /**
    * 错误消息
    */
    private String errorMsg;

    /**
    * 操作时间
    */
    private Date operTime;

    private static final long serialVersionUID = 1L;
}