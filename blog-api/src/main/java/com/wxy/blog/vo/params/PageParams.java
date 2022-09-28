package com.wxy.blog.vo.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("PageParams 首页文章列表")
public class PageParams {
    @ApiModelProperty("显示页数")
    private int page = 1;

    @ApiModelProperty("每页显示数量")
    private int pageSize = 10;

    @ApiModelProperty("文章id")
    private Long categoryId;

    @ApiModelProperty("标签id")
    private Long tagId;

    @ApiModelProperty("发布年份")
    private String year;

    @ApiModelProperty("发布月份")
    private String month;

    public String getMonth(){
        if (this.month != null && this.month.length() == 1){
            return "0"+this.month;
        }
        return this.month;
    }
}
