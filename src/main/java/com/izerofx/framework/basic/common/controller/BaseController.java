package com.izerofx.framework.basic.common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.izerofx.framework.basic.util.StringEscapeEditor;
import com.izerofx.wenku.domain.User;

/**
 * 
 * 类名称：BaseController<br>
 * 类描述： Controller基类<br>
 * 创建人：qinjiaxue<br>
 * 创建时间：2015年12月15日 上午9:31:45<br>
 * @version v1.0
 *
 */
public abstract class BaseController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;

    /**
     * 绑定字符过滤编辑器
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringEscapeEditor(true));// html转义
    }
    
    /**
     * 初始化分页查询参数Page
     * 
     * @param pageNo
     * @param pageSize
     * @return
     */
    protected Pageable initPage(Integer pageNo, Integer pageSize) {
        // @RequestParam在处理不存在的参数时，赋值为null，基本类型无法为null，改为包装类型
        if (pageNo == null) {
            pageNo = 0;
        }
        if (pageSize == null) {
            pageSize = 0;
        }
        pageNo = pageNo == 0 ? 0 : pageNo - 1;
        pageSize = pageSize == 0 ? 10 : pageSize;
        Pageable page = new PageRequest(pageNo, pageSize);
        return page;
    }

    /**
     * 初始化分页查询参数Page
     * 
     * @param pageNo
     * @param pageSize
     * @param direction
     * @param properties
     * @return
     */
    protected Pageable initPage(Integer pageNo, Integer pageSize, Direction direction, String... properties) {
        if (pageNo == null) {
            pageNo = 0;
        }
        if (pageSize == null) {
            pageSize = 0;
        }
        pageNo = pageNo == 0 ? 0 : pageNo - 1;
        pageSize = pageSize == 0 ? 10 : pageSize;
        Pageable page = new PageRequest(pageNo, pageSize, direction, properties);
        return page;
    }

    /**
     * 重定向到指定url
     * @param url
     * @return
     */
    protected String redirectTo(String url) {
        StringBuilder rto = new StringBuilder("redirect:");
        rto.append(url);
        return rto.toString();
    }

    /**
     * 获取项目根路径
     * @return
     */
    protected String getContextPath() {
        return request.getContextPath();
    }

    /**
     * 设置用户
     * @param user
     */
    protected void setUser(User user) {
        request.getSession().setAttribute("user", user);
    }

    /**
     * 获取用户
     * @return
     */
    protected User getUser() {
        User user = (User) request.getSession().getAttribute("user");
        return user;
    }

    /**
     * 移动用户
     */
    protected void removeUser() {
        request.getSession().removeAttribute("user");
        request.getSession().invalidate();
    }
}
