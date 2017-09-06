package com.xwbing.controller;

import com.alibaba.fastjson.JSONObject;
import com.xwbing.annotation.LogInfo;
import com.xwbing.entity.SysConfig;
import com.xwbing.service.SysConfigService;
import com.xwbing.util.JSONObjResult;
import com.xwbing.util.RestMessage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * 说明:
 * 项目名称: spring-demo
 * 创建时间: 2017/4/28 16:13
 * 作者:  xiangwb
 */
@RestController
@RequestMapping("/sysConfig/")
public class SysConfigControl {
    private Logger logger = LoggerFactory.getLogger(SwaggerControl.class);
    @Autowired
    private SysConfigService sysConfigService;

    /**
     * 增
     *
     * @param sysConfig
     * @return
     */
    @LogInfo("新增系统配置信息")
    @PostMapping("save")
    public JSONObject save(SysConfig sysConfig) {
        RestMessage result = sysConfigService.addConfig(sysConfig);
        return JSONObjResult.toJSONObj(result);
    }

    /**
     * 删
     *
     * @param code
     * @return
     */
    @LogInfo("删除系统配置信息")
    @DeleteMapping("removeByCode")
    public JSONObject removeByCode(@RequestParam String code) {
        if (StringUtils.isEmpty(code)) {
            return JSONObjResult.toJSONObj("code不能为空");
        }
        RestMessage result = sysConfigService.removeByCode(code);
        return JSONObjResult.toJSONObj(result);
    }

    /**
     * 改
     *
     * @param sysConfig
     * @return
     */
    @LogInfo("修改系统配置信息")
    @PostMapping("update")
    public JSONObject update(SysConfig sysConfig) {
        if (StringUtils.isEmpty(sysConfig.getCode())) {
            return JSONObjResult.toJSONObj("code不能为空");
        }
        RestMessage result = sysConfigService.update(sysConfig);
        return JSONObjResult.toJSONObj(result);
    }

    @LogInfo("修改系统配置信息")
    @PostMapping("setConfigByCode")
    public JSONObject setConfigByCode(@RequestParam String code, @RequestParam String value) {
        if (StringUtils.isEmpty(code)) {
            return JSONObjResult.toJSONObj("code不能为空");
        }
        if (StringUtils.isEmpty(value)) {
            return JSONObjResult.toJSONObj("value不能为空");
        }
        RestMessage result = sysConfigService.setConfigByCode(code, value);
        return JSONObjResult.toJSONObj(result);
    }

    /**
     * 查
     *
     * @param code
     * @return
     */
    @LogInfo("根据code查找系统配置信息")
    @GetMapping("findByCode")
    public JSONObject findByCode(@RequestParam String code) {
        if (StringUtils.isEmpty(code)) {
            return JSONObjResult.toJSONObj("code不能为空");
        }
        SysConfig one = sysConfigService.findByCode(code);
        if (Objects.isNull(one)) {
            return JSONObjResult.toJSONObj("该对象不存在");
        }
        return JSONObjResult.toJSONObj(one, true, "");
    }
}
