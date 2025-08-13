package com.jyotirmay.useridentity.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.io.IOException;

import static com.jyotirmay.useridentity.util.UserIdentityConstants.ACCEPT_VERSION;
import static com.jyotirmay.useridentity.util.UserIdentityConstants.ACCEPT_VERSION_V1;

public class VersionHeaderFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest instanceof HttpServletRequest httpRequest) {
            HttpServletRequestWrapper wrapper = new HttpServletRequestWrapper(httpRequest) {
                @Override
                public String getHeader(String name) {
                    if (ACCEPT_VERSION.equalsIgnoreCase(name) && super.getHeader(name) == null) {
                        return ACCEPT_VERSION_V1;
                    }

                    return super.getHeader(name);
                }
            };
            filterChain.doFilter(wrapper, servletResponse);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
