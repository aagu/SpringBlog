package com.aagu.blog.config;

import com.aagu.blog.system.filter.RequestHolderFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<RequestHolderFilter> requestFilter() {
        FilterRegistrationBean<RequestHolderFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new RequestHolderFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }
}
