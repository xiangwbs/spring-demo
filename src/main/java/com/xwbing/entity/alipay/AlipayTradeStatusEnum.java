package com.xwbing.entity.alipay;

/**
 * 说明: 支付宝交易状态
 * 项目名称: spring-demo
 * 创建时间: 2017/5/10 17:39
 * 作者:  xiangwb
 */
public enum AlipayTradeStatusEnum {
    WAIT_BUYER_PAY("交易创建，等待买家付款", "WAIT_BUYER_PAY"),
    TRADE_CLOSED("未付款交易超时关闭，或支付完成后全额退款", "TRADE_CLOSED"),
    TRADE_SUCCESS("交易支付成功", "TRADE_SUCCESS"),
    CLOSED("交易结束，不可退款", "TRADE_FINISHED");
    // 成员变量
    private String code;
    private String name;

    private AlipayTradeStatusEnum(String name, String code) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
