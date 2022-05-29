package com.wxy.blog.admin.controller;

import com.wxy.blog.admin.model.params.PageParam;
import com.wxy.blog.admin.pojo.Permission;
import com.wxy.blog.admin.service.PermissionService;
import com.wxy.blog.admin.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private PermissionService permissionService;

    /**
     * 分页查询后台管理员
     * @param pageParam
     * @return
     */
    @PostMapping("permission/permissionList")
    public Result permissionList(@RequestBody PageParam pageParam){
        return permissionService.listPermission(pageParam);
    }

    //增
    @PostMapping("permission/add")
    public Result add(@RequestBody Permission permission){
        return permissionService.add(permission);
    }

    //改
    @PostMapping("permission/update")
    public Result update(@RequestBody Permission permission){
        return permissionService.update(permission);
    }

    //查
    @GetMapping("permission/delete/{id}")
    public Result delete(@PathVariable("id") Long id){
        return permissionService.delete(id);
    }
}

