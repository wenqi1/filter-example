package com.learn.filterexample.filter;

import com.learn.filterexample.response.CacheResponseWrapper;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * 缓存Filter
 */
public class CacheFilter implements Filter {
    //缓存文件夹
    private File cacheFolder;
    //缓存时长
    private long cacheMax;
    private ServletContext servletContext;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        try {
            cacheFolder = ResourceUtils.getFile("classpath:cache");
        } catch (IOException e) {
            e.printStackTrace();
        }
        cacheMax = new Long(filterConfig.getInitParameter("cacheMax"));
        servletContext = filterConfig.getServletContext();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;

        if(!"GET".equalsIgnoreCase(request.getMethod())){
            filterChain.doFilter(request,response);
            return;
        }

        String url = request.getRequestURI();
        if(url == null){
            url = "";
        }
        url = url.replace(request.getContextPath() + "/", "");
        url = request.getQueryString() == null ? url : (url + "?" + request.getQueryString());
        //以请求的url为文件名，创建缓存文件
        File cacheFile = new File(cacheFolder, URLEncoder.encode(url, "utf-8"));
        //判断缓存文件是否存在
        if(!cacheFile.exists() ||
                cacheFile.length() == 0 ||
                cacheFile.lastModified() < System.currentTimeMillis() - cacheMax){
            CacheResponseWrapper cacheResponseWrapper = new CacheResponseWrapper(response);
            filterChain.doFilter(request,cacheResponseWrapper);
            //响应的内容
            byte[] content = cacheResponseWrapper.getByteArrayOutputStream().toByteArray();
            FileOutputStream fileOutputStream = new FileOutputStream(cacheFile);
            //响应写入缓存文件
            fileOutputStream.write(content);
            fileOutputStream.close();
        }

        //读取缓存文件
        byte[] bytes = FileCopyUtils.copyToByteArray(cacheFile);
        response.getOutputStream().write(bytes);

    }

    @Override
    public void destroy() {

    }
}
