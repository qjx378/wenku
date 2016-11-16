package com.izerofx.framework.core.util;

import org.apache.commons.codec.binary.Base64;

import com.izerofx.framework.basic.util.CodecUtil;
import com.izerofx.framework.basic.util.StringUtils;

/**
 * 
 * 类名称：PasswordUtil<br>
 * 类描述：账户密码工具类<br>
 * 创建人：qinjiaxue<br>
 * 创建时间：2016年4月21日 下午12:48:07<br>
 * @version v1.0
 *
 */
public class PasswordHelper {
    
    private static final String DEFAULT_PUBLIC_SALT = "9BDFBEC9F7324BF2B047BC4089BC9151";

    /**
     * 初始化密码
     * @param md5Pwd md5加密的密码
     * @param salt
     * @return
     */
    public static String initPassword(String md5Pwd, String salt) {
        // 对hash过的密码掺入盐值再次hash
        return encryptPassword(md5Pwd, salt);
    }

    /**
     * 通过MD5加密密码
     * @param plainPwd
     * @return
     */
    public static String encryptPassword(String plainPwd) {
        return CodecUtil.encryptMD5(plainPwd);
    }

    /**
     * 通过MD5加密 密码+盐
     * @param pwd 密码
     * @param salt 密码盐
     * @return
     */
    public static String encryptPassword(String pwd, String salt) {
        // 使用MD5对密码和盐值hash
        return Base64.encodeBase64String(CodecUtil.encryptMD5(pwd + salt + DEFAULT_PUBLIC_SALT).getBytes());
    }

    /**
     * 生成随机私盐值
     * @return
     */
    public static String generateSalt() {
        return CodecUtil.encryptMD5(CodecUtil.createUUID());
    }

    /**
     * 校验密码是否正确
     * @param pwd 经过md5加密的密码
     * @param dbPwd 数据库用户密码
     * @param salt 用户密码私盐
     * @return
     */
    public static boolean checkPwd(String pwd, String dbPwd, String salt) {
        boolean result = false;
        if (StringUtils.isNotBlank(pwd)) {
            if (dbPwd.equals(encryptPassword(pwd, salt))) {
                result = true;
            }
        }
        return result;
    }
}
