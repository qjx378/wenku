package com.izerofx.wenku.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.izerofx.framework.basic.common.controller.BaseController;
import com.izerofx.framework.basic.util.CodecUtil;
import com.izerofx.framework.basic.util.StringUtils;
import com.izerofx.framework.core.util.PasswordHelper;
import com.izerofx.wenku.domain.DocBaseInfo;
import com.izerofx.wenku.domain.User;
import com.izerofx.wenku.service.DocBaseInfoService;
import com.izerofx.wenku.service.FileService;
import com.izerofx.wenku.service.UserService;
import com.izerofx.wenku.service.VisitorService;

/**
 * 
 * 类名称：UserController<br>
 * 类描述：用户页面控制<br>
 * 创建人：qinjiaxue<br>
 * 创建时间：2016年11月3日 下午5:09:40<br>
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private VisitorService visitorService;

    @Autowired
    private FileService fileService;

    @Autowired
    private DocBaseInfoService docBaseInfoService;

    /**
     * 用户中心
     * @return
     */
    @RequestMapping(value = { "", "/" }, method = RequestMethod.GET)
    public String index(Model model) {

        model.addAttribute("visitor_list", visitorService.findByUserId(getUser().getId(), 10));

        return "user/index";
    }

    /**
     * 用户首页
     * @return
     */
    @RequestMapping(value = { "/u/{uId}", "/u/{uId}/" }, method = RequestMethod.GET)
    public String home(@PathVariable String uId, Model model) {
        User user = userService.getById(uId);
        if (user == null) {
            return redirectTo("/404");
        }
        model.addAttribute("user", user);

        return "user/home";
    }

    /**
     * 用户设置
     * @param model
     * @return
     */
    @RequestMapping(value = { "/set", "/set/" }, method = RequestMethod.GET)
    public String userSet(Model model) {
        User user = getUser();
        if (user == null || StringUtils.isBlank(user.getId())) {
            return redirectTo("/user/login");
        }
        model.addAttribute("user", user);
        return "user/set";
    }

    /**
     * 用户设置
     * @param model
     * @return
     */
    @RequestMapping(value = { "/set", "/set/" }, method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> userSet(String nickName, Integer sex, String city, String sign, String avatar) {
        Map<String, Object> result = new HashMap<>();
        User user = getUser();

        if (StringUtils.isNotBlank(avatar)) {
            user.setAvatar(avatar);
        } else {
            user.setNickName(nickName);
            user.setSex(sex);
            user.setCity(city);
            user.setSign(sign);
        }

        userService.save(user);
        setUser(user);

        result.put("status", 0);
        result.put("msg", "修改成功");

        return result;
    }

    /**
     * 头像上传
     * @param model
     * @return
     */
    @RequestMapping(value = { "/upload", "/upload/" }, method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> upload(@RequestParam("file") MultipartFile file) {
        Map<String, Object> result = new HashMap<>();
        if (!file.isEmpty()) {
            try {
                String fullPath = fileService.uploadHead(file, 128, 128);

                Map<String, Object> data = new HashMap<>();
                data.put("url", fullPath);

                result.put("code", 0);
                result.put("msg", "上传成功");
                result.put("url", fullPath);
            } catch (Exception e) {
                result.put("code", 1);
                result.put("msg", "上传失败");
            }
        } else {
            result.put("code", 1);
            result.put("msg", "上传失败，因为文件是空的");
        }
        return result;
    }
    /**
     * 修改密码
     * @param model
     * @return
     */
    @RequestMapping(value = { "/repass", "/repass/" }, method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> repass(String nowpass, String pass) {
        Map<String, Object> result = new HashMap<>();
        User user = getUser();

        if (PasswordHelper.checkPwd(CodecUtil.encryptMD5(nowpass), user.getPassword(), user.getPwdSalt())) {

            user.setPwdSalt(CodecUtil.createUUID());
            user.setPassword(PasswordHelper.initPassword(CodecUtil.encryptMD5(pass), user.getPwdSalt()));

            userService.save(user);

            result.put("status", 0);
            result.put("msg", "修改成功");
        } else {
            result.put("status", 1);
            result.put("msg", "旧密码输入错误");
        }

        return result;
    }

    /**
     * 获取我分享的文档
     * @return
     */
    @RequestMapping(value = { "/doc_list", "/doc_list/" }, method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> docList(@RequestParam(name = "u") String userId, @RequestParam(name = "p", defaultValue = "1", required = false) Integer pageNo) {
        Map<String, Object> result = new HashMap<>();

        Pageable pageable = initPage(pageNo, 15);

        Page<DocBaseInfo> docs = docBaseInfoService.findAllByUserId(userId, pageable);

        result.put("status", 0);
        result.put("list", docs.getContent());
        result.put("total", docs.getTotalElements());
        result.put("pages", docs.getTotalPages());

        return result;
    }
}
