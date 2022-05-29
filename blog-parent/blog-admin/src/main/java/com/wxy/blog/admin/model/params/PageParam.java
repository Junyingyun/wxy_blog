package com.wxy.blog.admin.model.params;

import lombok.Data;

/**
 * 接受页面传来的参数
 */
@Data
public class PageParam {

    private Integer currentPage;

    private Integer pageSize;

    private String queryString;
}
