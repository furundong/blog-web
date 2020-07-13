package com.example.blog.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * (SysUser)实体类
 *
 * @author makejava
 * @since 2020-04-09 13:51:26
 */
@Data
@Table
@Entity
public class SysUser implements Serializable {
    private static final long serialVersionUID = -68810786789081461L;

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    private String id;
    /**
    * 用户名称
    */
    private String username;
    /**
    * 密码
    */
    private String password;
    /**
    * 1开启0关闭
    */
    private Boolean status;
    /**
     * 1开启0关闭
     */
    private Boolean account;
    /**
     * 1开启0关闭
     */
    private Boolean credential;
    /**
     * 1开启0关闭
     */
    private Boolean accountLock;

}
