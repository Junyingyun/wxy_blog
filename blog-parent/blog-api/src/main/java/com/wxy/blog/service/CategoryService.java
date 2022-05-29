package com.wxy.blog.service;

import com.wxy.blog.vo.CategoryVo;
import com.wxy.blog.vo.Result;

public interface CategoryService {

    /**
     * 根据id查找文章分类
     * @param id
     * @return
     */
    CategoryVo findCategoryById(Long id);

    /**
     * 查找所有文章分类
     * @return
     */
    Result findAll();

    /**
     * 详细查询所有文章分类
     * @return
     */
    Result findAllDetail();

    /**
     * 分类文章列表
     * @param id
     * @return
     */
    Result categoriesDetailById(Long id);
}
