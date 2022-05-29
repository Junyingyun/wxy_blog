package com.wxy.blog.service;

import com.wxy.blog.dao.pojo.SysUser;
import com.wxy.blog.vo.Result;
import com.wxy.blog.vo.UserVo;

public interface SysUserService {

    /**
     * 通过id查找用户
     * @param id
     * @return
     */
    SysUser findUserById(Long id);

    /**
     * 通过账户密码查找用户
     * @param account
     * @param pwd
     * @return
     */
    SysUser findUser(String account, String pwd);

    /**
     * 根据token查询用户信息
     * @param token
     * @return
     */
    Result findUserByToken(String token);

    /**
     * 根据账户查找用户
     * @param account
     * @return
     */
    SysUser findUserByAccount(String account);

    /**
     * 保存用户
     * @param sysUser
     */
    void save(SysUser sysUser);

    /**
     * 根据用户id查询用户信息
     * @param id
     * @return
     */
    UserVo findUserVoById(Long id);
}
