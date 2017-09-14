package com.xwbing.redis;

import com.xwbing.util.CommonConstant;
import com.xwbing.util.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Set;

/**
 * 说明:启动的时候加载方法
 * 项目名称: spring-demo
 * 创建日期: 2016年9月27日 下午1:25:47
 * 作者: xiangwb
 */
@Service
public class StartupInit implements InitializingBean {
    private static Logger logger = LoggerFactory.getLogger(StartupInit.class);
    @Autowired
    RedisService redisService;
    private static final String TARGET = CommonConstant.REDIS;
    static boolean firstInit = false;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (firstInit) {
        } else {
            firstInit = true;
            System.out.println("---------启动执行方法---刷新缓存----------------");
            redisService.set("xwb", "测试数据");
            System.out.printf(redisService.get("xwb"));
            redisService.del("xwb");
            System.out.printf(String.valueOf(redisService.exists("xwb")));

            Set<String> set = redisService.keys(PropertiesUtil
                    .getValueByKey("customerRedisCode", TARGET) + "*");
            Iterator<String> it = set.iterator();
            while (it.hasNext()) {
                String keyStr = it.next();
                System.out.println("启动删除缓存名称==" + keyStr);
                redisService.delInit(keyStr);
            }
        }
    }
}
