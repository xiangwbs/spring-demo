package com.xwbing.entity.vo;

import com.xwbing.entity.model.Trace;
import lombok.Data;

import java.util.List;

/**
 * 说明: 快递信息展示
 * 项目名称: spring-demo
 * 创建时间: 2017/5/12 15:26
 * 作者:  xiangwb
 */
@Data
public class ExpressInfoVo {
    /**
     * 快递代码
     */
    private String ShipperCode;
    private Boolean Success;
    /**
     * 运单号
     */
    private String LogisticCode;
    /**
     * 状态
     */
    private String State;
    /**
     * 物流信息
     */
    private List<Trace> traces;
}
