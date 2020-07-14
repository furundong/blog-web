package com.example.blog.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Data
@Entity
@Table
public class Category {
    @Id
    private String id;
    private String cateName;
    private Timestamp date;

}
