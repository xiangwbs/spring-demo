package com.xwbing.handler;

import com.xwbing.util.CommonConstant;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * 说明:  登录拦截器
 * 项目名称: spring-demo
 * 创建时间: 2017/5/3 10:27
 * 作者:  xiangwb
 */

public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean flag = true;
        HttpSession session = request.getSession();
        Object obj=session.getAttribute(CommonConstant.SESSION_CURRENT_USER);
        if (Objects.isNull(obj)) {
//            flag = false;
            //未登录，重定向到登录页面
//            response.sendRedirect(request.getContextPath() + "/usere/login");
        }
        return flag;
    }

}
