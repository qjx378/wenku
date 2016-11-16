package com.izerofx.framework.basic.util;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * 
 * 类名称：StringUtils<br>
 * 类描述：字符串工具类, 继承org.apache.commons.lang3.StringUtils类<br>
 * 创建人：qinjiaxue<br>
 * 创建时间：2015-3-19 下午3:33:21<br>
 * @version v1.0
 * 
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    /**
     * 替换掉HTML标签方法
     */
    public static String replaceHtml(String html) {
        if (isBlank(html)) {
            return "";
        }
        // . 匹配除换行符以外的任意字符
        // + 重复一次或更多次
        // ? 重复零次或一次
        String regEx = "<.+?>";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(html);
        String s = m.replaceAll("");
        return s;
    }

    /**
     * 缩略字符串（不区分中英文字符）
     * @param str 目标字符串
     * @param length 截取长度
     * @return
     */
    public static String abbr(String str, int length) {
        if (str == null) {
            return "";
        }
        try {
            StringBuilder sb = new StringBuilder();
            int currentLength = 0;
            for (char c : replaceHtml(StringEscapeUtils.unescapeHtml4(str)).toCharArray()) {
                currentLength += String.valueOf(c).getBytes("GBK").length;
                if (currentLength <= length - 3) {
                    sb.append(c);
                } else {
                    sb.append("...");
                    break;
                }
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 缩略字符串（替换html）
     * @param str 目标字符串
     * @param length 截取长度
     * @return
     */
    public static String rabbr(String str, int length) {
        return abbr(replaceHtml(str), length);
    }

    // 将首字母改成小写
    public static String lowerFirst(String str) {
        if (StringUtils.isBlank(str)) {
            return "";
        } else {
            return str.substring(0, 1).toLowerCase() + str.substring(1);
        }
    }
    // 将首字母改成大写
    public static String upperFirst(String str) {
        if (StringUtils.isBlank(str)) {
            return "";
        } else {
            return str.substring(0, 1).toUpperCase() + str.substring(1);
        }
    }

    /**
     * 获取一个字符串UUID
     * @return
     */
    public static String getUUIDString() {
        String uuid = UUID.randomUUID().toString();
        // 去掉“-”符号
        return uuid.replace("-", "");
    }

    /**
     * 判断字符串是否为数字
     */
    public static boolean isNumeric(String str) {
        return NumberUtils.isDigits(str);
    }

    /**
     * 正向查找指定字符串
     */
    public static int indexOf(String str, String searchStr, boolean ignoreCase) {
        if (ignoreCase) {
            return StringUtils.indexOfIgnoreCase(str, searchStr);
        } else {
            return str.indexOf(searchStr);
        }
    }

    /**
     * 反向查找指定字符串
     */
    public static int lastIndexOf(String str, String searchStr, boolean ignoreCase) {
        if (ignoreCase) {
            return StringUtils.lastIndexOfIgnoreCase(str, searchStr);
        } else {
            return str.lastIndexOf(searchStr);
        }
    }

    /**
     * 将用分隔符分隔的字符串转换为list
     * 
     * @param str 字符串
     * @param separator 分隔符，如","
     * @return
     */
    public static List<String> splits(String str, String separator) {
        String[] arr = str.split(separator);
        return Arrays.asList(arr);
    }

    /**
     * 将列表List<String>,转成字符串逗号隔开,前后带"'"
     * 
     * @param list
     * @return
     */
    public static String join(List<String> list) {
        String str = "";
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) {
                str += ",";
            }
            str += "'" + list.get(i) + "'";
        }
        return str;
    }

    /**
     * 将列表List<String>,转成字符串逗号隔开
     * 
     * @param list
     * @return
     */
    public static String join(List<String> list, String separator) {
        String str = "";
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) {
                str += separator;
            }
            str += list.get(i);
        }
        return str;
    }

}
