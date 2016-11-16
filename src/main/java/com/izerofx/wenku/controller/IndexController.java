package com.izerofx.wenku.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.izerofx.framework.basic.common.controller.BaseController;

/**
 * 
 * 类名称：IndexController<br>
 * 类描述：首页页面控制<br>
 * 创建人：qinjiaxue<br>
 * 创建时间：2016年11月3日 下午3:18:55<br>
 * @version v1.0
 *
 */
@Controller
public class IndexController extends BaseController {
    
    /**
     * 首页视图名称
     */
    private static final String INDEX_VIEW = "index";

    /**
     * 首页
     * @param model
     * @return
     */
    @RequestMapping(value = { "", "/" }, method = RequestMethod.GET)
    public String index(Model model) {
        return INDEX_VIEW;
    }
}
