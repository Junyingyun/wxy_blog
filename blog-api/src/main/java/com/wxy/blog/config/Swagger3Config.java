package com.wxy.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;

@Configuration
public class Swagger3Config {
    /**
     * 配置swagger的Docket bean
     * @return
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30) // 指定swagger3.0版本
                .enable(true) //控制Swagger是否运行
                .groupName("开发组01")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.wxy.blog.controller"))//RequestHandlerSelectors配置要扫描接口的方式
                .build()
                .apiInfo(createApiInfo());

    }
    /**
     * 配置swagger的ApiInfo bean，即swagger信息
     * @return
     */
    @Bean
    public ApiInfo createApiInfo(){
        return new ApiInfo("Junying Blog Swagger"
                ,"Junying Blog Documentation"
                ,"3.0"
                ,"http://www.junying.fun"
                ,new Contact("wxy", "http://www.junying.fun",
                "1751833747@qqcom")
                ,"Apache 2.0"
                ,"http://www.apache.org/licenses/LICENSE-2.0"
                ,new ArrayList());
    }
}