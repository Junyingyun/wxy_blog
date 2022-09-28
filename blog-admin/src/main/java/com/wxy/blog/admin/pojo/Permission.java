package com.wxy.blog.admin.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Permission {

    @TableId(type = IdType.AUTO) //更改为自增id
    private Long id;

    private String name;

    private String path; //权限对应请求路径

    private String description; //描述信息
}