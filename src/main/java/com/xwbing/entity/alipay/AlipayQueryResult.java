package com.xwbing.entity.alipay;

/**
 * 说明: 支付宝查询状态
 * 项目名称: spring-demo
 * 创建时间: 2017/5/10 17:38
 * 作者:  xiangwb
 */
public class AlipayQueryResult extends  AlipayBaseResult {
    /**
     * 交易支付状态
     */
    private  String tradeStatus;

    public AlipayQueryResult(boolean success) {
        this.setSuccess(success);
    }

    public String getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
    }
}

