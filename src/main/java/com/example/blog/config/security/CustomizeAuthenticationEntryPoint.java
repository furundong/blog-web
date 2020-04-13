package com.example.blog.config.security;

import com.alibaba.fastjson.JSON;
import com.example.blog.entity.response.Result;
import com.example.blog.entity.response.ResultCode;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Hutengfei
 * @Description: 匿名用户访问无权限资源时的异常
 * @Date Create in 2019/9/3 21:35
 * <p>
 * AuthenticationEntryPoint主要是用来处理匿名用户访问无权限资源时的异常（即未登录，或者登录状态过期失效）
 */
@Component
public class CustomizeAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException exception) throws IOException, ServletException {
        Result result = new Result(ResultCode.FAIL);;
        if (exception instanceof InsufficientAuthenticationException) {
            result = new Result(ResultCode.UNAUTHORISE);
        }
        httpServletResponse.setContentType("text/json;charset=utf-8");
        httpServletResponse.getWriter().write(JSON.toJSONString(result));
    }
}
