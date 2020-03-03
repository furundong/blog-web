package com.example.blog.service;

import com.example.blog.bean.Article;
import com.example.blog.dao.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    @Autowired
    ArticleRepository articleRepository;

    public List<Article> findList(){
        return articleRepository.findAll();
    }
}
