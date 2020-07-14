package com.example.blog.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table
@Entity
@NoArgsConstructor
public class SysUserRole {

    @Id
    private String id;

    private String uid;
    private String rid;

    public SysUserRole(String id ,String uid, String rid) {
        this.id = id;
        this.uid = uid;
        this.rid = rid;
    }
}
