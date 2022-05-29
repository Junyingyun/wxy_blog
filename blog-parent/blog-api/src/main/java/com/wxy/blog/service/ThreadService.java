package com.wxy.blog.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.wxy.blog.dao.mapper.ArticleMapper;
import com.wxy.blog.dao.pojo.Article;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ThreadService {

    //异步执行
    @Async("taskExecutor")
    public void updateArticleViewCount(ArticleMapper articleMapper, Article article) {
        int viewCounts = article.getViewCounts();
        Article articleUpdate = new Article();
        articleUpdate.setViewCounts(viewCounts + 1);
        LambdaUpdateWrapper<Article> UpdateWrapper = new LambdaUpdateWrapper<>();
        //设值一个 为了在多线程的环境下 线程安全
        UpdateWrapper.eq(Article::getId,article.getId());
        //乐观锁CAS，如果修改的时候发现与期望的阅读量不一致，修改失败。即修改前查询一次，修改时再查询一次，如果未被改动则再执行
        //因为这个场景不重要，所以允许数据丢失，不用悲观锁
        //update article set view_count=100 where view_count=99 and id=11
        UpdateWrapper.eq(Article::getViewCounts,article.getViewCounts());
        articleMapper.update(articleUpdate,UpdateWrapper);
    }
}
