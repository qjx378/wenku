package com.izerofx.wenku.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.izerofx.framework.basic.common.domain.BaseEntity;

/**
 * 
 * 类名称：User<br>
 * 类描述：用户实体类<br>
 * 创建人：qinjiaxue<br>
 * 创建时间：2016年11月3日 下午2:58:42<br>
 * @version v1.0
 *
 */
@Entity
@Table(name = "user_info")
public class User extends BaseEntity {

    /**
     * serialVersionUID
     * @since Ver 1.1
     */
    private static final long serialVersionUID = 1L;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 性别 0-女 1-男，默认1
     */
    private Integer sex;

    /**
     * 密码
     */
    private String password;

    /**
     * 密码盐
     */
    private String pwdSalt;

    /**
     * 城市
     */
    private String city;

    /**
     * 个性签名
     */
    private String sign;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 
     * 创建一个新的实例 User.
     */
    public User() {
        super();
    }

    /**
     * 
     * 创建一个新的实例 User.     
     * @param email
     * @param nickName
     * @param sex
     * @param password
     * @param pwdSalt
     * @param city
     * @param sign
     */
    public User(String email, String nickName, Integer sex, String password, String pwdSalt, String city, String sign) {
        super();
        this.email = email;
        this.nickName = nickName;
        this.sex = sex;
        this.password = password;
        this.pwdSalt = pwdSalt;
        this.city = city;
        this.sign = sign;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Column(nullable = false, columnDefinition = "INT default 1")
    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPwdSalt() {
        return pwdSalt;
    }

    public void setPwdSalt(String pwdSalt) {
        this.pwdSalt = pwdSalt;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

}
