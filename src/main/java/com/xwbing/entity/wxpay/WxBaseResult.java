package com.xwbing.entity.wxpay;

import org.apache.commons.lang.StringUtils;

/**
 * 说明: 微信支付结果基础类
 * 项目名称: spring-demo
 * 创建时间: 2017/5/10 17:42
 * 作者:  xiangwb
 */

public class WxBaseResult {
    /**
     * 返回状态码
     */
    private String resultCode;
    /**
     * 返回信息
     */
    private String message;
    /**
     * 是否成功
     */
    private boolean isSuccess;

    public boolean isSuccess() {
        if (StringUtils.isNotEmpty(resultCode)) {
            return resultCode.equals("SUCCESS");
        }
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "BaseResult{" + "resultCode='" + resultCode + '\'' + ", message='" + message + '\'' + ", isSuccess=" + isSuccess + '}';
    }
}

