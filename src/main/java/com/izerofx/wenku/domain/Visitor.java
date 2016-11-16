package com.izerofx.wenku.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.izerofx.framework.basic.common.domain.BaseEntity;

/**
 * 
 * 类名称：Visitor<br>
 * 类描述：访客实体<br>
 * 创建人：qinjiaxue<br>
 * 创建时间：2016年11月7日 下午1:38:04<br>
 * @version v1.0
 *
 */
@Entity
@Table(name = "user_visitor")
public class Visitor extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 访客用户id
     */
    private String visitorUserId;

    /****** 以下字段不持久化 ******/
    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 
     * 创建一个新的实例 Visitor.
     */
    public Visitor() {
        super();
    }

    /**
     * 
     * 创建一个新的实例 Visitor.
     * @param userId
     * @param visitorUserId
     */
    public Visitor(String userId, String visitorUserId) {
        super();
        this.userId = userId;
        this.visitorUserId = visitorUserId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getVisitorUserId() {
        return visitorUserId;
    }

    public void setVisitorUserId(String visitorUserId) {
        this.visitorUserId = visitorUserId;
    }

    @Transient
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Transient
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
