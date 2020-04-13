package com.example.blog.dao;

import com.example.blog.entity.SysPermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 权限表(SysPermission)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-10 10:16:59
 */
public interface SysPermissionDao extends JpaRepository<SysPermission,String> {
    List<SysPermission> findByUrl(String url);
}
