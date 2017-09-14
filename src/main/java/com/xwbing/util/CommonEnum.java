package com.xwbing.util;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 说明: 公共枚举
 * 项目名称: zdemo
 * 创建日期: 2016年12月9日 上午9:50:30
 * 作者: xiangwb
 */
public class CommonEnum {

    /**
     * YesOrNo
     */
    public enum YesOrNo {
        YES("是", "Y"), NO("否", "N");
        //成员变量
        private String name;
        private String code;

        YesOrNo(String name, String code) {
            this.name = name;
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * 校验信息
     */
    public enum ValidateResultEnum {
        SUCCESS("校验通过", 1), FAILED("校验失败", 0);
        private String msg;
        private int errorCode;

        ValidateResultEnum(String msg, int errorCode) {
            this.msg = msg;
            this.errorCode = errorCode;
        }

        public String getMsg() {
            return this.msg;
        }

        public int getErrorCode() {
            return this.errorCode;
        }
    }

    /**
     * 快递鸟物流状态
     */
    public enum ExpressStatusEnum {
        NoMsg("无信息", 0),
        HasTake("已取件", 1),
        OnTheWay("在途中", 2),
        Received("已签收", 3),
        Question("问题件", 4),
        ToTake("待取件", 5),
        ToSend("待派件", 6),
        HasBeenShipped("已发货", 8),
        Ufilled("未发货", 9);
        private String name;
        private int value;

        ExpressStatusEnum(String name, int value) {
            this.name = name;
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public String getName() {
            return name;
        }
    }

    /***
     * 支付方式
     *
     */
    public enum PayWayEnum {
        Alipay("支付宝支付", "alipay"), Wxpay("微信支付", "wxpay"), Cash("现金支付", "cash");
        private String name;
        private String value;

        PayWayEnum(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public String getName() {
            return name;
        }

    }

    /***
     * 支付状态
     *
     */
    public enum PayStatusEnum {
        HasPay("已支付", "HasPay"),
        NoPay("未支付", "NoPay"),
        HasRefund("已退款", "HasRefund"),
        HasCancel("取消", "HasCancel");
        private String name;
        private String value;

        PayStatusEnum(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 比较符号
     */
    public enum baseRage {
        ltRange("小于", "ltRange"), lteRange("小于等于", "lteRange"), gtRange("大于等于", "gtRange"), gteRange("大于", "gteRange");
        private String name;
        private String code;

        baseRage(String name, String code) {
            this.code = code;
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public String getCode() {
            return code;
        }
    }

    public static void main(String[] args) {
        String code = "Y";
        for (YesOrNo yesOrNo : YesOrNo.values()) {
            if (yesOrNo.getCode().equals(code)) {
                System.out.println(yesOrNo.getName());
                break;
            }
        }
        YesOrNo yesOrNo = Arrays.stream(YesOrNo.values()).filter(obj -> obj.getCode().equals(code)).collect(Collectors.toList()).get(0);
        System.out.println(yesOrNo.getName());
    }
}
