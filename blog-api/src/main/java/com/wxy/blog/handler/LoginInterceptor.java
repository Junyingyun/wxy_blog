package com.wxy.blog.handler;

import com.alibaba.fastjson.JSON;
import com.wxy.blog.dao.pojo.SysUser;
import com.wxy.blog.service.LoginService;
import com.wxy.blog.utils.UserThreadLocal;
import com.wxy.blog.vo.ErrorCode;
import com.wxy.blog.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器，SpringMVC
 */
@Component
@Slf4j  //lombok包中的日志
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //在执行controller方法（Handler）之前执行
        /**
         * 1.需要判断 请求的接口路径 是否为HandlerMethod (controller方法)
         * 2.判断token是否为空，如果为空 未登录
         * 3.token不为空，登录验证 loginServicw checkToken
         * 4.如果认证成功 放行
         */
        if (!(handler instanceof HandlerMethod)) {
            //handler 可能是 RequestResourceHandler springboot 程序 访问静态资源 默认去classpath下的static目录去查询
            return true;
        }

        String token = request.getHeader("Authorization");
        //打印登录信息
        log.info("=================request start===========================");
        String requestURI = request.getRequestURI();
        log.info("request uri:{}",requestURI);
        log.info("request method:{}",request.getMethod());
        log.info("token:{}", token);
        log.info("=================request end===========================");
        if(StringUtils.isBlank(token)){
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), "未登录");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
        //token是正确 判断是否登录
        SysUser sysUser = loginService.checkToken(token);
        if(sysUser == null){
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), "未登录");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
        //登录验证成功，放行

        //在controller中直接获取用户信息
        UserThreadLocal.put(sysUser);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //如果不删除 ThreadLocal中用完的信息 会有内存泄露的风险
        UserThreadLocal.remove();
    }

}
