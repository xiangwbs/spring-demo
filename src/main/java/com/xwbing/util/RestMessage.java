package com.xwbing.util;

import java.io.Serializable;

/**
 * 说明:返回消息封装类
 * 创建日期: 2017年3月3日 上午11:43:13
 * 作者: xwb
 */
public class RestMessage implements Serializable {
    private static final long serialVersionUID = -4167591341943919542L;
    private boolean isSuccess = false;// 默认false
    private String msg;//  成功、错误返回提示信息
    private Object data;// 返回的数据
    private String id;// 新增、修改主鍵返回id

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }
    public RestMessage() {
        super();
    }

    public RestMessage(Object data) {
        super();
        this.data = data;
    }

    public RestMessage(boolean isSuccess, Object data) {
        super();
        this.isSuccess = isSuccess;
        this.data = data;
    }
}
