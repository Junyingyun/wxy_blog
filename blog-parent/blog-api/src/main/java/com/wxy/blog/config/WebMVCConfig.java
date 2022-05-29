package com.wxy.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 项目启动时前端是8080端口，后端是8888端口，相当于是两个域名在相互访问，即两个不同的服务器在相互访问
 * 此时浏览器会有一个跨域的问题，不能访问8888端口
 */

@Configuration
public class WebMVCConfig implements WebMvcConfigurer {
    @Autowired
    private HandlerInterceptor loginInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //跨域配置，不可设置为*，不安全, 前后端分离项目，可能域名不一致
        //本地测试 端口不一致 也算跨域
        //域名：IP地址+端口
        registry.addMapping("/**").allowedOrigins("http://localhost:8080");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        //拦截test接口，后续实际遇到需要拦截的接口时，在配置为正真的拦截接口
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/test")
                .addPathPatterns("/articles/publish")
                .addPathPatterns("/comments/create/change"); // /** 表示拦截所有
    }
}
