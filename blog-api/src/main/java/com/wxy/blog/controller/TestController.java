package com.wxy.blog.controller;


import com.wxy.blog.dao.pojo.SysUser;
import com.wxy.blog.utils.UserThreadLocal;
import com.wxy.blog.vo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

    @RequestMapping
    public Result test(){
        SysUser sysUser = UserThreadLocal.get();
        System.out.println(sysUser);
        return Result.success(null);
    }
}
