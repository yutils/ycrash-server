package com.yujing.crash.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.MultipartConfigElement;
import java.io.File;

/**
 * 设置虚拟路径，访问绝对路径下资源
 *
 * @author yujing 2019年6月24日10:17:11
 */
@Configuration
public class WebConfigurer implements WebMvcConfigurer {
    //虚拟路径
    @Value("${file.virtualPath}")
    private String virtualPath;
    //磁盘路径
    @Value("${file.physicsPath}")
    private String physicsPath;
    //登录拦截器
    LoginInterceptor loginInterceptor=new LoginInterceptor();

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册登录拦截器
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/", "/user/login", "/user/logout", "/user/register", "/upload/*", "/submit","/webStatic/*");
    }

    //虚拟路径映射
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //请求拦截器
        registry.addResourceHandler(virtualPath).addResourceLocations("file:" + physicsPath);
    }

    //临时存储路径
    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        String location = physicsPath + File.separator + "temp";
        File tmpFile = new File(location);
        if (!tmpFile.exists()) {
            tmpFile.mkdirs();
        }
        factory.setLocation(location);
        return factory.createMultipartConfig();
    }
}