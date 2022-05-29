package com.wxy.blog.controller;

import com.wxy.blog.common.aop.LogAnnotation;
import com.wxy.blog.common.cache.Cache;
import com.wxy.blog.service.ArticleService;
import com.wxy.blog.vo.params.ArticleParam;
import com.wxy.blog.vo.params.PageParams;
import com.wxy.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//json数据进行交互
@RestController
@RequestMapping("articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 首页文章列表
     * @param pageParams
     * @return
     */

    @PostMapping
    //加上这个注解表示要对此接口记录日志
    @LogAnnotation(module="文章",operator="获取文章列表")
    public Result listArticle(@RequestBody PageParams pageParams){
        return articleService.listArticle(pageParams);
    }

    /**
     * 首页最热文章
     * @return
     */
    @PostMapping("hot") //url
    public Result hotArticle(){
        int limit = 5;
        return articleService.hotArticle(limit);
    }

    /**
     * 首页最新文章
     * @return
     */
    @PostMapping("new") //url
    public Result newArticle(){
        int limit = 5;
        return articleService.newArticle(limit);
    }

    /**
     * 文章归档
     * @return
     */
    @PostMapping("listArchives") //url
    public Result listArchives(){
        return articleService.listArchives();
    }

    /**
     * 文章详情
     * @return
     */
    @PostMapping("view/{id}")
    public Result findArticleById(@PathVariable("id") Long articleId){
        return articleService.findArticleById(articleId);
    }

    /**
     * 文章发布功能
     * @param articleParam
     * @return
     */
    @PostMapping("publish")
    public Result publish(@RequestBody ArticleParam articleParam){
        return articleService.publish(articleParam);
    }
}
