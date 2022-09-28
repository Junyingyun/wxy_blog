package com.wxy.blog.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("CommentVo 评论信息")
public class CommentVo  {

    //转为String,要不然传入前端后会有精度损失
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("评论id")
    private Long id;

    @ApiModelProperty("评论者")
    private UserVo author;

    @ApiModelProperty("评论内容")
    private String content;

    @ApiModelProperty("评论孩子")
    private List<CommentVo> childrens;

    @ApiModelProperty("评论时间")
    private String createDate;

    @ApiModelProperty("评论层数")
    private Integer level;

    @ApiModelProperty("评论被评论用户ID")
    private UserVo toUser;
}