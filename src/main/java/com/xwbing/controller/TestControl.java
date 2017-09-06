package com.xwbing.controller;

import com.alibaba.fastjson.JSONObject;
import com.xwbing.annotation.LogInfo;
import com.xwbing.entity.SysConfig;
import com.xwbing.entity.alipay.AlipayQueryResult;
import com.xwbing.entity.alipay.AlipayTradeStatusEnum;
import com.xwbing.entity.vo.RangeVo;
import com.xwbing.exception.BusinessException;
import com.xwbing.redis.RedisName;
import com.xwbing.redis.RedisService;
import com.xwbing.service.AlipayBuilder;
import com.xwbing.service.SysConfigService;
import com.xwbing.util.CommonEnum;
import com.xwbing.util.JSONObjResult;
import com.xwbing.util.PassWordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 说明:
 * 项目名称: spring-demo
 * 创建时间: 2017/4/27 17:50
 * 作者:  xiangwb
 */
@RestController
@RequestMapping("/test/")
public class TestControl {
    @Autowired
    SysConfigService sysConfigService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private AlipayBuilder alipayBuilder;
    private final Logger logger = LoggerFactory.getLogger(TestControl.class);

    @LogInfo("log")
    @GetMapping("logback")
    public void logback() {
//        throw new BusinessException("error");
        logger.info("info");
        logger.error("error");
    }

    @GetMapping("redis")
    public void redis() {
        redisService.set(RedisName.redistest, "xwbing");
        logger.info("获取的数据为:" + redisService.get(RedisName.redistest));
    }

    @LogInfo("业务组件")
    @GetMapping("logAop")
    public void logAop() {
        System.out.println("工作了。。。。。。。。。。。。。。");
    }

    @Transactional
    @GetMapping("transaction")
    public void transaction() {
        SysConfig one = new SysConfig();
        one.setId(PassWordUtil.createId());
        one.setCode("aaa");
        one.setIsEnable("Y");
        one.setName("aaa");
        one.setValue("aaa");
        sysConfigService.addConfig(one);
        SysConfig two = new SysConfig();
        two.setId(PassWordUtil.createId());
        two.setCode("bbb");
        two.setIsEnable("Y");
        two.setName("bbb");
        two.setValue("bbb");
        sysConfigService.addConfig(two);
        throw new BusinessException("这组操作出现异常，事务回滚");
    }

    @GetMapping("getEnumValues")
    public JSONObject getEnumValues() {
        List<RangeVo> list = new ArrayList<>();
        RangeVo vo;
        for (CommonEnum.baseRage rage : CommonEnum.baseRage.values()) {
            vo = new RangeVo();
            vo.setCode(rage.getCode());
            vo.setName(rage.getName());
            list.add(vo);
        }
        return JSONObjResult.toJSONObj(list, true, "");
    }

    @GetMapping("alipay")
    public void alipay() {
        AlipayQueryResult queryResult = alipayBuilder.queryOrder("201705120202", "2017051221001004630289850336");
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
    }
}
