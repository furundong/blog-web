package com.example.blog.controller;

import com.example.blog.entity.SysUser;
import com.example.blog.entity.response.Result;
import com.example.blog.service.SysUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (SysUser)表控制层
 *
 * @author makejava
 * @since 2020-04-09 14:04:48
 */
@RestController
@RequestMapping("user")
public class SysUserController {
    /**
     * 服务对象
     */
    @Resource
    private SysUserService sysUserService;

    @RequestMapping("getData")
    public Result getData(Integer pageNum, Integer pageSize, String userName) {
        return sysUserService.findAll(pageNum, pageSize, userName);
    }

    @PostMapping
    public Result add(@RequestBody SysUser user) {
        return sysUserService.save(user);
    }

    @PutMapping
    public Result upd(@RequestBody SysUser user) {
        return sysUserService.update(user);
    }

    @DeleteMapping("{id}")
    public Result del(@PathVariable String id) {
        return sysUserService.deleteById(id);
    }

    @DeleteMapping("batchDelete")
    public Result batchDelete(String[] ids){
        return sysUserService.batchDelete(ids);
    }
}
