package com.example.blog.service;

import com.example.blog.dao.SysRoleDao;
import com.example.blog.dao.SysUserDao;
import com.example.blog.dao.SysUserMapper;
import com.example.blog.dao.SysUserRoleDao;
import com.example.blog.entity.SysUser;
import com.example.blog.entity.response.PageResult;
import com.example.blog.entity.response.Result;
import com.example.blog.entity.response.ResultCode;
import com.example.blog.utils.IdWorker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    SysUserMapper sysUserMapper;

    @Resource
    SysRoleDao sysRoleDao;

    @Resource
    SysUserRoleDao sysUserRoleDao;

    @Resource
    PasswordEncoder bCryptPasswordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = sysUserDao.findByUsername(username);
        if (user == null) {
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

    public Result findAll(Integer pageNum, Integer pageSize, String userName) {
        PageRequest of = PageRequest.of(pageNum - 1, pageSize);
        Page<SysUser> all;
        if (StringUtils.isEmpty(userName)) {
            all = sysUserDao.findAll(of);
        } else {
            all = sysUserDao.findByUsernameContaining(userName, of);
        }
        List<SysUser> content = all.getContent();
        List<SysUser> list = content.stream().map(user -> new SysUser(user.getId(),user.getUsername())).collect(Collectors.toList());
        return new Result(ResultCode.SUCCESS, new PageResult<>(all.getTotalElements(), list));
    }

    public Result save(SysUser user) {
        user.setId(IdWorker.genId());
        user.setPassword(bCryptPasswordEncoder.encode(user.getUsername()));
        user.setAccount(true);
        user.setAccountLock(true);
        user.setCredential(true);
        user.setStatus(true);
        SysUser save = sysUserDao.save(user);
        return new Result(ResultCode.SUCCESS, save);
    }

    public Result update(SysUser user) {
        sysUserMapper.updateById(user);
        return new Result(ResultCode.SUCCESS);
    }

    @Transactional
    public Result deleteById(String id) {
        //删除用户下的所有用户角色关系
        sysUserRoleDao.deleteByUid(id);
        //删除用户
        sysUserDao.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }

    public Result batchDelete(String[] ids) {
        sysUserDao.deleteByIdIn(Arrays.asList(ids));
        return new Result(ResultCode.SUCCESS);
    }
}
