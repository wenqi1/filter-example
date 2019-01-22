package com.learn.filterexample.filter;

import com.learn.filterexample.request.UploadRequestWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FileUploadFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;

        UploadRequestWrapper uploadRequestWrapper = new UploadRequestWrapper(request);

        filterChain.doFilter(uploadRequestWrapper,response);
    }

    @Override
    public void destroy() {

    }
}
