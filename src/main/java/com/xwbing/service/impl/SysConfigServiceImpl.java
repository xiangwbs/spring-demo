package com.xwbing.service.impl;

import com.xwbing.dao.SysConfigDao;
import com.xwbing.entity.SysConfig;
import com.xwbing.exception.BusinessException;
import com.xwbing.service.SysConfigService;
import com.xwbing.util.PassWordUtil;
import com.xwbing.util.RestMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class SysConfigServiceImpl implements SysConfigService {
    @Autowired
    private SysConfigDao sysConfigDao;

    /**
     * 增
     *
     * @param sysConfig
     * @return
     */
    @Override
    public RestMessage addConfig(SysConfig sysConfig) {

        RestMessage result = new RestMessage();
        if (sysConfig == null) {
            throw new BusinessException("配置数据不能为空");
        }
        SysConfig one = findByCode(sysConfig.getCode());
        if (one != null) {
            throw new BusinessException(one.getCode() + "已存在");
        }
        sysConfig.setId(PassWordUtil.createId());
        sysConfig.setCreateTime(new Date());
        int row = sysConfigDao.addConfig(sysConfig);
        if (row == 1) {
            result.setMsg("保存配置成功");
            result.setSuccess(true);
        } else {
            result.setMsg("保存配置失败");
        }
        return result;
    }

    /**
     * 删
     *
     * @param code
     * @return
     */
    @Override
    public RestMessage removeByCode(String code) {
        RestMessage result = new RestMessage();
        // 检查当前系统配置
        SysConfig sysConfig = findByCode(code);
        if (Objects.isNull(sysConfig)) {
            throw new BusinessException("该配置已删除");
        }
        int row = sysConfigDao.removeByCode(code);
        if (row == 1) {
            result.setMsg("删除配置成功");
            result.setSuccess(true);
        } else {
            result.setMsg("删除配置失败");
        }
        return result;

    }

    @Override
    public RestMessage setConfigByCode(String code, String value) {
        RestMessage result = new RestMessage();
        SysConfig old = findByCode(code);
        if (old == null) {
            throw new BusinessException("该配置不存在");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("code", code);
        params.put("value", value);
        int row = sysConfigDao.updateValueByCode(params);
        if (row == 1) {
            result.setMsg("更新配置成功");
            result.setSuccess(true);
        } else {
            result.setMsg("更新配置失败");
        }
        return result;
    }

    /**
     * 改
     *
     * @param sysConfig
     * @return
     */
    @Override
    public RestMessage update(SysConfig sysConfig) {
        RestMessage result = new RestMessage();
        SysConfig old = findByCode(sysConfig.getCode());
        if (old == null) {
            throw new BusinessException("该配置不存在");
        }
        old.setValue(sysConfig.getValue());
        old.setEnable(sysConfig.getEnable());
        old.setName(sysConfig.getName());
        old.setCode(sysConfig.getCode());
        old.setModifiedTime(new Date());
        int row = sysConfigDao.update(old);
        if (row == 1) {
            result.setMsg("更新配置成功");
            result.setSuccess(true);
        } else {
            result.setMsg("更新配置失败");
        }
        return result;
    }

    /**
     * 根据code查找
     *
     * @param code
     * @return
     */
    @Override
    public SysConfig findByCode(String code) {
        SysConfig sysConfig = sysConfigDao.findByCode(code);
        return sysConfig;
    }
}
