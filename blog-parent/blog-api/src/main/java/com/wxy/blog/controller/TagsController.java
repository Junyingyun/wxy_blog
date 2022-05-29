package com.wxy.blog.controller;

import com.wxy.blog.service.TagService;
import com.wxy.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //代表返回的是一个json数据
@RequestMapping("tags")
public class TagsController {
    @Autowired
    private TagService tagService;

    @GetMapping("/hot")
    public Result listHotTags() {
        int limit = 6;  //返回最热的6个标签
        return tagService.hots(limit);
    }

    @GetMapping
    public Result findAll(){
        return tagService.findAll();
    }

    @GetMapping("detail")
    public Result findAllDetail(){
        return tagService.findAllDetail();
    }

    @GetMapping("detail/{id}")
    public Result findDetailById(@PathVariable("id") Long id){
        return tagService.findDetailById(id);
    }
}
