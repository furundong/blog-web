package com.example.blog.dao;

import com.example.blog.entity.SysPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 权限表(SysPermission)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-10 10:16:59
 */
public interface SysPermissionDao extends JpaRepository<SysPermission, String> {
    @Query(value = "SELECT r.ROLE_NAME roleName FROM sys_role r LEFT JOIN sys_role_permission rp ON rp.RID = r.ID LEFT JOIN sys_permission p ON rp.PID = p.id\n" +
            "\twhere url = :url", nativeQuery = true)
    List<String> findByUrl(String url);
}
