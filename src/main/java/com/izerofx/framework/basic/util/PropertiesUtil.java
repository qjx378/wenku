package com.izerofx.framework.basic.util;

import java.io.UnsupportedEncodingException;

/**
 * 
 * 类名称：PropertiesUtil<br>
 * 类描述：系统属性文件工具类. <br>
 * 此工具用于读取系统属性文件system-config.properties,并将所有属性存在内存中,读取属性值, 直接调用方法{@link #getValue(String)}.<br>
 * 另外用户还可指定某个属性文件,并从中取值,调用方法 {@link #getValue(String, String)}.<br>
 * 所有返回的字符串,都将转换成UTF-8编码.<br>
 * 创建人：qinjiaxue<br>
 * 创建时间：2016年4月6日 下午3:11:59<br>
 * @version v1.0
 *
 */
public abstract class PropertiesUtil {

    /**
     * 取属性文件中的某项值
     * @param key
     * @return String
     */
    public static String getValue(String key) {
        return (String) SystemResourceLocator.getValue(key);
    }

    /**
     * 取属性文件中的某项值
     * @param key
     * @return Object
     */
    public static Object getObjectValue(String key) {
        return SystemResourceLocator.getValue(key);
    }

    /**
     * iso转utf-8
     * @param str
     * @return
     */
    public static final String isoToUTF(String str) {
        String str1 = null;
        try {
            str1 = new String(str.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str1;
    }
}
