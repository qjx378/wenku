package com.izerofx.wenku.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.izerofx.framework.basic.common.controller.BaseController;
import com.izerofx.framework.basic.util.CodecUtil;
import com.izerofx.framework.basic.util.StringUtils;
import com.izerofx.framework.core.util.PasswordHelper;
import com.izerofx.wenku.domain.User;
import com.izerofx.wenku.service.UserService;

/**
 * 
 * 类名称：LoginController<br>
 * 类描述：登录、注册页面控制<br>
 * 创建人：qinjiaxue<br>
 * 创建时间：2016年11月7日 上午10:51:42<br>
 * @version v1.0
 *
 */
@Controller
@RequestMapping
public class LoginController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * 登录页面模版名
     */
    private static final String LOGIN_VIEW = "user/login";

    /**
     * 注册页面模版名
     */
    private static final String REG_VIEW = "user/reg";

    /**
     * 登录页面
     * @param model
     * @return
     */
    @RequestMapping(value = { "/login", "/login/" }, method = RequestMethod.GET)
    public String login(Model model) {
        User user = getUser();
        if (user != null && StringUtils.isNotBlank(user.getId())) {
            return redirectTo("/");
        }
        return LOGIN_VIEW;
    }

    /**
     * 登录接口
     * @param email
     * @param password
     * @return
     */
    @RequestMapping(value = { "/login", "/login/" }, method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> login(String email, String password) {

        Map<String, Object> result = new HashMap<>();

        User user = userService.findByEmail(email);
        if (user != null) {
            if (PasswordHelper.checkPwd(CodecUtil.encryptMD5(password), user.getPassword(), user.getPwdSalt())) {

                // 设置用户到session
                setUser(user);

                result.put("status", 0);
                result.put("msg", "登录成功");

                String redirectUrl = request.getParameter("redirect");
                result.put("action", StringUtils.isNotBlank(redirectUrl) ? redirectUrl : getContextPath() + "/");
            } else {
                result.put("status", 1);
                result.put("msg", "邮箱或密码错误");
            }
        } else {
            result.put("status", 1);
            result.put("msg", "邮箱或密码错误");
        }

        return result;
    }

    /**
     * 注册页面
     * @param model
     * @return
     */
    @RequestMapping(value = { "/reg", "/reg/" }, method = RequestMethod.GET)
    public String reg(Model model) {
        return REG_VIEW;
    }

    /**
     * 注册接口
     * @param user
     * @return
     */
    @RequestMapping(value = { "/reg", "/reg/" }, method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> reg(User user) {

        Map<String, Object> result = new HashMap<>();

        if (userService.isExistByEmail(user.getEmail())) {
            result.put("status", 1);
            result.put("msg", "该邮箱已被注册");
        } else {
            if (user != null && StringUtils.isNotBlank(user.getPassword())) {
                user.setPwdSalt(CodecUtil.createUUID());
                user.setPassword(PasswordHelper.initPassword(CodecUtil.encryptMD5(user.getPassword()), user.getPwdSalt()));
                user.setCreateTime(new Date());
            }

            userService.save(user);
            if (StringUtils.isNotBlank(user.getId())) {
                result.put("status", 0);
                result.put("msg", "注册成功");
                result.put("action", getContextPath() + "/login");
            } else {
                result.put("status", 1);
                result.put("msg", "注册失败");
            }
        }

        return result;
    }

    /**
     * 退出
     * @param model
     * @return
     */
    @RequestMapping(value = { "/logout", "/logout/" }, method = RequestMethod.GET)
    public String logout(Model model) {
        removeUser();
        return redirectTo("/");
    }
}
