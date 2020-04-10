package com.example.blog.config;

import com.alibaba.fastjson.JSON;
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
import org.springframework.security.web.AuthenticationEntryPoint;
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

    @Resource
    AuthenticationEntryPoint authenticationEntryPoint;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }

    @Bean
    LoginFilter loginFilter() throws Exception {
        LoginFilter loginFilter = new LoginFilter();
        loginFilter.setAuthenticationSuccessHandler((request, response, authentication) -> {
            // 更新用户表上次登录时间、更新人、更新时间等字段
            // User userDetails = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            response.setContentType("application/json;charset=utf-8");
            PrintWriter out = response.getWriter();
            Object principal = authentication.getPrincipal();

            //此处还可以进行一些处理，比如登录成功之后可能需要返回给前台当前用户有哪些菜单权限，
            //进而前台动态的控制菜单的显示等，具体根据自己的业务需求进行扩展

//            principal.setPassword(null);
            String result = JSON.toJSONString(new Result(ResultCode.SUCCESS, principal));
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
            } else if (exception instanceof InternalAuthenticationServiceException) {
                respBean.setMessage("用户不存在!"); // 这个其实已经在userService已经写了
            } else if (exception instanceof DisabledException) {
                respBean.setMessage("账户被禁用，请联系管理员!");
            } else if (exception instanceof BadCredentialsException) {
                respBean.setMessage("用户名或者密码输入错误，请重新输入!");
            } else {
                //其他错误
                respBean.setMessage("登录失败");
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
                .antMatchers("/**").permitAll()
//                .antMatchers("/admin/**").permitAll()///admin/**的URL都需要有超级管理员角色，如果使用.hasAuthority()方法来配置，需要在参数中加上ROLE_,如下.hasAuthority("ROLE_超级管理员")
                .anyRequest().authenticated()//其他的路径都是登录后即可访问
                //异常处理(权限拒绝、登录失效等)
                .and().exceptionHandling().
                authenticationEntryPoint(authenticationEntryPoint)//匿名用户访问无权限资源时的异常处理
                .and().logout().permitAll().logoutSuccessHandler((req, resp, auth) ->{
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    out.write(JSON.toJSONString(new Result(ResultCode.SUCCESS)));
                    out.flush();
                    out.close();
        }).deleteCookies("JSESSIONID").and().csrf().disable();  // todo have some problem
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/blog/img/**", "/index.html", "/static/**", "/favicon.ico");
    }

    @Bean
    AccessDeniedHandler getAccessDeniedHandler() {
        return new AuthenticationAccessDeniedHandler();
    }
}
