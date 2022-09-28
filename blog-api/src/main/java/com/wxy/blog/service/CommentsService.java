package com.wxy.blog.service;

import com.wxy.blog.vo.Result;
import com.wxy.blog.vo.params.CommentParam;

public interface CommentsService {
    /**
     * 根据文章id查询所有评论
     * @param articleId
     * @return
     */
    Result commentsByArticleId(Long articleId);

    /**
     * 用户评论功能
     * @param commentParam
     * @return
     */
    Result comment(CommentParam commentParam);
}
