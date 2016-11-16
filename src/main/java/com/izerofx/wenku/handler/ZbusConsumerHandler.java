package com.izerofx.wenku.handler;

import java.io.IOException;

import org.icepdf.core.exceptions.PDFException;
import org.icepdf.core.exceptions.PDFSecurityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zbus.broker.Broker;
import org.zbus.mq.Consumer;
import org.zbus.mq.Consumer.ConsumerHandler;
import org.zbus.net.http.Message;

import com.alibaba.fastjson.JSON;
import com.izerofx.framework.basic.util.StringUtils;
import com.izerofx.framework.basic.util.SystemResourceLocator;
import com.izerofx.framework.core.util.FileUtils;
import com.izerofx.wenku.domain.DocFileInfo;
import com.izerofx.wenku.domain.UploadProperties;
import com.izerofx.wenku.service.DocFileInfoService;
import com.izerofx.wenku.util.FileExtConstant;

/**
 * 
 * 类名称：ZbusConsumerHandler<br>
 * 类描述：zbus消息队列消费处理<br>
 * 创建人：qinjiaxue<br>
 * 创建时间：2016年11月9日 上午11:39:51<br>
 * @version v1.0
 *
 */
@Component
public class ZbusConsumerHandler implements ConsumerHandler {

    private static Logger logger = LoggerFactory.getLogger(ZbusConsumerHandler.class);

    @Autowired
    private Broker broker;

    @Override
    public void handle(Message msg, Consumer consumer) throws IOException {
        DocFileInfo docFileInfo = JSON.parseObject(msg.getBodyString(), DocFileInfo.class);
        if (docFileInfo != null && StringUtils.isNotBlank(docFileInfo.getPath())) {
            //转换文件
            converFile(docFileInfo);

            //更新文档信息
            update(docFileInfo);
        }
    }

    /**
     * 转换文件
     * @param docFileInfo
     */
    private void converFile(DocFileInfo docFileInfo) {

        boolean conResult = true;

        FileConverHandler fileConverHandler = (FileConverHandler) SystemResourceLocator.getBean("fileConverHandler", FileConverHandler.class);

        //转换pdf
        if (!FileExtConstant.FILETYPE_PDF.equalsIgnoreCase(docFileInfo.getExt())) {
            try {
                docFileInfo.setPdfPath("upload" + FileUtils.getFileUriGeneratedPart(StringUtils.getUUIDString(), FileExtConstant.FILETYPE_PDF));
                fileConverHandler.office2PDF(getRootPath() + docFileInfo.getPath(), getRootPath() + docFileInfo.getPdfPath());
            } catch (IOException e) {
                logger.error("转换pdf发生异常", e);
                conResult = false;
                docFileInfo.setConState(3);
                docFileInfo.setPdfPath(null);
            }
        }

        if (conResult) {

            try {
                //生成首页缩略图
                docFileInfo.setThumPath("upload" + FileUtils.getFileUriGeneratedPart(StringUtils.getUUIDString(), FileExtConstant.FILETYPE_PNG));
                fileConverHandler.tranfer(getRootPath() + docFileInfo.getPdfPath(), getRootPath() + docFileInfo.getThumPath(), 1);

                //获取pdf总页数
                int pages = fileConverHandler.getPdfPages(getRootPath() + docFileInfo.getPdfPath());
                docFileInfo.setPages(pages);

                //pdf转换swf
                String swfPath = FileUtils.getFileUriGeneratedPart(StringUtils.getUUIDString(), FileExtConstant.FILETYPE_SWF);
                docFileInfo.setSwfPath("upload" + swfPath.substring(0, swfPath.lastIndexOf("/")));
                fileConverHandler.pdf2swf(getRootPath() + docFileInfo.getPdfPath(), getRootPath() + docFileInfo.getSwfPath());

                docFileInfo.setConState(2);
            } catch (PDFException | PDFSecurityException | IOException e) {
                logger.error("转换swf发生异常", e);
                docFileInfo.setConState(3);
                docFileInfo.setThumPath(null);
                docFileInfo.setPages(0);
                docFileInfo.setSwfPath(null);
            }
        }
    }

    /**
     * 更新文档信息
     * @param docFileInfo
     */
    private void update(DocFileInfo docFileInfo) {
        DocFileInfoService docFileInfoService = (DocFileInfoService) SystemResourceLocator.getBean("docFileInfoService", DocFileInfoService.class);
        docFileInfoService.save(docFileInfo);
    }

    /**
     * 获取上传的根目录
     * @return
     */
    private String getRootPath() {
        UploadProperties uploadProperties = (UploadProperties) SystemResourceLocator.getBean("uploadProperties", UploadProperties.class);
        return uploadProperties.getPath().replace(uploadProperties.getPublicPath(), "");
    }

}
