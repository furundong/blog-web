package com.example.blog.dao;

import com.example.blog.entity.SysRolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * (SysRolePermission)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-10 10:17:01
 */
public interface SysRolePermissionDao extends JpaRepository<SysRolePermission,String> {

}
