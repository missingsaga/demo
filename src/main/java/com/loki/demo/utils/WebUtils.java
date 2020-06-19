package com.loki.demo.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;

/**
 * Web工具类
 *
 * @author Loki
 */
public class WebUtils {
    /**
     * 一、设置Jackson序列化时只包含不为空的字段
     * new ObjectMapper().setSerializationInclusion(Include.NON_NULL);
     * 二、设置在反序列化时忽略在JSON字符串中存在，而在Java中不存在的属性
     * new ObjectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
     **/

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    private static final String UNKNOW = "unknown";

    /**
     * 获取IP
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        if (request == null) {
            return null;
        }

        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || UNKNOW.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOW.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOW.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOW.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || UNKNOW.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 如果存在多个反向代理，获得的IP是一个用逗号分隔的IP集合，取第一个
        if (StringUtils.contains(ip, ",")) {
            ip = StringUtils.substringBefore(ip, ",");
        }
        return ip;
    }

    /**
     * 从请求中获取对象
     *
     * @param request       HTTP请求
     * @param attributeName 属性名称
     * @param c             转换对象
     * @return
     */
    public static <T> T getAttrAsObject(HttpServletRequest request, String attributeName, Class<T> c) throws Exception {
        String attribute = request.getHeader(attributeName);

        if (attribute != null) {
            return MAPPER.readValue(URLDecoder.decode(attribute, "UTF-8"), c);
        }
        return null;
    }

}
