package com.xwbing.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 说明: PropertiesUtil
 * 项目名称: spring-demo
 * 创建时间: 2017/5/12 16:50
 * 作者:  xiangwb
 */

public class PropertiesUtil {
    // 根据Key读取Value
    public static String getValueByKey(String key, String target) {
        Properties pps = new Properties();
        String filePath = PropertiesUtil.class.getClassLoader().getResource(target).getPath();
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(filePath));
            pps.load(in);
            String value = pps.getProperty(key);
            in.close();
            return value;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
