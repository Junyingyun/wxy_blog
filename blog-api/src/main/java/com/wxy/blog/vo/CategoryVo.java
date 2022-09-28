package com.wxy.blog.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("CategoryVo 类别")
public class CategoryVo {

    @ApiModelProperty("类别ID")
    private Long id;

    @ApiModelProperty("类别图标")
    private String avatar;

    @ApiModelProperty("类别名")
    private String categoryName;

    @ApiModelProperty("类别描述")
    private String description;
}
