package com.izerofx.wenku.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.izerofx.framework.basic.util.PropertiesUtil;
import com.izerofx.wenku.domain.User;

/**
 * 
 * 类名称：UserSecurityInterceptor<br>
 * 类描述：用户安全拦截器<br>
 * 创建人：qinjiaxue<br>
 * 创建时间：2016年11月7日 上午10:40:46<br>
 * @version v1.0
 *
 */
public class UserSecurityInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 验证用户是否登陆
        Object obj = request.getSession().getAttribute("user");
        if (obj == null || !(obj instanceof User)) {
            String url = PropertiesUtil.getValue("izerofx.config.domain") + "/login";
            String requestURI = request.getRequestURI();
            url = url + "?redirect=" + requestURI;
            response.sendRedirect(url);
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

}
