package org.clever.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.clever.common.utils.exception.ExceptionUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-5-8 16:31 <br/>
 */
@Slf4j
public class CookieUtils {
    /**
     * Cookie的默认编码格式
     */
    private final static String DEFAULT_COOKIE_Encode = "UTF-8";

    /**
     * Cookie的默认路径，为根目录，其所有子目录都能访问
     */
    public final static String DEFAULT_COOKIE_PATH = "/";

    /**
     * 设置Cookie
     *
     * @param response HTTP请求
     * @param path     Cookie的Path
     * @param name     名称
     * @param value    值
     * @param maxAge   Cookie生存时间，单位：秒。负数表示Cookie永不过期，0表示删除Cookie
     */
    public static void setCookie(HttpServletResponse response, String path, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        if (StringUtils.isNotBlank(path)) {
            cookie.setPath(path);
        }
        if (maxAge >= 0) {
            cookie.setMaxAge(maxAge);
        }
        try {
            cookie.setValue(URLEncoder.encode(value, DEFAULT_COOKIE_Encode));
        } catch (Throwable e) {
            throw ExceptionUtils.unchecked(e);
        }
        response.addCookie(cookie);
    }

    /**
     * 设置Cookie
     *
     * @param name  名称
     * @param value 值
     */
    public static void setRooPathCookie(HttpServletResponse response, String name, String value) {
        setCookie(response, "/", name, value, -1);
    }

    /**
     * 设置Cookie
     *
     * @param name  名称
     * @param value 值
     */
    public static void setCookie(HttpServletResponse response, String name, String value) {
        setCookie(response, null, name, value, -1);
    }

    /**
     * 获得指定Cookie的值
     *
     * @param request  请求对象
     * @param response 响应对象，设置移除Cookie时(isRemove=true),此对象不能传null
     * @param name     名字
     * @param isRemove 是否移除
     * @return Cookie值，不存在返回null
     */
    public static String getCookie(HttpServletRequest request, HttpServletResponse response, String name, boolean isRemove) {
        String value = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    try {
                        value = URLDecoder.decode(cookie.getValue(), DEFAULT_COOKIE_Encode);
                    } catch (Throwable e) {
                        log.error("Cookie的值解码失败", e);
                        value = cookie.getValue();
                    }
                    if (isRemove) {
                        cookie.setMaxAge(0);
                        response.addCookie(cookie);
                    }
                    break;
                }
            }
        }
        return value;
    }

    /**
     * 获得指定Cookie的值
     *
     * @param request 请求对象
     * @param name    名称
     * @return Cookie值
     */
    public static String getCookie(HttpServletRequest request, String name) {
        return getCookie(request, null, name, false);
    }

    /**
     * 获得指定Cookie的值，并删除
     *
     * @param request  请求对象
     * @param response 响应对象
     * @param name     名称
     * @return Cookie值
     */
    public static String getCookieAndRemove(HttpServletRequest request, HttpServletResponse response, String name) {
        return getCookie(request, response, name, true);
    }

}
