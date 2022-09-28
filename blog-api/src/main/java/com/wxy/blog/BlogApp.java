package com.wxy.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * 启动类
 */

@EnableOpenApi
@SpringBootApplication
public class BlogApp {
    public static void main(String[] args){
        SpringApplication.run(BlogApp.class,args);
    }

}
