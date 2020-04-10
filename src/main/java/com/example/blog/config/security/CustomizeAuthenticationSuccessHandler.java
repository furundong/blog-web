package com.example.blog.config.security;

import com.alibaba.fastjson.JSON;
import com.example.blog.entity.response.Result;
import com.example.blog.entity.response.ResultCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class CustomizeAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
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
    }
}
