package com.wxy.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wxy.blog.dao.mapper.TagMapper;
import com.wxy.blog.dao.pojo.Tag;
import com.wxy.blog.service.TagService;
import com.wxy.blog.vo.Result;
import com.wxy.blog.vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagMapper tagMapper;
    //将tag映射到tagVo
    public TagVo copy(Tag tag){
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag,tagVo);
        return tagVo;
    }
    //将List<Tag>映射到List<TagVo>
    public List<TagVo> copyList(List<Tag> tagList){
        List<TagVo> tagVoList = new ArrayList<>();
        for (Tag tag : tagList) {
            tagVoList.add(copy(tag));
        }
        return tagVoList;
    }

    @Override
    public List<TagVo> findTagsByArticleId(Long articleId) {
        //mybatisplus无法进行多表查询，根据文章id查找标签
        List<Tag> tags = tagMapper.findTagsByArticleId(articleId);
        return copyList(tags);
    }

    @Override
    public Result hots(int limit){
        /**
         * 1.标签所拥有的文章数量最多的前limit 是最热标签
         * 2.查询 根据tag_id 分组 计数， 从大到小 排列 取前 limit 个
         */
        List<Long> tagIds = tagMapper.findHotsTagIds(limit); //取tagId
        if(CollectionUtils.isEmpty(tagIds)){//若为空传递空值过去
            return Result.success(Collections.emptyList());
        }
        //需求是tagId 和 tagName Tag对象
        //select * from tag where id in (1,2,3,4)
        List<Tag> tagList = tagMapper.findTagsByTagIds(tagIds);//取tag对象
        return Result.success(tagList);
    }

    @Override
    public Result findAll() {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Tag::getId,Tag::getTagName);//用多少查多少
        List<Tag> tags = this.tagMapper.selectList(queryWrapper);
        return Result.success(copyList(tags));
    }

    @Override
    public Result findAllDetail() {
        List<Tag> tags = this.tagMapper.selectList(new LambdaQueryWrapper<>());
        return Result.success(copyList(tags));
    }

    @Override
    public Result findDetailById(Long id) {
        Tag tag = tagMapper.selectById(id);
        TagVo copy = copy(tag);
        return Result.success(copy);
    }
}
