package com.wxy.blog.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("TagVo 标签")
public class TagVo {

    @ApiModelProperty("标签id")
    private Long id;

    @ApiModelProperty("标签名")
    private String tagName;

    @ApiModelProperty("标签图片")
    private String avatar;
}
