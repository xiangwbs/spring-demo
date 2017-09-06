package com.xwbing.service;

import com.xwbing.entity.wxpay.*;
import com.xwbing.exception.PayException;

/**
 * 说明: 微信支付接口
 * 项目名称: spring-demo
 * 创建时间: 2017/5/10 17:46
 * 作者:  xiangwb
 */
public interface WxPayBuilder {

    /**
     * 条形码扫码付
     *
     * @param param
     * @return
     */
    WxBarCodePayResult barCodePay(WxBarCodePayParam param) throws PayException;

    /**
     * 退款
     *
     * @param param
     * @return
     */
    WxRefundResult refund(WxRefundParam param) throws PayException;

    /**
     * 查询订单状态
     * 根据订单号 交易号查询 只需要一个即可
     * 如果isSuccess，根据tradeStatus，遍历WxTradeStatusEnum获取交易支付状态
     *
     * @param outTradeNo    商户订单号
     * @param transactionId 微信的订单号(推荐)
     * @return
     */
    WxQueryResult oderQuery(String outTradeNo, String transactionId) throws PayException;

    /**
     * 查询退款
     * 参数四选一即可
     * 如果isSuccess，根据refundStatus，遍历WxRefundStatusEnum获取退款状态
     *
     * @param outTradeNo    商户订单号
     * @param transactionId 微信的订单号
     * @param ouRefundNo    商户退款单号(推荐)
     * @param refundid      微信退款单号(推荐)
     * @return
     * @throws PayException
     */
    WxQueryResult refundQuery(String outTradeNo, String transactionId, String ouRefundNo, String refundid) throws PayException;
}

