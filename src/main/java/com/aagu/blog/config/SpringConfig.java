package com.aagu.blog.config;

import com.aagu.blog.common.StringDateConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class SpringConfig extends WebMvcConfigurationSupport {

    @Value("${file.path}")
    private String resLoc;

    @Override
    protected void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringDateConverter());
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations(
                "classpath:/static/",
                "file:"+resLoc
        );
    }
}
