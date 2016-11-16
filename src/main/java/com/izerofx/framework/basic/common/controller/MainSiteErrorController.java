package com.izerofx.framework.basic.common.controller;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * 类名称：MainSiteErrorController<br>
 * 类描述：网站错误页面控制<br>
 * 创建人：qinjiaxue<br>
 * 创建时间：2016年11月7日 下午3:43:39<br>
 * @version v1.0
 *
 */
@Controller
public class MainSiteErrorController implements ErrorController {

    private static final String ERROR_PATH = "/error";

    private static final String ERROR_VIEW = "error/404";

    /**
     * 404页面
     * @return
     */
    @RequestMapping(value = "/error")
    public String handleError() {
        return ERROR_VIEW;
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

}
