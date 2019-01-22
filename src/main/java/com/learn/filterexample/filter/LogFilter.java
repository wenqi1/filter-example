package com.learn.filterexample.filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日志记录Filter
 */
public class LogFilter implements Filter {
    private Log log = LogFactory.getLog(this.getClass());
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        
        //执行前的时间
        long startTimeMillis = System.currentTimeMillis();
        String startTime = dateFormat.format(new Date(startTimeMillis));
        //发请求的地址
        String remoteAddr = request.getRemoteAddr();
        //访问的url
        String requestURI = request.getRequestURI();
        //请求参数
        String queryString = request.getQueryString();
        requestURI = queryString == null ? requestURI : (requestURI + "?" + queryString);

        filterChain.doFilter(request,response);

        //执行后的时间
        long endTimeMillis = System.currentTimeMillis();

        //输出日志
        log.info(startTime + " " + remoteAddr + "访问了" + requestURI + ",耗时" + (endTimeMillis - startTimeMillis));

    }

    @Override
    public void destroy() {

    }
}
