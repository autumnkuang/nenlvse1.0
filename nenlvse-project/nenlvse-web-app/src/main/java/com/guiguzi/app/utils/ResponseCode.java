package com.guiguzi.app.utils;

/**
 * Created by Administrator on 2015/4/19.
 */
public enum ResponseCode {
    NO_LOGIN(-99,"未登录"),
    SUCCESS(1,"请求数据成功"),
    FAILURE(0,"请求数据失败"),
    BIZ_ERROR(-2,"业务异常"),
    EXCEPTION(-1,"服务器内部错误");

    private int code;
    private String msg;

    ResponseCode(int code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
