package com.learn.filterexample.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 防盗链Filter
 */
public class AntiTheftChainFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //获取初始化参数
        String param1 = filterConfig.getInitParameter("paramName1");
        String param2 = filterConfig.getInitParameter("paramName2");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;

        //链接来源地址
        String referer = request.getHeader("referer");

        if(referer == null || !referer.contains(request.getServerName())){
            //转发到错误页面
            request.getRequestDispatcher("/error.jpg").forward(request,response);
        }else{
            filterChain.doFilter(request,response);
        }

    }

    @Override
    public void destroy() {

    }
}
