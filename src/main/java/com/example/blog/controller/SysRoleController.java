package com.example.blog.controller;

import com.example.blog.entity.SysRole;
import com.example.blog.entity.response.Result;
import com.example.blog.service.SysRoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (SysRole)表控制层
 *
 * @author makejava
 * @since 2020-04-09 14:04:47
 */
@RestController
@RequestMapping("role")
public class SysRoleController {
    /**
     * 服务对象
     */
    @Resource
    private SysRoleService sysRoleService;

    @GetMapping("findAllRole")
    public Result findAllRole() {
        return sysRoleService.findAllRole();
    }

    @GetMapping("findAllRoleByUser/{id}")
    public Result findAllRoleByUser(@PathVariable String id){
        return sysRoleService.findAllRoleByUser(id);
    }

    @PostMapping("assignRole/{id}")
    public Result assignRole(@PathVariable String id,String[] rolesId){
        return sysRoleService.assignRole(rolesId,id);
    }

    @GetMapping("getData")
    public Result getData(Integer pageNum, Integer pageSize, String roleName) {
        return sysRoleService.findAll(pageNum, pageSize, roleName);
    }

    @PutMapping
    public Result upd(@RequestBody SysRole role) {
        return sysRoleService.upd(role);
    }

    @PostMapping
    public Result save(@RequestBody SysRole role){
        return sysRoleService.save(role);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id){
        return sysRoleService.deleteById(id);
    }

    @DeleteMapping("batchDelete")
    public Result batchDelete(String[] ids){
        return sysRoleService.batchDelete(ids);
    }

}
