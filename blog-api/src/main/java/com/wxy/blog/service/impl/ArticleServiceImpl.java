package com.wxy.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wxy.blog.dao.dos.Archives;
import com.wxy.blog.dao.mapper.ArticleBodyMapper;
import com.wxy.blog.dao.mapper.ArticleMapper;
import com.wxy.blog.dao.mapper.ArticleTagMapper;
import com.wxy.blog.dao.pojo.Article;
import com.wxy.blog.dao.pojo.ArticleBody;
import com.wxy.blog.dao.pojo.ArticleTag;
import com.wxy.blog.dao.pojo.SysUser;
import com.wxy.blog.service.*;
import com.wxy.blog.utils.UserThreadLocal;
import com.wxy.blog.vo.ArticleBodyVo;
import com.wxy.blog.vo.Result;
import com.wxy.blog.vo.ArticleVo;
import com.wxy.blog.vo.TagVo;
import com.wxy.blog.vo.params.ArticleParam;
import com.wxy.blog.vo.params.PageParams;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service //注射到Spring容器中
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private TagService tagService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private ArticleBodyMapper articleBodyMapper;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ThreadService threadService ;
    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Override
    public Result listArticle(PageParams pageParams) {
        Page<Article> page = new Page<>(pageParams.getPage(),pageParams.getPageSize());
        IPage<Article> articleIPage = this.articleMapper.listArticle(
                page,
                pageParams.getCategoryId(),
                pageParams.getTagId(),
                pageParams.getYear(),
                pageParams.getMonth());
        return Result.success(copyList(articleIPage.getRecords(),true,true));
    }

    /*
    @Override
    public Result listArticle(PageParams pageParams) {
        //1.分页查询article数据库表

        Page<Article> page = new Page<>(pageParams.getPage(),pageParams.getPageSize());//声明Page对象传入当前页数，每页查询的数量
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();//声明查询条件LambdaQueryWrapper对象
        //是否查询该分类中的文章
        if(pageParams.getCategoryId() != null){
            //and category_id=#{categoryId}
            queryWrapper.eq(Article::getCategoryId,pageParams.getCategoryId());
        }
        //是否查询该标签中的文章
        List<Long> articleIdList = new ArrayList<>();
        if (pageParams.getTagId() != null){
            LambdaQueryWrapper<ArticleTag> articleTagLambdaQueryWrapper = new LambdaQueryWrapper<>();
            articleTagLambdaQueryWrapper.eq(ArticleTag::getTagId,pageParams.getTagId());//查标签
            List<ArticleTag> articleTags = articleTagMapper.selectList(articleTagLambdaQueryWrapper);//获取文章列表
            for (ArticleTag articleTag : articleTags) {//提取ArticleTag中的articleId
                articleIdList.add(articleTag.getArticleId());
            }
            if (articleIdList.size() > 0){//如果文章数量大于0则查询
                queryWrapper.in(Article::getId,articleIdList);
            }
        }
        //
        queryWrapper.orderByDesc(Article::getWeight,Article::getCreateDate);//先按是否置顶排序再按时间排序
        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);//声明Page对象传入页信息、排序方式，进行选择传入哪些内容
        List<Article> records = articlePage.getRecords(); //将分页结果传入到records
        //此时records里由很多信息，但并不是都需要，所以不能以原始数据库数据返回要进行信息筛选，将article转成articleVo再返回
        List<ArticleVo> articleVoList = copyList(records, true,true);//内容，需要显示标签，需要显示作者
        return Result.success(articleVoList);
    }
    */

    @Override
    public Result hotArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();//声明查询条件LambdaQueryWrapper对象
        queryWrapper.orderByDesc(Article::getCreateDate);//根据浏览量倒叙排
        queryWrapper.select(Article::getId,Article::getTitle);//只返回文章id和标题
        queryWrapper.last("limit "+limit);//注意空格
        //select id,title from article order by create_date desc limit 5
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles,false,false));//不需要作者和标签
    }

    @Override
    public Result newArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();//声明查询条件LambdaQueryWrapper对象
        queryWrapper.orderByDesc(Article::getViewCounts);//根据浏览量倒叙排
        queryWrapper.select(Article::getId,Article::getTitle);//只返回文章id和标题
        queryWrapper.last("limit "+limit);//注意空格
        //select id,title from article order by view_counts desc limit 5
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles,false,false));//不需要作者和标签
    }

    @Override
    public Result listArchives() {
        List<Archives> archivesList = articleMapper.listArchives();

        return Result.success(archivesList);
    }

    @Override
    public Result findArticleById(Long articleId) {
        /**
         * 1. 根据id查询文章信息
         * 2. 根据bodyid和categoryid 做关联查询
         * 3. 文章被浏览了，文章阅读数也要加，也就是在返回之前做一个更新操作
         *    这个更新操作会导致此次响应耗时更长，并且更新出问题还会影响看文章的操作
         *    用 线程池 解决这个问题，把更新操作放到更新池里执行，和主线程就不相关了
         */
        Article article = this.articleMapper.selectById(articleId);
        ArticleVo articleVo = copy(article, true,true,true,true);
        //线程池 更新浏览量
        threadService.updateArticleViewCount(articleMapper,article);
        return Result.success(articleVo);
    }

    @Override
    public Result publish(ArticleParam articleParam) {
        //该接口需加入到登录拦截器中
        SysUser sysUser = UserThreadLocal.get();
        /**
         * 1.发布文章 目的 构建Article对象
         * 2.作者id 当前的用户登录
         * 3.标签 要将标签加入到 关联表当中
         * 4.body 内容存储
         */
        Article article = new Article();
        article.setAuthorId(sysUser.getId());//作者 id
        article.setCategoryId(articleParam.getCategory().getId());//文章类别
        article.setCreateDate(System.currentTimeMillis());//创建日期
        article.setSummary(articleParam.getSummary());//简介
        article.setTitle(articleParam.getTitle());//标题
        article.setViewCounts(0);//浏览量
        article.setWeight(Article.Article_Common);//是否置顶
        article.setCommentCounts(0);//评论数
        this.articleMapper.insert(article); //插入后主键 id自动赋值
        //tag
        List<TagVo> tags = articleParam.getTags();
        if(tags != null){
            for (TagVo tag : tags) {//tag会不止一个所以要循环，articleParam传过来的tag是列表但articleTag里tag不是列表
                ArticleTag articleTag = new ArticleTag();
                articleTag.setArticleId(article.getId());
                articleTag.setTagId(tag.getId());
                this.articleTagMapper.insert(articleTag);
            }
        }
        //body
        ArticleBody articleBody = new ArticleBody();
        articleBody.setArticleId(article.getId());
        articleBody.setContent(articleParam.getBody().getContent());
        articleBody.setContentHtml(articleParam.getBody().getContentHtml());
        articleBodyMapper.insert(articleBody);
        article.setBodyId(articleBody.getId());//body id
        articleMapper.updateById(article);
        ArticleVo articleVo = new ArticleVo();
        articleVo.setId(article.getId());
        return Result.success(articleVo);
    }

    //将List<Article>映射到List<ArticleVo>
    private List<ArticleVo> copyList(List<Article> records,boolean isTags,boolean isAuthor) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record,isTags,isAuthor,false,false));
        }
        return articleVoList;
    }
    //重载
    private List<ArticleVo> copyList(List<Article> records,boolean isTags,boolean isAuthor,boolean isBody,boolean isCategory) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record,isTags,isAuthor,isBody,isCategory));
        }
        return articleVoList;
    }

    //将article映射到articleVo，判断是否需要作者，标签信息，文章内容，文章类别
    //分离copyList与copy是要考虑到有些接口不需要标签和作者
    public ArticleVo copy(Article article,boolean isTags,boolean isAuthor,boolean isBody,boolean isCategory){
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article, articleVo); //BeanUtils.copyProperties()由Spring提供的数据映射方法
        //article中creatDate属性为Long,vo中为String,需要转换
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        //判断是否需要并注入作者，标签信息
        if (isTags){
            Long articleId = article.getId();
            articleVo.setTags(tagService.findTagsByArticleId(articleId));
        }
        if (isAuthor) {
            Long articleId = article.getId();
            articleVo.setAuthor(sysUserService.findUserById(articleId).getNickname());
        }
        if(isBody){
            Long bodyId = article.getBodyId();
            articleVo.setBody(findArticleBodyById(bodyId));
        }
        if(isCategory){
            Long categoryId = article.getCategoryId();
            articleVo.setCategory(categoryService.findCategoryById(categoryId));
        }
        return articleVo;
    }

    private ArticleBodyVo findArticleBodyById(Long bodyId) {
        ArticleBody articleBody = articleBodyMapper.selectById(bodyId);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        articleBodyVo.setContent(articleBody.getContent());
        return articleBodyVo;
    }

}
