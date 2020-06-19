package com.loki.demo.filter;

import com.loki.demo.utils.CookieUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class HttpServletRequestReplacedFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        MyHttpServletRequestWrapper requestWrapper = null;
        if (request instanceof HttpServletRequest) {
            HttpServletRequest req = (HttpServletRequest) request;
            String token = req.getHeader("Authorization");
            if (StringUtils.isBlank(token) || StringUtils.equals("Bearer", token)) {
                token = CookieUtils.getCookieValue(req, "token");
                requestWrapper = new MyHttpServletRequestWrapper(req);
                requestWrapper.addHeader("Authorization", "Bearer " + token);
            }
        }
        if (requestWrapper == null) {
            chain.doFilter(request, response);
        } else {
            chain.doFilter(requestWrapper, response);
        }
    }

    @Override
    public void destroy() {

    }

}
