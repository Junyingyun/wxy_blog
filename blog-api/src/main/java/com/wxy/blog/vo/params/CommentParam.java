package com.wxy.blog.vo.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("CommentParam 评论信息")
public class CommentParam {

    @ApiModelProperty("被评论文章id")
    private Long articleId;

    @ApiModelProperty("评论内容")
    private String content;

    @ApiModelProperty("父评论id")
    private Long parent;

    @ApiModelProperty("评论被评论 用户的id")
    private Long toUserId;
}
