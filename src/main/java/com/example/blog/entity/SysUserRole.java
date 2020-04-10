package com.example.blog.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table
@Entity
public class SysUserRole {

  @Id
  @GenericGenerator(name = "idGenerator", strategy = "uuid")
  @GeneratedValue(generator = "idGenerator")
  private  String id;

  private String uid;
  private String rid;

}
