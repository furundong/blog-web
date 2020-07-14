package com.example.blog.service;

import com.example.blog.dao.SysRoleDao;
import com.example.blog.dao.SysUserRoleDao;
import com.example.blog.entity.SysRole;
import com.example.blog.entity.SysUserRole;
import com.example.blog.entity.response.Result;
import com.example.blog.entity.response.ResultCode;
import com.example.blog.utils.IdWorker;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * (SysRole)表服务接口
 *
 * @author makejava
 * @since 2020-04-09 13:36:08
 */
@Service
@Transactional
public class SysRoleService {

    @Resource
    SysRoleDao sysRoleDao;

    @Resource
    SysUserRoleDao sysUserRoleDao;

    public Result findAllRole() {
        List<SysRole> all = sysRoleDao.findAll();
        if(all.size()==0){
            return new Result(ResultCode.SUCCESS);
        }
        return new Result(ResultCode.SUCCESS,all);
    }

    public Result findAllRoleByUser(String id) {
       List<SysRole> list =  sysRoleDao.findAllRoleByUser(id);
       if(list.size() == 0){
           return new Result(ResultCode.SUCCESS);
       }
        return new Result(ResultCode.SUCCESS,list);
    }

    public Result assignRole(String[] rolesId, String id) {
        //删除原来的用户角色
        sysUserRoleDao.deleteByUid(id);
        //重建用户角色关系
        List<SysUserRole> userRoles = new ArrayList<>();
        for (String s : rolesId) {
            userRoles.add(new SysUserRole(IdWorker.genId(),id,s));
        }
        sysUserRoleDao.saveAll(userRoles);
        return new Result(ResultCode.SUCCESS);
    }
}
