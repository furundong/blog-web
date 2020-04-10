package com.example.blog.config.security;


import com.example.blog.entity.response.Result;
import com.example.blog.entity.response.ResultCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class CustomizeAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

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
    }
}
