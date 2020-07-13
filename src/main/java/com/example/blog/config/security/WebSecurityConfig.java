package com.example.blog.config.security;

import com.example.blog.service.SysUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import javax.annotation.Resource;

/**
 * Created by sang on 2017/12/17.
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //获取用户账号密码及权限信息
    @Resource
    SysUserService userService;

    //明文加密方式
    @Resource
    PasswordEncoder passwordEncoder;

    //登录成功处理
    @Resource
    CustomizeAuthenticationSuccessHandler authenticationSuccessHandler;

    //登录失败处理
    @Resource
    CustomizeAuthenticationFailureHandler authenticationFailureHandler;

    //注销登录
    @Resource
    CustomizeLogoutSuccessHandler logoutSuccessHandler;

    //匿名用户访问无权限资源时的异常
    @Resource
    CustomizeAuthenticationEntryPoint authenticationEntryPoint;

    //会话失效(账号被挤下线)处理逻辑
    @Resource
    CustomizeSessionInformationExpiredStrategy sessionInformationExpiredStrategy;

    //访问决策管理器
    @Resource
    CustomizeAccessDecisionManager accessDecisionManager;

    //实现安全元数据源权限拦截
    @Resource
    CustomizeFilterInvocationSecurityMetadataSource securityMetadataSource;

    //拦截器
    @Resource
    CustomizeAbstractSecurityInterceptor securityInterceptor;

    //权限不足拦截器
    @Resource
    CustomizeAccessDeniedHandler customizeAccessDeniedHandler;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();
        //这里的角色-权限关联关系， 开始拦截且处理
        http.authorizeRequests().withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
            @Override
            public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                o.setAccessDecisionManager(accessDecisionManager);//访问决策管理器
                o.setSecurityMetadataSource(securityMetadataSource);//安全元数据源
                return o;
            }
        })
                //异常处理(权限拒绝、登录失效等)
                .and().exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)//匿名用户访问无权限资源时的异常处理
                //权限不足异常处理
                .and().exceptionHandling().accessDeniedHandler(customizeAccessDeniedHandler)
                //登入
                .and().formLogin().permitAll()//允许所有用户
                .successHandler(authenticationSuccessHandler)//登录成功处理逻辑
                .failureHandler(authenticationFailureHandler)//登录失败处理逻辑
                //登出
                .and().logout().permitAll()//允许所有用户
                .logoutSuccessHandler(logoutSuccessHandler)//登出成功处理逻辑
                .deleteCookies("JSESSIONID")//登出之后删除cookie
//                .and().csrf().disable()
                //会话信息过期策略会话信息过期策略(账号被挤下线)
                .and().sessionManagement().maximumSessions(1).expiredSessionStrategy(sessionInformationExpiredStrategy)
        ;
        http.addFilterBefore(securityInterceptor, FilterSecurityInterceptor.class);//增加到默认拦截链中
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/blog/img/**", "/index.html", "/static/**", "/favicon.ico");
    }

}
