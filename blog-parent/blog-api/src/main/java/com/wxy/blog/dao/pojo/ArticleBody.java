package com.wxy.blog.dao.pojo;

import lombok.Data;

/**
 * 文章
 */

@Data
public class ArticleBody {

    private Long id;

    private String content;

    private String contentHtml;

    private Long articleId;
}