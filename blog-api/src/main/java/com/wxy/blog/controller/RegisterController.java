package com.wxy.blog.controller;

import com.wxy.blog.service.LoginService;
import com.wxy.blog.vo.Result;
import com.wxy.blog.vo.params.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("register")
public class RegisterController {

    @Autowired
    private LoginService loginService;

    /**
     * 这里将register功能和login杂糅在了一起，当然不建议这样用
     * 接受参数使用了LoginParam类，因该另外创建RegisterParam类
     * 注册功能service层放在了LoginService里，建议单独领出来
     * @param loginParam
     * @return
     */
    @PostMapping
    public Result register(@RequestBody LoginParam loginParam){
        return loginService.register(loginParam);
    }
}
