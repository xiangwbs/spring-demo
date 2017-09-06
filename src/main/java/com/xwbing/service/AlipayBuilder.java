package com.xwbing.service;

import com.xwbing.entity.alipay.*;
import com.xwbing.exception.PayException;

/**
 * 说明: 支付宝支付接口
 * 项目名称: spring-demo
 * 创建时间: 2017/5/10 17:30
 * 作者:  xiangwb
 */

public interface AlipayBuilder {
    /**
     * 条形码支付
     *
     * @param parma
     * @return
     */
    AlipayBarCodePayResult barCodePay(AlipayBarCodePayParam parma) throws PayException;

    /**
     * 退款
     *
     * @param parma
     * @return
     */
    AlipayRefundResult refund(AlipayRefundParam parma) throws PayException;

    /**
     * 根据订单号 交易号查询 只需要一个即可
     * 如果isSuccess，根据tradeStatus，遍历AlipayTradeStatusEnum获取对应支付状态
     *
     * @param outTradeNo 订单号
     * @param tradeNo    交易号(推荐)
     * @return
     */
    AlipayQueryResult queryOrder(String outTradeNo, String tradeNo) throws PayException;

    /**
     * 退款查询  没有tradeStatus，isSuccess即为成功
     * 订单号和交易号2选1
     *
     * @param outTradeNo   订单号
     * @param tradeNo      交易号(推荐)
     * @param outRequestNo 退款请求号
     * @return
     * @throws PayException
     */
    AlipayQueryResult queryRefund(String outTradeNo, String tradeNo, String outRequestNo) throws PayException;

}
