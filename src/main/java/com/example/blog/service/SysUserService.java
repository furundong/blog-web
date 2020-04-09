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
public class SysUserService implements UserDetailsService {

    @Resource
    SysUserDao sysUserDao;

    @Resource
    SysRoleDao sysRoleDao;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        SysUser byUsername = sysUserDao.findByUsername(s);
        if(byUsername==null){
            throw new UsernameNotFoundException("用户名不存在");
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        List<String> roleByUserId = sysRoleDao.findRoleByUserId(byUsername.getId());
        for (String s1 : roleByUserId) {
            authorities.add(new SimpleGrantedAuthority(s1));
        }
        return new User(byUsername.getUsername(),byUsername.getPassword(),authorities);
    }
}
