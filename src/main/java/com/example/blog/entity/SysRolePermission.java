package com.example.blog.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * (SysRolePermission)实体类
 *
 * @author makejava
 * @since 2020-04-10 10:17:01
 */
@Entity
@Data
@Table
public class SysRolePermission implements Serializable {
    private static final long serialVersionUID = -67494405335832556L;
    /**
    * 角色编号
    */
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    private String id;


    private Integer rid;
    /**
    * 权限编号
    */
    private Integer pid;


}
