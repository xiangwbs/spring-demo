package com.xwbing.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.beanutils.PropertyUtilsBean;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 说明: json格式转换
 * 创建日期: 2016年8月16日 下午8:06:01
 * 作者: xiangwb
 */
public class JSONUtil {

    /**
     * 实体对象不序列化转换成jsonobject
     *
     * @param obj
     * @return
     */
    public static Object beanToMap(Object obj) {
        if (obj == null)
            return null;
        if (obj instanceof ArrayList) {
            ArrayList<?> list = (ArrayList<?>) obj;
            ArrayList<JSONObject> result = new ArrayList<JSONObject>();
            for (Object o : list) {
                JSONObject javaObject = (JSONObject) beanToMap(o);
                result.add(javaObject);
            }
            return result;
        } else if (obj instanceof JSONObject) {
            return obj;
        } else if (obj instanceof Map) {
            return obj;
        } else {
            Map<String, Object> params = new HashMap<String, Object>(0);
            try {
                PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
                PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj);
                for (int i = 0; i < descriptors.length; i++) {
                    String name = descriptors[i].getName();
                    if (!"class".equals(name)) {
                        params.put(name, propertyUtilsBean.getNestedProperty(obj, name));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new JSONObject(params);
        }
    }
}
