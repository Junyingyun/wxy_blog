package com.wxy.blog.dao.pojo;

import lombok.Data;

@Data
public class SysUser {

    //这里的Id是mybatise默认自动实现的内容，不需要手动赋值，
    //@TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String account;

    private Integer admin;

    private String avatar;

    private Long createDate;

    private Integer deleted;

    private String email;

    private Long lastLogin;

    private String mobilePhoneNumber;

    private String nickname;

    private String password;

    private String salt;

    private String status;
}
