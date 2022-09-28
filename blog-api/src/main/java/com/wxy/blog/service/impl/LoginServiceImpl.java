package com.wxy.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.wxy.blog.dao.pojo.SysUser;
import com.wxy.blog.service.LoginService;
import com.wxy.blog.service.SysUserService;
import com.wxy.blog.utils.JWTUtils;
import com.wxy.blog.vo.ErrorCode;
import com.wxy.blog.vo.Result;
import com.wxy.blog.vo.params.LoginParam;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Transactional //事务注解，出问题时保证回滚，可以放在接口上，保证通用性
public class LoginServiceImpl implements LoginService {

    //加密盐
    private static final String salt = "mszlu!@#";
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public Result login(LoginParam loginParam) {
        /**
         * 1.检查参数是否合法
         * 2.根据用户名和密码去user表中查询 是否存在
         * 3.如果不存在 登录失败
         * 4.如果存在，使用jwt 生成token:user信息 设置过期时间
         * （登录认证的时候 先认证token字符串是否合法，去redis认证是否存在）
         */
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        //1.检查参数是否合法
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMsg());
        }
        //2.根据用户名和密码去user表中查询 是否存在 3.如果不存在 登录失败
        String pwd = DigestUtils.md5Hex(password + salt);//md5加密
        SysUser sysUser = sysUserService.findUser(account,pwd);
        if (sysUser == null){
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(),ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        //登录成功，使用JWT生成token，返回token和redis中
        String token = JWTUtils.createToken(sysUser.getId());
        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);//导入token,用户信息，过期时间
        return Result.success(token);
    }

    @Override
    public Result logout(String token) {
        //退出登录直接删除token就可以了
        redisTemplate.delete("TOKEN_"+token);
        return Result.success(null);
    }

    @Override
    public SysUser checkToken(String token) {
        if(StringUtils.isBlank(token)){//校验token合法性
            return null;
        }
        Map<String, Object> StringObjectMap = JWTUtils.checkToken(token);
        if(StringObjectMap == null){//查询该token是否存在
            return null;
        }
        String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);//查询redis里的token
        if(StringUtils.isBlank(userJson)){//如果redis里没有该token则表示登录过期
            return null;
        }
        SysUser sysUser = JSON.parseObject(userJson, SysUser.class);
        return sysUser;
    }

    @Override
    public Result register(LoginParam loginParam) {
        /**
         * 1.判断参数合法性
         * 2.判断用户是否存在
         * 3.不存在则注册，存在则返回用户已存在
         * 4.生成token
         * 5.存入redis 并返回
         * 6.注意加上事务，一旦中间的任何过程出现问题，注册的用户需要回滚
         */
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        String nickname = loginParam.getNickname();
        if (StringUtils.isBlank(account)
                || StringUtils.isBlank(password)
                || StringUtils.isBlank(nickname)
        ){//检验参数合法性（是否为空，感觉这个应该让前端来检验）
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMsg());
        }

        SysUser sysUser = this.sysUserService.findUserByAccount(account);
        if (sysUser != null){//检验用户是否存在
            return Result.fail(ErrorCode.ACCOUNT_EXIST.getCode(),ErrorCode.ACCOUNT_EXIST.getMsg());
        }
        sysUser = new SysUser();
        sysUser.setNickname(nickname);
        sysUser.setAccount(account);
        sysUser.setPassword(DigestUtils.md5Hex(password+salt));
        sysUser.setCreateDate(System.currentTimeMillis());
        sysUser.setLastLogin(System.currentTimeMillis());
        sysUser.setAvatar("/static/img/logo.b3a48c0.png");
        sysUser.setAdmin(1); //1 为true
        sysUser.setDeleted(0); // 0 为false
        sysUser.setSalt("");
        sysUser.setStatus("");
        sysUser.setEmail("");
        this.sysUserService.save(sysUser);//mybatisplus有自己的save方法，这里用自己写的

        //注册完直接登录，使用JWT生成token，返回token和redis中
        String token = JWTUtils.createToken(sysUser.getId());
        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);//导入token,用户信息，过期时间
        return Result.success(token);
    }

}
