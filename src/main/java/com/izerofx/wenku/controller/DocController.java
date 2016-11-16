package com.izerofx.wenku.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.zbus.net.http.Message;

import com.alibaba.fastjson.JSON;
import com.izerofx.framework.basic.common.controller.BaseController;
import com.izerofx.framework.basic.util.StringUtils;
import com.izerofx.wenku.domain.DocBaseInfo;
import com.izerofx.wenku.domain.DocFileInfo;
import com.izerofx.wenku.service.DocBaseInfoService;
import com.izerofx.wenku.service.DocFileInfoService;
import com.izerofx.wenku.service.FileService;
import com.izerofx.wenku.service.ProducerService;

/**
 * 
 * 类名称：DocController<br>
 * 类描述：文档页面控制<br>
 * 创建人：qinjiaxue<br>
 * 创建时间：2016年11月7日 上午11:11:58<br>
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/doc")
public class DocController extends BaseController {

    @Autowired
    private DocBaseInfoService docBaseInfoService;

    @Autowired
    private DocFileInfoService docFileInfoService;

    @Autowired
    private FileService fileService;

    @Autowired
    private ProducerService producerService;

    /**
     * 文档首页模版名
     */
    private static final String DOC_INDEX_VIEW = "doc/index";

    /**
     * 阅读文档页面模版名
     */
    private static final String DOC_READ_VIEW = "doc/view";

    /**
     * 文档上传页面模版名
     */
    private static final String DOC_UPLOAD_VIEW = "doc/upload";

    /**
     * 文档首页
     * @param model
     * @return
     */
    @RequestMapping(value = { "", "/" }, method = RequestMethod.GET)
    public String index(Model model) {
        DocBaseInfo docBaseInfo = new DocBaseInfo();
        docBaseInfo.setConState(2);//只查询转换完成的

        Page<DocBaseInfo> docs = docBaseInfoService.findAll(docBaseInfo, initPage(1, 10));
        
        model.addAttribute("list", docs.getContent());
        
        return DOC_INDEX_VIEW;
    }

    /**
     * 阅读文档
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = { "/P-{id}", "/P-{id}/" }, method = RequestMethod.GET)
    public String view(@PathVariable String id, Model model) {
        DocBaseInfo docBaseInfo = docBaseInfoService.findById(id);

        //找不到就说找不到吧
        if (docBaseInfo == null) {
            return redirectTo("/404");
        }

        model.addAttribute("docBaseInfo", docBaseInfo);

        return DOC_READ_VIEW;
    }

    /**
     * 文档上传页面
     * @param model
     * @return
     */
    @RequestMapping(value = { "/upload", "/upload/" }, method = RequestMethod.GET)
    public String upload(Model model) {
        return DOC_UPLOAD_VIEW;
    }

    /**
     * 文档首页列表
     * @param pageNo
     * @return
     */
    @RequestMapping(value = { "/list", "/list/" }, method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> upload(@RequestParam(name = "p", defaultValue = "1", required = false) Integer pageNo) {
        Map<String, Object> result = new HashMap<>();

        DocBaseInfo docBaseInfo = new DocBaseInfo();
        docBaseInfo.setConState(2);//只查询转换完成的

        Page<DocBaseInfo> docs = docBaseInfoService.findAll(docBaseInfo, initPage(pageNo, 10));

        result.put("status", 0);
        result.put("list", docs.getContent());
        result.put("total", docs.getTotalElements());
        result.put("pages", docs.getTotalPages());

        return result;
    }

    /**
     * 文件上传
     * @param file
     * @return
     */
    @RequestMapping(value = { "/upload", "/upload/" }, method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> upload(@RequestParam("file") MultipartFile file) {
        Map<String, Object> result = new HashMap<>();
        try {

            //判断文件是否已存在
            if (docFileInfoService.isExistByHash(DigestUtils.md5Hex(file.getInputStream()))) {
                result.put("status", 1);
                result.put("msg", "文件已存在");
                result.put("fileInfo", null);
            } else {

                //保存文件
                DocFileInfo docFileInfo = fileService.uploadDoc(file);

                docFileInfo.setUserId(getUser().getId());

                //保存文件到数据库
                docFileInfoService.save(docFileInfo);

                //把文件信息丢到mq队列，由转换服务进行转换
                producerService.sendAsync(new Message().setJsonBody(JSON.toJSONString(docFileInfo)));

                result.put("status", 0);
                result.put("msg", "上传成功");
                result.put("fileInfo", docFileInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 删除上传文件
     * @param id
     * @return
     */
    @RequestMapping(value = { "/delfile", "/delfile/" }, method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delUploadFile(String id) {
        Map<String, Object> result = new HashMap<>();
        try {

            docFileInfoService.deleteByUserIdAndId(getUser().getId(), id);

            result.put("status", 0);
            result.put("msg", "删除成功");
        } catch (Exception e) {
            result.put("status", 1);
            result.put("msg", "删除失败，请稍候重试");
        }

        return result;
    }

    /**
     * 保存文档基本信息
     * @param docBaseInfo
     * @return
     */
    @RequestMapping(value = { "/save", "/save/" }, method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> save(DocBaseInfo docBaseInfo) {
        Map<String, Object> result = new HashMap<>();
        try {
            if (docBaseInfo != null && StringUtils.isNotBlank(docBaseInfo.getFileId())) {
                docBaseInfo.setUserId(getUser().getId());
                docBaseInfo.setViewCount(0);
                docBaseInfo.setCreateTime(new Date());
                docBaseInfoService.save(docBaseInfo);
            }

            if (StringUtils.isNotBlank(docBaseInfo.getId())) {
                result.put("status", 0);
                result.put("msg", "保存成功");
                result.put("id", docBaseInfo.getId());
            } else {
                result.put("status", 1);
                result.put("msg", "保存失败");
                result.put("id", "");
            }
        } catch (Exception e) {
            result.put("status", 1);
            result.put("msg", "服务器异常，请稍候重试");
        }

        return result;
    }
}
