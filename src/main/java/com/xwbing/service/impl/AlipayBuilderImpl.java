package com.xwbing.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradePayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.xwbing.entity.alipay.*;
import com.xwbing.exception.BusinessException;
import com.xwbing.exception.PayException;
import com.xwbing.service.AlipayBuilder;
import com.xwbing.util.CommonConstant;
import com.xwbing.util.PropertiesUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 说明: 支付宝支付接口实现
 * 项目名称: spring-demo
 * 创建时间: 2017/5/10 17:30
 * 作者:  xiangwb
 */
@Service
public class AlipayBuilderImpl implements AlipayBuilder {
    private final Logger logger = LoggerFactory.getLogger(AlipayBuilderImpl.class);
    private static final String TARGET = CommonConstant.PAY;
    /**
     * 支付宝分配给开发者的应用ID
     */
    private static String appId;
    /**
     * 私钥
     */
    private static String privateKey;
    /**
     * 公钥
     */
    private static String publicKey;
    /**
     * 请求url
     */
    private static String requestUrl;

    static {
        appId = PropertiesUtil.getValueByKey("alipay.appId", TARGET);
        requestUrl = PropertiesUtil.getValueByKey("alipay.requestUrl", TARGET);
        privateKey = PropertiesUtil.getValueByKey("alipay.rsaPrivateKey", TARGET);
        publicKey = PropertiesUtil.getValueByKey("alipay.rsaPublicKey", TARGET);
    }

    @Override
    public AlipayBarCodePayResult barCodePay(AlipayBarCodePayParam parma) throws PayException {
        parma.setScene("bar_code");//设置条码支付
        AlipayBarCodePayResult result = new AlipayBarCodePayResult(false);
        String checkArgument = checkArgument(parma);
        if (StringUtils.isNotEmpty(checkArgument)) {
            result.setMessage(checkArgument);
            return result;
        }
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(requestUrl, appId, privateKey, "json", "UTF-8", publicKey, "RSA2");
        //创建API对应的request类
        AlipayTradePayRequest request = new AlipayTradePayRequest();
        request.setBizContent(JSONObject.toJSONString(parma));
        //通过alipayClient调用API，获得对应的response类
        AlipayTradePayResponse response = null;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            logger.info(e.getMessage());
            result.setMessage(e.getMessage());
            return result;
        }
        if (response==null) {
            logger.info("alipay response is null!");
            result.setMessage("alipay response is null!");
            return result;
        }
        result.setSuccess(response.isSuccess()); //源码StringUtils.isEmpty(this.subCode)
        //根据response中的结果继续业务逻辑处理:subCode不为空，表示接口调用失败|code为10000，代表接口调用成功，subCode为空
        if (StringUtils.isNotEmpty(response.getSubCode())) {
            result.setCode(response.getSubCode());
            result.setMessage(response.getSubMsg());
            logger.info(response.getSubMsg());
            return result;
        } else {
            result.setCode(response.getCode());
            result.setMessage(response.getMsg());
        }
        result.setTradeNo(response.getTradeNo());
        result.setOutTradeNo(response.getOutTradeNo());
        result.setBuyerLogonId(response.getBuyerLogonId());
        result.setTotalAmount(response.getTotalAmount());
        result.setReceiptAmount(response.getReceiptAmount());
        result.setGmtPayment(response.getGmtPayment());
        result.setFundBillList(response.getFundBillList());
        result.setBuyerUserId(response.getBuyerUserId());
        result.setDiscountGoodsDetail(response.getDiscountGoodsDetail());
        logger.info("result = " + result);
        return result;
    }

    @Override
    public AlipayRefundResult refund(AlipayRefundParam parma) throws PayException {
        AlipayRefundResult result = new AlipayRefundResult(false);
        AlipayClient alipayClient = new DefaultAlipayClient(requestUrl, appId, privateKey, "json", "UTF-8", publicKey, "RSA2");
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        request.setBizContent(JSONObject.toJSONString(parma));
        //通过alipayClient调用API，获得对应的response类
        AlipayTradeRefundResponse response = null;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            logger.info(e.getMessage());
            result.setMessage(e.getMessage());
            return result;
        }
        if (response==null) {
            logger.info("alipay response is null!");
            result.setMessage("alipay response is null!");
            return result;
        }
        result.setSuccess(response.isSuccess());
        //如果subCode存在，代表调用接接口失败
        if (StringUtils.isNotEmpty(response.getSubCode())) {
            result.setCode(response.getSubCode());
            result.setMessage(response.getSubMsg());
            logger.info(response.getSubMsg());
            return result;
        } else {
            result.setCode(response.getCode());
            result.setMessage(response.getMsg());
        }
        result.setTradeNo(response.getTradeNo());
        result.setOutTradeNo(response.getOutTradeNo());
        result.setBuyerLogonId(response.getBuyerLogonId());
        result.setFundChange(response.getFundChange());
        result.setRefundFee(response.getRefundFee());
        result.setGmtRefundPay(response.getGmtRefundPay());
        result.setBuyerUserId(response.getBuyerUserId());
        logger.info("result = " + result);
        return result;
    }

    @Override
    public AlipayQueryResult queryOrder(String outTradeNo, String tradeNo) throws PayException {
        if (StringUtils.isEmpty(outTradeNo) && StringUtils.isEmpty(tradeNo)) {
            throw new PayException("订单号和交易号不能同时为空!");
        }
        AlipayQueryResult result = new AlipayQueryResult(false);
        AlipayClient alipayClient = new DefaultAlipayClient(requestUrl, appId, privateKey, "json", "UTF-8", publicKey, "RSA2");
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        JSONObject jsonObject = new JSONObject();
        if (StringUtils.isNotEmpty(outTradeNo)) {
            jsonObject.put("out_trade_no", outTradeNo);
        }
        if (StringUtils.isNotEmpty(tradeNo)) {
            jsonObject.put("trade_no", tradeNo);
        }
        request.setBizContent(jsonObject.toString());
        //通过alipayClient调用API，获得对应的response类
        AlipayTradeQueryResponse response = null;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            logger.info(e.getMessage());
            result.setMessage(e.getMessage());
            return result;
        }
        if (response==null) {
            logger.info("alipay response is null!");
            result.setMessage("alipay response is null!");
            return result;
        }
        result.setSuccess(response.isSuccess());
        //如果subCode存在，代表调用接接口失败
        if (StringUtils.isNotEmpty(response.getSubCode())) {
            result.setCode(response.getSubCode());
            result.setMessage(response.getSubMsg());
            logger.info(response.getSubMsg());
            return result;
        } else {
            result.setCode(response.getCode());
            result.setMessage(response.getMsg());
        }
        //返回支付状态,业务调用时,遍历AlipayTradeStatusEnum与tradeStatus做比较
        result.setTradeStatus(response.getTradeStatus());
        logger.info(response.getTradeStatus());
        return result;
    }

    @Override
    public AlipayQueryResult queryRefund(String outTradeNo, String tradeNo, String outRequestNo) throws PayException {
        if (StringUtils.isEmpty(outTradeNo) && StringUtils.isEmpty(tradeNo)) {
            throw new PayException("订单号和交易号不能同时为空!");
        }
        AlipayQueryResult result = new AlipayQueryResult(false);
        AlipayClient alipayClient = new DefaultAlipayClient(requestUrl, appId, privateKey, "json", "UTF-8", publicKey, "RSA2");
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("out_request_no", outRequestNo);
        if (StringUtils.isNotEmpty(outTradeNo)) {
            jsonObject.put("out_trade_no", outTradeNo);
        }
        if (StringUtils.isNotEmpty(tradeNo)) {
            jsonObject.put("trade_no", tradeNo);
        }
        request.setBizContent(jsonObject.toString());
        //通过alipayClient调用API，获得对应的response类
        AlipayTradeQueryResponse response = null;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            logger.info(e.getMessage());
            result.setMessage(e.getMessage());
            return result;
        }
        if (response==null) {
            logger.info("alipay response is null!");
            result.setMessage("alipay response is null!");
            return result;
        }
        result.setSuccess(response.isSuccess());
        //如果subCode存在，代表调用接接口失败。业务调用时只需判断result是否为success即可
        if (StringUtils.isNotEmpty(response.getSubCode())) {
            result.setCode(response.getSubCode());
            result.setMessage(response.getSubMsg());
            logger.info(response.getSubMsg());
        } else {
            result.setCode(response.getCode());
            result.setMessage(response.getMsg());
            logger.info(response.getMsg());
        }
        return result;
    }

    /**
     * 入参校验
     *
     * @return
     */
    private String checkArgument(AlipayBarCodePayParam parma) {
        String message;
        if (StringUtils.isEmpty(parma.getOutTradeNo())) {
            message = "订单号为空";
        } else if (StringUtils.isEmpty(parma.getAuthCode())) {
            message = "授权码为空";
        } else if (StringUtils.isEmpty(parma.getSubject())) {
            message = "产品名称为空";
        } else if (0 >= parma.getTotalAmount()) {
            message = "金额必须大于0";
        } else {
            message = StringUtils.EMPTY;
        }
        return message;
    }

    public static void main(String[] args) {
        //刷卡支付
        AlipayBuilderImpl alipayBuilder = new AlipayBuilderImpl();
        String orderNo = "201705180202";
//        String authCode = "286796667181427987";
//        AlipayBarCodePayParam codePayParam = new AlipayBarCodePayParam(orderNo, authCode, "test", 0.1f);
//        AlipayBarCodePayResult codePayResult = alipayBuilder.barCodePay(codePayParam);
//        System.out.println(codePayResult.isSuccess() + codePayResult.getMessage());

        //查询订单
        String tradeNo = "2017051221001004630289850336";
        AlipayQueryResult queryResult = alipayBuilder.queryOrder(orderNo, tradeNo);
        if (!queryResult.isSuccess()) {
            throw new BusinessException(queryResult.getMessage());
        }
        String tradeStatus = queryResult.getTradeStatus();
        for (AlipayTradeStatusEnum status : AlipayTradeStatusEnum.values()) {
            if (Objects.equals(tradeStatus, status.getCode())) {
                System.out.println(status.getName());
                break;
            }
        }

//        //退款操作
        AlipayRefundParam refundParam = new AlipayRefundParam("201505129999", orderNo, 0.05f, "test");
//        AlipayRefundResult refundResult = alipayBuilder.refund(refundParam);
//        System.out.println(refundResult.getMessage());

//        //查询退款 只要success,即为成功
//        AlipayQueryResult refund = alipayBuilder.queryRefund(orderNo, "", "201505129999");
//        System.out.println(refund.isSuccess());
    }
}
