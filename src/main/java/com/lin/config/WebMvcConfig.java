package com.lin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author lkmc2
 * @date 2019/2/1
 * @description 网络MVC配置
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                // 让静态资源可以访问到Swagger
                .addResourceLocations("classpath:/META-INF/resources/")
                // 让从网址上访问的静态资源映射到本机指定位置
                .addResourceLocations("file:F:/AwesomeVideoUpload/");
    }

}
