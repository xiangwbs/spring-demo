package com.xwbing.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 说明: 定时任务
 * 项目名称: spring-demo
 * 创建时间: 2016/5/18 17:46
 * 作者: xiangwb
 */
@Component
public class BusinessTask {
    private final Logger logger = LoggerFactory.getLogger(BusinessTask.class);

    @Scheduled(cron = "0 */30 * * * ?")//每30分钟执行一次
    public void doTask() {
        logger.info("--------------每30分钟执行一次任务----------------------");
        // TODO: 2016/5/18
    }
}
