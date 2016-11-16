package com.izerofx.wenku.service;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 
 * 类名称：WebappContext<br>
 * 类描述：office转换服务上下文<br>
 * 创建人：qinjiaxue<br>
 * 创建时间：2016年3月23日 下午1:23:55<br>
 * @version v1.0
 *
 */
@Component
public class OfficeConService {

    private final Logger logger = LoggerFactory.getLogger(OfficeConService.class);

    private String officePort;
    private String officeHome;

    private final OfficeManager officeManager;

    private final OfficeDocumentConverter documentConverter;

    /**
     * 
     * 创建一个新的实例 OfficeConService.
     */
    public OfficeConService() {

        DefaultOfficeManagerConfiguration configuration = new DefaultOfficeManagerConfiguration();

        //设置转换端口，默认为2002
        if (StringUtils.isNotBlank(this.officePort)) {
            configuration.setPortNumber(Integer.parseInt(this.officePort));
            logger.info("office转换服务监听端口设置为：" + this.officePort);
        }

        //设置office安装目录
        if (StringUtils.isNotBlank(officeHome)) {
            configuration.setOfficeHome(new File(this.officeHome));
            logger.info("设置office安装目录为：" + this.officeHome);
        }

        officeManager = configuration.buildOfficeManager();
        documentConverter = new OfficeDocumentConverter(this.officeManager);
    }

    /**
     * 
     * 创建一个新的实例 OfficeConService.
     * @param officePort
     * @param officeHome
     */
    public OfficeConService(String officePort, String officeHome) {
        this.officePort = officePort;
        this.officeHome = officeHome;

        DefaultOfficeManagerConfiguration configuration = new DefaultOfficeManagerConfiguration();

        //设置转换端口，默认为8100
        if (StringUtils.isNotBlank(this.officePort)) {
            configuration.setPortNumber(Integer.parseInt(this.officePort));
            logger.info("office转换服务监听端口设置为：" + this.officePort);
        }

        //设置office安装目录
        if (StringUtils.isNotBlank(this.officeHome)) {
            configuration.setOfficeHome(new File(this.officeHome));
            logger.info("设置office安装目录为：" + this.officeHome);
        }

        officeManager = configuration.buildOfficeManager();
        documentConverter = new OfficeDocumentConverter(officeManager);
    }

    protected void init() {
        officeManager.start();
    }

    protected void destroy() {
        officeManager.stop();
    }

    public String getOfficePort() {
        return officePort;
    }

    public void setOfficePort(String officePort) {
        this.officePort = officePort;
    }

    public String getOfficeHome() {
        return officeHome;
    }

    public void setOfficeHome(String officeHome) {
        this.officeHome = officeHome;
    }

    public OfficeManager getOfficeManager() {
        return officeManager;
    }

    public OfficeDocumentConverter getDocumentConverter() {
        return documentConverter;
    }

}
