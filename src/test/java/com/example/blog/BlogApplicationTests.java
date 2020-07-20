package com.example.blog;

import com.example.blog.controller.SystemControllerReflect;
import com.example.blog.entity.response.Result;
import com.example.blog.service.SysRoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BlogApplicationTests {

    @Resource
    SysRoleService sysRoleService;

    @Resource
    SystemControllerReflect systemControllerReflect;

    @Resource
    MockHttpServletRequest request;

    @Test
    public void contextLoads() {
        Result all = sysRoleService.findAll(0, 10, "");
        System.out.println("all = " + all);
    }

    @Test
    public void contextLoads2() {
        System.out.println("systemControllerReflect.getUrlMapping(request) = " + systemControllerReflect.getUrlMapping(request));
    }

    @Test
    public void testPasswordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String amdin = bCryptPasswordEncoder.encode("test");
        System.out.println("amdin = " + amdin);

    }


}
