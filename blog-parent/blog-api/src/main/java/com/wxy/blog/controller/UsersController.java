package com.wxy.blog.controller;

import com.wxy.blog.service.SysUserService;
import com.wxy.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UsersController {
    @Autowired
    private SysUserService sysUserService;

    ///users/currentUser
    @GetMapping("currentUser")
    public Result currentUser(@RequestHeader("Authorization") String token){  //@RequestHeader获取头部信息
        return sysUserService.findUserByToken(token);
    }
}
