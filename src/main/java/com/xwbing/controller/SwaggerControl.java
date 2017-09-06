package com.xwbing.controller;

import com.alibaba.fastjson.JSONObject;
import com.xwbing.util.JSONObjResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 浙江卓锐科技股份有限公司 版权所有 ? Copyright 2016<
 * 说明:
 * 项目名称: spring-demo <br/>
 * 创建日期: 2017年4月20日 下午3:57:05
 * 作者: xwb
 */
@Api(tags = "swaggerApi",description = "用于测试")
@RestController
@RequestMapping("/swagger/")
public class SwaggerControl {
    private Logger logger = LoggerFactory.getLogger(SwaggerControl.class);

    @ApiOperation(value = "单个参数",notes = "单个参数请求")
    @ApiImplicitParam(name = "id", value = "主键", paramType = "query", required = true, dataType = "string")
    @PostMapping("/find")
    public JSONObject find(String id) {
        logger.info("info---------------------------");
        logger.error("error-------------------------");
        return JSONObjResult.toJSONObj(id, true, "");
    }

    @ApiOperation(value = "多个参数",notes = "多个参数请求")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "编码", paramType = "query", required = true, dataType = "string"),
            @ApiImplicitParam(name = "name", value = "名字", paramType = "query", required = true, dataType = "string")
    })
    @PostMapping("/list")
    public JSONObject list(@RequestParam String code, @RequestParam String name) {
        String sub = code + name;
        logger.info("info---------------------------");
        logger.error("error-------------------------");
        return JSONObjResult.toJSONObj(sub, true, "");
    }

}
