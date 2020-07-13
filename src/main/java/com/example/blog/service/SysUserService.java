package com.example.blog.service;

import com.example.blog.dao.SysRoleDao;
import com.example.blog.dao.SysUserDao;
import com.example.blog.entity.SysUser;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * (SysUser)表服务实现类
 *
 * @author makejava
 * @since 2020-04-09 14:04:47
 */
@Service
public class  SysUserService implements UserDetailsService {

    @Resource
    SysUserDao sysUserDao;

    @Resource
    SysRoleDao sysRoleDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = sysUserDao.findByUsername(username);
        if(user==null){
            throw new UsernameNotFoundException("用户名不存在");
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        List<String> roleByUserId = sysRoleDao.findRoleByUserId(user.getId());
        for (String s1 : roleByUserId) {
            authorities.add(new SimpleGrantedAuthority(s1));
        }
        return new User(user.getUsername(),
                user.getPassword(),
                user.getStatus(), //账号是否可用,1为可用，其余为不可用
                user.getAccount(),//账号是否过期
                user.getCredential(),//密码是否过期
                user.getAccountLock(),//账号是否被锁
                authorities);
    }
}
