package com.xwbing.dao;

import com.xwbing.entity.SysConfig;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * 说明:
 * 项目名称: spring-demo
 * 创建时间: 2017/4/27 16:19
 * 作者:  xiangwb
 */
@Repository
public interface SysConfigDao {
    int addConfig(SysConfig sysConfig);
    int removeByCode(String code);
    int updateValueByCode(Map<String,Object> params);
    int update(SysConfig sysConfig);
    SysConfig findByCode(String code);
    SysConfig findById(String id);
}
