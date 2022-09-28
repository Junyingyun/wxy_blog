package com.wxy.blog.service;

import com.wxy.blog.vo.Result;
import com.wxy.blog.vo.params.ArticleParam;
import com.wxy.blog.vo.params.PageParams;

public interface ArticleService {
    /**
     * 分页查询 文章列表
     */
    Result listArticle(PageParams pageParams);

    /**
     * 最热文章
     * @param limit
     * @return
     */
    Result hotArticle(int limit);

    /**
     * 最新文章
     * @param limit
     * @return
     */
    Result newArticle(int limit);

    /**
     * 首页文章归档
     * @return
     */
    Result listArchives();

    /**
     * 文章内容
     * @param articleId
     * @return
     */
    Result findArticleById(Long articleId);

    /**
     * 发布文章
     * @param articleParam
     * @return
     */
    Result publish(ArticleParam articleParam);
}
