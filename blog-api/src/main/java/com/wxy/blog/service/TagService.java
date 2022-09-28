package com.wxy.blog.service;

import com.wxy.blog.vo.Result;
import com.wxy.blog.vo.TagVo;

import java.util.List;

public interface TagService {
    /**
     * 根据文章id查询标签列表
     * @param articleId
     * @return
     */
    List<TagVo> findTagsByArticleId(Long articleId);

    Result hots(int limit);

    Result findAll();

    Result findAllDetail();

    Result findDetailById(Long id);
}
