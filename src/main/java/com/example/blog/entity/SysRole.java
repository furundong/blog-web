package com.example.blog.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * (SysRole)实体类
 *
 * @author makejava
 * @since 2020-04-09 13:36:08
 */
@Data
@Entity
@Table
public class SysRole implements Serializable {
    private static final long serialVersionUID = -92659696200487771L;
    /**
    * 编号
    */
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    private String id;
    /**
    * 角色名称
    */
    private String roleName;
    /**
    * 角色描述
    */
    private String roleDesc;

}
