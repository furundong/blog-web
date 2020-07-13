package com.example.blog.config;

import com.example.blog.entity.response.Result;
import com.example.blog.entity.response.ResultCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice//控制器增强/
public class ExceptionCatch {

  /*  @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public Result accessDeniedException(Exception exception){
        exception.printStackTrace();
        return new Result(ResultCode.UNAUTHORISE);
    }*/

    //捕获Exception此类异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result exception(Exception exception){
        return new Result(ResultCode.FAIL.code(),"出现服务端异常，信息如下："+exception.getMessage(),false);
    }

}
