package com.wxy.blog.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("ArticleBodyVo 文章内容")
public class ArticleBodyVo {

    @ApiModelProperty("文章内容")
    private String content;
}
