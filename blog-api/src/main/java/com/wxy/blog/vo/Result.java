package com.wxy.blog.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 返回参数
 */
@Data
//全部构造方法
@AllArgsConstructor
public class Result {
    @ApiModelProperty()
    private boolean success;

    private int code;

    private String msg;

    private Object data;

    //传递成功
    public static Result success(Object data) {
        return new Result(true,200,"success",data);
    }

    //传递失败
    public static Result fail(Integer code, String msg) {
        return new Result(false,code,msg,null);
    }

}
