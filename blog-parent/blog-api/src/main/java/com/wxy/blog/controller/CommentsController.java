package com.wxy.blog.controller;

import com.wxy.blog.service.CommentsService;
import com.wxy.blog.vo.Result;
import com.wxy.blog.vo.params.CommentParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("comments")
public class CommentsController {
    /**
     * 评论功能
     */

    @Autowired
    private CommentsService commentsService;

    /**
     * 展示评论
     * @param articleId
     * @return
     */
    @GetMapping("article/{id}")
    public Result comments(@PathVariable("id") Long articleId){

        return commentsService.commentsByArticleId(articleId);
    }

    /**
     * 评论
     * @param commentParam
     * @return
     */
    @PostMapping("create/change")
    public Result comment(@RequestBody CommentParam commentParam){
        return commentsService.comment(commentParam);
    }
}