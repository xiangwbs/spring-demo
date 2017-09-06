package com.xwbing.entity.model;

import com.xwbing.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 说明: 快递信息
 * 项目名称: spring-demo
 * 创建时间: 2017/5/12 15:03
 * 作者:  xiangwb
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ExpressInfo extends BaseEntity {
    private static final long serialVersionUID = 6735419588148148727L;
    public static String table = "express_info";
    /**
     * 快递名称
     */
    private String expressName;
    /**
     * 运单号
     */
    private String LogisticCode;
    /**
     * 订单号
     */
    private String orderNo;
}
