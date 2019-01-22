package com.learn.filterexample.configuration;

import com.learn.filterexample.filter.AntiTheftChainFilter;
import com.learn.filterexample.filter.CacheFilter;
import com.learn.filterexample.filter.FileUploadFilter;
import com.learn.filterexample.filter.LogFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    /**
     * 注册防盗链Filter
     * @return
     */
    @Bean
    public FilterRegistrationBean antiTheftChainFilter() {
        AntiTheftChainFilter antiTheftChainFilter = new AntiTheftChainFilter();
        FilterRegistrationBean registration = new FilterRegistrationBean(antiTheftChainFilter);
        //指定过滤的url
        registration.addUrlPatterns("/image/*");
        //设置初始化参数（可以添加多个）
        registration.addInitParameter("paramName1", "paramValue1");
        registration.addInitParameter("paramName2", "paramValue2");
        //设置过滤器名称
        registration.setName("antiTheftChainFilter");
        //设置过滤器顺序
        registration.setOrder(2);
        return registration;
    }

    /**
     * 注册日志记录Filter
     * @return
     */
    @Bean
    public FilterRegistrationBean logFilter() {
        LogFilter logFilter = new LogFilter();
        FilterRegistrationBean registration = new FilterRegistrationBean(logFilter);
        //指定过滤的url
        registration.addUrlPatterns("/*");
        //设置过滤器名称
        registration.setName("logFilter");
        //设置过滤器顺序
        registration.setOrder(1);
        return registration;
    }

    /**
     * 注册缓存Filter
     * @return
     */
    @Bean
    public FilterRegistrationBean CacheFilter() {
        CacheFilter cacheFilter = new CacheFilter();
        FilterRegistrationBean registration = new FilterRegistrationBean(cacheFilter);
        //指定过滤的url
        registration.addUrlPatterns("/*");
        //设置初始化参数（可以添加多个）
        registration.addInitParameter("cacheMax", "3600000");
        //设置过滤器名称
        registration.setName("cacheFilter");
        //设置过滤器顺序
        registration.setOrder(3);
        return registration;
    }

    /**
     * 注册文件上传Filter
     * @return
     */
    @Bean
    public FilterRegistrationBean FileUploadFilter() {
        FileUploadFilter fileUploadFilter = new FileUploadFilter();
        FilterRegistrationBean registration = new FilterRegistrationBean(fileUploadFilter);
        //指定过滤的url
        registration.addUrlPatterns("/*");
        //设置过滤器名称
        registration.setName("fileUploadFilter");
        //设置过滤器顺序
        registration.setOrder(4);
        return registration;
    }

}
