package com.xwbing.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 说明: 封装对象结果的json结果
 * 创建日期: 2016年12月14日 下午3:20:05
 * 作者: xwb
 */
public class JSONObjResult {
    /**
     * 是否成功
     */
    private boolean isSuccess;

    /***
     * 新增、修改主鍵返回id
     */
    private String id;

    /**
     * 错误信息
     */
    private String errorMessage = "未知异常";
    /**
     * 返回数据
     */
    private Object data;

    public static JSONObject toJSONObj(Object o, boolean isSuccess, String errorMessage) {
        JSONObjResult jsonObjResult = new JSONObjResult();
        jsonObjResult.setSuccess(isSuccess);
        jsonObjResult.setErrorMessage(errorMessage);
        jsonObjResult.setData(JSONUtil.beanToMap(o));
        return JSON.parseObject(JSON.toJSONString(jsonObjResult, SerializerFeature.WriteMapNullValue));
    }

    public static JSONObject toJSONObj(RestMessage rest) {
        JSONObjResult jsonObjResult = new JSONObjResult();
        jsonObjResult.setSuccess(rest.isSuccess());
        jsonObjResult.setErrorMessage(rest.getMsg());
        jsonObjResult.setData(JSONUtil.beanToMap(rest.getData()));
        return JSON.parseObject(JSON.toJSONString(jsonObjResult, SerializerFeature.WriteMapNullValue));
    }

    /**
     * 设置错误信息 （默认isSuccess是false）
     *
     * @param error
     * @return
     */
    public static JSONObject toJSONObj(String error) {
        JSONObjResult jsonObjResult = new JSONObjResult();
        jsonObjResult.setSuccess(false);
        jsonObjResult.setErrorMessage(error);
        return JSON.parseObject(JSONObject.toJSONString(jsonObjResult, SerializerFeature.WriteMapNullValue));
    }


    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
