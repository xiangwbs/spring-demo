package com.xwbing.exception;

/**
 * 说明: 支付异常
 * 项目名称: spring-demo
 * 创建时间: 2017/5/10 17:31
 * 作者:  xiangwb
 */

public class PayException extends RuntimeException {
    public PayException(Throwable cause) {
        super(cause);
    }

    public PayException(String message) {
        super(message);
    }

    public PayException(String message, Throwable cause) {
        super(message, cause);
    }
}
