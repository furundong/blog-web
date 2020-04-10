package com.example.blog.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 权限表(SysPermission)实体类
 *
 * @author makejava
 * @since 2020-04-10 10:16:58
 */
@Data
@Entity
@Table
public class SysPermission implements Serializable {
    private static final long serialVersionUID = 808516100905413895L;
    /**
    * 主键id
    */
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    private String id;
    /**
    * 权限code
    */
    private String permissionCode;
    /**
    * 权限名
    */
    private String permissionName;
}
