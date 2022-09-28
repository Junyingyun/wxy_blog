package com.wxy.blog.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("ArticleVo 文章信息")
public class ArticleVo {

    // @JsonSerialize(using = ToStringSerializer.class) 保证雪花算法得到的id的精度
    //建议不要使用Long存储雪花算法生成的id
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("文章id")
    private Long id;

    @ApiModelProperty("文章标题")
    private String title;

    @ApiModelProperty("文章简介")
    private String summary;

    @ApiModelProperty("评论数")
    private Integer commentCounts;

    @ApiModelProperty("浏览量")
    private Integer viewCounts;

    @ApiModelProperty("文章权重")
    private Integer weight;
    /**
     * 创建时间
     */
    @ApiModelProperty("文章创建时间")
    private String createDate;

    @ApiModelProperty("文章作者")
    private String author;

    @ApiModelProperty("文章内容")
    private ArticleBodyVo body;

    @ApiModelProperty("文章标签")
    private List<TagVo> tags;

    @ApiModelProperty("文章分类")
    private CategoryVo category;

}