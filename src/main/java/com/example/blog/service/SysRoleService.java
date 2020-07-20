package com.example.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.blog.dao.SysRoleDao;
import com.example.blog.dao.SysUserRoleDao;
import com.example.blog.entity.SysRole;
import com.example.blog.entity.SysUserRole;
import com.example.blog.entity.response.PageResult;
import com.example.blog.entity.response.Result;
import com.example.blog.entity.response.ResultCode;
import com.example.blog.mapper.SysRoleMapper;
import com.example.blog.utils.IdWorker;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
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

    @Resource
    SysRoleMapper sysRoleMapper;

    public Result findAllRole() {
        List<SysRole> all = sysRoleDao.findAll();
        if (all.size() == 0) {
            return new Result(ResultCode.SUCCESS);
        }
        return new Result(ResultCode.SUCCESS, all);
    }

    public Result findAllRoleByUser(String id) {
        List<SysRole> list = sysRoleDao.findAllRoleByUser(id);
        if (list.size() == 0) {
            return new Result(ResultCode.SUCCESS);
        }
        return new Result(ResultCode.SUCCESS, list);
    }

    public Result assignRole(String[] rolesId, String id) {
        //删除原来的用户角色
        sysUserRoleDao.deleteByUid(id);
        //重建用户角色关系
        List<SysUserRole> userRoles = new ArrayList<>();
        for (String s : rolesId) {
            userRoles.add(new SysUserRole(IdWorker.genId(), id, s));
        }
        sysUserRoleDao.saveAll(userRoles);
        return new Result(ResultCode.SUCCESS);
    }

    public Result findAll(Integer pageNum, Integer pageSize, String userName) {
        IPage<SysRole> iPage = new Page<>(pageNum, pageSize);
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(userName)) {
            wrapper.like("role_desc", userName);
        }
        IPage<SysRole> sysRoleIPage = sysRoleMapper.selectPage(iPage, wrapper);
        return new Result(ResultCode.SUCCESS, new PageResult<>(sysRoleIPage.getTotal(), sysRoleIPage.getRecords()));
    }

    public Result upd(SysRole role) {
        sysRoleMapper.updateById(role);
        return new Result(ResultCode.SUCCESS);
    }

    public Result save(SysRole role) {
        role.setId(IdWorker.genId());
        sysRoleMapper.insert(role);
        return new Result(ResultCode.SUCCESS);
    }

    public Result deleteById(String id){
        sysRoleMapper.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }


    public Result batchDelete(String[] ids) {
        sysRoleMapper.deleteBatchIds(Arrays.asList(ids));
        return new Result(ResultCode.SUCCESS);
    }
}
