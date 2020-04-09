package com.example.blog.dao;

import com.example.blog.entity.SysUserRole;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * (SysUserRole)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-09 14:02:01
 */
public interface SysUserRoleDao  extends JpaRepository<SysUserRole,String> {
}
