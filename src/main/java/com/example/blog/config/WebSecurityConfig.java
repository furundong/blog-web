package com.example.blog.config;

import com.alibaba.fastjson.JSON;
import com.example.blog.entity.SysUser;
import com.example.blog.entity.response.Result;
import com.example.blog.entity.response.ResultCode;
import com.example.blog.service.SysUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.annotation.Resource;
import java.io.PrintWriter;

/**
 * Created by sang on 2017/12/17.
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    SysUserService userService;

    @Resource
    PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);;
    }

    @Bean
    LoginFilter loginFilter() throws Exception {
        LoginFilter loginFilter = new LoginFilter();
        loginFilter.setAuthenticationSuccessHandler((request, response, authentication) -> {
            response.setContentType("application/json;charset=utf-8");
            PrintWriter out = response.getWriter();
            SysUser sysUser = (SysUser) authentication.getPrincipal();
            sysUser.setPassword(null);
            String result = JSON.toJSONString(new Result(ResultCode.SUCCESS, sysUser));
            out.write(result);
            out.flush();
            out.close();
        });
        loginFilter.setAuthenticationFailureHandler((request, response, exception) -> {
            response.setContentType("application/json;charset=utf-8");
            PrintWriter out = response.getWriter();
            Result respBean = new Result(ResultCode.FAIL);
            if (exception instanceof LockedException) {
                respBean.setMessage("账户被锁定，请联系管理员!");
            } else if (exception instanceof CredentialsExpiredException) {
                respBean.setMessage("密码过期，请联系管理员!");
            } else if (exception instanceof AccountExpiredException) {
                respBean.setMessage("账户过期，请联系管理员!");
            } else if (exception instanceof DisabledException) {
                respBean.setMessage("账户被禁用，请联系管理员!");
            } else if (exception instanceof BadCredentialsException) {
                respBean.setMessage("用户名或者密码输入错误，请重新输入!");
            }
            out.write(new ObjectMapper().writeValueAsString(respBean));
            out.flush();
            out.close();
        });
        loginFilter.setAuthenticationManager(authenticationManagerBean());
        loginFilter.setFilterProcessesUrl("/login");
        return loginFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/**").access("hasRole('admin')")
                .antMatchers("/admin/**").permitAll()///admin/**的URL都需要有超级管理员角色，如果使用.hasAuthority()方法来配置，需要在参数中加上ROLE_,如下.hasAuthority("ROLE_超级管理员")
                .anyRequest().authenticated()//其他的路径都是登录后即可访问
                .and().logout().permitAll().and().csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/blog/img/**","/index.html","/static/**","/favicon.ico");
    }

    @Bean
    AccessDeniedHandler getAccessDeniedHandler() {
        return new AuthenticationAccessDeniedHandler();
    }
}
