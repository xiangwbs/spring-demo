package com.xwbing.util;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 说明:获取登录ip工具类
 * 创建日期: 2016年11月29日 上午10:30:20 
 * 作者: xwb
 */

public class IpUtil {
    public static String getIpAddr(HttpServletRequest request) {
        // 获取X-Forwarded-For
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0
                || "unknown".equalsIgnoreCase(ip)) {
            // 获取Proxy-Client-IP
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0
                || "unknown".equalsIgnoreCase(ip)) {
            // WL-Proxy-Client-IP
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0
                || "unknown".equalsIgnoreCase(ip)) {
            // 获取的IP实际上是代理服务器的地址，并不是客户端的IP地址
            ip = request.getRemoteAddr();
            if (ip.equals("127.0.0.1")
                    || ip.equals("0:0:0:0:0:0:0:1")) {
                // 根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ip = inet.getHostAddress();
            }
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ip != null && ip.length() > 15) { // "***.***.***.***".length()
                                                            // = 15
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        return ip;
    }
}
