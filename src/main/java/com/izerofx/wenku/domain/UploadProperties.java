package com.izerofx.wenku.domain;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 
 * 类名称：UploadProperties<br>
 * 类描述：文件上传配置属性<br>
 * 创建人：qinjiaxue<br>
 * 创建时间：2016年11月7日 上午8:59:23<br>
 * @version v1.0
 *
 */
@Component
@ConfigurationProperties("izerofx.config.upload")
public class UploadProperties {

    /**
     * 系统类型 0-win, 1-linux 默认0
     */
    private Integer osType = 0;

    /**
     * 上传路径
     */
    private String path;

    /**
     * 公共路径
     */
    private String publicPath;

    public Integer getOsType() {
        return osType;
    }

    public void setOsType(Integer osType) {
        this.osType = osType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPublicPath() {
        return publicPath;
    }

    public void setPublicPath(String publicPath) {
        this.publicPath = publicPath;
    }
}
