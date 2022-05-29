package com.wxy.blog.dao.pojo;

import lombok.Data;

/**
 * 实体类中最好不要出现基本数据类型，基本数据类型默认会有初始值
 * 应该使用包装类型
 */
@Data
public class Article {

    //置顶
    public static final int Article_TOP = 1;

    public static final int Article_Common = 0;

    private Long id;

    private String title;

    private String summary;

    private Integer commentCounts;

    private Integer viewCounts;

    /**
     * 作者id
     */
    private Long authorId;
    /**
     * 内容id
     */
    private Long bodyId;
    /**
     *类别id
     */
    private Long categoryId;

    /**
     * 置顶
     */
    private Integer weight ;


    /**
     * 创建时间
     */
    private Long createDate;
}