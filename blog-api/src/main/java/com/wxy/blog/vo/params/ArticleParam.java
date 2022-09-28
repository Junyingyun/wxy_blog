package com.wxy.blog.vo.params;

import com.wxy.blog.vo.CategoryVo;
import com.wxy.blog.vo.TagVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("ArticleParam 文章信息实体")
public class ArticleParam {

    @ApiModelProperty("文章id")
    private Long id;

    @ApiModelProperty("文章内容")
    private ArticleBodyParam body;

    @ApiModelProperty("文章分类")
    private CategoryVo category;

    @ApiModelProperty("文章简介")
    private String summary;

    @ApiModelProperty("文章标签")
    private List<TagVo> tags;

    @ApiModelProperty("文章标题")
    private String title;
}
