package com.example.blog.dao;

import com.example.blog.entity.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

/**
 * (SysRole)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-09 14:01:53
 */
public interface SysRoleDao  extends JpaRepository<SysRole,String> {

    @Query(value = "SELECT r.ROLE_NAME  FROM sys_user u, sys_user_role ur , sys_role r WHERE u.id = ur.uid and  r.id = ur.rid and  u.id = :uid",nativeQuery = true)
    List<String> findRoleByUserId(String uid);

    @Modifying
    @Transactional
    void deleteByIdIn(List<String> ids);

    @Query(value = "SELECT r.ID,r.ROLE_NAME,r.ROLE_DESC\n" +
            "FROM sys_user u\n" +
            "\tLEFT JOIN sys_user_role ur ON u.id = ur.uid\n" +
            "\tLEFT JOIN sys_role r on r.ID = ur.RID\n" +
            "\tWHERE u.id = :id",nativeQuery = true)
    List<SysRole> findAllRoleByUser(String id);
}
