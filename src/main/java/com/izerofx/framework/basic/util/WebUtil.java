package com.izerofx.framework.basic.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import eu.bitwalker.useragentutils.UserAgent;

/**
 * 
 * 类名称：WebUtil<br>
 * 类描述：Web工具类<br>
 * 创建人：qinjiaxue<br>
 * 创建时间：2016年11月3日 上午10:40:32<br>  
 * @version v1.0
 *
 */
public class WebUtil {

    /**
     * 获取访问者IP 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效。 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)， 如果还不存在则调用Request .getRemoteAddr()。
     * @param request
     * @return
     */
    public static String getRemoteAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 获取系统名称
     * @param request 系统名称字符串
     * @return
     */
    public static String getOSName(HttpServletRequest request) {
        String userAgentStr = request.getHeader("User-Agent");
        String result = "未知";
        UserAgent userAgent = UserAgent.parseUserAgentString(userAgentStr);
        result = userAgent.getOperatingSystem().getName();
        return result;
    }

    /**
     * 获取浏览器名称及版本号
     * @param request 浏览器名称及版本号字符串
     * @return
     */
    public static String getBrowserInfo(HttpServletRequest request) {
        String userAgentStr = request.getHeader("User-Agent");
        String result = "未知";
        UserAgent userAgent = UserAgent.parseUserAgentString(userAgentStr);
        result = removeNumberRegx(userAgent.getBrowser().getName()) + " " + userAgent.getBrowserVersion().getVersion();
        return result;
    }

    private static String removeNumberRegx(String str) {
        return Pattern.compile("[\\d]").matcher(str).replaceAll("");
    }

    /**
     * 判断ajax请求
     * @param request
     * @return
     */
    public static boolean isAjax(HttpServletRequest request) {
        return (request.getHeader("X-Requested-With") != null && "XMLHttpRequest".equals(request.getHeader("X-Requested-With").toString()));
    }

    /**
     * 检测是否是移动设备访问
     * @param request
     * @return
     */
    public static boolean checkIsMobile(HttpServletRequest request) {
        String userAgentStr = request.getHeader("User-Agent").toLowerCase();
        if (null == userAgentStr) {
            userAgentStr = "";
        }
        // \b 是单词边界(连着的两个(字母字符 与 非字母字符) 之间的逻辑上的间隔),    
        // 字符串在编译时会被转码一次,所以是 "\\b"    
        // \B 是单词内部逻辑间隔(连着的两个字母字符之间的逻辑上的间隔)    
        String phoneReg = "\\b(ip(hone|od)|android|opera m(ob|in)i" + "|windows (phone|ce)|blackberry" + "|s(ymbian|eries60|amsung)|p(laybook|alm|rofile/midp" + "|laystation portable)|nokia|fennec|htc[-_]" + "|mobile|up.browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";
        String tableReg = "\\b(ipad|tablet|(Nexus 7)|up.browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";
        // 匹配
        Matcher matcherPhone = Pattern.compile(phoneReg).matcher(userAgentStr);
        Matcher matcherTable = Pattern.compile(tableReg).matcher(userAgentStr);
        if (matcherPhone.find() || matcherTable.find()) {
            return true;
        } else {
            return false;
        }
    }
}
