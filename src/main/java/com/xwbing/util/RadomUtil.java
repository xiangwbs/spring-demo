package com.xwbing.util;

import java.util.Random;

/**
 * 说明:生成随机码< <br/>
 * 创建日期: 2016年11月29日 上午10:28:21 <br/>
 * 作者: xwb
 */
public class RadomUtil {

    public static String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String buildRandom(int length) {
        if (length < 1)
            throw new RuntimeException("参数异常!!!");
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(str.length());
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        while (true)
            System.out.println(buildRandom(8));
    }

    
}
