package com.wxy.blog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags="helloWorld类测试")
@RestController
public class HelloWorldController {
    @ApiOperation("测试方法")
    @RequestMapping("/helloWorld")
    public String helloWorld(){
        return "helloWorld";
    }
}