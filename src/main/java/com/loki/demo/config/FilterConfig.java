package com.loki.demo.config;

import com.loki.demo.filter.HttpServletRequestReplacedFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 过滤器配置类
 *
 * @author Loki
 */
@Configuration
public class FilterConfig {

    /**
     * 配置过滤器
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean<HttpServletRequestReplacedFilter> requestFilterRegistration() {
        FilterRegistrationBean<HttpServletRequestReplacedFilter> registration = new FilterRegistrationBean<>();
        // 注入过滤器
        registration.setFilter(new HttpServletRequestReplacedFilter());
        // 拦截规则
        registration.addUrlPatterns("/*");
        // 过滤器名称
        registration.setName("requestFilter");
        // 是否自动注册 false 取消Filter的自动注册
        // registration.setEnabled(false);
        // 过滤器顺序
        registration.setOrder(1);

        return registration;
    }

}
