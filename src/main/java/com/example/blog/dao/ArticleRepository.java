package com.example.blog.dao;

import com.example.blog.bean.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article,String> {
}
