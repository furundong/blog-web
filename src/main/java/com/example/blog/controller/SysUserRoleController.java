package com.example.blog.controller;

import com.example.blog.service.SysUserRoleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * (SysUserRole)表控制层
 *
 * @author makejava
 * @since 2020-04-09 14:04:49
 */
@RestController
@RequestMapping("sysUserRole")
public class SysUserRoleController {
    /**
     * 服务对象
     */
    @Resource
    private SysUserRoleService sysUserRoleService;


}
