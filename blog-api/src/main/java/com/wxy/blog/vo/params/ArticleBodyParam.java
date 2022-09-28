package com.wxy.blog.vo.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("ArticleBodyParam 文章内容")
public class ArticleBodyParam {

    @ApiModelProperty("文章内容MakeDown格式")
    private String content;

    @ApiModelProperty("文章内容HTML格式")
    private String contentHtml;

}