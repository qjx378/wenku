package com.izerofx.wenku.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.izerofx.framework.basic.common.domain.BaseEntity;

/**
 * 
 * 类名称：DocBaseInfo<br>
 * 类描述：文档基本信息<br>
 * 创建人：qinjiaxue<br>
 * 创建时间：2016年11月8日 上午10:12:50<br>
 * @version v1.0
 *
 */
@Entity
@Table(name = "doc_base_info")
public class DocBaseInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    private String title;

    /**
     * 摘要
     */
    private String summary;

    /**
     * 分类
     */
    private Integer type;

    /**
     * 浏览次数
     */
    private Integer viewCount;

    /**
     * 文件id
     */
    private String fileId;

    /**
     * 用户id
     */
    private String userId;

    /******** 以下字段不持久化 ********/
    /**
     * 封面缩略图路径
     */
    private String thumPath;

    /**
     * swf路径
     */
    private String swfPath;

    /**
     * 文件扩展名
     */
    private String ext;

    /**
     * 文档总页数
     */
    private Integer pages;

    /**
     * 文档转换状态 0-待转换 1-转换中 2-转换完成 3-转换失败，默认0
     */
    private int conState;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 
     * 创建一个新的实例 DocBaseInfo.
     */
    public DocBaseInfo() {
        super();
    }

    /**
     * 
     * 创建一个新的实例 DocBaseInfo.
     * @param title
     * @param summary
     * @param type
     * @param viewCount
     * @param fileId
     * @param userId
     */
    public DocBaseInfo(String title, String summary, Integer type, Integer viewCount, String fileId, String userId) {
        super();
        this.title = title;
        this.summary = summary;
        this.type = type;
        this.viewCount = viewCount;
        this.fileId = fileId;
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Column(columnDefinition = "INT default 1")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Column(columnDefinition = "INT default 0")
    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Transient
    public String getThumPath() {
        return thumPath;
    }

    public void setThumPath(String thumPath) {
        this.thumPath = thumPath;
    }

    @Transient
    public String getSwfPath() {
        return swfPath;
    }

    public void setSwfPath(String swfPath) {
        this.swfPath = swfPath;
    }

    @Transient
    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    @Transient
    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    @Transient
    public int getConState() {
        return conState;
    }

    public void setConState(int conState) {
        this.conState = conState;
    }

    @Transient
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

}
