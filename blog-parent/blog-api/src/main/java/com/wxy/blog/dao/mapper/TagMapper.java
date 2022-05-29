package com.wxy.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wxy.blog.dao.pojo.Tag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TagMapper extends BaseMapper<Tag> {
    /**
     * 根据文章id查询标签列表
     * @param articleId
     * @return
     */
    List<Tag> findTagsByArticleId(Long articleId);

    /**
     * 查找最热的limit个标签Id
     * @param limit
     * @return
     */
    List<Long> findHotsTagIds(int limit);

    /**
     * 根据标签Id查找tags
     * @param tagIds
     * @return
     */
    List<Tag> findTagsByTagIds(List<Long> tagIds);
}
