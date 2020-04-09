package com.example.blog.service;

import com.example.blog.entity.Article;
import com.example.blog.dao.ArticleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    @Autowired
    ArticleDao articleDao;

    public List<Article> findList(){
        return articleDao.findAll();
    }
}
