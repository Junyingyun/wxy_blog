package com.wxy.blog.service;

import com.wxy.blog.dao.pojo.SysUser;
import com.wxy.blog.vo.Result;
import com.wxy.blog.vo.params.LoginParam;

public interface LoginService {
    /**
     * 登录功能
     * @param loginParam
     * @return
     */
    Result login(LoginParam loginParam);

    /**
     * 退出的登录
     * @param token
     * @return
     */
    Result logout(String token);

    /**
     * 根据token返回用户信息
     * @param token
     * @return
     */
    SysUser checkToken(String token);

    /**
     * 注册
     * @param loginParam
     * @return
     */
    Result register(LoginParam loginParam);
}
