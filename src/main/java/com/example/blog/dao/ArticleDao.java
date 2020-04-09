package com.example.blog.dao;

import com.example.blog.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleDao extends JpaRepository<Article,String> {
}
