package com.xwbing.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xwbing.entity.model.ExpressInfo;
import com.xwbing.entity.vo.ExpressInfoVo;
import com.xwbing.exception.BusinessException;
import com.xwbing.util.CommonConstant;
import com.xwbing.util.CommonEnum;
import com.xwbing.util.PropertiesUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static com.xwbing.util.KdniaoUtil.*;

/**
 * 说明: 快递鸟订阅推送2.0接口
 * 项目名称: spring-demo
 * 创建时间: 2017/5/10 17:30
 * 作者:  xiangwb
 */
@Service
public class KdniaoSubscribeAPI {
    private static final String TARGET = CommonConstant.KDNIAO;
    /**
     * 电商ID
     */
    private String EBusinessID;
    /**
     * 电商加密私钥
     */
    private String AppKey;
    /**
     * 正式请求url
     */
    private String ReqURL;

    public KdniaoSubscribeAPI() {
        if (StringUtils.isEmpty(EBusinessID) || StringUtils.isEmpty(AppKey) || StringUtils.isEmpty(ReqURL)) {
            EBusinessID = PropertiesUtil.getValueByKey("kdniao.EBusinessID", TARGET);
            AppKey = PropertiesUtil.getValueByKey("kdniao.AppKey", TARGET);
            ReqURL = PropertiesUtil.getValueByKey("kdniao.ReqURL", TARGET);
        }
    }

    /**
     * Json方式 查询订单物流轨迹
     *
     * @param info
     * @return
     * @throws Exception
     */
    public ExpressInfoVo orderTracesSubByJson(ExpressInfo info) throws Exception {
        String shipperCode = "";
        if (info != null) {// 暂时处理这些常用快递
            if ("顺丰速运".equals(info.getExpressName())) {
                shipperCode = "SF";
            } else if ("中国邮政".equals(info.getExpressName())
                    || "EMS".equalsIgnoreCase(info.getExpressName())) {
                shipperCode = "EMS";
            } else if ("百世快递".equals(info.getExpressName())
                    || "百世汇通".equals(info.getExpressName())) {
                shipperCode = "HTKY";
            } else if ("中通".equals(info.getExpressName())
                    || "中通快递".equals(info.getExpressName())) {
                shipperCode = "ZTO";
            } else if ("申通".equals(info.getExpressName())
                    || "申通快递".equals(info.getExpressName())) {
                shipperCode = "STO";
            } else if ("圆通".equals(info.getExpressName())
                    || "圆通快递".equals(info.getExpressName())) {
                shipperCode = "YTO";
            } else if ("韵达".equals(info.getExpressName())
                    || "韵达快递".equals(info.getExpressName())) {
                shipperCode = "YD";
            } else if ("天天".equals(info.getExpressName())
                    || "天天快递".equals(info.getExpressName())) {
                shipperCode = "HHTT";
            }
        }
        if (info != null && info.getLogisticCode() != null) {
            String requestData = "{'OrderCode':'','ShipperCode':'"
                    + shipperCode + "','LogisticCode':'" + info.getLogisticCode()
                    + "'}";
            Map<String, String> params = new HashMap<String, String>();
            try {
                params.put("RequestData", urlEncoder(requestData, "UTF-8"));
                params.put("EBusinessID", EBusinessID);
                params.put("RequestType", "1002");
                String dataSign = encrypt(requestData, AppKey, "UTF-8");
                params.put("DataSign", urlEncoder(dataSign, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                throw new BusinessException(e.getMessage());
            } catch (Exception e) {
                throw new BusinessException(e.getMessage());
            }
            params.put("DataType", "2");
            // 返回物流信息
            // status: 0|null 无信息 1已取件 2在途中 3已签收 4问题件 5待取件 6待派件 8已发货 9未发货
            String result = sendPost(ReqURL, params);
            ExpressInfoVo infoVo = JSONObject.parseObject(result, ExpressInfoVo.class);
            // TODO: 2017/5/12  根据公司业务处理返回的信息......
            return infoVo;
        }
        return null;
    }

    // DEMO
    public static void main(String[] args) {
        KdniaoSubscribeAPI api = new KdniaoSubscribeAPI();
        ExpressInfo info = new ExpressInfo();
        info.setExpressName("百世汇通");
        info.setLogisticCode("211386517825");
        info.setOrderNo("201703140000000018");
        try {
            ExpressInfoVo result = api.orderTracesSubByJson(info);
            String status = result.getState();
            if (StringUtils.isEmpty(status)) {
                status = String.valueOf(0);
            }
            String describe = null;
            //更新订单物流状态
            for (CommonEnum.ExpressStatusEnum statusEnum : CommonEnum.ExpressStatusEnum.values()) {
                int value = statusEnum.getValue();
                if (Integer.valueOf(status) == value) {
                    describe = statusEnum.getName();
                    break;
                }
            }
            // TODO: 2017/5/12
            System.out.print(describe);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }
}
