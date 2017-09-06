package com.xwbing.util.payWxpay;


import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by drore-wzm on 2015/11/23.
 */
public class IPKit {
    public static String getIp2(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }

    public static String getIPAddress(HttpServletRequest request) {

        // 获取X-Forwarded-For

        String ip = request.getHeader("x-forwarded-for");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

            // 获取Proxy-Client-IP

            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            // WL-Proxy-Client-IP
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            // 获取的IP实际上是代理服务器的地址，并不是客户端的IP地址
            ip = request.getRemoteAddr();
        }
		/*
		 *
		 * 如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值
		 *
		 * X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130,
		 * 192.168.1.100
		 *
		 * 用户真实IP为： 192.168.1.110
		 */

        if (ip.contains(",")) {

            ip = ip.split(",")[0];

        }
        return ip;

    }
}
