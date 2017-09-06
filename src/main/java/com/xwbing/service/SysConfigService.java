package com.xwbing.service;

import com.xwbing.entity.SysConfig;
import com.xwbing.util.RestMessage;

/**
 * 说明:
 * 项目名称: spring-demo
 * 创建时间: 2017/5/12 14:20
 * 作者:  xiangwb
 */

public interface SysConfigService {
    /**
     * 增
     *
     * @param sysConfig
     * @return
     */
    RestMessage addConfig(SysConfig sysConfig);

    /**
     * 删
     *
     * @param code
     * @return
     */
    RestMessage removeByCode(String code);

    /**
     * 根据code修改value
     *
     * @param code
     * @param value
     * @return
     */
    RestMessage setConfigByCode(String code, String value);

    /**
     * 改
     *
     * @param sysConfig
     * @return
     */
    RestMessage update(SysConfig sysConfig);

    /**
     * 根据code查找
     *
     * @param code
     * @return
     */
    SysConfig findByCode(String code);
}
