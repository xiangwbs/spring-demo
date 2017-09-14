package com.xwbing.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 说明: 系统配置
 * 创建日期: 2016年9月27日 下午1:15:47
 * 作者: xiangwb
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysConfig extends BaseEntity {
    private static final long serialVersionUID = -6864746539080441497L;
    public static String table = "system_config";
    /**
     * 配置项的key
     */
    private String code;
    /**
     * 配置项的值
     */
    private String value;
    /**
     * 配置项的描述（名称）
     */
    private String name;
    /**
     * 是否启用
     */
    private String isEnable;
}
