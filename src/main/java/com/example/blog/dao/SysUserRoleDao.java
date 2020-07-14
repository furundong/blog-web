package com.example.blog.dao;

import com.example.blog.entity.SysUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

/**
 * (SysUserRole)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-09 14:02:01
 */
public interface SysUserRoleDao  extends JpaRepository<SysUserRole,String> {

    @Transactional
    @Modifying
    void deleteByUid(String id);

}
