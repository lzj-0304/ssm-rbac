package com.powernode.enums;

/**
 * 业务操作类型
 */
public enum BusinessType {

    /**
     * 新增
     */
    INSERT(1,"新增"),

    /**
     * 修改
     */
    UPDATE(2,"修改"),

    /**
     * 删除
     */
    DELETE(3,"删除"),

    /**
     * 授权
     */
    GRANT(4,"授权"),

    /**
     * 导出
     */
    EXPORT(5,"导出"),

    /**
     * 清空数据
     */
    CLEAN(6,"清空数据"),


    QUERY(7,"查询"),

    /**
     * 其它
     */
    OTHER(8,"其它");

    private Integer code;
    private String msg;

    BusinessType(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
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