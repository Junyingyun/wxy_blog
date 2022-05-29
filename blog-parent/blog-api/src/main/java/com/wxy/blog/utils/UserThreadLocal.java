package com.wxy.blog.utils;

import com.wxy.blog.dao.pojo.SysUser;

public class UserThreadLocal {

    private UserThreadLocal(){}
    //线程变量隔离，在一个线程中设置私人信息
    private static final ThreadLocal<SysUser> LOCAL = new ThreadLocal<>();

    public static void put(SysUser sysUser){//放
        LOCAL.set(sysUser);
    }

    public static SysUser get(){//取
        return LOCAL.get();
    }

    public static void remove(){//删除
        LOCAL.remove();
    }
}