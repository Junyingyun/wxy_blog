package com.wxy.blog.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("LoginUserVo 登录信息")
public class LoginUserVo {

    @ApiModelProperty("用户id")
    private Long id;

    @ApiModelProperty("用户账号")
    private String account;

    @ApiModelProperty("密码")
    private String nickname;

    @ApiModelProperty("头像")
    private String avatar;
}
