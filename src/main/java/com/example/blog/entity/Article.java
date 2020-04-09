package com.example.blog.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Data
@Entity
@Table
public class Article {
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    private String id;
    private String title;
    private String mdContent;
    private String htmlContent;
    private String summary;
    private Long cid;
    private Long uid;
    private Timestamp publishDate;
    private Integer state;
    private Integer pageView;
    private Timestamp editTime;
    private String nickname;
    private String cateName;
    private String stateStr;
}
