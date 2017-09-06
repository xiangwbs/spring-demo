package com.xwbing.util.captcah;

/**
 * 说明:创建验证码异常类 <br/>
 * 创建日期: 2016年8月29日 上午11:02:42 <br/>
 * 作者: xwb
 */
public class CaptchaException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CaptchaException() {
        super();
    }

    public CaptchaException(String message, Throwable cause) {
        super(message, cause);
    }

    public CaptchaException(String message) {
        super(message);
    }

    public CaptchaException(Throwable cause) {
        super(cause);
    }

}	