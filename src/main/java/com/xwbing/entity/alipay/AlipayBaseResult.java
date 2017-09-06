package com.xwbing.entity.alipay;

import lombok.Data;

/**
 * 说明: 支付宝接口基础类
 * 项目名称: spring-demo
 * 创建时间: 2017/5/10 17:35
 * 作者:  xiangwb
 */
@Data
public class AlipayBaseResult {
    /**
     * 是否成功
     */
    private boolean isSuccess;
    /**
     * 错误信息
     */
    private String message;
    /**
     * 错误码
     */
    private String code;
    /**
     * 签名信息
     */
    private String sign;
}
