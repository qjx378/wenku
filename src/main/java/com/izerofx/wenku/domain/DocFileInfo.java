package com.izerofx.wenku.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.izerofx.framework.basic.common.domain.BaseEntity;

/**
 * 
 * 类名称：FileInfo<br>
 * 类描述：文件信息<br>
 * 创建人：qinjiaxue<br>
 * 创建时间：2016年11月8日 上午10:28:49<br>
 * @version v1.0
 *
 */
@Entity
@Table(name = "doc_file_info")
public class DocFileInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;
    
    /**
     * 文件名称
     */
    private String name;

    /**
     * 文件hash
     */
    private String hash;

    /**
     * 文件保存路径
     */
    private String path;

    /**
     * 文件pdf路径
     */
    private String pdfPath;

    /**
     * 文件swf路径
     */
    private String swfPath;

    /**
     * 封面缩略图路径
     */
    private String thumPath;

    /**
     * 文件扩展名
     */
    private String ext;

    /**
     * 文件大小(字节)
     */
    private Long size;
    
    /**
     * 文档总页数
     */
    private Integer pages;

    /**
     * 文档转换状态 0-待转换 1-转换中 2-转换完成 3-转换失败，默认0
     */
    private int conState;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 
     * 创建一个新的实例 DocFileInfo.
     */
    public DocFileInfo() {
        super();
    }

    /**
     * 
     * 创建一个新的实例 DocFileInfo.
     * @param hash
     * @param path
     * @param pdfPath
     * @param swfPath
     * @param thumPath
     * @param ext
     * @param size
     * @param conState
     */
    public DocFileInfo(String hash, String path, String pdfPath, String swfPath, String thumPath, String ext, Long size, int conState) {
        super();
        this.hash = hash;
        this.path = path;
        this.pdfPath = pdfPath;
        this.swfPath = swfPath;
        this.thumPath = thumPath;
        this.ext = ext;
        this.size = size;
        this.conState = conState;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPdfPath() {
        return pdfPath;
    }

    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath;
    }

    public String getSwfPath() {
        return swfPath;
    }

    public void setSwfPath(String swfPath) {
        this.swfPath = swfPath;
    }

    public String getThumPath() {
        return thumPath;
    }

    public void setThumPath(String thumPath) {
        this.thumPath = thumPath;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    @Column(nullable = false, columnDefinition = "INT default 0")
    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public int getConState() {
        return conState;
    }

    public void setConState(int conState) {
        this.conState = conState;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
