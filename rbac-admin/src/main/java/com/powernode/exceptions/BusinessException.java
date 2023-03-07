package com.powernode.exceptions;

/**
 * 自定义异常-业务异常类
 */
public class BusinessException  extends  RuntimeException{

    private Integer code=500;
    private String msg="操作失败!";


    public BusinessException(String msg){
        super(msg);
        this.msg=msg;
    }


    public BusinessException(Integer code,String msg){
        super(msg);
        this.code=code;
        this.msg=msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
