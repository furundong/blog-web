package com.example.blog.dao;

import com.example.blog.entity.SysUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;
import java.util.List;

/**
 * (SysUser)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-09 14:01:53
 */
public interface SysUserDao extends JpaRepository<SysUser,String> {

    SysUser findByUsername(String username);

    @Modifying
    @Transactional
    void deleteByIdIn(List<String> ids);

    Page<SysUser> findByUsernameContaining(String userName, Pageable pageable);
}
